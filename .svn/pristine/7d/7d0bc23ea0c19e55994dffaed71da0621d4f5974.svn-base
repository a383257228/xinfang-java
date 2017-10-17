package com.sinosoft.xf.system.logon.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.system.logon.domain.LoginIp;
import com.sinosoft.xf.system.logon.manager.LoginIpManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 注入集中式部署的区域ip action
 * @author oba
 */
@Namespace("/logon")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "login-ip.action", type = "redirect")})
public class LoginIpAction extends CrudExtActionSupport<LoginIp, String> {
	private static final long serialVersionUID = 1L;
	//注入集中式部署的区域ip业务层
	@Autowired
	LoginIpManager loginIpManager;
	
	LoginIp entity = new LoginIp();

	//IP集合
	private String[] loginIps;
	//访问区域编码
	private String regionCode;
	//访问区域编码名
	private String regionName;
	//IP
	private String delIp;
	
	/**
	 * 通过ip获得行政区域
	 * @throws IOException 
	 */
	public void getInfoByIp() throws IOException{
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			String realPath = request.getRealPath("/");
			String region = loginIpManager.getRegionByIp(entity.getIp(),realPath);
			Struts2Utils.getResponse().getWriter().print(region);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.getResponse().getWriter().print(Constants.SUCCESS_FALSE);
		}
	}
	/**
	 * 重写父类方法
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id)) {
			entity = loginIpManager.get(id);
		} else {
			entity = new LoginIp();
		}
	}
	
	/**
	 * 重写父类的方法
	 * @return 人员信息Manager
	 */
	@Override
	protected EntityManager<LoginIp, String> getEntityManager() {
		return loginIpManager;
	}
	
	/**
	 * 获得处理状态实体
	 * @return 处理状态实体
	 */
	public LoginIp getModel() {
		return entity;
	}

	/**
	 * 保存loginIp
	 * @date 2014-02-27
	 * @author Liuchang
	 * @throws IOException 
	 */
	public void saveLoginIp() throws IOException{
		try {
			loginIpManager.saveLoginIp(regionCode, regionName, loginIps);
			Struts2Utils.getResponse().getWriter().print(Constants.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询访问Ip
	 * @date 2014-02-27
	 * @author Liuchang
	 */
	public void getInfoByRegionCode() throws IOException{
		try {
			ExtjsPage<LoginIp> extjsPage = loginIpManager.getInfoByRegionCode(regionCode, start, limit,entity.getIp());
			JsonUtils.write(extjsPage, Struts2Utils.getResponse().getWriter(), getExcludes(), getDatePattern());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除loginIp
	 * @date 2014-02-27
	 * @author Liuchang
	 * @throws IOException 
	 */
	public void delIp() throws IOException{
		try {
			loginIpManager.delIp(delIp);
			Struts2Utils.getResponse().getWriter().print(Constants.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String[] getLoginIps() {
		return loginIps;
	}
	public void setLoginIps(String[] loginIps) {
		this.loginIps = loginIps;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getDelIp() {
		return delIp;
	}
	public void setDelIp(String delIp) {
		this.delIp = delIp;
	}
}
