<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <title>...</title>
    <style>
        body {
            background: url("http://192.168.1.8:3000/images/ee5/962/ee5962fee4bc6600dc2b2ce454ede6ff.jpg");
            background-size: 100% 100%;
        }
        .content {
            background: whitesmoke;
            position: absolute;
            left: 15%;
            top: 20%;
            height: 70%;
            width: 70%;
            box-shadow: 0 0 5px 5px rgba(102, 99, 99, 0.5);
            border-radius: 50px;
            opacity: 1; 
            overflow: hidden;
        }
        .title {
            font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
            font-size: 80px;
            text-align: center;
            font-weight: bold;
        }
    </style>
<body>
    <div class="title">
        <!-- ${title} -->
    </div>
    <div class="content">
        <iframe id="wrap" frameborder="0" scrolling="no" height="100%" width="100%" src=""></iframe>
    </div>
</body>
<script>
    let wrapJsonStr = '${wrapJsonStr}'
    window.onload = () => {
        document.getElementById('wrap').src = '/pw/prePreview?wrapJsonStr=' + encodeURIComponent(wrapJsonStr)
    }
</script>
</html>