package com.sinosoft.xf.workflow.domain;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 信访状态轨迹信息
 * 用于记录信访状态流转轨迹信息
 * @date 2011-09-12
 * @author wangxx
 */
@Entity
@Table(name = "Petition_Circulation_State_Trace_Info")
public class PetitionCirculationStateTraceInfo extends AudiEntity {
	private static final long serialVersionUID = 1L;
	private String petitionNo;//全国信访编码
	private PetitionBasicInfo petitionBasicInfo;//信访基本信息表oid
	private String regionCode;//当前纪检监察机构/行政区域
	private String regionName;//当前纪检监察机构/行政区域值
	private String activityNo;//活动编号
	private String activityNoName;//活动编号名称
	private String conditionCode;//流转条件代码
	private String conditionType;//流转条件类型
	private String nextActivityNo;//下一活动编号
	private String nextActivityNoName;//下一活动编号名称
	private String operateRegionCode;//所属处理机构代码
	private String operateRegionName;//所属处理机构名称
	private String transFlag="0";//传输标识
	private String petitionStateNo;//状态轨迹编号
	private String defaultInstructor;//批示领导
	private String operate;//操作
	private String state;//状态
	private String defaultDeptName;//转发机构名称
	private String backReason;//回退原因
	
	public static final int FIELDNUM = 19;	// 数据库表除oid的字段个数
	
	/**
	 * 将map中的值复制到状态轨迹信息对象中，并返回该对象
	 * @date 2011-09-14
	 * @author wangxx
	 * @param map 操作数据库所需要的参数集合
	 * @return 空
	 */
	public void generate(final Map<String,Object> map){
		for(int i = 0; i <= FIELDNUM; i++){
			final String name = getFieldName(i);
			if(map.containsKey(name)){
				setFieldName(name, map.get(name));
			}
		}
	}
	
	/**
	 * 通过属性的编号返回属性名称
	 * @date 2011-09-14
	 * @author wangxx
	 * @param i 属性编号
	 * @return 属性名称
	 */
	private String getFieldName(final int i){
		String fieldName = "";
		switch (i) {
			case 0:
				fieldName = "petitionNo";
				break;
			case 1:
				fieldName = "petitionBasicInfo";
				break;
			case 2:
				fieldName = "regionCode";
				break;
			case 3:
				fieldName = "regionName";
				break;
			case 4:
				fieldName = "activityNo";
				break;
			case 5:
				fieldName = "activityNoName";
				break;
			case 6:
				fieldName = "conditionCode";
				break;
			case 7:
				fieldName = "nextActivityNo";
				break;
			case 8:
				fieldName = "nextActivityNoName";
				break;
			case 9:
				fieldName = "operateRegionCode";
				break;
			case 10:
				fieldName = "operateRegionName";
				break;
			case 11:
				fieldName = "transFlag";
				break;
			case 12:
				fieldName = "petitionStateNo";
				break;
			case 13:
				fieldName = "creatorId";
				break;
			case 14:
				fieldName = "creatorName";
				break;
			case 15:
				fieldName = "conditionType";
				break;
			case 16:
				fieldName = "defaultDeptName";
				break;
			case 17:
				fieldName = "backReason";
				break;
			case 18:
				fieldName = "defaultInstructor";
				break;
			default:
				break;
		}
		return fieldName;
	}
	
	/**
	 * 通过属性名称给属性赋值
	 * @date 2011-09-14
	 * @author wangxx
	 * @param name 属性名称
	 * @param value 属性值
	 * @return 空
	 */
	private void setFieldName(final String name,final Object value){
		if("petitionNo".equals(name)){
			petitionNo = (String)value;
		}else if("petitionBasicInfo".equals(name)){
			petitionBasicInfo = (PetitionBasicInfo)value;
		}else if("regionCode".equals(name)){
			regionCode = (String)value;
		}else if("regionName".equals(name)){
			regionName = (String)value;
		}else if("activityNo".equals(name)){
			activityNo = (String)value;
		}else if("activityNoName".equals(name)){
			activityNoName = (String)value;
		}else if("conditionCode".equals(name)){
			conditionCode = (String)value;
		}else if("nextActivityNo".equals(name)){
			nextActivityNo = (String)value;
		}else if("nextActivityNoName".equals(name)){
			nextActivityNoName = (String)value;
		}else if("operateRegionCode".equals(name)){
			operateRegionCode = (String)value;
		}else if("operateRegionName".equals(name)){
			operateRegionName = (String)value;
		}else if("transFlag".equals(name)){
			transFlag = (String)value;
		}else if("petitionStateNo".equals(name)){
			petitionStateNo = (String)value;
		}else if("defaultDeptName".equals(name)){
			defaultDeptName = (String)value;
		}else if("backReason".equals(name)){
			backReason = (String)value;
		}else if("defaultInstructor".equals(name)){
			defaultInstructor = (String)value;
		}else if("conditionType".equals(name)){
			conditionType = (String)value;
		}
	}

	/**
	 * @return the petitionBasicInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "petitionBasicInfoOid")
	public PetitionBasicInfo getPetitionBasicInfo() {
		return petitionBasicInfo;
	}

	/**
	 * @param petitionBasicInfo the petitionBasicInfo to set
	 */
	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
		this.petitionBasicInfo = petitionBasicInfo;
	}
	/**
	 * @return the petitionNo
	 */
	public String getPetitionNo() {
		return petitionNo;
	}
	/**
	 * @param petitionNo the petitionNo to set
	 */
	public void setPetitionNo(final String petitionNo) {
		this.petitionNo = petitionNo;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(final String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(final String regionName) {
		this.regionName = regionName;
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
	 * @return the activityNoName
	 */
	public String getActivityNoName() {
		return activityNoName;
	}
	/**
	 * @param activityNoName the activityNoName to set
	 */
	public void setActivityNoName(final String activityNoName) {
		this.activityNoName = activityNoName;
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
	/**
	 * @return the nextActivityNoName
	 */
	public String getNextActivityNoName() {
		return nextActivityNoName;
	}
	/**
	 * @param nextActivityNoName the nextActivityNoName to set
	 */
	public void setNextActivityNoName(final String nextActivityNoName) {
		this.nextActivityNoName = nextActivityNoName;
	}
	
	public String getOperateRegionCode() {
		return operateRegionCode;
	}

	public void setOperateRegionCode(String operateRegionCode) {
		this.operateRegionCode = operateRegionCode;
	}

	public String getOperateRegionName() {
		return operateRegionName;
	}

	public void setOperateRegionName(String operateRegionName) {
		this.operateRegionName = operateRegionName;
	}

	/**
	 * @return the transFlag
	 */
	public String getTransFlag() {
		return transFlag;
	}
	/**
	 * @param transFlag the transFlag to set
	 */
	public void setTransFlag(final String transFlag) {
		this.transFlag = transFlag;
	}
	/**
	 * @return the petitionStateNo
	 */
	public String getPetitionStateNo() {
		return petitionStateNo;
	}
	/**
	 * @param petitionStateNo the petitionStateNo to set
	 */
	public void setPetitionStateNo(String petitionStateNo) {
		this.petitionStateNo = petitionStateNo;
	}

	/**
	 * @return the operate
	 */
	public String getOperate() {
		return operate;
	}

	/**
	 * @param operate the operate to set
	 */
	public void setOperate(String operate) {
		this.operate = operate;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getDefaultInstructor() {
		return defaultInstructor;
	}

	public void setDefaultInstructor(String defaultInstructor) {
		this.defaultInstructor = defaultInstructor;
	}

	public String getDefaultDeptName() {
		return defaultDeptName;
	}

	public void setDefaultDeptName(String defaultDeptName) {
		this.defaultDeptName = defaultDeptName;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
}
