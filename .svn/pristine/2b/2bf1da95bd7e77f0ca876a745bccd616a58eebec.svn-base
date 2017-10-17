package com.sinosoft.authority.manager;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDataDao;
import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.authority.dao.AuthorityRoleDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.authorityAudit.dao.AuthorityAuthorizationInfoDao;
import com.sinosoft.authorityAudit.dao.AuthorityHistoryOperateDao;
import com.sinosoft.authorityAudit.domain.AuthorityAuthorizationInfo;
import com.sinosoft.authorityAudit.domain.AuthorityDataHistory;
import com.sinosoft.authorityAudit.domain.AuthorityHistory;
import com.sinosoft.authorityAudit.domain.AuthorityHistoryOperate;
import com.sinosoft.authorityAudit.manager.AuthorityAuthorizationInfoManager;
import com.sinosoft.menu.dao.MenuItemInfoDao;
import com.sinosoft.menu.dao.SystemInfoDao;
import com.sinosoft.menu.domain.MenuItemInfo;
import com.sinosoft.menu.domain.SystemInfo;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.util.TimeUtils;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.string.StringUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 用户角色权限Manager层
 * @author sunzhe
 * Create 2010-08-10
 */
@Service
@Transactional
public class AuthorityUserRoleManager extends EntityManager<AuthorityUserRole, String>{
	
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	//注入菜单权限dao层
	@Autowired
	AuthorityMenuDao authorityMenuDao;
	//注入功能菜单dao层
	@Autowired
	MenuItemInfoDao menuItemInfoDao;
	
	//注入员信息dao层
	@Autowired
	PersonInfoDao personInfoDao;
	//注入角色dao层
	@Autowired
	AuthorityRoleDao authorityRoleDao;
	
	//注入人员部门关系dao层
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	//注入授权操作记录dao层
	@Autowired
	AuthorityAuthorizationInfoManager authorityAuthorizationInfoManager;
	@Autowired
	AuthorityAuthorizationInfoDao authorityAuthorizationInfoDao;
	
	//注入部门角色关系业务层
	@Autowired
	AuthorityDeptRoleManager authorityDeptRoleManager;
	/**
	 * 注入用户角色权限信息dao
	 */
	@Autowired
	private AuthorityUserRoleDao ahiUserRoleDao;
	//注入系统dao
	@Autowired
	SystemInfoDao systemInfoDao;
	//注入数据dao
	@Autowired
	AuthorityDataDao authorityDataDao;
	//注入操作的权限菜单
	@Autowired
	AuthorityHistoryOperateDao authorityHistoryOperateDao; 
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	
	@Override
	protected HibernateDao<AuthorityUserRole, String> getEntityDao() {
		return authorityUserRoleDao;
	}
	
	/**
	 * 根据条件查询用户角色，返回所有结果
	 * @param propertyName　查询字段
	 * @param value　查询值
	 * @return　用户角色集合
	 */
	@Transactional(readOnly=true)
	public List<AuthorityUserRole> findBy(String propertyName,Object value){
		return authorityUserRoleDao.findBy(propertyName, value);
	}
	
	/**
	 * 根据条件查询用户角色，只返回有效角色
	 * @param propertyName　查询字段
	 * @param value　查询值
	 * @return　用户角色集合
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<AuthorityUserRole> findInvalidBy(String propertyName,Object value) throws ParseException{
		ArrayList<AuthorityUserRole> result = new ArrayList<AuthorityUserRole>();
		List<AuthorityUserRole> list =  authorityUserRoleDao.findBy(propertyName, value);
		for(AuthorityUserRole aur : list){
			AuthorityRole ar = aur.getRole();
			//判断角色是否有效并且未过期
			if(ar.getInvalidFlag().equals("1")&&(ar.getExpireDate()==null||ar.getExpireDate().toString().equals("")
					||compareDate(ar.getExpireDate().toString())<0)){
				if(aur.getExpireDate()==null||aur.getExpireDate().toString().equals("")
						||compareDate(aur.getExpireDate().toString())<0){
					result.add(aur);
				}
			}
		}
		return result;
	}
	/**
	 * 根据用户查询该用户的角色信息
	 * 
	 * @author zhul
	 * @date 2016-05-13
	 * @param personId 用户唯一标识
	 * @return 角色id串
	 */
	/*public String queryRoleIdsByPerson(String personId){
		return authorityUserRoleDao.queryRoleIdsByPerson(personId);
	}*/
	/**
	 * 根据查询参数，查询人员信息列表
	 * 
	 * @author shenhy 2010年 5月12日
	 * @param filterT
	 *            查询参数字段名称
	 * @param filterV
	 *            查询参数字段值
	 * @param start
	 *            查询开始标号
	 * @param limit
	 *            页记录数
	 * @param sort
	 *            排序字段
	 * @param dir
	 *            排序方式 
	 * @return String 
	 * 			  Ext数据
	 * @author shenhy
	 * May 18 2010
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public String queryAuthorityUser(String filterT, String filterV, int start,
			int limit,String sort,String dir) throws IOException{
		filterT+="regionCode";
		filterV+=getRegionAuth();
		// 查询总的记录数
		int size = 0;
		// 查询总记录数
		
		size = authorityUserRoleDao.queryAuthorityUserCount(filterT, filterV);
		// 函数返回值
		String json = "{\"totalProperty\":" + size + ",\"totalCount\":" + size+",\"total\":" + size + ",\"result\":[";
		// 根据查询参数，查询对象
		List list = authorityUserRoleDao.queryAuthorityUser(filterT, filterV,
				start, limit,sort,dir);
		// 对象数组
		Object[] obj = null;
		// 循环List对象，封装查询结果信息
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 获取对象数据
				obj = (Object[]) list.get(i);
				// 获取人员机构关系对象
				OrganPersonRelationInfo organPersonRelationInfo = (OrganPersonRelationInfo) obj[1];
				// 获取人员信息对象
				PersonInfo personInfo = (PersonInfo) obj[0];
				// 获取机构实体
				OrganizationInfo organizationInfo = (OrganizationInfo) obj[2];
				if (i > 0) {
					json += ",";
				}
				//将机构+岗位这种样式显示在页面中
				String orgDeptName = "";
				if(organizationInfo.getOrganizationRelationInfo()!=null&&organizationInfo.getOrganizationRelationInfo().size()>0){
					if(organizationInfo.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo()!=null){
						if(organizationInfo.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo().getOrganizationInfo()!=null){
							orgDeptName = organizationInfo.getOrganizationRelationInfo().iterator()
							.next().getOrganizationRelationInfo().getOrganizationInfo()
							.getOrgShortCname()+organizationInfo.getOrgShortCname();
						}
					}
				}
				json += "{\"Relation_Oid\":\""
						+ organPersonRelationInfo.getId()
						+ "\","
						+
						// 用户中文名
						"\"Person_CName\":\""
						+ personInfo.getUserCname()
						+ "\","
						+
						// 用户所属机构 ID
						"\"Org_ID\":\""
						+ organizationInfo.getId()
						+ "\","
						+
						// 用户所属机构 简称
						"\"Parent_Org_ShortName\":\""
						+ organizationInfo.getOrgShortCname()
						+ "\","
						+
						// 用户所属机构 +部门　简称
						"\"Org_Dept_Name\":\""
						+ orgDeptName
						+ "\","
						+
						// 用户英文名称
						"\"Person_EName\":\""
						+ personInfo.getUserEname()
						+ "\","
						+
						// 用户职位
						"\"User_Position\":\""
						+ organPersonRelationInfo.getUserPosition()
						+ "\","
						+
						// 创建时间
						"\"Create_Date\":\""
						+ (personInfo.getCreateDate() == null ? ""
								: (personInfo.getCreateDate()).toString()
										.substring(0, 10))
						+ "\","
						+
						// 修改时间
						"\"Modify_Date\":\""
						+ (personInfo.getModifyDate() == null ? "" : personInfo
								.getModifyDate().toString().substring(0, 10))
						+ "\","
						+
						"\"Person_ID\":\""
						+ personInfo.getId()
						+ "\","
						+
						// 失效时间
						"\"Invalid_Date\":\""
						+ (personInfo.getInvalidDate() == null ? ""
								: personInfo.getInvalidDate().toString()
										.substring(0, 10)) + "\","
						+
						// 用户是否失效
						"\"Invalid_Flag\":\""
						+ personInfo.getInvalidFlag()
						+"\","
						+
						//用户所具有的角色，放开后会影响效率
						"\"Person_Owner_Role\":\""
						+ getPersonOwnerRole(personInfo)
						+"\",\"checked\":true}";
			}
		}
		json += "]}";
		// 方法返回值
		return json;
	}
	
	/**
	 * 根据用户ID，查找他所具有的所有角色，返回所有角色
	 * @param userId 用户ID
	 * @return 人员角色数组
	 */
	@Transactional(readOnly=true)
	public List<AuthorityUserRole> getRoleIdByUserId(String userId){
		return authorityUserRoleDao.getRoleIdByUserId(userId);
	}
	
	/**
	 * 根据用户ID，查找他所具有的所有有效角色,不包含分配日期已到的角色和无效的角色
	 * @param userId 用户ID
	 * @return 人员角色数组
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<AuthorityUserRole> getInvalidRoleByUserId(String userId) throws ParseException{
		ArrayList<AuthorityUserRole> result = new ArrayList<AuthorityUserRole>();
		// 根据查询条件，查询用户所有角色
		List<AuthorityUserRole> list = authorityUserRoleDao.getRoleIdByUserId(userId);
		for(AuthorityUserRole aur : list){
			AuthorityRole ar = aur.getRole();
			//判断角色是否有效并且未过期
			if(ar.getInvalidFlag().equals("1")&&(ar.getExpireDate()==null||ar.getExpireDate().toString().equals("")
					||compareDate(ar.getExpireDate().toString())<0)){
				if(aur.getExpireDate()==null||aur.getExpireDate().toString().equals("")
						||compareDate(aur.getExpireDate().toString())<0){
					result.add(aur);
				}
			}
		}
		return result;
	}
	/**
	 * 根据条件查找唯一对象　
	 * @param hql　hql语句
	 * @param values　查询值
	 * @return　人员角色信息
	 */
	@Transactional(readOnly=true)
	public AuthorityUserRole findUnique(String hql,Map<String, Object> values){
		return authorityUserRoleDao.findUnique(hql, values);
	}
	/**
	 * 根据hql查找人员角色信息
	 * @param hql　hql语句
	 * @param values　查询值
	 * @return　人员角色信息集合
	 */
	@Transactional(readOnly=true)
	public List<AuthorityUserRole> find(String hql,Map<String, Object> values){
		return authorityUserRoleDao.find(hql, values);
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
	    String str1 = d1.format(new Date());
		if(compareDate.length()>10){
			parseTime = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			str1 = str1 + " 00:00:00.0";
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
	 * 更新用户角色关系信息
	 * @param userId　人员ＩＤ
	 * @param data　角色相关信息
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserRoleDate(String userId,String data) throws ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List<AuthorityUserRole> userRoleList = authorityUserRoleDao.getRoleIdByUserId(userId);
		AuthorityAuthorizationInfo authorizationInfo = new AuthorityAuthorizationInfo();
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//记录菜单权限
		AuthorityHistory authorityHistory ;
		//判断用户操作方式
		if(userRoleList.size() == 0 && data.length()>0){//新增操作
			authorizationInfo.setOperateType("Add");
		}else if(userRoleList.size() > 0 && data.length()>0){//修改操作
			authorizationInfo.setOperateType("Update");
		}else if(userRoleList.size() > 0 && data.length()==0){//删除操作
			authorizationInfo.setOperateType("Del");
			//存放菜单信息，减少查询次数
			Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
			for(AuthorityUserRole userRole: userRoleList){
				Set<AuthorityMenu> menus = userRole.getRole().getRoleMenu();
				for(AuthorityMenu menu : menus){
					authorityHistory = new AuthorityHistory();
					//菜单信息项
					MenuItemInfo menuItemInfo = null;
					if(menuMap.get(menu.getMenu())==null){
						menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getMenu());
						menuMap.put(menu.getMenu(),menuItemInfo);
					}else{
						menuItemInfo = menuMap.get(menu.getMenu());
					}
					if(menuItemInfo!=null){
						authorityHistory.setMenuOid(menuItemInfo.getId());
						authorityHistory.setMenuName(menuItemInfo.getMenuCname());
						authorityHistory.setParentMenu(menu.getParentMenu());
					}
					//获取菜单父信息项
					if(menuMap.get(menu.getParentMenu())==null){
						menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getParentMenu());
						menuMap.put(menu.getParentMenu(),menuItemInfo);
					}else{
						menuItemInfo = menuMap.get(menu.getParentMenu());
					}
					if(menuItemInfo!=null){
						authorityHistory.setParentMenu(menuItemInfo.getId());
						authorityHistory.setParentMenuName(menuItemInfo.getMenuCname());
					}else {
						String systemName = "";
						SystemInfo si = systemInfoDao.findByUnique("id",menu.getParentMenu());
						if(si!=null)
							systemName = si.getSystemName();
						authorityHistory.setParentMenuName(systemName);
					}
					
					authorityHistory.setOperateDate(new Timestamp(System.currentTimeMillis()));
					authorizationInfo.addAuthorityHistory(authorityHistory);
				}	
				//记录数据权限信息
				AuthorityDataHistory authorityDataHistory;			
				List<AuthorityData> dataList  = authorityDataDao.findBy("role.id", userRole.getRole().getId());
				for(AuthorityData authorityData : dataList){
					authorityDataHistory = new AuthorityDataHistory();
					if("true".equals(authorityData.getAddAble()) || "true".equals(authorityData.getSelectAble())){
						PropertyUtils.copyProperties(authorityDataHistory,authorityData);
			            authorityDataHistory.setId(null);//将copy过来的id置空，否则session以为这是一条已经存在的记录
			            authorizationInfo.addAuthorityDataHistory(authorityDataHistory);
					}
				}
			}
		}
		//设置操作人信息 2011-11-18改为从onlineUser中获取登录用户信息
//		PersonInfo mainPersonInfo = (PersonInfo)Struts2Utils.getSession().getAttribute("personInfo");
		PersonInfo mainPersonInfo = personInfoDao.findByUnique("id", onlineUser.getUserId());
		if(mainPersonInfo!=null ){
		    authorizationInfo.setMainOperatorOid(mainPersonInfo.getId());
		    authorizationInfo.setMainOperatorName(mainPersonInfo.getUserCname());
		    Criteria criteria = organPersonRelationDao.getSession().createCriteria(OrganPersonRelationInfo.class);
		    criteria.add(Restrictions.eq("personInfo.id",mainPersonInfo.getId()));
		    List<OrganPersonRelationInfo>  mainList = criteria.list();
		    if(mainList.size()>0)
		    	//设置操作人部门信息，部门信息=机构+部门
		    	authorizationInfo.setMainOperatorOrganization(mainList.get(0).getOrganizationInfo().
		    		getOrganizationRelationInfo().iterator()
					.next().getOrganizationRelationInfo().getOrganizationInfo()
					.getOrgShortCname() + mainList.get(0).getOrganizationInfo().getOrgShortCname());
		}
		//设置被操作人信息
		authorizationInfo.setClientOid(userId);
		authorizationInfo.setClientOperatorName(personInfoDao.get(userId).getUserCname());
		List<OrganPersonRelationInfo> organPersonRelationInfos = organPersonRelationDao.
			findBy("personInfo.id", userId);
		if(organPersonRelationInfos.size()>0){
			String parentOrgan = "";
			//设置被操作人部门信息，部门信息=机构+部门
			if(organPersonRelationInfos.get(0).getOrganizationInfo()
					.getOrganizationRelationInfo().iterator().hasNext())
				parentOrgan = organPersonRelationInfos.get(0).getOrganizationInfo()
				.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo().getOrganizationInfo()
				.getOrgShortCname();
			authorizationInfo.setClientOperatorOrganization(parentOrgan + organPersonRelationInfos.get(0).getOrganizationInfo().getOrgShortCname());
		}
			//设置操作时间
		authorizationInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
		authorityUserRoleDao.deleteByType("person", userId);
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		if(data!=null&&!data.equals("")){
			String[] saveData = data.split(";");
			for(int i=0;i<saveData.length;i++){
				AuthorityUserRole aur = new AuthorityUserRole();
				//构建人员信息
				aur.setPerson(userId);
				//每组信息中保存角色ＩＤ，失效日期、区域编码
				String[] roleInfo = saveData[i].split(",");
				AuthorityRole role = new AuthorityRole();
				role.setId(roleInfo[0]);
				aur.setRole(role);
				AuthorityRole authorityRole = authorityRoleDao.get(roleInfo[0]);
				Set<AuthorityMenu> authorityMenus= authorityRole.getRoleMenu();
				String roleName = authorityRole.getRoleName();
				if(i == 0){
					authorizationInfo.setRoleName(roleName );
				}else{
					authorizationInfo.setRoleName(authorizationInfo.getRoleName() + ";" + roleName);
				}
				
				for(AuthorityMenu menu : authorityMenus){
					authorityHistory = new AuthorityHistory();
					authorityHistory.setRoleOid(roleInfo[0]);
					//菜单信息项
					MenuItemInfo menuItemInfo = null;
					if(menuMap.get(menu.getMenu())==null){
						menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getMenu());
						menuMap.put(menu.getMenu(),menuItemInfo);
					}else{
						menuItemInfo = menuMap.get(menu.getMenu());
					}
					if(menuItemInfo!=null){
						authorityHistory.setMenuOid(menuItemInfo.getId());
						authorityHistory.setMenuName(menuItemInfo.getMenuCname());
						authorityHistory.setParentMenu(menu.getParentMenu());
					}
					//获取菜单父信息项
					if(menuMap.get(menu.getParentMenu())==null){
						menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getParentMenu());
						menuMap.put(menu.getParentMenu(),menuItemInfo);
					}else{
						menuItemInfo = menuMap.get(menu.getParentMenu());
					}
					if(menuItemInfo!=null){
						authorityHistory.setParentMenu(menuItemInfo.getId());
						authorityHistory.setParentMenuName(menuItemInfo.getMenuCname());
					}else {
						String systemName = "";
						SystemInfo si = systemInfoDao.findByUnique("id",menu.getParentMenu());
						if(si!=null)
							systemName = si.getSystemName();
						authorityHistory.setParentMenuName(systemName);
					}
					
					authorityHistory.setOperateDate(new Timestamp(System.currentTimeMillis()));
					authorizationInfo.addAuthorityHistory(authorityHistory);
				}
		
				if(roleInfo.length>1){
					String date = roleInfo[1];
					//去掉双引号后再进行转换
					if(date!=null&&!date.equals("")){
						date = date.replace("\"", "");
						if(!date.equals("")){
							aur.setExpireDate(string2Time(date));
						}
					}
				}		
				aur.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					aur.setOperator(onlineUser.getUserId());
				//记录数据权限信息
				AuthorityDataHistory authorityDataHistory;			
				List<AuthorityData> dataList  = authorityDataDao.findBy("role.id", authorityRole.getId());
				for(AuthorityData authorityData : dataList){
					authorityDataHistory = new AuthorityDataHistory();
					//如果选中了该数据权限则记录
					if("true".equals(authorityData.getAddAble()) || "true".equals(authorityData.getSelectAble())){
			            PropertyUtils.copyProperties(authorityDataHistory,authorityData);
			            authorityDataHistory.setId(null);//将copy过来的id置空，否则session以为这是一条已经存在的记录
			            authorizationInfo.addAuthorityDataHistory(authorityDataHistory);
					}
				}
				authorityUserRoleDao.save(aur);
				
			}
		}
		//持久化授权操作记录
		authorityAuthorizationInfoDao.save(authorizationInfo);
	}
	
	/**
	 * 日期格式转换
	 * @param dateString　要转换的日期
	 * @return　Timestamp格式的日期
	 * @throws ParseException
	 */
	private Timestamp string2Time(String dateString) throws ParseException{
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//设定格式
		  dateFormat.setLenient(false);
		  java.util.Date timeDate = dateFormat.parse(dateString);//util类型
		  java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
		  return dateTime;
	}
	/**
	 * 根据用户得到他所具有的权限名称
	 * @param person 系统用户
	 * @return 权限名称
	 */
	private String getPersonOwnerRole(PersonInfo person){
		String role = "";
		List<AuthorityRole> roleList = new ArrayList<AuthorityRole>();
		List<AuthorityUserRole> list = findBy("person", person.getId());
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		//查找人员所在部门的角色数据
		while(it.hasNext()){
			OrganPersonRelationInfo opri = it.next();
			/*List<AuthorityDeptRole> organList = authorityDeptRoleManager.findBy("organization", opri.getOrganizationInfo().getId());
			for(AuthorityDeptRole adr : organList){
				roleList.add(adr.getRole());
			}*/
		}
		//拼装人员所具有的角色数据
		for(AuthorityUserRole aur : list){
			roleList.add(aur.getRole());
		}
		for(AuthorityRole authorityRole : roleList){
			//role += authorityRole.getRoleName()+";";
			if(role.length()>0)
				role=role+";";
			role+=authorityRole.getRoleName();
		}
		return role;
	}
	/**
	 * 删除人员与角色关系
	 * @param roleId 角色ID
	 * @param userId 人员ID，可以是多个，用,分隔
	 */
	@SuppressWarnings("unchecked")
	public void removeUserRole(String roleId,String userId) throws Exception{
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		String[] userIds = userId.split(",");
		for(int i=0;i<userIds.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("personId", userIds[i]);
			map.put("roleId", roleId);
			//判断该 人员是否已与角色绑定
			List<AuthorityUserRole> list = authorityUserRoleDao.find("From AuthorityUserRole " +
					"Where person=:personId and role.id=:roleId", map);
			for(AuthorityUserRole aur : list){
				authorityUserRoleDao.delete(aur);
				//保存操作记录
				List<AuthorityUserRole> userRoleList = ahiUserRoleDao
				  .getRoleIdByUserId(userIds[i]);
				String operateType = "Update";
				if (userRoleList.size() == 0) {//更新操作
					operateType = "Del";
				} 
				// 初始化授权操作记录信息类
				AuthorityAuthorizationInfo atzInfo = new AuthorityAuthorizationInfo();
				atzInfo.setOperateType(operateType);
				// 设置操作时间
				atzInfo.setOperateDate(new Timestamp(System
						.currentTimeMillis()));
				//获得该用户关联的的所有角色
				List<AuthorityUserRole> userRoles =	ahiUserRoleDao.getRoleIdByUserId(userIds[i]);
				//存储用户关联的角色名称
				StringBuffer  roleNames = new StringBuffer();
				for (AuthorityUserRole authorityUserRole :  userRoles) {
					if (!authorityUserRole.getRole().getId().equals(roleId)) {
						//拼接角色名称，多个角色用";"号隔开
						roleNames.append(authorityUserRole.getRole().getRoleName()).append(";");
						//记录角色关联的菜单
						AuthorityHistory authorityHistory;
						Set<AuthorityMenu> menuSet = authorityUserRole.getRole().getRoleMenu();
						for (AuthorityMenu menu : menuSet) {
							MenuItemInfo menuItemInfo = null;
							authorityHistory = new AuthorityHistory();
							menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getMenu());
							if(menuItemInfo!=null){
								authorityHistory.setMenuName(menuItemInfo.getMenuCname());
								authorityHistory.setMenuOid(menuItemInfo.getId());
								authorityHistory.setParentMenu(menu.getParentMenu());
								authorityHistory.setRoleOid(authorityUserRole.getRole().getId());
							}
							menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getParentMenu());
							if (menuItemInfo != null) {
								authorityHistory.setParentMenu(menuItemInfo.getId());
								authorityHistory.setParentMenuName(menuItemInfo.getMenuCname());
							} else {
								String systemName = systemInfoDao.get(menu.getParentMenu())
										.getSystemName();
								authorityHistory.setParentMenuName(systemName);
							}
							authorityHistory.setOperateDate(new Timestamp(System
									.currentTimeMillis()));
							atzInfo.addAuthorityHistory(authorityHistory);

						}
					}
				}
				if (roleNames.length() > 0) {//删除多余";"
					roleNames.deleteCharAt(roleNames.length() - 1);
				}
				// 设置角色名称
				atzInfo.setRoleName(roleNames.toString());
				// 设置操作人员信息
				PersonInfo mainPersonInfo =  personInfoDao.findByUnique("id", onlineUser.getUserId());
				Criteria criteria = organPersonRelationDao.getSession()
					.createCriteria(OrganPersonRelationInfo.class);
				if (mainPersonInfo != null) {
					atzInfo.setMainOperatorOid(mainPersonInfo
							.getId());
					atzInfo.setMainOperatorName(mainPersonInfo
							.getUserCname());
					criteria.add(Restrictions.eq("personInfo.id", mainPersonInfo
							.getId()));
					List<OrganPersonRelationInfo> mainList = criteria.list();
					//设置操作人部门信息，部门信息=机构+部门
					if (!mainList.isEmpty()) {
						atzInfo.setMainOperatorOrganization(
						  mainList.get(0).getOrganizationInfo().getOrganizationRelationInfo()
						  .iterator().next().getOrganizationRelationInfo().getOrganizationInfo()
						  .getOrgShortCname() + mainList.get(0).getOrganizationInfo().getOrgShortCname()
						);
					}
				}
				// 设置被操作人员信息
				PersonInfo clientPersonInfo = personInfoDao.get(userIds[i]);
				atzInfo.setClientOid(userIds[i]);
				atzInfo.setClientOperatorName(clientPersonInfo
						.getUserCname());
				List<OrganPersonRelationInfo> clientList = organPersonRelationDao.findBy("personInfo.id", userIds[i]);
				//设置被操作人部门信息，部门信息=机构+部门
				if (!clientList.isEmpty()){
					atzInfo.setClientOperatorOrganization(
					  clientList.get(0).getOrganizationInfo().getOrganizationRelationInfo()
					  .iterator().next().getOrganizationRelationInfo().getOrganizationInfo()
					  .getOrgShortCname() + clientList.get(0).getOrganizationInfo().getOrgShortCname()
					);
				}
				//authorityAuthorizationInfoDao.save(atzInfo);
				//保存被操作的权限菜单
				Set<AuthorityMenu> authorityMenuSet  = authorityRoleDao.get(roleId).getRoleMenu();
				//根据用户除正在操作的角色之外拥有的其它角色
				//查看新的新的角色菜单是否跟其它的角色菜单有重复
				Set<String> menuIds = new HashSet<String>();
				for (AuthorityUserRole authorityUserRole : userRoles) {
					if (!authorityUserRole.getRole().getId().equals(roleId)) {
						Set<AuthorityMenu> ahMenuSet = authorityUserRole.getRole().getRoleMenu();
						for (AuthorityMenu menu: ahMenuSet) {
							menuIds.add(menu.getMenu());
						}
					}
				}
				AuthorityHistoryOperate authorityHistoryOperate;
				for (AuthorityMenu authorityMenu : authorityMenuSet) {
					String menuOid = authorityMenu.getMenu();
					int beforeAddSize = menuIds.size();
					menuIds.add(menuOid);
					int afterAddSize = menuIds.size();
					if (beforeAddSize != afterAddSize) {//只有当该菜单不存在与其它角色中时，才证明这是一个真正被删除的菜单
						authorityHistoryOperate = new AuthorityHistoryOperate();
						authorityHistoryOperate.setMenuOid(menuOid);
						authorityHistoryOperate.setOperateType("Del");
						authorityHistoryOperate.setRoleOid(roleId);
						MenuItemInfo menuItemInfo=menuItemInfoDao.findUnique("from MenuItemInfo where oid='"+menuOid+"'");
						if(menuItemInfo!=null){
							authorityHistoryOperate.setMenuName(menuItemInfo.getMenuCname());
						}
//						authorityHistoryOperate.setAuthorityAuthorizationInfo(atzInfo);
						//authorityHistoryOperateDao.save(authorityHistoryOperate);
					}
				}
			}
		}
	} 
	
	/**
	 * flag标示：Add 新增,Update 更新
	 * @param userId 用户id
	 * @param data 关联数据 包含角色id
	 * @param flag 
	 * @param systemAuthorityCode 系统权限编码
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUserRoleDate(String userId,String data,String flag, String systemAuthorityCode,String regionCode) throws ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List<AuthorityUserRole> userRoleList = authorityUserRoleDao.getRoleIdByUserId(userId);
		AuthorityAuthorizationInfo authorizationInfo = new AuthorityAuthorizationInfo();
		//OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//记录菜单权限
		AuthorityHistory authorityHistory ;
		//判断用户操作方式
		if(flag!=null && !flag.equals("")){
			if(flag.equals("Add")){
				authorizationInfo.setOperateType("Add");//新增操作
			}else if(flag.equals("Update")){
				authorizationInfo.setOperateType("Update");//修改操作
			}else if(flag.equals("Del")){
				authorizationInfo.setOperateType("Del");//删除操作
				//存放菜单信息，减少查询次数
				Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
				for(AuthorityUserRole userRole: userRoleList){
					Set<AuthorityMenu> menus = userRole.getRole().getRoleMenu();
					for(AuthorityMenu menu : menus){
						authorityHistory = new AuthorityHistory();
						//菜单信息项
						MenuItemInfo menuItemInfo = null;
						if(menuMap.get(menu.getMenu())==null){
							menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getMenu());
							menuMap.put(menu.getMenu(),menuItemInfo);
						}else{
							menuItemInfo = menuMap.get(menu.getMenu());
						}
						if(menuItemInfo!=null){
							authorityHistory.setMenuOid(menuItemInfo.getId());
							authorityHistory.setMenuName(menuItemInfo.getMenuCname());
							authorityHistory.setParentMenu(menu.getParentMenu());
						}
						//获取菜单父信息项
						if(menuMap.get(menu.getParentMenu())==null){
							menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getParentMenu());
							menuMap.put(menu.getParentMenu(),menuItemInfo);
						}else{
							menuItemInfo = menuMap.get(menu.getParentMenu());
						}
						if(menuItemInfo!=null){
							authorityHistory.setParentMenu(menuItemInfo.getId());
							authorityHistory.setParentMenuName(menuItemInfo.getMenuCname());
						}else {
							String systemName = "";
							SystemInfo si = systemInfoDao.findByUnique("id",menu.getParentMenu());
							if(si!=null)
								systemName = si.getSystemName();
							authorityHistory.setParentMenuName(systemName);
						}
						
						authorityHistory.setOperateDate(new Timestamp(System.currentTimeMillis()));
						authorizationInfo.addAuthorityHistory(authorityHistory);
					}	
					//记录数据权限信息
					AuthorityDataHistory authorityDataHistory;			
					List<AuthorityData> dataList  = authorityDataDao.findBy("role.id", userRole.getRole().getId());
					for(AuthorityData authorityData : dataList){
						authorityDataHistory = new AuthorityDataHistory();
						if("true".equals(authorityData.getAddAble()) || "true".equals(authorityData.getSelectAble())){
							PropertyUtils.copyProperties(authorityDataHistory,authorityData);
				            authorityDataHistory.setId(null);//将copy过来的id置空，否则session以为这是一条已经存在的记录
				            authorizationInfo.addAuthorityDataHistory(authorityDataHistory);
						}
					}
				}
			}
		}
		//设置操作人信息 2011-11-18改为从onlineUser中获取登录用户信息
//		PersonInfo mainPersonInfo = (PersonInfo)Struts2Utils.getSession().getAttribute("personInfo");
		PersonInfo mainPersonInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		if(mainPersonInfo!=null ){
		    authorizationInfo.setMainOperatorOid(mainPersonInfo.getId());
		    authorizationInfo.setMainOperatorName(mainPersonInfo.getUserCname());
		    Criteria criteria = organPersonRelationDao.getSession().createCriteria(OrganPersonRelationInfo.class);
		    criteria.add(Restrictions.eq("personInfo.id",mainPersonInfo.getId()));
		    List<OrganPersonRelationInfo>  mainList = criteria.list();
		    if(mainList.size()>0)
		    	//设置操作人部门信息，部门信息=机构+部门
		    	authorizationInfo.setMainOperatorOrganization(mainList.get(0).getOrganizationInfo().
		    		getOrganizationRelationInfo().iterator()
					.next().getOrganizationRelationInfo().getOrganizationInfo()
					.getOrgShortCname() + mainList.get(0).getOrganizationInfo().getOrgShortCname());
		}
		//设置被操作人信息
		authorizationInfo.setClientOid(userId);
		
		//给用户设置系统访问权限	jingwen	2016-07-02
		PersonInfo person = personInfoDao.get(userId);
		personInfoDao.save(person);
		authorizationInfo.setClientOperatorName(person.getUserCname());
		List<OrganPersonRelationInfo> organPersonRelationInfos = organPersonRelationDao.
			findBy("personInfo.id", userId);
		if(organPersonRelationInfos.size()>0){
			String parentOrgan = "";
			//设置被操作人部门信息，部门信息=机构+部门
			if(organPersonRelationInfos.get(0).getOrganizationInfo()
					.getOrganizationRelationInfo().iterator().hasNext())
				parentOrgan = organPersonRelationInfos.get(0).getOrganizationInfo()
				.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo().getOrganizationInfo()
				.getOrgShortCname();
			authorizationInfo.setClientOperatorOrganization(parentOrgan + organPersonRelationInfos.get(0).getOrganizationInfo().getOrgShortCname());
		}
			//设置操作时间
		authorizationInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
		//authorityUserRoleDao.deleteByType("person", userId);
		//存放菜单信息，减少查询次数
		Map<String,MenuItemInfo> menuMap = new HashMap<String,MenuItemInfo>();
		if(data!=null&&!data.equals("")){
			String[] saveData = data.split(";");
			for(int i=0;i<saveData.length;i++){
				AuthorityUserRole aur = new AuthorityUserRole();
				//构建人员信息
				aur.setPerson(userId);
				//每组信息中保存角色ＩＤ，失效日期、区域编码
				String[] roleInfo = saveData[i].split(",");
				AuthorityRole role = new AuthorityRole();
				if(flag.equals("Update")){
					authorityUserRoleDao.deleteByRoleAndUser(roleInfo[0],userId);
				}
				role.setId(roleInfo[0]);
				aur.setRole(role);
				AuthorityRole authorityRole = authorityRoleDao.get(roleInfo[0]);
				Set<AuthorityMenu> authorityMenus= authorityRole.getRoleMenu();
				String roleName = authorityRole.getRoleName();
				if(i == 0){
					authorizationInfo.setRoleName(roleName );
				}else{
					authorizationInfo.setRoleName(authorizationInfo.getRoleName() + ";" + roleName);
				}
				
				for(AuthorityMenu menu : authorityMenus){
					authorityHistory = new AuthorityHistory();
					authorityHistory.setRoleOid(roleInfo[0]);
					//菜单信息项
					MenuItemInfo menuItemInfo = null;
					if(menuMap.get(menu.getMenu())==null){
						menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getMenu());
						menuMap.put(menu.getMenu(),menuItemInfo);
					}else{
						menuItemInfo = menuMap.get(menu.getMenu());
					}
					if(menuItemInfo!=null){
						authorityHistory.setMenuOid(menuItemInfo.getId());
						authorityHistory.setMenuName(menuItemInfo.getMenuCname());
						authorityHistory.setParentMenu(menu.getParentMenu());
					}
					//获取菜单父信息项
					if(menuMap.get(menu.getParentMenu())==null){
						menuItemInfo = menuItemInfoDao.findByUnique("id", menu.getParentMenu());
						menuMap.put(menu.getParentMenu(),menuItemInfo);
					}else{
						menuItemInfo = menuMap.get(menu.getParentMenu());
					}
					if(menuItemInfo!=null){
						authorityHistory.setParentMenu(menuItemInfo.getId());
						authorityHistory.setParentMenuName(menuItemInfo.getMenuCname());
					}else {
						String systemName = "";
						SystemInfo si = systemInfoDao.findByUnique("id",menu.getParentMenu());
						if(si!=null)
							systemName = si.getSystemName();
						authorityHistory.setParentMenuName(systemName);
					}
					
					authorityHistory.setOperateDate(new Timestamp(System.currentTimeMillis()));
					authorizationInfo.addAuthorityHistory(authorityHistory);
				}
		
				if(roleInfo.length>1){
					String date = roleInfo[1];
					//去掉双引号后再进行转换
					if(date!=null&&!date.equals("")){
						date = date.replace("\"", "");
						if(!date.equals("")){
							aur.setExpireDate(string2Time(date));
						}
					}
					//添加保存区域
					if(roleInfo.length>2){
						String regionCodeStr=roleInfo[2];
						if(regionCodeStr!=null && !regionCodeStr.equals("")){
							aur.setRegionCode(roleInfo[2]);
						}
					}
				}		
				aur.setOperateDate(TimeUtils.getAllTimestamp());
				if(mainPersonInfo!=null)
					aur.setOperator(mainPersonInfo.getId());
				//记录数据权限信息
				AuthorityDataHistory authorityDataHistory;			
				List<AuthorityData> dataList  = authorityDataDao.findBy("role.id", authorityRole.getId());
				for(AuthorityData authorityData : dataList){
					authorityDataHistory = new AuthorityDataHistory();
					//如果选中了该数据权限则记录
					if("true".equals(authorityData.getAddAble()) || "true".equals(authorityData.getSelectAble())){
			            PropertyUtils.copyProperties(authorityDataHistory,authorityData);
			            authorityDataHistory.setId(null);//将copy过来的id置空，否则session以为这是一条已经存在的记录
			            authorizationInfo.addAuthorityDataHistory(authorityDataHistory);
					}
				}
				if(!StringUtils.isNullString(regionCode)){
					aur.setRegionCode(regionCode);
				}
				authorityUserRoleDao.save(aur);
				
			}
		}
		//持久化授权操作记录
		//authorityAuthorizationInfoDao.save(authorizationInfo);
	}
	
	/**
	 * 封装region_code权限值获取方法
	 * @author hjh
	 * 2015年8月21日
	 * @return
	 */
	public String getRegionAuth(){
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		String regionCode=personInfo.getRegionCode();
		String provRegionCode=regionCode.substring(0,2);//省级
		String cityRegionCode = regionCode.substring(2,4);//市级
		String countyRegionCode = regionCode.substring(4,6);//县级
		String region="";
		if(!provRegionCode.equals("00")&&!cityRegionCode.equals("00")&&!countyRegionCode.equals("00")){
			region=regionCode.substring(0,6).concat("%");
		}else if(!provRegionCode.equals("00") && cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			region=regionCode.substring(0, 2).concat("%");
		}else if(!provRegionCode.equals("00") && !cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			region=regionCode.substring(0, 4).concat("%");
		}else if(provRegionCode.equals("00") && cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			region="%";
		}
		return region;
	}
	
	/**
	 * 根据用户所属区域查询该区域下所有用户角色关联信息
	 * @param filterTxt
	 * @param filterValue
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String queryAuthorityUserAndRoleList(String filterTxt, String filterValue,int start,
			int limit,String sort, String dir) throws IOException {
		
		String filterT="regionCode";
		String filterV=getRegionAuth();
		if(filterTxt.trim().length()>0){
			filterT=filterT+";"+filterTxt;
			filterV=filterV+";"+filterValue;
		}
		int size=0;
		// 查询总的记录数
		size=authorityUserRoleDao.queryAuthorityUserCount(filterT,filterV);
		//查询有效用户信息
		List<Object[]> list=authorityUserRoleDao.queryAuthorityUser(filterT, filterV, start, limit, sort, dir);
		StringBuffer json=new StringBuffer("{");
		json.append("\"total\":" + size);
		json.append(",\"rows\":[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 获取人员机构关系对象
				OrganPersonRelationInfo organPersonRelationInfo=(OrganPersonRelationInfo) list.get(i)[1];
				// 获取人员信息对象
				PersonInfo person =organPersonRelationInfo.getPersonInfo();
				// 获取机构实体
				OrganizationInfo organizationInfo =organPersonRelationInfo.getOrganizationInfo();
				if (i > 0) {
					json.append(",");
				}
				//将机构+岗位这种样式显示在页面中
				String orgDeptName = "";
				if(organizationInfo.getOrganizationRelationInfo()!=null&&organizationInfo.getOrganizationRelationInfo().size()>0){
					if(organizationInfo.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo()!=null){
						if(organizationInfo.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo().getOrganizationInfo()!=null){
							orgDeptName = organizationInfo.getOrgShortCname() /*+"-"+ organizationInfo.getOrganizationRelationInfo().iterator().next().getOrganizationRelationInfo().getOrganizationInfo().getOrgShortCname()*/;
						}
					}
				}
				json.append("{\"Relation_Oid\":\""+organPersonRelationInfo.getId()+ "\"");
				json.append(",\"Person_EName\":\""+ person.getUserEname()+ "\"");
				json.append(",\"Person_CName\":\""+ person.getUserCname()+ "\"");
				json.append(",\"Org_ID\":\""+ organizationInfo.getId()+ "\"");
				json.append(",\"Org_Dept_Name\":\""+ orgDeptName+ "\"");
				json.append(",\"Person_ID\":\""+ person.getId()+ "\"");
				json.append(",\"Invalid_Date\":\""+ (person.getInvalidDate() == null ? "": person.getInvalidDate()
						    .toString().substring(0, 10)) + "\"");
				json.append(",\"Person_Owner_Role\":\""+ getPersonOwnerRole(person)+"\"");
				json.append(",\"Role_ID\":\""+ getPersonOwnerRoleId(person)+"\"");
				json.append("}");
			}
		}
		json.append("]}");
		return json.toString();
	}
    
	/**
	 * 1、新增时，查询操作用户所属区域及区域下的下级子区域，带复选 框
	 * 2、更新时，查看操作用户所属区域及区域下的下级子区域，初始用户已拥有的区域权限，带复选框
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getChildOrgRelaInfoListByRegion(String userId,String roleId) {
		//PersonInfo personInfo=logonManager.getOnlinePersonInfo();
		PersonInfo personInfo=null;
		if(!"".equals(userId.trim()) && !"undefined".equals(userId)){
			//集中式部署，针对具体用户显示区域信息
			personInfo=personInfoDao.get(userId);
		}else{
		    personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		}
		List<OrganizationRelationInfo> list = null ;
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		Object sci;
		String regionCodeStr="";
		//初始化时查询系统定义表
		/*list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
				personInfo.getRegionCode()+"' order by organizationInfo.orgCode");*/
		List<OrganizationRelationInfo> organizationRelationInfo=organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
				personInfo.getRegionCode()+"' order by organizationInfo.orgCode");
		//得到权限区域
		if(!userId.trim().equals("") && !userId.equals("undefined") 
				&& !roleId.trim().equals("") && !roleId.equals("undefined")){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("roleId", roleId);
			AuthorityUserRole authorityUserRole=authorityUserRoleDao.findUnique("from AuthorityUserRole where person=:userId and role.id=:roleId", map);
			regionCodeStr=authorityUserRole.getRegionCode();
		}
		if(!organizationRelationInfo.isEmpty()){
			OrganizationRelationInfo o=organizationRelationInfo.get(0);
			list=organizationRelationInfoDao.getOrganRelaInfoByParentOid("1",o.getId(),"",false,"");
			treeJson.append("{");
			treeJson.append("\"id\":\"" + o.getId()+ "\"");
			treeJson.append(",\"text\":\"" + o.getOrganizationInfo().getOrgCname()+ "\"");
			treeJson.append(",\"orgCode\":\"" + o.getOrganizationInfo().getOrgCode()+ "\"");
			treeJson.append(",\"expanded\":true");
			if(regionCodeStr!=null && !regionCodeStr.equals("") && regionCodeStr.contains(
					o.getOrganizationInfo().getOrgCode())){
				treeJson.append(",\"checked\":true");
			}else{
				treeJson.append(",\"checked\":false");
			}
			treeJson.append(",\"iconCls\":\"chart_organisation\"" );
			if(!list.isEmpty()){
				treeJson.append(",\"leaf\":false" );
				treeJson.append(",\"allowChildren\":true,\"children\":[");
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
					treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"orgCode\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "\"");
					treeJson.append(",\"leaf\":true" );
					treeJson.append(",\"iconCls\":\"chart_organisation\"" );
					if(regionCodeStr!=null && !regionCodeStr.equals("") && regionCodeStr.contains(
							((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode())){
						treeJson.append(",\"checked\":true");
					}else{
						treeJson.append(",\"checked\":false");
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
					
				}
				treeJson.append("]");
			}else{
				treeJson.append(",\"leaf\":true" );
			}
		}
		treeJson.append("}]");
		return treeJson.toString();
	}
	
	/**
	 * 查询用户拥有所有角色id
	 * @param person 用户id
	 * @return
	 */
	@Transactional(readOnly=true)
	private String getPersonOwnerRoleId(PersonInfo person){
		String roleId = "";
		List<AuthorityRole> roleList = new ArrayList<AuthorityRole>();
		List<AuthorityUserRole> list = findBy("person", person.getId());
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		//查找人员所在部门的角色数据
		while(it.hasNext()){
			OrganPersonRelationInfo opri = it.next();
			/*List<AuthorityDeptRole> organList = authorityDeptRoleManager.findBy("organization", opri.getOrganizationInfo().getId());
			for(AuthorityDeptRole adr : organList){
				roleList.add(adr.getRole());
			}*/
		}
		//拼装人员所具有的角色数据
		for(AuthorityUserRole aur : list){
			roleList.add(aur.getRole());
		}
		for(AuthorityRole authorityRole : roleList){
			//role += authorityRole.getRoleName()+";";
			if(roleId.length()>0)
				roleId=roleId+",";
			roleId+=authorityRole.getId();
		}
		return roleId;
	}
	
	/**
	 * 查询用户所有角色关联信息
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=true)
	public String loadUserRole(String userId){
		List<AuthorityUserRole> list = authorityUserRoleDao.findBy("person", userId);
		StringBuffer treeJson=new StringBuffer("{\"result\":[");
		AuthorityUserRole  authorityUserRole=null;
		boolean firstItem=true;
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				authorityUserRole=list.get(i);
				
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("\"id\":\"" + authorityUserRole.getId()+ "\"");
				treeJson.append(",\"userId\":\"" +authorityUserRole.getPerson()+ "\"");
				treeJson.append(",\"roleId\":\"" +authorityUserRole.getRole().getId()+ "\"");
				treeJson.append(",\"roleCode\":\"" + authorityUserRole.getRole().getRoleCode()+ "\"");
				treeJson.append(",\"roleName\":\""+authorityUserRole.getRole().getRoleName()+ "\"");
				treeJson.append(",\"roleDescription\":\""+authorityUserRole.getRole().getDescription()+ "\"");
				treeJson.append(",\"regionCode\":\""+authorityUserRole.getRegionCode()+ "\"");
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			
		}
		treeJson.append("]");
		treeJson.append("}");
		return treeJson.toString();
	}
	
	/**
	 * 区域权限sql方法
	 * @param isSql 判断是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return sql语句中带权限的where部分
	 */
	@SuppressWarnings("unchecked")
	public String getRegionAuthorityByTable(String[] tableName,boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		if(tableName.length>0){
			for(int i=0;i<tableName.length;i++){
				if("PETITION_BASIC_INFO".equals(tableName[i].toUpperCase())||
						changeTableToHql("PETITION_BASIC_INFO").toUpperCase().equals(
								tableName[i].toUpperCase())){
					OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
					PersonInfo person = personInfoDao.findByUnique("id", onlineUser.getUserId());
					if(person!=null){
						//找出该用户角色下所有的区域权限
						List<AuthorityUserRole> userRoleList = authorityUserRoleDao.find("from AuthorityUserRole " +
								"where person='"+person.getId()+"'");
						Set<String> regionCodeSet = new HashSet<String>();
						String fieldName = "";//sql中的字段名称
						for(AuthorityUserRole userRole : userRoleList){
							//权限区域
							String regionCodeStr=userRole.getRegionCode();
							if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
								for(String regionCode:regionCodeStr.split(":")){
									regionCodeSet.add(regionCode);
								}
							}
						}
						//根据语句类型，判断选择哪种处理方式，拼装表名称与字段名称
						if(isSql){
							if(withTable){
								fieldName = "PETITION_BASIC_INFO"+"."+"SUB_ISSUE_REGION_CODE";
							}else{
								fieldName = "SUB_ISSUE_REGION_CODE";
							}
						}else{
							if(withTable){
								fieldName = changeTableToHql("PETITION_BASIC_INFO")+"."+
										changeColumnToHql("SUB_ISSUE_REGION_CODE");
							}else{
								fieldName = changeColumnToHql("SUB_ISSUE_REGION_CODE");
							}
						}
						if(regionCodeSet.size()>0){
							boolean first = false;
							hql.append(" and ");
							hql.append(fieldName).append(" in (");
							//遍历所有用户机构信息
							Iterator<String> it=regionCodeSet.iterator();
							while(it.hasNext()){
								if(first){
									hql.append(",");
								}
								hql.append("'"+it.next()+"'");
								if(!first){
									first = true;
								}
							}
							hql.append(")");
						}else{
							hql.append(" and ");
							hql.append(fieldName).append("=''");
						}
						
					}
				}
			}
		}
		return hql.toString();
	}
	/**
	 * 区域权限sql方法
	 * @param isSql 判断是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return sql语句中带权限的where部分
	 */
	@SuppressWarnings({ "unchecked"})
	public String getRegionAuthority(boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		PersonInfo person = personInfoDao.findByUnique("id", onlineUser.getUserId());
		if(person!=null){
			//找出该用户角色下所有的区域权限
			List<AuthorityUserRole> userRoleList = authorityUserRoleDao.find("from AuthorityUserRole " +
					"where person='"+person.getId()+"'");
			Set<String> regionCodeSet = new HashSet<String>();
			String fieldName = "";//sql中的字段名称
			for(AuthorityUserRole userRole : userRoleList){
				//权限区域
				String regionCodeStr=userRole.getRegionCode();
				if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
					for(String regionCode:regionCodeStr.split(":")){
						regionCodeSet.add(regionCode);
					}
				}
			}
			//根据语句类型，判断选择哪种处理方式，拼装表名称与字段名称
			if(isSql){
				if(withTable){
					fieldName = "PETITION_BASIC_INFO"+"."+"SUB_ISSUE_REGION_CODE";
				}else{
					fieldName = "SUB_ISSUE_REGION_CODE";
				}
			}else{
				if(withTable){
					fieldName = changeTableToHql("PETITION_BASIC_INFO")+"."+
							changeColumnToHql("SUB_ISSUE_REGION_CODE");
				}else{
					fieldName = changeColumnToHql("SUB_ISSUE_REGION_CODE");
				}
			}
			if(regionCodeSet.size()>0){
				boolean first = false;
				hql.append(" and ");
				hql.append(fieldName).append(" in (");
				//遍历所有用户机构信息
				Iterator<String> it=regionCodeSet.iterator();
				while(it.hasNext()){
					if(first){
						hql.append(",");
					}
					hql.append("'"+it.next()+"'");
					if(!first){
						first = true;
					}
				}
				hql.append(")");
			}else{
				hql.append(" and ");
				hql.append(fieldName).append("=''");
			}
			
		}
		return hql.toString();
	}
	
	/**
	 * 区域权限sql方法
	 * @param isSql 判断是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return sql语句中带权限的where部分
	 */
	@SuppressWarnings("unchecked")
	public String getRegionAuthorityByTableAndRole(PersonInfo person,String[] tableName,AuthorityRole role,boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		for(int i=0;i<tableName.length;i++){
			if("PETITION_BASIC_INFO".equals(tableName[i].toUpperCase())||
					changeTableToHql("PETITION_BASIC_INFO").toUpperCase().equals(
							tableName[i].toUpperCase())){
				//OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
				//PersonInfo person=logonManager.getOnlinePersonInfo();
				//PersonInfo person = personInfoDao.findByUnique("id", onlineUser.getUserId());
				if(person!=null){
					//找出该用户角色下所有的区域权限
					List<AuthorityUserRole> userRoleList = authorityUserRoleDao.find("from AuthorityUserRole " +
							"where person='"+person.getId()+"' and role.id='"+role.getId()+"'");
					if(!userRoleList.isEmpty()){
						AuthorityUserRole userRole=userRoleList.get(0);
						//权限区域
						Set<String> regionCodeSet = new HashSet<String>();
						String regionCodeStr=userRole.getRegionCode();
						if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
							for(String regionCode:regionCodeStr.split(":")){
								regionCodeSet.add(regionCode);
							}
						}
						String fieldName = "";//sql中的字段名称
						//根据语句类型，判断选择哪种处理方式，拼装表名称与字段名称
						if(isSql){
							if(withTable){
								fieldName = "PETITION_BASIC_INFO"+"."+"SUB_ISSUE_REGION_CODE";
							}else{
								fieldName = "SUB_ISSUE_REGION_CODE";
							}
						}else{
							if(withTable){
								fieldName = changeTableToHql("PETITION_BASIC_INFO")+"."+
										changeColumnToHql("SUB_ISSUE_REGION_CODE");
							}else{
								fieldName = changeColumnToHql("SUB_ISSUE_REGION_CODE");
							}
						}
						if(regionCodeSet.size()>0){
							boolean first = false;
							hql.append(" and (");
							hql.append(fieldName).append(" in (");
							//遍历所有用户机构信息
							Iterator<String> it=regionCodeSet.iterator();
							while(it.hasNext()){
								if(first){
									hql.append(",");
								}
								hql.append("'"+it.next()+"'");
								if(!first){
									first = true;
								}
							}
							hql.append(")");
							hql.append(" or ").append(fieldName).append("=''");
							hql.append(")");
						}else{
							hql.append(" and ");
							hql.append(fieldName).append("=''");
						}
						
					}
				}
				break;
			}
		}
		return hql.toString();
	}
	/**
	 * 将表名称改为Hibernate对象形式的名称 例：AA_BB AaBb
	 * @param name 表名称
	 * @return
	 */
	private String changeTableToHql(String name){
		String result = "";
		if(name.indexOf("_")>0){
			String[] ss = name.split("_");
			for(int i=0;i<ss.length;i++){
					result += ss[i].substring(0,1).toUpperCase()+
					ss[i].substring(1,ss[i].length()).toLowerCase();
			}
		}
		return result;
	}
	
	/**
	 * 将字段名称改为Hibernate对象形式的名称 例：AA_BB aaBb
	 * @param name 字段名称
	 * @return
	 */
	private String changeColumnToHql(String name){
		String result = "";
		if(name.indexOf("_")>0){
			String[] ss = name.split("_");
			for(int i=0;i<ss.length;i++){
				if(i==0){
					result = ss[i].substring(0,1).toLowerCase()+
					ss[i].substring(1,ss[i].length()).toLowerCase();
				}else{
					result += ss[i].substring(0,1).toUpperCase()+
					ss[i].substring(1,ss[i].length()).toLowerCase();
				}
			}
		}
		return result;
	}

	/**
	 * 用户权限区域OrganizationInfo
	 * 被哪些方法调用：
	 * 1、LeaderStatInfoAction.getPetitionRegionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationInfo> getUserAuthorityOrganInfo(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		//找出该用户角色下所有的区域权限
		List<AuthorityUserRole> userRoleList = authorityUserRoleDao.find("from AuthorityUserRole " +
				"where person='"+person.getId()+"'");
		Set<String> regionCodeSet = new HashSet<String>();
		for(AuthorityUserRole userRole : userRoleList){
			//权限区域
			String regionCodeStr=userRole.getRegionCode();
			if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
				for(String region:regionCodeStr.split(":")){
					regionCodeSet.add(region);
				}
			}
		}
		//移除中纪委
		if(regionCodeSet.contains(person.getRegionCode())){
			regionCodeSet.remove(person.getRegionCode());
		}
		Iterator<String> it=regionCodeSet.iterator();
		StringBuffer region=new StringBuffer("");
		while(it.hasNext()){
			if(region.length()>0)
				region.append(",");
			region.append("'"+it.next()+"'");
		}
		List<OrganizationInfo> list=organizationInfoDao.find(" from OrganizationInfo where orgCode in("+region+")");
		return list;
	}
	
}

