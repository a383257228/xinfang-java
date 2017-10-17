package com.sinosoft.authority.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDataDao;
import com.sinosoft.authority.dao.AuthorityDeptRoleDao;
import com.sinosoft.authority.dao.AuthorityRoleDao;
import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.databasetable.dao.DatabaseFieldInfoDao;
import com.sinosoft.databasetable.dao.DatabaseTableInfoDao;
import com.sinosoft.databasetable.domain.DatabaseFieldInfo;
import com.sinosoft.databasetable.domain.DatabaseTableInfo;
import com.sinosoft.databasetable.manager.DatabaseFieldInfoManager;
import com.sinosoft.menu.dao.MenuItemInfoDao;
import com.sinosoft.menu.domain.MenuItemInfo;
import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.systemcode.dao.SystemCodeInfoDao;
import com.sinosoft.systemcode.dao.SystemCodeNodeInfoDao;
import com.sinosoft.systemcode.domain.SystemCodeInfo;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 数据权限Manager层
 * @author sunzhe
 * Create 2010-08-10
 */
@Service
@Transactional
public class AuthorityDataManager extends EntityManager<AuthorityData, String>{
	
	//注入数据权限信息dao层
	@Autowired
	AuthorityDataDao authorityDataDao;
	//注入库表信息dao层
	@Autowired
	DatabaseTableInfoDao databaseTableInfoDao;
	//注入字段信息dao层
	@Autowired
	DatabaseFieldInfoDao databaseFieldInfoDao;
	//注入字段信息业务层
	@Autowired
	DatabaseFieldInfoManager databaseFieldInfoManager;
	//注入代码类别dao层
	@Autowired
	SystemCodeInfoDao  systemCodeInfoDao;
	//注入代码信息dao层
	@Autowired
	SystemCodeNodeInfoDao  systemCodeNodeInfoDao;
	//注入角色信息dao层
	@Autowired
	AuthorityRoleDao authorityRoleDao;
	//注入组织机构信息业务层
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	//注入用户部门权限信息dao层
	@Autowired
	AuthorityDeptRoleDao authorityDeptRoleDao;
	//注入用户信息dao层
	@Autowired
	PersonInfoDao personInfoDao;
	@Autowired
	AuthorityUserRoleManager authorityUserRoleManager;
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	
	@Autowired
	private MenuItemInfoDao menuItemInfoDao;
	
	@Override
	protected HibernateDao<AuthorityData, String> getEntityDao() {
		return authorityDataDao;
	}
	/**
	 * 查询数据权限
	 * @param propertyName　查询字段
	 * @param value　查询值
	 * @return　数据权限信息
	 */
	@Transactional(readOnly=true)
	public List<AuthorityData> findBy(String propertyName,Object value){
		return authorityDataDao.findBy(propertyName, value);
	}

	/**
	 * 数据权限对应库表定义树
	 * @return 树形显示数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly=true)
	public String buildAuthorityDatabaseTree() {
		StringBuffer treeJson = new StringBuffer("[");
		//查找所有用于权限的库表对象
		List<DatabaseFieldInfo> list = databaseFieldInfoDao.find(Restrictions.eq("isAuthority", "true"));
		boolean firstItem = true;
		Map map = new HashMap();
		//将库表对象信息放入map中
		for(DatabaseFieldInfo dfi : list){
			DatabaseTableInfo dti = dfi.getDatabaseTableInfo();
			map.put(dti.getId(), dti);
		}
		Set key = map.keySet();
		Iterator it = key.iterator();
		String tableId = "";
		//遍历所有用于权限控制的库表信息
		while(it.hasNext()){
			DatabaseTableInfo dti = (DatabaseTableInfo) map.get(it.next());
			tableId = dti.getId();
			if(!firstItem){
				treeJson.append(",");
			}
			treeJson.append("{");
			treeJson.append("id:'" + tableId+ "'");
			if(dti.getTableCname()!=null&&!dti.getTableCname().equals("")){
				treeJson.append(",text:'" + dti.getTableCname()+ "'");
			}else{
				treeJson.append(",text:'" + dti.getTableEname()+ "'");
			}
			treeJson.append(",allowChildren:true,children:[");
			//拼装表下面字段信息
			Set set = dti.getDatebaseFieldInfos();
			Iterator<DatabaseFieldInfo> field = set.iterator();
			boolean first = true;
			String fieldId = "";
			while(field.hasNext()){
				DatabaseFieldInfo dfi = field.next();
				fieldId = dfi.getId();
				//判断字段是否用于权限控制
				if(dfi.getIsAuthority()!=null&&dfi.getIsAuthority().equals("true")){
					if(!first){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + fieldId+ "'");
					treeJson.append(",tableid:'"+tableId+"'");
					treeJson.append(",fieldid:'"+fieldId+"'");
					if(dfi.getFieldCname()!=null&&!dfi.getFieldCname().equals("")){
						treeJson.append(",text:'" + dfi.getFieldCname()+ "'");
					}else{
						treeJson.append(",text:'" + dfi.getFieldEname()+ "'");
					}
					//如果字段与码表关联，则将关联信息记录
					if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
						SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
						treeJson.append(",codeType:'" + (sci==null?"":sci.getCodeType()) + "'");
					}else{
						treeJson.append(",codeType:''");
					}
					//如果字段与组织机构关联，则将关联信息记录
					if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
						treeJson.append(",organField:'true'");
					}else{
						treeJson.append(",organField:'false'");
					}
					//字段下面不会有下一级，所以都是叶子节点
					treeJson.append(",leaf:true");
					treeJson.append("}");
					if(first){
						first = false;
					}
				}
			}
			treeJson.append("]");
			treeJson.append("}");
			if(firstItem) {
				firstItem = false;
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 保存数据权限信息
	 * @param ad 数据权限实体
	 * @param roleid 角色
	 * @param tableid 表
	 * @param columnid 字段
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveData(AuthorityData ad,String roleid,String tableid,String columnid){
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("role", roleid);
		values.put("tableid", tableid);
		values.put("columnid", columnid);
		//判断是新增还是修改
		AuthorityData authorityData = authorityDataDao.findUnique("FROM AuthorityData " +
				"WHERE role.id=:role and tableInfo=:tableid and fieldInfo=:columnid", values);
		if(authorityData!=null){
			ad.setId(authorityData.getId());
		}
		//将角色，表，字段属性装入
		DatabaseFieldInfo dfi = databaseFieldInfoDao.findByUnique("id", columnid);
		ad.setRole(authorityRoleDao.get(roleid));
		ad.setTableInfo(tableid);
		ad.setFieldInfo(columnid);
		ad.setOperateDate(new Timestamp(System.currentTimeMillis()));
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(onlineUser!=null)
			ad.setOperator(onlineUser.getUserId());
		//将代码类型的默认值清空
		if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
			if(ad.getAddAble()==null&&ad.getAddRestrictValue().equals("请选择")){
				ad.setAddRestrictValue("");
			}
			if(ad.getUpdateAble()==null&&ad.getUpdateRestrictValue().equals("请选择")){
				ad.setUpdateRestrictValue("");
			}
			if(ad.getDeleteAble()==null&&ad.getDeleteRestrictValue().equals("请选择")){
				ad.setDeleteRestrictValue("");
			}
			if(ad.getSelectAble()==null&&ad.getSelectRestrictValue().equals("请选择")){
				ad.setSelectRestrictValue("");
			}
		}
		authorityDataDao.save(ad);
	}
	
	/**
	 * 查找唯一数据权限对象
	 * @param hql 查询语句
	 * @param values 查询值
	 * @return 数据权限实体
	 */
	@Transactional(readOnly=true)
	public AuthorityData findUnique(String hql,Map<String, Object> values){
		return authorityDataDao.findUnique(hql, values);
	}
	
	/**
	 * 读取已保存的信息
	 * @param roleid 角色
	 * @param tableid 表
	 * @param columnid 字段
	 * @return 数据权限信息
	 */
	@Transactional(readOnly=true)
	public AuthorityData loadSavedData(String roleid,String tableid,String columnid){
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("role", roleid);
		values.put("tableid", tableid);
		values.put("columnid", columnid);
		//查看对应的已保存数据权限
		AuthorityData authorityData = authorityDataDao.findUnique("FROM AuthorityData " +
				"WHERE role.id=:role and tableInfo=:tableid and fieldInfo=:columnid", values);
		return authorityData;
	}
	
	/**
	 * 拼装数据权限配置页面的列表数据，用于修改数据权限
	 * @param roleid 角色ID
	 * @return 列表所需要的JSON
	 */
	@Transactional(readOnly=true)
	public String buildAuthorityGridData(String roleid) throws Exception{
		List<DatabaseFieldInfo> list = databaseFieldInfoDao.find(Restrictions.eq("isAuthority", "true"));
		Map<String, AuthorityData> dataMap = new HashMap<String, AuthorityData>();
		//得到已保存过的数据权限,用于拼装页面显示值
		if(roleid!=null&&!roleid.equals("")){
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", roleid);
			//将已保存过的字段信息放到MAP中方便比对
			for(AuthorityData ad : dataList){
				dataMap.put(ad.getFieldInfo(), ad);
			}
		}
		StringBuffer json = new StringBuffer("{\"totalProperty\":"+list.size());
		json.append(",\"total\":"+list.size());
		json.append(",\"result\":[");
		boolean firstItem = true;
		DatabaseTableInfo dti = null;
		//遍历所有用于权限的库表信息
		for(DatabaseFieldInfo dfi : list){
			dti = dfi.getDatabaseTableInfo();//字段所属库表信息
			if(!firstItem)
				json.append(",");
			json.append("{");
			json.append("\"oid\":\""+dfi.getId()+"\"");
			json.append(",\"tableid\":\""+dti.getId()+"\"");
			json.append(",\"fieldid\":\""+dfi.getId()+"\"");
			//如果没有中文名称，则显示英文名称
			if(dti.getTableCname()!=null&&!dti.getTableCname().equals("")){
				json.append(",\"groupName\":\""+dti.getTableCname()+"\"");
			}else{
				json.append(",\"groupName\":\""+dti.getTableEname()+"\"");
			}
			json.append(",\"fieldEName\":\""+dfi.getFieldEname()+"\"");
			json.append(",\"fieldCName\":\""+dfi.getFieldCname()+"\"");
			//如果字段与码表关联，则将关联信息记录
			if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
				SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
				json.append(",\"codeType\":\"" + (sci==null?"":sci.getCodeType())+ "\"");
			}else{
				json.append(",\"codeType\":'\"");
			}
			//如果字段与组织机构关联，则将关联信息记录
			if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
				json.append(",\"organField\":\"true\"");
			}else{
				json.append(",\"organField\":\"false\"");
			}
			AuthorityData ad = dataMap.get(dfi.getId());
			//判断是否已经保存过，如果保存过，则将值取出，填回页面
			if(ad!=null){
				String restrictValue = "";
				json.append(",\"selectAble\":\""+ad.getSelectAble()+"\"");
				json.append(",\"addAble\":\""+ad.getAddAble()+"\"");
				json.append(",\"restrictType\":\""+ad.getRestrictType()+"\"");
				//判断是否是代码类型，如果是代码类型，转换成文字传回页面
				if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
					restrictValue = ad.getAddRestrictValue();
					if(restrictValue!=null&&!restrictValue.equals("")){
						String[] values = restrictValue.split(";");
						restrictValue = "";
						for(int i=0;i<values.length;i++){
							SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("codeType",sci==null?"null":sci.getCodeType());
							map.put("code", values[i]);
							SystemCodeNodeInfo scni = systemCodeNodeInfoDao.findUnique("From SystemCodeNodeInfo Where " +
									"codeType=:codeType and code=:code", map);
							//如果分配权限后，代码被删除，则显示空值
							restrictValue += scni==null?"":scni.getName()+";";
						}
						json.append(",\"addRestrictValue\":\""+restrictValue+"\"");
						json.append(",\"addRestrict\":\""+ad.getAddRestrictValue()+"\"");
					}
					//判断是否是组织机构类型，如果是转换组织机构编码
				}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
					restrictValue =  ad.getAddRestrictValue();
					if(restrictValue!=null&&!restrictValue.equals("")){
						//将ID值转换为中文名称，用于显示
						String[] ids = restrictValue.split(";");
						String organName = "";
						for(int i=0;i<ids.length;i++){
							OrganizationInfo organ = organizationInfoDao.findByUnique("id", ids[i]);
							//只显示未删除和有效的组织机构
							if(organ!=null&&organ.getInvalidFlag().equals("1")&&organ.getOrganizationRelationInfo().size()>0){
								organName+=organ.getOrgShortCname()+";";
							}
						}
						json.append(",\"addRestrictValue\":\""+organName+"\"");
						json.append(",\"addRestrict\":\""+ad.getAddRestrictValue()+"\"");
					}
				}else{
					//普通类型可将值直接传回页面
					json.append(",\"addRestrictValue\":\""+(ad.getAddRestrictValue()==null?"":ad.getAddRestrictValue())+"\"");
					json.append(",\"addRestrict\":\""+(ad.getAddRestrictValue()==null?"":ad.getAddRestrictValue())+"\"");
				}
				
			}
			json.append("}");
			if(firstItem){
				firstItem = false;
			}
		}
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 拼装数据权限配置页面的列表数据，用于显示数据权限
	 * @param roleid 角色ID
	 * @return 列表所需要的JSON
	 */
	public String showRoleAuthorityGridData(String roleid){
		
		//得到已保存过的数据权限,用于拼装页面显示值
		List<AuthorityData> dataList = new ArrayList<AuthorityData>();
		if(roleid!=null&&!roleid.equals(""))
			dataList = authorityDataDao.findBy("role.id", roleid);
		StringBuffer json = new StringBuffer("{\"totalProperty\":"+dataList.size());
		json.append(",\"result\":[");
		boolean firstItem = true;
		DatabaseTableInfo dti = null;
		DatabaseFieldInfo dfi = null;
		//存放库表信息，减少查询次数
		Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
		//存放字段信息，减少查询次数
		Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
		//找出所有数据权限，与库表信息相对应
		for(AuthorityData ad : dataList){
			if(ad.getAddAble()!=null||ad.getAddRestrictValue()!=null
					||ad.getUpdateAble()!=null||ad.getUpdateRestrictValue()!=null
					||ad.getDeleteAble()!=null||ad.getDeleteRestrictValue()!=null
					||ad.getSelectAble()!=null||ad.getSelectRestrictValue()!=null){
			//库表信息项
			if(tableMap.get(ad.getTableInfo())==null){
				dti = databaseTableInfoDao.findByUnique("id", ad.getTableInfo());
				tableMap.put(ad.getTableInfo(),dti);
			}else{
				dti = tableMap.get(ad.getTableInfo());
			}
			//字段信息项
			if(fieldMap.get(ad.getFieldInfo())==null){
				dfi = databaseFieldInfoDao.findByUnique("id", ad.getFieldInfo());
				fieldMap.put(ad.getFieldInfo(),dfi);
			}else{
				dfi = fieldMap.get(ad.getFieldInfo());
			}
			//判断该库表字段是否还用于权限，如果配完权限后将该字段修改为不再用于权限，则不显示
			if(dfi!=null&&dfi.getIsAuthority()!=null&&dfi.getIsAuthority().equals("true")){
				//如果该字段不需要显示任何内容，则不在页面上显示
				if(!"false".equals(ad.getAddAble())||!"false".equals(ad.getSelectAble())
						||null!=ad.getAddRestrictValue()||null!=ad.getSelectRestrictValue()){
				if(!firstItem){
					json.append(",");
				}
				json.append("{");
				json.append("\"oid\":\""+dfi.getId()+"\"");
				json.append(",\"tableid\":\""+dti.getId()+"\"");
				json.append(",\"fieldid\":\""+dfi.getId()+"\"");
				String restrictValue = "";
				json.append(",\"selectAble\":\""+ad.getSelectAble()+"\"");
				json.append(",\"addAble\":\""+ad.getAddAble()+"\"");
				json.append(",\"restrictType\":\""+ad.getRestrictType()+"\"");
				//判断是否是代码类型，如果是代码类型，转换成文字传回页面
				if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
					restrictValue = ad.getAddRestrictValue();
					if(restrictValue!=null&&!restrictValue.equals("")){
						String[] values = restrictValue.split(";");
						restrictValue = "";
						for(int i=0;i<values.length;i++){
							SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("codeType",sci==null?"null":sci.getCodeType());
							map.put("code", values[i]);
							SystemCodeNodeInfo scni = systemCodeNodeInfoDao.findUnique("From SystemCodeNodeInfo Where " +
									"codeType=:codeType and code=:code", map);
							//如果分配权限后，代码被删除，则显示空值
							restrictValue += scni==null?"":scni.getName()+";";
						}
						json.append(",\"addRestrictValue\":\""+restrictValue+"\"");
					}
					//判断是否是组织机构类型，如果是转换组织机构编码
				}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
					restrictValue =  ad.getAddRestrictValue();
					if(restrictValue!=null&&!restrictValue.equals("")){
						//将ID值转换为中文名称，用于显示
						String[] ids = restrictValue.split(";");
						String organName = "";
						for(int i=0;i<ids.length;i++){
							OrganizationInfo organ = organizationInfoDao.findByUnique("id", ids[i]);
							//只显示未删除和有效的组织机构
							if(organ!=null&&organ.getInvalidFlag().equals("1")&&organ.getOrganizationRelationInfo().size()>0){
								organName+=organ.getOrgShortCname()+";";
							}
						}
						json.append(",\"addRestrictValue\":\""+organName+"\"");
					}
				}else{
					//普通类型可将值直接传回页面
					json.append(",\"addRestrictValue\":\""+(ad.getAddRestrictValue()==null?"":ad.getAddRestrictValue())+"\"");
				}
				if(dti.getTableCname()!=null&&!dti.getTableCname().equals("")){
					json.append(",\"groupName\":\""+dti.getTableCname()+"\"");
				}else{
					json.append(",\"groupName\":\""+dti.getTableEname()+"\"");
				}
				json.append(",\"fieldEName\":\""+dfi.getFieldEname()+"\"");
				json.append(",\"fieldCName\":\""+(dfi.getFieldCname()==null?"":dfi.getFieldCname())+"\"");
				//如果字段与码表关联，则将关联信息记录
				if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
					SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
					json.append(",\"codeType\":\"" + (sci==null?"":sci.getCodeType())+ "\"");
				}else{
					json.append(",\"codeType\":\"\"");
				}
				//如果字段与组织机构关联，则将关联信息记录
				if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
					json.append(",\"organField\":\"true\"");
				}else{
					json.append(",\"organField\":\"false\"");
				}
				json.append("}");
				if(firstItem){
					firstItem = false;
				}
			}
			}else{
				//如果库表中已取消该字段属于权限，那么就在权限中删除
				authorityDataDao.delete(ad);
			}
		}
		}
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 保存列表中数据权限项
	 * @param dataList
	 * @param roleId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveGridData(List<AuthorityData> dataList,String roleId){
		authorityDataDao.deleteByRole(roleId);
		for(AuthorityData ad : dataList){
			//设置表关联ID
			ad.setTableInfo(ad.getTableid());
			//设置字段关联ID
			ad.setFieldInfo(ad.getFieldid());
			//设置角色关联ID
			AuthorityRole ar = new AuthorityRole();
			ar.setId(roleId);
			ad.setRole(ar);
			ad.setSelectRestrictValue(ad.getAddRestrictValue());
			//判断是否有代码类型值被传进来，如果有代码或组织机构类型的值，则要保存代码而不是文字
			if(ad.getAddRestrict()!=null&&!ad.getAddRestrict().equals("")){
				//根据字段类型进行判断，如果不是组织机构类型或代码类型，则直接保存页面输入值
				DatabaseFieldInfo dfi = databaseFieldInfoDao.findByUnique("id", ad.getFieldid());
				if((dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true"))
						||(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals(""))){
					ad.setSelectRestrictValue(ad.getAddRestrict());
					ad.setAddRestrictValue(ad.getAddRestrict());
				}
			}
			ad.setOperateDate(new Timestamp(System.currentTimeMillis()));
			OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
			if(onlineUser!=null)
				ad.setOperator(onlineUser.getUserId());
			authorityDataDao.save(ad);
		}
	}
	
	
	
	/**
	 * 将表名称改为Hibernate对象形式的名称 例：AA_BB AaBb
	 * @param name 表名称
	 * @return
	 */
	private String changeTableToHql(String name){
		String result = "";
		if(name.indexOf("_")>0){
			String[] ss = name.split("_");
			for(int i=0;i<ss.length;i++){
					result += ss[i].substring(0,1).toUpperCase()+
					ss[i].substring(1,ss[i].length()).toLowerCase();
			}
		}
		return result;
	}
	
	/**
	 * 将字段名称改为Hibernate对象形式的名称 例：AA_BB aaBb
	 * @param name 字段名称
	 * @return
	 */
	private String changeColumnToHql(String name){
		String result = "";
		if(name.indexOf("_")>0){
			String[] ss = name.split("_");
			for(int i=0;i<ss.length;i++){
				if(i==0){
					result = ss[i].substring(0,1).toLowerCase()+
					ss[i].substring(1,ss[i].length()).toLowerCase();
				}else{
					result += ss[i].substring(0,1).toUpperCase()+
					ss[i].substring(1,ss[i].length()).toLowerCase();
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据用户，得到权限数据的sql
	 * 从onlineUser中得到用户信息
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getAuthoritySql(){
		return getAuthority(true,false);
	}
	
	/**
	 * 根据用户，得到权限数据的sql 并在字段名前带上表名称
	 * 从onlineUser中得到用户信息
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getAuthoritySqlWithTableName(){
		return getAuthority(true,true);
	}
	
	/**
	 * 只针对某些表并根据用户，得到权限数据的sql
	 * 从onlineUser中得到用户信息
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getAuthoritySqlByTable(String[] tableName){
		return getAuthorityByTable(tableName,true,false);
	}

	/**
	 * 只针对某些表并根据用户，得到权限数据的sql 并在字段名前带上表名称
	 * 从onlineUser中得到用户信息
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getAuthoritySqlByTableWithTableName(String[] tableName){
		return getAuthorityByTable(tableName,true,true);
	}
	
	/**
	 * 根据用户，得到权限数据的Hql
	 * 从onlineUser中得到用户信息
	 * @return hql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getAuthorityHql(){
		return getAuthority(false,false);
	}
	
	/**
	 * 根据用户，得到权限数据的Hql 并在字段名前带上表名称
	 * 从onlineUser中得到用户信息
	 * @return hql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getAuthorityHqlWithTableName(){
		return getAuthority(false,true);
	}
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql
	 * 从onlineUser中得到用户信息
	 * @param tableName 表名称
	 * @return hql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getAuthorityHqlByTable(String[] tableName){
		return getAuthorityByTable(tableName,false,false);
	}
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql 并在字段名前带上表名称
	 * 从onlineUser中得到用户信息
	 * @param tableName 表名称
	 * @return hql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getAuthorityHqlByTableWithTableName(String[] tableName){
		return getAuthorityByTable(tableName,false,true);
	}
	/**
	 * 根据用户，得到权限数据的sql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthoritySql(PersonInfo person){
		return getMultiAuthorityRole(person,true,false);
	}
	
	/**
	 * 根据用户，得到权限数据的sql 并在字段名前带上表名称,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthoritySqlWithTableName(PersonInfo person){
		return getMultiAuthorityRole(person,true,true);
	}
	
	/**
	 * 只针对某些表并根据用户，得到权限数据的sql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthoritySqlByTable(PersonInfo person,String[] tableName){
		return getMultiAuthorityRoleByTable(person,tableName,null,true,false);
	}
	
	/**
	 *只针对某些表并根据用户，得到权限数据的sql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * 此方法角色与菜单进行关联
	 * @Title: getMultiAuthoritySqlByTable
	 * @author: lixl  
	 * @date: 2014年8月13日 下午5:02:15 
	 * @param person
	 * @param tableName
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthoritySqlByTable(PersonInfo person,String[] tableName,String menuUrl){
		return getMultiAuthorityRoleByTable(person,tableName,menuUrl,true,false);
	}
	/**
	 * 只针对某些表并根据用户，得到权限数据的sql 并在字段名前带上表名称,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthoritySqlByTableWithTableName(PersonInfo person,String[] tableName){
		return getMultiAuthorityRoleByTable(person,tableName,null,true,true);
	}
	/**
	 * 只针对某些表并根据用户，得到权限数据的sql 并在字段名前带上表名称,
	 * 用户具有多个角色时，不同角色之间用or关联
	 *  此方法 角色会与功能菜单进行绑定
	 * @Title: getMultiAuthoritySqlByTableWithTableName
	 * @author: lixl  
	 * @date: 2014年8月13日 下午5:04:01 
	 * @param person
	 * @param tableName
	 * @param menuUrl
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthoritySqlByTableWithTableName(PersonInfo person,String[] tableName,String menuUrl){
		return getMultiAuthorityRoleByTable(person,tableName,menuUrl,true,true);
	}
	
	/**
	 * 根据用户，得到权限数据的hql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthorityHql(PersonInfo person){
		return getMultiAuthorityRole(person,false,false);
	}
	
	/**
	 * 根据用户，得到权限数据的hql 并在字段名前带上表名称,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthorityHqlWithTableName(PersonInfo person){
		return getMultiAuthorityRole(person,false,true);
	}
	
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthorityHqlByTable(PersonInfo person,String[] tableName){
		return getMultiAuthorityRoleByTable(person,tableName,null,false,false);
	}

	
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分
	 */
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * 此方法功能菜单会与角色进行绑定和关联
	 * @Title: getMultiAuthorityHqlByTable
	 * @author: lixl  
	 * @date: 2014年8月13日 下午5:05:33 
	 * @param person
	 * @param tableName
	 * @param menuUrl
	 * @return sql语句中带权限的where部分
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthorityHqlByTable(PersonInfo person,String[] tableName,String menuUrl){
		return getMultiAuthorityRoleByTable(person,tableName,menuUrl,false,false);
	}
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql 并在字段名前带上表名称,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * @param person 当前用户
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthorityHqlByTableWithTableName(PersonInfo person,String[] tableName){
		return getMultiAuthorityRoleByTable(person,tableName,null,false,true);
	}
	
	/**
	 * 只针对某些表并根据用户，得到权限数据的hql 并在字段名前带上表名称,
	 * 用户具有多个角色时，不同角色之间用or关联
	 * 此方法功能菜单会与角色进行绑定和关联
	 * @param person 当前用户
	 * @param tableName 表名称
	 * @return sql语句中带权限的where部分 其中字段名称前会带上表名
	 */
	@Transactional(readOnly=true)
	public String getMultiAuthorityHqlByTableWithTableName(PersonInfo person,String[] tableName,String menuUrl){
		return getMultiAuthorityRoleByTable(person,tableName,menuUrl,false,true);
	}
	/**
	 * 拼权限sql方法
	 * 从onlineUser中得到用户信息
	 * @param isSql 判断是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return sql语句中带权限的where部分
	 */
	private String getAuthority(boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		PersonInfo person = personInfoDao.findByUnique("id", onlineUser.getUserId());
		if(person!=null){
			String restrictValue = "";
			Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
			Iterator<OrganPersonRelationInfo> it = set.iterator();
			//存放库表信息，减少查询次数
			Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
			//存放字段信息，减少查询次数
			Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
			AuthorityRole role = authorityRoleDao.findByUnique("id", onlineUser.getOnlineRoleId());
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());
			String fieldName = "";//sql中的字段名称
			for(AuthorityData data : dataList){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals("true")){
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
					if(fieldMap.get(data.getFieldInfo())==null){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//根据语句类型，判断选择哪种处理方式，拼装表名称与字段名称
					if(isSql){
						if(withTable){
							fieldName = dti.getTableEname()+"."+dfi.getFieldEname();
						}else{
							fieldName = dfi.getFieldEname();
						}
					}else{
						if(withTable){
							fieldName = changeTableToHql(dti.getTableEname())+"."+
									changeColumnToHql(dfi.getFieldEname());
						}else{
							fieldName = changeColumnToHql(dfi.getFieldEname());
						}
					}
					//判断是否对该字段有数据值的控制
					if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals(""))
					{
						//判断是否是数据字典类型
						if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
							hql.append( " and ");
							//判断该数据权限是否需要处理空值
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = data.getSelectRestrictValue();
								//将空值补进权限串 如：('1','2','3','')
								restrictValue = restrictValue.replace(";", "','");
								hql.append("(").append(fieldName).append(" is null or ");
								hql.append(fieldName).append(" in ('"+restrictValue+"'))");
							}else{
								restrictValue = data.getSelectRestrictValue();
								restrictValue = restrictValue.substring(0,restrictValue.length()-1);
								restrictValue = restrictValue.replace(";", "','");
								hql.append(fieldName);
								hql.append(" in ('"+restrictValue+"')");
							}
							//判断是否是组织机构类别，如果是组织机构类别的权限，除了将已选择的机构权限加上，还要把当前人员所在部门权限加上
						}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
							hql.append( " and ");
							//判断该数据权限是否需要处理空值
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = data.getSelectRestrictValue();
								restrictValue = restrictValue.replace(";", "','");
								restrictValue = "'"+restrictValue+"'";
								//得到用户所属机构信息
								set = person.getOrganPersonRelationInfo();
								it = set.iterator();
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									restrictValue+=",";
									restrictValue+="'"+opri.getOrganizationInfo().getId()+"'";
								}
								hql.append("(").append(fieldName).append(" is null or ");
								hql.append(fieldName).append(" in ("+restrictValue+"))");
							}else{//不需要处理空值，正常处理数据
								restrictValue = data.getSelectRestrictValue();
								restrictValue = restrictValue.substring(0,restrictValue.length()-1);
								restrictValue = restrictValue.replace(";", "','");
								restrictValue = "'"+restrictValue+"'";
								//得到用户所属机构信息
								set = person.getOrganPersonRelationInfo();
								it = set.iterator();
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									restrictValue+=",";
									restrictValue+="'"+opri.getOrganizationInfo().getId()+"'";
								}
								hql.append(fieldName).append(" in ("+restrictValue+")");
							}
						}else{//普通类型的权限值处理
							//如果权限值中有;表示有多个值需要分隔处理
							hql.append( " and ");
							//判断是否包含';'，如果有则表示值中包括多项，按';'进行分隔
							if(!data.getSelectRestrictValue().contains(";")){
								//判断该数据权限是否需要处理空值
								if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
									hql.append("( ").append(fieldName).append(" is null or ").append(fieldName);
									//判断是否需要模糊查询
									if(data.getSelectRestrictValue().contains("%")){
										hql.append(" like '"+data.getSelectRestrictValue()+"' or ").append(fieldName)
										.append("='' )");
									}else{
										hql.append("='"+data.getSelectRestrictValue()+"' or ").append(fieldName)
										.append("='' )");
									}
								}else{
									//判断是否需要模糊查询
									if(data.getSelectRestrictValue().contains("%")){
										hql.append(fieldName).append("like '"+data.getSelectRestrictValue()+"'");
									}else{
										hql.append(fieldName).append("='"+data.getSelectRestrictValue()+"'");
									}
								}
							}else{

								//判断该数据权限是否需要处理空值
								if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
									hql.append("( ").append(fieldName).append(" is null ");
									restrictValue = data.getSelectRestrictValue();
									String[] values = restrictValue.split(";");
									for(String v : values){
										hql.append(" or ").append(fieldName).append(" like '").append(v).append("'");
									}
									hql.append(" or ").append(fieldName).append(" like '' )");
								}else{
									restrictValue = data.getSelectRestrictValue();
									hql.append("( ");
									String[] values = restrictValue.split(";");
									boolean first = true;
									for(String v : values){
										if(first)
											hql.append(fieldName).append(" like '").append(v).append("'");
										else
											hql.append(" or ").append(fieldName).append(" like '").append(v).append("'");
										if(first)
											first = false;
									}
									hql.append(")");
								}
							
							}
						}
					}else{
						//如果不控制数据值，先判断是否是关联了部门权限，如果关联，只取本部门数据
						if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
							hql.append( " and ");
							//得到用户所属机构信息
							set = person.getOrganPersonRelationInfo();
							it = set.iterator();
							boolean first = false;
							//判断该数据权限是否需要处理空值
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								hql.append("( ").append(fieldName).append(" is null or ")
								.append(fieldName).append(" in ( ''");
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									hql.append(",");
									hql.append("'"+opri.getOrganizationInfo().getId()+"'");
								}
								hql.append(")");
							}else{
								hql.append(fieldName).append(" in (");
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									if(first){
										hql.append(",");
									}
									hql.append("'"+opri.getOrganizationInfo().getId()+"'");
									if(!first){
										first = true;
									}
								}
							}
							hql.append(")");
						}
					}
				}
			}
		}
		return hql.toString();
	}
	/**
	 * 拼权限sql方法
	 * 从onlineUser中得到用户信息
	 * @param tableName 表名称
	 * @param isSql 是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return
	 */
	private String getAuthorityByTable(String[] tableName,boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		PersonInfo person = personInfoDao.findByUnique("id", onlineUser.getUserId());
		if(person!=null){
			String restrictValue = "";
			//得到用户所属机构信息
			Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
			Iterator<OrganPersonRelationInfo> it = set.iterator();
			//存放库表信息，减少查询次数
			Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
			//存放字段信息，减少查询次数
			Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
			AuthorityRole role = authorityRoleDao.findByUnique("id", onlineUser.getOnlineRoleId());
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());
			String fieldName = "";//sql中的字段名称
			for(AuthorityData data : dataList){
				DatabaseTableInfo dti = null;
				//库表信息项
				if(tableMap.get(data.getTableInfo())==null){
					dti = databaseTableInfoDao.findByUnique("id", data.getTableInfo());
					tableMap.put(data.getTableInfo(),dti);
				}else{
					dti = tableMap.get(data.getTableInfo());
				}
				boolean isEql = false;
				for(int x=0;x<tableName.length;x++)
				{
					if(dti!=null&&(dti.getTableEname().toUpperCase().equals(tableName[x].toUpperCase())||
							changeTableToHql(dti.getTableEname()).toUpperCase().equals(
									tableName[x].toUpperCase()))){
						isEql = true;
					}
				}
				//只取对应表的数据
				if(isEql){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals("true")){
					DatabaseFieldInfo dfi = null;
					//字段信息项
					if(fieldMap.get(data.getFieldInfo())==null){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//根据语句类型，判断选择哪种处理方式，拼装表名称与字段名称
					if(isSql){
						if(withTable){
							fieldName = dti.getTableEname()+"."+dfi.getFieldEname();
						}else{
							fieldName = dfi.getFieldEname();
						}
					}else{
						if(withTable){
							fieldName = changeTableToHql(dti.getTableEname())+"."+
									changeColumnToHql(dfi.getFieldEname());
						}else{
							fieldName = changeColumnToHql(dfi.getFieldEname());
						}
					}
					//判断是否对该字段有数据值的控制
					if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals(""))
					{
						//判断是否是数据字典类型
						if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
							hql.append( " and ");
							//判断该数据权限是否需要处理空值
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = data.getSelectRestrictValue();
								//将空值补进权限串 如：('1','2','3','')
								restrictValue = restrictValue.replace(";", "','");
								hql.append("(").append(fieldName).append(" is null or ");
								hql.append(fieldName).append(" in ('"+restrictValue+"'))");
							}else{
								restrictValue = data.getSelectRestrictValue();
								restrictValue = restrictValue.substring(0,restrictValue.length()-1);
								restrictValue = restrictValue.replace(";", "','");
								hql.append(fieldName);
								hql.append(" in ('"+restrictValue+"')");
							}
							//判断是否是组织机构类别，如果是组织机构类别的权限，除了将已选择的机构权限加上，还要把当前人员所在部门权限加上
						}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
							hql.append( " and ");
							//判断该数据权限是否需要处理空值
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = data.getSelectRestrictValue();
								restrictValue = restrictValue.replace(";", "','");
								restrictValue = "'"+restrictValue+"'";
								//得到用户所属机构信息
								set = person.getOrganPersonRelationInfo();
								it = set.iterator();
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									restrictValue+=",";
									restrictValue+="'"+opri.getOrganizationInfo().getId()+"'";
								}
								hql.append("(").append(fieldName).append(" is null or ");
								hql.append(fieldName).append(" in ("+restrictValue+"))");
							}else{//不需要处理空值，正常处理数据
								restrictValue = data.getSelectRestrictValue();
								restrictValue = restrictValue.substring(0,restrictValue.length()-1);
								restrictValue = restrictValue.replace(";", "','");
								restrictValue = "'"+restrictValue+"'";
								//得到用户所属机构信息
								set = person.getOrganPersonRelationInfo();
								it = set.iterator();
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									restrictValue+=",";
									restrictValue+="'"+opri.getOrganizationInfo().getId()+"'";
								}
								hql.append(fieldName).append(" in ("+restrictValue+")");
							}
						}else{
							hql.append( " and ");							
							//判断是否包含';'，如果有则表示值中包括多项，按';'进行分隔
							if(!data.getSelectRestrictValue().contains(";")){
								//判断该数据权限是否需要处理空值
								if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
									hql.append("( ").append(fieldName).append(" is null or ").append(fieldName);
									//判断是否需要模糊查询
									if(data.getSelectRestrictValue().contains("%")){
										hql.append(" like '"+data.getSelectRestrictValue()+"' or ").append(fieldName)
										.append("='' )");
									}else{
										hql.append("='"+data.getSelectRestrictValue()+"' or ").append(fieldName)
										.append("='' )");
									}
								}else{
									//判断是否需要模糊查询
									if(data.getSelectRestrictValue().contains("%")){
										hql.append(fieldName).append("like '"+data.getSelectRestrictValue()+"'");
									}else{
										hql.append(fieldName).append("='"+data.getSelectRestrictValue()+"'");
									}
								}
							}else{
								//判断该数据权限是否需要处理空值
								if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
									hql.append("( ").append(fieldName).append(" is null ");
									restrictValue = data.getSelectRestrictValue();
									String[] values = restrictValue.split(";");
									for(String v : values){
										hql.append(" or ").append(fieldName).append(" like '").append(v).append("'");
									}
									hql.append(" or ").append(fieldName).append(" like '' )");
								}else{
									restrictValue = data.getSelectRestrictValue();
									hql.append("( ");
									String[] values = restrictValue.split(";");
									boolean first = true;
									for(String v : values){
										if(first)
											hql.append(fieldName).append(" like '").append(v).append("'");
										else
											hql.append(" or ").append(fieldName).append(" like '").append(v).append("'");
										if(first)
											first = false;
									}
									hql.append(")");
								}
							
							}
							
						}
					}else{
						//如果不控制数据值，先判断是否是关联了部门权限，如果关联，只取本部门数据
						if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
							hql.append( " and ");
							//得到用户所属机构信息
							set = person.getOrganPersonRelationInfo();
							it = set.iterator();
							boolean first = false;
							//判断该数据权限是否需要处理空值
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								hql.append("( ").append(fieldName).append(" is null or ")
								.append(fieldName).append(" in ( ''");
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									hql.append(",");
									hql.append("'"+opri.getOrganizationInfo().getId()+"'");
								}
								hql.append(")");
							}else{
								hql.append(fieldName).append(" in (");
								//遍历所有用户机构信息
								while(it.hasNext()){
									OrganPersonRelationInfo opri = it.next();
									if(first){
										hql.append(",");
									}
									hql.append("'"+opri.getOrganizationInfo().getId()+"'");
									if(!first){
										first = true;
									}
								}
							}
							hql.append(")");
						}
					}
				}
				}
			}
		}
		return hql.toString();
	
	}
	
	/**
	 * 根据用户权限返回标准查询权限对象
	 * 从onlineUser中得到用户信息
	 * @param manager 创建标准查询对象的manager
	 * @param tableName 表名称
	 * @return Criteria 对象
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public Criteria getAuthorityCriteria(EntityManager manager,String[] tableName){
		Criteria criteria = manager.createCriteria();
		ArrayList<Criterion> list = getAuthorityCriterionList(tableName);
		for(Criterion cri : list){
			criteria.add(cri);
		}
		return criteria;
	}
	
	/**
	 * 根据用户权限返回标准查询权限对象
	 * 从onlineUser中得到用户信息
	 * @param manager 创建标准查询对象的manager
	 * @param orderBy 排序字段
	 * @param isAsc 顺序
	 * @param tableName 表名称
	 * @return Criteria 对象
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public Criteria getAuthorityCriteria(EntityManager manager,String orderBy,
			boolean isAsc,Criterion criterions,String[] tableName){
		Criteria criteria = manager.createCriteria(orderBy, isAsc, criterions);
		ArrayList<Criterion> list = getAuthorityCriterionList(tableName);
		for(Criterion cri : list){
			criteria.add(cri);
		}
		return criteria;
	}
	
	/**
	 * 根据用户权限返回标准查询权限对象，不同角色之间用or关联
	 * @param person 当前登录用户
	 * @param manager 创建标准查询对象的manager
	 * @param tableName 表名称
	 * @return Criteria 对象
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public Criteria getMultiAuthorityCriteria(PersonInfo person,EntityManager manager,String[] tableName){
		Criteria criteria = manager.createCriteria();
		ArrayList<Criterion> list = getMultiAuthorityCriterionList(person,tableName);
		for(Criterion cri : list){
			criteria.add(cri);
		}
		return criteria;
	}
	/**
	 * 根据用户权限返回标准查询权限对象，不同角色之间用or关联
	 * @param person 当前登录用户
	 * @param manager 创建标准查询对象的manager
	 * @param orderBy 排序字段
	 * @param isAsc 顺序
	 * @param tableName 表名称
	 * @return Criteria 对象
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public Criteria getMultiAuthorityCriteria(PersonInfo person,EntityManager manager,String orderBy,
			boolean isAsc,Criterion criterions,String[] tableName){
		Criteria criteria = manager.createCriteria(orderBy, isAsc, criterions);
		ArrayList<Criterion> list = getMultiAuthorityCriterionList(person,tableName);
		for(Criterion cri : list){
			criteria.add(cri);
		}
		return criteria;
	}
	/**
	 * 根据用户权限返回标准查询中查询项集合
	 * 从onlineUser中得到用户信息
	 * @param tableName 要查询的表名称
	 * @return Criterion组
	 */
	@Transactional(readOnly=true)
	public ArrayList<Criterion> getAuthorityCriterionList(String[] tableName){		
		ArrayList<Criterion> criList = new ArrayList<Criterion>();
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		//根据用户得到角色信息，只取该角色的数据权限信息
		if(onlineUser!=null){
			PersonInfo person = personInfoDao.findByUnique("id", onlineUser.getUserId());
			String restrictValue = "";
			//存放库表信息，减少查询次数
			Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
			//存放字段信息，减少查询次数
			Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
			AuthorityRole role = authorityRoleDao.findByUnique("id", onlineUser.getOnlineRoleId());
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());
			
			for(AuthorityData data : dataList){
				DatabaseTableInfo dti = null;
				//库表信息项
				if(tableMap.get(data.getTableInfo())==null){
					dti = databaseTableInfoDao.findByUnique("id", data.getTableInfo());
					tableMap.put(data.getTableInfo(),dti);
				}else{
					dti = tableMap.get(data.getTableInfo());
				}
				boolean isEql = false;
				for(int x=0;x<tableName.length;x++)
				{
					if(dti!=null&&(dti.getTableEname().toUpperCase().equals(tableName[x].toUpperCase())||
						changeTableToHql(dti.getTableEname()).toUpperCase().equals(
								tableName[x].toUpperCase()))){
					isEql = true;
					}
				}
				if(isEql){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals("true")){
					DatabaseFieldInfo dfi = null;
					//字段信息项
					if(fieldMap.get(data.getFieldInfo())==null){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//判断是否对该字段有数据值的控制
					if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals(""))
					{
						//判断是否是数据字典类型
						if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
							//如果需要包含空值，则将空字符串与null值添加进criList中
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = data.getSelectRestrictValue()+" ;";
								criList.add(Restrictions.or(Restrictions.isNull(changeColumnToHql(dfi.getFieldEname())), 
										Restrictions.in(changeColumnToHql(dfi.getFieldEname()), 
												restrictValue.split(";"))));
							}else{
								criList.add(Restrictions.in(changeColumnToHql(dfi.getFieldEname()), 
										data.getSelectRestrictValue().split(";")));
							}
							//判断是否是组织机构类别，如果是组织机构类别的权限，除了将已选择的机构权限加上，还要把当前人员所在部门权限加上
						}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
							restrictValue = data.getSelectRestrictValue();
							//得到用户机构信息
							Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
							Iterator<OrganPersonRelationInfo> it = set.iterator();
							//遍历所有用户机构信息
							while(it.hasNext()){
								OrganPersonRelationInfo opri = it.next();
								restrictValue+=opri.getOrganizationInfo().getId();
								restrictValue+=";";
							}
							//如果需要包含空值，则将空字符串与null值添加进criList中
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = restrictValue+" ;";
								criList.add(Restrictions.or(Restrictions.isNull(changeColumnToHql(dfi.getFieldEname())), 
										Restrictions.in(changeColumnToHql(dfi.getFieldEname()), 
												restrictValue.split(";"))));
							}else{
								criList.add(Restrictions.in(changeColumnToHql(dfi.getFieldEname()),restrictValue.split(";")));
							}
						}else{//普通输入框输入的权限信息，如果中间有;分隔则表示多个值
							//判断是否包含';'，如果有则表示值中包括多项，按';'进行分隔
							if(!data.getSelectRestrictValue().contains(";")){
								//判断是否需要模糊查询
								if(data.getSelectRestrictValue().contains("%")){
									//处理空值
									if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
										criList.add(Restrictions.or(Restrictions.isNull(changeColumnToHql(dfi.getFieldEname())),
											Restrictions.or(Restrictions.eq(changeColumnToHql(dfi.getFieldEname()), ""),
											Restrictions.like(changeColumnToHql(dfi.getFieldEname()), data.getSelectRestrictValue()))));
									}else{
										criList.add(Restrictions.like(changeColumnToHql(dfi.getFieldEname()), data.getSelectRestrictValue()));
									}
								}else{
									if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
										criList.add(Restrictions.or(Restrictions.isNull(changeColumnToHql(dfi.getFieldEname())),
											Restrictions.or(Restrictions.eq(changeColumnToHql(dfi.getFieldEname()), ""),
											Restrictions.eq(changeColumnToHql(dfi.getFieldEname()), data.getSelectRestrictValue()))));
									}else{
										criList.add(Restrictions.eq(changeColumnToHql(dfi.getFieldEname()), data.getSelectRestrictValue()));
									}
								}
							}else{//对多个字符串型的值进行处理，这些值用;分隔
								//处理空值
								if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
									restrictValue = data.getSelectRestrictValue();
									//添加空字符串与null值
									String[] values = restrictValue.split(";");
									for(String v :values){
										criList.add(Restrictions.or((Restrictions.like(changeColumnToHql(dfi.getFieldEname()), v)),
												Restrictions.or(Restrictions.isNull(changeColumnToHql(dfi.getFieldEname())), 
												Restrictions.eq(changeColumnToHql(dfi.getFieldEname()), ""))));
									}
								}else{
									//不包含空值，直接将保存的权限值拼进criList
									restrictValue = data.getSelectRestrictValue();
									String[] values = restrictValue.split(";");
									for(String v :values){
										criList.add(Restrictions.like(changeColumnToHql(dfi.getFieldEname()), v));
									}
								}
								
							}
						}
					}else{
						//如果不控制数据值，先判断是否是关联了部门权限，如果关联，只取本部门数据
						if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
							restrictValue = "";
							//得到用户机构信息
							Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
							Iterator<OrganPersonRelationInfo> it = set.iterator();
							//遍历所有用户机构信息
							while(it.hasNext()){
								OrganPersonRelationInfo opri = it.next();
								restrictValue+=opri.getOrganizationInfo().getId();
								restrictValue+=";";
							}
							if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
								restrictValue = restrictValue+" ;";
								criList.add(Restrictions.or(Restrictions.isNull(changeColumnToHql(dfi.getFieldEname())), 
										Restrictions.in(changeColumnToHql(dfi.getFieldEname()), 
												restrictValue.split(";"))));
							}else{
								criList.add(Restrictions.in(changeColumnToHql(dfi.getFieldEname()), restrictValue.split(";")));
							}
						}
					}
				}
			}
		}
		}
		return criList;
	}
	/**
	 * 拼权限sql方法,多个角色之间用or关联
	 * @param person 当前用户
	 * @param isSql 判断是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return
	 */
	private String getMultiAuthorityRole(PersonInfo person,boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		if(person!=null){
		//得到该用户所具有的角色权限
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		ArrayList<AuthorityRole> roleOwnerList = new ArrayList<AuthorityRole>();
		//权限分为部门角色与用户角色两部分
		for(AuthorityUserRole aur : roleList){
			roleOwnerList.add(aur.getRole());
		}
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.
			findBy("organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				roleOwnerList.add(deptRole.getRole());
			}
		}
		//存放库表信息，减少查询次数
		Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
		//存放字段信息，减少查询次数
		Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
		boolean firstRole = true;
		for(AuthorityRole role : roleOwnerList){
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());
			StringBuffer dataBuffer = new StringBuffer();
			for(AuthorityData data : dataList){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals("true")){
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
					if(fieldMap.get(data.getFieldInfo())==null){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//取回该角色对应的权限串
					String authSql = userAuthorityData(data,person,isSql,withTable,dfi,dti);
					authSql = authSql.replace("{alias}.", "");
					//每个角色下的数据权限之间用and相联
					if(!dataBuffer.toString().equals("")&&!authSql.equals(""))
						dataBuffer.append(" and ");
					dataBuffer.append(authSql);
					
				}
				
			}
			//不同角色之间用or连接
			if(!firstRole){
				for(AuthorityData data : dataList){
					if(data.getSelectAble()!=null){
						hql.append(" or ( ");
						hql.append(dataBuffer);
						hql.append(")");
						break;
					}
				}
			}else{
				//首个角色权限串前不加or
				for(AuthorityData data : dataList){
					if(data.getSelectAble()!=null){
						hql.append(" ( ");
						hql.append(dataBuffer);
						hql.append(")");
						break;
					}
				}
				firstRole = false;
			}
		}
		}
		if(!hql.toString().equals("")){
			hql.insert(0, " and ( ");
			hql.append(" )");
		}
		return hql.toString();
	}
	/**
	 * 拼权限sql方法,多个角色之间用or关联
	 * @param person 当前用户
	 * @param isSql 判断是否是sql或是hql
	 * @param withTable 判断是否需要在字段名前加上表名
	 * @return
	 */
	private String getMultiAuthorityRoleByTable(PersonInfo person,String[] tableName,String menuUrl,boolean isSql,boolean withTable){
		StringBuffer hql = new StringBuffer();
		if(person!=null){
		//得到该用户所具有的角色权限
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		List<AuthorityRole> roleOwnerList = new ArrayList<AuthorityRole>();
		//权限分为 部门角色与用户角色两部分
		if(menuUrl!=null&&!"".equals(menuUrl)){
			//如果有功能菜单，则查询该功能菜单对应的角色信息
			roleOwnerList = queryRoleByMenuUrl(person,menuUrl);
		}else{
			for(AuthorityUserRole aur : roleList){
				roleOwnerList.add(aur.getRole());
			}
		}
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.
			findBy("organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				roleOwnerList.add(deptRole.getRole());
			}
		}
		//存放库表信息，减少查询次数
		Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
		//存放字段信息，减少查询次数
		Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
		boolean first=true;
		for(AuthorityRole role : roleOwnerList){
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());
			StringBuffer dataBuffer = new StringBuffer();
			for(AuthorityData data : dataList){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals("true")){
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
					if(fieldMap.get(data.getFieldInfo())==null){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//判断该角色下拥有的数据权限是否跟传入的表名称相同，只返回相同表的数据权限
					boolean isEql = false;
					for(int x=0;x<tableName.length;x++)
					{
						if(dti!=null&&(dti.getTableEname().toUpperCase().equals(tableName[x].toUpperCase())||
								changeTableToHql(dti.getTableEname()).toUpperCase().equals(
										tableName[x].toUpperCase()))){
							isEql = true;
						}
						
					}
					//只取对应表的数据
					if(isEql){
						//取回该角色对应的权限串
						String authSql = userAuthorityData(data,person,isSql,withTable,dfi,dti);
						authSql = authSql.replace("{alias}.", "");
						//每个角色下的数据权限之间用and相联
						if(!dataBuffer.toString().equals("")&&!authSql.equals(""))
							dataBuffer.append(" and ");
						dataBuffer.append(authSql);
					}
				}
				
			}
			//区域权限
			String regionAuthory=authorityUserRoleManager.getRegionAuthorityByTableAndRole(person,tableName,role,isSql, withTable);
			
			if(!dataBuffer.toString().equals("")){
				first=false;
				//不同角色之间用or连接
			    if(hql.length()>1){
			    	for(AuthorityData data : dataList){
			    		if(data.getSelectAble()!=null){
			    			hql.append(" or ( ");
			    			hql.append(dataBuffer);
			    			hql.append(regionAuthory);
			    			hql.append(")");
			    			break;
			    		}
			    	}
				}else{
					//首个角色权限串前不加or
					for(AuthorityData data : dataList){
						if(data.getSelectAble()!=null){
							hql.append(" ( ");
							hql.append(dataBuffer);
							hql.append(regionAuthory);
							hql.append(")");
							break;
						}
					}
				}
			}else{
				//去掉and 防止转换sql时出现错误
				if(regionAuthory.indexOf("and")>=0){
					regionAuthory = regionAuthory.substring(regionAuthory.indexOf("and")+3);
				}
				if(!first){
					hql.append(" or ");
				}
				if(first){
					first=false;
				}
				hql.append(regionAuthory);
			}
		  }
		
		  if(!hql.toString().equals("")){
			 hql.insert(0, " ( ");
			 hql.append(" )");
		  }
		  //添加是否仅限本人权限
		  String limitFlagAuthory=getAuthorityByLimitFlag(person,tableName,isSql,withTable);
		  if(!limitFlagAuthory.equals("")){
			  hql.append(limitFlagAuthory);
		  }
	    }
		if(!hql.toString().equals("")){
			hql.insert(0, " and ( ");
			hql.append(" )");
		}
		return hql.toString();
	}
	
	/**
	 * 根据用户权限返回标准查询中查询项集合
	 * @param person 当前用户
	 * @param tableName 要查询的表名称
	 * @return Criterion组
	 */
	@Transactional(readOnly=true)
	public ArrayList<Criterion> getMultiAuthorityCriterionList(PersonInfo person,String[] tableName){		
		ArrayList<Criterion> criList = new ArrayList<Criterion>();
		StringBuffer hql = new StringBuffer();
		if(person!=null){
		//得到该用户所具有的角色权限
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		ArrayList<AuthorityRole> roleOwnerList = new ArrayList<AuthorityRole>();
		//权限分为部门角色与用户角色两部分
		for(AuthorityUserRole aur : roleList){
			roleOwnerList.add(aur.getRole());
		}
		Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
		Iterator<OrganPersonRelationInfo> it = set.iterator();
		while(it.hasNext()){
			OrganPersonRelationInfo opr = it.next();
			List<AuthorityDeptRole> deptList = authorityDeptRoleDao.
			findBy("organization", opr.getOrganizationInfo().getId());
			for(AuthorityDeptRole deptRole : deptList){
				roleOwnerList.add(deptRole.getRole());
			}
		}
		//存放库表信息，减少查询次数
		Map<String,DatabaseTableInfo> tableMap = new HashMap<String,DatabaseTableInfo>();
		//存放字段信息，减少查询次数
		Map<String,DatabaseFieldInfo> fieldMap = new HashMap<String,DatabaseFieldInfo>();
		boolean firstRole = true;
		for(AuthorityRole role : roleOwnerList){
			//找出该角色下所有的数据权限
			List<AuthorityData> dataList = authorityDataDao.findBy("role.id", role.getId());

			StringBuffer dataBuffer = new StringBuffer();
			for(AuthorityData data : dataList){
				//判断用户是否有读取权限
				if(data.getSelectAble()!=null&&data.getSelectAble().equals("true")){
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
					if(fieldMap.get(data.getFieldInfo())==null){
						dfi = databaseFieldInfoDao.findByUnique("id", data.getFieldInfo());
						fieldMap.put(data.getFieldInfo(),dfi);
					}else{
						dfi = fieldMap.get(data.getFieldInfo());
					}
					//判断该角色下拥有的数据权限是否跟传入的表名称相同，只返回相同表的数据权限
					boolean isEql = false;
					for(int x=0;x<tableName.length;x++)
					{
						if(dti!=null&&(dti.getTableEname().toUpperCase().equals(tableName[x].toUpperCase())||
								changeTableToHql(dti.getTableEname()).toUpperCase().equals(
										tableName[x].toUpperCase()))){
							isEql = true;
						}
					}
					//只取对应表的数据
					if(isEql){
						//取回该角色对应的权限串
						String authSql = userAuthorityData(data,person,true,false,dfi,dti);
						//每个角色下的数据权限之间用and相联
						if(!dataBuffer.toString().equals("")&&!authSql.equals(""))
							dataBuffer.append(" and ");
						dataBuffer.append(authSql);
					}
				}
				
			}
			if(!dataBuffer.toString().equals("")){
			//不同角色之间用or连接
			if(!firstRole){
				for(AuthorityData data : dataList){
					if(data.getSelectAble()!=null){
						hql.append(" or ( ");
						hql.append(dataBuffer);
						hql.append(")");
						break;
					}
				}
			}else{
				//首个角色权限串前不加or
				for(AuthorityData data : dataList){
					if(data.getSelectAble()!=null){
						hql.append(" ( ");
						hql.append(dataBuffer);
						hql.append(")");
						break;
					}
				}
				firstRole = false;
			}
			}
		}
		}
		if(!hql.toString().equals("")){
			hql.insert(0, " and ( ");
			hql.append(" )");
		}
		String sql = hql.toString();
		//去掉and 防止转换sql时出现错误
		if(sql.indexOf("and")>=0){
			sql = sql.substring(sql.indexOf("and")+3);
		}
		criList.add(Restrictions.sqlRestriction(sql));
		return criList;
	}
	/**
	 * 拼装用户数据权限，用于列表显示
	 * @param userId 用户ID
	 * @return 列表显示数据
	 */
	@Transactional(readOnly=true)
	public String userAuthorityGridData(String userId) {
		List<DatabaseFieldInfo> list = databaseFieldInfoDao.find(Restrictions.eq("isAuthority", "true"));
		//得到已保存过的数据权限,用于拼装页面显示值
		//得到该用户所具有的角色权限
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(userId);
		List<AuthorityData> dataList = new ArrayList<AuthorityData>();
		for(AuthorityUserRole aur : roleList){
			dataList.addAll(authorityDataDao.findBy("role.id", aur.getRole().getId()));
		}
		//将已保存过的字段信息放到MAP中方便比对
		Map<String, AuthorityData> dataMap = new HashMap<String, AuthorityData>();
		for(AuthorityData ad : dataList){
			dataMap.put(ad.getFieldInfo(), ad);
		}
		StringBuffer json = new StringBuffer("{\"totalProperty\":"+list.size());
		json.append(",\"result\":[");
		boolean firstItem = true;
		DatabaseTableInfo dti = null;
		//遍历所有用于权限的库表信息
		for(DatabaseFieldInfo dfi : list){
			dti = dfi.getDatabaseTableInfo();
			if(!firstItem){
				json.append(",");
			}
			json.append("{");
			json.append("\"oid\":\""+dfi.getId()+"\"");
			json.append(",\"tableid\":\""+dti.getId()+"\"");
			json.append(",\"fieldid\":\""+dfi.getId()+"\"");
			//如果没有中文名，则显示英文名
			if(dti.getTableCname()!=null&&!dti.getTableCname().equals("")){
				json.append(",\"groupName\":\""+dti.getTableCname()+"\"");
			}else{
				json.append(",\"groupName\":\""+dti.getTableEname()+"\"");
			}
			json.append(",\"fieldEName\":\""+dfi.getFieldEname()+"\"");
			json.append(",\"fieldCName\":\""+(dfi.getFieldCname()==null?"":dfi.getFieldCname())+"\"");
			//如果字段与码表关联，则将关联信息记录
			if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
				SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
				json.append(",\"codeType\":\"" + (sci==null?"":sci.getCodeType())+ "\"");
			}else{
				json.append(",\"codeType\":\"\"");
			}
			//如果字段与组织机构关联，则将关联信息记录
			if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
				json.append(",\"organField\":\"true\"");
			}else{
				json.append(",\"organField\":\"false\"");
			}
			AuthorityData ad = dataMap.get(dfi.getId());
			//判断是否已经保存过，如果保存过，则将值取出，填回页面
			if(ad!=null){
				json.append(",\"selectAble\":\""+ad.getSelectAble()+"\"");
				json.append(",\"addAble\":\""+ad.getAddAble()+"\"");
				json.append(",\"restrictType\":\""+ad.getRestrictType()+"\"");
				String restrictValue = ad.getAddRestrictValue();
				//判断是否是代码类型，如果是代码类型，转换成文件传回页面
				if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
					if(restrictValue!=null&&!restrictValue.equals("")){
						String[] values = restrictValue.split(";");
						restrictValue = "";
						for(int i=0;i<values.length;i++){
							SystemCodeInfo sci = systemCodeInfoDao.findByUnique("id", dfi.getCodeOid());
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("codeType",sci==null?"null":sci.getCodeType());
							map.put("code", values[i]);
							SystemCodeNodeInfo scni = systemCodeNodeInfoDao.
							findUnique("From SystemCodeNodeInfo Where " +
									"codeType=:codeType and code=:code", map);
							restrictValue += scni.getName()+";";
						}
						json.append(",\"addRestrictValue\":\""+restrictValue+"\"");
					}
				}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
					if(restrictValue!=null&&!restrictValue.equals("")){
						//将ID值转换为中文名称，用于显示
						String[] ids = restrictValue.split(";");
						String organName = "";
						for(int i=0;i<ids.length;i++){
							OrganizationInfo organ = organizationInfoDao.findByUnique("id", ids[i]);
							//只显示未删除和有效的组织机构
							if(organ!=null&&organ.getInvalidFlag().equals("1")&&organ.getOrganizationRelationInfo().size()>0){
								organName+=organ.getOrgShortCname()+";";
							}
						}
						json.append(",\"addRestrictValue\":\""+organName+"\"");
					}
				}else{
					//普通类型可将值直接传回页面
					if(restrictValue==null){
						json.append(",\"addRestrictValue\":\"\"");
					}else{
						json.append(",\"addRestrictValue\":\""+restrictValue+"\"");
					}
				}
				
			}
			json.append("}");
			if(firstItem){
				firstItem = false;
			}
		}
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 通过用户ID，查找其所拥有的所有角色信息
	 * @param userId 用户ID
	 * @return 用户角色关系信息
	 */
	@Transactional(readOnly = true)
	public List<AuthorityUserRole> getRoleIdByUserId(String userId){
		return authorityUserRoleDao.getRoleIdByUserId(userId);
	}
	
	/**
	 * 将角色所具有的数据权限拼装成sql形式或hql形式
	 * @param data 数据权限
	 * @param person 用户信息
	 * @param isSql 需要sql还是hql
	 * @param withTable 是否在字段前加上表名
	 * @param dfi 权限值对应表信息
	 * @param dti 权限值对应字段信息
	 * @return
	 */
	private String userAuthorityData(AuthorityData data,PersonInfo person ,boolean isSql,boolean withTable,DatabaseFieldInfo dfi,DatabaseTableInfo dti){
		String restrictValue = "";
		String fieldName = "";//sql中的字段名称
		//根据语句类型，判断选择哪种处理方式
		if(isSql){
			if(withTable){
				fieldName = dti.getTableEname()+"."+dfi.getFieldEname();
			}else{
				fieldName = "{alias}."+dfi.getFieldEname();
			}
		}else {
			if(withTable){
				fieldName = changeTableToHql(dti.getTableEname())+"."+
						changeColumnToHql(dfi.getFieldEname());
			}else {
				fieldName = "{alias}."+changeColumnToHql(dfi.getFieldEname());
			}
		}
		StringBuffer hql = new StringBuffer();
		if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals(""))
		{
			//判断是否是数据字典类型
			if(dfi.getCodeOid()!=null&&!dfi.getCodeOid().equals("")){
				//判断该数据权限是否需要处理空值
				if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
					restrictValue = data.getSelectRestrictValue();
					//将空值补进权限串 如：('1','2','3','')
					restrictValue = restrictValue.replace(";", "','");
					if(!restrictValue.equals("")){
						hql.append("(").append(fieldName).append(" is null or ");
						hql.append(fieldName).append(" in ('"+restrictValue+"'))");
					}
				}else{
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.substring(0,restrictValue.length()-1);
					restrictValue = restrictValue.replace(";", "','");
					if(!restrictValue.equals(""))
						hql.append(fieldName).append(" in ('"+restrictValue+"')");
				}
			//判断是否是组织机构类别，如果是组织机构类别的权限，除了将已选择的机构权限加上，还要把当前人员所在部门权限加上
			}else if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
				//判断该数据权限是否需要处理空值
				if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.replace(";", "','");
					restrictValue = "'"+restrictValue+"'";
					//得到用户所属机构信息
					Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
					Iterator<OrganPersonRelationInfo> it = set.iterator();
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						restrictValue+=",";
						restrictValue+="'"+opri.getOrganizationInfo().getId()+"'";
					}
					if(!restrictValue.equals("")){
						hql.append("(").append(fieldName).append(" is null or ");
						hql.append(fieldName).append(" in ("+restrictValue+"))");
					}
				}else{//不需要处理空值，正常处理数据
					restrictValue = data.getSelectRestrictValue();
					restrictValue = restrictValue.substring(0,restrictValue.length()-1);
					restrictValue = restrictValue.replace(";", "','");
					restrictValue = "'"+restrictValue+"'";
					//得到用户所属机构信息
					Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
					Iterator<OrganPersonRelationInfo> it = set.iterator();
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						restrictValue+=",";
						restrictValue+="'"+opri.getOrganizationInfo().getId()+"'";
					}
					if(!restrictValue.equals(""))
						hql.append(fieldName).append(" in ("+restrictValue+")");
				}
			}//其他文本框输入的权限值拼装
			else if(data.getSelectRestrictValue()!=null&&!data.getSelectRestrictValue().equals("")){
				//判断是否包含';'，如果有则表示值中包括多项，按';'进行分隔
				if(!data.getSelectRestrictValue().contains(";")){
					//判断该数据权限是否需要处理空值
					if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
						hql.append("( ").append(fieldName).append(" is null or ").append(fieldName);
						//判断是否需要模糊查询
						if(data.getSelectRestrictValue().contains("%")){
							hql.append(" like '"+data.getSelectRestrictValue()+"' or ").append(fieldName)
							.append("='' )");
						}else{
							hql.append("='"+data.getSelectRestrictValue()+"' or ").append(fieldName)
							.append("='' )");
						}
					}else{
						//判断是否需要模糊查询
						if(data.getSelectRestrictValue().contains("%")){
							hql.append(fieldName).append("like '"+data.getSelectRestrictValue()+"'");
						}else{
							hql.append(fieldName).append("='"+data.getSelectRestrictValue()+"'");
						}
					}
				}else{
					//判断该数据权限是否需要处理空值
					if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
						hql.append("( ").append(fieldName).append(" is null ");
						restrictValue = data.getSelectRestrictValue();
						String[] values = restrictValue.split(";");
						for(String v : values){
							hql.append(" or ").append(fieldName).append(" like '").append(v).append("'");
						}
						hql.append(" or ").append(fieldName).append(" like '' )");
					}else{
						restrictValue = data.getSelectRestrictValue();
						hql.append("( ");
						String[] values = restrictValue.split(";");
						boolean first = true;
						for(String v : values){
							if(first)
								hql.append(fieldName).append(" like '").append(v).append("'");
							else
								hql.append(" or ").append(fieldName).append(" like '").append(v).append("'");
							if(first)
								first = false;
						}
						hql.append(")");
					}
				}
			}
		}else{
			//如果不控制数据值，先判断是否是关联了部门权限，如果关联，只取本部门数据
			if(dfi.getIsOrgan()!=null&&dfi.getIsOrgan().equals("true")){
				//hql.append( " and ");
				//得到用户所属机构信息
				Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
				Iterator<OrganPersonRelationInfo> it = set.iterator();
				boolean first = false;
				//判断该数据权限是否需要处理空值
				if(data.getRestrictType()!=null&&data.getRestrictType().equals("true")){
					hql.append("( ").append(fieldName).append(" is null or ")
					.append(fieldName).append(" in ( ''");
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						hql.append(",");
						hql.append("'"+opri.getOrganizationInfo().getId()+"'");
					}
					hql.append(")");
				}else{
					hql.append(fieldName).append(" in (");
					//遍历所有用户机构信息
					while(it.hasNext()){
						OrganPersonRelationInfo opri = it.next();
						if(first){
							hql.append(",");
						}
						hql.append("'"+opri.getOrganizationInfo().getId()+"'");
						if(!first){
							first = true;
						}
					}
				}
				hql.append(")");
			}
		}
		return hql.toString();
	}
	
	/**
	 * 是否仅限本人权限串
	 * @param tableName
	 * @param isSql
	 * @param withTable
	 * @return
	 */
	public String getAuthorityByLimitFlag(PersonInfo person,String[] tableName,boolean isSql,boolean withTable){
		StringBuffer buf=new StringBuffer("");
		for(String t:tableName){
			if("PETITION_BASIC_INFO".equals(t.toUpperCase())||
					changeTableToHql("PETITION_BASIC_INFO").toUpperCase().equals(
							t.toUpperCase())){
				String fieldName = "";
				//根据语句类型，判断选择哪种处理方式，拼装表名称与字段名称
				if(isSql){
					if(withTable){
						fieldName = "PETITION_BASIC_INFO"+"."+"CREATOR_ID";
					}else{
						fieldName = "CREATOR_ID";
					}
				}else{
					if(withTable){
						fieldName = changeTableToHql("PETITION_BASIC_INFO")+"."+
								changeColumnToHql("CREATOR_ID");
					}else{
						fieldName = changeColumnToHql("CREATOR_ID");
					}
				}
				Session session = organPersonRelationDao.getSession();
				Criteria criteria = session.createCriteria(OrganPersonRelationInfo.class,"o");
				criteria.add(Restrictions.eq("personInfo.id", person.getId()));
				criteria.add(Restrictions.eq("limitFlag","1"));
				int size = (Integer)criteria.setProjection(Projections.countDistinct("id")).uniqueResult();
				if(size==1){
					buf.append(" and ");
					buf.append(fieldName);
					buf.append("=");
					buf.append("'"+person.getId()+"'");
				}
			}
		}
		return buf.toString();
	}
	
	/**
	 * @Description:根据功能菜单的url，判断当前功能菜单，所在用户的角色
	 * @Title: queryRoleByMenuUrl
	 * @author: lixl  
	 * @date: 2014年8月13日 下午4:14:16 
	 * @param menuUrl
	 */
	public List<AuthorityRole> queryRoleByMenuUrl(PersonInfo person,String menuUrl){
		//首先得到当前用户所有的角色权限信息
		List<AuthorityUserRole> roleList = authorityUserRoleDao.getRoleIdByUserId(person.getId());
		List<AuthorityRole> roleOwnerList = new ArrayList<AuthorityRole>();
		for(AuthorityUserRole aur : roleList){
			Set<AuthorityMenu> authorityMenuSet = 	aur.getRole().getRoleMenu();
			for(AuthorityMenu authorityMenu : authorityMenuSet){
				String menuId = authorityMenu.getMenu();
				String menuUrl_db = queryMenuUrlByMenuId(menuId);
				if(menuUrl.equals(menuUrl_db)){
					roleOwnerList.add(aur.getRole());
					break;
				}
			}
		}
		return roleOwnerList;
	}
	
	/**
	 * @Description:根据功能菜单的ID，查询对应的url
	 * @Title: queryMenuUrlByMenuId
	 * @author: lixl  
	 * @date: 2014年8月13日 下午4:47:20 
	 * @param menuId
	 * @return
	 */
	public String queryMenuUrlByMenuId(String menuId){
		Criteria criteria = menuItemInfoDao.getSession().createCriteria(MenuItemInfo.class);
		criteria.add(Restrictions.eq("id", menuId));
		criteria.setProjection(Projections.property("menuUrl"));
		Object object = criteria.uniqueResult();
		if(object!=null){
			return object.toString();
		}
		return "";
	}
}
