package com.sinosoft.xf.workflow.manager;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.workflow.dao.PetitionCirculationStateInfoDao;
import com.sinosoft.xf.workflow.dao.PetitionCirculationStateTraceInfoDao;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateTraceInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

/**
 * 信访流转状态轨迹信息manager
 * @date 2011-09-13
 * @author wangxx 
 */
@Service
@Transactional
public class PetitionCirculationStateTraceInfoManager extends EntityManager<PetitionCirculationStateTraceInfo, String> {
	/**
	 * 注入信访流转状态轨迹信息dao
	 */
	@Autowired
	private PetitionCirculationStateTraceInfoDao petitionCirculationStateTraceInfoDao;
	

	/**
	 * 注入信访流转状态轨迹信息dao
	 */
	@Autowired
	private PetitionCirculationStateInfoDao petitionCirculationStateInfoDao;
	/**
	 * 重写父类方法
	 * @return 信访流转状态轨迹信息dao
	 */
	@Override
	protected HibernateDao<PetitionCirculationStateTraceInfo, String> getEntityDao() {
		return petitionCirculationStateTraceInfoDao;
	}

	/**
	 * 查询轨迹信息
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly=true)
	public List<PetitionCirculationStateTraceInfo> listByPetitionNo(String filterTxt,String filterValue,int start,int limit) throws Exception {
		return petitionCirculationStateTraceInfoDao.listByPetitionNo(filterTxt,filterValue,start,limit);
	}
	@Transactional(readOnly=true)
	public int listByPetitionNoSize(String filterTxt,String filterValue) throws Exception {
		return petitionCirculationStateTraceInfoDao.listByPetitionNoSize(filterTxt,filterValue);
	}
	/**
	 * 判断状态中是否存在指定状态
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public List<PetitionCirculationStateTraceInfo> listByBasicInfoOid(String petitionBasicInfoOid, String activityNo) {
		return petitionCirculationStateTraceInfoDao.listByBasicInfoOid(petitionBasicInfoOid, activityNo);
	}
	@Autowired
	PetitionBasicInfoManager basicInfoManager;
}