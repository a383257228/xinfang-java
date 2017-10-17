$.SUI("deptSettingWindow");
deptSettingWindow.prototype=$.f("div","deptSettingWindow");
deptSettingWindow.init=function(type){
	var row=deptSettingGrid.prototype.treegridSinosoft("getSelected");
	var parid=row._parentId;
	 var obj ={};
	 var flag="insert";
	 $.ajax({
		 	url:sessionlostName+ "/organization/organization-relation-info!findOrganById.action",
			type:"POST",
			async: false,
			data:{
				id:row.id
			},
			success:function(data){
				row=$.toJSON(data)[0].organizationInfo;
				row.organizationRelationInfo[0]=$.toJSON(data)[0];
			}
		  })
	//如果是新增
	if(type=="add"){
			$.f("div","deptSettingWindow").window("setTitle","新增部门信息")  
		 obj.parentName=row.orgCname
			flag="insert";
		 //如果是新增
	  	  $.ajax({  //获取区域编码
	  			url:sessionlostName+ "/petition/petition-organ-info!getMaxDeptNo.action",
	  			type:"POST",
	  			async: false,
	  			data:{
	  				orgCode:row.orgCode,
	  				id:row.organizationRelationInfo[0].id
	  			},
	  			success:function(data){
	  				data=$.toJSON(data);
	  				obj.orgCode=data.maxDeptNo
	  				obj.intSort=data.deptOrder;
	  				obj.id="",
	  				obj.parentId=row.id;
	  				deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
	  				deptSettingWindow.prototype.find("form").form({
	  					 url:sessionlostName+ "/petition/petition-organ-info!saveOrganizationInfo.action",
	  					 parm:data
	  				})
	  			}
	  	  })
	}else{
		$.f("div","deptSettingWindow").window("setTitle","修改部门信息")  
		flag="save";
		obj =row;
		obj.orgCode=row.orgCode;
		obj.parentId=row.organizationRelationInfo[0].organizationRelationInfo.id;
		obj.intSort=row.organizationRelationInfo[0].organOrder;
		obj.parentName=row.organizationRelationInfo[0].organizationRelationInfo.organizationInfo.orgCname;
		
		if(row.invalidFlag=="1"){
			deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
		}
		deptSettingWindow.prototype.find("form").form({
				 url:sessionlostName+ "/organization/organization-info!saveOrganizationInfo.action"
		})
	}
	deptSettingWindow.resetObj=obj;
	deptSettingWindow.prototype.find("form").form("reset");
	deptSettingWindow.prototype.find("form").form("load",obj);
	deptSettingWindow.prototype.find("form").form({
		onSubmit: function(par){ 
			var validate=deptSettingWindow.prototype.find("form").form("validate")
			par.flag=flag;
			par.purpose="2";
			if(deptSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("isChecked")){
	   				par.invalidFlag="true";		
	   			}else{
	   		 		par.invalidFlag="false";	
	   			}
   		   par.organizationRelationOid=row.organizationRelationInfo[0].id;
   		   par.organOrder=deptSettingWindow.prototype.find("input[name='intSort']").val();
   		   if(flag=="insert"){
	   	  	 	var parm=deptSettingWindow.prototype.find("form").form("options").parm;
	   	   		par.maxNoInfoId=parm.IdsInfo;
	   	   }
   		   return validate;
		},
		success:function(){
			if(deptSettingGrid.IsSelect){
				deptSettingGrid.queryCondition();
			}else{
				var currow=$.f("table","departmentGrid").treegridSinosoft("getSelected");//当前选中的
				var parentrow=$.f("table","departmentGrid").treegridSinosoft("getParent",currow.id);//选中的父节点
				if($.f("div","deptSettingWindow").findName("input","id").val().length==0){
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;部门保存成功', 3000);
					if(currow.children){
						$.f("table","departmentGrid").treegridSinosoft("reload",currow.id);
					}else{
						$.f("table","departmentGrid").treegridSinosoft("reload",parentrow.id);
					}
				}else{
					 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;部门修改成功', 3000);
					 $.f("table","departmentGrid").treegridSinosoft("reload",parentrow.id);
				}
			}
			deptSettingWindow.prototype.window("close")
		}
	})
}
/**
 *重置按钮
 *
 */
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
		deptSettingWindow.prototype.find("form").form("submit");
    })
    deptSettingWindow.prototype.find(".write").click(function(){
   	 deptSettingWindow.reset();
    })
})
