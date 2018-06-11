<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>制度规范</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <%@include file="/include/bootstrap.jsp" %>
    <style>
        .main {
            padding: 20px;
            height: 90%;
        }
        .el-tabs__item {
            width: 200px;
            text-align: center;
            border-bottom: 1px solid #e4e7ed;
            margin-right: 10px;
            font-size: 18px;
            font-weight: bold;
        }
        .content {
            margin: 0 25px;
            border-bottom: 1px solid #e4e7ed;
        }
        .content h5 {
            font-weight: bold;
            margin: 20px 0;
        }
        .content li {
            margin: 10px 0;
        }
    </style>
</head>
<body>
<div id="app">
    <el-container>
        <el-header style="height: 10%">
            <h3>制度规范</h3>
        </el-header>
        <el-mian class="main">
            <el-tabs tab-position="left">
                <el-tab-pane label="党章">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="准则">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="条列">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="规则">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="规定">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="办法">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="细则">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </el-mian>
    </el-container>
</div>
<script>
    const app = new Vue({
        el: '#app',
        data: {
            content: [
                {
                    name: '党章',
                    label: '党章',
                    list:[{
                        title: '中国共产党章程（2017年修改）',
                        sub: [{
                            title: '中国共产党第十九次全国代表大会关于《中国共产党章程（修正案）》的决议'
                        }]
                    }, {
                        title: '中国共产党章程（2012年修改）',
                        sub: [{
                            title: '习近平：认真学习党章 严格遵守党章 学党章用党章系列文章'
                        }, {
                            title: '中国共产党第十八次全国代表大会关于《中国共产党章程(修正案)》的决议'
                        }, {
                            title: '党章知识 看案例学党章 党章历次修订概览 党章史话 党章背后的故事 党章知识竞赛试题'
                        }]
                    }, {
                        title: '中国共产党章程（2007年修改）',
                        sub: [{
                            title: '中国共产党第十七次全国代表大会关于《中国共产党章程（修正案）》的决议'
                        }]
                    }, {
                        title: '中国共产党章程（2002年修改）',
                        sub: []
                    }, {
                        title: '中国共产党章程（1997年修改）',
                        sub: []
                    }, {
                        title: '中国共产党章程（1992年修改）',
                        sub: []
                    }, {
                        title: '中国共产党章程（1987年修改）',
                        sub: []
                    }]
                }
            ]
        },
        methods: {

        }
    })
</script>
</body>
</html>
