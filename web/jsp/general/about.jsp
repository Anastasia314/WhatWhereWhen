<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custg" uri="customtag" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="page.title.about"/></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo.png">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <%@ include file="/WEB-INF/jspf/novigation.jspf" %>
    <div id="main">
        <!-- Post -->
        <article class="post">
            <header>
                <div class="title">
                    <h2><fmt:message key="page.title.about"/></h2>
                    <p><fmt:message key="project.general.name"/></p>
                </div>
                <div class="meta">
                    <p><fmt:message key="about.title.author"/></p>
                    <div class="author">
                        <img src="${pageContext.request.contextPath}/images/logo.png" alt=""/>
                    </div>
                    <p class="author">Anastasia Kavzovich</p>
                </div>
            </header>
            <article>
                <%@ include file="/resources/about_content.txt" %>
            </article>
            <object data="${pageContext.request.contextPath}/resources/Записка_К4.pdf"
                    type="application/pdf" width="100%" height="600px"></object>
            <br>
            <a href="${pageContext.request.contextPath}/resources/Записка_К4.pdf" class="button">
                <fmt:message key="about.button.download"/> </a>
        </article>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <a href="#" class="logo"><img src="${pageContext.request.contextPath}/images/logo.jpg" alt=""/></a>
            <header>
                <h2><fmt:message key="project.general.name"/></h2>
            </header>
            <p><fmt:message key="main.about.content"/></p>
        </section>

        <section>
            <div>
                <article>
                    <header>
                        <h3><fmt:message key="about.title.functions"/></h3>
                    </header>
                    <ul class="links">
                        <li><fmt:message key="about.function.main_page"/></li>
                        <li><fmt:message key="about.function.user_profile"/></li>
                        <li><fmt:message key="about.function.questions"/></li>
                        <li><fmt:message key="about.function.administration"/></li>
                    </ul>
                </article>
            </div>
        </section>

        <div style='overflow: hidden'>
            <a href="http://13.tvigra.ru"><img src="${pageContext.request.contextPath}/images/13sector.jpg"></a>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </section>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
