package com.sinosoft.organization.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 机构关系dao层
 * @author wangxx 
 * @date 2010-06-22
 */
@Repository
public class OrganizationRelationInfoDao extends HibernateDao<OrganizationRelationInfo, String> {
	
	//private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * 根据本级组织编码和下级组织编码查询存在的个数
	 * @date 2012-01-03
	 * @author Oba
	 * @param orgCode 本级组织编码
	 * @param childOrgCode 下级组织编码
	 * @return 存在的个数
	 */
	@SuppressWarnings("static-access")
	public int getOrgRelaSizeByOrgCodeAndChildOrgCode(final String orgCode,final String childOrgCode){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class,"orgRela");
		criteria.createAlias("organizationInfo", "org", criteria.INNER_JOIN);
		criteria = criteria.add(Restrictions.eq("org.orgCode", orgCode));
		criteria.createAlias("childrens", "childrens", criteria.INNER_JOIN);
		criteria = criteria.createCriteria("childrens.organizationInfo")
					.add(Restrictions.eq("orgCode", childOrgCode));
		int size = (Integer)criteria.setProjection(Projections.count("id")).uniqueResult(); 
		return size;
	}
	/**
	 * 根据机构编码查询组织关系信息
	 * @date 2012-01-03
	 * @author Oba
	 * @param orgCode 组织机构编码
	 * @return 组织关系集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrgRelaInfoByOrgCode(final String orgCode) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo")
					.add(Restrictions.eq("orgCode", orgCode));
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过组织基本信息属性查询在组织关联信息表中存在关联的组织基本信息对象集合
	 * @date 2011-05-25
	 * @author wangxx
	 * @param filterTxt 组织属性
	 * @param filterValue  测试值
	 * @return 在组织关联信息表中有关联的组织基本信息对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationInfo> getOrganizationInfoListByOrganization(String filterTxt,String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo").add(Restrictions.like(filterTxt, filterValue));
		List<OrganizationRelationInfo> list = criteria.list();
		List<OrganizationInfo> orgList= new ArrayList<OrganizationInfo>();
		for (int i = 0; i < list.size(); i++) {
			OrganizationRelationInfo organizationRelationInfo= list.get(i);
			orgList.add(organizationRelationInfo.getOrganizationInfo());
		}
		return orgList;
	}
	/**
	 * 通过组织基本信息属性查询组织关联信息对象的个数
	 * 用途：
	 * 	1、在新增组织信息时判断该属性值是否已经存在
	 * @date 2010-12-10
	 * @author wangxx
	 * @param filterTxt 属性名
	 * @param filterValue属性值，是一个String数组
	 * @return 返回存在的记录数
	 */
	public int judgeOrganizationInfos(String filterTxt,String[] filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo");
		criteria = criteria.add(Property.forName(filterTxt).in(filterValue));
		int size = (Integer)criteria.setProjection(Projections.count("id")).uniqueResult(); 
		return size;
	}
	
	/**
	 * 通过上级组织关联信息oid，组织类型标识，有效标识查询组织关联信息对象个数
	 * @date 2010-12-06
	 * @author wangxx
	 * @param purpose 组织类型
	 * @param parentOrgRelaOid 上级关系信息oid
	 * @param invalidFlag 是否有效
	 * @return 存在的记录数
	 */
	public int getOrgSize(String purpose,String parentOrgRelaOid,String invalidFlag) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
		}
		int size = (Integer)criteria.setProjection(Projections.count("id")).uniqueResult(); 
		return size;
	}

	/**
	 * 通过上级组织关联信息oid，组织类型标识，有效标识，组织基本信息oid数组
	 * 	查询组织关联信息对象个数
	 * @date 2010-12-06
	 * @author wangxx
	 * @param purpose 组织类型
	 * @param parentOrgRelaOid 上级关系信息oid
	 * @param invalidFlag 是否有效
	 * @param nodeIds 组织基本信息oid数组
	 * @return 存在的记录数
	 */
	public int getOrgSize(String purpose,String parentOrgRelaOid,String invalidFlag,String[] nodeIds) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		criteria.add(Restrictions.not(Restrictions.in("organizationInfo.id", nodeIds)));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
		}
		int size = (Integer)criteria.setProjection(Projections.count("id")).uniqueResult(); 
		return size;
	}
	/**
	 * 通过上级组织关联信息oid数组，组织类型标识，有效标识，组织基本信息oid数组
	 * 	查询组织关联信息对象集合
	 * @date 2012-01-09
	 * @author oba
	 * @param purpose 组织类型
	 * @param parentOrgRelaOid 上级关系信息oid数组
	 * @param invalidFlag 是否有效
	 * @param nodeIds 组织基本信息oid数组
	 * @return 存在的记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrgRelaInfo(String purpose,String[] parentOrgRelaOid,String invalidFlag,String[] nodeIds) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		if(parentOrgRelaOid!=null && parentOrgRelaOid.length != 0){
			//sql语句中in()这种形式最多支持1000条，所以超过1000条时需要进行分隔
			if(parentOrgRelaOid.length>1000){
				int d = parentOrgRelaOid.length/1000;
				Criterion[] cri = new Criterion[d];
				for(int i=0;i<d;i++){
					Object[] relaOid = new Object[1000];
					for(int x=0;x<1000;x++){
						if(((i*1000)+x)<parentOrgRelaOid.length){
							relaOid[x] = parentOrgRelaOid[(i*1000)+x];
						}
					}
					Criterion o = Restrictions.in("organizationRelationInfo.id", relaOid);
					cri[i]=o;
				}
				Criterion addCriterion = Restrictions.or(cri[0], cri[1]);
				for (int i = 2; i < cri.length; i++) {
					Restrictions.or(addCriterion, cri[i]);
				}
				criteria.add(addCriterion);
			}else{
				criteria.add(Restrictions.in("organizationRelationInfo.id", parentOrgRelaOid));
			}
		}
		criteria.add(Restrictions.not(Restrictions.in("organizationInfo.id", nodeIds)));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
		}
		return criteria.list();
	}
	
	/**
	 * 通过组织基本信息属性和组织关联信息的父组织关联信息oid查询组织关联信息对象集合
	 * @date 2010-12-01
	 * @author wangxx
	 * @param filterTxt 组织属性名
	 * @param filterValue  组织属性值
	 * @param parentOrgRelaOid 组织关联信息的父组织关联信息oid
	 * @return 组织关联信息对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrgRelaInfoByOrgAndParentOrgRelaOid(
			String filterTxt,String filterValue,String parentOrgRelaOid) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		criteria = criteria.createCriteria("organizationInfo")
					.add(Restrictions.eq(filterTxt, filterValue));
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过组织基本信息属性查找组织关联信息对象集合
	 * 被哪些方法调用:
	 * 1、OrganizationInfoManager.deleteOrganizationInfo
	 * 1、OrganizationInfoManager.deleteOrganizationInfoBySplit
	 * 3、OrganizationInfoManager.judgeOrganizationInfo
	 * @date 2010-11-29
	 * @author wangxx
	 * @param filterTxt 组织基本信息属性名
	 * @param filterValue  组织基本信息属性值
	 * @return 组织关联信息对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganizationRelationInfoListByOrganization(
			String filterTxt,String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo")
					.add(Restrictions.like(filterTxt, filterValue));
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过组织基本信息中文名或者英文名查找组织关系对象集合
	 * 被哪些方法调用:
	 * 1、OrganPersonRelationManager.getOrganUserColumnsTree
	 * @date 2010-11-29
	 * @author wangxx
	 * @param filterValue  组织基本信息中文名或者英文名对应的值
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganizationRelationInfoListByOrganization(
			String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo");
		criteria = criteria.add(Restrictions.or(Restrictions.like("orgEname", filterValue)
				,Restrictions.like("orgCname", filterValue)));
		criteria = criteria.add(Restrictions.eq("invalidFlag", "1"));
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过组织基本信息属性查找组织关系对象，带分页和排序。
	 * 2011-08-12孙哲修改可根据父级机构与机构基本信息进行查询
	 * @date 2010-04-20
	 * @author wangxx
	 * @param filterTxt 组织基本信息属性名
	 * @param filterValue  组织基本信息属性值
	 * @param start 当前页从多少条开始显示
	 * @param limit 每页显示条数
	 * @param order 排序字段
	 * @return 组织关系信息对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganizationRelationInfoListByOrganization(
			String filterTxt,String filterValue,int start,int limit,String order){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		if(filterTxt!=null&&filterValue!=null){
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			//根据关系表中的父机构进行查询，得到下级机构信息
			for(int i=0;i<filterTxts.length;i++){
				if(filterTxts[i].equals("organizationRelationInfo.id"))
					criteria = criteria.add(Restrictions.eq(filterTxts[i], filterValues[i]));
			}
			//判断是否将组织机构基本属性信息传进来
			criteria = criteria.createCriteria("organizationInfo");
			for(int i=0;i<filterTxts.length;i++){
				if(!filterTxts[i].equals("organizationRelationInfo.id"))
				criteria = criteria.add(Restrictions.like(filterTxts[i], filterValues[i]));
			}
		}
		
		//添加排序
		criteria.addOrder(Order.asc(order));
		criteria.setFirstResult(start).setMaxResults(limit);
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过组织基本信息属性查找组织关系对象个数
	 * 2011-08-12孙哲修改可根据父级机构与机构基本信息进行查询
	 * @date 2011-04-18
	 * @author wangxx
	 * @param filterTxt 组织基本信息属性名
	 * @param filterValue  组织基本信息属性值
	 * @return 组织关系对象个数
	 */
	public int getOrganizationRelationInfoListByOrganizationSize(String filterTxt,String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		//criteria = criteria.createCriteria("organizationInfo").add(Restrictions.like(filterTxt, filterValue));
		if(filterTxt!=null&&filterValue!=null){
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			//根据关系表中的父机构进行查询，得到下级机构信息
			for(int i=0;i<filterTxts.length;i++){
				if(filterTxts[i].equals("organizationRelationInfo.id"))
					criteria = criteria.add(Restrictions.eq(filterTxts[i], filterValues[i]));
			}
			//判断是否将组织机构基本属性信息传进来
			criteria = criteria.createCriteria("organizationInfo");
			for(int i=0;i<filterTxts.length;i++){
				if(!filterTxts[i].equals("organizationRelationInfo.id"))
				criteria = criteria.add(Restrictions.like(filterTxts[i], filterValues[i]));
			}
		}
		int size = (Integer)criteria.setProjection(Projections.count("id")).uniqueResult(); 
		return size;
	}
	/**
	 * 通过父节点组织oid和本级组织关系对应的组织oid获取本级组织关系对象集合
	 * 被哪些方法调用:
	 * 1、OrganizationInfoManager.deleteOrganizationInfo
	 * 1、OrganizationInfoManager.saveOrganizationInfo
	 * @date 2010-11-29
	 * @author wangxx
	 * @param parentOrgOid 父节点组织oid
	 * @param orgOid 组织关系对象关联的组织oid
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganizationRelationInfoListByParentOrgOidAndOrnOid(String parentOrgOid,String orgOid){
		String hql = "from OrganizationRelationInfo o where o.organizationRelationInfo.organizationInfo.id=:parentOrgOid and o.organizationInfo.id=:orgOid";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setString("parentOrgOid", parentOrgOid);
		query.setString("orgOid", orgOid);
		// 根据部门id查询记录
		List<OrganizationRelationInfo> list = query.list();
		// 返回OrganRelationInfo对象
		return list;
	}
	/**
	 * 通过上级组织关系oid和本级组织oid查询在关系表中存在的个数
	 * @date June 30 2010
	 * @author wangxx
	 * 用途
	 * 1、上级组织是否已经存在本组织
	 * @param organOid  组织基本信息oid
	 * @param parentOrgRelaOid  上级组织关系oid
	 * @return 查询记录数
	 */
	public int judgeOrganRepeat(String organOid,String parentOrgRelaOid) {
		// 创建Session
		Session session = this.getSession();
		String querySql = " select count(*) from OrganizationRelationInfo o where o.organizationInfo.id=:organOid  and " +
				"o.organizationRelationInfo.id=:parentOrgRelaOid";
		Query query = session.createQuery(querySql);
		query.setString("organOid", organOid);
		query.setString("parentOrgRelaOid", parentOrgRelaOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}
	/**
	 * 通过上级组织关系oid和本级组织中文名查找在组织关系表中存在的个数
	 * @date June 30 2010
	 * @author wangxx
	 * 用途：
	 * 1、判断上级组织下是否已经存在该中文名的组织
	 * @param orgCname  机构信息中文名
	 * @param parentOrgRelaOid  上级组织关系对象oid
	 * @return 查询记录数
	 */
	public int judgeOrganCNameRepeat(String orgCname,String parentOrgRelaOid) {
		// 创建Session
		Session session = this.getSession();
		String querySql = " select count(*) from OrganizationRelationInfo o " +
				"where o.organizationInfo.orgCname=:orgCname " +
				" and o.organizationRelationInfo.id=:parentOrgRelaOid";
		Query query = session.createQuery(querySql);
		query.setString("orgCname", orgCname);
		query.setString("parentOrgRelaOid", parentOrgRelaOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}
	/**
	 * 通过组织英文名和组织oid判断，组织中是否存在这样的记录
	 * @date June 30 2010
	 * @author wangxx
	 * 用途：
	 * 1、再修改机构信息时判断该条记录的中文名是否被修改
	 * @param organOid  组织基本信息oid
	 * @param orgCname  组织基本信息中文名
	 * @return 查询记录数
	 */
	public int judgeOrganCNameAndOidRepeat(String orgCname,String orgOid) {
		// 创建Session
		Session session = this.getSession();
		String querySql = " select count(*) from OrganizationInfo o " +
				"where o.orgCname=:orgCname and o.id=:orgOid";
		Query query = session.createQuery(querySql);
		query.setString("orgCname", orgCname);
		query.setString("orgOid", orgOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}
	
	/**
	 * 通过组织关系oid判断其下级存在机构的个数 organizationInfo.purpose='1'代表为机构
	 * 用途：
	 * 1、分级加载树时判断上级组织下是否有子机构，根据平煤项目组要求，
	 * 		在组织设置时左侧的树只展示机构不展示部门
	 * @param id  组织关系oid
	 * @return 组织关系对象个数
	 */
	public int builOrganTreeSize(String id){
		//创建Session
		Session session = this.getSession();
		String querySql="select count(id) FROM OrganizationRelationInfo " +
				"WHERE  organizationRelationInfo.id=:id and organizationInfo.purpose='1'";
		Query query = session.createQuery(querySql);
		query.setString("id", id);
		int count = ((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	/**
	 * 通过组织关系oid判断其下级存在组织关系对象的个数
	 * 用途：
	 * 1、分级加载树时判断上级组织下是否有子组织
	 * @param id 组织关系oid
	 * @return 组织关系对象个数
	 */
	public int buildMergeDeptTree(String id){
		//创建Session
		Session session = this.getSession();
		String querySql="select count(id) FROM OrganizationRelationInfo " +
				"WHERE  organizationRelationInfo.id=:id";
		Query query = session.createQuery(querySql);
		query.setString("id", id);
		int count = ((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	/**
	 * 根据组织oid查找组织关系对象集合
	 * @date 2010-12-02
	 * @author wangxx
	 * @param organOid 组织oid
	 * @return 组织关系对象集合
	 */
	public List<OrganizationRelationInfo> getOrganizationRelationInfoByOrganizationInfoOid(
			String organOid){
		Criterion criterionParentOid = Restrictions.eq("organizationInfo.id", organOid);
		// 根据部门id查询记录
		List<OrganizationRelationInfo> list = find(criterionParentOid);
		// 返回OrganRelationInfo对象
		return list;
	}

	/**
	 * 根据关系表父关系对象Oid查找关系表对象集合
	 * @date 2010-08-20 
	 * @author wangxx
	 * @param parentOrgRelaOid 上级组织关系对象oid 
	 * @return 机构关系对象集合
	 */
	public List<OrganizationRelationInfo> getOrgRelaInfoListByParentOrgRelaOid(String parentOrgRelaOid) {
		Criterion criterionParentOid = Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid);
		// 根据部门id查询记录
		List<OrganizationRelationInfo> list = find(criterionParentOid);
		// 返回OrganRelationInfo对象
		return list;
	}

	/**
	 * 根据组织关系表父关系对象Oid数组和组织对象的purpose属性，有效标识查找关系表对象集合
	 * @author wangxx
	 * @date 2011-08-04
	 * @param parentOrgRelaOid 上级组织关系对象oid数组 
	 * @param purpose 组织对象的身份，查找机构，部门，还是全部
	 * @param invalidFlag 组织基本信息有效标识
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,Object[] parentOrgRelaOid,String invalidFlag) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		if(parentOrgRelaOid!=null && parentOrgRelaOid.length>0){
			//sql语句中in()这种形式最多支持1000条，所以超过1000条时需要进行分隔
			if(parentOrgRelaOid.length>1000){
				int d = parentOrgRelaOid.length/1000;
				Criterion[] cri = new Criterion[d];
				for(int i=0;i<d;i++){
					Object[] relaOid = new Object[1000];
					for(int x=0;x<1000;x++){
						if(((i*1000)+x)<parentOrgRelaOid.length){
							relaOid[x] = parentOrgRelaOid[(i*1000)+x];
						}
					}
					Criterion o = Restrictions.in("organizationRelationInfo.id", relaOid);
					cri[i]=o;
				}
				Criterion addCriterion = Restrictions.or(cri[0], cri[1]);
				for (int i = 2; i < cri.length; i++) {
					Restrictions.or(addCriterion, cri[i]);
				}
				criteria.add(addCriterion);
			}else{
				criteria.add(Restrictions.in("organizationRelationInfo.id", parentOrgRelaOid));
			}
			//添加排序
//			criteria.addOrder(Order.asc("organOrder"));
			if(!"".equals(purpose)||!"".equals(invalidFlag)){
				criteria = criteria.createCriteria("organizationInfo");
				if(!"".equals(purpose)){
					criteria = criteria.add(Restrictions.eq("purpose", purpose));
				}
				if(!"".equals(invalidFlag)){
					criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
				}
			}
			return criteria.list();
		}else{
			return new ArrayList<OrganizationRelationInfo>();
		}
	}
	/**
	 * 根据组织关系表父关系对象Oid和组织对象的purpose属性，有效标识查找关系表对象集合
	 * @author wangxx
	 * @date 2010-11-25
	 * @param parentOrgRelaOid 上级组织关系对象oid 
	 * @param purpose 组织对象的身份，1查找机构，2部门，还是全部
	 * @param invalidFlag 组织基本信息有效标识
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,String parentOrgRelaOid,String invalidFlag) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		if(parentOrgRelaOid!=null&&!parentOrgRelaOid.equals(""))
			criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		//添加排序
		criteria.addOrder(Order.asc("organOrder"));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo","orgInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("orgInfo.purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("orgInfo.invalidFlag", invalidFlag));
			}
		}
		return criteria.list();
	}
	/**
	 * wld平台中承办部门排除案管室等部门
	 * @author jk
	 * @date 2014-7-6
	 * @param parentOrgRelaOid 上级组织关系对象oid 
	 * @param purpose 组织对象的身份，1查找机构，2部门，还是全部
	 * @param invalidFlag 组织基本信息有效标识
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOidNotContansOtherDept(
			String purpose,String parentOrgRelaOid,String invalidFlag) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		String [] b = {"310000000010","310000000012","310000000013",
				"310000000015","310000000017","310000000019","310000000020",
				"310000000031","310000000032","310000000008","310000000099"};
		if(parentOrgRelaOid!=null&&!parentOrgRelaOid.equals(""))
			criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		//添加排序
		criteria.addOrder(Order.asc("organOrder"));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo","orgInfo");
			criteria.add(Restrictions.not(Restrictions.in("orgInfo.orgCode", b)));
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("orgInfo.purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("orgInfo.invalidFlag", invalidFlag));
			}
		}
		return criteria.list();
	}
	/**
	 * 根据组织关系表父关系对象Oid和组织对象的purpose属性，有效标识查找关系表对象集合
	 * @author wangxx
	 * @date 2010-11-25
	 * @param parentOrgRelaOid 上级组织关系对象oid 
	 * @param purpose 组织对象的身份，查找机构，部门，还是全部
	 * @param invalidFlag 组织基本信息有效标识
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,String parentOrgRelaOid,String invalidFlag,boolean flag,String groupId) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		if(parentOrgRelaOid!=null&&!parentOrgRelaOid.equals("")&&!"1".equals(parentOrgRelaOid))
			criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		if(flag){//查询未进行分组的机构数据
			String sql = " this_.oid not in (select t.OBJ_OID from GROUP_ITEM_INFO t)";
			criteria.add(Restrictions.sqlRestriction(sql));
		}
		if(!"".equals(groupId)){
			String sql = " this_.oid in (select t.OBJ_OID from GROUP_ITEM_INFO t where  GROUP_OID = '"+groupId+"')";
			criteria.add(Restrictions.sqlRestriction(sql));
		}
		//添加排序
		criteria.addOrder(Order.asc("organOrder"));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
		}
		return criteria.list();
	}
	/**
	 * wld平台中承办部门排除案管室等部门,排除下级,只查询本级
	 * @author liub
	 * @date 2014-10-28
	 * @param parentOrgRelaOid 上级组织关系对象oid 
	 * @param purpose 组织对象的身份，1查找机构，2部门，还是全部
	 * @param invalidFlag 组织基本信息有效标识
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOidNotContansOtherDeptForTheLevel(
			String purpose,String invalidFlag,String TheLevelRegionCode) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		String [] b = {"310000000010","310000000012","310000000013",
				"310000000015","310000000017","310000000019","310000000020",
				"310000000031","310000000032","310000000008","310000000099"};
		//添加排序
		criteria.addOrder(Order.asc("organOrder"));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo","orgInfo");
			criteria.add(Restrictions.not(Restrictions.in("orgInfo.orgCode", b)));
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("orgInfo.purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("orgInfo.invalidFlag", invalidFlag));
			}
			if(!"".equals(TheLevelRegionCode)){
				TheLevelRegionCode=TheLevelRegionCode.substring(0, 6);
				criteria = criteria.add(Restrictions.like("orgInfo.orgCode", TheLevelRegionCode, MatchMode.START));
			}
		}
		List<OrganizationRelationInfo> orgList= criteria.list();
		List<OrganizationRelationInfo> orgList1=new ArrayList<OrganizationRelationInfo>();
		int sort=0;
		for (OrganizationRelationInfo organizationRelationInfo : orgList) {
			if("信访室".equals(organizationRelationInfo.getOrganizationInfo().getOrgCname())&&sort==0){
				orgList1.add(organizationRelationInfo);
				sort++;
			}
			if("案管室".equals(organizationRelationInfo.getOrganizationInfo().getOrgCname())&&sort==1){
				orgList1.add(organizationRelationInfo);
				for (OrganizationRelationInfo organizationRelationInfo1 : orgList){
					if(!"案管室".equals(organizationRelationInfo1.getOrganizationInfo().getOrgCname())&&!"信访室".equals(organizationRelationInfo1.getOrganizationInfo().getOrgCname())){
						orgList1.add(organizationRelationInfo1);
					}
				}
			}
		}
		return orgList1;
	}
	/**
	 * 根据组织关系表父关系对象Oid和组织对象的purpose属性查找关系表对象集合
	 * @author wangxx
	 * @date 2011-04-21
	 * 用途：
	 * 	1、目录系统，通过组织编码进行排序。
	 * @param parentOrgRelaOid 上级组织关系对象oid
	 * @param purpose 组织对象的身份，查找机构，部门，还是全部
	 * @param invalidFlag 组织对象有效标识
	 * @param order 排序字段
	 * @return OrganRelationInfo 机构关系对象数组
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,String parentOrgRelaOid,String invalidFlag,String order) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		if(!"".equals(purpose)||!"".equals(invalidFlag)||!"".equals(order)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
			//添加排序
			criteria.addOrder(Order.asc(order));
		}
		return criteria.list();
	}
	
	/**
	 * 根据组织关系表父关系对象Oid和组织对象的purpose属性查找关系表对象集合,带有分页功能。
	 * @date 2011-04-20
	 * @author wangxx
	 * @param parentOrgRelaOid 上级组织关系对象oid
	 * @param purpose 组织对象的身份，查找机构，部门，还是全部
	 * @param invalidFlag 组织基本信息对象有效标识
	 * @param start 当前页起始数据
	 * @param limit 当前页显示数据条数
	 * @param order 排序字段
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,String parentOrgRelaOid,String invalidFlag,int start 
			,int limit,String order) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
			//添加排序
			criteria.addOrder(Order.asc(order));
		}
		criteria.setFirstResult(start).setMaxResults(limit);
		return criteria.list();
	}

	/**
	 * 根据组织关系表父关系对象Oid和组织对象的purpose属性查找关系表对象集合，
	 * 	但是该集合不包含节点参数对应的节点
	 * @date 2010-11-25
	 * @author wangxx
	 * @param parentOrgRelaOid 上级组织关系对象oid
	 * @param purpose 组织对象的身份，查找机构，部门，还是全部
	 * @param invalidFlag 组织基本信息对象有效标识
	 * @param nodeId 组织基本信息对象oid
	 * @return OrganRelationInfo 机构关系对象数组
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,String parentOrgRelaOid,String invalidFlag,String[] nodeIds) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		criteria.add(Restrictions.not(Restrictions.in("organizationInfo.id", nodeIds)));
		//添加排序
		criteria.addOrder(Order.asc("organOrder"));
		if(!"".equals(purpose)||!"".equals(invalidFlag)){
			criteria = criteria.createCriteria("organizationInfo");
			if(!"".equals(purpose)){
				criteria = criteria.add(Restrictions.eq("purpose", purpose));
			}
			if(!"".equals(invalidFlag)){
				criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
			}
		}
		return criteria.list();
	}

	/**
	 * 根据组织oid查找其在组织关系表中存在下级的个数
	 * @date June 30 2010
	 * @author wangxx
	 * 用途：
	 * 1、判断组织下是否有部门，主要用于删除组织时做判断
	 * @param organOid  组织基本信息oid
	 * @return 查询记录数
	 */
	public int judgeChildOrgRelaInfoByOrgOid(String organOid) {
		Session session = this.getSession();
		String querySql = " select count(*) from OrganizationRelationInfo o " +
				",OrganizationRelationInfo org where o.organizationInfo.id=:organOid " +
				" and o.id=org.organizationRelationInfo.id";
		Query query = session.createQuery(querySql);
		query.setString("organOid", organOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}
	/**
	 * 通过组织关系oid查询该组织关系节点下是否存在下级组织
	 * @date June 30 2010
	 * @author wangxx
	 * 用途：
	 * 1、判断该组织节点下是否存在下级组织
	 * @param organRelaOid  机构关系信息对象oid
	 * @return 查询记录数
	 */
	public int judgeChildOrgRelaInfoByOrgRelaOid(String organRelaOid) {
		// 创建Session
		Session session = this.getSession();
		String querySql = " select count(*) from OrganizationRelationInfo o " +
				"where o.organizationRelationInfo.id=:organRelaOid";
		Query query = session.createQuery(querySql);
		query.setString("organRelaOid", organRelaOid);
		// 返回函数值
		return ((Long) query.uniqueResult()).intValue();
	}

	/**
	 * 根据父节点对象的组织oid查找组织关系集合
	 * 被哪些方法调用:
	 * 1、OrganizationInfoManager.deleteOrganizationInfo
	 * 2、OrganizationInfoManager.deleteOrganizationInfoBySplit
	 * 2、OrganizationInfoManager.saveOrganizationInfo
	 * @date 2010-08-20 
	 * @author wangxx
	 * @param parentOrgOid 上级组织基本信息oid
	 * @return OrganRelationInfo 机构关系对象数组
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganizationRelationInfoByParentOrgOid(
			String parentOrgOid) {
		// 创建Session
		Session session = this.getSession();
		String querySql = "from OrganizationRelationInfo o where " +
				"o.organizationRelationInfo.organizationInfo.id=:parentOrgOid";
		Query query = session.createQuery(querySql);
		query.setString("parentOrgOid", parentOrgOid);
		List<OrganizationRelationInfo> list = query.list();
		// 返回OrganRelationInfo对象
		return list;
	}
	
	/**
	 * 查询子区域
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganizationRelationInfoChildrens() {
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		String curRegionCode=person.getRegionCode();
		// 创建Session
		Session session = this.getSession();
		String querySql = "from OrganizationRelationInfo o where " +
				"o.organizationRelationInfo.organizationInfo.orgCode=:orgCode " +
				"and o.organizationRelationInfo.organizationInfo.purpose='1' " +
				"and o.organizationInfo.purpose='1'";
		Query query = session.createQuery(querySql);
		query.setString("orgCode", curRegionCode);
		List<OrganizationRelationInfo> list = query.list();
		return list;
	}
}
