package com.sinosoft.authority.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityDeptRoleDao;
import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.authority.dao.AuthorityRoleDao;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.menu.dao.MenuItemInfoDao;
import com.sinosoft.menu.domain.MenuItemInfo;
import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**
 * 部门角色权限Manager层
 * @author sunzhe
 * Create 2010-08-10
 */
@Service
@Transactional
public class AuthorityDeptRoleManager extends EntityManager<AuthorityDeptRole, String>{

	//注入部门角色权限信息dao层
	@Autowired
	AuthorityDeptRoleDao authorityDeptRoleDao;

	//注入机构关系dao层
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	
	//注入机构信息dao层
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	
	//注入角色dao层
	@Autowired
	AuthorityRoleDao authorityRoleDao;
	
	//注入菜单信息dao层
	@Autowired
	MenuItemInfoDao menuItemInfoDao;
	
	//注入菜单权限信息dao层
	@Autowired
	AuthorityMenuDao authorityMenuDao;
	
	@Override
	protected HibernateDao<AuthorityDeptRole, String> getEntityDao() {
		return authorityDeptRoleDao;
	}

	/**
	 * 分级加载树
	 * 2010-07-12
	 * 用于权限组件配置部门
	 * @param id 树节点ID
	 * @param flag 是否需要多选框
	 * @param roleId 角色ID
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String buildAuthorityDeptTree(String id,boolean flag,String roleId){
		StringBuffer treeJson = new StringBuffer("[");
		AuthorityRole authorityRole = null;
		String[] selected = null;
		String start = "";
		String deptId = "";
		//存放机构信息，减少查询次数
		Map<String,OrganizationInfo> organMap = new HashMap<String,OrganizationInfo>();
		if(roleId!=null&&!roleId.equals("")){
			//得到角色对象
			authorityRole = authorityRoleDao.get(roleId);
			Set<AuthorityDeptRole> deptRole = authorityRole.getRoleDept();
			Iterator<AuthorityDeptRole> it = deptRole.iterator();
			//得到所有已选择的节点，然后拼到一起
			while(it.hasNext()){
				AuthorityDeptRole dept = it.next();
				deptId += dept.getOrganization()+";";
				//机构信息
				OrganizationInfo organInfo = null;
				if(organMap.get(dept.getOrganization())==null){
					organInfo = organizationInfoDao.findByUnique("id", dept.getOrganization());
					organMap.put(dept.getOrganization(),organInfo);
				}else{
					organInfo = organMap.get(dept.getOrganization());
				}
				start += idCycle(organInfo)+";";
			}
			//重新过滤一遍，把重复的去掉
			selected = rebuildIdCycle(start);
		}
		
			List<OrganizationRelationInfo> list = null ;
			//判断是否是根节点 
			if(id==null||id.equals("root")||id.equals("-1")||(id.split("-")[0].equals("xnode"))){
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo " +
						"WHERE organizationRelationInfo is null  order by organizationInfo.id");
			}else {
				list = organizationRelationInfoDao.find("FROM OrganizationRelationInfo " +
						"WHERE  organizationRelationInfo.id='"+id+"' order by organizationInfo.id");
			}
			
			boolean firstItem = true;
			OrganizationRelationInfo sci;
			String organId = "";
			for(int i=0;i<list.size();i++){

				sci = list.get(i);
				organId = sci.getOrganizationInfo().getId();
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				//关系表中的ID
				treeJson.append("id:'" + sci.getId()+ "'");
				treeJson.append(",text:'" + sci.getOrganizationInfo().getOrgCname()+ "'");
				//组织机构的ID
				treeJson.append(",organizationInfoId:'" + organId + "'");				
				
				int size=organizationRelationInfoDao.buildMergeDeptTree(sci.getId());
				//判断是否含有子节点
				if(size>0){
					treeJson.append(",leaf:false" );
				}else{
					treeJson.append(",leaf:true" );
				}
				if(sci.getOrganizationInfo().getPurpose().trim().equals("1")){
					treeJson.append(",iconCls:'chart_organisation'" );
				}else if(sci.getOrganizationInfo().getPurpose().trim().equals("2")){
					treeJson.append(",iconCls:'config'" );
				}else{
					treeJson.append(",iconCls:'cog'" );
				}
				treeJson.append(",expanded:false");
				
				
				//如果flag为true，带有复选框
				if(flag){
					treeJson.append(",checked:false");
				}
				
				if(deptId.indexOf(organId)>=0){
					treeJson.append(",checked:true");
				}
				//根据已选择的节点集合，重新生成树的下级节点
				if(selected!=null){
					for(int x=0;x<selected.length;x++){
						String[] rootNode = selected[x].split(",");
					for(int j=0;j<rootNode.length;j++){
						if(rootNode[j].equals(organId)){
							treeJson.append(",expanded:true,allowChildren:true,children:");
							treeJson.append(buildAuthorityDeptTree(sci.getId(),true,roleId));
						}
					}
					}
				}
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			treeJson.append("]");
			return treeJson.toString();	
	}
	/**
	 * 从当前机构出发，将其上的所有机构全部查出来
	 * @param curOrgan
	 * @return id字符串
	 */
	private String idCycle(OrganizationInfo curOrgan){
		String result = curOrgan.getId()+",";
		Set<OrganizationRelationInfo> set = curOrgan.getOrganizationRelationInfo();
		Iterator<OrganizationRelationInfo> it = set.iterator();
		//将所有ＩＤ拼装起来
		while(it.hasNext()){
			OrganizationRelationInfo ori = it.next();
			if(ori.getOrganizationRelationInfo()!=null)
			result+=idCycle(ori.getOrganizationRelationInfo().getOrganizationInfo());
		}
		
		return result.endsWith(",")?result.substring(0, result.length()-1):result;
	}
	
	/**
	 * 将拼装好的ID组整理成固定格式，用于过滤
	 * @param ids　一组ＩＤ
	 * @return　整理好的ＩＤ字符串
	 */
	private String[] rebuildIdCycle(String ids){
		String[] splitId = ids.split(";");
		//整理ＩＤ格式
		for(int i=0;i<splitId.length;i++){
			String result = "";
			if(!splitId[i].equals("")){
				String[] id = splitId[i].split(",");
				for(int j=id.length-1;j>=0;j--){
					if(j>=0&&j<id.length-1){
						result+=",";
					}
					result+=id[j];
				}
			}
			splitId[i]=result;
		}
			
		return reCompareId(splitId);
	}
	
	/**
	 * 将拼装好的ID组重新过滤，去掉重复项
	 * @param ids　ＩＤ字符串
	 * @return　没有重复信息的ＩＤ字符串
	 */
	private String[] reCompareId(String[] ids){
		int maxSize = 0;
		for(int i=0;i<ids.length;i++){
			if(ids[i].split(",").length>maxSize){
				maxSize = ids[i].split(",").length;
			}
		}

		String[] save = new String[maxSize];
		//在循环中去掉重复信息
		for(int i=0;i<ids.length;i++){
			String[] temp = ids[i].split(",");
			for(int j=0;j<temp.length;j++){
				if(save[j]==null){
					save[j] = "";
				}
				if(save[j].indexOf(temp[j])<0){
					save[j] = save[j]+","+temp[j];	
				}
			}
		}

		return save;
	}
	
	/**
	 * 根据条件查找部门角色，返回所有结果
	 * @param propertyName 查询条件
	 * @param value  查询值
	 * @return 部门角色列表
	 */
	@Transactional(readOnly=true)
	public List<AuthorityDeptRole> findBy(String propertyName,Object value){
		return authorityDeptRoleDao.findBy(propertyName, value);
	}
	
	/**
	 * 根据条件查找部门角色，只返回有效角色
	 * @param propertyName 查询条件
	 * @param value  查询值
	 * @return 部门角色列表
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public List<AuthorityDeptRole> findInvalidBy(String propertyName,Object value) throws ParseException{
		List<AuthorityDeptRole> result = new ArrayList<AuthorityDeptRole>();
		List<AuthorityDeptRole> list = authorityDeptRoleDao.findBy(propertyName, value);
		for(AuthorityDeptRole adr : list){
			AuthorityRole ar = adr.getRole();
			//只增加有效角色
			if(ar.getInvalidFlag().equals("1")&&(ar.getExpireDate()==null
					||compareDate(ar.getExpireDate().toString())<0)){
				result.add(adr);
			}
		}
		return result;
	}
	/**
	 * 日期比较，看日期在当前日期前还是后
	 * @param compareDate　要比较的日期
	 * @return　距当前日期距离
	 * @throws ParseException 
	 */
	private long compareDate(String compareDate) throws ParseException{
		Calendar cpcalendar = new GregorianCalendar(); 
		Calendar cpcalendarNow = new GregorianCalendar();
		java.util.Date date = null; 
		java.util.Date today = null;
		java.text.SimpleDateFormat parseTime = null;
		DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
	    String str1 = d1.format(new Date());
		if(compareDate.length()>10){
			parseTime = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			str1 = str1 + " 00:00:00.0";
		}else{
			parseTime = new java.text.SimpleDateFormat("yyyy-MM-dd");
		}
		//可以把数据库中查询的日期放在这里，需要格式一下 
		date = parseTime.parse(compareDate); 
		today = parseTime.parse(str1);
		cpcalendar.setTime(date);
		cpcalendarNow.setTime(today); 
		return (cpcalendarNow.getTimeInMillis() - cpcalendar.getTimeInMillis())-1;
	}
	
	/**
	 * 分级加载树
	 * 2011-03-24
	 * 用于权限组件根据功能菜单反查组织机构
	 * @param id 树节点ID
	 * @param flag 是否需要多选框
	 * @param menuId 功能菜单ID
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String buildAuthorityDeptTreeByMenuId(String id,boolean flag,String menuId){
		List<AuthorityMenu> list = authorityMenuDao.findBy("menu", menuId);
		StringBuffer treeJson = new StringBuffer("[");
		AuthorityRole authorityRole = null;
		String[] selected = null;
		String start = "";
		String deptId = "";
		Set<AuthorityDeptRole> deptRole = new HashSet<AuthorityDeptRole>();
		//存放机构信息，减少查询次数
		Map<String,OrganizationInfo> organMap = new HashMap<String,OrganizationInfo>();
		if(menuId!=null&&!menuId.equals("")){
			//得到角色对象
			for(AuthorityMenu am : list){
				authorityRole = am.getRole();
				deptRole.addAll(authorityRole.getRoleDept());
			}
			Iterator<AuthorityDeptRole> it = deptRole.iterator();
			//得到所有已选择的节点，然后拼到一起
			while(it.hasNext()){
				AuthorityDeptRole dept = it.next();
				deptId += dept.getOrganization()+";";
				//机构信息
				OrganizationInfo organInfo = null;
				if(organMap.get(dept.getOrganization())==null){
					organInfo = organizationInfoDao.findByUnique("id", dept.getOrganization());
					organMap.put(dept.getOrganization(),organInfo);
				}else{
					organInfo = organMap.get(dept.getOrganization());
				}
				start += idCycle(organInfo)+";";
			}
			//重新过滤一遍，把重复的去掉
			selected = rebuildIdCycle(start);
		}
		//处理ID，所有已选择过的组织机构都将记录，并用在查询语句中
		String idBuffer = "";
		for(String selectedId : selected){
			//去掉字符串开头的','
			selectedId = selectedId.substring(1);
			//多个ID组合在一起时将','进行分隔
			String[] temp = selectedId.split(",");
			//将这些已选择过的ID拼在一起
			for(String tempId : temp)
				idBuffer+="'"+tempId+"',";
		}
		if(idBuffer.endsWith(","))
			idBuffer = idBuffer.substring(0,idBuffer.length()-1);

			List<OrganizationRelationInfo> organList = null ;
			if(id==null||id.equals("root")||id.equals("-1")||(id.split("-")[0].equals("xnode"))){
				organList = organizationRelationInfoDao.find("FROM OrganizationRelationInfo " +
						"WHERE organizationRelationInfo is null  order by organizationInfo.id");
			}else {
				organList = organizationRelationInfoDao.find("FROM OrganizationRelationInfo " +
						"WHERE organizationRelationInfo.id='"+id+"' and organizationInfo.id in " +
								"("+idBuffer+") order by organizationInfo.id");
			}
			
			boolean firstItem = true;
			OrganizationRelationInfo sci;
			String organId = "";
			for(int i=0;i<organList.size();i++){

				sci = organList.get(i);
				//通过机构关系，得到机构或岗位信息
				OrganizationInfo oi =  sci.getOrganizationInfo();
				organId = oi.getId();
				if(!firstItem){
					treeJson.append(",");
				}
				//拼装 JSON数据
				treeJson.append("{");
				treeJson.append("id:'" + sci.getId()+ "'");
				//默认显示简称
				treeJson.append(",text:'" + sci.getOrganizationInfo().getOrgShortCname()+ "'");
				treeJson.append(",organizationInfoId:'" + organId + "'");				
				treeJson.append(",organEname:'" + oi.getOrgEname() + "'");	
				treeJson.append(",organShortCname:'" + oi.getOrgShortCname() + "'");	
				int size=organizationRelationInfoDao.buildMergeDeptTree(sci.getId());
				//判断是否含有子节点
				if(size>0){
					treeJson.append(",leaf:false" );
				}else{
					treeJson.append(",leaf:true" );
				}
				if(sci.getOrganizationInfo().getPurpose().trim().equals("1")){
					treeJson.append(",iconCls:'chart_organisation'" );
				}else if(sci.getOrganizationInfo().getPurpose().trim().equals("2")){
					treeJson.append(",iconCls:'config'" );
					treeJson.append(",checked:false");
				}else{
					treeJson.append(",iconCls:'cog'" );
				}
				treeJson.append(",expanded:false");
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
			treeJson.append("]");
			return treeJson.toString();
	
	}
	/**
	 * 分级加载树
	 * 2011-03-24
	 * 用于权限组件根据功能菜单反查组织机构
	 * @param id 树节点ID
	 * @param flag 是否需要多选框
	 * @param menuEname 功能菜单英文名称
	 */
	@Transactional(readOnly=true)
	public String buildAuthorityDeptTreeByMenu(String id,boolean flag,String menuEname){
		MenuItemInfo mii = menuItemInfoDao.findByUnique("menuEname", menuEname);
		if(mii!=null)
			return buildAuthorityDeptTreeByMenuId(id , flag , mii.getId());
		else
			return "";
	}
	
	/**
	 * 通过功能菜单ID查询拥有此菜单的机构
	 * 平煤系统专用
	 * 2011-07-28
	 * @param organs 组织机构关系对象
	 * @param menus 功能菜单ID，多个对象用;分隔
	 * @return 机构关系对象list
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> findDeptByMenu(List<OrganizationRelationInfo> organs,String menus){
		Map<String,Object> values = new HashMap<String,Object>();
		StringBuffer menuIds = new StringBuffer();
		boolean firstItem = true;
		String[] menuId = menus.split(";");
		for(int i=0;i<menuId.length;i++){
			if(!firstItem)
				menuIds.append(",");
			menuIds.append("'");
			menuIds.append(menuId[i]);
			menuIds.append("'");
			if(firstItem)
				firstItem = false;
		}
		values.put("menuIds", menuIds.toString());
		StringBuffer organIds = new StringBuffer();
		firstItem = true;
		for(OrganizationRelationInfo oi : organs){
			if(!firstItem)
				organIds.append(",");
			organIds.append("'");
			organIds.append(oi.getOrganizationInfo().getId());
			organIds.append("'");
			if(firstItem)
				firstItem = false;
		}
		List deptList = authorityDeptRoleDao.find("From AuthorityDeptRole adr,AuthorityMenu am " +
				"Where adr.role.id=am.role.id and am.menu in ("+menuIds.toString()+") and adr.organization in " +
				"("+organIds.toString()+")", values);
		List<OrganizationRelationInfo> relationList = new ArrayList<OrganizationRelationInfo>();
		Map<String,OrganizationRelationInfo> organRelationMap = new HashMap<String,OrganizationRelationInfo>();
		
		for(int i=0;i<deptList.size();i++){
			Object[] obj = (Object[])deptList.get(i);
			AuthorityDeptRole adr = (AuthorityDeptRole) obj[0];
			OrganizationInfo oi = organizationInfoDao.get(adr.getOrganization());
			OrganizationRelationInfo rela = oi.getOrganizationRelationInfo().iterator().next();
			organRelationMap.put(rela.getId(), rela);
		}
		Set<String> key = organRelationMap.keySet();
		Iterator<String> it = key.iterator();
		while(it.hasNext()){
			relationList.add(organRelationMap.get(it.next()));
		}
		return relationList;
	}
	
	/**
	 * 通过功能菜单英文名称查询拥有此菜单的机构
	 * 平煤系统专用
	 * @param organs　组织机构关系对象
	 * @param menuENames　菜单英文名，多个对象用;分隔
	 * @return 机构关系对象list
	 */
	@Transactional(readOnly=true)
	public List<OrganizationRelationInfo> findDeptByMenuEName(List<OrganizationRelationInfo> organs,String menuENames){
		String[] enames = menuENames.split(";");
		String menus = "";
		for(int i=0;i<enames.length;i++){
			menus += menuItemInfoDao.findByUnique("menuEname", enames[i]).getId()+";";
		}
		return findDeptByMenu(organs,menus);
	}
}
