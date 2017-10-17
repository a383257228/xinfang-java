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
    <%-- <script src="<%=basePath%>/publicresource/js/g2.js"></script> --%>
      <script src="<%=basePath%>/publicresource/js/echarts.js"></script>
  </head>
  <body>
  <div style="width:98%;height:430px;background:url(<%=basePath%>/images/bk2.png) no-repeat;background-size:100%;padding-top:12px">
      <div style="height:410px;background:url(<%=basePath%>/images/bjk1.png) no-repeat;background-size:98%;margin-left:17px">
          <div style="float:left;margin-left:75px;color:#43D3FF;font-size:16px;width:40%;padding-top:15px">
              <span style="margin-left:10px">违纪行为</span>
              <div style="border:1px solid #43D3FF;"></div>
          </div>
          <div style="color:#DE9B32;font-size:16px;margin-left:390px;width:40%;padding-top:15px">
          <span style="margin-left:230px">违法行为</span>
          <div style="border:1px solid #DE9B32;"></div>
          </div>
          <div id="top5" style="height:400px; width:52%;margin-left:50px;float:left;margin-top:-20px"></div>
          <div id="top5-1" style="height: 400px; width:52%;float:left;margin-left:-159px;margin-top:-20px"></div> 
      </div>
  </div>
    <script>
      //柱状图
    var myChart = echarts.init(document.getElementById('top5'));
    //初始化数据
   /*  var category = ['','违反政治纪律行为','','违反组织纪律行为','','违反廉洁纪律行为','','违反群众纪律行为','','违反生活纪律行为','','违反工作纪律行为'];
    var barData = [-3100,'', -2142,'',-1218,'', -581,'', -1431,'', -1142,]; */
    var category = ['违反政治纪律行为','违反组织纪律行为','违反廉洁纪律行为','违反群众纪律行为','违反生活纪律行为','违反工作纪律行为'];
    var barData = [-3100, -2142,-1218, -581, -1431,-1142,];
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
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: false,
            borderColor:'#003574',
            borderWidth:2,
            
        },
        xAxis: {
            type: 'value',
            axisLabel: {
            	formatter:function(value){//改变坐标的方向
                	return -value
                },
        		  textStyle: {
                      color: '#ffffff',
                      //fontSize:'16'
                  },
                
          },
          splitLine:{//坐标系颜色
				//show:false,
				lineStyle:{
					 color: '#003574',
					    width: 1,
					  //  type: 'dashed'
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
        	/* show:false, */
            type: 'category',
            data: category,
            splitLine: {show: false},
            axisLine: {
                show: false
            },
            axisLabel: {
         //   margin:-300,//调整位置
      		  textStyle: {
                    color: '#ffffff',
                    //fontSize:'16'
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
                data: barData,
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
     //2
    var myChart = echarts.init(document.getElementById('top5-1'));
    //初始化数据
   /*  var category = ['','侵犯人身权利民主权利行为','','妨碍社会主义管理秩序行为','','其他违法行为','','贪污贿赂行为','','失职渎职行为','','破坏社会主义市场经济行为',];
    var barData = [3100,'', 2142,'',1520 ,'', 581,'', 1218, '',383]; */
    var category = ['侵犯人身权利民主权利行为','妨碍社会主义管理秩序行为','其他违法行为','贪污贿赂行为','失职渎职行为','破坏社会主义市场经济行为',];
    var barData = [3100, 2142,1520 ,581, 1218, 383];
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
         grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
            borderWidth:2,
            borderColor:'#003574',
        }, 
       /*  grid:{ //外边框线设置为0
      		borderWidth:0.5,
      	}, */
        xAxis: {
            type: 'value',
            axisLabel: {
        		  textStyle: {
                      color: '#ffffff',
                      //fontSize:'16'
                  },
                  
          },
          splitLine:{//坐标系颜色
        	  show:true,
        	  lineStyle:{
					 color: '#003574',
					    width: 1,
					  //  type: 'dashed'
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
        	/* show:false, */
            type: 'category',
            data: category,
            axisLabel: {
            	//margin:-300,//调整位置
        		  textStyle: {
                      color: '#ffffff',
                      //fontSize:'16'
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
                data: barData,
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
                       /*   //渐变颜色，但在页面中不能用
                       color: new echarts.graphic.LinearGradient(
                            0, 0, 1, 0,
                            [
                                {offset: 0, color: '#3977E6'},
                                {offset: 1, color: '#37BBF8'}

                            ]
                        )  */
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
      
      
    </script>
  </body>
</html>



