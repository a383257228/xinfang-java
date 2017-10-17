<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>登录</title>
    <script src="<%=basePath%>/login/js/jquery.md5.js"></script>
  </head>
  <style>
  </style>
  <body>
  	<div style="width:100px;height:100px">
  		<h4>用户名:</h4><input class="sinosoft-textbox" idFlag="loginName" value="00020"/>
  		<h4>密码:</h4><input class="sinosoft-textbox" idFlag="loginPwd" value="1"/>
  	</div>
  	<script type="text/javascript">
  		$(function(){
  		    //定位光标到登录名
  			$("#loginName").focus();
  			var login = function(){
 					$.ajax({
					url:"/jubao/logon/logon!logonStandard.action",
					type:"POST",
					data:{
						userEname:$.f("input","loginName").val(),
						userPwd:$.md5($.f("input","loginPwd").val()),
						regionCode:310000000000
					},
					success:function(data){
						var result =  data.responseText;
						if(result=='admin'){
							window.location.href="<%=basePath%>/managertIndex.jsp";
						}else{
							window.location.href="<%=basePath%>/index.jsp";
						}
					}
				});
			};
 			$(document).keyup(function(e){
  				 var curKey = e.which;
			     if(curKey==13){
			    	 login();
			     }
			});
  		});
  	</script>
 </body>
</html>