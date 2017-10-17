package com.sinosoft.organization.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.domain.OperationDataLog;
import com.sinosoft.log.domain.OperationLog;
import com.sinosoft.log.manager.OperationLogManager;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.manager.OrganizationInfoManager;
import com.sinosoft.organization.manager.OrganizationRelationInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：organization   
 * 类名称：OrganizationInfoAction   
 * 类描述：组织机构action层
 * 创建人：wangxx   
 * 创建时间：2010-06-23  
 */
@Namespace("/organization")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="organization-info.action", type = "redirect")})
public class OrganizationInfoAction extends CrudExtActionSupport<OrganizationInfo, String> {
	
	private static final long serialVersionUID = 1L;
	
	//注入组织机构信息业务层
	@Autowired
	OrganizationInfoManager organizationInfoManager;

	//注入组织机构关系业务层
	@Autowired
	OrganizationRelationInfoManager organizationRelationInfoManager;

	/**
	 * 操作日志service用于手动记录操作日志
	 */
	@Autowired
	OperationLogManager operationLogManager;

	OrganizationInfo entity = new OrganizationInfo();

	private String organOid;//机构oid
	
	private String relation;//隶属关系
	
	private String fieldText;//用于通过对象属性查找对象
	
	private String fieldValue;//用于通过对象属性查找对象
	
	private String organOrder;//机构显示顺序

	// 机构关系OID
	private String organizationRelationOid;
	//区分是否保存还是新增
	private String flag;
	
	//拆分部门时父机构参数
	private String parentOrganizationRelationOids;
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
	 * 通过组织中文名和上级组织关系oid判断该组织下是否已经存在此中文名的下级组织
	 * @date 2010-12-13
	 * @author wangxx
	 * 用途：
	 *  1、在组织拆分时，同时判断多个组织中文名是否已经存在
	 * @param organizationRelationOid 上级组织关系oid，每个oid之间用“%”分离
	 * @param filterValue 组织中文名，每个中文名之间用“%”分离
	 * @return 空
	 * @throws IOException 
	 */
	public void judgeOrganizationCname() throws IOException{
		String flag = organizationInfoManager.judgeOrganizationInfos(
				fieldText,parentOrganizationRelationOids,filterValue);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	
	/**
	 * 用于判断含有某属性值的组织在上级组织下已经存在，例如判断上级组织下是否已经含有某中文名的组织
	 * @date 2010-12-01
	 * @author wangxx
	 * @param filterTxt  属性名称
	 * @param filterValue  属性值
	 * @param parentOrgRelaOid  上级部门关系对象oid
	 * @param orgOid  本级组织的oid
	 * @return  空
	 * @throws IOException 
	 */
	public void judgeOrganization() throws IOException{
		String flag = organizationInfoManager.judgeOrganizationInfo(
				filterTxt, filterValue,organizationRelationOid, organOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * 删除组织信息，只删除关系信息，不删除机构信息
	 * 调用哪些方法:
	 * 1、organizationInfoManager.deleteOrganizationInfo
	 * 被前台哪些模块调用:
	 * 1、Organization.OrganColumnTree 区域操作界面
	 * 2、Organ.Dept.DeptColumnTree 部门
	 * @param organizationRelationOid   组织关系表oid
	 * @return 空
	 */
	public void deleteOrganizationInfo(){
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			organizationInfoManager.deleteOrganizationInfo(this.organizationRelationOid);
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
				dataLogs.add(new OperationDataLog("关系表唯一标示",this.organizationRelationOid));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
	}
	/**
	 * 验证组织属性是否已经存在
	 * @date 2010-11-27
	 * @author wangxx 
	 * @param filterTxt  需要验证的属性
	 * @param filterValue  用户输入的值
	 * @param id  组织oid
	 * @return 空
	 * @throws IOException 
	 */
	public void judgeOrganizationInfo() throws IOException{
		String flag = organizationInfoManager
			.judgeOrganizationInfo(filterTxt, filterValue, this.id);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * 保存组织信息  该功能包括：新增保存和修改保存
	 * 调用哪些方法:
	 * 1、organizationInfoManager.saveOrganizationInfo
	 * 被前台哪些模块调用：
	 * 1、Organization.EditOrganizationWindow修改区域
	 * 2、Organ.Dept.EditDeptWindow 部门
	 * @param flag  保存类型，insert为新增保存，save为修改保存
	 * @param entity  组织对象
	 * @param organizationRelationOid  组织oid，在新增保存时为上级组织关系oid，修改保存时为本级组织关系oid
	 * @param organOrder  组织显示顺序
	 * @param relation  组织隶属关系
	 * @return 空
	 */
	public void saveOrganizationInfo(){
		try {
			organizationInfoManager.saveOrganizationInfo(this.flag, entity, this.organizationRelationOid, this.organOrder, this.relation);
		} catch (Exception e) {
		}
	}
	/**
	 * 通过对象属性查找组织，如果存在返回json，如果不存在返回""
	 * @date 2010-10-28
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganization() throws IOException{
		String json = organizationInfoManager
			.getOrganization(this.fieldText, this.fieldValue);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 同时判断多个组织属性是否存在是否存在
	 * 用途：
	 *  1、在组织拆分时，判断多个组织英文名和组织编码是否已存在
	 * @return 空
	 * @throws IOException 
	 */
	public void judgeOrganizations() throws IOException{
		String flag = organizationInfoManager
			.judgeOrganizations(filterTxt, filterValue);
		Struts2Utils.getResponse().getWriter().print(flag);
	}

	/**
	 * 查询是否存在相关属性的方法
	 * @return 空
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void judgeExist() throws Exception{
		int pageNo = (start / limit) + 1;
		ExtjsPage<OrganizationInfo> page = null;
		Criteria criteria = organizationInfoManager.createCriteria();
		criteria = criteria.add(Restrictions.eq(filterTxt, filterValue));
		if(null != this.id && !"".equals(this.id.trim())){
			criteria = criteria.add(Restrictions.ne("id", this.id));
		}
		page = organizationInfoManager.pagedQuery(criteria, pageNo, limit);
		List<OrganizationInfo> loadingUnitList = page.getResult();
		if(loadingUnitList.size() == 0)
			Struts2Utils.getResponse().getWriter().print(false);
		else Struts2Utils.getResponse().getWriter().print(true);
	}
	
	/**
	 * 根据机构ID得到机构名称，多个机构ID用;分隔
	 * @throws IOException
	 * @throws Exception
	 */
	public void getOrganName() throws IOException, Exception {
		String organId = Struts2Utils.getParameter("organId");
		String[] ids = organId.split(";");
		String organName = "";
		for(int i=0;i<ids.length;i++){
			organName+=organizationInfoManager.get(ids[i]).getOrgCname()+";";
		}
		Struts2Utils.getResponse().getWriter().print(organName);
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
	
	/**
	 * @param organOid the organOid to set
	 */
	public void setOrganOid(String organOid) {
		this.organOid = organOid;
	}
	
	public String[] getExcludes() {
		return new String[] { "hibernateLazyInitializer", "handler", "organizationRelationInfo"};
	}

	/**
	 * @param organizationRelationOid the organizationRelationOid to set
	 */
	public void setOrganizationRelationOid(String organizationRelationOid) {
		this.organizationRelationOid = organizationRelationOid;
	}

	/**
	 * @param parentOrganizationRelationOids the parentOrganizationRelationOids to set
	 */
	public void setParentOrganizationRelationOids(
			String parentOrganizationRelationOids) {
		this.parentOrganizationRelationOids = parentOrganizationRelationOids;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * @param fieldText the fieldText to set
	 */
	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @param organOrder the organOrder to set
	 */
	public void setOrganOrder(String organOrder) {
		this.organOrder = organOrder;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
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

	@Override
	protected EntityManager<OrganizationInfo, String> getEntityManager() {
		return organizationInfoManager;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = organizationInfoManager.get(id);
		} else {
			entity = new OrganizationInfo();
		}
	}

	public OrganizationInfo getModel() {
		return entity;
	}

	public String getEnabledLog() {
		return enabledLog;
	}

	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}
	
}
