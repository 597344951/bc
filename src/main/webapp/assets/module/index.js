/**增加标签{menuId:'',name:'',url:'',closable:false,icon:''}**/
window.addTab = function (target) {
    appInstince.addTab(target);
}

function checkScreenSize(){
    if(window.screen.width < 1024){
        return {collapse:true,width:'0px'}
    }

    return {collapse:false,width:'230px'}
}

var title_theme = window.sysInfo.title_theme
window.appInstince = new Vue({
    el: '#app',
    data: {
        app: {
            sysIcon: 'glyphicon glyphicon-send',
            sysName: window.sysInfo.sysName,
            userId: window.sysInfo.userId,
            sysUserName: window.sysInfo.sysUserName,
            sysUserAvatar: './assets/avatars/develop.gif',
            menuWidth: checkScreenSize().width,
            isCollapse: checkScreenSize().collapse, //是否收缩
            colors: {
                menu_bg: '#eef1f6',
                menu_txt: '#48576a',
                menu_act_bg: '#d1dbe5',
                menu_act_txt: title_theme
            },
            fullscreen: false,
        },
        tab_index: '', //tab选中分页index
        openTabDatas: [], //tab分页中的数据
        tabIndex: 2,
        menuDatas: [],
        messages: [
            /*{
                        mid: 1,
                        title: 'test title',
                        time: new Date(),
                        sender: "System",
                        content: "test message",
                        url: 'http://www.baidu.com'
                    }*/
        ],
        changeTheme: {
            visible: false,
            color: title_theme
        },
        //右键菜单点击的节点
        contextNode: null,
        contextMenu: {
            visiable: false,
            event: null
        },
        contextMenuData: [{
            label: '关闭所有',
            icon: 'el-icon-close'
        }, {
            label: '关闭其他',
            icon: 'el-icon-close'
        }]
    },
    mounted() {
        this.loadMenu();
        this.initWS();
        this.loadSetting();
    },
    watch: {
        tab_index(val, oldVal) {
            this.noticeTabFocus(val);
        }
    },
    methods: {
        contextMenuClick(item,datas) {
            this.contextNode = datas[0];
            let me = this;
            let tabs = this.openTabDatas.filter(item => item.closable);
            if (item.label == '关闭所有') {
                tabs.map(item => item.menuId).forEach(id => me.removeTab(id));
            } else if (item.label = '关闭其他') {
                let closeIds = tabs.filter(item => item.menuId != me.contextNode.menuId).map(item => item.menuId);
                closeIds.forEach(id => me.removeTab(id));
            }
            this.contextNode = null;
        },
        showMenu(item) {
            console.log('ContextMenu Show:', arguments);
            this.$refs.contextMenu.showMenu(event,item);
            this.contextNode = item;
        },
        tabRemove() {
            console.log(arguments)
        },
        //
        initWS() {
            //获取userid
            var userId = this.app.userId
            var stompClient = null;
            connect();

            function connect() {
                var socket = new SockJS('/ws');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, onConnected, onError);
            }

            function onConnected() {
                stompClient.subscribe('/notification/' + userId, message => {
                    handleMessage(JSON.parse(message.body));
                });
            }

            function onError() {
                appInstince.$message('无法连接到消息服务器.');
            }

            /**
             * 请在该方法中处理收到的消息
             */
            function handleMessage(message) {
                console.log(message);
            }
        },
        loadMenu() {
            var ins = this;
            ajax_json('sys/menu/tree', 'get', null, function (r) {
                ins.menuDatas = r.data;
            });
        },
        logout() {
            var ins = this;
            $confirm('是否退出?', '是否退出系统登陆?', 'warning', this, function () {
                ins.saveSetting(false);
                top.window.location.href = "/logout";
            });
        },
        //true:show,false:hide,其他为 事件
        openHideMenu() {
            this._openHideMenu(!this.app.isCollapse)
            this.saveSetting();
        },
        _openHideMenu(show) {
            this.app.isCollapse = checkScreenSize().collapse || show;
            if (!this.app.isCollapse) this.app.menuWidth = '230px';
            else this.app.menuWidth = '65px'
        },
        handleOpen(key, keyPath) {
            //console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            // console.log(key, keyPath);
        },
        //增加标签{menuId:'',name:'',url:''}
        addTab(target) {
            if (target.closable !== false) target.closable = true;
            var have = false;
            this.openTabDatas.forEach((tab, index) => {
                if (tab.menuId === target.menuId) {
                    this.tab_index = tab.menuId + '';
                    have = true;
                }
            });
            if (!have) {
                //判断打开标签数量
                if (this.openTabDatas.length > 10) {
                    toast('打开过多的标签', '请关闭一些后继续!', 'warning', this);
                    return;
                }
                //打开新标签
                this.openTabDatas.push(target);
                this.tab_index = target.menuId + '';
            }
            this.saveSetting();
        },
        //通知标签页面 被选中
        noticeTabFocus(target_index) {
            let ifid = target_index + '_iframe';
            console.debug('tab focus:  ', ifid);
            try {
                let callback = $('#' + ifid)[0].contentWindow.onFocus; //页面的关闭回调函数
                if(callback){
                    console.debug('tab focus method invoke');
                    callback();
                }else{
                    console.debug('tab focus method not found');
                }
            } catch (e) {}
        },
        checkTabClose(target_index) {
            let ifid = target_index + '_iframe';
            console.debug('close ', ifid);
            try {
                let callback = $('#' + ifid)[0].contentWindow.onclose; //页面的关闭回调函数
                if (callback && !callback()) {
                    console.debug('不关闭标签');
                    return false;
                }
            } catch (e) {}
            return true;
        },
        //移除标签
        removeTab(target_index) {
            if (!this.checkTabClose(target_index)) return;

            let tabs = this.openTabDatas;
            let action_index = this.tab_index;
            this.openTabDatas.forEach((tab, index) => {
                if (tab.menuId == target_index) {
                    let nextTab = tabs[index + 1] || tabs[index - 1];
                    if (nextTab) {
                        action_index = nextTab.menuId;
                    }
                }
            });
            this.tab_index = action_index + '';
            this.openTabDatas = this.openTabDatas.filter(tab => tab.menuId != target_index);

            this.saveSetting();
        },
        //切换tab时
        beforeLeaveTab(activeName, oldActiveName) {
            console.log(arguments)
            return false;
        },
        menuItemClick(obj) {
            console.log('打开' + obj.menuId + ' ' + obj.name)
            this.addTab(obj);
        },
        //消息点击
        message_click(m) {
            var mt = {
                menuId: m.mid,
                name: m.title,
                url: m.url
            }
            this.addTab(mt);
        },
        //全部消息点击
        messageAllClick() {
            console.log('全部消息点击');
        },
        //加载设置
        loadSetting() {
            let ck = this.getSettingKey();
            let str = getCookie(ck);
            if (!str || JSON.parse(str).openTabDatas.length == 0) {
                this.addTab({
                    menuId: 0,
                    name: '工作台',
                    url: '/view/bench.jsp',
                    closable: false,
                    icon: 'fa fa-briefcase'
                });
                return;
            }
            let obj = JSON.parse(str);
            this._openHideMenu(obj.showMenu);
            this.openTabDatas = obj.openTabDatas || [];
            this.tab_index = obj.tab_index || 0;
        },
        /**保存设置**/
        saveSetting(save) {
            let ck = this.getSettingKey();
            if (save == false) {
                //clear setting
                setCookie(ck, '');
                return;
            }
            let data = {
                showMenu: this.app.isCollapse, //
                openTabDatas: this.openTabDatas,
                tab_index: this.tab_index
            }
            setCookie(ck, JSON.stringify(data), 1); //
        },
        getSettingKey() {
            return 'userSetting' + this.app.userId;
        },
        changeTheme_handle() {
            let color = this.changeTheme.color;
            if (!color) return;
            console.log('change Color to :', this.changeTheme.color);
            var url = 'theme/color-' + encodeURIComponent(this.changeTheme.color.replace('#', '')) + '.css';
            this.app.colors.menu_act_txt = color;
            $('#theme-css')[0].href = url;
            this.changeTheme.visible = false;

            //this.app.colors.menu_bg = ColorCalc.gradient(color,36)
        },
        //设置全屏
        setFullscreen() {
            this.app.fullscreen = !this.app.fullscreen;
            if (this.app.fullscreen) {
                fullScreen(this);
            } else {
                exitFullScreen(this);
            }
        }
    },
    components: {
        //局部注册组件
        // 'test':(resolve,reject)=>require_comp(resolve,reject,MessageNotice)
    }
});