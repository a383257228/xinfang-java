<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>民意分析</title>
</head>
<body>
	<div idFlag="categoryMainWord" style="margin-top :20px;display:none">
		<div id="dci"></div>
	</div>
</body>
<script>
	var categoryMainObjWord;
	$.SUI("categoryMainWord");
	categoryMainWinWord = {
			init : function(){
				$("#dci").empty();
				//var queTypeId = "012103";
				var queTypeId = categoryMainObjWord.panel("options").word;
				 $.ajax({
						url:'/jubao/dataPredict/data-predict-opinion-info!getWordCloud.action', 
						type : "post",
						data:{
							swFlag : swFlag,
							dateFlag : dateFlag,
							startDateValue:startDate,
							endDateValue:endDate,
							queTypeId :queTypeId
						},
						success:function(data){
							 //加载完毕  添加日志
				        	 var params={
				         			operationFuncCode : "getWordCloud",
				         			operationFuncName : "查询民意分析获取词云",
				         			operationBtnName : dateFlag,
				         			operationDesc : "民意分析词典表",
				         			operationTypeCode : OperationType.query,
				         			operationTypeName : OperationType.QUERY_NAME,
				         			enableDataLog :true
				         		};
				      	    	saveOperaLog(params);
							
							data = eval('('+data+')');
							if(data.length==0){
								$("#dci").html("<div style='margin-left:41%;margin-top:-40px;line-height: 390px;color: white;'>当前选中问题类别没有词</div>");
								return;
							}
							if(data != null && data.length > 0) {
								//var maxs = Number(data[0].maxs);
								//var mins = Number(data[0].mins);
								//最大字号
								var maxs = 100;
								//最小字号
								var mins = 10;
								var step = (maxs - mins)/data.length;
								
								for(var i = 0; i < data.length; i++) {
									if(i == 0) {
										var js = {'text':data[i].text,'size':maxs};
										maxs = maxs - step;
										data[i] = js;
									} else {
										//判断是否前后相等 相等则字号一致
										if(data[i].size == data[i-1].size) {
											maxs = maxs + step;
										}
										var js = {'text':data[i].text,'size':maxs};
										maxs = maxs - step;
										data[i] = js;
										
									}
									
								}
								
								var fill = d3.scale.category20();
								  d3.layout.cloud().size([800, 400])
								      //map 返回新的object数组
									  .words(data)
									  //~~的作用是单纯的去掉小数部分，不论正负都不会改变整数部分 
									  //这里的作用是产生0 1 
								      .rotate(function() { return ~~(Math.random() * 2) * 90; })
								      .font("Impact")
								      .fontSize(function(d) { return d.size; })
								      .on("end", draw)//结束时运行draw函数
								      .spiral("archimedean")
								      .start();
									  
								  function draw(words) {
								    d3.select("#dci").append("svg")
								        .attr("width", 800)
								        .attr("height", 400)
								        //.attr("style","border:1px solid")
								      .append("g")
								        .attr("transform", "translate(370,200)")
								      .selectAll("text")
								        .data(words)
								      .enter().append("text")
								        .style("border","1px solid blue")
								        .style("font-size", function(d) { return d.size + "px"; })
								        .style("font-family", "Impact")
								        .style("fill", function(d, i) {   return fill(i); })//fill 在前面15行定义为颜色集
								        .attr("text-anchor", "middle")
								        .attr("transform", function(d) {
								          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
								        })
								        .text(function(d) { return d.text; });
										
										
								  }
							}
							
						},
						error:function(){  
							alert("数据加载失败！");  
						}  
					}); 
			}};
</script>
</html>

