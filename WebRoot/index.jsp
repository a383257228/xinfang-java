<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>
<jsp:include page="/fullTextSearch/BasicInfoShowWin.jsp"/>
<!-- 信访数据挖掘分析  信访件分布情况 -->
<jsp:include page="/DataPredict/Distribution/Trend.jsp"/>
<jsp:include page="/DataPredict/Distribution/Analysis.jsp"/>
<jsp:include page="/DataPredict/Distribution/TrendAnalysis.jsp"/>
<!-- 聚焦分析 -->
<jsp:include page="/DataPredict/liebiao.jsp"/>
<!-- 信访件分布情况 -->
<jsp:include page="/DataAnalysis/XFJDistribution/Regimentation.jsp"/>
<jsp:include page="/DataAnalysis/XFJDistribution/LetterInfoGrid.jsp"/>
<!-- 数量前五位 -->
<jsp:include page="/DataAnalysis/AmountTop5/Top5Drilling.jsp"/>
<jsp:include page="/DataAnalysis/AmountTop5/Top5InfoGrid.jsp"/>
<!-- 问题类别分布情况 -->
<jsp:include page="/DataAnalysis/ProblemDistribution/ProblemDistributedDrilling.jsp"/>
<jsp:include page="/DataAnalysis/ProblemDistribution/ProblemInfoGrid.jsp"/>
<!-- 问题类别变化情况 -->
<jsp:include page="/DataAnalysis/ClassChange/ChangeDrilling.jsp"/>
<jsp:include page="/DataAnalysis/ClassChange/ClassChangeGrid.jsp"/>
<!-- 各月趋势、同比、环比 -->
<jsp:include page="/DataAnalysis/Trend/MouthDrilling.jsp"/>
<jsp:include page="/DataAnalysis/Trend/TrendCompareGrid.jsp"/>

<jsp:include page="/DataAnalysis/PetitionInfo/Assemble.jsp"/>


<!-- 问题类别变化 -->
<jsp:include page="/DataPredict/ProChange/AlarmOther.jsp"/>

<!-- 民意分析 -->
<jsp:include page="/DataPredict/Opinion/AddDict.jsp"/>
<jsp:include page="/DataPredict/Opinion/WordCloud.jsp"/>
<jsp:include page="/DataPredict/Opinion/EditWord.jsp"/> 
<jsp:include page="/DataPredict/Opinion/MergeWord.jsp"/>
<jsp:include page="/DataPredict/Opinion/AddClass.jsp"/>
<jsp:include page="/DataPredict/Opinion/PbasicInfoList.jsp"/>
<jsp:include page="/DataPredict/Opinion/OpinionGrid.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="<%=basePath%>/js/index.js"></script>
	<style>
	.panel-body{
	   background:transparent;
	   border:none;
	}
	/* 媒体查询  */
	body{
	    background:url(<%=basePath%>/images/bg_size1366.jpg);
	}
	.banner{
		margin-left:12.5%;
	}
	.logo{
		margin-top:-70px;
		margin-left:13%;
	}
	.bottom{
		 width:30.7%;
		 background:url(<%=basePath%>/images/bottom-bj.png)
	}
   .text{
	     font-size:15px;
	     color:#999999;
	     width:40%;
	     height:36px;
	      margin-left: 51.5%; 
    }
    .swbtn{
         margin-left:2px
    }
	@media only screen and (max-width: 1024px) {
	    body {
	         background-color: url(<%=basePath%>/images/bg.png);
	    }
	    .banner{
		     margin-left:0;
	    }
	    .logo{
			 margin-top:-70px;
			 margin-left:0
		}
	     .bottom{
	         width:41%;
	         background:url(<%=basePath%>/images/bottom-bj.png)
	    }
	   .text{
			font-size:15px;
			color:#999999;
		        width:40%;
			height:36px;
			margin-left:48%;
			/*  margin-left: 52%; */
		}
	    .swbtn{
	   		margin-left:0
	    }
	}
	</style>
  </head>
  <body>
  	<div style="width:100%;height:100%;">
		<div class="sinosoft-layout" style="width:100%;height:100%;">
			<div par="region:'north'" style=" height:100px;width:100%;">
				<div class="heading">
				<img src="<%=basePath%>/images/banner.png" class="banner">
					<img src="<%=basePath%>/images/logo.png" class="logo">
					<div style="width:100%;margin-top:-70px;">
			   	         <a style=" text-align:center; margin-left:8%" idFlag="know" onclick="dataAnalysis.KnownPanel()">已知信访情况分析 </a>
				         <a style=" text-align:center;color:#00bbda;margin-left:60%" idFlag="unknow" onclick="dataAnalysis.UnknownMapPanel()">信访数据挖掘分析 </a> 
				    </div> 
					
				</div>
			</div>
			<div par="region:'west'" style="width:20%;">
			   <div id = "knowMenu"> 
					<div idFlag="knowBtn" style="border:1px solid #49D9FE;width:80%;margin-left:17px;height:36px;/* margin-top:10px */">
					    <a class="button orang qsbwbutton" style="width:48.6%"  idflag="swBtn" onClick="dataAnalysis.jumpPanel('qs','0','0','N')">全市</a>
						<a class="button deepblu qsbwbutton swbtn" style="width:48.6%"   idflag="swBtn" onClick="dataAnalysis.jumpPanel('bw','0','0','0')">本委</a> 
					</div>
					<div>
						<a class="button orange mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','1','0','N')">信访件分布情况</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','2','0','N')">问题类别分布情况</a> 
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','3','0','N')">数量前五位问题类别</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','4','0','N')">问题类别变化情况</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','5','0','N')">各月趋势统计</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','6','0','N')">同比分布情况</a>
						<a class="button deepblue mainMenuCss"    idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','7','0','N')">环比分布情况</a>
						<!--  
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','10','0')">民意分析</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','11','0')">问题类别的变化情况</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','12','0')">问题类别的分布情况</a>
				        -->
					</div>
			  </div>  
			  <div id = "unknowMenu"> 
			  <div style="border:1px solid #49D9FE;width:80%;margin-left:17px;height:36px;/* margin-top:10px */">
					    					<a class="button orang qsbwbutton"  style="width:48.6%" idflag="swBtn" onClick="dataAnalysis.jumpPanel('qs','0','0','N')">全市</a>
						<a class="button deepblu qsbwbutton swbtn"  style="width:48.6%" idflag="swBtn" onClick="dataAnalysis.jumpPanel('bw','0','0','N')" style="margin-left:1%">本委</a> 
					</div>
					<div>
						<a class="button orange mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','9','0','N')">信访件分布情况</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','12','0','N')">问题类别分布情况</a> 
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','11','0','N')">问题类别的变化情况</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','10','0','N')">民意分析</a>
						<a class="button deepblue mainMenuCss"   idflag="menuBtn"  onclick="dataAnalysis.jumpPanel('0','13','0','N')">聚焦分析</a>
					</div>
			  </div>  
			</div>
			<div par="region:'center'" style="width:70%;height:500px">
			 <div style="width:100%;height:50px;">
			 <div id = "tabTar">
				<div idFlag="bwRadio" style="color:#fff;width:40%;float:left;margin-top:15px;display:none">
			  		<input type="radio" name="contains" value="含上级"  style="width:15px;" checked>含上级
			  		<input type="radio" name="contains" value="不含上级"  style="width:15px">不含上级
			  	</div>
			  	</div>
			   		<div style="width:70%;float:left;height:34px;position: absolute; margin-left: 27%; ">
			   		<div id = "searchTar">
		   				<input type="text" id="text1" value="" class="text" placeholder="&nbsp;&nbsp;&nbsp;请输入搜索的内容" >
		   				<a  onClick="dataAnalysis.jumpPanel('0','8','0','N')" class="button deepblue" style="width:50px;height:34px;line-height:34px;font-size:16px;text-align:center;border:1px solid #49D9FE " plain="true" idFlag="search">搜索</a>
			   		</div>
			   		<div id = "searchTar4Left" style = "display:none">
		   				<input type="text" id="search4Predict" value="" class="text" placeholder="&nbsp;&nbsp;&nbsp;请输入搜索的内容" />
		   				<a  onClick="getEdges()" class="button deepblue" style="width:50px;height:34px;line-height:34px;font-size:16px;text-align:center;border:1px solid #49D9FE " plain="true" idFlag="search">搜索</a>
			   		</div>
			   		<div id = "analysisDiv" style = "display:none">
		   				<a onClick="analysisIn()" class="button deepblue" style="width:84px;height:34px;line-height:34px;font-size:16px;text-align:center;margin-left: 84%;border:1px solid #49D9FE " plain="true" idFlag="analysis">分析报告</a>
			   		</div>
			   	    </div>
			   </div>
			   	<div style="width:100%;height:450px;margin-top:4px">
			   		<div idFlag="dataAnalysisPanel" style="width:100%;height: 100% ;" par="border:false"></div>
			   	</div>
			</div>
 			<div par="region:'south'" style="height:60px;width:100%; top:620px !important" id = "dataStatus">
 			<!-- 本月 本年 本季度 自定义 -->
 			 <div style="width:100%;height:46px; margin-top:12px ">
			   <div style="height:46px;margin:auto;"class="bottom">
					 <div style="text-align:center" idFlag="dateBtn">
					   		<a class="button dated" idFlag="timeBtn" style="width:80px;" onclick="dataAnalysis.jumpPanel('0','0','BY')">本月数据</a>
					   		<a class="button date"  idFlag="timeBtn" style="width:80px;" onclick="dataAnalysis.jumpPanel('0','0','BJD')">本季数据</a>
					   		<a class="button date"  idFlag="timeBtn" style="width:80px;" onclick="dataAnalysis.jumpPanel('0','0','BN')">本年数据</a>
					   		<a class="button date"  idFlag="timeBtn" style="width:80px;" onclick="dataAnalysis.jumpPanel('0','0','ZDY')">自定义</a> 
					  </div>
				 </div> 
			     <div style="width:30%;float: right;height:34px;margin-top: -35px;display: none;" idFlag="zdyDIV">
				      <input id="startDateValue" validType="startDate['#endDateValue']"  style="width:100px" idFlag="startDate" class="sinosoft-dateBox" par="editable:false"/>
			          <span style="color:#00DEFE">至</span>
					  <input id="endDateValue" validType="endDate['#startDateValue']"  style="width:100px" idFlag="endDate"   class="sinosoft-dateBox" par="editable:false"/>
					  <a class="button " idFlag="zdyBtn" style="width:50px;line-height:24px;border:1px solid #49D9FE;text-align:center;font-size:16px;color:#00DEFE" onclick="dataAnalysis.jumpPanel('0','0','ZDYC')">查询</a>
				 </div> 
			</div> 
	</div>
  	<script type="text/javascript"> 
  	  $("#unknowMenu").css("display","none");
  	  $.SUI("dataAnalysis");
  	  $(function(){
  		 	dataAnalysis.jumpPanel('qs','1','BY','N');//默认显示全市信访件分布情况
		    $.f("div","bwRadio").hide();
	  		$("#text1").bigAutocomplete({/* 初始化搜索下拉框 */
				width:500,	
				url : "<%=basePath%>/fullTextSearch/full-text-search!suggest.action?indexName=petitioninfo&indexType=petitionissue",
				callback:function(data){
				},
				onMakeContAndShow : function(data){
				}
			});
	  		$.f("a","menuBtn").click(function(){//左侧菜单的点击切换
	            if($(this).hasClass("deepblue")){
	        	   $(this).removeClass("deepblue").addClass("orange");
	        	   $(this).siblings().addClass("deepblue").removeClass("orange");
	            }
	  	    });
	  		$(":radio").click(function(){
	  			if($(this).val()=="含上级"){
	  				 dataAnalysis.jumpPanel('0','0','0','0');
	  			}else if($(this).val()=="不含上级"){
		  			 dataAnalysis.jumpPanel('0','0','0','1');
	  			}
	  		});
	  		$.f("a","swBtn").click(function(){//全市、本委点击切换
	            if($(this).hasClass("deepblu")){
	             $(this).removeClass("deepblu").addClass("orang");
		         $(this).siblings().addClass("deepblu").removeClass("orang"); 
	            }
	  	    });
	  		$.f("a","dateBtn").click(function(){//本月、本季度、本年、自定义点击切换
	  			 if(!$(this).hasClass("gray")){
		        	  $(this).removeClass("date").addClass("dated");
		        	  $(this).siblings().addClass("date").removeClass("dated");
	             }
	  	    });
  			$.f("a","timeBtn").on("click", function() {//本月、本季度、本年、自定义点击切换
  				if($(this).index()==0){
  					if(!$(this).hasClass("gray")){//如果0可以点击说明不是各月、同比环比
	  					 $(this).removeClass("date").addClass("dated");
	  					 $(this).siblings().addClass("date").removeClass("dated");
  					}
  				}else if($(this).index()==1){
  					if(!$(this).hasClass("gray")){//如果1可以点击 需要判断0是否可点击 如果不可以就是各月
						$(this).removeClass("date").addClass("dated");
  						if($.f("div","dateBtn").find(".button").eq(0).hasClass("gray")){
  							$.f("div","dateBtn").find(".button").eq(2).addClass("date").removeClass("dated");
  							$.f("div","dateBtn").find(".button").eq(3).addClass("date").removeClass("dated");
  						}else{
  		  					 $(this).siblings().addClass("date").removeClass("dated");
  						}
 					}
  				}else if($(this).index()==2){
  					if(!$(this).hasClass("gray")){
  						$(this).removeClass("date").addClass("dated");
  						if($.f("div","dateBtn").find(".button").eq(0).hasClass("gray")){
  							if($.f("div","dateBtn").find(".button").eq(1).hasClass("gray")){//如果0禁用1也禁用说明是同比环比
  								
  							}else{//如果0禁用1没有禁用说明是各月
  								$.f("div","dateBtn").find(".button").eq(1).addClass("date").removeClass("dated");
  	  							$.f("div","dateBtn").find(".button").eq(3).addClass("date").removeClass("dated");
  							}
  						}else{
  		  					 $(this).siblings().addClass("date").removeClass("dated");
  						}
  					}
  				}else if($(this).index()==3){
  					if(!$(this).hasClass("gray")){//如果可以点说明不是同比环比
 						$(this).removeClass("date").addClass("dated");
  						if($.f("div","dateBtn").find(".button").eq(0).hasClass("gray")){//如果0禁用说明是各月
							$.f("div","dateBtn").find(".button").eq(1).addClass("date").removeClass("dated");
  							$.f("div","dateBtn").find(".button").eq(2).addClass("date").removeClass("dated");
  						}else{
  							$.f("div","dateBtn").find(".button").eq(0).addClass("date").removeClass("dated");
							$.f("div","dateBtn").find(".button").eq(1).addClass("date").removeClass("dated");
  							$.f("div","dateBtn").find(".button").eq(2).addClass("date").removeClass("dated");
  						}
  					}
  				}
            });
	  	    /* 已知未知 分析*/
	  	    $.f("a","know").css("color","yellow");
	  	 	$.f("div","zdyDIV").f("input","startDate").datebox({//开始日期
			    onSelect : function(date){
			    	var start = $("#startDateValue").datebox("getValue");
			    	var end = $("#endDateValue").datebox("getValue");
			    	$.f("div","zdyDIV").f("input","startDate").datebox("setText",(date.getFullYear()+"-"+(date.getMonth()+1)));
			    	if(end!=""&&start!=""){
				    	if(end>start){//end大了就禁用校验
				    		$("#startDateValue").datebox("disableValidation");
				    	}else if(end<start){//开始日期大了
				    		$("#startDateValue").datebox("enableValidation");
				    	}
				    	$("#endDateValue").datebox("disableValidation");
			    	}
			    } 
			}); 
	  	 	$.f("div","zdyDIV").f("input","endDate").datebox({
			    onSelect : function(date){
			    	var start = $("#startDateValue").datebox("getValue");
			    	var end = $("#endDateValue").datebox("getValue");
			    	$.f("div","zdyDIV").f("input","endDate").datebox("setText",(date.getFullYear()+"-"+(date.getMonth()+1)));
			    	if(end!=""&&start!=""){
				    	if(end>start){//end大了就禁用校验
				    		$("#endDateValue").datebox("disableValidation");
				    	}else if(end<start){
				    		$("#endDateValue").datebox("enableValidation");
				    	}
				    	$("#startDateValue").datebox("disableValidation");
			    	}
			    } 
			}); 
	  	 	
  	   });
  	</script>
  </body>
</html>