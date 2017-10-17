package com.sinosoft.xf.petition.scan.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.petition.scan.dao.ReportUploadFileDao;
import com.sinosoft.xf.petition.scan.domain.ReportUploadFile;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;

@Service
@Transactional
public class ReportUploadFileManager extends EntityManager<ReportUploadFile, String> {
	@Autowired
	ReportUploadFileDao reportUploadFileDao;
	@Override
	protected HibernateDao<ReportUploadFile, String> getEntityDao() {
		return reportUploadFileDao;
	}
	/**
	 * 查询列表
	 */
	@Transactional(readOnly=true)
	public ExtjsPage<ReportUploadFile> listReportUploadFile(String filterTxt, String filterValue, String sort, String dir, int start, int limit) throws Exception {
		ExtjsPage<ReportUploadFile> page = new ExtjsPage<ReportUploadFile>();
		int size = reportUploadFileDao.listReportUploadFileCount(filterTxt,filterValue);
		List<ReportUploadFile> list = reportUploadFileDao.listReportUploadFile(filterTxt,  filterValue,  sort,dir,  start, limit);
		page.setTotalCount(size);
		page.setResult(list);
		return page;
	}
//	public String queryFileCategory() throws Exception{
//		List<String> categoryList = reportUploadFileDao.getFileCategory();
//		StringBuffer sb=new StringBuffer("[");
//		if(categoryList.size()>0){
//			for(int i=0;i<categoryList.size();i++){
//				if("chart".equals(categoryList.get(i))){
//					sb.append("{");
//					sb.append("'category':");
//					sb.append("'chart',");
//					sb.append("'categoryName':");
//					sb.append("'报表文件'},");
//				}else if("secretary".equals(categoryList.get(i))){
//					sb.append("{");
//					sb.append("'category':");
//					sb.append("'secretary',");
//					sb.append("'categoryName':");
//					sb.append("'书记专题会文件'},");
//				}else if("otherFiles".equals(categoryList.get(i))){
//					sb.append("{");
//					sb.append("'category':");
//					sb.append("'otherFiles',");
//					sb.append("'categoryName':");
//					sb.append("'其它文件'},");
//				}
//			}
//		}
//		if(sb.length()>0){
//			sb.deleteCharAt(sb.length()-1);
//		}
//		sb.append("]");
//		return sb.toString();
//	}
}