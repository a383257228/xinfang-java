/**
 * 
 */
package com.sinosoft.organization.dao.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;

/**   
 *    
 * 项目名称：organ   
 * 类名称：PersonInfoDaoTest   
 * 类描述：   用户信息dao层测试类
 * 创建人：shenhy   
 * 创建时间：2010-5-19   
 *    
 */

public class PersonInfoDaoTest   extends SpringTxTestCase{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	PersonInfoDao personInfoDao;
	
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){
		logger.info(personInfoDao.get("1168573389484871    ").getUserCname());
		logger.info("------------------------------");
		logger.info(personInfoDao.get("1168573389484871    ").getUserCname());
	}

}
