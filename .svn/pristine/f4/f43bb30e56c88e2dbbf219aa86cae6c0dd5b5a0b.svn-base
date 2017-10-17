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
	<div idFlag="categoryMainEdit" style="margin-top :20px;display:none" class="textmain"> 
		 <span style="font-size:16px;color:white;margin-left:-30px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编辑新词 &nbsp;&nbsp;</span>
         <input  id="newWordEdit" style="background-size:100%; width:100%;height:30px; font-size:20px;width:60%;height:30px;border: 1px solid #07687d; background-color: #054b86"/></br>
         </br>
         
         <a  onClick="newWordClassEdit()" class="button deepblue" style="background:url(<%=basePath%>/images/01_07.png) no-repeat;background-size:100%; width:80px;height:40px;line-height:34px;font-size:16px;text-align:center; margin-top :50px;margin-left:210px" ></a>
	</div>
</body>
<script>
	var categoryMainObjEdit;
	$.SUI("categoryMainEdit");
	categoryMainWinEdit = {
			init : function(){
				$("#newWordEdit").val($("#newWordHidd").val());
			}};
	
	function newWordClassEdit() {
    	var oid = $("#newWordIdHidd").val();
    	var newVal = $("#newWordEdit").val();
		if(newVal == null || newVal == '') {
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   新词不能为空！', 3000);
		} else if($("#newWordHidd").val() == newVal) {
			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   未编辑新词！', 3000);
		} else {
			$.ajax({
				url:'/jubao/dataPredict/data-predict-opinion-info!saveNewWord.action',  
				type : "post",
				data:{
					swFlag : swFlag, 
		 	    	dateFlag : dateFlag,
		 	    	startDateValue　: startDate,
		 	    	endDateValue :　endDate,
					oid : oid,
					newVal : newVal
				},
				success:function(d){
					var params={
							operationFuncCode : "saveNewWord",
							operationFuncName : "编辑新词",
							operationBtnName : "保存",
							operationResultCode : "1",
							operationResultName : "成功",
							operationDesc : "编辑新词",
							operationTypeCode : OperationType.MODIFY,
							operationTypeName : OperationType.MODIFY_NAME,
							operationDataDesc : "编辑新词",
							operationDataValue : "新词："+newVal+"oid:"+oid,		
							enableDataLog :true
							}
						saveOpeationRecord(params);
					
					 $.f("div","categoryMainEdit").window('close', true);
					 $("#xfff").datagrid('reload'); 
					 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>   修改成功！', 3000);
					 
				},
				error:function(){  
					dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   数据加载失败！', 3000);
				}  
			}); 
		}
    }
			
</script>
</html>

