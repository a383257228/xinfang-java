
package com.sinosoft.xf.util.filter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.authority.dao.AuthorityMenuDao;
import com.sinosoft.log.common.LogInitParams;
import com.sinosoft.log.common.LoginArgs;
import com.sinosoft.log.common.OnlineManager;
import com.sinosoft.log.dao.LoginLogDao;
import com.sinosoft.log.domain.LoginLog;
import com.sinosoft.log.exception.LogException;
import com.sinosoft.log.manager.LoginLogManager;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.RoleStr;
import com.sinosoft.xf.petition.domainutil.ChartPlatCache;
import com.sinosoft.xf.util.PropertiesUtils;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.date.DateHelper;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * CAS 自动根据单点登录系统的信息设置本系统的用户信息
 * 
 * @author lirb
 * 
 */
public class AutoSetUserAdapterFilter implements Filter {
	
	
	
	/**
	 * 是否获取最新数据标志
	 */
	private boolean currData;
	
	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { ".js", ".css",
			".jpg", ".gif" };

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Default constructor.
	 */
	public AutoSetUserAdapterFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * 过滤逻辑：首先判断单点登录的账户是否已经存在本系统中，
	 * 如果不存在使用用户查询接口查询出用户对象并以session.id设置在onlinuser对象中
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {/*
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		//HttpServletResponse httpResponse = (HttpServletResponse)response;
		String path = httpRequest.getServletPath();
		for (String suffix : DEFAULT_EXCLUDE_SUFFIXS) {
			if (path.endsWith(suffix)) {
				chain.doFilter(request, response);
				return;
			}
		}

		// _const_cas_assertion_是CAS中存放登录用户名的session标志
		Object object =httpRequest.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (object != null) {
			Assertion assertion = (Assertion) object;
			String userCode = assertion.getPrincipal().getName();
			OnlineUser user = AppUtils.getOnlineUser(httpRequest.getSession().getId());
			// 第一次登录系统
			if (user == null) {
				LogonManager logonManager = AppUtils.getBean("logonManager");
				//Map<String, Object> map = logonManager.getPersonInfoMapByPersonEnameAndRegionCode(loginName, "510000000000");
				OrganPersonRelationDao organPersonRelationDao = AppUtils.getBean("organPersonRelationDao");
				Criteria criteria = organPersonRelationDao.createCriteria();
				criteria.createAlias("personInfo", "personInfo",criteria.LEFT_JOIN);
				criteria.createAlias("organizationInfo", "organizationInfo",criteria.LEFT_JOIN);
				criteria.add(Restrictions.eq("personInfo.userCode",userCode));
				//criteria.add(Restrictions.eq("personInfo.regionCode","510000000000"));
				//数据库中返回的对象数组
				List<OrganPersonRelationInfo> list = criteria.list();
				if(list.size()>0){
					PersonInfo personInfo = list.get(0).getPersonInfo();
					//判断当前用户所在机构 是否 开通信访举报子系统
					CentralizedDeploymentInfoDao centralizedDeploymentInfoDao = AppUtils.getBean("centralizedDeploymentInfoDao");
					criteria = centralizedDeploymentInfoDao.createCriteria();
					criteria.add(Restrictions.eq("regionCode", personInfo.getRegionCode()));
					List<CentralizedDeploymentInfo> listDeploymentInfo = criteria.list();
					HttpSession session = httpRequest.getSession();
					if (listDeploymentInfo.size()>0) {
						if ("1".equals(personInfo.getInvalidFlag())) {//有效用户
							try {
								BusinessConstants.getInstance();//初始化业务口径时间设置,时间不相同，每次登陆都需要设置
								Map<String, Object> map = logonManager.getPersonInfoMapByPersonEnameAndRegionCode(personInfo.getUserEname(), personInfo.getRegionCode());
								//用户正常登录，添加进在线用户供其他模块调用
								OnlineUser onlineUser = AppUtils.getOnlineUser(session.getId());
								if(onlineUser!=null){
									AppUtils.removeOnlineUser(session.getId());
								}
								//添加downIssueRegionCode字段方便所属区域校验
								PetitionOrganInfoManager petitionOrganInfoManager = AppUtils.getBean("petitionOrganInfoManager");
								String downIssueRegionCode = petitionOrganInfoManager.getDownIssueRegionCodeByRegionAuthority(personInfo.getRegionCode());
								personInfo.setDownIssueRegionCode(downIssueRegionCode);
								AppUtils.addOnlineUser(session.getId(), personInfo);
								//没有用户关系，说明该用户已经被删除
								OrganPersonRelationInfo opri = list.get(0);
								OrganizationInfo organ = opri.getOrganizationInfo();
								
								if(organ==null || personInfo==null){
									throw new RuntimeException("登录失败，personInfo或organ为null，具体请联系系统管理员。");
								}
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
								String interfaceQuerySHJW="";
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
								int pendingNotCompletedSize = 0;//延期未结
								int overdueNotCompletedSize = 0;//逾期未结
								int alarmActiveSize = 0;//预警
								String roleStr = logonManager.geneRoleStr(personInfo);
								if(personInfo.getLeaderFlag().equals(LeaderFlag.LEADER_MAIN.toString())){
									result += personInfo.getLeaderFlag();
									if(!StringUtils.isBlank(personInfo.getPlatformHomePage()))
										result += personInfo.getPlatformHomePage();//wld主页设置
								}
								session.setAttribute("roleStr", roleStr);
								//不是上海的用户跳入到系统，不走统计页面
								if(!Constants.SH_REGION_CDOE.equals(personInfo.getRegionCode())){
									result = Constants.TRUE;
								}
								if(RoleStr.LEADER_MAIN.toString().equals(roleStr)){
//									if(!"2".equals(personInfo.getPlatformHomePage()))//当主页标记不是2（文字统计），不执行以下方法，以免重复统计降低效率
									getChartCount();
								}
								AnalysisChartDao analysisChartDao = AppUtils.getBean("analysisChartDao");	
								pendingNotCompletedSize = analysisChartDao.pendingNotComplete(roleStr,personInfo,request);//延期未结
								overdueNotCompletedSize = analysisChartDao.overdueNotComplete(roleStr,personInfo,request);//逾期未结
								alarmActiveSize = analysisChartDao.alarmActived(roleStr,personInfo,request);//预警
								session.setAttribute("pendingNotCompletedSize",pendingNotCompletedSize);
								session.setAttribute("overdueNotCompletedSize",overdueNotCompletedSize);
								session.setAttribute("alarmActiveSize",alarmActiveSize);
								//获得版本信息
								getVersion(session);
							} catch(Exception e){
								e.printStackTrace();
							}
							chain.doFilter(request, response);
							try {
								addLoginLogs(new LoginArgs(personInfo.getId()
										,personInfo.getUserCname(),personInfo.getRegionCode(),personInfo.getRegionName(),"0"),httpRequest);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{//用户无效
							response.setContentType("text/JavaScript;charset=utf-8");
							response.getWriter().print("该用户已失效，请与管理员确认!");
						}
					}else{
						response.setContentType("text/JavaScript;charset=utf-8");
						response.getWriter().print("当前用户所在机构尚未部署开通信访举报子系统，请与系统管理员联系！");
					}
				}else{
					response.setContentType("text/JavaScript;charset=utf-8");
					response.getWriter().print("信访系统不存在该用户，请去平台进行同步!");
				}
			}else{
				HttpSession session = httpRequest.getSession();
				PersonInfo person = (PersonInfo) session.getAttribute("personInfo");
				//如果在同一个页面切换用户，session不变，但是用户已改变，需要重新加载用户信息
				if(person!=null){
					String code = person.getUserCode();
					if(!code.equals(userCode)){
						LogonManager logonManager = AppUtils.getBean("logonManager");
						OrganPersonRelationDao organPersonRelationDao = AppUtils.getBean("organPersonRelationDao");
						Criteria criteria = organPersonRelationDao.createCriteria();
						criteria.createAlias("personInfo", "personInfo",criteria.LEFT_JOIN);
						criteria.createAlias("organizationInfo", "organizationInfo",criteria.LEFT_JOIN);
						criteria.add(Restrictions.eq("personInfo.userCode",userCode));
						//criteria.add(Restrictions.eq("personInfo.regionCode","510000000000"));
						//数据库中返回的对象数组
						List<OrganPersonRelationInfo> list = criteria.list();
						if(list.size()>0){
							PersonInfo personInfo = list.get(0).getPersonInfo();
							//判断当前用户所在机构 是否 开通信访举报子系统
							CentralizedDeploymentInfoDao centralizedDeploymentInfoDao = AppUtils.getBean("centralizedDeploymentInfoDao");
							criteria = centralizedDeploymentInfoDao.createCriteria();
							criteria.add(Restrictions.eq("regionCode", personInfo.getRegionCode()));
							List<CentralizedDeploymentInfo> listDeploymentInfo = criteria.list();
							if (listDeploymentInfo.size()>0) {
								if ("1".equals(personInfo.getInvalidFlag())) {
									try {
										BusinessConstants.getInstance();//初始化业务口径时间设置,时间不相同，每次登陆都需要设置
										Map<String, Object> map = logonManager.getPersonInfoMapByPersonEnameAndRegionCode(personInfo.getUserEname(), personInfo.getRegionCode());
										//用户正常登录，添加进在线用户供其他模块调用
										OnlineUser onlineUser = AppUtils.getOnlineUser(session.getId());
										if(onlineUser!=null){
											AppUtils.removeOnlineUser(session.getId());
										}
										//添加downIssueRegionCode字段方便所属区域校验
										PetitionOrganInfoManager petitionOrganInfoManager = AppUtils.getBean("petitionOrganInfoManager");
										String downIssueRegionCode = petitionOrganInfoManager.getDownIssueRegionCodeByRegionAuthority(personInfo.getRegionCode());
										personInfo.setDownIssueRegionCode(downIssueRegionCode);
										AppUtils.addOnlineUser(session.getId(), personInfo);
										//没有用户关系，说明该用户已经被删除
										OrganPersonRelationInfo opri = list.get(0);
										OrganizationInfo organ = opri.getOrganizationInfo();
										
										if(organ==null || personInfo==null){
											throw new RuntimeException("登录失败，personInfo或organ为null，具体请联系系统管理员。");
										}
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
										String interfaceQuerySHJW="";
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
										int pendingNotCompletedSize = 0;//延期未结
										int overdueNotCompletedSize = 0;//逾期未结
										int alarmActiveSize = 0;//预警
										String roleStr = logonManager.geneRoleStr(personInfo);
										if(personInfo.getLeaderFlag().equals(LeaderFlag.LEADER_MAIN.toString())){
											result += personInfo.getLeaderFlag();
											if(!StringUtils.isBlank(personInfo.getPlatformHomePage()))
												result += personInfo.getPlatformHomePage();//wld主页设置
										}
										session.setAttribute("roleStr", roleStr);
										//不是上海的用户跳入到系统，不走统计页面
										if(!Constants.SH_REGION_CDOE.equals(personInfo.getRegionCode())){
											result = Constants.TRUE;
										}
										if(RoleStr.LEADER_MAIN.toString().equals(roleStr)){
//											if(!"2".equals(personInfo.getPlatformHomePage()))//当主页标记不是2（文字统计），不执行以下方法，以免重复统计降低效率
											getChartCount();
										}
										AnalysisChartDao analysisChartDao = AppUtils.getBean("analysisChartDao");	
										pendingNotCompletedSize = analysisChartDao.pendingNotComplete(roleStr,personInfo,request);//延期未结
										overdueNotCompletedSize = analysisChartDao.overdueNotComplete(roleStr,personInfo,request);//逾期未结
										alarmActiveSize = analysisChartDao.alarmActived(roleStr,personInfo,request);//预警
										session.setAttribute("pendingNotCompletedSize",pendingNotCompletedSize);
										session.setAttribute("overdueNotCompletedSize",overdueNotCompletedSize);
										session.setAttribute("alarmActiveSize",alarmActiveSize);
										//获得版本信息
										getVersion(session);
									} catch(Exception e){
										e.printStackTrace();
									}
									chain.doFilter(request, response);
									try {
										addLoginLogs(new LoginArgs(personInfo.getId()
												,personInfo.getUserCname(),personInfo.getRegionCode(),personInfo.getRegionName(),"0"),httpRequest);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}else{//用户无效
									response.setContentType("text/JavaScript;charset=utf-8");
									response.getWriter().print("该用户已失效，请与管理员确认！");
								}
							}else{
								response.setContentType("text/JavaScript;charset=utf-8");
								response.getWriter().print("当前用户所在机构尚未部署开通信访举报子系统，请与系统管理员联系！");
							}	
						}else{//用户不存在
							response.setContentType("text/JavaScript;charset=utf-8");
							response.getWriter().print("信访系统不存在该用户，请去平台进行同步！");
						}
					}else{
						chain.doFilter(request, response);
					}
				}
			}
		}else{
			chain.doFilter(request, response);
		}
		
	*/}
	public void addLoginLogs(LoginArgs loginArgs,HttpServletRequest request)throws Exception{
		 addLoginLog(loginArgs, request);
	}
	public void addLoginLog(LoginArgs loginArgs, HttpServletRequest request) throws Exception{
		if(loginArgs == null || "".equals(loginArgs.getUserId().trim())){
			return;
		}
	    if(!LogInitParams.isLogEnable()){
	    	return;
	    }
	    LoginLogDao loginLogDao = AppUtils.getBean("loginLogDao");	
	    List dbLogs = loginLogDao.findBy("userId", loginArgs.getUserId());
	    LoginLog dbLog = null;
	    Integer loginNum = Integer.valueOf(1);
	    if(dbLogs != null && !dbLogs.isEmpty()){
	        dbLog = (LoginLog)dbLogs.get(0);
	        loginNum = Integer.valueOf(dbLog.getLoginNum().intValue() + 1);
	    } else{
	        dbLog = new LoginLog();
	        try{
	            BeanUtils.copyProperties(dbLog, loginArgs);
	        }
	        catch(Exception e){
	            throw new LogException("\u521B\u5EFA\u767B\u5F55\u65E5\u5FD7\u4FE1\u606F\u5931\u8D25\uFF01");
	        }
	        dbLog.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
	    }
	    dbLog.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
	    dbLog.setLoginNum(loginNum);
	    dbLog.setLoginIp(request.getRemoteAddr());
	    dbLog.setDeptId(loginArgs.getDeptId());
	    dbLog.setDeptName(loginArgs.getDeptName());
	    dbLog.setLogionStatus(loginArgs.getLogionStatus());
	    LoginLogManager loginLogManager = AppUtils.getBean("loginLogManager");
	    String mac = loginLogManager.getMACAddress(loginLogManager.getIpAddr(request));
	    dbLog.setLoginMac(mac);
	    if("0".equals(loginArgs.getLogionStatus())){
	        dbLog.setOnlineFlag("1");
	        dbLog.setLoginErrorNum(Integer.valueOf(0));
	        loginLogDao.save(dbLog);
	        request.getSession().setAttribute("log_loginTime", new Timestamp(System.currentTimeMillis()));
	        request.getSession().setAttribute("log_loginLogId", dbLog.getId());
	        request.getSession().setAttribute("log_loginNum", loginNum);
	        OnlineManager.add(request.getSession().getId(), loginArgs.getUserId());
	    } else{
	        dbLog.setOnlineFlag("0");
	        if("2".equals(loginArgs.getLogionStatus())){
	            LoginLog loginInfo = queryLogionInfoByUserId(loginArgs.getUserId());
	            if(loginInfo != null && "2".equals(loginInfo.getLogionStatus())){
	                Integer loginErrorNum = loginInfo.getLoginErrorNum();
	                if(loginErrorNum == null)
	                    loginErrorNum = Integer.valueOf(0);
	                dbLog.setLoginErrorNum(Integer.valueOf(loginErrorNum.intValue() + 1));
	            }
	        } else{
	            dbLog.setLoginErrorNum(Integer.valueOf(0));
	        }
	        loginLogDao.save(dbLog);
	    }
	}
	 private LoginLog queryLogionInfoByUserId(String userId){
		EntityManager entityManager=AppUtils.getBean("entityManager");
        Criteria criteria = entityManager.createCriteria();
        criteria.add(Restrictions.ge("lastLoginTime", DateHelper.simpleStringToDate(DateHelper.getNowString())));
        criteria.add(Restrictions.eq("userId", userId));
        criteria.addOrder(Order.desc("lastLoginTime"));
        criteria.setMaxResults(1);
        List list = criteria.list();
        if(list != null && !list.isEmpty())
            return (LoginLog)list.get(0);
        else
            return null;
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
	 * 获得版本号
	 * @throws IOException 
	 */
	private void getVersion(HttpSession session) throws IOException{
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
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}

