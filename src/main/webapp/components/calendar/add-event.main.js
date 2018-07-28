/**
 * 增加计划
 */
let AddEvent = {
    info: {
        name: 'add-event', 
        template_url: '/components/calendar/add-event.view.html', 
        author: 'Wangch',
        descript: '增加计划组件'
    },
    props: {
        addEventDialog:{
            type:Object,
            default:()=>{
                let obj  = {
                    data:{
                        
                    }
                }
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
        submitEvent() {
            var data = this.addEventDialog.data;
            this.$emit("added-data", data) //触发事件
        }
    },
    template: require("./add-event.view.html")
}
export default AddEvent;