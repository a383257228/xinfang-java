package com.sinosoft.xf.system.logon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@Repository
public class PetitionOrganInfoDao  extends HibernateDao<OrganizationInfo,String>{
	/**
	 * 注入jdbcTemplate
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 日志查询的用户下拉框的查看
	 * @author guoh
	 * @date 2017-07-24
	 * @throws IOException
	 */
	public List<Map<String,Object>> queryLogOnUsers(String currDeptCode){
		 List<Map<String,Object>> list=jdbcTemplate.queryForList("select distinct login.user_Id userId,login.user_Name deptName from Login_Log login  where login.dept_Id!=''");
		 return list;
	}
	/**日志查询的统计机构下拉框的查看
	 * @author guoh
	 * @date 2017-07-25
	 * @throws IOException
	 */
	public List<Map<String,Object>> getGroupDeptInfos(){
		 List<Map<String,Object>> list=jdbcTemplate.queryForList("select login.dept_Id deptId,max(login.dept_Name) deptName from Login_Log login where login.dept_Id!='' group by login.dept_Id");
		 return list;
	}
	/**
	 * 通过信访方式查询区域权限
	 * @param petitionTypeCode 信访方式
	 * @return 用户权限集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getAuthorityUserRoleByPetitionTypeCode(String petitionTypeCode,boolean hasSetPetitionAuthor){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer sql = new StringBuffer();
		sql.append("select region_code ");
		sql.append("from Authority_User_Role aur ");
		sql.append("left join AUTHORITY_ROLE ar on aur.role_oid = ar.oid "); 
		sql.append("left join AUTHORITY_DATA ad on ad.role_oid = ar.oid ");
		if(hasSetPetitionAuthor){
			sql.append("where ad.add_restrict_value like '%"+petitionTypeCode+"%' ");
			sql.append("and ad.table_oid='00fd013df846aade0441' and column_oid='00fd013e633f6a9d0043' ");
		}
		sql.append("and aur.user_oid='"+person.getId()+"'");
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 通过orgCode查询orgName,并且按照orgCode进行正序排列
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getOrgCnames(String sql){
		if(sql==null||"".equals(sql))
			return new ArrayList<Map<String,Object>>();
		StringBuffer sb = new StringBuffer("");
		sb.append("select org_cname from organization where org_code in("+sql+") order by org_code asc");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}
	/**
	 * 通过regionCode获取下级机构
	 * @date 2012-03-06
	 * @author 
	 * @param regionCode 
	 * @return 保存查询list
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationInfo> getChildrensOrganization(String regionCode,String orgCodeSubstr,String orgCodeLike,Set<String> authorityRegions) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationInfo.class,"query");
		criteria = criteria.add(Restrictions.like("orgCode",orgCodeSubstr + orgCodeLike));
		criteria = criteria.add(Restrictions.ne("orgCode", regionCode));
		criteria = criteria.add(Restrictions.eq("purpose", "1"));
		criteria = criteria.add(Restrictions.in("orgCode", authorityRegions));
		return criteria.list();
	}
	
	/**
	 * 根据机构编码和有效标示查询组织关系信息
	 * @param orgCode 组织机构编码
	 * @return 组织关系集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrgRelaInfoByOrgCodeAndinvalidFlag(final String orgCode,String invalidFlag,String orgName,String purpose) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria.addOrder(Order.asc("organOrder"));
		criteria = criteria.createCriteria("organizationInfo");
		if(!orgCode.equals("")){
			criteria.add(Restrictions.like("orgCode", orgCode));
		}
		if(!invalidFlag.equals("")){
			criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
		}
		if(!orgName.equals("")){
			criteria.add(Restrictions.like("orgCname", orgName));
		}
		if(!purpose.equals("")){
			criteria.add(Restrictions.eq("purpose", purpose));
		}
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
    /**
     * 条件查询本机及下一级数据
     * @param regionCode
     * @param filterText
     * @param filterValue
     * @param purpose
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Object[]> getOrgRelaInfo(String regionCode,String filterText, String filterValue,
			String purpose) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class,"orgRela");
		criteria.createAlias("organizationInfo", "org");
		criteria = criteria.add(Restrictions.like("org.orgCode", regionCode));
		criteria.createAlias("childrens", "childrens");
		criteria = criteria.createCriteria("childrens.organizationInfo","child");
		if(filterText!=null && !filterText.trim().equals("") && filterValue!=null && !filterValue.equals("")){
			String[] texts=filterText.split(";");
			String[] values=filterValue.split(";");
			for(int i=0;i<texts.length;i++){
				if(texts[i].equals("orgName")){
					criteria.add(Restrictions.like("child.orgCname", "%"+values[i]+"%"));
				}else if(texts[i].equals("invalidFlag")){
					criteria.add(Restrictions.eq("child.invalidFlag", values[i]));
				}else if(texts[i].equals("orgCode")){
					criteria.add(Restrictions.like("child.orgCode", "%"+values[i]+"%"));
				}
			}
		}
		if(!purpose.equals("")){
			criteria.add(Restrictions.eq("child.purpose", purpose));
		}
		criteria.addOrder(Order.asc("orgRela.organOrder"));
		ProjectionList proList = Projections.projectionList();//设置投影集合 
		proList.add(Projections.property("childrens.id"));
		proList.add(Projections.property("child.orgCname"));
		proList.add(Projections.property("child.orgShortCname"));
		proList.add(Projections.property("childrens.organOrder"));
		proList.add(Projections.property("child.invalidFlag"));
		proList.add(Projections.property("org.orgCname"));
		proList.add(Projections.property("child.orgCode"));
		proList.add(Projections.property("child.id"));
		proList.add(Projections.property("child.purpose"));
		criteria.setProjection(proList);
		List<Object[]> list = criteria.list();
		return list;
	}
	
	/**
	 * 根据组织关系表父关系对象Oid数组和组织对象的purpose属性，有效标识查找集中式部署的组织关系对象集合
	 * @author hxh 2013-7-10
	 * 被哪些方法调用：
	 * 1、petitionOrganInfoManager.getOrganColumnsTree
	 * 1、petitionOrganInfoManager.getOrganUserColumnsTree
	 * @param parentOrgRelaOid 上级组织关系对象oid
	 * @param purpose 组织对象的身份，查找机构，部门，还是全部
	 * @param invalidFlag 组织基本信息有效标识
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<OrganizationRelationInfo> getOrganRelaInfoByParentOid(
			String purpose,String parentOrgRelaOid,String invalidFlag) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		//添加排序
		criteria.addOrder(Order.asc("organOrder"));
		if(parentOrgRelaOid!=null&&!parentOrgRelaOid.equals(""))
			criteria.add(Restrictions.eq("organizationRelationInfo.id", parentOrgRelaOid));
		criteria = criteria.createCriteria("organizationInfo");
		if("2".equals(purpose)){//部门
			criteria = criteria.add(Restrictions.eq("purpose", "2"));
			
		}else if("1".equals(purpose)){//区域
			//查询集中式部署区域
			//保存集中式部署区域code
			ArrayList<String> centralizedDeploymentRegionCode = new ArrayList<String>();
			Map<String ,String> map = CodeSwitchUtil.getCentralizedMap();
			for (String key : map.keySet()) {
				centralizedDeploymentRegionCode.add(key);
			}
			criteria=criteria.add(Restrictions.or(Restrictions.in("orgCode", centralizedDeploymentRegionCode)
					, Restrictions.eq("purpose", "2")));
		}
		if(!"".equals(invalidFlag)){
			criteria = criteria.add(Restrictions.eq("invalidFlag", invalidFlag));
		}
		return criteria.list();
	}
	/**
	 * 查询当前用户所具有的全部有效角色的Role_oid的集合
	 * @author xuyi
	 * @date 2014-01-23
	 * 被哪些方法调用：
	 1、getAllValidAuthorityRegionWithPetitionTypeCode()
	 * 		获取当前用户有效角色的带有信访方式的问题属地权限
	 * @return 角色的Role_oid的集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllRolesForOnlineUser() {
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer sql = new StringBuffer();
		sql.append("select userRole.role_oid ");
		sql.append("from authority_user_role userRole ");
		sql.append("where userRole.user_oid='"+person.getId()+"' "); 
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 查询当前用户所具有的有效角色所具有的问题属地权限code集合
	 * @author xuyi
	 * @date 2014-01-23
	 * 被哪些方法调用：
	 * 1、getAllValidAuthorityRegionWithPetitionTypeCode()
	 * 		获取当前用户有效角色的带有信访方式的问题属地权限
	 * @param roleIds
	 * @return 问题属地code集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAuthorityUserRoleId(String roleIds) {
		StringBuffer sql = new StringBuffer();
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		sql.append("select userRole.region_code ");
		sql.append("from authority_user_role userRole ");
		sql.append("where userRole.role_oid in ("+roleIds+") "); 
		sql.append("and userRole.user_oid='"+person.getId()+"' "); 
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 查询信访方式读取问题属地权限角色的Role_oid的集合
	 * @author xuyi
	 * @date 2014-01-09
	 * 被哪些方法调用：
	 * 1、getAllValidAuthorityRegionWithPetitionTypeCode()
	 * 		获取当前用户有效角色的带有信访方式的问题属地权限
	 * @return 读取字段权限的List集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getReadAuthorityList(String petitonTypeCodeOid,String issueRegionOid){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer sql = new StringBuffer();
		sql.append("select data_1.role_oid roleOid,data_1.select_able selectAble1,data_2.select_able selectAble2, ");
		sql.append("data_1.column_oid columnOid1,data_2.column_oid columnOid2, ");
		sql.append("data_1.add_restrict_value restrictValue ");
		sql.append("from authority_data data_1 ");
		sql.append("inner join authority_data data_2 on data_1.role_oid=data_2.role_oid ");
		sql.append("inner join authority_user_role userRole on userRole.role_oid=data_1.role_oid ");
		sql.append("where data_1.column_oid='"+petitonTypeCodeOid+"' ");
		sql.append("and data_2.column_oid='"+issueRegionOid+"' ");
		sql.append("and userRole.user_oid='"+person.getId()+"' ");
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 获取库表中信访方式和问题属地字段的Id值
	 * @author xuyi
	 * @date 2014-01-23
	 * 被哪些方法调用：
	 * 1、getAllValidAuthorityRegionWithPetitionTypeCode()
	 * 		获取当前用户有效角色的带有信访方式的问题属地权限
	 * @return 库表中信访方式和问题属地字段对应的记录的实体集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDatabaseFieldInfoList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select  db.oid,db.field_cname,db.field_ename from database_field_info db ");
		sql.append("where  db.is_authority='true' and  db.is_show='1' ");
		sql.append("and (db.field_cname like '%问题属地%' or db.field_cname like '%信访方式%') ");
		return jdbcTemplate.queryForList(sql.toString());
	}
}
