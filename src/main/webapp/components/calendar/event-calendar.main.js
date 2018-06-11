let EventCalendar = {
    info: {
        name: 'event-calendar',
        template_url: '/components/calendar/event-calendar.view.html',
        author: 'Wangch',
        descript: '事件日历'
    },
    props: {
        data:{
            type:Object,
            default:()=>{
                return {};
            }
        }
    },
    data() {
        return {
            fullscreenLoading:false
        };
    },
    mounted(){
        this.initFullCalendar();
    },
    computed: {},
    watch: {},
    computed: {},
    methods: {
        //重新加载数据
        reload(){
            $('#calendar').fullCalendar('refetchEvents')
        },
        //月份 更改回调
        monthChange(pn){
            //prev 上一个月
            //next 下一个月
            this.$emit("month-change", pn);
        },
        //日期点击
        dayClick(date){
            this.$emit("day-click",date);
        },
        //事件点击
        eventClick(data){
            this.$emit("event-click", data);
        },
        initFullCalendar() {
            let formData = this.data;
            let ins = this;
            $('#calendar').fullCalendar({
                aspectRatio: 2,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,listMonth'
                },
                events: function (start, end, timezone, callback) {
                    console.debug(start, end, timezone);
                    formData.stime = start.toDate();//start.format('YYYY-MM-DD HH:mm:ss');
                    formData.etime = end.toDate();// end.format('YYYY-MM-DD HH:mm:ss');

                    ins.fullscreenLoading = true;
                    //加载事件数据
                    ajax_json_promise('/event/list','post',formData).then((result)=>{
                        var _nd = initCalendarData(result.data); 
                        //console.debug(_nd);
                        callback(_nd);
                        ins.fullscreenLoading = false;
                    }) 
                     
                },
                defaultDate: new Date(),
                navLinks: true, // can click day/week names to navigate views
                businessHours: true, // display business hours
                editable: true,
                //点击事件
                dayClick: function (date, event, view) {
                    //check
                    ins.dayClick(date);
                },
                eventClick: function (data, event, view) {
                   ins.eventClick(data);
                }
            });
        
            //鼠标 滚动切换月份
            $('#calendar')
                .bind('mousewheel', function (event, delta) {
                    var delta = event.originalEvent.deltaY;
                    var dir = delta > 0 ? 'next' : 'prev';
                    if (dir == 'prev') {
                        $('#calendar').fullCalendar('prev');
                    } else {
                        $('#calendar').fullCalendar('next');
                    }
                    ins.monthChange(dir);
                    return false;
                });
        }
    },
    //模版内容
    template: ''
}

function initCalendarData(datas) {
        let priorityMap = {
            "1": "emergency-level",
            "2": "high-level",
            "3": "normal-level",
            "4": "low-level"
        }
        let statusMap = {
            "0": "event-ready",
            "1": "event-vote",
            "2": "event-progress",
            "-1": "event-disable"
        }

        datas.map((v) => {
            v.className = ' event-icon ';
            let priority = v.priority;
            if (!priority) v.priority = priority = 3;

            let cst = priorityMap[priority]; //计算优先级
            v.className += " " + cst;
            v.priorityClass = cst + "-label";

            //计算颜色
            let tmstr = statusMap[v.status || 0];
            v.className += " " + tmstr;
            v.themeClass = tmstr;

            v.start = moment(v.stime).format('YYYY-MM-DD');
            v.end = moment(v.etime).format('YYYY-MM-DD');

            v.editable = false;
        });
        // console.log(datas);
        return datas;
    }
export default EventCalendar;