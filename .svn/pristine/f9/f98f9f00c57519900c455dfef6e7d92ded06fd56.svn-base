$.fn.extend({
		/**
		 * 适用
		 * 柱状图，折线图，条形图，面积图，一组多色图
		 * @param {Object} paroption
		 * @memberOf {TypeName} 
		 * @return {TypeName}   echarts图表对象
		 */
		EchartLineOrBar:function(paroption){
			var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
			var returnString= function (val, num){
		   			var length=0;
		   			if(val.length%num==0){
		   				length=val.length/num
		   			}else{
		   				length=val.length/num+1
		   			}
		   			var str="";
		   			var sub=0;
		   			for(var i=0;i<length;i++){
		   				str+=val.substring(sub,sub+num)+"\n"
		   				sub+=num
		   			}
		   			return str;
		      }
			option = {
				them:"default",//主题
				async:true,//是否异步请求
				echartType:"bar",//图的类型
				oneBarManyColor:null,//是否支持一组多色   可以是一个数组["xsxx",xsxx","yyyy""yyyy"]
				isTiao:false,//是否是条形图
				isArea:false,//是否是面积图
				isMax:false,//是否有最大值
				isMin:false,//是否有最小值
				isAverage:false,//是否有平均值
				isResize:true,//是否自适应窗体宽//
				dataUrl:null,//数据取值地址
				parameter:null,//参数
				wrap:null,//隔字换行
				xx:null,//接受数组[0,1,0,1]
				yy:null,//接受数组[0,1,0,1]
				datamanage:function(data){
					return data;
				},
				legendSelectked:null,//图例点击事件
				seriesClick:null,//内容点击事件
			    title : {
					show:false,//是否显示
			        text: '',//标题
			        subtext: '',//副标题
			        x:"left",//x轴位置
			        y:"top",//y轴位置
			    },
			    tooltip : {
			        trigger: 'item'
			    },
			    legend: {
					show:false,
			        data:['巡视情况分析']
			    },
			    calculable : true,//是否拖拽重计算
			    xAxis : [
			        {
			            type : 'category',
			            data : []
			        }
			    ],
			    yAxis : [
			        {
						
			            type : 'value'
			        }
			    ],
			    series : [ 
						 {
							name:'巡视情况分析',
							type:'bar',
							data:[6,2,1,1]
						 }]
						 
			};
		var newoption=$.extend( true,  {},option,paroption)
		var myChart = echarts.init(this[0],newoption.them);
		if(newoption.dataUrl!=null){
				$.ajax({
					type:"post",
					url: newoption.dataUrl, 
					data:newoption.parameter,
					async:newoption.async,
					success: function(data){
							try{data=	eval("("+data+")")}catch(e){}
							data=newoption.datamanage(data)
							newoption.legend.data=[];
							newoption.series=[];
							for(var i=0;i<data.valuedata.length;i++){
									newoption.legend.data.push(data.valuedata[i].name);//图例
									if(newoption.echartType=="bar"){//这里看类型
											data.valuedata[i].type="bar"
											if(newoption.oneBarManyColor!=null){
												data.valuedata[i].stack=newoption.oneBarManyColor[i]
											}
												
									}else if(newoption.echartType=="line"){
											data.valuedata[i].type="line"
											if(newoption.isArea){
												 	data.valuedata[i].itemStyle={normal: {areaStyle: {type: 'default'}}}
											}
									}else if($.type(newoption.echartType)=="array"){
											if(newoption.echartType.length>=data.valuedata.length){
													if("line"==newoption.echartType[i]||"bar"==newoption.echartType[i]){
															data.valuedata[i].type=newoption.echartType[i];
																if("line"==newoption.echartType[i]&&newoption.isArea){
																		 data.valuedata[i].itemStyle={normal: {areaStyle: {type: 'default'}}}
																}else if("bar"==newoption.echartType[i]){
																		if(newoption.oneBarManyColor!=null){
																			data.valuedata[i].stack=newoption.oneBarManyColor[i]
																			}
																	
																}else{
																		tryconsole("oneBarManyColor或者isArea配置有误");
																}
													}else{
															tryconsole("您的echartType 索引为"+i+"的类型写错了。只支持line和bar，您写的是"+newoption.echartType[i]);
													}
											}else{
														var ss=newoption.echartType.length-data.valuedata.length;
														tryconsole("您的echartType少长度和返回的数据长度少"+ss+"个");
											}
									}else{
										tryconsole("看看你的echartType是不是填写错了，目前只支持 bar, line，数组,且数组里值支持 bar ， line")
									}
									
									if(newoption.isMax){//这里是判断是否有最大值
										  data.valuedata[i].markPoint = {
													                data : [
													                    {type : 'max', name: '最大值'}
													                ]
													            }
									}
									if(newoption.isMin){//这里判断是否有最小值
										if(data.valuedata[i].markPoint){
												  data.valuedata[i].markPoint.data.push({type : 'min', name: '最小值'})
										}else{
												  data.valuedata[i].markPoint = {
													                data : [
													                    {type : 'min', name: '最小值'}
													                ]
												}
										}
									}
									if(newoption.isAverage){//这里判断是否有平均值
												  data.valuedata[i].markLine = {
										                data : [
										                    {type : 'average', name : '平均值'}
										                ]
										            }
									}
									//	xAxisIndex
									if(newoption.yAxis.length>1){
										if(newoption.yy!=null||$.type(newoption.yy)=="array"){
											if(newoption.yy.length>=data.valuedata.length){
													data.valuedata[i].yAxisIndex=newoption.yy[i]
											}else{
													tryconsole("yy属性里的数组和返回结果不符合");
											}
										}else{
											tryconsole("您是双y 轴，yy属性不能为空并且必须是数组,数组里必须是0或者1");
										}
										
									}
									//	xAxisIndex
									if(newoption.xAxis.length>1){
										if(newoption.xx!=null||$.type(newoption.xx)=="array"){
											if(newoption.yy.length>=data.valuedata.length){
													data.valuedata[i].xAxisIndex=newoption.xx[i]
											}else{
													tryconsole("xx属性里的数组和返回结果不符合");
											}
										}else{
											tryconsole("您是双xx轴，xx属性不能为空并且必须是数组");
										}
										
									}
									
									
									newoption.series.push(data.valuedata[i]);//值
							}
							
						
							if(	newoption.wrap!=null){
							
								newoption.xAxis[0].axisLabel=	$.extend(true,{},newoption.xAxis[0].axisLabel,{
						               interval:0,
						               formatter:function(val){
						               		 return returnString(val,newoption.wrap)
						               },
						            })
							}
							if(newoption.isTiao){
										var them=newoption.xAxis;
										newoption.xAxis=newoption.yAxis;
										newoption.yAxis=them;
							}
							myChart.setOption(newoption);
							var ecConfig = echarts.config
							if(newoption.legendSelectked!=null){
								myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
							}
							if(newoption.seriesClick!=null){
								myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
							}
							if(newoption.isResize){
							$(window).resize(function(){
													   myChart.resize();
												})
							}
							return myChart
							
					},
					error:function(data){
						tryconsole("Ajax请求错误，请检查dataUrl错误");
					}
				})
				
			
		}else{
			
			if(	newoption.wrap!=null){
				newoption.xAxis[0].axisLabel=	$.extend(true,{},newoption.xAxis[0].axisLabel,{
		               interval:0,
		               formatter:function(val){
		               		 return returnString(val,newoption.wrap)
		               },
		            })
			}
				if(newoption.isTiao){
							var them=newoption.xAxis;
							newoption.xAxis=newoption.yAxis;
							newoption.yAxis=them;
				}
				myChart.setOption(newoption);
				var ecConfig = echarts.config
				if(newoption.legendSelectked!=null){
					myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
				}
				if(newoption.seriesClick!=null){
					myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
				}
				if(newoption.isResize){
				$(window).resize(function(){
										   myChart.resize();
									})
				}
				return myChart

			
			
			
			
		}
	
	
		},
		/**
		 * 适用:饼图 枚瑰图
		 * @param {Object} paroption
		 * @memberOf {TypeName} 
		 * @return {TypeName} 
		 */
		EchartPie:function(paroption){
				var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
				var	option = {
							them:"default",//主题
							async:true,//是否异步请求
							isResize:true,//是否自适应窗体宽//
							echartType:"pie",//图的类型
							tooltipName:"",//气泡标题
							radiusnull:null,//空心圆
							radius:"55%",//圆半径
							centerX:"50%",//圆坐标X
							centerY:"60%",//圆坐标Y
							selectedMode: 'single',//选中方式 single /multiple
							dataUrl:null,//数据取值地址
							parameter:null,//参数
							labelposition:"out",
							datamanage:function(data){//数据管理
								return data;
							},
							legendSelectked:null,//图例点击事件
							seriesClick:null,//内容点击事件
						    title : {

						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
								show:false,
						        orient : 'vertical',
						        x : 'left',
						        data:['违反生活纪律','违反组织纪律','违反政治纪律','违反廉洁纪律','违反群众纪律','违反工作纪律']
						    },
						  
						    calculable : true,
						    series : [	
									
									{
									  name:'问题类别占比',
									  type:'pie',
									  radius : ['20%', '55%'],
									  center: ['50%', '60%'],
									  roseType : 'area',
									  data:[
										{"value":335, "name":"违反生活纪律"},
										{"value":310, "name":"违反组织纪律"},
										{"value":210, "name":"违反工作纪律"},
										{"value":234,"name":"违反政治纪律"},
										{"value":135, "name":"违反廉洁纪律"},
										{"value":1548, "name":"违反群众纪律"}
										]
									} 
										]
						};
				
				   var  itemStyle = {
			                normal : {
			                    label : {
			                        position : 'inner'
			                    },
			                    labelLine : {
			                        show : false
			                    }
			                },
			              	emphasis:{
			              	  label : {
			                    show:true,
			                        position : 'inner'
			                    },
			                    labelLine : {
			                        show : false
			                    }
			             	 }
			            }
					var newoption=$.extend( true,  {},option,paroption)
					var myChart = echarts.init(this[0],newoption.them);
					
					if(newoption.dataUrl!=null){
							$.ajax({
								type:"post",
								url: newoption.dataUrl, 
								data:newoption.parameter,
								async:newoption.async,
								success: function(data){
										try{data=	eval("("+data+")")}catch(e){}
										data=newoption.datamanage(data)
										newoption.legend.data=[];
										for(var i=0;i<data.valuedata[0].data.length;i++){
												newoption.legend.data.push(data.valuedata[0].data[i].name)
										}
										
										newoption.series=data.valuedata;
										if(newoption.tooltipName!=null){//name
												newoption.series[0].name=newoption.tooltipName
										}
										if(newoption.selectedMode="single"){
												newoption.series[0].selectedMode="single"
										}else if(newoption.selectedMode="multiple"){
											newoption.series[0].selectedMode="multiple"
										}else{
												tryconsole("点击选中方式只能是single(单选)或者multiple(复选)"+newoption.selectedMode);
										}
										if(newoption.echartType=="pie"||newoption.echartType=="radius"||newoption.echartType=="area"){
												newoption.series[0].type="pie"
													if(newoption.echartType=="radius"||newoption.echartType=="area"){
															newoption.series[0].roseType=newoption.echartType;
													}
											
										}else{
												tryconsole("图类型只允许pie,radius,area您写成了"+newoption.echartType);
										}
										if(newoption.radiusnull){//是空心圆
												newoption.series[0].radius=[newoption.radiusnull,newoption.radius]
										}else{//实心圆
												newoption.series[0].radius=newoption.radius;
										}
										if(newoption.labelposition=="out"){
											
										}else if(newoption.labelposition=="in"){
												newoption.series[0].itemStyle=itemStyle;
										}else{
												tryconsole("图类型只允许out,in您写成了"+newoption.labelposition);
										}
										
										newoption.series[0].center=[newoption.centerX,newoption.centerY];
										myChart.setOption(newoption);
										
										var ecConfig = echarts.config
										if(newoption.legendSelectked!=null){
											myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
										}
										if(newoption.seriesClick!=null){
											myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
										}
										if(newoption.isResize){
											$(window).resize(function(){
													   myChart.resize();
												})
										}
										return myChart
															
										},
								error:function(data){
									tryconsole("Ajax请求错误，请检查dataUrl错误");
								}
					})
					
								
					}else{
									myChart.setOption(newoption);
										
										var ecConfig = echarts.config
										if(newoption.legendSelectked!=null){
											myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
										}
										if(newoption.seriesClick!=null){
											myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
										}
										if(newoption.isResize){
											$(window).resize(function(){
													   myChart.resize();
												})
										}
										return myChart
						
						
						
					}
		},
		/**
		 * 适用:
		 * 			圆环图
		 * @param {Object} paroption
		 * @memberOf {TypeName} 
		 * @return {TypeName} 
		 */
		EchartPieMany:function(paroption){
				var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
				var dataStyle = {
						    normal: {
						        label: {show:false},
						        labelLine: {show:false}
						    }
						};
				var placeHolderStyle = {
				    normal : {
				        color: 'rgba(0,0,0,0)',
				        label: {show:false},
				        labelLine: {show:false}
				    },
				    emphasis : {
				        color: 'rgba(0,0,0,0)'
				    }
				};
						option = {
							them:"default",//主题
							async:true,//是否异步请求
							isResize:true,//是否自适应窗体宽//
							tooltipName:null,//气泡标题
							radiusnull:125,//空心圆
							radius:150,//圆半径
							radiuswidth:25,//圆环宽
							centerX:"20%",//圆坐标X
							centerY:"60%",//圆坐标Y
							selectedMode: 'single',//选中方式 single /multiple
							dataUrl:null,//数据取值地址
							parameter:null,//参数
							legendSelectked:null,//图例点击事件
							seriesClick:null,//内容点击事件
							datamanage:function(data){
							return data
							},
						    title: {
						        
						        x: 'center',
						        y: 'center',
						        itemGap: 20,
						        textStyle : {
						            color : 'rgba(30,144,255,0.8)',
						            fontFamily : '微软雅黑',
						            fontSize : 35,
						            fontWeight : 'bolder'
						        }
						    },
						    tooltip : {
						        show: true,
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient : 'horizontal',
						        data:['省部级','地厅级','县处级','乡科及以下','其他']
						    },
						   
						    series : [  {
									name:'省部级',
									type:'pie',
									clockWise:false,
									radius : [110, 125],
									itemStyle : dataStyle,
									data:[
										{
											value:60,
											name:'省部级'
										},
										{
											value:40,
											name:'invisible',
											itemStyle : placeHolderStyle
										}
									]
								},
								{
									name:'地厅级',
									type:'pie',
									clockWise:false,
									radius : [95, 110],
									itemStyle : dataStyle,
									data:[
										{
											value:29, 
											name:'地厅级'
										},
										{
											value:71,
											name:'invisible',
											itemStyle : placeHolderStyle
										}
									]
								},
								{
									name:'县处级',
									type:'pie',
									clockWise:false,
									radius : [80, 95],
									itemStyle : dataStyle,
									data:[
										{
											value:20, 
											name:'县处级'
										},
										{
											value:80,
											name:'invisible',
											itemStyle : placeHolderStyle
										}
									]
								},
								{
									name:'乡科及以下',
									type:'pie',
									clockWise:false,
									radius : [65, 80],
									itemStyle : dataStyle,
									data:[
										{
											value:8, 
											name:'乡科及以下'
										},
										{
											value:80,
											name:'invisible',
											itemStyle : placeHolderStyle
										}
									]
								},
								{
									name:'其他',
									type:'pie',
									clockWise:false,
									radius : [50, 65],
									itemStyle : dataStyle,
									data:[
										{
											value:5, 
											name:'其他'
										},
										{
											value:80,
											name:'invisible',
											itemStyle : placeHolderStyle
										}
									]
								}
							]
						};
						                    
	     	var newoption=$.extend( true,  {},option,paroption)
			var myChart = echarts.init(this[0],newoption.them);
	     	if(newoption.dataUrl!=null){
							$.ajax({
								type:"post",
								url: newoption.dataUrl, 
								data:newoption.parameter,
								async:newoption.async,
								success: function(data){
										try{data=	eval("("+data+")")}catch(e){}
										data=newoption.datamanage(data)
										var datalength=data.length;
										newoption.legend.data=[];
										newoption.series=[]
										for(var i=0;i<data.valuedata.length;i++){//图例
												for(var a=0;a<data.valuedata[i].data.length;a++){
														if(data.valuedata[i].data[a].name!="invisible"){
																newoption.legend.data.push(data.valuedata[i].data[a].name)
														}
												}
												if(newoption.tooltipName!=null||$.type(newoption.tooltipName)=="array"){
														data.valuedata[i].name=newoption.tooltipName[i];
												}else if(newoption.tooltipName==null){
													
												}else{
														tryconsole("个人觉得啊，tooltipName 只能是一个数组格式像这样[\"气泡标题1\"，\"气泡标题2\"，\"气泡标题3\"]");
												}
												if($.type(newoption.radius)=="number"&&$.type(newoption.radiusnull)=="number"&&$.type(newoption.radiuswidth)=="number"){
														var radiunull=	newoption.radiusnull-i*newoption.radiuswidth
														var radiu=	newoption.radius-i*newoption.radiuswidth
														data.valuedata[i].radius =[radiunull, radiu]
													}else{
														tryconsole("个人觉得啊，radius(半径) ，radiusnull（空心圆）都必须是一个数字");
													}
												data.valuedata[i].type='pie';
						         			   	data.valuedata[i].clockWise=false;
						         			   	data.valuedata[i].itemStyle=dataStyle;
						         			 //家data
						         			   	$(	data.valuedata[i].data).each(function(){
						         			   			if("invisible"==this.name){
						         			   				this.itemStyle=placeHolderStyle;
						         			   				this.tooltip={show:false}
						         			   			}
						         			   	})
												if(typeof newoption.series=="undefined"){
													newoption.series=[];
												}
						         			  newoption.series.push(data.valuedata[i]) 	 
										}
									myChart.setOption(newoption);
										if(newoption.isResize){
											$(window).resize(function(){
													   myChart.resize();
												})
											}
										var ecConfig = echarts.config
											if(newoption.legendSelectked!=null){
												myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
											}
											if(newoption.seriesClick!=null){
												myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
											}
										return myChart
								},
								error:function(){	
										tryconsole("Ajax请求错误，请检查dataUrl错误");
								}
								});	                    
		
			}else{
				myChart.setOption(newoption);
				if(newoption.isResize){
					$(window).resize(function(){
							   myChart.resize();
						})
					}
				var ecConfig = echarts.config
					if(newoption.legendSelectked!=null){
						myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
					}
					if(newoption.seriesClick!=null){
						myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
					}
				return myChart
			}
	    },
	    /**
	     * 适用:雷达图
	     * @param {Object} paroption
	     * @memberOf {TypeName} 
	     * @return {TypeName} 
	     */
	    EchartsRadar:function(paroption){
	    	var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
				option = {
						them:"default",
						async:true,//是否异步请求
						isResize:true,//是否自适应窗体宽//
						radius:"50%",//雷达图半径
						option:null,//各属性值   格式[{text:'某某',max:100}]
						dataUrl:null,//数据路径
						parameter:null,//参数
						legendSelectked:null,//图例点击事件
						seriesClick:null,//内容点击事件
						datamanage:function(data){
							return data
						},
					    title : {
					        text: '罗纳尔多 vs 舍普琴科',
					        subtext: '完全实况球员数据'
					    },
					    tooltip : {
					        trigger: 'item'
					    },
					    legend: {
					        x : 'center',
					        data:[]
					    },
					    polar : [
					        {
					            indicator :[],
					            radius :  '50'
					        }
					    ],
					    series : [
					        {
					            name: '',
					            type: 'radar',
					            itemStyle: {
					                normal: {
					                    areaStyle: {
					                        type: 'default'
					                    }
					                }
					            },
					            data : []
					        }
					    ]
					};
		    	var newoption=$.extend( true,  {},option,paroption)
				var myChart = echarts.init(this[0],newoption.them);
				if(newoption.resize){
					window.addEventListener("resize", function () {
		                myChart.resize();
					});
				}
				if(newoption.dataUrl!=null){
					$.ajax({
						type:"post",
						url: newoption.dataUrl, 
						data:newoption.parameter,
						async:newoption.async,
						success: function(data){
							try{data=	eval("("+data+")")}catch(e){}
							data=newoption.datamanage(data)
							newoption.legend.data=[];//初始化图例
					    	newoption.series[0].data=[];//初始化数据
					    	newoption.polar[0].radius=newoption.radius;//控制半径
					    	if($.type(newoption.option)=="array"){
					    		$(newoption.option).each(function(){
					    			if(this.text){
					    				if(this.max){
					    						newoption.polar[0].indicator=newoption.option;
					    				}else{
					    					tryconsole("option的max属性缺失")
					    				}
					    			}else{
					    				tryconsole("option的text属性缺失")
					    			}
					    		})
					    	}else{
					    		tryconsole("option只能是一个数组")
					    	}
					    	for(var i=0;i<data.valuedata.length;i++){
					    		newoption.legend.data.push(data.valuedata[i].name);
					    		data.valuedata[i].value=data.valuedata[i].data;
					    		data.valuedata[i].data=null;
			    				newoption.series[0].data.push(data.valuedata[i])
					    	}
							myChart.setOption(newoption);
							if(newoption.isResize){
							$(window).resize(function(){
													   myChart.resize();
												})
								}
							var ecConfig = echarts.config
								if(newoption.legendSelectked!=null){
									myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
								}
								if(newoption.seriesClick!=null){
									myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
								}
							return myChart
						},
						error:function(data){
								tryconsole("Ajax请求错误，请检查dataUrl错误")
						}
					})
				}else{
						tryconsole("地址可能错了")
				}
	},
	/**
	 * 适用:仪表盘
	 * @param {Object} paroption
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	EchartsGauge:function(paroption){
		var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
		var option = {
			  them:"default",
			  dataUrl:null,
			  async:true,//是否异步请求
			  parameter:null,//参数
			  isResize:true,//是否自适应窗体宽//
			  tooltipName:null,//气泡标题
			  maxsplitNumber:5,//大的分隔段
			  startAngle:225,//开始角度
			  endAngle:-45,//结束角度
			  color: [[0.2, 'yellow'],[0.5, '#48b'],[1, '#ff4500']], 
			  width:3,//线条宽
			   minsplitNumber: 10, //小分隔端
			   pointer:5,//这个是指针宽
			   	legendSelectked:null,//图例点击事件
				seriesClick:null,//内容点击事件
			
			   datamanage:function(data){
							return data
				},
			   title:{  //标题
							show : true,
			                offsetCenter: ['0', '-40%'],       // x, y，单位px
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                 fontWeight: 'bolder'
			                }
				},
				detail:{
						 	 formatter:'{value}km',
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto',
			                    fontWeight: 'bolder'
			                }
				},
   			  tooltip : {
      			  formatter: "{a} <br/>{b} : {c}"
    		  },
   			  series :[
			        {
			            name:'Echart仪表盘',
			            type:'gauge',
			            splitNumber: 5,
			            startAngle:225,
            			endAngle:-45,
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: [[0.2, 'yellow'],[0.5, '#48b'],[1, '#ff4500']], 
			                    width: 3
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                splitNumber: 10,   // 每份split细分多少段
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                show: true,        // 默认显示，属性show控制显示与否
			                length :30,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            pointer : {
			                width : 5
			            },
			            title : {
			                show : true,
			                offsetCenter: ['0', '-40%'],       // x, y，单位px
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder'
			                }
			            },
			            detail : {
			                formatter:'{value}km',
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto',
			                    fontWeight: 'bolder'
			                }
			            },
			            data:[]
			        }
			    ]
			};
 		var newoption=$.extend( true,  {},option,paroption)
		var myChart = echarts.init(this[0],newoption.them);
		if(newoption.dataUrl!=null){
			$.ajax({
				type:"post",
				url: newoption.dataUrl, 
				data:newoption.parameter,
				async:newoption.async,
				success: function(data){
					try{data=	eval("("+data+")")}catch(e){}
					data=newoption.datamanage(data)
			    	newoption.series[0].data=[]
			    	$(data.valuedata).each(function(){
			    		this.value=this.data
						this.data=null;
			    	})
			    	newoption.series[0].data=data.valuedata
			    	newoption.series[0].name=newoption.tooltipName;
			    	newoption.series[0].splitNumber=newoption.maxsplitNumber;
			    	newoption.series[0].axisTick.splitNumber=newoption.maxsplitNumber;
			    	newoption.series[0].startAngle=newoption.startAngle;
			    	newoption.series[0].endAngle=newoption.endAngle;
			    	newoption.series[0].axisLine.lineStyle.color=newoption.color;
			    	newoption.series[0].axisLine.lineStyle.width=newoption.width;
			    	newoption.series[0].pointer.width=newoption.pointer;
			    	newoption.series[0].title=newoption.title;
			    	newoption.series[0].detail=newoption.detail;
			    	
	    			if(newoption.isResize){
							$(window).resize(function(){
													   myChart.resize();
												})
						}
					var ecConfig = echarts.config
						if(newoption.legendSelectked!=null){
							myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
						}
						if(newoption.seriesClick!=null){
							myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
						}
					myChart.setOption(newoption);
					return myChart
				},
				error:function(data){
					console.log("Ajax请求错误，请检查url错误")
				}
			})
		}else{
			try{
				console.log("地址可能错了")
			}catch(r){
				alert("地址可能错了")
			}
		}
	},
	/**
	 * 适用:漏斗图，金字塔
	 * @param {Object} paroption
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	EchartsFunnel:function(paroption){
			var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
			var option = {
				  them:"default",
				  dataUrl:null,
				  async:true,//是否异步请求
				  parameter:null,//参数
				  isResize:true,//是否自适应窗体宽//
				  legendSelectked:null,//图例点击事件
				  seriesClick:null,//内容点击事件
				  tooltipName:null,//气泡标题
				  width:"50%",//宽
				  height:"70%",//高
				  x:"25%",
				  y:"20%",
				  sort: 'ascending',//金字塔还是漏斗
				  datamanage:function(data){
							return data
					},
				   title : {
				        text: '漏斗图',
				        subtext: '纯属虚构'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c}"
				    },
				    legend: {
				        data : ['展现','点击','访问','咨询','订单']
				    },
				    series : [
				        {
				            name:'漏斗图',
				            type:'funnel',
				          	width:"50%",
				          	height:"70%",
				         	 x:"25%",
				         	 y:"20%",
				         	sort : 'ascending',
				            data:[
				                {value:60, name:'访问'},
				                {value:40, name:'咨询'},
				                {value:20, name:'订单'},
				                {value:80, name:'点击'},
				                {value:100,name:'展现'}
				            ]
				        }
				    ]
				};
		 	var newoption=$.extend( true,  {},option,paroption)
				var myChart = echarts.init(this[0],newoption.them);
				if(newoption.resize){
					window.addEventListener("resize", function () {
		                myChart.resize();
					});
				}
				
				if(newoption.dataUrl!=null){
					$.ajax({
						type:"post",
						url: newoption.dataUrl, 
						async:false, 
						data:newoption.par,
						success: function(data){
							try{data=	eval("("+data+")")}catch(e){}
							data=newoption.datamanage(data)
							newoption.legend.data=[];
					    	newoption.series[0].data=[];
					    	newoption.series[0].name=newoption.tooltipName;
					    	newoption.series[0].width=newoption.width;
					    	newoption.series[0].height=newoption.height;
					    	newoption.series[0].sort=newoption.sort;
					    	newoption.series[0].x=newoption.x;
					    	newoption.series[0].y=newoption.y;
					    	for(var i=0;i<data.valuedata.length;i++){
					    		newoption.legend.data.push(data.valuedata[i].name);
					    		data.valuedata[i].value=data.valuedata[i].data
					    		data.valuedata[i].data=null;
			    				newoption.series[0].data.push(data.valuedata[i])
					    	}
					        newoption.series[0].name=newoption.title.text;
							myChart.setOption(newoption);
								
			    			if(newoption.isResize){
								$(window).resize(function(){
													   myChart.resize();
												})
								}
							var ecConfig = echarts.config
								if(newoption.legendSelectked!=null){
									myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
								}
								if(newoption.seriesClick!=null){
									myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
								}
							return myChart
						},
						error:function(data){
						tryconsole("Ajax请求错误，请检查url错误")
						}
					})
				}else{
					tryconsole("地址为null")
				}
	},
	EchartsMap:function(paroption){
				optes = {
						them:"default",
						dataUrl:null,
						expansionUrl:"../china-main-city/",//扩展包
						async:true,//是否异步请求
						parameter:null,//参数
						isResize:true,//是否自适应窗体宽//ie8不支持
						legendSelectked:null,//图例点击事件
						seriesClick:null,//内容点击事件
						datamanage:function(data){
							return data;
						},
						drill:"county",//钻取等级
					    title: {
					        text : '全国34个省市自治区',
					        subtext : '中国区域图'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: '{b}\n{a}\n{c}'
					    },
					     dataRange: {
					    	color:["red","yellow"],
					        min: 0,
					        max: 2500,
					        x: 'left',
					        y: 'bottom',
					        text:['高','低'],           // 文本，默认为数值文本
					        calculable : true
					    },
					    series : [
					        {
					            name: '随机数据',
					            type: 'map',
					            mapType: 'china',
					            selectedMode : 'single',
					            showLegendSymbol: false,
					            itemStyle:{
					                normal:{label:{show:true},
					                    },
					                emphasis:{label:{show:true}}
					            },
								markPoint : {
					            symbolSize: 6,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
					            itemStyle: {
					                normal: {
					                    borderColor: '#87cefa',
					                    borderWidth: 1,            // 标注边线线宽，单位px，默认为1
					                    label: {
					                        show: false
					                    },
					                },
					                emphasis: {
					                    borderColor: '#1e90ff',
					                    borderWidth: 5,
					                    label: {
					                        show: false
					                    }
					                },
					                symbol:'emptyCircle',
					        	    },  
					            },
					        }
					    ]
					};
					var mapType = [
					   'china',
					// 23个省
					   '广东','青海','四川','海南','陕西', 
					   '甘肃','云南','湖南','湖北','黑龙江',
					   '贵州','山东','江西','河南','河北',
					   '山西','安徽','福建','浙江','江苏', 
					   '吉林','辽宁','台湾',
					// 5个自治区
					   '新疆','广西','宁夏','内蒙古','西藏', 
					// 4个直辖市
					   '北京','天津','上海','重庆',
					// 2个特别行政区
					   '香港','澳门'
					];
				initshi=[];
				var shen=[             
								{'上海':  {
								'上海' : [121.4648, 31.2891],
							 	'崇明县' : [121.40, 31.73],
							 	'宝山区' : [121.48, 31.41],
							 	'浦东新区' : [121.53 ,31.22],
							 	'嘉定区' : [121.24, 31.40],
							 	'杨浦区' : [121.52, 31.27],
							 	'徐汇区' : [121.43 ,31.18],
							 	'闵行区' :　[121.38, 31.12],
							 	'奉贤区' :　[121.47, 30.92],
							 	'南汇区' : [121.76, 31.05],
							 	'青浦区' : [121.10, 31.15],
							 	'金山区' : [121.16, 30.89],
							 	'松江区' : [121.24, 31.00],
							 	'卢湾区' : [121.47, 31.22],
								'长宁区' : [121.42,31.22],
								'静安区' : [121.45 ,31.23],
								'黄浦区' : [121.48, 31.23],
								'普陀区' : [121.40 ,31.25],
								'闸北区' : [121.45 ,31.25],
								'虹口区' : [121.50 ,31.27]
								}}
,					{"新疆":{
								'新疆' : [87.9236, 43.5883],
								'乌鲁木齐市' : [87.68,　 43.77],
								'吐鲁番地区' : [89.19,　 42.91],
								'哈密地区' : [93.44,　42.78],
								'昌吉回族自治州' : [87.31,　44.05],
								'五家渠市' : [87.54, 44.16],
								'石河子市' : [85.94,　44.27],
								'阿勒泰地区' : [88.13, 47.85],
								'塔城地区' : [82.96, 46.74],
								'克拉玛依市' : [84.77,　45.59],
								'博尔塔拉蒙古自治州' : [82.07, 44.90],
								'伊犁哈萨克自治州' : [81.32, 43.92],
								'阿克苏地区' : [80.29, 41.15],
								'阿拉尔市' : [81.28, 40.54],	
								'图木舒克市' : [79.06, 39.86],
								'喀什地区' : [75.98, 39.47],
								'和田地区' : [79.94,　37.12],
								'克孜勒苏柯尔克孜自治州' : [76.172825, 39.713431],
								'博尔塔拉蒙古自治州' : [82.07, 44.9]
							}},
						        {'甘肃' : {'甘肃' : [103.5901, 36.3043],
								'庆阳市' : [107.88　,36.03],
								'平凉市' : [106.68　,35.51],
								'天水市' : [105.69　,34.60],
								'陇南市' : [104.92 ,33.40],
								'定西市' : [104.57, 35.57],
								'白银市' : [104.18, 36.55],
								'兰州市' : [103.73　, 36.03],
								'武威市' : [102.61,　37.94],
								'金昌市' : [102.18, 38.50],
								'张掖市' : [100.46　,38.93],
								'嘉峪关市' : [98.27 ,39.80],
								'酒泉市' : [98.50　,39.71],
								'临夏回族自治州' : [103.22 , 35.62],
							   '甘南藏族自治州' : [102.92 ,34.98]
							    	}},
							    {'北京' : {
							 	'怀柔区' : [116.62, 40.32],
							 	'延庆县' : [115.97, 40.47],
							 	'密云县' : [116.85, 40.37],
								'平谷区' : [117.10, 40.13],
							   '顺义区' : [116.65, 40.13],
							   '昌平区' : [116.20, 40.22],
							   '海淀区' : [116.30, 39.95],
							   '朝阳区' : [116.43 ,39.92],
							   '通州区' : [116.65 ,39.92],
							   '大兴区' : [116.33, 39.73],
							   '房山区' : [115.98, 39.72],
							   '门头沟区' : [116.10, 39.93],
							   '石景山区' : [116.22, 39.90],
							   '丰台区' : [116.28, 39.85],
							   '崇文区' : [116.43, 39.88],
							   '宣武区' : [116.35, 39.87],
							   '东城区' : [116.42, 39.93],
							   '西城区' : [116.37, 39.92],
							   '中央' : [116.4440, 40.2440]}},
							    {'天津' :{'天津' : [117.4219, 39.4189],
							 	'蓟县' : [117.40, 40.05],
							 	'宝坻区' : [117.30, 39.75],
							 	'宁河县' : [117.83, 39.33],
							 	'武清区' : [117.05, 39.40],
								'汉沽区' : [117.80 ,39.25],
								'北辰区' : [117.13, 39.22],
								'塘沽区' :　[117.65, 39.02],
								'津南区' : [117.38, 38.98],
								'大港区' : [117.45, 38.83],
								'西青区' : [117.00, 39.13],
								'静海县' : [116.92, 38.93],
								'河西区' : [117.22, 39.12],
								'东丽区' : [117.30, 39.08],
								'和平区' : [117.20 ,39.12],
								'红桥区' :　[117.15 ,39.17],
								'河北区' : [117.18 ,39.15],
								'南开区' : [117.15 ,39.13],
								'河东区' : [117.22 ,39.12],
							   '北海市' : [109.314, 21.6211]}},
							    {'江苏' :{'江苏' : [118.8062, 31.9208],
							 	'苏州市'　:　[120.62,　31.32],
							 	'无锡市'　:　[120.29,　31.59],
							 	'南通市'　:　[120.86,　32.01],
							 	'常州市'　:　[119.95,　31.79],
							 	'镇江市'　:　[119.44,　32.20],
							 	'南京市'　:　[118.78,　32.04],
							 	'泰州市'　:　[119.90,　32.49],
							 	'扬州市'　:　[119.42,　32.39],
							 	'盐城市'　:　[120.13,　33.38],
							 	'淮安市'　:　[119.15,　33.50],
							 	'连云港市' :　[119.16,　34.59],
							 	'宿迁市'　:　[118.30　,33.96],
							 	'徐州市'　:　[117.20, 34.26]}},
							    {'广西' :{'广西' : [108.479, 23.1152],
								'桂林市'　:　[110.28, 25.29],
							   '贺州市'　:　[111.54, 24.44],
							   '柳州市'　:　[109.40, 24.33],
								'梧州市'　: [111.34,　23.51],
								'来宾市'　:　[109.24,  23.76],
								'贵港市' : [109.60, 23.10],
								'河池市'　:　[108.06,　24.70],
								'南宁市'　:　[108.33, 22.84],
								'玉林市'　:　[110.14,　22.64],
								'百色市'　:　[106.62,　23.91],
							   '钦州市'　:　[108.61,　21.96],
							   '崇左市'　:　[107.37,　22.42],
							   '防城港市' : [108.35, 21.70]}},
							    {'江西': {'江西': [116.0046, 28.6633],
								'九江市'　:　[115.97,　29.71],
								'景德镇市'　: [117.22,　29.30],
								'南昌市'　:　[115.89,　28.68],
								'宜春市'　:　[114.38,　27.81],
								'上饶市'　:　[117.97, 28.47],
								'鹰潭市' : [117.07, 28.27],
								'新余市'　:　[114.92,　27.81],
								'抚州市'　:　[116.34,　28.00],
								'赣州市'　:　[114.92,　25.85],
								'吉安市'　:　[114.97, 27.12],
								'萍乡市'　:　[113.85,　27.60]}},
							    {'安徽' :{'安徽' : [117.29, 32.0581],
								'淮北市'　:　[116.77,　33.97],
								'宿州市'　:　[116.97,　 33.63],
								'亳州市' : [115.78 ,33.85],
								'蚌埠市'　:　[117.34,　32.93],
							   '阜阳市'　:　[115.81,　32.89],
							   '淮南市'　:　[116.98, 32.62],
							   '滁州市'　:　[118.31,　 32.33],
							   '合肥市'　:　[117.27,　31.86],
							   '六安市'　:　[116.49,　 31.73],
								'巢湖市'　:　[117.87,　 31.62],
								'马鞍山市'　: [118.48,  31.56],
								'芜湖市'　:　[118.38,　 31.33],
								'铜陵市'　:　[117.82,　 30.93],
								'安庆市'　:　[117.03,　30.52],
								'池州市' : [117.48 ,30.67],
								'宣城市'　:　[118.73,  31.95],
								'黄山市' : [118.33, 29.72]}},
							    {'内蒙古' :{'内蒙古' : [111.4124, 40.4901],
							 	'呼伦贝尔市' : [119.77 ,49.22],
							 	'兴安盟' : [122.05, 46.08],
							 	'通辽市'　:　[122.28,　 43.63],
							 	'赤峰市'　:　[118.87,　42.28],
							 	'锡林郭勒盟' : [116.07 ,43.95],
							 	'乌兰察布市' : [113, 41.09],
								'呼和浩特市'　: [111.65,　40.82],
								'包头市'　:　　[110, 40.58],
								'巴彦淖尔市' : [107.42, 40.75],
								'鄂尔多斯市' : [109.80 ,39.62],
								'乌海市' :　[106.82,　39.67],
								'阿拉善盟' : [105.67, 38.83]}},
							    {'黑龙江' :{'黑龙江' : [127.9688, 45.368],
							 	'佳木斯市'　: [130.35,　46.83],
							 	'双鸭山市'　: [131.17,　46.65],
							 	'鸡西市'　: [130.97,　45.30],
							 	'七台河市'　: [130.83, 45.82],
							 	'牡丹江市'　: [129.58,　44.60],
							 	'鹤岗市'　:　[130.30,　47.33],
								'哈尔滨市' :　[126.63,　45.75],
								'伊春市'　:　[128.92,　47.73],
								'绥化市'　:　[127,　 46.63],
								'齐齐哈尔市' : [123.97,　47.33],
								'大庆市'　:　[125.03,　 46.58],
								'黑河市' 　:　[127.53, 50.22],
								'大兴安岭地区' : [124.12, 50.42]}},
							    {'山西' : {'山西' : [112.3352, 37.9413],
							 	'大同市'　:　[113.30, 40.12],
							 	'朔州市' : [112.43, 39.33],
							 	'忻州市' : [112.73, 38.42],
							 	'阳泉市'　:　[113.57,　37.85],
							 	'太原市'　:　[112.53,　37.87],
							   '吕梁市' : [111.13, 37.52],
							   '晋中市' : [112.75, 37.68],
							   '长治市'　:　[113.08,　36.18],
								'临汾市'　:　[111.50, 36.08],
								'晋城市'　:　[112.83,　35.52],
							   '运城市'　:　[110.97, 35.03]}},
							    {'广东' : {'广东' : [113.5107, 23.2196],
							 	'汕头市'　:　[116.69,　23.39],
							 	'潮州市'　:　[116.63,　23.68],
							   '梅州市'　:　[116.10,　24.55],
							   '揭阳市' : [116.37, 23.55],
							   '汕尾市' : [115.37, 22.78],
								'河源市'　:　[114.68,　23.73],
								'惠州市' :　[114.40,　23.09],
								'韶关市' :　[113.62,　24.84],
								'清远市' :　[113.01,　23.70],
								'广州市'　:　[113.23,　 23.16],
								'深圳市'　:　[114.07,　22.62],
								'东莞市'　:　[113.75,　 23.04],
								'珠海市'　:　[113.52,　22.30], 
								'中山市'　:　[113.38,　22.52],
								'佛山市' :　[113.11,　23.05],
								'肇庆市' :　[112.44,　23.05],
								'江门市'　:　[113.06,　22.61],
								'云浮市'　:　[112.02,　22.93],
								'茂名市' :　[110.88,　21.68],
								'阳江市' :　[111.95,　21.85],
								'湛江市' : [110.35, 21.27]}},
							    {'四川' :{'四川' : [103.9526, 30.7617],
							    '广元市' : [105.59,32.52],
								'巴中市'　: [106.73,31.86],
								'绵阳市' : [104.73,31.48],
								'达州市' : [107.50,31.22],
								'南充市' : [106.08,30.78],
								'广安市'　:　[106.61,30.48],
								'遂宁市'　:　[105.58,30.52],
								'德阳市' : [104.37,31.13],
								'成都市'　:　[104.06, 30.67],
								'资阳市'　:　[104.60,30.19],
								'眉山市' : [103.50,30.04],
								'内江市'　:　[105.04,29.59],
								'自贡市' : [104.46,29.23],
								'雅安市'　:　[102.97,29.97],
								'乐山市' : [103.77,29.57],
								'宜宾市' : [104.56,29.77],
								'泸州市'　:　[105.39,28.91],
								'攀枝花市' : [101.72,26.58],
								'凉山彝族自治州' : [102.27,27.90],
								'甘孜藏族自治州' : [101.97,30.05],
								'阿坝藏族羌族自治州' : [102.22,31.90]}},
							     {'西藏' :{'西藏' :[91.1865, 30.1465],
								'拉萨市' :　[91.11,29.97],
								'林芝地区' : [94.37 ,29.68],
								'昌都地区' : [97.18, 31.13],
								'山南地区' : [91.77 ,29.23],
								'那曲地区' : [92.07, 31.48],
								'日喀则地区' : [88.88, 29.27],
								'阿里地区' : [80.10 ,32.50]}},
							    {'云南' : {'云南' : [102.9199, 25.4663],
								'昭通市' :　[103.70,29.32],
								'曲靖市'　: [103.79,25.51],
							   '昆明市' :　[102.73, 25.04],
								'玉溪市' :　[102.52,24.35],
								'普洱市' :　[101.03, 23.07],
								'临沧市' : [100.08,23.88],
								'保山市' : [99.17,25.12],
								'丽江市'　: [100.25,26.86],
								'迪庆藏族自治州' : [99.70,27.83],
								'怒江傈僳族自治州' : [98.85,25.85],
								'大理白族自治州'	: [100.19,25.69],
								'德宏傣族景颇族自治州' : [98.58,24.43],
								'西双版纳傣族自治州' : [100.80,22.02],
								'楚雄彝族自治州' : [101.54,25.01],
								'红河哈尼族彝族自治州' : [103.40,23.37],
							 	'文山壮族苗族自治州' : [104.25,23.37]}},
							    {'浙江' :{'浙江' : [119.5313, 29.8773],
							 	'温州市' : [120.65, 28.01],
							 	'丽水市' : [119.92, 28.45],
							 	'台州市' : [121.43, 28.68],
							 	'金华市' : [119.64, 29.12],
								'衢州市' : [118.88, 28.97],
								'宁波市' : [121.56, 29.86],
								'绍兴市' : [120.58, 30.01],
								'杭州市' : [120.19, 30.26],
								'嘉兴市' : [120.76, 30.77],
								'舟山市' : [122.2, 30],
								'湖州市' : [120.10, 30.86]}},
							    {'湖北' :{'湖北' : [114.3896, 30.6628],
								'黄石市' :　[115.09,30.20],
								'咸宁市'　:　[114.28,　29.87],
								'黄冈市'　:　[114.87,　30.44],
								'鄂州市' : [114.88, 30.40],
								'武汉市'　:　[114.31,30.52],
								'孝感市'　:　[113.91,31.92],
							   '仙桃市' : [113.45 ,30.37],
							   '荆州市' : [112.23, 30.33],
							   '天门市' : [113.10 ,60.39],
							   '潜江市' : [112.89, 30.42],
							   '荆门市'　:　[112.19,　31.02],
							   '随州市' : [113.37, 31.72],
							   '襄樊市'　:　[112.14　,30.02],
							   '宜昌市'　: [111.30,　30.70],
							   '十堰市'　:　[110.79,　32.65],
							   '神农架林区' : [110.64, 31.75],
							    '恩施土家族苗族自治州' : [109.48, 30.27]}},
							    {'辽宁' :{'辽宁' : [123.1238, 42.1216],
								'沈阳市' : [123.38,41.8],
								'大连市' : [121.62,38.92],	
								'抚顺市' : [123.97,41.97],
								'本溪市' : [123.73,41.3],
								'锦州市' : [121.15,41.13],
								'丹东市' : [124.37,40.13],
								'阜新市' : [121.65,42],
								'营口市' : [122.18,40.65],
								'辽阳市' : [123.17,41.28],
								'铁岭市' : [123.85,42.32],
								'朝阳市' : [120.42,41.58],
								'鞍山市' : [122.85,41.12],
								'葫芦岛市' : [120.83,40.77],
								'盘锦市' : [122.03,41.07]}},
							    {'山东' :{'山东' : [117.1582, 36.8701],
								'泰安市' : [117.13,36.18],
								'济宁市' : [116.59,35.38],
								'荷泽市' : [115.43,35.24],
								'聊城市' : [115.97,36.45],
								'枣庄市' : [117.57,34.86],
								'莱芜市' : [117.67,36.19],
								'德州市' : [116.29,37.45],
								'济南市' : [117,36.65],
								'滨州市' : [118.03,37.36],
								'临沂市' : [118.35,35.05],	
								'淄博市' : [118.05,36.78],
								'日照市' : [119.46,35.42],	
								'东营市' : [118.49,37.46],
								'潍坊市' : [119.1,36.62],
								'烟台市' : [121.39,37.52],
								'威海市' : [122.1,37.5],
								'青岛市' : [120.33,36.07]}},
							    {'青海' :{'青海' : [101.4038,36.8207],
								'西宁市' : [101.74,36.56],
								'海东地区' : [102.12,36.50],
								'海北藏族自治州' : [100.90,36.97],
								'海南藏族自治州' : [100.62,36.28],
								'黄南藏族自治州' : [102.02,35.52],
								'海西蒙古族藏族自治州' : [97.37,37.37],
								'果洛藏族自治州' : [100.23,34.48],
								'玉树藏族自治州' : [97,43]}},
							    {'陕西' : {'陕西' : [109.1162, 34.2004],
								'西安市' : [108.95,34.27],
								'宝鸡市' : [107.15,34.38],
								'铜川	市' : [109.11,35.09],
								'榆林市' : [109.77,38.3],
								'延安市' : [109.47,36.6],
								'咸阳市' : [108.72,34.36],
								'渭南市' : [109.5,34.52],
								'安康	市' : [109.02,32.7],
								'汉中市' : [108.04,33.07],
								'商洛市' : [109.93,33.87]}},
							    {'贵州' : {'贵州' : [106.6992, 26.7682],
								'贵阳市' : [106.71,26.57],
								'六盘水市' : [104.82,26.58],
								'遵义市' : [106.9,27.7],
								'安顺市' : [105.55,26.14], 
								'铜仁地区' : [109.21,27.73],
								'毕节地区' : [105.29,27.32],
								'黔西南布依族苗族自治州' : [106.04,27.03],
								'黔南布依族苗族自治州' : [107.52,26.27],
								'黔东南苗族侗族自治州' : [107.97,26.58]}},
							     {'河南' :{'河南' : [113.4668, 34.6234],
								'郑州市' : [113.65,34.76],
								'开封市' : [114.35,34.79],
								'焦作市' : [113.21,35.24],
								'鹤壁市' : [114.17,35.9],
								'安阳市' : [114.35,36.1],		
								'濮阳市' : [114.98,35.71],
								'新乡市' : [113.85,35.31],	
								'商丘市' : [115.65,34.44],
								'周口市' : [114.63,33.63],
								'南阳市' : [112.53,33.01],	
								'平顶山市' : [113.29,33.75],
								'洛阳市' : [112.44,34.7],
								'信阳市' : [114.08,32.13],	
								'许昌市' : [113.81,34.02],
								'漯河市' : [114.02,33.56],
								'驻马店市' : [114.02,32.98],
								'三门峡市' : [111.19,34.76]}},
							    {'重庆' : {
								'城口县' : [108.67, 31.95],
								'巫溪县' : [109.63, 31.40],
								'巫山县' : [109.88, 31.08],
								'开县' : [108.42 ,31.18],
								'云阳县' : [108.67, 30.95],
								'奉节县' : [109.47, 31.02],
								'万州区' : [108.40 ,0.82],
								'梁平县' : [107.80, 30.68],
								'忠县' : [108.02, 30.30],
								'石柱土家族自治县' : [108.12,30.00],
								'垫江县' : [107.35, 30.33],
								'丰都县' : [107.73, 29.87],
								'长寿区' : [106.64, 29.01],
								'涪陵区' : [107.40 ,29.72],
								'渝北区' : [106.63 ,29.72],
								'北碚区' : [106.40 ,29.80],
								'合川区' : [106.28, 29.26],
								'潼南县' : [106.22, 30.03],
								'铜梁县' : [105.80, 30.16],
								'大足县' : [105.59, 29.40],
								'双桥区' : [105.78, 29.48],
								'荣昌县' : [106.21, 29.62],
								'永川区' : [105.71, 29.75],
								'江津区' : [106.25, 29.26],
								'綦江县' : [106.56, 29.41],
								'万盛区' : [105.91, 29.38],
								'南川区' : [107.11,29.16],
								'武隆县' : [107.75 ,29.33],
								'彭水苗族土家族自治县' : [108.17, 29.30],
								'黔江区' : [108.77, 29.53],
								'酉阳土家族苗族自治县' : [108.77,28.85],
								'秀山土家族苗族自治县' : [108.98 ,28.45],
								'江北区' : [106.57, 29.60],
								'沙坪坝区' : [106.45, 29.53],
								'壁山县' : [106.03, 29.86],
								'渝中区' : [106.57, 29.55],
								'南岸区' : [106.57 ,29.52],
								'九龙坡区' : [106.50, 29.50],
								'大渡口区' : [106.48,29.48],
								'巴南区' : [106.52, 29.38]}},
							    {'宁夏' : {
							 	'固原市' :　[106.28,36.01],
							 	'吴忠市' :　[106.21　,37.99],
							 	'石嘴山市' :　[106.39　,39.04],
							   '银川市' :　[106.27　,38.47],
								'中卫市' :　[105.18　,37.51]}},
							    {'吉林' : {
							 	'白城市'　:　[122.82,　45.63],
							 	'松原市' : [124.82, 45.13],
							 	'长春市'　:　[125.35,　43.88],
							 	'四平市'　:　[124.37,　43.17],
							   '吉林市'　:　[126.57,　43.87],
							   '辽源市'　:　[125.15, 42.97],
							   '通化市'　:　[125.92,　41.49],
							   '白山市' : [126.42, 41.93],
								'延边朝鲜族自治州'　:　[129.52, 42.93]}},
							    {'湖南' :{
							 	'常德市' :　[111.69,29.05],
							 	'张家界市' : [110.47,29.13],
							 	'岳阳市' :　[113.09　,29.37],
								'长沙市' :　[113.00,　 28.21],
								'益阳市' :　[112.33,　28.60],
								'娄底市' :　[111.96,　27.71],
								'湘潭市' :　[112.91,　27.87],
								'衡阳市' :　[112.61, 26.89],
								'株洲市' :　[113.16　,27.83],
								'郴州市' :　　[113 ,25.79],
								'永州市'　:　[111.63　,26.22],
								'邵阳市'　:　[111.50,　 27.22],
								'怀化市'　:　[109.95,　27.52],
								'湘西土家族苗族自治州' : [109.73, 28.32]}},
							    {'河北' :{
								'石家庄市' : [114.48,38.03],
								'邯郸市' : [114.47,36.6],
								'邢台市' : [114.48,37.05],
								'廊坊市' : [116.70,39.52],
								'承德市' : [117.51,40.57],
								'唐山市' : [118.02,39.63],
								'秦皇岛市' : [119.57,39.95],
								'张家口市' : [114.87,40.82],
								'保定市' : [115.48,38.85],
								'沧州市' : [116.83 ,38.33],
								'衡水市' : [115.72,37.72]}},
							    {'福建' :{
							 	'南平市'　:　[118.16, 26.65],
							 	'宁德市'　:　[119.52, 26.65],
							 	'三明市'　:　[117.61,　 26.23],
								'福州市'　:　[119.30,　 26.08],
							   '莆田市'　:　[119,  25.44],
							   '泉州市'　:　[118.58,　 24.93],
							   '厦门市'　:　[118.10, 24.46],
							   '漳州市'　:　[117.35,　24.52],
							   '龙岩市'　:　[117.01,　25.12]}},
							    {'海南' :{
								'海口市'　:　[110.35,　20.02],
								'文昌市'　:　[110.72,　 19.61],
								'临高县'　:　[109.69,　19.91],
								'澄迈县'　:　[110,　19.75],
								'定安县'　:　[110.31,　19.68],
								'琼海市'　:　[110.46,　19.25],
								'屯昌县'　:　[110.10,　19.36],
								'儋州市' : [109.57, 19.52],
								'万宁市'　:　[110.39,　18.80],
								'东方市'　:　[108.64,　19.09],
								'五指山市' : [109.52, 18.78],
								'昌江黎族自治县' : [109.05, 19.25],
								'乐东黎族自治县' : [109.17, 18.75],
								'保亭黎族苗族自治县' : [109.70, 18.63],
								'琼中黎族苗族自治县' : [109.83, 19.03],
								'白沙黎族自治县' :　[109.44,　19.23],
								'陵水黎族自治县' : [110.02, 18.48],
								'三亚市' : [109.50, 18.25]}},
								{'香港':{'香港':[114.196472,22.315521]}},
								{'台湾':{'台湾':[121.504174,25.07989]}},
								{'澳门':{'澳门':[113.554932,22.191937]}}
								];
					var cityMap = {
								"北京市": "110100",
							    "天津市": "120100",
							    "上海市": "310100",
							    "重庆市": "500100",
							    
							    "崇明县": "310200",            
							    "湖北省直辖县市": "429000",       
							    "铜仁市": "522200",           
							    "毕节市": "522400",           
							    
							    "石家庄市": "130100",
							    "唐山市": "130200",
							    "秦皇岛市": "130300",
							    "邯郸市": "130400",
							    "邢台市": "130500",
							    "保定市": "130600",
							    "张家口市": "130700",
							    "承德市": "130800",
							    "沧州市": "130900",
							    "廊坊市": "131000",
							    "衡水市": "131100",
							    "太原市": "140100",
							    "大同市": "140200",
							    "阳泉市": "140300",
							    "长治市": "140400",
							    "晋城市": "140500",
							    "朔州市": "140600",
							    "晋中市": "140700",
							    "运城市": "140800",
							    "忻州市": "140900",
							    "临汾市": "141000",
							    "吕梁市": "141100",
							    "呼和浩特市": "150100",
							    "包头市": "150200",
							    "乌海市": "150300",
							    "赤峰市": "150400",
							    "通辽市": "150500",
							    "鄂尔多斯市": "150600",
							    "呼伦贝尔市": "150700",
							    "巴彦淖尔市": "150800",
							    "乌兰察布市": "150900",
							    "兴安盟": "152200",
							    "锡林郭勒盟": "152500",
							    "阿拉善盟": "152900",
							    "沈阳市": "210100",
							    "大连市": "210200",
							    "鞍山市": "210300",
							    "抚顺市": "210400",
							    "本溪市": "210500",
							    "丹东市": "210600",
							    "锦州市": "210700",
							    "营口市": "210800",
							    "阜新市": "210900",
							    "辽阳市": "211000",
							    "盘锦市": "211100",
							    "铁岭市": "211200",
							    "朝阳市": "211300",
							    "葫芦岛市": "211400",
							    "长春市": "220100",
							    "吉林市": "220200",
							    "四平市": "220300",
							    "辽源市": "220400",
							    "通化市": "220500",
							    "白山市": "220600",
							    "松原市": "220700",
							    "白城市": "220800",
							    "延边朝鲜族自治州": "222400",
							    "哈尔滨市": "230100",
							    "齐齐哈尔市": "230200",
							    "鸡西市": "230300",
							    "鹤岗市": "230400",
							    "双鸭山市": "230500",
							    "大庆市": "230600",
							    "伊春市": "230700",
							    "佳木斯市": "230800",
							    "七台河市": "230900",
							    "牡丹江市": "231000",
							    "黑河市": "231100",
							    "绥化市": "231200",
							    "大兴安岭地区": "232700",
							    "南京市": "320100",
							    "无锡市": "320200",
							    "徐州市": "320300",
							    "常州市": "320400",
							    "苏州市": "320500",
							    "南通市": "320600",
							    "连云港市": "320700",
							    "淮安市": "320800",
							    "盐城市": "320900",
							    "扬州市": "321000",
							    "镇江市": "321100",
							    "泰州市": "321200",
							    "宿迁市": "321300",
							    "杭州市": "330100",
							    "宁波市": "330200",
							    "温州市": "330300",
							    "嘉兴市": "330400",
							    "湖州市": "330500",
							    "绍兴市": "330600",
							    "金华市": "330700",
							    "衢州市": "330800",
							    "舟山市": "330900",
							    "台州市": "331000",
							    "丽水市": "331100",
							    "合肥市": "340100",
							    "芜湖市": "340200",
							    "蚌埠市": "340300",
							    "淮南市": "340400",
							    "马鞍山市": "340500",
							    "淮北市": "340600",
							    "铜陵市": "340700",
							    "安庆市": "340800",
							    "黄山市": "341000",
							    "滁州市": "341100",
							    "阜阳市": "341200",
							    "宿州市": "341300",
							    "六安市": "341500",
							    "亳州市": "341600",
							    "池州市": "341700",
							    "宣城市": "341800",
							    "福州市": "350100",
							    "厦门市": "350200",
							    "莆田市": "350300",
							    "三明市": "350400",
							    "泉州市": "350500",
							    "漳州市": "350600",
							    "南平市": "350700",
							    "龙岩市": "350800",
							    "宁德市": "350900",
							    "南昌市": "360100",
							    "景德镇市": "360200",
							    "萍乡市": "360300",
							    "九江市": "360400",
							    "新余市": "360500",
							    "鹰潭市": "360600",
							    "赣州市": "360700",
							    "吉安市": "360800",
							    "宜春市": "360900",
							    "抚州市": "361000",
							    "上饶市": "361100",
							    "济南市": "370100",
							    "青岛市": "370200",
							    "淄博市": "370300",
							    "枣庄市": "370400",
							    "东营市": "370500",
							    "烟台市": "370600",
							    "潍坊市": "370700",
							    "济宁市": "370800",
							    "泰安市": "370900",
							    "威海市": "371000",
							    "日照市": "371100",
							    "莱芜市": "371200",
							    "临沂市": "371300",
							    "德州市": "371400",
							    "聊城市": "371500",
							    "滨州市": "371600",
							    "菏泽市": "371700",
							    "郑州市": "410100",
							    "开封市": "410200",
							    "洛阳市": "410300",
							    "平顶山市": "410400",
							    "安阳市": "410500",
							    "鹤壁市": "410600",
							    "新乡市": "410700",
							    "焦作市": "410800",
							    "濮阳市": "410900",
							    "许昌市": "411000",
							    "漯河市": "411100",
							    "三门峡市": "411200",
							    "南阳市": "411300",
							    "商丘市": "411400",
							    "信阳市": "411500",
							    "周口市": "411600",
							    "驻马店市": "411700",
							    "省直辖县级行政区划": "469000",
							    "武汉市": "420100",
							    "黄石市": "420200",
							    "十堰市": "420300",
							    "宜昌市": "420500",
							    "襄阳市": "420600",
							    "鄂州市": "420700",
							    "荆门市": "420800",
							    "孝感市": "420900",
							    "荆州市": "421000",
							    "黄冈市": "421100",
							    "咸宁市": "421200",
							    "随州市": "421300",
							    "恩施土家族苗族自治州": "422800",
							    "长沙市": "430100",
							    "株洲市": "430200",
							    "湘潭市": "430300",
							    "衡阳市": "430400",
							    "邵阳市": "430500",
							    "岳阳市": "430600",
							    "常德市": "430700",
							    "张家界市": "430800",
							    "益阳市": "430900",
							    "郴州市": "431000",
							    "永州市": "431100",
							    "怀化市": "431200",
							    "娄底市": "431300",
							    "湘西土家族苗族自治州": "433100",
							    "广州市": "440100",
							    "韶关市": "440200",
							    "深圳市": "440300",
							    "珠海市": "440400",
							    "汕头市": "440500",
							    "佛山市": "440600",
							    "江门市": "440700",
							    "湛江市": "440800",
							    "茂名市": "440900",
							    "肇庆市": "441200",
							    "惠州市": "441300",
							    "梅州市": "441400",
							    "汕尾市": "441500",
							    "河源市": "441600",
							    "阳江市": "441700",
							    "清远市": "441800",
							    "东莞市": "441900",
							    "中山市": "442000",
							    "潮州市": "445100",
							    "揭阳市": "445200",
							    "云浮市": "445300",
							    "南宁市": "450100",
							    "柳州市": "450200",
							    "桂林市": "450300",
							    "梧州市": "450400",
							    "北海市": "450500",
							    "防城港市": "450600",
							    "钦州市": "450700",
							    "贵港市": "450800",
							    "玉林市": "450900",
							    "百色市": "451000",
							    "贺州市": "451100",
							    "河池市": "451200",
							    "来宾市": "451300",
							    "崇左市": "451400",
							    "海口市": "460100",
							    "三亚市": "460200",
							    "三沙市": "460300",
							    "成都市": "510100",
							    "自贡市": "510300",
							    "攀枝花市": "510400",
							    "泸州市": "510500",
							    "德阳市": "510600",
							    "绵阳市": "510700",
							    "广元市": "510800",
							    "遂宁市": "510900",
							    "内江市": "511000",
							    "乐山市": "511100",
							    "南充市": "511300",
							    "眉山市": "511400",
							    "宜宾市": "511500",
							    "广安市": "511600",
							    "达州市": "511700",
							    "雅安市": "511800",
							    "巴中市": "511900",
							    "资阳市": "512000",
							    "阿坝藏族羌族自治州": "513200",
							    "甘孜藏族自治州": "513300",
							    "凉山彝族自治州": "513400",
							    "贵阳市": "520100",
							    "六盘水市": "520200",
							    "遵义市": "520300",
							    "安顺市": "520400",
							    "黔西南布依族苗族自治州": "522300",
							    "黔东南苗族侗族自治州": "522600",
							    "黔南布依族苗族自治州": "522700",
							    "昆明市": "530100",
							    "曲靖市": "530300",
							    "玉溪市": "530400",
							    "保山市": "530500",
							    "昭通市": "530600",
							    "丽江市": "530700",
							    "普洱市": "530800",
							    "临沧市": "530900",
							    "楚雄彝族自治州": "532300",
							    "红河哈尼族彝族自治州": "532500",
							    "文山壮族苗族自治州": "532600",
							    "西双版纳傣族自治州": "532800",
							    "大理白族自治州": "532900",
							    "德宏傣族景颇族自治州": "533100",
							    "怒江傈僳族自治州": "533300",
							    "迪庆藏族自治州": "533400",
							    "拉萨市": "540100",
							    "昌都地区": "542100",
							    "山南地区": "542200",
							    "日喀则地区": "542300",
							    "那曲地区": "542400",
							    "阿里地区": "542500",
							    "林芝地区": "542600",
							    "西安市": "610100",
							    "铜川市": "610200",
							    "宝鸡市": "610300",
							    "咸阳市": "610400",
							    "渭南市": "610500",
							    "延安市": "610600",
							    "汉中市": "610700",
							    "榆林市": "610800",
							    "安康市": "610900",
							    "商洛市": "611000",
							    "兰州市": "620100",
							    "嘉峪关市": "620200",
							    "金昌市": "620300",
							    "白银市": "620400",
							    "天水市": "620500",
							    "武威市": "620600",
							    "张掖市": "620700",
							    "平凉市": "620800",
							    "酒泉市": "620900",
							    "庆阳市": "621000",
							    "定西市": "621100",
							    "陇南市": "621200",
							    "临夏回族自治州": "622900",
							    "甘南藏族自治州": "623000",
							    "西宁市": "630100",
							    "海东地区": "632100",
							    "海北藏族自治州": "632200",
							    "黄南藏族自治州": "632300",
							    "海南藏族自治州": "632500",
							    "果洛藏族自治州": "632600",
							    "玉树藏族自治州": "632700",
							    "海西蒙古族藏族自治州": "632800",
							    "银川市": "640100",
							    "石嘴山市": "640200",
							    "吴忠市": "640300",
							    "固原市": "640400",
							    "中卫市": "640500",
							    "乌鲁木齐市": "650100",
							    "克拉玛依市": "650200",
							    "吐鲁番市": "652100",
							    "哈密地区": "652200",
							    "昌吉回族自治州": "652300",
							    "博尔塔拉蒙古自治州": "652700",
							    "巴音郭楞蒙古自治州": "652800",
							    "阿克苏地区": "652900",
							    "克孜勒苏柯尔克孜自治州": "653000",
							    "喀什地区": "653100",
							    "和田地区": "653200",
							    "伊犁哈萨克自治州": "654000",
							    "塔城地区": "654200",
							    "阿勒泰地区": "654300",
							    "自治区直辖县级行政区划": "659000",
							    "台湾省": "710000",
							    "香港特别行政区": "810100",
							    "澳门特别行政区": "820000"
							};
		 	var newoption=$.extend( true,  {},optes,paroption)
			newoption.series[0].geoCoord={
          	                   '上海' : [121.4648, 31.2891],
							 	'崇明县' : [121.40, 31.73],
							 	'宝山区' : [121.48, 31.41],
							 	'浦东新区' : [121.53 ,31.22],
							 	'嘉定区' : [121.24, 31.40],
							 	'杨浦区' : [121.52, 31.27],
							 	'徐汇区' : [121.43 ,31.18],
							 	'闵行区' :　[121.38, 31.12],
							 	'奉贤区' :　[121.47, 30.92],
							 	'南汇区' : [121.76, 31.05],
							 	'青浦区' : [121.10, 31.15],
							 	'金山区' : [121.16, 30.89],
							 	'松江区' : [121.24, 31.00],
							 	'卢湾区' : [121.47, 31.22],
								'长宁区' : [121.42,31.22],
								'静安区' : [121.45 ,31.23],
								'黄浦区' : [121.48, 31.23],
								'普陀区' : [121.40 ,31.25],
								'闸北区' : [121.45 ,31.25],
								'虹口区' : [121.50 ,31.27],
							   '新疆' : [87.9236, 43.5883],
								'乌鲁木齐市' : [87.68,　 43.77],
								'吐鲁番地区' : [89.19,　 42.91],
								'哈密地区' : [93.44,　42.78],
								'昌吉回族自治州' : [87.31,　44.05],
								'五家渠市' : [87.54, 44.16],
								'石河子市' : [85.94,　44.27],
								'阿勒泰地区' : [88.13, 47.85],
								'塔城地区' : [82.96, 46.74],
								'克拉玛依市' : [84.77,　45.59],
								'博尔塔拉蒙古自治州' : [82.07, 44.90],
								'伊犁哈萨克自治州' : [81.32, 43.92],
								'阿克苏地区' : [80.29, 41.15],
								'阿拉尔市' : [81.28, 40.54],	
								'图木舒克市' : [79.06, 39.86],
								'喀什地区' : [75.98, 39.47],
								'和田地区' : [79.94,　37.12],
								'克孜勒苏柯尔克孜自治州' : [76.172825, 39.713431],
								'博尔塔拉蒙古自治州' : [82.07, 44.9],
						       '甘肃' : [103.5901, 36.3043],
								'庆阳市' : [107.88　,36.03],
								'平凉市' : [106.68　,35.51],
								'天水市' : [105.69　,34.60],
								'陇南市' : [104.92 ,33.40],
								'定西市' : [104.57, 35.57],
								'白银市' : [104.18, 36.55],
								'兰州市' : [103.73　, 36.03],
								'武威市' : [102.61,　37.94],
								'金昌市' : [102.18, 38.50],
								'张掖市' : [100.46　,38.93],
								'嘉峪关市' : [98.27 ,39.80],
								'酒泉市' : [98.50　,39.71],
								'临夏回族自治州' : [103.22 , 35.62],
							   '甘南藏族自治州' : [102.92 ,34.98],
							   '北京' : [116.4551, 40.2539],
							 	'怀柔区' : [116.62, 40.32],
							 	'延庆县' : [115.97, 40.47],
							 	'密云县' : [116.85, 40.37],
								'平谷区' : [117.10, 40.13],
							   '顺义区' : [116.65, 40.13],
							   '昌平区' : [116.20, 40.22],
							   '海淀区' : [116.30, 39.95],
							   '朝阳区' : [116.43 ,39.92],
							   '通州区' : [116.65 ,39.92],
							   '大兴区' : [116.33, 39.73],
							   '房山区' : [115.98, 39.72],
							   '门头沟区' : [116.10, 39.93],
							   '石景山区' : [116.22, 39.90],
							   '丰台区' : [116.28, 39.85],
							   '崇文区' : [116.43, 39.88],
							   '宣武区' : [116.35, 39.87],
							   '东城区' : [116.42, 39.93],
							   '西城区' : [116.37, 39.92],
							   '中央' : [116.4440, 40.2440],
							   '天津' : [117.4219, 39.4189],
							 	'蓟县' : [117.40, 40.05],
							 	'宝坻区' : [117.30, 39.75],
							 	'宁河县' : [117.83, 39.33],
							 	'武清区' : [117.05, 39.40],
								'汉沽区' : [117.80 ,39.25],
								'北辰区' : [117.13, 39.22],
								'塘沽区' :　[117.65, 39.02],
								'津南区' : [117.38, 38.98],
								'大港区' : [117.45, 38.83],
								'西青区' : [117.00, 39.13],
								'静海县' : [116.92, 38.93],
								'河西区' : [117.22, 39.12],
								'东丽区' : [117.30, 39.08],
								'和平区' : [117.20 ,39.12],
								'红桥区' :　[117.15 ,39.17],
								'河北区' : [117.18 ,39.15],
								'南开区' : [117.15 ,39.13],
								'河东区' : [117.22 ,39.12],
							   '北海市' : [109.314, 21.6211],
							   '江苏' : [118.8062, 31.9208],
							 	'苏州市'　:　[120.62,　31.32],
							 	'无锡市'　:　[120.29,　31.59],
							 	'南通市'　:　[120.86,　32.01],
							 	'常州市'　:　[119.95,　31.79],
							 	'镇江市'　:　[119.44,　32.20],
							 	'南京市'　:　[118.78,　32.04],
							 	'泰州市'　:　[119.90,　32.49],
							 	'扬州市'　:　[119.42,　32.39],
							 	'盐城市'　:　[120.13,　33.38],
							 	'淮安市'　:　[119.15,　33.50],
							 	'连云港市' :　[119.16,　34.59],
							 	'宿迁市'　:　[118.30　,33.96],
							 	'徐州市'　:　[117.20, 34.26],
							   '广西' : [108.479, 23.1152],
								'桂林市'　:　[110.28, 25.29],
							   '贺州市'　:　[111.54, 24.44],
							   '柳州市'　:　[109.40, 24.33],
								'梧州市'　: [111.34,　23.51],
								'来宾市'　:　[109.24,  23.76],
								'贵港市' : [109.60, 23.10],
								'河池市'　:　[108.06,　24.70],
								'南宁市'　:　[108.33, 22.84],
								'玉林市'　:　[110.14,　22.64],
								'百色市'　:　[106.62,　23.91],
							   '钦州市'　:　[108.61,　21.96],
							   '崇左市'　:　[107.37,　22.42],
							   '防城港市' : [108.35, 21.70],
							   '江西' : [116.0046, 28.6633],
								'九江市'　:　[115.97,　29.71],
								'景德镇市'　: [117.22,　29.30],
								'南昌市'　:　[115.89,　28.68],
								'宜春市'　:　[114.38,　27.81],
								'上饶市'　:　[117.97, 28.47],
								'鹰潭市' : [117.07, 28.27],
								'新余市'　:　[114.92,　27.81],
								'抚州市'　:　[116.34,　28.00],
								'赣州市'　:　[114.92,　25.85],
								'吉安市'　:　[114.97, 27.12],
								'萍乡市'　:　[113.85,　27.60],
							   '安徽' : [117.29, 32.0581],
								'淮北市'　:　[116.77,　33.97],
								'宿州市'　:　[116.97,　 33.63],
								'亳州市' : [115.78 ,33.85],
								'蚌埠市'　:　[117.34,　32.93],
							   '阜阳市'　:　[115.81,　32.89],
							   '淮南市'　:　[116.98, 32.62],
							   '滁州市'　:　[118.31,　 32.33],
							   '合肥市'　:　[117.27,　31.86],
							   '六安市'　:　[116.49,　 31.73],
								'巢湖市'　:　[117.87,　 31.62],
								'马鞍山市'　: [118.48,  31.56],
								'芜湖市'　:　[118.38,　 31.33],
								'铜陵市'　:　[117.82,　 30.93],
								'安庆市'　:　[117.03,　30.52],
								'池州市' : [117.48 ,30.67],
								'宣城市'　:　[118.73,  31.95],
								'黄山市' : [118.33, 29.72],
							   '内蒙古' : [111.4124, 40.4901],
							 	'呼伦贝尔市' : [119.77 ,49.22],
							 	'兴安盟' : [122.05, 46.08],
							 	'通辽市'　:　[122.28,　 43.63],
							 	'赤峰市'　:　[118.87,　42.28],
							 	'锡林郭勒盟' : [116.07 ,43.95],
							 	'乌兰察布市' : [113, 41.09],
								'呼和浩特市'　: [111.65,　40.82],
								'包头市'　:　　[110, 40.58],
								'巴彦淖尔市' : [107.42, 40.75],
								'鄂尔多斯市' : [109.80 ,39.62],
								'乌海市' :　[106.82,　39.67],
								'阿拉善盟' : [105.67, 38.83],
							   '黑龙江' : [127.9688, 45.368],
							 	'佳木斯市'　: [130.35,　46.83],
							 	'双鸭山市'　: [131.17,　46.65],
							 	'鸡西市'　: [130.97,　45.30],
							 	'七台河市'　: [130.83, 45.82],
							 	'牡丹江市'　: [129.58,　44.60],
							 	'鹤岗市'　:　[130.30,　47.33],
								'哈尔滨市' :　[126.63,　45.75],
								'伊春市'　:　[128.92,　47.73],
								'绥化市'　:　[127,　 46.63],
								'齐齐哈尔市' : [123.97,　47.33],
								'大庆市'　:　[125.03,　 46.58],
								'黑河市' 　:　[127.53, 50.22],
								'大兴安岭地区' : [124.12, 50.42],
							   '山西' : [112.3352, 37.9413],
							 	'大同市'　:　[113.30, 40.12],
							 	'朔州市' : [112.43, 39.33],
							 	'忻州市' : [112.73, 38.42],
							 	'阳泉市'　:　[113.57,　37.85],
							 	'太原市'　:　[112.53,　37.87],
							   '吕梁市' : [111.13, 37.52],
							   '晋中市' : [112.75, 37.68],
							   '长治市'　:　[113.08,　36.18],
								'临汾市'　:　[111.50, 36.08],
								'晋城市'　:　[112.83,　35.52],
							   '运城市'　:　[110.97, 35.03],
							   '广东' : [113.5107, 23.2196],
							 	'汕头市'　:　[116.69,　23.39],
							 	'潮州市'　:　[116.63,　23.68],
							   '梅州市'　:　[116.10,　24.55],
							   '揭阳市' : [116.37, 23.55],
							   '汕尾市' : [115.37, 22.78],
								'河源市'　:　[114.68,　23.73],
								'惠州市' :　[114.40,　23.09],
								'韶关市' :　[113.62,　24.84],
								'清远市' :　[113.01,　23.70],
								'广州市'　:　[113.23,　 23.16],
								'深圳市'　:　[114.07,　22.62],
								'东莞市'　:　[113.75,　 23.04],
								'珠海市'　:　[113.52,　22.30], 
								'中山市'　:　[113.38,　22.52],
								'佛山市' :　[113.11,　23.05],
								'肇庆市' :　[112.44,　23.05],
								'江门市'　:　[113.06,　22.61],
								'云浮市'　:　[112.02,　22.93],
								'茂名市' :　[110.88,　21.68],
								'阳江市' :　[111.95,　21.85],
								'湛江市' : [110.35, 21.27],
							   '四川' : [103.9526, 30.7617],
							    '广元市' : [105.59,32.52],
								'巴中市'　: [106.73,31.86],
								'绵阳市' : [104.73,31.48],
								'达州市' : [107.50,31.22],
								'南充市' : [106.08,30.78],
								'广安市'　:　[106.61,30.48],
								'遂宁市'　:　[105.58,30.52],
								'德阳市' : [104.37,31.13],
								'成都市'　:　[104.06, 30.67],
								'资阳市'　:　[104.60,30.19],
								'眉山市' : [103.50,30.04],
								'内江市'　:　[105.04,29.59],
								'自贡市' : [104.46,29.23],
								'雅安市'　:　[102.97,29.97],
								'乐山市' : [103.77,29.57],
								'宜宾市' : [104.56,29.77],
								'泸州市'　:　[105.39,28.91],
								'攀枝花市' : [101.72,26.58],
								'凉山彝族自治州' : [102.27,27.90],
								'甘孜藏族自治州' : [101.97,30.05],
								'阿坝藏族羌族自治州' : [102.22,31.90],
							    '西藏' : [91.1865, 30.1465],
								'拉萨市' :　[91.11,29.97],
								'林芝地区' : [94.37 ,29.68],
								'昌都地区' : [97.18, 31.13],
								'山南地区' : [91.77 ,29.23],
								'那曲地区' : [92.07, 31.48],
								'日喀则地区' : [88.88, 29.27],
								'阿里地区' : [80.10 ,32.50],
							   '云南' : [102.9199, 25.4663],
								'昭通市' :　[103.70,29.32],
								'曲靖市'　: [103.79,25.51],
							   '昆明市' :　[102.73, 25.04],
								'玉溪市' :　[102.52,24.35],
								'普洱市' :　[101.03, 23.07],
								'临沧市' : [100.08,23.88],
								'保山市' : [99.17,25.12],
								'丽江市'　: [100.25,26.86],
								'迪庆藏族自治州' : [99.70,27.83],
								'怒江傈僳族自治州' : [98.85,25.85],
								'大理白族自治州'	: [100.19,25.69],
								'德宏傣族景颇族自治州' : [98.58,24.43],
								'西双版纳傣族自治州' : [100.80,22.02],
								'楚雄彝族自治州' : [101.54,25.01],
								'红河哈尼族彝族自治州' : [103.40,23.37],
							 	'文山壮族苗族自治州' : [104.25,23.37],
							   '浙江' : [119.5313, 29.8773],
							 	'温州市' : [120.65, 28.01],
							 	'丽水市' : [119.92, 28.45],
							 	'台州市' : [121.43, 28.68],
							 	'金华市' : [119.64, 29.12],
								'衢州市' : [118.88, 28.97],
								'宁波市' : [121.56, 29.86],
								'绍兴市' : [120.58, 30.01],
								'杭州市' : [120.19, 30.26],
								'嘉兴市' : [120.76, 30.77],
								'舟山市' : [122.2, 30],
								'湖州市' : [120.10, 30.86],
							   '湖北' : [114.3896, 30.6628],
								'黄石市' :　[115.09,30.20],
								'咸宁市'　:　[114.28,　29.87],
								'黄冈市'　:　[114.87,　30.44],
								'鄂州市' : [114.88, 30.40],
								'武汉市'　:　[114.31,30.52],
								'孝感市'　:　[113.91,31.92],
							   '仙桃市' : [113.45 ,30.37],
							   '荆州市' : [112.23, 30.33],
							   '天门市' : [113.10 ,60.39],
							   '潜江市' : [112.89, 30.42],
							   '荆门市'　:　[112.19,　31.02],
							   '随州市' : [113.37, 31.72],
							   '襄樊市'　:　[112.14　,30.02],
							   '宜昌市'　: [111.30,　30.70],
							   '十堰市'　:　[110.79,　32.65],
							   '神农架林区' : [110.64, 31.75],
							    '恩施土家族苗族自治州' : [109.48, 30.27],
							   '辽宁' : [123.1238, 42.1216],
								'沈阳市' : [123.38,41.8],
								'大连市' : [121.62,38.92],	
								'抚顺市' : [123.97,41.97],
								'本溪市' : [123.73,41.3],
								'锦州市' : [121.15,41.13],
								'丹东市' : [124.37,40.13],
								'阜新市' : [121.65,42],
								'营口市' : [122.18,40.65],
								'辽阳市' : [123.17,41.28],
								'铁岭市' : [123.85,42.32],
								'朝阳市' : [120.42,41.58],
								'鞍山市' : [122.85,41.12],
								'葫芦岛市' : [120.83,40.77],
								'盘锦市' : [122.03,41.07],
							   '山东' : [117.1582, 36.8701],
								'泰安市' : [117.13,36.18],
								'济宁市' : [116.59,35.38],
								'荷泽市' : [115.43,35.24],
								'聊城市' : [115.97,36.45],
								'枣庄市' : [117.57,34.86],
								'莱芜市' : [117.67,36.19],
								'德州市' : [116.29,37.45],
								'济南市' : [117,36.65],
								'滨州市' : [118.03,37.36],
								'临沂市' : [118.35,35.05],	
								'淄博市' : [118.05,36.78],
								'日照市' : [119.46,35.42],	
								'东营市' : [118.49,37.46],
								'潍坊市' : [119.1,36.62],
								'烟台市' : [121.39,37.52],
								'威海市' : [122.1,37.5],
								'青岛市' : [120.33,36.07],
							   '青海' : [101.4038,36.8207],
								'西宁市' : [101.74,36.56],
								'海东地区' : [102.12,36.50],
								'海北藏族自治州' : [100.90,36.97],
								'海南藏族自治州' : [100.62,36.28],
								'黄南藏族自治州' : [102.02,35.52],
								'海西蒙古族藏族自治州' : [97.37,37.37],
								'果洛藏族自治州' : [100.23,34.48],
								'玉树藏族自治州' : [97,43],
							   '陕西' : [109.1162, 34.2004],
								'西安市' : [108.95,34.27],
								'宝鸡市' : [107.15,34.38],
								'铜川	市' : [109.11,35.09],
								'榆林市' : [109.77,38.3],
								'延安市' : [109.47,36.6],
								'咸阳市' : [108.72,34.36],
								'渭南市' : [109.5,34.52],
								'安康	市' : [109.02,32.7],
								'汉中市' : [108.04,33.07],
								'商洛市' : [109.93,33.87],
							   '贵州' : [106.6992, 26.7682],
								'贵阳市' : [106.71,26.57],
								'六盘水市' : [104.82,26.58],
								'遵义市' : [106.9,27.7],
								'安顺市' : [105.55,26.14], 
								'铜仁地区' : [109.21,27.73],
								'毕节地区' : [105.29,27.32],
								'黔西南布依族苗族自治州' : [106.04,27.03],
								'黔南布依族苗族自治州' : [107.52,26.27],
								'黔东南苗族侗族自治州' : [107.97,26.58],
							    '河南' : [113.4668, 34.6234],
								'郑州市' : [113.65,34.76],
								'开封市' : [114.35,34.79],
								'焦作市' : [113.21,35.24],
								'鹤壁市' : [114.17,35.9],
								'安阳市' : [114.35,36.1],		
								'濮阳市' : [114.98,35.71],
								'新乡市' : [113.85,35.31],	
								'商丘市' : [115.65,34.44],
								'周口市' : [114.63,33.63],
								'南阳市' : [112.53,33.01],	
								'平顶山市' : [113.29,33.75],
								'洛阳市' : [112.44,34.7],
								'信阳市' : [114.08,32.13],	
								'许昌市' : [113.81,34.02],
								'漯河市' : [114.02,33.56],
								'驻马店市' : [114.02,32.98],
								'三门峡市' : [111.19,34.76],
							   '重庆' : [107.7539, 30.1904],
								'城口县' : [108.67, 31.95],
								'巫溪县' : [109.63, 31.40],
								'巫山县' : [109.88, 31.08],
								'开县' : [108.42 ,31.18],
								'云阳县' : [108.67, 30.95],
								'奉节县' : [109.47, 31.02],
								'万州区' : [108.40 ,0.82],
								'梁平县' : [107.80, 30.68],
								'忠县' : [108.02, 30.30],
								'石柱土家族自治县' : [108.12,30.00],
								'垫江县' : [107.35, 30.33],
								'丰都县' : [107.73, 29.87],
								'长寿区' : [106.64, 29.01],
								'涪陵区' : [107.40 ,29.72],
								'渝北区' : [106.63 ,29.72],
								'北碚区' : [106.40 ,29.80],
								'合川区' : [106.28, 29.26],
								'潼南县' : [106.22, 30.03],
								'铜梁县' : [105.80, 30.16],
								'大足县' : [105.59, 29.40],
								'双桥区' : [105.78, 29.48],
								'荣昌县' : [106.21, 29.62],
								'永川区' : [105.71, 29.75],
								'江津区' : [106.25, 29.26],
								'綦江县' : [106.56, 29.41],
								'万盛区' : [105.91, 29.38],
								'南川区' : [107.11,29.16],
								'武隆县' : [107.75 ,29.33],
								'彭水苗族土家族自治县' : [108.17, 29.30],
								'黔江区' : [108.77, 29.53],
								'酉阳土家族苗族自治县' : [108.77,28.85],
								'秀山土家族苗族自治县' : [108.98 ,28.45],
								'江北区' : [106.57, 29.60],
								'沙坪坝区' : [106.45, 29.53],
								'壁山县' : [106.03, 29.86],
								'渝中区' : [106.57, 29.55],
								'南岸区' : [106.57 ,29.52],
								'九龙坡区' : [106.50, 29.50],
								'大渡口区' : [106.48,29.48],
								'巴南区' : [106.52, 29.38],
							   '宁夏' : [106.3586, 38.1775],
							 	'固原市' :　[106.28,36.01],
							 	'吴忠市' :　[106.21　,37.99],
							 	'石嘴山市' :　[106.39　,39.04],
							   '银川市' :　[106.27　,38.47],
								'中卫市' :　[105.18　,37.51],
							   '吉林' : [125.8154, 44.2584],
							 	'白城市'　:　[122.82,　45.63],
							 	'松原市' : [124.82, 45.13],
							 	'长春市'　:　[125.35,　43.88],
							 	'四平市'　:　[124.37,　43.17],
							   '吉林市'　:　[126.57,　43.87],
							   '辽源市'　:　[125.15, 42.97],
							   '通化市'　:　[125.92,　41.49],
							   '白山市' : [126.42, 41.93],
								'延边朝鲜族自治州'　:　[129.52, 42.93],
							   '湖南' : [113.0823, 28.2568],
							 	'常德市' :　[111.69,29.05],
							 	'张家界市' : [110.47,29.13],
							 	'岳阳市' :　[113.09　,29.37],
								'长沙市' :　[113.00,　 28.21],
								'益阳市' :　[112.33,　28.60],
								'娄底市' :　[111.96,　27.71],
								'湘潭市' :　[112.91,　27.87],
								'衡阳市' :　[112.61, 26.89],
								'株洲市' :　[113.16　,27.83],
								'郴州市' :　　[113 ,25.79],
								'永州市'　:　[111.63　,26.22],
								'邵阳市'　:　[111.50,　 27.22],
								'怀化市'　:　[109.95,　27.52],
								'湘西土家族苗族自治州' : [109.73, 28.32],
							   '河北' : [114.4995, 38.1006],
								'石家庄市' : [114.48,38.03],
								'邯郸市' : [114.47,36.6],
								'邢台市' : [114.48,37.05],
								'廊坊市' : [116.70,39.52],
								'承德市' : [117.51,40.57],
								'唐山市' : [118.02,39.63],
								'秦皇岛市' : [119.57,39.95],
								'张家口市' : [114.87,40.82],
								'保定市' : [115.48,38.85],
								'沧州市' : [116.83 ,38.33],
								'衡水市' : [115.72,37.72],
							   '福建' : [119.4543, 25.9222],
							 	'南平市'　:　[118.16, 26.65],
							 	'宁德市'　:　[119.52, 26.65],
							 	'三明市'　:　[117.61,　 26.23],
								'福州市'　:　[119.30,　 26.08],
							   '莆田市'　:　[119,  25.44],
							   '泉州市'　:　[118.58,　 24.93],
							   '厦门市'　:　[118.10, 24.46],
							   '漳州市'　:　[117.35,　24.52],
							   '龙岩市'　:　[117.01,　25.12],
							   '海南' : [110.3893, 19.8516],
								'海口市'　:　[110.35,　20.02],
								'文昌市'　:　[110.72,　 19.61],
								'临高县'　:　[109.69,　19.91],
								'澄迈县'　:　[110,　19.75],
								'定安县'　:　[110.31,　19.68],
								'琼海市'　:　[110.46,　19.25],
								'屯昌县'　:　[110.10,　19.36],
								'儋州市' : [109.57, 19.52],
								'万宁市'　:　[110.39,　18.80],
								'东方市'　:　[108.64,　19.09],
								'五指山市' : [109.52, 18.78],
								'昌江黎族自治县' : [109.05, 19.25],
								'乐东黎族自治县' : [109.17, 18.75],
								'保亭黎族苗族自治县' : [109.70, 18.63],
								'琼中黎族苗族自治县' : [109.83, 19.03],
								'白沙黎族自治县' :　[109.44,　19.23],
								'陵水黎族自治县' : [110.02, 18.48],
								'三亚市' : [109.50, 18.25],
								'香港':[114.196472,22.315521],
								'台湾':[121.504174,25.07989],
								'澳门':[113.554932,22.191937],
								'新疆生产建设兵团' : [87.619521, 45.80856]
     		   }
		 		var tryconsole=function(message){try{console.log(message)}catch(e){alert("你用的是ie9吗。不过这些都不重要，",+message)}}
		 		var myChart = echarts.init(this[0],newoption.them);
		 		if(newoption.dataUrl!=null){
		 				$.ajax({
								type:"post",
								url: newoption.dataUrl, 
								async:newoption.async,
								data:newoption.parameter,
								success: function(data){
		 								try{data=	eval("("+data+")")}catch(e){}
										data=newoption.datamanage(data)
										newoption.series[0].markPoint.data=data.pointData;
		    	 						newoption.series[0].data=data.regionData;
		    	 						 myChart.setOption(newoption);
		    	 							var ecConfig = echarts.config
											var mapGeoData = echarts.util.mapData.params;
											var cityType = [];
											for (var city in cityMap) {
													cityType.push(city);
											    // 自定义扩展图表类型
											    mapGeoData.params[city] = {
											        getGeoJson: (function (c) {
											            var geoJsonName = cityMap[c];
											            return function (callback) {
											                $.getJSON(newoption.expansionUrl+ geoJsonName +'.json', callback);
											            }
											        })(city)
											    }
											}
											var curIndx=0;
											var drillfunction=null;//钻取方法	drill:"province",//province,city,county
											if(newoption.drill=="county"){
												drillfunction=function (param){
																		if(initshi.length==0){
																			initshi=newoption.series[0].markPoint.data ;
																		}
																		var arr=[];
																		var newarr=[];
																		for(var i=0;i<shen.length;i++){
																			var names=null;
																			 for(var name in shen[i]){       
																			       names=name;
																			       arr= shen[i][name]
																			    }  
																			if(names==param.target){
																				var cityarr=newoption.series[0].markPoint.data
																				for(var x=0 ;x< cityarr.length;x++){
																						 for(var name in arr){       
																							 	if(name==cityarr[x].name){
																							 		newarr.push(cityarr[x])
																							 	}
																						 }
																				}
																			}
																		}
																	newoption.series[0].markPoint.data =newarr;
																	    var len = mapType.length;
																	    var mt = mapType[curIndx % len];
																	    if (mt =='china') {
																	        // 全国选择时指定到选中的省份
																	        var selected = param.selected;
																	        for (var i in selected) {
																		            if (selected[i]) {
																		                mt = i;
																		                while (len--) {
																		                    if (mapType[len] == mt) {
																		                        curIndx = len;
																		                    }
																		                }
																		                break;
																		            }
												      						  }
																	    }else {//判断是否是市
																    		//遍历省下面的   
																    		var cityFlag = false;
																    		for (var i = 0, l = len; i < l; i++) {
																		        if (mt == mapType[i]) {
																		           cityFlag = true;
																		           break;
																		        }
																		    }
																	        if(cityFlag){
																	        	mt = param.target;
																			    var len = cityType.length;
																			    var f= false;
																			    for(var i=0;i<len;i++){
																			        if(mt == cityType[i]){
																		              	f =true;
																			        }
																			    }
																			    if(!f){
																				      curIndx = 0;
																	       		      mt ='china';
																	       		      newoption.series[0].markPoint.data=initshi;
																	       	    	  newoption.tooltip.formatter ='点击进入该省市';
																			    }else{
																			    	  newoption.tooltip.formatter ='点击返回全国';
																			    }
																	        }else{
																	         	 curIndx = 0;
																	       		 mt ='china';
																	       		 newoption.tooltip.formatter ='点击进入该省市';
																	       		 newoption.series[0].markPoint.data =initshi;
																	        }
																	    }
																	    newoption.series[0].mapType = mt;
																	 //   newoption.series[0].geoCoord=newoption.geoCoord;
																	    if(mt=="china"){
																	    	 newoption.title.subtext ='中国区域图';
																	    }else{
																	    	 newoption.title.subtext = mt +'区域图';
																	    }
																	    myChart.setOption(newoption, true);
																	}
											}else if(newoption.drill=="city"){
												drillfunction=function (param){
																		if(initshi.length==0){
																			initshi=newoption.series[0].markPoint.data ;
																		}
																		var arr=[];
																		var newarr=[];
																		for(var i=0;i<shen.length;i++){
																			var names=null;
																			 for(var name in shen[i]){       
																			       names=name;
																			       arr= shen[i][name]
																			    }  
																			if(names==param.target){
																				var cityarr=newoption.series[0].markPoint.data
																				for(var x=0 ;x< cityarr.length;x++){
																						 for(var name in arr){       
																							 	if(name==cityarr[x].name){
																							 		newarr.push(cityarr[x])
																							 	}
																						 }
																				}
																			}
																		}
																	newoption.series[0].markPoint.data =newarr;
																	    var len = mapType.length;
																	    var mt = mapType[curIndx % len];
																	    if (mt =='china') {
																	        // 全国选择时指定到选中的省份
																	        var selected = param.selected;
																	        for (var i in selected) {
																	            if (selected[i]) {
																	                mt = i;
																	                while (len--) {
																	                    if (mapType[len] == mt) {
																	                        curIndx = len;
																	                    }
																	                }
																	                break;
																	            }
																	        }
																	        	  newoption.tooltip.formatter ='点击返回全国';
																	    }else {
																    		 curIndx = 0;
																       		 mt ='china';
																       		 newoption.tooltip.formatter ='点击进入该省市';
																       		 newoption.series[0].markPoint.data =initshi;
																	    }
																	    newoption.series[0].mapType = mt;
																	//    newoption.series[0].geoCoord=newoption.geoCoord;
																	    if(mt=="china"){
																	    	 newoption.title.subtext ='中国区域图';
																	    }else{
																	    	 newoption.title.subtext = mt +'区域图';
																	    }
																	    myChart.setOption(newoption, true);
																	}
											}else if(newoption.drill=="province"){
											}else{
											}
		    	 						 if(newoption.legendSelectked!=null){
												myChart.on(ecConfig.EVENT.LEGEND_SELECTED, newoption.legendSelectked)
											}
											if(newoption.seriesClick!=null){
												myChart.on(ecConfig.EVENT.CLICK,newoption.seriesClick);
											}
											if(newoption.isResize){
												$(window).resize(function(){
													   myChart.resize();
												})
											}
											if(drillfunction){
												myChart.on(ecConfig.EVENT.MAP_SELECTED, drillfunction)
											}
		    	 						 return  myChart;
								},
								error:function(data){
										console.log("Ajax请求错误，请检查url错误")
								}
							})
		 		}
	},
	/**
	 * 判断浏览器
	 * @return {TypeName} 
	 */
	myBrowserVersion:function(){
			var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
		    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
		    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
		    var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari 谷歌浏览器
		    if (isIE) {
		        var IE5 = IE55 = IE6 = IE7 = IE8 = IE9 = IE10=  false;
		        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
		        reIE.test(userAgent);
		        var fIEVersion = parseFloat(RegExp["$1"]);
		        IE55 = fIEVersion == 5.5;
		        IE6 = fIEVersion == 6.0;
		        IE7 = fIEVersion == 7.0;
		        IE8 = fIEVersion == 8.0;
		        IE9 = fIEVersion == 9.0;
		        IE10 = fIEVersion == 10.0;
		        IE11 = fIEVersion == 11.0;
		
		        if (IE55) {
		            return "IE55";
		        }
		        if (IE6) {
		            return "IE6";
		        }
		        if (IE7) {
		            return "IE7";
		        }
		        if (IE8) {
		            return "IE8";
		        }
		        if(IE9){
		         return "IE9";
		        }
		        if(IE10){
		         return "IE10";
		        }
		
		    }else
		    if (isFF) {
		        return "FF";
		    }else 
		    if (isOpera) {
		        return "Opera";
		    }else 
		    if(isSafari){
		        return "Chrome";
		    }else{
		        return "IE11"
		    }				
		}
})