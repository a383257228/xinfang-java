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
    <link
  </head>
  <body>
    <div idFlag="categoryMainClass" style = "margin-top :20px;display:none" class="textmain">
     <span style="font-size:16px;color:white">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分类名称 &nbsp;&nbsp;</span>
         <input  id="newWordClass" style="background-size:100%; font-size:20px;width:60%;height:30px;border: 1px solid #07687d;
  background-color:#054b86"/></br>

         </br>
       
        
         <span style="font-size:16px;color:white">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择父级 &nbsp;&nbsp;</span>
         <select  id="industryClass" style="font-size:15px;width:60%;height:30px;background-color: #E3F2F7;">
         </select>
          <input id = "selectTypeClass" type = "hidden"/>
          </br>
             </br>
        <span id = "appDict">
              
         </span>
		<!--  <input type = "radio" name = "cy" value = "012" alt = "00fd0131fb3f2c700018" ></input><span style="font-size:16px;color:white">&nbsp;违法 &nbsp;&nbsp;</span>-->
        <a  onClick="newWordClassAddClass()" class="button deepblue" style="background:url(<%=basePath%>/images/01_07.png) no-repeat;background-size:94%; width:16%;height:30px;line-height:34px;font-size:24px;text-align:center; margin-top :70px;margin-left:250px" ></a>
	   </div>
</body>
<script>
	var categoryMainObjClass;//textbox-value
	$.SUI("categoryMainWinClass");
	categoryMainWinClass = {
			init : function(){
				$("#appDict").empty();
				$("#appDict").append('<span style="font-size:16px;color:white">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分类类型 &nbsp;&nbsp;</span>');
				$("#newWordClass").val("");
				$("#selectTypeClass").val("");
				$("#industryClass").combotree({
					//url:"<%=basePath%>/DataPredict/Opinion/json.json",
				    url :"/jubao/dataPredict/data-predict-opinion-info!getClassTreeBean.action",
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
			            $("#selectTypeClass").val(node.oid);
			            
			         },
			         onLoadSuccess: function () {
			           //加载完毕  添加日志
			        	 var params={
			         			operationFuncCode : "getClassTreeBean",
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
				$.ajax({
					url:'/jubao/dataPredict/data-predict-opinion-info!getDictType.action', 
					type : "post",
					data:{
						swFlag : swFlag,
						dateFlag : dateFlag,
						startDateValue:startDate,
						endDateValue:endDate
					},
					success:function(data){
						//日志
						 var params={
			         			operationFuncCode : "getDictType",
			         			operationFuncName : "查询民意分析问题类别类型",
			         			operationBtnName : dateFlag,
			         			operationDesc : "民意分析词典类别表",
			         			operationTypeCode : OperationType.QUERY,
			         			operationTypeName : OperationType.QUERY_NAME,
			         			enableDataLog :true
			         		};
			      	    	saveOperaLog(params);
						data = eval('('+data+')');
						for(var i = 0; i < data.length; i++) {
							$("#appDict").append('<input style="width:15px;" type = "radio" name = "cy" id = "'+data[i].CODE+'" value = "'+data[i].CODE+'" alt = "'+data[i].OID	+'"></input><span style="font-size:16px;color:white">&nbsp;'+data[i].DICTNAME+'&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
						}
						$('#'+data[0].CODE).attr("checked","checked");
					},
					error:function(){  
						alert("数据加载失败！");  
					}  
				}); 
			}
	};
	function newWordClassAddClass() {
		 if($("#newWordClass").val() == null || $("#newWordClass").val() == '') {
			 dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   请输入分类名称！', 3000);
		 } else  if($("#selectTypeClass").val() == null || $("#selectTypeClass").val() == '') {
			 dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>   请选择分类父级！', 3000); 
		 } else {
			 var oid = uuids(20,62);
			 var parentId = "";
			 if($("#selectTypeClass").val() == "00fd0131fb3f2c700016") {
				 parentId = $('input[name="cy"]:checked ').attr("alt");
			 }
			 
			 $.ajax({
					url:'/jubao/dataPredict/data-predict-opinion-info!addClass.action', 
					type : "post",
					data:{
						swFlag : swFlag,
						dateFlag : dateFlag,
						startDateValue:startDate,
						endDateValue:endDate,
						parent_sec_Id : $("#selectTypeClass").val(),
						parentId :parentId,
						className : $("#newWordClass").val(),
						oid : oid,
						codeType:"WTXZ",
						classType :$('input[name="cy"]:checked ').val()
						
					},
					success:function(data){
						//新增日志
						var params={
								operationFuncCode : "addClass",
								operationFuncName : "添加字典（问题类别）",
								operationBtnName : "保存",
								operationResultCode : "1",
								operationResultName : "成功",
								operationDesc : "添加字典（问题类别）",
								operationTypeCode : OperationType.ADD,
								operationTypeName : OperationType.ADD_NAME,
								operationDataDesc : "添加字典（问题类别）",
								operationDataValue : "父节点ID："+$("#selectTypeClass").val()+"第二级父节点ID:"+parentId+"问题类型的类型:WTXZ"+"问题类别名称："+$('input[name="cy"]:checked ').val()+"oid:"+oid,		
								enableDataLog :true
							};
							saveOpeationRecord(params);
						
						$.f("div","categoryMainClass").window('close', true);
						dialog.showMiniDialog("self-style", '<i class="icon-success"></i>   添加成功！', 3000);
						 
						 
					},
					error:function(){  
						alert("数据加载失败！");  
					}  
				}); 
		 }
		 
	}
	
	//生成uuid
	function uuids(len, radix) {
	    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	    var uuid = [], i;
	    radix = radix || chars.length;
	 
	    if (len) {
	      // Compact form
	      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	    } else {
	      // rfc4122, version 4 form
	      var r;
	 
	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';
	 
	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random()*16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }
	 
	    return uuid.join('');
	}

</script>
</html>

