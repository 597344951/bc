<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html >
<head>
	<meta 	http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>先进典型</title>
	<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- <link href="/view/xjdx/xjdx/css/bootstrap.css" rel='stylesheet' type='text/css' />
	<script src="/view/xjdx/xjdx/js/jquery.min.js"></script> -->

	<style>
	*{
		margin: 0;
		padding: 0;
	}
	html,body{
		margin: 0;
		padding: 0;
		width: 100%;
		height: 100%;
	}
	.box{
		width: 100%;
		height: 100%;
		background: url(/view/xjdx/xjdx/images/contact.jpg);
		background-size: cover;
	}
	.top-header{
		height:15%;
	}
	a{
		text-decoration: none;
		color:#fff;
		font-size: 36px;
	}
	a>span{
	    border: 7px solid red;
	    padding: 0px 14px;
	    font-size: 1.2em;
	    color: #fff;
	    line-height: 1;
	}
	.logo{
		padding-top: 1%;
		width: 80%;
		margin:0 auto;
		height: 100%;
		overflow: hidden;
	}
	.logo h1{
		float: left;
	}
	.nav-wil{
		float: right;
		padding: 30px 0;
	}
	.nav-wil li{
		float: left;
		list-style: none;
	}
	.nav-wil li a{
		font-size: 18px;
		margin-right: 15px;
	}
	.content{
		overflow: hidden;
	}
	.content{
		height: 65%;
		background-color: #fff;
	}
	.contain-left,.contain-right{
		float: left;
		width: 50%;
		height: 100%;
	}
	.contain-right{
		padding: 40px 20px 0 40px;
	}
	.contain-left img{
		display: block;
	}
	.carousel,.item,.carousel-inner,.item>img{
		width: 100%;
		height: 100%;
	}

	.footer{
		height:20%;
		padding-bottom: 20px;
	}
	.footer p{
		box-sizing:border-box;
		text-align: center;
		font-size:32px;
		line-height: 34px;
		font-weight: bold;
		width: 80%;
		margin: 0 auto;
		color: #fff;
	}
	.content-mask{
		position: absolute;
		width: 100%;
		height: 65%;
		background-color: rgba(0,0,0,.1);
		z-index: 10;
	}
	.active a:hover,li .scroll:hover{
		color: #fff;
		text-decoration: none;
		cursor: pointer;
		font-weight: bold;
	}
	</style>
</head>
<body>
	<div class="box">
		<div class="top-header">
			<div class="top-nav">
				<div class="navigation">
					<div class="logo">
						<h1><a href=""><span>人</span>物简介</a></h1>
						<ul class="nav-wil">
							<li class="active">
								
							</li>
							<!-- <li>
								<a class="scroll" href=" " data-hover="节目制作" onclick="makeshow()">节目制作</a>
							</li> -->
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="content-mask"></div>
		<div class="content">
			<div class="contain-left">
				<div id="myCarousel1" class="carousel slide">
					<!-- 轮播（Carousel）指标 -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>   

					<!-- 轮播（Carousel）项目 -->
					<div class="carousel-inner">
						<div class="item active">
							<img src="${photos[1]}" alt="">
						</div>
						<div class="item">
							<img src="${photos[2]}" alt="">
						</div>
						<div class="item">
							<img src="${photos[3]}" alt="">
						</div>
					</div>
					
				</div>
			</div>
			<div class="contain-right">
				<div id="myCarousel2" class="carousel slide">
					<!-- 轮播（Carousel）指标 -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>   

					<!-- 轮播（Carousel）项目 -->
					<div class="carousel-inner">
						<div class="item active">
							<h3>${xx.x6}</h3>
							<p>${xx.x10}</p>
						</div>
						<div class="item">
							<h3>${xx.x7}</h3>
							<p >${xx.x11}</p>
						</div>
						<div class="item">
							<h3>${xx.x8}</h3>
							<p>${xx.x12}</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<p>${xx.x13}</p>
		</div>
	</div>
</body>
<script>
	$(function(){
		$("#myCarousel1").carousel('cycle');
		$("#myCarousel2").carousel('cycle');
	});
</script>
<script>
		function makeshow() {
			var share = [{
			
				weburl: '/faker/xjdx/new_model/index2.html',
				playtime: 5 * 1000
			} ];
			
			var url = '/view/publish/new.jsp?title=先进典型&startStep=2&url=' + encodeURIComponent(JSON.stringify(share));
			window.location.href = url;
		}
	</script>
</html>