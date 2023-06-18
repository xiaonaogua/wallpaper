package com.huoji.bing.wallpaper.config;

import cn.hutool.core.util.StrUtil;
import com.huoji.bing.wallpaper.utils.CookieUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String redUrl = "/index";
        // 没有登录
        /*String loginToken = CookieUtils.getCookie(request, "merchantToken");
        if (StrUtil.isBlank(loginToken)) {
            response.sendRedirect(redUrl);
            return false; // 阻止往后放行
        }
        Object obj = redisTemplate.opsForValue().get(loginToken);
        if (obj == null) {
            response.sendRedirect(redUrl);
            return false; // 阻止往后放行
        }*/
        return true;
    }
}
