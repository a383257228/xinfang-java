<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
    
    <script src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
    <script src="<%=basePath%>/publicresource/js/d3.v3.min.js"></script>
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
  </head>
 <body>
 	<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/grid.css">
 	<style>
 		.pagination-num,.pagination-page-list{
		    background:#BBD0DE;
		    font-size:14px;
		    text-align:center
	    }
	</style>
  <div style="width:98%;height:430px;background:url(<%=basePath%>/images/bk2.png) no-repeat;background-size:100%;padding-top:12px">
    <div id="graphdiv" style="height:410px;margin-left:15px;background:url(<%=basePath%>/images/bjk1.png) no-repeat;background-size:98%;padding-top:17px">        

       <div id="dSearch" ></div>
       <div id="dNexus" style = "display:none;" >
	       <jsp:include page="/DataPredict/AlarmG2.jsp"/>
         </div>

     </div>
  </div>
    <script>
    $(document).ready(function(){
	var availableTags = []
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

    		var jsonData = JSON.parse(d);  
    		var jj = JSON.stringify(jsonData.result); 
    		jj=jj.replace(/ACCUSED_1/g,"source").replace(/ACCUSED_2/g,"target").replace(/DISTANCE/g,"type");
   
    		links=eval(jj); 
    		if(svg)svg.remove();
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
    function getNexusInfo(a){
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
    }
</script>
  </body>

</html>
