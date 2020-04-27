package com.esiran.greenpay.admin.controller.merchant;

import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.merchant.entity.MerchantDetailDTO;
import com.esiran.greenpay.merchant.entity.MerchantInputDTO;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.pay.entity.Passage;
import com.esiran.greenpay.pay.entity.PassageAccount;
import com.esiran.greenpay.pay.entity.ProductPassage;
import com.esiran.greenpay.pay.service.IPassageAccountService;
import com.esiran.greenpay.pay.service.IPassageService;
import com.esiran.greenpay.pay.service.IProductService;
import com.esiran.greenpay.pay.service.ITypeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController {
    private static final Gson gson = new GsonBuilder().create();
    private final IMerchantService merchantService;
    private final IProductService productService;
    private final ITypeService typeService;
    private final IPassageService passageService;
    private final IPassageAccountService passageAccountService;
    public AdminMerchantController(IMerchantService merchantService, IProductService productService, ITypeService typeService, IPassageService passageService, IPassageAccountService passageAccountService) {
        this.merchantService = merchantService;
        this.productService = productService;
        this.typeService = typeService;
        this.passageService = passageService;
        this.passageAccountService = passageAccountService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/merchant/list";
    }
    @GetMapping("/list/{mchId}/edit")
    public String edit(@PathVariable Integer mchId, ModelMap modelMap){
        MerchantDetailDTO merchantDTO = merchantService.findMerchantById(mchId);
        modelMap.addAttribute("merchant",merchantDTO);
        return "admin/merchant/edit";
    }

    @GetMapping("/list/{mchId}/product/list")
    public String password(@PathVariable String mchId){
        return "admin/merchant/product/list";
    }


    @GetMapping("/list/{mchId}/product/edit")
    public String product(@PathVariable String mchId, @RequestParam Integer productId, ModelMap modelMap) throws Exception {
        MerchantProductDTO merchantProduct = merchantService.selectMchProductById(Integer.valueOf(mchId),productId);
        List<Passage> availPassages = passageService.listByPayTypeCode(merchantProduct.getPayTypeCode());
        List<PassageAccount> availPassagesAcc = passageAccountService.listByPayTypeCode(merchantProduct.getPayTypeCode());
//        List<ProductPassage> usagePassages = productPassageService.listByProductId(product.getId());
//        String usagePassagesJson = gson.toJson(usagePassages);
        String availPassagesJson = gson.toJson(availPassages);
        modelMap.addAttribute("merchantProduct", merchantProduct);
        modelMap.addAttribute("availPassages", availPassages);
        modelMap.addAttribute("availPassagesAcc", availPassagesAcc);
        modelMap.addAttribute("availPassagesJson", availPassagesJson);
        return "admin/merchant/product/edit";
    }
    @GetMapping("/add")
    @SuppressWarnings("unchecked")
    public String add(HttpSession httpSession, ModelMap modelMap){
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        return "admin/merchant/add";
    }

    @PostMapping("/add")
    public String add(@Valid MerchantInputDTO merchant) throws Exception {
        merchantService.addMerchant(merchant);
        return "redirect:/admin/merchant/list";
    }
}
