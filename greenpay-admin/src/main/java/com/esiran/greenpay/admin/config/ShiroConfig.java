package com.esiran.greenpay.admin.config;

import com.esiran.greenpay.admin.shiro.AdminAuthRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

@Configuration
public class ShiroConfig {

//    private final AdminAuthRealm realm;
//
//    public ShiroConfig(AdminAuthRealm realm) {
//        this.realm = realm;
//    }
//    @Bean
//    public DefaultWebSessionManager sessionManager(){
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        Cookie cookie = new SimpleCookie("abc");
//        cookie.setSecure(true);
//        cookie.setMaxAge(24 * 60 * 60);
//        sessionManager.setSessionIdCookie(cookie);
//        return sessionManager;
//    }
//
//    @Bean("securityManager")
//    public DefaultWebSecurityManager  securityManager(SessionManager sessionManager){
//        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//        securityManager.setRealm(realm);
//        securityManager.setSessionManager(sessionManager);
////        SessionManager s = new DefaultSessionManager();
////        s.start()
////        securityManager.setSessionManager();
//        return securityManager;
//    }
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/admin/**", "authc");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }
}
