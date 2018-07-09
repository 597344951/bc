<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事件追踪</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>

<%@include file="/include/mock.jsp"%>
</head>

<body>
    <div id="app" class="height_full">
        <pubed-plan-info></pubed-plan-info>
    </div>
</body>
    
<script type="module">
    var ins = new Vue({
        el: '#app',
            data: { 
        },	
        methods: { 
        }
    });
    </script>
    
    </html>