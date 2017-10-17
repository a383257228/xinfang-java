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
import com.sinosoft.organization.domain.OrganMergeSplitInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.manager.OrganMergeSplitInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 提供机构用户的新建、修改、查找用户等功能
 * @author shenhy
 */
@Namespace("/organization")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results({@Result(name=CrudActionSupport.RELOAD,location="organ-merge-split-info.action",type = "redirect")})
public class OrganMergeSplitInfoAction extends CrudExtActionSupport<OrganMergeSplitInfo, String> {
	private static final long serialVersionUID = 1L;

	//注入机构合并，拆分信息业务层
	@Autowired
	OrganMergeSplitInfoManager organMergeSplitInfoManager;

	/**
	 * 操作日志service用于手动记录操作日志
	 */
	@Autowired
	OperationLogManager operationLogManager;
	
	//定义人员关系对象实体
	OrganMergeSplitInfo entity = new OrganMergeSplitInfo();

	private String organIds;//待合并的机构oid
	
	private String organRelaOid;//新上级组织
	
	private String mergeType;//合并类型
	
	private String parentOrganizationRelationOids;//拆分后的上级组织
	
	private String childrensOrganizationRelationOids;//拆分后的下级组织
	
	private String personOids;//拆分部门时所分陪得用户信息
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
	 * 合并组织机构和部门
	 * @date 2010-11-24
	 * @author wangxx
	 * 根据新的需求，合并时分为三种情况，保留建制合并（complete），保留下级合并（part），不保留建制合并（unComplete）
	 * 保留建制合并（complete）：将本组织及下级的子组织保留
	 * 保留下级合并（part）：本组织将被删除，下面的子组织保留，本组织下的人员将被分配到新的上级组织下
	 * 不保留建制合并（unComplete）：本组织及下面的子组织将被删除，本组织以子组织下面的人员被分配到新的上级组织下
	 * @return 空
	 * @throws IOException 
	 */
	public void insertOrganMergeInfo() throws IOException{
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			String flag = organMergeSplitInfoManager.insertOrganMergeInfo(this.mergeType,this.organIds,this.organRelaOid);
			Struts2Utils.getResponse().getWriter().print(flag);
			if(enabledLogFlag){
				log.setOperationResultCode("1");
				log.setOperationResultName("成功");
			}
		} catch (Exception e) {
			if(enabledLogFlag){
				log.setOperationResultCode("2");
				log.setOperationResultName("失败");
			}
		}finally{
			if(enabledLogFlag){
				List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
				dataLogs.add(new OperationDataLog("合并类型",this.mergeType));
				dataLogs.add(new OperationDataLog("合并的组织标示",this.organIds));
				dataLogs.add(new OperationDataLog("合并后的组织关系标示",this.organRelaOid));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
	}
	
	/**
	 * 保存拆分机构信息
	 * @return 空
	 * @throws Exception 
	 */
	public void saveOrganSplitInfo() throws Exception{
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			//对JSON数据进行转换，将JSON数据组转换成对象集合
			List<OrganizationInfo> organizationInfoList = JsonUtils.json2List(data,OrganizationInfo.class);
			organMergeSplitInfoManager.saveOrganSplitInfo(organizationInfoList,parentOrganizationRelationOids,childrensOrganizationRelationOids,organRelaOid);
			if(enabledLogFlag){
				log.setOperationResultCode("1");
				log.setOperationResultName("成功");
			}
		} catch (Exception e) {
			if(enabledLogFlag){
				log.setOperationResultCode("2");
				log.setOperationResultName("失败");
			}
		}finally{
			if(enabledLogFlag){
				List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
				dataLogs.add(new OperationDataLog("本级机构标示",parentOrganizationRelationOids));
				dataLogs.add(new OperationDataLog("描述",childrensOrganizationRelationOids));
				dataLogs.add(new OperationDataLog("唯一标示",organRelaOid));
				dataLogs.add(new OperationDataLog("有效标示",data));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
	}
	
	/**
	 * 保存拆分部门信息
	 * @return 空
	 * @throws Exception 
	 */
	public void saveDeptSplitInfo() throws Exception{
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			// 对JSON数据进行转换，将JSON数据组转换成对象集合
			List<OrganizationInfo> organizationInfoList = JsonUtils.json2List(data,OrganizationInfo.class);
			organMergeSplitInfoManager.saveOrganSplitInfo(organizationInfoList,parentOrganizationRelationOids
					,childrensOrganizationRelationOids,organRelaOid,personOids);
			if(enabledLogFlag){
				log.setOperationResultCode("1");
				log.setOperationResultName("成功");
			}
		} catch (Exception e) {
			if(enabledLogFlag){
				log.setOperationResultCode("2");
				log.setOperationResultName("失败");
			}
		}finally{
			if(enabledLogFlag){
				List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
				dataLogs.add(new OperationDataLog("本级机构标示",parentOrganizationRelationOids));
				dataLogs.add(new OperationDataLog("描述",childrensOrganizationRelationOids));
				dataLogs.add(new OperationDataLog("唯一标示",organRelaOid));
				dataLogs.add(new OperationDataLog("有效标示",data));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
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

	public void setPersonOids(String personOids) {
		this.personOids = personOids;
	}
	public void setParentOrganizationRelationOids(
			String parentOrganizationRelationOids) {
		this.parentOrganizationRelationOids = parentOrganizationRelationOids;
	}
	public void setChildrensOrganizationRelationOids(
			String childrensOrganizationRelationOids) {
		this.childrensOrganizationRelationOids = childrensOrganizationRelationOids;
	}
	/**
	 * @param mergeType the mergeType to set
	 */
	public void setMergeType(String mergeType) {
		this.mergeType = mergeType;
	}
	/**
	 * @param organIds the organIds to set
	 */
	public void setOrganIds(String organIds) {
		this.organIds = organIds;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(OrganMergeSplitInfo entity) {
		this.entity = entity;
	}
	/**
	 * @param organRelaOid the organRelaOid to set
	 */
	public void setOrganRelaOid(String organRelaOid) {
		this.organRelaOid = organRelaOid;
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
	public void prepare() throws Exception {
	}
	/**
	 * 注入人员关系实体
	 * throws Exception 
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = organMergeSplitInfoManager.get(id);
		} else {
			entity = new OrganMergeSplitInfo();
		}
	}
	/**
	 * 获取人员关系Manager
	 */
	@Override
	protected EntityManager<OrganMergeSplitInfo, String> getEntityManager() {
		return organMergeSplitInfoManager;
	}
	/**
	 * 获取人员信息实体
	 */
	public OrganMergeSplitInfo getModel() {
		return entity;
	}

	public String getEnabledLog() {
		return enabledLog;
	}

	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	} 
}
