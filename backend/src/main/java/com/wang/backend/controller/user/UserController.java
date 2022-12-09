package com.wang.backend.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("user/")
    public List<String> user(){
        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        return list;
    }

    @RequestMapping("userto/")
    public Map<Object, Object> userto(){
        Map<Object, Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("age","43");
        return map;
    }
}
