package com.sinosoft.xf.petition.scan.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.petition.scan.domain.ReportUploadFile;
import com.sinosoft.xf.util.TimeUtils;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 扫描文件Dao
 */
@Repository
@SuppressWarnings("unchecked")
public class ReportUploadFileDao extends HibernateDao<ReportUploadFile,String>{

	/**
	 * 查询列表个数
	 */
	public int listReportUploadFileCount(String filterTxt, String filterValue) throws Exception{
		Criteria criteria = appReportUploadFile(filterTxt, filterValue);
		return (Integer) criteria.setProjection(Projections.count("id")).uniqueResult();
	}

	/**
	 * 列表查询
	 * @return
	 */
	public List<ReportUploadFile> listReportUploadFile(String filterTxt, String filterValue, String sort, String dir, int start, int limit) throws Exception{
		Criteria criteria = appReportUploadFile(filterTxt, filterValue);
		if (sort != null && !"".equals(sort)) {
			boolean isAsc = dir.equalsIgnoreCase(Constants.ASC);
			if (isAsc)
				criteria.addOrder(Order.asc(sort));
			else
				criteria.addOrder(Order.desc(sort));
		}
		criteria.setFirstResult(start).setMaxResults(limit);
		return criteria.list();
	}
	/**
	 * 列表查询条件拼接
	 * isSreator 是否添加上传人权限
	 * @param filterTxt
	 * @param filterValue
	 * @return
	 */
	private Criteria appReportUploadFile(String filterTxt, String filterValue) {
		Criteria criteria = this.getSession().createCriteria(ReportUploadFile.class);
		PersonInfo person = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		criteria.add(Restrictions.eq("regionCode",person.getRegionCode()));
		if (filterTxt != null && !"".equals(filterTxt.trim())) {
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			for(int i = 0; i < filterTxts.length; i++) {
				String stringTxt = filterTxts[i];
				String stringValue = filterValues[i];
				if("fileName".equals(stringTxt)){
					criteria.add(Restrictions.like(stringTxt, "%"+stringValue+"%"));
				} else if ("startDate".equals(stringTxt)) {
					criteria.add(Restrictions.ge("createDate", TimeUtils.string2Date(stringValue)));
				} else if ("endDate".equals(stringTxt)) {
					criteria.add(Restrictions.lt("createDate", new Timestamp(new DateTime(stringValue).plusDays(1).toDate().getTime())));
				} else if (stringValue != null && !"".equals(stringValue)) {
					criteria.add(Restrictions.eq(stringTxt, stringValue));
				}
			}
		}
		return criteria;
	}
//	/**
//	 * 查询所有类别
//	 * @Author zhouyic
//	 * @Date 2015-12-4
//	 * @return List<String>
//	 */
//	public List<String> getFileCategory()throws Exception{
//		String hql="select distinct category from ReportUploadFile";
//		Query query = getSession().createQuery(hql);
//		List<String> list=query.list();
//		return list;
//	}
}
