package com.sinosoft.authority.manager;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDeptRoleDao;
import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.authority.dao.AuthorityRoleDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.authorityAudit.dao.AuthorityHistoryOperateDao;
import com.sinosoft.authorityAudit.domain.AuthorityAuthorizationInfo;
import com.sinosoft.authorityAudit.domain.AuthorityHistoryOperate;
import com.sinosoft.authorityAudit.manager.AuthorityAuthorizationInfoManager;
import com.sinosoft.authorityAudit.thread.AaInfoContainer;
import com.sinosoft.menu.dao.MenuItemInfoDao;
import com.sinosoft.menu.dao.SystemInfoDao;
import com.sinosoft.menu.domain.MenuItemInfo;
import com.sinosoft.menu.domain.SystemInfo;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 角色权限Manager层
 * @author sunzhe
 * Create 2010-08-10
 */
@Service
@Transactional
public class AuthorityRoleManager extends EntityManager<AuthorityRole, String>{

	//注入角色权限信息dao层
	@Autowired
	AuthorityRoleDao authorityRoleDao;

	//注入系统信息dao层
	 
	@Autowired
	SystemInfoDao systemInfoDao;
	
	//注入部门角色信息dao层
	@Autowired
	AuthorityDeptRoleDao authorityDeptRoleDao;
	//注入组织机构dao层
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	//注入人员角色信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	//注入人员dao层
	@Autowired
	PersonInfoDao personInfoDao;
	//注入机构人员关系dao层
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	//注入功能菜单权限dao层
	@Autowired
	AuthorityMenuDao authorityMenuDao;
	//注入菜单信息dao层
	@Autowired
	MenuItemInfoDao menuInfoDao;
	//注入数据权限业务层
	@Autowired
	AuthorityDataManager authorityDataManager;
	//注入功能菜单dao
	@Autowired
    MenuItemInfoDao	menuItemInfoDao;
	//注入权限审计业务层
	@Autowired
	AuthorityAuthorizationInfoManager authorityAuthorizationInfoManager;
	//注入授权操作菜单记录dao层
	@Autowired
	AuthorityHistoryOperateDao authorityHistoryOperateDao;
	@Autowired
	AuthorityUserRoleDao authoirtyUserRoleDao;
	
	@Override
	protected HibernateDao<AuthorityRole, String> getEntityDao() {
		return authorityRoleDao;
	}

	/**
	 * 获取系统信息
	 * @param parameterName 查询字段
	 * @param parameterValue 查询值
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<AuthorityRole> getDataByType(String parameterName,
			String parameterValue) {
		return authorityRoleDao.getDataByType(parameterName,parameterValue);
	}

	/**
	 * 保存角色实体
	 * @param model 角色实体 
	 * @param system 所属系统
	 */
	@SuppressWarnings("unused")
	@NotTransactional
	public void save(AuthorityRole model, String system,String menuId,String parentMenuId,
			List<AuthorityData> dataList) throws Exception {
		boolean flag = false;
		if(model.getId()!= null){//该角色是否存在有用户关联
			flag = true;
		}
		if(system!=null&&!system.equals(""))
			model.setSystem(system);
		model.setOperateDate(new Timestamp(System.currentTimeMillis()));
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(onlineUser!=null)
			model.setOperator(onlineUser.getUserId());
		authorityRoleDao.save(model);
		//保存角色后，根据角色信息，将其他信息也保存
		//model = authorityRoleDao.findUnique(Restrictions.eq("roleCode", model.getRoleCode()));
		model = authorityRoleDao.findUnique(Restrictions.eq("id", model.getId()));
		//删除该角色关联的原有菜单前，用map接受这些菜单key是菜单oid，value是菜单object
		Set<AuthorityMenu> authorityMenuSet = model.getRoleMenu();
		HashMap<String, MenuItemInfo> menuItemInfoMap = new HashMap<String, MenuItemInfo>();
		for (AuthorityMenu authorityMenu : authorityMenuSet) {
			MenuItemInfo menuItemInfo = menuItemInfoDao.get(authorityMenu.getMenu());
			menuItemInfoMap.put(authorityMenu.getMenu(), menuItemInfo);
		}
		authorityMenuDao.deleteByRole(model.getId());
		//保存菜单功能权限
		if(menuId!=null&&!menuId.equals("")){
			String[] menuIds = menuId.split(",");
			String[] parentMenuIds = parentMenuId.split(",");
			for(int i=0;i<menuIds.length;i++){
				if(parentMenuIds[i]!=null&&!parentMenuIds[i].equals("")&&!parentMenuIds[i].equals("-1")){
					AuthorityMenu menuRole = new AuthorityMenu();
					menuRole.setMenu(menuIds[i]);
					menuRole.setRole(model);
					menuRole.setParentMenu(parentMenuIds[i]);
					menuRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
					if(onlineUser!=null)
						menuRole.setOperator(onlineUser.getUserId());
					authorityMenuDao.save(menuRole);
				}
			}
		}
		//保存数据权限部分
		if(dataList!=null&&dataList.size()>0){
			authorityDataManager.saveGridData(dataList, model.getId());
		}
		authorityMenuDao.getSession().flush();
		authorityMenuDao.getSession().evict(model);
		/*
		if (flag) {//如果存在关联的用户则对角色的操作同样认为是对用户权限的操作,记录其操作信息	
			List<AuthorityUserRole> userRoleList = authorityUserRoleDao.getUserIdByRoleId(model.getId());//得到与该角色关联的所有用户
			for(AuthorityUserRole userRole: userRoleList){
				//复制一个装有角色原有菜单的hashMap集合
				HashMap<String, MenuItemInfo> cloneMenuItemInfoMap = (HashMap<String,
					MenuItemInfo>)menuItemInfoMap.clone();
//				authorityAuthorizationInfoManager.saveAuthorizationInfoByUserId(userRole.getPerson(),
//						entity.getId(),"Update"); 
              // 目录系统时解注该部分代码
				AuthorityAuthorizationInfo aaInfo = authorityAuthorizationInfoManager.saveAuthorizationInfoByPerson(
					userRole.getPerson(),model.getId(),"Update");          
			  //记录操作的权限菜单
				//获取用户除正在操作的角色之外拥有的其它角色
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("personId", userRole.getPerson());
				map.put("roleId", model.getId());
				List<AuthorityUserRole> userRoles  = authorityUserRoleDao.find("From AuthorityUserRole " +
						"Where person=:personId and role.id!=:roleId", map);
				Set<String> ahMenuIds = new HashSet<String>();
				for (AuthorityUserRole authorityUserRole : userRoles) {
					Set<AuthorityMenu> ahMenuSet = authorityUserRole.getRole().getRoleMenu();
					for (AuthorityMenu menu: ahMenuSet) {
						ahMenuIds.add(menu.getMenu());
					}
				}
				String[] menuIds = menuId.split(",");
				Set<String> removeKeys = new HashSet<String>();
				for(int i=0;i< menuIds.length;i++){ 
					boolean isExist = false;
					for (String key : cloneMenuItemInfoMap.keySet()) {
						if (menuIds[i].equals(key)) {
							isExist = true;
							//记录要被删除掉的菜单key
							removeKeys.add(key);
						}
					}
					if (!isExist) {
				//不存在于原来的角色菜单中，就视为新增的菜单，记录该菜单用于权限审计
						//从菜单集合中移除该菜单
						MenuItemInfo mif = menuItemInfoDao.findByUnique("id", menuIds[i]);
						if (mif != null) {//如果该菜单不为系统信息则保存该菜单的信息
							int beforeAddSize = ahMenuIds.size();
							ahMenuIds.add(menuIds[i]);
							int afterAddSize = ahMenuIds.size();
							if (beforeAddSize != afterAddSize) {//只有当该菜单不存在与其它角色中时，才证明这是一个新的菜单
								AuthorityHistoryOperate authorityHistoryOperate = new AuthorityHistoryOperate();
								authorityHistoryOperate.setMenuOid(menuIds[i]);
								authorityHistoryOperate.setOperateType("Add");
								authorityHistoryOperate.setRoleOid(model.getId());
								authorityHistoryOperate.setMenuName(mif.getMenuCname());	
								authorityHistoryOperate.setAuthorityAuthorizationInfo(aaInfo);
								aaInfo.getAuthorityHistoryOperates().add(authorityHistoryOperate);
								//authorityHistoryOperateDao.save(authorityHistoryOperate);
							}
						}
					}
				}
				//删除未发生改变的菜单
				for (String mapKey : removeKeys) {
					cloneMenuItemInfoMap.remove(mapKey);
				}
				//menuItemInfoMap中剩余的菜单则全部为被删除了菜单
				
				AuthorityHistoryOperate authorityHistoryOperate ;
				for (Map.Entry<String, MenuItemInfo> entry :  cloneMenuItemInfoMap.entrySet()) {
					MenuItemInfo mif = menuItemInfoDao.findByUnique("id", entry.getKey());
					if (mif != null) {//如果该菜单不为系统信息则保存该菜单的信息
						int beforeAddSize = ahMenuIds.size();
						ahMenuIds.add(entry.getKey());
						int afterAddSize = ahMenuIds.size();
						if (beforeAddSize != afterAddSize) {//只有当该菜单不存在与其它角色中时，才证明这是一个被删除的菜单
							authorityHistoryOperate  = new AuthorityHistoryOperate();
							authorityHistoryOperate.setMenuOid(entry.getKey());
							authorityHistoryOperate.setOperateType("Del");
							authorityHistoryOperate.setRoleOid(model.getId());
							authorityHistoryOperate.setMenuName(entry.getValue().getMenuCname());	
							authorityHistoryOperate.setAuthorityAuthorizationInfo(aaInfo);
							aaInfo.getAuthorityHistoryOperates().add(authorityHistoryOperate);
							//authorityHistoryOperateDao.save(authorityHistoryOperate);
						}
					}
				}
				
				//添加权限审计
				//AaInfoContainer.addLog(aaInfo);
			}
		}*/
	}
	
	/**
	 * 保存角色所对应的用户与菜单功能信息
	 * @param role 角色信息
	 * @param deptId 部门ID
	 * @param userId 人员ID
	 * @param menuId 功能菜单ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveConfigRole(AuthorityRole role,String deptId,String userId,String menuId,
			String parentMenuId)throws Exception{
		authorityDeptRoleDao.deleteByRole(role.getId());
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(!deptId.equals("")){
			//保存所选择的部门信息
			String[] deptIds = deptId.split(",");
			for(int i=0;i<deptIds.length;i++){
				AuthorityDeptRole deptRole = new AuthorityDeptRole();
				deptRole.setRole(role);
				deptRole.setOrganization(deptIds[i]);
				deptRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					deptRole.setOperator(onlineUser.getUserId());
				authorityDeptRoleDao.save(deptRole);
			}
		}
		authorityUserRoleDao.deleteByRole(role.getId());
		if(!userId.equals("")){
			//保存人员信息
			String[] userIds = userId.split(",");
			for(int i=0;i<userIds.length;i++){
				AuthorityUserRole userRole = new AuthorityUserRole();
				userRole.setRole(role);
				userRole.setPerson(userIds[i]);
				userRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					userRole.setOperator(onlineUser.getUserId());
				authorityUserRoleDao.save(userRole);
			}
		}
		authorityMenuDao.deleteByRole(role.getId());
		if(!menuId.equals("")){
			//保存功能菜单信息
			String[] menuIds = menuId.split(",");
			String[] parentMenuIds = parentMenuId.split(",");
			for(int i=0;i<menuIds.length;i++){
				if(parentMenuIds[i]!=null&&!parentMenuIds[i].equals("")&&!parentMenuIds[i].equals("-1")){
					AuthorityMenu menuRole = new AuthorityMenu();
					menuRole.setMenu(menuIds[i]);
					menuRole.setRole(role);
					menuRole.setParentMenu(parentMenuIds[i]);
					menuRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
					if(onlineUser!=null)
						menuRole.setOperator(onlineUser.getUserId());
					authorityMenuDao.save(menuRole);
				}
			}
		}
	}
	
	/**
	 * 保存角色所对应的用户与部门信息,清空之前保存信息
	 * @param role 角色信息
	 * @param deptId 部门ID
	 * @param userId 人员ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveDeptUserRole(String[] roleId,String deptId,String userId) throws Exception{
		String[] deptIds = deptId.split(",");
		String[] userIds = userId.split(",");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//清除该部门已分配角色
		for(int i=0;i<deptIds.length;i++){
			authorityDeptRoleDao.deleteByType("organization", deptIds[i]);
		}
		//清除该人员已分配角色
		for(int i=0;i<userIds.length;i++){
			authorityUserRoleDao.deleteByType("person", userIds[i]);
		}
		//重新为部门与人员分配角色
		for(int j=0;j<roleId.length;j++){
		if(!deptId.equals("")){
			for(int i=0;i<deptIds.length;i++){
				AuthorityDeptRole deptRole = new AuthorityDeptRole();
				deptRole.setRole(authorityRoleDao.get(roleId[j]));
				deptRole.setOrganization(deptIds[i]);
				deptRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					deptRole.setOperator(onlineUser.getUserId());
				authorityDeptRoleDao.save(deptRole);
			}
		}
		//保存人员角色关联信息
		if(!userId.equals("")){
			for(int i=0;i<userIds.length;i++){
				AuthorityUserRole userRole = new AuthorityUserRole();
				userRole.setRole(authorityRoleDao.get(roleId[j]));
				userRole.setPerson(userIds[i]);
				userRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					userRole.setOperator(onlineUser.getUserId());
				authorityUserRoleDao.save(userRole);
			}
		}
		}
	}
	
	/**
	 * 保存角色所对应的用户与部门信息,不清空之前保存信息，只在原来基础上进行添加
	 * @param role 角色信息
	 * @param deptId 部门ID
	 * @param userId 人员ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addDeptUserRole(String[] roleId,String deptId,String userId)throws Exception{
		String[] deptIds = deptId.split(",");
		String[] userIds = userId.split(",");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//为部门与人员分配角色
		for(int j=0;j<roleId.length;j++){
			if(!deptId.equals("")){
				for(int i=0;i<deptIds.length;i++){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("organId", deptIds[i]);
					map.put("roleId", roleId[j]);
					//判断该 部门是否已与角色绑定
					List<AuthorityDeptRole> list = authorityDeptRoleDao.find("From AuthorityDeptRole " +
							"Where organization=:organId and role.id=:roleId", map);
					//对未绑定该角色的部门保存，其余略过
					if(list.size()==0){
						AuthorityDeptRole deptRole = new AuthorityDeptRole();
						deptRole.setRole(authorityRoleDao.get(roleId[j]));
						deptRole.setOrganization(deptIds[i]);
						deptRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
						if(onlineUser!=null)
							deptRole.setOperator(onlineUser.getUserId());
						authorityDeptRoleDao.save(deptRole);
					}
				}
			}
			//保存人员角色关联信息
			if(!userId.equals("")){
				for(int i=0;i<userIds.length;i++){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("personId", userIds[i]);
					map.put("roleId", roleId[j]);
					//判断该 人员是否已与角色绑定
					List<AuthorityUserRole> list = authorityUserRoleDao.find("From AuthorityUserRole " +
							"Where person=:personId and role.id=:roleId", map);
					//对未绑定该角色的人员保存，其余略过
					if(list.size()==0){
						AuthorityUserRole userRole = new AuthorityUserRole();
						userRole.setRole(authorityRoleDao.get(roleId[j]));
						userRole.setPerson(userIds[i]);
						userRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
						if(onlineUser!=null)
							userRole.setOperator(onlineUser.getUserId());
						//记录该次操作信息，为权限审计提供相应数据
						String OperateType = "Update";
						//判断该用户是否已经存在关联的角色
						List<AuthorityUserRole> userRoleList = authorityUserRoleDao.getRoleIdByUserId(userIds[i]);
						if ( userRoleList.size() == 0) {//如果不存在关联角色，则为“更新”操作，否则为“新增操作”
							OperateType = "Add";
						}
						authorityUserRoleDao.save(userRole);
						//保存操作信息和执行完该次授权操作后该用户剩余的菜单
						AuthorityAuthorizationInfo aaInfo = authorityAuthorizationInfoManager
							.saveAuthorizationInfoByPerson(userIds[i], roleId[j], OperateType);
						//保存正在操作的菜单
						Set<AuthorityMenu> authorityMenuSet  = authorityRoleDao.get(roleId[j]).getRoleMenu();
						//获取用户除正在操作的角色之外拥有的其它角色
						List<AuthorityUserRole> userRoles  = authorityUserRoleDao.find("From AuthorityUserRole " +
								"Where person=:personId and role.id!=:roleId", map);
						//查看新的新的角色菜单是否跟其它的角色菜单有重复
						Set<String> menuIds = new HashSet<String>();
						for (AuthorityUserRole authorityUserRole : userRoles) {
							Set<AuthorityMenu> ahMenuSet = authorityUserRole.getRole().getRoleMenu();
							for (AuthorityMenu menu: ahMenuSet) {
								menuIds.add(menu.getMenu());
							}
						}
						AuthorityHistoryOperate authorityHistoryOperate;
						for (AuthorityMenu authorityMenu : authorityMenuSet) {
							String menuOid = authorityMenu.getMenu();
							int beforeAddSize = menuIds.size();
							menuIds.add(menuOid);
							int afterAddSize = menuIds.size();
							if (beforeAddSize != afterAddSize) {//只有当该菜单不存在与其它角色中时，才证明这是一个新的菜单
								authorityHistoryOperate = new AuthorityHistoryOperate();
								authorityHistoryOperate.setMenuOid(menuOid);
								authorityHistoryOperate.setOperateType("Add");
								authorityHistoryOperate.setRoleOid(roleId[j]);
								authorityHistoryOperate.setMenuName(menuItemInfoDao.get(menuOid).getMenuCname());	
//								authorityHistoryOperate.setAuthorityAuthorizationInfo(aaInfo);
//								aaInfo.getAuthorityHistoryOperates().add(authorityHistoryOperate);
							//	authorityHistoryOperateDao.save(authorityHistoryOperate);
							}
						}
						AaInfoContainer.addLog(aaInfo);
					}
				}
			}
		}
	}
	
	/**
	 * 保存角色所对应的用户与部门信息,并清空之间保存的所有部门下人员角色
	 * @param role 角色信息
	 * @param deptId 部门ID
	 * @param userId 人员ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetDeptUserRole(String[] roleId,String deptId,String userId)throws Exception{
		String[] deptIds = deptId.split(",");
		String[] userIds = userId.split(",");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//清除该部门已分配角色
		for(int i=0;i<deptIds.length;i++){
			authorityDeptRoleDao.deleteByType("organization", deptIds[i]);
			OrganizationInfo organInfo = organizationInfoDao.get(deptIds[i]);
			//根据机构，找出机构下所有人员，并删除其已分配的角色
			Set<OrganPersonRelationInfo> personSet = organInfo.getOrganPersonRelationInfo();
			Iterator<OrganPersonRelationInfo> it = personSet.iterator();
			while(it.hasNext()){
				OrganPersonRelationInfo opri = it.next();
				authorityUserRoleDao.deleteByType("person", opri.getPersonInfo().getId());
			}
		}
		//清除该人员已分配角色
		for(int i=0;i<userIds.length;i++){
			authorityUserRoleDao.deleteByType("person", userIds[i]);
		}
		//重新为部门与人员分配角色
		for(int j=0;j<roleId.length;j++){
		if(!deptId.equals("")){
			for(int i=0;i<deptIds.length;i++){
				AuthorityDeptRole deptRole = new AuthorityDeptRole();
				deptRole.setRole(authorityRoleDao.get(roleId[j]));
				deptRole.setOrganization(deptIds[i]);
				deptRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					deptRole.setOperator(onlineUser.getUserId());
				authorityDeptRoleDao.save(deptRole);
			}
		}
		//保存人员角色关联信息
		if(!userId.equals("")){
			for(int i=0;i<userIds.length;i++){
				AuthorityUserRole userRole = new AuthorityUserRole();
				userRole.setRole(authorityRoleDao.get(roleId[j]));
				userRole.setPerson(userIds[i]);
				userRole.setOperateDate(new Timestamp(System.currentTimeMillis()));
				if(onlineUser!=null)
					userRole.setOperator(onlineUser.getUserId());
				authorityUserRoleDao.save(userRole);
			}
		}
		}
	}
	/**
	 * 查询方法，继承自框架中
	 * @param hql 查询语句
	 * @param values 查询值
	 * @return 角色查询结果集
	 */
	@Transactional(readOnly=true)
	public List<AuthorityRole> find(String hql,Map<String, Object> values)throws Exception{
		return authorityRoleDao.find(hql, values);
	}

	/**
	 * 树形显示角色信息
	 * @param node 树节点ID
	 * @param flag 是否需要选择框
	 * @param userId 用户ID
	 * @return
	 */
	public String buildAuthorityRoleTree(String node,boolean flag,String userId)throws Exception {
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		//首先显示系统信息，第二步查询根据系统信息显示该系统下所有角色
		if(node.equals("-1")){
			List<SystemInfo> list = systemInfoDao.getAll();
			for(SystemInfo system : list){
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'"+system.getId()+"'");
				treeJson.append(",text:'"+system.getSystemName()+"'");
				treeJson.append(",code:'"+system.getSystemCode()+"'");
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
		}else{
			List<AuthorityRole> list = authorityRoleDao.findBy("system", node);
			for(AuthorityRole role : list){
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'"+role.getId()+"'");
				treeJson.append(",text:'"+role.getRoleName()+"'");
				treeJson.append(",code:'"+role.getRoleCode()+"'");
				treeJson.append(",leaf:true");
				//判断是否需要选择框
				if(flag){
					if(userId!=null&&!userId.equals("")){
						//判断该用户是否已选择过权限
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("person", userId);
						map.put("role.id", role.getId());
						List<AuthorityUserRole> roleList = 
							authorityUserRoleDao.find(Restrictions.allEq(map));
						String checked = "false";
						//如果数据库中存在记录，则勾选上树节点
						if(roleList.size()>0)
							checked = "true";
						treeJson.append(",checked:"+checked);
					}else{
						treeJson.append(",checked:false");
					}
				}
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 根据id删除角色
	 * @param ids 要被删除的角色id集合
	 * date 2012-03-08
	 * @throws Exception 
	 */
	@Transactional
	public void remove(String ids) throws Exception {
		String as[];
        int j = (as = ids.split(",")).length;
        for(int i = 0; i < j; i++)
        {
            String str = as[i];
            	//删除角色时，要删除其关联的数据
                List<AuthorityUserRole> list = authoirtyUserRoleDao.findBy("role.id", str);
                //删除人员角色关联信息
                for(AuthorityUserRole aur : list){
                	authoirtyUserRoleDao.delete(aur.getId());
                }
                List<AuthorityDeptRole> dlist = authorityDeptRoleDao.findBy("role.id", str);
                //删除部门角色关联信息
                for(AuthorityDeptRole adr : dlist){
                	authorityDeptRoleDao.delete(adr.getId());
                }
                List<AuthorityMenu> mlist = authorityMenuDao.findBy("role.id", str);
                //删除菜单权限
                for(AuthorityMenu am : mlist){
                	authorityMenuDao.delete(am.getId());
                }
                List<AuthorityData> alist = authorityDataManager.findBy("role.id", str);

                //删除数据权限
                for(AuthorityData ad : alist){
                	authorityDataManager.delete(ad.getId());
                }
                //删除角色前先记录该角色拥有的所有菜单权限
				Set<AuthorityMenu> authorityMenuSet  = authorityRoleDao.get(str).getRoleMenu();
				for (AuthorityMenu menu : authorityMenuSet) {
					menu.setMenu(menu.getMenu());
				}
                authorityRoleDao.delete(str);
//                authorityRoleDao.getSession().flush();
//				authorityRoleDao.getSession().clear();
                //记录该操作关联的所有人员权限的变动信息           
                for(AuthorityUserRole userRole: list){  	
                	//authorityAuthorizationInfoManager.saveAuthorizationInfoByUserId(userRole.
                	//		getPerson(),(String)id,"Del");   
                	List<AuthorityUserRole> userRoleList = authorityUserRoleDao.getRoleIdByUserId(userRole.getPerson());
                	String operateType = "Update";
					if ( userRoleList.size() == 0) {//如果不存在关联角色，则为“更新”操作，否则为“新增操作”
						operateType = "Del";
					}
                     //目录系统下时解注该部分代码
                	AuthorityAuthorizationInfo aai = authorityAuthorizationInfoManager.
                		saveAuthorizationInfoByPerson(userRole.getPerson(),str,operateType);   
            		//保存被操作的权限菜单
    				Map<String,Object> map = new HashMap<String,Object>();
    				map.put("personId", userRole.getPerson());
    				map.put("roleId", str);
    				List<AuthorityUserRole> userRoles  = authorityUserRoleDao.find("From AuthorityUserRole " +
    						"Where person=:personId and role.id!=:roleId", map);
    				Set<String> ahMenuIds = new HashSet<String>();
    				for (AuthorityUserRole authorityUserRole : userRoles) {
    					Set<AuthorityMenu> ahMenuSet = authorityUserRole.getRole().getRoleMenu();
    					for (AuthorityMenu menu: ahMenuSet) {
    						ahMenuIds.add(menu.getMenu());
    					}
    				}
    				AuthorityHistoryOperate authorityHistoryOperate;
    				for (AuthorityMenu authorityMenu : authorityMenuSet) {
    					String menuOid = authorityMenu.getMenu();
						int beforeAddSize = ahMenuIds.size();
						ahMenuIds.add(menuOid);
						int afterAddSize = ahMenuIds.size();
						if (beforeAddSize != afterAddSize) {//只有当该菜单不存在与其它角色中时，才证明这是一个被删除的菜单
	    					authorityHistoryOperate = new AuthorityHistoryOperate();
	    					authorityHistoryOperate.setMenuOid(menuOid);
	    					authorityHistoryOperate.setOperateType("Del");
	    					authorityHistoryOperate.setRoleOid(str);
	    					authorityHistoryOperate.setMenuName(menuItemInfoDao.get(menuOid).getMenuCname());	
//	    					authorityHistoryOperate.setAuthorityAuthorizationInfo(aai);
//	    					aai.getAuthorityHistoryOperates().add(authorityHistoryOperate);
	    					//authorityHistoryOperateDao.save(authorityHistoryOperate);
						}
    				}
    				AaInfoContainer.addLog(aai);
                }
        }
		
	}
}
