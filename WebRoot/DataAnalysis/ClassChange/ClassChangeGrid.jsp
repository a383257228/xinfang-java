<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
  	<script src="<%=basePath%>/DataAnalysis/js/gridColumns.js"></script>
  </head> 
  <body>
  	<style>
  		 span{
	       font-size:15px;
	       margin-right:3px
	    }
	    input{
	       width:150px;
	       height:26px;
	       font-size:14px;
	    }
	    .write,.write:hover{
	      background:#BBD0DE;
	      border:none;
	      height:26px;
	      line-height:26px;
	      color:#062852
	    }
  	</style>
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
    <div idFlag="ClassChangeDiv" style="display: none">
         <div style="margin-left:10px;width:98%;height:550px"> 
         	<div idFlag="classChangeFindPanel" style="margin-bottom:10px;">
         	 	  <span style="color:#BBD0DE;">反映人姓名:</span><input idFlag="accuserName" class="sinosoft-textbox" style="width:200px">
				  <span style="color:#BBD0DE;">被反映人姓名:</span><input idFlag="accusedName" class="sinosoft-textbox" style="width:200px">
				  
				  <a href="javascript:void(0)"class="button deepblue" style="width:50px;margin-left:10px;text-align:center;height:26px;line-height:26px;color:#062852;background:#49D9FE;font-size:14px" onclick="classChangeGrid.query()">查询</a>
				  <a href="javascript:void(0)"class="button write" style="width:50px;margin-left:10px;text-align:center;font-size:14px"  onclick="classChangeGrid.reset()">重置</a>
         	 </div>
         	 <div style="height:510px;">
	        	  <table idFlag="classChangeInfoGrid"></table>
         	 </div>
         </div>
    </div>
	<script type="text/javascript">
	var classChangeWinObj;
	$.SUI("classChangeGrid");
	classChangeGrid = {
		query : function(){
			var accusedName;
			var accuserName;
			//被反映人姓名
			if($.f("div", "classChangeFindPanel").f("input","accusedName").textbox('getValue')){
				accusedName = $.f("div", "classChangeFindPanel").f("input","accusedName").textbox("getValue");
			}
			//反映人姓名
			if($.f("div", "classChangeFindPanel").f("input","accuserName").textbox('getValue')){
				accuserName = $.f("div", "classChangeFindPanel").f("input","accuserName").textbox("getValue");
			}
			var params = {
					swFlag : swFlag,
		 	    	dateFlag : dateFlag,
		 	    	startDateValue　: startDate,
		 	    	endDateValue :　endDate,
		 	    	accusedName : accusedName,
		 	    	accuserName : accuserName,
		 	    	issueRegionCode : classChangeWinObj.panel("options").code,
		 	    	issueTypeName : classChangeWinObj.panel("options").name,
		 	    	bwRadio : classChangeWinObj.panel("options").bwRadio
			};
			//将参数对象赋值给列表参数
			$.f("div","ClassChangeDiv").f("table","classChangeInfoGrid").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","ClassChangeDiv").f("table","classChangeInfoGrid").datagridSinosoft("load");
		},
		reset : function(){
			$.f("div", "classChangeFindPanel").f("input","accusedName").textbox('setValue',"");
			$.f("div", "classChangeFindPanel").f("input","accuserName").textbox('setValue',"");
		},
		init : function(){
			var swFlag=classChangeWinObj.panel("options").swFlag;
			var dateFlag=classChangeWinObj.panel("options").dateFlag;
			var startDate=classChangeWinObj.panel("options").startDate;
			var endDate=classChangeWinObj.panel("options").endDate;
			var code=classChangeWinObj.panel("options").code;
			var name=classChangeWinObj.panel("options").name;
			var bwRadio=classChangeWinObj.panel("options").bwRadio;
			
			var classChangeInfoGrid = $.f("div","ClassChangeDiv").f("table","classChangeInfoGrid");
			classChangeInfoGrid.expandGridSinosoft({
				body:{
					detailFormatter: function(index,row){
						return "<div id=\""+index+"classChangeDiv\" style=\"width:95%\"></div>";
					},
					onExpandRow : function(index, row) {
						$("#" +index+"classChangeDiv").html("<div id=\""+index+"classChangeGrid\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
						$.parser.parse("#" +index+"classChangeDiv");
						$("#"+index+"classChangeGrid").edatagridSinosoft({
							body : {
								url : '<%=basePath%>/workflow/petition-circulation-state-trace-info!pagedQuery.action',
								queryParams : {
									parameter:0,
									filterTxt : 'petitionNo',
									filterValue : row.petitionNo
								},
								fitColumns : true,
								border : false,
								fit : false,
								striped : true,
								pagination : false,
								singleSelect : false,
								pageSize:3,
								columns : [[{
											field : 'regionName',
											title : "所属机构",
											width : 120,
											align : 'center'
										}, {
											field : 'activityNoName',
											title : "状态",
											width : 120,
											align : 'center'
										}, {
											title : "操作人",
											field : 'instructor',
											width : 100,
											align : 'center'
										}, {
											title : "操作时间",
											field : 'createDate',
											width : 120,
											align : 'center'
										}]],
								loadFilter : function(data) {
									data.rows = data.result;
									return data;
								},
								onBeforeLoad : function(param) {
									param.sort="createDate";
									param.dir = "desc";
								},
								onLoadSuccess: function(data){
									var params={
			   		 	    			operationFuncCode : "pagedQuery",
			   		 	    			operationFuncName : "查看问题类别变化情况信访件轨迹",
			   		 	    			operationBtnName : dateFlag,
			   		 	    			operationDesc : "查看信访编号为"+row.currPetitionNo+"的轨迹",
			   		 	    			operationTypeCode : OperationType.QUERY,
			   		 	    			operationTypeName : OperationType.QUERY_NAME,
			   		 	    			enableDataLog :true
			   		 	    		}
			   		 	    		saveOperaLog(params);
								}
							}
						});
					},
					url:'<%=basePath%>/petition/petition-basic-info!classChangeInfo.action',
					queryParams:{
			 	    	swFlag : swFlag,
			 	    	dateFlag : dateFlag,
			 	    	startDateValue　: startDate,
			 	    	endDateValue :　endDate,
			 	    	issueRegionCode : code,
			 	    	issueTypeName : name,
			 	    	bwRadio : bwRadio
			 	    },
					border : false,
					nowrap :true,
					singleSelect : true,
					checkbox : false,
					fitColumns:false,
					pageSize : 15,
					frozenColumns : [[{
						field : "oid",
		        		hidden : true
		            }]],
					columns:[gridColumn()],
					loadFilter:function(data){
						data.rows=data.result;
						return data;
					},
					onBeforeLoad:function(param){
						param.start=(param.page-1)*param.rows;
						param.limit=param.rows;
					},
					onLoadSuccess: function(data){
						var params={
   		 	    			operationFuncCode : "classChangeInfo",
   		 	    			operationFuncName : "查询问题类别变化情况信访件列表",
   		 	    			operationBtnName : dateFlag,
   		 	    			operationDesc : "查询问题类别变化情况信访件列表",
   		 	    			operationTypeCode : OperationType.QUERY,
   		 	    			operationTypeName : OperationType.QUERY_NAME,
   		 	    			enableDataLog :true
   		 	    		}
   		 	    		saveOperaLog(params);
						//$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
					},
					pageList : [15, 30, 45]
				}
			});
		}
	}
	</script>
  </body>
</html>