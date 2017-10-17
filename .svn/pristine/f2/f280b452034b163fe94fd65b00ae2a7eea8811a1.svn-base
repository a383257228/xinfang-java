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
  	<div  idFlag="roleRemoveWindow" style="width:1008px;height:540px;display:none;overflow: hidden;">
  			<div class="sinosoft-layout" par="fit:true">
  			<div par="region:'center'">
  				<div class="fieldset">修改权限</div>
				<div style="height:420px">
					<table idFlag="removeRoleGrid">
			    		<thead>
							<tr>
								<th par="field:'id',checkbox:true,hidden:true"  ></th>
								<th par="field:'roleName'" width="150" align="center">角色名称</th>
								<th par="field:'roleNames'" width="150" align="center">角色描述</th>
								<th par="field:'caozuo',formatter:roleRemoveWindow.formatter" width="150" align="center">操作区</th>
							</tr>
						</thead>
			    	</table>
				</div>
				<div style="height:30px;text-align: center;">
					<a class="button deepblue" idFlag="saveUserRole" style="width:53px;">保存</a>
					<a class="button write" style="width:53px;"onclick="roleRemoveWindow.closeWin()">关闭</a>
				</div>
			</div>
			<div par="region:'east',width:'260px'">
				<div style="width:100%;height:100%">
					<div class="fieldset">区域信息</div>
					<div>
						<ul  idFlag="organizationtree"  multiple>
						</ul>						
					</div>
				</div>
			</div> 
  		</div>	
  	</div>
	<script type="text/javascript">
		$.SUI("roleRemoveWindow");
		roleRemoveWindow.onBeforeLoad=function(node, param){
			param.userId = '';
			param.roleId = '';
			param.node = -1;
		};
		roleRemoveWindow.treeloadFilter=function(data,parent){
			if(parent==null){
				$(data).each(function(){
				})
			}else{
				$(data).each(function(){
					delete this.state;
				})
			}
			return data;
		},
		roleRemoveWindow.treeformatter=function(node){
			delete node.state
			return node.text;
		};
		
		roleRemoveWindow.closeWin=function(){
			$.f("div","roleRemoveWindow").window("close");
		};
		roleRemoveWindow.formatter=function(value,row,index){
			return "<a style=\"width:52px\" class=\"rowaStyle\" onclick='roleRemoveWindow.remove(\""+row.roleId+"\")'>移除</a>";
		}
		//删除文件
		roleRemoveWindow.remove=function(roleid){
			$.ajax({
				url:"/jubao/authority/authority-user-role!removeUserRole.action",
				type:"post",
				data:{
					roleId:roleid,
					userId:$.f("div","roleRemoveWindow").data("userId")
				},
				success:function(data){
					var params={
						operationFuncCode : "removeUserRole",
						operationFuncName : "移除角色权限",
						operationBtnName : "移除",
						operationResultCode : "1",
						operationResultName : "成功",
						operationDesc : "移除角色权限",
						operationTypeCode : OperationType.DELETE,
						operationTypeName : OperationType.DELETE_NAME,
						operationDataDesc : "移除角色权限",
						operationDataValue : "移除用户id为:"+$.f("div","roleRemoveWindow").data("userId")+"的角色权限id为:"+roleid+"的数据",   			
						enableDataLog :true
					}
					saveOpeationRecord(params);
					
					$.f("div","roleRemoveWindow").f("table","removeRoleGrid").datagridSinosoft("reload");
					$("#roleDistributionMain").f("table","roleDistributionGrid").datagridSinosoft("reload");
					var nodes= $.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getChecked")
					$(nodes).each(function(){
						$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("uncheck", this.target);
					})
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;权限移除成功！', 2000);
				}
			})
		};
		roleRemoveWindow.saveRole=function(event){
			var row=$.f("div","roleRemoveWindow").f("table","removeRoleGrid").datagridSinosoft("getSelected");
			
			var tree=$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getChecked");
			var orgCode="";
			$(tree).each(function(){
				orgCode+=":"+this.orgCode;
			})
			if(tree.length>0){
				orgCode=orgCode.substring(1);
			}
			$.ajax({
				url:sessionlostName+"/authority/authority-user-role!saveUserRoleDate.action",
				type:"post",
				data:{
					userId:event.data,
					data:row.roleId+",,"+orgCode,
					flag:"Update"
				},
				success:function(data){
					var params={
						operationFuncCode : "saveUserRoleDate",
						operationFuncName : "给用户分配权限",
						operationBtnName : "保存",
						operationResultCode : "1",
						operationResultName : "成功",
						operationDesc : "给用户分配权限",
						operationTypeCode : OperationType.ADD,
						operationTypeName : OperationType.ADD_NAME,
						operationDataDesc : "给用户分配权限",
						operationDataValue : "区域编码："+orgCode+"用户id:"+event.data+"角色id:"+row.id,		
						enableDataLog :true
					}
					saveOpeationRecord(params);
					
					filterValue=$.f("div", "roleRemoveWindow").data("roleid")
					$.f("div", "roleRemoveWindow").data("roleid",(filterValue+","+row.id));
					
					$.f("div","roleRemoveWindow").f("table","removeRoleGrid").datagridSinosoft("reload");
					$("#roleDistributionMain").f("table","roleDistributionGrid").datagridSinosoft("reload");
					
					$.f("div","roleRemoveWindow").window("close");
					var nodes= $.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getChecked")
					$(nodes).each(function(){
						$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("uncheck", this.target);
					})
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;权限分配成功！', 2000);					
				}
			});
		};
		//初始化
		roleRemoveWindow.init=function(indexid){
			$.f("a","saveUserRole").unbind("click");
			$.f("a","saveUserRole").click(indexid,roleRemoveWindow.saveRole);
			
			$.f("div","roleRemoveWindow").data("userId",indexid);
			$.f("div","roleRemoveWindow").f("table","removeRoleGrid").datagridSinosoft({
				toolbar:'',
				body:{
					pageSize:10,
					pageList:[10],
					url:sessionlostName+"/authority/authority-user-role!loadUserRole.action",
					onBeforeLoad:function(par){
						par.start=(par.page-1)*par.rows;
						par.limit=par.rows;
						par.filterTxt="userId";
						par.filterValue=indexid;
						par.dir="ASC";
					},
					onSelect:function(index, row){
						var nodes= $.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getChecked")
						$(nodes).each(function(){
							$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("uncheck", this.target);
						})
						//获取根节点
						var nodes=$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getRoots");
						//获取子节点
						var children=null;
						$(nodes).each(function(){
							children=$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getChildren",this.target);
						})
						$(children).each(function(){
							if(row.regionCode.indexOf(this.orgCode)!=-1){
								$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("check", this.target);
							}
						});
					},
					loadFilter:function(data){
						data.rows=data.result;
						data.total=data.rows.length;
						return data;
					},
					onLoadSuccess: function(data){
						var params={
							operationFuncCode : "loadData",
							operationFuncName : "修改权限load回显数据",
							operationBtnName : "查询",
							operationDesc : "load回显数据",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
					}
				}
			})
			if($.trim($.f("div","roleRemoveWindow").f("ul","organizationtree").html()).length==0){
				$.f("div","roleRemoveWindow").f("ul","organizationtree").tree({
					checkbox:true,
					url:sessionlostName+"/authority/authority-user-role!getChildOrgRelaInfoList.action",
					onBeforeLoad:roleRemoveWindow.onBeforeLoad,
					formatter:roleRemoveWindow.treeformatter,
					loadFilter:roleRemoveWindow.treeloadFilter,
					onLoadSuccess:function(node){
						var params={
							operationFuncCode : "getChildOrgRelaInfoList",
							operationFuncName : "修改权限load回显区域数据",
							operationBtnName : "查询",
							operationDesc : "load回显区域数据",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
						$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("expandAll");
					}
				})
			}else{
				var nodes= $.f("div","roleRemoveWindow").f("ul","organizationtree").tree("getChecked")
				$(nodes).each(function(){
					$.f("div","roleRemoveWindow").f("ul","organizationtree").tree("uncheck", this.target);
				})
			}
		}
			
	</script>
  </body>
</html>
