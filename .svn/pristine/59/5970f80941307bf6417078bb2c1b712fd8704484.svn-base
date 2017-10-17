<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
  <head>
 
  </head>
  <body>
  <style>
 div[idFlag="roleDistributionMain"]{
 	width: 100%;
 	height: 100%;
 }
  </style>
	<div id="roleDistributionMain" idFlag="roleDistributionMain">
		<div   class="roleDistributionGridtool"  style="height: 115px;padding:0px;overflow: hidden;">
			<div class="fieldset">检索查询</div>
			<!-- 用户名称 -->
			<div style="padding:13px 10px">
				<span> 用户名称:</span><input idFlag="personCName" par="validType:'customChinaValidator[10]'"   class="sinosoft-textbox" style="width:200px">
				 <a  onclick="roleDistributionGrid.findbypersonCName()" class="button deepblue" style="width:50px;" >查询</a>
				 <a href="javascript:void(0)"class="button write" style="width:50px;"  onclick="roleDistributionGrid.queryreset()">重置</a>
			</div>
			<div class="fieldset">检索列表</div>
		</div>
		<table idFlag="roleDistributionGrid">
			<thead>
				<tr>
					<th par="field:'Relation_Oid',checkbox:true,hidden:true"  ></th>
					<th par="field:'Person_CName'" width="150" align="center">用户名</th>
					<th par="field:'Org_Dept_Name'" width="150" align="center">部门岗位</th>
					<th par="field:'Person_Owner_Role'" width="150" align="center">角色名称</th>
					<th par="field:'Person_Owner_Rolex',formatter:roleDistributionGrid.formatter" width="150" align="center">操作区</th>
				</tr>
			</thead>
		</table>
		<jsp:include  page="/organization/roleDistribution/roleDistributionWindow.jsp"/>
		<jsp:include  page="/organization/roleDistribution/roleRemoveWindow.jsp"/>
	</div>
		<script type="text/javascript">
			$.SUI("roleDistributionGrid");
			//列表的格式化
			roleDistributionGrid.formatter=function(value,row,index){
				if(!row.Person_Owner_Role){
					return "<a style=\"width:52px\" class=\"rowaStyle\" onclick='roleDistributionGrid.distribution(\""+row.Person_ID+"\",\""+row.Role_ID+"\")'>分配权限</a><span class=\"rowshu\">|</span>"
					+"<a style=\"width:52px\" onclick='roleDistributionGrid.aaa(\""+row.Person_ID+"\")' class=\"rowaStyle\">解除锁定</a>";
				}else{
					return "<a style=\"width:52px\" class=\"rowaStyle\" onclick='roleDistributionGrid.distribution(\""+row.Person_ID+"\",\""+row.Role_ID+"\")'>分配权限</a><span class=\"rowshu\">|</span>"
					+"<a style=\"width:52px\" onclick='roleDistributionGrid.removeRole(\""+row.Person_ID+"\")' class=\"rowaStyle\">修改</a><span class=\"rowshu\">|</span>"
					+"<a style=\"width:52px\" onclick='roleDistributionGrid.relieveRole(\""+row.Person_ID+"\",\""+row.Person_CName+"\")' class=\"rowaStyle\">解除锁定</a>";
				}
			};
			//分配权限
			roleDistributionGrid.distribution = function(indexid,Role_ID,Person_CName){
				$.f("div", "roleDistributionWindow").data("roleid",Role_ID);
				$.f("div", "roleDistributionWindow").window({
					title : "权限分配",
					modal:true,
					collapsible : false,
					draggable:true,
					maximizable:false,
					minimizable:false,
					closed : true,
					top:150
				});
				$.f("div", "roleDistributionWindow").window("open");
				roleDistributionWindow.init(indexid);
			};
			//修改权限
			roleDistributionGrid.removeRole=function(personid){
				$.f("div", "roleRemoveWindow").window({
					title : "修改",
					modal:true,
					collapsible : false,
					draggable:false,
					maximizable:false,
					minimizable:false,
					closed : true,
					top:150,
				});
				$.f("div", "roleRemoveWindow").window("open");
				roleRemoveWindow.init(personid);
			};
			//解除锁定
			roleDistributionGrid.relieveRole = function(PersonID,userCName){
				$.ajax({
					url:sessionlostName+"/logon/logon!unLockUser.action",
					type:"post",
					data:{
						userId:PersonID,
				    	userCName:userCName
					},
					success:function(data){
						var params={
							operationFuncCode : "unLockUser",
							operationFuncName : "解除锁定",
							operationBtnName : "解除锁定",
							operationResultCode : "1",
							operationResultName : "成功",
							operationDesc : "解除锁定",
							operationTypeCode : OperationType.DELETE,
							operationTypeName : OperationType.DELETE_NAME,
							operationDataDesc : "解除锁定",
							operationDataValue : "解除锁定用户:"+userCName,   			
							enableDataLog :true
						}
						saveOpeationRecord(params);
						
						if("true"==data||true==data){
							dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;已对'+userCName+'解除锁定！', 2000);
							return;
						}else{
							dialog.showMiniDialog("self-style", '<i class="icon-success"></i>&nbsp;&nbsp;解除锁定失败！', 2000);
							return;
						}
					}
				});
			}
			//重置
			roleDistributionGrid.queryreset=function(){
				$.f("input","personCName").textbox("setValue","");
			}
			//查询
			roleDistributionGrid.findbypersonCName=function(){
				if($.f("div","roleDistributionMain").find(".validatebox-invalid").length==0){
					roleDistributionGrid.value="%"+$.f("input","personCName").textbox("getValue")+"%";
					$("#roleDistributionMain").f("table","roleDistributionGrid").datagridSinosoft("load",{
						filterTxt:"personCName;",
						filterValue:roleDistributionGrid.value
					});
					roleDistributionGrid.value=null;
				}
			};
			$(function(){
				$("#roleDistributionMain").f("table","roleDistributionGrid").datagridSinosoft({
					toolbar:".roleDistributionGridtool",
					body:{
						fit:true,
						url:sessionlostName+"/authority/authority-user-role!queryAuthorityUserAndRoleList.action",
						onBeforeLoad:function(par){
							par.start=(par.page-1)*par.rows;
							par.limit=par.rows;
							if(!roleDistributionGrid.value){
								par.filterTxt="";
								par.filterValue="";
								par.dir = "ASC";
							}
						},
						loadFilter:function(data){
							return data;
						},
						onLoadSuccess: function(data){
							var params={
								operationFuncCode : "queryAuthorityUserAndRoleList",
								operationFuncName : "查询区域下的所有用户及用户对应的角色",
								operationBtnName : "查询",
								operationDesc : "查询区域下的所有用户及用户对应的角色",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
							}
							saveOperaLog(params);
						}
					}
				});
			});
		</script>
  </body>
</html>