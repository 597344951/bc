/**https://github.com/zwhGithub/vue-calendar **/
import timeUtil from '/components/vue-calendar/timeUtil.js';
export default {
    info: {
        name: 'vue-calendar', //注册组件名
        template_url: '/components/vue-calendar/vue-calendar.view.html', //模块 页面模板 url
        author: 'Wangch',
        descript: 'Vue日历'
    },
    data() {
        return {
            textTop: ['一', '二', '三', '四', '五', '六', '日'],
            myDate: [],
            list: [],
            historyChose: [],
            dateTop: ''
        };
    },
    props: {
        //arr=['2018/4/1','2018/4/3'] 标记4月1日和4月3日 简单标记
        markDate: {
            type: Array,
            default: () => []
        },
        // 多种不同的标记
        //第一个标记和第二个标记不能同时使用
        markDateMore: {
            type: Array,
            default: () => []
        },
        // :agoDayHide='1514937600' //某个日期以前的不允许点击  时间戳10位
        agoDayHide: {
            type: String,
            default: `0`
        },
        // :futureDayHide='1525104000' //某个日期以后的不允许点击  时间戳10位
        futureDayHide: {
            type: String,
            default: `2554387200`
        }
    },
    created() {
        this.myDate = new Date();
    },
    methods: {
        setClass(data) {
            let obj = {};
            obj[data.markClassName] = data.markClassName;
            return obj;
        },
        clickDay: function (item, index) {
            if (item.otherMonth === 'nowMonth' && !item.dayHide) {
                this.getList(this.myDate, item.date);
            }
            if (item.otherMonth !== 'nowMonth') {
                item.otherMonth === 'preMonth' ?
                    this.PreMonth(item.date) :
                    this.NextMonth(item.date);
            }
        },
        ChoseMonth: function (date, isChosedDay = true) {
            date = timeUtil.dateFormat(date);
            this.myDate = new Date(date);
            this.$emit('changemonth', timeUtil.dateFormat(this.myDate));
            if (isChosedDay) {
                this.getList(this.myDate, date, isChosedDay);
            } else {
                this.getList(this.myDate);
            }
        },
        PreMonth: function (date, isChosedDay = true) {
            date = timeUtil.dateFormat(date);
            this.myDate = timeUtil.getOtherMonth(this.myDate, 'preMonth');
            this.$emit('changemonth', timeUtil.dateFormat(this.myDate));
            if (isChosedDay) {
                this.getList(this.myDate, date, isChosedDay);
            } else {
                this.getList(this.myDate);
            }
        },
        NextMonth: function (date, isChosedDay = true) {
            date = timeUtil.dateFormat(date);
            this.myDate = timeUtil.getOtherMonth(this.myDate, 'nextMonth');
            this.$emit('changemonth', timeUtil.dateFormat(this.myDate));
            if (isChosedDay) {
                this.getList(this.myDate, date, isChosedDay);
            } else {
                this.getList(this.myDate);
            }
        },
        forMatArgs: function () {
            let markDate = this.markDate;
            for (let i = 0; i < markDate.length; i++) {
                markDate[i] = timeUtil.dateFormat(markDate[i]);
            }
            let markDateMore = this.markDateMore;
            for (let i = 0; i < markDateMore.length; i++) {
                markDateMore[i].date = timeUtil.dateFormat(markDateMore[i].date);
            }
            return [markDate, markDateMore];
        },
        getList: function (date, chooseDay, isChosedDay = true) {
            const [markDate, markDateMore] = this.forMatArgs();
            this.dateTop = `${date.getFullYear()}年${date.getMonth() + 1}月`;
            let arr = timeUtil.getMonthList(this.myDate);
            for (let i = 0; i < arr.length; i++) {
                let markClassName = '';
                let k = arr[i];
                k.chooseDay = false;
                const nowTime = k.date;
                const t = new Date(nowTime).getTime() / 1000;
                //看每一天的class
                for (const c of markDateMore) {
                    if (c.date === nowTime) {
                        markClassName = c.className || '';
                    }
                }
                //标记选中某些天 设置class
                k.markClassName = markClassName;
                k.isMark = markDate.indexOf(nowTime) > -1;
                //无法选中某天
                k.dayHide = t < this.agoDayHide || t > this.futureDayHide;
                if (k.isToday) {
                    this.$emit('isToday', nowTime);
                }
                let flag = !k.dayHide && k.otherMonth === 'nowMonth';
                if (chooseDay && chooseDay === nowTime && flag) {
                    this.$emit('choseday', nowTime);
                    this.historyChose.push(nowTime);
                    k.chooseDay = true;
                } else if (
                    this.historyChose[this.historyChose.length - 1] === nowTime && !chooseDay && flag
                ) {
                    k.chooseDay = true;
                }
            }
            this.list = arr;
        }
    },
    mounted() {
        this.getList(this.myDate);
    },
    watch: {
        markDate(val, oldVal) {
            this.getList(this.myDate);
        },
        markDateMore(val, oldVal) {
            this.getList(this.myDate);
        },
        agoDayHide(val, oldVal) {
            this.agoDayHide = parseInt(val);
            this.getList(this.myDate);
        },
        futureDayHide(val, oldVal) {
            this.futureDayHide = parseInt(val);
            this.getList(this.myDate);
        }
    }
};