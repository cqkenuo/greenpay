package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.merchant.entity.PrepaidAccount;
import com.esiran.greenpay.merchant.entity.PrepaidAccountDTO;
import com.esiran.greenpay.merchant.entity.SettleAccount;
import com.esiran.greenpay.merchant.entity.SettleAccountDTO;
import com.esiran.greenpay.merchant.mapper.SettleAccountMapper;
import com.esiran.greenpay.merchant.service.ISettleAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户结算账户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-21
 */
@Service
public class SettleAccountServiceImpl extends ServiceImpl<SettleAccountMapper, SettleAccount> implements ISettleAccountService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public SettleAccountDTO findByMerchantId(Integer mchId) {
        LambdaQueryWrapper<SettleAccount> settleAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        settleAccountLambdaQueryWrapper.eq(SettleAccount::getMerchantId,mchId);
        SettleAccount settleAccount = this.getOne(settleAccountLambdaQueryWrapper);
        SettleAccountDTO dto = modelMapper.map(settleAccount,SettleAccountDTO.class);
        dto.setSettleFeeAmountDisplay(String.format("%.2f",(dto.getSettleFeeAmount()/100.00f)));
        dto.setSettleFeeRateDisplay(String.format("%.4f",(dto.getSettleFeeRate().floatValue()/100.00f)));
        return modelMapper.map(settleAccount,SettleAccountDTO.class);
    }
}
