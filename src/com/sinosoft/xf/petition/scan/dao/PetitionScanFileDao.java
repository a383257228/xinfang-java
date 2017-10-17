package com.sinosoft.xf.petition.scan.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.RoleStr;
import com.sinosoft.xf.petition.scan.domain.PetitionScanFile;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 扫描文件Dao
 */
@Repository
@SuppressWarnings("unchecked")
public class PetitionScanFileDao extends HibernateDao<PetitionScanFile,String>{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 查询列表个数
	 */
	public int listScanFileCount(Map<String, Object> map,String filterTxt, String filterValue) throws Exception{
		Criteria criteria = appListScanFile(map,filterTxt, filterValue);
		Object i = criteria.setProjection(Projections.count("id")).uniqueResult();
		return i!=null?(Integer)i:0;
	}

	/**
	 * 列表查询
	 * @return
	 */
	public List<PetitionScanFile> listScanFile(Map<String, Object> map,String filterTxt, String filterValue, String sort, String dir, int start, int limit) throws Exception{
		Criteria criteria = appListScanFile(map,filterTxt, filterValue);
		if (sort != null && !"".equals(sort)) {
			boolean isAsc = dir.equalsIgnoreCase(Constants.ASC);
			if (isAsc)
				criteria.addOrder(Order.asc(sort));
			else
				criteria.addOrder(Order.desc(sort));
		}
		criteria.setFirstResult(start).setMaxResults(limit);
		return criteria.list();
	}
	/**
	 * 列表查询条件拼接
	 * isSreator 是否添加上传人权限
	 * @param map
	 * @param filterTxt
	 * @param filterValue
	 * @return
	 * @throws ParseException 
	 */
	private Criteria appListScanFile(Map<String, Object> map,String filterTxt, String filterValue) throws ParseException {
		Criteria criteria = this.getSession().createCriteria(PetitionScanFile.class);
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		String roleStr = Struts2Utils.getSessionAttribute("roleStr").toString();
		criteria.add(Restrictions.eq("invalidFlag","1"));
		//如果是扫描查看模块，就添加一下权限控制
		if(map.get("scanView")!=null && "true".equals(map.get("scanView").toString())){
			//信访秘书、信访领导、业务管理员查看所有扫描件
			if((RoleStr.XFS_SECRETARY.toString().equals(roleStr)
					||RoleStr.XFS_LEADER.toString().equals(roleStr)
					||RoleStr.BUSINESS_ADMIN.toString().equals(roleStr))){
				if(!person.getRegionCode().equals(Constants.SH_REGION_CDOE)){
					criteria.add(Restrictions.eq("regionCode", person.getRegionCode()));
				}
			}else if (RoleStr.LEADER_MAIN.toString().equals(roleStr)) {//委领导查看分管部门所有扫描件
				String[] deptCodes = person.getUserDescription().split(",");
				criteria.add(Restrictions.in("deptCode", deptCodes));
			}else if (RoleStr.BXS_SECRETARY.toString().equals(roleStr)
					||RoleStr.AGS_SECRETARY.toString().equals(roleStr)) {//办信室、案管室秘书可以看到本部门所有扫描件
				String deptCode = (String)Struts2Utils.getSessionAttribute("deptCode");
				criteria.add(Restrictions.eq("deptCode", deptCode));
			}else if (RoleStr.XFS_GENERAL.toString().equals(roleStr) 
					&& person.getRegionCode().equals(Constants.SH_REGION_CDOE)){//上海市的信访室承办人员
				//上海市的信访室承办人员，并且拥有“局级件办理”角色的所有用户，在扫描查看能看到彼此的扫描件
				//查看上海市局级件办理角色的所有用户id
				StringBuffer sqlBuffer = new StringBuffer("");
				sqlBuffer.append("select person.oid personId from person_info person")
						 .append(" left join authority_user_role userrole")
						 .append(" on person.oid = userrole.user_oid")
						 .append(" left join authority_role  role")
						 .append(" on userrole.role_oid = role.oid")
						 .append(" where role.role_name = '局级件办理'")
						 .append(" and person.region_code = '"+person.getRegionCode()+"'");
				List<String> userIds = jdbcTemplate.queryForList(sqlBuffer.toString(),String.class);
				//如果局级件办理角色用户中有当前用户，就添加权限控制
				if(userIds!=null && !userIds.isEmpty()&&userIds.contains(person.getId())){
					criteria.add(Restrictions.in("creatorId",userIds.toArray()));
				}else if(map.get("isCreator")!=null){
					criteria.add(Restrictions.eq("creatorId", person.getId()));
				}
			}else{//其他用户根据isCreator参数是否传递判断是否查看自录扫描件
				if(map.get("isCreator")!=null){
					criteria.add(Restrictions.eq("creatorId", person.getId()));
				}
			}
		}
		if (filterTxt != null && !"".equals(filterTxt.trim())) {
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			for(int i = 0; i < filterValues.length; i++) {
				String stringTxt = filterTxts[i];
				String stringValue = filterValues[i];
				Date dateValue=new Date();
				if("createDate".equals(stringTxt)){
					SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
					dateValue=sim.parse(stringValue);
				}
				if (stringValue != null && !"".equals(stringValue)) {
					if("monthStart".equals(stringTxt)){
						criteria.add(Restrictions.ge("createDate", stringValue));
						continue;
					}
					if("monthEnd".equals(stringTxt)){
						criteria.add(Restrictions.le("createDate", stringValue));
						continue;
					}
					if("createDate".equals(stringTxt)){
						criteria.add(Restrictions.ge(stringTxt, dateValue));
						continue;
					}
					if("createDates".equals(stringTxt)){
						criteria.add(Restrictions.le("createDate", dateValue));
						continue;
					}
					if("scanType".equals(stringTxt)){
						criteria.add(Restrictions.eq(stringTxt, stringValue));
						continue;
					}else {
						criteria.add(Restrictions.like(stringTxt, stringValue,MatchMode.ANYWHERE));
					}
				}
			}
		}
		return criteria;
	}
}
