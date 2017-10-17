package com.sinosoft.xf.document.docissue.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 存储公文基本信息
 * 
 * @date 2011-04-19
 * @author wanghang
 */
@Entity
@Table(name = "Doc_Info")
public class DocInfo extends AudiEntity {

	private static final long serialVersionUID = 1L;

	private TempDefineInfo tempDefineInfo;// 公文模板定义表OID
    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
	private String docName;// 当前状态
	private String docState;// 当前状态
	private String docTypeCode;// 公文类型代码值
	private String docTypeName;// 公文类型名称
	private Integer docLetterNum;// 公文函号/期序号数
	private String docLetterText;// 公文函号/期序号内容
	private String secretGrade;// 密级
	private String secretDuration;// 保密期限
	private String masthead;// 报头
	private String subMasthead;// 子报头
	private String publishUnit;// 编印单位
	private Date compileDate;// 编写日期
	private String title;// 标题
	private String docContent;// 正文
	private String to;// 主送
	private String rs;// 报送
	private String cc;// 抄送
	private String send;// 发
	private String asend;// 增发
	private Integer nt;// 印数
	private String docPath;// 公文存储路径
	private String pdfPath;// pdf存储路径
	private Integer printNum;// 打印次数
	private String validFlag;// 有效标识
	private String archivedFlag;// 归档标识
	private String organizationInfoOid;//存储操作部门
	private String orgCname;//存储操作部门
	private String leaderCode;//提交批示领导code
	private String leaderName;//提交批示领导name
	private Timestamp createNoDate;//取号日期
	private Set<DocInstructInfo> docInstructInfo = new HashSet<DocInstructInfo>();
	private Set<DocLetterInfo> docLetterInfo = new HashSet<DocLetterInfo>();
	private DocRelationInfo docRelationInfo;
	private DocumentInfo documentInfo = new DocumentInfo();
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "docInfo")
	public DocumentInfo getDocumentInfo() {
		return documentInfo;
	}
	public void setDocumentInfo(DocumentInfo documentInfo) {
		this.documentInfo = documentInfo;
	}
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "docInfo")
	public DocRelationInfo getDocRelationInfo() {
		return docRelationInfo;
	}
	public void setDocRelationInfo(DocRelationInfo docRelationInfo) {
		this.docRelationInfo = docRelationInfo;
	}
	/**
	 * @return the docLetterInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "docInfo")
	public Set<DocLetterInfo> getDocLetterInfo() {
		return docLetterInfo;
	}
	/**
	 * @param docLetterInfo the docLetterInfo to set
	 */
	public void setDocLetterInfo(Set<DocLetterInfo> docLetterInfo) {
		this.docLetterInfo = docLetterInfo;
	}
	/**
	 * @return the docInstructInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "docInfo")
	public Set<DocInstructInfo> getDocInstructInfo() {
		return docInstructInfo;
	}
	/**
	 * @param docInstructInfo the docInstructInfo to set
	 */
	public void setDocInstructInfo(Set<DocInstructInfo> docInstructInfo) {
		this.docInstructInfo = docInstructInfo;
	}
	/**
	 * @return the tempDefineInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "tempDefineInfoOid")
	public TempDefineInfo getTempDefineInfo() {
		return tempDefineInfo;
	}
	/**
	 * @param tempDefineInfo the tempDefineInfo to set
	 */
	public void setTempDefineInfo(TempDefineInfo tempDefineInfo) {
		this.tempDefineInfo = tempDefineInfo;
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
	 * @return the docState
	 */
	public String getDocState() {
		return docState;
	}
	/**
	 * @param docState the docState to set
	 */
	public void setDocState(String docState) {
		this.docState = docState;
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
	 * @return the secretGrade
	 */
	public String getSecretGrade() {
		return secretGrade;
	}
	/**
	 * @param secretGrade the secretGrade to set
	 */
	public void setSecretGrade(String secretGrade) {
		this.secretGrade = secretGrade;
	}
	/**
	 * @return the secretDuration
	 */
	public String getSecretDuration() {
		return secretDuration;
	}
	/**
	 * @param secretDuration the secretDuration to set
	 */
	public void setSecretDuration(String secretDuration) {
		this.secretDuration = secretDuration;
	}
	/**
	 * @return the masthead
	 */
	public String getMasthead() {
		return masthead;
	}
	/**
	 * @param masthead the masthead to set
	 */
	public void setMasthead(String masthead) {
		this.masthead = masthead;
	}
	/**
	 * @return the subMasthead
	 */
	public String getSubMasthead() {
		return subMasthead;
	}
	/**
	 * @param subMasthead the subMasthead to set
	 */
	public void setSubMasthead(String subMasthead) {
		this.subMasthead = subMasthead;
	}
	/**
	 * @return the publishUnit
	 */
	public String getPublishUnit() {
		return publishUnit;
	}
	/**
	 * @param publishUnit the publishUnit to set
	 */
	public void setPublishUnit(String publishUnit) {
		this.publishUnit = publishUnit;
	}
	/**
	 * @return the compileDate
	 */
	public Date getCompileDate() {
		return compileDate;
	}
	/**
	 * @param compileDate the compileDate to set
	 */
	public void setCompileDate(Date compileDate) {
		this.compileDate = compileDate;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the docContent
	 */
	public String getDocContent() {
		return docContent;
	}
	/**
	 * @param docContent the docContent to set
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return the rs
	 */
	public String getRs() {
		return rs;
	}
	/**
	 * @param rs the rs to set
	 */
	public void setRs(String rs) {
		this.rs = rs;
	}
	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}
	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}
	/**
	 * @return the send
	 */
	public String getSend() {
		return send;
	}
	/**
	 * @param send the send to set
	 */
	public void setSend(String send) {
		this.send = send;
	}
	/**
	 * @return the asend
	 */
	public String getAsend() {
		return asend;
	}
	/**
	 * @param asend the asend to set
	 */
	public void setAsend(String asend) {
		this.asend = asend;
	}
	/**
	 * @return the nt
	 */
	public Integer getNt() {
		return nt;
	}
	/**
	 * @param nt the nt to set
	 */
	public void setNt(Integer nt) {
		this.nt = nt;
	}
	/**
	 * @return the docPath
	 */
	public String getDocPath() {
		return docPath;
	}
	/**
	 * @param docPath the docPath to set
	 */
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	/**
	 * @return the pdfPath
	 */
	public String getPdfPath() {
		return pdfPath;
	}
	/**
	 * @param pdfPath the pdfPath to set
	 */
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	/**
	 * @return the printNum
	 */
	public Integer getPrintNum() {
		return printNum;
	}
	/**
	 * @param printNum the printNum to set
	 */
	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
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
	/**
	 * @return the archivedFlag
	 */
	public String getArchivedFlag() {
		return archivedFlag;
	}
	/**
	 * @param archivedFlag the archivedFlag to set
	 */
	public void setArchivedFlag(String archivedFlag) {
		this.archivedFlag = archivedFlag;
	}
	/**
	 * @return the organizationInfoOid
	 */
	public String getOrganizationInfoOid() {
		return organizationInfoOid;
	}
	/**
	 * @param organizationInfoOid the organizationInfoOid to set
	 */
	public void setOrganizationInfoOid(String organizationInfoOid) {
		this.organizationInfoOid = organizationInfoOid;
	}
	public String getLeaderCode() {
		return leaderCode;
	}
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public Timestamp getCreateNoDate() {
		return createNoDate;
	}
	public void setCreateNoDate(Timestamp createNoDate) {
		this.createNoDate = createNoDate;
	}
	public String getOrgCname() {
		return orgCname;
	}
	public void setOrgCname(String orgCname) {
		this.orgCname = orgCname;
	}
}
