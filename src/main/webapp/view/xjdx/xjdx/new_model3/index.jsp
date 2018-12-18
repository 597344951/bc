<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html> 
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">  
<title>Home</title>

<link href="/view/xjdx/xjdx/new_model3/css/bootstrap.min.css" rel="stylesheet">
<link href="/view/xjdx/xjdx/new_model3/css/style.css" rel="stylesheet" type="text/css"> 
<style>
	.container{
		width:85%;
	}
</style>
</head>
<body> 
<section id="hero_section" class="top_cont_outer">
<div class="hero_wrapper" style="height:500px">
<div class="container">
  <div class="hero_section">
	<div class="row">
	  <div class="col-lg-7 col-sm-7">
		<div class="top_left_cont zoomIn wow animated"> 
		  <h2 style="word-break: break-all">${xx.x6}</h2>
		  <p style="word-break: break-all">${xx.x10}</p>
		   </div>
	  </div>
	  <div class="col-lg-5 col-sm-5">
		<img src="${photos[0]}" class="zoomIn wow animated" alt="" />
	  </div>
	</div>
  </div>
</div>
</div>
</section>

<section id="aboutUs" style="height:580px">
<div class="inner_wrapper">
<div class="container">
<div class="inner_section">
<div class="row">
  <div class=" col-lg-4 col-md-4 col-sm-4 col-xs-12 pull-right"><img src="${photos[1]}" class="withripple delay-03s animated wow zoomIn" style="max-height:400px" alt=""></div>
	<div class=" col-lg-7 col-md-7 col-sm-7 col-xs-12 pull-left">
		<div class=" delay-01s animated fadeInDown wow animated">
		<h3 style="word-break: break-all">${xx.x7}</h3><br/> 
		<p style="word-break: break-all">${xx.x11}</p>
</div>
 
   </div>
	
  </div>
  
  
</div>
</div> 
</div>
</section>






<script src="/view/xjdx/xjdx/new_model3/js/jquery-1.10.2.min.js"></script>




</body>
</html>
