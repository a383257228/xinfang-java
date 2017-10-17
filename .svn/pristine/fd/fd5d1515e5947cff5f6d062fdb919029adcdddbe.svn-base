$.SUI("hisAuthorty");

hisAuthorty.createList = function(){
	var hisAuthortyGrid = $.f("div","hisAuthortyDiv").f("table","hisAuthortyGrid");
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	var par = {
		purpose:'',
		invalidFlag:1,
		node:-1
	};
	$.f("ul","hisAuthortyMenu").tree({
		lines:true,
		url:sessionlostName+"/petition/petition-organ-info!getOrganColumnsTree.action",
		queryParams : par,
		loadFilter : function(data){
			$(data).each(function(index){
				if(!data[index].leaf){
					data[index].state="closed"
				}else{
					delete data[index].state;
				}
			})
			return data;		
		},
		onBeforeLoad:function(node,par){
			if(node){
				if(node.id.trim() != '310000'){
					par.purpose='2';
   				}
   				par.node=node.id;
   			}
			par.invalidFlag="1";
		},
		onClick : function(node){
			params.filterTxt = "organizationInfoID;";
			params.filterValue = node.id+";";
			hisAuthortyGrid.expandGridSinosoft("gridoptions").queryParams = params;
			hisAuthortyGrid.expandGridSinosoft("load");
		},
		onLoadSuccess: function(data){
			var params={
				operationFuncCode : "getOrganColumnsTree",
				operationFuncName : "历史权限查看部门树列表",
				operationBtnName : "查询",
				operationDesc : "历史权限查看部门树列表",
				operationTypeCode : OperationType.QUERY,
				operationTypeName : OperationType.QUERY_NAME,
				enableDataLog :true
			}
			saveOperaLog(params);
		}
	});
	
	
	hisAuthortyGrid.expandGridSinosoft({
		body:{
			onExpandRow : function(index, row) {
				$("#" + row.itemid + index).html("<div id=\""+row.itemid+index+"p\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
				$.parser.parse("#" + row.itemid + index);
				$("#"+row.itemid+index+"p").datagridSinosoft({
					body : {
						url : '/jubao/authorityAudit/authority-authorization-info!pagedQuery.action',
						queryParams : {
							filterTxt : "clientOid",
							filterValue : row.Person_ID
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
							field : 'operateType',
							title : "操作方式",
							width : 100,
							align : 'center',
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
							field : 'operateDate',
							title : "操作时间",
							width : 100,
							align : 'center'
						},{
							title : "操作人",
							field : 'mainOperatorName',
							width : 100,
							align : 'center'
						},{
							title : "操作人所属部门",
							field : 'mainOperatorOrganization',
							width : 100,
							align : 'center'
						},{
							title : "授予角色名称",
							field : 'roleName',
							width : 100,
							align : 'center'
						}]],
						loadFilter:function(data){
							data.rows =  data.result;
							return data;
						},
						onBeforeLoad:function(param){
							param.start=(param.page-1)*param.rows
							param.limit=param.rows
						},
						onLoadSuccess : function(data) {
							var params={
								operationFuncCode : "getOrganColumnsTree",
								operationFuncName : "历史权限查看部门用户:"+row.Person_CName+"的操作轨迹列表",
								operationBtnName : "查询",
								operationDesc : "历史权限查看部门用户:"+row.Person_CName+"的操作轨迹列表",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
							}
							saveOperaLog(params);
						},
						onSelect : function(index, row){
							record.lookRecord(row.id);
						},
						pageList : [3, 6, 9]
					}
				});
			},
			url:sessionlostName+ '/authority/authority-user-role!queryAuthorityUserList.action',
			queryParams : params,
			border : false,
			singleSelect : true,
			pageSize : 20,
			checkbox : false,
			columns:[[{
        		field : "Person_CName",
        		title : "用户名称",
        		align : "center",
        		width : 100
        	},{
        		field : "Org_Dept_Name",
        		title : "部门岗位",
        		align : "center",
        		width : 100
        	},{
        		field : "Person_EName",
        		title : "用户英文名",
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
					operationFuncCode : "queryAuthorityUserList",
					operationFuncName : "历史权限查看部门用户列表",
					operationBtnName : "查询",
					operationDesc : "历史权限查看部门用户列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		}
	});
	
}
//重置
hisAuthorty.reset = function(){
	$.f("div","hisAuthortyDiv").f("input","queryuserenameHistory").textbox("setValue","");
}
//查询
hisAuthorty.queryList = function(){
	if (!$.f("div","hisAuthortyDiv").f("form","hisForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	
	var userName = $.f("div","hisAuthortyDiv").f("input","queryuserenameHistory").textbox('getValue');
	if(userName){
		params.filterTxt = "personName;";
		params.filterValue = "%"+userName+"%;";
	}
	$.f("div","hisAuthortyDiv").f("table","hisAuthortyGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("div","hisAuthortyDiv").f("table","hisAuthortyGrid").datagridSinosoft("load","/jubao/authority/authority-user-role!queryAuthorityUserList.action");
}