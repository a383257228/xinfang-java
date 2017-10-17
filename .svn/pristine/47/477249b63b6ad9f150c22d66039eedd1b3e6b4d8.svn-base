package com.sinosoft.authority.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.util.TimeUtils;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 用户角色信息dao层
 * 
 * @author sunzhe 
 * Create 2010-08-10
 */
@Repository
public class AuthorityUserRoleDao extends HibernateDao<AuthorityUserRole, String> {

	/**
	 * 删除用户角色信息
	 * @param roleId 角色ID
	 */
	public void deleteByRole(String roleId) {
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("roleOid", roleId);
		this.batchExecute("delete from AuthorityUserRole Where role_oid=:roleOid",values);
	}
	
	/**
	 * 根据条件删除用户角色信息
	 * @param key 条件
	 * @param value 查询值
	 */
	public void deleteByType(String key,String value){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("key", value);
		this.batchExecute("delete from AuthorityUserRole Where "+key+"=:key",values);
	}
	
	/**
	 * 根据用户ID，查找他所具有的所有角色
	 * @param userId 用户ID
	 * @return 人员角色数组
	 */
	public List<AuthorityUserRole> getRoleIdByUserId(String userId){
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("userId", userId);
		values.put("expireDate",TimeUtils.getTimestamp());
		
		
		
		// 根据查询条件，查询
		List<AuthorityUserRole> list = this.find("from AuthorityUserRole Where person=:userId and expireDate>:expireDate",values);
		return list;
	}
	/**
	 * 根据角色ID，查找它所关联的所有用户
	 * @param userId 用户ID
	 * @return 人员角色数组
	 * @author zhangxy
	 * @crate 2011-04-26
	 */
	public List<AuthorityUserRole> getUserIdByRoleId(String roleId){
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("roleId", roleId);
		//根据查询条件，查询
		List<AuthorityUserRole> list = this.find("from AuthorityUserRole Where role.id=:roleId",values);
		return list;
	}
	
	/**
	 * 查询人员列表
	 * @param filterT 
	 * 			查询字段名称
	 * @param filterV 
	 * 			查询字段值
	 * @param start 
	 * 			查询记录开始号
	 * @param limit 
	 * 			每页显示的记录数量
	 * @param sort
	 *            排序字段
	 * @param dir
	 *            排序方式          
	 * @return  List
	 * 			返回人员关系记录
	 */
	@SuppressWarnings({ "rawtypes" })
	public List queryAuthorityUser(String filterT,String filterV,int start,int limit,String sort,String dir) throws IOException{
		Map<String,Object> values = new HashMap<String,Object>();
//		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		//函数返回值
		List list = new ArrayList();
		//查询语句
		String querySql = " from PersonInfo p,OrganPersonRelationInfo op , " +
				"OrganizationInfo og,OrganizationRelationInfo ori where ori.organizationInfo.id=og.id" +
				" and op.personInfo.id = p.id and op.organizationInfo.id = og.id and p.invalidFlag='1'" +
				"and p.userEname not like '%admin' and p.userEname not like '%secadmin' and p.userEname" +
				" not like '%secauditor' and og.invalidFlag='1'";
		//构建排序
		String order = "";
		if(sort != null&&!sort.equals(""))
		{
			if(sort.equals("Person_CName"))
				sort = " p.userCname";
			else if(sort.equals("Org_Dept_Name"))
				sort = " op.organizationInfo.orgCname";
			else if(sort.equals("Person_EName"))
				sort = " p.userEname";
			order = " order by "+sort+" "+dir;
		}
		//如果filterT不为空，则组装SQL
		if(filterT!=null&&!"".equals(filterT)){
			String[] filterName = filterT.split(";");
			String[] filterValue = filterV.split(";");
			//判断查询项是否为空
			//循环设置查询参数
			for(int i=0;i<filterName.length;i++){
				//查询已经保存过的用户
				if(filterName[i]!=null&&"ids".equals(filterName[i])){
					querySql += " and p.id in ("+filterValue[i]+")";
				}
				//用户英文名称
				if(filterName[i]!=null&&"personName".equals(filterName[i])){
					values.put("queryName", filterValue[i]);
					querySql += " and (p.userEname like :queryName or p.userCname like :queryName)";
				}
				//用户中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					querySql += " and p.userCname like :personCName ";
				}
				//用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					querySql += " and op.organizationInfo.id = :organizationInfoId ";
				}	
				//区域
				if(filterName[i]!=null&&"regionCode".equals(filterName[i])){
					querySql += " and p.regionCode like :regionCode ";
				}
			}

			querySql = querySql + order;

			//创建查询器
			Query query = this.createQuery(querySql,values);
			for(int i=0;i<filterName.length;i++){
				//设置查询中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					query.setString("personCName", filterValue[i]);
				}
				//设置查询用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					query.setString("organizationInfoId", filterValue[i]);
				}
				if(filterName[i]!=null&&"regionCode".equals(filterName[i])){
					query.setString("regionCode", filterValue[i]);
				}
			}
			//分页查询
			query.setFirstResult(start).setMaxResults(limit);
			//返回值
			list = query.list();
		}else{
			querySql = querySql + order;
			Query query = this.createQuery(querySql,values);
			//分页查询
			query.setFirstResult(start).setMaxResults(limit);
			//返回值
			list = query.list();

		}

		return list;
	}
	
	/**
	 * 查询人员列表总数
	 * @param filterT 
	 * 			查询参数字段名称
	 * @param filterV 
	 * 			查询参数字段值
	 * @return int
	 * 			人员记录总数
	 */
	public int queryAuthorityUserCount(String filterT,String filterV){
		Map<String,Object> values = new HashMap<String,Object>();
		//方法返回值
		int count =0;
//		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		//查询人员信息总数语句
		String querySql = "select count(op.id) from OrganPersonRelationInfo op ,PersonInfo p, " +
				"OrganizationInfo og,OrganizationRelationInfo ori where ori.organizationInfo.id=og.id" +
				" and op.personInfo.id = p.id and op.organizationInfo.id = og.id and p.invalidFlag='1'"+
				" and p.userEname not like '%admin' and p.userEname not like '%secadmin' and p.userEname" +
				" not like '%secauditor' and og.invalidFlag='1'";
		Query query = null;
		//如果filterT不为空，则组装SQL
		if(filterT!=null&&!"".equals(filterT)){
			String[] filterName = filterT.split(";");
			String[] filterValue = filterV.split(";");
			//判断查询项是否为空

			//循环设置查询参数
			for(int i=0;i<filterName.length;i++){
				if(filterName[i]!=null&&"ids".equals(filterName[i])){
					querySql += " and p.id in ("+filterValue[i]+")";
				}
				if(filterName[i]!=null&&"personName".equals(filterName[i])){
					values.put("queryName", filterValue[i]);
					querySql += " and (p.userEname like :queryName or p.userCname like :queryName)";
				}
				//用户中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					querySql += " and p.userCname like :personCName ";
				}
				//用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					querySql += " and op.organizationInfo.id = :organizationInfoId ";
				}
				//区域
				if(filterName[i]!=null&&"regionCode".equals(filterName[i])){
					querySql += " and p.regionCode like :regionCode ";
				}
			}
			//创建查询器
			query = this.createQuery(querySql,values);
			//设置值英文名称
			for(int i=0;i<filterName.length;i++){
//				//设置查询中文名称
				if(filterName[i]!=null&&"personCName".equals(filterName[i])){
					query.setString("personCName", filterValue[i]);
				}
				//设置查询用户部门id
				if(filterName[i]!=null&&"organizationInfoID".equals(filterName[i])){
					query.setString("organizationInfoId", filterValue[i]);
				}
				if(filterName[i]!=null&&"regionCode".equals(filterName[i])){
					query.setString("regionCode", filterValue[i]);
				}
			}
		//如果为空，则直接查询	
		}else{
			//创建查询器
			query = this.createQuery(querySql,values);
		}
		
		//获取记录总数
		count = ((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	/**
	 * 删除用户角色信息
	 * @param roleId 角色id
	 * @param userId 用户id
	 */
	public void deleteByRoleAndUser(String roleId,String userId) {
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("roleOid", roleId);
		values.put("userId", userId);
		this.batchExecute("delete from AuthorityUserRole Where role_oid=:roleOid and person=:userId",values);
	}
	/**
	 * 根据用户查询该用户的角色信息
	 * 
	 * @author zhul
	 * @date 2016-05-13
	 * @param personId 用户唯一标识
	 * @return 角色id串
	 */
	@SuppressWarnings("unchecked")
	public String queryRoleIdsByPerson(String personId){
		DateFormat format = DateFormat.getDateInstance();
		String date = format.format(new Date());
		String roleIds = "";
		String sql = "SELECT CAST(UR.ROLE_OID AS VARCHAR(20)) FROM AUTHORITY_USER_ROLE UR WHERE UR.USER_OID =:personId AND "
				+ "UR.ROLE_OID IN (SELECT R.OID FROM AUTHORITY_ROLE R WHERE INVALID_FLAG  = '1' "
				+ "AND (R.EXPIRE_DATE IS NULL OR R.EXPIRE_DATE >=TO_DATE(:date,'yyyy-mm-dd')))";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter("personId", personId);
		query.setParameter("date", date);
		List<Object> ids = query.list();
		if(ids !=null && ids.size()>0){
			for(int i=0; i< ids.size() ;i++){
				roleIds += ids.get(i) + "','";
			}
			roleIds = roleIds.substring(0, roleIds.length()-3);
		}
		return roleIds;
	}
}
