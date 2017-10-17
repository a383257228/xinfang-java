$.SUI("operationList");

operationList.createList = function(){
	var operationListGrid = $.f("div","operationList").f("table","operationListGrid");
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	operationListGrid.datagridSinosoft({
		toolbar:'.listtool',
		body:{
			url:sessionlostName+ '/log/login-log!pagedQuery.action',
			border : false,
			singleSelect : true,
			checkbox : false,
			queryParams : params,
			frozenColumns : [[{
        		field : "id",
        		hidden : true
        	},{
        		field : "userId",
        		hidden : true
        	}]],
			columns:[[{
        		field : "userName",
        		title : "用户名",
        		align : "center",
        		width : 60
        	},{
        		field : "lastLoginTime",
        		title : "最近登录时间",
        		align : "center",
        		width : 95
        	},{
        		field : "lastLogoutTime",
        		title : "最近退出时间",
        		align : "center",
        		width : 95
        	},{
        		field : "loginIp",
        		title : "最近操作IP",
        		align : "center",
        		width : 80
        	},{
        		field : "loginNum",
        		title : "登录次数",
        		align : "center",
        		width : 60
        	},{
        		field : "loginErrorNum",
        		title : "错误登录次数",
        		align : "center",
        		width : 60
        	},{
        		field : "loginMac",
        		title : "访问机器的MAC地址",
        		align : "center",
        		width : 90
        	},{
        		field : "onlineFlag",
        		title : "当前登录状态",
        		align : "center",
        		width : 60,
        		formatter : function(val,row,index){
        			if (val == '0') {
						return '<img src=/jubao/publicresource/images/icons/no.png  style="height:16; width:16" valign="center"></img>'
								+ '离线';
					} else if (val == '1') {
						return '<img src=/jubao/publicresource/images/icons/yes.png style="height:18; width:18" valign="center"></img>'
								+ "在线";
					}
        		}
        	},{
        		field : "c",
        		title : "操作区",
        		width : 150,
        		align : "center",
        		formatter : function(value,row,index){
        			return returnStr="<a  href='javascript:operationList.bcLoginWin(\""+row.id+"\",\""+ row.loginNum+ "\",\""+ row.userName+ "\")'>查看本次登录</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  href='javascript:operationList.lcLoginWin(\""+row.id+"\",\""+ row.loginNum+ "\",\""+ row.userName+ "\")'>查看历次登录</a>";
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
				param.sort = "lastLoginTime";
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "pagedQuery",
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
//重置
operationList.reset = function(){
	$.f("div","operationList").f("form","operationListForm").form("reset");
}
//查询
operationList.queryList = function(){
	if (!$.f("div","operationList").f("form","operationListForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	var userName = $.f("form","operationListForm").f("input","userNameQ").textbox('getText');
	if(userName){
		params.filterTxt += "userName;";
		params.filterValue += "%"+userName+"%"+";";
	}
	var deptNameQ = $.f("form","operationListForm").f("input","deptNameQ").textbox('getValue');
	if(deptNameQ){
		params.filterTxt += "deptName;";
		params.filterValue += "%"+deptNameQ+"%"+";";
	}
	var operatIPQ = $.f("form","operationListForm").f("input","operatIPQ").textbox('getValue');
	if(operatIPQ){
		params.filterTxt += "loginIp;";
		params.filterValue += "%"+operatIPQ+"%"+";";
	}
	// 获取的起始时间   
	var startTimeValue = $.f("form","operationListForm").f("input","stateTime_startQ").datebox("getValue");
	if (startTimeValue) {
		params.filterTxt += "lastLoginTime;";
		params.filterValue += startTimeValue + ";";
		params.startDate = startTimeValue + " 00:00:00";
	}
	var endTimeValue = $.f("form","operationListForm").f("input","stateTime_endQ").datebox("getValue");
	if(endTimeValue){
		params.endDate = endTimeValue + " 23:59:59";
	}
	$.f("table","operationListGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("table","operationListGrid").datagridSinosoft("load","/jubao/log/login-log!pagedQuery.action");
}
//查看本次登录
operationList.bcLoginWin = function(id,loginNum,userName){
	var params = {};
	params.filterTxt = "loginLogId;loginNum";
	params.filterValue = id+ ';' +loginNum;
	params.loginInfoId = id;
	
	$.f("div", "bcLoginDiv").window({
		title : "用户操作信息",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		width:1300,
		height:600,
		onOpen : function(){
			var bcLoginGrid = $.f("div","bcLoginDiv").f("table","bcLoginGrid");
			bcLoginGrid.expandGridSinosoft({
				body:{
					onExpandRow : function(index, row) {
						$("#" + row.itemid + index).html("<div id=\""+row.itemid+index+"p\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
						$.parser.parse("#" + row.itemid + index);
						$("#"+row.itemid+index+"p").datagridSinosoft({
							body : {
								url : '/jubao/log/operation-data-log!operationDataLogQuery.action',
								queryParams : {
									filterTxt : "operationLog.id",
									filterValue : row.id
								},
								fitColumns : true,
								border : false,
								fit : false,
								striped : true,
								height : 180,
								pagination : true,
								singleSelect : false,
								pageSize : 3,
								frozenColumns : [[{
									field : 'itemid',
									align : 'center',
									hidden : true
								}]],
								columns : [[{
									field : 'dataDesc',
									title : "操作数据描述",
									width : 100,
									align : 'center'
								}, {
									field : 'dataValue',
									title : "操作数据值",
									width : 100,
									align : 'center'
								}, {
									title : "数据类型",
									field : 'dataTypeName',
									width : 100,
									align : 'center'
								}]],
								loadFilter : function(data) {
									data.rows = data.result;
									return data;
								},
								onBeforeLoad : function(param) {
									param.start = (param.page - 1) * param.rows;
									param.limit = param.rows
								},
								onLoadSuccess : function(data) {
									var params={
										operationFuncCode : "operationDataLogQuery",
										operationFuncName : "查看"+userName+"本次登录轨迹",
										operationBtnName : "查看本次登录",
										operationDesc : "查看"+userName+"本次登录轨迹",
										operationTypeCode : OperationType.QUERY,
										operationTypeName : OperationType.QUERY_NAME,
										enableDataLog :true
									}
									saveOperaLog(params);
								},
								pageList : [3, 6, 9]
							}
						});
					},
					url:sessionlostName+ '/log/operation-log!operationLogQuery.action',
					queryParams : params,
					border : false,
					singleSelect : true,
					checkbox : false,
					columns:[[{
		        		field : "operationFuncName",
		        		title : "操作功能",
		        		align : "center",
		        		width : 80,
		        		formatter : function(value,row,index){
		        			return value.trim();
		        		}
		        	},{
		        		field : "operationBtnName",
		        		title : "操作按钮",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "operationTime",
		        		title : "操作时间",
		        		align : "center",
		        		width : 150
		        	},{
		        		field : "operationDesc",
		        		title : "操作描述",
		        		align : "center",
		        		width : 150
		        	},{
		        		field : "operationResultName",
		        		title : "操作结果",
		        		align : "center",
		        		width : 100,
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
							operationFuncCode : "operationLogQuery",
							operationFuncName : "查看"+userName+"本次登录列表",
							operationBtnName : "查看本次登录",
							operationDesc : "查看"+userName+"本次登录列表",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
					}
				}
			});
		}
	});
	$.f("div", "bcLoginDiv").window("open");
}
//查看历次登录
operationList.lcLoginWin = function(id,loginNum,userName){
	var params = {};
	params.filterTxt = "log";
	params.filterValue = id;
	
	$.f("div", "lcLoginDiv").window({
		title : "用户登录信息",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		width:1300,
		height:530,
		loginLogId : id,
		onOpen : function(){
			var lcLoginGrid = $.f("div","lcLoginDiv").f("table","lcLoginGrid");
			lcLoginGrid.datagridSinosoft({
				toolbar:'.lctool',
				body:{
					url:sessionlostName+ '/log/previous-login-log!pageQueryPreviousLoginLog.action',
					queryParams : params,
					border : false,
					singleSelect : true,
					checkbox : false,
					sortType : 'DESC',
					columns:[[{
		        		field : "loginNum",
		        		hidden : true
		        	},{
		        		field : "loginIp",
		        		title : "登录IP",
		        		align : "center",
		        		width : 60
		        	},{
		        		field : "loginTime",
		        		title : "登录时间",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "logoutTime",
		        		title : "退出时间",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "c",
		        		title : "操作轨迹",
		        		align : "center",
		        		width : 100,
		        		formatter : function(val,row,index){
		        			return returnStr="<a  href='javascript:operationList.lcTrackWin(\""+row.loginNum+"\",\""+userName+ "\")'>查看操作轨迹</a>";
		        		}
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
							operationFuncCode : "pageQueryPreviousLoginLog",
							operationFuncName : "查询"+userName+"历次登录列表",
							operationBtnName : "查看历次登录",
							operationDesc : "查询"+userName+"历次登录列表",
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
	$.f("div", "lcLoginDiv").window("open");
}
//点击历次登录信息窗口的重置
operationList.lcReset = function(){
	$.f("div","lcLoginDiv").f("form","lcForm").form("reset");
}
//点击历次登录信息窗口的查询
operationList.lcQyery = function(){
	var id = $.f("div", "lcLoginDiv").panel("options").loginLogId;
	var params = {};
	params.filterTxt = "log;";
	params.filterValue = id+";";
	
	var logIpQ = $.f("form","lcForm").f("input","logIpQ").textbox('getValue');
	if(logIpQ){
		params.filterTxt += "loginIp;";
		params.filterValue += logIpQ+";";
	}
	
	var startTimeValue = $.f("form","lcForm").f("input","loginbegin").datebox("getValue");
	if (startTimeValue) {
		params.filterTxt += "loginTime;";
		params.filterValue += startTimeValue + " 00:00:00" + ";";
		params.startDate = startTimeValue + " 00:00:00;";
		
		if($.f("form","lcForm").f("input","loginend").datebox("getValue")){
			var endTimeValue = $.f("form","lcForm").f("input","loginend").datebox("getValue");
			params.filterTxt += "logoutTime;";
			params.filterValue += endTimeValue + " 23:59:59";
		}
	}
	
	$.f("table","lcLoginGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("table","lcLoginGrid").datagridSinosoft("load","/jubao/log/previous-login-log!pageQueryPreviousLoginLog.action");
}

//历次登录的查看操作轨迹
operationList.lcTrackWin = function(loginNum,userName){
	var params = {};
	params.filterTxt = "loginNum;loginLogId";
	params.filterValue = loginNum+ ';' + $.f("div", "lcLoginDiv").panel("options").loginLogId;;
	
	$.f("div", "lcTrackDiv").window({
		title : "用户操作信息",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		width:1300,
		height:600,
		onOpen : function(){
			var bcLoginGrid = $.f("div","lcTrackDiv").f("table","lcTrackGrid");
			bcLoginGrid.expandGridSinosoft({
				body:{
					onExpandRow : function(index, row) {
						$("#" + row.itemid + index).html("<div id=\""+row.itemid+index+"p\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
						$.parser.parse("#" + row.itemid + index);
						$("#"+row.itemid+index+"p").datagridSinosoft({
							body : {
								url : '/jubao/log/operation-data-log!operationDataLogQuery.action',
								queryParams : {
									filterTxt : "operationLog.id",
									filterValue : row.id
								},
								fitColumns : true,
								border : false,
								fit : false,
								striped : true,
								height : 180,
								pagination : true,
								singleSelect : false,
								pageSize : 3,
								frozenColumns : [[{
									field : 'itemid',
									align : 'center',
									hidden : true
								}]],
								columns : [[{
									field : 'dataDesc',
									title : "操作数据描述",
									width : 100,
									align : 'center'
								}, {
									field : 'dataValue',
									title : "操作数据值",
									width : 100,
									align : 'center'
								}, {
									title : "数据类型",
									field : 'dataTypeName',
									width : 100,
									align : 'center'
								}]],
								loadFilter : function(data) {
									data.rows = data.result;
									return data;
								},
								onBeforeLoad : function(param) {
									param.start = (param.page - 1) * param.rows;
									param.limit = param.rows
								},
								onLoadSuccess : function(data) {
									var params={
										operationFuncCode : "operationDataLogQuery",
										operationFuncName : "历次登录查看"+userName+"指定时间段的操作轨迹",
										operationBtnName : "查看操作轨迹",
										operationDesc : "历次登录查看"+userName+"指定时间段的操作轨迹",
										operationTypeCode : OperationType.QUERY,
										operationTypeName : OperationType.QUERY_NAME,
										enableDataLog :true
									}
									saveOperaLog(params);
								},
								pageList : [3, 6, 9]
							}
						});
					},
					url:sessionlostName+ '/log/operation-log!operationLogQuery.action',
					queryParams : params,
					border : false,
					singleSelect : true,
					checkbox : false,
					pageSize : 20,
					columns:[[{
		        		field : "operationFuncName",
		        		title : "操作功能",
		        		align : "center",
		        		width : 80,
		        		formatter : function(value,row,index){
		        			return value.trim();
		        		}
		        	},{
		        		field : "operationBtnName",
		        		title : "操作按钮",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "operationTime",
		        		title : "操作时间",
		        		align : "center",
		        		width : 150
		        	},{
		        		field : "operationDesc",
		        		title : "操作描述",
		        		align : "center",
		        		width : 150
		        	},{
		        		field : "operationResultName",
		        		title : "操作结果",
		        		align : "center",
		        		width : 100,
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
							operationFuncCode : "operationLogQuery",
							operationFuncName :  "历次登录查看"+userName+"指定时间段的操作",
							operationBtnName : "查看操作轨迹",
							operationDesc : "历次登录查看"+userName+"指定时间段的操作",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
					}
				}
			});
		}
	});
	$.f("div", "lcTrackDiv").window("open");
}