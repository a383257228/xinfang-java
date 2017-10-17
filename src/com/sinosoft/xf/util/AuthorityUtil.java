package com.sinosoft.xf.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.authority.domain.AuthorityData;
import com.sinosoft.authority.domain.AuthorityUserRole;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;

/**
 * 系统权限缓存类
 * @date  2012-12-05
 * @author sunzhe
 *
 */
public class AuthorityUtil {

	public static final Map<String,String> authCacheMap = new HashMap<String,String>();
	
	public static final Map<String,List<AuthorityData>> codeAuthCacheMap = new HashMap<String,List<AuthorityData>>();
	
	public static final Map<String,List<SystemCodeNodeInfo>> codeCacheMap = new HashMap<String,List<SystemCodeNodeInfo>>();
	
	public static final Map<String,List<AuthorityUserRole>> userRoleCacheMap = new HashMap<String,List<AuthorityUserRole>>();
}
