$.SUI("deptSettingGrid");
//初始化部门列表
deptSettingGrid.init=function(){
	$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
	$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
	
	var params = {
		filterValue:"",
		filterTxt:'',
		purpose:'',
		node:-1	
	};
	$.f("table","departmentGrid").treegridSinosoft({
		toolbar:'.departmenttool',
		body:{
		    url:sessionlostName+'/petition/petition-organ-info!getOrganColumnsTree.action', 
		    queryParams : params,
		    idField:'id', 
		    treeField:'text', 
		    //加载之前执行
		    onBeforeLoad:function(row,parm){
	   			if(row){
	   				parm.node=row.id.trim();
	   			}else{
	   				parm.node=-1;
	   			}
		    },
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
			onClickRow:function(row){
				if(row.orgOrDept=="dept"){
					if(row.invalidFlag=="1"){
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(false);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(false);
					}else{
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(false);
					}
				}else if(row.orgOrDept=="org"){
					if(row.invalidFlag=="1"){
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(false);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
					}else{
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
					}
				}
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "getOrganColumnsTree",
					operationFuncName : "查询部门树列表",
					operationBtnName : "查询",
					operationDesc : "查询部门树列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		}
	})
}
//重置
deptSettingGrid.queryreset=function(){
	$.f("div","departmentGridDiv").f("form","departForm").form("reset");
	$.f("div","departmentGridDiv").find("input[idFlag='validFlag']").prop("checked", "checked");
};
//查询
deptSettingGrid.queryCondition=function(){
	var params = {};
	params.purpose = '';
	params.node = -1;
	params.filterTxt = "";
	params.filterValue = "";
	var flag="";
	var orgName = $.f("div","departmentGridDiv").f("input","deptName").textbox('getValue');
	var orgCode = $.f("div","departmentGridDiv").f("input","deptCode").textbox('getValue');
	if(orgName!=""||orgCode!=""){
		$.f("div","departmentGridDiv").find("input[idFlag='validFlag']:checked").each(function () {
			flag += $(this).find("input").context.value+";";
	    });
		if(flag){
			params.filterTxt += "invalidFlag;";
			params.filterValue +="1;";
		}else{
			params.filterTxt += "invalidFlag;";
			params.filterValue +="2;";
		}		
		if(orgName){
			params.filterTxt += "orgName;";
			params.filterValue += orgName+";";
		}
		if(orgCode){
			params.filterTxt += "orgCode;";
			params.filterValue += orgCode+";";
		}
	}else{
		params.filterTxt = "";
		params.filterValue = "";
	}
	
	$.ajax({
		 url:sessionlostName+"/petition/petition-organ-info!getOrganColumnsTree.action",    
		 type:"post",
		 data : params,
		 success:function(data){
			$.f("table","departmentGrid").treegridSinosoft("loadData",$.toJSON(data));
			$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
			$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
		 }
	});
};
//新增
deptSettingGrid.add = function(){
	deptSettingGrid.openWin("add");
}
//修改
deptSettingGrid.update = function(){
	deptSettingGrid.openWin("update");
}
deptSettingGrid.openWin=function(type){
	$.f("div", "deptSettingWindow").window({
		title : "新增下级部门信息",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		height:350,
		width:800,
		top:150,
		onClose : function(){
			deptSettingWindow.prototype.findName("input","organizationRelationOid").val("");
  			$(".departmenttool").find(".dis").setdisabledEvent(true);
  			deptSettingWindow.prototype.orgCnameFlag="";
		}
	});
	//记录操作类型
	$.f("div", "deptSettingWindow").window("open");
	deptSettingWindow.init(type);
}