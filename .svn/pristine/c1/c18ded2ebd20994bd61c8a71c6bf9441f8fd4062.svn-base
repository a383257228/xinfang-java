package com.sinosoft.xf.util.listener;

import java.sql.Timestamp;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

public class PetitionAuditListener implements SaveOrUpdateEventListener {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(PetitionAuditListener.class);

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		Object object = event.getObject();

		if (object instanceof AudiEntity) {
			AudiEntity entity = (AudiEntity) object;

			OnlineUser onlineUser = null;
			try {
				onlineUser = AppUtils.getOnlineUser(Struts2Utils.getRequest().getSession().getId());
			} catch (NullPointerException e) {
				logger.debug(e.toString());
			}
			String loginName = "系统管理员";
			String userId = "0000000000000000000";

			if (onlineUser != null) {
				loginName = onlineUser.getUserCName();
				userId = onlineUser.getUserId();
			}

			if (entity.getId() == null) {
				if (onlineUser != null) {
					if (entity.getCreateDate() == null)
						entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
					if (entity.getCreatorId() == null || entity.getCreatorId().equals(""))
						entity.setCreatorId(userId);
					if (entity.getCreatorName() == null || entity.getCreatorName().equals(""))
						entity.setCreatorName(loginName);
			        entity.setModifyDate(new Timestamp(System.currentTimeMillis()));
			        entity.setModifyName(loginName);
			        entity.setModifyId(userId);
				}else{//如果没有在线用户而进行保存，可能是由于自动传输等功能进行的操作，需要特殊处理
					if (entity.getCreateDate() == null)
						entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
					if (entity.getCreatorId() == null || entity.getCreatorId().equals(""))
						entity.setCreatorId(userId);
					if (entity.getCreatorName() == null || entity.getCreatorName().equals(""))
						entity.setCreatorName(loginName);
					if (entity.getModifyDate() == null)
						entity.setModifyDate(new Timestamp(System.currentTimeMillis()));
					if (entity.getModifyName() == null || entity.getModifyName().equals(""))
						entity.setModifyName(loginName);
					if (entity.getModifyId() == null || entity.getModifyId().equals(""))
						entity.setModifyId(userId);
				}
			} else {
				if (onlineUser != null) {
			        entity.setModifyName(loginName);
			        entity.setModifyId(userId);
			        entity.setModifyDate(new Timestamp(System.currentTimeMillis()));
				}else{//如果没有在线用户而进行保存，可能是由于自动传输等功能进行的操作，需要特殊处理
					if (entity.getModifyDate() == null)
						entity.setModifyDate(new Timestamp(System.currentTimeMillis()));
					if (entity.getModifyName() == null || entity.getModifyName().equals(""))
						entity.setModifyName(loginName);
					if (entity.getModifyId() == null || entity.getModifyId().equals(""))
						entity.setModifyId(userId);
				}
			}
		}
	}
}
