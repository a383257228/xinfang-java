package com.sinosoft.xf.util.inmap2map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 将页面的获得的参数集合inmap放到map<String,Object>中。
 * @date 2011-12-15
 * @author Oba
 */
public class InMap2Map {
	/**
	 * 由于页面传入的map是<String,Object[]>类型，所以要将其转化为Map<String,Object>类型
	 * @param inMap 页面传入的map
	 * @return 正确类型的map
	 */
	public static Map<String, Object> getMapFormInMap(Map<String,String[]> inMap){
		Map<String,Object> map = new HashMap<String,Object>();
		Iterator<String> it = inMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = inMap.get(key)[0];
			map.put(key, value);
		}
		return map;
	}
}
