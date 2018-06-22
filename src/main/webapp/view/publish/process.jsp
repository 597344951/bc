<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>待编辑/审核发布内容</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <style>
        .el-table thead,.el-table__row {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div id="app">
        <el-container>
            <el-header>
                <h3>正在处理中内容活动</h3>
            </el-header>
            <el-main>
                <template>
                    <el-table :data="processContent" border style="width: 100%">
                        <el-table-column prop="title" label="主题"></el-table-column>
                        <el-table-column prop="sponsor" label="发起人"></el-table-column>
                        <el-table-column prop="date" label="时间" width="180"></el-table-column>
                        <el-table-column prop="startDate" label="预定开始时间" width="120"></el-table-column>
                        <el-table-column prop="endDate" label="预定结束时间" width="120"></el-table-column>
                        <el-table-column prop="period" label="预定播放时段" width="120"></el-table-column>
                        <el-table-column prop="state" label="当前状态">
                            <template slot-scope="scope">
                                <el-tag>{{scope.row.state}}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="查看" width="200">
                            <template slot-scope="scope">
                                <el-button type="text" size="small" v-if="scope.row.operate.process" @click="getProcessState(scope.row)">进度</el-button>
                                <el-button type="text" size="small" v-if="scope.row.operate.template" @click="viewTemplate(scope.row)">模板</el-button>
                                <el-button type="text" size="small" v-if="scope.row.operate.snapshot" @click="view(scope.row)">预览</el-button>
                                <el-button type="text" size="small" @click="viewTerminal(scope.row)">发布终端</el-button>
                            </template>
                        </el-table-column>
                        <el-table-column prop="operate" label="操作" width="200">
                            <template slot-scope="scope">
                                <el-button type="text" size="small" @click="startMoreEdit(scope.row)" v-if="scope.row.operate.more_edit_start">开始编辑</el-button>
                                <el-button type="text" size="small" @click="moreEdit(scope.row)" v-if="scope.row.operate.more_edit">编辑</el-button>
                                <el-button type="text" size="small" @click="preCommitMoreEdit(scope.row)" v-if="scope.row.operate.more_edit_commit">编辑提交</el-button>
                                <el-button type="text" size="small" @click="preVerify(scope.row, true)" v-if="scope.row.operate.verify">审核（通过）</el-button>
                                <el-button type="text" size="small" @click="preVerify(scope.row, false)" v-if="scope.row.operate.verify">审核（不通过）</el-button>
                                <el-button type="text" size="small" @click="publish(scope.row)" v-if="scope.row.operate.publish">发布</el-button>
                                <el-button type="text" size="small" @click="discard(scope.row)" v-if="scope.row.operate.delete">移除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-main>
        </el-container>
        <%--编辑提交确定--%>
        <el-dialog title="提交确认" :visible.sync="isMoreEditCommit" width="30%">
            <%--<el-input v-model="snapshot" placeholder="预览地址"></el-input>--%>
            <p style="color: red; padding-left: 5px;">* 请确认是否已经完成内容编辑制作, 确认无误请点击确认提交.</p>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="isMoreEditCommit = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="commitMoreEdit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="填写理由/修改建议" :visible.sync="isVerify" width="30%">
            <el-input v-model="opinion" placeholder="不通过理由/修改意见" type="textarea" :rows="4"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="isVerify = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="verify">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="详细进度" :visible.sync="processState.visible" width="60%">
            <el-steps :space="200" :active="processState.active" finish-status="success">
                <el-step v-for="step in processState.steps" :title="step.title"></el-step>
            </el-steps>
            <el-card shadow="never">
                <p style="font-size: 12px;" v-for="d in processState.detail">{{d}}</p>
            </el-card>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="processState.visible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="processState.visible = false">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="终端列表" :visible.sync="publishTerminal.show" width="80%">
            <el-table :data="publishTerminal.list" border>
                <el-table-column prop="name" label="名称"></el-table-column>
                <el-table-column prop="rev" label="横屏/竖屏"></el-table-column>
                <el-table-column prop="typ" label="触摸类型"></el-table-column>
                <el-table-column prop="size" label="尺寸"></el-table-column>
                <el-table-column prop="ratio" label="分辨率"></el-table-column>
                <el-table-column prop="ip" label="IP"></el-table-column>
                <el-table-column prop="loc" label="位置"></el-table-column>
            </el-table>
        </el-dialog>


        <el-dialog title="素材验证" :visible="commitMessage.show" :show-close="false" width="300px">
			<p style="font-size: 13px; margin: 5px 20px; color: blue" v-for="m in commitMessage.message">{{m}}</p>
		</el-dialog>
    </div>

    <script>
        var app = new Vue({
            el: '#app',
            data:{
                processContent: [],
                snapshot: '',
                isMoreEditCommit: false,
                isVerify: false,
                opinion: '',
                postData: {},
                url:'',
                processState: {
                    visible: false,
                    active: 0,
                    steps:[],
                    detail: []
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
                startMoreEdit(row) {
                    var url = '/publish/process/start_me/'+row.id;
                    get(url, reps => {
                        init();
                        if(reps.status) {
                            window.open("/sola/edit/" + reps.editId, 'edit', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
                        } else {
                            app.$message('编辑失败, 该内容已被其他人编辑 !');
                        }
                    })
                },
                moreEdit(row) {
                    window.open("/sola/edit/" + row.snapshot, 'edit', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
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
                        if(reps.status) {
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
                    if(!isAdopt) {
                        this.isVerify = true;
                    } else {
                        this.verify();
                    }
                },
                verify() {
                    this.isVerify = false;
                    this.postData.opinion = this.opinion;
                    post(this.url, this.postData, reps => {
                        if(reps.status) {
                            init();
                            app.$message('审核完成 !');
                        } else {
                            app.$message('审核失败 !');
                        }
                    })
                },
                publish(row) {
                    this.url = '/publish/process/publish/' + row.id;
                    get(this.url, reps => {
                        if(reps.status) {
                            init();
                            app.$message('发布成功 !');
                            commitMessage()
                        } else {
                            app.$message('发布失败 !');
                        }
                    })
                },
                discard(row) {
                    this.url = '/publish/process/discard/' + row.id;
                    get(this.url, reps => {
                        init();
                        if(reps.status) {
                            app.$message('移除成功 !');
                        } else {
                            app.$message('移除失败 !');
                        }
                    })
                },
                getProcessState(row) {
                    this.url = '/publish/process/state/' + row.type + '/' + row.id;
                    get(this.url, reps => {
                        if(reps.status) {
                            this.processState.detail = [];
                            var msg, processItem;
                            for(var i in reps.data.log) {
                                msg = reps.data.log[i];
                                this.processState.detail.push(msg.add_date + ': ' + msg.label + '  ' + msg.msg + ' [ '+ (msg.remark ? msg.remark : '') +' ] ');
                            }
                            this.processState.steps = [];
                            for(var i in reps.data.steps) {
                                processItem = reps.data.steps[i];
                                this.processState.steps.push({
                                    index: i,
                                    title: reps.data.steps[i].label
                                });
                                if(row.processItem == processItem.process_item_id) {
                                    this.processState.active = Number.parseInt(i);
                                }
                            }

                            this.processState.visible = true;
                        } else {
                            app.$message('获取进度信息失败!!!');
                        }
                    })
                },
                viewTemplate(row) {
                    window.open ('/publish/template/' + row.id, 'template', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
                },
                view(row) {
                    if(!row.snapshot) {
                        this.$message("预览还未提交, 无法预览.");
                        return;
                    }
                    window.open ('/sola/view/' + row.snapshot, 'view', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
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
                if(reps.status) {
                    var d;
                    app.processContent = [];
                    for(var i in reps.data) {
                        d = reps.data[i];
                        app.processContent.push({
                            id: d.content_id,
                            type: d.content_type_id,
                            title: d.title,
                            sponsor: d.username,
                            snapshot: d.snapshot,
                            date: d.add_date,
                            startDate: d.start_date,
                            endDate:d.end_date,
                            period:d.period,
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
			app.commitMessage.show = true
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
			}, 2*1000);
		}

        function get(url, callback) {
            $.ajax({
                type:'GET',
                url:url,
                dataType:'json',
                success: function(reps){
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
            for(var key in postData) {
                postStr += key + '=' + postData[key] + '&';
            }
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                data: postStr,
                success: function(reps){
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
    </script>
</body>
</html>
