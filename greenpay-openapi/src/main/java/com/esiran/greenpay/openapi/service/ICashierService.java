package com.esiran.greenpay.openapi.service;

import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.openapi.entity.CashierInputDTO;

public interface ICashierService {
    void createCashierByInput(CashierInputDTO inputDTO, Merchant merchant) throws Exception;
}
