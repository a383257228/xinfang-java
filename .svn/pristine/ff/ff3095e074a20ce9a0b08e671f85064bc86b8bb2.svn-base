package com.sinosoft.xf.query.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.query.dao.ForecastAnalysisDao;
import com.sinosoft.xf.query.domain.PetitionQuerySave;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

@Service
@Transactional
public class ForecastAnalysisManager extends EntityManager<PetitionQuerySave,String>{

	@Autowired
	ForecastAnalysisDao forecastAnalysisDao;
	public String queryDealWays() {
		StringBuffer json = new StringBuffer("[");
		List<Map<String,Object>> list = forecastAnalysisDao.queryDealWays();
		int i = 0;
		for (Map<String, Object> map : list) {
			String typeName = "";
			if(map.get("NAME")!=null && !"".equals(map.get("NAME"))){
				typeName = map.get("NAME").toString();
			}
			if(!"".equals(typeName)){
				String times = "";
				if(map.get("TIMES")!=null && !"".equals(map.get("TIMES"))){
					times = map.get("TIMES").toString();
				}
				int num = 0;
				if(map.get("NUM")!=null && !"".equals(map.get("NUM"))){
					num = Integer.parseInt(map.get("NUM").toString());
				}
				if(i!=0){
					json.append(",{");
				}else{
					json.append("{");
				}
				json.append("\"typeName\":\"");
				json.append(typeName);
				json.append("\",");
				json.append("\"times\":\"");
				json.append(times);
				json.append("\",");
				json.append("\"num\":\"");
				json.append(num);
				json.append("\"}");
				i++;
			}
		}
		json.append("]");
		return json.toString();
	}
	@Override
	protected HibernateDao<PetitionQuerySave, String> getEntityDao() {
		// TODO Auto-generated method stub
		return forecastAnalysisDao;
	}

}
