package com.sinosoft.xf.dataPredict.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.dataPredict.constants.DataPredictTreeForestConstants;
import com.sinosoft.xf.dataPredict.domain.DataPredictTreeForest;
import com.sinosoft.xf.petition.accept.dao.PetitionBasicInfoDao;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.util.sql.DateUtil;
import com.sinosoft.xf.util.sql.SqlUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;

/**
 * 树木与森林     dao
 * @date 2017-08-12
 * @author liyang
 */
@Repository
public class DataPredictTreeForestDao extends HibernateDao<DataPredictTreeForest, String> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PetitionBasicInfoDao basicInfoDao;
	@Autowired
	PetitionBasicInfoManager petitionBasicInfoManager;
	int mgdepth = DataPredictTreeForestConstants.MAX_GROW_DEPTH;
	int mnnum = DataPredictTreeForestConstants.MAX_NEXUS_NUM;
	int mndistanceo = DataPredictTreeForestConstants.MIN_NEXUS_DISTANCE_OUT;
	int mndistancei = DataPredictTreeForestConstants.MIN_NEXUS_DISTANCE_IN;
	public List<Map<String, Object>> queryTreeForest(String keyWords){
		StringBuilder sql = 
				new StringBuilder("SELECT AID_1,AID_2,NEXUS,DISTANCE,WORK_UNIT,HOME_PLACE,PETITION_NO,ACCUSER,PETITION_NO_PREDICTED FROM "
						+ "TF"//ZZ
						+ "_ACCUSED_NEXUS T WHERE T.AID_1 LIKE '");	
		sql.append(keyWords);
		sql.append("%'");
		sql.append(" UNION ");
		sql.append("SELECT AID_1,AID_2,NEXUS,DISTANCE,WORK_UNIT,HOME_PLACE,PETITION_NO,ACCUSER,PETITION_NO_PREDICTED FROM "
				+ "TF"//ZZ
				+ "_ACCUSED_NEXUS T WHERE T.AID_2 LIKE '");
		sql.append(keyWords);
		sql.append("%'");
//		System.out.println("111 "+sql.toString());
		return jdbcTemplate.queryForList(sql.toString());
	}
	public List<Map<String, Object>> dataPredictFindAccused(Map<String, Object> map){
		StringBuffer sqlBuffer = new StringBuffer();
//		sqlBuffer.append("SELECT ACCUSED_NAME,ACCUSED_SEX_NAME,PETITION_BASIC_INFO_OID FROM "
//				+ "TF"//ZZ
//				+ "_PETITION_ACCUSED_INFO WHERE ACCUSED_NAME LIKE '%");
		String aname = map.get("aname").toString();//accused name
//		sqlBuffer.append(aname);
//		sqlBuffer.append("%'");
		
		sqlBuffer.append("SELECT DISTINCT ACCUSED FROM(SELECT ACCUSED_1 AS ACCUSED FROM "
				+ "TF"
				+ "_ACCUSED_NEXUS WHERE ACCUSED_1 LIKE '%" );
		sqlBuffer.append(aname);
		sqlBuffer.append("%' AND NEXUS=1 UNION SELECT ACCUSED_2 AS ACCUSED FROM "
				+ "TF"
				+ "_ACCUSED_NEXUS WHERE ACCUSED_2 LIKE '%");
		sqlBuffer.append(aname);
		sqlBuffer.append("%' AND NEXUS=1)");
		
//		System.out.println("222 "+sqlBuffer.toString());
		List<Map<String, Object>> l = jdbcTemplate.queryForList(sqlBuffer.toString());
		
		
		return l;
	}
	
	public Set<String> mohuPipei(String aname){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT ACCUSED_NAME,ACCUSED_SEX_NAME,PETITION_BASIC_INFO_OID FROM "
				+ "TF"//ZZ
				+ "_PETITION_ACCUSED_INFO WHERE ACCUSED_NAME LIKE '%");
		sqlBuffer.append(aname);
		sqlBuffer.append("%'");
//		System.out.println("333 "+sqlBuffer.toString());
		List<Map<String, Object>> l = jdbcTemplate.queryForList(sqlBuffer.toString());
		Set<String> accusedSet=new HashSet<String>();	
		for(Map<String, Object> accused : l) {
			//System.out.println(accused);
			accusedSet.add((String) accused.get("ACCUSED_NAME"));
		}
		return accusedSet;
	}
	public Set<String> mohuPipei2(String aname){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT DISTINCT ACCUSED FROM(SELECT ACCUSED_1 AS ACCUSED FROM "
				+ "TF"
				+ "_ACCUSED_NEXUS WHERE ACCUSED_1 LIKE '%");
		sqlBuffer.append(aname);
		sqlBuffer.append("%' UNION SELECT ACCUSED_2 AS ACCUSED FROM "
				+ "TF"
				+ "_ACCUSED_NEXUS WHERE ACCUSED_2 LIKE '%");
		sqlBuffer.append(aname);
		sqlBuffer.append("%')");
		System.out.println("444 "+sqlBuffer.toString());
		List<Map<String, Object>> l = jdbcTemplate.queryForList(sqlBuffer.toString());
		Set<String> accusedSet=new HashSet<String>();	
		for(Map<String, Object> accused : l) {
			//System.out.println(accused);
			accusedSet.add((String)accused.get("ACCUSED"));
		}
		return accusedSet;
	}
	
	public String fetchFirst(){
		String first = "";
		String sql="SELECT ACCUSED_1 FROM TF_ACCUSED_NEXUS WHERE DISTANCE=(SELECT MAX(DISTANCE)FROM TF_ACCUSED_NEXUS WHERE NEXUS=1) AND NEXUS=1 FETCH FIRST 1 ROWS ONLY";
//		System.out.println("555 "+sql.toString());
		List<Map<String, Object>> l = jdbcTemplate.queryForList(sql);//
		for(Map<String, Object> m : l) {
			first = m.get("ACCUSED_1").toString();					
		}
		return first;
	}
	
	//1个
	public String buildSql1(String aname,int b,boolean detail){
		StringBuffer sqlBuffer = new StringBuffer();
		if(detail){
			sqlBuffer.append("SELECT ACCUSED,DISTANCE,WORK_UNIT,HOME_PLACE,PETITION_NO,ACCUSER,PETITION_NO_PREDICTED FROM(SELECT ACCUSED_2 AS ACCUSED,DISTANCE,WORK_UNIT,HOME_PLACE,PETITION_NO,ACCUSER,PETITION_NO_PREDICTED FROM "
					+ "TF"//ZZ
					+ "_ACCUSED_NEXUS WHERE ACCUSED_1='");
			sqlBuffer.append(aname);
			sqlBuffer.append("'UNION SELECT ACCUSED_1 AS ACCUSED,DISTANCE,WORK_UNIT,HOME_PLACE,PETITION_NO, ACCUSER,PETITION_NO_PREDICTED FROM "
					+ "TF"//ZZ
					+ "_ACCUSED_NEXUS WHERE ACCUSED_2 ='");
		}else{
			sqlBuffer.append("SELECT * FROM(SELECT ACCUSED_2 AS ACCUSED,DISTANCE FROM "
					+ "TF"//ZZ
					+ "_ACCUSED_NEXUS WHERE ACCUSED_1='");
			sqlBuffer.append(aname);
			sqlBuffer.append("' AND NEXUS=1 UNION SELECT ACCUSED_1 AS ACCUSED,DISTANCE FROM "
					+ "TF"//ZZ
					+ "_ACCUSED_NEXUS WHERE ACCUSED_2 ='");
		}
		sqlBuffer.append(aname);
		sqlBuffer.append("' AND NEXUS=1) WHERE DISTANCE>");
		sqlBuffer.append(mndistanceo);
		sqlBuffer.append(" ORDER BY DISTANCE DESC FETCH FIRST ");
		sqlBuffer.append(b);
		sqlBuffer.append(" ROWS ONLY");
		
		return sqlBuffer.toString();
	}
	//2个
	public String buildSql2(String aname,int b,boolean detail){
		StringBuffer sqlBuffer = new StringBuffer();
		if(detail){
			sqlBuffer.append("SELECT ACCUSED_1,ACCUSED_2,DISTANCE,WORK_UNIT,HOME_PLACE,PETITION_NO, ACCUSER FROM "
					+ "TF"//ZZ
					+ "_ACCUSED_NEXUS WHERE ACCUSED_1 IN (");
			sqlBuffer.append(aname);
			sqlBuffer.append(") OR ACCUSED_2 IN (");
			sqlBuffer.append(aname);
		}else{
			sqlBuffer.append("SELECT ACCUSED_1,ACCUSED_2,DISTANCE FROM "
					+ "TF"//ZZ
					+ "_ACCUSED_NEXUS WHERE ACCUSED_1 IN (");
			sqlBuffer.append(aname);
			sqlBuffer.append(") OR ACCUSED_2 IN (");
			sqlBuffer.append(aname);
		}
		sqlBuffer.append(") AND NEXUS=1 ORDER BY DISTANCE DESC FETCH FIRST ");
		sqlBuffer.append(b);
		sqlBuffer.append(" ROWS ONLY");
		
		return sqlBuffer.toString();
	}
	
	public String buildSql3(String aname){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT PETITION_BASIC_INFO_"
				+ "TEMP_"
				+ "OID FROM "
				//+ "ZZ_"
				+ "PETITION_ACCUSED_INFO"
				+ "_TEMP"
				+ " WHERE ACCUSED_NAME='");
		sqlBuffer.append(aname);
		sqlBuffer.append("'");
		return sqlBuffer.toString();
	}

	public Set<String> grow(Set<String> accusedSet){
		Set<String> tempSet=new HashSet<String>();
		String hahaSql="";
		for(String accused1:accusedSet){
			hahaSql=buildSql1(accused1,mnnum,false);
			//System.out.println(hahaSql);
//			System.out.println("666 "+hahaSql);
			List<Map<String, Object>> l = jdbcTemplate.queryForList(hahaSql.toString());//
			for(Map<String, Object> m : l) {
				String accused2 = m.get("ACCUSED").toString();
				tempSet.add(accused2);					
			}
		}
		tempSet.addAll(accusedSet);
		return tempSet;		
	}
	public List<Map<String, Object>> dptfAccurateDoubleBrief2(Map<String, Object> map){	
		
		boolean detail = Integer.parseInt((String) map.get("detail"))==1;
		String aname = map.get("aname").toString();//accused name
		//System.out.println("-"+aname+"-");
		if(aname.length()<1){
			//System.out.println("empty"+aname);
			aname=fetchFirst();
			//System.out.println(aname);
		}
		Set<String> accusedSet=new HashSet<String>();				
		accusedSet.add(aname);		

		for(int i=0;i<mgdepth;i++){
			accusedSet=grow(accusedSet);
		}
		StringBuffer nameBuffer = new StringBuffer();
		for(String accused1:accusedSet){
			nameBuffer.append("'");
			nameBuffer.append(accused1);
			nameBuffer.append("',");
		}
		nameBuffer=nameBuffer.deleteCharAt(nameBuffer.length() - 1);
		//System.out.println("~~"+nameBuffer.toString());
//		System.out.println("777 "+buildSql2(nameBuffer.toString(),40,detail));
		List<Map<String, Object>> l = jdbcTemplate.queryForList(buildSql2(nameBuffer.toString(),40,detail));
		return l;
	}
	public List<Map<String, Object>> dptfAccurateSingleDetail2(Map<String, Object> map){
		
		String aname = map.get("aname").toString();//accused name
		String hahaSql=buildSql1(aname,20,true);
//		System.out.println("888 "+hahaSql.toString());
		List<Map<String, Object>> l = jdbcTemplate.queryForList(hahaSql.toString());//
		return l;
	}
	
	public List<Map<String, Object>> queryIssueTypeTop5(Map<String, Object> map,PersonInfo person) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select issue.issue_type_name as typeName,issue.issue_type_code as issueTypeCode,count(*) as count from petition_basic_info basic ");
		sqlBuffer.append(" left join issue_type_info issue on basic.oid=issue.petition_basic_info_oid where basic.petition_class_code = '1' ");
		//sqlBuffer.append(SqlUtil.municipalAndcommittee(map, person));
		/*try {
			sqlBuffer.append(DateUtil.queryStartdateAndEnddate(map));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		sqlBuffer.append(" and basic.petition_source_code not in (select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送') ) ");
		sqlBuffer.append(" group by issue.issue_type_name,issue.issue_type_code order by count desc fetch first 5 rows only");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}

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
		sqlBuffer.append("and issue.issue_type_code='").append(code).append("' ");
		
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
	
	public List<String> getOidsByName(Map<String,Object> map){
		String aname = map.get("aname").toString();
		//System.out.println("1	"+aname);
		aname="c9f2cec0bbaa";
		//System.out.println("2	"+aname);
		String hahaSql=buildSql3(aname);
		//System.out.println("3	"+hahaSql);
		ArrayList<String> result = new ArrayList<String>();
//		System.out.println("999 "+hahaSql.toString());
		List<Map<String, Object>> l = jdbcTemplate.queryForList(hahaSql.toString());
		for(Map<String, Object> m : l) {
			String oid = m.get("PETITION_BASIC_INFO"
					+ "_TEMP"
					+ "_OID").toString();
			result.add(oid);				
		}
		//System.out.println("haha");
		//System.out.println(result);
		return result;
		
	}
	/**
	 * 信访件分布情况钻取查询基本信息
	 * @date 2017-08-25
	 * @author guoh
	 * @param filterValue
	 * @param person
	 * @return
	 */
	public int getLetterInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer("select count(*) from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join petition_issue_info issue on basic.oid = issue.petition_basic_info_oid right join ( ");
		sb.append(" select petition_no petition_no,min(region_code) region_code from petition_basic_info   ");
		sb.append(" where petition_class_code = '1' ");
		//没有时间维度去掉时间查询
		//String dateValue = DateUtil.queryStartdateAndEnddate(map);
		//sb.append(dateValue.replace("basic.petition_date", "petition_date"));
		
		if(null!=map.get("orgCode")){//不为空问题类别变化情况、数量前五位
			String code = map.get("orgCode").toString().substring(0, 4);
			if(!"".equals(map.get("index"))&&null!=map.get("index")){
				if("0".equals(map.get("index").toString())){//自收件
					sb.append(" and petition_source_code like '01%' ");
				}else if("1".equals(map.get("index").toString())){//下级转、交办
					sb.append(" and deal_type_code in ('0302','0201','0202')  ");
				}else if("2".equals(map.get("index").toString())){//上级转、交办
					sb.append(" and petition_source_code not like '01%' ");
				}
			}
			if("310000000000".equals(map.get("orgCode").toString())){
				sb.append(" and issue_region_code like '31%' ");
			}else{
				sb.append(" and substr(issue_region_code,1,4) ='").append(code).append("' ");
			}
		}
		sb.append(" group by petition_no ) basicCode  on basicCode.region_code = basic.region_code and basicCode.petition_no = basic.petition_no ");
		sb.append(" where basic.petition_class_code = '1' ");
		
		if(null!=map.get("accusedName")){
			sb.append("and (accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
			sb.append("OR basic.first_accused like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
			sb.append("OR basic.other_accuseds like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%') ");			
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		//sb.append(dateValue);
		
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
				sb.append("and issue.issue_type_code='").append(typeCode).append("'");
			}
			if("310000000000".equals(map.get("orgCode").toString())){
				sb.append(" and basic.issue_region_code like '31%' ");
			}else{
				sb.append(" and substr(basic.issue_region_code,1,4) ='").append(code).append("'");
			}
		}
		if(!"".equals(map.get("index"))&&null!=map.get("index")){
			if("0".equals(map.get("index").toString())){//自收件
				sb.append(" and petition_source_code like '01%' ");
			}else if("1".equals(map.get("index").toString())){//下级转、交办
				sb.append(" and deal_type_code in ('0302','0201','0202')  ");
			}else if("2".equals(map.get("index").toString())){//上级转、交办
				sb.append(" and petition_source_code not like '01%' ");
			}
		}
		sb.append(" and basic.petition_source_code not in (select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送') ) ");
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 信访件分布情况钻取查询列表
	 * @date 2017-08-25
	 * @author guoh
	 * @param filterValue
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 */
	public List<Map<String, Object>> getLetterInfoList(Map<String, Object> map,int start,int limit,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select basic.oid as bId from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join petition_issue_info issue on basic.oid = issue.petition_basic_info_oid right join ( ");
		sb.append(" select petition_no petition_no,min(region_code) region_code from petition_basic_info   ");
		sb.append(" where petition_class_code = '1' ");
		//没有时间维度去掉时间查询
		//String dateValue = DateUtil.queryStartdateAndEnddate(map);
		//sb.append(dateValue.replace("basic.petition_date", "petition_date"));
		
		if(null!=map.get("orgCode")){
			String code = map.get("orgCode").toString().substring(0, 4);
			if(!"".equals(map.get("index"))&&null!=map.get("index")){
				if("0".equals(map.get("index").toString())){//自收件
					sb.append(" and petition_source_code like '01%' ");
				}else if("1".equals(map.get("index").toString())){//下级转、交办
					sb.append(" and deal_type_code in ('0302','0201','0202')  ");
				}else if("2".equals(map.get("index").toString())){//上级转、交办
					sb.append(" and petition_source_code not like '01%' ");
				}
			}
			if("310000000000".equals(map.get("orgCode").toString())){
				sb.append(" and issue_region_code like '31%' ");
			}else{
				sb.append(" and substr(issue_region_code,1,4) ='").append(code).append("' ");
			}
		}
		sb.append(" group by petition_no ) basicCode  on basicCode.region_code = basic.region_code and basicCode.petition_no = basic.petition_no ");
		sb.append(" where basic.petition_class_code = '1' ");
		
		if(null!=map.get("accusedName")){
			sb.append("and (accused.accused_name like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
			sb.append("OR basic.first_accused like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%' ");
			sb.append("OR basic.other_accuseds like '%").append(Encrypt.encrypt(map.get("accusedName").toString())).append("%') ");		
		}
		if(null!=map.get("accuserName")){
			sb.append("and accuser.accuser_name like '%").append(Encrypt.encrypt(map.get("accuserName").toString())).append("%' ");
		}
		//sb.append(dateValue);
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
				sb.append("and issue.issue_type_code='").append(typeCode).append("'");
			}
			if("310000000000".equals(map.get("orgCode").toString())){
				sb.append(" and basic.issue_region_code like '31%' ");
			}else{
				sb.append(" and substr(basic.issue_region_code,1,4) ='").append(code).append("'");
			}
		}
		if(!"".equals(map.get("index"))&&null!=map.get("index")){
			if("0".equals(map.get("index").toString())){//自收件
				sb.append(" and petition_source_code like '01%' ");
			}else if("1".equals(map.get("index").toString())){//下级转、交办
				sb.append(" and deal_type_code in ('0302','0201','0202')  ");
			}else if("2".equals(map.get("index").toString())){//上级转、交办
				sb.append(" and petition_source_code not like '01%' ");
			}
		}
		sb.append(" and basic.petition_source_code not in (select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送') ) ");
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sb.toString());
	}
}