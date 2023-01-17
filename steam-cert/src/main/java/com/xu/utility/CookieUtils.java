package com.xu.utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
	/**
	 * 创建一个新的Cookie
	 * @param name  名字
	 * @param value 值
	 * @param uri 路径
	 * @param time 最长保存时间
	 * @return 返回这个新Cookie
	 */
	public static Cookie setCookie(String name,String value,String uri,int time) {
		Cookie cook=new Cookie(name,value);
		cook.setMaxAge(time);
		cook.setPath(uri);
		return cook;
	}

	public static String getCookieValue(HttpServletRequest rq, String cookieName){
		if(null==cookieName)return "";
        Cookie[] cookies = rq.getCookies();
        String cookieValue="";
        if(cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                if(cookieName.equals(cookies[i].getName())) {
					cookieValue=cookies[i].getValue();
                }
            }
        }
		return cookieValue;
	}
}
