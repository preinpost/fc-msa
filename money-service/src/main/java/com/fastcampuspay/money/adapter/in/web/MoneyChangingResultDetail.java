package com.fastcampuspay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;

    // 증액, 감액
    private int moneyChangingType; // enum. 0: 증액, 1: 감액

    private int moneyChangingResultStatus;

    private int amount;
}

//enum MoneyChangingType {
//    INCREASING, // 증액
//    DECREASING, // 감액
//}
//
//enum MoneyChangingResultStatus {
//    SUCCEEDED, // 성공
//    FAILED, // 실패
//    FAILED_NOT_ENOUGH_MONEY,
//    FAILED_NOT_EXIST_MEMBERSHIP,
//    FAILED_NOT_EXIST_MONEY_CHARGING_REQUEST
//}