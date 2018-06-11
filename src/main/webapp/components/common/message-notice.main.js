var data = {
    mid: 1,
    title: 'test title',
    time: new Date(),
    sender: "System",
    content: "test message",
    url: 'http://www.baidu.com'
}
var MessageNotice = {
    info: {
        name: 'message-notice', //注册组件名
        template_url: '/components/common/message-notice.view.html', //模块 页面模板 url
        author: 'Wangch',
        descript: '通知信息组件'
    },
    props: {
        data: { //数据
            type: Array,
            default: () => {
                return [];
            }
        },
        width: { //弹出pop宽度
            type: Number,
            default: 350
        },
        placement: { //弹出pop位置
            type: String,
            default: 'bottom'
        }
    },
    data: function () {
        return {
            props: {
                width: this.width ? this.width : 350,
                placement: this.placement ? this.placement : 'bottom'
            }
        }
    },
    mounted() {
        var ins = this;
        var timer = setInterval(() => {
            if (!ins.data && ins.data.length == 0) return;
            //重新设置时间数据,触发页面信息刷新
            ins.data.forEach((d) => {
                d.time = new Date(d.time.getTime());
            });
        }, 60000);
    },
    watch: {},
    computed: {},
    methods: {
        message_click_handler(m) {
            this.$emit('click', m)
        },
        all_message_click_handler() {
            this.$emit('allclick')
        },
        getAvatar(m) {
            if ('System' == m.sender) {
                return '/assets/avatars/messages.png';
            }
        },
        //计算 时间信息
        getDateTimeInfo(m) {

            return this.timeAgo(m.time);
        },
        timeAgo(dt) {
            var n = new Date().getTime();
            var f = n - dt.getTime();
            var bs = (f >= 0 ? '前' : '后'); //判断时间点是在当前时间的 之前 还是 之后
            f = Math.abs(f);
            // return f;
            if (f < 6e4) {
                return '刚刚'
            } //小于60秒,刚刚
            if (f < 36e5) {
                return parseInt(f / 6e4) + '分钟' + bs
            } //小于1小时,按分钟
            if (f < 864e5) {
                return parseInt(f / 36e5) + '小时' + bs
            } //小于1天按小时
            if (f < 2592e6) {
                return parseInt(f / 864e5) + '天' + bs
            } //小于1个月(30天),按天数
            if (f < 31536e6) {
                return parseInt(f / 2592e6) + '个月' + bs
            } //小于1年(365天),按月数
            return parseInt(f / 31536e6) + '年' + bs; //大于365天,按年算
        }


    },
    template: '<h1>Message Notice loading</h1>'
};

export default MessageNotice;