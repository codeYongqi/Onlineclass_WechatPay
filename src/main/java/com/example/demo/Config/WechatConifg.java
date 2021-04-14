package com.example.demo.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
}
