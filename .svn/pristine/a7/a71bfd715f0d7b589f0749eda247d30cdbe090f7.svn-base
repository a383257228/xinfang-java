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
	       <div id="area" style="width:45%;height:380px;float:left;font-size:16px;cursor: hand"></div>
	       <div id="paizhu" style="width:45%;height:380px;float:left;font-size:16px;margin-left: 10px;cursor: hand"></div>
	       <div style="width:30px;height:30px;clear:both;float: right;margin-right: 30px; margin-top: -50px;">
	             <img onclick="javascript:up()" src="<%=basePath%>/images/up.png">
	       </div>
	       <div style="width:30px;height:30px;clear:both;float: right;margin-right: 30px; margin-top: -24px;">
	             <img  onclick="javascript:next()" src="<%=basePath%>/images/next.png">
	       </div> 
	     </div>
  </div>
    <script>
      var swFlag = "<%=swFlag%>";
	  var dateFlag = "<%=dateFlag%>";
	  var startDate = "<%=startDate%>";
	  var endDate = "<%=endDate%>";
      var aPaiZhu = new Array(); 
	  var aCounty = new Array();
	  
	  $.ajax({
	 	    url:'<%=basePath%>/petition/petition-basic-info!queryXFJPaiZhu.action',  
	 	    type : "post",
	        async : false,
	 	    data:{
	 	    	swFlag : swFlag,
	 	    	dateFlag : dateFlag,
	 	    	startDateValue　: startDate,
	 	    	endDateValue :　endDate
	 	    },
	 	    success:function(data){
	 	    	var params={
 	    			operationFuncCode : "queryXFJPaiZhu",
 	    			operationFuncName : "信访件分布情况主查询",
 	    			operationBtnName : dateFlag,
 	    			operationDesc : "信访件分布情况主查询",
 	    			operationTypeCode : OperationType.QUERY,
 	    			operationTypeName : OperationType.QUERY_NAME,
 	    			enableDataLog :true
 	    		}
 	    		saveOperaLog(params);
	 	    	
	 	    	datas = eval('(' + data + ')');
	 	    	datas.forEach(function(ele,datas){
	 	    		var name = ele.name;
	 	    		if("虹口区"==name||"杨浦区"==name||"闵行区"==name||"宝山区"==name||"嘉定区"==name
						||"浦东新区"==name||"金山区"==name||"松江区"==name||"青浦区"==name||"奉贤区"==name||"上海市"==name
						||"崇明区"==name||"黄浦区"==name||"徐汇区"==name||"长宁区"==name||"静安区"==name||"普陀区"==name){
						aCounty.push(ele);
					}else{
						aPaiZhu.push(ele);
					}
	            });
	 	    	aPaiZhu.sort(compare);
	        },
	        error:function(){  
	             dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
	        }  
 	  }); 
      var n=16;
	  var start = 0;
	  var end = start + n;
      var PZData="";
    
      //柱状图
      $('<span style="font-size:15px;color:#ffffff;margin-left:24%;margin-top:10px">全市(委办)信访件分布情况（件/次）</span>').appendTo($('#paizhu'));
      var chart = new G2.Chart({
	       id : 'paizhu',
	       forceFit: true,
	       height: 380,
	       plotCfg: {
	    	   margin: [0, 50, 10, 140]
	       }
     });
	 if(PZData.length==0){
    	PZData = aPaiZhu.slice(start,end);
     }
     var Stat = G2.Stat;
	 var Frame = G2.Frame;
	 var frame = new Frame(PZData);
	 
	// frame = Frame.sort(frame, 'name');
     chart.source(frame);
     chart.axis('name',{
	         title: null,
	         line:null,
	     	 tickLine:null,
	         labels: {
	             label: {
	           	     fill: '#ffffff',
	           	     fontSize:'4',
	             }
	         }
	  });
	  chart.axis('count',{
	    	line:null,
	    	tickLine:null,
	        title:null,
	        grid: false,
	        labels:null,
	  });
	  chart.coord('rect').transpose();
	  chart.interval().position('name*count').color('#49d9fe').label('count',{
		   label: {
		      fill: '#fff'
		   },
	 	   offset: 2,//距离图像的距离
	  });
	  chart.render();
	  // 监听click事件
     chart.on('plotclick',function(ev){
    	 if(ev.data==undefined){
    		 return;
    	 }
    	 if(letterInfoWinObj){
    		 letterInfoWinObj.panel("options").swFlag=swFlag;
    		 letterInfoWinObj.panel("options").dateFlag=dateFlag;
    		 letterInfoWinObj.panel("options").startDate=startDate;
    		 letterInfoWinObj.panel("options").endDate=endDate;
    		 letterInfoWinObj.panel("options").orgCode=ev.data._origin.code;
    	 }else{
	    	 letterInfoWinObj = $.f("div","LetterInfoWin").window({
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
	    		  orgCode:ev.data._origin.code,
		 		  onOpen:function(){
		 			 letterInfoWin.init();
		 		  },
		 		  onClose : function(){
		 			 $.f("div", "qsFindPanel").f("input","accusedName").textbox('setValue',"");
		 			 $.f("div", "qsFindPanel").f("input","accuserName").textbox('setValue',"");
		 			 letterInfoWinObj.panel("options").swFlag="";
		    		 letterInfoWinObj.panel("options").dateFlag="";
		    		 letterInfoWinObj.panel("options").startDate="";
		    		 letterInfoWinObj.panel("options").endDate="";
		    		 letterInfoWinObj.panel("options").orgCode="";
		 		  }
	 		});
    	 }
		letterInfoWinObj.window("open");
     });
	  
	  
     //玫瑰图
     $('<span style="font-size:15px;color:#ffffff;margin-left:28%;margin-top:-9px">全市(区/县)信访件分布情况（件/次）</span>').appendTo($('#area'));
     var Frame = G2.Frame;
     var frame = new Frame(aCounty); // 加工数据
     frame = Frame.combinColumns(frame, ['count'], 'count', 'name');
     var chart1 = new G2.Chart({
	      id: 'area',
	      forceFit: true,
	      height: 390,
	      plotCfg: {
	         margin: [50, 0,75, 0]
	      }
     });
     chart1.source(frame);
     chart1.coord('polar'); 
     chart1.axis('count', {
    	 labels: null
     });
     chart1.axis('name', {
	       labelOffset: 12,
	       labels: {
	         label: {
	       	  	fill: '#ffffff',
	       		fontSize:'12'
	         }
	       }
     });
     chart1.legend(false);
     chart1.interval().position('name*count').color('name','RGB(0,0,255)-RGB(220,20,60)')
     .label('count',{offset: 13,label: {textAlign: 'center',fill:'#ffffff'}})
     .style({
	       lineWidth: 1,
	       stroke: '#ffffff'
     });
     chart1.render();
     // 监听click事件
     chart1.on('plotclick',function(ev){
    	 if(ev.data==undefined){
    		 return;
    	 }
    	 if(categoryMainObj){
    		   categoryMainObj.panel("options").swFlag=swFlag;
    		   categoryMainObj.panel("options").dateFlag=dateFlag;
    		   categoryMainObj.panel("options").startDate=startDate;
    		   categoryMainObj.panel("options").endDate=endDate;
    		   categoryMainObj.panel("options").orgCode=ev.data._origin.code;
   		}else{
	    	 categoryMainObj = $.f("div","categoryMain").window({
	    		    width:800, 
	    		    height:500,
	    		    title:'问题分布情况',
	    		    modal:true,
	    			maximizable:false,
	    			minimizable:false,
	    			collapsible:false,
	    			closed:true,
	    			swFlag:swFlag,
	    			dateFlag:dateFlag,
	    			startDate:startDate,
	    			endDate:endDate,
	    			orgCode:ev.data._origin.code,
	    			onOpen:function(){
	    				categoryMainWin.init();
	    			},
	    			onClose : function(){
	    				categoryMainObj.panel("options").swFlag="";
	    	    		categoryMainObj.panel("options").dateFlag="";
	    	    		categoryMainObj.panel("options").startDate="";
	    	    		categoryMainObj.panel("options").endDate="";
	    	    		categoryMainObj.panel("options").orgCode="";
			 		}
	    	});
    	}
    	categoryMainObj.window("open");
     });
     
     
     function up(){
    	if(start!=0){
	    	start -= n;
	 		end -= n;
    	}else{
    		return;
    	}
 		PZData = aPaiZhu.slice(start,end);
 		PZData.sort(compare);
 		chart.changeData(PZData); // 动态更新数据
 		return;
     };
     function next(){
    	 if(end<aPaiZhu.length){
	    	 start += n;
			 end += n;
		 }
		 PZData = aPaiZhu.slice(start,end);
		 PZData.sort(compare);
		 chart.changeData(PZData); // 动态更新数据
		 return;
     };
    
  	function compare(obj1, obj2){
	 	    var val1 = obj1.name;
	 	    var val2 = obj2.name;
	 	    if (val1 < val2) {
	 	        return -1;
	 	    } else if (val1 > val2) {
	 	        return 1;
	 	    } else {
	 	        return 0;
	 	    }            
	 }
    </script>
  </body>
</html>