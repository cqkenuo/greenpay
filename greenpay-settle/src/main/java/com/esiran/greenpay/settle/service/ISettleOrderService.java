package com.esiran.greenpay.settle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.settle.entity.SettleOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.settle.entity.SettleOrderDTO;
import com.esiran.greenpay.settle.entity.SettleOrderInputDTO;

/**
 * <p>
 * 商户结算订单 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
public interface ISettleOrderService extends IService<SettleOrder> {
    IPage<SettleOrderDTO> selectPage(IPage<SettleOrderDTO> page, SettleOrderDTO orderDTO);
    IPage<SettleOrderDTO> selectPageByAudit(IPage<SettleOrderDTO> page);
    IPage<SettleOrderDTO> selectPageByPayable(IPage<SettleOrderDTO> page);
    SettleOrderDTO getByOrderNo(String orderNo);
    void updateOrderStatus(String orderNo, Integer status);
    void postOrder(SettleOrderInputDTO inputDTO) throws PostResourceException, ResourceNotFoundException;
}