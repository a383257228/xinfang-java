package com.sinosoft.xf.util.bean2map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.utils.bean.BeanUtils;
/**
 * 将对象信息放到map中
 * @date 2011-12-15
 * @author Oba
 */
public class Bean2MapUtil {
	
	private static Logger logger = Logger.getLogger(Bean2MapUtil.class);
	
	/**
	 * 为Map对象添加值
	 * @param fieldName
	 * @param object
	 * @return
	 */
	public static Map<String, Object> object2Map(Object source,Map<String, Object> map){
		//获得所有字段并遍历
		Field[] sourceFields = source.getClass().getDeclaredFields();
		for(Field field : sourceFields){
			String fieldName = field.getName();
			String fieldValue = reflectGetFieldValue(fieldName, source).toString();
			if(!"".equals(fieldValue)&& !"[]".equals(fieldValue)){
				map.put(fieldName.trim(), fieldValue.trim());
			}
		}
		return map;
	}
	/**
	 * 得到对象字段的值
	 * @param fieldName 字段名
	 * @param object 对象
	 * @return 对象
	 */
	private static Object reflectGetFieldValue(String fieldName,Object object){
		Object fieldValue = null;
		//利用反射读取对象中该字段的值
		try {
			fieldValue = BeanUtils.forceGetProperty(object, fieldName);
		} catch (Exception e) {
			logger.info("读取" + object.getClass().getSimpleName() + "对象的" + fieldName + "属性的值失败！");
		}
		//如果值为null则返回空字符串
		if(fieldValue == null){
			return "";
		}else{
			return fieldValue;
		}
	}
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("d", "d");
		PetitionBasicInfo basicInfo = new PetitionBasicInfo();
		basicInfo.setDealTypeName("dealTypeddddddddName");
		 object2Map(basicInfo,map);
		 System.out.println(map.get("dealTypeName"));
		 
	}
}
