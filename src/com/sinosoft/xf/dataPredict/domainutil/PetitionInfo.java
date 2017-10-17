package com.sinosoft.xf.dataPredict.domainutil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/***
 * 分页返回字段属性
 * @author lzd
 */
public class PetitionInfo implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Map<String, Object>> result;

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}
	
}
