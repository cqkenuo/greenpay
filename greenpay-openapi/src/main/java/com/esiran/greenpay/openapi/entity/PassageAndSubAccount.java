package com.esiran.greenpay.openapi.entity;

import com.esiran.greenpay.pay.entity.Passage;
import com.esiran.greenpay.pay.entity.PassageAccount;
import lombok.Data;

@Data
public class PassageAndSubAccount {
    private Passage passage;
    private PassageAccount passageAccount;

    public PassageAndSubAccount(Passage passage, PassageAccount passageAccount) {
        this.passage = passage;
        this.passageAccount = passageAccount;
    }
}
