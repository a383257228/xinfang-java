package com.sinosoft.xf.dataPredict.dao;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ibm.icu.text.SimpleDateFormat;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.UserConstants.SysCodeType;
import com.sinosoft.xf.dataPredict.domain.DataPredictOpinionInfo;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.hiberante.HibernateDao;

/**
 * 民意分析    dao
 * @date 2017-08-12
 * @author liyang
 */
@Repository
public class DataPredictOpinionInfoDao extends HibernateDao<DataPredictOpinionInfo, String> {
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 获取新词列表
	 * @author liyang
	 * @param limit 
	 * @param start 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getNewWord(int start, int limit) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY oid DESC) AS ROWNUM,oid as oid ,new_Word as newWord,word_frequency as wordFrequency,word_Part as wordPart,edit_word as editWord,dictionary_flag as dictionaryFlag,ignore_flag as ignoreFlag,CREATE_DATE as createDate from DATAPREDICT_OPINION_INFO where dictionary_flag = '0' and ignore_flag = '0' and DEAL_FLAG = '0' order by CREATE_DATE desc) where ROWNUM >=  1 and ROWNUM <= 20 order by wordFrequency desc");
		//sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY oid DESC) AS ROWNUM, oid as oid, new_Word as newWord,word_frequency as wordFrequency,word_Part as wordPart,edit_word as editWord,dictionary_flag as dictionaryFlag,ignore_flag as ignoreFlag,CREATE_DATE as createDate from DATAPREDICT_OPINION_INFO where dictionary_flag = '0' and ignore_flag = '0' ) where ROWNUM >= "+start+" and ROWNUM <= "+(start+limit));
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 更新忽略信息 、加入词典
	 * @author liyang 
	 * @param person 
	 * @date 2017-08-21
	 * @throws Exception
	 */

	public void updateIgnore(String oids,String otype, PersonInfo person) {
		String oidArr [] = oids.split(",");
		StringBuffer sb = new StringBuffer();
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d);  
		if("ignore".equals(otype)) {
			sb.append("UPDATE DATAPREDICT_OPINION_INFO SET IGNORE_FLAG = '1',MODIFY_DATE = '"+dateNowStr+"', MODIFY_NAME = '"+person.getUserCname()+"', MODIFY_ID = '"+person.getId()+"' where ");
		} else if("add".equals(otype)) {
			sb.append("UPDATE DATAPREDICT_OPINION_INFO SET DICTIONARY_FLAG = '1' ,MODIFY_DATE = '"+dateNowStr+"', MODIFY_NAME = '"+person.getUserCname()+"', MODIFY_ID = '"+person.getId()+"' where ");
		}
		
		// OID = '1' or OID = '2'
		for(String oid : oidArr) {
			sb.append(" OID = '"+oid+"' OR" );
		}
		String sql = sb.toString();
		sql = sql.substring(0,sql.length() - 3);
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 保存新词
	 * @author liyang 
	 * @param person 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void saveNewWord(String oids, String newVal, PersonInfo person) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d);  
		//WORD_USED_FLAG标识要更新到分词词典中
		sb.append("UPDATE DATAPREDICT_OPINION_INFO SET NEW_WORD = '"+newVal+"',WORD_USED_FLAG ='0',MODIFY_DATE = '"+dateNowStr+"', MODIFY_NAME = '"+person.getUserCname()+"', MODIFY_ID = '"+person.getId()+"' where OID = '"+oids+"'");
		String sql = sb.toString();
		jdbcTemplate.update(sql);
		
	}
	/**
	 * 获取词典路径及分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAllDict() {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		//WORD_USED_FLAG标识要更新到分词词典中
		sb.append("SELECT CLASS_WORD_DICT_URL AS CLASSWORDDICTURL, DICT_NAME AS DICTNAME,DICT_TYPE AS DICTTYPE,STOP_WORD_DICT_URL AS STOPWORDDICTURL,TEMPORARY_WORD_DICT_URL AS TEMPORARYWORDDICTURL FROM DATAPREDICT_OPINION_DICT ORDER BY DICT_TYPE");
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 添加分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void addClass(Map<String, Object> map,PersonInfo person) {
		 StringBuffer sb = new StringBuffer();//EDIT_WORD
		 //WORD_USED_FLAG标识要更新到分词词典中
		 Date d = new Date();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
         String dateNowStr = sdf.format(d);  
         
		 sb.append("INSERT INTO DATAPREDICT_OPINION_DICT (OID, STOP_WORD_DICT_URL, TEMPORARY_WORD_DICT_URL, CREATE_DATE, CREATOR_NAME, CREATOR_ID, MODIFY_DATE, MODIFY_NAME, MODIFY_ID, CLASS_WORD_DICT_URL, HAS_CHILD_FLAG, PARENT_SEC_ID, PARENT_ID,CODE, DICT_NAME,CODE_TYPE,DICT_TYPE)");
		 sb.append("VALUES ('"+(String)map.get("oid")+"', '"+map.get("STOPWORDDICTURL")+"', '"+map.get("TEMPORARYWORDDICTURL")+"', '"+dateNowStr+"', '"+person.getUserCname()+"', '"+person.getId()+"', '"+dateNowStr+"', '', '', '"+map.get("CLASSWORDDICTURL")+"', 0, '"+map.get("parent_sec_Id")+"','"+map.get("parentId")+"', '"+map.get("CODE")+"', '"+map.get("className")+"','"+map.get("codeType")+"','"+map.get("classType")+"')");
		 String sql = sb.toString();
		 jdbcTemplate.update(sql);
	}
	
	/**
	 * 新词分类
	 * @author liyang 
	 * @param person 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void updateNewWordClass(Map<String, Object> map, PersonInfo person) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		//WORD_USED_FLAG标识要更新到分词词典中
		 Date d = new Date();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
         String dateNowStr = sdf.format(d);  
         
		 sb.append("UPDATE DATAPREDICT_OPINION_INFO SET  NEW_WORD = '"+map.get("newWord")+"', DICTIONARY_FLAG = '"+map.get("wordClass")+"', CODE_TYPE = '"+map.get("codeType")+"' ,MODIFY_DATE = '"+dateNowStr+"', MODIFY_NAME = '"+person.getUserCname()+"', MODIFY_ID = '"+person.getId()+"' WHERE OID = '"+map.get("oid")+"'");
		 String sql = sb.toString();
		 jdbcTemplate.update(sql);
		
	}
	
	/**
	 * 新词合并 第一个词更新成合并词
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void mergeNewWord(Map<String, Object> map,PersonInfo person) {
		 StringBuffer sb = new StringBuffer();//EDIT_WORD
		//WORD_USED_FLAG标识要更新到分词词典中
		 Date d = new Date();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
         String dateNowStr = sdf.format(d);  
         //第一个词更新成合并词
         String[] oids = ((String)map.get("oid")).split(",");
		 sb.append("UPDATE DATAPREDICT_OPINION_INFO SET  NEW_WORD = '"+map.get("newVal")+"' ,MODIFY_DATE = '"+dateNowStr+"', MODIFY_NAME = '"+person.getUserCname()+"', MODIFY_ID = '"+person.getId()+"'  WHERE OID = '"+oids[0]+"'");
		 String sql = sb.toString();
		 jdbcTemplate.update(sql);
		 

		
	}
	//其他词置成不可用
	public void disableNewWord(Map<String, Object> map,PersonInfo person) {
		 Date d = new Date();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
         String dateNowStr = sdf.format(d);  
         String[] oids = ((String)map.get("oid")).split(",");
		 //其他词置成不可用
		 StringBuffer sba = new StringBuffer();//EDIT_WORD
		 sba.append("UPDATE DATAPREDICT_OPINION_INFO SET IGNORE_FLAG = '1',MODIFY_DATE = '"+dateNowStr+"', MODIFY_NAME = '"+person.getUserCname()+"', MODIFY_ID = '"+person.getId()+"'  WHERE ");
		 for(int i = 1; i < oids.length; i++) {
			 sba.append(" OID = '"+oids[i]+"' OR "); 
		 }
		 String sqla = sba.toString();
		 sqla = sqla.substring(0,sqla.length() - 3);
		 jdbcTemplate.update(sqla);
		
	}
	public List<Map<String, Object>> getTreeNodeBean(String pid) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("SELECT OID , DICT_NAME AS NAME, CODE AS ID, PARENT_SEC_ID AS PID, HAS_CHILD_FLAG AS HASCHILDFLAG,CODE_TYPE AS CODETYPE FROM DATAPREDICT_OPINION_DICT WHERE PARENT_SEC_ID = '"+pid+"' AND PARENT_SEC_ID != ''");
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	public List<Map<String, Object>> getQuesTypeBean(String pid) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("SELECT OID , DICT_NAME AS NAME, CODE AS ID, PARENT_ID AS PID, HAS_CHILD_FLAG AS HASCHILDFLAG FROM DATAPREDICT_OPINION_DICT WHERE PARENT_ID = '"+pid+"'");
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	public List<Map<String, Object>> getWordCloud(String queTypeId) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("SELECT NEW_WORD AS TEXT,WORD_FREQUENCY AS SIZE FROM DATAPREDICT_OPINION_INFO WHERE DICTIONARY_FLAG = '"+queTypeId+"' ORDER BY WORD_FREQUENCY DESC");
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	public List<Map<String, Object>> getDictBase() {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("SELECT STOP_WORD_DICT_URL AS STOPWORDDICTURL, TEMPORARY_WORD_DICT_URL AS TEMPORARYWORDDICTURL, CLASS_WORD_DICT_URL AS CLASSWORDDICTURL,CODE FROM DATAPREDICT_OPINION_DICT WHERE CLASS_WORD_DICT_URL != ''");
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
		
	}
	public List<Map<String, Object>> getClassCode(String pid) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		//if(type == 0) {
			sb.append("SELECT  RIGHT(CAST(A.CODE AS INT),2) CODES,CODE  FROM DATAPREDICT_OPINION_DICT A WHERE A.PARENT_SEC_ID = '"+pid+"' ORDER BY CODES DESC");
		//} else {
		//	sb.append("SELECT  RIGHT(CAST(A.CODE as int),2) CODES,code  FROM DATAPREDICT_OPINION_DICT A where A.parent_id = '00fd0131fb3f2c700017' or  A.parent_id = '00fd0131fb3f2c700018' order by CODES desc");
		//}
		
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 获取词典类型   违纪违法 
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDictType() {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("SELECT OID AS OID,CODE AS CODE,DICT_NAME AS DICTNAME FROM DATAPREDICT_OPINION_DICT WHERE PARENT_SEC_ID = ''");
		
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取区信息
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAreaMess(String newWord) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("select ISSUE_REGION_CODE AS ISSUE_REGION_CODE,ISSUE_REGION_NAME AS ISSUE_REGION_NAME,COUNT(distinct(PETITION_BASIC_INFO_OID)) AS FORECAST_NUM FROM DATAPREDICT_OPINION_WORD  WHERE NEW_WORD = '"+newWord+"' AND ISSUE_REGION_FLAG = '2' GROUP BY ISSUE_REGION_CODE,ISSUE_REGION_NAME ");
		
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 获取派驻信息
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCommitteeMess(String newWord) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("select ISSUE_REGION_CODE AS ISSUE_REGION_CODE,ISSUE_REGION_NAME AS ISSUE_REGION_NAME,COUNT(distinct(PETITION_BASIC_INFO_OID)) AS FORECAST_NUM FROM DATAPREDICT_OPINION_WORD  WHERE  NEW_WORD = '"+newWord+"' AND ISSUE_REGION_FLAG = '3' GROUP BY ISSUE_REGION_CODE,ISSUE_REGION_NAME ");
		
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 信访件分布情况钻取查询基本信息
	 * @date 2017-08-25
	 * @author liyang
	 * @param filterValue
	 * @param person
	 * @return
	 */
	public int getLetterInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		StringBuffer sb = new StringBuffer("select count(*) from petition_basic_info basic ");
		sb.append("left join petition_accuser_info accuser on accuser.petition_basic_info_oid=basic.oid and accuser.accuser_num='1' ");
		sb.append("left join petition_accused_info accused on accused.petition_basic_info_oid=basic.oid and accused.accused_num='1' ");
		sb.append("left join petition_issue_info issue on basic.oid = issue.petition_basic_info_oid ");
		
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
		sb.append(" and basic.oid in (select PETITION_BASIC_INFO_OID AS OID  FROM DATAPREDICT_OPINION_WORD  WHERE NEW_WORD = '"+(String)map.get("newWord")+"'   AND ISSUE_REGION_FLAG = '"+(String)map.get("irFlag")+"' ");
		if(null!=map.get("Code")){
		sb.append("AND ISSUE_REGION_CODE = '"+(String)map.get("Code")+"'");
		}
		sb.append(")");
		
		//System.out.println("select PETITION_BASIC_INFO_OID AS OID  FROM DATAPREDICT_OPINION_WORD  WHERE REGION_CODE != '3100        ' and NEW_WORD = '"+(String)map.get("newWord")+"' AND ISSUE_REGION_CODE = '"+(String)map.get("Code")+"'  AND ISSUE_REGION_FLAG = '"+(String)map.get("irFlag")+"'");
		//System.out.println(sb.toString());
		
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
		sb.append("left join petition_issue_info issue on basic.oid = issue.petition_basic_info_oid ");
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
		sb.append(" and basic.oid in (select PETITION_BASIC_INFO_OID AS OID  FROM DATAPREDICT_OPINION_WORD  WHERE NEW_WORD = '"+(String)map.get("newWord")+"'   AND ISSUE_REGION_FLAG = '"+(String)map.get("irFlag")+"' ");
		if(null!=map.get("Code")){
		sb.append("AND ISSUE_REGION_CODE = '"+(String)map.get("Code")+"'");
		}
		sb.append(")");
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		//System.out.println("select PETITION_BASIC_INFO_OID AS OID  FROM DATAPREDICT_OPINION_WORD  WHERE REGION_CODE != '3100        ' and NEW_WORD = '"+(String)map.get("newWord")+"' AND ISSUE_REGION_CODE = '"+(String)map.get("Code")+"' AND ISSUE_REGION_FLAG = '"+(String)map.get("irFlag")+"' ");
		//System.out.println(sb.toString());
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 获取某一区的信访编号
	 * @date 2017-08-25
	 * @author liyang
	 * @throws IOException
	 * @throws Exception
	 */
	public List<Map<String, Object>> getALLPitId(Map<String, Object> map) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("select PETITION_BASIC_INFO_OID AS OID ,ISSUE_REGION_CODE AS ISSUE_REGION_CODE,ISSUE_REGION_NAME AS ISSUE_REGION_NAME FROM DATAPREDICT_OPINION_WORD  WHERE REGION_CODE != '3100        ' and NEW_WORD = '"+(String)map.get("newWord")+"' AND ISSUE_REGION_CODE = '"+(String)map.get("orgCode")+"' ");
		
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取某一分类下的词频
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getWordDis(String queTypeId) {
		StringBuffer sb = new StringBuffer();//EDIT_WORD
		sb.append("select NEW_WORD as NEWWORD,WORD_FREQUENCY as WORDFREQUENCY from DATAPREDICT_OPINION_INFO where DICTIONARY_FLAG = '"+queTypeId+"'");
		
		String sql = sb.toString();
		return jdbcTemplate.queryForList(sql);
	}
}