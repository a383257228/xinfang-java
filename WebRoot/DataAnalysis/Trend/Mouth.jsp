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
    <script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
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
	     <div id="Mouth" style="width:100%;height:400px;padding-top:15px "></div>
	  </div>
  </div>
    <script>
    var month = new Array();
    var count = new Array();
    var myChart = echarts.init(document.getElementById('Mouth'));
    var option ;
    $.ajax({
 	    url:'<%=basePath%>/petition/petition-basic-info!trendStatistical.action',
 	    data:{
 	    	swFlag : swFlag,
 	    	dateFlag : dateFlag,
 	    	startDateValue　: startDate,
 	    	endDateValue :　endDate,
 	    	bwRadio : bwRadio
 	    },
 	    type : "post",
 	    success:function(data){
 	    	var params={
	   			operationFuncCode : "trendStatistical",
	   			operationFuncName : "各月趋势主查询",
	   			operationBtnName : dateFlag,
	   			operationDesc : "各月趋势主查询",
	   			operationTypeCode : OperationType.QUERY,
	   			operationTypeName : OperationType.QUERY_NAME,
	   			enableDataLog :true
	   		}
	   		saveOperaLog(params);
 	    	
 	    	datas = eval('(' + data + ')');
 	    	datas.forEach(function(ele,datas){
 	    		month.push(ele.month);
 	    		if(null==ele.count){
 	    			count.push(0);
 	    		}else{
	 	    		count.push(ele.count);
 	    		}
 	    	});
 	    	option = {
 	    		    title : {},
 	    		    tooltip : {
 	    		        trigger: 'axis'
 	    		    },
 	    		    legend: {
 	    		    	show:false,
 	    		    	data:['件数'],
 	    		        textStyle:{    //图例文字的样式
 	    		            color:'#fff',
 	    		            fontSize:14
 	    		        } 
 	    		    },
 	    		    toolbox: {
 	    		        show : false,
 	    		    },
 	    		    calculable : true,
 	    		    xAxis : [{
 	    	           type : 'category',
 	    	           boundaryGap : false,
 	    	           data : month,
 	    	           axisLabel: {
 	    		    		textStyle: {
 	    		    			color: '#ffffff',
 	    		    		},
 	    	   		   },
 	    	   		   splitLine:{
 	    	   				show:false,
 	    	   				lineStyle:{
 	    	   					 color: '#ffffff',
 	    	   					    width: 0.3,
 	    	   					    type: 'dashed'
 	    	   				}
 	    	   		   }
 	    	       }],
 	    		   yAxis : [{
 	    	            type : 'value',
 	    	            axisLabel: {
 	    		    		textStyle: {
 	    		    			color: '#ffffff',
 	    		    		},
 	    	    		},
 	    	   			splitLine:{
 	    	   				lineStyle:{
 	    	   					 color: '#ffffff',
 	    	   					    width: 0.3,
 	    	   					    type: 'dashed'
 	    	   				}
 	    	   			}
 	    	        }],
 	    		    grid:{ //外边框线设置为0
 	    	         		borderWidth:0,
 	    	        },
 	    		    series : [ {
 	    	            name:'件数',
 	    	            type:'line',
 	    	            smooth:true,
 	    	            itemStyle: {
 	    	            	normal: {
 	    	            		color:'#ffffff',//折线点颜色
 	    	            		areaStyle: {
 	    	            			color:'rgba(73,174,213,0.5)',//阴影区域颜色
 	    	            			type: 'default'
 	    	            		},
 	    	            		lineStyle:{
 	    	            				color:'#49AED5',//折线颜色
 	    	            		},
 	    	            			
 	    		          }
 	    		        },
 	    	            data:count
 	    	        }]
 	    		};
 	    		myChart.setOption(option,true);
 	    		myChart.on('click',function(param){
 	    			if(monthMainObj){
 	    				monthMainObj.panel("options").swFlag=swFlag;
 	    				monthMainObj.panel("options").bwRadio=bwRadio;
 	    				monthMainObj.panel("options").month=param.name;
 	    				monthMainObj.panel("options").JQFlag="trend";
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
 	 	    		 		  JQFlag:"trend",
 	 	    		 		  YN:"2",
 	 	    		 		  onOpen:function(){
 	 	    		 			  monthWin.init();
 	 	    		 		  },
 	 	    		 		  onClose:function(){
 	 	    		 			  monthMainObj.panel("options").swFlag="";
 	 	 	    				  monthMainObj.panel("options").bwRadio="";
 	 	 	    				  monthMainObj.panel("options").month="";
 	 	 	    				  monthMainObj.panel("options").JQFlag="";
 	 	 	    				  monthMainObj.panel("options").YN="";
 	 	    		 		  }
 	 	    	 		});
 	    		    }
 	    	   		monthMainObj.window("open");
 	    		});
 	    },
        error:function(){  
        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
        }  
    });
    </script>
  </body>
</html>