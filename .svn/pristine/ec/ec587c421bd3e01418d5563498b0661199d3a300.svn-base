<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  </head>
  <body>
    <div idFlag="categoryMain" style="display: none">
		  <div id="petitionType" style="width:50%; height:400px;float:left">
		  
		  </div>
	      <div style="font-size:14px;color:#ffffff;text-align:center;margin-top:5px">问题类别及数量</div>
		  <div id="issueType" style="width:50%; height:370px;float:left;cursor: hand">
		  </div>
   </div>
  <script>
  	var categoryMainObj;
  	$.SUI("categoryMainWin");
  	categoryMainWin = {
  		init : function(){
  			var swFlag=categoryMainObj.panel("options").swFlag;
			var dateFlag=categoryMainObj.panel("options").dateFlag;
			var startDate=categoryMainObj.panel("options").startDate;
			var endDate=categoryMainObj.panel("options").endDate;
			var orgCode=categoryMainObj.panel("options").orgCode;
  			
  			$.ajax({
				  url:'/jubao/petition/petition-issue-info!petitionTypeAndIssueType.action',  
				  type : "post",
			      async : false,
				  data:{
				    	swFlag : swFlag,
				    	dateFlag : dateFlag,
				    	startDateValue　: startDate,
				    	endDateValue :　endDate,
				    	orgCode : orgCode
				  },
				  success:function(data){
					  var params={
	 	    			  operationFuncCode : "petitionTypeAndIssueType",
	 	    			  operationFuncName : "信访件分布情况钻取查询信访件类别及数量",
	 	    			  operationBtnName : dateFlag,
	 	    			  operationDesc : "信访件分布情况钻取查询信访件类别及数量",
	 	    			  operationTypeCode : OperationType.QUERY,
	 	    			  operationTypeName : OperationType.QUERY_NAME,
	 	    			  enableDataLog :true
	 	    		  }
	 	    	      saveOperaLog(params);
					  
					  var myChart = echarts.init(document.getElementById('petitionType'));
						option = {
							title : {
								text : '信访类别及数量',
								x : 'center',
								textStyle : {
									fontSize : 14,
									fontWeight : 'normal',
									color : '#fff'
								}
							},
							tooltip : {
								trigger : 'item',
								position: function(a){
									a[1] =a[1]-40;
									return a;
						        }
							},
							toolbox : {show : false,},
							calculable : true,
							grid : {
								borderWidth : 0,
								y : 50,
								y2 : 60
							},
							xAxis : [ {
								type : 'category',
								data : [ '自收件', '下级转、交办' ],
								axisLine : {
									lineStyle : {
										color : '#fff',
										width : 1,
										type : 'solid'
									}
								},
								splitLine : {
									show : false
								},
								axisLabel : {
									textStyle : {
										color : '#ffffff',
									},
								},
								axisTick : {
									show : false,
								},
							} ],
							yAxis : [ {
								type : 'value',
								show : true,
								axisLine : {
									lineStyle : {
										color : '#fff',
										width : 1,
										type : 'solid'
									}
								},
								splitLine : {
									lineStyle : {
										color : '#fff',
										width : 1,
										type : 'dotted'
									}
								},
								axisLabel : {
									textStyle : {
										color : '#ffffff',
									},
								},
							} ],
							series : [ {
								name : '数量',
								type : 'bar',
								itemStyle : {
									normal : {
										color : function(params) {
											var colorList = [ '#E70012', '#FFBE00','#14D296', ];
											return colorList[params.dataIndex]
										},
										label : {
											show : true,
											position : 'top',
											formatter : '{c}',
											textStyle : {
												color : '#ffffff',
											},
										}
									}
								},
								barGap : '80',
								barWidth : 40,//柱子的宽度
								data : eval('(' + data + ')')
							} ]
						};
						myChart.setOption(option,true);
						
						myChart.on('click',function(param){
							if(letterInfoWinObj){
					    		 letterInfoWinObj.panel("options").swFlag=swFlag;
					    		 letterInfoWinObj.panel("options").dateFlag=dateFlag;
					    		 letterInfoWinObj.panel("options").startDate=startDate;
					    		 letterInfoWinObj.panel("options").endDate=endDate;
					    		 letterInfoWinObj.panel("options").orgCode=orgCode;
					    		 letterInfoWinObj.panel("options").index=param.dataIndex;
					    	 }else{
					    		 letterInfoWinObj = $.f("div","LetterInfoWin").window({
						    		    width:1000, 
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
						    			orgCode:orgCode,
						    			index:param.dataIndex,
						    			onOpen:function(){
						    				letterInfoWin.init();
						    			},
						    			onClose : function(){
						    				 letterInfoWinObj.panel("options").swFlag="";
								    		 letterInfoWinObj.panel("options").dateFlag="";
								    		 letterInfoWinObj.panel("options").startDate="";
								    		 letterInfoWinObj.panel("options").endDate="";
								    		 letterInfoWinObj.panel("options").orgCode="";
								    		 letterInfoWinObj.panel("options").index="";
								 		}
						    	 });
					    	 }
							letterInfoWinObj.window("open");
					     });
			      },
			      error:function(){  
			           dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
			           return;
			      }  
			 });
  			
  			$.ajax({
				  url:'/jubao/petition/petition-issue-info!issueTypeCount.action',  
				  type : "post",
			      async : false,
			      data:{
						swFlag : swFlag,
				    	dateFlag : dateFlag,
				    	startDateValue　: startDate,
				    	endDateValue :　endDate,
				    	orgCode : orgCode
					},
					success:function(data){
						var params={
		 	    			  operationFuncCode : "issueTypeCount",
		 	    			  operationFuncName : "信访件分布情况钻取查询问题类别及数量",
		 	    			  operationBtnName : dateFlag,
		 	    			  operationDesc : "信访件分布情况钻取查询问题类别及数量",
		 	    			  operationTypeCode : OperationType.QUERY,
		 	    			  operationTypeName : OperationType.QUERY_NAME,
		 	    			  enableDataLog :true
		 	    		}
		 	    	    saveOperaLog(params);
						
						$("#issueType").html('');
						var datas = eval('(' + data + ')');
						var Stat = G2.Stat;
						var Frame = G2.Frame;
						var frame = new Frame(datas);
						frame = Frame.sort(frame, 'count'); // 将数据按照count 进行排序，由大到小
						var chart = new G2.Chart({
							id : 'issueType',
							forceFit : true,
							height : 350,
							plotCfg : {
								margin : [ 8, 50, 10, 140 ]
							}
						});
						chart.source(frame);
						chart.axis('name', {
							title : null,
							line : null,
							tickLine : null,
							labels : {
								label : {
									fill : '#ffffff',
									fontSize : '12',
								}
							}
						});
						chart.axis('count', {
							line : null,
							tickLine : null,
							title : null,
							grid : false,
							labels : null,
						});
						// chart.source(data);//随机排序
						chart.coord('rect').transpose();
						chart.interval().position('name*count').color('#01BDC8').label('count', {
							label : {
								fill : '#fff'
							},
							offset : 5,//距离图像的距离
						});
						chart.render();
			      },
			      error:function(){  
			           dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
			           return;
			      }  
			 });
  		}
  	}
</script>
  </body>
</html>