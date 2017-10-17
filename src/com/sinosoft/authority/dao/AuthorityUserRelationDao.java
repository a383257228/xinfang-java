package com.sinosoft.authority.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityUserRelation;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 人员菜单功能权限信息dao层
 * 
 * @author sunzhe 
 * Create 2011-04-19
 */
@Repository
public class AuthorityUserRelationDao extends HibernateDao<AuthorityUserRelation, String> {

	
	/**
	 * 根据人员删除所有功能菜单信息
	 * @param userId 人员ID
	 */
	public void deleteByUser(String userId){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("userId", userId);
		this.batchExecute("delete from AuthorityUserRelation Where person=:userId",values);
	}
}
