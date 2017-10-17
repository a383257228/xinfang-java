package com.sinosoft.xf.petition.accept.web;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccusedInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccuserInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.petition.accept.manager.PetitionDealInfoManager;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.domainutil.PetitionInfo;
import com.sinosoft.xf.petition.domainutil.SystemCodeNodeInfoVO;
import com.sinosoft.xf.petition.end.domain.PetitionEndInfo;
import com.sinosoft.xf.petition.instruct.domain.InstructInfo;
import com.sinosoft.xf.petition.instruct.manager.InstructInfoManager;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 基本信息action
 * @date 2017-06-12
 * @author guoh
 */
@Namespace("/petition")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-basic-info.action", type = "redirect") })
public class PetitionBasicInfoAction extends CrudExtActionSupport<PetitionBasicInfo, String>{

	private static final long serialVersionUID = 1L;
	PetitionBasicInfo entity = new PetitionBasicInfo();
	/**
	 * 
	 * 注入办理信息manager
	 */
	@Autowired
	PetitionDealInfoManager petitionDealInfoManager;
	@Autowired
	PetitionBasicInfoManager entityManager;

	public PetitionBasicInfo getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	@Override
	protected EntityManager<PetitionBasicInfo, String> getEntityManager() {
		return entityManager;
	}
	/**
	 * 信访件分布情况-全市的信访件分布主页面查询
	 * @author guoh
	 * @date 2017-06-30
	 * @throws Exception
	 */
	public void queryXFJPaiZhu() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = entityManager.queryXFJPaiZhu(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 信访件分布情况（本委）-查询信访件列表
	 * @date 2017-06-12
	 * @author guoh
	 * @throws IOException
	 * @throws Exception
	 */
	public void queryPetitionInfo() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<PetitionInfo> page = entityManager.getPetitionInfo(map,start, limit,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 信访件分布情况（本市）、问题类别数量前5位
	 * 自收件、转交办钻取查询信的基本信息
	 * @date 2017-08-25
	 * @author guoh
	 * @throws IOException
	 * @throws Exception
	 */
	public void queryLetterInfo() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<PetitionInfo> page = entityManager.getLetterInfo(map,start, limit,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 问题类别变化情况、问题类别分布情况
     * 钻取查询信访件列表
	 * @date 2017-08-28
	 * @author guoh
	 * @throws IOException
	 * @throws Exception
	 */
	public void classChangeInfo() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<PetitionInfo> page = entityManager.classChangeInfo(map,start, limit,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 全市本委各月趋势统计
	 * @author guoh
	 * @date 2017-06-26
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void trendStatistical() throws IOException, ParseException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = entityManager.trendStatistical(map,person); 
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 各月统计、同比环比的钻取查询
	 * @author guoh
	 * @date 2017-08-31
	 * @throws IOException
	 */
	public void reportDrilling() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<SystemCodeNodeInfoVO> page = entityManager.reportDrilling(map,person); 
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 同比分布情况(只查本年)-主查询
	 * @author guoh
	 * @date 2017-07-13
	 * @throws IOException
	 */
	public void tbQuery() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String json = entityManager.tbQuery(swFlag,bwRadio,person); 
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 环比分布情况(只查本年)-主查询
	 * @author guoh
	 * @date 2017-07-14
	 * @throws IOException
	 */
	public void SequentialQuery() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String json = entityManager.SequentialQuery(swFlag,bwRadio,person); 
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 同比、环比、各月趋势-钻取查询列表
	 * @author guoh
	 * @throws Exception 
	 * @date 2017-09-20
	 */
	public void TrendCompareQueryList() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		ExtjsPage<PetitionInfo> page = entityManager.TrendCompareQueryList(map,start, limit,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 点击搜索出的标题显示基本信息
	 * @date 2017-06-21
	 * @author guoh
	 * @throws Exception 
	 */
	public void loadPetitionInfo(){
		try {
			PetitionInfo petitionInfo = entityManager.loadPetitionInfo(id);
			String json = ObjectMapperWrap.getMapperInstance().writeValueAsString(petitionInfo, "yyyy-MM-dd hh:mm:ss");
			Struts2Utils.getResponse().getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 点击信访编号查看信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 */
	@Autowired
	PetitionBasicInfoManager basicInfoManager;
	@Autowired
	InstructInfoManager instructInfoManager;
	PetitionInfo petitionInfo;
	PetitionDealInfo petitionDeal;
	List<InstructInfo> instructSet;
	Map<String,String>  regionCodeMap;
	String  petitionPrtNo;
	String endReport;
	List<PetitionAccuserInfo> petitionAccuserInfoList;
	List<PetitionAccusedInfo> petitionAccusedInfoList;
	@Action(results={
		@Result(name="success",location="/DataAnalysis/PetitionInfo/Information.jsp"),
		@Result(name="instruction",location="/DataAnalysis/PetitionInfo/Instructions.jsp"),
		@Result(name="input",location="/DataAnalysis/PetitionInfo/DealInfo.jsp")
		})
	public String getBasicInfo() throws Exception{
		String flag = Struts2Utils.getRequest().getParameter("flag");
		String petitionNo = Struts2Utils.getRequest().getParameter("petitionNo");
		String regionCode = Struts2Utils.getRequest().getParameter("regionCode");
		regionCodeMap = basicInfoManager.getRegionCodeMapByPetitionNo(petitionNo);
		PetitionBasicInfo basic = basicInfoManager.getBasicInfoByPetitionNo(petitionNo,regionCode);
		if("0".equals(flag)){
			if (basic != null) {
				petitionPrtNo = basic.getPetitionPrtNo();
				petitionInfo = basicInfoManager.loadPetitionInfo(basic.getId(), basic.getPetitionClassCode());
				if(basic.getCirculationStateInfo()!=null){
					petitionInfo.setDefaultDealerName(basic.getCirculationStateInfo().getDefaultDealerName());
				}
				petitionAccuserInfoList = basicInfoManager.getOtherAccuserInfo(petitionNo,regionCode);
				petitionAccusedInfoList = basicInfoManager.getOtherAccusedInfo(petitionNo,regionCode);
				/*Struts2Utils.getRequest().setAttribute("petitionInfo", petitionInfo);
				Struts2Utils.getRequest().setAttribute("petitionAccuserInfoList", petitionAccuserInfoList);
				Struts2Utils.getRequest().setAttribute("petitionAccusedInfoList", petitionAccusedInfoList);*/
			}
			
			return SUCCESS;
		}else if("2".equals(flag)){
			if (basic != null) {
				petitionPrtNo = basic.getPetitionPrtNo();
				//批示信息需要按照时间倒叙
				Criteria criteria = instructInfoManager.createCriteria();
				criteria.add(Restrictions.eq("petitionBasicInfo.id", basic.getId()));
				criteria.addOrder(Order.asc("instructTime"));
				criteria = CodeSwitchUtil.selectByrRegionCode(criteria, regionCode);
				instructSet = criteria.list();
				PetitionInfo petitionInfo2 = basicInfoManager.loadPetitionInfo(basic.getId(), basic.getPetitionClassCode());
				petitionInfo = new PetitionInfo();
				petitionInfo.setPetitionSourceCode(petitionInfo2.getPetitionSourceCode());
				petitionInfo.setInstruction(petitionInfo2.getInstruction());
				petitionInfo.setInstructor(petitionInfo2.getInstructor());
				petitionInfo.setInstructText(petitionInfo2.getInstructText());
				petitionInfo.setInstructTime(petitionInfo2.getInstructTime());
				petitionInfo.setCurrPetitionNo(petitionInfo2.getCurrPetitionNo());
			}
			return "instruction";
		}else{
			if (basic != null) {
				petitionPrtNo = basic.getPetitionPrtNo();
				petitionDeal = petitionDealInfoManager.getValidDealInfoByBasicOid(basic.getId());
				if(petitionDeal != null){
					PetitionEndInfo petitionEndInfo = petitionDeal.getPetitionEndInfo();
					if(petitionEndInfo !=null && petitionEndInfo.getEndReportInfo()!=null){
						if(petitionEndInfo.getEndReportInfo()!=null){
							endReport= new String(petitionEndInfo.getEndReportInfo().getEndReport());
							endReport = Encrypt.decrypt(endReport);
						}else{
							endReport = "";
						}
					}
				}
			}
			return INPUT;
		}
	}
	public List<InstructInfo> getInstructSet() {
		return instructSet;
	}

	public void setInstructSet(List<InstructInfo> instructSet) {
		this.instructSet = instructSet;
	}
	private String swFlag;
	private String bwRadio;
 	private String dateFlag;
 	private String startDateValue;
 	private String endDateValue;
	public String getSwFlag() {
		return swFlag;
	}
	public String getBwRadio() {
		return bwRadio;
	}

	public void setBwRadio(String bwRadio) {
		this.bwRadio = bwRadio;
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
	public PetitionInfo getPetitionInfo() {
		return petitionInfo;
	}

	public void setPetitionInfo(PetitionInfo petitionInfo) {
		this.petitionInfo = petitionInfo;
	}

	public PetitionDealInfo getPetitionDeal() {
		return petitionDeal;
	}

	public void setPetitionDeal(PetitionDealInfo petitionDeal) {
		this.petitionDeal = petitionDeal;
	}

	public Map<String, String> getRegionCodeMap() {
		return regionCodeMap;
	}

	public void setRegionCodeMap(Map<String, String> regionCodeMap) {
		this.regionCodeMap = regionCodeMap;
	}

	public List<PetitionAccuserInfo> getPetitionAccuserInfoList() {
		return petitionAccuserInfoList;
	}

	public void setPetitionAccuserInfoList(
			List<PetitionAccuserInfo> petitionAccuserInfoList) {
		this.petitionAccuserInfoList = petitionAccuserInfoList;
	}

	public List<PetitionAccusedInfo> getPetitionAccusedInfoList() {
		return petitionAccusedInfoList;
	}

	public void setPetitionAccusedInfoList(
			List<PetitionAccusedInfo> petitionAccusedInfoList) {
		this.petitionAccusedInfoList = petitionAccusedInfoList;
	}
}