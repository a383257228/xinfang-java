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
			<div class="organizationBartool" style="height: 130px;padding:0px;overflow: hidden;">
				<div class="fieldset">检索查询</div>
				<div style="padding:13px 10px">
					<span>区域名称:</span><input idFlag="orgName" class="sinosoft-textbox"  par="validType:'customChinaValidator[10]'" style="width:200px"> 
					<a href="javascript:void(0)"class="button deepblue" style="width:50px;margin-bottom:-3px;"  onclick="organizationBarGrid.queryCondition()">查询</a>
					<a href="javascript:void(0)"class="button write" style="width:50px;margin-bottom:-3px;"  onclick="organizationBarGrid.queryreset()">重置</a>
				</div>
				<div class="fieldset">检索列表</div>
				<div style="float: right;position: relative;top: -35px;">
					<a class="button green dis" style="width:50px;" onclick="organizationBarGrid.add()"> 新增</a>
					<a class="button lightgreen2 dis" style="width:50px;" onclick="organizationBarGrid.update()"> 修改</a>
				</div>
			</div>
			<table idFlag="organizationBarGrid">
				<thead>
					<tr>
						<th par="field:'orgCname'" width="220" >区域名称</th>
						<th par="field:'orgShortCname'" width="150" align="center">区域简称</th>
						<th par="field:'parentName'" width="150" align="center">上级区域名</th>
						<th par="field:'intSort'" width="150" align="center">排序号</th>
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
			organizationBarGrid.IsSelect=false;//是否是查询
			organizationBarGrid.queryreset=function(){
				$.f("div","organizationBarGridDiv").f("input","orgName").textbox("setValue","");
			};
			organizationBarGrid.queryCondition=function(){
				if($.f("div","organizationBarGridDiv").find(".validatebox-invalid").length==0){
					organizationBarGrid.IsSelect=true;
					var value=$.f("div","organizationBarGridDiv").f("input","orgName").textbox("getValue");
					if($.trim(value)==""){
						organizationBarGrid.IsSelect=false;
						organizationBarGrid.init();
						$(".organizationBartool").find(".dis").setdisabledEvent(true);
					}else{
						$.ajax({
							 url:sessionlostName+"/organization/organization-relation-info!queryfindorganization.action",    
							 type:"post",
							 data:{
								 orgCname:$.f("input","orgName").textbox("getValue")
							 },
							success:function(data){
								$.f("table","organizationBarGrid").treegridSinosoft("loadData",$.toJSON(data));
								$(".organizationBartool").find(".dis").setdisabledEvent(true);
							}
						})
					}
				}
			};
			//树状表初始化
			organizationBarGrid.init=function(){
				$(".organizationBartool").find(".dis").eq(0).setdisabledEvent(true);
				$(".organizationBartool").find(".dis").eq(1).setdisabledEvent(true);
				$.f("table","organizationBarGrid").treegridSinosoft({
					toolbar:".organizationBartool",
					body:{
					    url:sessionlostName+"/organization/organization-relation-info!findOrganBylist.action",    
					    idField:"id", 
					    lines:true,
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
							
							
						}
					}
				})
			}
			organizationBarGrid.add = function(){
				var row=organizationBarGrid.treegridSinosoft("getSelected");
				if(row){
					organizationBarGrid.openWin("add");
				}else{
				    dialog.showMiniDialog("self-style", '<i class="icon-faild"></i> 请选择一行', 3000);
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
					operation:type
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
