<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String swFlag = request.getParameter("swFlag");
	String bwRadio = request.getParameter("bwRadio");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src="<%=basePath%>/publicresource/js/g2.js"></script>
</head>
<body>


   <style>
     .bk{
	     width:98%;
	     height:430px;
	     background:url(<%=basePath%>/images/bk.png) no-repeat;
	     padding-top:10px;
	     }
	     .tie{
	        height:420px;
	        margin-left:15px;
	        background:url(<%=basePath%>/images/bjk2.png) no-repeat;
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
  <div class="bk">
     <div class="tie">      
         <div id="HB" style="cursor: hand"></div>
     </div>
  </div>
<script>
	  var swFlag = "<%=swFlag%>";
	  var bwRadio = "<%=bwRadio%>";
	  var datas ;
	  $.ajax({
	 	    url:'<%=basePath%>/petition/petition-basic-info!SequentialQuery.action',  
	 	    type : "post",
	        async : false,
	 	    data:{
	 	    	swFlag : swFlag,
	 	    	bwRadio : bwRadio
	 	    },
	 	    success:function(data){
	 	    	var params={
 	    			operationFuncCode : "SequentialQuery",
 	    			operationFuncName : "环比主查询",
 	    			operationBtnName : dateFlag,
 	    			operationDesc : "环比主查询",
 	    			operationTypeCode : OperationType.QUERY,
 	    			operationTypeName : OperationType.QUERY_NAME,
 	    			enableDataLog :true
 	    		}
 	    		saveOperaLog(params);
	 	    	datas = eval('(' + data.substring(0,(data.indexOf("]")+1)) + ')');
	 	    },
	        error:function(){  
	        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
	        }  
      });
	  
      var Frame = G2.Frame;
      var frame = new Frame(datas);
      frame = Frame.combinColumns(frame, ['数量'],'数量','type',['time', 'percent']);
      var chart = new G2.Chart({
	        id: 'HB',
	        forceFit: true,
	        height: 400
      });
      chart.source(frame, {
        '数量': {
        	alias: '数量（件）'
         },
        'percent': {
        	alias: '环比（%）'
         }
      });
      chart.axis('time', {
    	  labels: {
              label: {
            	  fill: '#ffffff' 
              }
          },
          title: null
      });
      chart.axis('percent', {
    	  title:{
    		  textAlign : 'center',
    		  fill:'#fff'
    	  },
    	  labels: {
              label: {
            	  fill: '#ffffff' 
              }
          },
      });
      chart.axis('数量', {
    	  title:{
    		  textAlign : 'center',
    		  fill:'#fff'
    	  },
    	  labels: {
              label: {
            	  fill: '#ffffff'  
              }
          },
      });
      chart.legend({
    	  position:'top',
    	  marker: 'square', // 设置图例 marker 的显示样式
    	  word: {
            	  fill: '#ffffff' 
            },
      });
      chart.intervalStack().position('time*数量').color('type', ['#43D3FF']); // 绘制层叠柱状图
      chart.line().position('time*percent').color('#f3db5d').size(2).shape('smooth'); // 绘制曲线图
      chart.point().position('time*percent').color('#b35fff'); // 绘制点图
      chart.render();
      
      chart.on('plotclick',function(ev){
    	    if(ev.data==undefined){
     		   return;
     	    }
    	    if(monthMainObj){
 				monthMainObj.panel("options").swFlag=swFlag;
 				monthMainObj.panel("options").bwRadio=bwRadio;
 				monthMainObj.panel("options").month=ev.data._origin.time;
 				monthMainObj.panel("options").JQFlag="sequent";
 				monthMainObj.panel("options").YN="1";
 		    }else{
				monthMainObj = $.f("div","MonthWin").window({
			 		  width:910, 
			 		  height:580,
			 		  title:'问题类别数量分布情况',
			 		  modal:true,
			 		  maximizable:false,
			 		  minimizable:false,
			 		  collapsible:false,
			 		  closed:true,
			 		  swFlag:swFlag,
			 		  bwRadio:bwRadio,
			 		  month:ev.data._origin.time,
			 		  JQFlag:"sequent",
			 		  YN:"2",
			 		  onOpen:function(){
			 			  monthWin.init();
			 		  },
			 		  onClose:function(){
			 			    monthMainObj.panel("options").swFlag="";
			 				monthMainObj.panel("options").bwRadio="";
			 				monthMainObj.panel("options").month="";
			 				monthMainObj.panel("options").JQFlag="";
			 				monthMainObj.panel("options").YN="";
			 		  }
		 		});
 		    }
	   		monthMainObj.window("open");
	 });
	</script>
</body>
</html>