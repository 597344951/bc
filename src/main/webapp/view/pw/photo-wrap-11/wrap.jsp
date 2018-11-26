<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/../wrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/../fx.css" />
	<script src="https://aliyun.beecdn.cn/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
	<script src="https://aliyun.beecdn.cn/libs/classie/1.0.1/classie.min.js" type="text/javascript"></script>
</head>

<body>
	<c:if test="${backgroundMusic != null}">
		<audio style="display: none;" autoplay="autoplay" controls="controls" loop="loop" preload="auto" src="${backgroundMusic}"></audio>
 	</c:if>
	<div class="container">
		<section>
			<div id="component" class="component component-fullwidth">
				<ul class="itemwrap">
					<c:forEach items="${photos}" var="photo" varStatus="status">
						<c:if test="${status.first}">
							<li class="current"><img src="<c:out value=" ${photo}" />" alt="" /></li>
						</c:if>
						<c:if test="${!status.first}">
							<li><img src="<c:out value=" ${photo}" />" alt="" /></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</section>
	</div>
</body>
<script>
	const effect='${effect}'
</script>
<script src="<%=path%>/../wrap.js" type="text/javascript"></script>

</html>