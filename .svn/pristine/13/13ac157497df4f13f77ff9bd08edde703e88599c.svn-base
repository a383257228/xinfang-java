/**
 * 中科软行业推广一部
 * @date 2011-09-10
 * @author Dusitn
 */
package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionDealLogInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionOverSeeInfo;
import com.sinosoft.xf.petition.instruct.domain.InstructInfo;
import com.sinosoft.xf.util.CommonUtil;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateTraceInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 信访基本信息表实体类信息
 * @date 2011-11-09
 * @author wangxx
 */
@Entity
@Table(name = "PETITION_BASIC_INFO")
public class PetitionBasicInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;
	private String petitionTitle;//信访件标题
	private String isDistributeFlag="0";  //是否经过秘书录入分发，0没有，1分发  。生成get和set方法
	
    private String encrypetitionTitle;
    @Transient
	public String getPetitionTitle() {
    	this.petitionTitle = Encrypt.decrypt(encrypetitionTitle);
    	return this.petitionTitle;
	}
	public void setPetitionTitle(String petitionTitle) {
		this.petitionTitle = petitionTitle;
		this.encrypetitionTitle = Encrypt.encrypt(petitionTitle);
	}
	@Column(name = "petition_Title")
	public String getEncrypetitionTitle() {
		return encrypetitionTitle;
	}
	public void setEncrypetitionTitle(String petitionTitle) {
		this.encrypetitionTitle = petitionTitle;
	}
	private String sensitiveFlag="0";//敏感件的处理 1表示敏感件，0表示否
	private String petitionNo;
	private String regionCode;
	private String regionName;
	private String petitionSeqNo;
	private String petitionPrtNo;
	private String petitionAbbrNo;
	private Timestamp petitionDate;
	private String petitionTypeCode;//1、来信 2、来访 3、电话举报 4、网络举报 5、其他
	private String petitionTypeName;
	private String subPetitionTypeCode;//电话举报方式：3001人工  3002留言   3003传真
	private String subPetitionTypeName;
	private String petitionTypeCodeTemp;//1、来信 2、来访 3、电话举报 4、网络举报 5、其他
	private String petitionSourceCode;
	private String petitionSourceName;
	private String petitionClassCode;
	private String petitionClassName;
	private String petitionUrgeCode="0";
	private String petitionUrgeName;
	private String actualDate;//实际日期格式yyyy-MM  存放业务月
	private String petitionSecrecyCode;
	private String petitionSecrecyName;
	private String overAccuseFlag;
	private String overAccuseTypeCode;
	private String overAccuseTypeName;
	private String groupFlag="0";
	private int    personNum;
	private String importantFlag="0";
	private Timestamp requireEndDate;//逾期日期  当前时间+3个月，受理分发指定办信部门时候设置
	
	private Timestamp signDate;//签收日期,当前时间+5个工作日，受理分发指定办信部门时候,受理签收时设置
	//basicInfo = petitionBasicInfoManager.setSignDateAndRequireEndDate(basicInfo);
	//逾期未结件:没申请过延期&requireEndDate.before(new Date())
	private String postponeFlag = "0";//是否申请过延期  //0否，1同意申请延期，2正在申请中，3申请没同意
	//延期未结件:申请过延期&requireEndDate.before(new Date())
	private String issueRegionCode;
	private String issueRegionName;
	private String pretreatmentCode;
	private String pretreatmentName;
	private String pretreatment;
	private String dealTypeCode;
	private String dealTypeName;
	private String endTypeCode;
	private String endTypeName;
	private Timestamp endDate;//了结日期  了结批示时候置
	private String endFlag="0";//办结  
	private String basicFlag="1";       //月基数统计用 0为无效 1为有效
//	private String exceptionCond;
//	private String exceptionHandling;
	private String archiveFlag = "0"; //1归档，2电子归档
	private Timestamp archiveDate; //归档日期
	private String remark;
	private String currDeptCode;
	private String currDeptName;
	private String returnContent;
	private Integer repeatNum = 0;
	private String repeatFlag = "0";
	private String objectClassName;
	private String objectClassCode;
	private String subIssueRegionCode;
	private String subIssueRegionName;
//	private String exceptionFlag;
	private String transFlag="0";
	private String currPetitionNo;				//用于给用户展示的信访编号	                		CHAR(26)
	private String realNameReport="0";				//用于记录是否为实名举报信件 0： 否 	1：是
	private String returnFlag="0";
	private String otherAccusers;				//用于存放其他反映人姓名								VARCHAR(1000)
	private String otherAccuseds;				//用于存放其他被反映人姓名							VARCHAR(1000)
	private String firstAccuser;				//用于存放第一反映人									VARCHAR(120)
	private String encryfirstAccuser;			//用于存放第一反映人(加密)							VARCHAR(120)
	private String firstAccused;				//用于存放第一被反映人								VARCHAR(120)
	private String encryfirstAccused;			//用于存放第一被反映人(加密)							VARCHAR(120)
	private String instructor;					//用于机关领导交办--领导姓名							VARCHAR(50)
	private String instructText;				//用于机关领导交办--批示文号							VARCHAR(30)
	private Timestamp instructTime;				//用于机关领导交办--批示时间
	private String instruction;					//用于机关领导交办--批示内容							VARCHAR(1000)
	private String receiptor;					//用于来访、电话受理信件中的接访、接听人				VARCHAR(50)
	private String receptionCond;				//用于来访、电话受理信件中的接访、接听情况				VARCHAR(3000)
	private String receptionNotion;				//用于来访、电话受理信件中的接访、接听人意见			VARCHAR(3000)
	private String hisDeptCode;				//历史部门，逗号分隔
	private String isCountFlag = "1";		//是否属于统计范围 0、不统计，1、统计 。默认统计。
	private String isSecretaryFlag = "0";		//书记专题会,设置标志位0否，1初核，2谈话函询，3转办，4了结，5暂存,6留存
	private String handlingPretreatment;        //案管秘书的拟办意见
	private String isNetDocumentOpenFlag="0";        //是否打印过网络原信的标识
	private String isSwgat="0";        //2016-01-05liub是否涉外及港澳台
	
	private Set<PetitionOverSeeInfo> overSeeSet = new HashSet<PetitionOverSeeInfo>();
	private Set<PetitionPostponeInfo> postponeInfoSet = new HashSet<PetitionPostponeInfo>();
	private Set<PetitionAccusedInfo> accusedInfoSet = new HashSet<PetitionAccusedInfo>();
	private Set<PetitionAccuserInfo> accuserInfoSet = new HashSet<PetitionAccuserInfo>();
	private Set<PetitionIssueInfo> issueInfoSet = new HashSet<PetitionIssueInfo>();
	private Set<IssueTypeInfo> issueTypeInfoSet= new HashSet<IssueTypeInfo>();
	private PetitionCirculationStateInfo circulationStateInfo;
	private Set<PetitionCirculationStateTraceInfo> circulationStateTraceInfoSet = new HashSet<PetitionCirculationStateTraceInfo>();
	private Set<InstructInfo> instructInfoSet = new HashSet<InstructInfo>();
	private Set<PetitionDealInfo> dealInfoSet = new HashSet<PetitionDealInfo>();
	private Set<PetitionMultiMediaInfo> multiMediaInfoSet = new HashSet<PetitionMultiMediaInfo>();
	private Set<PetitionDealLogInfo> dealLogInfo = new HashSet<PetitionDealLogInfo>();
	private Set<NetContentInfo> netContentInfoSet = new HashSet<NetContentInfo>();
	private Set<PetitionAssignedInfo> assignedInfoSet = new HashSet<PetitionAssignedInfo>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionPostponeInfo> getPostponeInfoSet() {
		return postponeInfoSet;
	}
	public void setPostponeInfoSet(Set<PetitionPostponeInfo> postponeInfoSet) {
		this.postponeInfoSet = postponeInfoSet;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionOverSeeInfo> getOverSeeSet() {
		return overSeeSet;
	}
	public void setOverSeeSet(Set<PetitionOverSeeInfo> overSeeSet) {
		this.overSeeSet = overSeeSet;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<NetContentInfo> getNetContentInfoSet() {
		return netContentInfoSet;
	}
	public void setNetContentInfoSet(Set<NetContentInfo> netContentInfoSet) {
		this.netContentInfoSet = netContentInfoSet;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionAssignedInfo> getAssignedInfoSet() {
		return assignedInfoSet;
	}
	public void setAssignedInfoSet(Set<PetitionAssignedInfo> assignedInfoSet) {
		this.assignedInfoSet = assignedInfoSet;
	}
	/**
	 * @return the dealLogInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionDealLogInfo> getDealLogInfo() {
		return dealLogInfo;
	}
	/**
	 * @param dealLogInfo the dealLogInfo to set
	 */
	public void setDealLogInfo(Set<PetitionDealLogInfo> dealLogInfo) {
		this.dealLogInfo = dealLogInfo;
	}
	/**
	 * @return the PetitionDealInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionMultiMediaInfo> getMultiMediaInfoSet() {
		return multiMediaInfoSet;
	}
	/**
	 * @param PetitionDealInfo the PetitionDealInfo to set
	 */
	public void setMultiMediaInfoSet(Set<PetitionMultiMediaInfo> multiMediaInfoSet) {
		this.multiMediaInfoSet = multiMediaInfoSet;
	}
	
	/**
	 * @return the PetitionDealInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionDealInfo> getDealInfoSet() {
		return dealInfoSet;
	}
	/**
	 * @param PetitionDealInfo the PetitionDealInfo to set
	 */
	public void setDealInfoSet(Set<PetitionDealInfo> dealInfoSet) {
		this.dealInfoSet = dealInfoSet;
	}
	
	/**
	 * @return the instructInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<InstructInfo> getInstructInfoSet() {
		return instructInfoSet;
	}
	/**
	 * @param instructInfoSet the instructInfoSet to set
	 */
	public void setInstructInfoSet(Set<InstructInfo> instructInfoSet) {
		this.instructInfoSet = instructInfoSet;
	}
	/**
	 * @return the circulationStateTraceInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionCirculationStateTraceInfo> getCirculationStateTraceInfoSet() {
		return circulationStateTraceInfoSet;
	}
	/**
	 * @param circulationStateTraceInfoSet the circulationStateTraceInfoSet to set
	 */
	public void setCirculationStateTraceInfoSet(
			Set<PetitionCirculationStateTraceInfo> circulationStateTraceInfoSet) {
		this.circulationStateTraceInfoSet = circulationStateTraceInfoSet;
	}
	/**
	 * @return the circulationStateInfoSet
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "petitionBasicInfo")
	public PetitionCirculationStateInfo getCirculationStateInfo() {
		return circulationStateInfo;
	}
	/**
	 * @param circulationStateInfoSet the circulationStateInfoSet to set
	 */
	public void setCirculationStateInfo(
			PetitionCirculationStateInfo circulationStateInfo) {
		this.circulationStateInfo = circulationStateInfo;
	}
	/**
	 * @return the accusedInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionAccusedInfo> getAccusedInfoSet() {
		return accusedInfoSet;
	}
	/**
	 * @param accusedInfoSet the accusedInfoSet to set
	 */
	public void setAccusedInfoSet(Set<PetitionAccusedInfo> accusedInfoSet) {
		this.accusedInfoSet = accusedInfoSet;
	}
	/**
	 * @return the accuserInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionAccuserInfo> getAccuserInfoSet() {
		return accuserInfoSet;
	}
	/**
	 * @param accuserInfoSet the accuserInfoSet to set
	 */
	public void setAccuserInfoSet(Set<PetitionAccuserInfo> accuserInfoSet) {
		this.accuserInfoSet = accuserInfoSet;
	}
	/**
	 * @return the issueInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionIssueInfo> getIssueInfoSet() {
		return issueInfoSet;
	}
	/**
	 * @param issueInfoSet the issueInfoSet to set
	 */
	public void setIssueInfoSet(Set<PetitionIssueInfo> issueInfoSet) {
		this.issueInfoSet = issueInfoSet;
	}
	/**
	 * @return the issueTypeInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<IssueTypeInfo> getIssueTypeInfoSet() {
		return issueTypeInfoSet;
	}
	/**
	 * @param issueTypeInfoSet the issueTypeInfoSet to set
	 */
	public void setIssueTypeInfoSet(Set<IssueTypeInfo> issueTypeInfoSet) {
		this.issueTypeInfoSet = issueTypeInfoSet;
	}
	/**
	 * @return the petitionNo
	 */
	public String getPetitionNo() {
		return petitionNo;
	}
	/**
	 * @param petitionNo the petitionNo to set
	 */
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the petitionSeqNo
	 */
	public String getPetitionSeqNo() {
		return petitionSeqNo;
	}
	/**
	 * @param petitionSeqNo the petitionSeqNo to set
	 */
	public void setPetitionSeqNo(String petitionSeqNo) {
		this.petitionSeqNo = petitionSeqNo;
	}
	/**
	 * @return the petitionPrtNo
	 */
	public String getPetitionPrtNo() {
		return petitionPrtNo;
	}
	/**
	 * @param petitionPrtNo the petitionPrtNo to set
	 */
	public void setPetitionPrtNo(String petitionPrtNo) {
		this.petitionPrtNo = petitionPrtNo;
	}
	/**
	 * @return the petitionAbbrNo
	 */
	public String getPetitionAbbrNo() {
		return petitionAbbrNo;
	}
	/**
	 * @param petitionAbbrNo the petitionAbbrNo to set
	 */
	public void setPetitionAbbrNo(String petitionAbbrNo) {
		this.petitionAbbrNo = petitionAbbrNo;
	}
	/**
	 * @return the petitionDate
	 */
	public Timestamp getPetitionDate() {
		return petitionDate;
	}
	/**
	 * @param petitionDate the petitionDate to set
	 */
	public void setPetitionDate(Timestamp petitionDate) {
		this.petitionDate = petitionDate;
		setActualDate(CommonUtil.countActualDate(petitionDate));
	}
	/**
	 * @return the petitionTypeCode
	 */
	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}
	/**
	 * @param petitionTypeCode the petitionTypeCode to set
	 */
	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode;
	}
	/**
	 * @return the petitionTypeName
	 */
	public String getPetitionTypeName() {
		return petitionTypeName;
	}
	/**
	 * @param petitionTypeName the petitionTypeName to set
	 */
	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
	}
	/**
	 * @return the petitionSourceCode
	 */
	public String getPetitionSourceCode() {
		return petitionSourceCode;
	}
	/**
	 * @param petitionSourceCode the petitionSourceCode to set
	 */
	public void setPetitionSourceCode(String petitionSourceCode) {
		this.petitionSourceCode = petitionSourceCode;
	}
	/**
	 * @return the petitionSourceName
	 */
	public String getPetitionSourceName() {
		return petitionSourceName;
	}
	/**
	 * @param petitionSourceName the petitionSourceName to set
	 */
	public void setPetitionSourceName(String petitionSourceName) {
		this.petitionSourceName = petitionSourceName;
	}
	/**
	 * @return the petitionClassCode
	 */
	public String getPetitionClassCode() {
		return petitionClassCode;
	}
	/**
	 * @param petitionClassCode the petitionClassCode to set
	 */
	public void setPetitionClassCode(String petitionClassCode) {
		this.petitionClassCode = petitionClassCode;
	}
	/**
	 * @return the petitionClassName
	 */
	public String getPetitionClassName() {
		return petitionClassName;
	}
	/**
	 * @param petitionClassName the petitionClassName to set
	 */
	public void setPetitionClassName(String petitionClassName) {
		this.petitionClassName = petitionClassName;
	}
	/**
	 * @return the petitionUrgeCode
	 */
	public String getPetitionUrgeCode() {
		return petitionUrgeCode;
	}
	/**
	 * @param petitionUrgeCode the petitionUrgeCode to set
	 */
	public void setPetitionUrgeCode(String petitionUrgeCode) {
		this.petitionUrgeCode = petitionUrgeCode;
	}
	/**
	 * @return the petitionUrgeName
	 */
	public String getPetitionUrgeName() {
		return petitionUrgeName;
	}
	/**
	 * @param petitionUrgeName the petitionUrgeName to set
	 */
	public void setPetitionUrgeName(String petitionUrgeName) {
		this.petitionUrgeName = petitionUrgeName;
	}
	/**
	 * @return the petitionSecrecyCode
	 */
	public String getPetitionSecrecyCode() {
		return petitionSecrecyCode;
	}
	/**
	 * @param petitionSecrecyCode the petitionSecrecyCode to set
	 */
	public void setPetitionSecrecyCode(String petitionSecrecyCode) {
		this.petitionSecrecyCode = petitionSecrecyCode;
	}
	/**
	 * @return the petitionSecrecyName
	 */
	public String getPetitionSecrecyName() {
		return petitionSecrecyName;
	}
	/**
	 * @param petitionSecrecyName the petitionSecrecyName to set
	 */
	public void setPetitionSecrecyName(String petitionSecrecyName) {
		this.petitionSecrecyName = petitionSecrecyName;
	}
	/**
	 * @return the overAccuseFlag
	 */
	public String getOverAccuseFlag() {
		return overAccuseFlag;
	}
	/**
	 * @param overAccuseFlag the overAccuseFlag to set
	 */
	public void setOverAccuseFlag(String overAccuseFlag) {
		this.overAccuseFlag = overAccuseFlag;
	}
	/**
	 * @return the overAccuseTypeCode
	 */
	public String getOverAccuseTypeCode() {
		return overAccuseTypeCode;
	}
	/**
	 * @param overAccuseTypeCode the overAccuseTypeCode to set
	 */
	public void setOverAccuseTypeCode(String overAccuseTypeCode) {
		this.overAccuseTypeCode = overAccuseTypeCode;
	}
	/**
	 * @return the overAccuseTypeName
	 */
	public String getOverAccuseTypeName() {
		return overAccuseTypeName;
	}
	/**
	 * @param overAccuseTypeName the overAccuseTypeName to set
	 */
	public void setOverAccuseTypeName(String overAccuseTypeName) {
		this.overAccuseTypeName = overAccuseTypeName;
	}
	
	public String getGroupFlag() {
		if(getPersonNum()>=5&&"2".equals(getPetitionTypeCode())){
			return("1");
		}else {
			return("0");
		}
	}
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		if(personNum>=5&&"2".equals(getPetitionTypeCode())){
			setGroupFlag("1");
		}else {
			setGroupFlag("0");
		}
		this.personNum = personNum;
	}
	/**
	 * @return the importantFlag
	 */
	public String getImportantFlag() {
		return importantFlag;
	}
	/**
	 * @param importantFlag the importantFlag to set
	 */
	public void setImportantFlag(String importantFlag) {
		this.importantFlag = importantFlag;
	}
	/**
	 * @return the issueRegionCode
	 */
	public String getIssueRegionCode() {
		return issueRegionCode;
	}
	/**
	 * @param issueRegionCode the issueRegionCode to set
	 */
	public void setIssueRegionCode(String issueRegionCode) {
		this.issueRegionCode = issueRegionCode;
	}
	/**
	 * @return the requireEndDate
	 */
	public Timestamp getRequireEndDate() {
		return requireEndDate;
	}
	/**
	 * @param requireEndDate the requireEndDate to set
	 */
	public void setRequireEndDate(Timestamp requireEndDate) {
		this.requireEndDate = requireEndDate;
	}
	/**
	 * @return the issueRegionName
	 */
	public String getIssueRegionName() {
		return issueRegionName;
	}
	/**
	 * @param issueRegionName the issueRegionName to set
	 */
	public void setIssueRegionName(String issueRegionName) {
		this.issueRegionName = issueRegionName;
	}
	/**
	 * @return the pretreatmentCode
	 */
	public String getPretreatmentCode() {
		return pretreatmentCode;
	}
	/**
	 * @param pretreatmentCode the pretreatmentCode to set
	 */
	public void setPretreatmentCode(String pretreatmentCode) {
		this.pretreatmentCode = pretreatmentCode;
	}
	/**
	 * @return the pretreatmentName
	 */
	public String getPretreatmentName() {
		return pretreatmentName;
	}
	/**
	 * @param pretreatmentName the pretreatmentName to set
	 */
	public void setPretreatmentName(String pretreatmentName) {
		this.pretreatmentName = pretreatmentName;
	}
	/**
	 * @return the dealTypeCode
	 */
	public String getDealTypeCode() {
		return dealTypeCode;
	}
	/**
	 * @param dealTypeCode the dealTypeCode to set
	 */
	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}
	/**
	 * @return the dealTypeName
	 */
	public String getDealTypeName() {
		return dealTypeName;
	}
	/**
	 * @param dealTypeName the dealTypeName to set
	 */
	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}
	/**
	 * @return the endTypeCode
	 */
	public String getEndTypeCode() {
		return endTypeCode;
	}
	/**
	 * @param endTypeCode the endTypeCode to set
	 */
	public void setEndTypeCode(String endTypeCode) {
		this.endTypeCode = endTypeCode;
	}
	/**
	 * @return the endTypeName
	 */
	public String getEndTypeName() {
		return endTypeName;
	}
	/**
	 * @param endTypeName the endTypeName to set
	 */
	public void setEndTypeName(String endTypeName) {
		this.endTypeName = endTypeName;
	}
	/**
	 * @return the endFlag
	 */
	public String getEndFlag() {
		return endFlag;
	}
	/**
	 * @param endFlag the endFlag to set
	 */
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	/**
	 * @return the archiveFlag
	 */
	public String getArchiveFlag() {
		return archiveFlag;
	}
	/**
	 * @param archiveFlag the archiveFlag to set
	 */
	public void setArchiveFlag(String archiveFlag) {
		this.archiveFlag = archiveFlag;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCurrDeptCode() {
		return currDeptCode;
	}
	public void setCurrDeptCode(String currDeptCode) {
		this.currDeptCode = currDeptCode;
		setHisDeptCode(currDeptCode, true);
	}
	public String getCurrDeptName() {
		return currDeptName;
	}
	public void setCurrDeptName(String currDeptName) {
		this.currDeptName = currDeptName;
	}
	
	public String getReturnContent() {
		return returnContent;
	}
	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}
	public Integer getRepeatNum() {
		return repeatNum;
	}
	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}
	public String getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public String getObjectClassName() {
		return objectClassName;
	}
	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}
	public String getObjectClassCode() {
		return objectClassCode;
	}
	public void setObjectClassCode(String objectClassCode) {
		this.objectClassCode = objectClassCode;
	}
	public String getSubIssueRegionCode() {
		return subIssueRegionCode;
	}
	public void setSubIssueRegionCode(String subIssueRegionCode) {
		this.subIssueRegionCode = subIssueRegionCode;
	}
	public String getSubIssueRegionName() {
		return subIssueRegionName;
	}
	public void setSubIssueRegionName(String subIssueRegionName) {
		this.subIssueRegionName = subIssueRegionName;
	}
	/**
	 * @return the pretreatment
	 */
	public String getPretreatment() {
		return pretreatment;
	}
	/**
	 * @param pretreatment the pretreatment to set
	 */
	public void setPretreatment(String pretreatment) {
		this.pretreatment = pretreatment;
	}
//	public String getExceptionCond() {
//		return exceptionCond;
//	}
//	public void setExceptionCond(String exceptionCond) {
//		this.exceptionCond = exceptionCond;
//	}
//	public String getExceptionHandling() {
//		return exceptionHandling;
//	}
//	public void setExceptionHandling(String exceptionHandling) {
//		this.exceptionHandling = exceptionHandling;
//	}
	
	/**
	 * @return the exceptionFlag
	 */
//	public String getExceptionFlag() {
//		return exceptionFlag;
//	}
	/**
	 * @param exceptionFlag the exceptionFlag to set
	 */
//	public void setExceptionFlag(String exceptionFlag) {
//		this.exceptionFlag = exceptionFlag;
//	}
	public String getTransFlag() {
		return transFlag;
	}
	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}
	/**
	 * @return the currPetitionNo
	 */
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}
	/**
	 * @param currPetitionNo the currPetitionNo to set
	 */
	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}
	/**
	 * @return the realNameReport
	 */
	public String getRealNameReport() {
		return realNameReport;
	}
	/**
	 * @param realNameReport the realNameReport to set
	 */
	public void setRealNameReport(String realNameReport) {
		this.realNameReport = realNameReport;
	}
	public String getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}
	public String getPetitionTypeCodeTemp() {
		return petitionTypeCodeTemp;
	}
	public void setPetitionTypeCodeTemp(String petitionTypeCodeTemp) {
		this.petitionTypeCodeTemp = petitionTypeCodeTemp;
	}
	public String getSubPetitionTypeCode() {
		return subPetitionTypeCode;
	}
	public void setSubPetitionTypeCode(String subPetitionTypeCode) {
		this.subPetitionTypeCode = subPetitionTypeCode;
	}
	public String getSubPetitionTypeName() {
		return subPetitionTypeName;
	}
	public void setSubPetitionTypeName(String subPetitionTypeName) {
		this.subPetitionTypeName = subPetitionTypeName;
	}
	/**
	 * @return the otherAccusers
	 */
	public String getOtherAccusers() {
		return otherAccusers;
	}
	/**
	 * @param otherAccusers the otherAccusers to set
	 */
	public void setOtherAccusers(String otherAccusers) {
		this.otherAccusers = otherAccusers;
	}
	/**
	 * @return the otherAccuseds
	 */
	public String getOtherAccuseds() {
		return otherAccuseds;
	}
	/**
	 * @param otherAccuseds the otherAccuseds to set
	 */
	public void setOtherAccuseds(String otherAccuseds) {
		this.otherAccuseds = otherAccuseds;
	}
	public Timestamp getSignDate() {
		return signDate;
	}
	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}
	public String getPostponeFlag() {
		return postponeFlag;
	}
	public void setPostponeFlag(String postponeFlag) {
		this.postponeFlag = postponeFlag;
	}
	@Transient
	public String getFirstAccuser() {
		firstAccuser = Encrypt.decrypt(encryfirstAccuser);
		return firstAccuser;
	}
	public void setFirstAccuser(String firstAccuser) {
		this.firstAccuser = firstAccuser;
		this.encryfirstAccuser = Encrypt.encrypt(firstAccuser);
	}
	@Column(name = "FIRST_ACCUSER")
	public String getEncryfirstAccuser() {
		return encryfirstAccuser;
	}
	public void setEncryfirstAccuser(String firstAccuser) {
		this.encryfirstAccuser = firstAccuser;
	}
	@Transient
	public String getFirstAccused() {
		firstAccused = Encrypt.decrypt(encryfirstAccused);
		return firstAccused;
	}
	public void setFirstAccused(String firstAccused) {
		this.firstAccused = firstAccused;
		this.encryfirstAccused = Encrypt.encrypt(firstAccused);
	}
	@Column(name = "FIRST_ACCUSED")
	public String getEncryfirstAccused() {
		return encryfirstAccused;
	}
	public String getBasicFlag() {
		return basicFlag;
	}
	public void setBasicFlag(String basicFlag) {
		this.basicFlag = basicFlag;
	}
	public void setEncryfirstAccused(String firstAccused) {
		this.encryfirstAccused = firstAccused;
	}
	public String getActualDate() {
		return actualDate;
	}
	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}
	public String getReceiptor() {
		return receiptor;
	}
	public void setReceiptor(String receiptor) {
		this.receiptor = receiptor;
	}
	public String getReceptionCond() {
		return receptionCond;
	}
	public void setReceptionCond(String receptionCond) {
		this.receptionCond = receptionCond;
	}
	public String getReceptionNotion() {
		return receptionNotion;
	}
	public void setReceptionNotion(String receptionNotion) {
		this.receptionNotion = receptionNotion;
	}
	public Timestamp getArchiveDate() {
		return archiveDate;
	}
	public void setArchiveDate(Timestamp archiveDate) {
		this.archiveDate = archiveDate;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getInstructText() {
		return instructText;
	}
	public void setInstructText(String instructText) {
		this.instructText = instructText;
	}
	public Timestamp getInstructTime() {
		return instructTime;
	}
	public void setInstructTime(Timestamp instructTime) {
		this.instructTime = instructTime;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getHisDeptCode() {
		return hisDeptCode;
	}
	public void setHisDeptCode(String hisDeptCode) {
		this.hisDeptCode = hisDeptCode;
	}

	public String getIsCountFlag() {
		return isCountFlag;
	}

	public void setIsCountFlag(String isCountFlag) {
		this.isCountFlag = isCountFlag;
	}
	
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getIsSecretaryFlag() {
		return isSecretaryFlag;
	}
	public void setIsSecretaryFlag(String isSecretaryFlag) {
		this.isSecretaryFlag = isSecretaryFlag;
	}
	@Column(name = "handling_pretreatment")
	public String getHandlingPretreatment() {
		return handlingPretreatment;
	}
	public void setHandlingPretreatment(String handlingPretreatment) {
		this.handlingPretreatment = handlingPretreatment;
	}
	@Column(name = "is_netdocument_open_flag")
	public String getIsNetDocumentOpenFlag() {
		return isNetDocumentOpenFlag;
	}
	public void setIsNetDocumentOpenFlag(String isNetDocumentOpenFlag) {
		this.isNetDocumentOpenFlag = isNetDocumentOpenFlag;
	}
	/**
	 * 重写历史部门的设置方法，
	 * 将新部门添加到历史部门里面
	 * @param hisDeptCode
	 */
	public void setHisDeptCode(String hisDeptCode,boolean b) {
		hisDeptCode = hisDeptCode+",";
		if (b) {
			if (getHisDeptCode() == null || "".equals(getHisDeptCode())) {
				this.hisDeptCode = hisDeptCode;
			} else {
				if (!getHisDeptCode().contains(hisDeptCode)) {
					hisDeptCode = getHisDeptCode() + hisDeptCode;
					this.hisDeptCode = hisDeptCode;
				}
			}
		}else{
			hisDeptCode = getHisDeptCode().replaceAll(hisDeptCode, "");
			this.hisDeptCode = hisDeptCode;
		}
	}
	public String getIsDistributeFlag() {
		return isDistributeFlag;
	}
	public void setIsDistributeFlag(String isDistributeFlag) {
		this.isDistributeFlag = isDistributeFlag;
	}
	public String getSensitiveFlag() {
		return sensitiveFlag;
	}
	public void setSensitiveFlag(String sensitiveFlag) {
		this.sensitiveFlag = sensitiveFlag;
	}
	@Column(name = "IS_SWGAT", length = 1)
	public String getIsSwgat() {
		return isSwgat;
	}
	public void setIsSwgat(String isSwgat) {
		this.isSwgat = isSwgat;
	}
	
	
}
