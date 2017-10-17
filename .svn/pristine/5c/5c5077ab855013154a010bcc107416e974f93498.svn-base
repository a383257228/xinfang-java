package com.sinosoft.authority.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 系统权限信息dao层.
 * @author sunzhe
 * Create 2012-02-01
 */
@Repository
public class SystemAuthorityInfoDao
    extends HibernateDao<SystemCodeNodeInfo, String> {
   
	/**
	 * 根据角色与代码类型得到该角色下所有代码权限
	 * @param roleId 角色id
	 * @param codeType 代码类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityData> getCodeRestrictValue(String roleId,Object codeType){
		List<AuthorityData> authList = new ArrayList<AuthorityData>();
		String hql = " From SystemCodeInfo code,AuthorityData auth,DatabaseFieldInfo field" +
				" Where auth.fieldInfo = field.id and code.id = field.codeOid and " +
				"auth.role.id=? and code.codeType=?";
		//角色数据权限下有很多其他信息，这里只取代码类别下的数据权限
		List<Object[]> restrictValueList = this.find(hql, roleId,codeType);
		for(int i=0;i<restrictValueList.size();i++){
			Object[] obj = restrictValueList.get(i);
			authList.add((AuthorityData) obj[1]);
		}
		return authList;
	}
}
