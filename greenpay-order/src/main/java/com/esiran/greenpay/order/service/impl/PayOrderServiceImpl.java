package com.esiran.greenpay.order.service.impl;

import com.esiran.greenpay.order.entity.PayOrder;
import com.esiran.greenpay.order.mapper.PayOrderMapper;
import com.esiran.greenpay.order.service.IPayOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-16
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

}
