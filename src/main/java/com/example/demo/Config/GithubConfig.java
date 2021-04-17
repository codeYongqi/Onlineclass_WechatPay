package com.example.demo.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = "classpath:application.properties")
public class GithubConfig {

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.redirect_uri}")
    private String redirectUrl;

    private final String GITHUB_LOGIN = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s";

}
