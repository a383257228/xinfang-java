package com.sinosoft.xf.petition.accept.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.petition.accept.domain.PetitionAccusedInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;

@Repository
public class PetitionAccusedInfoDao extends HibernateDao<PetitionAccusedInfo, String>  {

	/**
	 * 通过信访编号查询其他被反映人信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param petitionNo 属性
	 * @return 被反映人集合
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionAccusedInfo> getOtherAccusedInfo(final String petitionNo
			,String regionCode){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionAccusedInfo.class,"accused");
		criteria = criteria.add(Restrictions.eq("accused.petitionNo", petitionNo));
		criteria = criteria.add(Restrictions.eq("accused.regionCode", regionCode));
		criteria = criteria.add(Restrictions.ne("accused.accusedNum", 1));
		List<PetitionAccusedInfo> list = criteria.list();
		return list;
	}
}
