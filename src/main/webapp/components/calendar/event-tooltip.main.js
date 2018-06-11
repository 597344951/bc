let EventToolTip = {
    info: {
        name: 'event-tooltip', 
        template_url: '/components/calendar/event-tooltip.view.html', 
        author: 'Wangch',
        descript: '计划展示用 tooltip'
    },
    props: {
        data:{
            type:Object,
            default:function(){
                let obj = {
                    visiable:true
                };
                return obj;
            }

        }
    },
    data: function () {
        return {}
    },
    watch: {},
    computed: {},
    methods: {
        handle(type){
            this.$emit('callback',type, this.data)
        } 
    },
    //模版内容
    template: ''
}

export default EventToolTip;