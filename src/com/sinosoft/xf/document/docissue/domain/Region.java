package com.sinosoft.xf.document.docissue.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 所属区域
 * @date 2011-08-02
 * @author wangxx
 * 2011-08-02
 */
@Entity
@Table(name = "XFSRegion")
public class Region  {
	
	private static final long serialVersionUID = 1L; 
	
	
	//反映地区代码
	@Id
    @Column(name="REGIONCODE")
    private String regioncode;
    
	//省级代码
	@Column(name="PROVINCECODE")
    private String provincecode;
	
	//市级代码
	@Column(name="CITYCODE")
    private String citycode;
	
	//县级代码
	@Column(name="COUNTYCODE")
    private String countycode;
	
	//行政区域层次代码
	@Column(name="LEVELCODE")
    private String levelcode;
	
	//行政区域名称
	@Column(name="REGIONNAME")
    private String regionname;
	
	//行政区域简称
	@Column(name="REGIONSHORTNAME")
    private String regionshortname;
	
	//下级行政区域个数
	@Column(name="CHILDNUM")
    private String childnum;
	
	//修改标志
	@Column(name="MODFLAG")
    private String modflag;
	
	//机构标志
	@Column(name="REGIONFLAG")
    private String regionflag;
	
	//上级行政区域代码
	@Column(name="PARENTREGIONCODE")
    private String parentregioncode;
	
	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getProvincecode() {
		return provincecode;
	}

	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCountycode() {
		return countycode;
	}

	public void setCountycode(String countycode) {
		this.countycode = countycode;
	}

	public String getLevelcode() {
		return levelcode;
	}

	public void setLevelcode(String levelcode) {
		this.levelcode = levelcode;
	}

	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public String getRegionshortname() {
		return regionshortname;
	}

	public void setRegionshortname(String regionshortname) {
		this.regionshortname = regionshortname;
	}

	public String getChildnum() {
		return childnum;
	}

	public void setChildnum(String childnum) {
		this.childnum = childnum;
	}

	public String getModflag() {
		return modflag;
	}

	public void setModflag(String modflag) {
		this.modflag = modflag;
	}

	public String getRegionflag() {
		return regionflag;
	}

	public void setRegionflag(String regionflag) {
		this.regionflag = regionflag;
	}

	public String getParentregioncode() {
		return parentregioncode;
	}

	public void setParentregioncode(String parentregioncode) {
		this.parentregioncode = parentregioncode;
	}


	
	
}






