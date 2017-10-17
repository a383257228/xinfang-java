package com.sinosoft.authority.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.manager.SystemAuthorityInfoManager;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoft.xf.util.CodeSwitchUtil;
import com.sinosoft.xf.util.AuthorityUtil;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 项目名称：中纪委信访2期 类名称：SystemAuthorityInfoAction 类描述：系统权限信息action层 创建人：sunzhe
 * 创建时间：2012-01-28
 */
@Namespace("/system")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "system-authority-info.action", type = "redirect") })
public class SystemAuthorityInfoAction extends CrudExtActionSupport<SystemCodeNodeInfo, String> {
	private static final long serialVersionUID = 1L;
	// 注入系统代码权限处理业务层
	@Autowired
	SystemAuthorityInfoManager systemAuthorityInfoManager;

	@Override
	protected EntityManager<SystemCodeNodeInfo, String> getEntityManager() {
		return systemAuthorityInfoManager;
	}

	/**
	 * 
	 */
	private SystemCodeNodeInfo entity = new SystemCodeNodeInfo();

	private String exceptCode;
	private String node; // 树节点ＩＤ

	private String codeType; // 代码类别

	private String singleSelect; // 是否单选

	@Override
	protected void prepareModel() throws Exception {
//		if (id != null && !"".equals(id)) {
//			entity = systemAuthorityInfoManager.get(id);
//		} else {
//			entity = new SystemCodeNodeInfo();
//		}
	}

	public SystemCodeNodeInfo getModel() {
		return entity;
	}

	/**
	 * @return the entity
	 */
	public SystemCodeNodeInfo getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(SystemCodeNodeInfo entity) {
		this.entity = entity;
	}

	/**
	 * 封装渲染代码列表所需的参数
	 * 
	 * @return String[] 封装渲染代码列表所需的参数
	 * @author sunzhe
	 */
	public String[] getExcludesForAll() {
		return new String[] { "hibernateLazyInitializer", "class", "root", "parent", "users", "childrens", "handler", "systemCodeNodeInfo",
				"addSubAble", "allowChildren", "allowDelete", "allowEdit", "childNodes", "children", "codeProperty", "draggable", "editAble",
				"iconCls", "leaf", "operateDate", "orgCode", "parentId", "parentNode", "qtip", "renderAble", "sendAble", "sequence", "operator",
				"codeType", "theSort", "text", "systemcode" };
	}

	/**
	 * 获取代码类别对应列表
	 * 
	 * @throws Exception
	 */
	public void getSystemAuthorityCodeList() throws Exception {
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		List<SystemCodeNodeInfo> systemCodeList = systemAuthorityInfoManager.getSystemAuthorityCodeNode("codeType", this.filterValue,
				this.exceptCode, onlineUser);
		JsonUtils.write(systemCodeList, Struts2Utils.getResponse().getWriter(), getExcludesForAll(), getDatePattern());
	}
	/**
	 * 获取代码类别对应列表
	 * 
	 * @throws Exception
	 */
	public void getSystemAuthorityCodeListToAvailable() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String,Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		OnlineUser onlineUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		List<SystemCodeNodeInfo> systemCodeList = systemAuthorityInfoManager.getSystemAuthorityCodeNodeToAvailable("codeType", this.filterValue,
				this.exceptCode, onlineUser,map);
		JsonUtils.write(systemCodeList, Struts2Utils.getResponse().getWriter(), getExcludesForAll(), getDatePattern());
	}

	/**
	 * 根据代码类别，得到代码信息，用于树状显示 只展示下级节点
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void getSystemCodeListByType() {
		try {
			String treejson = "";
			if("ZHCXWTXZ".equals(codeType)){
				codeType = "WTXZ";
				//2016-01-22 liub新问题类别中已经去除业务范围外,所以this.exceptCode = "02,03,04",exceptCode的值已经在前台传了这里不需要再次赋值
//				this.exceptCode = "02,03";
			}
			if ("WTXZ".equals(codeType)) {
				treejson = systemAuthorityInfoManager.buildIssueTypeCodeTrueOnce(node, codeType, Boolean.valueOf(singleSelect), this.exceptCode);
			} else {
				treejson = systemAuthorityInfoManager.buildCodeTreeByType(node, codeType, Boolean.valueOf(singleSelect), this.exceptCode);
			}
			Struts2Utils.getResponse().getWriter().print(treejson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据代码类别，并且通过权限过滤，得到信访来源代码信息，用于树状显示一次全部查出
	 * （现有的数据权限管理的字段： 行政级别、）
	 * @throws Exception
	 */
	public void getSystemCodeListByTypeAndAuthority() {
		try {
			String treejson = "";
			treejson = systemAuthorityInfoManager.buildCodeTreeByTypeAndAuthority(node, codeType, Boolean.valueOf(singleSelect), this.exceptCode);
			Struts2Utils.getResponse().getWriter().print(treejson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据codeType缓存代码俩表（不是树形的下拉框）
	 * filterValue---sysCodeType
	 */
	public void listNoAuthoritySystemCodeByType() {
		try {
			Long a = System.currentTimeMillis();
			List<SystemCodeNodeInfo> list =  AuthorityUtil.codeCacheMap.get(filterValue+exceptCode);
			if(list==null||list.isEmpty()){
				list = CodeSwitchUtil.getCodeNodeList(filterValue,exceptCode);
				AuthorityUtil.codeCacheMap.put(filterValue+exceptCode,list);
			}
			System.out.println( System.currentTimeMillis()-a);
			StringBuffer json=new StringBuffer("[");
			for (SystemCodeNodeInfo systemCodeNodeInfo : list) {
				json.append("{");
				json.append("\"code\":\""+systemCodeNodeInfo.getCode()+"\",");
				json.append("\"name\":\""+systemCodeNodeInfo.getName()+"\"");
				json.append("},");
			}
			json.deleteCharAt(json.length()-1);
			json.append("]");
			Long b = System.currentTimeMillis();
			Struts2Utils.getResponse().getWriter().print(json.toString());
			System.out.println( System.currentTimeMillis()-b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getPetitionLetterSourceTree() throws IOException {
		String treejson = systemAuthorityInfoManager.getPetitionLetterSourceTree();
		Struts2Utils.getResponse().getWriter().print(treejson);
	}

	/**
	 * @return the exceptCode
	 */
	public String getExceptCode() {
		return exceptCode;
	}

	/**
	 * @param exceptCode
	 *            the exceptCode to set
	 */
	public void setExceptCode(String exceptCode) {
		this.exceptCode = exceptCode;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(String singleSelect) {
		this.singleSelect = singleSelect;
	}
}
