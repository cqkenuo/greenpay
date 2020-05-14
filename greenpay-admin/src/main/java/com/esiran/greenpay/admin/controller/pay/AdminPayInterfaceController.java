package com.esiran.greenpay.admin.controller.pay;

import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.common.util.MapUtil;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.pay.entity.Interface;
import com.esiran.greenpay.pay.entity.InterfaceInputDTO;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.ITypeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pay/interface")
public class AdminPayInterfaceController extends CURDBaseController {
    private final IInterfaceService interfaceService;
    private final ITypeService typeService;
    private static final Gson gson = new GsonBuilder().create();
    public AdminPayInterfaceController(IInterfaceService interfaceService, ITypeService typeService) {
        this.interfaceService = interfaceService;
        this.typeService = typeService;
    }

    @GetMapping("/list")
    @PageViewHandleError
    public String list(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        String queryString = httpServletRequest.getQueryString();
        Map<String, String> qm = MapUtil.httpQueryString2map(queryString);
        if (qm != null && qm.size()>0) {
            String s = qm.get("queryData");
            if (StringUtils.isNumeric(s)){
                qm.put("id", s);
            }else {
                qm.put("interfaceName",s);
            }
            String qsall = MapUtil.map2httpQuery(qm);
            modelMap.put("qs",qsall);
        }
        return "admin/pay/interface/list";
    }

    @PostMapping(value = "/list")
    public String listPost(@RequestParam String action, @RequestParam String ids) throws PostResourceException {
        if (action.equals("del")){
            List<Integer> allIds = gson.fromJson(ids,new TypeToken<List<Integer>>(){}.getType());
            interfaceService.delByIds(allIds);
        }
        return redirect("/admin/pay/interface/list");
    }

    @GetMapping("/list/add")
    @PageViewHandleError
    public String add(HttpSession httpSession, ModelMap modelMap){
        List<Type> availableTypes = typeService.list();
        modelMap.addAttribute("availableTypes",availableTypes);
        return "admin/pay/interface/add";
    }

    @GetMapping("/list/{id}/edit")
    @PageViewHandleError
    public String update(@PathVariable String id,HttpSession httpSession, ModelMap modelMap) throws ResourceNotFoundException {
        List<Type> availableTypes = typeService.list();
        Interface data = interfaceService.getById(id);
        if (data == null) throw new ResourceNotFoundException();
        modelMap.addAttribute("availableTypes",availableTypes);
        modelMap.addAttribute("data",data);
        return "admin/pay/interface/edit";
    }

    @PostMapping("/list/add")
    public String addFrom(@Valid InterfaceInputDTO dto) throws PostResourceException {
        interfaceService.addInterface(dto);
        return redirect("/admin/pay/interface/list");
    }
    @PostMapping("/list/{id}/edit")
    public String addFrom(@PathVariable Integer id, @Valid InterfaceInputDTO dto) throws PostResourceException, ResourceNotFoundException {
        interfaceService.updateById(id,dto);
        return redirect("/admin/pay/interface/list/%s/edit",id);
    }
}
