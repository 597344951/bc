<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>发布新内容</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<%@include file="/include/ueditor.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-row {
			margin-top: 5px;
		}
		.el-transfer {
			width: 80%;
		}
		.el-transfer-panel {
			width: 40%;
		}
		.el-transfer-panel__header {
			padding-top: 10px;
		}
		.el-upload-list__item {
			height: 25px;
		}
		.el-input-number i {
			margin-top: 6px;
		}
		h4 {
			margin: 5px;
			font-size: 14px;
		}

		h5 {
			margin: 5px;
			font-size: 13px;
		}
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-steps :active="step" finish-status="success">
					<el-step title="编辑内容"></el-step>
					<el-step title="选择发布终端"></el-step>
					<el-step title="编辑审核人"></el-step>
					<el-step title="发布完成"></el-step>
				</el-steps>
			</el-header>
			<el-main>
				<div v-show="step==0">
					<h3>内容/活动简单编辑</h3>
					<el-row :gutter="20">
						<el-col :span="4"><h4>主题 [<el-button type="text" @click="additionDialogVisible = true">补充</el-button>]</h4></el-col>
						<el-col :span="20"><el-input v-model="title" placeholder="发布内容主题"></el-input></el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="4"><h4>播放周期</h4></el-col>
						<el-col :span="20">
							<el-date-picker style="width: 18%;" v-model="startDate" type="date" placeholder="开始日期" value-format="yyyy-MM-dd 00:00:00"></el-date-picker>
							<el-date-picker style="width: 18%;" v-model="endDate" type="date" placeholder="结束日期" value-format="yyyy-MM-dd 23:59:59"></el-date-picker>
							：
							<el-select
									style="width: 18%;"
									v-model="week"
									multiple
									collapse-tags
									placeholder="工作日/非工作日">
								<el-option key="0" label="星期日" value="0"></el-option>
								<el-option key="1" label="星期一" value="1"></el-option>
								<el-option key="2" label="星期二" value="2"></el-option>
								<el-option key="3" label="星期三" value="3"></el-option>
								<el-option key="4" label="星期四" value="4"></el-option>
								<el-option key="5" label="星期五" value="5"></el-option>
								<el-option key="6" label="星期六" value="6"></el-option>
							</el-select>
							：
							<el-time-picker
									style="width: 30%;"
									is-range
									v-model="time"
									range-separator="-"
									value-format="H:mm"
									start-placeholder="开始时间"
									end-placeholder="结束时间"
									placeholder="选择时间范围">
							</el-time-picker>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="4"><h4>播放设置</h4></el-col>
						<el-col :span="20">
							<el-select
									style="width: 18%;"
									v-model="resolution"
									placeholder="屏幕分辨率">
								<el-option key="0" label="1920x1080" value="1920x1080"></el-option>
								<el-option key="1" label="1400x900" value="1400x900"></el-option>
								<el-option key="2" label="1280x720" value="1280x720"></el-option>
								<el-option key="3" label="960x540" value="960x540"></el-option>
								<el-option key="4" label="640x360" value="640x360"></el-option>
							</el-select>
							<el-select
									style="width: 18%;"
									v-model="screenType"
									placeholder="屏幕类型">
								<el-option key="0" label="非触屏" value="0"></el-option>
								<el-option key="1" label="触屏" value="1"></el-option>
							</el-select>
							：
							<el-input style="width: 18%;" v-model="playLength" placeholder="播放时长（秒）"></el-input>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="4">
							<h4>内容 [<el-button type="text" @click="showTemplates">选取模板</el-button>]</h4>
						</el-col>
						<el-col :span="20">
							<div><script id="templateText" name="templateText" style="height:500px;" type="text/plain"></script></div>
						</el-col>
					</el-row>
					<%--图片--%>
					<el-row :gutter="20">
						<el-col :span="4"><h5>素材提交（图片）</h5></el-col>
						<el-col :span="20">
							<el-upload
									action="/material/uploadImage"
									accept="image/*"
									:on-success="handleUploadSuccess"
									:on-error="handleUploadError"
									:on-remove="handleMaterialRemove"
									list-type="picture-card">
								<i class="el-icon-plus" style="line-height:5"></i>
							</el-upload>
						</el-col>
					</el-row>
					<%--视频--%>
					<el-row :gutter="20">
						<el-col :span="4"><h5>素材提交（视频）</h5></el-col>
						<el-col :span="20">
							<el-upload
									action="/material/uploadVideo"
									accept="video/*"
									:on-remove="handleMaterialRemove"
									:on-success="handleUploadSuccess"
									:on-error="handleUploadError"
									list-type="text">
								<el-button size="small" type="primary">点击上传</el-button>
							</el-upload>
						</el-col>
					</el-row>
					<%--音频--%>
					<el-row :gutter="20">
						<el-col :span="4"><h5>素材提交（音频）</h5></el-col>
						<el-col :span="20">
							<el-upload
									action="/material/uploadAudio"
									accept="audio/*"
									:on-remove="handleMaterialRemove"
									:on-success="handleUploadSuccess"
									:on-error="handleUploadError"
									list-type="text">
								<el-button size="small" type="primary">点击上传</el-button>
							</el-upload>
						</el-col>
					</el-row>
				</div>
				<%--选择终端--%>
				<div v-show="step==1">
					<h3>选择发布终端</h3>
					<el-table
							ref="multipleTable"
							:data="terminals"
							height="500"
							tooltip-effect="dark"
							style="width: 100%;"
							border
							@selection-change="handleSelectionChange">
						<el-table-column type="selection" width="55"></el-table-column>
						<el-table-column
								prop="group"
								label="分组"
								:filters="terminalGroup"
								:filter-method="filterGroup"
								filter-placement="bottom-end">
							<template slot-scope="scope">
								<el-tag>{{scope.row.group}}</el-tag>
							</template>
						</el-table-column>
						<el-table-column prop="name" label="名称"></el-table-column>
						<el-table-column prop="type" label="类型"></el-table-column>
						<el-table-column prop="size" label="尺寸"></el-table-column>
						<el-table-column prop="size" label="位置"></el-table-column>

					</el-table>
				</div>
				<%--审核人--%>
				<div v-show="step==2">
					<h3>编辑审核人（审核顺序为添加顺序）</h3>
					<div style="text-align: center">
						<el-transfer
								style="text-align: left; display: inline-block;"
								filterable
								:filter-method="exUserFilterMethod"
								filter-placeholder="请输入关键字"
								:titles="['可选审核人', '已选审核人']"
								v-model="selectedExUser"
								:data="exUsers">
						</el-transfer>
					</div>
				</div>
				<div v-show="step > 2">
					<el-card class="box-card" shadow="never">
						内容编辑完成.[<a :href="viewProcess">点击跳转查看进度...</a>]
					</el-card>
				</div>
			</el-main>
			<el-footer style="text-align:right;margin-right:50px;" >
				<el-button type="primary" @click="preStep" v-show="step > 0 && step <= 2">上一步</el-button>
				<el-button type="primary" @click="nextStep" v-show="step < 2">下一步</el-button>
				<el-button type="primary" @click="commit" v-show="step == 2">提交</el-button>
			</el-footer>
		</el-container>
		<%--模板--%>
		<el-dialog title="选择模板" :visible.sync="templateDialogVisible">
			<el-row>
				<el-col :span="10">
					<el-menu default-active="0">
						<el-submenu v-for="template in templates" :index="'' + template.id">
							<template slot="title">
								<i class="el-icon-tickets"></i>
								<span>{{template.label}}</span>
							</template>
							<el-submenu v-for="item in template.children" :index="'' + item.id">
								<template slot="title">
									<i class="el-icon-tickets"></i>
									<span>{{item.label}}</span>
								</template>
								<el-menu-item v-for="t in item.children" :index="'' + t.tpId" @click="templateSelect(t.content)">{{t.title}}</el-menu-item>
							</el-submenu>
						</el-submenu>
					</el-menu>
				</el-col>
				<el-col :span="14"></el-col>
			</el-row>

			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="templateDialogVisible = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="writeTemplate">确 定</el-button>
			</div>
		</el-dialog>

		<%--补充信息--%>
		<el-dialog title="信息补充" :visible.sync="additionDialogVisible">
			<el-form ref="form" :model="addition" label-width="100px">
				<el-form-item label="预顶参加人数">
					<el-input-number v-model="addition.ordainParticipantNumber" controls-position="right" :min="0"></el-input-number>
				</el-form-item>
				<el-form-item label="预定时间">
					<el-date-picker type="datetime" placeholder="选择日期" v-model="addition.date" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
				</el-form-item>
				<el-form-item label="开放对象">
					<el-select v-model="addition.participantType" placeholder="请选择开放对象">
						<el-option label="所有人" value="all"></el-option>
						<el-option label="党员" value="partyMember"></el-option>
						<el-option label="职工" value="staff"></el-option>
						<el-option label="社会人" value="masses"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="补充描述">
					<el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="addition.desc">
					</el-input>
				</el-form-item>
			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="additionDialogVisible = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="addAddition()">添 加</el-button>
			</div>
		</el-dialog>
	</div>


	<script type="text/javascript">
		const app = new Vue({
			el: '#app',
			data: {
			    step: 0,
                viewProcess: '#',
                templateDialogVisible: false,
                additionDialogVisible: false,
                commitCheckMsgVisible: true,
                templates:[],
				selectedTemplate: 0,
				type: 4,
				title: '',
				startDate: '',
				endDate:'',
				time: '',
				screenType: '',
				playLength: '',
				resolution: '',
				week: '',
                templateText: '',
                material: [],
                terminals: getTerminals(),
                terminalGroup: [{ text: 'one', value: 'one' }, { text: 'two', value: 'two' }, { text: 'three', value: 'three' }],
                selectedTerminals: [],
				selectedExUser: [${userId}],
				exUsers:getExUsers(),
				week: [],
                resolution: '',
                screenType: '',
                playLength: '',
                addition: {
                    participantType: 'all'
				}
			},
            methods: {
			    nextStep() {
					if(commitCheck()) {
                        this.step ++;
					}
				},
				preStep() {
			        this.step --;
				},
                handleMaterialRemove(file, fileList) {
					for(let i=0; i < this.material.length; i++) {
					    if(file.uid == this.material[i].uid) {
					        this.material.splice(i, 1);
						}
					}
				},
				handleUploadSuccess(response, file, fileList) {
					if(response.status) {
					    this.material.push({
							uid: file.uid,
							name: response.name,
							type: response.type,
							url: response.url,
							isFile: response.isFile
						})
					} else {
                        this.$message('上传失败,请重新选择上传.')
					}
				},
                handleUploadError(err, file, fileList) {
                    this.$notify('上传失败,请重新选择上传.')
                },
				commit() {
			        if(!commitCheck()) {
			            return;
					}
                    this.$confirm('提交后将不能再修改，确认提交？')
                        .then(_ => {
                            var content = {};
                            content.type = this.type;
                            content.title = this.title;
                            content.startDate = this.startDate;
                            content.endDate = this.endDate;
                            content.period = this.time[0] + '-' + this.time[1];
                            content.screenType = this.screenType;
                            content.playLength = this.playLength;
                            content.resolution = this.resolution;
                            content.week = this.week;
                            content.templateText = ue.getContent();
                            content.addition = this.addition;
                            //附加素材
							content.material = this.material;
                            //终端
                            content.terminals = this.selectedTerminals;
                            //审核人
                            content.exUsers = [];
                            for(var i in this.selectedExUser) {
                                for(var j in this.exUsers) {
                                    if(this.selectedExUser[i] == this.exUsers[j].key) {
                                        content.exUsers.push(this.exUsers[j]);
                                        break;
									}
								}
							}
							commit(content);
                        })
                        .catch(_ => {});
				},
				showTemplates() {
			        if(this.templates.length > 0) {
                        this.templateDialogVisible = true;
			            return;
					}
			        get("/tpt/listTypeTree", reps => {
			            if(reps.status) {
                            app.templates = reps.data;
                            for(var i in app.templates) {
                                for(var j in app.templates[i].children) {
                                    getTemplateContent(app.templates[i].children[j]);
								}
							}
							setTimeout(() => {
                                this.templateDialogVisible = true;
							}, 1000);

						} else {
                            app.$message('没有找到已定义模板 !');
						}
					});
				},
                getTemplates(index, indexPath) {
			        console.log(index, indexPath);
				},
                templateSelect(content) {
			        this.selectedTemplate = content;
				},
				writeTemplate() {
                    this.templateDialogVisible = false;
					ue.setContent(this.selectedTemplate);
				},
                handleSelectionChange(selected) {
                    this.selectedTerminals = selected;
                },
				filterGroup(value, row) {
                    return row.group === value;
                },
                exUserFilterMethod(query, item) {
			        if(!query) {
			            return true;
					} else {
			            return item.label.indexOf(query) > -1;
					}

				},
                addAddition() {
			        this.type = 3;
                    this.additionDialogVisible = false;
				}

			}
		});
		//编辑器
        var ue = UE.getEditor('templateText',{
            serverUrl: '/ueditor/controller.jsp',
            allowDivTransToP: false,
            wordCount: false,
            elementPathEnabled: false
        });

        function commit(postData) {
            $.ajax({
                type:'POST',
                url:'/publish/add',
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(postData),
                success: function(reps){
                    if(reps.status) {
                        app.step += 2;
                        app.viewProcess = '/publish/process';
                    } else {
                        app.$notify({
                            title: 'ERROR',
                            message: '提交失败,请检查输入.',
                            type: 'error'
                        });
                    }
                },
                error: function (err) {
                    app.$notify({
                        title: 'ERROR',
                        message: '提交失败.',
                        type: 'error'
                    });
                }
            });
        }

        function commitCheck() {
            var msgs = [];
            if(app.step == 0) {
                if(!app.title) {
                    msgs.push('* 请填写主题.');
                }
                if(!app.startDate || !app.endDate || !app.time) {
                    msgs.push('* 请完整输入内容预计发布时间.')
                }
                if(!ue.getContent()) {
                    msgs.push('* 请选择模板或填写自定义内容.');
                }
				if(!app.screenType) {
					msgs.push('* 请选择终端屏幕类型.');
				}
				if(!app.playLength) {
					msgs.push('* 请输入播放时长.');
				}
				if(!app.resolution) {
					msgs.push('* 请选择分辨率.');
				}
            } else if(app.step == 1) {
                if(app.selectedTerminals.length <= 0) {
                    msgs.push('* 请选择要发布内容的终端.');
				}
			} else if(app.step == 2) {
                if(app.selectedExUser.length <= 0) {
                    msgs.push('* 请选择内容审核人.');
                }
			}

            if(msgs.length > 0) {
                checkAlert(msgs);
                return false;
            } else {
                return true;
			}


        }

        function checkAlert(msgs) {
            var html = '';
            for(let i in msgs) {
                html += '<p style="font-size: 13px; color: red">'+ msgs[i] +'</p>';
			}
            app.$alert(html, '警告', {
                dangerouslyUseHTMLString: true
            });
        }

        function getTemplateContent(type) {
            get('/tp/listByType?tpTypeId=' + type.id, reps => {
                if(reps.status) {
                    type.children = reps.data;
                }
            })
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
                    app.$message('系统错误!');
                }
            });
		}

        function getTerminals() {
            var data = [];
            var area = ['A', 'B', 'C'];
            var floor = [1, 2, 3, 4];
            var type = ['横屏触摸', '竖屏触摸', '横屏非触摸', '竖屏非触摸'];
            var size = ['800x600', '1400x900', '1080x2048', '2048x3698'];
            var group = ['one', 'two', 'three'];
            for(var i=0; i < 40; i++) {
                data.push({
					id: i,
					name: 'T-' + area[i%3] + '-' + floor[i%4],
					type: type[i%4],
					size: size[i%4],
					group: group[i%3],
					groupId: i%3

				});
			}
			return data;
		}

		function getExUsers() {
			var data = [];
            for(var i=0; i < 20; i++) {
                data.push({
					key: i,
					id: i,
					duty: '',
                    label: 'exUser' + i + 'xx职务'
				});
			}
			return data;
        }
	</script>
</body>
</html>