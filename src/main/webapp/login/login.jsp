<%@page import="java.util.Random"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    Random r = new Random(System.nanoTime());
    int n = r.nextInt(7);
    request.setAttribute("rn", n);
            
%>
<html>

<head>
	<base href="<%=basePath%>">
	<title>${sysInfo.appname}</title>
	<link href="${urls.getForLookupPath('/login/index.css')}" rel="stylesheet">

</head>

<body class="login-theme-${rn}">
	<div class="bg1"></div>
	<div class="gyl">
		${sysInfo.appname}
		<div class="gy2">打造国内最具规模的、最专业的智能服务平台 </div>
	</div>
	<form action="login" method="post">
	<div class="bg">
		<div class="wel">用户登录</div>
		
		<div class="user">
			<div id="yonghu">用户名</div>
			<input name="un" placeholder="请输入账户名" type="text" autocomplete="on" style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAAAXNSR0IArs4c6QAAAPhJREFUOBHlU70KgzAQPlMhEvoQTg6OPoOjT+JWOnRqkUKHgqWP4OQbOPokTk6OTkVULNSLVc62oJmbIdzd95NcuGjX2/3YVI/Ts+t0WLE2ut5xsQ0O+90F6UxFjAI8qNcEGONia08e6MNONYwCS7EQAizLmtGUDEzTBNd1fxsYhjEBnHPQNG3KKTYV34F8ec/zwHEciOMYyrIE3/ehKAqIoggo9inGXKmFXwbyBkmSQJqmUNe15IRhCG3byphitm1/eUzDM4qR0TTNjEixGdAnSi3keS5vSk2UDKqqgizLqB4YzvassiKhGtZ/jDMtLOnHz7TE+yf8BaDZXA509yeBAAAAAElFTkSuQmCC&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;"
			 >
		</div>
		<div class="password">
			<div id="yonghu">密&nbsp;&nbsp;&nbsp;码</div>
			<input type="password" name="ps" placeholder="请输入密码" autocomplete="off" style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAAAXNSR0IArs4c6QAAAPhJREFUOBHlU70KgzAQPlMhEvoQTg6OPoOjT+JWOnRqkUKHgqWP4OQbOPokTk6OTkVULNSLVc62oJmbIdzd95NcuGjX2/3YVI/Ts+t0WLE2ut5xsQ0O+90F6UxFjAI8qNcEGONia08e6MNONYwCS7EQAizLmtGUDEzTBNd1fxsYhjEBnHPQNG3KKTYV34F8ec/zwHEciOMYyrIE3/ehKAqIoggo9inGXKmFXwbyBkmSQJqmUNe15IRhCG3byphitm1/eUzDM4qR0TTNjEixGdAnSi3keS5vSk2UDKqqgizLqB4YzvassiKhGtZ/jDMtLOnHz7TE+yf8BaDZXA509yeBAAAAAElFTkSuQmCC&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;"
			 >
		</div>
		<div class="error-msg">
			<div class="error">
				${error}
			</div>
		</div>
		<input class="btn"  type="submit" name="登录" value="登录">
	</div>
</form>

</body>

</html>
<script>
	console.log(top.location.href)
	console.log(window.location.href)
	if(top.location.href !== window.location.href) top.location.href = window.location.href
</script>