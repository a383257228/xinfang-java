<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
  <head>
  </head>
  <body>
  	<div idFlag="systemCodeMain" id="systemCodeMain">
	  <div class="sinosoft-layout" style="width:100%;height:100%;">   
		  <div id="systemcodeCodeTree" par="region:'west',title:'代码类别',split:true" style="width:215px;">
		  	<ul idFlag="systemCodeTree"></ul> 
		  </div>
		  <div par="region:'center',split:true" style="width:200px;">
		  	<div idFlag="systemCodeNodeGridBar">
		 	 	<a idFlag="add" href="javascript:void(0)"  class="button invalid" style="width:50px;" plain="true" onclick="systemCode.add()" >新增</a>
	       		<a idFlag="update" href="javascript:void(0)"  class="button invalid" style="width:50px;" plain="true" onclick="systemCode.update()">修改</a>
			    <a idFlag="delete" href="javascript:void(0)"  class="button invalid" style="width:50px;" plain="true" onclick="systemCode.del()">删除</a>
	  		</div>
		  	<table idFlag="systemCodeNodeGrid"></table>
		  </div>
	  </div>
	</div>
	<jsp:include  page="/organization/systemCode/systemCodeNodeAddWin.jsp"/>
	<script type="text/javascript" src="<%=basePath%>/organization/systemCode/systemCode.js"></script>
	<script type="text/javascript">
	$.SUI("systemCode");
	$(function(){
		systemCode.init();
	});
  </script>
  </body>
</html>