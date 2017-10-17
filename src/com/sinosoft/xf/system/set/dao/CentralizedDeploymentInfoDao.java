package com.sinosoft.xf.system.set.dao;

import org.springframework.stereotype.Repository;

import com.sinosoft.xf.system.set.domain.CentralizedDeploymentInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 集中式部署信息dao
 * @date 2012-03-12
 * @author oba
 */
@Repository
public class CentralizedDeploymentInfoDao extends HibernateDao<CentralizedDeploymentInfo,String> {
}
