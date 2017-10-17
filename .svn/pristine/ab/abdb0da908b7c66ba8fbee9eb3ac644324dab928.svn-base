package com.sinosoft.xf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.sinosoft.organization.domain.OrganMergeSplitInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganMergeSplitInfoManager;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoft.systemcode.manager.SystemCodeNodeInfoManager;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.system.set.domain.CentralizedDeploymentInfo;
import com.sinosoft.xf.system.set.manager.CentralizedDeploymentInfoManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 编码转换类
 * @author HJM
 *
 */
public class CodeSwitchUtil {
	/**
	 * 系统码表
	 * key : codeType
	 * value : codeType对应的List<SystemCodeNodeInfo>
	 */
	private static Map<String,List<SystemCodeNodeInfo>>  codeMapList = new HashMap<String,List<SystemCodeNodeInfo>>();
	/**
	 * 工作流状态map
	 * ActivityNo：activityName
	 */
	private static Map<String, String> activityMap = new HashMap<String, String>();
	//private static  Map<String, List<PetitionWorkflowEngineInfo>> workflowListMap = new HashMap<String, List<PetitionWorkflowEngineInfo>>();
	/**
	 * 集中式部署Map
	 * regioncode：regionName
	 */
	private static Map<String, String> centralizedMap = new HashMap<String, String>();
	private static  Map<String, String> loginIpMap = new HashMap<String, String>();
	/**
	 * 机构调整map
	 * 结构：{调整后的orgCode ：调整前的orgCode集合}
	 */
	private static Map<String, String> orgAdjustMap = new HashMap<String, String>();
	
	/**
	 * 系统启动执行的方法，
	 * 初始化系统码表
	 * 初始化状态信息集合
	 * 初始化工作流
	 * 初始化集中式部署
	 * @throws Exception 
	 */
	 static void init() throws Exception {
		//初始化系统码表
		if (codeMapList.isEmpty()) {
			SystemCodeNodeInfoManager systemCodeNodeInfoManager = AppUtils.getBean("systemCodeNodeInfoManager");
			List<SystemCodeNodeInfo> list = systemCodeNodeInfoManager.getAll();// 加载系统码表
			for (SysCodeType sysCodeType : SysCodeType.values()) {
				String tempCode = sysCodeType.toString();
				List<SystemCodeNodeInfo> listCodeNode = new ArrayList<SystemCodeNodeInfo>();
				for (Iterator<SystemCodeNodeInfo> iterator = list.iterator(); iterator.hasNext();) {
					SystemCodeNodeInfo codeNode = iterator.next();
					if(tempCode.equals(codeNode.getCodeType())){
						if("Y".equals(codeNode.getAvailable())){//只存放有效的
							codeNode.setChildNodes(null);
							codeNode.setSystemcode(null);
							listCodeNode.add(codeNode);
							iterator.remove();
						}
					}
				}
				codeMapList.put(tempCode, listCodeNode);
			}
		}
		//初始化状态信息集合
	/*	if(activityMap.isEmpty()){
			PetitionWorkflowActivityInfoManager petitionWorkflowActivityInfoManager = AppUtils.getBean("petitionWorkflowActivityInfoManager");
			List<PetitionWorkflowActivityInfo> list = petitionWorkflowActivityInfoManager.getAll();
			for(PetitionWorkflowActivityInfo activityInfo : list) {
				activityMap.put(activityInfo.getActivityNo(), activityInfo.getActivityName());
			}
		}*/
		//初始化工作流
		/*if (workflowListMap.isEmpty()) {
			PetitionWorkflowEngineInfoManager petitionWorkflowEngineInfoManager = AppUtils.getBean("petitionWorkflowEngineInfoManager");
			List<PetitionWorkflowEngineInfo> list = petitionWorkflowEngineInfoManager.getAll();
			for (PetitionWorkflowEngineInfo workflow : list) {
				//工作流  有效无效标示 1为有效 0为无效
				if ("1".equals(workflow.getValidFlag())) {
					String key = workflow.getActivityNo() + workflow.getConditionCode();
					List<PetitionWorkflowEngineInfo> workflowList = workflowListMap.get(key);
					if (workflowList == null || workflowList.isEmpty()) {
						workflowList = new ArrayList<PetitionWorkflowEngineInfo>();
					}
					workflowList.add(workflow);
					workflowListMap.put(key, workflowList);
				}
			}
		}*/
		//初始化集中式部署
		if(centralizedMap.isEmpty()){
			CentralizedDeploymentInfoManager centralizedDeploymentInfoManager = AppUtils.getBean("centralizedDeploymentInfoManager");
			List<CentralizedDeploymentInfo> list = centralizedDeploymentInfoManager.getAll();
			for (CentralizedDeploymentInfo centr : list) {
				centralizedMap.put(centr.getRegionCode(), centr.getRegionName());
			}
		}
		//初始化机构调整集合
		if(orgAdjustMap.isEmpty()){
			OrganMergeSplitInfoManager organMergeSplitInfoManager = AppUtils.getBean("organMergeSplitInfoManager");
			List<OrganMergeSplitInfo> list = new ArrayList<OrganMergeSplitInfo>();
			//当映射表内存在一些本机构organization表中没有的 记录 对应的话，会报错。
			try {
				list = organMergeSplitInfoManager.getAll();
			} catch (Exception e) {
				System.err.println("==========映射表中有本数据库organization表中不存在的记录id===来报错位置源码这里拿sql验证一下=========================");
				//SELECT * FROM  (SELECT ORGAN_ID AS id FROM ORGAN_MERGE_SPLIT_INFO UNION SELECT ORGANIZATION_INFO_ID  AS id FROM ORGAN_MERGE_SPLIT_INFO) om  WHERE  om.ID NOT IN (SELECT oid FROM ORGANIZATION);
			}
			Map<String,Set<String>> targetMap = new HashMap<String,Set<String>>();
			if(list!= null && list.size()>0){
				//获取所有的键集合(结果map 的key，set有过滤重复的功能)；
				Set<String> targets = new HashSet<String>();
				for (OrganMergeSplitInfo organMergeSplitInfo : list){
					targets.add(organMergeSplitInfo.getOrganizationInfo().getOrgCode());
				}
				//外层遍历
				for (String str : targets){
					//递归调用，外层传入：当前key，总的集合，用来返回的，值集合。
					HashSet<String> result = getAll(str, list, new HashSet<String>());
					targetMap.put(str, result);
				}
				//结果 格式化 装入最终容器
				for (Entry<String, Set<String>> item : targetMap.entrySet()) {
					orgAdjustMap.put(item.getKey(), item.getValue().toString().replace(" ","").replace("[", "").replace("]", "")+","+item.getKey());
				}
			}
		}
		//初始化登录IP
		/*if(loginIpMap.isEmpty()){
			LoginIpManager loginIpManager = AppUtils.getBean("loginIpManager");
			List<LoginIp> list = loginIpManager.getAll();
			for (LoginIp logonIp : list) {
				loginIpMap.put(logonIp.getIp(), logonIp.getRegionCode()+";"+logonIp.getRegionName());
			}
		}*/
	}
	 /**
	  * 递归获取调整机构的对应关系
	  * @param parent
	  * @param list
	  * @param result
	  * @return
	  */
	 public static HashSet<String> getAll(String parent, List<OrganMergeSplitInfo> list,HashSet<String> result){
		//遍历总的源数据集
		for (OrganMergeSplitInfo item : list){
			if(parent.equals(item.getOrganizationInfo().getOrgCode())){//当传入的 key 与 元数据中的key对应，
				String next = item.getOrganCodes();//就将值取出作为下次递归的key 
				result.add(next);
				getAll(next,list,result);//用 这个值作为key 去总的源数据集里再次匹配 参数里传递的结果集为同一结果集至循环结束。
			}
		}
		return result;//返回本次循环对应的结果集。
	}
	/**
	 * 根据orgCode获取 并入的机构
	 * @param orgCode
	 * @return  查询出来的拼接字符串
	 */
	public static String getOrgAdjustCodesInSet(String orgCode) {
		if(orgAdjustMap.get(orgCode)!=null){
			String inSet ="in ('";
			String inSetCodes = orgAdjustMap.get(orgCode);
			inSet += inSetCodes.replace(",","','")+"')";
			return inSet;
		}else{
			return " = '"+orgCode+"' ";
		}
	}
	/**
	 * 根据orgCode获取 并入的机构
	 * @param orgCode
	 * @return  查询出来的拼接字符串
	 */
	public static String getOrgAdjustCodes(String orgCode) {
		if(orgAdjustMap.get(orgCode)!=null){
			return orgAdjustMap.get(orgCode);
		}else{
			return orgCode;
		}
	}
	/**
	 * 返回整个合并映射机构map
	 * @return  查询出来的拼接字符串
	 */
	public static Map<String,String> getOrgAdjustMap() {
		return orgAdjustMap;
	}
	/**
	 * 根据codeType返回代码列表
	 * @param codeType   编码类型
	 * @param exceptCode  需要过滤的编码
	 * @return
	 */
	public static List<SystemCodeNodeInfo> getCodeNodeList(String sysCodeType,String exceptCode) {
		if(sysCodeType==null||sysCodeType.equals(""))
			return null;
		List<SystemCodeNodeInfo> list = codeMapList.get(sysCodeType);
		if (exceptCode != null && !"".equals(exceptCode)) {
			for (Iterator<SystemCodeNodeInfo> iterator = list.iterator(); iterator.hasNext();) {
				SystemCodeNodeInfo systemCodeNodeInfo = iterator.next();
				if (exceptCode != null && exceptCode.contains(systemCodeNodeInfo.getCode())) {
					iterator.remove();
				}
			}
		}
		return list;
	}
	/**
	 * 根据codeType返回码表map 
	 * map.put(code, name);
	 * @param codeType
	 * @return
	 */
	public static Map<String,String> getCodeNodeMap(String sysCodeType) {
		if(sysCodeType==null||sysCodeType.equals(""))
			return null;
		Map<String,String> map=new TreeMap<String, String>();
		List<SystemCodeNodeInfo> list = getCodeNodeList(sysCodeType,null);
		for (SystemCodeNodeInfo node : list) {
			map.put(node.getCode(), node.getName());
		}
		return map;
	}
	/**
	 * 码表的相互转换，
	 * @param codeType   编码类型
	 * @param value  编码值或者名称
	 * @param code2name  ture表示code转换为name，否则为name转换为code
	 * @return
	 */
	public static String systemCodeSwitch(String sysCodeType,String value,boolean code2name) {
		if(StringUtils.isBlank(sysCodeType)||StringUtils.isBlank(value))
			return "";
		PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		sysCodeType = sysCodeType.toUpperCase();
		List<SystemCodeNodeInfo> list = getCodeNodeList(sysCodeType,null);
		for (SystemCodeNodeInfo node : list) {
			String orgCode = node.getOrgCode();
			if(code2name){
				if(node.getCode().equals(value)){
					if(StringUtils.isBlank(orgCode) || person.getRegionCode().equals(orgCode))
						return node.getName();
				}
			}else{
				if(node.getName().equals(value)){
					if(StringUtils.isBlank(orgCode) || person.getRegionCode().equals(orgCode))
						return node.getCode();
				}
			}
		}
		return "";
	}
	/**
	 * 统计局地区编码和信访系统编码之间的转换
	 * @param regionCode 统计局格式的地区编码，长度6位
	 * @return  信访系统地区编码 长度12位
	 */
	public static String regionCodeSwitch(String regionCode) {
		// 区域编码为null，""，长度不等于6
		if (regionCode == null || "".equals(regionCode.trim()) || regionCode.trim().length() != 6) {
			return "";
		}
		String province = regionCode.substring(0, 2);
		String city = regionCode.substring(2, 4);
		String county = regionCode.substring(4, 6);
		// 判断是否是直辖市，11北京，12天津，31上海，50重庆
		if (province.equals("11") || province.equals("12") || province.equals("31") || province.equals("50")) {
			regionCode = province + county + "00000000";
		} else {
			regionCode = regionCode + "000000";
		}
		return regionCode;
	}
	/**
	 * 判断是否为集中式部署
	 * @param regionCode
	 * @return
	 */
	public static boolean isCentralized(String regionCode) {
		return centralizedMap.containsKey(regionCode);
	}
	/**
	 * 获取集中式map
	 * regioncode：regionName
	 * @return
	 */
	public static Map<String, String> getCentralizedMap() {
		return centralizedMap;
	}
	
	public static Map<String, String> getLoginIpMap() {
		return loginIpMap;
	}
	/**
	 * 状态code和name的相互转换
	 * @param value  编码值或者名称
	 * @param code2name  ture表示code转换为name，否则为name转换为code
	 * @return
	 */
	public static String activitySwitch(String value,boolean code2name) {
		if(value==null||value.equals(""))
			return null;
		if(code2name){
			return activityMap.get(value);
		}else{
			for (String key : activityMap.keySet()) {
				if(value.equals(activityMap.get(key))){
					return key;
				}
			}
		}
		return null;
	}
	/**
	 * 根据key获取工作流List
	 * key = activityNo + conditionCode;
	 * @param key
	 * @return  查询出来的list
	 */
	/*public static List<PetitionWorkflowEngineInfo> getWorkflowListMap(String key) {
		return workflowListMap.get(key);
	}*/
	/**
	 * 根据regionCode判断查询权限
	 * @param criteria
	 * @param regionCode
	 * @return
	 */
//	public static Criteria selectByrRegionCode(Criteria criteria,String regionCode){
//		if (Constants.SH_REGION_CDOE.equals(regionCode)||"0000".equals(regionCode.substring(2, 6))) {
//			criteria.add(Restrictions.like("regionCode", regionCode.substring(0, 2), MatchMode.START));
//		}else if ("00".equals(regionCode.substring(4, 6))||"0230".equals(regionCode.substring(2, 6))) {
//			criteria.add(Restrictions.like("regionCode", regionCode.substring(0, 4), MatchMode.START));
//		}else{
//			criteria.add(Restrictions.eq("regionCode",regionCode));
//		}
//		return criteria;
//	}
	/**
	 * 根据regionCode判断查询权限
	 * @param stringBuffer 传入需要进行添加权限的sql
	 * @param regionCode 判断所属行政区划码
	 * @return
	 */
	public static StringBuffer selectByRegionCode(StringBuffer stringBuffer,String regionCode){
		if (Constants.SH_REGION_CDOE.equals(regionCode)||"0000".equals(regionCode.substring(2, 6))) {
			stringBuffer.append(" and region_code  like '" + regionCode.substring(0, 2) + "%'  ");
		}else if ("00".equals(regionCode.substring(4, 6))||"0230".equals(regionCode.substring(2, 6))) {
			stringBuffer.append(" and region_code  like '" + regionCode.substring(0, 4) + "%'  ");
		}else{
			stringBuffer.append(" and region_code  = '" + regionCode + "' ");
		}
		return stringBuffer;
	}
	/**
	 * 通过本级的regionCode获得上级的regionCode,如果没有上级机关返回null
	 * @param regionCode 本级regionCode
	 * @return 上级regionCode
	 */
	public static String getSuperRegionCode(String regionCode){
		String superRegionCode = null;
		if (Constants.SH_REGION_CDOE.equals(regionCode)) {
			superRegionCode="000000000000";
		}else if ("00".equals(regionCode.substring(4, 6))) {
			superRegionCode = Constants.SH_REGION_CDOE;
		}else{
			superRegionCode=regionCode.substring(0, 4)+"00000000";
		}
		return superRegionCode;
	}
	/**
	 * 级别之间的两系统编码转换
	 * @param 
	 * @return
	 */
	public static String accusedClassCodeSwitch(String s) {
		if ("40".equals(s))
			return "0200";
		else if ("50".equals(s))
			return "0300";
		else if ("60".equals(s))
			return "0300";
		else if ("70".equals(s))
			return "0400";
		else if ("80".equals(s))
			return "0400";
		else if ("90".equals(s))
			return "0500";
		else if ("100".equals(s))
			return "0500";
		else if ("110".equals(s))
			return "0600";
		else if ("120".equals(s))
			return "0600";
		else if ("130".equals(s))
			return "0905";
		else
			return "0905";
	}
//	public static String getClassCode(String name) {
//		String code = "";
//		if("省部级".equals(name)){
//			code="0200";
//		}else if("副部级".equals(name)){
//			code="0202";
//		}else if("地厅级".equals(name)){
//			code="0300";
//		}else if("副厅级".equals(name)){
//			code="0302";
//		}else if("县处级".equals(name)){
//			code="0400";
//		}else if("副处级".equals(name)){
//			code="0402";
//		}else if("乡科级".equals(name)){
//			code="0500";
//		}else if("副科级".equals(name)){
//			code="0502";
//		}else if("一般干部".equals(name)){
//			code="0600";
//		}else if("其他企业人员".equals(name)){
//			code="0903";
//		}else if("其他军队人员".equals(name)){
//			code="0901";
//		}else if("其他农村人员".equals(name)){
//			code="0904";
//		}else if("其他人员".equals(name)){
//			code="0905";
//		}
//		return code;
//	}
//	public static String getDisciplineCode(String name){
//		String code = "";
//		if("中共党员".equals(name)){
//			code="1";
//		}else if("中共预备党员".equals(name)){
//			code="2";
//		}else if("共青团员".equals(name)){
//			code="3";
//		}else if("民主党派人士".equals(name)){
//			code="4"; 
//		}else if("无党派民主人士".equals(name)){
//			code="5";
//		}else if("群众".equals(name)){
//			code="6";
//		}
//		return code;
//	}
//	public static String getWorkTypeCode(String name){
//		String code = "";
//		if("党政领导机关".equals(name)){
//			code="01";
//		}else if("司法机关".equals(name)){
//			code="02";
//		}else if("行政执法机关".equals(name)){
//			code="03";
//		}else if("经济管理部门".equals(name)){
//			code="04"; 
//		}else if("其它".equals(name)){
//			code="99";
//		}
//		return code;
//	}
//	private static String getPetitionerRegionCode(String name){
//		String code = "";
//		if("黄浦区".equals(name)){
//			code="310100000000";
//		}else if("崇明县".equals(name)){
//			code="313000000000";
//		}else if("徐汇区".equals(name)){
//			code="310400000000";
//		}else if("长宁区".equals(name)){
//			code="310500000000"; 
//		}else if("静安区".equals(name)){
//			code="310600000000";
//		}else if("普陀区".equals(name)){
//			code="310700000000";
//		}else if("闸北区".equals(name)){
//			code="310800000000";
//		}else if("虹口区".equals(name)){
//			code="310900000000";
//		}else if("杨浦区".equals(name)){
//			code="311000000000";
//		}else if("闵行区".equals(name)){
//			code="311200000000";
//		}else if("宝山区".equals(name)){
//			code="311300000000";
//		}else if("嘉定区".equals(name)){
//			code="311400000000";
//		}else if("浦东新区".equals(name)){
//			code="311500000000";
//		}else if("金山区".equals(name)){
//			code="311600000000";
//		}else if("松江区".equals(name)){
//			code="311700000000";
//		}else if("青浦区".equals(name)){
//			code="311800000000";
//		}else if("奉贤区".equals(name)){
//			code="312000000000";
//		}
//		return code;
//	}
//	public static String getFlagCode(String name){
//		String code = "";
//		if("是".equals(name)){
//			code="1";
//		}else if("否".equals(name)){
//			code="2";
//		}
//		return code;
//	}
	/**
	 * 根据regionCode判断查询权限
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param criteria
	 * @param regionCode
	 * @return
	 */
	public static Criteria selectByrRegionCode(Criteria criteria,String regionCode){
		if (Constants.SH_REGION_CDOE.equals(regionCode)||"0000".equals(regionCode.substring(2, 6))) {
			criteria.add(Restrictions.like("regionCode", regionCode.substring(0, 2), MatchMode.START));
		}else if ("00".equals(regionCode.substring(4, 6))||"0230".equals(regionCode.substring(2, 6))) {
			criteria.add(Restrictions.like("regionCode", regionCode.substring(0, 4), MatchMode.START));
		}else{
			criteria.add(Restrictions.eq("regionCode",regionCode));
		}
		return criteria;
	}
}
