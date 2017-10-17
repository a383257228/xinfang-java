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
 * 无实质性质计数信息
 * @author hxh
 * @date 2013-08-09
 */
@Entity
@Table(name = "INSIGNIFICANCE_COUNTER_INFO")
public class InsignificanceCounterInfo extends CounterInfo{
	private static final long serialVersionUID = 1L;
	
	//业务返回外计数关联信息
    private Set<InsignificanceChildCounterInfo> insignificanceChildCounterInfo = 
    		    new HashSet<InsignificanceChildCounterInfo>();
   
    
    /**
	 * @return the insignificanceChildCounterInfo
	 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "insignificanceCounterInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<InsignificanceChildCounterInfo> getInsignificanceChildCounterInfo() {
		return insignificanceChildCounterInfo;
	}

    /**
   	 * @param insignificanceChildCounterInfo the insignificanceChildCounterInfo to set
   	 */
	public void setInsignificanceChildCounterInfo(
			Set<InsignificanceChildCounterInfo> insignificanceChildCounterInfo) {
		this.insignificanceChildCounterInfo = insignificanceChildCounterInfo;
	}
	

	
}

