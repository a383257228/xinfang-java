package com.sinosoft.xf.system.logon.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganizationInfoManager;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.pubfun.dao.PetitionMaxNoInfoDao;
import com.sinosoft.xf.pubfun.domain.PetitionMaxNoInfo;
import com.sinosoft.xf.system.logon.dao.PetitionOrganInfoDao;
import com.sinosoft.xf.system.transset.dao.PetitionOrganTransInfoDao;
import com.sinosoft.xf.system.transset.domain.PetitionOrganTransInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 *最大号信息manager
 * @date 2011-09-15
 * @author wangxx 
 */
@Service
@Transactional
public class PetitionOrganInfoManager extends EntityManager<OrganizationInfo,String> {
	/**
	 * 注入用户dao
	 */
	@Autowired
	PersonInfoDao personInfoDao;
	/**
	 * 注入用户信息业务层
	 */
	@Autowired
	PersonInfoManager personInfoManager;
	/**
	 * 注入机构关系业务层
	 */
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	
	/**
	 * 注入机构关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	/** 注入机构用户关系dao层类 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	/**
	 * 注入组织机构dao层
	 */
	@Autowired
	PetitionOrganInfoDao petitionOrganInfoDao;
	/**
	 * 注入转发机构设置信息dao
	 */
	@Autowired
	PetitionOrganTransInfoDao petitionOrganTransInfoDao;
	
	/**
	 * 注入最大号信息dao
	 */
	@Autowired
	PetitionMaxNoInfoDao maxNoInfoDao;

	/**
	 * 注入机构业务层
	 */
	@Autowired
	OrganizationInfoManager organizationInfoManager;
	
	/**
	 * 注入权限用户角色
	 */
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	/**
	 * @author wangxx
	 * 2012-09-17
	 * 加载本级一下的数据
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @param isCurr  是否显示本级
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTree(String node,String isChecked,String text
			,String isOrgan,String invalidFlag,boolean isCurr){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		boolean flag = false;
		String orgPurpose = "";
		if(isOrgan.equals("organ")){
			//显示机构
			orgPurpose = "1";
		}else if(isOrgan.equals("dept")){
			//显示部门
			orgPurpose = "2";
		}
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			flag = true;
			if(isCurr){
				//根节点
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationInfo.orgCode ='"
						+person.getRegionCode()+"'  order by organOrder");
			}else{
				//根节点
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo.organizationInfo.orgCode ='"
						+person.getRegionCode()+"' and organizationInfo.purpose='1' order by organOrder");
			}
		}else {
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose,node,invalidFlag);
		}
		boolean firstItem = true;
		Object sci;
		//用于存放有下级节点的关系表oid
		Set<String> set = new HashSet<String>();
		//是否需要连表进行查询是否存在下级节点
		String[] orgRelaOid = new String[list.size()];
		if(!flag){
			//遍历获取组织关系表oid
			for(int i=0;i<list.size();i++){
				OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
				orgRelaOid[i]=ori.getId();
			}
			List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose, orgRelaOid, invalidFlag);
			for(int i=0;i<clist.size();i++){
				set.add(clist.get(i).getOrganizationRelationInfo().getId());
			}
		}
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
			treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
			treeJson.append(",code:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
			}
			treeJson.append(",editable:false");
			if(flag){
				//不连表
				int size = 0;
				//判断是存在要显示的下级节点
				size = organizationRelationInfoDao.getOrgSize(orgPurpose,((OrganizationRelationInfo) sci).getId(),invalidFlag);
				//判断是否含有子节点
				if(size>0){
					//存在下级节点
					treeJson.append(",leaf:false" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
				}
			}else{
				//连表
				//判断是否含有子节点
				if(set.contains(((OrganizationRelationInfo) sci).getId())){
					//存在下级节点
					treeJson.append(",leaf:false" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
				}
			}
			if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
				//该节点身份为机构
				//统一样式，注释掉自定义样式
				//treeJson.append(",iconCls:'chart_organisation'" );
				treeJson.append(",isType:'organ'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:true");
				treeJson.append(",isDelete:true");
				treeJson.append(",isMerge:true");
				treeJson.append(",isSplit:true");
			}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
				//该节点身份为部门
				treeJson.append(",iconCls:'partverify'" );
				treeJson.append(",isType:'dept'");
				treeJson.append(",isAdd:false");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}else{
				//该节点身份为根节点
				treeJson.append(",iconCls:'cog'" );
				treeJson.append(",isType:'system'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}
			treeJson.append(",expanded:false");
			//判断复选框属性
			if(isChecked.equals(Constants.TRUE)){
				//带复选框并且选中
				treeJson.append(",checked:true");
			}else if(isChecked.equals("false")){
				//带复选框但不选中
				treeJson.append(",checked:false");
			}
			treeJson.append(",allowDelete:true"); 
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 加载下级自收区域
	 * @date 2015-05-26
	 * @author liub
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrgCnameLowerSelfAcceptRegionCode(String node){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		List<Map<String,Object>> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		StringBuffer sql=new StringBuffer();
		sql.append("select organ.* from PETITION_ORGAN_TRANS organ ");
		sql.append("inner join ORGANIZATION org on org.oid=organ.organ_id  ");
		sql.append("where  region_code < organ.org_code  ");
		sql.append("and region_Code=''  and organ.org_type != 'noexchange' ");
		sql.append("order by organ.organ_id asc  ");
		PetitionOrganTransInfo pot=null;//
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			list = jdbcTemplate.queryForList(sql.toString().replace("region_Code=''", "region_Code='"+person.getRegionCode()+"'"));
		}else {
			pot=petitionOrganTransInfoDao.get(node);
			list = jdbcTemplate.queryForList(sql.toString().replace("region_Code=''", "region_Code='"+pot.getOrgCode()+"'"));
		}
		boolean firstItem = true;
		for (Map<String, Object> map : list) {
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			treeJson.append("id:'" + (String)map.get("OID")+ "'");
			treeJson.append(",organId:'" + (String)map.get("ORGAN_ID")+ "'");
			treeJson.append(",organType:'" + (String)map.get("OID")+ "'");
			treeJson.append(",code:'" + (String)map.get("ORG_CODE")+ "'");
			treeJson.append(",orgCode:'" + (String)map.get("ORG_CODE")+ "'");
			treeJson.append(",text:'" + (String)map.get("ORG_NAME")+ "'");
			treeJson.append(",editable:false");
			int size = 0;
			//判断是存在要显示的下级节点
			String tempSQL=sql.toString();
			tempSQL=tempSQL.replace("region_Code=''", "region_Code='"+(String)map.get("ORG_CODE")+"'");
			tempSQL=tempSQL.replace("organ.*", "count(*) size");
			tempSQL=tempSQL.replace("order by organ.organ_id asc", "");
			List<Map<String,Object>> lMap= (List<Map<String, Object>>) jdbcTemplate.queryForList(tempSQL);
			for (Map<String, Object> maptemp : lMap) 
				size+= Integer.parseInt(maptemp.get("size")+"");
			//判断是否含有子节点
			if(size>0){
				//存在下级节点
				treeJson.append(",leaf:false" );
			}else{
				//不存在下级节点
				treeJson.append(",leaf:true" );
			}
			//该节点身份为机构
			//统一样式，注释掉自定义样式
			treeJson.append(",isType:'organ'");
			treeJson.append(",isAdd:true");
			treeJson.append(",isUpdate:true");
			treeJson.append(",isDelete:true");
			treeJson.append(",isMerge:true");
			treeJson.append(",isSplit:true");
			treeJson.append(",expanded:false");
			//带复选框但不选中
			treeJson.append(",checked:false");
			treeJson.append(",allowDelete:true"); 
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 分级加载用户列表树
	 * @date 2010-12-07
	 * @author wangxx
	 * 更新1：
	 * 在原有基础上增加了查询功能
	 * @date 2011-05-03
	 * @author wangxx
	 * 更新2：
	 * 机构只查询集中式部署
	 * @date 2013-7-10
	 * @author hxh
	 * 步骤：
	 * 	1、通过参数filterValue来判断是否是查询
	 * 		如果是查询，分别用该参数分别查询对应的组织和人员，但是组织是不含下级组织的
	 * 		如果不是查询，根据节点信息查询下级组织和人员信息
	 * 用途：
	 *  1、用于加载组织用户页面列表树（显示中文名）
	 *  调用哪些方法:
	 *  1、organPersonRelationDao.
					getCentralizedDeploymentOrganPersonRelaListByPersonName
	 *  2、organPersonRelationDao.getOrganPersonRelationInfoByOrganOid
	 *  3、petitionOrganInfoDao.getOrganRelaInfoByParentOid
	 *  4、organizationRelationInfoDao.getOrganRelaInfoByParentOid
	 *  5、organPersonRelationDao.getOrganRelaPersonInfoByOrganOid
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
			//List<OrganPersonRelationInfo> list = organPersonRelationDao.getOrganPersonRelaListByPersonName(filterValue);
			List<OrganPersonRelationInfo> list = organPersonRelationDao.
					getCentralizedDeploymentOrganPersonRelaListByPersonName(filterValue);
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
				treeJson.append("\"id\":\"" + opri.getId()+ "\"");
				treeJson.append(",\"text\":\"" + opri.getPersonInfo().getUserCname()+ "\"");
				treeJson.append(",\"organCname\":\"" + opri.getOrganizationInfo().getOrgCname()+ "\"");
				treeJson.append(",\"userEname\":\"" + opri.getPersonInfo().getUserEname()+ "\"");
				treeJson.append(",\"userPosition\":\"" + opri.getUserPosition()+ "\"");
				treeJson.append(",\"createDate\":\"" +createDate + "\"");
				treeJson.append(",\"modifyDate\":\"" +modifyDate + "\"");
				treeJson.append(",\"invalidDate\":\"" +invalidDate + "\"");
				treeJson.append(",\"invalidFlag\":\"" + opri.getPersonInfo().getInvalidFlag()+ "\"");
				treeJson.append(",\"leaderFlag\":\"" + opri.getPersonInfo().getLeaderFlag()+ "\"");
				treeJson.append(",\"userCode\":\"" + opri.getPersonInfo().getUserCode()+ "\"");
				treeJson.append(",\"operator\":\"" + opri.getPersonInfo().getOperatorName()+ "\"");
				treeJson.append(",\"organizationInfoId\":\"" + opri.getOrganizationInfo().getId()+ "\"");
				//判断是否含有子节点
				treeJson.append(",\"leaf\":true" );
				treeJson.append(",\"expanded\":true");
				treeJson.append(",\"iconCls\":\"add16\"" );//显示图片
				treeJson.append(",\"orgOrDeptOrPerson\": \"person\"");//显示该节点的身份，为用户
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
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
						person.getRegionCode()+"' order by organizationInfo.orgCode");
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
					if(text.equals("orgCname")){
						treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "纪委\"");
					}else if(text.equals("orgShortCname")){
						treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "纪委\"");
					}
					treeJson.append(",\"code\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "\"");
					treeJson.append(",\"organCname\":\"\"");
					treeJson.append(",\"userEname\":\"\"");
					treeJson.append(",\"userPosition\":\"\"");
					treeJson.append(",\"createDate\":\"\"");
					treeJson.append(",\"modifyDate\":\"\"");
					treeJson.append(",\"invalidDate\":\"\"");
					treeJson.append(",\"invalidFlag\":\"\"");
					treeJson.append(",\"leaderFlag\":\"\"");
					treeJson.append(",\"userCode\":\"\"");
					treeJson.append(",\"operator\":\"\"");
					treeJson.append(",\"organizationInfoId\":\""+((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+"\"");
					int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
					//判断是否含有子节点
					if(size>0){
						treeJson.append(",\"leaf\":false" );
					}else{
						treeJson.append(",\"leaf\":true" );
					}
					treeJson.append(",\"expanded\":true");
					treeJson.append(",\"orgOrDeptOrPerson\": \"org\"");
					treeJson.append(",\"iconCls\":\"chart_organisation\"" );//显示图片
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
					treeJson.append("\"id\":\"" + ((OrganPersonRelationInfo) sci).getId()+ "\"");
					treeJson.append(",\"text\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCname()+ "\"");
					treeJson.append(",\"organCname\":\"" + ((OrganPersonRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"userEname\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserEname()+ "\"");
					treeJson.append(",\"userPosition\":\"" + ((OrganPersonRelationInfo) sci).getUserPosition()+ "\"");
					treeJson.append(",\"personInfoOid\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getId()+ "\"");
					treeJson.append(",\"createDate\":\"" +createDate + "\"");
					treeJson.append(",\"modifyDate\":\"" +modifyDate + "\"");
					treeJson.append(",\"invalidDate\":\"" +invalidDate + "\"");
					treeJson.append(",\"invalidFlag\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getInvalidFlag()+ "\"");
					treeJson.append(",\"leaderFlag\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getLeaderFlag()+ "\"");
					treeJson.append(",\"userCode\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getUserCode()+ "\"");
					treeJson.append(",\"operator\":\"" + ((OrganPersonRelationInfo) sci).getPersonInfo().getOperatorName()+ "\"");
					treeJson.append(",\"organizationInfoId\":\"" + ((OrganPersonRelationInfo) sci).getOrganizationInfo().getId()+ "\"");
					//判断是否含有子节点
					treeJson.append(",\"leaf\":true" );
					treeJson.append(",\"expanded\":false");
					treeJson.append(",\"iconCls\":\"add16\"" );//显示图片
					treeJson.append(",\"orgOrDeptOrPerson\": \"person\"");//显示该节点的身份，为用户
					treeJson.append("}");
				}
				//初始化时查询系统定义表
				//list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",node,invalidFlag);
				//只查询集中式部署下的机构
				OrganizationRelationInfo r=organizationRelationInfoDao.get(node);
				list=petitionOrganInfoDao.getOrganRelaInfoByParentOid(r.getOrganizationInfo().getPurpose(), 
						node, invalidFlag);
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
					treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
					if(text.equals("orgCname")){
						treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
					}else if(text.equals("orgShortCname")){
						treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "\"");
					}
					treeJson.append(",\"qtip\":\""+((OrganizationRelationInfo) sci).getOrganizationRelationInfo()
							.getOrganizationInfo().getOrgCname()+"\"");//添加一个浮动框，父机构名称
					treeJson.append(",\"organCname\":\"\"");
					treeJson.append(",\"userEname\":\"\"");
					treeJson.append(",\"userPosition\":\"\"");
					treeJson.append(",\"createDate\":\"\"");
					treeJson.append(",\"modifyDate\":\"\"");
					treeJson.append(",\"invalidDate\":\"\"");
					treeJson.append(",\"invalidFlag\":\"\"");
					treeJson.append(",\"leaderFlag\":\"\"");
					treeJson.append(",\"userCode\":\"\"");
					treeJson.append(",\"operator\":\"\"");
					treeJson.append(",\"organizationInfoId\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "\"");
					//判断是否含有子节点
					if(set.contains(((OrganizationRelationInfo) sci).getId())
							||orpSet.contains(((OrganizationRelationInfo) sci).getOrganizationInfo().getId())){
						treeJson.append(",\"leaf\":false" );
						treeJson.append(",\"expanded\":false");
					}else{
						treeJson.append(",\"leaf\":true" );
						treeJson.append(",\"expanded\":false");
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",\"orgOrDeptOrPerson\" : \"org\"");//显示该节点的身份，为机构
						treeJson.append(",\"iconCls\":\"chart_organisation\"" );//显示图片
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",\"orgOrDeptOrPerson\": \"dept\"");//显示该节点的身份，为部门
						treeJson.append(",\"iconCls\":\"partverify\"" );//显示图片
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
	 * wangxx
	 * 2011-05-03
	 * 分级加载列表树：此方法添加了查询功能，可以针对组织中文名或者英文名进行查询
	 * 步骤：
	 * 	1、通过参数filterValue判断是否是查询的参数，
	 * 		（1）如果是filterValue进行查询，但是在拼接json时只显示本机组织
	 * 			而不显示是否还存在下级组织，因为如果显示下级组织有可能会出现
	 * 			树节点id重复，从而导致页面出现多个重复id的问题
	 * 		（2）如果不是查询，就用node节点为父节点进行查询，然后拼接json数据
	 * 用途：
	 *  1、渲染组织设置右侧的列表树
	 *  2、渲染组织变更的列表树
	 *  调用哪些方法:
	 *  1、getOrgAndRelaInfoByCondition
	 *  2、organizationRelationInfoDao.getOrganRelaInfoByParentOid
	 *  3、petitionOrganInfoDao.getOrganRelaInfoByParentOid
	 *  被哪些方法调用:
	 *  1、petitionOrganInfoAction.getOrganColumnsTree
	 * @param node 该节点id 也就是组织关系oid
	 * @param invalidFlag  是否显示无效 “”为全部  1为显示有效   2为显示无效
	 * @param filterValue  查询所用的参数
	 * @param rootExpandFlag true时根节点自动展开
	 * hxh 2013-7-10 修改区域显示本级及下一级
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganColumnsTree(String node,String purpose,String invalidFlag,
			String filterText,String filterValue,boolean changeFlag,boolean rootExpandFlag){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		List<OrganizationRelationInfo> list = null ;
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(filterText!=null && !filterText.trim().equals("") && filterValue!=null && !filterValue.equals("")){
			return getOrgAndRelaInfoByCondition(person.getRegionCode(),filterText,filterValue,purpose );
		}else{
			if(node.equals("-1")){
				Object sci;
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
						person.getRegionCode()+"' order by organOrder");
				//用于存放有下级节点的关系表oid
				Set<String> set = new HashSet<String>();
				//是否需要连表进行查询是否存在下级节点
				String[] orgRelaOid = new String[list.size()];
				//遍历获取组织关系表oid
				for(int i=0;i<list.size();i++){
					OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
					orgRelaOid[i]=ori.getId();
				}
				List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("", orgRelaOid, invalidFlag);
				for(int i=0;i<clist.size();i++){
					set.add(clist.get(i).getOrganizationRelationInfo().getId());
				}
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
					treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "纪委\"");
					treeJson.append(",\"orgEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "\"");
					treeJson.append(",\"orgShortCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "\"");
					treeJson.append(",\"orgShortEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortEname()+ "\"");
					treeJson.append(",\"organOrder\":\"" + ((OrganizationRelationInfo) sci).getOrganOrder()+ "\"");
	//				treeJson.append(",currentOrgan:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getCurrentOrgan()+ "'");
					treeJson.append(",\"invalidFlag\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "\"");
					treeJson.append(",\"parentOrgCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"orgCode\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "\"");
	//				treeJson.append(",description:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getDescription()+ "'");
					treeJson.append(",\"createDate\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getCreateDate()+ "\"");
					treeJson.append(",\"organizationInfoId\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "\"");
					//判断是否含有子节点
					if(set.contains(((OrganizationRelationInfo) sci).getId())){
						treeJson.append(",\"leaf\":false" );
						treeJson.append(",\"expanded\":true");
					}else if(changeFlag){
						treeJson.append(",\"leaf\":false" );
						treeJson.append(",\"expanded\":true");
					}else{
						treeJson.append(",\"leaf\":true" );
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",\"orgOrDept\":\"org\"" );//该节点的身份 为机构
						treeJson.append(",\"iconCls\":\"chart_organisation\"" );//显示图片
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",\"orgOrDept\":\"dept\"" );//该节点的身份 为部门
						treeJson.append(",\"iconCls\":\"partverify\"" );//显示图片
					}
					treeJson.append(",\"isAdd\":true");
					treeJson.append(",\"isUpdate\":false");
					treeJson.append(",\"isDelete\":false");
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				treeJson.append("]");
			}else {
				Object sci;
				//初始化时查询系统定义表
				if("1".equals(purpose)){
					list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(purpose,node,invalidFlag);
				}else{//部门设置的操作
					OrganizationRelationInfo r=organizationRelationInfoDao.get(node);
					list=petitionOrganInfoDao.getOrganRelaInfoByParentOid("1", 
							node, invalidFlag);
				}
				//用于存放有下级节点的关系表oid
				Set<String> set = null;
				//hxh 2013-7-10 修改 对前台区域设置的操作就不需要判断是否有下级节点，因为区域显示的只是下一级
				if("".equals(purpose)){
					set=new HashSet<String>();
					//是否需要连表进行查询是否存在下级节点
					String[] orgRelaOid = new String[list.size()];
					//遍历获取组织关系表oid
					for(int i=0;i<list.size();i++){
						OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
						orgRelaOid[i]=ori.getId();
					}
					List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("2", orgRelaOid, invalidFlag);
					for(int i=0;i<clist.size();i++){
						set.add(clist.get(i).getOrganizationRelationInfo().getId());
					}
				}
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
					treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"orgEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "\"");
					treeJson.append(",\"orgShortCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "\"");
					treeJson.append(",\"orgShortEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortEname()+ "\"");
					treeJson.append(",\"organOrder\":\"" + ((OrganizationRelationInfo) sci).getOrganOrder()+ "\"");
	//				treeJson.append(",currentOrgan:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getCurrentOrgan()+ "'");
					treeJson.append(",\"invalidFlag\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "\"");
					treeJson.append(",\"parentOrgCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"orgCode\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "\"");
	//				treeJson.append(",description:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getDescription()+ "'");
					treeJson.append(",\"createDate\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getCreateDate()+ "\"");
					treeJson.append(",\"organizationInfoId\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "\"");
					//判断是否含有子节点 并且是对部门设置的操作
					if("".equals(purpose) && set.contains(((OrganizationRelationInfo) sci).getId())){
						treeJson.append(",\"leaf\":false" );
						treeJson.append(",\"expanded\":false");
					}else if(changeFlag){
						treeJson.append(",\"leaf\":false" );
						treeJson.append(",\"expanded\":true");
					}else{
						treeJson.append(",\"leaf\":true" );
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",\"orgOrDept\":\"org\"" );//该节点的身份 为机构
						treeJson.append(",\"iconCls\":\"chart_organisation\"" );//显示图片
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",\"orgOrDept\":\"dept\"" );//该节点的身份 为部门
						treeJson.append(",\"iconCls\":\"partverify\"" );//显示图片
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",\"orgOrDept\":\"org\"" );//该节点的身份 为机构
						treeJson.append(",\"iconCls\":\"chart_organisation\"" );//显示图片
						treeJson.append(",\"isType\":\"organ\"");
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",\"orgOrDept\":\"dept\"" );//该节点的身份 为部门
						treeJson.append(",\"iconCls\":\"partverify\"" );//显示图片
					}
					/*treeJson.append(",isAdd:true");
					treeJson.append(",isUpdate:true");
					treeJson.append(",isDelete:true");*/
					treeJson.append(",\"isAdd\":true");
					treeJson.append(",\"isUpdate\":true");
					//初始话节点禁止删除
					String orgCode=((OrganizationRelationInfo)sci).getOrganizationInfo().getOrgCode();
					if(((OrganizationRelationInfo)sci).getOrganizationInfo().getPurpose().equals("1")){//区域
						//orgCode=orgCode.substring(0,6);
						try {
							//Integer.parseInt(orgCode);
							Double.parseDouble(orgCode);
							treeJson.append(",\"isDelete\":false");
						} catch (Exception e) {
							treeJson.append(",\"isDelete\":true");
						}
					}else if(((OrganizationRelationInfo)sci).getOrganizationInfo().getPurpose().equals("2")){//部门
						if(orgCode.length()>9 && orgCode.substring(8, 9).equals("D")){
							treeJson.append(",\"isDelete\":true");
						}else{
							treeJson.append(",\"isDelete\":false");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				treeJson.append("]");			}
		}
		return treeJson.toString();
	}
	/**
	 * 只查询省一级的组织机构
	 * @author xuyi
	 * @date 2013-10-31
	 * 调用哪些方法：
	 * 1、organizationRelationInfoDao.find()
	 * 		
	 * 被那些方法调用：
	 * 1、getOrgCnameTreeByInvalidFlagAndOrganOnlyProv()
	 * 		用于综合查询中问题属地的查询，只返回省一级的机构
	 * @return 组织机构的json树
	 */
	@SuppressWarnings({ "unchecked" })
	@Transactional(readOnly=true)
	public String getOrganizationTreeOnlyProv(boolean singleSelect){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		Set<String> authorityRegion=getPersonAuthorityRegionWithPetitionTypeCode("");
		StringBuffer region=new StringBuffer("");
		List<OrganizationRelationInfo> list = null ;
		if(authorityRegion.size()>0){
			Iterator<String> it=authorityRegion.iterator();
			while(it.hasNext()){
				if(region.length()>0)
					region.append(",");
				region.append("'"+it.next()+"'");
			}
			list = organizationRelationInfoDao.find("from OrganizationRelationInfo where organizationInfo.orgCode in("+region.toString()+") " +
					"and organizationInfo.purpose='1' order by organOrder");
//			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo.organizationInfo.orgCode in ("
//					+region.toString()+") and organizationInfo.purpose='1' order by organOrder");
		}else{
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo.organizationInfo.orgCode ='"
					+person.getRegionCode()+"' and organizationInfo.purpose='1' order by organOrder");
		}
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		Object sci;
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
			treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
			treeJson.append(",code:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
			treeJson.append(",editable:false");
			treeJson.append(",leaf:true" );
			if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
				//该节点身份为机构
				treeJson.append(",iconCls:'chart_organisation'" );
				treeJson.append(",isType:'organ'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:true");
				treeJson.append(",isDelete:true");
				treeJson.append(",isMerge:true");
				treeJson.append(",isSplit:true");
			}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
				//该节点身份为部门
				treeJson.append(",iconCls:'partverify'" );
				treeJson.append(",isType:'dept'");
				treeJson.append(",isAdd:false");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}else{
				//该节点身份为根节点
				treeJson.append(",iconCls:'cog'" );
				treeJson.append(",isType:'system'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}
			treeJson.append(",expanded:false");
			if(!singleSelect){
				treeJson.append(",checked:false");
			}
			treeJson.append(",allowDelete:true"); 
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 获得需要集中式部署的机构树信息，其中包括本级的直属下级机构并且没有集中的机构
	 * @return 集中式部署的机构树信息
	 */
	@Transactional(readOnly=true)
	public String getCentralizedDeploymentInfoTree(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		//获取已经存在的集中式部署机构
		Map<String ,String> map =CodeSwitchUtil.getCentralizedMap();
		Set<String> cSet = new HashSet<String>();
		for (String key : map.keySet()) {
			cSet.add(key);
		}
		//查询登录用户的下级机构
		//List<OrganizationInfo> org = organizationInfoDao.getOrganization("orgCode", person.getRegionCode());
		//OrganizationRelationInfo orgRelaInfo = org.get(0).getOrganizationRelationInfo().iterator().next();
		//hxh 2013-6-25 添加排序
		OrganizationRelationInfo orgRelaInfo=(OrganizationRelationInfo) organizationRelationInfoDao.
				find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
				person.getRegionCode()+"' order by organizationInfo.orgCode").get(0);
		//Set<OrganizationRelationInfo> set = orgRelaInfo.getChildrens();
		//Iterator<OrganizationRelationInfo> it = set.iterator();
		boolean flag = false;
		List<OrganizationRelationInfo> olist=organizationRelationInfoDao.getOrganRelaInfoByParentOid("1",orgRelaInfo.getId(),"1");
		for(OrganizationRelationInfo organizationRelationInfo:olist){
//		while (it.hasNext()) {
//			OrganizationRelationInfo organizationRelationInfo
//					= (OrganizationRelationInfo) it.next();
			OrganizationInfo organ = organizationRelationInfo.getOrganizationInfo();
			if(/*"1".equals(organ.getPurpose())&&*/!cSet.contains(organ.getOrgCode().trim())){
				if(flag){
					treeJson.append(",");
				}
				treeJson.append("{id:'");
				treeJson.append(organ.getOrgCode());
				treeJson.append("',");
				treeJson.append("text:'");
				treeJson.append(organ.getOrgCname());
				treeJson.append("',leaf:true}");
				flag = true;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 分级加载属性combo：此方法添加了查询功能，可以针对组织中文名或者英文名进行查询
	 * @date 2012-03-08
	 * @author oba
	 * 步骤：
	 * 	1、通过参数filterValue判断是否是查询的参数，
	 * 		（1）如果是filterValue进行查询，但是在拼接json时只显示本机组织
	 * 			而不显示是否还存在下级组织，因为如果显示下级组织有可能会出现
	 * 			树节点id重复，从而导致页面出现多个重复id的问题
	 * 		（2）如果不是查询，就用node节点为父节点进行查询，然后拼接json数据
	 * 用途：
	 *  1、用于查询的带查询功能的combo
	 * @param node 该节点id 也就是组织关系oid
	 * @param invalidFlag  是否显示无效 “”为全部  1为显示有效   2为显示无效
	 * @param filterValue  查询所用的参数
	 */
	@Transactional(readOnly=true)
	public String getOrganTree(String node,String invalidFlag,String filterValue,String regionCode,boolean singleSelect,String checkedOption){
		List<OrganizationRelationInfo> list = null ;
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(null!=filterValue&&!"".equals(filterValue.trim())){
			Object sci;
			String provRegionCode = regionCode.substring(0,2);
			String cityRegionCode = regionCode.substring(2,4);
			String countyRegionCode = regionCode.substring(4,6);
			String orgCode = "";
			if("00".equals(provRegionCode)){//环保部
				orgCode="%";//省级
			}else if(!"00".equals(provRegionCode)&&"00".equals(cityRegionCode)){//省级单位
				orgCode=provRegionCode+"%";//市级
			}else if("00".equals(countyRegionCode)&&!"00".equals(provRegionCode)&&!"00".equals(cityRegionCode)){//市级单位
				orgCode=provRegionCode+cityRegionCode+"%";//县级
			}else {
				orgCode="%";//县级
			}
			list = getOrganizationRelationInfoListByOrganization("%"+filterValue+"%",orgCode);
			for (int i = 0; i < list.size(); i++) {
				sci = (OrganizationRelationInfo) list.get(i);
				//此处用于过滤错误数据
				try {
					if(null!=((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
						((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname();
					}
				} catch (ObjectNotFoundException e) {
					logger.info("有错误数据，错误数据："+((OrganizationRelationInfo) sci).getId());
					continue;
				}
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				OrganizationRelationInfo pORI = ((OrganizationRelationInfo) sci).getOrganizationRelationInfo();
				String text = ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname();
				while(null!=pORI&&!"000000000001".equals(pORI.getId().trim())&&!"000000".equals(pORI.getId().trim())
						&&!pORI.getOrganizationInfo().getOrgCode().equals(regionCode)){
					text = pORI.getOrganizationInfo().getOrgCname()+text;
					pORI = pORI.getOrganizationRelationInfo();
				}
				treeJson.append(",text:'" +text + "'");
				treeJson.append(",orgEname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
				if(null==((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
					treeJson.append(",parentOrgCname:''");
				}else {
					treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
				}
				treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
				//判断是否含有子节点
				treeJson.append(",leaf:true" );
				if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
					treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
					treeJson.append(",iconCls:'chart_organisation'" );//显示图片
				}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
					treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
					treeJson.append(",iconCls:'partverify'" );//显示图片
				}
				if(singleSelect==false){
					treeJson.append(",checked:false");
				}
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			treeJson.append("]");
		}else{
			if(node.equals("-1")){
				regionCode = "000000000000";
				OrganizationInfo organ = organizationInfoDao.findByUnique("orgCode", regionCode);
				OrganizationRelationInfo orgRelaInfo = organ.getOrganizationRelationInfo().iterator().next();
				Object sci;
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("1",orgRelaInfo.getId(),invalidFlag);
				//用于存放有下级节点的关系表oid
				Set<String> set = new HashSet<String>();
				//是否需要连表进行查询是否存在下级节点
				boolean flag = true;
				if(!"".equals(invalidFlag)){
					String[] orgRelaOid = new String[list.size()];
					//遍历获取组织关系表oid
					for(int i=0;i<list.size();i++){
						OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
						orgRelaOid[i]=ori.getId();
					}
					List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("", orgRelaOid, invalidFlag);
					for(int i=0;i<clist.size();i++){
						set.add(clist.get(i).getOrganizationRelationInfo().getId());
					}
					flag = false;
				}
				for(int i=0;i<list.size();i++){
					sci = list.get(i);
					if("1".equals(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose())){
						if(!firstItem){
							treeJson.append(",");
						}
						treeJson.append("{");
						treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
						treeJson.append(",orgEname:''");
						treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
						if(flag){
							int size = organizationRelationInfoDao.getOrgSize("",((OrganizationRelationInfo) sci).getId(),invalidFlag);
							//判断是否含有子节点
							if(size>0){
								treeJson.append(",leaf:false" );
							}else{
								treeJson.append(",leaf:true" );
							}
						}else{
							//判断是否含有子节点
							if(set.contains(((OrganizationRelationInfo) sci).getId())){
								treeJson.append(",leaf:false" );
							}else{
								treeJson.append(",leaf:true" );
							}
						}
						treeJson.append(",expanded:false");
						treeJson.append(",iconCls:'org '" );//显示图片
						if(singleSelect==false){
							if(checkedOption!=null&&checkedOption.contains(((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode())){
								treeJson.append(",checked:true");
							}else{
								treeJson.append(",checked:false");
							}
						}
						treeJson.append("}");
						if(firstItem) {
							firstItem = false;
						}
					}
				}
				treeJson.append("]");
			}else {
				Object sci;
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",node,invalidFlag);
				//用于存放有下级节点的关系表oid
				Set<String> set = new HashSet<String>();
				//是否需要连表进行查询是否存在下级节点
				boolean flag = true;
				if(!"".equals(invalidFlag)){
					String[] orgRelaOid = new String[list.size()];
					//遍历获取组织关系表oid
					for(int i=0;i<list.size();i++){
						OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
						orgRelaOid[i]=ori.getId();
					}
					List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("", orgRelaOid, invalidFlag);
					for(int i=0;i<clist.size();i++){
						set.add(clist.get(i).getOrganizationRelationInfo().getId());
					}
					flag = false;
				}
				for(int i=0;i<list.size();i++){
					sci = list.get(i);
					if("1".equals(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose())){
						if(!firstItem){
							treeJson.append(",");
						}
						treeJson.append("{");
						treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
						treeJson.append(",orgEname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
						treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
						treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
						if(flag){
							int size = organizationRelationInfoDao.getOrgSize("",((OrganizationRelationInfo) sci).getId(),invalidFlag);
							//判断是否含有子节点
							if(size>0){
								treeJson.append(",leaf:false" );
								treeJson.append(",expanded:false");
							}
						}else{
							//判断是否含有子节点
							if(set.contains(((OrganizationRelationInfo) sci).getId())){
								treeJson.append(",leaf:false" );
								treeJson.append(",expanded:false");
							}					
						}
						if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
							treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
							treeJson.append(",iconCls:'chart_organisation'" );//显示图片
						}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
							treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
							treeJson.append(",iconCls:'partverify'" );//显示图片
						}
						if(singleSelect==false){
							if(checkedOption!=null&&checkedOption.contains(((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode())){
								treeJson.append(",checked:true");
							}else{
								treeJson.append(",checked:false");
							}
						}
						treeJson.append("}");
						if(firstItem) {
							firstItem = false;
						}
					}
				}
				treeJson.append("]");
			}
		}
		return treeJson.toString();
	}
	
	/**
	 * 查询用户区域权限，返回区域关联信息
	 * @param node
	 * @param invalidFlag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTreeByRegionAuthority(String node
			,String invalidFlag,String filterValue
			,String regionCode,String petitionTypeCode){
		List<OrganizationRelationInfo> list = null ;
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(null!=filterValue&&!"".equals(filterValue.trim())){
			Object sci;
			String provRegionCode = regionCode.substring(0,2);
			String cityRegionCode = regionCode.substring(2,4);
			String countyRegionCode = regionCode.substring(4,6);
			String orgCode = "";
			String flag="";
			if("00".equals(provRegionCode)){//环保部
				orgCode="%";//省级
			}else if(!"00".equals(provRegionCode)&&"00".equals(cityRegionCode)){//省级单位
				orgCode=provRegionCode+"%";//市级
				flag="pro";
			}else if("00".equals(countyRegionCode)&&!"00".equals(provRegionCode)&&!"00".equals(cityRegionCode)){//市级单位
				orgCode=provRegionCode+cityRegionCode+"%";//县级
				flag="city";
			}else {
				orgCode="%";//县级
				flag="county";
			}
			list = getOrganizationRelationInfoListByOrganization("%"+filterValue+"%",orgCode);
			for (int i = 0; i < list.size(); i++) {
				sci = (OrganizationRelationInfo) list.get(i);
				//此处用于过滤错误数据
				try {
					if(null!=((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
						((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname();
					}
				} catch (ObjectNotFoundException e) {
					logger.info("有错误数据，错误数据："+((OrganizationRelationInfo) sci).getId());
					continue;
				}
//				Set<String> authorityRegion=getPersonAuthorityRegionWithPetitionTypeCode(petitionTypeCode);
				Set<String> authorityRegion=getAllValidAuthorityRegionWithPetitionTypeCode(petitionTypeCode);
//				Set<String> authorityRegion=getPersonAuthorityRegion();
				if(authorityRegion.size()>0){
					String curOrgCode=((OrganizationRelationInfo)sci).getOrganizationInfo().getOrgCode();
					String pro="";//getShortOrgCode(curOrgCode);
					if(flag.equals("pro")){
						pro=curOrgCode.substring(0, 4).concat("00000000");
					}else if(flag.equals("city")){
						pro=curOrgCode.substring(0, 6).concat("000000");
					}
					if(!authorityRegion.contains(pro)){
						continue;
					}
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					OrganizationRelationInfo pORI = ((OrganizationRelationInfo) sci).getOrganizationRelationInfo();
					String text = ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname();
					while(null!=pORI&&!"00000000000000000001".equals(pORI.getId())
							&&!pORI.getOrganizationInfo().getOrgCode().equals(regionCode)){
						text = pORI.getOrganizationInfo().getOrgCname()+" "+text;
						pORI = pORI.getOrganizationRelationInfo();
					}
					treeJson.append(",text:'" +text + "'");
					treeJson.append(",orgEname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
					if(null==((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
						treeJson.append(",parentOrgCname:''");
					}else {
						treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
					}
					treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
					//判断是否含有子节点
					treeJson.append(",leaf:true" );
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
						treeJson.append(",iconCls:'chart_organisation'" );//显示图片
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
						treeJson.append(",iconCls:'partverify'" );//显示图片
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
			}
			treeJson.append("]");
		}else{
			if(node.equals("-1")){
//				Set<String> authorityRegion=getPersonAuthorityRegionWithPetitionTypeCode(petitionTypeCode);
				Set<String> authorityRegion=getAllValidAuthorityRegionWithPetitionTypeCode(petitionTypeCode);
//				Set<String> authorityRegion=getPersonAuthorityRegion();
				if(authorityRegion.size()>0){
					Iterator<String> it=authorityRegion.iterator();
					StringBuffer region=new StringBuffer("");
					while(it.hasNext()){
						if(region.length()>0)
							region.append(",");
						region.append("'"+it.next()+"'");
					}
					OrganizationInfo organ = organizationInfoDao.findByUnique("orgCode", regionCode);
					OrganizationRelationInfo orgRelaInfo = organ.getOrganizationRelationInfo().iterator().next();
					Object sci;
					list=organizationRelationInfoDao.find(" from OrganizationRelationInfo" +
							" where organizationInfo.orgCode in("+region.toString()+") and organizationRelationInfo.id='"+orgRelaInfo.getId()+"'" +
							" and organizationInfo.purpose='1' and organizationInfo.invalidFlag='"+invalidFlag+"' order by organOrder");
					//用于存放有下级节点的关系表oid
					Set<String> set = new HashSet<String>();
					//是否需要连表进行查询是否存在下级节点
					boolean flag = true;
					if(!"".equals(invalidFlag)){
						String[] orgRelaOid = new String[list.size()];
						//遍历获取组织关系表oid
						for(int i=0;i<list.size();i++){
							OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
							orgRelaOid[i]=ori.getId();
						}
						List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("1", orgRelaOid, invalidFlag);
						for(int i=0;i<clist.size();i++){
							set.add(clist.get(i).getOrganizationRelationInfo().getId());
						}
						flag = false;
					}
					for(int i=0;i<list.size();i++){
						sci = list.get(i);
						if("1".equals(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose())){
							if(!firstItem){
								treeJson.append(",");
							}
							treeJson.append("{");
							treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
							treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
							treeJson.append(",orgEname:''");
							treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
							if(flag){
								int size = organizationRelationInfoDao.getOrgSize("",((OrganizationRelationInfo) sci).getId(),invalidFlag);
								//判断是否含有子节点
								if(size>0){
									treeJson.append(",leaf:false" );
								}else{
									treeJson.append(",leaf:true" );
								}
							}else{
								//判断是否含有子节点
								if(set.contains(((OrganizationRelationInfo) sci).getId())){
									treeJson.append(",leaf:false" );
								}else{
									treeJson.append(",leaf:true" );
								}
							}
							treeJson.append(",expanded:false");
//							if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
//								treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
//								treeJson.append(",iconCls:'chart_organisation'" );//显示图片
//							}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
//								treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
//								treeJson.append(",iconCls:'partverify'" );//显示图片
//							}
							treeJson.append(",iconCls:'org '" );//显示图片
							treeJson.append("}");
							if(firstItem) {
								firstItem = false;
							}
						}
					}
				}
				treeJson.append("]");
			}else {
				Object sci;
				list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",node,invalidFlag);
				//用于存放有下级节点的关系表oid
				Set<String> set = new HashSet<String>();
				//是否需要连表进行查询是否存在下级节点
				boolean flag = true;
				if(!"".equals(invalidFlag)){
					String[] orgRelaOid = new String[list.size()];
					//遍历获取组织关系表oid
					for(int i=0;i<list.size();i++){
						OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
						orgRelaOid[i]=ori.getId();
					}
					List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("", orgRelaOid, invalidFlag);
					for(int i=0;i<clist.size();i++){
						set.add(clist.get(i).getOrganizationRelationInfo().getId());
					}
					flag = false;
				}
				for(int i=0;i<list.size();i++){
					sci = list.get(i);
					if("1".equals(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose())){
						if(!firstItem){
							treeJson.append(",");
						}
						treeJson.append("{");
						treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
						treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
						treeJson.append(",orgEname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
						treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
						treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
						if(flag){
							int size = organizationRelationInfoDao.getOrgSize("",((OrganizationRelationInfo) sci).getId(),invalidFlag);
							//判断是否含有子节点
							if(size>0){
								treeJson.append(",leaf:false" );
								treeJson.append(",expanded:false");
							}else{
								treeJson.append(",leaf:true" );
							}
						}else{
							//判断是否含有子节点
							if(set.contains(((OrganizationRelationInfo) sci).getId())){
								treeJson.append(",leaf:false" );
								treeJson.append(",expanded:false");
							}else{
								treeJson.append(",leaf:true" );
							}
						}
//						if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
//							treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
//							treeJson.append(",iconCls:'chart_organisation'" );//显示图片
//						}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
//							treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
//							treeJson.append(",iconCls:'partverify'" );//显示图片
//						}
						treeJson.append("}");
						if(firstItem) {
							firstItem = false;
						}
					}
				}
				treeJson.append("]");
			}
		}
		return treeJson.toString();
	}
	
	@Autowired
	public JdbcTemplate  jdbcTemplate;
	
	/**
	 * 拼音检索组织树，仅供测试用
	 * @author hjh
	 * @return
	 * 2015-1-13下午06:29:40
	 * @Seealso getPinyinOrganTreeSpeeding
	 */
	public String getPinyinOrganTreeSpeeding(){
		StringBuffer treeJson = new StringBuffer("[");
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		String regionCode = personInfo.getRegionCode();
		String shortRegCode = regionCode.substring(0,6);
		
		StringBuffer sql = new StringBuffer();  
		sql.append(" select orgRel.oid,orgRel.organization_id,org.org_code,org.org_cname ")
		.append(" from organization_relation orgRel ")
		.append(" left join organization org on org.oid=orgRel.organization_id  ")
		.append(" left join organization parentOrg on parentOrg.oid=orgRel.organization_id  ")
		.append(" where orgRel.parent_org_id='"+shortRegCode+"' ")
		.append(" and org.purpose='1' ")
		.append(" and org.invalid_Flag='1' ")
		.append(" order by orgRel.organ_Order ")
		;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		boolean firstItem = true;
		for(int i=0;i<list.size();i++){
			Map tempMap = list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			treeJson.append("id:'" + tempMap.get("OID")+ "'");
			treeJson.append(",organId:'" +tempMap.get("ORGANIZATION_ID")+ "'");//用与转发地区验证
			treeJson.append(",name:'" +tempMap.get("ORG_CNAME")+ "'");
			treeJson.append(",code:'" +tempMap.get("ORG_CODE")+ "'");
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * @author lijun 2014-6-24
	 * 用于问题属地查询拼音检索下拉框的方法
	 * @throws IOException
	 * @param invalidFlag
	 * @param regionCode
	 * @param petitionTypeCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getPinyinOrganTreeByRegionAuthority(String invalidFlag, String regionCode, String petitionTypeCode){
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		Set<String> authorityRegion=getAllValidAuthorityRegionWithPetitionTypeCode(petitionTypeCode);
		if(authorityRegion.size()>0){
			Iterator<String> it=authorityRegion.iterator();
			StringBuffer region=new StringBuffer("");
			while(it.hasNext()){
				if(region.length()>0)
					region.append(",");
				region.append("'"+it.next()+"'");
			}
			OrganizationInfo organ = organizationInfoDao.findByUnique("orgCode", regionCode);
			OrganizationRelationInfo orgRelaInfo = organ.getOrganizationRelationInfo().iterator().next();
			List<OrganizationRelationInfo> list  =organizationRelationInfoDao.find(" from OrganizationRelationInfo" +
					" where organizationInfo.orgCode in("+region.toString()+") and organizationRelationInfo.id='"+orgRelaInfo.getId()+"'" +
					" and organizationInfo.purpose='1' and organizationInfo.invalidFlag='"+invalidFlag+"' order by organOrder");
			//用于存放有下级节点的关系表oid
			Set<String> set = new HashSet<String>();
			//是否需要连表进行查询是否存在下级节点
			if(!"".equals(invalidFlag)){
				String[] orgRelaOid = new String[list.size()];
				//遍历获取组织关系表oid
				for(int i=0;i<list.size();i++){
					OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
					orgRelaOid[i]=ori.getId();
				}
				List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("1", orgRelaOid, invalidFlag);
				for(int i=0;i<clist.size();i++){
					set.add(clist.get(i).getOrganizationRelationInfo().getId());
				}
			}
			for(int i=0;i<list.size();i++){
				OrganizationRelationInfo sci = list.get(i);
				organ = sci.getOrganizationInfo();
				if("1".equals(organ.getPurpose())){
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + sci.getId()+ "'");
					treeJson.append(",organId:'" +organ.getId()+ "'");//用与转发地区验证
					treeJson.append(",name:'" +organ.getOrgCname()+ "'");
					treeJson.append(",code:'" +organ.getOrgCode()+ "'");
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
			}
		}
		treeJson.append("]");
	return treeJson.toString();
	}

	/**
	 * 用于登陆时personInfo中downIssueRegionCode的查询
	 * @author liub 2014-6-24
	 * @throws IOException
	 * @param regionCode
	 * @param petitionTypeCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getDownIssueRegionCodeByRegionAuthority(String regionCode){
		StringBuffer downIssueRegionCode=new StringBuffer();
		OrganizationInfo organ = organizationInfoDao.findByUnique("orgCode", regionCode);
		OrganizationRelationInfo orgRelaInfo = organ.getOrganizationRelationInfo().iterator().next();
		List<OrganizationRelationInfo> list  =organizationRelationInfoDao.find(" from OrganizationRelationInfo" +
				" where   organizationRelationInfo.id='"+orgRelaInfo.getId()+"'" +
				" and organizationInfo.purpose='1' and organizationInfo.invalidFlag='1' order by organOrder");
		//用于存放有下级节点的关系表oid
		//是否需要连表进行查询是否存在下级节点
		downIssueRegionCode.append("{");
		for(int i=0;i<list.size();i++){
			OrganizationRelationInfo sci = list.get(i);
			organ = sci.getOrganizationInfo();
			if("1".equals(organ.getPurpose())){
				//organ.getOrgCode(),organ.getOrgCname()
				if(i!=0){
					downIssueRegionCode.append(",");
				}
				downIssueRegionCode.append(""+organ.getOrgCode()+":"+organ.getOrgCname()+"");
			}
		}
		downIssueRegionCode.append("}");
		return downIssueRegionCode.toString();
	}
	/**
	 * 通过组织基本信息中文名或者英文名查找组织关系对象集合
	 * @date 2010-11-29
	 * @author wangxx
	 * @param filterValue  组织基本信息中文名或者英文名对应的值
	 * @return 组织关系对象集合
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> getOrganizationRelationInfoListByOrganization(
			String filterValue,String orgCode){
		Session session = organizationInfoDao.getSession();
		Criteria criteria = session.createCriteria(OrganizationRelationInfo.class);
		criteria = criteria.createCriteria("organizationInfo");
		criteria = criteria.add(Restrictions.or(Restrictions.like("orgEname", filterValue)
				,Restrictions.like("orgCname", filterValue)));
		criteria = criteria.add(Restrictions.ne("orgCname", "中央"));
		criteria = criteria.add(Restrictions.ne("orgCname", "组织机构"));
//		criteria = criteria.add(Restrictions.like("orgCode", orgCode));
		criteria = criteria.add(Restrictions.eq("invalidFlag", "1"));
		criteria = criteria.add(Restrictions.eq("purpose", "1"));
		List<OrganizationRelationInfo> list = criteria.list();
		return list;
	}
	
	/**
	 * 通过组织编码获得下级组织编码和组织中文名
	 * @date 2010-11-29
	 * @author Liuchang
	 * @param regionCode 当前用户组织编码
	 * @return 组织编码和组织中文名集合
	 */
	@Transactional(readOnly=true)
	public StringBuffer getChildrensOrganization(String regionCode){
		String orgCodeSubstr = "";
		String orgCodeLike = "";
		if(regionCode.substring(0,2).equals("00")){//中央
			orgCodeLike = "%0000000000";
		}else if(!regionCode.substring(0,2).equals("00") 
				&& regionCode.substring(2,4).equals("00")){//省级
			orgCodeSubstr = regionCode.substring(0,2);
			orgCodeLike = "%00000000";
		}else if(!regionCode.substring(2,4).equals("00") 
				&& regionCode.substring(4,6).equals("00")){//市级
			orgCodeSubstr = regionCode.substring(0,4);
			orgCodeLike = "%000000";
		}else if(!regionCode.substring(4,6).equals("00") 
				&& regionCode.substring(6,8).equals("00")){//县级
			orgCodeSubstr = regionCode.substring(0,6);
			orgCodeLike = "%0000";
		}
		Set<String> authorityRegion=getPersonAuthorityRegion();
		List<OrganizationInfo> list = petitionOrganInfoDao.getChildrensOrganization(
				regionCode, orgCodeSubstr, "%", authorityRegion);
		StringBuffer bf = new StringBuffer("[");
		boolean flag = false;
		for (OrganizationInfo organizationInfo : list) {
			if(flag){
				bf.append(",");
			}
			bf.append("{");
			bf.append("text:'");
			bf.append(organizationInfo.getOrgCname());
			bf.append("'");
			bf.append(",code:'");
			bf.append(organizationInfo.getOrgCode());
			bf.append("'");
			bf.append(",leaf:true");
			bf.append(",checked:false}");
			flag=true;
		}
		bf.append("]");
		return bf;
	}
	@Override
	protected HibernateDao<OrganizationInfo, String> getEntityDao() {
		return organizationInfoDao;
	}
	
	/**
	 * 显示本机及下级区域
	 * 调用哪些方法:
	 * 1、getOrgAndRelaInfoByCondition 条件查询区域或部门
	 * 2、
	 * @param node
	 * @param purpose 1：区域 2：部门
	 * @return 组织关联集合信息
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrgAndRelaInfoByOrgCode(String node,String purpose,String filterText,String filterValue){
		StringBuffer treeJson = new StringBuffer("[");
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		boolean firstItem = true;
		if(filterText!=null && !filterText.trim().equals("") && filterValue!=null && !filterValue.equals("")){
			return getOrgAndRelaInfoByCondition(personInfo.getRegionCode(),filterText,filterValue,purpose );
		}else{
			//nodeFlag 为空：系统第一次查询节点并且是根节点
			String nodeFlag="";
			OrganizationRelationInfo organizationRelationInfo = null ;
			if(node.equals("-1")){
				organizationRelationInfo= organizationRelationInfoDao.getOrgRelaInfoByOrgCode(personInfo.getRegionCode()).get(0);
				if(organizationRelationInfo.getOrganizationRelationInfo().getId()==null||
						organizationRelationInfo.getOrganizationRelationInfo().getId().trim().equals("")){
					treeJson.append("{");
					treeJson.append("id:'" + organizationRelationInfo.getId()+ "'");
					treeJson.append(",text:'" + organizationRelationInfo.getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",orgShortCname:'" + organizationRelationInfo.getOrganizationInfo().getOrgShortCname()+"'");
					int size = organizationRelationInfoDao.getOrgSize("",organizationRelationInfo.getId(),"");
					//判断是否含有子节点
					if(size>0){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
					treeJson.append(",organOrder:''");
					treeJson.append(",parentOrgCname:''");
					treeJson.append(",invalidFlag:''");
					treeJson.append(",orgCode:''");
					treeJson.append(",orgOrDept:''" );
					treeJson.append(",iconCls:'cog'" );
					treeJson.append(",isType:'system'");
					treeJson.append(",isAdd:true");
					treeJson.append(",isUpdate:false");
					treeJson.append(",isDelete:false");
					treeJson.append(",expanded:false");
					treeJson.append(",iconCls:'cog'" );//显示图片
					treeJson.append("}]");
				}else{
					nodeFlag=organizationRelationInfo.getId();
					
				}
			}else{
				nodeFlag="1";
			}
			if(nodeFlag.trim().equals("")){
				return treeJson.toString();
			}else{
				List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>() ;
				if(!nodeFlag.trim().equals("1")){
					list=organizationRelationInfoDao.find("from OrganizationRelationInfo o where o.id='"+nodeFlag+"'  order by o.organOrder asc");
					
				}else{
					list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(purpose,node,"");
				}
				Object sci;
				//初始化时查询系统定义表
				//用于存放有下级节点的关系表oid
				Set<String> set = new HashSet<String>();
				//是否需要连表进行查询是否存在下级节点
				String[] orgRelaOid = new String[list.size()];
				//遍历获取组织关系表oid
				for(int i=0;i<list.size();i++){
					OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
					orgRelaOid[i]=ori.getId();
				}
				List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid(purpose, orgRelaOid,"");
				for(int i=0;i<clist.size();i++){
					set.add(clist.get(i).getOrganizationRelationInfo().getId());
				}
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",orgShortCname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
					treeJson.append(",organOrder:'" + ((OrganizationRelationInfo) sci).getOrganOrder()+ "'");
					treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
					treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
					treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
					//判断是否含有子节点
					if(set.contains(((OrganizationRelationInfo) sci).getId())){
						treeJson.append(",leaf:false" );
						treeJson.append(",expanded:false");
					}else{
						treeJson.append(",leaf:true" );
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
						treeJson.append(",iconCls:'chart_organisation'" );//显示图片
						treeJson.append(",isType:'organ'");
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
						treeJson.append(",iconCls:'partverify'" );//显示图片
					}
					treeJson.append(",isAdd:true");
					treeJson.append(",isUpdate:true");
					//初始话节点禁止删除
					String orgCode=((OrganizationRelationInfo)sci).getOrganizationInfo().getOrgCode();
					if(((OrganizationRelationInfo)sci).getOrganizationInfo().getPurpose().equals("1")){//区域
						orgCode=orgCode.substring(0,6);
						try {
							Integer.parseInt(orgCode);
							treeJson.append(",isDelete:false");
						} catch (Exception e) {
							treeJson.append(",isDelete:true");
						}
					}else if(((OrganizationRelationInfo)sci).getOrganizationInfo().getPurpose().equals("2")){//部门
						if(orgCode.length()>9 && orgCode.substring(8, 9).equals("D")){
							treeJson.append(",isDelete:true");
						}else{
							treeJson.append(",isDelete:false");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				treeJson.append("]");
				
			}
		}
		return treeJson.toString();
		
	}
	
	/**
	 * 显示本级及下级区域
	 * 调用哪些方法:
	 * 1、organizationRelationInfoDao.getOrgRelaInfoByOrgCode
	 * 2、organizationRelationInfoDao.getOrganRelaInfoByParentOid
	 * 被哪些方法调用:
	 * 1、PetitionOrganInfoAction.getCurrentOrgAndNextLevel
	 * @param node
	 * @param purpose 1：区域 2：部门
	 * 2013-7-9 hxh 添加 invalidFlag参数：1：有效组织 2:无效组织 空（""）:全部 
	 * @return 组织关联集合信息
	 */
	@Transactional(readOnly=true)
	public String getCurrentOrgAndNextLevel(String node,String purpose,String invalidFlag,String filterText,String filterValue){
		StringBuffer treeJson = new StringBuffer("[");
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		boolean firstItem = true;
		if(filterText!=null && !filterText.trim().equals("") && filterValue!=null && !filterValue.equals("")){
			return getOrgAndRelaInfoByCondition(personInfo.getRegionCode(),filterText,filterValue,purpose );
		}else{
			//nodeFlag 为空：系统第一次查询节点并且是根节点
			//String nodeFlag="";
			OrganizationRelationInfo organizationRelationInfo = null ;
			if(node.equals("-1")){
				organizationRelationInfo= organizationRelationInfoDao.getOrgRelaInfoByOrgCode(personInfo.getRegionCode()).get(0);
				/*if(organizationRelationInfo.getOrganizationRelationInfo().getId()==null||
						organizationRelationInfo.getOrganizationRelationInfo().getId().trim().equals("")){*/
					treeJson.append("{");
					treeJson.append("id:'" + organizationRelationInfo.getId()+ "'");
					treeJson.append(",text:'" + organizationRelationInfo.getOrganizationInfo().getOrgCname()+ "纪委'");
					treeJson.append(",orgShortCname:'" + organizationRelationInfo.getOrganizationInfo().getOrgShortCname()+"纪委'");
					int size = organizationRelationInfoDao.getOrgSize("",organizationRelationInfo.getId(),"");
					//判断是否含有子节点
					if(size>0){
						treeJson.append(",leaf:false" );
					}else{
						treeJson.append(",leaf:true" );
					}
					treeJson.append(",organOrder:'"+organizationRelationInfo.getOrganOrder()+"'");
					treeJson.append(",parentOrgCname:'"+organizationRelationInfo.getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+"'");
					treeJson.append(",invalidFlag:'"+organizationRelationInfo.getOrganizationInfo().getInvalidFlag()+"'");
					treeJson.append(",orgCode:'"+organizationRelationInfo.getOrganizationInfo().getOrgCode()+"'");
					if(organizationRelationInfo.getOrganizationInfo().getPurpose()!=null &&
							organizationRelationInfo.getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",orgOrDept:'dept'" );
					}else{
						treeJson.append(",orgOrDept:'org'" );
					}
					treeJson.append(",iconCls:'cog'" );
					treeJson.append(",isType:'system'");
					treeJson.append(",isAdd:true");
					treeJson.append(",isUpdate:false");
					treeJson.append(",isDelete:false");
					treeJson.append(",expanded:true");
					treeJson.append(",iconCls:'cog'" );//显示图片
					treeJson.append("}]");
					return treeJson.toString();
					/*}else{
					nodeFlag=organizationRelationInfo.getId();
					
				}*/
			}/*else{
				nodeFlag="1";
			}*/
//			if(nodeFlag.trim().equals("")){
//				return treeJson.toString();
//			}else{
				List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>() ;
//				if(!nodeFlag.trim().equals("1")){
//					list=organizationRelationInfoDao.find("from OrganizationRelationInfo o where o.id='"+nodeFlag+"'  order by o.organOrder asc");
//					
//				}else{
					list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(purpose,node,invalidFlag);
//				}
				Object sci;
				Set<String> set = new HashSet<String>();
				if(purpose!=null && purpose.equals("2")){
					//初始化时查询系统定义表
					//用于存放有下级节点的关系表oid
					//是否需要连表进行查询是否存在下级节点
					String[] orgRelaOid = new String[list.size()];
					//遍历获取组织关系表oid
					for(int i=0;i<list.size();i++){
						OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
						orgRelaOid[i]=ori.getId();
					}
					List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid(purpose, orgRelaOid,"");
					for(int i=0;i<clist.size();i++){
						set.add(clist.get(i).getOrganizationRelationInfo().getId());
					}
				}
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
					treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",orgShortCname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
					treeJson.append(",organOrder:'" + ((OrganizationRelationInfo) sci).getOrganOrder()+ "'");
					treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
					treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
					treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
					treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
					if(purpose!=null && purpose.equals("2")){
						//判断是否含有子节点
						if(set.contains(((OrganizationRelationInfo) sci).getId())){
							treeJson.append(",leaf:false" );
							treeJson.append(",expanded:false");
						}else{
							treeJson.append(",leaf:true" );
						}
						treeJson.append(",isAdd:true");
					}else{
						treeJson.append(",leaf:true" );
						treeJson.append(",isAdd:false");
					}
					if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
						treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
						treeJson.append(",iconCls:'chart_organisation'" );//显示图片
						treeJson.append(",isType:'organ'");
					}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
						treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
						treeJson.append(",iconCls:'partverify'" );//显示图片
					}
					treeJson.append(",isUpdate:true");
					//初始话节点禁止删除
					String orgCode=((OrganizationRelationInfo)sci).getOrganizationInfo().getOrgCode();
					if(((OrganizationRelationInfo)sci).getOrganizationInfo().getPurpose().equals("1")){//区域
						orgCode=orgCode.substring(0,6);
						try {
							Integer.parseInt(orgCode);
							treeJson.append(",isDelete:false");
						} catch (Exception e) {
							treeJson.append(",isDelete:true");
						}
					}else if(((OrganizationRelationInfo)sci).getOrganizationInfo().getPurpose().equals("2")){//部门
						if(orgCode.length()>9 && orgCode.substring(8, 9).equals("D")){
							treeJson.append(",isDelete:true");
						}else{
							treeJson.append(",isDelete:false");
						}
					}
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
				treeJson.append("]");
				
			//}
		}
		return treeJson.toString();
		
	}
	/**
	 * 显示部门权限下拉框
	 * @author lijun
	 * 调用哪些方法:
	 * 1、organizationRelationInfoDao.getOrganRelaInfoByParentOid
	 * 被哪些方法调用:
	 * 1、PetitionOrganInfoAction.getOrgPowerCombox
	 * @param purpose 1：区域 2：部门
	 * @param parpentOrgId 上级部门id
	 * @param invalidFlag  是否有效
	 * @return 部门权限下拉框信息
	 * @update lijun 2014-3-20
	 *	1、将拼接name属性字符串的getOrgShortCname()改为getOrgCname()
	 */
	@Transactional(readOnly=true)
	public String getOrgPowerCombox(String purpose,String parpentOrgId,String invalidFlag){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>() ;
		list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(purpose,parpentOrgId,invalidFlag);
		for(OrganizationRelationInfo organizationRelationInfo : list){
			treeJson.append("{");
			treeJson.append("code:'" + organizationRelationInfo.getOrganizationInfo().getOrgCode() + "'");
			treeJson.append(",name:'" + organizationRelationInfo.getOrganizationInfo().getOrgCname() + "'");
			treeJson.append("},");
		}
		treeJson.deleteCharAt(treeJson.length()-1);
		treeJson.append("]");
			
		return treeJson.toString();
		
	}
	/**
	 * 生成区域编码和排序号
	 * @author hxh
	 * 调用哪些方法：
	 * 1、maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode 
	 * 被哪些方法调用
	 * 1、PetitionOrganInfoAction.getMaxOrgCodeAndOrder
	 * @param orgCode
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getMaxOrgCodeAndOrder(String regionCode){
		String zero="",regionFlag="";
		String provRegionCode=regionCode.substring(0,2);//省级
		String cityRegionCode = regionCode.substring(2,4);//市级
		String countyRegionCode = regionCode.substring(4,6);//县级
		if(!provRegionCode.equals("00") && !cityRegionCode.equals("00") && !countyRegionCode.equals("00"))
		{
			regionFlag = provRegionCode+cityRegionCode;
			zero = "";
		}
		else if(!provRegionCode.equals("00") && cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			regionFlag = provRegionCode;
			zero = "00";
		}
		else if(!provRegionCode.equals("00") && !cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			regionFlag = provRegionCode+cityRegionCode;
			zero = "";
				
		}else{
		    regionFlag = "";
			zero = "0000";
		}
		char provMaxNo='A',cityMaxNo='A';
		StringBuffer maxNoInfoId=new StringBuffer();
		String maxOrgCode="",
		       provNoId="",//省级区域id
		       cityNoId="",//市级区域id
		       countyNoId="",//县级区域id
		       orderId="";
		//区域显示顺序
		int  countyMaxNo=0,organOrder=1;
		//String[] noType=new String[]{"ORGCODEPROV","ORGCODECITY","ORGANORDER"};//号码类型
		StringBuffer noType=new StringBuffer("ORGCODEPROV,ORGCODECITY,ORGANORDER");
		if(!provRegionCode.equals("00") && !cityRegionCode.equals("00") && !countyRegionCode.equals("00")){
			//noType=new String[]{"ORGCODEPROV","ORGCODECITY","ORGCODECOUNTY","ORGANORDER"};
			noType.append(",ORGCODECOUNTY");
		}
		List<PetitionMaxNoInfo> list=maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(noType.toString().split(","), regionCode);
		//if(!list.isEmpty()){
			for(PetitionMaxNoInfo petitionMaxNoInfo:list){
				if("ORGCODEPROV".equals(petitionMaxNoInfo.getNoType().trim())){
					provNoId=petitionMaxNoInfo.getId();
					provMaxNo=(char)petitionMaxNoInfo.getMaxNo();
				}else if("ORGCODECITY".equals(petitionMaxNoInfo.getNoType().trim())){
					cityNoId=petitionMaxNoInfo.getId();
					cityMaxNo=(char)petitionMaxNoInfo.getMaxNo();
				}else if("ORGCODECOUNTY".equals(petitionMaxNoInfo.getNoType().trim())){
					countyNoId=petitionMaxNoInfo.getId();
					countyMaxNo=(char)petitionMaxNoInfo.getMaxNo();
				}else if("ORGANORDER".equals(petitionMaxNoInfo.getNoType().trim())){
					organOrder=petitionMaxNoInfo.getMaxNo();
					orderId=petitionMaxNoInfo.getId();
				}
			}
		//}
		if(!provNoId.equals("") && !cityNoId.equals("")){
			maxNoInfoId.append(provNoId).append(",").append(cityNoId);
		}
		if(!provRegionCode.equals("00") && !cityRegionCode.equals("00") && !countyRegionCode.equals("00")){
			maxOrgCode = regionFlag + provMaxNo + countyMaxNo + zero;
			if(!countyNoId.equals("")){
				maxNoInfoId.append(",").append(countyNoId);
			}
		}else{
			maxOrgCode = regionFlag + provMaxNo + cityMaxNo +zero;
		}

		if(!orderId.equals("")){
			maxNoInfoId.append(",").append(orderId);
		}
		StringBuffer json = new StringBuffer("");
		json.append("{");
		json.append("maxOrgCode:'"+maxOrgCode.concat("000000")+"'");
		json.append(",maxNoInfoId:'"+maxNoInfoId.toString()+"'");
		json.append(",organOrder:'"+organOrder+"'");
		json.append("}");
		 
		return json.toString();
	}
	public static void main(String[] args) {
		char provMaxNo='A',cityMaxNo='A';
		int a=416790;
		 String s = String.valueOf(a);   
		 char c = s.charAt(2);   
		 System.out.println(c);
//		cityMaxNo=(char)a;
//		cityMaxNo  =   (char)(a   &   0xff); 
//		cityMaxNo  =   (char)((a  >>  8)   &   0xff); 
//		cityMaxNo =   (char)((a   >>   16)   &   0xff); 
//		cityMaxNo  =   (char)((a   >>   24)   &   0xff);


		System.out.println(cityMaxNo);
	}
	/**
	 * 生成部门编码
	 * @param orgCode
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getMaxDeptNo(String orgCode){
		String maxDeptNoId="";
		String maxNo="";
		StringBuffer IdsInfo=new StringBuffer("");
		//部门显示顺序
		int deptOrder=1;
		String maxOrderId="";
		String[] noType=new String[]{"DEPTNO","DEPTORDER"};//号码类型
		List<PetitionMaxNoInfo> list=maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(noType,
				"SN", orgCode);
		if(!list.isEmpty()){
			for(PetitionMaxNoInfo petitionMaxNoInfo:list){
				if(petitionMaxNoInfo.getNoType().trim().equals("DEPTNO")){
					maxDeptNoId=petitionMaxNoInfo.getId();
					maxNo=((Integer)petitionMaxNoInfo.getMaxNo()).toString();
					int length=maxNo.length();
					int cLength=3;
					String cReturn="";
					String[] zero={"00","0"};
					if(length<cLength){
						cReturn=zero[length-1];
					}
					maxNo=orgCode.substring(0,8).concat("D").concat(cReturn).concat(maxNo);
					IdsInfo.append(maxDeptNoId);
				}else if(petitionMaxNoInfo.getNoType().trim().equals("DEPTORDER")){
					maxOrderId=petitionMaxNoInfo.getId();
					deptOrder=petitionMaxNoInfo.getMaxNo();
				}
			}
		}else{
			maxNo=orgCode.substring(0,8).concat("D").concat("001");
		}
		if(!maxOrderId.equals("")){
			IdsInfo.append(",").append(maxOrderId);
		}
		StringBuffer json = new StringBuffer("");
		json.append("{");
		json.append("maxDeptNo:'"+maxNo+"'");
		json.append(",IdsInfo:'"+IdsInfo.toString()+"'");
		json.append(",deptOrder:'"+deptOrder+"'");
		json.append("}");
		return json.toString();
	}
	/**
	 * 保存组织信息前保存区域编码和排序
	 * 调用哪些方法：
	 * 1、organizationInfoManager.insertOrganizationInfo 新增组织
	 * 2、organizationInfoManager.saveOrganizationInfo 修改保存组织
	 * 被哪些方法调用：
	 * 1、PetitionOrganInfoAction.saveOrganizationInfo
	 * @param flag
	 * @param maxNoInfoId
	 * @param organ
	 * @param organRelationInfoOid
	 * @param organOrder
	 * @param relation
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganizationInfo(String flag,String maxNoInfoId,OrganizationInfo organ,String organRelationInfoOid,String organOrder,String relation){
		//判断是否是新增组织
		if(flag.equals("insert")){
			//区域
			OrganizationRelationInfo o=organizationRelationInfoDao.get(organRelationInfoOid);
			if(organ.getPurpose().equals("1")){
				if(maxNoInfoId!=null&&!maxNoInfoId.trim().equals("")){
					String orgCode=o.getOrganizationInfo().getOrgCode();
					String cityRegionCode=orgCode.substring(2,4);
					String countyRegionCode=orgCode.substring(4,6);
					String[] id=maxNoInfoId.split(",");
					if(cityRegionCode.equals("00") || countyRegionCode.equals("00")){
						PetitionMaxNoInfo petitionMaxNoInfob=maxNoInfoDao.get(id[1]);
						if(petitionMaxNoInfob.getMaxNo()==90){
							petitionMaxNoInfob.setMaxNo(65);
							PetitionMaxNoInfo petitionMaxNoInfoa=maxNoInfoDao.get(id[0]);
							if(petitionMaxNoInfoa.getMaxNo()==90){
								petitionMaxNoInfoa.setMaxNo(65);
							}else{
								petitionMaxNoInfoa.setMaxNo(petitionMaxNoInfoa.getMaxNo()+1);
							}
							maxNoInfoDao.save(petitionMaxNoInfoa);
						}else{
							petitionMaxNoInfob.setMaxNo(petitionMaxNoInfob.getMaxNo()+1);
						}
						maxNoInfoDao.save(petitionMaxNoInfob);
						PetitionMaxNoInfo maxOrganOrderInfo=maxNoInfoDao.get(id[2]);
						maxOrganOrderInfo.setMaxNo(maxOrganOrderInfo.getMaxNo()+1);
					}else{
						PetitionMaxNoInfo petitionMaxNoInfoc=maxNoInfoDao.get(id[2]);
						if(petitionMaxNoInfoc.getMaxNo()==9){
							petitionMaxNoInfoc.setMaxNo(0);
							PetitionMaxNoInfo petitionMaxNoInfoa=maxNoInfoDao.get(id[0]);
							if(petitionMaxNoInfoa.getMaxNo()==90){
								petitionMaxNoInfoa.setMaxNo(65);
							}else{
								petitionMaxNoInfoa.setMaxNo(petitionMaxNoInfoa.getMaxNo()+1);
							}
							maxNoInfoDao.save(petitionMaxNoInfoa);
						}else{
							petitionMaxNoInfoc.setMaxNo(petitionMaxNoInfoc.getMaxNo()+1);
						}
						maxNoInfoDao.save(petitionMaxNoInfoc);
						PetitionMaxNoInfo maxOrganOrderInfo=maxNoInfoDao.get(id[3]);
						maxOrganOrderInfo.setMaxNo(maxOrganOrderInfo.getMaxNo()+1);
					}
					
				}else{
					//区域编码的保存
					for(int i=0;i<4;i++){
						PetitionMaxNoInfo maxNoInfo=new PetitionMaxNoInfo();
						maxNoInfo.setId(null);
						maxNoInfo.setRegionCode(o.getOrganizationInfo().getOrgCode());
						if(i==0){
							maxNoInfo.setNoType("ORGCODEPROV");
							maxNoInfo.setMaxNo(65);//字符A
						}else if(i==1){
							maxNoInfo.setNoType("ORGCODECITY");
							maxNoInfo.setMaxNo(66);//字符B
						}else if(i==2){
							maxNoInfo.setNoType("ORGCODECOUNTY");
							maxNoInfo.setMaxNo(1);
						}else if(i==3){
							maxNoInfo.setNoType("ORGANORDER");
							maxNoInfo.setMaxNo(2);
						}
						maxNoInfoDao.save(maxNoInfo);
					}
				}
			}else if(organ.getPurpose().equals("2")){//部门
				if (maxNoInfoId != null && !maxNoInfoId.trim().equals("")) {
					String[] id=maxNoInfoId.split(",");
					for(int i=0;i<id.length;i++){
						PetitionMaxNoInfo petitionMaxNo=maxNoInfoDao.get(id[i]);
						petitionMaxNo.setMaxNo(petitionMaxNo.getMaxNo()+1);
					}
				}else{
					for(int i=0;i<2;i++){
						PetitionMaxNoInfo maxNoInfo=new PetitionMaxNoInfo();
						maxNoInfo.setId(null);
						maxNoInfo.setRegionCode(o.getOrganizationInfo().getOrgCode());
						if(i==0){
							maxNoInfo.setNoType("DEPTNO");
							maxNoInfo.setMaxNo(2);
						}else if(i==1){
							maxNoInfo.setNoType("DEPTORDER");
							maxNoInfo.setMaxNo(2);
						}
						maxNoInfoDao.save(maxNoInfo);
					}
				}
			}
			//保存机构信息
			//判断是否是新增的组织
			if(organ.getId()==null||"".equals(organ.getId().trim())){
				//新增的组织
				//此处如果organ的oid不是null，就会执行update操作，而不会增，oid也为空字符串，在级联保存时会有问题，所以给设置为null
				organ.setId(null);
				organizationInfoManager.insertOrganizationInfo(organ, organRelationInfoOid, organOrder, relation);
			}else{
				organizationInfoManager.insertOrganizationInfo(organ, organRelationInfoOid, Integer.parseInt(organOrder), relation);
			}
		}else if(flag.equals("save")){
			//保存 修改组织信息
			organizationInfoManager.saveOrganizationInfo(organ, organRelationInfoOid, organOrder, relation);
		}
  }

	/**
	 * 条件查询区域及区域关系集合
	 * @param regionCode 用户区域编码
	 * @param filterText 查询字段
	 * @param filterValue 查询值
	 * @return 
	 */
	@Transactional(readOnly=true)
	public String getOrgAndRelaInfoByCondition(String regionCode,String filterText,String filterValue,String purpose ){
		String provRegionCode=regionCode.substring(0,2);//省级
		String cityRegionCode = regionCode.substring(2,4);//市级
		String countyRegionCode = regionCode.substring(4,6);//县级
		/*//区域编码
		String orgCode="";
		//标示区域是否有效
		String invalidFlag="";
		//区域名称
		String orgName="";*/
		boolean firstItem = true;
		String region="";
		StringBuffer treeJson=new StringBuffer("[");
		//List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>() ;
		if(!provRegionCode.equals("00")&&!cityRegionCode.equals("00")&&!countyRegionCode.equals("00")){
			region=regionCode.substring(0,6).concat("%");
		}else if(!provRegionCode.equals("00") && cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			region=regionCode.substring(0, 2).concat("%");
		}else if(!provRegionCode.equals("00") && !cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			region=regionCode.substring(0, 4).concat("%");
		}else if(provRegionCode.equals("00") && cityRegionCode.equals("00") && countyRegionCode.equals("00")){
			region="000000%";
		}
		/*
		String[] texts=filterText.split(";");
		String[] values=filterValue.split(";");
		for(int i=0;i<texts.length;i++){
			if(texts[i].equals("orgName")){
				orgName=values[i].concat("%");
			}else if(texts[i].equals("invalidFlag")){
				invalidFlag=values[i];
			}else if(texts[i].equals("orgCode")){
				orgCode=values[i];
			}
		}*/
		//list=petitionOrganInfoDao.getOrgRelaInfoByOrgCodeAndinvalidFlag(region,invalidFlag,orgName,purpose);
		List<Object[]> objList=petitionOrganInfoDao.getOrgRelaInfo(region,filterText,filterValue,purpose);
		for(Object[] o:objList){
			/*if(!orgCode.equals("")){
				if(!o.getOrganizationInfo().getOrgCode().startsWith(orgCode))
					continue;
			}*/
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			
			/*treeJson.append("id:'" +((OrganizationRelationInfo) o).getId()+ "'");
			treeJson.append(",text:'" + ((OrganizationRelationInfo) o).getOrganizationInfo().getOrgCname()+ "'");
			treeJson.append(",orgShortCname:'" + ((OrganizationRelationInfo) o).getOrganizationInfo().getOrgShortCname()+ "'");
			treeJson.append(",organOrder:'" + ((OrganizationRelationInfo) o).getOrganOrder()+ "'");
			treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) o).getOrganizationInfo().getInvalidFlag()+ "'");
			treeJson.append(",parentOrgCname:'" + ((OrganizationRelationInfo) o).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "'");
			treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) o).getOrganizationInfo().getOrgCode()+ "'");
			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) o).getOrganizationInfo().getId()+ "'");*/
			
			treeJson.append("id:'" +o[0].toString()+ "'");
			treeJson.append(",text:'" +o[1].toString() + "'");
			treeJson.append(",orgShortCname:'" + o[2].toString()+ "'");
			treeJson.append(",organOrder:'" + o[3].toString()+ "'");
			treeJson.append(",invalidFlag:'" + o[4].toString()+ "'");
			treeJson.append(",parentOrgCname:'" +o[5].toString()+ "'");
			treeJson.append(",orgCode:'" +o[6].toString()+ "'");
			treeJson.append(",organizationInfoId:'" + o[7].toString()+ "'");
			treeJson.append(",leaf:true" );
			if(purpose.equals("1")){
				treeJson.append(",orgOrDept:'org'" );//该节点的身份 为机构
				treeJson.append(",iconCls:'chart_organisation'" );//显示图片
				treeJson.append(",isType:'organ'");
			}else if(purpose.equals("2")){
				treeJson.append(",orgOrDept:'dept'" );//该节点的身份 为部门
				treeJson.append(",iconCls:'partverify'" );//显示图片
			}
			treeJson.append(",isUpdate:true");
			//初始话节点禁止删除
			String orgCode1=o[6].toString();
			if(orgCode1.equals("000000000000")){
				treeJson.append(",isAdd:true");
			}else{
				treeJson.append(",isAdd:false");
			}
			if(o[8].toString().equals("1")){//区域
				orgCode1=orgCode1.substring(0,6);
				try {
					Integer.parseInt(orgCode1);
					treeJson.append(",isDelete:false");
				} catch (Exception e) {
					treeJson.append(",isDelete:true");
				}
			}else if(o[8].toString().equals("2")){//部门
				if(orgCode1.length()>9 && orgCode1.substring(8, 9).equals("D")){
					treeJson.append(",isDelete:true");
				}else{
					treeJson.append(",isDelete:false");
				}
			}
			
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		return treeJson.append("]").toString();
	}
	
	/**
	 * 生成部门编码和排序号
	 * @author hxh
	 * 调用哪些方法:
	 * 1、maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode
	 * 被哪些方法调用:
	 * 1、PetitionOrganInfoAction.getMaxDeptNoAndOrder
	 * @param orgCode
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getMaxDeptNoAndOrder(String orgCode){
		String maxDeptNoId="",
		       maxNo="",
		       maxOrderId="";
		StringBuffer IdsInfo=new StringBuffer("");
		//部门显示顺序
		int deptOrder=1;
		String[] noType=new String[]{"DEPTNO","DEPTORDER"};//号码类型
		List<PetitionMaxNoInfo> list=maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(noType,
				 orgCode);
		if(!list.isEmpty()){
			for(PetitionMaxNoInfo petitionMaxNoInfo:list){
				if(petitionMaxNoInfo.getNoType().trim().equals("DEPTNO")){
					maxDeptNoId=petitionMaxNoInfo.getId();
					maxNo=((Integer)petitionMaxNoInfo.getMaxNo()).toString();
					int length=maxNo.length();
					int cLength=3;
					String cReturn="";
					String[] zero={"00","0"};
					if(length<cLength){
						cReturn=zero[length-1];
					}
					maxNo=orgCode.substring(0,8).concat("D").concat(cReturn).concat(maxNo);
					IdsInfo.append(maxDeptNoId);
				}else if(petitionMaxNoInfo.getNoType().trim().equals("DEPTORDER")){
					maxOrderId=petitionMaxNoInfo.getId();
					deptOrder=petitionMaxNoInfo.getMaxNo();
				}
			}
		}else{
			maxNo=orgCode.substring(0,8).concat("D").concat("001");
		}
		if(!maxOrderId.equals("")){
			IdsInfo.append(",").append(maxOrderId);
		}
		StringBuffer json = new StringBuffer("");
		json.append("{");
		json.append("maxDeptNo:'"+maxNo+"'");
		json.append(",IdsInfo:'"+IdsInfo.toString()+"'");
		json.append(",deptOrder:'"+deptOrder+"'");
		json.append("}");
		return json.toString();
	}
	
	/**
	 * @author wangxx
	 * 2010-12-06
	 * 分级加载树形组件
	 * 此处还可以扩展，例如设定特定节点为根结点，只要添加一个参数，再①处判断该节点是否为空
	 * 	 1、如果为空就不以自定义的节点为根节点
	 *   2、如果不为空则自定义节点为根节点
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTree(String node,String isChecked,String text,String isOrgan,String invalidFlag){
		//PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "";
		boolean flag = false;
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//①
			flag = true;
			//根节点
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo is null  order by organOrder");
		}else {
			if(isOrgan.equals("organ")){
				//显示机构
				orgPurpose = "1";
			}else if(isOrgan.equals("dept")){
				//显示部门
				orgPurpose = "2";
			}
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose,node,invalidFlag);
		}
		boolean firstItem = true;
		Object sci;
		//用于存放有下级节点的关系表oid
		Set<String> set = new HashSet<String>();
		//是否需要连表进行查询是否存在下级节点
		String[] orgRelaOid = new String[list.size()];
		if(!flag){
			//遍历获取组织关系表oid
			for(int i=0;i<list.size();i++){
				OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
				orgRelaOid[i]=ori.getId();
			}
			List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose, orgRelaOid, invalidFlag);
			for(int i=0;i<clist.size();i++){
				set.add(clist.get(i).getOrganizationRelationInfo().getId());
			}
		}
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
			treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
			treeJson.append(",code:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
			}
			treeJson.append(",editable:false");
			if(flag){
				//不连表
				int size = 0;
				//判断是存在要显示的下级节点
				size = organizationRelationInfoDao.getOrgSize(orgPurpose,((OrganizationRelationInfo) sci).getId(),invalidFlag);
				//判断是否含有子节点
				if(size>0){
					//存在下级节点
					treeJson.append(",leaf:false" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
				}
			}else{
				//连表
				//判断是否含有子节点
				if(set.contains(((OrganizationRelationInfo) sci).getId())){
					//存在下级节点
					treeJson.append(",leaf:false" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
				}
			}
			if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
				//该节点身份为机构
				treeJson.append(",iconCls:'chart_organisation'" );
				treeJson.append(",isType:'organ'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:true");
				treeJson.append(",isDelete:true");
				treeJson.append(",isMerge:true");
				treeJson.append(",isSplit:true");
			}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
				//该节点身份为部门
				treeJson.append(",iconCls:'partverify'" );
				treeJson.append(",isType:'dept'");
				treeJson.append(",isAdd:false");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}else{
				//该节点身份为根节点
				treeJson.append(",iconCls:'cog'" );
				treeJson.append(",isType:'system'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}
			treeJson.append(",expanded:false");
			//判断复选框属性
			if(isChecked.equals(Constants.TRUE)){
				//带复选框并且选中
				treeJson.append(",checked:true");
			}else if(isChecked.equals("false")){
				//带复选框但不选中
				treeJson.append(",checked:false");
			}
			treeJson.append(",allowDelete:true"); 
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	
	/**
	 * 查询当前用户区域下的所有部门，子部门不包含父部门
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> getOrganDeptByPersonOrgCode(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.
				find("from OrganizationRelationInfo" +
					" where organizationRelationInfo.organizationInfo.orgCode='"+person.getRegionCode()+"'" +
					" and organizationInfo.purpose='2'");
		List<OrganizationRelationInfo> deptList=new ArrayList<OrganizationRelationInfo>();
	    for(OrganizationRelationInfo organizationRelationInfo:list){
	    	List<OrganizationRelationInfo> clist=new ArrayList<OrganizationRelationInfo>();
	    	clist.addAll(organizationRelationInfoDao
					.getOrganRelaInfoByParentOid("2", organizationRelationInfo.getId(),"1"));
	    	if(!clist.isEmpty()){
	    		for(int i=0;i<clist.size();i++){
	    			OrganizationRelationInfo o=clist.get(i);
	    			int size=0;
		    		size=organizationRelationInfoDao.judgeChildOrgRelaInfoByOrgRelaOid(o.getId());
		    		//判断是存在要显示的下级节点
		    		if(size>0){
		    			clist.addAll(organizationRelationInfoDao
		    					.getOrganRelaInfoByParentOid("2", o.getId(),"1"));
		    			clist.remove(o);
		    		}
    			}
	    		deptList.addAll(clist);
	    	}
	    }
	    if(deptList.isEmpty())
	    	deptList.addAll(list);
	    return deptList;
	}

	/**
	 * 查询用户所分配的权限区域
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	private Set<String> getPersonAuthorityRegion(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		//找出该用户角色下所有的区域权限
		List<AuthorityUserRole> userRoleList = authorityUserRoleDao.find("from AuthorityUserRole " +
				"where person='"+person.getId()+"'");
		Set<String> regionCodeSet = new HashSet<String>();
		for(AuthorityUserRole userRole : userRoleList){
			//权限区域
			String regionCodeStr=userRole.getRegionCode();
			if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
				for(String region:regionCodeStr.split(":")){
					regionCodeSet.add(region);
				}
			}
		}
		//移除中纪委
		if(regionCodeSet.contains("000000000000")){
			regionCodeSet.remove("000000000000");
		}
		/*Iterator<String> it=regionCodeSet.iterator();
		StringBuffer region=new StringBuffer("");
		while(it.hasNext()){
			if(region.length()>0)
				region.append(",");
			region.append("'"+it.next()+"'");
		}
		return region.toString();*/
		return regionCodeSet;
	}
	/**
	 * 查询用户所分配的权限区域
	 * 如果信访方式为空，则查询其所有角色的区域进行合并
	 * 如果信访方式不为空，则通过信访方式将区域权限进一步划分，主要用在各个受理模块：{
	 * 	例如：来信有北京的权限，来访有天津的权限，那么进入来信受理模块只能看见北京的区域。
	 * 	解决思路：通过数据权限，区域权限用户oid联合查询出可选区域
	 * }
	 * @return 可选区域
	 */
	@Transactional(readOnly=true)
	private Set<String> getPersonAuthorityRegionWithPetitionTypeCode(String petitionTypeCode){
		if(null==petitionTypeCode||"".equals(petitionTypeCode)){
			return getPersonAuthorityRegion();
		}else{
			//找出该用户角色下所有的区域权限
			List<Map<String,Object>> list;
			list = petitionOrganInfoDao.getAuthorityUserRoleByPetitionTypeCode(petitionTypeCode,true);
			if(!list.isEmpty()){
				Set<String> regionCodeSet = new HashSet<String>();
				for(Map<String,Object> map : list){
					//权限区域
					String regionCodeStr=(String) map.get("REGION_CODE");
					if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
						for(String region:regionCodeStr.split(":")){
							regionCodeSet.add(region);
						}
					}
				}
				//移除中纪委
				if(regionCodeSet.contains("000000000000")){
					regionCodeSet.remove("000000000000");
				}
				return regionCodeSet;
			}else
				return getPersonAuthorityRegion();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getSelfTranserDeptInfo() throws IOException{
		//获得操作人员
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		person = personInfoDao.get(person.getId());
		String parentOrgCode = person.getRegionCode().substring(0,6);
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.find(
				"FROM OrganizationRelationInfo WHERE organizationRelationInfo ='"+parentOrgCode+"' order by organOrder");
		//获得操作人员所在部门  目前只考虑用户只在一个部门下
		OrganizationInfo org = person.getOrganPersonRelationInfo()
				.iterator().next().getOrganizationInfo();
		StringBuffer bf = new StringBuffer("[");
		boolean flag = false;
		//遍历下级部门
		for(OrganizationRelationInfo cOrgRelaInfo : list) {
			OrganizationInfo cOrg = cOrgRelaInfo.getOrganizationInfo();
			if("1".equals(cOrg.getPurpose())){
				continue;
			}else if("2".equals(cOrg.getPurpose())&&!org.getId().equals(cOrg.getId()) && "1".equals(cOrg.getInvalidFlag())){
				if(flag){
					bf.append(",");
				}
				bf.append("{");
				bf.append("id:'");
				bf.append(cOrg.getId());
				bf.append("'");
				bf.append(",text:'");
				bf.append(cOrg.getOrgCname());
				bf.append("'");
				bf.append(",code:'");
				bf.append(cOrg.getOrgCode());
				bf.append("'");
				bf.append(",orgEname:'");
				bf.append(cOrg.getOrgEname());
				bf.append("',leaf:true}");
				flag = true;
			}
		}
		bf.append("]");
		return bf.toString();
	}

	/**
	 * @author wangxx
	 * 2010-12-06
	 * 调用哪些方法：
	 * 1、logonManager.getOnlinePersonInfo()
	 * 		获取当前登录系统用户信息
	 * 2、organizationRelationInfoDao.find()
	 * 		
	 * 3、organizationRelationInfoDao.getOrganRelaInfoByParentOid()
	 * 4、organizationRelationInfoDao.getOrgSize()
	 * 		判断是存在要显示的下级节点
	 * 被哪些方法调用：
	 * 1、getOrgShortCnameTreeByInvalidFlag()
	 * 		分级加载树形组件，显示中文简称，不带复选框，显示所有有效节点
	 * 2、getOrgShortCnameTreeForAccuserIssueAndWorkUnit()
	 * 		分级加载树形组件,用于前台页面中反映人的所属区域和单位或地址的下拉框
	 * 分级加载树形组件
	 * 此处还可以扩展，例如设定特定节点为根结点，只要添加一个参数，再①处判断该节点是否为空
	 * 	 1、如果为空就不以自定义的节点为根节点
	 *   2、如果不为空则自定义节点为根节点
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @param forAccerInfoFlag  是否用于反映人所属区域和单位地址的下拉框
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTree(String node,String invalidFlag,String forAccerInfoFlag){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "1";
		boolean flag = false;
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//①
			flag = true;
			//根节点
			//显示登录用户上一级机构
			String parentOrgCode = person.getRegionCode().replace("00", "");
			if(forAccerInfoFlag.equals("accuser")){
				parentOrgCode = "000000";
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo ='"+parentOrgCode+"'  order by organOrder");
			}else{
				if(parentOrgCode.length()==2){
					parentOrgCode = "000000";
				}else if(parentOrgCode.length()==4){
					parentOrgCode = parentOrgCode.substring(0,2)+"0000";
				}else if(parentOrgCode.length()==6){
					parentOrgCode = parentOrgCode.substring(0,4)+"00";
				}
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationInfo ='"+parentOrgCode+"'  order by organOrder");
			}
		}else {
//			if(isOrgan.equals("organ")){
//				//显示机构
//				orgPurpose = "1";
//			}else if(isOrgan.equals("dept")){
//				//显示部门
//				orgPurpose = "2";
//			}
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose,node,invalidFlag);
		}
		boolean firstItem = true;
		Object sci;
		//用于存放有下级节点的关系表oid
		Set<String> set = new HashSet<String>();
		//是否需要连表进行查询是否存在下级节点
		String[] orgRelaOid = new String[list.size()];
		if(!flag){
			//遍历获取组织关系表oid
			for(int i=0;i<list.size();i++){
				OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
				orgRelaOid[i]=ori.getId();
			}
			List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose, orgRelaOid, invalidFlag);
			for(int i=0;i<clist.size();i++){
				set.add(clist.get(i).getOrganizationRelationInfo().getId());
			}
		}
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
//			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
//			treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
//			treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
			treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
//			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
//			}else if(text.equals("orgShortCname")){
//				//显示中文简称
//				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
//			}
			treeJson.append(",editable:false");
			if(flag){
				//不连表
				int size = 0;
				//判断是存在要显示的下级节点
				size = organizationRelationInfoDao.getOrgSize(orgPurpose,((OrganizationRelationInfo) sci).getId(),invalidFlag);
				//判断是否含有子节点
				if(size>0){
					//存在下级节点
					treeJson.append(",leaf:false" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
				}
			}else{
				//连表
				//判断是否含有子节点
				if(set.contains(((OrganizationRelationInfo) sci).getId())){
					//存在下级节点
					treeJson.append(",leaf:false" );
//					treeJson.append(",iconCls:'org '" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
//					treeJson.append(",iconCls:'chart_organisation'" );
				}
			}
			if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
				//该节点身份为机构
//				treeJson.append(",iconCls:'chart_organisation'" );
				treeJson.append(",isType:'organ'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:true");
				treeJson.append(",isDelete:true");
				treeJson.append(",isMerge:true");
				treeJson.append(",isSplit:true");
			}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
				//该节点身份为部门
//				treeJson.append(",iconCls:'partverify'" );
				treeJson.append(",isType:'dept'");
				treeJson.append(",isAdd:false");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}else{
				//该节点身份为根节点
//				treeJson.append(",iconCls:'cog'" );
				treeJson.append(",isType:'system'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}
			treeJson.append(",expanded:false");
			//判断复选框属性
//			if(isChecked.equals(Constants.TRUE)){
//				//带复选框并且选中
//				treeJson.append(",checked:true");
//			}else if(isChecked.equals("false")){
				//带复选框但不选中
//				treeJson.append(",checked:false");
//			}
//			treeJson.append(",allowDelete:true"); 
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 获取当前用户有效角色的带有信访方式的问题属地权限
	 * @author xuyi
	 * @date 2014-01-23
	 * 调用哪些方法：
	 * 1、petitionOrganInfoDao.getDatabaseFieldInfoList()
	 * 		获取库表中信访方式和问题属地字段的Id值
	 * 2、petitionOrganInfoDao.getReadAuthorityList
	 * 		查询信访方式读取问题属地权限角色的Role_oid的集合
	 * 3、petitionOrganInfoDao.getAllRolesForOnlineUser()
	 * 		查询当前用户所具有的全部有效角色的Role_oid的集合
	 * 4、petitionOrganInfoDao.getAuthorityUserRoleId()
	 * 		查询当前用户所具有的有效角色所具有的问题属地权限code集合
	 * 被哪些方法调用：
	 * 1、getOrganTreeByRegionAuthority
	 * 		查询用户区域权限，返回用户拥有的区域关联信息
	 * @param petitionTypeCode
	 * @return 问题属地的set集合
	 */
	@Transactional(readOnly=true)
	private Set<String> getAllValidAuthorityRegionWithPetitionTypeCode(String petitionTypeCode){
		Set<String> roleOidSet = new HashSet<String>();
		Set<String> removeRoleOidSet = new HashSet<String>();
		List<Map<String,Object>> databaseFieldInfoList=petitionOrganInfoDao.getDatabaseFieldInfoList();
		if(databaseFieldInfoList.size() == 2){
			String PETITIONTYPECODEOID="";
			String ISSUEREGIONCODEOID="";
			for(Map<String,Object> databaseFieldInfo:databaseFieldInfoList){
				if(databaseFieldInfo.get("field_ename").toString().contains("PETITION_TYPE_CODE"))
					PETITIONTYPECODEOID = databaseFieldInfo.get("oid").toString().trim();
				if(databaseFieldInfo.get("field_ename").toString().contains("ISSUE_REGION"))
					ISSUEREGIONCODEOID = databaseFieldInfo.get("oid").toString().trim();
			}
			List<Map<String,Object>> roleList = petitionOrganInfoDao.getReadAuthorityList(PETITIONTYPECODEOID,ISSUEREGIONCODEOID);
			if(roleList.size()>0){
				for(Map<String,Object> authorityDataMap : roleList){
					boolean readRegionAuthority = Boolean.valueOf(authorityDataMap.get("selectAble2").toString().trim());
					boolean readPetitionTypeAuthority = Boolean.valueOf(authorityDataMap.get("selectAble1").toString().trim());
					String[] petitionTypeAuthorityValues = authorityDataMap.get("restrictValue").toString().trim().split(";");
					String roleOid = authorityDataMap.get("roleOid").toString().trim();
					if(readRegionAuthority && readPetitionTypeAuthority){
						boolean addRemoveRoleValue = true;
						for(String value : petitionTypeAuthorityValues){
							if(petitionTypeCode.equals(value)){
								roleOidSet.add(roleOid);
								addRemoveRoleValue = false;
								break;
							}
						}
						if(addRemoveRoleValue)
							removeRoleOidSet.add(roleOid);
					}else 
						roleOidSet.add(roleOid);
				}
			}
			if(roleOidSet.size()>0){
				List<Map<String,Object>> allRolesForOnlineUserSet = petitionOrganInfoDao.getAllRolesForOnlineUser();
				if(allRolesForOnlineUserSet.size()>0){
					for(Map<String,Object> userRoleObj: allRolesForOnlineUserSet){
						String userRole = userRoleObj.get("role_oid").toString().trim();
						if(removeRoleOidSet.size()>0){
							boolean addItem = true;
							for(String removeRoleOid:removeRoleOidSet){
								if(removeRoleOid.equals(userRole)){
									addItem = false;
									break;
								}
							}
							if(addItem)
								roleOidSet.add(userRole);
						}else
							roleOidSet.add(userRole);
					}
				}
				StringBuffer roleIdBuffer = new StringBuffer();
				boolean firstItem =true;
				for(String roleId : roleOidSet){
					if(!firstItem)
						roleIdBuffer.append(",");
					roleIdBuffer.append("'"+roleId+"'");
					firstItem = false;
				}
				if(roleIdBuffer.length()>0){
					List<Map<String,Object>> list = petitionOrganInfoDao.getAuthorityUserRoleId(roleIdBuffer.toString());
					if(list.size()>0){
						Set<String> regionCodeSet = new HashSet<String>();
						for(Map<String,Object> map : list){
							//权限区域
							String regionCodeStr=(String) map.get("REGION_CODE");
							if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
								for(String region:regionCodeStr.split(":")){
									regionCodeSet.add(region);
								}
							}
						}
						//移除中纪委
						if(regionCodeSet.contains("000000000000")){
							regionCodeSet.remove("000000000000");
						}
						return regionCodeSet;
					}else 
						return getPersonAuthorityRegion();
				}else 
					return getPersonAuthorityRegion();
			}else 
				return getPersonAuthorityRegion();
		}else
			return getPersonAuthorityRegion();
	}
}

