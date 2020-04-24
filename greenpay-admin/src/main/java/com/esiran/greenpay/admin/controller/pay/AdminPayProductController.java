package com.esiran.greenpay.admin.controller.pay;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.IPassageAccountService;
import com.esiran.greenpay.pay.service.IPassageService;
import com.esiran.greenpay.pay.service.IProductService;
import com.esiran.greenpay.pay.service.ITypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/pay/product")
public class AdminPayProductController extends CURDBaseController {
    private final ITypeService typeService;
    private final IProductService productService;
    private final IPassageService passageService;
    private final IPassageAccountService passageAccountService;
    public AdminPayProductController(
            ITypeService typeService,
            IProductService productService,
            IPassageService passageService,
            IPassageAccountService passageAccountService) {
        this.typeService = typeService;
        this.productService = productService;
        this.passageService = passageService;
        this.passageAccountService = passageAccountService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/pay/product/list";
    }
    @GetMapping("/list/add")
    public String add(HttpSession httpSession, ModelMap modelMap){
        List<Type> availableTypes = typeService.list();
        modelMap.addAttribute("availableTypes",availableTypes);
//        return "admin/pay/product/add";
        return renderViewAndError("pay/product/add",httpSession,modelMap);
    }
    @PostMapping("/list/add")
    public String addPost(@Valid ProductInputDTO productInputDTO) throws PostResourceException {
        productService.add(productInputDTO);
        return "redirect:/admin/pay/product/list";
    }


    @GetMapping("/list/{id}/edit")
    public String edit(@PathVariable Integer id, HttpSession httpSession, ModelMap modelMap ){
        Product product = productService.getById(id);
        List<Type> availTypes = typeService.list();
        List<Passage> availPassages = passageService.listByPayTypeCode(product.getPayTypeCode());
        List<PassageAccount> availPassagesAcc = passageAccountService.listByPayTypeCode(product.getPayTypeCode());
        modelMap.addAttribute("availTypes",availTypes);
        modelMap.addAttribute("availPassages",availPassages);
        modelMap.addAttribute("availPassagesAcc",availPassagesAcc);
        modelMap.addAttribute("data",product);
//        return "admin/pay/product/edit";
        return renderViewAndError("pay/product/edit",httpSession,modelMap);
    }

    @PostMapping("/list/{id}/edit")
    public String editPost(@PathVariable Integer id, @Valid ProductInputDTO productInputDTO ) throws PostResourceException, ResourceNotFoundException {
        productService.updateById(id,productInputDTO);
        return String.format("redirect:/admin/pay/product/list/%s/edit",id);
    }
}
