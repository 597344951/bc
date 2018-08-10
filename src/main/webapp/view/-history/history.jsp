<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>历史记录</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>

<%@include file="/include/mock.jsp"%>
<link href="${urls.getForLookupPath('/view/-history/history.css')}" rel="stylesheet">
<style>
   
</style>
</head>

<body>
<div id="app">
    <div class="bg-box">
        <div class="content">
            <img src="/components/dang//images/content.png" alt="">
            <div class="ding">
                <img class="ding1" src="/components/dang/images/ding.png" alt="">
                <img class="ding2" src="/components/dang//images/ding.png" alt="">
            </div>
            <div class="left-box">
                <div class="main-content">
                    <template v-for="he in  historyEvent">
                        <div class="content-item">
                            <h1>{{he.title}}</h1>
                            <img :src="he.cover_img">
                            <p>{{he.descript}}</p>
                        </div>
                    </template>
                </div>
            </div>
            <div class="calender-box">
                <div class="date-box">
                    <div class="select">
                        <el-select v-model="choseYear" @change="dateTimeChange">
                            <el-option v-for="i in years" :key="i" :label="i" :value="i">{{i}}年</el-option>
                        </el-select>
                    </div>
                    <div class="select">
                        <el-select v-model="choseMonth" @change="dateTimeChange">
                            <el-option v-for="i in [1,2,3,4,5,6,7,8,9,10,11,12]" :key="i" :label="i" :value="i">{{i}}月</el-option>
                        </el-select>
                    </div>

                </div>
                <div class="date-list">
                    <vue-calendar ref="calendar" :mark-date="markDate" @choseday="choseday"></vue-calendar>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${urls.getForLookupPath('/view/-history/history.js')}"></script>
</html>