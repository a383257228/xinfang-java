package com.sinosoft.xf.dataPredict.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.domain.DataPredictDistributionInfo;
import com.sinosoft.xf.dataPredict.manager.DataPredictDistributionInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 信访件分布  action
 * @date 2017-08-12
 * @author liyang
 */
@Namespace("/dataPredict")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "data-predict-distribution-info.action", type = "redirect") })
public class DataPredictDistributionInfoAction extends CrudExtActionSupport<DataPredictDistributionInfo, String> {

	private static final long serialVersionUID = 1L;
	
	DataPredictDistributionInfo entity = new DataPredictDistributionInfo();
	@Autowired
	DataPredictDistributionInfoManager distributionInfoManager;
	@Override
	protected EntityManager<DataPredictDistributionInfo, String> getEntityManager() {
		return distributionInfoManager;
	}
	@Override
	protected void prepareModel() throws Exception {
	}
	
	public DataPredictDistributionInfo getModel() {
		return entity;
	}
	/**
	 * 查询区下个月的预测数据
	 * @return
	 * @throws IOException
	 */
	public void dataPredict() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String json = distributionInfoManager.dataPredict(swFlag,dateFlag,startDateValue,endDateValue,person); 
		Struts2Utils.getResponse().getWriter().print(json);	
	}
	/**
	 * 查询派驻下个月的预测数据
	 * @return
	 * @throws IOException
	 */
	public void dataCityOne() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String json = distributionInfoManager.dataCityOne(swFlag,dateFlag,startDateValue,endDateValue,person); 
		Struts2Utils.getResponse().getWriter().print(json);		
	}
	/**
	 * 查询区和派驻的一年的数据的趋势图
	 * @return
	 * @throws IOException
	 */
	public void trend() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String json = distributionInfoManager.trend(code,person); 
		Struts2Utils.getResponse().getWriter().print(json);		
	}
	/**
	 * 查询上海市的一年的数据的趋势图
	 * @throws IOException
	 */
	public void year() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String json = distributionInfoManager.year(code,person); 
		Struts2Utils.getResponse().getWriter().print(json);		
	}
	private String swFlag;
 	private String dateFlag;
 	private String startDateValue;
 	private String endDateValue;
 	private String code; 	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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