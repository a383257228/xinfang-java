package com.sinosoft.xf.system.logon.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.dao.SystemAuthorityInfoDao;
import com.sinosoft.organization.common.Encript;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoft.xf.constants.UserConstants.DeptName;
import com.sinosoft.xf.constants.UserConstants.LeaderFlag;
import com.sinosoft.xf.constants.UserConstants.RoleStr;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 *最大号信息manager
 * @date 2011-09-15
 * @author wangxx 
 */
@Service
@Transactional
public class LogonManager extends EntityManager<PersonInfo,String> {
	/**
	 * 注入用户dao
	 */
	@Autowired
	PersonInfoDao personInfoDao;
	//注入菜单权限信息dao层
	@Autowired
	AuthorityMenuDao authorityMenuDao;
	//注入用户信息业务层
	@Autowired
	PersonInfoManager personInfoManager;
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	/** 注入机构用户关系dao层类 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	//注入代码信息dao层
	@Autowired
	private SystemAuthorityInfoDao systemAuthorityInfoDao;
	
	Set<String> menuUrlSet = null;
	@Override
	protected HibernateDao<PersonInfo, String> getEntityDao() {
		return personInfoDao;
	}
	/**
	 * 根据personInfo对象信息获取对应roleStr
	 * @param personInfo
	 * @return
	 * @author garview
	 * 2015年12月17日 下午6:20:54
	 */
	public String geneRoleStr(PersonInfo personInfo){
		OrganizationInfo organ = organPersonRelationDao.getOrganizationInfo(personInfo);
		String roleStr = "userMain";
		if (organ.getOrgCname().contains(DeptName.XFS.toString())) {
			roleStr = RoleStr.XFS.toString();
		} else if (organ.getOrgCname().contains(DeptName.AGS.toString())) {
			roleStr = RoleStr.AGS.toString();
		}else {
			roleStr = RoleStr.BXS.toString();
		}
		if (personInfo.getLeaderFlag().equals(LeaderFlag.LEADER.toString())) {//领导
			roleStr = roleStr + RoleStr.LEADER.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.SECRETARY.toString())) {//秘书
			roleStr = roleStr + RoleStr.SECRETARY.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.GENERAL.toString())) {//办信员
			roleStr = roleStr + RoleStr.GENERAL.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.BUSINESS_ADMIN.toString())) {//业务管理员
			roleStr = RoleStr.BUSINESS_ADMIN.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.LEADER_MAIN.toString())) {//委领导或信访室领导
			roleStr = RoleStr.LEADER_MAIN.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.SYSTEM_ADMIN.toString())) {//系统管理员 2016-03-18 liub
			roleStr = RoleStr.SYSTEMADMIN.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.SAFE_ADMIN.toString())) {//安全管理员 2016-03-18 liub
			roleStr = RoleStr.SAFEADMIN.toString();
		} else if (personInfo.getLeaderFlag().equals(LeaderFlag.SAFE_AUDITOR.toString())) {//安全审计员 2016-03-18 liub
			roleStr = RoleStr.SAFEAUDITOR.toString();
		}else{
			roleStr = RoleStr.ADMIN.toString();
		}
		return roleStr;
	}
	/**
	 * 该方法用于通过用户英文名返回一个包含用户信息和解密后的密码信息集合
	 * @param personEname 用户英文名
	 * @return 返回一个map，key为person，value为用户对象；key为pwd时，value为明文密码信息。
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@Transactional(readOnly=true)
	public Map<String,Object> getPersonInfoMapByPersonEnameAndRegionCode(String personEname,String regionCode){
		//实例化一个要返回的map
		Map<String,Object> map = new HashMap<String,Object>();
		//查询时所需要的对象
		Criteria criteria = organPersonRelationDao.createCriteria();
		criteria.createAlias("personInfo", "personInfo",criteria.LEFT_JOIN);
		criteria.createAlias("organizationInfo", "organizationInfo",criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("personInfo.userEname",personEname));
		criteria.add(Restrictions.eq("personInfo.regionCode",regionCode.trim()));
		//数据库中返回的对象数组
		List<OrganPersonRelationInfo> list = criteria.list();
		//如果list数组内存在对象
		if(!list.isEmpty()){
			PersonInfo p = list.get(0).getPersonInfo();
			Set<OrganPersonRelationInfo> set=new HashSet<OrganPersonRelationInfo>(list);
			p.setOrganPersonRelationInfo(set);
			//将用户对象保存到map中
			map.put("personInfo", p);
			map.put("organPersonRelationInfo", list.get(0));
			//将对象的密码进行解密并保存到map中
			if(p.getUserPwd()!=null){
				map.put("pwd",Encript.decript(p.getUserPwd()));
			}
			return map;
		}else{
			return null;//为null表示没有这个对象
		}
	}
	/**
	 * 获取当前操作用户对象
	 * @return 当前操作用户对象
	 */
	@Transactional(readOnly=true)
	public PersonInfo getOnlinePersonInfo() {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		OnlineUser onlineUser = AppUtils.getOnlineUsers().get(session.getId());
		//当前操作人
		if (onlineUser != null) {
			PersonInfo person = (PersonInfo) session.getAttribute("personInfo");
			if (null == person) {
				person = personInfoManager.get(onlineUser.getUserId());
				session.setAttribute("personInfo", person);
				OrganizationInfo organ = person.getOrganPersonRelationInfo().iterator().next().getOrganizationInfo();
				session.setAttribute("deptCode", organ.getOrgCode());
				session.setAttribute("deptName", organ.getOrgCname());
				session.setAttribute("organizationInfoOid", organ.getId());
				session.setAttribute("organizationInfoCname", organ.getOrgCname());
				session.setAttribute("curRegionCode", person.getRegionCode());//当前区域编码
				session.setAttribute("curRegionName", person.getRegionName());//当前区域编码名称
				session.setAttribute("curRegionId", organ.getId());//当前区域组织机构id
			}
			return person;
		}
		return null;
	}
}

