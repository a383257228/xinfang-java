package com.sinosoft.xf.petition.scan.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.petition.scan.domain.ReportUploadFile;
import com.sinosoft.xf.petition.scan.manager.ReportUploadFileManager;
import com.sinosoft.xf.util.FileUtil;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 历史报表上传Action
 */
@Namespace("/petition")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "report-upload-file.action", type = "redirect"),
		@Result(name = "download", type = "stream", params = { 
				"contentType", "application/octet-stream;charset=utf-8", "inputName", "inputStream",
				"contentDisposition", "attachment;filename=\"${fileFileName}\"",
				"bufferSize", "4096" })
})
public class ReportUploadFileAction extends
		CrudExtActionSupport<ReportUploadFile, String> {
	private static final long serialVersionUID = 1L;
	/**
	 * 注入任务信息Manager
	 */
	@Autowired
	private ReportUploadFileManager reportUploadFileManager;

	@Override
	protected EntityManager<ReportUploadFile, String> getEntityManager() {
		return reportUploadFileManager;
	}

	private ReportUploadFile entity = new ReportUploadFile();

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = reportUploadFileManager.get(id);
		} else {
			entity = new ReportUploadFile();
		}

	}

	public ReportUploadFile getModel() {
		return entity;
	}

	private File file;
	private String fileFileName;

	 /** 
     * 下载文档，下载问价的访问入口方法 
     * 
     * @return resultName 
     */  
//    public String testDownload() {  
//        // 返回 @Result 注解中的 name 值  
//        return "download";  
//    }  
	/**
	 * 文件下载
	 * @author hjh
	 * 2015年9月6日
	 * @return
	 * @throws Exception
	 */
	public String downloadFile() throws Exception {
		ReportUploadFile entity = reportUploadFileManager.get(id);
//		String path = Struts2Utils.getSession().getServletContext()
//				.getRealPath(File.separator + entity.getFilePath());
		setFileFileName(new String(entity.getFileName().getBytes(), "iso8859-1") );
		return "download";
	}

	/**
	 * 文件下载辅助方法，struts2自动调用
	 * @author hjh
	 * 2015年9月6日
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream() throws Exception {
		ReportUploadFile entity = reportUploadFileManager.get(id);
		String path = Struts2Utils.getSession().getServletContext() .getRealPath(File.separator + entity.getFilePath());
		return new FileInputStream(new File(path));
	}
//	/**
//	 * 查询所有文件类别
//	 * @Author zhouyic
//	 * @Date 2015-12-4
//	 * @return void
//	 */
//	public void queryFileCategory() throws Exception{
//		String queryFileCategory = reportUploadFileManager.queryFileCategory();
//		Struts2Utils.getResponse().getWriter()
//		.print(queryFileCategory);
//	}
	/**
	 * 文件上传
	 * 
	 * @throws IOException
	 */
	public void fileUpload() throws IOException {
		
		Struts2Utils.getRequest().setCharacterEncoding("UTF-8");
		String category = Struts2Utils.getRequest().getParameter("category");
		Struts2Utils.getResponse().setContentType("text/html;charset=GBK");
		String path = Struts2Utils.getSession().getServletContext()
				.getRealPath(File.separator);
		try {
			if(!com.sinosoftframework.core.utils.string.StringUtils.isEmpty(ids)){
					String[] idArray = ids.split(";");
					for(int i=0;i<idArray.length;i++){
						entity =reportUploadFileManager.get(idArray[i]);
						entity.setCategory(category);
						File folder = new File(path + "uploadfile" + File.separator
								+ category);
						if (!folder.exists())
							folder.mkdirs();
						reportUploadFileManager.save(entity);
					}
			}else{
				String fileExt = fileFileName.substring(fileFileName
						.lastIndexOf('.'));
				entity = new ReportUploadFile();
				entity.setFileName(fileFileName);
				entity.setFileSize(file.length());
				entity.setFileType(fileExt);// .txt;.pdf；.jpg格式
				PersonInfo person = (PersonInfo) Struts2Utils
						.getSessionAttribute("personInfo");
				entity.setRegionCode(person.getRegionCode());
				entity.setRegionName(person.getRegionName());
				entity.setCategory(category);
				entity.setFilePath("uploadfile" + File.separator + category
						+ File.separator + UUID.randomUUID() + fileExt);
				File folder = new File(path + "uploadfile" + File.separator
						+ category);
				if (!folder.exists())
					folder.mkdirs();
				reportUploadFileManager.save(entity);
				FileUtils.copyFile(file, new File(path + entity.getFilePath()));
			}
			
			Struts2Utils.getResponse().getWriter()
					.print(Constants.SUCCESS_TRUE);
		} catch (Exception e) {
			Struts2Utils.getResponse().getWriter()
					.print(Constants.SUCCESS_FALSE);
			e.printStackTrace();
		}
	}
//    /**
//     * 查询文件类别
//     * @Author zhouyic
//     * @Date 2015-12-4
//     * @return void
//     */
//    public void queryFileCategory()throws Exception{
//    	System.out.println("eeee");
//    }
	/**
	 * 查询列表
	 * 
	 * @throws Exception
	 */
	public void listReportUploadFile() throws Exception {
		ExtjsPage<ReportUploadFile> page = reportUploadFileManager
				.listReportUploadFile(filterTxt, filterValue, sort, dir, start,
						limit);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(),
				getExcludes(), Constants.DateLong);
	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	public void delReportUploadFile() throws Exception {
		reportUploadFileManager.delete(id);
		String path = Struts2Utils.getSession().getServletContext()
				.getRealPath(File.separator + entity.getFilePath());
		new File(path).delete();
	}

	/**
	 * 多媒体文件下载
	 * 
	 * @author hxh 被前台哪些模块调用： 1、upload.SingleUploadContractUploadGrid调用
	 * @throws IOException
	 */
	public void fileDownload() throws IOException {
		String path = Struts2Utils.getSession().getServletContext()
				.getRealPath(File.separator + entity.getFilePath());
		HttpServletResponse response = Struts2Utils.getResponse();
		OutputStream out = response.getOutputStream();
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(entity.getFileName().getBytes("GBK"), "ISO8859-1"));
		out.write(FileUtil.file2Byte(new File(path)));
		out.close();

	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
