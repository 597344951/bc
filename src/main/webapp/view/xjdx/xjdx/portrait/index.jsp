<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
	<title>Home</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="/view/xjdx/xjdx/portrait/assets/css/main.css" />
	<style>
		#banner {
		background-image: url("${photos[0]}");
	}
	</style>
</head>

<body>

	<!-- Header -->
	<header id="header">
		<div class="inner">
			<a href="index.html" class="logo">先进典型</a>
			<nav id="nav">
				<!-- <a href="/view/xjdx/index.jsp">Home</a> -->

			</nav>
		</div>
	</header>
	<!-- <a href="#menu" class="navPanelToggle"><span class="fa fa-bars"></span></a> -->

	<!-- Banner -->
	<section id="banner">
		<div class="inner">
			<h1>${xx.x6} <span>${xx.x10}</span></h1>
			<!-- <ul class="actions">
			<li><a href="#" class="button alt">Get Started</a></li>
		</ul> -->
		</div>
	</section>

	<!-- One -->
	<section id="one" style="height: 650px">
		<div class="inner">
			<header>
				<h2>${xx.x7}</h2>
			</header>
			<p>${xx.x11}</p>
			<!-- <ul class="actions">
			<li><a href="#" class="button alt">Learn More</a></li>
		</ul> -->
		</div>
	</section>

	<!-- Two -->
	<section id="two">
		<div class="inner">
			<article>
				<div class="content">
					<header>
						<h3>${xx.x8}</h3>
					</header>
					<div class="image fit">
						<img src="${photos[1]}" alt="" />
					</div>
					<p>${xx.x12}</p>
				</div>
			</article>
			<article class="alt">
				<div class="content">
					<header>
						<h3>${xx.x9}</h3>
					</header>
					<div class="image fit">
						<img src="${photos[2]}" alt="" />
					</div>
					<p>${xx.x13}</p>
				</div>
			</article>
		</div>
	</section>


	<!-- Scripts -->
	<script src="/view/xjdx/xjdx/portrait/assets/js/jquery.min.js"></script>
	<script src="/view/xjdx/xjdx/portrait/assets/js/skel.min.js"></script>
	<script src="/view/xjdx/xjdx/portrait/assets/js/util.js"></script>
	<script src="/view/xjdx/xjdx/portrait/assets/js/main.js"></script>

</body>

</html>