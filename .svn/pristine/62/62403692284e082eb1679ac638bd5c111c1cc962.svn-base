package com.sinosoft.xf.system.logon.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.system.logon.dao.LoginIpDao;
import com.sinosoft.xf.system.logon.domain.LoginIp;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;
/**
 * 注入集中式部署的区域ip manager
 * @date 2012-08-13
 * @author oba 
 */
@Service
@Transactional
public class LoginIpManager extends EntityManager<LoginIp,String> {
	/**
	 * 注入集中式部署的区域ip dao
	 */
	@Autowired
	LoginIpDao loginIpDao;

	@Override
	protected HibernateDao<LoginIp, String> getEntityDao() {
		return loginIpDao;
	}
	/**
	 * 通过ip信息得到区域信息，包括regionCode和regionName
	 * @return
	 * @throws IOException 
	 */
	@Transactional(readOnly=true)
	public String getRegionByIp(String ip,String realPath) throws IOException{
		LoginIp loginIp = loginIpDao.getInfoByIp(ip);
		if(null==loginIp){
			return "";
		}
		return loginIp.getRegionCode()+";"+loginIp.getRegionName();
	}
	/**
	 * 保存loginIp
	 * @date 2014-02-27
	 * @author Liuchang
	 */
	public void saveLoginIp(String regionCode,String regionName,String[] loginIps) throws RuntimeException{
		if(loginIps != null){
			for(int i = 0; i < loginIps.length ; i++){
				LoginIp loginIp = new LoginIp();
				loginIp.setIp(loginIps[i]);
				loginIp.setRegionCode(regionCode);
				loginIp.setRegionName(regionName);
				loginIpDao.save(loginIp);
				CodeSwitchUtil.getLoginIpMap().put(loginIps[i],regionCode+";"+regionName);
			}
		}
	}

	/**
	 * 查询访问Ip
	 * @date 2014-02-27
	 * @author Liuchang
	 */
	@Transactional(readOnly=true)
	public ExtjsPage<LoginIp> getInfoByRegionCode(String regionCode,int start,int limit,String ip){ 
		List<LoginIp> loginIpList = new ArrayList<LoginIp>();
		ExtjsPage<LoginIp> page = new ExtjsPage<LoginIp>();
		loginIpList = loginIpDao.getInfoByRegionCode( regionCode,start,limit,ip);
		int size=loginIpDao.getInfoByRegionCodeSize( regionCode,start,limit,ip);
		if(loginIpList == null){
			page.setTotalCount(0);
		}else{
			page.setTotalCount(size);
		}
		page.setResult(loginIpList);
		return page;
	}
	/**
	 * 删除loginIp
	 * @date 2014-02-27
	 * @author Liuchang
	 */
	public void delIp(String delIp)  throws RuntimeException{
		LoginIp loginIp = loginIpDao.findBy("ip", delIp).get(0);
		loginIpDao.delete(loginIp);
		CodeSwitchUtil.getLoginIpMap().remove(loginIp.getIp());
	}
}

