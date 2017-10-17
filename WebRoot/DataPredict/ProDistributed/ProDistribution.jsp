<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String swFlag = request.getParameter("swFlag");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>问题类别分布情况</title>
<script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
<script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
</head>
<body>
	<style>
	.bk{
	     width:98%;
	     height:430px;
	     background:url(<%=basePath%>/images/bk.png) no-repeat;
	     padding-top:10px
	  }
	 .tie{
        height:420px;
        margin-left:15px;
        background:url(<%=basePath%>/images/bjk2.png) no-repeat;
	  }
	  .chart-legend{
	     margin-top:0;
	  }
	 @media only screen and (max-width: 1024px) {
	    .bk{
		    background:url(<%=basePath%>/images/bk2.png) no-repeat;
	    }
	   .tie{
	        background:url(<%=basePath%>/images/bjk1.png) no-repeat;
	         background-size:97%;
	     }
	     .chart-legend{
	     margin-top:-20px;
	  }
	}
</style>
	<div class="bk">
		<div class="tie">
			<div style = "float:left;margin-top:10px;margin-left:20px;" id = "quse">
				<select id="qudd" onchange='quChange(this)' style='position:absolute;z-index:200'>
<!-- 						<option selected value="">- 请选择 -</option> -->
<!-- 					<option value="闵行区">闵行区</option> -->
<!-- 					<option value="杨浦区">杨浦区</option> -->
<!-- 					<option value="奉贤区">奉贤区</option> -->
<!-- 					<option value="黄浦区">黄浦区</option> -->
<!-- 					<option value="普陀区">普陀区</option> -->
<!-- 					<option value="崇明区">崇明区</option> -->
<!-- 					<option value="青浦区">青浦区</option> -->
<!-- 					<option value="宝山区">宝山区</option> -->
<!-- 					<option value="长宁区">长宁区</option> -->
<!-- 					<option selected="selected" value="浦东新区">浦东新区</option> -->
<!-- 					<option value="徐汇区">徐汇区</option> -->
<!-- 					<option value="静安区">静安区</option> -->
<!-- 					<option value="虹口区">虹口区</option> -->
<!-- 					<option value="嘉定区">嘉定区</option> -->
<!-- 					<option value="金山区">金山区</option> -->
<!-- 					<option value="松江区">松江区</option> -->
					
<!-- 					<option value="市国资委党委纪检组">市国资委党委纪检组</option> -->
<!-- 					<option value="市建设交通工作党委纪检组">市建设交通工作党委纪检组</option> -->
<!-- 					<option value="市委宣传部纪检组">市委宣传部纪检组</option> -->
<!-- 					<option value="市级机关纪工委">市级机关纪工委</option> -->
<!-- 					<option value="市委政法委机关纪检组">市委政法委机关纪检组</option> -->
<!-- 					<option value="市经济信息化工作党委纪检组">市经济信息化工作党委纪检组</option> -->
<!-- 					<option value="市合作交流工作党委纪检组">市合作交流工作党委纪检组</option> -->
<!-- 					<option value="市科技工作党委纪检组">市科技工作党委纪检组</option> -->
<!-- 					<option value="市财政局纪检组">市财政局纪检组</option> -->
<!-- 					<option value="市国家安全局纪检组">市国家安全局纪检组</option> -->
<!-- 					<option value="市公安局纪检组">市公安局纪检组</option> -->
					
<!-- 					<option value="市检察院纪检组">市检察院纪检组</option> -->
<!-- 					<option value="市高级人民法院纪检组">市高级人民法院纪检组</option> -->
<!-- 					<option value="市教育卫生党委纪检组">市教育卫生党委纪检组</option> -->
<!-- 					<option value="市委农办纪检组">市委农办纪检组</option> -->
<!-- 					<option value="市发展改革委纪检组">市发展改革委纪检组</option> -->
<!-- 					<option value="市商务委纪检组">市商务委纪检组</option> -->
<!-- 					<option value="市环保局纪检组">市环保局纪检组</option> -->
<!-- 					<option value="市规划国土资源局纪检组">市规划国土资源局纪检组</option> -->
<!-- 					<option value="市地税局纪检组">市地税局纪检组</option> -->
<!-- 					<option value="市民政局纪检组">市民政局纪检组</option> -->
<!-- 					<option value="市人力资源社会保障局纪检组">市人力资源社会保障局纪检组</option> -->
					
<!-- 					<option value="市政府办公厅纪检组">市政府办公厅纪检组</option> -->
<!-- 					<option value="市卫生计生委纪检组">市卫生计生委纪检组</option> -->
<!-- 					<option value="市交通委纪检组">市交通委纪检组</option> -->
<!-- 					<option value="市人大机关纪检组">市人大机关纪检组</option> -->
<!-- 					<option value="市政协机关纪检组">市政协机关纪检组</option> -->
<!-- 					<option value="市文广影视局纪检组">市文广影视局纪检组</option> -->
<!-- 					<option value="市委统战部纪检组">市委统战部纪检组</option> -->
<!-- 					<option value="市委办公厅纪检组">市委办公厅纪检组</option> -->
<!-- 					<option value="市委组织部纪检组">市委组织部纪检组</option> -->
<!-- 					<option value="市总工会机关纪检组">市总工会机关纪检组</option> -->
				</select>
			</div>

			<div id="dl"> 
				<span style="font-size:18px;color:#fff;margin-left:30%;margin-top:10px;float:left">行政级别与问题类别关系图</span>
				<div
					style="width:20px;height:20px;clear:both;float: right;margin-right:5%;margin-top:-10px;z-index:200;"
					id='rId'>
					<img onclick="right()" id = 'rightId' src="<%=basePath%>/images/right.png">
					<img onclick="up()" src="<%=basePath%>/images/up.png" style = "margin-top:280px;"> 
					<img onclick="down()" src="<%=basePath%>/images/next.png" style = "margin-top:10px;">
				</div>
                
				<div id="c1"
					style="width:90%;height:200px;float:left;font-size:16px;margin-left:30px"
					class="chart-legend"></div>
			</div>
			<div id="topP" style="display:none;padding-top:10px">
				<span style="font-size:18px;color:#fff;margin-left:40%">突出问题矩阵</span>
				<div
					style="width:20px;height:20px;clear:both;float: left;margin-right: 10px;margin-left:15px; margin-top:6px;">
					<img onclick="left()" src="<%=basePath%>/images/left.png">
				</div>
				<div id="c2"
					style="width:95%;height:50%;float:left;font-size:16px;margin-left:30px;"
					class="chart-legend"></div>
			</div>
			<input id="arrI10" type="hidden"> <input id="arrOTop" type="hidden"><input id="arrO"
				type="hidden"> <input id="pag" type="hidden"> </div>
		</div>
		
			<script type="text/javascript">
	
	    
		function findDataNoSelect() {
	
			$.ajax({
				url : '/jubao/dataPredict/data-predict-fpm-info!findSelectData.action',
				type : "post",
				data : {
					swFlag : swFlag,
					dateFlag : dateFlag,
					startDateValue : startDate,
					endDateValue : endDate,
				},
				success : function(res) {
					//打印日志  OPERATION_LOG表查找信息
					var params = {
						operationFuncCode : "findSelectData",
						operationFuncName : "查询问题类别与行政级别隐藏的关联关系下拉框的区域",
						operationBtnName : dateFlag,
						operationDesc : "查询问题类别与行政级别隐藏的关联关系下拉框的区域",
						operationTypeCode : OperationType.QUERY,
						operationTypeName : OperationType.QUERY_NAME,
						enableDataLog : true
					};
					saveOperaLog(params);
					var data = eval('(' + res + ')'); //转换
					regNameStr = data.result[0].regNameStr;//区域串（下拉框显示的区域）
					regNameArr = regNameStr.split(",");
					
					for(var i = 0; i < regNameArr.length; i++){
					     $("#qudd").append("<option value='"+regNameArr[i]+"'>"+regNameArr[i]+"</option>");
					     
					}
					$("#qudd").find("option[val='"+regNameArr[0]+"']").attr("selected","selected");
					var aucNum=$("#qudd option:selected").text();
					findData(aucNum, 0);
				},
				error : function() {
					alert("数据加载失败！");
				}
			});
		}
	</script>
		
		
	<script type="text/javascript">
	
	    
		function findData(regName, frequency) {
	
			$.ajax({
				url : '/jubao/dataPredict/data-predict-fpm-info!findDataQL.action',
				type : "post",
				data : {
					swFlag : swFlag,
					dateFlag : dateFlag,
					startDateValue : startDate,
					endDateValue : endDate,
					regName : regName,
					frequency : frequency
				},
				success : function(res) {
					//打印日志  OPERATION_LOG表查找信息
					var params = {
						operationFuncCode : "findDataQL",
						operationFuncName : "查询问题类别与行政级别隐藏的关联关系",
						operationBtnName : dateFlag,
						operationDesc : "问题类别与行政级别色块图",
						operationTypeCode : OperationType.QUERY,
						operationTypeName : OperationType.QUERY_NAME,
						enableDataLog : true
					};
					saveOperaLog(params);
	
					var data = eval('(' + res + ')'); //转换
					console.info(data)
					if(data.result.length == 0){
						$("#c1").html("<div style='text-align:center;line-height: 390px;color: white;'>当前选中时间段暂无数据</div>");
						return;
					}
					result = data.result[0].data; //取数据
					issueTypeLen = data.result[0].issueTypeLen; //获取问题类别个数
					issueTypeNameArr = data.result[0].issueTypeName; //获取问题类别
					objectClassNameArr = data.result[0].objectClassName; //获取行政级别
					pag = data.result[0].pag; //分页数
					$("#pag").val(pag);
					$("#arrO").val(objectClassNameArr);
					$("#arrI10").val(issueTypeNameArr);
					$("#c1").empty(); //清空图表
					proL(result); //绘图
				},
				error : function() {
					alert("数据加载失败！");
				}
			});
	
		}
	</script>
	
	<script type="text/javascript">
		function topRe() {
			$.ajax({
				url : '/jubao/dataPredict/data-predict-fpm-info!findDataTOP.action',
				type : "post",
				data : {
					swFlag : swFlag,
					dateFlag : dateFlag,
					startDateValue : startDate,
					endDateValue : endDate,
				},
				success : function(res) {
					//打印日志  OPERATION_LOG表查找信息
					var params = {
						operationFuncCode : "findDataTOP",
						operationFuncName : "top5隐藏的关联关系",
						operationBtnName : dateFlag,
						operationDesc : "top5色块图",
						operationTypeCode : OperationType.QUERY,
						operationTypeName : OperationType.QUERY_NAME,
						enableDataLog : true
					};
					saveOperaLog(params);
					var data = eval('(' + res + ')'); //转换
					if(data.result.length == 0){
						$("#c2").html("<div style='text-align:center;line-height: 390px;color: white;'>当前选中时间段暂无数据</div>");
						return;
					}
					result = data.result[0].data; //取数据
					issueTypeNameArr = data.result[0].issueTypeName; //获取问题类别
					$("#arrOTop").val(issueTypeNameArr);
					$("#arrITop").val(issueTypeNameArr);
					top5(result); //绘图
				},
				error : function() {
					alert("数据加载失败！");
				}
			});
	
		}
	</script>
	<script>
		function proL(listAll) {
			var data = listAll;
			var source = [];
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				var obj = {};
				obj.name = item[0];
				obj.day = item[1];
				obj.支持度 = item[2];
				source.push(obj);
			}
			var chart = new G2.Chart({
				id : 'c1',
				forceFit : true,
				height : 370,
				plotCfg : {
					margin : [ 20, 80, 80, 205 ] //顺时针
				}
			});
			chart.source(source, {
				name : {
					type : 'cat',
					values : $("#arrO").val().split(",")
				},
				day : {
					type : 'cat',
					values : $("#arrI10").val().split(",")
				}
			});
			chart.axis('name', {
				//换行函数
				formatter : function(val) {
					if (val.length > 2) {
						qTwo = val.substr(0, 2); //取字符串前两个字符
						hOther = val.substr(2, val.length); //取字符串后几位
						val = qTwo + "\n" + hOther //拼接
						return val
					}
					if (val.length <= 2) {
						return val
					}
				},
				tickLine : {
					stroke : '#FFF',
					value : 6 // 刻度线长度
				},
				labels : {
					autoRotate : false, //文本是否允许自动旋转
					label : {
						textAlign : 'center', // 文本对齐方向,可取值为： left center right
						fill : '#FFF'
					}
				},
				line : {
					stroke : '#FFF'
				},
				title : null,
				grid : {
					line : {
						stroke : '#d9d9d9',
						lineWidth : 1,
						lineDash : [ 2, 2 ]
					}
				}
			});
			chart.axis('day', {
				tickLine : {
					stroke : '#FFF',
					value : 6 // 刻度线长度
				},
				labels : {
					label : {
						fill : '#FFF'
					}
				},
				line : {
					stroke : '#FFF'
				},
				title : null
			});
			chart.legend({
				word : {
					fill : '#FFF'
				},
				position : 'bottom', // 设置图例的显示位置
				dy : 30 // 图例项之间的水平间距
			});
			chart.polygon()
				.position('name*day')
				.color('支持度', '#f6f6f6-#36B3C3')
				.label('支持度', {
					offset : -2,
					label : {
						fill : '#444',
						fontWeight : 'bold'
					}
				})
				.style({
					lineWidth : 1,
					stroke : '#fff'
				});
			chart.render();
	
		}
		//点击右剪头执行的方法
		function right() {
			//样式隐藏与显示
			$("#dl").css("display", "none");
			$("#c1").css("display", "none");
			$("#c2").css("display", "block");
			$("#topP").css("display", "block");
			$("#quse").css("display", "none");
			$("#paizhuse").css("display", "none");
			topRe(); //绘制问题TOP5色块图
			$("#c1").empty(); //清空图表
		}
		//点击左剪头执行的方法
		function left() {
			//样式隐藏与显示
			$("#topP").css("display", "none");
			$("#c2").css("display", "none");
			$("#dl").css("display", "block");
			$("#c1").css("display", "block");
			$("#quse").css("display", "block");
			$("#paizhuse").css("display", "block");
			var q = document.getElementById("qudd");
			regName = q.options[q.selectedIndex].value;
// 			var p = document.getElementById("paizhu");
// 			paizhu = p.options[p.selectedIndex].value;
			findData(regName, 0);
			$("#c2").empty(); //清空图表
			if(swFlag == 'bw'){
			   	$("#quse").hide();
// 			    $("#paizhuse").hide();
			} 
		}
	
	
		var n = 0; //全局变量,用来控制反转上下页
		function up() { 
			if (n > 0) {
				n = n - 1;
				$("#topP").css("display", "none");
				$("#c2").css("display", "none");
				$("#dl").css("display", "block");
				$("#c1").css("display", "block");
				$("#c1").empty(); //清空图表
				$("#c2").empty(); //清空图表
				var q = document.getElementById("qudd");
				regName = q.options[q.selectedIndex].value;
// 				var p = document.getElementById("paizhu");
// 				paizhu = p.options[p.selectedIndex].value;
				findData(regName, n);
			}
		}
		function down() {
			//判断分页数，避免无限分页
			if (n < $("#pag").val() - 1) {
				n = n + 1;
				$("#topP").css("display", "none");
				$("#c2").css("display", "none");
				$("#dl").css("display", "block");
				$("#c1").css("display", "block");
				$("#c1").empty(); //清空图表
				$("#c2").empty(); //清空图表
				var qudd = document.getElementById("qudd");
				regName = qudd.options[qudd.selectedIndex].value;
// 				var p = document.getElementById("paizhu");
// 				paizhu = p.options[p.selectedIndex].value;
				findData(regName, n);
			}
	
		}
	
		$(document).ready(function() {
			$("#c1").empty();
			$("#c2").empty();
			$("#topP").css("display", "none");
			$("#dl").css("display", "block");
			findDataNoSelect();
		});
	</script>
	<script>
		function top5(result) {
// 			var data = [ [ 0, 0, 0 ], [ 0, 1, 19 ], [ 0, 2, 8 ], [ 0, 3, 20 ], [ 0, 4, 45 ], [ 1, 0, 92 ], [ 1, 1, 0 ], [ 1, 2, 78 ], [ 1, 3, 278 ], [ 1, 4, 18 ], [ 2, 0, 35 ], [ 2, 1, 15 ], [ 2, 2, 0 ], [ 2, 3, 223 ], [ 2, 4, 452 ], [ 3, 0, 72 ], [ 3, 1, 132 ], [ 3, 2, 114 ], [ 3, 3, 0 ], [ 3, 4, 214 ], [ 4, 0, 214 ], [ 4, 1, 14 ], [ 4, 2, 24 ], [ 4, 3, 4 ], [ 4, 4, 0 ] ];
            var data = result;
			var source = [];
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				var obj = {};
				obj.name = item[0];
				obj.day = item[1];
				obj.支持度 = item[2];
				source.push(obj);
			}
			var chart = new G2.Chart({
				id : 'c2',
				forceFit : true,
				height : 370,
				plotCfg : {
					margin : [ 20, 80, 80, 110 ]
				}
			});
			chart.source(source, {
				name : {
					type : 'cat',
					//values: $("#arrO").val().split(",")
					values : $("#arrOTop").val().split(",")
				},
				day : {
					type : 'cat',
					//values: $("#arrI").val().split(",")
					values : $("#arrOTop").val().split(",")
				}
			});
			chart.axis('name', {
			//换行函数
				formatter : function(val) {
					if (val.length > 8) {
						qTwo = val.substr(0, 8); //取字符串前两个字符
						hOther = val.substr(8, val.length); //取字符串后几位
						val = qTwo + "\n" + hOther //拼接
						return val
					}
					if (val.length <= 8) {
						return val
					}
				},
				tickLine : {
					stroke : '#FFF',
					value : 6 // 刻度线长度
				},
				labels : {
					label : {
						fill : '#FFF'
					}
				},
				line : {
					stroke : '#FFF'
				},
	
				title : null,
				grid : {
					line : {
						stroke : '#d9d9d9',
						lineWidth : 1,
						lineDash : [ 2, 2 ]
					}
				}
			});
			chart.axis('day', {
			//换行函数
				formatter : function(val) {
					if (val.length > 9) {
						qTwo = val.substr(0, 9); //取字符串前两个字符
						hOther = val.substr(9, val.length); //取字符串后几位
						val = qTwo + "\n" + hOther //拼接
						return val
					}
					if (val.length <= 9) {
						return val
					}
				},
				tickLine : {
					stroke : '#FFF',
					value : 6 // 刻度线长度
				},
				labels : {
					label : {
						fill : '#FFF'
					}
				},
				line : {
					stroke : '#FFF'
				},
	
				title : null
			});
			chart.legend({
				word : {
					fill : '#FFF'
				},
				position : 'bottom', // 设置图例的显示位置
				dy : 30 // 图例项之间的水平间距
			});
			chart.polygon()
				.position('name*day')
				.color('支持度', '#f6f6f6-#36B3C3')
				.label('支持度', {
					offset : -2,
					label : {
						fill : '#444',
						fontWeight : 'bold'
					}
				})
				.style({
					lineWidth : 1,
					stroke : '#fff'
				});
			chart.render();
		}
	</script>

	<script>
	//获取区下拉框值
	function quChange(obj){
	    //点击区下拉框默认显示第一页色块图 隐藏其他图 并且清空之前绘制的图形
	    $("#topP").css("display", "none");
	    $("#c2").css("display", "none");
		$("#dl").css("display", "block");
		$("#c1").css("display", "block");
		$("#c1").empty(); //清空图表
		$("#c2").empty(); //清空图表
// 		var checkText=$("#qudd").find("option:selected").text(); 
// 		console.info(checkText)
// 		$("#qudd").find('option[value="崇明区"]').attr("selected",true);
// 		document.getElementById("paizhu").options.selectedIndex = 0;
		n = 0;
		findData(obj.value, 0);
	}
// 	//获取派驻下拉框值
// 	function paizhuChange(obj){
// 	 	//点击区下拉框默认显示第一页色块图 隐藏其他图 并且清空之前绘制的图形
// 	    $("#topP").css("display", "none");
// 	    $("#c2").css("display", "none");
// 		$("#dl").css("display", "block");
// 		$("#c1").css("display", "block");
// 	    $("#c1").empty();//清空图表
// 		$("#c2").empty();//清空图表
// 		document.getElementById("qudd").options.selectedIndex = 0;
// 		n = 0;
// 		findData(null, obj.value, 0);
// 	}
		if(swFlag == 'bw'){
		   	$("#quse").hide();
		    $("#paizhuse").hide();
		} 
	</script>

</body>
</html>



