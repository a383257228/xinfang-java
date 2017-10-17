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
     <script src="<%=basePath%>/publicresource/js/g2.js"></script>
  </head>
  <body>
    <div idFlag="classChangeWin" style="width:800px;height:430px;display: none">
       <div id="classChangeDiv"  style="width:100%;height:420px;float:left;font-size:16px;cursor: hand"></div>
	</div> 
    <script>
      var classChangeObj;
      $.SUI("classChange");
      classChange = {
    	   init : function(){
    		   var swFlag=classChangeObj.panel("options").swFlag;
    		   var dateFlag=classChangeObj.panel("options").dateFlag;
    		   var startDate=classChangeObj.panel("options").startDate;
    		   var endDate=classChangeObj.panel("options").endDate;
    		   var bwRadio=classChangeObj.panel("options").bwRadio;
    		   var issueTypeName=classChangeObj.panel("options").issueTypeName;
    		   
    		   G2.Global.setTheme('cheery'); // 切换默认主题
    		   
    		   $.ajax({
    		 	    url:'<%=basePath%>/petition/petition-issue-info!issueDistribution.action',  
    		 	    type : "post",
    		        async : false,
    		 	    data:{
    		 	    	swFlag : swFlag,
    		 	    	dateFlag : dateFlag,
    		 	    	startDateValue　: startDate,
    		 	    	endDateValue :　endDate,
    		 	    	issueTypeName : issueTypeName,
    		 	    	bwRadio : bwRadio
    		 	    },
    		 	    success:function(data){
    		 	    	$("#classChangeDiv").html('');
    		 	    	var params={
   		 	    			operationFuncCode : "issueDistribution",
   		 	    			operationFuncName : "问题类别变化钻取查询分布情况",
   		 	    			operationBtnName : dateFlag,
   		 	    			operationDesc : "问题类别变化钻取查询分布情况",
   		 	    			operationTypeCode : OperationType.QUERY,
   		 	    			operationTypeName : OperationType.QUERY_NAME,
   		 	    			enableDataLog :true
   		 	    		}
   		 	    		saveOperaLog(params);
    		 	    	
    		 	    	datas = eval('(' + data + ')');
    		 	    	if(datas.length==0){
    		 	    		$("#classChangeDiv").html("<div style='margin-left:310px;line-height: 390px;color: white;'>当前选中时间段暂无数据</div>");
    						return;
    		 	    	}else{
    		 	    		var chart = new G2.Chart({
    		    		        id: 'classChangeDiv',
    		    		        forceFit: true,
    		    		        height: 420,
    		    		        plotCfg: {
    		    			    	   margin: [30, 90, 120, 50]
    		   			        }
    		    		    });
    		    		    chart.source(datas, {
   		    		            count: {
   		    		            	min:0
   		    		            }
   		    		        });
    		    		    chart.axis('name', {
    		    		    	title: null ,
    		    		    	line: {
    		    		    		  lineWidth:1, // 设置线的宽度
    		    		    		  stroke: 'white', // 设置线的颜色
    		    		        },
    		    		    	labels: {
    		    		    		label: {
    		    		    			rotate: 0,
    		    		    			textAlign: 'center', 
    		    		    			fill: '#ffffff', 
    		    		    			fontSize: '14', 
    		    		    		}
    		    		        }
    		    		    });
    		    		    chart.axis('count', {
    		    		    	title: null ,
    		    		    	line: {
   		    		    		    lineWidth:1, // 设置线的宽度
   		    		    		    stroke: '#ffffff', // 设置线的颜色
   		    		    		},
    		    		    	labels: {
    		    		    		label: {
    		    		    			fill: '#ffffff', // 文本的颜色
    		    		    			fontSize: '14', // 文本大小
    		    		    		}
    		    		    	}
    		    		    });
    		    		    chart.legend(false);
    		    		    chart.point().position('name*count').color('#F10505').shape('type', ['circle', 'triangle-down', 'square', 'diamond']).size(10);
    		    		   
    		    		    datas.forEach(function(ele,datas){
	    		    		    chart.guide().line([ele.name, 0],[ele.name, ele.count],{stroke: '#999', lineWidth: 3}); // 线的宽度及颜色
    		    		    });
    		    		    
    		    		    chart.render();
    		    		    chart.on('plotclick',function(ev){
    		    		    	if(ev.data==undefined){
    		    		    		 return;
    		    		    	}
    		    		    	if(classChangeWinObj){
    		    		    		 classChangeWinObj.panel("options").swFlag=swFlag;
    		    		    		 classChangeWinObj.panel("options").dateFlag=dateFlag;
    		    		    		 classChangeWinObj.panel("options").startDate=startDate;
    		    		    		 classChangeWinObj.panel("options").endDate=endDate;
    		    		    		 classChangeWinObj.panel("options").bwRadio=bwRadio;
    		    		    		 classChangeWinObj.panel("options").name=issueTypeName;
    		    		    		 classChangeWinObj.panel("options").code=ev.data._origin.code;
    		    		    	 }else{
    		    		    		   classChangeWinObj = $.f("div","ClassChangeDiv").window({
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
	     				    	   		    code:ev.data._origin.code,
	     				    	   		    name:issueTypeName,
	     					    			onOpen:function(){
	     					    				classChangeGrid.init();
	     					    			},
	     					    			onClose : function(){
	     					    				$.f("div", "classChangeFindPanel").f("input","accusedName").textbox('setValue',"");
	     					    				$.f("div", "classChangeFindPanel").f("input","accuserName").textbox('setValue',"");
	     					    				classChangeWinObj.panel("options").swFlag="";
	     	    		    		    		classChangeWinObj.panel("options").dateFlag="";
	     	    		    		    		classChangeWinObj.panel("options").startDate="";
	     	    		    		    		classChangeWinObj.panel("options").endDate="";
	     	    		    		    		classChangeWinObj.panel("options").bwRadio="";
	     	    		    		    		classChangeWinObj.panel("options").name="";
	     	    		    		    		classChangeWinObj.panel("options").code="";
	     					    			}
	     					    		});
    		    		    	}
    		    		    	classChangeWinObj.window("open");
    					    });
    		 	    	}
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