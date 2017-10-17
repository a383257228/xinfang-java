<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
	<div class="sinosoft-layout" idFlag="permissionsDiv" style="width:100%;height:99%"">
		<div par="region:'west',title:'部门信息'" idFlag="deptWest" style="width:220px;">
			<ul idFlag="permissionDept" class="sinosoft-tree"></ul>
	    </div> 
	    
		<div  par="region:'center'" idFlag="centerDiv" style="width:450px;">
    		<div idFlag="centerTool" style="height:40px;width:99%;float:left;background:#d3dded !important" class="fieldset">
					<form idFlag="centerForm" style="margin-top:4px">
						<span>用户名称:</span><input idFlag="queryuserenameHistory" validType="customChinaValidator[100]"  class="sinosoft-textbox" style="width:200px;">
						<a href="javascript:void(0)" class="button deepblue" style="width:50px;margin-bottom: 3px"  onclick="permissions.queryPerson()">查询</a>
						<a href="javascript:void(0)" class="button write" style="width:50px;margin-bottom: 3px"  onclick="permissions.resetPerson()">重置</a>
					</form> 
			</div>
    		<table idFlag="permissionsGrid"></table>
		</div>
		
		<div par="region:'east'" idFLag="eastDiv" style="width:350px;">
			<div idFlag="eastTool" style="height:80px;width:99%;float:left;background:#d3dded !important" class="fieldset">
					<form idFlag="eastForm">
						<span>角色名称:</span><input idFlag="queryrolename" validType="customChinaValidator[100]"  class="sinosoft-textbox" style="width:210px;">
						<a href="javascript:void(0)" class="button deepblue" style="width:50px;margin-bottom: 3px"  onclick="permissions.queryAuthorty()">查询</a>
						<a href="javascript:void(0)" class="button write" style="width:50px;margin-bottom: 3px"  onclick="permissions.resetAuthorty()">重置</a>
						<a href="javascript:void(0)" idFlag="update" class="button lightgreen2" style="width:50px;margin-bottom: 3px"  onclick="permissions.updateAuthorty()">修改</a>
					</form> 
			</div>
    		<table idFlag="authortyEastGrid"></table>
    		<ul idFlag="permissionsmenutree" hidden="true"></ul>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$.f("div","eastTool").f("a","update").setdisabledEvent(true);//修改按钮禁用
			permissions.init();
		});
	</script>
  </body>
</html>