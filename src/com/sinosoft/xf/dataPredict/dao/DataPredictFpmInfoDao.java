package com.sinosoft.xf.dataPredict.dao;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.constants.DataPredictFpmInfoConstants;
import com.sinosoft.xf.dataPredict.domain.DataPredictFpmInfo;
import com.sinosoft.xf.dataPredict.domainutil.AlarmVO;
import com.sinosoftframework.core.hiberante.HibernateDao;


/**
 * 关联分析     dao
 * @date 2017-08-12
 * @author liyang
 */
@Repository
public class DataPredictFpmInfoDao extends HibernateDao<DataPredictFpmInfo, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	//连接符
	String separator = DataPredictFpmInfoConstants.SEPARATOR;
	//业务日 20
	int businessDay = DataPredictFpmInfoConstants.BUSINESS_DAY;
	/***
	 * 查询区自办，转办数量
	 * @author lzd
	 * @param map 页面传递参数map
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> dataPredictFPMQW(Map<String, Object> map,int start,int limit,PersonInfo person){
		StringBuffer sb = new StringBuffer();
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		//判断时间维度
		switch(dateFlag){
		 case "BY" : 
			 String timeM = DataPredictFpmInfoConstants.monthTime;
			 sb = dataPredictFPMQWSql(timeM, sb);
			 break; 
		 case "BJD" : 
			 String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
			 sb = dataPredictFPMQWSql(timeQ, sb);
			 break; 
		 case "BN" : 
			 String timeY = DataPredictFpmInfoConstants.yearTime;
			 sb = dataPredictFPMQWSql(timeY, sb);
			 break; 
		}
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 查询区自办，转办数量sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sb StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer dataPredictFPMQWSql(String timeDimension,StringBuffer sb){
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select oid as oid, SELF as SELF,TURN as TURN, ISSUE_REGION_NAME as ISSUEREGIONNAME  from DATAPREDICT_QC_INFO where ISSUE_REGION_FLAG = '2' and PETITION_DATE_FLAG = '" + timeDimension + "'");
		return sb;
	}
	/***
	 * 查询派驻自办，转办数量
	 * @author lzd
	 * @param map 页面传递参数map
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> dataPredictFPMS(Map<String, Object> map,int start,int limit,PersonInfo person){
		StringBuffer sb = new StringBuffer();
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		//判断时间维度
		switch(dateFlag){
		 case "BY" : 
			 String timeM = DataPredictFpmInfoConstants.monthTime;
			 sb = dataPredictFPMSSql(timeM, sb); 
			 break; 
		 case "BJD" : 
			 String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
			 sb = dataPredictFPMSSql(timeQ, sb);
			 break; 
		 case "BN" : 
			 String timeY = DataPredictFpmInfoConstants.yearTime;
			 sb = dataPredictFPMSSql(timeY, sb);
			 break; 
		}
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 查询派驻自办，转办数量sql语句
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sb StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer dataPredictFPMSSql(String timeDimension,StringBuffer sb){
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select oid as oid, SELF as SELF,TURN as TURN, ISSUE_REGION_NAME as ISSUEREGIONNAME  from DATAPREDICT_QC_INFO where ISSUE_REGION_FLAG = '3' and PETITION_DATE_FLAG = '" + timeDimension + "'");
		return sb;
	}
	
	/**
	 * 通过传的按钮名称拼接查询的日期字符串
	 * @author lzd
	 * @date 2017-08-18
	 * @param dateFlag 日期
	 * @param startDateValue 日期查询的按钮标识开始时间
	 * @param endDateValue 日期查询的按钮标识结束时间
	 * @return String[] 
	 * @throws ParseException 
	 */
	public String[] queryStartdateAndEnddate(String dateFlag,String startDateValue,String endDateValue) throws ParseException{
		String[] dateValue = new String[2];
		String startDate = "";
		String endDate = "";
		if(dateFlag.equals("BY")){//选中本月   拼接上月的20号到今天，如果今天超过20号 则取到20号
			DateTime date = new DateTime();
			if(date.getMonthOfYear() - 1 != 0){//判断是否是一月份
				if(date.getDayOfMonth() <= businessDay){ //判断当前日期是否超过20号
					startDate = date.getYear() + separator + (date.getMonthOfYear() - 1) + separator + businessDay;
				} else {
					startDate = date.getYear() + separator + date.getMonthOfYear() + separator + businessDay;
				}
				endDate = date.getYear() + separator + date.getMonthOfYear() + separator + date.getDayOfMonth();
			} else {
				if(date.getDayOfMonth() <= businessDay){
					startDate = (date.getYear() - 1) + "-12-" + businessDay;
				} else {
					startDate = date.getYear() + separator + date.getMonthOfYear() + separator + businessDay;
				}
				endDate = date.getYear() + separator + date.getMonthOfYear() + separator + date.getDayOfMonth();
			}
			
		}
		if(dateFlag.equals("BJD")){//选中本季度    
			StringBuffer start = new StringBuffer();
			StringBuffer end = new StringBuffer();
			DateTime date = new DateTime();
			//获取当前日期
			end = end.append(date.getYear()).append(separator).append(date.getMonthOfYear()).append(separator).append(date.getDayOfMonth());
			//判断当前日期开始的季度
			if(date.getMonthOfYear() == 1 && date.getMonthOfYear() == 2 && date.getMonthOfYear() == 12){
				start = start.append(date.getYear() - 1).append("-12-").append(businessDay);
			}else if(date.getMonthOfYear() >= 3 && 5 >= date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-3-").append(businessDay); 
			}else if(date.getMonthOfYear() >=6 && 8 >= date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-6-").append(businessDay); 
			}else if(date.getMonthOfYear() >= 9 && 11 >= date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-9-").append(businessDay); 
			}
			startDate = start.toString();//转字符串
			endDate = end.toString();//转字符串
		}
		if(dateFlag.equals("BN")){//选中本年  
			DateTime date = new DateTime();
			//判断当月是否是12月
			if(date.getMonthOfYear() == 12){
				//判断当日是否小于等于业务日
				if(date.getDayOfMonth() <= businessDay){
					startDate = (date.getYear() - 1) + "-12-" + businessDay;
					endDate = date.getYear() + separator + date.getMonthOfYear() + separator + date.getDayOfMonth();
				} else {
					startDate = date.getYear() + separator + date.getMonthOfYear() + separator + businessDay;
					endDate = date.getYear() + separator + date.getMonthOfYear() + separator + date.getDayOfMonth();
				}
			} else {
				startDate = (date.getYear() - 1) + "-12-" + businessDay;
				endDate = date.getYear() + separator + date.getMonthOfYear() + separator + date.getDayOfMonth();
			}
		}
		dateValue[0] = startDate;
		dateValue[1] = endDate;
		return dateValue;
	}
	
	/***
	 * 告警
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> dataPredictAlarm(Map<String, Object> map){
		StringBuffer sqlBuffer = new StringBuffer();
		AlarmVO vo = (AlarmVO) map.get("vo");
//		Date dealTime = vo.getDealTime();//信件了结的时间
		String issueTypeCode = vo.getIssueTypeCode();//问题类别code
	    sqlBuffer.append("select oid,DEAL_TYPE_NAME,support from DATAPREDICT_FPM_QD_INFO as a where  support=(select max(b.support) from DATAPREDICT_FPM_QD_INFO as b where b.ISSUE_TYPE_CODE = '");
	    sqlBuffer.append(issueTypeCode).append("'").append(" and b.REGIN_NAME = '").append(vo.getRegionName()).append("'").append(")");
	    //System.out.println(sqlBuffer.toString());
	    return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/***
	 * 处理完成的信件插入到数据库（告警页面数据）
	 * @author lzd
	 * @param map
	 * @return
	 */
	public void dataPredictInsertAlarm(Map<String, Object> map){
		StringBuffer sqlBuffer = new StringBuffer();
		AlarmVO vo = (AlarmVO) map.get("vo");
		String dealTypeName = vo.getDealTypeName();//获取处理方式name
		String dealTypeCode = vo.getDealTypeCode();//获取处理方式code
		String currPetitionNo  = vo.getCurrPetitionNo();//给客户显示的信访编号
		String accuserName  = vo.getAccuserName();//反映人
		String pno = vo.getPetitionNo();//开发用的信访编号
		String oid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,16);
		//String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String accuseds = vo.getAccuseds();//获取被反映人
		String issueContent = vo.getIssueContent();//问题描述
		String issueTypeName = vo.getIssueTypeName();//问题类别name
		String issueTypeCode = vo.getIssueTypeCode();//问题类别code
		String sDealType = vo.getsDealType();//应处理方式
		String operatingArea = vo.getOperatingArea();//操作区
		int dealWay = vo.getDealWay();//分类 1代表处理方式相同。0代表处理方式不同
		Timestamp dealTime = vo.getDealTime();//信件了结的时间
		String regionName = vo.getRegionName();//区域name
		String regionCode = vo.getRegionCode();//区域code
		Timestamp createDate = vo.getCreateDate();//创建时间
		String creatorId = vo.getCreatorId();//创建者id
		String creatorName = vo.getCreatorName();//创建者R
		Timestamp modifyDate = vo.getModifyDate();//修改时间
		String modifyId = vo.getModifyId();//修改者id
		String modifyName = vo.getModifyName();//修改者
	    sqlBuffer.append("insert into DATAPREDICT_FPM_ALARM_INFO(OID,PETITION_NO,ISSUE_TYPE_CODE,ISSUE_TYPE_NAME,DEAL_TYPE_CODE,DEAL_TYPE_NAME,DEAL_WAY,DEAL_TIME,ACCUSEDS,ISSUE_CONTENT,S_DEAL_TYPE,OPERATING_AREA,CREATE_DATE,CREATOR_NAME,CREATOR_ID,MODIFY_DATE,MODIFY_NAME,MODIFY_ID,REGION_NAME,REGION_CODE,CURR_PETITION_NO,ACCUSER_NAME)");
	    sqlBuffer.append("VALUES(").append("'").append(oid).append("','").append(pno).append("','").append(issueTypeCode).append("','").append(issueTypeName).append("','").append(dealTypeCode).append("','").append(dealTypeName).append("','").append(dealWay).append("','").append(dealTime).append("','").append(accuseds).append("','").append(issueContent).append("','").append(sDealType).append("','").append(operatingArea).append("','").append(createDate).append("','").append(creatorName).append("','").append(creatorId).append("','").append(modifyDate).append("','").append(modifyName).append("','").append(modifyId).append("','").append(regionName).append("','").append(regionCode).append("'");
	    sqlBuffer.append(",'").append(currPetitionNo).append("','").append(accuserName).append("'");
	    sqlBuffer.append(")");
//	    System.out.println(sqlBuffer.toString());
	    try {
	    	jdbcTemplate.update(sqlBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	
	/***
	 * 上海市告警
	 * @author lzd
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> dataPredictFindAlarm(Map<String, Object> map,int start,int limit,PersonInfo person) throws ParseException{
		StringBuffer sb = new StringBuffer();
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select oid as OID,CURR_PETITION_NO as CURRPETITIONNO, ACCUSER_NAME as ACCUSERNAME,ISSUE_TYPE_NAME AS ISSUETYPENAME, ACCUSEDS AS ACCUSEDS ,ISSUE_CONTENT AS ISSUECONTENT,DEAL_TYPE_NAME AS DEALTYPENAME,S_DEAL_TYPE AS SDEALTYPE,OPERATING_AREA AS OPERATINGAREA,PETITION_NO AS PETITIONNO,REGION_CODE AS REGIONCODE,ACCUSEDS AS ACCUSEDS  from DATAPREDICT_FPM_ALARM_INFO where DEAL_WAY = 0 and ");
		
	    String[] dateValue = new String[2];
		try {
			dateValue = queryStartdateAndEnddate(map.get("dateFlag").toString(),map.get("startDateValue").toString(),map.get("endDateValue").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sb.append("  DEAL_TIME>='").append(dateValue[0]).append("'");
		sb.append("  and DEAL_TIME<='").append(dateValue[1]).append("'");	
		sb.append(" and  REGION_NAME='").append("上海市").append("'");	
		
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForList(sb.toString());
	}
	/**
	 * 上海市count条数用于分页（告警表）
	 * @author lzd
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public int getCityInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		 StringBuffer sb = new StringBuffer("select count(*) from DATAPREDICT_FPM_ALARM_INFO where DEAL_WAY = 0 and");
		 String[] dateValue = new String[2];
			try {
				dateValue = queryStartdateAndEnddate(map.get("dateFlag").toString(),map.get("startDateValue").toString(),map.get("endDateValue").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		sb.append("   DEAL_TIME>='").append(dateValue[0]).append("'");
		sb.append(" and  DEAL_TIME<='").append(dateValue[1]).append("'");	
		sb.append(" and  REGION_NAME='").append("上海市").append("'");	
		
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 区，派驻count条数用于分页（告警表）
	 * @author lzd
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public int getBasicInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		 StringBuffer sb = new StringBuffer("select count(*) from DATAPREDICT_FPM_ALARM_INFO where DEAL_WAY = 0 and");
		 String reName = map.get("reName").toString();
		 String[] dateValue = new String[2];
			try {
				dateValue = queryStartdateAndEnddate(map.get("dateFlag").toString(),map.get("startDateValue").toString(),map.get("endDateValue").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		sb.append("   DEAL_TIME>='").append(dateValue[0]).append("'");
		sb.append(" and  DEAL_TIME<='").append(dateValue[1]).append("'");	
		sb.append(" and  REGION_NAME='").append(reName).append("'");	
		
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 派驻count条数用于分页（table表格）
	 * @author lzd
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public int getpaizhuInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		StringBuffer sb = new StringBuffer();
		switch(dateFlag){ //判断时间维度
		 case "BY" : 
			 String timeM = DataPredictFpmInfoConstants.monthTime;
			 sb = getpaizhuInfoSizeSql(timeM, sb);	//派驻count条数sql	
			 break; 
		 case "BJD" : 
			 String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
			 sb = getpaizhuInfoSizeSql(timeQ, sb); //派驻count条数sql
			 break; 
		 case "BN" : 
			 String timeY = DataPredictFpmInfoConstants.yearTime;
			 sb = getpaizhuInfoSizeSql(timeY, sb); //派驻count条数sql
			 break; 
		}
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 派驻count条数sql语句
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sb StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer getpaizhuInfoSizeSql(String timeDimension,StringBuffer sb){
		sb.append("select count(*) from DATAPREDICT_QC_INFO where ISSUE_REGION_FLAG = '3' ");
		sb.append(" and PETITION_DATE_FLAG = '").append(timeDimension).append("'");	
		return sb;
	}
	/**
	 * 区count条数用于分页
	 * @author lzd
	 * @param map
	 * @param person
	 * @return
	 * @throws ParseException
	 */
	public int getquInfoSize(Map<String, Object> map,PersonInfo person) throws ParseException {
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		StringBuffer sb = new StringBuffer();
		switch(dateFlag){
		 case "BY" : 
			 String timeM = DataPredictFpmInfoConstants.monthTime;
			 sb = getquInfoSizeSql(timeM, sb);	//区count条数sql
			 break; 
		 case "BJD" : 
			 String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
			 sb = getquInfoSizeSql(timeQ, sb);	//区count条数sql	
			 break; 
		 case "BN" : 
			 String timeY = DataPredictFpmInfoConstants.yearTime;
			 sb = getquInfoSizeSql(timeY, sb);	//区count条数sql	
			 break; 
		}
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForInt(sb.toString());
	}
	/**
	 * 区count条数sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sb  StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer getquInfoSizeSql(String timeDimension,StringBuffer sb){
		sb.append("select count(*) from DATAPREDICT_QC_INFO where ISSUE_REGION_FLAG = '2' ");
		sb.append(" and PETITION_DATE_FLAG = '").append(timeDimension).append("'");
		return sb;
	}
	/***
	 * 区，派驻告警
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> dataPredictFindAlarmOther(Map<String, Object> map,int start,int limit,PersonInfo person){
		
		StringBuffer sb = new StringBuffer();
		String reName = map.get("reName").toString();//区域名字
		sb.append("select  * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sb.append("select oid as OID,CURR_PETITION_NO as CURRPETITIONNO, ACCUSER_NAME as ACCUSERNAME,ISSUE_TYPE_NAME AS ISSUETYPENAME, ACCUSEDS AS ACCUSEDS,ISSUE_CONTENT AS ISSUECONTENT,DEAL_TYPE_NAME AS DEALTYPENAME,S_DEAL_TYPE AS SDEALTYPE,OPERATING_AREA AS OPERATINGAREA,PETITION_NO AS PETITIONNO,REGION_CODE AS REGIONCODE,ACCUSEDS AS ACCUSEDS  from DATAPREDICT_FPM_ALARM_INFO where DEAL_WAY = 0 and ");
		
	    String[] dateValue = new String[2];
		try {
			dateValue = queryStartdateAndEnddate(map.get("dateFlag").toString(),map.get("startDateValue").toString(),map.get("endDateValue").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sb.append("  DEAL_TIME>='").append(dateValue[0]).append("'");
		sb.append("  and DEAL_TIME<='").append(dateValue[1]).append("'");	
		sb.append(" and  REGION_NAME='").append(reName).append("'");	
		
		sb.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForList(sb.toString());
		
	}
	
	/***
	 * 行政级别个数
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findOBCount(Map<String, Object> map){
		StringBuffer sqlBuffer = new StringBuffer();
		String swFlag = map.get("swFlag").toString();//市，区派驻
		String regName = map.get("regName").toString();//获取当前选中的区域
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		// 时间维度标识符
		String timeM = DataPredictFpmInfoConstants.monthTime; 
		String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
		String timeY = DataPredictFpmInfoConstants.yearTime;
		if(swFlag.equals("qs")){ 
			
				//判断时间维度
				switch(dateFlag){
				 case "BY" : 
					 sqlBuffer = findOBCountQuSql(timeM, sqlBuffer, regName);
					 break; 
				 case "BJD" : 
					 sqlBuffer = findOBCountQuSql(timeQ, sqlBuffer, regName);
					 break; 
				 case "BN" : 
					 sqlBuffer = findOBCountQuSql(timeY, sqlBuffer, regName);
					 break; 
				}
//				System.out.println(sqlBuffer.toString());
				return jdbcTemplate.queryForList(sqlBuffer.toString());
		} else {
			//判断时间维度
			switch(dateFlag){
			 case "BY" : 
				 sqlBuffer = findOBCountShanghaiSql(timeM, sqlBuffer);
				 break; 
			 case "BJD" : 
				 sqlBuffer = findOBCountShanghaiSql(timeQ, sqlBuffer);
				 break; 
			 case "BN" : 
				 sqlBuffer = findOBCountShanghaiSql(timeY, sqlBuffer);
				 break; 
			}
//			System.out.println(sqlBuffer.toString());
			return jdbcTemplate.queryForList(sqlBuffer.toString());
		}
		
	}
	/**
	 * 区sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sqlBuffer  StringBuffer
	 * @param qu 区
	 * @return StringBuffer
	 */
	public StringBuffer findOBCountQuSql(String timeDimension, StringBuffer sqlBuffer, String regName){
		sqlBuffer.append("select count(distinct OBJECT_CLASS_CODE) as cou,count(distinct ISSUE_TYPE_CODE) as isscou from DATAPREDICT_FPM_QL_ALL_DATA_INFO");
		sqlBuffer.append(" where REGIN_NAME = '" + regName + "' and PETITION_DATE_FLAG = '" + timeDimension + "'"); //FETCH FIRST 1 ROWS ONLY
		return sqlBuffer;
	}
	/**
	 * 派驻sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sqlBuffer StringBuffer
	 * @param paizhu 派驻
	 * @return StringBuffer
	 */ 
	public StringBuffer findOBCountPaizhuSql(String timeDimension, StringBuffer sqlBuffer, String paizhu){
		sqlBuffer.append("select count(distinct OBJECT_CLASS_CODE) as cou,count(distinct ISSUE_TYPE_CODE) as isscou from DATAPREDICT_FPM_QL_ALL_DATA_INFO");
		sqlBuffer.append(" where REGIN_NAME = '" + paizhu + "' and PETITION_DATE_FLAG = '" + timeDimension + "'"); //FETCH FIRST 1 ROWS ONLY
		return sqlBuffer;
	}
	/**
	 * 上海市sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sqlBuffer StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer findOBCountShanghaiSql(String timeDimension, StringBuffer sqlBuffer){
		sqlBuffer.append("select count(distinct OBJECT_CLASS_CODE) as cou,count(distinct ISSUE_TYPE_CODE) as isscou from DATAPREDICT_FPM_QL_ALL_DATA_INFO");
		sqlBuffer.append(" where REGIN_NAME = '上海市' and PETITION_DATE_FLAG = '" + timeDimension + "'"); //FETCH FIRST 1 ROWS ONLY
		return sqlBuffer;
	}
	
	
	/**
	 * 动态获取regName
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getNameQuAndPaizhu(Map<String, Object> map){
		String swFlag = map.get("swFlag").toString();//市，区派驻
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		StringBuffer sb = new StringBuffer();
		//判断是否是本委还是全市 
		if(swFlag.equals("qs")){ 
			//判断时间维度
			switch(dateFlag){
			case "BY" :
				sb.append("SELECT DISTINCT REGIN_NAME from DATAPREDICT_FPM_QL_ALL_DATA_INFO where PETITION_DATE_FLAG = 'm'  and REGIN_NAME != '上海市'");
				break;
			case "BJD" : 
				sb.append("SELECT DISTINCT REGIN_NAME from DATAPREDICT_FPM_QL_ALL_DATA_INFO where PETITION_DATE_FLAG = 'q'  and REGIN_NAME != '上海市'");
				break;
			case "BN" : 
				sb.append("SELECT DISTINCT REGIN_NAME from DATAPREDICT_FPM_QL_ALL_DATA_INFO where PETITION_DATE_FLAG = 'y'  and REGIN_NAME != '上海市'");
				break;
			}
		} 
//		System.out.println(sb.toString());
		return jdbcTemplate.queryForList(sb.toString());
	}
	/***
	 * 查找问题类别与行政级别关联关系数据
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findDataQL(Map<String, Object> map){
		String swFlag = map.get("swFlag").toString();//市，区派驻
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		int obCodeSum = (int) map.get("obCodeSum");//行政级别个数
		String regName = map.get("regName").toString();//获取当前选中的区域
		String frequency =  map.get("frequency").toString();//web页面分页数
		int pagS = Integer.valueOf(frequency).intValue();
		int start = 0;
		int end = obCodeSum * 10;
		//判断是否是本委还是全市 
		if(swFlag.equals("qs")){ 
		
				StringBuffer sb = new StringBuffer();
				//判断时间维度
				switch(dateFlag){
				 case "BY" : 
					 if(pagS == 0){
						 sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
						 sb.append("where PETITION_DATE_FLAG = 'm' and REGIN_NAME = '"+ regName +"' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
						 break; 
					 } else {
						 sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
						 sb.append("where PETITION_DATE_FLAG = 'm' and REGIN_NAME = '"+ regName +"' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
						 break; 
					 }
				 case "BJD" : 
					 if(pagS == 0){
						 sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
						 sb.append("where PETITION_DATE_FLAG = 'q' and REGIN_NAME = '"+ regName +"' )  where  ROWNUM >= " + start + " and ROWNUM <= " + end*(1+pagS) + "");
						 break; 
					 } else {
						 sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
						 sb.append("where PETITION_DATE_FLAG = 'q' and REGIN_NAME = '"+ regName +"' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
						 break; 
					 }
				 case "BN" : 
					 if(pagS == 0){
						 sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
						 sb.append("where PETITION_DATE_FLAG = 'y' and REGIN_NAME = '"+ regName +"' )  where  ROWNUM >= " + start + " and ROWNUM <= " + end*(1+pagS) + "");
						 break; 
					 } else {
						 sb.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
						 sb.append("where PETITION_DATE_FLAG = 'y' and REGIN_NAME = '"+ regName +"' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
						 break; 
					 }
				}
//				System.out.println(sb.toString());
				return jdbcTemplate.queryForList(sb.toString());
	
		}
		else { //上海市
			StringBuffer sqlBuffer = new StringBuffer();
			//判断时间维度
			switch(dateFlag){
			 case "BY" : 
				 if(pagS == 0){
					 sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
					 sqlBuffer.append("where PETITION_DATE_FLAG = 'm' and REGIN_NAME = '上海市' )  where  ROWNUM >= " + start + " and ROWNUM <= " + end*(1+pagS) + "");
					 break; 
				 } else {
					 sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
					 sqlBuffer.append("where PETITION_DATE_FLAG = 'm' and REGIN_NAME = '上海市' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
					 break; 
				 }
			 case "BJD" : 
				 if(pagS == 0){
					 sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
					 sqlBuffer.append("where PETITION_DATE_FLAG = 'q' and REGIN_NAME = '上海市' )  where  ROWNUM >= " + start + " and ROWNUM <= " + end*(1+pagS) + "");
					 break; 
				 } else {
					 sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
					 sqlBuffer.append("where PETITION_DATE_FLAG = 'q' and REGIN_NAME = '上海市' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
					 break; 
				 }
			 case "BN" : 
				 if(pagS == 0){
					 sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
					 sqlBuffer.append("where PETITION_DATE_FLAG = 'y' and REGIN_NAME = '上海市' )  where  ROWNUM >= " + start + " and ROWNUM <= " + end*(1+pagS) + "");
					 break; 
				 } else {
					 sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME DESC) AS ROWNUM, ISSUE_TYPE_NAME ,OBJECT_CLASS_NAME,OBJECT_CLASS_CODE,SUPPORT from DATAPREDICT_FPM_QL_ALL_DATA_INFO ");
					 sqlBuffer.append("where PETITION_DATE_FLAG = 'y' and REGIN_NAME = '上海市' )  where  ROWNUM >= " + (end*pagS+1) + " and ROWNUM <= " + (end*(1+pagS)) + "");
					 break; 
				 }
			}
//			System.out.println(sqlBuffer.toString());
			return jdbcTemplate.queryForList(sqlBuffer.toString());
		}
	}
	/***
	 * 根据问题1，问题2 去补全的数据表中查找对应的support
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> completion(Map<String, Object> map){
		String swFlag = map.get("swFlag").toString();//
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		StringBuffer sqlBuffer = new StringBuffer();
		String issueTypeName1 = map.get("issueTypeName1").toString();//获取问题1
		String issueTypeName2 = map.get("issueTypeName2").toString();//获取问题2
		// 时间维度标识符
		String timeM = DataPredictFpmInfoConstants.monthTime; 
		String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
		String timeY = DataPredictFpmInfoConstants.yearTime;
		String flagNameQW = "qw";//区，派驻标识
		String flagNameS = "s";//上海市标识
		if(swFlag.equals("qs")){ 
			switch(dateFlag){
			 case "BY" : 
				 sqlBuffer = completionSql(timeM, sqlBuffer, flagNameQW, issueTypeName1, issueTypeName2);
				 break; 
			 case "BJD" : 
				 sqlBuffer = completionSql(timeQ, sqlBuffer, flagNameQW, issueTypeName1, issueTypeName2);
				 break; 
			 case "BN" : 
				 sqlBuffer = completionSql(timeY, sqlBuffer, flagNameQW, issueTypeName1, issueTypeName2);
				 break; 
			}
		} else {
			switch(dateFlag){
			 case "BY" : 
				 sqlBuffer = completionSql(timeM, sqlBuffer, flagNameS, issueTypeName1, issueTypeName2);
				 break; 
			 case "BJD" : 
				 sqlBuffer = completionSql(timeQ, sqlBuffer, flagNameS, issueTypeName1, issueTypeName2);
				 break; 
			 case "BN" : 
				 sqlBuffer = completionSql(timeY, sqlBuffer, flagNameS, issueTypeName1, issueTypeName2);
				 break; 
			}
		}
//		System.out.println(sqlBuffer.toString());
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 根据问题1，问题2 去补全的数据表中查找对应的support sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sqlBuffer StringBuffer
	 * @param flagName 地区标识符
	 * @param issueTypeName1  问题类别1
	 * @param issueTypeName2  问题类别2
	 * @return StringBuffer
	 */
	public StringBuffer completionSql(String timeDimension, StringBuffer sqlBuffer, String flagName, String issueTypeName1, String issueTypeName2){
		sqlBuffer.append("select SUPPORT from DATAPREDICT_TOP5_INFO");
		sqlBuffer.append(" where QWS = '" + flagName + "'  and PETITION_DATE_FLAG = '" + timeDimension + "' and ISSUE_TYPE_NAME_1 = '" + issueTypeName1 + "' and ISSUE_TYPE_NAME_2 = '" + issueTypeName2 + "'");
		return sqlBuffer;
	}
	/**
	 * 查看TOP5
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findDataTOP(Map<String, Object> map){
		String swFlag = map.get("swFlag").toString();//
		String dateFlag = map.get("dateFlag").toString();//获取时间纬度
		StringBuffer sqlBuffer = new StringBuffer();
		// 时间维度标识符
		String timeM = DataPredictFpmInfoConstants.monthTime; 
		String timeQ = DataPredictFpmInfoConstants.quarterlyTime;
		String timeY = DataPredictFpmInfoConstants.yearTime;
		String flagNameQW = "qw";//区，派驻标识
		String flagNameS = "s";//上海市标识
		if(swFlag.equals("qs")){  //判断全市还是本委
			switch(dateFlag){  //判断时间维度
			 case "BY" : 
				 sqlBuffer = findDataTOPSql(timeM, sqlBuffer, flagNameQW);
				 break; 
			 case "BJD" : 
				 sqlBuffer = findDataTOPSql(timeQ, sqlBuffer, flagNameQW);
				 break; 
			 case "BN" : 
				 sqlBuffer = findDataTOPSql(timeY, sqlBuffer, flagNameQW);
				 break; 
			}
		} else {
			switch(dateFlag){
			 case "BY" : 
				 sqlBuffer = findDataTOPSql(timeM, sqlBuffer, flagNameS);
				 break; 
			 case "BJD" : 
				 sqlBuffer = findDataTOPSql(timeQ, sqlBuffer, flagNameS);
				 break; 
			 case "BN" : 
				 sqlBuffer = findDataTOPSql(timeY, sqlBuffer, flagNameS);
				 break; 
			}
		}
//		System.out.println(sqlBuffer.toString());
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	/**
	 * 查看TOP5 sql
	 * @author lzd
	 * @param timeDimension 时间维度
	 * @param sqlBuffer StringBuffer
	 * @param flagName 地区标识
	 * @return StringBuffer
	 */
	public StringBuffer findDataTOPSql(String timeDimension, StringBuffer sqlBuffer, String flagName){
		sqlBuffer.append("select ISSUE_TYPE_NAME_1,ISSUE_TYPE_CODE_1, SUPPORT from DATAPREDICT_TOP5_INFO");
		sqlBuffer.append(" where QWS = '" + flagName + "'  and PETITION_DATE_FLAG = '" + timeDimension + "' and ISSUE_TYPE_CODE_2 = '' ORDER BY  SUPPORT DESC  fetch first 5 rows only");
		return sqlBuffer;
	}
	/***
	 * 根据oid查找信访件的详细信息
	 * @author lzd
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> fpmFindInfo(Map<String, Object> map){
		StringBuffer sqlBuffer = new StringBuffer();
		String oid = map.get("OID").toString();//获取信访件oid
		sqlBuffer.append("select * from (select ROW_NUMBER() OVER(ORDER BY oid DESC) AS ROWNUM,oid as OID,ISSUE_TYPE_NAME AS ISSUETYPENAME, ACCUSEDS AS ACCUSEDS,ISSUE_CONTENT AS ISSUECONTENT,DEAL_TYPE_NAME AS DEALTYPENAME,S_DEAL_TYPE AS SDEALTYPE,OPERATING_AREA AS OPERATINGAREA from DATAPREDICT_FPM_ALARM_INFO where DEAL_WAY = ");
	    sqlBuffer.append(0);//.append(" and DEAL_TIME = '").append("2017-08-20")
	    String[] dateValue = new String[2];
		try {
			dateValue = queryStartdateAndEnddate(map.get("dateFlag").toString(),map.get("startDateValue").toString(),map.get("endDateValue").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String page = map.get("page").toString();//首页
		String rows = map.get("rows").toString();//尾页
		sqlBuffer.append(" and  DEAL_TIME>='").append(dateValue[0]).append("'");
		sqlBuffer.append(" and  DEAL_TIME<='").append(dateValue[1]).append("'");
		sqlBuffer.append(" and  OID = '").append(oid).append("'");
		sqlBuffer.append(" )");
		sqlBuffer.append(" where ROWNUM >= " + page + " and ROWNUM <= " + rows);
		
		return jdbcTemplate.queryForList(sqlBuffer.toString());
	}
	
	/**
	 * 更新未操作字段
	 * @author lzd 
	 * @date 2017-08-30
	 * @throws Exception
	 */
	public void updateUnconfirmed(String oids) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE DATAPREDICT_FPM_ALARM_INFO SET (OPERATING_AREA) = ('1') where ");
		sb.append(" OID = '" + oids + "' " );
		String sql = sb.toString();
		//System.out.println(sql);
		jdbcTemplate.update(sql);
	}
	
	
}