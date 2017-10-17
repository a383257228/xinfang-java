$.SUI("deptSettingWindow");
deptSettingWindow.prototype=$.f("div","deptSettingWindow");
deptSettingWindow.prototype.orgCnameFlag = "";

deptSettingWindow.init=function(type){
	var row = deptSettingGrid.prototype.treegridSinosoft("getSelected");
	var parid = row._parentId;
	var flag="insert";
	
	//如果是新增
	if(type=="add"){
		 $.f("div","deptSettingWindow").window("setTitle","新增下级部门信息")  
		 flag="insert";
		 //如果是新增
	  	  $.ajax({  //获取区域编码
	  			url:sessionlostName+ "/petition/petition-organ-info!getMaxDeptNoAndOrder.action",
	  			type:"POST",
	  			async: false,
	  			data:{
	  				orgCode : row.orgCode,
	  				id : row.id
	  			},
	  			success:function(data){
	  				var obj ={};
	  				data = $.toJSON(data);
	  				obj.orgCode = data.maxDeptNo
	  				obj.organOrder = data.deptOrder;
	  				obj.id = null,
	  				obj.parentId = row.id;
	  				obj.parentOrgCname = row.text;
	  				
	  				deptSettingWindow.prototype.findName("input","organizationRelationOid").val(row.id);
	  				deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
	  				
	  				deptSettingWindow.resetObj=obj;
		            deptSettingWindow.prototype.find("form").form("reset");
	  				deptSettingWindow.prototype.find("form").form({
	  					 url:sessionlostName+ "/petition/petition-organ-info!saveOrganizationInfo.action",
	  					 parm:data
	  				});
	  				deptSettingWindow.prototype.find("form").form("load",obj);
	  			}
	  	  });
	}else{
		var obj = row;
		$.f("div","deptSettingWindow").window("setTitle","修改部门信息")  
		flag="save";
		$.ajax({
		 	url:sessionlostName+ "/organization/organization-relation-info!loadData.action",
			type:"POST",
			async: false,
			data:{
				id:row.id
			},
			success:function(data){
				var params={
					operationFuncCode : "loadData",
					operationFuncName : "部门设置修改load回显数据",
					operationBtnName : "查询",
					operationDesc : "load回显"+row.text+"的数据",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
							
				deptSettingWindow.prototype.orgCnameFlag = $.toJSON(data).id;
				var result = $.toJSON(data).organizationInfo;
				obj.description = result.description;
				obj.id = result.id;
				obj.organOrder = row.organOrder;
				deptSettingWindow.prototype.findName("input","organizationRelationOid").val($.toJSON(data).id);
			}
		});
		
		deptSettingWindow.resetObj=obj;
		deptSettingWindow.prototype.find("form").form("reset");
		if(row.invalidFlag=="1"){
			deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
		}
		deptSettingWindow.prototype.find("form").form({
			url:sessionlostName+ "/organization/organization-info!saveOrganizationInfo.action"
		});
		deptSettingWindow.prototype.find("form").form("load",obj);
	}
	
	deptSettingWindow.prototype.find("form").form({
		onSubmit: function(par){ 
			var validate=deptSettingWindow.prototype.find("form").form("validate")
			par.flag=flag;
			par.purpose="2";
			par.relation="1";
			par.orgCname = deptSettingWindow.prototype.find("input[name='text']").val();
			if(deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("isChecked")){
	   				par.invalidFlag="true";		
   			}else{
	   		 		par.invalidFlag="false";	
   			}
   		    if(flag=="insert"){
	   	  	 	var parm=deptSettingWindow.prototype.find("form").form("options").parm;
	   	   		par.maxNoInfoId=parm.IdsInfo;
	   	    }
   		    return validate;
		},
		success:function(){
			var currow=$.f("table","departmentGrid").treegridSinosoft("getSelected");//当前选中的
			var parentrow=$.f("table","departmentGrid").treegridSinosoft("getParent",currow.id);//选中的父节点
			if($.f("div","deptSettingWindow").findName("input","id").val().length==0){
				var params={
					operationFuncCode : "saveOrganizationInfo",
					operationFuncName : "新增部门信息",
					operationBtnName : "保存信息",
					operationResultCode : "1",
					operationResultName : "成功",
					operationDesc : "新增部门信息",
					operationTypeCode : OperationType.ADD,
					operationTypeName : OperationType.ADD_NAME,
					operationDataDesc : "新增部门信息",
					operationDataValue : "部门编码："+deptSettingWindow.prototype.find("input[name='orgCode']").val()+",部门名称："+deptSettingWindow.prototype.find("input[name='text']").val(),		
					enableDataLog :true
				}
				saveOpeationRecord(params);
				dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;部门保存成功', 3000);
				if(currow.children){
					$.f("table","departmentGrid").treegridSinosoft("reload",currow.id);
				}else{
					$.f("table","departmentGrid").treegridSinosoft("reload",parentrow.id);
				}
			}else{
				var params={
					operationFuncCode : "saveOrganizationInfo",
					operationFuncName : "修改部门信息",
					operationBtnName : "保存信息",
					operationResultCode : "1",
					operationResultName : "成功",
					operationDesc : "修改部门信息",
					operationTypeCode : OperationType.MODIFY,
					operationTypeName : OperationType.MODIFY_NAME,
					operationDataDesc : "修改部门信息",
					operationDataValue : "部门编码："+deptSettingWindow.prototype.find("input[name='orgCode']").val()+",部门名称："+deptSettingWindow.prototype.find("input[name='text']").val(),		
					enableDataLog :true
				}
				saveOpeationRecord(params);
				 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;部门修改成功', 3000);
				 $.f("table","departmentGrid").treegridSinosoft("reload",parentrow.id);
			}
			deptSettingWindow.prototype.window("close")
		}
	})
}

//重置
deptSettingWindow.reset=function(){
	deptSettingWindow.prototype.find("form").form("reset");
	deptSettingWindow.prototype.find("form").form("load",deptSettingWindow.resetObj);
	if(deptSettingWindow.resetObj.invalidFlag=="2"){
		deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setUnCheck");
	}else{
		deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
	}
}
$(function(){
	deptSettingWindow.prototype.find(".deepblue").click(function(){
		var row = deptSettingGrid.prototype.treegridSinosoft("getSelected");
		$.ajax({
		 	url:sessionlostName+ "/organization/organization-info!judgeOrganizationInfo.action",
			type:"POST",
			async: false,
			data:{
				filterTxt:'orgCode',
				filterValue:row.orgCode,
				id:row.id
			},
			success:function(data){
				if(data){
					if(data=='true'||data==true){
						dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;该值已存在！', 3000);
	   					return;
					}else{
						$.ajax({
						 	url:sessionlostName+ "/organization/organization-info!judgeOrganization.action",
							type:"POST",
							async: false,
							data:{
								filterTxt:'orgCname',
								filterValue:deptSettingWindow.prototype.find("input[name='text']").val(),
								organizationRelationOid:deptSettingWindow.prototype.orgCnameFlag,
								organOid:deptSettingWindow.prototype.orgCnameFlag
							},
							success:function(data){
								if(data==true||data=='true'){
									dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;部门下已经存在该中文名的部门！', 3000);
				   					return;
								}else{
									deptSettingWindow.prototype.find("form").form("submit");
								}
							}
						});
					}
				}
			}
		});
    })
    deptSettingWindow.prototype.find(".write").click(function(){
   	    deptSettingWindow.reset();
    })
});