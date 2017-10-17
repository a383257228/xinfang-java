package com.sinosoft.xf.system.logon.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.authority.manager.AuthorityMenuManager;
import com.sinosoft.log.common.LoginArgs;
import com.sinosoft.log.domain.OperationLog;
import com.sinosoft.log.manager.LoginLogManager;
import com.sinosoft.log.manager.OperationLogManager;
import com.sinosoft.organization.domain.OrganPersonRelationInfo;
import com.sinosoft.organization.domain.OrganizationInfo;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.organization.manager.PersonInfoManager;
import com.sinosoft.xf.constants.BusinessConstants;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.LeaderFlag;
import com.sinosoft.authority.manager.SystemAuthorityInfoManager;
import com.sinosoft.xf.system.logon.manager.LogonManager;
import com.sinosoft.xf.system.logon.manager.PetitionOrganInfoManager;
import com.sinosoft.xf.util.PropertiesUtils;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 系统登录action
 * @author sunzhe
 * @createDate 2011-08-30
 */
@Namespace("/logon")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack")})
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "logon.action", type = "redirect")
})
public class LogonAction extends CrudExtActionSupport<PersonInfo, String> {
	private static final long serialVersionUID = 1L;
	//注入用户信息业务层
	@Autowired
	PersonInfoManager personInfoManager;
	//登录人员实体类对象
	PersonInfo entity = new PersonInfo();;
	//操作日志service用于手动记录操作日志
	@Autowired
	LoginLogManager loginLogManager;
	
	//注入登录信息manager
	@Autowired
	LogonManager logonManager;
	@Autowired
	AuthorityMenuManager authorityMenuManager;
	@Autowired
	OperationLogManager operationLogManager;
	private String regionCode;
	@Autowired
	SystemAuthorityInfoManager systemAuthorityInfoManager;	
	@Autowired
	PetitionOrganInfoManager petitionOrganInfoManager;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户userCName
	 */
	private String userCName;

	/**
	 * 普通登录处理类
	 * @date 2011-08-31
	 * @author sz
	 * 被哪些方法调用：
	 * 1、logon.jsp		登陆文件
	 * @throws Exception 
	 */
	public void logonStandard() {
		try {
			BusinessConstants.getInstance();//初始化业务口径时间设置,时间不相同，每次登陆都需要设置
			Map<String, Object> map = logonManager.getPersonInfoMapByPersonEnameAndRegionCode(entity.getUserEname(), getRegionCode());
			if(map==null){
				Struts2Utils.getResponse().getWriter().print("用户名或密码错误！");
				return;
			}
			PersonInfo personInfo = (PersonInfo) map.get("personInfo");
			String pwd = (String) map.get("pwd");
			//对于用户名密码校验，要先校验用户是否有效
			if (!personInfo.getInvalidFlag().equals("1")) {
				Struts2Utils.getResponse().getWriter().print("该用户已经删除或设置成无效！");
				return;
			} 
			//用户名正确，但密码错误
			int loginErrorNum = loginLogManager.queryNowDateLogionErrorNumber(personInfo.getId());
			//先校验密码是否正确
			if(!validateLogin(entity,pwd)){
				saveLoginLog(personInfo,"2");
				//密码校验失败时，第五次及五次以后要提示用户，账户已被锁定，需解锁后再登录
				if(loginErrorNum >= 5){
					Struts2Utils.getResponse().getWriter().print("此用户已被锁定,请联系安全管理员解除锁定！");
					return;
				}
				Struts2Utils.getResponse().getWriter().print("用户名或密码错误！");
				return;
			}else{
				//密码校验成功时，要确定一下用户是否已锁定
				if(loginErrorNum >= 5){
					Struts2Utils.getResponse().getWriter().print("此用户已被锁定,请联系安全管理员解除锁定！");
					return;
				}
			}
			HttpSession session = Struts2Utils.getSession();
			String codes = systemAuthorityInfoManager.getClueSourceCodes();
			session.setAttribute("petitionSourceCodesForClue", codes);//“问题线索移送的信访来源”类型字符串放入session
			//用户正常登录，添加进在线用户供其他模块调用
			OnlineUser onlineUser = AppUtils.getOnlineUser(session.getId());
			if(onlineUser!=null){
				AppUtils.removeOnlineUser(session.getId());
			}
			//2015-10-26 liub 添加downIssueRegionCode字段方便所属区域校验
			String downIssueRegionCode=petitionOrganInfoManager.getDownIssueRegionCodeByRegionAuthority(personInfo.getRegionCode());
			personInfo.setDownIssueRegionCode(downIssueRegionCode);
			AppUtils.addOnlineUser(session.getId(), personInfo);
			//没有用户关系，说明该用户已经被删除
			OrganPersonRelationInfo opri = (OrganPersonRelationInfo) map.get("organPersonRelationInfo");
			OrganizationInfo organ = opri.getOrganizationInfo();
			
			String result = geneLoginAttribute(organ,personInfo,"");
			//loginLogManager.addLoginLog(new LoginArgs(personInfo.getId(),personInfo.getUserCname(),personInfo.getRegionCode(),personInfo.getRegionName(),"0"));
			//获得版本信息
			getVersion();
			Struts2Utils.getResponse().getWriter().print(result);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 获得版本号
	 * @throws IOException 
	 */
	private void getVersion() throws IOException{
		HttpSession session = Struts2Utils.getSession();
		String src = PropertiesUtils.getPropertiesValue("version", "VersionID");
		StringBuffer version = new StringBuffer("纪检监察信访信息管理系统 CMWIMS&copy; V.");
		if(src!=null&&!"".equals(src)){
			byte[] b = new byte[src.length()/2];
			for(int i=0;i<src.length();i+=2){
				String s = src.substring(i,i+2);
				b[i/2] = hexToByte(s);
			}
			version.append(new String(b,"Utf-8"));
		}
		session.setAttribute("version", version.toString());
	}
	/**
	 * 登录日志保存
	 * @throws Exception 
	 */
	public void saveLoginLog(PersonInfo personInfo,String logionStatus) throws Exception {
		loginLogManager.addLoginLog(new LoginArgs(personInfo.getId()
				,personInfo.getUserCname(),personInfo.getRegionCode(),personInfo.getRegionName()));
	}
	/**
	 * 验证密码是否正确。正确为true，反之false
	 * @param uiInfo 前台输入的person信息对象
	 * @param realPwd 密码字符串
	 * @return
	 * @author garview
	 * 2015年11月24日 下午4:53:10
	 */
	private boolean validateLogin(PersonInfo uiInfo, String realPwd){
		return uiInfo.getUserPwd().equals(DigestUtils.md5Hex(realPwd));
	}
	
	/**
	 * 登录校验成功后调用此方法，该方法会把用户在整个session当中可能用到的信息放进去，
	 * 并返回result（result相当于跳转标记，不同result跳转到不同的地方）
	 * @author hjh
	 * @param organ
	 * @param personInfo
	 * @return result   "true"->"/jubao/petition/petition.jsp"，
	 * 					"true5"->"/jubao/platform/wld/wldplatform.jsp"，
	 * 					"true52"->"/jubao/query/simple-query!characterStatistics.action"
	 * 2015-1-5上午11:40:22
	 * @throws Exception 
	 */
	public String geneLoginAttribute(OrganizationInfo organ,PersonInfo personInfo,String interfaceQuerySHJW) throws Exception{
		if(organ==null || personInfo==null){
			throw new RuntimeException("登录失败，personInfo或organ为null，具体请联系系统管理员。");
		}
		HttpSession session = Struts2Utils.getSession();
		session.removeAttribute("authorityMenuMap");
		session.removeAttribute("menuStr");
		session.removeAttribute("personInfo");
		session.removeAttribute("roleStr");
		session.removeAttribute("pendingNotCompletedSize");
		session.removeAttribute("overdueNotCompletedSize");
		session.removeAttribute("alarmActiveSize");
		session.removeAttribute("leaderInfo");
		session.removeAttribute("interfaceQuerySHJW");
		
		//将登陆人信息放到session中
		session.setAttribute("personInfo", personInfo);
		session.setAttribute("deptCode", organ.getOrgCode());
		session.setAttribute("deptName", organ.getOrgCname());
		session.setAttribute("organizationInfoOid", organ.getId());
		session.setAttribute("organizationInfoCname", organ.getOrgCname());
		session.setAttribute("curRegionCode", personInfo.getRegionCode());//当前区域编码
		session.setAttribute("curRegionName", personInfo.getRegionName());//当前区域编码名称
		session.setAttribute("curRegionId", organ.getId());//当前区域组织机构id
		session.setAttribute("interfaceQuerySHJW",interfaceQuerySHJW);//标识着是从上海纪委平台请求过来的
		String result = Constants.TRUE;
		String roleStr = logonManager.geneRoleStr(personInfo);
		session.setAttribute("roleStr", roleStr);
		return result;
	}
	
	private void clearSession(HttpSession session){
		session.removeAttribute("authorityMenuMap");
		session.removeAttribute("menuStr");
		session.removeAttribute("personInfo");
		session.removeAttribute("UserCname");
		session.removeAttribute("UserEname");
		session.removeAttribute("organizationInfoOid");
		session.removeAttribute("organizationInfoCname");
		session.removeAttribute("curRegionCode");
		session.removeAttribute("curRegionName");
		session.removeAttribute("curRegionId");
	}
	
	/**
	 * 
	* @param userId
	* @description 
	* @version 1.2.0
	* @author lixl
	 * @throws IOException 
	* @update 2014-11-24 上午10:40:40
	 */
	public void unLockUser() throws IOException{
		 loginLogManager.unLockLoginInfo(userId);
		 OperationLog log = new OperationLog();
		 OnlineUser onLineUser  = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
		 log.setOperationDesc(onLineUser.getUserCName()+"("+onLineUser.getUserId()+")" +"对用户："+userCName+"("+userId+") 进行解锁操作");
		 log.setOperationResultName("成功");
		 log.setOperationTime(new Timestamp(System.currentTimeMillis()));
		 operationLogManager.saveLog(log);
		 Struts2Utils.getResponse().getWriter().print(true);
	}
	
	/**
	 * 退出系统,清空在线用户,返回登录页
	 * @return
	 * @throws Exception 
	 */
	public void logout() throws Exception{
		HttpSession session = Struts2Utils.getSession();
		AppUtils.removeOnlineUser(session.getId());
		clearSession(session);
		loginLogManager.logout();
		Struts2Utils.getSession().invalidate();
		Struts2Utils.getResponse().getWriter().print(Constants.TRUE);
	}
	/**
	 * 重写父类方法
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null&& !"".equals(id)) {
			entity = personInfoManager.get(id);
		} else {
			entity = new PersonInfo();
		}
	}
	/**
	 * 对于版本信息的参数转译
	 * @param s
	 * @return
	 */
	private byte hexToByte(String s) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		byte lo = 0;
		byte hi = 0;
		for (int i = 0; i < hexDigit.length; i++) {
			if (hexDigit[i] == s.charAt(0))
				hi = (byte) ((byte) i * 16);
			if (hexDigit[i] == s.charAt(1))
				lo = (byte) i;
		}
		return (byte) (lo + hi);
	}
	/**
	 * 重写父类的方法
	 * @return 人员信息Manager
	 */
	@Override
	protected EntityManager<PersonInfo, String> getEntityManager() {
		return personInfoManager;
	}
	/**
	 * 获得处理状态实体
	 * @return 处理状态实体
	 */
	public PersonInfo getModel() {
		return entity;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCName() {
		return userCName;
	}

	public void setUserCName(String userCName) {
		this.userCName = userCName;
	} 
	
}
