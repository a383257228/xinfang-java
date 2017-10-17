<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/publicresource/jsp/comm_head.jsp"%>
<!-- 修改密码窗体 -->
<%@ include file="/PersonalCenter/modifyPwdWin.jsp"%>
<!-- 日志查询的查看数据日志窗口 -->
<%@ include file="/operation/query/lookDataWin.jsp"%>
<!-- 日志列表查看本次、历次登录信息 -->
<%@ include file="/operation/list/bcLoginWin.jsp"%>
<%@ include file="/operation/list/lcLoginWin.jsp"%>
<%@ include file="/operation/list/lcTrackWin.jsp"%>
<!-- 历史权限查看 -->
<%@ include file="/operation/record/lookHistoryRecordWin.jsp"%>
<!--权限查看点击人员姓名弹框-->
<%@ include file="/operation/permissions/permissionsWin.jsp"%>
<!-- 修改角色的窗口 -->
<%@ include file="/operation/permissions/updatePermissionsWin.jsp"%>
<%@ include file="/organization/userSetting/userInfo.jsp"%>

<%
	session.setAttribute("curRegionCode","310000000000");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<script type="text/javascript" src="<%=basePath%>/operation/query/operationQuery.js" ></script>
	<script type="text/javascript" src="<%=basePath%>/operation/list/operationList.js" ></script>
	<script type="text/javascript" src="<%=basePath%>/operation/record/record.js" ></script>
	<script type="text/javascript" src="<%=basePath%>/js/managerIndex.js"></script>
	<script type="text/javascript" src="<%=basePath%>/operation/lookHisAuthority/hisAuthorty.js"></script>
	<script type="text/javascript" src="<%=basePath%>/operation/permissions/permissions.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/operation/css/style.css">
  </head>
  <style>
		#center{
			border:0px
		}
		.tabs-title{
			font-size:20px !important;
		}
		.tabs{
			padding-top:5px;
			border:0px;
			height:29px !important;
		}
		.tabs-inner{
			height:28px !important;
			line-height:28px !important;
			width:140px !important;
		}
		#west .tree-node{
			height:38px;
		}
		.window .window-header{
			background:#5694eb;
			border:0;
		}
  </style>
  <body class="sinosoft-layout">
	    <div par="region:'north',border:false" style="height:80px;">
		 		<div style="text-align: center;background-color:#427edb;height:100%;line-height:80px">
					<label style="color:white;font-size:32px;line-height:80px;margin-left:100px">纪检监察信访信息管理系统</label>
			   		<label style="color:white;font-size:30px;line-height:80px">(上海版)</label>
			   		<label style="color:white;font-size:30px;line-height:80px">&nbsp;&nbsp;&nbsp;&nbsp;大数据分析</label>
				</div>
	    </div> 
	    <div par="region:'west'" id="west" style="width:200px;	background: #7bb1ff;">
	    	<p id="p">
	               <a href="javascript:;" class="menu-toggle" title="折叠/展开" data-val="open"></a>
	        </p>
			<div class="accordion"  par=""></div>
	    </div>  
	    <div par="region:'center',border:false"  id="center" >
	    	<div  name="contenttab" >
			</div>
	    </div>  
  </body>
</html>