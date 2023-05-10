package com.wang.backend.service.impl.user.account;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.backend.mapper.UserMapper;
import com.wang.backend.pojo.User;
import com.wang.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String,String> map = new HashMap<>();
        if (username == null){
            map.put("error_message:","用户名不能为空");
            return map;
        }
        if (password == null || confirmedPassword ==null){
            map.put("error_message:","密码不能为空");
            return map;
        }

        username = username.trim();//去掉输入的空格
        if (username.length() == 0){
            map.put("error_message:","用户名不能为空");
            return map;
        }

        if (password.length() == 0 || confirmedPassword.length() == 0){
            map.put("error_message","密码不能为空");
            return map;
        }
        if (username.length()>100){
            map.put("error_message:","用户名过长");
            return map;
        }
        if (password.length()>100||confirmedPassword.length()>100){
            map.put("error_message","密码过长");
            return map;
        }
        if (!confirmedPassword.equals(password)){
            map.put("error_message","密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()){
            map.put("error_message","用户名已存在");
            return map;
        }

        String encodepassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/211729_lg_f171e3850d.jpg";
        User user = new User(null,username,encodepassword,photo);

        userMapper.insert(user);

        map.put("message","success");
        return map;
    }
}
