<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>节目接入</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-row {
			margin: 10px auto;
			width: 95%;
		}
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<h3>节目接入</h3>
			</el-header>
			<el-main>
				<el-row :gutter="10">
					<el-col :span="4">
						<el-button type="primary" icon="el-icon-plus" @click="newProgram.show = true">接入新节目</el-button>
					</el-col>
				</el-row>
				<el-table :data="program.list" border style="width: 100%">
					<el-table-column type="index" label="#"></el-table-column>
					<el-table-column prop="name" label="节目名称"></el-table-column>
					<el-table-column prop="org" label="节目单位"></el-table-column>
					<el-table-column prop="length" label="节目时长（秒）"></el-table-column>
					<el-table-column prop="desc" label="节目描述"></el-table-column>
					<el-table-column prop="checked" label="审核状态"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button v-if="scope.row.checked=='未审核'" size="mini" type="text" @click="showVideo(scope.row)">审核视频内容</el-button>
							<el-button size="mini" type="text" @click="remove(scope.row)">删除节目</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="program.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="program.total">
				</el-pagination>
			</el-footer>
		</el-container>

		<%--接入新节目--%>
		<el-dialog title="添加新节目" :visible="newProgram.show" :show-close="false">
			<el-form :model="newProgram" label-width="120px">
				<el-form-item label="节目名称">
					<el-input v-model="newProgram.name" placeholder="请输入节目名称"></el-input>
				</el-form-item>
				<el-form-item label="组织单位">
					<el-input v-model="newProgram.org" placeholder="请输入组织单位"></el-input>
				</el-form-item>
				<el-form-item label="节目时长">
					<el-input v-model="newProgram.length" placeholder="请输入节目时长（单位：秒）"></el-input>
				</el-form-item>
				<el-form-item label="节目描述">
					<el-input v-model="newProgram.desc" placeholder="请输入描述性文字"></el-input>
				</el-form-item>
				<el-form-item label="选择节目">
					<el-select v-model="newProgram.upType" placeholder="请选节目接入方式">
						<el-option label="链接地址" value="url"></el-option>
						<el-option label="本地上传" value="upload"></el-option>
					</el-select>
					<el-input v-if="newProgram.upType == 'url'" v-model="newProgram.url" placeholder="请输入节目链接地址（http://）" style="margin-top: 5px;"></el-input>
					<el-upload v-if="newProgram.upType == 'upload'"
							style="margin-top: 5px;"
							accept="video/*"
							:limit="1"
							action="/material/uploadFile"
							:on-success="handleUploadSuccess"
							drag>
						<i class="el-icon-upload"></i>
						<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						<div class="el-upload__tip" slot="tip">请上传节目视频文件（文件格式：*.mp4, *.avi）</div>
					</el-upload>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button size="mini" @click="newProgram.show = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="addProgram">添 加</el-button>
			</span>
		</el-dialog>

		<el-dialog title="审核视频内容" :visible="video.show" width="545" center>
			<video :src="video.url" width="500" height="350" autoplay="autoplay" controls="controls">
				您的浏览器不支持该视频播放...
			</video>
			<span slot="footer" class="dialog-footer">
				<el-button size="mini" @click="check(false)">不通过</el-button>
				<el-button size="mini" @click="video.show = false">取消</el-button>
				<el-button size="mini" type="primary" @click="check(true)">通 过</el-button>
			</span>
		</el-dialog>

		<el-dialog title="节目接入" :visible="commitMessage.show" :show-close="false" width="300px">
			<p style="font-size: 13px; margin: 5px 20px; color: blue" v-for="m in commitMessage.message">{{m}}</p>
		</el-dialog>
	</div>
	<script>
		const app = new Vue({
			el: '#app',
			data: {
                pageSizes: [10, 20, 50, 100],
			    pageSize: 10,
                program: {
                    pageNum: 1,
					total: 0,
					list: []
				},
				newProgram: {
                    show: false
				},
				video: {
                  	show: false,
					url: ''
				},
				checkRow: {},
				commitMessage: {
                    show: false,
                    message: []
                }

			},
            methods: {
                handleSizeChange(pageSize) {
                    load(1, pageSize)
                },
                handleCurrentChange(curPage) {
                    load(curPage, this.pageSize)
                },
                showVideo(row) {
                    this.checkRow = row
					this.video.url = row.url
					this.video.show = true
				},
				check(isPass) {
					if(isPass) {
						this.checkRow.checked = '通过审核'
						commitMessage(()=> {
							app.$message({
								message: '通过审核，节目已更新到节目库',
								type: 'success'
							})
						})
                       
					} else {
                        this.checkRow.checked = '未通过审核'
					}
                    this.video.show = false
				},
                handleUploadSuccess(response, file, fileList) {
                    this.newProgram.url = response.url
				},
				addProgram() {
					this.program.list.push({
						id: new Date().getTime(),
						name: this.newProgram.name,
						org: this.newProgram.org,
						length: this.newProgram.length,
                        desc: this.newProgram.desc,
						url: this.newProgram.url,
						checked: '未审核'
					})
                    this.newProgram.show = false
                    this.newProgram = {show: false}
				},
				remove(row) {
                    for(let i=0; i<this.program.list.length; i++) {
                        if(row.id == this.program.list[i].id) {
                            this.program.list.splice(i, 1)
						}
					}
				}
            }
		})

		//load(1, app.pageSize);

		const load = (pageNum, pageSize) => {
		    let url = '' + '/' + pageNum + '/' + pageSize
		    get(url + pageSize, reps => {
				if(reps.status) {
					app.plan = reps.data
				} else {
				    app.$message('没有数据...')
				}
			});
		}
		
		const commitMessage = (callback) => {
			app.commitMessage.show = true
			message = [
						'提取节目文件MD5...',
						'提取完成',
						'保存节目数据...',
						'保存完成'
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

        const get = (url, callback) => {
            $.ajax({
                type:'GET',
                url:url,
                dataType:'json',
                success: function(reps){
                    callback(reps)
                },
                error: function (err) {
                    app.$message.error('系统错误,请联系管理员')
                }
            })
        }

        const post = (url, postData, callback) => {
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(postData),
                success: function(reps){
                    callback(reps)
                },
                error: function (err) {
                    app.$message.error('系统错误,请联系管理员')
                }
            })
        }
	</script>
</body>
</html>