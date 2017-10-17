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
    <div idFlag="ProblemInfoWin" style="display: none">
         <div style="margin-left:10px;width:98%;height:550px"> 
         	  <div idFlag="problemFindPanel" style="margin-bottom:10px;">
         	 	  <span style="color:#BBD0DE;">反映人姓名:</span><input idFlag="accuserName" class="sinosoft-textbox" style="width:200px">
				  <span style="color:#BBD0DE;">被反映人姓名:</span><input idFlag="accusedName" class="sinosoft-textbox" style="width:200px">
				  
				  <a href="javascript:void(0)"class="button deepblue" style="width:50px;margin-left:10px;text-align:center;height:26px;line-height:26px;color:#062852;background:#49D9FE;font-size:14px" onclick="problemInfoWin.query()">查询</a>
				  <a href="javascript:void(0)"class="button write" style="width:50px;margin-left:10px;text-align:center;font-size:14px"  onclick="problemInfoWin.reset()">重置</a>
         	 </div>
         	 <div style="height:510px;">
	        	 <table idFlag="problemInfoGrid"></table>
         	 </div>
         </div>
    </div>
	<script type="text/javascript">
	var problemInfoWinObj;
	$.SUI("problemInfoWin");
	
	problemInfoWin = {
		query : function(){
			var accusedName;
			var accuserName;
			//被反映人姓名
			if($.f("div", "problemFindPanel").f("input","accusedName").textbox('getValue')){
				accusedName = $.f("div", "problemFindPanel").f("input","accusedName").textbox("getValue");
			}
			//反映人姓名
			if($.f("div", "problemFindPanel").f("input","accuserName").textbox('getValue')){
				accuserName = $.f("div", "problemFindPanel").f("input","accuserName").textbox("getValue");
			}
			var params = {
					swFlag : swFlag,
		 	    	dateFlag : dateFlag,
		 	    	startDateValue　: startDate,
		 	    	endDateValue :　endDate,
		 	    	accusedName : accusedName,
		 	    	accuserName : accuserName,
		 	    	issueTypeName : problemInfoWinObj.panel("options").name,
		 	    	flag : 'problem'
			};
			//将参数对象赋值给列表参数
			$.f("div","ProblemInfoWin").f("table","problemInfoGrid").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","ProblemInfoWin").f("table","problemInfoGrid").datagridSinosoft("load");
		},
		reset : function(){
			$.f("div", "problemFindPanel").f("input","accusedName").textbox('setValue',"");
			$.f("div", "problemFindPanel").f("input","accuserName").textbox('setValue',"");
		},
		init : function(){
			var swFlag=problemInfoWinObj.panel("options").swFlag;
			var dateFlag=problemInfoWinObj.panel("options").dateFlag;
			var startDate=problemInfoWinObj.panel("options").startDate;
			var endDate=problemInfoWinObj.panel("options").endDate;
			var bwRadio=problemInfoWinObj.panel("options").bwRadio;
			var name=problemInfoWinObj.panel("options").name;
			var problemInfoGrid = $.f("div","ProblemInfoWin").f("table","problemInfoGrid");
			problemInfoGrid.expandGridSinosoft({
				body:{
					detailFormatter: function(index,row){
						return "<div id=\""+index+"problemDiv\" style=\"width:95%\">></div>";
					},
					onExpandRow : function(index, row) {
						$("#" +index+"problemDiv").html("<div id=\""+index+"problemGrid\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
						$.parser.parse("#" +index+"problemDiv");
						$("#"+index+"problemGrid").edatagridSinosoft({
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
								columns : [[{
											field : 'regionName',
											title : "所属机构",
											width : 100,
											align : 'center'
										}, {
											field : 'activityNoName',
											title : "状态",
											width : 100,
											align : 'center'
										}, {
											title : "操作人",
											field : 'instructor',
											width : 100,
											align : 'center'
										}, {
											title : "操作时间",
											field : 'createDate',
											width : 100,
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
					 	    			operationFuncName : "查看问题分布情况信访件轨迹",
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
			 	    	issueTypeName : name,
			 	    	flag : 'problem',
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
		 	    			operationFuncName : "查询问题类别分布情况信访件列表",
		 	    			operationBtnName : dateFlag,
		 	    			operationDesc : "查询问题类别分布情况信访件列表",
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