package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.merchant.entity.MerchantProduct;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.mapper.MerchantProductMapper;
import com.esiran.greenpay.merchant.service.IMerchantProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户产品 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class MerchantProductServiceImpl extends ServiceImpl<MerchantProductMapper, MerchantProduct> implements IMerchantProductService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public MerchantProductDTO getByProductId(Integer mchId, Integer productId) {
        LambdaQueryWrapper<MerchantProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MerchantProduct::getMerchantId, mchId)
                .eq(MerchantProduct::getProductId, productId);
        MerchantProduct mp = this.getOne(queryWrapper);
        if (mp == null) return null;
        return modelMapper.map(mp,MerchantProductDTO.class);
    }
}
