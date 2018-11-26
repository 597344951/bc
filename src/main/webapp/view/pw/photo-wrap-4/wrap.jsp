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
    <c:forEach items="${photos}" var="photo">
        <div class="bg">
            <img src="<c:out value=" ${photo}" />">
        </div>
    </c:forEach>
   
    <div id="wrap">
        <ul>
            <c:forEach items="${photos}" var="photo">
                <li>
                    <div class="text">
                        <p></p>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
<script>
    const delay = '${delay}' ? Number.parseInt('${delay}') : 5000;
</script>
<script type="text/javascript" src="<%=path%>/../wrap.js"></script>
</html>