package com.sinosoft.xf.petition.domainutil;

import java.io.Serializable;
import java.sql.Timestamp;
public class UnitCadreConsult implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	//Cadre表
    private String positionCode;
    private String positionName;
    private String classCode;
    private String className;
    private String partyCode;
    private String partyName;
    private String sexCode;
    private Timestamp birthday;
    private String remark;
    private String flag;//0干部表查到的结果,1征询查询表查到的结果
    //Unit表
    private String unitName;
    private String unitTypeName;
    private String unitTypeCode;
    //Consult表
    private String unitPosition;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getSexCode() {
		return sexCode;
	}
	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitTypeName() {
		return unitTypeName;
	}
	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}
	public String getUnitTypeCode() {
		return unitTypeCode;
	}
	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}
	public String getUnitPosition() {
		return unitPosition;
	}
	public void setUnitPosition(String unitPosition) {
		this.unitPosition = unitPosition;
	}
}