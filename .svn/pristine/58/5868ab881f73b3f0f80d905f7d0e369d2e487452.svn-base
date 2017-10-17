package com.sinosoft.organization.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 机构信息dao层
 * 
 * @author wangxx 
 * @date 2010-06-22
 */
@Repository
public class OrganizationInfoDao extends HibernateDao<OrganizationInfo, String> {
	/**
	 * 通过组织基本信息对象oid和其他属性查找组织信息对象集合
	 * @date 2010-11-29
	 * @author wangxx
	 * 用途：
	 * 	1、判断组织信息是否本修改
	 * @param oid  组织oid
	 * @param filterTxt  组织属性名
	 * @param filterValue  对应的属性值
	 * @return 组织信息对象集合
	 */
	public List<OrganizationInfo> getOrganizationInfoListByOidAndOrganizationProperty(
			String oid,String filterTxt , String filterValue){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("id", oid);
		values.put(filterTxt, filterValue);
		List<OrganizationInfo> list = this.find("From OrganizationInfo Where id=:id and " +
				filterTxt +"=:"+filterTxt, values);
		/*Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationInfo.class);
		criteria.add(Restrictions.eq("id", oid));
		criteria.add(Restrictions.eq(filterTxt, filterValue));
		List<OrganizationInfo> list = criteria.list();*/
		return list;
	}

//	/**
//	 * 通过机构英文名获取机构对象
//	 * @param OrgEname 机构英文名称
//	 * @date June 24 2010
//	 * @author wangxx
//	 * @return 组织基本信息对象如果不存在返回null
//	 */
//	public OrganizationInfo getOrganizationInfoByEname(String orgEname){
//		// 封装查询参数，查询人信息记录Oid
//		Criterion criterion = Restrictions.eq("orgEname", orgEname);
//		// 根据查询条件，查询
//		List<OrganizationInfo> list = this.find(criterion);
//		//判断是否取出来值，如果有值就进行选取
//		if(list.size()>0){
//			//获取机构对象
//			OrganizationInfo returnOrganizationInfo=list.get(0);
//			//返回机构对象
//			return returnOrganizationInfo;
//		}
//		//如果没有获取到机构对象，则返回null
//		return null;
//	}
//	
	/**
	 * 通过上级组织关系对象oid和本级组织oid查询数据库中存在组织关系对象个数
	 * 用途：
	 * 	1、用于判断组织下是否已经存在子组织，用于在组织关联添加时使用
	 * @date 2010-12-02
	 * @author wangxx
	 * @param organOid机构信息主键
	 * @param parentOrgId机构父节点
	 * @return 组织关系对象个数
	 */
	public int judgeOrganRepeat(String organOid,String parentOrgRelaOid) {
		// 创建Session
		Session session = this.getSession();
		String querySql = " select count(*) from OrganizationRelationInfo o where o.organizationInfo.id=:organOid  " +
				" and o.organizationRelationInfo.id=:parentOrgRelaOid";
		Query query = session.createQuery(querySql);
		query.setString("organOid", organOid);
		query.setString("parentOrgRelaOid", parentOrgRelaOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}

	/**
	 * 通过对象属性查找组织基本信息对象集合
	 * @date 2010-10-28
	 * @author wangxx
	 * @param text 组织基本信息对象属性名
	 * @param value 组织基本信息对象属性值
	 * @return 组织基本信息对象集合
	 */
	public List<OrganizationInfo> getOrganization(String text,String value){	
		Criterion criterion = Restrictions.eq(text, value);
		List<OrganizationInfo> list = this.find(criterion);
		return list;
	}
}
