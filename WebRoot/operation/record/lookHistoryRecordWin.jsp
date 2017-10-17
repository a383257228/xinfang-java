<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<div class="sinosoft-layout" idFlag="lookHisRecordDiv" style="width:100%;height:100%">
		<div par="region:'west'"  style="width:15%;">
			<div style="height:38px;line-height: 38px;text-align: center;background-color: #2065cb;color: #fff;">
				功能菜单权限
			</div>
	    	<ul idFlag="accessMenu" class="sinosoft-tree"></ul>
	    </div>  
	    <div par="region:'center',border:false" style="width:85%;height:100%">
	    	<table idFlag="lookHisRecordGrid"></table>
	    </div>  
  	</div>
  </body>
</html>