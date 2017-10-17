package com.sinosoft.xf.petition.counter.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * 业务范围外信息
 * @author hxh
 * @date 2013-08-05
 */
@Entity
@Table(name = "UNBUSINESS_COUNTER_INFO")
public class UnbusinessCounterInfo extends CounterInfo{
	private static final long serialVersionUID = 1L;

	private int repeatNum;//重复件数量
	
	//业务返回外子项计数信息
    private Set<UnbusinessChildInfo> unbusinessChildInfo = 
    		    new HashSet<UnbusinessChildInfo>();
    
    /**
	 * @return the unbusinessChildRelationInfo
	 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "unbusinessCounterInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<UnbusinessChildInfo> getUnbusinessChildInfo() {
		return unbusinessChildInfo;
	}

    /**
	 * @param unbusinessChildInfo the unbusinessChildInfo to set
	 */
	public void setUnbusinessChildInfo(
			Set<UnbusinessChildInfo> unbusinessChildInfo) {
		this.unbusinessChildInfo = unbusinessChildInfo;
	}

	/**
	 * @return the repeatNum
	 */
	public int getRepeatNum() {
		return repeatNum;
	}

	/**
	 * @param repeatNum the repeatNum to set
	 */
	public void setRepeatNum(int repeatNum) {
		this.repeatNum = repeatNum;
	}
	
}

