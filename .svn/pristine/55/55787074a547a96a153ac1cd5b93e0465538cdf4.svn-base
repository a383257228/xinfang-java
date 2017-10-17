package com.sinosoft.organization.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.log.common.AttType;
import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.domain.OperationDataLog;
import com.sinosoft.log.domain.OperationLog;
import com.sinosoft.log.manager.OperationLogManager;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.manager.OrganizationRelationInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@Namespace("/organization") 
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location="organization-relation-info.action", type = "redirect")})
public class OrganizationRelationInfoAction extends CrudExtActionSupport<OrganizationRelationInfo, String>{

	private static final long serialVersionUID = 1L;
	
	//注入机构关系业务层
	@Autowired
	OrganizationRelationInfoManager organizationRelationInfoManager;
	/**
	 * 操作日志service用于手动记录操作日志
	 */
	@Autowired
	OperationLogManager operationLogManager;
	OrganizationRelationInfo entity = new OrganizationRelationInfo();
	
	//关系表中的机构oid
	private String organizationInfoOid;
	
	//关系表中的父机构oid
	private String organizationRelationInfoOid;
	
	private String node;//当前树节点ＩＤ
	
	private String pOid;//父机构ＩＤ
	
	private String oid;//机构ＩＤ
	
	private String organRelaOid;//机构关系ＩＤ
	
	private String groupOid;//分组ＩＤ
	
	private String orgOid;//机构ＩＤ
	
	private String orgCname;//机构中文名
	
	private String loadAll = "true";//是否需要加载全部树节点 true需要　false不需要
	
	private boolean expandFlag;
	//按钮名称
	@AttLog(attType=AttType.BUTTON)
	private String btnName;
	//操作描述
	@AttLog(attType=AttType.DESCRIPTION)
	private String desp;
	//操作功能代码
	@AttLog(attType=AttType.FUNCTION_CODE)
	private String functionCode;
	//操作功能名称
	@AttLog(attType=AttType.FUNCTION_NAME)
	private String functionName;
	//操作登记代码
	@AttLog(attType=AttType.LEVEL_CODE)
	private String levelCode;
	//操作登记名称
	@AttLog(attType=AttType.LEVEL_NAME)
	private String levelName;
	//操作类型代码
	@AttLog(attType=AttType.TYPE_CODE)
	private String typeCode;
	//操作类型名称
	@AttLog(attType=AttType.TYPE_NAME)
	private String typeName;
	//操作结果代码
	private String operationResultCode;
	//操作结果名称
	private String operationResultName;
	
	/**
	 * 是否记录日志，当enabledLog为false时，不记录日志信息
	 */
	@AttLog(attType=AttType.LOG_ENABLE)
	private String enabledLog;
	
	/**
	 * 用于combo查询的分级加载树
	 * @date 2011-12-02
	 * @author Oba
	 * @throws IOException
	 */
	public void getOrganTreeByQuery() throws IOException{
		String json = organizationRelationInfoManager.getOrganTree(node, "1", filterValue);
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 重写loadDate方法
	 * @return 空
	 */
	public void loadData() throws Exception{
		try {
			OrganizationRelationInfo model = getEntityManager().get(this.id);
		    if (model != null){
			      JsonUtils.write(model, Struts2Utils.getResponse().getWriter(),getExcludes(), getDatePattern());
		    }
		} catch (Exception e) {
		}finally{
			List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
			dataLogs.add(new OperationDataLog("组织关系唯一标示",this.id));
		}
	}
	/**
	 * 在复制组织信息时判断复制的节点是否在新增下级组织节点的上级
	 * @date 2011-06-02
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void judgeParentOrgan() throws IOException{
		String flag = organizationRelationInfoManager
			.judgeParentOrgan(organizationInfoOid, organizationRelationInfoOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * 加载普通列表数据，如果有下级就显示下级组织，如果没有下级就显示本机信息
	 * @author wangxx
	 * @date 2011-04-11
	 * 用途：
	 * 	1、该接口主要用于目录项目
	 * 步骤：通过orgRelaOid 判断是否有下级，如果有就显示下级信息
	 * 		如果没有就显示本机信息
	 * @return 空
	 * @throws IOException 
	 */
	public void organRelaPagedQuery() throws IOException{
		String json = organizationRelationInfoManager.organRelaPagedQuery(
				filterTxt,filterValue,start,limit);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 根据页面传入的参数，不显示该参数对应的节点信息及其下级节点信息，其他节点也只显示有效节点信息
	 * 该方法显示的组织机构中文名
	 * 用途：
	 *  1、机构拆分，上级组织
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganCnameOrganTreeNeedNotNodeIdByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeNeedNotNodeId(
				node, "", "orgCname", "organ", "1", organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 根据页面传入的参数，不显示该参数对应的节点信息及其下级节点信息，其他节点也只显示有效节点信息
	 * 该方法显示的组织机构中文简称
	 * 用途：
	 *  1、机构拆分，上级组织
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganShortCnameOrganTreeNeedNotNodeIdByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeNeedNotNodeId(
				node, "", "orgShortCname", "organ", "1", organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 根据页面传入的参数，不显示该参数对应的节点信息及其下级节点信息，其他节点也只显示有效节点信息
	 * 该方法显示的全部组织中文简称
	 * 用途：
	 *  1、机构拆分，上级组织
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganCnameTreeNeedNotNodeIdByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeNeedNotNodeId(
				node, "", "orgCname", "all", "1", organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 根据页面传入的参数，不显示该参数对应的节点信息及其下级节点信息，其他节点也只显示有效节点信息
	 * 该方法显示的全部组织中文简称
	 * 用途：
	 *  1、机构拆分，上级组织
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganShortCnameTreeNeedNotNodeIdByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeNeedNotNodeId(
				node, "", "orgShortCname", "all", "1", organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 根据页面传入的参数显示其下级组织  中文名 全部节点
	 * @return 空
	 * @throws IOException
	 */
	public void getOrganCnameTreeByNodeId() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeByNodeId(
				node, "false", "orgCname", "all", "", organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 根据页面传入的参数显示其下级组织  中文简称  全部节点
	 * @return 空
	 * @throws IOException
	 */
	public void getOrganShortCnameTreeByNodeId() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeByNodeId(node, "false", "orgShortCname", "all", "", organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 验证添加组织时判断上级组织下是否已经存在该中文名
	 * @return 空
	 * @throws IOException
	 */
	public void judgeOrganCNameRepeat() throws Exception {
		String orgCname = this.orgCname;
		String parentOrgRelaId = this.organizationRelationInfoOid;
		String orgEnameRepeat = organizationRelationInfoManager
			.judgeOrganCNameRepeat(orgCname, parentOrgRelaId);
		Struts2Utils.getResponse().getWriter().print(orgEnameRepeat);
	}
	/**
	 * 根据制定部门为根节点，分级渲染树
	 * @return 空
	 * @throws IOException
	 */
	public void buildUserDeptTreeByPersonOrgOid() throws IOException {
		String flag = organizationRelationInfoManager
			.buildUserDeptTreeByPersonOrgOid(node,orgOid,false);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	/**
	 * 组织分组修改时左边的树：显示中文简称，带复选框
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException
	 */
	public void buildGroupDeptTree() throws IOException {
		String treejson = organizationRelationInfoManager.buildGroupDeptTree(
				node, true, groupOid,"orgShortCname");
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 组织分组修改时左边的树：显示中文名，带复选框
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException
	 */
	public void buildGroupDeptTreeLongName() throws IOException {
		String treejson = organizationRelationInfoManager.buildGroupDeptTree(
				node, true, groupOid,"orgCname");
		Struts2Utils.getResponse().getWriter().print(treejson);
	}

	/**
	 * 分部加载列表树，不带复选框，全部节点
	 * @date 2010-12-07
	 * @author wangxx 
	 * 更新1：
	 * 在原有基础上添加了查询功能
	 * @date 2011-05-03
	 * @author wangxx
	 * 用途： 
	 * 	1、渲染组织设置右侧的列表树
	 * @return 空
	 * @throws IOException
	 * @throws Exception
	 */
	public void getOrganColumnsTree() throws IOException, Exception {
		String treejson = organizationRelationInfoManager.getOrganColumnsTree(node,"",filterValue,false,false);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 分部加载列表树，不带复选框，全部节点
	 * 在进行树节点拖拉时，由于前台树控件的问题，无法将子节点拖到子节点下
	 * 所以只能在后台做处理，就是让没有子节点的节点自动展开。
	 * @date 2010-01-10
	 * @author wangxx 
	 * 用途： 
	 * 	1、渲染组织变更的列表树
	 * @return 空
	 * @throws IOException
	 * @throws Exception
	 */
	public void getOrganColumnsTreeForChange() throws IOException, Exception {
		String treejson = organizationRelationInfoManager.getOrganColumnsTree(node,"",filterValue,true,expandFlag);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}

	/**
	 * 分部加载列表树，不带复选框，全部有效节点
	 * @date 2010-12-07
	 * @author wangxx
	 * 更新1：
	 * 在原有基础上添加了查询功能
	 * @date 2011-05-03
	 * @author wangxx
	 * 用途：
	 *  1、渲染组织设置右侧的列表树
	 * @return 空
	 * @throws IOException
	 * @throws Exception
	 */
	public void getOrganColumnsTreeByInvalidFlag() throws IOException  {
		String treejson = organizationRelationInfoManager
			.getOrganColumnsTree(node,"1",filterValue,false,false);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 分部加载列表树，不带复选框，全部有效节点
	 * 在进行树节点拖拉时，由于前台树控件的问题，无法将子节点拖到子节点下
	 * 所以只能在后台做处理，就是让没有子节点的节点自动展开。
	 * @date 2012-01-10
	 * @author oba
	 * 用途：
	 *  1、渲染组织变更的列表树
	 * @return 空
	 * @throws IOException
	 * @throws Exception
	 */
	public void getOrganColumnsTreeByInvalidFlagForChange() throws IOException  {
		String treejson = organizationRelationInfoManager
			.getOrganColumnsTree(node,"1",filterValue,true,expandFlag);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	
	/**
	 * 分级加载树形组件，显示中文名，不带复选框，显示所有节点
	 * @author wangxx
	 * @date 2010-12-06
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameTree() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgCname", "all","");
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 分级加载树形组件，显示中文名，不带复选框，显示所有有效节点
	 * @date 2010-12-06
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameTreeByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgCname", "all","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 分级加载树形组件，显示中文名，不带复选框，显示所有有效节点
	 * @date 2010-12-06
	 * @author wangxx
	 * 用途：
	 * 	1、用于目录系统：如果有本机组织oid则以本级组织为根节点进行加载树
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameTreeByInvalidFlagAndOrganOid() throws IOException{
		String orgOid = Struts2Utils.getParameter("orgOid");
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgCname", "all","1",orgOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 分级加载树形组件，显示中文名，带复选框，显示所有有效节点
	 * @date 2010-12-06
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameCheckTreeByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "false", "orgCname", "all","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示所有节点,通过组织编码进行排序
	 * @date 2010-12-07
	 * @author wangxx
	 * 用途：
	 * 	1、目录系统
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameTreeOrderOrgCode() throws IOException{
		String json = organizationRelationInfoManager.getOrganTreeOrderOrgCode(
				node, "", "orgShortCname", "all","","orgCode");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示所有节点
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameTree() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgShortCname", "all","");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示所有有效节点
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameTreeByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgShortCname", "all","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示所有有效节点 ，通过组织编码进行排序
	 * @date 2011-04-21
	 * @author wangxx
	 * 用途：
	 * 	1、目录系统
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameTreeByInvalidFlagOrderOrgCode() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTreeOrderOrgCode(node, "", "orgShortCname", "all","1","orgCode");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 分级加载树形组件，显示中文简称，带复选框，显示所有有效节点
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameCheckTreeByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "false", "orgShortCname", "all","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示根节点和机构节点
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameOrganTree() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgShortCname", "organ","");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 一次性加载树形组件，所有节点全部加载
	 * 显示中文简称，不带复选框，显示根节点和机构节点
	 * @date 2011-11-25
	 * @author sunzhe
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameOrganTreeAllNode() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTreeAllNode(node, "", "orgShortCname", "organ","",loadAll);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示根节点和有效机构节点
	 * @date 2010-12-10
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameOrganTreeByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgShortCname", "organ","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 分级加载树形组件，显示中文名，不带复选框，显示根节点和有效机构节点
	 * @date 2010-12-10
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameOrganTreeByInvalidFlag() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "", "orgCname", "organ","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}
		
	/**
	 * 分级加载树形组件，显示中文名，带复选框但不选中，显示全部节点
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameCheckTree() throws IOException{
		String json = organizationRelationInfoManager
			.getOrganTree(node, "false", "orgCname", "all","");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	/**
	 * 通过关系表的oid查询出对应部门的下级部门信息
	 * @return 空
	 * @throws IOException
	 * @throws Exception
	 */
	public void pagedQueryByOrganRelationOid() throws IOException, Exception{
		if(this.filterTxt==null||this.filterTxt.equals("")){
			this.filterTxt="organizationRelationInfo.id";
			this.filterValue="";
		}
		int pageNo = (start / limit) + 1;
		Criteria criteria = organizationRelationInfoManager.createCriteria();
		criteria = criteria.add(Restrictions.eq(this.filterTxt,this.filterValue));
		@SuppressWarnings("unchecked")
		ExtjsPage<OrganizationInfo> page = getEntityManager()
			.pagedQuery(criteria, pageNo, limit);
		JsonUtils.write(page, Struts2Utils.getResponse()
				.getWriter(),pagedQueryByOrganRelaOid(), getDatePattern());

	}
	
	/**
	 * 判断机构是否有重复子部门
	 * @date 2010-06-30
	 * @author wangxx 
	 * @return 空
	 * @throws IOException 
	 *
	 */
	public void judgeOrganRepeat() throws Exception{
		//获取页面参数
		//机构主键
		String organOid =this.organizationInfoOid;
		//机构父节点
		String parentOrgId = this.organizationRelationInfoOid;
		//判断机构是否有重复子部门
		String orgEnameRepeat = organizationRelationInfoManager.judgeOrganRepeat(organOid,parentOrgId);
		//返回机构是否有重复子部门是否重复到页面
		Struts2Utils.getResponse().getWriter().print(orgEnameRepeat);
	}
	
	/**
	 * 判断机构下是否存在该中文名的子部门  用于修改
	 * @date 2010-11-18
	 * @author wangxx 
	 * @return 空
	 * @throws IOException
	 *
	 */
	public void judgeOrganEditCNameRepeat() throws Exception{
		//获取页面参数
		//机构主键
		String orgCname =this.orgCname;
		//机构父节点
		String parentOrgId = this.organizationRelationInfoOid;
		String organOid=this.orgOid;
		//判断机构是否有重复子部门
		String orgEnameRepeat = organizationRelationInfoManager.judgeOrganEditCNameRepeat(orgCname,parentOrgId,organOid);
		//返回机构是否有重复子部门是否重复到页面
		Struts2Utils.getResponse().getWriter().print(orgEnameRepeat);
	}

	/**
	 * 封装渲染树所需的参数
	 * @date 2010-5-18
	 * @author wangxx
	 * @return String[]渲染树所需的参数
	 */
	@SuppressWarnings("unused")
	private String[] getExcludesForAll() {
		return new String[] { "hibernateLazyInitializer", "class", "root",
				"parent", "users" ,"childrens","children","organizationRelationInfo"};
	}

	/**
	 * pagedQuery需要的参数，用于屏蔽那些属性不需要显示的
	 * @date 2010-5-18
	 * @author wangxx
	 * @return String[]渲染树所需的参数
	 */
	public String[] getExcludes() {
		return new String[] { "hibernateLazyInitializer", "class", "root",
				"parent", "users" ,"childrens","children","organizationRelationInfo","organMergeSplitInfo","organPersonRelationInfo"};
	}

	/**
	 * loadData需要的参数，用于屏蔽那些属性不需要显示的
	 * @date 2010-5-18
	 * @author wangxx
	 * @return String[]渲染树所需的参数
	 */
	public String[] getExcludesLoad() {
		return new String[] { "hibernateLazyInitializer", "class", "root",
				"parent", "users" ,"childrens","children","organizationRelationInfo","organMergeSplitInfo","organPersonRelationInfo"};
	}

	/**
	 * pagedQueryByOrganRelationOid需要的参数，用于屏蔽那些属性不需要显示的
	 * @date 2010-5-18
	 * @author wangxx
	 * @return String[]渲染树所需的参数
	 */
	private String[] pagedQueryByOrganRelaOid(){
		return new String[] {"hibernateLazyInitializer","handler","organizationRelationInfo","organPersonRelationInfo",
				"organMergeSplitInfo","children","childrens"};
	}
	/**
	 * 通过多个机构关系表oid拼接他们的中文名
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganCnames() throws IOException{
		String json = organizationRelationInfoManager.getOrganCnames(this.organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 通过机构关系表oid拼接他的中文名
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrganCname() throws IOException{
		String json = organizationRelationInfoManager.getOrganCname(this.organRelaOid);
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 组织调度
	 * @return 空
	 * @throws IOException 
	 */
	public void changeOrganization() throws IOException{
		String flag = organizationRelationInfoManager.changeDept(this.pOid ,this.oid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	
	/**
	 * 组织调度
	 * 用途：专用于平煤项目，他们没有多个上级，所以不需要过于复杂的操作
	 * @date 2011-07-25
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void changeOrganizationByPM() throws IOException{
		String flag = organizationRelationInfoManager.changeDeptByPM(this.pOid ,this.oid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}

	/**
	 * 判断该部门是否有下级部门
	 * @date June 30 2010
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void isDeleteDeptAll() throws IOException {
		String flag = organizationRelationInfoManager.judgeChildOrgRelaInfoByOrgOid(this.organRelaOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	

	/**
	 * 判断该节点部门是否有下级部门
	 * @date June 30 2010
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void judgeChildOrgRelaInfoByOrgRelaOid() throws IOException {
		String flag = organizationRelationInfoManager.judgeChildOrgRelaInfoByOrgRelaOid(this.organRelaOid);
		Struts2Utils.getResponse().getWriter().print(flag);
	}
	
	/**
	 * 查询本机及下一级区域信息不带复选框
	 * @author hxh
	 * @throws IOException
	 */
	public void getOrgTree()throws IOException{
		String json = organizationRelationInfoManager.getOrgTree();
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 设置操作日志基础信息
	 * @param log 操作日志对象
	 */
	private void setOperaLogInfo(OperationLog log) {
		log.setOperationFuncCode(getFunctionCode());
		log.setOperationFuncName(getFunctionName());
		log.setOperationBtnName(getBtnName());
		log.setOperationDesc(getDesp());
		log.setOperationLevelCode(getLevelCode());
		log.setOperationLevelName(getLevelName());
		log.setOperationTypeCode(getTypeCode());
		log.setOperationTypeName(getTypeName());
	}
	
	/**
	 * @param oid the pOid to set
	 */
	public void setPOid(String oid) {
		pOid = oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	/**
	 * @param orgCname the orgCname to set
	 */
	public void setOrgCname(String orgCname) {
		this.orgCname = orgCname;
	}

	/**
	 * @param orgOid the orgOid to set
	 */
	public void setOrgOid(String orgOid) {
		this.orgOid = orgOid;
	}

	/**
	 * @param organRelaOid the organRelaOid to set
	 */
	public void setOrganRelaOid(String organRelaOid) {
		this.organRelaOid = organRelaOid;
	}

	/**
	 * @return the node
	 */
	public String getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}

	/**
	 * @param organizationInfoOid the organizationInfoOid to set
	 */
	public void setOrganizationInfoOid(String organizationInfoOid) {
		this.organizationInfoOid = organizationInfoOid;
	}

	/**
	 * @param organizationRelationInfoOid the organizationRelationInfoOid to set
	 */
	public void setOrganizationRelationInfoOid(String organizationRelationInfoOid) {
		this.organizationRelationInfoOid = organizationRelationInfoOid;
	}

	/**
	 * @param groupOid the groupOid to set
	 */
	public void setGroupOid(String groupOid) {
		this.groupOid = groupOid;
	}

	public String getLoadAll() {
		return loadAll;
	}

	public void setLoadAll(String loadAll) {
		this.loadAll = loadAll;
	}

	public void setExpandFlag(boolean expandFlag) {
		this.expandFlag = expandFlag;
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

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOperationResultCode() {
		return operationResultCode;
	}

	public void setOperationResultCode(String operationResultCode) {
		this.operationResultCode = operationResultCode;
	}

	public String getOperationResultName() {
		return operationResultName;
	}

	public void setOperationResultName(String operationResultName) {
		this.operationResultName = operationResultName;
	}

	@Override
	protected EntityManager<OrganizationRelationInfo, String> getEntityManager() {
		return organizationRelationInfoManager;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id.trim())) {
			entity = organizationRelationInfoManager.get(id);
		} else {
			entity = new OrganizationRelationInfo();
		}
	}

	public OrganizationRelationInfo getModel() {
		return entity;
	}

	public String getEnabledLog() {
		return enabledLog;
	}

	public void setEnabledLog(String enabledLog) {
		this.enabledLog = enabledLog;
	}
	
}
