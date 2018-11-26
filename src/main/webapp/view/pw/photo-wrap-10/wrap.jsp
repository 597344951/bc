<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
	<meta charset="UTF-8">
	<title>Wrap...</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/../wrap.css" />
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
	<div id="imageFlow">
		<div class="bank">
			<c:forEach items="${photos}" var="photo">
				<a rel="<c:out value=" ${photo}" />" title="" href="#" target="_blank"></a>
			</c:forEach>
		</div>
		<div class="text">
			<div class="title">Loading</div>
			<div class="legend">Please wait...</div>
		</div>
		<div class="scrollbar"> <img class="track" src="<%=path%>/../sb.gif" alt=""> <img class="arrow-left" src="<%=path%>/../sl.gif"
			 alt="">
			<img class="arrow-right" src="<%=path%>/../sr.gif" alt=""> <img class="bar" src="<%=path%>/../sc.gif" alt="">
		</div>
	</div>
</body>

</html>