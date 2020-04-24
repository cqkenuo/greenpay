package com.esiran.greenpay.admin.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @GetMapping("/profile")
    public String profile(){
        return "admin/user/profile";
    }
    @GetMapping("/security")
    public String security(){
        return "admin/user/security";
    }
}
