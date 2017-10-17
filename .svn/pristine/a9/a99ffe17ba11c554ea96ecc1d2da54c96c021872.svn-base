package com.sinosoft.authority.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDeptRoleDao;
import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.menu.dao.MenuItemInfoDao;
import com.sinosoft.menu.domain.MenuItemInfo;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 信访菜单菜单权限Manager层
 * @author Dustin
 * Create 2013-02-21
 */
@Service
@Transactional
public class PetitionAuthorityMenuManager extends EntityManager<AuthorityMenu, String>{

	//注入菜单权限信息dao层
	@Autowired
	AuthorityMenuDao authorityMenuDao;
	//注入菜单信息dao层
	@Autowired
	MenuItemInfoDao menuItemInfoDao;
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	//注入部门角色信息dao层
	@Autowired
	AuthorityDeptRoleDao authorityDeptRoleDao;
	
	@Override
	protected HibernateDao<AuthorityMenu, String> getEntityDao() {
		return authorityMenuDao;
	}
	
	@Autowired
	PersonInfoDao personInfoDao;
	/**
	 * 设置wld平台主页
	 * @author hjh
	 * @param 
	 * @return
	 * 2014-7-21下午01:02:22
	 */
	public PersonInfo setWldHomePage(String platformHomePage) throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		PersonInfo personInfo = (PersonInfo) session.getAttribute("personInfo");
		PersonInfo temp = personInfoDao.get(personInfo.getId());
		temp.setPlatformHomePage(platformHomePage);
		personInfoDao.save(temp);
		return temp;
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
		Date date = new Date();
		String roleOid = "";
		//增加人员菜单权限
		for (int i = 0; i < roleList.size(); i++) {
			AuthorityUserRole nAuthorityUserRole=(AuthorityUserRole) roleList.get(i);
			AuthorityRole role = nAuthorityUserRole.getRole();
			//判断权限是否过期
			if(role.getExpireDate()==null||role.getExpireDate().toString().equals("")||date.before(role.getExpireDate())){
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
						||date.before(role.getExpireDate()))&&"1".equals(role.getInvalidFlag())){
					roleOid += "'"+role.getId()+"',";
				}
			}
		}
		if(roleOid.endsWith(","))
			roleOid = roleOid.substring(0,roleOid.length()-1);
		//拼装系统菜单json
		StringBuffer treeJson = new StringBuffer("{'info':[");
		List<AuthorityMenu> list = new ArrayList<AuthorityMenu>();
		if(!roleOid.equals("")){
			list = authorityMenuDao.find("from AuthorityMenu where role.id in ("+roleOid+") " +
					"and parentMenu is not null and menu in " +
					"(Select id From MenuItemInfo where menuItemInfo is null)");
		}
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
				//功能菜单详细信息
				treeJson.append("{'allowChildren':true,'allowDelete':true,'allowEdit':true,'children':[");
				String json = getMenuItemInfoById4Petition(menuItem.getId(),roleOid);
				treeJson.append(json);
				treeJson.append("],'descn':'menu','draggable':true,");
				//菜单图标
				treeJson.append("'iconCls':'menuempty',");
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
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		Set<String> set = (Set<String>) session.getAttribute("authorityMenuSet");
		StringBuffer treeJson = new StringBuffer("");
		//根据角色，查询所有功能菜单信息
		List<AuthorityMenu> cList= new ArrayList<AuthorityMenu>();
		//关联菜单表查询，防止菜单被删除，并加上排序
		List<Object[]> objList = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii " +
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
				treeJson.append("'cls': 'x-tree-node-menu','iconCls':'menuempty',");
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
					set.add(menuItem.getMenuUrl().trim());
				}
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
//	private long compareDate(String compareDate) throws ParseException{
//		Calendar cpcalendar = new GregorianCalendar(); 
//		Calendar cpcalendarNow = new GregorianCalendar();
//		java.util.Date date = null; 
//		java.util.Date today = null;
//		java.text.SimpleDateFormat parseTime = null;
//		DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
//	    String str1 = d1.format(new Date());
//		if(compareDate.length()>10){
//			parseTime = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			str1 = str1 + " 00:00:00.0";
//		}else{
//			parseTime = new java.text.SimpleDateFormat(Constants.Date_Short);
//		}
//		//可以把数据库中查询的日期放在这里，需要格式一下 
//		date = parseTime.parse(compareDate); 
//		today = parseTime.parse(str1);
//		cpcalendar.setTime(date);
//		cpcalendarNow.setTime(today); 
//		return (cpcalendarNow.getTimeInMillis() - cpcalendar.getTimeInMillis())-1;
//	}
	/**
	 * 通过角色与菜单父节点 拼装树形展示所需要的数据
	 * @param parentMenu 父菜单
	 * @param roleOid 角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getMenuItemInfoByMenuItemInfoId(String parentMenu,String roleOid){
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		Set<String> set = (Set<String>) session.getAttribute("authorityMenuSet");
		StringBuffer treeJson = new StringBuffer("");
		//根据角色，查询所有功能菜单信息
		List<AuthorityMenu> cList= new ArrayList<AuthorityMenu>();
		//关联菜单表查询，防止菜单被删除，并加上排序
		List<Object[]> objList = authorityMenuDao.find("from AuthorityMenu am , MenuItemInfo mii " +
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
					set.add(menuItem.getMenuUrl().trim());
				}
			}
		}
		return treeJson.toString();
	}
}
