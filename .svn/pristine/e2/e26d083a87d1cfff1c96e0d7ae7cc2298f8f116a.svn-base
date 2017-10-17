<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<style>
	</style>
	<div idFlag="operationQuery">
		<div class="querytool"style="padding:0px;overflow: hidden;">
			<div class="fieldset">检索查询</div>
			<div style="padding:3px 10px">
				
				<form idFLag="operationQueryForm">
					<table class="tablebox">
						<tr>
							<td>
								<span>用户:&nbsp;</span><input idFlag='userName' name="userId" class="sinosoft-combobox" style="width:200px"
								par="
									editable:false,
									valueField:'userId',
									textField:'userName',
									panelHeight : 200,
									url : '/jubao/petition/petition-organ-info!queryLogOnUsers.action' 
									"  />  
							</td>
							<td>
								<span>操作功能:&nbsp;</span><input idFlag="operationFuncName"  class="sinosoft-textbox" style="width:200px"> 
							</td>
							<td>
								<span>操作结果:&nbsp;</span><input idFlag='operationResultName'  class="sinosoft-combobox" style="width:200px"
								par="
									editable:false,
									valueField:'operationResultCode',
									textField:'operationResultName',
									panelHeight : 90,
									data:[{
										operationResultCode :'',
										operationResultName:'全部'
									},{
										operationResultCode :'1',
										operationResultName:'成功'
									},{
										operationResultCode :'2',
										operationResultName:'失败'
									}]
									"  /> 
							</td>
							<td>
								 <span>操作类型:&nbsp;</span><input idFlag='operationTypeName'  class="sinosoft-combobox" style="width:200px"
								 par="
								 	editable:false,
									valueField:'operationTypeCode',
									textField:'operationTypeName',
									panelHeight : 120,
									data : [{
										operationTypeCode :'add',
										operationTypeName:'新增'
									},{
										operationTypeCode:'modify',
										operationTypeName:'修改'
									},{
										operationTypeCode:'delete',
										operationTypeName:'删除'
									},{
										operationTypeCode:'query',
										operationTypeName:'查询'
									}]
									"  /> 
							</td>
							<td>
							     <span>操作描述:&nbsp;</span><input idFlag="operationDesc"  class="sinosoft-textbox" style="width:200px">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								 <span>操作时间:&nbsp;</span><input idFlag="operationTime_startQ" style="width:235px"  class="sinosoft-datebox" par="editable:false" ></input>
								 <span>至:&nbsp;</span><input idFlag="operationTime_endQ" style="width:235px"  class="sinosoft-datebox" par="editable:false" ></input>			
							</td>	
							<td>
								 <span>统计机构:&nbsp;</span><input idFlag='deptName' name='deptId'  class="sinosoft-combobox" style="width:200px"
								  par="
								  	editable:false,
									valueField:'deptId',
									textField:'deptName',
									panelHeight : 150,
									url : '/jubao/petition/petition-organ-info!queryDeptInfoAfterGroup.action' 
									"  /> 
							</td>
							<td>
								<a href="javascript:void(0)" class="button deepblue" style="width:50px;margin-bottom:8px"  onclick="operationQuery.queryList()">查询</a>
								<a href="javascript:void(0)"class="button write" style="width:50px;margin-bottom:8px"  onclick="operationQuery.reset()">重置</a>
							</td>
						</tr>
					</table>
				</form>
				
			</div>
			<div class="fieldset">检索列表</div>
		</div>
		<div style="height:860px;">
			<table idFlag="operationQueryGrid"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			operationQuery.createList();
		});
	</script>
  </body>
</html>