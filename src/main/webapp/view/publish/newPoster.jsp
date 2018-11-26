<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ page import="org.apache.shiro.SecurityUtils,org.apache.shiro.subject.Subject,com.zltel.broadcast.um.bean.SysUser" %>

<%
	Subject subject = SecurityUtils.getSubject();
    SysUser user = (SysUser) subject.getPrincipal();
    String userId = user == null ? "" : "" + user.getUserId();

%>
<html>

<head>
	<meta charset="UTF-8">
	<title>发布新内容</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		body {
			width: 98%;
			margin: 20px auto;
		}
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-row {
			margin: 10px auto;
			width: 95%;
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
		.template-preview {
			width: 100%
		}
		
		.image-box {
            width: 200px;
            height: 250px;
            border-radius: 5px;
            padding: 2px;
            margin: 10px;
            background-color: ghostwhite;
            float: left;
			position: relative;
			border: 1px solid #ddd;
			transition: transform 0.2s,box-shadow 0.3s;
        }

		.image-box:hover{
			cursor: pointer;
			box-shadow: 0 8px 15px rgba(0,0,0,0.15);
			transform:translateY(-2px);
		}
		.image-box.active {
			border-radius: 5px;
			box-shadow: 0 8px 15px rgba(0,178,255,.28);
			border: 1px solid #00b2ff;
			transform:translateY(-2px);
		}
        .image {
            /*width: 100%;*/
            height: 200px;
			background: white;
        }

        .bottom {
            padding: 5px 5px;
			text-align: center;
        }
        .bottom p {
            margin: 0px;
			font-size: 13px;
			font-weight: bold;
        }
		.image-box .el-radio {
			position: absolute;
    		right: 10px;
    		top: 10px;
		}
		.image-box .el-radio .el-radio__label {
			display: none;
		}
		.image-box .el-radio .el-radio__inner {
			height: 20px;
			width: 20px;
		}
		.el-dialog {
			border-radius: 7px;	

		}
		
		.el-dialog__body{
			padding: 0 15px 16px;
			text-align:center;
		}
		.el-dialog__header {
			padding: 20px 32px 10px;
			font-weight: unset;
			border-bottom: 1px solid #ddd;
			margin-bottom: 12px;
			text-align: center;
		}
		.el-tab-pane{
			overflow: auto;
			padding-bottom: 20px;
		}

		.material-box {
			width: 150px;
			height: 150px;
			border-radius: 5px;
			margin: 3px;
			overflow: hidden;
			display: inline-block;
			position: relative;
		}
		.material-box img {
			height: 100%;
			width: 100%;
		}
		.material-add {
			border: 1px dashed #c0ccda;
			text-align: center;
			cursor: pointer;
		}
		.material-add i {
			font-size: 30px;
			line-height: 140px;
			color: #c0ccda;
		}
		.material-del {
			height: 100%;
			width: 100%;
			background-color: rgba(0, 0, 0, 0.7);
			position: absolute;
			top: 0;
			left: 0;
			z-index: 100;
			text-align: center;
			color: white;
			opacity: 0;
			transition:opacity 0.5s;
		}
		.material-del:hover{
			opacity: 1;
		}
		.material-del i {
		    margin: 60px auto;
			font-size: 20px;
			cursor: pointer;
			color:#fff;
		}

	</style>
</head>

<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-steps :active="curStep" finish-status="success" simple>
					<el-step v-for="(step, index) in processSteps" :title="step" :key="index"></el-step>
				</el-steps>
			</el-header>
			<el-main>
				<%-- 内容编辑 --%>
				<div v-show="curStep==0">
					<h3>填写海报编辑要求</h3>
					<br />
					<el-row :gutter="20">
						<el-col :span="4">
							<h4>主题</h4>
						</el-col>
						<el-col :span="19">
							<el-input v-model="title" placeholder="发布内容主题"></el-input>
						</el-col>
					</el-row>
					<%-- 编辑要求 --%>
					<el-row :gutter="20">
						<el-col :span="4">
							<h5>海报制作要求</h5>
						</el-col>
						<el-col :span="19">
							<el-input type="textarea" :rows="8" placeholder="请在此处输入海报制作要求.." v-model="demand"></el-input>
						</el-col>
					</el-row>
					<%--图片--%>
					<el-row :gutter="20">
						<el-col :span="4">
							<h5>附加图片</h5>
						</el-col>
						<el-col :span="19">
							<el-upload multiple :action="imageUploadServer" accept="image/*" :on-success="handleUploadSuccess" :on-error="handleUploadError"
							 :on-remove="handleMaterialRemove" list-type="picture-card">
								<i class="el-icon-plus" style="line-height:5"></i>
							</el-upload>
						</el-col>
					</el-row>
					<%--视频--%>
					<el-row :gutter="20">
						<el-col :span="4">
							<h5>附加视频</h5>
						</el-col>
						<el-col :span="19">
							<el-upload multiple :action="videoUploadServer" accept="video/*" :on-remove="handleMaterialRemove" :on-success="handleUploadSuccess"
							 :on-error="handleUploadError" list-type="text">
								<el-button size="small" type="primary">点击上传</el-button>
							</el-upload>
						</el-col>
					</el-row>
					<%--音频--%>
					<el-row :gutter="20">
						<el-col :span="4">
							<h5>附加音频</h5>
						</el-col>
						<el-col :span="19">
							<el-upload multiple :action="videoUploadServer" accept="audio/*" :on-remove="handleMaterialRemove" :on-success="handleUploadSuccess"
							 :on-error="handleUploadError" list-type="text">
								<el-button size="small" type="primary">点击上传</el-button>
							</el-upload>
						</el-col>
					</el-row>
				</div>
				<%--选择终端--%>
				<div v-show="curStep==1">
					<!-- <h3>选择发布终端</h3>
					<el-table ref="multipleTable" :data="showTerminals" height="500" tooltip-effect="dark" style="width: 100%;" border
					 @selection-change="handleSelectionChange">
						<el-table-column type="selection" width="55"></el-table-column>
						<el-table-column prop="name" label="名称"></el-table-column>
						<el-table-column prop="address" label="位置信息"></el-table-column>
						<el-table-column prop="status" label="在线状态" :filters="terminalGroup.status" :filter-method="filter" filter="bottom-end"></el-table-column>
						<el-table-column prop="screenType" label="类型" :filters="terminalGroup.screenType" :filter-method="filter" filter="bottom-end"></el-table-column>
						<el-table-column prop="interaction" label="触摸类型"></el-table-column>
						<el-table-column prop="direction" label="屏幕方向" :filters="terminalGroup.direction" :filter-method="filter" filter="bottom-end"></el-table-column>
						<el-table-column prop="size" label="尺寸" :filters="terminalGroup.size" :filter-method="filter" filter="bottom-end"></el-table-column>
						<el-table-column prop="resolution" label="分辨率" :filters="terminalGroup.resolution" :filter-method="filter" filter="bottom-end"></el-table-column>
						<el-table-column prop="version" label="版本"></el-table-column>
						<el-table-column prop="ip" label="IP"></el-table-column>
					</el-table> -->
					<el-container>
						<el-header>
							<h3>选择发布终端</h3>
						</el-header>
						<el-container>
							<el-aside width="300px">
								<el-tree :data="terminalGroupTree" node-key="id" default-expand-all :expand-on-click-node="false" @node-click="onNodeClick">
									<span class="tree-node" slot-scope="{ node, data }">
										<span>{{ node.label }}</span>
									</span>
								</el-tree>
							</el-aside>
							<el-main>
								<el-table :data="showTerminals" border @selection-change="handleSelectionChange">
									<el-table-column type="selection" width="55"></el-table-column>
									<el-table-column prop="name" label="名称"></el-table-column>
									<el-table-column prop="status" label="在线状态"></el-table-column>
									<el-table-column prop="screenType" label="类型"></el-table-column>
									<el-table-column prop="interaction" label="触摸类型"></el-table-column>
									<el-table-column prop="direction" label="屏幕比列"></el-table-column>
									<el-table-column prop="resolution" label="分辨率"></el-table-column>
									<el-table-column prop="version" label="版本"></el-table-column>
									<el-table-column prop="ip" label="IP"></el-table-column>
								</el-table>
							</el-main>
						</el-container>
					</el-container>
				</div>
				<%--审核人--%>
				<div v-show="curStep==2">
					<h3>编辑审核人（审核顺序为添加顺序）</h3>
					<div style="text-align: center">
						<el-transfer style="text-align: left; display: inline-block;" filterable :filter-method="exUserFilterMethod"
						 filter-placeholder="请输入关键字" :titles="['可选审核人', '已选审核人']" v-model="selectedExUser" target-order="push" :data="exUsers">
						</el-transfer>
					</div>
				</div>
				<%-- 完成 --%>
				<div v-show="curStep > 2">
					<el-card style="height: 300px; width: 350px; margin: 50px auto; text-align: center;">
						<span class="el-icon-success" style="font-size: 100px; color: green; margin: 50px auto; display: block;"></span>
						<el-button round type="primary" @click="refresh()">继续添加内容</el-button>
						<el-button round type="success" @click="viewProcess()">查看进度</el-button>
					</el-card>
				</div>
			</el-main>
			<el-footer style="text-align:center; margin:50px;">
				<el-button type="primary" @click="preStep" v-show="curStep > 0 && curStep <= 2">上一步</el-button>
				<el-button type="primary" @click="nextStep" v-show="curStep < 2">下一步</el-button>
				<el-button type="primary" @click="commit" v-show="curStep == 2">提交</el-button>
			</el-footer>
		</el-container>

		<el-dialog title="素材验证" :visible="commitMessage.show" :show-close="false" width="300px">
			<p style="font-size: 13px; margin: 5px 20px; color: #2b2b2b" v-for="m in commitMessage.message">{{m}}</p>
			<div style="text-align:center;"><span class="el-icon-loading"></span></div>
		</el-dialog>
	</div>


	<script type="module">
		import serverConfig from '/environment/resourceUploadConfig.jsp'

		window.onFocus = function () {
			window.location.reload()
		}
		window.onbeforeunload = function () {
			return "还没有完成,确认退出吗?"
		}

		window.app = new Vue({
			el: '#app',
			data: {
				processSteps: ['海报要求填写', '选择发布终端', '编辑审核人', '提交完成'],
				curStep: 0,
				commitCheckMsgVisible: true,
				type: 7,
				title: '',
				material: [],
				terminals: [],
				showTerminals: [],
				terminalGroup: {},
				selectedTerminals: [],
				selectedExUser: [],
				exUsers: [],
				commitMessage: {
					show: false,
					message: [],
				},
				demand: '',
				imageUploadServer: serverConfig.getUrl('/image'),
				videoUploadServer: serverConfig.getUrl('/video'),
				terminalGroupTree: []
			},
			methods: {
				nextStep() {
					if (commitCheck()) {
						this.curStep++
					}
				},
				preStep() {
					this.curStep--
				},
				handleMaterialRemove(file, fileList) {
					for (let i = 0; i < this.material.length; i++) {
						if (file.uid == this.material[i].uid) {
							this.material.splice(i, 1);
						}
					}
				},
				handleUploadSuccess(response, file, fileList) {
					if (response.state) {
						this.material.push({
							uid: file.uid,
							name: response.original,
							type: response.type,
							url: response.url,
							isFile: true
						})
					} else {
						this.$message('上传失败,请重新选择上传.')
					}
				},
				handleUploadError(err, file, fileList) {
					this.$notify('上传失败,请重新选择上传.')
				},
				commit() {
					if (!commitCheck()) {
						return;
					}
					this.$confirm('提交后将不能再修改，确认提交？')
						.then(_ => {
							var content = {};
							content.type = this.type;
							content.title = this.title;
							//附加素材
							content.material = this.material;
							//终端
							content.terminals = this.selectedTerminals;
							//审核人
							content.exUsers = [];
							for (var i in this.selectedExUser) {
								for (var j in this.exUsers) {
									if (this.selectedExUser[i] == this.exUsers[j].key) {
										content.exUsers.push(this.exUsers[j]);
										break;
									}
								}
							}
							//节目要求
							content.demand = this.demand
							commit(content);
						})
						.catch(_ => { });
				},
				showTemplates() {
					if (this.templates.length > 0) {
						this.templateDialogVisible = true;
						return;
					}
					loadTemplate(() => {
						this.templateDialogVisible = true;
					})
				},
				getTemplates(index, indexPath) {
					console.log(index, indexPath);
				},
				templateSelect(template) {
					this.selectedTemplate = template.content;
					this.selectedTemplatePreview = template.previewPicture
				},
				writeTemplate() {
					this.templateDialogVisible = false;
					ue.setContent(this.selectedTemplate);
				},
				handleSelectionChange(selected) {
					this.selectedTerminals = selected;
				},
				filter(value, row) {
					let tv = value.split(':')
					return row[tv[0]] === tv[1];
				},
				exUserFilterMethod(query, item) {
					if (!query) {
						return true;
					} else {
						return item.label.indexOf(query) > -1;
					}

				},
				addAddition() {
					this.type = 3;
					this.additionDialogVisible = false;
				},
				filterTemplate() {
					this.showTemplateArr = {}
					Object.keys(this.templateArr).forEach(key => {
						this.showTemplateArr[key] = []
						this.templateArr[key].forEach((item => {
							if (!this.keyword || item.desc.indexOf(this.keyword) >= 0) {
								this.showTemplateArr[key].push(item)
								this.activeCategory = key
							}
						}))

					})
				},
				startTemplate(id, programTemplateId, programTemplateCategoryId) {
					this.selectedTemplateId = id
					this.selectedProgramTemplateId = programTemplateId
					this.selectedProgramTemplateCategoryId = programTemplateCategoryId
				},
				viewProcess() {
					if (parent.addTab) {
						parent.addTab({ menuId: 29, name: '正在审核', url: '/publish/process' })
					} else {
						window.location.href = '/publish/process'
					}

				},
				refresh() {
					window.location.reload()
				},
				materialSelected(data) {
					let coverUrl
					data.forEach(item => {
						coverUrl = item.type.startsWith('audio') ? "/assets/img/timg.png" : serverConfig.getUrl(item.coverUrl)
						this.material.push({
							uid: item.materialId,
							name: item.name,
							type: item.type,
							coverUrl: coverUrl,
							url: serverConfig.getUrl(item.url),
							content: item.content,
							isFile: item.type == 'text' ? false : true
						})
					})
				},
				materialSelect() {
					this.materialExplorerShow = true
				},
				materialDelete(uid) {
					for (var i in this.material) {
						if (this.material[i].uid == uid) {
							this.material.splice(i, 1)
						}
					}
				},
				screenDirectionChange(val) {
					if (val == '竖屏') {
						app.resolution = '1080x1920'
					} else {
						app.resolution = '1920x1080'
					}
				},
				onNodeClick(data, node, component) {
					loadGroupTerminals(data)
				}

			}
		});
		//编辑器

		init()

		function init() {
			getTerminals()
			getExUsers()
			getTerminalGroupTree()
		}

		function commit(postData) {
			let url = '/publish/add'
			$.ajax({
				type: 'POST',
				url: url,
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(postData),
				success: function (reps) {
					if (reps.status) {
						app.curStep += 2;
					} else {
						app.$message.error('添加失败...');
					}
				},
				error: function (err) {
					app.$message.error('系统错误.');
				}
			});
		}

		function commitCheck() {
			var msgs = [];
			if (app.curStep == 0) {
				if (!app.title) {
					msgs.push('* 请填写主题.');
				}
				if (!app.demand) {
					msgs.push('* 请填写制作要求.');
				}
			} else if (app.curStep == 1) {
				if (app.selectedTerminals.length <= 0) {
					msgs.push('* 请选择要发布内容的终端.');
				}
			} else if (app.curStep == 2) {
				if (app.selectedExUser.length <= 0) {
					msgs.push('* 请选择内容审核人.');
				}
			}
			if (msgs.length > 0) {
				checkAlert(msgs);
				return false;
			} else {
				return true;
			}


		}

		function checkAlert(msgs) {
			var html = '';
			for (let i in msgs) {
				html += '<p style="font-size: 13px; color: red">' + msgs[i] + '</p>';
			}
			app.$alert(html, '警告', {
				dangerouslyUseHTMLString: true
			});
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
					app.$message('系统错误!');
				}
			});
		}

		function postJson(url, data, callback) {
			$.ajax({
				type: 'POST',
				url: url,
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(data),
				success: function (reps) {
					if (!reps.status) {
						app.$message.error(reps.msg)
					} else {
						callback(reps.data)
					}

				},
				error: function (err) {
					app.$message.error('系统错误, 请联系管理员')
				}
			});
		}

		function getTerminals() {
			const screenType = {
				'1': '一体机',
				'2': '播放盒+显示屏',
				'3': '播放盒+投影仪'
			}
			postJson('/terminal/basic/queryInfo/1-10000', {}, reps => {
				app.terminals = []
				app.showTerminals = []
				app.terminalGroup = {
					size: [],
					direction: [],
					screenType: [],
					resolution: [],
					status: []

				}
				let size, direction, type, resolution, status
				reps.list.forEach(item => {
					app.terminals.push({
						id: item.id,
						name: item.name,
						code: item.code,
						size: item.size,
						direction: item.rev,
						interaction: item.typ,
						screenType: screenType[item.typeId],
						position: item.loc,
						resolution: item.ratio,
						ip: item.ip,
						mac: item.mac,
						regDate: item.resTime,
						version: item.ver,
						status: item.online == 1 ? '在线' : '离线',
						address: item.addr
					})
					if (item.online == 1) {
						app.showTerminals.push({
							id: item.id,
							name: item.name,
							code: item.code,
							size: item.size,
							direction: item.rev,
							interaction: item.typ,
							screenType: screenType[item.typeId],
							position: item.loc,
							resolution: item.ratio,
							ip: item.ip,
							mac: item.mac,
							regDate: item.resTime,
							version: item.ver,
							status: '在线',
							address: item.addr
						})
					}
					size = item.size
					addFilterItem(app.terminalGroup.size, size, "size:" + size)
					direction = item.rev
					addFilterItem(app.terminalGroup.direction, direction, "direction:" + direction)
					type = screenType[item.typeId]
					addFilterItem(app.terminalGroup.screenType, type, "screenType:" + type)
					resolution = item.ratio
					addFilterItem(app.terminalGroup.resolution, resolution, "resolution:" + resolution)
					status = item.online == 1 ? '在线' : '离线'
					addFilterItem(app.terminalGroup.status, status, "status:" + status)
				})
			})
		}

		function addFilterItem(filter, text, value) {
			let has = false
			for (let i = 0; i < filter.length; i++) {
				if (filter[i].text == text && filter[i].value == value) {
					has = true
					break
				}
			}
			if (!has) {
				filter.push({
					text: text,
					value: value
				})
			}
		}

		function getExUsers() {
			get('/publish/examineUsers', reps => {
				if (reps.status) {
					app.exUsers = []
					reps.data.data.forEach(item => {
						app.exUsers.push({
							key: item.userId,
							id: item.userId,
							label: item.username + '(' + item.mobile + ')'
						})
					})
					app.selectedExUser = [<%=userId %>]
				}
			})
		}

		function commitMessage(callback) {
			app.commitMessage.show = true
			let message = [
				'敏感词检测...',
				'完成, 未发现敏感词',
				'提取素材文件MD5...',
				'素材文件MD5提取完成',
				'MD5校验...',
				'校验完成',
				'正在提交...',
				'提交完成.'
			]
			let index = 1;
			app.commitMessage.message = []
			app.commitMessage.message.push(message[0])
			let iterval = setInterval(() => {
				app.commitMessage.message.push(message[index])
				index++
				if (index == message.length) {
					app.commitMessage.show = false
					clearInterval(iterval)
					if (callback) {
						callback()
					}

				}
			}, 1000);
		}

		function getTerminalGroupTree() {
			get('/terminal/m/group/tree', reps => {
				if(reps.status) {
					app.terminalGroupTree = reps.data
					if(app.terminalGroupTree.length > 0) {
						loadGroupTerminals(app.terminalGroupTree[0])
					}
				}
			})
		}
		function getChildIds(group) {
			let ids = []
			ids.push(group.id)
			if(group.children && group.children.length > 0) {
				for(let i in group.children) {
				ids = ids.concat(getChildIds(group.children[i]))
				}
			}
			return ids
		}
		function loadGroupTerminals(group) {
			let ids = getChildIds(group)
			postJson('/terminal/m/group/terminals', ids, data => {
				let screenType = {
					'1': '一体机',
					'2': '播放盒+显示屏',
					'3': '播放盒+投影仪'
				}
				app.showTerminals = []
				data.forEach(item => {
					app.showTerminals.push({
						id: item.id,
						name: item.name,
						code: item.code,
						size: item.size,
						direction: item.rev,
						interaction: item.typ,
						screenType: screenType[item.type_id],
						position: item.loc,
						resolution: item.ratio,
						ip: item.ip,
						mac: item.mac,
						regDate: item.resTime,
						version: item.ver,
						status: item.online == 1 ? '在线' : '离线',
						address: item.addr
					})
				})
			})
		}
	</script>
</body>

</html>