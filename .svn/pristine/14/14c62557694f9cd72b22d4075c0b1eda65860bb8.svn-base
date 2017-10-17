/**
 * Copyright (c) sinosoft 2010
 * 中科软科技股份有限公司 行业推广
 * com.sinosoft.authority.filter
 * Create Date 2010-12-1
 * @author Sunzhe
 */
package com.sinosoft.xf.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.utils.app.AppUtils;
/**
 * 在用户操作action时将操作人信息保存到数据库中
 * @date 2011-06-10
 * @author wangxx
 */
public class OnlineUserFilter implements Filter{
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			System.out.println("进入过滤器");
			//HttpServletRequest servletRequest = (HttpServletRequest) request;
			System.out.println("name-------------------------"+request.getParameter("personInfoEname"));
			//Map map1 = servletRequest.getParameterMap();
			HttpSession session = ((HttpServletRequest) request).getSession();
			String modeId = request.getParameter("modeId");
			System.out.println(modeId);
			String personInfoEname = request.getParameter("personInfoEname");
			System.out.println("进入过滤器---ename1："+personInfoEname);
			System.out.println("进入过滤器---ename2："+request.getAttribute("personInfoEname"));
			if(null!=personInfoEname&&!"".equals(personInfoEname)){
				PersonInfoManager personInfoManager = AppUtils.getBean("personInfoManager");
				PersonInfo person = personInfoManager.getPersonInfoByPersonEnameIm(personInfoEname);
				System.out.println("进入过滤器---cname："+person.getUserCname());
				//将用户的登录信息添加到session中 wangxx 2011-06-09 start
				if (!(AppUtils.getOnlineUsers().containsKey(session.getId()))) {
					OnlineUser onlineUser = new OnlineUser();
					onlineUser.setUserCName(person.getUserCname());
					onlineUser.setSessionId(session.getId());
					onlineUser.setUserEName(person.getUserEname());
					onlineUser.setUserId(person.getId());
					onlineUser.setDepCName("");
					onlineUser.setDepEName("");
					AppUtils.getOnlineUsers().put(session.getId(), onlineUser);
				}
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
