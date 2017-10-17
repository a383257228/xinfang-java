package com.sinosoft.xf.petition.accept.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.constants.UserConstants;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;

/**
 * 基本信息     dao
 * @date 2017-06-12
 * @author guoh
 */
@Repository
public class PetitionDealInfoDao extends HibernateDao<PetitionDealInfo, String> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 根据id查询SurveyDealAndEndInfo信息
	 * @author guoh
	 * @date 2017-06-22
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> loadSurveyDealAndEndInfo(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select deal.deal_Type_Name as dealTypeName,deal.deal_Suggestion as dealSuggestion,survey.survey_Dept as surveyDept, ");
		sb.append("survey.survey_Composing as surveyComposing,survey.survey_Content as surveyContent, ");
		sb.append("ends.reality_Name as realityName,ends.end_Type_Name as endTypeName from petition_basic_info  basic ");
		sb.append("left join  petition_deal_info deal on basic.oid=deal.petition_basic_info_oid and deal.valid_flag=0 ");
		sb.append("inner join Petition_End_Info ends on ends.petition_deal_info_oid=deal.oid ");
		sb.append("inner join Petition_Survey_Deal_Info survey on survey.petition_deal_info_oid=deal.oid ");
		sb.append("where basic.oid='").append(id).append("'");
		//sb.append("and basic.region_code='310000000000'");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}
	/**
	 * 通过基本表oid查询有效的办理方案信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param basicOid 基本表oid
	 * @return 有效的办理信息集合
	 */
	@SuppressWarnings("unchecked")
	public PetitionDealInfo getValidDealInfoByBasicOid(String basicOid){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionDealInfo.class);
		criteria = criteria.add(Restrictions.eq("petitionBasicInfo.id", basicOid));
		criteria = criteria.add(Restrictions.eq("validFlag", UserConstants.ValidFlag.Valid.toString()));
		List<PetitionDealInfo> list = criteria.list();
		if(!delOtherValidDeal(list).isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param list
	 * @return
	 */
	private List<PetitionDealInfo> delOtherValidDeal(List<PetitionDealInfo> list){
		if(list.size()>1){
			for (int i = 1; i < list.size(); i++) {
				PetitionDealInfo deal=list.get(i);
				if(deal.getPetitionBasicInfo()!=null){
					deal.getPetitionBasicInfo().setDealInfoSet(null);
				}
				deal.setPetitionBasicInfo(null);
				this.delete(deal);
			}
		}
		return list;
	}
}