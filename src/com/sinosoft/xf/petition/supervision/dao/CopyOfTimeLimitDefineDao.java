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

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 时限dao
 * @author ljx 
 * @createDate 2011-04-27
 */
@Repository
public class CopyOfTimeLimitDefineDao extends HibernateDao<TimeLimitDefine, String> {

	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 通过regionCode查询信息
	 * @param regionCode 区域编码
	 * @return 信息集合
	 */
	@SuppressWarnings("unchecked")
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
	public String getRegionCode(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		return person.getRegionCode();
	}
	
	/**
	 * 查询纵向转发查询列表时查询转办或者交办信息的条件语句: 未归档
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * alarmActive 为统计图定制的sql串
	 * @return String sql
	 */
	public String getUnAchievedStateSQL(Map map){
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer();
		sql.append("from petition_basic_info ");
		sql.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		sql.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		sql.append("left join petition_issue_info   on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
		sql.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid "); 
		sql.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
		sql.append("left join "+map.get("ZFTypeTable")+"  on petition_deal_info.oid = "+map.get("ZFTypeTable")+".petition_deal_info_oid and petition_deal_info.deal_no = "+map.get("ZFTypeTable")+".deal_no ");
		sql.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no and petition_basic_info.region_code = w.region_code and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF' ");  
		sql.append("where petition_basic_info.region_code = '000000000000' ");
		sql.append(" and "+map.get("ZFTypeTable")+".From_Region_Code = '"+map.get("CurrRegion")+"' ");
		sql.append("and  "+map.get("ZFTypeTable")+".To_Region_Code <> ''  and "+map.get("ZFTypeTable")+".back_date is null ");   		
		
		if((map.get("IsSend") != null) && (map.get("IsSend1") != null) ){ 	
		    if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
		    	sql.append(" and ("+map.get("ZFTypeTable")+".send_date is null or ("+map.get("ZFTypeTable")+".send_date is not null and "+map.get("ZFTypeTable")+".Receive_Date is not null))");
		    }else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
		    	sql.append(" and ("+map.get("ZFTypeTable")+".send_date is null or ("+map.get("ZFTypeTable")+".send_date is not null and "+map.get("ZFTypeTable")+".Receive_Date is null))");
		    }
		}
		if((map.get("IsSend") != null)  && (map.get("IsSend1") == null) ){ 	
			sql.append(" and ("+map.get("ZFTypeTable")+".send_date is not null)");
		    if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
		    	sql.append(" and ("+map.get("ZFTypeTable")+".Receive_Date is not null)");
		    }else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
		    	sql.append(" and ("+map.get("ZFTypeTable")+".Receive_Date is null)");
		    }
		}
		else if((map.get("IsSend") == null) && (map.get("IsSend1") != null) ){
			sql.append(" and ("+map.get("ZFTypeTable")+".send_date is null) ");
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
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * alarmActive 为统计图定制的sql串
	 * @return String sql
	 */
	public String getUnAchievedStateSQL2(Map map){
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer();
		sql.append("from petition_basic_info ");
		sql.append("left join petition_accused_info on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 "); 
		sql.append("left join petition_accuser_info on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num = 1 "); 
		sql.append("left join petition_deal_info on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' "); 
		sql.append("left join "+map.get("ZFTypeTable")+"  on petition_deal_info.oid = "+map.get("ZFTypeTable")+".petition_deal_info_oid and petition_deal_info.deal_no = "+map.get("ZFTypeTable")+".deal_no "); 
		sql.append("left join petition_circulation_state_trace_info  on  petition_circulation_state_trace_info.petition_no = petition_basic_info.petition_no and "+map.get("ZFTypeTable")+".to_region_code = petition_circulation_state_trace_info.region_code "); 
		sql.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF'  ");
		sql.append("where petition_basic_info.region_code ='"+regioncode+"'  ");
		sql.append("and  petition_circulation_state_trace_info.petition_state_no = ");
		sql.append("(select max(petition_state_no) md  from petition_circulation_state_trace_info  where petition_no=petition_basic_info.petition_no  and region_code = "+map.get("ZFTypeTable")+".to_region_code)  ");
		sql.append("and "+map.get("ZFTypeTable")+".back_date is null  and "+map.get("ZFTypeTable")+".send_date is not null and  "+map.get("ZFTypeTable")+".receive_date is not null  ");
		sql.append(" and "+map.get("ZFTypeTable")+".From_Region_Code = '"+map.get("CurrRegion")+"' ");
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			sql.append(" and petition_basic_info.petition_date >=  '"+map.get("InputDate")+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			sql.append(" and petition_basic_info.petition_date <= '"+map.get("InputDate1")+"' ");
		}
		if(map.get("AccusedClasses")!= null && !"".equals(map.get("AccusedClasses").toString())){
			sql.append(" and petition_accused_info.Accused_class_code = '"+map.get("AccusedClasses")+"' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
			sql.append(" and "+map.get("ZFTypeTable")+".To_Region_Code = '"+map.get("ToRegionCode")+"' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			sql.append(" and petition_basic_info.issue_region_code = '"+map.get("RRegionCode")+"' ");
		}
		if(map.get("nextactivityno")!= null && !"".equals(map.get("nextactivityno").toString())){
			if("2".equals(map.get("nextactivityno").toString())){
				sql.append(" and (petition_circulation_state_trace_info.next_activity_no = '0000000100' or petition_circulation_state_trace_info.next_activity_no = '0000000101' or petition_circulation_state_trace_info.next_activity_no = '0000000102' ) ");
			}else if("3".equals(map.get("nextactivityno").toString())){
				sql.append(" and (petition_circulation_state_trace_info.next_activity_no = '0000000201' or petition_circulation_state_trace_info.next_activity_no = '0000000202' or petition_circulation_state_trace_info.next_activity_no = '0000000203' or ");
				sql.append(" petition_circulation_state_trace_info.next_activity_no = '0000000204' or petition_circulation_state_trace_info.next_activity_no = '0000000205' or petition_circulation_state_trace_info.next_activity_no = '0000000206' or ");
				sql.append(" petition_circulation_state_trace_info.next_activity_no = '0000000207' or petition_circulation_state_trace_info.next_activity_no = '0000000208' or petition_circulation_state_trace_info.next_activity_no = '0000000209' or ");
				sql.append(" petition_circulation_state_trace_info.next_activity_no = '0000000210' or petition_circulation_state_trace_info.next_activity_no = '0000000211' or petition_circulation_state_trace_info.next_activity_no = '0000000212' or ");
				sql.append(" petition_circulation_state_trace_info.next_activity_no = '0000000213' or petition_circulation_state_trace_info.next_activity_no = '0000000216' or petition_circulation_state_trace_info.next_activity_no = '0000000217' or ");
				sql.append(" petition_circulation_state_trace_info.next_activity_no = '0000000218' or petition_circulation_state_trace_info.next_activity_no = '0000000219' ) ");
			}else if("4".equals(map.get("nextactivityno").toString())){
				sql.append(" and (petition_circulation_state_trace_info.next_activity_no = '0000000300' or petition_circulation_state_trace_info.next_activity_no = '0000000301' or petition_circulation_state_trace_info.next_activity_no = '0000000302' or ");
				sql.append("petition_circulation_state_trace_info.next_activity_no = '0000000303' or petition_circulation_state_trace_info.next_activity_no = '0000000304' or petition_circulation_state_trace_info.next_activity_no = '0000000305' ) ");
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
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * @return String sql
	 */
	public String getUnAchievedStateConditionSQL(Map map){
		StringBuffer conditionSql = new StringBuffer();
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			conditionSql.append(" and petition_basic_info.petition_date >= '"+map.get("InputDate")+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			conditionSql.append(" and petition_basic_info.petition_date <= '"+map.get("InputDate1")+"' ");
		}
		if(map.get("XFNo1")!= null && !"".equals(map.get("XFNo1").toString())){
			conditionSql.append(" and petition_basic_info.petition_No like '%"+map.get("XFNo1")+"%' ");
		}
		if(map.get("XFPrtNo")!= null && !"".equals(map.get("XFPrtNo").toString())){
			conditionSql.append(" and petition_basic_info.petition_prt_no like '%"+map.get("XFPrtNo")+"%' ");
		}
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			conditionSql.append(" and petition_accused_info.accused_name like '%"+Encrypt.encrypt(map.get("AccusedName").toString())+"%' ");
		}
		if(map.get("AccusedClasses")!= null && !"".equals(map.get("AccusedClasses").toString())){
			conditionSql.append(" and petition_accused_info.accused_class_code = '"+map.get("AccusedClasses")+"' ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			conditionSql.append(" and petition_accuser_info.accuser_name like '%"+Encrypt.encrypt(map.get("AccuserName").toString())+"%' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
			conditionSql.append(" and "+map.get("ZFTypeTable")+".To_Region_Code = '"+map.get("ToRegionCode")+"' ");
		}
		if(map.get("ToRegionName")!= null && !"".equals(map.get("ToRegionName").toString())){
			conditionSql.append(" and "+map.get("ZFTypeTable")+".To_Region_Name = '"+map.get("ToRegionName")+"' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			conditionSql.append(" and petition_basic_info.issue_region_code = '"+map.get("RRegionCode")+"' ");
		}
		if(map.get("RRegionName")!= null && !"".equals(map.get("RRegionName").toString())){
			conditionSql.append(" and petition_basic_info.issue_region_name = '"+map.get("RRegionName")+"' ");
		}
		return conditionSql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时查询转办信息的条件语句: 已归档
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param IsCheck：0：本级查询、1：下级查询(必选)
	 * @return String sql
	 */
	public String getAchievedZBSQL(String IsCheck){
		String regioncode = getRegionCode();
		String CurrRegion1 = regioncode.substring(0,2);
		String CurrRegion2 = regioncode.substring(2,4);
		String CurrRegion3 = regioncode.substring(4,6);
		int RegionFlag1 = 0;
		String Zero ="";
		if(CurrRegion1.equals("00")){
			RegionFlag1 = 2;
			Zero = "0000";
		}else if(!CurrRegion1.equals("00") && CurrRegion2.equals("00") && CurrRegion3.equals("00")){
			RegionFlag1 = 4;
			Zero = "00";
		}else if(!CurrRegion1.equals("00") && !CurrRegion2.equals("00") && CurrRegion3.equals("00")){
			RegionFlag1 = 6;
			Zero = "";
		}

		StringBuffer zbSql = new StringBuffer();
		zbSql.append("FROM XFBZBInfo,XFBXFInfo,XFBAccusedInfo left outer join ");
		zbSql.append("xfscode jibe on jibe.code = XFBAccusedInfo.ACCUSEDCLASSCODE and ");
		zbSql.append("jibe.codetype='JIBE' left outer join XFSREGION regi ");
		zbSql.append("on regi.regioncode = substr(XFBAccusedInfo.AccusedRegionCode,1,");
	 	zbSql.append(RegionFlag1+")||'"+Zero+"',XFBAccuserInfo,XFBAchieveInfo ");
	 	zbSql.append(" where XFBXFInfo.xfno=XFBZBInfo.xfno and XFBXFInfo.xfno=XFBAccusedInfo.xfno and ");
		zbSql.append("XFBXFInfo.xfno=XFBAccuserInfo.xfno and XFBXFInfo.xfno = XFBAchieveInfo.xfno ");
		zbSql.append("and XFBAccusedInfo.accusednum=0 and XFBAccuserInfo.accusernum=0 ");
		zbSql.append(" and XFBXFInfo.regioncode = '" + regioncode + "'");
		if(IsCheck.equals("0")){
			zbSql.append("and XFBAccusedInfo.regioncode = '"+regioncode+"' ");
			zbSql.append("and XFBAccuserInfo.regioncode = '"+regioncode+"' ");
		}else{
			zbSql.append(" and XFBAccusedInfo.regioncode = (select max(b.regioncode) from XFBAccusedInfo b ");
			zbSql.append("where XFBXFInfo.xfno = b.xfno) ");
			zbSql.append(" and XFBAccuserInfo.regioncode = (select max(c.regioncode) from XFBAccuserInfo c ");
			zbSql.append("where XFBXFInfo.xfno = c.xfno) ");
		}
		zbSql.append(" and XFBAchieveInfo.regioncode = '" + regioncode + "'");
		zbSql.append(" and XFBZBInfo.Dealno = (Select max(e.dealno) from XFBDealInfo e where ");
		zbSql.append("XFBZBInfo.xfno = e.xfno and e.regioncode='"+regioncode+"' and e.currflag ='0' ) ");
		return zbSql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时查询交办信息的条件语句: 已归档
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param IsCheck：0：本级查询、1：下级查询(必选)
	 * @return String sql
	 */
	public String getAchievedJBSQL(String IsCheck){
		String regioncode = getRegionCode();
		String CurrRegion1 = regioncode.substring(0,2);
		String CurrRegion2 = regioncode.substring(2,4);
		String CurrRegion3 = regioncode.substring(4,6);
		int RegionFlag1 = 0;
		String Zero ="";
		if(CurrRegion1.equals("00")){
			RegionFlag1 = 2;
			Zero = "0000";
		}else if(!CurrRegion1.equals("00") && CurrRegion2.equals("00") && CurrRegion3.equals("00")){
			RegionFlag1 = 4;
			Zero = "00";
		}else if(!CurrRegion1.equals("00") && !CurrRegion2.equals("00") && CurrRegion3.equals("00")){
			RegionFlag1 = 6;
			Zero = "";
		}
		StringBuffer jbSql = new StringBuffer();
		jbSql.append("FROM XFBJBInfo,XFBXFInfo,XFBAccusedInfo left outer join xfscode jibe on ");
		jbSql.append("jibe.code=XFBAccusedInfo.ACCUSEDCLASSCODE and jibe.codetype='JIBE' ");
		jbSql.append("left outer join XFSREGION regi on regi.regioncode = ");
		jbSql.append("substr(XFBAccusedInfo.AccusedRegionCode,1,"+RegionFlag1+")||'"+Zero+"',");
		jbSql.append("XFBAccuserInfo,XFBAchieveInfo where XFBXFInfo.xfno = XFBJBInfo.xfno ");
		jbSql.append("and XFBXFInfo.xfno = XFBAccusedInfo.xfno and XFBXFInfo.xfno = ");
	  	jbSql.append("XFBAccuserInfo.xfno and XFBXFInfo.xfno = XFBAchieveInfo.xfno ");
	  	jbSql.append("and XFBAccusedInfo.accusednum=0 and XFBAccuserInfo.accusernum = 0 ");
	  	jbSql.append("and XFBXFInfo.regioncode = '"+regioncode+"' ");
		if(IsCheck.equals("0")){
			jbSql.append("and XFBAccusedInfo.regioncode = '"+regioncode+"' ");
			jbSql.append("and XFBAccuserInfo.regioncode = '"+regioncode+"' ");
		}else{
			jbSql.append(" and XFBAccusedInfo.regioncode = (select max(b.regioncode) from XFBAccusedInfo b ");
			jbSql.append("where XFBXFInfo.xfno = b.xfno) ");
			jbSql.append(" and XFBAccuserInfo.regioncode = (select max(c.regioncode) from XFBAccuserInfo c ");
			jbSql.append("where XFBXFInfo.xfno = c.xfno) ");
		}
		jbSql.append("and XFBAchieveInfo.regioncode = '"+regioncode+"' ");
		jbSql.append("and XFBJBInfo.Dealno = (Select max(e.dealno) from XFBDealInfo e where ");
	  	jbSql.append("XFBJBInfo.xfno = e.xfno and e.regioncode='"+regioncode+"' ");
	  	jbSql.append("and e.currflag ='0' ) ");
		return jbSql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时查询交办信息的条件语句: 未归档
	 * @author mengxy
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * alarmActive 为统计图定制的sql串
	 * @createDate 2011-05-17
	 * @return String sql
	 */
	public String getUnAchievedSQL(Map map){
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer jbSql = new StringBuffer();
		jbSql.append("FROM petition_basic_info  ");
		jbSql.append("left join petition_accuser_info  on petition_basic_info.petition_no = petition_accuser_info.petition_no and petition_accuser_info.accuser_num = 1 "); 
		jbSql.append("left join petition_accused_info  on petition_basic_info.petition_no = petition_accused_info.petition_no and petition_accused_info.accused_num = 1 "); 
		jbSql.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
		jbSql.append("left join "+map.get("ZFTypeTable")+"  on petition_deal_info.oid = "+map.get("ZFTypeTable")+".petition_deal_info_oid and petition_deal_info.deal_no = "+map.get("ZFTypeTable")+".deal_no ");
		jbSql.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		jbSql.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no  and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF' "); 
		jbSql.append("where  "+map.get("ZFTypeTable")+".back_date is null and "+map.get("ZFTypeTable")+".To_Region_code <> ''  ");
		jbSql.append(" and "+map.get("ZFTypeTable")+".From_Region_Code = '"+map.get("CurrRegion")+"' ");
		jbSql.append(" and substr(petition_basic_info.region_code,1,");
		jbSql.append( regionFlag );
		jbSql.append( ")||'" );
		if(regionFlag!=0)
			for(int i=0;i<12-regionFlag;i++){
				jbSql.append('0');
			}
		jbSql.append("' = '" );
		jbSql.append(regioncode);
		jbSql.append("' ");
		
		jbSql.append("and petition_accuser_info.region_code = (select max(accuserTemp.region_code) from petition_accuser_info accuserTemp where petition_basic_info.petition_no = accuserTemp.petition_no) ");
		jbSql.append("and petition_accused_info.region_code = (select max(accusedTemp.region_code) from petition_accused_info accusedTemp where petition_basic_info.petition_no = accusedTemp.petition_no) ");
		
		if((map.get("IsSend") != null) && (map.get("IsSend1") != null) ){ 	
		    if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
		    	jbSql.append(" and ("+map.get("ZFTypeTable")+".send_date is null or ("+map.get("ZFTypeTable")+".send_date is not null and "+map.get("ZFTypeTable")+".Receive_Date is not null))");
		    }else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
		    	jbSql.append(" and ("+map.get("ZFTypeTable")+".send_date is null or ("+map.get("ZFTypeTable")+".send_date is not null and "+map.get("ZFTypeTable")+".Receive_Date is null))");
		    }
		}
		if((map.get("IsSend") != null)  && (map.get("IsSend1") == null) ){ 	
			jbSql.append(" and ("+map.get("ZFTypeTable")+".send_date is not null)");
		    if((map.get("IsReceive") != null) && (map.get("IsReceive1") == null)){
		    	jbSql.append(" and ("+map.get("ZFTypeTable")+".Receive_Date is not null)");
		    }else if((map.get("IsReceive") == null) && (map.get("IsReceive1") != null)){
		    	jbSql.append(" and ("+map.get("ZFTypeTable")+".Receive_Date is null)");
		    }
		}else if((map.get("IsSend") == null) && (map.get("IsSend1") != null) ){
			jbSql.append(" and ("+map.get("ZFTypeTable")+".send_date is null)");
		}
		return jbSql.toString();
	}
	
	/**
	 * 查询纵向转发查询列表时关联的查询条件: 已归档 
	 * @author mengxy
	 * @createDate 2011-05-17
	 * @param Map key为ZFTypeTable的value代表着转办表还是交办表,其他值为前台传递的参数
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AchieveTypeCode:是否归档,AccuserName:反映人
	 * @return String 已归档查询链接的条件
	 */
	public String getAchievedConditionSQL(Map map){
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer conditionSql = new StringBuffer();
		if(map.get("InputDate")!= null && !"".equals(map.get("InputDate").toString())){
			conditionSql.append(" and petition_basic_info.petition_date >= '"+map.get("InputDate")+"' ");
		}
		if(map.get("InputDate1")!= null && !"".equals(map.get("InputDate1").toString())){
			conditionSql.append(" and petition_basic_info.petition_date <= '"+map.get("InputDate1")+"' ");
		}
		if(map.get("XFNo1")!= null && !"".equals(map.get("XFNo1").toString())){
			conditionSql.append(" and petition_basic_info.petition_No like '%"+map.get("XFNo1")+"%' ");
		}
		if(map.get("XFPrtNo")!= null && !"".equals(map.get("XFPrtNo").toString())){
			conditionSql.append(" and petition_basic_info.petition_Prt_No like '%"+map.get("XFPrtNo")+"%' ");
		}
		if(map.get("AccusedName")!= null && !"".equals(map.get("AccusedName").toString())){
			conditionSql.append(" and petition_accused_info.Accused_Name like '%"+Encrypt.encrypt(map.get("AccusedName").toString())+"%' ");
		}
		if(map.get("AccusedClasses")!= null && !"".equals(map.get("AccusedClasses").toString())){
			conditionSql.append(" and petition_accused_info.Accused_class_code = '"+map.get("AccusedClasses")+"' ");
		}
		if(map.get("AccuserName")!= null && !"".equals(map.get("AccuserName").toString())){
			conditionSql.append(" and petition_accuser_info.Accuser_Name like '%"+Encrypt.encrypt(map.get("AccuserName").toString())+"%' ");
		}
		if(map.get("ToRegionCode")!= null && !"".equals(map.get("ToRegionCode").toString())){
			conditionSql.append(" and substr("+map.get("ZFTypeTable")+".To_Region_Code,1," );
			conditionSql.append( regionFlag );
			conditionSql.append( ")||'" );
			if(regionFlag!=0)
				for(int i=0;i<12-regionFlag;i++){
					conditionSql.append('0');
				}
			conditionSql.append("' = '" );
			conditionSql.append(map.get("ToRegionCode").toString());
			conditionSql.append("' ");
		}
		if(map.get("RRegionCode")!= null && !"".equals(map.get("RRegionCode").toString())){
			conditionSql.append(" and substr(petition_basic_info.issue_Region_Code,1,");
			conditionSql.append( regionFlag );
			conditionSql.append( ")||'" );
			if(regionFlag!=0)
				for(int i=0;i<12-regionFlag;i++){
					conditionSql.append('0');
				}
			conditionSql.append("' = '" );
			conditionSql.append(map.get("RRegionCode").toString());
			conditionSql.append("' ");
		}
		return conditionSql.toString();
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
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param type 类型：UnAchievedState、UnAchieved、Achieved
	 * @return long
	 */
	public long advancedPageQueryCount(String authoritySql,Map map, String type) {
		if(!type.equals("")){
			StringBuffer sql = new StringBuffer("");
			if("UnAchievedState".equals(type)){
				if("1".equals(map.get("ZFTypeCode").toString())){//转办
					sql.append("select count(0) xfno ");
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append("select count(0) xfno ");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}else{
					sql.append("select sum(temp.xfno) from (");
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append("select count(0) xfno ");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append( " union ");
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append("select count(0) xfno ");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append(") temp");
				}
			}else{
				if("1".equals(map.get("ZFTypeCode").toString())){//转办
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append("select count(0) ");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append("select count(0) ");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}else{
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append("select count(0)  from (");
					sql.append("select petition_basic_info.petition_No xfno ");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append(" union ");
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append("select petition_basic_info.petition_No xfno ");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append(")");
				}
			}
			return jdbcTemplate.queryForLong(sql.toString());
		}
		return 0;
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
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param type 类型：UnAchievedState、UnAchieved、Achieved
	 * @param start 
	 * @param limit 
	 * @return
	 */
	public List<Map<String, Object>> advancedPageQuery(String authoritySql,Map map, String type,String sort,String dir,int start,int limit) {
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer("");
		if(!type.equals("")){
			sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
			if("UnAchievedState".equals(type)){//查询state表
				if("1".equals(map.get("ZFTypeCode").toString())){//转办
					sql.append("SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_No xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
					sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses, ");
					sql.append("petition_accuser_info.accuser_name accuserName,petition_trans_deal_info.To_Region_name toRegionName,petition_trans_deal_info.input_date inputDate, ");
					sql.append("petition_circulation_state_info.activity_no activityno,petition_trans_deal_info.Send_Date sendDate,'转办' zFTypeCode , ");
					sql.append("(case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive,w.alarm_active alarmActive ");
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
					sql.append("SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
					sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses, ");
					sql.append("petition_accuser_info.accuser_name accuserName,petition_assign_info.To_Region_name toRegionName,petition_assign_info.input_date inputDate, ");
					sql.append("petition_circulation_state_info.activity_no activityno ,petition_assign_info.Send_Date sendDate , ");
					sql.append("(case when petition_deal_info.deal_type_code = '0202' then '交办要情况' when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode , ");
					sql.append("(case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive,w.alarm_active alarmActive "); 
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}else{
					sql.append("SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_No xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
					sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses, ");
					sql.append("petition_accuser_info.accuser_name accuserName,petition_trans_deal_info.To_Region_name toRegionName,petition_trans_deal_info.input_date inputDate, ");
					sql.append("petition_circulation_state_info.activity_no activityno,petition_trans_deal_info.Send_Date sendDate,'转办' zFTypeCode , ");
					sql.append("(case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive,w.alarm_active alarmActive "); 
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append("union  SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
					sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses, ");
					sql.append("petition_accuser_info.accuser_name accuserName,petition_assign_info.To_Region_name toRegionName,petition_assign_info.input_date inputDate, ");
					sql.append("petition_circulation_state_info.activity_no activityno ,petition_assign_info.Send_Date sendDate , ");
					sql.append("(case when petition_deal_info.deal_type_code = '0202' then '交办要情况' when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode , ");
					sql.append("(case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive,w.alarm_active alarmActive "); 
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append(getUnAchievedStateSQL(map));
					sql.append(getUnAchievedStateConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
				}
			}else{
				if("1".equals(map.get("ZFTypeCode").toString())){//转办
					map.put("ZFTypeTable", "trans");
					sql.append(" select distinct * from (SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_No xfno,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
					sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses, ");
					sql.append("petition_accuser_info.accuser_name accuserName, petition_trans_deal_info.To_Region_Name toRegionName, petition_trans_deal_info.input_date inputDate, ");
					sql.append("petition_circulation_state_info.activity_no activityno, petition_trans_deal_info.Send_Date sendDate,'转办' zFTypeCode, ");
					sql.append("(case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive ");

					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append(" ) as z  ");
				}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
					map.put("ZFTypeTable", "assign");
					sql.append(" select distinct * from (SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,");
					sql.append(" petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses," );
					sql.append(" petition_accuser_info.accuser_name accuserName, petition_assign_info.To_Region_Name toRegionName,");
					sql.append(" petition_assign_info.input_date inputDate,petition_circulation_state_info.activity_no activityno,petition_assign_info.Send_Date sendDate,");
					sql.append(" (case when petition_deal_info.deal_type_code = '0202' then '交办要情况' ");
					sql.append(" when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode,");
					sql.append(" (case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend,");
					sql.append(" (case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive,");
					sql.append("w.alarm_active alarmActive ");
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append(" ) as z  ");
				}else{
					map.put("ZFTypeTable", "trans");
					sql.append(" select distinct * from (SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,");
					sql.append(" petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses,");
					sql.append(" petition_accuser_info.accuser_name accuserName, petition_trans_deal_info.To_Region_Name toRegionName,");
					sql.append(" petition_trans_deal_info.input_date inputDate,petition_circulation_state_info.activity_no activityno,petition_trans_deal_info.Send_Date sendDate,'转办' zFTypeCode,");
					sql.append(" (case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend,");
					sql.append(" (case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, ");
					sql.append("w.alarm_active alarmActive ");
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("' ");
						}
					}
					sql.append(authoritySql);
					sql.append(" union ");
					map.put("ZFTypeTable", "assign");
					sql.append(" SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,");
					sql.append(" petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_name regionName,petition_accused_info.accused_class_name accusedClasses," );
					sql.append(" petition_accuser_info.accuser_name accuserName, petition_assign_info.To_Region_Name toRegionName,");
					sql.append(" petition_assign_info.input_date inputDate,petition_circulation_state_info.activity_no activityno,petition_assign_info.Send_Date sendDate,");
					sql.append(" (case when petition_deal_info.deal_type_code = '0202' then '交办要情况' ");
					sql.append(" when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode,");
					sql.append(" (case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend,");
					sql.append(" (case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, ");
					sql.append("w.alarm_active alarmActive ");
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append(getUnAchievedSQL(map));
					sql.append(getAchievedConditionSQL(map));
					if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
						if("verTrans2".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and  w.alarm_active is null ");
						}else if("verTrans4".equals(map.get("ALARMACTIVE").toString().trim())){
							sql.append(" and ( w.alarm_active = 'ChaoS' or w.alarm_active = 'ZhongDCS' or w.alarm_active = 'YanZCS' ) ");
						}else{
							sql.append(" and  w.alarm_active = '");
							sql.append(map.get("ALARMACTIVE"));
							sql.append("'");
						}
					}
					sql.append(authoritySql);
					sql.append(" ) as z  ");
				}
			}
			
			//拼装排序字段
			if(sort!=null && !"".equals(sort)){
				sql.append(" order by ");
				sql.append(sort);
				if(dir!=null && !"".equals(dir)){
					sql.append(" ");
					sql.append(dir);
				}
			}else{
				sql.append(" order by xfNo desc");
			}
			sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
			
			return jdbcTemplate.queryForList(sql.toString());
		}
		return null;
	}
	
	/**
	 * 纵向转发分页查询钻取的
	 * @author ljx
	 * @date 2011-07-21
	 * @param String authoritySql 权限Sql
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param type 类型：UnAchievedState、UnAchieved、Achieved
	 * @param start 
	 * @param limit 
	 * @return
	 */
	public List<Map<String, Object>> advancedPageQueryDrill(String authoritySql,Map map, String sort,String dir,int start,int limit) {
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer("");
			sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
			//查询state表
			if("1".equals(map.get("ZFTypeCode").toString())){//转办
				sql.append("SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,petition_accused_info.accused_name accusedName, "); 
				sql.append("petition_basic_info.issue_region_code regionName,petition_accused_info.accused_class_name accusedClasses,petition_accuser_info.accuser_name accuserName, ");
				sql.append("petition_trans_deal_info.To_Region_Name toRegionName,petition_trans_deal_info.input_date inputDate,petition_trans_deal_info.Send_Date sendDate, ");
				sql.append("'转办' zFTypeCode ,(case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
				sql.append("(case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive ");
				sql.append(authoritySql);
				map.put("ZFTypeTable", "petition_trans_deal_info");
				sql.append(getUnAchievedStateSQL2(map));
			}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
				sql.append("SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,petition_accused_info.accused_name accusedName, ");
				sql.append("petition_basic_info.issue_region_code regionName,petition_accused_info.accused_class_name accusedClasses,petition_accuser_info.accuser_name accuserName, ");
				sql.append("petition_assign_info.To_Region_Name toRegionName,petition_assign_info.input_date inputDate,petition_assign_info.Send_Date sendDate, ");
				sql.append("(case when petition_deal_info.deal_type_code = '0202' then '交办要情况' when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode , ");
				sql.append("(case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
				sql.append("(case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive ");
				map.put("ZFTypeTable", "petition_assign_info");
				sql.append(getUnAchievedStateSQL2(map));
			}else{
				sql.append("SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,petition_accused_info.accused_name accusedName, ");
				sql.append("petition_basic_info.issue_region_code regionName,petition_accused_info.accused_class_name accusedClasses,petition_accuser_info.accuser_name accuserName, ");
				sql.append("petition_trans_deal_info.To_Region_Name toRegionName,petition_trans_deal_info.input_date inputDate,petition_trans_deal_info.Send_Date sendDate, ");
				sql.append("'转办' zFTypeCode ,(case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
				sql.append("(case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive ");
				map.put("ZFTypeTable", "petition_trans_deal_info");
				sql.append(getUnAchievedStateSQL2(map));		
				sql.append(" union  SELECT petition_basic_info.petition_title petitionTitle,petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
				sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_code regionName,petition_accused_info.accused_class_name accusedClasses, ");
				sql.append("petition_accuser_info.accuser_name accuserName,petition_assign_info.To_Region_Name toRegionName,petition_assign_info.input_date inputDate, ");
				sql.append("petition_assign_info.Send_Date sendDate, ");
				sql.append("(case when petition_deal_info.deal_type_code = '0202' then '交办要情况' when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode , ");
				sql.append("(case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
				sql.append("(case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive "); 
				map.put("ZFTypeTable", "petition_assign_info");
				sql.append(getUnAchievedStateSQL2(map));
			}
			
			//拼装排序字段
			if(sort!=null && !"".equals(sort)){
				sql.append(" order by ");
				sql.append(sort);
				if(dir!=null && !"".equals(dir)){
					sql.append(" ");
					sql.append(dir);
				}
			}else{
				sql.append(" order by xfNo desc");
			}
			sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
			
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
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param type 类型：UnAchievedState、UnAchieved、Achieved
	 * @return long
	 */
	public long advancedPageQueryDrillCount(String authoritySql,Map map, String type) {
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer("");
		if(!type.equals("")){
			if("UnAchievedState".equals(type)){//查询state表
				if("1".equals(map.get("ZFTypeCode").toString())){//转办
					sql.append("select count(0) ");
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedStateSQL2(map));
				}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
					sql.append("select count(0) ");
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append(getUnAchievedStateSQL2(map));
				}else{
					sql.append(" select count(0) from ( ");
					sql.append("SELECT petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate,petition_accused_info.accused_name accusedName, ");
					sql.append("petition_basic_info.issue_region_code regionName,petition_accused_info.accused_class_name accusedClasses,petition_accuser_info.accuser_name accuserName, ");
					sql.append("petition_trans_deal_info.To_Region_Name toRegionName,petition_trans_deal_info.input_date inputDate,petition_trans_deal_info.Send_Date sendDate, ");
					sql.append("'转办' zFTypeCode ,(case when petition_trans_deal_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_trans_deal_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive ");
					map.put("ZFTypeTable", "petition_trans_deal_info");
					sql.append(getUnAchievedStateSQL2(map));		
					sql.append(" union  SELECT petition_basic_info.petition_no xfNo,petition_basic_info.petition_prt_no xfPrtNo,petition_basic_info.petition_date xfDate, ");
					sql.append("petition_accused_info.accused_name accusedName,petition_basic_info.issue_region_code regionName,petition_accused_info.accused_class_name accusedClasses, ");
					sql.append("petition_accuser_info.accuser_name accuserName,petition_assign_info.To_Region_Name toRegionName,petition_assign_info.input_date inputDate, ");
					sql.append("petition_assign_info.Send_Date sendDate, ");
					sql.append("(case when petition_deal_info.deal_type_code = '0202' then '交办要情况' when petition_deal_info.deal_type_code = '0201' then '交办要结果' else '' end) zFTypeCode , ");
					sql.append("(case when petition_assign_info.Send_Date is null then '未传输' else '已传输' end) isSend, ");
					sql.append("(case when petition_assign_info.Receive_Date is null then '未接收' else '已接收' end) isReceive, w.alarm_active alarmActive "); 
					map.put("ZFTypeTable", "petition_assign_info");
					sql.append(getUnAchievedStateSQL2(map));
					sql.append(" ) ");
				}
			}
			
			return jdbcTemplate.queryForLong(sql.toString());
		}
		return 0;
	}
	
	/**
	 * 查找下级区域，或者转发区域
	 * @return List<Map>
	 */
	public List<Map<String, Object>> getToRegionCodeList(){
		String sql = "select regioncode,regionname from xfsregion where parentregioncode = (SELECT sysvarvalue FROM XFSVARIABLE where sysvarcode = 'CurRegionCode')";
		return jdbcTemplate.queryForList(sql);
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
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param type 类型：UnAchievedState、UnAchieved、Achieved
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String doRegionChart(String authoritySql,Map map) throws ParseException {
			StringBuilder codeStr = new StringBuilder(); 
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			codeStr.append(" select tt.regionName,tt.RECEIVE,count(0) c from ( ");
			if("1".equals(ZFTypeCode)){
				codeStr.append(" select petition_basic_info.petition_no , petition_trans_deal_info.To_Region_Name regionName , ");
				codeStr.append(" (case  when ( petition_trans_deal_info.RECEIVE_DATE is null ) then '1' when (petition_trans_deal_info.RECEIVE_DATE is not null) then '2' end) RECEIVE ");
				map.put("ZFTypeTable", "petition_trans_deal_info");
				codeStr.append(getUnAchievedStateSQL(map));
				codeStr.append(getUnAchievedStateConditionSQL(map));
				codeStr.append(authoritySql);
			}else if("2".equals(ZFTypeCode)){
				codeStr.append(" select petition_basic_info.petition_no , petition_assign_info.To_Region_Name regionName , ");
				codeStr.append(" (case  when ( petition_assign_info.RECEIVE_DATE is null ) then '1' when (petition_assign_info.RECEIVE_DATE is not null) then '2' end) RECEIVE ");
				map.put("ZFTypeTable", "petition_assign_info");
				codeStr.append(getUnAchievedStateSQL(map));
				codeStr.append(getUnAchievedStateConditionSQL(map));
				codeStr.append(authoritySql);
			}else{
				codeStr.append(" select petition_basic_info.petition_no , petition_trans_deal_info.To_Region_Name regionName , ");
				codeStr.append(" (case  when ( petition_trans_deal_info.RECEIVE_DATE is null ) then '1' when (petition_trans_deal_info.RECEIVE_DATE is not null) then '2' end) RECEIVE ");
				map.put("ZFTypeTable", "petition_trans_deal_info");
				codeStr.append(getUnAchievedStateSQL(map));
				codeStr.append(getUnAchievedStateConditionSQL(map));
				codeStr.append(authoritySql);
				codeStr.append(" union ");
				codeStr.append(" select petition_basic_info.petition_no , petition_assign_info.To_Region_Name regionName , ");
				codeStr.append(" (case  when ( petition_assign_info.RECEIVE_DATE is null ) then '1' when (petition_assign_info.RECEIVE_DATE is not null) then '2' end) RECEIVE ");
				map.put("ZFTypeTable", "petition_assign_info");
				codeStr.append(getUnAchievedStateSQL(map));
				codeStr.append(getUnAchievedStateConditionSQL(map));
				codeStr.append(authoritySql);
			}
			codeStr.append(" ) tt group by tt.regionName,RECEIVE order by tt.regionName desc ");
			List<Map<String, Object>> list = jdbcTemplate.queryForList(codeStr.toString());
			if(null == list || list.size()<1){
				return "<chart></chart>";
			}
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='不同下级转发信访量对比' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='区域名称' yAxisName='信访量' >");
			strXML.append("<categories>");
			String oldValue = "";
			String newValue = "";
			StringBuffer strXMLyCoun = new StringBuffer();
			StringBuffer strXMLwCoun = new StringBuffer();
			String w = "";
			String y = "";
			if(null != list && list.size() > 0){
				oldValue = replaceChart(((Map)list.get(0)).get("regionname").toString().trim());
			}
			int t = 0;
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = (Map) list.get(i);
				newValue = replaceChart(mapXML.get("regionname").toString().trim());
				if( !oldValue.equals(newValue) ){
					strXML.append("<category label='"+ oldValue.trim() +"' />");
					if("".equals(w))
						strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='0' />");
					else
						strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='"+w+"' link='JavaScript:verTransRegion("+t+",1);' />");
					if("".equals(y))
						strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='0' />");
					else
						strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='"+y+"' link='JavaScript:verTransRegion("+t+",2);' />");
					w = "";
					y = "";
					oldValue = newValue;
					t++;
				}
				if("1".equals(mapXML.get("receive").toString().trim()))
					w = mapXML.get("c").toString();
				else if("2".equals(mapXML.get("receive").toString().trim()))
					y = mapXML.get("c").toString();
			}
			strXML.append("<category label='"+ newValue.trim() +"' />");
			if("".equals(w))
				strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='0' />");
			else
				strXMLwCoun.append("<set rname='"+oldValue.trim()+"' value='"+w+"' link='JavaScript:verTransRegion("+t+",1);' />");
			if("".equals(y))
				strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='0' />");
			else
				strXMLyCoun.append("<set rname='"+oldValue.trim()+"' value='"+y+"' link='JavaScript:verTransRegion("+t+",2);' />");
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
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionName:转发机构,AccuserName:反映人
	 * @param type 类型：UnAchievedState、UnAchieved
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String doReplyChart(String authoritySql,Map map) throws ParseException {
		String regioncode = getRegionCode();
			StringBuilder codeStr = new StringBuilder(); 
			String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
			//查询state表
				if("1".equals(ZFTypeCode)){
					codeStr.append(" select count(0) coun,petition_trans_deal_info.To_Region_Name regionName ,w.ALARM_ACTIVE warn ");
					codeStr.append("from petition_basic_info  ");
					codeStr.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
					codeStr.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
					codeStr.append("left join petition_issue_info   on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
					codeStr.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid "); 
					codeStr.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
					codeStr.append("left join petition_trans_deal_info  on petition_deal_info.oid = petition_trans_deal_info.petition_deal_info_oid and petition_deal_info.deal_no = petition_trans_deal_info.deal_no ");
					codeStr.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no and petition_basic_info.region_code = w.region_code  and w.alarm_type_code='ZongXZF' ");  
					codeStr.append(" where  petition_trans_deal_info.To_Region_Code <> '' and petition_basic_info.region_code = '"+regioncode+"' ");
					codeStr.append(" and petition_trans_deal_info.back_date is null   and petition_trans_deal_info.send_date is not null and petition_trans_deal_info.Receive_Date is null ");
					codeStr.append(" and petition_trans_deal_info.to_region_code = '"+map.get("CurrRegion")+"' ");
					codeStr.append(getUnAchievedStateConditionSQL(map));
					codeStr.append(authoritySql);
					codeStr.append(" group by petition_trans_deal_info.To_Region_Name ,w.ALARM_ACTIVE ");
					codeStr.append(" order by petition_trans_deal_info.To_Region_Name desc,w.ALARM_ACTIVE ");
				}else if("2".equals(ZFTypeCode)){
					codeStr.append(" select count(0) coun,petition_assign_info.To_Region_Name regionName ,w.ALARM_ACTIVE warn ");
					codeStr.append("from petition_basic_info  ");
					codeStr.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
					codeStr.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
					codeStr.append("left join petition_issue_info   on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
					codeStr.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid "); 
					codeStr.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
					codeStr.append("left join petition_assign_info  on petition_deal_info.oid = petition_assign_info.petition_deal_info_oid and petition_deal_info.deal_no = petition_assign_info.deal_no ");
					codeStr.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no  and petition_basic_info.region_code = w.region_code and w.alarm_type_code='ZongXZF' ");  
					codeStr.append(" where petition_assign_info.To_Region_Code <> '' and petition_basic_info.region_code = '"+regioncode+"' ");
					codeStr.append(" and petition_assign_info.back_date is null   and petition_assign_info.send_date is not null and petition_assign_info.Receive_Date is null ");
					codeStr.append(" and petition_assign_info.to_region_code = '"+map.get("CurrRegion")+"' ");
					codeStr.append(getUnAchievedStateConditionSQL(map));
					codeStr.append(authoritySql);
					codeStr.append(" group by petition_assign_info.To_Region_Name ,w.ALARM_ACTIVE ");
					codeStr.append(" order by petition_assign_info.To_Region_Name desc,w.ALARM_ACTIVE ");
				}else{ 
					codeStr.append(" select count(0) coun,temp.regionName ,temp.warn from ( ");
					codeStr.append(" select petition_basic_info.petition_no xfno,petition_trans_deal_info.To_Region_Name regionName ,w.ALARM_ACTIVE warn ");
					codeStr.append("from petition_basic_info ");
					codeStr.append("left join petition_accuser_info  on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
					codeStr.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
					codeStr.append("left join petition_issue_info  on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
					codeStr.append("left join petition_circulation_state_info on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid "); 
					codeStr.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
					codeStr.append("left join petition_trans_deal_info  on petition_deal_info.oid = petition_trans_deal_info.petition_deal_info_oid and petition_deal_info.deal_no = petition_trans_deal_info.deal_no ");
					codeStr.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no  and petition_basic_info.region_code = w.region_code  and w.alarm_type_code='ZongXZF' ");  
					codeStr.append(" where petition_trans_deal_info.To_Region_code <> '' and petition_basic_info.region_code = '"+regioncode+"' ");
					codeStr.append(" and petition_trans_deal_info.back_date is null   and petition_trans_deal_info.send_date is not null and petition_trans_deal_info.Receive_Date is null ");
					codeStr.append(" and petition_trans_deal_info.to_region_code = '"+map.get("CurrRegion")+"' ");
					codeStr.append(getUnAchievedStateConditionSQL(map));
					codeStr.append(authoritySql);
					codeStr.append(" union ");
					codeStr.append(" select petition_basic_info.petition_no xfno,petition_assign_info.To_Region_Name regionName ,w.ALARM_ACTIVE warn ");
					codeStr.append("from petition_basic_info  ");
					codeStr.append("left join petition_accuser_info  on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
					codeStr.append("left join petition_accused_info on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
					codeStr.append("left join petition_issue_info   on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
					codeStr.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid "); 
					codeStr.append("left join petition_deal_info  on petition_basic_info.oid = petition_deal_info.petition_basic_info_oid and petition_basic_info.region_code = petition_deal_info.region_code and petition_deal_info.valid_flag = '0' ");
					codeStr.append("left join petition_assign_info on petition_deal_info.oid = petition_assign_info.petition_deal_info_oid and petition_deal_info.deal_no = petition_assign_info.deal_no ");
					codeStr.append("left join warn_state_info w on w.petition_no = petition_basic_info.petition_no  and petition_basic_info.region_code = w.region_code  and w.alarm_type_code='ZongXZF' ");  
					codeStr.append(" where petition_assign_info.To_Region_Code <> '' and petition_basic_info.region_code = '"+regioncode+"' ");
					codeStr.append(" and petition_assign_info.back_date is null   and petition_assign_info.send_date is not null and petition_assign_info.Receive_Date is null ");
					codeStr.append(" and petition_assign_info.to_region_code = '"+map.get("CurrRegion")+"' ");
					codeStr.append(getUnAchievedStateConditionSQL(map));
					codeStr.append(authoritySql);
					codeStr.append(" ) temp ");
					codeStr.append(" group by temp.regionName ,temp.warn ");
					codeStr.append(" order by temp.regionName desc,temp.warn ");
				}
			List<Map<String, Object>> list = jdbcTemplate.queryForList(codeStr.toString());
			if (list == null || list.isEmpty()) {
				return "<chart></chart>";
			}
			StringBuffer strXML = new StringBuffer();
			/* 设置统计图头信息 */
			strXML.append("<chart caption='不同下级未接收时限对比' ");
			strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
			strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
			strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
			strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
			strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
			strXML.append(" palette='2' baseFontSize='12' xAxisName='区域名称' yAxisName='信访量' >");
			strXML.append("<categories>");
			String oldValue = "";
			String newValue = "";
			StringBuffer strXMLweiCoun = new StringBuffer();
			StringBuffer strXMLzcCoun = new StringBuffer();
			StringBuffer strXMLyjCoun = new StringBuffer();
			StringBuffer strXMLcsCoun = new StringBuffer();
			int wei = 0;
			int zc = 0;
			int yj = 0;
			int cs = 0;
			if(null != list && list.size() > 0){
				oldValue = replaceChart(((Map)list.get(0)).get("regionName").toString().trim());
			}
			int t = 0;
			for (int i = 0; i < list.size(); i++) {
				Map mapXML = (Map) list.get(i);
				newValue = replaceChart(mapXML.get("regionName").toString().trim());
				if( !oldValue.equals(newValue) ){
					wei = zc + yj + cs;
					strXML.append("<category label='"+ oldValue +"' />");
					if(wei == 0)
						strXMLweiCoun.append("<set rname = '"+oldValue+"' value='0' />");
					else
						strXMLweiCoun.append("<set rname = '"+oldValue+"' value='"+wei+"' link='JavaScript:reply("+t+",1);' />");				
					if(zc == 0)
						strXMLzcCoun.append("<set rname = '"+oldValue+"' value='0' />");
					else
						strXMLzcCoun.append("<set rname = '"+oldValue+"' value='"+zc+"' link='JavaScript:reply("+t+",2);' />");
					if(yj == 0)
						strXMLyjCoun.append("<set rname = '"+oldValue+"' value='0' />");
					else
						strXMLyjCoun.append("<set rname = '"+oldValue+"' value='"+yj+"' link='JavaScript:reply("+t+",3);' />");
					if(cs == 0)
						strXMLcsCoun.append("<set rname = '"+oldValue+"' value='0' />");
					else
						strXMLcsCoun.append("<set rname = '"+oldValue+"' value='"+cs+"' link='JavaScript:reply("+t+",4);' />");
					wei = 0;
					zc = 0;
					yj = 0;
					cs = 0;
					oldValue = newValue;
					t++;
				}
				if(null == mapXML.get("warn")){
					zc += Integer.parseInt(mapXML.get("coun").toString());
				}else if("YuJ".equals(mapXML.get("warn")))
					yj += Integer.parseInt(mapXML.get("coun").toString());
				else 
					cs += Integer.parseInt(mapXML.get("coun").toString());
			}
			wei = zc + yj + cs;
			strXML.append("<category label='"+ newValue.trim() +"' />");
			if(wei == 0)
				strXMLweiCoun.append("<set rname = '"+oldValue+"' value='0' />");
			else
				strXMLweiCoun.append("<set rname = '"+oldValue+"' value='"+wei+"' link='JavaScript:reply("+t+",1);' />");				
			if(zc == 0)
				strXMLzcCoun.append("<set rname = '"+oldValue+"' value='0' />");
			else
				strXMLzcCoun.append("<set rname = '"+oldValue+"' value='"+zc+"' link='JavaScript:reply("+t+",2);' />");
			if(yj == 0)
				strXMLyjCoun.append("<set rname = '"+oldValue+"' value='0' />");
			else
				strXMLyjCoun.append("<set rname = '"+oldValue+"' value='"+yj+"' link='JavaScript:reply("+t+",3);' />");
			if(cs == 0)
				strXMLcsCoun.append("<set rname = '"+oldValue+"' value='0' />");
			else
				strXMLcsCoun.append("<set rname = '"+oldValue+"' value='"+cs+"' link='JavaScript:reply("+t+",4);' />");
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
	 * 查询纵向转发轨迹查询列表总数
	 * @author mengxy
	 * @createDate 2011-05-24
	 * @param filterTxt
	 * @param filterValue
	 * @return
	 */
	public long getTraceCountByXfno(String filterTxt, String filterValue) {
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer();		
		sql.append("select count(0) ");
		sql.append("from petition_circulation_state_trace_info  ");
		sql.append("where petition_no = '"+filterValue+"'  and region_code = '"+regioncode+"'  ");
		return jdbcTemplate.queryForLong(sql.toString());
	}
	
	/**
	 * 查询横向处理轨迹查询列表总数
	 * @author ljx
	 * @createDate 2011-05-24
	 * @param filterTxt
	 * @param filterValue
	 * @return
	 */
	public long getHorizTraceCountByXfno(String filterTxt, String filterValue) {
		String regioncode = getRegionCode();
		StringBuffer sql = new StringBuffer();		
		sql.append("select count(0) ");
		sql.append("from petition_circulation_state_trace_info  ");
		sql.append("where petition_no = '"+filterValue+"'  and region_code = '"+regioncode+"' ");
		return jdbcTemplate.queryForLong(sql.toString());
	}
	
	/**
	 * 查询纵向转发轨迹查询列表: 返回json数据
	 * @author mengxy
	 * @createDate 2011-05-24
	 * @param filterTxt
	 * @param filterValue
	 * @param sort
	 * @param dir
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getTraceByXfno(String filterTxt, String filterValue,String sort, String dir,int start,int limit) {
		if("XFNO".equals(filterTxt)){
			String regioncode = getRegionCode();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
			sql.append("select region_name ,creator_name ,operate ,state ,create_date ");
			sql.append("from petition_circulation_state_trace_info  ");
			sql.append("where petition_no = '"+filterValue+"' and region_code = '"+regioncode+"'  ");
			//拼装排序字段
			if(sort!=null && !"".equals(sort)){
				sql.append(" order by ");
				sql.append(sort);
				if(dir!=null && !"".equals(dir)){
					sql.append(" ");
					sql.append(dir);
				}
			}
			sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
			return jdbcTemplate.queryForList(sql.toString());
		}
		return null;
	}

	/**
	 * 查询横向处理轨迹查询列表: 返回json数据
	 * @author ljx
	 * @createDate 2011-05-24
	 * @param filterTxt
	 * @param filterValue
	 * @param sort
	 * @param dir
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getHorizTraceByXfno(String filterTxt, String filterValue,String sort, String dir,int start,int limit) {
		if("XFNO".equals(filterTxt)){
			String regioncode = getRegionCode();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
			sql.append("select region_name ,creator_name ,operate ,state ,create_date ");
			sql.append("from petition_circulation_state_trace_info  ");
			sql.append("where petition_no = '"+filterValue+"' and region_code = '"+regioncode+"' ");
			//拼装排序字段
			if(sort!=null && !"".equals(sort)){
				sql.append(" order by ");
				sql.append(sort);
				if(dir!=null && !"".equals(dir)){
					sql.append(" ");
					sql.append(dir);
				}
			}
			sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
			return jdbcTemplate.queryForList(sql.toString());
		}
		return null;
	}
	
	/**
	 * 横向处理查询未归档的查询条件
	 * @author ljx
	 * @date 2011-05-19
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @param regionFlag1,zero
	 * @return String
	 */
	public String whereUnAchieved(Map map,int regionFlag){
		StringBuffer strSQL = new StringBuffer();
		if(null != map.get("XFNo1") && !"".equals(map.get("XFNo1").toString().trim())){
			strSQL.append(" and petition_basic_info.petition_no like '%");
			strSQL.append(map.get("XFNo1"));
			strSQL.append("%'");
		}
		if(null != map.get("InputDate") && !"".equals(map.get("InputDate").toString().trim())){
			strSQL.append(" and petition_basic_info.petition_date >= '");
			strSQL.append(map.get("InputDate"));
			strSQL.append("'");
		}
		if(null != map.get("InputDate1") && !"".equals(map.get("InputDate1").toString().trim())){
			strSQL.append(" and petition_basic_info.petition_date <= '");
			strSQL.append(map.get("InputDate1"));
			strSQL.append("'");
		}
		if(null != map.get("SourceClasses") && !"".equals(map.get("SourceClasses").toString().trim())){
			strSQL.append(" and petition_basic_info.petition_source_code = '");
			strSQL.append(map.get("SourceClasses"));
			strSQL.append("'");
		}
		if(null != map.get("XFClassCode") && !"".equals(map.get("XFClassCode").toString().trim())){
			strSQL.append(" and petition_basic_info.petition_class_code = '");
			strSQL.append(map.get("XFClassCode"));
			strSQL.append("'");
		}
		if(null != map.get("ActivityCode") && !"".equals(map.get("ActivityCode").toString().trim())){
			strSQL.append(" and petition_circulation_state_info.Activity_No = '");
			strSQL.append(map.get("ActivityCode"));
			strSQL.append("'");
		}
		if(null != map.get("AccusedName") && !"".equals(map.get("AccusedName").toString().trim())){
			strSQL.append(" and petition_accused_info.accused_name like '%");
			strSQL.append(Encrypt.encrypt(map.get("AccusedName").toString()));
			strSQL.append("%'");
		}
		if(null != map.get("AccuserName") && !"".equals(map.get("AccuserName").toString().trim())){
			strSQL.append(" and petition_accuser_info.accuser_name like '%");
			strSQL.append(Encrypt.encrypt(map.get("AccuserName").toString()));
			strSQL.append("%'");
		}
		if(null != map.get("AccusedClasses") && !"".equals(map.get("AccusedClasses").toString().trim())){
			strSQL.append(" and petition_accused_info.accused_class_code = '");
			strSQL.append(map.get("AccusedClasses"));
			strSQL.append("'");
		}
		if(null != map.get("ToRegionName") && !"".equals(map.get("ToRegionName").toString().trim())){
			strSQL.append(" and petition_basic_info.issue_region_name = '");
			strSQL.append(map.get("ToRegionName"));
			strSQL.append("'");
		}
		if(null != map.get("ToRegionCode") && !"".equals(map.get("ToRegionCode").toString().trim())){
			if("isnull".equals(map.get("ToRegionCode").toString())){
				strSQL.append(" and petition_basic_info.issue_region_code is null ");
			}else{
				strSQL.append(" and substr(petition_basic_info.issue_region_code,1,");
				strSQL.append( regionFlag );
				strSQL.append( ")||'" );
				if(regionFlag!=0)
					for(int i=0;i<12-regionFlag;i++){
						strSQL.append('0');
					}
				strSQL.append("' = '" );
				strSQL.append(map.get("ToRegionCode"));
				strSQL.append("'");
			}
		}
		if(null != map.get("WTCode") && !"".equals(map.get("WTCode").toString().trim())){
			if("isnull".equals(map.get("WTCode").toString())){
				strSQL.append(" and petition_issue_info.issue_type_code is null  ");
			}else if("w".equals(map.get("WTCode").toString())){
				strSQL.append(" and substr(petition_issue_info.issue_type_code,1,1)='");
				strSQL.append(map.get("WTCode"));
				strSQL.append("'");
			}else{
				strSQL.append(" and petition_issue_info.issue_type_code = '");
				strSQL.append(map.get("WTCode"));
				strSQL.append("'");
			}
		}
		return strSQL.toString();
	}
	
	/**
	 * 横向处理查询记录总数
	 * @author ljx
	 * @date 2011-05-19
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @param type 类型：UnAchievedState、Achieved
	 * @return long
	 */
	public long horizWarningPageQueryCount(String sql,Map map) {
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer strSQL = new StringBuffer();
		String whereUnAchievedString = whereUnAchieved(map ,regionFlag);
		strSQL.append("select count(0) from ( ");
		strSQL.append("select petition_basic_info.region_code region_code ,petition_basic_info.petition_No XFNO from petition_basic_info ");
		strSQL.append("left join petition_accuser_info  on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		strSQL.append("left join petition_accused_info on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		strSQL.append("left join petition_issue_info  on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
		strSQL.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		strSQL.append("where  petition_circulation_state_info.activity_no <> '0000000205' and petition_circulation_state_info.activity_no <> '0000000206' and petition_circulation_state_info.activity_no <> '0000000207' ");
		strSQL.append("and petition_circulation_state_info.activity_no <> '0000000208' and petition_circulation_state_info.activity_no <> '0000000209' and petition_circulation_state_info.activity_no <> '9' and petition_basic_info.region_code = '");
		strSQL.append(regioncode);
		strSQL.append("'");		
		strSQL.append(whereUnAchievedString);
//		权限串 
		strSQL.append(sql);
		strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code and alarm_type_code = 'HengXCL'");
		if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
			strSQL.append(" where  warn_state_info.alarm_active = '");
			strSQL.append(map.get("ALARMACTIVE"));
			strSQL.append("'");
		}
		return jdbcTemplate.queryForLong(strSQL.toString());
	}
	
	/**
	 * 横向处理分页查询
	 * @author ljx
	 * @date 2011-05-19
	 * @param map
	 * XFNo1：信访编号
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @param type 类型：UnAchievedState、Achieved
	 * @param start 
	 * @param limit 
	 * @param sort
	 * @param dir
	 * @return list
	 */
	public List<Map<String, Object>> horizWarningPageQuery(String sql,Map map, String sort,String dir,int start,int limit) {
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		String whereUnAchievedString = whereUnAchieved(map ,regionFlag );
		strSQL.append("select warn_state_info.alarm_active ,temp.* from ( ");
		strSQL.append("select petition_basic_info.petition_title petitionTitle,petition_basic_info.region_code region_code,petition_basic_info.petition_No XFNO,petition_basic_info.petition_Date  XFDATE,petition_basic_info.petition_type_name XFTYPE," );
		strSQL.append("petition_basic_info.petition_source_name XFSOURCE ,petition_basic_info.petition_class_name XFCLASS,petition_accuser_info.accuser_name ACCUSERNAME, ");
		strSQL.append("petition_accused_info.accused_name  ACCUSEDNAME,petition_basic_info.issue_region_name TOREGIONCODE, ");
		strSQL.append("petition_accused_info.accused_class_name ACCUSEDCLASSES,petition_circulation_state_info.activity_name ACTIVITY ,petition_basic_info.deal_type_name DEALTYPE, ");
		strSQL.append("petition_issue_info.issue_type_name WT,petition_issue_info.issue_type_code WTCode ");
		strSQL.append("from petition_basic_info ");
		strSQL.append("left join petition_accuser_info   on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		strSQL.append("left join petition_issue_info   on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
		strSQL.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		strSQL.append("where  petition_circulation_state_info.activity_no <> '0000000205' and petition_circulation_state_info.activity_no <> '0000000206' and petition_circulation_state_info.activity_no <> '0000000207' ");
		strSQL.append("and petition_circulation_state_info.activity_no <> '0000000208' and petition_circulation_state_info.activity_no <> '0000000209' and petition_circulation_state_info.activity_no <> '9' and petition_basic_info.region_code = '");
		strSQL.append(regioncode);
		strSQL.append("'");
		strSQL.append(whereUnAchievedString);
//		权限串 
		strSQL.append(sql);
		strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code and alarm_type_code = 'HengXCL'");
		if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
			strSQL.append(" where  warn_state_info.alarm_active = '");
			strSQL.append(map.get("ALARMACTIVE"));
			strSQL.append("'");
		}
		//拼装排序字段
		if(sort!=null && !"".equals(sort) ){
			if(!"ALARM_ACTIVE".equals(sort)){
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
	 * 横向处理“超时信访量对比”柱形图需要的数据集的方法
	 * @author ljx
	 * @date 2011-05-25
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String horizWarningOverTimeChart(String sql,Map map) throws ParseException {
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer strSQL = new StringBuffer();
		String whereUnAchievedString = whereUnAchieved(map ,regionFlag);
		strSQL.append("select count(0) c,warn_state_info.alarm_active w from ( ");
		strSQL.append("SELECT petition_basic_info.region_code region_code ,petition_basic_info.Petition_no XFNO,petition_circulation_state_info.activity_name ACTIVITY ");
		strSQL.append("from petition_basic_info ");
		strSQL.append("left join petition_accuser_info  accuser on petition_basic_info.oid = accuser.petition_basic_info_oid and accuser.accuser_num =1 ");
		strSQL.append("left join petition_accused_info accused on petition_basic_info.oid = accused.petition_basic_info_oid and accused.accused_num = 1 ");
		strSQL.append("left join petition_issue_info issue  on petition_basic_info.oid = issue.petition_basic_info_oid and issue.issue_num = 1 ");
		strSQL.append("left join petition_circulation_state_info on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		strSQL.append("where  petition_circulation_state_info.activity_no <> '0000000205' and petition_circulation_state_info.activity_no <> '0000000206' and petition_circulation_state_info.activity_no <> '0000000207' ");
		strSQL.append("and petition_circulation_state_info.activity_no <> '0000000208' and petition_circulation_state_info.activity_no <> '0000000209'  and petition_circulation_state_info.activity_no <> '9' ");
		strSQL.append("and petition_basic_info.region_code ='");
		strSQL.append(regioncode);
		strSQL.append("' ");
		strSQL.append(whereUnAchievedString);
//				权限串 
		strSQL.append(sql);
		strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code and alarm_type_code = 'HengXCL' where warn_state_info.alarm_active is not null ");
		if(null != map.get("ALARMACTIVE") && !"".equals(map.get("ALARMACTIVE").toString().trim())){
			strSQL.append(" and  warn_state_info.alarm_active = '");
			strSQL.append(map.get("ALARMACTIVE"));
			strSQL.append("'");
		}
		strSQL.append("group by warn_state_info.alarm_active ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(strSQL.toString());
		if(list.size()<1){
			return "<chart></chart>";
		}
		StringBuffer strXML = new StringBuffer();
		/* 设置统计图头信息 */
		strXML.append("<chart caption='预警超时信访量对比' ");
		strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
		strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
		strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
		strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
		strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
		strXML.append(" palette='2' baseFontSize='12' xAxisName='超时等级' yAxisName='信访量' >");
		String yujingValue = "";
		String chaoshiValue = "";
		String zhongchaoshiValue = "";
		String yanChaoshiValue = "";
		for (int i = 0; i < list.size(); i++) {
			Map mapXML = (Map) list.get(i);
			if(mapXML.get("w").equals("YuJ")){
				yujingValue = mapXML.get("c").toString(); 
			}else if(mapXML.get("w").equals("ChaoS")){
				chaoshiValue = mapXML.get("c").toString();
			}else if(mapXML.get("w").equals("ZhongDCS")){
				zhongchaoshiValue = mapXML.get("c").toString();
			}else if(mapXML.get("w").equals("YanZCS")){
				yanChaoshiValue = mapXML.get("c").toString();
			}
		}
		if(!"".equals(yujingValue)){
			strXML.append("<set yujing='YuJ' label='预警' value='"+yujingValue+"' link='JavaScript:overTime(1);' />");
		}else{
			strXML.append("<set yujing='YuJ' label='预警' value='"+yujingValue+"' />");
		}
		if(!"".equals(chaoshiValue)){
			strXML.append("<set yujing='ChaoS' label='超时' value='"+chaoshiValue+"' link='JavaScript:overTime(2);' />");
		}else{
			strXML.append("<set yujing='ChaoS' label='超时' value='"+chaoshiValue+"'  />");
		}
		if(!"".equals(zhongchaoshiValue)){
			strXML.append("<set yujing='ZhongDCS' label='中度超时' value='"+zhongchaoshiValue+"' link='JavaScript:overTime(3);' />");
		}else{
			strXML.append("<set yujing='ZhongDCS' label='中度超时' value='"+zhongchaoshiValue+"'  />");
		}
		if(!"".equals(yanChaoshiValue)){
			strXML.append("<set yujing='YanZCS' label='严重超时' value='"+yanChaoshiValue+"' link='JavaScript:overTime(4);' />");
		}else{
			strXML.append("<set yujing='YanZCS' label='严重超时' value='"+yanChaoshiValue+"' />");
		}
		strXML.append("</chart>");
		return strXML.toString();
	}
	
	/**
	 * 横向处理“不同下级信访量对比”柱形图需要的数据集的方法
	 * @author ljx
	 * @date 2011-06-27
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @param type 类型：UnAchievedState、Achieved
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String horizWarningRegionChart(String sql,Map map) throws ParseException {
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer strSQL = new StringBuffer();
		//未归档查询state表
		String whereUnAchievedString = whereUnAchieved(map ,regionFlag);
		strSQL.append("select toregioncode ,toregionname ,count(0) num from ( ");
		strSQL.append("SELECT petition_basic_info.petition_no XFNO,petition_basic_info.sub_issue_region_code TOREGIONCODE,petition_basic_info.sub_issue_region_name TOREGIONNAME "); 
		strSQL.append("from petition_basic_info ");
		strSQL.append("left join petition_accuser_info  on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		strSQL.append("left join petition_issue_info  on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
		strSQL.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		strSQL.append("where  petition_circulation_state_info.activity_no <> '0000000205' and petition_circulation_state_info.activity_no <> '0000000206' and petition_circulation_state_info.activity_no <> '0000000207' ");
		strSQL.append("and petition_circulation_state_info.activity_no <> '0000000208' and petition_circulation_state_info.activity_no <> '0000000209'  and petition_circulation_state_info.activity_no <> '9' ");
		strSQL.append("and petition_basic_info.region_code ='");  
		strSQL.append(regioncode);
		strSQL.append("' ");
		strSQL.append(whereUnAchievedString);
//				权限串 
		strSQL.append(sql);
		strSQL.append(" ) group by toregionname,toregioncode ");
		strSQL.append(" order by toregioncode ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(strSQL.toString());
		if(list.size()<1){
			return "<chart></chart>";
		}
		StringBuffer strXML = new StringBuffer();
		/* 设置统计图头信息 */
		strXML.append("<graph  caption='下级区域信访量对比' ");
		strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
		strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
		strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
		strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
		strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
		strXML.append(" palette='2' baseFontSize='12' xAxisName='所属区域' yAxisName='信访量' >");
		for (int i = 0; i < list.size(); i++) {
			Map mapXML = (Map) list.get(i);
			if(null != mapXML.get("toregioncode")){
				strXML.append("<set regioncode='"+mapXML.get("toregioncode").toString()+"' label='"+replaceChart(mapXML.get("toregionname").toString())+"' value='"+mapXML.get("num")+"' link='JavaScript:region("+i+");' />");
			}else{
				strXML.append("<set regioncode='isnull' label='其它' value='"+mapXML.get("num")+"' link='JavaScript:region("+i+");' />");
			}
		}
		strXML.append("</graph >");
		return strXML.toString();
	}
	
	/**
	 * 横向处理“不同问题类别信访量对比”柱形图需要的数据集的方法
	 * @author ljx
	 * @date 2011-06-28
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String horizWarningWTChart(String sql,Map map) throws ParseException {
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer strSQL = new StringBuffer();
		//未归档查询state表
		String whereUnAchievedString = whereUnAchieved(map ,regionFlag);
		strSQL.append("select WT ,WTCode ,count(0) num from ( ");
		strSQL.append("SELECT petition_basic_info.petition_no XFNO,(case when substr(petition_issue_info.issue_type_code,1,1)='w' then '业务范围外' else petition_issue_info.issue_type_name end ) WT , ");
		strSQL.append("(case when substr(petition_issue_info.issue_type_code,1,1)='w' then 'w' else petition_issue_info.issue_type_code end ) WTCode "); 
		strSQL.append("from petition_basic_info ");
		strSQL.append("left join petition_accuser_info  on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		strSQL.append("left join petition_accused_info on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		strSQL.append("left join petition_issue_info  on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
		strSQL.append("left join petition_circulation_state_info  on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid "); 
		strSQL.append("where  petition_circulation_state_info.activity_no <> '0000000205' and petition_circulation_state_info.activity_no <> '0000000206' and petition_circulation_state_info.activity_no <> '0000000207' ");
		strSQL.append("and petition_circulation_state_info.activity_no <> '0000000208' and petition_circulation_state_info.activity_no <> '0000000209'  and petition_circulation_state_info.activity_no <> '9' ");
		strSQL.append("and petition_basic_info.region_code ='");
		strSQL.append(regioncode);
		strSQL.append("'");
		strSQL.append(whereUnAchievedString);
//				权限串 
		strSQL.append(sql);
		strSQL.append(" ) group by WT ,WTCode ");
		strSQL.append(" order by WTCode ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(strSQL.toString());
		if(list.size()<1){
			return "<chart></chart>";
		}
		StringBuffer strXML = new StringBuffer();
		/* 设置统计图头信息 */
		strXML.append("<chart caption='问题类别信访量对比' ");
		strXML.append(" shownames='1' showSum='1' baseFontSize='13' yAxisValuesStep='1' ");
		strXML.append(" showShadow='1' legendAllowDrag='1' showDivLineSecondaryValue='1' ");
		strXML.append(" showYAxisValues='1' showSecondaryLimits='1' showBorder='1' ");
		strXML.append(" showPlotBorder='0' exportEnabled='1' exportShowMenuItem='1' ");
		strXML.append(" useRoundEdges='1' decimals='0' formatNumberScale='0' numberSuffix='件'");
		strXML.append(" palette='2' baseFontSize='12' xAxisName='问题类别' yAxisName='信访量' >");
		int wint = 0;
		int tsum = 0;
		for (int i = 0; i < list.size(); i++) {
			Map mapXML = (Map) list.get(i);
			if(null != mapXML.get("WT") && !"".equals(mapXML.get("WT"))){
				strXML.append("<set wtcode='"+mapXML.get("WTCode")+"' label='"+mapXML.get("WT")+"' value='"+mapXML.get("num")+"' link='JavaScript:wt("+tsum+");' />");
				tsum++; 
			}else{
				wint += Integer.parseInt(mapXML.get("num").toString());
			}
		}
		strXML.append("<set wtcode='isnull' label='其它' value='"+wint+"' link='JavaScript:wt("+tsum+");' />");
		strXML.append("</chart>");
		return strXML.toString();
	}
	
	/**
	 * 横向处理导出Excel方法
	 * @author ljx
	 * @date 2011-06-2
	 * @param sqlSelect 列表查询字段项
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @param type 类型：UnAchievedState、Achieved
	 * @return  list
	 */
	public List<Map<String, Object>> horizWarningExportExcel(String sql,String sqlSelect,Map map) {
		String regioncode = getRegionCode();
		int regionFlag = regioncode.indexOf("00");
		if(regionFlag != -1){
			regionFlag = regionFlag + 2;
		}else{
			regionFlag = 0;
		}
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select "+sqlSelect+" from ( ");
		String whereUnAchievedString = whereUnAchieved(map ,regionFlag );
		strSQL.append("select warn_state_info.alarm_active ,temp.* from ( ");
		strSQL.append("select petition_basic_info.petition_title petitiontitle,petition_basic_info.region_code region_code,petition_basic_info.petition_No XFNO,petition_basic_info.petition_Date  XFDATE,petition_basic_info.petition_type_name XFTYPE," );
		strSQL.append("petition_basic_info.petition_source_name XFSOURCE ,petition_basic_info.petition_class_name XFCLASS,petition_accuser_info.accuser_name ACCUSERNAME, ");
		strSQL.append("petition_accused_info.accused_name  ACCUSEDNAME,petition_basic_info.issue_region_name TOREGIONCODE, ");
		strSQL.append("petition_accused_info.accused_class_name ACCUSEDCLASSES,petition_circulation_state_info.activity_name ACTIVITY ,petition_basic_info.deal_type_name DEALTYPE, ");
		strSQL.append("petition_issue_info.issue_type_name WT,petition_issue_info.issue_type_code WTCode ");
		strSQL.append("from petition_basic_info ");
		strSQL.append("left join petition_accuser_info  on petition_basic_info.oid = petition_accuser_info.petition_basic_info_oid and petition_accuser_info.accuser_num =1 ");
		strSQL.append("left join petition_accused_info  on petition_basic_info.oid = petition_accused_info.petition_basic_info_oid and petition_accused_info.accused_num = 1 ");
		strSQL.append("left join petition_issue_info  on petition_basic_info.oid = petition_issue_info.petition_basic_info_oid and petition_issue_info.issue_num = 1 ");
		strSQL.append("left join petition_circulation_state_info on petition_basic_info.oid = petition_circulation_state_info.petition_basic_info_oid ");
		strSQL.append("where  petition_circulation_state_info.activity_no <> '0000000205' and petition_circulation_state_info.activity_no <> '0000000206' and petition_circulation_state_info.activity_no <> '0000000207' ");
		strSQL.append("and petition_circulation_state_info.activity_no <> '0000000208' and petition_circulation_state_info.activity_no <> '0000000209' and petition_circulation_state_info.activity_no <> '9' and petition_basic_info.region_code = '");
		strSQL.append(regioncode);
		strSQL.append("'");
		strSQL.append(whereUnAchievedString);
//		权限串 
		strSQL.append(sql);
		strSQL.append(")  temp  left join warn_state_info warn_state_info on warn_state_info.petition_no = temp.xfno and warn_state_info.region_code = temp.region_code and alarm_type_code = 'HengXCL'");
		strSQL.append(" order by xfNo desc )");
		return jdbcTemplate.queryForList(strSQL.toString());
	}

	/**
	 * 查询纵向接收查询列表: 返回列表总数
	 * @author mengxy
	 * @createDate 2011-06-02
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型
				   CurrRegion 当前用户所属机构 作用于集中部署时
	 * @return long
	 */
	public long getVerReceiveWarningWListCount(Map map) {
		StringBuffer sql = new StringBuffer("");
		if("1".equals(map.get("ZFTypeCode").toString())){//转办
			sql.append("select count(0) xfno "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_trans_deal_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left join warn_state_info w on w.petition_no = basictemp.petition_no and basictemp.region_code = w.region_code and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
		}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
			sql.append("select count(0) xfno "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_assign_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left outer join warn_state_info w on w.petition_no = basictemp.petition_no and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
		}else{
			sql.append("select count(0) from ( "); 
			sql.append("select basictemp.petition_no xfno "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_trans_deal_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left join warn_state_info w on w.petition_no = basictemp.petition_no and basictemp.region_code = w.region_code and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
			sql.append(" union all select basictemp.petition_no xfNo "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_assign_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left outer join warn_state_info w on w.petition_no = basictemp.petition_no and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
			sql.append(")");
		}
		return jdbcTemplate.queryForLong(sql.toString());
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
	 */
	public List<Map<String, Object>> getVerReceiveWarningWList(Map map,String sort,String dir,int start,int limit) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		if("1".equals(map.get("ZFTypeCode").toString())){//转办
			sql.append("select basictemp.petition_no xfNo,trim(transtemp.deal_type_name) ZFTYPECODE, ");
			sql.append("transtemp.send_date sendDate,w.alarm_active ALARMACTIVE "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_trans_deal_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left join warn_state_info w on w.petition_no = basictemp.petition_no and basictemp.region_code = w.region_code and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
		}else if("2".equals(map.get("ZFTypeCode").toString())){//交办
			sql.append("select basictemp.petition_no xfNo,trim(transtemp.deal_type_name) ZFTYPECODE, ");
			sql.append("transtemp.send_date sendDate,w.alarm_active ALARMACTIVE "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_assign_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left outer join warn_state_info w on w.petition_no = basictemp.petition_no and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
		}else{
			sql.append("select basictemp.petition_no xfNo,trim(transtemp.deal_type_name) ZFTYPECODE, ");
			sql.append("transtemp.send_date sendDate,w.alarm_active ALARMACTIVE "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_trans_deal_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left join warn_state_info w on w.petition_no = basictemp.petition_no and basictemp.region_code = w.region_code and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
			sql.append(" union select basictemp.petition_no xfNo,trim(transtemp.deal_type_name) ZFTYPECODE, ");
			sql.append("transtemp.send_date sendDate,w.alarm_active ALARMACTIVE "); 
			sql.append("from petition_basic_info_temp basictemp ");
			sql.append("left join petition_deal_info_temp dealtemp on basictemp.oid = dealtemp.petition_basic_info_temp_oid and dealtemp.valid_flag = '0' ");
			sql.append("left join petition_assign_info_temp transtemp on dealtemp.oid = transtemp.petition_deal_info_temp_oid and dealtemp.deal_no = transtemp.deal_no ");
			sql.append("left outer join warn_state_info w on w.petition_no = basictemp.petition_no and w.alarm_type_code = 'ZongXJS' "); 
			sql.append("where transtemp.receive_date is null and transtemp.back_date is null ");
			sql.append(" and transtemp.to_region_code = '"+map.get("CurrRegion")+"' ");
			sql.append(getVerReceiveWarningWListConditionSQL(map,"transtemp"));
		}
		//拼装排序字段
		if(sort!=null && !"".equals(sort)){
			sql.append(" order by ");
			sql.append(sort);
			if(dir!=null && !"".equals(dir)){
				sql.append(" ");
				sql.append(dir);
			}
		}else{
			sql.append(" order by xfNo desc");
		}
		sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sql.toString());
	}
	
	/**
	 * 为纵向接收列表查询拼接sql 返回条件语句
	 * @author mengxy
	 * @createDate 2011-06-02
	 * @param map sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型
	 * @return
	 */
	public String getVerReceiveWarningWListConditionSQL(Map map,String zfType){
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
			conditionSql.append(" and "+zfType+".send_date >= '"+map.get("sendDate").toString() +"'") ;
		}
		if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
			conditionSql.append(" and "+zfType+".send_date <= '"+map.get("sendDate1").toString() +"'") ;
		}
		return conditionSql.toString();
	}
	
	/**
	 * 为纵向已接收列表查询拼接sql 返回条件语句 未归档的
	 * @author ljx
	 * @createDate 2011-06-08
	 * @param map  XFPrtNo：条形码编号,AchieveTypeCode ：是否归档， AccusedName:被反映人,ZFTypeCode:转发类型
				   ReceiveDate ：接收起始日期,ReceiveDate1 : 接收终止日期
	 * @return string
	 */
	public String getVerReceiveWarningYListConditionSQLUnAchieve(Map map){
		StringBuffer conditionSql = new StringBuffer();
		if( map.get("ReceiveDate") != null && !map.get("ReceiveDate").toString().equals("")){
			conditionSql.append(" and trans.receive_date >= '"+map.get("ReceiveDate").toString() +"'") ;
		}
		if( map.get("ReceiveDate1") != null && !map.get("ReceiveDate1").toString().equals("")){
			conditionSql.append(" and trans.receive_date <= '"+map.get("ReceiveDate1").toString() +"'") ;
		}
		if( map.get("sendDate") != null	&& !map.get("sendDate").toString().equals("")){
			conditionSql.append(" and trans.send_date >= '"+map.get("sendDate").toString() +"'") ;
		}
		if( map.get("sendDate1") != null && !map.get("sendDate1").toString().equals("")){
			conditionSql.append(" and trans.send_date <= '"+map.get("sendDate1").toString() +"'") ;
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
	 * 字符串替换函数，将半角符号替换为全角符号
	 * @param str
	 * @return 返回替换后的字符串
	 */
	public String replaceChart(String str){
		String replaceStr = str.replace("&", "＆");
		return replaceStr;
	}
}
