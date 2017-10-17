package com.sinosoft.authority.web;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.dao.AuthorityUserRoleDao;
import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.domain.AuthorityDeptRole;
import com.sinosoft.authority.domain.AuthorityMenu;
import com.sinosoft.authority.domain.AuthorityRole;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.authority.manager.AuthorityDataManager;
import com.sinosoft.authority.manager.AuthorityDeptRoleManager;
import com.sinosoft.authority.manager.AuthorityMenuManager;
import com.sinosoft.authority.manager.AuthorityRoleManager;
import com.sinosoft.authority.manager.AuthorityUserRoleManager;
import com.sinosoft.authorityAudit.manager.AuthorityAuthorizationInfoManager;
import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.OperationType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.string.StringMapper;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**   
 * 项目名称：authority   
 * 类名称：AuthorityRoleAction   
 * 类描述：角色action层
 * 创建人：sunzhe   
 * 创建时间：2010-08-10   
 */
@Namespace("/authority")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="authority-role.action", type = "redirect")})
public class AuthorityRoleAction extends CrudExtActionSupport<AuthorityRole, String> {
	
	private static final long serialVersionUID = 6718094969199268275L;
	
	/**
	 * 注入角色权限业务层
	 */
	@Autowired
	AuthorityRoleManager authorityRoleManager;
	
	/**
	 * 注入人员角色关系业务层
	 */
	@Autowired
	AuthorityUserRoleManager authoirtyUserRoleManager;
	
	/**
	 * 注入部门角色关系业务层
	 */
	@Autowired
	AuthorityDeptRoleManager authorityDeptRoleManager;
	
	/**
	 * 注入菜单权限业务层
	 */
	@Autowired
	AuthorityMenuManager authorityMenuManager;
	
	/**
	 * 注入数据权限业务层
	 */
	@Autowired
	AuthorityDataManager authorityDataManager;
	
	/**
	 * 注入人员业务层
	 */
	@Autowired
	PersonInfoManager personInfoManager;
	
	/**
	 * 注入授权操作记录信息业务层
	 */
	@Autowired
	AuthorityAuthorizationInfoManager authorityAuthorizationInfoManager;
	
	/**
	 * 用户角色信息dao层
	 */
	@Autowired
	AuthorityUserRoleDao authorityUserRoleDao;
	
	AuthorityRole entity;
	
	/**
	 * 传入的参数名称
	 */
	@AttLog(description="参数名称")
	private String parameterName;
	
	/**
	 * 传入的参数值
	 */
	@AttLog(description="参数值")
	private String parameterValue;
	
	/**
	 * 应用系统ＩＤ
	 */
	@AttLog(description="系统ID")
	private String appSystem;
	
	/**
	 * 部门ＩＤ
	 */
	@AttLog(description="部门ID")
	private String deptId;
	
	/**
	 * 人员ＩＤ
	 */
	@AttLog(description="人员ID")
	private String userId;
	
	/**
	 * 功能菜单ＩＤ
	 */
	@AttLog(description="功能菜单ID")
	private String menuId;
	
	/**
	 * 父级功能菜单ＩＤ
	 */
	@AttLog(description="父级功能菜单ID")
	private String parentMenuId;
	
	/**
	 * 角色ＩＤ
	 */
	@AttLog(description="角色ID")
	private String roleId;
	
	/**
	 * 角色失效日期
	 */
	@AttLog(description="角色失效日期")
	private String expire;
	
	/**
	 * 树节点ID
	 */
	@AttLog(description="树节点ID")
	private String node;
	
	/**
	 * 是否记录日志，当enabledLog为false时，不记录日志信息
	 */
	@AttLog(attType=AttType.LOG_ENABLE)
	private String enabledLog;
	
	/**
	 * 操作日志对应描述信息
	 */
	@AttLog(attType=AttType.DESCRIPTION)
	private String operaDesc;
	
	
	/**
	 * 记录操作日志中按钮名称
	 */
	@AttLog(attType=AttType.BUTTON)
	private String btnName;
	
	/**
	 * 记录操作日志中操作描述
	 */
	@AttLog(attType=AttType.DESCRIPTION)
	private String desp;
	
	/**
	 * 记录操作日志中操作功能代码
	 */
	@AttLog(attType=AttType.FUNCTION_CODE)
	private String functionCode;
	
	/**
	 * 记录操作日志中操作功能名称
	 */
	@AttLog(attType=AttType.FUNCTION_NAME)
	private String functionName;
	
	/**
	 * 获取系统信息
	 * @throws IOException
	 * @throws Exception
	 */
	public void getDataByType() throws IOException, Exception {
		List<AuthorityRole> list = new ArrayList<AuthorityRole>();
		list = authorityRoleManager.getDataByType(parameterName, parameterValue);
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(),
				new String[] { "hibernateLazyInitializer", "handler","ipaddr","ipport",
			"isControl","isOpen","menuItemInfo"}, getDatePattern());
	}
 
	/**
	 * 获取角色信息JSON形式
	 * @throws IOException
	 * @throws Exception
	 */
	public void getRoleComboJson() throws IOException, Exception {
		List<AuthorityRole> list = new ArrayList<AuthorityRole>();
		list = authorityRoleManager.getAll();
		JsonUtils.write(list, Struts2Utils.getResponse().getWriter(),
				new String[] { "hibernateLazyInitializer", "handler","ipaddr","ipport",
			"isControl","isOpen","menuItemInfo","system","rolePerson","roleDept","roleMenu",
			"visitUrl","operator","operateDate","expireDate","description"}, getDatePattern());
	}
	
      public String beforeSave(AuthorityRole model) {
	    String regionCode = null;
	    String regionName = null;
	    PersonInfo personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
	    if(personInfo !=null){
	    	regionCode = personInfo.getRegionCode();
	    	regionName = personInfo.getRegionName();
	    }
	    model.setRegionCode(regionCode);
	    model.setRegionName(regionName);
	    return null;
	  }
	
	/**
	 * 保存角色信息
	 */
	@Override
	public void save() throws Exception
	{   
		beforeSave(this.entity);
		boolean flag = false;
		if(entity.getId()!= null){//该角色是否存在有用户关联
			flag = true;
		}
		//对日期进行格式转换
		if(expire!=null&&!expire.equals("")){
			entity.setExpireDate(string2Time(expire));
		}
		
		List<AuthorityData> objList = new ArrayList<AuthorityData>();
		//判断页面传回的数据权限是否有值
		if(data!=null&&!data.equals("")){
			objList = JsonUtils.json2List(data,AuthorityData.class);
		}
		authorityRoleManager.save(entity,appSystem,menuId,parentMenuId,objList);
		if(flag){//如果存在关联的用户则对角色的操作同样认为是对用户权限的操作,记录其操作信息	
			List<AuthorityUserRole> userRoleList = authorityUserRoleDao.getUserIdByRoleId(entity.getId());//得到与该角色关联的所有用户
			for(AuthorityUserRole userRole: userRoleList){
				authorityAuthorizationInfoManager.saveAuthorizationInfoByUserId(userRole.getPerson(),
						entity.getId(),"Update"); 
              // 目录系统时解注该部分代码
				//authorityAuthorizationInfoManager.saveAuthorizationInfoByPerson(userRole.getPerson(),entity.getId(),"Update");           	
			}
			
		}
	    Struts2Utils.getResponse().getWriter().print("{success:true}");
	}
	
	/**
	 * 保存角色配置信息 对角色赋予部门，人员，菜单功能
	 * @throws Exception
	 */
	public void saveConfigRole() throws Exception
	{
		prepareModel();
		authorityRoleManager.saveConfigRole(entity,deptId,userId,menuId,parentMenuId);
	    Struts2Utils.getResponse().getWriter().print("{success:true}");
	}
	
	/**
	 * 保存角色配置信息 对角色赋予部门，人员，清空之前保存信息
	 * @throws Exception
	 */
	public void saveDeptUserRole() throws Exception
	{
		prepareModel();
		authorityRoleManager.saveDeptUserRole(roleId.split(","),deptId,userId);
	    Struts2Utils.getResponse().getWriter().print("{success:true}");
	}
	/**
	 * 保存角色配置信息 对角色赋予部门，人员，不清空之前保存信息，只在原来基础上进行添加
	 * @throws Exception 
	 */
	public void addDeptUserRole() throws Exception 
	{
		prepareModel();
		authorityRoleManager.addDeptUserRole(roleId.split(","),deptId,userId);
	    Struts2Utils.getResponse().getWriter().print("{success:true}");
	}
	/**
	 * 通过角色查找所有人员,并返回角色下人员数量
	 * @throws IOException
	 * @throws Exception
	 */
	public void getRoleUser() throws IOException, Exception {
		List<AuthorityUserRole> list = authoirtyUserRoleManager.findBy("role.id",id);
		Struts2Utils.getResponse().getWriter().print(list.size());
	}
	
	/**
	 * 查询是否存在角色相关属性的方法
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void judgeExist() throws Exception{
		int pageNo = (start / limit) + 1;
		ExtjsPage<AuthorityRole> page = null;
		Criteria criteria = authorityRoleManager.createCriteria();
		//查询filterTxt,filterValue是否已存在
		criteria = criteria.add(Restrictions.eq(filterTxt, filterValue));
		//角色名称，只去判断角色代码为null和角色代码为本级的不可以为空
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		if(onlineUser!=null){
//			criteria.add(Restrictions.or(Restrictions.isNull("regionCode"), 
//					Restrictions.eq("regionCode", onlineUser.getRegionCode())));
		}
		if(null != this.id && !"".equals(this.id)){
			criteria = criteria.add(Restrictions.ne("id", this.id));
		}
		page = authorityRoleManager.pagedQuery(criteria, pageNo, limit);
		List<AuthorityRole> loadingUnitList = page.getResult();
		if(loadingUnitList.size() == 0){
			Struts2Utils.getResponse().getWriter().print(false);
		}else {
			Struts2Utils.getResponse().getWriter().print(true);
		}
	}

	/**
	 * 封装渲染数据所需的参数
	 * @return String[] 渲染数据所需的参数
	 * @author sunzhe
	 */
	public String[] getExcludes() {
		return new String[] { "hibernateLazyInitializer","handler","rolePerson","roleMenu",
				"ipaddr","ipport","isControl","isOpen","menuItemInfo","systemCode",
				"systemDescription","url","roleDept","person","organPersonRelationInfo",
				"organMergeSplitInfo"};
	}
	
	/**
	 * 获取已保存角色信息
	 * @throws Exception
	 */
	public void getLoadData() throws Exception
    {
        AuthorityRole model = authorityRoleManager.get(id);
        if(model != null){
            JsonUtils.write(model, Struts2Utils.getResponse().getWriter(), new String[]{"hibernateLazyInitializer","handler"
                	,"roleDept","organPersonRelationInfo","personInfo","organizationRelationInfo","organizationInfo"
                	,"roleMenu","rolePerson","menuItemInfo"}, getDatePattern());
        }
    }
	/**
	 * 删除角色 并删除其关联的一切信息
	 */
	@Override
	public void remove() throws Exception
	{
		String as[];
	    String success="";
        int j = (as = ids.split(",")).length;
        for(int i = 0; i < j; i++)
        {
            String str = as[i];
            	//删除角色时，要删除其关联的数据
                List<AuthorityUserRole> list = authoirtyUserRoleManager.findBy("role.id", str);
         
                if(list.size()>0){
                	success="{success:"+list.size()+"}";
                }else{
	               /* List<AuthorityDeptRole> dlist = authorityDeptRoleManager.findBy("role.id", str);
	                //删除部门角色关联信息
	                for(AuthorityDeptRole adr : dlist){
	                	authorityDeptRoleManager.delete(adr.getId());
	                }*/
	                List<AuthorityMenu> mlist = authorityMenuManager.findBy("role.id", str);
	                //删除菜单权限
	                for(AuthorityMenu am : mlist){
	                	authorityMenuManager.delete(am.getId());
	                }
	                /*List<AuthorityData> alist = authorityDataManager.findBy("role.id", str);
	
	                //删除数据权限
	                for(AuthorityData ad : alist){
	                	authorityDataManager.delete(ad.getId());
	                }
	*/
	                authorityRoleManager.delete(str);
	                //记录该操作关联的所有人员权限的变动信息   
	                //删除人员角色关联信息
	                for(AuthorityUserRole aur : list){
	                	authoirtyUserRoleManager.delete(aur.getId());
	                }
	                for(AuthorityUserRole userRole: list){  	
	                	authorityAuthorizationInfoManager.saveAuthorizationInfoByUserId(userRole.getPerson(),(String)id,"Del");           	
	                     //目录系统下时解注该部分代码
	                	//authorityAuthorizationInfoManager.saveAuthorizationInfoByPerson(userRole.getPerson(),(String)id,"Del");           	
	                };
	            	success="{success:true}";
                }
                
        }

        Struts2Utils.getResponse().getWriter().print(success);
	}
	
	/**
	 * 根据人员或部门，查看其所拥有的角色
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchRole() throws Exception {
		String order = "";
		if(sort != null&&!sort.equals(""))
		{
			order = "order by "+sort+" "+dir;
		} 
		ExtjsPage page = new ExtjsPage();
		List<AuthorityRole> roleList = new ArrayList<AuthorityRole>();
	    PersonInfo personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
	    String regionCode = ""; 
	    if (personInfo != null) {
	        regionCode = personInfo.getRegionCode();
	    }
		if(filterTxt != null && filterValue != null && !filterTxt.equals("") && !filterValue.equals(""))
		{
			if(filterTxt.equals("userId")){
				//根据人员查询所拥有的角色
				List<AuthorityUserRole> list = authoirtyUserRoleManager.findBy("person", filterValue);
				
				//拼装人员所具有的角色数据
				for(AuthorityUserRole aur : list){
					AuthorityRole role = aur.getRole();
					role.setExpireDate(aur.getExpireDate());
					role.setRoleResource("user");//保存角色来源，用于显示
					roleList.add(role);
				}
				
				PersonInfo person = personInfoManager.get(filterValue);
				Set<OrganPersonRelationInfo> set = person.getOrganPersonRelationInfo();
				Iterator<OrganPersonRelationInfo> it = set.iterator();
				//查找人员所在部门的角色数据
				while(it.hasNext()){
					OrganPersonRelationInfo opri = it.next();
					List<AuthorityDeptRole> organList = authorityDeptRoleManager
					.findBy("organization", opri.getOrganizationInfo().getId());
					for(AuthorityDeptRole adr : organList){
						AuthorityRole role = adr.getRole();
						role.setRoleResource("dept");//保存角色来源,用于显示
						roleList.add(role);
					}
				}
				page.setResult(roleList);
			}
			//根据部门查询部门所拥有的角色
			else if(filterTxt.equals("organId")){
				List<AuthorityDeptRole> list = authorityDeptRoleManager.findBy("organization", filterValue);
				for(AuthorityDeptRole adr : list){
					roleList.add(adr.getRole());
				}
				page.setResult(roleList);
			//角色查询
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("roleName", filterValue);
				//map.put("roleCode", filterValue);
				//roleList = authorityRoleManager.find("From AuthorityRole Where (roleName like :roleName" +
				//		" or roleCode like :roleCode) and invalidFlag='1' "+order, map);
				roleList = authorityRoleManager.find("From AuthorityRole Where roleName like :roleName" +
						"  and invalidFlag='1' and regionCode='"+regionCode+"'"+order, map);
				page.setResult(roleList);
			}
		//无条件时，显示全部有效角色
		}else if(filterValue==null||filterValue.equals("")){
			Map<String,Object> map = new HashMap<String,Object>();
			roleList =  authorityRoleManager.find("From AuthorityRole  where invalidFlag='1' and regionCode='"+regionCode+"'"+order, map);
			page.setResult(roleList);
		}
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), getExcludes(), getDatePattern());
}
	@Override
	 public String beforeQuery(Criteria criteria)
	  {
	    Disjunction disJunction = Restrictions.disjunction();
	    PersonInfo personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
	    if (personInfo != null) {
	      String regionCode = personInfo.getRegionCode();
	      disJunction.add(Restrictions.eq("regionCode", regionCode));
	      disJunction.add(Restrictions.isNull("regionCode"));
	    }
	    criteria.add(disJunction);
	    return null;
	  }
	@SuppressWarnings("unchecked")
	@Override
	public void pagedQuery() throws Exception{
	    int pageNo = this.start / this.limit + 1;
	    ExtjsPage<AuthorityRole> page = null;
	    String str = beforeQuery(this.criteria);
	    if (str == null){
	    	if ((this.filterTxt != null) && (this.filterValue != null) && 
	    			(!(this.filterTxt.equals(""))) && (!(this.filterValue.equals("")))) {
	    		String[] filterT = this.filterTxt.split(";");
	    		String[] filterV = this.filterValue.split(";");
	    		for (int i = 0; i < filterT.length; ++i) {
		    		try {
		    			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
		    					filterT[i], getModel().getClass());
		    			if (filterV[i].indexOf("%") >= 0) {
		    				this.criteria = this.criteria.add(Restrictions.like(
		    						filterT[i], StringMapper.fromString(
		    								filterV[i], 
		    								propertyDescriptor.getPropertyType())));
		    				break;
		    			}
		    			this.criteria = buildWhere(this.criteria,propertyDescriptor.getPropertyType(), filterT[i], filterV[i], 
		    							MatchType.LIKE);
		    		}catch (IntrospectionException e){
		    				this.criteria = this.criteria.add(Restrictions.eq(filterT[i],filterV[i]));
		    		}
	    		}
	    	}
	    	if (this.cache) {
	    		this.criteria.setCacheable(true);
	    		this.criteria.setCacheRegion(getModel().getClass().getName());
	    	}
	    	if ((this.sort != null) && (!("".equals(this.sort)))) {
	    		boolean isAsc = this.dir.equalsIgnoreCase("asc");
	    		if (isAsc)
	    			this.criteria.addOrder(Order.asc(this.sort));
	    		else
	    			this.criteria.addOrder(Order.desc(this.sort));
	    	}else {
	    		this.criteria.addOrder(Order.desc("id"));
	    	}
	    	criteria.add(Restrictions.ne("roleCode", "admin"));
	    	criteria.add(Restrictions.ne("roleCode", "secadmin"));
	    	criteria.add(Restrictions.ne("roleCode", "secauditor"));
	    	page = getEntityManager().pagedQuery(this.criteria, pageNo, this.limit);
	    	page.setPageSize(this.limit);
	    	afterQuery(page);
	    	if (this.isPrint)
	    		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), 
	    				getExcludes(), getDatePattern());
	    } else {
	      Struts2Utils.getResponse().getWriter().print("{success:true,mes:'" + str + "'}");
	   }
	}	
	/**
	 * 根据人员或部门，查看其所拥有的角色,支持分页查询
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void searchRolePagedQuery() throws Exception {
    int pageNo = start / limit + 1;
		String order = "";
    if(sort != null&&!sort.equals("")){
    	order = "order by "+sort+" "+dir;
    } 
    ExtjsPage page = new ExtjsPage();
   // page.setPageNo(pageNo);
    List<AuthorityRole> roleList = new ArrayList<AuthorityRole>();
    if(filterTxt != null && filterValue != null && !filterTxt.equals("") && !filterValue.equals(""))
    {
    	//根据人员查询所拥有的角色
    	if(filterTxt.equals("userId")){
    		List<AuthorityUserRole> list = authoirtyUserRoleManager.findBy("person", filterValue);
    		
    		for(AuthorityUserRole aur : list){
    			roleList.add(aur.getRole());
    		}
    		page.setResult(roleList);
    	}
    	//根据部门查询部门所拥有的角色
    	else if(filterTxt.equals("organId")){
    		List<AuthorityDeptRole> list = authorityDeptRoleManager.findBy("organization", filterValue);
    		for(AuthorityDeptRole adr : list){
    			roleList.add(adr.getRole());
    		}
    		page.setResult(roleList);
    	//角色查询
    	}else{
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("roleName", filterValue);
    		map.put("roleCode", filterValue);
    		roleList = authorityRoleManager.find("From AuthorityRole Where (roleName like :roleName" +
    				" or roleCode like :roleCode ) and invalidFlag='1' "+order, map);
    		
    		page.setResult(roleList);
    	}
    //无条件时，显示全部有效角色
    }else if(filterValue==null||filterValue.equals("")){
    	Map<String,Object> map = new HashMap<String,Object>();
		//roleList =  authorityRoleManager.find("From AuthorityRole  where invalidFlag='1' "+order, map);
    	criteria=criteria.add(Restrictions.eq("invalidFlag", "1"));
    	page=getEntityManager().pagedQuery(criteria, pageNo, limit);
    	page.setPageSize(limit);
		//page.setResult(roleList);
	}
    JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), getExcludes(), getDatePattern());
}
	/**
	 * 根据人员信息或部门信息查询得到角色信息
	 * @throws IOException
	 * @throws Exception
	 */
	public void getUniqueRole() throws IOException, Exception{
		List<AuthorityRole> roleList = new ArrayList<AuthorityRole>();
		if(filterTxt.equals("userId")){
    		List<AuthorityUserRole> list = authoirtyUserRoleManager.findBy("person", filterValue);
    		for(AuthorityUserRole aur : list){
    			roleList.add(aur.getRole());
    		}
    	}
    	else if(filterTxt.equals("organId")){
    		List<AuthorityDeptRole> list = authorityDeptRoleManager.findBy("organization", filterValue);

    		for(AuthorityDeptRole adr : list){
    			roleList.add(adr.getRole());
    		}
    	}
		JsonUtils.write(roleList, Struts2Utils.getResponse().getWriter(), new String[]{"hibernateLazyInitializer","handler"
        	,"roleDept","organPersonRelationInfo","personInfo","organizationRelationInfo","organizationInfo"
        	,"roleMenu","rolePerson","system"}, getDatePattern());
	}
	/**
	 * 转换日期格式
	 * @param dateString　日期
	 * @return　Timestamp　类型的日期
	 * @throws ParseException
	 */
	private Timestamp string2Time(String dateString) throws ParseException{
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//设定格式
		  dateFormat.setLenient(false);
		  java.util.Date timeDate = dateFormat.parse(dateString);//util类型
		  java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
		  return dateTime;
	}
	
	/**
	 * 显示角色树形列表
	 */
	public void showAuthorityTree() throws IOException, Exception {
		String treejson = authorityRoleManager.buildAuthorityRoleTree(node,false,userId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 显示角色树形列表
	 */
	public void showAuthorityTreeChecked() throws IOException, Exception {
		String treejson = authorityRoleManager.buildAuthorityRoleTree(node,true,userId);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 分页查询角色信息，排除用户已有的角色
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void queryRolePagedQuery() throws Exception {
	    int pageNo = start / limit + 1;
	    ExtjsPage page = new ExtjsPage();
	    String roleIds="";
    	String roleNameOrCode="";//角色名或角色区域
    	// 添加根据登录热区域查询所在区域角色信息    yehb 2016-06-17
    	PersonInfo personInfo = (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
	    String regionCode = ""; 
	    if (personInfo != null) {
	        regionCode = personInfo.getRegionCode();
	    }
	    
	    if(filterTxt != null && filterValue != null && !filterTxt.equals("") && !filterValue.equals(""))
	    {
	    	if(filterTxt.indexOf(";")!=-1){
	    		String[] filterT=filterTxt.split(";");
	    		String[] filterV=filterValue.split(";");
	    		for(int i=0;i<filterT.length;i++){
	    			if(filterT[i].equals("roleIds")){
	    				roleIds=filterV[i];
	    			}else if(filterT[i].equals("roleName")){
	    				roleNameOrCode=filterV[i];
	    			}
	    		}
	    	}else{
	    		roleIds=filterValue;
	    	}
	    }
	    if(!roleNameOrCode.equals("")){
			criteria=criteria.add(Restrictions.or(Restrictions.like("roleName",roleNameOrCode), 
					Restrictions.like("roleCode",roleNameOrCode)));
		}
	    if(roleIds.length()>0){
	    	criteria=criteria.add(Restrictions.not(Restrictions.in("id", roleIds.split(","))));
    	}
	    // 添加根据登录查询所在区域角色信息判断   yehb 2016-06-17
	    if(!"".equals(regionCode)){
	    	criteria=criteria.add(Restrictions.eq("regionCode", regionCode));
	    }
	    criteria=criteria.add(Restrictions.eq("invalidFlag", "1"));
	    criteria=criteria.add(Restrictions.or(Restrictions.isNull("expireDate"),
	    		Restrictions.ge("expireDate",string2Time(new DateTime().toString()))));
	    criteria=criteria.add(Restrictions.not(Restrictions.in("roleCode", new String[]{"administrator","admin","secadmin","secauditor"})));
	    if ((this.sort != null) && (!("".equals(this.sort)))) {
    		boolean isAsc = this.dir.equalsIgnoreCase("asc");
    		if (isAsc)
    			this.criteria.addOrder(Order.asc(this.sort));
    		else
    			this.criteria.addOrder(Order.desc(this.sort));
    	}else {
    		this.criteria.addOrder(Order.desc("id"));
    	}
	    page=getEntityManager().pagedQuery(criteria, pageNo, limit);
	    page.setPageSize(limit);
	    JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), 
	    		new String[]{ "hibernateLazyInitializer","handler","invalidFlag","operator"
	    	,"operateDate","visitUrl","roleResource","system","ipaddr","ipport","isControl","isOpen"
	    	,"menuItemInfo","rolePerson","roleDept","roleMenu"}, getDatePattern());
	}
	@Override
	protected EntityManager<AuthorityRole, String> getEntityManager() {
		return authorityRoleManager;
	}
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAppSystem() {
		return appSystem;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null&&!"".equals(id)) {
			entity = authorityRoleManager.get(id);
		} else {
			entity = new AuthorityRole();
		}
	}

	public AuthorityRole getModel() {
		return entity;
	}

	/**
	 * @return the entity
	 */
	public AuthorityRole getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(AuthorityRole entity) {
		this.entity = entity;
	}

	public String getEnabledLog() {
		return enabledLog;
	}

	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}

	public String getOperaDesc() {
		return operaDesc;
	}

	public void setOperaDesc(String operaDesc) {
		this.operaDesc = operaDesc;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
}
