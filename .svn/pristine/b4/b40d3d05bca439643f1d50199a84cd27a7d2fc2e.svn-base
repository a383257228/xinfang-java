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
    <div idFlag="categoryMainTrend" style="display: none">
    		<a onClick="analysisIn()" class="button deepblue" style="width:84px;height:34px;line-height:34px;font-size:16px;text-align:center;margin-left: 85%;border:1px solid #49D9FE " plain="true" idFlag="analysis">分析报告</a>
	  		<div id="trend"></div>	
	  		<div id = "fx" style = "display:none">
				 
			</div>	
	</div>
	<script >
		var categoryMainObjTrend;
		$.SUI("categoryMainWinTrend");
		categoryMainWinTrend = {
			init : function(){
			var swFlag=categoryMainObjTrend.panel("options").swFlag;
			var dateFlag=categoryMainObjTrend.panel("options").dateFlag;
			var startDate=categoryMainObjTrend.panel("options").startDate;
			var endDate=categoryMainObjTrend.panel("options").endDate;
			var code=categoryMainObjTrend.panel("options").code;
				 $.ajax({
					url:'/jubao/dataPredict/data-predict-distribution-info!trend.action', 
					type : "post",
					data:{
						code : code,	 	    	
					},
					success:function(d){
						//日志 查询
						var params={
							operationFuncCode : "dataCityOne",
							operationFuncName : "查询区和派驻的趋势图",
							operationBtnName : dateFlag,
							operationDesc : "信访基本信息表、反映问题信息表、信访办理信息表、信访预测分析表",
							operationTypeCode : OperationType.QUERY,
							operationTypeName : OperationType.QUERY_NAME,
							enableDataLog :true
						};
						saveOperaLog(params);
						var data = eval('(' + d + ')'); 
						/* var data = [
		                  {DATE:'03',去年:132,今年:126,预测:null},
		                  {DATE:'04',去年:165,今年:154,预测:null},
		                  {DATE:'05',去年:153,今年:162,预测:null},
		                  {DATE:'06',去年:148,今年:143,预测:null},
		                  {DATE:'07',去年:123,今年:120,预测:null},
		                  {DATE:'08',去年:138,今年:140,预测:null},
		                  {DATE:'09',去年:142,今年:null,预测:141},
		                  {DATE:'10',去年:122,今年:null,预测:120},
		                  {DATE:'11',去年:135,今年:null,预测:137},
		                  {DATE:'12',去年:111,今年:null,预测:120},
		                  {DATE:'01',去年:126,今年:null,预测:130},
		                  {DATE:'02',去年:185,今年:null,预测:172}
		                  ]; */
						var Frame = G2.Frame;
						var frame = new Frame(data);
						frame = Frame.combinColumns(frame, ['去年', '今年','预测'], 'value', 'city', 'DATE');
						/* frame = Frame.combinColumns(frame, ['历史','预测'], 'value', 'city', 'DATE');  */
						var chart = new G2.Chart({
							id: 'trend',
							forceFit: true,
							height: 400,
							plotCfg: {
								margin: [60, 50, 90, 60]
							}
						});
						chart.source(frame, {
							DATE: {
					            tickCount: 12
				            }
						});
						chart.legend({
							position: 'bottom',
							word:{
            					fill: '#FFF'
            				}
						});
						chart.axis('DATE', {
							tickLine: {
								stroke: '#FFF',
								value: 6 // 刻度线长度
							},
							labels: {
								label: {
									fill: '#FFF'
								}
							},
							line: {
								stroke: '#FFF'
							},
							title: {
							    fill: '#FFF'
						    }
						});
						chart.col('DATE', {
					      	alias: '时间'
					    });
					    chart.col('value', {
					      	alias: '数量'
					    });
						chart.axis('value', {
							tickLine: {
								stroke: '#FFF',
								value: 6 // 刻度线长度
							},
							labels: {
								label: {
									fill: '#FFF'
								}
							},
							line: {
								stroke: '#FFF'
							},
							grid: null,
							title: {
							    fill: '#FFF'
						    }
						});
						
						chart.line().position('DATE*value').color('city', ['#1f77b4','#ff7f0e', '#2ca02c']).shape('spline').size(2);												
						chart.render();													    						 	    	
					},
					error:function(){  
						dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据加载失败！', 3000);
					}  
				});
				document.body.style.overflow='hidden';
			}
		}
		function analysisIn(){	
				if(categoryMainObjTrendAnalysis){
				    		 
			    }else{	
				categoryMainObjTrendAnalysis = $.f("div","categoryMainTrendAnalysis").window({
			 	    		    width:500, 
			 	    		    height:252,
			 	    		    left:376,
		   						top:167,
			 	    		    title:'分析报告',
			 	    		    resizable:false,
			 	    		    draggable:false,
			 	    		    shadow:false,
			 	    		    modal:true,
			 	    			maximizable:false,
			 	    			minimizable:false,
			 	    			collapsible:false,
			 	    			closed:true,		 	    			
			 	    			onOpen:function(){
			 	    				categoryMainWinTrendAnalysis.init();
			 	    			}
			 	});
			 }
		    categoryMainObjTrendAnalysis.window("open");
		}
	</script>	
</body>
</html>