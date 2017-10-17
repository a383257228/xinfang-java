package com.sinosoft.xf.petition.supervision.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * TimeLimitDefine 时限 entity.
 * @author ljx
 * @createDate 2011-04-27
 */
@Entity
@Table(name = "TIME_LIMIT_DEFINE")
public class TimeLimitDefine extends IdEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	
	//当前纪检监察机构/行政区域
	private String regionCode;
	//信访方式代码
	private String petitionTypeCode;
	//信访方式名称
	private String petitionTypeName;
	//紧急程度代码
	private String urgencyCode;
	//紧急程度名称
	private String urgencyName;
	//当前状态代码
	private String activityNo;
	//当前状态名称
	private String activityName;
	//办理方式代码
	private String dealTypeCode;
	//办理方式名称
	private String dealTypeName;
	//信访来源代码
	private String petitionSourceCode;
	//信访来源名称
	private String petitionSourceName;
	//交换类型,1导出交换，2传输交换，3集中部署，4不交换
	private String transType;
	//交换标志
	private String sendFlag;
	//接受标志
	private String arriveFlag;
	//预警类型代码
	private String alarmTypeCode;
	//预警类型名称
	private String alarmTypeName;
	//提醒天数
	private Integer warnDays;
	//超时天数
	private Integer overDays;
	//中度超时天数
	private Integer moderateOverDays;
	//严重超时天数
	private Integer seriousOverDays;
	//有效标识
	private String isValid;

		
	@Column(name = "REGION_CODE", length = 12)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "URGENCY_CODE", length = 10)
	public String getUrgencyCode() {
		return this.urgencyCode;
	}

	public void setUrgencyCode(String urgencyCode) {
		this.urgencyCode = urgencyCode;
	}

	@Column(name = "URGENCY_NAME", length = 20)
	public String getUrgencyName() {
		return this.urgencyName;
	}

	public void setUrgencyName(String urgencyName) {
		this.urgencyName = urgencyName;
	}

	@Column(name = "ACTIVITY_NO", length = 10)
	public String getActivityNo() {
		return this.activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	@Column(name = "ACTIVITY_NAME", length = 50)
	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Column(name = "DEAL_TYPE_CODE", length = 10)
	public String getDealTypeCode() {
		return this.dealTypeCode;
	}

	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}

	@Column(name = "DEAL_TYPE_NAME", length = 50)
	public String getDealTypeName() {
		return this.dealTypeName;
	}

	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}


	@Column(name = "ALARM_TYPE_CODE", length = 10)
	public String getAlarmTypeCode() {
		return alarmTypeCode;
	}

	public void setAlarmTypeCode(String alarmTypeCode) {
		this.alarmTypeCode = alarmTypeCode;
	}

	@Column(name = "ALARM_TYPE_NAME", length = 50)
	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	@Column(name = "WARN_DAYS")
	public Integer getWarnDays() {
		return this.warnDays;
	}

	public void setWarnDays(Integer warnDays) {
		this.warnDays = warnDays;
	}

	@Column(name = "OVER_DAYS")
	public Integer getOverDays() {
		return this.overDays;
	}

	public void setOverDays(Integer overDays) {
		this.overDays = overDays;
	}

	@Column(name = "MODERATE_OVER_DAYS")
	public Integer getModerateOverDays() {
		return this.moderateOverDays;
	}

	public void setModerateOverDays(Integer moderateOverDays) {
		this.moderateOverDays = moderateOverDays;
	}

	@Column(name = "SERIOUS_OVER_DAYS")
	public Integer getSeriousOverDays() {
		return this.seriousOverDays;
	}

	public void setSeriousOverDays(Integer seriousOverDays) {
		this.seriousOverDays = seriousOverDays;
	}

	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}

	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode;
	}

	public String getPetitionTypeName() {
		return petitionTypeName;
	}

	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
	}

	public String getPetitionSourceCode() {
		return petitionSourceCode;
	}

	public void setPetitionSourceCode(String petitionSourceCode) {
		this.petitionSourceCode = petitionSourceCode;
	}

	public String getPetitionSourceName() {
		return petitionSourceName;
	}

	public void setPetitionSourceName(String petitionSourceName) {
		this.petitionSourceName = petitionSourceName;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getArriveFlag() {
		return arriveFlag;
	}

	public void setArriveFlag(String arriveFlag) {
		this.arriveFlag = arriveFlag;
	}
}