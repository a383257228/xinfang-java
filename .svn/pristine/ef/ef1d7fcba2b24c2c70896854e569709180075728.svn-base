package com.sinosoft.xf.petition.supervision.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * WarnStateInfo 预警状态entity.
 * @author ljx
 * @createDate 2011-05-23
 */
@Entity
@Table(name = "WARN_STATE_INFO")
public class WarnStateInfo extends IdEntity{
	
	private static final long serialVersionUID = 1L;

	
	
	//时限规则设置表主键
	private String timeLimitDefineOid;
	//信访编号
	private String petitionNo;
	//所属区域
	private String regionCode;
	//预警超时状态
	private String alarmActive;
	//预警类型code
	private String alarmTypeCode;
	//预警类型名
	private String alarmTypeName;
	//预警原因
	private String alarmReason;
	@Column(name = "TIME_LIMIT_DEFINE_OID", length = 20)
	public String getTimeLimitDefineOid() {
		return this.timeLimitDefineOid;
	}

	public void setTimeLimitDefineOid(String timeLimitDefineOid) {
		this.timeLimitDefineOid = timeLimitDefineOid;
	}

	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}

	@Column(name = "ALARM_ACTIVE", length = 20)
	public String getAlarmActive() {
		return this.alarmActive;
	}

	public void setAlarmActive(String alarmActive) {
		this.alarmActive = alarmActive;
	}

	@Column(name = "ALARM_TYPE_CODE", length = 10)
	public String getAlarmTypeCode() {
		return this.alarmTypeCode;
	}

	public void setAlarmTypeCode(String alarmTypeCode) {
		this.alarmTypeCode = alarmTypeCode;
	}

	@Column(name = "ALARM_TYPE_NAME", length = 50)
	public String getAlarmTypeName() {
		return this.alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getAlarmReason() {
		return alarmReason;
	}

	public void setAlarmReason(String alarmReason) {
		this.alarmReason = alarmReason;
	}

	

	
}
	