package com.sinosoft.xf.petition.petitionaccept.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 重复件规则设置 entity.
 * @author ljx
 * @createDate 2011-09-21
 */
@Entity
@Table(name = "REPEATRULEINFO")
public class RepeatRule extends IdEntity {


	private static final long serialVersionUID = 3520895729634630425L;
	
	private String xftype;
	private String xfclass;
	private String xfsource;
	private String xfissue;
	private String effectDate;
	private String createDate;
	private String valid;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getXftype() {
		return this.xftype;
	}

	public void setXftype(String xftype) {
		this.xftype = xftype;
	}

	public String getXfclass() {
		return this.xfclass;
	}

	public void setXfclass(String xfclass) {
		this.xfclass = xfclass;
	}

	public String getXfsource() {
		return this.xfsource;
	}

	public void setXfsource(String xfsource) {
		this.xfsource = xfsource;
	}

	public String getXfissue() {
		return this.xfissue;
	}

	public void setXfissue(String xfissue) {
		this.xfissue = xfissue;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	

}