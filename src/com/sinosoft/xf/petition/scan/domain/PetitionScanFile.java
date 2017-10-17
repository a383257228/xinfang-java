package com.sinosoft.xf.petition.scan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 *扫描文件存放信息
 *scanType
 *String codes="1,2,3,4,5,6,7,8,9";
 *  String name="原信,重要信访拟办单,办理方案呈批表,直接核实呈批表,信访谈话呈批表,函询单,通知书,交办函,结案呈批表"
 */
@Entity
@Table(name = "PETITION_SCAN_FILE")
public class PetitionScanFile extends AudiEntity{
	private static final long serialVersionUID = 1L;
	private String regionCode;
	private String regionName;
	private String petitionNo;
	private String currPetitionNo;
	private String petitionPrtNo;
	private String scanType;
	private String fileType;
	private String filePath;
	private String deptCode;
	private String deptName;
	private String pageCount;
	private String secretKey;
	private String invalidFlag;
	private String fileContent;
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
	public String getPetitionPrtNo() {
		return petitionPrtNo;
	}
	public void setPetitionPrtNo(String petitionPrtNo) {
		this.petitionPrtNo = petitionPrtNo;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getPetitionNo() {
		return petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}
	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getInvalidFlag() {
		return invalidFlag;
	}
	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
}
