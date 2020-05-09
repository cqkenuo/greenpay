package com.esiran.greenpay.settle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.common.util.IdWorker;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.SettleAccountDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.merchant.service.IPayAccountService;
import com.esiran.greenpay.merchant.service.ISettleAccountService;
import com.esiran.greenpay.settle.entity.SettleOrder;
import com.esiran.greenpay.settle.entity.SettleOrderDTO;
import com.esiran.greenpay.settle.entity.SettleOrderInputDTO;
import com.esiran.greenpay.settle.mapper.SettleOrderMapper;
import com.esiran.greenpay.settle.service.ISettleOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final IPayAccountService payAccountService;
    private final  IMerchantService iMerchantService;
    private final ISettleAccountService iSettleAccountService;
    private final IdWorker idWorker;
    public SettleOrderServiceImpl(IPayAccountService payAccountService, IMerchantService iMerchantService, ISettleAccountService iSettleAccountService, IdWorker idWorker) {
        this.payAccountService = payAccountService;
        this.iMerchantService = iMerchantService;
        this.iSettleAccountService = iSettleAccountService;
        this.idWorker = idWorker;
    }


    public static SettleOrderDTO convertOrderEntity(SettleOrder order){
        if (order == null) return null;
        SettleOrderDTO dto = modelMapper.map(order,SettleOrderDTO.class);
        dto.setAmountDisplay(NumberUtil.amountFen2Yuan(order.getAmount()));
        dto.setFeeDisplay(NumberUtil.amountFen2Yuan(order.getFee()));
        dto.setSettleAmountDisplay(NumberUtil.amountFen2Yuan(order.getSettleAmount()));
        String status = order.getStatus() == 1 ? "待审核"
                : order.getStatus() == 2 ? "待处理"
                : order.getStatus() == 3 ? "处理中"
                : order.getStatus() == 4 ? "已结算"
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderNo, Integer status) throws PostResourceException {
        SettleOrderDTO orderDTO = getByOrderNo(orderNo);
        if (orderDTO == null) {
            throw new PostResourceException("订单不存在");
        }

        //驳回逻辑
        SettleOrder settleOrder = getById(orderDTO.getId());
        //状态（1：待审核，2：待处理，3：处理中，4：已结算，-1：已驳回，-2：结算失败）
        int old_stat = settleOrder.getStatus();

        //其它状态修改
        if (old_stat == -1) {
            throw new PostResourceException("订单状态：已驳回");
        }else
        if (old_stat == -2) {
            throw new PostResourceException("订单状态：结算失败");
        }else
        if (old_stat == 4) {
            throw new PostResourceException("订单状态：已结算");
        }

        if (status == 1) {
            throw new PostResourceException("订单状态：待审核");
        }

        if (status == -1) {
            int result = payAccountService.updateBalance(orderDTO.getMchId(), -orderDTO.getAmount(), orderDTO.getAmount());
            if (result == 0) {
                throw new PostResourceException("账户余额不正确");
            }
        }

        //结算逻辑:成功后不能标识，待处理状态才能标识已经结算


        settleOrder.setStatus(status);
        updateById(settleOrder);

    }

    /**
     * 提交订单
     * @param inputDTO
     * @throws PostResourceException
     * @throws ResourceNotFoundException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postOrder(SettleOrderInputDTO inputDTO) throws PostResourceException, ResourceNotFoundException {
        Merchant merchant = iMerchantService.getById(inputDTO.getMchId());

        if (merchant == null) {
            throw new PostResourceException("商户不存在");
        }
        //商户状态 判断
        if (!merchant.getStatus()) {
            throw new PostResourceException("商户状态为禁用");
        }
        int result = payAccountService.updateBalance(inputDTO.getMchId(),inputDTO.getAmount(),-inputDTO.getAmount());
        if (result==0){
            // 账户yue不足
            throw new PostResourceException("账户余额不足");
        }

        //计算结算金额
        //获取对应商户的费率
        Integer free = 0;
        Integer SettleAmount = 0;
        SettleAccountDTO settleAccountDTO = iSettleAccountService.findByMerchantId(merchant.getId());
        if (settleAccountDTO.getStatus()) {
            throw new PostResourceException("结算状态已关闭");
        }
        //1)百分比
        //2)固定
        //3)百分+固定
        BigDecimal amount = new BigDecimal(inputDTO.getAmount());
        BigDecimal freerate ;
        BigDecimal  feeAmount;
        switch (settleAccountDTO.getSettleFeeType()) {
            case 1:
                freerate = amount.multiply(settleAccountDTO.getSettleFeeRate());
                free = freerate.intValue();
                break;
            case 2:
                feeAmount = new BigDecimal(settleAccountDTO.getSettleFeeAmount());
                free = feeAmount.intValue()* 100;
                break;
            case 3:
                //百分比
                freerate = amount.multiply(settleAccountDTO.getSettleFeeRate());
                free = freerate.intValue();
                //固定
                Integer free2;
                feeAmount = new BigDecimal(settleAccountDTO.getSettleFeeAmount());
                free2 = feeAmount.intValue();

                free = free + free2;
                break;
            default:
                break;
        }

        //记录到订单中
        SettleAmount = inputDTO.getAmount()-free;

        SettleOrder settleOrder = modelMapper.map(inputDTO, SettleOrder.class);
        settleOrder.setOrderNo(String.valueOf(idWorker.nextId()));
        settleOrder.setOrderSn(EncryptUtil.baseTimelineCode());
        settleOrder.setSettleType(true);
        settleOrder.setStatus(1);
        settleOrder.setFee(free);
        settleOrder.setSettleAmount(SettleAmount);
        settleOrder.setCreatedAt(LocalDateTime.now());
        settleOrder.setUpdatedAt(settleOrder.getCreatedAt());
        save(settleOrder);

    }
}
