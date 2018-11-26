<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事件管理</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>
<link href="${urls.getForLookupPath('/view/calendar/calendar.css')}" rel="stylesheet" charset="utf-8">

<%@include file="/include/mock.jsp"%>
<%@include file="/include/map-baidu.jsp"%>
<%@include file="/include/vcharts.jsp"%>
<link href="${urls.getForLookupPath('/assets/module/eventplan/style.css')}"  rel="stylesheet" charset="utf-8">
</head>

<body>
    <div id="app" v-cloak>
        <el-row style="display:flex;">
            <el-col :span="4" style="min-width:260px;">
                <div class="plane-chose">
                    <h2>计划日历</h2>
                </div>
                <div>
                    <vue-calendar :mark-date="markDate" @changemonth="changeMonth" @choseday="choseDay"></vue-calendar>
                </div>
                <div class="plane-chose">
                    <el-button type="primary" @click="planGuide.visiable = true">宏观信息</el-button>
                </div>
                <div class="plane-chose">
                    <h3>展示计划类型</h3>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.froms[0]" true-label="system" @change="event_filter_change">系统内置计划</el-checkbox>
                    </div>

                    <div class="item">
                        <el-checkbox v-model="event_filter_form.froms[1]" true-label="user" @change="event_filter_change">用户计划</el-checkbox>
                    </div>
                </div>
                <div class="plane-chose">
                    <h3>计划优先级</h3>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.prioritys[0]" true-label="1" @change="event_filter_change">
                            <el-tooltip class="item" effect="dark" content="紧急事件" placement="right">
                                <span class="emergency-level-label">1</span>
                            </el-tooltip>
                        </el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.prioritys[1]" true-label="2" @change="event_filter_change">
                            <el-tooltip class="item" effect="dark" content="高优先等级" placement="right">
                                <span class="high-level-label">2</span>
                            </el-tooltip>
                        </el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.prioritys[2]" true-label="3" @change="event_filter_change">
                            <el-tooltip class="item" effect="dark" content="普通优先等级" placement="right">
                                <span class="normal-level-label">3</span>
                            </el-tooltip>
                        </el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.prioritys[3]" true-label="4" @change="event_filter_change">
                            <el-tooltip class="item" effect="dark" content="低优先等级" placement="right">
                                <span class="low-level-label">4</span>
                            </el-tooltip>
                        </el-checkbox>
                    </div>
                </div>
                <div class="plane-chose">
                    <h3>计划状态</h3>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.statuss[0]" true-label="0" @change="event_filter_change">可创建</el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.statuss[1]" true-label="1" @change="event_filter_change">正在公示</el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.statuss[2]" true-label="2" @change="event_filter_change">正在进行</el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.statuss[3]" true-label="3" @change="event_filter_change">已结束</el-checkbox>
                    </div>
                    <div class="item">
                        <el-checkbox v-model="event_filter_form.statuss[4]" true-label="-1" @change="event_filter_change">已关闭</el-checkbox>
                    </div>
                </div>
            </el-col>
            <el-col :span="20">
                <el-tabs v-model="activeMonth">
                    <el-tab-pane v-for="mp in monthPlans" :label="mp.title" :name="mp.title">
                        <div>
                            <div class="festival-container">
                                <h3>{{mp.title}}</h3>
                                <template v-for=" eventdata in mp.datas">
                                    <div class="theme-festival theme-jdj">
                                        <div class="shadow">
                                            <span class="festival-title">
                                                <span :class="getPriorityClass(eventdata)">{{eventdata.priority?eventdata.priority:3}}</span>
                                                {{eventdata.title}} ({{eventdata.stime | date}})
                                                <span style="margin-left:30px;">{{eventdata.source=='system'?'系统内置计划':'用户计划'}}
                                                </span>
                                            </span>
                                            <div class="main">
                                                <div class="suggest-type">
                                                    <span>主推活动</span>
                                                </div>
                                                <div class="main-content">
                                                    <template v-for="plan in eventdata.suggestItems.filter(item=>item.type==1)">
                                                        <div class="box" :class="getPlanTheme(plan)">
                                                            <div class="box-up">
                                                                <h3>
                                                                    <i></i>{{plan.suggestInfo.title}}
                                                                    <el-button class="btn" type="primary" icon="el-icon-edit" circle @click="showAddActivityPlan(eventdata,plan)"> </el-button>
                                                                </h3>
                                                                <p>
                                                                    <i class="fa fa-users"></i>
                                                                    <span class="title">参与人数: </span>
                                                                    <span class="text">32人</span>
                                                                </p>
                                                                <p>
                                                                    <i class="fa fa-jpy"></i>
                                                                    <span class="title">活动预算: </span>
                                                                    <span class="text">1000元</span>
                                                                </p>
                                                                <p>
                                                                    <i class="fa fa-line-chart"></i>
                                                                    <span class="title">推荐指数: </span>
                                                                    <span class="text">80</span>
                                                                </p>
                                                                <p>
                                                                    <i class="fa fa-user-circle-o"></i>
                                                                    <span class="title">策划人员: </span>
                                                                    <span class="text">develop</span>
                                                                </p>

                                                            </div>
                                                            <div class="box-down">
                                                                <p>浙江省杭州市党委</p>
                                                                <p>余杭区仓前街道党支部</p>
                                                                <p></p>
                                                            </div>
                                                        </div>
                                                    </template>
                                                </div>
                                            </div>
                                            <div class="main">
                                                <div class="suggest-type">
                                                    <span>辅推活动</span>
                                                </div>
                                                <div class="main-content">
                                                    <template v-for=" plan in eventdata.suggestItems.filter(item=>item.type==2)">
                                                        <div class="box" :class="getPlanTheme(plan)" @click="showAddActivityPlan(eventdata,plan)">
                                                            <div class="box-up">
                                                                <h3>
                                                                    <i></i>{{plan.suggestInfo.title}}
                                                                    <el-button class="btn" type="primary" icon="el-icon-edit" circle @click="showAddActivityPlan">
                                                                    </el-button>
                                                                </h3>
                                                                <p>
                                                                    <i></i>
                                                                    <span class="title">参与人数: </span>
                                                                    <span class="text">32人</span>
                                                                </p>
                                                                <p>
                                                                    <i></i>
                                                                    <span class="title">活动预算: </span>
                                                                    <span class="text">1000元</span>
                                                                </p>
                                                                <p>
                                                                    <i></i>
                                                                    <span class="title">推荐指数: </span>
                                                                    <span class="text">80</span>
                                                                </p>
                                                                <p>
                                                                    <i></i>
                                                                    <span class="title">策划人员: </span>
                                                                    <span class="text">develop</span>
                                                                </p>

                                                            </div>
                                                            <div class="box-down">
                                                                <p>浙江省杭州市党委</p>
                                                                <p>余杭区仓前街道党支部</p>
                                                                <p></p>
                                                            </div>
                                                        </div>
                                                    </template>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </div>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </el-col>
        </el-row>

        <!--dialog-->
        <el-dialog title="新增计划" :visible.sync="addEventDialog.visiable">
            <add-event :add-event-dialog="addEventDialog" @added-data="addEventCallback"></add-event>
        </el-dialog>
        <el-dialog title="新建活动策划" :visible.sync="createActivityPlanDialog.visiable" :fullscreen="true" :modal="false">
            <create-activity-plan ref="activityPlan" :time-info="eventdata" :plan="sugPlan" @submit="addActivityPlan"></create-activity-plan>
        </el-dialog>
        <el-dialog custom-class="no-title" :visible.sync="planGuide.visiable" :fullscreen="true">
            <load-report @save="createPlanGuidSave"></load-report>
        </el-dialog>
        <!--dialog-->
    </div>
</body>
<script type="module" src="${urls.getForLookupPath('/assets/module/eventplan/eventplan.js')}"></script>

</html>