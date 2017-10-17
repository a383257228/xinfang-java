$.SUI("operationQuery");

operationQuery.createList = function(){
	var operationQueryGrid = $.f("div","operationQuery").f("table","operationQueryGrid");
	//var dataLogId = operationQueryGrid.datagridSinosoft("getSelected").id;dataLogId
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	operationQueryGrid.datagridSinosoft({
		toolbar:'.querytool',
		body:{
			url:sessionlostName+ '/log/operation-log!operationLogQuery.action',
			border : false,
			singleSelect : true,
			checkbox : false,
			queryParams : params,
			frozenColumns : [[{
        		field : "id",
        		hidden : true
        	},{
        		field : "loginLogId",
        		hidden : true
        	}]],
			columns:[[{
        		field : "userName",
        		title : "用户",
        		align : "center",
        		width : 60
        	},{
        		field : "operationFuncName",
        		title : "操作功能",
        		align : "center",
        		width : 70,
        		formatter : function(value,row,index){
        			return value.trim();
        		}
        	},{
        		field : "operationBtnName",
        		title : "操作按钮",
        		align : "center",
        		width : 70
        	},{
        		field : "operationTime",
        		title : "操作时间",
        		align : "center",
        		width : 120
        	},{
        		field : "operationDesc",
        		title : "操作描述",
        		align : "center",
        		width : 160
        	},{
        		field : "operationResultName",
        		title : "操作结果",
        		align : "center",
        		width : 50,
        		formatter : function(value,row,index){
        			if(value.indexOf('成功') != -1) {
						return '<img src=/jubao/publicresource/images/icons/yes.png style="height:18; width:18" valign="center"></img>'
								+ "成功";
					}else {
						return '<img src=/jubao/publicresource/images/icons/no.png  style="height:16; width:16" valign="center"></img>'
								+ '失败';
					}  
        		}
        	},{
        		field : "operationTypeName",
        		title : "操作类型",
        		align : "center",
        		width : 50
        	},{
        		field : "countNum",
        		title : "统计数",
        		align : "center",
        		width : 60
        	},{
        		field : "c",
        		title : "操作区",
        		width : 120,
        		align : "center",
        		formatter : function(value,row,index){
        			return returnStr="<a  href='javascript:operationQuery.lookDataWin(\""+row.id+"\")'>查看数据日志</a>";
        		}
        	}]],
			loadFilter:function(data){
				data.rows =  data.result;
				return data;
			},
			onBeforeLoad:function(param){
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
				param.dir = "DESC";
				param.sort = "operationTime";
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "operationLogQuery",
					operationFuncName : "查询日志列表",
					operationBtnName : "查询",
					operationDesc : "查询日志列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
				 $(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
			}
		}
	});
}
//查看数据日志
operationQuery.lookDataWin = function(id){
	var params = {};
	params.filterTxt = "dataLogId";
	params.filterValue = id;
	
	$.f("div", "operationDataDiv").window({
		title : "数据日志信息",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		width:800,
		height:500,
		onOpen : function(){
			var operationDataGrid = $.f("div","operationDataDiv").f("table","operationDataGrid");
			operationDataGrid.datagridSinosoft({
				body:{
					url:sessionlostName+ '/log/operation-data-log!operationDataLogQuery.action',
					queryParams : params,
					border : false,
					singleSelect : true,
					checkbox : false,
					sortType : 'DESC',
					columns:[[{
		        		field : "dataDesc",
		        		title : "操作数据描述",
		        		align : "center",
		        		width : 60
		        	},{
		        		field : "dataValue",
		        		title : "操作数据值",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "dataTypeName",
		        		title : "数据类型",
		        		align : "center",
		        		width : 100
		        	}]],
					loadFilter:function(data){
						data.rows =  data.result;
						return data;
					},
					onBeforeLoad:function(param){
						param.start=(param.page-1)*param.rows
						param.limit=param.rows
					},
					onLoadSuccess: function(data){
						var params={
							operationFuncCode : "operationDataLogQuery",
							operationFuncName : "查询日志详细信息",
							operationBtnName : "查询",
							operationDesc : "查询日志详细信息",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
						 $(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
					}
				}
			});
		}
	});
	$.f("div", "operationDataDiv").window("open");
}
//查询
operationQuery.queryList = function(){
	var filterTxt="";
	var filterValue="";
	
	var userName = $.f("form","operationQueryForm").f("input","userName").combobox('getText');//用户名
	if(userName){
		filterTxt += "userName;";
		filterValue += userName+";";
	}
	var operationFuncName = $.f("form","operationQueryForm").f("input","operationFuncName").textbox('getValue');//操作功能
	if(operationFuncName){
		filterTxt += "operationFuncName;";
		filterValue += operationFuncName+";";
	}
	var operationResultName = $.f("form","operationQueryForm").f("input","operationResultName").combobox('getValue');//操作结果
	if(operationResultName){
		filterTxt += "operationResultCode;";
		filterValue += operationResultName+";";
	}
	var operationTypeName = $.f("form","operationQueryForm").f("input","operationTypeName").combobox('getValue');//操作类型
	if(operationTypeName){
		filterTxt += "operationTypeCode;";
		filterValue += operationTypeName+";";
	}
	var operationDesc = $.f("form","operationQueryForm").f("input","operationDesc").textbox('getValue');//操作描述
	if(operationDesc){
		filterTxt += "operationDesc;";
		filterValue += operationDesc+";";
	}
	var deptName = $.f("form","operationQueryForm").f("input","deptName").combobox('getText');//统计机构
	if(deptName){
		filterTxt += "deptName;";
		filterValue += deptName+";";
	}
	
	// 获取的起始时间   
	var startTimeValue = $.f("form","operationQueryForm").f("input","operationTime_startQ").datebox("getValue");
	if (startTimeValue) {
		var startTime = startTimeValue + " 00:00:00";
		filterTxt += "operationStartTime;";
		filterValue += startTime + ";";
	}
	var endTimeValue = $.f("form","operationQueryForm").f("input","operationTime_endQ").datebox("getValue");
	if(endTimeValue){
		var endTime = endTimeValue + " 23:59:59";
		filterTxt += "operationEndTime;";
		filterValue += endTime + ";";
	}
	var params = {
		filterTxt : filterTxt,
		filterValue : filterValue
	};
	$.f("table","operationQueryGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("table","operationQueryGrid").datagridSinosoft("load","/jubao/log/operation-log!operationLogQuery.action");
}
//重置
operationQuery.reset = function(){
	$.f("form","operationQueryForm").form("reset");
	$.f("form","operationQueryForm").f("input","userName").combobox('clear');
	$.f("form","operationQueryForm").f("input","operationResultName").combobox('setValue','');
	$.f("form","operationQueryForm").f("input","operationTypeName").combobox('clear');
	$.f("form","operationQueryForm").f("input","deptName").combobox('clear');
}