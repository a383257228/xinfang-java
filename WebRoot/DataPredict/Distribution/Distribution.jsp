<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>
<%
	String swFlag = request.getParameter("swFlag");
	String dateFlag = request.getParameter("dateFlag");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
%>
<!DOCTYPE html>
<html>
  <head>
    <title>信访件分布情况</title>
    <meta charset="utf-8">    
    <script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
    <script src="<%=basePath%>/publicresource/js/g2.js"></script>
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
	       <div id="area" style="width:45%;height:380px;float:left;font-size:16px;cursor: hand;padding-top:10px"></div>
	       <div id="paizhu" style="width:45%;height:380px;float:left;font-size:16px;margin-left: 10px;cursor: hand;padding-top:10px"></div>
	       <div style="width:30px;height:30px;clear:both;float: right;margin-right: 30px; margin-top: -80px;cursor: hand;">
	             <img onclick="javascript:up()" src="<%=basePath%>/images/up.png">
	       </div>
	       <div style="width:30px;height:30px;clear:both;float: right;margin-right: 30px; margin-top: -40px;cursor: hand;">
	             <img  onclick="javascript:next()" src="<%=basePath%>/images/next.png">
	       </div> 
	     </div>
	     <div id = "fx" style = "display:none">
	     
	     </div>
  </div>
    <script>
    //定义传参的全局变量
    var swFlag = "<%=swFlag%>";
	var dateFlag = "<%=dateFlag%>";
	var startDate = "<%=startDate%>";
	var endDate = "<%=endDate%>";
    var code;
    //玫瑰图  
 	$.ajax({
			url:'/jubao/dataPredict/data-predict-distribution-info!dataPredict.action', 
			type : "post",
			data:{
				swFlag : swFlag,
				dateFlag : dateFlag,
				startDateValue:startDate,
				endDateValue:endDate		 	    	
			},
			success:function(d){
				if(d=='[]'){
			    	$("#area").html("<div style='text-align:center;line-height: 390px;color: white;'>当前时间段暂无数据</div>");
					return;
			    }													
				//日志 查询
				var params={
					operationFuncCode : "dataCityOne",
					operationFuncName : "查询区下个月预测数据",
					operationBtnName : dateFlag,
					operationDesc : "数据预测分析表",
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
				$('<span style="font-size:15px;color:#ffffff;margin-left:28%;margin-top:-9px">全市信访件分布情况（件/次）</span>').appendTo($('#area'));				  
				var data = eval('(' + d + ')');
				var chart1 = new G2.Chart({
					id: 'area',
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
					    document.getElementById('trend').innerHTML = '';											
						if(categoryMainObjTrend){
				    		 categoryMainObjTrend.panel("options").swFlag=swFlag;
				    		 categoryMainObjTrend.panel("options").dateFlag=dateFlag;
				    		 categoryMainObjTrend.panel("options").startDate=startDate;
				    		 categoryMainObjTrend.panel("options").endDate=endDate;
				    		 categoryMainObjTrend.panel("options").code=data._origin['ISSUE_REGION_CODE'];
			    	 	}else{
							categoryMainObjTrend = $.f("div","categoryMainTrend").window({
			 	    		    width:800, 
			 	    		    height:500,
			 	    		    title:'趋势图',
			 	    		    modal:true,
			 	    			maximizable:false,
			 	    			minimizable:false,
			 	    			collapsible:false,
			 	    			closed:true,
			 	    			swFlag:swFlag,
					    		dateFlag:dateFlag,
					    		startDate:startDate,
					    		endDate:endDate,
					    	    code:data._origin['ISSUE_REGION_CODE'],
			 	    			onOpen:function(){
			 	    				categoryMainWinTrend.init();
			 	    			},
			 	    			onClose : function(){
					 			categoryMainObjTrend.panel("options").swFlag="";
					    		categoryMainObjTrend.panel("options").dateFlag="";
					    		categoryMainObjTrend.panel("options").startDate="";
					    		categoryMainObjTrend.panel("options").endDate="";
					    		categoryMainObjTrend.panel("options").code="";
					 		  }
			 	    		});
		 	    		}
		 	    		categoryMainObjTrend.window("open");
		 	    	}/* } */						
				});  					 	    	
		 	 },
			error:function(){  
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据加载失败！', 3000);
			}  
		});  

 
		var data;
		//柱状图
 		$.ajax({
			url:'/jubao/dataPredict/data-predict-distribution-info!dataCityOne.action', 
			type : "post",
			async:false,
			data:{
				swFlag : swFlag,
				dateFlag : dateFlag,
				startDateValue:startDate,
				endDateValue:endDate		 	    	
			},
			success:function(d){	
				if(d=='[]'){
			    	$("#paizhu").html("<div style='text-align:center;line-height: 390px;color: white;'>当前时间段暂无数据</div>");
					return;
			    }
			    $('<span style="font-size:15px;color:#ffffff;margin-left:24%;margin-top:10px">本委信访件分布情况（件/次）</span>').appendTo($('#paizhu'));	
				data = eval('(' + d + ')');		
				//日志 查询
				var params={
					operationFuncCode : "dataCityOne",
					operationFuncName : "查询派驻下个月预测数据",
					operationBtnName : dateFlag,
					operationDesc : "信访预测分析表",
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

		
		/*  var data = [
                  {"ISSUE_REGION_NAME":"市级机关纪工委","FORECAST_NUM":1968},
                  {"ISSUE_REGION_NAME":"市委办公厅","FORECAST_NUM":1263},
                  {"ISSUE_REGION_NAME":"市委组织部","FORECAST_NUM":6120},
                  {"ISSUE_REGION_NAME":"市委宣传部","FORECAST_NUM":2700},
                  {"ISSUE_REGION_NAME":"市委统战部","FORECAST_NUM":2429},
                  {"ISSUE_REGION_NAME":"市委政法委机关","FORECAST_NUM":4723},
                  {"ISSUE_REGION_NAME":"市委农办","FORECAST_NUM":2725},
                  {"ISSUE_REGION_NAME":"市教育卫生党委","FORECAST_NUM":3891},
                  {"ISSUE_REGION_NAME":"市科技工作党委","FORECAST_NUM":2309},
                  {"ISSUE_REGION_NAME":"市经济信息化工作党委","FORECAST_NUM":6941},
                  {"ISSUE_REGION_NAME":"市建设交通工作党委","FORECAST_NUM":5191},
                  {"ISSUE_REGION_NAME":"市合作交流工作党委","FORECAST_NUM":5468},
                  {"ISSUE_REGION_NAME":"市国资委党委","FORECAST_NUM":4457},
                  {"ISSUE_REGION_NAME":"市人大机关","FORECAST_NUM":275},
                  {"ISSUE_REGION_NAME":"市政府办公厅","FORECAST_NUM":891},
                  {"ISSUE_REGION_NAME":"市发展改革委","FORECAST_NUM":239},
                  {"ISSUE_REGION_NAME":"市商务委","FORECAST_NUM":641},
                  {"ISSUE_REGION_NAME":"市公安局","FORECAST_NUM":511},
                  {"ISSUE_REGION_NAME":"市国家安全局","FORECAST_NUM":548},
                  {"ISSUE_REGION_NAME":"市民政局","FORECAST_NUM":447},
                  {"ISSUE_REGION_NAME":"市财政局","FORECAST_NUM":511},
                  {"ISSUE_REGION_NAME":"市人力资源社会保障局","FORECAST_NUM":58},
                  {"ISSUE_REGION_NAME":"市交通委","FORECAST_NUM":44},
                  {"ISSUE_REGION_NAME":"市规划国土资源局","FORECAST_NUM":48},
                  {"ISSUE_REGION_NAME":"市文广影视局","FORECAST_NUM":47},
                  {"ISSUE_REGION_NAME":"市地税局","FORECAST_NUM":51},
                  {"ISSUE_REGION_NAME":"市政协机关","FORECAST_NUM":848},
                  {"ISSUE_REGION_NAME":"市高级人民法院","FORECAST_NUM":45},
                  {"ISSUE_REGION_NAME":"市检察院","FORECAST_NUM":58},
                  {"ISSUE_REGION_NAME":"市总工会机关","FORECAST_NUM":43},
                ]; */
		var n=16;
		var start = 0;
		var end = start + n;
		var pzData="";
		var Stat = G2.Stat;
		var Frame = G2.Frame;   
		var frame;
		if(pzData.length>0){
			frame= new Frame(pzData);
		}else{
			frame= new Frame(data.slice(start,end));
		}
		//frame = Frame.sort(frame, 'FORECAST_NUM'); // 将数据按照population 进行排序，由大到小
		var chart = new G2.Chart({
			id : 'paizhu',
			forceFit: true,
			height: 370,
			plotCfg: {
				margin: [0, 50, 10, 140]
			}
		});
		chart.source(frame);
		chart.axis('ISSUE_REGION_NAME',{
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
		chart.axis('FORECAST_NUM',{
			line:null,
			tickLine:null,
			title:null,
			grid: false,
			labels:null,
		});
		chart.coord('rect').transpose();
		chart.legend(false);
		chart.interval().position('ISSUE_REGION_NAME*FORECAST_NUM').color('ISSUE_REGION_NAME',['#49d9fe']).label('FORECAST_NUM',{
			label: {
				fill: '#fff'
			},
			offset: 2,//距离图像的距离
		});		          
		chart.on('plotclick',function(ev){	
			var data = ev.data;
			if (data) {
				document.getElementById('trend').innerHTML = '';
				if(categoryMainObjTrend){
				   categoryMainObjTrend.panel("options").swFlag=swFlag;
				   categoryMainObjTrend.panel("options").dateFlag=dateFlag;
				   categoryMainObjTrend.panel("options").startDate=startDate;
				   categoryMainObjTrend.panel("options").endDate=endDate;
				   categoryMainObjTrend.panel("options").code=data._origin['ISSUE_REGION_CODE'];
			    }else{
				categoryMainObjTrend = $.f("div","categoryMainTrend").window({
 	    		    width:800, 
 	    		    height:500,
 	    		    title:'趋势图',
 	    		    modal:true,
 	    			maximizable:false,
 	    			minimizable:false,
 	    			collapsible:false,
 	    			closed:true,
 	    			swFlag:swFlag,
					dateFlag:dateFlag,
					startDate:startDate,
					endDate:endDate,
					code:data._origin['ISSUE_REGION_CODE'],
 	    			onOpen:function(){
 	    				categoryMainWin.init();
 	    			},
			 	    onClose : function(){
					 	categoryMainObjTrend.panel("options").swFlag="";
					    categoryMainObjTrend.panel("options").dateFlag="";
					    categoryMainObjTrend.panel("options").startDate="";
					    categoryMainObjTrend.panel("options").endDate="";
					    categoryMainObjTrend.panel("options").code="";
					}
 	    		});
 	    		}
 	    		categoryMainObjTrend.window("open");
			}
		});
		chart.render();			

		       
		
		function up(){
			if(start!=0){
				start -= n;
				end -= n;
			}
			pzData = data.slice(start,end);
			chart.changeData(pzData); // 动态更新数据
			return;
		};

		function next(){
			if(end<data.length){
				start += n;
				end += n;
			}
			pzData = data.slice(start,end);
			chart.changeData(pzData); // 动态更新数据
			return;
		};
    </script>
  </body>
</html>
