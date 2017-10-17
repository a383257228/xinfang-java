package com.sinosoft.xf.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sinosoft.xf.constants.Constants;


public class ObjectMapperWrap extends ObjectMapper {
	
	private static ObjectMapperWrap mapper;
	public static  ObjectMapperWrap getMapperInstance() {
		 if (mapper == null) {
			mapper = new ObjectMapperWrap();
		}
		return mapper;
	}
		
	private String defaultDateFormat = Constants.Date_Short;

	public ObjectMapperWrap() {
		SimpleDateFormat df = new SimpleDateFormat(defaultDateFormat);
		super.getSerializationConfig().setDateFormat(df);
		super.getDeserializationConfig().setDateFormat(df);
	}

	public String getDateFormat() {
		return defaultDateFormat;
	}

	public void setDateFormat(String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		super.getSerializationConfig().setDateFormat(df);
		super.getDeserializationConfig().setDateFormat(df);
	}

	public String writeValueAsString(Object value, String dateFormat) throws JsonGenerationException, JsonMappingException, IOException {
		this.setDateFormat(dateFormat);
		String result = super.writeValueAsString(value);
		this.setDateFormat(defaultDateFormat);
		return result;
	}

	public String writeValueAsString(Object value) throws JsonGenerationException, JsonMappingException, IOException {
		return super.writeValueAsString(value);
	}

}
