package com.sinosoft.xf.petition.supervision.manager;


import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
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
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.petition.supervision.dao.TimeLimitDefineDao;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.util.sql.SqlUtil;
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TimeLimitDefineManager extends EntityManager<TimeLimitDefine, String>{
	
	/**
	 * 时限dao
	 */
	@Autowired
	TimeLimitDefineDao timeLimitDefineDao;
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
	@Transactional(readOnly=true)
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
	@Transactional(readOnly=true)
	public ExtjsPage<TimeLimitDefine> pagedQueryTimeLimitInfo(int start,int limit,String filterTxt,String filterValue,String sort,String dir){
		int pageNo = (start / limit) + 1;
		String regioncode = Struts2Utils.getSession().getAttribute("curRegionCode").toString();
		Criteria criteria = timeLimitDefineDao.createCriteria();
		criteria.add(Restrictions.eq("regionCode", regioncode));
		if (filterTxt != null && filterValue != null && !"".equals(filterTxt) && !"".equals(filterValue)) {
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			for (int i = 0; i < filterTxts.length; i++) {
				if("isValid".equals(filterTxts[i])&&"0".equals(filterValues[i]))
					criteria.add(Restrictions.or(Restrictions.ne(filterTxts[i], "1"), Restrictions.isNull(filterTxts[i])));
				else
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
	@Transactional(readOnly=true)
	public String getToRegionCodeList(){
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		List<OrganizationRelationInfo> ori = organizationRelationInfoManger.getChildOrgRelaInfoList(person.getRegionCode());
		StringBuffer json=new StringBuffer();
		json.append("[");
		for(int i=0;i<ori.size();i++){
			if("1".equals(ori.get(i).getOrganizationInfo().getPurpose())){
				json.append("{");
				json.append("\"orgCode\":\""+ ori.get(i).getOrganizationInfo().getOrgCode()+"\",");
				json.append("\"text\":\""+ ori.get(i).getOrganizationInfo().getOrgCname()+"\",");
				json.append("\"leaf\":\""+ Constants.TRUE+"\",");
				json.append("\"expanded\":\""+ "false"+"\",");
				json.append("\"iconCls\":\""+ "'org'"+"\"");
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
	 * AccusedName:被反映人,RRegionCode、RRegionCodeName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionCodeName:转发机构,AccuserName:反映人
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	@Transactional(readOnly=true)
	public String doRegionChart(PersonInfo person,Map map) throws ParseException {
		String sql = getAuthoritySql(person,true);
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
	 * AccusedName:被反映人,RRegionCode、RRegionCodeName:所属区域,objectClassName,行政级别
	 * ToRegionCode、ToRegionCodeName:转发机构,AccuserName:反映人
	 * @return  XML 统计图
	 * @throws ParseException
	 */
	@Transactional(readOnly=true)
	public String doReplyChart(PersonInfo person,Map map) throws Exception {
		String sql = getAuthoritySql(person,true);
		map.put("CurrRegion", person.getRegionCode());
		return timeLimitDefineDao.doReplyChart(sql,map);
	}

	/**
	 * 查询纵向接收查询列表: 返回纵向接收“未接收操作时限对比分析”柱形图需要的XML数据集
	 * @author mengxy
	 * @createDate 2011-06-07
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
	 * @return XML
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public String doReceiveChart(Map map) throws ParseException {
		return timeLimitDefineDao.doReceiveChart(map);
	}
	
	/**
	 * 查询纵向接收查询列表: 返回纵向接收“已接收未接收对比分析”柱形图需要的XML数据集
	 * @author ljx
	 * @createDate 2011-07-11
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
	 * @return XML
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public String doYWReceiveChart(PersonInfo person,Map map) throws ParseException {
		String ZFTypeCode = map.get("ZFTypeCode") == null ?"":map.get("ZFTypeCode").toString();
		String sql = "";
//		if("1".equals(ZFTypeCode)){
//			sql = " and petition_trans_deal_info_temp.creator_name='" +person.getUserCname()+"'";
//		}else if("2".equals(ZFTypeCode)){
//			sql = " and petition_assign_info_temp.creator_name='" +person.getUserCname()+"'";
//		}else{
//			sql = " and (petition_trans_deal_info_temp.creator_name='" +person.getUserCname()+
//					"' or petition_assign_info_temp.creator_name='" +person.getUserCname()+"')";
//		}
//		//一般用户添加权限，其他不添加
//		String roleStr = (String) Struts2Utils.getSessionAttribute("roleStr");
//		if(!RoleStr.BXS_GENERAL.toString().equals(roleStr)&&!RoleStr.XFS_GENERAL.toString().equals(roleStr)){
//			sql ="";
//		}
		return timeLimitDefineDao.doYWReceiveChart(sql,map);
	}
	
	/**
	 * 查询纵向接收查询列表: 返回纵向接收“已接收后续处理对比分析”柱形图需要的XML数据集
	 * @author ljx
	 * @createDate 2011-07-11
	 * @param map  sendDate：传输起始日期，sendDate1：传输终止日期
				   XFPrtNo:条形码编号,ZFTypeCode:转发类型 1、转办 2、交办
	 * @return XML
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public String doYReceiveChart(PersonInfo person,Map map) throws ParseException {
		String sql = getAuthoritySql(person,true);
		return timeLimitDefineDao.doYReceiveChart(sql,map);
	}

	/**
	 * 按regionCode查询区域代码表记录数
	 * @author ljx
	 * @return String
	 */
	@Transactional(readOnly=true)
	public String  getRegionSize(String regionCode) {
		long  regionSize = timeLimitDefineDao.getRegionSize(regionCode);
		if(regionSize > 0) {  
			return "{right:true}";
		}else{
			return "{right:false}";
		}
	}

	/**
	 * 纵向转发“已接收信访件下级后续处理时限对比”多柱图需要的数据集的方法
	 * @author mengxy
	 * @date 2011-06-20
	 * @param map
	 * IsCheck：0：本级查询、1：下级查询(必选),AchieveTypeCode:1：未归档、2：以归档(必选)
	 * InputDate：信访起始日，InputDate1：信访终止日，ZFTypeCode:转发类型
	 * @throws ParseException 
	 */
	@Transactional(readOnly=true)
	public String verTransTraceChart(PersonInfo person,Map map) throws ParseException {
		String sql = getAuthoritySql(person,true);
		return timeLimitDefineDao.verTransTraceChart(sql,map);
	}
	
	/**
	 * 根据用户得到权限串
	 * @param person
	 * @param tableSql
	 * @return
	 */
	private String getAuthoritySql(PersonInfo person,boolean flag){
		Map<String,String> tableMap = new HashMap<String,String>();
		if(flag){
		tableMap.put("petition_basic_info", "petition_basic_info");
		tableMap.put("PETITION_CIRCULATION_STATE_INFO", "PETITION_CIRCULATION_STATE_INFO");
	}
		//return SqlUtil.addAuth(person, tableMap, true);
		return "";
	}
}
