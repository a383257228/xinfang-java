<%@ page language="java" pageEncoding="utf-8"%>

<!DOCTYPE>
<html>
  <head>
  </head>
  <body>
  	<div idflag="systemCodeNodeAddWin" style="display:none;overflow:hidden">
  		<form idFlag="systemCodeNodeForm">
	  		<table class="tablebox" >
	  			<tr>
	  				<td>代码编码<span style="color:red">*</span>:</td>
	  				<td><input class="sinosoft-textbox" idFlag="code" name="code" par="required:true,missingMessage:'代码编码为必填项',width:'149px',readonly:true"></input></td>
	  				<td>代码名称<span style="color:red">*</span>:</td>
	  				<td><input class="sinosoft-textbox" name="name" idFlag="name" par="required:true,missingMessage:'代码名称为必填项',width:'149px'"></input></td>
	  			</tr>
	  			<tr>
	  				<td>是否有效:</td>
	  				<td>
	  					<span class="sinosoft-redioBox" par="labelText:'否' ,value:'N',group:'available'" ></span>&nbsp;&nbsp;
						<span class="sinosoft-redioBox" par="labelText:'是' ,value:'Y',group:'available',checked:'checked'" ></span>
	  				</td>
	  				<td>代码属性<span style="color:red">*</span>:</td>
	  				<td>
	  					<input idFlag="codeProperty" name="codeProperty" class="sinosoft-combobox" par="
	  						width:150,
	  						valueField:'codeProperty',
							textField:'codePropertyName',
							editable:false,
							panelHeight : 70,
							required:true,
							data : [{
								codeProperty:'SelfUse',
								codePropertyName:'本级自用'
							},{
								codeProperty:'DownwardUnify',
								codePropertyName:'向下统一'
							}]
	  					"></input>
	  				</td>
	  			</tr>
	  			<tr style="text-align:center;">
	  				<td colspan="4">
	  					<a idFlag="delete" href="javascript:void(0)"  class="button deepblue"  plain="true" style="width:53px;" onclick="systemCode.submit()">保存</a>
			       		<a idFlag="update" href="javascript:void(0)"  class="button write"  plain="true" style="width:53px;" onclick="systemCode.reset()">重置</a>
	  				</td>
	  			</tr>
	  		</table>
  		</form>
  	</div>
  	<script type="text/javascript">
  		var systemCodeNodeAddWinObj;
  		$.SUI("systemCodeNodeAddWin");
  	</script>
  </body>
</html>