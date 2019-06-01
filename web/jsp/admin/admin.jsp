<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <title><fmt:message key="page.title.admin"/></title>
    <link rel="shortcut icon" href="${contextPath}/images/logo.png">
    <link href="${contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <%@ include file="/WEB-INF/jspf/novigation.jspf" %>
    <div id="main">
        <!-- Post -->
        <article class="post" id="users">
            <header>
                <div class="title">
                    <h2><fmt:message key="admin.button.users"/></h2>
                    <p><fmt:message key="project.general.name"/></p>
                </div>
                <div class="meta">

                </div>
            </header>
            <table>
                <tbody>
                <tr>
                    <td><fmt:message key="admin.table_data.userId"/></td>
                    <td><fmt:message key="admin.table_data.login"/></td>
                    <td><fmt:message key="admin.table_data.rating"/></td>
                    <td><fmt:message key="admin.table_data.isActive"/></td>
                    <td><fmt:message key="admin.button.remove_points"/></td>
                </tr>
                <c:forEach items="${sessionScope.userList}" var="item">
                    <tr id="user-row${item.userId}">
                        <td>${item.userId}</td>
                        <td>${item.login}</td>
                        <td id="user-rating${item.userId}">${item.rating}</td>
                        <td>${item.active}</td>
                        <td>
                            <a onclick="removePoints(${item.userId});" class="button big">
                                <fmt:message key="admin.button.remove_points"/></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </article>
        <article class="post" id="questions">
            <jsp:useBean id="publishedDate" class="java.util.Date"/>
            <header>
                <div class="title">
                    <h2><fmt:message key="admin.button.questions"/></h2>
                    <p><fmt:message key="project.general.name"/></p>
                </div>
                <div class="meta">

                </div>
            </header>
            <c:forEach items="${sessionScope.sentQuestions}" var="item">
                <div id="sent-question${item.questionId}">
                    <label for="type${item.questionId}"><fmt:message key="question.input.type"/></label>
                    <div class="form-group">
                        <input type="text" name="type" class="form-control" id="type${item.questionId}" required
                               maxlength="30" value="${fn:escapeXml(item.type)}" minlength="2">
                    </div>
                    <div class="image featured question">
                        <c:if test="${not empty item.photo}">
                            <img src="${contextPath}/images/${item.photo}" alt=""/>
                        </c:if>
                    </div>
                    <label for="body${item.questionId}"><fmt:message key="question.input.body"/></label>
                    <div class="form-group">
                            <textarea name="body" id="body${item.questionId}" class="form-control"
                                      minlength="20" maxlength="1000" required>${fn:escapeXml(item.body)}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="answer${item.questionId}"><fmt:message key="question.input.answer"/></label>
                        <textarea name="answer" id="answer${item.questionId}" class="form-control"
                                  minlength="1" maxlength="500" required>${fn:escapeXml(item.answer)}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="source${item.questionId}"><fmt:message key="question.input.source"/></label>
                        <textarea name="source" id="source${item.questionId}" class="form-control"
                                  minlength="5" maxlength="150" required>${fn:escapeXml(item.source)}</textarea>
                    </div>
                    <footer>
                        <ul class="actions">
                            <li>
                                <a onclick="approveQuestion(true,${item.questionId});"
                                   class="button big"><fmt:message key="admin.button.approve"/></a>
                            </li>
                            <li>
                                <a onclick="approveQuestion(false,${item.questionId});" class="button big">
                                    <fmt:message key="admin.button.not_approve"/></a>
                            </li>
                        </ul>
                    </footer>

                </div>
            </c:forEach>
        </article>
        <article class="post" id="appeals">
            <header>
                <div class="title">
                    <h2><fmt:message key="admin.button.appeals"/></h2>
                    <p><fmt:message key="project.general.name"/></p>
                </div>
                <div class="meta">
                </div>
            </header>
            <table>
                <tbody>
                <tr>
                    <td><fmt:message key="admin.table_data.questionId"/></td>
                    <td><fmt:message key="admin.table_data.userId"/></td>
                    <td><fmt:message key="admin.table_data.answer"/></td>
                    <td><fmt:message key="admin.table_data.correct_answer"/></td>
                    <td><fmt:message key="admin.button.approve"/></td>
                    <td><fmt:message key="admin.button.not_approve"/></td>
                </tr>
                <c:forEach items="${sessionScope.appealQuestions}" var="item">
                    <tr id="appeal-question${item.questionId}">
                        <td>${item.questionId}</td>
                        <td>${item.userId}</td>
                        <td>${item.answer}</td>
                        <td>${item.correctAnswer}</td>
                        <td>
                            <a onclick="approveAppeal(true,${item.questionId},${item.userId});"
                               class="button big"><fmt:message key="admin.button.approve"/></a>
                        </td>
                        <td>
                            <a onclick="approveAppeal(false,${item.questionId},${item.userId});"
                               class="button big"><fmt:message key="admin.button.not_approve"/></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </article>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <header>
                <h2><fmt:message key="project.general.name"/></h2>
            </header>
        </section>

        <section>
            <div>
                <article>
                    <header>
                        <h3 class="button big fit" id="users-button">
                            <a href="#"><fmt:message key="admin.button.users"/></a>
                        </h3>
                        <h3 class="button big fit" id="questions-button">
                            <a href="#"><fmt:message key="admin.button.questions"/></a>
                        </h3>
                        <h3 class="button big fit" id="appeals-button">
                            <a href="#"><fmt:message key="admin.button.appeals"/></a>
                        </h3>
                    </header>
                </article>
            </div>
        </section>

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </section>
</div>
<script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/skel.min.js"></script>
<script src="${contextPath}/js/util.js"></script>
<script src="${contextPath}/js/main.js"></script>
<script src="${contextPath}/js/admin.js"></script>
</body>
</html>
