package com.esiran.greenpay.openapi.controller;

import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.openapi.entity.Invoice;
import com.esiran.greenpay.openapi.entity.InvoiceInputDTO;
import com.esiran.greenpay.openapi.security.OpenAPISecurityUtils;
import com.esiran.greenpay.openapi.service.IInvoiceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1/cashiers")
public class APICashiers {
    private final IInvoiceService invoiceService;

    public APICashiers(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice create(@Valid InvoiceInputDTO invoiceDto) throws Exception {
        Merchant merchant = OpenAPISecurityUtils.getSubject();
        return invoiceService.createInvoiceByInput(invoiceDto,merchant);
    }
}
