<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custg" uri="customtag" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <title><fmt:message key="page.title.userdata"/></title>
    <link rel="shortcut icon" href="${contextPath}/images/logo.png">
    <link href="${contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <%@ include file="/WEB-INF/jspf/novigation.jspf" %>
    <div id="main">
        <article class="post">
            <header>
                <div class="title">
                    <h2><fmt:message key="page.title.userdata"/></h2>
                    <p><c:if test="${not empty sessionScope.userData}">
                        ${sessionScope.userData.lastName} ${sessionScope.userData.firstName}
                    </c:if>
                    </p>
                </div>
                <div class="meta">
                    <c:if test="${not empty sessionScope.user.photo}">
                        <img src="${contextPath}/image?command=display" alt="" class="avatar-photo-small"/>
                    </c:if>
                    <c:if test="${empty sessionScope.user.photo}">
                        <img src="${contextPath}/images/logo.png" alt="" class="avatar-photo-small"/>
                    </c:if>
                    <p>${sessionScope.user.login}<br>
                        <fmt:message key="main.header.rating"/>: ${sessionScope.user.rating}</p>
                </div>
            </header>
            <div class="own-container">
                <div class="half-panel">
                    <h3 id="server-message">
                        <c:if test="${not empty param.serverMessage}">
                            <fmt:message key="${param.serverMessage}"/>
                        </c:if>
                    </h3>
                    <form id="register-form" method="post" action="${contextPath}/controller">
                        <label for="lastName"><fmt:message key="userdata.input.lastname"/>*</label>
                        <div class="form-group">
                            <input type="text" name="lastName" class="form-control" id="lastName" required
                                   maxlength="30" minlength="2"
                                   value="${fn:escapeXml(not empty sessionScope.userData?sessionScope.userData.lastName:"")}"/>
                            <div class="input-tip">
                                <c:if test="${not empty param.serverMessage}">
                                    <fmt:message key="userdata.tip.name"/>
                                </c:if>
                            </div>
                        </div>
                        <label for="firstName"><fmt:message key="userdata.input.firstname"/>*</label>
                        <div class="form-group">
                            <input type="text" name="firstName" id="firstName" class="form-control"
                                   minlength="2" maxlength="30" required
                                   value="${fn:escapeXml(not empty sessionScope.userData?sessionScope.userData.firstName:"")}">
                            <div class="input-tip">
                                <c:if test="${not empty param.serverMessage}">
                                    <fmt:message key="userdata.tip.name"/>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                                <jsp:useBean id="birthdate" class="java.util.Date"/>
                                <c:set target="${birthdate}" property="time"
                                       value="${not empty sessionScope.userData?sessionScope.userData.birthdate:0}"/>
                                <label for="birthday"><fmt:message key="userdata.input.birthday"/>*</label>
                                <input type="date" name="birthday" id="birthday" class="form-control"
                                       value="<fmt:formatDate value="${birthdate}" pattern="yyyy-MM-dd"/>" required>
                        </div>
                        <div class="form-group">
                            <label for="email"><fmt:message
                                    key="userdata.input.email"/>*</label>
                            <input type="email" name="email" id="email"
                                   class="form-control" maxlength="45" minlength="7"
                                   value="${fn:escapeXml(not empty sessionScope.userData?sessionScope.userData.email:"")}" required>
                            <div class="input-tip">
                            <c:if test="${not empty param.serverMessage}">
                                <fmt:message key="userdata.tip.email"/>
                            </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="city"><fmt:message
                                    key="userdata.input.city"/></label>
                            <input type="text" name="city" id="city"
                                   class="form-control" maxlength="45" minlength="2"
                                   value="${fn:escapeXml(not empty sessionScope.userData?sessionScope.userData.city:"")}">
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-6 offset-sm-3">
                                    <input type="submit" name="register-submit" id="register-submit"
                                           value=<fmt:message key="userdata.button.change_data"/>/>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="command" value="change_userdata">
                    </form>
                </div>
                <div class="half-panel">
                    <div class="own-container">
                        <div class="avatar-photo">
                            <c:if test="${not empty sessionScope.user.photo}">
                                <img src="${contextPath}/image?command=display" alt=""/>
                            </c:if>
                            <c:if test="${empty sessionScope.user.photo}">
                                <img src="${contextPath}/images/logo.png" alt=""/>
                            </c:if>
                        </div>
                    </div>
                    <div class="left-margin-div">
                        <form action="${contextPath}/image?command=upload" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="uri" value=${pageContext.request.requestURI}>
                            <input type="file" name="image" size="2MB" accept="image/*" required/>
                            <br/>
                            <input type="submit" value=
                                    <fmt:message key="userdata.button.upload_image"/>/>
                        </form>
                    </div>
                </div>
            </div>
        </article>
    </div>
</div>
<div class="left-margin-div">
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/skel.min.js"></script>
<script src="${contextPath}/js/util.js"></script>
<script src="${contextPath}/js/main.js"></script>
</body>
</html>
