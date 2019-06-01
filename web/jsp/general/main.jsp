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
    <title><fmt:message key="page.title.general"/></title>
    <link rel="shortcut icon" href="${contextPath}/images/logo.png">
    <link href="${contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <%@ include file="/WEB-INF/jspf/novigation.jspf" %>
    <div id="main">
        <div id="articles-list">
            <jsp:useBean id="publishedDate" class="java.util.Date"/>
            <c:forEach items="${sessionScope.articleList}" var="item">
                <!-- Post -->
                <article class="post">
                    <header>
                        <div class="title">
                            <h2><a href="#" onclick="showArticle(${item.articleId})"> ${item.header}</a>
                            </h2>
                            <p>${item.theme}</p>
                        </div>
                        <div class="meta">
                            <time class="published">
                                <c:set target="${publishedDate}" property="time" value="${item.date}"/>
                                <fmt:formatDate value="${publishedDate}" type="date"/>
                            </time>
                            <div class="author">
                            <span class="name">${sessionScope.articleAuthors.get(item.authorId).login}<br>
                                <fmt:message key="main.header.rating"/>:
                                    ${sessionScope.articleAuthors.get(item.authorId).rating}</span>
                                <c:if test="${not empty sessionScope.articleAuthors.get(item.authorId).photo}">
                                    <img src="${contextPath}/image?command=display&authorId=${item.authorId}">
                                </c:if>
                            </div>
                        </div>
                    </header>
                    <div class="image featured">
                        <c:if test="${not empty item.photo}">
                            <img src="${contextPath}/images/${item.photo}" alt=""/>
                        </c:if>
                    </div>
                    <p>
                        <custg:sectionTag articleBody="${item.body}"/>
                    </p>
                    <footer>
                        <ul class="actions">
                            <li><a href="#" onclick="showArticle(${item.articleId})" class="button big continue-link">
                                <fmt:message key="main.button.continue_reading"/></a>
                            </li>
                        </ul>
                    </footer>
                </article>
            </c:forEach>
            <!-- Pagination -->
            <ul class="actions pagination">
                <c:if test="${sessionScope.articlesAmount gt sessionScope.lastArticleNum}">
                    <li><a href="${contextPath}/controller?command=load_articles&direction=next"
                           class="button big next"><fmt:message key="main.button.next_page"/></a></li>
                </c:if>
                <c:if test="${sessionScope.lastArticleNum gt sessionScope.articleDisplayNum}">
                    <li><a href="${contextPath}/controller?command=load_articles&direction=previous"
                           class="button big previous"><fmt:message key="main.button.prev_page"/></a></li>
                </c:if>
            </ul>
        </div>

        <div id="one-article" style="display:none;">
            <!-- Post -->
            <article class="post">
                <header>
                    <div class="title">
                        <h2 id="browse-header"></h2>
                        <p id="browse-theme"></p>
                    </div>
                    <div class="meta">
                        <time class="published" id="browse-date">
                        </time>
                        <div class="author">
                            <img id="browse-author" src="">
                        </div>
                    </div>
                </header>
                <div class="image featured">
                    <img id="browse-photo" src="" alt=""/>
                </div>
                <p id="browse-body"></p>
            </article>
            <ul class="actions pagination">
                <li><a href="${contextPath}/jsp/general/main.jsp" onclick="toArticles()"
                       class="button big previous"><fmt:message key="main.button.back"/></a></li>
            </ul>
        </div>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <div class="logo"><img src="${contextPath}/images/logo.jpg" alt=""/></div>
            <header>
                <h2><fmt:message key="project.general.name"/></h2>
                <p>Another fine responsive site template by <a href="http://html5up.net">HTML5 UP</a></p>
            </header>
        </section>

        <!-- Mini Posts -->
        <section>
            <div class="mini-posts">
                <c:forEach items="${sessionScope.newestArticleList}" var="item">
                    <!-- Mini Post -->
                    <article class="mini-post">
                        <header>
                            <h3><a href="#" onclick="showArticle(${item.articleId})"> ${item.header} </a>
                            </h3>
                            <time class="published">
                                <c:set target="${publishedDate}" property="time" value="${item.date}"/>
                                <fmt:formatDate value="${publishedDate}" type="date"/>
                            </time>
                            <div class="author">
                                <c:if test="${not empty sessionScope.articleAuthors.get(item.authorId).photo}">
                                    <img src="${contextPath}/image?command=display&authorId=${item.authorId}">
                                </c:if>
                            </div>
                        </header>
                        <div class="image">
                            <c:if test="${not empty item.photo}">
                                <img src="${contextPath}/images/${item.photo}" alt=""/>
                            </c:if>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </section>
        <!-- About -->
        <section class="blurb">
            <h2><fmt:message key="main.section.about"/></h2>
            <p><fmt:message key="main.about.content"/></p>
            <ul class="actions">
                <li><a href="${contextPath}/jsp/general/about.jsp"
                       class="button"><fmt:message key="main.link.learn_more"/></a></li>
            </ul>
        </section>
        <div style='overflow: hidden;'>
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
