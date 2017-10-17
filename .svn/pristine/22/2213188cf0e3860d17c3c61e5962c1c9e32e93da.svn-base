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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>信访件分布情况本委</title>
  	<base href="<%=basePath%>">
  	<script src="<%=basePath%>/DataAnalysis/js/gridColumns.js"></script>
  </head> 
  <body>
  	<style>
	    span{
	       font-size:15px;
	       margin-right:3px
	    }
	    input{
	       width:150px;
	       height:26px;
	       font-size:14px;
	    }
	    .write,.write:hover{
	      background:#BBD0DE;
	      border:none;
	      height:26px;
	      line-height:26px;
	      color:#062852
	    }
	    
	    
	    
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
	     .xffbDiv{
	     margin-left:10px;
	     width:95%;
	     height:380px;
	     }
	    @media only screen and (max-width: 1024px) {
	     .bk{
		     background:url(<%=basePath%>/images/bk2.png) no-repeat;
	     }
	     .tie{
	        background:url(<%=basePath%>/images/bjk1.png) no-repeat;
	         background-size:96.5%;
	     }
	     .xffbDiv{
	     margin-left:5px;
	     }
		}
  	</style>
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
    <div class="bk">
	    <div class="tie">
	         <div idFlag="xffbDiv" class="xffbDiv"> 
	         	 <div idFlag="bwFindPanel" style="margin-bottom:10px;">
	         	 	  <span style="color:#BBD0DE;">反映人姓名:</span><input idFlag="accuserName" class="sinosoft-textbox" style="width:200px">
					  <span style="color:#BBD0DE;">被反映人姓名:</span><input idFlag="accusedName" class="sinosoft-textbox" style="width:200px">
					  
					  <a href="javascript:void(0)"class="button deepblue" style="width:50px;margin-left:10px;text-align:center;height:26px;line-height:26px;color:#062852;background:#49D9FE;font-size:14px" onclick="XFBWWin.query()">查询</a>
					  <a href="javascript:void(0)"class="button write" style="width:50px;margin-left:10px;text-align:center;font-size:14px"  onclick="XFBWWin.reset()">重置</a>
	         	 </div>
	         	 <div style="height:360px;">
		        	 <table idFlag="xffb" ></table>
	         	 </div>
	         </div>
	    </div>
    </div>
	<script type="text/javascript">
	$.SUI("XFBWWin");
	XFBWWin = {
		query : function(){
			var accusedName;
			var accuserName;
			//被反映人姓名
			if($.f("div", "bwFindPanel").f("input","accusedName").textbox('getValue')){
				accusedName = $.f("div", "bwFindPanel").f("input","accusedName").textbox("getValue");
			}
			//反映人姓名
			if($.f("div", "bwFindPanel").f("input","accuserName").textbox('getValue')){
				accuserName = $.f("div", "bwFindPanel").f("input","accuserName").textbox("getValue");
			}
			var params = {
					swFlag : swFlag,
		 	    	dateFlag : dateFlag,
		 	    	startDateValue　: startDate,
		 	    	endDateValue :　endDate,
		 	    	accusedName : accusedName,
		 	    	accuserName : accuserName,
		 	    	bwRadio :　bwRadio
			};
			//将参数对象赋值给列表参数
			$.f("div","xffbDiv").f("table","xffb").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","xffbDiv").f("table","xffb").datagridSinosoft("load");
		},
		reset : function(){
			$.f("div", "bwFindPanel").f("input","accusedName").textbox('setValue',"");
			$.f("div", "bwFindPanel").f("input","accuserName").textbox('setValue',"");
		}
	}
	var swFlag = "<%=swFlag%>";
	var dateFlag = "<%=dateFlag%>";
	var startDate = "<%=startDate%>";
	var endDate = "<%=endDate%>";
	var bwRadio = "<%=bwRadio%>";
	var xffbGrid = $.f("div","xffbDiv").f("table","xffb");
	xffbGrid.expandGridSinosoft({
		body:{
			detailFormatter: function(index,row){
				return "<div id=\""+index+"bwDiv\" style=\"width:100%\"></div>";
			},
			onExpandRow : function(index, row) {
				$("#" +index+"bwDiv").html("<div id=\""+index+"bwTrack\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
				$.parser.parse("#" + index+"bwDiv");
				$("#"+index+"bwTrack").edatagridSinosoft({
					body : {
						url : '<%=basePath%>/workflow/petition-circulation-state-trace-info!pagedQuery.action',
						queryParams : {
							parameter:0,
							filterTxt : 'petitionNo',
							filterValue : row.petitionNo
						},
						fitColumns : true,
						border : false,
						fit : false,
						striped : true,
						pagination : false,
						singleSelect : false,
						columns : [[{
									field : 'regionName',
									title : "所属机构",
									width : 120,
									align : 'center'
								}, {
									field : 'activityNoName',
									title : "状态",
									width : 110,
									align : 'center'
								}, {
									title : "操作人",
									field : 'instructor',
									width : 100,
									align : 'center'
								}, {
									title : "操作时间",
									field : 'createDate',
									width : 100,
									align : 'center'
								}]],
						loadFilter : function(data) {
							data.rows = data.result;
							return data;
						},
						onBeforeLoad : function(param) {
							param.sort="createDate";
							param.dir = "desc";
						},
						onLoadSuccess: function(data){
							var params={
			 	    			  operationFuncCode : "pagedQuery",
			 	    			  operationFuncName : "查看信访件分布情况本委模块的信访件轨迹",
			 	    			  operationBtnName : dateFlag,
			 	    			  operationDesc : "查看信访编号为"+row.currPetitionNo+"的轨迹",
			 	    			  operationTypeCode : OperationType.QUERY,
			 	    			  operationTypeName : OperationType.QUERY_NAME,
			 	    			  enableDataLog :true
			 	    		}
			 	    	    saveOperaLog(params);
						}
					}
				});
			},
			url:'<%=basePath%>/petition/petition-basic-info!queryPetitionInfo.action',
			queryParams:{
	 	    	swFlag : swFlag,
	 	    	dateFlag : dateFlag,
	 	    	startDateValue　: startDate,
	 	    	endDateValue :　endDate,
	 	    	bwRadio　: bwRadio
	 	    },
			border : false,
			nowrap :true,
			singleSelect : true,
			checkbox : false,
			fitColumns:true,
			pageSize : 10,
			frozenColumns : [[{
				field : "oid",
        		hidden : true
            }]],
			columns:[gridColumn()],
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
 	    			  operationFuncCode : "queryPetitionInfo",
 	    			  operationFuncName : "信访件分布情况查询本委信访件列表",
 	    			  operationBtnName : dateFlag,
 	    			  operationDesc : "信访件分布情况查询本委信访件列表",
 	    			  operationTypeCode : OperationType.QUERY,
 	    			  operationTypeName : OperationType.QUERY_NAME,
 	    			  enableDataLog :true
 	    		}
 	    	    saveOperaLog(params);
				//$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
			}
		}
	});
	</script>
  </body>
</html>