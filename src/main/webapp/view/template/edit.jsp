<%--
  Created by IntelliJ IDEA.
  User: Touss
  Date: 2018/5/16
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>模板编辑</title>
    <%@include file="/include/bootstrap.jsp"%>
    <%@include file="/include/ueditor.jsp"%>
    <style type="text/css">
        body {
            margin: 10px;
        }
        #style {
            height: 300px;
        }
        #editor {
            height: 500px;
        }
        #load {
            color: #00a2d4;
            float: right;
            cursor: pointer;
            font-weight: 900;
            margin-right: 20px;
            font-size: 15px;
        }

    </style>
    <style type="text/css" id="customize">

    </style>
</head>


<body>
    <h5>自定义样式<span id="load">加载样式</span></h5>
    <div id="style">

    </div>
    <h5>HTML编辑</h5>
    <div id="editor">

    </div>

    <script>
        var style = UE.getEditor('style', {
            allowDivTransToP: false,
            toolbars: [],
            wordCount: false,
            tabSize: 2,
            elementPathEnabled: false,
            autoHeightEnabled: false

        });
        style.ready(() => {
            style.addListener('contentChange', () => {
                loadStyle();
            });
        });
        var editor = UE.getEditor('editor', {
            allowDivTransToP: false
            //autoHeightEnabled: false
        });

        $('#load').click(() => {
            loadStyle();
        });

        function loadStyle() {
            var css = style.getContentTxt();
            var iframe = document.getElementById('ueditor_1');
            var head = iframe.contentWindow.document.getElementsByTagName('head')[0];
            var customize = head.querySelector('style#customize');
            if(customize) {
                customize.innerHTML = css;
            } else {
                var newStyle = document.createElement('style');
                newStyle.id = 'customize';
                newStyle.innerHTML = css;
                head.appendChild(newStyle)
            }
        }
    </script>
</body>
</html>
