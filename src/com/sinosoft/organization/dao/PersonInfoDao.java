/**
 * Copyright (c) sinosoft May 18 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 18 2010
 * @author shenhy
 */
package com.sinosoft.organization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 人员信息持久层 
 * @author shenhy 
 * May 18 2010
 */
@Repository
public class PersonInfoDao extends HibernateDao<PersonInfo, String> {
	/**
	 * 通过用户基本信息对象oid和其他属性查找用户对象集合
	 * 用途：
	 * 	1、在判断用户属性唯一性时，判断该用户基本信息是否被修改。
	 * @date 2010-11-27
	 * @author wangxx
	 * @param oid  用户oid
	 * @param filterTxt  用户属性名
	 * @param filterValue  对应的属性值
	 * @return 用户对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<PersonInfo> getPersonInfoListByOidAndPersonProperty(
			String oid,String filterTxt , String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PersonInfo.class);
		criteria.add(Restrictions.eq("id", oid));
		criteria.add(Restrictions.eq(filterTxt, filterValue));
		List<PersonInfo> list = criteria.list();
		return list;
	}

	/**
	 * 通过组织基本信息oid和用户基本信息oid判断在组织用户关系表中存在多少条记录
	 * 用途
	 * 	1、在将用户和组织关联时，判断两者是否已经建立了关联
	 * @date May 31 2010
	 * @author shenhy
	 * @param organizationInfoOid  组织基本信息oid
	 * @param personOid  用户基本信息oid
	 * @return int 返回记录的条数
	 */
	public int judgePersonOrganRepeat(String organizationInfoOid,String personOid) {
		// 创建Session
		Session session = this.getSession();
		// 根据人员英文名查询语句
		String querySql = " select count(*) from OrganPersonRelationInfo p " +
				"where p.organizationInfo.id=:organizationInfoOid " +
				"and p.personInfo.id=:personOid";
		// 创建查询器
		Query query = session.createQuery(querySql);
		query.setString("organizationInfoOid", organizationInfoOid);
		query.setString("personOid", personOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}
	/**
	 * 更新领导用户的使用次数(下拉框排序)
	 * @date 2014-06-29
	 * @author liub
	 * @return 
	 */
	public void updateQueryCount(String leaderName){
		Session session =this.getSession();
		StringBuffer hql = new StringBuffer("update PersonInfo set queryCount=queryCount+1 where userCname=:leaderName and invalidFlag='1' ");
		Query query = session.createQuery(hql.toString());
		query.setString("leaderName",leaderName);
		query.executeUpdate();
	}
	/**
	 * 通过部门名称和用户类型获取personInfo信息
	 * @date 2014-07-04
	 * @author liub
	 * @return 
	 */
	public PersonInfo getPersonInfoListByLeaderFlagAndOrgCname(String LeaderFlag,String OrgCname){
		Session session =this.getSession();
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer sql = new StringBuffer("select p.* from ORGAN_PERSON_RELATION_INFO op ,ORGANIZATION o ,PERSON_INFO p "+
				" where op.PERSON_INFO_id=p.oid and op.ORGANIZATION_Info_id=o.oid "+
				" and p.leader_flag=:LeaderFlag and o.org_Cname=:OrgCname and p.region_code='"+person.getRegionCode()+"' " +
						" and p.Invalid_Flag!='2' ");
		List<PersonInfo> list=session.createSQLQuery(sql.toString()).addEntity(PersonInfo.class).setString("LeaderFlag", LeaderFlag).setString("OrgCname", OrgCname).list();
		if(list.size()>0){
			return (PersonInfo)list.get(0);
		}else {
			return null;
		}
		
	}
	/**
	 * 通过用户英文名称和用户类型获取personInfo信息
	 * @date 2015-04-10
	 * @author liub
	 * @return 
	 */
	public PersonInfo getPersonInfoByUserEname(String UserEname){
		Session session =this.getSession();
		StringBuffer sql = new StringBuffer("select p.* from PERSON_INFO p "+
				" where p.user_ename='"+UserEname+"'");
		List<PersonInfo> list=session.createSQLQuery(sql.toString()).addEntity(PersonInfo.class).list();
		if(list.size()>0){
			return (PersonInfo)list.get(0);
		}else {
			return null;
		}
		
	}
}
