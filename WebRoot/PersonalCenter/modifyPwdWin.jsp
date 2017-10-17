<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
  <head>
  </head>
  
  <body>
   	<div idFlag="modifyPasswordWindow" style="display:none;">
    	<div  style="width:100%;margin: auto;height: 100%;">
			<div class="formPanel" style="overflow: hidden;">
   				<form  idFlag="modifyPasswordForm" method="post" >
   					<table class="tablebox">
			   			<tr>
			   				<td style="text-align: right;width:116px"><div class="xs">新密码<span style="color: red;">*</span>:</div></td>
			   				<td>
			   					<input type="password"  class= "sinosoft-textbox" prompt="********"  par="required:true,width:200" validType="ispassword[6]" idFlag="newPassword" name="userPwd" />
			   				</td>
			   			</tr>
			   			<tr>
			   				<td style="text-align: right;"><div class="xs" style="width:130px">确认新密码<span style="color: red;">*</span>:</div></td>
			   				<td >
			   					<input type="password" class="sinosoft-textbox" prompt="********"  par="required:true,width:200" validType="ispassword[6]" idFlag="confirmPassword"/>
			   				</td>
			   			</tr>
			   			<tr align="center">
			   				<td colspan="2" style="text-align: center;">
			   					<a  style="width:50px;" class="button deepblue" onclick="modifyPasswordWin.submit()">保存</a>
							</td>
			   			</tr>
	   				</table>
   				</form>
   			</div>
   		</div>
  	</div> 	
   	<script type="text/javascript">
  	$.SUI("modifyPasswordWin");
  	var modifyPasswordWinObj;
  	modifyPasswordWin={
  		submit:function(){
  			if (!$.f("form","modifyPasswordForm").form("validate")){
				dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>请检查数据格式！', 3000);
			}else{
				var newPassword=$.f("div","modifyPasswordWindow").f("input","newPassword").textbox("getValue");
				var confirmPassword=$.f("div","modifyPasswordWindow").f("input","confirmPassword").textbox("getValue");
				if(newPassword==confirmPassword){
					$.f("form","modifyPasswordForm").form('submit', { 
					 	url:"<%=basePath%>/organperson/person-info!modifyPassword.action",
					    onSubmit: function(param){
					    	
				   		} ,   
					    success:function(data){
					    	 dialog.showMiniDialog("self-style", '<i class="icon-success"></i>数据保存成功！', 1000);
					    	 modifyPasswordWinObj.window("close");
					    }   
					}); 
				}else{
					dialog.showMiniDialog("self-style", '<i class="icon-faild"></i>两次输入密码不一致！', 3000);
				}
				
			}
  		},
  		reset:function(){
  			$.f("form","modifyPasswordForm").form("reset");
  		},
  		close:function(){
  			//在关闭的时候进行重置动作
  			$.f("form","modifyPasswordForm").form("reset");		
  			$.f("div","modifyPasswordWindow").window("close");  
  		},	
		openModifyPasswordWindow : function(){
			modifyPasswordWinObj= $.findName("div","modifyPasswordWindow","idFlag").window({  
			    title:"修改密码", 
			    modal:true,
				collapsible:false,
				closed:true,
				width:420,    
				resizable:false,
				draggable:false,
				maximizable:false,
				minimizable:false,
				onBeforeClose:function(){
					$.f("form","modifyPasswordForm").form("reset");
				}
			});
			modifyPasswordWinObj.window("open");
		}
			
	}
  	</script>
  </body>
</html>
