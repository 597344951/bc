<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <title>学习</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <script>
        window.resourceData = ${data}
    </script>
    <style>
        .container{
            width:80%;
            padding: 10px;
        }
    </style>
</head>

<body>
    <div class="container" id="app" v-cloak>
        <h1>{{resourceData.name}}</h1>
        <template v-if="resourceData.type == 'text'">
            <div v-html="resourceData.content"> </div>
        </template>
        <template v-if="resourceData.type == 'video'">
            <video :src="getResUrl(resourceData.url)" controls="controls" class="videoView"></video>
        </template>
        
    </div>
</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/learn-center/view.js')}"></script>