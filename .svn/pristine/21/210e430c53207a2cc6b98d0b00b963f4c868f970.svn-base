package com.sinosoft.organization.manager;


import java.sql.Timestamp;
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
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.GroupItemInfo;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**
 * 机构信息Manager层
 * @author wangxx
 */
@Service
@Transactional
public class OrganizationInfoManager extends EntityManager<OrganizationInfo, String>{
	
	//private Logger logger = Logger.getLogger(this.getClass().getName());
	
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
	

	/** 注入持久层类 */
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	
	/**
	 * 注入机构分组信息dao层
	 */
	@Autowired
	GroupItemInfoDao groupItemInfoDao;
	
	@Override
	protected HibernateDao<OrganizationInfo, String> getEntityDao() {
		return organizationInfoDao;
	}
	/**
	 * 通过组织中文名和上级组织关系oid判断该组织下是否已经存在此中文名的下级组织
	 * @date 2010-12-13
	 * @author wangxx
	 * 用途：
	 *  1、在组织拆分时，同时判断多个组织中文名是否已经存在
	 * @param filterTxt 组织基本信息属性名
	 * @param parentOrgRelaOid 上级组织关系oid，每个oid之间用“；”分离
	 * @param filterValue 组织基本信息属性值，每个中文名之间用“；”分离
	 * @return true：存在      false：不存在
	 */
	@Transactional(readOnly = true)
	public String judgeOrganizationInfos(String filterTxt,String parentOrgRelaOid,String filterValue){
		String[] parentOrgRelaOids = parentOrgRelaOid.split("%");
		String[] filterValues = filterValue.split("%");
		for (int i = 0; i < filterValues.length; i++) {
			List<OrganizationRelationInfo> list = organizationRelationInfoDao
				.getOrgRelaInfoByOrgAndParentOrgRelaOid(filterTxt, filterValues[i],
				parentOrgRelaOids[i]);
			//判断是否已经存在
			if(list.size()>0){
				//存在
				return "true";
			}
		}
		return "false";
	}
	/**
	 * 用于判断含有某属性值的组织在上级组织下已经存在，例如判断上级组织下是否已经含有某中文名的组织
	 * 判断是否为新增机构，如果为新增只需要判断当前上级组织是否已经存在该属性的组织
	 * 如果不是新增组织就需要考虑
	 * @date 2010-12-01
	 * @author wangxx
	 * @param filterTxt  属性名称
	 * @param filterValue  属性值
	 * @param parentOrgRelaOid  上级部门关系对象oid
	 * @param orgOid  本级组织的oid
	 * @return  返回true是说名存在，不能添加，返回false时说明不存在可以添加
	 */
	@Transactional(readOnly = true)
	public String judgeOrganizationInfo(String filterTxt,String filterValue,String parentOrgRelaOid,String orgOid){
		String flag="true";
		//判断是新增还是修改组织信息
		if(null!=orgOid&&!"".equals(orgOid.trim())){
			//修改组织信息
			OrganizationRelationInfo ori = organizationRelationInfoDao.get(orgOid);
			//需要判断是否被修改，如果没有被修改，可以提交
			List<OrganizationInfo> list = organizationInfoDao.getOrganizationInfoListByOidAndOrganizationProperty(ori.getOrganizationInfo().getId(), filterTxt, filterValue);
			if(list.size()==0){
				//说明已经被修改
				List<OrganizationRelationInfo> orgRelaList = organizationRelationInfoDao.getOrgRelaInfoByOrgAndParentOrgRelaOid(filterTxt, filterValue, ori.getOrganizationRelationInfo().getId());
				if(orgRelaList.size()==0){
					//说明可以添加，不存在
					flag = "false";
				}
			}else{
				//没有被修改
				flag="false";
			}
		}else{
			//新增组织信息
			List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrgRelaInfoByOrgAndParentOrgRelaOid(filterTxt, filterValue, parentOrgRelaOid);
			//判断是否已经存在
			if(list.size()==0){
				//不存在
				flag = "false";
			}
		}
		return flag;
	}
	/**
	 * 通过关系表的oid删除组织，此处会将oid对应的组织信息和上级组织信息确定出的关系信息都删除。
	 * 调用哪些方法:
	 * 1、groupItemInfoDao.getGroupItemInfoByOrgRelaOid
	 * 2、organizationRelationInfoDao
     *			.getOrganizationRelationInfoByParentOrgOid
	 * 3、organizationRelationInfoDao
	 *		.getOrganizationRelationInfoListByParentOrgOidAndOrnOid
	 * 4、organPersonRelationDao
	 *				.getOrganPersonRelationInfoByOrganOid
	 * 被哪些方法调用:
	 * 1、OrganizationInfoAction.deleteOrganizationInfo
	 * @date 2010-11-29
	 * @author wangxx
	 * 此处只是删除组织的关系信息，不会删除组织的基本信息，在删除组织关系的同时还会删除和这个机构相关联的组织用户关系信息
	 * 1、通过关系表oid查找关系对象
	 * 2、通过关系oid遍历其下级所有的组织信息，去除重复
	 * 3、通过查询出的关系对象可以查出上级组织基本信息和本级组织基本信息
	 * 4、通过以上两个信息可以查出两者在关系表中的所有信息
	 * 5、然后将他们之间所有的关系信息都删除  再删除组织关系时需要将他们分组信息删除
	 * 6、删除组织用户之间的关系
	 * 		(1)首先要判断该组织在组织关系树上是否存在节点
	 * 			①如果不存在则删除其组织用户关系信息  删除时需要将分组信息一并删除
	 * 			②如果存在则不删除其组织用户关系信息
	 * @param organRelationInfoOid  关系表oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOrganizationInfo(String organRelationInfoOid){
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
		//通过本级组织信息，和上级组织信息查出两者在关系表中的所有相关联的信息
		List<OrganizationRelationInfo> rList = organizationRelationInfoDao
			.getOrganizationRelationInfoListByParentOrgOidAndOrnOid(
			ori.getOrganizationRelationInfo().getOrganizationInfo().getId(),
			ori.getOrganizationInfo().getId());
		//遍历该集合并删除组织关系信息
		for (int i = 0; i < rList.size(); i++) {
			organizationRelationInfoDao.delete(rList.get(i));
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
					organPersonRelationDao.delete(oprList.get(i));
				}
			}
		}
	}
	/**
	 * 通过关系表的oid删除组织,此处是将该oid对应的组织信息在组织关系信息中都删除
	 * @date 2010-11-29
	 * @author wangxx
	 * 用途：
	 * 	1、在机构拆分时会将待拆分的机构关系信息，下级的人员信息，组织分组信息，用户分组信息都删除
	 * 此处只是删除组织的关系信息，不会删除组织的基本信息，在删除组织关系的同时还会删除和这个机构相关联的组织用户关系信息
	 * 1、通过关系表oid查找关系对象
	 * 2、通过关系oid遍历其下级所有的组织信息，去除重复
	 * 3、通过查询出的关系对象可以查出本级组织基本信息
	 * 4、通过本级组织基本信息可以查出两者在关系表中的所有信息
	 * 5、然后将他们之间所有的关系信息都删除  再删除组织关系时需要将他们分组信息删除
	 * 6、删除组织用户之间的关系
	 * 		(1)首先要判断该组织在组织关系树上是否存在节点
	 * 			①如果不存在则删除其组织用户关系信息  删除时需要将分组信息一并删除
	 * 			②如果存在则不删除其组织用户关系信息
	 * @param organRelationInfoOid  关系表oid
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteOrganizationInfoBySplit(String organRelationInfoOid){
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
		//通过本级组织信息，和上级组织信息查出两者在关系表中的所有相关联的信息
		List<OrganizationRelationInfo> rList = organizationRelationInfoDao
			.getOrganizationRelationInfoByOrganizationInfoOid(
			ori.getOrganizationInfo().getId());
		//遍历该集合并删除组织关系信息
		for (int i = 0; i < rList.size(); i++) {
			organizationRelationInfoDao.delete(rList.get(i));
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
					organPersonRelationDao.delete(oprList.get(i));
				}
			}
		}
	}
	/**
	 * 用于判断是否已经存在该组织信息，判断是需要考虑已经被删除的组织，如果已经被删除的组织是可以重复添加的，
	 * 通过判断该属性是否已经在关系表中存在判断是否可以添加
	 * @date 2010-11-29
	 * @author wangxx
	 * 1、直接添加新的组织
	 * 		由于是新添加组织需要在验证是否已经在关系表中存在这样的organiztion对象，如果不存在说明没有这样的organiztion对象或者存在但是已经被删除
	 * 		这样的属性是可以再被使用的。
	 * 2、将已有的组织关联到别的组织下，主要根据是否有组织oid决定
	 * 		需要判断关联之后的该属性是否被用户修改
	 * 			①如果没有被修改，直接返回false
	 * 			②如果被修改了，就需要判断修改后的是否已经存在
	 * @param filterTxt   属性名
	 * @param filterValue   属性名对应的值
	 * @param organOid  组织对象oid判断是否为新增
	 * @return 返回true表明已经存在，不可以添加，如果为false说明可以添加
	 */
	@Transactional(readOnly = true)
	public String judgeOrganizationInfo(String filterTxt,String filterValue,String organOid){
		String flag = "true";
		//判断是新增的组织还是关联已有的组织
		if(null!=organOid&&!"".equals(organOid.trim())){
			//关联已有的用户
			//判断该属性是否被修改
			List<OrganizationInfo> list = organizationInfoDao.getOrganizationInfoListByOidAndOrganizationProperty(organOid, filterTxt, filterValue);
			if(list.size()==0){
				//已经被修改
				List<OrganizationRelationInfo> listOPRI = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization(filterTxt, filterValue);
				if(listOPRI.size()==0){
					flag="false";
				}else{
					flag="true";
				}
			}else{
				//说明没有被修改
				flag="false";
			}
		}else{
			//新的用户
			List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization(filterTxt, filterValue);
			if(list.size()==0){
				flag="false";
			}
		}
		return flag;
	}
	
	/**
	 * 保存组织信息
	 * @date 2010-11-28
	 * @author wangxx
	 * 调用哪些方法：
	 * 1、insertOrganizationInfo
	 * 2、saveOrganizationInfo
	 * 被哪些方法调用：
	 * 1、OrganizationInfoAction.saveOrganizationInfo
	 * 2、PetitionOrganInfoManager.saveOrganizationInfo
	 * 首先需要判断是否是新增组织，主要用一个表示判断
	 * 1、新增组织：分两种情况，是否是已有的组织关联到其他组织下级，主要判断该组织是否已经存在组织Oid
	 * 	（1）如果是新增的组织只需要保存组织信息，在保存机构关系信息即可
	 * 	（2）如果是关联有的信息，就需要将其下级机构都关联过来
	 * 		①需要判断该组织的需要关联的上级组织是否在组织树上出现多次，如果出现多次，就需要都关联上
	 * 		②遍历下级组织，将下级组织作出关联
	 * 2、修改组织信息：只需要保存组织信息，在保存关系信息即可。
	 * @param  flag  判断新增或者修改的标示   insert为新增，save为修改
	 * @param  organ  组织对象
	 * @param  organRelationInfoOid  关系表oid  在保存时为上级组织关系信息  在修改时为本级组织关系信息
	 * @param  organOrder  关系表排序
	 * @param  relation  关系表隶属关系
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganizationInfo(String flag,OrganizationInfo organ,String organRelationInfoOid,String organOrder,String relation){
		//判断是否是新增组织
		if(flag.equals("insert")){
			//新增组织
			//判断是否是新增的组织
			if(organ.getId()==null||"".equals(organ.getId().trim())){
				//新增的组织
				//此处如果organ的oid不是null，就会执行update操作，而不会增，oid也为空字符串，在级联保存时会有问题，所以给设置为null
				organ.setId(null);
				insertOrganizationInfo(organ, organRelationInfoOid, organOrder, relation);
			}else{
				//关联新上级的组织
				insertOrganizationInfo(organ, organRelationInfoOid, Integer.parseInt(organOrder), relation);
			}
		}else if(flag.equals("save")){
			//保存 修改组织信息
			saveOrganizationInfo(organ, organRelationInfoOid, organOrder, relation);
		}
	}
	/**
	 * 保存新增的组织功能方法
	 * 被哪些方法调用:
	 * 1、saveOrganizationInfo
	 * @date 2010-11-29
	 * @author wangxx
	 * @param organ  组织信息对象
	 * @param organRelationInfoOid  上级组织关系信息oid
	 * @param organOrder  组织顺序
	 * @param relation  组织隶属关系
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertOrganizationInfo(OrganizationInfo organ,String organRelationInfoOid,String organOrder,String relation){
		//封装创建时间
		organ.setCreateDate(new Timestamp(System.currentTimeMillis()));
		//判断是否有效
		if("true".equals(organ.getInvalidFlag())){
			//有效
			organ.setInvalidFlag("1");
		}else{
			//无效
			organ.setInvalidFlag("2");
			//存储无效时间
			organ.setInvalidDate(new Timestamp(System.currentTimeMillis()));
		}
		//是否本级机构
		if("true".equals(organ.getCurrentOrgan())){
			organ.setCurrentOrgan("1");
		}else{
			organ.setCurrentOrgan("2");
		}
		//保存组织信息
		organizationInfoDao.save(organ);
		//获取上级组织关系信息
		OrganizationRelationInfo pori = organizationRelationInfoDao.get(organRelationInfoOid);
		//上组织信息对象
		OrganizationInfo pOrganizationInfo = pori.getOrganizationInfo();
		//上级所有组织关系对象
		Set<OrganizationRelationInfo> set = pOrganizationInfo.getOrganizationRelationInfo();
		Iterator<OrganizationRelationInfo> it = set.iterator();
		while (it.hasNext()) {
			OrganizationRelationInfo ppori = it.next();
			//创建本级组织关系信息
			OrganizationRelationInfo ori = new OrganizationRelationInfo();
			//封装本级组织的组织关系对象
			ori.setRelation(relation);
			ori.setOrganOrder(Integer.parseInt(organOrder));
			ori.setOrganizationInfo(organ);
			ori.setOrganizationRelationInfo(ppori);
			//保存本级组织关系对象信息
			organizationRelationInfoDao.save(ori);
		}
		
	}
	/**
	 * 修改组织信息后的保存功能
	 * @date 2010-11-29
	 * @author wangxx
	 * @param organ  组织信息
	 * @param organRelationInfoOid 本级组织关系信息
	 * @param organOrder 组织排序
	 * @param relation  组织隶属关系
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganizationInfo(OrganizationInfo organ,String organRelationInfoOid,String organOrder,String relation){
		//封装修改时间
		organ.setModifyDate(new Timestamp(System.currentTimeMillis()));
		//判断是否有效
		if(organ.getInvalidFlag().equals("true")){
			//有效
			organ.setInvalidFlag("1");
		}else{
			//无效
			organ.setInvalidFlag("2");
			//存储无效时间
			organ.setInvalidDate(new Timestamp(System.currentTimeMillis()));
		}
		//是否本级机构
		if(organ.getCurrentOrgan().equals("true")){
			organ.setCurrentOrgan("1");
		}else{
			organ.setCurrentOrgan("2");
		}
		//保存组织信息
		organizationInfoDao.save(organ);
		//获取本级组织对象
		OrganizationRelationInfo ori = organizationRelationInfoDao.get(organRelationInfoOid);
		//封装本级组织的组织关系对象
		ori.setRelation(relation);
		ori.setOrganOrder(Integer.parseInt(organOrder));
		ori.setOrganizationInfo(organ);
		//保存本级组织关系对象信息
		organizationRelationInfoDao.save(ori);
	}
	/**
	 * 新增组织信息
	 * @date 2010-11-29
	 * @author wangxx
	 * 保存新增的组织信息，其中组织信息是将已经存在的组织关联过来
	 * 		①需要判断该组织的需要关联的上级组织是否在组织树上出现多次，如果出现多次，就需要都关联上
	 * 		②遍历下级组织，将下级组织作出关联
	 * @param organ 组织信息对象
	 * @param organRelationInfoOid  上级组织关系对象oid
	 * @param organOrder  组织顺序
	 * @param relation  组织隶属关系
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertOrganizationInfo(OrganizationInfo organ,String organRelationInfoOid,int organOrder,String relation){
		//判断是否有效
		if(organ.getInvalidFlag().equals("true")||organ.getInvalidFlag().equals("1")){
			//有效
			organ.setInvalidFlag("1");
		}else{
			//无效
			organ.setInvalidFlag("2");
			//存储无效时间
			organ.setInvalidDate(new Timestamp(System.currentTimeMillis()));
		}
		//是否本级机构
		if(organ.getCurrentOrgan().equals("true")||organ.getCurrentOrgan().equals("1")){
			organ.setCurrentOrgan("1");
		}else{
			organ.setCurrentOrgan("2");
		}
		//保存组织信息
		organizationInfoDao.save(organ);
		//获取上级组织关系信息
		OrganizationRelationInfo pori = organizationRelationInfoDao.get(organRelationInfoOid);
		Set<OrganizationRelationInfo> set = pori.getOrganizationInfo().getOrganizationRelationInfo();
		Iterator<OrganizationRelationInfo> it = set.iterator();
		while(it.hasNext()){
			pori = it.next();
			//创建本级组织关系信息
			OrganizationRelationInfo ori = new OrganizationRelationInfo();
			//封装本级组织的组织关系对象
			ori.setRelation(relation);
			ori.setOrganOrder(organOrder);
			ori.setOrganizationInfo(organ);
			ori.setOrganizationRelationInfo(pori);
			//保存组织关系信息
			organizationRelationInfoDao.save(ori);
			//保存组织信息和组织关系信息
			saveOrganizationInfo(organ, ori);
		}
	}
	
	/**
	 * 保存组织信息
	 * @date 2010-11-28
	 * @author wangxx
	 * 将组织对象下的组织都关联到关系组织对象下
	 * 1、保存本级关系
	 * 2、通过组织对象查找其下级组织关系对象 ，遍历集合保存到Set集合中去除重复
	 * 3、遍历Set，并保存，保存之后需要递归此方法
	 * @param organ  组织对象
	 * @param organRelation  组织关系对象
	 * @return 空
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrganizationInfo(OrganizationInfo organ,OrganizationRelationInfo organRelation){
		//首先通过组织对象查找其下级组织关系对象  
		List<OrganizationRelationInfo> oriList = organizationRelationInfoDao.getOrganizationRelationInfoByParentOrgOid(organ.getId());
		//获取下级组织对象 用set去除重复
		Set<OrganizationInfo> set = new HashSet<OrganizationInfo>();
		//遍历组织关系对象，用set去除重复
		for (int i = 0; i < oriList.size(); i++) {
			set.add(oriList.get(i).getOrganizationInfo());
		}
		Iterator<OrganizationInfo> it = set.iterator();
		while(it.hasNext()){
			OrganizationInfo organizationInfo = it.next();
			OrganizationRelationInfo addOrganizationRelationInfo = new OrganizationRelationInfo();
			addOrganizationRelationInfo.setOrganizationInfo(organizationInfo);
			addOrganizationRelationInfo.setOrganizationRelationInfo(organRelation);
			//通过本级组织对象和下级组织对象可以获取下级组织已经存在的关系对象集合，用这个集合取出已经存在的组织顺序和隶属关系
			List<OrganizationRelationInfo> list = organizationRelationInfoDao.getOrganizationRelationInfoListByParentOrgOidAndOrnOid(organ.getId(), organizationInfo.getId());
			if (list.size()>0) {
				addOrganizationRelationInfo.setOrganOrder(list.get(0).getOrganOrder());
				addOrganizationRelationInfo.setRelation(list.get(0).getRelation());
			}else{
				addOrganizationRelationInfo.setOrganOrder(1);
				addOrganizationRelationInfo.setRelation("1");
			}
			organizationRelationInfoDao.save(addOrganizationRelationInfo);
			saveOrganizationInfo(organizationInfo, addOrganizationRelationInfo);
		}
	}
	
	/**
	 * 同时判断多个组织属性是否存在是否存在
	 * 用途：
	 *  1、在组织拆分时，判断多个组织英文名和组织编码是否已存在
	 * @param filterTxt 组织基本信息属性名
	 * @param filterValues 组织基本信息属性值
	 * @return true存在，false不存在
	 */
	public String judgeOrganizations(String filterTxt,String filterValue){
		String[] filterValues = filterValue.split(";");
		int size = organizationRelationInfoDao.judgeOrganizationInfos(filterTxt, filterValues);
		String flag = "true";
		if(size==0){
			flag = "false";
		}
		return flag;
	}

	/**
	 * 通过对象属性查找组织基本信息
	 * @date 2010-10-28
	 * @author wangxx
	 * 用途：
	 * 	1、平煤组高俊提出的需求
	 * @return 如果存在返回json，如果不存在返回null
	 */
	@Transactional(readOnly=true)
	public String getOrganization(String text,String value){
		List<OrganizationInfo> list = organizationInfoDao.getOrganization(text, value);
		StringBuffer json = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i>0){
				json.append(",");
			}
			OrganizationInfo organizationInfo = list.get(i);
			json.append("{");
			json.append("currentOrgan:'"+(organizationInfo.getCurrentOrgan()==null?"":organizationInfo.getCurrentOrgan())+"'");
			json.append(",description:'"+(organizationInfo.getDescription()==null?"":organizationInfo.getDescription())+"'");
			json.append(",id:'"+(organizationInfo.getId()==null?"":organizationInfo.getId())+"'");
			json.append(",invalidFlag:'"+(organizationInfo.getInvalidFlag()==null?"":organizationInfo.getInvalidFlag())+"'");
			json.append(",orgCname:'"+(organizationInfo.getOrgCname()==null?"":organizationInfo.getOrgCname())+"'");
			json.append(",orgCode:'"+(organizationInfo.getOrgCode()==null?"":organizationInfo.getOrgCode())+"'");
			json.append(",orgEname:'"+(organizationInfo.getOrgEname()==null?"":organizationInfo.getOrgEname())+"'");
			json.append(",orgShortCname:'"+(organizationInfo.getOrgShortCname()==null?"":organizationInfo.getOrgShortCname())+"'");
			json.append(",orgShortEname:'"+(organizationInfo.getOrgShortEname()==null?"":organizationInfo.getOrgShortEname())+"'");
			json.append(",purpose:"+(organizationInfo.getPurpose()==null?"":organizationInfo.getPurpose())+"'");
			json.append(",createDate:'"+(organizationInfo.getCreateDate() == null ? "" : organizationInfo.getCreateDate().toString().substring(0, 10))+"'");
			json.append(",invalidDate:'"+(organizationInfo.getInvalidDate() == null ? "" : organizationInfo.getInvalidDate().toString().substring(0, 10))+"'");
			json.append(",modifyDate:'"+(organizationInfo.getModifyDate() == null ? "" : organizationInfo.getModifyDate().toString().substring(0, 10))+"'");
			json.append("}");
		}
		return json.toString();
	}
}
