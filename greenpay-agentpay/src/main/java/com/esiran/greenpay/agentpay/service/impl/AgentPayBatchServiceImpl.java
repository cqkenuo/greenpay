package com.esiran.greenpay.agentpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.agentpay.entity.AgentPayBatch;
import com.esiran.greenpay.agentpay.entity.AgentPayBatchDTO;
import com.esiran.greenpay.agentpay.mapper.AgentPayBatchMapper;
import com.esiran.greenpay.agentpay.service.IAgentPayBatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代付批次 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-05-06
 */
@Service
public class AgentPayBatchServiceImpl extends ServiceImpl<AgentPayBatchMapper, AgentPayBatch> implements IAgentPayBatchService {

    @Override
    public IPage<AgentPayBatchDTO> selectPage(Page<AgentPayBatchDTO> page, AgentPayBatchDTO batchDTO) {
        LambdaQueryWrapper<AgentPayBatch> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(AgentPayBatch::getCreatedAt);
        Page<AgentPayBatch> batchPage = this.page(new Page<>(page.getCurrent(), page.getSize()), wrapper);
        return batchPage.convert(AgentPayBatchDTO::convertOrderEntity);
    }
}
