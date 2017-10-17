package com.sinosoft.xf.dataPredict.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.domain.DataPredictOpinionInfo;
import com.sinosoft.xf.dataPredict.domainutil.QuestionTypeBean;
import com.sinosoft.xf.dataPredict.domainutil.TreeNodeBean;
import com.sinosoft.xf.dataPredict.manager.DataPredictOpinionInfoManager;
import com.sinosoft.xf.petition.domainutil.PetitionInfo;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 民意分析  action
 * @date 2017-08-12
 * @author liyang
 */
@Namespace("/dataPredict")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "data-predict-opinion-info.action", type = "redirect") })
public class DataPredictOpinionInfoAction extends CrudExtActionSupport<DataPredictOpinionInfo, String> {

	private static final long serialVersionUID = 1L;
	DataPredictOpinionInfo entity = new DataPredictOpinionInfo();
	@Autowired
	DataPredictOpinionInfoManager opinionInfoManager;
	@Override
	protected EntityManager<DataPredictOpinionInfo, String> getEntityManager() {
		return opinionInfoManager;
	}
	@Override
	protected void prepareModel() throws Exception {
	}
	
	public DataPredictOpinionInfo getModel() {
		return entity;
	}
    
	/**
	 * 获取新词列表
	 * @author liyang
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getNewWordList() throws Exception{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		ExtjsPage<Map<String, Object>> page = opinionInfoManager.getNewWord(start, limit); 
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		
	}
	
	/**
	 * 更新忽略信息 、加入词典
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void updateIgnore() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String oids = request.getParameter("oid");
		String otype = request.getParameter("otype");
		opinionInfoManager.updateIgnore(oids,otype,person); 
	}
	
	/**
	 * 保存新词
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void saveNewWord() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String oids = request.getParameter("oid");
		String newVal = request.getParameter("newVal");
		opinionInfoManager.saveNewWord(oids,newVal,person); 
	}
	/**
	 * 获取词典路径及分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getAllDict() throws Exception{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		List<Map<String, Object>> list = opinionInfoManager.getAllDict(); 
		if(null != list && list.size() > 0) {
			JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		} 
	}
	/**
	 * 新词分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void updateNewWordClass() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		opinionInfoManager.updateNewWordClass(map,person); 
		
	}
	
	/**
	 * 新词合并
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void mergeNewWord() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		opinionInfoManager.mergeNewWord(map,person); 
		
	}
	
	/**
	 * 获取问题类别bean
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getQuesTypeBean() throws Exception{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		List<QuestionTypeBean> list = opinionInfoManager.getQuesTypeBean();
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 加入词典   获取问题类别bean
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getTreeNodeBean() throws Exception{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		List<TreeNodeBean> list = opinionInfoManager.getTreeNodeBean(); 
		if(null != list && list.size() > 0) {
			JsonUtils.write(list.get(0).getChildren(), Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		} 
	}
	
	/**
	 * 添加分类页面   获取问题类别bean
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getClassTreeBean() throws Exception{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		List<TreeNodeBean> list = opinionInfoManager.getClassTreeBean(); 
		if(null != list && list.size() > 0) {
			JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		} 
	}
	
	/**
	 * 添加分类页面  添加分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void addClass() throws Exception{
	
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		opinionInfoManager.addClass(map,person); 
	}
	
	/**
	 * 获取词云数据
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getWordCloud() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String queTypeId = request.getParameter("queTypeId");
		List<Map<String,String>> list = opinionInfoManager.getWordCloud(queTypeId);
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	/**
	 * 获取词典类型   违纪违法 
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getDictType() throws Exception{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		List<Map<String,Object>> list = opinionInfoManager.getDictType();
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	/**
	 * 获取区信息
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getAreaMess() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String newWord = request.getParameter("newWord");
		List<Map<String,Object>> list = opinionInfoManager.getAreaMess(newWord);
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	/**
	 * 获取派驻信息
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void getCommitteeMess() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
//		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");
		String newWord = request.getParameter("newWord");
		List<Map<String,Object>> list = opinionInfoManager.getCommitteeMess(newWord);
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	/**
	 * 自收件、转交办钻取查询信的基本信息
	 * @date 2017-08-25
	 * @author liyang
	 * @throws IOException
	 * @throws Exception
	 */
	public void queryLetterInfo() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<PetitionInfo> page = opinionInfoManager.getLetterInfo(map,start, limit,person);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 获取某一区的信访编号
	 * @date 2017-08-25
	 * @author liyang
	 * @throws IOException
	 * @throws Exception
	 */
	public void getALLPitId() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		List<Map<String,Object>> list = opinionInfoManager.getALLPitId(map);
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	
}