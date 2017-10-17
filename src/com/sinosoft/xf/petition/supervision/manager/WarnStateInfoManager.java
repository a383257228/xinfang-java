package com.sinosoft.xf.petition.supervision.manager;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.constants.UserConstants.AlarmType;
import com.sinosoft.xf.petition.supervision.dao.WarnStateInfoDao;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.petition.supervision.domain.WarnStateInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**   
 * 预警状态信息manager。
 * @author ljx   
 * @createDate 2011-04-27  
 */
@Service
@Transactional
public class WarnStateInfoManager extends EntityManager<WarnStateInfo, String>{
	
	/**
	 * 预警状态信息dao
	 */
	@Autowired
	WarnStateInfoDao warnStateInfoDao;
	
	/**
	 * 时限manager
	 */
	@Autowired
	TimeLimitDefineManager timeLimitDefineManager;
	
	/**
	 * 重写父类dao
	 * @return 预警状态信息dao
	 */
	protected HibernateDao<WarnStateInfo, String> getEntityDao() {
		return warnStateInfoDao;
	}
	
	/**
	 * 预警状态信息保存方法
	 * @author ljx
	 * @date 2011-05-11
	 * @param t 时限设置对象
	 * @param wList 符合时限设置条件的扫描对象
	 * @return void
	 * @throws ParseException 
	 */
	@SuppressWarnings("rawtypes")
	public void  warnStateInfoSave(TimeLimitDefine t, List wList) throws Exception {
		ArrayList<WarnStateInfo> warnStateInfos = new ArrayList<WarnStateInfo>();
		for(int i = 0 ;i< wList.size() ; i++ ){
			WarnStateInfo wsInfo = new WarnStateInfo();
			Map warnStateMap = (Map)wList.get(i);
			wsInfo.setTimeLimitDefineOid(t.getId());
			wsInfo.setAlarmTypeCode(t.getAlarmTypeCode());
			wsInfo.setAlarmTypeName(t.getAlarmTypeName());
			wsInfo.setPetitionNo((String)warnStateMap.get("petitionNo"));
			wsInfo.setRegionCode(t.getRegionCode());
			if(warnStateMap.get("DateDiff")==null||warnStateMap.get("DateDiff").equals("")){
				continue;
			}
			//预警原因
			if(t.getAlarmTypeCode().equals("HengXCL")){
				wsInfo.setAlarmReason(t.getActivityName()+"环节未办理");
			}else if(t.getAlarmTypeCode().equals("ZongXZF")){
				String transType = t.getTransType();//交换类型,1导出交换，2传输交换，3集中部署，4不交换	
				if (transType != null && !"".equals(transType.trim())) {
					if (!transType.equals("4")) {
						if(transType.equals("3")){
							wsInfo.setAlarmReason(t.getActivityName()+"未接收");
						}else{
							String sendFlag=t.getSendFlag();
							if (sendFlag!=null&&sendFlag.equals("0")) {//未传输
								wsInfo.setAlarmReason(t.getActivityName()+"未传输");
							}else if(sendFlag!=null&&sendFlag.equals("1")){//已传输
								String arriveFlag = t.getArriveFlag();//1已接受，0没接收
								if(arriveFlag!=null&&arriveFlag.equals("0")){
									wsInfo.setAlarmReason(t.getActivityName()+"已传输未接收");
								}
							}
						}
					}
				}else{
					wsInfo.setAlarmReason("未上报");
				}
			}else if(t.getAlarmTypeCode().equals("ZongXJS")){
				String dealTypeCode=t.getDealTypeCode();
				if("0302".equals(dealTypeCode))
					wsInfo.setAlarmReason("转办未接收");
				else if("0201".equals(dealTypeCode))
					wsInfo.setAlarmReason("交办要结果未接收");
				else if("0202".equals(dealTypeCode)) {
					wsInfo.setAlarmReason("交办要情况未接收");
				}
			}
			long timeDiff = getDateDiff(warnStateMap.get("DateDiff").toString());
			//预警状态信息保存
			if(t.getSeriousOverDays()!=0 && timeDiff >= t.getSeriousOverDays()){
				wsInfo.setAlarmActive(/*"YanZCS"*/AlarmType.YanZCS.toString());
				warnStateInfos.add(wsInfo);
			}else if(t.getModerateOverDays()!=0 && timeDiff >= t.getModerateOverDays()){
				wsInfo.setAlarmActive(/*"ZhongDCS"*/AlarmType.ZhongDCS.toString());
				warnStateInfos.add(wsInfo);
			}else if(t.getOverDays()!=0 && timeDiff >= t.getOverDays()){
				wsInfo.setAlarmActive(/*"ChaoS"*/AlarmType.ChaoS.toString());
				warnStateInfos.add(wsInfo);
			}else if(t.getWarnDays()!=0 && timeDiff >= t.getWarnDays()){
				wsInfo.setAlarmActive(/*"YuJ"*/AlarmType.YuJ.toString());
				warnStateInfos.add(wsInfo);
			}
		}
		warnStateInfoDao.insertWarnStateInfos(warnStateInfos);
	}
	/**
	 * 两日期差方法
	 * @author ljx
	 * @date 2011-07-11
	 * @throws ParseException 
	 * @return long
	 */
	public long getDateDiff(String d2) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = df.parse(d2);
		long timeDiff = System.currentTimeMillis()-date2.getTime();
		long days = timeDiff/(1000*60*60*24);
		return days;
	}
	/**
	 * 预警状态扫描方法,定时器调用的类方法
	 * @author ljx
	 * @date 2011-05-11
	 * @throws ParseException 
	 * @return void
	 * @throws ParseException 
	 */
	public void warnStateInfoScanMethod() throws Exception  {
		long olddate = System.currentTimeMillis();
		System.out.println("====================================扫描开始==="+olddate+"=================================");
		//删除掉信访件预警状态表中的所有数据
		warnStateInfoDao.deleteWarnStateInfoAll();
		//查找到时限设置表中所有有效标志为1也就是有效的设置信息
		List<TimeLimitDefine> tlList = timeLimitDefineManager.getTimeLimitDefine();
		for(int i = 0 ; i < tlList.size(); i++){
			TimeLimitDefine t = tlList.get(i);
			if (t.getRegionCode() == null || t.getRegionCode().trim().equals("")) {
				continue;
			}
			List wList = warnStateInfoDao.warnStateInfo(t);
			//预警状态信息保存方法
			if (wList != null && !wList.isEmpty()) {
				warnStateInfoSave(t, wList);
			}
		}
		System.out.println("====================================扫描结束======"+(olddate-System.currentTimeMillis())+"==============================");
	}
	
	
}
