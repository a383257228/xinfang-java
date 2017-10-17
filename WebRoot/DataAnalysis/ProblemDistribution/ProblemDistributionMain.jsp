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
	     .weiji{
	     float:left;
	     margin-left:20px;
	     color:#43D3FF;
	     font-size:16px;
	     width:46%;
	     padding-top:15px
	     }
	     .weifa{
	     color:#DE9B32;
	     font-size:16px;
	     margin-left:47%;
	     width:47.5%;
	     padding-top:15px
	     }
	     .weifa span{
	    	 margin-left:84%
	     }
	     
	    @media only screen and (max-width: 1024px) {
		   .bk{
		     background:url(<%=basePath%>/images/bk2.png) no-repeat;
		   }
		  .tie{
	        background:url(<%=basePath%>/images/bjk1.png) no-repeat;
	        background-size:96.5%;
	     }
	     .weiji{
	     float:left;
	     margin-left:25px;
	     color:#43D3FF;
	     font-size:16px;
	     width:45%;
	     padding-top:15px
	     }
	     .weifa{
	     color:#DE9B32;
	     font-size:16px;
	     margin-left:47.5%;
	     width:45%;
	     padding-top:15px
	     }
	     .weifa span{
	    	 margin-left:80%
	     }
		}
  </style>
  
  
  <div class="bk">
      <div class="tie">
          <div class="weiji">
              <span style="margin-left:10px">违纪行为</span>
              <div style="border:1px solid #43D3FF;"></div>
          </div>
          <div class="weifa">
          <span style="">违法行为</span>
          <div style="border:1px solid #DE9B32;"></div>
          </div>
          <div id="wj" style="width:48%;float:left;height: 400px; margin-top:-20px;"></div>
          <div id="wf" style="width:48%;float:left;height: 400px; margin-top:-20px;" ></div>  
      </div>
  </div>
    <script>
    var swFlag = "<%=swFlag%>";
	var dateFlag = "<%=dateFlag%>";
	var startDate = "<%=startDate%>";
	var endDate = "<%=endDate%>";
	var bwRadio = "<%=bwRadio%>";
	
	var wfData =  new Array(); 
	var wfCount =  new Array();  
	
	var wjData =  new Array(); 
	var wjCount =  new Array();  
	
	$.ajax({//查询违纪行为
 	    url:'<%=basePath%>/petition/petition-issue-info!problemDistribution.action',  
 	    type : "post",
        async : false,
 	    data:{
 	    	swFlag : swFlag,
 	    	dateFlag : dateFlag,
 	    	startDateValue　: startDate,
 	    	endDateValue :　endDate,
 	    	bwRadio :　bwRadio
 	    },
 	    success:function(data){
 	    	var params={
    			operationFuncCode : "problemDistribution",
    			operationFuncName : "问题类别分布情况主查询",
    			operationBtnName : dateFlag,
    			operationDesc : "问题类别分布情况主查询",
    			operationTypeCode : OperationType.QUERY,
    			operationTypeName : OperationType.QUERY_NAME,
    			enableDataLog :true
    		}
    		saveOperaLog(params);
 	    	
 	    	var wf = eval('(' + data.substring(0,(data.indexOf("]")+1)) + ')');
 	    	var wj = eval('(' + data.substring((data.indexOf("],")+2),data.length) + ')');
 	    	wf.forEach(function(ele,datas){
 	    		wfData.push(ele.name);
 	    		wfCount.push(ele.count);
 	    	});
 	    	wj.forEach(function(ele,datas){
 	    		wjData.push(ele.name);
 	    		wjCount.push(ele.count);
 	    	});
        },
        error:function(){
        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
        	return;
        }  
    });
    //柱状图
    var myChart = echarts.init(document.getElementById('wj'));
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter:function(params){ //后台返回的是负数 处理一下鼠标悬浮提示框
                var relVal = params[0].name+"<br/>";
                relVal += params[0].seriesName+ ' : ' + (-params[0].value) +"<br/>";
                return relVal;
            }
        },
        grid: {
        	x2:0,
            left: '3%',
            right: '4%', 
            bottom: '3%',
            containLabel: false,
            borderColor:'#003574',
            borderWidth:2
        },
        xAxis: {
            type: 'value',
            axisLabel: {
            	formatter:function(value){//改变坐标的方向
            		return -value;
                },
        		textStyle: {
                    color: '#ffffff',
                }
            },
            splitLine:{//坐标系颜色
				lineStyle:{
					 color: '#003574',
					 width: 1,
				}
         	},
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            }
        },
        yAxis: {
            type: 'category',
            data: wjData,
            splitLine: {show: false},
            axisLine: {
                show: false
            },
            axisLabel: {
	      		textStyle: {
	                    color: '#ffffff',
	             },
	             formatter:function(value){ 
	                  var ret = "";//拼接加\n返回的类目项  
	                  var maxLength = 4;//每项显示文字个数  
	                  var valLength = value.length;//X轴类目项的文字个数  
	                  var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
	                  if (rowN > 1){//如果类目项的文字大于3,  
	                      for (var i = 0; i < rowN; i++) {  
	                          var temp = "";//每次截取的字符串  
	                          var start = i * maxLength;//开始截取的位置  
	                          var end = start + maxLength;//结束截取的位置  
	                          //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
	                          temp = value.substring(start, end) + "\n";  
	                          ret += temp; //凭借最终的字符串  
	                      }  
	                      return ret;  
	                  }else {  
	                      return value;  
	                  }  
               }
            },
            axisTick: {
                show: false
            },
            offset: 10,
            nameTextStyle: {
                fontSize: 15
            }
        },
        series: [
            {
                name: '数量',
                type: 'bar',
                data: wjCount,
                barWidth: 10,
                barGap: 10,
                smooth: true,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        offset: [5, -2],
                        textStyle: {
                            color: '#F68300',
                            fontSize: 13
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                    	 formatter:function(e,v){//改变坐标的方向
                    		return -(e.data)
                        }, 
                        barBorderRadius: 7
                    },
                    normal: {
                    	/* formatter:function(e,v){//改变坐标的方向
                        	return -(e.barData)
                        }, */
                        barBorderRadius: 7,
                        color:'#43D3FF',
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
    myChart.on('click',function(param){
    	if(ProblemDisWinObj){
    		 ProblemDisWinObj.panel("options").swFlag=swFlag;
    		 ProblemDisWinObj.panel("options").dateFlag=dateFlag;
    		 ProblemDisWinObj.panel("options").startDate=startDate;
    		 ProblemDisWinObj.panel("options").endDate=endDate;
    		 ProblemDisWinObj.panel("options").bwRadio=bwRadio;
    		 ProblemDisWinObj.panel("options").name=param.name;
    	 }else{
    		   ProblemDisWinObj = $.f("div","ProblemDisWin").window({
	   	   		    width:850, 
	   	   		    height:450,
	   	   		    title:'问题类别分布情况',
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
	   	   				ProblemDistribute.init();
	   	   			},
	   	   			onClose:function(){
		   	   			 ProblemDisWinObj.panel("options").swFlag="";
		   	    		 ProblemDisWinObj.panel("options").dateFlag="";
		   	    		 ProblemDisWinObj.panel("options").startDate="";
		   	    		 ProblemDisWinObj.panel("options").endDate="";
		   	    		 ProblemDisWinObj.panel("options").bwRadio="";
		   	    		 ProblemDisWinObj.panel("options").name="";
	   	   			}
   	   		 });
    	 }
    	ProblemDisWinObj.window("open");
   });
    
    var myChart = echarts.init(document.getElementById('wf'));
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
         	x:0,
        	left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
            borderWidth:2,
            borderColor:'#003574',
        }, 
        xAxis: {
            type: 'value',
            axisLabel: {
        		  textStyle: {
                      color: '#ffffff'
                  }
            },
            splitLine:{//坐标系颜色
        	     show:true,
        	     lineStyle:{
					 color: '#003574',
					 width: 1
				 }
       	    },
        
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            }
        },
        yAxis: {
        	position:'right',
            type: 'category',
            data: wfData,
            axisLabel: {
          	    interval:0,  
          	    formatter:function(value){ 
	                  var ret = "";//拼接加\n返回的类目项  
	                  var maxLength = 6;//每项显示文字个数  
	                  var valLength = value.length;//X轴类目项的文字个数  
	                  var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
	                  if (rowN > 1){//如果类目项的文字大于3,  
	                      for (var i = 0; i < rowN; i++) {  
	                          var temp = "";//每次截取的字符串  
	                          var start = i * maxLength;//开始截取的位置  
	                          var end = start + maxLength;//结束截取的位置  
	                          //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
	                          temp = value.substring(start, end) + "\n";  
	                          ret += temp; //凭借最终的字符串  
	                      }  
	                      return ret;  
	                  }else {  
	                      return value;  
	                  }  
                },
        		textStyle: {
                     color: '#ffffff'
                },
            },
            splitLine: {
            	show: false,
            	},
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            offset: 10,
            nameTextStyle: {
                fontSize: 15
            }
        },
        series: [
            {
                name: '数量',
                type: 'bar',
                data: wfCount,
                barWidth: 10,
                barGap: 10,
                smooth: true,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        offset: [5, -2],
                        textStyle: {
                            color: '#F68300',
                            fontSize: 13
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        barBorderRadius: 7
                    },
                    normal: {
                        barBorderRadius: 7,
                        color:'#DE9B32',
                    }
                }
            }
        ]
    };
    myChart.setOption(option,true);
    myChart.on('click',function(param){
    	if(problemInfoWinObj){
    		 problemInfoWinObj.panel("options").swFlag=swFlag;
    		 problemInfoWinObj.panel("options").dateFlag=dateFlag;
    		 problemInfoWinObj.panel("options").startDate=startDate;
    		 problemInfoWinObj.panel("options").endDate=endDate;
    		 problemInfoWinObj.panel("options").bwRadio=bwRadio;
    		 problemInfoWinObj.panel("options").name=param.name;
	   	 }else{
	   		problemInfoWinObj = $.f("div","ProblemInfoWin").window({
	   		    width:960, 
	   		    height:600,
	   		    title:'信访件列表',
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
	   				problemInfoWin.init();
	   			},
	   			onClose : function(){
	   				$.f("div", "problemFindPanel").f("input","accusedName").textbox('setValue',"");
	   				$.f("div", "problemFindPanel").f("input","accuserName").textbox('setValue',"");
		   			problemInfoWinObj.panel("options").swFlag="";
		    		problemInfoWinObj.panel("options").dateFlag="";
		    		problemInfoWinObj.panel("options").startDate="";
		    		problemInfoWinObj.panel("options").endDate="";
		    		problemInfoWinObj.panel("options").bwRadio="";
		    		problemInfoWinObj.panel("options").name="";
	   			}
	   		});
	   	}
		problemInfoWinObj.window("open");
   });
    </script>
  </body>
</html>