<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="UTF-8">
    <title>Wrap</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/../wrap.css" />
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/../wrap.js"></script>
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
    <div class="container">
        <ul id="wrap">
            <c:forEach items="${photos}" var="photo">
                <li class="poster-item zturn-item">
                    <img src="<c:out value=" ${photo}" />">
                </li>
            </c:forEach>
        </ul>
    </div>
    <script type="text/javascript">
        const effect = '${effect}'
        let delay = '${delay}' ? Number.parseInt('${delay}') : 5000;
        let opacity, scale
        if ('far' == effect) {
            opacity = 0.8
            scale = 0.6
        } else {
            opacity = 0.9
            scale = 0.9
        }
        var aa = new zturn({
            id: "wrap",
            opacity: opacity,
            width: 385,
            Awidth: 1024,
            scale: scale,
            auto: true,//是否轮播 默认5000
            turning: delay//轮播时长
        })
    </script>

</html>