<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
<title>人物简介</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/view/xjdx/xjdx/css/bootstrap.css" rel='stylesheet' type='text/css' />
<script src="/view/xjdx/xjdx/js/jquery.min.js"></script>
<link href="/view/xjdx/xjdx/css/style.css" rel='stylesheet' type='text/css' />
<script type="text/javascript" src="/view/xjdx/xjdx/js/move-top.js"></script>
<script type="text/javascript" src="/view/xjdx/xjdx/js/easing.js"></script>
</head>
<style>
#home {
    background: url(${photos[0]}) no-repeat center top;
    background-size: cover;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    -ms-background-size: cover;
    min-height:440px;
	position: relative;
}
</style>
<body>
	<!-- container -->
	<!-- header -->
	<!-- <a class="make-program"
		onclick="makeshow()" >节目制作</a> -->
	<div id="home" class="header" >
		<div class="container">
			<!-- top-hedader -->
			<div class="top-header">
				<!-- /logo -->
				<!--top-nav---->
				<div class="top-nav">
					<div class="navigation">
						<div class="logo">
							<h1>
								<a href=""> <span>人</span>物简介
								</a>
							</h1>
						</div>
						<div class="navigation-right">
							<span class="menu"> <img src="${photos[0]}" alt=" " />
							</span>
							<nav class="link-effect-3" id="link-effect-3">
						<!-- 		<ul class="nav1 nav nav-wil">
									<li><a class="scroll" data-hover="先进典型" href="index.htm">先进典型</a>
									</li>
									<li><a class="scroll" data-hover="人物风采" href="1.htm">人物风采</a>
									</li>
								</ul> -->
							</nav>

						</div>
						<div class="clearfix"></div>
					</div>
					<!-- /top-hedader -->
				</div>
				<div class="banner-info">
					<div class="col-md-7 header-right">
						<h1>Hi !</h1>
						<h6>${xx.x1}</h6>
						<ul class="address">

							<li>
								<ul class="address-text">
									<li><b>姓名</b></li>
									<li>${xx.x2}</li>
								</ul>
							</li>
							<li>
								<ul class="address-text">
									<li><b>职业</b></li>
									<li>${xx.x3}</li>
								</ul>
							</li>
							<li>
								<ul class="address-text">
									<li><b>主要成就 </b></li>
									<li>${xx.x4}</li>
								</ul>
							</li>
							<li>
								<ul class="address-text">
									<li><b>籍贯 </b></li>
									<li>${xx.x5}</li>
								</ul>
							</li>
						</ul>
					</div>
					<div class="col-md-5 header-left">
						<img src="${photos[1]}" alt="">
					</div>
					<div class="clearfix"></div>

				</div>
			</div>
		</div>
	</div>

	<!-- services -->
	<div id="services" class="services">
		<div class="container">
			<div class="service-head one text-center ">
				<h4>人物经历</h4>
				<!-- <h3>人物
					<span>经历</span>
				</h3> -->
				<span class="border two"></span>
			</div>
			<!-- services-grids -->
			<div class="wthree_about_right_grids w3l-agile">
				<div class="col-md-6 wthree_about_right_grid">
					<div class="col-xs-2 wthree_about_right_grid_left">
						<div class="hvr-rectangle-in">
							<i class="glyphicon glyphicon-pencil"></i>
						</div>
					</div>
					<div class="col-xs-8 wthree_about_right_grid_right">
						<h4>${xx.x6}</h4>
						<p>${xx.x10}</p>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6 wthree_about_right_grid">
					<div class="col-xs-2 wthree_about_right_grid_left">
						<div class="hvr-rectangle-in">
							<i class="glyphicon glyphicon-cog"></i>
						</div>
					</div>
					<div class="col-xs-8 wthree_about_right_grid_right">
						<h4>${xx.x7}</h4>
						<p>${xx.x11}</p>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6 wthree_about_right_grid">
					<div class="col-xs-2 wthree_about_right_grid_left">
						<div class="hvr-rectangle-in">
							<i class="glyphicon glyphicon-leaf"></i>
						</div>
					</div>
					<div class="col-xs-8 wthree_about_right_grid_right">
						<h4>${xx.x8}</h4>
						<p>${xx.x12}</p>


					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6 wthree_about_right_grid">
					<div class="col-xs-2 wthree_about_right_grid_left">
						<div class="hvr-rectangle-in">
							<i class="glyphicon glyphicon-gift"></i>
						</div>
					</div>
					<div class="col-xs-8 wthree_about_right_grid_right">
						<h4>${xx.x9}</h4>
						<p>${xx.x13}</p>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>

			<!-- services-grids -->
		</div>
	</div>
	<!-- services -->





	<!-- /container -->










	<a href="#home" id="toTop" style="display: block;"> <span
		id="toTopHover" style="opacity: 1;"> </span>
	</a>
	<!--start-smooth-scrolling-->
	<script>
		function makeshow() {
					var share = [{
						weburl: '/view/xjdx/xjdx/rwfc.jsp',
						playtime: 5 * 1000
					}];
					var url = '/view/publish/new.jsp?title=先进典型&startStep=2&url=' + encodeURIComponent(JSON.stringify(share));
					window.location.href = url;
				}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			/*
										var defaults = {
								  			containerID: 'toTop', // fading element id
											containerHoverID: 'toTopHover', // fading element hover id
											scrollSpeed: 1200,
											easingType: 'linear' 
								 		};
			 */

			$().UItoTop({
				easingType : 'easeOutQuart'
			});

		});
	</script>
	<!--end-smooth-scrolling-->
	<!-- //for bootstrap working -->
	<script src="/view/xjdx/xjdx/js/bootstrap.js"></script>


</body>

</html>
