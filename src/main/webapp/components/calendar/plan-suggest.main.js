/** 活动推荐组件 **/

let PlanSuggest = {
    info: {
        name: 'plan-suggest',
        template_url: '/components/calendar/plan-suggest.view.html',
        author: 'Wangch',
        descript: '活动计划推荐'
    },
    props: {
        eventData: {
            //查询相关活动的事件
            type: Object,
            default: () => {
                let obj = {
                    title:'相关节日名称'
                };
                return obj;
            }
        },
        visiable: {
            type: Boolean,
            default: true
        }
    },
    // ['eventData','visiable'], 
    data: function () {
        return {
            loading: {
                visiable: false,
                title: ''
            },
            suggestData: [],
            oldData: {}
        }
    },
    watch: {
        visiable: function (val, oldVal) {
            //展示时 加载事件对应计划
            if (val == true && this.oldData != this.eventData) {
                this.loadSuggestData();
            }
        }
    },
    mounted() {
        console.log('PlanSuggest 加载完成,事件:', this.eventData)
        this.loadSuggestData();
    },
    computed: {},
    methods: {
        loadSuggestData() {
            if (!this.eventData) return;
            this.oldData = this.eventData;

            this.loading.title = '正在加载 [' + this.eventData.title + '] 活动计划';
            this.loading.visiable = true;
            var ins = this;
            setTimeout(function () {
                ins.suggestData = [{
                    id: 1,
                    name: '唱红歌',
                    score: 80,
                    reason: '活动适应全年龄段;老少皆宜;社区/组织下有专业音乐教师;环保无污染',
                    precautions: '社区/组织下老年人较多,注意安排活动时间,避免扰民'
                }, {
                    id: 2,
                    name: '春游踏青',
                    score: 70,
                    reason: '活动适应全年龄段;老少皆宜;环保无污染;亲子活动',
                    precautions: '注意活动计划'
                }]
                ins.loading.visiable = false;
            }, 2000)
        },
        activityItemClick(row) {
            this.$emit('change', row)
        },
        split(str) {
            return str.split(';');
        }

    },
    //模版内容
    template: ''
}

export default PlanSuggest;