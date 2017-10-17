package com.sinosoft.organization.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 机构分组信息dao层
 * @author wangxx 
 * @date 2010-10-11
 */
@Repository
public class GroupItemInfoDao extends HibernateDao<GroupItemInfo, String> {

	/**
	 * 通过分组信息oid查询分组关联用户信息对象集合数量
	 * @param groupOid  分组信息oid分组信息oid
	 * @return  分组关联信息对象集合数量
	 */
	public int getGroupItemSizeByGroupOid(String groupOid){
		//获取session
		Session session = this.getSession();
		//方法返回值
		int count =0;
		//查询信息总数语句
		String querySql = "select count(g.id) " +
				"from GroupItemInfo g ,OrganPersonRelationInfo o " +
				"where g.objType = 'person' and g.groupInfo.id=:groupOid " +
				" and g.objOid=o.id";
		Query query = session.createQuery(querySql);
		query.setString("groupOid", groupOid);
		//获取记录总数
		count = ((Long)query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 通过分组信息oid查询分组关联用户关联信息对象集合，带分页
	 * @param groupOid  分组信息oid分组信息oid
	 * @param start 起始条数
	 * @param limit 每页显示的条数
	 * @return  分组关联信息对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupItemByGroupOid(String groupOid,int start,int limit){
		//获取session
		Session session = this.getSession();
		//查询信息语句
		String querySql = "from GroupItemInfo g ,OrganPersonRelationInfo o " +
				"where g.objType = 'person' and g.groupInfo.id=:groupOid " +
				" and g.objOid=o.id";
		Query query = session.createQuery(querySql);
		query.setString("groupOid", groupOid);
		//设置分页
		query.setFirstResult(start).setMaxResults(limit);
		//获取集合
		List<Object[]> list = query.list();
		//返回集合
		return list;
	}

	/**
	 * 通过分组信息分组编码和组织分组标示查询分组关联信息对象集合
	 * @param groupCode  分组信息分组编码
	 * @return  分组关联信息对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<GroupItemInfo> getGroupItemByGroupCode(String groupCode){
		//获取session
		Session session = this.getSession();
		//查询信息语句
		String querySql = "from GroupItemInfo g where g.objType = 'organ' " +
				" and g.groupInfo.groupCode=:groupCode";
		Query query = session.createQuery(querySql);
		query.setString("groupCode", groupCode);
		//获取集合
		List<GroupItemInfo> list = query.list();
		//返回集合
		return list;
	}

	/**
	 * 根据组织用户关系oid和用户分组标示，查询分组关联对象集合
	 * @param orgPersonRelaOid  组织用户关系表oid
	 * @return 分组项对戏集合
	 */
	@SuppressWarnings("unchecked")
	public List<GroupItemInfo> getGroupItemInfoByPersonOid(String orgPersonRelaOid){
		//获取session
		Session session = this.getSession();
		//查询信息语句
		String querySql = "from GroupItemInfo g where g.objType='person' and g.objOid =:orgPersonRelaOid";
		Query query = session.createQuery(querySql);
		query.setString("orgPersonRelaOid", orgPersonRelaOid);
		//获取集合
		List<GroupItemInfo> list = query.list();
		//返回集合
		return list;
	}
	/**
	 * 根据组织关系oid和组织分组标示，查询分组关联对象集合
	 * @param orgRelaOid  组织关系oid
	 * @return 分组项对戏集合
	 */
	@SuppressWarnings("unchecked")
	public List<GroupItemInfo> getGroupItemInfoByOrgRelaOid(String orgRelaOid){
		//获取session
		Session session = this.getSession();
		//查询信息语句
		String querySql = "from GroupItemInfo g where g.objType='organ' and g.objOid =:orgRelaOid";
		Query query = session.createQuery(querySql);
		query.setString("orgRelaOid", orgRelaOid);
		//获取集合
		List<GroupItemInfo> list = query.list();
		//返回集合
		return list;
	}
	
	/**
	 * 通过分组项关联对象oid查处分组项对象集合
	 * @param objOid 分组项关联对象oid
	 * @return 分组项对戏集合
	 */
	@SuppressWarnings("unchecked")
	public List<GroupItemInfo> getGroupItemInfoByObjOid(String objOid){
		//获取session
		Session session = this.getSession();
		//查询信息语句
		String querySql = "from GroupItemInfo g where g.objOid =:objOid";
		Query query = session.createQuery(querySql);
		query.setString("objOid", objOid);
		//获取集合
		List<GroupItemInfo> list = query.list();
		//返回集合
		return list;
	}
}
