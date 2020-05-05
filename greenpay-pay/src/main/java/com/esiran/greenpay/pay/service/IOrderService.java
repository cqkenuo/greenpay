package com.esiran.greenpay.pay.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.esiran.greenpay.pay.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.pay.entity.OrderDTO;

/**
 * <p>
 * 支付订单 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-14
 */
public interface IOrderService extends IService<Order> {
        IPage<OrderDTO> selectPage(IPage<OrderDTO> page,OrderDTO orderDTO);
        OrderDTO getByOrderNo(String orderNo);
        Order getOneByOrderNo(String orderNo);
        void updateOrderStatus(String orderNo, Integer status);
}
