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
	   	  <div style="font-size:16px;color:#ffffff;margin-left:15%;padding-top:15px">
	      	<span style="font-size:20px;font-weight:200">增</span>&nbsp;量前<span style="font-size:24px;color:#ED5655;font-style:italic">&nbsp;5&nbsp;</span>位问题类别
	      </div>
         <div id="zlDiv" style="width:48%;height:300px;padding-top:15px;float:left;margin-left:20px;cursor: hand" ></div>
         <div id="jlDiv" style="width:48%;height:300px;padding-top:15px;float:left;cursor: hand" ></div> 
    	 <div style="font-size:16px;color:#ffffff;margin-left:64%;margin-top:-30px;line-height:10px;clear:both">
	     	 <span style="font-size:20px;font-weight:200">减</span>&nbsp;量前<span style="font-size:24px;color:#ED5655;font-style:italic">&nbsp;5&nbsp;</span>位问题类别
	     </div>
   	 </div>
 </div>
<script type="text/javascript">
    var swFlag = "<%=swFlag%>";
	var dateFlag = "<%=dateFlag%>";
	var startDate = "<%=startDate%>";
	var endDate = "<%=endDate%>";
	var bwRadio = "<%=bwRadio%>";
	var JL = new Array(); 
	var ZL = new Array(); 
		
	$.ajax({
 	    url:'<%=basePath%>/petition/petition-issue-info!zjIssueTypeTop5.action',  
 	    type : "post",
        async : false,
 	    data:{
 	    	swFlag : swFlag,
 	    	dateFlag : dateFlag,
 	    	startDateValue　: startDate,
 	    	endDateValue :　endDate,
 	    	bwRadio : bwRadio
 	    },
 	    success:function(data){
 	    	var params={
    			operationFuncCode : "zjIssueTypeTop5",
    			operationFuncName : "问题类别变化主查询",
    			operationBtnName : dateFlag,
    			operationDesc : "问题类别变化主查询",
    			operationTypeCode : OperationType.QUERY,
    			operationTypeName : OperationType.QUERY_NAME,
    			enableDataLog :true
    		}
    		saveOperaLog(params);
 	    	
 	    	var ZLData = eval('(' + data.substring(0,(data.indexOf("]")+1)) + ')');
 	    	ZL = ZLData;
 	    	var JLData = eval('(' + data.substring((data.indexOf("],")+2),data.length) + ')');
 	    	JL = JLData;
 	    	if(ZL.length==0){
 	    		ZL = [{'name':'当前选中时间段暂无增量数据','value':0}]
 	    	}
 	    	if(JL.length==0){
 	    		JL = [{'name':'当前选中时间段暂无减量数据','value':0}]
 	    	}
        },
        error:function(){  
        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
        	return;
        }  
    });
	
	var chartz = new G2.Chart({
	         id: 'zlDiv',
	         forceFit: true,
	         height: 300,
	         plotCfg: {
	           margin: [0,10,10,50]
	         }
     });
	chartz.source(ZL, {
         name: {
           formatter: function(val) {
             return val;
           }
         }
     });
	chartz.coord('rect').transpose().scale(1,-1);
	chartz.axis(false);
	chartz.legend(false);
	chartz.tooltip({
         	title: null
    });
       
	chartz.intervalSymmetric().position('name*value').color('name',['#1D9FF2', '#97B552', '#DE9B32','#50CDF6','#ED5655']).shape('pyramid').label('name',{label: {fill: '#fff'},offset:'left'});
	chartz.render();
	chartz.on('plotclick',function(ev){
		if(classChangeObj){
			 classChangeObj.panel("options").swFlag=swFlag;
			 classChangeObj.panel("options").dateFlag=dateFlag;
			 classChangeObj.panel("options").startDate=startDate;
			 classChangeObj.panel("options").endDate=endDate;
			 classChangeObj.panel("options").bwRadio=bwRadio;
			 classChangeObj.panel("options").issueTypeName=ev.data._origin.name;
		 }else{
			 classChangeObj = $.f("div","classChangeWin").window({
	   		    width:810, 
	   		    height:490,
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
	   			issueTypeName:ev.data._origin.name,
	   			onOpen:function(){
	   				classChange.init();
	   			},
	   			onClose:function(){
	   				classChangeObj.panel("options").swFlag="";
		   			classChangeObj.panel("options").dateFlag="";
		   			classChangeObj.panel("options").startDate="";
		   			classChangeObj.panel("options").endDate="";
		   			classChangeObj.panel("options").bwRadio="";
		   			classChangeObj.panel("options").issueTypeName="";
	   			}
	   		});
		 }
    	 classChangeObj.window("open");
	});
	  
	   
    var chartj = new G2.Chart({
           id: 'jlDiv',
           forceFit: true,
           height: 300,
           plotCfg: {
         	  margin:[0,50,10,0],
           }
    });
    chartj.source(JL, {
           name: {
              formatter: function(val) {
                return val;
              }
           }
    });
    chartj.legend(false);
    chartj.coord('rect').transpose().scale(-1,1);
    chartj.axis(false);
    chartj.intervalSymmetric().position('name*value').color('name',['#1D9FF2', '#97B552', '#DE9B32','#50CDF6','#ED5655']).shape('pyramid').label('name',{label: {fill: '#fff'},offset:'center'});
    chartj.render(); 
	       
    chartj.on('plotclick',function(ev){
    	if(classChangeObj){
    		 classChangeObj.panel("options").swFlag=swFlag;
			 classChangeObj.panel("options").dateFlag=dateFlag;
			 classChangeObj.panel("options").startDate=startDate;
			 classChangeObj.panel("options").endDate=endDate;
			 classChangeObj.panel("options").bwRadio=bwRadio;
			 classChangeObj.panel("options").issueTypeName=ev.data._origin.name;
			 classChangeObj.panel("options").title="问题类"
    	 }else{
    		 classChangeObj = $.f("div","classChangeWin").window({
    	   		    width:810, 
    	   		    height:490,
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
    	   			issueTypeName:ev.data._origin.name,
    	   			onOpen:function(){
    	   				classChange.init();
    	   			},
    	   			onClose:function(){
    	   				classChangeObj.panel("options").swFlag="";
	    	   			classChangeObj.panel("options").dateFlag="";
	    	   			classChangeObj.panel("options").startDate="";
	    	   			classChangeObj.panel("options").endDate="";
	    	   			classChangeObj.panel("options").bwRadio="";
	    	   			classChangeObj.panel("options").issueTypeName="";
    	   			}
   	   		});
    	 }
    	classChangeObj.window("open");
     });
    </script>
  </body>
</html>