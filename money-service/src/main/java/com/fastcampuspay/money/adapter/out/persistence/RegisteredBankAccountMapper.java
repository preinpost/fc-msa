package com.fastcampuspay.money.adapter.out.persistence;

import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {
    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity membershipJpaEntity) {
        return RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(membershipJpaEntity.getRegisteredBankAccountId() + ""),
                new RegisteredBankAccount.MembershipId(membershipJpaEntity.getMembershipId()),
                new RegisteredBankAccount.BankName(membershipJpaEntity.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(membershipJpaEntity.getBankAccountNumber()),
                new RegisteredBankAccount.LinkedStatusIdValid(membershipJpaEntity.isLinkedStatusIdValid())
        );
    }
}
