<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>活动剪影预览</title>
    <%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
    <script src="${urls.getForLookupPath('/js/jquery.transform.min.js')}"></script>
    <script src="https://cdn.bootcss.com/jquery-easing/1.4.1/jquery.easing.min.js"></script>
    <script src="${urls.getForLookupPath('/js/jquery.masonry.min.js')}"></script>
    <script src="${urls.getForLookupPath('/components/xx-components.js')}"></script>
    <style>
    #app{
        background-color: #f5f5f5;
    }
        .txt{
            text-align:center;
            height: 100px!important;
        }
        .el-main{
            position: relative;
            height: 800px;
        }
        .content{
            top: 0% !important;
            left: 2% !important;
            width: 93% !important;
            height: 74% !important;
            margin: 10px auto 20px;
            box-shadow: 0 2px 20px 2px #ddd;
            padding:20px;
            background-color: #fff;
        }
        .content2{
            top: 0% !important;
            left: 2% !important;
            width: 96% !important;
            height: 88% !important;
            margin: 0px auto 20px;
            box-shadow: 0 2px 20px 2px #ddd;
            padding: 20px;
            background-color: #fff;
        }

        #titleEffect .letter{
            color:#666!important;
        }
    </style>
</head>

<body>
    <div id="app">
        <el-container>
            <el-header class="txt">
                <text-effect id="title" :text="silhouette.title" type="hop"></text-effect>
            </el-header>
            <el-main>
                <photo-wall v-if="wall1"  class="content" id="wall" :photos="silhouette.photos"></photo-wall>
                <photo-wall2 v-if="!wall1"  class="content2" id="wall2" :photos="silhouette.photos"></photo-wall>
            </el-main>
        </el-container>
    </div>
</body>
<script type="module">
    import serverConfig from '/environment/resourceUploadConfig.jsp'

     window.app = new Vue({
        el: '#app',
        data: {
            wall1: true,
            silhouette: {
                title: '',
                photos: [{
                    src: '/assets/img/loading.gif',
                    alt: 'loading'
                }]
            }
        },
        mounted() {
            this.loadSilhouette()
        },
        methods: {
           loadSilhouette() {
               let url = '/publish/activity/silhouette/<%=id%>'
               get(url, reps => {
                   if(reps.status) {
                       this.silhouette.title = reps.data.title
                       if(reps.data.materials.length > 20) {
                           this.wall1 = false
                       } else {
                           this.wall1 = true
                       }
                       this.silhouette.photos = reps.data.materials.map(material => {
                           return {src: serverConfig.getUrl(material.url), alt: ''}
                       })
                   } else {
                       this.$message.error(reps.msg)
                   }
               })
           }
        }
    })

    function get(url, callback) {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            success: function (reps) {
                if (callback) callback(reps)
            },
            error: function (err) {
                app.$message.error('系统错误')
            }
        });
    }
    function postJson(url, postData, callback) {
        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            data: JSON.stringify(postData),
            contentType: 'application/json',
            success: function (reps) {
                if (callback) callback(reps)
            },
            error: function (err) {
                app.$message.error('系统错误')
            }
        });
    }
</script>

</html>