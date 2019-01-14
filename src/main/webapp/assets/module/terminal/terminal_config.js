window.appInstince = new Vue({
    el: '#app',
    data: {
        programLoading: false,
        disStatus: '',
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
        totalPrograms: [],
        logs: [],
        tpager: {
            total: 0,
            current: 1,
            size: 10
        },
        onlineCount: {},
        tqueryIf: {
            online: '1',
            orgId: 0,
        },
        pgw: {
            title: '终端配置',
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
        guidTags: [],
        configForm: {},
        orgTreeData: [],
        orgListData: [],
        props: {
            label: (data, node) => {
                return data.data.orgInfoName
            },
            children: 'children'
        },


    },
    mounted() {
        this.loadSetting();
        this.loadTeminalInfo();
        this.loadOrgTree()
    },
    computed: {
        playingCount() {
            return this.totalPrograms ? this.totalPrograms.filter(el => el.status == 1).length : 0;
        },
        expiredCount() {
            return this.totalPrograms ? this.totalPrograms.filter(el => el.status == 0).length : 0;
        },
        notPlayCount() {
            return this.totalPrograms ? this.totalPrograms.filter(el => el.status == 2).length : 0;
        }
    },
    methods: {
        loadOrgTree() {
            let url = '/org/ifmt/queryOrgInfosToTree'
            ajax_promise(url, 'post', {}).then(result => {
                console.log(result)
                this.orgTreeData = result.data
                this.orgListData = result.list
            })
        },
        loadTeminalInfo() {
            let me = this;
            let url = '/terminal/org-config/unconfig/' + this.tpager.current + '-' + this.tpager.size;
            if(this.tqueryIf.orgId == -1) delete this.tqueryIf.orgId
            ajax_json_promise(url, 'post', this.tqueryIf).then((result) => {
                if (result.data) {
                    me.tpager.total = result.pager.total;
                    me.terminals = result.data;
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
            this.programLoading = true;
            let me = this;
            this.pgw.visiable = true;
            this.pgw.title = tm_info.name + ' 节目列表';
            this.pgw.terminal = tm_info;
            let url = '/terminal/control/' + tm_info.id + '/program';
            ajax_promise(url, 'get').then(result => {
                me.tprograms = result.data;
                me.totalPrograms = result.data;
                me.programLoading = false;
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
            this.$confirm('是否移除该节目', '是否移除?', {
                type: 'warn'
            }).then(() => {
                let url = '/terminal/control/' + this.pgw.terminal.id + '/program/' + t.pkId;
                ajax_promise(url, 'delete').then((result) => {
                    if (result.status) {
                        this.$message({
                            message: '成功取消发布节目',
                            type: 'success'
                        });
                    }
                });
            })
        },
        SendCommand(control) {
            this.controlList.map((c) => {
                c.checked = false;
            });
            //危险操作， 提示确认
            if (control.danger) {
                this.$confirm('危险命令,是否确定？', '是否执行此操作?', {
                    type: 'danger'
                }).then(() => {
                    this.executeCommand(control);
                })
            } else this.executeCommand(control);
        },
        executeCommand(control) {
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
        },
        getStatusLabel(t) {
            let status = t.status;
            if (status == -1) return '屏幕不存在';
            if (status == 0) return '节目已过期';
            if (status == 1) return '正在播放';
            if (status == 2) return '当前未播放';
            return '未知';
        },
        filterStatus(status) {
            this.disStatus = status;
            this.tprograms = this.totalPrograms.filter(el => el.status == status);
        },
        fileSelectChange(file) {
            const reader = new FileReader()
            reader.readAsText(file.raw)
            reader.onload = (event) => {
                let result = event.target.result
                console.log('读取到guid: ', result)
                this.addNewGuid({code:result})
            }
        },
        handleClose(tag) {
            this.guidTags.splice(this.guidTags.indexOf(tag), 1);
        },
        openTerminalConfig(t) {
            this.pgw.visiable = true
            this.addNewGuid(t)
        },
        addNewGuid(t){
            if (!this.guidTags.find(e => e.guid == t.code)) {
                this.guidTags.push({
                    guid: t.code,
                    type: 'success',
                    checked: false,
                    status: false,
                    msg:'',

                })
            }
            this.checkTerminal()
        },
        submitConfig() {
            let guids = this.guidTags.map(e => e.guid)
            if (guids.length == 0) {
                this.$message({
                    type: 'warning',
                    message: '请选择要配置的终端guid.config'
                })
                return;
            }
            let node = this.$refs.orgTree.getCurrentNode()
            if (!node) {
                this.$message({
                    type: 'warning',
                    message: '请选择关联组织'
                })
                return
            }
            let ay = []
            guids.forEach(g => {
                ay.push({
                    code: g,
                    orgId: node.data.orgInfoId
                })
            })
            console.log(ay)
            ajax_json_promise('/terminal/org-config/configs', 'post', ay).then(result => {
                let ay = result.data
                if (ay.filter(s => s == 0).length > 0) {
                    this.$message({
                        type: 'warning',
                        message: '部分终端未配置成功'
                    })
                    ay.forEach((e, i) => {
                        this.guidTags[i].type = e == 1 ? 'success' : 'danger'
                    })
                } else {
                    if (result.status) {
                        this.$message({
                            type: 'success',
                            message: '终端组织配置成功'
                        })
                        this.guidTags = []
                        this.pgw.visiable = false
                        this.loadTeminalInfo()
                    }
                }
            })

        },
        terminalDelete(t) {
            this.$confirm('是否删除此终端?', '警告', {
                type: 'error'
            }).then(() => {
                console.debug('删除终端oid: ', t.oid)
                ajax_json_promise(`/terminal/basic/addup/${t.oid}`, 'delete', {}).then(result => {
                    if (result.status) {
                        this.$message({
                            type: 'success',
                            message: '删除终端成功'
                        })
                        this.loadTeminalInfo()
                    }
                })
            }).catch(() => {});
        },
        terminalConfigDelete(t){
            this.$confirm('删除关联配置信息后,可重新配置', '删除此联配置信息?', {
                type: 'warning'
            }).then(() => {
                ajax_json_promise('/terminal/org-config/config', 'delete', t).then(result => {
                    this.$message({
                        type: 'success',
                        message: '删除关联配置成功'
                    })
                    this.loadTeminalInfo()
                })
            })
        },
        syn() {
            ajax_json_promise("/terminal/basic/addup", "post", "").then((result) => {
                this.$message({
                    message: result.msg,
                    type: result.status ? 'success' : 'error'
                });
                setTimeout(this.loadTeminalInfo,1000)
            });
        },
        //获取所属组织
        getBelowOrg(t){
            if(t.orgId == 0)return '未配置所属组织'
            if(!this.orgListData || this.orgListData.length == 0)return '未找到组织信息'
            let org = this.orgListData.find(e => e.orgInfoId == t.orgId)
            if(org)return org.orgInfoName
            
            return '未配置'
        },
        checkTerminal(){
            let tags = this.guidTags.filter(e => e.checked == false)
            if(tags.length == 0)return
            let n = tags.map(e => {
                return {code:e.guid,orgId:0}
            })
            ajax_json_promise('/terminal/org-config/check','post',n).then(result =>{
                console.log(result)
                let ay = result.data
                ay.forEach(ele => {
                    let f = this.guidTags.find(e => e.guid == ele.source)
                    if(f){
                        f.checked = true
                        f.msg = ele.msg
                        f.status = ele.status
                    } 
                })
            })
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
        },
        'guidTags': function(val){
            console.log(val)
        }
    }
});