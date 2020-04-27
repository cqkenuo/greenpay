package com.esiran.greenpay.settle.service.impl;

import com.esiran.greenpay.settle.entity.Config;
import com.esiran.greenpay.settle.mapper.ConfigMapper;
import com.esiran.greenpay.settle.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 结算设置 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-27
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
