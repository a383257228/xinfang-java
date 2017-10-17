package com.sinosoft.xf.petition.counter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.sinosoftframework.core.domain.IdEntity;
/**
 * 业务范围外子项信息
 * @author hxh
 * @date 2013-08-05
 */
@Entity
@Table(name = "UNBUSINESS_CHILD_INFO")
public class UnbusinessChildInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String issueTypeCode;
	private String issueTypeName;
	private int total;//总数量
	private int repeatNum;//重复件数量
    private UnbusinessCounterInfo unbusinessCounterInfo;
    
    /**
	 * @return the unbusinessCounterInfo
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinColumn(name="unbusinessCounterInfoOid")
	public UnbusinessCounterInfo getUnbusinessCounterInfo() {
		return unbusinessCounterInfo;
	}

    /**
	 * @param unbusinessCounterInfo the unbusinessCounterInfo to set
	 */
	public void setUnbusinessCounterInfo(
			UnbusinessCounterInfo unbusinessCounterInfo) {
		this.unbusinessCounterInfo = unbusinessCounterInfo;
	}

	public String getIssueTypeCode() {
		return issueTypeCode;
	}

	public void setIssueTypeCode(String issueTypeCode) {
		this.issueTypeCode = issueTypeCode;
	}

	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(int repeatNum) {
		this.repeatNum = repeatNum;
	}

	
}

