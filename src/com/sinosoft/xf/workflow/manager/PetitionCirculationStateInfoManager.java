package com.sinosoft.xf.workflow.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.workflow.dao.PetitionCirculationStateInfoDao;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

/**
 * 信访流转状态信息manager
 * @date 2011-09-13
 * @author wangxx 
 */
@Service
@Transactional
public class PetitionCirculationStateInfoManager extends EntityManager<PetitionCirculationStateInfo, String> {
	/**
	 * 注入信访流转状态信息dao
	 */
	@Autowired
	private PetitionCirculationStateInfoDao petitionCirculationStateInfoDao;
	
	/**
	 * 重写父类方法
	 * @return 信访流转状态信息dao
	 */
	@Override
	protected HibernateDao<PetitionCirculationStateInfo, String> getEntityDao() {
		return petitionCirculationStateInfoDao;
	}
}