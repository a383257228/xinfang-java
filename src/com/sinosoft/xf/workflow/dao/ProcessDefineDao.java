package com.sinosoft.xf.workflow.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.workflow.domain.ProcessDefine;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 流程定义信息dao
 * @date 2012-05-05
 * @author oba
 */
@Repository
public class ProcessDefineDao extends HibernateDao<ProcessDefine, String> {
	/**
	 * 通过区域编码查询流程定义信息
	 * @date 2012-05-05
	 * @author oba
	 * @param regionCode 区域编码
	 * @return 符合条件的对象个数
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessDefine> getProcessDefineByRegionCode(String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(ProcessDefine.class);
		criteria = criteria.add(Restrictions.eq("regionCode", ""));
		return criteria.list();
	}
}
