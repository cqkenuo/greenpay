package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.entity.TypeDTO;
import com.esiran.greenpay.pay.entity.TypeInputDTO;
import com.esiran.greenpay.pay.mapper.TypeMapper;
import com.esiran.greenpay.pay.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付类型 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public Type findTypeByCode(String code) {
        LambdaQueryWrapper<Type> typeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        typeLambdaQueryWrapper.eq(Type::getTypeCode,code);
        return getOne(typeLambdaQueryWrapper);
    }

    @Override
    public TypeDTO getTypeByCode(String code) {
        Type type = findTypeByCode(code);
        if (type == null) return null;
        return modelMapper.map(type,TypeDTO.class);
    }

    @Override
    public void saveType(TypeInputDTO dto) throws PostResourceException {
        Type type = modelMapper.map(dto,Type.class);
        Type query = findTypeByCode(type.getTypeCode());
        if (query != null) throw new PostResourceException("支付类型编码已存在");
        save(type);
    }

    @Override
    public void updateType(TypeInputDTO dto) throws ResourceNotFoundException {
        Type type = findTypeByCode(dto.getTypeCode());
        Type target = modelMapper.map(dto,Type.class);
        if (type == null) throw new ResourceNotFoundException();
        target.setId(type.getId());
        updateById(target);
    }
}
