package com.sinosoft.xf.petition.petitionaccept.dao;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.authority.manager.AuthorityDataManager;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.RoleStr;
import com.sinosoft.xf.petition.accept.dao.PetitionBasicInfoDao;
import com.sinosoft.xf.petition.petitionaccept.domain.PetitionRepeatInfo;
import com.sinosoft.xf.util.CommonUtil;
import com.sinosoft.xf.util.TimeUtils;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.util.sql.SqlUtil;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 重复件判定dao
 * @author ljx 
 * @createDate 2011-10-12
 */
@Repository
public class RepeatJudgeDao extends HibernateDao<PetitionRepeatInfo, String> {
	//注入人员角色关系业务层
	@Autowired
	AuthorityDataManager authorityDataManager;
	@Autowired
	PetitionBasicInfoDao basicInfoDao;
		
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	/**
	 * 修改主信重复次数
	 * @author ljx
	 * @createDate 2011-10-20
	 */
	public void editMainLetterRepeatNum(int repeatNum,String xfno,boolean flag) throws Exception{
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		String addOrSubtract = "-"+(repeatNum+1);
		if(flag)
			addOrSubtract = "+"+(repeatNum+1);
		String sql = "update petition_basic_info set repeat_num = repeat_num"+addOrSubtract+"  where region_code='"+currRegionCode+"' and petition_no ='"+xfno+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 修改重复信重复次数
	 * @author ljx
	 * @createDate 2011-10-20
	 */
	public void editRepeatInfoRepeatNum(int repeatNum,String oid,boolean flag) throws Exception{
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		String addOrSubtract = "-" + (repeatNum + 1);//是加1还是减1
		if (flag)
			addOrSubtract = "+" + (repeatNum + 1);
		String sql = "update petition_repeat_Info set repeat_num = repeat_num"+addOrSubtract+"  where oid ='"+oid+"' and region_code='"+currRegionCode+"'";
		jdbcTemplate.update(sql);
	}

	/**
	 * 修改当前信件的重复件标志为1，1代表是重复件，0代表不是重复件
	 * i:repeat_Flag为1代表是重复件
	 * @author ljx
	 * @createDate 2011-10-20
	 */
	public void editCurrentLetterRepeatFlag(int repeatNum,String xfno,int i) throws Exception{
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		String sql = "update petition_basic_info set repeat_Flag = '"+i+"' ,repeat_num = "+repeatNum+" where region_code='"+currRegionCode+"' and petition_no ='"+xfno+"'";
		jdbcTemplate.update(sql);
	}
	/**
	 * 修改重复件信息的mainxfno
	 * @author ljx
	 * @createDate 2012-03-09
	 */
	public void editRepeatInfoByMainNo(String xfno,String mainxfno,String mainCurrPetitionNo) throws Exception{
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		String sql = "update petition_repeat_info set main_petition_no = '"+mainxfno+"',main_Curr_petition_no = '"+mainCurrPetitionNo+"'  where region_code='"+currRegionCode+"' and main_petition_no='"+xfno+"'";
		jdbcTemplate.update(sql);
	}
	/**
	 * 修改重复次数
	 * @author liub
	 * @createDate 2015-05-18
	 */
	public void editRepeatNum(int repeatNum,String xfno) throws Exception{
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		String sql = "update petition_basic_info set repeat_num = "+repeatNum+"  where region_code='"+currRegionCode+"' and petition_no ='"+xfno+"'";
		jdbcTemplate.update(sql);
	}
	/**
	 * 修改repeatinfo重复次数
	 * @author liub
	 * @createDate 2015-05-18
	 */
	public void editRepeatInfoNum(int repeatNum,String xfno) throws Exception{
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		String sql = "update petition_repeat_info set repeat_num = "+repeatNum+"  where region_code='"+currRegionCode+"' and petition_no ='"+xfno+"'";
		jdbcTemplate.update(sql);
	}
	/**
	 * 得到所有Mainxfno信的集合
	 * @author liub
	 * @createDate 2015-05-18
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionRepeatInfo> getRepeatInfoTreeListByMainxfno(String xfno) throws Exception{
		Session session = this.getSession();
		Criteria criteriaMain = session.createCriteria(PetitionRepeatInfo.class);
		String currRegionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		criteriaMain.add(Restrictions.eq("mainPetitionNo",xfno));
		criteriaMain.add(Restrictions.eq("regionCode",currRegionCode));
		//2015-10-21 liub这个必须添加repeatFlag为1的数据,因为当用户选择一信多投或者并处的时候也会保存mainPetitionNo,但是这样的数据不是重复件,不能算重复件的次数
		criteriaMain.add(Restrictions.eq("repeatFlag","1"));
		return criteriaMain.list(); 
	}
	/**
	 * 修改重复件信息下级的mainxfno
	 * @author ljx
	 * @createDate 2012-03-09
	 */
	public void editRepeatInfoMainNo(String oids,String xfno,String currPetitionNo) throws Exception{
		String sql = "update petition_repeat_info set main_petition_no = '"+xfno+"' ,main_curr_petition_no = '"+currPetitionNo+"' where oid in( "+ oids+ ")";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 删除重复件信息
	 * @author ljx
	 * @createDate 2012-03-09
	 */
	public void deleteRepeatInfo(String xfno,String regionCode) throws Exception{
		String sql = "delete from petition_repeat_info where repeat_Flag='1' and petition_no ='"+xfno+"' and region_code='"+regionCode+"'";
		jdbcTemplate.update(sql);
	}
	/**
	 * 查询当前判重重复件信息
	 * @author ljx
	 * @createDate 2011-10-20
	 */
	public List<PetitionRepeatInfo> findRepeatJudgingInfo(String petitionNo)  throws Exception{
		String regionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		Criteria criteriaMain = this.createCriteria();
		criteriaMain.add(Restrictions.eq("petitionNo",petitionNo));
		criteriaMain.add(Restrictions.eq("regionCode",regionCode));
		criteriaMain.add(Restrictions.eq("repeatFlag","1"));
		return criteriaMain.list();
	}
	/**
	 * 得到主重复信访编号
	 * @author ljx
	 * @createDate 2011-10-20
	 */
	public String getMainxfno(String xfno) throws Exception{
		String mainxfno = xfno;
		List<PetitionRepeatInfo> list = findRepeatJudgingInfo(xfno);
		if (list != null && !list.isEmpty()) {
			mainxfno = list.get(0).getMainPetitionNo();
		}
		return mainxfno.trim();
	}
	
//	/**
//	 * 得到主重复显示信访编号
//	 * @createDate 2013-01-14
//	 */
//	public String getMainCurrXfno(String currPetitionNo) throws Exception{
//		String mainCurrXfno = currPetitionNo;
//		List<PetitionRepeatInfo> list = findRepeatJudgingInfo(currPetitionNo);
//		if (list != null && !list.isEmpty()) {
//			mainCurrXfno = list.get(0).getMainCurrPetitionNo();
//		}
//		return mainCurrXfno.trim();
//	}
	
	/**
	 * 查询补判重复件信息
	 * @author ljx
	 * @createDate 2011-10-12
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findAddedRepeatInfo(String filterTxt, String filterValue,int start,int limit,String sort,String dir) throws Exception{
		PersonInfo personInfo =  (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		StringBuffer sql = new	StringBuffer("");
		sql.append("select * from ( select rownumber() over() as rownumber_, row_.* from ( ");
		sql.append("select  petition_basic_info.oid,petition_basic_info.petition_prt_no,petition_basic_info.petition_no,petition_basic_info.curr_petition_no,petition_basic_info.petition_date,petition_basic_info.petition_title,petition_basic_info.repeat_flag" +
				",petition_basic_info.deal_type_code,(case when petition_basic_info.repeat_flag = '1'  then  petition_repeat_info.repeat_num  else  petition_basic_info.repeat_num end )  repeat_num" +
				",petition_basic_info.petition_type_code,petition_basic_info.petition_source_code,petition_basic_info.petition_class_code "+
				",petition_basic_info.petition_type_name,petition_basic_info.petition_source_name,petition_basic_info.petition_class_name ") ;
		sql.append(",petition_accuser_info.accuser_name,petition_accuser_info.accuser_Work_Unit " );
		sql.append(",petition_accused_info.accused_name,petition_accused_info.accused_work_unit" );
		sql.append(",issue_type_info.issue_type_code,issue_type_info.issue_type_name " );
		sql.append(",petition_circulation_state_info.activity_no " );
		//sql.append(appendFindAddedRepeatInfo( filterTxt, filterValue,personInfo));
		//拼装排序字段
		if(sort!=null && !"".equals(sort)){
			String[] sorts = sort.split(",");
			String[] dirs = dir.split(",");
			for(int i=0;i<sorts.length;i++){
				if(0==i){
					 sql.append(" order by ");
				}else{
					sql.append(" , ");
				}
				sql.append("petition_basic_info."+sorts[i]);
				if(dirs[i]!=null && !"".equals(dirs[i])){
					sql.append(" ");
					sql.append(dirs[i]);
				}
			}
		}else{
			sql.append(" order by petition_basic_info.petition_no desc");
		}
		sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 查询补判重复件信息总记录数
	 * @author ljx
	 * @createDate 2011-10-12
	 */
	public long findAddedRepeatInfoCount(String filterTxt, String filterValue) throws Exception{
		PersonInfo personInfo =  (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		StringBuffer sql = new	StringBuffer("");
		sql.append("select count(0) ");
		//sql.append( appendFindAddedRepeatInfo(filterTxt, filterValue,personInfo));
		return jdbcTemplate.queryForLong(sql.toString());
	}
	
	/**
	 * 查询主重复件信息
	 * @author hxh
	 * 被哪些方法调用:
	 * 1、LeaderStatInfoAction.getRepeatByEndFlag
	 * @param filterTxt
	 * @param filterValue
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getRepeatByEndFlag(String filterTxt, String filterValue,int start,int limit) {
		PersonInfo personInfo=(PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		StringBuffer sql = new StringBuffer("select * from ( select rownumber() over() as rownumber_, row_.* from (");
		sql.append("select repeat.main_petition_no as mainCurrPetitionNo,state.activity_name as activityName");
		sql.append(",petitionba0_.repeat_num as repeatNum,petitionba0_.sub_issue_region_name as subIssueRegionName");
		sql.append(",petitionba0_.petition_date as firstPetitionDate");
		sql.append(",to_char(repeat.create_date,'yyyy-MM-dd') as lastPetitionDate");
		sql.append(" from (select repeat3.create_date, repeat4.main_petition_no,repeat4.petition_no");
		sql.append(" from petition_repeat_info repeat4,(");
		sql.append(" select repeat1.main_petition_no,repeat1.create_date from petition_repeat_info repeat1");
		sql.append(" where repeat1.create_date=(select max(repeat2.create_date) from petition_repeat_info repeat2");
		sql.append(" where repeat2.main_petition_no=repeat1.main_petition_no)");
		sql.append(") as repeat3 where repeat4.main_petition_no=repeat3.main_petition_no and repeat4.repeat_flag='1')");
		sql.append(" as repeat,petition_basic_info petitionba0_ ,petition_circulation_state_info state");
		sql.append(" where petitionba0_.region_code='"+personInfo.getRegionCode()+"'"); 
		sql.append(" and repeat.petition_no=petitionba0_.petition_no");
		sql.append(" and petitionba0_.petition_no=state.petition_no");
		if(filterTxt!=null && !filterTxt.equals("") && filterValue!=null && !filterValue.equals("")){
			String[] filterT=filterTxt.split(";");
			String[] filterV=filterValue.split(";");
			for(int i=0;i<filterT.length;i++){
				if("startDate".equals(filterT[i])){
					if(filterV[i].length()==4){
						sql.append(" and to_char(petitionba0_.petition_date,'yyyy')='"+filterV[i]+"'");
					}else if(filterV[i].length()==7){
						sql.append(" and to_char(petitionba0_.petition_date,'yyyy-MM')='"+filterV[i]+"'");
					}else{
						sql.append(" and petitionba0_.petition_date>='"+TimeUtils.string2Time(filterV[i])+"'");
					}
				}else if("endFlag".equals(filterT[i])){
					if(filterV[i].equals("0")){
						sql.append(" and (petitionba0_.end_flag='"+filterV[i]+"' or petitionba0_.end_flag is null)");
					}else{
						sql.append(" and petitionba0_.end_flag='"+filterV[i]+"'");
					}
				}else if("mainCurrPetitionNo".equals(filterT[i])){
					sql.append(" and repeat.main_petition_no='"+filterV[i]+"'");
				}
			}
		}
		sql.append(" order by petitionba0_.repeat_num desc");
		sql.append(" ) as row_ ) as temp_  where rownumber_ > "+start+" and rownumber_ <= "+(start+limit));
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 查询主重复件信息数据量
	 * @author hxh
	 * 被哪些方法调用:
	 *  1、LeaderStatInfoAction.getRepeatByEndFlag
	 * @param filterTxt
	 * @param filterValue
	 * @return
	 */
	public int getRepeatByEndFlagCount(String filterTxt,String filterValue){
		PersonInfo personInfo=(PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		StringBuffer sql = new StringBuffer("select count(*)");
		sql.append(" from (select repeat3.create_date, repeat4.main_petition_no,repeat4.petition_no from petition_repeat_info repeat4,(");
		sql.append(" select repeat1.main_petition_no,repeat1.create_date from petition_repeat_info repeat1");
		sql.append(" where repeat1.create_date=(select max(repeat2.create_date) from petition_repeat_info repeat2");
		sql.append(" where repeat2.main_petition_no=repeat1.main_petition_no)");
		sql.append(") as repeat3 where repeat4.main_petition_no=repeat3.main_petition_no and repeat4.repeat_flag='1')");
		sql.append(" as repeat,petition_basic_info petitionba0_");
		sql.append(" where petitionba0_.region_code='"+personInfo.getRegionCode()+"'"); 
		sql.append(" and repeat.petition_no=petitionba0_.petition_no");
		if(filterTxt!=null && !filterTxt.equals("") && filterValue!=null && !filterValue.equals("")){
			String[] filterT=filterTxt.split(";");
			String[] filterV=filterValue.split(";");
			for(int i=0;i<filterT.length;i++){
				if("startDate".equals(filterT[i])){
					if(filterV[i].length()==4){
						sql.append(" and to_char(petitionba0_.petition_date,'yyyy')='"+filterV[i]+"'");
					}else if(filterV[i].length()==7){
						sql.append(" and to_char(petitionba0_.petition_date,'yyyy-MM')='"+filterV[i]+"'");
					}else{
						sql.append(" and petitionba0_.petition_date>='"+TimeUtils.string2Time(filterV[i])+"'");
					}
				}else if("endFlag".equals(filterT[i])){
					if(filterV[i].equals("0")){
						sql.append(" and (petitionba0_.end_flag='"+filterV[i]+"' or petitionba0_.end_flag is null)");
					}else{
						sql.append(" and petitionba0_.end_flag='"+filterV[i]+"'");
					}
				}else if("mainCurrPetitionNo".equals(filterT[i])){
					sql.append(" and repeat.main_petition_no='"+filterV[i]+"'");
				}
			}
		}
		int size = jdbcTemplate.queryForInt(sql.toString());
		return size;
	}
	
	/**
	 * 查询当月是否与主信重复的信息 不包含当前信
	 * @param mainXfno 
	 * @param directPetitionDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionRepeatInfo> getRepetionDate(String mainXfno,
			Timestamp directPetitionDate,String petitionNo) {
		String regionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		Session session = this.getSession();
		Timestamp[] months   = getMonthLastAndFirstDate(directPetitionDate);
		Criteria criteriaMain = session.createCriteria(PetitionRepeatInfo.class);
		criteriaMain.add(Restrictions.eq("mainPetitionNo",mainXfno));
		criteriaMain.add(Restrictions.eq("regionCode",regionCode));
		criteriaMain.add(Restrictions.eq("repeatFlag","1"));
		criteriaMain.add(Restrictions.ne("petitionNo",petitionNo));
		criteriaMain.add(Restrictions.lt("directPetitionDate",months[1]));
		criteriaMain.add(Restrictions.gt("directPetitionDate",months[0]));
		criteriaMain.addOrder(Order.asc("createDate"));
		criteriaMain.setMaxResults(1);
		
		return criteriaMain.list(); 
	}
	
	/**
	 * 获取指定 月初时间 和月末时间
	 * 被哪些方法调用 
	 * 1、getRepetionDate（）查询当月是否与主信重复的信息 不包含当前信
	 * @param directPetitionDate	当前信件 信访日期
	 */
	public Timestamp[] getMonthLastAndFirstDate(Timestamp directPetitionDate) {
		Timestamp[] date = new Timestamp[2];
		DateTime dt = new DateTime(directPetitionDate);
		//获取月末时间
		DateTime lastday = dt.dayOfMonth().withMaximumValue();
		//获取月初时间
		DateTime firstday = dt.dayOfMonth().withMinimumValue();
		
		String lastdays = TimeUtils.date2Str(lastday.toDate(),null);
		
		String firstdays =  TimeUtils.date2Str(firstday.toDate(),null);
		
		
		date[0]=TimeUtils.string2Time(firstdays+" 00:00:00");
		date[1]=TimeUtils.string2Time(lastdays+ " 23:59:59");
		return date;
	}

	/**
	 * 更新基本表basic_flag字段 
	 * 被哪些方法调用  
	 * RepeatJudgeManager。editCurrentLetterBasicFlag（）   控制基本表basic_flag赋值
	 * @param xfno	信访件编号
	 * @param basicFlag	月基数标示
	 */
	public void editPetitionBasicFlag(String xfno,String basicFlag) {
		String regionCode =  (String)Struts2Utils.getSession().getAttribute("curRegionCode");
		/*PetitionBasicInfo basicInfo = basicInfoDao.getBasicInfoByPetitionNo(xfno, regionCode);
		basicInfo.setBasicFlag(basicFlag);
		basicInfoDao.save(basicInfo);*/
		String updateSql = "update petition_basic_info set basic_flag = '"+basicFlag+"' where petition_no='"+xfno+"' and region_code='"+regionCode+"'";
		jdbcTemplate.update(updateSql);
	}
	/**
	 * @Description: 查询当前信的所有的OID
	 * @Title: queryInfo
	 * @author: lixl  
	 * @date: 2014年7月31日 下午2:48:36 
	 * @param xfno
	 * @return
	 */
	public List<String> queryInfo(String xfno){
		
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		Criteria criteria = this.getSession().createCriteria(PetitionRepeatInfo.class);
		DetachedCriteria dic = DetachedCriteria.forClass(PetitionRepeatInfo.class);
		dic.add(Restrictions.eq("petitionNo", xfno));
		dic.add(Restrictions.eq("regionCode", person.getRegionCode()));
		dic.setProjection(Projections.property("mainPetitionNo"));//投影所有的主信编号
		
		criteria.add(Subqueries.propertyEq("mainPetitionNo", dic));
		List<PetitionRepeatInfo> list = criteria.list();
		
		Map<String,String> map = new HashMap<String, String>();//存储当前信 与 对应的OID 
		
		Map<String,List<String>> map1 = new HashMap<String, List<String>>();
		List<String> petionNoList = null;
		
		//设置map对象的值
		for(PetitionRepeatInfo repeatInfo : list){
			map.put(repeatInfo.getPetitionNo(), repeatInfo.getId());
			
			if(map1.containsKey(repeatInfo.getDirectPetitionNo())){
				map1.get(repeatInfo.getDirectPetitionNo()).add(repeatInfo.getPetitionNo());
			}else{
				petionNoList = new ArrayList<String>();
				petionNoList.add(repeatInfo.getPetitionNo());
				map1.put(repeatInfo.getDirectPetitionNo(), petionNoList);
			}
		}
		List<String> listPNos = new ArrayList<String>();
		List<String> oidList = new ArrayList<String>();
		
		//oidList.add(queryOIDByPetition(xfno));//设置当前信的OID
		
		listPNos = query(xfno, map1, listPNos);
		for(String str : listPNos){
			oidList.add(map.get(str));//设置子信的OID
		}
		return oidList;
	}
	
	/**
	 * @Description:递归查询与当前信有关的所有的子信
	 * @Title: query
	 * @author: lixl  
	 * @date: 2014年7月31日 下午2:45:40 
	 * @param directPetition
	 * @param map
	 * @param listPNos
	 * @return
	 */
	public List<String> query(String directPetition,Map<String,List<String>> map,
			List<String> listPNos){
		List<String> list = new ArrayList<String>();
		list = map.get(directPetition);
		if(list!=null){
			for(String str : list){
				listPNos.add(str);
				query(str,map,listPNos);
			}
		}
		return listPNos;
	}
	/**
	 * 得到所有directxfno信的向下集合
	 * @author liub
	 * @createDate 2015-05-18
	 */
	@SuppressWarnings("unchecked")
	public List<PetitionRepeatInfo> getRepeatInfoTreeListDown(String xfno) throws Exception{
		Session session = this.getSession();
		Criteria criteriaMain = session.createCriteria(PetitionRepeatInfo.class);
		criteriaMain.add(Restrictions.eq("directPetitionNo",xfno));
		String regionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		criteriaMain.add(Restrictions.eq("regionCode", regionCode));
		return criteriaMain.list(); 
	}
	/**
	 * 获取重复件
	 * @param xfno
	 * @param repeatFlag
	 * @param moreFlag
	 * @param sameDealFlag
	 * @return
	 */
	public List<PetitionRepeatInfo> getRepeateInfo(String xfno, String repeatFlag, String moreFlag, String sameDealFlag) {
		Session session = this.getSession();
		Criteria criteriaMain = session.createCriteria(PetitionRepeatInfo.class);
		criteriaMain.add(Restrictions.eq("petitionNo", xfno));
		if (repeatFlag != null && !"".equals(repeatFlag))
			criteriaMain.add(Restrictions.eq("repeatFlag", repeatFlag));
		if (moreFlag != null && !"".equals(moreFlag))
			criteriaMain.add(Restrictions.eq("moreFlag", moreFlag));
		if (sameDealFlag != null && !"".equals(sameDealFlag))
			criteriaMain.add(Restrictions.eq("sameDealFlag", sameDealFlag));
		String regionCode =  (String) Struts2Utils.getSession().getAttribute("curRegionCode");
		criteriaMain.add(Restrictions.eq("regionCode", regionCode));
		return criteriaMain.list(); 
	}
}
