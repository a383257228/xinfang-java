<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
	<div idFlag="recordListDiv">
		<div class="recordTool" style="padding:0px;overflow: hidden;">
			<div class="fieldset">检索查询</div>
			<div style="padding:1px 10px">
				<form idFLag="recordForm">
					<table class="tablebox">
						<tr>
							<td style="width:20%">
								<input idFlag="authorityAudit_field_property"  class="sinosoft-combobox" style="width:98%;"
								par="
									editable:false,
									valueField:'code',
									textField:'clientOperatorName',
									panelHeight : 80,
									data:　[{
										code: '1',
										clientOperatorName: '被授权人名称'
									},{
										code: '2',
										clientOperatorName: '操作人名称'
									}]
									"  /> 
							</td>
							<td style="width:20%">
								<input idFlag="field_property_value" validType="customChinaValidator[20]"  class="sinosoft-textbox" style="width:98%;"> 
							</td>
							<td style="width:40%">
								<span>开始日期:&nbsp;</span><input idFlag="search_startDate" style="width:220px"  class="sinosoft-datebox" par="editable:false" ></input>
								<span>结束日期:&nbsp;</span><input idFlag="search_endDate" style="width:220px"  class="sinosoft-datebox" par="editable:false" ></input>			
							</td>
							<td style="width:20%">
								<a href="javascript:void(0)" class="button deepblue" style="width:50px; margin-bottom: 8px;"  onclick="record.queryList()">查询</a>
								<a href="javascript:void(0)" class="button write" style="width:50px; margin-bottom: 8px;"  onclick="record.reset()">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="fieldset">检索列表</div>
		</div>
		<div style="height:860px;">
			<table idFlag="recordListGrid"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			record.createList();
		});
	</script>
  </body>
</html>