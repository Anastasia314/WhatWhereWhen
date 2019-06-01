<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo.png">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/error.jpg") no-repeat;
        background-size: 100%;
    }
</style>
<title><fmt:message key="page.title.error"/></title>
<body>
<%@ include file="/WEB-INF/jspf/novigation.jspf" %>
<div class="own-container">
    <div class="opacity-div">
    <br>
    <b><fmt:message key="error.message"/></b>
    <p>
        <br>
        <b>Request is failed</b>
        <br>
        <b>Servlet name: </b>${pageContext.errorData.servletName}
        <br>
        <b>Status code:</b> ${pageContext.errorData.statusCode}
        <br>
        <b>Exception:</b> ${pageContext.exception}
        <br>
    </p>
    <a href="${pageContext.request.contextPath}/controller?command=initialize_start_page" class="button">
        <fmt:message key="error.button.back"/></a>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
