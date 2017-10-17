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
import com.sinosoft.organization.domain.GroupInfo;
import com.sinosoft.organization.manager.GroupInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
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
@Results( { @Result(name = CrudActionSupport.RELOAD, location="group-info.action", type = "redirect")})
public class GroupInfoAction extends CrudExtActionSupport<GroupInfo, String> {
	
	private static final long serialVersionUID = 1L;
	//注入组织机构分组信息业务层
	@Autowired
	GroupInfoManager groupInfoManager;

	/**
	 * 操作日志service用于手动记录操作日志
	 */
	@Autowired
	OperationLogManager operationLogManager;
	
	private String selectValue;//选择的用户oid
	
	private String deptOids;//选择的部门oid

	private String groupOid;//分组ＩＤ
	
	private String node;//树节点

	GroupInfo entity = new GroupInfo();
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
	 * 查询是否存在分组相关属性的方法
	 * @return 空
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void judgeExist() throws Exception{
		int pageNo = (start / limit) + 1;
		ExtjsPage<GroupInfo> page = null;
		Criteria criteria = groupInfoManager.createCriteria();
		criteria = criteria.add(Restrictions.eq(filterTxt, filterValue));
		if(null != this.id && !"".equals(this.id.trim())){
			criteria = criteria.add(Restrictions.ne("id", this.id));
		}
		page = groupInfoManager.pagedQuery(criteria, pageNo, limit);
		List<GroupInfo> loadingUnitList = page.getResult();
		if(loadingUnitList.size() == 0)
			Struts2Utils.getResponse().getWriter().print(false);
		else Struts2Utils.getResponse().getWriter().print(true);
	}
	/**
	 * 新增分组信息
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 */
	public void insertOrganGroup(){
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			groupInfoManager.insertOrganGroup(entity, deptOids, selectValue);
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
				dataLogs.add(new OperationDataLog("分组机构",deptOids));
				dataLogs.add(new OperationDataLog("分组用户",selectValue));
				dataLogs.add(new OperationDataLog("分组编码",entity.getGroupCode()));
				dataLogs.add(new OperationDataLog("分组名称",entity.getGroupName()));
				dataLogs.add(new OperationDataLog("分组唯一标示",entity.getId()));
				dataLogs.add(new OperationDataLog("操作人",entity.getOperator()));
				dataLogs.add(new OperationDataLog("操作人姓名",entity.getOperatorName()));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
	}
	
	/**
	 * 修改保存分组信息
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 */
	public void saveOrganGroup(){
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			groupInfoManager.saveOrganGroup(entity, deptOids, selectValue,this.groupOid);
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
				dataLogs.add(new OperationDataLog("分组机构",deptOids));
				dataLogs.add(new OperationDataLog("分组用户",selectValue));
				dataLogs.add(new OperationDataLog("原分组唯一标示",groupOid));
				dataLogs.add(new OperationDataLog("分组编码",entity.getGroupCode()));
				dataLogs.add(new OperationDataLog("分组名称",entity.getGroupName()));
				dataLogs.add(new OperationDataLog("分组唯一标示",entity.getId()));
				dataLogs.add(new OperationDataLog("操作人",entity.getOperator()));
				dataLogs.add(new OperationDataLog("操作人姓名",entity.getOperatorName()));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
	}
	
	/**
	 * 删除分组信息
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 */
	public void deleteOrganGroup(){
		OperationLog log = null;
		boolean enabledLogFlag = (!"false".equals(enabledLog));
		if(enabledLogFlag){
			log = new OperationLog();
			setOperaLogInfo(log);
		}
		try {
			//此处selectValue表示分组信息的oids
			groupInfoManager.deleteOrganGroup(selectValue);
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
				dataLogs.add(new OperationDataLog("分组标示",selectValue));
				operationLogManager.saveLog(log,dataLogs);
			}
		}
	}
	/**
	 * 通过机构分组信息现实树形列表， 显示中文简称
	 * 渲染修改分组信息时的做出树
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 * @throws IOException   
	 */
	public void getOrganRelationInfosByGroup() throws IOException{
		String json =groupInfoManager.getOrganRelationInfosByGroup(node, "groupCode", filterValue, false);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 通过机构分组信息现实树形列表，显示中文简称 且带复选框
	 * 渲染修改分组信息时的做出树
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 * @throws IOException   
	 */
	public void getOrganRelationInfosCheckedByGroup() throws IOException{
		String json =groupInfoManager.getOrganRelationInfosByGroup(node, "groupCode", filterValue, true);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 通过机构分组信息现实树形列表，显示中文名
	 * 渲染修改分组信息时的做出树
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 * @throws IOException   
	 */
	public void getOrganRelationInfosByGroupLongName() throws IOException{
		String json =groupInfoManager.getOrganRelationInfosByGroupLongName(node, "groupCode", filterValue, false);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 通过机构分组信息现实树形列表，显示中文名  且带复选框
	 * 渲染修改分组信息时的做出树
	 * @date 2010-10-12
	 * @author wangxx
	 * @return 空
	 * @throws IOException   
	 */
	public void getOrganRelationInfosCheckedByGroupLongName() throws IOException{
		String json =groupInfoManager.getOrganRelationInfosByGroupLongName(node, "groupCode", filterValue, true);
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 重写loadDate方法
	 * @return 空
	 */
	public void loadData() throws Exception{
		OperationLog log = new OperationLog();
		log.setOperationFuncCode(getFunctionCode());
		log.setOperationFuncName(getFunctionName());
		log.setOperationBtnName(getBtnName());
		log.setOperationDesc(getDesp());
		log.setOperationLevelCode(getLevelCode());
		log.setOperationLevelName(getLevelName());
		log.setOperationTypeCode(getTypeCode());
		log.setOperationTypeName(getTypeName());
		try {
			GroupInfo model = getEntityManager().get(this.id);
		    if (model != null){
			      JsonUtils.write(model, Struts2Utils.getResponse().getWriter(), 
			  	        getExcludes(), getDatePattern());
		    }
		    log.setOperationResultCode("1");
			log.setOperationResultName("成功");
		} catch (Exception e) {
			log.setOperationResultCode("2");
			log.setOperationResultName("失败");
		}finally{
			List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
			dataLogs.add(new OperationDataLog("分组信息唯一标示",this.id));
			operationLogManager.saveLog(log,dataLogs);
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
	
	/**
	 * 设置loaddata方法不显示的数据对象
	 * @return String[]不显示属性的字符串数组
	 */
	public String[] getExcludes() {
		return new String[] { "hibernateLazyInitializer", "handler", "groupItemInfo"};
	}
	/**
	 * @param selectValue the selectValue to set
	 */
	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}

	/**
	 * @param deptOids the deptOids to set
	 */
	public void setDeptOids(String deptOids) {
		this.deptOids = deptOids;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}
	/**
	 * @param groupOid the groupOid to set
	 */
	public void setGroupOid(String groupOid) {
		this.groupOid = groupOid;
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
	protected EntityManager<GroupInfo, String> getEntityManager() {
		return groupInfoManager;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = groupInfoManager.get(id);
		} else {
			entity = new GroupInfo();
		}
	}

	public GroupInfo getModel() {
		return entity;
	}
	public String getEnabledLog() {
		return enabledLog;
	}
	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}
}
