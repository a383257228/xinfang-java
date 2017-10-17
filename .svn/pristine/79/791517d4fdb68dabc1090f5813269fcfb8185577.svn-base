<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>信访件分布情况</title>
<meta charset="utf-8">
</head>
<body style="overflow-x:visible;overflow-y:scroll">	
<style>
	  .bk{
	     width:98%;
	     height:430px;
	     background:url(<%=basePath%>/images/bk.png) no-repeat;
	     padding-top:12px
	     }
	     .tie{
	        height:420px;
	        margin-left:15px;
	        background:url(<%=basePath%>/images/bjk2.png) no-repeat;
	        padding-top:17px"
	     }
	    @media only screen and (max-width: 1024px) {
		   .bk{
		    background:url(<%=basePath%>/images/bk2.png) no-repeat;
	    }
	   .tie{
	        background:url(<%=basePath%>/images/bjk1.png) no-repeat;
	         background-size:96.5%;
	     }
		}
</style>

	<div class="bk">
		<div class="tie">
			<div id="c1" style="padding-top:10px"></div>      
		</div>
		<div id = "fx" style = "display:none">
		
		</div>
	</div>
	
	<script type="text/javascript">
 		$.ajax({
			url:'/jubao/dataPredict/data-predict-distribution-info!year.action', 
			type : "post",
			data:{	 	    	
			},
			success:function(d){
				if(d=='[]'){
			    	$("#paizhu").html("<div style='text-align:center;line-height: 390px;color: white;'>当前时间段暂无数据</div>");
					return;
			    } 
				//日志 查询
				var params={
					operationFuncCode : "dataCityOne",
					operationFuncName : "查询上海市的趋势图",
					operationBtnName : dateFlag,
					operationDesc : "信访基本信息表、反映问题信息表、信访办理信息表、信访预测分析表",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				};
				saveOperaLog(params);
			$('<span style="font-size:15px;color:#ffffff;margin-left:39%;margin-top:10px">本委信访件分布情况（件/次）</span>').appendTo($('#c1'));
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
				var chart = new G2.Chart({
					id: 'c1',
					forceFit: true,
					height: 400,
					plotCfg: {
						margin: [50, 50, 130, 70]
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
            			 },
            		dy:-30
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
				chart.col('DATE', {
					alias: '时间'
				});
				chart.col('value', {
					alias: '数量'
				});
				chart.line().position('DATE*value').color('city', ['#1f77b4', '#ff7f0e', '#2ca02c']).shape('spline').size(2);
				chart.render();					 	    	
			 },
			error:function(){  
				dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据加载失败！', 3000);
			}  
		});  
		document.body.style.overflow='hidden';
		function analysisIn(){
			if(categoryMainObjAnalysis){
				    		 
			}else{	
			categoryMainObjAnalysis = $.f("div","categoryMainAnalysis").window({
		 	    		    width:500, 
		 	    		    height:252,
		 	    		    title:'分析报告',
		 	    		    left:492,
		   					top:139,
		 	    		    resizable:false,
		 	    		    draggable:false,
		 	    		    shadow:false,
		 	    		    modal:false,
		 	    			maximizable:false,
		 	    			minimizable:false,
		 	    			collapsible:false,
		 	    			closed:true,
		 	    			onOpen:function(){
		 	    				categoryMainWinAnalysis.init();
		 	    			}
		 	});}
		    categoryMainObjAnalysis.window("open");
		}
	</script>	
</body>
</html>