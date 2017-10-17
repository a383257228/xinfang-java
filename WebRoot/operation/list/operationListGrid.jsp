<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<style>
	</style>
	<div idFlag="operationList">
		<div class="listtool" style="padding:0px;overflow: hidden;">
			<div class="fieldset">检索查询</div>
			<div style="padding:3px 10px">
				
				<form idFLag="operationListForm">
					<table class="tablebox">
						<tr>
							<td>
								<span>用户名:&nbsp;</span><input idFlag="userNameQ" validType="customChinaValidator[30]" class="sinosoft-textbox" style="width:200px"> 
							</td>
							<td>
								<span>机构:&nbsp;</span><input idFlag="deptNameQ" validType="customChinaValidator[30]" class="sinosoft-textbox" style="width:200px"> 
							</td>
							<td>
								<span>操作IP:&nbsp;</span><input idFlag="operatIPQ" validType="customChinaValidator[30]" class="sinosoft-textbox" style="width:200px"> 
							</td>
							<td>
								<span>操作时间:&nbsp;</span><input idFlag="stateTime_startQ" style="width:235px"  class="sinosoft-datebox" par="editable:false" ></input>
								<span>至:&nbsp;</span><input idFlag="stateTime_endQ" style="width:235px"  class="sinosoft-datebox" par="editable:false" ></input>			
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<a href="javascript:void(0)" class="button deepblue" style="width:50px;"  onclick="operationList.queryList()">查询</a>
								<a href="javascript:void(0)" class="button write" style="width:50px;"  onclick="operationList.reset()">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="fieldset">检索列表</div>
		</div>
		<div style="height:860px;">
			<table idFlag="operationListGrid"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			operationList.createList();
		});
	</script>
  </body>
</html>