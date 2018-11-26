<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <title>Photo wrap...</title>
    <link rel="stylesheet" href="<%=path%>/../wrap.css">
    <script src="<%=path%>/../wrap.js"></script>
    <style>
        body {
			<c:if test="${backgroundColor != null}">
				background-color: ${backgroundColor};
			</c:if>
			<c:if test="${backgroundImage != null}">
				background: url('${backgroundImage}');
				background-size: 100% 100%;
			</c:if>
		}
    </style>
</head>

<body>
    <noscript>
        <strong>We're sorry but hello-world doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
    </noscript>
    <c:if test="${backgroundMusic != null}">
		<audio style="display: none;" autoplay="autoplay" controls="controls" loop="loop" preload="auto" src="${backgroundMusic}"></audio>
	</c:if>
    <div class="perspective">
        <div class="wrap">
            <c:forEach items="${photos}" var="photo">
                <img src="<c:out value=" ${photo}" />">
            </c:forEach>
            <p></p>
        </div>
    </div>
    <div class="mask"></div>
    <div class="preview">
        <img>
    </div>
</body>

</html>