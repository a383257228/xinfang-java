var centerContentPanel;
$(function() {
	 $("#p").click(function(){
    	if(	$("#west").parent().width()!=50){
        	$("#west").parent().animate({"left":"-150px"},200);
        	$(".accordion").animate({ "opacity": 0 }, 200);
        	var widths=$("#center").parent().width()+150;
        	$("#center").parent().animate({"left":"50px",width:widths+"px"},200,function(){
        		$("#bodybody").layout('panel', 'west').panel('resize',{width:50});
        		$("#bodybody").layout('resize');
        	});
        	$(".menu-toggle").css("background-position","-184px -31px");
        }else{
        	$(".accordion").animate({ "opacity": 1 }, 200);
        	$("#west").parent().animate({"width":"200px"},200);
        	$("#west").animate({"width":"200px"},200,function(){
        		$("#bodybody").layout('panel', 'west').panel('resize',{width:200});
        		$("#bodybody").layout('resize');
        	});
        	$(".menu-toggle").css("background-position","-184px -71px");
        } 
    });
	$.ajax({
		url : sessionlostName+"/allmenu.json",
		type : "get",
		async : false,
		error : function(data) {
			alert("xxx")
		},
		success : function(data) {
			/*if (typeof data == "string") {
				data = eval("(" + data + ")");
			}
			if(UserEname=="admin" || UserEname == "xsbadmin" || UserEname == "xsbgly"){
				data=data.info;
			}else{
				data=menuStr.info;
			}
			*/
			data=data.info;
			$(data).each(function(index, obj) {
				obj.title = obj.text;
				// 获取手风琴节点下的菜单节点对象数组
				var children = obj.children;
				// 删除父菜单手风琴下的children属性
				delete obj["children"];
				// 将当前手风琴节点对象内容转换为字符串
				var str = JSON.stringify(obj);
				str = str.substring(1, str.length - 1);
				// 将当前手风琴节点添加到手风琴面板中
				$(".accordion").append("<div id=\""+ obj.id + "\" par='" + str+ "'></div>");
				// 给当前手风琴节点添加存放菜单树
				$("#" + obj.id + "").append("<ul name='"+ obj.id + "MenuTree'></ul>")
				// 创建菜单树
				$("ul[name='" + obj.id + "MenuTree']").tree({
					data : children,
					animate : true,
					onClick : function(node) {
						// 叶子节点才能打开功能模块面板
						if (!node.children|| node.children.length == 0) {
							// 判断选项卡是否存在
							if ($.findName("div","contenttab").tabs("exists",node.text)) {
								// 将选项卡置为选中状态
								$.findName("div","contenttab").tabs("select",node.text);
							} else {
								var params = {
									iconCls : node.iconCls,// 图标
									title : node.text,// 文本
									closable : true
									// 是否有关不按钮
								};
								getHref(node.url,params);
									// 新增一个选项卡
									$.findName("div","contenttab").tabs("add",params);
								}
							}
						}
					});
			});
			// 创建手风琴菜单
			$(".accordion").accordion({
				animate : true,
				fit : false,
				border : false,
				onSelect : function(title, index) {
					if (title == '巡视了解') {
						// 判断选项卡是否存在
						if ($.findName("div", "contenttab").tabs("exists", title)) {
							// 将选项卡置为选中状态
							$.findName("div", "contenttab").tabs("select", title);
						} else {
							var params = {
								title : title,// 文本
								iconCls : "icon-large-menu",
								closable : true,// 是否有关不按钮
								href : "/inspectionUnderstand/inspectionUnderstand.jsp"
							};
							// 新增一个选项卡
							$.findName("div", "contenttab").tabs("add", params)
						}
					}
				}
			});
		}
	});			

	// 创建选项卡所在主面板
	centerContentPanel = $.findName("div", "contenttab");
	centerContentPanel.tabs({
		fit : true,
		border : false,
		onSelect : function(title, index) {
			var contenttab = $(this);
			var tab = contenttab.tabs('tabs');
			if (tab.length == 7) {
				$.findName("div", "contenttab").tabs("close", 0);
			}
			var selectedTab = contenttab.tabs("getTab", title);
			var selectedTabOptions = selectedTab.panel("options");
			var currGridObj = selectedTabOptions.currGridObj;
			if (currGridObj) {
				currGridObj.datagridSinosoft("reload");
			}
		}
	});
});

function getHref(url,params){
	var href = null;
	if(url=="organizationSettingGrid"){//组织设置
		href="/organization/organizationSetting/organizationSettingMain.jsp";
	}else if(url=="deptSetting"){//部门设置
		href="/organization/departmentSetting/deptSettingGrid.jsp";
	}else if(url=="userSetting"){//用户设置
		href="/organization/userSetting/userSettingGrid.jsp";
	}else if(url=="systemCodeMain"){//代码设置
		href="/organization/systemCode/systemCodeMain.jsp";
	}else if(url=="roleSetting"){//角色设置
		href="/organization/roleSetting/roleSettingMan.jsp";
	}else if(url=="roleDistribution"){//权限分配
		href="/organization/roleDistribution/roleDistributionMan.jsp";
	}else if(url=="authority-view"){//权限查看
		href="/operation/permissions/permissionsMain.jsp";
	}else if(url=="authorityLogPanel"){//授权记录
		href="/operation/record/recordGrid.jsp";
	}else if(url=="authorityHistoryPanel"){//历史权限查看
		href="/operation/lookHisAuthority/hisAuthorty.jsp";
	}else if(url=="logstate"){//日志列表
		href="/operation/list/operationListGrid.jsp";
	}else if(url=="showDataLogMain"){//日志查询
		href="/operation/query/operationQueryGrid.jsp";
	}else if(url=="pwd"){//修改密码
		parent.modifyPasswordWin.openModifyPasswordWindow();
	}else if(url=="logout"){//注销登录
		dialog.showNormalDialog("提示", '<i class="icon-quertion"></i> 确认注销当前登录？', {
	        ok: {
	            name: "确认",
	            click: function () {
	                dialog.removeDialog();
	                $.ajax({
	        			url : basePath+'/logon/logon!logout.action',
	        	    	success : function(response){
	        	    		window.location.href=basePath + "/logon/login.jsp";
	        	    	},
	        	    	failure: function(){
	        			}
	        		});
	            }
	        },
	        cancel: {
	            name: "取消",
	            click: function () {
	                dialog.removeDialog();
	            }
	        }
	    });
		return ;
	}else if(url=="quit"){//退出系统
		dialog.showNormalDialog("提示", '<i class="icon-quertion"></i> 是否关闭此窗口？', {
	        ok: {
	            name: "确认",
	            click: function () {
	            	dialog.removeDialog();
	                $.ajax({
						url : basePath+'/logon/logon!logout.action',
				    	success : function(response){
				    		window.open("", "_self");
		              		window.close();
				    	},
				    	failure: function(){
						}
					});
	            }
	        },
	        cancel: {
	            name: "取消",
	            click: function () {
	                dialog.removeDialog();
	            }
	        }
	    });
		return ;
	}
	if(href){
		params.href =sessionlostName+href;
	}
}

function openUpdateWin() {
	$.f("div", "updatepwdWin").window({
		width : 450,
		height : 210,
		title : "修改用户密码",
		modal : true,
		maximizable : false,
		minimizable : false,
		collapsible : false,
		closed : true
	});
	$.f("div", "updatepwdWin").find("form").form("reset");
	$.f("div", "updatepwdWin").find("form").form({
		url : "/organperson/person-info!modifyPassword.action",
		onSubmit : function(par) {
			var validate = $.f("div", "updatepwdWin").find("form").form("validate");
			return validate;
		},
		success : function(data) {
			if (data == "true") {
				dialog.showNormalDialog("用户密码修改成功",'<i class="icon-quertion"></i>密码修改成功,为了保护您的账户安全，是否需要重新登录',{
					ok : {
						name : "确认",
						click : function() {
							dialog.removeDialog();
							$.ajax({
								url : "/logon/logon!logout.action",
								success : function(data) {
									window.location.href = "/";
								}
							});
						}
					},
					cancel : {
						name : "取消",
						click : function() {
							localStorage.sessionpwd = $.md5($.f("div","updatepwdWin").find("input[name=userPwd]").val());
							dialog.removeDialog();
							closeWin();
						}
					}
				});
			}
		}
	});
	$.f("div", "updatepwdWin").window("open");
}