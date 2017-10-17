<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>问题类别的变化情况</title>
<base href="<%=basePath%>">
<script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
<script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
<script src="<%=basePath%>/publicresource/js/g2.js"></script>
<script src="<%=basePath%>/DataAnalysis/js/gridColumns.js"></script>
</head>
<body>
	<style>
 .pagination-num,.pagination-page-list{
		    background:#BBD0DE;
		    font-size:14px;
		    text-align:center
	    }
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
	     .xffbDiv{
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
<div idFlag="categoryMainxzx" style="display: none">
	    <div style="width:98%;margin-left:1%;margin-right:1%;height:420px;background-size:100%;padding-top:10px;">
		    <div style="height:400px;background-size:98%;padding-top:10px">
		         <div style="width:100%;height:300px"> 
		        	  <div idFlag="xffbDivx"  class="xffbDiv" style="width:100%;height:410px">
		        	  <table idFlag="xffbxx" id="xffbx"></table>
		        	  </div>
		         </div>
		    </div>
	    </div>
    </div>
<input id = "oid" type="hidden">
<script type="text/javascript">
var categoryMainObjxx;
	$.SUI("categoryMainWinx");
	categoryMainWinx = {
			init : function(){
			var swFlag=categoryMainObjxx.panel("options").swFlag;
			var dateFlag=categoryMainObjxx.panel("options").dateFlag;
			var startDate=categoryMainObjxx.panel("options").startDate;
			var endDate=categoryMainObjxx.panel("options").endDate;
			var reName=categoryMainObjxx.panel("options").reName;
			
	var xffbGrid = $.f("div","xffbDivx").f("table","xffbxx");
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
							filterTxt : 'PETITIONNO',
							filterValue : row.PETITIONNO
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
			 	    			  operationDesc : "查看信访编号为"+row.PETITIONNO+"的轨迹",
			 	    			  operationTypeCode : OperationType.QUERY,
			 	    			  operationTypeName : OperationType.QUERY_NAME,
			 	    			  enableDataLog :true
			 	    		}
			 	    	    saveOperaLog(params);
						}
					}
				});
			},
			url:'/jubao/dataPredict/data-predict-fpm-info!alarmOther.action',
			queryParams:{
	 	    	swFlag : swFlag,
	 	    	dateFlag : dateFlag,
	 	    	startDateValue　: startDate,
	 	    	endDateValue :　endDate,
	 	    	reName : reName,
	 	    },
			border : false,
			nowrap :true,
			singleSelect : true,
			checkbox : false,
			fitColumns:true,
			frozenColumns : [[{
				field : "OID",
        		hidden : true
            }]],
			columns:[[{
        		field : "PETITIONNO",
        		title : "信访编号",
        		width : 90,
        		align : "center",
        		formatter:function(value,row,rec){  
        			var petitionNo;
					if(value!=null)
						petitionNo = value.substring(0,6)+'-'+value.substring(value.length-6);
					else
						petitionNo = '';
	
					return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:showPetitionInfo(\""+row.PETITIONNO+"\",\""+row.REGIONCODE+"\",\""+value+"\")'>"+petitionNo+"</a>";
// 					return "<a style=\"text-decoration:none;font-size: 12px;\" class=\"rowaStyle\"  href='javascript:showPetitionInfo(\""+row.petitionNo+"\",\""+row.regionCode+"\",\""+value+"\")'>"+petitionNo+"</a>";
        		} 
        	},{
        		field : "REGIONCODE",
        		title : "机构编码",
        		width : 0,
        		align : "center",
        		hidden : true,
        	},{
        		field : "ISSUETYPENAME",
        		title : "问题类别",
        		width : 90,
        		align : "center",
        	},{
        		field : "ACCUSERNAME",
        		title : "反映人",
        		width : 80,
        		align : "center",
        	},{
        		field : "ACCUSEDS",
        		title : "被反映人",
        		width : 80,
        		align : "center",
        	},{
        		field : "ISSUECONTENT",
        		title : "问题描述",
        		align : "center",
        		width : 80
        	},{
        		field : "DEALTYPENAME",
        		title : "处理方式",
        		width : 90,
        		align : "center"
        	},{
        		field : "SDEALTYPE",
        		title : "应处理方式",
        		align : "center",
        		width : 90
        	},{
        		field : "OPERATINGAREA",
        		title : "操作区",
        		align : "center",
        		width : 100,
        		formatter:function(value,row,rec){  
        			//var btn = '<input id = "'+row.OID+'_input" class="text" value = "'+value+'"></input>  <a class="button deepblue" id = "'+row.OID+'" value = "'+row.EDITWORD+'" onclick="saveNewWord(this)" href="javascript:void(0)">保存</a>';    
                    //var btn = '<input id = "'+row.OID+'_input" class="text" value = "'+value+'"></input>  <a  class="button deepblue" id = "'+row.OID+'" value = "'+row.EDITWORD+'" onclick="saveNewWord(this)" style="width:50px;height:20px;line-height:20px;font-size:14px;margin-top:4px;margin-bottom: 5px;text-align:center;border:1px solid #49D9FE " plain="true">保存</a>';
        			
                    
                    if(row.OPERATINGAREA == "1"){
                    	var btn = '<a  class="button deepblue"  id = "'+row.OID+'" value = "'+row.OPERATINGAREA+'"  style="width:50px;height:20px;line-height:20px;font-size:12px;text-align:center;border:1px solid #49D9FE ;color:#9d9d9d !important" plain="true">确认</a>';
                    } else {
                        var btn = '<a  class="button deepblue"  id = "'+row.OID+'" value = "'+row.OPERATINGAREA+'" onclick="unconfirmed(this)" style="width:50px;height:20px;line-height:20px;font-size:12px;text-align:center;border:1px solid #49D9FE;color:#00BDE0 !important" plain="true">未确认</a>';
                    }
                    return btn;
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
				//$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
				var params={
 	    			  operationFuncCode : "alarmOther",
 	    			  operationFuncName : "区，派驻告警列表",
 	    			  operationBtnName : dateFlag,
 	    			  operationDesc : "区，派驻告警列表",
 	    			  operationTypeCode : OperationType.QUERY,
 	    			  operationTypeName : OperationType.QUERY_NAME,
 	    			  enableDataLog :true
 	    		}
 	    	    saveOperaLog(params);
			}
		}
	});
	}
	}
	</script>
	<script>
	//确认
	function unconfirmed(ithis) {
		var oid=$(ithis).attr("id");
		$.ajax({
			url:'/jubao/dataPredict/data-predict-fpm-info!updateUnconfirmed.action', 
			type : "post",
			data:{
				swFlag : swFlag,
	 	    	dateFlag : dateFlag,
	 	    	startDateValue　: startDate,
	 	    	endDateValue :　endDate,
				oid : oid,
			},
			success:function(d){
			//修改
			var params={
				operationFuncCode : "updateUnconfirmed",
				operationFuncName : "是否操作",
				operationBtnName : "保存",
				operationResultCode : "1",
				operationResultName : "成功",
				operationDesc : "是否操作",
				operationTypeCode : OperationType.MODIFY,
				operationTypeName : OperationType.MODIFY_NAME,
				operationDataDesc : "是否操作",
				operationDataValue : "oid:"+oid,		
				enableDataLog :true
				}
			saveOpeationRecord(params);
				$("#xffbx").datagrid('reload');
				dialog.showMiniDialog("self-style", '<i class="icon-success"></i>   确认成功！', 3000);
			},
			error:function(){ 
				dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   数据加载失败！', 3000);
			}  
		});
	}
	
	
	function unconfirmedId(ithis) {
		var oid=$(ithis).attr("id");
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
		 			 petitionInfoWin.init(oid.split(',')[1], oid.split(',')[0], oid.split(',')[1]);
		 		  }
	 		});
			petitionInfoWinObj.window("open");
	}
	</script>
</body>
</html>