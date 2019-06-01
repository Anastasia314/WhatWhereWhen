<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="page.title.enter"/></title>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo.png">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jspf/novigation.jspf" %>
<div class="own-container">
    <div class=" opacity-div">
        <hr class="my-hr">
        <div class="row">

            <div class="panel panel-login">
                <h3 id="server-message">
                    <c:if test="${not empty param.serverMessage}">
                        <fmt:message key="${param.serverMessage}"/>
                    </c:if>
                </h3>
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-sm" id="login-form-link-div">
                            <a href="#" class="active" id="login-form-link"><fmt:message key="login.link.signin"/></a>
                        </div>
                        <div class="col-sm dis-active" id="register-form-link-div">
                            <a href="#" style="padding-top: 10px" id="register-form-link"><fmt:message
                                    key="login.link.signup"/></a>
                        </div>
                    </div>
                    <hr>
                </div>

                <div class="panel-body">
                    <div class="row">
                        <form id="login-form" method="post" action="${pageContext.request.contextPath}/controller"
                              style="display: block;">
                            <div class="form-group">
                                <input type="text" name="login" class="form-control" id="login" minlength="5"
                                       maxlength="15" required value="${fn:escapeXml('')}"
                                       placeholder=<fmt:message key="login.input.login"/>>
                            </div>

                            <div class="form-group">
                                <input type="password" name="password" class="form-control"
                                       minlength="5" maxlength="15" required value="${fn:escapeXml('')}"
                                       placeholder=<fmt:message key="login.input.password"/>>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 offset-sm-3">
                                        <input type="submit" name="login-submit" id="login-submit"
                                               value=<fmt:message key="login.button.signin"/>>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="command" value="sign_in">
                        </form>

                        <form id="register-form" method="post" action="${pageContext.request.contextPath}/controller"
                              style="display: none;">
                            <label for="newLogin"><fmt:message key="register.input.login"/>*</label>
                            <div class="form-group">
                                <input type="text" name="newLogin" class="form-control" id="newLogin" required
                                       maxlength="15" minlength="5" value="${fn:escapeXml('')}">
                                <div class="input-tip">
                                    <c:if test="${not empty param.serverMessage}">
                                        <fmt:message key="register.tip.login"/>
                                    </c:if>
                                </div>
                            </div>
                            <label for="email"><fmt:message key="register.input.email"/>*</label>
                            <div class="form-group">
                                <input type="email" name="newEmail" id="email" class="form-control"
                                       minlength="8" maxlength="45" value="${fn:escapeXml('')}" required>
                            </div>
                            <div class="form-group">
                                <label for="password-register"><fmt:message key="register.input.password"/>*</label>
                                <input type="password" name="newPassword" id="password-register" class="form-control"
                                       maxlength="15" minlength="5" value="${fn:escapeXml('')}" required>
                                <div class="input-tip">
                                    <c:if test="${not empty param.serverMessage}">
                                        <fmt:message key="register.tip.password"/>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirm-password"><fmt:message
                                        key="register.input.confirm_password"/>*</label>
                                <input type="password" name="confirmPassword" id="confirm-password" required
                                       class="form-control" maxlength="15" minlength="5" value="${fn:escapeXml('')}">
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 offset-sm-3">
                                        <input type="submit" name="register-submit" id="register-submit"
                                               value=<fmt:message key="login.button.signup"/>>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="command" value="sign_up">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <hr class="my-hr">
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/login.js"></script>
<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>
