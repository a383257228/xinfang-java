package com.sinosoft.authority.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 菜单功能权限信息dao层
 * 
 * @author sunzhe 
 * Create 2010-08-10
 */
@Repository
public class AuthorityMenuDao extends HibernateDao<AuthorityMenu, String> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 删除角色菜单
	 * @param roleId 角色ID
	 */
	public void deleteByRole(String roleId) {
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("roleOid", roleId);
		this.batchExecute("delete from AuthorityMenu Where role_oid=:roleOid",values);
	}
	/**
	 * 根据角色与父菜单查找菜单对象
	 * @param roleId 角色ID
	 * @param parentMenu 父菜单信息
	 * @return 菜单角色数组
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityMenu> findMenuItemByRole(String roleId,String parentMenu){
		//将查询条件放到map中
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("roleId", roleId);
		String hql = "From AuthorityMenu Where role.id=:roleId and menu in " +
				"(select id from MenuItemInfo)";
		//根据传入的值判断采用哪种hql
		if(parentMenu!=null&&parentMenu.equals("null"))
			hql += " and parentMenu is null";
		else if(parentMenu!=null&&parentMenu.equals("not null"))
			hql += " and parentMenu is not null";
		else if(parentMenu!=null&&!parentMenu.equals("")){
			values.put("parentMenu",parentMenu);
			hql += " and parentMenu=:parentMenu";
		}
		Query query = this.createQuery(hql,values);
		return query.list();
	}
	
	/**
	 * 根据菜单Id和用户id决定首页显示页面
	 * 被哪些方法调用：
	 * 1、LogonAction.logonStandard
	 * @param userId 用户id
	 * @param menuIds 菜单id
	 * @param parentMenuId 父菜单id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> findMenuByUserIdAndMenuId(String userId,String menuId,String parentMenuId){
		StringBuffer sql=new StringBuffer("select menu.menu_oid as meunId,menu.parent_menu as parentMenu");
		sql.append(" from authority_menu menu,authority_user_role role");
		sql.append(" where menu.role_oid=role.role_oid");
		if(menuId!=null && !menuId.equals("")){
			sql.append(" and menu.menu_oid in("+menuId+")");
		}else if(parentMenuId!=null && !parentMenuId.equals("")){
			sql.append(" and menu.parent_menu='"+parentMenuId+"'");
		}
		sql.append(" and role.user_oid='"+userId+"'");
		sql.append(" order by menu.parent_menu asc");
		return jdbcTemplate.queryForList(sql.toString());
	}
}
