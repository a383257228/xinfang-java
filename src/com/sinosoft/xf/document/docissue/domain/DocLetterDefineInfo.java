package com.sinosoft.xf.document.docissue.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 存储公文函号/期序号定义信息
 * @date 2011-04-19
 * @author wanghang
 */

@Entity
@Table(name = "Doc_Letter_Define_Info")
public class DocLetterDefineInfo extends AudiEntity {
	
	private static final long serialVersionUID = 1L;

    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
	private String docTypeCode;// 公文类型代码值
	private String docTypeName;// 公文类型名称
	private Integer initLetterNum;// 初始函号/期许号
	private Integer maxNum;// 当前最大函号/期许号
	private Timestamp startTime;// 函号/期许号有效起始日期
	private Timestamp endTime;// 函号/期许号有效终止日期
	private String prefix;//前缀
	private String suffix;//后缀
	
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the docTypeCode
	 */
	public String getDocTypeCode() {
		return docTypeCode;
	}

	/**
	 * @param docTypeCode the docTypeCode to set
	 */
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}

	/**
	 * @return the docTypeName
	 */
	public String getDocTypeName() {
		return docTypeName;
	}

	/**
	 * @param docTypeName the docTypeName to set
	 */
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	/**
	 * @return the initLetterNum
	 */
	public Integer getInitLetterNum() {
		return initLetterNum;
	}

	/**
	 * @param initLetterNum the initLetterNum to set
	 */
	public void setInitLetterNum(Integer initLetterNum) {
		this.initLetterNum = initLetterNum;
	}

	/**
	 * @return the maxNum
	 */
	public Integer getMaxNum() {
		return maxNum;
	}

	/**
	 * @param maxNum the maxNum to set
	 */
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	/**
	 * @return the startTime
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
