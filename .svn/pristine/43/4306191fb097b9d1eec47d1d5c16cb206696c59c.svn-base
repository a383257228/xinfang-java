package com.sinosoft.xf.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.xf.query.common.JubaoException;

/**
 * 过滤非法refer发送的请求
 * 
 * @author garview
 * 
 *         2015年11月9日下午4:10:51
 */
public class ReferFilter implements Filter {
	public FilterConfig config;
	
	/**
	 * ip配置文件
	 */
	public static Properties ipConfig ;
	
	static {
		if(ipConfig==null){
			ipConfig = new Properties();
			InputStream in = ReferFilter.class.getResourceAsStream("/ipconfig.properties");
			try {
				ipConfig.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {

					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest servletrequest = (HttpServletRequest) request;
		HttpServletResponse servletresponse = (HttpServletResponse) response;
		if(isValidRefer(servletrequest)){
//			System.out.println("refer filter running!!!!!!!!!!"+servletrequest.getHeader("Referer"));
			filterChain.doFilter(servletrequest, servletresponse);
		}else {
//			throw new JubaoException("非法refer的请求");
			servletresponse.getWriter().print("非法refer的请求");
		}
	}
	
	/**
	 * refer校验规则：1. 来自本站的请求  2. 来自指定合法网站的请求
	 * @param request
	 * @return
	 * @throws IOException
	 * @author garview
	 * 2015年11月9日 下午4:49:51
	 */
	public boolean isValidRefer(HttpServletRequest request) throws IOException{
		
		String refer = request.getHeader("Referer");
		return true;
//		if(StringUtils.isBlank(refer))
//			return false;
//		String accessUrl = ipConfig.getProperty("accessUrl");
//		if(refer.startsWith(accessUrl)){
//			return true;
//		}
//		String validRefer = ipConfig.getProperty("validRefer");
//		String[] validRefers = validRefer.split(",");
//		for(String s : validRefers){
//			if(refer.startsWith(s.trim()))
//				return true;
//		}
//		return false;
	}

	public void destroy() {
		this.config = null;
	}
}
