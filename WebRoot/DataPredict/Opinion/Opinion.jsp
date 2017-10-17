<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--  <%
	String swFlag = request.getParameter("swFlag");
	String dateFlag = request.getParameter("dateFlag");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
%> --%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>民意分析</title>
  	<base href="<%=basePath%>"> 	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/whole.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/sinosoftUI.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/jeasyui.extensions.datagrid.css">
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.sinosoftUI.master.js"></script>
	<script type="text/javascript" src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
  </head> 
 
  <body>
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>/DataPredict/Opinion/textbox.css">
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
		
	.dot{
      display: inline-block;
      width: 10px;height: 10px;
      border-radius: 50%;
      margin: 0 5px 0 0;
    }
	</style>
   <div class="bk">
    <div class="tie" style="overflow-y:auto;">
         <div idFlag="xffbDivA" class = "xffbDiv" style="padding-top:10px" id = "showTable"> 
         <!-- <div idFlag="xffbDiv" style="margin-left:10px;width:95%;height:331px"> -->
          <a  class="button deepblue" onclick = "batchIgnore()" style="width:50px;height:20px;line-height:20px;font-size:12px;margin-top:0px;margin-bottom: 1px;text-align:center;border:1px solid #49D9FE " plain="true" idFlag="search">忽略</a>
  	       <!-- 
  	       <a  class="button deepblue" onclick = "batchAddDic()" style="width:80px;height:28px;line-height:28px;font-size:16px;margin-top:4px;margin-bottom: 5px;text-align:center;border:1px solid #49D9FE " plain="true" idFlag="search">加入词典</a>
  	       -->
  	       <a  class="button deepblue" onclick = "mergeWord()" style="width:50px;height:20px;line-height:20px;font-size:12px;margin-top:0px;margin-bottom: 1px;text-align:center;border:1px solid #49D9FE " plain="true" idFlag="search">合并词</a>
  	       <a  class="button deepblue" onclick = "addClassS()" style="width:80px;height:20px;line-height:20px;font-size:12px;margin-top:0px;margin-bottom: 1px;text-align:center;border:1px solid #49D9FE " plain="true" idFlag="search">添加分类</a>
  	        
	       <div style="width:18px;height:18px;clear:both;float: right;margin-right:6px;margin-bottom:2px;" onmouseover="this.style.cursor='hand'">
	             <img  onclick="right()" src="<%=basePath%>/images/right.png">
	       </div>
	      
	     <div id="w" style = "display:none;" >
	       <jsp:include page="/DataPredict/Opinion/AddDict.jsp"/>
         </div>
         
         <div id="WordCloud" style = "display:none;" >
	       <jsp:include page="/DataPredict/Opinion/WordCloud.jsp"/>
         </div>
         
         <div id="EditWord" style = "display:none;" >
	       <jsp:include page="/DataPredict/Opinion/EditWord.jsp"/>
         </div>
           
         <div id="MergeWord" style = "display:none;" >
	       <jsp:include page="/DataPredict/Opinion/MergeWord.jsp"/>
         </div>
         <div id="AddClass" style = "display:none;" >
	       <jsp:include page="/DataPredict/Opinion/AddClass.jsp"/>
         </div>
         <div id="PbasicInfoList" style = "display:none;" >
	       <jsp:include page="/DataPredict/Opinion/PbasicInfoList.jsp"/>
         </div>
         
         
         <table idFlag="xffbA" id = "xfff"></table>
         </div>
          <div  id = "showTree" style = "display:none">
           <div style="width:100%;height:30px;clear:both;float: left; margin-top:0px;" onmouseover="this.style.cursor='hand'">
	             <img style="margin-left:15px;margin-top:5px;" onclick="left()" src="<%=basePath%>/images/left.png">
	             <div style="font-size:16px;color:#ffffff;text-align:center;margin-top:-10px">问题类别树</div>
	       </div>
	       <div style="width:190px;height:50px;clear:both;float: right;margin-right: 30px;margin-left:15px; margin-top:0px;" onmouseover="this.style.cursor='hand'">
	       
	       <span class="dot" style="background:#CB555B;"> </span><span style= "color:white;font-size:12px">词总数  > 50      &nbsp;&nbsp;&nbsp;词频 > 100</span></br>
	       <span class="dot" style="background:#C8A05A";"> </span><span style= "color:white;font-size:12px">词总数 20~50  &nbsp;词频 50~100</span></br>
	       <span class="dot" style="background:#94B271;"> </span><span style= "color:white;font-size:12px">词总数 0~20  &nbsp;&nbsp;&nbsp;词频 20~50 </span></br>
	       <span class="dot" style="background:white;"> </span><span style= "color:white;font-size:12px">词总数  0</span>
	       </div>
           <div id="c1"><div style= "height:22px"></div></div>
         </div>
      </div>
  </div>
  <input type = "hidden" id = "newWordHidd" value = ""></input>
  <input type = "hidden" id = "newWordIdHidd" value = ""></input>
  <input type = "hidden" id = "queTypeId"></input>
      </div>
  </div>
	<script type="text/javascript">
var xffbGrid = $.f("div","xffbDivA").f("table","xffbA");
	
	xffbGrid.datagridSinosoft({
		body:{
			
	 	   url:'/jubao/dataPredict/data-predict-opinion-info!getNewWordList.action',
	 	   queryParams:{
	 	    	swFlag : swFlag,
	 	    	dateFlag : dateFlag,
	 	    	startDateValue　: startDate,
	 	    	endDateValue :　endDate
	 	    },
			border : false,
			singleSelect : false,
			//pagination:false,
			fitColumns:true,
		     /* pagination : false, */  
			columns:[[{
        		field : "OID",
        		checkbox:'true',
        		width : 28,
        		align : "center",
        		/*
        		formatter:function(value,rec){  
        			return '<input type = "checkbox" name = "OID"></input>';    
        		}*/
        	},{
        		field : "NEWWORD",
        		title : "新词",
        		width : 90,
        		align : "center",
       			formatter:function(value,row,rec){  
           			//var btn = '<input id = "'+row.OID+'_input" class="text" value = "'+value+'"></input>  <a class="button deepblue" id = "'+row.OID+'" value = "'+row.EDITWORD+'" onclick="saveNewWord(this)" href="javascript:void(0)">保存</a>';    
                       //var btn = '<input id = "'+row.OID+'_input" class="text" value = "'+value+'"></input>  <a  class="button deepblue" id = "'+row.OID+'" value = "'+row.EDITWORD+'" onclick="saveNewWord(this)" style="width:50px;height:20px;line-height:20px;font-size:14px;margin-top:4px;margin-bottom: 5px;text-align:center;border:1px solid #49D9FE " plain="true">保存</a>';
           			var btn = '<span style = "font-size:14px;cursor: hand;" onclick="getBasic(this)">'+value+'</span>';
                       return btn;
           		}  
        	},{
        		field : "WORDFREQUENCY",
        		title : "词频",
        		width : 90,
        		align : "center",
        		hidden : false
        	}/* ,{
        		field : "WORDPART",
        		title : "词性",
        		width : 90,
        		align : "center"
        	} */,{
        		field : "EDITWORD",
        		title : "操作区",
        		align : "center",
        		formatter:function(value,row,rec){  
        			//var btn = '<input id = "'+row.OID+'_input" class="text" value = "'+value+'"></input>  <a class="button deepblue" id = "'+row.OID+'" value = "'+row.EDITWORD+'" onclick="saveNewWord(this)" href="javascript:void(0)">保存</a>';    
                    //var btn = '<input id = "'+row.OID+'_input" class="text" value = "'+value+'"></input>  <a  class="button deepblue" id = "'+row.OID+'" value = "'+row.EDITWORD+'" onclick="saveNewWord(this)" style="width:50px;height:20px;line-height:20px;font-size:14px;margin-top:4px;margin-bottom: 5px;text-align:center;border:1px solid #49D9FE " plain="true">保存</a>';
        			var btn = '<a  class="button deepblue" id = "'+row.OID+'" value = "'+row.NEWWORD+'" onclick="saveNewWord(this)" style="width:50px;height:20px;line-height:20px;font-size:12px;text-align:center;border:1px solid #49D9FE;color:#00BDE0 !important" plain="true">编辑</a> <a  class="button deepblue" id = "'+row.OID+'" value = "'+row.NEWWORD+'" onclick="addDic(this)" style="width:70px;height:20px;line-height:20px;font-size:12px;text-align:center;border:1px solid #49D9FE;color:#00BDE0 !important " plain="true" >加入词典</a> <a  class="button deepblue" id = "'+row.OID+'" value = "'+row.OID+'" onclick="ignore(this)" style="width:50px;height:20px;line-height:20px;font-size:12px;text-align:center;border:1px solid #49D9FE;color:#00BDE0 !important; " plain="true">忽略</a>';
                    return btn;
        		} 
        	}
        	
        	]],
			loadFilter:function(data){
				data.rows=data.result;
				return data;
			},
			 onClickRow: function (rowIndex, rowData) {
                 $(this).datagrid('unselectRow', rowIndex);
             },
             onBeforeLoad:function(param){
 				param.start=(param.page-1)*param.rows;
 				param.limit=param.rows;
 			},
			onLoadSuccess: function(data){
				$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 500 });
				$(".pagination").css("display","none");
				 //加载完毕  添加日志
	        	 var params={
	         			operationFuncCode : "getNewWordList",
	         			operationFuncName : "查询民意分析查询新词列表",
	         			operationBtnName : dateFlag,
	         			operationDesc : "民意分析词典表",
	         			operationTypeCode : OperationType.QUERY,
	         			operationTypeName : OperationType.QUERY_NAME,
	         			enableDataLog :true
	         		};
	      	    	saveOperaLog(params);
	         
			}
		}
	});
	$("#showTable").css("display","block");
	
	//新词钻取信访列表
	var getBasicListName;
	function getBasic(ithis) {
		var getBasicListName = $(ithis).text();
		if(swFlag == 'qs') {
			//if(categoryMainObjPb){
			//	categoryMainObjPb.panel("options").newWord=getBasicListName;
			//} else {
	   		categoryMainObjPb = $.f("div","categoryMainPb").window({
	    		    width:800, 
	    		    height:500,
	    		    title:'信访分布情况',
	    		    modal:true,
	    			maximizable:false,
	    			minimizable:false,
	    			collapsible:false,
	    			newWord:getBasicListName,
	    			closed:true,
	    			onClose : function(){
	    				categoryMainObjPb.panel("options").newWord="";
		 		    },
	    			onOpen:function(){
	    				categoryMainWinPb.init();
	    			}
	    		});
			 //}
	   		 categoryMainObjPb.window("open");
			} else {
				//本委
				//if(categoryMainObjOpinion){
				//	 categoryMainObjOpinion.panel("options").dateFlag = dateFlag;
		    	//	 categoryMainObjOpinion.panel("options").newWord=getBasicListName;
		    	//	 categoryMainObjOpinion.panel("options").irFlag="1";
	   	 	    //}else{
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
		    			irFlag:"1",
		    			newWord:getBasicListName,
		    			onOpen:function(){
		    				categoryMainWinOpinion.init();
		    			},
		    			onClose : function(){
			 			categoryMainObjOpinion.panel("options").newWord="";
			    		categoryMainObjOpinion.panel("options").dateFlag="";
			    		categoryMainObjOpinion.panel("options").irFlag="";
			 		  }
		    		});
	    		//}
	    		categoryMainObjOpinion.window("open");
			}
		
	}
	var arrOids = [];
	var arrVals = [];
	//合并词
	function mergeWord() {
		arrOids = [];
		arrVals = [];
		$('input[name="OID"]:checked').each(function(){ 
			arrOids.push($(this).val());
			arrVals.push($(this).parent().parent().next("td").text());
		});
		if(arrOids ==null || arrOids.length <= 0) {
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   请选择词！', 3000);
		} else {
			//$("#MergeWord").css("display","block");
			//if(categoryMainObjMerge){
				//categoryMainObjMerge.panel("options").swFlag=swFlag;
				//categoryMainObjMerge.panel("options").dateFlag=dateFlag;
				//categoryMainObjMerge.panel("options").startDate=startDate;
				//categoryMainObjMerge.panel("options").endDate=endDate;
				//categoryMainObjMerge.panel("options").bwRadio=bwRadio;
				//categoryMainObjMerge.panel("options").name=param.name;
 		  //}else{
	   		categoryMainObjMerge = $.f("div","categoryMainMerge").window({
	    		    width:500, 
	    		    height:200,
	    		    title:'合并新词',
	    		    modal:true,
	    			maximizable:false,
	    			minimizable:false,
	    			collapsible:false,
	    			closed:true,
	    			onOpen:function(){
	    				categoryMainWinMerge.init();
	    			}
	    		});
 		 //}
	   	 categoryMainObjMerge.window("open");
 		 
	   		/*
			$("#w").window({
	    		title:'加入词典',
	    		href:'<%=basePath%>/DataPredict/Opinion/MergeWord.jsp',
	    		width:850,
	    		height:490,
	    		collapsible:false,
	    		minimizable:false,
	    		maximizable:false,
	    		closable:true,
	   		});
	   		*/
		}
		
	}
	//添加分类
	function addClassS() {
		//$("#AddClass").css("display","block");
		//if(categoryMainObjClass){
			//categoryMainObjMerge.panel("options").swFlag=swFlag;
			//categoryMainObjMerge.panel("options").dateFlag=dateFlag;
			//categoryMainObjMerge.panel("options").startDate=startDate;
			//categoryMainObjMerge.panel("options").endDate=endDate;
			//categoryMainObjMerge.panel("options").bwRadio=bwRadio;
			//categoryMainObjMerge.panel("options").name=param.name;
		  //}else{
   		   categoryMainObjClass = $.f("div","categoryMainClass").window({
    		    width:550, 
    		    height:350,
    		    title:'添加分类',
    		    modal:true,
    			maximizable:false,
    			minimizable:false,
    			collapsible:false,
    			closed:true,
    			onOpen:function(){
    				categoryMainWinClass.init();
    			}
    		});
		 // }
   		categoryMainObjClass.window("open");
	}
	
	 function addClass() {
		 $("#w").window({
	    		title:'添加问题类别',
	    		href:'<%=basePath%>/DataPredict/Opinion/AddClass.jsp',
	    		width:850,
	    		height:490,
	    		collapsible:false,
	    		minimizable:false,
	    		maximizable:false,
	    		closable:true,
	   		}); 
	 }
	//加入词典
	function addDic(ithis) {
		var oid=$(ithis).attr("id");
		var val=$(ithis).attr("value");
		$("#newWordIdHidd").val(oid);
		$("#newWordHidd").val(val);
		//$("#w").css("display","block");
		//if(categoryMainObjDict){
		//	categoryMainObjDict.panel("options").swFlag='222222111dcdcf';
			//categoryMainObjMerge.panel("options").swFlag=swFlag;
			//categoryMainObjMerge.panel("options").dateFlag=dateFlag;
			//categoryMainObjMerge.panel("options").startDate=startDate;
			//categoryMainObjMerge.panel("options").endDate=endDate;
			//categoryMainObjMerge.panel("options").bwRadio=bwRadio;
			//categoryMainObjMerge.panel("options").name=param.name;
		//  }else{
		categoryMainObjDict = $.f("div","categoryMainDict").window({
 		    width:550, 
 		    height:350,
 		    title:'加入词典',
 		    modal:true,
 			maximizable:false,
 			minimizable:false,
 			collapsible:false,
			swFlag:'112wfcv',
 			closed:true,
 			onOpen:function(){
 				categoryMainWinDict.init();
 			}
 		});
		//  }
 	 categoryMainObjDict.window("open");
 	 /*
		$("#w").window({
    		title:'加入词典',
    		href:'<%=basePath%>/DataPredict/Opinion/AddDict.jsp',
    		width:850,
    		height:490,
    		collapsible:false,
    		minimizable:false,
    		maximizable:false,
    		closable:true,
   		});*/
		//updateIgnore(oid,"add");
	}
	//加入词典 批量
	function batchAddDic() {
		var oids = "";
		$('input[name="OID"]:checked').each(function(){ 
			oids += $(this).val() + ",";
		});
		oids = oids.substring(0,oids.length - 1);
		//alert(oids);
		updateIgnore(oids,"add");
	}
	
	//忽略词
	function ignore(ithis) {
		var oid=$(ithis).attr("id");
		updateIgnore(oid,"ignore");
	}
	//忽略词 批量
	function batchIgnore() {
		arrOids = [];
		arrVals = [];
		$('input[name="OID"]:checked').each(function(){ 
			arrOids.push($(this).val());
			arrVals.push($(this).parent().parent().next("td").text());
		});
		if(arrOids ==null || arrOids.length <= 0) {
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   请选择词！', 3000);
		} else {
			var oids = "";
			$('input[name="OID"]:checked').each(function(){ 
				oids += $(this).val() + ",";
			});
			oids = oids.substring(0,oids.length - 1);
			//alert(oids);
			updateIgnore(oids,"ignore");
			
		}
	}
	function updateIgnore(oid,otype) {
		$.ajax({
			url:'/jubao/dataPredict/data-predict-opinion-info!updateIgnore.action', 
			type : "post",
			data:{
				swFlag : swFlag,
	 	    	dateFlag : dateFlag,
	 	    	startDateValue　: startDate,
	 	    	endDateValue :　endDate,
				oid : oid,
				otype : otype
			},
			success:function(d){
				var params={
						operationFuncCode : "updateIgnore",
						operationFuncName : "忽略新词",
						operationBtnName : "保存",
						operationResultCode : "1",
						operationResultName : "成功",
						operationDesc : "忽略新词",
						operationTypeCode : OperationType.MODIFY,
						operationTypeName : OperationType.MODIFY_NAME,
						operationDataDesc : "忽略新词",
						operationDataValue : "oid:"+oid,		
						enableDataLog :true
						}
					saveOpeationRecord(params);
				$("#xfff").datagrid('reload');
				dialog.showMiniDialog("self-style", '<i class="icon-success"></i>   忽略成功！', 3000);
			},
			error:function(){ 
				dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   数据加载失败！', 3000);
			}  
		}); 
	}
	
	
	//保存新词 编辑
	function saveNewWord(ithis) {
		
		var oid=$(ithis).attr("id");
		var val=$(ithis).attr("value");
		$("#newWordIdHidd").val(oid);
		$("#newWordHidd").val(val);
		 
		//$("#EditWord").css("display","block");
		//if(categoryMainObjEdit){
			//categoryMainObjMerge.panel("options").swFlag=swFlag;
			//categoryMainObjMerge.panel("options").dateFlag=dateFlag;
			//categoryMainObjMerge.panel("options").startDate=startDate;
			//categoryMainObjMerge.panel("options").endDate=endDate;
			//categoryMainObjMerge.panel("options").bwRadio=bwRadio;
			//categoryMainObjMerge.panel("options").name=param.name;
		 // }else{
   		categoryMainObjEdit = $.f("div","categoryMainEdit").window({
    		    width:500, 
    		    height:200,
    		    title:'编辑新词',
    		    modal:true,
    			maximizable:false,
    			minimizable:false,
    			collapsible:false,
    			closed:true,
    			onOpen:function(){
    				categoryMainWinEdit.init();
    			}
    		});
		//  }
   		categoryMainObjEdit.window("open");
   		/*
		$("#w").window({
    		title:'编辑新词',
    		href:'<%=basePath%>/DataPredict/Opinion/EditWord.jsp',
    		width:450,
    		height:290,
    		collapsible:false,
    		minimizable:false,
    		maximizable:false,
    		closable:true,
   		});
		*/
		
		/*
		var oid=$(ithis).attr("id");
		var newVal = $("#" + oid + "_input").val();
		if(newVal == null || newVal == '') {
			alert("不能为空");
		} else if($(ithis).val() == newVal) {
			alert("未编辑新词");
		} else {
			$.ajax({
				url:'/jubao/dataPredict/data-predict-opinion-info!saveNewWord.action',  
				type : "post",
				data:{
					swFlag : swFlag, 
		 	    	dateFlag : dateFlag,
		 	    	startDateValue　: startDate,
		 	    	endDateValue :　endDate,
					oid : oid,
					newVal : newVal
				},
				success:function(d){
					$("#xfff").datagrid('reload'); 			 	    	
				},
				error:function(){  
					alert("数据加载失败！");  
				}  
			}); 
		}
		*/
	}
	
	function Word() {
		$("#w").window({
    		title:'加入词典',
    		href:'<%=basePath%>/DataPredict/Opinion/WordCloud.jsp',
    		width:850,
    		height:490,
    		collapsible:false,
    		minimizable:false,
    		maximizable:false,
    		closable:true,
   		});
	}
	
	$("#open").click(function(){
   		$("#w").window({
    		title:'加入词典',
    		href:'<%=basePath%>/DataPredict/Opinion/AddDict.jsp',
    		width:850,
    		height:490,
    		collapsible:false,
    		minimizable:false,
    		maximizable:false,
    		closable:true,
   		});
   	}); 
	
	function left() {
		$("#showTree").css("display","none");
		$("#showTable").css("display","block");
		
		
	} 
	function right() {
		$("#showTable").css("display","none");
		$("#showTree").css("display","block");
	}
	$.ajax({
		url:'/jubao/dataPredict/data-predict-opinion-info!getQuesTypeBean.action', 
		type : "post",
		data:{
			swFlag : swFlag,
 	    	dateFlag : dateFlag,
 	    	startDateValue　: startDate,
 	    	endDateValue :　endDate
		},
		success:function(data){
			 //加载完毕  添加日志
       	 var params={
        			operationFuncCode : "getQuesTypeBean",
        			operationFuncName : "查询民意分析中所有问题类别树",
        			operationBtnName : dateFlag,
        			operationDesc : "民意分析词典类别表",
        			operationTypeCode : OperationType.QUERY,
        			operationTypeName : OperationType.QUERY_NAME,
        			enableDataLog :true
        		};
     	    	saveOperaLog(params);
			
			data = eval('('+data+')');
			var allColor = "";
			function drawNode(cfg, group, allColor, collapsed, isLeaf) {
		        var x = cfg.x;
		        var y = cfg.y;
		        var pointSize = 5;
		        var width = cfg.size + 10;
		        var height = 18 + 2;
		        //var label = cfg.label;
		        var shape = group.addShape('rect', {
		          attrs: {
		            x: x,
		            y: y - height / 2 ,
		            width: width,
		            height: height,
		            fill: allColor,
		            cursor: isLeaf ? '' : 'pointer',
		            stroke: allColor
		          }
		        });
		        if (!isLeaf) {
		          x = x - pointSize;
		          group.addShape('circle', {
		            attrs: {
		              r: pointSize,
		              x: x,
		              y: y,
		              fill: allColor,
		              stroke: allColor // 可以直接设置颜色 cfg.color，也可以使用映射
		            }
		          });
		          var path = [];
		          path.push(['M', x - pointSize/2, y]);
		          path.push(['L', x + pointSize/2, y]);
		          if (collapsed) {
		            path.push(['M', x, y - pointSize/2]);
		            path.push(['L', x, y + pointSize/2]);
		          }
		          group.addShape('path', {
		            attrs: {
		              path: path,
		              stroke: 'black'
		            }
		          });
		        }
		        return shape;
		      }
		      G2.Shape.registShape('point', 'collapsed', {
		        drawShape: function(cfg, group) {
		          return drawNode(cfg, group,allColor, true)
		        }
		      });
		      G2.Shape.registShape('point', 'expanded', {
		        drawShape: function(cfg, group) {
		          return drawNode(cfg, group,allColor, false);
		        }
		      });
		      G2.Shape.registShape('point', 'leaf', {
		        drawShape: function(cfg, group) {
		          return drawNode(cfg, group, allColor, false, true);
		        }
		      });
		      var Layout = G2.Layout;
		     
		      // 使用layout，用户可以自己编写自己的layout
		      // 仅约定输出的节点 存在 id,x，y字段即可
		      var layout = new Layout.Tree({
		        nodes: data
		      });
		      
		      var dx = layout.dx;
		      var nodes = layout.getNodes();
		      var edges = layout.getEdges();
		      var Stat = G2.Stat;
		      var chart = new G2.Chart({
		        id: 'c1',
		        forceFit: true,
		        height: 450,
		        plotCfg: {
		          margin: [80,30]
		        },
		        animate: true
		      });
		      // 不显示title
		      chart.tooltip({
		        title: null,
		          map: {    
				    name: '名称', // 为数据字段名时则显示该字段名对应的数值，常量则显示常量
				    value:'name',
				    lable:{fill:'red'}
				  }
		      });
		      chart.legend('children', false);
		      chart.legend('name', false);
		      chart.legend(false);
		      renderTree(nodes, edges, dx);
		      
		      function renderTree(nodes, edges, dx) {
		        chart.clear();
		        var height = Math.max(580, 26 / dx); // 最小高度 500
		        chart.changeSize(700, height);
		        // 首先绘制 edges，点要在边的上面
		        // 创建单独的视图
		        var edgeView = chart.createView();
		        edgeView.source(edges);
		        edgeView.coord().transpose().scale(1, -1); //
		        edgeView.axis(false);
		        edgeView.tooltip(false);
		        // Stat.link 方法会生成 ..x, ..y的字段类型，数值范围是 0-1
		        edgeView.edge()
		          .position(Stat.link('source*target',nodes))
		          .shape('smooth')
		          .color('#ccc');
		        function strLen(str) {
		          var len = 0;
		          for (var i = 0; i < str.length; i ++) {
		            if(str.charCodeAt(i) > 0 && str.charCodeAt(i) < 128) {
		              len ++;
		            } else {
		              len += 2;
		            }
		          }
		          return len;
		        }
		        // 创建节点视图
		        var nodeView = chart.createView();
		        nodeView.coord().transpose().scale(1, -1); //'polar'
		        nodeView.axis(false);
		        // 节点的x,y范围是 0，1
		        // 因为边的范围也是 0,1所以正好统一起来
		        nodeView.source(nodes, {
		          x: {min: 0,max:1},
		          y: {min: 0, max:1},
		          value: {min: 0}
		        },['id','x','y','name','children','collapsed','color']); // 由于数据中没有 'collapsed' 字段，所以需要设置所有的字段名称
		        nodeView.point().position('x*y').color('color', function(color) {
			         return color;
			        }).size('name', function(name) {
		          var length = strLen(name);
		          return length * 6 + 5 * 2;
		        }).label('name', {
		          offset: 6,
		          label: { // 所有文本共享的配置信息
		             fill:'black'
		             
		          },
		          labelEmit: true,
		          
		        }).shape('children*collapsed*color', function(children,collapsed,color) {
		          allColor = color;
		          if (children) {
		            if (collapsed) {
		              return 'collapsed';
		            } else {
		              return 'expanded';
		            }
		          }
		          return 'leaf';
		        });
		        chart.render();
		      }		      
		      chart.on('plotclick', function(ev){
		        var shape = ev.shape;
		        if (shape) {
		          var obj = shape.get('origin');
		          var id = obj._origin.id;
		          console.info(id)
		          var name =  obj._origin.name+ "词云";
		          //var name = "词云图";
		          if(obj._origin.children.length == 0) {
		        	  //alert(obj._origin.dictType);
		        	 $("#queTypeId").val(obj._origin.dictType);
		        	 //$("#WordCloud").css("display","block");
		        	 $("#dci").empty();
		        	 
		      		categoryMainObjWord = $.f("div","categoryMainWord").window({
		       		    width:850, 
		       		    height:450,
		       		    title:name,
		       		    modal:true,
		       			maximizable:false,
		       			minimizable:false,
		       			collapsible:false,
		       			word:obj._origin.dictType,
		       			closed:true,
		       			onOpen:function(){
		       				categoryMainWinWord.init();
		       			},
		       			onClose : function(){
		       				categoryMainObjWord.panel("options").word="";
				 		}
		       		});
		     		  
		      		categoryMainObjWord.window("open");
		          } else {
		        	  initNode(id);
		        	  /* var node = layout.findNode(id);
			          if (node && node.children) {
			            node.collapsed = !node.collapsed ? 1 : 0;
			            layout.reset();
			            nodes = layout.getNodes();
			            edges = layout.getEdges();
			            dx = layout.dx;
			            // edgeView.changeData(edges);
			            renderTree(nodes, edges, dx);
			          }  */
		          }
		        }
		      });
 
	          function initNode(nodeId) {
	        	  var node = layout.findNode(nodeId);
		          if (node && node.children) {
		            node.collapsed = !node.collapsed ? 1 : 0;
		            layout.reset();
		            nodes = layout.getNodes();
		            edges = layout.getEdges();
		            dx = layout.dx;
		            // edgeView.changeData(edges);
		            renderTree(nodes, edges, dx);
		          }
	          }
	          //初始化时加减号不好使问题
	          initNode(0);
	          initNode(0);
		      $("#showTree").css("display","none");
		},
		error:function(){  
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   数据加载失败！', 3000);
		}  
	}); 
	</script>
  </body>
</html>