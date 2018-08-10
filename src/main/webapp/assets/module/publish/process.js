window.onFocus = function () {
    window.location.reload()
}

let app = new Vue({
    el: '#app',
    data: {
        tpager: {
            total: 0,
            current: 1,
            size: 10
        },
        dis_h_v: false,
        processContent: [],
        snapshot: '',
        isMoreEditCommit: false,
        isVerify: false,
        opinion: '',
        postData: {},
        url: '',
        processState: {
            visible: false,
            contentId: 9,
            active: 0,
            steps: [],
            detail: []
        },
        verifyState: {
            visible: false,
            list: []
        },
        publishPeriod: {
            show: false,
            period: {
                startDate: '',
                endDate: '',
                week: [],
                time: ''
            },
            rules: {
                startDate: [
                    { required: true, message: '请选择开始时间', trigger: 'blur' }
                ],
                endDate: [
                    { required: true, message: '请选择结束时间', trigger: 'blur' }
                ],
                week: [
                    { required: true, message: '请选择工作/非工作日', trigger: 'blur' }
                ],
                time: [
                    { required: true, message: '请选择时段', trigger: 'blur' }
                ]
            }
        },
        publishTerminal: {
            show: false,
            list: []
        },
        commitMessage: {
            show: false,
            message: []
        }
    },
    methods: {
        handleSizeChange(val) {
            //console.log(`每页 ${val} 条`);
            this.tpager.size = val;
        },
        handleCurrentChange(val) {
            //console.log(`当前页: ${val}`);
            this.tpager.current = val;
        },
        startMoreEdit(row) {
            var url = '/publish/process/start_me/' + row.id;
            get(url, reps => {
                init();
                if (reps.status) {
                    window.open("/sola/edit/" + reps.editId, 'edit', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,width=1920,height=1080');
                } else {
                    app.$message('编辑失败, 该内容已被其他人编辑 !');
                }
            })
        },
        moreEdit(row) {
            window.open("/sola/edit/" + row.snapshot, 'edit', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,width=1920,height=1080');
        },
        preCommitMoreEdit(row) {
            this.isMoreEditCommit = true;
            this.url = '/publish/process/commit_me/' + row.id;

        },
        commitMoreEdit() {
            this.postData = {
                snapshot: this.snapshot
            }
            this.isMoreEditCommit = false;
            post(this.url, this.postData, reps => {
                if (reps.status) {
                    init();
                    app.$message('提交成功 !');
                } else {
                    app.$message('提交失败 !');
                }
            });
        },
        preVerify(row, isAdopt) {
            this.url = '/publish/process/verify/' + row.id;
            this.postData = {
                type: row.type,
                isAdopt: isAdopt
            }
            if (isAdopt == 2) {
                this.isVerify = true;
            } else {
                this.verify();
            }
        },
        verify() {
            this.isVerify = false;
            this.postData.opinion = this.opinion;
            post(this.url, this.postData, reps => {
                if (reps.status) {
                    init();
                    app.$message('审核完成 !');
                } else {
                    app.$message('审核失败 !');
                }
            })
        },
        openPublishPeriod(row) {
            this.url = '/publish/process/publish/' + row.id
            this.publishPeriod.show = true
        },
        publish(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.$confirm('确认选择时间并发布？')
                        .then(_ => {
                            app.publishPeriod.show = false
                            let postData = this.publishPeriod.period
                            postData.period = postData.time[0] + '-' + postData.time[1]
                            postData.weeks = postData.week.join(',')
                            postJson(this.url, postData, reps => {
                                if (reps.status) {
                                    init()
                                    commitMessage(() => {
                                        app.$message('发布成功 !')
                                    })
                                }
                            })
                        })
                        .catch(_ => { });
                } else {
                    return false;
                }
            })
        },
        resetForm(form) {
            this.$refs[form].resetFields();
        },
        discard(row) {
            this.url = '/publish/process/discard/' + row.id;
            get(this.url, reps => {
                init();
                if (reps.status) {
                    app.$message('移除成功 !');
                } else {
                    app.$message('移除失败 !');
                }
            })
        },
        //show 是否默认显示dialog
        getProcessState(row, show) {
            if (show === false) {
                this.url = '/publish/process/state/' + row.type + '/' + row.id;
                get(this.url, reps => {
                    if (reps.status) {
                        this.processState.detail = [];
                        var msg, processItem;
                        for (var i in reps.data.log) {
                            msg = reps.data.log[i];
                            this.processState.detail.push(msg.add_date + ': ' + msg.label + '  ' + msg.msg + ' [ ' + (msg.remark ? msg.remark : '') + ' ] ');
                        }
                        this.processState.steps = [];
                        for (var i in reps.data.steps) {
                            processItem = reps.data.steps[i];
                            this.processState.steps.push({
                                index: i,
                                title: reps.data.steps[i].label
                            });
                            if (row.processItem == processItem.process_item_id) {
                                this.processState.active = Number.parseInt(i);
                            }
                        }
                    } else {
                        app.$message('获取进度信息失败!!!');
                    }
                })
            } else {
                this.processState.visible = true;
            }
        },
        getVerifyState(row) {
            this.url = '/publish/verifyState/' + row.id
            get(this.url, reps => {
                if (reps.status) {
                    this.verifyState.list = reps.data
                    this.verifyState.visible = true
                } else {
                    app.$message('获取审核进度失败')
                }
            })
        },
        viewTemplate(row) {
            window.open('/publish/template/' + row.id, 'template', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,width=1920,height=1080');
        },
        view(row) {
            if (!row.snapshot) {
                this.$message("预览还未提交, 无法预览.");
                return;
            }
            window.open('/sola/view/' + row.snapshot, 'view', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,width=1920,height=1080');
        },
        viewTerminal(row) {
            let url = '/publish/publishTerminal/' + row.id
            get(url, reps => {
                this.publishTerminal.list = reps.data
                this.publishTerminal.show = true
            })
        }
    }
});

//init
init();
function init() {
    get('/publish/process/content', reps => {
        if (reps.status) {
            var d;
            app.processContent = [];
            for (var i in reps.data) {
                d = reps.data[i];
                app.processContent.push({
                    id: d.content_id,
                    type: d.content_type_id,
                    title: d.title,
                    sponsor: d.username,
                    snapshot: d.snapshot,
                    date: d.add_date,
                    startDate: d.start_date,
                    endDate: d.end_date,
                    period: d.period,
                    processItem: d.process_item_id,
                    state: d.label,
                    operate: d.operate
                });
            }
        } else {
            app.$notify({
                title: '提示',
                message: '没有待处理发布内容.',
                type: 'info'
            });
        }
    });
}

function commitMessage(callback) {
    /* app.commitMessage.show = true
    message = [
                '敏感词检测...',
                '完成, 未发现敏感词',
                '校验素材文件MD5...',
                '完成, 未发现异常素材',
                '正在发布...',
                '发布完成.'
            ]
    let index = 1;
    app.commitMessage.message = []
    app.commitMessage.message.push(message[0])
    let iterval = setInterval(() => {
        app.commitMessage.message.push(message[index])
        index ++
        if(index == message.length) {
            app.commitMessage.show = false
            clearInterval(iterval)
            if(callback) {
                callback()
            }
            
        }
    }, 2*1000); */
    app.commitMessage.show = true;
}

function get(url, callback) {
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function (reps) {
            callback(reps);
        },
        error: function (err) {
            app.$notify({
                title: 'ERROR',
                message: '系统错误...',
                type: 'error'
            });
        }
    });
}

function post(url, postData, callback) {
    var postStr = '';
    for (var key in postData) {
        postStr += key + '=' + postData[key] + '&';
    }
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: postStr,
        success: function (reps) {
            callback(reps);
        },
        error: function (err) {
            app.$notify({
                title: 'ERROR',
                message: '系统错误...',
                type: 'error'
            });
        }
    });
}

function postJson(url, postData, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: JSON.stringify(postData),
        contentType: 'application/json',
        success: function (reps) {
            callback(reps);
        },
        error: function (err) {
            app.$notify({
                title: 'ERROR',
                message: '系统错误...',
                type: 'error'
            });
        }
    });
}

window.appInstince = app;