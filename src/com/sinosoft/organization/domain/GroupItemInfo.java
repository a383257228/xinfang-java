package com.sinosoft.organization.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Group_Item_Info")//机构信息表
//@Cache(region = "com.sinosoft.organization.domain.GroupItemInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupItemInfo extends IdEntity {
	
    private static final long serialVersionUID = 1L;

	private GroupInfo groupInfo;//组ID
	
	private String objOid;//对象id
	
	private String objType;//类型(人员或部门)
	
	private Timestamp operateDate;//操作日期
	
	private String operator;//操作人

	//操作人姓名
	private String operatorName;
	
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

	/**
	 * @return the groupInfo
	 */
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "Group_Oid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	/**
	 * @param groupInfo the groupInfo to set
	 */
	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}


	/**
	 * @return the objOid
	 */
	public String getObjOid() {
		return objOid;
	}

	/**
	 * @param objOid the objOid to set
	 */
	public void setObjOid(String objOid) {
		this.objOid = objOid;
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
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}


}
