/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 17 2010
 * @author shenhy
 */
package com.sinosoft.organization.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.domain.OperationDataLog;
import com.sinosoft.log.domain.OperationLog;
import com.sinosoft.log.manager.OperationLogManager;
import com.sinosoft.organization.common.Encript;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganPersonRelationManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 提供机构用户的删除、查找用户等功能
 * @author shenhy May 17 2010
 */
@Namespace("/organperson")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results({@Result(name=CrudActionSupport.RELOAD,location="organ-person-relation.action",type = "redirect")})
public class OrganPersonRelationAction extends CrudExtActionSupport<OrganPersonRelationInfo, String>{
	private static final long serialVersionUID = 1L;
	/**
	 * 页面查询参数
	 */
	private String selectValue;
	
	//注入机构用户关系业务层
	@Autowired
	OrganPersonRelationManager organPersonRelationManager;
	/**
	 * 操作日志service用于手动记录操作日志
	 */
	@Autowired
	OperationLogManager operationLogManager;
	//机构用户关系实体
	OrganPersonRelationInfo entity = new OrganPersonRelationInfo();

	private String node;//节点信息
	
	private String organRelaOid;//机构关系表ＩＤ

	private String organPersonRelationOrganizationOid;//人员机构关系ＩＤ
	
	private String orgCode;//机构编码
	
	private String flag;//是否含有选择框

	private String showType;//显示类型，只显示用户person，只显示机构organ，显示全部all
	
	private String orgNameType;//组织机构名称   orgCname全称  orgShortCname简称
	//按钮名称
	@AttLog(attType=AttType.BUTTON)
	private String btnName;
	//操作描述
	@AttLog(attType=AttType.DESCRIPTION)
	private String desp;
	//操作功能代码
	@AttLog(attType=AttType.FUNCTION_CODE)
	private String functionCode;
	//操作功能名称
	@AttLog(attType=AttType.FUNCTION_NAME)
	private String functionName;
	//操作登记代码
	@AttLog(attType=AttType.LEVEL_CODE)
	private String levelCode;
	//操作登记名称
	@AttLog(attType=AttType.LEVEL_NAME)
	private String levelName;
	//操作类型代码
	@AttLog(attType=AttType.TYPE_CODE)
	private String typeCode;
	//操作类型名称
	@AttLog(attType=AttType.TYPE_NAME)
	private String typeName;
	//操作结果代码
	private String operationResultCode;
	//操作结果名称
	private String operationResultName;
	
	/**
	 * 是否记录日志，当enabledLog为false时，不记录日志信息
	 */
	@AttLog(attType=AttType.LOG_ENABLE)
	private String enabledLog;
	
	/**
	 * 通过属性查询机构用户关系信息
	 * @date 2011-04-19
	 * @author wangxx
	 * 用途：
	 * 	1、目录项目
	 * @return 空
	 * @throws Exception
	 */
	public void organRelaPersonPagedQuery() throws Exception {
		ExtjsPage<OrganPersonRelationInfo> page = organPersonRelationManager
			.organRelaPersonPagedQuery(filterTxt, filterValue, start, limit, sort, dir);
		page.setPageSize(this.limit);
		afterQuery(page);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(),
				getExcludes(), getDatePattern());
	}
	/**
	 * 分部加载组织人员树:中文简称，全部有效节点
	 * @date 2010-12-23
	 * @author wangxx
	 * 用途：
	 * 	1、组织用户设置，右侧列表树
	 * @return 空
	 */
	public void getOrganUserTreeByOrgShortCnameAndInvalidFlag() throws IOException, Exception {
		String treejson = organPersonRelationManager.getOrganUserTree(node,"orgShortCname","1");
		Struts2Utils.getResponse().getWriter().print(treejson);
	}

	/**
	 * 分部加载组织人员树:中文简称，全部有效节点
	 * @date 2010-12-23
	 * @author wangxx
	 * 用途：
	 * 	1、组织用户设置，右侧列表树
	 * @return 空
	 */
	public void getOrganUserTreeByOrgCnameAndInvalidFlag() throws IOException, Exception {
		String treejson = organPersonRelationManager.getOrganUserTree(node,"orgCname","1");
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 根据输入框内容查询人员信息  现用于组织分组添加分组的人员列表
	 * @author shenhy
	 * @return 空
	 * @throws IOException 
	 */
	public void queryPersonInfo() throws IOException{
		if(start==1){
			start=0;
		}
		if(null==filterValue||"".equals(filterValue)){
			Struts2Utils.getResponse().getWriter().print("{totalProperty:0,result:[]}");
		}else{
			//获取查询列表
			String json = organPersonRelationManager.queryPersonInfo(
					filterTxt, filterValue, start, limit,sort,dir);
			//返回页面列表
			Struts2Utils.getResponse().getWriter().print(json);
		}
	}
	/**
	 * 根据机构用户关系主键删除机构和人员之间的关系信息
	 * @date 2010-11-27
	 * @author wangxx
	 * @return 空
	 */
	public void deleteOrganPersonRelationInfo(){
		//获取页面参数
		String organPersonRelationInfoOid = this.selectValue;
		//删除数据
		organPersonRelationManager.deleteOrganPersonRelationInfo(organPersonRelationInfoOid);
	}
	/**
	 * 机构用户，通过部门oid查找person，形成树节点
	 * @date 2010-07-28
	 * @author wangxx 
	 * @return 空
	 * @throws IOException 
	 */
	public void getPersonByOrganizationOid() throws IOException{
		String treejson="";
		treejson=organPersonRelationManager.getOrganPersonRelationInfoByOrganizationOid(
				this.organPersonRelationOrganizationOid);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 分部加载列表树:中文简称，全部有效节点
	 * 调用哪些方法:
	 * 1、organPersonRelationManager.getOrganUserColumnsTree
	 * 被前台哪些模块调用:
	 * 1、Organ.User.organUserColumnTree 用户模块
	 * @date 2010-12-07
	 * @author wangxx
	 * 用途：
	 * 	1、组织用户设置，右侧列表树
	 * 更新1：
	 * 在原有基础上增加了查询功能
	 * @return 空
	 */
	public void getOrganUserColumnsTreeByOrgShortCnameAndInvalidFlag() throws IOException{
		String treejson = organPersonRelationManager.getOrganUserColumnsTree(
				node,"orgShortCname","1",filterValue);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 分部加载列表树:通过不同的参数控制输出的内容
	 * @date 2011-10-19
	 * @author wangxx
	 * 用途：
	 * 	1、组织用户设置，右侧列表树
	 * 更新1：
	 * 在原有基础上增加了查询功能
	 * @return 空
	 */
	public void getOrganUserColumnsTree() throws IOException{
		String treejson = organPersonRelationManager.getOrganUserColumnsTree(
				node,orgNameType,"1",filterValue,showType);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 分部加载列表树:中文名，全部有效节点
	 * @date 2010-12-07
	 * @author wangxx
	 * 用途：
	 * 	1、组织用户设置，右侧列表树
	 * 更新1：
	 * 在原有基础上增加了查询功能
	 * @return 空
	 */
	public void getOrganUserColumnsTreeByInvalidFlag() throws IOException, Exception {
		String treejson = organPersonRelationManager.getOrganUserColumnsTree(
				node,"orgCname","1",filterValue);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 组织机构：分级加载树判断是否有子节点,子节点为用户
	 * 该节点的下级是用户
	 * 用途：
	 * 1、在删除组织时判断该组织下级是否存在用户
	 * @return 空
	 */
	public void getOrgUserListSize() throws IOException {
		String flag = organPersonRelationManager.getOrgUserListSize(this.organRelaOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * 通过机构Oid返回其本级和下级的用户信息
	 * @return 空
	 * @throws IOException 
	 */
	public void getPersonJsonByOrgOid() throws IOException{
		String json = organPersonRelationManager.getPersonJsonByOrgOid(orgCode,flag);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 重写loadDate方法
	 * @date 2010-11-25
	 * @author wangxx
	 * 重写原因：由于加载时需要将用户的密码进行解密
	 * @return 空
	 */
	public void loadData() throws Exception{
		try {
			OrganPersonRelationInfo model = getEntityManager().get(this.id);
			if(model!=null&&model.getPersonInfo()!=null&&null!=model.getPersonInfo().getUserPwd()){
				model.getPersonInfo().setUserPwd(Encript.decript( model.getPersonInfo().getUserPwd()));
			}
		    if (model != null){
			      JsonUtils.write(model, Struts2Utils.getResponse().getWriter(), getExcludes(), getDatePattern());
		    }
		} catch (Exception e) {
		}finally{
			List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
			dataLogs.add(new OperationDataLog("组织用户关系唯一标示",this.id));
		}
	}
	/**
	 * loadData方法需要的参数，用于屏蔽一些不需要显示的参数，节省IO
	 * @date 2010-11-25
	 * @author wangxx
	 * @return 字符串数组
	 */
	public String[] getExcludes() {
		return new String[] { "hibernateLazyInitializer", "organizationRelationInfo","organMergeSplitInfo"
			,"organPersonRelationInfo","currentOrgan","description","orgShortCname"
			,"orgShortEname","organType","purpose"};
	}
	/**
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}
	/**
	 * @param organRelaOid the organRelaOid to set
	 */
	public void setOrganRelaOid(String organRelaOid) {
		this.organRelaOid = organRelaOid;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @param organPersonRelationOrganizationOid the organPersonRelationOrganizationOid to set
	 */
	public void setOrganPersonRelationOrganizationOid(
			String organPersonRelationOrganizationOid) {
		this.organPersonRelationOrganizationOid = organPersonRelationOrganizationOid;
	}
	/**
	 * @param showType the showType to set
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}
	/**
	 * @param orgNameType the orgNameType to set
	 */
	public void setOrgNameType(String orgNameType) {
		this.orgNameType = orgNameType;
	}
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = organPersonRelationManager.get(id);
		} else {
			entity = new OrganPersonRelationInfo();
		}
	}
	@Override
	protected EntityManager<OrganPersonRelationInfo, String> getEntityManager() {
		return organPersonRelationManager;
	}
	public OrganPersonRelationInfo getModel() {
		return entity;
	}
	/**
	 * 注入页面查询参数
	 * @param selectValue
	 * 			页面查询参数
	 * @author shenhy
	 * 2010-5-26
	 */
	public void setSelectValue(String selectValue){
		this.selectValue = selectValue; 
	}
	/**
	 * 设置操作日志基础信息
	 * @param log 操作日志对象
	 */
	private void setOperaLogInfo(OperationLog log) {
		log.setOperationFuncCode(getFunctionCode());
		log.setOperationFuncName(getFunctionName());
		log.setOperationBtnName(getBtnName());
		log.setOperationDesc(getDesp());
		log.setOperationLevelCode(getLevelCode());
		log.setOperationLevelName(getLevelName());
		log.setOperationTypeCode(getTypeCode());
		log.setOperationTypeName(getTypeName());
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
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getOperationResultCode() {
		return operationResultCode;
	}
	public void setOperationResultCode(String operationResultCode) {
		this.operationResultCode = operationResultCode;
	}
	public String getOperationResultName() {
		return operationResultName;
	}
	public void setOperationResultName(String operationResultName) {
		this.operationResultName = operationResultName;
	}
	public String getEnabledLog() {
		return enabledLog;
	}
	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}
	
}
