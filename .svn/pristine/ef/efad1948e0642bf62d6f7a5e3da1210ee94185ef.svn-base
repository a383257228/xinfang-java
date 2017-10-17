<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  </head>
  
  <body>
  	<div idFlag="petitionBasicInfo">
		<div class="fieldset" >基本信息</div>
		<table class="lookinfocss">
			<tr>
				<td style="width:35%"><div class="tdz">信访编号：</div><span idFlag="currPetitionNo"></span></td>
				<td style="width:24%"><div class="tdz">条形码：</div><span idFlag="petitionPrtNo"></span></td>
				<td style="width:24%"><div class="tdz">信访日期：</div><span idFlag="petitionDate"></span></td>
				<td style="width:17%"><div class="tdz">逾期日期:</div><span idFlag="requireEndDate"></span></td>
			</tr>
			<tr>
				<td style="width:25%"><div class="tdz">信访类别：</div><span idFlag="petitionClassName"></span></td>
				<td style="width:25%"><div class="tdz">信访来源：</div><span idFlag="petitionSourceName"></span></td>	
				<td style="width:25%"><div class="tdz">是否紧急：</div><span idFlag="petitionUrgeName"></span></td>
				<td style="width:25%"><div class="tdz">是否延期：</div><span idFlag="postponeFlag"></span></td>	
			</tr>
			<tr>
				<td style="width:25%"><div class="tdz">承办人：</div><span idFlag="defaultDealerName"></span></td>
				<td style="width:25%"><div class="tdz">信访人数：</div><span idFlag="petitionNum"></span></td>
				<td style="width:25%"><div class="tdz">签收日期：</div><span idFlag="signDate"></span></td>
				<td style="width:25%"><div class="tdz">信访方式：</div><span idFlag="petitionTypeName"></span></td>
			</tr>
		</table>
		<div class="fieldset">被信访人对象信息</div>
		<table  class="lookinfocss">
			<tr>
				<td style="width:25%"><div class="tdz">对象类型：</div><span idFlag="accusedTypeName"></span></td>
				<td style="width:25%"><div class="tdK">姓名 ：</div><span idFlag="accusedName"> </span></td>	
				<td style="width:25%"><div class="tdK">身份证号：</div><span idFlag="accusedCardId"></span></td>
				<td style="width:25%"><div class="tdK">行政级别：</div><span idFlag="accusedClassName"></span></td>
			</tr>
			<tr>
				<td style="width:25%"><div class="tdK">职务：</div><span idFlag="accusedPositionName"> </span></td>
				<td style="width:25%"><div class="tdK">单位或地址：</div><span idFlag="accusedWorkUnit"></span></td>		
				<td style="width:25%"><div class="tdK">所属区域：</div><span idFlag="accusedRegionName"></span></td>		
				<td style="width:25%"><div class="tdK">单位性质：</div><span idFlag="accusedWorkTypeName"></span></td>	
			</tr>
			<tr>
				<td style="width:25%"><div class="tdK">监督对象：</div><span idFlag="accusedDisciplineName"></span></td>
				<td style="width:25%"><div class="tdK">性别：</div><span idFlag="accusedSexName"></span></td>
				<td style="width:25%"><div class="tdK">出生日期：</div><span idFlag="accusedBirthday"></span></td>
				<td style="width:25%"><div class="tdK">民族：</div><span idFlag="accusedNationName"></span></td>
			</tr>
			<tr>
				<td style="width:25%"><div class="tdK">是否本级干部：</div><span idFlag="accusedLocalCadreFlag"> </span></td>
				<td style="width:25%"><div class="tdK">是否负责人：</div><span idFlag="accusedDevianceFlag"></span></td>		
			</tr>
		</table>
		<div class="fieldset">反映人信息</div>
		<table class="lookinfocss">
			<tr>
				<td style="width:25%"><div class="tdK">姓名 ：</div><span idFlag="accuserName"> </span></td>
				<td style="width:25%"><div class="tdK">是否老访户 ：</div><span idFlag="accuserAppealFlag"> </span></td>	
				<td style="width:25%"><div class="tdK">行政级别 ：</div><span idFlag="accuserClassName"> </span></td>	
				<td style="width:25%"><div class="tdK">联系电话：</div><span idFlag="accuserTelOne"></span></td>		
			</tr>
			<tr>
				<td style="width:25%"><div class="tdK">曾上访问处：</div><span idFlag="everAccusePlace"></span></td>
				<td style="width:25%"><div class="tdK">何部门处理过：</div><span idFlag="everDealDept"></span></td>
				<td style="width:25%"><div class="tdK">单位或地址：</div><span idFlag="accuserWorkUnit"></span></td>
				<td style="width:25%"><div class="tdK">所属区域：</div><span idFlag="accuserRegionName"></span></td>
			</tr>
			<tr>
				<td style="width:25%"><div class="tdK">职务：</div><span idFlag="accuserPositionName"></span></td>		
				<td style="width:25%"><div class="tdK">电子邮件：</div><span idFlag="accuserEmail"></span></td>
				<td style="width:25%"><div class="tdK">邮政编码：</div><span idFlag="accuserZip"></span></td>
				<td style="width:25%"><div class="tdK">通讯地址：</div><span idFlag="accuserAddr"></span></td>	
			</tr>
			<tr>
				<td style="width:25%"><div class="tdK">民族：</div><span idFlag="accuserNationName"></span></td>		
				<td style="width:25%"><div class="tdK">性别：</div><span idFlag="accuserSexName"></span></td>
				<td style="width:25%"><div class="tdK">政治面貌：</div><span idFlag="accuserPartyName"></span></td>
				<td style="width:25%"><div class="tdK">职业：</div><span idFlag="accuserOccupationName"></span></td>	
			</tr>
			<tr>
				<td colspan="2"><div class="tdK">身份证号：</div><span idFlag="accuserCardId"></span></td>		
				<td colspan="2"><div class="tdK">涉外及港澳台：</div><span idFlag="isSwgat"></span></td>
			</tr>
			<tr>
				<td colspan="2"><div class="tdK">异常情况：</div><span idFlag="exceptionCond"></span></td>		
				<td colspan="2"><div class="tdK">处理情况：</div><span idFlag="exceptionHandling"></span></td>
			</tr>
		</table>
		<div class="fieldset">反映问题信息</div>
		<table  class="lookinfocss">
			<tr>
				<td style="width:40%"><div class="tdK">主问题类别：</div><span idFlag="issueTypeName"> </span></td>
				<td style="width:40%"><div class="tdK">次问题类别：</div><span idFlag="otherIssueTypeName"> </span></td>
				<td style="width:20%"><div class="tdK">关键字：</div><span idFlag="keyWordContent"> </span></td>	
			</tr>
			<tr>
				<td colspan="3"><div class="tdK">问题属地：</div><span idFlag="issueRegionName"></span></td>	
			</tr>
			<tr>
				<td colspan="3"><div class="tdK">问题备注：</div><span idFlag="remark"></span></td>
			</tr>
			<tr>
				<td colspan="3"><div class="tdK">回复信息：</div><span idFlag="returnContent"></span></td>
			</tr>
			<tr>
				<td colspan="3"><div class="tdK">问题描述：</div><span idFlag="issueContent"> </span></td>	
			</tr>
		</table>
		<div class="fieldset">拟办信息</div>
		<table  class="lookinfocss">
			<tr>
				<td style="width:100%"><div class="tdK">拟办方式：</div><span idFlag="pretreatmentName"></span></td>
			</tr>
			<tr>
				<td style="width:100%"><div class="tdK">拟办意见：</div><span idFlag="pretreatment"> </span></td>	
			</tr>
		</table>
  	</div>
  </body>
</html>