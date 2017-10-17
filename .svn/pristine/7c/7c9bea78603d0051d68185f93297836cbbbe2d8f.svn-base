$.SUI("userSettingWindow");
//用户窗体的原型
userSettingWindow.prototype=$.f("div","userSettingWindow");
//用户的初始化
userSettingWindow.init=function(){
	userSettingWindow.prototype=$.f("div","userSettingWindow");
	//当前选中行
	var row=userSettingGrid.prototype.treegridSinosoft("getSelected");
	userSettingWindow.prototype.find("input[type='password']").eq(0).attr("placeholder","输入不少于6位的密码")
	userSettingWindow.prototype.find("input[type='password']").eq(1).attr("placeholder","输入和刚才相同的密码")
	//判断当前选中行是用户还是机构,部门
	var obj={};
	if(row.UserCName){//如果他有登录名就说明是修改
		$.f("div","userSettingWindow").window("setTitle","修改用户信息")  
		//修改
		$.ajax({
			url:sessionlostName+ "/organperson/organ-person-relation!finduserByOid.action",
			type:"POST",
			async: false,
			data:{
				id:row.id
			},
			success:function(data){
				obj=$.toJSON(data)[0];
				obj.orgName=obj.orgShortCname;
				obj.userCname=obj.UserCName;
				obj.userPwd2=obj.userPwd;
				if(obj.invalidFlag=="1"){
					userSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
				}
				
			}
		});
	}else{
			$.f("div","userSettingWindow").window("setTitle","新增用户信息")  
		//新增
		obj=$.copyObj(row)
		obj.organizationInfoOid=row.id;
		obj.orgName=row.orgCname;
		obj.id="";
		obj.leaderFlag="0";
		obj.organPersonRelationOid=null;
		//默认设置为有效
		userSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("setCheck");
	}
	//弄好重置的对象
	userSettingWindow.resetObj=obj;
	userSettingWindow.prototype.find("form").form("reset");
	userSettingWindow.prototype.find("form").form({
		url:sessionlostName+ "/organperson/person-info!savePersonInfo.action",
		onSubmit: function(par){ 
			var validate=userSettingWindow.prototype.find("form").form("validate");
			if(userSettingWindow.prototype.find(".sinosoft-checkedbox").checkedBox("isChecked")){
   				par.invalidFlag="true";		
   			}else{
   		 		par.invalidFlag="false";	
   			}
			return validate;
		},
		success:function(data){
			var objx=eval("("+data+")");
			if(objx.success){
				try {
					if(userSettingGrid.IsSelect){
						userSettingGrid.queryUser();
					}else{
						if(userSettingWindow.prototype.findName("input","id").val().length==0){
							 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;用户保存成功', 3000);
							 var row=userSettingGrid.prototype.treegridSinosoft("getSelected");
							if(row.children){
								 $.f("table","userGrid").treegridSinosoft("reload",row.id);
							}else{
								 $.f("table","userGrid").treegridSinosoft("reload",row._parentId);
							}
							
							
						}else{
							 var row=userSettingGrid.prototype.treegridSinosoft("getSelected");
							 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;用户修改成功', 3000);
							 $.f("table","userGrid").treegridSinosoft("reload",row._parentId);
						}
					}
					userSettingWindow.prototype.window("close")
				} catch (e) {
					userSettingWindow.prototype.window("close")
				}
				
			}else{
				if(userSettingWindow.prototype.findName("input","id").val().length==0){
					 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;用户保存失败，用户名重复', 3000);
				}else{
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;用户修改失败，用户名重复', 3000);
				}
			}
			
			
			
		}
	})
	//load进去表单
	userSettingWindow.resetObj=	obj;
	userSettingWindow.prototype.find("form").form("load",obj);
	
}

//保存方法
userSettingWindow.savePerson=function(){
	userSettingWindow.prototype.find("form").form("submit");
}
//重置
userSettingWindow.reset=function(){
	userSettingWindow.prototype.find("form").form("reset");
		if(userSettingWindow.resetObj.invalidFlag=="2"){
			userSettingWindow.prototype.find("form").find(".sinosoft-checkedbox").checkedBox("setUnCheck");
		}else{
			userSettingWindow.prototype.find("form").find(".sinosoft-checkedbox").checkedBox("setCheck");
		}
		userSettingWindow.prototype.find("form").form("load",userSettingWindow.resetObj);
}
/*$(function(){
	userSettingWindow.prototype.find(".deepblue").click(function(){
		
   })
   userSettingWindow.prototype.find(".write").click(function(){
	
   })
})*/
