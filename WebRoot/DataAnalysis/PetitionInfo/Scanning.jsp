<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%@ include file="/publicresource/jsp/comm_head.jsp"%>
  <head>
  	 <title>扫描件信息</title>
  	 <base href="<%=basePath%>"> 	
  </head> 
  <body style="background:#042241">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
	   	 <style>
		      .panel-body{
		         background:none
		     }
		     .datagrid-header,.panel-header-noborder{
		    	 border:0
		     }
		     .datagrid-header, .datagrid-header-hover{
		        background:#015190 !important;
		     }
	     </style>
	     
	     <div style="margin-top:10px;margin-left:30%">
	        <span style="color:#00C9D9;font-size:14px">处理机构：</span>
	        
	        <select style="width:80px;height:30px;background:#015190;color:#fff;border-color:#01BDC8;font-size:14px">
			    <s:iterator value="regionCodeMap">
		     	<option value="<s:property value='key'/>" >
				    <s:if test="key==petitionInfo.regionCode">
				    
				    </s:if>
		   		 <s:property value='value'/>
		     	</option>
		    </s:iterator>
		   </select>
	    </div>
    <div idFlag="Scanning" style="width:785px;height:450px;margin-top: 10px;margin-left: 18px"> 
          <table idFlag="scan" style="height:350px;"></table>
    </div>
  </body>
</html>