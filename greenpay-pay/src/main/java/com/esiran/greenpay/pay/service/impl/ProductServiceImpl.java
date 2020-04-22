package com.esiran.greenpay.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.entity.ProductDTO;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.mapper.ProductMapper;
import com.esiran.greenpay.pay.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 支付产品 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    private final ModelMapper modelMapper = new ModelMapper();
    private final ITypeService typeService;

    public ProductServiceImpl(ITypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public List<ProductDTO> findAllProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO,Product.class);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(product);
        List<Product> productList = this.list(queryWrapper);
        return productList.stream()
                .map(item->modelMapper.map(item,ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findAllProductByPayTypeCode(String payTypeCode) {
        Type type = typeService.findTypeByCode(payTypeCode);
        if (type == null) return null;
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getPayTypeCode,type.getTypeCode());
        List<Product> productList = this.list(queryWrapper);
        List<ProductDTO> list = productList.stream()
                .map(item->modelMapper.map(item,ProductDTO.class))
                .collect(Collectors.toList());
        return list.stream()
                .peek(item-> item.setPayTypeName(type.getTypeName()))
                .collect(Collectors.toList());
    }
}
