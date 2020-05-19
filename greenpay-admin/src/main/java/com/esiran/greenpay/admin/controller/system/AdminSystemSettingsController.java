package com.esiran.greenpay.admin.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/settings")
public class AdminSystemSettingsController {
    @GetMapping()
    public String list(){
        return "admin/system/settings/index";
    }
//    @GetMapping("/add")
//    public String add(){
//        return "admin/merchant/add";
//    }
}
