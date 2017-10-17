package com.sinosoft.xf.query.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 保存查询信息
 * @date 2012-03-06
 * @author mengxy
 */
@Entity
@Table(name = "Petition_Query_save")
public class PetitionQuerySave extends IdEntity {
	private static final long serialVersionUID = 1L; 
	private String queryName;//查询名称
	private String itemColumn;//查询项字段
	private String itemValue;//查询项输入值
	private String operator;//操作人
	/**
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}
	/**
	 * @param queryName the queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	/**
	 * @return the itemColumn
	 */
	public String getItemColumn() {
		return itemColumn;
	}
	/**
	 * @param itemColumn the itemColumn to set
	 */
	public void setItemColumn(String itemColumn) {
		this.itemColumn = itemColumn;
	}
	/**
	 * @return the itemValue
	 */
	public String getItemValue() {
		return itemValue;
	}
	/**
	 * @param itemValue the itemValue to set
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
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
	
}
