package com.sinosoft.authority.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.authority.manager.AuthorityRoleManager;
import com.sinosoft.authority.manager.AuthorityUserRoleManager;
import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.OperationType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.organization.manager.OrganPersonRelationManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：authority   
 * 类名称：AuthorityUserRoleAction   
 * 类描述：用户角色action层
 * 创建人：sunzhe   
 * 创建时间：2010-08-10  
 */
@Namespace("/authority")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="authority-user-role.action", type = "redirect")})
public class AuthorityUserRoleAction extends CrudExtActionSupport<AuthorityUserRole, String> {
	
	private static final long serialVersionUID = 4445283048948523656L;

	/**
	 * 注入用户角色业务层
	 */
	@Autowired
	AuthorityUserRoleManager authorityUserRoleManager;

	/**
	 * 注入用户角色业务层
	 */
	@Autowired
	AuthorityRoleManager authorityRoleManager;
	
	/**
	 * 注入机构用户关系业务层
	 */
	@Autowired
	OrganPersonRelationManager organPersonRelationManager;
	
	AuthorityUserRole entity;
	
	/**
	 * 角色ＩＤ
	 */
	@AttLog(description="角色ID")
	private String roleId;
	
	/**
	 * 人员ＩＤ
	 */
	@AttLog(description="人员ID")
	private String userId;
	
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
	
	//标示新增、修改、删除
	private String flag;
	//可以访问的系统编码	jingwen  2016-07-02
	private String systemAuthorityCode;
	
	/**
	 * 新增时保存用户角色关联信息
	 * 修改时先删除用户角色关联信息，在新增
	 * @throws Exception
	 */
	public void saveUserRoleDate() throws Exception{
		authorityUserRoleManager.saveUserRoleDate(userId, data,flag,systemAuthorityCode,entity.getRegionCode());
	}
	
	/**
	 * 根据输入框内容查询人员信息
	 * @throws IOException 
	 *				返回页面出现异常，则抛出异常
	 */
	public void queryAuthorityUser() throws IOException{

		if(start==1){
			start=0;
		}
		//判断是否是修改配置项，如果是修改权限配置，则提供上次配置时的人员列表
		if(filterTxt==null||filterTxt.equals("")){
			List<AuthorityUserRole> list = authorityUserRoleManager.findBy("role.id",roleId);
			filterValue = "";
			for(int i=0;i<list.size();i++){
				AuthorityUserRole userRole = list.get(i);
				if(i>0)
					filterValue += ",";
				filterValue += "'"+userRole.getPerson()+"'";
			}
			if(!filterValue.equals(""))
				filterTxt = "ids";
		}else{
			if(filterValue==null||filterValue.equals("")){
				filterTxt="";
			}
		}
		//获取查询列表
		String json = authorityUserRoleManager.queryAuthorityUser(filterTxt, filterValue, start, limit,sort,dir);
		//返回页面列表
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 根据输入框内容查询人员信息,该方法用于角色查询功能
	 * @throws IOException 
	 *				返回页面出现异常，则抛出异常
	 */
	public void queryAuthorityUserList() throws IOException{
		if(filterValue==null||filterValue.equals("")){
			filterTxt="";
		}else if(filterTxt!=null&&filterTxt.equals("roleId")){
			filterTxt = "ids";
			AuthorityRole role = authorityRoleManager.get(filterValue);
			Set<AuthorityUserRole> set = role.getRolePerson();
			filterValue = "";
			for(AuthorityUserRole aur :set){
				filterValue += "'"+aur.getPerson()+"',";
			} 
			if(filterValue.endsWith(","))
				filterValue = filterValue.substring(0, filterValue.length()-1);
		}

		//获取查询列表
		String json = authorityUserRoleManager.queryAuthorityUser(filterTxt, filterValue, start, limit, sort,dir);

		//返回页面列表
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 查询人员与角色关系集合
	 * @throws Exception
	 */
	public void queryAuthorityUserByFilter() throws Exception{
		List<AuthorityUserRole> list = authorityUserRoleManager.findBy(filterTxt,filterValue);
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), new String[]{"hibernateLazyInitializer","handler"
        	,"role","organizationInfo","personInfo","organPersonRelationInfo"}, getDatePattern());
	}
	/**
	 * 修改人员角色对应关系数据
	 * @throws Exception
	 */
	public void updateUserRoleDate() throws Exception{
		authorityUserRoleManager.updateUserRoleDate(userId, data);
	}
	
	/**
	 * 删除人员角色信息
	 * @throws Exception
	 */
	public void removeUserRole() throws Exception{
		authorityUserRoleManager.removeUserRole(roleId, userId);
		
	}
	
	/**
	 * 查询区域下的所有用户及用户对应的角色
	 */
	public void queryAuthorityUserAndRoleList() throws Exception{
		String json=authorityUserRoleManager.queryAuthorityUserAndRoleList(filterTxt, filterValue,start, limit, sort,dir);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 新增时显示登录用户所属区域及所属区域下的所有子区域
	 * @throws Exception
	 */
	public void getChildOrgRelaInfoList() throws Exception{
		String json=authorityUserRoleManager.getChildOrgRelaInfoListByRegion(userId,roleId);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 查询用户角色关联信息
	 * @throws Exception
	 */
	public void loadUserRole() throws Exception{
		String json=authorityUserRoleManager.loadUserRole(filterValue);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	@Override
	protected EntityManager<AuthorityUserRole, String> getEntityManager() {
		return authorityUserRoleManager;
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

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = authorityUserRoleManager.get(id);
		} else {
			entity = new AuthorityUserRole();
		}
	}

	public AuthorityUserRole getModel() {
		return entity;
	}

	/**
	 * @return the entity
	 */
	public AuthorityUserRole getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(AuthorityUserRole entity) {
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSystemAuthorityCode() {
		return systemAuthorityCode;
	}

	public void setSystemAuthorityCode(String systemAuthorityCode) {
		this.systemAuthorityCode = systemAuthorityCode;
	}
	
	
}
