package com.sinosoft.xf.pubfun.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.pubfun.domain.PetitionMaxNoInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 最大号信息dao
 * @date 2011-09-15
 * @author wangxx
 */
@Repository
public class PetitionMaxNoInfoDao extends HibernateDao<PetitionMaxNoInfo,String> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 根据id修改最大号，使最大号加1
	 * @param id id
	 */
	public void updateMaxNoInfo(int maxNo , String id){
        String sql = "update Petition_Max_No_Info t " +
        		"set t.max_No ="+maxNo+" where oid ='"+id+"'";
        jdbcTemplate.update(sql);
	}
	/**
	 * 通过号码类型和号码限制条件查询对象
	 * @date 2012-12-24
	 * @author Dustin
	 * @param noType 号码类型
	 * @param noLength 号码限制条件
	 * @return 符合条件的对象
	 */
	public PetitionMaxNoInfo getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(
			String noType,String regionCode){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionMaxNoInfo.class);
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria = criteria.add(Restrictions.eq("noType", noType));
		return (PetitionMaxNoInfo)criteria.uniqueResult(); 
	}
	/**
	 * 通过号码类型和号码限制条件查询对象
	 * @date 2012-12-24
	 * @author Dustin
	 * @param noType 号码类型
	 * @param noLength 号码限制条件
	 * @return 符合条件的对象
	 */
	@SuppressWarnings("unchecked")
	public PetitionMaxNoInfo getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(
			final String noType,final String noLength,String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionMaxNoInfo.class);
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria = criteria.add(Restrictions.eq("noType", noType));
		criteria = criteria.add(Restrictions.eq("noLimit", noLength));
		return (PetitionMaxNoInfo)criteria.uniqueResult(); 
	}
	/**
	 * 通过号码类型集和号码限制条件查询对象集合
	 * @param noType
	 * @param noLength
	 * @param regionCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionMaxNoInfo> getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(
			String[] noType,String regionCode){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionMaxNoInfo.class);
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria = criteria.add(Restrictions.in("noType", noType));
		return criteria.list(); 
	}
	/**
	 * 通过号码类型集和号码限制条件查询对象集合
	 * @param noType
	 * @param noLength
	 * @param regionCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionMaxNoInfo> getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(
			final String[] noType,final String noLength,String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionMaxNoInfo.class);
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria = criteria.add(Restrictions.in("noType", noType));
		criteria = criteria.add(Restrictions.eq("noLimit", noLength));
		return criteria.list(); 
	}
}
