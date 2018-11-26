<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/../wrap.css" />
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/../wrap.js"></script>
	<title>Wrap...</title>
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
	<c:if test="${backgroundMusic != null}">
		<audio style="display: none;" autoplay="autoplay" controls="controls" loop="loop" preload="auto" src="${backgroundMusic}"></audio>
	</c:if>
	<div class="heartPic">
		<ul>
			<li></li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[0]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[1]}" />
				</div>
			</li>
			<li></li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[2]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[3]}" />
				</div>
			</li>
			<li></li>
		</ul>
		<ul>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[4]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[5]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[6]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[7]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[8]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[9]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[10]}" />
				</div>
			</li>
		</ul>
		<ul>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[11]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[12]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[13]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[14]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[15]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[16]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[17]}" />
				</div>
			</li>
		</ul>
		<ul>
			<li></li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[18]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[19]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[20]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[21]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[22]}" />
				</div>
			</li>
			<li></li>
		</ul>
		<ul>
			<li></li>
			<li></li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[23]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[24]}" />
				</div>
			</li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[25]}" />
				</div>
			</li>
			<li></li>
			<li></li>
		</ul>
		<ul>
			<li></li>
			<li></li>
			<li></li>
			<li>
				<div class="in">
					<img width="100" height="100" src="${photos[26]}" />
				</div>
			</li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
</body>

</html>