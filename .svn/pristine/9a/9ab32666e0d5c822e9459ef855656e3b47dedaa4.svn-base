/**
 * Copyright (c) sinosoft 2010
 * 中科软科技股份有限公司 行业推广
 * com.sinosoft.authority.filter
 * Create Date 2010-12-1
 * @author Sunzhe
 */
package com.sinosoft.authority.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 *权限过滤器，用于防止非法访问action层
 *
 * @author Sunzhe
 *
 * 2010-12-1
 */
public class AuthorityFilter implements Filter{

	private String allowAddress;
	
	private String sessionKey;
	
	private String loginPage;
	
	ServletContext application;
	//系统文根
	String contextPath;
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		String url = servletRequest.getRequestURI();
		if(!"/".equals(contextPath)){
			url = StringUtils.substringAfter(url, contextPath);
		}
		   
		//开始过滤
		if(session.getAttribute(sessionKey)==null){
			//如果当前请求的url不在被允许的请求url中
			String[] allowAddressArr = allowAddress.split(";");
			boolean allow = false;
			for(String allowUrl : allowAddressArr){
				if(url.endsWith(allowUrl)){
					allow = true;
					break;
				}
			}
			if(!allow){
				System.out.println("无登录用户，禁止访问action，JSP等，将跳转回"+loginPage);
				// 拦截器的处理：  
				//处理session失效引起的超时
				//如果是XMLHttpRequest发起的ajax请求，将session置为超时
				if (servletRequest.getHeader("x-requested-with") != null  
				          && servletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					servletResponse.addHeader("sessionstatus", "timeout");  
					servletResponse.addHeader("loginPath", loginPage);
//					chain.doFilter(request, response);
				} else if(servletRequest.getHeader("Content-Type")!= null
						&&servletRequest.getHeader("Content-Type").contains("multipart/form-data")){
//					chain.doFilter(request, response);
					PrintWriter out = servletResponse.getWriter();
					out.println("<html>");
					out.println("<script type=\"text/javascript\">");
					out.println("window.open ('"+loginPage+"','_top')");
					out.println("</script>");
					out.println("</html>");
//					servletResponse.sendRedirect(loginPage);
					return;
				}else {  
				      /* 普通http请求session超时的处理 */  
					  servletResponse.sendRedirect(loginPage);
				}
			}else{
				chain.doFilter(request, response);
			}
		}else
			chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		application = filterConfig.getServletContext();
		contextPath = application.getContextPath();
		//初始化时将参数添入
		if(!"/".equals(contextPath)){
			loginPage = contextPath+filterConfig.getInitParameter("loginPage");
		}else{
			loginPage = filterConfig.getInitParameter("loginPage");
		}
		sessionKey = filterConfig.getInitParameter("sessionKey");
		allowAddress = filterConfig.getInitParameter("allowAddress");
	}

}
