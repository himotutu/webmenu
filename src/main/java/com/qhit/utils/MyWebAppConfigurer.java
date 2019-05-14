package com.qhit.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2019/3/31 0031.
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")           //允许请求哪些资源
                .allowedOrigins("*") //允许哪个网址可以访问我们的程序
                .allowedMethods("GET", "POST")          //请求方式
                .allowCredentials(true).maxAge(3600);  //超时时间
    }
}