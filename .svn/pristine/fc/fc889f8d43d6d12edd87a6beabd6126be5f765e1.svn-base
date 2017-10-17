package com.sinosoft.organization.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 机构分组信息实体类
 * 2010-10-11
 * @author wangxx
 */
@Entity
@Table(name = "Group_Info")//机构信息表
//@Cache(region = "com.sinosoft.organization.domain.GroupInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupInfo extends IdEntity {
	
    private static final long serialVersionUID = 1L;

	private String groupCode;//机构分组编码
	
	private String groupName;//机构分组名称
	
	private Timestamp operateDate;//操作日期
	
	private String operator;//操作人

	//操作人姓名
	private String operatorName;
	//分组内容信息
	private Set<GroupItemInfo> groupItemInfo = new HashSet<GroupItemInfo>();
	
	/**
	 * @return the groupItemInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupInfo")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<GroupItemInfo> getGroupItemInfo() {
		return groupItemInfo;
	}

	/**
	 * @param groupItemInfo the groupItemInfo to set
	 */
	public void setGroupItemInfo(Set<GroupItemInfo> groupItemInfo) {
		this.groupItemInfo = groupItemInfo;
	}
	
	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	/**
	 * @return the operateDate
	 */
	public Timestamp getOperateDate() {
		return operateDate;
	}

	/**
	 * @param operateDate the operateDate to set
	 */
	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
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
}
