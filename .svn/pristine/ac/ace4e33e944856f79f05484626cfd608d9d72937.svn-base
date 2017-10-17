$.SUI("roleSettingMan");
var row = "";
var flag = "";
//初始化
roleSettingMan.init = function(){
	$("#roleSettingMan").find(".button").eq(1).setdisabledEvent(true);
	$("#roleSettingMan").find(".button").eq(2).setdisabledEvent(true);
	//右面角色窗体
	$("#roleSettingMan").f("table","roleSettingGrid").datagridSinosoft({
		toolbar:".roleSettingManBar",
		body:{
			border:false,
			singleSelect:false,
			onBeforeLoad:function(par){
				par.start=(par.page-1)*par.rows;
				par.limit=par.rows;
			},
			loadFilter:function(data){
				data.rows=data.result
				return data;
			},
			singleSelect:true,
			url:sessionlostName+ "/authority/authority-role!pagedQuery.action",
			onCheck:function(index,row){
				$("#roleSettingMan").f("ul","rolemenutree").tree({
					url:sessionlostName+ "/authority/authority-menu!showAuthorityMenuTree.action",
					queryParams:{
						roleId:row.id,
						node:-1
					},
					onLoadSuccess: function(data){
						var params = {
							operationFuncCode : "showAuthorityMenuTree",
							operationFuncName : "查询角色所具有的功能菜单项",
							operationBtnName : "查询",
							operationDesc : "查询角色所具有的功能菜单项",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
					}
				});
				
				var params = {
					filterValue : row.id,
					filterTxt : 'roleId'
				};
				$.f("div","roleSettingMan").f("table","permissionsGridMain").datagridSinosoft("gridoptions").queryParams = params;
				$.f("div","roleSettingMan").f("table","permissionsGridMain").datagridSinosoft("load","/jubao/authority/authority-data!showRoleAuthorityGridData.action");
				
				$("#roleSettingMan").find(".button").eq(1).setdisabledEvent(false)
				$("#roleSettingMan").find(".button").eq(2).setdisabledEvent(false)
			},
			onBeforeSelect:function(index,row){
			},
			onLoadSuccess: function(data){
				var params = {
					operationFuncCode : "pagedQuery",
					operationFuncName : "查询角色列表",
					operationBtnName : "查询",
					operationDesc : "查询角色列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		},
		pagebar:{
			pageSize:20,
			pageList:[10,20,30,50],
			showPageList:true,
			beforePageText:"",
			afterPageText:"",
			displayMsg:""
		}
	});
	
	var params = {
		filterValue:'',
		filterTxt:''
	}
	var permissionsGridMain = $.f("div","roleSettingMan").f("table","permissionsGridMain");
	permissionsGridMain.datagridSinosoft({
		body:{
			url:sessionlostName+ '/authority/authority-data!showRoleAuthorityGridData.action',
			queryParams : params,
			border : false,
			singleSelect : true,
			checkbox : false,
			columns:[[{
        		field : "fieldCName",
        		title : "字段中文名",
        		align : "center",
        		width : 90
        	},{
        		idFlag : "selectAble",
        		field : "selectAble",
        		title : "读取权限",
        		align : "center",
        		width : 70,
        		formatter : function(value,row,index){
        			if(value==true||value=='true'){
						return "<img src=\"/jubao/images/ok.png\">";
					}else{
						return "<img src=\"jubao/images/error.png\">";
					}
        		},
        		editor: {
        			type : 'checkbox'
        		}
        	},{
        		field : "addRestrictValue",
        		title : "权限值",
        		align : "center",
        		width : 100
        	},{
        		idFlag : "restrictType",
        		field : "restrictType",
        		title : "处理空值",
        		align : "center",
        		width : 80,
        		formatter : function(value,row,index){
        			if(value==true||value=='true'){
						return "<img src=\"/jubao/images/ok.png\">";
					}else{
						return "<img src=\"jubao/images/error.png\">";
					}
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
					operationFuncCode : "showRoleAuthorityGridData",
					operationFuncName : "查询构建数据权限列表",
					operationBtnName : "查询",
					operationDesc : "查询构建数据权限列表",
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
//新增
roleSettingMan.add=function(){
	$.f("div","roleSetingWindow").window({
	    width:1010, 
	    height:700,
	    title:"新增角色", 
	    modal:true,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		closed:true,
		onOpen : function(){
			roleSettingMan.initGrid();
		},
		onClose : function(){
			row = "";
			flag = "";
		}
	});
	$.f("div","roleSetingWindow").f("ul","menutree").tree("reload");
	$.f("div","roleSetingWindow").find("form").form("reset");
	$.f("div","roleSetingWindow").form("load",{id:""})
	//新增时清除窗口的复选框
	$.f("div","roleSetingWindow").panel("options").clearFlag=true;
	$.f("div","roleSetingWindow").window("open");
};
//代开新增窗口初始化权限列表
roleSettingMan.initGrid = function(){
	var params = {};
	params.filterTxt = "roleId";
	params.filterValue = "";
	var dataPermissionsGrid = $.f("div","roleSetingWindow").f("table","dataPermissionsGrid");
	dataPermissionsGrid.datagridSinosoft({
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
				   		return "<input name='selectAble' type='checkbox' checked onclick=roleSettingMan.onAuthorityCheckBoxClick("+ index +",'selectAble','false')>";
			         }else {
				   		return "<input name='selectAble' type='checkbox' onclick=roleSettingMan.onAuthorityCheckBoxClick("+ index +",'selectAble','true')>";
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
				   		return "<input name='restrictType' type='checkbox' checked onclick=roleSettingMan.onAuthorityCheckBoxClick("+ index +",'restrictType','false')>";
			        }else {
				   		return "<input name='restrictType' type='checkbox' onclick=roleSettingMan.onAuthorityCheckBoxClick("+ index +",'restrictType','true')>";
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
				var params = {
					operationFuncCode : "buildAuthorityGridData",
					operationFuncName : "构建数据权限列表",
					operationBtnName : "查询",
					operationDesc : "构建数据权限列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		}
	});
}
//给选中的复选框
roleSettingMan.onAuthorityCheckBoxClick = function(index,column,value){
	var rows = $.f("div","roleSetingWindow").f("table","dataPermissionsGrid").datagridSinosoft('getRows');
    var row = rows[index];
    
    var td = $('.datagrid-body td[field='+column+']')[index];
    var div = $(td).find('div')[0];
    $($(div).find('input[name='+column+']')[0]).attr("checked", value);
    
    row[column] = value;
}
//修改
roleSettingMan.updateobj=function(){
	$.f("div","roleSetingWindow").window({
	    width:1020,    
	    title:"修改角色信息", 
	    modal:true,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		closed:true,
		onClose: function(){
			row = "";
			flag = "";
		}
	});
	roleSettingMan.initGrid();//初始化数据权限列表
	roleSettingMan.showTree ();
}
//修改的时候load树
roleSettingMan.showTree = function(){
	var row = $("#roleSettingMan").f("table","roleSettingGrid").datagridSinosoft("getChecked");
	$.ajax({
		url:sessionlostName+ "/authority/authority-role!getLoadData.action",
		type:"post",
		data:{
			id:row[0].id
		},
		success:function(data){
			var params={
				operationFuncCode : "loadData",
				operationFuncName : "角色设置修改load回显数据",
				operationBtnName : "查询",
				operationDesc : "load回显"+row.roleName+"的数据",
				operationTypeCode : OperationType.QUERY,
				operationTypeName : OperationType.QUERY_NAME,
				enableDataLog :true
			}
			saveOperaLog(params);
							
			obj = $.toJSON(data);
			obj.appSystem=obj.system;
			
			$.f("div","roleSetingWindow").f("ul","menutree").tree("reload");
			$.f("div","roleSetingWindow").find("form").form("reset");
			
			$.f("div","roleSetingWindow").find("form").form("load",obj);
			//修改时不清除窗口的复选框
			$.f("div","roleSetingWindow").panel("options").clearFlag=false;
			$.f("div","roleSetingWindow").window("open");
			var node=$("#roleSettingMan").f("ul","rolemenutree").tree("getRoots")[0];
			var childeren  =[];
			var getChildren=function(node){
				nodechildren=node.children;
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
			roleSetingWindow.children=childeren;
			
			flag = obj.id;
			
			var params = {
				filterValue:obj.id,
				filterTxt:'roleId'
			};
			$.f("div","roleSetingWindow").f("table","dataPermissionsGrid").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","roleSetingWindow").f("table","dataPermissionsGrid").datagridSinosoft("load");
		}
	});
}
//删除
roleSettingMan.remove=function(){
	dialog.showNormalDialog("提示", '确定要删除当前角色吗？', {
        ok: {
            name: "确认",
            click: function () {
            	var row=$("#roleSettingMan").f("table","roleSettingGrid").datagridSinosoft("getChecked");
    			$.ajax({
    				url:sessionlostName+ "/authority/authority-role!remove.action",
    				type:"post",
    				data:{
    					ids:row[0].id+","
    				},
    				success:function(data){
    					var params={
  			    			operationFuncCode : "remove",
  			    			operationFuncName : "删除角色",
  			    			operationBtnName : "删除",
  			    			operationResultCode : "1",
  			    			operationResultName : "成功",
  				      		operationDesc : "删除角色",
  				      		operationTypeCode : OperationType.DELETE,
  				      		operationTypeName : OperationType.DELETE_NAME,
  				      		operationDataDesc : "删除角色",
  				      		operationDataValue : "删除角色:"+row[0].roleName,   			
  			    			enableDataLog :true
  				      	}
  			   			saveOpeationRecord(params);
    					
    					var obj=eval("("+data+")");
    					if(typeof obj.success=="number"){
    						  dialog.showMiniDialog("self-style", "<i class=\"icon-faild\"></i> 删除失败，已经有"+obj.success+"个用户绑定了当前角色，请先解绑所有角色后重试", 2000);
    					}else{
    						 dialog.showMiniDialog("self-style", "<i class=\"icon-success\"></i> 角色已经被成功删除", 2000);
    						 $("#roleSettingMan").f("table","roleSettingGrid").datagridSinosoft("reload");
    					}
    				}
    			})
                dialog.removeDialog();
            }
        },
        cancel: {
            name: "取消",
            click: function () {
                dialog.removeDialog();
            }
        }
    });
}
//保存
roleSettingMan.submit=function(){
	if (!$.f("form","roleSetingForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>请检查表单填写是否正确', 3000);
		return;
	}else{
		var checkarr=$.f("ul","menutree").tree("getChecked");
		var nodes = $.f("ul","menutree").tree("getChecked","indeterminate");	// 获取不确定的节点
		$(nodes).each(function(){
			checkarr.push(this)
		});
		
		var changeRow = $.f("div","roleSetingWindow").f("table","dataPermissionsGrid").datagridSinosoft('getRows');
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
				filterValue:$.f("div","roleSetingWindow").find("input[name='roleName']").val(),
				id:$.f("div","roleSetingWindow").find("input[name='id']").val()
			},
			success:function(data){
				if(data=="false"){
					$.f("div","roleSetingWindow").find("form").form({  
						type:"post",
					    url:sessionlostName+ "/authority/authority-role!save.action",    
					    onSubmit: function(par){
					    	var validate=$.f("div","roleSetingWindow").find("form").form("validate");
					    	var m="";
					    	var pm="";
					    	$(checkarr).each(function(index){
					    		m+=","+this.id;
					    		var node=	$.f("ul","menutree").tree("getParent",this.target);
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
					    	if($.f("div","roleSetingWindow").find("input[name='id']").val().length>0){
					    		var params={
									operationFuncCode : "save",
									operationFuncName : "修改角色信息",
									operationBtnName : "保存",
									operationResultCode : "1",
									operationResultName : "成功",
									operationDesc : "修改角色信息",
									operationTypeCode : OperationType.MODIFY,
									operationTypeName : OperationType.MODIFY_NAME,
									operationDataDesc : "修改角色信息",
									operationDataValue : "角色名称："+$.f("div","roleSetingWindow").find("input[name='roleName']").val(),		
									enableDataLog :true
								}
								saveOpeationRecord(params);
					    		dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色修改成功！', 2500);
					    	}else{
					    		var params={
									operationFuncCode : "save",
									operationFuncName : "新增角色信息",
									operationBtnName : "保存",
									operationResultCode : "1",
									operationResultName : "成功",
									operationDesc : "新增角色信息",
									operationTypeCode : OperationType.ADD,
									operationTypeName : OperationType.ADD_NAME,
									operationDataDesc : "新增角色信息",
									operationDataValue : "角色名称："+$.f("div","roleSetingWindow").find("input[name='roleName']").val(),		
									enableDataLog :true
								}
								saveOpeationRecord(params);
					    		dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色新增成功！', 2500);
					    	}
					     	$("#roleSettingMan").f("table","roleSettingGrid").datagridSinosoft("reload");
					     	$.f("div","roleSetingWindow").window("close")
					     	$("#roleSettingMan").find(".button").eq(1).setdisabledEvent(true);
							$("#roleSettingMan").find(".button").eq(2).setdisabledEvent(true);
							$("#roleSettingMan").f("ul","rolemenutree").html("");
					    }    
					});
					$.f("div","roleSetingWindow").find("form").submit();  
				}else{
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色已经存在！', 2500);
				}
			}
		});
	}
}
//窗口重置
roleSettingMan.reset=function(){
	var params = {};
	if(flag!=""){
		roleSettingMan.showTree();
	}else{
		$.f("form","roleSetingForm").form("reset");
		params.filterTxt = "roleId";
		params.filterValue = "";
		$.f("div","roleSetingWindow").f("ul","menutree").tree("clearChecked");
		$.f("div","roleSetingWindow").f("table","dataPermissionsGrid").datagridSinosoft("gridoptions").queryParams = params;
		$.f("div","roleSetingWindow").f("table","dataPermissionsGrid").datagridSinosoft("load");
	}
}