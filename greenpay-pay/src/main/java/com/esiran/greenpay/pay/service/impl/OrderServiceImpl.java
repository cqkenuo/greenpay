package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.sign.Md5SignType;
import com.esiran.greenpay.common.sign.SignType;
import com.esiran.greenpay.common.sign.SignVerify;
import com.esiran.greenpay.common.util.MapUtil;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDTO;
import com.esiran.greenpay.pay.entity.OrderDetail;
import com.esiran.greenpay.pay.mapper.OrderMapper;
import com.esiran.greenpay.pay.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.orderByDesc(Order::getCreatedAt);
        IPage<Order> orderPage = this.page(new Page<>(page.getCurrent(),page.getSize()),orderQueryWrapper);
        return orderPage.convert(OrderDTO::convertOrderEntity);
    }


    @Override
    public OrderDTO getByOrderNo(String orderNo) {
        Order order = this.getOneByOrderNo(orderNo);
        if (order == null) return null;
        return OrderDTO.convertOrderEntity(order);
    }

    @Override
    public Order getOneByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getOrderNo, orderNo);
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public List<Order> getByDay(Integer mchId) {
        return this.baseMapper.getByDay(mchId);
    }

    @Override
    public void updateOrderStatus(String orderNo, Integer status) {
        if (orderNo == null || status == null) return;
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.set(Order::getStatus, status)
                .eq(Order::getOrderNo,orderNo);
        update(orderUpdateWrapper);
    }

}
