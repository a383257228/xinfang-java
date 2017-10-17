$.SUI("userSettingGrid");
//用这个变量代替部门表格
userSettingGrid.openWin=function(type){
	$.f("div", "userSettingWindow").window({
		title : "新增用户",
		modal:true,
		collapsible : false,
		draggable:false,
		maximizable:false,
		minimizable:false,
		closed : true,
		width:750,
		top:150			
	});
	userSettingWindow.init();
	$.f("div", "userSettingWindow").window("open");
}

userSettingGrid.IsSelect=false;

userSettingGrid.queryreset=function(){
	$.f("div","userGridDiv").f("input","orgCname").textbox("setValue","");
	$.f("div","userGridDiv").f("input","userEname").textbox("setValue","");
	
}
userSettingGrid.queryUser=function(){
	if($.f("div","userGridDiv").find(".validatebox-invalid").length==0){
		userSettingGrid.IsSelect=true;
		var orgCname=$.f("div","userGridDiv").f("input","orgCname").textbox("getValue");
		var userEname=$.f("div","userGridDiv").f("input","userEname").textbox("getValue");
		if($.trim(orgCname)==""&&$.trim(userEname)==""){
			userSettingGrid.IsSelect=false;
			userSettingGrid.init();
			$(".usertool").find(".button").setdisabledEvent(false);
		}else{
			$.ajax({
				 url:sessionlostName+"/organperson/organ-person-relation!queryUser.action",    
				 type:"post",
				 data:{
					 orgCname:orgCname,
					 userEname:userEname
				 },
				success:function(data){
					$.f("table","userGrid").treegridSinosoft("loadData",$.toJSON(data));
					$(".usertool").find(".button").setdisabledEvent(false);
				}
			})
		}
	}
};

userSettingGrid.add = function(){
	userSettingGrid.openWin("add");
}

userSettingGrid.update = function(){
	userSettingGrid.openWin("update");
}
userSettingGrid.init=function(){
	$(".usertool").find(".dis").eq(0).setdisabledEvent(true);
	$(".usertool").find(".dis").eq(1).setdisabledEvent(true);
	$.f("table","userGrid").treegridSinosoft({
		toolbar:'.usertool',
		body:{
		    url:sessionlostName+ '/organperson/organ-person-relation!finduserByorg.action',    
		    idField:'id', 
		    treeField:'orgCname', 
		    //加载之前执行
		    onBeforeLoad:function(row,parm){
	   			if(row==null){
	   				parm.id=""
	   			}else{
	   				parm.id=row.id;
	   			}
		    },
			onClickRow:function(row){
				if(row.UserCName){//如果有登陆名就是用户
					//新增按钮失效
					$(".usertool").find(".dis").eq(0).setdisabledEvent(true);
					//修改按钮启用
					$(".usertool").find(".dis").eq(1).setdisabledEvent(false);
				}else{
					if(row.invalidFlag=="1"){
						//新增按钮启用
						$(".usertool").find(".dis").eq(0).setdisabledEvent(false);
						//修改按钮失效
						$(".usertool").find(".dis").eq(1).setdisabledEvent(true);
					}else if(row.invalidFlag=="2"){
						//当为无效部门时，不可新增
						$(".usertool").find(".dis").eq(0).setdisabledEvent(true);
						$(".usertool").find(".dis").eq(1).setdisabledEvent(true);
					}
				}
			}
		}
	})
}