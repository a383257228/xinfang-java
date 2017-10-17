package com.sinosoft.xf.dataPredict.manager;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.dao.DataPredictDistributionInfoDao;
import com.sinosoft.xf.dataPredict.domain.DataPredictDistributionInfo;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

/**
 * 信访件分布  manager
 * @date 2017-08-12
 * @author liyang
 */
@Service
@Transactional
public class DataPredictDistributionInfoManager extends EntityManager<DataPredictDistributionInfo, String> {
	@Autowired
	DataPredictDistributionInfoDao distributionInfoDao;

	@Override
	protected HibernateDao<DataPredictDistributionInfo, String> getEntityDao() {
		return distributionInfoDao;
	}
	
	public String dataPredict(String swFlag,String dateFlag,String startDateValue,String endDateValue,PersonInfo person) throws JsonGenerationException, JsonMappingException, IOException{		
		String[] date=dateHistory();
		List<Map<String, Object>> list = distributionInfoDao.dataPredict(swFlag,dateFlag,startDateValue,endDateValue,person,date);		
		ObjectMapperWrap objectMapperWrap=new ObjectMapperWrap();
		String json=objectMapperWrap.writeValueAsString(list);
		return json;
	}
	public String dataCityOne(String swFlag,String dateFlag,String startDateValue,String endDateValue,PersonInfo person)throws JsonGenerationException, JsonMappingException, IOException{
		String[] date=dateHistory();
		List<Map<String, Object>> list = distributionInfoDao.dataCityOne(swFlag,dateFlag,startDateValue,endDateValue,person,date);		
		ObjectMapperWrap objectMapperWrap=new ObjectMapperWrap();
		String json=objectMapperWrap.writeValueAsString(list);
		return json;
	}

	public String trend(String code,PersonInfo person) throws JsonGenerationException, JsonMappingException, IOException{
		String[] date=dateHistory();
		//List<Map<String, Object>> list1 = distributionInfoDao.date(code,person,date);
		List<Map<String, Object>> t=distributionInfoDao.t(code, person, date);
		ObjectMapperWrap objectMapperWrap1=new ObjectMapperWrap();
		String json=objectMapperWrap1.writeValueAsString(t);
		return json;
	}
	
	public String year(String code,PersonInfo person) throws JsonGenerationException, JsonMappingException, IOException{
		String[] date=dateHistory();
		List<Map<String, Object>> t=distributionInfoDao.year(code, person, date);
		ObjectMapperWrap objectMapperWrap1=new ObjectMapperWrap();
		String json=objectMapperWrap1.writeValueAsString(t);
		return json;
	}
	/**
	 * dateSelect[0] 当前业务月
	 * dateSelect[1] 当前业务月之后六个月
	 * dateSelect[2] 当前业务月之前六个月
	 * dateSelect[3] 历史数据的起始月
	 * dateSelect[4] 历史数据的结束月
	 * @return
	 */
	public String[] dateHistory(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-21");
		Date date;
		String[] dateSelect = new String[4];		
		try {
			date = sdf.parse(date());
			Calendar calendar = Calendar.getInstance();    
	        calendar.setTime(date);    
	        dateSelect[0]=sdf.format(date.getTime());
	        calendar.add(Calendar.MONTH, +6);//当前时间加一个月
	        dateSelect[1]=sdf.format(calendar.getTime());
	        calendar.add(Calendar.MONTH, -12);//当前时间加一个月
	        dateSelect[2]=sdf.format(calendar.getTime());
	        calendar.add(Calendar.YEAR, -1);//当前时间加一个月
	        dateSelect[3]=sdf.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//获取当前业务月
       		
		return dateSelect;
	}
	
	public String date(){
		SimpleDateFormat ym_20 = new SimpleDateFormat("yyyy-MM-21");//设置日期格式
		SimpleDateFormat ym_dd = new SimpleDateFormat("yyyy-MM-dd");
		String day = ym_dd.format(new Date());
		String month = ym_20.format(new Date());
		String date= compare_date(day, month);
		return date;
	}
	/**
	 * 业务月时间处理
	 * shil
	 * @param DATE1 当前系统时间
	 * @param DATE2 本月20号
	 * @return 小于本月21号返回本月20号，大于等于本月21号返回下个月20号（2017-8-19 返回2017-7-20,2017-8-21返回2017-9-20）
	 */
	public String compare_date(String DATE1, String DATE2) {
        String date_20=null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-21");//设置日期格式 
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() < dt2.getTime()) {
            	Date date = sdf.parse(sdf.format(new Date()));//获取当前业务月
                Calendar calendar = Calendar.getInstance();    
                calendar.setTime(date);    
                calendar.add(Calendar.MONTH, -1);
                date_20=sdf.format(calendar.getTime());
            } else if (dt1.getTime() > dt2.getTime()||dt1.getTime() == dt2.getTime()) {
            	date_20=sdf.format(new Date());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return date_20;
    }
}