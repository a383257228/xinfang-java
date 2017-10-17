package com.sinosoft.xf.document.docissue.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 存储公文函号/期序号使用信息
 * 
 * @author wanghang
 * @createDat 2011-04-19
 */
@Entity
@Table(name = "Doc_Letter_Info")
public class DocLetterInfo extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	private DocInfo docInfo;//公文对象
    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
	private String docTypeCode;// 公文类型代码值
	private String docTypeName;// 公文类型名称
	private Integer docLetterNum;// 公文函号/期序号数
	private String docLetterText;// 公文函号/期序号内容
	private String retrieveFlag;// 回收标识 1是已回收利用，0是回收未利用，2表示未回收
	private Timestamp retrieveTime;// 回收时间
	private String retrieveOperatorId;// 回收人标识
	private String retrieveOperatorName;// 回收人名称
	private String retrieveReason;// 回收说明
	private String cancelFlag;// 废号标识 1表示废号，0或空表示不是废号
	private Timestamp cancelTime;// 废号时间
	private String cancelOperatorId;// 废号人标识
	private String cancelOperatorName;// 废号人名称
	private String cancelReason;// 废号说明
	private String reuseFlag;// 有效标识
	private String year;//存放使用年信息，暂时按自然年计算

	/**
	 * @return the docInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "docInfoOid")
	public DocInfo getDocInfo() {
		return docInfo;
	}
	/**
	 * @param docInfo the docInfo to set
	 */
	public void setDocInfo(DocInfo docInfo) {
		this.docInfo = docInfo;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
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
	 * @return the docLetterNum
	 */
	public Integer getDocLetterNum() {
		return docLetterNum;
	}
	/**
	 * @param docLetterNum the docLetterNum to set
	 */
	public void setDocLetterNum(Integer docLetterNum) {
		this.docLetterNum = docLetterNum;
	}
	/**
	 * @return the docLetterText
	 */
	public String getDocLetterText() {
		return docLetterText;
	}
	/**
	 * @param docLetterText the docLetterText to set
	 */
	public void setDocLetterText(String docLetterText) {
		this.docLetterText = docLetterText;
	}
	/**
	 * @return the retrieveFlag
	 */
	public String getRetrieveFlag() {
		return retrieveFlag;
	}
	/**
	 * @param retrieveFlag the retrieveFlag to set
	 */
	public void setRetrieveFlag(String retrieveFlag) {
		this.retrieveFlag = retrieveFlag;
	}
	/**
	 * @return the retrieveTime
	 */
	public Timestamp getRetrieveTime() {
		return retrieveTime;
	}
	/**
	 * @param retrieveTime the retrieveTime to set
	 */
	public void setRetrieveTime(Timestamp retrieveTime) {
		this.retrieveTime = retrieveTime;
	}
	/**
	 * @return the retrieveOperatorId
	 */
	public String getRetrieveOperatorId() {
		return retrieveOperatorId;
	}
	/**
	 * @param retrieveOperatorId the retrieveOperatorId to set
	 */
	public void setRetrieveOperatorId(String retrieveOperatorId) {
		this.retrieveOperatorId = retrieveOperatorId;
	}
	/**
	 * @return the retrieveOperatorName
	 */
	public String getRetrieveOperatorName() {
		return retrieveOperatorName;
	}
	/**
	 * @param retrieveOperatorName the retrieveOperatorName to set
	 */
	public void setRetrieveOperatorName(String retrieveOperatorName) {
		this.retrieveOperatorName = retrieveOperatorName;
	}
	/**
	 * @return the retrieveReason
	 */
	public String getRetrieveReason() {
		return retrieveReason;
	}
	/**
	 * @param retrieveReason the retrieveReason to set
	 */
	public void setRetrieveReason(String retrieveReason) {
		this.retrieveReason = retrieveReason;
	}
	/**
	 * @return the cancelFlag
	 */
	public String getCancelFlag() {
		return cancelFlag;
	}
	/**
	 * @param cancelFlag the cancelFlag to set
	 */
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	/**
	 * @return the cancelTime
	 */
	public Timestamp getCancelTime() {
		return cancelTime;
	}
	/**
	 * @param cancelTime the cancelTime to set
	 */
	public void setCancelTime(Timestamp cancelTime) {
		this.cancelTime = cancelTime;
	}
	/**
	 * @return the cancelOperatorId
	 */
	public String getCancelOperatorId() {
		return cancelOperatorId;
	}
	/**
	 * @param cancelOperatorId the cancelOperatorId to set
	 */
	public void setCancelOperatorId(String cancelOperatorId) {
		this.cancelOperatorId = cancelOperatorId;
	}
	/**
	 * @return the cancelOperatorName
	 */
	public String getCancelOperatorName() {
		return cancelOperatorName;
	}
	/**
	 * @param cancelOperatorName the cancelOperatorName to set
	 */
	public void setCancelOperatorName(String cancelOperatorName) {
		this.cancelOperatorName = cancelOperatorName;
	}
	/**
	 * @return the cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}
	/**
	 * @param cancelReason the cancelReason to set
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	/**
	 * @return the reuseFlag
	 */
	public String getReuseFlag() {
		return reuseFlag;
	}
	/**
	 * @param reuseFlag the reuseFlag to set
	 */
	public void setReuseFlag(String reuseFlag) {
		this.reuseFlag = reuseFlag;
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
}
