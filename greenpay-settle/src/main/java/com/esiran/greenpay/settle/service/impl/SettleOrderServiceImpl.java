package com.esiran.greenpay.settle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.settle.entity.SettleOrder;
import com.esiran.greenpay.settle.entity.SettleOrderDTO;
import com.esiran.greenpay.settle.mapper.SettleOrderMapper;
import com.esiran.greenpay.settle.service.ISettleOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 商户结算订单 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
@Service
public class SettleOrderServiceImpl extends ServiceImpl<SettleOrderMapper, SettleOrder> implements ISettleOrderService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static SettleOrderDTO convertOrderEntity(SettleOrder order){
        if (order == null) return null;
        SettleOrderDTO dto = modelMapper.map(order,SettleOrderDTO.class);
        dto.setAmountDisplay(NumberUtil.amountFen2Yuan(order.getAmount()));
        dto.setFeeDisplay(NumberUtil.amountFen2Yuan(order.getFee()));
        dto.setSettleAmountDisplay(NumberUtil.amountFen2Yuan(order.getSettleAmount()));
        String status = order.getStatus() == 1 ? "待审核"
                : order.getStatus() == 2 ? "处理中"
                : order.getStatus() == 3 ? "已结算"
                : order.getStatus() == -1 ? "已驳回"
                : order.getStatus() == -2 ? "结算失败"
                : "未知";
        dto.setStatusDisplay(status);
        dto.setCreatedAtDisplay(dtf.format(order.getCreatedAt()));
        dto.setUpdatedAtDisplay(dtf.format(order.getUpdatedAt()));
        if (dto.getSettledAt() != null)
            dto.setSettledAtDisplay(dtf.format(dto.getSettledAt()));
        return dto;
    }
    @Override
    public IPage<SettleOrderDTO> selectPage(IPage<SettleOrderDTO> page, SettleOrderDTO orderDTO) {
        SettleOrder settleOrder = modelMapper.map(orderDTO,SettleOrder.class);
        LambdaQueryWrapper<SettleOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SettleOrder::getStatus,orderDTO.getStatus());
        queryWrapper.setEntity(settleOrder);
        IPage<SettleOrder> orderPage = this.page(new Page<>(page.getCurrent(),page.getSize()),queryWrapper);
        return orderPage.convert(SettleOrderServiceImpl::convertOrderEntity);
    }

    @Override
    public IPage<SettleOrderDTO> selectPageByAudit(IPage<SettleOrderDTO> page) {
        LambdaQueryWrapper<SettleOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(SettleOrder::getStatus,-1);
        queryWrapper.lt(SettleOrder::getStatus,2);
        IPage<SettleOrder> orderPage = this.page(new Page<>(page.getCurrent(),page.getSize()),queryWrapper);
        return orderPage.convert(SettleOrderServiceImpl::convertOrderEntity);
    }

    @Override
    public IPage<SettleOrderDTO> selectPageByPayable(IPage<SettleOrderDTO> page) {
        LambdaQueryWrapper<SettleOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(SettleOrder::getStatus,2);
        IPage<SettleOrder> orderPage = this.page(new Page<>(page.getCurrent(),page.getSize()),queryWrapper);
        return orderPage.convert(SettleOrderServiceImpl::convertOrderEntity);
    }

    @Override
    public SettleOrderDTO getByOrderNo(String orderNo) {
        LambdaQueryWrapper<SettleOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SettleOrder::getOrderNo, orderNo);
        SettleOrder order = this.getOne(lambdaQueryWrapper);
        if (order == null) return null;
        return SettleOrderServiceImpl.convertOrderEntity(order);
    }
}
