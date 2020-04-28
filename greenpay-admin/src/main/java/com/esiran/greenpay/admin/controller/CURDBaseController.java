package com.esiran.greenpay.admin.controller;

import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.merchant.entity.Merchant;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class CURDBaseController {
    private static final String THEME_PATH = "admin";
    public final String render(String viewName){
        return THEME_PATH.concat("/").concat(viewName);
    }

    public final String redirect(String url){
        return String.format("redirect:%s",url);
    }

    public final String redirect(String url, Object... args){
        return redirect(String.format(url,args));
    }

    public final Merchant theUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return (Merchant) principal;
    }
}
