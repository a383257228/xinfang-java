package com.sinosoft.organization.dao.test;



import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.GroupInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;
/**
 * @date 2010-06-22
 * @author wangxx
 * 机构分组信息表单元测试类
 */
public class GroupInfoDaoTest  extends SpringTxTestCase{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	GroupInfoDao groupInfoDao;
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){
		logger.info(groupInfoDao.get("00fd012c9bd8a6530003").getId());
		logger.info("-----------------------------");
		logger.info(groupInfoDao.get("00fd012c9bd8a6530003").getId());
	}
}
