package com.esiran.greenpay.agentpay.service.impl;

import com.esiran.greenpay.agentpay.entity.Order;
import com.esiran.greenpay.agentpay.mapper.OrderMapper;
import com.esiran.greenpay.agentpay.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代付订单表 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
