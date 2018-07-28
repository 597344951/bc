<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ page import="org.apache.shiro.SecurityUtils,org.apache.shiro.subject.Subject,com.zltel.broadcast.um.bean.SysUser" %>

<%
	String title = request.getParameter("title");
	String programId = request.getParameter("programId");
	String startStep = request.getParameter("startStep");
	String reAdd = request.getParameter("reAdd");
	String eventPlanId = request.getParameter("eventPlanId");
	title = title == null ? "" : title;
	programId = programId == null ? "-1" : programId;
	startStep = startStep == null ? "0" : startStep;
	reAdd = reAdd == null ? "false" : reAdd;
	eventPlanId = eventPlanId == null ? "-1" : eventPlanId;
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
	<%@include file="/include/ueditor.jsp" %>
	<style>
		body {
			width: 98%;
			margin: 20px auto;
		}
		/* .el-container {
			height: 100%;
		} */
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
		.el-radio {
			position: absolute;
    		right: 10px;
    		top: 10px;
		}
		.el-radio__label {
			display: none;
		}
		.el-radio__inner {
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
					<el-step v-for="(s, index) in processSteps" :title="s" v-if="index >= startStep"></el-step>
					<%-- <el-step title="选择模板"></el-step>
					<el-step title="编辑内容"></el-step>
					<el-step title="选择发布终端"></el-step>
					<el-step title="编辑审核人"></el-step>
					<el-step title="提交完成"></el-step> --%>
				</el-steps>
			</el-header>
			<el-main v-loading="loading">
				<div v-show="step==0">
					<h3>选择模板</h3>
					<el-row :gutter="20">
						<%-- <el-col :span="3">
							<el-select
									size="medium"
									v-model="screenDirection"
									placeholder="节目类型">
								<el-option label="横屏" value="横屏"></el-option>
								<el-option label="竖屏" value="竖屏"></el-option>
							</el-select>
						</el-col> --%>
						<el-col :span="4">
							<el-input size="medium" v-model="keyword" placeholder="输入关键字"></el-input>
						</el-col>
						<el-col :span="2">
							<el-button size="medium" type="primary" icon="el-icon-search" @click="filterTemplate"></el-button>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col>
							<el-tabs tab-position="left" style="height: 450px; overflow: auto" v-model="activeCategory">
								<el-tab-pane label="最近使用 (1) " name="最近使用">
									<div class="image-box" v-bind:class="{active: selectedTemplateId==0}" @click="startTemplate('0')">
										<el-radio v-model="selectedTemplateId" label="0"></el-radio>
										<div class="image"></div>
										<div class="bottom">
											<p>空白模板</p>
										</div>
									</div>
								</el-tab-pane>
								<el-tab-pane label="常用模板 (1) " name="常用模板">
									<div class="image-box" v-bind:class="{active: selectedTemplateId==0}" @click="startTemplate('0')">
										<el-radio v-model="selectedTemplateId" label="0"></el-radio>
										<div class="image"></div>
										<div class="bottom">
											<p>空白模板</p>
										</div>
									</div>
								</el-tab-pane>
								<el-tab-pane :label="key + ' (' + value.length + ') '" :name="key" v-for="(value, key) in showTemplateArr">
									<div class="image-box" v-for="t in value" @click="startTemplate(t.tpId, t.programTemplate, t.categoryId)" v-bind:class="{active: selectedTemplateId==t.tpId}">
										<el-radio v-model="selectedTemplateId" :label="t.tpId"></el-radio>
										<img :src="t.previewPicture" class="image">
										<div class="bottom">
											<p>{{t.desc}}</p>
										</div>
									</div>
								</el-tab-pane>
							</el-tabs>
						</el-col>		
					</el-row>
				</div>
				<%-- 内容编辑 --%>
				<div v-show="step==1">
					<h3>内容/活动简单编辑</h3>
					<el-row :gutter="20">
						<el-col :span="4"><h4>主题 <%-- [<el-button type="text" @click="additionDialogVisible = true">补充</el-button>] --%></h4></el-col>
						<el-col :span="19"><el-input v-model="title" placeholder="发布内容主题"></el-input></el-col>
					</el-row>
					<%-- <el-row :gutter="20">
						<el-col :span="4"><h4>播放周期</h4></el-col>
						<el-col :span="19">
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
					</el-row> --%>
					<el-row :gutter="20">
						<el-col :span="4"><h4>播放设置</h4></el-col>
						<el-col :span="19">
							<%-- <el-select
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
							<el-input style="width: 18%;" v-model="playLength" placeholder="播放时长（秒）"></el-input> --%>
							<el-checkbox-group v-model="screenDirection" style="display: inline-block;">
								<el-checkbox v-for="d in screenDirections" :label="d" :key="d">{{d}}</el-checkbox>
							</el-checkbox-group>
							（勾选发布节目的显示类型：横屏/竖屏）
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="4">
							<h4>内容 <%-- [<el-button type="text" @click="showTemplates">选取模板</el-button>] --%></h4>
						</el-col>
						<el-col :span="19">
							<div><script id="templateText" name="templateText" style="height:300px;" type="text/plain"></script></div>
						</el-col>
					</el-row>
					<%-- 编辑要求 --%>
					<el-row :gutter="20">
						<el-col :span="4"><h5>节目制作要求</h5></el-col>
						<el-col :span="19">
							<el-input type="textarea" :rows="4" placeholder="请在此处输入节目制作要求（列如：节目编辑要求、节目发布时间等等）" v-model="demand"></el-input>
						</el-col>
					</el-row>
					<%-- 附加素材 --%>
					<el-row :gutter="20">
						<el-col :span="4"><h5>附加素材</h5></el-col>
						<el-col :span="19">
							<div class="material-box" v-for="m in material">
								<img :src="m.coverUrl">
								<div class="material-del">
									<i class="el-icon-delete" @click="materialDelete(m.uid)"></i>
								</div>
							</div>
							<div class="material-box material-add" @click="materialSelect">
								<i class="el-icon-plus"></i>
							</div>
						</el-col>
					</el-row>
					<%--图片--%>
					<%-- <el-row :gutter="20">
						<el-col :span="4"><h5>素材提交（图片）</h5></el-col>
						<el-col :span="19">
							<el-upload
									multiple
									action="/material/uploadImage"
									accept="image/*"
									:on-success="handleUploadSuccess"
									:on-error="handleUploadError"
									:on-remove="handleMaterialRemove"
									list-type="picture-card">
								<i class="el-icon-plus" style="line-height:5"></i>
							</el-upload>
						</el-col>
					</el-row> --%>
					<%--视频--%>
					<%-- <el-row :gutter="20">
						<el-col :span="4"><h5>素材提交（视频）</h5></el-col>
						<el-col :span="19">
							<el-upload
									multiple
									action="/material/uploadVideo"
									accept="video/*"
									:on-remove="handleMaterialRemove"
									:on-success="handleUploadSuccess"
									:on-error="handleUploadError"
									list-type="text">
								<el-button size="small" type="primary">点击上传</el-button>
							</el-upload>
						</el-col>
					</el-row> --%>
					<%--音频--%>
					<%-- <el-row :gutter="20">
						<el-col :span="4"><h5>素材提交（音频）</h5></el-col>
						<el-col :span="19">
							<el-upload
									multiple
									action="/material/uploadAudio"
									accept="audio/*"
									:on-remove="handleMaterialRemove"
									:on-success="handleUploadSuccess"
									:on-error="handleUploadError"
									list-type="text">
								<el-button size="small" type="primary">点击上传</el-button>
							</el-upload>
						</el-col>
					</el-row> --%>
				</div>
				<%--选择终端--%>
				<div v-show="step==2">
					<h3>选择发布终端</h3>
					<el-table
							ref="multipleTable"
							:data="showTerminals"
							height="500"
							tooltip-effect="dark"
							style="width: 100%;"
							border
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
					</el-table>
				</div>
				<%--审核人--%>
				<div v-show="step==3">
					<h3>编辑审核人（审核顺序为添加顺序）</h3>
					<div style="text-align: center">
						<el-transfer
								style="text-align: left; display: inline-block;"
								filterable
								:filter-method="exUserFilterMethod"
								filter-placeholder="请输入关键字"
								:titles="['可选审核人', '已选审核人']"
								v-model="selectedExUser"
								target-order="push"
								:data="exUsers">
						</el-transfer>
					</div>
				</div>
				<%-- 完成 --%>
				<div v-show="step > 3">
					<el-card style="height: 300px; width: 350px; margin: 50px auto; text-align: center;">
						<%-- 内容编辑完成.[<a href="#" @click="viewProcess()">点击跳转查看进度...</a>] --%>

						<span class="el-icon-success" style="font-size: 100px; color: green; margin: 50px auto; display: block;"></span>
						<el-button round type="primary" @click="refresh()">继续添加内容</el-button>
						<el-button round type="success" @click="viewProcess()">查看进度</el-button>
					</el-card>
				</div>
			</el-main>
			<el-footer style="text-align:right;margin-right:50px;" >
				<el-button type="primary" @click="preStep" v-show="step > startStep && step <= 3">上一步</el-button>
				<el-button type="primary" @click="nextStep" v-show="step < 3">下一步</el-button>
				<el-button type="primary" @click="commit" v-show="step == 3">提交</el-button>
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
								<el-menu-item v-for="t in item.children" :index="'' + t.tpId" @click="templateSelect(t)">{{t.title}}</el-menu-item>
							</el-submenu>
						</el-submenu>
					</el-menu>
				</el-col>
				<el-col :span="14">
					<image v-if="selectedTemplatePreview" :src="selectedTemplatePreview" class="template-preview"/>
				</el-col>
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

		<el-dialog title="素材验证" :visible="commitMessage.show" :show-close="false" width="300px">
			<p style="font-size: 13px; margin: 5px 20px; color: #2b2b2b" v-for="m in commitMessage.message">{{m}}</p>
			<div style="text-align:center;"><span class="el-icon-loading"></span></div>
		</el-dialog>

		<resource-material-explorer :display.sync="materialExplorerShow" title="素材选择" @submit="materialSelected"></resource-material-explorer>
	</div>


	<script type="module">
		import serverConfig from '/environment/resourceUploadConfig.jsp'

		window.onFocus = function() {
			window.location.reload()
		}

		window.app = new Vue({
			el: '#app',
			data: {
				eventPlanId: <%=eventPlanId%>,
				programId: <%=programId%>,
				reAdd: <%=reAdd%>,
				loading: true,
				processSteps: ['选择模板', '编辑内容', '选择发布终端', '编辑审核人', '创建提交'],
				startStep: <%=startStep%>,
				curStep: 0,
			    step: <%=startStep%>,
                templateDialogVisible: false,
                additionDialogVisible: false,
                commitCheckMsgVisible: true,
                templates:[],
				templateArr: {},
				showTemplateArr: {},
				keyword: '',
				activeCategory:'常用模板',
				selectedTemplateId: '-1',
				selectedProgramTemplateId: '',
				selectedProgramTemplateCategoryId: '',
				selectedTemplate: {},
				selectedTemplatePreview: '',
				type: 4,
				title: '<%=title%>',
				startDate: '',
				endDate:'',
				time: '',
				screenType: '0',
				screenDirections: ['竖屏', '横屏'],
				screenDirection: ['竖屏', '横屏'],
				playLength: '100',
				resolution: '1920x1080',
				week: '',
                templateText: '',
                material: [],
                terminals: [],
				showTerminals:[],
                terminalGroup: {},
                selectedTerminals: [],
				selectedExUser: [],
				exUsers:[],
                addition: {
                    participantType: 'all'
				},
				commitMessage: {
					show: false,
					message: [],
				},
				demand: '',
				materialExplorerShow: false
			},
            methods: {
			    nextStep() {
					if(commitCheck()) {
						if(this.step == 0) {
							if(this.selectedTemplateId == 0) {
								//空白, 清空
								this.selectedTemplate = ''
								ue.setContent(this.selectedTemplate)
							} else {
								for(var name in this.templateArr) {
									for(var i in this.templateArr[name]) {
										if(this.selectedTemplateId == this.templateArr[name][i].tpId) {
											this.selectedTemplate = this.templateArr[name][i].content
											ue.setContent(this.selectedTemplate)
											break
										}
									}
								}
							}
						} else if(this.step == 1) {
							app.showTerminals = []
							app.terminals.forEach(item => {
								if(app.screenDirection.indexOf(item.direction) >= 0 && item.status == '在线') {
									app.showTerminals.push(item)
								}
							})
						}
						this.step ++
						this.curStep ++
					}
				},
				preStep() {
					this.step --
					this.curStep --
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
							if(this.selectedProgramTemplateId) {
								content.programTemplateId = this.selectedProgramTemplateId
							}
							if(this.selectedProgramTemplateCategoryId) {
								content.programTemplateCategoryId = this.selectedProgramTemplateCategoryId
							}
							
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
							//节目要求
							content.demand = this.demand
							content.programId = this.programId
							if(this.eventPlanId >= 0) {
								content.eventPlanId = this.eventPlanId
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
			        if(!query) {
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
							if(!this.keyword || item.desc.indexOf(this.keyword) >= 0) {
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
					if(parent.addTab) {
						parent.addTab({menuId: 29, name: '正在审核', url: '/publish/process'})
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
					for(var i in this.material) {
						if(this.material[i].uid == uid) {
							this.material.splice(i, 1)
						}
					}
				}

			}
		});
		//编辑器
        var ue = UE.getEditor('templateText',{
            //serverUrl: '/ueditor/controller.jsp',
			serverUrl: serverConfig.getUrl('/ueditor'),
            allowDivTransToP: false,
            wordCount: false,
            elementPathEnabled: false
        });

		init()

		function init() {
			getTerminals()
			getExUsers()
			loadTemplate(()=>{
				app.loading = false
			})
		}

        function commit(postData) {
			let url
			if(app.reAdd) {
				url = '/publish/reAdd'
			} else {
				url = '/publish/add'
			}
            $.ajax({
                type:'POST',
                url: url,
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(postData),
                success: function(reps){
                    if(reps.status) {
                        //app.step += 2;
						commitMessage(() => {
							app.step += 2
							app.curStep +=2
						})
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
			if(app.step == 0) {
				if(app.selectedTemplateId == -1) {
					msgs.push('* 请选择一个模板开始.');
				}
			} else if(app.step == 1) {
                if(!app.title) {
                    msgs.push('* 请填写主题.');
                }
                /* if(!app.startDate || !app.endDate || !app.time) {
                    msgs.push('* 请完整输入内容预计发布时间.')
                } */
                if(!ue.getContent()) {
                    msgs.push('* 请选择模板或填写自定义内容.');
                }
				/* if(!app.screenType) {
					msgs.push('* 请选择终端屏幕类型.');
				}
				if(!app.playLength) {
					msgs.push('* 请输入播放时长.');
				}
				if(!app.resolution) {
					msgs.push('* 请选择分辨率.');
				} */
            } else if(app.step == 2) {
                if(app.selectedTerminals.length <= 0) {
                    msgs.push('* 请选择要发布内容的终端.');
				}
			} else if(app.step == 3) {
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


		function loadTemplate(callback) {
			get("/tpt/listTypeTree", reps => {
				if(reps.status) {
					app.templates = reps.data;
					for(var i in app.templates) {
						for(var j in app.templates[i].children) {
							getTemplateContent(app.templates[i].name, app.templates[i].children[j].name, app.templates[i].children[j]);
						}
					}
					setTimeout(() => {
						if(callback) {
							callback()
						}
					}, 1000);

				} else {
					app.$message('没有找到已定义模板 !');
				}
			});
		}

        function getTemplateContent(name1, name2, type) {
			app.templateArr[name2] = []
			app.showTemplateArr[name2] = []

            get('/tp/listByType?tpTypeId=' + type.id, reps => {
                if(reps.status) {
                    type.children = reps.data;
					reps.data.forEach((item => {
						item.previewPicture = serverConfig.getUrl(item.previewPicture)
						item.desc = item.title
						app.templateArr[name2].push(item)
						app.showTemplateArr[name2].push(item)
					}))
					
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

		function postJson(url, data, callback) {
			$.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(reps) {
					if(!reps.status) {
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
					if(item.online == 1) {
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
			for(let i=0; i<filter.length; i++) {
				if(filter[i].text == text && filter[i].value == value) {
					has = true
					break
				}
			}
			if(!has) {
				filter.push({
					text: text,
					value: value
				})
			}
		}

		function getExUsers() {
    		get('/publish/examineUsers', reps => {
				if(reps.status) {
					app.exUsers = []
					reps.data.data.forEach(item => {
						app.exUsers.push({
							key: item.userId,
							id: item.userId,
							label: item.username + '('+ item.mobile +')'
						})
					})
					app.selectedExUser = [<%=userId%>]
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
				index ++
				if(index == message.length) {
					app.commitMessage.show = false
					clearInterval(iterval)
					if(callback) {
						callback()
					}
					
				}
			}, 1000);
		}
	</script>
</body>
</html>