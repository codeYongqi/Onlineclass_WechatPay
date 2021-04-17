package com.example.demo.Controller;

import com.example.demo.Config.WechatConifg;
import com.example.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {

    @Autowired
    private WechatConifg wechatConifg;

    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page")String accessPage) throws UnsupportedEncodingException {
        String url = wechatConifg.getOpenRedirectUrl();
        String callbakc = URLEncoder.encode(url,"GBK");
        String qrCodeUrl = String.format(wechatConifg.getOPEN_QRCODE_URL(),wechatConifg.getOpenAppId(),callbakc,accessPage);
        return JsonData.buildSuccess(qrCodeUrl);
    }
}
