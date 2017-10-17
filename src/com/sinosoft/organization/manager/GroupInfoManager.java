package com.sinosoft.organization.manager;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.dao.GroupInfoDao;
import com.sinosoft.organization.dao.GroupItemInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.GroupInfo;
import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**
 * 机构分组信息Manager层
 * @author wangxx
 */
@Service
@Transactional
public class GroupInfoManager extends EntityManager<GroupInfo, String>{
	
	/**
	 * 注入组织分组信息dao层
	 */
	@Autowired
	GroupInfoDao groupInfoDao;
	
	/**
	 * 注入组织分组关系信息dao层
	 */
	@Autowired
	GroupItemInfoDao groupItemInfoDao;
	
	/**
	 * 注入组织关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	

	//注入机构关系业务层
	@Autowired
	OrganizationRelationInfoManager organizationRelationInfoManager;
	
	@Override
	protected HibernateDao<GroupInfo, String> getEntityDao() {
		return groupInfoDao;
	}

	/**
	 * 新增机构分组信息
	 * @param groupCode 分组编码
	 * @param groupName 分组名称
	 * @param deptOids 部门oid
	 * @param personOids 用户oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertOrganGroup(GroupInfo groupInfo,String deptOids,String personOids){
		//保存分组信息
		groupInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
		groupInfoDao.save(groupInfo);
		//保存分组和部门的关系信息
		String[] deptOid = deptOids.split(";");
		for (int i = 0; i < deptOid.length; i++) {
			GroupItemInfo groupItemInfo = new GroupItemInfo();
			groupItemInfo.setGroupInfo(groupInfo);
			groupItemInfo.setObjType("organ");
			groupItemInfo.setObjOid(deptOid[i]);
			groupItemInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
			groupItemInfo.setOperator(groupInfo.getOperator());
			groupItemInfo.setOperatorName(groupInfo.getOperatorName());
			if(deptOid[i].equals("")){
			}else{
				groupItemInfoDao.save(groupItemInfo);
			}
		}
		//保存分组和人员的关系信息
		String[] personOid = personOids.split(";");
		for (int i = 0; i < personOid.length; i++) {
			GroupItemInfo groupItemInfo = new GroupItemInfo();
			groupItemInfo.setGroupInfo(groupInfo);
			groupItemInfo.setObjType("person");
			groupItemInfo.setObjOid(personOid[i]);
			groupItemInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
			groupItemInfo.setOperator(groupInfo.getOperator());
			groupItemInfo.setOperatorName(groupInfo.getOperatorName());
			if(personOid[i].equals("")){
			}else{
				groupItemInfoDao.save(groupItemInfo);
			}
		}
	}

	/**
	 * 保存分组信息
	 * @param groupCode 分组编码
	 * @param groupName 分组名称
	 * @param deptOids 部门oid
	 * @param personOids 用户oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganGroup(GroupInfo groupInfo,String deptOids,String personOids,String groupInfoOid){
		//保存分组信息
		groupInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
		groupInfoDao.save(groupInfo);
		
		//保存分组和部门的关系信息
		String[] deptOid = deptOids.split(";");
		for (int i = 0; i < deptOid.length; i++) {
			GroupItemInfo groupItemInfo = new GroupItemInfo();
			groupItemInfo.setGroupInfo(groupInfo);
			groupItemInfo.setObjType("organ");
			groupItemInfo.setObjOid(deptOid[i]);
			groupItemInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
			groupItemInfo.setOperator(groupInfo.getOperator());
			groupItemInfo.setOperatorName(groupInfo.getOperatorName());
			if(deptOid[i].equals("")){
			}else{
				groupItemInfoDao.save(groupItemInfo);
			}
		}
		//保存分组和人员的关系信息
		String[] personOid = personOids.split(";");
		for (int i = 0; i < personOid.length; i++) {
			GroupItemInfo groupItemInfo = new GroupItemInfo();
			groupItemInfo.setGroupInfo(groupInfo);
			groupItemInfo.setObjType("person");
			groupItemInfo.setObjOid(personOid[i]);
			groupItemInfo.setOperateDate(new Timestamp(System.currentTimeMillis()));
			groupItemInfo.setOperator(groupInfo.getOperator());
			groupItemInfo.setOperatorName(groupInfo.getOperatorName());
			groupItemInfoDao.save(groupItemInfo);
		}
		groupInfoDao.delete(groupInfoOid);
	}
	
	/**
	 * 删除分组信息
	 * 说明：删除分组信息的同时会删除其在分组关联信息中的对应的数据
	 * @param groupOids 组织分组oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOrganGroup(String groupOids){
		String[] groupOid = groupOids.split(";");
		for (int i = 0; i < groupOid.length; i++) {
			groupInfoDao.delete(groupOid[i]);
		}
	}
	/**
	 * 根据用户对象查找所在分组关联信息集合
	 * 用途：
	 * 	1、用于给权限提供的接口
	 * @param personInfo 用户对象
	 * @return 分组信息对象集合
	 */
	@Transactional(readOnly=true)
	public List<GroupInfo> getGroupInfoByPersonInfo(PersonInfo personInfo){
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		List<GroupItemInfo> groupItemInfoList = groupItemInfoDao.getGroupItemInfoByPersonOid(personInfo.getId());
		for (int i = 0; i < groupItemInfoList.size(); i++) {
			GroupItemInfo nGroupItemInfo = groupItemInfoList.get(i);
			list.add(nGroupItemInfo.getGroupInfo());
		}
		return list;
	}


	/**
	 * 通过机构分组编码信息和该节点的信息（关系表的oid）加载树形数据
	 * @date 2010-11-05
	 * @author wangxx
	 * 用途：
	 * 	1、组织分组的修改页面左侧菜单（根据平煤项目组提供：显示中文简称）
	 * 实现：需要判断是否是第一加载
	 * 	1、如果是第一次加载就需要将用户保存的组织分组信息和组织树信息相结合，
	 * 	  如果一个节点已经被用户选择，那在初始化的时候就会将其下级展开，并复选框为勾选
	 *  2、如果不是第一次加载就需要获取点击节点的信息（组织关系表的oid）通过该节点信息加载
	 *    组织树信息
	 * @param id 树节点id
	 * @param txt 分组信息属性名
	 * @param val 分组信息属性值
	 * @param flag 是否带复选框，如果为true待复选框并且不勾选
	 * @return tree的json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganRelationInfosByGroup(String id,String txt,String val,boolean flag){
		if(null!=val&&!"".equals(val.trim())){
			StringBuffer jsonTree =new StringBuffer("[");
			if(id==null||id.equals("root")||id.equals("-1")||(id.split("-")[0].equals("xnode"))){
				//第一次加载数据
				List<GroupItemInfo> list = groupItemInfoDao.getGroupItemByGroupCode(val);
				Set<OrganizationRelationInfo> pList = new HashSet<OrganizationRelationInfo>();
				for (int i = 0; i < list.size(); i++) {
					GroupItemInfo groupItemInfo = list.get(i);
					OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(groupItemInfo.getObjOid()).getOrganizationRelationInfo();
					for (int j = 0; j < list.size(); j++) {
						String  groupItemInfoOid = list.get(j).getObjOid();
						if(organizationRelationInfo.getId().equals(groupItemInfoOid)){
							break;
						}else if(j==list.size()-1){
							pList.add(organizationRelationInfo);
						}
					}
				}
				//开始遍历已经存储的“根节点”
				Iterator<OrganizationRelationInfo> it = pList.iterator();
				int m = 0;
				while(it.hasNext()){
					OrganizationRelationInfo pOrganizationRelationInfo = it.next();
					if(m>0){
						jsonTree.append(",");
					}
					m++;
					jsonTree.append("{");
					jsonTree.append("id:'" + pOrganizationRelationInfo.getId()+ "'");
					jsonTree.append(",text:'" + pOrganizationRelationInfo.getOrganizationInfo().getOrgShortCname()+ "'");
					jsonTree.append(",organizationInfoId:'" + pOrganizationRelationInfo.getOrganizationInfo().getId()+ "'");
					jsonTree.append(",organizationCode:'" + pOrganizationRelationInfo.getOrganizationInfo().getOrgCode()+ "'");
					jsonTree.append(",purpose:'" + pOrganizationRelationInfo.getOrganizationInfo().getPurpose()+ "'");
					jsonTree.append(",leaf:false" );
					jsonTree.append(",expanded:false");
					//如果flag为true，带有复选框
					if(flag){
						jsonTree.append(",checked:false");
					}
					jsonTree.append(",allowDelete:true"); 
					jsonTree.append(",iconCls:'chart_organisation'" );
					jsonTree.append("}");
				}
			}else{
				//以后加载
				List<GroupItemInfo> list = groupItemInfoDao.getGroupItemByGroupCode(val);
				//从数据库中查询出的机构
				List<OrganizationRelationInfo> organizationRelationInfoList = organizationRelationInfoDao.find("FROM OrganizationRelationInfo WHERE  organizationRelationInfo.id='"+id+"' order by organOrder");
				int m = 0;
				for (int i = 0; i < list.size(); i++) {
					GroupItemInfo groupItemInfo = list.get(i);
					//分组中的数据
					OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao.get(groupItemInfo.getObjOid());
					for (int j = 0; j < organizationRelationInfoList.size(); j++) {
						if(organizationRelationInfo.getId().equals(organizationRelationInfoList.get(j).getId())){
							if(m>0){
								jsonTree.append(",");
							}
							m++;
							jsonTree.append("{");
							jsonTree.append("id:'" + organizationRelationInfo.getId()+ "'");
							jsonTree.append(",text:'" + organizationRelationInfo.getOrganizationInfo().getOrgShortCname()+ "'");
							jsonTree.append(",organizationInfoId:'" + organizationRelationInfo.getOrganizationInfo().getId()+ "'");
							jsonTree.append(",organizationCode:'" + organizationRelationInfo.getOrganizationInfo().getOrgCode()+ "'");
							jsonTree.append(",purpose:'" + organizationRelationInfo.getOrganizationInfo().getPurpose()+ "'");
							for (int t = 0; t < list.size(); t++) {
								GroupItemInfo fgroupItemInfo = list.get(t);
								OrganizationRelationInfo forganizationRelationInfo = organizationRelationInfoDao.get(fgroupItemInfo.getObjOid());
								if(organizationRelationInfoList.get(j).getId().equals(forganizationRelationInfo.getOrganizationRelationInfo().getId())){
									jsonTree.append(",leaf:false" );
									break;
								}else if(t==list.size()-1){
									jsonTree.append(",leaf:true" );
								}
							}
							jsonTree.append(",expanded:false");
							//如果flag为true，带有复选框
							if(flag){
								jsonTree.append(",checked:false");
							}
							jsonTree.append(",allowDelete:true"); 
							jsonTree.append(",iconCls:'chart_organisation'" );
							jsonTree.append("}");
						}
					}
				}
			}
			jsonTree.append("]");
			return jsonTree.toString();
		}else{
			String jsontree = organizationRelationInfoManager.getOrganTree(id, "false", "orgShortCname", "all", "1");
			return jsontree;
		}
	}

	/**
	 * 通过机构分组编码信息和该节点的信息（关系表的oid）加载树形数据
	 * @date 2010-11-05
	 * @author wangxx
	 * 用途：
	 * 	1、组织分组的修改页面左侧菜单（显示中文名）
	 * 实现：需要判断是否是第一加载
	 * 	1、如果是第一次加载就需要将用户保存的组织分组信息和组织树信息相结合，
	 * 	  如果一个节点已经被用户选择，那在初始化的时候就会将其下级展开，并复选框为勾选
	 *  2、如果不是第一次加载就需要获取点击节点的信息（组织关系表的oid）通过该节点信息加载
	 *    组织树信息
	 * @param id 树节点id
	 * @param txt 分组信息属性名
	 * @param val 分组信息属性值
	 * @param flag 是否带复选框，如果为true待复选框并且不勾选
	 * @return tree的json数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getOrganRelationInfosByGroupLongName(String id,String txt,String val,boolean flag){
		StringBuffer jsonTree =new StringBuffer("[");
		if(id==null||id.equals("root")||id.equals("-1")||(id.split("-")[0].equals("xnode"))){
			//第一次加载数据
			List<GroupItemInfo> list = groupItemInfoDao.getGroupItemByGroupCode(val);
			Set<OrganizationRelationInfo> pList = new HashSet<OrganizationRelationInfo>();
			for (int i = 0; i < list.size(); i++) {
				GroupItemInfo groupItemInfo = list.get(i);
				OrganizationRelationInfo organizationRelationInfo = organizationRelationInfoDao
					.get(groupItemInfo.getObjOid()).getOrganizationRelationInfo();
				for (int j = 0; j < list.size(); j++) {
					String  groupItemInfoOid = list.get(j).getObjOid();
					if(organizationRelationInfo.getId().equals(groupItemInfoOid)){
						break;
					}else if(j==list.size()-1){
						pList.add(organizationRelationInfo);
					}
				}
			}
			//开始遍历已经存储的“根节点”
			Iterator<OrganizationRelationInfo> it = pList.iterator();
			int m = 0;
			while(it.hasNext()){
				OrganizationRelationInfo pOrganizationRelationInfo = it.next();
				if(m>0){
					jsonTree.append(",");
				}
				m++;
				jsonTree.append("{");
				jsonTree.append("id:'" + pOrganizationRelationInfo.getId()+ "'");
				jsonTree.append(",text:'" + pOrganizationRelationInfo.getOrganizationInfo().getOrgCname()+ "'");
				jsonTree.append(",organizationInfoId:'" + pOrganizationRelationInfo.getOrganizationInfo().getId()+ "'");
				jsonTree.append(",organizationCode:'" + pOrganizationRelationInfo.getOrganizationInfo().getOrgCode()+ "'");
				jsonTree.append(",leaf:false" );
				jsonTree.append(",expanded:false");
				//如果flag为true，带有复选框
				if(flag){
					jsonTree.append(",checked:false");
				}
				jsonTree.append(",allowDelete:true"); 
				jsonTree.append(",iconCls:'chart_organisation'" );
				jsonTree.append("}");
			}
		}else{
			//以后加载
			List<GroupItemInfo> list = groupItemInfoDao.getGroupItemByGroupCode(val);
			//从数据库中查询出的机构
			List<OrganizationRelationInfo> organizationRelationInfoList = 
				organizationRelationInfoDao.find("FROM OrganizationRelationInfo " +
				"WHERE  organizationRelationInfo.id='"+id+"' order by organizationInfo.id");
			int m = 0;
			for (int i = 0; i < list.size(); i++) {
				GroupItemInfo groupItemInfo = list.get(i);
				//分组中的数据
				OrganizationRelationInfo organizationRelationInfo = 
					organizationRelationInfoDao.get(groupItemInfo.getObjOid());
				for (int j = 0; j < organizationRelationInfoList.size(); j++) {
					if(organizationRelationInfo.getId().equals(organizationRelationInfoList.get(j).getId())){
						if(m>0){
							jsonTree.append(",");
						}
						m++;
						jsonTree.append("{");
						jsonTree.append("id:'" + organizationRelationInfo.getId()+ "'");
						jsonTree.append(",text:'" + organizationRelationInfo.getOrganizationInfo().getOrgCname()+ "'");
						jsonTree.append(",organizationInfoId:'" + organizationRelationInfo.getOrganizationInfo().getId()+ "'");
						jsonTree.append(",organizationCode:'" + organizationRelationInfo.getOrganizationInfo().getOrgCode()+ "'");
						for (int t = 0; t < list.size(); t++) {
							GroupItemInfo fgroupItemInfo = list.get(t);
							OrganizationRelationInfo forganizationRelationInfo = 
								organizationRelationInfoDao.get(fgroupItemInfo.getObjOid());
							if(organizationRelationInfoList.get(j).getId().equals(
									forganizationRelationInfo.getOrganizationRelationInfo().getId())){
								jsonTree.append(",leaf:false" );
								break;
							}else if(t==list.size()-1){
								jsonTree.append(",leaf:true" );
							}
						}
						jsonTree.append(",expanded:false");
						//如果flag为true，带有复选框
						if(flag){
							jsonTree.append(",checked:false");
						}
						jsonTree.append(",allowDelete:true"); 
						jsonTree.append(",iconCls:'chart_organisation'" );
						jsonTree.append("}");
					}
				}
			}
		}
		jsonTree.append("]");
		return jsonTree.toString();
	}
	
}
