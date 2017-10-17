$.SUI("record");

record.createList = function(){
	var recordListGrid = $.f("div","recordListDiv").f("table","recordListGrid");
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	recordListGrid.datagridSinosoft({
		toolbar:'.recordTool',
		body:{
			url:sessionlostName+ '/authorityAudit/authority-authorization-info!pagedQuery.action',
			border : false,
			singleSelect : true,
			checkbox : false,
			queryParams : params,
			frozenColumns : [[{
        		field : "id",
        		hidden : true
        	}]],
			columns:[[{
        		field : "mainOperatorName",
        		title : "操作人",
        		align : "center",
        		width : 60
        	},{
        		field : "mainOperatorOrganization",
        		title : "操作人所属部门",
        		align : "center",
        		width : 100
        	},{
        		field : "operateType",
        		title : "操作方式",
        		align : "center",
        		width : 80,
        		formatter : function(val,row,index){
        			if(val == 'Add'){
						val =  '增加';
					}else if(val == 'Del'){
						val =  '删除';
					}else if(val == 'Update'){
						val =  '更新';
					}
					return val;
        		}
        	},{
        		field : "clientOperatorName",
        		title : "被授权人名称",
        		align : "center",
        		width : 80
        	},{
        		field : "clientOperatorOrganization",
        		title : "被授权人所属部门",
        		align : "center",
        		width : 100
        	},{
        		field : "operateDate",
        		title : "操作时间",
        		align : "center",
        		width : 100,
        		formatter : function(val,row,index){
        			return val.substr(0,10);
        		}
        	},{
        		field : "roleName",
        		title : "授予角色名称",
        		align : "center",
        		width : 90
        	}]],
			loadFilter:function(data){
				data.rows =  data.result;
				return data;
			},
			onBeforeLoad:function(param){
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
				param.dir = "ASC";
				param.sort = "operateDate";
			},
			onSelect : function(index, row){
				record.lookRecord(row.id,row.clientOperatorName);	
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "pagedQuery",
					operationFuncName : "查询授权记录列表",
					operationBtnName : "查询",
					operationDesc : "查询授权记录列表",
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
record.reset = function(){
	$.f("div","recordListDiv").f("form","recordForm").form("reset");
}
//查询
record.queryList = function(){
	if (!$.f("div","recordListDiv").f("form","recordForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	
	var fpValue = $.f("div","recordListDiv").f("form","recordForm").f("input","field_property_value").textbox('getValue');
	if(fpValue){
		var txt = $.f("div","recordListDiv").f("form","recordForm").f("input","authorityAudit_field_property").combobox('getText');
		if(txt){
			if(txt=="被授权人名称"){
				params.filterTxt += "clientOperatorName;";
			}else if(txt=="操作人名称"){
				params.filterTxt += "mainOperatorName;";
			}
			params.filterValue += "%"+fpValue.trim()+"%"+";";
		}else{
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请选择查询字段！', 3000);
			return;
		}
	}
	
	var startTimeValue = $.f("div","recordListDiv").f("form","recordForm").f("input","search_startDate").datebox("getValue");
	if (startTimeValue) {
		params.queryStartDate =  new Date(startTimeValue).getTime();
	}
	var endTimeValue = $.f("div","recordListDiv").f("form","recordForm").f("input","search_endDate").datebox("getValue");
	if(endTimeValue){
		params.queryEndDate = new Date(endTimeValue).getTime();
	}
	$.f("div","recordListDiv").f("table","recordListGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("div","recordListDiv").f("table","recordListGrid").datagridSinosoft("load","/jubao/authorityAudit/authority-authorization-info!pagedQuery.action");
}
//授权历史记录查询
record.lookRecord = function(id,clientOperatorName){
	var params = {};
	params.filterTxt = "authorityAuthorizationInfo.id";
	params.filterValue = id;
	
	$.f("div", "lookHisRecordDiv").window({
		title : "授权历史记录查询",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		fit:true,
		onOpen : function(){
			$.f("ul","accessMenu").tree({
				lines:true,
				url:sessionlostName+"/authorityAudit/authority-history!getUserAuthorizationHistoryMenuTree.action",
				queryParams : {
					authorizationId	: id
				},
				onLoadSuccess: function(data){
						var params={
							operationFuncCode : "getUserAuthorizationHistoryMenuTree",
							operationFuncName : "授权记录模块"+clientOperatorName+"的功能菜单查询",
							operationBtnName : "查询",
							operationDesc : "授权记录模块"+clientOperatorName+"的功能菜单查询",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
					}
			});
			var lookHisRecordGrid = $.f("div","lookHisRecordDiv").f("table","lookHisRecordGrid");
			lookHisRecordGrid.datagridSinosoft({
				body:{
					url:sessionlostName+ '/authorityAudit/authority-data-history!pagedQuery.action',
					queryParams : params,
					border : false,
					singleSelect : true,
					checkbox : false,
					columns:[[{
		        		field : "groupName",
		        		title : "表名称",
		        		align : "center",
		        		width : 60
		        	},{
		        		field : "fieldCName",
		        		title : "字段中文名",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "selectAble",
		        		title : "读取权限",
		        		align : "center",
		        		width : 100
		        	},{
		        		field : "addRestrictValue",
		        		title : "权限值",
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
							operationFuncCode : "getOrganColumnsTree",
							operationFuncName : "授权记录模块"+clientOperatorName+"权限详细信息查询",
							operationBtnName : "查询",
							operationDesc : "授权记录模块"+clientOperatorName+"权限信息查询",
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
	$.f("div", "lookHisRecordDiv").window("open");
}