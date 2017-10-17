<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  </head>
  <body>
  	<div idFlag="userInfoDiv">
		<table class="lookinfocss">
			<tr>
				<td><span style="color: red">用户登录名</span>:<span style="margin-left: 20px;" idFlag="userEname"></span></td>
				<td><span style="color: red">用户姓名</span>:<span style="margin-left: 20px;" idFlag="userCname"></span></td>
			</tr>
			<tr>
				<td><span style="color: red">所属部门</span>:<span style="margin-left: 20px;" idFlag="orgCname"></span></td>
				<td>注册日期:<span style="margin-left: 20px;" idFlag="createDate"></span></td>
			</tr>
			<tr>
				<td>修改日期:<span style="margin-left: 20px;" idFlag="modifyDate"></span></td>
				<td>是否有效:<span style="margin-left: 20px;" idFlag="invalidFlag"></span></td>
			</tr>
			<tr>
				<td>失效日期:<span style="margin-left: 20px;" idFlag="invalidDate"></span></td>
				<td>用户类型:<span style="margin-left: 20px;" idFlag="leaderFlag"></span></td>
			</tr>
			<tr>
				<td colspan="2"><span style="color: red">操作人</span>:<span style="margin-left: 20px;" idFlag="operatorName"></span></td>
			</tr>
			<tr>
				<td colspan="2">用户描述:<span style="margin-left: 20px;" idFlag="userDescription"></span></td>
			</tr>
		</table>
  	</div>
  </body>
</html>