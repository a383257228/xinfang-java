package com.sinosoft.authority.manager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDeptRoleDao;
import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.authority.dao.AuthorityUserRelationDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRelation;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.menu.dao.MenuItemInfoDao;
import com.sinosoft.menu.dao.SystemInfoDao;
import com.sinosoft.menu.domain.MenuItemInfo;
import com.sinosoft.menu.domain.SystemInfo;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**
 * 菜单权限Manager层
 * @author sunzhe
 * Create 2010-08-10
 */
@Service
@Transactional
public class AuthorityMenuManager extends EntityManager<AuthorityMenu, String>{
	private final Logger log = LoggerFactory.getLogger(AuthorityMenuManager.class);
	//注入菜单权限信息dao层
	@Autowired
	AuthorityMenuDao authorityMenuDao;
	//注入人员角色关系业务层
	@Autowired
	AuthorityUserRoleManager authorityUserRoleManager;
	//注入系统信息dao层
	@Autowired
	SystemInfoDao systemInfoDao;
	//注入菜单信息dao层
	@Autowired
	MenuItemInfoDao menuItemInfoDao;
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	//注入部门角色信息dao层
	@Autowired
	AuthorityDeptRoleDao authorityDeptRoleDao;
	
	//注入人员功能菜单信息dao层，非角色下功能菜单
	@Autowired
	AuthorityUserRelationDao authorityUserRelationDao;
	
	@Override
	protected HibernateDao<AuthorityMenu, String> getEntityDao() {
		return authorityMenuDao;
	}
	private Properties menuPropertie;
	
	public AuthorityMenuManager(){
//		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("menu.properties");
//		menuPropertie = new Properties();
//		try {
//			menuPropertie.load(inputStream);
//			if(inputStream != null ){
//				inputStream.close();
//				inputStream = null;
//			}
//		} catch (IOException e) {
//			if(log.isErrorEnabled()){
//				log.error("加载菜单配置文件有误！");
//			}
//		}
	}
	/**
	 * @see 通过用户菜单权限，渲染系统菜单，该处主要用于拼接第一级菜单并将菜单信息返回
	 * @date 2011-05-03
	 * @author wangxx
	 * 用途：
	 * 	1、目录系统
	 * @param personInfo 登录人员信息
	 * @param systemId 当前系统ID，根据此系统ID，生成人员功能菜单
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuJson(PersonInfo personInfo,String systemId) throws ParseException{
		StringBuffer json = new StringBuffer("{success:true,menus:[");
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(personInfo.getId());
		Set<OrganPersonRelationInfo> set = personInfo.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		String roleOid = "";
		//增加人员菜单权限
		for (int i = 0; i < roleList.size(); i++) {
			AuthorityUserRole nAuthorityUserRole=roleList.get(i);
			AuthorityRole role = nAuthorityUserRole.getRole();
			//判断权限是否过期
			if(role.getExpireDate()==null||role.getExpireDate().toString().equals("")
					||compareDate(role.getExpireDate().toString())<0){
				roleOid += "'"+role.getId()+"',";
			}
		}
		//增加人员所在部门菜单权限
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.
			findBy("organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				AuthorityRole role = deptRole.getRole();
				//判断权限是否过期
				if((role.getExpireDate()==null||role.getExpireDate().toString().equals("")
						||compareDate(role.getExpireDate().toString())<0)&&
						"1".equals(role.getInvalidFlag())){
					roleOid += "'"+role.getId()+"',";
				}
			}
		}
		if(roleOid.endsWith(","))
			roleOid = roleOid.substring(0,roleOid.length()-1);
		//拼装系统菜单json
		List<Object[]> list = new ArrayList<Object[]>();
		if(!roleOid.equals("")){
			list = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii where " +
					"am.menu=mii.id and am.role.id in ("+roleOid+") and am.parentMenu " +
					"is not null and mii.menuItemInfo is null " +
					" and mii.systemInfo.id=?  order by mii.menuOrder",systemId);
			
		}
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		//查询用户非角色下的功能菜单权限，该权限只与用户绑定，不与角色绑定
		List<AuthorityUserRelation> userMenuList = authorityUserRelationDao.find("From AuthorityUserRelation Where " +
				"person=? and menu in (Select id From MenuItemInfo " +
					" Where systemInfo.id=? and menuItemInfo is null ) ", personInfo.getId(),systemId);
		for(AuthorityUserRelation aur : userMenuList){
			AuthorityMenu am = new AuthorityMenu();
			am.setMenu(aur.getMenu());
			am.setParentMenu(aur.getParentMenu());
			Object[] obj = {am};
			list.add(obj);
		}
		
		for (int i = 0; i < list.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) list.get(i)[0];
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		boolean firstFlag = false;
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(firstFlag){
					json.append(",");
				}
				json.append("{xtype:'button',text:'&nbsp;&nbsp;&nbsp;&nbsp;"+menuItem.getMenuCname()+"'");
				if(menuItem.getChildrens().size()>0){
					json.append(",menu:{items:[");
					json.append(getMenuJson(menuItem.getId(), roleOid,systemId,personInfo));
					json.append("]}");
				}else{
					json.append(",listeners:{'click':accordion");
					json.append("},url:'");
					json.append(menuItem.getMenuUrl());
					json.append("'");
				}

				json.append("}");
				//功能菜单详细信息
				firstFlag=true;
			}
		}
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * @see 通过用户菜单权限，渲染系统菜单,该处主要用于拼接第二级及以下的菜单
	 * @date 2011-05-03
	 * @author wangxx
	 * 用途：
	 * 	1、目录系统
	 * @param parentMenu 父菜单ID，用于查找下级菜单
	 * @param roleOid 角色ID，可以是多个，查找该角色下的功能菜单
	 * @param systemId 系统ID，如果是多系统下，只显示当前登录系统的菜单
	 * @param personInfo 人员信息，当前登录用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuJson(String parentMenu,String roleOid,String systemId,PersonInfo personInfo){
		StringBuffer json = new StringBuffer("");
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("parentMenu", parentMenu);
		values.put("systemId", systemId);
		List<Object[]> cList = new ArrayList<Object[]>();
		//根据角色查找功能菜单权限信息
		if(!roleOid.equals(""))
			cList = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii " +
					"where am.parentMenu ='"+parentMenu+"' " +
					"and am.menu=mii.id and am.role.id in ("+roleOid+") " +
				"and mii.systemInfo.id=?  order by mii.menuOrder",systemId);

		//查询用户非角色下的功能菜单权限，该权限只与用户绑定，不与角色绑定
		List<AuthorityUserRelation> userMenuList = authorityUserRelationDao.find("From AuthorityUserRelation Where " +
				"person=? and parentMenu =? and menu in (Select id From MenuItemInfo " +
					" Where systemInfo.id=?) ", personInfo.getId(),parentMenu,systemId);
		for(AuthorityUserRelation aur : userMenuList){
			AuthorityMenu am = new AuthorityMenu();
			am.setMenu(aur.getMenu());
			am.setParentMenu(aur.getParentMenu());
			Object[] obj = {am};
			cList.add(obj);
		}
		//将已选择过的功能菜单信息提取出来
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		for (int i = 0; i < cList.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) cList.get(i)[0];
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		//拼接数据
		boolean firstFlag = false;
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(firstFlag){
					json.append(",");
				}
				json.append("{text:'");
				json.append(menuItem.getMenuCname());
				json.append("',listeners:{'click':accordion");
				json.append("},url:'");
				json.append(menuItem.getMenuUrl());
				json.append("'}");
				firstFlag = true;
			}
		}
		return json.toString();
	}
	/**
	 * 查询角色功能菜单信息，返回所有结果
	 * @param propertyName　查询字段
	 * @param Value　查询值
	 * @return　权限功能菜单信息集合
	 */
	@Transactional(readOnly=true)
	public List<AuthorityMenu> findBy(String propertyName,Object Value){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("property", Value);
		List<AuthorityMenu> cList=authorityMenuDao.find("from AuthorityMenu where "+propertyName+"=:property " +
				"and menu in (Select id From MenuItemInfo ) ",values);
		return cList;
	}
	/**
	 * 查询角色功能菜单信息，只返回有效信息
	 * @param propertyName　查询字段
	 * @param Value　查询值
	 * @return　权限功能菜单信息集合
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<AuthorityMenu> findInvalidBy(String propertyName,Object Value) throws ParseException{
		ArrayList<AuthorityMenu> result = new ArrayList<AuthorityMenu>();
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("property", Value);
		List<AuthorityMenu> cList=authorityMenuDao.find("from AuthorityMenu where" +
					propertyName+"=:property and menu in (Select id From MenuItemInfo ) ",values);
		for(AuthorityMenu am : cList){
			AuthorityRole role = am.getRole();
		//判断权限是否过期
		if((role.getExpireDate()==null||role.getExpireDate().toString().equals("")
				||compareDate(role.getExpireDate().toString())<0)&&"1".equals(role.getInvalidFlag())){
			result.add(am);
		}
		}
		return result;
	}
	/**
	 * 框架中查询方法
	 * @param hql 查询语句
	 * @param values　查询值
	 * @return　菜单角色集合
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<AuthorityMenu> find(String hql,Object... values){
		return authorityMenuDao.find(hql, values);
	}
	
	/**
	 * 通过用户拼接其拥有权限的菜单,用于信访系统
	 * 用途：
	 * 1、用于信访系统
	 * 2、重新定义了菜单样式
	 * @param person 当前用户
	 * @return　菜单json字符串
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuWithAuthorityByPetition(PersonInfo person) throws Exception{
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		
		String roleOid = "";
		//增加人员菜单权限
		for (int i = 0; i < roleList.size(); i++) {
			AuthorityUserRole nAuthorityUserRole=roleList.get(i);
			AuthorityRole role = nAuthorityUserRole.getRole();
			//判断权限是否过期
			if(role.getExpireDate()==null||role.getExpireDate().toString().equals("")
					||compareDate(role.getExpireDate().toString())<0){
				roleOid += "'"+role.getId()+"',";
			}
		}
		//增加人员所在部门菜单权限
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			/*List<AuthorityDeptRole> deptList = authorityDeptRoleDao.findBy("organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				AuthorityRole role = deptRole.getRole();
				//判断权限是否过期
				if((role.getExpireDate()==null||role.getExpireDate().toString().equals("")
						||compareDate(role.getExpireDate().toString())<0)&&"1".equals(role.getInvalidFlag())){
					roleOid += "'"+role.getId()+"',";
				}
			}*/
		}
		if(roleOid.endsWith(","))
			roleOid = roleOid.substring(0,roleOid.length()-1);
		//拼装系统菜单json
		StringBuffer treeJson = new StringBuffer("{'info':[");
		List<Object[]> list = new ArrayList<Object[]>();
		if(!roleOid.equals("")){
			list = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii" +
					" where am.menu=mii.id and am.role.id in ("+roleOid+") " +
					" and am.parentMenu is not null and mii.menuItemInfo is null " +
					" order by mii.menuOrder");
		}
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		for (int i = 0; i < list.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) list.get(i)[0];
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(i>0){
					treeJson.append(",");
				}
				//功能菜单详细信息
				treeJson.append("{'allowChildren':true,'allowDelete':true,'allowEdit':true,'children':[");
				String json = getMenuItemInfoById4Petition(menuItem.getId(),roleOid);
				treeJson.append(json);
				treeJson.append("],'descn':'menu','draggable':true,");
				//菜单图标
				treeJson.append("'iconCls':'"+menuItem.getMenuIcon()+"',");
				//菜单ID
				treeJson.append("'id':'"+authorityMenu.getId()+"'," );
				if(json.equals("")){
					treeJson.append("'leaf':true,");
					treeJson.append("'root':false,");
				}else{
					treeJson.append("'leaf':false,");
					treeJson.append("'root':true,");
				}
				//菜单名称
				String menuCname = menuItem.getMenuCname();
				treeJson.append("'name':'"+menuCname+"','parentId':0,'qtip':'',");
				treeJson.append("'text':'"+menuCname+"',");
				//排序值
				treeJson.append("'theSort':'"+menuItem.getMenuOrder()+"',");
				if(menuItem.getMenuUrl()==null){
					treeJson.append("'url':''}");
				}else{
					treeJson.append("'url':'"+menuItem.getMenuUrl()+"'}");
				}
			}
		}
		treeJson.append("],'msg':'已经登陆','success':true}");
		return treeJson.toString();
	}
	
	/**
	 * 通过用户拼接其拥有权限的菜单
	 * @param person 当前用户
	 * @return　菜单json字符串
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuWithAuthority(PersonInfo person) throws Exception{
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		
		String roleOid = "";
		//增加人员菜单权限
		for (int i = 0; i < roleList.size(); i++) {
			AuthorityUserRole nAuthorityUserRole=roleList.get(i);
			AuthorityRole role = nAuthorityUserRole.getRole();
			//判断权限是否过期
			if(role.getExpireDate()==null||role.getExpireDate().toString().equals("")
					||compareDate(role.getExpireDate().toString())<0){
				roleOid += "'"+role.getId()+"',";
			}
		}
		//增加人员所在部门菜单权限
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.findBy("organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				AuthorityRole role = deptRole.getRole();
				//判断权限是否过期
				if((role.getExpireDate()==null||role.getExpireDate().toString().equals("")
						||compareDate(role.getExpireDate().toString())<0)&&"1".equals(role.getInvalidFlag())){
					roleOid += "'"+role.getId()+"',";
				}
			}
		}
		if(roleOid.endsWith(","))
			roleOid = roleOid.substring(0,roleOid.length()-1);
		//拼装系统菜单json
		StringBuffer treeJson = new StringBuffer("{'info':[");
		List<Object[]> list = new ArrayList<Object[]>();
		if(!roleOid.equals("")){
			list = authorityMenuDao.find("from  AuthorityMenu am , MenuItemInfo mii " +
					"where am.menu=mii.id and am.role.id in ("+roleOid+") " +
					"and am.parentMenu is not null and mii.menuItemInfo is null " +
					"order by mii.menuOrder");
		}
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		for (int i = 0; i < list.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) list.get(i)[0];
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(i>0){
					treeJson.append(",");
				}
				//功能菜单详细信息
				treeJson.append("{'allowChildren':true,'allowDelete':true,'allowEdit':true,'children':[");
				String json = getMenuItemInfoByMenuItemInfoId(menuItem.getId(),roleOid);
				treeJson.append(json);
				treeJson.append("],'descn':'menu','draggable':true,");
				//菜单图标
				treeJson.append("'iconCls':'"+menuItem.getMenuIcon()+"',");
				//菜单ID
				treeJson.append("'id':'"+authorityMenu.getId()+"'," );
				if(json.equals("")){
					treeJson.append("'leaf':true,");
					treeJson.append("'root':false,");
				}else{
					treeJson.append("'leaf':false,");
					treeJson.append("'root':true,");
				}
				//菜单名称
				treeJson.append("'name':'"+menuItem.getMenuCname()+"','parentId':0,'qtip':'',");
				treeJson.append("'text':'"+menuItem.getMenuCname()+"',");
				//排序值
				treeJson.append("'theSort':'"+menuItem.getMenuOrder()+"',");
				if(menuItem.getMenuUrl()==null){
					treeJson.append("'url':''}");
				}else{
					treeJson.append("'url':'"+menuItem.getMenuUrl()+"'}");
				}
			}
		}
		treeJson.append("],'msg':'已经登陆','success':true}");
		return treeJson.toString();
	}

	/**
	 * 通过角色拼接其拥有权限的菜单
	 * @param roleId 角色ID
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuWithRole(String roleId) throws ParseException{	
		String roleOid = "'"+roleId+"'";
		StringBuffer treeJson = new StringBuffer("{'info':[");
		List<AuthorityMenu> list = new ArrayList<AuthorityMenu>();
		//关联菜单表查询，防止菜单被删除，并加上排序
		List<Object[]> objList = new ArrayList<Object[]>();
		//如果角色ID不为空，就将角色下全部功能菜单信息查出来
		if(!roleOid.equals("")){
			objList = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii where " +
					"am.menu=mii.id and am.role.id in ("+roleOid+") and mii.menuItemInfo is null " +
					"and am.parentMenu is not null order by mii.menuOrder ");
			//将权限菜单对象放入集合中
			for(Object[] obj : objList){
				list.add((AuthorityMenu) obj[0]);
			}
		}
//		for(int i=0;i<list.size();i++){
//			
//			AuthorityRole role = list.get(i).getRole();
//			//判断权限是否过期
//			if((role.getExpireDate()!=null&&!role.getExpireDate().toString().equals("")
//					&&compareDate(role.getExpireDate().toString())>0)||!"1".equals(role.getInvalidFlag())){
//				list.remove(i);
//			}
//		}
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		for (int i = 0; i < list.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) list.get(i);
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(i>0){
					treeJson.append(",");
				}
				treeJson.append("{'allowChildren':true,'allowDelete':true,'allowEdit':true,'children':[");
				//功能菜单详细信息
				String json = getMenuItemInfoByMenuItemInfoId(menuItem.getId(),roleOid);
				treeJson.append(json);
				treeJson.append("],'descn':'menu','draggable':true,");
				//菜单图标
				treeJson.append("'iconCls':'"+menuItem.getMenuIcon()+"',");
				//菜单ID
				treeJson.append("'id':'"+authorityMenu.getId()+"'," );
				if(json.equals("")){
					treeJson.append("'leaf':true,");
					treeJson.append("'root':false,");
				}else{
					treeJson.append("'leaf':false,");
					treeJson.append("'root':true,");
				}
				//菜单中文名
				treeJson.append("'name':'"+menuItem.getMenuCname()+"','parentId':0,'qtip':'',");
				treeJson.append("'text':'"+menuItem.getMenuCname()+"',");
				treeJson.append("'ename':'"+menuItem.getMenuEname()+"',");
				//排序值
				treeJson.append("'theSort':'"+menuItem.getMenuOrder()+"',");
				if(menuItem.getMenuUrl()==null){
					treeJson.append("'url':''}");
				}else{
					treeJson.append("'url':'"+menuItem.getMenuUrl()+"'}");
				}
			}
		}
		treeJson.append("],'msg':'已经登陆','success':true}");
		return treeJson.toString();
	}
	
	/**
	 * 通过角色与菜单父节点 拼装树形展示所需要的数据
	 * @param parentMenu 父菜单
	 * @param roleOid 角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuItemInfoByMenuItemInfoId(String parentMenu,String roleOid){
		StringBuffer treeJson = new StringBuffer("");
		List<Object[]> objList = new ArrayList<Object[]>();
		//根据角色，查询所有功能菜单信息
		List<AuthorityMenu> cList= new ArrayList<AuthorityMenu>();
		//关联菜单表查询，防止菜单被删除，并加上排序
		objList = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii " +
				"where am.menu=mii.id and am.parentMenu =? and am.role.id in ("+roleOid+") " +
				" order by mii.menuOrder ",parentMenu);
		//将权限菜单对象放入list中
		for(Object[] obj : objList){
			cList.add((AuthorityMenu) obj[0]);
		}
		//将已选择过的功能菜单信息提取出来
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		for (int i = 0; i < cList.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) cList.get(i);
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(i>0){
					treeJson.append(",");
				}
				treeJson.append("{'allowChildren':true,'allowDelete':true,'allowEdit':true,'children':[");
				//拼装功能菜单详细信息
				String json = getMenuItemInfoByMenuItemInfoId(menuItem.getId(),roleOid);
				treeJson.append(json);
				treeJson.append("],'descn':'menu','draggable':true,");
				//菜单图标
				treeJson.append("'iconCls':'"+menuItem.getMenuIcon()+"',");
				//菜单ID
				treeJson.append("'id':'"+authorityMenu.getId()+"'," );
				if(json.equals("")){
					treeJson.append("'leaf':true,");
					treeJson.append("'root':false,");
				}else{
					treeJson.append("'leaf':false,");
					treeJson.append("'root':true,");
				}
				//菜单中文名称
				treeJson.append("'name':'"+menuItem.getMenuCname()+"','parentId':0,'qtip':'',");
				treeJson.append("'text':'"+menuItem.getMenuCname()+"',");
				//菜单英文名称
				treeJson.append("'ename':'"+menuItem.getMenuEname()+"',");
				//排序值
				treeJson.append("'theSort':'"+menuItem.getMenuOrder()+"',");
				if(menuItem.getMenuUrl()==null){
					treeJson.append("'url':''}");
				}else{
					treeJson.append("'url':'"+menuItem.getMenuUrl()+"'}");
				}
			}
		}
		return treeJson.toString();
	}
	/**
	 * 获取角色对应菜单的javascript字符串
	 * 
	 * @author zhulin
	 * @date 2016-04-19
	 * @param roids 权限id串
	 * @return 角色对应菜单的javascript字符串
	 * @throws IOException
	 */
	@Transactional(readOnly=true)
	public final String getMenuJSStr(String roids) throws IOException {
		HashMap<String,String> map = getMenuMap(roids);
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			sb.append(map.get(key));
		}
		return sb.toString();
	}
	/**
	 * 根据角色对象获取各个功能菜单对应的js map
	 * 
	 * @author zhulin
	 * @date 2016-04-20
	 * @param roids 角色id串
	 * @return js文件map，key为菜单名称，value为菜单需要的js文件
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getMenuMap(String roids) {
		//使用hashmap为了进行排重
		HashMap<String,String> map = new HashMap<String,String>();
		List<AuthorityMenu> menus = authorityMenuDao.createCriteria(Restrictions.in("role.id", roids.split(","))).list();
		String menuEName = null;
		String menuENameJSStr = null;
		for(AuthorityMenu menu : menus){
			MenuItemInfo item = menuItemInfoDao.findByUnique("id", menu.getMenu());
			if(item != null){
				menuEName = item.getMenuEname();
				menuENameJSStr = null;
				menuENameJSStr = menuPropertie.getProperty(menuEName);
				if(menuENameJSStr != null && !"".equals(menuENameJSStr.trim())){
					map.put(menuEName, menuENameJSStr);
				}
			}
		}
		return map;
	}
	/**
	 * 通过角色与菜单父节点 拼装树形展示所需要的数据
	 * 信访系统使用,拼装的json采用了新样式,
	 * 其他系统如想改变菜单样式也可以使用
	 * @param parentMenu 父菜单
	 * @param roleOid 角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	private String getMenuItemInfoById4Petition(String parentMenu,String roleOid){
		StringBuffer treeJson = new StringBuffer("");
		List<Object[]> objList = new ArrayList<Object[]>();
		//根据角色，查询所有功能菜单信息
		List<AuthorityMenu> cList= new ArrayList<AuthorityMenu>();
		//关联菜单表查询，防止菜单被删除，并加上排序
		objList = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii " +
				"where am.menu=mii.id and am.parentMenu =? and am.role.id in ("+roleOid+") " +
				" order by mii.menuOrder ",parentMenu);
		//将权限菜单对象放入list中
		for(Object[] obj : objList){
			cList.add((AuthorityMenu) obj[0]);
		}
		//将已选择过的功能菜单信息提取出来
		List<AuthorityMenu> mList = new ArrayList<AuthorityMenu>();
		for (int i = 0; i < cList.size(); i++) {
			AuthorityMenu oldAuthorityMenu=(AuthorityMenu) cList.get(i);
			if(i==0){
				mList.add(oldAuthorityMenu);
			}
			int k = 0;
			for (int j = 0; j < mList.size(); j++) {
				AuthorityMenu newAuthorityMenu=(AuthorityMenu) mList.get(j);
				if(oldAuthorityMenu.getMenu().equals(newAuthorityMenu.getMenu())){
					break;
				}else{
					k++;
				}
			}
			if(k==mList.size()){
				mList.add(oldAuthorityMenu);
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		for (int i = 0; i < mList.size(); i++) {
			AuthorityMenu authorityMenu = (AuthorityMenu)mList.get(i);
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			//只拼装菜单信息
			if(menuItem.getMenuType().equals("1")){
				if(i>0){
					treeJson.append(",");
				}
				treeJson.append("{'allowChildren':true,'allowDelete':true,'allowEdit':true,'children':[");
				//拼装功能菜单详细信息
				String json = getMenuItemInfoByMenuItemInfoId(menuItem.getId(),roleOid);
				treeJson.append(json);
				treeJson.append("],'descn':'menu','draggable':true,");
				//菜单图标
				treeJson.append("'cls': 'x-tree-node-menu','iconCls':'"+menuItem.getMenuIcon()+"',");
				//菜单ID
				treeJson.append("'id':'"+authorityMenu.getId()+"'," );
				if(json.equals("")){
					treeJson.append("'leaf':true,");
					treeJson.append("'root':false,");
				}else{
					treeJson.append("'leaf':false,");
					treeJson.append("'root':true,");
				}
				//菜单中文名称
				String menuCname = menuItem.getMenuCname();
				for(int x = menuCname.length();x<9;x++){
					menuCname += "　";
				}
				treeJson.append("'name':'"+menuCname+"','parentId':0,'qtip':'',");
				treeJson.append("'text':'"+menuCname+"',");
				//菜单英文名称
				treeJson.append("'ename':'"+menuItem.getMenuEname()+"',");
				//排序值
				treeJson.append("'theSort':'"+menuItem.getMenuOrder()+"',");
				if(menuItem.getMenuUrl()==null){
					treeJson.append("'url':''}");
				}else{
					treeJson.append("'url':'"+menuItem.getMenuUrl()+"'}");
				}
			}
		}
		return treeJson.toString();
	}
	/**
	 * 构建用户按钮权限串
	 * @param person 当前用户
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<String> buildUserButton(PersonInfo person) throws ParseException{
		List<AuthorityUserRole> roleList = authorityUserRoleDao.findBy("person", person.getId());
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		List<AuthorityMenu> menuList = new ArrayList<AuthorityMenu>();
		//增加人员权限
		for(AuthorityUserRole userRole : roleList){
			AuthorityRole role = userRole.getRole();
			//判断权限是否过期
			if((role.getExpireDate()==null||role.getExpireDate().toString().equals("")
					||compareDate(role.getExpireDate().toString())<0)&&"1".equals(role.getInvalidFlag())){
				List<AuthorityMenu> menuItemList = authorityMenuDao.findMenuItemByRole(role.getId(), "not null");
				if(menuItemList.size()>0){
					menuList.addAll(menuItemList);
				}
			}
		}
		//增加人员所在部门权限
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.findBy(
					"organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				AuthorityRole role = deptRole.getRole();
				if(role.getExpireDate()==null||role.getExpireDate().toString().equals("")
						||compareDate(role.getExpireDate().toString())<0){
					List<AuthorityMenu> menuItemList = authorityMenuDao.findMenuItemByRole(role.getId(), "not null");
					if(menuItemList.size()>0){
						menuList.addAll(menuItemList);
					}
				}
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		//添加所有按钮
		List<String> menus = new ArrayList<String>(); 
		for(AuthorityMenu authorityMenu : menuList){
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			if(menuItem.getMenuType().equals("2")){
				menus.add(menuItem.getMenuEname());
			}
		}
		return menus;
	}

	/**
	 * 根据角色构建按钮权限串
	 * @param roleId 角色id
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<String> buildUserButtonWithRole(String roleId) throws ParseException{
		//得到该角色下所有菜单权限
		List<AuthorityMenu> menuList = authorityMenuDao.findBy("role.id", roleId);
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		//添加所有按钮
		List<String> menus = new ArrayList<String>(); 
		for(AuthorityMenu authorityMenu : menuList){
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			if(menuItem.getMenuType().equals("2")){
				menus.add(menuItem.getMenuEname());
			}
		}
		return menus;
	}
	
	/**
	 * 构建用户可访问的url权限串
	 * @param person 当前用户
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<String> buildUserUrlAuthority(PersonInfo person) throws ParseException{
		List<String> urls = new ArrayList<String>(); 
		List<AuthorityUserRole> roleList = authorityUserRoleDao.findBy("person", person.getId());
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		List<AuthorityMenu> menuList = new ArrayList<AuthorityMenu>();
		//增加人员权限
		for(AuthorityUserRole userRole : roleList){
			AuthorityRole role = userRole.getRole();
			//判断权限是否过期
			if((role.getExpireDate()==null||role.getExpireDate().toString().equals("")
					||compareDate(role.getExpireDate().toString())<0)&&"1".equals(role.getInvalidFlag())){
				List<AuthorityMenu> menuItemList = authorityMenuDao.findMenuItemByRole(
						role.getId(), "not null");
				if(menuItemList.size()>0){
					menuList.addAll(menuItemList);
				}
				//将角色访问首页也保存在用户可访问url串中
				if(role.getVisitUrl()!=null&&!role.getVisitUrl().equals("")){
					urls.add(role.getVisitUrl());
				}
			}
		}
		//增加人员所在部门权限
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.findBy(
					"organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				AuthorityRole role = deptRole.getRole();
				if(role.getExpireDate()==null||role.getExpireDate().toString().equals("")
						||compareDate(role.getExpireDate().toString())<0){
					List<AuthorityMenu> menuItemList = authorityMenuDao.findMenuItemByRole(role.getId(), "not null");
					if(menuItemList.size()>0){
						menuList.addAll(menuItemList);
					}
					//将角色访问首页也保存在用户可访问url串中
					if(role.getVisitUrl()!=null&&!role.getVisitUrl().equals("")){
						urls.add(role.getVisitUrl());
					}
				}
			}
		}
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		//添加所有可访问的ＵＲＬ
		for(AuthorityMenu authorityMenu : menuList){
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(authorityMenu.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", authorityMenu.getMenu());
				menuMap.put(authorityMenu.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(authorityMenu.getMenu());
			}
			if(menuItem.getMenuUrl()!=null&&!menuItem.getMenuUrl().equals("")){
				urls.add(menuItem.getMenuUrl());
			}
		}
		return urls;
	}
	
	/**
	 * 分级加载树
	 * wangxx
	 * 2010-08-05
	 * 用于权限功能菜单显示
	 * @param id 节点ID
	 * @param flag 是否需要复选框
	 * @param roleId 角色ID
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public String buildAuthorityMenuTree(String id,boolean flag,String roleId){
		List list = null ;
		String sysId = "";
		List<AuthorityMenu> menuList = new ArrayList<AuthorityMenu>();
		if(roleId!=null&&!roleId.equals("")){
			//得到角色下对应的菜单对象
			menuList = authorityMenuDao.findMenuItemByRole(roleId, "");
		}
		
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(id.equals("-1")){
			Object sci;
			//初始化时查询系统定义表
			list = systemInfoDao.find("FROM SystemInfo");
			for(int i=0;i<list.size();i++){
				sci = (SystemInfo) list.get(i);
				sysId = ((SystemInfo) sci).getId();

				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + sysId+ "'");
				//显示系统名称
				treeJson.append(",text:'" + ((SystemInfo) sci).getSystemName()+ "'");
				int size=menuItemInfoDao.buildMenuItemInfoSTree(sysId);
				//判断是否含有子节点
				if(size>0){
					treeJson.append(",leaf:false" );
				}else{
					treeJson.append(",leaf:true" );
				}
				//treeJson.append(",expanded:false");
				treeJson.append(",expanded:true");
				//如果flag为true，带有复选框
				if(flag){
					treeJson.append(",checked:false");
				}
				//treeJson.append(",iconCls:'cog'" );//显示图片

				//判断是否需要增加子级菜单
				for(AuthorityMenu am : menuList){
					if(am.getParentMenu().equals(sysId)){
						treeJson.append(",expanded:true,checked:true,allowChildren:true,children:");
						treeJson.append(buildAuthorityMenuTree(sysId,true,roleId));
						break;
					}else if(am.getMenu().equals(sysId))
						treeJson.append(",expanded:true,checked:true");
				}
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			
			treeJson.append("]");
		}else {
			Object sci;
			//根据不同系统，不同节点进行查询
			//判断是系统定义表的oid还是菜单的oid
			// 封装查询参数
			Criterion criterion = Restrictions.eq("id", id);
			// 根据部门Oid查询记录
			List<SystemInfo> booleanlist = systemInfoDao.find(criterion);
			// 判断是系统信息，还是菜单信息，一开始加载时，第一步显示系统信息
			if(booleanlist.size()==0){
				//说明该oid为菜单的oid
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("menuId", id);
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE mii.menuItemInfo.id=:menuId " +
						"and mii.id in (Select id From MenuItemInfo ) and mii.isShow='Y' order by " +
						"mii.menuItemInfo.menuOrder",values);
				for(int i=0;i<list.size();i++){
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + sysId + "'");
					//显示功能菜单中文名
					treeJson.append(",text:'" + ((MenuItemInfo) sci).getMenuCname()+ "'");
					//判断是否含有子节点
					if(((MenuItemInfo) sci).getChildrens().size()>0){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
					//treeJson.append(",expanded:false");
					treeJson.append(",expanded:true");
					//如果flag为true，带有复选框
					if(flag){
						treeJson.append(",checked:false");
					}
					//treeJson.append(",iconCls:'"+((MenuItemInfo) sci).getMenuIcon()+"'" );//显示图片
					
					//判断是否需要增加子级菜单
					for(AuthorityMenu am : menuList){
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true,allowChildren:true,children:");
							treeJson.append(buildAuthorityMenuTree(sysId,true,roleId));
							break;
						}else if(am.getMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				
				treeJson.append("]");
			}else{
				//下面拼装子菜单信息
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("systemInfoId", id);
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE " +
						"mii.systemInfo.id=:systemInfoId and menuItemInfo is null and mii.id in " +
						"(Select id From MenuItemInfo ) and mii.isShow='Y' order by mii.menuOrder",values);
				for(int i=0;i<list.size();i++){
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + sysId + "'");
					treeJson.append(",text:'" + ((MenuItemInfo) sci).getMenuCname()+ "'");
					//判断是否含有子节点
					if(((MenuItemInfo) sci).getChildrens().size()>0){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
//					treeJson.append(",expanded:false");
					treeJson.append(",expanded:true");
					//如果flag为true，带有复选框
					if(flag){
						treeJson.append(",checked:false");
					}
					//treeJson.append(",iconCls:'"+((MenuItemInfo) sci).getMenuIcon()+"'" );//显示图片
					
					//判断是否需要增加子级菜单
					for(AuthorityMenu am : menuList){
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true,allowChildren:true,children:");
							treeJson.append(buildAuthorityMenuTree(sysId,true,roleId));
							break;
						}else if(am.getMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				
				treeJson.append("]");
			}
		}
		
		
		return treeJson.toString();
	}
	/**
	 * 日期比较，看日期在当前日期前还是后
	 * @param compareDate　要比较的日期
	 * @return　距当前日期距离
	 * @throws ParseException 
	 */
	private long compareDate(String compareDate) throws ParseException{
		Calendar cpcalendar = new GregorianCalendar(); 
		Calendar cpcalendarNow = new GregorianCalendar();
		java.util.Date date = null; 
		java.util.Date today = null;
		java.text.SimpleDateFormat parseTime = null;
		DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
	    String str1 =d1.format(new Date());
		if(compareDate.length()>10){
			parseTime = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			str1=new DateTime().toString("yyyy-MM-dd hh:mm:ss");
		}else{
			parseTime = new java.text.SimpleDateFormat("yyyy-MM-dd");
		}
		//可以把数据库中查询的日期放在这里，需要格式一下 
		date = parseTime.parse(compareDate); 
		today = parseTime.parse(str1);
		cpcalendar.setTime(date);
		cpcalendarNow.setTime(today); 
		return (cpcalendarNow.getTimeInMillis() - cpcalendar.getTimeInMillis())-1;
	}
	/**
	 * @author wangxx
	 * @createDate 2011-04-22
	 * 通过用户角色权限来分级加载菜单树
	 * 步骤：
	 * 	1、由于用户可能有多个角色，角色中有可能出现重复菜单，所以需要通过set集合去重
	 * 	2、要判断是否是根节点
	 * 		如果是根节点：
	 * 			（1）遍历所有系统信息
	 * 			（2）如果集合的菜单中存在该系统信息，就将信息拼接进json中
	 * 		如果不是根节点
	 * 			（1）遍历集合中的节点信息，如果该节点的父节点信息oid与node相同
	 * 				就将该节点信息拼接到json中
	 * 			（2）用相同方式判断集合中是否含有他的下级
	 * 用途：
	 * 	1、目录系统。
	 * @param node
	 * @param flag
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public String getUserAuthorityMenuTree(String node, boolean flag, String userId){
		List list = null ;
		Set<AuthorityMenu> roleMenu = new HashSet<AuthorityMenu>();
		Set<MenuItemInfo> menu = new HashSet<MenuItemInfo>();
		//得到角色对象
		List<AuthorityUserRole> roleList = authorityUserRoleDao.findBy("person", userId);
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		//遍历角色信息
		for(AuthorityUserRole aur : roleList){
			Set<AuthorityMenu> roleMenuSet = aur.getRole().getRoleMenu();
			Iterator<AuthorityMenu> it = roleMenuSet.iterator();
			//遍历每个角色中的权限菜单信息
			while (it.hasNext()) {
				AuthorityMenu am = it.next();
				int size = menu.size();
				//菜单信息项
				MenuItemInfo menuItem = null;
				if(menuMap.get(am.getMenu())==null){
					menuItem = menuItemInfoDao.findByUnique("id", am.getMenu());
					menuMap.put(am.getMenu(),menuItem);
				}else{
					menuItem = menuMap.get(am.getMenu());
				}
				//通过菜单来辅助权限菜单去重
				menu.add(menuItem);
				if(size<menu.size()){
					roleMenu.add(am);
				}
			}
		}
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(node.equals("-1")){
			//根节点
			list = systemInfoDao.getAll();
			//遍历系统信息
			for (int i = 0; i < list.size(); i++) {
				SystemInfo si = (SystemInfo) list.get(i);
				Iterator<AuthorityMenu> it = roleMenu.iterator();
				boolean flag1 = false;
				//遍历菜单集合信息
				while (it.hasNext()) {
					AuthorityMenu am = it.next();
					//如果集合中存在系统信息，跳出循环
					if(am.getParentMenu().equals(si.getId())){
						flag1=true;
						break;
					}
				}
				if(flag1){
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + si.getId()+ "'");
					treeJson.append(",text:'" + si.getSystemName()+ "'");
					treeJson.append(",leaf:false" );
					treeJson.append(",expanded:false");
					treeJson.append(",iconCls:'cog'" );//显示图片
					treeJson.append(",expanded:false");
					treeJson.append("}");
					firstItem=false;
				}
			}
		}else{
			Iterator<AuthorityMenu> it = roleMenu.iterator();
			//遍历集合信息
			while (it.hasNext()) {
				AuthorityMenu am = it.next();
				if(am.getParentMenu().equals(node)){
					if(!firstItem){
						treeJson.append(",");
					}
					MenuItemInfo menuItem = null;
					if(menuMap.get(am.getMenu())==null){
						menuItem = menuItemInfoDao.findByUnique("id", am.getMenu());
						menuMap.put(am.getMenu(),menuItem);
					}else{
						menuItem = menuMap.get(am.getMenu());
					}
					treeJson.append("{");
					treeJson.append("id:'" + am.getMenu()+ "'");
					treeJson.append(",text:'" + menuItem.getMenuCname()+ "'");
					Iterator<AuthorityMenu> itp = roleMenu.iterator();
					boolean flag1 = false;
					while(itp.hasNext()){
						AuthorityMenu amp = itp.next();
						//判断菜单是否有下级菜单，如果有跳出循环
						if(amp.getParentMenu().equals(am.getMenu())){
							flag1 = true;
							break;
						}
					}
					if(flag1){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
					treeJson.append(",expanded:false");
					treeJson.append(",iconCls:'"+menuItem.getMenuIcon()+"'" );//显示图片
					treeJson.append("}");
					firstItem=false;
				}
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 查询已选择的用户功能权限信息，用于列表显示
	 * @param node 节点ID
	 * @param flag 是否需要复选框
	 * @param userId 用户ID
	 * @return 树形列表展示数据
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public String userAuthorityMenuTree(String node, boolean flag, String userId) {
		List list = null ;
		String sysId = "";
		Set<AuthorityMenu> roleMenu = new HashSet<AuthorityMenu>();
		
		//得到角色对象
		List<AuthorityUserRole> roleList = authorityUserRoleDao.findBy("person", userId);
		for(AuthorityUserRole aur : roleList){
			roleMenu.addAll(aur.getRole().getRoleMenu());
		}
		
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(node.equals("-1")){
			Object sci;
			//初始化时查询系统定义表
			list = systemInfoDao.getAll();
			for(int i=0;i<list.size();i++){
				Iterator<AuthorityMenu> it = roleMenu.iterator();//用于判断是否已选择了菜单项
				sci = (SystemInfo) list.get(i);
				sysId = ((SystemInfo) sci).getId();

				if(!firstItem){
					treeJson.append(",");
				}
				//拼装树的基本信息
				treeJson.append("{");
				treeJson.append(buildTreeJson(sysId,sci,"SystemInfo",flag));
				//判断是否需要增加子级菜单
				while(it.hasNext()){
					AuthorityMenu am = it.next();
					//判断是否需要拼装 下级，如果有，则将下级数据一拼显示出来
					if(am.getParentMenu().equals(sysId)){
						treeJson.append(",expanded:true,checked:true,allowChildren:true,children:");
						treeJson.append(userAuthorityMenuTree(sysId,flag,userId));
						break;
					}else if(am.getMenu().equals(sysId)){
						treeJson.append(",expanded:true,checked:true");
					}
				}
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			treeJson.append("]");
		}else {
			Object sci;
			//根据不同系统，不同节点进行查询
			//判断是系统定义表的oid还是菜单的oid
			// 封装查询参数
			Criterion criterion = Restrictions.eq("id", node);
			// 根据部门Oid查询记录
			List<SystemInfo> booleanlist = systemInfoDao.find(criterion);
			// 判断是系统信息，还是菜单信息，一开始加载时，第一步显示系统信息
			if(booleanlist.size()==0){
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("menuId", node);
				//说明该oid为菜单的oid
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE " +
						"mii.menuItemInfo.id=:menuId and mii.id in (Select id From MenuItemInfo " +
						" ) and mii.isShow='Y' order by mii.menuItemInfo.menuOrder",values);
				for(int i=0;i<list.size();i++){
					Iterator<AuthorityMenu> it = roleMenu.iterator();//用于判断是否已选择了菜单项
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",flag));
					
					//判断是否需要增加子级菜单
					while(it.hasNext()){
						AuthorityMenu am = it.next();
						//判断是否需要拼装 下级，如果有，则将下级数据一拼显示出来
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true,allowChildren:true,children:");
							treeJson.append(userAuthorityMenuTree(sysId,flag,userId));
							break;
						}else if(am.getMenu().equals(sysId))
							treeJson.append(",expanded:true,checked:true");
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				treeJson.append("]");
			}else{
				//下面拼装子功能菜单信息
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("systemCodeId", node);
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE " +
						"mii.systemInfo.id=:systemCodeId and menuItemInfo is null and mii.id in " +
						"(Select id From MenuItemInfo ) and mii.isShow='Y' order by mii.menuOrder",values);
				for(int i=0;i<list.size();i++){
					Iterator<AuthorityMenu> it = roleMenu.iterator();//用于判断是否已选择了菜单项
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",flag));
					
					//判断是否需要增加子级菜单
					while(it.hasNext()){
						AuthorityMenu am = it.next();
						//判断是否需要拼装 下级，如果有，则将下级数据一拼显示出来
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true,allowChildren:true,children:");
							treeJson.append(userAuthorityMenuTree(sysId,flag,userId));
							break;
						}else if(am.getMenu().equals(sysId)){
							treeJson.append(",expanded:true,checked:true");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				treeJson.append("]");
			}
		}
		return treeJson.toString();
	}
	
	/**
	 * 查询已选择的用户功能权限信息 
	 * 目前只用于目录系统
	 * @param node 节点ID
	 * @param flag 是否需要复选框
	 * @param userId 用户ID
	 * @return 树形列表展示数据
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public String getUserAuthorityMenuTreeChecked(String node, boolean flag, String userId,String checkedUserId) {
		List list = null ;
		String sysId = "";
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		//存放用户拥有功能菜单信息
		Set<AuthorityMenu> roleMenu = new HashSet<AuthorityMenu>();
		Set<AuthorityMenu> checkedMenu = new HashSet<AuthorityMenu>();
		//将该用户所具有的角色范围外的独立功能权限也作为功能权限进行判断
		if(checkedUserId!=null&&!"".equals(checkedUserId)){
			List<AuthorityUserRelation> userMenuList = authorityUserRelationDao.findBy("person", checkedUserId);
			for(AuthorityUserRelation aur : userMenuList){
				AuthorityMenu am = new AuthorityMenu();
				am.setMenu(aur.getMenu());
				am.setParentMenu(aur.getParentMenu());
				checkedMenu.add(am);
			}
		}
		//得到角色对象
		List<AuthorityUserRole> roleList = authorityUserRoleDao.findBy("person", userId);
		for(AuthorityUserRole aur : roleList){
			roleMenu.addAll(aur.getRole().getRoleMenu());
		}
		//处理不同角色下重复的功能菜单项
		Set<AuthorityMenu> tempSet = roleMenu;
		Iterator<AuthorityMenu> tempIt = tempSet.iterator();
		Set<String> id = new HashSet<String>();
		roleMenu = new HashSet<AuthorityMenu>();
		//将所有信息循环遍历一遍
		while(tempIt.hasNext()){
			AuthorityMenu am = tempIt.next();
			//菜单信息项
			MenuItemInfo menuItem = null;
			if(menuMap.get(am.getMenu())==null){
				menuItem = menuItemInfoDao.findByUnique("id", am.getMenu());
				menuMap.put(am.getMenu(),menuItem);
			}else{
				menuItem = menuMap.get(am.getMenu());
			}
			//去重复
			if(!id.contains(menuItem.getId().trim())){
				id.add(menuItem.getId().trim());
				roleMenu.add(am);
			}
		}
		
		
		StringBuffer treeJson = new StringBuffer("[");
		if(node.equals("-1")){
			Object sci;
			//初始化时查询系统定义表
			list = systemInfoDao.getAll();
			for(int i=0;i<list.size();i++){
				Iterator<AuthorityMenu> it = roleMenu.iterator();//用于判断是否已选择了菜单项
				Iterator<AuthorityMenu> checkedIt = checkedMenu.iterator();//用于判断需要勾选的菜单项
				sci = (SystemInfo) list.get(i);
				sysId = ((SystemInfo) sci).getId();
				//判断是否需要增加子级菜单
				while(it.hasNext()){
					AuthorityMenu am = it.next();
					//判断是否需要拼装 下级，如果有，则将下级数据一拼显示出来
					if(am.getParentMenu().equals(sysId)){
						if(!treeJson.toString().endsWith("[")){
							treeJson.append(",");
						}
						
						treeJson.append("{");
						treeJson.append(buildTreeJson(sysId,sci,"SystemInfo",flag));
						//将需要勾选的信息进行判断，如果与此菜单ID相等，则勾选上
						while(checkedIt.hasNext()){
							AuthorityMenu checked = checkedIt.next();
							if(checked.getParentMenu().equals(sysId)||checked.getMenu().equals(sysId)){
								treeJson.append(",checked:true");
								break;
							}
						}
						treeJson.append(",expanded:true,allowChildren:true,children:");
						treeJson.append(getUserAuthorityMenuTreeChecked(sysId,flag,userId,checkedUserId));
						treeJson.append("}");

						break;
					}else if(am.getMenu().equals(sysId)&&!isParent(roleMenu,sysId)){
						if(!treeJson.toString().endsWith("[")){
							treeJson.append(",");
						}
						treeJson.append("{");
						treeJson.append(buildTreeJson(sysId,sci,"SystemInfo",flag));
						//将需要勾选的信息进行判断，如果与此菜单ID相等，则勾选上
						while(checkedIt.hasNext()){
							AuthorityMenu checked = checkedIt.next();
							if(checked.getParentMenu().equals(sysId)||checked.getMenu().equals(sysId)){
								treeJson.append(",checked:true");
								break;
							}
						}
						treeJson.append("}");
					}

				}
				
			}
			treeJson.append("]");
		}else {
			Object sci;
			//根据不同系统，不同节点进行查询
			//判断是系统定义表的oid还是菜单的oid
			List<SystemInfo> booleanlist = systemInfoDao.find( Restrictions.eq("id", node));
			// 判断是系统信息，还是菜单信息，一开始加载时，第一步显示系统信息
			if(booleanlist.size()==0){
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("menuId", node);
				//说明该oid为菜单的oid
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE mii.menuItemInfo.id=:menuId" +
						" and mii.id in (Select id From MenuItemInfo ) and mii.isShow='Y' " +
						"order by mii.menuItemInfo.menuOrder",values);
				for(int i=0;i<list.size();i++){
					Iterator<AuthorityMenu> it = roleMenu.iterator();//用于判断是否已选择了菜单项
					Iterator<AuthorityMenu> checkedIt = checkedMenu.iterator();//用于判断需要勾选的菜单项
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					
					//判断是否需要增加子级菜单
					while(it.hasNext()){
						AuthorityMenu am = it.next();
						//判断是否需要拼装 下级，如果有，则将下级数据一并显示出来
						if(am.getParentMenu().equals(sysId)){
							if(!treeJson.toString().endsWith("[")){
								treeJson.append(",");
							}
							treeJson.append("{");
							treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",flag));
							//将需要勾选的信息进行判断，如果与此菜单ID相等，则勾选上
							while(checkedIt.hasNext()){
								AuthorityMenu checked = checkedIt.next();
								if(checked.getParentMenu().equals(sysId)||checked.getMenu().equals(sysId)){
									treeJson.append(",checked:true");
									break;
								}
							}
							treeJson.append(",expanded:true,allowChildren:true,children:");
							treeJson.append(getUserAuthorityMenuTreeChecked(sysId,flag,userId,checkedUserId));
							treeJson.append("}");

							break;
						}else if(am.getMenu().equals(sysId)&&!isParent(roleMenu,sysId)){
							if(!treeJson.toString().endsWith("[")){
								treeJson.append(",");
							}
							treeJson.append("{");
							treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",flag));
							//将需要勾选的信息进行判断，如果与此菜单ID相等，则勾选上
							while(checkedIt.hasNext()){
								AuthorityMenu checked = checkedIt.next();
								if(checked.getParentMenu().equals(sysId)||checked.getMenu().equals(sysId)){
									treeJson.append(",checked:true");
									break;
								}
							}
							treeJson.append("}");

						}
					}
				}
				treeJson.append("]");
			}else{
				//下面拼装系统下功能菜单信息
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("systemCodeId", node);
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE mii.systemInfo.id=:systemCodeId " +
						"and menuItemInfo is null and mii.id in (Select id From MenuItemInfo " +
						" ) and mii.isShow='Y' order by mii.menuOrder",values);
				for(int i=0;i<list.size();i++){
					Iterator<AuthorityMenu> it = roleMenu.iterator();//用于判断是否已选择了菜单项
					Iterator<AuthorityMenu> checkedIt = checkedMenu.iterator();//用于判断需要勾选的菜单项
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					
					//判断是否需要增加子级菜单
					while(it.hasNext()){
						AuthorityMenu am = it.next();
						//判断是否需要拼装 下级，如果有，则将下级数据一拼显示出来
						if(am.getParentMenu().equals(sysId)){
							if(!treeJson.toString().endsWith("[")){
								treeJson.append(",");
							}
							treeJson.append("{");
							treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",flag));
							//将需要勾选的信息进行判断，如果与此菜单ID相等，则勾选上
							while(checkedIt.hasNext()){
								AuthorityMenu checked = checkedIt.next();
								if(checked.getParentMenu().equals(sysId)||checked.getMenu().equals(sysId)){
									treeJson.append(",checked:true");
									break;
								}
							}
							treeJson.append(",expanded:true,allowChildren:true,children:");
							treeJson.append(getUserAuthorityMenuTreeChecked(sysId,flag,userId,checkedUserId));
							treeJson.append("}");

							break;
						}else if(am.getMenu().equals(sysId)&&!isParent(roleMenu,sysId)){
							if(!treeJson.toString().endsWith("[")){
								treeJson.append(",");
							}
							treeJson.append("{");
							treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",flag));
							//将需要勾选的信息进行判断，如果与此菜单ID相等，则勾选上
							while(checkedIt.hasNext()){
								AuthorityMenu checked = checkedIt.next();
								if(checked.getParentMenu().equals(sysId)||checked.getMenu().equals(sysId)){
									treeJson.append(",checked:true");
									break;
								}
							}
							treeJson.append("}");
						}

					}
					
				}
				
				treeJson.append("]");
			}
		}

		return treeJson.toString();
	}
	/**
	 * 拼装树时，去重复数据用到的方法，如果父节点在序列中，则不进行单独的拼装
	 * @param roleMenu 所有角色功能菜单信息
	 * @param id 当前要查询的功能菜单ID
	 * @return
	 */
	private boolean isParent(Set<AuthorityMenu> roleMenu,String id){
		boolean parent = false;
		Iterator<AuthorityMenu> it = roleMenu.iterator();
		while(it.hasNext()){
			AuthorityMenu am = it.next();
			if(am.getParentMenu().equals(id))
				parent = true;
		}
		return parent;
	}
	/**
	 * 将树信息拼装统一放在此方法中
	 * @param sysId 节点ID
	 * @param sci 信息来源对象 系统信息或是功能菜单信息
	 * @param objectType 信息对象类型
	 * @param flag 是否需要复选框
	 * @return
	 */
	private String buildTreeJson(String sysId,Object sci,String objectType,boolean flag){
		StringBuffer treeJson = new StringBuffer();
		treeJson.append("id:'" + sysId+ "'");
		//判断实体是系统信息还是功能菜单信息
		if(objectType.equals("SystemInfo")){
			treeJson.append(",text:'" + ((SystemInfo) sci).getSystemName()+ "'");
			treeJson.append(",iconCls:'cog'" );//显示图片
			//判断是否含有子节点
			if(((SystemInfo) sci).getMenuItemInfo().size()>0){
				treeJson.append(",leaf:false" );
			}else{
				treeJson.append(",leaf:true" );
			}
		}else if(objectType.equals("MenuItemInfo")){
			treeJson.append(",text:'" + ((MenuItemInfo) sci).getMenuCname()+ "'");
			//treeJson.append(",iconCls:'"+((MenuItemInfo) sci).getMenuIcon()+"'" );//显示图片
			treeJson.append(",iconCls:''" );//显示图片
			//判断是否含有子节点
			if(((MenuItemInfo) sci).getChildrens().size()>0){
				treeJson.append(",leaf:false" );
			}else{
				treeJson.append(",leaf:true" );
			}
		}
		treeJson.append(",expanded:false");
		//如果flag为true，带有复选框
		if(flag){
			treeJson.append(",checked:false");
		}
		return treeJson.toString();
	}
	
	/**
	 * 显示角色下所对应的菜单功能
	 * @param id 树节点ID
	 * @param roleId 角色ID
	 * @return 树json
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public String showAuthorityMenuTree(String id,String roleId){
		List list = null ;
		String sysId = "";
		List<AuthorityMenu> menuList = new ArrayList<AuthorityMenu>();
		if(roleId!=null&&!roleId.equals("")){
			//得到角色对象
			menuList = authorityMenuDao.findMenuItemByRole(roleId, "");
		}
		
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(id.equals("-1")){
			Object sci;
			//初始化时查询系统定义表
			list = systemInfoDao.find("FROM SystemInfo");
			for(int i=0;i<list.size();i++){
				sci = (SystemInfo) list.get(i);
				sysId = ((SystemInfo) sci).getId();
				
				boolean build = false;
				for(AuthorityMenu am :menuList){
					if(am.getParentMenu().equals(sysId))
						build = true;
				}
				if(build){
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append(buildTreeJson(sysId,sci,"SystemInfo",false));
					//判断是否需要增加子级菜单
					for(AuthorityMenu am : menuList){
						//判断是否需要拼装 下级，如果有，则将下级数据一拼显示出来
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,allowChildren:true,children:");
							treeJson.append(showAuthorityMenuTree(sysId,roleId));
							break;
						}else if(am.getMenu().equals(sysId)){
							treeJson.append(",expanded:true");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
			}
			}
			treeJson.append("]");
		}else {
			Object sci;
			//根据不同系统，不同节点进行查询
			//判断是系统定义表的oid还是菜单的oid
			// 封装查询参数
			Criterion criterion = Restrictions.eq("id", id);
			// 根据部门Oid查询记录
			List<SystemInfo> booleanlist = systemInfoDao.find(criterion);
			// 判断是系统信息，还是菜单信息，一开始加载时，第一步显示系统信息
			if(booleanlist.size()==0){
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("menuItemId", id);
				//说明该oid为菜单的oid
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE " +
						"mii.menuItemInfo.id=:menuItemId and mii.id in (Select id From MenuItemInfo " +
					" ) and mii.isShow='Y' order by mii.menuItemInfo.menuOrder",values);
				for(int i=0;i<list.size();i++){
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					boolean build = false;
					for(AuthorityMenu am :menuList){
						if(am.getMenu().equals(sysId))
							build = true;
					}
					if(build){
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",false));
					
					//判断是否需要增加子级菜单
					for(AuthorityMenu am : menuList){
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,allowChildren:true,children:");
							treeJson.append(showAuthorityMenuTree(sysId,roleId));
							break;
						}else if(am.getMenu().equals(sysId)){
							treeJson.append(",expanded:true");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				}
				treeJson.append("]");
			}else{
				//下面拼装子功能菜单信息
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("systemInfoId", id);
				list = menuItemInfoDao.find("FROM MenuItemInfo mii WHERE mii.systemInfo.id=:systemInfoId" +
						" and menuItemInfo is null and mii.id in (Select id From MenuItemInfo " +
					" ) and mii.isShow='Y' order by mii.menuOrder",values);
				for(int i=0;i<list.size();i++){
					sci = (MenuItemInfo) list.get(i);
					sysId = ((MenuItemInfo) sci).getId();
					boolean build = false;
					for(AuthorityMenu am :menuList){
						if(am.getMenu().equals(sysId))
							build = true;
					}
					if(build){
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append(buildTreeJson(sysId,sci,"MenuItemInfo",false));
					
					//判断是否需要增加子级菜单
					for(AuthorityMenu am : menuList){
						if(am.getParentMenu().equals(sysId)){
							treeJson.append(",expanded:true,allowChildren:true,children:");
							treeJson.append(showAuthorityMenuTree(sysId,roleId));
							break;
						}else if(am.getMenu().equals(sysId)){
							treeJson.append(",expanded:true");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				}
				treeJson.append("]");
			}
		}
		//无信息加载时，显示欢迎信息
		if(id.equals("-1") && treeJson.toString().equals("[]")){
			treeJson = new StringBuffer();
			treeJson.append("[{");
			treeJson.append("id:'welcome'");
			treeJson.append(",text:'功能菜单',leaf:true}]");
		}
		return treeJson.toString();
	}
}
