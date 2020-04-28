package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.merchant.entity.MerchantProductInputDTO;
import com.esiran.greenpay.merchant.entity.MerchantProductPassage;
import com.esiran.greenpay.merchant.mapper.MerchantProductPassageMapper;
import com.esiran.greenpay.merchant.service.IMerchantProductPassageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.entity.ProductInputDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户产品通道 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
@Service
public class MerchantProductPassageServiceImpl extends ServiceImpl<MerchantProductPassageMapper, MerchantProductPassage> implements IMerchantProductPassageService {

    @Override
    public void removeByProductId(Integer productId) {
        LambdaUpdateWrapper<MerchantProductPassage> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MerchantProductPassage::getProductId,productId);
        remove(updateWrapper);
    }

    @Override
    public List<MerchantProductPassage> listByProductId(Integer productId) {
        LambdaQueryWrapper<MerchantProductPassage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MerchantProductPassage::getProductId,productId);
        return list(queryWrapper);
    }

    @Override
    public boolean updateById(Integer id, MerchantProductInputDTO productInputDTO) throws PostResourceException, ResourceNotFoundException {
        return false;
    }

}
