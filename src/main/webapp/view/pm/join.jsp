<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>党员发展</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<style type="text/css">
	
</style>
</head>

<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20">
					<el-popover 
			  			class="margin-left-10"
						placement="bottom" 
					  	width="200" 
					  	trigger="click" >
					  	<el-button size="small" type="primary" slot="reference">
					  		<i class="el-icon-search"></i>
					  		搜索人员
					  	</el-button>
					  	<div>
							<el-row>
								<el-input size="small" clearable
									@change="queryJoinPartyOrgUser"
									v-model="joinPartyOrgUser.query.conditions.idCard" placeholder="请输入身份证号码"></el-input>
							</el-row>
					  	</div>
					</el-popover>
					<span class="margin-left-10" v-if="false">
						<el-button size="small" type="primary" @click="zidonghuachengxu_video()">开始</el-button>
					</span>
					<span style="float: right;">
	                    <el-pagination
						  	layout="total, prev, pager, next, jumper" 
			      		 	@current-change="queryJoinPartyOrgUser"
						  	:current-page.sync="joinPartyOrgUser.query.page.pageNum"
						  	:page-size.sync="joinPartyOrgUser.query.page.pageSize"
						  	:total="joinPartyOrgUser.query.page.total">
						</el-pagination>
					</span>
				</el-row>
			</el-header>
			<el-main>
				<el-table 
					row-key="id"
					size="small" 
					:data="joinPartyOrgUser.query.page.list" 
					style="width: 100%">
					<el-table-column label="入党编号" prop="id" width="100"></el-table-column>
					<el-table-column label="姓名" prop="name" width="100"></el-table-column>
					<el-table-column label="性别" prop="sex" width="100"></el-table-column>
					<el-table-column label="身份证号码" prop="idCard" width="200"></el-table-column>
					<el-table-column label="入党类型" prop="joinPartyType"></el-table-column>
					<el-table-column label="申请时间" prop="applyTime" width="200"></el-table-column>
					<el-table-column label="申请状态" prop="joinStatus" width="100"></el-table-column>
					<el-table-column label="当前进行" prop="nowStepName"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button 
								@click="openJoinPartyOrgStatusDialog(scope.row)" 
								type="text" size="small">审批 / 状态
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
		</el-container>

		<el-dialog @close="reset_join_party_org_dialog" title="申请入党" :visible.sync="joinPartyOrgUser.joinStatus.dialog" width="80%">
			<!-- <el-form size="small" :model="" status-icon :rules="" 
				ref="" label-width="100px"> -->
				<el-row>
					<el-steps :active="joinPartyOrgUser.joinStatus.stepNum" finish-status="success">
					  	<template v-for="item in joinPartyOrgUser.joinStatus.process">
					  		<el-step :title="item.name" :status="item.status"></el-step>
					  	</template>
					</el-steps>
				</el-row>

				<!-- 公共提示信息 -->
				<span style="font-size: 12px;">
					<!-- thisStepInfoNull代表没有查询到这个步骤信息 -->
					<el-row v-if="joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo != null">	
						<p>
							提交时间：{{joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepTime}}
						</p>
						<p>
							希望加入的组织：{{joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.orgInfoName}}
						</p>
						<p>
							加入方式：{{joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.joinPartyType}}
						</p>
						<p>审核状态：
							<span :style="joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatus == 'success' ? 
								'color: green; font-weight: bold;' : joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatus == 'wait' ? 
								'color: #808080; font-weight: bold;' : joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatus == 'error' ? 
								'color: red; font-weight: bold;' : 'color: black; font-weight: bold;'">
								{{joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatus}}
							</span>
						</p>
						<p v-if="joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatusReason != null && 
								joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatusReason != ''">
							附加消息：{{joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepStatusReason}}
						</p>
					</el-row>
					<template v-if="joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo == null">
						<p>请等待用户将资料上传完毕</p>
					</template>

					<el-row v-if="joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo != null 
						&& joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepFiles != null
						&& joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepFiles.length > 0">
						<p style="margin-bottom: 10px;">以下是上传的附加材料：</p>
						<template v-for="item in joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.stepFiles">
							<div style="width: 80px; height: 150px; float: left; margin-right: 10px; margin-bottom: 10px;">
								<a target="_blank" style="text-decoration:none; color: dimgray;" :href="'http://192.168.1.8:3000' + item.filePath" :title="item.fileTitle">
									<div style="text-align: center">
										<img 
											:src="getFileTypeImg(item.fileType)" style="width: 60px; height: 80px;" />
									</div>
									<div>
										<p style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">
											{{item.fileTitle}}
										</p>
									</div>
								</a>
							</div>
						</template>
					</el-row>
				</span>

				<el-row style="margin-bottom: 10px;">
					<el-button v-if="joinPartyOrgUser.joinStatus.stepNum > 0" 
						size="small" @click="joinStatusStepSet('s')">查看上一步结果
					</el-button>
					<el-button v-if="joinPartyOrgUser.joinStatus.stepNum < joinPartyOrgUser.joinStatus.stepNumNow" 
						size="small" @click="joinStatusStepSet('x')">查看下一步结果
					</el-button>
					<el-button v-if="joinPartyOrgUser.joinStatus.stepNum == joinPartyOrgUser.joinStatus.stepNumNow
						&& joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.joinStatus == 'wait'" 
						size="small" type="danger" @click="openJoinPartyOrgStepReasonDialog('error')">拒绝入党申请
					</el-button>
					<el-button v-if="joinPartyOrgUser.joinStatus.stepNum == joinPartyOrgUser.joinStatus.stepNumNow
						&& joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.joinStatus == 'wait'" 
						size="small" type="primary" @click="openJoinPartyOrgStepReasonDialog('success')">本次通过
					</el-button>
				</el-row>
			<!-- </el-form> -->
		</el-dialog>

		<el-dialog @close="resetStepReason" :visible.sync="joinPartyOrgUser.update.joinPartyOrgStepReasonDialog">
			<el-input
				style="margin-top: 10px; margin-bottom: 10px;"
				type="textarea"
				:autosize="{ minRows: 4}"
				placeholder="有什么想说的吗？（可不填）"
				v-model="joinPartyOrgUser.update.setStepStatus.stepReason">
			</el-input>
			<el-button	
				style="margin-bottom: 10px;"
				size="small" type="primary" @click="joinPartyOrgUser.update.setStepStatus.stepStatus == 'error' ? 
					refuseJoinPartyOrgStep() : adoptJoinPartyOrgStep()">下一步</el-button>
		</el-dialog>
	</div>
</body>

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			commons: {
				innerScreenHeight: 0	//屏幕高度
			},
			joinPartyOrgUser: {	//申请入党的用户
				query: {
					page: {
						pageNum: 1,		/* 当前页 */
						pageSize: 10,	/* 页面大小 */
						total: 0,
						list: []
					},
					conditions: {	//搜索条件
						idCard: null
					}
				},
				update: {
					joinPartyOrgStepReasonDialog: false,
					setStepStatus: {
						stepStatus: null,
						stepReason: null
					}
				},
				delete: {

				},
				insert: {

				},
				joinStatus: {
					dialog: false,	/*申请入党弹窗*/
					stepNum: 1,	//步骤条开始
					stepNumNow: 1,	//当前进行到第几步
					process: [],	//所有的步骤信息
					getJoinPartyOrgProcessOver: false,	//是否查询完成
					joinPartyOrgStepInfo: null,
					joinPartyOrgUserInfo: {
						joinStatus: null
					}
				}
			},
			zidonghuachengxu_videoVariable: {
				joinUserInfo: null
			}
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.queryJoinPartyOrgUser();
		},
		methods: {
			zidonghuachengxu_video() {	//为了录像
				var obj = this;
				setTimeout(() => {
					obj.$message({
						message:'打开审核窗口',
						duration: 1000
					});
					obj.zidonghuachengxu_videoVariable.joinUserInfo = obj.joinPartyOrgUser.query.page.list[0];
					setTimeout(() => {
						obj.openJoinPartyOrgStatusDialog(obj.zidonghuachengxu_videoVariable.joinUserInfo);
						setTimeout(() => {
							if (obj.zidonghuachengxu_videoVariable.joinUserInfo.nowStep == 1) {
								obj.$message({
									message:'通过提出入党申请',
									duration: 1000
								});
							} else if (obj.zidonghuachengxu_videoVariable.joinUserInfo.nowStep == 2) {
								obj.$message({
									message:'通过成为积极分子',
									duration: 1000
								});
							} else if (obj.zidonghuachengxu_videoVariable.joinUserInfo.nowStep == 3) {
								obj.$message({
									message: '通过申请党支部的申请',
									duration: 1000
								});
							} else if (obj.zidonghuachengxu_videoVariable.joinUserInfo.nowStep == 4) {
								obj.$message({
									message: '通过申请党支部上级谈话，审查',
									duration: 1000
								});
							}
							setTimeout(() => {
								obj.openJoinPartyOrgStepReasonDialog('success');
								setTimeout(() => {
									obj.$message({
										message:'填写此步操作的备注',
										duration: 1000
									});
									setTimeout(() => {
										obj.joinPartyOrgUser.update.setStepStatus.stepReason = "这是此步操作的意见，这是此步操作的意见，这是此步操作的意见，这是此步操作的意见"
										setTimeout(() => {
											obj.$message({
												message:'通过申请',
												duration: 1000
											});
											setTimeout(() => {
												obj.changeJoinPartyOrgStepStatus();
											}, 1300);
										}, 1300);
									}, 1300);
								}, 1300);
							}, 1300);
						}, 1300);
					}, 1300);
				}, 1300);
			},
			openJoinPartyOrgStepReasonDialog(stepStatus) {
				var obj = this;
				obj.joinPartyOrgUser.update.setStepStatus.stepStatus = stepStatus;
				obj.joinPartyOrgUser.update.joinPartyOrgStepReasonDialog = true;
			},
			changeJoinPartyOrgStepStatus(){	//变更此步骤的状态
				var obj = this;
				url = "/join/step/updateJoinPartyOrgSteps";
				var t = {
					id: obj.joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.id,
					stepStatus: obj.joinPartyOrgUser.update.setStepStatus.stepStatus,
					stepStatusReason: obj.joinPartyOrgUser.update.setStepStatus.stepReason == null 
						|| obj.joinPartyOrgUser.update.setStepStatus.stepReason == "" ? 
						obj.joinPartyOrgUser.update.setStepStatus.stepStatus == 'error' ? '拒绝申请' : '审核通过' 
						: obj.joinPartyOrgUser.update.setStepStatus.stepReason,
					joinId: obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.id,
					processId: obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.nowStep,
					isFile: obj.joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo.isFile
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.$message({
							type: 'success',
							message: '操作成功'
						});
						obj.joinPartyOrgUser.joinStatus.dialog = false;
						obj.joinPartyOrgUser.update.joinPartyOrgStepReasonDialog = false;
						obj.queryJoinPartyOrgUser();
					} 
				})
			},
			refuseJoinPartyOrgStep() {	//入党此步骤拒绝
				var obj = this;
				obj.$confirm(
					'拒绝此人的入党申请', 
					'提示', 
					{
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
		        	}
		        ).then(function(){
	        		obj.changeJoinPartyOrgStepStatus();
		        }).catch(function(){
		        	obj.$message({
			            type: 'info',
			            message: '已取消此操作'
			        });  
		        });
			},
			adoptJoinPartyOrgStep() {	//入党此步骤通过
				var obj = this;
				obj.$confirm(
					'入党申请本步骤通过', 
					'提示', 
					{
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
		        	}
		        ).then(function(){
	        		obj.changeJoinPartyOrgStepStatus();
		        }).catch(function(){
		        	obj.$message({
			            type: 'info',
			            message: '已取消此操作'
			        });  
		        });
			},
			getFileTypeImg(fileType) {
				if (fileType == "doc") {
					return "/view/pm/fileTypeImg/doc.png";
				} else if (fileType == "docx") {
					return "/view/pm/fileTypeImg/docx.png";
				} else if (fileType == "jpg") {
					return "/view/pm/fileTypeImg/jpg.png";
				} else if (fileType == "xls" || fileType == "xlsx") {
					return "/view/pm/fileTypeImg/xls.png";
				} else if (fileType == "ppt" || fileType == "pptx") {
					return "/view/pm/fileTypeImg/ppt.jpg";
				} 
			},
			getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
				var obj = this;
				obj.commons.innerScreenHeight = window.innerHeight;
				obj.joinPartyOrgUser.query.page.pageSize = parseInt((obj.commons.innerScreenHeight - 100)/50);
			},
			queryJoinPartyOrgUser() {	//查询申请入党的用户及申请信息
				var obj = this;
				var url = "/join/user/queryJoinPartyOrgUsers";
				var t = {
					pageNum: obj.joinPartyOrgUser.query.page.pageNum,
					pageSize: obj.joinPartyOrgUser.query.page.pageSize,
					isHistory: 0,
					idCard: obj.joinPartyOrgUser.query.conditions.idCard
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.joinPartyOrgUser.query.page.pageNum = 1;
							obj.joinPartyOrgUser.query.page.total = 0;
							obj.joinPartyOrgUser.query.page.list = new Array();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.joinPartyOrgUser.query.page = data.data;
						}
					}
					
				})
			},
			openJoinPartyOrgStatusDialog(joinPartyOrgUserInfo) {	//申请入党状态弹窗
				var obj = this;
				obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo = joinPartyOrgUserInfo;

				var url = "/org/process/queryOrgOjp";
				var t = {
					orgId: obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.joinOrgId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.joinPartyOrgUser.joinStatus.process = data.data;
						//当前用户进行的流程
						let url = "/join/step/queryUserJoinPartyOrgSteps"
						let t = {
							userId: obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.baseUserId,
							isHistory: 0
						}
						$.post(url, t, function(_data, status){
							if (_data.code == 200) {
								let userProcess = _data.data;
								for (var i = 0; i < obj.joinPartyOrgUser.joinStatus.process.length; i++) {	
									//增加status属性，给步骤条设置状态
									var _process = {id: null, name: null, orgId: null, processId: null, indexNum: null, isFile: 0, status: 'wait'};
									//给已进行的步骤设置状态
									if (userProcess != null && userProcess != undefined && userProcess[i] != null) {
										_process.status = userProcess[i].stepStatus == 'wait' ? 'process' : userProcess[i].stepStatus;
									}
									_process.id = obj.joinPartyOrgUser.joinStatus.process[i].id;
									_process.name = obj.joinPartyOrgUser.joinStatus.process[i].name;
									_process.orgId = obj.joinPartyOrgUser.joinStatus.process[i].orgId;
									_process.processId = obj.joinPartyOrgUser.joinStatus.process[i].processId;
									_process.indexNum = obj.joinPartyOrgUser.joinStatus.process[i].indexNum;
									_process.isFile = obj.joinPartyOrgUser.joinStatus.process[i].isFile;
									obj.joinPartyOrgUser.joinStatus.process[i] = _process;
									//设置步骤条步骤，根据当前进行的步骤取得进行到第几步
									if (_process.processId == obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.nowStep) {
										obj.joinPartyOrgUser.joinStatus.stepNum = _process.indexNum;	//设置当前步骤，默认1
										obj.joinPartyOrgUser.joinStatus.stepNumNow = _process.indexNum;
									}
								}
								obj.joinStatusStepSet('z');
							}
						})
					} 
				})
			},
			joinStatusStepSet(step) {	//设置步骤条
				var obj = this;

				if (step == 's') {
					obj.joinPartyOrgUser.joinStatus.stepNum--;
				} else if (step == 'x') {					
					obj.joinPartyOrgUser.joinStatus.stepNum++;
				}
				obj.joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo = null;

				//查询出此步骤的信息
				let url = "/org/process/queryOrgOjp";
				let t = {
					orgId: obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.joinOrgId,
					indexNum: obj.joinPartyOrgUser.joinStatus.stepNum
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						let url = "/join/step/queryUserJoinPartyOrgSteps"
						let t = {
							userId: obj.joinPartyOrgUser.joinStatus.joinPartyOrgUserInfo.baseUserId,
							processId: data.data[0].processId
						}
						$.post(url, t, function(data, status){
							if (data.code == 200 && data.data != null && data.data != undefined) {
								obj.joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo = data.data[0];
							}
							obj.joinPartyOrgUser.joinStatus.dialog = true;
						})
					}
				})
			},
			reset_join_party_org_dialog() {
				let obj = this;
				obj.joinPartyOrgUser.joinStatus.joinPartyOrgStepInfo = null;
			},
			resetStepReason() {
				var obj = this;
				obj.joinPartyOrgUser.update.setStepStatus.stepReason = null;
			}
		}
	});

	window.onFocus = function () {
		window.location.reload();
	}
</script>

</html>