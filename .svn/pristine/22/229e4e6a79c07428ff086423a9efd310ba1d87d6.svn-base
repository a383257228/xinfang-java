package com.sinosoft.xf.workflow.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.constants.UserConstants.ActivityNo;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.bean2json.Bean2jsonUtil;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateTraceInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 信访流转状态轨迹信息dao
 * @date 2011-09-13
 * @author wangxx
 */
@Repository
public class PetitionCirculationStateTraceInfoDao extends HibernateDao<PetitionCirculationStateTraceInfo, String> {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 通过活动编号和信访编号查询状态轨迹信息集合
	 * 说明：该方法用于查找之前一次的非批示状态轨迹信息
	 * @date 2011-09-14
	 * @author wangxx
	 * @param activityNo 活动编号
	 * @param petitionNo 信访编号
	 * @return 符合条件的对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionCirculationStateTraceInfo> listStateTrace(String activityNo, String petitionNo, String regionCode,String conditionType) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateTraceInfo.class);
		if(ActivityNo.ZCHFPS.toString().equals(activityNo)||ActivityNo.SJZTHZCPS.toString().equals(activityNo)||
				ActivityNo.SJZTHLCPS.toString().equals(activityNo)||ActivityNo.SJZTHLJPS.toString().equals(activityNo)){
			//暂存恢复批示902,903,904,906到100或者102
			criteria = criteria.add(Restrictions.ne("conditionCode", "0"));
			criteria = criteria.add(Restrictions.ne("nextActivityNo", activityNo));
		}else if(activityNo.equals(ActivityNo.LJLR.toString())){//了结录入的回退，不查询9和303传4到301的轨迹
			criteria = criteria.add(Restrictions.ne("conditionCode", "4"));
			criteria = criteria.add(Restrictions.eq("nextActivityNo", activityNo));
//		}else if(activityNo.equals(ActivityNo.DBPS.toString())&&"3".equals(conditionType)){//督办批示的回退修改
//			criteria = criteria.add(Restrictions.eq("nextActivityNo", ActivityNo.LJPS.toString()));
		}else {
			criteria = criteria.add(Restrictions.eq("nextActivityNo", activityNo));
		}
		criteria = criteria.add(Restrictions.ne("activityNo", activityNo));
		criteria = criteria.add(Restrictions.eq("petitionNo", petitionNo));
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();
	}
	/**
	 * 通过信访编号查询轨迹数据
	 * @date 2013-3-08 create ,2016-05-13 update 从QBC 方式 改变为SQL 方式
	 * @author zpj , update ：byg
	 * @return 符合条件的对象集合
	 */
	public List<PetitionCirculationStateTraceInfo> listByPetitionNo(String filterTxt, String filterValue,int start,int limit) throws Exception {
		List<Map<String ,Object>> list = new ArrayList<Map<String ,Object>>();
		StringBuffer sql=new StringBuffer("select  * from ( select rownumber() over() as rownumber_, row_.* from (  ");
		
		sql.append(" select * from PETITION_CIRCULATION_STATE_TRACE_INFO where 1=1 ");
		if (filterTxt != null && filterValue != null && !filterTxt.equals("") && !filterValue.equals("")) {
			String[] filterT = filterTxt.split(";");
			String[] filterV = filterValue.split(";");
			for(int i = 0; i < filterT.length; i++) {
				if ("petitionNo".equals(filterT[i].trim())) {
					sql.append("and petition_no  = '" + filterV[i]+"'  ");
				}
			}
		}else{
			return new ArrayList<PetitionCirculationStateTraceInfo>();
		}
		//加入根据regionCode判断查询权限
		sql=CodeSwitchUtil.selectByRegionCode(sql, Struts2Utils.getSessionAttribute("curRegionCode").toString());
		//按 操作日期排序
		sql.append(" order by create_date desc ");
		sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		list=jdbcTemplate.queryForList(sql.toString());
		list=this.StringToLowerCase(list);
		List<PetitionCirculationStateTraceInfo> circulationStateTraceInfos = new ArrayList<PetitionCirculationStateTraceInfo>();
		for (Map<String ,Object> map : list) {
			PetitionCirculationStateTraceInfo petitionCirculationStateTraceInfo = new PetitionCirculationStateTraceInfo();
			String jsonObject = Bean2jsonUtil.map2json(map);
			//2016-06-18 byg 在使用sql查询出的为List<map<str,obj>>需调用map2json之后json2Bean转换结果集，json2Bean方法默认只保留 年月日 不保留时分秒。现在需求加上时分秒，加入日期格式化。
			petitionCirculationStateTraceInfo = JsonUtils.json2Bean(jsonObject, PetitionCirculationStateTraceInfo.class, null, "yy-MM-dd HH:mm:ss");
			circulationStateTraceInfos.add(petitionCirculationStateTraceInfo);
		}
		return circulationStateTraceInfos;
	}
	
	public int listByPetitionNoSize(String filterTxt, String filterValue) throws Exception {
		StringBuffer sql=new StringBuffer("select count(*) from PETITION_CIRCULATION_STATE_TRACE_INFO where 1=1 ");
		if (filterTxt != null && filterValue != null && !filterTxt.equals("") && !filterValue.equals("")) {
			String[] filterT = filterTxt.split(";");
			String[] filterV = filterValue.split(";");
			for(int i = 0; i < filterT.length; i++) {
				if ("petitionNo".equals(filterT[i].trim())) {
					sql.append("and petition_no  = '" + filterV[i]+"'  ");
				}
			}
		}
		//加入根据regionCode判断查询权限
		sql=CodeSwitchUtil.selectByRegionCode(sql, Struts2Utils.getSessionAttribute("curRegionCode").toString());
		//按 操作日期排序
		return jdbcTemplate.queryForInt(sql.toString());
	}
	
	/**
	 * 用于将表字段转换为 类属性
	 * @author zhz 
	 * @date 2016-05-13
	 * @param attribute 表字段
	 * @param attributeType 表字段类型
	 * @return
	 */
	public String filedToAttribute(String attribute,String attributeType){
		String[] arr = attribute.toLowerCase().split("_");
		StringBuffer filed = new StringBuffer();
		if(attributeType!=null && attributeType.equals("table")){
			filed.append(String.valueOf(arr[0].charAt(0)).toUpperCase() + arr[0].substring(1));
		}else{
			filed.append(arr[0]);
		}
		for(int i=1;i<arr.length;i++){
			filed.append(String.valueOf(arr[i].charAt(0)).toUpperCase() + arr[i].substring(1));
		}
		return filed.toString();
	}

	/**
	 * 将map 中的 key表字段 字母下划线分隔 改为驼峰式 bean属性
	 * @author zhz 
	 * @date 2016-05-13
	 * @param listMap 需要处理的map
	 * @return 转换key后的 map
	 */
	public List<Map<String, Object>> StringToLowerCase(List<Map<String, Object>> listMap) {
		List<Map<String, Object>> listMap1 = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> mapObj : listMap) {
			Map<String, Object> mapObj1 = new HashMap<String, Object>();
			Set keys = mapObj.keySet();
			for (Object obj : keys) {
				String str = obj.toString();
				String toLowerCaseStr = this.filedToAttribute(str, "column");
				mapObj1.put(toLowerCaseStr, mapObj.get(str));
			}
			listMap1.add(mapObj1);
		}
		return listMap1;
	}

	/**
	 * 判断状态中是否存在指定状态
	 * @throws Exception
	 */
	public List<PetitionCirculationStateTraceInfo> listByBasicInfoOid(String petitionBasicInfoOid, String activityNo) {
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateTraceInfo.class);
		if (activityNo != null && !activityNo.equals(""))
			criteria.add(Restrictions.eq("activityNo", activityNo));
		criteria.add(Restrictions.eq("petitionBasicInfo.id", petitionBasicInfoOid));
		return criteria.list();
	}
	/**
	 * 通过活动编号和信访编号查询状态集合
	 * @date 2011-09-14
	 * @author wangxx
	 * @param activityNo 活动编号
	 * @param petitionNo 信访编号
	 * @return 状态集合
	 */
	public PetitionCirculationStateInfo getStateInfoByRegionCodeAndPetitionNo(
			String petitionNo,String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateInfo.class);
		criteria = criteria.add(Restrictions.eq("petitionNo", petitionNo));
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		return (PetitionCirculationStateInfo) criteria.uniqueResult();
	}
	
	/**
	 * 全局修改获取最后一条轨迹信息
	 * @date 2013-09-9
	 * @author zpj
	 * @param petitionNo 信访编号
	 * @return 符合条件的对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionCirculationStateTraceInfo>  getByBasicId(final String basicId){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateTraceInfo.class);
		criteria = criteria.add(Restrictions.eq("petitionBasicInfo.id", basicId));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.setFetchSize(1).setMaxResults(1).list();
	}
	
	/**
	 * 保存向上级回退轨迹
	 * @date 2016-05-31
	 * @author zhz
	 * @param sql 需要执行的SQL语句
	 */
	public void upBackMethod(String sql){
		jdbcTemplate.execute(sql);
	}
}
