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
  	<div  idFlag="roleDistributionWindow" style="width:1108px;height:603px;display:none;overflow: hidden;">
  			<div class="sinosoft-layout" par="fit:true">
  			<div par="region:'center'">
  				<div  class="roleDistool"  style="height:60px;padding:0px;overflow: hidden;">
					<div style="padding:13px 10px">
						 <span>角色名称:</span><input idFlag="roleName"  class="sinosoft-textbox" style="width:200px">
						 <a  onclick="roleDistributionWindow.query()" class="button deepblue" style="width:50px;margin-bottom:-3px;" >查询</a>
						 <a  class="button write" style="width:50px;margin-bottom:-3px;"  onclick="roleDistributionWindow.reset()">重置</a>
					</div>
				</div>
				<div style="height:500px">
					<table idFlag="DistributionGrid"></table>
				</div>
				<div style="height:30px;text-align: center;">
						<a class="button deepblue" idFlag="saveUserRoleDat" style="width:53px;" >保存</a>
						<a class="button write"  onclick="roleDistributionWindow.closewin()" style="width:53px;">关闭</a>
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
		$.SUI("roleDistributionWindow");
		roleDistributionWindow.onBeforeLoad=function(node, param){
			param.userId = '';
			param.roleId = '';
			param.node = -1;
		};
		roleDistributionWindow.treeloadFilter=function(data,parent){
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
		roleDistributionWindow.treeformatter=function(node){
			delete node.state
			return node.text;
		};
		
		roleDistributionWindow.closewin=function(){
			$.f("div","roleDistributionWindow").window("close");
		};
		roleDistributionWindow.saveUserRoleDate=function(event){
			var row=$.f("div","roleDistributionWindow").f("table","DistributionGrid").datagridSinosoft("getSelected");
			
			var tree=$.f("div","roleDistributionWindow").f("ul","organizationtree").tree("getChecked");
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
					data:row.id+",,"+orgCode,
					flag:"Add"
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
					
					filterValue = $.f("div", "roleDistributionWindow").data("roleid");
					$.f("div", "roleDistributionWindow").data("roleid",(filterValue+","+row.id));
					
					$.f("div","roleDistributionWindow").f("table","DistributionGrid").datagridSinosoft("load",{
						filterValue : filterValue+","+row.id,
						filterTxt : "roleId"
					});
					
					$("#roleDistributionMain").f("table","roleDistributionGrid").datagridSinosoft("reload");
					
					var nodes= $.f("div","roleDistributionWindow").f("ul","organizationtree").tree("getChecked")
					$(nodes).each(function(){
						$.f("div","roleDistributionWindow").f("ul","organizationtree").tree("uncheck", this.target);
					})
					dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;权限分配成功！', 2000);					
				}
			});
		};
		//初始化
		roleDistributionWindow.init=function(indexid){
			$.f("a","saveUserRoleDat").unbind("click");
			$.f("a","saveUserRoleDat").click(indexid,roleDistributionWindow.saveUserRoleDate);
			
			var param = {};
			var filterValue = $.f("div", "roleDistributionWindow").data("roleid");
			var filterTxt = "";
			if(filterValue.length>0){
				param.filterValue = filterValue;
				param.filterTxt = "roleId";
			}else{
				param.filterTxt = "";
				param.filterValue = "";
			}
			
			var row = $("#roleDistributionMain").f("table","roleDistributionGrid").datagridSinosoft("getSelected");
			$.f("div","roleDistributionWindow").f("table","DistributionGrid").datagridSinosoft({
				toolbar:'.roleDistool',
				body:{
					url:sessionlostName+"/authority/authority-role!queryRolePagedQuery.action",
					queryParams:param,
					frozenColumns:[[{
						field : "id",
						hidden : true
					}]],
					columns:[[{
		        		field : "roleName",
		        		title : "角色名称",
		        		align : "center",
		        		width : 150
		        	},{
		        		field : "roleNames",
		        		title : "角色描述",
		        		align : "center",
		        		width : 150
		        	}]],
					onBeforeLoad:function(par){
						par.start=(par.page-1)*par.rows;
						par.limit=par.rows;
						par.dir="ASC";
					},
					loadFilter:function(data){
						data.rows=data.result
						return data;
					},
					onLoadSuccess: function(data){
						var params={
							operationFuncCode : "queryRolePagedQuery",
							operationFuncName : "查询角色信息",
							operationBtnName : "查询",
							operationDesc : "查询角色信息",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
					}
				}
			});
			
			if($.trim($.f("div","roleDistributionWindow").f("ul","organizationtree").html()).length==0){
				$.f("div","roleDistributionWindow").f("ul","organizationtree").tree({
					checkbox:true,
					url:sessionlostName+"/authority/authority-user-role!getChildOrgRelaInfoList.action",
					onBeforeLoad:roleDistributionWindow.onBeforeLoad,
					formatter:roleDistributionWindow.treeformatter,
					loadFilter:roleDistributionWindow.treeloadFilter,
					onLoadSuccess:function(node){
						var params={
							operationFuncCode : "getChildOrgRelaInfoList",
							operationFuncName : "查询用户区域信息",
							operationBtnName : "查询",
							operationDesc : "查询用户区域信息",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						}
						saveOperaLog(params);
						$.f("div","roleDistributionWindow").f("ul","organizationtree").tree("expandAll");
					}
				});
			}
		};
		//查询
		roleDistributionWindow.query = function(){
			var params = {};
			var roleName = $.f("div","roleDistributionWindow").f("input","roleName").textbox("getValue");
			if(roleName){
				params.filterTxt = "roleIds;roleName";
				params.filterValue = $.f("div", "roleDistributionWindow").data("roleid")+";%"+roleName+"%";
			}
			$.f("div","roleDistributionWindow").f("table","DistributionGrid").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","roleDistributionWindow").f("table","DistributionGrid").datagridSinosoft("load","/jubao/authority/authority-role!queryRolePagedQuery.action");
		}
		//重置
		roleDistributionWindow.reset = function(){
			$.f("div","roleDistributionWindow").f("input","roleName").textbox("setValue","");
		}
	</script>
  </body>
</html>