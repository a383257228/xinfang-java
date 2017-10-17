$.SUI("userSettingGrid");
//初始化用户列表
userSettingGrid.init = function(){
	//设置新增、修改按钮的禁用
	$(".usertool").find(".dis").eq(0).setdisabledEvent(true);
	$(".usertool").find(".dis").eq(1).setdisabledEvent(true);
	
	var params = {
		filterValue:"",
		node:-1	
	};
	var userGrid = $.f("div","userGridDiv").f("table","userGrid");
	userGrid.treegridSinosoft({
		toolbar:'.usertool',
		body:{
		    url:sessionlostName+ '/petition/petition-organ-info!getOrganUserColumnsTreeByOrgCnameAndInvalidFlag.action',  
		    queryParams : params,
		    animate : true,
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
				if(row.orgOrDeptOrPerson=="person"){//如果是人则可以修改
					//新增按钮失效;修改按钮启用
					$(".usertool").find(".dis").eq(0).setdisabledEvent(true);
					$(".usertool").find(".dis").eq(1).setdisabledEvent(false);
				}else if(row.orgOrDeptOrPerson=="dept"||row.orgOrDeptOrPerson=="org"){
					//新增按钮启用;修改按钮失效
					$(".usertool").find(".dis").eq(0).setdisabledEvent(false);
					$(".usertool").find(".dis").eq(1).setdisabledEvent(true);
				}
			},
			onDblClickRow : function(row){
				if(row.orgOrDeptOrPerson=="person"){
					$.f("div", "userInfoDiv").window({
						title : "查看用户信息",
						modal:true,
						collapsible : false,
						draggable:false,
						maximizable:false,
						minimizable:false,
						closed : true,
						width:750,
						height:310,
						onOpen: function(){
							$.ajax(sessionlostName+"/organperson/organ-person-relation!loadData.action",{
								data:{
									id : row.id
								},
								success:function(data, textStatus, jqXHR){
									var result = $.parseJSON(data);
									var setInt=0;
									var interval=setInterval(function(){
										if($.f("div","userInfoDiv").html()==""){
											setInt+=1
											if(setInt==200){
												clearInterval(interval);
											}
										}else{
											for(var field in result){
												
												if(field=="organizationInfo"){
													var organization = result[field];
													for(var f in organization){
														var value = organization[f];
														if(f=="createDate"||f=="invalidDate"){
															if(value){
																value = value.substr(0,10);
															}
														}
														if($.f("div","userInfoDiv").f("span", f)){
															$.f("div","userInfoDiv").f("span", f).html(value);
														}
													}
												}
												
												if(field=="personInfo"){
													var personInfo = result[field];
													for(var p in personInfo){
														var value = personInfo[p];
														if(p=="leaderFlag"){
															if (value == 1) {
										        				value = '领导';
										        			} else if (value == 2) {
										        				value =  '秘书';
										        			} else if (value == 3) {
										        				value =  '一般用户';
										        			} else if (value == 4) {
										        				value =  '业务管理员';
										        			} else if (value == 5) {
										        				value =  '委领导';
										        			}else if (value == 6) {
										        				value = '系统管理员';
										        			} else if (value == 7) {
										        				value = '安全管理员';
										        			} else if (value == 8) {
										        				value = '安全审计员';
										        			}
														}
														if(p=="modifyDate"){
															if(value){
																value = value.substr(0,10);
															}
														}
														if(p=="invalidFlag"){
															if(value=="1"){
																value = "是";
															}else if(value=="2"){
																value = "否";
															}
														}
														if($.f("div","userInfoDiv").f("span", p)){
															$.f("div","userInfoDiv").f("span", p).html(value);
														}
													}
												}
											}
											clearInterval(interval);
										}
									},50);
								},
								error: function(XMLHttpRequest, textStatus, errorThrown) {
									dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;数据有问题！', 3000);
								}
							});
						}
					});
					$.f("div", "userInfoDiv").window("open");
				}
			},
			onLoadSuccess: function(data){
				var params={
					operationFuncCode : "getOrganUserColumnsTreeByOrgCnameAndInvalidFlag",
					operationFuncName : "查询用户列表",
					operationBtnName : "查询",
					operationDesc : "查询用户列表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				}
				saveOperaLog(params);
			}
		}
	})
}
//用户的查询
userSettingGrid.queryUser=function(){
	if (!$.f("div","userGridDiv").f("form","userGridForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	
	var params = {};
	params.filterValue = "";
	params.node = -1;
	
	var name = $.f("div","userGridDiv").f("input","name").textbox('getValue');
	if(name){
		params.filterValue = "%"+name+"%";
		params.node = -1;
	}else{
		params.filterValue = "";
		params.node = -1;
	}
	
	$.ajax({
		url:sessionlostName+"/petition/petition-organ-info!getOrganUserColumnsTreeByOrgCnameAndInvalidFlag.action",    
		type:"post",
		data : params,
		success:function(data){
			$.f("div","userGridDiv").f("table","userGrid").treegridSinosoft("loadData",$.toJSON(data));
			$(".usertool").find(".dis").setdisabledEvent(false);
		}
		
	})
};
//用户的重置
userSettingGrid.queryreset=function(){
	$.f("div","userGridDiv").f("input","name").textbox("setValue","");
}
//新增用户
userSettingGrid.add = function(){
	userSettingGrid.openWin("add");
}
//修改用户
userSettingGrid.update = function(){
	userSettingGrid.openWin("update");
}
//用这个变量代替部门表格
userSettingGrid.openWin=function(type){
	var row = $.f("div","userGridDiv").f("table","userGrid").treegridSinosoft("getSelected");
	userSettingWinObj=$.f("div","userSettingWindow").window({
		title : "新增用户信息",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		width:750,
		top:150,
		rowFlag : row,
		userId : row.id,
		onOpen : function(){
			if(type=="add"){
				userSettingWinObj.f("input","userEname").textbox('readonly',false); 
				userSettingWinObj.f("input","userCname").textbox('readonly',false); 
				userSettingWinObj.f("input","orgName").combotree('readonly',true); 
				
				userSettingWinObj.find(".sinosoft-checkedbox").checkedBox("setCheck");//是否有效给默认值
				userSettingWinObj.f("input","orgName").combotree("setText",row.text);//所属部门给treeGrid选中的值
				userSettingWinObj.findName("input","organizationInfoOid").val(row.id);
			}else if(type=="update"){
				userSettingWinObj.f("input","userEname").textbox('readonly',true); 
				userSettingWinObj.f("input","userCname").textbox('readonly',true); 
				userSettingWinObj.f("input","orgName").combotree('readonly',false); 
				
				$.f("div","userSettingWindow").window("setTitle","修改用户信息");
				$.ajax({
					url:sessionlostName+ "/organperson/organ-person-relation!loadData.action",
					type:"POST",
					async: false,
					data:{
						id:row.id
					},
					success:function(data){
						var params={
							operationFuncCode : "loadData",
							operationFuncName : "用户设置修改load回显数据",
							operationBtnName : "查询",
							operationDesc : "load回显"+row.text+"的数据",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);

						
						obj=$.toJSON(data).personInfo;
						userSettingWinObj.findName("input","id").val(obj.id);//修改需要给用户的id
						
						userSettingWinObj.findName("input","organPersonRelationOid").val($.toJSON(data).id);
						userSettingWinObj.findName("input","organizationInfoOid").val($.toJSON(data).organizationInfo.id);
						userSettingWinObj.f("input","orgName").combotree("setText",$.toJSON(data).organizationInfo.orgCname);//修改也需要给所属部门福值
						
						userSettingWinObj.f("input","defaultDealerNameOrder").textbox("setValue",obj.defaultDealerNameOrder);
						userSettingWinObj.f("input","userEname").textbox("setValue",obj.userEname);
						userSettingWinObj.f("input","userCname").textbox("setValue",obj.userEname);
						userSettingWinObj.f("input","userPwd").textbox("setValue",obj.userPwd);
						userSettingWinObj.f("input","userPwd2").textbox("setValue",obj.userPwd);
						if(obj.invalidFlag=="1"){
							userSettingWinObj.find(".sinosoft-checkedbox").checkedBox("setCheck");
						}
						userSettingWinObj.f("input","telephone").textbox("setValue",obj.telephone);
						userSettingWinObj.f("input","leaderFlag").combobox("setValue",obj.leaderFlag);
					}
				});
			}
		},
		onClose: function(){
			 userSettingWinObj.findName("input","id").val("");
			 userSettingWinObj.findName("input","organizationInfoOid").val("");
			 userSettingWinObj.findName("input","organPersonRelationOid").val("");
			
			 userSettingWinObj.panel("options").rowFlag="";
			 userSettingWinObj.panel("options").userId="";
			 userSettingWinObj.f("form","userWinForm").form("reset");
			 userSettingWinObj.f("input","leaderFlag").combobox("clear");
		}
	});
	userSettingWinObj.window("open");
}
//用户的保存
userSettingGrid.saveUser = function(){
	if (!userSettingWinObj.f("form","userWinForm").form("validate")) {
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
		return;
	}
	var dpwd = userSettingWinObj.f("input","userPwd").textbox("getValue");
	var qpwd = userSettingWinObj.f("input","userPwd2").textbox("getValue");
	if(dpwd!=qpwd){ 
		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;&nbsp;确认密码与密码不一致！', 3000);
		return;
	}
	userSettingWinObj.f("form","userWinForm").form('submit',{
		url:sessionlostName+ "/organperson/person-info!savePersonInfo.action",    
	    onSubmit: function(param){
    		var validate = userSettingWinObj.f("form","userWinForm").form("validate");
			if(userSettingWinObj.find(".sinosoft-checkedbox").checkedBox("isChecked")){
   				param.invalidFlag="true";		
   			}else{
   		 		param.invalidFlag="false";
   			}
			return validate;
	    },
	    success:function(data){
	    	if(userSettingWinObj.findName("input","id").val().length>0){
	    		var params={
					operationFuncCode : "savePersonInfo",
					operationFuncName : "修改用户信息",
					operationBtnName : "保存信息",
					operationResultCode : "1",
					operationResultName : "成功",
					operationDesc : "修改用户信息",
					operationTypeCode : OperationType.MODIFY,
					operationTypeName : OperationType.MODIFY_NAME,
					operationDataDesc : "修改用户信息",
					operationDataValue : "用户登录名："+userSettingWinObj.f("form","userWinForm").f("input","userEname").val()+"用户姓名:"+userSettingWinObj.f("form","userWinForm").f("input","userCname").val(),		
					enableDataLog :true
				}
				saveOpeationRecord(params);
	    	}else{
	    		var params={
					operationFuncCode : "savePersonInfo",
					operationFuncName : "新增用户信息",
					operationBtnName : "保存信息",
					operationResultCode : "1",
					operationResultName : "成功",
					operationDesc : "新增用户信息",
					operationTypeCode : OperationType.ADD,
					operationTypeName : OperationType.ADD_NAME,
					operationDataDesc : "新增用户信息",
					operationDataValue : "用户登录名："+userSettingWinObj.f("form","userWinForm").f("input","userEname").val()+"用户姓名:"+userSettingWinObj.f("form","userWinForm").f("input","userCname").val(),		
					enableDataLog :true
				}
				saveOpeationRecord(params);
	    	}
		    dialog.showMiniDialog("self-style", "<i class='icon-success'></i>&nbsp;&nbsp;用户保存成功！", 3000);
		    $.f("table","userGrid").treegridSinosoft("reload",userSettingWinObj.panel("options").rowFlag._parentId);
		    userSettingWinObj.window("close");
	    }
	});
}
//窗口重置
userSettingGrid.resetWin = function(){
	var rowFlag = userSettingWinObj.panel("options").rowFlag;
	if(rowFlag.orgOrDeptOrPerson=="org"||rowFlag.orgOrDeptOrPerson=="dept"){
		$.f("form","userWinForm").form("reset");
		userSettingWinObj.find(".sinosoft-checkedbox").checkedBox("setCheck")
		userSettingWinObj.f("input","orgName").textbox('setText',rowFlag.text);
		userSettingWinObj.f("input","leaderFlag").combotree('clear');
	}else if(rowFlag.orgOrDeptOrPerson=="person"){
		$.ajax({
			url:sessionlostName+ "/organperson/organ-person-relation!loadData.action",
			type:"POST",
			async: false,
			data:{
				id:rowFlag.id
			},
			success:function(data){
				obj=$.toJSON(data).personInfo;
				userSettingWinObj.findName("input","id").val(obj.id);//修改需要给用户的id
				
				userSettingWinObj.findName("input","organPersonRelationOid").val($.toJSON(data).id);
				userSettingWinObj.findName("input","organizationInfoOid").val($.toJSON(data).organizationInfo.id);
				userSettingWinObj.f("input","orgName").combotree("setText",$.toJSON(data).organizationInfo.orgCname);//修改也需要给所属部门福值
				
				userSettingWinObj.f("input","defaultDealerNameOrder").textbox("setValue",obj.defaultDealerNameOrder);
				userSettingWinObj.f("input","userEname").textbox("setValue",obj.userEname);
				userSettingWinObj.f("input","userCname").textbox("setValue",obj.userEname);
				userSettingWinObj.f("input","userPwd").textbox("setValue",obj.userPwd);
				userSettingWinObj.f("input","userPwd2").textbox("setValue",obj.userPwd);
				if(obj.invalidFlag=="1"){
					userSettingWinObj.find(".sinosoft-checkedbox").checkedBox("setCheck")
				}
				userSettingWinObj.f("input","telephone").textbox("setValue",obj.telephone);
				userSettingWinObj.f("input","leaderFlag").combobox("setValue",obj.leaderFlag);
			}
		});
	}
}