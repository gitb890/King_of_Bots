package com.wang.backend.service.impl.user.account;


import com.wang.backend.pojo.User;
import com.wang.backend.service.impl.utils.UserDetailImpl;
import com.wang.backend.service.user.account.LoginService;
import com.wang.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    //自动注入
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(username,password);
            //存储加密的密码
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            /*登录失败，会自动处理*/

            UserDetailImpl userDetail = (UserDetailImpl) authenticate.getPrincipal();
            User user = userDetail.getUser();//取出用户

            String jwt = JwtUtil.createJWT(user.getId().toString());//JWT获取用户Id

        Map<String, String> map = new HashMap<>(); // 存放返回的结果
            map.put("error_message", "success");
            map.put("token", jwt);
            return map;
    }
}
