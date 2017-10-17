package com.sinosoft.xf.petition.accept.web;


import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.petition.accept.manager.PetitionDealInfoManager;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.domainutil.SurveyDealAndEndInfo;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 办理信息action
 * @date 2017-06-22
 * @author guoh
 */
@Namespace("/petition")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-deal-info.action", type = "redirect") })
public class PetitionDealInfoAction extends CrudExtActionSupport<PetitionDealInfo, String>{

	private static final long serialVersionUID = 1L;
	/*
	 * 实体对象
	 */
	PetitionDealInfo entity = new PetitionDealInfo();
	
	@Autowired
	PetitionDealInfoManager entityManager;

	public PetitionDealInfo getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	@Override
	protected EntityManager<PetitionDealInfo, String> getEntityManager() {
		return entityManager;
	}
	/**
	 * 点击搜索查询信件的办理信息
	 * @author guoh
	 * @date 2017-06-22
	 */
	public void loadSurveyDealAndEndInfo(){
		try {
			SurveyDealAndEndInfo surveyDealAndEndInfo = entityManager.loadSurveyDealAndEndInfo(id);
			String json = ObjectMapperWrap.getMapperInstance().writeValueAsString(surveyDealAndEndInfo, "yyyy-MM-dd hh:mm:ss");
			Struts2Utils.getResponse().getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}