$.SUI("dataAnalysis");
var swFlag = "qs";/*全市1、本委2标识*/
var menuFlag = "1";/*左侧菜单标识*/
var dateFlag = "1";/*日期查询的按钮标识*/
var bwRadio = "N";/*0为含上级1为不含上级*/
var startDate;
var endDate;/*自定义的开始日期和结束日期*/

dataAnalysis.jumpPanel = function(swFlag1,menuFlag1,dateFlag1,bwRadio1){
	if(bwRadio1!="N"){
		bwRadio = bwRadio1;//是否含上级, 'checked'
	}
	if("bw"==swFlag1){
	     $.f("div","bwRadio").show();
	     $("input[name=contains]").eq(0).prop("checked",true);  
	     $("input[name=contains]").eq(1).removeProp('checked');;  
	}else if("qs"==swFlag1){
		 $.f("div","bwRadio").hide();
		 $("input[name=contains]").eq(0).prop("checked",true);  
		 $("input[name=contains]").eq(1).removeProp('checked');;  
	}
	if(swFlag1 != "0"){/*点击的是全市或本委*/
		swFlag = swFlag1;
	}
	if(menuFlag1 != "0"){/*点击的是左侧菜单*/
		menuFlag = menuFlag1;
		if("5"==menuFlag){/*说明选中的是各月趋势统计*/
			$.f("div","dateBtn").find(".button").eq(0).setdisabledEvent(true);
			$.f("div","dateBtn").find(".button").eq(1).setdisabledEvent(false);
			$.f("div","dateBtn").find(".button").eq(3).setdisabledEvent(false);
			dateFlag = "BJD";
			$.f("div","dateBtn").find(".button").eq(1).removeClass("date").addClass("dated");
			$.f("div","dateBtn").find(".button").eq(0).removeClass("dated").addClass("date");
			$.f("div","dateBtn").find(".button").eq(2).removeClass("dated").addClass("date");
			$.f("div","dateBtn").find(".button").eq(3).removeClass("dated").addClass("date");
			dataAnalysis.showHide();
		}else if("6"==menuFlag||"7"==menuFlag){/*说明选中的是同比或者环比*/
			$.f("div","dateBtn").find(".button").eq(0).setdisabledEvent(true);
	    	$.f("div","dateBtn").find(".button").eq(1).setdisabledEvent(true);
	   	    $.f("div","dateBtn").find(".button").eq(3).setdisabledEvent(true);
	   	    dateFlag = "BN";
	   	    $.f("div","dateBtn").find(".button").eq(2).removeClass("date").addClass("dated");
	   	    $.f("div","dateBtn").find(".button").eq(0).removeClass("dated").addClass("date");
			$.f("div","dateBtn").find(".button").eq(1).removeClass("dated").addClass("date");
			$.f("div","dateBtn").find(".button").eq(3).removeClass("dated").addClass("date");
			dataAnalysis.showHide();
		}else{
			$.f("div","dateBtn").find(".button").eq(0).setdisabledEvent(false);
			$.f("div","dateBtn").find(".button").eq(1).setdisabledEvent(false);
	    	$.f("div","dateBtn").find(".button").eq(2).setdisabledEvent(false);
	   	    $.f("div","dateBtn").find(".button").eq(3).setdisabledEvent(false);
	   	    if(dateFlag=="ZDY"){
		   	    dataAnalysis.ZDYDate();
	   	    }
		}
	}
	if(dateFlag1 != "0"){/*点击的是时间查询按钮*/
		dateFlag = dateFlag1;
		if(dateFlag1=="ZDY"){/*自定义*/
			$.f("div","zdyDIV").show();
			return;/*点击自定义只是显示日期框不查询所以返回*/
		}else if(dateFlag1=="ZDYC"){/*说明点击的查询按钮*/
			var start = $.f("div","zdyDIV").f("input","startDate").datebox("getValue");
	    	var end = $.f("div","zdyDIV").f("input","endDate").datebox("getValue");
			if(end!=""){
				if(end<start){
					dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;结束日期要小于开始日期!', 3000);
					return;
				}
			}
			if(start==null||start==""){
				dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;开始日期不能为空!', 3000);
				return;
			}
			
			startDate = $.f("div","zdyDIV").f("input","startDate").datebox("getText");
			endDate = $.f("div","zdyDIV").f("input","endDate").datebox("getText");
			if('5'==menuFlag){
				if(end!=""){
					//如果选中的是各月趋势前后日期不能超过一年
					if(endDate.substr(0,4)-startDate.substr(0,4)==1){//如果为 1说明相差一年
						if(startDate.substr(5,6)-endDate.substr(5,6)<=0){//说明结束日期和开始日期不在一年内
							dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;请选择一年内的日期!', 3000);
							return;
						}
					}else if(endDate.substr(0,4)-startDate.substr(0,4)>1){
						dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;请选择一年内的日期!', 3000);
						return;
					}
				}else{
					var myDate = new Date(); 
					var year = myDate.getFullYear();
					var month = myDate.getMonth()+1; 
					if(year-startDate.substr(0,4)==1){//如果为 1说明相差一年
						if(startDate.substr(5,6)-month<=0){//说明结束日期和开始日期不在一年内
							dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;请选择一年内的日期!', 3000);
							return;
						}
					}else if(year-startDate.substr(0,4)>1){
						dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;请选择一年内的日期!', 3000);
						return;
					}
					endDate = year+"-"+month;
				}
			}
		}else{//菜单初始化的时候是传过来的BN BJD等
			dataAnalysis.showHide();
		}
	}else{
		if(dateFlag=="ZDY"){//如果点击自定义但是没输入时间段就选择其他菜单
			dataAnalysis.ZDYDate();
		}
	}
	
	var href;
	if("1"==menuFlag){/*说明选中的是信访件分布情况*/
		if(swFlag=="qs"){/* 全市柱状图 */
			href = sessionlostName+'/DataAnalysis/XFJDistribution/LetterMain.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}
		if(swFlag=="bw"){/* 本委列表 */
			href = sessionlostName+'/DataAnalysis/XFJDistribution/XFFBBWGrid.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate+'&bwRadio='+bwRadio;
		}
	}
	if("2"==menuFlag){/*说明选中的是问题类别分布情况*/
		href = sessionlostName+'/DataAnalysis/ProblemDistribution/ProblemDistributionMain.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate+'&bwRadio='+bwRadio;
	}
	if("3"==menuFlag){/*说明选中的是数量前五位问题类别*/
		href = sessionlostName+'/DataAnalysis/AmountTop5/AmountTopMain.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate+'&bwRadio='+bwRadio;
	}
	if("4"==menuFlag){/*说明选中的是增、减量前五位问题类别*/
		href = sessionlostName+'/DataAnalysis/ClassChange/ClassChangeMain.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate+'&bwRadio='+bwRadio;
	}
	if("5"==menuFlag){/*说明选中的是各月趋势统计*/
		href = sessionlostName+'/DataAnalysis/Trend/Mouth.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate+'&bwRadio='+bwRadio;
	}
	if("6"==menuFlag){/*说明选中的是同比分布情况*/
		href = sessionlostName+'/DataAnalysis/SequentialAndSameCompare/SameCompare.jsp?swFlag='+swFlag+'&bwRadio='+bwRadio;
	}
	if("7"==menuFlag){/*说明选中的是环比分布情况*/
		href = sessionlostName+'/DataAnalysis/SequentialAndSameCompare/Sequential.jsp?swFlag='+swFlag+'&bwRadio='+bwRadio;
	}
	if("8"==menuFlag){/*说明点击的是搜索按钮*/
		var searchData = $("#text1").val();
		$.f("div","dataAnalysisPanel").panel({
			href : sessionlostName+'/fullTextSearch/search.jsp?param='+searchData
		});
	}
	if("9"==menuFlag){//未知/*说明选中的是信访件分布情况*/
		if(swFlag=="qs"){/* 全市柱状图 */
			href = sessionlostName+'/DataPredict/Distribution/Distribution.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}
		if(swFlag=="bw"){/* 本委列表 */
			$("#analysisDiv").css("display","block");
			href = sessionlostName+'/DataPredict/Distribution/CityTrendMap.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}else {
			$("#analysisDiv").css("display","none");
		}
	} else {
		$("#analysisDiv").css("display","none");
	}
	if("10"==menuFlag){/*民意分析*/
		$("#dataStatus").css("display","none");
		href = sessionlostName+'/DataPredict/Opinion/Opinion.jsp';
	} 
	if("11" == menuFlag){/*说明选中的是问题类别变化情况*/
		if(swFlag=="qs"){/* 全市列表 */
			href = sessionlostName+'/DataPredict/ProChange/Fpm.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}
		if(swFlag=="bw"){/* 本委告警列表 */
			href = sessionlostName+'/DataPredict/ProChange/AlarmCity.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}
	}
	if("12" == menuFlag){/*说明选中的是问题类别分布情况*/
		if(swFlag=="qs"){/* 全市列表 */
			href = sessionlostName+'/DataPredict/ProDistributed/ProDistribution.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}
		if(swFlag=="bw"){/* 本委告警列表 */
			href = sessionlostName+'/DataPredict/ProDistributed/ProDistribution.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
		}
		
	}
	
	if("13" == menuFlag){/*聚焦分析*/
		$("#searchTar4Left").css("display","none");
		$("#search4Predict").val("");
		href = sessionlostName+'/DataPredict/AmountTopMain.jsp?swFlag='+swFlag+'&dateFlag='+dateFlag+'&startDate='+startDate+'&endDate='+endDate;
	} else {
		$("#searchTar4Left").css("display","none");
	}
	
	if(menuFlag == '9' || menuFlag == '10' || menuFlag == '13') {
		$("#dataStatus").css("display","none");
	} else {
		$("#dataStatus").css("display","block");
		//问题类别分布情况时间维度修改 去掉自定义
		if(menuFlag == "12" || menuFlag == "11") {
			//当自定义时默认显示本月
			if($.f("div","dateBtn").find(".button").eq(3).attr("class") == 'button dated') {
				dateFlag = "BY";
				$.f("div","dateBtn").find(".button").eq(0).removeClass("date").addClass("dated");
				$.f("div","dateBtn").find(".button").eq(1).removeClass("dated").addClass("date");
				$.f("div","dateBtn").find(".button").eq(2).removeClass("dated").addClass("date");
				$.f("div","dateBtn").find(".button").eq(3).removeClass("dated").addClass("date");
				 $.f("div","zdyDIV").css("display","none");
			}
			$.f("div","dateBtn").find(".button").eq(3).hide();
		} else {
			
			$.f("div","dateBtn").find(".button").eq(3).show();
		}
	}
	$.f("div","dataAnalysisPanel").panel({
		href : href
	});
}

dataAnalysis.showHide = function(){
	$.f("div","zdyDIV").hide();
	$.f("div","zdyDIV").f("input","startDate").datebox("setText","");
	$.f("div","zdyDIV").f("input","endDate").datebox("setText","");
	startDate = "";
	endDate = "";
}
dataAnalysis.ZDYDate = function(){
	$.f("div","zdyDIV").show();
	var start = $.f("div","zdyDIV").f("input","startDate").datebox("getValue");
	var end = $.f("div","zdyDIV").f("input","endDate").datebox("getValue");
	if(start==""){
		var myDate = new Date();
		var year = myDate.getFullYear();
		var month = myDate.getMonth()+1;
		startDate = year+"-"+month;
	    endDate = "";
		$.f("div","zdyDIV").f("input","startDate").datebox("setText",startDate);
	}
}
//未来
dataAnalysis.UnknownMapPanel = function(){
	/*$.f("a","know").css("color","black");
	$.f("a","unknow").css("color","red");*/
	$.f("a","know").css("color","#00bbda");
	$.f("a","unknow").css("color","yellow");
	swFlag = "qs";
	menuFlag = "9";
	dateFlag = "BY";
	$.f("div","dateBtn").find(".button").eq(0).addClass("dated").removeClass("date");
	$.f("div","dateBtn").find(".button").eq(0).siblings().addClass("date").removeClass("dated");
	$("#unknowMenu").find(".mainMenuCss").eq(0).removeClass("deepblue").addClass("orange");
	$("#unknowMenu").find(".mainMenuCss").eq(0).siblings().removeClass("orange").addClass("deepblue");
	dataAnalysis.jumpPanel('qs','9','BY');
	//隐藏已知菜单
	$("#knowMenu").css("display","none");
	$("#unknowMenu").css("display","block");
	//隐藏搜索框
	$("#searchTar").css("display","none"); 
	//隐藏上级包含上级
	$("#tabTar").css("display","none"); 
	
}

//已知
dataAnalysis.KnownPanel = function(){
	/*$.f("a","know").css("color","black");
	$.f("a","unknow").css("color","red");*/
	$.f("a","unknow").css("color","#00bbda");
	$.f("a","know").css("color","yellow");
	//隐藏已知菜单
	$("#unknowMenu").css("display","none");
	$("#knowMenu").css("display","block");
	//显示搜索框
	$("#searchTar").css("display","block"); 
	//显示上级包含上级
	$("#tabTar").css("display","block"); 
	swFlag = "qs";
	menuFlag = "1";
	dateFlag = "BY";
	$.f("div","dateBtn").find(".button").eq(0).addClass("dated").removeClass("date");
	$.f("div","dateBtn").find(".button").eq(0).siblings().addClass("date").removeClass("dated");
	$("#knowMenu").find(".mainMenuCss").eq(0).removeClass("deepblue").addClass("orange");
	$("#knowMenu").find(".mainMenuCss").eq(0).siblings().removeClass("orange").addClass("deepblue");
	$.f("div","knowBtn").find(".button").eq(0).addClass("orang").removeClass("deepblu");
	$.f("div","knowBtn").find(".button").eq(1).addClass("deepblu").removeClass("orang");
	//切换默认显示的数据
	dataAnalysis.jumpPanel('qs','1','BY');
  	
	
}