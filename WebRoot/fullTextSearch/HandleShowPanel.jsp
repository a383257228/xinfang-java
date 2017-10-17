<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  </head>
  
  <body>
  	<div idFlag="handlePanel" style="background: #f5f8fd;height:100%">
		<div class="fieldset" >办理信息</div>
		<table class="lookinfocss">
			<tr>
				<td><div class="tdz">办理方式：</div><span idFlag="dealTypeName""></span></td>
			</tr>
			<tr>
				<td><div class="tdz">办理方案：</div><span idFlag="dealSuggestion"></span></td>
			</tr>
			<tr>
				<td><div class="tdz">调查部门：</div><span idFlag="surveyDept"></span></td>
			</tr>
			<tr>
				<td><div class="tdz">调查成员：</div><span idFlag="surveyComposing"></span></td>
			</tr>
			<tr>
				<td><div class="tdz">调查结果：</div><span idFlag="surveyContent"></span></td>
			</tr>
		</table>
		<div class="fieldset">了结信息</div>
		<table  class="lookinfocss">
			<tr>
				<td><div class="tdz">属实程度：</div><span idFlag="realityName"></span></td>
			</tr>
			<tr>
				<td><div class="tdz">了结方式：</div><span idFlag="endTypeName"> </span></td>
			</tr>
		</table>
  	</div>
  </body>
</html>