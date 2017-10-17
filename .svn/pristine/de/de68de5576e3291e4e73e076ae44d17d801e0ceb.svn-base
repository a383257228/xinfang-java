<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String basePath = request.getContextPath();
String pathloginvarBasePath = request.getContextPath();
String loginvarBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathloginvarBasePath+"/";

%>

<html> 
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
  </head>
	<script type="text/javascript" >
	var loginvarBasePath = "<%= loginvarBasePath%>";
	var basePath = "<%=basePath%>";
	
$(function(){
	
})

function createIndex(){
	$.ajax({
		type: "POST",
		url:loginvarBasePath + "/fullTextSearch/full-text-search!recreateIndexAndImportData.action",
		dataType :"text",
		success: function(data, textStatus, jqXHR ){
			if(data=="true"){
				alert("创建成功");
			}else{
				console.error(data);
			}
		}
	});
}

	</script>
<body>
	<a href="javascript:void(0)" onclick="createIndex()">创建索引</a>
</body>
</html>
