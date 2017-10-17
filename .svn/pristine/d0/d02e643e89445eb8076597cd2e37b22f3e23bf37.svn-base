<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	  <%@ include file="/publicresource/jsp/comm_head.jsp"%>
  <head>
  	<title>多媒体信息</title>
  	<base href="<%=basePath%>"> 	
  </head> 
  <body >
   <link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
  <style>
    .tree-icon ,.tree-folder ,.tree-folder-open{
	   background:none !important
     }
    .tree-expanded{
       margin-top:3px;
	   background:url(<%=basePath%>/DataAnalysis/PetitionInfo/images/reduce.png) no-repeat !important
     }
     .tree-collapsed{
        margin-top:3px;
        background:url(<%=basePath%>/DataAnalysis/PetitionInfo/images/plus.png) no-repeat !important
     }
     .datagrid-body{
        background:#042241
      }
     .datagrid-row-checked .datagrid-cell,.datagrid-header .datagrid-cell span, .pagination-info, .pagination span, .datagrid-cell-rownumber, .datagrid-cell, .datagrid-htable, .datagrid-btable, .datagrid-ftable{
        color:#fff !important;
     }
     .datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber{
        font-size:13px !important;
     } 
      .datagrid-header td, .datagrid-body td, .datagrid-footer td{
        border-color:#0B4A80!important; 
        border-width:0 0 1 0;
      }
     .datagrid-sort .datagrid-sort-icon,.datagrid-row{
        background:none !important;
      }
       .datagrid-header{
         background:#015190
      }
      .panel-header, .panel-body, .datagrid-header,.panel-header-noborder{
         border:0
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
	<div style="margin-top:10px;margin-left:18px">
		<table idFlag="multimedia"  style="width:780px;height:300px;">
		</table>
	</div>
	<script>
	 $.f("table","multimedia").treegrid({
	    url:'<%=basePath%>/DataAnalysis/PetitionInfo/json/Multi.json',    
	    idField:'idFlag',    
	    treeField:'name', 
	    title:false,
	    fitColumns:true,
	    columns:[[    
	        {title:'序号',field:'name',width:150},    
	        {title:'文件名称',field:'FileName',width:150},    
	        {title:'上传时间',field:'UploadTime',width:140},    
	        {title:'文件类型',field:'FileType',width:140},
	        {title:'操作',field:'Operation',width:100} 
	    ]],  
	}); 
	</script>
  </body>
</html>