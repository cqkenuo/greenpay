package com.esiran.greenpay.pay.entity;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String productName;

    private Boolean productType;
    private String payTypeCode;
    private String payTypeName;

    private Integer interfaceMode;
    private Integer defaultPassageId;
    private Integer defaultPassageAccId;

    private Boolean status;
}
