<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<div class="sinosoft-layout" idFlag="roleSetingWindow" style="width:1000px;height:700px;">
			<div par="region:'center'" style="width: 750px;height:610px;">
				<div style="width:650px;height:290px;float:left;margin-left: 20px;" idFlag="basicDiv">
					<fieldset style="height:280px">
						<legend>基本信息</legend>
						<form idFlag="roleSetingForm">
							<table class="tablebox">
								<tr>
									<td>
										<input type="hidden"  name="id">
			  							<input type="hidden"  name="appSystem">
			  							<input type="hidden"  name="roleCode">
										角色名称<span style="color:red">*</span>:&nbsp;<input idFlag="roleName" name="roleName" required="true" validType="customChinaValidator[32]"  class="sinosoft-textbox" style="width:200px;" prompt="最多输入32个字符">
									</td>
									<td>
										失效日期<span style="color:red">*</span>:&nbsp;<input idFlag="expireDate" name="expireDate"  par="validType:'minDate'" required="true"  class="sinosoft-datebox " style="width:200px;">
									</td>
								</tr>
								<tr>
									<td colspan="2">
										登录首页:&nbsp;<input idFlag="visitUrl" name="visitUrl" validType="customChinaValidator[65]"  class="sinosoft-textbox" style="width:520px;" prompt="最多输入65个字符" >
									</td>
								</tr>
								<tr>
									<td colspan="2">
										角色描述:&nbsp;<input idFlag="description" name="description" validType="singleTextValidator[100]"  class="sinosoft-textbox"par="multiline:true" style="height:150px;width:520px" prompt="最多输入100个字符" >
									</td>
								</tr>
							</table>
						</form>
					</fieldset>
				</div>
				<div style="width: 650px;height:310px;float:left;margin-left: 20px;">
						<fieldset style="height:310px">
							<legend>数据权限</legend>
							<div style="height:280px;">
								<table id="dataPermissionsGrid" idFlag="dataPermissionsGrid"></table>
							</div>
						</fieldset>
				</div>
			</div>
		    <div par="region:'east',width:'310px',height:'610px'">
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
							url:'/jubao/authority/authority-menu!getAuthorityMenuTree.action',
							loadFilter:roleSetingWindow.treeloadFilter,
							onBeforeLoad:roleSetingWindow.onBeforeLoad,
							onLoadSuccess:roleSetingWindow.onLoadSuccess
							" multiple>
							</ul>						
						</div>
					</div>
			</div>
		    <div  par="region:'south'" style="width:1000px;height:80px;text-align: center;">
		    	<a href="javascript:void(0)" class="button deepblue" style="width:50px;margin-bottom: 3px"  onclick="roleSettingMan.submit()">保存</a>
				<a href="javascript:void(0)" class="button write" style="width:50px;margin-bottom: 3px"  onclick="roleSettingMan.reset()">重置</a>
		    </div>
  	</div>
  	<script type="text/javascript">
		$.SUI("roleSetingWindow");
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
	</script>
  </body>
</html>