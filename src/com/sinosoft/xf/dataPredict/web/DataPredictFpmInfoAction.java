package com.sinosoft.xf.dataPredict.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.sinosoft.xf.dataPredict.domain.DataPredictFpmInfo;
import com.sinosoft.xf.dataPredict.domainutil.PetitionInfo;
import com.sinosoft.xf.dataPredict.manager.DataPredictFpmInfoManager;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;




/**
 * 关联分析  action
 * @date 2017-08-12
 * @author liyang
 */
@Namespace("/dataPredict")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "data-predict-fpm-info.action", type = "redirect") })
public class DataPredictFpmInfoAction extends CrudExtActionSupport<DataPredictFpmInfo, String> {

	private static final long serialVersionUID = 1L;
	DataPredictFpmInfo entity = new DataPredictFpmInfo();
	@Autowired
	DataPredictFpmInfoManager fpmInfoManager;
	@Override
	protected EntityManager<DataPredictFpmInfo, String> getEntityManager() {
		return fpmInfoManager;
	}
	@Override
	protected void prepareModel() throws Exception {
	}
	
	public DataPredictFpmInfo getModel() {
		return entity;
	}
	
	/**
	 * 问题类别变化情况（查询区）
	 * @author lzd
	 * @throws IOException
	 */
	public void fpmQW() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		ExtjsPage<PetitionInfo> page = fpmInfoManager.dataPredictFPMQW(map,start, limit,person); 
		JsonUtils.write(page.getResult().get(0).getResult(), Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 问题类别变化情况（查询派驻）
	 * @author lzd
	 * @throws IOException
	 */
	public void fpmS() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		ExtjsPage<PetitionInfo> page = fpmInfoManager.dataPredictFPMS(map,start, limit,person); 
		JsonUtils.write(page.getResult().get(0).getResult(), Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 上海市告警
	 * @author lzd
	 * @throws IOException
	 */
	public void alarmCity() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		ExtjsPage<PetitionInfo> pageFind = fpmInfoManager.dataPredictFindAlarm(map,start, limit,person); 
		JsonUtils.write(pageFind.getResult().get(0).getResult(), Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	/**
	 * 区，委办告警
	 * @author lzd
	 * @throws IOException
	 */
	public void alarmOther() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		PersonInfo person = (PersonInfo) request.getSession().getAttribute("personInfo");//获取当前登录人的regionCode
		ExtjsPage<PetitionInfo> pageFind = fpmInfoManager.dataPredictFindAlarmOther(map,start, limit,person); 
		JsonUtils.write(pageFind.getResult().get(0).getResult(), Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	/**
	 * 查找问题类别与行政级别下拉框的区域
	 * @author lzd
	 * @throws IOException
	 */
	public void findSelectData() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		//区，派驻下拉框动态获取
		ExtjsPage<Map<String, Object>> getName = fpmInfoManager.getNameQuAndPaizhu(map);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < getName.getResult().size(); i++) {
			Map<String, Object> getRegName = getName.getResult().get(i);
			String obCodeSum = (String) getRegName.get("REGIN_NAME");// 区域
			sb.append(obCodeSum).append(",");
		}
		String regNameStr = sb.toString().substring(0, sb.toString().length() - 1);
		map.put("regNameStr", regNameStr);// 区域(下拉框)
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		getName.setResult(list);
		JsonUtils.write(getName, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
	}
	
	/**
	 * 查找问题类别与行政级别关联关系数据
	 * @author lzd
	 * @throws IOException
	 */
	public void findDataQL() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		//根据区、派驻名字去查对应的行政级别个数
		ExtjsPage<Map<String, Object>> pageFindOBCode = fpmInfoManager.findOBCount(map);
		for (int i = 0; i < pageFindOBCode.getResult().size(); i++) {
			Map<String, Object> mapResult = pageFindOBCode.getResult().get(i);
			int obCodeSum = (int) mapResult.get("cou");// 获取行政级别个数
			int issCodeSum = (int) mapResult.get("isscou");// 获取问题类别个数
			double le = issCodeSum / (double) 10;
			double sum = Math.ceil(le);// 向上取整
			int pag = (new Double(sum)).intValue(); // 分页数
			map.put("pag", pag); //分页数
			map.put("obCodeSum", obCodeSum);
		}
		ExtjsPage<Map<String, Object>> pageFindQL = fpmInfoManager.findDataQL(map);
		StringBuffer sbi = new StringBuffer();
		StringBuffer sbo = new StringBuffer();

		List<List<String>> listIOSAll = new ArrayList<>(); // 存所有数据的list集合
		if(pageFindQL.getResult().size() == 0){
			JsonUtils.write(pageFindQL, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		} else {
			List<List<Integer>> listAll = dealData(pageFindQL, listIOSAll, sbi, sbo, map);
			map.put("data", listAll); //数据
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(map);
			pageFindQL.setResult(list);
			JsonUtils.write(pageFindQL, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		}
		
	}
	/**
	 * //定义字典 用来合并正负级
	 * @author lzd
	 * @return Map<String, String>
	 */
	public Map<String, String> defineDictionary() {
		Map<String, String> dictionary = new HashMap<String, String>();
		dictionary.put("正省部级", "省部级");
		dictionary.put("副省部级", "省部级");
		dictionary.put("正厅局级", "厅局级");
		dictionary.put("副厅局级", "厅局级");
		dictionary.put("正县处级", "县处级");
		dictionary.put("副县处级", "县处级");
		dictionary.put("正乡科级", "乡科级");
		dictionary.put("副乡科级", "乡科级");
		return dictionary;
	}
	/**
	 * 定义map 用来累加正负级支持度
	 * @param listIOSAll 存所有数据的list集合
	 * @return Map<String, String>
	 */
	public Map<String, String> accumulate(List<List<String>> listIOSAll){
		//定义map 用来累加正负级支持度
		Map<String, String> supAllMap = new HashMap<String, String>();
		for (int j = 0; j < listIOSAll.size(); j++) {
			List<String> arr = listIOSAll.get(j);
			String issKey = arr.get(0) + "-" +arr.get(1);//key:"a-b" value:n
			if(!supAllMap.containsKey(issKey)){  //判断map是否存在key，存在则value相加，不存在key则添加
				supAllMap.put(issKey, arr.get(2));
			} else {
				String val = supAllMap.get(issKey);
				int valInt = Integer.valueOf(val).intValue();
				int valInt2 = Integer.valueOf(arr.get(2)).intValue();
				int all = valInt + valInt2; //value相加
				String s = Integer.toString(all);//类型转换
				supAllMap.put(issKey, s);
			}
		}
		return supAllMap;
	}
	
	/**
	 * 处理相应格式的数据返回 页面
	 * @param pageFindQL 查找问题类别与行政级别关联关系数据
	 * @param listIOSAll  存所有数据的list集合
	 * @param sbi StringBuffer
	 * @param sbo StringBuffer
	 * @param map 页面回去参数集合
	 * @return List<List<Integer>>
	 */
	public List<List<Integer>> dealData(ExtjsPage<Map<String, Object>> pageFindQL, List<List<String>> listIOSAll, StringBuffer sbi, StringBuffer sbo, Map<String, Object> map){
		Map<String, String> dictionary = defineDictionary();//定义字典 用来合并正负级
		for (int i = 0; i < pageFindQL.getResult().size(); i++) {
			Map<String, Object> mapResult = pageFindQL.getResult().get(i);
			String issueTypeName = (String) mapResult.get("ISSUE_TYPE_NAME");// 获取问题类别名字
			String objectClassName = (String) mapResult.get("OBJECT_CLASS_NAME");// 获取行政级别名字
//			String objectClassCode = (String) mapResult.get("OBJECT_CLASS_CODE");// 获取行政级别code
			int support = (int) mapResult.get("SUPPORT");// 获取支持度
			String objName = dictionary.get(objectClassName);
			//判断行政级别是否存在在map中
			if("".equals(objName) || objName == null){
				objName = objectClassName;
			}
			String s = String.valueOf(support);
			List<String> listIOS = new ArrayList<>();
			listIOS.add(issueTypeName);
			listIOS.add(objName);
			listIOS.add(s);
			listIOSAll.add(listIOS);
			sbi.append(issueTypeName).append(",");
			sbo.append(objectClassName).append(",");
		}
		String issueTypeNameStr = sbi.toString().substring(0, sbi.toString().length() - 1);
		String[] stri = issueTypeNameStr.split(",");
		String[] strii = arrToHeavy(stri);// 数组去重
		String objectClassCodeStr = sbo.toString().substring(0, sbo.toString().length() - 1);
		String[] stro = objectClassCodeStr.split(",");
		String[] stroo = arrToHeavy(stro);// 数组去重
        
		map.put("issueTypeLen", strii.length);// 问题类别个数
		map.put("issueTypeName", strii); // 问题类别集合
		StringBuffer sb = new StringBuffer();
		//合并正负级
		for (int i = 0; i < stroo.length; i++) {
			String objName = dictionary.get(stroo[i]);
			//判断行政级别是否存在在map中
			if("".equals(objName) || objName == null){
				sb.append(stroo[i]).append(",");
			} else {
				sb.append(objName).append(",");
			}
		}
		String objNameArr = sb.toString().substring(0, sb.toString().length() - 1);
		String[] obj = objNameArr.split(",");
		String[] objj = arrToHeavy(obj);// 数组去重
		map.put("objectClassName", objj); //行政级别集合
		
		// 映射 问题类别
		Map<String, Integer> mapArrIs = new HashMap<String, Integer>();
		Arrays.sort(strii);//数组排序
		for (int i = 0; i < strii.length; i++) {
			mapArrIs.put(strii[i], i);
		}
		// 映射 行政级别
		Map<String, Integer> mapArrOb = new HashMap<String, Integer>();
		Arrays.sort(objj);//数组排序
		for (int i = 0; i < objj.length; i++) {
			mapArrOb.put(objj[i], i);
		}
		//定义map 用来累加正负级支持度
		Map<String, String> supAllMap = accumulate(listIOSAll);
        //把累加support的map拆解合并		
		List<List<Integer>> listAll = dismantlingMerger(supAllMap, mapArrIs, mapArrOb);
		return listAll;
	}
	
	/**
	 * 把累加support的map拆解合并
	 * @param supAllMap 合并support之后的map
	 * @param mapArrIs 字典 
	 * @param mapArrOb  字典
	 * @return List<List<Integer>>
	 */
	public List<List<Integer>> dismantlingMerger(Map<String, String> supAllMap, Map<String, Integer> mapArrIs, Map<String, Integer> mapArrOb){
		//构造新数据格式页面展示
		List<List<String>> listNameAll = new ArrayList<>(); // 存所有数据的list集合
		for (Map.Entry<String, String> entry : supAllMap.entrySet()) {  
			String key = entry.getKey();
			List<String> lis = new ArrayList<>();
			lis.add(key.split("-")[0]);
			lis.add(key.split("-")[1]);
			lis.add(entry.getValue());
			listNameAll.add(lis);
		}  
		
		//数据处理相对应的格式在页面展示
		List<List<Integer>> listAll = new ArrayList<>(); // 存所有数据的list集合
		for (int i = 0; i < listNameAll.size(); i++) {
			List<String> arr = listNameAll.get(i);
			int isInt = mapArrIs.get(arr.get(0)); 
			int obInt = mapArrOb.get(arr.get(1)); 
			String ss = arr.get(2);// 支持度
			int s = Integer.parseInt(ss);
			List<Integer> listAllData = new ArrayList<>();
			listAllData.add(obInt);
			listAllData.add(isInt);
			listAllData.add(s);
			listAll.add(listAllData);
		}
		return listAll;
	}
	/**
	 * 问题top5之间的隐藏关系
	 * @throws Exception
	 */
	public void findDataTOP() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		// 查看TOP5
		ExtjsPage<Map<String, Object>> pageFindTOP = fpmInfoManager.findDataTOP(map);
		// 判断时间维度下的数据是否为空
		if (pageFindTOP.getResult().size() == 0) {
			JsonUtils.write(pageFindTOP, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		} else {
			List<List<Integer>> listAll = dealDataTop(pageFindTOP, map);
			map.put("data", listAll); // 数据
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(map);
			pageFindTOP.setResult(list);
			JsonUtils.write(pageFindTOP, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
		}
	}
	/** 
	 * 问题TOP5处理相应格式的数据返回 页面
	 * @param pageFindTOP  查看TOP5
	 * @param map 页面回去参数集合
	 * @return List<List<Integer>>
	 */
	public List<List<Integer>> dealDataTop(ExtjsPage<Map<String, Object>> pageFindTOP, Map<String, Object> map){
		StringBuffer sbi = new StringBuffer();
		for (int i = 0; i < pageFindTOP.getResult().size(); i++) {
			Map<String, Object> mapResult = pageFindTOP.getResult().get(i);
			String issueTypeName = (String) mapResult.get("ISSUE_TYPE_NAME_1");// 获取问题类别前五位名字
			sbi.append(issueTypeName).append(",");
		}
        //截取
		String issueTypeNameStr = sbi.toString().substring(0, sbi.toString().length() - 1);
		String[] stri = issueTypeNameStr.split(",");
		map.put("issueTypeName", stri); // 问题
		// 映射 问题类别
		Map<String, Integer> mapArrIs = new HashMap<String, Integer>();
		for (int i = 0; i < stri.length; i++) {
			mapArrIs.put(stri[i], i);
		}
		List<List<Integer>> listAll = new ArrayList<>(); // 存所有数据的list集合
		for (Map.Entry<String, Integer> m : mapArrIs.entrySet()) {
			for (Map.Entry<String, Integer> n : mapArrIs.entrySet()) {
				map.put("issueTypeName1", m.getKey()); // 问题1
				map.put("issueTypeName2", n.getKey()); // 问题2
				// 根据问题1，问题2 去补全的数据表中查找对应的support
				ExtjsPage<Map<String, Object>> completion = fpmInfoManager.completion(map);
				List<Integer> listAllData = new ArrayList<>();
				if (completion.getResult().size() == 0) { //查询不到数据支持度默认赋值为0
					 listAllData.add(m.getValue());
					 listAllData.add(n.getValue());
					 listAllData.add(0);
					 listAll.add(listAllData);
				} 
				for (int i = 0; i < completion.getResult().size(); i++) {
					Map<String, Object> mapResultc = completion.getResult().get(i);
					int support = (int) mapResultc.get("SUPPORT");// 获取支持度
					listAllData.add(m.getValue());
					listAllData.add(n.getValue());
					listAllData.add(support);
					listAll.add(listAllData);
				}
			}

		}
		return listAll;
	}
	/***
	 * 数组去重
	 * @return
	 */
	public String[] arrToHeavy(String[] array) {
		List<String> list = new ArrayList<>();
		list.add(array[0]);
		for (int i = 1; i < array.length; i++) {
			if (list.toString().indexOf(array[i]) == -1) {
				list.add(array[i]);
			}
		}
		String[] arrayResult = (String[]) list.toArray(new String[list.size()]);
		return arrayResult;
	}
     
     /**
 	 * 根据oid查找信访件的详细信息
 	 * @author lzd
 	 * @throws IOException
 	 */
 	public void fpmFindInfo() throws Exception{
 		HttpServletRequest request = Struts2Utils.getRequest();
 		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
 		ExtjsPage<Map<String, Object>> pageFind = fpmInfoManager.fpmFindInfo(map); 
 		JsonUtils.write(pageFind, Struts2Utils.getResponse().getWriter(), "", getDatePattern());
 	}
 	
 	/**
	 * 更新未操作字段
	 * @author lzd 
	 * @date 2017-08-30
	 * @throws Exception
	 */
	public void updateUnconfirmed() throws Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		String oids = request.getParameter("oid");
		fpmInfoManager.updateUnconfirmed(oids); 
	}
}