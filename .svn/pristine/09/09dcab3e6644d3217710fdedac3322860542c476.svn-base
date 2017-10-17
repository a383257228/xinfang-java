package com.sinosoft.xf.petition.accept.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.petition.accept.dao.PetitionIssueInfoDao;
import com.sinosoft.xf.petition.accept.domain.PetitionIssueInfo;
import com.sinosoft.xf.petition.domainutil.RegionNameCount;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;

/**
 * 问题类别manager
 * @date 2017-06-06
 * @author guoh
 */
@Service
@Transactional
public class PetitionIssueInfoManager extends EntityManager<PetitionIssueInfo, String> {
	@Autowired
	PetitionIssueInfoDao issueInfoDao;
	@Autowired
	PetitionBasicInfoManager petitionBasicInfoManager;

	@Override
	protected HibernateDao<PetitionIssueInfo, String> getEntityDao() {
		return issueInfoDao;
	}

	/**
	 * 问题类别数量前五位
	 * @author guoh
	 * @date 2017-07-05
	 * @param map
	 * @param person
	 * @return
	 */
	public String queryIssueTypeTop5(Map<String, Object> map,PersonInfo person){
		List<Map<String, Object>> list = issueInfoDao.queryIssueTypeTop5(map,person);
		//20170925 byg 对结果集中旧的问题性质进行替换，将旧的count值累加至对应的新问题性质count中
		for (Map<String, Object> rawMap : list) {
			rawMap.put("issueTypeCode", petitionBasicInfoManager.oldIssueCodeToNewcode(rawMap.get("issueTypeCode").toString()));
		}
		//20170926 byg 修改去重方式，用于 list 排序获取 前5名
		List<Map<String, Object>> noRepeatList = new ArrayList<Map<String, Object>>();
		Set<String> route = new HashSet<String>();
		for (Map<String, Object> item : list) {
			if(route.add(item.get("issueTypeCode").toString())){
				item.put("typeName",CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),item.get("issueTypeCode").toString(),true));
				noRepeatList.add(item);
			}else{
				for (Map<String, Object> real : noRepeatList) {
					if(item.get("issueTypeCode").equals(real.get("issueTypeCode"))){
						real.put("count", Integer.parseInt(item.get("count").toString()) + Integer.parseInt(real.get("count").toString()));
					}
				}
			}
		}
		Collections.sort(noRepeatList, new Comparator<Map<String,Object>>() {  
            @Override  
            public int compare(Map<String,Object> o1, Map<String,Object> o2) { 
            	int vs = Integer.parseInt(o2.get("count").toString()) - Integer.parseInt(o1.get("count").toString());
                return vs;  
            }  
        });
		StringBuffer sb = new StringBuffer("[");
		int size = 5;
		if(noRepeatList.size()<5){
			size = noRepeatList.size();
		}
		for(int i=0;i<size;i++){
			sb.append("{\"name\":\"").append(list.get(i).get("typeName")).append("\",\"count\":").append(list.get(i).get("count")).append(",\"issueTypeCode\":\"").append(list.get(i).get("issueTypeCode")).append("\"}");
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 问题类别数量前五位
	 * 钻取查询问题属地以及自收件、上级转交办、下级转交办的数量
	 * @author guoh
	 * @date 2017-08-30
	 * @param map
	 * @param person
	 * @return
	 */
	public ExtjsPage<RegionNameCount> drillingTopCount(Map<String, Object> map,PersonInfo person){
		ExtjsPage<RegionNameCount> page = new ExtjsPage<RegionNameCount>();
		List<RegionNameCount> list = new ArrayList<RegionNameCount>();
		
		List<Map<String, Object>> zsNum = issueInfoDao.drillingTopCount(map,1,person);//自收
		for(int i=0;i<zsNum.size();i++){
			Map<String, Object> zsmap = zsNum.get(i);
			RegionNameCount regionNameCount = new RegionNameCount();
			regionNameCount.setZsCount(Integer.parseInt(zsmap.get("count").toString()));
			regionNameCount.setIssueRegionCode(zsmap.get("regionCode").toString());
			list.add(regionNameCount);
		}
		
		List<Map<String, Object>> sjNum = issueInfoDao.drillingTopCount(map,2,person);//上级转交办
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
		List<Map<String, Object>> xjNum = issueInfoDao.drillingTopCount(map,3,person);//下级转交办
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
	 * 问题类别变化情况-增、减量主查询
	 * @author guoh
	 * @date 2017-07-05
	 * @param map
	 * @param person
	 * @return
	 */
	public String queryZJIssueTypeTop5(Map<String, Object> map,PersonInfo person){
		List<Map<String, Object>> bigList = issueInfoDao.queryZJIssueTypeTop5(map,"big",person);
		List<Map<String, Object>> smallList = issueInfoDao.queryZJIssueTypeTop5(map,"small",person);
		
		//把List<Map<String, Object>>转换成map
		Map<String,Integer> bMap = new HashMap<String, Integer>();
	    for(int i=0;i<bigList.size();i++){
	    	if(null!=bigList.get(i).get("issueTypeCode")){
	    		String name = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),bigList.get(i).get("issueTypeCode").toString(),true);
	    		bMap.put(name, Integer.parseInt(bigList.get(i).get("count").toString()));
	    	}
	    }
	    Map<String,Integer> sMap = new HashMap<String, Integer>();
	    for(int i=0;i<smallList.size();i++){
	    	if(null!=smallList.get(i).get("issueTypeCode")){
	    		String name = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),smallList.get(i).get("issueTypeCode").toString(),true);
	    		sMap.put(name, Integer.parseInt(smallList.get(i).get("count").toString()));
	    	}
	    }
		
	    Map<String, Integer> zl = new HashMap<String, Integer>();
	    Map<String, Integer> jl = new HashMap<String, Integer>();
	    for (String b : bMap.keySet()) {
    		if(sMap.containsKey(b)){
    			int bNum = bMap.get(b);
    			int sNum = sMap.get(b);
    			if(bNum>sNum){//说明是增量
    				zl.put(b, bNum-sNum);
    			}
    			if(bNum<sNum){//说明是减量
    				jl.put(b, sNum-bNum);
    			}
    		}
    		if(!sMap.containsKey(b)){
    			zl.put(b, bMap.get(b));
    		}
	    }
	    for (String s : sMap.keySet()) {
	    	if(!bMap.containsKey(s)){
	    		jl.put(s, sMap.get(s));
	    	}
	    }
	    
	    
	    ArrayList<Entry<String, Integer>> arrayListZ = new ArrayList<Entry<String, Integer>>(zl.entrySet());  
		Collections.sort(arrayListZ, new Comparator<Map.Entry<String, Integer>>() {  
		    public int compare(Map.Entry<String, Integer> map1,Map.Entry<String, Integer> map2) {  
		        return (map2.getValue() - map1.getValue());  
		    }  
		});
		StringBuffer sbBig = new StringBuffer("[");
		int iFlag=0;
		for (Entry<String, Integer> entry : arrayListZ) {
			sbBig.append("{\"name\":\"").append(entry.getKey()).append("\",\"value\":").append(entry.getValue()).append("}");
			iFlag++;
			if(iFlag==5){
				break;
			}
			if(iFlag<5){
				sbBig.append(",");
			}
		}
		sbBig.append("],");
		
	    
		ArrayList<Entry<String, Integer>> arrayListJ = new ArrayList<Entry<String, Integer>>(jl.entrySet());  
		Collections.sort(arrayListJ, new Comparator<Map.Entry<String, Integer>>() {  
		    public int compare(Map.Entry<String, Integer> map1,Map.Entry<String, Integer> map2) {  
		        return (map2.getValue() - map1.getValue());  
		    }  
		});
		StringBuffer sbSmall = new StringBuffer("[");
		int iiFlag=0;
		for (Entry<String, Integer> entry : arrayListJ) {
			sbSmall.append("{\"name\":\"").append(entry.getKey()).append("\",\"value\":").append(entry.getValue()).append("}");
			iiFlag++;
			if(iiFlag==5){
				break;
			}
			if(iiFlag<5){
				sbSmall.append(",");
			}
		}
		sbSmall.append("]");
	    
		return sbBig.toString()+sbSmall.toString();
	}
	/**
	  * 问题类别分布情况-查询违法、违纪大类数量
	 * @author guoh
	 * @date 2017-08-18
	 * @param map
	 * @param person
	 * @return
	 */
	public String problemDistribution(Map<String, Object> map ,PersonInfo person){
		List<Map<String, Object>> wflist = issueInfoDao.problemDistributionWF(map,person,"wf");
		//20170925 byg 对结果集中被合并的机构码进行替换，将被合并的机构的count值累加至对应的并入的机构count中
		for (Map<String, Object> rawMap : wflist) {
			rawMap.put("code", petitionBasicInfoManager.oldIssueCodeToNewcode(rawMap.get("code").toString()));
		}
		//使用map进行去重与累加操作。
		Map<String, Map<String, Object>> wfTargetmMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> rawMap : wflist) {
			rawMap.put("name",CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),rawMap.get("code").toString(),true));
			if(wfTargetmMap.containsKey(rawMap.get("code"))){
				Map<String, Object> item = wfTargetmMap.get(rawMap.get("code").toString());
				item.put("count", Integer.parseInt(item.get("count").toString()) + Integer.parseInt(rawMap.get("count").toString()));
				wfTargetmMap.put(rawMap.get("code").toString(), item);
			}else{
				wfTargetmMap.put(rawMap.get("code").toString(), rawMap);
			}
		}
		Collection<Map<String, Object>> wfNewList =wfTargetmMap.values();
		StringBuffer wfsb = new StringBuffer("[");
		if(wfNewList.size()>0){
			int count1=0;
			int count2=0;
			int count3=0;
			int count4=0;
			int count5=0;
			int count6=0;
			for (Map<String, Object> item : wfNewList) {
				if("0131".equals(item.get("code").toString())){
					count1 = Integer.parseInt(item.get("count").toString());
				}else if("0132".equals(item.get("code").toString())){
					count2 = Integer.parseInt(item.get("count").toString());
				}else if("0133".equals(item.get("code").toString())){
					count3 = Integer.parseInt(item.get("count").toString());
				}else if("0134".equals(item.get("code").toString())){
					count4 = Integer.parseInt(item.get("count").toString());
				}else if("0135".equals(item.get("code").toString())){
					count5 = Integer.parseInt(item.get("count").toString());
				}else if("0136".equals(item.get("code").toString())){
					count6 = Integer.parseInt(item.get("count").toString());
				}
			}
			wfsb.append("{\"code\":\"0136\",\"name\":\"其他涉法行为\",\"count\":").append(count6).append("},");
			wfsb.append("{\"code\":\"0135\",\"name\":\"妨害社会管理秩序行为\",\"count\":").append(count5).append("},");
			wfsb.append("{\"code\":\"0134\",\"name\":\"侵犯人身权利民主权利行为\",\"count\":").append(count4).append("},");
			wfsb.append("{\"code\":\"0133\",\"name\":\"破坏社会主义市场经济秩序行为\",\"count\":").append(count3).append("},");
			wfsb.append("{\"code\":\"0132\",\"name\":\"失职渎职行为\",\"count\":").append(count2).append("},");
			wfsb.append("{\"code\":\"0131\",\"name\":\"贪污贿赂行为\",\"count\":").append(count1).append("}");
		}else{
			wfsb.append("{\"code\":\"0136\",\"name\":\"其他涉法行为\",\"count\":0},");
			wfsb.append("{\"code\":\"0135\",\"name\":\"妨害社会管理秩序行为\",\"count\":0},");
			wfsb.append("{\"code\":\"0134\",\"name\":\"侵犯人身权利民主权利行为\",\"count\":0},");
			wfsb.append("{\"code\":\"0133\",\"name\":\"破坏社会主义市场经济秩序行为\",\"count\":0},");
			wfsb.append("{\"code\":\"0132\",\"name\":\"失职渎职行为\",\"count\":0},");
			wfsb.append("{\"code\":\"0131\",\"name\":\"贪污贿赂行为\",\"count\":0}");
		}
		wfsb.append("],");
		
		List<Map<String, Object>> wjlist = issueInfoDao.problemDistributionWF(map,person,"wj");
		//20170925 byg 对结果集中被合并的机构码进行替换，将被合并的机构的count值累加至对应的并入的机构count中
		for (Map<String, Object> rawMap : wjlist) {
			rawMap.put("code", petitionBasicInfoManager.oldIssueCodeToNewcode(rawMap.get("code").toString()));
		}
		//使用map进行去重与累加操作。
		Map<String, Map<String, Object>> wjTargetmMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> rawMap : wjlist) {
			rawMap.put("name",CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),rawMap.get("code").toString(),true));
			if(wjTargetmMap.containsKey(rawMap.get("code"))){
				Map<String, Object> item = wjTargetmMap.get(rawMap.get("code").toString());
				item.put("count", Integer.parseInt(item.get("count").toString()) + Integer.parseInt(rawMap.get("count").toString()));
				wjTargetmMap.put(rawMap.get("code").toString(), item);
			}else{
				wjTargetmMap.put(rawMap.get("code").toString(), rawMap);
			}
		}
		Collection<Map<String, Object>> wjNewList =wjTargetmMap.values();
		StringBuffer wjsb = new StringBuffer("[");
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		if(wjNewList.size()>0){
			for (Map<String, Object> item : wjNewList) {
				if("0121".equals(item.get("code").toString().substring(0, 4))){
					count1 += Integer.parseInt(item.get("count").toString());
				}else if("0122".equals(item.get("code").toString().substring(0, 4))){
					count2 += Integer.parseInt(item.get("count").toString());
				}else if("0123".equals(item.get("code").toString().substring(0, 4))){
					count3 += Integer.parseInt(item.get("count").toString());
				}else if("0124".equals(item.get("code").toString().substring(0, 4))){
					count4 += Integer.parseInt(item.get("count").toString());
				}else if("0125".equals(item.get("code").toString().substring(0, 4))){
					count5 += Integer.parseInt(item.get("count").toString());
				}else if("0126".equals(item.get("code").toString().substring(0, 4))){
					count6 += Integer.parseInt(item.get("count").toString());
				}
			}
			wjsb.append("{\"code\":\"0126\",\"name\":\"违反生活纪律行为\",\"count\":").append(-count6).append("},");
			wjsb.append("{\"code\":\"0125\",\"name\":\"违反工作纪律行为\",\"count\":").append(-count5).append("},");
			wjsb.append("{\"code\":\"0124\",\"name\":\"违反群众纪律行为\",\"count\":").append(-count4).append("},");
			wjsb.append("{\"code\":\"0123\",\"name\":\"违反廉洁纪律行为\",\"count\":").append(-count3).append("},");
			wjsb.append("{\"code\":\"0122\",\"name\":\"违反组织纪律行为\",\"count\":").append(-count2).append("},");
			wjsb.append("{\"code\":\"0121\",\"name\":\"违反政治纪律行为\",\"count\":").append(-count1).append("}");
		}else{
			wjsb.append("{\"code\":\"0126\",\"name\":\"违反生活纪律行为\",\"count\":0},");
			wjsb.append("{\"code\":\"0125\",\"name\":\"违反工作纪律行为\",\"count\":0},");
			wjsb.append("{\"code\":\"0124\",\"name\":\"违反群众纪律行为\",\"count\":0},");
			wjsb.append("{\"code\":\"0123\",\"name\":\"违反廉洁纪律行为\",\"count\":0},");
			wjsb.append("{\"code\":\"0122\",\"name\":\"违反组织纪律行为\",\"count\":0},");
			wjsb.append("{\"code\":\"0121\",\"name\":\"违反政治纪律行为\",\"count\":0}");
		}
		wjsb.append("]");
		return wfsb.toString()+wjsb.toString();
	}
	/**
	 * 信访件分布情况-点击玫瑰图查询指定地区信访类别分布情况
	 * @author guoh
	 * @date 2017-08-24
	 * @param map
	 * @param person
	 * @return
	 */
	public String petitionTypeAndIssueType(Map<String, Object> map,PersonInfo person){
		String code = map.get("orgCode").toString().substring(0, 4);
		//自收的数量
		int zsNum = issueInfoDao.count(1,map,code,person);
		//上级转交办数量
		//int szjbNum = issueInfoDao.count(2,map,code,person);
		//下级转交办
		int xzjbNum = issueInfoDao.count(3,map,code,person);
		//return "["+zsNum+","+szjbNum+","+xzjbNum+"]";
		return "["+zsNum+","+xzjbNum+"]";
	}
	/**
	 * 信访件分布情况-点击玫瑰图查询指定地区问题类别分布情况
	 * @author guoh
	 * @date 2017-08-24
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public String issueTypeCount(Map<String, Object> map,PersonInfo person) throws ParseException{
		List<Map<String, Object>> list = issueInfoDao.issueTypeCount(1,map,person);
		StringBuffer sb = new StringBuffer("[");
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		int count7 = 0;
		int count8 = 0;
		int count9 = 0;
		int count10 = 0;
		int count11 = 0;
		int count12 = 0;
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(null!=list.get(i).get("issueTypeCode")){
					if("0121".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count1 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0122".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count2 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0123".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count3 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0124".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count4 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0125".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count5 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0126".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count6 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0131".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count7 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0132".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count8 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0133".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count9 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0134".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count10 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0135".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count11 += Integer.parseInt(list.get(i).get("count").toString());
					}else if("0136".equals(list.get(i).get("issueTypeCode").toString().substring(0, 4))){
						count12 += Integer.parseInt(list.get(i).get("count").toString());
					}
				}
			}
			sb.append("{\"code\":\"0121\",\"name\":\"违反政治纪律行为\",\"count\":").append(count1).append("},");
			sb.append("{\"code\":\"0122\",\"name\":\"违反组织纪律行为\",\"count\":").append(count2).append("},");
			sb.append("{\"code\":\"0123\",\"name\":\"违反廉洁纪律行为\",\"count\":").append(count3).append("},");
			sb.append("{\"code\":\"0124\",\"name\":\"违反群众纪律行为\",\"count\":").append(count4).append("},");
			sb.append("{\"code\":\"0125\",\"name\":\"违反工作纪律行为\",\"count\":").append(count5).append("},");
			sb.append("{\"code\":\"0126\",\"name\":\"违反生活纪律行为\",\"count\":").append(count6).append("},");
			
			sb.append("{\"code\":\"0131\",\"name\":\"贪污贿赂行为\",\"count\":").append(count7).append("},");
			sb.append("{\"code\":\"0132\",\"name\":\"失职渎职行为\",\"count\":").append(count8).append("},");
			sb.append("{\"code\":\"0133\",\"name\":\"破坏社会主义市场经济秩序行为\",\"count\":").append(count9).append("},");
			sb.append("{\"code\":\"0134\",\"name\":\"侵犯人身权利民主权利行为\",\"count\":").append(count10).append("},");
			sb.append("{\"code\":\"0135\",\"name\":\"妨害社会管理秩序行为\",\"count\":").append(count11).append("},");
			sb.append("{\"code\":\"0136\",\"name\":\"其他涉法行为\",\"count\":").append(count12).append("}]");
		}else{
			sb.append("{\"code\":\"0121\",\"name\":\"违反政治纪律行为\",\"count\":0},");
			sb.append("{\"code\":\"0122\",\"name\":\"违反组织纪律行为\",\"count\":0},");
			sb.append("{\"code\":\"0123\",\"name\":\"违反廉洁纪律行为\",\"count\":0},");
			sb.append("{\"code\":\"0124\",\"name\":\"违反群众纪律行为\",\"count\":0},");
			sb.append("{\"code\":\"0125\",\"name\":\"违反工作纪律行为\",\"count\":0},");
			sb.append("{\"code\":\"0126\",\"name\":\"违反生活纪律行为\",\"count\":0},");
			
			sb.append("{\"code\":\"0131\",\"name\":\"贪污贿赂行为\",\"count\":0},");
			sb.append("{\"code\":\"0132\",\"name\":\"失职渎职行为\",\"count\":0},");
			sb.append("{\"code\":\"0133\",\"name\":\"破坏社会主义市场经济秩序行为\",\"count\":0},");
			sb.append("{\"code\":\"0134\",\"name\":\"侵犯人身权利民主权利行为\",\"count\":0},");
			sb.append("{\"code\":\"0135\",\"name\":\"妨害社会管理秩序行为\",\"count\":0},");
			sb.append("{\"code\":\"0136\",\"name\":\"其他涉法行为\",\"count\":0}]");
		}
		return sb.toString();
	}
	/**
	 *问题类别变化情况-钻取查询问题类别分布属地
	 * @author guoh
	 * @date 2017-08-28
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public String issueDistribution(Map<String, Object> map,PersonInfo person) throws ParseException{
		List<Map<String, Object>> list = issueInfoDao.issueDistribution(map,person);
		StringBuffer sb = new StringBuffer("[");
		for(int i=0;i<list.size();i++){
			sb.append("{\"type\":\"件数\",\"code\":\"").append(list.get(i).get("issueRegionCode")).append("\",\"name\":\"").append(list.get(i).get("issueRegionName")).append("\",\"count\":").append(list.get(i).get("count")).append("}");
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 问题类别分布情况-点击违纪行为查询子类问题类别对应的数量
	 * @author guoh
	 * @date 2017-08-29
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public String queryWJSubclass(Map<String, Object> map,PersonInfo person) throws ParseException{
		List<Map<String, Object>> sourceList = issueInfoDao.queryWJSubclass(map,person);
		//20170925 byg 对结果集中旧的问题性质进行替换，将旧的count值累加至对应的新问题性质count中
		for (Map<String, Object> rawMap : sourceList) {
			rawMap.put("code", petitionBasicInfoManager.oldIssueCodeToNewcode(rawMap.get("code").toString()));
		}
		//使用map进行去重与累加操作。
		Map<String, Map<String, Object>> targetmMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> rawMap : sourceList) {
			rawMap.put("name",CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),rawMap.get("code").toString(),true));
			if(targetmMap.containsKey(rawMap.get("code"))){
				Map<String, Object> item = targetmMap.get(rawMap.get("code").toString());
				item.put("count", Integer.parseInt(item.get("count").toString()) + Integer.parseInt(rawMap.get("count").toString()));
				targetmMap.put(rawMap.get("code").toString(), item);
			}else{
				targetmMap.put(rawMap.get("code").toString(), rawMap);
			}
		}
		StringBuffer sb = new StringBuffer("[");
		for (Entry<String, Map<String, Object>> entry : targetmMap.entrySet()) {
			sb.append("{\"name\":\"").append(entry.getValue().get("name")).append("\",\"value\":\"").append(entry.getValue().get("count")).append("\"},");
		}
		sb = sb.toString().endsWith(",")?new StringBuffer(sb.substring(0, sb.length()-1)):sb;
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 点击违法行为查询子类
	 * @return
	 */
	public String wfBehavior(){
		List<Map<String, Object>> list = issueInfoDao.wfBehavior();
		StringBuffer sb = new StringBuffer("[");
		for(int i=0;i<list.size();i++){
			sb.append("{\"name\":\"").append(list.get(i).get("name")).append("\",\"code\":\"").append(list.get(i).get("code")).append("\",\"count\":").append(list.get(i).get("count")).append("}");
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 点击子类查询问题类别
	 * @return
	 */
	public String queryIssueType(String code){
		List<Map<String, Object>> list = issueInfoDao.queryIssueType(code);
		StringBuffer sb = new StringBuffer("[");
		for(int i=0;i<list.size();i++){
			sb.append("{\"name\":\"").append(list.get(i).get("name")).append("\",\"code\":\"").append(list.get(i).get("code")).append("\",\"count\":").append(list.get(i).get("count")).append("}");
			if(i!=list.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}