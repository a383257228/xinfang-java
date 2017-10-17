package com.sinosoft.organization.dao.test;



import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.GroupItemInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;
/**
 * @date 2010-06-22
 * @author wangxx
 * 机构分组信息表单元测试类
 */
public class GroupItemInfoDaoTest  extends SpringTxTestCase{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	GroupItemInfoDao groupItemInfoDao;
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){
		logger.info(groupItemInfoDao.get("00fd012c9bd8a6a10005").getId());
		logger.info("-----------------------------");
		logger.info(groupItemInfoDao.get("00fd012c9bd8a6a10005").getId());
	}
	/**
	 * 测试通过实体类oid查处分组关系对象
	 */
	@Test
	public void getGroupItemSizeByGroupOidTest(){
		logger.info(groupItemInfoDao.getGroupItemSizeByGroupOid("00fd012ce4db9aaf0015"));
	}
	/**
	 * 测试通过分组信息oid查询分组关联信息对象集合数量
	 */
	@Test
	public void getGroupItemInfoByObjOidTest(){
		logger.info(groupItemInfoDao.getGroupItemInfoByObjOid("00fd012ffcf7a05a0006").size());
	}
}
