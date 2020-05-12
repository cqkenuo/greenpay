package com.esiran.greenpay.admin.controller;

import com.esiran.greenpay.admin.entity.UsernamePasswordInputDTO;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class AdminController extends CURDBaseController{
    @GetMapping
    public String index(){
        return redirect("/home");
    }
    @GetMapping("/home")
    public String home(){
        return "admin/index";
    }
    @GetMapping("/login")
    @PageViewHandleError
    public String login(){
        if (SecurityUtils.getSubject().isAuthenticated()){
            return redirect("/home");
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid UsernamePasswordInputDTO inputDTO){
        UsernamePasswordToken token = new UsernamePasswordToken(
                inputDTO.getUsername(),inputDTO.getPassword());
        SecurityUtils.getSubject().login(token);
        return redirect("/home");
    }
    @PostMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return redirect("/login");
    }
}
