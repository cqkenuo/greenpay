package com.esiran.greenpay.admin.controller.agentpay;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.agentpay.entity.AgentPayPassage;
import com.esiran.greenpay.agentpay.entity.AgentPayPassageAccount;
import com.esiran.greenpay.agentpay.entity.AgentPayPassageAccountInputDTO;
import com.esiran.greenpay.agentpay.entity.AgentPayPassageInputDTO;
import com.esiran.greenpay.agentpay.service.IAgentPayPassageAccountService;
import com.esiran.greenpay.agentpay.service.IAgentPayPassageService;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.IPassageAccountService;
import com.esiran.greenpay.pay.service.IPassageService;
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
@RequestMapping("/admin/agentpay/passage")
public class AdminAgentPayPassageController extends CURDBaseController {
    private final IAgentPayPassageService passageService;
    private final IAgentPayPassageAccountService passageAccountService;
    private final IInterfaceService interfaceService;
    private final ITypeService typeService;
    public AdminAgentPayPassageController(
            IAgentPayPassageService passageService,
            IInterfaceService interfaceService,
            ITypeService typeService,
            IAgentPayPassageAccountService passageAccountService) {
        this.passageService = passageService;
        this.interfaceService = interfaceService;
        this.typeService = typeService;
        this.passageAccountService = passageAccountService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/agentpay/passage/list";
    }
    @GetMapping("/list/add")
    public String add(ModelMap modelMap, HttpSession httpSession){
        List<Type> availableTypes = typeService.list();
        List<Interface> availableInters = interfaceService.list();
        modelMap.addAttribute("availableTypes",availableTypes);
        modelMap.addAttribute("availableInters",availableInters);
        return renderViewAndError("agentpay/passage/add",httpSession,modelMap);
//        return "admin/pay/passage/add";
    }
    @PostMapping("/list/add")
    public String addPost(@Valid AgentPayPassageInputDTO dto) throws PostResourceException {
        passageService.add(dto);
        return "redirect:/admin/agentpay/passage/list";
    }

    @GetMapping("/list/{passageId}/edit")
    public String edit(@PathVariable String passageId,ModelMap modelMap, HttpSession httpSession){
        AgentPayPassage data = passageService.getById(passageId);
        List<Type> availableTypes = typeService.list();
        List<Interface> availableInters = interfaceService.listByPayTypeCode(data.getPayTypeCode());
        modelMap.addAttribute("data", data);
        modelMap.addAttribute("availableTypes", availableTypes);
        modelMap.addAttribute("availableInters", availableInters);
        return renderViewAndError("agentpay/passage/edit", httpSession, modelMap);
//        return "admin/pay/passage/edit";
    }

    @PostMapping("/list/{passageId}/edit")
    public String editPost(@PathVariable Integer passageId, @Valid AgentPayPassageInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageService.updateById(passageId,dto);
        return String.format("redirect:/admin/agentpay/passage/list/%s/edit",passageId);
    }

    @GetMapping("/list/{passageId}/acc")
    public String listAcc(@PathVariable String passageId, ModelMap modelMap){
        modelMap.addAttribute("passageId", passageId);
        return "admin/agentpay/passage/acc/list";
    }
    @GetMapping("/list/{passageId}/acc/add")
    public String addAcc(@PathVariable String passageId, ModelMap modelMap, HttpSession httpSession){
        AgentPayPassage passage = passageService.getById(passageId);
        modelMap.addAttribute("passage", passage);
//        return "admin/pay/passage/acc/add";
        return renderViewAndError("agentpay/passage/acc/add",httpSession,modelMap);
    }
    @PostMapping("/list/{passageId}/acc/add")
    public String addAccPost(@PathVariable Integer passageId, @Valid AgentPayPassageAccountInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageAccountService.add(dto);
        return String.format("redirect:/admin/agentpay/passage/list/%s/acc",passageId);
    }
    @GetMapping("/list/{passageId}/acc/{accId}/edit")
    public String editAcc(@PathVariable String passageId, @PathVariable String accId, ModelMap modelMap, HttpSession httpSession){
        AgentPayPassage passage = passageService.getById(passageId);
        AgentPayPassageAccount passageAccount = passageAccountService.getById(accId);
        modelMap.addAttribute("passage", passage);
        modelMap.addAttribute("data",passageAccount);
//        return "admin/pay/passage/acc/edit";
        return renderViewAndError("agentpay/passage/acc/edit",httpSession,modelMap);
    }


    @PostMapping("/list/{passageId}/acc/{accId}/edit")
    public String editAccPost(
            @PathVariable Integer passageId,
            @PathVariable Integer accId,
            @Valid AgentPayPassageAccountInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageAccountService.updateById(accId,dto);
        return String.format("redirect:/admin/agentpay/passage/list/%s/acc/%s/edit",passageId,accId);
    }
}
