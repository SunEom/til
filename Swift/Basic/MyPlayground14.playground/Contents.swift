import Foundation

enum PhoneError: Error {
    case unknown
    case batteryLow(batteryLevel: Int)
}


//throw PhoneError.batteryLow(batteryLevel: 20)

func checkPhoneBatteryStatus(batteryLevel: Int) throws -> String {
    guard batteryLevel != -1 else { throw PhoneError.unknown }
    guard batteryLevel > 20 else { throw PhoneError.batteryLow(batteryLevel: batteryLevel)}
    
    return "배터리 상태가 정상입니다."
}

do {
    try checkPhoneBatteryStatus(batteryLevel: 20)
} catch PhoneError.unknown {
    print("알 수 없는 에러입니다.")
} catch PhoneError.batteryLow(let batteryLevel){
    print("베터리 전원 부족, 남은 베터리: \(batteryLevel)%")
} catch {
    print("그 외 오류 발생: \(error)")
}


let status = try? checkPhoneBatteryStatus(batteryLevel: 30)
print(status)


let status2 = try! checkPhoneBatteryStatus(batteryLevel: 30)
print(status2)
