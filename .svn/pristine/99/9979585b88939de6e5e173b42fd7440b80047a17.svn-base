/**
 * Copyright (c) sinosoft May 18 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 18 2010
 * @author shenhy
 */
package com.sinosoft.organization.manager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.dao.GroupItemInfoDao;
import com.sinosoft.organization.dao.OrganMergeSplitInfoDao;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.domain.OrganMergeSplitInfo;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.manager.exception.ServiceException;

/**   
 * 机构合并拆分信息业务层
 * @author shenhy   
 * 
 * @date May 18 2010   
 */
@Service
@Transactional
public class OrganMergeSplitInfoManager extends EntityManager<OrganMergeSplitInfo, String>{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 注入机构信息dao层
	 */
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	
	/**
	 * 注入机构分组信息dao层
	 */
	@Autowired
	GroupItemInfoDao groupItemInfoDao;
	
	/**
	 * 注入合并，拆分Dao层
	 */
	@Autowired
	OrganMergeSplitInfoDao organMergeSplitInfoDao;
	
	/**
	 * 注入机构关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	
	//注入组织机构信息业务层
	@Autowired
	OrganizationInfoManager organizationInfoManager;
	
	/** 注入机构用户关系dao层 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	
	//注入机构关系业务层
	@Autowired
	OrganizationRelationInfoManager organizationRelationInfoManager;
	
	protected HibernateDao<OrganMergeSplitInfo, String> getEntityDao() {
		return organMergeSplitInfoDao;
	}
	/**
	 * 保存机构拆分信息
	 * @date 2010-12-13
	 * @author wangxx
	 * 步骤：
	 * 	1、遍历拆分后的组织信息
	 * 	2、保存拆分后的组织信息
	 * 	3、保存拆分后组织的关系信息
	 * 	4、保存拆分后组织和下级组织关系的信息
	 * 	5、遍历结束
	 * 	6、保存拆分信息
	 * 	7、通过待拆分的组织关系oid，查找的该组织在组织关系树上的所有关系
	 * 	8、遍历得到上面组织的所有下级组织
	 * 	9、删除和他们相关联的分组信息
	 * 	10、删除待拆分组织的关系信息
	 * @param organizationInfoList  拆分后的组织信息集合
	 * @param parentOrganizationRelationOids  拆分后的上级组织关系oid，每个oid之间用“%”分离
	 * @param childrensOrganizationRelationOids  拆分后的下级组织oid，每组oid用“%”分离，而每一组内部用“；”分离具体某个下级组织关系oid
	 * @param organRelaOid 被拆分的组织关系信息对象oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganSplitInfo(List<OrganizationInfo> organizationInfoList,
			String parentOrganizationRelationOids,String childrensOrganizationRelationOids,
			String organRelaOid){
		//上级组织关系oid
		String[] parentOrganizationRelationOid = parentOrganizationRelationOids.split("%");
		//下级组关系oid集合
		String[] childrensOrganizationRelationOid = childrensOrganizationRelationOids.split("%");
		//拆分后的组织oid
		String organOids = "";
		for (int i = 0; i < organizationInfoList.size(); i++) {
			OrganizationInfo organizationInfo = organizationInfoList.get(i);
			organizationInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
			//是否本级组织
			if(organizationInfo.getCurrentOrgan()==null||organizationInfo.getCurrentOrgan().equals("true")){
				organizationInfo.setCurrentOrgan("1");
			}else{
				organizationInfo.setCurrentOrgan("2");
			}
			//是否有效
			if(organizationInfo.getInvalidFlag()==null||organizationInfo.getInvalidFlag().equals("true")){
				organizationInfo.setInvalidFlag("1");
			}else{
				organizationInfo.setInvalidFlag("2");
			}
			//该组织为机构
			organizationInfo.setPurpose("1");
			//保存组织信息
			organizationInfoDao.save(organizationInfo);
			organOids +=organizationInfo.getId()+";";
			//封装组织关系信息
			
			OrganizationRelationInfo pOrganizationRelationInfo = 
				organizationRelationInfoDao.get(parentOrganizationRelationOid[i]);
			//因为上级组织可能出现多次所以需要便利
			Set<OrganizationRelationInfo> set = pOrganizationRelationInfo.getOrganizationInfo().getOrganizationRelationInfo();
			Iterator<OrganizationRelationInfo> it = set.iterator();
			while (it.hasNext()) {
				OrganizationRelationInfo pori = it.next();
				OrganizationRelationInfo organizationRelationInfo = new OrganizationRelationInfo();
				organizationRelationInfo.setOrganizationInfo(organizationInfo);
				//保存上级组织关系信息
				organizationRelationInfo.setOrganizationRelationInfo(pori);
				organizationRelationInfo.setOrganOrder(0);
				organizationRelationInfo.setRelation("1");
				//保存组织关系信息
				try {
					organizationRelationInfoManager.save(organizationRelationInfo);
				} catch (ServiceException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//遍历下级组织信息
				String[] childrens = childrensOrganizationRelationOid[i].split(";");
				for (int j = 0; j < childrens.length; j++) {
					//保存下级组织关系信息
					if(null!=childrens[j]&&!"".equals(childrens[j].trim())&&!"undefined".equals(childrens[j])){
						OrganizationRelationInfo cOrganizationRelationInfo = new OrganizationRelationInfo();
						cOrganizationRelationInfo.setOrganizationRelationInfo(organizationRelationInfo);
						cOrganizationRelationInfo.setOrganizationInfo(
							organizationRelationInfoDao.get(childrens[j]).getOrganizationInfo());
						cOrganizationRelationInfo.setOrganOrder(j);
						cOrganizationRelationInfo.setRelation("1");
						organizationRelationInfoDao.save(cOrganizationRelationInfo);
						//将下级组织的下级所有组织的关联信息都保存
						organizationInfoManager.saveOrganizationInfo(
							organizationRelationInfoDao.get(childrens[j]).getOrganizationInfo(), 
							cOrganizationRelationInfo);
					}
				}
			}
		}
		//处理被拆分的组织
		OrganizationRelationInfo o = organizationRelationInfoDao.get(organRelaOid);
		//封装拆分信息
		OrganMergeSplitInfo omsi = new OrganMergeSplitInfo();
		omsi.setOperateDate(new Timestamp(System.currentTimeMillis()));
		omsi.setOperateFlag("2");
		omsi.setOrganId(organOids);
		omsi.setOrganizationInfo(o.getOrganizationInfo());
		//保存拆分信息
		organMergeSplitInfoDao.save(omsi);
		//删除待拆分组织关系
		organizationInfoManager.deleteOrganizationInfoBySplit(organRelaOid);
	}
	
	/**
	 * 保存部门拆分信息
	 * @date 2010-12-13
	 * @author wangxx
	 * 步骤：
	 * 	1、遍历拆分后的组织信息
	 * 	2、保存拆分后的组织信息
	 * 	3、保存拆分后组织的关系信息
	 * 	4、保存拆分后组织和下级组织关系的信息
	 * 	5、保存拆分组织和用户之间的关系
	 * 	6、遍历结束
	 * 	7、通过待拆分的组织关系oid，查找的该组织在组织关系树上的所有关系
	 * 	8、遍历得到上面组织的所有下级组织
	 * 	9、删除和他们相关联的分组信息
	 * 	10、删除待拆分组织的关系信息
	 * @param organizationInfoList 拆分后的组织信息集合
	 * @param parentOrganizationRelationOids 拆分后的上级组织关系oid，每个oid之间用“%”分离
	 * @param childrensOrganizationRelationOids   拆分后的下级组织oid，每组oid用“%”分离，而每一组内部用“；”分离具体某个下级组织关系oid
	 * @param organOid 待拆分的组织关系oid
	 * @param personOids 待拆分的组织人员关系oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganSplitInfo(List<OrganizationInfo> organizationInfoList,
			String parentOrganizationRelationOids,String childrensOrganizationRelationOids,
			String organRelaOid,String personOids){
		//上级组织关系oid
		String[] parentOrganizationRelationOid = parentOrganizationRelationOids.split("%");
		//下级组关系oid集合
		String[] childrensOrganizationRelationOid = childrensOrganizationRelationOids.split("%");
		//待拆分的组织人员关系oid
		String[] personOid = personOids.split("%");
		//拆分后的组织oid
		String organOids = "";
		for (int i = 0; i < organizationInfoList.size(); i++) {
			OrganizationInfo organizationInfo = organizationInfoList.get(i);
			organizationInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
			//是否本级组织
			if(organizationInfo.getCurrentOrgan()==null||organizationInfo.getCurrentOrgan().equals("true")){
				organizationInfo.setCurrentOrgan("1");
			}else{
				organizationInfo.setCurrentOrgan("2");
			}
			//是否有效
			if(organizationInfo.getInvalidFlag()==null||organizationInfo.getInvalidFlag().equals("true")){
				organizationInfo.setInvalidFlag("1");
			}else{
				organizationInfo.setInvalidFlag("2");
			}
			//该组织为机构
			organizationInfo.setPurpose("2");
			//保存组织信息
			organizationInfoDao.save(organizationInfo);
			organOids +=organizationInfo.getId()+";";
				//保存上级组织关系信息
			OrganizationRelationInfo pOrganizationRelationInfo = 
				organizationRelationInfoDao.get(parentOrganizationRelationOid[i]);
			//因为上级组织有可能出现多次所以要便利全部添加
			Set<OrganizationRelationInfo> set = pOrganizationRelationInfo.getOrganizationInfo().getOrganizationRelationInfo();
			Iterator<OrganizationRelationInfo> it = set.iterator();
			while (it.hasNext()) {
				OrganizationRelationInfo newPOrganizationRelationInfo =  it.next();
				//封装组织关系信息
				OrganizationRelationInfo organizationRelationInfo = new OrganizationRelationInfo();
				organizationRelationInfo.setOrganizationInfo(organizationInfo);
				organizationRelationInfo.setOrganizationRelationInfo(newPOrganizationRelationInfo);
				organizationRelationInfo.setOrganOrder(0);
				organizationRelationInfo.setRelation("1");
				//保存组织关系信息
				try {
					organizationRelationInfoManager.save(organizationRelationInfo);
				} catch (ServiceException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//遍历下级组织信息
				String[] childrens = childrensOrganizationRelationOid[i].split(";");
				for (int j = 0; j < childrens.length; j++) {
					//保存下级组织关系信息
					if(null!=childrens[j]&&!"".equals(childrens[j].trim())&&!"undefined".equals(childrens[j])){
						OrganizationRelationInfo cOrganizationRelationInfo = new OrganizationRelationInfo();
						cOrganizationRelationInfo.setOrganizationRelationInfo(organizationRelationInfo);
						cOrganizationRelationInfo.setOrganizationInfo(
							organizationRelationInfoDao.get(childrens[j]).getOrganizationInfo());
						cOrganizationRelationInfo.setOrganOrder(j);
						cOrganizationRelationInfo.setRelation("1");
						organizationRelationInfoDao.save(cOrganizationRelationInfo);
						//将下级组织的下级所有组织的关联信息都保存
						organizationInfoManager.saveOrganizationInfo(
							organizationRelationInfoDao.get(childrens[j]).getOrganizationInfo(), 
							cOrganizationRelationInfo);
					}
				}
				//遍历分配给该组织的用户
				String[] person = personOid[i].split(";");
				for (int j = 0; j < person.length; j++) {
					//保存下级组织关系信息
					if(null!=person[j]&&!"".equals(person[j].trim())&&!"undefined".equals(person[j])){
						OrganPersonRelationInfo opriOld = organPersonRelationDao.get(person[j]);
						OrganPersonRelationInfo organPersonRelationInfo = organPersonRelationDao.getOrganPersonRelationInfoByOrganOidAndPersonOid(organizationInfo.getId(), opriOld.getPersonInfo().getId());
						if(organPersonRelationInfo==null){
							OrganPersonRelationInfo opri = new OrganPersonRelationInfo();
							opri.setOrganizationInfo(organizationInfo);
							opri.setPersonInfo(opriOld.getPersonInfo());
							opri.setUserPosition(opriOld.getUserPosition());
							organPersonRelationDao.save(opri);
						}else{
							continue;
						}
					}
				}
			}
		}
		//处理被拆分的组织
		OrganizationRelationInfo o = organizationRelationInfoDao.get(organRelaOid);
		//封装拆分信息
		OrganMergeSplitInfo omsi = new OrganMergeSplitInfo();
		omsi.setOperateDate(new Timestamp(System.currentTimeMillis()));
		omsi.setOperateFlag("2");
		omsi.setOrganId(organOids);
		omsi.setOrganizationInfo(o.getOrganizationInfo());
		//保存拆分信息
		organMergeSplitInfoDao.save(omsi);
		//删除待拆分组织关系
		organizationInfoManager.deleteOrganizationInfoBySplit(organRelaOid);
	}
	/**
	 * 合并组织机构和部门
	 * @author wangxx
	 * 在合并之前需要判断待合并组织的下级是否关联着新上级组织
	 * 根据新的需求，合并时分为三种情况，保留建制合并（complete），保留下级合并（part），不保留建制合并（unComplete）
	 * 保留建制合并（complete）：将本组织及下级的子组织保留
	 * 保留下级合并（part）：本组织将被删除，下面的子组织保留，本组织下的人员将被分配到新的上级组织下
	 * 不保留建制合并（unComplete）：本组织及下面的子组织将被删除，本组织以子组织下面的人员被分配到新的上级组织下
	 * @param mergeType 合并类型
	 * @param organOids 待合并组织关系oid
	 * @param organRelaOid  新上级组织
	 * @return parentFalse表示新上级组织在待合并组织中或者在待合并组织下级，true表示成功,childFalse表示新上级组织下已经存在已经存在带合并的组织信息。
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String insertOrganMergeInfo(String mergeType,String organRelaOids,String organRelaOid){
		//待合并的组织关系表oid
		String[] orgRelaOid = organRelaOids.split(";");
		//新上级组织
		OrganizationRelationInfo pORI = organizationRelationInfoDao.get(organRelaOid);
		for (int i = 0; i < orgRelaOid.length; i++) {
			//需要判断待合并组织的下级是否关联着新上级组织
			List<OrganizationRelationInfo> childList = organizationRelationInfoManager.getOrganizationRelationInfoByParentOrgRelaOid(orgRelaOid[i]);
			for (OrganizationRelationInfo o : childList ) {
				if(o.getOrganizationInfo().getId().equals(pORI.getOrganizationInfo().getId())){
					return "parentFalse";
				}
			}
			//判断新上级组织是否在待合并组织中
			OrganizationRelationInfo ori = organizationRelationInfoDao.get(orgRelaOid[i]);
			if(ori.getOrganizationInfo().getId().equals(pORI.getOrganizationInfo().getId())){
				return "parentFalse";
			}
			//判断新上级组织下已经存在已经存在带合并的组织信息
			Set<OrganizationRelationInfo> set = pORI.getChildrens();
			Iterator<OrganizationRelationInfo> it = set.iterator();
			while (it.hasNext()) {
				OrganizationRelationInfo cOrganizationRelationInfo = it.next();
				if(ori.getOrganizationInfo().getId().equals(cOrganizationRelationInfo.getOrganizationInfo().getId())){
					return "childFalse";
				}
			}
		}
		//定义一个list用于最后删除机构关系
		if(mergeType.equals("complete")){
			//保留建制合并（complete）
			mergeComplete(orgRelaOid, pORI);
		}else if(mergeType.equals("part")){
			//保留下级合并（part）
			mergePart(orgRelaOid, pORI);
		}else if(mergeType.equals("unComplete")){
			//不保留建制合并（unComplete）
			mergeUnComplete(orgRelaOid, pORI);
		}
		return "true";
	}
	/**
	 * 保留全部建制的合并   要在合并结束后删除去再分组表中的信息数据
	 * @date 2010-11-30
	 * @author wangxx
	 * @param orgRelaOid  待合并组织oid数组
	 * @param pORI 新上级组织在关系表中的对象
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void mergeComplete(String[] orgRelaOid,OrganizationRelationInfo pORI){
		//用于存放分组关系信息中的实体类oid
		List<String> listString = new ArrayList<String>();
		//定义一个list用于最后删除机构关系
		List<OrganizationRelationInfo> deleteOrganList=new ArrayList<OrganizationRelationInfo>();
		//保存到数据库中合并的部门
		StringBuffer organInfoOids = new StringBuffer("");
		//保留建制
		for (int i = 0; i < orgRelaOid.length; i++) {
			OrganizationRelationInfo ori = organizationRelationInfoDao.get(orgRelaOid[i]);
			OrganizationInfo organ = ori.getOrganizationInfo();
			Set<OrganizationRelationInfo> organRelaSet = organ.getOrganizationRelationInfo();
			//判断是否在组织机构树上出现多次，如果出现多次就将没有勾选合并的组织删除
			if(organRelaSet.size()>0){
				//说明该组织在组织机构树上出现多次
				Iterator<OrganizationRelationInfo> it = organRelaSet.iterator();
				while(it.hasNext()){
					OrganizationRelationInfo orii = it.next();
					deleteOrganList.add(orii);
					//添加下级组织分组信息中的实体oid
					List<OrganizationRelationInfo> childList = organizationRelationInfoManager
						.getOrganizationRelationInfoByParentOrgRelaOid(ori.getId());
					for (int j = 0; j < childList.size(); j++) {
						listString.add(childList.get(j).getId());
					}
				}
			}
			//上级组织在组织树上存在所有节点信息
			Set<OrganizationRelationInfo> set =pORI.getOrganizationInfo().getOrganizationRelationInfo();
			Iterator<OrganizationRelationInfo> pit = set.iterator();
			while (pit.hasNext()) {
				OrganizationRelationInfo newPOrganizationRelationInfo = new OrganizationRelationInfo();
				newPOrganizationRelationInfo.setOrganizationInfo(organ);
				newPOrganizationRelationInfo.setOrganizationRelationInfo(pit.next());
				newPOrganizationRelationInfo.setOrganOrder(ori.getOrganOrder());
				newPOrganizationRelationInfo.setRelation(ori.getRelation());
				organizationRelationInfoDao.save(newPOrganizationRelationInfo);
				organizationInfoManager.saveOrganizationInfo(organ, newPOrganizationRelationInfo);
			}
			if(i>0){
				organInfoOids.append(",");
			}
			organInfoOids.append(ori.getOrganizationInfo().getId());
		}
		//封装合并机构的信息
		OrganMergeSplitInfo omsi = new OrganMergeSplitInfo();
		omsi.setOrganId(organInfoOids.toString());
		omsi.setOperateDate(new Timestamp(System.currentTimeMillis()));
		omsi.setOperateFlag("1");
		omsi.setOrganizationInfo(pORI.getOrganizationInfo());
		organMergeSplitInfoDao.save(omsi);
		for (int i = 0; i < deleteOrganList.size(); i++) {
			OrganizationRelationInfo orii= deleteOrganList.get(i);
			//添加本级组织分组信息中的实体oid
			//orii.setOrganizationRelationInfo(null);
			orii.getOrganizationInfo().getOrganizationRelationInfo().remove(orii);
			orii.setOrganizationInfo(null);
			organizationRelationInfoDao.delete(orii.getId());
			String oirOid=orii.getId();
			listString.add(oirOid);
		}
		//删除分组信息
		for (int i = 0; i < listString.size(); i++) {
			//获得组织对应的分组信息
			List<GroupItemInfo> groupList = groupItemInfoDao.getGroupItemInfoByObjOid(listString.get(i));
			//遍历本级关系对象对应的分组信息，并删除
			for (int j2 = 0; j2 < groupList.size(); j2++) {
				GroupItemInfo gii = groupList.get(j2);
				gii.getGroupInfo().getGroupItemInfo().remove(gii);
				gii.setGroupInfo(null);
				groupItemInfoDao.delete(gii);
			}
		}
	}
	/**
	 * 保留下级建制的合并方法    要在合并结束后删除去再分组表中的信息数据
	 * @date 2010-11-30
	 * @author wangxx
	 * @param orgRelaOid  待合并组织oid数组
	 * @param pORI 新上级组织在关系表中的对象
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void mergePart(String[] orgRelaOid,OrganizationRelationInfo pORI){
		//用于存放分组关系信息中的实体类oid
		List<String> listString = new ArrayList<String>();
		//定义一个list用于最后删除机构关系
		List<OrganizationRelationInfo> deleteOrganList=new ArrayList<OrganizationRelationInfo>();
		//保存到数据库中合并的部门
		StringBuffer organInfoOids = new StringBuffer("");
		//保留子组织建制
		for (int i = 0; i < orgRelaOid.length; i++) {
			OrganizationRelationInfo ori = organizationRelationInfoDao.get(orgRelaOid[i]);
			//判断是否在组织机构树上出现多次，如果出现多次就将没有勾选合并的组织删除  还有将待合并组织的关系信息删除
			OrganizationInfo organ = ori.getOrganizationInfo();
			Set<OrganizationRelationInfo> organRelaSet = organ.getOrganizationRelationInfo();
			//说明该组织在组织机构树上出现多次
			Iterator<OrganizationRelationInfo> it = organRelaSet.iterator();
			while(it.hasNext()){
				OrganizationRelationInfo orii = it.next();
				deleteOrganList.add(orii);
			}
			//保存组织本级的用户信息
			Set<OrganPersonRelationInfo> set = organ.getOrganPersonRelationInfo();
			Set<OrganPersonRelationInfo> personSet = pORI.getOrganizationInfo().getOrganPersonRelationInfo();
			//需要和新上级组织建立关系的用户关系集合
			Set<OrganPersonRelationInfo> editSet = new HashSet<OrganPersonRelationInfo>();
			Iterator<OrganPersonRelationInfo> setIt = set.iterator();
			Iterator<OrganPersonRelationInfo> orgPerRelaSet = personSet.iterator();
			//用于判断要待合并组织和新上级组织是否存在相同的人员
			while(setIt.hasNext()){
				OrganPersonRelationInfo opri = setIt.next();
				int m = 0;
				while(orgPerRelaSet.hasNext()){
					OrganPersonRelationInfo popri = orgPerRelaSet.next();
					//判断上级组织是否已经含有该人员
					if(opri.getPersonInfo().getId().equals(popri.getPersonInfo().getId())){
						//已经含有该人员  删除
					}else{
						//不含有  修改  
						m++;
					}
				}
				if(m==personSet.size()){
					editSet.add(opri);
				}
			}
			//将需要保存的组织用户关系，和新的上级机构建立关系
			Iterator<OrganPersonRelationInfo> editSetIt = editSet.iterator();
			logger.info(""+editSet.size());
			while(editSetIt.hasNext()){
				OrganPersonRelationInfo opri = editSetIt.next();
				OrganPersonRelationInfo newOPRI = new OrganPersonRelationInfo();
				newOPRI.setPersonInfo(opri.getPersonInfo());
				newOPRI.setUserPosition(opri.getUserPosition());
				newOPRI.setOrganizationInfo(pORI.getOrganizationInfo());
				organPersonRelationDao.save(newOPRI);
			}
			List<OrganPersonRelationInfo> listOrganPersonRelationInfo = new ArrayList<OrganPersonRelationInfo>();
			//将需要删除的组织用户关系
			Iterator<OrganPersonRelationInfo> removeSetIt = set.iterator();
			while(removeSetIt.hasNext()){
				OrganPersonRelationInfo opri = removeSetIt.next();
				listOrganPersonRelationInfo.add(opri);
				//获得组织用户对应的分组信息
				listString.add(opri.getId());
			}
			for (int j = 0; j < listOrganPersonRelationInfo.size(); j++) {
				OrganPersonRelationInfo opri = listOrganPersonRelationInfo.get(j);
				opri.getOrganizationInfo().getOrganPersonRelationInfo().remove(opri);
				opri.setOrganizationInfo(null);
				opri.getPersonInfo().getOrganPersonRelationInfo().remove(opri);
				opri.setPersonInfo(null);
				organPersonRelationDao.delete(opri);
			}
			//待合并组织的子组织
			List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrgRelaInfoListByParentOrgRelaOid(ori.getId());
			for (int j = 0; j < list.size(); j++) {
				//待合并组织的子组织
				OrganizationRelationInfo oric = list.get(j);
				oric.getOrganizationRelationInfo().getChildrens().remove(oric);
				oric.setOrganizationRelationInfo(pORI);
				//保存子组织的关系信息
				organizationRelationInfoDao.save(oric);
			}
			if(i>0){
				organInfoOids.append(",");
			}
			organInfoOids.append(ori.getOrganizationInfo().getId());
		}
		//封装合并对象数据
		OrganMergeSplitInfo omsi = new OrganMergeSplitInfo();
		omsi.setOrganId(organInfoOids.toString());
		omsi.setOperateDate(new Timestamp(System.currentTimeMillis()));
		omsi.setOperateFlag("1");
		omsi.setOrganizationInfo(pORI.getOrganizationInfo());
		organMergeSplitInfoDao.save(omsi);
		//将要删除的组织分组信息放到集合中，以便删除分组信息
		for (int i = 0; i < deleteOrganList.size(); i++) {
			listString.add(deleteOrganList.get(i).getId());
		}
		//删除分组信息
		for (int i = 0; i < listString.size(); i++) {
			//获得组织对应的分组信息
			List<GroupItemInfo> groupList = groupItemInfoDao.getGroupItemInfoByObjOid(listString.get(i));
			//遍历本级关系对象对应的分组信息，并删除
			for (int j2 = 0; j2 < groupList.size(); j2++) {
				groupItemInfoDao.delete(groupList.get(j2));
			}
		}
		//删除操作
		for (int i = 0; i < deleteOrganList.size(); i++) {
			OrganizationRelationInfo ori = deleteOrganList.get(i);
			organizationRelationInfoManager.removeOrganizationRelationInfo(ori.getId());
		}
	}
	/**
	 * 完全合并，不保留建制的合并  要在合并结束后删除去再分组表中的信息数据
	 * @date 2010-11-30
	 * @author wangxx
	 * @param orgRelaOid  待合并组织oid数组
	 * @param pORI 新上级组织在关系表中的对象
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void mergeUnComplete(String[] orgRelaOid,OrganizationRelationInfo pORI){
		//不保留建制
		//用于存放分组关系信息中的实体类oid
		List<String> listString = new ArrayList<String>();
		//定义一个list用于最后删除机构关系
		List<OrganizationRelationInfo> deleteOrganList=new ArrayList<OrganizationRelationInfo>();
		//保存到数据库中合并的部门
		StringBuffer organInfoOids = new StringBuffer("");
		//用于保存需要重新分配组织的人员
		Set<PersonInfo> setPersonInfo = new HashSet<PersonInfo>();
		//用于删除无效的机构用户关系
		Set<OrganPersonRelationInfo> setOrganPersonRelationInfo = new HashSet<OrganPersonRelationInfo>();
		for (int i = 0; i < orgRelaOid.length; i++) {
			//获取待合并组织的关系对象
			OrganizationRelationInfo ori = organizationRelationInfoDao.get(orgRelaOid[i]);
			//判断是否在组织机构树上出现多次，如果出现多次就将没有勾选合并的组织删除  还有将待合并组织的关系信息删除
			OrganizationInfo organ=ori.getOrganizationInfo();
			Set<OrganizationRelationInfo> organRelaSet = organ.getOrganizationRelationInfo();
			//说明该组织在组织机构树上出现多次
			Iterator<OrganizationRelationInfo> it = organRelaSet.iterator();
			while(it.hasNext()){
				OrganizationRelationInfo orii = it.next();
				//获得组织对应的分组信息
				listString.add(orii.getId());
				//获取其下级组织关系对象集合
				List<OrganizationRelationInfo> oriList = organizationRelationInfoManager.getOrganizationRelationInfoByParentOrgRelaOid(orii.getId());
				for (int j = 0; j < oriList.size(); j++) {
					//填充组织关系oid
					listString.add(oriList.get(j).getId());
					List<OrganPersonRelationInfo> organPersonList = organPersonRelationDao.getOrganPersonRelationInfoByOrganOid(oriList.get(j).getOrganizationInfo().getId());
					for (int k = 0; k < organPersonList.size(); k++) {
						//填充组织用户关系oid
						listString.add(organPersonList.get(k).getId());
					}
				}
				deleteOrganList.add(orii);
			}
			//封装需要重新分配机构的人员
				//本级组织的用户
			List<OrganPersonRelationInfo> personList = organPersonRelationDao.getOrganPersonRelationInfoByOrganOid(organ.getId());
			for (int j = 0; j < personList.size(); j++) {
				setOrganPersonRelationInfo.add(personList.get(j));
				setPersonInfo.add(personList.get(j).getPersonInfo());
			}
				//待合并组织的子组织
			List<OrganizationRelationInfo> childList = organizationRelationInfoManager.getOrganizationRelationInfoByParentOrgRelaOid(orgRelaOid[i]);
			for (int j = 0; j < childList.size(); j++) {
				//获得组织对应的分组信息
				listString.add(childList.get(j).getId());
				personList = organPersonRelationDao.getOrganPersonRelationInfoByOrganOid(childList.get(j).getOrganizationInfo().getId());
				for (int j2 = 0; j2 < personList.size(); j2++) {
					setOrganPersonRelationInfo.add(personList.get(j2));
					setPersonInfo.add(personList.get(j2).getPersonInfo());
				}
			}
			//封装待合并组织的组织oid
			if(i>0){
				organInfoOids.append(",");
			}
			organInfoOids.append(ori.getOrganizationInfo().getId());
		}
		//封装合并对象数据
		OrganMergeSplitInfo omsi = new OrganMergeSplitInfo();
		omsi.setOrganId(organInfoOids.toString());
		omsi.setOperateDate(new Timestamp(System.currentTimeMillis()));
		omsi.setOperateFlag("1");
		omsi.setOrganizationInfo(pORI.getOrganizationInfo());
		organMergeSplitInfoDao.save(omsi);
		//保存新的用户信息setPersonInfo
		Iterator<PersonInfo> it = setPersonInfo.iterator();
		while(it.hasNext()){
			PersonInfo p = it.next();
			OrganPersonRelationInfo opri = new OrganPersonRelationInfo();
			opri.setOrganizationInfo(pORI.getOrganizationInfo());
			opri.setPersonInfo(p);
			List<OrganPersonRelationInfo> pList =organPersonRelationDao.getOrganPersonRelationInfoByPersonOid(p.getId());
			if(pList.size()>0){
				opri.setUserPosition(pList.get(0).getUserPosition());
			}else{
				opri.setUserPosition("");
			}
			organPersonRelationDao.save(opri);
		}
		//删除机构用户关系信息
		Iterator<OrganPersonRelationInfo> opriIt = setOrganPersonRelationInfo.iterator();
		while(opriIt.hasNext()){
			OrganPersonRelationInfo opri = opriIt.next();
			listString.add(opri.getId());
			organPersonRelationDao.delete(opri);
		}
		//删除分组信息
		for (int i = 0; i < listString.size(); i++) {
			//获得组织对应的分组信息
			List<GroupItemInfo> groupList = groupItemInfoDao.getGroupItemInfoByObjOid(listString.get(i));
			//遍历本级关系对象对应的分组信息，并删除
			for (int j2 = 0; j2 < groupList.size(); j2++) {
				groupItemInfoDao.delete(groupList.get(j2));
			}
		}
		
		//删除机构关系信息
		for (int i = 0; i < deleteOrganList.size(); i++) {
			organizationRelationInfoManager.removeOrganizationRelationInfo(deleteOrganList.get(i).getId());
		}
	}

}
