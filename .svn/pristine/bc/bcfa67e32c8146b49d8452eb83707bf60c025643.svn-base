package com.sinosoft.xf.petition.accept.manager;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.systemcode.dao.SystemCodeNodeInfoDao;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants;
import com.sinosoft.xf.constants.UserConstants.PetitionSourceCode;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.petition.accept.dao.PetitionAccusedInfoDao;
import com.sinosoft.xf.petition.accept.dao.PetitionAccuserInfoDao;
import com.sinosoft.xf.petition.accept.dao.PetitionBasicInfoDao;
import com.sinosoft.xf.petition.accept.domain.IssueContentInfo;
import com.sinosoft.xf.petition.accept.domain.IssueTypeInfo;
import com.sinosoft.xf.petition.accept.domain.NetContentInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccusedInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccuserInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAssignedInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionIssueInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionOverSeeInfo;
import com.sinosoft.xf.petition.domainutil.IssueTypeCodeCount;
import com.sinosoft.xf.petition.domainutil.PetitionInfo;
import com.sinosoft.xf.petition.domainutil.SystemCodeNodeInfoVO;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 基本信息 manager
 * @date 2017-06-12
 * @author guoh
 */
@Service
@Transactional
public class PetitionBasicInfoManager extends EntityManager<PetitionBasicInfo, String> {
	@Autowired
	PetitionBasicInfoDao basicInfoDao;
	@Autowired
	SystemCodeNodeInfoDao systemCodeNodeInfoDao;
	/**
	 * 注入accuserInfoDao
	 */
	@Autowired
	PetitionAccuserInfoDao accuserInfoDao;
	/**
	 * 注入accuserInfoDao
	 */
	@Autowired
	PetitionAccusedInfoDao accusedInfoDao;
	@Override
	protected HibernateDao<PetitionBasicInfo, String> getEntityDao() {
		return basicInfoDao;
	}
	/**
	 * 信访件分布情况-全市的信访件分布主页面查询
	 * @author guoh
	 * @date 2017-06-30
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public String queryXFJPaiZhu(Map<String, Object> map,PersonInfo person) throws ParseException{
		List<Map<String, Object>> list = basicInfoDao.queryXFJPaiZhu(map,person);
		//20170925 byg 对结果集中被合并的机构码进行替换，将被合并的机构的count值累加至对应的并入的机构count中
		for (Map<String, Object> item : list) {
			//将所有被合并的 组织机构代码替换为被并入的机构码
			item.put("orgCode", oldRegionCodeToNewcodeSync(item.get("orgCode").toString()));
		}
		//使用map进行去重与累加操作。
		Map<String, Map<String, Object>> targetmMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> rawMap : list) {
			rawMap.put("orgCname", Constants.countyRegionCode_name.get(rawMap.get("orgCode")));
			if(targetmMap.containsKey(rawMap.get("orgCode"))){
				Map<String, Object> item = targetmMap.get(rawMap.get("orgCode").toString());
				item.put("count", Integer.parseInt(item.get("count")==null?"0":item.get("count").toString()) + Integer.parseInt(rawMap.get("count")==null?"0":rawMap.get("count").toString()));
				targetmMap.put(rawMap.get("orgCode").toString(), item);
			}else{
				targetmMap.put(rawMap.get("orgCode").toString(), rawMap);
			}
		}
		Collection<Map<String, Object>> noRepeatList = targetmMap.values();
		StringBuffer sb = new StringBuffer("[");
		String[] code1 = Constants.dispatchRegionCode;//派驻编码集
		String[] code = Constants.countyRegionCode;//区县编码集
		int shCount=0;//统计上海的总数
		for (Map<String, Object> item : noRepeatList) {
			for(int j=0;j<code1.length;j++){
				if(item.get("orgCode").equals(code1[j])){
					if(item.get("count")!=null){
						shCount+=Integer.parseInt(item.get("count").toString());
					}else {
						item.put("count", 0);
					}
					sb.append("{\"code\":\"").append(item.get("orgCode")).append("\",\"name\":\"").append(item.get("orgCname")).append("\",\"count\":").append(item.get("count")).append("},");
					break;
				}
			}
			for(int j=0;j<code.length;j++){
				if(item.get("orgCode").equals(code[j])){
					if(item.get("count")!=null){
						shCount+=Integer.parseInt(item.get("count").toString());
					}else {
						item.put("count", 0);
					}
					sb.append("{\"code\":\"").append(item.get("orgCode")).append("\",\"name\":\"").append(item.get("orgCname")).append("\",\"count\":").append(item.get("count")).append("},");
					break;
				}
			}
			if("310000000000".equals(item.get("orgCode").toString())){
				if(item.get("count")!=null){
					shCount+=Integer.parseInt(item.get("count").toString());
				}else {
					item.put("count", 0);
				}
			}
		}
		/*20171012 byg 暂不将上海的总量进行展示
		sb.append("{\"code\":\"310000000000\",\"name\":\"上海市\",\"count\":").append(shCount).append("}");
		*/
		sb.append("]");
		return sb.toString();
	}
	/**
	 *  信访件分布情况-查询信访件列表
	 * @date 2017-06-12
	 * @author guoh
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ExtjsPage<PetitionInfo> getPetitionInfo(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception {
		int size = basicInfoDao.getBasicInfoSize(map,person);
		List<Map<String, Object>> lists = basicInfoDao.getBasicInfoList(map,start,limit,person);
		return getPetitionInfo(size,lists);
	}
	/**
	 * 信访件分布情况（本市）、问题类别数量前5位-查询信访件列表
	 * @date 2017-08-25
	 * @author guoh
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ExtjsPage<PetitionInfo> getLetterInfo(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception {
		int size = basicInfoDao.getLetterInfoSize(map,person);
		List<Map<String, Object>> lists = basicInfoDao.getLetterInfoList(map,start,limit,person);
		return getPetitionInfo(size,lists);
	}
	/**
	 * 问题类别变化情况、问题类别分布情况
	 * 钻取查询信访件列表
	 * @date 2017-08-28
	 * @author guoh
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ExtjsPage<PetitionInfo> classChangeInfo(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception {
		int size = basicInfoDao.getClassChangeInfoSize(map,person);
		List<Map<String, Object>> lists = basicInfoDao.getClassChangeInfoList(map,start,limit,person);
		return getPetitionInfo(size,lists);
	}
	/**
	 *  全市本委各月趋势统计
	 * @author guoh
	 * @date 2017-06-26
	 * @param map1
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public String trendStatistical(Map<String, Object> map1,PersonInfo person) throws ParseException{
		List<Map<String, Object>> list = basicInfoDao.trendStatistical(map1,person);
		StringBuffer sb = new StringBuffer("[");
		Map<String, Object> map = list.get(0);
		
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if(entry.getKey().length()>3){
				String year = entry.getKey().substring(1, 5);
				String month = entry.getKey().substring(5, 6);
				sb.append("{\"month\":\"").append(year).append("年").append(month).append("月\",\"count\":").append(entry.getValue()).append("},");
			}else{
				sb.append("{\"month\":\"").append(entry.getKey().substring(1)).append("月\",\"count\":").append(entry.getValue()).append("},");
			}
        }
		String str = sb.substring(0, sb.length()-1)+"]";
		return str;
	}
	/**
	 * 各月、同比、环比-钻取查询问题属地和问题类别
	 * @author guoh
	 * @date 2017-08-31
	 * @param map
	 * @param person
	 * @return
	 */
	public ExtjsPage<SystemCodeNodeInfoVO> reportDrilling(Map<String, Object> map,PersonInfo person){
		ExtjsPage<SystemCodeNodeInfoVO> page = new ExtjsPage<SystemCodeNodeInfoVO>();
		try {
			List<IssueTypeCodeCount> list = basicInfoDao.reportDrilling(map,person);//查询选中的月份问题属地和问题类别
			//将所有的key 都转换为新的
			for (IssueTypeCodeCount item : list) {
				item.setIssueTypeCode(oldIssueCodeToNewcode(item.getIssueTypeCode()));
				item.setRegionCode(oldRegionCodeToNewcodeSync(item.getRegionCode()));
			}
			Map<String, IssueTypeCodeCount> targetMap = new HashMap<String, IssueTypeCodeCount>();
			for (IssueTypeCodeCount item : list) {
				if (targetMap.containsKey(item.getIssueTypeCode()+item.getRegionCode())) {
					IssueTypeCodeCount tempItem =targetMap.get(item.getIssueTypeCode()+item.getRegionCode());
					tempItem.setCount(item.getCount()+tempItem.getCount());
					targetMap.put(item.getIssueTypeCode()+item.getRegionCode(), tempItem);
				}else {
					targetMap.put(item.getIssueTypeCode()+item.getRegionCode(), item);
				}
			}
			Collection<IssueTypeCodeCount> vals = targetMap.values();
			//统计每个地区的违法违纪总量
			Map<String,Integer> zNum = new HashMap<String, Integer>();
			for (IssueTypeCodeCount item : vals) {
				String regionCode = item.getRegionCode();
				if(null==zNum.get(regionCode)){
					zNum.put(regionCode, item.getCount());
				}else{
					zNum.put(regionCode,zNum.get(regionCode)+item.getCount());
				}
			}
			Map<String,SystemCodeNodeInfoVO> issueTypeCodeCountMap = new HashMap<String, SystemCodeNodeInfoVO>();
			Map<String,Map<String,Integer>> sumZ = new HashMap<String, Map<String,Integer>>();
			for (IssueTypeCodeCount item : vals) {
				//regionCode前4位固定值其余补零作为key在map中初始化对应的对象
				String mapKey = item.getRegionCode()+"00000000";//问题属地 
				SystemCodeNodeInfoVO vo = issueTypeCodeCountMap.get(mapKey);
				if(vo == null){
					vo = new SystemCodeNodeInfoVO();
					issueTypeCodeCountMap.put(mapKey, vo);
				}
				String issueTypeCode = item.getIssueTypeCode();//问题性质的code
				if(issueTypeCode!=null&&!"".equals(issueTypeCode)){//由于有错误的数据存在（检举控告 问题性质为空），这里加入判断，防止异常
					vo.setRegionName(Constants.countyRegionCode_name.get(mapKey));//确定对象的机构名称
					vo.setzCount(zNum.get(item.getRegionCode()));//地区违法违纪总量装入
					Field f = vo.getClass().getDeclaredField("num"+issueTypeCode);//获取对应问题性质的 属性
					if(f!=null){
						f.setAccessible(true);//可访问
						f.setInt(vo, item.getCount());//绑定对象 并为此对象的此属性赋值  确定 当前机构对应的当前问题性质的量
					}
					String codeSum = issueTypeCode.substring(0, 4);//取问题类别的前4位判断属于哪个大类
					Field fsum = vo.getClass().getDeclaredField("num"+codeSum);//获取问题性质的大类对应的属性，并为其赋值，当循环完成即为累加值
					if(fsum!=null){
						Map<String,Integer> sumF = new HashMap<String, Integer>();
						fsum.setAccessible(true);
						if(null==sumZ.get(item.getRegionCode())){
							sumF.put(codeSum,item.getCount());
							sumZ.put(item.getRegionCode(), sumF);
						}else{
							if(null==sumZ.get(item.getRegionCode()).get(codeSum)){
								sumZ.get(item.getRegionCode()).put(codeSum, item.getCount());
							}else{
								sumZ.get(item.getRegionCode()).put(codeSum,sumZ.get(item.getRegionCode()).get(codeSum)+item.getCount());
							}
						}
						fsum.setInt(vo,sumZ.get(item.getRegionCode()).get(codeSum));
					}
				}
			}
			//有其他系统 31DD00000000 ，东航系统 31AT00000000 ，宝钢系统 31AQ00000000 时 ，剔除
			issueTypeCodeCountMap.remove("31DD00000000");
			issueTypeCodeCountMap.remove("31AT00000000");
			issueTypeCodeCountMap.remove("31AQ00000000");
			page.setTotal(issueTypeCodeCountMap.keySet().size());
			page.setResult(new ArrayList<SystemCodeNodeInfoVO>(issueTypeCodeCountMap.values()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 信访件列表查询公共调用方法
	 * @date 2017-06-16
	 * @author guoh
	 * @param size
	 * @param lists
	 * @return
	 * @throws Exception
	 */
	public ExtjsPage<PetitionInfo> getPetitionInfo(int size,List<Map<String, Object>> lists)throws Exception{
		ExtjsPage<PetitionInfo> page = new ExtjsPage<PetitionInfo>();
		List<PetitionBasicInfo> list = new ArrayList<PetitionBasicInfo>();
		if(size>0){
			String [] ids=new String[lists.size()];
			for(int i=0;i<lists.size();i++){
				ids[i] = lists.get(i).get("BID").toString();
			}
			list = basicInfoDao.listBasicByIds(ids);
		}
		
		List<PetitionInfo> petitionInfoList = new ArrayList<PetitionInfo>();
		for(PetitionBasicInfo petitionBasicInfo : list) {
			PetitionInfo petitionInfo = new PetitionInfo();
			PropertyUtils.copyProperties(petitionInfo, petitionBasicInfo);
			petitionInfo.setPetitionTitle(petitionBasicInfo.getPetitionTitle());
			Set<PetitionAccuserInfo> accuserInfoSet = petitionBasicInfo.getAccuserInfoSet();
			Iterator<PetitionAccuserInfo> accuserInfoSetIt = accuserInfoSet.iterator();
			while(accuserInfoSetIt.hasNext()){
				PetitionAccuserInfo accuserInfo = accuserInfoSetIt.next();
				if(accuserInfo.getAccuserNum()==1){
					PropertyUtils.copyProperties(petitionInfo, accuserInfo);
					petitionInfo.setAccuserId(accuserInfo.getId());
					break;
				}
			}
			Set<PetitionAccusedInfo> accusedInfoSet = petitionBasicInfo.getAccusedInfoSet();
			Iterator<PetitionAccusedInfo> accusedInfoSetIt = accusedInfoSet.iterator();
			while(accusedInfoSetIt.hasNext()){
				PetitionAccusedInfo accusedInfo = accusedInfoSetIt.next();
				if(accusedInfo.getAccusedNum()==1){
					PropertyUtils.copyProperties(petitionInfo, accusedInfo);
					petitionInfo.setAccusedId(accusedInfo.getId());
					break;
				}
			}
			Set<PetitionIssueInfo> issueInfoSet = petitionBasicInfo.getIssueInfoSet();
			Iterator<PetitionIssueInfo> issueInfoSetIt = issueInfoSet.iterator();
			while(issueInfoSetIt.hasNext()){
				PetitionIssueInfo issueInfo = issueInfoSetIt.next();
				if(issueInfo.getIssueNum()==1){
					PropertyUtils.copyProperties(petitionInfo, issueInfo);
					petitionInfo.setIssueId(issueInfo.getId());
					break;
				}
			}
			petitionInfo.setBasicId(petitionBasicInfo.getId());
			petitionInfoList.add(petitionInfo);
		}
		
		page.setTotalCount(size);
		page.setResult(petitionInfoList);
		return page;
	}
	/**
	 * 显示信访件详细信息
	 * @author guoh
	 * @date 2017-06-21
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PetitionInfo loadPetitionInfo(String id) throws Exception {
		Object[] obj = basicInfoDao.getPetition(id);
		if(obj == null) {
			return null;
		}
		PetitionInfo petition = new PetitionInfo();
		if (null != obj[0]) {//基本信息
			petition.setBasicId(((PetitionBasicInfo) obj[0]).getId());
		}
		if (null != obj[1]) {//反映人
			PropertyUtils.copyProperties(petition, (PetitionAccuserInfo) obj[1]);
			petition.setAccuserId(((PetitionAccuserInfo) obj[1]).getId());
		}
		if (null != obj[2]) {//问题属地
			PropertyUtils.copyProperties(petition, (PetitionIssueInfo) obj[2]);
			petition.setIssueId(((PetitionIssueInfo) obj[2]).getId());
		}
		//如果petition.getIssueContentNumFlag()是1问题描述的长度超过2000了从issueContentInfo表里取值
		if(null!=petition.getIssueContentNumFlag()){
			if("1"==petition.getIssueContentNumFlag()){
				Set<IssueContentInfo> issueContentInfos = ((PetitionIssueInfo)obj[2]).getIssueContentInfoSet();
				Iterator<IssueContentInfo> iterator = issueContentInfos.iterator();
				while (iterator.hasNext()) {
					IssueContentInfo issueContentInfo = iterator.next();
					if(issueContentInfo.getIssueNum()==1){
						petition.setIssueContent(issueContentInfo.getIssueContent());
					}
				}
			}
		}
		if (obj.length >= 4) {//被反映人
			if (null != obj[3]) {
				boolean temp = obj[3] instanceof PetitionAccusedInfo;
				if(temp){
					PropertyUtils.copyProperties(petition, (PetitionAccusedInfo) obj[3]);
					petition.setAccusedId(((PetitionAccusedInfo) obj[3]).getId());
				}
			}
		}
		//查询其他问题类别信息
		Set<IssueTypeInfo> issueTypeInfos = ((PetitionBasicInfo)obj[0]).getIssueTypeInfoSet();
		Iterator<IssueTypeInfo> issueTypeIt = issueTypeInfos.iterator();
		StringBuffer otherIssueTypeName = new StringBuffer();
		StringBuffer otherIssueTypeCode = new StringBuffer();
		while (issueTypeIt.hasNext()) {
			IssueTypeInfo issueTypeInfo = issueTypeIt.next();
			otherIssueTypeName.append(issueTypeInfo.getIssueTypeName());
			otherIssueTypeCode.append(issueTypeInfo.getIssueTypeCode());
			otherIssueTypeName.append(",");
			otherIssueTypeCode.append(",");
		}
		petition.setIssueTypeCode(otherIssueTypeCode.toString());
		petition.setIssueTypeName(otherIssueTypeName.toString());
		PropertyUtils.copyProperties(petition, (PetitionBasicInfo) obj[0]);
		petition.setAccusedRegionCode(petition.getAccusedRegionCode().trim());
		petition.setIssueRegionCode(petition.getIssueRegionCode().trim());
		return petition;
	}
	
	/**
	 *  同比分布情况(只查本年)-主查询
	 * @author guoh
	 * @date 2017-07-13
	 * @param swFlag
	 * @param bwRadio
	 * @param person
	 * @return
	 */
	public String tbQuery(String swFlag,String bwRadio,PersonInfo person){
		List<Map<String, Object>> Jlist = basicInfoDao.tbQuery(swFlag,bwRadio,"JN",person);//今年的数据
		List<Map<String, Object>> Qlist = basicInfoDao.tbQuery(swFlag,bwRadio,"QN",person);//去年的数据
		
		StringBuffer JData = new StringBuffer("[");
		Map<String, Object> jmap = Jlist.get(0);
		for(int i=1;i<13;i++){
			String num = "Q"+i;
			if(null!=jmap.get(num)){
				JData.append("{count:").append(jmap.get(num)).append("}");
			}else{
				JData.append("{count:").append(0).append("}");
			}
			if(i!=12){
				JData.append(",");
			}
		}
		JData.append("],");
		
		StringBuffer QData = new StringBuffer("[");
		Map<String, Object> qmap = Qlist.get(0);
		for(int i=1;i<13;i++){
			String num = "Q"+i;
			if(null!=qmap.get(num)){
				QData.append("{count:").append(qmap.get(num)).append("}");
			}else{
				QData.append("{count:").append(0).append("}");
			}
			if(i!=12){
				QData.append(",");
			}
		}
		QData.append("],");
		
		StringBuffer sbz = new StringBuffer("[");
		NumberFormat numberFormat = NumberFormat.getInstance();     
		numberFormat.setMaximumFractionDigits(2); 
		
		for(int i=1;i<13;i++){
			String big = "Q"+i;
			String small = "Q"+i;
			int b = 0;
			int s = 0;
			String result = "0";
			if(null!=jmap.get(big)){
				b = Integer.parseInt(jmap.get(big).toString());
			}
			if(null!=qmap.get(small)){
				s = Integer.parseInt(qmap.get(small).toString());
			}
			/*if(b==0&&s!=0){
				result = numberFormat.format(((float)b-(float)s)/(float)1*100); 
			}else if(b!=0&&s==0){
				result = numberFormat.format((float)b/(float)1*100); 
			}else if(b==0&&s==0){
				result = numberFormat.format(0); 
			}else{
				result = numberFormat.format(((float)b-(float)s)/(float)s*100);
			}*/
			if(b!=0&&s!=0){
				result = numberFormat.format(((float)b-(float)s)/(float)s*100);
			}
			
			sbz.append("{count:").append(result).append("}");
			if(i!=12){
				sbz.append(",");
			}
		}
		sbz.append("]");
		
		return JData.toString()+QData.toString()+sbz.toString();
	}
	/**
	 *  环比分布情况(只查本年)-主查询
	 * @author guoh
	 * @date 2017-07-14
	 * @param swFlag
	 * @param bwRadio
	 * @param person
	 * @return
	 */
	public String SequentialQuery(String swFlag,String bwRadio,PersonInfo person){
		List<Map<String, Object>> hbList = basicInfoDao.SequentialQuery(swFlag,bwRadio,person);//今年的数据
		Map<String, Object> map = hbList.get(0);
		
		List<Float> percent = new ArrayList<Float>();//获取环比的百分比
		NumberFormat numberFormat = NumberFormat.getInstance();     
		numberFormat.setMaximumFractionDigits(2); 
		
		DateTime date = new DateTime();
		int month = date.getMonthOfYear();
		for(int i=0;i<month;i++){
			String big = "Q"+i;
			String small = "Q"+(i+1);
			int b = 0;
			int s = 0;
			float result = 0;
			if(null!=map.get(big)){
				b = Integer.parseInt(map.get(big).toString());
			}
			if(null!=map.get(small)){
				s = Integer.parseInt(map.get(small).toString());
			}
			if(b!=0&&s!=0){
				result = Float.parseFloat(numberFormat.format(((float)s-(float)b)/(float)b*100));
			}
			percent.add(result);
		}
		StringBuffer sbData = new StringBuffer("[");
		
		for(int i=1;i<13;i++){
			String num = "Q"+i;
			sbData.append("{\"time\":\"").append(i).append("月\",");
			if(i<=month){
				if(null!=map.get(num)){
					sbData.append("\"数量\":").append(map.get(num)).append(",");
				}else{
					sbData.append("\"数量\":").append(0).append(",");
				}
				sbData.append("\"percent\":").append(percent.get(i-1).toString()).append("}");
			}else{
				sbData.append("\"数量\":").append(0).append("}");
			}
			if(i!=12){
				sbData.append(",");
			}
		}
		sbData.append("]");
		return sbData.toString();
	}
	/**
	 *  同比、环比、各月趋势-钻取查询列表
	 * @author guoh
	 * @date 2017-09-20
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public ExtjsPage<PetitionInfo> TrendCompareQueryList(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception{
		int size = basicInfoDao.getTrendCompareQuerySize(map,person);
		List<Map<String, Object>> lists = basicInfoDao.getTrendCompareQueryList(map,start,limit,person);
		return getPetitionInfo(size,lists);
	}
	/**
	 * 根据信访编号查询所有regionCode
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Map<String, String> getRegionCodeMapByPetitionNo(String petitionNo) throws Exception {
		String regionCode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		List<PetitionBasicInfo> list = basicInfoDao.listByPetitionNo(petitionNo,null);
		Map<String, String> map = new TreeMap<String, String>();
		for(PetitionBasicInfo basic : list) {
			if(regionCode.compareTo(basic.getRegionCode())<=0){
				map.put(basic.getRegionCode(), basic.getRegionName());
			}
		}
		return map;
	}
	/**
	 * 根据信访编号和regionCode查询信访信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public PetitionBasicInfo getBasicInfoByPetitionNo(String petitionNo, String regionCode) throws Exception {
		PetitionBasicInfo basic = basicInfoDao.getBasicInfoByPetitionNo(petitionNo, regionCode);
		return basic;
	}
	/**
	 * 通过信访基本信息oid加载信访信息，用于修改
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param id 基本信息oid
	 * @param petitionClassCode 信访类别
	 * @return bean对象
	 */
	@Transactional(readOnly = true)
	public PetitionInfo loadPetitionInfo(final String id, final String petitionClassCode) throws Exception {
		PetitionBasicInfo basic = basicInfoDao.get(id);
		PetitionInfo petition = new PetitionInfo();
		PropertyUtils.copyProperties(petition, basic);
		petition.setBasicId(basic.getId());
		//2016-05-25 byg 在全局修改中添加 “线索来源” 修改项 (回显)
		Set<PetitionOverSeeInfo> overSeeSet =basic.getOverSeeSet();
		for (PetitionOverSeeInfo overSee : overSeeSet) {
			petition.setQueryKeyNo(overSee.getQueryKeyNo());
		}
		Set<PetitionAccuserInfo> accuserInfoSet = basic.getAccuserInfoSet();
		for (PetitionAccuserInfo petitionAccuserInfo : accuserInfoSet) {
			if(petitionAccuserInfo.getAccuserNum()==1){
				PropertyUtils.copyProperties(petition,petitionAccuserInfo);
				petition.setAccuserId(petitionAccuserInfo.getId());
				petition.setAccuserAppealFlag(petitionAccuserInfo.getConstantAppealFlag());
				break;
			}
		}
		if (UserConstants.PetitionClassCode.Accuse.toString().equals(petitionClassCode)
				||UserConstants.PetitionClassCode.OutBusiness.toString().equals(petitionClassCode)) {
			Set<PetitionAccusedInfo> accusedInfoSet = basic.getAccusedInfoSet();
			for (PetitionAccusedInfo petitionAccusedInfo : accusedInfoSet) {
				if(petitionAccusedInfo.getAccusedNum()==1){
					PropertyUtils.copyProperties(petition,petitionAccusedInfo);
					petition.setAccusedId(petitionAccusedInfo.getId());
					petition.setAccusedAppealFlag(petitionAccusedInfo.getConstantAppealFlag());
					break;
				}
			}
		}
		Set<PetitionIssueInfo> issueSet = basic.getIssueInfoSet();
		for (PetitionIssueInfo petitionIssueInfo : issueSet) {
			PropertyUtils.copyProperties(petition, petitionIssueInfo);
			petition.setIssueId(petitionIssueInfo.getId());
			if ("1".equals(petitionIssueInfo.getIssueContentNumFlag())) {
				Set<IssueContentInfo> issueContentInfos = petitionIssueInfo.getIssueContentInfoSet();
				Iterator<IssueContentInfo> iterator = issueContentInfos.iterator();
				while (iterator.hasNext()) {
					IssueContentInfo issueContentInfo = iterator.next();
					if (issueContentInfo.getIssueNum() == 1) {
						petition.setIssueContent(issueContentInfo.getIssueContent());
					}
					break;
				}
			}
		}
		Set<NetContentInfo> netContentSet = basic.getNetContentInfoSet();
		Iterator<NetContentInfo> iterator = netContentSet.iterator();
		while (iterator.hasNext()) {
			NetContentInfo netInfo = iterator.next();
			if(null!=netInfo.getNetContent()){
				petition.setNetContent(Encrypt.decrypt(new String(netInfo.getNetContent())));
			}
			break;
		}		
		Set<PetitionDealInfo> dealInfoSet = basic.getDealInfoSet();
		for (PetitionDealInfo petitionDealInfo : dealInfoSet) {
			if("0".equals(petitionDealInfo.getValidFlag())){
				PropertyUtils.copyProperties(petition,petitionDealInfo);
				petition.setDealId(petitionDealInfo.getId());
				petition.setDealSuggestion(petitionDealInfo.getDealSuggestion());
				if(petitionDealInfo.getTransDealInfo()!=null){
					petition.setToRegionName(petitionDealInfo.getTransDealInfo().getToRegionName());
				}
				break;
			}
		}
		//查询其他问题类别信息
		Set<IssueTypeInfo> issueTypeInfos = basic.getIssueTypeInfoSet();
		Iterator<IssueTypeInfo> issueTypeIt = issueTypeInfos.iterator();
		String otherIssueTypeName = "";
		String otherIssueTypeCode = "";
		while (issueTypeIt.hasNext()) {
			IssueTypeInfo issueTypeInfo = issueTypeIt.next();
			if(!"1".equals(issueTypeInfo.getFirstFlag())){//其他问题类别
				otherIssueTypeName += issueTypeInfo.getIssueTypeName() + ";";
				otherIssueTypeCode += issueTypeInfo.getIssueTypeCode() + ";";
			}
		}
		petition.setOtherIssueTypeName(otherIssueTypeName);
		petition.setOtherIssueTypeCode(otherIssueTypeCode);
		petition.setRemark(basic.getRemark());
		//如果是上级交办的话
		if(PetitionSourceCode.SJJB.toString().equals(petition.getPetitionSourceCode()) || PetitionSourceCode.QTBMJB.toString().equals(petition.getPetitionSourceCode())){
			Set<PetitionAssignedInfo> assignedInfoSet =  basic.getAssignedInfoSet();
			if (assignedInfoSet != null && !assignedInfoSet.isEmpty()) {
				PetitionAssignedInfo assignedInfo = assignedInfoSet.iterator().next();
				petition.setLetterNo(assignedInfo.getAssignedLetterText());
				petition.setLetterContent(assignedInfo.getAssignedContent());
				petition.setAssignedDate(assignedInfo.getAssignedDate());
				petition.setAssignedRequireEndDate(assignedInfo.getAssignedRequireEndDate());
			}
		}
		petition.setCreatorName(basic.getCreatorName());
		//2016-01-06 liub防止二期之前的信访来源在现在的库中查询不到
		String petitionSourceParentCode=getPetitionSourceParentCode(petition.getPetitionSourceCode());
		if(petitionSourceParentCode!=null&&!petitionSourceParentCode.equals("")){
			petition.setPetitionSourceParentCode(petitionSourceParentCode);
		}
		return petition;
	}
	/**
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param petitionSourceCode
	 * @return
	 */
	private String getPetitionSourceParentCode(String petitionSourceCode){
		// add by lijun 2014-12-17根据当前信访来源获取信访来源父节点code
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		Map map = new HashMap();
		map.put("codeType", "XFLY");
		map.put("code", petitionSourceCode);
		map.put("orgCode", person.getRegionCode());
		SystemCodeNodeInfo node = (SystemCodeNodeInfo)systemCodeNodeInfoDao.findUnique(
		"From SystemCodeNodeInfo Where codeType=:codeType and code=:code and (orgCode=:orgCode or orgCode is null or orgCode = '')", 
		map);
		//2016-01-06 liub防止二期之前的信访来源在现在的库中查询不到
		if(node!=null){
			map.put("parentId", node.getParentId());
			SystemCodeNodeInfo parentNode = (SystemCodeNodeInfo)systemCodeNodeInfoDao.findUnique(
					"From SystemCodeNodeInfo Where id=:parentId", 
					map);
			return parentNode!=null?parentNode.getCode():"";
		}else {
			return "";
		}
	}
	/**
	 * 查询其他反映人信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @return 其他反映人集合
	 */
	@Transactional(readOnly = true)
	public List<PetitionAccuserInfo> getOtherAccuserInfo(String petitionNo,String regionCode) throws Exception{
		List<PetitionAccuserInfo> list = accuserInfoDao.getOtherAccuserInfo(petitionNo, regionCode);
		return list;
	}
	/**
	 * 查询其他被反映对象信息
	 * @param petitionNo  部门编号
	 * @author lijun
	 * @date 2014-04-03
	 * @return 其他被反映对象集合
	 */
	@Transactional(readOnly = true)
	public List<PetitionAccusedInfo> getOtherAccusedInfo(String petitionNo,String regionCode) throws Exception{
		List<PetitionAccusedInfo> list = accusedInfoDao.getOtherAccusedInfo(petitionNo, regionCode);
		return list;
	}
	/**
	 * 旧的机构code转换为新的机构code
	 * @author BYG
	 * @date 2017年9月25日
	 * @param code 旧区划regionCode
	 * @return 新区划regionCode
	 */
	public String oldRegionCodeToNewcode(String code){
		int bit = code.length();
		for(int i=0;i<12-bit;i++){
			code = code + "0";
		}
		if("310800000000".equals(code)){
			code = "310600000000";//310600000000 静安区  <in 闸北区 310800000000
		}else if("31AJ00000000".equals(code)){
			code = "31DN00000000";//31DN00000000 市委组织部纪检组  <in 市社会工作党委纪检组31AJ00000000
		}else if("31AO00000000".equals(code)){
			code = "31AF00000000";//31AF00000000 市委政法委机关纪检组  <in 市司法局纪检组31AO00000000
		}else if("31AZ00000000".equals(code)||"31BH00000000".equals(code)||"31DJ00000000".equals(code)){
			code = "31BA00000000";//31BA00000000 市委农办纪检组  <in 市工商局纪检组31AZ00000000；市质量技监局纪检组31BH00000000；市食品药品监管局纪检组31DJ00000000
		}else if("31BV00000000".equals(code)){
			code = "31AX00000000";//31AX00000000 市教育卫生党委纪检组  <in 市体育局纪检组31BV00000000
		}else if("31BJ00000000".equals(code)){
			code = "31AH00000000";//31AH00000000 市经济信息化工作党委纪检组  <in 市安全监管局纪检组31BJ00000000
		}else if("31DK00000000".equals(code)||"31BN00000000".equals(code)){
			code = "31AB00000000";//31AB00000000 市建设交通工作党委纪检组  <in 市绿化市容局纪检组31DK00000000；民防办纪检组31BN00000000
		}else if("31AC00000000".equals(code)){
			code = "31AA00000000";//31AA00000000 市国资委党委纪检组  <in 市金融工作党委纪检组31AC00000000
		}else if("31BQ00000000".equals(code)||"31AE00000000".equals(code)||"31DQ00000000".equals(code)){
			code = "31BR00000000";//31BR00000000 市政府办公厅纪检组  <in 市政府外办纪检组31BQ00000000；市机管局纪检组31AE00000000；市政府办公厅(旧) 31DQ00000000
		}else if("31BI00000000".equals(code)){
			code = "31BB00000000";//31BB00000000 市发展改革委纪检组  <in 市统计局纪检组31BI00000000
		}else if("31BM00000000".equals(code)){
			code = "31BC00000000";//31BC00000000 市商务委纪检组  <in 旅游局纪检组31BM00000000
		}else if("31BF00000000".equals(code)){
			code = "31AL00000000";//31AL00000000 市财政局纪检组  <in 市审计局纪检组31BF00000000
		}else if("31DH00000000".equals(code)){
			code = "31DG00000000";//31DG00000000 市交通委纪检组  <in 市水务局纪检组31DH00000000
		}
		return code.substring(0,bit);
	}
	/**
	 * 旧的机构code转换为新的机构code 同步支持
	 * @author BYG
	 * @date 2017年9月25日
	 * @param code 旧区划regionCode
	 * @return 新区划regionCode
	 */
	public String oldRegionCodeToNewcodeSync(String code){
		int bit = code.length();
		for(int i=0;i<12-bit;i++){
			code = code + "0";
		}
		Map<String,String> adjustMap = CodeSwitchUtil.getOrgAdjustMap();
		if(adjustMap != null && adjustMap.size()>0){
			for (Entry<String, String> item : adjustMap.entrySet()) {
				if(item.getValue().contains(code)){
					code = item.getKey();
					break;
				}
			}
		}
		return code.substring(0,bit);
	}
	/**
	 * 新的机构查询要查询 被并入的机构
	 * @author BYG
	 * @date 2017年9月25日
	 * @param code 新的区划regionCode
	 * @return 包含新区划regionCode 及被并入其中的区划regionCode sql in集合
	 */
	public String unionOldRegionCodeAndNew(String code){
		int bit = code.length();
		for(int i=0;i<12-bit;i++){
			code = code + "0";
		}
		String[] codes;
		if("310600000000".equals(code)){
			codes = new String[]{"310600000000","310800000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//310600000000 静安区  <in 闸北区310800000000
		}else if("31DN00000000".equals(code)){
			codes = new String[]{"31DN00000000","31AJ00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31DN00000000 市委组织部纪检组  <in 市社会工作党委纪检组31AJ00000000
		}else if("31AF00000000".equals(code)){
			codes = new String[]{"31AF00000000","31AO00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31AF00000000 市委政法委机关纪检组  <in 市司法局纪检组31AO00000000
		}else if("31BA00000000".equals(code)){
			codes = new String[]{"31BA00000000","31AZ00000000","31BH00000000","31DJ00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"','"+codes[2].substring(0,bit)+"','"+codes[3].substring(0,bit)+"') ";//31BA00000000 市委农办纪检组  <in 市工商局纪检组31AZ00000000；市质量技监局纪检组31BH00000000；市食品药品监管局纪检组31DJ00000000
		}else if("31AX00000000".equals(code)){
			codes = new String[]{"31AX00000000","31BV00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31AX00000000 市教育卫生党委纪检组  <in 市体育局纪检组31BV00000000
		}else if("31AH00000000".equals(code)){
			codes = new String[]{"31AH00000000","31BJ00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31AH00000000 市经济信息化工作党委纪检组  <in 市安全监管局纪检组31BJ00000000
		}else if("31AB00000000".equals(code)){
			codes = new String[]{"31AB00000000","31DK00000000","31BN00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"','"+codes[2].substring(0,bit)+"') ";//31AB00000000 市建设交通工作党委纪检组  <in 市绿化市容局纪检组31DK00000000；民防办纪检组31BN00000000
		}else if("31AA00000000".equals(code)){
			codes = new String[]{"31AA00000000","31AC00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31AA00000000 市国资委党委纪检组  <in 市金融工作党委纪检组31AC00000000
		}else if("31BR00000000".equals(code)){
			codes = new String[]{"31BR00000000","31BQ00000000","31AE00000000","31DQ00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"','"+codes[2].substring(0,bit)+"','"+codes[3].substring(0,bit)+"') ";//31BR00000000 市政府办公厅纪检组  <in 市政府外办纪检组31BQ00000000；市机管局纪检组31AE00000000；市政府办公厅(旧) 31DQ00000000
		}else if("31BB00000000".equals(code)){
			codes = new String[]{"31BB00000000","31BI00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31BB00000000 市发展改革委纪检组  <in 市统计局纪检组31BI00000000
		}else if("31BC00000000".equals(code)){
			codes = new String[]{"31BC00000000","31BM00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31BC00000000 市商务委纪检组  <in 旅游局纪检组31BM00000000
		}else if("31AL00000000".equals(code)){
			codes = new String[]{"31AL00000000","31BF00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31AL00000000 市财政局纪检组  <in 市审计局纪检组31BF00000000
		}else if("31DG00000000".equals(code)){
			codes = new String[]{"31DG00000000","31DH00000000"};
			code = " IN ('"+codes[0].substring(0,bit)+"','"+codes[1].substring(0,bit)+"') ";//31DG00000000 市交通委纪检组  <in 市水务局纪检组31DH00000000
		}else{
			code = " = '"+code.substring(0,bit)+"' ";
		}
		return code;
	}
	/**
	 * 新的机构查询要查询 被并入的机构 同步支持
	 * @author BYG
	 * @date 2017年9月25日
	 * @param code 新的区划regionCode
	 * @return 包含新区划regionCode 及被并入其中的区划regionCode sql in集合
	 */
	public String unionOldRegionCodeAndNewSync(String code){
		int bit = code.length();
		for(int i=0;i<12-bit;i++){
			code = code + "0";
		}
		String codeStrs = CodeSwitchUtil.getOrgAdjustCodes(code);
		String[] codeArr = codeStrs.split(",");
		StringBuffer sb = new StringBuffer();
		if(codeArr.length>1){
			sb.append(" IN (");
			for (int i = 0; i < codeArr.length; i++) {
				sb.append("'").append(codeArr[i].substring(0, bit)+"',");
			}
			sb = new StringBuffer(sb.substring(0, sb.length()-1)+") ");
		}else {
			sb.append(" = '"+code.substring(0,bit)+"' ");
		}
		return sb.toString();
	}
	/**
	 * 16年违纪行为调整，旧代码转换新代码
	 * @author BYG
	 * @date 2017年9月25日
	 * @param code 旧问题类别code
	 * @return 新问题类别code
	 */
	public String oldIssueCodeToNewcode(String code){
		if("0101".equals(code)){
			code = "";
		}else if("010101".equals(code)){
			code = "012101";
		}else if("010102".equals(code)){
			code = "012104";
		}else if("010103".equals(code)){
			code = "012104";
		}else if("010104".equals(code)){
			code = "012101";
		}else if("010105".equals(code)){
			code = "012103";
		}else if("0102".equals(code)){
			code = "";
		}else if("010201".equals(code)){
			code = "012201";
		}else if("010202".equals(code)){
			code = "012206";
		}else if("010203".equals(code)){
			code = "012506";
		}else if("010204".equals(code)){
			code = "012207";
		}else if("0103".equals(code)){
			code = "";
		}else if("010301".equals(code)){
			code = "012302";
		}else if("010302".equals(code)){
			code = "012304";
		}else if("010303".equals(code)){
			code = "012307";
		}else if("010304".equals(code)){
			code = "012315";
		}else if("010305".equals(code)){
			code = "012306";
		}else if("010306".equals(code)){
			code = "012305";
		}else if("0104".equals(code)){
			code = "";
		}else if("010401".equals(code)){
			code = "0131";
		}else if("010402".equals(code)){
			code = "0131";
		}else if("010403".equals(code)){
			code = "0131";
		}else if("010404".equals(code)){
			code = "0131";
		}else if("010405".equals(code)){
			code = "0131";
		}else if("0105".equals(code)){
			code = "";
		}else if("010501".equals(code)){
			code = "0133";
		}else if("010502".equals(code)){
			code = "0133";
		}else if("010503".equals(code)){
			code = "0133";
		}else if("010504".equals(code)){
			code = "0136";
		}else if("010505".equals(code)){
			code = "0133";
		}else if("010506".equals(code)){
			code = "0133";
		}else if("0106".equals(code)){
			code = "  ";
		}else if("010601".equals(code)){
			code = "0133";
		}else if("010602".equals(code)){
			code = "0136";
		}else if("010603".equals(code)){
			code = "0136";
		}else if("010604".equals(code)){
			code = "0136";
		}else if("0107".equals(code)){
			code = "  ";
		}else if("010701".equals(code)){
			code = "0136";
		}else if("010702".equals(code)){
			code = "0132";
		}else if("010703".equals(code)){
			code = "012401";
		}else if("010704".equals(code)){
			code = "0133";
		}else if("0108".equals(code)){
			code = "";
		}else if("010801".equals(code)){
			code = "012204";
		}else if("010802".equals(code)){
			code = "0134";
		}else if("010804".equals(code)){
			code = "012204";
		}else if("010803".equals(code)){
			code = "0134";
		}else if("0109".equals(code)){
			code = "";
		}else if("010901".equals(code)){
			code = "012207";
		}else if("010902".equals(code)){
			code = "012602";
		}else if("010903".equals(code)){
			code = "012603";
		}else if("0110".equals(code)){
			code = "";
		}else if("011001".equals(code)){
			code = "0135";
		}else if("011002".equals(code)){
			code = "0136";
		}else if("011004".equals(code)){
			code = "0135";
		}else if("011003".equals(code)){
			code = "0135";
		}else if("0111".equals(code)){
			code = "012402";
		}
		return code;
	}
	/**
	 * 问题性质详细分类 新code 转换 新旧 并集
	 * @param code问题性质 新code
	 * @return 包含新问题性质code 及与其对应的旧问题性质code sql in集合
	 */
	public String unionOldIssueCodeAndNew(String code){
		if("012101".equals(code)){
			code = " IN ('012101','010101','010104') ";
		}else if("012103".equals(code)){
			code = " IN ('012103','010105') ";
		}else if("012104".equals(code)){
			code = " IN ('012104','010102','010103') ";
		}else if("012201".equals(code)){
			code = " IN ('012201','010201') ";
		}else if("012204".equals(code)){
			code = " IN ('012204','010801','010804') ";
		}else if("012206".equals(code)){
			code = " IN ('012206','010202') ";
		}else if("012207".equals(code)){
			code = " IN ('012207','010204','010901') ";
		}else if("012306".equals(code)){
			code = " IN ('012306','010305') ";
		}else if("012302".equals(code)){
			code = " IN ('012302','010301') ";
		}else if("012304".equals(code)){
			code = " IN ('012304','010302') ";
		}else if("012307".equals(code)){
			code = " IN ('012307','010303') ";
		}else if("012305".equals(code)){
			code = " IN ('012305','010306') ";
		}else if("012315".equals(code)){
			code = " IN ('012315','010304') ";
		}else if("012401".equals(code)){
			code = " IN ('012401','010703') ";
		}else if("012402".equals(code)){
			code = " IN ('012402','0111') ";
		}else if("012506".equals(code)){
			code = " IN ('012506','010203') ";
		}else if("012602".equals(code)){
			code = " IN ('012602','010902') ";
		}else if("012603".equals(code)){
			code = " IN ('012603','010903') ";
		}else if("0131".equals(code)){
			code = " IN ('0131','010401','010402','010403','010404','010405') ";
		}else if("0132".equals(code)){
			code = " IN ('0132','010702') ";
		}else if("0133".equals(code)){
			code = " IN ('0133','010505','010506','010501','010502','010503','010601','010704') ";
		}else if("0134".equals(code)){
			code = " IN ('0134','010802','010803') ";
		}else if("0135".equals(code)){
			code = " IN ('0135','011001','011004','011003') ";
		}else if("0136".equals(code)){
			code = " IN ('0136','010504','010604','010602','010603','010701','011002') ";
		}else{
			code = " = '"+code+"' ";
		}
		return code;
	}
	/**
	 * 问题性质大类12种 新code转换 新旧 并集
	 * @param 新的问题性质大类code 
	 * @return 包含新问题性质code 及与其对应的旧问题性质code无遗漏的 sql in集合
	 */
	public String switchToContainOld(String code){
		if("0121".equals(code)){
			code = " AND LEFT(issue.issue_type_code,4) IN ('0121','0101') ";
		}else if ("0122".equals(code)) {
			code = "AND (LEFT(issue.issue_type_code,4) = '0122' OR issue.issue_type_code IN ('010201','010202','010204','010801','010804','010901')) ";
		}else if ("0123".equals(code)) {
			code = " AND LEFT(issue.issue_type_code,4) IN ('0123','0103') ";
		}else if ("0124".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0124' OR issue.issue_type_code IN ('010703','0111')) ";
		}else if ("0125".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0125' OR issue.issue_type_code ='010203') ";
		}else if ("0126".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0126' OR issue.issue_type_code IN ('010902','010903')) ";
		}else if ("0131".equals(code)) {
			code = " AND LEFT(issue.issue_type_code,4) IN ('0131','0104') ";
		}else if ("0132".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0132' OR issue.issue_type_code ='010702') ";
		}else if ("0133".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0133' OR issue.issue_type_code IN ('010704','010601','010501','010502','010503','010505','010506')) ";
		}else if ("0134".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0134' OR issue.issue_type_code IN ('010802','010803')) ";
		}else if ("0135".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0135' OR issue.issue_type_code IN ('011001','011003','011004')) ";
		}else if ("0136".equals(code)) {
			code = " AND (LEFT(issue.issue_type_code,4) = '0136' OR issue.issue_type_code IN ('010504','010602','010603','010604','010701','011002')) ";
		}else if ("012".equals(code)) {
			code = "AND ((left(issue.issue_type_code,3)='012') OR (left(issue.issue_type_code,4) IN ('0101','0103')) OR issue.issue_type_code in('010201','010202','010204','010801','010804','010901','010703','0111','010203','010902','010903'))";
		}else if ("013".equals(code)) {
			code = "AND ((left(issue.issue_type_code,3)='013' and length(issue.issue_type_code)=4) OR (left(issue.issue_type_code,4)='0104') OR issue.issue_type_code in('010702','010704','010601','010501','010502','010503','010505','010506','010802','010803','011001','011003','011004','010504','010602','010603','010604','010701','011002'))";
		}else{
			code = " AND (LEFT(issue.issue_type_code,4) = '"+code+"' ";
		}
		return code;
	}
}