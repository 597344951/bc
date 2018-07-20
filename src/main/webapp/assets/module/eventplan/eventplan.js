import {
    Activities
} from '/assets/js/activity-all.js';

window.appInstince = new Vue({
    el: '#app',
    data: {
        //活动生成界面
        planGuide: {
            visiable: false
        },
        //建议的活动计划
        sugPlan: {},
        eventdata:{},//关联事件信息
        activeMonth: '',
        curDate: new Date(),
        markDate: [],//日期标记
        monthPlans: [],
        //新增计划对话框
        addEventDialog: {
            visiable: false,
            data: {}
        },
        createActivityPlanDialog: {
            visiable: false
        }
    },
    mounted() {
        this.activeMonth = (this.curDate.getMonth() + 1) + '月推荐活动';
        this.loadEvents();
    },
    computed: {},
    methods: {
        showAddActivityPlan(eventdata, plan) {
            console.log('show Add Activity Plan: ',arguments);
            this.sugPlan = plan;
            this.eventdata = eventdata;
            console.log(eventdata, plan)
            this.createActivityPlanDialog.visiable = true;
        },
        addActivityPlan(planData, costPlan) {
            var ins = this;
            planData.eventId = this.eventdata.eventId;
            planData.costplans = costPlan;
            ajax_json_promise('/event/plan', 'post', planData).then((result) => {
                if (result.status) {
                    $message('增加成功!', 'success', ins);
                    ins.createActivityPlanDialog.visiable = false;
                    ins.$refs['activityPlan'].reset();
                }
            });
        },
        choseDay(dts) {

            this.showAddEventDialog(new Date(dts));
        },
        showAddEventDialog(date) {
            this.addEventDialog.data.stime = date
            this.addEventDialog.data.etime = date;
            this.addEventDialog.visiable = true;
        },
        addEventCallback(data) {
            this.$message({
                message: '成功增加活动计划',
                type: 'success'
            });
            this.addEventDialog.visiable = false;
            console.log('已增加事件', data)
        },
        getActivities(data) {
            let ay = [];
            let count = Math.floor(Math.random() * 3);
            for (let i = 0; i <= count; i++) {
                let index = Math.floor((Math.random() * Activities.length));
                ay.push(Activities[index]);
            }

            return ay
        },
        changeMonth(date) {
            console.log(date);
            this.curDate = new Date(date);
            this.loadEvents();
            this.activeMonth = (this.curDate.getMonth() + 1) + '月推荐活动';
        },
        loadEvents() {
            let me = this;
            let formData = {};//查询最近两个月活动
            formData.stime = moment(this.curDate).date(1).hour(0).minute(0).second(0);
            formData.etime = moment(this.curDate).add(2, "months").date(1).hour(0).minute(0).second(0);
 
            //加载事件数据
            ajax_json_promise('/event/list', 'post', formData).then((result) => {
                let dt = result.data;
                me.setCalendarMarker(dt);
                me.groupByAge(dt);
            }).catch((xhr) => {
                ins.$message({
                    message: xhr.statusText,
                    type: 'error'
                });
            });
        },
        setCalendarMarker(data) {
            this.markDate = data.map(it => it.stime);
        },
        groupByAge(data) {
            let map = new Map();
            data.forEach(e => {
                let startDate = new Date(e.stime);
                if (startDate.getMonth() !=
                    this.curDate.getMonth() && startDate.getMonth() != this.curDate.getMonth() + 1) {
                    return;
                }
                let k = new Date(e.stime).getMonth() + 1;
                let v = map.get(k);
                if (!v) {
                    v = [];
                    map.set(k, v);
                }
                v.push(e);
            });
            console.log(map);
            let ay = [];
            map.forEach((v, k) => {
                ay.push({
                    title: k + '月推荐活动',
                    datas: v
                });
            })
            this.monthPlans = ay;
        },
        getPriorityClass(data) {
            let priority = data.priority;
            switch (priority) {
                case 1:
                    return 'emergency-level-label';
                    break;
                case 2:
                    return 'high-level-label';
                case 3:
                    return 'normal-level-label';

                case 4:
                    return 'low-level-label';
                default:
                    return 'normal-level-label';
            }
        },
        createPlanGuidSave() {
            this.planGuide.visiable = false;

            var ins = this;
            //
            this.planData.costplans = this.costPlanData;
            ajax_json_promise('/event/plan', 'post', this.planData).then((result) => {
                if (result.status) {
                    $message('增加成功!', 'success', ins);
                    ins.calendarDialog.visiable = true;
                    ins.planEditorDialog.visiable = false;
                }
            });
        }
    },
    components: {}
});