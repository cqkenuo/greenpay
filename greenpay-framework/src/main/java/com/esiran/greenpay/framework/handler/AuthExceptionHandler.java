package com.esiran.greenpay.framework.handler;

import com.esiran.greenpay.common.entity.APIError;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(
            AuthenticationException e,
            HttpServletRequest request,
            HttpSession httpSession){
        List<APIError> apiErrors = new ArrayList<>();
        if (e instanceof IncorrectCredentialsException){
            apiErrors.add(new APIError("AUTH_FAIL","用户名或密码错误"));
        }else if (e instanceof UnknownAccountException){
            apiErrors.add(new APIError("AUTH_FAIL","用户名或密码错误"));
        } else {
            apiErrors.add(new APIError("AUTH_FAIL",e.getMessage()));
        }
        httpSession.setAttribute("errors",apiErrors);
        String s = request.getRequestURI();
        return String.format("redirect:%s",s);
    }
}
