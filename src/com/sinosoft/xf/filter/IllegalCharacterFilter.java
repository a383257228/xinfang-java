package com.sinosoft.xf.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 过滤非法字符
 */
public class IllegalCharacterFilter implements Filter {
	public FilterConfig config;
	
	public static boolean isContains(String container, String[] regx) {
        boolean result = false;
        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }
	String key = "timstamp";
	public void init(FilterConfig filterConfig) throws ServletException {
		 config = filterConfig;
	}
	private Map<String, String[]> processParamsters(Map<String, String[]> m) {
		for(String key : m.keySet()) {
			key = key.replaceAll("'", "");
			key = key.replaceAll("|", "");
			key = key.replaceAll("\\|", "");
			key = key.replaceAll("\\'", "");
			String[] paramValue = m.get(key);
			if(paramValue!=null){
				String value = String.valueOf(paramValue[0]);
//				value = value.replaceAll("<S", "《");
//				value = value.replaceAll("<s", "《");
//				value = value.replaceAll("t>", "》");
//				value = value.replaceAll("T>", "》");
				value = value.replaceAll("'", "");
				value = value.replaceAll("|", "");
				value = value.replaceAll("\\'", "");
//				value = value.replaceAll("%", "％");
//				value = value.replaceAll("\\%", "％");
//				value = value.replaceAll("\\_", "－");
//				value = value.replaceAll("\\@", "＠");
				m.put(key, new String[]{value});
			}
		}
		return m ;
	}
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest servletrequest = (HttpServletRequest) request;
		HttpServletResponse servletresponse = (HttpServletResponse) response;
		servletresponse.setHeader("Cache-Control","no-store");
		servletresponse.setHeader("Pragrma","no-cache");
		servletresponse.setDateHeader("Expires",0);
		
		if(!"/logon/logon!logonStandard.action,login!getOnlineUser.action,login!login.action,systemnotice!loadSystemNoticeById.action".contains(servletrequest.getServletPath())){
			Cookie[] cookies = servletrequest.getCookies();
			if(cookies==null||cookies.length==1){
				return ;
			}else if(cookies.length>1){
				//验证规则,必须有含有key的cookie,可以不添加
			}
		}
		String uuid = UUID.randomUUID().toString();//自定义规则
		Cookie cookie = new Cookie(key,uuid);
//		cookie.setSecure(true);
		cookie.setPath("/");
		servletresponse.addCookie(cookie);
        Map<String, String[]> m = getMapFormInMap(request.getParameterMap());
        ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(servletrequest,processParamsters(m)); 
		filterChain.doFilter(wrapRequest, servletresponse);
	}
	public static Map<String,String[]> getMapFormInMap(Map<String,String[]> inMap){
		Map<String,String[]> map = new HashMap<String,String[]>();
		Iterator<String> it = inMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] value = inMap.get(key);
			map.put(key, value);
		}
		return map;
	}
	public void destroy() {
		 this.config = null;
	}
}
