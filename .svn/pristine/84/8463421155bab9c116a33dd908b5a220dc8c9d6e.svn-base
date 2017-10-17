package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfoTemp;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 多媒体附件关联信息
 * @date 2012-04-20
 * @author sunzhe
 */
@Entity
@Table(name = "Petition_Multi_Media_Info_temp")
public class PetitionMultiMediaInfoTemp  extends AudiEntity{
	private static final long serialVersionUID = 1L;
	
	private PetitionBasicInfoTemp petitionBasicInfoTemp;
	private String petitionNo;//信访编号
	private String regionCode;//机构区域编码
	private String regionName;//机构名称
	private String tempFlag;
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
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionBasicInfoTempOid")
	public PetitionBasicInfoTemp getPetitionBasicInfoTemp() {
		return petitionBasicInfoTemp;
	}

	public void setPetitionBasicInfoTemp(PetitionBasicInfoTemp petitionBasicInfoTemp) {
		this.petitionBasicInfoTemp = petitionBasicInfoTemp;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
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
	public String getTempFlag() {
		return tempFlag;
	}
	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
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
	
}
