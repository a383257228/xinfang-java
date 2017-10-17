package com.sinosoft.organization.dao.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.organization.dao.OrganizationRelationInfoDao;
import com.sinosoftframework.core.test.SpringTxTestCase;
/**
 * @date 2010-06-22
 * @author wangxx
 * 机构关系表单元测试类
 */
public class OrganizationRelationInfoDaoTest  extends SpringTxTestCase{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	OrganizationRelationInfoDao organizationRelationInfoDao;
	
	/**
	 * 测试组织信息表的二级缓存
	 */
	@Test
	public void getInfo(){  
		logger.info(organizationRelationInfoDao.getOrganizationRelationInfoByOrganizationInfoOid("1168492054537144    ").size());
		logger.info("-----------------------------");
		logger.info(organizationRelationInfoDao.getOrganizationRelationInfoByOrganizationInfoOid("1168492054537144    ").size());
	}
	/**
	 * 测试判断有下级节点的方法
	 */
	@Test
	public void buildUserDeptTreeTest(){
		logger.info(organizationRelationInfoDao.buildMergeDeptTree("00000000000000000001"));
	}
	
	/**
	 * 测试通过组织属性和组织关系的父节点关系oid   查询组织关系集合
	 */
	@Test
	public void getOrganizationInfoAndParentOrgRelaOidTest(){
		logger.info(organizationRelationInfoDao.getOrgRelaInfoByOrgAndParentOrgRelaOid("orgCname","erererrr","00fd012afee4af0d0002").size());
	}
	
	/**
	 * @author wangxx
	 * 2010-12-06
	 * 测试通过组织oid查询器下级组织的个数
	 */
	@Test
	public void getOrgSizeTest() {
		logger.info(organizationRelationInfoDao.getOrgSize("", "00000000000000000001",""));
	}
	/**
	 * @author wangxx
	 *  2010-11-25
	 * 测试 根据组织关系表父关系对象Oid和组织对象的purpose属性查找关系表对象集合
	 */
	@Test
	public void getOrganRelaInfoByParentOidTest() {
		logger.info(organizationRelationInfoDao.getOrganRelaInfoByParentOid("2", "00000000000000000001","1").size());
	}
	
	/**
	 * Test:通过组织属性查询系统中已经存在这样属性的组织的个数
	 */
	@Test
	public void judgeOrganizationInfosTest(){
		logger.info(organizationRelationInfoDao.judgeOrganizationInfos("orgEname", new String[]{"C10000250000","C10000260000"}));
	}
	/**
	 * Test:通过组织基本信息属性查找组织关系对象，带分页和排序。
	 */
	@Test
	public void getOrganizationRelationInfoListByOrganizationTest(){
		organizationRelationInfoDao.getOrganizationRelationInfoListByOrganization("orgEname", "c", 1, 20, "orgEname");
	}
}
