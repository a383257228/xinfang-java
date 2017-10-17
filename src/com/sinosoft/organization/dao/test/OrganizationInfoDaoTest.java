package com.sinosoft.organization.dao.test;



import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;
/**
 * @date 2010-06-22
 * @author wangxx
 * 机构信息表单元测试类
 */
public class OrganizationInfoDaoTest  extends SpringTxTestCase{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	OrganizationInfoDao organizationInfoDao;
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){
		logger.info(organizationInfoDao.get("00000000000000000001").getOrgCname());
		logger.info("-----------------------------");
		logger.info(organizationInfoDao.get("00000000000000000001").getOrgCname());
	}
}
