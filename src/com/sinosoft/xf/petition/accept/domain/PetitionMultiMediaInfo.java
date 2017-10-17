package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 多媒体附件关联信息
 * @date 2012-04-20
 * @author sunzhe
 */
@Entity
@Table(name = "Petition_Multi_Media_Info")
public class PetitionMultiMediaInfo  extends AudiEntity{
	private static final long serialVersionUID = 1L;
	
	private String petitionNo;//信访编号
	
	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;//机构区域编码
	
	private String regionName;//机构名称
	private String transFlag="0";
	//private FilePropertiesInfo filePropertiesInfo;//关联多媒体文件信息
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件大小
	 */
	private long fileSize;
	/**
	 * 文件存储路径
	 */
	private String filePath;
	
	
	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	/*@OneToOne(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "fileInfoOid")
	public FilePropertiesInfo getFilePropertiesInfo() {
		return filePropertiesInfo;
	}

	public void setFilePropertiesInfo(FilePropertiesInfo filePropertiesInfo) {
		this.filePropertiesInfo = filePropertiesInfo;
	}*/
	
}
