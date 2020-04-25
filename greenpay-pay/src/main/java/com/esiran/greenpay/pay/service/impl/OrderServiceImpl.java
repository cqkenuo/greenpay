package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDTO;
import com.esiran.greenpay.pay.mapper.OrderMapper;
import com.esiran.greenpay.pay.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    private final static ModelMapper modelMapper = new ModelMapper();
    @Override
    public IPage<OrderDTO> selectPage(IPage<OrderDTO> page, OrderDTO orderDTO) {
        IPage<Order> orderPage = this.page(new Page<>(page.getCurrent(),page.getSize()));
        return orderPage.convert(item->{
            OrderDTO dto = modelMapper.map(item,OrderDTO.class);
            dto.setAmountDisplay(NumberUtil.amountFen2Yuan(item.getAmount()));
            return dto;
        });
    }

    @Override
    public OrderDTO getByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getOrderNo, orderNo);
        Order order = this.getOne(lambdaQueryWrapper);
        if (order == null) return null;
        OrderDTO dto = modelMapper.map(order,OrderDTO.class);
        dto.setAmountDisplay(NumberUtil.amountFen2Yuan(order.getAmount()));
        return dto;
    }
}
