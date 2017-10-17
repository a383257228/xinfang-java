package com.sinosoft.xf.system.logon.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganizationInfoManager;
import com.sinosoft.organization.manager.OrganizationRelationInfoManager;
import com.sinosoft.xf.system.logon.dao.PetitionOrganInfoDao;
import com.sinosoft.xf.system.logon.manager.PetitionOrganInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 纪委中特定化的组织机构方法
 * @author oba
 */
@Namespace("/petition")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-organ-info.action", type = "redirect")})
public class PetitionOrganInfoAction extends CrudExtActionSupport<OrganizationInfo, String>  {
	private static final long serialVersionUID = 1L;
	/**
	 * 注入组织基本信息manager
	 */
	@Autowired
	OrganizationInfoManager organizationInfoManager;
	
	/**
	 * 注入组织关联信息manager
	 */
	@Autowired
	OrganizationRelationInfoManager organizationRelationInfoManager;
	
	/**
	 * 注入纪委组织信息manager
	 */
	@Autowired
	PetitionOrganInfoManager petitionOrganInfoManager;
	
	/**
	 * 注入用户信息manager
	 */
	@Autowired
	PersonInfoDao personInfoDao;
	
	/**
	 * 组织基本信息对象
	 */
	OrganizationInfo entity = new OrganizationInfo();
	
	@Override
	protected EntityManager<OrganizationInfo, String> getEntityManager() {
		return organizationInfoManager;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id)) {
			entity = organizationInfoManager.get(id);
		} else {
			entity = new OrganizationInfo();
		}
	}

	public OrganizationInfo getModel() {
		return entity;
	}
	private String node;//当前树节点ＩＤ
	
    private String orgCode;//区域编码
	
	private String maxNoInfoId;//区域编码id
	
	private String organizationRelationOid;//组织关系父id
	
	private String organOrder;//组织显示顺序
	
	private String relation;//组织隶属关系
	
	private String flag;
	
	private String purpose;
	
	private String invalidFlag="";
	
	private String petitionTypeCode;
	
	private boolean singleSelect;
	
	private String parentOrgId;//上级机构id

	@Autowired
	PetitionOrganInfoDao petitionOrganInfoDao;
	/**
	 * 日志查询的用户下拉框的查看
	 * @author guoh
	 * @date 2017-07-24
	 * @throws IOException
	 */
	public void queryLogOnUsers() throws IOException{
	    StringBuffer usersInfo = new StringBuffer();
	    String currDeptCode = "310000000000";
	    List<Map<String,Object>> usersList = this.petitionOrganInfoDao.queryLogOnUsers(currDeptCode);
	    usersInfo.append("[");
	    if (!usersList.isEmpty()) {
	    	boolean followNode = false;
	    	for (Map<String,Object> userObect : usersList) {
	    	  if (followNode)
	        	usersInfo.append(",");
		        usersInfo.append("{");
		        usersInfo.append("\"userId\":\"" + userObect.get("userId") + "\"");
		        usersInfo.append(",\"userName\":\"" + userObect.get("deptName") + "\"");
		        usersInfo.append("}");
		        followNode = true;
	        }
	    }
	    usersInfo.append("]");
	    Struts2Utils.getResponse().getWriter().print(usersInfo.toString());
	  }
	/**日志查询的统计机构下拉框的查看
	 * @author guoh
	 * @date 2017-07-25
	 * @throws IOException
	 */
	public void queryDeptInfoAfterGroup() throws IOException{
	    StringBuffer deptInfo = new StringBuffer();
	    List<Map<String,Object>> deptList = this.petitionOrganInfoDao.getGroupDeptInfos();
	    deptInfo.append("[");
	    if (!deptList.isEmpty()) {
		      boolean followNode = false;
	    	  for (Map<String,Object> deptObect : deptList) {
		          if (followNode)
			          deptInfo.append(",");
				      deptInfo.append("{");
				      deptInfo.append("\"deptId\":\"" + deptObect.get("deptId") + "\"");
				      deptInfo.append(",\"deptName\":\"" + deptObect.get("deptName") + "\"");
				      deptInfo.append("}");
				      followNode = true;
		      }
	    }
	    deptInfo.append("]");
	    Struts2Utils.getResponse().getWriter().print(deptInfo.toString());
    }
	/**
	 * 分级加载树形组件，显示登录用户所在机构的下级有效机构，中文简称，带复选框
	 * @date 2012-09-17
	 * @author oba
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameCheckTreeByInvalidFlagAndOrgan() throws IOException{
		String json = petitionOrganInfoManager
			.getOrganTree(node, "false", "orgCname", "organ","1",false);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 加载下级自收区域
	 * @date 2015-05-26
	 * @author liub
	 * @return
	 */
	public void getOrgCnameLowerSelfAcceptRegionCode() throws IOException{
		String json = petitionOrganInfoManager
		.getOrgCnameLowerSelfAcceptRegionCode(node);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示所有有效节点
	 * 用于前台页面中反映人的所属区域和单位或地址的下拉框
	 * @date 2013-07-09
	 * @author xuyi
	 * 调用哪些方法：
	 * 1、petitionOrganInfoManager.getOrganTree()
	 * 		分级加载树形组件
	 * 被哪些页面调用：
	 * 1、Petition.PetitionAccept.CommonAccuserSet.js
	 * 		第一反映人信息录入表单
	 * 2、Petition.PetitionAccept.LetterAcceptanceOtherAccuserWin.js
	 * 		多个反应人信息录入表单（非第一反映人信息）
	 * @return json树
	 * @throws IOException 
	 */
	public void getOrgShortCnameTreeForAccuserIssueAndWorkUnit() throws IOException{ 
		String json = petitionOrganInfoManager.getOrganTree(node,"1","accuser");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 分级加载树形组件，显示登录用户所在机构的下级有效机构，中文简称，带复选框
	 * @date 2012-09-17
	 * @author oba
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgCnameTreeByInvalidFlagAndOrgan() throws IOException{
		String json = petitionOrganInfoManager
			.getOrganTree(node, "", "orgCname", "organ","1",false);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 分部加载列表树，不带复选框，全部节点
	 * 用途： 
	 * 	1、渲染组织设置列表树
	 * 调用哪些方法：
	 * 1、petitionOrganInfoManager.getOrganColumnsTree
	 * @return 空
	 * @throws IOException
	 * @throws Exception
	 * 2012-12-7 hxh 添加purpose参数 查询部门或机构
	 */
	public void getOrganColumnsTree() throws IOException, Exception {
		String treejson = petitionOrganInfoManager.getOrganColumnsTree(node,purpose,invalidFlag,filterTxt,filterValue,false,false);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 分部加载列表树:中文名，全部有效节点
	 * 用途：
	 * 	1、组织用户设置，列表树
	 * 更新1：
	 * 在原有基础上增加了查询功能
	 * 调用哪些方法:
	 * 1、petitionOrganInfoManager.getOrganUserColumnsTree
	 * 被前台哪些模块调用:
	 * 1、用户设置
	 * @return 空
	 */
	public void getOrganUserColumnsTreeByOrgCnameAndInvalidFlag() throws IOException{
		String treejson = petitionOrganInfoManager.getOrganUserColumnsTree(
				node,"orgCname","1",filterValue);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 用于综合查询中问题属地的查询，只返回省一级的机构
	 * @author xuyi
	 * @date 2013-10-31
	 * 调用哪些方法：
	 * 1、petitionOrganInfoManager.getOrganizationTreeOnlyProv()
	 * 		只查询省一级的组织机构
	 * 被那些js引用：
	 * 1、Petition.Query.HighIssue.js
	 * @throws IOException
	 */
	public void getOrgCnameTreeByInvalidFlagAndOrganOnlyProv() throws IOException{
		String json = petitionOrganInfoManager.getOrganizationTreeOnlyProv(singleSelect);
		Struts2Utils.getResponse().getWriter().print(json);
	}

	/**
	 * 获得需要集中式部署的机构树信息
	 * @date 2012-03-15
	 * @author oba
	 * @throws IOException 
	 */
	public void getCentralizedDeploymentInfoTree() throws IOException{
		String json = petitionOrganInfoManager.getCentralizedDeploymentInfoTree();
		Struts2Utils.getResponse().getWriter().print(json);
	}
	//用于下拉框再次打开时勾选上已选选项
	private String checkedOption;
	/**
	 * 用于combo查询的分级加载树
	 * @date 2011-12-02
	 * @author Oba
	 * @throws IOException
	 */
	public void getOrganTreeByQuery() {
		//获得操作人员
		try{
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		String json = petitionOrganInfoManager.getOrganTree(getNode(), "1", filterValue,person.getRegionCode(),singleSelect,checkedOption);
		Struts2Utils.getResponse().getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询用户所分配的权限
	 * 调用哪些方法：
	 * 1、petitionOrganInfoManager.getOrganTreeByRegionAuthority
	 * 被前台哪些模块调用
	 * 1、Petition.PetitionAccept.CommonIssueSet
	 * 2、Petition.PetitionAccept.LetterAcceptanceMethod
	 * @date 2013-04-13
	 * @author hxh
	 * @throws IOException
	 */
	public void getOrganTreeByRegionAuthority() {
		//获得操作人员
		try{
			PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
			String json = petitionOrganInfoManager.getOrganTreeByRegionAuthority(node
					, "1",filterValue,person.getRegionCode(),getPetitionTypeCode());
			Struts2Utils.getResponse().getWriter().print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @author lijun 2014-6-24
	 * 用于问题属地查询拼音检索下拉框的方法
	 * @throws IOException
	 */
	public void getPinyinOrganTreeByRegionAuthority() throws IOException {
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		String json = petitionOrganInfoManager.getPinyinOrganTreeByRegionAuthority("1",person.getRegionCode(),getPetitionTypeCode());
		Struts2Utils.getResponse().getWriter().print(json);
	}
	public void getPinyinOrganTreeSpeeding() throws IOException {
		String json = petitionOrganInfoManager.getPinyinOrganTreeSpeeding();
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 获得转办本委的部门信息（获得除操作人员所在部门外的所有同级部门信息）
	 * @date 2012-02-18
	 * @author oba
	 * @throws IOException 
	 */
	public void getSelfTranserDeptInfo() throws IOException{
		String json = petitionOrganInfoManager.getSelfTranserDeptInfo();
		Struts2Utils.getResponse().getWriter().print(json);
	}
	/**
	 * 获得转办部门信息（获得除操作人员所在机构的下一级机构）
	 * @date 2012-02-18
	 * @author oba
	 * @throws IOException 
	 */
	public void getTranserDeptInfo() throws IOException{
		//获得操作人员
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		person = personInfoDao.get(person.getId());
		//获得操作人员所在部门  目前只考虑用户只在一个部门下
		OrganizationInfo org = person.getOrganPersonRelationInfo()
				.iterator().next().getOrganizationInfo();
		//获得获得上级部门关系信息 目前只考虑只有一个上级的情况
		OrganizationRelationInfo organRelaInfo = org.getOrganizationRelationInfo()
				.iterator().next().getOrganizationRelationInfo();
		while ("2".equals(organRelaInfo.getOrganizationInfo().getPurpose())) {
			organRelaInfo = organRelaInfo.getOrganizationRelationInfo();
		}
		organRelaInfo = organizationRelationInfoManager.get(organRelaInfo.getId());
		//获得上级部门的所有下级信息
		Set<OrganizationRelationInfo> set = organRelaInfo.getChildrens();
		Iterator<OrganizationRelationInfo> it = set.iterator();
		StringBuffer bf = new StringBuffer("[");
		boolean flag = false;
		//遍历下级部门
		while (it.hasNext()) {
			OrganizationRelationInfo cOrgRelaInfo = it.next();
			OrganizationInfo cOrg = cOrgRelaInfo.getOrganizationInfo();
			if("2".equals(cOrg.getPurpose())){
				continue;
			}else if("1".equals(cOrg.getPurpose())&&!org.getId().equals(cOrg.getId())){
				if(flag){
					bf.append(",");
				}
				bf.append("{");
				bf.append("id:'");
				bf.append(cOrg.getId());
				bf.append("'");
				bf.append(",text:'");
				bf.append(cOrg.getOrgCname());
				bf.append("'");
				bf.append(",code:'");
				bf.append(cOrg.getOrgCode());
				bf.append("'");
				bf.append(",orgEname:'");
				bf.append(cOrg.getOrgEname());
				bf.append("',leaf:true}");
				flag = true;
			}
		}
		bf.append("]");
		Struts2Utils.getResponse().getWriter().print(bf.toString());
	}
	
	/**
	 * 通过当前用户组织编码获得下级组织编码和组织中文名
	 * @date 2012-05-16
	 * @author Liuchang
	 * @throws  IOException
	 */
	public void getChildrensOrganization() throws IOException{
		//获得当前用户
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		person = personInfoDao.get(person.getId());
		
		//获得当前用户组织编码
		String regionCode = person.getRegionCode();
		
		//获得下级组织编码和组织中文名
		StringBuffer bf = petitionOrganInfoManager.getChildrensOrganization(regionCode);
		Struts2Utils.getResponse().getWriter().print(bf.toString());
}

	public void getOrgAndRelaInfoByOrgCode() throws IOException{
		Struts2Utils.getResponse().getWriter().print(petitionOrganInfoManager.getOrgAndRelaInfoByOrgCode
				(node, purpose, filterTxt, filterValue));
	}
	
	/**
	 * 查询本级和下级区域或部门
	 * @author hxh
	 * 调用哪些方法:
	 * 1、petitionOrganInfoManager.getCurrentOrgAndNextLevel
	 * 被前台哪些模块调用:
	 * 1、Authority.QueryAuthorityDeptTree 
	 * 2、AuthorityAudit.QueryAuthorityDeptTree 权限查看显示部门
	 * 3、Organ.Dept.DeptColumnTree
	 * 4、Organization.OrganColumnTree
	 * 5、Organ.User.EditOrganPersonWindow
	 * @throws Exception
	 */
	public void getCurrentOrgAndNextLevel() throws Exception{
		Struts2Utils.getResponse().getWriter().print(petitionOrganInfoManager.getCurrentOrgAndNextLevel
				(node, purpose,invalidFlag, filterTxt, filterValue));
	}
	/**
	 * 查询部门权限下拉框
	 * @author lijun
	 * 调用哪些方法:
	 * 1、petitionOrganInfoManager.getOrgPowerCombox
	 * 被前台哪些模块调用:
	 * 1、Organ.User.AddOrganPersonWindow 显示部门权限下拉框
	 * 2、Organ.User.EditOrganPersonWindow 显示部门权限下拉框
	 * @param purpose 1：区域 2：部门
	 * @param parpentOrgId 上级部门id
	 * @param invalidFlag  是否有效
	 * @throws Exception
	 */
	public void getOrgPowerCombox() throws Exception{
		String treejson = petitionOrganInfoManager.getOrgPowerCombox(purpose,parentOrgId,invalidFlag);
		Struts2Utils.getResponse().getWriter().print(treejson);
	}
	/**
	 * 生成区域编码和排序号
	 * @author hxh
	 * 调用哪些方法：
	 * 1、petitionOrganInfoManager.getMaxOrgCode
	 * 被前台哪些模块调用：
	 * 1、Organization.DropTree.js 区域
	 * @throws Exception
	 */
	public void getMaxOrgCodeAndOrder() throws Exception{
		Struts2Utils.getResponse().getWriter().print(petitionOrganInfoManager.getMaxOrgCodeAndOrder(orgCode)) ;
	}
	
	/**
	 * 生成部门编码
	 * @author hxh
	 * 调用哪些方法:
	 * 1、petitionOrganInfoManager.getMaxDeptNoAndOrder
	 * 被前台哪些模块调用:
	 * 1、Organ.Dept.DeptColumnTree 部门操作js文件
	 * 2、Organ.Dept.AddDeptWindow
	 * @throws Exception
	 */
	public void getMaxDeptNoAndOrder() throws Exception{
		Struts2Utils.getResponse().getWriter().print(petitionOrganInfoManager.getMaxDeptNoAndOrder(orgCode)) ;
	}
	
	/**
	 * 在保存机构前先保存区域编码信息
	 * 调用哪些方法：
	 * 1、petitionOrganInfoManager.saveOrganizationInfo
	 * 被前台哪些模块调用：
	 * 1、Organ.Dept.AddDeptWindow 新增部门
	 * 2、Organ.Dept.EditDeptWindow 修改部门
	 * 3、Organization.AddDeptWindow 新增区域
	 * 4、Organization.EditOrganizationWindow 修改区域
	 * @param flag  保存类型，insert为新增保存，save为修改保存
	 * @param maxNoInfoId  自动生成区域编码和排序号的id
	 * @param entity  组织对象
	 * @param organizationRelationOid  组织oid，在新增保存时为上级组织关系oid，修改保存时为本级组织关系oid
	 * @param organOrder  组织显示顺序
	 * @param relation  组织隶属关系
	 */
	public void saveOrganizationInfo(){
		petitionOrganInfoManager.saveOrganizationInfo(this.flag,this.maxNoInfoId
				, entity, this.organizationRelationOid, this.organOrder, this.relation);
	}
	
	/**
	 * 分级加载树形组件，显示中文简称，不带复选框，显示所有有效节点
	 * @date 2010-12-07
	 * @author wangxx
	 * @return 空
	 * @throws IOException 
	 */
	public void getOrgShortCnameTreeByInvalidFlag() throws IOException{
		String json = petitionOrganInfoManager
			.getOrganTree(node, "", "orgShortCname", "all","1");
		Struts2Utils.getResponse().getWriter().print(json);
	}
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMaxNoInfoId() {
		return maxNoInfoId;
	}

	public void setMaxNoInfoId(String maxNoInfoId) {
		this.maxNoInfoId = maxNoInfoId;
	}

	public String getOrganizationRelationOid() {
		return organizationRelationOid;
	}

	public void setOrganizationRelationOid(String organizationRelationOid) {
		this.organizationRelationOid = organizationRelationOid;
	}

	public String getOrganOrder() {
		return organOrder;
	}

	public void setOrganOrder(String organOrder) {
		this.organOrder = organOrder;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getInvalidFlag() {
		return invalidFlag;
	}

	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}
	

	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}

	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getCheckedOption() {
		return checkedOption;
	}

	public void setCheckedOption(String checkedOption) {
		this.checkedOption = checkedOption;
	}
	
}
