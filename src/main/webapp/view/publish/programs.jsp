<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>待编辑/审核发布内容</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <style>
       
    </style>
</head>
<body>
    <div id="app">
        <program-manage @publish="publish"></program-manage>
    </div>
    <script type="module">
        const app = new Vue({
            el: "#app",
            methods: {
                publish(program) {
                    let url = '/view/publish/new.jsp?title=' + program.name + '&programId=' + program.programId + '&reAdd=true&startStep=2'
                    if(parent.addTab) {
						parent.addTab({menuId: 28, name: '内容制作', url: url})
					} else {
						window.location.href = url
					}
                }
            }    
        })
    </script>
</body>
</html>