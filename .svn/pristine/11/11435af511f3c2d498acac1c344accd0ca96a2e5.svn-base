package com.sinosoft.organization.manager;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.dao.GroupItemInfoDao;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**
 * 机构分组项信息Manager层
 * @author Administrator
 */
@Service
@Transactional
public class GroupItemInfoManager extends EntityManager<GroupItemInfo, String>{
	/**
	 * 注入机构分组项信息dao层
	 */
	@Autowired
	GroupItemInfoDao groupItemInfoDao;
	
	@Override
	protected HibernateDao<GroupItemInfo, String> getEntityDao() {
		return groupItemInfoDao;
	}

	/**
	 * 注入人员信息持久层
	 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;

	/**
	 * 注入机构关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	/**
	 * 通过组织分组信息oid查询人员分组信息，并封装成json列表信息返回
	 * @date 2010-12-03
	 * @author wangxx
	 * 用途：
	 *  1、用于渲染修改分组信息中的用户列表信息
	 * @param groupInfoOid 分组信息对象oid
	 * @param start 当前页开始条数
	 * @param limit 每页显示多少条数据
	 * @return grid的json数据
	 */
	@Transactional(readOnly=true)
	public String loadOrganGroupGrid(String groupInfoOid,int start, int limit){
		StringBuffer json = new StringBuffer("{totalProperty:");
		int count = groupItemInfoDao.getGroupItemSizeByGroupOid(groupInfoOid);
		json.append(count);
		json.append(",result:[");
		List<Object[]> list = groupItemInfoDao.getGroupItemByGroupOid(groupInfoOid, start, limit);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			GroupItemInfo groupItemInfo = (GroupItemInfo) obj[0];
			OrganPersonRelationInfo organPersonRelationInfo = organPersonRelationDao.get(groupItemInfo.getObjOid());
			if(i>0){
				json.append(",");
			}
			json.append("{");
			json.append("Relation_Oid : '"+organPersonRelationInfo.getId()+"'");
			json.append(",Person_CName : '"+organPersonRelationInfo.getPersonInfo().getUserCname()+"'");
			json.append(",Person_EName : '"+organPersonRelationInfo.getPersonInfo().getUserEname()+"'");
			json.append(",Parent_Org_ID : '"+organPersonRelationInfo.getOrganizationInfo().getOrgCname()+"'");
			json.append(",User_Position : '"+organPersonRelationInfo.getUserPosition()+"'");
			json.append("}");
		}
		json.append("]}");
		return json.toString();
	}

	/**
	 * 通过关系表的oid删除分组项信息(包括本级)
	 * @date 2011-07-01
	 * @author wangxx
	 * @param organRelationInfoOid  关系表oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteGroupItemInfoByOrgRelaInfoOid(String organRelationInfoOid){
		//获取关系对象
		OrganizationRelationInfo ori = organizationRelationInfoDao.get(organRelationInfoOid);
		//通过本级关系信息查找分组信息
		List<GroupItemInfo> groupPList = groupItemInfoDao.getGroupItemInfoByOrgRelaOid(ori.getId());
		//遍历本级关系对象对应的分组信息
		for (int j = 0; j < groupPList.size(); j++) {
			groupItemInfoDao.delete(groupPList.get(j));
		}
		//获得该节点下级组织关系节点
		List<OrganizationRelationInfo> list = organizationRelationInfoDao
			.getOrganizationRelationInfoByParentOrgOid(organRelationInfoOid);
		//通过set集合去除重复
		Set<OrganizationInfo> set = new HashSet<OrganizationInfo>();
		//将本机节点对应的组织也加入到set集合中
		set.add(ori.getOrganizationInfo());
		//遍历下级节点集合，并将这些节点删除
		for (int i = 0; i < list.size(); i++) {
			//获得组织对应的分组信息
			List<GroupItemInfo> groupList = groupItemInfoDao
				.getGroupItemInfoByOrgRelaOid(list.get(i).getId());
			//遍历下级级关系对象对应的分组信息
			for (int j = 0; j < groupList.size(); j++) {
				groupItemInfoDao.delete(groupList.get(j));
			}
			set.add(list.get(i).getOrganizationInfo());
		}
		Iterator<OrganizationInfo> it = set.iterator();
		//遍历组织对象
		while(it.hasNext()){
			OrganizationInfo oi = it.next();
			//通过组织对象的oid到组织关系中查找看是否还存在关系
			List<OrganizationRelationInfo> organList = organizationRelationInfoDao
				.getOrganizationRelationInfoListByOrganization("id", oi.getId());
			if(organList.size()>0){
				//存在关系  这样不删除组织和用户之间的关系
			}else{
				//不存在关系  删除该组织和用户之间的关系
				//通过组织oid查找组织用户关系  将其删除
				List<OrganPersonRelationInfo> oprList = organPersonRelationDao
					.getOrganPersonRelationInfoByOrganOid(oi.getId());
				for (int i = 0; i < oprList.size(); i++) {
					//获得组织用户对应的分组信息
					List<GroupItemInfo> groupList = groupItemInfoDao
						.getGroupItemInfoByPersonOid(oprList.get(i).getId());
					//遍历，将分组信息删除
					for (int j = 0; j < groupList.size(); j++) {
						groupItemInfoDao.delete(groupList.get(j));
					}
				}
			}
		}
	}
}
