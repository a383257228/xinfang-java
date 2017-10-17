package com.sinosoft.authority.web;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.domain.AuthorityUserRelation;
import com.sinosoft.authority.manager.AuthorityUserRelationManager;
import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.OperationType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
/**   
 * 项目名称：authority   
 * 类名称：AuthorityUserRelationAction   
 * 类描述：用户菜单权限action层
 * 创建人：sunzhe   
 * 创建时间：2010-08-10  
 */
@Namespace("/authority")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="authority-user-relation.action", type = "redirect")})
public class AuthorityUserRelationAction extends CrudExtActionSupport<AuthorityUserRelation, String> {
	
	private static final long serialVersionUID = 3715067652450193432L;
	
	/**
	 * 注入用户角色业务层
	 */
	@Autowired
	AuthorityUserRelationManager authorityUserRelationManager;
	
	/**
	 * 人员ＩＤ
	 */
	@AttLog(description="人员ID")
	private String userId;
	
	/**
	 * 功能菜单ＩＤ
	 */
	@AttLog(description="功能菜单ID")
	private String menuId;
	
	/**
	 * 父级功能菜单ＩＤ
	 */
	@AttLog(description="父级功能菜单ID")
	private String parentMenuId;	
	
	AuthorityUserRelation entity;
	
	/**
	 * 是否记录日志，当enabledLog为false时，不记录日志信息
	 */
	@AttLog(attType=AttType.LOG_ENABLE)
	private String enabledLog;
	
	
	/**
	 * 记录操作日志中按钮名称
	 */
	@AttLog(attType=AttType.BUTTON)
	private String btnName;
	
	/**
	 * 记录操作日志中操作描述
	 */
	@AttLog(attType=AttType.DESCRIPTION)
	private String desp;
	
	/**
	 * 记录操作日志中操作功能代码
	 */
	@AttLog(attType=AttType.FUNCTION_CODE)
	private String functionCode;
	
	/**
	 * 记录操作日志中操作功能名称
	 */
	@AttLog(attType=AttType.FUNCTION_NAME)
	private String functionName;
	
	/**
	 * 保存人员与功能菜单之间的关系
	 * @throws Exception
	 */
	public void saveUserRelation() throws Exception
	{
		authorityUserRelationManager.saveConfigRole(userId,menuId,parentMenuId);
	}
	
	@Override
	protected EntityManager<AuthorityUserRelation, String> getEntityManager() {
		return authorityUserRelationManager;
	}
	

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = authorityUserRelationManager.get(id);
		} else {
			entity = new AuthorityUserRelation();
		}
	}

	public AuthorityUserRelation getModel() {
		return entity;
	}

	/**
	 * @return the entity
	 */
	public AuthorityUserRelation getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(AuthorityUserRelation entity) {
		this.entity = entity;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getEnabledLog() {
		return enabledLog;
	}

	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
}
