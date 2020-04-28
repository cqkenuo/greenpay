package com.esiran.greenpay.admin.controller;

import com.esiran.greenpay.admin.entity.UsernamePasswordInputDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/home")
    public String index(){
        return "admin/index";
    }
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid UsernamePasswordInputDTO inputDTO){
        return "redirect:/admin/home";
    }
}
