package com.sinosoft.xf.petition.supervision.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.constants.UserConstants.AlarmType;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.util.CommonUtil;
import com.sinosoft.xf.util.TimeUtils;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.util.sql.SqlUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 时限dao
 * @author ljx 
 * @createDate 2011-04-27
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class TimeLimitDefineDao extends HibernateDao<TimeLimitDefine, String> {
    static private String transTableName = "petition_trans_deal_info";
    static private String assignTableName = "petition_assign_info";
	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 通过regionCode查询信息
	 * @param regionCode 区域编码
	 * @return 信息集合
	 */
	public List<TimeLimitDefine> getByRegionCode(String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(TimeLimitDefine.class);
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		return criteria.list();
	}
	/**
	 * 得到当前机构
	 * @author mengxy
	 * @createDate 2011-05-11
	 * @return 机构编码
	 */
//	public String getRegionCode(){
//		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
//		return person.getRegionCode();
//	}
	
	/**
	 * 查询纵向转发查询列表时查询转办或者交办信息的条件语句: 未归档
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * realNameReport 是否实名举报 1：是；0：否
	 * alarmActive 为统计图定制的sql串
	 * @return String sql
	 */
	public String getUnAchievedStateSQL(Map map,String ZFTypeTableName){
		String regionCode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		StringBuffer sql = new StringBuffer();
		sql.append("from petition_basic_info ");
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			sql.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			sql.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		}
		sql.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_deal_info.valid_flag = '0' ");
		sql.append("left join "+ZFTypeTableName+" on petition_deal_info.oid = "+ZFTypeTableName+".petition_deal_info_oid ");
//		sql.append("left join "+ZFTypeTableName+"  on "+ZFTypeTableName+".petition_no = petition_basic_info.petition_no and petition_basic_info.region_code = "+ZFTypeTableName+".region_code and "+ZFTypeTableName+".valid_flag = '0' ");
		sql.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF' ");  
		//add by lijun 2014-4-8 增加PETITION_CIRCULATION_STATE_INFO表连接
		sql.append("left join PETITION_CIRCULATION_STATE_INFO on petition_basic_info.oid = PETITION_CIRCULATION_STATE_INFO.petition_basic_info_oid ");
		//add by liub 2015-1-12 去除TO_region_code为XX政府的信件
		sql.append("left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = "+ZFTypeTableName+".to_region_code ");
		sql.append("where petition_basic_info.region_code ='"+regionCode+"' ");
		if(ZFTypeTableName.equals(transTableName)){
			sql.append(" and  petition_basic_info.deal_type_code='0302' ");
		}else{
			sql.append(" and  petition_basic_info.deal_type_code in('0201','0202') ");
		}
		sql.append(" and "+ZFTypeTableName+".From_Region_Code = '"+map.get("CurrRegion")+"' ");
		sql.append(" and  "+ZFTypeTableName+".To_Region_Code <> ''  and "+ZFTypeTableName+".back_date is null ");   		
		if((map.get("IsSend") != null) && (map.get("IsSend1") != null) ){ 	
			if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
				sql.append(" and ("+ZFTypeTableName+".send_date is null or ("+ZFTypeTableName+".send_date is not null and "+ZFTypeTableName+".Receive_Date is not null))");
			}else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
				sql.append(" and ("+ZFTypeTableName+".send_date is null or ("+ZFTypeTableName+".send_date is not null and "+ZFTypeTableName+".Receive_Date is null))");
			}
		}else if ((map.get("IsSend") != null) && (map.get("IsSend1") == null)) {
			sql.append(" and (" + ZFTypeTableName + ".send_date is not null)");
			if ((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)) {
				sql.append(" and (" + ZFTypeTableName + ".Receive_Date is not null)");
			} else if ((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)) {
				sql.append(" and (" + ZFTypeTableName + ".Receive_Date is null)");
			}
		} else if ((map.get("IsSend") == null) && (map.get("IsSend1") != null)) {
			sql.append(" and (" + ZFTypeTableName + ".send_date is null) ");
		}
		return sql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时查询转办或者交办信息的条件语句: 未归档---------------钻取用的 drill
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * alarmActive 为统计图定制的sql串
	 * @return String sql
	 * @throws ParseException 
	 */
	private String getUnAchievedStateSQL2(Map map,String ZFTypeTableName) throws ParseException{
		String regionCode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		StringBuffer sql = new StringBuffer();
		sql.append("from petition_basic_info ");
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			sql.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			sql.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		}
		//以下对petition_circulation_state_trace_info表的连接全部改为petitionPETITION_CIRCULATION_STATE_INFO表的连接，next_activity_no字段全部修改为activity_no字段
		sql.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_deal_info.valid_flag = '0' ");
		sql.append("left join "+ZFTypeTableName+"  on petition_deal_info.oid = "+ZFTypeTableName+".petition_deal_info_oid ");
		sql.append("left join PETITION_CIRCULATION_STATE_INFO  on  PETITION_CIRCULATION_STATE_INFO.petition_basic_info_oid = petition_basic_info.oid "); 
		sql.append("left join PETITION_CIRCULATION_STATE_INFO s on  s.petition_no = petition_basic_info.petition_no and "+ZFTypeTableName+".to_region_code = s.region_code "); 
		sql.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF'  ");
		//add by liub 2015-1-12 去除TO_region_code为XX政府的信件
		sql.append("left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = "+ZFTypeTableName+".to_region_code ");
		sql.append("where petition_basic_info.region_code ='"+regionCode+"'  ");
//		sql.append("and  petition_circulation_state_trace_info.petition_state_no = ");
//		sql.append("(select max(petition_state_no) md  from petition_circulation_state_trace_info  where petition_no=petition_basic_info.petition_no  and region_code = "+ZFTypeTableName+".to_region_code)  ");
		sql.append(" and "+ZFTypeTableName+".back_date is null  and "+ZFTypeTableName+".send_date is not null and  "+ZFTypeTableName+".receive_date is not null  ");
		sql.append(" and "+ZFTypeTableName+".From_Region_Code = '"+map.get("CurrRegion")+"' ");
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			sql.append(" and petition_basic_info.petition_date >=  '"+TimeUtils.string2Date(map.get("InputDate").toString())+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			sql.append(" and petition_basic_info.petition_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("InputDate1").toString()))+"' ");
		}
		if(map.get("objectClassName")!= null && !"".equals(map.get("objectClassName").toString())){
			sql.append(" and petition_basic_info.object_Class_code = '"+map.get("objectClassName")+"' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
			sql.append(" and "+ZFTypeTableName+".To_Region_Code = '"+map.get("ToRegionCode")+"' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			sql.append(" and petition_basic_info.issue_region_code = '"+map.get("RRegionCode")+"' ");
		}
		if(map.get("nextactivityno")!= null && !"".equals(map.get("nextactivityno").toString())){
			if("2".equals(map.get("nextactivityno").toString())){
				sql.append(" and s.activity_no in ( " +
						"'0000000100','0000000101','0000000102','0000000103','0000000104','0000000105','0000000107','0000000905' ) ");
			}else if("3".equals(map.get("nextactivityno").toString())){
				sql.append(" and s.activity_no not in ('0000000100','0000000101','0000000102','0000000103','0000000104','0000000105','0000000107','0000000216','0000000905','0000000303','0000000304','0000000305','0000000307','9')");
			}else if("4".equals(map.get("nextactivityno").toString())){
				sql.append(" and s.activity_no in( '0000000205','0000000216','0000000303','0000000304','0000000305','0000000307','9') ");
			}
		}
		return sql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时查询转办或者交办信息的条件语句: 未归档
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * @return String sql
	 * @throws ParseException 
	 */
	public String getUnAchievedStateConditionSQL(Map<String,Object> map,String ZFTypeTableName) throws ParseException{
		StringBuffer conditionSql = new StringBuffer();
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			conditionSql.append(" and petition_basic_info.petition_date >= '"+TimeUtils.string2Date(map.get("InputDate").toString())+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			conditionSql.append(" and petition_basic_info.petition_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("InputDate1").toString()))+"' ");
		}
		if(map.get("currPetitionNo")!= null && !"".equals(map.get("currPetitionNo").toString())){
			conditionSql.append(" and petition_basic_info.curr_petition_No like '%"+map.get("currPetitionNo")+"%' ");
		}
		if(map.get("XFPrtNo")!= null && !"".equals(map.get("XFPrtNo").toString())){
			conditionSql.append(" and petition_basic_info.petition_prt_no like '%"+map.get("XFPrtNo")+"%' ");
		}
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			conditionSql.append(" and petition_accused_info.accused_name like '%"+Encrypt.encrypt(map.get("AccusedName").toString())+"%' ");
		}
		if(map.get("objectClassName")!= null && !"".equals(map.get("objectClassName").toString())){
			conditionSql.append(" and petition_basic_info.object_class_code = '"+map.get("objectClassName")+"' ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			conditionSql.append(" and petition_accuser_info.accuser_name like '%"+Encrypt.encrypt(map.get("AccuserName").toString())+"%' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
			conditionSql.append(" and organ.org_code = '"+map.get("ToRegionCode")+"' ");
		}
		if(map.get("ToRegionName")!= null && !"".equals(map.get("ToRegionName").toString())){
			conditionSql.append(" and organ.org_name = '"+map.get("ToRegionName")+"' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			conditionSql.append(" and petition_basic_info.issue_region_code = '"+map.get("RRegionCode")+"' ");
		}
		if(null != map.get("realNameReport")&& !"".equals(map.get("realNameReport").toString())){
			conditionSql.append(" and petition_basic_info.real_Name_Report = '"+map.get("realNameReport")+"' ");
		}
		if(map.get("realNameReport") !=null ){
			conditionSql.append("and petition_basic_info.real_Name_Report ='"+map.get("realNameReport")+"' ");
		}
		if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
			if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
				conditionSql.append(" and  w.alarm_active is null ");
			}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){//ChaoS:2 ZhongDCS:'3' YanZCS:'4'
				conditionSql.append(" and ( w.alarm_active = '2' or w.alarm_active = '3' or w.alarm_active = '4' ) ");
			}else if("0".equals(map.get("ALARMACTIVE").toString().trim())){//正常
				conditionSql.append(" and  w.alarm_active is null "); 
			}else{
				conditionSql.append(" and  w.alarm_active = '"); 
				conditionSql.append(map.get("ALARMACTIVE"));
				conditionSql.append("'");
			}
		}
		return conditionSql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时查询交办信息的条件语句: 未归档
	 * @author mengxy
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * alarmActive 为统计图定制的sql串
	 * @createDate 2011-05-17
	 * @return String sql
	 */
	private String getUnAchievedSQL(Map map,String ZFTypeTableName){
		String regioncode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("FROM petition_basic_info  ");
		sql.append("inner join petition_basic_info lowerBasic on petition_basic_info.petition_no = lowerBasic.petition_no ");
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			sql.append(" left join petition_accused_info on lowerBasic.oid = petition_accused_info.petition_basic_info_oid ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			sql.append(" left join petition_accuser_info on lowerBasic.oid = petition_accuser_info.petition_basic_info_oid ");
		}
		sql.append(" left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_deal_info.valid_flag = '0' ");
		sql.append(" left join "+ZFTypeTableName+"  on petition_deal_info.oid = "+ZFTypeTableName+".petition_deal_info_oid ");
		sql.append(" left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		sql.append(" left join warn_state_info w on w.petition_no = petition_basic_info.petition_no  and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF' "); 
		//add by liub 2015-1-12 去除TO_region_code为XX政府的信件
		sql.append("left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = "+ZFTypeTableName+".to_region_code ");
		sql.append(" where  "+ZFTypeTableName+".back_date is null and "+ZFTypeTableName+".To_Region_code <> ''  ");
		sql.append(" and "+ZFTypeTableName+".From_Region_Code = '"+map.get("CurrRegion")+"' ");
		sql.append(" and lowerBasic.region_code = (select max(region_code) from petition_basic_info basic where basic.petition_no = lowerBasic.petition_no) ");
//		sql.append(" and substr(petition_basic_info.region_code,1,");
//		sql.append( regionFlag );
//		sql.append( ")||'" );
//		if(regionFlag!=0)
//			for(int i=0;i<12-regionFlag;i++){
//				sql.append('0');
//			}
//		sql.append("' = '" );
//		sql.append(regioncode);
//		sql.append("' ");
//		jbSql.append(" and petition_accuser_info.region_code = (select max(accuserTemp.region_code) from petition_accuser_info accuserTemp where petition_basic_info.petition_no = accuserTemp.petition_no) ");
//		jbSql.append(" and petition_accused_info.region_code = (select max(accusedTemp.region_code) from petition_accused_info accusedTemp where petition_basic_info.petition_no = accusedTemp.petition_no) ");
		if((map.get("IsSend") != null) && (map.get("IsSend1") != null) ){ 	
		    if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
		    	sql.append(" and ("+ZFTypeTableName+".send_date is null or ("+ZFTypeTableName+".send_date is not null and "+ZFTypeTableName+".Receive_Date is not null))");
		    }else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
		    	sql.append(" and ("+ZFTypeTableName+".send_date is null or ("+ZFTypeTableName+".send_date is not null and "+ZFTypeTableName+".Receive_Date is null))");
		    }
		}
		if((map.get("IsSend") != null)  && (map.get("IsSend1") == null) ){ 	
			sql.append(" and ("+ZFTypeTableName+".send_date is not null)");
		    if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
		    	sql.append(" and ("+ZFTypeTableName+".Receive_Date is not null)");
		    }else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
		    	sql.append(" and ("+ZFTypeTableName+".Receive_Date is null)");
		    }
		}else if((map.get("IsSend") == null) && (map.get("IsSend1") != null) ){
			sql.append(" and ("+ZFTypeTableName+".send_date is null)");
		}
		return sql.toString();
	}
	/**
	 * 查询纵向转发查询列表时关联的查询条件: 已归档 
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * @return String 已归档查询链接的条件
	 * @throws ParseException 
	 */
	private String getAchievedConditionSQL(Map map,String ZFTypeTableName) throws ParseException{
		String regioncode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer conditionSql = new StringBuffer();
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			conditionSql.append(" and petition_basic_info.petition_date >= '"+TimeUtils.string2Date(map.get("InputDate").toString())+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			conditionSql.append(" and petition_basic_info.petition_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("InputDate1").toString()))+"' ");
		}
		if(map.get("XFNo1")!= null && !"".equals(map.get("XFNo1").toString())){
			conditionSql.append(" and petition_basic_info.petition_No like '%"+map.get("XFNo1")+"%' ");
		}
		if(map.get("XFPrtNo")!= null && !"".equals(map.get("XFPrtNo").toString())){
			conditionSql.append(" and petition_basic_info.petition_Prt_No like '%"+map.get("XFPrtNo")+"%' ");
		}
		if(map.get("currPetitionNo")!= null && !"".equals(map.get("currPetitionNo").toString())){
			conditionSql.append(" and petition_basic_info.curr_petition_No like '%"+map.get("currPetitionNo")+"%' ");
		}
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			conditionSql.append(" and petition_accused_info.Accused_Name like '%"+Encrypt.encrypt(map.get("AccusedName").toString())+"%' ");
		}
		if(map.get("objectClassName")!= null && !"".equals(map.get("objectClassName").toString())){
			conditionSql.append(" and lowerBasic.object_class_code = '"+map.get("objectClassName")+"' ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			conditionSql.append(" and petition_accuser_info.Accuser_Name like '%"+Encrypt.encrypt(map.get("AccuserName").toString())+"%' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
			conditionSql.append(" and "+ZFTypeTableName+".To_Region_Code = '"+map.get("ToRegionCode")+"' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			conditionSql.append(" and lowerBasic.issue_region_code = '"+map.get("RRegionCode")+"' ");
		}
//		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
//			conditionSql.append(" and substr("+ZFTypeTableName+".To_Region_Code,1," );
//			conditionSql.append( regionFlag );
//			conditionSql.append( ")||'" );
//			if(regionFlag!=0)
//				for(int i=0;i<12-regionFlag;i++){
//					conditionSql.append('0');
//				}
//			conditionSql.append("' = '" );
//			conditionSql.append(map.get("ToRegionCode").toString());
//			conditionSql.append("' ");
//		}
//		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
//			conditionSql.append(" and substr(petition_basic_info.issue_Region_Code,1,");
//			conditionSql.append( regionFlag );
//			conditionSql.append( ")||'" );
//			if(regionFlag!=0)
//				for(int i=0;i<12-regionFlag;i++){
//					conditionSql.append('0');
//				}
//			conditionSql.append("' = '" );
//			conditionSql.append(map.get("RRegionCode").toString());
//			conditionSql.append("' ");
//		}
		return conditionSql.toString();
	}

	/**
	 * 纵向转发分页查询 查询项
	 * @param ZFTypeTableName
	 * @return
	 */
	private StringBuffer advancedSelect(String ZFTypeTableName,String basicTableName){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT petition_basic_info.oid,petition_basic_info.petition_title petitionTitle,");
		sql.append("petition_basic_info.petition_no xfNo,petition_basic_info.region_code,petition_basic_info.curr_petition_no currPetitionNo,");
		sql.append("petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
		sql.append(basicTableName+".issue_region_name regionName,"+basicTableName+".object_Class_Name objectClassName, ");
		sql.append("petition_basic_info.deal_type_code zFTypeCode, petition_basic_info.important_flag importantFlag, ");
		sql.append(ZFTypeTableName+".To_Region_name toRegionName,"+ZFTypeTableName+".input_date inputDate, ");
		sql.append(ZFTypeTableName+".Send_Date sendDate , ");
		sql.append(ZFTypeTableName+".Send_Date isSend , ");
		sql.append(ZFTypeTableName+".Receive_Date isReceive, ");
		sql.append(" case when w.alarm_active is null then '0' else w.alarm_active  end  alarmActive ,w.alarm_reason alarmReason "); 
		return sql;
	}
	
	/**
	 * 纵向转发分页查询
	 * @param authoritySql
	 * @param map
	 * @param ZFTypeTableName
 	 * @param isCount  true 统计中的查询，false查询具体项
	 * @param isNotAllFlag  true查询本级，false查询最下级
	 * @return
	 * @throws ParseException
	 */
	private StringBuffer advancedSql(String authoritySql,Map map,boolean isCount) throws ParseException{
		StringBuffer sql = new StringBuffer("");
		String ZFTypeCode = map.get("ZFTypeCode").toString();
		boolean isNotAllFlag = isNotAllFlag(map);
		String tebleName =transTableName;
		if("2".equals(ZFTypeCode)){
			tebleName = assignTableName;
		}
		if(isNotAllFlag){
			if(!isCount){
				sql.append(advancedSelect(tebleName,"petition_basic_info"));
			}
			sql.append(getUnAchievedStateSQL(map,tebleName));
			sql.append(getUnAchievedStateConditionSQL(map,tebleName));
		}else{
			if(!isCount){
				sql.append(advancedSelect(tebleName,"lowerBasic"));
			}
//			sql.append("select distinct * from (");
			sql.append(getUnAchievedSQL(map,tebleName));
			sql.append(getAchievedConditionSQL(map,tebleName));
//			sql.append(" ) as z ");
		}
		sql.append(authoritySql);
		return sql;
	}
	
	private boolean isNotAllFlag(Map map){
		boolean type = true;//显示本级信息
		if(map.get("IsCheck")!= null && "1".equals(map.get("IsCheck").toString())){
			type = false;//显示最下级信息
		}
		return type;
	}
	/**
	 * 返回count总数
	 * @author mengxy
	 * @date 2011-05-19
	 * @param String authoritySql 权限Sql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @return long
	 * @throws ParseException 
	 */
	public long advancedPageQueryCount(String authoritySql,Map map) throws ParseException {
		StringBuffer sql = new StringBuffer("");
//		boolean isNotAllFlag = isNotAllFlag(map);
		sql.append("select count(0) ");
		sql.append(advancedSql(authoritySql, map,true));
//		if(isNotAllFlag){
//		}else{
//			sql.append("select count(0) from (");
//			sql.append(advancedSql(authoritySql, map,true));
//			sql.append(" )");
//		}
		return jdbcTemplate.queryForLong(sql.toString());
	}
	/**
	 * 纵向转发分页查询
	 * @author mengxy
	 * @date 2011-05-19
	 * @param String authoritySql 权限Sql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param start 
	 * @param limit 
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> advancedPageQuery(String authoritySql,Map map,String sort,String dir,int start,int limit) throws ParseException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sql.append(advancedSql(authoritySql, map,false));
		//拼装排序字段
		if (sort != null && !"".equals(sort)&&dir != null && !"".equals(dir)) {
			sql.append(" order by ");
			sql.append(sort);
			if (dir.equalsIgnoreCase("asc")) {
				sql.append(" asc ");
			}else {
				sql.append(" desc ");
			}
		}else{
			sql.append(" order by xfNo desc");
		}
		sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sql.toString());
	}

	private StringBuffer advancedDrillSql(String authoritySql,Map map,boolean isCount) throws ParseException{
		String tebleName =transTableName;
		if("2".equals(map.get("ZFTypeCode"))){
			tebleName = assignTableName;
		}
		StringBuffer sql = new StringBuffer("");
		if(!isCount){
			sql.append(advancedSelect(tebleName,"petition_basic_info"));
		}
		sql.append(getUnAchievedStateSQL2(map, tebleName));
		sql.append(authoritySql);
		return sql;
	}
	/**
	 * 纵向转发分页查询    钻取
	 * @author ljx
	 * @date 2011-07-21
	 * @param String authoritySql 权限Sql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param start 
	 * @param limit 
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> advancedPageQueryDrill(String authoritySql,Map map,String sort,String dir,int start,int limit) throws ParseException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sql.append(advancedDrillSql(authoritySql, map,false));
		//拼装排序字段
		if (sort != null && !"".equals(sort)&&dir != null && !"".equals(dir)) {
			sql.append(" order by ");
			sql.append(sort);
			if (dir.equalsIgnoreCase("asc")) {
				sql.append(" asc ");
			}else {
				sql.append(" desc ");
			}
		} else {
			sql.append(" order by xfNo desc");
		}
		sql.append(" ) as row_ ) as temp_  where rownumber_ > " + start + " and rownumber_ <= " + (start + limit));
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 返回count总数
	 * @author mengxy
	 * @date 2011-05-19
	 * @param String authoritySql 权限Sql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @return long
	 * @throws ParseException 
	 */
	public long advancedPageQueryDrillCount(String authoritySql, Map map) throws ParseException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select count(0)  ");
		sql.append(advancedDrillSql(authoritySql, map,true));
		return jdbcTemplate.queryForLong(sql.toString());
	}

	/**
	 * 拼装纵向转发“不同下级转发信访量对比”柱形图需要的数据集的方法
	 * @author mengxy
	 * @date 2011-05-25
	 * @param String authoritySql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String doRegionChart(String authoritySql,Map map) throws ParseException {
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			String tebleName =transTableName;
			if("2".equals(ZFTypeCode)){
				tebleName = assignTableName;
			}
			StringBuilder codeStr = new StringBuilder(); 
			codeStr.append(" select organ.org_name regionName ,"+tebleName+".RECEIVE_DATE RECEIVE,count(0) c ");
			codeStr.append(getUnAchievedStateSQL(map, tebleName));
			codeStr.append(getUnAchievedStateConditionSQL(map, tebleName));
			codeStr.append(authoritySql);
			codeStr.append(" group by  organ.org_name,"+tebleName+".RECEIVE_DATE order by organ.org_name desc ");
			
			List<Map<String,Object>> list = jdbcTemplate.queryForList(codeStr.toString());
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='不同下级转发信访量对比' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='区域名称' yAxisName='信访量' >");
			if(list.isEmpty()){
				return strXML.append("</chart>").toString();
			}
			strXML.append("<categories>");
			String newValue = "";
			StringBuffer strXMLyCoun = new StringBuffer();
			StringBuffer strXMLwCoun = new StringBuffer();
			String w = "0";//未接收
			String y = "0";//已接收
			String	oldValue = replaceChart(list.get(0).get("regionname"));
			int t = 0;
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = list.get(i);
				newValue = replaceChart(mapXML.get("regionname"));
				if("".equals(newValue)){
					continue;
				}
				if( !oldValue.equals(newValue) ){
					strXML.append("<category label='"+ oldValue.trim() +"' />");
					if("1".equals(ZFTypeCode)){
						strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='"+w+"' link='JavaScript:verTransRegion("+t+",1);' />");
						strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='"+y+"' link='JavaScript:verTransRegion("+t+",2);' />");
					}else if("2".equals(ZFTypeCode)){
						strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='"+w+"' link='JavaScript:verAssignRegion("+t+",1);' />");
						strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='"+y+"' link='JavaScript:verAssignRegion("+t+",2);' />");
					}
					w = "0";
					y = "0";
					oldValue = newValue;
					t++;
				}
				if(mapXML.get("receive")==null||"".equals(mapXML.get("receive").toString().trim())){
					w = String.valueOf(Integer.parseInt(w) +Integer.parseInt(mapXML.get("c").toString()));
				}else {
					y = String.valueOf(Integer.parseInt(y) +Integer.parseInt(mapXML.get("c").toString()));
				}
			}
			strXML.append("<category label='"+ newValue.trim() +"' />");
			if("1".equals(ZFTypeCode)){
				strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='"+w+"' link='JavaScript:verTransRegion("+t+",1);' />");
				strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='"+y+"' link='JavaScript:verTransRegion("+t+",2);' />");
			}else if("2".equals(ZFTypeCode)){
				strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='"+w+"' link='JavaScript:verAssignRegion("+t+",1);' />");
				strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='"+y+"' link='JavaScript:verAssignRegion("+t+",2);' />");
			}
			strXML.append("</categories>");			
			strXML.append("<dataset seriesName='未接收信访量'  showValues='0'>");
			strXML.append(strXMLwCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='已接收信访量'  showValues='0'>");
			strXML.append(strXMLyCoun);
			strXML.append("</dataset>");
			strXML.append("</chart>");
			return strXML.toString();
	}

	/**
	 * 拼装纵向转发“不同下级未接收时限对比”柱形图需要的数据集的方法
	 * @author mengxy
	 * @date 2011-05-27
	 * @param String authoritySql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String doReplyChart(String authoritySql,Map map) throws Exception {
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			String tebleName =transTableName;
			if("2".equals(ZFTypeCode)){
				tebleName = assignTableName;
			}
			StringBuilder codeStr = new StringBuilder(); 
			int hasReceivedAndUnReceived = 0;//是否是同时插已接收未接收，0否，1shi
			//add by lijun 2014-10-29 如果同时查已接受和未接收，就要采用以下这种sql语句
			if(map.get("IsReceive")!=null&&map.get("IsReceive1")!=null){
				hasReceivedAndUnReceived = 1;
				codeStr.append(" select count(0) coun,temp.To_Region_Name regionName ,temp.ALARM_ACTIVE warn ");
				codeStr.append(",temp.hasReceived from ( ");
				codeStr.append("select petition_trans_deal_info.*,w.*, case when petition_trans_deal_info.receive_date is null then 0 else 1 end  hasReceived ");//0未接收，1已接受
			}else{
				codeStr.append(" select count(0) coun,"+tebleName+".To_Region_Name regionName ,w.ALARM_ACTIVE warn ");
			}
			codeStr.append(getUnAchievedStateSQL(map, tebleName));
			codeStr.append(getUnAchievedStateConditionSQL(map, tebleName));
			codeStr.append(authoritySql);
			//add by lijun 2014-10-29 如果同时查已接受和未接收，就要采用以下这种sql语句
			if(map.get("IsReceive")!=null&&map.get("IsReceive1")!=null){
				codeStr.append(" ) as temp ");
				codeStr.append(" group by temp.To_Region_Name , temp.ALARM_ACTIVE, temp.hasReceived ");
				codeStr.append(" order by temp.To_Region_Name desc");
			}else{
				codeStr.append(" group by "+tebleName+".To_Region_Name ,w.ALARM_ACTIVE ");
				codeStr.append(" order by "+tebleName+".To_Region_Name desc");
			}
			List<Map<String,Object>> list = jdbcTemplate.queryForList(codeStr.toString());
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='不同下级未接收时限对比' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='区域名称' yAxisName='信访量' >");
			if(list.isEmpty()){
				return strXML.append("</chart>").toString();
			}
			strXML.append("<categories>");
			String newValue = "";
			StringBuffer strXMLweiCoun = new StringBuffer();
			StringBuffer strXMLzcCoun = new StringBuffer();
			StringBuffer strXMLyjCoun = new StringBuffer();
			StringBuffer strXMLcsCoun = new StringBuffer();
			int wei = 0;//未接收
			int zc = 0;//正常
			int yj = 0;//预警
			int cs = 0;//超时
			int yjs = 0;//已接收
			String	oldValue = replaceChart(list.get(0).get("regionName"));
			int t = 0;
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = list.get(i);
				newValue = replaceChart(mapXML.get("regionName"));
				if("".equals(newValue)){
					continue;
				}
				if( !oldValue.equals(newValue) ){
					//add by lijun 2014-10-29 如果同时查已接受和未接收,正常 = 已接收+未接收，未接收=未接收+超时+预警
					if(map.get("IsReceive")!=null&&map.get("IsReceive1")!=null){
						zc = wei + yjs;
						wei = wei +yj + cs;
					}else{//否则，未接收=正常+预警+超时
						wei = zc + yj + cs;
					}
					strXML.append("<category label='"+ oldValue +"' />");
					if("1".equals(ZFTypeCode)){
						strXMLweiCoun.append("<set rname = '"+oldValue+"' value='"+wei+"' link='JavaScript:reply("+t+",1);' />");
						strXMLzcCoun.append("<set rname = '"+oldValue+"' value='"+zc+"' link='JavaScript:reply("+t+",2,"+hasReceivedAndUnReceived+");' />");
						strXMLyjCoun.append("<set rname = '"+oldValue+"' value='"+yj+"' link='JavaScript:reply("+t+",3);' />");
						strXMLcsCoun.append("<set rname = '"+oldValue+"' value='"+cs+"' link='JavaScript:reply("+t+",4);' />");
					}else if("2".equals(ZFTypeCode)){
						strXMLweiCoun.append("<set rname = '"+oldValue+"' value='"+wei+"' link='JavaScript:replyAssign("+t+",1);' />");	
						strXMLzcCoun.append("<set rname = '"+oldValue+"' value='"+zc+"' link='JavaScript:replyAssign("+t+",2,"+hasReceivedAndUnReceived+");' />");
						strXMLyjCoun.append("<set rname = '"+oldValue+"' value='"+yj+"' link='JavaScript:replyAssign("+t+",3);' />");
						strXMLcsCoun.append("<set rname = '"+oldValue+"' value='"+cs+"' link='JavaScript:replyAssign("+t+",4);' />");
					}
					wei = 0;
					zc = 0;
					yj = 0;
					cs = 0;
					yjs = 0;
					oldValue = newValue;
					t++;
				}
				//add by lijun 2014-10-29 如果同时查已接受和未接收,已接收=已接收，未接收=未接收
				if(map.get("IsReceive")!=null&&map.get("IsReceive1")!=null){
					if(null == mapXML.get("warn")){//如果没有预警状态
						if("1".equals(mapXML.get("hasReceived").toString())){
							yjs += Integer.parseInt(mapXML.get("coun").toString());
						}else if("0".equals(mapXML.get("hasReceived").toString())){
							wei += Integer.parseInt(mapXML.get("coun").toString());
						}
					}else if(AlarmType.YuJ.toString().equals(mapXML.get("warn").toString()))
						yj += Integer.parseInt(mapXML.get("coun").toString());
					else  if(AlarmType.ChaoS.toString().equals(mapXML.get("warn").toString())){
						cs += Integer.parseInt(mapXML.get("coun").toString());
					}
				}else{//
					if(null == mapXML.get("warn")){
						zc += Integer.parseInt(mapXML.get("coun").toString());
					}else if(AlarmType.YuJ.toString().equals(mapXML.get("warn").toString()))
						yj += Integer.parseInt(mapXML.get("coun").toString());
					else 
						cs += Integer.parseInt(mapXML.get("coun").toString());
				}
			}
			//add by lijun 2014-10-29 如果同时查已接受和未接收,正常 = 已接收+未接收，未接收=未接收+超时+预警
			if(map.get("IsReceive")!=null&&map.get("IsReceive1")!=null){
				zc = wei + yjs;
				wei = wei +yj + cs;
			}else{//否则，未接收=正常+预警+超时
				wei = zc + yj + cs;
			}
			strXML.append("<category label='"+ newValue.trim() +"' />");
			if("1".equals(ZFTypeCode)){
				strXMLweiCoun.append("<set rname = '"+oldValue+"' value='"+wei+"' link='JavaScript:reply("+t+",1);' />");
				strXMLzcCoun.append("<set rname = '"+oldValue+"' value='"+zc+"' link='JavaScript:reply("+t+",2,"+hasReceivedAndUnReceived+");' />");
				strXMLyjCoun.append("<set rname = '"+oldValue+"' value='"+yj+"' link='JavaScript:reply("+t+",3);' />");
				strXMLcsCoun.append("<set rname = '"+oldValue+"' value='"+cs+"' link='JavaScript:reply("+t+",4);' />");
			}else if("2".equals(ZFTypeCode)){
				strXMLweiCoun.append("<set rname = '"+oldValue+"' value='"+wei+"' link='JavaScript:replyAssign("+t+",1);' />");	
				strXMLzcCoun.append("<set rname = '"+oldValue+"' value='"+zc+"' link='JavaScript:replyAssign("+t+",2,"+hasReceivedAndUnReceived+");' />");
				strXMLyjCoun.append("<set rname = '"+oldValue+"' value='"+yj+"' link='JavaScript:replyAssign("+t+",3);' />");
				strXMLcsCoun.append("<set rname = '"+oldValue+"' value='"+cs+"' link='JavaScript:replyAssign("+t+",4);' />");
			}
			strXML.append("</categories>");
			strXML.append("<dataset seriesName='未接收信访量' color='AFD8F8' showValues='0'>");
			strXML.append(strXMLweiCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='正常信访量' color='F6BD0F' showValues='0'>");
			strXML.append(strXMLzcCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='预警信访量' color='8BBA00' showValues='0'>");
			strXML.append(strXMLyjCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='超时信访量' color='F1683C' showValues='0'>");
			strXML.append(strXMLcsCoun);
			strXML.append("</dataset>");
			strXML.append("</chart>");
			return strXML.toString();
	}
	/**
	 * 横向处理查询项
	 * @return
	 */
	private String horizWarningPageQuerySqlBefore(){
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select temp.petition_prt_no, case when warn_state_info.alarm_active is null then '0' else warn_state_info.alarm_active end alarm_active,");
		strSQL.append("warn_state_info.alarm_reason alarm_reason,temp.* from ( ");
		strSQL.append("select petition_basic_info.petition_prt_no,petition_basic_info.oid,petition_basic_info.petition_title petitionTitle,petition_basic_info.region_code region_code,petition_basic_info.petition_No XFNO,petition_basic_info.curr_petition_no CURRPETITIONNO,petition_basic_info.petition_Date  XFDATE,petition_basic_info.petition_type_name XFTYPE," );
		strSQL.append("petition_basic_info.petition_source_name XFSOURCE ,petition_basic_info.petition_class_name XFCLASS, ");
		strSQL.append("petition_basic_info.issue_region_name TOREGIONCODE, petition_basic_info.curr_dept_name CURRDEPTNAME, ");
		strSQL.append("petition_basic_info.important_flag IMPORTANTFLAG, ");
//		strSQL.append("petition_issue_info.issue_type_name WT,petition_issue_info.issue_type_code WTCode,  ");
		strSQL.append("petition_basic_info.object_class_name objectClassName,petition_circulation_state_info.activity_name ACTIVITY ,petition_basic_info.deal_type_name DEALTYPE");
		return strSQL.toString();
	}
	/**
	 * 查询纵向接收查询列表: 返回列表总数
	 * @author mengxy
	 * @createDate 2011-06-02
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型
				   CurrRegion 当前用户所属机构 作用于集中部署时
	 * @return long
	 * @throws ParseException 
	 */
	public long getVerReceiveWarningWListCount(Map map) throws Exception {
		String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
		StringBuffer sql = new StringBuffer("");
		sql.append("select count(0) from ( "); 
		if("1".equals(ZFTypeCode)){//转办
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_trans_deal_info_temp");
		}else if("2".equals(ZFTypeCode)){//交办
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_assign_info_temp");
		}else{
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_trans_deal_info_temp");
			sql.append(" union  all ");
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_assign_info_temp");
		}
		sql.append(")");
		return jdbcTemplate.queryForLong(sql.toString());
	}
	private StringBuffer verReceiveWarningWListConditionSQL(Map map,StringBuffer sql,String tableName) throws ParseException{
		sql.append("select basictemp.petition_no xfNo, basictemp.important_flag IMPORTANTFLAG,basictemp.curr_petition_no CURRPETITIONNO,trim(transtemp.deal_type_name) ZFTYPECODE, ");
		sql.append("transtemp.send_date sendDate,"); 
		sql.append("(case when w.alarm_active is null then '0' else w.alarm_active end) ALARMACTIVE "); 
		sql.append(",w.alarm_reason  alarmReason "); 
		sql.append("from petition_basic_info_temp basictemp ");
		sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
		sql.append("left join "+tableName+" transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
		sql.append("left join warn_state_info w on w.petition_no = basictemp.petition_no and basictemp.region_code = w.region_code and w.alarm_type_code = 'ZongXJS' "); 
		sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
		sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
		if( map.get("XFPrtNo") != null	&& !map.get("XFPrtNo").toString().equals("")){
			sql.append(" and basictemp.petition_prt_no like '%"+map.get("XFPrtNo").toString()+"%'");
		}
		if(map.get("realNameReport") != null){
			sql.append(" and basictemp.real_Name_Report = '"+map.get("realNameReport").toString()+"'");
		}
		if( map.get("ALARMACTIVE") != null	&& !map.get("ALARMACTIVE").toString().equals("")){
			if(map.get("ALARMACTIVE").toString().equals("0")){
				sql.append(" and w.alarm_active is null ");
			}else{
				sql.append(" and w.alarm_active = '");
				sql.append(map.get("ALARMACTIVE").toString());
				sql.append("'");
			}
		}
		
		if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
			sql.append(" and transtemp.send_date >= '"+TimeUtils.string2Date(map.get("sendDate").toString()) +"'") ;
		}
		if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
			sql.append(" and transtemp.send_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
		}
		return sql;
	}
	/**
	 * 查询纵向接收查询列表: 返回列表查询结果
	 * @author mengxy
	 * @createDate 2011-06-02
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型
	 * @param sort 
	 * @param dir 
	 * @param start 
	 * @param limit 
	 * @return List<Map>
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getVerReceiveWarningWList(Map map,String sort,String dir,int start,int limit) throws ParseException {
		StringBuffer sql = new StringBuffer("");
		String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
		sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		if("1".equals(ZFTypeCode)){//转办
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_trans_deal_info_temp");
		}else if("2".equals(ZFTypeCode)){//交办
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_assign_info_temp");
		}else{
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_trans_deal_info_temp");
			sql.append(" union all ");
			sql = verReceiveWarningWListConditionSQL(map,sql,"petition_assign_info_temp");
		}
		//拼装排序字段
		if(sort!=null && !"".equals(sort)&&dir!=null&&!"".equals(dir)){
			sql.append(" order by ");
			sql.append(sort);
			if("asc".equalsIgnoreCase(dir)){
				sql.append(" asc ");
			}else {
				sql.append(" desc ");
			}
		}else{
			sql.append(" order by xfNo desc ");
		}
		sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 查询纵向接收查询列表: 返回纵向接收“未接收操作时限对比分析”柱形图需要的XML数据集
	 * @author mengxy
	 * @createDate 2011-06-07
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
	 * @return
	 * @throws ParseException 
	 */
	public String doReceiveChart(Map map) throws ParseException {
			StringBuffer sql = new StringBuffer(); 
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			sql.append(" select count(0) c,alarmActive  from ( ");
			if("1".equals(ZFTypeCode)){
				sql = verReceiveWarningWListConditionSQL(map,sql,"petition_trans_deal_info_temp");
			}else if("2".equals(ZFTypeCode)){
				sql = verReceiveWarningWListConditionSQL(map,sql,"petition_assign_info_temp");
			}else{
				sql = verReceiveWarningWListConditionSQL(map,sql,"petition_trans_deal_info_temp");
				sql.append(" union all ");
				sql = verReceiveWarningWListConditionSQL(map,sql,"petition_assign_info_temp");
			}
			sql.append(" ) group by alarmactive");
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='未接收时限对比' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='预警时限' yAxisName='信访量' >");
			if(list.size()<1){
				return  strXML.append("<set yujing='YuJ' label='预警' value='0' />")
						.append("<set yujing='ChaoS' label='超时' value='0' />")
						.append("<set yujing='ZhongDCS' label='中度超时' value='0' />")
						.append("<set yujing='YanZCS' label='严重超时' value='0' />")
						.append("</chart>").toString();
			}
			String yuJ = "0";
			String chaoS = "0";
			String zhongDCS = "0";
			String yanZCS = "0";
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = (Map) list.get(i);
				if(/*"YanZCS"*/AlarmType.YanZCS.toString().equals(mapXML.get("alarmActive"))){
//					yanZCS = mapXML.get("c").toString();
					yanZCS = String.valueOf(Integer.parseInt(yanZCS) +Integer.parseInt(mapXML.get("c").toString()));
				}else if(/*"ZhongDCS"*/AlarmType.ZhongDCS.toString().equals(mapXML.get("alarmActive"))){
//					zhongDCS = mapXML.get("c").toString();
					zhongDCS = String.valueOf(Integer.parseInt(zhongDCS) +Integer.parseInt(mapXML.get("c").toString()));
				}else if(/*"ChaoS"*/AlarmType.ChaoS.toString().equals(mapXML.get("alarmActive"))){
//					chaoS = mapXML.get("c").toString();
					chaoS = String.valueOf(Integer.parseInt(chaoS) +Integer.parseInt(mapXML.get("c").toString()));
				}else if(/*"YuJ"*/AlarmType.YuJ.toString().equals(mapXML.get("alarmActive"))){
//					yuJ = mapXML.get("c").toString();
					yuJ = String.valueOf(Integer.parseInt(yuJ) +Integer.parseInt(mapXML.get("c").toString()));
				}
			}
			strXML.append("<set yujing='YuJ' label='预警' value='"+yuJ+"' link='JavaScript:wReceive(1);' />");
			strXML.append("<set yujing='ChaoS' label='超时' value='"+chaoS+"' link='JavaScript:wReceive(2);' />");
			strXML.append("<set yujing='ZhongDCS' label='中度超时' value='"+zhongDCS+"' link='JavaScript:wReceive(3);' />");
			strXML.append("<set yujing='YanZCS' label='严重超时' value='"+yanZCS+"' link='JavaScript:wReceive(4);' />");
			strXML.append("</chart>");
			return strXML.toString();
	}
	/**
	 * 为纵向接收列表查询拼接sql 返回条件语句
	 * @author mengxy
	 * @createDate 2011-06-02
	 * @param map sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型
	 * @return
	 * @throws ParseException 
	 */
	public String getVerReceiveWarningWListConditionSQL(Map map,String zfType) throws ParseException{
		StringBuffer conditionSql = new StringBuffer();
		if( map.get("XFPrtNo") != null	&& !map.get("XFPrtNo").toString().equals("")){
			conditionSql.append(" and basic.petition_prt_no = '");
			conditionSql.append(map.get("XFPrtNo").toString());
			conditionSql.append("'");
		}
		if( map.get("ALARMACTIVE") != null	&& !map.get("ALARMACTIVE").toString().equals("")){
			conditionSql.append(" and w.alarm_active = '");
			conditionSql.append(map.get("ALARMACTIVE").toString());
			conditionSql.append("'");
		}
		
		if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
			conditionSql.append(" and "+zfType+".send_date >= '"+TimeUtils.string2Date(map.get("sendDate").toString()) +"'") ;
		}
		if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
			conditionSql.append(" and "+zfType+".send_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
		}
		return conditionSql.toString();
	}
	/**
	 * 查询纵向接收查询列表: 返回纵向接收“已接收未接收对比分析”柱形图需要的XML数据集
	 * @author ljx
	 * @createDate 2011-07-11
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
	 * @return
	 * @throws ParseException 
	 */
	public String doYWReceiveChart(String authoritySql,Map map) throws ParseException {
			StringBuilder codeStr = new StringBuilder(); 
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			if("1".equals(ZFTypeCode)){ 
				codeStr.append(" select sum(temp1.c) count,'yi' receivetype from ( ");
				codeStr.append("select count(0) c 	from petition_trans_deal_info_temp  ");
				codeStr.append("where petition_trans_deal_info_temp.receive_date is not null and  petition_trans_deal_info_temp.back_date is null and petition_trans_deal_info_temp.send_date is not null ");
				codeStr.append(" and petition_trans_deal_info_temp.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(authoritySql);
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and petition_trans_deal_info_temp.receive_date >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and petition_trans_deal_info_temp.receive_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				String zfType = "petition_trans_deal_info_temp";
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,zfType));
				codeStr.append(" ) temp1");
				codeStr.append(" union all select sum(temp2.c) count,'wei' receivetype from ( ");
				codeStr.append("select count(0) c from petition_basic_info_temp basic ");
				codeStr.append("inner join petition_deal_info_temp deal on basic.oid = deal.petition_basic_info_temp_oid and deal.valid_flag = '0' ");
				codeStr.append("inner join petition_trans_deal_info_temp trans on deal.oid = trans.petition_deal_info_temp_oid ");
				codeStr.append("where trans.receive_date is null and  trans.back_date is null and trans.send_date is not null ");
				codeStr.append(" and trans.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,"trans"));
				codeStr.append(") temp2");
			}else if("2".equals(ZFTypeCode)){
				codeStr.append(" select sum(temp1.c) count,'yi' receivetype from ( ");
				codeStr.append(" select count(0) c  from petition_assign_info_temp ");
				codeStr.append("where petition_assign_info_temp.receive_date is not null and  petition_assign_info_temp.back_date is null and petition_assign_info_temp.send_date is not null  ");
				codeStr.append(" and petition_assign_info_temp.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(authoritySql);
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and petition_assign_info_temp.receive_date >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and petition_assign_info_temp.receive_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				String zfType = "petition_assign_info_temp";
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,zfType));
				codeStr.append(") temp1");
				codeStr.append(" union all select sum(temp2.c) count,'wei' receivetype from ( ");
				codeStr.append(" select count(0) c  from petition_basic_info_temp basic "); 
				codeStr.append("inner join petition_deal_info_temp deal on basic.oid = deal.petition_basic_info_temp_oid and deal.valid_flag = '0' ");
				codeStr.append("inner join petition_assign_info_temp assign on deal.oid = assign.petition_deal_info_temp_oid ");
				codeStr.append(" where assign.receive_date is null and assign.back_date is null and assign.send_date is not null ");
				codeStr.append(" and assign.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,"assign"));
				codeStr.append(") temp2");
			}else{
				codeStr.append("select sum(temp1.c) count,'yi' receivetype from (  ");
				codeStr.append("select count(0) c	 from petition_trans_deal_info_temp "); 
				codeStr.append("where petition_trans_deal_info_temp.receive_date is not null and  petition_trans_deal_info_temp.back_date is null and petition_trans_deal_info_temp.send_date is not null  ");
				codeStr.append(" and petition_trans_deal_info_temp.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(authoritySql);
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and petition_trans_deal_info_temp.receive_date >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and petition_trans_deal_info_temp.receive_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				String zfType = "petition_trans_deal_info_temp";
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,zfType));
				codeStr.append(" union all select count(0) c	 from petition_assign_info_temp  "); 
				codeStr.append("where petition_assign_info_temp.receive_date is not null and  petition_assign_info_temp.back_date is null and petition_assign_info_temp.send_date is not null  ");
				codeStr.append(" and petition_assign_info_temp.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(authoritySql);
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and petition_assign_info_temp.receive_date >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and petition_assign_info_temp.receive_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,"petition_assign_info_temp"));
				codeStr.append(") temp1 ");
				codeStr.append("union  all select sum(temp2.c) count,'wei' receivetype from ( ");
				codeStr.append("select count(0) c from petition_basic_info_temp basic ");
				codeStr.append("inner join petition_deal_info_temp deal on basic.oid = deal.petition_basic_info_temp_oid and deal.valid_flag = '0' ");
				codeStr.append("inner join petition_trans_deal_info_temp trans on deal.oid = trans.petition_deal_info_temp_oid ");
				codeStr.append("where trans.receive_date is null and  trans.back_date is null and trans.send_date is not null ");
				codeStr.append(" and trans.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,"trans"));
				codeStr.append(" union all select count(0) c  from petition_basic_info_temp basic ");
				codeStr.append("inner join petition_deal_info_temp deal on basic.oid = deal.petition_basic_info_temp_oid and deal.valid_flag = '0' ");
				codeStr.append("inner join petition_assign_info_temp assign on deal.oid = assign.petition_deal_info_temp_oid ");
				codeStr.append("where assign.receive_date is null and assign.back_date is null and assign.send_date is not null ");
				codeStr.append(" and assign.to_region_code = '"+map.get("CurrRegion")+"' ");
				codeStr.append(getVerReceiveWarningWListConditionSQL(map,"assign"));
				codeStr.append(") temp2");
			}
			List<Map<String, Object>> list = jdbcTemplate.queryForList(codeStr.toString());
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='未接收已接收对比分析' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='未接收已接收' yAxisName='信访量' >");
			if(list.isEmpty()){
				return strXML.append("</chart>").toString();
			}
			String wjs = "0";
			String yjs = "0";
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = (Map) list.get(i);
				if(mapXML.get("receivetype").equals("wei")){
					wjs = mapXML.get("count").toString();
				}else if(mapXML.get("receivetype").equals("yi")){
					yjs = mapXML.get("count").toString();
				}
			}
			strXML.append("<set label='未接收' value='"+wjs+"'link='JavaScript:ywReceive(1);' />");
			strXML.append("<set label='已接收' value='"+yjs+"'  />");
			strXML.append("</chart>");
			return strXML.toString();
	}
	
	/**
	 * 查询纵向接收查询列表: 返回纵向接收“已接收后续处理对比分析”柱形图需要的XML数据集
	 * @author ljx
	 * @createDate 2011-07-11
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
	 * @return
	 * @throws ParseException 
	 */
	public String doYReceiveChart(String authoritySql,Map map) throws ParseException {
			StringBuilder codeStr = new StringBuilder(); 
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			if("1".equals(ZFTypeCode)){
				codeStr.append(" select count(0) c,temp.ACTIVITY aname,temp.activityno ano,warn_state_info.alarm_active w from ( ");
				codeStr.append(" SELECT xfbzbinfotemp.xfno XFNO,XFWACTIVITY.ACTIVITYNO activityno,XFWACTIVITY.activityname ACTIVITY  ");
				codeStr.append(" from  xfbzbinfotemp xfbzbinfotemp,xfwxfstate xfwxfstate ");
				codeStr.append(" left outer join  XFWACTIVITY XFWACTIVITY on XFWACTIVITY.activityno=xfwxfstate.activityno ");
				codeStr.append(" where  xfbzbinfotemp.xfno = xfwxfstate.xfno  ");
				codeStr.append(" and xfbzbinfotemp.senddate is not null and xfbzbinfotemp.receivedate is not null  ");
				codeStr.append(authoritySql);
				if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.senddate >= '"+TimeUtils.string2Date(map.get("sendDate").toString()) +"'") ;
				}
				if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.senddate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
				}
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.receivedate >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.receivedate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				codeStr.append(" )  temp left join warn_state_info warn_state_info on warn_state_info.xfno = temp.xfno  ");
				if( map.get("ALARMACTIVE") != null	&& !map.get("ALARMACTIVE").toString().equals("")){
					codeStr.append(" where warn_state_info.alarm_active is not null and warn_state_info.alarm_active = '");
					codeStr.append(map.get("ALARMACTIVE").toString());
					codeStr.append("'");
				}
				codeStr.append(" group by temp.activityno,temp.ACTIVITY,warn_state_info.alarm_active order by ano ");
			}else if("2".equals(ZFTypeCode)){
				codeStr.append(" select count(0) c,temp.ACTIVITY aname,temp.activityno ano,warn_state_info.alarm_active w from ( ");
				codeStr.append(" SELECT xfbjbinfotemp.xfno XFNO,XFWACTIVITY.ACTIVITYNO activityno,XFWACTIVITY.activityname ACTIVITY ");
				codeStr.append(" from xfbjbinfotemp xfbjbinfotemp, xfwxfstate xfwxfstate  ");
				codeStr.append(" left outer join  XFWACTIVITY XFWACTIVITY on XFWACTIVITY.activityno=xfwxfstate.activityno ");
				codeStr.append(" where  xfbjbinfotemp.xfno = xfwxfstate.xfno  ");
				codeStr.append(" and xfbjbinfotemp.senddate is not null and xfbjbinfotemp.receivedate is not null ");
				codeStr.append(authoritySql);
				if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.senddate >= '"+TimeUtils.string2Date(map.get("sendDate").toString()) +"'") ;
				}
				if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.senddate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
				}
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.receivedate >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.receivedate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				codeStr.append(" )  temp left join warn_state_info warn_state_info on warn_state_info.xfno = temp.xfno  ");
				if( map.get("ALARMACTIVE") != null	&& !map.get("ALARMACTIVE").toString().equals("")){
					codeStr.append(" where warn_state_info.alarm_active is not null and warn_state_info.alarm_active = '");
					codeStr.append(map.get("ALARMACTIVE").toString());
					codeStr.append("'");
				}
				codeStr.append(" group by temp.activityno,temp.ACTIVITY,warn_state_info.alarm_active order by ano ");
			}else{
				codeStr.append(" select count(0) c,temp.ACTIVITY aname,temp.activityno ano,warn_state_info.alarm_active w from ( ");
				codeStr.append(" SELECT xfbzbinfotemp.xfno XFNO,XFWACTIVITY.ACTIVITYNO activityno,XFWACTIVITY.activityname ACTIVITY  ");
				codeStr.append(" from  xfbzbinfotemp xfbzbinfotemp,xfwxfstate xfwxfstate ");
				codeStr.append(" left outer join  XFWACTIVITY XFWACTIVITY on XFWACTIVITY.activityno=xfwxfstate.activityno ");
				codeStr.append(" where  xfbzbinfotemp.xfno = xfwxfstate.xfno  ");
				codeStr.append(" and xfbzbinfotemp.senddate is not null and xfbzbinfotemp.receivedate is not null  ");
				codeStr.append(authoritySql);
				if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.senddate >= '"+TimeUtils.string2Date(map.get("sendDate").toString())+"'") ;
				}
				if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.senddate <= '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
				}
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.receivedate >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and xfbzbinfotemp.receivedate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				codeStr.append(" union all SELECT xfbjbinfotemp.xfno XFNO,XFWACTIVITY.ACTIVITYNO activityno,XFWACTIVITY.activityname ACTIVITY ");
				codeStr.append(" from xfbjbinfotemp xfbjbinfotemp, xfwxfstate xfwxfstate  ");
				codeStr.append(" left outer join  XFWACTIVITY XFWACTIVITY on XFWACTIVITY.activityno=xfwxfstate.activityno ");
				codeStr.append(" where  xfbjbinfotemp.xfno = xfwxfstate.xfno  ");
				codeStr.append(" and xfbjbinfotemp.senddate is not null and xfbjbinfotemp.receivedate is not null ");
				codeStr.append(authoritySql);
				if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.senddate >= '"+TimeUtils.string2Date(map.get("sendDate").toString()) +"'") ;
				}
				if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.senddate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
				}
				if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.receivedate >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
				}
				if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
					codeStr.append(" and xfbjbinfotemp.receivedate < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
				}
				codeStr.append(" )  temp left join warn_state_info warn_state_info on warn_state_info.xfno = temp.xfno  ");
				if( map.get("ALARMACTIVE") != null	&& !map.get("ALARMACTIVE").toString().equals("")){
					codeStr.append(" where warn_state_info.alarm_active is not null and warn_state_info.alarm_active = '");
					codeStr.append(map.get("ALARMACTIVE").toString());
					codeStr.append("'");
				}
				codeStr.append(" group by temp.activityno,temp.ACTIVITY,warn_state_info.alarm_active order by ano ");
			}
			List<Map<String, Object>> list = jdbcTemplate.queryForList(codeStr.toString());
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='已接收后续处理对比' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='处理环节' yAxisName='信访量' >");
			if (list == null || list.isEmpty()) {
				return strXML.append("</chart>").toString();
			}
			strXML.append("<categories>");
			String oldValue = "";
			String oldValueCode = "";
			String newValue = "";
			String newValueCode = "";
			StringBuffer strXMLzcCoun = new StringBuffer();
			StringBuffer strXMLyjCoun = new StringBuffer();
			StringBuffer strXMLcsCoun = new StringBuffer();
			StringBuffer strXMLzcsCoun = new StringBuffer();
			StringBuffer strXMLycsCoun = new StringBuffer();
			String zc = "0";
			String yj = "0";
			String cs = "0";
			String zcs = "0";
			String ycs = "0";
			if(null != list && list.size() > 0){
				oldValue = ((Map)list.get(0)).get("aname").toString().trim();
				oldValueCode = ((Map)list.get(0)).get("ano").toString().trim();
			}
			int t = 0;
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = (Map) list.get(i);
				newValue = mapXML.get("aname").toString().trim();
				newValueCode = mapXML.get("ano").toString().trim();
				if( !oldValue.equals(newValue) ){
					strXML.append("<category label='"+ oldValue.trim() +"' />");
					if("".equals(zc))
						strXMLzcCoun.append("<set acode='"+oldValueCode+"' value='0' />");
					else
						strXMLzcCoun.append("<set acode='"+oldValueCode+"' value='"+zc+"' link='JavaScript:yReceive("+t+",0);' />");
					if("".equals(yj))
						strXMLyjCoun.append("<set acode='"+oldValueCode+"' value='0' />");
					else
						strXMLyjCoun.append("<set acode='"+oldValueCode+"' value='"+yj+"' link='JavaScript:yReceive("+t+",1);' />");
					if("".equals(cs))
						strXMLcsCoun.append("<set acode='"+oldValueCode+"' value='0' />");
					else
						strXMLcsCoun.append("<set acode='"+oldValueCode+"' value='"+cs+"' link='JavaScript:yReceive("+t+",2);' />");
					if("".equals(zcs))
						strXMLzcsCoun.append("<set acode='"+oldValueCode+"' value='0' />");
					else
						strXMLzcsCoun.append("<set acode='"+oldValueCode+"' value='"+zcs+"' link='JavaScript:yReceive("+t+",3);' />");
					if("".equals(ycs))
						strXMLycsCoun.append("<set acode='"+oldValue+"' value='0' />");
					else
						strXMLycsCoun.append("<set acode='"+oldValueCode+"' value='"+ycs+"' link='JavaScript:yReceive("+t+",4);' />");
					zc = "0";
					yj = "0";
					cs = "0";
					zcs = "0";
					ycs = "0";
					oldValue = newValue;
					oldValueCode = newValueCode;
					t++;
				}
				if(null == mapXML.get("w") )
					zc = String.valueOf(Integer.parseInt(zc) +Integer.parseInt(mapXML.get("c").toString()));
				else if(/*"YuJ"*/AlarmType.YuJ.toString().equals(mapXML.get("w")))
					yj = String.valueOf(Integer.parseInt(yj) +Integer.parseInt(mapXML.get("c").toString()));
				else if(/*"ChaoS"*/AlarmType.ChaoS.toString().equals(mapXML.get("w")))
					cs = String.valueOf(Integer.parseInt(cs) +Integer.parseInt(mapXML.get("c").toString()));
				else if(/*"ZhongDCS"*/AlarmType.ZhongDCS.toString().equals(mapXML.get("w")))
					zcs = String.valueOf(Integer.parseInt(zcs) +Integer.parseInt(mapXML.get("c").toString()));
				else if(/*"YanZCS"*/AlarmType.YanZCS.toString().equals(mapXML.get("w")))
					ycs = String.valueOf(Integer.parseInt(ycs) +Integer.parseInt(mapXML.get("c").toString()));
			}
			
			strXML.append("<category label='"+ newValue.trim() +"' />");
			if("0".equals(zc))
				strXMLzcCoun.append("<set acode='"+oldValueCode+"' value='0' />");
			else
				strXMLzcCoun.append("<set acode='"+oldValueCode+"' value='"+zc+"' link='JavaScript:yReceive("+t+",0);' />");
			if("0".equals(yj))
				strXMLyjCoun.append("<set acode='"+oldValueCode+"' value='0' />");
			else
				strXMLyjCoun.append("<set acode='"+oldValueCode+"' value='"+yj+"' link='JavaScript:yReceive("+t+",1);' />");
			if("0".equals(cs))
				strXMLcsCoun.append("<set acode='"+oldValueCode+"' value='0' />");
			else
				strXMLcsCoun.append("<set acode='"+oldValueCode+"' value='"+cs+"' link='JavaScript:yReceive("+t+",2);' />");
			if("0".equals(zcs))
				strXMLzcsCoun.append("<set acode='"+oldValueCode+"' value='0' />");
			else
				strXMLzcsCoun.append("<set acode='"+oldValueCode+"' value='"+zcs+"' link='JavaScript:yReceive("+t+",3);' />");
			if("0".equals(ycs))
				strXMLycsCoun.append("<set acode='"+oldValueCode+"' value='0' />");
			else
				strXMLycsCoun.append("<set acode='"+oldValueCode+"' value='"+ycs+"' link='JavaScript:yReceive("+t+",4);' />");
			
			strXML.append("</categories>");
			strXML.append("<dataset seriesName='正常信访量'  showValues='0'>");
			strXML.append(strXMLzcCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='预警信访量'  showValues='0'>");
			strXML.append(strXMLyjCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='超时信访量'  showValues='0'>");
			strXML.append(strXMLcsCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='中度超时信访量'  showValues='0'>");
			strXML.append(strXMLzcsCoun);
			strXML.append("</dataset>");
			strXML.append("<dataset seriesName='严重超时信访量'  showValues='0'>");
			strXML.append(strXMLycsCoun);
			strXML.append("</dataset>");
			strXML.append("</chart>");
			return strXML.toString();
	}
	
	/**
	 * 纵向转发导出excel功能
	 * @author mengxy
	 * @createDate 2011-06-15
	 * @param String authoritySql
	 * @param sqlSelect
	 * @param map
	 * @param type
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> verTransWarningExportExcel(String authoritySql,String sqlSelect, Map map) throws Exception {
		StringBuffer sql = new StringBuffer("");
		boolean isNotAllFlag = isNotAllFlag(map);
		if (isNotAllFlag) {//查询state表
			sql.append("select "+sqlSelect+" from ( ");
		}else {//显示最下级信息
			sql.append("select distinct "+sqlSelect+" from ( ");
		}
		sql.append(advancedSql(authoritySql, map, false));
		sql.append(" ) order by alarmActive desc ");
		return jdbcTemplate.queryForList(sql.toString());
	}
	
	/**
	 * 查询纵向已接收查询列表: 返回列表查询结果
	 * @author ljx
	 * @createDate 2011-06-08
	 * @param map  XFPrtNo：条形码编号,AchieveTypeCode ：是否归档， AccusedName:被反映人,ZFTypeCode:转发类型
				   ReceiveDate ：接收起始日期,ReceiveDate1 : 接收终止日期
	 * @param sort 
	 * @param dir 
	 * @param start 
	 * @param limit 
	 * @return List<Map>
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getVerReceiveWarningYList(String sql,Map map,String sort,String dir,int start,int limit) throws ParseException {
		String regionCode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		String zfTypeCode = map.get("ZFTypeCode").toString().trim();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		if("1".equals(zfTypeCode)){
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append("SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append("accused.accused_name ACCUSEDNAME,basic.issue_region_name ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append("'转办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE "); 
			strSQL.append("from petition_basic_info basic "); 
			strSQL.append("left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
			strSQL.append("left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append("left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 "); 
			strSQL.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid "); 
			strSQL.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' "); 
			strSQL.append("left join petition_trans_deal_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append("where trans.Send_Date is not null and trans.Back_Date is null and basic.region_code ='"+regionCode+"' ");
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}else if("2".equals(zfTypeCode)){
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append(" SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no  XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append(" accused.accused_name ACCUSEDNAME,basic.issue_region_code ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append(" '交办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE ");
			strSQL.append(" from petition_basic_info basic ");
			strSQL.append(" left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 "); 
			strSQL.append(" left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append(" left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
			strSQL.append(" left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid ");
			strSQL.append(" left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' ");
			strSQL.append(" left join petition_assign_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append(" where trans.Send_Date is not null and trans.Back_Date is null  and basic.region_code ='"+regionCode+"' ");
			
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}else if("0".equals(zfTypeCode)){	
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append("SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append("accused.accused_name ACCUSEDNAME,basic.issue_region_name ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append("'转办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE "); 
			strSQL.append("from petition_basic_info basic "); 
			strSQL.append("left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
			strSQL.append("left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append("left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 "); 
			strSQL.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid "); 
			strSQL.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' "); 
			strSQL.append("left join petition_trans_deal_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append("where trans.Send_Date is not null and trans.Back_Date is null and basic.region_code ='"+regionCode+"' ");

			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(" union all SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no  XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append(" accused.accused_name ACCUSEDNAME,basic.issue_region_code ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append(" '交办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE ");
			strSQL.append(" from petition_basic_info basic ");
			strSQL.append(" left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 "); 
			strSQL.append(" left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append(" left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
			strSQL.append(" left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid ");
			strSQL.append(" left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' ");
			strSQL.append(" left join petition_assign_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append(" where trans.Send_Date is not null and trans.Back_Date is null  and basic.region_code ='"+regionCode+"' ");
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}
		//拼装排序字段
		if(sort!=null && !"".equals(sort) ){
			if(!"ALARMACTIVE".equals(sort) ){
				strSQL.append(" order by ");
				strSQL.append(sort);
				if(dir!=null && !"".equals(dir)){
					strSQL.append(" ");
					strSQL.append(dir);
				}
			}else{
				strSQL.append(" order by xfno desc");
			}
		}else{
			strSQL.append(" order by xfno desc");
		}
		strSQL.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(strSQL.toString());
	}
	
	/**
	 * 为纵向已接收列表查询拼接sql 返回条件语句 未归档的
	 * @author ljx
	 * @createDate 2011-06-08
	 * @param map  XFPrtNo：条形码编号,AchieveTypeCode ：是否归档， AccusedName:被反映人,ZFTypeCode:转发类型
				   ReceiveDate ：接收起始日期,ReceiveDate1 : 接收终止日期
	 * @return string
	 * @throws ParseException 
	 */
	public String getVerReceiveWarningYListConditionSQLUnAchieve(Map map) throws ParseException{
		StringBuffer conditionSql = new StringBuffer();
		if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
			conditionSql.append(" and trans.receive_date >= '"+TimeUtils.string2Date(map.get("ReceiveDate").toString()) +"'") ;
		}
		if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
			conditionSql.append(" and trans.receive_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("ReceiveDate1").toString())) +"'") ;
		}
		if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
			conditionSql.append(" and trans.send_date >= '"+TimeUtils.string2Date(map.get("sendDate").toString()) +"'") ;
		}
		if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
			conditionSql.append(" and trans.send_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("sendDate1").toString())) +"'") ;
		}
		if( map.get("AccusedName") != null && !map.get("AccusedName").toString().equals("")){
			conditionSql.append(" and accused.accused_Name like '%"+Encrypt.encrypt(map.get("AccusedName").toString().trim()) +"%'") ;
		}
		if( map.get("XFPrtNo") != null && !map.get("XFPrtNo").toString().equals("")){
			conditionSql.append(" and basic.petition_prt_no = '"+map.get("XFPrtNo").toString() +"'") ;
		}
		if( map.get("ActivityCode") != null && !map.get("ActivityCode").toString().equals("")){
			conditionSql.append(" and state.activity_No = '"+map.get("ActivityCode").toString() +"'") ;
		}
		return conditionSql.toString();
	}
	
	/**
	 * 查询纵向已接收查询列表: 返回列表总数
	 * @author ljx
	 * @createDate 2011-06-09
	 * @param map  XFPrtNo：条形码编号,AchieveTypeCode ：是否归档， AccusedName:被反映人,ZFTypeCode:转发类型
				   ReceiveDate ：接收起始日期,ReceiveDate1 : 接收终止日期
	 * @return long
	 * @throws ParseException 
	 */
	public long getVerReceiveWarningYListCount(String sql,Map map) throws ParseException {
		String regionCode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		String zfTypeCode = map.get("ZFTypeCode").toString().trim();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select count(0) XFCOUNT from (");
		if("1".equals(zfTypeCode)){
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append("SELECT basic.region_code region_code ,basic.petition_No XFNO,basic.petition_prt_no XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append("accused.accused_name ACCUSEDNAME,basic.issue_region_name ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append("'转办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE "); 
			strSQL.append("from petition_basic_info basic "); 
			strSQL.append("left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
			strSQL.append("left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append("left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 "); 
			strSQL.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid "); 
			strSQL.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' "); 
			strSQL.append("left join petition_trans_deal_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append("where trans.Send_Date is not null and trans.Back_Date is null and basic.region_code ='"+regionCode+"' ");
			
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}else if("2".equals(zfTypeCode)){
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append(" SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no  XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append(" accused.accused_name ACCUSEDNAME,basic.issue_region_code ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append(" '交办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE ");
			strSQL.append(" from petition_basic_info basic ");
			strSQL.append(" left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 "); 
			strSQL.append(" left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append(" left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
			strSQL.append(" left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid ");
			strSQL.append(" left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' ");
			strSQL.append(" left join petition_assign_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append(" where trans.Send_Date is not null and trans.Back_Date is null  and basic.region_code ='"+regionCode+"' ");
			
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}else if("0".equals(zfTypeCode)){	
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append("SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append("accused.accused_name ACCUSEDNAME,basic.issue_region_name ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append("'转办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE "); 
			strSQL.append("from petition_basic_info basic "); 
			strSQL.append("left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
			strSQL.append("left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append("left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 "); 
			strSQL.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid "); 
			strSQL.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' "); 
			strSQL.append("left join petition_trans_deal_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append("where trans.Send_Date is not null and trans.Back_Date is null and basic.region_code ='"+regionCode+"' ");

			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(" union all SELECT basic.region_code region_code ,basic.petition_No XFNO,basic.petition_prt_no  XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append(" accused.accused_name ACCUSEDNAME,basic.issue_region_code ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append(" '交办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE ");
			strSQL.append(" from petition_basic_info basic ");
			strSQL.append(" left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 "); 
			strSQL.append(" left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append(" left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
			strSQL.append(" left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid ");
			strSQL.append(" left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' ");
			strSQL.append(" left join petition_assign_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append(" where trans.Send_Date is not null and trans.Back_Date is null  and basic.region_code ='"+regionCode+"' ");
			
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code =temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}
		strSQL.append(")");
		return jdbcTemplate.queryForLong(strSQL.toString());
	}

	/**
	 * 按regionCode查询区域代码表记录数,条形码校验用的 
	 * @author ljx
	 * @createDate 2011-06-10
	 * @param regionCode
	 * @return long
	 */
	public long getRegionSize(String regionCode) {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select count(0) XFCOUNT from organization where substr(org_Code,1,6)='");
		strSQL.append(regionCode);
		strSQL.append("'");
		return jdbcTemplate.queryForLong(strSQL.toString());
	}

	/**
	 * 纵向已接收导出Excel方法
	 * @author ljx
	 * @date 2011-06-2
	 * @param sqlSelect 列表查询字段项
	 *           XFPrtNo：条形码编号,AchieveTypeCode ：是否归档
	 *           AccusedName:被反映人,ZFTypeCode:转发类型
	 *           Receivedate ：接收起始日期,Receivedate1 : 接收终止日期
	 * @return  list
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> verReceiveWarningYExportExcel(String sql,String sqlSelect,Map map) throws ParseException {
		String regionCode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		String zfTypeCode = map.get("ZFTypeCode").toString().trim();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select "+sqlSelect+" from ( ");
		if("1".equals(zfTypeCode)){
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append("SELECT basic.region_code region_code, basic.petition_No XFNO,basic.petition_prt_no XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append("accused.accused_name ACCUSEDNAME,basic.issue_region_name ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append("'转办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE "); 
			strSQL.append("from petition_basic_info basic "); 
			strSQL.append("left join petition_accuser_info accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
			strSQL.append("left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append("left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 "); 
			strSQL.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid "); 
			strSQL.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' "); 
			strSQL.append("left join petition_trans_deal_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append("where trans.Send_Date is not null and trans.Back_Date is null and basic.region_code ='"+regionCode+"' ");
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code =temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}else if("2".equals(zfTypeCode)){
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append(" SELECT basic.region_code region_code ,basic.petition_No XFNO,basic.petition_prt_no  XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append(" accused.accused_name ACCUSEDNAME,basic.issue_region_code ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append(" '交办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE ");
			strSQL.append(" from petition_basic_info basic ");
			strSQL.append(" left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 "); 
			strSQL.append(" left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append(" left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
			strSQL.append(" left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid ");
			strSQL.append(" left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' ");
			strSQL.append(" left join petition_assign_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append(" where trans.Send_Date is not null and trans.Back_Date is null  and basic.region_code ='"+regionCode+"' ");
			
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}else if("0".equals(zfTypeCode)){	
			strSQL.append("select warn_state_info.alarm_active ALARMACTIVE,temp.* from ( ");
			strSQL.append("SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append("accused.accused_name ACCUSEDNAME,basic.issue_region_name ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append("'转办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE "); 
			strSQL.append("from petition_basic_info basic "); 
			strSQL.append("left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
			strSQL.append("left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append("left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 "); 
			strSQL.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid "); 
			strSQL.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' "); 
			strSQL.append("left join petition_trans_deal_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append("where trans.Send_Date is not null and trans.Back_Date is null and basic.region_code ='"+regionCode+"' ");

			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(" union all SELECT basic.region_code region_code,basic.petition_No XFNO,basic.petition_prt_no  XFPRTNO,basic.petition_date XFDATE,accuser.accuser_name ACCUSERNAME, ");
			strSQL.append(" accused.accused_name ACCUSEDNAME,basic.issue_region_code ACCUSEDREGIONNAME,issue.issue_type_name WT, ");
			strSQL.append(" '交办' ZFTYPECODE,state.activity_Name activityName,trans.input_date ZBDATE,trans.Send_Date SENDDATE,trans.Receive_date RECEIVEDATE ");
			strSQL.append(" from petition_basic_info basic ");
			strSQL.append(" left join petition_accuser_info  accuser on basic.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 "); 
			strSQL.append(" left join petition_accused_info accused on basic.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
			strSQL.append(" left join petition_issue_info issue  on basic.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
			strSQL.append(" left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid ");
			strSQL.append(" left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid and basic.region_code = deal.region_code and deal.valid_flag = '0' ");
			strSQL.append(" left join petition_assign_info trans on deal.oid = trans.petition_deal_info_oid and deal.deal_no = trans.deal_no "); 
			strSQL.append(" where trans.Send_Date is not null and trans.Back_Date is null  and basic.region_code ='"+regionCode+"' ");
			
			strSQL.append(sql);
			strSQL.append(getVerReceiveWarningYListConditionSQLUnAchieve(map));
			strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code ");
			if( map.get("ALARMACTIVE") != null && !map.get("ALARMACTIVE").toString().equals("")){
				if("zhengchang".equals(map.get("ALARMACTIVE").toString().trim())){
					strSQL.append(" where  warn_state_info.alarm_active is null ");
				}else{
					strSQL.append(" where  warn_state_info.alarm_active = '");
					strSQL.append(map.get("ALARMACTIVE"));
					strSQL.append("'");
				}
			}
		}
		strSQL.append(" order by xfno desc )");
		return jdbcTemplate.queryForList(strSQL.toString());
	}

	/**
	 * 纵向转发“已接收信访件下级后续处理时限对比”多柱图需要的数据集的方法
	 * @author mengxy
	 * @date 2011-06-20
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * InputDate：信访起始日，InputDate1：信访终止日，ZFTypeCode:转发类型
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String verTransTraceChart(String sqlDrill,Map map) throws ParseException {
		String regioncode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		StringBuffer strSQL = new StringBuffer();
		if("1".equals(map.get("ZFTypeCode").toString())){//转办
			strSQL.append("select count(0) c ,temp.regioncode,temp.regionname,temp.nextactivityno from (  ");
			strSQL.append("select organ.org_code regioncode, organ.org_name regionname, ");  
			strSQL.append("s.activity_no nextactivityno ");  
			strSQL.append("from petition_basic_info ");
			if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
				strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
			}
			if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
				strSQL.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
			}
			strSQL.append(" left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_deal_info.valid_flag = '0' "); 
			strSQL.append(" left join petition_trans_deal_info on petition_deal_info.oid = petition_trans_deal_info.petition_deal_info_oid ");
			//连接本级
			strSQL.append(" left join PETITION_CIRCULATION_STATE_INFO  on  PETITION_CIRCULATION_STATE_INFO.petition_basic_info_oid = petition_basic_info.oid ");
			strSQL.append(" inner join PETITION_CIRCULATION_STATE_INFO s on  s.petition_no = petition_basic_info.petition_no ");
			strSQL.append(" and petition_trans_deal_info.to_region_code = s.region_code ");
			strSQL.append(" left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = petition_trans_deal_info.to_region_code ");
			strSQL.append(" where  petition_basic_info.region_code = '");
			strSQL.append(regioncode);
			strSQL.append("' ");
			strSQL.append(" and petition_trans_deal_info.send_date is not null and petition_trans_deal_info.receive_date is not null  ");
			strSQL.append(" and petition_trans_deal_info.From_Region_code = '"+map.get("CurrRegion")+"' ");
			strSQL.append(sqlDrill);
			strSQL.append(getVerTransTraceChartConditionSQL(map, transTableName));
			
			strSQL.append("union all ");
			strSQL.append("select organ.org_code regioncode, organ.org_name regionName ,'9999999999' nextactivityno  ");
			strSQL.append("from petition_basic_info  ");
			if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
				strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
			}
			if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
				strSQL.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
			}
			strSQL.append(" left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
			strSQL.append(" left join petition_trans_deal_info on petition_deal_info.oid = petition_trans_deal_info.petition_deal_info_oid");
			strSQL.append(" left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = petition_trans_deal_info.to_region_code ");
			strSQL.append(" where  petition_trans_deal_info.To_Region_Code <> '' and petition_basic_info.region_code = '"+regioncode+"' ");
			strSQL.append(" and (petition_trans_deal_info.back_date is null )  and (petition_trans_deal_info.RECEIVE_DATE is null) and (petition_trans_deal_info.send_date is not null) ");
			strSQL.append(" and petition_trans_deal_info.From_Region_code = '"+map.get("CurrRegion")+"' ");
			strSQL.append(getUnAchievedStateConditionSQL(map, transTableName));
			strSQL.append(sqlDrill);
			strSQL.append(") temp ");
			strSQL.append("group by temp.regioncode,temp.regionname,temp.nextactivityno ");
			strSQL.append("order by temp.regioncode,temp.nextactivityno");
		}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
			strSQL.append("select count(0) c,temp.regioncode,temp.regionname,temp.nextactivityno, 'jb' ZJtype from (  ");
			strSQL.append("select petition_basic_info.petition_no xfno, organ.org_code regioncode, organ.org_name regionName,  ");
			strSQL.append("s.activity_no nextactivityno "); 
			strSQL.append("from petition_basic_info  ");
			if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
				strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
			}
			if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
				strSQL.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
			}
			strSQL.append("left join petition_deal_info on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
			strSQL.append("left join petition_assign_info  on petition_deal_info.oid = petition_assign_info.petition_deal_info_oid and petition_deal_info.deal_no = petition_assign_info.deal_no ");
			//连接本级
			strSQL.append(" left join PETITION_CIRCULATION_STATE_INFO  on  PETITION_CIRCULATION_STATE_INFO.petition_basic_info_oid = petition_basic_info.oid ");
			strSQL.append(" inner join PETITION_CIRCULATION_STATE_INFO s on  s.petition_no = petition_basic_info.petition_no and petition_assign_info.to_region_code = s.region_code ");
			strSQL.append(" left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = petition_assign_info.to_region_code ");
			strSQL.append(" where  petition_basic_info.region_code = '"+regioncode+"' ");
			strSQL.append(" and petition_assign_info.send_date is not null and petition_assign_info.receive_date is not null  ");
			strSQL.append(" and petition_assign_info.From_Region_code = '"+map.get("CurrRegion")+"' ");
			strSQL.append(sqlDrill);
			strSQL.append(getVerTransTraceChartConditionSQL(map, assignTableName));
			strSQL.append("union  all  ");
			strSQL.append("select petition_basic_info.petition_no xfno, organ.org_code regioncode, organ.org_name regionName ,'9999999999' nextactivityno ");
			strSQL.append("from petition_basic_info ");
			if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
				strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
			}
			if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
				strSQL.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
			}
			strSQL.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
			strSQL.append("left join petition_assign_info on petition_deal_info.oid = petition_assign_info.petition_deal_info_oid and petition_deal_info.deal_no = petition_assign_info.deal_no ");
			strSQL.append("left join (select o.org_Code,o.org_name from PETITION_ORGAN_TRANS o inner join ORGANIZATION org on org.org_code=o.org_code and LEFT(o.org_Code,6)=o.Organ_Id) organ on organ.org_Code = petition_assign_info.to_region_code ");
			strSQL.append("where  petition_assign_info.To_Region_Code <> '' and petition_basic_info.region_code = '"+regioncode+"' ");
			strSQL.append("and (petition_assign_info.back_date is null )  and (petition_assign_info.RECEIVE_DATE is null) and (petition_assign_info.send_date is not null) ");
			strSQL.append(" and petition_assign_info.From_Region_code = '"+map.get("CurrRegion")+"' ");
			strSQL.append(getUnAchievedStateConditionSQL(map, assignTableName));
			strSQL.append(sqlDrill);
			strSQL.append(") temp ");
			strSQL.append("group by temp.regioncode,temp.regionname,temp.nextactivityno ");
			strSQL.append("order by temp.regioncode,temp.nextactivityno");
		}
			
		List<Map<String, Object>> list = jdbcTemplate.queryForList(strSQL.toString());
		StringBuffer strXML = new StringBuffer();
		/* 设置统计图头信息 */
		strXML.append("<chart caption='下级后续处理对比' ");
		strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
		strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
		strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
		strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
		strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
		strXML.append(" palette='2' baseFontSize='12' xAxisName='处理环节' yAxisName='信访量' >");
		if(list.isEmpty()){
			return strXML.append("</chart>").toString();
		}
		strXML.append("<categories>");
		String oldValue = "";
		String oldValueCode = "";
		String newValue = "";
		String newValueCode = "";
		String activityno ="";
		StringBuffer strXMLweiCCoun = new StringBuffer();
		StringBuffer strXMLyjCoun = new StringBuffer();
		StringBuffer strXMLcsCoun = new StringBuffer();
		StringBuffer strXMLzcsCoun = new StringBuffer();
		int weiC = 0;
		int sl = 0;
		int bl = 0;
		int lj = 0;
		oldValue = ((Map)list.get(0)).get("regionname").toString();
		oldValueCode = ((Map)list.get(0)).get("regioncode").toString();
		int t = 0;
		for (int i = 0; i < list.size(); i++) {
			Map mapXML = (Map) list.get(i);
			if(mapXML.get("regioncode")==null||"".equals(mapXML.get("regioncode"))){
				continue;
			}
			newValue = mapXML.get("regionname").toString();
			newValueCode = mapXML.get("regioncode").toString();
			activityno = mapXML.get("nextactivityno").toString();
			if( !oldValue.equals(newValue) ){
				strXML.append("<category label='"+ oldValue.trim() +"' />");
				if(weiC == 0)
					strXMLweiCCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
				else{
					if("1".equals(map.get("ZFTypeCode").toString())){//转办
						strXMLweiCCoun.append("<set regionCode = '"+oldValueCode+"' value='"+weiC+"' link='JavaScript:trace("+t+",1);' />");
					}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
						strXMLweiCCoun.append("<set regionCode = '"+oldValueCode+"' value='"+weiC+"' link='JavaScript:traceAssign("+t+",1);' />");
					}
				}
				if(sl == 0)
					strXMLyjCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
				else{
					if("1".equals(map.get("ZFTypeCode").toString())){//转办
						strXMLyjCoun.append("<set regionCode = '"+oldValueCode+"' value='"+sl+"' link='JavaScript:trace("+t+",2);' />");
					}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
						strXMLyjCoun.append("<set regionCode = '"+oldValueCode+"' value='"+sl+"' link='JavaScript:traceAssign("+t+",2);' />");
					}
				}
				if(bl == 0)
					strXMLcsCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
				else{
					if("1".equals(map.get("ZFTypeCode").toString())){//转办
						strXMLcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+bl+"' link='JavaScript:trace("+t+",3);' />");
					}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
						strXMLcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+bl+"' link='JavaScript:traceAssign("+t+",3);' />");
					}
				}
				if(lj == 0)
					strXMLzcsCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
				else{
					if("1".equals(map.get("ZFTypeCode").toString())){//转办
						strXMLzcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+lj+"' link='JavaScript:trace("+t+",4);' />");
					}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
						strXMLzcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+lj+"' link='JavaScript:traceAssign("+t+",4);' />");
					}
				}
				weiC = 0;
				sl = 0;
				bl = 0;
				lj = 0;
				oldValue = newValue;
				oldValueCode = newValueCode;
				t++;
			}
			if("9999999999".equals(activityno)){
				weiC += Integer.parseInt(mapXML.get("c").toString());
			}else if("0000000100".equals(activityno) || 
				    activityno.equals("0000000101") ||
				    activityno.equals("0000000102") ||
				    activityno.equals("0000000103") ||
				    activityno.equals("0000000104") ||
				    activityno.equals("0000000105") ||
				    activityno.equals("0000000107") ||
//				    activityno.equals("0000000108") ||
//				    activityno.equals("0000000903") ||
//				    activityno.equals("0000000904") ||
//				    activityno.equals("0000000906") ||
				    activityno.equals("0000000905")){
				sl += Integer.parseInt(mapXML.get("c").toString());
				//0000000300,0000000301,0000000302,0000000303,0000000304,0000000305,0000000307,9
				//2016-05-25 zhz 修改bug： 转下级后续处理状况 图表内 未了结内统计了 转下级已了结的记录
			}else if("0000000205,0000000216,0000000303,0000000304,0000000305,0000000307,9".contains(activityno)){
				lj += Integer.parseInt(mapXML.get("c").toString());
			}else {
				bl += Integer.parseInt(mapXML.get("c").toString());
			}
		}
		strXML.append("<category label='"+ newValue.trim() +"' />");
		if(weiC == 0)
			strXMLweiCCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
		else{
			if("1".equals(map.get("ZFTypeCode").toString())){//转办
				strXMLweiCCoun.append("<set regionCode = '"+oldValueCode+"' value='"+weiC+"' link='JavaScript:trace("+t+",1);' />");
			}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
				strXMLweiCCoun.append("<set regionCode = '"+oldValueCode+"' value='"+weiC+"' link='JavaScript:traceAssign("+t+",1);' />");
			}
		}
		if(sl == 0)
			strXMLyjCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
		else{
			if("1".equals(map.get("ZFTypeCode").toString())){//转办
				strXMLyjCoun.append("<set regionCode = '"+oldValueCode+"' value='"+sl+"' link='JavaScript:trace("+t+",2);' />");
			}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
				strXMLyjCoun.append("<set regionCode = '"+oldValueCode+"' value='"+sl+"' link='JavaScript:traceAssign("+t+",2);' />");
			}
		}
		if(bl == 0)
			strXMLcsCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
		else{
			if("1".equals(map.get("ZFTypeCode").toString())){//转办
				strXMLcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+bl+"' link='JavaScript:trace("+t+",3);' />");
			}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
				strXMLcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+bl+"' link='JavaScript:traceAssign("+t+",3);' />");
			}
		}
		if(lj == 0)
			strXMLzcsCoun.append("<set regionCode = '"+oldValueCode+"' value='0' />");
		else{
			if("1".equals(map.get("ZFTypeCode").toString())){//转办
				strXMLzcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+lj+"' link='JavaScript:trace("+t+",4);' />");
			}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
				strXMLzcsCoun.append("<set regionCode = '"+oldValueCode+"' value='"+lj+"' link='JavaScript:traceAssign("+t+",4);' />");
			}
		}
		strXML.append("</categories>");
//		for (int i = 0; i < list.size(); i++) {
//			Map mapXML = (Map) list.get(i);
//		}
		strXML.append("<dataset seriesName='未接收'  showValues='0'>");
		strXML.append(strXMLweiCCoun);
		strXML.append("</dataset>");
		strXML.append("<dataset seriesName='受理未办理'  showValues='0'>");
		strXML.append(strXMLyjCoun);
		strXML.append("</dataset>");
		strXML.append("<dataset seriesName='办理未了结'  showValues='0'>");
		strXML.append(strXMLcsCoun);
		strXML.append("</dataset>");
		strXML.append("<dataset seriesName='已了结'  showValues='0'>");
		strXML.append(strXMLzcsCoun);
		strXML.append("</dataset>");
		strXML.append("</chart>");
		return strXML.toString();
	}
	
	/**
	 * 纵向转发“已接收信访件下级后续处理时限对比”多柱图需要的数据集的查询条件
	 * @author mengxy
	 * @createDate 2011-07-18
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * InputDate：信访起始日，InputDate1：信访终止日,ZFTypeCode:转发类型,
	 * RRegionCode、RRegionName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionName:转发机构
	 * @return String 查询链接的条件
	 * @throws ParseException 
	 */
	public String getVerTransTraceChartConditionSQL(Map map,String ZFTypeTableName) throws ParseException{
		StringBuffer conditionSql = new StringBuffer();
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			conditionSql.append(" and petition_basic_info.petition_date >= '"+TimeUtils.string2Date(map.get("InputDate").toString())+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			conditionSql.append(" and petition_basic_info.petition_date < '"+TimeUtils.string2Date(CommonUtil.plusOneDays(map.get("InputDate1").toString()))+"' ");
		}
		if(map.get("objectClassName")!= null && !"".equals(map.get("objectClassName").toString())){
			conditionSql.append(" and petition_basic_info.object_class_code = '"+map.get("objectClassName")+"' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
				conditionSql.append(" and "+ZFTypeTableName+".To_Region_Code = '"+map.get("ToRegionCode")+"' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			conditionSql.append(" and petition_basic_info.issue_Region_Code = '"+map.get("RRegionCode")+"' ");
		}
		if(null != map.get("realNameReport")&& !"".equals(map.get("realNameReport").toString())){
			conditionSql.append(" and petition_basic_info.real_Name_Report = '"+map.get("realNameReport")+"' ");
		}
		return conditionSql.toString();
	}

	/**
	 * 字符串替换函数，将半角符号替换为全角符号
	 * @param str
	 * @return 返回替换后的字符串
	 */
	public String replaceChart(Object str){
		if(str==null||"".equals(str)){
			return "";
		}
		String replaceStr = str.toString().replace("&", "＆");
		return replaceStr;
	}
}
