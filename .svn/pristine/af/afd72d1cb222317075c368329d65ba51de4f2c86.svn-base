<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //if(null!=request.getParameter("aname")) {  
    //    page=request.getParameter("aname").toString();  
    //} 
	//String swFlag = request.getParameter("swFlag");
	//String dateFlag = request.getParameter("dateFlag");
	//String startDate = request.getParameter("startDate");
	//String endDate = request.getParameter("endDate");
	//String aname = request.getParameter("aname");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>告警</title>
  	<base href="<%=basePath%>"> 	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/whole.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/sinosoftUI.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/jeasyui.extensions.datagrid.css">
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.sinosoftUI.master.js"></script>
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.pagination.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/jquery.pagination.css">
  </head> 
  <body>
  <style>
  .datagrid-header{
      background:#1B5EB7;
      }
  .datagrid-header td, .datagrid-body td, .datagrid-footer td{
     border-color:#0B4A80!important;
     border-right:aliceblue
     }
	 .datagrid-view tr{
	    height:33px 
	 }
	 .datagrid-row{
	    background:rgba(143,174,195,0.6) !important
	 }
	 .datagrid-cell-rownumber,.datagrid-cell {
	 font-size:15px !important
	 }
	 .datagrid-header .datagrid-cell span,.pagination-info,.pagination span{
	  font-size:16px !important
	 }
	 .datagrid-row-over  .datagrid-cell{
	   color:#000000;
	 }
	 .datagrid-row-selected{
	    background:#BBD0DD
	 }
	 .datagrid-row-over, .datagrid-header td.datagrid-header-over{
	    background:#BBD0DD
	 }
	 .datagrid-toolbar, .datagrid-pager{
	    background:none
	 }
	 .datagrid-header, .datagrid-toolbar, .datagrid-pager, .datagrid-footer-inner{
	 border-color:#0B4A80
	 }
	 .datagrid-header .datagrid-cell span, .pagination-info, .pagination span{
	 color:#ffffff
	 }
	 .pagination-btn-separator{
	 border:none
	 }
	.pagination-num, .pagination-page-list{
	 font-size: 15px;
    width: 45px;
    height: 27px;
	 }
	</style>
  	<div style="width:100%;height:430px;background:url(<%=basePath%>/images/bk2.png) no-repeat;background-size:100%;padding-top:12px">
    <div style="height:410px;margin-left:15px;background:url(<%=basePath%>/images/bjk1.png) no-repeat;background-size:98%;padding-top:10px">
         <div idFlag="hahaDiv" style="margin-left:10px;width:95%;height:410px"> 
         <table idFlag="hahax" id = "hahax"></table>
         </div>
      </div>
  </div>
	<script type="text/javascript">
	var xffbGrid = $.f("div","hahaDiv").f("table","hahax");
	var temp="";
	var aname=smallname;
	xffbGrid.datagridSinosoft({
		body:{
			url:'/jubao/dataPredict/data-predict-tree-forest!gTest.action',
			queryParams:{
	 	    	aname : aname
	 	    },
			border : false,
			singleSelect : false,
			columns:[[{
        		field : "PETITION_BASIC_INFO_OID",
        		title : "OID",
        		width : 30,
        		align : "center",
        	},{
        		field : "ACCUSED_NAME",
        		title : "被反映人",
        		width : 30,
        		align : "center",
        	},{
        		field : "ACCUSED_SEX_NAME",
        		title : "性别",
        		width : 30,
        		align : "center",
        	}]],
			loadFilter:function(data){
				data.rows=data.result;
				return data;
			},
			//单击事件   
			onClickRow:function(rowIndex,rowData){
				middlename=rowData["ACCUSED_NAME"];
				$("#haha").window('close');
				nnn();

			},
			onLoadSuccess: function(data){
				$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
			}
		}
	});
	</script>
  </body>
</html>