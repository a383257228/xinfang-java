package com.sinosoft.xf.dataPredict.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.domain.DataPredictDistributionInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;

/**
 * 信访件分布     dao
 * @date 2017-08-12
 * @author liyang
 */
@Repository
public class DataPredictDistributionInfoDao extends HibernateDao<DataPredictDistributionInfo, String> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询区下个月的预测结果
	 * @author shilei
	 * @param swFlag
	 * @param dateFlag
	 * @param startDateValue
	 * @param endDateValue
	 * @param person
	 * @param date  时间数组
	 * @return
	 */
	public List<Map<String, Object>> dataPredict(String swFlag,String dateFlag,String startDateValue,String endDateValue,PersonInfo person,String[] date){
		StringBuffer sqlBuffer = new StringBuffer();	
		sqlBuffer.append("SELECT ISSUE_REGION_CODE,ISSUE_REGION_NAME,FORECAST_NUM,FORECAST_DATE FROM DATAPREDICT_DISTRIBUTION_INFO WHERE  ISSUE_REGION_FLAG=2 AND FORECAST_DATE='").append(date[0]).append("' AND CREATE_DATE=(SELECT MAX(CREATE_DATE) FROM DATAPREDICT_DISTRIBUTION_INFO)");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 查询派驻下个月的预测结果
	 * @param swFlag
	 * @param dateFlag
	 * @param startDateValue
	 * @param endDateValue
	 * @param person
	 * @param date
	 * @return
	 */
	public List<Map<String, Object>> dataCityOne(String swFlag,String dateFlag,String startDateValue,String endDateValue,PersonInfo person,String[] date){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT ISSUE_REGION_CODE,ISSUE_REGION_NAME,FORECAST_NUM,ISSUE_REGION_FLAG,FORECAST_DATE FROM DATAPREDICT_DISTRIBUTION_INFO WHERE  ISSUE_REGION_FLAG=3 AND FORECAST_DATE='").append(date[0]).append("' AND CREATE_DATE=(SELECT MAX(CREATE_DATE) FROM DATAPREDICT_DISTRIBUTION_INFO)");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 查询区和派驻的趋势图
	 * @param code
	 * @param person
	 * @param date
	 * @return
	 */
	public List<Map<String, Object>> t(String code,PersonInfo person,String[] date){
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("SELECT DATE,去年,(CASE WHEN ID<7 THEN COALESCE (今年, 0) END) 今年,预测 FROM (SELECT ID,DATE,COALESCE (q.NUM, 0) AS 去年,今年,预测 FROM(SELECT ID,substr(DATE,6,2) AS DATE,今年,预测 FROM(SELECT ROW_NUMBER () over () AS ID,d.DATE,j.NUM AS 今年,y.NUM AS 预测 FROM(SELECT substr(DATE+ 1 months,1,7)AS DATE FROM(SELECT DATE FROM (SELECT (DATE - 6 months) AS date FROM(SELECT FORECAST_DATE AS date FROM DATAPREDICT_DISTRIBUTION_INFO WHERE CREATE_DATE=(SELECT MAX(CREATE_DATE) FROM DATAPREDICT_DISTRIBUTION_INFO) GROUP BY FORECAST_DATE))UNION(SELECT FORECAST_DATE AS date FROM DATAPREDICT_DISTRIBUTION_INFO WHERE CREATE_DATE=(SELECT MAX(CREATE_DATE) FROM DATAPREDICT_DISTRIBUTION_INFO) GROUP BY FORECAST_DATE))) d LEFT JOIN(SELECT YEAR_MONTH,count(1) AS NUM FROM(SELECT m.*, (CASE WHEN DAY (m.Petition_Date) > 20 THEN substr((m.Petition_Date + 1 months),1,7)ELSE substr(m.Petition_Date, 1, 7)END) YEAR_MONTH FROM(SELECT d.oid,a.Issue_Region_Name,substr(a.Issue_Region_Code, 1, 4) Issue_Region_Code4,a.Region_Code,a.Petition_Date FROM PETITION_BASIC_INFO a LEFT JOIN Petition_Issue_Info b ON a.Oid = b.Petition_Basic_Info_Oid LEFT JOIN Petition_Deal_Info d ON a.Oid = d.Petition_Basic_Info_Oid WHERE a.Petition_Class_Code = '1'AND d.valid_flag = '0'AND a.ISSUE_REGION_CODE LIKE '31%') m LEFT JOIN Petition_End_Info e ON m.Oid = e.Petition_Deal_Info_Oid)WHERE ");			
		//传参
		if("3101".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3101'OR ISSUE_REGION_CODE4 ='3113')");
		}
		else if("3106".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3106'OR ISSUE_REGION_CODE4 ='3108')");
		}
		else if("3115".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3115'OR ISSUE_REGION_CODE4 ='3119')");
		}else{
			sqlBuffer.append("ISSUE_REGION_CODE4 = '").append(code).append("'");
		}		
		
		//sqlBuffer.append("(ISSUE_REGION_CODE4 = '").append(code).append("' or ISSUE_REGION_CODE4=(CASE WHEN ('3106'='").append(code).append("') THEN '3108' WHEN ('3115'='").append(code).append("') THEN '3119' WHEN('3101'='").append(code).append("') THEN '3113' ELSE '").append(code).append("' END))");
		//传参
		sqlBuffer.append("AND PETITION_DATE >= '").append(date[2]).append("'AND PETITION_DATE < '").append(date[0]).append("' GROUP BY YEAR_MONTH) j ON d.DATE=j.YEAR_MONTH LEFT JOIN(SELECT NUM,ISSUE_REGION_NAME,substr(FORECAST_DATE +1 MONTH, 1, 7) AS FORECAST_DATE FROM(SELECT FORECAST_NUM AS NUM,ISSUE_REGION_NAME,FORECAST_DATE FROM DATAPREDICT_DISTRIBUTION_INFO WHERE ");
		//传参
		sqlBuffer.append("ISSUE_REGION_CODE = '").append(code).append("' AND FORECAST_DATE >= '").append(date[0]).append("'AND FORECAST_DATE < '").append(date[1]).append("'AND CREATE_DATE=(SELECT MAX(CREATE_DATE) FROM DATAPREDICT_DISTRIBUTION_INFO))) y ON d.DATE=y.FORECAST_DATE)) m ");
		sqlBuffer.append("LEFT JOIN(SELECT count(1) AS num,substr(YEAR_MONTH,6,2) YEAR_MONTH FROM(SELECT * FROM(SELECT m.*, (CASE WHEN DAY (m.Petition_Date) > 20 THEN substr((m.Petition_Date + 1 months),1,7)ELSE substr(m.Petition_Date, 1, 7)END) YEAR_MONTH FROM(SELECT d.oid,a.Issue_Region_Name,substr(a.Issue_Region_Code, 1, 4) Issue_Region_Code4,a.Region_Code,a.Petition_Date FROM PETITION_BASIC_INFO a LEFT JOIN Petition_Issue_Info b ON a.Oid = b.Petition_Basic_Info_Oid LEFT JOIN Petition_Deal_Info d ON a.Oid = d.Petition_Basic_Info_Oid WHERE a.Petition_Class_Code = '1'AND d.valid_flag = '0'AND a.ISSUE_REGION_CODE LIKE '31%') m LEFT JOIN Petition_End_Info e ON m.Oid = e.Petition_Deal_Info_Oid)WHERE ");
		//传参
		if("3101".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3101'OR ISSUE_REGION_CODE4 ='3113')");
		}
		else if("3106".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3106'OR ISSUE_REGION_CODE4 ='3108')");
		}
		else if("3115".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3115'OR ISSUE_REGION_CODE4 ='3119')");
		}else{
			sqlBuffer.append("ISSUE_REGION_CODE4 = '").append(code).append("'");
		}
				
		//sqlBuffer.append("(ISSUE_REGION_CODE4 = '").append(code).append("' or ISSUE_REGION_CODE4=(CASE WHEN ('3106'='").append(code).append("') THEN '3108' WHEN ('3115'='").append(code).append("') THEN '3119' WHEN('3101'='").append(code).append("') THEN '3113' ELSE '").append(code).append("' END))");
		//传参
		sqlBuffer.append("AND PETITION_DATE >= '").append(date[3]).append("'AND PETITION_DATE < '").append(date[2]).append("')GROUP BY YEAR_MONTH ORDER BY YEAR_MONTH)q ON q.YEAR_MONTH=m.DATE)");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 查询上海市的趋势图
	 * @param code
	 * @param person
	 * @param date
	 * @return
	 */
	public List<Map<String, Object>> year(String code,PersonInfo person,String[] date){
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("SELECT DATE,去年,(CASE WHEN ID<7 THEN COALESCE (今年, 0) END) 今年,预测 FROM(SELECT ID,DATE,COALESCE(q.NUM,0) AS 去年,今年,预测 FROM(SELECT ID,substr(DATE, 6, 2) AS DATE,今年,预测 FROM(SELECT ROW_NUMBER () over () AS ID,d.DATE,j.NUM AS 今年,y.NUM AS 预测 FROM(SELECT substr(DATE + 1 months, 1, 7) AS DATE FROM(SELECT DATE FROM(SELECT(DATE - 6 months) AS date FROM(SELECT FORECAST_DATE AS date FROM DATAPREDICT_DISTRIBUTION_INFO WHERE CREATE_DATE = (SELECT MAX(CREATE_DATE)FROM DATAPREDICT_DISTRIBUTION_INFO)GROUP BY FORECAST_DATE))UNION(SELECT FORECAST_DATE AS date FROM DATAPREDICT_DISTRIBUTION_INFO WHERE CREATE_DATE = (SELECT MAX(CREATE_DATE)FROM DATAPREDICT_DISTRIBUTION_INFO)GROUP BY FORECAST_DATE))) d LEFT JOIN (SELECT YEAR_MONTH,count(1) AS NUM FROM(SELECT m.*, (CASE WHEN DAY (m.Petition_Date) > 20 THEN substr((m.Petition_Date + 1 months),1,7)ELSE substr(m.Petition_Date, 1, 7)END) YEAR_MONTH FROM(SELECT d.oid,a.Issue_Region_Name,substr(a.Issue_Region_Code, 1, 4) Issue_Region_Code4,a.Region_Code,a.Petition_Date FROM PETITION_BASIC_INFO a LEFT JOIN Petition_Issue_Info b ON a.Oid = b.Petition_Basic_Info_Oid LEFT JOIN Petition_Deal_Info d ON a.Oid = d.Petition_Basic_Info_Oid WHERE a.Petition_Class_Code = '1'AND d.valid_flag = '0'AND a.ISSUE_REGION_CODE LIKE '31%') m LEFT JOIN Petition_End_Info e ON m.Oid = e.Petition_Deal_Info_Oid)WHERE ");			
		//传参
		sqlBuffer.append("REGION_CODE = '310000000000'AND PETITION_DATE >= '").append(date[2]).append("'AND PETITION_DATE < '").append(date[0]).append("' GROUP BY YEAR_MONTH) j ON d.DATE = j. YEAR_MONTH ");
		sqlBuffer.append("LEFT JOIN (SELECT NUM,ISSUE_REGION_NAME,substr(FORECAST_DATE + 1 MONTH, 1, 7) AS FORECAST_DATE FROM(SELECT FORECAST_NUM AS NUM,ISSUE_REGION_NAME,FORECAST_DATE FROM DATAPREDICT_DISTRIBUTION_INFO WHERE ");
		//传参
		sqlBuffer.append("ISSUE_REGION_NAME = '上海市'AND FORECAST_DATE >= '").append(date[0]).append("'AND FORECAST_DATE < '").append(date[1]).append("'AND CREATE_DATE = (SELECT MAX(CREATE_DATE)FROM DATAPREDICT_DISTRIBUTION_INFO))) y ON d.DATE = y.FORECAST_DATE)) m ");
		sqlBuffer.append("LEFT JOIN (SELECT count(1) AS num,substr(YEAR_MONTH, 6, 2) YEAR_MONTH FROM(SELECT * FROM(SELECT m.*, (CASE WHEN DAY (m.Petition_Date) > 20 THEN substr((m.Petition_Date + 1 months),1,7)ELSE substr(m.Petition_Date, 1, 7)END) YEAR_MONTH FROM(SELECT d.oid,a.Issue_Region_Name,substr(a.Issue_Region_Code, 1, 4) Issue_Region_Code4,a.Region_Code,a.Petition_Date FROM PETITION_BASIC_INFO a LEFT JOIN Petition_Issue_Info b ON a.Oid = b.Petition_Basic_Info_Oid LEFT JOIN Petition_Deal_Info d ON a.Oid = d.Petition_Basic_Info_Oid WHERE a.Petition_Class_Code = '1'AND d.valid_flag = '0'AND a.ISSUE_REGION_CODE LIKE '31%') m LEFT JOIN Petition_End_Info e ON m.Oid = e.Petition_Deal_Info_Oid)WHERE ");
		//传参
		sqlBuffer.append("REGION_CODE = '310000000000'AND PETITION_DATE >= '").append(date[3]).append("'AND PETITION_DATE < '").append(date[2]).append("')GROUP BY YEAR_MONTH ORDER BY YEAR_MONTH) q ON q. YEAR_MONTH = m.DATE)");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	
	/**
	 * 本委/派驻趋势图
	 * @param code
	 * @param person
	 * @param date
	 * @return
	 */
	public List<Map<String, Object>> date(String code,PersonInfo person,String[] date){
		code="3101";
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("SELECT s.YEAR_MONTH as DATE,l.历史,y.预测 FROM ((SELECT YEAR_MONTH FROM(SELECT m.*, ( CASE WHEN DAY (m.Petition_Date) > 20 THEN substr((m.Petition_Date + 1 months),1,7)ELSE substr(m.Petition_Date, 1, 7)END) YEAR_MONTH FROM(SELECT d.oid,substr(a.Issue_Region_Code, 1, 4) Issue_Region_Code4,a.Petition_Date FROM PETITION_BASIC_INFO a LEFT JOIN Petition_Issue_Info b ON a.Oid = b.PETITION_BASIC_INFO_OID LEFT JOIN Petition_Deal_Info d ON a.Oid = d.Petition_Basic_Info_Oid WHERE a.Petition_Class_Code = '1' AND d.valid_flag = '0' AND a.ISSUE_REGION_CODE LIKE '31%') m LEFT JOIN Petition_End_Info e ON m.Oid = e.Petition_Deal_Info_Oid)WHERE");			
		//传参
		if("3101".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3101'OR ISSUE_REGION_CODE4 ='3113')");
		}
		else if("3106".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3106'OR ISSUE_REGION_CODE4 ='3108')");
		}
		else if("3115".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3115'OR ISSUE_REGION_CODE4 ='3119')");
		}else{
			sqlBuffer.append("ISSUE_REGION_CODE4 = '3115'");
		}
		sqlBuffer.append("AND PETITION_DATE > '2016-08-20'AND PETITION_DATE <= '2017-06-20'");
		sqlBuffer.append("GROUP BY YEAR_MONTH)UNION(SELECT substr(FORECAST_DATE + 1 MONTH, 1, 7) AS YEAR_MONTH FROM(SELECT FORECAST_DATE FROM DATAPREDICT_DISTRIBUTION_INFO WHERE ");
		//传参
		sqlBuffer.append("ISSUE_REGION_CODE = '3101'AND FORECAST_DATE > '2017-08-20'AND FORECAST_DATE <= '2018-02-20'AND CREATE_DATE = (SELECT MAX(CREATE_DATE) FROM DATAPREDICT_DISTRIBUTION_INFO)))) s ");
		sqlBuffer.append("LEFT JOIN (SELECT YEAR_MONTH,count(1) AS 历史 FROM(SELECT m.*, (CASE WHEN DAY (m.Petition_Date) > 20 THEN substr((m.Petition_Date + 1 months),1,7)ELSE substr(m.Petition_Date, 1, 7)END) YEAR_MONTH FROM(SELECT d.oid,a.Issue_Region_Name,substr(a.Issue_Region_Code, 1, 4) Issue_Region_Code4,a.Region_Code,a.Petition_Date FROM PETITION_BASIC_INFO a LEFT JOIN Petition_Issue_Info b ON a.Oid = b.Petition_Basic_Info_Oid LEFT JOIN Petition_Deal_Info d ON a.Oid = d.Petition_Basic_Info_Oid WHERE a.Petition_Class_Code = '1' AND d.valid_flag = '0' AND a.ISSUE_REGION_CODE LIKE '31%') m LEFT JOIN Petition_End_Info e ON m.Oid = e.Petition_Deal_Info_Oid)WHERE");
		//传参
		if("3101".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3101'OR ISSUE_REGION_CODE4 ='3113')");
		}
		else if("3106".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3106'OR ISSUE_REGION_CODE4 ='3108')");
		}
		else if("3115".equals(code)){
			sqlBuffer.append("(ISSUE_REGION_CODE4 = '3115'OR ISSUE_REGION_CODE4 ='3119')");
		}else{
			sqlBuffer.append("ISSUE_REGION_CODE4 = '3101'");
		}
		//传参
		sqlBuffer.append("AND PETITION_DATE > '2016-08-20'AND PETITION_DATE <= '2017-06-20'");
		sqlBuffer.append("GROUP BY YEAR_MONTH) l ON l.YEAR_MONTH=s.YEAR_MONTH LEFT JOIN (SELECT substr(FORECAST_DATE + 1 MONTH, 1, 7) AS YEAR_MONTH,NUM 预测 FROM(SELECT FORECAST_NUM AS NUM,ISSUE_REGION_NAME,FORECAST_DATE FROM DATAPREDICT_DISTRIBUTION_INFO WHERE ");
		//传参
		sqlBuffer.append(" ISSUE_REGION_CODE = '3101'AND FORECAST_DATE > '2017-08-20'AND FORECAST_DATE <= '2018-02-20'AND CREATE_DATE = (SELECT MAX(CREATE_DATE)FROM DATAPREDICT_DISTRIBUTION_INFO))) y ON y.YEAR_MONTH=s.YEAR_MONTH");
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
}