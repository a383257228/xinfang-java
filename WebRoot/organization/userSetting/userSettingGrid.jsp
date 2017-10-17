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
   	div[idFlag="userGridDiv"]{
 	 	height: 100%;
 	 }
 	 div[idFlag="userGridDiv"] .datagrid-header-row, div[idFlag="userGridDiv"]  .datagrid-row{
	 	 height:40px; 
	 }
 	 div[idFlag="userGridDiv"] td{
 	 	border-right:0px !important;
 	 }
  
  </style>
	<!--管理用户的新增窗体-->
	<jsp:include  page="/organization/userSetting/userSettingWindow.jsp"/>
	<div idFlag="userGridDiv">
		<div class="usertool"style="height: 115px;padding:0px;overflow: hidden;">
			<div class="fieldset">检索查询</div>
			<div style="padding:13px 10px">
				<form idFlag="userGridForm">
					<span>用户名称:</span><input idFlag='name'  par="validType:'customChinaValidator[30]'"  class="sinosoft-textbox" style="width:200px"> 
				
					<a href="javascript:void(0)" class="button deepblue" style="width:50px;"  onclick="userSettingGrid.queryUser()">查询</a>
					<a href="javascript:void(0)"class="button write" style="width:50px;"  onclick="userSettingGrid.queryreset()">重置</a>
				</form>
			</div>
			<div class="fieldset">检索列表</div>
			<div  style="float: right;position: relative;top: -28px;">
				<a class="button green dis" style="width:50px;" onclick="userSettingGrid.add()"> 新增</a>
				<a class="button lightgreen2 dis" style="width:50px;" onclick="userSettingGrid.update()"> 修改</a>
			</div>
		</div>
		<table idFlag="userGrid">
			<thead>
				<tr>
					<th par="field:'text'" width="220" >用户姓名</th>
					<th par="field:'organCname'" width="150" align="center">所属部门</th>
					<th par="field:'userEname'" width="150" align="center">用户登录名</th>
					<th par="field:'createDate'" width="150" align="center">注册日期</th>
					<th par="field:'invalidFlag',formatter:invalidFlagformatter " width="150" align="center">是否有效</th>
					<th par="field:'operator'" width="150" align="center">操作人</th>
				</tr>
			</thead>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath%>/organization/userSetting/userSettingGrid.js"></script>	
		<script type="text/javascript">
		invalidFlagformatter=function(value,row,index){
			if(parseInt(value)==1){
				return "<img src=\"/jubao/images/ok.png\">";
			}else if(parseInt(value)==2){
				return "<img src=\"/jubao/images/error.png\">";
			}
		}
		$(function(){
			userSettingGrid.init();
			userSettingGrid.prototype=$.f("table","userGrid");
		})
		</script>
  </body>
</html>
