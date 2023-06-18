package com.huoji.bing.wallpaper.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * cookie操作工具
 * 
 * @author Joe
 */
public class CookieUtils {

	private CookieUtils() {
	}


    /**
     * 设置token到cookie
     * @param request
     * @param response
     */
	public static void setCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
		// Cookie添加token
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(3600);
		cookie.setPath("/");
		if ("https".equals(request.getScheme())) {
			cookie.setSecure(true);
		}
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
	
	/**
	 * 按名称获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || StrUtil.isBlank(name)) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

	/**
	 * 清除cookie
	 * 
	 * @param response
	 * @param response
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name, String path, String domain) {
		Cookie cookie = new Cookie(name, null);
		if (path != null) {
			cookie.setPath(path);
		}
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setMaxAge(-1000);
		response.addCookie(cookie);
	}
}
