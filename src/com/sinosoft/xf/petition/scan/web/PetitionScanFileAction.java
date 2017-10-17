package com.sinosoft.xf.petition.scan.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.petition.scan.domain.PetitionScanFile;
import com.sinosoft.xf.petition.scan.manager.PetitionScanFileManager;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
@Namespace("/petition")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-scan-file.action", type = "redirect")})
public class PetitionScanFileAction extends CrudExtActionSupport<PetitionScanFile, String> {
	private static final long serialVersionUID = 1L;
	PetitionScanFile entity = new PetitionScanFile();
	@Autowired
	PetitionScanFileManager petitionScanFileManager;
	@Override
	protected EntityManager<PetitionScanFile, String> getEntityManager() {
		return petitionScanFileManager;
	}
	@Override
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = petitionScanFileManager.get(id);
		} else {
			entity = new PetitionScanFile();
		}
	}
	@Override
	public PetitionScanFile getModel() {
		return entity;
	}
	public void listScanFile() throws Exception{
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
			ExtjsPage<PetitionScanFile> page = petitionScanFileManager.listScanFile(map,filterTxt, filterValue, sort, dir, start, limit);
			JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), getExcludes(), getDatePattern());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
