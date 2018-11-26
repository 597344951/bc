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
        <c:forEach items="${photos}" var="photo" varStatus="status">
            .photo-cube__panel:nth-of-type(<c:out value="${status.count}" />) {
                background-image: url(<c:out value="${photo}" />);
                background-size: cover;
                background-position: center
            }
        </c:forEach>
    </style>
</head>

<body>
    <c:if test="${backgroundMusic != null}">
		<audio style="display: none;" autoplay="autoplay" controls="controls" loop="loop" preload="auto" src="${backgroundMusic}"></audio>
	</c:if>
    <div class="content">
        <div class="photo-cube__wrapper">
            <input type="radio" id="1" name="cube" checked="true" class="photo-cube__control" />
            <input type="radio" id="2" name="cube" class="photo-cube__control" />
            <input type="radio" id="3" name="cube" class="photo-cube__control" />
            <input type="radio" id="4" name="cube" class="photo-cube__control" />
            <input type="radio" id="5" name="cube" class="photo-cube__control" />
            <input type="radio" id="6" name="cube" class="photo-cube__control" />
            <div class="photo-cube">
                <div class="photo-cube__panel"></div>
                <div class="photo-cube__panel"></div>
                <div class="photo-cube__panel"></div>
                <div class="photo-cube__panel"></div>
                <div class="photo-cube__panel"></div>
                <div class="photo-cube__panel"></div>
            </div>
            <div class="photo-cube__actions--left">
                <label for="6" class="photo-cube__action"></label>
                <label for="1" class="photo-cube__action"></label>
                <label for="2" class="photo-cube__action"></label>
                <label for="3" class="photo-cube__action"></label>
                <label for="4" class="photo-cube__action"></label>
                <label for="5" class="photo-cube__action"></label>
            </div>
            <div class="photo-cube__actions--right">
                <label for="2" class="photo-cube__action"></label>
                <label for="3" class="photo-cube__action"></label>
                <label for="4" class="photo-cube__action"></label>
                <label for="5" class="photo-cube__action"></label>
                <label for="6" class="photo-cube__action"></label>
                <label for="1" class="photo-cube__action"></label>
            </div>
            <div class="photo-cube__indicators">
                <label for="1" class="photo-cube__indicator"></label>
                <label for="2" class="photo-cube__indicator"></label>
                <label for="3" class="photo-cube__indicator"></label>
                <label for="4" class="photo-cube__indicator"></label>
                <label for="5" class="photo-cube__indicator"></label>
                <label for="6" class="photo-cube__indicator"></label>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="<%=path%>/../wrap.js"></script>

</html>