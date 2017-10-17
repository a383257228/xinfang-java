package com.sinosoft.authority.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.manager.PetitionAuthorityMenuManager;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 项目名称：中纪委信访2期
 * 类名称：PetitionAuthorityMenuAction
 * 类描述：信访系统菜单权限信息action层
 * 创建人：sunzhe
 * 创建时间：2012-01-28
 */
@Namespace("/system")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({ @Result(name = CrudActionSupport.RELOAD,
        location = "petition-authority-menu.action", type = "redirect") })
public class PetitionAuthorityMenuAction
        extends CrudExtActionSupport<AuthorityMenu, String> {
	private static final long serialVersionUID = 1L;
	
	//注入系统代码权限处理业务层
	@Autowired
	PetitionAuthorityMenuManager petitionAuthorityMenuManager;

	private AuthorityMenu entity = new AuthorityMenu();
	/**
	 * 通过用户获取用户菜单权限信息，并且将菜单信息放到session共享范围
	 * @throws Exception
	 */
	public void getMenuWithAuthorityByPetition() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		PersonInfo personInfo = (PersonInfo) session.getAttribute("personInfo");
		//用于存储该用户已有的权限菜单
		Set<String> menuSet = new HashSet<String>();
		session.setAttribute("authorityMenuSet", menuSet);
		String menuStr = petitionAuthorityMenuManager.getMenuWithAuthorityByPetition(personInfo);
	    Struts2Utils.getResponse().getWriter().print(menuStr);
	}
	
	private String platformHomePage;
	/**
	 * 设置wld平台主页
	 * @author hjh
	 * @param 
	 * @return
	 * 2014-7-21下午01:02:22
	 */
	public void setWldHomePage() throws IOException{
		try{
			if(!StringUtils.isBlank(platformHomePage)){
				PersonInfo personInfo = petitionAuthorityMenuManager.setWldHomePage(platformHomePage);
				HttpServletRequest request = Struts2Utils.getRequest();
				HttpSession session = request.getSession();
				session.setAttribute("personInfo", personInfo);
				Struts2Utils.getResponse().getWriter().print("success");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = petitionAuthorityMenuManager.get(id);
		} else {
			entity = new AuthorityMenu();
		}
	}
	@Override
	public AuthorityMenu getModel() {
		return entity;
	}
	@Override
	protected EntityManager<AuthorityMenu, String> getEntityManager() {
		return petitionAuthorityMenuManager;
	}
	public String getPlatformHomePage() {
		return platformHomePage;
	}
	public void setPlatformHomePage(String platformHomePage) {
		this.platformHomePage = platformHomePage;
	}
	
}
