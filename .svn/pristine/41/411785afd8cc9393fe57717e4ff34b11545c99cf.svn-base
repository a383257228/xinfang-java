<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>

<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<title>巡视系统-巡视办版</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <script>
	  	var sysType="0";//系统类型  0：网络版  1：现场版
	</script>
  </head>
<body  class="sinosoft-layout" id="bodybody" style="background-color:#000" >  
	    <div par="region:'center',border:false" style="background-color:#000;padding: 50px" id="center" >
	    		<button class="green">
 	 				<i class="icon-edit"></i> green
 	 			</button>
 	 			
 	 			<button class="deepblue">
 	 				<i class="icon-email"></i> deepblue
 	 			</button>
 	 			<button class="lightblue">
 	 				<i class="icon-del"></i> lightblue
 	 			</button>
 	 			<button class="red">
 	 				<i class="icon-del"></i> lightblue
 	 			</button>
 	 			<button class="paray">
 	 				<i class="icon-menu2 "></i> lightgreen
 	 			</button>
 	 			<button class="update">
 	 				<i class="icon-search"></i> lightgreen2
 	 			</button>
 	 			<button class="write">
 	 				重置
 	 			</button>
 	 			<button class="orange">
 	 				<i class="icon-export"></i> orange
 	 			</button>
 	 			<div style="height: 5px"></div>
 	 			<button class="green">
 	 				<i class="icon-edit"></i> 新增
 	 			</button>
 	 			
 	 			<button class="deepblue">
 	 				<i class="icon-email"></i> 邮件发送
 	 			</button>
 	 			<button class="lightblue">
 	 				<i class="icon-del"></i> 删除
 	 			</button>
 	 			<button class="red">
 	 				<i class="icon-del"></i>  删除
 	 			</button>
 	 			<button class="paray">
 	 				<i class="icon-menu2 "></i>菜单
 	 			</button>
 	 			<button class="update">
 	 				<i class="icon-search"></i> 修改
 	 			</button>
 	 			<button class="write">
 	 				 重置
 	 			</button>
 	 			<button class="orange">
 	 				<i class="icon-export"></i> 导出
 	 			</button>
 	 			
 	 			<div style="height: 5px"></div>
 	 			
 	 			<a class="button green">
 	 				<i class="icon-edit"></i> 新增
 	 			</a>
 	 			
 	 			<a class="button deepblue">
 	 				<i class="icon-email"></i> 邮件发送
 	 			</a>
 	 			<a class="button lightblue">
 	 				<i class="icon-del"></i> 删除
 	 			</a>
 	 			<a class="button red">
 	 				<i class="icon-del"></i>  删除
 	 			</a>
 	 			<a class="button paray">
 	 				<i class="icon-menu2 "></i>菜单
 	 			</a>
 	 			<a class="button update">
 	 				<i class="icon-search"></i> 修改
 	 			</a>
 	 			<a class="button write">
 	 				重置
 	 			</a>
 	 			<a class="button reset">
 	 				重置x
 	 			</a>
 	 			<a class="button orange">
 	 				<i class="icon-export"></i> 导出
 	 			</a>
 				<a class="button gray">
 					失效
 				</a>
 	 			<div style="height: 5px"></div>
 	 			<a class="button write" id="success">
 	 				正确提示框
 	 			</a>
 	 			<a class="button write" id="error">
 	 				错误提示框
 	 			</a>
 	 			<a class="button write" id="chang">
 	 				好长的字啊
 	 			</a>
 	 			<a class="button write" id="A">
 	 				对话框A
 	 			</a>
 	 			<a class="button write" id="B">
 	 				对话框B
 	 			</a>
 	 			
 	 			<div style="height: 5px"></div>
 	 			<div class="button write"  style="width: 64px">
 	 				上传
 	 				<input type="file" class="fileinput"></input>
 	 			</div>
 	 			<div class="button orange"  style="width: 64px">
 	 				<i class="icon-export"></i>上传
 	 				<input type="file" class="fileinput"></input>
 	 			</div>
 	 			<div style="height: 5px"></div>
 	 			<input idFlag="inspectionTeamCode" style="width:280px" name="inspectionTeamCode"  class="sinosoft-systemCodeCombobox" 
									par='queryParams:{
											codeType:"XFFS"
										}
										'/>
 	 			
	    </div>  
	    <script type="text/javascript">
		    $("#success").click(function () {
	            dialog.showMiniDialog("self-style", '<i class="icon-success"></i> 恭喜您，提交成功！', 3000);
	        });
		    $("#error").click(function () {
	            dialog.showMiniDialog("self-style", '<i class="icon-faild"></i> 提交失败！巡视组已经存在', 3000);
	        });
		    $("#chang").click(function () {
	            dialog.showMiniDialog("self-style", '<i class="icon-faild"></i> 提交失败！好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊好长的字啊巡视组已经存在', 3000);
	        });
		    

            $("#A").click(function () {
                dialog.showNormalDialog("提交成功", '<i class="icon-success"></i> 恭喜您，提交成功！', {
                    ok: {
                        name: "确认",
                        click: function () {
                            //alert("clicked 确认");
                            dialog.removeDialog();
                            //如果不加这句，对话框不会关闭
                        }
                    },
                    cancel: {
                        name: "取消",
                        click: function () {
                            //alert("clicked 取消");
                            dialog.removeDialog();
                        }
                    }
                });
            });

            $("#B").click(function () {
                dialog.showNormalDialog("提交成功", '<i class="icon-faild"></i> 提交失败，巡视组已存在！', {
                    ok: {
                        name: "确认",
                        click: function () {
                            //alert("clicked 确认");
                            dialog.removeDialog();
                            //如果不加这句，对话框不会关闭
                        }
                    },
                    cancel: {
                        name: "取消",
                        click: function () {
                            //alert("clicked 取消");
                            dialog.removeDialog();
                        }
                    }
                });
            });
	    </script>
 </body>
</html>