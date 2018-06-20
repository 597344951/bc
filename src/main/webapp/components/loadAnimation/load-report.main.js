/**
 * 加载 报告
 */
import CommunityInfo from '/components/loadAnimation/community-info.main.js';
import PmInfo from '/components/loadAnimation/pm-info.main.js';
import FestivalInfo from '/components/loadAnimation/festival-info.main.js';
import HotNews from '/components/loadAnimation/hot-news.main.js';

let routes = [];
let comps = [CommunityInfo, PmInfo, FestivalInfo, HotNews];
comps.map((comp) => {
    let {
        info
    } = comp;
    let o = {
        info: info,
        path: '/report/' + info.name,
        component: (resolve, reject) => require_comp(resolve, reject, comp)
    }
    routes.push(o);
});
const router = new VueRouter({
    routes: routes
})
Vue.use(VueRouter);
let LoadReport = {
    info: {
        name: 'load-report',
        template_url: '/components/loadAnimation/load-report.view.html',
        author: 'Wangch',
        descript: '展示报表'
    },
    router: router,
    props: {},
    mounted() {
        this.loadDis();
    },
    data() {
        return {
            routes: routes,
            empty_dis:true,
            idx: 0,
            steps: [{
                t: 2000,
                msg: '查询社区信息'
            }, {
                t: 2000,
                msg: '查询党员信息'
            }, {
                t: 2500,
                msg: '查询节日信息'
            }, {
                t: 3000,
                msg: '查询近期热点'
            }, {
                t: 3000,
                msg: '正在生成报告中'
            }],
            loading: {
                visiable: false,
                text: ''
            }
        }
    },
    methods: {
        loadDis() {
            this.idx = 0;
            this.nextStep();
        },
        nextStep() {
            let ins = this;
            let step = this.steps[this.idx];
            if (!step) {
                ins.loading.visiable = false;
                return;
            }
            ins.loading.visiable = true;
            ins.loading.text = step.msg;
            setTimeout(() => {
                ins.idx++;
                ins.nextStep();
            }, step.t);
        },
        savePlan() {
            this.$emit('save')
        },
        changeReport(r) {
            this.empty_dis = false;
            this.$router.push(r);
        }
    }
};

export default LoadReport;