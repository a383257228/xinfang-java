package com.sinosoft.xf.system.transset.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganizationInfoManager;
import com.sinosoft.xf.system.transset.dao.PetitionOrganTransInfoDao;
import com.sinosoft.xf.system.transset.domain.PetitionOrganTransInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 转发机构设置信息manager
 * @date 2011-11-09
 * @author sunzhe 
 */
@Service
@Transactional
public class PetitionOrganTransInfoManager extends EntityManager<PetitionOrganTransInfo,String> {
	/**
	 * 注入转发机构设置信息dao
	 */
	@Autowired
	PetitionOrganTransInfoDao petitionOrganTransInfoDao;
	
	/**
	 * 注入转发机构设置信息dao
	 */
	@Autowired
	OrganizationInfoManager organizationInfoManager;
	
	/**
	 * 注入机构关系dao层
	 */
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	/**
	 * 重写父类方法
	 * @return 转发机构设置信息dao
	 */
	@Override
	protected HibernateDao<PetitionOrganTransInfo,String> getEntityDao() {
		// TODO Auto-generated method stub
		return petitionOrganTransInfoDao;
	}
	
	/**
	 * 是否验证条形码
	 * false 不验证，是不交换的机构
	 * true 验证
	 */
	@Transactional(readOnly=true)
	public boolean isJudge(String orgCode){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		int size = petitionOrganTransInfoDao.getOrganTransInfoByOrgCodeAndOrgType("noexchange"
				, orgCode, person.getRegionCode());
		if(size>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据机构id得到转发信息，如果机构未曾设置过转发信息，则只返回机构信息
	 * @param orgId 机构id
	 * @return 转发信息的json形式
	 */
	@Transactional(readOnly=true)
	public String getTransOrganInfo(String orgId,String id){
		//先根据机构id查找转发信息
		PetitionOrganTransInfo poti = petitionOrganTransInfoDao.findUniqueBy("id", id);
		StringBuffer jsonBuffer = new StringBuffer("");
		//如果机构未曾设置转发信息，则只显示机构信息
		if(poti == null) {
			OrganizationInfo organInfo = organizationInfoManager.get(orgId);
			jsonBuffer.append("{");
			jsonBuffer.append("id:'',");
			//机构id
			jsonBuffer.append("organId:'").append(organInfo.getId()).append("',");
			//机构编码
			jsonBuffer.append("orgCode:'").append(organInfo.getOrgCode()).append("',");
			//机构中文名称
			jsonBuffer.append("orgName:'").append(organInfo.getOrgCname()).append("',");
			jsonBuffer.append("ipAddress:'',");
			jsonBuffer.append("port:'',");
			jsonBuffer.append("orgType:'',");
			jsonBuffer.append("version:''");
			jsonBuffer.append("}");
		} else {
			jsonBuffer.append("{");
			//转发设置id
			jsonBuffer.append("id:'").append(poti.getId()).append("',");
			//机构id
			jsonBuffer.append("organId:'").append(poti.getOrganId()).append("',");
			//机构编码
			jsonBuffer.append("orgCode:'").append(poti.getOrgCode()).append("',");
			//机构名称
			jsonBuffer.append("orgName:'").append(poti.getOrgName()).append("',");
			//机构IP地址
			jsonBuffer.append("ipAddress:'").append(poti.getIpAddress()).append("',");
			//端口号
			jsonBuffer.append("port:'").append(poti.getPort()).append("',");
			//机构转发类型
			jsonBuffer.append("orgType:'").append(poti.getOrgType()).append("',");
			//接收机构系统版本
			jsonBuffer.append("version:'").append(poti.getVersion()).append("',");
			//是否有效 2015-10-14 liub 需求:转发设置中对转发机构添加可以设置失效的功能，让转发机构在录信时不显示，但不影响查询信件。
			jsonBuffer.append("isTrans:'").append(poti.getIsTrans()).append("'");
			jsonBuffer.append("}");
		}
		return jsonBuffer.toString();
	}
	
	/**
	 * 转发设置机构与部门信息树
	 * 显示上级机构与本级部门与下级机构信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public String getTransOrganTree(String node){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		StringBuffer treeJson = new StringBuffer("[");
		if("-1".equals(node)){
			List<OrganizationRelationInfo> list = new ArrayList<OrganizationRelationInfo>() ;
			String parentOrgCode = person.getRegionCode().substring(0,6);
			//得到本级的上级机构信息
			OrganizationRelationInfo relaInfo = organizationRelationInfoDao.findByUnique("organizationInfo.id", parentOrgCode);
//			String parentId = relaInfo.getOrganizationRelationInfo().getId();
			list.add(relaInfo.getOrganizationRelationInfo());//父机构对象
			//根据本级，查询下级机构与部门信息
			list.addAll( organizationRelationInfoDao.find(
					"FROM OrganizationRelationInfo WHERE organizationRelationInfo ='"+parentOrgCode+"' order by organOrder"));
			boolean firstItem = true;
			OrganizationRelationInfo sci;
			OrganizationInfo organ;
			//先拼机构，后拼部门
			for(int i=0;i<list.size();i++){
				sci =  list.get(i);
				organ = sci.getOrganizationInfo();
				//只显示有效的机构信息
				if(organ.getInvalidFlag().equals("1")&&organ.getPurpose().equals("1")){
					if(!firstItem){
						treeJson.append(",");
					}
					treeJson.append("{");
					treeJson.append("id:'" + organ.getId()+ "'");
					treeJson.append(",organizationInfoId:'" + organ.getId()+ "'");
					treeJson.append(",code:'" + organ.getOrgCode()+ "'");
					treeJson.append(",text:'" + organ.getOrgShortCname()+ "'");
					treeJson.append(",leaf:false" );
					//该节点身份为机构
					treeJson.append("}");
					if(firstItem) {
						firstItem = false;
					}
				}
			}
		}else{
			List<PetitionOrganTransInfo> list = petitionOrganTransInfoDao.find(Restrictions.eq("organId", node)
					,Restrictions.eq("validFlag", "0"));
			boolean firstItem = true;
			for (int i = 0; i < list.size(); i++) {
				PetitionOrganTransInfo temp = list.get(i);
				if(!firstItem){
					treeJson.append(",");
				}
				treeJson.append("{");
				treeJson.append("id:'" + temp.getId()+ "'");
				treeJson.append(",organizationInfoId:'" + temp.getOrganId()+ "'");
				treeJson.append(",code:'" + temp.getOrgCode()+ "'");
				treeJson.append(",text:'" + temp.getOrgName()+ "'");
				treeJson.append(",leaf:true" );
				treeJson.append(",iconCls:'chart_organisation'" );
				treeJson.append("}");
				if(firstItem) {
					firstItem = false;
				}
			}
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	@Transactional(readOnly=true)
	public List<PetitionOrganTransInfo> getTransRegionCombo(String regionCode,String filterValue,Map<String,Object> map) {
		return petitionOrganTransInfoDao.getTransRegionCombo(regionCode,filterValue,map);
	}
	
	/**
	 * 获取转发机构
	 * @param orgType
	 * @param orgCode
	 * @param regionCode
	 * @return
	 */
	@Transactional(readOnly=true)
	public PetitionOrganTransInfo getOrganTrans(String orgCode,String regionCode,String orgType){
		return petitionOrganTransInfoDao.getOrganTrans(orgCode,regionCode,orgType);
	}
	/**
	 * 生成转发机构机构编码
	 * @param organId 机构父节点id
	 * @param parentNodeCode 机构父节点orgCode
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getNextOrgCode(String organId, String parentNodeCode){
		String curRegionCode = (String) Struts2Utils.getSessionAttribute("curRegionCode");
		boolean isUpperRegion = false; //是否为上级
		if(parentNodeCode.compareTo(curRegionCode.substring(0,6))<0){
			isUpperRegion = true;
		}
		Map<String,Object> map = petitionOrganTransInfoDao.getMaxOrgCode(organId,curRegionCode,isUpperRegion);
		String maxStr = (String) map.get("max");
		//如果是上级
		if(isUpperRegion){
			//如果上级还没有转发机构，转发机构代码就使用上级org_code
			if(maxStr==null){
				
			}else{//如果上级有转发机构，转发机构代码就置为空，前台将添加按钮隐藏
				parentNodeCode = "";
			}
			return parentNodeCode;
		}else{//如果不是上级
			//如果没有转发机构
			if(maxStr==null){
				//如果没有转发机构，就将组织机构orgCode置为转发机构orgCode  by lijun  2015-08-12
				return parentNodeCode;
//				maxStr = "0";
			}
		}
		//将要改变的字符串转换为char类型再转换为int类型
		int max = maxStr.charAt(0);
		//如果max<65，即为A或A以前的字符，就将其设为A，否则就为当前字符+1
		if(max<65){
			max = 65;
		}else{
			max = max + 1;
		}
		//生成的转发机构代码为，所属组织机构代码第10位字母从A算起，追加1的代码字符串
		String orgCode = parentNodeCode.substring(0, 9) + String.valueOf((char)max).concat("00");
		List<PetitionOrganTransInfo> list = petitionOrganTransInfoDao.findBy("orgCode", orgCode);
		if(!list.isEmpty()){
			orgCode = parentNodeCode.substring(0, 9) + String.valueOf((char)(max+1)).concat("00");
		}
		return orgCode;
	}
	public static void main(String[] args) {
		int max = "A".charAt(0);
		if(max<65){
			max = 65;
		}else{
			max = max + 1;
		}
		System.out.println(String.valueOf((char)max));
	}
	
	
}
