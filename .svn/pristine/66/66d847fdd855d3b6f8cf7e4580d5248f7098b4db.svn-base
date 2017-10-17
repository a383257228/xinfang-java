/**
 * Copyright (c) sinosoft May 18 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 18 2010
 * @author shenhy
 */
package com.sinosoft.organization.manager;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.common.Encript;
import com.sinosoft.organization.common.EncryptMD5;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

/**   
 * 用户信息业务层
 * @author shenhy   
 * 
 * @date May 18 2010   
 */
@Service
@Transactional
public class PersonInfoManager extends EntityManager<PersonInfo, String>{
	/**
	 * 注入人员信息持久层
	 */
	@Autowired
	PersonInfoDao personInfoDao;
	/**
	 * 注入机构用户关系持久层
	 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	
	/**
	 * 注入机构信息dao层
	 */
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	
	protected HibernateDao<PersonInfo, String> getEntityDao() {
		return personInfoDao;
	}
	
	/**
	 * @author wangxx
	 * @createDate 2011-04-19
	 * 提供了一个通过hql返回集合的接口
	 */
	@SuppressWarnings("unchecked")
	public List<PersonInfo> find(String hql){
		List<PersonInfo> list = personInfoDao.find(hql);
		return list;
	}
	/**
	 * 保存用户信息：分为三种情况  注意加密
	 * @date 2010-12-06
	 * @author wangxx
	 * 1、新增新的用户信息
	 * 2、新增关联已有的用户信息
	 * 3、修改用户信息
	 * 当orgPersonRelaOid不为空时为修改，当personInfo.Oid为空时为新增新的用户信息
	 * @param orgPersonRelaOid  关系表oid
	 * @param personInfo  用户对象
	 * @param orgOid  组织oid
	 * @param userPosition  用户职务
	 * @return 空
	 * 2013-1-4 hxh 添加的参数limitFlag 用于权限是否仅限本人
	 */
	public void savePersonInfo(String orgPersonRelaOid,PersonInfo personInfo
			,String orgOid,String userPosition,String limitFlag){
		//获取组织对象
		OrganizationInfo organizationInfo =organizationInfoDao.get(orgOid);
		//判断是新增还是修改
		if(null!=orgPersonRelaOid&&!"".equals(orgPersonRelaOid)){
			//修改用户信息
			personInfo.setModifyDate(new Timestamp(System.currentTimeMillis()));
			if("false".equals(personInfo.getInvalidFlag())){
				personInfo.setInvalidDate(new Timestamp(System.currentTimeMillis()));
				personInfo.setInvalidFlag("2");
			}else{
				personInfo.setInvalidFlag("1");
			}
			//对密码进行md5加密
			personInfo.setMd5Pwd("{"+EncryptMD5.getMD5(personInfo.getUserPwd().getBytes())+"}");
			//对密码进行加密
			personInfo.setUserPwd(Encript.encript(personInfo.getUserPwd()));
			OrganizationInfo org = organizationInfo;
			while(!"1".equals(org.getPurpose())){
				if(org.getOrganizationRelationInfo().iterator().hasNext()){
					org = org.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo().getOrganizationInfo();
				}else{
					logger.info("组织机构数据出现错误");
				}
			}
			personInfo.setRegionCode(org.getOrgCode());
			personInfo.setRegionName(org.getOrgCname());
			personInfoDao.save(personInfo);
			//封装组织用户关系对象信息
			OrganPersonRelationInfo orgPersonRelaInfo = organPersonRelationDao.get(orgPersonRelaOid);
			orgPersonRelaInfo.setPersonInfo(personInfo);
			orgPersonRelaInfo.setOrganizationInfo(organizationInfo);
			orgPersonRelaInfo.setUserPosition(userPosition);
			organPersonRelationDao.save(orgPersonRelaInfo);
		}else{
			//新增用户信息
			if(null!=personInfo.getId()&&!"".equals(personInfo.getId().trim())){
				//关联已存在的用户信息
				//封装用户对象
				if("false".equals(personInfo.getInvalidFlag())){
					personInfo.setInvalidDate(new Timestamp(System.currentTimeMillis()));
					personInfo.setInvalidFlag("2");
				}else{
					personInfo.setInvalidFlag("1");
				}
				personInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				//对密码进行md5加密
				personInfo.setMd5Pwd("{"+EncryptMD5.getMD5(personInfo.getUserPwd().getBytes())+"}");
				//对密码进行加密
				personInfo.setUserPwd(Encript.encript(personInfo.getUserPwd()));
				OrganizationInfo org = organizationInfo;
				while(!"1".equals(org.getPurpose())){
					if(org.getOrganizationRelationInfo().iterator().hasNext()){
						org = org.getOrganizationRelationInfo().iterator().next()
								.getOrganizationRelationInfo().getOrganizationInfo();
					}else{
						logger.info("组织机构数据出现错误");
					}
				}
				personInfo.setRegionCode(org.getOrgCode());
				personInfo.setRegionName(org.getOrgCname());
				//封装组织用户关系对象信息
				OrganPersonRelationInfo orgPersonRelaInfo = new OrganPersonRelationInfo();
				orgPersonRelaInfo.setPersonInfo(personInfo);
				orgPersonRelaInfo.setOrganizationInfo(organizationInfo);
				orgPersonRelaInfo.setUserPosition(userPosition);
//				if("false".equals(limitFlag)){
//					orgPersonRelaInfo.setLimitFlag("0");
//				}else{
//					orgPersonRelaInfo.setLimitFlag("1");
//				}
				organPersonRelationDao.save(orgPersonRelaInfo);
			}else{
				//新增新的用户信息
				//封装用户对象
				personInfo.setId(null);
				if("false".equals(personInfo.getInvalidFlag())){
					personInfo.setInvalidDate(new Timestamp(System.currentTimeMillis()));
					personInfo.setInvalidFlag("2");
				}else{
					personInfo.setInvalidFlag("1");
				}
				personInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				//对密码进行md5加密
				personInfo.setMd5Pwd("{"+EncryptMD5.getMD5(personInfo.getUserPwd().getBytes())+"}");
				//对密码进行加密
				personInfo.setUserPwd(Encript.encript(personInfo.getUserPwd()));
				OrganizationInfo org = organizationInfo;
				while(!"1".equals(org.getPurpose())){
					if(org.getOrganizationRelationInfo().iterator().hasNext()){
						org = org.getOrganizationRelationInfo().iterator().next()
								.getOrganizationRelationInfo().getOrganizationInfo();
					}else{
						logger.info("组织机构数据出现错误");
					}
				}
				personInfo.setRegionCode(org.getOrgCode());
				personInfo.setRegionName(org.getOrgCname());
				personInfoDao.save(personInfo);
				//封装组织用户关系对象信息
				OrganPersonRelationInfo orgPersonRelaInfo = new OrganPersonRelationInfo();
				orgPersonRelaInfo.setPersonInfo(personInfo);
				orgPersonRelaInfo.setOrganizationInfo(organizationInfo);
				orgPersonRelaInfo.setUserPosition(userPosition);
				organPersonRelationDao.save(orgPersonRelaInfo);
			}
		}
	}
	/**
	 * 验证用户属性是否已经存在
	 * @date 2010-11-27
	 * @author wangxx 
	 * 该方法只适合用于添加时验证，需要考虑的情况有：
	 * 1、直接添加新的用户
	 * 		由于是新用户需要在验证是否已经在关系表中存在这样的personInfo对象，
	 * 		如果不存在说明没有这样的personInfo对象或者存在但是已经被删除
	 * 		这样的属性是可以再被使用的。
	 * 2、将已有的用户关联到别的部门下，主要根据是否有用户oid决定
	 * 		需要判断关联之后的该属性是否被用户修改
	 * 			①如果没有被修改，直接返回false
	 * 			②如果被修改了，就需要判断修改后的是否已经存在
	 * @param filterTxt  需要验证的属性
	 * @param filterValue  用户输入的值
	 * @param personOid 用户基本信息oid
	 * @return 返回true时表示重复，返回false时表示不重复
	 */
	@Transactional(readOnly=true)
	public String judgePerson(String filterTxt,String filterValue,String personOid){
		String flag = "true";
		//判断是新增的用户还是关联已有的用户
		if(null!=personOid&&!"".equals(personOid.trim())){
			//关联已有的用户
			//判断该属性是否被修改
			List<PersonInfo> list = personInfoDao
				.getPersonInfoListByOidAndPersonProperty(personOid
				, filterTxt, filterValue);
			if(list.size()==0){
				//已经被修改
				List<OrganPersonRelationInfo> listOPRI = organPersonRelationDao
					.getOrganPersonRelaListByPerson(filterTxt, filterValue);
				if(listOPRI.size()==0){
					flag="false";
				}else{
					flag="true";
				}
			}else{
				//说明没有被修改
				flag="false";
			}
		}else{
			//新的用户
			List<OrganPersonRelationInfo> list = organPersonRelationDao
				.getOrganPersonRelaListByPerson(filterTxt, filterValue);
			if(list.size()==0){
				flag="false";
			}
		}
		return flag;
	}
	/**
	 * 修改用户信息时，修改用户部门判断该用户是否已经关联到该部门下
	 * @date 2010-11-27
	 * @author wangxx
	 * 首先用关系oid，部门oid，用户oid是否能确定一条关系数据对象，如果能，说明没有修改用户的部门
	 * 如果不能，说明修改了用户部门，需要用部门oid和用户oid确定是否已经建立了关系，如果建立关系则返回true
	 * 如果没有关系则返回false
	 * @param organPersonOid  关系表oid
	 * @param organOid 部门oid
	 * @param personOid 用户oid
	 * @return  返回true表示已存在，返回false表示不存在，可以建立关系
	 */
	@Transactional(readOnly=true)
	public String judgeOrganPerson(String organPersonOid,String organOid,String personOid){
		String flag = "true";
		List<OrganPersonRelationInfo> list = organPersonRelationDao
			.getOrganPersonRelationInfoByOrganPersonOidAndOrganOidAndPersonOid(
			organPersonOid, organOid, personOid);
		//判断是否修改了用户部门信息
		if(list.size()>0){
			//说明没有修改
			flag = "false";
		}else{
			list = organPersonRelationDao
				.getOrganPersonRelationInfoByOrganPersonOidAndOrganOidAndPersonOid(
						"", organOid, personOid);
			//判断是否重复
			if(list.size()>0){
				//说明重复不能使用返回true
				flag = "true";
			}else{
				//说明不重复，可以使用
				flag = "false";
			}
		}
		return flag;
	}

	/**
	 * 判断复制用户时同一个部门是否有重复用户
	 * @date May 31 2010
	 * @author shenhy
	 * @param organizationInfoId 机构主键
	 * @param personOid 人员主键           
	 * @return true为存在，false为不存在
	 */
	@Transactional(readOnly=true)
	public String judgePersonOrganRepeat(String organizationInfoOid,String personOid){
		//方法返回值
		String returnString="true";
		int result=1;
		result = personInfoDao.judgePersonOrganRepeat(organizationInfoOid,personOid);
		//如果result结果为0，则返回"false"
		if(result==0){
			returnString = "false";
		}
		//返回
		return returnString;
	}
	/**
	 * 该方法用于通过用户英文名返回一个包含用户信息和解密后的密码信息集合
	 * @param personEname 用户英文名
	 * @return 返回一个map，key为person，value为用户对象；key为pwd时，value为明文密码信息。
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getPersonInfoMapByPersonEname(String personEname){
		//实例化一个要返回的map
		Map<String,Object> map = new HashMap<String,Object>();
		//查询时所需要的对象
		Criteria criteria = personInfoDao.createCriteria();
		criteria.add(Restrictions.eq("userEname",personEname));
		//数据库中返回的对象数组
		List<PersonInfo> list = criteria.list();
		//如果list数组内存在对象
		if(list.size()>0){
			PersonInfo p = list.get(0);
			//将用户对象保存到map中
			map.put("person", p);
			//将对象的密码进行解密并保存到map中
			if(p.getUserPwd()!=null){
				map.put("pwd", Encript.decript(p.getUserPwd()));
			}
			return map;
		}else{
			return null;//为null表示没有这个对象
		}
	}
	/**
	 * 该方法用于通过用户英文名返回一个密码经过解密的PersonInfo对象
	 * @date 2010-06-07
	 * @param personEname 用户基本信息英文名
	 * @return 用户对象
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Deprecated
	public PersonInfo getPersonInfoByPersonEnameIm(String personEname){
		//生成一个要返回的PersonInfo对象
		//定义一个接受数据库信息的对象
		PersonInfo p=null;
		//查询时所需要的对象
		Criteria criteria = personInfoDao.createCriteria();
		criteria.add(Restrictions.eq("userEname",personEname));
		//数据库中返回的对象数组
		List<PersonInfo> list = criteria.list();
		//如果list数组内存在对象
		if(list.size()>0){
			//获得这个对象
			p=list.get(0);
			//将对象的密码进行解密处理
			if(p.getUserPwd()!=null)
				p.setUserPwd(Encript.decript(p.getUserPwd()));
			return p;
		}else{
			return null;//为null表示没有这个对象
		}
	}
	/**
	 * 通过多个用户oid返回他们对应的的中文名
	 * @param oids 用户oid，多个oid之间用“；”分隔
	 * @return 用户的中文名字符串，每个中文名之间用“；”分隔
	 */
	@Transactional(readOnly=true)
	public String getPersonCnameByOid(String oids){
		oids = oids.substring(0,oids.length()-1);
		String[] oid = oids.split(";");
		String cnames = "";
		for (int i = 0; i < oid.length; i++) {
			PersonInfo rePersonInfo =organPersonRelationDao.get(oid[i]).getPersonInfo();
			cnames += rePersonInfo.getUserCname()+";";
		}
		return cnames;
	}
}
