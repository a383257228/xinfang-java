package com.sinosoft.xf.petition.accept.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.util.TimeUtils;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.petition.accept.domain.PetitionIssueInfo;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoft.xf.util.sql.DateUtil;
import com.sinosoft.xf.util.sql.SqlUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;

/**
 * 问题类别     dao
 * @date 2017-06-06
 * @author guoh
 */
@Repository
public class PetitionIssueInfoDao extends HibernateDao<PetitionIssueInfo, String> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PetitionBasicInfoDao basicInfoDao;
	@Autowired
	PetitionBasicInfoManager petitionBasicInfoManager;
	
	/**
	 * 问题类别数量前五位 
	 * @date 2017-06-06
	 * @author guoh
	 * @param map
	 * @param person
	 * @return
	 */
	public List<Map<String, Object>> queryIssueTypeTop5(Map<String, Object> map,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue.issue_type_name as typeName,issue.issue_type_code as issueTypeCode,count(*) as count from petition_basic_info basic ");
		sqlBuffer.append(" left join issue_type_info issue on basic.oid=issue.petition_basic_info_oid where basic.petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		try {
			sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//20170926 BYG 不依赖查询语句获取前五名，因为会丢失统计精度（丢失了前5名对应的旧问题性质量）
		sqlBuffer.append(" group by issue.issue_type_name,issue.issue_type_code");// order by count desc fetch first 5 rows only
		return jdbcTemplate.queryForList(sqlBuffer.toString());
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
	public List<Map<String, Object>> drillingTopCount(Map<String, Object> map,int flag,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select substr(basic.issue_region_code,1,4) as regionCode,count(*) count from petition_basic_info basic ");
		sqlBuffer.append("left join issue_type_info issue on basic.oid=issue.petition_basic_info_oid where basic.petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		try {
			sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String code = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
		//20170925 byg 查询包含被合并的问题属地
		code = petitionBasicInfoManager.unionOldIssueCodeAndNew(code);
		sqlBuffer.append("and issue.issue_type_code ").append(code);
		if(1==flag){//自收
			sqlBuffer.append("and petition_source_code like '01%' ");
		}else if(2==flag){//上级转交办
			sqlBuffer.append(" and petition_source_code not like '01%' ");
		}else if(3==flag){
			sqlBuffer.append("and deal_type_code in ('0302','0201','0202')  ");
		}
		sqlBuffer.append(" group by substr(basic.issue_region_code,1,4)");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	
	/**
	 * 问题类别变化情况-增、减量主查询
	 * @date 2017-07-05
	 * @author guoh
	 * @param map
	 * @param bsFlag
	 * @param person
	 * @return
	 */
	public List<Map<String, Object>> queryZJIssueTypeTop5(Map<String, Object> map,String bsFlag,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue.issue_type_code as issueTypeCode,count(*) as count from petition_basic_info basic ");
		sqlBuffer.append(" left join issue_type_info issue on basic.oid=issue.petition_basic_info_oid  where basic.petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		String dateFlag = map.get("dateFlag").toString();
		if("BY".equals(dateFlag)){
			String[] dateValue = new String[2];
			if("big".equals(bsFlag)){//big说明是后面的月份 例如目前是7月6日   则start为6月7日   end为7月6日
				dateValue[0] = new DateTime().minusMonths(1).toString(Constants.Date_Short);
				dateValue[1] = new DateTime().toString(Constants.Date_Short);
			}else if("small".equals(bsFlag)){//small说明是前面的月份 例如目前是7月6日  则start为5月6日  end为6月6日
				dateValue[0] =  new DateTime().minusMonths(2).toString(Constants.Date_Short);
				dateValue[1] =  new DateTime().minusMonths(1).toString(Constants.Date_Short);
			}
			sqlBuffer.append(" and  basic.petition_date>='").append(dateValue[0]).append("'");
			sqlBuffer.append(" and  basic.petition_date<='").append(dateValue[1]).append("'");
		}else if("BJD".equals(dateFlag)){//选中的本季度
			DateTime date = new DateTime();
			if(date.getMonthOfYear()>=1&&3>=date.getMonthOfYear()){
				if("big".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('10','11','12') AND TO_CHAR(basic.petition_date,'YYYY') = '").append(date.getYear()).append("'");
				}else if("small".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('07','08','09') AND TO_CHAR(basic.petition_date,'YYYY') = '").append((date.getYear()-1)).append("'");
				}
			}else if(date.getMonthOfYear()>=4&&6>=date.getMonthOfYear()){
				if("big".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('01','02','03') AND TO_CHAR(basic.petition_date,'YYYY') = '").append(date.getYear()).append("'");
				}else if("small".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('10','11','12') AND TO_CHAR(basic.petition_date,'YYYY') = '").append((date.getYear()-1)).append("'");
				}
			}else if(date.getMonthOfYear()>=7&&9>=date.getMonthOfYear()){
				if("big".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('04','05','06') AND TO_CHAR(basic.petition_date,'YYYY') = '").append(date.getYear()).append("'");
				}else if("small".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('01','02','03') AND TO_CHAR(basic.petition_date,'YYYY') = '").append(date.getYear()).append("'");
				}
			}else if(date.getMonthOfYear()>=10&&12>=date.getMonthOfYear()){
				if("big".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('07','08','09') AND TO_CHAR(basic.petition_date,'YYYY') = '").append(date.getYear()).append("'");
				}else if("small".equals(bsFlag)){
					sqlBuffer.append(" and TO_CHAR(basic.petition_date,'MM')  IN ('04','05','06') AND TO_CHAR(basic.petition_date,'YYYY') = '").append(date.getYear()).append("'");
				}
			}
		}else if("BN".equals(dateFlag)){
			String[] dateValue = new String[2];
			DateTime date = new DateTime();
			if("big".equals(bsFlag)){
				dateValue[0] = date.getYear()+"-01-01";
				dateValue[1] = date.getYear()+"-"+date.getMonthOfYear()+"-"+date.getDayOfMonth();
			}else if("small".equals(bsFlag)){
				dateValue[0] = (date.getYear()-1)+"-01-01";
				dateValue[1] = (date.getYear()-1)+"-"+date.getMonthOfYear()+"-"+date.getDayOfMonth();
			}
			sqlBuffer.append(" and  basic.petition_date>='").append(dateValue[0]).append("'");
			sqlBuffer.append(" and  basic.petition_date<='").append(dateValue[1]).append("'");
		}else if("ZDYC".equals(dateFlag)){
			String startDateValue = map.get("startDateValue").toString();
			String endDateValue = map.get("endDateValue").toString();
			String startDate = startDateValue+"-01";
			String endDate = "";
			
			String[] dateValue = new String[2];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			if(StringUtils.isNotBlank(endDateValue)){
				int day=0;
				try {
					day = DateUtil.getDaysOfMonth(sdf.parse(endDateValue+"-01"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				endDate = endDateValue+"-"+day;
			}else{
				Timestamp dateNow= TimeUtils.getAllTimestamp();
				endDate = sdf.format(dateNow);
			}
			int year = Integer.parseInt(startDate.substring(0, startDate.indexOf("-")))-1;
			String month = startDateValue.substring((startDateValue.indexOf("-")+1),startDateValue.length());
			int eyear = Integer.parseInt(endDate.substring(0, startDate.indexOf("-")))-1;
			String emonth = endDate.substring(endDate.indexOf("-")+1, endDate.length());
			String bStartDate = year+"-"+month+"-01";
			String sEndDate = eyear+"-"+emonth;
			if("big".equals(bsFlag)){
				dateValue[0] = startDateValue+"-01";
				dateValue[1] = endDate;
			}else if("small".equals(bsFlag)){
				dateValue[0] = bStartDate;
				dateValue[1] = sEndDate;
			}
			sqlBuffer.append(" and  basic.petition_date>='").append(dateValue[0]).append("'");
			sqlBuffer.append(" and  basic.petition_date<='").append(dateValue[1]).append("'");
		}
		sqlBuffer.append(" group by issue.issue_type_code");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	
	/**
	 * 问题列表分布情况-查询违法大类数量
	 * @author guoh
	 * @date 2017-08-18
	 * @param map
	 * @param person
	 * @param flag
	 * @return
	 */
	public List<Map<String, Object>> problemDistributionWF(Map<String, Object> map,PersonInfo person,String flag) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue.issue_type_code as code,issue.issue_type_name as name,count(*) as count from issue_type_info issue");
		sqlBuffer.append(" left join petition_basic_info basic on basic.oid=issue.petition_basic_info_oid where basic.petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		if("wf".equals(flag)){
			//20170925 byg 查询问题性质大类包含对应的旧的问题性质---违法行为大类
			sqlBuffer.append(petitionBasicInfoManager.switchToContainOld("013"));
		}else if("wj".equals(flag)){
			//20170925 byg 查询问题性质大类包含对应的旧的问题性质---违纪行为大类
			sqlBuffer.append(petitionBasicInfoManager.switchToContainOld("012"));
		}
		try {
			sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sqlBuffer.append(" group by issue.issue_type_code,issue.issue_type_name");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 信访件分布情况-点击玫瑰图查询指定地区信访类别分布情况
	 * @author guoh
	 * @date 2017-08-24
	 * @param flag
	 * @param map
	 * @param orgCode
	 * @param person
	 * @return
	 */
	public int count(int flag,Map<String, Object> map,String orgCode,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer();
		if(1==flag){//自收
			sqlBuffer.append("select count(0) num from ( select count(0) num from petition_basic_info basic where petition_class_code = '1' and petition_source_code like '01%' ");
		}else if(3==flag){
			sqlBuffer.append("select count(0) num from ( select petition_no from petition_basic_info basic where petition_class_code = '1' and deal_type_code in ('0302','0201','0202')  ");
		}
		try {
			sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if("3100".equals(orgCode)){
			sqlBuffer.append(" and issue_region_code like '31%' ");
		}else{
			//20170925 byg 查询包含被合并的问题属地
			orgCode = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(orgCode);
			sqlBuffer.append(" and substr(issue_region_code,1,4) ").append(orgCode);
		}
		sqlBuffer.append(" group by petition_no)");
		return jdbcTemplate.queryForInt(sqlBuffer.toString());
	}
	/**
	 * 信访件分布情况-点击玫瑰图查询指定地区问题类别分布情况
	 * @author guoh
	 * @date 2017-08-24
	 * @param flag
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> issueTypeCount(int flag,Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue.issue_type_name as issueTypeName,count(*) count,substr(issue.issue_type_code,1,4)  issueTypeCode from petition_basic_info basic ");
		sqlBuffer.append(" left join issue_type_info issue on issue.petition_basic_info_oid = basic.oid where basic.petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		if("310000000000".equals(map.get("orgCode").toString())){
			sqlBuffer.append(" and basic.issue_region_code like '31%'");
		}else{
			//20170925 byg 查询包含被合并的问题属地
			String orgCodeSql = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(map.get("orgCode").toString());
			sqlBuffer.append("and basic.issue_region_code ").append(orgCodeSql);
		}
		sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		//20170925这里的sql 有旧的sql会不准吧？
		sqlBuffer.append("  group by issue.issue_type_name,substr(issue.issue_type_code,1,4)");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 问题类别变化情况-钻取查询问题类别分布属地
	 * @author guoh
	 * @date 2017-08-28
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> issueDistribution(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select basic.issue_region_name as issueRegionName,count(*) as count,basic.issue_region_code issueRegionCode from petition_basic_info basic  ");
		sqlBuffer.append("left join issue_type_info issue on basic.oid=issue.petition_basic_info_oid  where basic.petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		String code = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
		//20170925 byg 查询包含对应的旧的问题性质
		code = petitionBasicInfoManager.unionOldIssueCodeAndNew(code);
		sqlBuffer.append("and issue.issue_type_code ").append(code);
		sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		sqlBuffer.append("  group by basic.issue_region_name,basic.issue_region_code");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
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
	public List<Map<String, Object>> queryWJSubclass(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue.issue_type_code as code,issue.issue_type_name as name,count(*) as count from issue_type_info issue ");
		sqlBuffer.append("left join petition_basic_info basic on basic.oid=issue.petition_basic_info_oid where basic.petition_class_code = '1'  ");
		String code = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
		//20170925 byg 查询问题性质大类包含对应的旧的问题性质
		code = petitionBasicInfoManager.switchToContainOld(code);
		sqlBuffer.append(code);
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		sqlBuffer.append(" group by issue.issue_type_code,issue.issue_type_name");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 点击子类查询问题类别
	 * @date 2017-06-08
	 * @author guoh
	 * @return
	 */
	public List<Map<String, Object>> queryIssueType(String code) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue_type_code as code,issue_type_name as name,count(*) as count from petition_issue_info  ");
		sqlBuffer.append("  where left(issue_type_code,4)='").append(code).append("'");
		sqlBuffer.append(" group by issue_type_code,issue_type_name");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 点击违法行为查询子类
	 * @date 2017-06-08
	 * @author guoh
	 * @return
	 */
	public List<Map<String, Object>> wfBehavior() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue_type_code as code,issue_type_name as name,count(*) as count from petition_issue_info  ");
		sqlBuffer.append("  where left(issue_type_code,4)='0131' group by issue_type_code,issue_type_name");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
}