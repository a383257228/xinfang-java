package com.sinosoft.xf.dataPredict.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.domain.DataPredictTreeForest;
import com.sinosoft.xf.dataPredict.domainutil.RegionNameCount;
import com.sinosoft.xf.dataPredict.manager.DataPredictTreeForestManager;
import com.sinosoft.xf.petition.domainutil.PetitionInfo;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 树木与森林  action
 * @date 2017-08-24
 * @author 
 */
@Namespace("/dataPredict")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "data-predict-tree-forest.action", type = "redirect") })
public class DataPredictTreeForestAction extends CrudExtActionSupport<DataPredictTreeForest, String> {

	private static final long serialVersionUID = 1L;
	
	DataPredictTreeForest entity = new DataPredictTreeForest();
	@Autowired
	DataPredictTreeForestManager treeForestManager;
	@Override
	protected EntityManager<DataPredictTreeForest, String> getEntityManager() {
		return treeForestManager;
	}
	@Override
	protected void prepareModel() throws Exception {
	}
	
	public DataPredictTreeForest getModel() {
		return entity;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public void searchTree() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		String keyWords = request.getParameter("keyWords");
		
		String json = treeForestManager.queryTreeForest(keyWords); 
		Struts2Utils.getResponse().getWriter().print(json);	
	}

	public void gTest() throws Exception{
		//System.out.println("gTest1");
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		//System.out.println(map);
		ExtjsPage<Map<String, Object>> pageFind = treeForestManager.dataPredictGetAccused(map); 		
		JsonUtils.write(pageFind, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	public void gTest2() throws Exception{
		//System.out.println("gTest2");
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());	
		ExtjsPage<Map<String, Object>> pageFind = treeForestManager.dataPredictGetNexus2(map); 		
		JsonUtils.write(pageFind, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	public void gTest3() throws Exception{
		//System.out.println("gTest2");
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());	
		ExtjsPage<Map<String, Object>> pageFind = treeForestManager.dataPredictGetNexus2(map); 		
		JsonUtils.write(pageFind, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	public void issueTypeTop5() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String json = treeForestManager.queryIssueTypeTop5(map,person);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	public void drillingTopCount() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<RegionNameCount> page = treeForestManager.drillingTopCount(map,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 信访件分布情况
	 * 自收件、转交办钻取查询信的基本信息
	 * @date 2017-08-25
	 * @author shil
	 * @throws IOException
	 * @throws Exception
	 */
	public void queryLetterInfo() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<PetitionInfo> page = treeForestManager.getLetterInfo(map,start, limit,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
}