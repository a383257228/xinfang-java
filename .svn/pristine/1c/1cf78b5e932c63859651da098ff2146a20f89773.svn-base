package com.sinosoft.xf.workflow.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 信访工作流 mini 引擎
 * 进行状态变换,记录状态转换轨迹
 * @date 2011-09-12
 * @author wangxx
 */
@Entity
@Table(name = "Petition_Workflow_Engine_Info")
public class PetitionWorkflowEngineInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String regionCode;
	private String regionName;
	private String processNo;//流程编号
	private String activityNo;//活动编号
	private String conditionType;//流转条件类型
	private String conditionCode;//流转条件代码
	private String conditionName;//流转条件名称
	private String conditionSql;//流转条件sql
	private String nextActivityNo;//下一步活动编号
	private String validFlag;//1有效，0无效
	/**
	 * @return the processNo
	 */
	public String getProcessNo() {
		return processNo;
	}
	/**
	 * @param processNo the processNo to set
	 */
	public void setProcessNo(final String processNo) {
		this.processNo = processNo;
	}
	/**
	 * @return the activityNo
	 */
	public String getActivityNo() {
		return activityNo;
	}
	/**
	 * @param activityNo the activityNo to set
	 */
	public void setActivityNo(final String activityNo) {
		this.activityNo = activityNo;
	}
	/**
	 * @return the conditionType
	 */
	public String getConditionType() {
		return conditionType;
	}
	/**
	 * @param conditionType the conditionType to set
	 */
	public void setConditionType(final String conditionType) {
		this.conditionType = conditionType;
	}
	/**
	 * @return the conditionCode
	 */
	public String getConditionCode() {
		return conditionCode;
	}
	/**
	 * @param conditionCode the conditionCode to set
	 */
	public void setConditionCode(final String conditionCode) {
		this.conditionCode = conditionCode;
	}
	/**
	 * @return the conditionName
	 */
	public String getConditionName() {
		return conditionName;
	}
	/**
	 * @param conditionName the conditionName to set
	 */
	public void setConditionName(final String conditionName) {
		this.conditionName = conditionName;
	}
	/**
	 * @return the conditionSql
	 */
	public String getConditionSql() {
		return conditionSql;
	}
	/**
	 * @param conditionSql the conditionSql to set
	 */
	public void setConditionSql(final String conditionSql) {
		this.conditionSql = conditionSql;
	}
	/**
	 * @return the nextActivityNo
	 */
	public String getNextActivityNo() {
		return nextActivityNo;
	}
	/**
	 * @param nextActivityNo the nextActivityNo to set
	 */
	public void setNextActivityNo(final String nextActivityNo) {
		this.nextActivityNo = nextActivityNo;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}
	/**
	 * @param validFlag the validFlag to set
	 */
	public void setValidFlag(final String validFlag) {
		this.validFlag = validFlag;
	}
}
