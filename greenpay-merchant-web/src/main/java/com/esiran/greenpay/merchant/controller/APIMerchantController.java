package com.esiran.greenpay.merchant.controller;


import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantUpdateDTO;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/merchant/api/v1")
public class APIMerchantController extends CURDBaseController {

    private final IApiConfigService apiConfigService;
    private final IMerchantService merchantService;

    public APIMerchantController(IApiConfigService apiConfigService, IMerchantService merchantService) {
        this.apiConfigService = apiConfigService;
        this.merchantService = merchantService;

    }

    @PostMapping("/update")
    public void updateMerchant(@Validated MerchantUpdateDTO merchantUpdateDTO){
        Merchant merchant = theUser();
        BeanUtils.copyProperties(merchantUpdateDTO,merchant);
        merchant.setUpdatedAt(LocalDateTime.now());
        merchantService.updateById(merchant);
    }
    @PostMapping("/updatepassword")
    @PageViewHandleError
    public void updatePassword(@RequestBody
                                    @NotBlank(message = "旧密码不能为空") String oldPassword,
                                    @NotBlank(message = "新密码不能为空") String password,
                               Map<String,String> map ) throws Exception {
        Merchant merchant = theUser();
        if (!merchant.getPassword().equals(oldPassword)) {
            throw new PostResourceException("旧密码错误为空");
        }
        merchant.setPassword(password);
        merchantService.updateById(merchant);
    }

    @ApiOperation("上传商户公钥")
    @PostMapping(value = "/mch_pub_key",produces = "text/plain")
    public void publicKey( @RequestBody String content) throws Exception {
        Merchant merchant = theUser();
        String publicKey = RSAUtil.resolvePublicKey(content);
        LambdaUpdateWrapper<ApiConfig> updateWrapper = new UpdateWrapper<ApiConfig>().lambda();
        updateWrapper.set(ApiConfig::getMchPubKey, publicKey)
                .eq(ApiConfig::getMchId, merchant.getId());
        apiConfigService.update(updateWrapper);

    }
}
