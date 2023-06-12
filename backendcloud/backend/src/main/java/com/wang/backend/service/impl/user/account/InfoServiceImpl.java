package com.wang.backend.service.impl.user.account;


import com.wang.backend.pojo.User;
import com.wang.backend.service.impl.utils.UserDetailImpl;
import com.wang.backend.service.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public Map<String, String> getinfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailImpl infoUser = (UserDetailImpl) authentication.getPrincipal();
        User user = infoUser.getUser();

        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("id",user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo",user.getPhoto());

        return map;
    }
}
