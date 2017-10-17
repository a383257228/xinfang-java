<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
  	</style>
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
    <div idFlag="Top5InfoWin" style="display: none">
         <div style="margin-left:10px;width:98%;height:550px"> 
         	<div idFlag="topFindPanel" style="margin-bottom:10px;">
         	 	  <span style="color:#BBD0DE;">反映人姓名:</span><input idFlag="accuserName" class="sinosoft-textbox" style="width:200px">
				  <span style="color:#BBD0DE;">被反映人姓名:</span><input idFlag="accusedName" class="sinosoft-textbox" style="width:200px">
				  
				  <a href="javascript:void(0)"class="button deepblue" style="width:50px;margin-left:10px;text-align:center;height:26px;line-height:26px;color:#062852;background:#49D9FE;font-size:14px" onclick="top5InfoGrid.query()">查询</a>
				  <a href="javascript:void(0)"class="button write" style="width:50px;margin-left:10px;text-align:center;font-size:14px"  onclick="top5InfoGrid.reset()">重置</a>
         	 </div>
         	 <div style="height:510px;">
	        	   <table idFlag="top5InfoGrid"></table>
         	 </div>
         </div>
	    <jsp:include page="/DataAnalysis/PetitionInfo/Assemble.jsp"/>
    </div>
	<script type="text/javascript">
	var top5InfoWinObj;
	$.SUI("top5InfoGrid");
	var names;
	var orgCode;
	var index;
	
	top5InfoGrid = {
		query : function(){
			var accusedName;
			var accuserName;
			//被反映人姓名
			if($.f("div", "topFindPanel").f("input","accusedName").textbox('getValue')){
				accusedName = $.f("div", "topFindPanel").f("input","accusedName").textbox("getValue");
			}
			//反映人姓名
			if($.f("div", "topFindPanel").f("input","accuserName").textbox('getValue')){
				accuserName = $.f("div", "topFindPanel").f("input","accuserName").textbox("getValue");
			}
			var params = {
					swFlag : swFlag,
		 	    	dateFlag : dateFlag,
		 	    	startDateValue　: startDate,
		 	    	endDateValue :　endDate,
		 	    	accusedName : accusedName,
		 	    	accuserName : accuserName,
		 	    	orgCode : orgCode,
		 	    	index : index,
		 	    	issueTypeName : names
			};
			//将参数对象赋值给列表参数
			$.f("div","Top5InfoWin").f("table","top5InfoGrid").datagridSinosoft("gridoptions").queryParams = params;
			$.f("div","Top5InfoWin").f("table","top5InfoGrid").datagridSinosoft("load");
		},
		reset : function(){
			$.f("div", "topFindPanel").f("input","accusedName").textbox('setValue',"");
			$.f("div", "topFindPanel").f("input","accuserName").textbox('setValue',"");
		},
		init : function(swFlag,dateFlag,startDate,endDate,code,index,issueTypeName){
			names = issueTypeName;
			orgCode=code;
			index = index;
			var top5InfoGrid = $.f("div","Top5InfoWin").f("table","top5InfoGrid");
			top5InfoGrid.expandGridSinosoft({
				body:{
					detailFormatter: function(index,row){
						return "<div id=\""+row.itemid+index+"topDiv\" style=\"width:95%\"></div>";
					},
					onExpandRow : function(index, row) {
						$("#" + row.itemid + index+"topDiv").html("<div id=\""+row.itemid+index+"topGrid\"  class=\"sinosoft-panel\" style=\"width:100%\"></div>");
						$.parser.parse("#" + row.itemid + index+"topDiv");
						$("#"+row.itemid+index+"topGrid").edatagridSinosoft({
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
								height : 145,
								pagination : true,
								singleSelect : false,
								pageSize : 3 ,
								frozenColumns : [[{
									field : 'itemid',
									align : 'center',
									hidden : true
								}]],
								columns : [[{
											field : 'regionName',
											title : "所属机构",
											width : 100,
											align : 'center'
										}, {
											field : 'activityNoName',
											title : "状态",
											width : 100,
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
									param.start = (param.page - 1) * param.rows;
									param.limit = param.rows;
									param.sort="createDate";
									param.dir = "desc";
								},
								pageList : [3, 6, 9]
							}
						});
					},
					url:'<%=basePath%>/petition/petition-basic-info!queryLetterInfo.action',
					queryParams:{
			 	    	swFlag : swFlag,
			 	    	dateFlag : dateFlag,
			 	    	startDateValue　: startDate,
			 	    	endDateValue :　endDate,
			 	    	orgCode : code,//问题类别
			 	    	index : index,//自收件 、转交办
			 	    	issueTypeName : issueTypeName//问题类别
			 	    },
					border : false,
					nowrap :true,
					singleSelect : true,
					checkbox : false,
					fitColumns:false,
					pageSize : 15,
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
						//$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
					},
					pageList : [15, 30, 45]
				}
			});
		},
		showPetitionInfo : function(petitionNo,regionCode,currPetitionNo){
			petitionInfoWinObj = $.f("div","assmble").window({
		 		  width:825, 
		 		  height:600,
		 		  modal:true,
		 		  title : "信访件信息",
		 		  maximizable:false,
		 		  minimizable:false,
		 		  collapsible:false,
		 		  closed:true,
		 		  onOpen:function(){
		 			 petitionInfoWin.init(petitionNo,regionCode,currPetitionNo);
		 		  }
	 		});
			petitionInfoWinObj.window("open");
		}
	}
	</script>
  </body>
</html>