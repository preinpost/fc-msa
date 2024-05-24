package com.fastcampuspay.banking.application.port.out;

import com.fastcampuspay.banking.adapter.out.persistence.external.bank.ExternalFirmbankingRequest;
import com.fastcampuspay.banking.adapter.out.persistence.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {
    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest externalFirmbankingRequest);
}
