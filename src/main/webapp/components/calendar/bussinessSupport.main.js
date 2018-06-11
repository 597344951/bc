/** 异步方式定义组件**/
Vue.component('bussiness-support-module', function (resolve, reject) {
    html_require_asyn('/components/calendar/bussinessSupport.view.html', function (html) {
        resolve(initComp(html))
    });
})
/**
 * 组件简介: 根据关联事件类型 和 选择的活动,
 */
function initComp(html) {
    return {
        props: ['eventData'],
        data: function () {
            return {
                supplierCompany:[{
                    name:'星辰文化用品公司',
                    category:'文化用品',
                    principal:'王尼玛',
                    tel:'138912345134',
                    address:'浙江省杭州市下城区河东路109号',
                    delivery:'是',
                    refund:'否'
                }]
            }
        },
        watch: {},
        computed: {},
        methods: {
            handleSelect(){
                console.log(arguments)
            }
        },
        template: html
    };
}