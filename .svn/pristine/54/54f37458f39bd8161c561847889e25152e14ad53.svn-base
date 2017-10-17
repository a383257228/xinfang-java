/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 17 2010
 * @author shenhy
 */
package com.sinosoft.organization.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.DeptName;
import com.sinosoft.xf.constants.UserConstants.LeaderFlag;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**   
 * 机构用户关系持久层  
 * @author shenhy   
 *  
 * @date May 17 2010   
 */
@Repository
public class OrganPersonRelationDao  extends HibernateDao<OrganPersonRelationInfo,String>{
	
	/**
	 * 通过用户中文名或者英文名查询组织用户关系
	 * @date 2011-05-03
	 * 被哪些方法调用:
	 * 1、OrganPersonRelationManager.getOrganUserColumnsTree
	 * @author wangxx
	 * @param filterValue  属性值
	 * @return 组织用户关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getOrganPersonRelaListByPersonName(
			String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		criteria = criteria.createCriteria("personInfo").add(
				Restrictions.or(Restrictions.like("userEname", filterValue)
						, Restrictions.like("userCname", filterValue)));
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}

	/**
	 * 通过用户属性查找机构用户关系对象
	 * @date 2010-11-27
	 * @author wangxx
	 * @param filterTxt 用户属性
	 * @param filterValue  测试值
	 * @return 组织用户关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getOrganPersonRelaListByPerson(
			String filterTxt,String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		criteria = criteria.createCriteria("personInfo")
					.add(Restrictions.eq(filterTxt, filterValue));
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过用户属性查找机构用户关系对象
	 * @date 2010-11-27
	 * @author wangxx
	 * @param filterTxt 用户属性
	 * @param filterValue  测试值
	 * @return 组织用户关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getOrganPersonRelaListByOrgan(
			String filterTxt,String filterValue){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo")
					.add(Restrictions.eq(filterTxt, filterValue));
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}
	/**
	 * 通过组织用户关系oid，组织基本信息oid和用户基本信息oid
	 * 	确定关系对象集合或者通过部门oid和用户oid确定关系集合
	 * @date 2010-11-27
	 * @author wangxx
	 * @param organPersonOid 组织用户关系oid
	 * @param organOid 组织基本信息oid
	 * @param personOid 用户基本信息oid
	 * @return 组织用户关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> 
			getOrganPersonRelationInfoByOrganPersonOidAndOrganOidAndPersonOid(
			String organPersonOid,String organOid,String personOid){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		if(!organPersonOid.equals("")){
			criteria = criteria.add(Restrictions.eq("id", organPersonOid));
		}
		criteria = criteria.add(Restrictions.eq("organizationInfo.id", organOid));
		criteria = criteria.add(Restrictions.eq("personInfo.id", personOid));
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}
	
	/**
	 * 以用户中文名或者组织信息oid查询机构用户关系对象个数
	 * @param filterTxt 属性值 	personInfo.userCname为用户中文名，
	 * 							organizationInfo.id组织基本信息oid，
	 * 							“”为全部信息
	 * @param filterValue 属性名
	 * @return 存在的组织用户关系对象个数
	 */
	public int organRelaPersonPagedQuery(String filterTxt,String filterValue){
		Session session = this.getSession();
		StringBuffer bf = new StringBuffer("");
		if("personInfo.userCname".equals(filterTxt)){
			bf.append("select count(id) from OrganPersonRelationInfo " +
					"where personInfo.userCname like :filterValue");
		}else if("organizationInfo.id".equals(filterTxt)){
			bf.append("select count(id) from OrganPersonRelationInfo " +
					"where organizationInfo.id=:filterValue");
			//如果是多个参数，用;分隔
		}else if(filterTxt!=null&&filterTxt.indexOf(";")>0){
			bf.append("select count(id) from OrganPersonRelationInfo Where ");
			String[] filterTxts = filterTxt.split(";");
			for(int i=0;i<filterTxts.length;i++){
				if(filterTxts[i].equals("personInfo.userCname"))
					bf.append(filterTxts[i]+" like :filterValue"+i);
				else
					bf.append(filterTxts[i]+"=:filterValue"+i);
				bf.append(" and ");
			}
			bf.append(" 1=1 ");
		}else{
			bf.append("select count(id) from OrganPersonRelationInfo ");
		}
		Query query = session.createQuery(bf.toString());
		if("personInfo.userCname".equals(filterTxt)||"organizationInfo.id".equals(filterTxt)){
			query.setString("filterValue", filterValue);
			//如果是多个参数，对多个参数进行传参
		}else if(filterTxt!=null&&filterTxt.indexOf(";")>0&&filterValue.indexOf(";")>0){
			String[] filterValues = filterValue.split(";");
			for(int i=0;i<filterValues.length;i++){
				query.setString("filterValue"+i, filterValues[i]);
			}
		}
		return ((Long)query.uniqueResult()).intValue();
	}
	
	/**
	 * 以用户中文名或者组织信息oid查询机构用户关系对象集合
	 * 2011-08-12孙哲修改支持多个参数同时查询，用;分隔
	 * @param filterTxt 属性名 	personInfo.userCname为用户中文名，
	 * 							organizationInfo.id组织基本信息oid，
	 * 							“”为全部信息
	 * @param filterValue 属性值
	 * @param start 开始记录
	 * @param limit 显示记录数
	 * @param sort 排序字段
	 * @param dir 顺序
	 * @return 组织用户关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> organRelaPersonPagedQuery(String filterTxt
			,String filterValue,int start,int limit,String sort,String dir){
		Session session = this.getSession();
		StringBuffer bf = new StringBuffer("");
		String sortPro = "";
		if(null!=sort&&!"".equals(sortPro)){
			sortPro=" order by "+sort+" "+dir;
		}
		if("personInfo.userCname".equals(filterTxt)){
			bf.append("from OrganPersonRelationInfo where personInfo.userCname like :filterValue "+sortPro);
		}else if("organizationInfo.id".equals(filterTxt)){
			bf.append("from OrganPersonRelationInfo where organizationInfo.id=:filterValue "+sortPro);
			//如果是多个参数，用;分隔
		}else if(filterTxt!=null&&filterTxt.indexOf(";")>0){
			bf.append("from OrganPersonRelationInfo Where ");
			String[] filterTxts = filterTxt.split(";");
			for(int i=0;i<filterTxts.length;i++){
				if(filterTxts[i].equals("personInfo.userCname"))
					bf.append(filterTxts[i]+" like :filterValue"+i);
				else
					bf.append(filterTxts[i]+"=:filterValue"+i);
				bf.append(" and ");
			}
			bf.append(" 1=1 ");
			bf.append(sortPro);
		}else{
			bf.append("from OrganPersonRelationInfo ");
		}
		Query query = session.createQuery(bf.toString());
		if("personInfo.userCname".equals(filterTxt)||"organizationInfo.id".equals(filterTxt)){
			query.setString("filterValue", filterValue);
			//如果是多个参数，对多个参数进行传参
		}else if(filterTxt!=null&&filterTxt.indexOf(";")>0&&filterValue.indexOf(";")>0){
			String[] filterValues = filterValue.split(";");
			for(int i=0;i<filterValues.length;i++){
				query.setString("filterValue"+i, filterValues[i]);
			}
		}
		query.setFirstResult(start).setMaxResults(limit);
		return query.list();
	}
	/**
	 * 查询人员列表
	 * @date May 17 2010
	 * @author shenhy
	 * @param filterT 查询字段名称 ，多个属性之间用“；”分隔
	 * @param filterV 查询字段值 ，多个值之间用“；”分隔
	 * @param start 查询记录开始号
	 * @param limit 每页显示的记录数量
	 * @return  List返回人员关系记录
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryPersonInfo(String filterT,String filterV
			,int start,int limit,String sort,String dir){
		String sortField="";
		if(sort.equals("Person_CName")){
			sortField=" order by p.userCname "+dir;
		}else if(sort.equals("Person_EName")){
			sortField=" order by p.userEname "+dir;
		}
		//函数返回值
		List<Object[]> list ;
		//获取session
		Session session = this.getSession();
		//查询语句
		String querySql = " from PersonInfo p,OrganPersonRelationInfo op , OrganizationInfo og" +
				" where op.personInfo.id = p.id and op.organizationInfo.id = og.id and p.invalidFlag='1' "+sortField;
		//如果filterT不为空，则组装SQL
		if(filterT!=null&&!"".equals(filterT)){
			String[] filterName = filterT.split(";");
			String[] filterValue = filterV.split(";");
			//判断查询项是否为空
			//循环设置查询参数
			for(int i=0;i<filterName.length;i++){
				//用户英文名称
				if(filterName[i]!=null&&"personName".equals(filterName[i])){
					querySql += " and (p.userEname like '"+filterValue[i]+"' or p.userCname like '"+filterValue[i]+"')";
				}
				//用户中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					querySql += " and p.userCname like :personCName ";
				}
				//用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					querySql += " and op.organizationInfo.id = :organizationInfoId ";
				}		
			}
			//创建查询器
			Query query = session.createQuery(querySql);
			for(int i=0;i<filterName.length;i++){
				//设置值英文名称
//				if(filterName[i]!=null&&"personEName".equals(filterName[i])){
//					query.setString("personEName", filterValue[i]);
//				}
				//设置查询中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					query.setString("personCName", filterValue[i]);
				}
				//设置查询用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					query.setString("organizationInfoId", filterValue[i]);
				}
			}

			//分页查询
			query.setFirstResult(start).setMaxResults(limit);
			//返回值
			list = query.list();
		}else{
			Query query = session.createQuery(querySql);
			//分页查询
			query.setFirstResult(start).setMaxResults(limit);
			//返回值
			list = query.list();
		}

		return list;
	}
	
	/**
	 * 查询人员列表总数
	 * @date May 18 2010
	 * @author shenhy
	 * @param filterT 查询参数字段名称 ，多个属性之间用“；”分隔
	 * @param filterV 查询参数字段值 ，多个值之间用“；”分隔
	 * @return 人员记录总数
	 */
	public int queryPersonCount(String filterT,String filterV){
		//获取session
		Session session = this.getSession();
		//方法返回值
		int count =0;
		//查询人员信息总数语句
		String querySql = "select count(op.id) from OrganPersonRelationInfo op ,PersonInfo p, OrganizationInfo og" +
				" where op.personInfo.id = p.id and op.organizationInfo.id = og.id and p.invalidFlag='1'";
		Query query = null;
		//如果filterT不为空，则组装SQL
		if(filterT!=null&&!"".equals(filterT)){
			String[] filterName = filterT.split(";");
			String[] filterValue = filterV.split(";");
			//判断查询项是否为空
			//用户英文名称
			//循环设置查询参数
			for(int i=0;i<filterName.length;i++){
				if(filterName[i]!=null&&"personName".equals(filterName[i])){
					querySql += " and (p.userEname like '"+filterValue[i]+"' or p.userCname like '"+filterValue[i]+"')";
				}
				//用户中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					querySql += " and p.userCname like :personCName ";
				}
				//用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					querySql += " and op.organizationInfo.id = :organizationInfoId ";
				}
			}
			//创建查询器
			query = session.createQuery(querySql);
			//设置值英文名称
			for(int i=0;i<filterName.length;i++){
//				if(filterName[i]!=null&&"personEName".equals(filterName[i])){
//					query.setString("personEName", filterValue[i]);
//				}
//				//设置查询中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					query.setString("personCName", filterValue[i]);
				}
				//设置查询用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					query.setString("organizationInfoId", filterValue[i]);
				}
			}
		//如果为空，则直接查询	
		}else{
			//创建查询器
			query = session.createQuery(querySql);
		}
		
		//获取记录总数
		count = ((Long)query.uniqueResult()).intValue();
		return count;
	}
	/**
	 * 通过部门oid查询组织人员关系对象集合
	 * 被哪些方法调用:
	 * 1、OrganPersonRelationManager.getOrganUserColumnsTree
	 * @date July 28 2010 
	 * @author wangxx 
	 * @param organOid 组织信息oid
	 * @return 组织用户关系集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getOrganPersonRelationInfoByOrganOid(String organOid){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		criteria.add(Restrictions.eq("organizationInfo.id", organOid));
		criteria = criteria.createCriteria("personInfo");
		criteria.addOrder(Order.asc("userCode"));
		return criteria.list();
	}
	/**
	 * 通过用户oid查询组织用户关系对象集合
	 * @date July 28 2010 
	 * @author wangxx
	 * @param personOid 用户oid
	 * @return 组织用户对象集合 
	 */
	public List<OrganPersonRelationInfo> getOrganPersonRelationInfoByPersonOid(
			String personOid){
		// 封装查询参数，查询人信息记录Oid
		Criterion criterion = Restrictions.eq("personInfo.id", personOid);
		// 根据查询条件，查询
		List<OrganPersonRelationInfo> list = this.find(criterion);
		//返回集合
		return list;
	}
	
	/**
	 * 根据部门oid和用户oid查找组织用户对象，只要求返回一个对象
	 * @date 2010-07-29
	 * @author wangxx
	 * @param organOid 组织oid
	 * @param personOid 用户oid
	 * @return 组织用户关系对象
	 */
	public OrganPersonRelationInfo getOrganPersonRelationInfoByOrganOidAndPersonOid(
			String organOid,String personOid){
		// 封装查询参数，查询人信息记录Oid
		Criterion criterion = Restrictions.eq("organizationInfo.id", organOid);
		Criterion criterion1 = Restrictions.eq("personInfo.id", personOid);
		// 根据查询条件，查询
		List<OrganPersonRelationInfo> list = this.find(criterion,criterion1);
		//返回集合
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据组织oid查找在组织用户中存在的个数
	 * 用途：
	 * 1、分级加载树判断是否有子节点  该节点的下级是用户
	 * @param id 组织oid
	 * @return 组织用户对象个数
	 */
	public int getOrgUserListSize(String id){
		//创建Session
		Session session = this.getSession();
		String querySql="select count(id) FROM OrganPersonRelationInfo " +
				"WHERE  organizationInfo.id=:id";
		Query query = session.createQuery(querySql);
		query.setString("id", id);
		int count = ((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	/**
	 * 通过组织oid数组查询组织用户关系对象集合
	 * @author wangxx
	 * @date 2011-08-04
	 * @param organOid 组织Oid数组
	 * @return 组织用户关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getOrganRelaPersonInfoByOrganOid(String[] organOid) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		if(organOid!=null && organOid.length>0){
			//sql语句中in()这种形式最多支持1000条，所以超过1000条时需要进行分隔
			if(organOid.length>800){
				int d = organOid.length/800;
				Criterion[] cri = new Criterion[d];
				for(int i=0;i<d;i++){
					Object[] relaOid = new Object[800];
					for(int x=0;x<800;x++){
						if(((i*800)+x)<organOid.length){
							relaOid[x] = organOid[(i*800)+x];
						}
					}
					Criterion o = Restrictions.in("organizationInfo.id", relaOid);
					cri[i]=o;
				}
				Criterion addCriterion = Restrictions.or(cri[0], cri[1]);
				for (int i = 2; i < cri.length; i++) {
					Restrictions.or(addCriterion, cri[i]);
				}
				criteria.add(addCriterion);
			}else{
				criteria.add(Restrictions.in("organizationInfo.id", organOid));
			}
			return criteria.list();
		}
		return new ArrayList<OrganPersonRelationInfo>();
	}
	
	/**
	 * 查询部门领导关联集合
	 * @param deptCode :部门编码值deptCode
	 * @param deptName :部门值deptName  如果deptName存在不按照deptCode查询
	 * @param regionCode :存在时查询regionCode为当前用户的所有用户
	 * @return 部门领导关联集合
	 * if 顺序不能改变
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getDeptLeaderPersonRelaListByDeptCode(Map<String,Object> map){
		Object isAllPersion = map.get("isAllPersion");
		if(!Constants.TRUE.equals(isAllPersion)){
			if(map.get("deptCode")==null||"".equals(map.get("deptCode"))){
				map.put("deptCode", Struts2Utils.getSessionAttribute("deptCode"));
			}
		}
		Object deptCode = map.get("deptCode");
		Object deptName = map.get("deptName");
		Object leaderFlag = map.get("leaderFlag");
		Session session=this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		if ((deptName != null && !"".equals(deptName))||(deptCode != null && !"".equals(deptCode))){
			criteria.createAlias("organizationInfo", "organizationInfo", Criteria.INNER_JOIN);
		}
		criteria.createAlias("personInfo", "personInfo", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("personInfo.regionCode", Struts2Utils.getSessionAttribute("curRegionCode")));
		//顺序不能改变
		if (deptName != null && !"".equals(deptName)){
			criteria.add(Restrictions.eq("organizationInfo.orgCname", deptName));
		}else if (deptCode != null && !"".equals(deptCode)){
			criteria.add(Restrictions.eq("organizationInfo.orgCode", deptCode));
		}
		criteria.add(Restrictions.eq("personInfo.invalidFlag", "1"));
		//一室指定办理人，查一室秘书和一般用户
		//信访室指定办理人，查信访室秘书，一般用户和所有的业务管理员
		if(leaderFlag.equals(LeaderFlag.GENERAL.toString())){
			String currDeptName = Struts2Utils.getSessionAttribute("deptName").toString();
			if(DeptName.XFS.toString().equals(currDeptName)){
				//update by jk  如果是xfs则所有的用户都进行查询，包括业务管理员 
				criteria.add(
						Restrictions.in("personInfo.leaderFlag", new String[] { LeaderFlag.SECRETARY.toString(), LeaderFlag.GENERAL.toString(),
								LeaderFlag.LEADER.toString() , LeaderFlag.BUSINESS_ADMIN.toString()}));
//				criteria.add(Restrictions.or(
//						Restrictions.in("personInfo.leaderFlag", new String[] { LeaderFlag.SECRETARY.toString(), LeaderFlag.GENERAL.toString(),
//								LeaderFlag.LEADER.toString() }), Restrictions.eq("personInfo.leaderFlag", LeaderFlag.BUSINESS_ADMIN.toString())));
			}else{
				criteria.add(Restrictions.in("personInfo.leaderFlag", new String[]{LeaderFlag.SECRETARY.toString(),LeaderFlag.GENERAL.toString(),LeaderFlag.LEADER.toString()}));
			}
		}else{
			criteria.add(Restrictions.eq("personInfo.leaderFlag", leaderFlag));
		}
		String defaultDealerNameOrder = (String)map.get("defaultDealerNameOrder");
		if("defaultDealerNameOrder".equals(defaultDealerNameOrder)){
			criteria.addOrder(Order.asc("personInfo.defaultDealerNameOrder"));
		}else{
			criteria.addOrder(Order.desc("personInfo.queryCount"));
		}
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}
	
	/**
	 * 查询部门领导关联集合
	 * @param filterValue :组织id
	 * @return 部门领导关联集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getDeptLeaderPersonRelaListByDeptCode(String[] filterValue,String leaderFlag){
		Session session=this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class,"op");
		criteria.createAlias("organizationInfo", "organizationInfo", Criteria.INNER_JOIN);
		criteria = criteria.add(Restrictions.in("organizationInfo.id", filterValue));
		criteria.createAlias("personInfo", "personInfo", Criteria.INNER_JOIN);
		criteria = criteria.add(Restrictions.eq("personInfo.leaderFlag", leaderFlag))
				.add(Restrictions.eq("personInfo.invalidFlag","1"));
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}
	
	/**
	 * 查询区域领导关联集合
	 * @param filterValue 区域编码regionCode
	 * @return 区域领导关联集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganPersonRelationInfo> getOrganLeaderPersonRelaListByRegionCode(String filterValue,String leaderFlag){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
		criteria = criteria.createCriteria("personInfo")
					.add(Restrictions.eq("regionCode", filterValue)).add(Restrictions.eq("leaderFlag",leaderFlag))
					.add(Restrictions.eq("invalidFlag","1"));
		List<OrganPersonRelationInfo> list = criteria.list();
		return list;
	}
	@Autowired
	  PersonInfoDao personInfoDao;

	 public List<OrganPersonRelationInfo> getCentralizedDeploymentOrganPersonRelaListByPersonName(String filterValue)
	  {
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
	    Session session = getSession();
	    Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class);
	    criteria = criteria.createCriteria("personInfo").add(
	      Restrictions.or(Restrictions.like("userEname", filterValue), 
	      Restrictions.like("userCname", filterValue)));

	    List<String> centralizedDeploymentRegionCode = new ArrayList<String>();
	    Map<String ,String> map =CodeSwitchUtil.getCentralizedMap();
		for (String key : map.keySet()) {
			centralizedDeploymentRegionCode.add(key);
		}
		//modify by lijun 2014-11-23 上海市本级就从集中部署里取，否则就只查本级。
		if(Constants.SH_REGION_CDOE.equals(person.getRegionCode())){
			criteria = criteria.add(Restrictions.in("regionCode", centralizedDeploymentRegionCode));
		}else{
			criteria = criteria.add(Restrictions.eq("regionCode", person.getRegionCode()));
		}
	    return criteria.list();
	  }
	 
	/**
	 * 查询领导用户——本部门、办公厅部门领导和委领导。
	 * @author hjh
	 * @param isModifyInstruction 是否为修改批示
	 * @return
	 * 2014-6-29下午08:49:04
	 */
	public List<OrganPersonRelationInfo> listLeaders(String isModifyInstruction){
		Session session = this.getSession();
		String regionCode = Struts2Utils.getSessionAttribute("curRegionCode").toString();
		StringBuffer bf = new StringBuffer();
		bf.append("from OrganPersonRelationInfo where 1=1 ");
		//如果不是修改批示，并且是上海市，就添加部门或办公厅限制
		if((isModifyInstruction==null||!isModifyInstruction.equals("true"))&&Constants.SH_REGION_CDOE.equals(regionCode)){
			bf.append(" and (organizationInfo.id =:deptid"); //本部门id
			bf.append(" or organizationInfo.id =:deptid2 )");//办公厅id
		}
		bf.append(" and personInfo.invalidFlag =1");//有效
		bf.append(" and ( personInfo.leaderFlag =:leaderFlag or personInfo.leaderFlag =:leaderFlagW )");//领导(w:表示委领导)
		bf.append(" and personInfo.regionCode =:regionCode");//领导
		bf.append(" order by personInfo.queryCount desc ");//
		Query query = session.createQuery(bf.toString());
		query.setString("leaderFlag",LeaderFlag.LEADER.toString());
		query.setString("leaderFlagW",LeaderFlag.LEADER_MAIN.toString());
		if((isModifyInstruction==null||!isModifyInstruction.equals("true"))&&Constants.SH_REGION_CDOE.equals(regionCode)){
			query.setString("deptid", Struts2Utils.getSessionAttribute("organizationInfoOid").toString());
			query.setString("deptid2", "310000000015"); //办公厅
		}
		query.setString("regionCode", regionCode);
		List<OrganPersonRelationInfo> list = query.list();
		return list;
	}
	
	/**
	 * 根据person信息获取对应OrganPersonRelationInfo对象
	 * @param p
	 * @return
	 * @author garview
	 * 2015年12月17日 下午6:06:35
	 */
	public OrganizationInfo getOrganizationInfo(PersonInfo p){
		Criteria criteria = this.createCriteria();
		criteria.createAlias("personInfo", "personInfo",criteria.LEFT_JOIN);
		criteria.createAlias("organizationInfo", "organizationInfo",criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("personInfo.userEname",p.getUserEname()));
		criteria.add(Restrictions.eq("personInfo.regionCode",p.getRegionCode()));
		//数据库中返回的对象数组
		List<OrganPersonRelationInfo> list = criteria.list();
		// 每个用户必然有对应部门
		Assert.isTrue(!list.isEmpty());
		return list.get(0).getOrganizationInfo();
	}
	
}