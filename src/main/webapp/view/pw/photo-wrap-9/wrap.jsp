<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Wrap...</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/../wrap.css"/>
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
<body class="fullscreen">
	<c:if test="${backgroundMusic != null}">
		<audio style="display: none;" autoplay="autoplay" controls="controls" loop="loop" preload="auto" src="${backgroundMusic}"></audio>
	</c:if>
	<div id="gallery" class="fullscreen"></div>
	<div id="nav" class="navbar">
	    <button id="preview">&lt; 前一张</button>
	    <button id="next">下一张 &gt;</button>
	</div>
	
	
	<script type="text/javascript" src="<%=path%>/../wrap.js"></script>
	<script>
		const delay = '${delay}' ? Number.parseInt('${delay}') : 5000;
	    window.onload = function () {
			let photos = [
				<c:forEach items="${photos}" var="photo">
					{name: "<c:out value=" ${photo}" />", caption: "" },
				</c:forEach>
			]
	        new polaroidGallery(photos);
	    }
	</script>
</body>
</html>