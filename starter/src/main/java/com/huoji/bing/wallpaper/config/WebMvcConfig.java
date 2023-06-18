package com.huoji.bing.wallpaper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 这里需要先将限流拦截器入住，不然无法获取到拦截器中的
     *
     * @return
     */
    @Bean
    public AccessLimitInterceptor getAccessLimitIntercept() {
        return new AccessLimitInterceptor();
    }

    /**
     * 多个拦截器组成一个拦截器链
     * 拦截admin
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAccessLimitIntercept())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/login/**", "/index/**", "/doLogin/**", "/captcha/**", "/admin/**", "/component/**", "/api/**", "/websocket/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/main");
        super.addViewControllers(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**", "/favicon.ico").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
