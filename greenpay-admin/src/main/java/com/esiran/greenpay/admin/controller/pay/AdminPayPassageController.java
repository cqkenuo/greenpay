package com.esiran.greenpay.admin.controller.pay;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.IPassageAccountService;
import com.esiran.greenpay.pay.service.IPassageService;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/admin/pay/passage")
public class AdminPayPassageController extends CURDBaseController {
    private final IPassageService passageService;
    private final IInterfaceService interfaceService;
    private final ITypeService typeService;
    private final IPassageAccountService passageAccountService;
    public AdminPayPassageController(
            IPassageService passageService,
            IInterfaceService interfaceService,
            ITypeService typeService, IPassageAccountService passageAccountService) {
        this.passageService = passageService;
        this.interfaceService = interfaceService;
        this.typeService = typeService;
        this.passageAccountService = passageAccountService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/pay/passage/list";
    }
    @GetMapping("/list/add")
    public String add(ModelMap modelMap, HttpSession httpSession){
        List<Type> availableTypes = typeService.list();
        List<Interface> availableInters = interfaceService.list();
        modelMap.addAttribute("availableTypes",availableTypes);
        modelMap.addAttribute("availableInters",availableInters);
        return renderViewAndError("pay/passage/add",httpSession,modelMap);
//        return "admin/pay/passage/add";
    }
    @PostMapping("/list/add")
    public String addPost(@Valid PassageInputDTO dto) throws PostResourceException {
        passageService.add(dto);
        return "redirect:/admin/pay/passage/list";
    }

    @GetMapping("/list/{passageId}/edit")
    public String edit(@PathVariable String passageId,ModelMap modelMap, HttpSession httpSession){
        Passage data = passageService.getById(passageId);
        List<Type> availableTypes = typeService.list();
        List<Interface> availableInters = interfaceService.listByPayTypeCode(data.getPayTypeCode());
        modelMap.addAttribute("data", data);
        modelMap.addAttribute("availableTypes", availableTypes);
        modelMap.addAttribute("availableInters", availableInters);
        return renderViewAndError("pay/passage/edit", httpSession, modelMap);
//        return "admin/pay/passage/edit";
    }

    @PostMapping("/list/{passageId}/edit")
    public String editPost(@PathVariable Integer passageId, @Valid PassageInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageService.updateById(passageId,dto);
        return String.format("redirect:/admin/pay/passage/list/%s/edit",passageId);
    }

    @GetMapping("/list/{passageId}/acc")
    public String listAcc(@PathVariable String passageId, ModelMap modelMap){
        modelMap.addAttribute("passageId", passageId);
        return "admin/pay/passage/acc/list";
    }
    @GetMapping("/list/{passageId}/acc/add")
    public String addAcc(@PathVariable String passageId, ModelMap modelMap, HttpSession httpSession){
        Passage passage = passageService.getById(passageId);
        modelMap.addAttribute("passage", passage);
//        return "admin/pay/passage/acc/add";
        return renderViewAndError("pay/passage/acc/add",httpSession,modelMap);
    }
    @PostMapping("/list/{passageId}/acc/add")
    public String addAccPost(@PathVariable Integer passageId, @Valid PassageAccountInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageAccountService.add(dto);
        return String.format("redirect:/admin/pay/passage/list/%s/acc",passageId);
    }
    @GetMapping("/list/{passageId}/acc/{accId}/edit")
    public String editAcc(@PathVariable String passageId, @PathVariable String accId, ModelMap modelMap, HttpSession httpSession){
        Passage passage = passageService.getById(passageId);
        PassageAccount passageAccount = passageAccountService.getById(accId);
        modelMap.addAttribute("passage", passage);
        modelMap.addAttribute("data",passageAccount);
//        return "admin/pay/passage/acc/edit";
        return renderViewAndError("pay/passage/acc/edit",httpSession,modelMap);
    }


    @PostMapping("/list/{passageId}/acc/{accId}/edit")
    public String editAccPost(
            @PathVariable Integer passageId,
            @PathVariable Integer accId,
            @Valid PassageAccountInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageAccountService.updateById(accId,dto);
        return String.format("redirect:/admin/pay/passage/list/%s/acc/%s/edit",passageId,accId);
    }
}
