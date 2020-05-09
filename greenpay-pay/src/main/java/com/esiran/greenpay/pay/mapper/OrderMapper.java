package com.esiran.greenpay.pay.mapper;

import com.esiran.greenpay.pay.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 支付订单 Mapper 接口
 * </p>
 *
 * @author Militch
 * @since 2020-04-14
 */
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from pay_order where to_days(created_at)=to_days(now()) AND mch_id = #{mchId}")
    List<Order> getByDay(Integer mchId);
}
