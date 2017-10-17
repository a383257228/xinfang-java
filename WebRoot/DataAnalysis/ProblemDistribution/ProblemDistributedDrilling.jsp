<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
  </head>
  <body>
  	<div idFlag="ProblemDisWin" style="display: none">
	 	 <div id="proDisDri" style="width:100%; height:440px;margin-top:-40px"></div>
  	</div>
  </body>
<script>
	var ProblemDisWinObj;
	$.SUI("ProblemDistribute");
	ProblemDistribute = {
		 init : function(){
			 var swFlag=ProblemDisWinObj.panel("options").swFlag;
			 var dateFlag=ProblemDisWinObj.panel("options").dateFlag;
			 var startDate=ProblemDisWinObj.panel("options").startDate;
			 var endDate=ProblemDisWinObj.panel("options").endDate;
			 var bwRadio=ProblemDisWinObj.panel("options").bwRadio;
			 var name=ProblemDisWinObj.panel("options").name;
			 
			 var issueTypeName = new Array();
			 var dataValue;
			 $.ajax({//查询违纪行为
			 	    url:'<%=basePath%>/petition/petition-issue-info!queryWJSubclass.action',  
			 	    type : "post",
			        async : false,
			 	    data:{
			 	    	swFlag : swFlag,
			 	    	dateFlag : dateFlag,
			 	    	startDateValue　: startDate,
			 	    	endDateValue :　endDate,
			 	    	issueTypeName : name,
			 	    	bwRadio : bwRadio
			 	    },
			 	    success:function(data){
			 	    	var params={
		 	    			operationFuncCode : "queryWJSubclass",
		 	    			operationFuncName : "问题类别分布情况钻取查询",
		 	    			operationBtnName : dateFlag,
		 	    			operationDesc : "问题类别分布情况钻取查询",
		 	    			operationTypeCode : OperationType.QUERY,
		 	    			operationTypeName : OperationType.QUERY_NAME,
		 	    			enableDataLog :true
		 	    		}
		 	    		saveOperaLog(params);
			 	    	
			 	    	datas = eval('(' + data + ')');
			 	    	dataValue = datas;
			 	    	datas.forEach(function(ele,datas){
			 	    		issueTypeName.push(ele.name);
			            });
			        },
			        error:function(){
			        	dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;数据加载失败!', 3000);
			        	return;
			        }  
		    });
			
			 
			var myChart = echarts.init(document.getElementById('proDisDri'));
			option = {
				    title : {
				    },
				    legend: {
				    	orient: 'vertical',
				        x : '1%', 
				        y:'77%',
				        data:issueTypeName,
				        textStyle:{color:'#ffffff'},
				    },
				    toolbox: {
				        show : false,
				    },
				    tooltip : {    
			            trigger: 'item',
			            formatter: "{b} : {c}"    
			        },
				    calculable : true,
				    series : [
				        {
				            name:'面积模式',
				            type:'pie',
				            radius : [30, 110],
				            center : ['50%', 200],
				            roseType : 'area',
				            x: '50%',
				            max: 40,
				            sort : 'ascending',
				            data:dataValue,
				            itemStyle: {//每块样式    
			                    normal: { 
			                  	    color:function(params){
			  		    				var colorList=['#59B1F0','#FF9E4D', '#F8545D', '#A86CC4', '#FCE303','#0AD509','#8D98B3','#5AB1EF', '#E5CF0D', '#8D98B3', '#2EC7C9','#97B552','#FFB980','#D87A80']
			  		    				return colorList[params.dataIndex]
		  		    				}
			                    }    
			                }
				        }
				    ]
			};
			myChart.setOption(option,true);
			myChart.on('click',function(param){
				if(problemInfoWinObj){
					 problemInfoWinObj.panel("options").swFlag=swFlag;
					 problemInfoWinObj.panel("options").dateFlag=dateFlag;
					 problemInfoWinObj.panel("options").startDate=startDate;
					 problemInfoWinObj.panel("options").endDate=endDate;
					 problemInfoWinObj.panel("options").bwRadio=bwRadio;
					 problemInfoWinObj.panel("options").name=param.name;
			   	 }else{
			   		problemInfoWinObj = $.f("div","ProblemInfoWin").window({
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
			   			bwRadio:bwRadio,
			   			name:param.name,
			   			onOpen:function(){
			   				problemInfoWin.init();
			   			},
			   			onClose : function(){
			   				$.f("div", "problemFindPanel").f("input","accusedName").textbox('setValue',"");
			   				$.f("div", "problemFindPanel").f("input","accuserName").textbox('setValue',"");
			   				problemInfoWinObj.panel("options").swFlag="";
							problemInfoWinObj.panel("options").dateFlag="";
							problemInfoWinObj.panel("options").startDate="";
							problemInfoWinObj.panel("options").endDate="";
							problemInfoWinObj.panel("options").bwRadio="";
							problemInfoWinObj.panel("options").name="";
			   			}
			   		});
			   	}
				problemInfoWinObj.window("open");
		   });
		 }
	}
</script>
</html>