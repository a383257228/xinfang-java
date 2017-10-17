package com.sinosoft.xf.pubfun.manager.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.pubfun.domain.PetitionMaxNoInfo;
import com.sinosoft.xf.pubfun.manager.PetitionMaxNoInfoManager;
import com.sinosoftframework.core.test.SpringTxTestCase;
/**
 * 最大号信息manager测试类
 * @date 2011-09-15
 * @author wangxx
 */
public class PetitionMaxNoManagerTest extends SpringTxTestCase {
	
	/**
	 * 注入最大号manager
	 */
	@Autowired
	PetitionMaxNoInfoManager maxNoInfoManager;
	
	/**
	 * 测试配置关系
	 */
	@Test
	public void getInfo(){
		PetitionMaxNoInfo entity = new PetitionMaxNoInfo();
	//	maxNoInfoManager.save(entity);
		maxNoInfoManager.getAll();
	}
}
