package com.esiran.greenpay.openapi.service.impl;

import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.openapi.entity.CashierInputDTO;
import com.esiran.greenpay.openapi.service.ICashierService;
import org.springframework.stereotype.Service;

@Service
public class CashierService implements ICashierService {
    @Override
    public void createCashierByInput(CashierInputDTO inputDTO, Merchant merchant) throws Exception {
        
    }
}
