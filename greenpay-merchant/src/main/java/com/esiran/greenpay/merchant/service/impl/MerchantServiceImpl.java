package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.*;
import com.esiran.greenpay.merchant.mapper.MerchantMapper;
import com.esiran.greenpay.merchant.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.entity.ProductDTO;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.service.IProductService;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {
    private final ITypeService iTypeService;
    private final IProductService productService;
    private final IMerchantProductService merchantProductService;
    private final IApiConfigService apiConfigService;
    private final IPayAccountService payAccountService;
    private final IPrepaidAccountService prepaidAccountService;
    private final ISettleAccountService settleAccountService;
    private static final ModelMapper modelMapper = new ModelMapper();
    public MerchantServiceImpl(
            ITypeService iTypeService,
            IProductService productService,
            IMerchantProductService merchantProductService,
            IApiConfigService apiConfigService,
            IPayAccountService payAccountService,
            IPrepaidAccountService prepaidAccountService, ISettleAccountService settleAccountService) {
        this.iTypeService = iTypeService;
        this.productService = productService;
        this.merchantProductService = merchantProductService;
        this.apiConfigService = apiConfigService;
        this.payAccountService = payAccountService;
        this.prepaidAccountService = prepaidAccountService;
        this.settleAccountService = settleAccountService;
    }

    @Override
    public void updateMerchantInfoById(MerchantUpdateDTO dto, Integer id) throws Exception {
        Merchant target = this.getById(id);
        if (target == null) throw new Exception("商户不存在");
        LambdaUpdateWrapper<Merchant> updateWrapper = new LambdaUpdateWrapper<>();
        if (dto == null) return;
        updateWrapper.set(Merchant::getName,dto.getName());
        updateWrapper.set(Merchant::getName,dto.getEmail());
        updateWrapper.set(Merchant::getStatus,dto.getStatus());
        updateWrapper.set(Merchant::getUpdatedAt, LocalDateTime.now());
        updateWrapper.eq(Merchant::getId,target.getId());
        update(updateWrapper);
    }

    @Override
    @Transactional
    public void updateMerchantProduct(MerchantProductInputDTO dto, Integer id) throws Exception {
//        MerchantProduct mp = modelMapper.map(dto,MerchantProduct.class);
//        mp.setMerchantId(id);
//        Type type = iTypeService.findTypeByCode(mp.getPayTypeCode());
//        if (type == null) throw new Exception("未知支付类型");
//        Product product = productService.getById(mp.getProductId());
//        if (product == null )  throw new Exception("支付产品不存在");
//        if (!product.getPayTypeCode().equals(type.getTypeCode()))
//            throw new Exception("支付产品不属于该支付类型");
//        LambdaUpdateWrapper<MerchantProduct> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        lambdaUpdateWrapper.eq(MerchantProduct::getPayTypeCode,mp.getPayTypeCode());
//        merchantProductService.remove(lambdaUpdateWrapper);
//        merchantProductService.save(mp);
    }

    @Override
    public void updatePasswordById(String password, Integer id) throws Exception {
        Merchant target = this.getById(id);
        if (target == null) throw new Exception("商户不存在");
        if (StringUtils.isEmpty(password)) throw new Exception("密码不能为空");
        LambdaUpdateWrapper<Merchant> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Merchant::getPassword, password);
        updateWrapper.set(Merchant::getUpdatedAt, LocalDateTime.now());
        updateWrapper.eq(Merchant::getId,target.getId());
        update(updateWrapper);
    }

    @Override
    public void updateSettleById(SettleAccountDTO settleAccountDTO, Integer id) throws Exception {
        Merchant merchant = this.getById(id);
        if (merchant == null) throw new Exception("商户不存在");
        SettleAccount target = modelMapper.map(settleAccountDTO,SettleAccount.class);
        target.setMerchantId(merchant.getId());
        LambdaQueryWrapper<SettleAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SettleAccount::getMerchantId, target.getMerchantId());
        SettleAccount src = settleAccountService.getOne(queryWrapper);
        if (src == null) throw new Exception("结算账户不存在");
        target.setId(src.getId());
        target.setUpdatedAt(LocalDateTime.now());
        settleAccountService.updateById(target);
    }

    @Override
    public void updateAccountBalance(Integer accType, Integer mchId, Double amount, Integer type, Integer action) throws Exception {
        if (amount == null || amount.floatValue() < 0.00f) throw new Exception("金额格式不正确");
        if (type == null)  throw new Exception("类型不能为空");
        long amountFen = Math.round(amount * 100);
        int availAmount = type == 1?(int) amountFen:0;
        int freezeAmount = type == 2?(int) amountFen:0;
        availAmount = action == 1 ? -availAmount:availAmount;
        freezeAmount = action == 1 ? -freezeAmount:freezeAmount;
        int i = accType == 1 ?
                    payAccountService.updateBalance(mchId,availAmount,freezeAmount):
                accType == 2 ?
                    prepaidAccountService.updateBalance(mchId,availAmount,freezeAmount)
                : -1;
        if (i == -1) throw new Exception("账户类型不正确");
        if (i == 0) throw new Exception("账户余额不足");
    }


    @Override
    public MerchantDetailDTO findMerchantById(Integer id) {
        Merchant merchant = getById(id);
        MerchantDetailDTO dto = modelMapper.map(merchant, MerchantDetailDTO.class);
        ApiConfigDTO apiConfigDTO = apiConfigService.findByMerchantId(merchant.getId());
        String publicKeyVal = String.format("%s\r\n%s\r\n%s",
                RSAUtil.PEM_FILE_PUBLIC_PKCS1_BEGIN,
                apiConfigDTO.getPubKey(),
                RSAUtil.PEM_FILE_PUBLIC_PKCS1_END);
        String privateKeyVal = String.format("%s\r\n%s\r\n%s",
                RSAUtil.PEM_FILE_PRIVATE_PKCS8_BEGIN,
                apiConfigDTO.getPrivateKey(),
                RSAUtil.PEM_FILE_PRIVATE_PKCS8_BEGIN);
        apiConfigDTO.setPubKeyVal(publicKeyVal);
        apiConfigDTO.setPrivateKeyVal(privateKeyVal);
        PayAccountDTO payAccountDTO = payAccountService.findByMerchantId(merchant.getId());
        PrepaidAccountDTO prepaidAccountDTO = prepaidAccountService.findByMerchantId(merchant.getId());
        SettleAccountDTO settleAccountDTO = settleAccountService.findByMerchantId(merchant.getId());
        dto.setApiConfig(apiConfigDTO);
        dto.setPayAccount(payAccountDTO);
        dto.setPrepaidAccount(prepaidAccountDTO);
        dto.setSettleAccount(settleAccountDTO);
        return dto;
    }

    @Override
    @Transactional
    public void addMerchant(MerchantInputDTO merchantInputDTO) throws Exception {
        Merchant merchant = modelMapper.map(merchantInputDTO,Merchant.class);
        LambdaQueryWrapper<Merchant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Merchant::getUsername, merchant.getUsername()).or()
                .eq(Merchant::getEmail, merchant.getEmail());
        Merchant oldMerchant = getOne(queryWrapper);
        if (oldMerchant != null){
            throw new Exception("用户名或邮箱已存在");
        }
        save(merchant);
        // api 配置信息构造
        KeyPair keyPair = RSAUtil.generateKeyPair();
        String privateKey = RSAUtil.getPrivateKey(keyPair);
        String publicKey = RSAUtil.getPublicKey(keyPair);
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setMchId(merchant.getId());
        apiConfig.setApiKey(EncryptUtil.md5(EncryptUtil.baseTimelineCode()));
        apiConfig.setApiSecurity(EncryptUtil.md5(EncryptUtil.baseTimelineCode()));
        apiConfig.setPrivateKey(privateKey);
        apiConfig.setPubKey(publicKey);
        apiConfigService.save(apiConfig);
        // 商户账户信息初始化
        PayAccount payAccount = new PayAccount();
        payAccount.setMerchantId(merchant.getId());
        payAccount.setAvailBalance(0);
        payAccount.setFreezeBalance(0);
        payAccountService.save(payAccount);
        PrepaidAccount prepaidAccount = new PrepaidAccount();
        prepaidAccount.setMerchantId(merchant.getId());
        prepaidAccount.setAvailBalance(0);
        prepaidAccount.setFreezeBalance(0);
        prepaidAccountService.save(prepaidAccount);
        SettleAccount settleAccount = new SettleAccount();
        settleAccount.setMerchantId(merchant.getId());
        settleAccount.setSettleFeeType(1);
        settleAccount.setSettleFeeRate(new BigDecimal("0.0"));
        settleAccount.setSettleFeeAmount(0);
        settleAccount.setStatus(false);
        settleAccount.setCreatedAt(LocalDateTime.now());
        settleAccountService.save(settleAccount);
    }

    @Override
    public List<MerchantProductDTO> selectMchProductById(Integer mchId) throws APIException {
        Merchant merchant = getById(mchId);
        if (merchant == null){
            throw new APIException("商户号不存在","MERCHANT_NOT_FOUND");
        }
        List<Product> products = productService.list();
        List<MerchantProductDTO> mps = new ArrayList<>();
        for (Product product : products){
            MerchantProductDTO mp =  this.selectMchProductById(mchId,product.getId());
            mps.add(mp);
        }
        return mps;
    }

    @Override
    public MerchantProductDTO selectMchProductById(Integer mchId, Integer productId){
        Product product = productService.getById(productId);
        MerchantProductDTO mp =  merchantProductService.getByProductId(mchId,product.getId());
        if (mp == null){
            mp = new MerchantProductDTO();
            mp.setMerchantId(mchId);
            mp.setProductId(product.getId());
            mp.setProductName(product.getProductName());
            mp.setProductType(product.getProductType());
            mp.setPayTypeCode(product.getPayTypeCode());
            mp.setInterfaceMode(1);
            mp.setStatus(false);
        }
        mp.setRateDisplay(NumberUtil.twoDecimals(mp.getRate()));
        return mp;
    }

    @Override
    public IPage<MerchantDTO> selectMerchantByPage(IPage<Void> page) {
        IPage<Merchant> merchantPage = this.page(new Page<>(page.getCurrent(),page.getSize()));
        List<Merchant> merchants = merchantPage.getRecords();
        IPage<MerchantDTO> merchantDTOIPage = merchantPage.convert(item-> modelMapper.map(item,MerchantDTO.class));
        List<MerchantDTO> merchantDTOList = merchants.stream().map(item->{
            MerchantDTO dto = modelMapper.map(item,MerchantDTO.class);
            PayAccountDTO payAccount = payAccountService.findByMerchantId(dto.getId());
            PrepaidAccountDTO prepaidAccountDTO = prepaidAccountService.findByMerchantId(dto.getId());
            SettleAccountDTO settleAccountDTO = settleAccountService.findByMerchantId(dto.getId());
            dto.setPayAccount(payAccount);
            dto.setPrepaidAccount(prepaidAccountDTO);
            dto.setSettleAccountDTO(settleAccountDTO);
            return dto;
        }).collect(Collectors.toList());
        merchantDTOIPage.setRecords(merchantDTOList);
        return merchantDTOIPage;
    }
}
