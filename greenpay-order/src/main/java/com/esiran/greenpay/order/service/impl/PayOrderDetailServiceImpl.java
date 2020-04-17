package com.esiran.greenpay.order.service.impl;

import com.esiran.greenpay.order.entity.PayOrderDetail;
import com.esiran.greenpay.order.mapper.PayOrderDetailMapper;
import com.esiran.greenpay.order.service.IPayOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-16
 */
@Service
public class PayOrderDetailServiceImpl extends ServiceImpl<PayOrderDetailMapper, PayOrderDetail> implements IPayOrderDetailService {

}
