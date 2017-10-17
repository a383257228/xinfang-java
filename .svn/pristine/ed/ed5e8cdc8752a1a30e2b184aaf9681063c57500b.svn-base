package com.sinosoft.authority.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.manager.AuthorityDeptRoleManager;
import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.OperationType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.organization.manager.OrganPersonRelationManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：authority   
 * 类名称：AuthorityDeptRoleAction   
 * 类描述：部门角色action层
 * 创建人：sunzhe   
 * 创建时间：2010-08-10  
 */
@Namespace("/authority")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="authority-dept-role.action", type = "redirect")})
public class AuthorityDeptRoleAction extends CrudExtActionSupport<AuthorityDeptRole, String> {
	
	private static final long serialVersionUID = 8564346955919258682L;

	/**
	 * 注入部门角色业务层
	 */
	@Autowired
	AuthorityDeptRoleManager authorityDeptRoleManager;
	
	/**
	 * 人员部门关系业务层
	 */
	@Autowired
	OrganPersonRelationManager organPersonRelationManager;
	
	AuthorityDeptRole entity;
	
	@AttLog(description="树节点ID")
	private String node;//树节点ＩＤ
	
	@AttLog(description="角色ID")
	private String roleId;//角色ＩＤ
	
	@AttLog(description="功能菜单ID")
	private String menuId;//功能菜单ID
	
	@AttLog(description="功能菜单Code")
	private String menuEName;//功能菜单Code
	
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
	 * 分部加载树
	 * 用于角色加载组织机构-带有复选框
	 */
	public void buildAuthorityDeptTree() throws IOException, Exception {
		String treejson = authorityDeptRoleManager.buildAuthorityDeptTree(node,true,roleId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 分部加载树,根据功能菜单反查组织机构
	 * 用于角色加载组织机构-带有复选框
	 */
	public void buildAuthorityDeptTreeByMenuId() throws IOException, Exception {
		String treejson = authorityDeptRoleManager.buildAuthorityDeptTreeByMenuId(node,true,menuId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 分部加载树,根据功能菜单反查组织机构
	 * 用于角色加载组织机构-带有复选框
	 */
	public void buildAuthorityDeptTreeByMenu() throws IOException, Exception {
		String treejson = authorityDeptRoleManager.buildAuthorityDeptTreeByMenu(node,true,menuEName);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = authorityDeptRoleManager.get(id);
		} else {
			entity = new AuthorityDeptRole();
		}
	}

	public AuthorityDeptRole getModel() {
		return entity;
	}
	
	@Override
	protected EntityManager<AuthorityDeptRole, String> getEntityManager() {
		return authorityDeptRoleManager;
	}
	
	public String getMenuEName() {
		return menuEName;
	}

	public void setMenuEName(String menuEName) {
		this.menuEName = menuEName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	/**
	 * @return the entity
	 */
	public AuthorityDeptRole getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(AuthorityDeptRole entity) {
		this.entity = entity;
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
