<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String basePath = request.getContextPath();
	String pathloginvarBasePath = request.getContextPath();
	String loginvarBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathloginvarBasePath+"/";
	String searchData = request.getParameter("param");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body>
	<div style="width:1200px;height:10px;">
		<a href="javascript:void(0)" onclick="openSet();" style="float:right">设置</a>
	</div>
	<div class="resultPanel"></div>
	<script>
		var loginvarBasePath = "<%= loginvarBasePath%>";
		var basePath = "<%=basePath%>";
		var searchData = "<%=searchData%>";
		$(function(){
			$("#text").val(searchData);
			search(searchData);
		})
	
		/**
		 * 检索查询方法
		 */
		function search(text){
			if(!text){
				return;
			}
			$.ajax({
			  type: "POST",
			  url: loginvarBasePath+"/fullTextSearch/full-text-search!search.action",
			  dataType :"json",
			  data: {
				  from : 0,
				  size : 5,
				  indexName : "petitioninfo",
				  indexType : "petitionissue",
				  searchField : "petitionTitle,issueContent",
				  searchText : text
			  },
			  success: function(result, textStatus, jqXHR ){
				  if(result.result.length>0){
					  $(".resultPanel").html(createDataHtml(result.result));
				  }
			  },
			  error: function(result, textStatus, jqXHR ){
				  console.info(result.responseText)
			  }
			});
		}
		
		/**
		*创建所有数据的html
		**/
		function createDataHtml(dataArr){
			var html="";
			for(i in dataArr){
				html+=createOneDataHtml(dataArr[i]);
			}
			return html;
		}
		/**
		*创建一条数据的html
		**/
		function createOneDataHtml(data){
			 var html = "<div class='result'>"
		  			+"<div class=\"petitionTitle\" title=\""+data.petitionTitleTitle+"\"><a  href=\"javascript:none\" onclick=\"loadPetitionInfo(\'"+data.id+"\',\'"+data.petitionNo+"\',\'"+data.regionCode+"\')\">"+data.petitionTitle+"</a></div>"
		  			+"<div class='issueContent' title='"+data.issueContentTitle+"'>"+data.issueContent+"</div>"
		  			+"</div>";
		  	 return html;
		}
		/**
		*点击查询按钮时触发的方法
		**/
		function clickSearch(){
			var text = $("#text").val();
			search(text);
		}
		
		/**
		*点击信访标题，通过信访件id，信访编号等查看信访件详情页面
		**/
		function loadPetitionInfo(id,petitionNo,regionCode){
			var wholeCityDiv=$.f("div","basicInfoShowWin").window({
				fit:true,
				title:"信访件信息",	
				closed:true,
				maximizable : false,
				minimizable : false,
				collapsible : false,
				onOpen : function(){
					var href = basePath+"/fullTextSearch/PetitionInfoShowPanel.jsp";
					$.f("div","basicInfoShowWin").f("div","acceptInfoShowPanel").panel({/* 基本信息 */
						href : href
					});
					
					$.ajax(loginvarBasePath+"/petition/petition-basic-info!loadPetitionInfo.action",{
						data:{
							id : id
						},
						success:function(data, textStatus, jqXHR){
							var result = $.parseJSON(data);
							var setInt=0;
							var interval=setInterval(function(){
								if($.f("div","petitionBasicInfo").html()==""){
									setInt+=1
									if(setInt==200){
										clearInterval(interval);
									}
								}else{
									for(var field in result){
										var value = result[field];
										if(field=="petitionUrgeCode" 
												|| field=="accusedLocalCadreFlag" 
												|| field=="accusedDevianceFlag" 
												|| field=="accuserAppealFlag" || field=="isSwgat"){
											if(value=="1"){
												value = "是";
											}else{
												value = "否";
											}
										}
										if(field=="postponeFlag"){
											if(value=="1"){
												value = "同意延期";
											}else if(value=="2"){
												value = "正在申请中";
											}else if(value=="3"){
												value = "没同意延期";
											}else{
												value = "否";
											}
										}
										if(field=="petitionDate"||field=="requireEndDate"||field=="signDate"||field=="assignedDate"
												||field=="assignedRequireEndDate"||field=="instructTime"||field=="accusedBirthday"
												||field=="accusedBirthday"||field=="accusedBirthday"||field=="accusedBirthday"){
											if(null!=value){
												value = value.substring(0,10);
											}
										}
										if($.f("div","petitionBasicInfo").f("span", field)){
											$.f("div","petitionBasicInfo").f("span", field).html(value);
										}
									}
									clearInterval(interval);
								}
							},50);
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据有问题！', 3000);
						}
					});
					
					
					$.f("div","basicInfoShowWin").f("div","instructShowPanel").panel({/* 批示信息 */
						href : basePath+"/fullTextSearch/InstructShowPanel.jsp"
					});
					$.f("div","basicInfoShowWin").f("div","handleShowPanel").panel({/* 办理信息 */
						href : basePath+"/fullTextSearch/HandleShowPanel.jsp"
					});
					$.ajax(loginvarBasePath+"/petition/petition-deal-info!loadSurveyDealAndEndInfo.action",{
						data:{
							id : id
						},
						success:function(data, textStatus, jqXHR){
							var result = $.parseJSON(data);
							var setInt=0;
							var interval=setInterval(function(){
								if($.f("div","handlePanel").html()==""){
									setInt+=1
									if(setInt==200){
										clearInterval(interval);
									}
								}else{
									for(var field in result){
										var value = result[field];
										if($.f("div","handlePanel").f("span", field)){
											$.f("div","handlePanel").f("span", field).html(value);
										}
									}
									clearInterval(interval);
								}
							},50);
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>数据有问题！', 3000);
						}
					});
				},
			});
			wholeCityDiv.window("open");
		}
		
		/**
		*打开设置页面
		**/
		function openSet(){
			window.open(loginvarBasePath+"/fullTextSearch/setting.jsp", "设置");
		}
	</script>
</body>
</html>