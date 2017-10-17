<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>"> 	
  </head> 
  <body>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/window.css">
	  <style>
	    /* tab标签 */
	    .tabs{padding:0;}
	    .tabs li{margin:0;}
	    ul li {
	    width:100px;
	    line-height:35px; 
	    font-size:14px;
	    text-align:center;
	    }
	    .active{
	    background:#01BDC8;
	    }
	    a{
	    color:#fff;
	    }
	  </style>
	
	<div idFlag="assmble" style="display: none;">
	     <div class="container" > 
	        <ul class="tabs" style="border:1px solid #01BDC8;width:500px;height:35px;margin-top:10px;margin-left:10px"> 
		         <li class="active"><a href="#tab1">信访件信息</a></li> 
		         <li><a href="#tab2">批示信息</a></li> 
		         <li><a href="#tab3">办理信息</a></li> 
		         <li><a href="#tab4">多媒体信息</a></li> 
		         <li><a href="#tab5">扫描件信息</a></li> 
	        </ul> 
		    
		    <div class="tab_container"> 
	          	<div id="tab1" class="tab_content" >
	                  <iframe id="infoIframe" width="805" height="500" frameborder=0></iframe>  
	            </div> 
	            <div id="tab2" class="tab_content" style="display: none; "> 
		             <iframe id="instructionIframe" width="805" height="500" frameborder=0/></iframe>  
	            </div>
	            <div id="tab3" class="tab_content" style="display: none; "> 
	                 <iframe id="handleIframe" width="805" height="500" frameborder=0></iframe> 
	            </div> 
	            <div id="tab4" class="tab_content" style="display: none; "> 
	            	 <iframe id="handleIframe" src="<%=basePath%>/DataAnalysis/PetitionInfo/MultiMedia.jsp" width="805" height="500" frameborder=0></iframe>
	            </div> 
	            <div id="tab5" class="tab_content" style="display: none; "> 
	                 <iframe id="scanIframe" src="<%=basePath%>/DataAnalysis/PetitionInfo/Scanning.jsp" width="805" height="500" frameborder=0></iframe> 
	           </div> 
		    </div>
        </div> 
    </div>
	 <script>
		 var petitionInfoWinObj;
		 $.SUI("petitionInfoWin");
		 petitionInfoWin = {
			 init : function(){
				 var petitionNo= petitionInfoWinObj.panel("options").petitionNo;
				 var regionCode=	petitionInfoWinObj.panel("options").regionCode;
				 var currPetitionNo=	petitionInfoWinObj.panel("options").currPetitionNo;
				 $(".tab_content").hide(); //隐藏全部的tab菜单内容
				 petitionInfoWinObj.find("ul.tabs li:first").siblings().removeClass("active");
				 petitionInfoWinObj.find("ul.tabs li:first").addClass("active").show(); //对第一个li标签添加class="active属性"
				 $(".tab_content:first").show(); //显示第一个tab内容 
				 //默认显示基本信息
				 document.getElementById('infoIframe').src = '<%=basePath%>/petition/petition-basic-info!getBasicInfo.action?flag=0&petitionNo=' + petitionNo+'&regionCode='+regionCode;
				 var params={
					operationFuncCode : "getBasicInfo",
					operationFuncName : "点击信访编号查看信访件信息",
					operationBtnName : dateFlag,
					operationDesc : "查看信访编号为"+currPetitionNo+"的信访件信息",
					operationTypeCode : OperationType.QUERY,
					operationTypeName : OperationType.QUERY_NAME,
					enableDataLog :true
				 }
				 saveOperaLog(params);
				 $("ul.tabs li").click(function() {
					  $("ul.tabs li").removeClass("active"); //移除class="active"属性
					  $(this).addClass("active"); //添加class="active"到选择标签中 
					  
					  $(".tab_content").hide(); //隐藏全部标签内容  
					  var activeTab = $(this).find("a").attr("href"); //找到所属属性值来识别活跃选项卡和内容 
					  $(activeTab).fadeIn(); //使内容消失 
					  
					  if($(this).index()==0){
						  document.getElementById('infoIframe').src = '<%=basePath%>/petition/petition-basic-info!getBasicInfo.action?flag=0&petitionNo=' + petitionNo+'&regionCode='+regionCode;
						  var params={
								operationFuncCode : "getBasicInfo",
								operationFuncName : "点击信访编号查看信访件信息",
								operationBtnName : dateFlag,
								operationDesc : "查看信访编号为"+currPetitionNo+"的信访件信息",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
						  }
						  saveOperaLog(params);
					  }else if($(this).index()==1){
						  document.getElementById('instructionIframe').src = '<%=basePath%>/petition/petition-basic-info!getBasicInfo.action?flag=2&petitionNo=' + petitionNo+'&regionCode='+regionCode;
						  var params={
								operationFuncCode : "getBasicInfo",
								operationFuncName : "查看批示信息",
								operationBtnName : dateFlag,
								operationDesc : "查看信访编号为"+currPetitionNo+"的批示信息",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
						  }
						  saveOperaLog(params);
					  }else if($(this).index()==2){
						  document.getElementById('handleIframe').src = '<%=basePath%>/petition/petition-basic-info!getBasicInfo.action?flag=1&petitionNo=' + petitionNo+'&regionCode='+regionCode;
						  var params={
								operationFuncCode : "getBasicInfo",
								operationFuncName : "查看办理信息",
								operationBtnName : dateFlag,
								operationDesc : "查看信访编号为"+currPetitionNo+"的办理信息",
								operationTypeCode : OperationType.QUERY,
								operationTypeName : OperationType.QUERY_NAME,
								enableDataLog :true
						  }
						  saveOperaLog(params);
					  }else if($(this).index()==3){
						  $(window.frames["handleIframe"].document).f("table","multimedia").treegrid({    
							    url:'<%=basePath%>/DataAnalysis/PetitionInfo/json/Multi.json',    
							    idField:'id',    
							    treeField:'name', 
							    title:false,
							    columns:[[    
							        {title:'序号',field:'name',width:180},    
							        {title:'文件名称',field:'FileName',width:180},    
							        {title:'上传时间',field:'UploadTime',width:140},    
							        {title:'文件类型',field:'FileType',width:140},
							        {title:'操作',field:'Operation',width:115} 
							    ]], 
							    onLoadSuccess: function(data){
									var params={
											operationFuncCode : "Multi",
											operationFuncName : "查看多媒体信息",
											operationBtnName : dateFlag,
											operationDesc : "查看信访编号为"+currPetitionNo+"的多媒体信息",
											operationTypeCode : OperationType.QUERY,
											operationTypeName : OperationType.QUERY_NAME,
											enableDataLog :true
									 }
									 saveOperaLog(params);
									$(this).datagrid("doCellTip", { "max-width": "370px", "delay": 500 });
								}
						  });  
					  }else if($(this).index()==4){
						    var xffbGrid = $(window.frames["scanIframe"].document).f("div","Scanning").f("table","scan");
							xffbGrid.datagridSinosoft({
								body:{
									url:'<%=basePath%>/petition/petition-scan-file!listScanFile.action',
									queryParams:{
										filterTxt : "currPetitionNo",
										filterValue : currPetitionNo
							 	    },
							 	    border : false,
								    singleSelect : true,
								    checkbox : false,
								    fitColumns:false,
								    pageSize : 10,
								    columns:[[{
						        		field : "oid",
						        		hidden : true
						        	},{
						        		field : "petitionPrtNo",
						        		title : "条形码",
						        		width : 150,
						        		align : "left"
						        	},{
						        		field : "createDate",
						        		title : "扫描日期",
						        		width : 150,
						        		align : "left",
						        	},{
						        		field : "creatorName",
						        		title : "扫描人",
						        		width : 80,
						        		align : "left",
						        	},{
						        		field : "currPetitionNo",
						        		title : "信访编号",
						        		width : 145,
						        		align : "center",
						        	},{
						        		field : "regionName",
						        		title : "所属区域",
						        		width : 130,
						        		align : "center",
						        	},{
						        		field : "Operation",
						        		title : "操作区",
						        		width : 85,
						        		align : "center",
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
												operationFuncCode : "listScanFile",
												operationFuncName : "查看扫描件信息",
												operationBtnName : dateFlag,
												operationDesc : "查看信访编号为"+currPetitionNo+"的扫描件信息",
												operationTypeCode : OperationType.QUERY,
												operationTypeName : OperationType.QUERY_NAME,
												enableDataLog :true
										 }
										 saveOperaLog(params);
										$(this).datagrid("doCellTip", { "max-width": "370px", "delay": 500 });
									}
								}
							});
					  }
					  return false; 
				 });
			 }
		 }
	 </script>
  </body>
</html>