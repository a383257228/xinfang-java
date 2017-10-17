/**
 * Copyright (c) sinosoft January 12 2011
 * 中科软科技股份有限公司  行业推广部
 * Create January 12 2011
 * @author mgb
 */
package com.sinosoft.xf.document.docissue.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 项目名称：信访二期
 * 类名称：Xfsvariable
 * 类描述：系统配置信息
 * 创建人： wangxx
 * 创建时间：2011-05-31
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "XFSVARIABLE")
@IdClass(Xfsvariable.class)
public class Xfsvariable  implements java.io.Serializable {

	//代码
	private String sysvarcode;
	//名称
	private String sysvarname;
	//值
	private String sysvarvalue;
	/**
	 * @return the sysvarcode
	 */
	@Column(name = "SYSVARCODE", length = 100)
	@Id
	public String getSysvarcode() {
		return sysvarcode;
	}
	/**
	 * @param sysvarcode the sysvarcode to set
	 */
	public void setSysvarcode(String sysvarcode) {
		this.sysvarcode = sysvarcode;
	}
	/**
	 * @return the sysvarname
	 */
	public String getSysvarname() {
		return sysvarname;
	}
	/**
	 * @param sysvarname the sysvarname to set
	 */
	public void setSysvarname(String sysvarname) {
		this.sysvarname = sysvarname;
	}
	/**
	 * @return the sysvarvalue
	 */
	public String getSysvarvalue() {
		return sysvarvalue;
	}
	/**
	 * @param sysvarvalue the sysvarvalue to set
	 */
	public void setSysvarvalue(String sysvarvalue) {
		this.sysvarvalue = sysvarvalue;
	}
}