package com.sinosoft.xf.petition.accept.web;


import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionIssueInfo;
import com.sinosoft.xf.petition.accept.manager.PetitionIssueInfoManager;
import com.sinosoft.xf.petition.domainutil.RegionNameCount;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 问题类别action
 * @date 2017-06-06
 * @author guoh
 */
@Namespace("/petition")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-issue-info.action", type = "redirect") })
public class PetitionIssueInfoAction extends CrudExtActionSupport<PetitionIssueInfo, String> {

	private static final long serialVersionUID = 1L;
	PetitionIssueInfo entity = new PetitionIssueInfo();
	@Autowired
	PetitionIssueInfoManager issueInfoManager;
	@Override
	protected EntityManager<PetitionIssueInfo, String> getEntityManager() {
		return issueInfoManager;
	}
	@Override
	protected void prepareModel() throws Exception {
	}
	
	public PetitionIssueInfo getModel() {
		return entity;
	}
	/**
	 * 信访件分布情况-点击玫瑰图查询指定地区信访类别分布情况
	 * @author guoh
	 * @date 2017-08-24
	 * @throws IOException
	 */
	public void petitionTypeAndIssueType() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.petitionTypeAndIssueType(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 信访件分布情况-点击玫瑰图查询指定地区问题类别分布情况
	 * @author guoh
	 * @date 2017-08-24
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void issueTypeCount() throws IOException, ParseException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.issueTypeCount(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别数量前五位-主页面查询
	 * @author guoh
	 * @date 2017-07-05
	 * @throws IOException
	 */
	public void issueTypeTop5() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.queryIssueTypeTop5(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别数量前五位
	 * 钻取查询问题属地以及自收件、上级转交办、下级转交办的数量
	 * @author guoh
	 * @throws Exception 
	 * @date 2017-08-30
	 */
	public void drillingTopCount() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<RegionNameCount> page = issueInfoManager.drillingTopCount(map,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 问题类别变化情况-增、减量主查询
	 * @author guoh
	 * @date 2017-07-05
	 * @throws IOException
	 */
	public void zjIssueTypeTop5() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.queryZJIssueTypeTop5(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别变化情况-钻取查询问题类别分布属地
	 * @author guoh
	 * @date 2017-08-28
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void issueDistribution() throws IOException, ParseException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.issueDistribution(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别分布情况-查询违法、违纪大类数量
	 * @author guoh
	 * @date 2017-08-18
	 * @throws IOException
	 */
	public void problemDistribution() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.problemDistribution(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 问题类别分布情况-点击违纪行为查询子类问题类别对应的数量
	 * @author guoh
	 * @date 2017-08-29
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void queryWJSubclass() throws IOException, ParseException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = issueInfoManager.queryWJSubclass(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 点击子类查询问题类别
	 * @throws IOException
	 */
	public void queryIssueType() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		String code = request.getParameter("code");
		String json = issueInfoManager.queryIssueType(code);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 点击违法行为查询子类
	 * @throws IOException
	 */
	public void wfBehavior() throws IOException{
		String json = issueInfoManager.wfBehavior();
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	
	
	private String swFlag;
 	private String dateFlag;
 	private String startDateValue;
 	private String endDateValue;
 	private String orgCode;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getSwFlag() {
		return swFlag;
	}
	public void setSwFlag(String swFlag) {
		this.swFlag = swFlag;
	}
	public String getDateFlag() {
		return dateFlag;
	}
	public void setDateFlag(String dateFlag) {
		this.dateFlag = dateFlag;
	}
	public String getStartDateValue() {
		return startDateValue;
	}
	public void setStartDateValue(String startDateValue) {
		this.startDateValue = startDateValue;
	}
	public String getEndDateValue() {
		return endDateValue;
	}
	public void setEndDateValue(String endDateValue) {
		this.endDateValue = endDateValue;
	}
}