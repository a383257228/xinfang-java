package com.sinosoft.xf.document.docissue.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 公文的文件存储信息
 * @author oba
 */
@Entity
@Table(name = "Document_Info")
public class DocumentInfo extends IdEntity{
	
	private static final long serialVersionUID = 1L;
	
	DocInfo docInfo;
	
	private byte[] documentFile; //word文件
	
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "docInfoOid")
	public DocInfo getDocInfo() {
		return docInfo;
	}
	public void setDocInfo(DocInfo docInfo) {
		this.docInfo = docInfo;
	}
	public byte[] getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(byte[] documentFile) {
		this.documentFile = documentFile;
	}
}
