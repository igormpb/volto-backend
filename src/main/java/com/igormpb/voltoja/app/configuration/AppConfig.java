package com.igormpb.voltoja.app.configuration;

import com.igormpb.voltoja.infra.middleware.AuthMiddleware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor( new AuthMiddleware()).addPathPatterns("/checkout/**");
    }
}
