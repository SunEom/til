//
//  StationResponse.swift
//  SubwaySearch
//
//  Created by 엄태양 on 2022/07/29.
//

import Foundation

struct StationResponseModel: Decodable {
    var stations: [Station] { searchInfo.row }

    private let searchInfo: SearchInfoBySubwayNameServiceModel
    
    enum CodingKeys: String, CodingKey {
        case searchInfo = "SearchInfoBySubwayNameService"
    }
    
    struct SearchInfoBySubwayNameServiceModel: Decodable {
        var row: [Station] = []
    }
}

struct Station: Decodable {
    let stationName: String
    let lineNumber: String
    
    enum CodingKeys: String, CodingKey {
        case stationName = "STATION_NM"
        case lineNumber = "LINE_NUM"
    }
}

