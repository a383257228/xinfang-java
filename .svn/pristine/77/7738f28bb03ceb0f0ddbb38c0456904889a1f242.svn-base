package com.sinosoft.xf.petition.petitionaccept.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.petition.petitionaccept.domain.RepeatRuleInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 重复件规则设置dao
 * @author ljx 
 * @createDate 2011-09-21
 */
@Repository
public class RepeatRuleDao extends HibernateDao<RepeatRuleInfo, String> {
	
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * 修改重复规则的，有效记录，为无效记录
	 * @return void
	 */
	public void updateAllRepeat(String timeDate){
		String sql = "update REPEAT_RULE_INFO set valid_Flag = '0',valid_end_date = '"+timeDate+"'  where valid_Flag = '1' and region_code ='"+Struts2Utils.getSession().getAttribute("curRegionCode").toString()+"'";
		jdbcTemplate.update(sql);
	}
}
