package com.fastcampuspay.banking.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fastcampuspay.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestFirmbankingCommand extends SelfValidating<RequestFirmbankingCommand> {

    @NotNull
    private final String fromBankName;

    @NotNull
    private final String fromBankAccountNumber;

    @NotNull
    private final String toBankName;

    @NotNull
    private final String toBankAccountNumber;

    @NotNull
    private final int moneyAmount;

    public RequestFirmbankingCommand(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;

        validateSelf();
    }
}
