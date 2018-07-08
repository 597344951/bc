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
<style>

</style>
</head>

<body>
    <div id="app" v-cloak>
        <event-tooltip :data="eventData" @callback="toolTipHandleCallback"></event-tooltip>

        <el-row>
            <el-col :span="3" style="padding:5px;">
                <h1>计划日历</h1>
                <div class="card-date">
                    <div>{{nowDate}}</div>
                    <div>{{nowDay}}</div>
                </div>
                <div class="plane-chose">
                    <el-button type="primary" @click="createPlanGuide">生成活动</el-button>
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
                <div v-show="calendarDialog.visiable">
                    <event-calendar ref="calendar" :data="event_filter_form" @day-click="calendar_day_click" @event-click="calendar_event_click"
                        @month-change="calendar_month_change"></event-calendar>
                </div>
                <!--全屏表单-->
                <div id="newPlane" v-show="planEditorDialog.visiable">
                    <div>
                        <el-button type="primary" icon="el-icon-close" circle @click="caclePlane"></el-button>
                    </div>
                    <div>
                        <h3>{{planEditorDialog.title}}</h3>
                        <el-form ref="form" :model="planData" label-width="80px">
                            <el-form-item label="活动名称">
                                <el-col :span="5">
                                    <el-input v-model="planData.title"></el-input>
                                </el-col>
                            </el-form-item>
                            <el-form-item label="活动时间">
                                <el-col :span="5">
                                    <el-date-picker type="date" v-model="planData.stime" placeholder="活动开始日期" style="width: 100%;"></el-date-picker>
                                </el-col>
                                <el-col class="line" :span="1" style="text-align: center;">-</el-col>
                                <el-col :span="5">
                                    <el-date-picker type="date" v-model="planData.etime" placeholder="活动结束日期" style="width: 100%;"></el-date-picker>
                                </el-col>
                            </el-form-item>
                            <el-form-item label="活动类型">
                                <el-col :span="5">
                                    <el-input placeholder="请选择活动类型" v-model="planData.choseActivity.name"></el-input>
                                </el-col>
                                <el-col class="line" :span="1" style="text-align: center;"> &nbsp; </el-col>
                                <el-col :span="5">
                                    <el-button size="small " type="primary" @click="planSuggestdialog.visiable = true">选择活动</el-button>
                                </el-col>
                            </el-form-item>
                            <el-form-item label="策划模板">
                                <el-col :span="24">
                                    <el-radio @change="tpChoseChange" v-model="choseTemplate" :label="null" border>空模板</el-radio>
                                    <el-radio @change="tpChoseChange" v-for=" tp in planeTemplates" v-model="choseTemplate" :label="tp" :key="tp.id" border>{{tp.name}}</el-radio>
                                </el-col>
                            </el-form-item>
                            <el-form-item label="策划方案">
                                <el-col :span="24">
                                    <div id="editor">

                                    </div>
                                </el-col>
                            </el-form-item>
                            <el-form-item label="费用计划">
                                <el-row :gutter="20">
                                    <el-col :span="3">
                                        赞助金额:
                                        <el-tag type="success">{{sponsorship_fee}} 元</el-tag>
                                    </el-col>
                                    <el-col :span="3">
                                        支出金额:
                                        <el-tag type="warning">{{cost_fee}} 元</el-tag>
                                    </el-col>
                                    <el-col :span="3">
                                        总计结果:
                                        <el-tag type="info">{{total_fee}} 元</el-tag>
                                    </el-col>
                                    <el-col :span="3">
                                        <el-button size="small " type="primary" @click="costPlanDataDialog.visiable = true">更改计划</el-button>
                                    </el-col>
                                    <el-col :span="10" v-if="total_fee < 0">
                                        <p class="bg-warning" style="padding: 0 15;color: #f56c6c;">
                                            <i class="glyphicon glyphicon-warning-sign"></i>警告: 费用超过预算</p>
                                    </el-col>
                                </el-row>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="submitPlane">立即创建</el-button>
                                <el-button @click="caclePlane">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </div>

                </div>
                <!---->
            </el-col>
        </el-row>
        <!--dialog-->
        <el-dialog title="智能活动推荐" :visible.sync="planSuggestdialog.visiable">
            <plan-suggest :event-data="eventData" :visiable="planSuggestdialog.visiable" @change="planChoseChange"></plan-suggest-module>
        </el-dialog>
        <el-dialog title="新增计划" :visible.sync="addEventDialog.visiable">
            <add-event :add-event-dialog="addEventDialog" @added-data="addEventCallback"></add-event>
        </el-dialog>
        <el-dialog title="费用计划编辑" :visible.sync="costPlanDataDialog.visiable">
            <cost-plan :cost-plan-data="costPlanData" :cost-type-group="costTypeGroup" @complete="costPlanDataEditComplete"></cost-plan>
        </el-dialog>
         <el-dialog custom-class="no-title" :visible.sync="planGuide.visiable" :fullscreen="true" >
             <load-report @save="createPlanGuidSave"></load-report>
        </el-dialog>
        <!--dialog-->
    </div>
</body>
<script type="module">
    window.appInstince = new Vue({
        el: '#app',
        data: {
            //活动生成界面
            planGuide:{
                visiable:false
            },
            //事件过滤查询表单
            event_filter_form: {
                froms:['system','user'], //显示来源
                prioritys:['1','2','3','4'],//显示优先级
                statuss:['0','1','2'] //显示状态
            },
            costPlanDataDialog: {
                visiable: false
            },
            //日历对话框
            calendarDialog: {
                visiable: true
            },
            //活动编辑对话框
            planEditorDialog: {
                visiable: false,
                title: '创建活动'
            },
            //活动建议对话框
            planSuggestdialog: {
                visiable: false
            },
            //新增计划对话框
            addEventDialog: {
                visiable: false,
                data: {}
            },
            //计划数据
            planData: {
                choseActivity: {},
                title: '',
                content: '',
                stime: '',
                etime: '',
                eventId:'',
                costplans:[]
            },
            //选中模板
            choseTemplate: {},

            planeTemplates: [],

            //当前选中计划
            eventData: {
                title: '测试计划',
                start: '2018-05-01',
                type: 'CyclePlan',
                source: 'system',
                voteInfo: {}
            },
            costTypeGroup: [],
            costPlanData: []
        },
        mounted() {
            this.loadCostType();
            var ins = this;
            var editor = UE.getEditor('editor', {
                allowDivTransToP: false
                //autoHeightEnabled: false
            });
            //事件 回填属性
            editor.addListener("contentChange", function () {
                //console.log('内容改变:' + editor.getContent());
                ins.planData.content = editor.getContent();
            });
            this.$refs['ueditor'] = editor;
             
        },
        computed: {
            nowDate() {
                return moment().format('YYYY-MM-DD');
            },
            nowDay() {
                return moment().format('DD');
            },
            //转换花费数据
            costValues() {
                const values = this.costPlanData.map(item => {
                    if (item.$CostType) {
                        return item.$CostType.type * item.value;
                    }
                    return 0;
                });
                return values;
            },
            //赞助费用
            sponsorship_fee() {
                const values = this.costValues;
                let in_sum = 0;
                var ay1 = values.filter(i => i > 0);
                if (ay1.length > 0) in_sum = ay1.reduce((a, b) => a + b);
                return in_sum;
            },
            //花费费用
            cost_fee() {
                const values = this.costValues;
                let minus_sum = 0;
                let ay2 = values.filter(i => i < 0);
                if (ay2.length > 0) minus_sum = ay2.reduce((a, b) => a + b);
                return minus_sum;
            },
            //总计费用
            total_fee() {
                return this.sponsorship_fee + this.cost_fee;
            }
        },
        methods: {
            loadCostPlan(){
                let ins = this;
                ajax_promise('/plan/costplan','post',null).then((result) => {
                    if(result.status){
                        ins.costPlanData = ins.initCostType(result.data);
                    }
                });
            },
            loadCostType(){
                var ins = this;
                ajax_promise('/costplan/costtype','get',null).then((result) => {
                    if(result.status){
                        ins.costTypeGroup = result.data;
                    }
                });
            },
            event_filter_change(){
                this.$refs.calendar.reload();
            },
            calendar_day_click(date) {
                //check
                if(moment().isAfter(date)){
                    $message('只能创建将来的事件信息!','warning',this);
                    return;
                };
                this.showTooltip(false);
                this.addEventDialog.data.stime = date
                this.addEventDialog.data.etime = date;
                this.addEventDialog.visiable = true;
            },
            calendar_event_click(data) {
                data.visiable = false;
                this.eventData = data;                
                this.showTooltip(true, event);
            },
            calendar_month_change(pn) {
                this.showTooltip(false);
            },

            //tooltip 回调处理
            toolTipHandleCallback(type, data) {
                console.log('tooltip 回调', type, data);
                if (type == 'create') this.createPlane();
                
            },
            //创建计划
            createPlane() {
                this.calendarDialog.visiable = false;
                this.planEditorDialog.visiable = true;
                this.showTooltip(false);
                this.planData.eventId = this.eventData.eventId;
                this.planData.stime = this.eventData.stime;
                this.planData.etime = this.eventData.etime ? this.eventData.etime : this.eventData.stime;
            },
            //显示计划
            disPlane() {

            },
            showTooltip(show, event) {
                this.eventData.visiable = show;
                if (event) {
                    var target = event.target;
                    //弹窗
                    var popper = new Popper(target, $('#tooltip-container'), {
                        placement: 'right-start',
                        flipBehavior: ['left', 'bottom', 'top'],
                    });
                }
            },
            //加载对应的模版信息
            loadTemplate() {
                var ins = this;
                
                $.ajax({
                    type: "post",
                    url: "/plan/templates",
                    async: true,
                    success: function (data) {
                        ins.planeTemplates = data.data;
                        ins.event_filter_change();
                    }
                });
            },
            //模板选择改变
            tpChoseChange() {
                if (this.choseTemplate) {
                    this.$refs.ueditor.setContent(this.choseTemplate.content);
                    this.planData.title = this.choseTemplate.title;

                    this.loadCostPlan();
                } else {
                    this.$refs.ueditor.setContent("");
                    this.planData.title = "";

                    this.costPlanData = [];
                }

            },
            //活动计划选择
            planChoseChange(row) {
                console.log('活动推荐回调:', row);
                this.planData.choseActivity = row;
                this.planSuggestdialog.visiable = false;
                this.loadTemplate();
            },
            split(str) {
                return str.split(';');
            },
            //提交计划
            submitPlane() {
                var ins = this;
                //
                this.planData.costplans = this.costPlanData;
                ajax_json_promise('/event/plan','post',this.planData).then((result) =>{
                    if(result.status){
                        $message('增加成功!','success',ins);
                        ins.calendarDialog.visiable = true;
                        ins.planEditorDialog.visiable = false;
                    }
                });
            },
            //取消计划
            caclePlane() {
                this.calendarDialog.visiable = true;
                this.planEditorDialog.visiable = false;
            },

                        //加载花费数据 类型
            initCostType(rows) {
                rows.forEach(row => {
                    let tv = row.costType;
                    this.costTypeGroup.forEach(e1 => {
                    e1.children.forEach(e2 => {
                        if (e2.data.costType == tv) {
                            row.$CostType = e2.data;
                        }
                    })
                });
                });
                return rows;
            },
            addEventCallback(data) {
                let ins = this;
                ajax_json_promise('/event/user/user-event','post',data).then((result)=>{
                    if(result.status){
                        $message('增加成功!','success',ins);
                        ins.addEventDialog.visiable = false;
                        ins.addEventDialog.data = {};
                    }
                });
            },
            costPlanDataEditComplete(){
                console.log('费用编辑计划回调');
                this.costPlanDataDialog.visiable = false;
            },
            createPlanGuide(){
                this.planGuide.visiable = true;
            },
            createPlanGuidSave(){
                this.planGuide.visiable = false;
            }
        },
        components: {}
    });
    function filterEvent() {
        $('#calendar').fullCalendar('removeEvents', function (e) {
            console.log(e);
        })
    }
</script>

</html>