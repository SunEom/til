//
//  ViewController.swift
//  WeatherApp
//
//  Created by 엄태양 on 2022/02/10.
//

import UIKit

class ViewController: UIViewController {
	@IBOutlet weak var cityNameTextField: UITextField!
	@IBOutlet weak var cityNameLabel: UILabel!
	@IBOutlet weak var weatherDescribtionLabel: UILabel!
	@IBOutlet weak var tempLabel: UILabel!
	@IBOutlet weak var maxTempLabel: UILabel!
	@IBOutlet weak var minTempLabel: UILabel!
	@IBOutlet weak var weatherStackView: UIStackView!
	
	override func viewDidLoad() {
			super.viewDidLoad()
			// Do any additional setup after loading the view.
	}


	@IBAction func tapFetchWeatherButton(_ sender: UIButton) {
			if let cityName = self.cityNameTextField.text {
					self.getCurrentWeather(cityName: cityName)
					self.view.endEditing(true)
			}
	}
	
	func getCurrentWeather(cityName: String){
			guard let url = URL(string: "http://api.openweathermap.org/data/2.5/weather?q=\(cityName)&appid=3aafe7d2a71729dabbaa983a502a97b1") else { return }
			let session = URLSession(configuration: .default)
			session.dataTask(with: url) {[weak self] data, response, error  in
				let successRange = (200..<300)
				guard let data = data, error == nil else { return }
				let decoder = JSONDecoder()
				
				if let response = response as? HTTPURLResponse, successRange.contains(response.statusCode) {
					guard let weatherInformation = try? decoder.decode(WeatherInformation.self, from: data) else { return }
					DispatchQueue.main.async {
						self?.weatherStackView.isHidden = false
						self?.configureView(weatherInformation: weatherInformation)
					}
				} else {
					guard let errorMessage = try? decoder.decode(ErrorMessage.self, from: data) else { return }
					DispatchQueue.main.async {
						self?.showAlert(message: errorMessage.message)
					}
				}
				
				
			}.resume()
	}
	
	func configureView(weatherInformation : WeatherInformation) {
		self.cityNameLabel.text = weatherInformation.name
		if let weather = weatherInformation.weather.first {
			self.weatherDescribtionLabel.text = weather.description
		}
		self.tempLabel.text = "\(Int(weatherInformation.temp.temp - 273.15))°C"
		self.minTempLabel.text = "최저: \(Int(weatherInformation.temp.minTemp - 273.15))°C"
		self.maxTempLabel.text = "최고: \(Int(weatherInformation.temp.maxTemp - 273.15))°C"
	}
	
	func showAlert(message: String) {
		let alert = UIAlertController(title: "에러", message: message, preferredStyle: .alert)
		alert.addAction(UIAlertAction(title: "확인", style: .default, handler: nil))
		self.present(alert, animated: true, completion: nil)
	}
}

