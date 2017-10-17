/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 17 2010
 * @author shenhy
 */
package com.sinosoft.organization.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import com.sinosoft.organization.common.Encript;
import com.sinosoft.organization.common.EncryptMD5;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganizationInfoManager;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 提供机构用户的新建、修改、查找用户等功能
 * @author shenhy
 */
@Namespace("/organperson")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results({@Result(name=CrudActionSupport.RELOAD,location="person-info.action",type = "redirect")})
public class PersonInfoAction extends CrudExtActionSupport<PersonInfo, String> {
	private static final long serialVersionUID = 1L;
	
	//注入用户信息业务层
	@Autowired
	PersonInfoManager personInfoManager;
	
	//注入机构信息业务层
	@Autowired
	OrganizationInfoManager organizationInfoManager;

	/**
	 * 操作日志service用于手动记录操作日志
	 */
	@Autowired
	OperationLogManager operationLogManager;
	//定义人员关系对象实体
	PersonInfo entity = new PersonInfo();

	//人员信息OID
	private String personOid;	

	//关系表oid
	private String organPersonRelationOid;

	//	部门Oid
	private String organizationInfoOid;
	
	// 人员职务
	private String userPosition;
	
	//是否仅限本人
	private String limitFlag;
	
	private String createDate;
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
	 * 保存更新用户信息
	 * 调用哪些方法：
	 * 1、personInfoManager.savePersonInfo
	 * 被前台哪些模块调用：
	 * 1、Organ.User.AddOrganPersonWindow
	 * 2、Organ.User.EditOrganPersonWindow
	 * @date 2010-12-06
	 * @author wangxx
	 * @return 空
	 */
	public void savePersonInfo(){
		try {
			if(null!=this.createDate){
				entity.setCreateDate(string2Date(this.createDate));
			}
			personInfoManager.savePersonInfo(organPersonRelationOid, entity, organizationInfoOid, userPosition,limitFlag);
		} catch (Exception e) {
		}
	}
	//添加日志操作记录
	public void saveOperationLogRecordInfo(){
		HttpServletRequest request = Struts2Utils.getRequest();
		String operationFuncCode = request.getParameter("operationFuncCode");
		String operationFuncName = request.getParameter("operationFuncName");
		String operationBtnName = request.getParameter("operationBtnName");
		String operationDesc = request.getParameter("operationDesc");
		String operationResultCode = request.getParameter("operationResultCode");
		String operationResultName = request.getParameter("operationResultName");
		String operationLevelCode = request.getParameter("operationLevelCode");
		String operationLevelName = request.getParameter("operationLevelName");
		String operationTypeCode = request.getParameter("operationTypeCode");
		String operationTypeName = request.getParameter("operationTypeName");
		String operationDataDesc = request.getParameter("operationDataDesc");
		String operationDataDescArr []=request.getParameterValues("operationDataDesc[]");
		String operationDataValue = request.getParameter("operationDataValue");
		String operationDataValueArr []=request.getParameterValues("operationDataValue[]");
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			log.setOperationFuncCode(operationFuncCode);
			log.setOperationFuncName(operationFuncName);
			log.setOperationBtnName(operationBtnName);
			log.setOperationDesc(operationDesc);
			log.setOperationLevelCode(operationLevelCode);
			log.setOperationLevelName(operationLevelName);
			log.setOperationTypeCode(operationTypeCode);
			log.setOperationTypeName(operationTypeName);
			log.setOperationTime( new Timestamp(System.currentTimeMillis()));
			if(operationDataValueArr!=null){
				log.setCountNum(operationDataValueArr.length);
			}else{
				log.setCountNum(1);	
			}
		}
		try {
			if(enabledLogFlag){
				log.setOperationResultCode(operationResultCode);
				log.setOperationResultName(operationResultName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(enabledLogFlag){
				List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
				if(operationDataValueArr!=null){
					for(int i=0;i<operationDataValueArr.length;i++){
						if(operationDataDescArr!=null && operationDataDescArr.length==operationDataValueArr.length){
							dataLogs.add(new OperationDataLog(operationDataDescArr[i],operationDataValueArr[i]));
						}else{
							dataLogs.add(new OperationDataLog(operationDataDesc,operationDataValueArr[i]));
						}
					}
					operationLogManager.saveLog(log,dataLogs);
				}else{
					dataLogs.add(new OperationDataLog(operationDataDesc,operationDataValue));
					operationLogManager.saveLog(log,dataLogs);
				}
			}
		}
	}
	
	
	
	/**
	 * 验证用户属性是否已经存在
	 * @date 2010-11-27
	 * @author wangxx 
	 * @param filterTxt  需要验证的属性
	 * @param filterValue  用户输入的值
	 * @param personOid 用户信息对象oid
	 * @return 返回true时表示重复，返回false时表示不重复
	 * @throws IOException 
	 */
	public void judgePerson() throws IOException{
		String flag = personInfoManager.judgePerson(filterTxt, filterValue, this.personOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * 在修改用户信息时用于判断修改用户部门的相关验证
	 * @date 2010-11-27
	 * @author wangxx
	 * @return 空
	 * @throws IOException
	 */
	public void judgeOrganPerson() throws IOException{
		String flag = personInfoManager.judgeOrganPerson(organPersonRelationOid, organizationInfoOid, personOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}

	/**
	 * 判断复制用户时同一个部门是否有重复用户
	 * @author shenhy
	 * @return 空
	 * @throws IOException
	 */
	public void judgePersonOrganRepeat() throws IOException {
		//获取页面参数 
		//机构编号
		String organizationInfoId = Struts2Utils.getParameter("organizationInfoId");
		//用户Id
		String personOid=Struts2Utils.getParameter("personOid");
		// 判断人员英文名称是否重复
		String personEnameRepeat = personInfoManager.judgePersonOrganRepeat(organizationInfoId,personOid);
		//返回页面参数
		Struts2Utils.getResponse().getWriter().print(personEnameRepeat);
	}
	/**
	 * 通过oids返回他们的中文名
	 * 用于部门分组时返回人员的中文名
	 * @return 空
	 * @throws IOException 
	 */
	public void getPersonCnameByOid() throws IOException{
		String json = personInfoManager.getPersonCnameByOid(this.ids);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	

	/**
	 * 查询是否存在相关属性的方法
	 * @return 空
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void judgeExist() throws Exception{
		int pageNo = (start / limit) + 1;
		ExtjsPage<PersonInfo> page = null;
		Criteria criteria = personInfoManager.createCriteria();
		criteria = criteria.add(Restrictions.eq(filterTxt, filterValue));
		if(null != this.id && !"".equals(this.id.trim())){
			criteria = criteria.add(Restrictions.ne("id", this.id));
		}
		page = personInfoManager.pagedQuery(criteria, pageNo, limit);
		List<PersonInfo> loadingUnitList = page.getResult();
		if(loadingUnitList.size() == 0)
			Struts2Utils.getResponse().getWriter().print(false);
		else Struts2Utils.getResponse().getWriter().print(true);
	}
	
	/**
	 * 将String转换为Timestamp类型
	 * @date 2011-05-05
	 * @author wangxx
	 * @param dateString
	 * @return dateTime
	 */
	public final Timestamp string2Date(final String dateString){
		try {
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);// 设定格式
//		    dateFormat.setLenient(false);// 严格控制输入 比如2010－02－31，根本没有这一天 ，也会认为时间格式不对。
		    Date timeDate;
			timeDate = dateFormat.parse(dateString);
			final Timestamp dateTime = new Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型  
			return dateTime;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改密码
	 * @throws Exception
	 */
	public void modifyPassword() throws Exception{
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		boolean flag=false;
		if(person!=null){
			person.setMd5Pwd("{"+EncryptMD5.getMD5(entity.getUserPwd().getBytes())+"}");
			//对密码进行加密
			person.setUserPwd(Encript.encript(entity.getUserPwd()));
			personInfoManager.save(person);
			flag=true;
		}
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * @param userPosition the userPosition to set
	 */
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
	
	/**
	 * @param organizationInfoOid the organizationInfoOid to set
	 */
	public void setOrganizationInfoOid(String organizationInfoOid) {
		this.organizationInfoOid = organizationInfoOid;
	}
	/**
	 * @param personOid the personOid to set
	 */
	public void setPersonOid(String personOid) {
		this.personOid = personOid;
	}

	/**
	 * @param organPersonRelationOid the organPersonRelationOid to set
	 */
	public void setOrganPersonRelationOid(String organPersonRelationOid) {
		this.organPersonRelationOid = organPersonRelationOid;
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
	/**
	 * 注入人员关系实体
	 *  throws Exception 
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = personInfoManager.get(id);
		} else {
			entity = new PersonInfo();
		}
	}
	/**
	 * 获取人员关系Manager
	 */
	@Override
	protected EntityManager<PersonInfo, String> getEntityManager() {
		return personInfoManager;
	}
	/**
	 * 获取人员信息实体
	 */
	public PersonInfo getModel() {
		return entity;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEnabledLog() {
		return enabledLog;
	}
	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}
}
