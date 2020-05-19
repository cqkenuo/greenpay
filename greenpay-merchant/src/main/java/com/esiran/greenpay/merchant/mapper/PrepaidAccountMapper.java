package com.esiran.greenpay.merchant.mapper;

import com.esiran.greenpay.merchant.entity.PrepaidAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户预充值账户 Mapper 接口
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface PrepaidAccountMapper extends BaseMapper<PrepaidAccount> {

    int updateBalance(@Param("mchId") Integer mchId,
                      @Param("availAmount") Integer availAmount,
                      @Param("freezeAmount") Integer freezeAmount);
    Integer selectAvailBalance(@Param("mchId") Integer mchId,@Param("amount") Integer amount);
}
