<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <title>文字快闪</title>
    <script src="https://aliyun.beecdn.cn/libs/vue/2.5.17/vue.min.js"></script>
    <script src="https://aliyun.beecdn.cn/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="../../components/xx-components.js"></script>
</head>

<body>
    <div id="app">
        <flashing :words="playWords" delay="800" loop="true"></flashing>
    </div>
</body>
<script>
    const id = '${param.id}'
    const app = new Vue({
        el: '#app',
        data: {
            playWords: [],
        },
        mounted() {
            get("/flashing/preview/" + id, resp => {
                if (resp.status) {
                    this.playWords = resp.words
                }
            })
        },
        methods: {}
    })

    function get(url, callback, errorCallback) {
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: resp => {
                if (callback) callback(resp)
            },
            error: (jqXHR, textStatus, errorThrown) => {
                console.error(errorThrown)
                if (errorCallback) errorCallback(jqXHR, textStatus, errorThrown)
            }
        })
    }
</script>

</html>