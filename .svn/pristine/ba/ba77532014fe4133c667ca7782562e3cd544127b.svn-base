package com.sinosoft.organization.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDeptRoleDao;
import com.sinosoft.authority.dao.AuthorityRoleDao;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.organization.dao.GroupInfoDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.GroupInfo;
import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
@Service
@Transactional
public class OrganizationRelationInfoManager extends EntityManager<OrganizationRelationInfo, String> {
	
	/**
	 * 注入机构关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	
	/**
	 * 注入机构信息dao层
	 */
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	//注入权限角色信息dao层
	@Autowired
	AuthorityRoleDao authorityRoleDao;
	//注入部门角色关联信息dao层
	@Autowired
	AuthorityDeptRoleDao authorityDeptRoleDao;	

	//注入组织机构信息业务层
	@Autowired
	OrganizationInfoManager organizationInfoManager;
	
	@Override
	protected HibernateDao<OrganizationRelationInfo, String> getEntityDao() {
		return organizationRelationInfoDao;
	}

	/**
	 * 注入机构分组信息dao层
	 */
	@Autowired
	GroupInfoDao groupInfoDao;
	/**
	 * 根据组织编码返回下级所有组织关系信息集合
	 * @date 2012-01-03
	 * @author Oba
	 * @param orgCode 组织编码
	 * @return 组织关系信息集合
	 */
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> getAllChildOrgRelaInfoList(final String orgCode){
		List<OrganizationRelationInfo> reList = new ArrayList<OrganizationRelationInfo>();
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrgRelaInfoByOrgCode(orgCode);
		for (OrganizationRelationInfo orgRela : list) {
			List<OrganizationRelationInfo> cList = getOrganizationRelationInfoByParentOrgRelaOid(orgRela.getId());
			for (OrganizationRelationInfo childOrgRela : cList) {
				reList.add(childOrgRela);
			}
		}
		return reList;
	}
	/**
	 * 根据组织编码返回下一级的组织关系信息集合
	 * @date 2012-01-03
	 * @author Oba
	 * @param orgCode 组织编码
	 * @return 组织关系信息集合
	 */
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> getChildOrgRelaInfoList(final String orgCode){
		List<OrganizationRelationInfo> reList = new ArrayList<OrganizationRelationInfo>();
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrgRelaInfoByOrgCode(orgCode);
		for (OrganizationRelationInfo orgRela : list) {
			//update by lijun 2014-5-27修改子部门的获取方式
			List<OrganizationRelationInfo> orgChildrens = organizationRelationInfoDao.getOrganizationRelationInfoByParentOrgOid(orgRela.getId());
			for (Iterator<OrganizationRelationInfo> iterator = orgChildrens.iterator(); iterator.hasNext();) {
				OrganizationRelationInfo organizationRelationInfo = iterator.next();
				reList.add(organizationRelationInfo);
			}
		}
		return reList;
	}
	/**
	 * 判断childOrgCode是否是parentOrgCode的下一级
	 * @date 2012-01-03
	 * @author Oba
	 * @param parentOrgCode 上级组织编码
	 * @param childOrgCode 下级组织编码
	 * @return true表示不是下级  false表示是下级
	 */
	@Transactional(readOnly=true)
	public boolean isChildOrg(final String parentOrgCode,final String childOrgCode){
		int size = organizationRelationInfoDao.getOrgRelaSizeByOrgCodeAndChildOrgCode(parentOrgCode, childOrgCode);
		if(size>0){
			return false;
		}
		return true;
	}
	/**
	 * 判断childOrgCode是否是parentOrgCode的下级
	 * @date 2012-01-03
	 * @author Oba
	 * @param parentOrgCode 上级组织编码
	 * @param childOrgCode 下级组织编码
	 * @return true表示不是是下级  false表示是下级
	 */
	@Transactional(readOnly=true)
	public boolean judgeRelation(final String parentOrgCode,final String childOrgCode){
		if(null==parentOrgCode||null==childOrgCode){
			logger.info("oba：参数不合法。");
			return true;
		}
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrgRelaInfoByOrgCode(childOrgCode);
		for(OrganizationRelationInfo orgRela : list){
			List<OrganizationRelationInfo> allHigherLevelList = getAllHigherLevelOrgRelationInfo(orgRela);
			for(OrganizationRelationInfo higherLevelOrgRela : allHigherLevelList){
				if(parentOrgCode.equals(higherLevelOrgRela.getOrganizationInfo().getOrgCode())){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 根据下级组织关系对象返回所有上级组织集合
	 * @date 2012-01-03
	 * @author Oba
	 * @param childOrgRelaInfo 下级组织关系对象
	 * @return 所有上级组织集合
	 */
	@Transactional(readOnly=true)
	private List<OrganizationRelationInfo> getAllHigherLevelOrgRelationInfo(final OrganizationRelationInfo childOrgRelaInfo){
		//定义返回的关系表List集合
		List<OrganizationRelationInfo> reList = new ArrayList<OrganizationRelationInfo>();
		//根据下级关系对象查询上一级的组织
		OrganizationRelationInfo parent = childOrgRelaInfo.getOrganizationRelationInfo();
		while (null!=parent) {
			//将上一级组织添加到集合中
			reList.add(parent);
			parent = parent.getOrganizationRelationInfo();
		}
		return reList;
	}
	/**
	 * 删除组织关系
	 * 该方法存在原因：在删除组织关系时由于级联删除出现问题，所以单独维护组织删除的关系
	 * @date 2011-06-20
	 * @author wangxx
	 * @param orgRelaOid 要删除的组织关系oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeOrganizationRelationInfo(String orgRelaOid){
		OrganizationRelationInfo ori = organizationRelationInfoDao.get(orgRelaOid);
		Set<OrganizationRelationInfo> coriSet = ori.getChildrens();
		Iterator<OrganizationRelationInfo> cit = coriSet.iterator();
		while (cit.hasNext()) {
			OrganizationRelationInfo cori = cit.next();
			removeOrganizationRelationInfo(cori.getId());
		}
		ori.getOrganizationInfo().getOrganizationRelationInfo().remove(ori);
		ori.setOrganizationInfo(null);
		organizationRelationInfoDao.delete(ori);
	}
	/**
	 * 在复制组织信息时判断复制的节点是否在新增下级组织节点的上级
	 * @date 2011-06-02
	 * @author wangxx
	 * @param organOid 机构基本信息主键,复制节点信息
	 * @param orgRelaOid 机构关系信息主键,新增下级组织的节点信息
	 * @return true：表示在上级，不能渲染，false表示关系正确，可以渲染数据
	 */
	@Transactional(readOnly=true)
	public String judgeParentOrgan(String organOid,String orgRelaOid){
		String flag = "false";
		OrganizationRelationInfo entity = organizationRelationInfoDao.get(orgRelaOid);
		while (null!=entity) {
			if (organOid.equals(entity.getOrganizationInfo().getId())) {
				return "true";
			}else{
				entity = entity.getOrganizationRelationInfo();
			}
		}
		return flag;
	}
	/**
	 * 加载普通列表数据，如果有下级就显示下级组织，如果没有下级就显示本机信息
	 * @author wangxx
	 * @createDate 2011-04-11
	 * 用途：
	 * 	1、该接口主要用于目录项目
	 * 步骤：通过orgRelaOid 判断是否有下级，如果有就显示下级信息
	 * 		如果没有就显示本机信息
	 * @return
	 */
	@Transactional(readOnly=true)
	public String organRelaPagedQuery(String filterTxt,String filterValue,int start,int limit){
		if(null==filterTxt||"".equals(filterTxt.trim())){
			StringBuffer sb = new StringBuffer("");
			int size = organizationRelationInfoDao.getOrgSize("",filterValue,"1");
			List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>();
			if(size>0){
				//有下级
				list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",filterValue,"1",start,limit,"orgCode");
				sb.append("{totalProperty:"+size+",result:[");
			}else{
				//没有下级
				OrganizationRelationInfo ori = organizationRelationInfoDao.get(filterValue);
				list.add(ori);
				sb.append("{totalProperty:1,result:[");
			}
			//遍历数据
			for (int i = 0; i < list.size(); i++) {
				OrganizationRelationInfo ori = list.get(i);
				if(i>0){
					sb.append(",");
				}
				sb.append("{");
				sb.append("id:'"+ori.getId()+"'");
				sb.append(",orgCname:'"+ori.getOrganizationInfo().getOrgCname()+"'");
				sb.append(",orgEname:'"+ori.getOrganizationInfo().getOrgEname()+"'");
				sb.append(",orgCode:'"+ori.getOrganizationInfo().getOrgCode()+"'");
				sb.append("}");
			}
			sb.append("]}");
			return sb.toString();
		}else{
			StringBuffer sb = new StringBuffer("");
			int size = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganizationSize(filterTxt, filterValue);
			sb.append("{totalProperty:"+size+",result:[");
			List<OrganizationRelationInfo> list =organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization(filterTxt, filterValue,start,limit,"orgCode");
			//遍历数据
			for (int i = 0; i < list.size(); i++) {
				OrganizationRelationInfo ori = list.get(i);
				if(i>0){
					sb.append(",");
				}
				sb.append("{");
				sb.append("id:'"+ori.getId()+"'");
				sb.append(",orgCname:'"+ori.getOrganizationInfo().getOrgCname()+"'");
				sb.append(",orgEname:'"+ori.getOrganizationInfo().getOrgEname()+"'");
				sb.append(",orgCode:'"+ori.getOrganizationInfo().getOrgCode()+"'");
				sb.append("}");
			}
			sb.append("]}");
			return sb.toString();
		}
	}
	
	/**
	 * wangxx
	 * 2011-12-2
	 * 分级加载属性combo：此方法添加了查询功能，可以针对组织中文名或者英文名进行查询
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
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTree(String node,String invalidFlag,String filterValue){
		List<OrganizationRelationInfo> list = null ;
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(null!=filterValue&&!"".equals(filterValue.trim())){
			Object sci;
			list = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization("%"+filterValue+"%");
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
				while(!"00000000000000000001".equals(pORI.getId())){
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
			treeJson.append("]");
		}else{
			if(node.equals("-1")){
				Object sci;
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationRelationInfo is null order by organOrder");
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
					sci = (OrganizationRelationInfo) list.get(i);
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
					treeJson.append(",iconCls:'cog'" );//显示图片
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
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
					sci = (OrganizationRelationInfo) list.get(i);
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
	 * @param node 该节点id 也就是组织关系oid
	 * @param invalidFlag  是否显示无效 “”为全部  1为显示有效   2为显示无效
	 * @param filterValue  查询所用的参数
	 * @param rootExpandFlag true时根节点自动展开
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganColumnsTree(String node,String invalidFlag,String filterValue,boolean changeFlag,boolean rootExpandFlag){
		List<OrganizationRelationInfo> list = null ;
		boolean firstItem = true;
		StringBuffer treeJson = new StringBuffer("[");
		if(null!=filterValue&&!"".equals(filterValue.trim())){
			Object sci;
			list = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization(filterValue);
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
				treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
				treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
				treeJson.append(",\"orgEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "\"");
				treeJson.append(",\"orgShortCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "\"");
				treeJson.append(",\"orgShortEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortEname()+ "\"");
				treeJson.append(",\"organOrder\":\"" + ((OrganizationRelationInfo) sci).getOrganOrder()+ "\"");
				if(null==((OrganizationRelationInfo) sci).getOrganizationRelationInfo()){
					treeJson.append(",\"parentOrgCname\":\"'");
				}else {
					treeJson.append(",\"parentOrgCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "\"");
				}
				treeJson.append(",\"invalidFlag\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "\"");
				treeJson.append(",\"orgCode\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "\"");
				treeJson.append(",\"createDate\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getCreateDate()+ "\"");
				treeJson.append(",\"organizationInfoId\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "\"");
				//判断是否含有子节点
				treeJson.append(",\"leaf\":true" );
				if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
					treeJson.append(",\"orgOrDept\":\"org\"" );//该节点的身份 为机构
					treeJson.append(",\"iconCls\":\"chart_organisation\"" );//显示图片
				}else if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("2")){
					treeJson.append(",\"orgOrDept\":\"dept\"" );//该节点的身份 为部门
					treeJson.append(",\"iconCls\":\"partverify\"" );//显示图片
				}
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			treeJson.append("]");
		}else{
			if(node.equals("-1")){
				Object sci;
				//初始化时查询系统定义表
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationRelationInfo is null order by organOrder");
				for(int i=0;i<list.size();i++){
					sci = (OrganizationRelationInfo) list.get(i);
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("\"id\":\"" + ((OrganizationRelationInfo) sci).getId()+ "\"");
					treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"code\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "\"");
					treeJson.append(",\"orgEname\":\"\"");
					treeJson.append(",\"orgShortCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+"\"");
					treeJson.append(",\"organOrder\":\"\"");
					treeJson.append(",\"parentOrgCname\":\"\"");
					treeJson.append(",\"invalidFlag\":\"\"");
					treeJson.append(",\"orgCode\":\"\"");
					treeJson.append(",\"createDate\":\"\"");
					int size = organizationRelationInfoDao.getOrgSize("",((OrganizationRelationInfo) sci).getId(),invalidFlag);
					//判断是否含有子节点
					if(size>0){
						treeJson.append(",\"leaf\":false" );
					}else{
						treeJson.append(",\"leaf\":true" );
					}
					if(rootExpandFlag){
						treeJson.append(",\"expanded\":true");
					}else{
						treeJson.append(",\"expanded\":false");
					}
					treeJson.append(",\"iconCls\":\"cog\"" );//显示图片
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
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
					treeJson.append(",\"text\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"orgEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgEname()+ "'");
					treeJson.append(",\"orgShortCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "\"");
					treeJson.append(",\"orgShortEname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortEname()+ "\"");
					treeJson.append(",\"organOrder\":\"" + ((OrganizationRelationInfo) sci).getOrganOrder()+ "\"");
					treeJson.append(",\"invalidFlag\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "\"");
					treeJson.append(",\"parentOrgCname\":\"" + ((OrganizationRelationInfo) sci).getOrganizationRelationInfo().getOrganizationInfo().getOrgCname()+ "\"");
					treeJson.append(",\"orgCode\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "\"");
					treeJson.append(",\"createDate\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getCreateDate()+ "\"");
					treeJson.append(",\"organizationInfoId\":\"" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "\"");
					//判断是否含有子节点
					if(set.contains(((OrganizationRelationInfo) sci).getId())){
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
	 * @param organOid 本级组织oid
	 * 用途：
	 * 	1、目录项目，如果有本机组织oid则以本级组织为根节点进行加载树
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTree(String node,String isChecked,String text,String isOrgan,String invalidFlag,String organOid){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String organPurpose = "";
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//①
			//根节点
			if(null==organOid||"".equals(organOid.trim())){
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo is null  order by organizationInfo.orgCode");
			}else{
				OrganizationInfo oi = organizationInfoDao.get(organOid);
				Set<OrganizationRelationInfo> set=oi.getOrganizationRelationInfo();
				OrganizationRelationInfo ori=set.iterator().next();
				list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",ori.getId(),invalidFlag,"orgCode");
			}
		}else {
			if(isOrgan.equals("organ")){
				//显示机构
				organPurpose = "1";
			}else if(isOrgan.equals("dept")){
				//显示部门
				organPurpose = "2";
			}
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(organPurpose,node,invalidFlag,"orgCode");
		}
		boolean firstItem = true;
		Object sci;
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
				treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
				
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
				treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
			}
			int size = 0;
			//判断是存在要显示的下级节点
			size = organizationRelationInfoDao.getOrgSize(organPurpose,((OrganizationRelationInfo) sci).getId(),invalidFlag);
			//判断是否含有子节点
			if(size>0){
				//存在下级节点
				treeJson.append(",leaf:false" );
			}else{
				//不存在下级节点
				treeJson.append(",leaf:true" );
			}
			if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
				//该节点身份为机构
				treeJson.append(",iconCls:'partverify'" );
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
				treeJson.append(",iconCls:'partverify'" );
				treeJson.append(",isType:'system'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}
			treeJson.append(",expanded:false");
			//判断复选框属性
			if(isChecked.equals("true")){
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
	 * @author wangxx
	 * 2010-12-06
	 * 分级加载树形组件
	 * 此处还可以扩展，例如设定特定节点为根结点，只要添加一个参数，再①处判断该节点是否为空
	 * 	 1、如果为空就不以自定义的节点为根节点
	 *   2、如果不为空则自定义节点为根节点
	 * 用途：
	 * 	1、目录系统，可以根据组织信息的字段进行排序。
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @param order  排序字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTreeOrderOrgCode(String node,String isChecked,String text,String isOrgan,String invalidFlag,String order){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "";
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//①
			//根节点
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo is null  order by organizationInfo.orgCode");
		}else {
			if(isOrgan.equals("organ")){
				//显示机构
				orgPurpose = "1";
			}else if(isOrgan.equals("dept")){
				//显示部门
				orgPurpose = "2";
			}
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose,node,invalidFlag,order);
		}
		boolean firstItem = true;
		Object sci;
		//用于存放有下级节点的关系表oid
		Set<String> set = new HashSet<String>();
		//是否需要连表进行查询是否存在下级节点
		String[] orgRelaOid = new String[list.size()];
		//遍历获取组织关系表oid
		for(int i=0;i<list.size();i++){
			OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
			orgRelaOid[i]=ori.getId();
		}
		List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose, orgRelaOid, invalidFlag);
		for(int i=0;i<clist.size();i++){
			set.add(clist.get(i).getOrganizationRelationInfo().getId());
		}
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
				treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
				
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
				treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
			}
			//判断是否含有子节点
			if(set.contains(((OrganizationRelationInfo) sci).getId())){
				//存在下级节点
				treeJson.append(",leaf:false" );
			}else{
				//不存在下级节点
				treeJson.append(",leaf:true" );
			}
			treeJson.append(",iconCls:'partverify'" );
			if(((OrganizationRelationInfo) sci).getOrganizationInfo().getPurpose().equals("1")){
				//该节点身份为机构
//				treeJson.append(",iconCls:'partverify'" );
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
//				treeJson.append(",iconCls:'partverify'" );
				treeJson.append(",isType:'system'");
				treeJson.append(",isAdd:true");
				treeJson.append(",isUpdate:false");
				treeJson.append(",isDelete:false");
				treeJson.append(",isMerge:false");
				treeJson.append(",isSplit:false");
			}
			treeJson.append(",expanded:false");
			//判断复选框属性
			if(isChecked.equals("true")){
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
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "";
		boolean flag = false;
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//①
			flag = true;
			//根节点
			//list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo is null  order by organOrder");
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo where organizationInfo.orgCode ='"+
					person.getRegionCode()+"' order by organizationInfo.orgCode");
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
			if(isChecked.equals("true")){
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
	 * @author sunzhe
	 * 2011-11-25
	 * 一次性加载树形组件，加载全部节点
	 * 此处还可以扩展，例如设定特定节点为根结点，只要添加一个参数，再①处判断该节点是否为空
	 * 	 1、如果为空就不以自定义的节点为根节点
	 *   2、如果不为空则自定义节点为根节点
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @param loadAll  是否需要加载全部节点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTreeAllNode(String node,String isChecked,String text,String isOrgan,String invalidFlag,String loadAll){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "";
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//①
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
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
				treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
				treeJson.append(",orgShortCname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",orgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
				treeJson.append(",invalidFlag:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getInvalidFlag()+ "'");
				treeJson.append(",orgShortCname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",orgCname:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",orgCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			}
			int size = 0;
			//判断是存在要显示的下级节点
			size = organizationRelationInfoDao.getOrgSize(orgPurpose,((OrganizationRelationInfo) sci).getId(),invalidFlag);
			//判断是否含有子节点
			if(size>0){
				//存在下级节点
				treeJson.append(",leaf:false" );
				//判断是否需要一次将全部树节点加载过来
				if(loadAll!=null&&loadAll.equals("true")){
					treeJson.append(",expanded:true,allowChildren:true,children:");
					treeJson.append(getOrganTreeAllNode(((OrganizationRelationInfo)sci).getId(),isChecked,text,isOrgan,invalidFlag,loadAll));
				}
			}else{
				//不存在下级节点
				treeJson.append(",leaf:true" );
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
			if(isChecked.equals("true")){
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
	 * @author wangxx
	 * 2010-12-06
	 * 分级加载树形组件，只显示节点参数的下级节点
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @param nodeId  节点参数
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getOrganTreeByNodeId(String node,String isChecked,String text,String isOrgan,String invalidFlag,String nodeId){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "";//显示节点身份的参数
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//根节点
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid("",nodeId,invalidFlag);
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
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			}
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
			if(isChecked.equals("true")){
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
	 * @author wangxx
	 * 2010-12-13
	 * 分级加载树形组件，不显示节点参数节点及其下级节点
	 * @param checked 复选框设置参数  true：带复选框并且勾选  false：带复选框但不勾选   “”：不带复选框
	 * @param text  显示中文名还是中文简称的参数  orgCname：中文名   orgShortCname：中文简称
	 * @param nodeOid 该节点id 也就是组织关系oid
	 * @param isOrgan  显示内容参数  all:展示全部  organ:只展示根节点和机构  dept：只展示根节点和部门
	 * @param invalidFlag  是否显示无效
	 * @param nodeId  节点参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganTreeNeedNotNodeId(String node,String isChecked,String text
			,String isOrgan,String invalidFlag,final String nodeId){
		StringBuffer bf = new StringBuffer("");
		List<OrganizationRelationInfo> orgRelalist = getOrganizationRelationInfoByParentOrgRelaOid(nodeId);
		bf.append(organizationRelationInfoDao.get(nodeId).getOrganizationInfo().getId());
		for (int i = 0; i < orgRelalist.size(); i++) {
			bf.append(";");
			bf.append(orgRelalist.get(i).getOrganizationInfo().getId());
		}
		String[] nodeIds = bf.toString().split(";");
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = null ;
		//此处判断是否为根节点 如果不是就需要判断显示的内容
		String orgPurpose = "";
		//如果不是根节点则使用优化方法
		boolean flag = true;
		if(node==null||node.equals("root")||node.equals("-1")||(node.split("-")[0].equals("xnode"))){
			//根节点
			flag = false;
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo is null  order by organOrder");
		}else {
			if(isOrgan.equals("organ")){
				//显示机构
				orgPurpose = "1";
			}else if(isOrgan.equals("dept")){
				//显示部门
				orgPurpose = "2";
			}
			list = organizationRelationInfoDao.getOrganRelaInfoByParentOid(orgPurpose,node,invalidFlag,nodeIds);
		}
		boolean firstItem = true;
		//用于存放有下级节点的关系表oid
		Set<String> set = new HashSet<String>();
		//是否需要连表进行查询是否存在下级节点
		String[] orgRelaOid = new String[list.size()];
		if(flag){
			//遍历获取组织关系表oid
			for(int i=0;i<list.size();i++){
				OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
				orgRelaOid[i]=ori.getId();
			}
			List<OrganizationRelationInfo> clist = organizationRelationInfoDao
					.getOrgRelaInfo(orgPurpose,orgRelaOid,invalidFlag,nodeIds);
			for(int i=0;i<clist.size();i++){
				set.add(clist.get(i).getOrganizationRelationInfo().getId());
			}
		}
		Object sci;
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo) list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			//判断显示中文名还是中文简称
			if(text.equals("orgCname")){
				//显示中文名
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			}else if(text.equals("orgShortCname")){
				//显示中文简称
				treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
				treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
				treeJson.append(",organType:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrganType()+ "'");
			}
			//判断是存在要显示的下级节点
			if(flag){
				if(set.contains(((OrganizationRelationInfo) sci).getId())){
					//存在下级节点
					treeJson.append(",leaf:false" );
				}else{
					//不存在下级节点
					treeJson.append(",leaf:true" );
				}
			}else{
				int size = 0;
				size = organizationRelationInfoDao.getOrgSize(orgPurpose,((OrganizationRelationInfo) sci).getId(),invalidFlag,nodeIds);
				//判断是否含有子节点
				if(size>0){
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
			if(isChecked.equals("true")){
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
	 * @author wangxx
	 * 2010-12-03
	 * 通过上级组织关系oid和本级组织oid查询在关系表中是否已经存在这样的关系如果存在返回true，如果不存在返回false
	 * 用途：
	 *  1、在新增关联组织时，判断是否已经关联
	 *   ①返回true为已存在，不能新增关联
	 *   ②返回false为不村子，可以关联
	 * @param organOid  机构信息主键
	 * @param parentOrgRelaOid 机构父节点
	 */
	@Transactional(readOnly=true)
	public String judgeOrganRepeat(String organOid,String parentOrgRelaOid) {
		// 方法返回值
		String returnString = "true";
		int result = 1;
		result = organizationRelationInfoDao.judgeOrganRepeat( organOid,parentOrgRelaOid);
		// 如果result结果为0，则返回"false"
		if (result == 0) {
			returnString = "false";
		}
		return returnString;
	}

	/**
	 * @author wangxx
	 * 2010-12-03
	 * 通过组织中文名，上级组织关系oid，本级组织oid判断，上级组织下是否已经存在该中文名
	 * 用途：
	 *  1、在修改组织修改时，判断该组织下是否已经存在该中文名的组织
	 *    ①返回true时，表示已经存在
	 *    ②返回false时，表示不存在
	 * @param organOid  机构信息主键
	 * @param parentOrgRelaOid 机构父节点
	 */
	@Transactional(readOnly=true)
	public String judgeOrganEditCNameRepeat(String orgCname,String parentOrgRelaOid,String orgOid) {
		int i = organizationRelationInfoDao.judgeOrganCNameAndOidRepeat(orgCname, orgOid);
		String returnString = "true";
		//如果i>0表明没有修改中文名
		if(i>0){
			return "false";
		}else{
			// 方法返回值
			int result = 1;
			result = organizationRelationInfoDao.judgeOrganCNameRepeat(orgCname,organizationRelationInfoDao.get(parentOrgRelaOid).getOrganizationRelationInfo().getId());
			// 如果result结果为0，则返回"false"
			if (result == 0) {
				returnString = "false";
			}
		}
		return returnString;
	}

	/**
	 * 组织调度
	 * 用途：专用于平煤项目，他们没有多个上级，所以不需要过于复杂的操作
	 * @date 2011-07-25
	 * @author wangxx
	 * @param pOid 父机构关系表oid
	 * @param oid 关系表oid
	 * @return true表示成功
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String changeDeptByPM(String pOid ,String oid){
		OrganizationRelationInfo pOrganRelationInfo = organizationRelationInfoDao.get(pOid);
		OrganizationRelationInfo organRelationInfo = organizationRelationInfoDao.get(oid);
		organRelationInfo.setOrganizationRelationInfo(pOrganRelationInfo);
		organizationRelationInfoDao.save(organRelationInfo);
		return "true";
	}
	/**
	 * 组织调度
	 * @param pOid 父机构关系表oid
	 * @param oid 关系表oid
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String changeDept(String pOid ,String oid){
		//需要判断上级组织是否已经在被调动的部门下级
		List<OrganizationRelationInfo> list = getOrganizationRelationInfoByParentOrgRelaOid(oid);
		OrganizationRelationInfo pOrganRelationInfo = organizationRelationInfoDao.get(pOid);
		while(pOrganRelationInfo!=null){
			for (int i = 0; i < list.size(); i++) {
				if(pOrganRelationInfo.getOrganizationInfo().getId().equals(list.get(i).getOrganizationInfo().getId())){
					//上级组织是否已经在被调动的部门下级
					return "false";
				}
			}
			pOrganRelationInfo = pOrganRelationInfo.getOrganizationRelationInfo();
		}

		OrganizationRelationInfo pOrganizationRelationInfo = organizationRelationInfoDao.get(pOid);
		OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(oid);
		Set<OrganizationRelationInfo> set = pOrganizationRelationInfo.getOrganizationInfo().getOrganizationRelationInfo();
		Iterator<OrganizationRelationInfo> it = set.iterator();
		while(it.hasNext()){
			pOrganizationRelationInfo = it.next();
			OrganizationRelationInfo ori = new OrganizationRelationInfo();
			ori.setOrganizationInfo(organizationRelationInfo.getOrganizationInfo());
			ori.setOrganizationRelationInfo(pOrganizationRelationInfo);
			ori.setOrganOrder(organizationRelationInfo.getOrganOrder());
			ori.setRelation(organizationRelationInfo.getRelation());
			organizationRelationInfoDao.save(ori);
			organizationInfoManager.saveOrganizationInfo(organizationRelationInfo.getOrganizationInfo(), ori);
		}
		organizationInfoManager.deleteOrganizationInfo(organizationRelationInfo.getId());
		return "true";
	}
	/**
	 * 通过部门oid查找关系表对象，但只取一个，因为所有部门的下级都是同一套，不可能出现不同项
	 * @param organOid
	 * @return 
	 */
	@Transactional(readOnly=true)
	public OrganizationRelationInfo getOrganRelaByOrganOid(String organOid){
		// 封装查询参数，查询人信息记录Oid
		Criterion criterion = Restrictions.eq("organizationInfo.id", organOid);
		// 根据查询条件，查询
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.find(criterion);
		//判断是否取出来值，如果有值就进行选取
		if(list.size()>0){
			//获取机构对象
			OrganizationRelationInfo returnOrganizationRelationInfo=list.get(0);
			//返回机构对象
			return returnOrganizationRelationInfo;
		}
		//如果没有获取到机构对象，则返回null
		return null;
	}
	/**
	 * 判断该部门是否有下级部门
	 * @param organOid  机构关系oid
	 * @return int   查询记录数
	 * @author wangxx
	 * June 30 2010
	 */
	@Transactional(readOnly=true)
	public String judgeChildOrgRelaInfoByOrgOid(String organRelaOid) {
		OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(organRelaOid);
		int i = organizationRelationInfoDao.judgeChildOrgRelaInfoByOrgOid(organizationRelationInfo.getOrganizationInfo().getId());
		String flag = "false";
		if(i>0){
			flag = "true";
		}
		return flag;
	}

	/**
	 * 判断该节点部门是否有下级部门
	 * @param organRelaOid  机构关系信息主键
	 * @return int   查询记录数
	 * @author wangxx
	 * June 30 2010
	 */
	@Transactional(readOnly=true)
	public String judgeChildOrgRelaInfoByOrgRelaOid(String organRelaOid) {
		int i = organizationRelationInfoDao.judgeChildOrgRelaInfoByOrgRelaOid(organRelaOid);
		String flag = "false";
		if(i>0){
			flag = "true";
		}
		return flag;
	}
	/**
	 * 通过多个机构关系表oid拼接他们的中文名
	 * @param organRelaOids
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getOrganCnames(String organRelaOids){
		String organCnames = "";
		organRelaOids=organRelaOids.substring(0,organRelaOids.length()-1);
		String[] organRelaOid = organRelaOids.split(";");
		for (int i = 0; i < organRelaOid.length; i++) {
			OrganizationRelationInfo nOrganizationRelationInfo = organizationRelationInfoDao.get(organRelaOid[i]);
			if(nOrganizationRelationInfo!=null){
				organCnames+=nOrganizationRelationInfo.getOrganizationInfo().getOrgCname()+";";
			}
			
		}
		return organCnames;
	}
	/**
	 * 通过机构关系表oid拼接他的中文名
	 * @param organRelaOids
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getOrganCname(String organRelaOid){
		OrganizationRelationInfo nOrganizationRelationInfo = organizationRelationInfoDao.get(organRelaOid);
		if(nOrganizationRelationInfo==null){
			return "";
		}else{
			String organCnames = nOrganizationRelationInfo.getOrganizationInfo().getOrgCname();
			return organCnames;
		}
	}
	/**
	 * 分级加载树
	 * @date 2010-07-12
	 * @author wangxx
	 * 1、在组织分组修改时渲染左侧树
	 * @param id 树节点oid
	 * @param checkboxFlag 是否带复选框
	 * @param groupInfoOid 分组oid
	 * @param nameFlag 名称标示，如果是orgCname中文名，orgShaotCname中文简称
	 * @return json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String buildGroupDeptTree(String id,boolean checkboxFlag,String groupInfoOid,String nameFlag){
		GroupInfo groupInfo = null;
		//用于存储上级组织关系对象
		Set<OrganizationRelationInfo> pset = new HashSet();
		//用于存储本级关系对象
		Set<OrganizationRelationInfo> set = new HashSet();
		if(groupInfoOid!=null&&!groupInfoOid.equals("")){
			//得到分组对象
			groupInfo = groupInfoDao.get(groupInfoOid);
			Set<GroupItemInfo> groupItemInfoSet = groupInfo.getGroupItemInfo();
			Iterator<GroupItemInfo> it = groupItemInfoSet.iterator();
			//遍历分组关系对象，从中获取组织关系对象，并封装到set集合中
			while (it.hasNext()) {
				GroupItemInfo groupItemInfo = (GroupItemInfo) it.next();
				try {
					if(groupItemInfo.getObjType().equals("organ")){
						if(organizationRelationInfoDao.get(groupItemInfo.getObjOid())!=null){
							OrganizationRelationInfo o = organizationRelationInfoDao.get(groupItemInfo.getObjOid());
							set.add(o);
						}
					}
				} catch (ObjectNotFoundException e) {
					logger.info("分组对象信息中有错误数据objOid："+groupItemInfo.getObjOid());
					continue;
				}
			}
		}
		Iterator<OrganizationRelationInfo> orgIt = set.iterator();
		while (orgIt.hasNext()) {
			OrganizationRelationInfo organizationRelationInfo = (OrganizationRelationInfo) orgIt
					.next();
			while(null!=organizationRelationInfo.getOrganizationRelationInfo()){
				//如果存在证明已经有该组织机构的上级组织管信息，所以可以返回进行下一次循环
				if(pset.contains(organizationRelationInfo.getOrganizationRelationInfo())){
					break;
				}
				pset.add(organizationRelationInfo.getOrganizationRelationInfo());
				organizationRelationInfo = organizationRelationInfo.getOrganizationRelationInfo();
			}
		}
		String json = getGroupItemTree(id, checkboxFlag, nameFlag, pset, set);
		return json;
	}
	/**
	 * 通过分组信息加载树
	 * @date 2011-07-25
	 * @author wangxx
	 * 用途：
	 * 	1、配合buildGroupDeptTree方法使用，主要是通过已知的分组信息递归遍历生成树形json数据
	 * @param id 树节点id
	 * @param checkboxFlag 是否存在复选框，为true存在，为false不存在
	 * @param nameFlag 名称标示，如果是orgCname中文名，orgShaotCname中文简称
	 * @param pset 父级集合
	 * @param set 分组对象集合
	 * @return 树形json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	private String getGroupItemTree(String id,boolean checkboxFlag,String nameFlag
			,Set<OrganizationRelationInfo> pset,Set<OrganizationRelationInfo> set){
		StringBuffer treeJson = new StringBuffer("[");
		List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>();
		if(id==null||id.equals("root")||id.equals("-1")||(id.split("-")[0].equals("xnode"))){
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE organizationRelationInfo is null  order by organizationInfo.id");
		}else {
			list =  organizationRelationInfoDao.getOrganRelaInfoByParentOid("",id,"1");
		}
		//用于存放有下级节点的关系表oid
		Set<String> rSet = new HashSet<String>();
		String[] orgRelaOid = new String[list.size()];
		//遍历获取组织关系表oid
		for(int i=0;i<list.size();i++){
			OrganizationRelationInfo ori = (OrganizationRelationInfo) list.get(i);
			orgRelaOid[i]=ori.getId();
		}
		List<OrganizationRelationInfo> clist = organizationRelationInfoDao.getOrganRelaInfoByParentOid("", orgRelaOid, "1");
		for(int i=0;i<clist.size();i++){
			rSet.add(clist.get(i).getOrganizationRelationInfo().getId());
		}
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			OrganizationRelationInfo ori = list.get(i);
			if(flag){
				treeJson.append(",");
			}
			treeJson.append("{");
			treeJson.append("id:'" + ori.getId()+ "'");
			if("orgCname".equals(nameFlag)){
				treeJson.append(",text:'" + ori.getOrganizationInfo().getOrgCname()+ "'");
			}else if("orgShortCname".equals(nameFlag)){
				treeJson.append(",text:'" + ori.getOrganizationInfo().getOrgShortCname()+ "'");
			}
			treeJson.append(",organizationInfoId:'" + ori.getOrganizationInfo().getId() + "'");
			//判断是否含有子节点
			if(rSet.contains(ori.getId())){
				treeJson.append(",leaf:false" );
			}else{
				treeJson.append(",leaf:true" );
			}
			if(ori.getOrganizationInfo().getPurpose().equals("1")){
				treeJson.append(",iconCls:'chart_organisation'" );
			}else if(ori.getOrganizationInfo().getPurpose().equals("2")){
				treeJson.append(",iconCls:'partverify'" );
			}else{
				treeJson.append(",iconCls:'cog'" );
			}
			//判断是否勾选了该机构
			if(checkboxFlag&&set.contains(ori)){
				treeJson.append(",checked:true");
			}else if(checkboxFlag){
				treeJson.append(",checked:false");
			}
			if(pset.contains(ori)){
				treeJson.append(",expanded:true,allowChildren:true,children:");
				String json = getGroupItemTree(ori.getId(), checkboxFlag, nameFlag, pset, set);
				treeJson.append(json);
			}else{
				treeJson.append(",expanded:false");
			}
			treeJson.append("}");
			flag = true;
		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 分级加载树 用户所属部门为根节点
	 * wangxx
	 * 2010-07-12
	 * 用于机构用户组件   显示中文简称
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String buildUserDeptTreeByPersonOrgOid(String node,String id,boolean flag){
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrganizationRelationInfoByOrganizationInfoOid(id);
		if(!"root".equals(node)){
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE  organizationRelationInfo.id='"+node+"' order by organizationInfo.id");
		}
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		Object sci;
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo)list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
			treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgShortCname()+ "'");
			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
			treeJson.append(",organizationCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
			//判断是否含有子节点
			if(size>0){
				treeJson.append(",leaf:false" );
			}else{
				treeJson.append(",leaf:true" );
			}
			treeJson.append(",expanded:false");
			//如果flag为true，带有复选框
			if(flag){
				treeJson.append(",checked:false");
			}
			treeJson.append(",allowDelete:true"); 
			treeJson.append(",organOrDept:'dept'"); 
			treeJson.append(",iconCls:'partverify'" );
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 分级加载树 用户所属部门为根节点
	 * wangxx
	 * 2010-07-12
	 * 用于机构用户组件   显示中文名
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String buildUserDeptTreeByPersonOrgOidLongName(String node,String id,boolean flag){
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrganizationRelationInfoByOrganizationInfoOid(id);
		if(node.equals("root")){
		}else {
			list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE  organizationRelationInfo.id='"+node+"' order by organizationInfo.id");
		}
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		Object sci;
		for(int i=0;i<list.size();i++){
			sci = (OrganizationRelationInfo)list.get(i);
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			treeJson.append("id:'" + ((OrganizationRelationInfo) sci).getId()+ "'");
			treeJson.append(",text:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCname()+ "'");
			treeJson.append(",organizationInfoId:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getId()+ "'");
			treeJson.append(",organizationCode:'" + ((OrganizationRelationInfo) sci).getOrganizationInfo().getOrgCode()+ "'");
			int size=organizationRelationInfoDao.buildMergeDeptTree(((OrganizationRelationInfo) sci).getId());
			//判断是否含有子节点
			if(size>0){
				treeJson.append(",leaf:false" );
			}else{
				treeJson.append(",leaf:true" );
			}
			treeJson.append(",expanded:false");
			//如果flag为true，带有复选框
			if(flag){
				treeJson.append(",checked:false");
			}
			treeJson.append(",allowDelete:true"); 
			treeJson.append(",organOrDept:'dept'"); 
			treeJson.append(",iconCls:'partverify'" );
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 验证添加组织时判断上级组织下是否已经存在该中文名
	 * @param orgCname  需要验证的中文名
	 * @param parentOrgRelaId  上级关系表oid
	 * @return
	 */
	@Transactional(readOnly=true)
	public String judgeOrganCNameRepeat(String orgCname, String parentOrgRelaId) {
		String returnString = "true";
		int result = 1;
		result = this.organizationRelationInfoDao.judgeOrganCNameRepeat(orgCname, parentOrgRelaId);
		if (result == 0){
			returnString = "false";
		}
		return returnString;
	}
	/**
	 * 保存部门信息   添加了角色信息
	 * wangxx
	 * @param o
	 * @param roleName
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganization(OrganizationInfo oi,OrganizationRelationInfo o , String roleId){
		organizationInfoDao.save(oi);
		o.setOrganizationInfo(oi);
		organizationRelationInfoDao.save(o);
		if(roleId==null||roleId.equals("")){
		}else{
			AuthorityDeptRole adr = new AuthorityDeptRole();
			adr.setRole(authorityRoleDao.get(roleId));
			adr.setOrganization(oi.getId());
			authorityDeptRoleDao.save(adr);
		}
	}
	/**
	 * 保存部门信息   修改了角色信息
	 * wangxx
	 * @param o
	 * @param roleName
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveEditOrganization(OrganizationInfo oi,OrganizationRelationInfo o , String roleId){
		organizationInfoDao.save(oi);
		o.setOrganizationInfo(oi);
		organizationRelationInfoDao.save(o);
		if(roleId==null||roleId.equals("")){
		}else{
			AuthorityDeptRole adr = authorityDeptRoleDao.findByUnique("organization.id", oi.getId());
			if(adr==null){
				adr = new AuthorityDeptRole();
			}
			adr.setRole(authorityRoleDao.get(roleId));
			adr.setOrganization(oi.getId());
			authorityDeptRoleDao.save(adr);
		}
	}
	/**
	 * 通过关系表父节点oid返回其下级所有的关系对象集合 不包含父节点本身
	 * @param orgRelaOid  //关系表oid  getOrganizationRelationInfoByParentOid
	 * wangxx
	 * 2010-11-25
	 */
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> getOrganizationRelationInfoByParentOrgRelaOid(String orgRelaOid){
		//定义返回的关系表List集合
		List<OrganizationRelationInfo> reList = new ArrayList<OrganizationRelationInfo>();
		//查询其子组织
		List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrgRelaInfoListByParentOrgRelaOid(orgRelaOid);
		//遍历气子组织
		for (int i = 0; i < list.size(); i++) {
			//递归调用
			List<OrganizationRelationInfo> lista = getOrganizationRelationInfoByParentOrgRelaOid(list.get(i).getId());
			//将子组织的子组织添加到返回集合中
			for (int j = 0; j < lista.size(); j++) {
				reList.add(lista.get(j));
			}
			//将子组织添加到返回集合中
			reList.add(list.get(i));
		}
		return reList;
	}
	
	/**
	 * 查询本级及下一级区域信息，不带复选框
	 * @author hxh
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrgTree() {
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		List<OrganizationRelationInfo> list=organizationRelationInfoDao.find("FROM OrganizationRelationInfo o where " +
				"o.organizationRelationInfo.id='000000' and o.organizationInfo.purpose='1' order by o.organizationInfo.orgCode");
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				OrganizationRelationInfo organizationRelationInfo = (OrganizationRelationInfo) list.get(i);
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("\"code\":\""+ organizationRelationInfo.getOrganizationInfo().getOrgCode()+"\",");
				treeJson.append("\"name\":\""+ organizationRelationInfo.getOrganizationInfo().getOrgCname()+"\"");
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
}
