<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/publicresource/jsp/taglibs.jsp"%>
<html>
<head>
<style type="text/css"> 
	table{
		border-collapse: collapse;
	}
     td{
	     height:25px;
	     text-align:center;
	     color:#fff;
	      border:1px solid #00BEC8;
	     font-size: 14px;
     }
     textarea{
	      background:none;
	      font-family: '宋体';
	      font-size: 12px;
	      font-weight: normal;
	      color: #ffffff;
	      overflow:auto;
	      border:0;
     }
     .cky_border03{
     font-size: 14px;
     }
     .cky .cky_border03,.cky .cky_border05{
	     font-family: '宋体';
	     font-size: 12px;
	     font-weight: normal;
	     color: #ffffff;
	     padding-left:8px;
	     font-weight: bold;
     }
     .cky1 .cky_border03{
	     font-family: '宋体';
	     font-size: 14px;
	     font-weight: normal;
	     color: #ffffff;
	     padding-left:8px;
     }
</style>
</head>
<body>
<div style="margin-top:10px;margin-left:30%">
       <span style="color:#00C9D9;font-size:14px">处理机构：</span>
       
       <select style="width:80px;height:30px;background:#015190;color:#fff;border-color:#01BDC8;font-size:14px">
		    <s:iterator value="regionCodeMap">
		     	<option value="<s:property value='key'/>" >
				    <s:if test="key==petitionInfo.regionCode">
				    
				    </s:if>
		   		 <s:property value='value'/>
		     	</option>
		    </s:iterator>
      </select>
</div>
<div idFlag="infomatation" style="margin-top:10px">
<table style="width:780px;margin:auto;clear:both;overflow-x:hidden;overflow-y:hidden;">
	<div style="float:left; width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:10px;">
		<table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style="float:left;">
		  <tr>
			<td  colspan="8" valign="middle"  class="cky_border05"  style="font-family: '宋体';font-size: 15px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left; " >基本信息</td>
		  </tr>
		  <tr>
			<td width="70"  valign="middle" class="cky_border05">信访编号</td>
			<td width="110"  class="cky_border03"  ><s:property value="petitionInfo.currPetitionNo"/></td>
			<td width="70"  valign="middle" class="cky_border05">条形码</td>
			<td width="110"  class="cky_border03"  ><s:property value="petitionInfo.petitionPrtNo"/></td>
			<td width="70"  valign="middle" class="cky_border05">信访日期</td>
			<td width="110"  class="cky_border03"  ><s:property value="petitionInfo.petitionDate.toString().substring(0,10)"/></td>
			<td width="70"  valign="middle" class="cky_border05">逾期日期</td>
			<td width="80"  class="cky_border03"   ><s:property value="petitionInfo.requireEndDate.toString().substring(0,10)"/></td>
		  </tr>   
		  <tr>
			<td width="70"  valign="middle" class="cky_border05">信访类别</td>
			<td width="120"  class="cky_border03"  ><s:property value="petitionInfo.petitionClassName"/></td>
			<td width="70"  valign="middle" class="cky_border05">信访来源</td>
			<td width="100"  class="cky_border03"  ><s:property value="petitionInfo.petitionSourceName"/></td>
			<td width="70"  valign="middle" class="cky_border05">是否紧急</td>
			<td width="100"  class="cky_border03"><s:if test="petitionInfo.petitionUrgeCode==1">是</s:if><s:else>否</s:else></td>
			<td width="70"  valign="middle" class="cky_border05">是否延期</td>
			<td width="100"  class="cky_border03"  >
				<s:if test="petitionInfo.postponeFlag==1">同意延期</s:if>
	       		<s:elseif test="petitionInfo.postponeFlag==2">正在申请中</s:elseif>
	       		<s:elseif test="petitionInfo.postponeFlag==3">没同意延期</s:elseif>
	       		<s:else>否</s:else>
			</td>
		  </tr>
		  <tr>
			<td width="70"  valign="middle" class="cky_border05" >承办人</td>
			<td width="120"  class="cky_border03"  ><s:property value="petitionInfo.defaultDealerName"/><s:if test="petitionInfo.defaultDealerName!=null&&petitionInfo.defaultDealerName!=''">(<s:property value="petitionInfo.currDeptName"/>)</s:if></td>
			<td width="70"  valign="middle" class="cky_border05" >信访人数</td>
			<td width="100"  class="cky_border03"  ><s:property value="petitionInfo.personNum"/></td>
			<td width="70"  valign="middle" class="cky_border05" >签收日期</td>
			<td width="100"  class="cky_border03"  ><s:property value="petitionInfo.signDate.toString().substring(0,10)"/></td>
			<td width="70"  valign="middle" class="cky_border05" >信访方式</td>
			<td width="80"  class="cky_border03"  style="border-bottom:1px solid #00BEC8"><s:property value="petitionInfo.petitionTypeName"/></td> 
		  </tr>
		</table>
	</div>
	
	<div style="float:left; width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
	  <table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style=" float:left;margin-top:10px" bordercolor="#000000">
		<tr>
			<td colspan="6" valign="middle"  class="cky_border05"  style="font-size:14px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left; " >其他部门交办信息</td>
		</tr>
		<tr>
			<td width="80"  valign="middle" class="cky_border05">信访函号</td>
			<td width="110"  class="cky_border03"  ><s:property value="petitionInfo.letterNo"/></td>
			<td width="80"  valign="middle" class="cky_border05">交办日期</td>
			<td width="110"  class="cky_border03"  ><s:property value="petitionInfo.assignedDate.toString().substring(0,10)"/></td>
			<td width="80"  valign="middle" class="cky_border05">交办截止日期</td>
			<td width="110"  class="cky_border03" "><s:property value="petitionInfo.assignedRequireEndDate.toString().substring(0,10)"/></td>
		</tr>
		<tr>
	        <td width="80"  valign="middle" class="cky_border05" >函件内容</td>
	        <td width="100" height="60" colspan="5" class="cky_border03" >
	        	<textarea  rows="5" cols="80" readonly='readonly'><s:property value="petitionInfo.letterContent"/></textarea>
	        </td>
	    </tr>
	  </table>
	</div>
	
	<div style="float:left; width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
	  <table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style=" float:left;margin-top:10px" bordercolor="#000000">
		<td  colspan="8" valign="middle"  class="cky_border05"  style="font-size:14px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190; text-align:left" >本机关领导交办信息</td>
		<tr>
			<td width="80"  valign="middle" class="cky_border05">领导姓名</td>
			<td width="120"  class="cky_border03"><s:property value="petitionInfo.instructor"/></td>
			<td width="80"  valign="middle" class="cky_border05">批示文号</td>
			<td width="120"  class="cky_border03"><s:property value="petitionInfo.instructText"/></td>
			<td width="80"  valign="middle" class="cky_border05">批示日期</td>
			<td width="120"  class="cky_border03" ><s:property value="petitionInfo.instructTime.toString().substring(0,10)"/></td>
		</tr>
		<tr>
	        <td width="80"  valign="middle" class="cky_border05" >批示内容</td>
	        <td width="100" height="60" colspan="7" class="cky_border03" >
	        	<textarea ondblclick="magnifier(this);" rows="5" cols="80" readonly='readonly'><s:property value="petitionInfo.instruction"/></textarea>
	        </td>
	    </tr>
	  </table>
	</div>
	
	<div style="float:left; width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
		<table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style=" float:left;margin-top:10px" bordercolor="#000000">
			  <tr>
				<td  colspan="8" valign="middle"  class="cky_border05"  style="font-size:14px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190; text-align:left" >被反映人对象信息</td>
			  </tr>
			  <tr>
				<td width="70"  valign="middle" class="cky_border05">对象类型</td>
				<td width="120"  class="cky_border03"><s:property value="petitionInfo.accusedTypeName"/></td>
				<td width="70"  valign="middle" class="cky_border05">姓名</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accusedName"/></td>
				<td width="70"  valign="middle" class="cky_border05">身份证号</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accusedCardId"/></td>
				<td width="70"  valign="middle" class="cky_border05">行政级别</td>
				<td width="80"  class="cky_border03" ><s:property value="petitionInfo.accusedClassName"/></td>
			  </tr>
			  <tr>
				<td width="70"  valign="middle" class="cky_border05">职务</td>
				<td width="120"  class="cky_border03"><s:property value="petitionInfo.accusedPositionName"/></td>
				<td width="70"  valign="middle" class="cky_border05">单位或地址</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accusedWorkUnit"/></td>
				<td width="70"  valign="middle" class="cky_border05">所属区域</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accusedRegionName"/></td>
				<td width="70"  valign="middle" class="cky_border05">单位性质</td>
				<td width="80"  class="cky_border03"  ><s:property value="petitionInfo.accusedWorkTypeName"/></td>
			  </tr>
			  <tr>
				<td width="70"  valign="middle" class="cky_border05">监察对象</td>
				<td width="120"  class="cky_border03"  ><s:property value="petitionInfo.accusedDisciplineName"/></td>
				<td width="70"  valign="middle" class="cky_border05">性别</td>
				<td width="100"  class="cky_border03"  ><s:property value="petitionInfo.accusedSexName"/></td>
				<td width="70"  valign="middle" class="cky_border05">出生日期</td>
				<td width="100"  class="cky_border03"  colspan="3" "><s:property value="petitionInfo.accusedBirthday.toString().substring(0,10)"/></td>
			  </tr>
			  <tr>
				<td width="70"  valign="middle" class="cky_border05"  >民族</td>
				<td width="120"  class="cky_border03" ><s:property value="petitionInfo.accusedNationName"/></td>
				<td width="82"  valign="middle" class="cky_border05" >是否本级干部</td>
				<td width="100"  class="cky_border03" >
					<s:if test="petitionInfo.accusedLocalCadreFlag==1">是</s:if><s:else>否</s:else>
				</td>
				<td width="70"  valign="middle" class="cky_border05" >是否负责人</td>
				<td width="100"  class="cky_border03"  colspan="3" >
					<s:if test="petitionInfo.accusedDevianceFlag==1">是</s:if><s:else>否</s:else>
				</td>
			  </tr>
		</table>
	</div>

	<div style="float:left; width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
		<table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style=" float:left;margin-top:10px" bordercolor="#000000">
			  <tr>
				<td colspan="8" valign="middle"  class="cky_border05"  style="font-size:14px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190; text-align:left" >其他被反映对象信息</td>
			  </tr>
			  <tr class="cky">
	              <td width="30" class="cky_border05" valign="middle"  >序号</td>
	              <td width="60" class="cky_border05" valign="middle"  >对象类型</td>
	              <td width="100" class="cky_border05" valign="middle"  >姓名/名称</td>
	              <td width="100" class="cky_border05" valign="middle"  >行政级别</td>
	              <td width="100" class="cky_border05" valign="middle"  >单位或地址</td>
	              <td width="100" class="cky_border05" valign="middle"  >所属区域</td>
	              <td width="100" class="cky_border05" valign="middle"  >职务</td>
	              <td width="50" class="cky_border05" valign="middle" >性别</td>
			  </tr>
			  <s:iterator value="petitionAccusedInfoList" var="accusedInfo" status="status">
		          <tr class="cky1">
		                <td class="cky_border03"  ><s:property value="#status.count"/></td>
		                <td class="cky_border03"  ><s:property value="#accusedInfo.accusedTypeName"/></td>
		                <td class="cky_border03"  ><s:property value="#accusedInfo.accusedName"/></td>
		                <td class="cky_border03"  ><s:property value="#accusedInfo.accusedClassName"/></td>
		                <td class="cky_border03" ><s:property value="#accusedInfo.accusedWorkUnit"/></td>
		                <td class="cky_border03" ><s:property value="#accusedInfo.accusedRegionName"/></td>
		                <td class="cky_border03" ><s:property value="#accusedInfo.accusedPositionName"/></td>
		                <td class="cky_border03"  ><s:property value="#accusedInfo.accusedSexName"/></td>
		          </tr>
	          </s:iterator>
		</table>
    </div>
		
	<div style="float:left;width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
		<table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style="float:left;margin-top:10px">
			<tr>
				<td  colspan="8" valign="middle"  class="cky_border05"  style="font-family: '宋体'; font-size: 15px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left" >反映人信息</td>
		    </tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">姓名</td>
				<td width="110"  class="cky_border03"  ><s:property value="petitionInfo.accuserName"/></td>
				<td width="80"  valign="middle" class="cky_border05">是否老访户</td>
				<td width="100"  class="cky_border03"  ><s:if test="petitionInfo.accuserAppealFlag==1">是</s:if><s:else>否</s:else></td>
				<td width="70"  valign="middle" class="cky_border05">行政级别</td>
				<td width="100"  class="cky_border03"  ><s:property value="petitionInfo.accuserClassName"/></td>
				<td width="70"  valign="middle" class="cky_border05">联系电话</td>
				<td width="80"  class="cky_border03" style=" font-size: 14px;word-break:break-all;"><s:property value="petitionInfo.accuserTelOne"/></td>
			</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">曾上访何处</td>
				<td width="110"  class="cky_border03"><s:property value="petitionInfo.everAccusePlace"/></td>
				<td width="80"  valign="middle" class="cky_border05">何部门处理过</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.everDealDept"/></td>
				<td width="70"  valign="middle" class="cky_border05">单位或地址</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accuserWorkUnit"/></td>
				<td width="70"  valign="middle" class="cky_border05">所属区域</td>
				<td width="80"  class="cky_border03"  ><s:property value="petitionInfo.accuserRegionName"/></td>
			</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">职务</td>
				<td width="110"  class="cky_border03"><s:property value="petitionInfo.accuserPositionName"/></td>
				<td width="80"  valign="middle" class="cky_border05">电子邮件</td>
				<td width="100"  class="cky_border03" style="font-size: 14px;word-break:break-all;"><s:property value="petitionInfo.accuserEmail"/></td>
				<td width="70"  valign="middle" class="cky_border05">邮政编码</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accuserZip"/></td>
				<td width="70"  valign="middle" class="cky_border05">通讯地址</td>
				<td width="80"  class="cky_border03"  ><s:property value="petitionInfo.accuserAddr"/></td>
			</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">民族</td>
				<td width="110"  class="cky_border03"><s:property value="petitionInfo.accuserNationName"/></td>
				<td width="80"  valign="middle" class="cky_border05">性别</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accuserSexName"/></td>
				<td width="70"  valign="middle" class="cky_border05">政治面貌</td>
				<td width="100"  class="cky_border03"><s:property value="petitionInfo.accuserPartyName"/></td>
				<td width="70"  valign="middle" class="cky_border05">职业</td>
				<td width="80"  class="cky_border03"  ><s:property value="petitionInfo.accuserOccupationName"/></td>
			</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">身份证号</td>
				<td width="110"  class="cky_border03"><s:property value="petitionInfo.accuserCardId"/></td>
				<td width="80"  valign="middle" class="cky_border05">涉外及港澳台</td>
				<td width="100"  class="cky_border03" colspan="5"  ><s:if test="petitionInfo.isSwgat==1">是</s:if><s:else>否</s:else></td>
			</tr>
			<tr>
				<td width="80"  valign="middle" class="cky_border05" >异常情况</td>
				<td colspan='3' width="190"  class="cky_border03"style="border-bottom:1px solid #00BEC8;">
					<textarea ondblclick="magnifier(this);" rows="6" cols="30" readonly='readonly'><s:property value="petitionInfo.exceptionCond"/></textarea>
				</td>
				<td width="70"  valign="middle" class="cky_border05" >处理情况</td>
				<td colspan='3' width="190"  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="6" cols="30" readonly='readonly' ><s:property value="petitionInfo.exceptionHandling"/></textarea>
				</td>
			</tr>
		</table>
	</div>
	
	<div style="float:left; width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
		<table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style="float:left;margin-top:10px" bordercolor="#000000">
			  <tr>
				<td  colspan="8" valign="middle"   class="cky_border05"  style="font-family: '宋体'; font-size: 15px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left" >其他反映人信息</td>
			  </tr>
			  <tr class="cky">
	              <td width="50" class="cky_border03" valign="middle" >序号</td>
	              <td width="120" class="cky_border03" valign="middle" >所属区域</td>
	              <td width="120" class="cky_border03" valign="middle" >单位或地址</td>
	              <td width="80" class="cky_border03" valign="middle" >姓名</td>
	              <td width="70" class="cky_border03" valign="middle" >性别</td>
	              <td width="120" class="cky_border03" valign="middle" >联系方式</td>
	              <td width="100" class="cky_border03" valign="middle" >政治面貌</td>
	              <td width="100" class="cky_border03" valign="middle" style=" border-bottom:1px solid #00BEC8">职业</td>
			  </tr>
			  <s:iterator value="petitionAccuserInfoList" var="accuserInfo" status="status">
	              <tr class="cky1">
		                <td class="cky_border03" ><s:property value="#status.count"/></td>
		                <td class="cky_border03" ><s:property value="#accuserInfo.accuserRegionName"/></td>
		                <td class="cky_border03" ><s:property value="#accuserInfo.accuserWorkUnit"/></td>
		                <td class="cky_border03" ><s:property value="#accuserInfo.accuserName"/></td>
		                <td class="cky_border03" ><s:property value="#accuserInfo.accuserSexName"/></td>
		                <td class="cky_border03" ><s:property value="#accuserInfo.accuserTelOne"/></td>
		                <td class="cky_border03" ><s:property value="#accuserInfo.accuserPartyName"/></td>
		                <td class="cky_border03" style=" border-bottom:1px solid #00BEC8"><s:property value="#accuserInfo.accuserOccupationName"/></td>
	              </tr>
              </s:iterator>
		</table>
	</div>
	
	<div style="float:left;width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
		<table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style="float:left;margin-top:10px">
			<tr>
    			<td  colspan="8" valign="middle"  class="cky_border05"  style="font-family: '宋体'; font-size: 15px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left" >反映问题</td>
    		</tr>
			<tr>
        		<td width="70" valign="middle" class="cky_border05">原处理机关</td>
				<td width="170" class="cky_border03"><s:property value="petitionInfo.originDealRegion"/></td>
				<td width="70" valign="middle" class="cky_border05">原党纪处分</td>
				<td width="170" class="cky_border03"><s:property value="petitionInfo.partyMeasureName"/></td>
				<td width="70" valign="middle" class="cky_border05">原政纪处分</td>
				<td width="170" class="cky_border03"  ><s:property value="petitionInfo.executiveMeasureName"/></td>
      		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05">原处理情况</td>
				<td width="170" colspan="5"  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="5" cols="80" readonly='readonly' style="font-family: '宋体'; font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;height:100px;width:700px;border:0;background:none" ><s:property value="petitionInfo.originSolution"/></textarea>
				</td>
      		</tr>
    		<tr>
    			<td width="70"  valign="middle" class="cky_border05">主问题类别</td>
				<td width="170"  class="cky_border03"><s:property value="petitionInfo.issueTypeName"/></td>
				<td width="70"  valign="middle" class="cky_border05">次问题类别</td>
				<td width="170"  class="cky_border03" ><s:property value="petitionInfo.otherIssueTypeName"/></td>
				<td width="70"  valign="middle" class="cky_border05">关键字</td>
				<td width="170"  class="cky_border03"  ><s:property value="petitionInfo.keyWordContent"/></td>
    		</tr>
    		<tr>
    			<td width="70"  valign="middle" class="cky_border05">问题属地</td>
				<td width="170" colspan='7'  class="cky_border03"  ><s:property value="petitionInfo.issueRegionName"/></td>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">问题备注</td>
				<td width="170" colspan='7'  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体'; font-size: 12px;font-weight: normal;color: #ffffff; overflow-x:hidden;border:0;background:none" ><s:property value="petitionInfo.remark"/></textarea>
				</td>
			</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">回复信息</td>
				<td width="170" colspan='7'  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体'; font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;"><s:property value="petitionInfo.returnContent"/></textarea>
				</td>
    		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05" >问题描述</td>
        		<td width="725" colspan="5"  class="cky_border03"  >
        			<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体'; font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;"></textarea>
        			<span idFlag="issueContent"></span>
        		</td>
      		</tr>
		</table>
	</div>
	
	<div style="float:left;width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
	     <table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style="float:left;margin-top:10px">
			<tr>
    			<td  colspan="6" valign="middle"  class="cky_border05"   style="font-size:14px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190; text-align:left" >接访信息</td>
    		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05">接访人</td>
				<td width="170" colspan="5"  class="cky_border03"  ><s:property value="petitionInfo.receiptor"/></td>
      		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05">接访情况</td>
				<td width="170" colspan="5"  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体';font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;"><s:property value="petitionInfo.receptionCond"/></textarea>
				</td>
      		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05" >接访意见</td>
				<td width="170" colspan="5"  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体';font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;"><s:property value="petitionInfo.receptionNotion"/></textarea>
				</td>
      		</tr>
	    </table>
	</div>
	
	<div style="float:left;width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
	    <table width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style="float:left;margin-top:10px">
			<tr>
    			<td  colspan="6" valign="middle"  class="cky_border05"  style=" font-family: '宋体';font-size: 15px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left" >接听信息</td>
    		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05">接听人</td>
				<td width="170" colspan="5"  class="cky_border03"  ><s:property value="petitionInfo.receiptor"/></td>
      		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05">接听情况</td>
				<td width="170" colspan="5"  class="cky_border03"  >
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体';font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;"><s:property value="petitionInfo.receptionCond"/></textarea>
				</td>
      		</tr>
			<tr>
        		<td width="70"  valign="middle" class="cky_border05" style="border-bottom:1px solid #00BEC8">接听意见</td>
				<td width="170" colspan="5"  class="cky_border03" style=" border-bottom:1px solid #00BEC8">
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体';font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;"><s:property value="petitionInfo.receptionNotion"/></textarea>
				</td>
      		</tr>
	    </table>
	</div>
	
	<div style="float:left;width:750px;position:relative; margin:auto; clear:both; overflow:hidden; top:20px;">
		<table  width="750" border="0" cellpadding="0" cellspacing="1" class="cky_border01" style=" float:left;margin-top:10px">
			<tr>
    			<td  colspan="6" valign="middle"  class="cky_border05"   style="font-size:14px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190; text-align:left" >拟办信息</td>
    		</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05">拟办方式</td>
				<td width="525"  class="cky_border03" style=""><s:property value="petitionInfo.pretreatmentName"/></td>
			</tr>
			<tr>
				<td width="70"  valign="middle" class="cky_border05" >
					<s:if test="petitionInfo.isSecretaryFlag==0">拟办意见</s:if><s:else>集体研判意见</s:else>
				</td>
				<td width="525"  class="cky_border03" style="border-bottom:1px solid #00BEC8">
					<textarea ondblclick="magnifier(this);" rows="5" cols="115" readonly='readonly' style="font-family: '宋体';font-size: 12px;font-weight: normal;color: #ffffff; overflow:auto;border:0;" ><s:property value="petitionInfo.pretreatment"/></textarea>
				</td>
			</tr>
		</table>
	</div>
</table>
</div>
</body>
</html>
