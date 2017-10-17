$.SUI("deptSettingGrid");
//用这个变量代替部门表格
deptSettingGrid.init=function(){
	$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
	$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
	$.f("table","departmentGrid").treegridSinosoft({
		toolbar:'.departmenttool',
		body:{
		    url:sessionlostName+'/organization/organization-relation-info!findDeptByList.action',    
		    idField:'id', 
		    treeField:'orgCname', 
		    //加载之前执行
		    onBeforeLoad:function(row,parm){
	   			if(row==null){
	   				parm.id=""
	   			}else{
	   				parm.id=row.id;
	   				parm.parentName=row.orgShortCname;
	   			}
		    },
			onClickRow:function(row){
				if(row.purpose=="2"){
					if(row.invalidFlag=="1"){
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(false);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(false);
					}else{
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(false);
					}
				}else{
					if(row.invalidFlag=="1"){
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(false);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
					}else{
						$(".departmenttool").find(".dis").eq(0).setdisabledEvent(true);
						$(".departmenttool").find(".dis").eq(1).setdisabledEvent(true);
					}
				}
			}
		}
	})
}
deptSettingGrid.add = function(){
	deptSettingGrid.openWin("add");
}
deptSettingGrid.update = function(){
	deptSettingGrid.openWin("update");
}
deptSettingGrid.IsSelect=false;

deptSettingGrid.queryreset=function(){
	$.f("div","departmentGridDiv").f("input","deptName").textbox("setValue","");
};
deptSettingGrid.queryCondition=function(){
	if($.f("div","departmentGridDiv").find(".validatebox-invalid").length==0){
		deptSettingGrid.IsSelect=true;
		var value=$.f("div","departmentGridDiv").f("input","deptName").textbox("getValue");
		if($.trim(value)==""){
			deptSettingGrid.IsSelect=false;
			deptSettingGrid.init();
			$(".departmenttool").find(".button").setdisabledEvent(false);
		}else{
			$.ajax({
				 url:sessionlostName+"/organization/organization-relation-info!queryfindDeptanization.action",    
				 type:"post",
				 data:{
					 orgCname:$.f("div","departmentGridDiv").f("input","deptName").textbox("getValue")
				 },
				success:function(data){
					$.f("table","departmentGrid").treegridSinosoft("loadData",$.toJSON(data));
					$(".departmenttool").find(".button").setdisabledEvent(false);
				}
				
			})
		
		}
	}
	
};

deptSettingGrid.openWin=function(type){
	$.f("div", "deptSettingWindow").window({
		title : "新增下级部门",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		height:350,
		width:800,
		top:150			
	});
	//记录操作类型
	$.f("div", "deptSettingWindow").window("open");
	deptSettingWindow.init(type);
}

