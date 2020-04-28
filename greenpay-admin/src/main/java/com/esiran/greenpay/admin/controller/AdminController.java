package com.esiran.greenpay.admin.controller;

import com.esiran.greenpay.admin.entity.UsernamePasswordInputDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController extends CURDBaseController{
    @GetMapping("/home")
    public String index(){
        return "admin/index";
    }
    @GetMapping("/login")
    public String login(){
        if (SecurityUtils.getSubject().isAuthenticated()){
            return redirect("/admin/home");
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid UsernamePasswordInputDTO inputDTO){
        UsernamePasswordToken token = new UsernamePasswordToken(
                inputDTO.getUsername(),inputDTO.getPassword());
        SecurityUtils.getSubject().login(token);
        return redirect("/admin/home");
    }
    @PostMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return redirect("/admin/login");
    }
}
