package com.sinosoft.authority.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 部门角色信息dao层
 * 
 * @author sunzhe 
 * Create 2010-08-10
 */
@Repository
public class AuthorityDeptRoleDao extends HibernateDao<AuthorityDeptRole, String> {
	
	/**
	 * 根据角色删除所有部门角色信息
	 * @param roleId 角色ID
	 */
	public void deleteByRole(String roleId){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("roleOid", roleId);
		this.batchExecute("delete from AuthorityDeptRole Where role=:roleOid",values);
	}
	
	/**
	 * 根据条件删除所有部门角色信息
	 * @param key 条件
	 * @param value 查询值
	 */
	public void deleteByType(String key,String value){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("key", value);
		this.batchExecute("delete from AuthorityDeptRole Where "+key+"=:key",values);
	}
}
