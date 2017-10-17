package com.sinosoft.organization.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.manager.GroupItemInfoManager;
import com.sinosoft.organization.manager.OrganPersonRelationManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：organization   
 * 类名称：GroupInfoAction   
 * 类描述：组织机构分组action层
 * 创建人：wangxx   
 * 创建时间：2010-10-11
 */
@Namespace("/organization")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="group-item-info.action", type = "redirect")})
public class GroupItemInfoAction extends CrudExtActionSupport<GroupItemInfo, String> {
	
	private static final long serialVersionUID = 1L;
	//注入组织机构分组信息业务层
	@Autowired
	GroupItemInfoManager groupItemInfoManager;
	
	//注入机构用户关系业务层
	@Autowired
	OrganPersonRelationManager organPersonRelationManager;
	
	@Override
	protected EntityManager<GroupItemInfo, String> getEntityManager() {
		return groupItemInfoManager;
	}
	GroupItemInfo entity = new GroupItemInfo();
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = groupItemInfoManager.get(id);
		} else {
			entity = new GroupItemInfo();
		}
	}

	public GroupItemInfo getModel() {
		return entity;
	}
	

	/**
	 * 用于渲染修改分组信息中的用户列表信息
	 * @return 空
	 * @throws IOException 
	 */
	public void loadOrganGroupGrid() throws IOException{
		if(start==1){
			start=0;
		}
		String json = "";
		if(filterTxt==null||filterTxt.equals("")){
			//获取查询列表
			json = groupItemInfoManager.loadOrganGroupGrid(filterValue, start, limit);
		}else{
			json = organPersonRelationManager.queryPersonInfo(filterTxt, filterValue, start, limit,"","");
		}
		//返回页面列表
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
}
