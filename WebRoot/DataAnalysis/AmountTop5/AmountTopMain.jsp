<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String swFlag = request.getParameter("swFlag");
String dateFlag = request.getParameter("dateFlag");
String startDate = request.getParameter("startDate");
String endDate = request.getParameter("endDate");
String bwRadio = request.getParameter("bwRadio");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
    <script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
  </head>
  <body>
  
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
          <div style="font-size:18px;color:#fff;text-align:center;padding-top:25px">数量前五位问题类别排名</div> 
          <div id="top5" style="height: 390px; width: 98%;margin-left:15px;margin-top:-30px;padding-left:10px;"></div>
      </div>
  </div>
<script>
    var swFlag = "<%=swFlag%>";
	var dateFlag = "<%=dateFlag%>";
	var startDate = "<%=startDate%>";
	var endDate = "<%=endDate%>";
	var bwRadio = "<%=bwRadio%>";
	var nameObj = new Array(); 
	var countObj = new Array();
	var code = new Array();
      //柱状图
    var myChart = echarts.init(document.getElementById('top5'));
    var option ;
    $.ajax({
    	url:'<%=basePath%>/petition/petition-issue-info!issueTypeTop5.action',
 	    type : "post",
 	    data:{
 	    	swFlag : swFlag,
 	    	dateFlag : dateFlag,
 	    	startDateValue　: startDate,
 	    	endDateValue :　endDate,
 	    	bwRadio : bwRadio
 	    },
 	    success:function(data){
 	    	var params={
    			operationFuncCode : "issueTypeTop5",
    			operationFuncName : "数量前五位问题类别主查询",
    			operationBtnName : dateFlag,
    			operationDesc : "数量前五位问题类别主查询",
    			operationTypeCode : OperationType.QUERY,
    			operationTypeName : OperationType.QUERY_NAME,
    			enableDataLog :true
    		}
 	    	saveOperaLog(params);
 	    	
 	    	datas = eval('(' + data + ')');
			if(datas.length==0){
				$("#top5").html("<div style='margin-left:250px;line-height: 390px;color: white;'>当前选中时间段暂无数据</div>");
				return;
			}
 	    	datas.forEach(function(ele,datas){
 	    		nameObj.push(ele.name);
 	    		code.push(ele.issueTypeCode);
 	    		countObj.push(ele.count);
 	    	});
 	    	option = {
    	   		  title : {
    	   		        textStyle:{
    	   		        	color:'#fff',
    	   		        	fontSize:16,
    	   		        }
    	   		  },
    	 		  tooltip : {
    	 		       trigger: 'axis'
    	 		  },
    	  		  calculable : true,
    	  		  grid:{ //外边框线设置为0
    	         		borderWidth:0.5,
    	         		x:'24%'
    	          },
    	  		  xAxis : [{
    	  		    	/* show : false,//去掉x轴坐标    		 */    	
    	  		    	type : 'value',
    	  		    	boundaryGap : [0, 0.01],
    	  		    	axisLine:{show:false},
    	  		    	axisLabel: {
    	  		    		textStyle: {
    	  		    			color: '#ffffff',
    	 		    		},
    	  		    	},
    		   			splitLine:{
    		   				lineStyle:{
    		   					 color: '#ffffff',
    		   					 width: 0.5,
    		   					 type: 'dashed'
    		   				}
    		   			}
    	  		  }],
    	  		  yAxis : [{
    	  		   	   type : 'category',
    	  		       splitLine:{show:false},  
    	  		       axisLabel: {
    	  		    		textStyle: {
    	  		    			color: '#ffffff',
    	  		    		},
    	  		    	},
   	  		            data : nameObj
    	  		   }],
    	  		  series : [{
    	  		    	name:'数量',
    	  		    	type:'bar',
    	  		    	itemStyle:{ 
    	  		    		normal:{
    	  		    			color:function(params){
    	  		    				var colorList=['#C79550', '#6AA372', '#00B9E1', '#EF6442','#EF0B33'];
    	  		    				return colorList[params.dataIndex]
    	  		    			},
    	  		    			label: {
    	  		    				show: true,
    	  		    				position: 'right',
    	  		    				formatter: '{c}',
    	  		    				textStyle:{color:'#ffffff'}
    	  		    			}
    	  		    		}
    	  		    	}, 
    	  		        barWidth:30,//柱子的宽度
    	  		        data :countObj
    	  		   }]
    	 	 };
 	    	 myChart.setOption(option,true);
 	    	 myChart.on('click',function(param){
 	    		 if(top5WinObj){
	 	    			top5WinObj.panel("options").swFlag=swFlag;
	 	    			top5WinObj.panel("options").dateFlag=dateFlag;
	 	    			top5WinObj.panel("options").startDate=startDate;
	 	    			top5WinObj.panel("options").endDate=endDate;
	 	    			top5WinObj.panel("options").bwRadio=bwRadio;
	 	    			top5WinObj.panel("options").name=param.name;
 	    		  }else{
	 	    			 top5WinObj = $.f("div","top5DrillingWin").window({
	 		    		    width:750, 
	 		    		    height:480,
	 		    		    title:'问题分布',
	 		    		    modal:true,
	 		    			maximizable:false,
	 		    			minimizable:false,
	 		    			collapsible:false,
	 		    			closed:true,
	 		    			swFlag:swFlag,
	 		    			dateFlag:dateFlag,
	 		    			startDate:startDate,
	 		    			endDate:endDate,
	 		    			bwRadio:bwRadio,
	 		    			name:param.name,
	 		    			onOpen:function(){
	 		    				top5DrillingWin.init();
	 		    			},
	 		    			onClose:function(){
	 		    				top5WinObj.panel("options").swFlag="";
	 		 	    			top5WinObj.panel("options").dateFlag="";
	 		 	    			top5WinObj.panel("options").startDate="";
	 		 	    			top5WinObj.panel("options").endDate="";
	 		 	    			top5WinObj.panel("options").bwRadio="";
	 		 	    			top5WinObj.panel("options").name="";
	 		    			}
	 		    		});
 	    		 }
 	    		 top5WinObj.window("open");
		     });
        },
        error:function(){  
        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
        }  
    }); 
</script>
</body>
</html>