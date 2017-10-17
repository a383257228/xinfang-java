package com.sinosoft.xf.system.transset.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.xf.system.transset.domain.PetitionOrganTransInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 转发机构设置信息dao
 * @date 2011-11-09
 * @author sunzhe
 */
@Repository
public class PetitionOrganTransInfoDao extends HibernateDao<PetitionOrganTransInfo,String> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 通过交换类型和交换机构查询数据个数
	 */
	public int getOrganTransInfoByOrgCodeAndOrgType(String orgType,String orgCode,String regionCode){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionOrganTransInfo.class,"basic");
		criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria.add(Restrictions.eq("orgType", orgType));
		criteria.add(Restrictions.eq("orgCode", orgCode));
		int size = (Integer)criteria.setProjection(Projections.count("id")).uniqueResult();
		return size;
	}
	
	/**
	 * 获取转发机构
	 * @param orgCode
	 * @param regionCode
	 * @return
	 */
	public PetitionOrganTransInfo getOrganTrans(String orgCode, String regionCode, String orgType) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionOrganTransInfo.class, "basic");
		criteria.add(Restrictions.eq("regionCode", regionCode));
		criteria.add(Restrictions.eq("validFlag", "0"));
		if (orgCode != null && !"".equals(orgCode))
			criteria.add(Restrictions.eq("orgCode", orgCode));
		if (orgType != null && !"".equals(orgType))
			criteria.add(Restrictions.eq("orgType", orgType));
		List<PetitionOrganTransInfo> list = criteria.list();
		if (!list.isEmpty())
			return list.get(0);
		return null;
	}
	
	/**
	 * 
	 * @author hjm
	 * 获取
	 * @param regionCode
	 * @param filterValue
	 * @param map
	 * @return
	 */
	public List<PetitionOrganTransInfo> getTransRegionCombo(String regionCode,
			String filterValue, Map<String, Object> map) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PetitionOrganTransInfo.class,
				"basic");
		criteria.add(Restrictions.eq("regionCode", regionCode));
		String orgType = (String) map.get("orgType");
		if (orgType != null && !"".equals(orgType)) {
			if (orgType.equals("noexchange")) {
				criteria.add(Restrictions.ne("orgType", "noexchange"));
			} else {
				criteria.add(Restrictions.eq("orgType", orgType));
			}
		}
		String validFlag = (String) map.get("validFlag");
		if (validFlag != null && !"".equals(validFlag)) {
			criteria.add(Restrictions.eq("validFlag", validFlag));
			criteria.add(Restrictions.ge("regionCode", regionCode));
			//add zhz 为了限制下级在传输时选择上级机构 这个只是暂时的,转发设置修改完毕后要删掉这个逻辑
			if(!"310000000000".equals(regionCode)){
				criteria.add(Restrictions.gt("orgCode", regionCode));
			}
		}
		//2015-10-15 liub 反映需要在转发设置中对转发机构添加可以设置失效的功能，让转发机构在录信时不显示，但不影响查询信件。
		String isTrans = (String) map.get("isTrans");
		if(isTrans!=null&&!"".equals(isTrans)){
			criteria.add(Restrictions.eq("isTrans", isTrans));
		}
		criteria.addOrder(Order.asc("orgCode"));
//		if (filterValue == null || "".equals(filterValue)) {
//			if (Constants.SH_REGION_CDOE.equals(regionCode)) {
//				criteria.add(Restrictions.ne("orgCode", "000000000000"));
//			} else {
//				criteria.add(Restrictions.ne("orgCode", Constants.SH_REGION_CDOE));
//			}
//		}
		return criteria.list();
	}
	/**
	 * 获取业务范围内的转发机构
	 * @param regionCode
	 * @param filterValue
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>>  getTransRegionComboByOrganTransInfos(String regionCode,
			String filterValue, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" select organ.* from PETITION_ORGAN_TRANS organ ");
		sql.append(" inner join ORGANIZATION org on org.org_code=organ.org_code ");
		sql.append(" where  LEFT(organ.org_Code,6)=organ.Organ_Id   ");
		sql.append(" and region_Code='"+regionCode+"' ");
		String orgType = (String) map.get("orgType");
		if (orgType != null && !"".equals(orgType)) {
			if (orgType.equals("noexchange")) {
				sql.append(" and org_Type <>'noexchange' ");
			} else {
				sql.append( "and org_Type ='"+orgType+"' ");
			}
		}
		String validFlag = (String) map.get("validFlag");
		if (validFlag != null && !"".equals(validFlag)) {
			sql.append("and validFlag ='"+validFlag+"'   ");
		}
		sql.append("order by organ.org_Code asc ");
		
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 获取转发机构代码第10位最大字符串的方法
	 * @author lijun 2015-1-7
	 * @param organId
	 * @param curRegionCode
	 * @param isUpperRegion
	 * @return
	 */
	public Map<String,Object> getMaxOrgCode(String organId, String curRegionCode, boolean isUpperRegion){
		StringBuffer sql = new StringBuffer("select max(substr(org_code,10,1)) max from petition_organ_trans ");
		sql.append("where 1=1 ");
		sql.append("and organ_id = '"+organId.trim()+"' ");
		if(!isUpperRegion){
			sql.append("and region_code = '"+curRegionCode+"' ");
		}
		return jdbcTemplate.queryForMap(sql.toString());
	}
}
