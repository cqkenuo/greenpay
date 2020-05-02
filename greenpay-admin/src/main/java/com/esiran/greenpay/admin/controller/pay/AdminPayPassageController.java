package com.esiran.greenpay.admin.controller.pay;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    private static final Gson gson = new GsonBuilder().create();
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
    @PageViewHandleError
    public String list(){
        return "admin/pay/passage/list";
    }
    @PostMapping(value = "/list")
    public String listPost(@RequestParam String action, @RequestParam String ids) throws PostResourceException {
        if (action.equals("del")){
            List<Integer> allIds = gson.fromJson(ids,new TypeToken<List<Integer>>(){}.getType());
            passageService.delByIds(allIds);
        }
        return redirect("/admin/pay/passage/list");
    }
    @GetMapping("/list/add")
    @PageViewHandleError
    public String add(ModelMap modelMap, HttpSession httpSession){
        List<Type> availableTypes = typeService.list();
        List<Interface> availableInters = interfaceService.list();
        modelMap.addAttribute("availableTypes",availableTypes);
        modelMap.addAttribute("availableInters",availableInters);
        return "admin/pay/passage/add";
    }
    @PostMapping("/list/add")
    public String addPost(@Valid PassageInputDTO dto) throws PostResourceException {
        passageService.add(dto);
        return "redirect:/admin/pay/passage/list";
    }

    @GetMapping("/list/{passageId}/edit")
    @PageViewHandleError
    public String edit(@PathVariable String passageId,ModelMap modelMap, HttpSession httpSession){
        Passage data = passageService.getById(passageId);
        List<Type> availableTypes = typeService.list();
        List<Interface> availableInters = interfaceService.listByPayTypeCode(data.getPayTypeCode());
        modelMap.addAttribute("data", data);
        modelMap.addAttribute("availableTypes", availableTypes);
        modelMap.addAttribute("availableInters", availableInters);
        return "admin/pay/passage/edit";
    }

    @PostMapping("/list/{passageId}/edit")
    public String editPost(@PathVariable Integer passageId, @Valid PassageInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageService.updateById(passageId,dto);
        return redirect("/admin/pay/passage/list/%s/edit",passageId);
    }

    @GetMapping("/list/{passageId}/acc")
    @PageViewHandleError
    public String listAcc(@PathVariable String passageId, ModelMap modelMap){
        modelMap.addAttribute("passageId", passageId);
        return "admin/pay/passage/acc/list";
    }
    @PostMapping(value = "/list/{passageId}/acc")
    public String listAccPost(
            @PathVariable String passageId,
            @RequestParam String action,
            @RequestParam String ids) throws PostResourceException {
        if (action.equals("del")){
            List<Integer> allIds = gson.fromJson(ids,new TypeToken<List<Integer>>(){}.getType());
            passageAccountService.delIds(allIds);
        }
        return redirect("/admin/pay/passage/list/%s/acc",passageId);
    }
    @GetMapping("/list/{passageId}/acc/add")
    @PageViewHandleError
    public String addAcc(@PathVariable String passageId, ModelMap modelMap, HttpSession httpSession){
        Passage passage = passageService.getById(passageId);
        modelMap.addAttribute("passage", passage);
        return "admin/pay/passage/acc/add";
    }
    @PostMapping("/list/{passageId}/acc/add")
    public String addAccPost(@PathVariable Integer passageId, @Valid PassageAccountInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageAccountService.add(dto);
        return redirect("/admin/pay/passage/list/%s/acc",passageId);
    }
    @GetMapping("/list/{passageId}/acc/{accId}/edit")
    @PageViewHandleError
    public String editAcc(@PathVariable String passageId, @PathVariable String accId, ModelMap modelMap, HttpSession httpSession){
        Passage passage = passageService.getById(passageId);
        PassageAccount passageAccount = passageAccountService.getById(accId);
        modelMap.addAttribute("passage", passage);
        modelMap.addAttribute("data",passageAccount);
        return "admin/pay/passage/acc/edit";
    }


    @PostMapping("/list/{passageId}/acc/{accId}/edit")
    public String editAccPost(
            @PathVariable Integer passageId,
            @PathVariable Integer accId,
            @Valid PassageAccountInputDTO dto) throws ResourceNotFoundException, PostResourceException {
        passageAccountService.updateById(accId,dto);
        return redirect("/admin/pay/passage/list/%s/acc/%s/edit",passageId,accId);
    }
}
