$.SUI("permissions");
var row = "";
var nodeId;  

//初始化部门信息、人员列表、角色列表
permissions.init = function(){
	var permissionsGrid = $.f("div","centerDiv").f("table","permissionsGrid");
	var authortyEastGrid = $.f("div","eastDiv").f("table","authortyEastGrid");
	var params = {};//人员的参数
	params.filterTxt = "";
	params.filterValue = "";
	
	var pars = {};//角色的参数
	pars.filterTxt = "";
	pars.filterValue = "";
	
	var par = {//部门的参数
		purpose:'',
		invalidFlag:1,
		node:-1
	};
	$.f("ul","permissionDept").tree({
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
			permissionsGrid.datagridSinosoft("gridoptions").queryParams = params;
			permissionsGrid.datagridSinosoft("load");
			
			pars.filterTxt = "organId";
			pars.filterValue = node.id;
			authortyEastGrid.datagridSinosoft("gridoptions").queryParams = pars;
			authortyEastGrid.datagridSinosoft("load");
		},
		onLoadSuccess: function(data){
			var params={
				operationFuncCode : "getOrganColumnsTree",
				operationFuncName : "查询权限查看部门信息树列表",
				operationBtnName : "查询",
				operationDesc : "查询权限查看部门信息树列表",
				operationTypeCode : OperationType.QUERY,
				operationTypeName : OperationType.QUERY_NAME,
				enableDataLog :true
			}
			saveOperaLog(params);
		}
	});
	
	permissionsGrid.datagridSinosoft({
		toolbar:"div[idFlag=centerTool]",
		body:{
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
        		width : 100,
        		formatter : function(value,row,index){
					 return "<span title='" + value + "'>" + value + "</span>";  
				}
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
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
				param.dir = "ASC";
			},
			onClickCell : function(index, field, value){
				var rows = permissionsGrid.datagrid('getRows');
				var row = rows[index];
				if(field=="Person_CName"){
					permissions.lookPermission(row.Person_ID);
				}
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "queryAuthorityUserList",
					operationFuncName : "查询权限查看用户列表",
					operationBtnName : "查询",
					operationDesc : "查询权限查看用户列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		}
	});
	
	authortyEastGrid.datagridSinosoft({
		body:{
			url:sessionlostName+ '/authority/authority-role!searchRole.action',
			queryParams : pars,
			border : false,
			singleSelect : true,
			checkbox : false,
			columns:[[{
        		field : "roleCode",
        		hidden : true
        	},{
        		field : "roleName",
        		title : "角色名称",
        		align : "center",
        		width : 100
        	},{
        		field : "description",
        		title : "角色描述",
        		align : "center",
        		width : 100
        	}]],
			loadFilter:function(data){
				data.rows =  data.result;
				return data;
			},
			onCheck:function(index,row){
				$.f("div","permissionsDiv").f("ul","permissionsmenutree").tree({
					url:sessionlostName+ "/authority/authority-menu!showAuthorityMenuTree.action",
					queryParams:{
						roleId:row.id,
						node:-1
					}
				})
			},
			onBeforeLoad:function(param){
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
				param.dir = "ASC";
			},
			onClickRow : function(index, row){
				$.f("div","eastTool").f("a","update").setdisabledEvent(false);
			},
			onLoadSuccess : function(data){
				var params={
					operationFuncCode : "searchRole",
					operationFuncName : "查询权限查看角色列表",
					operationBtnName : "查询",
					operationDesc : "查询权限查看角色列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
				$.f("div","eastTool").f("a","update").setdisabledEvent(true);
			}
		}
	});
}
//查询人员列表
permissions.queryPerson = function(){
	if (!$.f("div","centerDiv").f("form","centerForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	
	var userName = $.f("div","centerDiv").f("input","queryuserenameHistory").textbox('getValue');
	if(userName){
		params.filterTxt = "personName;";
		params.filterValue = "%"+userName+"%;";
	}
	$.f("div","centerDiv").f("table","permissionsGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("div","centerDiv").f("table","permissionsGrid").datagridSinosoft("load","/jubao/authority/authority-user-role!queryAuthorityUserList.action");
}
//查询角色列表
permissions.queryAuthorty = function(){
	if (!$.f("div","eastDiv").f("form","eastForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	var params = {};
	params.filterTxt = "";
	params.filterValue = "";
	
	
	var roleName = $.f("div","eastDiv").f("input","queryrolename").textbox('getValue');
	if(roleName){
		params.filterTxt = "roleName;";
		params.filterValue = "%"+roleName+"%";
	}
	$.f("div","eastDiv").f("table","authortyEastGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("div","eastDiv").f("table","authortyEastGrid").datagridSinosoft("load","/jubao/authority/authority-role!searchRole.action");
}
//人员重置
permissions.resetPerson = function(){
	$.f("div","centerDiv").f("input","queryuserenameHistory").textbox("setValue","");
}
//角色重置
permissions.resetAuthorty = function(){
	$.f("div","eastDiv").f("input","queryrolename").textbox("setValue","");
}
//点击人员姓名弹窗口
permissions.lookPermission = function(id){
	var params = {};
	params.filterTxt = "userId";
	params.filterValue = id;
	
	$.f("div","eastDiv").f("table","authortyEastGrid").datagridSinosoft("gridoptions").queryParams = params;
	$.f("div","eastDiv").f("table","authortyEastGrid").datagridSinosoft("load","/jubao/authority/authority-role!searchRole.action");
	
	$.f("div", "permissionWin").window({
		title : "授权历史记录查询",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		fit:true,
		onOpen : function(){
			$.f("ul","permissionMenu").tree({
				lines:true,
				url:sessionlostName+"/authority/authority-menu!getUserAuthorityMenuTree.action",
				queryParams : {
					userId:id,
					node:-1
				},
				onLoadSuccess: function(data){
					var params={
						operationFuncCode : "getUserAuthorityMenuTree",
						operationFuncName : "查询授权历史记录功能菜单",
						operationBtnName : "查询",
						operationDesc : "查询授权历史记录功能菜单",
						operationTypeCode : OperationType.QUERY,
						operationTypeName : OperationType.QUERY_NAME,
						enableDataLog :true
					}
					saveOperaLog(params);
				}
			});
			
			var permissionMenuGrid = $.f("div","permissionWin").f("table","permissionMenuGrid");
			permissionMenuGrid.datagridSinosoft({
				body:{
					url:sessionlostName+ '/authority/authority-data!userAuthorityGridData.action',
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
						param.start=(param.page-1)*param.rows;
						param.limit=param.rows;
						param.sort="fieldEName";
						param.dir = "ASC";
					},
					onLoadSuccess: function(data){
						var params={
							operationFuncCode : "userAuthorityGridData",
							operationFuncName : "查询授权历史记录详细信息",
							operationBtnName : "查询",
							operationDesc : "查询授权历史记录详细信息",
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
	$.f("div", "permissionWin").window("open");
}
//修改
permissions.updateAuthorty= function(){
	$.f("div","updatePermissionsWin").window({
	    width:1020,  
	    height:730,
	    title:"修改角色信息", 
	    modal:true,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		closed:true,
		onClose: function(){
			row = "";
		}
	});
	permissions.initGrid();//初始化数据权限列表
	permissions.showTree ();
}
//修改的时候load树
permissions.showTree = function(){
	var row = $.f("div","permissionsDiv").f("table","authortyEastGrid").datagridSinosoft("getChecked");
	$.ajax({
		url:sessionlostName+ "/authority/authority-role!getLoadData.action",
		type:"post",
		data:{
			id:row[0].id
		},
		success:function(data){
			var params={
				operationFuncCode : "getLoadData",
				operationFuncName : "权限查看修改回显功能菜单树和基本信息",
				operationBtnName : "查询",
				operationDesc : "权限查看修改回显功能菜单树和基本信息",
				operationTypeCode : OperationType.QUERY,
				operationTypeName : OperationType.QUERY_NAME,
				enableDataLog :true
			}
			saveOperaLog(params);
							
			obj = $.toJSON(data);
			obj.appSystem=obj.system;
			
			$.f("div","updatePermissionsWin").f("ul","menutreegn").tree("reload");
			$.f("div","updatePermissionsWin").find("form").form("reset");
			$.f("div","updatePermissionsWin").find("form").form("load",obj);
			
			//修改时不清除窗口的复选框
			$.f("div","updatePermissionsWin").panel("options").clearFlag=false;
			$.f("div","updatePermissionsWin").window("open");
			
			var node = $.f("div","permissionsDiv").f("ul","permissionsmenutree").tree("getRoots")[0];
			var childeren  =[];
			var getChildren = function(node){
				nodechildren = node.children;
				$(nodechildren).each(function(){
					if(this.children){
						getChildren(this)
					}else{
						childeren.push(this)
					}
				})
			}
			if(node.children){
				getChildren(node);
			}
			updatePermissionsWin.children = childeren;
			
			var params = {
				filterValue:obj.id,
				filterTxt:'roleId'
			};
			$.f("div","updatePermissionsWin").f("table","permissionsGrid").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","updatePermissionsWin").f("table","permissionsGrid").datagridSinosoft("load");
		}
	});
}
//代开新增窗口初始化权限列表
permissions.initGrid = function(){
	var params = {};
	params.filterTxt = "roleId";
	params.filterValue = "";
	var permissionsGrid = $.f("div","updatePermissionsWin").f("table","permissionsGrid");
	permissionsGrid.datagridSinosoft({
		body:{
			url:sessionlostName+ '/authority/authority-data!buildAuthorityGridData.action',
			queryParams : params,
			border : false,
			singleSelect : true,
			checkbox : false,
			columns:[[{
        		field : "groupName",
        		title : "表名称",
        		align : "center",
        		width : 100
        	},{
        		field : "fieldCName",
        		title : "字段中文名",
        		align : "center",
        		width : 100
        	},{
        		idFlag : "selectAble",
        		field : "selectAble",
        		title : "读取权限",
        		align : "center",
        		width : 80,
        		formatter : function(value,row,index){
        			 if (value == true || value == 'true') {
				   		return "<input name='selectAble' type='checkbox' checked onclick=permissions.onAuthorityCheckBoxClick("+ index +",'selectAble','false')>";
			         }else {
				   		return "<input name='selectAble' type='checkbox' onclick=permissions.onAuthorityCheckBoxClick("+ index +",'selectAble','true')>";
			         }
        		},
        		editor: {
        			type : 'checkbox'
        		}
        	},{
        		field : "addRestrictValue",
        		title : "权限值",
        		align : "center",
        		width : 80
        	},{
        		idFlag : "restrictType",
        		field : "restrictType",
        		title : "处理空值",
        		align : "center",
        		width : 80,
        		formatter : function(value,row,index){
        			if (value == true || value == 'true') {
				   		return "<input name='restrictType' type='checkbox' checked onclick=permissions.onAuthorityCheckBoxClick("+ index +",'restrictType','false')>";
			        }else {
				   		return "<input name='restrictType' type='checkbox' onclick=permissions.onAuthorityCheckBoxClick("+ index +",'restrictType','true')>";
			        }
        		},
        		editor: {
        			type : 'checkbox'
        		}
        	}]],
			loadFilter:function(data){
				data.rows =  data.result;
				return data;
			},
			onBeforeLoad:function(param){
				param.start=(param.page-1)*param.rows;
				param.limit=param.rows;
				param.sort="fieldEName";
				param.dir = "ASC";
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "buildAuthorityGridData",
					operationFuncName : "修改角色查看数据权限列表",
					operationBtnName : "查询",
					operationDesc : "修改角色查看数据权限列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		}
	});
}
permissions.onAuthorityCheckBoxClick = function(index,column,value){
	var rows = $.f("div","updatePermissionsWin").f("table","permissionsGrid").datagridSinosoft('getRows');
    var row = rows[index];
    
    var td = $('.datagrid-body td[field='+column+']')[index];
    var div = $(td).find('div')[0];
    $($(div).find('input[name='+column+']')[0]).attr("checked", value);
    
    row[column] = value;
}
//角色修改窗口的重置
permissions.reset=function(){
	permissions.showTree();
}
//保存
permissions.submit=function(){
	if (!$.f("form","permissionsForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>请检查表单填写是否正确', 3000);
		return;
	}else{
		var checkarr=$.f("ul","menutreegn").tree("getChecked");
		var nodes = $.f("ul","menutreegn").tree("getChecked","indeterminate");	// 获取不确定的节点
		$(nodes).each(function(){
			checkarr.push(this)
		});
		
		var changeRow = $.f("div","updatePermissionsWin").f("table","permissionsGrid").datagridSinosoft('getRows');
		var saveData = "";
			
		saveData = "[";
		for (var i = 0; i < changeRow.length; i++) {
			var json = changeRow[i];
			if(json.restrictType!=''||json.selectAble!=''){
				saveData += JSON.stringify(json) + ",";
			}
		}
		if (saveData.length > 1) {
			saveData = saveData.substr(0,saveData.length - 1);
		}
		saveData += "]";
		$.ajax({
			url:sessionlostName+ "/authority/authority-role!judgeExist.action",
			type:"post",
			data:{
				filterTxt:"roleName",
				filterValue:$.f("div","updatePermissionsWin").find("input[name='roleName']").val(),
				id:$.f("div","updatePermissionsWin").find("input[name='id']").val()
			},
			success:function(data){
				if(data=="false"){
					$.f("div","updatePermissionsWin").find("form").form({  
						type:"post",
					    url:sessionlostName+ "/authority/authority-role!save.action",    
					    onSubmit: function(par){
					    	var validate=$.f("div","updatePermissionsWin").find("form").form("validate");
					    	var m="";
					    	var pm="";
					    	$(checkarr).each(function(index){
					    		m+=","+this.id;
					    		var node=	$.f("ul","menutreegn").tree("getParent",this.target);
					    		if(node){
					    			pm+=","+node.id;
					    		}else{
					    			pm+=","+"-1";
					    		}
					    	})
					    	if(m.length>0){
					    		m=m.substring(1);
					    	}
							if(pm.length>0){
								pm=pm.substring(1);
					    	}
					    	par.menuId=m;
					    	par.parentMenuId=pm;
					    	par.invalidFlag=1;
					    	par.expire="";
					    	par.data=saveData;
					    	
					    	return validate;
					    },    
					    success:function(data){
					    	var params={
								operationFuncCode : "save",
								operationFuncName : "修改权限查看的角色信息",
								operationBtnName : "保存",
								operationResultCode : "1",
								operationResultName : "成功",
								operationDesc : "修改权限查看的角色信息",
								operationTypeCode : OperationType.MODIFY,
								operationTypeName : OperationType.MODIFY_NAME,
								operationDataDesc : "修改权限查看的角色信息",
								operationDataValue : "角色编码："+$.f("div","updatePermissionsWin").find("form").find("input[name='roleCode']").val()+",角色名称："+$.f("div","updatePermissionsWin").find("form").f("input","roleName").textbox("getValue"),		
								enableDataLog :true
							}
							saveOpeationRecord(params);
					    	
					    	if($.f("div","updatePermissionsWin").find("input[name='id']").val().length>0){
					    		dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色修改成功！', 2500);
					    	}else{
					    		dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色新增成功！', 2500);
					    	}
					     	$.f("div","permissionsDiv").f("table","authortyEastGrid").datagridSinosoft("reload");
					     	$.f("div","updatePermissionsWin").window("close")
					     	$.f("div","permissionsDiv").find(".button").eq(1).setdisabledEvent(true);
							$.f("div","permissionsDiv").find(".button").eq(2).setdisabledEvent(true);
							$.f("div","permissionsDiv").f("ul","permissionsmenutree").html("");
					    }    
					});
					$.f("div","updatePermissionsWin").find("form").submit();  
				}else{
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色已经存在！', 2500);
				}
			}
		});
	}
}