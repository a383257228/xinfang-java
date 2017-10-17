package com.sinosoft.xf.document.docissue.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 公文批示信息domain
 * @date 2011-05-17
 * @author wangxx
 */
@Entity
@Table(name = "Doc_Instruct_Info")
public class DocInstructInfo extends AudiEntity {

	private static final long serialVersionUID = 1L;

	private DocInfo docInfo;//信访公文信息表OID
    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
	private int orderNum;//批示顺序序号
	private String docInstructClassCode;//公文呈阅批示类型代码
	private String docInstructClassName;//公文呈阅批示类型代码值
	private Timestamp instructTime;//呈阅批示时间
	private String leaderCode;//呈阅批示领导编码
	private String leaderName;//呈阅批示领导姓名
	private String leaderClassCode;//呈阅批示领导职级代码
	private String leaderClassName;//呈阅批示领导职级代码值
	private String instruction;//呈阅批示意见
	private String validFlag;//有效标识 1有效，0无效
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
	 * @return the orderNum
	 */
	public int getOrderNum() {
		return orderNum;
	}
	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * @return the docInstructClassCode
	 */
	public String getDocInstructClassCode() {
		return docInstructClassCode;
	}
	/**
	 * @param docInstructClassCode the docInstructClassCode to set
	 */
	public void setDocInstructClassCode(String docInstructClassCode) {
		this.docInstructClassCode = docInstructClassCode;
	}
	/**
	 * @return the docInstructClassName
	 */
	public String getDocInstructClassName() {
		return docInstructClassName;
	}
	/**
	 * @param docInstructClassName the docInstructClassName to set
	 */
	public void setDocInstructClassName(String docInstructClassName) {
		this.docInstructClassName = docInstructClassName;
	}
	/**
	 * @return the instructTime
	 */
	public Timestamp getInstructTime() {
		return instructTime;
	}
	/**
	 * @param instructTime the instructTime to set
	 */
	public void setInstructTime(Timestamp instructTime) {
		this.instructTime = instructTime;
	}
	/**
	 * @return the leaderCode
	 */
	public String getLeaderCode() {
		return leaderCode;
	}
	/**
	 * @param leaderCode the leaderCode to set
	 */
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}
	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}
	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	/**
	 * @return the leaderClassCode
	 */
	public String getLeaderClassCode() {
		return leaderClassCode;
	}
	/**
	 * @param leaderClassCode the leaderClassCode to set
	 */
	public void setLeaderClassCode(String leaderClassCode) {
		this.leaderClassCode = leaderClassCode;
	}
	/**
	 * @return the leaderClassName
	 */
	public String getLeaderClassName() {
		return leaderClassName;
	}
	/**
	 * @param leaderClassName the leaderClassName to set
	 */
	public void setLeaderClassName(String leaderClassName) {
		this.leaderClassName = leaderClassName;
	}
	/**
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}
	/**
	 * @param instruction the instruction to set
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
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
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
