<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>民意分析</title> 
  </head>
  <body >
    <div idFlag="categoryMainDict" style = "margin-top :20px;display:none" class="textmain">
     <span style="font-size:16px;color:white">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编辑新词 &nbsp;&nbsp;</span>
         <input  id="newWord" style="background-size:100%; width:100%;height:30px; font-size:20px;width:60%;height:30px;border: 1px solid #07687d;
  background-color:#054b86"/>
         </br></br></br>
        
         <span style="font-size:16px;color:white">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新词分类 &nbsp;&nbsp;</span>
         <select  id="industry" style="font-size:15px;width:60%;height:30px;background-color: #E3F2F7;">
         </select>
         <input id = "selectType" type = "hidden"/>
         <input id = "selectCodeType" type = "hidden"/>
         </br>
        <a  onClick="newWordClassAdd()" class="button deepblue" style="background:url(<%=basePath%>/images/01_07.png) no-repeat;background-size:94%; width:16%;height:30px;line-height:34px;font-size:24px;text-align:center; margin-top :70px;margin-left:250px" ></a>
	   </div>
</body>
<script>
	var categoryMainObjDict;//textbox-value
	$.SUI("categoryMainWinDict");
	categoryMainWinDict = {
			init : function(){
				$("#selectType").val("");
				$("#industry").combotree({
					//url:"<%=basePath%>/DataPredict/Opinion/json.json",
				    url :"/jubao/dataPredict/data-predict-opinion-info!getTreeNodeBean.action",
					required:false,
					editable:false,
					missingMessage:"问题类别必选项",
					prompt:"请选择",
					checkbox:true,
					onlyLeafCheck:true,
					mutiple:false,
					onSelect: function (node) {//当为单选时，只允许选择叶子节点的设置
 			            //返回树对象  
			            var tree = $(this).tree;
			            //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
			            var isLeaf = tree('isLeaf', node.target);
			            if (!isLeaf) {
			                //清除选中  
			                $('#industry').combotree('clear');
			                $("#selectType").val("");
			                $("#selectCodeType").val("");
			            } else {
			            	$("#selectType").val(node.id);
			            	$("#selectCodeType").val(node.codeType);
			            }
			         },
			         onLoadSuccess: function () {
				           //加载完毕  添加日志
				        	 var params={
				         			operationFuncCode : "getTreeNodeBean",
				         			operationFuncName : "查询民意分析中所有问题类别树",
				         			operationBtnName : dateFlag,
				         			operationDesc : "民意分析词典类别表",
				         			operationTypeCode : OperationType.QUERY,
				         			operationTypeName : OperationType.QUERY_NAME,
				         			enableDataLog :true
				         		};
				      	    	saveOperaLog(params);
				         }
				});
			    
				$("#newWord").val(window.parent.$("#newWordHidd").val());
			    //	alert(window.parent.$("#newWordHidd").val()); 
			}
	};
	
	  function newWordClassAdd() {
    	if($("#selectType").val() == null || $("#selectType").val() == '') {
    		dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   请选择分类！', 3000);
    	} else {
    		$.ajax({
				url:'/jubao/dataPredict/data-predict-opinion-info!updateNewWordClass.action', 
				type : "post",
				data:{
					swFlag : swFlag,
					dateFlag : dateFlag,
					startDateValue:startDate,
					endDateValue:endDate,
					oid:window.parent.$("#newWordIdHidd").val(),
					newWord : $("#newWord").val(),
					wordClass : $("#selectType").val(),
					codeType : $("#selectCodeType").val()
					
				},
				success:function(data){
					var params={
							operationFuncCode : "updateNewWordClass",
							operationFuncName : "新词加入词典",
							operationBtnName : "保存",
							operationResultCode : "1",
							operationResultName : "成功",
							operationDesc : "新词加入词典",
							operationTypeCode : OperationType.MODIFY,
							operationTypeName : OperationType.MODIFY_NAME,
							operationDataDesc : "新词加入词典",
							operationDataValue : "新词："+$("#newWord").val()+"新词所属问题类别类型："+$("#selectType").val()+"问题类别类型:"+$("#selectCodeType").val()+"oid:"+window.parent.$("#newWordIdHidd").val(),		
							enableDataLog :true
							}
						saveOpeationRecord(params);
					
					$.f("div","categoryMain").window('close', true);
					 $("#xfff").datagrid('reload'); 
					 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>   添加成功！', 3000);
				},
				error:function(){  
					alert("数据加载失败！");  
				}  
			}); 
    	}
    	
    }
</script>
</html>

