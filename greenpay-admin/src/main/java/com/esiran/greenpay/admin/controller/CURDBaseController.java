package com.esiran.greenpay.admin.controller;

import com.esiran.greenpay.common.entity.APIError;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class CURDBaseController {
    private static final String THEME_PATH = "admin";
    public final String render(String viewName){
        return THEME_PATH.concat("/").concat(viewName);
    }

    @SuppressWarnings("unchecked")
    public final List<APIError> getErrors(
            HttpSession httpSession){
        return (List<APIError>) httpSession.getAttribute("errors");
    }

    public final String renderViewAndError(
            String viewName, HttpSession httpSession, ModelMap modelMap){
        List<APIError> apiErrors = getErrors(httpSession);
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        return THEME_PATH.concat("/").concat(viewName);
    }

}
