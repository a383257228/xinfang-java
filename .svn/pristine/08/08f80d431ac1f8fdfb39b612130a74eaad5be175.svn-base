<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
	<div class="sinosoft-layout" idFlag="hisAuthortyDiv" style="width:100%;height:99%">
		<div par="region:'west',title:'部门信息'"  style="width:15%;">
	    	<ul idFlag="hisAuthortyMenu" class="sinosoft-tree"></ul>
	    </div>  
	    
	    <div par="region:'center',border:false" style="width:85%;height:100%" class="hisAuthortyTool">
	    	<div style="height:40px;width:99%;text-align: right;background:#d3dded !important" class="fieldset">
				<a style="float:left;margin-top:4px">检索列表</a>
				<div style="float : left;margin-left: 60px;">
					<form idFlag="hisForm" style="margin-top:4px">
						<span>用户名称:</span><input idFlag="queryuserenameHistory" validType="customChinaValidator[20]"  class="sinosoft-textbox" style="width:200px;">
						<a href="javascript:void(0)" class="button deepblue" style="width:50px;margin-bottom: 3px"  onclick="hisAuthorty.queryList()">查询</a>
						<a href="javascript:void(0)" class="button write" style="width:50px;margin-bottom: 3px"  onclick="hisAuthorty.reset()">重置</a>
					</form> 
				</div>
			</div>
			<div style="height:810px;">
	    		<table idFlag="hisAuthortyGrid"></table>
			</div>
	    </div>  
  	</div>
  	<script type="text/javascript">
  		$.SUI("hisAuthortyWin");
  		$(function(){
  			hisAuthorty.createList();
  		});
  	</script>
  </body>
</html>