<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String swFlag = request.getParameter("swFlag");
	String dateFlag = request.getParameter("dateFlag");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问题类别变化情况</title>
<script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
</head>
<body>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/publicresource/css/grid.css">
	<style>
	span {
		font-size: 15px;
		margin-right: 3px
	}
	
	input {
		width: 150px;
		height: 26px;
		font-size: 14px;
	}
	
	.write, .write:hover {
		background: #BBD0DE;
		border: none;
		height: 26px;
		line-height: 26px;
		color: #062852
	}
	
	.bk {
		width: 98%;
		height: 430px;
		background: url(<%=basePath%>/images/bk.png) no-repeat;
		padding-top: 10px;
	}
	
	.tie {
		height: 420px;
		margin-left: 15px;
		background: url(<%=basePath%>/images/bjk2.png) no-repeat;
	}
	
	@media only screen and (max-width: 1024px) {
		.bk {
			background: url(<%=basePath%>/images/bk2.png) no-repeat;
		}
		.tie {
			background: url(<%=basePath%>/images/bjk1.png) no-repeat;
			background-size: 96.5%;
		}
		.xffbDiv {
			margin-left: 5px;
		}
	}
	
	.xffbDivxx {
		margin-left: 10px;
		width: 47%;
		height: 380px;
	}
</style>
	<div class="bk">
    <div class="tie">
         <div idFlag="xffbDiv" class = "xffbDivxx" style="float:left;padding-top:10px"> 
	     <div id="w" ></div>
         <table idFlag="xffb" id = "xffb"></table>
         </div>
         <div idFlag="xffbDiv1" class = "xffbDivxx" style="padding-top:10px;float:left;margin-left:2px"> 
	     <div id="w1" ></div>
         <table idFlag="xffb1" id = "xffb1"></table>
         </div>
      </div>
<!--       <div id="w" style = "display:none;" > -->
<%-- 	       <jsp:include page="/DataPredict/ProChange/AlarmOther.jsp"/> --%>
<!--          </div> -->
         
<!--          <div id="w1" style = "display:none;" > -->
<%-- 	       <jsp:include page="/DataAnalysis/PetitionInfo/Assemble.jsp"/> --%>
<!--          </div> -->
         
  </div>
  <input id = "reName" type="hidden">
	<script type="text/javascript">
	var xffbGrid = $.f("div","xffbDiv").f("table","xffb");
	xffbGrid.datagridSinosoft({
		body:{
			url:'/jubao/dataPredict/data-predict-fpm-info!fpmQW.action',
			queryParams:{
	 	    	swFlag : swFlag,/*全市1、本委2标识*/
	 	    	dateFlag : dateFlag,/*日期查询的按钮标识*/
	 	    	startDateValue　: startDate, /*按钮点击的开始时间*/
	 	    	endDateValue :　endDate,/*按钮点击的结束时间*/
	 	    },
			border : false,
			singleSelect : true,
			checkbox : false,
			fitColumns:true,
			columns:[[{
        		field : "ISSUEREGIONNAME",//列字段
        		title : "机构名称(区)",//列标题
        		align : "center",//表明如何对其列数据，可选值：'left'，'right'，'center'
        		width : 120
        	},{
        		field : "SELF",
        		title : "自收",
        		align : "center",
        		width : 98,
        		formatter : function(value,row,index){
        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\">"+value+"</a>";
        		}
        	},{
        		field : "TURN",
        		title : "上级转交办",
        		align : "center",
        		width : 106,
        		formatter : function(value,row,index){
        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\">"+value+"</a>";
        		}
        	}]],
        	
        	//单击事件   
			onClickRow:function(rowIndex,rowData){
			reName = rowData.ISSUEREGIONNAME;
			if(categoryMainObjxx){
    			categoryMainObjxx.panel("options").swFlag=swFlag;
    			categoryMainObjxx.panel("options").dateFlag=dateFlag;
    			categoryMainObjxx.panel("options").startDate=startDate;
    			categoryMainObjxx.panel("options").endDate=endDate;
    			categoryMainObjxx.panel("options").reName=reName;
 	    	} else {
		    categoryMainObjxx = $.f("div","categoryMainxzx").window({
 	    		    width:800, 
 	    		    height:500,
 	    		    title:'告警列表',
 	    		    modal:true,
 	    			maximizable:false,
 	    			minimizable:false,
 	    			collapsible:false,
 	    			closed:true,
    				swFlag:swFlag,
	    			dateFlag:dateFlag,
	    			startDate:startDate,
	    			endDate:endDate,
 		    		reName:reName,
 	    			onOpen:function(){
 	    				categoryMainWinx.init();
 	    			},
	    			onClose:function(){
	    				categoryMainObjxx.panel("options").swFlag="";
	 	    			categoryMainObjxx.panel("options").dateFlag="";
	 	    			categoryMainObjxx.panel("options").startDate="";
	 	    			categoryMainObjxx.panel("options").endDate="";
	 	    			categoryMainObjxx.panel("options").reName="";
	 	    		
	    			}
 	    		});
 	    	}
 	    	 categoryMainObjxx.window("open");
			},
        	//返回过滤的数据显示。该函数需要一个参数'data'，表示原始数据。您可以更改源数据的标准数据格式。此函数必须返回标准数据对象中包含的“total”和“rows”的属性
			loadFilter:function(data){
				data.rows = data.result;
				return data;
			},
			onBeforeLoad:function(param){
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
			},
			//当数据载入成功时触发
			onLoadSuccess: function(data){
				//$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
				var params={
 	    			  operationFuncCode : "fpmQW",
 	    			  operationFuncName : "区信件转办自收数量",
 	    			  operationBtnName : dateFlag,
 	    			  operationDesc : "区信件转办自收数量",
 	    			  operationTypeCode : OperationType.QUERY,
 	    			  operationTypeName : OperationType.QUERY_NAME,
 	    			  enableDataLog :true
 	    		}
 	    	    saveOperaLog(params);
			}
		}
	});
	//$(".datagrid-btable").width("100px")
	var xffbGrid1 = $.f("div","xffbDiv1").f("table","xffb1");
	xffbGrid1.datagridSinosoft({
		body:{
			url:'/jubao/dataPredict/data-predict-fpm-info!fpmS.action',
			queryParams:{
	 	    	swFlag : swFlag,/*全市1、本委2标识*/
	 	    	dateFlag : dateFlag,/*日期查询的按钮标识*/
	 	    	startDateValue　: startDate, /*按钮点击的开始时间*/
	 	    	endDateValue :　endDate,/*按钮点击的结束时间*/
	 	    },
			border : false,
			singleSelect : true,
			checkbox : false,
			fitColumns:true,
			columns:[[{
        		field : "ISSUEREGIONNAME",//列字段
        		title : "机构名称(派驻)",//列标题
        		align : "center",//表明如何对其列数据，可选值：'left'，'right'，'center'
        		width : 120
        	},{
        		field : "SELF",
        		title : "自收",
        		align : "center",
        		width : 98,
        		formatter : function(value,row,index){
        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\">"+value+"</a>";
        		}
        	},{
        		field : "TURN",
        		title : "上级转交办",
        		align : "center",
        		width : 106,
        		formatter : function(value,row,index){
        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\">"+value+"</a>";
        		}
        	}]],
        	//单击事件   
			onClickRow:function(rowIndex,rowData){
			reName = rowData.ISSUEREGIONNAME;
			if(categoryMainObjxx){
	 	    			categoryMainObjxx.panel("options").swFlag=swFlag;
	 	    			categoryMainObjxx.panel("options").dateFlag=dateFlag;
	 	    			categoryMainObjxx.panel("options").startDate=startDate;
	 	    			categoryMainObjxx.panel("options").endDate=endDate;
	 	    			categoryMainObjxx.panel("options").reName=reName;
 	    	}else{
		    categoryMainObjxx = $.f("div","categoryMainxzx").window({
 	    		    width:800, 
 	    		    height:500,
 	    		    title:'告警列表',
 	    		    modal:true,
 	    			maximizable:false,
 	    			minimizable:false,
 	    			collapsible:false,
 	    			closed:true,
 	    				swFlag:swFlag,
	 		    			dateFlag:dateFlag,
	 		    			startDate:startDate,
	 		    			endDate:endDate,
	 		    		reName:reName,
 	    			onOpen:function(){
 	    				categoryMainWinx.init();
 	    			},
	    			onClose:function(){
	    				categoryMainObjxx.panel("options").swFlag="";
	 	    			categoryMainObjxx.panel("options").dateFlag="";
	 	    			categoryMainObjxx.panel("options").startDate="";
	 	    			categoryMainObjxx.panel("options").endDate="";
	 	    			categoryMainObjxx.panel("options").reName="";
	    			}
 	    		});
 	    	}
 	    	 categoryMainObjxx.window("open");
			},
        	//返回过滤的数据显示。该函数需要一个参数'data'，表示原始数据。您可以更改源数据的标准数据格式。此函数必须返回标准数据对象中包含的“total”和“rows”的属性
			loadFilter:function(data){
				data.rows = data.result;
				return data;
			},
			onBeforeLoad:function(param){
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
			},
			//当数据载入成功时触发
			onLoadSuccess: function(data){
				//$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
				var params={
 	    			  operationFuncCode : "fpmS",
 	    			  operationFuncName : "派驻信件转办自收数量",
 	    			  operationBtnName : dateFlag,
 	    			  operationDesc : "派驻信件转办自收数量",
 	    			  operationTypeCode : OperationType.QUERY,
 	    			  operationTypeName : OperationType.QUERY_NAME,
 	    			  enableDataLog :true
 	    		}
 	    	    saveOperaLog(params);
			}
		}
	});
	</script>
  </body>
</html>