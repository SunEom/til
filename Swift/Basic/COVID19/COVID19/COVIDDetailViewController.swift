//
//  COVIDDetailViewController.swift
//  COVID19
//
//  Created by 엄태양 on 2022/02/10.
//

import UIKit

class COVIDDetailViewController: UITableViewController {
	@IBOutlet weak var newCaseCell: UITableViewCell!
	@IBOutlet weak var totalCaseCell: UITableViewCell!
	@IBOutlet weak var recoveredCell: UITableViewCell!
	@IBOutlet weak var deathCell: UITableViewCell!
	@IBOutlet weak var percentageCell: UITableViewCell!
	@IBOutlet weak var overseasInflowCell: UITableViewCell!
	@IBOutlet weak var regionalOutbreakCell: UITableViewCell!
	
	var covidOverview: CovidOverview?
	
	override func viewDidLoad() {
		super.viewDidLoad()
		self.configureView()
	}
	
	func configureView(){
		guard let covidOverview = self.covidOverview else { return }
		self.title = covidOverview.countryName
		self.newCaseCell.detailTextLabel?.text = "\(covidOverview.newCase)명"
		self.totalCaseCell.detailTextLabel?.text = "\(covidOverview.totalCase)명"
		self.recoveredCell.detailTextLabel?.text = "\(covidOverview.recovered)명"
		self.deathCell.detailTextLabel?.text = "\(covidOverview.death)명"
		self.percentageCell.detailTextLabel?.text = "\(covidOverview.percentage)명"
		self.overseasInflowCell.detailTextLabel?.text = "\(covidOverview.newFcase)명"
		self.regionalOutbreakCell.detailTextLabel?.text = "\(covidOverview.newCcase)명"
	}

}
