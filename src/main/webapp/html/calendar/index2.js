import {Activities} from '/assets/js/activity-all.js';

window.appInstince = new Vue({
    el: '#app',
    data: {
        activeMonth:'',
        curDate: new Date(),
        markDate: [],
        suggests: [{
            title: '七月活动推荐'
        }, {
            title: '八月活动推荐'
        }, {
            title: '九月活动推荐'
        }, {
            title: '十月活动推荐'
        }]
    },
    mounted() {
        this.activeMonth = (this.curDate.getMonth()+1)+'月推荐活动';
        this.loadEvents();
    },
    computed: {},
    methods: {
        getActivities(data){
            let ay = [];
            let count = Math.floor(Math.random() * 3);
            for(let i=0;i<=count;i++){
                let index = Math.floor((Math.random()*Activities.length)); 
                ay.push(Activities[index]);
            }
            
            return ay
        },
        changeMonth(date) {
            console.log(date);
            this.curDate = new Date(date);
            this.loadEvents();
            this.activeMonth = (this.curDate.getMonth()+1)+'月推荐活动';
        },
        loadEvents() {
            let me = this;
            $.ajax({
                type: "get",
                url: "calendar/list",
                async: true,
                success: function (data) {
                    let idata = $.parseJSON(data);
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
            this.suggests = ay;
        }
    },
    components: {}
});