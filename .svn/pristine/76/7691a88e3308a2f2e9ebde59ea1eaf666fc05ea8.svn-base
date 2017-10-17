package com.sinosoft.authority.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 角色权限信息dao层
 * 
 * @author sunzhe 
 * Create 2010-08-10
 */
@Repository
public class AuthorityRoleDao extends HibernateDao<AuthorityRole, String> {

	/**
	 * 查找系统信息
	 * @param parameterName 查询条件
	 * @param parameterValue 查询值
	 * @return 系统信息数组
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AuthorityRole> getDataByType(String parameterName,String parameterValue){
		String hql = "from SystemInfo";
		Query query = null;
		//如果没有查询条件，直接返回所有结果
		if(!"".equals(parameterName)&&parameterName!=null&&!"".equals(parameterValue)&&parameterValue!=null){
			Map values = new HashMap();
			values.put(parameterName, parameterValue);
			query = this.createQuery(hql, values);
		}else{
			query = this.getSession().createQuery(hql);
		}
		return query.list();
	}

}
