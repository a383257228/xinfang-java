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
  <body>
  	<div idFlag="top5DrillingWin" style="display: none">
         <div style="margin-left:20px;width:94%;height:420px"> 
        	  <table idFlag="top5DrillingGrid"></table>
         </div>
    </div>
	<script type="text/javascript">
	var top5WinObj;
	$.SUI("top5DrillingWin");
	top5DrillingWin = {
		init : function(){
			var swFlag=top5WinObj.panel("options").swFlag;
			var dateFlag=top5WinObj.panel("options").dateFlag;
			var startDate=top5WinObj.panel("options").startDate;
			var endDate=top5WinObj.panel("options").endDate;
			var bwRadio=top5WinObj.panel("options").bwRadio;
			var name=top5WinObj.panel("options").name;
			
			var top5DrillingGrid = $.f("div","top5DrillingWin").f("table","top5DrillingGrid");
			top5DrillingGrid.datagridSinosoft({
				body:{
					url:'<%=basePath%>petition/petition-issue-info!drillingTopCount.action',
					queryParams:{
			 	    	swFlag : swFlag,
			 	    	dateFlag : dateFlag,
			 	    	startDateValue　: startDate,
			 	    	endDateValue :　endDate,
			 	    	issueTypeName : name,
			 	    	bwRadio : bwRadio
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
		        		width : 130,
		        		align : "center",
		        		hidden : false,
		        		formatter : function(value,row,index){
		        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:top5DrillingWin.zsCount(\""+row.issueRegionCode+"\",\""+0+"\")'>"+value+"</a>";
		        		}
		        	},{
		        		field : "sjCount",
		        		title : "上级转、交办(件次)",
		        		width : 170,
		        		align : "center",
		        		hidden : false,
		        		formatter : function(value,row,index){
		        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:top5DrillingWin.zsCount(\""+row.issueRegionCode+"\",\""+2+"\")'>"+value+"</a>";
		        		}
		        	},{
		        		field : "xjCount",
		        		title : "下级转、交办(件次)",
		        		width : 175,
		        		align : "center",
		        		hidden : false,
		        		formatter : function(value,row,index){
		        			return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:top5DrillingWin.zsCount(\""+row.issueRegionCode+"\",\""+1+"\")'>"+value+"</a>";
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
			 	    	var params={
			    			operationFuncCode : "drillingTopCount",
			    			operationFuncName : "数量前五位点击问题类别查询问题分布",
			    			operationBtnName : dateFlag,
			    			operationDesc : "数量前五位点击问题类别查询问题分布",
			    			operationTypeCode : OperationType.QUERY,
			    			operationTypeName : OperationType.QUERY_NAME,
			    			enableDataLog :true
			    		}
			 	    	saveOperaLog(params);
			 	    	
						$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
					}
				}
			});
		},
		zsCount : function(code,index){
			if(top5InfoWinObj){
				 top5InfoWinObj.panel("options").swFlag=top5WinObj.panel("options").swFlag;
				 top5InfoWinObj.panel("options").dateFlag=top5WinObj.panel("options").dateFlag;
				 top5InfoWinObj.panel("options").startDate=top5WinObj.panel("options").startDate;
				 top5InfoWinObj.panel("options").endDate=top5WinObj.panel("options").endDate;
				 top5InfoWinObj.panel("options").code=code;
				 top5InfoWinObj.panel("options").index=index;
				 top5InfoWinObj.panel("options").name=top5WinObj.panel("options").name;
				 top5InfoWinObj.panel("options").bwRadio=top5WinObj.panel("options").bwRadio;
			 }else{
				 top5InfoWinObj = $.f("div","Top5InfoWin").window({
	    		    width:960, 
	    		    height:600,
	    		    title:'信访件列表',
	    		    modal:true,
	    			maximizable:false,
	    			minimizable:false,
	    			collapsible:false,
	    			closed:true,
	    			swFlag:top5WinObj.panel("options").swFlag,
	    			dateFlag:top5WinObj.panel("options").dateFlag,
	    			startDate:top5WinObj.panel("options").startDate,
	    			endDate:top5WinObj.panel("options").endDate,
	    			code:code,
	    			index:index,
	    			name:top5WinObj.panel("options").name,
	    			bwRadio:top5WinObj.panel("options").bwRadio,
	    			onOpen:function(){
	    				top5InfoGrid.init();
	    			},
	    			onClose : function(){
	    				$.f("div", "topFindPanel").f("input","accusedName").textbox('setValue',"");
	    				$.f("div", "topFindPanel").f("input","accuserName").textbox('setValue',"");
	    				top5InfoWinObj.panel("options").swFlag="";
		   				top5InfoWinObj.panel("options").dateFlag="";
		   				top5InfoWinObj.panel("options").startDate="";
		   				top5InfoWinObj.panel("options").endDate="";
		   				top5InfoWinObj.panel("options").code="";
		   				top5InfoWinObj.panel("options").index="";
		   				top5InfoWinObj.panel("options").name="";
		   				top5InfoWinObj.panel("options").bwRadio="";
		   			}
	    		});
			 }
			
			top5InfoWinObj.window("open");
		}
	}
	</script>
  </body>
</html>