var ContextMenu = {
    info: {
        name: 'context-menu', //注册组件名
        template_url: '/components/common/context-menu.view.html',
        author: 'Wangch',
        descript: '右键菜单'
    },
    props: {
        //菜单数据
        data: {
            type: Array,
            default: () => {
                return [{
                    label: '增加',
                    icon: 'el-icon-plus',
                    type: 'success'
                }, {
                    label: '修改',
                    icon: 'el-icon-edit',
                    type: 'primary'
                }, {
                    label: '删除',
                    icon: 'el-icon-delete',
                    type: 'danger'
                }];
            }
        },
        //点击后关闭
        hideOnClick: {
            type: Boolean,
            default: true
        },
        //关闭超时时间 毫秒
        closeTimeOut: {
            type: Number,
            default: 1000
        },
        visiable: {
            type: Boolean,
            default: true
        }
    },
    data() {
        return {
            closeTimer: null,
            position: {
                x: 333,
                y: 444
            },
            show: this.visiable,
            mouseEvent: {},
            datas: {}
        };
    },
    watch: {
        mouseEvent(val, oldVal) {
            if (val) {
                this.position.x = val.x - 10;
                this.position.y = val.y - 10;
            }
        }
    },
    computed: {},
    methods: {
        getTypeClass(type) {
            if (type == 'primary') return 'el-button--primary';
            if (type == 'danger') return 'el-button--danger';
            if (type == 'success') return 'el-button--success';
            if (type == 'info') return 'el-button--info';
            if (type == 'warning') return 'el-button--warning';

            return ' el-select-dropdown__item';
        },
        //定时器 延时5秒关闭菜单
        mouseleave() {
            console.debug('mouse leave, 创建倒计时关闭定时器', arguments);
            let me = this;
            //开始计时
            this.closeTimer = setTimeout(() => {
                me.hideMenu();
            }, this.closeTimeOut);
        },
        mouseenter() {
            //清除定时器
            console.debug('mouse enter,清除关闭定时器', arguments);
            this.clearTimer();
        },
        clearTimer() {
            if (this.closeTimer) {
                clearTimeout(this.closeTimer);
                this.closeTimer = null;
            }
        },
        menuItemClick(item) {
            console.debug('menu item click', arguments);
            this.$emit('click', item, this.datas);
            if (this.hideOnClick) {
                this.hideMenu();
            }
        },
        showMenu() {
            let [event, ...data] = arguments;
            console.debug('显示菜单');
            this.show = true;
            this.mouseEvent = event;
            this.datas = data;
        },
        //隐藏菜单
        hideMenu() {
            console.debug('隐藏菜单');
            this.clearTimer();
            this.show = false;
            this.mouseEvent = null;
            this.datas = null;
        }
    },
    template: require("./context-menu.view.html")
}

export default ContextMenu;