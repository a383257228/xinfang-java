<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
	    <script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
	    <script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
	    <script src="<%=basePath%>/publicresource/js/g2.js"></script>
  
  </head>
   <body>
    <div idFlag="categoryMainTrendAnalysis" style="display: none">
	  		<div id="TrendAnalysis" style="width: 450px; padding-left:20px; color: #FFF; line-height: 25px"><span >对信访分布影响较大的指标有问题属地、信访来源、信访方式、行政级别、问题类别、处置方式、属实程度等因素，基于这些因素，分析信访分布的构成，针对不同因素下的信访分布及信访分布趋势，分析各个机构信访量波动得知整体上信访量基本处于平稳趋势，通过建立模型来预测信访分布，基于不同因素下的信访分布及信访量趋势，构建了不同的规则，按照这些规则来预测未来的信访分布，最后经过模型评估（通过历史数据与预测结果对比）取预测效果最好的模型进行预测，模型可信度较高，可以用来预测未来信访分布。</span></div>		
	</div>
	<script >
		var categoryMainObjTrendAnalysis;
		$.SUI("categoryMainWinTrendAnalysis");
		categoryMainWinTrendAnalysis = {
			init : function(){
				document.body.style.overflow='hidden';
			}
		}
	</script>	
</body>
</html>