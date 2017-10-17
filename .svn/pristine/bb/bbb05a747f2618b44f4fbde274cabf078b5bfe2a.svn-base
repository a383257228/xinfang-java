package com.sinosoft.authority.manager;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.authority.dao.AuthorityUserRelationDao;
import com.sinosoft.authority.domain.AuthorityUserRelation;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
/**
 * 用户菜单权限Manager层
 * @author sunzhe
 * Create 2010-08-10
 */
@Service
@Transactional
public class AuthorityUserRelationManager extends EntityManager<AuthorityUserRelation, String>{
	
	//注入用户角色权限信息dao层
	@Autowired
	AuthorityUserRelationDao authorityUserRelationDao;
	
	@Override
	protected HibernateDao<AuthorityUserRelation, String> getEntityDao() {
		return authorityUserRelationDao;
	}
	
	/**
	 * 保存用户所对应的菜单功能信息
	 * @param userId 人员ID
	 * @param menuId 功能菜单ID
	 * @param parentMenuId 父功能菜单ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveConfigRole(String userId,String menuId,String parentMenuId){
		if(!userId.equals("")){
			OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
			authorityUserRelationDao.deleteByUser(userId);
			if(!menuId.equals("")){
				//保存功能菜单信息
				String[] menuIds = menuId.split(",");
				String[] parentMenuIds = parentMenuId.split(",");
				for(int i=0;i<menuIds.length;i++){
					if(parentMenuIds[i]!=null&&!parentMenuIds[i].equals("")&&!parentMenuIds[i].equals("-1")){
						AuthorityUserRelation userRelation = new AuthorityUserRelation();
						userRelation.setMenu(menuIds[i]);
						userRelation.setParentMenu(parentMenuIds[i]);
						userRelation.setPerson(userId);
						userRelation.setOperateDate(new Timestamp(System.currentTimeMillis()));
						if(onlineUser!=null)
							userRelation.setOperator(onlineUser.getUserId());
						authorityUserRelationDao.save(userRelation);
					}
				}
			}
		}
	}
}
