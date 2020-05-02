package com.esiran.greenpay.openapi.controller;

import com.esiran.greenpay.openapi.entity.WxOPenIdInputDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/api/v1/helper/wx")
public class APIWxHelperController {
    @GetMapping("/openid")
    public String openId(@Valid WxOPenIdInputDTO wxOPenIdInputDTO) throws UnsupportedEncodingException {
        String redirectUrl = wxOPenIdInputDTO.getRedirectUrl();
        String url = URLDecoder.decode(redirectUrl, "utf-8");
        return String.format("redirect:%s",url);
    }
}
