package com.sinosoft.authority.web;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.manager.AuthorityDataManager;
import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.OperationType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.organization.manager.OrganizationInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：authority   
 * 类名称：AuthorityDataAction   
 * 类描述：数据权限action层
 * 创建人：sunzhe   
 * 创建时间：2010-08-10  
 */
@Namespace("/authority")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="authority-data.action", type = "redirect")})
public class AuthorityDataAction extends CrudExtActionSupport<AuthorityData, String> {
	
	private static final long serialVersionUID = -1904490125540150376L;

	/**
	 * 注入数据权限业务层
	 */
	@Autowired
	AuthorityDataManager authorityDataManager;
	
	/**
	 * 注入组织机构业务层
	 */
	@Autowired
	OrganizationInfoManager organizationInfoManager;
	
	AuthorityData entity;
	
	@AttLog(description="角色ID")
	private String roleId;//角色ＩＤ
	
	@AttLog(description="库表ID")
	private String tableId;//库表ＩＤ
	
	@AttLog(description="字段ID")
	private String fieldId;//字段ＩＤ
	
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
	 * 用于库表字段显示在配置数据权限页上
	 */
	public void buildAuthorityDatabaseTree() throws IOException, Exception {
		String treejson = authorityDataManager.buildAuthorityDatabaseTree();
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 保存数据权限信息
	 * @throws Exception
	 */
	public void saveData() throws Exception
	{
		authorityDataManager.saveData(getModel(), roleId, tableId, fieldId);
	    Struts2Utils.getResponse().getWriter().print("{success:true}");
	}
	
	/**
	 * 提取已保存的数据权限信息
	 * @throws Exception
	 */
	public void loadSavedData() throws Exception{
		AuthorityData authorityData = authorityDataManager.loadSavedData(roleId, tableId, fieldId);
		if(authorityData != null){
            JsonUtils.write(authorityData, Struts2Utils.getResponse().getWriter(), new String[]{"hibernateLazyInitializer","handler"
            	,"role","tableInfo","fieldInfo"}, getDatePattern());
		}else{
			JsonUtils.write(new AuthorityData(), Struts2Utils.getResponse().getWriter(), new String[]{"hibernateLazyInitializer","handler"
            	,"role","tableInfo","fieldInfo"}, getDatePattern());
		}
	}
	
	/**
	 * 构建数据权限列表
	 * @throws Exception 
	 */
	public void buildAuthorityGridData() throws Exception  {
		if(filterTxt!=null&&filterTxt.equals("roleId")){
			roleId = filterValue;
		}
		String json = authorityDataManager.buildAuthorityGridData(roleId);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 构建数据权限列表
	 * @throws IOException 
	 * @throws IOException 
	 */
	public void showRoleAuthorityGridData() throws IOException   {
		if(filterTxt!=null&&filterTxt.equals("roleId")){
			roleId = filterValue;
		}
		String json = authorityDataManager.showRoleAuthorityGridData(roleId);
		Struts2Utils.getResponse().getWriter().print(json);
		
	}
	
	/**
	 * 保存数据权限列表中的数据
	 * @throws Exception
	 */
	public void saveGridData() throws Exception {
		List<AuthorityData> objList = JsonUtils.json2List(data,
				AuthorityData.class);
		authorityDataManager.saveGridData(objList,roleId);		
		Struts2Utils.getResponse().getWriter().print("{success:true}");
	}

	/**
	 * 根据用户已分配权限构建数据权限列表
	 * @throws IOException
	 * @throws Exception
	 */
	public void userAuthorityGridData() throws IOException, Exception {
		String userId = "";
		if(filterTxt!=null&&filterTxt.equals("userId")){
			userId = filterValue;
		}
		String json = authorityDataManager.userAuthorityGridData(userId);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	@Override
	protected EntityManager<AuthorityData, String> getEntityManager() {
		return authorityDataManager;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = authorityDataManager.get(id);
		} else {
			entity = new AuthorityData();
		}
	}

	public AuthorityData getModel() {
		return entity;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the entity
	 */
	public AuthorityData getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(AuthorityData entity) {
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
