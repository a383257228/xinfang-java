<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
  <head>
 
  </head>
  <body>
  <style>

  </style>
  	<div  idFlag="roleSetingWindow" style="width:1100px;height:503px;display:none;overflow: hidden;">
  			<div class="sinosoft-layout" par="fit:true">
  			<div par="region:'center'" style="background-color: #f5f8fd">
  				<div class="fieldset">基本信息</div>
  				<form  idFlag="roleSetingForm"style="margin: 0px;">
	  				<table class="tablebox" style="border-bottom: 0px">
	  					<tr>
	  						<td >
	  							<input type="hidden"  name="id">
	  							<input type="hidden"  name="appSystem">
	  							<input type="hidden"  name="roleCode">
	  							<input type="hidden"  name="visitUrl">
	  							<div>角色名称:</div>
	  						</td>
	  						<td >
	  							<input class="sinosoft-textbox" name="roleName" style="width:220px; " required="true"  prompt="最多输入10个字符" par="validType:'customChinaValidator[10]'"></input>
	  							
	  						</td>
	  						<td >
	  							<div>失效日期:</div>
	  						</td>
	  						<td>
	  							<input class="sinosoft-datebox" style="width:220px;" editable="false" required="true" name="expireDate"   par="validType:'minDate'"></input>	
	  						</td>
	  					</tr>
	  					<tr>
	  						<td>
	  							<div style="position: relative;top: -100px;">角色描述</div>
	  						</td>
	  						<td colspan="3">
	  							<input idFlag="description" class="sinosoft-textbox" name="description" multiline="true" par="validType:'singleTextValidator[100]'"  prompt="最多输入100个字符"   style="width:603px;height:213px"></input>	
	  						
	  						</td>
	  					</tr>
	  					<tr>
		  					<td  colspan="4" style="text-align: center;">
		  						<a class="button deepblue" onclick="roleSetingWindow.saveRole()" style="width:53px"> 保存</a>
		  						<a class="button write" style="width:53px" onclick="roleSetingWindow.resetForm()"> 重置</a>
		  					</td>
	  					</tr>
	  				</table>
  				</form>
			</div>
			<div par="region:'east',width:'250px'">
					<div style="width:100%;height:100%">
						<div class="fieldset">功能菜单</div>
						<div>
							<ul  idFlag="menutree" class="sinosoft-tree" 
							par="
							checkbox:true,
							queryParams:{
								roleId:'',
								node:-1
							},
							url:'<%=basePath%>/authority/authority-menu!getAuthorityMenuTree.action',
							loadFilter:roleSetingWindow.treeloadFilter,
							onBeforeLoad:roleSetingWindow.onBeforeLoad,
							onLoadSuccess:roleSetingWindow.onLoadSuccess
							" multiple>
								
							</ul>						
						</div>
					</div>
			</div> 
  		</div>	
  	</div>
	<script type="text/javascript">
		$.SUI("roleSetingWindow");
		roleSetingWindow.resetForm=function(){
			$.f("form","roleSetingForm").form("reset");
			$.f("div","roleSetingWindow").f("ul","menutree").tree("clearChecked");
		}
		roleSetingWindow.treeloadFilter=function(data){
			$(data).each(function(index){
				if(!data[index].leaf){
					data[index].state="closed"
				}else{
					delete data[index].state;
				}
			})
			return data;
		}
		roleSetingWindow.onBeforeLoad=function(node,par){
			if(node){
				par.node=node.id;
			}
		}
		roleSetingWindow.onLoadSuccess=function(node, data){
			$.f("ul","menutree").tree("expandAll");
			$(roleSetingWindow.children).each(function(index){
				var node=$.f("ul","menutree").tree("find",this.id);
				$.f("ul","menutree").tree("check", node.target);
			})
			if($.f("div","roleSetingWindow").panel("options").clearFlag){
				$.f("ul","menutree").tree("clearChecked");
			}
		}
		roleSetingWindow.saveRole=function(){
			if (!$.f("form","roleSetingForm").form("validate")) {
				dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>请检查表单填写是否正确', 3000);
			}else{
				var checkarr=$.f("ul","menutree").tree("getChecked");
				var nodes = $.f("ul","menutree").tree("getChecked","indeterminate");	// 获取不确定的节点
				$(nodes).each(function(){
					checkarr.push(this)
				})
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
							    	//2016/11/23加上这句话保存会吧角色描述信息复制一遍再添加一次
							    	//par.description=$.f("div","roleSetingWindow").f("input","description").textbox("getValue")
							    	par.data="";
							    	return validate;
							    },    
							    success:function(data){
							    	if($.f("div","roleSetingWindow").find("input[name='id']").val().length>0){
							    		dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 角色修改成功！', 2500);
							    	}else{
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
				})
			}
		}
	</script>
  </body>
</html>
