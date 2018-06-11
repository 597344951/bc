<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>宣传教育</title>
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
            <h3>宣传教育</h3>
        </el-header>
        <el-mian class="main">
            <el-tabs tab-position="left">
                <el-tab-pane label='高层动态'>
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label='党建要闻'>
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="时政解读">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="党建百科">
                    <div v-for="l in content[0].list" class="content">
                        <h5><a href="#">{{l.title}}</a></h5>
                        <ul>
                            <li v-for="s in l.sub"><a href="#">{{s.title}}</a></li>
                        </ul>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="先进典型">
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
                    name: '',
                    label: '“两学一做”学习教育',
                    list:[{
                        title: '学习习近平总书记重要讲话精神',
                        sub: [{
                            title: '[文章] "不忘初心、继续前进"背后的真意'
                        }, {
                            title: '[文章] 加快构建中国特色哲学社会科学——贯彻学习习近平同志在哲学社会科学...'
                        }, {
                            title: '[文章 ] 学习习近平总书记“七一”重要讲话精神'
                        }, {
                            title: '[视频] 学习习近平总书记“七一”重要讲话精神'
                        }, {
                            title: '[视频] 学习习近平总书记系列重要讲话精神——治国理政篇'
                        }, {
                            title: '[视频] 学习习近平总书记关于全面推进依法治国的重要论述'
                        }, {
                            title: '[视频] 学习习近平总书记系列重要讲话精神'
                        }]
                    }, {
                        title: '学党章',
                        sub: [{
                            title: '[视频] “两学一做”学习教育的意义和总体要求'
                        }, {
                            title: '[视频] 学党章党规 学系列讲话 做合格党员'
                        }, {
                            title: '[视频] 认真学习党章 严格遵守党章——解读学习党章的重要性和必要性'
                        }]
                    }, {
                        title: '做合格党员',
                        sub: [{
                            title: '[视频] 不忘初心、做合格党员'
                        }, {
                            title: '[视频] 知行止、守纪律，做合格党员'
                        }, {
                            title: '[视频] “两学一做”学习教育的意义和总体要求'
                        }, {
                            title: '[视频] 学党章党规 学系列讲话 做合格党员'
                        }]
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
