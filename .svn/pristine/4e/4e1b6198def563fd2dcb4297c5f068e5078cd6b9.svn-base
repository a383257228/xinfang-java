package com.sinosoft.xf.petition.counter.domain;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 计数父类
 * @author hxh
 *@date 2013-08-09
 */
@MappedSuperclass
public abstract class CounterInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String issueRegionName;//所属区域
	private String issueRegionCode;
	private String regionCode;//所属区域
	private String regionName;
	private String operator;
	private String operatorName;
	private Timestamp operateDate; 
	private int total;//总数量
	
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
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
    
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Timestamp getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}

	public String getIssueRegionName() {
		return issueRegionName;
	}

	public void setIssueRegionName(String issueRegionName) {
		this.issueRegionName = issueRegionName;
	}

	public String getIssueRegionCode() {
		return issueRegionCode;
	}

	public void setIssueRegionCode(String issueRegionCode) {
		this.issueRegionCode = issueRegionCode;
	}


}
