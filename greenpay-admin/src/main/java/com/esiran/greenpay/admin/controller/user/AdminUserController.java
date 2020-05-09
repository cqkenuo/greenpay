package com.esiran.greenpay.admin.controller.user;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.UserInputDTO;
import com.esiran.greenpay.system.entity.UserInputVo;
import com.esiran.greenpay.system.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends CURDBaseController {
    private final IUserService userService;

    public AdminUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    @PageViewHandleError
    public String profile(ModelMap modelMap) {
        User user = theUser();
        modelMap.addAttribute("data", user);
        return "/admin/user/profile";
    }

    @PostMapping("/profile")
    public String profilePost(@Valid UserInputDTO inputDto) throws PostResourceException {
        User theUser = theUser();
        userService.updateUser(theUser.getId(), inputDto);
        return redirect("/admin/user/profile");
    }

    @GetMapping("/security")
    @PageViewHandleError
    public String security() {
        return "/admin/user/security";
    }


    @PostMapping("/security")
    public String security(@Valid UserInputVo userInputVo) throws PostResourceException {
        userService.updateUserPWD(theUser().getId(), userInputVo);

        SecurityUtils.getSubject().logout();
        return redirect("/admin/login");
    }
}
