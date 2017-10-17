package com.sinosoft.xf.petition.counter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.sinosoftframework.core.domain.IdEntity;
/**
 * 无实质性质计数子项信息
 * @author hxh
 * @date 2013-08-09
 */
@Entity
@Table(name = "INSIGNIFICANCE_CHILD_COUNTER_INFO")
public class InsignificanceChildCounterInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String petitionTypeCode;
	private String petitionTypeName;
	private int total;//总数量
    private InsignificanceCounterInfo insignificanceCounterInfo;
    
    /**
	 * @return the insignificanceCounterInfo
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinColumn(name="insignificanceCounterInfoOid")
	public InsignificanceCounterInfo getInsignificanceCounterInfo() {
		return insignificanceCounterInfo;
	}

    /**
	 * @param unbusinessChildRelationInfo the unbusinessChildRelationInfo to set
	 */
	public void setInsignificanceCounterInfo(
			InsignificanceCounterInfo insignificanceCounterInfo) {
		this.insignificanceCounterInfo = insignificanceCounterInfo;
	}

	/**
	 * @return the petitionTypeCode
	 */
	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}

	/**
	 * @param petitionTypeCode the petitionTypeCode to set
	 */
	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode;
	}

	/**
	 * @return the petitionTypeName
	 */
	public String getPetitionTypeName() {
		return petitionTypeName;
	}

	/**
	 * @param petitionTypeName the petitionTypeName to set
	 */
	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
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

}

