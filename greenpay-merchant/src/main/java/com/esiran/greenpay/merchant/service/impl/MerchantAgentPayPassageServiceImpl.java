package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.merchant.entity.MerchantAgentPayPassage;
import com.esiran.greenpay.merchant.entity.MerchantAgentPayPassageDTO;
import com.esiran.greenpay.merchant.mapper.MerchantAgentPayPassageMapper;
import com.esiran.greenpay.merchant.service.IMerchantAgentPayPassageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户代付通道 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-05-06
 */
@Service
public class MerchantAgentPayPassageServiceImpl extends ServiceImpl<MerchantAgentPayPassageMapper, MerchantAgentPayPassage> implements IMerchantAgentPayPassageService {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public MerchantAgentPayPassage getOneByMchId(Integer mchId, Integer passageId) {
        LambdaQueryWrapper<MerchantAgentPayPassage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MerchantAgentPayPassage::getMerchantId,mchId)
                .eq(MerchantAgentPayPassage::getPassageId,passageId);
        return getOne(queryWrapper);
    }

    @Override
    public MerchantAgentPayPassageDTO getOneDTOByMchId(Integer mchId, Integer passageId) {
        MerchantAgentPayPassage mapp = getOneByMchId(mchId,passageId);
        if (mapp == null) return null;
        MerchantAgentPayPassageDTO dto = modelMapper.map(mapp,MerchantAgentPayPassageDTO.class);
        dto.setFeeAmountDisplay(NumberUtil.amountFen2Yuan(mapp.getFeeAmount()));
        dto.setFeeRateDisplay(NumberUtil.twoDecimals(mapp.getFeeRate()));
        return dto;
    }
}
