package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 导入回馈数据明细
 * @date 2013-03-25
 * @author ZPJ
 */
@Entity
@Table(name = "PETITION_IMPORT_DETAIL_INFO")
public class PetitionImportDetailInfo extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String petitionAcceptNo;//举报编码  
	private String dealType;//办理信息
	private String isDirectReportCode;//信件处理方式（1：直送件；2：业务处办理；3：省级处理）
	private String isDirectReportName;//信件处理方式（1：直送件；2：业务处办理；3：省级处理）
	private String processDept;////信件办理部门，在处理方式为2时有值。01：来信一处，02：来信二处，03：来信三处，04：来信四处
	private String importState; //导入状态
	private String currPetitionNo; //展示信访编号
	private String petitionNo; //信访编号
	private PetitionImportReceiveInfo petitionImportReceiveInfo;//属于的包信息
	
	public String getPetitionAcceptNo() {
		return petitionAcceptNo;
	}
	public void setPetitionAcceptNo(String petitionAcceptNo) {
		this.petitionAcceptNo = petitionAcceptNo;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getProcessDept() {
		return processDept;
	}
	public void setProcessDept(String processDept) {
		this.processDept = processDept;
	}
	public String getIsDirectReportCode() {
		return isDirectReportCode;
	}
	public void setIsDirectReportCode(String isDirectReportCode) {
		this.isDirectReportCode = isDirectReportCode;
	}
	public String getIsDirectReportName() {
		return isDirectReportName;
	}
	public void setIsDirectReportName(String isDirectReportName) {
		this.isDirectReportName = isDirectReportName;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionImportReceiveInfoOid")
	public PetitionImportReceiveInfo getPetitionImportReceiveInfo() {
		return petitionImportReceiveInfo;
	}
	public void setPetitionImportReceiveInfo(
			PetitionImportReceiveInfo petitionImportReceiveInfo) {
		this.petitionImportReceiveInfo = petitionImportReceiveInfo;
	}
	public String getImportState() {
		return importState;
	}
	public void setImportState(String importState) {
		this.importState = importState;
	}
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}
	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}
	public String getPetitionNo() {
		return petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	
}
