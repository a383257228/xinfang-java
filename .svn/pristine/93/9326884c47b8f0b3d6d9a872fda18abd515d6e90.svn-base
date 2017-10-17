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
    <div idFlag="categoryMainPb" style = "margin-top :20px;display:none">
           <div id="areaA" style="width:45%;height:380px;float:left;font-size:16px;cursor: hand"></div>
	       <div id="paizhuA" style="width:45%;height:380px;float:left;font-size:16px;margin-left: 10px;cursor: hand"></div>
	       <div style="width:30px;height:30px;clear:both;float: right;margin-right: 30px; margin-top: -80px;">
	             <img onclick="javascript:upP()" src="<%=basePath%>/images/up.png">
	       </div>
	       <div style="width:30px;height:30px;clear:both;float: right;margin-right: 30px; margin-top: -40px;">
	             <img  onclick="javascript:nextP()" src="<%=basePath%>/images/next.png">
	       </div> 
    
	</div>
</body>
<script>
	var categoryMainObjPb;//textbox-value	
	$.SUI("categoryMainWinPb");
	var chart;
	var datas;
	var newWord;
	
	var chartA;
	var n=16;
	var start = 0;
	var end = start + n;
	var pzData="";
	var Stat = G2.Stat;
	var Frame = G2.Frame;   
	var frame;
	categoryMainWinPb = {
			init : function(){
				$("#areaA").empty();
				$("#paizhuA").empty();
				 n=16;
				 start = 0;
				 end = start + n;
				 pzData="";
				 Stat = G2.Stat;
				 Frame = G2.Frame;   
				 frame;
				
				newWord = categoryMainObjPb.panel("options").newWord;
				//定义传参的全局变量
			    var code;
			  //玫瑰图  
			 	$.ajax({
						url:'/jubao/dataPredict/data-predict-opinion-info!getAreaMess.action', 
						type : "post",
						data:{
							newWord:newWord		 	    	
						},
						success:function(d){
							if(d=='[]'){
						    	$("#areaA").html("<div style='text-align:center;line-height: 390px;color: white;'>暂无数据</div>");
								return;
						    }	
							//日志 查询
							var params={
								operationFuncCode : "getAreaMess",
								operationFuncName : "查询词'"+newWord+"'下的所有区",
								operationBtnName : dateFlag,
								operationDesc : "查询词'"+newWord+"'下的所有区",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
							};
							saveOperaLog(params);		
						/* var data = [
			                  {ISSUE_REGION_NAME:'徐汇区',FORECAST_NUM:121},
			                  {ISSUE_REGION_NAME:'长宁区',FORECAST_NUM:100},
			                  {ISSUE_REGION_NAME:'普陀区',FORECAST_NUM:124},
			                  {ISSUE_REGION_NAME:'虹口区',FORECAST_NUM:102},
			                  {ISSUE_REGION_NAME:'杨浦区',FORECAST_NUM:124},
			                  {ISSUE_REGION_NAME:'黄浦区',FORECAST_NUM:128},
			                  {ISSUE_REGION_NAME:'静安区',FORECAST_NUM:111},
			                  {ISSUE_REGION_NAME:'奉贤区',FORECAST_NUM:124},
			                  {ISSUE_REGION_NAME:'松江区',FORECAST_NUM:109},
			                  {ISSUE_REGION_NAME:'崇明区',FORECAST_NUM:115},
			                  {ISSUE_REGION_NAME:'金山区',FORECAST_NUM:99},
			                  {ISSUE_REGION_NAME:'浦东新区',FORECAST_NUM:91},
			                  {ISSUE_REGION_NAME:'卢湾区',FORECAST_NUM:87},
			                  {ISSUE_REGION_NAME:'闵行区',FORECAST_NUM:125},
			                  {ISSUE_REGION_NAME:'嘉定区',FORECAST_NUM:130},
			                  {ISSUE_REGION_NAME:'南汇区',FORECAST_NUM:91}
			                  ]; */
							$('<span style="font-size:15px;color:#ffffff;margin-left:28%;margin-top:-9px">全市信访件分布情况（件/次）</span>').appendTo($('#areaA'));				  
							var data = eval('(' + d + ')');
							var chart1 = new G2.Chart({
								id: 'areaA',
								forceFit: true,
								height: 390,
								plotCfg: {
									margin: [50, 0, 75, 0]
								}
							});
							chart1.source(data, {
								'FORECAST_NUM': {
									min: 0
								}
							});
							chart1.coord('polar'); 
							chart1.axis('FORECAST_NUM', {
								labels: null
							});
							chart1.axis('ISSUE_REGION_NAME', {
								gridAlign: 'middle',
								labelOffset: 12,
								labels: {
									label: {
										fill: '#ffffff',
									}
								}
							});
							chart1.legend(false);
							chart1.interval().position('ISSUE_REGION_NAME*FORECAST_NUM').color('ISSUE_REGION_NAME','RGB(0,0,255)-RGB(220,20,60)') 
							.label('FORECAST_NUM',{offset:15,label: {textAlign: 'center',fill:'#ffffff'}}).style({
								lineWidth: 1,
								stroke: '#ffffff'
							});			
							chart1.render();
							chart1.on('plotclick',function(ev){
								var data = ev.data;
								if (data) {
									if(categoryMainObjOpinion){
										 categoryMainObjOpinion.panel("options").dateFlag = dateFlag;
							    		 categoryMainObjOpinion.panel("options").newWord=newWord;
							    		 categoryMainObjOpinion.panel("options").code=data._origin['ISSUE_REGION_CODE'];
							    		 categoryMainObjOpinion.panel("options").irFlag="2";
						    	 	}else{
										categoryMainObjOpinion = $.f("div","categoryMainOpinion").window({
											width:960, 
									 		height:600,
									 		title:'信访件列表',
									 		modal:true,
									 		maximizable:false,
									 		minimizable:false,
									 		collapsible:false,
									 		closed:true,
						 	    			dateFlag:dateFlag,
						 	    			newWord:newWord,
						 	    			irFlag:"2",
								    	    code:data._origin['ISSUE_REGION_CODE'],
						 	    			onOpen:function(){
						 	    				categoryMainWinOpinion.init();
						 	    			},
						 	    			onClose : function(){
								 			categoryMainObjOpinion.panel("options").newWord="";
								    		categoryMainObjOpinion.panel("options").code="";
								    		categoryMainObjOpinion.panel("options").dateFlag="";
								    		categoryMainObjOpinion.panel("options").irFlag="";
								 		  }
						 	    		});
					 	    		}
					 	    		categoryMainObjOpinion.window("open");
					 	    	}/* } */						
							});  					 	    	
					 	 },
						error:function(){  
						dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据加载失败！', 3000);
						}  
					});  

			 
					
					//柱状图
			 		$.ajax({
						url:'/jubao/dataPredict/data-predict-opinion-info!getCommitteeMess.action', 
						type : "post",
						async:false,
						data:{
							newWord:newWord			 	    	
						},
						success:function(d){
							if(d=='[]'){
						    	$("#paizhuA").html("<div style='text-align:center;line-height: 390px;color: white;'>暂无数据</div>");
								return;
						    }
						    $('<span style="font-size:15px;color:#ffffff;margin-left:24%;margin-top:10px">本委信访件分布情况（件/次）</span>').appendTo($('#paizhuA'));	
						    datas = eval('(' + d + ')');
						    zhuT(datas);
							//日志 查询
							var params={
								operationFuncCode : "getCommitteeMess",
								operationFuncName : "查询词'"+newWord+"'下所有区",
								operationBtnName : dateFlag,
								operationDesc : "查询词'"+newWord+"'下所有区",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
							};
							saveOperaLog(params);	
												
						},
						error:function(){  
							dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据加载失败！', 3000);  
						}  
					}); 

					       
				
			}
	};
	
	
	
	function zhuT(){
		if(pzData.length>0){
			frame= new Frame(pzData);
		}else{
			frame= new Frame(datas.slice(start,end));
		}
	//frame = Frame.sort(frame, 'FORECAST_NUM'); // 将数据按照population 进行排序，由大到小
	chartA = new G2.Chart({
		id : 'paizhuA',
		forceFit: true,
		height: 370,
		plotCfg: {
			margin: [0, 50, 10, 140]
		}
	});
	chartA.source(frame);
	chartA.axis('ISSUE_REGION_NAME',{
		title: null,
		line:null,
		tickLine:null,
		labels: {
			label: {
				fill: '#ffffff',
				fontSize:'12',
			}
		}
	});
	chartA.axis('FORECAST_NUM',{
		line:null,
		tickLine:null,
		title:null,
		grid: false,
		labels:null,
	});
	chartA.coord('rect').transpose();
	chartA.legend(false);
	chartA.interval().position('ISSUE_REGION_NAME*FORECAST_NUM').color('ISSUE_REGION_NAME',['#49d9fe']).label('FORECAST_NUM',{
		label: {
			fill: '#fff'
		},
		offset: 2,//距离图像的距离
	});		          
	chartA.on('plotclick',function(ev){	
		var data = ev.data;
		if (data) {
		   	if(categoryMainObjOpinion){
				 categoryMainObjOpinion.panel("options").dateFlag = dateFlag;
	    		 categoryMainObjOpinion.panel("options").newWord=newWord;
	    		 categoryMainObjOpinion.panel("options").code=data._origin['ISSUE_REGION_CODE'];
	    		 categoryMainObjOpinion.panel("options").irFlag="3";
   	 	    }else{
				categoryMainObjOpinion = $.f("div","categoryMainOpinion").window({
					width:960, 
			 	    height:600,
			 		title:'信访件列表',
			 		modal:true,
			 		maximizable:false,
			 		minimizable:false,
			 		collapsible:false,
			 		closed:true,
	    			dateFlag:dateFlag,
	    			irFlag:"3",
	    			newWord:newWord,
		    	    code:data._origin['ISSUE_REGION_CODE'],
	    			onOpen:function(){
	    				categoryMainWinOpinion.init();
	    			},
	    			onClose : function(){
		 			categoryMainObjOpinion.panel("options").newWord="";
		    		categoryMainObjOpinion.panel("options").code="";
		    		categoryMainObjOpinion.panel("options").dateFlag="";
		    		categoryMainObjOpinion.panel("options").irFlag="";
		 		  }
	    		});
    		}
    		categoryMainObjOpinion.window("open");
		}
	});
	chartA.render();	
	}
	function upP(){
		if(start!=0){
			start -= n;
			end -= n;
		}
		pzData = datas.slice(start,end);
		chartA.changeData(pzData); // 动态更新数据
		return;
	};

	function nextP(){
		if(end<datas.length){
			start += n;
			end += n;
		}
		pzData = datas.slice(start,end);
		chartA.changeData(pzData); // 动态更新数据
		return;
	};
</script>
</html>

