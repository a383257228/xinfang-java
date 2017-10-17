/**
 * 
 */
package com.sinosoft.organization.dao.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.OrganPersonRelationDao;
import com.sinosoftframework.core.test.SpringTxTestCase;
import com.sinosoftframework.core.utils.time.TimeUtils;

/**   
 *    
 * 项目名称：organ   
 * 类名称：OrganPersonRelationDaoTest   
 * 类描述：   机构人员关系dao层测试类
 * 创建人：shenhy   
 * 创建时间：2010-5-17 
 *    
 */

public class OrganPersonRelationDaoTest extends SpringTxTestCase{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	OrganPersonRelationDao organPersonRelationDao;
	
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){
		logger.info(organPersonRelationDao.get("1168575537889138    ").getId());
		logger.info("-----------------------------");
		logger.info(organPersonRelationDao.get("1168575537889138    ").getId());
	}
	/**
	 * 测试关联查询
	 * 通过用户属性查找机构用户关系对象
	 */
	@Test
	public void getOrganPersonRelaListByPersonTest(){
		TimeUtils.begin();
		logger.info(organPersonRelationDao.getOrganPersonRelaListByPerson("userEname", "gouzhongjun").size());
		TimeUtils.end("测试结束");
	}
}
