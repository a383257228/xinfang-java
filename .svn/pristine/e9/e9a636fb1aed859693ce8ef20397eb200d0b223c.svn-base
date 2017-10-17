<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>登录</title>
    <script src="<%=basePath%>/login/js/jquery.md5.js"></script>
  </head>
  <style>
  	input{
		background-color: transparent !important;
		border: 0px;
		outline:none;
		color:#AED7FD;
	}
	.img{
		background:url(<%=basePath%>/login/image/bg1.png) no-repeat;
    	 margin-left:14%;
    }
    .dl{
	   left:57%;
	}
	@media only screen and (max-width: 1366px) {
	    .img{
			margin-left:0;
		}  
	}
    @media only screen and (max-width: 1024px) {
    	.img{
    		margin-left:0;
		   background:url(<%=basePath%>/login/image/bg.png) no-repeat;
		}
		.dl{
		   left:550px;
		}
		
	}
  </style>
  <body>
  	<div class="sinosoft-layout" par="fit:true" >
  		<div par="region:'north'" style="height:150px; display: table-cell;vertical-align:middle;text-align: center;border:0 ">
  			<img src="<%=basePath%>/login/image/title.png" style="margin: auto;">
  		</div>
  		<div par="region:'center'" style="height:700px;" class="img">
  			<div class="dl" style="height:260px;width:400px;position:absolute;z-index:1;top:90px;background-image: url('<%=basePath%>/login/image/inputbg.png')">
				<div style="font-size: 20px;text-align: center;margin-top: 20px;">
				 	<span style="color: white; font-weight:bolder;">系统用户登录</span>
				</div>
				
				<div style="text-align: center;margin-top: 10px;">
					<div style="padding-top:2px;padding-left:40px ">
						<div style="height:45px;background:url('<%=basePath%>/login/image/logininput.png')   no-repeat;padding-top: 5px;padding-left:47px">
							<input id="loginName" tabindex="1" style="font-size: 20px;font-family: '微软雅黑';margin-left: -80px;margin-top: 10px;" placeholder="登录名" />
						</div>
					</div>
				</div>
				<div style="text-align: center;margin-top: 2px;">
					<div style="padding-top:2px;padding-left:40px ">
						<div style="height:45px;background:url('<%=basePath%>/login/image/logininput.png')  no-repeat 0px -60px ;padding-top: 5px;padding-left: 42px">
							<input type="password" tabindex="2 " placeholder="密码" style="font-size: 20px;margin-left: -80px;margin-top: 10px;font-family: '微软雅黑'"></input>
						</div>
					</div>
				</div>
				<div style="margin-top: 10px;">
					<img src="<%=basePath%>/login/image/login.png" style="margin-left: 80px; class="loginbtn" id="loginbtn" tabindex="3">
					<img src="<%=basePath%>/login/image/reset.png" style="margin-left: 32px" id="resetbtn" tabindex="4">
				</div>
  			</div>
  		</div>
  		<div par="region:'south'" style="height:70px;border:0">
  			<div style="text-align: center;height:60px;line-height: 60px;color: #3399FF;font-size: 12px;">
  				版本所有：上海市纪委市监察局
  			</div>
  		</div>
  	</div>
  	<script type="text/javascript">
  		$(function(){
  		    //定位光标到登录名
  			$("#loginName").focus();
  			var login = function(){
  				var login=$.trim($("#loginName").val()).length;
  				var password=$.trim($("input[type='password']").val()).length;
  				if(login==0&&password==0){
  					errormsg("用户名密码未填写");
  				}else if(login==0){
  					errormsg("用户名未填写");
  				}else if(password==0){
  					errormsg("密码未填写");
  				}else{
  					$.ajax({
  						url:"/jubao/logon/logon!logonStandard.action",
  						type:"POST",
  						data:{
  							userEname:$("#loginName").val(),
  							userPwd:$.md5($("input[type='password']").val()),
  							/* regionCode:curRegionCode */
  							regionCode:310000000000
  						},
  						success:function(data){
  							try{
  								var str = eval("("+data+")");
  								if(typeof str == "string"){
  									errormsg("用户名密码错误");
  								}else if(str){
  									localStorage.sessionpwd=$.md5($("input[type='password']").val());
  									var userName = $("#loginName").val();
  									if(userName!="admin"&&userName!="aqadmin"&&userName!="secadmin"){
  										window.location.href="<%=basePath%>/index.jsp";
  									}else{
  										window.location.href="<%=basePath%>/managertIndex.jsp";
  									}
  								}
  							}catch(e){
  								errormsg("用户名密码错误");
  							}
  						}
  					})
  				}
  			};
 			$(document).keyup(function(e){
  				 var curKey = e.which;
			     if(curKey==13){
			    	 login();
			     }
			});
 			$("#resetbtn").click(function(){
 				$("input[type='password']").val('');
 				$("#loginName").val('');
			});
  			$("#loginbtn").click(login);
  			$(".body").height($(window).height())
			$(window).resize(function(){
				$(".body").height($(window).height())
			})
  		});
  		function errormsg(str){
  			dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>&nbsp;&nbsp;&nbsp;&nbsp;'+str, 3000);
  		}
  	</script>
 </body>
</html>