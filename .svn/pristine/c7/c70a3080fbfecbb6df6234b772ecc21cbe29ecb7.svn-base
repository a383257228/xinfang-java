package com.sinosoft.xf.workflow.domain;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.domain.IdEntity;
/**
 * 信访流转状态信息
 * 记录信访件当前的流转状态
 * @date 2011-09-12
 * @author wangxx
 */
@Entity
@Table(name = "Petition_Circulation_State_Info")
public class PetitionCirculationStateInfo extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String petitionNo;//全国信访编码
	private PetitionBasicInfo petitionBasicInfo;//信访基本信息表oid  此处还需要修改
	private String regionCode;//当前纪检监察机构/行政区域
	private String regionName;//当前纪检监察机构/行政区域值
	private String activityNo;//活动编号  
	private String activityName;//活动编号名称  
	private String defaultInstructCode;//默认批示人
	private String defaultSuperviseCode;//默认督办人
	private String defaultDealerCode;//默认处理人code
	private String defaultDealerName;//默认处理人name
	private String deptCode;//部门编码
	private String deptName;//部门名称
	public static final int FIELDNUM = 11; // 数据库表除AudiEntity维护的字段个数
	public Timestamp createDate;
	public String creatorName;
	public String creatorId;
	public Timestamp modifyDate;
	public String modifyName;
	public String modifyId;

	/**
	 * 将map中的值复制到状态信息对象中，并返回该对象
	 * @date 2011-09-14
	 * @author wangxx
	 * @param map 操作数据库所需要的参数集合
	 * @return 空
	 */
	public void generate(final Map<String, Object> map) {
		for(int i = 0; i <= FIELDNUM; i++) {
			final String name = getFieldName(i);
			if (map.containsKey(name)) {
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
	private String getFieldName(final int i) {
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
			fieldName = "defaultInstructCode";
			break;
		case 6:
			fieldName = "defaultDealerCode";
			break;
		case 7:
			fieldName = "defaultDealerName";
			break;
		case 8:
			fieldName = "deptCode";
			break;
		case 9:
			fieldName = "deptName";
			break;
		case 10:
			fieldName = "defaultSuperviseCode";
			break;
		default:
			break;
		}
		return fieldName;
	}

	/**
	 * 通过字段名称给属性赋值
	 * @date 2011-09-14
	 * @author wangxx
	 * @param name 属性名称
	 * @param value 属性值
	 * @return 空
	 */
	private void setFieldName(final String name, final Object value) {
		if ("petitionNo".equals(name)) {
			petitionNo = (String) value;
		} else if ("petitionBasicInfo".equals(name)) {
			petitionBasicInfo = (PetitionBasicInfo) value;
		} else if ("regionCode".equals(name)) {
			regionCode = (String) value;
		} else if ("regionName".equals(name)) {
			regionName = (String) value;
		} else if ("defaultInstructCode".equals(name)) {
			defaultInstructCode = (String) value;
		} else if ("activityNo".equals(name)) {
			activityNo = (String) value;
		} else if ("defaultDealerCode".equals(name)) {
			defaultDealerCode = (String) value;
		} else if ("defaultDealerName".equals(name)) {
			defaultDealerName = (String) value;
		} else if ("deptCode".equals(name)) {
			deptCode = (String) value;
		} else if ("deptName".equals(name)) {
			deptName = (String) value;
		} else if ("defaultSuperviseCode".equals(name)) {
			defaultSuperviseCode = (String) value;
		}
	}

	/**
	 * @return the petitionBasicInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
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

	public String getDefaultDealerCode() {
		return defaultDealerCode;
	}

	public void setDefaultDealerCode(String defaultDealerCode) {
		this.defaultDealerCode = defaultDealerCode;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(final String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(final String deptName) {
		this.deptName = deptName;
	}

	public String getDefaultInstructCode() {
		return defaultInstructCode;
	}

	public void setDefaultInstructCode(String defaultInstructCode) {
		this.defaultInstructCode = defaultInstructCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getDefaultDealerName() {
		return defaultDealerName;
	}

	public void setDefaultDealerName(String defaultDealerName) {
		this.defaultDealerName = defaultDealerName;
	}

	public String getDefaultSuperviseCode() {
		return defaultSuperviseCode;
	}

	public void setDefaultSuperviseCode(String defaultSuperviseCode) {
		this.defaultSuperviseCode = defaultSuperviseCode;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
}
