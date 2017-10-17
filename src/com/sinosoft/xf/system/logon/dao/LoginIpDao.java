package com.sinosoft.xf.system.logon.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.system.logon.domain.LoginIp;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 集中式部署的区域ip Dao
 * @author oba
 */
@Repository
public class LoginIpDao  extends HibernateDao<LoginIp,String>{
	/**
	 * 通过ip地址查询信息
	 */
	public LoginIp getInfoByIp(String ip){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(LoginIp.class);
		criteria = criteria.add(Restrictions.eq("ip", ip));
		return (LoginIp) criteria.uniqueResult();
	}
	/**
	 * 显示ip的查询分页方法
	 */
	@SuppressWarnings("unchecked")
	public List<LoginIp> getInfoByRegionCode(String regionCode,int start,int limit,String ip){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(LoginIp.class);
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		if (ip!=null && !"".equals(ip)) {
			criteria.add(Restrictions.like("ip", ip,MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(start).setMaxResults(limit);
		return  criteria.list();
	}
	/**
	 * 显示ip的查询分页方法Size
	 */
	public int getInfoByRegionCodeSize(String regionCode, int start, int limit,String ip) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(LoginIp.class);
		criteria.add(Restrictions.eq("regionCode",regionCode));
		if (ip!=null && !"".equals(ip)) {
			criteria.add(Restrictions.like("ip", ip,MatchMode.ANYWHERE));
		}
		int size = (Integer)criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
		return size;
	}
}
