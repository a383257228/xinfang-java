package com.sinosoft.xf.system.transset.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.system.transset.dao.PetitionOrganTransInfoDao;
import com.sinosoft.xf.system.transset.domain.PetitionOrganTransInfo;
import com.sinosoft.xf.system.transset.manager.PetitionOrganTransInfoManager;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.manager.exception.ServiceException;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 转发机构设置信息Action
 * @date 2011-11-09
 * @author sunzhe
 */
@Namespace("/system")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-organ-trans-info.action", type = "redirect") })
public class PetitionOrganTransInfoAction extends CrudExtActionSupport<PetitionOrganTransInfo,String> {
	private static final long serialVersionUID = 1L;
	//实例化转发机构设置信息对象
	private PetitionOrganTransInfo entity = new PetitionOrganTransInfo();
	/**
	 * 注入转发机构设置信息manager
	 */
	@Autowired
	PetitionOrganTransInfoManager petitionOrganTransInfoManager;
	/**
	 * 注入转发机构设置信息dao
	 */
	@Autowired
	PetitionOrganTransInfoDao petitionOrganTransInfoDao;
	/**
	 * 重写父类方法
	 * @return 转发机构设置信息manager
	 */
	@Override
	protected EntityManager<PetitionOrganTransInfo,String> getEntityManager() {
		return petitionOrganTransInfoManager;
	}
	
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}

	private String node;//组织机构id
	private String orgId;//组织机构id
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 重写父类方法
	 * @throws Exception
	 * @return 转发机构设置信息对象
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = petitionOrganTransInfoManager.get(id);
		} else {
			entity = new PetitionOrganTransInfo();
		}
	}
	/**
	 * 是否验证条形码
	 * @throws IOException 
	 */
	public void isJudge() throws IOException{
		Struts2Utils.getResponse().getWriter().print(
				petitionOrganTransInfoManager.isJudge(entity.getOrgCode()));
	}
	/**
	 * 获取转发机构设置信息对象
	 * @return 转发机构设置信息对象
	 */
	public PetitionOrganTransInfo getModel() {
		return entity;
	}

	/**
	 * 根据机构id得到转发设置信息，如果机构未曾设置过转发信息，则只显示机构信息
	 * @throws IOException
	 */
	public void getTransOrganInfo() throws Exception{
		Struts2Utils.getResponse().getWriter().print(petitionOrganTransInfoManager.getTransOrganInfo(orgId,id));
	}
	
	public void save() throws IOException{
		if(id==null||id.trim().equals("")){
			PetitionOrganTransInfo temp = petitionOrganTransInfoManager.getOrganTrans(entity.getOrgCode().trim(), entity.getRegionCode().trim(),null);
			if(temp!=null){
				Struts2Utils.getResponse().getWriter().print("{success:false,data:'机构编码与【"+temp.getOrgName()+"】相同，请修改后添加！'}");
				return;
			}
			//判断是否存在导出交换,一个系统下只能有一个导出交换，因为导出交换的regionCode使用的是系统的orgCode。
			if("exportexchange".equals(entity.getOrgType())){
				temp = petitionOrganTransInfoManager.getOrganTrans(null, entity.getRegionCode().trim(),entity.getOrgType().trim());
				if(temp!=null){
					Struts2Utils.getResponse().getWriter().print("{success:false,data:'每个体统下面只能有一个导出交换，请先修改【"+temp.getOrgName()+"】后添加！'}");
					return;
				}
			}
		}
		try {
			petitionOrganTransInfoManager.save(entity);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Utils.getResponse().getWriter().print("{success:true,data:'保存成功'}");
	}
	/**
	 * 转发设置加载树形组件，显示中文简称，不带复选框，显示所有有效节点
	 * @date 2013-03-18
	 * @author sunzhe
	 * @throws IOException 
	 */
	public void getTransOrganTree() throws IOException{
		String json = petitionOrganTransInfoManager
			.getTransOrganTree(node);
		Struts2Utils.getResponse().getWriter().print(json);
	}
	public void getTransRegionCombo() throws IOException{
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String,Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		String regionCode = (String) Struts2Utils.getSessionAttribute("curRegionCode");
		StringBuffer json = new StringBuffer("[");
		String sqlType=(String) map.get("sqlType");
		if(sqlType==null||"".equals(sqlType)){
			List<PetitionOrganTransInfo> list = petitionOrganTransInfoManager.getTransRegionCombo(regionCode,filterValue,map);
			if(list.isEmpty()){
				Struts2Utils.getResponse().getWriter().print(json.append("]").toString());
				return;
			}
			for (PetitionOrganTransInfo petitionOrganTransInfo : list) {
				json.append("{id:'");
				json.append(petitionOrganTransInfo.getId());
				json.append("',organId:'"); //用与转发地区验证
				json.append(petitionOrganTransInfo.getOrganId());
				json.append("',leaf:true");
				json.append(",code:'");
				json.append(petitionOrganTransInfo.getOrgCode());
				json.append("',orgCode:'"); 
				json.append(petitionOrganTransInfo.getOrgCode());
				json.append("',text:'");
				json.append(petitionOrganTransInfo.getOrgName());
				json.append("',name:'");
				json.append(petitionOrganTransInfo.getOrgName());
				json.append("'},");
			}
		}else{
			List<Map<String, Object>> potMap=petitionOrganTransInfoDao.getTransRegionComboByOrganTransInfos(regionCode,filterValue,map);
			if(potMap.size()<=0){
				Struts2Utils.getResponse().getWriter().print(json.append("]").toString());
				return;
			}
			for (Map map2 : potMap) {
				json.append("{id:'");
				json.append(map2.get("oid"));
				json.append("',organId:'"); //用与转发地区验证
				json.append(map2.get("Organ_Id"));
				json.append("',leaf:true");
				json.append(",code:'");
				json.append(map2.get("Org_Code"));
				json.append("',orgCode:'"); 
				json.append(map2.get("Org_Code"));
				json.append("',text:'");
				json.append(map2.get("Org_Name"));
				json.append("',name:'");
				json.append(map2.get("Org_Name"));
				json.append("'},");
			}
		}
		json.deleteCharAt(json.length()-1);
		json.append("]");
		Struts2Utils.getResponse().getWriter().print(json.toString());
	}
	
	private String organId;
	private String parentNodeCode;
	/**
	 * 生成转发机构机构编码
	 * @author lijun
	 * @throws Exception
	 */
	public void getNextOrgCodeAndOrder() throws Exception{
		try {
			Struts2Utils.getResponse().getWriter().print(petitionOrganTransInfoManager.getNextOrgCode(organId,parentNodeCode)) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getParentNodeCode() {
		return parentNodeCode;
	}

	public void setParentNodeCode(String parentNodeCode) {
		this.parentNodeCode = parentNodeCode;
	}
	
}
