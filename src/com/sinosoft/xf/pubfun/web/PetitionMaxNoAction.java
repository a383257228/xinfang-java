package com.sinosoft.xf.pubfun.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.pubfun.domain.PetitionMaxNoInfo;
import com.sinosoft.xf.pubfun.manager.PetitionMaxNoInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;

public class PetitionMaxNoAction extends CrudExtActionSupport<PetitionMaxNoInfo,String> {
	private static final long serialVersionUID = 1L;
	//实例化最大号信息对象
	private PetitionMaxNoInfo entity = new PetitionMaxNoInfo();
	/**
	 * 注入最大号信息manager
	 */
	@Autowired
	PetitionMaxNoInfoManager maxNoInfoManager;
	/**
	 * 重写父类方法
	 * @return 最大号信息manager
	 */
	@Override
	protected EntityManager<PetitionMaxNoInfo,String> getEntityManager() {
		return maxNoInfoManager;
	}
	/**
	 * 重写父类方法
	 * @throws Exception
	 * @return 最大号信息对象
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = maxNoInfoManager.get(id);
		} else {
			entity = new PetitionMaxNoInfo();
			entity.setId(null);
		}
	}
	/**
	 * 获取最大号信息对象
	 * @return 最大号信息对象
	 */
	public PetitionMaxNoInfo getModel() {
		return entity;
	}

}
