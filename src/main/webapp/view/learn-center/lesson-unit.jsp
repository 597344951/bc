<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <title>${lessonUnit.name}</title>
    <%@include file="/include/head.jsp"%>
    <link href="${urls.getForLookupPath('/assets/module/learn-center/style.css')}" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.bootcss.com/material-design-icons/3.0.1/iconfont/material-icons.css">

    <script>
        window.progress = ${currentProgressJson}
        window.lesson = ${currentLessonJson}
        window.rmJson = ${rmJson != null ? rmJson : 'null'}
    </script>
</head>

<body>
    <div id="app">
        <div class="top">
            <el-row>
                <el-col :span="12">
                    <span class="cal">
                        <el-popover placement="top-start" width="400" trigger="hover">
                            <div class="catalog-list">
                                <c:forEach items="${lessonUnit.lessonTree}" var="lu" varStatus="status1">
                                    <div class="list-content">
                                        <p class="list-menu">${status1.index+1} ${lu.name}</p>
                                        <ol class="list-item">
                                            <c:forEach items="${lu.children}" var="lesson" varStatus="status2">
                                                <li class="${currentLesson.lessonId == lesson.lessonId ? 'active':''}">
                                                    <a href="/lesson/unit/unit/play/${lu.lessonUnitId}?lessonId=${lesson.lessonId}">${status1.index+1}.${status2.index+1}
                                                        ${lesson.name}</a>
                                                    <c:forEach items="${progress}" var="p">
                                                        <c:if test="${p.lessonId == lesson.lessonId}">
                                                            (学习进度：
                                                            <fmt:formatNumber value="${p.creditHours * 100 / lesson.creditHours}"
                                                                pattern="#0" />
                                                            %)
                                                        </c:if>
                                                    </c:forEach>
                                                </li>
                                            </c:forEach>
                                        </ol>
                                    </div>
                                </c:forEach>
                            </div>
                            <span slot="reference">
                                <span class="menu-icon"><i></i><i></i><i></i></span>
                                <span class="catalog">目录</span>
                            </span>
                        </el-popover>
                        <span class="title">${lessonUnit.name}</span>
                        <span class="title"> > </span>
                        <span class="title"> ${currentLesson.name} </span>
                </el-col>
                <el-col :span="12">
                    <el-row style="padding-top: 5px;">
                        <el-col :span="4" style="text-align: right;padding-right: 14px;box-sizing: border-box;">学时进度:</el-col>
                        <el-col :span="12" style="text-align: center;">
                            <el-progress :text-inside="true" :stroke-width="18" :percentage="Math.ceil(progress.creditHours * 100 / lesson.creditHours)"
                                status="success" style="width:90%;"></el-progress>
                            <span class="label-txt">{{progress.creditHours}} / {{lesson.creditHours}} 分钟</span>
                        </el-col>
                        <el-col :span="8">
                            下一次保存进度: {{tick}} 秒以后
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!--内部资源-->
        <div class="play">
            <c:if test="${currentLesson.sourceType == 1}">
                <div role="shadow" class="shadow" v-if="shadowOpen">
                    <!-- <strong>警告!</strong> 当前播放地址为为外链， 无法自动加载播放进度，请手动定位。
                        <br/>
                        <el-button type="primary" @click="otherResourceStart">确定</el-button> -->
                    <div class="alert-base alert-warning">
                        <strong>警告！</strong> 当前播放地址为为外链， 无法自动加载播放进度，请手动定位。
                        <el-button type="primary" @click="otherResourceStart">确定</el-button>
                    </div>
                   
                </div>
            </c:if>

            <c:if test="${currentLesson.sourceType == 0}">
                <c:if test="${rm.type == 'video' || rm.type == 'audio'}">
                    <!--
                    <video id="player" src="/media-server/url?url=${rm.url}" poster="/media-server/url?url=${rm.coverUrl}"
                        controls="controls">
                    </video>
                    -->
                    <!--封装的播放器-->
                    <multi-video-player ref="player" @play-state-change="onPlayStateChange" :seeker="progress.playProgress"
                        :parts="getDisplayParts()">
                    </multi-video-player>
                </c:if>
                <c:if test="${rm.type == 'image'}">
                    <img src="/media-server/url?url=${rm.url}">
                </c:if>
                <c:if test="${rm.type == 'text'}">
                    <div role="text-container">
                        ${rm.content}
                    </div>
                </c:if>
            </c:if>
            <!--外链-->
            <c:if test="${currentLesson.sourceType == 1}">
                ${currentLesson.sourceData}
            </c:if>
        </div>
    </div>
</body>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/learn-center/lesson-unit.js')}"></script>
<script src="${urls.getForLookupPath('/components/xx-components.js')}"></script>