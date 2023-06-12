package com.wang.backend.controller.user.account;


import com.wang.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/account/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        System.out.println("getusers");
        System.out.println(username+" "+password);
        System.out.println(loginService.getToken(username,password));
        return loginService.getToken(username,password);
    }

}
