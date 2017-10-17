package com.sinosoft.organization.dao;


import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sinosoft.organization.domain.GroupInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
/**
 * 机构分组信息dao层
 * @author wangxx 
 * @date 2010-10-11
 */
@Repository
public class GroupInfoDao extends HibernateDao<GroupInfo, String> {
	
	/**
	 * 通过分组属性查询分组对象，返回的对象只需要一个，如果没有返回null
	 * @date 2010-11-05
	 * @author wangxx
	 * @param txt 分组信息属性名
	 * @param value 分组信息属性值
	 * @return 分组信息对象
	 */
	public GroupInfo getGroupInfo(String txt,String val){
		// 封装查询参数，查询人信息记录Oid
		Criterion criterion = Restrictions.eq(txt, val);
		// 根据查询条件，查询
		List<GroupInfo> list = this.find(criterion);
		//判断是否取出来值，如果有值就进行选取
		if(list.size()>0){
			//获取机构对象
			GroupInfo returnGroupInfo=list.get(0);
			//返回机构对象
			return returnGroupInfo;
		}
		//如果没有获取到机构对象，则返回null
		return null;
	}
}
