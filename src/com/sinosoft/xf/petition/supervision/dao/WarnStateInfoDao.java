package com.sinosoft.xf.petition.supervision.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.petition.supervision.domain.WarnStateInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 预警状态信息dao
 * @author ljx 
 * @createDate 2011-04-27
 */
@Repository
public class WarnStateInfoDao extends HibernateDao<WarnStateInfo, String>{
	
	@Autowired
	JdbcTemplate jdbcTemplate;


	/**
	 * 预警扫描
	 * @param t
	 * @return
	 */
	public List warnStateInfo(TimeLimitDefine t) {
		String tempAlarmTypeCode = t.getAlarmTypeCode();
		if("ZongXZF".equals(tempAlarmTypeCode)){
			String activityno = t.getActivityNo();
			if("0000000205".equals(activityno)//转办
					||"0000000207".equals(activityno) || "0000000208".equals(activityno) || "0000000209".equals(activityno)){//交办
				return getVerTransWarnStateInfo(t);
			}
		}
		if("HengXCL".equals(tempAlarmTypeCode)){
			return getHorizWarnStateInfo(t);
		}
		if ("ZongXJS".equals(tempAlarmTypeCode)) {
			if (t.getActivityNo() == null || "".equals(t.getActivityNo())) {
				String dealTypeCode = t.getDealTypeCode();
				//0302转办   0201,0202交办
				if("0302".equals(dealTypeCode)||"0201".equals(dealTypeCode)||"0202".equals(dealTypeCode)) {
					return getVerReceiveWarnStateInfo(dealTypeCode, t.getRegionCode());
				}
			}
		}
		return new ArrayList();
	}
	/**
	 * 查询纵向转发扫描信息
	 * @author ljx
	 * @createDate 2011-05-11
	 * @return 扫描信息的集合
	 */
	private List getVerTransWarnStateInfo(TimeLimitDefine t){
		StringBuffer sql = new StringBuffer();
		String activityno = t.getActivityNo();
		String regioncode = t.getRegionCode();
		String petitionTypeCode = t.getPetitionTypeCode();
		String dealTypeCode = t.getDealTypeCode();
		String petitionSourceCode = t.getPetitionSourceCode();
		String urgencyCode = t.getUrgencyCode();
		String transType = t.getTransType();//交换类型,1导出交换，2传输交换，3集中部署，4不交换	
		String select ="select basic.petition_no petitionNo ";
		String selectWhere = "";
		if (transType != null && !"".equals(transType.trim())) {
			String sendFlag = t.getSendFlag();//1已经传输，0没传输
			String arriveFlag = t.getArriveFlag();//1已接受，0没接受
			if (!transType.equals("4")) {//1导出交换，2传输交换，3集中部署  预警
				selectWhere = " and zjbinfo.report_date is null ";
				String tempChange = "centralizedeploy";//petition_organ_trans表的交换类型
				if (transType.equals("1")) {
					tempChange ="exportexchange";
				}
				if (transType.equals("2")) {
					tempChange ="transferexchange";
				}
				if (arriveFlag != null && arriveFlag.equals("0")) {//未接受需要预警根据发送日期send_date预警
					select += ",zjbinfo.send_date DateDiff ";
					selectWhere += " and zjbinfo.Receive_Date is null ";
				} else if (transType.equals("1") || transType.equals("2")) {//不是集中式的话  上级没发送需要预警,根据录入日期input_date预警
					if (sendFlag != null && sendFlag.equals("0")) {//
						select += ",zjbinfo.input_date DateDiff ";
						selectWhere += " and zjbinfo.send_date is null ";
					}
				}
				selectWhere += " and organTrans.org_type = '"+tempChange+"' ";
			}
		}else{
			if("0000000207".equals(activityno) || "0000000208".equals(activityno)){//交办要情况和结果需要根据report_date上报日期预警
				select += ",zjbinfo.report_date DateDiff ";
				selectWhere = " and zjbinfo.report_date is not null  ";
			}
		}
		
		String tableName="petition_trans_deal_info";//默认查询转办表
		if("0000000207".equals(activityno) || "0000000208".equals(activityno) || "0000000209".equals(activityno)){//交办
			tableName="petition_assign_info";
		}
		sql.append(select);
		sql.append("from petition_basic_info basic	");
		sql.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid 	" );
		sql.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid 	" );
		sql.append("left join "+tableName+" zjbinfo on deal.oid = zjbinfo.petition_deal_info_oid " );
		sql.append("left join petition_organ_trans organTrans on zjbinfo.to_region_code = organTrans.org_code 	" );
		sql.append("where state.activity_no = '"+activityno+"' ");
		sql.append("and basic.region_code = '"+regioncode+"' ");
		sql.append("and deal.valid_flag = '0' " );
		sql.append("and zjbinfo.input_date is not null " );
		sql.append("and zjbinfo.back_date is null " );
		if(petitionTypeCode!= null && !"".equals(petitionTypeCode)){
			sql.append(" and basic.petition_type_code = '"+petitionTypeCode+"' ");
		}
		if(dealTypeCode!= null && !"".equals(dealTypeCode)){
			sql.append( " and deal.deal_type_code = '"+dealTypeCode+"' ");
		}
		if(petitionSourceCode!= null && !"".equals(petitionSourceCode)){
			sql.append( " and basic.petition_source_code = '"+petitionSourceCode+"' ");
		}
		if(urgencyCode!= null && !"".equals(urgencyCode.toString())){
			if("0".equals(urgencyCode.toString())){
				sql.append(" and (basic.petition_urge_code = '0' or basic.petition_urge_code is null or basic.petition_urge_code='' )");
			}else{
				sql.append( " and basic.petition_urge_code = '"+urgencyCode+"' ");
			}
		}
		sql.append(selectWhere);
		List list =  jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	
	/**
	 * 删除预警状态表中的全部数据
	 * @author ljx
	 * @createDate 2011-05-12
	 */
	public void deleteWarnStateInfoAll(){
		String sql="DELETE FROM WARN_STATE_INFO";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 查询纵向接收中未接收的扫描信息
	 * @author ljx
	 * @createDate 2011-05-18
	 * @return 扫描信息的集合
	 */
	private List getVerReceiveWarnStateInfo(String dealTypeCode,String regioncode){
		StringBuffer sql = new StringBuffer();
		String tableName = "petition_trans_deal_info_temp";//默认查询转办表
		if(!"0302".equals(dealTypeCode)){
			tableName = "petition_assign_info_temp";
		}
		sql.append("SELECT dealTemp.petition_no petitionNo,temp.send_date DateDiff ");
		sql.append("FROM petition_deal_info_temp dealTemp left join "+tableName+" temp on dealTemp.oid = temp.petition_deal_info_temp_oid ");
		sql.append("where dealTemp.region_code = '"+regioncode+"' and temp.send_date is not null and temp.receive_date is null and temp.back_date is null and temp.temp_Flag='0' " +
				"and dealTemp.deal_type_code='"+dealTypeCode+"' ");
		return jdbcTemplate.queryForList(sql.toString());
	}
	
	
	/**
	 * 查询横向处理扫描信息
	 * @author ljx
	 * @createDate 2011-05-11
	 * @return 扫描信息的集合
	 */
	private List getHorizWarnStateInfo(TimeLimitDefine t){
		StringBuffer sql = new StringBuffer();
		String activityno = t.getActivityNo();
		String regioncode = t.getRegionCode();
		String petitionTypeCode = t.getPetitionTypeCode();
		String dealTypeCode = t.getDealTypeCode();
		String urgencyCode = t.getUrgencyCode();
		sql.append("select basic.petition_no petitionNo ,state.modify_date  DateDiff from petition_basic_info basic	" );
		sql.append("left join petition_circulation_state_info state on basic.oid = state.petition_basic_info_oid 	" );
		if(dealTypeCode != null && !"".equals(dealTypeCode)){
			sql.append("left join petition_deal_info deal on basic.oid = deal.petition_basic_info_oid 	" );
			sql.append("where deal.valid_flag = '0' and basic.region_code = '"+regioncode+"' ");
			sql.append("and deal.deal_type_code = '"+dealTypeCode+"' ");
		}else {
			sql.append("where basic.region_code = '"+regioncode+"' ");
		}
		if(petitionTypeCode!= null && !"".equals(petitionTypeCode)){
			sql.append(" and basic.petition_type_code = '"+petitionTypeCode+"' ");
		}
		if(urgencyCode!= null && !"".equals(urgencyCode)){
			if("0".equals(urgencyCode.toString())){
				sql.append(" and (basic.petition_urge_code = '0' or basic.petition_urge_code is null)");
			}else{
				sql.append( " and basic.petition_urge_code = '"+urgencyCode+"' ");
			}
		}
		if(activityno!= null && !"".equals(activityno)){
			sql.append( " and state.activity_no = '"+activityno+"' ");
		}
		List list =  jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 保存扫描信息
	 * @author ljx
	 * @createDate 2011-08-11
	 */
	public void insertWarnStateInfos(List<WarnStateInfo> warnStateInfos) {
		final List<WarnStateInfo> tempWarnStateInfo = warnStateInfos;

		String sql = "insert into warn_state_info (oid,TIME_LIMIT_DEFINE_OID,petition_no,ALARM_ACTIVE ,ALARM_TYPE_CODE,ALARM_TYPE_NAME,region_code,ALARM_REASON)  values (?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String timeLimitDefineOid = tempWarnStateInfo.get(i).getTimeLimitDefineOid();
				String xfno = tempWarnStateInfo.get(i).getPetitionNo();
				String alarmActive = tempWarnStateInfo.get(i).getAlarmActive();
				String alarmTypeCode = tempWarnStateInfo.get(i).getAlarmTypeCode();
				String alarmTypeName = tempWarnStateInfo.get(i).getAlarmTypeName();
				String regionCode = tempWarnStateInfo.get(i).getRegionCode();
				String alarmReason=tempWarnStateInfo.get(i).getAlarmReason();
				UUID uuid = UUID.randomUUID();
				String s = uuid.toString().replace("-", "");
				String oid = s.substring(0, 20);

				ps.setString(1, oid);
				ps.setString(2, timeLimitDefineOid);
				ps.setString(3, xfno);
				ps.setString(4, alarmActive);
				ps.setString(5, alarmTypeCode);
				ps.setString(6, alarmTypeName);
				ps.setString(7, regionCode);
				ps.setString(8, alarmReason);
			}

			public int getBatchSize() {
				return tempWarnStateInfo.size();
			}
		});

	} 
	
	/**
	 * 查询预警信访件信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> listBaseWarnInfo(String regionCode) {
		Session session =this.getSession();
		String hql = "From PetitionBasicInfo basic,WarnStateInfo warn,PetitionCirculationStateInfo state " +
				"Where basic.petitionNo=warn.petitionNo and warn.regionCode=basic.regionCode " +
				"and basic.id = state.petitionBasicInfo.id and basic.regionCode='"+regionCode+"'" +
				" Order  by warn.petitionNo desc";
		Query query = session.createQuery(hql.toString());
		return query.list();
	}
}
