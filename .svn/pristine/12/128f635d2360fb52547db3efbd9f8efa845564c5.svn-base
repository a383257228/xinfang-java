package com.sinosoft.xf.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper{
	@SuppressWarnings("rawtypes")
	private Map params;   
	public ParameterRequestWrapper(HttpServletRequest request,@SuppressWarnings("rawtypes") Map newParams) {   
	  super(request);   
	  this.params=newParams;   
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map getParameterMap() {
	  return params;   
	}
	public String getParameter(String name) {   
	  Object v = params.get(name);   
	  if(v==null){   
	    return null;   
	  }else if(v instanceof String[]){             
	    String []strArr=(String[]) v;   
	    if(strArr.length>0){   
	      return strArr[0];   
	    }else{   
	      return null;   
	    }   
	  }else if(v instanceof String){   
	    return (String) v;   
	  }else{   
	    return v.toString();   
	  }   
	}  
}
