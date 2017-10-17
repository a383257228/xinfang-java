package com.sinosoft.xf.query.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.query.domain.PetitionQuerySave;
import com.sinosoft.xf.query.manager.ForecastAnalysisManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@SuppressWarnings("serial")
@Namespace("/query")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { 
	@Result(name = CrudActionSupport.RELOAD, location = "forecast-analysis.action", type = "redirect"),
})
public class ForecastAnalysisAction extends CrudActionSupport<PetitionQuerySave,String>{

	PetitionQuerySave entity;
	@Autowired
	ForecastAnalysisManager forecastAnalysisManager;
	/**
	 * @author ZHZ
	 * @throws IOException 
	 * @date 2017年5月22日 下午6:15:56
	 */
	public void queryDealWays() throws IOException{
		String json = forecastAnalysisManager.queryDealWays();
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return forecastAnalysisManager;
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		if(id==null||"".equals(id)){
			entity = new PetitionQuerySave();
		}
	}
}
