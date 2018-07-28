/**
 * 已发布的活动 追踪
 */
 let PubedPlanInfo = {
    info: {
        name: 'pubed-plan-info',
        template_url: '/components/calendar/pubed-plan-info.view.html',
        author: 'Wangch',
        descript: '已发布活动追踪'
    },
    data() {
        return {
            dis_h_v:false,
            tpager: {
                total: 0,
                current: 1,
                size: 10
            } 
        }
    },
    methods:{
        handleSizeChange(val) {
            //console.log(`每页 ${val} 条`);
            this.tpager.size = val;
        },
        handleCurrentChange(val) {
            //console.log(`当前页: ${val}`);
            this.tpager.current = val;
        }
    },
    template: require("./pubed-plan-info.view.html")
 }

 export default PubedPlanInfo;