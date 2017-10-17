package com.sinosoft.xf.workflow.dao;


import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 信访流转状态信息dao
 * @date 2011-09-13
 * @author wangxx
 */
@Repository
public class PetitionCirculationStateInfoDao extends HibernateDao<PetitionCirculationStateInfo, String> {

	/**
	 * 通过活动编号和信访编号查询状态集合
	 * @date 2011-09-14
	 * @author wangxx
	 * @param activityNo 活动编号
	 * @param petitionNo 信访编号
	 * @return 状态集合
	 */
	public PetitionCirculationStateInfo getStateInfoByActivityNoAndPetitionNo(
			final String activityNo,final String petitionNo,String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateInfo.class);
		criteria = criteria.add(Restrictions.eq("activityNo", activityNo));
		criteria = criteria.add(Restrictions.eq("petitionNo", petitionNo));
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		return (PetitionCirculationStateInfo) criteria.uniqueResult();
	}
	
	/**
	 * 通过活动编号和信访编号查询状态集合
	 * @date 2011-09-14
	 * @author wangxx
	 * @param activityNo 活动编号
	 * @param petitionNo 信访编号
	 * @return 状态集合
	 */
	public PetitionCirculationStateInfo getStateInfoByRegionCodeAndPetitionNo(
			String petitionNo,String regionCode){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateInfo.class);
		criteria = criteria.add(Restrictions.eq("petitionNo", petitionNo));
		criteria = criteria.add(Restrictions.eq("regionCode", regionCode));
		return (PetitionCirculationStateInfo) criteria.uniqueResult();
	}

	/**
	 * 通过信访编号查询状态集合
	 * @date 2011-09-22
	 * @author wangxx
	 * @param petitionNo 信访编号
	 * @return 状态集合
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionCirculationStateInfo> getStateInfoByPetitionNo(final String petitionNo){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateInfo.class);
		criteria = criteria.add(Restrictions.eq("petitionNo", petitionNo));
		return criteria.list();
	}
	
	/**
	 * 通过修改时间查询状态集合
	 * @date 2013-05-16
	 * @author Dustin
	 * @param modifyDate 修改时间
	 * @return 状态集合
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionCirculationStateInfo> getStateInfoByModifyDate(final Timestamp modifyDate){
		final Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionCirculationStateInfo.class);
		criteria = criteria.add(Restrictions.ge("modifyDate", modifyDate));
		return criteria.list();
	}
}
