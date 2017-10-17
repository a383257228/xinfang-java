<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
  <head>
    <title>进驻信息</title>
  </head>
  <body>
  <style>
 	 div[idFlag="departmentGridDiv"]{
 	 	height: 100%;
 	 }
 	 div[idFlag="departmentGridDiv"] .datagrid-header-row, div[idFlag="departmentGridDiv"]  .datagrid-row{
	 	 height:40px; 
	 }
 	 div[idFlag="departmentGridDiv"] td{
 	 	border-right:0px !important;
 	 }
  </style>
  <!--管理部门新增窗体-->
 <jsp:include  page="/organization/departmentSetting/deptSettingWindow.jsp"/>
  <div idFlag="departmentGridDiv">
	<div class="departmenttool" style="height: 115px;padding:0px;overflow: hidden;">
		<div class="fieldset">检索查询</div>
		<div style="padding:13px 10px">
			<form idFlag="departForm">
				<span>部门名称:</span><input  idFlag="deptName"  class="sinosoft-textbox" style="width:200px">
				<span>部门编码:</span><input idFlag="deptCode" class="sinosoft-textbox" style="width:200px">
				<span>是否有效:</span><input type="checkbox" value='1' idFlag="validFlag" checked="checked"/>
				<a href="javascript:void(0)" class="button deepblue" style="width:50px;"   onclick="deptSettingGrid.queryCondition()">查询</a>
				<a href="javascript:void(0)"class="button write" style="width:50px;"  onclick="deptSettingGrid.queryreset()">重置</a>
			</form>
			 

		</div>
		<div class="fieldset">检索列表</div>
			<div style="float: right;position: relative;top: -30px;">
			<a href="javascript:void(0)" class="button green dis" style="width:50px;" onclick="deptSettingGrid.add()"> 新增</a>
			<a href="javascript:void(0)" class="button lightgreen2 dis" style="width:50px;" onclick="deptSettingGrid.update()"> 修改</a>
			</div>
	</div>
	<table idFlag="departmentGrid">
		<thead>
			<tr>
				<th par="field:'text'" width="220" >部门名称</th>
				<th par="field:'orgShortCname'" width="150" align="center">部门简称</th>
				<th par="field:'parentOrgCname'" width="150" align="center">上级部门名</th>
				<th par="field:'organOrder'" width="150" align="center">排序号</th>
				<th par="field:'invalidFlag',formatter:invalidFlagformatter " width="150" align="center">是否有效</th>
				<th par="field:'orgCode'" width="150" align="center">组织编码</th>
			</tr>
		</thead>
	</table>
</div>
 <script type="text/javascript" src="<%=basePath%>/organization/departmentSetting/deptSettingGrid.js"></script>
  <script type="text/javascript" src="<%=basePath%>/organization/departmentSetting/deptSettingWindow.js"></script>
	<script type="text/javascript">
		invalidFlagformatter=function(value,row,index){
			if(parseInt(value)==1){
				return "<img src=\"/jubao/images/ok.png\">";
			}else{
				return "<img src=\"jubao/images/error.png\">";
			}
		}
		$(function(){
			//初始化
			deptSettingGrid.init();
			//获取表格对象（方便以后用）
			deptSettingGrid.prototype=$.f("table","departmentGrid");
		})
	</script>
  </body>
</html>
