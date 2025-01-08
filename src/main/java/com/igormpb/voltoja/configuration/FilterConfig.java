package com.igormpb.voltoja.configuration;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TokenValidate> tokenValidade (){
        FilterRegistrationBean<TokenValidate> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TokenValidate());
        registrationBean.setUrlPatterns(Arrays.asList("/checkout/*"));


        registrationBean.setOrder(1);
        return registrationBean;

    }

}
