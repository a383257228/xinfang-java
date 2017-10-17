/**
 * 
 */
package com.sinosoft.xf.util.bean2json;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.sinosoftframework.core.utils.json.JsonUtils;

/**
 * 这个类的功能就是把Java对象转换成json串
 * 
 * 它的基本原理就是根据反射将某对象中的所有的属性，以及属性的值拿到，从而拼成json串
 * 拼出来的复杂的json 串的形式为 {"name":"value","name":"value",...."name":{"name":"value","name":"value"}}
 * 或者最简单的json串：  {"name":"value","name":"value",..."name":"value"}
 * 对于给定的数组或集合拼出来的json串 为 [{},..{}]的形式
 * @author zl
 * @date Jul 13, 2010
 */
public class Bean2jsonUtil {
	/**
	 * 通过bean生成json数据，可以过滤不需要输出的属性，可以规定时间格式
	 * @param bean 对象
	 * @param excludes 不需要输出的属性字符串，每个属性之间用“，”分隔
	 * @param patePattern 时间格式
	 * @return 数据
	 */
	public static String object2Json(Object bean , String[] excludes ,String datePattern){
		JsonConfig jsonConfig = JsonUtils.configJson(excludes, datePattern);
	    JSON json = JSONSerializer.toJSON(bean, jsonConfig);
	    return "["+json.toString().replaceAll("\\s*", "")+"]";
	}
	/**
	 * 通过bean生成json数据，可以过滤不需要输出的属性，可以规定时间格式
	 * @param bean 对象
	 * @param excludes 不需要输出的属性数组
	 * @param patePattern 时间格式
	 * @return 数据
	 */
	public static String object2Json(Object bean , String excludes ,String datePattern){
		return object2Json(bean, excludes.split(","), datePattern);
	}
	/**
	 * 如果想把一个任意对象转换成json串，那么就可以调用这个方法
	 * @param obj
	 *            任意对象：无关联关系的Bean、有关联关系的Bean、Object数组、List集合、Map集合等
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String object2json(Object obj,String[] filter) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte
				|| obj instanceof Character) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Timestamp || obj instanceof Date) {
			String str = obj.toString();
			if(str.length()>=20){
				String subStr = str.substring(11,str.length()-2);
				str =(!subStr.equals("00:00:00"))?str.substring(0,19):str.substring(0,10);
				json.append("\"").append(str).append("\"");
			}else{
				json.append("\"").append(str).append("\"");
			}
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj));
		} else if (obj instanceof Object) {
			json.append(beanTojson(obj,filter));
		}
		return json.toString();
	}

	/**
	 * @param obj
	 *            任意的简单对象 对象只能是简单类型：基本的数据类型，还有我们自己创建的简单类型（不包含关联关系的domain类）
	 * @return String
	 */
	public static String simpleObject2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Timestamp || obj instanceof Date) {
			String str = (obj.toString()).substring(0, obj.toString().indexOf("."));
			json.append("\"").append(str).append("\"");
		}
		return json.toString();
	}

	/**
	 * @param bean
	 *            bean对象
	 * 注意：如果你的对象是包含双向关联关系的对象，那么用这个方法可能会出现内存溢出的异常
	 * 双向关联它会无限制的去拼主对象、子对象、主对象、子对象。。。。。
	 * 这样就进入了无限递归的死循环。
	 * 建议使用simpleBbean2json方法
	 * @return String
	 */
	public static String beanTojson(Object bean , String[] filter){
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					Object nameVal = props[i].getName();
					String name = object2json(nameVal,filter);
					String value = object2json(props[i].getReadMethod().invoke(bean),filter);
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}
	

	/**
	 * @param bean
	 *            bean对象 适用于对象中只有简单类型的属性
	 * @return String
	 */
	public static String simpleBean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					if(null!=props[i].getReadMethod()){
						String name = simpleObject2json(props[i].getName());
						String value = simpleObject2json(props[i].getReadMethod()
								.invoke(bean));
						json.append(name);
						json.append(":");
						if(null==value||"".equals(value)){
							json.append("\"\"");
						}else{
							//去掉空白字符不局限于空格
							json.append(value.replaceAll("\\s*", ""));
						}
						json.append(",");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * @param list
	 *            list对象
	 * @return String
	 */
	public static String list2json(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("{result:[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj,null));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
			
		} else {
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}

	/**
	 * @param array
	 *            对象数组
	 * @return String
	 */
	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("{result:[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj,null));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}

	/**
	 * @param map
	 *            map对象
	 * @return String
	 */
	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key,null));
				json.append(":");
				json.append(object2json(map.get(key),null));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * @param set
	 *            集合对象
	 * @return String
	 */
	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj,null));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * @param s
	 *            参数 只是把参数名或者参数值都加上双引号
	 * @return String
	 */
	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
	
}
