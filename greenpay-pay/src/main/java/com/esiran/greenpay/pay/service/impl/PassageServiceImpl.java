package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.mapper.PassageMapper;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.IPassageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付通道 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class PassageServiceImpl extends ServiceImpl<PassageMapper, Passage> implements IPassageService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final ITypeService typeService;
    private final IInterfaceService interfaceService;
    public PassageServiceImpl(ITypeService typeService, IInterfaceService interfaceService) {
        this.typeService = typeService;
        this.interfaceService = interfaceService;
    }

    @Override
    public List<Passage> listByPayTypeCode(String payTypeCode) {
        LambdaQueryWrapper<Passage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Passage::getPayTypeCode,payTypeCode);
        return list(queryWrapper);
    }

    @Override
    public IPage<PassageDTO> selectPage(IPage<PassageDTO> page, PassageDTO passageDTO) {
        IPage<Passage> interfaceDTOPage = this.page(new Page<>(page.getCurrent(),page.getSize()));
        return interfaceDTOPage.convert(item->modelMapper.map(item,PassageDTO.class));
    }


    @Override
    public int add(PassageInputDTO passageInputDTO) throws PostResourceException {
        Passage target = modelMapper.map(passageInputDTO,Passage.class);
        TypeDTO typeDTO = typeService.getTypeByCode(target.getPayTypeCode());
        checkupPost(target, typeDTO);
        save(target);
        return target.getId();
    }

    @Override
    public boolean updateById(Integer id, PassageInputDTO passageInputDTO) throws PostResourceException, ResourceNotFoundException {
        Passage src = this.getById(id);
        if (src == null) throw new ResourceNotFoundException("支付通道不存在");
        Passage passage = modelMapper.map(passageInputDTO,Passage.class);
        passage.setId(id);
        TypeDTO typeDTO = typeService.getTypeByCode(passage.getPayTypeCode());
        checkupPost(passage, typeDTO);
        return updateById(passage);
    }

    private void checkupPost(Passage passage, TypeDTO typeDTO) throws PostResourceException {
        if (typeDTO == null) throw new PostResourceException("支付类型不存在");
        Interface ins = interfaceService.getByCode(passage.getInterfaceCode());
        if (ins == null) throw new PostResourceException("支付接口不存在");
        if (!ins.getPayTypeCode().equals(typeDTO.getTypeCode())){
            throw new PostResourceException("支付类型与支付接口不匹配");
        }
    }
}
