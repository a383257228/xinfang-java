<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String swFlag = request.getParameter("swFlag");
	String dateFlag = request.getParameter("dateFlag");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
    <script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
  </head>
     <style>
   
   .link {
  fill: none;
  stroke: #666;
  stroke-width: 1.5px;
}

#licensing {
  fill: green;
}

.link.licensing {
  stroke: green;
}

.link.resolved {
  stroke-dasharray: 0,2 1;
}

circle {
  stroke: #333;
  stroke-width: 1.5px;
}

text {
  font: 10px sans-serif;
  pointer-events: none;
  text-shadow: 0 1px 0 #fff, 1px 0 0 #fff, 0 -1px 0 #fff, -1px 0 0 #fff;
}
   
		
  </style>
  <body>
  
   <style>
   
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
	    @media only screen and (max-width: 1024px) {
		   .bk{
		    background:url(<%=basePath%>/images/bk2.png) no-repeat;
	    }
	   .tie{
	        background:url(<%=basePath%>/images/bjk1.png) no-repeat;
	         background-size:96.5%;
	     }
		}
		
		
  </style>
  <div id = "top5Left">
	  <div class="bk">
	      <div class="tie">
	          <div style="font-size:18px;color:#fff;text-align:center;padding-top:5px">数量前五位问题类别排名  
	          <div style="width:20px;height:20px;clear:both;float: right;margin-right:40px;margin-bottom:2px;cursor: hand;">
		             <img  onclick="right()" src="<%=basePath%>/images/right.png">
		       </div>
		       </div>
	          <div id="top5" style="height: 390px; width: 98%;margin-left:15px;margin-top:-20px;padding-left:10px;"></div>
	      </div>
	 
	  </div>
  </div>
  <div id = "treeForRight" style = "display:none">
    <div  class="bk">
    <div id="graphdiv" class="tie">        
				<div style="font-size:18px;color:#fff;text-align:center;padding-top:5px">树木与森林 
	          <div style="width:20px;height:20px;clear:both;float: left;margin-left:18px;margin-bottom:2px;cursor: hand;">
		             <img  onclick="left()" src="<%=basePath%>/images/left.png">
		       </div>
		       </div>
       <div id="dSearch" ></div>
       <div id="dNexus" style = "display:none;" >
	       <%-- <jsp:include page="/DataPredict/AlarmG2.jsp"/> --%>
	       
         </div>

     </div>
  </div>
  </div>
<script>
 	var swFlag = "<%=swFlag%>";
	var dateFlag = "<%=dateFlag%>";
	var startDate = "<%=startDate%>";
	var endDate = "<%=endDate%>";
	var nameObj = new Array(); 
	var countObj = new Array();
	var code = new Array();
      //柱状图
    var myChart = echarts.init(document.getElementById('top5'));
    var option ;
    $.ajax({
    	url:'<%=basePath%>/dataPredict/data-predict-tree-forest!issueTypeTop5.action',
 	    type : "post",
 	    data:{
 	    	swFlag : swFlag,
 	    	dateFlag : dateFlag,
 	    	startDateValue　: startDate,
 	    	endDateValue :　endDate
 	    },
 	    success:function(data){
			 var params={
						operationFuncCode : "issueTypeTop5",
						operationFuncName : "数量前五位问题类别排名",
						operationBtnName : dateFlag,
						operationDesc : "信访基本信息表",
						operationTypeCode : OperationType.QUERY,
						operationTypeName : OperationType.QUERY_NAME,
						enableDataLog :true
				};
 			saveOperaLog(params);
 	    	datas = eval('(' + data + ')');
			if(datas.length==0){
				$("#top5").html("<div style='margin-left:250px;line-height: 390px;color: white;'>当前选中时间段暂无数据</div>");
				return;
			}
 	    	datas.forEach(function(ele,datas){
 	    		nameObj.push(ele.name);
 	    		code.push(ele.issueTypeCode);
 	    		countObj.push(ele.count);
 	    	});
 	    	option = {
    	   		  title : {
    	   		        textStyle:{
    	   		        	color:'#fff',
    	   		        	fontSize:16,
    	   		        }
    	   		  },
    	 		  tooltip : {
    	 		       trigger: 'axis'
    	 		  },
    	  		  calculable : true,
    	  		  grid:{ //外边框线设置为0
    	         		borderWidth:0.5,
    	         		x:'24%'
    	          },
    	  		  xAxis : [{
    	  		    	/* show : false,//去掉x轴坐标    		 */    	
    	  		    	type : 'value',
    	  		    	boundaryGap : [0, 0.01],
    	  		    	axisLine:{show:false},
    	  		    	axisLabel: {
    	  		    		textStyle: {
    	  		    			color: '#ffffff',
    	 		    		},
    	  		    	},
    		   			splitLine:{
    		   				lineStyle:{
    		   					 color: '#ffffff',
    		   					 width: 0.5,
    		   					 type: 'dashed'
    		   				}
    		   			}
    	  		  }],
    	  		  yAxis : [{
    	  		   	   type : 'category',
    	  		       splitLine:{show:false},  
    	  		       axisLabel: {
    	  		    		textStyle: {
    	  		    			color: '#ffffff',
    	  		    		},
    	  		    	},
   	  		            data : nameObj
    	  		   }],
    	  		  series : [{
    	  		    	name:'数量',
    	  		    	type:'bar',
    	  		    	itemStyle:{ 
    	  		    		normal:{
    	  		    			color:function(params){
    	  		    				var colorList=['#C79550', '#6AA372', '#00B9E1', '#EF6442','#EF0B33'];
    	  		    				return colorList[params.dataIndex]
    	  		    			},
    	  		    			label: {
    	  		    				show: true,
    	  		    				position: 'right',
    	  		    				formatter: '{c}',
    	  		    				textStyle:{color:'#ffffff'}
    	  		    			}
    	  		    		}
    	  		    	}, 
    	  		        barWidth:30,//柱子的宽度
    	  		        data :countObj
    	  		   }]
    	 	 };
 	    	 myChart.setOption(option,true);
 	    	/*  myChart.on('click',function(param){
 	    		top5WinObj = $.f("div","top5DrillingWin").window({
		    		    width:750, 
		    		    height:480,
		    		    title:'问题分布',
		    		    modal:true,
		    			maximizable:false,
		    			minimizable:false,
		    			collapsible:false,
		    			closed:true,
		    			onOpen:function(){
		    				top5DrillingWin.init(swFlag,dateFlag,startDate,endDate,param.name);
		    			}
		    		});
 	    		top5WinObj.window("open");
		     }); */
        },
        error:function(){  
        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
        }  
    }); 
    
    
    $(document).ready(function(){
    	getEdges();
    	var availableTags = [];
    	$.ajax({
    		url:'/jubao/dataPredict/data-predict-tree-forest!gTest.action',
    		type:"post",
    		data:{
    			aname : document.getElementById('search4Predict').value
    			},
    		success:function(d){
    			var jsonData = JSON.parse(d).result;
    			var jj = JSON.stringify(jsonData).replace(/ACCUSED/g,"name");
    			var xqo = eval('(' + jj + ')');
    			for(var i in xqo){
    				availableTags.push(xqo[i].name);
    			}
    			}
    		});
    		$("#search4Predict").autocomplete({
    			source: availableTags
    		});
    });
        var smallname;		//搜索框里
        var middlename;		//表格里
        var bigname;		//图上
             
        var links = [];
        var autonames=[];

        // Use elliptical arc path segments to doubly-encode directionality.
        function tick() {
          path.attr("d", linkArc);
          circle.attr("transform", transform);
          text.attr("transform", transform);
        }

        function linkArc(d) {
          var dx = d.target.x - d.source.x,
              dy = d.target.y - d.source.y,
              dr = Math.sqrt(dx * dx + dy * dy);
          return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
        }

        function transform(d) {
          return "translate(" + d.x + "," + d.y + ")";
        }
        var svg;

        function getDSearch(){
        	smallname=document.getElementById('search4Predict').value;
        	//alert(middlename);
        	var b="<%=basePath%>/DataPredict/AlarmG.jsp";	
        	$("#dSearch").window({
        		title:'搜索：'+smallname,
        		href:b,
        		width:850,
        		height:490,
        		collapsible:false,
        		minimizable:false,
        		maximizable:false,
        		closable:true,
        		});
        }

        function getNames(){
            alert(1)
            }
        
        function getEdges(){
            $.ajax({   		
        		url:'/jubao/dataPredict/data-predict-tree-forest!gTest2.action', 
        		type : "post",
        		data:{
        			aname : document.getElementById('search4Predict').value,//middlename,//document.getElementById('text111').value,
        			detail:0,	//不要细节
        			nexus:1,	//双向关系
        			search:1	//精确搜索			    	
        		},
        		success:function(d){
        			 var params={
      						operationFuncCode : "gTest2",
      						operationFuncName : "查询树木与森林",
      						operationBtnName : dateFlag,
      						operationDesc : "检举人联系表",
      						operationTypeCode : OperationType.QUERY,
      						operationTypeName : OperationType.QUERY_NAME,
      						enableDataLog :true
      				};
        			saveOperaLog(params);
        		var jsonData = JSON.parse(d);  
        		var jj = JSON.stringify(jsonData.result); 
        		if(svg)svg.remove();
        		if(jj.length==2){
        			alert("请重新输入");
            		}
        		jj=jj.replace(/ACCUSED_1/g,"source").replace(/ACCUSED_2/g,"target").replace(/DISTANCE/g,"type");
       
        		links=eval(jj); 
        		
        		var nodes = {};

        		// Compute the distinct nodes from the links.
        		links.forEach(function(link) {
        		  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
        		  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
        		});

        		var width = 770,
        		    height = 360;

        		force = d3.layout.force()
        		    .nodes(d3.values(nodes))
        		    .links(links)
        		    .size([width, height])
        		    .linkDistance(60)
        		    .charge(-300)
        		    .on("tick", tick)
        		    .start();

        		svg = d3.select("#graphdiv").append("svg")
        		    .attr("width", width)
        		    .attr("height", height);

        		// Per-type markers, as they don't inherit styles.
        		svg.append("defs").selectAll("marker")
        		    .data(["suit", "licensing", "resolved"])
        		  .enter().append("marker")
        		    .attr("id", function(d) { return d; })
        		    .attr("viewBox", "0 -5 10 10")
        		    .attr("refX", 15)
        		    .attr("refY", -1.5)
        		    .attr("markerWidth", 6)
        		    .attr("markerHeight", 6)
        		    .attr("orient", "auto")
        		  .append("path")
        		    .attr("d", "M0,-5L10,0L0,5");

        		path = svg.append("g").selectAll("path")
        		    .data(force.links())
        		  .enter().append("path")
        		    //.attr("class", function(d) { return "link " + d.type; })
        		    .attr("class", function(d) { return "link " +"licensing"; })
        		    //.attr("marker-end", function(d) { return "url(#" + d.type + ")"; })
        		    .attr("marker-end", function(d) { return "url(#" + "licensing" + ")"; })
        		    .attr("fill","none")
        		    .attr("stroke","#ccc");

        		circle = svg.append("g").selectAll("circle")
        		    .data(force.nodes())
        		  .enter().append("circle")
        		    .attr("r", 10)
        		    .call(force.drag)
        		    .attr("fill","#3ec2e7")
        		    .attr("stroke","#ccc")
        		    .attr("stroke-width","1.5")
        		    .attr("style","cursor: hand")
        		    .on("click", function(d){
        			    getNexusInfo(d.name);
        			    });
        	    text = svg.append("g").selectAll("text")
        	    .data(force.nodes())
        	  .enter().append("text")
        	    .attr("x", 8)
        	    .attr("y", ".31em")
        	    .text(function(d) { return d.name; })
        	    .attr("fill","#fff");
        		}  		
        	});   
        }
        <%-- function getNexusInfo(a){
        	$("#dNexus").css("display","none");
        	bigname=a;
        	var b="<%=basePath%>/DataPredict/AlarmG2.jsp";
        	//alert(a);
        	categoryMainObj = $.f("div","categoryMain").window({
     	    		    width:800, 
     	    		    height:500,
     	    		    title:a+'关系表',
     	    		    modal:true,
     	    			maximizable:false,
     	    			minimizable:false,
     	    			collapsible:false,
     	    			closed:true,
     	    			onOpen:function(){
     	    				categoryMainWin.init();
     	    			}
     	    		});
     	    	 categoryMainObj.window("open");
     	    /*
        	$("#haha2").window({
        		title:a+'关系表',
        		href:b,
        		queryParams:{
        	    	aname : a
        	    },
        		width:850,
        		height:490,
        		collapsible:false,
        		minimizable:false,
        		maximizable:false,
        		closable:true,
        		});*/
        } --%>
    function getNexusInfo(name){
    	 if(TopMainInfoWinObj){
    		 TopMainInfoWinObj.panel("options").swFlag = swFlag;
    		 TopMainInfoWinObj.panel("options").dateFlag = dateFlag;
    		 TopMainInfoWinObj.panel("options").startDate = startDate;
    		 TopMainInfoWinObj.panel("options").endDate = endDate;
    		 TopMainInfoWinObj.panel("options").accusedName= name;
		  }	else {
				TopMainInfoWinObj = $.f("div","TopMainInfoWin").window({
		 		  width:960, 
		 		  height:600,
		 		  title:'信访件列表',
		 		  modal:true,
		 		  maximizable:false,
		 		  minimizable:false,
		 		  collapsible:false,
		 		  closed:true,
				  swFlag:swFlag,
		    	  dateFlag:dateFlag,
		    	  startDate:startDate,
		    	  endDate:endDate,
		    	  accusedName:name,
		 		  onOpen:function(){
		 			 TopMainInfoWin.init();
		 		  },
		 		  onClose : function(){
		 			 $.f("div", "qsFindPanel").f("input","accusedName").textbox('setValue',"");
		 			 $.f("div", "qsFindPanel").f("input","accuserName").textbox('setValue',"");
		 			TopMainInfoWinObj.panel("options").swFlag="";
		 			TopMainInfoWinObj.panel("options").dateFlag="";
		 			TopMainInfoWinObj.panel("options").startDate="";
		 			TopMainInfoWinObj.panel("options").endDate="";
		 			TopMainInfoWinObj.panel("options").accusedName="";
		 		  }
	 		});			
		 }//else end
		 TopMainInfoWinObj.window("open");
	}
    
    function right(){
    	$("#top5Left").css("display","none");
    	$("#searchTar4Left").css("display","block");
    	$("#treeForRight").css("display","block");
    }
    function left(){
    	$("#top5Left").css("display","block");
    	$("#searchTar4Left").css("display","none");
    	$("#treeForRight").css("display","none");
    }
</script>
</body>
</html>