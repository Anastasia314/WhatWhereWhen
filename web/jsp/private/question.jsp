<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custg" uri="customtag" %>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="page.title.general"/></title>
    <link rel="shortcut icon" href="${contextPath}/images/logo.png">
    <link href="${contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <%@ include file="/WEB-INF/jspf/novigation.jspf" %>
    <div id="main">
        <c:forEach items="${sessionScope.questionList}" var="item">
            <!-- Post -->
            <article class="post">
                <header>
                    <div class="title">
                        <h2>${item.type}</h2>
                        <p><fmt:message key="question.header.source"/> ${item.source}</p>
                    </div>
                    <div class="meta">
                        <time class="published">
                            <jsp:useBean id="publishedDate" class="java.util.Date"/>
                            <c:set target="${publishedDate}" property="time" value="${item.date}"/>
                            <fmt:formatDate value="${publishedDate}" type="date"/>
                        </time>
                        <div class="author"><span class="name">
                            ${sessionScope.questionAuthors.get(item.authorId).login}<br>
                                <fmt:message key="main.header.rating"/>:
                                ${sessionScope.questionAuthors.get(item.authorId).rating}</span>
                            <c:if test="${not empty sessionScope.questionAuthors.get(item.authorId).photo}">
                                <img src="${contextPath}/image?command=display&authorId=${item.authorId}">
                            </c:if>
                        </div>
                    </div>
                </header>
                <div class="image featured question">
                    <c:if test="${not empty item.photo}">
                        <img src="${contextPath}/images/${item.photo}" alt=""/>
                    </c:if>
                </div>
                <div class="question-div">
                    <h3 id="question-result${item.questionId}"></h3>
                    <div id="question">
                        <b><fmt:message key="question.header.question"/> </b>${item.body}
                    </div>
                    <label for="user-answer${item.questionId}">
                        <b><fmt:message key="question.header.answer"/></b></label>
                    <div id="answer${item.questionId}" style="display: none">${item.answer}</div>
                    <textarea id="user-answer${item.questionId}" class="form-control"
                              minlength="1" maxlength="500" style="width: 67%;" required>${fn:escapeXml('')}</textarea>
                </div>
                <footer>
                    <ul class="actions">
                        <li id="answer-button${item.questionId}">
                            <a onclick="checkAnswer(${item.questionId});" class="button big">
                                <fmt:message key="question.button.answer"/></a>
                        </li>
                        <li id="appeal-button${item.questionId}" style="display: none">
                            <a onclick="fileAppeal(${item.questionId});" class="button big">
                                <fmt:message key="question.button.appeal"/></a>
                        </li>
                    </ul>
                    <ul class="stats">
                        <li><a href="#">General</a></li>
                        <li><a href="#" class="icon fa-heart">28</a></li>
                        <li><a href="#" class="icon fa-comment">128</a></li>
                    </ul>
                </footer>
            </article>
        </c:forEach>

        <!-- Pagination -->
        <ul class="actions pagination">
            <c:if test="${sessionScope.lastQuestionNum gt sessionScope.questionDisplayNum}">
                <li><a href="${contextPath}/controller?command=load_questions&direction=previous"
                       class="button big previous"><fmt:message key="main.button.prev_page"/></a></li>
            </c:if>
            <c:if test="${sessionScope.questionsAmount gt sessionScope.lastQuestionNum}">
                <li><a href="${contextPath}/controller?command=load_questions&direction=next"
                       class="button big next"><fmt:message key="main.button.next_page"/></a></li>
            </c:if>
        </ul>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <div class="logo">
                <img src="${contextPath}/images/logo.jpg" alt=""/>
            </div>
            <header>
                <h2>Questions base</h2>
                <p>play, add, like</p>
            </header>
        </section>

        <!-- Mini Posts -->
        <section>
            <div class="mini-posts">
                <!-- Mini Post -->
                <article>
                    <h2><fmt:message key="question.header.add"/></h2>
                    <h3 id="server-message">
                        <c:if test="${not empty param.serverMessage}">
                            <fmt:message key="${param.serverMessage}"/>
                        </c:if>
                    </h3>
                    <form id="register-question-form" method="post"
                          action="${contextPath}/controller">
                        <label for="type"><fmt:message key="question.input.type"/>*</label>
                        <div class="form-group">
                            <input type="text" name="type" class="form-control" id="type" required
                                   maxlength="30" value="${fn:escapeXml('')}" minlength="2">
                        </div>
                        <label for="body"><fmt:message key="question.input.body"/>*</label>
                        <div class="form-group">
                            <textarea name="body" id="body" class="form-control"
                                      minlength="20" maxlength="1000" required> ${fn:escapeXml('')}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="answer"><fmt:message key="question.input.answer"/>*</label>
                            <textarea name="answer" id="answer" class="form-control"
                                      minlength="1" maxlength="500" required>${fn:escapeXml('')}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="source"><fmt:message key="question.input.source"/>*</label>
                            <textarea name="source" id="source" class="form-control"
                                      minlength="5" maxlength="150" required>${fn:escapeXml('')}</textarea>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div>
                                    <input type="submit" name="register-submit" id="register-submit"
                                           value=<fmt:message key="question.button.send"/>>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="command" value="send_question">
                    </form>
                </article>
            </div>
        </section>
        <!-- About -->
        <section class="blurb">
            <h2><fmt:message key="main.section.about"/></h2>
            <p><fmt:message key="main.about.content"/></p>
            <ul class="actions">
                <li><a href="${contextPath}/jsp/general/about.jsp" class="button"><fmt:message
                        key="main.link.learn_more"/></a></li>
            </ul>
        </section>
        <div style='overflow: hidden'>
            <a href="http://13.tvigra.ru"><img src="${contextPath}/images/13sector.jpg"></a>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </section>
</div>
<script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/skel.min.js"></script>
<script src="${contextPath}/js/util.js"></script>
<script src="${contextPath}/js/main.js"></script>
</body>
</html>
