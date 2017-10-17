package com.sinosoft.xf.pubfun.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.pubfun.dao.PetitionMaxNoInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;
/**
 * 最大号信息dao测试类
 * @date 2011-09-15
 * @author wangxx
 */
public class PetitionMaxNoDaoTest extends SpringTxTestCase {
	/**
	 * 注入最大号信息dao
	 */
	@Autowired
	PetitionMaxNoInfoDao maxNoInfoDao;
	/**
	 * 测试配置关系
	 */
	@Test
	public void getInfo(){
		logger.info(maxNoInfoDao.getAll().size());
	}
}
