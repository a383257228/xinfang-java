<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/publicresource/jsp/taglibs.jsp"%>
<html>
<head>
<title>办理信息</title>
<style type="text/css">
table{
	border-collapse: collapse;
}
td {
	height: 30px;
	text-align: center;
	color: #fff;
	border: 1px solid #00BEC8;
	font-size: 14px;
}

textarea {
	background: none;
	font-family: '宋体';
	font-size: 12px;
	font-weight: normal;
	color: #ffffff;
	overflow: auto;
	border: 0;
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
	<table width="606" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;">
		<tr>
			<table width="542" border="0" cellpadding="0" cellspacing="1"
				style="margin:auto">
				<tr></tr>
				<tr>
					<td colspan="2" align="left" valign="middle"
						style="font-family:'宋体';font-size: 15px;font-weight: bold;color:#01BDC8;padding-left: 10px;height:30px;background:#015190;text-align:left">办理信息</td>
				</tr>
				<s:if test="petitionDeal.dealTypeName!=''&&petitionDeal.dealTypeName!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">办理方式</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.dealTypeName"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.dealSuggestion!=''&&petitionDeal.dealSuggestion!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">办理方案</td>
						<td width="100" class="cky_border03">
						<textarea ondblclick="magnifier(this);" rows="5" cols="60"readonly='readonly'><s:property value="petitionDeal.dealSuggestion"/></textarea></td>
					</tr>
				</s:if>
				<s:if test='"0302"==petitionDeal.dealTypeCode'> 
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">转办机构</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.transDealInfo.toRegionName"/></td>
					</tr>
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">承办单位</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.otherDealUnit"/></td>
					</tr>
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">函字函号</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.transpetitionDeal.letterNo"/></td>
					</tr>
				</s:if>
				<s:if test='"0201"==petitionDeal.dealTypeCode||"0202"==petitionDeal.dealTypeCode'> 
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">交办机构</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.assignInfo.toRegionName"/></td>
					</tr>
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">承办单位</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.otherDealUnit"/></td>
					</tr>
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">函字函号</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.assignInfo.letterNo"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.surveyDealInfo.surveyDate!=''&&petitionDeal.surveyDealInfo.surveyDate!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">调查日期</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.surveyDealInfo.surveyDate.toString().substring(0,10)"/></td>
					</tr>
				</s:if> 
				<s:if test="petitionDeal.surveyDealInfo.surveyDept!=''&&petitionDeal.surveyDealInfo.surveyDept!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">调查部门</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.surveyDealInfo.surveyDept"/></td>
					</tr>
				</s:if> 
				<s:if test="petitionDeal.surveyDealInfo.surveyComposing!=''&&petitionDeal.surveyDealInfo.surveyComposing!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">调查成员</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.surveyDealInfo.surveyComposing"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.surveyDealInfo.emcee!=''&&petitionDeal.surveyDealInfo.emcee!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">会议主持人</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.surveyDealInfo.emcee"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.surveyDealInfo.writer!=''&&petitionDeal.surveyDealInfo.writer!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">会议记录人</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.surveyDealInfo.writer"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.surveyDealInfo.surveyContent!=''&&petitionDeal.surveyDealInfo.surveyContent!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">调查结果</td>
						<td width="100" class="cky_border03">
							<textarea ondblclick="magnifier(this);" rows="5" cols="60" readonly='readonly'>
								<s:property value="petitionDeal.surveyDealInfo.surveyContent"/>
							</textarea>
						</td>
					</tr>
					
			   </s:if>
			   <s:if test="petitionDeal.surveyDealInfo.surveyAdvice!=''&&petitionDeal.surveyDealInfo.surveyAdvice!=null">
			   		<s:if test="petitionDeal.dealTypeCode=='01023'||petitionDeal.dealTypeCode=='0000000204'">
						<tr>
							<td width="82" align="left" valign="middle" class="cky_border05">主持人意见</td>
							<td width="100" class="cky_border03">
								<textarea ondblclick="magnifier(this);" rows="5" cols="60" readonly='readonly'>
									<s:property value="petitionDeal.surveyDealInfo.surveyAdvice"/>
								</textarea>
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td width="82" align="left" valign="middle" class="cky_border05">干部任用意见</td>
							<td width="100" class="cky_border03">
								<textarea ondblclick="magnifier(this);" rows="5" cols="60"readonly='readonly'>
									<s:property value="petitionDeal.surveyDealInfo.surveyAdvice"/>
								</textarea>
							</td>
						</tr>
						<tr>
							<td width="82" align="left" valign="middle" class="cky_border05">意见来源</td>
							<td width="100" class="cky_border03"><s:property value="petitionDeal.surveyDealInfo.opinionSources"/></td>
						</tr>
					</s:else>
     			</s:if>
     			<s:if test="petitionDeal.petitionEndInfo.realityName!=''&&petitionDeal.petitionEndInfo.realityName!=null">	
					<tr>
						<td colspan="2" align="left" valign="middle"
							style="font-family: '宋体';font-size: 15px;font-weight: bold;color: #01BDC8;padding-left:10px;height:30px;background:#015190;text-align:left">了结信息</td>
					</tr>
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">属实程度</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.petitionEndInfo.realityName"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.dealPeopleNum!=''&&petitionDeal.petitionEndInfo.dealPeopleNum!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">处理人数（人）</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.petitionEndInfo.dealPeopleNum"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.lostRetrieve!=''&&petitionDeal.petitionEndInfo.lostRetrieve!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">挽回损失(万元)</td>
						<td width="100" class="cky_border03"><fmt:formatNumber   maxFractionDigits='7' value='${petitionDeal.petitionEndInfo.lostRetrieve}'/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.endDate!=''&&petitionDeal.petitionEndInfo.endDate!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">了结日期</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.petitionEndInfo.endDate.toString().substring(0,10)"/></td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.endTypeName!=''&&petitionDeal.petitionEndInfo.endTypeName!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">了结方式</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.petitionEndInfo.endTypeName"/></td>
					</tr>
				</s:if>
				
				
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0101'">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">初步核实</td>
						<td width="100" class="cky_border03">转立案</td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0102'">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">初步核实</td>
						<td width="100" class="cky_border03">了结</td>
					</tr>
				</s:if>
				
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0103'">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">初步核实</td>
						<td width="100" class="cky_border03">谈话提醒</td>
					</tr>
				</s:if>
				
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0104'">
					<td width="82" align="left" valign="middle" class="cky_border05">初步核实</td>
					<td width="100" class="cky_border03">暂存</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0105'">
					<td width="82" align="left" valign="middle" class="cky_border05">初步核实</td>
					<td width="100" class="cky_border03">组织处理</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0201'">
					<td width="82" align="left" valign="middle" class="cky_border05">谈话函询</td>
					<td width="100" class="cky_border03">澄清问题</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0202'">
					<td width="82" align="left" valign="middle" class="cky_border05">谈话函询</td>
					<td width="100" class="cky_border03">谈话提醒</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0203'">
					<td width="82" align="left" valign="middle" class="cky_border05">谈话函询</td>
					<td width="100" class="cky_border03">批评教育</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0204'">
					<td width="82" align="left" valign="middle" class="cky_border05">谈话函询</td>
					<td width="100" class="cky_border03">责令检查</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0205'">
					<td width="82" align="left" valign="middle" class="cky_border05">谈话函询</td>
					<td width="100" class="cky_border03">诫勉谈话</td>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.verifyCode.trim()=='0206'">
					<td width="82" align="left" valign="middle" class="cky_border05">谈话函询</td>
					<td width="100" class="cky_border03">初步核实</td>
				</s:if>
				
				
				
				
				
				<s:if test="petitionDeal.petitionEndInfo.fourPatternName.trim()!=''&&petitionDeal.petitionEndInfo.fourPatternName.trim()!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">四种形态</td>
						<td width="100" class="cky_border03"><s:property value="petitionDeal.petitionEndInfo.fourPatternName"/></td>
					</tr>
				</s:if>
				<s:if test="endReport!=''&&endReport!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">办结报告</td>
						<td width="100" class="cky_border03">
							<textarea ondblclick="magnifier(this);" rows="5" cols="60" readonly='readonly'>
								<s:property value="endReport"/>
							</textarea>
						</td>
					</tr>
				</s:if>
				<s:if test="petitionDeal.petitionEndInfo.beforeEndNotion!=''&&petitionDeal.petitionEndInfo.beforeEndNotion!=null">
					<tr>
						<td width="82" align="left" valign="middle" class="cky_border05">拟结意见</td>
						<td width="100" class="cky_border03">
							<textarea ondblclick="magnifier(this);" rows="5" cols="60" readonly='readonly'>
								<s:property value="petitionDeal.petitionEndInfo.beforeEndNotion"/>
							</textarea>
						</td>
					</tr>
				</s:if>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>