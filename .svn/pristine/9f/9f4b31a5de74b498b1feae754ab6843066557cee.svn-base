package com.sinosoft.authority.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDataDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.dao.SystemAuthorityInfoDao;
import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.authority.manager.AuthorityDataManager;
import com.sinosoft.authority.manager.AuthorityUserRoleManager;
import com.sinosoft.authority.util.RestrictionsExt;
import com.sinosoft.databasetable.dao.DatabaseFieldInfoDao;
import com.sinosoft.databasetable.dao.DatabaseTableInfoDao;
import com.sinosoft.databasetable.domain.DatabaseFieldInfo;
import com.sinosoft.databasetable.domain.DatabaseTableInfo;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoft.systemcode.dao.SystemCodeNodeInfoDao;
import com.sinosoft.systemcode.domain.SystemCodeInfo;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoft.systemcode.manager.SystemCodeInfoManager;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.RoleStr;
import com.sinosoft.xf.util.AuthorityUtil;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 系统权限信息manager
 * @date 2012-01-28
 * @author sunzhe 
 */
@Service
@Transactional
public class SystemAuthorityInfoManager extends EntityManager<SystemCodeNodeInfo,String>{

	/**
	 * 注入代码信息dao层
	 */
	@Autowired
	private SystemAuthorityInfoDao systemAuthorityInfoDao;
	@Autowired
	private SystemCodeNodeInfoDao systemCodeNodeInfoDao;
		
	//注入人员角色关系业务层
	@Autowired
	AuthorityUserRoleManager authoirtyUserRoleManager;

	//注入人员角色关系业务层
	@Autowired
	PersonInfoManager personInfoManager;
	
	//注入人员角色关系业务层
	@Autowired
	AuthorityDataManager authorityDataManager;
	
	//注入数据权限信息dao层
	@Autowired
	AuthorityDataDao authorityDataDao;
	
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	
	//注入库表信息dao层
	@Autowired
	DatabaseTableInfoDao databaseTableInfoDao;
	
	//注入字段信息dao层
	@Autowired
	DatabaseFieldInfoDao databaseFieldInfoDao;
		
	/**
	 * 注入系统代码类别信息dao层
	 */
	@Autowired
	private SystemCodeInfoManager systemCodeInfoManager;
	/**
	 * 注入系统代码信息dao层
	 */
	@Autowired
	private SystemCodeNodeInfoDao syscniDao;
	
	@Override
	protected HibernateDao<SystemCodeNodeInfo, String> getEntityDao() {
		// TODO Auto-generated method stub
		return systemAuthorityInfoDao;
	}

	/**
	 * 根据条件查找系统码表对象,只查找有效的
	 * @param propertyName 字段名称
	 * @param value  查询值
	 * @param exceptCode  排除字段
	 * @return 系统码表List
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<SystemCodeNodeInfo> getSystemAuthorityCodeNode(
			String propertyName, Object value,String exceptCode,OnlineUser onlineUser) throws ParseException {		//先从缓存中读取信息,如果缓存中无代码信息，则从数据库中查询
		List<AuthorityUserRole> userRoleList = AuthorityUtil.userRoleCacheMap.get(onlineUser.getUserId());
		String[] values = value.toString().split(";");
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		if(userRoleList==null || userRoleList.size()==0){
			userRoleList = authoirtyUserRoleManager.getInvalidRoleByUserId(onlineUser.getUserId());
			AuthorityUtil.userRoleCacheMap.put(onlineUser.getUserId(), userRoleList);
		}
		//得到人员角色信息,通常是一个,也有多个的情况
		Criteria criteria = systemAuthorityInfoDao.createCriteria();
		String restrictValue = "";
		for(AuthorityUserRole userRole : userRoleList){
			AuthorityRole role = userRole.getRole();
			//根据角色信息得到该代码类别下的数据权限
			//先从缓存中读取信息,如果缓存中无代码信息，则从数据库中查询
			for(int i=0;i<values.length;i++){
				List<AuthorityData> dataList = AuthorityUtil.codeAuthCacheMap.get(role.getId()+values[i]);
				if(dataList==null || dataList.size()==0){
					dataList = systemAuthorityInfoDao.getCodeRestrictValue(role.getId(),values[i]);
					AuthorityUtil.codeAuthCacheMap.put(role.getId()+values[i],dataList);
				}
				for(AuthorityData data : dataList){
					restrictValue += data.getAddRestrictValue();
				}
			}
		}
		//如果为空，说明没有控制该类别的代码，默认返回所有。
		if(!restrictValue.equals(""))
			criteria.add(Restrictions.in("code", restrictValue.split(";")));
		if(!exceptCode.equals("")){
			criteria.add(Restrictions.not(Restrictions.in("code", exceptCode.split(","))));
		}
		//只查询有效代码
		criteria.add(Restrictions.eq("available", "Y"));
		criteria.addOrder(Order.asc("code"));
		List<SystemCodeNodeInfo> codeList = new ArrayList<SystemCodeNodeInfo>();
		for(int i=0;i<values.length;i++){
			List<SystemCodeNodeInfo> listz = AuthorityUtil.codeCacheMap.get(restrictValue+exceptCode+propertyName+values[i]);
			if(listz==null || listz.size()==0){
				criteria.add(Restrictions.eq(propertyName, values[i]));
				listz = criteria.list();
				AuthorityUtil.codeCacheMap.put(restrictValue+exceptCode+propertyName+values[i], listz);
			}
			//对代码值进行处理，从一期导入的数据，会带有空格，需去掉
			for(SystemCodeNodeInfo node : listz){
				//信访来源下拉框数据
				if("XFLY".equals(value)){
					if(node.getCode().trim().length() != 2){
						node.setCode(node.getCode().trim());
						codeList.add(node);
					}
				}else{
					if(node.getCodeProperty().equals("SelfUse")){
						if(personInfo.getRegionCode().equals(node.getOrgCode())){
							node.setCode(node.getCode().trim());
							codeList.add(node);
						}else if(personInfo.getRegionCode().equals("310000000000")&&node.getOrgCode()==null){
							node.setCode(node.getCode().trim());
							codeList.add(node);
						}
					}else{
						node.setCode(node.getCode().trim());
						codeList.add(node);
					}
				}
			}
		}
		return codeList;
}
	/**
	 * 根据条件查找系统码表对象,只查找有效的
	 * @param propertyName 字段名称
	 * @param value  查询值
	 * @param exceptCode  排除字段
	 * @return 系统码表List
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<SystemCodeNodeInfo> getSystemAuthorityCodeNodeToAvailable(
			String propertyName, Object value,String exceptCode,OnlineUser onlineUser,Map<String,Object> map) throws ParseException {		//先从缓存中读取信息,如果缓存中无代码信息，则从数据库中查询
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");

		//得到人员角色信息,通常是一个,也有多个的情况
		Criteria criteria = systemCodeNodeInfoDao.createCriteria();

		//如果为空，说明没有控制该类别的代码，默认返回所有。
		criteria.add(Restrictions.eq("codeType", value));
		if(map.get("available")!=null&&map.get("available").toString().equals("N")){
			criteria.add(Restrictions.eq("available", "N"));
		}else {
			//只查询有效代码
			criteria.add(Restrictions.eq("available", "Y"));
		}
		if(map.get("Order")==null||!map.get("Order").toString().equals("N")){
			criteria.addOrder(Order.asc("code"));
		}
		List<SystemCodeNodeInfo> codeList = new ArrayList<SystemCodeNodeInfo>();
		List<SystemCodeNodeInfo> listz = criteria.list();
		//对代码值进行处理，从一期导入的数据，会带有空格，需去掉
		for(SystemCodeNodeInfo node : listz){
			if(node.getCodeProperty().equals("SelfUse")){
				if(personInfo.getRegionCode().equals(node.getOrgCode())){
					node.setCode(node.getCode().trim());
					codeList.add(node);
				}else if(personInfo.getRegionCode().equals("310000000000")&&node.getOrgCode()==null){
					node.setCode(node.getCode().trim());
					codeList.add(node);
				}
			}else{
				node.setCode(node.getCode().trim());
				codeList.add(node);
			}
		}
		return codeList;
	}
	
	/**
	 * 根据代码类别，构建树形展示所需要的数据
	 * 展示该类别下级代码
	 * @param nodeId 当前树节点
	 * @param type 代码类别
	 * @param singleSelect 是否单元
	 * @return json 树形展示所需要的json
	 */
	@Transactional(readOnly = true)
	public String buildCodeTreeByType(
			String nodeId, String type, boolean singleSelect,String exceptCode) {
		Map<String, Object> values = new HashMap<String, Object>();
		List<SystemCodeNodeInfo> list = null;
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());//获取在线用户信息
		//判断当前树节点
		if ("-1".equals(nodeId)) {
			values.put("codeType", type);
			values.put("exceptCode", exceptCode.split(","));
			//先从缓存中提取代码信息，如果没有信息，再从数据库中读取并放入缓存中
		    if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = AuthorityUtil.codeCacheMap.get(type+exceptCode+values.get("orgCode").toString());
		    }
			if(list==null || list.size()==0){
			    if(onlineUser!=null){
					values.put("orgCode", onlineUser.getRegionCode());
					list = syscniDao.find("FROM SystemCodeNodeInfo"
			                + " WHERE (parentId is null or parentId='') and (orgCode=:orgCode or orgCode is null) and available ='Y' and codeType=:codeType and code not in (:exceptCode) order by sequence,code", values);
				}else{
		            list = syscniDao.find("FROM SystemCodeNodeInfo"
		                + " WHERE (parentId is null or parentId='') and available ='Y' and codeType=:codeType and code not in (:exceptCode) order by sequence,code", values);
				}
	            if(!"GJZ".equals(type))
	            	AuthorityUtil.codeCacheMap.put(type+exceptCode+values.get("orgCode").toString(),list);
			}
		} else if (systemCodeInfoManager.findCodeType(nodeId) > 0) {
			values.put("codeType", type);
			values.put("systemCodeId", nodeId);
			values.put("exceptCode", exceptCode.split(","));
			if(onlineUser!=null){
			     values.put("orgCode", onlineUser.getRegionCode());
			//先从缓存中提取代码信息，如果没有信息，再从数据库中读取并放入缓存中
			list = AuthorityUtil.codeCacheMap.get(type+nodeId+exceptCode+values.get("orgCode").toString());
			if(list==null || list.size()==0){
		          list = syscniDao.find("FROM SystemCodeNodeInfo WHERE"
							+ " (parentId is null or parentId='')"
		                    + " and systemcode.id=:systemCodeId and available ='Y' and (orgCode=:orgCode or orgCode is null) and codeType=:type  and code not in (:exceptCode) order by sequence,code", values);
		          AuthorityUtil.codeCacheMap.put(type+nodeId+exceptCode+values.get("orgCode").toString(),list);
			    }
			}
		} else {
			//先从缓存中提取代码信息，如果没有信息，再从数据库中读取并放入缓存中
			if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = AuthorityUtil.codeCacheMap.get(nodeId+values.get("orgCode").toString());
				if(list==null || list.size()==0){
				//list = syscniDao.findBy("parentId", nodeId);
				  values.put("parentId",nodeId);
				  list = syscniDao.find("FROM SystemCodeNodeInfo WHERE parentId=:parentId and  (orgCode=:orgCode or orgCode is null) and available ='Y' order by sequence,code", values);
				  AuthorityUtil.codeCacheMap.put(nodeId+values.get("orgCode").toString(),list);
				 }
			}
		}
		//拼装ＪＳＯＮ数据
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		SystemCodeNodeInfo sci;
		for (int i = 0; i < list.size(); i++) {
			sci = list.get(i);
			if (!firstItem) {
				treeJson.append(',');
			}
			treeJson.append("{id:'");
			treeJson.append(sci.getId());
			treeJson.append("',text:'");
			treeJson.append(sci.getName());
			treeJson.append("',codeType:'");
			treeJson.append(sci.getCodeType());
			treeJson.append("',systemCodeId:'");
			treeJson.append(sci.getSystemcode().getId());
			treeJson.append("',code:'");
			treeJson.append(sci.getCode());
			treeJson.append("',parentId:'");
			treeJson.append(sci.getParentId());
			//判断是否是叶子节点
			treeJson.append("',leaf:");
			treeJson.append(checkLeaf(null, sci));
			treeJson.append(",allowDelete:true");
			if (!singleSelect){
				treeJson.append(",checked:false");
			}
			treeJson.append('}');
			if (firstItem) {
				firstItem = false;
			}
		}
		treeJson.append(']');
		return treeJson.toString();
	}
	
	/**
	 * 一次性加载全部的问题类别tree
	 * @author xuyi
	 * @date 2013-10-09
	 * 调用那些方法：
	 * 1、syscniDao.findBy()
	 * 被哪些方法调用：
	 * 1、getSystemCodeListByType()
	 * 		根据代码类别，得到代码信息，用于树状显示
	 * @param nodeId 节点id
	 * @param type code类型
	 * @param singleSelect 选择模式
	 * @param exceptCode 不查询的类别
	 * @return 问题类别的json字符串
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String buildIssueTypeCodeTrueOnce(
			String nodeId, String type, boolean singleSelect,String exceptCode){
		Map<String, Object> values = new HashMap<String, Object>();
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());//获取在线用户信息
		
		List<SystemCodeNodeInfo> list = null;
		List<SystemCodeNodeInfo> listLeaf = null;
		if ("-1".equals(nodeId)) {
			values.put("codeType", type);
			values.put("exceptCode", exceptCode.split(","));
			if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = syscniDao.find("FROM SystemCodeNodeInfo"
		                + " WHERE (parentId is null or parentId='') and  (orgCode=:orgCode or orgCode is null) and codeType=:codeType and code not in (:exceptCode) order by sequence,code", values);
		           
			}else{
				list = syscniDao.find("FROM SystemCodeNodeInfo"
		                + " WHERE (parentId is null or parentId='') and codeType=:codeType and code not in (:exceptCode) order by sequence,code", values);
		           
			}
            
            AuthorityUtil.codeCacheMap.put(type+exceptCode,list);
		} else if (systemCodeInfoManager.findCodeType(nodeId) > 0) {
			values.put("codeType", type);
			values.put("systemCodeId", nodeId);
			values.put("exceptCode", exceptCode.split(","));
			if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = syscniDao.find("FROM SystemCodeNodeInfo WHERE"
						+ " (parentId is null or parentId='')"
	                    + " and systemcode.id=:systemCodeId and codeType=:codeType and (orgCode=:orgCode or orgCode is null) and code not in (:exceptCode) order by sequence,code", values);
			}else{
				list = syscniDao.find("FROM SystemCodeNodeInfo WHERE"
						+ " (parentId is null or parentId='')"
	                    + " and systemcode.id=:systemCodeId and codeType=:codeType  and code not in (:exceptCode) order by sequence,code", values);
			}
            
		} else {
			//list = syscniDao.findBy("parentId", nodeId);
			values.put("parentId",nodeId);
			if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = syscniDao.find("FROM SystemCodeNodeInfo WHERE parentId=:parentId and (orgCode=:orgCode or orgCode is null) order by sequence,code", values);
			}else{
				list = syscniDao.find("FROM SystemCodeNodeInfo WHERE parentId=:parentId order by sequence,code", values);
			}
			AuthorityUtil.codeCacheMap.put(nodeId,list);
		}
		//拼装ＪＳＯＮ数据
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		SystemCodeNodeInfo sci,leafNode;
		for (int i = 0; i < list.size(); i++) {
			sci = list.get(i);
			if("Y".equals(sci.getAvailable())){
				if (!firstItem) {
					treeJson.append(',');
				}
				treeJson.append("{id:'");
				treeJson.append(sci.getId());
				treeJson.append("',text:'");
				treeJson.append(sci.getName());
				treeJson.append("',codeType:'");
				treeJson.append(sci.getCodeType());
				treeJson.append("',systemCodeId:'");
				treeJson.append(sci.getSystemcode().getId());
				treeJson.append("',code:'");
				treeJson.append(sci.getCode());
				treeJson.append("',parentId:'");
				treeJson.append(sci.getParentId());
				//判断是否是叶子节点
				treeJson.append("',leaf:");
				boolean checkLeaf = checkLeaf(null, sci);
				treeJson.append(checkLeaf);
				treeJson.append(",allowDelete:true");
				if (!singleSelect){
					treeJson.append(",checked:false");
				}
				
				if(!checkLeaf){
					//listLeaf = syscniDao.findBy("parentId", sci.getId());
					Criteria leafCriteria = syscniDao.createCriteria(Restrictions.eq("parentId", sci.getId()));
					leafCriteria.addOrder(Order.asc("sequence"));
					leafCriteria.addOrder(Order.asc("code"));
					listLeaf = leafCriteria.list();
					AuthorityUtil.codeCacheMap.put(nodeId,listLeaf);
					treeJson.append(",allowChildren:true");
					treeJson.append(",children:[");
					boolean firstLeaf = true;
					for (int leaf = 0; leaf < listLeaf.size(); leaf++) {
						leafNode = listLeaf.get(leaf);
						if("Y".equals(leafNode.getAvailable())){
							if(!firstLeaf){
								treeJson.append(',');
							}
							treeJson.append("{id:'");
							treeJson.append(leafNode.getId());
							treeJson.append("',text:'");
							treeJson.append(leafNode.getName());
							treeJson.append("',codeType:'");
							treeJson.append(leafNode.getCodeType());
							treeJson.append("',systemCodeId:'");
							treeJson.append(leafNode.getSystemcode().getId());
							treeJson.append("',code:'");
							treeJson.append(leafNode.getCode());
							treeJson.append("',parentId:'");
							treeJson.append(sci.getParentId());
							//判断是否是叶子节点
							treeJson.append("',leaf:");
							treeJson.append(checkLeaf(null, leafNode));
							treeJson.append(",allowDelete:true");
							if (!singleSelect){
								treeJson.append(",checked:false");
							}
							treeJson.append('}');
							if(firstLeaf){
								firstLeaf = false;
							}
						}
					}
					treeJson.append("]");
				}
				treeJson.append('}');
				if (firstItem) {
					firstItem = false;
				}
			}
		}
		treeJson.append(']');
		return treeJson.toString();
	}
	/**
	 * 获取当前登录人的角色，并判断是否为“问题线索管理”，当不为此角色时，拼接信访来源的过滤串
	 * @throws ParseException 
	 * @date 20170626
	 * @param basicName 用来标识sql中拼接的基本表是别名 还是表名,listPetitinoBasicInfo()、simpleQuery()方法内使用的basic;判重查询findXFInfoByRule()方法内使用：petition_basic_info；
	 */
	public String getDefultQueryFilter(String basicName) throws ParseException{
		//获取在线用户信息
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		List<AuthorityUserRole> userRoleList  = authoirtyUserRoleManager.getInvalidRoleByUserId(onlineUser.getUserId());
		boolean isXSGLRY = false;//一个角色或者多个角色，只要有一个角色为“问题线索管理”角色，就可以查看“问题线索移送”的来源类型，默认不是“问题线索管理”角色
		for (AuthorityUserRole userRole : userRoleList) {
			AuthorityRole role = userRole.getRole();
			if(role.getRoleName()!=null&&"问题线索管理".equals(role.getRoleName())){
				isXSGLRY =true;
			}
		}
		String defultQueryFilter ="";
		String codes ="";
		//从缓存中取出登录时查询的信访来源为“问题线索移送”的code拼接字符串
		HttpSession session = Struts2Utils.getSession();
		if(session.getAttribute("petitionSourceCodesForClue")!=null){
			codes = session.getAttribute("petitionSourceCodesForClue").toString();
		}else{
			//20170701 这里是静态数据，如“问题线索移送”码表相关记录有改变，需及时修改：‘select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送');’
			codes = " ('31000020170620053531','31000020170620053929','31000020170620053948','31000020170620054108','31000020170620054128','31000020170620054143','31000020170620054155') ";
		}
		if(!isXSGLRY){//如果为true，至少有一个角色为线索管理人员;不是问题线索管理角色时 进行信访来源过滤，不查“问题线索移送”的来源类型。
			defultQueryFilter += " and "+basicName+".PETITION_SOURCE_CODE not in "+codes+" ";
		}
		return defultQueryFilter;
	}
	/**
	 * 获取“问题线索移送”类型的信访来源code拼接的sql in 集合
	 * @throws ParseException 
	 * @date 20170626
	 * @param basicName 用来标识sql中拼接的基本表是别名 还是表名,listPetitinoBasicInfo()、simpleQuery()方法内使用的basic;判重查询findXFInfoByRule()方法内使用：petition_basic_info；
	 */
	public String getClueSourceCodes() throws ParseException{
		String hql = "select code from SystemCodeNodeInfo where parentId =(select id from SystemCodeNodeInfo where name = '问题线索移送')";
		Query query = systemCodeNodeInfoDao.getSession().createQuery(hql);
		List<String> result = query.list();
		String codes ="(";
		if(result!=null&&result.size()>0){
			for(String item : result){
				codes+="'"+item+"',";
			}
			codes = codes.substring(0,codes.length()-1);
		}
		codes+=")";
		return codes;
	}
	/**
	 * 根据代码类别、及角色权限，构建树形展示所需要的数据
	 * 展示该类别下级代码
	 * @param nodeId 当前树节点
	 * @param type 代码类别
	 * @param singleSelect 是否单元
	 * @return json 树形展示所需要的json
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public String buildCodeTreeByTypeAndAuthority(String nodeId, String type, boolean singleSelect,String exceptCode) throws ParseException{
		//最终json容器
		Map<String, Object> values = new HashMap<String, Object>();
		//获取在线用户信息
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		// 先从缓存中读取信息,如果缓存中无代码信息，则从数据库中查询
		HttpSession session = Struts2Utils.getSession();
		List<AuthorityUserRole> userRoleList = (List<AuthorityUserRole>) session.getAttribute(onlineUser.getUserId());
		if(userRoleList==null || userRoleList.size()==0){
			userRoleList = authoirtyUserRoleManager.getInvalidRoleByUserId(onlineUser.getUserId());
			session.setAttribute(onlineUser.getUserId(), userRoleList);
		}
		// 得到人员角色信息,通常是一个,也有多个的情况
		Criteria criteria = systemAuthorityInfoDao.createCriteria();
		criteria.createAlias("systemcode", "systemcode", Criteria.LEFT_JOIN);
		StringBuffer restrictValue = new StringBuffer();
		boolean isEmpty = false;//一个角色或者多个角色，只要有一个角色没有设置信访方式权限，默认返回所有信访方式
		boolean isXSGLRY = false;//一个角色或者多个角色，只要有一个角色为“问题线索管理”，就可以查看线索来源，默认不是“线索管理人员”
		for (AuthorityUserRole userRole : userRoleList) {
			AuthorityRole role = userRole.getRole();
			if(role.getRoleName()!=null&&"问题线索管理".equals(role.getRoleName())){
				isXSGLRY =true;
			}
			//用角色ID与代码类型 作为key 保存 数据权限 的缓存（例如：信访室秘书id+XFLY）
			List<AuthorityData> dataList = (List<AuthorityData>) session.getAttribute(role.getId() + type);
			if (dataList == null || dataList.size() == 0) {
				dataList = systemAuthorityInfoDao.getCodeRestrictValue(role.getId(), type);
				if(dataList == null || dataList.size() == 0){
					isEmpty = true;
				}
				session.setAttribute(role.getId() + type, dataList);
			}
			for (AuthorityData data : dataList) {
				restrictValue.append(data.getAddRestrictValue());
			}
		}
		if(isEmpty){//如果为true，至少有一个角色没有信访方式权限
			restrictValue.setLength(0);
		}
		String defultQueryFilter ="";
		if(!isXSGLRY){//如果为true，至少有一个角色为线索管理人员;不是“问题线索管理”角色时 进行信访来源过滤，不查线索相关来源。
			defultQueryFilter += " and code not in (select code from SystemCodeNodeInfo where id = (select id from SystemCodeNodeInfo where name = '问题线索移送') or parentId =(select id from SystemCodeNodeInfo where name = '问题线索移送')) ";
		}
		// 如果为空，说明没有控制该类别的代码，默认返回所有。
		if (restrictValue != null && !restrictValue.toString().equals(""))
			criteria.add(Restrictions.in("code", restrictValue.toString().split(";")));
		if (exceptCode != null && !exceptCode.equals("")) {
			criteria.add(Restrictions.not(Restrictions.in("code",exceptCode.split(","))));
		}
		// 只查询有效代码
		criteria.add(Restrictions.eq("available", "Y"));
		//添加代码属性的条件
		criteria.addOrder(Order.asc("sequence"));
		criteria.addOrder(Order.asc("code"));
		criteria.add(Restrictions.eq("codeType", type));
		List<SystemCodeNodeInfo> listz = criteria.list();
		String inAuthorityCodes ="";
		if (restrictValue != null && !restrictValue.toString().equals("")){
			//声明一个set用来存放所有牵涉到的节点信息
			Set<SystemCodeNodeInfo> nodes = new HashSet<SystemCodeNodeInfo>();
			for(SystemCodeNodeInfo scni : listz){
				//首先将自己装进set
				nodes.add(scni);
				//当其有父节点时，将其父节点递归至无父节点的路径全部存入set
				getParentCodes(scni,nodes);
			}
			inAuthorityCodes += " and code in (";
			for(SystemCodeNodeInfo node : nodes){
				inAuthorityCodes += "'"+node.getCode()+"',";
			}
			if(','==(inAuthorityCodes.charAt(inAuthorityCodes.length()-1))){
				inAuthorityCodes =inAuthorityCodes.substring(0,inAuthorityCodes.length()-1);
			}
			inAuthorityCodes += ")";
		}
		//开始查询树结构
		List<SystemCodeNodeInfo> list = null;
		//判断当前树节点
		if ("-1".equals(nodeId)) {
			values.put("codeType", type);
			values.put("exceptCode", exceptCode.split(","));
			//先从缓存中提取代码信息，如果没有信息，再从数据库中读取并放入缓存中
		    if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = (List<SystemCodeNodeInfo>) session.getAttribute(onlineUser.getUserId() + restrictValue + type+exceptCode+values.get("orgCode").toString());
		    }
			if(list==null || list.size()==0){
			    if(onlineUser!=null){
					values.put("orgCode", onlineUser.getRegionCode());
					list = syscniDao.find("FROM SystemCodeNodeInfo"
			                + " WHERE (parentId is null or parentId='') and (orgCode=:orgCode or orgCode is null) and available ='Y' and codeType=:codeType and code not in (:exceptCode) "+ inAuthorityCodes + defultQueryFilter +" order by sequence,code", values);
				}else{
		            list = syscniDao.find("FROM SystemCodeNodeInfo"
		                + " WHERE (parentId is null or parentId='') and available ='Y' and codeType=:codeType and code not in (:exceptCode) "+ inAuthorityCodes + defultQueryFilter +" order by sequence,code", values);
				}
			    session.setAttribute(onlineUser.getUserId() + restrictValue + type+exceptCode+values.get("orgCode").toString(),list);
			}
		} else if (systemCodeInfoManager.findCodeType(nodeId) > 0) {//当节点在主码表存在时
			values.put("codeType", type);
			values.put("systemCodeId", nodeId);
			values.put("exceptCode", exceptCode.split(","));
			if(onlineUser!=null){
			     values.put("orgCode", onlineUser.getRegionCode());
			//先从缓存中提取代码信息，如果没有信息，再从数据库中读取并放入缓存中
			list = (List<SystemCodeNodeInfo>) session.getAttribute(onlineUser.getUserId() + restrictValue + type+nodeId+exceptCode+values.get("orgCode").toString());
			if(list==null || list.size()==0){
		          list = syscniDao.find("FROM SystemCodeNodeInfo WHERE"
							+ " (parentId is null or parentId='')"
		                    + " and systemcode.id=:systemCodeId and available ='Y' and (orgCode=:orgCode or orgCode is null) and codeType=:type  and code not in (:exceptCode) "+ inAuthorityCodes + defultQueryFilter +" order by sequence,code", values);
		          session.setAttribute(onlineUser.getUserId() + restrictValue + type+nodeId+exceptCode+values.get("orgCode").toString(),list);
			    }
			}
		} else {
			//先从缓存中提取代码信息，如果没有信息，再从数据库中读取并放入缓存中
			if(onlineUser!=null){
				values.put("orgCode", onlineUser.getRegionCode());
				list = (List<SystemCodeNodeInfo>) session.getAttribute(onlineUser.getUserId() + restrictValue + nodeId+values.get("orgCode").toString());
				if(list==null || list.size()==0){
				//list = syscniDao.findBy("parentId", nodeId);
					values.put("parentId",nodeId);
					list = syscniDao.find("FROM SystemCodeNodeInfo WHERE parentId=:parentId and  (orgCode=:orgCode or orgCode is null) and available ='Y' "+ inAuthorityCodes + defultQueryFilter +"order by sequence,code", values);
					session.setAttribute(onlineUser.getUserId() + restrictValue + nodeId+values.get("orgCode").toString(),list);
				}
			}
		}
		//拼装ＪＳＯＮ数据
		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;
		SystemCodeNodeInfo sci;
		for (int i = 0; i < list.size(); i++) {
			sci = list.get(i);
			if (!firstItem) {
				treeJson.append(',');
			}
			treeJson.append("{id:'");
			treeJson.append(sci.getId());
			treeJson.append("',text:'");
			treeJson.append(sci.getName());
			treeJson.append("',codeType:'");
			treeJson.append(sci.getCodeType());
			treeJson.append("',systemCodeId:'");
			treeJson.append(sci.getSystemcode().getId());
			treeJson.append("',code:'");
			treeJson.append(sci.getCode());
			treeJson.append("',parentId:'");
			treeJson.append(sci.getParentId());
			//判断是否是叶子节点
			treeJson.append("',leaf:");
			treeJson.append(checkLeaf(null, sci));
			treeJson.append(",allowDelete:true");
			if (!singleSelect){
				treeJson.append(",checked:false");
			}
			treeJson.append('}');
			if (firstItem) {
				firstItem = false;
			}
		}
		treeJson.append(']');
		return treeJson.toString();
	}
	/**
	 * 小递归
	 * 当scni节点有父节点时，将其父节点递归至无父节点的路径全部存入set
	 */
	private Set<SystemCodeNodeInfo> getParentCodes(SystemCodeNodeInfo scni,Set<SystemCodeNodeInfo> nodes){
		if(scni.getParentId()==null||"".equals(scni.getParentId())){
			return nodes;
		}else{
			SystemCodeNodeInfo parentNode = systemCodeNodeInfoDao.get(scni.getParentId());
			nodes.add(parentNode);
			getParentCodes(parentNode,nodes);
			return nodes;
		}
	}
	/**
	 * 为criteria查询方式添加权限, tableMap中存放的需要用到的权限表，
	 * map中有哪些表，返回的权限串就基于这些表拼接。
	 * 格式为  tableMap.put("表名","表别名"); 
	 * 例：tableMap.put("PETITION_BASIC_INFO", "basic");
	 * 	   tableMap.put("PETITION_ACCUSED_INFO", "accused");
	 * @param criteria 要查询的criteria
	 * @param tableMap 需要用到的权限关联表
	 * @return 添加好权限的criteria
	 */
	@Transactional(readOnly=true)
	public Criteria addAuth(Criteria criteria,Map<String,String> tableMap){
		return addAuth(criteria,tableMap,null);
	}
	/**
	 * 为criteria查询方式添加权限, tableMap中存放的需要用到的权限表，
	 * map中有哪些表，返回的权限串就基于这些表拼接。
	 * 格式为  tableMap.put("表名","表别名"); 
	 * 例：tableMap.put("PETITION_BASIC_INFO", "basic");
	 * 	   tableMap.put("PETITION_ACCUSED_INFO", "accused");
	 * @param criteria 要查询的criteria
	 * @param tableMap 需要用到的权限关联表
	 * @param dealType 信访室一般用户 办理方式
	 * @return 添加好权限的criteria
	 */
	@Transactional(readOnly=true)
	public Criteria addAuth(Criteria criteria,Map<String,String> tableMap,String[] dealType){
		String roleStr = (String) Struts2Utils.getSessionAttribute("roleStr");
		//得到当前登陆用户
		OnlineUser onLineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		PersonInfo personInfo = personInfoManager.get(onLineUser.getUserId());
		//业务管理员 什么都不加
		if(RoleStr.BUSINESS_ADMIN.toString().equals(roleStr)){
			return criteria;
		}//委领导  系统权限加选择的部门权限
		else if(RoleStr.LEADER_MAIN.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
			String deptInfo = personInfo.getUserDescription();
			if(deptInfo != null && !deptInfo.equals("")){
				criteria.add(Restrictions.in("currDeptCode", deptInfo.split(",")));
			}
		}//办信室  办信员 系统权限加自己办理的信 Petition_Circulation_State_Info.default_Dealer_Code 
		else if(RoleStr.BXS_GENERAL.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
			criteria.add(Restrictions.eq("circulationStateInfo.defaultDealerCode", personInfo.getUserEname()));
		}//办信室 秘书 ,领导 系统权限加本部门 Petition_Basic_Info.curr_Dept_Code
		else if(RoleStr.BXS_SECRETARY.toString().equals(roleStr) || RoleStr.BXS_LEADER.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
			criteria.add(Restrictions.eq("currDeptCode", Struts2Utils.getSessionAttribute("deptCode").toString()));
		}//案管室 办信员 系统权限加督办人是自己 Petition_Circulation_State_Info.default_Supervise_Code
		else if(RoleStr.AGS_GENERAL.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
			criteria.add(Restrictions.eq("circulationStateInfo.defaultSuperviseCode", personInfo.getUserEname()));
		}//案管室 秘书,领导 系统权限加his_dept_code有案管室
		else if(RoleStr.AGS_SECRETARY.toString().equals(roleStr) || RoleStr.AGS_LEADER.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
			criteria.add(Restrictions.like("hisDeptCode", "%"+Struts2Utils.getSessionAttribute("deptCode").toString()+"%"));
		}//信访室 办信员 系统权限加自己办理的信 Petition_Circulation_State_Info.default_Dealer_Code 
		else if(RoleStr.XFS_GENERAL.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
			criteria.add(Restrictions.eq("circulationStateInfo.defaultDealerCode", personInfo.getUserEname()));
			//办理方式
			//用户是信访室一般用户 传办理方式
			if(dealType != null)
				criteria.add(Restrictions.in("dealTypeCode", dealType));
		}//信访室 秘书,领导  系统权限
		else if(RoleStr.XFS_SECRETARY.toString().equals(roleStr) || RoleStr.XFS_LEADER.toString().equals(roleStr)){
			criteria = getAuthCriteria(criteria,tableMap,personInfo);
		}
		return criteria;
	}
	
	/**
	 * 系统权限 根据用户所拥有的角色处理后得到
	 * @param criteria
	 * @param tableMap
	 * @param personInfo
	 * @return
	 */
	private Criteria getAuthCriteria(Criteria criteria,Map<String,String> tableMap,PersonInfo personInfo){
		//将表map整理成数组形式
		String[] tables = new String[tableMap.size()];
		Set<String> tableSet = tableMap.keySet();
		Object[] tableObject =  tableSet.toArray();
		StringBuffer tableNames = new StringBuffer(personInfo.getId());
		for(int i=0;i<tableObject.length;i++){
			tables[i] = tableObject[i].toString();
			tableNames.append(tableObject[i].toString().toUpperCase());
		}
		//修改保存到session中
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		String sql=(String) session.getAttribute(tableNames.toString());
		//得到当前用户所拥有的权限串
		if(sql == null || sql.equals("")){
			sql = authorityDataManager.getMultiAuthoritySqlByTableWithTableName(personInfo, tables);
			session.setAttribute(tableNames.toString(), sql);
		}
		if(!sql.equals("")){
			//处理权限串，将表名转换为别名，否则hibernate criteria查询会报错
			for(String tableName : tables){
				String alias = tableMap.get(tableName);
				sql = sql.replace(tableName.toUpperCase()+".", "{"+alias+"}.");
			}
			//去掉and 防止转换sql时出现错误
			if(sql.startsWith(" and")){
				sql = sql.substring(sql.indexOf(" and")+4);
			}
			//将权限串添加进criteria中
			criteria.add(RestrictionsExt.sqlAliased(sql));
		}
		return criteria;
	}
	/**
	 * tableMap中存放的需要用到的权限表，isSql:true返回sql false:返回hql
	 * map中有哪些表，返回的权限串就基于这些表拼接。
	 * 格式为  tableMap.put("表名","表别名"); 
	 * 例：tableMap.put("PETITION_BASIC_INFO", "basic");
	 * 	   tableMap.put("PETITION_ACCUSED_INFO", "accused");
	 * @param tableMap 需要用到的权限关联表
	 * @return sql或hql权限串
	 */
	@Transactional(readOnly=true)
	public String addAuth(Map<String,String> tableMap,boolean isSql){
		return addAuth(tableMap,isSql,null);
	}
	/**
	 * tableMap中存放的需要用到的权限表，isSql:true返回sql false:返回hql
	 * map中有哪些表，返回的权限串就基于这些表拼接。
	 * 格式为  tableMap.put("表名","表别名"); 
	 * 例：tableMap.put("PETITION_BASIC_INFO", "basic");
	 * 	   tableMap.put("PETITION_ACCUSED_INFO", "accused");
	 * @param tableMap 需要用到的权限关联表
	 * @return sql或hql权限串
	 */
	@Transactional(readOnly=true)
	public String addAuth(Map<String,String> tableMap,boolean isSql,String[] dealType){
		String roleStr = (String) Struts2Utils.getSessionAttribute("roleStr");
		//得到当前登陆用户
		OnlineUser onLineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		PersonInfo personInfo = personInfoManager.get(onLineUser.getUserId());
		String sql = "";
		String addAuthSql = "";//根据人员角色附加的权限
		//业务管理员 什么都不加
		if(RoleStr.BUSINESS_ADMIN.toString().equals(roleStr)){
			return sql;
		}//委领导  系统权限加选择的部门权限
		else if(RoleStr.LEADER_MAIN.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
			String deptInfo = personInfo.getUserDescription();
			if(deptInfo != null && !deptInfo.equals("")){
				String[] depts = deptInfo.split(",");
				String deptCodes = "";
				for(String dept : depts){
					deptCodes += "'"+dept+"',";
				}
				if(deptCodes.endsWith(","))
					deptCodes = deptCodes.substring(0,deptCodes.length()-1);
				if(isSql){
					addAuthSql = " and PETITION_BASIC_INFO.curr_dept_code in ("+deptCodes+") ";
				}else{
					addAuthSql = " and PetitionBasicInfo.currDeptCode in ("+deptCodes+") ";
				}
			}
		}//办信室  办信员 系统权限加自己办理的信 Petition_Circulation_State_Info.default_Dealer_Code 
		else if(RoleStr.BXS_GENERAL.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
			if(isSql){
				addAuthSql = " and PETITION_CIRCULATION_STATE_INFO.default_Dealer_Code ='"+personInfo.getUserEname()+"' ";
			}else{
				addAuthSql = " and circulationStateInfo.defaultDealerCode ='"+personInfo.getUserEname()+"' ";
			}
		}//办信室 秘书 ,领导 系统权限加本部门 Petition_Basic_Info.curr_Dept_Code
		else if(RoleStr.BXS_SECRETARY.toString().equals(roleStr) || RoleStr.BXS_LEADER.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
			if(isSql){
				addAuthSql = " and PETITION_BASIC_INFO.curr_dept_code ='"+Struts2Utils.getSessionAttribute("deptCode").toString()+"' ";
			}else{
				addAuthSql = " and PetitionBasicInfo.currDeptCode ='"+Struts2Utils.getSessionAttribute("deptCode").toString()+"' ";
			}
		}//案管室 办信员 系统权限加督办人是自己 Petition_Circulation_State_Info.default_Supervise_Code
		else if(RoleStr.AGS_GENERAL.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
			if(isSql){
				addAuthSql = " and PETITION_CIRCULATION_STATE_INFO.default_Supervise_Code ='"+personInfo.getUserEname()+"' ";
			}else{
				addAuthSql = " and circulationStateInfo.defaultSuperviseCode ='"+personInfo.getUserEname()+"' ";
			}
		}//案管室 秘书,领导 系统权限加his_dept_code有案管室
		else if(RoleStr.AGS_SECRETARY.toString().equals(roleStr) || RoleStr.AGS_LEADER.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
			if(isSql){
				addAuthSql = " and PETITION_BASIC_INFO.his_dept_code like '%"+Struts2Utils.getSessionAttribute("deptCode").toString()+"%' ";
			}else{
				addAuthSql = " and PetitionBasicInfo.hisDeptCode like '%"+Struts2Utils.getSessionAttribute("deptCode").toString()+"%' ";
			}
		}//信访室 办信员 系统权限加自己办理的信 Petition_Circulation_State_Info.default_Dealer_Code 
		else if(RoleStr.XFS_GENERAL.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
			if(isSql){
				addAuthSql = " and PETITION_CIRCULATION_STATE_INFO.default_Dealer_Code ='"+personInfo.getUserEname()+"' ";
			}else{
				addAuthSql = " and circulationStateInfo.defaultDealerCode ='"+personInfo.getUserEname()+"' ";
			}
			//办理方式
			//用户是信访室一般用户 传办理方式
			if(dealType != null){
				String dealTypes = "";
				for(String deal : dealType){
					dealTypes += "'"+deal+"',";
				}
				if(dealTypes.endsWith(","))
					dealTypes = dealTypes.substring(0,dealTypes.length()-1);
				if(isSql){
					addAuthSql = " and PETITION_BASIC_INFO.deal_type_code in ("+dealTypes+") ";
				}else{
					addAuthSql = " and PetitionBasicInfo.dealTypeCode in ("+dealTypes+") ";
				}
			}
		}//信访室 秘书,领导  系统权限
		else if(RoleStr.XFS_SECRETARY.toString().equals(roleStr) || RoleStr.XFS_LEADER.toString().equals(roleStr)){
			sql = getAuthSql(tableMap,isSql,personInfo);
		}
		//将表map整理成数组形式
		String[] tables = new String[tableMap.size()];
		Set<String> tableSet = tableMap.keySet();
		Object[] tableObject =  tableSet.toArray();
		for(int i=0;i<tableObject.length;i++){
			tables[i] = tableObject[i].toString();
		}
		if(!addAuthSql.equals("")){
			//处理权限串，将表名转换为别名，否则hibernate criteria查询会报错
			for(String tableName : tables){
				String alias = tableMap.get(tableName);
				if(isSql){
					addAuthSql = addAuthSql.replace(tableName.toUpperCase()+".", ""+alias+".");
				}else{
					addAuthSql = addAuthSql.replace(tableName+".", ""+alias+".");
				}
			}
		}
		return sql+addAuthSql;
	}
	
	/**
	 * 系统权限 根据用户所拥有的角色处理后得到
	 * @param tableMap
	 * @param isSql
	 * @param personInfo
	 * @return
	 */
	private String getAuthSql(Map<String,String> tableMap,boolean isSql,PersonInfo personInfo){
		//将表map整理成数组形式
		String[] tables = new String[tableMap.size()];
		Set<String> tableSet = tableMap.keySet();
		Object[] tableObject =  tableSet.toArray();
		StringBuffer tableNames = new StringBuffer(personInfo.getId());
		for(int i=0;i<tableObject.length;i++){
			tables[i] = tableObject[i].toString();
			tableNames.append(tableObject[i].toString().toUpperCase());
		}
		//保存到session中
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		String authority=(String) session.getAttribute(tableNames.toString());
		//得到当前用户所拥有的权限串
		if(authority == null || authority.equals("")){
			if(isSql){
				authority = authorityDataManager.getMultiAuthoritySqlByTableWithTableName(personInfo, tables);
			}else{
				authority = authorityDataManager.getMultiAuthorityHqlByTableWithTableName(personInfo, tables);
			}
			session.setAttribute(tableNames.toString(), authority);
		}
		if(!authority.equals("")){
			//处理权限串，将表名转换为别名，否则hibernate criteria查询会报错
			for(String tableName : tables){
				String alias = tableMap.get(tableName);
				if(isSql){
					authority = authority.replace(tableName.toUpperCase()+".", ""+alias+".");
				}else{
					authority = authority.replace(tableName+".", ""+alias+".");
				}
			}
		}
		return authority;
	}
	/**
	 * 为全文检索查询时增加权限条件,多个角色对应多个map
	 * @date 2012-3-13
	 * @creator mengxy
	 * @param tableSet 字段集合 形式为“表名”+“__”+“字段名”
	 * @return 权限封装的条件list map格式为两个key：searchFields、keywords
	 */
	@SuppressWarnings("unchecked")
	public List<Map> addAuthForSearch(Set<String> tableSet){
		//得到当前登陆用户
		OnlineUser onLineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		PersonInfo personInfo = personInfoManager.get(onLineUser.getUserId());
		if(personInfo!=null){
			//得到当前用户所拥有的权限list,每个角色对应一个map
			List<Map> authoritylist = getMultiAuthorityRoleByTable(personInfo, tableSet);
			return authoritylist;
		}
		return null;
	}
	/**
	 * 为全文检索查询获取权限的方法
	 * @date 2012-3-13
	 * @creator mengxy
	 * @param person 当前用户
	 * @param tableSet 字段集合 形式为“表名”+“__”+“字段名”
	 * @return List<Map<String,String>> map格式为两个key：searchFields、keywords
	 */
	@SuppressWarnings("unchecked")
	private List<Map> getMultiAuthorityRoleByTable(PersonInfo person,Set<String> tableSet){
		List<Map> multiAuthority = new ArrayList<Map>();
		//将表map整理成数组形式
		String[] tables = new String[tableSet.size()];
		String[] fields = new String[tableSet.size()];
		Object[] tableObject =  tableSet.toArray();
		for(int i=0;i<tableObject.length;i++){
			String value = tableObject[i].toString();
			if(value.indexOf("__")>0){
				String[] ss = value.split("__");
				tables[i] = ss[0];
				fields[i] = ss[1];
			}
		}
		//得到该用户所具有的角色权限
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		//存放库表信息，减少查询次数
		Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
		//存放字段信息，减少查询次数
		Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
		for(AuthorityUserRole userRole : roleList){
			AuthorityRole role = userRole.getRole();
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());
			Map<String,String> roleMap = new HashMap<String,String>();
			for(AuthorityData data : dataList){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals(Constants.TRUE)){
					DatabaseFieldInfo dfi = null;
					DatabaseTableInfo dti = null;
					//库表信息项
					if(tableMap.get(data.getTableInfo())==null){
						dti = databaseTableInfoDao.findByUnique("id", data.getTableInfo());
						tableMap.put(data.getTableInfo(),dti);
					}else{
						dti = tableMap.get(data.getTableInfo());
					}
					//字段信息项
					if(!fieldMap.containsKey(data.getFieldInfo())){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//判断该角色下拥有的数据权限是否跟传入的表名称、字段名称相同，只返回相同表相同字段的数据权限
					boolean isEql = false;
					for(int x=0;x<tables.length;x++)
					{
						if(dti!=null&&dti.getTableEname().toUpperCase().equals(tables[x].toUpperCase())
								&&dfi.getFieldEname().toUpperCase().equals(fields[x].toUpperCase())){
							isEql = true;
						}
					}
					//只取对应表的数据
					if(isEql){
						//取回该角色对应的权限串
						String  mapKey = dti.getTableEname().toLowerCase()+"__"+dfi.getFieldEname().toLowerCase();
						if("petition_his_dept_info__dept_code".equals(mapKey)){
							mapKey = "deptcode";//单独处理deptcode字段，已适应视图
						}
						String keyWords = searchAuthorityData(data,person,dfi,dti);
						roleMap.put(mapKey,keyWords);
					}
				}
			}
			//拼接区域权限
			String regionCodeStr = userRole.getRegionCode();
			if(regionCodeStr!=null && !regionCodeStr.trim().equals("")){
				roleMap.put("petition_basic_info__sub_issue_region_code", "("+regionCodeStr.replace(":", " OR ")+")");
			}
			if(!roleMap.isEmpty()){
				multiAuthority.add(roleMap);
			}
		}
		return multiAuthority;
	}
	/**
	 * 将角色所具有的数据权限拼装成全文检索可以解析的形式，视图中空值使用EMPTY表示
	 * @param data 数据权限
	 * @param person 用户信息
	 * @param dfi 权限值对应表信息
	 * @param dti 权限值对应字段信息
	 * @return 数据权限
	 */
	private String searchAuthorityData(AuthorityData data,PersonInfo person ,DatabaseFieldInfo dfi,DatabaseTableInfo dti){
		String restrictValue = "";
		StringBuffer words = new StringBuffer();
		if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals(""))
		{
			//判断是否是数据字典类型
			if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
				//判断该数据权限是否需要处理空值
				if(data.getRestrictType()!=null&&data.getRestrictType().equals(Constants.TRUE)){
					//将空值补进权限串
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.substring(0,restrictValue.length()-1);
					restrictValue = restrictValue.replace(";", " OR ");
					if(!restrictValue.equals(""))
						words.append("(EMPTY OR "+restrictValue+")");
				}else{
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.substring(0,restrictValue.length()-1);
					restrictValue = restrictValue.replace(";", " OR ");
					if(!restrictValue.equals(""))
						words.append("("+restrictValue+")");
				}
			//判断是否是组织机构类别，如果是组织机构类别的权限，除了将已选择的机构权限加上，还要把当前人员所在部门权限加上
			}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals(Constants.TRUE)){
				//判断该数据权限是否需要处理空值
				if(data.getRestrictType()!=null&&data.getRestrictType().equals(Constants.TRUE)){
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.substring(0,restrictValue.length()-1);
					restrictValue = restrictValue.replace(";", " OR ");
					//得到用户所属机构信息
					Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
					Iterator<OrganPersonRelationInfo> it = set.iterator();
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						restrictValue+=" OR ";
						restrictValue+=opri.getOrganizationInfo().getId();
					}
					if(!restrictValue.equals(""))
						words.append("(EMPTY OR "+restrictValue+")");
				}else{//不需要处理空值，正常处理数据
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.substring(0,restrictValue.length()-1);
					restrictValue = restrictValue.replace(";", " OR ");
					//得到用户所属机构信息
					Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
					Iterator<OrganPersonRelationInfo> it = set.iterator();
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						restrictValue+=" OR ";
						restrictValue+=opri.getOrganizationInfo().getId();
					}
					if(!restrictValue.equals(""))
						words.append("("+restrictValue+")");
				}
			}//其他文本框输入的权限值拼装
			else if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals("")){
				//判断是否包含';'，如果有则表示值中包括多项，按';'进行分隔
				if(!data.getSelectRestrictValue().contains(";")){
					//判断该数据权限是否需要处理空值
					if(data.getRestrictType()!=null&&data.getRestrictType().equals(Constants.TRUE)){
							words.append("(EMPTY OR "+data.getSelectRestrictValue()+")");
					}else{
							words.append(data.getSelectRestrictValue());
					}
				}else{
					//判断该数据权限是否需要处理空值
					if(data.getRestrictType()!=null&&data.getRestrictType().equals(Constants.TRUE)){
						restrictValue = data.getSelectRestrictValue();
						String[] values = restrictValue.split(";");
						words.append("(EMPTY");
						for(String v : values){
							words.append(" OR ").append(v);
						}
						words.append(")");
					}else{
						restrictValue = data.getSelectRestrictValue();
						String[] values = restrictValue.split(";");
						words.append("(");
						boolean first = true;
						for(String v : values){
							if(first)
								words.append(v);
							else
								words.append(" OR ").append(v);
							if(first)
								first = false;
						}
						words.append(")");
					}
				}
			}
		}else{
			//如果不控制数据值，先判断是否是关联了部门权限，如果关联，只取本部门数据
			if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals(Constants.TRUE)){
				//得到用户所属机构信息
				Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
				Iterator<OrganPersonRelationInfo> it = set.iterator();
				//判断该数据权限是否需要处理空值
				if(data.getRestrictType()!=null&&data.getRestrictType().equals(Constants.TRUE)){
					words.append("(EMPTY");
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						words.append(" OR ");
						words.append(opri.getOrganizationInfo().getId());
					}
					words.append(")");
				}else{
					boolean first = false;
					words.append("(");
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						if(first){
							words.append(" OR ");
						}
						words.append(opri.getOrganizationInfo().getId());
						if(!first){
							first = true;
						}
					}
					words.append(")");
				}
			}
		}
		return words.toString();
	}
	
	/**
	 * 检查是否含有下级
	 * @param sci 代码类别信息
	 * @param scni 代码信息
	 * @return 是或否
	 */
	private boolean checkLeaf(SystemCodeInfo sci, SystemCodeNodeInfo scni) {
		List<SystemCodeNodeInfo> list =  CodeSwitchUtil.getCodeNodeList(scni.getCodeType(), null);
		PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		String regionCode = person.getRegionCode();
		for (SystemCodeNodeInfo systemCodeNodeInfo : list) {
			String orgCode = systemCodeNodeInfo.getOrgCode();
			if(scni.getId().equals(systemCodeNodeInfo.getParentId())
			&& (StringUtils.isBlank(orgCode) || regionCode.equals(orgCode))	
			//存在子节点并且该子节点本级可见
			){
				return false;
			}
		}
		return true;
//		boolean child = false;
//		if (sci == null) {
//			int size = scni.getChildNodes().size();
//			if (size == 0) {
//				child = true;
//			}
//		} else {
//			int count = syscniDao.getNodeCount(sci.getId());
//			if (count == 0) {
//				child = true;
//			}
//		}
//		return child;
	}
	/**
	 * 查询信访来源信息，返回没有父节点的json字符串
	 *  @author xuyi
	 * @date 2013-07-23
	 * 调用哪些方法：
	 * 1、syscniDao.findBy()
	 * 		根据属性名和属性值查询
	 * 被哪些方法调用：
	 * 1、getPetitionLetterSourceTree()
	 * 		查询信访来源信息，返回没有父节点的json字符串
	 * @return
	 * 		没有父节点的json字符串
	 */
	public String getPetitionLetterSourceTree() {
		StringBuffer sourceTree =  new StringBuffer();
		sourceTree.append("[");
		PersonInfo person = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		List<SystemCodeNodeInfo> systemCodeNodeInfos = syscniDao.find("From SystemCodeNodeInfo where (orgCode=? or orgCode is null) and codeType =? ", new String[]{person.getRegionCode(),"XFLY"});
		if(!systemCodeNodeInfos.isEmpty()){
			int allRecordNum = systemCodeNodeInfos.size();
			for(int i=0 ; i<allRecordNum;i++){
				SystemCodeNodeInfo systemCodeNodeInfo = systemCodeNodeInfos.get(i);
				if(systemCodeNodeInfo.getCode().trim().equals("01") ||
					systemCodeNodeInfo.getCode().trim().equals("02") ||
					systemCodeNodeInfo.getCode().trim().equals("03")){
					continue;
				}else{
					sourceTree.append("{\"code\":\""+systemCodeNodeInfo.getCode()+"\",");
					sourceTree.append("\"name\":\""+systemCodeNodeInfo.getName()+"\"}");
					if(i < allRecordNum-1){
						sourceTree.append(",");
					}
				}
			}
		}
		sourceTree.append("]");
		return sourceTree.toString();
	}
}
