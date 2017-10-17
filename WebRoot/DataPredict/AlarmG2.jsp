<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>树木与森林</title>
  	<base href="<%=basePath%>"> 	
<script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
<script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
<script src="<%=basePath%>/publicresource/js/g2.js"></script>
</head>
<body style="background:rgba(4,14,23,0.8)">
	<style>
/* 弹窗窗口样式修改   */
.panel-header, .panel-body, .pagination-btn-separator {
	border: none
}

.window {
	background: rgba(4, 37, 72, 0.8);
	border: 1px solid #27758C
}

.window .window-header {
	background: none
}

.window .panel-title {
	font-size: 16px;
	text-align: center
}

.window-shadow {
	width: 100% !important;
	height: 100% !important;
	background: rgba(4, 14, 23, 0.8);
	left: 0 !important;
	top: 0 !important;
}

.pagination {
	margin-left: -10px !important;
}
	</style>
	<div idFlag="categoryMain">
	    <div style="width:98%;height:420px;background:url(<%=basePath%>/images/bk2.png) no-repeat;background-size:100%;padding-top:10px;margin-left:8px;">
		    <div style="height:410px;margin-left:17px;margin-top:2px;background:url(<%=basePath%>/images/bjk1.png) no-repeat;background-size:98%;padding-top:10px">
		         <div style="margin-left:0px;width:98%;height:410px"> 
		        	  <div idFlag="hahaDiv2" style="margin-left:10px;width:97%;height:370px">
			             <table idFlag="hahax2" id="xffbx"></table>
					  </div>
		         </div>
		    </div>
	    </div>
    </div>
	
	
  	<div idFlag="categoryMain" style="margin-left:10px">
		<div idFlag="hahaDiv2" style="margin-left:10px;width:95%;height:410px">
			<table idFlag="hahax2" id="xffbx"></table>
		</div>
	</div>
	<script type="text/javascript">
	var categoryMainObj;
	$.SUI("categoryMainWin");
	categoryMainWin = {
			init : function(){
	var xffbGrid = $.f("div","hahaDiv2").f("table","hahax2");
	//var aname=document.getElementById('text111').value;
	//var aname="王爽";
	xffbGrid.datagridSinosoft({
		body:{
			url:'/jubao/dataPredict/data-predict-tree-forest!gTest2.action',
			queryParams:{
				detail:1,	//要细节
				nexus:0,	//单关系
				search:1,	//精确搜索
	 	    	aname : bigname
	 	    	//aname : a
	 	    },
			border : false,
			singleSelect : false,
			fitColumns:false,//让列宽自动适应数据表格的宽度
			columns:[[{
        		field : "ACCUSED",
        		title : "被反映人",
        		width : 240,
        		align : "center",
        	},{
        		field : "DISTANCE",
        		title : "关系权重",
        		width : 235,
        		align : "center",
        	},{
        		field : "WORK_UNIT",
        		title : "单位",
        		width : 205,
        		align : "center",
        	},]],
			loadFilter:function(data){
				data.rows=data.result;
				return data;
			},
			
			onLoadSuccess: function(data){
				$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
			}
		}
	});
			}
	};
	</script>
  </body>
</html>