<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	if(basePath != null && "/".equals(basePath)){
		basePath = "";
	}
%>
<!DOCTYPE>
<html>
  <head>
  </head>
  <body>
  <style>
  	 div[idFlag="organizationBarGridDiv"]{
 	 	height: 100%;
 	 }
 	 div[idFlag="organizationBarGridDiv"] .datagrid-header-row, div[idFlag="organizationBarGridDiv"]  .datagrid-row{
	 	 height:40px; 
	 }
 	 div[idFlag="organizationBarGridDiv"] td{
 	 	border-right:0px !important; 
 	 }
  </style>
  	<!--机构新增窗体-->
  	<jsp:include  page="/organization/organizationSetting/organizationSettingWindow.jsp"/>
	<div idFlag="organizationBarGridDiv">
			<div class="organizationBartool" style="height: 115px;padding:0px;overflow: hidden;">
				<div class="fieldset">检索查询</div>
				<div style="padding:13px 10px">
					<form idFlag="organizationBarGridForm">
						<span>区域名称:</span><input idFlag="orgName" class="sinosoft-textbox"  par="validType:'customChinaValidator[30]'" style="width:200px">
						<span>区域编码:</span><input idFlag="orgCode" class="sinosoft-textbox"  par="validType:'customChinaValidator[12]'" style="width:200px">
						<span>是否有效:</span><input type="checkbox" value='1' idFlag="validFlag" checked="checked"/>
						<a href="javascript:void(0)"class="button deepblue" style="width:50px;"  onclick="organizationBarGrid.queryCondition()">查询</a>
						<a href="javascript:void(0)"class="button write" style="width:50px;"  onclick="organizationBarGrid.queryreset()">重置</a>
					</form>
				</div>
				<div class="fieldset">检索列表</div>
				<div style="float: right;position: relative;top: -30px;">
					<a class="button green dis" style="width:50px;" onclick="organizationBarGrid.add()"> 新增</a>
					<a class="button lightgreen2 dis" style="width:50px;" onclick="organizationBarGrid.update()"> 修改</a>
				</div>
			</div>
			<table idFlag="organizationBarGrid">
				<thead>
					<tr>
						<th par="field:'text'" width="220" >区域名称</th>
						<th par="field:'orgShortCname'" width="150" align="center">区域简称</th>
						<th par="field:'parentOrgCname'" width="150" align="center">上级区域名</th>
						<th par="field:'organOrder'" width="150" align="center">排序号</th>
						<th par="field:'invalidFlag',formatter:invalidFlagformatter " width="150" align="center">是否有效</th>
						<th par="field:'orgCode'" width="150" align="center">区域编码</th>
					</tr>
				</thead>
			</table>
		</div>
		<script type="text/javascript">
			invalidFlagformatter=function(value,row,index){
				if(parseInt(value)==1){
					return "<img src=\"/jubao/images/ok.png\">";
				}else{
					return "<img src=\"/jubao/images/error.png\">";
				}
			};
			$.SUI("organizationBarGrid");
			organizationBarGrid=$.f("table","organizationBarGrid");
			//重置
			organizationBarGrid.queryreset=function(){
				$.f("div","organizationBarGridDiv").f("input","orgName").textbox("setValue","");
				$.f("div","organizationBarGridDiv").f("input","orgCode").textbox("setValue","");
				$.f("div","organizationBarGridDiv").find("input[idFlag='validFlag']").prop("checked", "checked");
			};
			//查询
			organizationBarGrid.queryCondition=function(){
				if (!$.f("div","organizationBarGridDiv").f("form","organizationBarGridForm").form("validate")) {
					dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请检查数据格式', 3000);
					return;
				}
				
				var params = {};
				params.purpose = 1,
				params.node = -1;
				params.filterTxt = "";
				params.filterValue = "";
				
				var orgName = $.f("div","organizationBarGridDiv").f("input","orgName").textbox('getValue');
				var orgCode = $.f("div","organizationBarGridDiv").f("input","orgCode").textbox('getValue');
				var flag="";
				if(orgName!=""||orgCode!=""){
					$("input[idFlag='validFlag']:checked").each(function () {
						flag += $(this).find("input").context.value+";";
				    });
					if(flag){
						params.filterTxt += "invalidFlag;";
						params.filterValue +="1;";
					}else{
						params.filterTxt += "invalidFlag;";
						params.filterValue +="2;";
					}
					if(orgName){
						params.filterTxt += "orgName;";
						params.filterValue += orgName+";";
					}
					if(orgCode){
						params.filterTxt += "orgCode;";
						params.filterValue += orgCode+";";
					}
				}else{
					params.filterTxt = "";
					params.filterValue = "";
				}
				$.ajax({
					 url:sessionlostName+"/petition/petition-organ-info!getOrganColumnsTree.action",    
					 type:"post",
					 data : params,
					 success:function(data){
						$.f("table","organizationBarGrid").treegridSinosoft("loadData",$.toJSON(data));
						$(".organizationBartool").find(".dis").setdisabledEvent(true);
					 }
				});
				
			};
			//树状表初始化
			organizationBarGrid.init=function(){
				var params = {
					filterTxt:"",
					purpose:1,
					filterValue:"",
					node:-1	
				};
				$(".organizationBartool").find(".dis").eq(0).setdisabledEvent(true);
				$(".organizationBartool").find(".dis").eq(1).setdisabledEvent(true);
				$.f("table","organizationBarGrid").treegridSinosoft({
					toolbar:".organizationBartool",
					body:{
					    url:sessionlostName+"/petition/petition-organ-info!getOrganColumnsTree.action",
					    queryParams : params,
					    idField:'id',
					    treeField:'text', 
					    lines:true,
					    //加载之前执行
					    onBeforeLoad:function(row,parm){
					    	if(row){
				   				parm.node=row.id.trim();
				   			}else{
				   				parm.node=-1;
				   			}
					    	parm.purpose=1
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
							if(typeof row._parentId!="undefined"){
								if(row.invalidFlag=="1"){
									$(".organizationBartool").find(".dis").eq(0).setdisabledEvent(false);
									$(".organizationBartool").find(".dis").eq(1).setdisabledEvent(false);
									
								}else{
									$(".organizationBartool").find(".dis").eq(0).setdisabledEvent(true);
									$(".organizationBartool").find(".dis").eq(1).setdisabledEvent(false);
								}
							}else{
								$(".organizationBartool").find(".dis").eq(0).setdisabledEvent(false);
								$(".organizationBartool").find(".dis").eq(1).setdisabledEvent(true);
							}
						},
						onLoadSuccess: function(data){
							var params={
								operationFuncCode : "getOrganColumnsTree",
								operationFuncName : "查询组织设置区域树列表",
								operationBtnName : "查询",
								operationDesc : "查询组织设置区域树列表",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
							}
							saveOperaLog(params);
						}
					}
				})
			}
			organizationBarGrid.add = function(){
				var row=organizationBarGrid.treegridSinosoft("getSelected");
				if(row){
					organizationBarGrid.openWin("add");
				}else{
					dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;请选择一行', 3000);
				    return;
				}
			}
			organizationBarGrid.update = function(){
				var row=organizationBarGrid.treegridSinosoft("getSelected");
				if(row){
					organizationBarGrid.openWin("update");
				}else{
				    dialog.showMiniDialog("self-style", '<i class="icon-faild"></i> 请选择一行', 3000);
				}	
			}
			organizationBarGrid.openWin=function(type){
				$.f("div", "organizationSettingWindow").window({
					title : "新增下级区域",
					modal:true,
					collapsible : false,
					draggable:false,
					maximizable:false,
					minimizable:false,
					closed : true,
					height:350,
					width:800,
					top:150,
					operation:type,
					onClose:function(){
						organizationSettingWindow.findName("input","organizationRelationOid").val("");
			  			organizationSettingWindow.findName("input","relation").val("");
			  			$(".organizationBartool").find(".dis").setdisabledEvent(true);
			  			organizationSettingWindow.oROid = "";
			  		  	organizationSettingWindow.oid = "";
					}
				});
				//记录操作类型
				organizationSettingWindow.operation=type;
				$.f("div", "organizationSettingWindow").window("open");
				organizationSettingWindow.init();
			}
			$(function(){
				organizationBarGrid.init();
			})
		</script>
  </body>
 
</html>
