package com.sinosoft.xf.system.set.manager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.petition.supervision.dao.TimeLimitDefineDao;
import com.sinosoft.xf.petition.supervision.domain.TimeLimitDefine;
import com.sinosoft.xf.system.set.dao.CentralizedDeploymentInfoDao;
import com.sinosoft.xf.system.set.domain.CentralizedDeploymentInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

/**
 * 集中式部署信息manager
 * 
 * @date 2012-03-12
 * @author oba
 */
@Service
@Transactional
public class CentralizedDeploymentInfoManager extends EntityManager<CentralizedDeploymentInfo, String> {

	/**
	 * 注入集中式信息dao
	 */
	@Autowired
	CentralizedDeploymentInfoDao centralizedDeploymentInfoDao;

	@Autowired
	TimeLimitDefineDao timeLimitDefineDao;

	/**
	 * 新增集中式部署信息，并对系统信息进行初始化
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public void saveCentralizedDeploymentInfoAndInitSystemVariable(CentralizedDeploymentInfo centralizedDeploymentInfo) throws Exception {
		try {
			initSystemVariable(centralizedDeploymentInfo);
			initPersonInfo(centralizedDeploymentInfo);
			centralizedDeploymentInfoDao.save(centralizedDeploymentInfo);
			CodeSwitchUtil.getCentralizedMap().put(centralizedDeploymentInfo.getRegionCode(), centralizedDeploymentInfo.getRegionName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 初始化信息通信息
	 * 
	 * @param centralizedDeploymentInfo
	 *            集中式部署信息
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	private void initSystemVariable(CentralizedDeploymentInfo centralizedDeploymentInfo) throws Exception {
		initNo(centralizedDeploymentInfo);
		initTimeLimitDefineDate(centralizedDeploymentInfo.getRegionCode());
	}

	/**
	 * 初始化编号信息
	 */
	private void initNo(CentralizedDeploymentInfo centralizedDeploymentInfo) {
		// 目前没有需要定制化的编号信息
	}

	/**
	 * 初始化用户信息
	 */
	private void initPersonInfo(CentralizedDeploymentInfo centralizedDeploymentInfo) {

	}

	/**
	 * 初始化时限设置基础数据
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void initTimeLimitDefineDate(String regionCode) throws Exception {
		List<TimeLimitDefine> list1 = timeLimitDefineDao.getByRegionCode(regionCode);
		for (TimeLimitDefine timeLimitDefine : list1) {
			timeLimitDefineDao.delete(timeLimitDefine);
		}
		List<TimeLimitDefine> list = timeLimitDefineDao.getByRegionCode(Constants.SH_REGION_CDOE);
		for (int i = 0; i < list.size(); i++) {
			TimeLimitDefine timeLimitDefineDate = list.get(i);
			TimeLimitDefine timeLimitDefine = new TimeLimitDefine();
			PropertyUtils.copyProperties(timeLimitDefine, timeLimitDefineDate);
			timeLimitDefine.setId(null);
			timeLimitDefine.setRegionCode(regionCode);
			timeLimitDefineDao.save(timeLimitDefine);
		}
	}

	@Override
	protected HibernateDao<CentralizedDeploymentInfo, String> getEntityDao() {
		return centralizedDeploymentInfoDao;
	}
}
