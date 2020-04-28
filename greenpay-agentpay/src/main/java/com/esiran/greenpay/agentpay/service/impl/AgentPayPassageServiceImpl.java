package com.esiran.greenpay.agentpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.agentpay.entity.AgentPayPassage;
import com.esiran.greenpay.agentpay.entity.AgentPayPassageDTO;
import com.esiran.greenpay.agentpay.entity.AgentPayPassageInputDTO;
import com.esiran.greenpay.agentpay.mapper.AgentPayPassageMapper;
import com.esiran.greenpay.agentpay.service.IAgentPayPassageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.Interface;
import com.esiran.greenpay.pay.entity.TypeDTO;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代付通道 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
@Service
public class AgentPayPassageServiceImpl extends ServiceImpl<AgentPayPassageMapper, AgentPayPassage> implements IAgentPayPassageService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final ITypeService typeService;
    private final IInterfaceService interfaceService;
    public AgentPayPassageServiceImpl(ITypeService typeService, IInterfaceService interfaceService) {
        this.typeService = typeService;
        this.interfaceService = interfaceService;
    }

    @Override
    public List<AgentPayPassage> listByPayTypeCode(String payTypeCode) {
        LambdaQueryWrapper<AgentPayPassage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AgentPayPassage::getPayTypeCode,payTypeCode);
        return list(queryWrapper);
    }

    @Override
    public IPage<AgentPayPassageDTO> selectPage(IPage<AgentPayPassageDTO> page, AgentPayPassageDTO passageDTO) {
        IPage<AgentPayPassage> interfaceDTOPage = this.page(new Page<>(page.getCurrent(),page.getSize()));
        return interfaceDTOPage.convert(item->modelMapper.map(item, AgentPayPassageDTO.class));
    }


    @Override
    public int add(AgentPayPassageInputDTO passageInputDTO) throws PostResourceException {
        AgentPayPassage target = modelMapper.map(passageInputDTO, AgentPayPassage.class);
        TypeDTO typeDTO = typeService.getTypeByCode(target.getPayTypeCode());
        checkupPost(target, typeDTO);
        save(target);
        return target.getId();
    }

    @Override
    public boolean updateById(Integer id, AgentPayPassageInputDTO passageInputDTO) throws PostResourceException, ResourceNotFoundException {
        AgentPayPassage src = this.getById(id);
        if (src == null) throw new ResourceNotFoundException("支付通道不存在");
        AgentPayPassage passage = modelMapper.map(passageInputDTO, AgentPayPassage.class);
        passage.setId(id);
        TypeDTO typeDTO = typeService.getTypeByCode(passage.getPayTypeCode());
        checkupPost(passage, typeDTO);
        return updateById(passage);
    }

    private void checkupPost(AgentPayPassage passage, TypeDTO typeDTO) throws PostResourceException {
        if (typeDTO == null) throw new PostResourceException("支付类型不存在");
        Interface ins = interfaceService.getByCode(passage.getInterfaceCode());
        if (ins == null) throw new PostResourceException("支付接口不存在");
        if (!ins.getPayTypeCode().equals(typeDTO.getTypeCode())){
            throw new PostResourceException("支付类型与支付接口不匹配");
        }
    }
}
