let CreateActivityPlan = {
    info: {
        name: 'create-activity-plan',
        template_url: '/components/calendar/create-activity-plan.view.html',
        author: 'Wangch',
        descript: '创建活动策划方案'
    },
    props: {
        //计划信息
        plan: {
            type: Object,
            default () {
                return {};
            }
        },
        //事件信息
        timeInfo: {
            type: Object,
            default () {
                return {};
            }
        }
    },
    watch: {
        "timeInfo":function(val, oldVal) {
            this.planData.stime = val.stime;
            this.planData.etime = val.stime;
        },
        plan(val, oldVal) {

        }
    },
    data: function () {
        return {
            templateView: {
                title: '活动策划方案选择',
                display: false,
                keyword: ''
            },
            planData: {
                stime:this.timeInfo.stime,
                etime:this.timeInfo.etime,
            },
            planeTemplates: [],
            //选中模板
            choseTemplate: {},
            costPlanDataDialog: {
                visiable: false
            },
            costPlanData: [],
            costTypeGroup: [ ]
        }
    },
    mounted() {
        this.loadCostTypeData();
        let me = this;
        this.loadTemplate();

        var editor = UE.getEditor('editor', {
            allowDivTransToP: false
        });
        //事件 回填属性
        editor.addListener("contentChange", function () {
            me.planData.content = editor.getContent();
        });
        this.$refs['ueditor'] = editor;
    },
    computed: {

        reset(){
            console.log('重置数据');
            this.$refs.ueditor.setContent("");
            this.planData.title = "";
            this.costPlanData = [];
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
        loadCostTypeData(){
            let url = '/costplan/costtype';
            ajax_promise(url,'get',{}).then(result=>{
                this.costTypeGroup = result.data;
            });
        },
        openTemplate() {
            this.templateView.keyword = this.plan.suggestInfo.title;
            this.templateView.display = true;
        },
        caclePlane() {

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
                }
            });
        },
        //模板选择改变
        tpChoseChange(tp) {
            this.choseTemplate = tp;
            if (this.choseTemplate) {
                this.$refs.ueditor.setContent(this.choseTemplate.content);
                this.planData.title = this.choseTemplate.title;
            } else {
                this.$refs.ueditor.setContent("");
                this.planData.title = "";
                this.costPlanData = [];
            }

        },
        loadCostPlan() {
            var ins = this;
            $.ajax({
                type: "post",
                url: "/plan/costplan",
                async: true,
                success: function (data) {
                    var idata = data
                    ins.costPlanData = ins.initCostType(idata.data);
                }
            });
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
        submitPlane() {
            this.$emit("submit", this.planData, this.costPlanData)
        }

    },
    template: require("./create-activity-plan.view.html")
}

export default CreateActivityPlan;