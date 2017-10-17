<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
  <head>
 
  </head>
  <body>
  <style>
 div[idFlag="roleSettingMan"]{
 	width: 100%;
 	height: 100%;
 }
  </style>
 	
	<div id="roleSettingMan" idFlag="roleSettingMan">
		<div class="sinosoft-layout" par="fit:true">  
		   <div par="region:'west',title:'角色'" style="width:280px;">
	   			 <div class="roleSettingManBar" style="height: 31px;text-align: center;">
						<a class="button green" style="width:50px;" onclick="roleSettingMan.add()">新增 </a>
						<a class="button lightgreen2" style="width:50px;" onclick="roleSettingMan.updateobj()">修改 </a>
						<a class="button write" style="width:50px;" onclick="roleSettingMan.remove()">删除 </a>
				</div> 
		    	<table idFlag="roleSettingGrid">
		    		<thead>
						<tr>
							<th par="field:'id',hidden:true"  ></th>
							<th par="field:'roleName'" width="150" align="center">角色名称</th>
						</tr>
					</thead>
		    	</table>
		    </div>   
		    <div par="region:'center'" style="width:500px;">
		    	<ul idFlag="rolemenutree"></ul>
		    </div>  
		    <div par="region:'east'" style="width:600px;">
		    	<table idFlag="permissionsGridMain"></table>
		    </div>   
		</div> 
		<jsp:include  page="/organization/roleSetting/roleSetingWin.jsp"/>
	</div>
	<script type="text/javascript" src="<%=basePath%>/organization/roleSetting/role.js"></script>
	<script type="text/javascript">
		$(function(){
			roleSettingMan.init();
		});
	</script>
  </body>
</html>