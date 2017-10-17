package com.sinosoft.xf.petition.accept.domain;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 导入回馈数据
 * @date 2013-03-25
 * @author ZPJ
 */
@Entity
@Table(name = "PETITION_IMPORT_RECEIVE_INFO")
public class PetitionImportReceiveInfo extends AudiEntity {
	
	private static final long serialVersionUID = 1L;
	private String regionCode;
	private String regionName;
	private String fileName;//导入文件名称
	private int containFileNum;//包含文件数量
	private String fileSourceType;//包类型 3为电话，4为网络
	private Set<PetitionImportDetailInfo> petitionImportDetailInfo =new HashSet<PetitionImportDetailInfo>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionImportReceiveInfo")
	public Set<PetitionImportDetailInfo> getPetitionImportDetailInfo() {
		return petitionImportDetailInfo;
	}

	public void setPetitionImportDetailInfo(
			Set<PetitionImportDetailInfo> petitionImportDetailInfo) {
		this.petitionImportDetailInfo = petitionImportDetailInfo;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSourceType() {
		return fileSourceType;
	}
	public void setFileSourceType(String fileSourceType) {
		this.fileSourceType = fileSourceType;
	}

	public int getContainFileNum() {
		return containFileNum;
	}

	public void setContainFileNum(int containFileNum) {
		this.containFileNum = containFileNum;
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
