<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://cdn.bootcss.com/material-design-icons/3.0.1/iconfont/material-icons.css">
    <link href="https://aliyun.beecdn.cn/libs/animate.css/3.7.0/animate.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <title>VideoPlayer..</title>
    <script src="https://aliyun.beecdn.cn/libs/vue/2.5.17/vue.js"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <script src="/components/xx-components.js"></script>
</head>

<body style="background-color: #000;">
    <noscript>
        <strong>We're sorry but hello-world doesn't work properly without JavaScript enabled. Please enable it to
            continue.</strong>
    </noscript>
    <div id="app" style="background-color: #000; height: 100%; width: 99.8%; overflow: hidden; position: absolute; top: 0; left: 0.1%;">
        <multi-video-player :parts="parts" enter="animated slideInLeft" leave="animated slideOutUp" loop autoplay></multi-video-player>
    </div>
</body>
<script>
    const app = new Vue({
        el: '#app',
        data: {
            parts: [
                <c:forEach items="${photos}" var="photo">
                    '<c:out value="${photo}"/>',
		        </c:forEach >
            ]
        }
    })
</script>

</html>