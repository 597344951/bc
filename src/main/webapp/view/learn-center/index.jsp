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

    <title>学习中心</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <link href="${urls.getForLookupPath('/assets/module/learn-center/style.css')}" rel="stylesheet">

</head>

<body>
    <div class="app-main" id="app" v-cloak>
        <div class="header">
            <div class="logo"><span>课程中心</span></div>
            <div class="search">
                <input v-model="keyword" type="text" placeholder="">
                <button @click="loadLessons">搜索</button>
            </div>
            <div class="account">

            </div>
        </div>
        <div role="nav">
            <div class="level">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/' }">所有课程</el-breadcrumb-item>
                    <template v-for="bp in  categoryBreadPath">
                        <el-breadcrumb-item :to="{ path: '/category/'+bp.categoryId}">{{bp.name}}</el-breadcrumb-item>
                    </template>
                </el-breadcrumb>
            </div>
            <div class="all">{{labelDisName}}：
                <template v-for="ps in nextCategory">
                    <a href="#" class="active"></a>
                    <router-link :to="{ path: '/category/'+ps.categoryId}">{{ps.name}}</router-link></a>
                </template>
            </div>
        </div>

        <div class="contain">
            <template v-for="lesson in lessons">
                <a class="item" @click="openLessonUnit(lesson)">
                    <div class="cover">
                        <img src="view/learn-center/img/01.png" alt="">
                        <p>总共 {{lesson.lessonList.length}} 小节 , <span>总时长：
                                {{sumTotalTime(lesson)}} 分钟</span></p>
                    </div>
                    <p class="title">{{lesson.name}}</p>
                    <div class="pandect">
                        <span class="student">学习限制:
                            <template v-if="lesson.learnerLimit == 0">
                                <i class="fa fa-unlock margin-left-10"></i> 无限制
                            </template>
                            <template v-if="lesson.learnerLimit == 1">
                                <i class="fa fa-lock margin-left-10 "></i> 限定组织
                            </template>
                        </span>
                    </div>
                    <div class="pandect">
                        <span class="student">创建时间: {{lesson.addTime | date}}</span>
                        <span class="teacher">讲师：吴景明</span>
                    </div>
                </a>
            </template>
        </div>
        <div role="pager">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="pager.pageNum" :page-sizes="[15, 30, 60, 100]" :page-size="pager.pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="pager.total">
            </el-pagination>
        </div>

        <!--dialog-->
        <el-dialog :title="currentLesson.name" :fullscreen="false" width="90%" :visible.sync="lessonViewDialog.visible">
            <div>
                <div class="detail-box">
                    <div class="detail">
                        <div class="screen">
                            <img src="view/learn-center/img/01.png" alt="">
                        </div>
                        <div class="information">
                            <p class="title">{{currentLesson.name}}</p>
                            <div class="info">
                                <span>创建时间: {{currentLesson.addTime | date}}</span> <span>课程节数：{{currentLesson.lessonList.length}}
                                    小节</span> <span>课程时长：
                                    {{sumTotalTime(currentLesson)}} 分钟</span>
                            </div>
                            <div class="info">
                                <span>考试资格要求:
                                    <template v-if="currentLesson.testThreshold == 0">
                                        无限制
                                    </template>
                                    <template v-if="currentLesson.testThreshold == 1">
                                        学时超过 {{sumTotalTime(currentLesson) * currentLesson.creditHoursPercent/100}}
                                        分钟
                                        ({{currentLesson.creditHoursPercent}}%)
                                    </template>
                                </span>
                            </div>
                            <div class="join-box">
                                <template v-if="currentLesson.lessonRegistration.length == 0">
                                    <a class="join" @click="joinLessonUnit(currentLesson)">立即报名</a>
                                </template>
                                <template v-if="currentLesson.lessonRegistration.length > 0">
                                    <a class="consult" :href="'/lesson/unit/unit/play/'+currentLesson.lessonUnitId"
                                        target="_blank">立即学习</a>
                                </template>

                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <el-tabs v-model="activeTabName">
                        <el-tab-pane label="课程概述" name="descript">
                            <div v-html="currentLesson.descript"></div>
                        </el-tab-pane>
                        <el-tab-pane label="课程目录" name="lessonMenu">
                            <div class="catalog">
                                <div class="list">
                                    <p class="list-head">课程目录</p>
                                    <!--
                                    <div class="list-content">
                                        <p class="list-menu">01 习近平新时代中国特色社会主义的内涵</p>
                                        <ol class="list-item">
                                            <li><a href="#">习近平新时代中国特色社会主义的内涵（第一节）</a><span>(2018年12月1日)</span></li>
                                            <li><a href="#">习近平新时代中国特色社会主义的内涵（第二节）</a><span>(2018年12月1日)</span></li>
                                        </ol>
                                    </div>
                                    -->
                                    <template v-for="(node,$index1) in  currentLesson.lessonTree">
                                        <div class="list-content">
                                            <p class="list-menu">{{$index1+1}} {{node.name}}</p>
                                            <ol class="list-item">
                                                <template v-for="(ls,$index2) in  node.children">
                                                    <li>
                                                        <el-popover placement="right-start" width="200" trigger="hover">
                                                            <div v-html="ls.descript"></div>
                                                            <a :href="'/lesson/unit/unit/play/'+ls.lessonUnitId+'?lessonId='+ls.lessonId"
                                                                slot="reference" target="_blank">{{$index1+1}}.{{$index2+1}}
                                                                {{ls.name}}</a>
                                                        </el-popover>
                                                        <span>({{ls.addTime|datetime}})</span>
                                                        <span>{{getLearnProgress(ls,currentLesson.lessonProgress)}}</span>
                                                    </li>
                                                </template>
                                            </ol>
                                        </div>
                                    </template>
                                </div>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="考核规则" name="second">
                            <div>
                                <p>下列任意考核方式</p>
                                <template v-for="(node,$index1)  in currentLesson.testTypes">
                                    <div class="list-content">
                                        <p class="list-menu">{{$index1+1}} {{node.name}}</p>
                                        <ol class="list-item">
                                            <div >
                                                <p v-for="str in node.descript.split('\n')">
                                                    {{str}}
                                                </p>
                                            </div>
                                        </ol>
                                    </div>
                                </template>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </el-dialog>
        <!--dialog-->
    </div>
    <!--app end-->

</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/learn-center/index.js')}"></script>