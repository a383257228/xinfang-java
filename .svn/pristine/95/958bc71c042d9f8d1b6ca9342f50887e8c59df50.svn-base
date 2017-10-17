package com.sinosoft.xf.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import jxl.common.Logger;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.sinosoft.organization.dao.PersonInfoDao;
import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.filter.ReferFilter;
import com.sinosoft.xf.util.encrypt.Encrypt;

/**
 * 通过自动登录获取缓存
 * 
 * @author hjh 2015年9月25日上午10:21:30
 */
@Transactional
public class AutoLoginCache implements ServletContextAware {

	Logger logger = Logger.getLogger(AutoLoginCache.class);

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {

		this.servletContext = servletContext;

	}

	public ServletContext getServletContext() {

		return servletContext;

	}

	@Autowired
	private PersonInfoDao personInfoDao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 这是quarz的注解配置。每天的20：30自动执行该方法。
	public void initCache() {
		List<PersonInfo> persons = getPersons();
		// System.out.println(persons.size());
		loginPersonsInitCache(persons);
	}


	public void loginPersonsInitCache(List<PersonInfo> persons) {
		for (PersonInfo p : persons) {
			loginPersonInitCache(p.getUserEname(),
					Encrypt.decrypt(p.getUserPwd()), p.getRegionCode());
			// logger.info("======正在缓冲用户："+p.getUserCname()+" 的平台数据========");
			// simpleQueryManager.cachePlatFormData(true, p);
		}
	}

	boolean test;

	public void loginPersonInitCache(String userEname, String pwd,
			String regionCode) {
		String url = ReferFilter.ipConfig.getProperty("accessUrl");
		String appName = null;
		if (test)
			appName = "/jubao";
		else
			appName = servletContext.getContextPath();

		String webUrl = url + appName;
		HttpClient httpClient = new HttpClient();
		loginAction(userEname, pwd, regionCode, webUrl, httpClient);
		queryPlatform(webUrl, httpClient);

	}

	public void loginAction(String userEname, String pwd, String regionCode,
			String webUrl, HttpClient httpClient) {
		// 登陆 Url
		String loginUrl = webUrl + "/logon/logon!logonStandard.action";

		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		PostMethod postMethod = new PostMethod(loginUrl);

		// 设置登陆时要求的信息，用户名和密码
		NameValuePair[] data = { new NameValuePair("userEname", userEname),
				new NameValuePair("userPwd", DigestUtils.md5Hex(pwd)),
				new NameValuePair("regionCode", regionCode) };
		postMethod.setRequestBody(data);
		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(postMethod);
			// 获得登陆后的 Cookie
			Cookie[] cookies = httpClient.getState().getCookies();
			StringBuffer tmpcookies = new StringBuffer();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
			}
			System.out.println("cookie:" + tmpcookies.toString());
			// 打印出返回数据，检验一下是否成功
			String loginText = postMethod.getResponseBodyAsString();
			logger.info("登录信息：" + loginText);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void queryPlatform(String webUrl, HttpClient httpClient) {
		// 需登陆后访问的 Url
		String dataUrl = webUrl
				+ "/query/simple-query!characterStatistics.action?currData=true";
		// 进行登陆后的操作1581,1602,1603,1610,1609,1608,1607,1606,1605,1620,1619,1617,1616,1622,1626,1642,1648,1647,1657
		GetMethod getMethod = new GetMethod(dataUrl);
		try {
			// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
			// getMethod.setRequestHeader("cookie", tmpcookies.toString());
			httpClient.executeMethod(getMethod);
			// 打印出返回数据，检验一下是否成功
			String text = getMethod.getResponseBodyAsString();
			logger.info("查询信息：" + text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用于部门缓存的模版领导用户的sql
	 * 
	 * @author hjh 2015年9月25日
	 * @return
	 */
	public String getDeptLeadersSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select user_ename    ")
				.append(" from(                                                                                                                         ")
				.append(" select p.user_ename,p.user_cname,o.org_cname,o.org_code,row_number() over(partition by o.org_code order by p.user_cname) as rn")
				.append(" from person_info p                                                                                                            ")
				.append(" inner join Organ_Person_Relation_Info op on p.oid=op.person_info_id                                                           ")
				.append(" inner join organization o on o.oid=op.organization_info_id                                                                    ")
				.append(" where p.region_code='310000000000'                                                                                            ")
				.append(" and p.invalid_flag='1'                                                                                                        ")
				.append(" and p.leader_flag=1                                                                                                           ")
				.append(" ) leaders                                                                                                                     ")
				.append(" where rn=1  ");
		return sb.toString();
	}

	public List<PersonInfo> getPersons() {
		// 获取要缓存的用户数组
		List<String> leaderEnames = new ArrayList<String>();
		leaderEnames.add("00020");
		leaderEnames.add("18051");
		List<Map<String, Object>> enames = jdbcTemplate
				.queryForList(getDeptLeadersSql());
		for (Map<String, Object> map : enames) {
			String ename = (String) map.get("user_ename");
			leaderEnames.add(ename);
		}
		List<PersonInfo> persons = personInfoDao
				.createQuery("from PersonInfo p where p.userEname in (:args)")
				.setParameterList("args", leaderEnames.toArray()).list();
		return persons;
	}

	public PersonInfoDao getPersonInfoDao() {
		return personInfoDao;
	}

	public void setPersonInfoDao(PersonInfoDao personInfoDao) {
		this.personInfoDao = personInfoDao;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	
	

}
