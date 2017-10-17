<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script src="<%=basePath%>/publicresource/js/g2.js"></script>
  </head> 
  <body style="background:rgba(4,14,23,0.8)">
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/window.css">
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
  	
  	<div idFlag="top5DrillingWin" style="display: none">
	    <div style="width:98%;height:420px;background:url(<%=basePath%>/images/bk2.png) no-repeat;background-size:100%;padding-top:10px;margin-left:5px;">
		    <div style="height:360px;margin-left:20px;background:url(<%=basePath%>/images/bjk1.png) no-repeat;background-size:98%;padding-top:10px">
		         <div style="margin-left:10px;width:92%;height:350px"> 
		        	  <table idFlag="top5DrillingGrid"></table>
		         </div>
		    </div>
	    </div>
    </div>
	<script type="text/javascript">
	var top5WinObj;
	$.SUI("top5DrillingWin");
	var swFlag ;
	var dateFlag ;
	var startDate ;
	var endDate ;
	var issueTypeName;
	top5DrillingWin = {
		init : function(swFlag,dateFlag,startDate,endDate,name){
			swFlag = swFlag ;
			dateFlag = dateFlag;
			startDate = startDate ;
			endDate = endDate ;
			issueTypeName = name;
			var top5DrillingGrid = $.f("div","top5DrillingWin").f("table","top5DrillingGrid");
			top5DrillingGrid.datagridSinosoft({
				body:{
					url:'<%=basePath%>dataPredict/data-predict-tree-forest!drillingTopCount.action',
					queryParams:{
			 	    	swFlag : swFlag,
			 	    	dateFlag : dateFlag,
			 	    	startDateValue　: startDate,
			 	    	endDateValue :　endDate,
			 	    	issueTypeName : name
			 	    },
					border : false,
					singleSelect : true,
					checkbox : false,
					fitColumns:false,
					pageSize : 10,
					columns:[[{
		        		field : "issueRegionName",
		        		title : "所属区域",
		        		width : 180,
		        		align : "center"
		        	},{
		        		field : "zsCount",
		        		title : "自收件(件次)",
		        		width : 120,
		        		align : "center",
		        		hidden : false,
		        		formatter : function(value,row,index){
		        			return "<a style=\"text-decoration:none;color:#062852;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:top5DrillingWin.zsCount(\""+row.issueRegionCode+"\",\""+0+"\")'>"+value+"</a>";
		        		}
		        	},{
		        		field : "sjCount",
		        		title : "上级转、交办(件次)",
		        		width : 150,
		        		align : "center",
		        		hidden : false,
		        		formatter : function(value,row,index){
		        			return "<a style=\"text-decoration:none;color:#062852;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:top5DrillingWin.zsCount(\""+row.issueRegionCode+"\",\""+1+"\")'>"+value+"</a>";
		        		}
		        	},{
		        		field : "xjCount",
		        		title : "下级转、交办(件次)",
		        		width : 160,
		        		align : "center",
		        		hidden : false,
		        		formatter : function(value,row,index){
		        			return "<a style=\"text-decoration:none;color:#062852;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:top5DrillingWin.zsCount(\""+row.issueRegionCode+"\",\""+2+"\")'>"+value+"</a>";
		        		}
		        	}]],
					loadFilter:function(data){
						data.rows=data.result;
						return data;
					},
					onBeforeLoad:function(param){
						param.start=(param.page-1)*param.rows;
						param.limit=param.rows;
					},
					onLoadSuccess: function(data){
						$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
					}
				}
			});
		},
		zsCount : function(code,index){
			top5InfoWinObj = $.f("div","Top5InfoWin").window({
    		    width:830, 
    		    height:500,
    		    title:'信访件列表',
    		    modal:true,
    			maximizable:false,
    			minimizable:false,
    			collapsible:false,
    			closed:true,
    			onOpen:function(){
    				top5InfoGrid.init(swFlag,dateFlag,startDate,endDate,code,index,issueTypeName);
    			},
    			onClose : function(){
    				$.f("div", "topFindPanel").f("input","accusedName").textbox('setValue',"");
    				$.f("div", "topFindPanel").f("input","accuserName").textbox('setValue',"");
	   			}
    		});
			top5InfoWinObj.window("open");
		}
	}
	</script>
  </body>
</html>