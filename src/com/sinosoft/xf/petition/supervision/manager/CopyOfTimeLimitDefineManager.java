package com.sinosoft.xf.petition.supervision.manager;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.manager.AuthorityDataManager;
import com.sinosoft.organization.domain.OrganizationRelationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.OrganizationRelationInfoManager;
import com.sinosoft.xf.petition.supervision.dao.CopyOfTimeLimitDefineDao;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**   
 * 时限manager。
 * @author ljx   
 * @createDate 2011-04-27  
 */
@Service
@Transactional
public class CopyOfTimeLimitDefineManager extends EntityManager<TimeLimitDefine, String>{
	
	/**
	 * 时限dao
	 */
	@Autowired
	CopyOfTimeLimitDefineDao timeLimitDefineDao;

	/**
	 * 注入数据权限manager
	 */
	@Autowired
	AuthorityDataManager authorityDataManager;
	
	@Autowired
	OrganizationRelationInfoManager  organizationRelationInfoManger;
	
	/**
	 * 重写父类dao
	 * @return 时限dao
	 */
	protected HibernateDao<TimeLimitDefine, String> getEntityDao() {
		return timeLimitDefineDao;
	}
	
	/**
	 * 按有效标识查询 
	 * @author ljx
	 * @createDate 2011-05-12
	 */
	public List<TimeLimitDefine> getTimeLimitDefine (){
		Criteria criteria = timeLimitDefineDao.createCriteria();
		criteria.add(Restrictions.eq("isValid", "1"));
		List<TimeLimitDefine> list = criteria.list();
		return list;
	}
	
	/**
	 * 查询时限信息
	 * @author ljx
	 * @createDate 2011-07-31
	 */
	public ExtjsPage<TimeLimitDefine> pagedQueryTimeLimitInfo(int start,int limit,String filterTxt,String filterValue,String sort,String dir){
		int pageNo = (start / limit) + 1;
		String regioncode = timeLimitDefineDao.getRegionCode();
		Criteria criteria = timeLimitDefineDao.createCriteria();
		criteria.add(Restrictions.eq("regionCode", regioncode));
		if (filterTxt != null && filterValue != null && !"".equals(filterTxt) && !"".equals(filterValue)) {
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			for (int i = 0; i < filterTxts.length; i++) {
				criteria.add(Restrictions.eq(filterTxts[i], filterValues[i]));
			}
		}
		if ((sort != null) && (!("".equals(sort)))) {
			boolean isAsc = dir.equalsIgnoreCase("asc");
			if (isAsc)
				criteria.addOrder(Order.asc(sort));
			else {
				criteria.addOrder(Order.desc(sort));
			}
		}
		return timeLimitDefineDao.pagedQuery(criteria, pageNo, limit);
	}
	
	
	/**
	 * 查询转发区域信息
	 * 
	 * @author ljx
	 * @throws Exception
	 * @throws IOException
	 * @createDate 2012-05-02
	 */
	public String getToRegionCodeList(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		List<OrganizationRelationInfo> ori = organizationRelationInfoManger.getChildOrgRelaInfoList(person.getRegionCode());
		StringBuffer json=new StringBuffer();
		json.append("[");
		for(int i=0;i<ori.size();i++){
			if("1".equals(ori.get(i).getOrganizationInfo().getPurpose())){
				json.append("{");
				json.append("\"code\":\""+ ori.get(i).getOrganizationInfo().getOrgCode()+"\",");
				json.append("\"name\":\""+ ori.get(i).getOrganizationInfo().getOrgCname()+"\"");
				json.append("},");
			}
		}
		json.deleteCharAt(json.length()-1);
		json.append("]");
		return json.toString().replace("\"null", "\"");
	}
	
	/**
	 * 拼装纵向转发“不同下级转发信访量对比”柱形图需要的数据集的方法
	 * @author mengxy
	 * @date 2011-05-25
	 * @param PersonInfo person
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionCodeName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionCodeName:转发机构,AccuserName:反映人
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String doRegionChart(PersonInfo person,Map map) throws ParseException {
		String [] sqls = {"petition_basic_info","petition_accuser_info","petition_accused_info","petition_issue_info","petition_circulation_state_info","petition_deal_info","petition_trans_deal_info","petition_assign_info"};
		String sql = authorityDataManager.getMultiAuthoritySqlByTableWithTableName(person,sqls);
		map.put("CurrRegion", person.getRegionCode());
		return timeLimitDefineDao.doRegionChart(sql,map);
	}

	/**
	 * 拼装纵向转发“不同下级首次回复时限对比”柱形图需要的数据集的方法
	 * @author mengxy
	 * @date 2011-05-27
	 * @param PersonInfo person
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * IsSend 已传输 0,1 ， IsSend1 未传输 0,1，IsReceive 下级已接收 0,1， IsReceive1 下级未接收 0,1
	 * InputDate：信访起始日，InputDate1：信访终止日，XFNo1:信访编号,XFPrtNo:条形码编号,ZFTypeCode:转发类型,
	 * AccusedName:被反映人,RRegionCode、RRegionCodeName:所属区域,AccusedClasses,行政级别
	 * ToRegionCode、ToRegionCodeName:转发机构,AccuserName:反映人
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String doReplyChart(PersonInfo person,Map map) throws ParseException {
		String [] sqls = {"petition_basic_info","petition_accuser_info","petition_accused_info","petition_issue_info","petition_circulation_state_info","petition_deal_info","petition_trans_deal_info","petition_assign_info"};
		String sql = authorityDataManager.getMultiAuthoritySqlByTableWithTableName(person,sqls);
		map.put("CurrRegion", person.getRegionCode());
		return timeLimitDefineDao.doReplyChart(sql,map);
	}
	
	
	/**
	 * 横向处理“不同问题类别信访量对比”柱形图需要的数据集的方法
	 * @author ljx
	 * @date 2011-06-28
	 * @param map
	 * XFNo1：信访编号,AchieveTypeCode:1：未归档、2：以归档(必选)
	 * AccusedName:被反映人 ， AccuserName: 反映人，InputDate: 信访起始日， InputDate1: 信访终止日
	 * XFClassCode：信访类型，ToRegionCode：所属区域，ActivityCode:处理环节,SourceClasses:信访来源,AccusedClasses:行政级别,
	 * @param type 类型：UnAchievedState、Achieved
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	public String horizWarningWTChart(PersonInfo person,Map map) throws ParseException {
		String [] sqls = {"petition_basic_info","petition_accuser_info","petition_accused_info","petition_issue_info","petition_circulation_state_info"};
		String sql = authorityDataManager.getMultiAuthoritySqlByTableWithTableName(person,sqls);
		return timeLimitDefineDao.horizWarningWTChart(sql,map);
	}
	
	/**
	 * 按regionCode查询区域代码表记录数
	 * @author ljx
	 * @return String
	 */
	public String  getRegionSize(String regionCode) {
		long  regionSize = timeLimitDefineDao.getRegionSize(regionCode);
		if(regionSize > 0) {  
			return "{right:true}";
		}else{
			return "{right:false}";
		}
	}
}
