package com.esiran.greenpay.merchant.controller;


import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.*;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashMap;
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
    public Map updateMerchant(@Validated MerMerchantUpdateDTO merchantUpdateDTO){
        Merchant merchant = theUser();
        Map m = new HashMap();
        BeanUtils.copyProperties(merchantUpdateDTO,merchant);
        merchant.setUpdatedAt(LocalDateTime.now());
        try {
            merchantService.updateById(merchant);
        } catch (Exception e) {
            e.printStackTrace();
            m.put("code",0);
            m.put("msg","修改失败");
            return m;
        }
        m.put("code",1);
        m.put("msg","修改成功");
        return m;
    }
    @PostMapping("/updatepassword")
    public Map updatePassword(@Validated MerchantSercurityDTO merchantSercurityDTO) throws Exception {
        Merchant merchant = theUser();
        Map m = new HashMap();
        String oldPasswordMd5 = EncryptUtil.md5(merchantSercurityDTO.getOldPassword());
        if (!merchant.getPassword().equals(oldPasswordMd5)) {
            m.put("code",0);
            m.put("msg","旧密码错误");
            return m;
        }
        merchant.setPassword(EncryptUtil.md5(merchantSercurityDTO.getPassword()));
        merchantService.updateById(merchant);
        m.put("code",1);
        m.put("msg","修改成功");
        return m;
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
