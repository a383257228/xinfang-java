package com.sinosoft.xf.petition.deal.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 存储信访信息管理业务中信访件办理过程中采用转办下级的过程中的交邮信息。
 * @date 2013-1-7
 * @author zpj
 */
@Entity
@Table(name = "Petition_Trans_Mail_Info")
public class PetitionTransMailInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String mailListNo;//交邮清单编号
	private String expressNo;//挂号条码
	private String toRegionCode;//接收机构编码
	private String toRegionName;//接收结构编码值
	private String printFlag;//打印标识 0未打印 ；1打印
	
	@Column(name = "Mail_List_No", length = 20)
	public String getMailListNo() {
		return mailListNo;
	}
	
	public void setMailListNo(String mailListNo) {
		this.mailListNo = mailListNo;
	}
	
	@Column(name = "Express_No", length = 50)
	public String getExpressNo() {
		return expressNo;
	}
	
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	@Column(name = "To_Region_Code", length = 12)
	public String getToRegionCode() {
		return toRegionCode;
	}
	
	public void setToRegionCode(String toRegionCode) {
		this.toRegionCode = toRegionCode;
	}
	
	@Column(name = "To_Region_Name", length = 100)
	public String getToRegionName() {
		return toRegionName;
	}
	
	public void setToRegionName(String toRegionName) {
		this.toRegionName = toRegionName;
	}
	
	@Column(name = "Print_Flag", length = 1)
	public String getPrintFlag() {
		return printFlag;
	}
	
	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
}