package com.esiran.greenpay.merchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.merchant.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商户 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IMerchantService extends IService<Merchant> {
    void updateMerchantInfoById(MerchantUpdateDTO dto, Integer id) throws Exception;
    void updateMerchantProduct(MerchantProductInputDTO dto, Integer id) throws Exception;
    void updatePasswordById(String password, Integer id) throws Exception;
    void updateSettleById(SettleAccountDTO settleAccountDTO, Integer id) throws Exception;
    void updatePayAccountBalance(Integer mchId, Double amount, Integer type, Integer action) throws Exception;
    MerchantDetailDTO findMerchantById(Integer id);
    void addMerchant(MerchantInputDTO merchantInputDTO) throws Exception;
    List<MerchantProductDTO> selectMchProductById(Integer mchId) throws APIException;
    MerchantProductDTO selectMchProductByIdAndPayTypeCode(Integer mchId,String payTypeCode) throws Exception;
    IPage<MerchantDTO> selectMerchantByPage(IPage<Void> page);
}
