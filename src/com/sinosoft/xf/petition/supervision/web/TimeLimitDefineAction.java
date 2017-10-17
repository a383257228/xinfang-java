package com.sinosoft.xf.petition.supervision.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.petition.supervision.manager.TimeLimitDefineManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 时限action
 * 
 * @author ljx
 * @createDate 2011-04-27
 */
@Namespace("/timelimit")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { 
@Result(name = "export", location = "../versiondownload/index.jsp") })
public class TimeLimitDefineAction extends CrudExtActionSupport<TimeLimitDefine, String> {

	private static final long serialVersionUID = 1L;

	public String headers;
	public String widths;
	public String dataIndexs;
	public String filterTxts;
	public String filterValues;
	public String regionCode;
	/**
	 * 时限业务层manager
	 */
	@Autowired
	TimeLimitDefineManager timeLimitDefineManager;
	/**
	 * 时限业务层实体类对象
	 */
	TimeLimitDefine entity = new TimeLimitDefine();

	/**
	 * 重写父类方法
	 * 
	 * @throws Exception
	 */
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = timeLimitDefineManager.get(id);
		} else {
			entity = new TimeLimitDefine();
		}
	}

	/**
	 * 重写父类的方法
	 * 
	 * @return 时限Manager
	 */
	protected EntityManager<TimeLimitDefine, String> getEntityManager() {
		return timeLimitDefineManager;
	}

	/**
	 * 获得时限实体
	 * 
	 * @return 时限实体
	 */
	public TimeLimitDefine getModel() {
		return entity;
	}

	/**
	 * 可编辑列表保存方法
	 * 
	 * @author ljx
	 * @throws Exception
	 * @createDate 2011-05-6
	 */
	public void saveGridData() throws Exception {
		// 对JSON数据进行转换，将JSON数据组转换成对象集合
		List<TimeLimitDefine> timeLimitDefineList = JsonUtils.json2List(data,TimeLimitDefine.class);
		for (TimeLimitDefine t : timeLimitDefineList) {
			timeLimitDefineManager.save(t);
		}
		Struts2Utils.getResponse().getWriter().print(Constants.SUCCESS_TRUE);
	}

	/**
	 * 查询时限信息
	 * 
	 * @author ljx
	 * @throws Exception , IOException
	 * @createDate 2011-05-5
	 */
	public void getTimeLimitInfoList() throws IOException, Exception {
		ExtjsPage<TimeLimitDefine> extjsPage = timeLimitDefineManager.pagedQueryTimeLimitInfo(start,limit,filterTxt,filterValue,sort,dir);
		JsonUtils.write(extjsPage, Struts2Utils.getResponse().getWriter(),new String[] {}, getDatePattern());
	}


	/**
	 * 拼装纵向转发“不同下级转发信访量对比”柱形图需要的数据集的方法
	 * 
	 * @author mengxy
	 * @date 2011-05-25
	 * @throws IOException,Exception
	 */
	public void doRegionChart() throws IOException, Exception {
		if (filterTxt != null && !"".equals(filterTxt) && filterValue != null
				&& !"".equals(filterValue)) {
			Map<String,String> map = new HashMap<String,String>();
			String xmlData = "<chart></chart>";
			String[] filterT = filterTxt.split(";");
			String[] filterV = filterValue.split(";");
			for (int i = 0; i < filterT.length; i++) {
				/*
				 * @param Map
				 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
				 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1，
				 * IsReceive1 下级未接收 0,1
				 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
				 * AccusedName:被反映人,RRegionCode、RRegionCodeName:所属区域,objectClassName,行政级别
				 * ToRegionCode、ToRegionCodeName:转发机构,AccuserName:反映人
				 */
				map.put(filterT[i], filterV[i]);
			}
			PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
			map.put("CurrRegion", person.getRegionCode());
			xmlData = timeLimitDefineManager.doRegionChart(person, map);
			Struts2Utils.getResponse().getWriter().print(xmlData);
		} else {
			Struts2Utils.getResponse().getWriter().print("<chart></chart>");
		}
	}

	/**
	 * 拼装纵向转发“不同下级首次回复时限对比”柱形图需要的数据集的方法
	 * 
	 * @author mengxy
	 * @date 2011-05-27
	 * @throws IOException,Exception
	 */
	public void doReplyChart() throws IOException, Exception {
		if (filterTxt != null && !"".equals(filterTxt) && filterValue != null
				&& !"".equals(filterValue)) {
			Map<String,String> map = new HashMap<String,String>();
			String xmlData = "<chart></chart>";
			String[] filterT = filterTxt.split(";");
			String[] filterV = filterValue.split(";");
			for (int i = 0; i < filterT.length; i++) {
				/*
				 * @param Map
				 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
				 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1，
				 * IsReceive1 下级未接收 0,1
				 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
				 * AccusedName:被反映人,RRegionCode、RRegionCodeName:所属区域,objectClassName,行政级别
				 * ToRegionCode、ToRegionCodeName:转发机构,AccuserName:反映人
				 */
				map.put(filterT[i], filterV[i]);
			}
			PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
			map.put("CurrRegion", person.getRegionCode());
			xmlData = timeLimitDefineManager.doReplyChart(person, map);
			Struts2Utils.getResponse().getWriter().print(xmlData);
		} else {
			Struts2Utils.getResponse().getWriter().print("<chart></chart>");
		}
	}

	/**
	 * 拼装纵向接收“未接收操作时限对比分析”柱形图需要的数据集的方法
	 * 
	 * @author mengxy
	 * @date 2011-06-07
	 * @throws IOException,Exception
	 */
	public void doReceiveChart() throws IOException, Exception {
		if (filterTxt != null && !"".equals(filterTxt) && filterValue != null && !"".equals(filterValue)) {
			Map<String,String> map = new HashMap<String,String>();
			String[] filterT = filterTxt.split(";");
			String[] filterV = filterValue.split(";");
			for (int i = 0; i < filterT.length; i++) {
				/*
				 * @param Map sendDate：传输起始日期，sendDate1：传输终止日期
				 * XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
				 */
				map.put(filterT[i], filterV[i]);
			}
			PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
			map.put("CurrRegion", person.getRegionCode());
			String xmlData = timeLimitDefineManager.doReceiveChart(map);
			Struts2Utils.getResponse().getWriter().print(xmlData);
		} else {
			Struts2Utils.getResponse().getWriter().print("<chart></chart>");
		}
	}
	/**
	 * 查询转发区域信息
	 * 
	 * @author ljx
	 * @throws Exception
	 * @throws IOException
	 * @createDate 2011-05-23
	 */
	public void getToRegionCodeList() throws IOException, Exception {
		String json = timeLimitDefineManager.getToRegionCodeList();
		Struts2Utils.getResponse().getWriter().print(json);
	}


	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getWidths() {
		return widths;
	}

	public void setWidths(String widths) {
		this.widths = widths;
	}

	public String getDataIndexs() {
		return dataIndexs;
	}

	public void setDataIndexs(String dataIndexs) {
		this.dataIndexs = dataIndexs;
	}

	public String getFilterTxts() {
		return filterTxts;
	}

	public void setFilterTxts(String filterTxts) {
		this.filterTxts = filterTxts;
	}

	public String getFilterValues() {
		return filterValues;
	}

	public void setFilterValues(String filterValues) {
		this.filterValues = filterValues;
	}

	/**
	 * 按regionCode查询区域代码表记录数
	 * 
	 * @author ljx
	 * @throws IOException
	 * @createDate 2011-06-08
	 */
	public void getRegionSize() throws IOException {
		String json = timeLimitDefineManager.getRegionSize(regionCode);
		Struts2Utils.getResponse().getWriter().print(json);
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
}
