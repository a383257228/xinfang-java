<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>"> 	
	<script src="<%=basePath%>/publicresource/js/g2.js"></script>
  </head> 
  <body>
  <style>
       .mouthDiv .datagrid-row-checked .datagrid-cell,
        .mouthDiv.datagrid-header .datagrid-cell span,
         .mouthDiv .pagination-info,
         .mouthDiv .pagination span,
          .mouthDiv .datagrid-cell-rownumber, 
          .mouthDiv .datagrid-cell-rownumber,.mouthDiv .datagrid-cell{
            color:#fff !important;
       }
        .mouthDiv .datagrid-header td, .mouthDiv .datagrid-body td, .mouthDiv .datagrid-footer td{
            border: 1px solid  #00BEC8 !important
       }
       .mouthDiv .datagrid-sort .datagrid-sort-icon, .mouthDiv .datagrid-row{
      	    background:none !important;
       }
       .mouthDiv .datagrid-header{
      	    background:#015190
       }
      .mouthDiv .m-btn-downarrow,  .mouthDiv .s-btn-downarrow{
        	background:none;
       }
  </style>
       <div idFlag="MonthWin" style="display: none">
       		 <div class="mouthDiv" idFlag="mouthDiv" style="margin-left:10px;width:880px;height:490px;"> 
            	 <table idFlag="monthGrid" style="margin-top:10px"></table>
	         </div>
	         <div style="width:30px;height:30px;clear:both;float: left;margin-left: 30px; margin-top: 10px;">
		             <img onclick="javascript:up()" src="<%=basePath%>/images/up.png">
		       </div>
		       <div style="width:30px;height:30px;clear:both;float: left;margin-left:80px; margin-top: -30px;">
		             <img  onclick="javascript:next()" src="<%=basePath%>/images/next.png">
		       </div>
	         <div style="width:30px;height:30px;clear:both;float: right;margin-right: 80px; margin-top: -30px;">
		             <img onclick="javascript:up()" src="<%=basePath%>/images/left.png">
		       </div>
		       <div style="width:30px;height:30px;clear:both;float: right;margin-right:30px; margin-top: -30px;">
		             <img  onclick="javascript:next()" src="<%=basePath%>/images/right.png">
		       </div>
       </div>
	<script type="text/javascript">
	var monthMainObj;
	$.SUI("monthWin");
	monthWin = {
		init : function(){
			var gridFlag=monthMainObj.panel("options").JQFlag;
			var JQFlag ;
			if(gridFlag=="sameCompare"){
				JQFlag=monthMainObj.panel("options").flag;
			}else if(gridFlag=="trend"||gridFlag=="sequent"){
				JQFlag = "";
			}
			var swFlag=monthMainObj.panel("options").swFlag;
			var bwRadio=monthMainObj.panel("options").bwRadio;
			var month=monthMainObj.panel("options").month;
			
			if("1"==monthMainObj.panel("options").YN){
				var mouthGrid = $.f("div","mouthDiv").f("table","monthGrid");
				var params ={
					swFlag :　swFlag,
					bwRadio : bwRadio,
					month : month,
					flag : JQFlag
				};
				mouthGrid.datagridSinosoft("gridoptions").queryParams = params;
				mouthGrid.datagridSinosoft("load","<%=basePath%>/petition/petition-basic-info!reportDrilling.action");
			}else if("2"==monthMainObj.panel("options").YN){
				monthWin.createList(swFlag,bwRadio,month,JQFlag);
			}
		},
		createList : function(swFlag,bwRadio,month,JQFlag){
			var mouthGrid = $.f("div","mouthDiv").f("table","monthGrid");
			mouthGrid.datagridSinosoft({
				body:{
					fit : true,
					url: '<%=basePath%>/petition/petition-basic-info!reportDrilling.action',
					queryParams:{
						swFlag :　swFlag,
						bwRadio : bwRadio,
						month : month,
						flag : JQFlag
			 	    },
					border : false,
					singleSelect : true,
					checkbox : false,
					fitColumns:false,
					pageSize : 10,
					frozenColumns: [[{ title: '所属区域', 
			              field: 'regionName', 
			              width: 80, 
			              align : 'center',
			              sortable: true
		              },{ title: '总量', 
			              field: 'zCount', 
			              width: 50, 
			              align : 'center',
			              sortable: true
		            }]],
		            columns: [[{
		           		"title":"违纪行为",
		           		"align": "center",
		           		"colspan":51
		           	},{
		           		"title":"违法行为",
		           		"align": "center",
		           		"colspan":7
		           	}],
		           	[{"title":"违反政治纪律行为","align": "center","colspan":9}, 
		           	{"title":"违反组织纪律行为","align": "center", "colspan":10}, 
		           	{"title":"违反廉洁纪律行为","align": "center","colspan":16}, 
		           	{"title":"违反群众纪律行为","align": "center","colspan":5}, 
		           	{"title":"违反工作纪律行为","align": "center","colspan":7}, 
		           	{"title":"违反生活纪律行为","align": "center","colspan":4},
		           	{"title":"","align": "center","colspan":7}
		           	],
		           	[
		           	{"field":"num0121","align":"center","title":"总量"},
		           	{"field":"num012101","title":"公开发表<br>危害党的<br>言论","align": "center", "width": 60},
		           	{"field":"num012102","title":"参加反对<br>政府的活<br>动或组织","align": "center", "width": 60},
		           	{"field":"num012103","title":"在党内搞<br>团团伙伙","align": "center", "width": 60},
		           	{"field":"num012104","title":"妨碍党和<br>国家方针<br>政策实施","align": "center", "width": 60},
		           	{"field":"num012105","title":"对抗组织<br>审查","align": "center", "width": 60},
		           	{"field":"num012106","title":"组织参加<br>迷信活动","align": "center", "width": 60},
		           	{"field":"num012107","title":"叛逃及涉<br>外活动中<br>损害党和<br>国家利益","align": "center", "width": 60},
		           	{"field":"num012108","title":"无原则一<br>团和气和<br>违反政治<br>规矩","align": "center", "width": 60},

		           	{"field":"num0122", "title":"总量"},
		           	{"field":"num012201","title":"违反民主<br>集中制原<br>则","align": "center", "width": 60},
		           	{"field":"num012202","title":"不按要求<br>请示报告<br>有关事项","align": "center", "width": 60},
		           	{"field":"num012203","title":"违规组织<br>参加老乡<br>会校友会<br>战友会","align": "center", "width": 60},
		           	{"field":"num012204","title":"侵犯党员<br>权利","align": "center", "width": 60},
		           	{"field":"num012205","title":"在投票和<br>选举中搞<br>非组织活<br>动","align": "center", "width": 60},
		           	{"field":"num012206","title":"违反干部<br>选拔任用<br>规定","align": "center", "width": 60},
		           	{"field":"num012207","title":"在人事劳<br>动工作中<br>违规谋利","align": "center", "width": 60},
		           	{"field":"num012208","title":"违规发展<br>党员","align": "center", "width": 60},
		           	{"field":"num012209","title":"违规办理<br>出国证件<br>和在境外<br>脱离组织","align": "center", "width": 60},


		           	{"field":"num0123", "title":"总量"},
		           	{"field":"num012301","title":"权权交易<br>和纵容特<br>定关系人<br>以权谋私","align": "center", "width": 60},
		           	{"field":"num012302","title":"违规接受<br>礼品礼金<br>宴请服务","align": "center", "width": 60},
		           	{"field":"num012303","title":"违规操办<br>婚丧喜庆<br>事宜","align": "center", "width": 60},
		           	{"field":"num012304","title":"违规从事<br>营利活动","align": "center", "width": 60},
		           	{"field":"num012305","title":"违反工作<br>生活待遇<br>规定","align": "center", "width": 60},
		           	{"field":"num012306","title":"违规占有<br>使用公私<br>财物","align": "center", "width": 60},
		           	{"field":"num012307","title":"违规参与<br>公款宴请<br>消费","align": "center", "width": 60},
		           	{"field":"num012308","title":"违规自定<br>薪酬和发<br>放津贴补<br>贴奖金","align": "center", "width": 60},
		           	{"field":"num012309","title":"公款旅游","align": "center", "width": 60},
		           	{"field":"num012310","title":"违反公务<br>接待管理<br>规定","align": "center", "width": 60},
		           	{"field":"num012311","title":"违反公务<br>用车管理<br>规定","align": "center", "width": 60},
		           	{"field":"num012312","title":"违反会议<br>活动管理<br>规定","align": "center", "width": 60},
		           	{"field":"num012313","title":"违反办公<br>用房管理<br>规定","align": "center", "width": 60},
		           	{"field":"num012314","title":"权色钱色<br>交易","align": "center", "width": 60},
		           	{"field":"num012315","title":"其他","align": "center", "width": 60},

		           	{"field":"num0124","align": "center", "title":"总量"},
		           	{"field":"num012401","title":"侵害群众<br>利益","align": "center", "width": 60},
		           	{"field":"num012402","title":"漠视群众<br>利益" ,"align": "center", "width": 60},
		           	{"field":"num012403","title":"侵犯群众<br>知情权","align": "center", "width": 60},
		           	{"field":"num012404","title":"其他违反<br>群众纪律<br>行为","align": "center", "width": 60},

		           	{"field":"num0125", "title":"总量"},
		           	{"field":"num012501","title":"侵害群众<br>利益","align": "center", "width": 60},
		           	{"field":"num012502","title":"漠视群众<br>利益","align": "center", "width": 60},
		           	{"field":"num012503","title":"侵犯群众<br>知情权","align": "center", "width": 60},
		           	{"field":"num012504","title":"其他违反<br>群众纪律<br>行为" ,"align": "center", "width": 60},
		           	{"field":"num012505","title":"侵犯群众<br>知情权","align": "center", "width": 60},
		           	{"field":"num012506","title":"其他违反<br>群众纪律<br>行为","align": "center", "width": 60},

		           	{"field":"num0126","align": "center", "title":"总量"},
		           	{"field":"num012601","title":"生活奢靡" ,"align": "center", "width": 60},
		           	{"field":"num012602","title":"不正当性<br>关系","align": "center", "width": 60},
		           	{"field":"num012603","title":"其他违反<br>生活纪律<br>行为","align": "center", "width": 60},

		           	{"field":"num0130","align": "center", "title":"总量"},
		           	{"field":"num0131","title":"贪污贿赂<br>行为" ,"align": "center", "width": 60},
		           	{"field":"num0132","title":"失职渎职<br>行为","align": "center", "width": 60},
		           	{"field":"num0133","title":"破坏社会<br>主义市场<br>经济秩序<br>行为","align": "center", "width": 60},
		           	{"field":"num0134","title":"侵犯人身<br>权利民主<br>权利行为","align": "center", "width": 60},
		           	{"field":"num0135","title":"妨害社会<br>管理秩序<br>行为","align": "center", "width": 60},
		           	{"field":"num0136","title":"其他涉法<br>行为","align": "center", "width": 60}
		           	]], 
		           	loadFilter:function(data){
						data.rows=data.result;
						return data;
					},
					onBeforeLoad:function(param){
						param.start=(param.page-1)*param.rows;
						param.limit=param.rows;
					},
					onClickCell : function(index, field, value){
						if(field=="regionName"){
							return;
						}
						var getRow = $.f("div","mouthDiv").f("table","monthGrid").datagridSinosoft('getRows')[index];
						if(trendCompareWinObj){
							trendCompareWinObj.panel("options").swFlag=swFlag;
							trendCompareWinObj.panel("options").bwRadio=bwRadio;
							trendCompareWinObj.panel("options").month=month;
							trendCompareWinObj.panel("options").field=field;
							trendCompareWinObj.panel("options").flag = JQFlag;
							trendCompareWinObj.panel("options").regionName=getRow.regionName;
	 	    		    }else{
	 	    		    	trendCompareWinObj = $.f("div","trendCompareWin").window({
					   		    width:960, 
					   		    height:600,
					   		    title:'信访件列表',
					   		    modal:true,
					   			maximizable:false,
					   			minimizable:false,
					   			collapsible:false,
					   			closed:true,
					   			swFlag:swFlag,
					   			bwRadio:bwRadio,
					   			field:field,
					   			month:month,
					   			flag:JQFlag,
					   			regionName:getRow.regionName,
					   			onOpen:function(){
					   				trendCompareWin.init();
					   			},
					   			onClose : function(){
					   				$.f("div", "trendCompareFindPanel").f("input","accusedName").textbox('setValue',"");
					   				$.f("div", "trendCompareFindPanel").f("input","accuserName").textbox('setValue',"");
					   				trendCompareWinObj.panel("options").swFlag="";
									trendCompareWinObj.panel("options").bwRadio="";
									trendCompareWinObj.panel("options").month="";
									trendCompareWinObj.panel("options").field="";
									trendCompareWinObj.panel("options").regionName="";
									trendCompareWinObj.panel("options").flag = "";
					   			}
					   		});
	 	    		    }
						trendCompareWinObj.window("open");
					},
					onLoadSuccess: function(data){
						var params={
				   			operationFuncCode : "reportDrilling",
				   			operationFuncName : "钻取查询信件问题类别分布情况",
				   			operationBtnName : dateFlag,
				   			operationDesc : "钻取查询信件问题类别分布情况",
				   			operationTypeCode : OperationType.QUERY,
				   			operationTypeName : OperationType.QUERY_NAME,
				   			enableDataLog :true
				   		}
				   		saveOperaLog(params);
						$(this).datagrid("doCellTip", { "max-width": "400px", "delay": 600 });
					}
				}
			});
		}
	}
	</script>
  </body>
</html>