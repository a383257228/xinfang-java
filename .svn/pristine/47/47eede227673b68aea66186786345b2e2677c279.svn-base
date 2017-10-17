package com.sinosoft.xf.petition.deal.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 调查办理记录表
 * @date 2011-12-12
 * @author Oba
 */
@Entity
@Table(name = "Petition_Survey_Deal_Info")
public class PetitionSurveyDealInfo extends AudiEntity{

	private static final long serialVersionUID = 1L;
	
	private String surveyContent;
	public String getSurveyContent() {
		return surveyContent;
	}
	public void setSurveyContent(String surveyContent) {
		this.surveyContent = surveyContent;
	}
	private String surveyAdvice;
	private String encrysurveyAdvice;
	@Transient
	public String getSurveyAdvice() {
		surveyAdvice = Encrypt.decrypt(encrysurveyAdvice);
		return surveyAdvice;
	}
	public void setSurveyAdvice(String surveyAdvice) {
		this.surveyAdvice = surveyAdvice;
		this.encrysurveyAdvice = Encrypt.encrypt(surveyAdvice);;
	}
	@Column(name = "SURVEY_ADVICE")
	public String getEncrysurveyAdvice() {
		return encrysurveyAdvice;
	}
	public void setEncrysurveyAdvice(String surveyAdvice) {
		this.encrysurveyAdvice = surveyAdvice;
	}
	
	private String dealNo;
	private PetitionDealInfo petitionDealInfo;
	private String regionCode;
	private String regionName;
	private Timestamp surveyDate;
	private Timestamp replyDate;
	private String surveySite;
	private String surveyDept;
	private String surveyComposing;
	private String emcee;
	private String writer;
	private String surveyDealType;
	private String opinionSources;//by lijun 2014-6-13  意见来源

	public String getSurveyDealType() {
		return surveyDealType;
	}
	public void setSurveyDealType(String surveyDealType) {
		this.surveyDealType = surveyDealType;
	}
	/**
	 * @return the petitionDealInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_DEAL_INFO_OID")
	public PetitionDealInfo getPetitionDealInfo() {
		return petitionDealInfo;
	}
	/**
	 * @param petitionDealInfo the petitionDealInfo to set
	 */
	public void setPetitionDealInfo(PetitionDealInfo petitionDealInfo) {
		this.petitionDealInfo = petitionDealInfo;
	}
	/**
	 * @return the dealNo
	 */
	public String getDealNo() {
		return dealNo;
	}
	/**
	 * @param dealNo the dealNo to set
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
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
	 * @return the surveyDate
	 */
	public Timestamp getSurveyDate() {
		return surveyDate;
	}
	/**
	 * @param surveyDate the surveyDate to set
	 */
	public void setSurveyDate(Timestamp surveyDate) {
		this.surveyDate = surveyDate;
	}
	/**
	 * @return the surveySite
	 */
	public String getSurveySite() {
		return surveySite;
	}
	/**
	 * @param surveySite the surveySite to set
	 */
	public void setSurveySite(String surveySite) {
		this.surveySite = surveySite;
	}
	public String getSurveyDept() {
		return surveyDept;
	}
	public void setSurveyDept(String surveyDept) {
		this.surveyDept = surveyDept;
	}
	/**
	 * @return the surveyComposing
	 */
	public String getSurveyComposing() {
		return surveyComposing;
	}
	/**
	 * @param surveyComposing the surveyComposing to set
	 */
	public void setSurveyComposing(String surveyComposing) {
		this.surveyComposing = surveyComposing;
	}
	/**
	 * @return the emcee
	 */
	public String getEmcee() {
		return emcee;
	}
	/**
	 * @param emcee the emcee to set
	 */
	public void setEmcee(String emcee) {
		this.emcee = emcee;
	}
	/**
	 * @return the writer
	 */
	public String getWriter() {
		return writer;
	}
	/**
	 * @param writer the writer to set
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Timestamp getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Timestamp replyDate) {
		this.replyDate = replyDate;
	}
	public String getOpinionSources() {
		return opinionSources;
	}
	public void setOpinionSources(String opinionSources) {
		this.opinionSources = opinionSources;
	}
	
	
}
