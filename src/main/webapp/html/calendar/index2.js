import {
    Activities
} from '/assets/js/activity-all.js';

window.appInstince = new Vue({
    el: '#app',
    data: {
         //活动生成界面
         planGuide:{
            visiable:false
        },
        //建议的活动计划
        sugPlan:{},
        activeMonth: '',
        curDate: new Date(),
        markDate: [],
        monthPlans: [ ],
        //新增计划对话框
        addEventDialog: {
            visiable: false,
            data: {}
        },
        createActivityPlanDialog:{
            visiable: false
        }
    },
    mounted() {
        this.activeMonth = (this.curDate.getMonth() + 1) + '月推荐活动';
        this.loadEvents();
    },
    computed: {},
    methods: {
        showAddActivityPlan(){
            this.createActivityPlanDialog.visiable = true;
        },
        addActivityPlan(planData,costPlan){
            this.$message({
                message: '新建活动策划',
                type: 'success'
            });
            this.createActivityPlanDialog.visiable = false;
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
            $.ajax({
                type: "get",
                url: "calendar/list",
                async: true,
                success: function (data) {
                    let idata = data;
                    let dt = idata.data;
                    me.setCalendarMarker(dt);
                    me.groupByAge(dt);
                }
            });
        },
        setCalendarMarker(data) {
            this.markDate = data.map(it => it.start);
        },
        groupByAge(data) {
            let map = new Map();
            data.forEach(e => {
                let startDate = new Date(e.start);
                if (startDate.getMonth() !=
                    this.curDate.getMonth() && startDate.getMonth() != this.curDate.getMonth() + 1) {
                    return;
                }
                let k = new Date(e.start).getMonth() + 1;
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
        getPriorityClass(data){
            let priority = data.priority;
            switch(priority){
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
        createPlanGuidSave(){
            this.planGuide.visiable = false;
        }
    },
    components: {}
});