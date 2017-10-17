package com.sinosoft.xf.query.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sinosoft.xf.petition.accept.domain.PetitionAccusedInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccuserInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionIssueInfo;
import com.sinosoft.xf.query.domain.SubPetitionBasicInfo;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;

/**
 * 生成Ext前台json数据
 * @author hjh
 *
 * 2015-3-2下午04:42:28
 */
public class ExtJsonUtil {
	/**
	 * list转换为前台json
	 * @author hjh
	 * @param 
	 * @return
	 * 2014-8-1上午11:05:57
	 */
	public static String list2Json(List<SubPetitionBasicInfo> list, int limit, int totalCount){
		StringBuffer json = new StringBuffer();
		String  jsonStr = "";
		if (list != null && !list.isEmpty()) {
			int totalPage = (totalCount % limit == 0) ? (totalCount / limit) : totalCount / limit + 1;//总页数
			json.append("{'totalProperty':");
			json.append(totalCount);
			json.append(",'totalPage':").append(totalPage);
			json.append(",'pageSize':").append(limit);
			json.append(",'result':[");
			json.append(list2ResultChart(list));
			json.append("]}");
			jsonStr = StringUtils.deleteNewLine(json.toString());
//			jsonStr = json.toString().replace("\n", " ");
		}
		return jsonStr;
	}
	
	/**
	 * 把list转换为result属性中的json串(只用于报表钻取)
	 * @author hjh
	 * @param list
	 * @return
	 * 2015-3-2下午04:46:47
	 */
	public static String list2ResultChart(List<SubPetitionBasicInfo> list){
		StringBuffer json = new StringBuffer("");
		// 循环List对象，封装查询结果信息
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SubPetitionBasicInfo petitionBasicInfo = list.get(i);
				Set<PetitionAccuserInfo> accuserInfoSet = list.get(i).getAccuserInfoSet();
				Set<PetitionAccusedInfo> accusedInfoSet = list.get(i).getAccusedInfoSet();
				PetitionIssueInfo petiIssueInfo = list.get(i).getIssueInfoSet().iterator().next();
				PetitionCirculationStateInfo circulationStateInfo = list.get(i).getCirculationStateInfo();
				PetitionAccuserInfo accuserInfo = new PetitionAccuserInfo();
				//2015-10-21 by 前台钻取刷新列表每次只取第一反映人或第一被反映人by zhouyic update
				if(accuserInfoSet!=null){
					Iterator<PetitionAccuserInfo> accuserIt = accuserInfoSet.iterator();
					while(accuserIt.hasNext()){
						accuserInfo = accuserIt.next();
						//如果为第一反映人
						if(accuserInfo.getAccuserNum() == 1){
							break;
						}
					}
				}
				PetitionAccusedInfo accusedInfo = new PetitionAccusedInfo();
				if(accusedInfoSet!=null){
					Iterator<PetitionAccusedInfo> accusedIt = accusedInfoSet.iterator();
					while(accusedIt.hasNext()){
						accusedInfo = accusedIt.next();
						//如果为第一被反映人
						if(accusedInfo.getAccusedNum() == 1){
							break;
						}
					}
				}
				// 获取对象数据
				if (i > 0) {
					json.append(",");
				}
				json.append("{id:'");
				json.append(petitionBasicInfo.getId());
				// 信访编号
				json.append("',curr_petition_no:'");
				json.append(petitionBasicInfo.getCurrPetitionNo());
				// 信访日期
				json.append("',petition_date:'");
				json.append(petitionBasicInfo.getPetitionDate().toString().substring(0, 10));
				json.append("',PETITIONNO:'");
				json.append(petitionBasicInfo.getPetitionNo());
				json.append("',PETITIONPRTNO:'");
				json.append(petitionBasicInfo.getPetitionPrtNo());
				json.append("',REGIONCODE:'");
				json.append(petitionBasicInfo.getRegionCode());
				json.append("',IMPORTANTFLAG:'");
				json.append(petitionBasicInfo.getImportantFlag());
				// 信访方式
				json.append("',petition_type_name:'");
				json.append(petitionBasicInfo.getPetitionTypeName());
				// 信访来源
				json.append("',petition_source_name:'");
				json.append(petitionBasicInfo.getPetitionSourceName());
				// 被反映人姓名
				json.append("',accused_name:'");
				if(accusedInfo.getAccusedName()==null||"null".equals(accusedInfo.getAccusedName())){
					json.append("");
				}else{
					json.append(accusedInfo.getAccusedName());
				}
				// 被反映人单位职务
				json.append("',accused_work_unit:'");
				if(accusedInfo.getAccusedWorkUnit() == null||"null".equals(accusedInfo.getAccusedWorkUnit())){
					json.append("");
				}else{
					json.append(accusedInfo.getAccusedWorkUnit());
					if(StringUtils.isNotBlank(accusedInfo.getAccusedPositionName()))
						json.append("【"+accusedInfo.getAccusedPositionName()+"】");
				}
				// 反映人姓名
				json.append("',accuser_name:'");
				if(accuserInfo.getAccuserName() ==null||"null".equals(accuserInfo.getAccuserName())){
					json.append("");
				}else{
					json.append(accuserInfo.getAccuserName());
				}
				// 问题描述
				json.append("',issue_content:'");
				if(petiIssueInfo.getIssueContent() == null||"null".equals(petiIssueInfo.getIssueContent()))
					json.append("");
				else
					json.append(petiIssueInfo.getIssueContent());
				// 承办人
				json.append("',default_dealer_name:'");
				if(circulationStateInfo!=null && StringUtils.isNotBlank(circulationStateInfo.getDefaultDealerName()))
					json.append(circulationStateInfo.getDefaultDealerName());
				json.append("'}");
			}
		}
		return json.toString();
	}
	/**
	 * 把list转换为result属性中的json串
	 * @author hjh
	 * @param list
	 * @return
	 * 2015-3-2下午04:46:47
	 */
	public static String list2Result(List<PetitionBasicInfo> list){
		StringBuffer json = new StringBuffer("");
		// 循环List对象，封装查询结果信息
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PetitionBasicInfo petitionBasicInfo = list.get(i);
				Set<PetitionAccuserInfo> accuserInfoSet = list.get(i).getAccuserInfoSet();
				Set<PetitionAccusedInfo> accusedInfoSet = list.get(i).getAccusedInfoSet();
				PetitionIssueInfo petiIssueInfo = list.get(i).getIssueInfoSet().iterator().next();
				PetitionCirculationStateInfo circulationStateInfo = list.get(i).getCirculationStateInfo();
				PetitionAccuserInfo accuserInfo = new PetitionAccuserInfo();
				if(accuserInfoSet != null && accuserInfoSet.iterator().hasNext()){
					while(accuserInfoSet.iterator().next().getAccuserNum() == 1){
						accuserInfo = accuserInfoSet.iterator().next();
						break;
					}
				}
				PetitionAccusedInfo accusedInfo = new PetitionAccusedInfo();
				if(accusedInfoSet != null && accusedInfoSet.iterator().hasNext()){
					while(accusedInfoSet.iterator().next().getAccusedNum() == 1){
						accusedInfo = accusedInfoSet.iterator().next();
						break;
					}
				}
				// 获取对象数据
				if (i > 0) {
					json.append(",");
				}
				json.append("{id:'");
				json.append(petitionBasicInfo.getId());
				// 信访编号
				json.append("',curr_petition_no:'");
				json.append(petitionBasicInfo.getCurrPetitionNo());
				// 信访日期
				json.append("',petition_date:'");
				json.append(petitionBasicInfo.getPetitionDate().toString().substring(0, 10));
				json.append("',PETITIONNO:'");
				json.append(petitionBasicInfo.getPetitionNo());
				json.append("',PETITIONPRTNO:'");
				json.append(petitionBasicInfo.getPetitionPrtNo());
				json.append("',REGIONCODE:'");
				json.append(petitionBasicInfo.getRegionCode());
				json.append("',IMPORTANTFLAG:'");
				json.append(petitionBasicInfo.getImportantFlag());
				// 信访方式
				json.append("',petition_type_name:'");
				json.append(petitionBasicInfo.getPetitionTypeName());
				// 信访来源
				json.append("',petition_source_name:'");
				json.append(petitionBasicInfo.getPetitionSourceName());
				// 被反映人姓名
				json.append("',accused_name:'");
				if(accusedInfo.getAccusedName()==null||"null".equals(accusedInfo.getAccusedName())){
					json.append("");
				}else{
					json.append(accusedInfo.getAccusedName());
				}
				// 被反映人单位职务
				json.append("',accused_work_unit:'");
				if(accusedInfo.getAccusedWorkUnit() == null||"null".equals(accusedInfo.getAccusedWorkUnit())){
					json.append("");
				}else{
					json.append(accusedInfo.getAccusedWorkUnit());
				}
				// 反映人姓名
				json.append("',accuser_name:'");
				if(accuserInfo.getAccuserName() ==null||"null".equals(accuserInfo.getAccuserName())){
					json.append("");
				}else{
					json.append(accuserInfo.getAccuserName());
				}
				// 问题描述
				json.append("',issue_content:'");
				if(petiIssueInfo.getIssueContent() == null||"null".equals(petiIssueInfo.getIssueContent())){
					json.append("");
				}
				// 承办人
				json.append("',default_dealer_name:'");
				if(circulationStateInfo!=null && StringUtils.isNotBlank(circulationStateInfo.getDefaultDealerName()))
					json.append(circulationStateInfo.getDefaultDealerName());
				json.append("'}");
			}
		}
		return json.toString();
	}
}
