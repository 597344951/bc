<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";

    String code = (String)request.getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>支付结果通知</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <style>
        #top_layer {
            width: 65%;
            margin: 0px auto;
            text-align: center;
        }
    </style>
</head>
<body>
    <div id="app">
        <div id="top_layer">
            支付结果：{{code}}
        </div>
    </div>
</body>

<script>
    var appInstince = new Vue({
        el: '#app',
        data: {
            code: '<%=code%>'
        },
        created: function () {
            
        },
        mounted:function () {
            
        },
        methods: {

        }
    });
</script>

</html>