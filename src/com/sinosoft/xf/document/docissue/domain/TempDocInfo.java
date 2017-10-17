package com.sinosoft.xf.document.docissue.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 模板文件信息
 * @author oba
 */
@Entity
@Table(name = "Temp_doc_Info")
public class TempDocInfo extends IdEntity{
	
	private static final long serialVersionUID = 1L;
	
	TempDefineInfo tempDefineInfo;
	
	private byte[] documentFile; //word文件
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "tempDefineInfoOid")
	public TempDefineInfo getTempDefineInfo() {
		return tempDefineInfo;
	}
	public void setTempDefineInfo(TempDefineInfo tempDefineInfo) {
		this.tempDefineInfo = tempDefineInfo;
	}
	public byte[] getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(byte[] documentFile) {
		this.documentFile = documentFile;
	}
	
}
