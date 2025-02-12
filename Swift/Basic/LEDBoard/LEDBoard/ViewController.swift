//
//  ViewController.swift
//  LEDBoard
//
//  Created by 엄태양 on 2022/01/07.
//

import UIKit

class ViewController: UIViewController,LEDBoardSettingDelegate {

    @IBOutlet weak var contentsLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.contentsLabel.textColor = .yellow
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let settingViewController = segue.destination as? SettingViewController {
            settingViewController.delegate = self
            settingViewController.ledText = self.contentsLabel.text
            settingViewController.textColor = self.contentsLabel.textColor
            settingViewController.backgroundColor = self.view.backgroundColor ?? .black
        }
    }
    
    func changedSetting(text: String?, color: UIColor, backgroundColor: UIColor) {
        if let text = text {
            self.contentsLabel.text = text
        }
        self.contentsLabel.textColor = color
        self.view.backgroundColor = backgroundColor
    }
}

