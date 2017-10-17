package com.sinosoft.xf.petition.end.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 了结报告实体类信息
 * @author oba
 */
@Entity
@Table(name = "end_report_info")
public class EndReportInfo extends IdEntity{

	private static final long serialVersionUID = 1L;

	private byte[] endReport;
	private PetitionEndInfo endInfo;
	/**
	 * @return the petitionDealInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionEndInfoOid")
	public PetitionEndInfo getEndInfo() {
		return endInfo;
	}
	public void setEndInfo(PetitionEndInfo endInfo) {
		this.endInfo = endInfo;
	}
	public byte[] getEndReport() {
		return endReport;
	}
	public void setEndReport(byte[] endReport) {
		this.endReport = endReport;
	}
}
