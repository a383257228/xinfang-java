package com.sinosoft.systemcode.manager;

import com.sinosoft.systemcode.dao.SystemCodeInfoDao;
import com.sinosoft.systemcode.dao.SystemCodeNodeInfoDao;
import com.sinosoft.systemcode.domain.SystemCodeInfo;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemCodeInfoManager extends
		EntityManager<SystemCodeInfo, String> {

	@Autowired
	private SystemCodeInfoDao sysciDao;

	@Autowired
	private SystemCodeNodeInfoDao syscniDao;

	@Autowired
	private SystemCodeNodeInfoManager syscniManager;
	private static String qName = "http://service.systemcode.sinosoft.com";

	protected HibernateDao<SystemCodeInfo, String> getEntityDao() {
		return this.sysciDao;
	}

	@Transactional(readOnly = true)
	public SystemCodeInfo getSystemCode(String propertyName, Object value) {
		return (SystemCodeInfo) this.sysciDao.findByUnique(propertyName, value);
	}

	public Criteria buildCrieriaForRegionCode1(Criteria criteria) {
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils
				.getSession().getId());
		if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.isNull("orgCode"));
			disjunction.add(Restrictions.isEmpty("orgCode"));
			disjunction.add(Restrictions.eq("orgCode",
					onlineUser.getRegionCode()));
			criteria.add(disjunction);
		}
		return criteria;
	}

	@Transactional(readOnly = true)
	public String buildCodeTree(String nodeId) {
		List list = null;
		Map values = new HashMap();
		//查询systemCode
		if ("-1".equals(nodeId)) {
			Criteria criteria = this.sysciDao.getSession().createCriteria(SystemCodeInfo.class);

			OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
			if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
				Disjunction dis = Restrictions.disjunction();
				dis.add(Restrictions.eq("orgCode", onlineUser.getRegionCode()));
				dis.add(Restrictions.isNull("orgCode"));
				dis.add(Restrictions.eq("orgCode", ""));
				criteria.add(dis);
			}
			criteria.add(Restrictions.eq("isAllowShow", "Y"));
			list = criteria.list();
		//如果当前展开的节点是systemCode的话
		} else if (findCodeType(nodeId) > 0) {
			values.put("systemCodeId", nodeId);
			OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils
					.getSession().getId());

			if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
				values.put("orgCode", onlineUser.getRegionCode());
				list = this.syscniDao.find("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and systemcode.id=:systemCodeId and (orgCode=:orgCode or orgCode is null) and isAllowShow='Y' order by code asc",values);
			} else {
				list = this.syscniDao.find("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and systemcode.id=:systemCodeId and isAllowShow='Y' order by code asc",values);
			}
		} else {
			Criteria criteria = this.syscniDao.getSession().createCriteria(SystemCodeNodeInfo.class);
			OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
			if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
				criteria.add(Restrictions.or(Restrictions.eq("orgCode", onlineUser.getRegionCode()),Property.forName("orgCode").isNull()));
			}
			criteria.add(Restrictions.eq("isAllowShow", "Y"));
			criteria.add(Restrictions.eq("parentId", nodeId));
			criteria.addOrder(Order.asc("code"));
			list = criteria.list();
		}

		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;

		for (int i = 0; i < list.size(); i++) {
			Object sci;
			if ("-1".equals(nodeId))
				sci = (SystemCodeInfo) list.get(i);
			else {
				sci = (SystemCodeNodeInfo) list.get(i);
			}
			if (!firstItem) {
				treeJson.append(',');
			}

			if ("-1".equals(nodeId)) {
				if ("Y".equals(((SystemCodeInfo) sci).getIsAllowShow())) {
					treeJson.append('{');
					if (firstItem) {
						firstItem = false;
					}

					treeJson.append("id:'");
					treeJson.append(((SystemCodeInfo) sci).getId());

					treeJson.append("',editAble:'");
					treeJson.append(((SystemCodeInfo) sci).getEditAble());

					treeJson.append("',isAllowEdit:'");
					treeJson.append(((SystemCodeInfo) sci).getIsAllowEdit());

					treeJson.append("',isAllowDelete:'");
					treeJson.append(((SystemCodeInfo) sci).getIsAllowDelete());

					treeJson.append("',isAllowChildren:'");
					treeJson.append(((SystemCodeInfo) sci).getIsAllowChildren());

					treeJson.append("',text:'");
					treeJson.append(((SystemCodeInfo) sci).getCodeTypeName());
					treeJson.append("',codeType:'");
					treeJson.append(((SystemCodeInfo) sci).getCodeType());
					treeJson.append("',systemCodeId:'");
					treeJson.append(((SystemCodeInfo) sci).getId());
					treeJson.append("',codeProperty:'");
					treeJson.append(((SystemCodeInfo) sci).getCodeProperty());
					treeJson.append("',type:'");
					treeJson.append("code");

					treeJson.append("',leaf:");
					treeJson.append(checkLeaf((SystemCodeInfo) sci, null));
					treeJson.append(",allowDelete:true}");
				} else {
					firstItem = true;
				}
			} else {
				treeJson.append('{');
				if (firstItem) {
					firstItem = false;
				}

				treeJson.append("id:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getId());

				treeJson.append("',isAllowEdit:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getIsAllowEdit());

				treeJson.append("',isAllowDelete:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getIsAllowDelete());

				treeJson.append("',isAllowChildren:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getIsAllowChildren());

				treeJson.append("',text:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getName());
				treeJson.append("',codeType:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCodeType());
				treeJson.append("',systemCodeId:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getSystemcode()
						.getId());
				treeJson.append("',codeProperty:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCodeProperty());
				treeJson.append("',code:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCode());
				treeJson.append("',type:'");
				treeJson.append("node");
				treeJson.append("',leaf:");
				treeJson.append(checkLeaf(null, (SystemCodeNodeInfo) sci));
				treeJson.append(",parentId:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getParentId() == null ? ""
						: ((SystemCodeNodeInfo) sci).getParentId());
				treeJson.append("'");
				treeJson.append(",allowDelete:true}");
			}

		}

		treeJson.append(']');

		return treeJson.toString();
	}

	@Transactional(readOnly = true)
	public String buildCodeTreeByType(String nodeId, String type,
			boolean singleSelect, boolean available) {
		Map values = new HashMap();
		List list = null;
		StringBuffer hql = new StringBuffer("");
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils
				.getSession().getId());

		if ("-1".equals(nodeId)) {
			if (onlineUser != null) {
				values.put("orgCode", onlineUser.getRegionCode());
				hql.append("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and codeType=:codeType  and  (orgCode=:orgCode or orgCode is null)  ");
			} else {
				hql.append("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and codeType=:codeType");
			}

			if (available) {
				hql.append(" and available=:available");
				values.put("available", "Y");
			}
			hql.append(" order by code ASC");
			values.put("codeType", type);
			list = this.syscniDao.find(hql.toString(), values);
		} else if (findCodeType(nodeId) > 0) {
			if (onlineUser != null) {
				values.put("orgCode", onlineUser.getRegionCode());
				hql.append("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and systemcode.id=:systemCodeId and codeType=:codeType and  (orgCode=:orgCode or orgCode is null or orgCode='')  ");
			} else {
				hql.append("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and systemcode.id=:systemCodeId and codeType=:codeType");
			}

			if (available) {
				hql.append(" and available=:available");
				values.put("available", "Y");
			}
			hql.append(" order by code ASC");
			values.put("codeType", type);
			values.put("systemCodeId", nodeId);
			list = this.syscniDao.find(hql.toString(), values);
		} else {
			Criteria criteria = this.syscniDao.createCriteria(new Criterion[0]);
			criteria.add(Restrictions.eq("parentId", nodeId));
			if (available) {
				criteria.add(Restrictions.eq("available", "Y"));
			}
			if (onlineUser != null) {
				criteria.add(Restrictions.or(
						Restrictions.eq("orgCode", onlineUser.getRegionCode()),
						Restrictions.isNull("orgCode")));
			}
			criteria.addOrder(Order.asc("code"));
			list = criteria.list();
		}

		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;

		for (int i = 0; i < list.size(); i++) {
			SystemCodeNodeInfo sci = (SystemCodeNodeInfo) list.get(i);
			if (!firstItem) {
				treeJson.append(',');
			}
			treeJson.append("{id:'");
			treeJson.append(sci.getId());
			treeJson.append("',text:'");
			treeJson.append(sci.getName());
			treeJson.append("',codeType:'");
			treeJson.append(sci.getCodeType());
			treeJson.append("',systemCodeId:'");
			treeJson.append(sci.getSystemcode().getId());
			treeJson.append("',code:'");
			treeJson.append(sci.getCode());
			treeJson.append("',parentId:'");
			treeJson.append(sci.getParentId());

			treeJson.append("',leaf:");
			treeJson.append(checkLeaf(null, sci));
			treeJson.append(",allowDelete:true");
			if (!singleSelect) {
				treeJson.append(",checked:false");
			}
			treeJson.append('}');
			if (firstItem) {
				firstItem = false;
			}
		}
		treeJson.append(']');
		return treeJson.toString();
	}

	@Transactional(readOnly = true)
	public String buildCodeTreeByCodeType(String nodeId, String type,
			boolean singleSelect) {
		Map values = new HashMap();
		List list = null;

		if ("-1".equals(nodeId)) {
			values.put("codeType", type);
			list = this.sysciDao.find(
					"FROM SystemCodeInfo WHERE codeType=:codeType", values);
		} else if (findCodeType(nodeId) > 0) {
			values.put("systemCodeId", nodeId);
			list = this.syscniDao
					.find("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and systemcode.id=:systemCodeId",
							values);
		} else {
			list = this.syscniDao.findBy("parentId", nodeId);
		}

		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;

		for (int i = 0; i < list.size(); i++) {
			Object sci;
			if ("-1".equals(nodeId))
				sci = (SystemCodeInfo) list.get(i);
			else {
				sci = (SystemCodeNodeInfo) list.get(i);
			}
			if (!firstItem) {
				treeJson.append(',');
			}
			treeJson.append('{');
			if ("-1".equals(nodeId)) {
				treeJson.append("id:'");
				treeJson.append(((SystemCodeInfo) sci).getId());
				treeJson.append("',text:'");
				treeJson.append(((SystemCodeInfo) sci).getCodeTypeName());
				treeJson.append("',codeType:'");
				treeJson.append(((SystemCodeInfo) sci).getCodeType());
				treeJson.append("',systemCodeId:'");
				treeJson.append(((SystemCodeInfo) sci).getId());

				treeJson.append("',leaf:");
				treeJson.append(checkLeaf((SystemCodeInfo) sci, null));
			} else {
				treeJson.append("id:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getId());
				treeJson.append("',text:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getName());
				treeJson.append("',codeType:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCodeType());
				treeJson.append("',systemCodeId:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getSystemcode()
						.getId());
				treeJson.append("',code:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCode());
				treeJson.append("',parentId:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getParentId());

				treeJson.append("',leaf:");
				treeJson.append(checkLeaf(null, (SystemCodeNodeInfo) sci));
			}
			treeJson.append(",allowDelete:true");
			if (!singleSelect) {
				treeJson.append(",checked:false");
			}
			treeJson.append('}');
			if (firstItem) {
				firstItem = false;
			}
		}
		treeJson.append(']');
		return treeJson.toString();
	}

	@Transactional(readOnly = true)
	public String buildCodeTreeByTypeName(String nodeId, String[] typeName,
			boolean singleSelect, boolean isString) {
		Map values = new HashMap();
		List list = new ArrayList();

		if ("-1".equals(nodeId)) {
			if (isString) {
				for (String e : typeName) {
					values.put("codeTypeName", e);
					List vlist = this.sysciDao
							.find("FROM SystemCodeInfo WHERE codeTypeName =:codeTypeName",
									values);
					list.addAll(vlist);
				}
			} else {
				values.put("codeTypeName", "%" + typeName[0] + "%");
				list = this.sysciDao
						.find("FROM SystemCodeInfo WHERE codeTypeName like:codeTypeName",
								values);
			}
		} else if (findCodeType(nodeId) > 0) {
			values.put("systemCodeId", nodeId);
			list = this.syscniDao
					.find("FROM SystemCodeNodeInfo WHERE (parentId is null or parentId='') and systemcode.id=:systemCodeId",
							values);
		} else {
			list = this.syscniDao.findBy("parentId", nodeId);
		}

		StringBuffer treeJson = new StringBuffer("[");
		boolean firstItem = true;

		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			Object sci;
			if ("-1".equals(nodeId))
				sci = (SystemCodeInfo) list.get(i);
			else {
				sci = (SystemCodeNodeInfo) list.get(i);
			}
			if (!firstItem) {
				treeJson.append(',');
			}
			if ("-1".equals(nodeId)) {
				if ("Y".equals(((SystemCodeInfo) sci).getIsAllowShow())) {
					treeJson.append('{');
					treeJson.append("id:'");
					treeJson.append(((SystemCodeInfo) sci).getId());
					treeJson.append("',text:'");
					treeJson.append(((SystemCodeInfo) sci).getCodeTypeName());
					treeJson.append("',codeType:'");
					treeJson.append(((SystemCodeInfo) sci).getCodeType());
					treeJson.append("',systemCodeId:'");
					treeJson.append(((SystemCodeInfo) sci).getId());

					treeJson.append("',leaf:");
					treeJson.append(checkLeaf((SystemCodeInfo) sci, null));
					flag = true;
				}
			} else {
				flag = true;
				treeJson.append('{');
				treeJson.append("id:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getId());
				treeJson.append("',text:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getName());
				treeJson.append("',codeType:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCodeType());
				treeJson.append("',systemCodeId:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getSystemcode()
						.getId());
				treeJson.append("',code:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getCode());
				treeJson.append("',parentId:'");
				treeJson.append(((SystemCodeNodeInfo) sci).getParentId());

				treeJson.append("',leaf:");
				treeJson.append(checkLeaf(null, (SystemCodeNodeInfo) sci));
			}
			if (flag) {
				treeJson.append(",allowDelete:true");
				if (!singleSelect) {
					treeJson.append(",checked:false");
				}
				treeJson.append('}');
				if (firstItem) {
					firstItem = false;
				}
			}
		}

		treeJson.append(']');
		return (String) (String) treeJson.toString();
	}

	@Transactional(readOnly = true)
	public int findCodeType(String codeId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("id", codeId));
		return this.sysciDao.getCount(criteria).intValue();
	}

	@Transactional(readOnly = true)
	public int codeType(String filterValue) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("codeType", filterValue));
		return this.sysciDao.getCount(criteria).intValue();
	}

	public boolean sendCode(String sendUrl, String node) throws AxisFault {
		boolean result = true;

		StringBuffer xml = new StringBuffer("");
		List nodeList;
		SystemCodeNodeInfo nodeInfo;
		if ("-1".equals(node)) {
			List list = this.sysciDao.getAll();
			for (int i = 0; i < list.size(); i++) {
				SystemCodeInfo sci = (SystemCodeInfo) list.get(i);
				xml.append(appendCodeType(sci));
				Set child = sci.getSystemCodeNodeInfo();
				nodeList = orderCodeNode(child);
				for (Iterator localIterator = nodeList.iterator(); localIterator
						.hasNext();) {
					nodeInfo = (SystemCodeNodeInfo) localIterator.next();
					xml.append(appendChilds(nodeInfo));
				}
				xml.append("</nodes>");
				xml.append("</CodeType>");
				result = axis2Send(sendUrl, xml.toString());
			}
		} else if (findCodeType(node) > 0) {
			 SystemCodeInfo sci = (SystemCodeInfo)sysciDao.get(node);
             xml.append(appendCodeType(sci));
             Set child = sci.getSystemCodeNodeInfo();
             nodeList = orderCodeNode(child);
             for(Iterator iterator = nodeList.iterator(); iterator.hasNext(); xml.append(appendChilds(nodeInfo)))
                 nodeInfo = (SystemCodeNodeInfo)iterator.next();

             xml.append("</nodes>");
             xml.append("</CodeType>");
             result = axis2Send(sendUrl, xml.toString());
		} else {
			SystemCodeNodeInfo scni = (SystemCodeNodeInfo)syscniDao.get(node);
            SystemCodeInfo sci = scni.getSystemcode();
            xml.append(appendCodeType(sci));
            xml.append(appendChilds(scni));
            Set child = scni.getChildNodes();
            nodeList = orderCodeNode(child);
            for(Iterator iterator1 = nodeList.iterator(); iterator1.hasNext(); xml.append(appendChilds(nodeInfo)))
                nodeInfo = (SystemCodeNodeInfo)iterator1.next();

            xml.append("</nodes>");
            xml.append("</CodeType>");
            result = axis2Send(sendUrl, xml.toString());
		}
		return result;
	}

	private StringBuffer appendCodeType(SystemCodeInfo sci) {
		StringBuffer xml = new StringBuffer();
		xml.append("<CodeType><codeTypeValue>" + sci.getCodeType()
				+ "</codeTypeValue>");
		xml.append("<codeTypeName>" + sci.getCodeTypeName() + "</codeTypeName>");
		xml.append("<editAble>" + sci.getEditAble() + "</editAble>");
		xml.append("<codeProperty>" + sci.getCodeProperty() + "</codeProperty>");
		xml.append("<nodes>");
		return xml;
	}

	private StringBuffer appendChilds(SystemCodeNodeInfo scni) {
		StringBuffer xml = new StringBuffer();

		xml.append("<codeNode>");
		xml.append("<id>" + scni.getId() + "</id>");
		xml.append("<code>" + scni.getCode() + "</code>");
		xml.append("<name>" + scni.getName() + "</name>");
		xml.append("<parentCode>");

		if (scni.getParentId() != null) {
			SystemCodeNodeInfo parent = (SystemCodeNodeInfo) this.syscniDao
					.findByUnique("id", scni.getParentId());
			if (parent != null)
				xml.append(parent.getCode());
		}
		xml.append("</parentCode>");
		xml.append("<editAble>" + scni.getEditAble() + "</editAble>");
		xml.append("<sendAble>" + scni.getSendAble() + "</sendAble>");
		xml.append("<addSubAble>" + scni.getAddSubAble() + "</addSubAble>");
		xml.append("<codeProperty>" + scni.getCodeProperty()
				+ "</codeProperty>");
		xml.append("<renderAble>" + scni.getRenderAble() + "</renderAble>");
		xml.append("<sequence>" + scni.getSequence() + "</sequence>");
		xml.append("</codeNode>");
		Set set = scni.getChildNodes();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			xml.append(appendChilds((SystemCodeNodeInfo) iterator.next()));
		}
		return xml;
	}

	private boolean checkLeaf(SystemCodeInfo sci, SystemCodeNodeInfo scni) {
		boolean child = false;
		if (sci == null) {
			int size = scni.getChildNodes().size();
			if (size == 0)
				child = true;
		} else {
			int count = this.syscniDao.getNodeCount(sci.getId());
			if (count == 0) {
				child = true;
			}
		}
		return child;
	}

	@Transactional(readOnly = true)
	public SystemCodeInfo findUnique(String propertyName, String value) {
		return (SystemCodeInfo) this.sysciDao.findByUnique(propertyName, value);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(SystemCodeInfo model, String orgCode) {
		SystemCodeInfo paramModel = model;
		if ((paramModel.getId() == null) || ("".equals(paramModel.getId()))) {
			paramModel.setOrgCode(orgCode);
			paramModel.setIsAllowShow("Y");
			paramModel.setIsAllowChildren("Y");
			paramModel.setIsAllowDelete("Y");
			paramModel.setIsAllowEdit(paramModel.getEditAble());
		}

		paramModel.setOperateDate(new Timestamp(System.currentTimeMillis()));
		this.sysciDao.save(paramModel);
		if ((paramModel.getId() != null) && (!"".equals(paramModel.getId()))) {
			paramModel = (SystemCodeInfo) this.sysciDao.get(paramModel.getId());
		}
		Set set = paramModel.getSystemCodeNodeInfo();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			SystemCodeNodeInfo scni = (SystemCodeNodeInfo) iterator.next();
			scni.setCodeType(paramModel.getCodeType());

			scni.setOperateDate(new Timestamp(System.currentTimeMillis()));
			this.syscniDao.save(scni);
		}
	}

	private boolean axis2Send(String sendUrl, String xml) throws AxisFault {
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();

		options.setTimeOutInMilliSeconds(60000L);
		options.setProperty("CONNECTION_TIMEOUT", Integer.valueOf("48000000"));
		options.setProperty("__CHUNKED__", "false");

		EndpointReference targetEPR = new EndpointReference(sendUrl);
		options.setTo(targetEPR);

		QName opGetInfor = new QName(qName, "save");

		Object[] opGetArgs = { this.syscniManager.getOrgCode(), xml };

		Class[] returnTypes = { Boolean.class };
		Object[] response = serviceClient.invokeBlocking(opGetInfor, opGetArgs,
				returnTypes);
		serviceClient.cleanupTransport();

		return ((Boolean) response[0]).booleanValue();
	}

	private List<SystemCodeNodeInfo> orderCodeNode(Set<SystemCodeNodeInfo> node) {
		Iterator ite = node.iterator();
		List nodeList = new ArrayList();
		List childNodeList = new ArrayList();
		while (ite.hasNext()) {
			SystemCodeNodeInfo code = (SystemCodeNodeInfo) ite.next();
			if (code.getParentId() == null)
				nodeList.add(code);
			else {
				childNodeList.add(code);
			}
		}
		nodeList.addAll(childNodeList);
		return nodeList;
	}
}
