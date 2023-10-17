package com.example.insta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.insta.interceptor.SignInCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
  @Autowired
  private SignInCheckInterceptor signInCheckInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(signInCheckInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/auth/**", "/auth/errorpage", "/loginerror", "/", "/img/**", "/CSS/**");
    WebMvcConfigurer.super.addInterceptors(registry);
  }
}