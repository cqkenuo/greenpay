package com.esiran.greenpay.merchant.service;

import com.esiran.greenpay.merchant.entity.MerchantAgentPayPassage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.merchant.entity.MerchantAgentPayPassageDTO;
import com.esiran.greenpay.merchant.entity.MerchantAgentPayPassageInputDTO;

/**
 * <p>
 * 商户代付通道 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-05-06
 */
public interface IMerchantAgentPayPassageService extends IService<MerchantAgentPayPassage> {
    MerchantAgentPayPassage getOneByMchId(Integer mchId, Integer passageId);
    MerchantAgentPayPassageDTO getOneDTOByMchId(Integer mchId, Integer passageId);
    void updateByInput(MerchantAgentPayPassageInputDTO inputDTO);
}
