/** 活动推荐组件 **/

let PubplanGuide = {
    info: {
        name: 'pubplan-guide',
        template_url: '/components/calendar/pubplan-guide.view.html',
        author: 'Wangch',
        descript: '发布计划向导'
    },
    props: {
        visiable: {
            type: Boolean,
            default: true
        }
    },
    data: function () {
        return {
             
        }
    },
    watch: {
         
    },
    mounted() {
    },
    computed: {},
    methods: {},
    template: require("./pubplan-guide.view.html")
}

export default PubplanGuide;