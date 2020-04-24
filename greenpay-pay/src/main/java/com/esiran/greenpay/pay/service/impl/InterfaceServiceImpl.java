package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.Interface;
import com.esiran.greenpay.pay.entity.InterfaceDTO;
import com.esiran.greenpay.pay.entity.InterfaceInputDTO;
import com.esiran.greenpay.pay.entity.TypeDTO;
import com.esiran.greenpay.pay.mapper.InterfaceMapper;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付接口 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class InterfaceServiceImpl extends ServiceImpl<InterfaceMapper, Interface> implements IInterfaceService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final ITypeService typeService;

    public InterfaceServiceImpl(ITypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public Interface getByCode(String code) {
        LambdaQueryWrapper<Interface> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Interface::getInterfaceCode,code);
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public List<Interface> listByPayTypeCode(String payTypeCode) {
        LambdaQueryWrapper<Interface> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Interface::getPayTypeCode,payTypeCode);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public InterfaceDTO getDTOByCode(String code) {
        Interface target = getByCode(code);
        if (target == null) return null;
        return modelMapper.map(target,InterfaceDTO.class);
    }

    @Override
    public IPage<InterfaceDTO> selectPage(IPage<InterfaceDTO> page, InterfaceDTO interfaceDTO) {
        IPage<Interface> interfaceDTOPage = this.page(new Page<>(page.getCurrent(),page.getSize()));
        return interfaceDTOPage.convert(item->modelMapper.map(item,InterfaceDTO.class));
    }

    @Override
    public int addInterface(InterfaceInputDTO interfaceInputDTO) throws PostResourceException {
        Interface target = modelMapper.map(interfaceInputDTO,Interface.class);
        TypeDTO typeDTO = typeService.getTypeByCode(target.getPayTypeCode());
        if (typeDTO == null) throw new PostResourceException("接口类型不存在");
        Interface src = this.getByCode(target.getInterfaceCode());
        if (src != null) throw new PostResourceException("接口编码已经存在");
        save(target);
        return target.getId();
    }

    @Override
    public boolean updateById(Integer id, InterfaceInputDTO interfaceInputDTO) throws PostResourceException, ResourceNotFoundException {
        Interface src = this.getById(id);
        if (src == null) throw new ResourceNotFoundException("支付接口不存在");
        Interface target = modelMapper.map(interfaceInputDTO,Interface.class);
        target.setId(id);
        TypeDTO typeDTO = typeService.getTypeByCode(target.getPayTypeCode());
        if (typeDTO == null) throw new PostResourceException("接口类型不存在");
        if (!target.getInterfaceCode().equals(src.getInterfaceCode())){
            if(getByCode(target.getInterfaceCode()) != null)
                throw new PostResourceException("接口编码已经存在");
        }
        return updateById(target);
    }
}
