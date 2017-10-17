/**
 * 
 */
package com.sinosoft.organization.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.OrganMergeSplitInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;

/**   
 *    
 * 项目名称：organ   
 * 类名称：OrganMergeSplitInfoDaoTest   
 * 类描述：   机构合并拆分信息dao层测试类
 * 创建人：shenhy   
 * 创建时间：2010-5-19   
 *    
 */

public class OrganMergeSplitInfoDaoTest   extends SpringTxTestCase{
	@Autowired
	OrganMergeSplitInfoDao organMergeSplitInfoDao;
	
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){
		logger.info(organMergeSplitInfoDao.get("00fd012ca65a3f740007").getId());
		logger.info("-----------------------------");
		logger.info(organMergeSplitInfoDao.get("00fd012ca65a3f740007").getId());
	}

}
