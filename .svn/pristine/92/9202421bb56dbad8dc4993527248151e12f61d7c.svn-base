/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 17 2010
 * @author shenhy
 */
package com.sinosoft.organization.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.dao.GroupItemInfoDao;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 机构用户关系业务层
 * 
 * @author shenhy
 * May 17 2010
 */
@Service
@Transactional
public class OrganPersonRelationManager extends
		EntityManager<OrganPersonRelationInfo, String> {
	
	/** 注入机构用户关系dao层类 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;

	/**
	 * 注入人员信息dao层
	 */
	@Autowired
	PersonInfoDao personInfoDao;
	
	/**
	 * 注入机构信息dao层
	 */
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	/**
	 * 注入机构关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	
	/**
	 * 注入机构分组信息dao层
	 */
	@Autowired
	GroupItemInfoDao groupItemInfoDao;

	//注入机构关系业务层
	@Autowired
	OrganizationRelationInfoManager organizationRelationInfoManager;
	
	protected HibernateDao<OrganPersonRelationInfo, String> getEntityDao() {
		return organPersonRelationDao;
	}
	/**
	 * 通过属性查询机构用户关系信息
	 * @date 2011-04-19
	 * @author wangxx
	 * 用途：
	 * 	1、目录项目
	 * @return
	 */
	public ExtjsPage<OrganPersonRelationInfo> organRelaPersonPagedQuery(
			String filterTxt,String filterValue,int start,int limit,String sort,String dir){
		int size = organPersonRelationDao.organRelaPersonPagedQuery(filterTxt, filterValue);
		List<OrganPersonRelationInfo> result = organPersonRelationDao
			.organRelaPersonPagedQuery(filterTxt, filterValue, start, limit, sort, dir);
		ExtjsPage<OrganPersonRelationInfo> page = new ExtjsPage<OrganPersonRelationInfo>();
	    page.setResult(result);
	    page.setTotalCount(size);
	    return page;
	}
	/**
	 * 根据查询参数，查询人员信息列表
	 * 
	 * @date 2010年 5月12日
	 * @author shenhy 
	 * @param filterT 查询参数字段名称
	 * @param filterV查询参数字段值
	 * @param start查询开始标号
	 * @param limit页记录数
	 * @return grid的json数据
	 */
	@Transactional(readOnly = true)
	public String queryPersonInfo(String filterT, String filterV, int start
			,int limit,String sort,String dir) {
		// 查询总的记录数
		int size = 0;
		// 查询总记录数
		size = organPersonRelationDao.queryPersonCount(filterT, filterV);
		// 函数返回值
		StringBuffer json = new StringBuffer("{totalProperty:");
		json.append(size);
		json.append(",result:[");
		// 根据查询参数，查询对象
		List<Object[]> list = organPersonRelationDao.queryPersonInfo(filterT
			, filterV,start, limit,sort,dir);
		// 对象数组
		Object[] obj = null;
		// 循环List对象，封装查询结果信息
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 获取对象数据
				obj = (Object[]) list.get(i);
				// 获取人员机构关系对象
				OrganPersonRelationInfo organPersonRelationInfo = (OrganPersonRelationInfo) obj[1];
				// 获取人员信息对象
				PersonInfo personInfo = (PersonInfo) obj[0];
				// 获取机构实体
				OrganizationInfo organizationInfo = (OrganizationInfo) obj[2];
				if (i > 0) {
					json.append(",");
				}
				json.append("{Relation_Oid:'");
				json.append(organPersonRelationInfo.getId());
				json.append("',");
				// 用户中文名
				json.append("Person_CName:'");
				json.append(personInfo.getUserCname());
				json.append("',");
				// 用户所属机构
				json.append("Parent_Org_ID:'");
				json.append(organizationInfo.getOrgCname());
				json.append("',");
				// 用户英文名称
				json.append("Person_EName:'");
				json.append(personInfo.getUserEname());
				json.append("',");
				// 用户职位
				json.append("User_Position:'");
				json.append(organPersonRelationInfo.getUserPosition());
				json.append("',");
				// 创建时间
				json.append("Create_Date:'");
				json.append(personInfo.getCreateDate() == null ? "": 
					(personInfo.getCreateDate()).toString().substring(0, 10));
				json.append("',");
				// 修改时间
				json.append("Modify_Date:'");
				json.append(personInfo.getModifyDate() == null ? "" : 
					personInfo.getModifyDate().toString().substring(0, 10));
				json.append("',");
				// 修改时间
				json.append("Person_ID:'");
				json.append(personInfo.getId());
				json.append("',");
				// 失效时间
				json.append("Invalid_Date:'");
				json.append(personInfo.getInvalidDate() == null ? "": 
					personInfo.getInvalidDate().toString().substring(0, 10));
				json.append("',");
				// 用户是否失效
				json.append("Invalid_Flag:'");
				json.append(personInfo.getInvalidFlag());
				json.append("'}");
			}
		}
		json.append("]}");
		// 方法返回值
		return json.toString();
	}

	/**
	 * 删除机构和人员的关系信息，同时还会删除分组信息，如果不删除分组信息，
	 * 	读取分组数据是会报错
	 * @date 2010-11-27  
	 * @author wangxx
	 * @param organPersonRelationInfoOid  机构用户关系主键      
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOrganPersonRelationInfo(String organPersonRelationInfoOid) {
		// 删除人员操作
		OrganPersonRelationInfo organPersonRelationInfo = organPersonRelationDao.get(organPersonRelationInfoOid);
		personInfoDao.delete(organPersonRelationInfo.getPersonInfo());
//		organPersonRelationDao.delete(organPersonRelationInfoOid);
		//在删除人员信息之前删除分组信息
		List<GroupItemInfo> list = groupItemInfoDao
			.getGroupItemInfoByPersonOid(organPersonRelationInfoOid);
		for (int j = 0; j < list.size(); j++) {
			groupItemInfoDao.delete(list.get(j).getId());
		}
	}
	/**
	 * 通过部门oid查询其关联的人员信息，并形成树形json返回
	 * @date July 28 2010 
	 * @author wangxx 
	 * 用途：
	 * 	1、拆分部门时显示待分配的用户信息
	 * return 一个json的树形数组
	 */
	@Transactional(readOnly = true)
	public String getOrganPersonRelationInfoByOrganizationOid(String organOid){
		StringBuffer json=new StringBuffer("[");
		List<OrganPersonRelationInfo> list = organPersonRelationDao
			.getOrganPersonRelationInfoByOrganOid(organOid);
		for (int i = 0; i < list.size(); i++) {
			if(i>0){
				json.append(",");
			}
			json.append("{");
			json.append("id:'" + ((OrganPersonRelationInfo) list.get(i)).getId()+ "'");
			json.append(",text:'" + ((OrganPersonRelationInfo) list.get(i))
					.getPersonInfo().getUserCname()+ "'");
			json.append(",leaf:true" );
			json.append(",expanded:false");
			json.append(",checked:false");
			json.append(",allowDelete:true"); 
			json.append("}");
		}
		json.append("]");
		return json.toString();
	}
	/**
	 * 通过部门编码查询其关联的人员信息，并形成树形json返回
	 * @date 2012-01-11
	 * @author oba
	 * 用途：
	 * 	1、展示该部门下的用户
	 * return 一个json的树形数组
	 */
	@Transactional(readOnly = true)
	public String getOrganPersonRelationInfoByOrganCode(String organCode){
		StringBuffer json=new StringBuffer("[");
		List<OrganPersonRelationInfo> list = organPersonRelationDao
			.getOrganPersonRelaListByOrgan("orgCode",organCode);
		for (int i = 0; i < list.size(); i++) {
			if(i>0){
				json.append(",");
			}
			json.append("{");
			json.append("id:'" + ((OrganPersonRelationInfo) list.get(i)).getId()+ "'");
			json.append(",text:'" + ((OrganPersonRelationInfo) list.get(i))
					.getPersonInfo().getUserCname()+ "'");
			json.append(",ename:'"+((OrganPersonRelationInfo) list.get(i))
					.getPersonInfo().getUserEname()+"'" );
			json.append(",code:'"+((OrganPersonRelationInfo) list.get(i))
					.getPersonInfo().getUserCode()+"'" );
			json.append(",leaf:true" );
			json.append(",expanded:false");
			json.append(",allowDelete:true"); 
			json.append("}");
		}
		json.append("]");
		return json.toString();
	}

	/**
	 * 分级加载用户列表树
	 * @date 2010-12-07
	 * @author wangxx
	 * 调用哪些方法:
	 * 1、organPersonRelationDao.getOrganPersonRelaListByPersonName
	 * 2、organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization
	 * 3、organPersonRelationDao.getOrganPersonRelationInfoByOrganOid
	 * 4、organizationRelationInfoDao.getOrganRelaInfoByParentOid
	 * 更新1：
	 * 在原有基础上增加了查询功能
	 * @date 2011-05-03
	 * @author wangxx
	 * 步骤：
	 * 	1、通过参数filterValue来判断是否是查询
	 * 		如果是查询，分别用该参数分别查询对应的组织和人员，但是组织是不含下级组织的
	 * 		如果不是查询，根据节点信息查询下级组织和人员信息
	 * 用途：
	 *  1、用于加载组织用户页面右侧的列表树（显示中文名）
	 * @param node 该节点id 也就是组织关系oid
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param invalidFlag  是否显示无效 “”为全部  1为显示有效   2为显示无效
	 * @param filterValue 查询所用到的参数
	 * @return tree的json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String getOrganUserColumnsTree(String node,String text,String invalidFlag,String filterValue){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		if(null!=filterValue&&!"".equals(filterValue.trim())){
			//通过查询查询组织用户关系信息
			boolean firstItem = false;
			//用户所属区域
			List<OrganPersonRelationInfo> list = organPersonRelationDao.getOrganPersonRelaListByPersonName(filterValue);
			for (int i = 0; i < list.size(); i++) {
				OrganPersonRelationInfo opri = list.get(i);
				if(firstItem){
					treeJson.append(",");
				}
				String createDate = "";
				if(opri.getPersonInfo().getCreateDate()!=null){
					createDate = opri.getPersonInfo().getCreateDate().toString().substring(0, 10);
				}
				String modifyDate = "";
				if(opri.getPersonInfo().getModifyDate()!=null){
					modifyDate = opri.getPersonInfo().getModifyDate().toString().substring(0, 10);
				}
				String invalidDate = "";
				if(opri.getPersonInfo().getInvalidDate()!=null){
					invalidDate = opri.getPersonInfo().getInvalidDate().toString().substring(0, 10);
				}
				treeJson.append("{");
				treeJson.append("id:'" + opri.getId()+ "'");
				treeJson.append(",text:'" + opri.getPersonInfo().getUserCname()+ "'");
				treeJson.append(",organCname:'" + opri.getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",userEname:'" + opri.getPersonInfo().getUserEname()+ "'");
				treeJson.append(",userPosition:'" + opri.getUserPosition()+ "'");
				treeJson.append(",createDate:'" +createDate + "'");
				treeJson.append(",modifyDate:'" +modifyDate + "'");
				treeJson.append(",invalidDate:'" +invalidDate + "'");
				treeJson.append(",invalidFlag:'" + opri.getPersonInfo().getInvalidFlag()+ "'");
				treeJson.append(",leaderFlag:'" + opri.getPersonInfo().getLeaderFlag()+ "'");
				treeJson.append(",userCode:'" + opri.getPersonInfo().getUserCode()+ "'");
				treeJson.append(",operator:'" + opri.getPersonInfo().getOperatorName()+ "'");
				treeJson.append(",organizationInfoId:'" + opri.getOrganizationInfo().getId()+ "'");
				//判断是否含有子节点
				treeJson.append(",leaf:true" );
				treeJson.append(",expanded:false");
				treeJson.append(",iconCls:'add16'" );//显示图片
				treeJson.append(",orgOrDeptOrPerson : 'person'");//显示该节点的身份，为用户
				treeJson.append("}");
				firstItem = true;
			}
			//通过参数查询组织关系信息
			List<OrganizationRelationInfo> orgRelaList = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization(filterValue);
			for (int i = 0; i < orgRelaList.size(); i++) {
				Object sci = (OrganizationRelationInfo) orgRelaList.get(i);
				try {
					if(null!=((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
						((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname();
					}
				} catch (ObjectNotFoundException e) {
					logger.info("有错误数据，错误数据："+((OrganizationRelationInfo) sci).getId());
					continue;
				}
				if(firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				if(text.equals("orgCname")){
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				}else if(text.equals("orgShortCname")){
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				}
				if(null==((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
					treeJson.append(",qtip:''");
				}else{
					treeJson.append(",qtip:'"+((OrganizationRelationInfo) sci).getOrganizationRelationInfo()
							.getOrganizationInfo().getOrgCname()+"'");//添加一个浮动框，父机构名称
				}
				treeJson.append(",organCname:''");
				treeJson.append(",userEname:''");
				treeJson.append(",userPosition:''");
				treeJson.append(",createDate:''");
				treeJson.append(",modifyDate:''");
				treeJson.append(",invalidDate:''");
				treeJson.append(",invalidFlag:''");
				treeJson.append(",leaderFlag:''");
				treeJson.append(",userCode:''");
				treeJson.append(",operator:''");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				//判断是否含有子节点
				treeJson.append(",leaf:true" );
				treeJson.append(",expanded:false");
				if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
					treeJson.append(",orgOrDeptOrPerson : 'org'");//显示该节点的身份，为机构
					treeJson.append(",iconCls:'chart_organisation'" );//显示图片
				}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
					treeJson.append(",orgOrDeptOrPerson : 'dept'");//显示该节点的身份，为部门
					treeJson.append(",iconCls:'partverify'" );//显示图片
				}
				treeJson.append("}");
				firstItem = true;
			}
			treeJson.append("]");
		}else{
			List<OrganizationRelationInfo> list = null ;
			boolean firstItem = true;
			if(node.equals("-1")){
				Object sci;
				//初始化时查询系统定义表
				//list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationRelationInfo is null order by organizationInfo.orgCode");
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
						person.getRegionCode()+"' order by organizationInfo.orgCode");
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					if(text.equals("orgCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "纪委'");
					}else if(text.equals("orgShortCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "纪委'");
					}
					treeJson.append(",code:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
					treeJson.append(",organCname:''");
					treeJson.append(",userEname:''");
					treeJson.append(",userPosition:''");
					treeJson.append(",createDate:''");
					treeJson.append(",modifyDate:''");
					treeJson.append(",invalidDate:''");
					treeJson.append(",invalidFlag:''");
					treeJson.append(",leaderFlag:''");
					treeJson.append(",userCode:''");
					treeJson.append(",operator:''");
					treeJson.append(",organizationInfoId:'"+((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+"'");
					int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
					//判断是否含有子节点
					if(size>0){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
					treeJson.append(",expanded:true");
					treeJson.append(",orgOrDeptOrPerson : 'org'");
					treeJson.append(",iconCls:'cog'" );//显示图片
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
			}else {
				Object sci;
				OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(node);
				List<OrganPersonRelationInfo> userList = organPersonRelationDao.getOrganPersonRelationInfoByOrganOid(organizationRelationInfo.getOrganizationInfo().getId());
				for (int i = 0; i < userList.size(); i++) {
					sci = (OrganPersonRelationInfo) userList.get(i);
					if(i>0){
						treeJson.append(",");
					}
					String createDate = "";
					if(((OrganPersonRelationInfo) sci).getPersonInfo().getCreateDate()!=null){
						createDate = ((OrganPersonRelationInfo) sci).getPersonInfo().getCreateDate().toString().substring(0, 10);
					}
					String modifyDate = "";
					if(((OrganPersonRelationInfo) sci).getPersonInfo().getModifyDate()!=null){
						modifyDate = ((OrganPersonRelationInfo) sci).getPersonInfo().getModifyDate().toString().substring(0, 10);
					}
					String invalidDate = "";
					if(((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidDate()!=null){
						invalidDate = ((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidDate().toString().substring(0, 10);
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganPersonRelationInfo) sci).getId()+ "'");
					treeJson.append(",text:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCname()+ "'");
					treeJson.append(",organCname:'" + ((OrganPersonRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",userEname:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserEname()+ "'");
					treeJson.append(",userPosition:'" + ((OrganPersonRelationInfo) sci).getUserPosition()+ "'");
					treeJson.append(",personInfoOid:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getId()+ "'");
					treeJson.append(",createDate:'" +createDate + "'");
					treeJson.append(",modifyDate:'" +modifyDate + "'");
					treeJson.append(",invalidDate:'" +invalidDate + "'");
					treeJson.append(",invalidFlag:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidFlag()+ "'");
					treeJson.append(",leaderFlag:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getLeaderFlag()+ "'");
					treeJson.append(",userCode:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCode()+ "'");
					treeJson.append(",operator:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getOperatorName()+ "'");
					treeJson.append(",organizationInfoId:'" + ((OrganPersonRelationInfo) sci).getOrganizationInfo().getId()+ "'");
					//判断是否含有子节点
					treeJson.append(",leaf:true" );
					treeJson.append(",expanded:false");
					treeJson.append(",iconCls:'add16'" );//显示图片
					treeJson.append(",orgOrDeptOrPerson : 'person'");//显示该节点的身份，为用户
					treeJson.append("}");
				}
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("2",node,invalidFlag);
				//用于存放有下级节点的关系表oid
				Set<String> set = new HashSet<String>();
				Set<String> orpSet = new HashSet<String>();
				String[] orgRelaOid = new String[list.size()];
				String[] organOid = new String[list.size()];
				for(int i=0;i<list.size();i++){
					OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
					orgRelaOid[i]=ori.getId();
					organOid[i]=ori.getOrganizationInfo().getId();
				}
				List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("2", orgRelaOid, invalidFlag);
				for(int i=0;i<clist.size();i++){
					set.add(clist.get(i).getOrganizationRelationInfo().getId());
				}
				List<OrganPersonRelationInfo> orplist = organPersonRelationDao.getOrganRelaPersonInfoByOrganOid(organOid);
				for (OrganPersonRelationInfo organPersonRelationInfo :orplist) {
					orpSet.add(organPersonRelationInfo.getOrganizationInfo().getId());
				}
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(userList.size()>0){
						treeJson.append(",");
					}else if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					if(text.equals("orgCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
					}else if(text.equals("orgShortCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
					}
					treeJson.append(",qtip:'"+((OrganizationRelationInfo) sci).getOrganizationRelationInfo()
							.getOrganizationInfo().getOrgCname()+"'");//添加一个浮动框，父机构名称
					treeJson.append(",organCname:''");
					treeJson.append(",userEname:''");
					treeJson.append(",userPosition:''");
					treeJson.append(",createDate:''");
					treeJson.append(",modifyDate:''");
					treeJson.append(",invalidDate:''");
					treeJson.append(",invalidFlag:''");
					treeJson.append(",leaderFlag:''");
					treeJson.append(",userCode:''");
					treeJson.append(",operator:''");
					treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
					//判断是否含有子节点
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						if(set.contains(((OrganizationRelationInfo) sci).getId())
								||orpSet.contains(((OrganizationRelationInfo) sci).getOrganizationInfo().getId())){
							treeJson.append(",leaf:false" );
							treeJson.append(",expanded:false");
						}else{
							treeJson.append(",leaf:true" );
							treeJson.append(",expanded:false");
						}
					}else{
						treeJson.append(",leaf:true" );
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",orgOrDeptOrPerson : 'org'");//显示该节点的身份，为机构
						treeJson.append(",iconCls:'chart_organisation'" );//显示图片
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",orgOrDeptOrPerson : 'dept'");//显示该节点的身份，为部门
						treeJson.append(",iconCls:'partverify'" );//显示图片
					}
					
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
			}
			treeJson.append("]");
		}
		return treeJson.toString();
	}

	/**
	 * 分级加载用户列表树，根据不同的参数控制输出项
	 * @date 2011-10-19
	 * @author wangxx
	 * 步骤：
	 * 	1、通过参数filterValue来判断是否是查询
	 * 		如果是查询，分别用该参数分别查询对应的组织和人员，但是组织是不含下级组织的
	 * 		如果不是查询，根据节点信息查询下级组织和人员信息
	 * 用途：
	 *  1、用于加载组织用户页面右侧的列表树（显示中文名）
	 * @param node 该节点id 也就是组织关系oid
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param invalidFlag  是否显示无效 “”为全部  1为显示有效   2为显示无效
	 * @param filterValue 查询所用到的参数
	 * @param showType 显示内容，org只显示机构  person只显示用户 all显示全部
	 * @return tree的json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String getOrganUserColumnsTree(String node,String text,String invalidFlag,String filterValue,String showType){
		StringBuffer treeJson = new StringBuffer("[");
		if(null!=filterValue&&!"".equals(filterValue.trim())){
			//通过查询查询组织用户关系信息
			boolean firstItem = false;
			if("person".equals(showType)||"all".equals(showType)){
				List<OrganPersonRelationInfo> list = organPersonRelationDao.getOrganPersonRelaListByPersonName(filterValue);
				for (int i = 0; i < list.size(); i++) {
					OrganPersonRelationInfo opri = list.get(i);
					if(firstItem){
						treeJson.append(",");
					}
					String createDate = "";
					if(opri.getPersonInfo().getCreateDate()!=null){
						createDate = opri.getPersonInfo().getCreateDate().toString().substring(0, 10);
					}
					String modifyDate = "";
					if(opri.getPersonInfo().getModifyDate()!=null){
						modifyDate = opri.getPersonInfo().getModifyDate().toString().substring(0, 10);
					}
					String invalidDate = "";
					if(opri.getPersonInfo().getInvalidDate()!=null){
						invalidDate = opri.getPersonInfo().getInvalidDate().toString().substring(0, 10);
					}
					treeJson.append("{");
					treeJson.append("id:'" + opri.getId()+ "'");
					treeJson.append(",text:'" + opri.getPersonInfo().getUserCname()+ "'");
					treeJson.append(",organCname:'" + opri.getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",userEname:'" + opri.getPersonInfo().getUserEname()+ "'");
					treeJson.append(",userPosition:'" + opri.getUserPosition()+ "'");
					treeJson.append(",createDate:'" +createDate + "'");
					treeJson.append(",modifyDate:'" +modifyDate + "'");
					treeJson.append(",invalidDate:'" +invalidDate + "'");
					treeJson.append(",invalidFlag:'" + opri.getPersonInfo().getInvalidFlag()+ "'");
					treeJson.append(",userCode:'" + opri.getPersonInfo().getUserCode()+ "'");
					treeJson.append(",operator:'" + opri.getPersonInfo().getOperatorName()+ "'");
					treeJson.append(",organizationInfoId:'" + opri.getOrganizationInfo().getId()+ "'");
					//判断是否含有子节点
					treeJson.append(",leaf:true" );
					treeJson.append(",expanded:false");
					treeJson.append(",iconCls:'add16'" );//显示图片
					treeJson.append(",orgOrDeptOrPerson : 'person'");//显示该节点的身份，为用户
					treeJson.append("}");
					firstItem = true;
				}
			}
			if("organ".equals(showType)||"all".equals(showType)){
				//通过参数查询组织关系信息
				List<OrganizationRelationInfo> orgRelaList = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization(filterValue);
				for (int i = 0; i < orgRelaList.size(); i++) {
					Object sci = (OrganizationRelationInfo) orgRelaList.get(i);
					try {
						if(null!=((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
							((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname();
						}
					} catch (ObjectNotFoundException e) {
						logger.info("有错误数据，错误数据："+((OrganizationRelationInfo) sci).getId());
						continue;
					}
					if(firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					if(text.equals("orgCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
					}else if(text.equals("orgShortCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
					}
					if(null==((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
						treeJson.append(",qtip:''");
					}else{
						treeJson.append(",qtip:'"+((OrganizationRelationInfo) sci).getOrganizationRelationInfo()
								.getOrganizationInfo().getOrgCname()+"'");//添加一个浮动框，父机构名称
					}
					treeJson.append(",organCname:''");
					treeJson.append(",userEname:''");
					treeJson.append(",userPosition:''");
					treeJson.append(",createDate:''");
					treeJson.append(",modifyDate:''");
					treeJson.append(",invalidDate:''");
					treeJson.append(",invalidFlag:''");
					treeJson.append(",userCode:''");
					treeJson.append(",operator:''");
					treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
					//判断是否含有子节点
					treeJson.append(",leaf:true" );
					treeJson.append(",expanded:false");
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",orgOrDeptOrPerson : 'org'");//显示该节点的身份，为机构
						treeJson.append(",iconCls:'chart_organisation'" );//显示图片
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",orgOrDeptOrPerson : 'dept'");//显示该节点的身份，为部门
						treeJson.append(",iconCls:'partverify'" );//显示图片
					}
					treeJson.append("}");
					firstItem = true;
				}
			}
			treeJson.append("]");
		}else{
			List<OrganizationRelationInfo> list = null ;
			boolean firstItem = true;
			if(node.equals("-1")&&("organ".equals(showType)||"all".equals(showType))){
				Object sci;
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationRelationInfo is null order by organizationInfo.orgCode");
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					if(text.equals("orgCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
					}else if(text.equals("orgShortCname")){
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
					}
					treeJson.append(",code:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
					treeJson.append(",organCname:''");
					treeJson.append(",userEname:''");
					treeJson.append(",userPosition:''");
					treeJson.append(",createDate:''");
					treeJson.append(",modifyDate:''");
					treeJson.append(",invalidDate:''");
					treeJson.append(",invalidFlag:''");
					treeJson.append(",userCode:''");
					treeJson.append(",operator:''");
					treeJson.append(",organizationInfoId:'"+((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+"'");
					int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
					//判断是否含有子节点
					if(size>0){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
					treeJson.append(",expanded:false");
					treeJson.append(",iconCls:'cog'" );//显示图片
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
			}else {
				Object sci;
				OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(node);
				List<OrganPersonRelationInfo> userList = organPersonRelationDao.getOrganPersonRelationInfoByOrganOid(organizationRelationInfo.getOrganizationInfo().getId());
				if("person".equals(showType)||"all".equals(showType)){
					for (int i = 0; i < userList.size(); i++) {
						sci = (OrganPersonRelationInfo) userList.get(i);
						if(i>0){
							treeJson.append(",");
						}
						String createDate = "";
						if(((OrganPersonRelationInfo) sci).getPersonInfo().getCreateDate()!=null){
							createDate = ((OrganPersonRelationInfo) sci).getPersonInfo().getCreateDate().toString().substring(0, 10);
						}
						String modifyDate = "";
						if(((OrganPersonRelationInfo) sci).getPersonInfo().getModifyDate()!=null){
							modifyDate = ((OrganPersonRelationInfo) sci).getPersonInfo().getModifyDate().toString().substring(0, 10);
						}
						String invalidDate = "";
						if(((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidDate()!=null){
							invalidDate = ((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidDate().toString().substring(0, 10);
						}
						treeJson.append("{");
						treeJson.append("id:'" + ((OrganPersonRelationInfo) sci).getId()+ "'");
						treeJson.append(",text:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCname()+ "'");
						treeJson.append(",organCname:'" + ((OrganPersonRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
						treeJson.append(",userEname:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserEname()+ "'");
						treeJson.append(",userPosition:'" + ((OrganPersonRelationInfo) sci).getUserPosition()+ "'");
						treeJson.append(",personInfoOid:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getId()+ "'");
						treeJson.append(",createDate:'" +createDate + "'");
						treeJson.append(",modifyDate:'" +modifyDate + "'");
						treeJson.append(",invalidDate:'" +invalidDate + "'");
						treeJson.append(",invalidFlag:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidFlag()+ "'");
						treeJson.append(",userCode:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCode()+ "'");
						treeJson.append(",operator:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getOperatorName()+ "'");
						treeJson.append(",organizationInfoId:'" + ((OrganPersonRelationInfo) sci).getOrganizationInfo().getId()+ "'");
						//判断是否含有子节点
						treeJson.append(",leaf:true" );
						treeJson.append(",expanded:false");
						treeJson.append(",iconCls:'add16'" );//显示图片
						treeJson.append(",orgOrDeptOrPerson : 'person'");//显示该节点的身份，为用户
						treeJson.append("}");
					}
				}
				if("organ".equals(showType)||"all".equals(showType)){
					//初始化时查询系统定义表
					list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",node,invalidFlag);
					for(int i=0;i<list.size();i++){
						sci = (OrganizationRelationInfo) list.get(i);
						if(("person".equals(showType)||"all".equals(showType))&&userList.size()>0){
							treeJson.append(",");
						}else if(!firstItem){
							treeJson.append(",");
						}
						treeJson.append("{");
						treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
						if(text.equals("orgCname")){
							treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
						}else if(text.equals("orgShortCname")){
							treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
						}
						treeJson.append(",qtip:'"+((OrganizationRelationInfo) sci).getOrganizationRelationInfo()
								.getOrganizationInfo().getOrgCname()+"'");//添加一个浮动框，父机构名称
						treeJson.append(",organCname:''");
						treeJson.append(",userEname:''");
						treeJson.append(",userPosition:''");
						treeJson.append(",createDate:''");
						treeJson.append(",modifyDate:''");
						treeJson.append(",invalidDate:''");
						treeJson.append(",invalidFlag:''");
						treeJson.append(",userCode:''");
						treeJson.append(",operator:''");
						treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
						int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
						int sizeP = organPersonRelationDao.getOrgUserListSize(((OrganizationRelationInfo) sci).getOrganizationInfo().getId());
						//判断是否含有子节点
						if(size>0){
							treeJson.append(",leaf:false" );
						}else if(sizeP>0){
							treeJson.append(",leaf:false" );
						}else{
							treeJson.append(",leaf:true" );
						}
						treeJson.append(",expanded:false");
						if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
							treeJson.append(",orgOrDeptOrPerson : 'org'");//显示该节点的身份，为机构
							treeJson.append(",iconCls:'chart_organisation'" );//显示图片
						}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
							treeJson.append(",orgOrDeptOrPerson : 'dept'");//显示该节点的身份，为部门
							treeJson.append(",iconCls:'partverify'" );//显示图片
						}
						
						treeJson.append("}");
						if(firstItem) {
							firstItem = false;
						}
					}
				}
			}
			treeJson.append("]");
		}
		return treeJson.toString();
	}
	/**
	 * 分级加载组织用户树
	 * @date 2010-12-23
	 * @author wangxx
	 * 用途：
	 *  1、用于人员代理组件，选择代理人和被代理人使用
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param node 该节点id 也就是组织关系oid
	 * @param invalidFlag  是否显示无效 “”为全部  1为显示有效   2为显示无效
	 * @return tree的json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String getOrganUserTree(String node,String text,String invalidFlag){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		boolean firstItem = true;
		if(node.equals("-1")){
			Object sci;
			//初始化时查询系统定义表
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo " +
				"where organizationRelationInfo is null order by organizationInfo.orgCode");
			for(int i=0;i<list.size();i++){
				sci = (OrganizationRelationInfo) list.get(i);
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				if(text.equals("orgCname")){
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci)
							.getOrganizationInfo().getOrgCname()+ "'");
				}else if(text.equals("orgShortCname")){
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci)
							.getOrganizationInfo().getOrgShortCname()+ "'");
				}
				int size=organizationRelationInfoDao.buildMergeDeptTree(
						((OrganizationRelationInfo) sci).getId());
				//判断是否含有子节点
				if(size>0){
					treeJson.append(",leaf:false" );
				}else{
					treeJson.append(",leaf:true" );
				}
				treeJson.append(",expanded:false");
				treeJson.append(",iconCls:'cog'" );//显示图片
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
		}else {
			Object sci;
			OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(node);
			List<OrganPersonRelationInfo> userList = organPersonRelationDao
				.getOrganPersonRelationInfoByOrganOid(organizationRelationInfo.getOrganizationInfo().getId());
			for (int i = 0; i < userList.size(); i++) {
				sci = (OrganPersonRelationInfo) userList.get(i);
				if(i>0){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getId()+ "'");
				treeJson.append(",text:'" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCname()+ "'");
				//判断是否含有子节点
				treeJson.append(",leaf:true" );
				treeJson.append(",expanded:false");
				treeJson.append(",iconCls:'add16'" );//显示图片
				treeJson.append(",orgOrDeptOrPerson : 'person'");//显示该节点的身份，为用户
				treeJson.append("}");
			}
			//初始化时查询系统定义表
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",node,invalidFlag);
			for(int i=0;i<list.size();i++){
				sci = (OrganizationRelationInfo) list.get(i);
				if(userList.size()>0){
					treeJson.append(",");
				}else if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				if(text.equals("orgCname")){
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				}else if(text.equals("orgShortCname")){
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				}
				int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
				int sizeP = organPersonRelationDao.getOrgUserListSize(((OrganizationRelationInfo) sci).getOrganizationInfo().getId());
				//判断是否含有子节点
				if(size>0){
					treeJson.append(",leaf:false" );
				}else if(sizeP>0){
					treeJson.append(",leaf:false" );
				}else{
					treeJson.append(",leaf:true" );
				}
				treeJson.append(",expanded:false");
				if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
					treeJson.append(",orgOrDeptOrPerson : 'org'");//显示该节点的身份，为机构
					treeJson.append(",iconCls:'chart_organisation'" );//显示图片
				}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
					treeJson.append(",orgOrDeptOrPerson : 'dept'");//显示该节点的身份，为部门
					treeJson.append(",iconCls:'partverify'" );//显示图片
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
	 * 组织机构：分级加载树判断是否有子节点,子节点为用户
	 * 该节点的下级是用户
	 * 用途：在删除部门时判断下级是否含有用户
	 * @param organRelaOid 组织关系信息oid
	 * @return true表示存在，false表示不存在
	 */
	@Transactional(readOnly = true)
	public String getOrgUserListSize(String organRelaOid){
		int i = organPersonRelationDao.getOrgUserListSize(
			organizationRelationInfoDao.get(organRelaOid).getOrganizationInfo().getId());
		String aa = "false";
		if(i>0){
			aa = "true";
		}
		return aa;
	}
	
	/**
	 * 通过机构oid查找该机构所属的所有人员信息
	 * @param orgOid 组织基本信息oid 
	 * @param flag 为true时查询下级用户信息，否则显示本级人员信息
	 * @return grid的json数据
	 */
	@Transactional(readOnly = true)
	public String getPersonJsonByOrgOid(String orgOid,String flag){
		StringBuffer  json = new StringBuffer("");
		if(flag.equals("true")){
			//加载下级人员
			List<OrganizationInfo> list=organizationInfoDao.getOrganization("id",orgOid);
			List<OrganPersonRelationInfo> listp = organPersonRelationDao
				.getOrganPersonRelationInfoByOrganOid(list.get(0).getId());
			Set<OrganPersonRelationInfo> orgPersonSet = new HashSet<OrganPersonRelationInfo>();
			for (int i = 0; i < listp.size(); i++) {
				orgPersonSet.add(listp.get(i));
			}
			//判断是否还有子结点，如果存在子结点查找该节点本级所存在的人员
			List<OrganPersonRelationInfo> listper = getOrganPersonRela(list.get(0).getId());
			for (int i = 0; i < listper.size(); i++) {
				orgPersonSet.add(listper.get(i));
			}
			json.append("{totalProperty:"+orgPersonSet.size()+",result:[");
			Iterator<OrganPersonRelationInfo> it = orgPersonSet.iterator();
			int m = 0;
			while(it.hasNext()){
				if(m>0){
					json.append(",");
				}
				m++;
				OrganPersonRelationInfo organPersonRelationInfo = it.next();
				json.append("{");
				json.append("OID:'"+organPersonRelationInfo.getPersonInfo().getId()+"'");
				json.append(",USER_CNAME:'"+organPersonRelationInfo.getPersonInfo().getUserCname()+"'");
				json.append(",USER_ENAME:'"+organPersonRelationInfo.getPersonInfo().getUserEname()+"'");
				json.append(",USER_CODE:'"+organPersonRelationInfo.getPersonInfo().getUserCode()+"'");
				json.append(",ORGAN_NAME:'"+organPersonRelationInfo.getOrganizationInfo().getOrgCname()+"'");
				json.append("}");
			}
			json.append("]}");
		}else{
			//不加载下级人员
			List<OrganizationInfo> list=organizationInfoDao.getOrganization("id",orgOid);
			List<OrganPersonRelationInfo> listp = organPersonRelationDao.getOrganPersonRelationInfoByOrganOid(list.get(0).getId());
			json.append("{totalProperty:"+listp.size()+",result:[");
			for (int i = 0; i < listp.size(); i++) {
				if(i>0){
					json.append(",");
				}
				OrganPersonRelationInfo organPersonRelationInfo = listp.get(i);
				json.append("{");
				json.append("OID:'"+organPersonRelationInfo.getPersonInfo().getId()+"'");
				json.append(",USER_CNAME:'"+organPersonRelationInfo.getPersonInfo().getUserCname()+"'");
				json.append(",USER_ENAME:'"+organPersonRelationInfo.getPersonInfo().getUserEname()+"'");
				json.append(",USER_CODE:'"+organPersonRelationInfo.getPersonInfo().getUserCode()+"'");
				json.append(",ORGAN_NAME:'"+organPersonRelationInfo.getOrganizationInfo().getOrgCname()+"'");
				json.append("}");
			}
			json.append("]}");
		}
		return json.toString();
	}
	
	/**
	 * 通过机构oid查找其下级所有的机构用户关系对象集合
	 * @date 2010-11-27
	 * @author wangxx
	 * @param orgRelaOid 组织关系信息对象oid
	 * @return 组织用户关系信息对象集合
	 */
	@Transactional(readOnly= true)
	public List<OrganPersonRelationInfo> getOrganPersonRela(String orgRelaOid){
		List<OrganPersonRelationInfo> list = new ArrayList<OrganPersonRelationInfo>();
		List<OrganizationRelationInfo> orgList = organizationRelationInfoManager
			.getOrganizationRelationInfoByParentOrgRelaOid(orgRelaOid);
		for (int i = 0; i < orgList.size(); i++) {
			List<OrganPersonRelationInfo> listp = organPersonRelationDao
				.getOrganPersonRelationInfoByOrganOid(
				orgList.get(i).getOrganizationInfo().getId());
			for (int j = 0; j < listp.size(); j++) {
				list.add(listp.get(j));
			}
		}
		return list;
	}
	/**
	 * 从该用户所属部门向上查询所有的领导，包含该部门的领导
	 * @param deptCode
	 * @author hxh
	 * @return
	 */
	public List<OrganPersonRelationInfo> getAllLeader(String deptCode, String leaderFlag) {
		OrganizationRelationInfo o = organizationRelationInfoDao.getOrgRelaInfoByOrgCode(deptCode).get(0);
		OrganizationRelationInfo parent = o.getOrganizationRelationInfo();
		StringBuffer buf = new StringBuffer(o.getOrganizationInfo().getId());
		while (parent != null && parent.getOrganizationInfo().getPurpose().equals("2")) {
			buf.append(";");
			buf.append(parent.getOrganizationInfo().getId());
			parent = parent.getOrganizationRelationInfo();
		}
		List<OrganPersonRelationInfo> organPersonRelationInfo = organPersonRelationDao.getDeptLeaderPersonRelaListByDeptCode(buf.toString()
				.split(";"), leaderFlag);
		return organPersonRelationInfo;
	}
	/**
	 * 查询部门用户关联集合
	 * @param deptCode
	 * @param leaderFlag  1-3
	 * @author hxh
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<OrganPersonRelationInfo> listPersonByDeptOrLeaderFlag(Map<String,Object> map){
		return organPersonRelationDao.getDeptLeaderPersonRelaListByDeptCode(map);
	}
}
