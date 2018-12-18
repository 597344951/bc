<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html >
<head>
<title>Home</title>     
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" /><!-- Bootstrap stylesheet -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" /><!-- stylesheet -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
</head>
<body>

	

	
	<!-- about-section -->	
		<div class="w3-agileits-about-section" id="w3-agile-about" style="padding-bottom:0;padding-top:30px;max-width:  1280px;max-height: 920px">
			<div class="container">
			
				<div class="about-section-grids">
					<!-- <div class="col-md-4 wthree-about-section-grid">
						<img src="images/abt_pic.jpg" alt="" />
					</div> -->
					<div class="col-md-8 wthree-about-section-grid" style="width:100%;">
						<h5>${xx.x6}</h5>
						<p><img src="${photos[0]}" alt="" />${xx.x10}</p>
					</div>			
				</div>
			</div>
		</div>

		<div class="agileits-w3layouts-specials" id="agileits-specials" style="max-height:980px;max-width: 1280px;">

				<div class="special-grids">
					<div class="col-md-6 special-left l-grids">
						 <figure class="effect-bubba">
								<img src="${photos[1]}" alt=""/>
									
						 </figure>
					</div>
					<div class="col-md-6 special-right">
						<div class="sp-wid">
							<h4>${xx.x7}</h4>
							<p>${xx.x11}</p>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="where-spl">
					<div class="col-md-6 special-right l-left">
						<div class="l-wid">
							<h4>${xx.x8}</h4>
							<p>${xx.x12}</p>
						</div>
						
					</div>
					<div class="col-md-6 special-left l-right l-grids">
						 <figure class="effect-bubba">
								<img src="${photos[2]}" alt=""/>
									
						 </figure>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="spl-bot">
						<div class="col-md-6 special-left spl-bot-right l-grids">
							<figure class="effect-bubba">
								<img class="img-responsive" src="${photos[3]}" alt=" "/>
		
							</figure>
						</div>
						<div class="col-md-6 spl-bot-left">
							<h4>${xx.x9}</h4>
							<p>${xx.x13}</p>
						</div>
						<div class="clearfix"></div>
				</div>
		</div>
		<!-- specials -->
		

</body>
</html>