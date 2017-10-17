package com.sinosoft.authority.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.manager.AuthorityMenuManager;
import com.sinosoft.authority.manager.AuthorityRoleManager;
import com.sinosoft.authority.manager.AuthorityUserRoleManager;
import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.OperationType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：authority   
 * 类名称：AuthorityMenuAction   
 * 类描述：菜单功能权限action层
 * 创建人：sunzhe   
 * 创建时间：2010-08-10  
 */
@Namespace("/authority")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="authority-menu.action", type = "redirect")})
public class AuthorityMenuAction extends CrudExtActionSupport<AuthorityMenu, String> {
	
	private static final long serialVersionUID = 6802044997931914991L;
	
	/**
	 * 注入菜单功能权限业务层
	 */
	@Autowired
	AuthorityMenuManager authorityMenuManager;
	
	/**
	 * 注入角色业务层
	 */
	@Autowired
	AuthorityRoleManager authorityRoleManager;
	
	/**
	 * 注入用户角色信息业务层
	 */
	@Autowired
	AuthorityUserRoleManager authorityUserRoleManager;
	
	/**
	 * 注入用户信息业务层
	 */
	@Autowired
	PersonInfoManager personInfoManager;
	
	/**
	 * 用于判断按钮权限的菜单英文名
	 */
	@AttLog(description="菜单英文名")
	private String itemEName;
	
	/**
	 * 树节点ＩＤ
	 */
	@AttLog(description="树节点ID")
	private String node;
	
	/**
	 * 角色ＩＤ
	 */
	@AttLog(description="角色ID")
	private String roleId;
	
	/**
	 * 用户ＩＤ
	 */
	@AttLog(description="用户ID")
	private String userId;
	
	/**
	 * 系统ID，对应菜单功能权限
	 */
	@AttLog(description="系统ID")
	private String systemId;
	
	/**
	 * 已选中用户ID
	 */
	@AttLog(description="已选中用户ID")
	private String checkedUserId;
	
	/**
	 * 是否记录日志，当enabledLog为false时，不记录日志信息
	 */
	@AttLog(attType=AttType.LOG_ENABLE)
	private String enabledLog;
	
	AuthorityMenu entity;
	
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
	private String testAuthorityRoleId;
	/**
	 * @see 通过存放在onlineUser中的person对象生成菜单
	 * @date 2011-05-03
	 * @author wangxx
	 * 用途：
	 * 	1、用于目录系统
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void getMenuWithAuthorityByDir() throws IOException, ParseException {
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//根据人员生成菜单
		String menuStr = authorityMenuManager.getMenuJson(personInfoManager.get(onlineUser.getUserId()),systemId);
		Struts2Utils.getResponse().getWriter().print(menuStr);
	}
	
	/**
	 * 通过存放在onlineUser中的person对象生成菜单
	 * @date 2011-11-16
	 * @author sunzhe
	 * * 用途：
	 * 1、用于信访系统
	 * 2、重新定义了菜单样式
	 * @throws Exception 
	 */
	public void getMenuWithAuthorityByPetition() throws Exception{
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//根据人员生成菜单
		String menuStr = authorityMenuManager.getMenuWithAuthorityByPetition(personInfoManager.get(onlineUser.getUserId()));
		Struts2Utils.getResponse().getWriter().print(menuStr);
	}
	/**
	 * 根据用户得到所有按钮权限
	 * @throws IOException
	 * @throws Exception
	 */
	public void authoirtyButton()throws IOException, Exception{
		StringBuffer userInfoStr = new StringBuffer("");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(onlineUser!=null){
			//根据人员得到按钮权限信息
			List<String> list = authorityMenuManager.buildUserButton(personInfoManager.get(onlineUser.getUserId()));
			boolean firstItem = true;
			for(String name : list){
				if(!firstItem){
					userInfoStr.append(";");
				}
				userInfoStr.append(name);
				if(firstItem){
					firstItem = false;
				}
			}
		}
		Struts2Utils.getResponse().getWriter().print(userInfoStr.toString());
	}

	/**
	 * 根据登录用户角色得到该角色下所有按钮权限
	 * @throws IOException
	 * @throws Exception
	 */
	public void authoirtyButtonWithRole()throws IOException, Exception{
		StringBuffer userInfoStr = new StringBuffer("");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(onlineUser!=null){
			//根据人员当前登录角色得到按钮权限信息
			List<String> list = authorityMenuManager.buildUserButtonWithRole(onlineUser.getOnlineRoleId());
			boolean firstItem = true;
			for(String name : list){
				if(!firstItem){
					userInfoStr.append(";");
				}
				userInfoStr.append(name);
				if(firstItem){
					firstItem = false;
				}
			}
		}
		Struts2Utils.getResponse().getWriter().print(userInfoStr.toString());
	}
	
	/**
	 * 根据用户得到所有可访问的url地址
	 * @throws IOException
	 * @throws Exception
	 */
	public void getAuthorityUrl()throws IOException, Exception{
		StringBuffer userInfoStr = new StringBuffer("");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(onlineUser!=null){
			//根据人员得到可访问路径权限信息
			List<String> list = authorityMenuManager.buildUserUrlAuthority(personInfoManager.get(onlineUser.getUserId()));
			boolean firstItem = true;
			for(String name : list){
				if(!firstItem){
					userInfoStr.append(";");
				}
				userInfoStr.append(name);
				if(firstItem){
					firstItem = false;
				}
			}
		}
		Struts2Utils.getResponse().getWriter().print(userInfoStr.toString());
	}
	/**
	 * 通过存放在onlineUser中的person对象生成菜单
	 * @throws IOException 
	 */
	public void getMenuWithAuthority() throws Exception{
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//根据人员生成菜单
		String menuStr = authorityMenuManager.getMenuWithAuthority(personInfoManager.get(onlineUser.getUserId()));
		Struts2Utils.getResponse().getWriter().print(menuStr);
	}

	/**
	 * 通过传入的filterValue参数生成菜单，filterValue中必须是角色ID
	 * @throws IOException 
	 * @throws ParseException 
	 */
	/*@MethodLog(name="getMenuWithRole",description="生成菜单",operationType=OperationType.QUERY)
	public void getMenuWithRole() throws IOException, ParseException{
		String roleIds = "";
		if(testAuthorityRoleId == null || "null".equals(testAuthorityRoleId)){
			//根据角色生成菜单
			PersonInfo personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
			roleIds = authorityUserRoleManager.queryRoleIdsByPerson(personInfo.getId());
		}else{
			roleIds = testAuthorityRoleId;
		}
		String menuStr = authorityMenuManager.getMenuWithRole(roleIds);
		Struts2Utils.getResponse().getWriter().print(menuStr);
	}*/
	
	public void getAuthorityMenuTree() throws IOException, Exception {
		String treejson = authorityMenuManager.buildAuthorityMenuTree(node, true,roleId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 查找用户所具有的所有功能权限,显示在树上，分级加载，不包含未选信息,不带选择框
	 * 用途：
	 * 	1、目录系统。
	 * @throws IOException
	 * @throws Exception
	 */
	public void getUserAuthorityMenuTreeByDir() throws IOException, Exception {
		String treejson = authorityMenuManager.getUserAuthorityMenuTree(node, true,userId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 查找用户所具有的所有功能权限,显示在树上，不包含未选信息,带选择框
	 * @throws IOException
	 * @throws Exception
	 */
	public void getUserAuthorityMenuTreeChecked() throws IOException, Exception {
		String treejson = authorityMenuManager.getUserAuthorityMenuTreeChecked(node, true,userId,checkedUserId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 查找用户所具有的所有功能权限,显示在树上，不包含未选信息，不带选择框
	 * @throws IOException
	 * @throws Exception
	 */
	public void getUserAuthorityMenuTree() throws IOException, Exception {
		String treejson = authorityMenuManager.getUserAuthorityMenuTreeChecked(node, false,userId,checkedUserId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 显示角色所具有的功能菜单项
	 */
	public void showAuthorityMenuTree() throws IOException, Exception {
		String treejson = authorityMenuManager.showAuthorityMenuTree(node, roleId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = authorityMenuManager.get(id);
		} else {
			entity = new AuthorityMenu();
		}
	}
	
	public AuthorityMenu getModel() {
		return entity;
	}
	
	public String getCheckedUserId() {
		return checkedUserId;
	}

	public void setCheckedUserId(String checkedUserId) {
		this.checkedUserId = checkedUserId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getItemEName() {
		return itemEName;
	}

	public void setItemEName(String itemEName) {
		this.itemEName = itemEName;
	}

	@Override
	protected EntityManager<AuthorityMenu, String> getEntityManager() {
		return authorityMenuManager;
	}

	/**
	 * @return the entity
	 */
	public AuthorityMenu getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(AuthorityMenu entity) {
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

	public String getTestAuthorityRoleId() {
		return testAuthorityRoleId;
	}

	public void setTestAuthorityRoleId(String testAuthorityRoleId) {
		this.testAuthorityRoleId = testAuthorityRoleId;
	}
	
}
