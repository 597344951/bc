window.appInstince = new Vue({
    el: '#app',
    data: {
        optLogs: [],
        extLogs: [],
        optPager: {
            loading: false,
            total: 0,
            current: 1,
            size: 10
        },
        extPager: {
            loading: false,
            total: 0,
            current: 1,
            size: 10
        },
        selectLogType: '1',
        prg_dis_h_v: false,
        dis_h_v: false,
        terminals: [], //终端数据
        tprograms: [], //终端节目数据
        logs: [],
        tpager: {
            total: 0,
            current: 1,
            size: 10
        },
        onlineCount: {},
        tqueryIf: {
            online: '1'
        },
        pgw: {
            title: '',
            visiable: false,
            terminal: null,
            fullscreen: false
        },
        tcw: {
            title: '终端控制指令下发',
            visiable: false,
            terminal: null,
            fullscreen: false
        },
        tlw: {
            title: '日志信息',
            visiable: false,
            terminal: null,
            fullscreen: false
        },
        controlList: [{
            type: 'play',
            label: '开始播放',
            opt: '1',
            icon: 'fa fa-play-circle',
            checked: false
        }, {
            type: 'play',
            label: '停止播放',
            opt: '0',
            icon: 'fa fa-stop-circle-o',
            checked: false
        }, {
            type: 'volume',
            label: '音量+',
            opt: '1',
            icon: 'fa fa-volume-up',
            checked: false
        }, {
            type: 'volume',
            label: '音量-',
            opt: '0',
            icon: 'fa fa-volume-down',
            checked: false
        }, {
            type: 'volume',
            label: '静音',
            opt: '2',
            icon: 'fa fa-volume-off',
            checked: false
        }, {
            type: 'volume',
            label: '取消静音',
            opt: '3',
            icon: 'fa fa-volume-up',
            checked: false
        }, {
            type: 'screenshot',
            label: '截屏',
            opt: '600|600',
            icon: 'fa fa-scissors',
            checked: false
        }, {
            type: 'ocscreen',
            label: '打开播放器',
            opt: '1',
            icon: 'fa fa-power-off',
            checked: false
        }, {
            type: 'ocscreen',
            label: '关闭播放器',
            opt: '0',
            icon: 'fa fa-times-circle',
            checked: false
        }, {
            type: 'wakelock',
            label: '打开显示屏',
            opt: '1',
            icon: 'fa fa-television',
            checked: false
        }, {
            type: 'wakelock',
            label: '关闭显示屏',
            opt: '0',
            icon: 'fa fa-window-close',
            checked: false
        }, {
            type: 'cleardata',
            label: '清空数据',
            opt: '1',
            icon: 'fa fa-trash-o',
            checked: false
        }, {
            type: 'refresh',
            label: '刷新页面',
            opt: '1',
            icon: 'fa fa-refresh',
            checked: false
        }, {
            type: 'version',
            label: '显示版本',
            opt: '1',
            icon: 'fa fa-info-circle',
            checked: false
        }, {
            type: 'version',
            label: '关闭版本显示',
            opt: '0',
            icon: 'fa fa-times',
            checked: false
        }, {
            type: 'upgrade',
            label: '升级',
            opt: '0',
            icon: 'fa fa-cloud-upload',
            checked: false
        }, {
            type: 'shutdown',
            label: '关机',
            opt: '1',
            icon: 'fa fa-toggle-off',
            checked: false
        }, {
            type: 'reboot',
            label: '重启',
            opt: '1',
            icon: 'fa fa-repeat',
            checked: false
        }]

    },
    mounted() {
        this.loadSetting();
        this.loadTeminalInfo();
    },
    methods: {
        loadTeminalInfo() {
            let me = this;
            let url = '/terminal/basic/queryInfo/' + this.tpager.current + '-' + this.tpager.size;
            ajax_json_promise(url, 'post', this.tqueryIf).then((result) => {
                if (result.data) {
                    me.tpager.total = result.data.total;
                    me.terminals = result.data.list;
                } else {
                    me.tpager.total = 0;
                    me.terminals = [];
                }
                me.onlineCount = result.online;
            });
        },
        loadTerminalLogs() {
            let me = this;
            ajax_promise('/terminal/logs', 'post').then(result => {
                me.logs = result.data;
            })
        },
        openTerminalController(tm_info) {
            this.controlList.map((c) => {
                c.checked = false;
            });
            this.tcw.terminal = tm_info;
            this.tcw.visiable = true;
            this.tcw.title = tm_info.name + ' 控制指令';
        },
        openTerminalProgram(tm_info) {
            let me = this;
            this.pgw.visiable = true;
            this.pgw.title = tm_info.name + ' 节目列表';
            this.pgw.terminal = tm_info;
            let url = '/terminal/control/' + tm_info.id + '/program';
            ajax_promise(url, 'get').then(result => {
                me.tprograms = result.data;
            });
        },
        openTerminalLog(tm_info) {
            this.tlw.terminal = tm_info;
            this.tlw.visiable = true;
            this.tlw.title = tm_info.name + ' 日志信息';

            this.loadOptLog();
            this.loadExtLog();
        },
        //取消发布节目
        cancelTerminalProgram(t) {
            let me = this;
            let url = '/terminal/control/' + this.pgw.terminal.id + '/program/' + t.pkId;
            ajax_promise(url, 'delete').then((result) => {
                if (result.status) {
                    this.$message({
                        message: '成功取消发布节目',
                        type: 'success'
                    });
                }
            });
        },
        SendCommand(control) {
            this.controlList.map((c) => {
                c.checked = false;
            });
            control.checked = true;
            let tid = this.tcw.terminal.id
            let cmd = {
                commandName: control.label,
                command: control.type,
                commandContent: control.opt,
                screenId: tid
            };
            let url = '/terminal/control/' + tid + '/command';
            ajax_json_promise(url, 'post', cmd).then(result => {
                if (result.status) {
                    this.$message({
                        message: '已送指令: ' + control.label,
                        type: 'success'
                    });
                }
            });
        },
        level_dis(target) {
            if (target.level == 'DEBUG') return '';
            if (target.level == 'INFO') return 'info';
            if (target.level == 'WARN') return 'warning';
            if (target.level == 'ERROR') return 'danger';
            if (target.level == 'FATAL') return 'danger';
            return '';
        }, //计算 时间信息
        getDateTimeInfo(dt) {
            if (!dt) return '未知';
            return timeAgo(new Date(dt));
        },
        handleSizeChange(val) {
            //console.log(`每页 ${val} 条`);
            this.tpager.size = val;
            this.loadTeminalInfo();
        },
        handleCurrentChange(val) {
            //console.log(`当前页: ${val}`);
            this.tpager.current = val;
            this.loadTeminalInfo();
        },
        changeSearch(type) {
            this.tqueryIf.online = type;
            this.loadTeminalInfo();
        },
        getImgUrl(str) {
            let pt = /img\W+src=[\"]+([\/\-\.\w]+)+/
            var group = str.match(pt);
            return group[1];
        },
        loadExtLog() {
            let terminal = this.tlw.terminal;
            let pager = this.extPager;
            pager.loading = true;
            let url = '/terminal/control/' + terminal.id + '/executelog/' + pager.current + '-' + pager.size;
            ajax_promise(url, 'get').then(result => {
                pager.loading = false;
                let data = result.data;
                let pg = result.pager;
                if (pg) pager.total = pg.total;
                else pager.total = 0;
                pager.msg = result.msg;
                this.extLogs = data;
            });
        },
        loadOptLog() {
            let terminal = this.tlw.terminal;
            let pager = this.optPager;
            pager.loading = true;
            let url = '/terminal/control/' + terminal.id + '/operatelog/' + pager.current + '-' + pager.size;
            ajax_promise(url, 'get').then(result => {
                pager.loading = false;
                let data = result.data;
                let pg = result.pager;

                if (pg) pager.total = pg.total;
                else pager.total = 0;
                pager.msg = result.msg;
                this.optLogs = data;
            });
        },
        logHandleSizeChange(val) {
            if (this.selectLogType == '1') { //执行日志
                this.extPager.size = val;
                this.loadExtLog();
            } else if (this.selectLogType == '2') { //操作日志
                this.optPager.size = val;
                this.loadOptLog();
            }
        },
        logHandleCurrentChange(val) {
            if (this.selectLogType == '1') { //执行日志
                this.extPager.current = val;
                this.loadExtLog();
            } else if (this.selectLogType == '2') { //操作日志
                this.optPager.current = val;
                this.loadOptLog();
            }
        },
        openProgramView(pt) {
            let url = '/sola/view/' + pt.pkId;
            window.open(url, "_blank", "width=800 height=600");
        }, //加载设置
        loadSetting() {
            let ck = this.getSettingKey();
            let str = getCookie(ck);
            if (!str) return;
            let obj = JSON.parse(str);
            this.selectLogType = obj.selectLogType;
            this.dis_h_v = obj.dis_h_v;
            this.prg_dis_h_v = obj.prg_dis_h_v;
            this.tqueryIf.online = obj.on_off_line;
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
                selectLogType: this.selectLogType, //
                dis_h_v: this.dis_h_v,
                prg_dis_h_v: this.prg_dis_h_v,
                on_off_line: this.tqueryIf.online
            }
            setCookie(ck, JSON.stringify(data), 1); //
        },
        getSettingKey() {
            return 'terminal_manage_Setting';
        }
    },
    watch: {
        'selectLogType': function (val, oldval) {
            this.saveSetting();
        },
        'dis_h_v': function (val, oldval) {
            this.saveSetting();
        },
        'prg_dis_h_v': function (val, oldval) {
            this.saveSetting();
        },
        'tqueryIf.online': function (val, oldval) {
            this.saveSetting();
        }
    }
});