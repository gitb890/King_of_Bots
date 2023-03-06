package com.wang.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {

    @RequestMapping("getbotinfo/")
    public String  getBotInfo(){
        return "1111";
    }

    @RequestMapping("getbotinfo1/")
    public List<Map<String,String>> getBotInfo1(){
        List<Map<String,String>> list = new LinkedList<>();
        Map<String,String> bot1 = new HashMap<>();
        bot1.put("zhangfei","1820");
        bot1.put("yuji","3023");
        Map<String,String> bot2 = new HashMap<>();
        bot2.put("liubei","8924");
        bot2.put("caiwenji","18");
        list.add(bot1);
        list.add(bot2);
        return list;
    }
}
