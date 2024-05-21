package com.fastcampuspay.banking.adapter.out.persistence.external.bank;

import com.fastcampuspay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import lombok.RequiredArgsConstructor;
import org.fastcampuspay.common.ExternalSystemAdapter;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // 실제로는 외부 은행에 http를 톨해서 실제 은행 계좌 정보를 가져와야함
        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
