<%@page import="org.apache.commons.lang.exception.ExceptionUtils"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
	ex.printStackTrace();
	out.write("{'success':false,'errorMsg':'" + (ex.getMessage() != null  ? ex.getMessage() : "操作失败！")  + "'}");
	out.newLine();
	out.write(ExceptionUtils.getStackTrace(ex));
%>
