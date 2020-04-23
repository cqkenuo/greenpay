package com.esiran.greenpay.admin.controller.pay;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.TypeDTO;
import com.esiran.greenpay.pay.entity.TypeInputDTO;
import com.esiran.greenpay.pay.service.ITypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/pay/type")
public class AdminPayTypeController extends CURDBaseController {
    private final ITypeService typeService;

    public AdminPayTypeController(ITypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/pay/type/list";
    }
    @GetMapping("/list/add")
    public String add(HttpSession httpSession, ModelMap modelMap){
        return renderViewAndError("pay/type/add",httpSession,modelMap);
    }
    @GetMapping("/list/{payTypeCode}/edit")
    public String edit(@PathVariable String payTypeCode,
                       HttpSession httpSession,
                       ModelMap modelMap) throws ResourceNotFoundException {
        TypeDTO typeDTO = typeService.getTypeByCode(payTypeCode);
        if (typeDTO == null) throw new ResourceNotFoundException();
        modelMap.addAttribute("type",typeDTO);
        return renderViewAndError("pay/type/edit",httpSession,modelMap);
    }
    @PostMapping("/list/add")
    public String addFrom(@Valid TypeInputDTO inputDTO){
        typeService.saveType(inputDTO);
        return "redirect:/admin/pay/type/list";
    }

    @PostMapping("/list/{payTypeCode}/edit")
    public String edit(
            @PathVariable String payTypeCode,
            @Valid TypeInputDTO inputDTO
    ) throws ResourceNotFoundException {
        typeService.updateType(inputDTO);
        return String.format("redirect:/admin/pay/type/list/%s/edit",payTypeCode);
    }
}
