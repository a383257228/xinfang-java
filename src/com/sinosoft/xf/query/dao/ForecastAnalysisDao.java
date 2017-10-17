package com.sinosoft.xf.query.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.query.domain.PetitionQuerySave;
import com.sinosoftframework.core.hiberante.HibernateDao;

@Repository
public class ForecastAnalysisDao extends HibernateDao<PetitionQuerySave,String>{

	@Autowired
	JdbcTemplate Jdbctemplate;
	
	public List<Map<String,Object>> queryDealWays() {
		return Jdbctemplate.queryForList("select deal_type_code code,deal_type_name name,count(0) num,substr(petition_date,1,7) times from petition_basic_info group by deal_type_code,deal_type_name,substr(petition_date,1,7)");
	}
}
