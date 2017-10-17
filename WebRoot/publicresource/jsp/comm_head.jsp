<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String basePath = request.getContextPath();
	if(basePath != null && "/".equals(basePath)){
		basePath = "";
	}
	String pathloginvarBasePath = request.getContextPath();
	String loginvarBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathloginvarBasePath+"/";
	String contextPath = application.getContextPath();
	String scanUrl = loginvarBasePath + (String)session.getAttribute("scanUrl");
%>


 <script type="text/javascript">
     var loginvarBasePath = '<%= loginvarBasePath%>';
     var basePath = loginvarBasePath;
     var httpIp = "..";
    
</script> 



<script type="text/javascript">
         <%-- 系统时间 --%> 
		var today=new Date(); 
		var day;   
		var date;   
		if(today.getDay()==0) day="星期日";   
		if(today.getDay()==1) day="星期一";  
		if(today.getDay()==2) day="星期二"; 
		if(today.getDay()==3) day="星期三";   
		if(today.getDay()==4) day="星期四";  
		if(today.getDay()==5) day="星期五";  
		if(today.getDay()==6) day="星期六"; 
		date=(today.getFullYear())+"年"+(today.getMonth()   +   1   )+"月"+today.getDate()+"日   "+day+"";
		var sessionlostName="<%=basePath%>";
</script>
<style type="text/css">
body{
  		margin: 0 0 0 0;
  	}
</style>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/sinosoftUI.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/whole.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/index.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/fullTextSearch/css/fullTextSearch.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/fullTextSearch/css/jquery.bigautocomplete.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/window.css">
<script type="text/javascript">
	  <%-- 屏蔽浏览器前进后退按钮功能 --%> 
	window.history.forward(); 
</script>
<script src="<%=basePath%>/js/logOperationUtil.js"></script>
<script src="<%=basePath%>/publicresource/js/g2.js"></script>
<script src="<%=basePath%>/publicresource/js/echarts-all.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.min.js"></script>
<script src="<%=basePath%>/publicresource/js/d3.v3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.sinosoftUI.master.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/sinosoftUI.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/sinosoftUIvalidate.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/mydialog.js"></script>

<script type="text/javascript" src="<%=basePath%>/publicresource/js/suspensionplug.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/jquery.jdirk.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/jeasyui.extensions.menu.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/jeasyui.extensions.datagrid.columnToggle.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicresource/css/jeasyui.extensions.datagrid.css">
<link rel="shortcut icon" href="<%=basePath%>/publicresource/images/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="<%=basePath%>/fullTextSearch/js/jquery.bigautocomplete.js"></script>
<script type="text/javascript" src="<%=basePath%>/publicresource/js/suspensionplug.js"></script>

<script src="<%=basePath%>/publicresource/js/d3.layout.cloud.js"></script>
<link rel="stylesheet" href="<%=basePath%>/publicresource/css/jquery.ui.all.css">
<script src="<%=basePath%>/publicresource/js/jqueryUI/jquery.ui.core.js"></script>
<script src="<%=basePath%>/publicresource/js/jqueryUI/jquery.ui.widget.js"></script>
<script src="<%=basePath%>/publicresource/js/jqueryUI/jquery.ui.position.js"></script>
<script src="<%=basePath%>/publicresource/js/jqueryUI/jquery.ui.menu.js"></script>
<script src="<%=basePath%>/publicresource/js/jqueryUI/jquery.ui.autocomplete.js"></script>