/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 14 2010
 * @author wangxx
 */
package com.sinosoft.organization.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 用户信息表
 * @author wangxx
 * 2010-05-14
 */
@Entity
@Table(name = "Person_Info")
//@Cache(region = "com.sinosoft.organization.domain.PersonInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonInfo extends IdEntity{
	private static final long serialVersionUID = 1L; 
	
    //用户英文名称
	@Column(name="User_EName")
    private String userEname;

    //用户中文名称
	@Column(name="User_CName")
    private String userCname;

    //登陆密码
	@Column(name="User_Pwd")
    private String userPwd;

    //注册日期
	@Column(name="Create_Date")
    private Timestamp createDate;

    //修改日期
	@Column(name="Modify_Date")
    private Timestamp modifyDate;

    //失效日期
	@Column(name="Invalid_Date")
    private Timestamp invalidDate;

    //是否失效
	@Column(name="Invalid_Flag")
    private String invalidFlag;
    
	//是否领导,1领导,2秘书,3办信人员,4业务管理员,5委领导
	@Column(name="Leader_Flag")
	private String leaderFlag;
	
	//用户描述
	@Column(name="User_Description")
	private String userDescription;

	//操作人
	@Column(name="Operator")
	private String operator;
	
	//操作人姓名
	private String operatorName;
	
	//用户编码
	@Column(name="User_Code")
	private String userCode;
	//用户电话
	private String telephone;
	//md5密码
	private String md5Pwd;
	private String regionCode;
	private String regionName;
	
	//用户编码
	@Column(name="Query_Count")
	private Integer queryCount=0;
	private Integer defaultDealerNameOrder=0;
	//2015-10-23 liub当前用户的全部下级机构,用于签收所属区域的判断
	private String downIssueRegionCode;
	/**
	 * 保存平台用户ID
	 * @author ZHZ
	 * Oct 25, 2016
	 */
	private String innerCode;
	/**
	 * 平台首页  默认null，1:图形平台，2:文字平台
	 */
	private String platformHomePage;
	
	//机构人员关系信息
    private Set<OrganPersonRelationInfo> organPersonRelationInfo = new HashSet<OrganPersonRelationInfo>();
    
	/**
	 * @return the organPersonRelationInfo
	 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<OrganPersonRelationInfo> getOrganPersonRelationInfo() {
		return organPersonRelationInfo;
	}

	/**
	 * @param organPersonRelationInfo the organPersonRelationInfo to set
	 */
	public void setOrganPersonRelationInfo(
			Set<OrganPersonRelationInfo> organPersonRelationInfo) {
		this.organPersonRelationInfo = organPersonRelationInfo;
	}

	/**
	 * 获取用户是否失效标志
	 * @return String 用户是否失效标志
	 */
	public String getInvalidFlag() {
		return invalidFlag;
	}

	/**
	 * 注入用户是否失效标志
	 * @param invalidFlag 
	 * 			用户是否失效标志
	 */
	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}

    public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the invalidDate
	 */
	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	/**
	 * @param invalidDate the invalidDate to set
	 */
	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}
	
	

	public String getLeaderFlag() {
		return leaderFlag;
	}

	public void setLeaderFlag(String leaderFlag) {
		this.leaderFlag = leaderFlag;
	}

	/**
	 * 获取用户密码
	 * @return String 用户密码
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * 注入用户密码
	 * @param userPwd 
	 * 			用户密码
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * @return the userEname
	 */
	public String getUserEname() {
		return userEname;
	}

	/**
	 * @param userEname the userEname to set
	 */
	public void setUserEname(String userEname) {
		this.userEname = userEname;
	}

	/**
	 * @return the userCname
	 */
	public String getUserCname() {
		return userCname;
	}

	/**
	 * @param userCname the userCname to set
	 */
	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}

	/**
	 * @return the userDescription
	 */
	public String getUserDescription() {
		return userDescription;
	}

	/**
	 * @param userDescription the userDescription to set
	 */
	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
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
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public String getMd5Pwd() {
		return md5Pwd;
	}

	public void setMd5Pwd(String md5Pwd) {
		this.md5Pwd = md5Pwd;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}

	public Integer getDefaultDealerNameOrder() {
		return defaultDealerNameOrder;
	}

	public void setDefaultDealerNameOrder(Integer defaultDealerNameOrder) {
		this.defaultDealerNameOrder = defaultDealerNameOrder;
	}

	public String getPlatformHomePage() {
		return platformHomePage;
	}

	public void setPlatformHomePage(String platformHomePage) {
		this.platformHomePage = platformHomePage;
	}
	
	@Transient
	public String getDownIssueRegionCode() {
		return downIssueRegionCode;
	}

	public void setDownIssueRegionCode(String downIssueRegionCode) {
		this.downIssueRegionCode = downIssueRegionCode;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

}
