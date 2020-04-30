package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.mapper.PassageAccountMapper;
import com.esiran.greenpay.pay.service.IPassageAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.service.IPassageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付通道账户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class PassageAccountServiceImpl extends ServiceImpl<PassageAccountMapper, PassageAccount> implements IPassageAccountService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final IPassageService passageService;

    public PassageAccountServiceImpl(IPassageService passageService) {
        this.passageService = passageService;
    }

    @Override
    public List<PassageAccount> listAvailable(Integer passageId) {
        LambdaQueryWrapper<PassageAccount> paQueryWrapper
                = new LambdaQueryWrapper<>();
        paQueryWrapper.eq(PassageAccount::getPassageId,passageId)
                .eq(PassageAccount::getStatus,1)
                .ge(PassageAccount::getWeight,0)
                .orderByDesc(PassageAccount::getWeight);
        return list(paQueryWrapper);
    }

    @Override
    public List<PassageAccount> listByPayTypeCode(String payTypeCode) {
        LambdaQueryWrapper<PassageAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PassageAccount::getPayTypeCode,payTypeCode);
        return this.list(queryWrapper);
    }

    @Override
    public IPage<PassageAccountDTO> selectPage(IPage<PassageAccountDTO> page, PassageAccountDTO passageAccountDTO) {
        PassageAccount passageAccount = modelMapper.map(passageAccountDTO,PassageAccount.class);
        LambdaQueryWrapper<PassageAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.setEntity(passageAccount);
        IPage<PassageAccount> passageAccountPage = this.page(new Page<>(page.getCurrent(),page.getSize()),lambdaQueryWrapper);
        return passageAccountPage.convert(item->{
            PassageAccountDTO data = modelMapper.map(item, PassageAccountDTO.class);
            Passage passage = passageService.getById(item.getPassageId());
            data.setPassageName(passage.getPassageName());
            return data;
        });
    }

    @Override
    public int add(PassageAccountInputDTO passageAccountInputDTO) throws PostResourceException {
        PassageAccount target = modelMapper.map(passageAccountInputDTO,PassageAccount.class);
        Passage passage = passageService.getById(target.getPassageId());
        if (passage == null) throw new PostResourceException("支付通道不存在");
        target.setPayTypeCode(passage.getPayTypeCode());
        save(target);
        return target.getId();
    }


    @Override
    public boolean updateById(Integer id, PassageAccountInputDTO passageAccountInputDTO) throws PostResourceException, ResourceNotFoundException {
        PassageAccount src = this.getById(id);
        if (src == null) throw new ResourceNotFoundException("支付通道不存在");
        PassageAccount passageAccount = modelMapper.map(passageAccountInputDTO,PassageAccount.class);
        passageAccount.setId(id);
        Passage passage = passageService.getById(passageAccount.getPassageId());
        if (passage == null) throw new PostResourceException("支付通道不存在");
        return updateById(passageAccount);
    }
}
