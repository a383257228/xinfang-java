<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String swFlag = request.getParameter("swFlag");
	String bwRadio = request.getParameter("bwRadio");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>同比</title>
<script src="<%=basePath%>/publicresource/js/g2.js"></script>
<script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
</head>
<body>


   <style>
     .bk{
	     width:98%;
	     height:430px;
	     background:url(<%=basePath%>/images/bk.png) no-repeat;
	     padding-top:10px;
	     }
	     .tie{
	        height:420px;
	        margin-left:15px;
	        background:url(<%=basePath%>/images/bjk2.png) no-repeat;
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
			<div id="TongB" style="width:100%;height:200px;padding-top:15px"></div>
			<div id="TongBi" style="width:100%;height:200px"></div>
		</div>
	</div>
	<script>
		var swFlag = "<%=swFlag%>";
		var bwRadio = "<%=bwRadio%>";
		var JCount = new Array();//存储今年的月份和数量
		var QCount = new Array();//存储去年的月份和数量
		var ZCount = new Array();//存储增量
		$.ajax({
	 	    url:'<%=basePath%>/petition/petition-basic-info!tbQuery.action',  
	 	    type : "post",
	        async : false,
	 	    data:{
	 	    	swFlag : swFlag,
	 	    	bwRadio : bwRadio
	 	    },
	 	    success:function(data){
	 	    	var params={
 	    			operationFuncCode : "tbQuery",
 	    			operationFuncName : "同比主查询",
 	    			operationBtnName : dateFlag,
 	    			operationDesc : "同比主查询",
 	    			operationTypeCode : OperationType.QUERY,
 	    			operationTypeName : OperationType.QUERY_NAME,
 	    			enableDataLog :true
 	    		}
 	    		saveOperaLog(params);
	 	    	
	 	    	var JData = eval('(' + data.substring(0,(data.indexOf("]")+1)) + ')');
	 	    	if(JData!=null){
		 	    	JData.forEach(function(ele,datas){
						JCount.push(ele.count);
					});
	 	    	}else{
	 	    		for(var i=0;i<12;i++){
	 	    			JCount.push("0");
	 	    		}
	 	    	}
	 	    	var QData = eval('(' + data.substring((data.indexOf("],")+2),(data.lastIndexOf("],")+1)) + ')');
	 	    	if(QData.length!=0){
		 	    	QData.forEach(function(ele,datas){
						QCount.push(ele.count);
					});
	 	    	}else{
	 	    		for(var i=0;i<12;i++){
	 	    			QCount.push("0");
	 	    		}
	 	    	}
	 	    	var zlData = eval('(' + data.substring(data.lastIndexOf("],[")+2,data.length) + ')');
	 	    	if(zlData.length!=0){
	 	    		zlData.forEach(function(ele,datas){
						ZCount.push(ele.count);
					});
	 	    	}else{
	 	    		for(var i=0;i<12;i++){
	 	    			ZCount.push("0");
	 	    		}
	 	    	}
	 	    	
	 	    },
	        error:function(){  
	        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
	        }  
	    });
		
		
		
		var myChart = echarts.init(document.getElementById('TongB'));
		option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '同比增长' ],
				textStyle : { //图例文字的样式
					color : '#fff',
					fontSize : 14
				}
			},
			toolbox : {
				show : false
			},
			calculable : false,
			grid : { //外边框线设置为0
				borderWidth : 0,
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" ],
				axisLabel : {
					textStyle : {
						color : '#ffffff'
					}
				},
				splitLine : {
					lineStyle : {
						color : '#ffffff',
						width : 0.3,
						type : 'dashed'
					}
				}
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					textStyle : {
						color : '#ffffff'
					}
				},
				splitLine : {
					lineStyle : {
						color : '#ffffff',
						width : 0.3,
						type : 'dashed'
					}
				}
			} ],
			series : [ {
				name : '同比增长',
				type : 'line',
				stack : '总量',
				data : ZCount
			} ]
		};
		myChart.setOption(option);

		//柱状图
		var myChart1 = echarts.init(document.getElementById('TongBi'));
		option = {
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'crossStyle' // 默认为直线，可选为：'line' | 'shadow'
				},
		      	position :function(val){
		          	val[0] = val[0]+25;
		          	val[1] = val[1]+25;
		        	return val;
		        }
			},
			legend : {
				data : [ '今年/件数', '去年/件数' ],
				textStyle : { //图例文字的样式
					color : '#ffffff',
					fontSize : 14
				}
			},
			toolbox : {
				show : false
			},
			calculable : true,
			grid : { //外边框线设置为0
				borderWidth : 0,
			},
			xAxis : [ {
				type : 'category',
				data : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月","10月", "11月", "12月" ],
				axisLabel : {
					textStyle : {
						color : '#ffffff',
					}
				},
				splitLine : {
					lineStyle : {
						color : '#ffffff',
						width : 0.3,
						type : 'dashed'
					}
				}
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					textStyle : {
						color : '#ffffff',
					}
				},
				splitLine : {
					lineStyle : {
						color : '#ffffff',
						width : 0.3,
						type : 'dashed'
					}
				}
			} ],
			series : [
			{
				name : '今年/件数',
				type : 'bar',
				data : JCount
			},
			{
				name : '去年/件数',
				type : 'bar',
				itemStyle: {
		            normal: {
		                color:'#00A1E9'
	                }
				},
				data : QCount
			} ]
		};

		myChart1.setOption(option,true);
		myChart1.on('click',function(param){
			if(monthMainObj){
 				monthMainObj.panel("options").swFlag=swFlag;
 				monthMainObj.panel("options").bwRadio=bwRadio;
 				monthMainObj.panel("options").month=param.name;
 				monthMainObj.panel("options").flag=param.seriesName;
 				monthMainObj.panel("options").JQFlag="sameCompare";
 				monthMainObj.panel("options").YN="1";
 		    }else{
				monthMainObj = $.f("div","MonthWin").window({
			 		  width:910, 
			 		  height:580,
			 		  title:'问题类别数量分布情况',
			 		  modal:true,
			 		  maximizable:false,
			 		  minimizable:false,
			 		  collapsible:false,
			 		  closed:true,
			 		  swFlag:swFlag,
			 		  bwRadio:bwRadio,
			 		  month:param.name,
			 		  JQFlag:"sameCompare",
			 		  YN:"2",
			 		  flag:param.seriesName,
			 		  onOpen:function(){
			 			  monthWin.init();
			 		  },
			 		  onClose:function(){
			 			 	monthMainObj.panel("options").swFlag="";
			 				monthMainObj.panel("options").bwRadio="";
			 				monthMainObj.panel("options").month="";
			 				monthMainObj.panel("options").JQFlag="";
			 				monthMainObj.panel("options").flag="";
			 				monthMainObj.panel("options").YN="";
			 		  }
		 		});
 		    }
	   		monthMainObj.window("open");
	   });
		
		//这三行是让控制鼠标悬浮的时候同一时间的同时显示
	 	myChart.connect([myChart1]);
	    myChart1.connect([myChart]);
	</script>
</body>
</html>