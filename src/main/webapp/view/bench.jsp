<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>工作台</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <%@include file="/include/vcharts.jsp" %>
    <style>
        #app {
            min-width: 1150px;
        }
        .el-row {
            margin-top: 10px;
        }
        .el-tabs__item {
            font-weight: bold;
            font-size: 15px;
        }
        .el-badge__content.is-fixed {
            top: 10px;
        }
        .el-card__header {
            font-weight: bold;
            font-size: 15px;
        }
        .right {
            float: right;
        }

        a:hover {
            text-decoration: underline;
        }

        .list-item {
            font-size: 14px;
            margin: 15px 5px;
            display: block;
            text-decoration: none;
        }
        .list-item-title {
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
            display: inline-block;
            width: 60%;
        }
        .pie-chart-3 {
            height: 200px;
            width: 35%;
            float: left;
        }
        .pie-chart-5 {
            height: 200px;
            width: 55%;
            float: left;
        }
        .line-chart-3 {
            height: 200px;
            width: 35%;
            float: left;
        }
        .activity-item {
            height: 90%;
            padding: 5px;
            border-right: 1px solid #ebeef5;
        }
        .activity-item h5 {
            font-size: 13px;
            margin: 5px 0px;
        }
        .activity-item p {
            font-size: 13px;
            line-height: 20px;
        }
        .shortcuts {
            width: 100%;
            height: 100%;
            overflow: hidden;
            float: left;
        }
        .shortcut {
            color: #51c9d4;
            width: 100px;
            text-align: center;
            margin: 5px 0px;
            display: inline-block;
            cursor: pointer;
        }
        .shortcut:hover {
            color: #1175ff;
        }
        .shortcut-icon {
            font-size: 50px;
            display: block;
        }
        .shortcut-label {
            font-size: 12px;
            font-weight: bold;
        }
        .party-change-list {
            width: 30%;
            float: right;
        }
        .archive-change-list {
            width: 45%;
            float: right;
        }
    </style>
</head>
<body>
<div id="app">
    <el-row :gutter="10">
        <%--待办待审--%>
        <el-col :span="8">
            <el-card class="box-card" shadow="never" :body-style="{height: '308px', padding: '15px'}">
                <el-tabs v-model="pending.active">
                    <el-tab-pane name="handle">
                        <template slot="label">
                            <el-badge :value="pending.handling.count" class="item">待办理事项</el-badge>
                        </template>
                        <a v-for="l in pending.handling.list" class="list-item" :href="l.href" :title="l.title"><span class="el-icon-warning list-item-title">&nbsp;&nbsp;{{l.title}}</span><span class="right">{{l.date}}</span></a>
                    </el-tab-pane>
                    <el-tab-pane name="verify">
                        <template slot="label">
                            <el-badge :value="pending.verifying.count" class="item">带审核内容</el-badge>
                        </template>
                        <a v-for="l in pending.verifying.list" class="list-item" :href="l.href" :title="l.title"><span class="el-icon-warning list-item-title">&nbsp;&nbsp;{{l.title}}</span><span class="right">{{l.date}}</span></a>
                    </el-tab-pane>
                </el-tabs>
            </el-card>
        </el-col>
        <%--通告--%>
        <el-col :span="8">
            <el-card class="box-card" shadow="never" :body-style="{height: '250px', padding: '15px'}">
                <div slot="header" class="clearfix">
                    <span>通知告示</span>
                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                </div>
                <a v-for="l in notice" class="list-item" :href="l.href" :title="l.title"><span class="el-icon-caret-right list-item-title">&nbsp;&nbsp;{{l.title}}</span><span class="right">{{l.date}}</span></a>
            </el-card>
        </el-col>

        <el-col :span="8">
            <el-card class="box-card" shadow="never" :body-style="{height: '250px', padding: '15px'}">
                <div slot="header" class="clearfix">
                    <span>正在播放内容</span>
                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                </div>
                <a v-for="l in notice" class="list-item" :href="l.href" :title="l.title"><span class="el-icon-caret-right list-item-title">&nbsp;&nbsp;{{l.title}}</span><span class="right">{{l.date}}</span></a>
            </el-card>
        </el-col>
    </el-row>
    <el-row :gutter="10">
        <el-col :span="16">
            <el-card class="box-card" shadow="never" :body-style="{height: '250px', padding: '15px'}">
                <div slot="header" class="clearfix">
                    <span>党员发展</span>
                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                </div>
                <div class="pie-chart-3">
                    <ve-pie :data="partyMembers.constitute" :settings="pieChartSettings"></ve-pie>
                </div>
                <div class="line-chart-3">
                    <ve-line :data="partyMembers.trend" width="300px" height="250px"></ve-line>
                </div>
                <div class="party-change-list">
                    <a v-for="l in partyMembers.items" class="list-item" :href="l.href" :title="l.title"><span class="el-icon-caret-right list-item-title">&nbsp;&nbsp;{{l.title}}</span><span class="right">{{l.date}}</span></a>
                </div>
            </el-card>
        </el-col>
        <el-col :span="8">
            <el-card class="box-card" shadow="never" :body-style="{height: '250px', padding: '15px'}">
                <div slot="header" class="clearfix">
                    <span>便捷入口</span>
                </div>
                <div class="shortcuts">
                    <div class="shortcut">
                        <span class="el-icon-setting shortcut-icon"></span>
                        <span class="shortcut-label">快速创建欢迎节目</span>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row :gutter="10">
        <el-col :span="12">
            <el-card class="box-card" shadow="never" :body-style="{height: '200px', padding: '15px'}">
                <div slot="header" class="clearfix">
                    <span>近期活动</span>
                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                </div>
                <el-row :gutter="20">
                    <el-col :span="8" v-for="l in activity">
                        <div class="activity-item">
                            <h5>主题：{{l.title}}</h5>
                            <h5>时间：{{l.date}}</h5>
                            <h5>状态：{{l.state}}</h5>
                            <h5>参与人员（应到/实到）：{{l.replied}} / {{l.really}}</h5>
                            <p><strong>总结：</strong>{{l.summary}}</p>
                        </div>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="12">
            <el-card class="box-card" shadow="never" :body-style="{height: '200px', padding: '15px'}">
                <div slot="header" class="clearfix">
                    <span>近期文档成果</span>
                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                </div>
                <div class="pie-chart-5">
                    <ve-pie :data="archive.added" :settings="pieChartSettings"></ve-pie>
                </div>
                <div class="archive-change-list">
                    <a v-for="l in archive.items" class="list-item" :href="l.href" :title="l.title"><span class="el-icon-caret-right list-item-title">&nbsp;&nbsp;{{l.title}}</span><span class="right">{{l.date}}</span></a>
                </div>
            </el-card>
        </el-col>
    </el-row>
</div>
<script>
    const app = new Vue({
        el: '#app',
        data: {
            pieChartSettings: {
                roseType: 'radius',
                radius: 70,
                offsetY: 120
            },
            pending: {
                active: 'handle',
                handling: {
                    count: 2,
                    list: []
                },
                verifying: {
                    count: 2,
                    list: []
                }
            },
            notice: [
                {date: '2018-05-30', title: '本日下午2点召开党员生活会...', href: '#' },
                {date: '2018-05-30', title: '本日下午2点召开党员生活会...', href: '#' },
                {date: '2018-05-30', title: '本日下午2点召开党员生活会...', href: '#' },
                {date: '2018-05-30', title: '本日下午2点召开党员生活会...', href: '#' },
                {date: '2018-05-30', title: '本日下午2点召开党员生活会...', href: '#' },
            ],
            playlist: [],
            partyMembers: {
                constitute: {
                    columns: ['类型', '成员数'],
                    rows: [
                        {'类型': '党员', '成员数': 50},
                        {'类型': '预备党员', '成员数': 15},
                        {'类型': '积极分子', '成员数': 30}
                    ]
                },
                trend: {
                    columns: ['日期', '党员', '预备党员', '积极份子'],
                    rows: [
                        {'日期': '1月', '党员': 40, '预备党员': 10, '积极份子': 20},
                        {'日期': '2月', '党员': 45, '预备党员': 15, '积极份子': 25},
                        {'日期': '3月', '党员': 42, '预备党员': 12, '积极份子': 26},
                        {'日期': '4月', '党员': 47, '预备党员': 13, '积极份子': 28},
                        {'日期': '5月', '党员': 20, '预备党员': 15, '积极份子': 30},
                    ]
                },
                items: [
                    { date: '2018-05-30', title: 'XXXX通过审核转为正式党员。。。', href: '#' },
                    { date: '2018-05-30', title: 'XXXX通过审核转为正式党员。。。', href: '#' },
                    { date: '2018-05-30', title: 'XXXX通过审核转为正式党员。。。', href: '#' },
                    { date: '2018-05-30', title: 'XXXX通过审核转为正式党员。。。', href: '#' }
                ]
            },
            activity: [
                {title: '欢度五一', date: '2018-05-01', state: '已结束', replied: 100, really: 100, summary: '五一劳动节, 劳动我光荣（放假了，放假了，放假了，放假了，放假了，放假了 。。。）' },
                {title: '欢度五一', date: '2018-05-01', state: '已结束', replied: 100, really: 100, summary: '' },
            ],
            archive: {
                added: {
                    columns: ['类型', '新增数'],
                    rows: [
                        {'类型': '制度规范', '新增数': 50},
                        {'类型': '工作计划', '新增数': 15},
                        {'类型': '工作总结', '新增数': 30},
                        {'类型': '学习心得', '新增数': 30},
                        {'类型': '思想汇报', '新增数': 30}
                    ]
                },
                items: [
                    { date: '2018-05-30', title: 'XXXX提交了学习心得。。。', href: '#' },
                    { date: '2018-05-30', title: 'XXXX提交了学习心得。。。', href: '#' },
                    { date: '2018-05-30', title: 'XXXX提交了学习心得。。。', href: '#' },
                    { date: '2018-05-30', title: 'XXXX提交了学习心得。。。', href: '#' }
                ]
            }

        },
        methods: {
            loadPending() {
                this.pending.handling.list = [{
                    date: '2018-05-30',
                    title: '你有一个待办事项。。。。。。。。。。。。。。。。。。。。。。。。',
                    href: '#'
                }, {
                    date: '2018-05-30',
                    title: '你有一个待办事项。。。',
                    href: '#'
                }]

                this.pending.verifying.list = [{
                    date: '2018-05-30',
                    title: '你有一个待审核事项。。。',
                    href: '#'
                }, {
                    date: '2018-05-30',
                    title: '你有一个待审核事项。。。',
                    href: '#'
                }]
            }
        }
    })

    app.loadPending();
</script>
</body>
</html>
