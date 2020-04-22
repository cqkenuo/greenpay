package com.esiran.greenpay.admin.controller.merchant;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.*;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/merchants")
@Api(tags = "商户管理")
public class APIAdminMerchantController {
    private final IMerchantService merchantService;
    private final IApiConfigService apiConfigService;
    public APIAdminMerchantController(IMerchantService merchantService, IApiConfigService apiConfigService) {
        this.merchantService = merchantService;
        this.apiConfigService = apiConfigService;
    }

    @ApiOperation("查询所有商户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="current",value="当前页码",defaultValue = "1"),
            @ApiImplicitParam(name="size",value="每页个数",defaultValue = "10"),
    })
    @GetMapping
    public IPage<MerchantDTO> list(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size){
        return merchantService.selectMerchantByPage(new Page<>(current,size));
    }


    @ApiOperation("查询指定商户的支付产品列表")
    @ApiImplicitParam(name="mchId", value="商户ID", dataType="int", required=true, paramType="path")
    @GetMapping("/{mchId}/products")
    public List<MerchantProductDTO> product(@PathVariable Integer mchId) throws APIException {
        return merchantService.selectMchProductById(mchId);
    }


    @ApiOperation("修改商户的支付产品")
    @ApiImplicitParam(name="mchId", value="商户ID", dataType="int", required=true, paramType="path")
    @PostMapping(value = "/{mchId}/products")
    public void updateProduct(@PathVariable String mchId, @Valid MerchantProductInputDTO dto) throws Exception {
        merchantService.updateMerchantProduct(dto,Integer.valueOf(mchId));
    }

    @ApiOperation("修改商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mchId",value="商户ID",required = true),
    })
    @PostMapping(value = "/{mchId}")
    public void updateUserInfo(@PathVariable String mchId, @Valid MerchantUpdateDTO merchantDTO) throws Exception {
        merchantService.updateMerchantInfoById(merchantDTO,Integer.valueOf(mchId));
    }

    @ApiOperation("修改商户安全信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mchId",value="商户ID",required = true),
            @ApiImplicitParam(name="password",value="重置密码",required = true),
    })
    @PostMapping(value = "/{mchId}/security")
    public void updateSecurity(@PathVariable String mchId, @RequestParam String password) throws Exception {
        merchantService.updatePasswordById(password,Integer.valueOf(mchId));
    }

    @ApiOperation("商户结算信息设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mchId",value="商户ID",required = true),
    })
    @PostMapping(value = "/{mchId}/settle")
    public void updateSettleInfo(@PathVariable String mchId, SettleAccountDTO settleAccountDTO) throws Exception {
        merchantService.updateSettleById(settleAccountDTO,Integer.valueOf(mchId));
    }

    @ApiOperation("修改支付账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mchId",value="商户ID",required = true),
            @ApiImplicitParam(name="action",value="变更方式",required = true),
            @ApiImplicitParam(name="type",value="变更类型",required = true),
            @ApiImplicitParam(name="amount",value="变更金额",required = true),
    })
    @PostMapping(value = "/{mchId}/pay/account")
    public void payAccount(@PathVariable String mchId,
                           @RequestParam Integer action,
                           @RequestParam Integer type,
                           @RequestParam Integer amount) throws Exception {
        // TODO: 修改支付账户
        merchantService.updatePayAccountBalance(Integer.valueOf(mchId),new BigDecimal("0"),type,amount);

    }
    @ApiOperation("修改预付款账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mchId",value="商户ID",required = true),
            @ApiImplicitParam(name="action",value="变更方式",required = true),
            @ApiImplicitParam(name="type",value="变更类型",required = true),
            @ApiImplicitParam(name="amount",value="变更金额",required = true),
    })
    @PostMapping(value = "/{mchId}/prepaid/account")
    public void prepaidAccount(@PathVariable String mchId,
                           @RequestParam Integer action,
                           @RequestParam Integer type,
                           @RequestParam Integer amount) {
        // TODO: 修改预付款账户
    }


    @PostMapping(value = "/{mchId}/mch_pub_key",produces = "text/plain")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mchId",value="商户ID",required = true),
    })
    @ApiOperation("上传商户公钥")
    public void publicKey(@PathVariable String mchId, @RequestBody String content) throws Exception {
        LambdaQueryWrapper<ApiConfig> queryWrapper = new QueryWrapper<ApiConfig>()
                .lambda().eq(ApiConfig::getMchId,mchId);
        ApiConfig apiConfig = apiConfigService.getOne(queryWrapper);
        if (apiConfig == null) throw new Exception("商户不存在");
        String publicKey = RSAUtil.resolvePublicKey(content);
        LambdaUpdateWrapper<ApiConfig> updateWrapper = new UpdateWrapper<ApiConfig>().lambda();
        updateWrapper.set(ApiConfig::getMchPubKey,publicKey)
                .eq(ApiConfig::getMchId,mchId);
        apiConfigService.update(updateWrapper);
    }




}
