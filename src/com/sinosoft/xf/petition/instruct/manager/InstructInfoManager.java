package com.sinosoft.xf.petition.instruct.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.petition.instruct.dao.InstructInfoDao;
import com.sinosoft.xf.petition.instruct.domain.InstructInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**批示信息manager
 * @author ZHZ
 *@date 2017年9月1日
 */
@Service
@Transactional
public class InstructInfoManager extends EntityManager<InstructInfo, String> {

	@Autowired
	InstructInfoDao instructInfoDao;

	@Override
	protected HibernateDao<InstructInfo, String> getEntityDao() {
		// TODO Auto-generated method stub
		return instructInfoDao;
	}
}
