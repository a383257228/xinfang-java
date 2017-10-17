<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>批示信息</title>
  </head> 
<body>
  <style>
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
  </style>
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
		<table style="margin-top:10px">
			<s:if test="petitionInfo.petitionSourceCode.indexOf('0102')!=-1">
				<tr>
					<td height="27" colspan="8" align="center" valign="middle"style="font-family: '宋体';font-size: 12px;font-weight: bold;color: #3b82b6;text-decoration: none;letter-spacing: 1px;" style="background-repeat:repeat-x">机关领导交办信息</td>
			  	</tr>
			    <tr>
					<td height="24" colspan="8" align="left" valign="middle"  style="font-family: '宋体';font-size: 12px;font-weight: bold;color: #505050;padding-left: 8px;" style="background-repeat:repeat-x"></td>
			    </tr>
			    <tr>
					<td align="center" valign="middle" bgcolor="#eaf7fd" style="height:35px;width:50px;padding-left: 0px;overflow:auto" class="cky_border05">领导<br/>姓名</td>
					<td bgcolor="#eaf7fd" style="width:50px;overflow:auto" class="cky_border03"><s:property value="petitionInfo.instructor"/></td>
					<td align="center" valign="middle" bgcolor="#eaf7fd" style="width:40px;padding-left: 0px;overflow:auto" class="cky_border05">批示<br/>文号</td>
					<td bgcolor="#eaf7fd" style="width:50px;overflow:auto" class="cky_border03"><s:property value="petitionInfo.instructText"/></td>
					<td align="center" valign="middle" bgcolor="#eaf7fd" style="width:40px;padding-left: 0px;overflow:auto" class="cky_border05">批示<br/>日期</td>
					<td bgcolor="#eaf7fd" style="width:50px;overflow:auto" class="cky_border03"><s:property value="petitionInfo.instructTime.toString().substring(0,10)"/></td>
				</tr>
				<tr>
					<td  height="28" align="center" valign="middle" bgcolor="#eaf7fd" style="width:50px;padding-left: 0px;overflow:auto" class="cky_border05">批示<br/>内容</td>
					<td height="60" colspan="5" bgcolor="#eaf7fd" class="cky_border03">
						<textarea ondblclick="magnifier(this);" rows="5" cols="75" readonly='readonly' style="font-family: '宋体';font-size: 14px;font-weight: normal;color: #fff;padding-left: 0px;overflow:auto;border:0;background-color:#eaf7fd" ><s:property value="petitionInfo.instruction"/></textarea>
					</td>
				</tr>
		    </s:if>
		   
		   
		   
		   
		   
			<tr>
		        <td height="27" colspan="8" align="center" valign="middle" style="font-family: '宋体';font-size: 15px;font-weight: bold;color: #01BDC8;text-decoration: none;letter-spacing: 1px;height:30px;background:#015190"">信访件批示信息</td>
		    </tr>
		    <tr style="margin-top:5px">
		        <td height="24" colspan="8" align="left" valign="middle" style="font-family: '宋体';font-size: 14px;font-weight: bold;color: #fff;padding-left: 8px;text-align:left" >批示信息</td>
		    </tr>
		    <tr>
				<td colspan = "8" height="200" align="left" valign="top" valign="middle" style="font-family: '宋体';font-size: 12px;font-weight: normal;color: #363738;padding-left:8px;" valign="top"width="1024">
					<textarea ondblclick="magnifier(this);" rows="20" cols="105" readonly='readonly' style="background:none;font-family: '宋体';font-size: 14px;font-weight: normal;color: #fff;padding-left: 0px;overflow:auto;border:0;" >
						<s:iterator value="instructSet"><s:property value="instructTime"/><s:property value="leaderName"/><s:if test="leaderName!=creatorName">，（<s:property value="creatorName"/>代）	</s:if> ：<s:property value="instruction"/></s:iterator>
					</textarea>
		       </td>
		   </tr>
	  </table>
  </body>
</html>