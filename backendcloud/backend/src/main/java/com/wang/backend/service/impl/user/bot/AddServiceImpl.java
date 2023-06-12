package com.wang.backend.service.impl.user.bot;

import com.wang.backend.mapper.BotMapper;
import com.wang.backend.pojo.Bot;
import com.wang.backend.pojo.User;
import com.wang.backend.service.impl.utils.UserDetailImpl;
import com.wang.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String,String> map = new HashMap<>();
        if (title == null || title.length() ==0){
            map.put("error_message","标题不能为空");
            return map;
        }
        if (title.length() >100){
            map.put("error_message","标题不能超过100");
            return map;
        }
        if (description == null || description.length() == 0){
            map.put("error_message","这个用户很懒啥也没留下~~");
        }
        if (description.length() > 300){
            map.put("error_message","Bot描述内容不能超过300字");
            return map;
        }
        if (content == null || content.length() == 0){
            map.put("error_message","代码不能为空");
            return map;
        }
        if (content.length()>10000){
            map.put("error_message","代码长度不能超过10000");
            return map;
        }

        Date now = new Date();
        Bot bot = new Bot(null, user.getId(),title,description,content,now,now);

        botMapper.insert(bot);
        map.put("error_message","success");

        return map;
    }
}
