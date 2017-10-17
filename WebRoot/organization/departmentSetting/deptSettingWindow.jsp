<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
  <head>
  
  </head>
  <body>
		<div  idFlag="deptSettingWindow" style=" display:none;background-color: #f5f8fd">
		<form method="post"> 
				<table class="tablebox" style="width:90%;margin: auto;border: 0px;padding: 5px;">
					<tr>
						<td>
							部门编码<font style="color:red">*</font>:
						</td>
						<td>
							<input type="hidden" name="id"></input>
							<input class="sinosoft-textbox" style="width: 200px"  name="orgCode" par="readonly:true"></input>
						</td>
						<td>
							部门名称<font style="color:red">*</font>:
						</td>
						<td>
							<input class="sinosoft-textbox" style="width: 200px"  name="text" required="true"  prompt="最多输入32个字符" par="validType:'customChinaValidator[32]'"></input>
						</td>
					</tr>
					<tr>
						<td>
							部门简称<font style="color:red">*</font>:
						</td>
						<td>
							<input class="sinosoft-textbox" style="width: 200px"    name="orgShortCname" required="true"  prompt="最多输入16个字符" par="validType:'customChinaValidator[16]'"></input>
						</td>
						<td>
							部门显示顺序<font style="color:red">*</font>:
						</td>
						<td>
							<input class="sinosoft-textbox"  style="width: 200px" name="organOrder"></input>
						</td>
					</tr>
					<tr>
						<td>
							是否有效:
						</td>
						<td>
							<span class="sinosoft-checkedbox"  par="labelText:'',group:'state' "></span>
						</td>
						<td>
							上级部门名称:
						</td>
						<td>
							<input type="hidden" name="parentId"></input>
							<input type="hidden" name="organizationRelationOid"></input>
							<input class="sinosoft-textbox"  style="width: 200px"  par="readonly:true" name="parentOrgCname"></input>
						</td>
					</tr>
					<tr>
						<td>
							<span style="
							    position: relative;
							    top: -34px;
							">
								部门描述:
							</span>
						</td>
						<td colspan="3">
							<div>
							<input class="sinosoft-textbox" name="description" par="multiline:true,validType:'singleTextValidator[65]'"  prompt="最多输入65个字符"  style="width:580px;height: 80px;"></input>
							</div>
						</td>
					</tr>
					<tr>
						<td  colspan="4">
							<div style="text-align: center;margin-top: 5px;" >
									<!-- <a class="button deepblue" style="margin-left: 20px;width:53px;">保存信息</a> -->
									<a class="button deepblue" style="margin-left: 20px;width:53px;">保存</a>
									<a class="button write" style="width:53px;">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</form>
	    </div>
  </body>
</html>
