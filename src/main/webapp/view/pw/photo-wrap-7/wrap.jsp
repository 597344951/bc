<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Wrap...</title>
    <link rel="stylesheet" href="<%=path%>/../wrap.css" />

    <style>
        body {
            overflow: hidden;
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
    <c:if test="${backgroundMusic != null}">
		<audio style="display: none;" autoplay="autoplay" controls="controls" loop="loop" preload="auto" src="${backgroundMusic}"></audio>
	</c:if>
    <div class="demo-wrapper">
        <h1></h1>
        <ul class="portfolio-items">
            <c:forEach items="${photos}" var="photo">
                <li class="item">
                    <figure>
                        <div class="view"> <img src="<c:out value=" ${photo}" />" /> </div>
                        <figcaption>
                            <p><span> <a href="#">blank</a></span></p>
                            <p><span>blank</span></p>
                        </figcaption>
                    </figure>
                    <div class="date">blank</div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <script src="https://aliyun.beecdn.cn/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://aliyun.beecdn.cn/libs/modernizr/2010.07.06dev/modernizr.min.js"></script>
    <script src="<%=path%>/../wrap.js"></script>

</body>

</html>