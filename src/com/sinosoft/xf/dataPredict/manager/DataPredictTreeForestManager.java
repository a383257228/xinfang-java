package com.sinosoft.xf.dataPredict.manager;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.dataPredict.dao.DataPredictTreeForestDao;
import com.sinosoft.xf.dataPredict.domain.DataPredictTreeForest;
import com.sinosoft.xf.dataPredict.domainutil.RegionNameCount;
import com.sinosoft.xf.petition.accept.dao.PetitionBasicInfoDao;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.petition.domainutil.PetitionInfo;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;

/**
 * 信访件分布  manager
 * @date 2017-08-12
 * @author liyang
 */
@Service
@Transactional
public class DataPredictTreeForestManager extends EntityManager<DataPredictTreeForest, String> {
	@Autowired
	DataPredictTreeForestDao treeForestDao;
	@Autowired
	PetitionBasicInfoDao issueInfoDao;
	@Autowired
	PetitionBasicInfoManager basicInfoManager;
	@Override
	protected HibernateDao<DataPredictTreeForest, String> getEntityDao() {
		return treeForestDao;
	}
	
	public String queryTreeForest(String keyWords) throws JsonGenerationException, JsonMappingException, IOException{		
		List<Map<String, Object>> list = treeForestDao.queryTreeForest(keyWords);
		return new ObjectMapperWrap().writeValueAsString(list);
	}
	//模糊匹配返回人名
	public ExtjsPage<Map<String,Object>> dataPredictGetAccused(Map<String, Object> map){
		//System.out.println("???");
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = treeForestDao.dataPredictFindAccused(map);
		page.setResult(list);
		return page;
	}
	//find accused nexus
	public ExtjsPage<Map<String,Object>> dataPredictGetNexus2(Map<String, Object> map){
		boolean nexus = Integer.parseInt((String) map.get("nexus"))==1;
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(nexus){
			list = treeForestDao.dptfAccurateDoubleBrief2(map);
		}else{			
			list = treeForestDao.dptfAccurateSingleDetail2(map);
		}
		page.setResult(list);
		return page;
	}
	public ExtjsPage<Map<String,Object>> dataPredictHahaTest3(Map<String, Object> map){
		boolean nexus = Integer.parseInt((String) map.get("nexus"))==1;
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(nexus){
			list = treeForestDao.dptfAccurateDoubleBrief2(map);
		}else{			
			list = treeForestDao.dptfAccurateSingleDetail2(map);
		}
		page.setResult(list);
		return page;
	}
	public String queryIssueTypeTop5(Map<String, Object> map,PersonInfo person){
		List<Map<String, Object>> list = treeForestDao.queryIssueTypeTop5(map,person);
		StringBuffer sb = new StringBuffer("[");
		for(int i=0;i<list.size();i++){
			sb.append("{\"name\":\"").append(list.get(i).get("typeName")).append("\",\"count\":").append(list.get(i).get("count")).append(",\"issueTypeCode\":\"").append(list.get(i).get("issueTypeCode")).append("\"}");
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	public ExtjsPage<RegionNameCount> drillingTopCount(Map<String, Object> map,PersonInfo person) throws ParseException{
		ExtjsPage<RegionNameCount> page = new ExtjsPage<RegionNameCount>();
		List<RegionNameCount> list = new ArrayList<RegionNameCount>();
		
		List<Map<String, Object>> zsNum = treeForestDao.drillingTopCount(map,1,person);//自收
		for(int i=0;i<zsNum.size();i++){
			Map<String, Object> zsmap = zsNum.get(i);
			RegionNameCount regionNameCount = new RegionNameCount();
			regionNameCount.setZsCount(Integer.parseInt(zsmap.get("count").toString()));
			regionNameCount.setIssueRegionCode(zsmap.get("regionCode").toString());
			list.add(regionNameCount);
		}
		
		List<Map<String, Object>> sjNum = treeForestDao.drillingTopCount(map,2,person);//上级转交办
		int sjFlag=0;
		for(int i=0;i<sjNum.size();i++){
			Map<String, Object> sjmap = sjNum.get(i);
			for(int j=0;j<list.size();j++){
				if(list.get(j).getIssueRegionCode().equals(sjmap.get("regionCode").toString())){
					list.get(j).setSjCount(Integer.parseInt(sjmap.get("count").toString()));
					list.get(j).setIssueRegionCode(sjmap.get("regionCode").toString());
					sjFlag++;
					break;
				}
			}
			if(sjFlag==0){
				RegionNameCount regionNameCount = new RegionNameCount();
				regionNameCount.setSjCount(Integer.parseInt(sjmap.get("count").toString()));
				regionNameCount.setIssueRegionCode(sjmap.get("regionCode").toString());
				list.add(regionNameCount);
				sjFlag=0;
			}
		}
		int xjFLag=0;
		List<Map<String, Object>> xjNum = treeForestDao.drillingTopCount(map,3,person);//下级转交办
		for(int i=0;i<xjNum.size();i++){
			Map<String, Object> xjmap = xjNum.get(i);
			for(int j=0;j<list.size();j++){
				if(list.get(j).getIssueRegionCode().equals(xjmap.get("regionCode").toString())){
					list.get(j).setXjCount(Integer.parseInt(xjmap.get("count").toString()));
					list.get(j).setIssueRegionCode(xjmap.get("regionCode").toString());
					xjFLag++;
					break;
				}
			}
			if(xjFLag==0){
				RegionNameCount regionNameCount = new RegionNameCount();
				regionNameCount.setXjCount(Integer.parseInt(xjmap.get("count").toString()));
				regionNameCount.setIssueRegionCode(xjmap.get("regionCode").toString());
				list.add(regionNameCount);
				xjFLag=0;
			}
		}
		
		for(int i=0;i<list.size();i++){
			String[] code = Constants.countyRegionCode;
			for(int j=0;j<code.length;j++){
				String issueCode = list.get(i).getIssueRegionCode()+"00000000";
				if(issueCode.equals(code[j])){
					list.get(i).setIssueRegionName(Constants.countyRegionName[j]);
					break;
				}
			}
			String[] code1 = Constants.dispatchRegionCode;
			for(int j=0;j<code1.length;j++){
				String issueCode = list.get(i).getIssueRegionCode()+"00000000";
				if(issueCode.equals(code1[j])){
					list.get(i).setIssueRegionName(Constants.dispatchRegionName[j]);
					break;
				}
			}
		}
		page.setTotalCount(list.size());
		page.setResult(list);
		return page;
	}
	/**
	 * 信访件分布情况本委查询
	 * @date 2017-08-25
	 * @author guoh
	 * @param filterValue
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ExtjsPage<PetitionInfo> getLetterInfo(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception {
		int size = treeForestDao.getLetterInfoSize(map,person);
		List<Map<String, Object>> lists = treeForestDao.getLetterInfoList(map,start,limit,person);
		return basicInfoManager.getPetitionInfo(size,lists);
	}		
}