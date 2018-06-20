<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>登陆系统</title>
<%@include file="/include/head.jsp"%>
<style>
	html,body{
		height: 100%;
		width: 100%;
	}
	body{
		background-image: url(assets/img/bg2.png);
		overflow: hidden;
		-webkit-background-size: 100% 100%;
		background-size: 100% 100%;
	}
	*{
		margin: 0;
		padding: 0;
	}
	h1{
		width: 100%;
		color:#fff;
		text-align: center;
		font-size: 56px;
		text-shadow: 3px 5px 0px #458dff;
	}
	.ipt{
		border-radius: 6px;
		border:1px solid #ddd;
		width: 384px;
		height: 40px;
		outline: none;
		margin-top: 20px;
		padding-left: 10px;
		box-shadow: inset 5px 5px 8px #ced1d4;
	}
	.icon1{
		background-image: url(assets/img/icon1.png);
		background-repeat: no-repeat;
		display: inline-block;
		width: 56px;
		height: 50px;
		vertical-align: bottom;
	}
	.icon2{
		background-image: url(assets/img/icon2.png);
		background-repeat: no-repeat;
		background-position: 6px 0px;
		display: inline-block;
		width: 56px;
		height: 50px;
		vertical-align: bottom;
	}
	.main{
		height: 250px;
		width: 600px;
		position: fixed;
		top: 40%;
		left: 50%;
		margin-top: -150px;
		margin-left: -300px;
		text-align: center;
		position: relative;
	}
	.btn{
		text-decoration: none;
		width: 205px;
		height: 50px;
		display: block;
		line-height: 44px; 
		border-radius: 8px;
		font-weight: bold;
		font-size: 20px;
		color: #fff;
		text-align: center;
		background-color: #458aff;
		letter-spacing:15px;
		margin: 50px auto;
		background: url(assets/img/btn.png) no-repeat;
	}
	.btn:active{
		padding-right: 1px;
		padding-top: 1px;
	}
	.name{
		margin: 30px 0;
	}
	.error {
	color: red;
	position: absolute;
	bottom:-40px;
	left: 33%;;
}
	</style>
</head>
<body>
<div class="main">
	<div class="error"><h3>${error}</h3></div>
	<h1>${sysInfo.appname}</h1>
	<form action="login" method="post">
		<div class="name">
			<i class="icon1"></i>
			<input name="un" value="develop" class="ipt" placeholder="请输入账户名" type="text">
		</div>
		<div class="kayword">
			<i class="icon2"></i>
			<input type="password" name="ps" value="develop" class="ipt" placeholder="请输入密码" type="text">
		</div>
		<button type="submit" value="登录" class="btn"></button>
	</form>
</div>

</body>
</html>
