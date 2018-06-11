<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>发布完成内容</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-dialog.el-table thead, .el-dialog.el-table__row {
			font-size: 12px;
		}
		.el-input-number i {
			margin-top: 6px;
		}
		.el-upload-list__item {
			height: 25px;
		}
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<h3>过往发布内容活动</h3>
			</el-header>
			<el-main>
				<el-table :data="publishingContent.list" border style="width: 100%">
					<el-table-column prop="title" label="主题"></el-table-column>
					<el-table-column prop="username" label="发起人" width="180"></el-table-column>
					<el-table-column prop="add_date" label="时间" width="180"></el-table-column>
					<el-table-column prop="start_date" label="预定开始时间" width="120"></el-table-column>
					<el-table-column prop="end_date" label="预定结束时间" width="120"></el-table-column>
					<el-table-column prop="period" label="预定播放时段" width="120"></el-table-column>
					<el-table-column label="操作" width="200">
						<template slot-scope="scope">
							<el-button v-if="scope.row.content_type_id == 3" type="text" size="small" @click="loadAddition(scope.row)">完善活动信息</el-button>
							<el-button v-if="scope.row.content_type_id == 3" type="text" size="small" @click="loadParticipant(scope.row)">参加人员</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="publishingContent.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="publishingContent.total">
				</el-pagination>
			</el-footer>
		</el-container>

		<el-dialog title="信息完善" :visible.sync="additionDialogVisible">
			<el-form ref="form" :model="addition" label-width="100px">
				<el-form-item label="预定参加人数">
					<el-input v-model="addition.ordainParticipantNumber" disabled></el-input>
				</el-form-item>
				<el-form-item label="活动时间">
					<el-input v-model="addition.date" disabled></el-input>
				</el-form-item>
				<el-form-item label="实际参加人数">
					<el-input-number v-model="addition.actualParticipantNumber" controls-position="right" :min="0" :max="addition.ordainParticipantNumber"></el-input-number>
				</el-form-item>
				<el-form-item label="活动总结">
					<el-input type="textarea" :rows="4" placeholder="请输入内容" v-model="addition.summary">
					</el-input>
				</el-form-item>
				<el-form-item label="活动素材">
					<el-upload
							action="/material/uploadFile"
							:file-list="addition.material"
							:before-remove="beforeRemove"
							:on-remove="handleUploadRemove"
							:on-success="handleUploadSuccess"
							:on-error="handleUploadError"
							list-type="text">
						<el-button size="small" type="primary">点击上传</el-button>
					</el-upload>
				</el-form-item>
			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="additionDialogVisible = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="completeAddition">确 定</el-button>
			</div>
		</el-dialog>

		<el-dialog title="参加人员" :visible.sync="participantDialogVisible">
			<el-input size="mini" style="width: 80px;" v-model="participateLength" placeholder="活动时长"></el-input>
			<el-button type="success" size="mini" @click="changeAllParticipateLength">一键修改</el-button>
			<el-button type="success" size="mini" @click="changeAllParticipated">一键签到</el-button>
			<el-table :data="participant" size="mini" style="width: 100%">
				<el-table-column type="index" width="50"></el-table-column>
				<el-table-column prop="name" label="姓名"></el-table-column>
				<el-table-column prop="isParticipated" label="是否缺勤">
					<template slot-scope="scope">
						<el-select size="mini" v-model="scope.row.isParticipated">
							<el-option label="缺勤" value="0"></el-option>
							<el-option label="参加" value="1"></el-option>
						</el-select>
					</template>
				</el-table-column>
				<el-table-column prop="participateLength" label="活动时长（分钟）">
					<template slot-scope="scope">
						<el-input size="mini" v-model="scope.row.participateLength"></el-input>
					</template>
				</el-table-column>
			</el-table>

			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="participantDialogVisible = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="updateParticipant">确 定</el-button>
			</div>
		</el-dialog>
	</div>

	<script>
		var app = new Vue({
			el: '#app',
			data: {
                pageSizes: [10, 20, 50, 100],
			    pageSize: 10,
				publishingContent:{},
                additionDialogVisible: false,
                participantDialogVisible: false,
                addition: {
                    ordainParticipantNumber: 0,
                    material: []
				},
                participateLength: 0,
                participant: []
			},
            methods: {
                handleSizeChange(pageSize) {
                    load(1, pageSize)
				},
                handleCurrentChange(curPage) {
					load(curPage, this.pageSize)
				},
				loadAddition(row) {
                    get("/publish/activity/addition/" + row.content_id, reps => {
						if(reps.status && reps.data) {
							this.addition.ordainParticipantNumber = reps.data.ordain_participant_number;
                            this.addition.actualParticipantNumber = reps.data.actual_participant_number;
                            this.addition.summary = reps.data.summary;
                            this.addition.date = new Date(reps.data.activity_date).toLocaleString();
                            this.addition.contentId = row.content_id;
                            this.addition.id = reps.data.activity_addition_id;

                            this.additionDialogVisible = true;
						} else {
                            app.$message('没有活动附加信息...');
						}
					});
				},
                completeAddition() {
                    this.$confirm('确认添加？').then(()=>{
						post("/publish/activity/addition", this.addition, reps => {
							if(reps.status) {
								app.$message('添加成功...');
                                this.additionDialogVisible = false;
							} else {
								app.$message('信息补充失败...');
							}
						});
                    }).catch(()=>{});
				},
                handleUploadRemove(file, fileList) {
                    for(var i=0; i<this.addition.material.length; i++) {
                        if(file.name == this.addition.material[i].name) {
                            this.addition.material.splice(i, 1);
                        }
                    }
				},
				handleUploadSuccess(response, file, fileList) {
                    if(response.status) {
                        this.addition.material.push({
                            name: response.name,
                            url:response.url,
							type:response.type,
                            isFile:response.isFile
                        });
                    } else {
                        this.$message('上传失败,请重新选择上传.');
                    }
                },
                handleUploadError(err, file, fileList) {
                    this.$message('上传失败,请重新选择上传.');
                },
                beforeRemove(file, fileList) {
                    return this.$confirm('确定移除 '+ file.name +'？');
                },
                changeAllParticipateLength() {
                    for(var i in this.participant) {
                        this.participant[i].participateLength = this.participateLength;
					}
				},
                changeAllParticipated() {
                    for(var i in this.participant) {
                        this.participant[i].isParticipated = '1';
                    }
				},
				loadParticipant(row) {
					get("/publish/activity/participant/" + row.content_id, reps => {
					    if(reps.status) {
					        this.participant = [];
					        var p;
					        for(var i in reps.data) {
					            p = reps.data[i];
                                this.participant.push({
                                    name: p.username,
                                	isParticipated: p.is_participated + '',
                                    participateLength: p.participate_length,
									id: p.activity_participant_id
                                });
							}
                            this.participantDialogVisible = true;
						} else {
							app.$message('没有参与人数据...');
						}
					})
				},
                updateParticipant() {
                    this.$confirm('确认提交？').then(()=>{
						post("/publish/activity/participant", this.participant, reps => {
							if(reps.status) {
                                app.$message('提交成功...');
								this.participantDialogVisible = false;
							} else {
								app.$message('系统繁忙请稍后再试...');
							}
						});
                    }).catch(()=>{});

				}
			}
		});
		load(1, app.pageSize);

		function load(pageNum, pageSize) {
		    get('/publish/published/content/'+ pageNum+'/' + pageSize, reps => {
				if(reps.status) {
					app.publishingContent = reps.data;
				} else {
				    app.$message('没有数据...');
				}
			});
			
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
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(postData),
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