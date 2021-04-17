package com.example.demo.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 微信配置类
 */
@Configuration
@Data
@PropertySource(value = "classpath:application.properties")
public class WechatConifg {

    //公众号appId
    @Value("${wxpay.appid}")
    private String appId;

    //公众号秘钥
    @Value("${wxpay.appsercret}")
    private String appSercret;

    @Value("${wxopen.appid}")
    private String openAppId;

    @Value("${wxopen.appsercret}")
    private String openAppSecret;

    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;

    //微信平台二维码连接
    private final String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
}
