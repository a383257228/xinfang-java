package com.sinosoft.xf.petition.accept.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.petition.accept.domain.PetitionAccuserInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;

@Repository
public class PetitionAccuserInfoDao extends HibernateDao<PetitionAccuserInfo, String>  {

	/**
	 * 通过信访编号查询其他反映人信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param petitionNo 属性
	 * @return 反映人集合
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionAccuserInfo> getOtherAccuserInfo(final String petitionNo
			,String regionCode){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionAccuserInfo.class,"accuser");
		criteria = criteria.add(Restrictions.eq("accuser.petitionNo", petitionNo));
		criteria = criteria.add(Restrictions.eq("accuser.regionCode", regionCode));
		criteria = criteria.add(Restrictions.ne("accuser.accuserNum", 1));
		List<PetitionAccuserInfo> list = criteria.list();
		return list;
	}
}
