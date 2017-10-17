package com.sinosoft.xf.petition.accept.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.util.TimeUtils;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.petition.domainutil.IssueTypeCodeCount;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.util.sql.DateUtil;
import com.sinosoft.xf.util.sql.SqlUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;

/**
 * 基本信息     dao
 * @date 2017-06-12
 * @author guoh
 */
@Repository
public class PetitionBasicInfoDao extends HibernateDao<PetitionBasicInfo, String> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PetitionBasicInfoManager petitionBasicInfoManager;
	/**
	 * 信访件分布情况-全市的信访件分布主页面查询
	 * @author guoh
	 * @date 2017-06-30
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> queryXFJPaiZhu(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select o.org_code as orgCode,o.org_cname as orgCname,basic.num as count from organization o ");
		sqlBuffer.append("left join (select issueRegionCode,count(*) as num from ( select petition_no petitionNo,substr(issue_region_code,1,4) issueRegionCode  ");
		sqlBuffer.append("from petition_basic_info basic where petition_class_code = '1' ");
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sqlBuffer.append(dateValue);
		sqlBuffer.append(" group by petition_no,substr(issue_region_code,1,4)   ) group by issueRegionCode )basic on substr(o.org_code,1,4) = basic.issueRegionCode ");
		sqlBuffer.append(" where o.org_code like '31%00000000'  order by o.org_code  ");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	
	/**
	 * 信访件分布情况-查询信访件列表
	 * @date 2017-06-12
	 * @author guoh
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public int getBasicInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer("select count(*) from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join petition_issue_info issue on basic.oid = issue.petition_basic_info_oid where petition_class_code = '1' ");
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sb.append(dateValue);
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 信访件分布情况-查询信访件列表
	 * @date 2017-06-12
	 * @author guoh
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getBasicInfoList(Map<String, Object> map,int start,int limit,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select basic.oid as bId from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join petition_issue_info issue on basic.oid = issue.petition_basic_info_oid where petition_class_code = '1' ");
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));

		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sb.append(dateValue);
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 信访件分布情况-钻取查询基本信息
	 * @date 2017-08-25
	 * @author guoh
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public int getLetterInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer("select count(*) from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		//20171012 byg XFFBQK此参数用来标识是否来自 信访类别分布情况（派驻）钻取信访件列表的请求。为空时标识 top5等模块的钻取
		if(null==map.get("XFFBQK")){
			//20171012 byg 修改关联表 如果统计问题类型量，使用issue_type_info表关联即可
			sb.append("left join issue_type_info issue on basic.oid = issue.petition_basic_info_oid");
		}
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sb.append(" where basic.petition_class_code = '1' ");
		sb.append(dateValue);
		//20171012 byg 添加 信件所属 机构约束
		String reginCode = person.getRegionCode();
		if(null!=map.get("swFlag")&&"bw".equals(map.get("swFlag"))){
			sb.append(" and basic.region_code  = '"+reginCode+"'   ");
		}
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		
		if(null!=map.get("orgCode")){
			String code = map.get("orgCode").toString().substring(0, 4);
			if(!"".equals(map.get("index"))&&null!=map.get("index")){
				if("0".equals(map.get("index").toString())){//自收件
					sb.append(" and basic.petition_source_code like '01%' ");
				}else if("1".equals(map.get("index").toString())){//转、交办下级
					sb.append(" and deal_type_code in ('0302','0201','0202')  ");
				}else if("2".equals(map.get("index").toString())){//上级转、交办
					sb.append(" and petition_source_code not like '01%' ");
				}
			}
			if(null!=map.get("issueTypeName")){//数量前五位
				String typeCode = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
				//20170925 byg 查询包含对应的旧的问题性质
				typeCode = petitionBasicInfoManager.unionOldIssueCodeAndNew(typeCode);
				sb.append("and issue.issue_type_code ").append(typeCode);
			}
			if("310000000000".equals(map.get("orgCode").toString())){
				sb.append(" and basic.issue_region_code like '31%' ");
			}else{
				//20170925 byg 查询包含被合并的问题属地
				code = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(code);
				sb.append(" and substr(basic.issue_region_code,1,4) ").append(code);
			}
		}
		sb.append(" and basic.petition_source_code not in (select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送') ) ");
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 信访件分布情况-钻取查询列表
	 * @date 2017-08-25
	 * @author guoh
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getLetterInfoList(Map<String, Object> map,int start,int limit,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select basic.oid as bId from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		//20171012 byg 修改关联表 如果统计问题类型量，使用issue_type_info表关联即可
		sb.append("left join issue_type_info issue on basic.oid = issue.petition_basic_info_oid ");
		sb.append(" where basic.petition_class_code = '1' ");
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sb.append(dateValue);
		//20171012 byg 添加 信件所属 机构约束
		String reginCode = person.getRegionCode();
		if(null!=map.get("swFlag")&&"bw".equals(map.get("swFlag"))){
			sb.append(" and basic.region_code  = '"+reginCode+"'   ");
		}
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		if(null!=map.get("orgCode")){
			String code = map.get("orgCode").toString().substring(0, 4);
			if(!"".equals(map.get("index"))&&null!=map.get("index")){
				if("0".equals(map.get("index").toString())){//自收件
					sb.append(" and basic.petition_source_code like '01%' ");
				}else if("1".equals(map.get("index").toString())){//下级转、交办
					sb.append(" and deal_type_code in ('0302','0201','0202')  ");
				}else if("2".equals(map.get("index").toString())){//上级转、交办
					sb.append(" and petition_source_code not like '01%' ");
				}
			}
			if(null!=map.get("issueTypeName")){//数量前五位
				String typeCode = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
				//20170925 byg 查询包含对应的旧的问题性质
				typeCode = petitionBasicInfoManager.unionOldIssueCodeAndNew(typeCode);
				sb.append("and issue.issue_type_code ").append(typeCode);
			}
			if("310000000000".equals(map.get("orgCode").toString())){
				sb.append(" and basic.issue_region_code like '31%' ");
			}else{
				//20170925 byg 查询包含被合并的问题属地
				code = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(code);
				sb.append(" and substr(basic.issue_region_code,1,4) ").append(code);
			}
		}
		sb.append(" and basic.petition_source_code not in (select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送') ) ");
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 问题类别分布情况-钻取查询信访件列表
	 * @date 2017-08-27
	 * @author guoh
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public int getClassChangeInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer("select count(*) from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join issue_type_info issue on basic.oid = issue.petition_basic_info_oid where basic.petition_class_code = '1'");
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sb.append(dateValue);
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		if(null==map.get("flag")){//如果为空时问题类别变化情况  如果不为空是问题类别分布情况
			//20170925 byg 查询包含被合并的问题属地
			String unionOldSql = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(map.get("issueRegionCode").toString());
			sb.append(" and basic.issue_region_code ").append(unionOldSql);
		}
		String code = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
		//20170925 byg 查询包含对应的旧的问题性质
		code = petitionBasicInfoManager.unionOldIssueCodeAndNew(code);
		sb.append("and issue.issue_type_code ").append(code);
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		return jdbcTemplate.queryForInt(sb.toString());
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
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> getClassChangeInfoList(Map<String, Object> map,int start,int limit,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append(" select basic.oid as bId from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num = '1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num = '1' ");
		sb.append("left join issue_type_info issue on basic.oid = issue.petition_basic_info_oid where basic.petition_class_code = '1'");
		String dateValue = DateUtil.queryStartdateAndEnddate(map);
		sb.append(dateValue);
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		if(null==map.get("flag")){//如果为空时问题类别变化情况  如果不为空是问题类别分布情况
			//20170925 byg 查询包含被合并的问题属地
			String unionOldSql = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(map.get("issueRegionCode").toString());
			sb.append(" and basic.issue_region_code ").append(unionOldSql);
		}
		String code = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(),map.get("issueTypeName").toString(),false);
		//20170925 byg 查询包含对应的旧的问题性质
		code = petitionBasicInfoManager.unionOldIssueCodeAndNew(code);
		sb.append("and issue.issue_type_code ").append(code);
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 同比、环比、各月趋势-钻取查询列表
	 * @author guoh
	 * @date 2017-09-20
	 * @param map
	 * @param person
	 * @return
	 */
	public int getTrendCompareQuerySize(Map<String, Object> map,PersonInfo person) {
		StringBuffer sb = new StringBuffer("select count(*) from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join issue_type_info issue on basic.oid = issue.petition_basic_info_oid where basic.petition_class_code = '1'");
		
		sb.append(DateUtil.queryStartEnd(map));
		
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		String regionCode = regionNameByCode(map.get("regionName").toString());
		//20170925 byg 查询包含被合并的问题属地
		String unionOldSql = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(regionCode.substring(0, 4));
		sb.append(" and left(basic.issue_region_code,4) ").append(unionOldSql);
		
		String name = map.get("issueTypeName").toString();
		if(!"zCount".equals(name)){
			String code = name.substring(3);
			if(code.length()==4){//长度为4说明是大类
				//20170925 byg 查询问题性质大类包含对应的旧的问题性质
				code = petitionBasicInfoManager.switchToContainOld(code);
				sb.append(code);
			}else{
				//20170925 byg 查询包含对应的旧的问题性质
				code = petitionBasicInfoManager.unionOldIssueCodeAndNew(code);
				sb.append("and issue.issue_type_code ").append(code);
			}
		}
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 同比、环比、各月趋势-钻取查询列表
	 * @author guoh
	 * @date 2017-09-20
	 * @param map
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 */
	public List<Map<String, Object>> getTrendCompareQueryList(Map<String, Object> map,int start,int limit,PersonInfo person) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append(" select basic.oid as bId from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join issue_type_info issue on basic.oid = issue.petition_basic_info_oid where basic.petition_class_code = '1'");
		
		sb.append(DateUtil.queryStartEnd(map));
		
		if(null!=map.get("accusedName")){
			sb.append("and accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		String name = map.get("issueTypeName").toString();
		if(!"zCount".equals(name)){
			String code = name.substring(3);
			if(code.length()==4){//长度为4说明是大类
				//20170925 byg 查询问题性质大类包含对应的旧的问题性质
				code = petitionBasicInfoManager.switchToContainOld(code);
				sb.append(code);
			}else{
				//20170925 byg 查询包含对应的旧的问题性质
				code = petitionBasicInfoManager.unionOldIssueCodeAndNew(code);
				sb.append("and issue.issue_type_code ").append(code);
			}
		}
		String regionCode = regionNameByCode(map.get("regionName").toString());
		//20170925 byg 查询包含被合并的问题属地
		String unionOldSql = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(regionCode.substring(0, 4));
		sb.append(" and left(basic.issue_region_code,4) ").append(unionOldSql);
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 全市本委各月趋势统计
	 * @author guoh
	 * @date 2017-06-26
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> trendStatistical(Map<String, Object> map,PersonInfo person) throws ParseException {
		String dateFlag = map.get("dateFlag").toString();
		String[] dateValue = DateUtil.queryDate(dateFlag,map.get("startDateValue").toString(),map.get("endDateValue").toString());
		StringBuffer sqlBuffer = new StringBuffer("SELECT ");
		int yearStart = 0;
		int yearEnd = 0;
		int monthStart = 0;
		int monthEnd = 0;
		if(dateFlag.equals("BJD")||dateFlag.equals("BN")){//本季度和本年的年份一样
			yearStart = Integer.parseInt(dateValue[1].substring(0, 4).toString());//取日期的年份
			monthStart = Integer.parseInt(dateValue[0].substring(5, 6).toString());//取开始日期的月份
			if(Integer.parseInt(dateValue[2])>9){//如果大于9需要截取2位
				monthEnd = Integer.parseInt(dateValue[1].substring(5, 7).toString());//取结束日期的月份
			}else{
				monthEnd = Integer.parseInt(dateValue[1].substring(5, 6).toString());//取结束日期的月份
			}
			
			for(int i=monthStart;i<=monthEnd;i++){
				if(i<=9){
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearStart).append("-0").append(i).append("'").append(" THEN num END) AS Q").append(i);
				}else{
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearStart).append("-").append(i).append("'").append(" THEN num END) AS Q").append(i);
				}
				if(i<monthEnd){
					sqlBuffer.append(",");
				}
			}
		}else if(dateFlag.equals("ZDYC")||dateFlag.equals("ZDY")){//例如2016-8  2017-7
			yearStart = Integer.parseInt(dateValue[0].substring(0, 4).toString());//2016
			yearEnd = Integer.parseInt(dateValue[1].substring(0, 4).toString());//2017
			monthStart = Integer.parseInt(dateValue[0].substring(5, 6).toString());//8
			if(Integer.parseInt(dateValue[2])>9){
				monthEnd = Integer.parseInt(dateValue[1].substring(5, 7).toString());
			}else{
				monthEnd = Integer.parseInt(dateValue[1].substring(5, 6).toString());
			}
			
			for(int i=monthStart;i<=12;i++){//8-12
				if(i==monthStart){
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearStart).append("-0").append(i).append("'");
					sqlBuffer.append(" THEN num END) AS Q").append(yearStart).append(i).append(",");
					continue;
				}
				if(i<=9){
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearStart).append("-0").append(i).append("'").append(" THEN num END) AS Q").append(i);
				}else{
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearStart).append("-").append(i).append("'").append(" THEN num END) AS Q").append(i);
				}
				sqlBuffer.append(",");
			}
			for(int i=1;i<=monthEnd;i++){//1-7
				if(i==1){
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearEnd).append("-0").append(i).append("'");
					sqlBuffer.append(" THEN num END) AS Q").append(yearEnd).append(i).append(",");
					continue;
				}
				if(i<=9){
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearEnd).append("-0").append(i).append("'").append(" THEN num END) AS Q").append(i);
				}else{
					sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(yearEnd).append("-").append(i).append("'").append(" THEN num END) AS Q").append(i);
				}
				if(i<monthEnd){
					sqlBuffer.append(",");
				}
			}
		}
		
		
		sqlBuffer.append(" FROM ( select substr(petition_Date,1,7) petitionDate,count(0) num from petition_basic_info basic");
		sqlBuffer.append(" where  petition_date>='").append(dateValue[0]).append("'");
		sqlBuffer.append(" and  petition_date<='").append(dateValue[1]).append("'");
		sqlBuffer.append(" and petition_class_code='1'");
		
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sqlBuffer.append(" group by substr(petition_Date,1,7))");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	
	
	/**
	 * 各月、同比、环比-钻取查询问题属地和问题类别
	 * @author guoh
	 * @date 2017-08-31
	 * @param map
	 * @param person
	 * @return
	 */
	public List<IssueTypeCodeCount> reportDrilling(Map<String, Object> map,PersonInfo person) {
		StringBuffer sb = new StringBuffer();
		sb.append("select issue.issue_type_code issueTypeCode,substr(basic.issue_region_code,1,4) regionCode,count(*) count from petition_basic_info basic ");
		sb.append("left join petition_issue_info issue on issue.petition_basic_info_oid=basic.oid ");
		sb.append("where petition_class_code='1' ");
		sb.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sb.append(DateUtil.queryStartEnd(map));
		sb.append("group by  substr(basic.issue_region_code,1,4) ,issue.issue_type_code");
		return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper(IssueTypeCodeCount.class));
	}
	/**
	 * 同比查询
	 * @author guoh
	 * @date 2017-07-13
	 * @param swFlag
	 * @param bwRadio
	 * @param yearFlag
	 * @param person
	 * @return
	 */
	public List<Map<String, Object>> tbQuery(String swFlag,String bwRadio,String yearFlag,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT ");
		DateTime date = new DateTime();
		int year = 0;
		if("JN".equals(yearFlag)){
			year = date.getYear();
		}else if("QN".equals(yearFlag)){
			year = date.getYear()-1;
		}
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-01'").append(" THEN num END) AS Q1, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-02'").append(" THEN num END) AS Q2, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-03'").append(" THEN num END) AS Q3, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-04'").append(" THEN num END) AS Q4, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-05'").append(" THEN num END) AS Q5, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-06'").append(" THEN num END) AS Q6, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-07'").append(" THEN num END) AS Q7, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-08'").append(" THEN num END) AS Q8, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-09'").append(" THEN num END) AS Q9, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-10'").append(" THEN num END) AS Q10, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-11'").append(" THEN num END) AS Q11, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(year).append("-12'").append(" THEN num END) AS Q12 ");
		sqlBuffer.append("FROM ( select substr(petition_Date,1,7) petitionDate,count(0) num from petition_basic_info basic ");
		if("JN".equals(yearFlag)){
			sqlBuffer.append("where substr(petition_Date,1,4)='").append(date.getYear()).append("'");
		}else if("QN".equals(yearFlag)){
			sqlBuffer.append("where substr(petition_Date,1,4)='").append(date.getYear()-1).append("'");
		}
		sqlBuffer.append("and petition_class_code='1'");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("swFlag", swFlag);
		map.put("bwRadio", bwRadio);
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sqlBuffer.append("group by substr(petition_Date,1,7))");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 *  查询环比
	 * @author guoh
	 * @date 2017-07-17
	 * @param swFlag
	 * @param bwRadio
	 * @param person
	 * @return
	 */
	public List<Map<String, Object>> SequentialQuery(String swFlag,String bwRadio,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT ");
		DateTime date = new DateTime();
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()-1).append("-12'").append(" THEN num END) AS Q0, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-01'").append(" THEN num END) AS Q1, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-02'").append(" THEN num END) AS Q2, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-03'").append(" THEN num END) AS Q3, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-04'").append(" THEN num END) AS Q4, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-05'").append(" THEN num END) AS Q5, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-06'").append(" THEN num END) AS Q6, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-07'").append(" THEN num END) AS Q7, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-08'").append(" THEN num END) AS Q8, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-09'").append(" THEN num END) AS Q9, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-10'").append(" THEN num END) AS Q10, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-11'").append(" THEN num END) AS Q11, ");
		sqlBuffer.append("MAX(CASE WHEN petitionDate = '").append(date.getYear()).append("-12'").append(" THEN num END) AS Q12 ");
		sqlBuffer.append("FROM ( select substr(petition_Date,1,7) petitionDate,count(0) num from petition_basic_info basic ");
		sqlBuffer.append("where (substr(petition_Date,1,4)='").append(date.getYear()).append("' or substr(petition_Date,1,7)='").append(date.getYear()-1).append("-12') ");
		sqlBuffer.append("and petition_class_code='1'");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("swFlag", swFlag);
		map.put("bwRadio", bwRadio);
		sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person,petitionBasicInfoManager));
		sqlBuffer.append("group by substr(petition_Date,1,7))");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 通过传的参数（本月、本季度、本年、自定义）返回日期值
	 * @author guoh
	 * @date 2017-07-06
	 * @param dateFlag
	 * @param bsFlag
	 * @param startDateValue
	 * @param endDateValue
	 * @return
	 * @throws ParseException
	 */
	public String[] queryContrastDate(String dateFlag,String bsFlag,String startDateValue,String endDateValue) throws ParseException{
		String[] dateValue = new String[2];
		String startDate = "";
		String endDate = "";
		if(dateFlag.equals("BY")){//选中本月   拼接本月的一号到今天
			if("big".equals(bsFlag)){//big说明是后面的月份 例如目前是7月6日   则start为6月7日   end为7月6日
				dateValue[0] = new DateTime().minusMonths(1).toString(Constants.Date_Short);
				dateValue[1] = new DateTime().toString(Constants.Date_Short);
			}else if("small".equals(bsFlag)){//small说明是前面的月份 例如目前是7月6日  则start为5月6日  end为6月6日
				dateValue[0] =  new DateTime().minusMonths(2).toString(Constants.Date_Short);
				dateValue[1] =  new DateTime().minusMonths(1).toString(Constants.Date_Short);
			}
		}
		if(dateFlag.equals("BN")){//选中本年   拼接今年的一月一号到今天
			DateTime year = new DateTime();
			startDate = year.getYear()+"-01-01";
			endDate = year.getYear()+"-"+year.getMonthOfYear()+"-"+year.getDayOfMonth();
		}
		if(dateFlag.equals("ZDYC")||dateFlag.equals("ZDY")){//选中自定义    拼接开始日期月的第一天到结束日期月的最后一天
			startDate = startDateValue+"-01";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			if(StringUtils.isNotBlank(endDateValue)){
				int day = DateUtil.getDaysOfMonth(sdf.parse(endDateValue+"-01"));
				endDate = endDateValue+"-"+day;
			}else{
				Timestamp dateNow= TimeUtils.getAllTimestamp();
				endDate = sdf.format(dateNow);
			}
		}
		dateValue[0] = startDate;
		dateValue[1] = endDate;
		return dateValue;
	}
	/**
	 * 根据id查询基本信息
	 * @date 2017-06-12
	 * @author guoh
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionBasicInfo> listBasicByIds(String[] ids) {
		Criteria criteria = this.getSession().createCriteria(PetitionBasicInfo.class,"basic");
		criteria.add(Restrictions.in("basic.id", ids));
		return criteria.list();
	}
	/**
	 * 根据id查询petitionInfo信息
	 * @author guoh
	 * @date 2017-06-21
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] getPetition(String id) {
		Session session = this.getSession();
		PetitionBasicInfo petitionBasicInfo = null;
		petitionBasicInfo = this.get(PetitionBasicInfo.class, id);
		if(petitionBasicInfo == null || "".equals(petitionBasicInfo.getId())) {
			return null;
		}
		StringBuffer hql = new StringBuffer(" from PetitionBasicInfo b ");
		hql.append(" left join b.accuserInfoSet r ");
		hql.append(" left join b.issueInfoSet i ");
		hql.append(" left join b.accusedInfoSet d ");
		hql.append(" left join b.dealInfoSet d");
		hql.append(" left join b.dealInfoSet d");
		hql.append(" where b.id=:oid");
		Query query = session.createQuery(hql.toString());
		query.setString("oid", id);
		List<Object[]> list = query.list();
		if (!list.isEmpty()) {
			return (Object[]) list.get(0);
		}
		return null;
	}
	/**
	 * 根据信访编号查询信访信息List
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionBasicInfo> listByPetitionNo(String petitionNo,String regionCode) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionBasicInfo.class);
		criteria.add(Restrictions.eq("petitionNo",petitionNo));
		if(regionCode!=null&&!regionCode.equals("")){
			criteria.add(Restrictions.eq("regionCode",regionCode));
		}
		return criteria.list();
	}
	/**
	 * 通过信访编号和regionCode，查询当前信的basicInfo表对象
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param petitionNo 信访编号
	 * @param regionCode 所属区域
	 * @return bean对象
	 */
	public PetitionBasicInfo getBasicInfoByPetitionNo(String petitionNo,String regionCode){
		Criteria criteriaObject = this.createCriteria();
		criteriaObject.add(Restrictions.eq("petitionNo", petitionNo));
		criteriaObject.add(Restrictions.eq("regionCode", regionCode));
		PetitionBasicInfo basicInfo = (PetitionBasicInfo) criteriaObject.uniqueResult();
		return basicInfo;
	}
	/**
	 * 使用组织机构编码 获取对应的 组织机构名称
	 * @param regionCode
	 * @return
	 */
	public String regionCodeToName(String regionCode){
		String regionName = "";
		String[] code1 = Constants.dispatchRegionCode;//派驻机构编码集
		String[] code = Constants.countyRegionCode;//区县机构编码集
		for(int j=0;j<code.length;j++){
			if(regionCode.equals(code[j])){
				regionName =  Constants.countyRegionName[j];
				break;
			}
		}
		if(StringUtils.isNotBlank(regionName)){
			return regionName;
		}
		for(int j=0;j<code1.length;j++){
			if(regionCode.equals(code1[j])){
				regionName =  Constants.dispatchRegionName[j];
				break;
			}
		}
		return regionName;
	}
	/**
	 * 使用组织机构编码 获取对应的 组织机构名称
	 * @param regionCode
	 * @return
	 */
	public String regionNameByCode(String regionName){
		String regionCode = "";
		String[] name1 = Constants.dispatchRegionName;//派驻机构名称
		String[] name = Constants.countyRegionName;//区县机构名称
		for(int j=0;j<name.length;j++){
			if(regionName.equals(name[j])){
				regionCode =  Constants.countyRegionCode[j];
				break;
			}
		}
		if(StringUtils.isNotBlank(regionCode)){
			return regionCode;
		}
		for(int j=0;j<name1.length;j++){
			if(regionName.equals(name1[j])){
				regionCode =  Constants.dispatchRegionCode[j];
				break;
			}
		}
		return regionCode;
	}
}