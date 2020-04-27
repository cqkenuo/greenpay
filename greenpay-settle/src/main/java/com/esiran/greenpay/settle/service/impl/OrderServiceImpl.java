package com.esiran.greenpay.settle.service.impl;

import com.esiran.greenpay.settle.entity.Order;
import com.esiran.greenpay.settle.mapper.OrderMapper;
import com.esiran.greenpay.settle.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户结算订单 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
