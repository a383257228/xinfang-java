package com.sinosoft.authority.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 数据权限信息dao层
 * 
 * @author sunzhe 
 * Create 2010-08-10
 */
@Repository
public class AuthorityDataDao extends HibernateDao<AuthorityData, String> {
	/**
	 * 根据角色信息删除所有数据权限
	 * @param roleId 角色ID
	 */
	public void deleteByRole(String roleId) {
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("roleOid", roleId);
		this.batchExecute("delete from AuthorityData Where role_oid=:roleOid",values);
	}
}
