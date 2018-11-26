<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
	<meta charset="utf-8">
	<title>Wrap...</title>
	<link rel="stylesheet" href="<%=path%>/../wrap.css">
	<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
	<script src="<%=path%>/../wrap.js"></script>
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
	<ul class="wrap"></ul>

</body>
<script>
	let photos = [
		<c:forEach items="${photos}" var="photo">
			'<c:out value="${photo}"/>',
		</c:forEach >
	];
	let curPhotoIndex = 0;
	let delay = '${delay}' ? Number.parseInt('${delay}') : 5000;
	let wrap = new Wrap();
	if (photos.length > 0) {
		wrap.load(photos[curPhotoIndex], () => {
			wrap.show();
		});

		setInterval(() => {
			curPhotoIndex++;
			if (curPhotoIndex == photos.length) {
				curPhotoIndex = 0;
			}
			wrap.hide();
			setTimeout(() => {
				wrap.load(photos[curPhotoIndex], () => {
					wrap.show();
				});
			}, delay / 5);
		}, delay);
	}
</script>

</html>