package com.example.demo.Controller;

import com.example.demo.Config.WechatConifg;
import com.example.demo.Mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private WechatConifg wechatConifg;

    @RequestMapping("test")
    public String test(){
        return "this is Config test" + wechatConifg.getAppId() + wechatConifg.getAppSercret();
    }

    @RequestMapping("wechatConfig")
    public WechatConifg wechatConifg(){
        return wechatConifg;
    }

    @Autowired
    private VideoMapper videoMapper;

    @RequestMapping("testDB")
    public Object testDB(){
        return  videoMapper.findAll();
    }

}
