//
//  CardListViewController.swift
//  CreditCardList
//
//  Created by 엄태양 on 2022/02/12.
//

import UIKit
import Kingfisher
import FirebaseDatabase
import FirebaseFirestore
import SwiftUI

class CardListViewController: UITableViewController {
    var creditCardList: [CreditCard] = []
//    var ref: DatabaseReference! // Firebase Realtime Database
    var db = Firestore.firestore()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //UITableView Cell Register
        let nibName = UINib(nibName: "CardListCell", bundle: nil)
        tableView.register(nibName, forCellReuseIdentifier: "CardListCell")
        
        
        
        //실시간 데이터 베이스 읽기
//        ref = Database.database().reference()
//
//        ref.observe(.value) { snapshot in
//            guard let value = snapshot.value as? [String: [String: Any]] else { return }
//
//            do {
//                let jsonData = try JSONSerialization.data(withJSONObject: value)
//                let cardData = try JSONDecoder().decode([String: CreditCard].self, from: jsonData)
//                let cardList = Array(cardData.values)
//                self.creditCardList = cardList.sorted{ $0.rank < $1.rank }
//
//                DispatchQueue.main.async {
//                    self.tableView.reloadData()
//                }
//
//            } catch let error {
//                print("Error JSON Parsing \(error.localizedDescription)")
//            }
//        }
        
        
        //Firestore 읽기
        db.collection("creditCardList").addSnapshotListener { snapshot, error in
            guard let documents = snapshot?.documents else {
                print("Error Firestore fetching document \(String(describing: error))")
                return
            }
            
            self.creditCardList = documents.compactMap { doc -> CreditCard? in
                do {
                    let jsonData = try JSONSerialization.data(withJSONObject: doc.data(), options: [])
                    let creditCard = try JSONDecoder().decode(CreditCard.self, from: jsonData)
                    return creditCard
                } catch let error {
                    print("Erorr JSON Parsing \(error.localizedDescription)")
                    return nil
                }
            }.sorted{ $0.rank < $1.rank}
            
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return creditCardList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "CardListCell", for: indexPath) as? CardListCell else { return UITableViewCell() }
        
        cell.rankLabel.text = "\(creditCardList[indexPath.row].rank)위"
        cell.promotionLabel.text = "\(creditCardList[indexPath.row].promotionDetail.amount)만원 증정"
        cell.cardNameLabel.text = "\(creditCardList[indexPath.row].name)"
        
        let imageURL = URL(string: creditCardList[indexPath.row].cardImageURL)
        cell.cardImageView.kf.setImage(with: imageURL)
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }

    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // 상세화면 전달
        let storyboard = UIStoryboard(name: "Main", bundle: .main)
        guard let detailViewController = storyboard.instantiateViewController(withIdentifier: "CardDetailViewController") as? CardDetailViewController else { return }
        
        detailViewController.promotionDetail = creditCardList[indexPath.row].promotionDetail
        self.show(detailViewController, sender: nil)
        
        
        //실시간 데이터 베이스 쓰기
        //Option1
//        let cardID = creditCardList[indexPath.row].id
//        ref.child("Item\(cardID)/isSelected ").setValue(true)
        
        
        //Option2
//        ref.queryOrdered(byChild: "id").queryEqual(toValue: cardID).observe(.value) { [weak self] snapshot in
//            guard let self = self,
//                  let value = snapshot.value as? [String:[ String: Any]],
//                  let key = value.keys.first else { return }
//
//            self.ref.child("\(key)/isSelected").setValue(true)
//        }
        
        //Firestore 쓰기
        //Option1
        let cardId = creditCardList[indexPath.row].id
//        db.collection("creditCardList").document("card\(cardId)").updateData(["isSelected": true])
        
        //Option2
        db.collection("creditCardList").whereField("id", isEqualTo: cardId).getDocuments { snapshot, _ in
            guard let document = snapshot?.documents.first else {
                print("Error Firesotre fetching document")
                return
            }
            
            document.reference.updateData(["isSelected": true])
        }
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            //실시간 데이터베이스 삭제
            //Option1
//            let cardId = creditCardList[indexPath.row].id
//            ref.child("Item\(cardId)").removeValue()
            
            //Option2
//            ref.queryOrdered(byChild: "id").queryEqual(toValue: cardId).observe(.value) {[weak self] snapshot in
//                guard let self = self,
//                      let value = snapshot.value as? [String: [String: Any]],
//                      let key = value.keys.first else { return }
//
//                self.ref.child(key).removeValue()
//            }
            
            //FireStore 삭제
            //Option1
            let cardId = creditCardList[indexPath.row].id
//            db.collection("creditCardList").document("card\(cardId)").delete()
            
            //Option2
            db.collection("creditCardList").whereField("id", isEqualTo: cardId).getDocuments{ snapshot, _ in
                guard let document = snapshot?.documents.first else {
                    print("Error Firestore fetching Error")
                    return
                }
                
                document.reference.delete()
            }
        }
    }
    
}
