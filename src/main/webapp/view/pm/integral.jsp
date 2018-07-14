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
<title>党员积分管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<script type="text/javascript" src="/json/address-pca.json"></script>
<%@include file="/include/echarts.jsp"%>
<style type="text/css">
	.toolbar{
	    position: fixed;
	    top: 0;
	    background: #fff;
	    width: 100%;
	    box-shadow: 0 2px 15px 2px #ddd;
	}
	.orgInfoNameList {
		padding: 3px 0;
		font-size: 12px;
		border-bottom: 1px solid #ddd;
	}
	.orgInfoNameList:hover {
		cursor: pointer;
		background-color: rgb(231, 202 ,212);
	}
	.el-row {
		margin-bottom: 10px;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20" style="margin:0;">
					<el-popover
						placement="bottom" 
						v-show="!dis_h_v"
					  	width="200" 
					  	trigger="click" >
					  	<el-button size="small" type="primary" slot="reference">
					  		<i class="el-icon-search"></i>
					  		搜索组织
					  	</el-button>
					  	<div>
							<el-row>
								<el-input size="small" clearable
									@change="ic_manage_queryOrgInfoForIc"
									v-model="queryOrgInfoForIcCondition.orgInfoName" placeholder="请输入组织名"></el-input>
							</el-row>
					  	</div>
					</el-popover>
					<el-popover
						placement="bottom" 
						v-show="dis_h_v"
					  	width="200" 
					  	trigger="click" >
					  	<el-button size="small" type="primary" slot="reference">
					  		<i class="el-icon-search"></i>
					  		搜索记录
					  	</el-button>
					  	<div>
							<el-row>
								<el-select size="small" clearable 
										@change="ic_manage_queryPartyIntegralRecords"
										v-model="queryPartyIntegralRecordsCondition.orgId" placeholder="所在组织">
									<el-option
										v-for="item in selectBox.orgInfo_Ic"
										:key="item.orgId"
										:label="item.orgInfoName"
										:value="item.orgId">
									</el-option>
								</el-select>
							</el-row>
							<el-row>
								<el-input size="small" clearable
									@change="ic_manage_queryPartyIntegralRecords"
									v-model="queryPartyIntegralRecordsCondition.idCard" placeholder="请输入身份证号码"></el-input>
							</el-row>
					  	</div>
					</el-popover>
					<el-button-group class="margin-0-10">
                        <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                        <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                    </el-button-group>
                    <span style="float: right;" v-show="!dis_h_v">
	                    <el-pagination
						  	layout="total, prev, pager, next, jumper" 
			      		 	@current-change="ic_manage_pagerCurrentChangeForOrgInfo_Ic"
						  	:current-page.sync="ic_manage_orgInfoForIcPages.pageNum"
						  	:page-size.sync="ic_manage_orgInfoForIcPages.pageSize"
						  	:total="ic_manage_orgInfoForIcPages.total">
						</el-pagination>
					</span>
					<span style="float: right;" v-show="dis_h_v">
	                    <el-pagination
						  	layout="total, prev, pager, next, jumper" 
			      		 	@current-change="ic_manage_pagerCurrentPartyIntegralRecords"
						  	:current-page.sync="ic_manage_partyIntegralRecordPages.pageNum"
						  	:page-size.sync="ic_manage_partyIntegralRecordPages.pageSize"
						  	:total="ic_manage_partyIntegralRecordPages.total">
						</el-pagination>
					</span>
				</el-row>
			</el-header>
			<el-main>
				<div v-show="!dis_h_v">
					<el-container>
						<el-aside width="15%">
							<div>
								<ul>
					  				<li class="orgInfoNameList" v-for="item in ic_manage_orgInfoForIcPages.list"
					  						@click="ic_manage_setOrgIdForQueryPartyUserInfoAndIcInfo(item.orgId)">
					  					<span>{{item.orgInfoName}}</span>
					  				</li>
					  				<li class="orgInfoNameList" v-if="ic_manage_orgInfoForIcPages.list.length == 0">
					  					没有查询到组织
					  				</li>
					  			</ul>
							</div>
						</el-aside>
						<el-main class="orgUserMain">
							<el-row>
				  				<el-button @click="ic_manage_queryPartyIntegralRecordsFororgInfoClick" size="small" type="primary">
									成员积分记录
								</el-button>
				  			</el-row>
							<el-table 
								row-key="orgRltUserId"
								size="small" 
								:data="ic_manage_partyUserInfoAndIcInfoPages.list" 
								style="width: 100%">
								<el-table-column label="用户ID" prop="orgRltUserId"></el-table-column>
								<el-table-column label="姓名" prop="name"></el-table-column>
								<el-table-column label="身份证号" prop="idCard"></el-table-column>
								<el-table-column fixed="right" label="操作" width=270>
									<template slot-scope="scope">
										<el-button @click="ic_manage_openChangePartyUserIntegralDialog(scope.row)" type="text" size="mini">积分变更</el-button>
										<el-button @click="ic_manage_queryPartyIntegralRecordsForPartyUserClick(scope.row)" type="text" size="mini">查看积分记录</el-button>
									</template>
								</el-table-column>
							</el-table>
							<div style="text-align: center; margin-top: 20px;">
								<el-pagination
								  	layout="total, prev, pager, next, jumper" 
					      		 	@current-change="ic_manage_pagerCurrentChangeForPartyUserInfoAndIcInfo"
								  	:current-page.sync="ic_manage_partyUserInfoAndIcInfoPages.pageNum"
								  	:page-size.sync="ic_manage_partyUserInfoAndIcInfoPages.pageSize"
								  	:total="ic_manage_partyUserInfoAndIcInfoPages.total">
								</el-pagination>
							</div>
						</el-main>
					</el-container>
				</div>
				<div v-show="dis_h_v">
					<el-table 
						row-key="pirId"
						size="small" 
						:data="ic_manage_partyIntegralRecordPages.list" 
						style="width: 100%">
						<el-table-column label="组织" prop="orgInfoName"></el-table-column>
						<el-table-column label="姓名" prop="name"></el-table-column>
						<el-table-column label="积分变更类型" prop="changeType"></el-table-column>
						<el-table-column label="积分变更原因" prop="changeName"></el-table-column>
						<el-table-column label="积分变更说明" prop="changeDescribes"></el-table-column>
						<el-table-column label="积分变更时间" prop="changeTime" width=200></el-table-column>
						<el-table-column label="积分变更操作">
							<template slot-scope="scope">
								<span :style="getChangeOperation(scope.row.changeOperation)">{{scope.row.changeOperation == 1 ? "加分" : "扣分"}}</span>
							</template>
						</el-table-column>
						<el-table-column label="积分变更" prop="changeScore"></el-table-column>
						<el-table-column label="是否计入总积分">
							<template slot-scope="scope">
								<span>{{scope.row.isMerge == 1 ? "是" : "否"}}</span>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</el-main>
		</el-container>



		<el-dialog title="党员积分变更" :visible.sync="ic_manage_changePartyUserIntegralDialog" width="400px">
			<el-form label-width="120px" size="small" :model="changePartyUserIntegralScoreForm" status-icon 
				ref="changePartyUserIntegralScoreForm" label-width="100px" :rules="changePartyUserIntegralScoreFormRules">
				<div>
					<div>
						<el-form-item label="变更类型" prop="changeType">
							<el-select size="small" clearable 
									@change="ic_manage_queryICT"
									style="width: 180px;"
									v-model="changePartyUserIntegralScoreForm.changeType" filterable placeholder="请选择变更类型">
								<el-option
									v-for="item in selectBox.integralChangeType"
								    :key="item.type"
								    :label="item.type"
								    :value="item.type">
								</el-option>
							</el-select>
						</el-form-item>
					</div>
					<div>
						<el-form-item label="变更分类" prop="changeId">
							<el-select size="small" clearable 
									@change="ic_manage_queryICTForOperation"
									style="width: 180px;"
									v-model="changePartyUserIntegralScoreForm.changeId" filterable placeholder="请选择变更分类">
								<el-option
									v-for="item in selectBox.ict"
								    :key="item.ictId"
								    :label="item.name"
								    :value="item.ictId">
								</el-option>
							</el-select>
						</el-form-item>
					</div>
					<div>
						<el-form-item label="操作" prop="operation">
							<el-select style="width: 180px;" :disabled="true" v-model="changePartyUserIntegralScoreForm.operation" size="small">
								<el-option label="扣分" :value="0"></el-option>
								<el-option label="加分" :value="1"></el-option>
							</el-select>
						</el-form-item>
					</div>
				</div>
				<div>
					<div>
						<el-form-item label="变更分值" prop="changeScore">
							<el-input 
								clearable 
								style="width: 180px;" 
								size="small" 
								v-model="changePartyUserIntegralScoreForm.changeScore" 
								placeholder="例：-3、0、5"></el-input>
						</el-form-item>
					</div>
					<div>
						<el-form-item label="计入总分" prop="isMerge">
							<el-select style="width: 180px;" v-model="changePartyUserIntegralScoreForm.isMerge" size="small">
								<el-option label="否" :value="0"></el-option>
								<el-option label="是" :value="1"></el-option>
							</el-select>
						</el-form-item>
					</div>
				</div>
				<div>
					<div>
						<el-form-item label="积分变更说明" prop="changeDescribes">
							<el-input 
							  	type="textarea"
							  	style="width: 180px;"
							 	:autosize="{ minRows: 3, maxRows: 5}"
							 	placeholder="积分变更说明"
								v-model="changePartyUserIntegralScoreForm.changeDescribes">
							</el-input>
						</el-form-item>
					</div>
				</div>
				<div style="margin: 20px 70px; float: right;">
					<el-button size="small" type="primary" @click="ic_manager_insertPartyUserIntegralRecord">变更积分</el-button>
			    	<el-button size="small" @click="ic_manage_resetChangePartyUserIntegralScoreForm">重置</el-button>
				</div>
			</el-form>
		</el-dialog>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			dis_h_v: false,
			ic_manage_changePartyUserIntegralDialog: false,	/*党员积分变更弹窗*/
			ic_manage_orgInfoForIcPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			ic_manage_partyUserInfoAndIcInfoPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			ic_manage_partyIntegralRecordPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			queryOrgInfoForIcCondition: {
				orgInfoName: null
			},
			queryPartyUserInfoAndIcInfoCondition: {
				orgId: null
			},
			queryPartyIntegralRecordsCondition: {
				orgId: null,
				idCard: null
			},
			changePartyUserIntegralScoreForm: {
				partyUserId: null,
				changeType: null,	/*用于判断类型选择框是否有值，不提交*/
				changeId: null,
				operation: null,		/*用于获得对应分值改变类型的操作，不提交*/
				changeScore: null,
				isMerge: null,
				changeDescribes: null
			},
			selectBox: {
				integralChangeType: [],
				ict: [],
				orgInfo_Ic: []
			},
			changePartyUserIntegralScoreFormRules: {
				changeId: [
					{ required: true, message: '请选择分数变更类型', trigger: 'blur' }
				],
				changeScore: [
					{ required: true, message: '请输入变更分值', trigger: 'blur' },
					{ pattern: /^(-|)?\d+(\.\d{1})?$/, message: '分值输入有误', trigger: 'blur'}
				],
				isMerge: [
					{ required: true, message: '请选择是否计入总分', trigger: 'blur' }
				]
			}
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.ic_manage_queryOrgInfoForIc();
			this.ic_manage_queryPartyIntegralRecords();	/*搜索积分变更记录*/
			this.money_manage_initSelectBox();
		},
		methods: {
			money_manage_initSelectBox() {
				var obj = this;
        		var url = "/org/ict/queryICT_ChangeType";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.selectBox.integralChangeType = datas.data;
					}
					
				})

				url = "/org/ic/queryOrgInfoForIcNotPage";
				t = {

				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.selectBox.orgInfo_Ic = datas.data;
					}
					
				})
			},
			getChangeOperation(changeOperation) {
				if (changeOperation == 1) {
					return "color: green; font-weight: bold;"
				} else {
					return "color: red; font-weight: bold;"
				}
			},
			getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
				var obj = this;
				var height = window.innerHeight;
				obj.ic_manage_orgInfoForIcPages.pageSize = parseInt((height - 100)/25);
				obj.ic_manage_partyUserInfoAndIcInfoPages.pageSize = parseInt((height - 100)/50);
			},
			ic_manage_initOrgInfoForIcPager() {
				var obj = this;
				obj.ic_manage_orgInfoForIcPages.pageNum = 1;
				obj.ic_manage_orgInfoForIcPages.total = 0;
				obj.ic_manage_orgInfoForIcPages.list = new Array();
			},
			ic_manage_initPartyUserInfoAndIcInfoPager() {
				var obj = this;
				obj.ic_manage_partyUserInfoAndIcInfoPages.pageNum = 1;
				obj.ic_manage_partyUserInfoAndIcInfoPages.total = 0;
				obj.ic_manage_partyUserInfoAndIcInfoPages.list = new Array();
			},
			ic_manage_initPartyIntegralRecordPager() {
				var obj = this;
				obj.ic_manage_partyIntegralRecordPages.pageNum = 1;
				obj.ic_manage_partyIntegralRecordPages.total = 0;
				obj.ic_manage_partyIntegralRecordPages.list = new Array();
			},
			ic_manage_pagerCurrentChangeForOrgInfo_Ic() {
				var obj = this;
				obj.ic_manage_queryOrgInfoForIc();
			},
			ic_manage_pagerCurrentChangeForPartyUserInfoAndIcInfo() {
				var obj = this;
				obj.ic_manage_queryPartyUserInfoAndIcInfo();
			},
			ic_manage_pagerCurrentPartyIntegralRecords() {
				var obj = this;
				obj.ic_manage_queryPartyIntegralRecords();
			},
			ic_manage_queryOrgInfoForIc() {	/*有积分结构的组织*/
				var obj = this;
				var url = "/org/ic/queryOrgInfoForIc";
				var t = {
					pageNum: obj.ic_manage_orgInfoForIcPages.pageNum,
					pageSize: obj.ic_manage_orgInfoForIcPages.pageSize,
					orgInfoName: obj.queryOrgInfoForIcCondition.orgInfoName
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.ic_manage_initOrgInfoForIcPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.ic_manage_setOrgIdForQueryPartyUserInfoAndIcInfo(data.data.list[0].orgId);
							obj.ic_manage_orgInfoForIcPages = data.data;
						}
					}
					
				})
			},
			ic_manage_setOrgIdForQueryPartyUserInfoAndIcInfo(orgId) {	/*点击组织查询组织下的成员和积分信息*/
				var obj = this;
				obj.queryPartyUserInfoAndIcInfoCondition.orgId = orgId;
				obj.ic_manage_queryPartyUserInfoAndIcInfo();
			},
			ic_manage_queryPartyUserInfoAndIcInfo() {
				var obj = this;
				var url = "/org/ic/queryPartyUserInfoAndIcInfo";
				var t = {
					pageNum: obj.ic_manage_partyUserInfoAndIcInfoPages.pageNum,
					pageSize: obj.ic_manage_partyUserInfoAndIcInfoPages.pageSize,
					orgId: obj.queryPartyUserInfoAndIcInfoCondition.orgId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.ic_manage_initPartyUserInfoAndIcInfoPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.ic_manage_partyUserInfoAndIcInfoPages = data.data;
						}
					}
					
				})
			},
			ic_manage_queryPartyIntegralRecordsFororgInfoClick() {
				var obj = this;
				obj.queryPartyIntegralRecordsCondition.idCard = null;
				obj.queryPartyIntegralRecordsCondition.orgId = obj.queryPartyUserInfoAndIcInfoCondition.orgId;
				obj.ic_manage_queryPartyIntegralRecords();
				obj.dis_h_v = true;
			},
			ic_manage_queryPartyIntegralRecordsForPartyUserClick(row) {
				var obj = this;
				obj.queryPartyIntegralRecordsCondition.idCard = row.idCard;
				obj.queryPartyIntegralRecordsCondition.orgId = obj.queryPartyUserInfoAndIcInfoCondition.orgId;
				obj.ic_manage_queryPartyIntegralRecords();
				obj.dis_h_v = true;
			},
			ic_manage_queryPartyIntegralRecords() {
				var obj = this;
				var url = "/party/integral/queryPartyIntegralRecords";
				var t = {
					pageNum: obj.ic_manage_partyIntegralRecordPages.pageNum,
					pageSize: obj.ic_manage_partyIntegralRecordPages.pageSize,
					orgId: obj.queryPartyIntegralRecordsCondition.orgId,
					idCard: obj.queryPartyIntegralRecordsCondition.idCard
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.ic_manage_initPartyIntegralRecordPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.ic_manage_partyIntegralRecordPages = data.data;
						}
					}
					
				})
			},
			ic_manage_openChangePartyUserIntegralDialog(row) {
				var obj = this;

				obj.changePartyUserIntegralScoreForm.partyUserId = row.orgRltUserId;

				obj.ic_manage_changePartyUserIntegralDialog = true;
			},
			ic_manage_queryICT() {
				var obj = this;
				obj.selectBox.ict = null;
				obj.changePartyUserIntegralScoreForm.changeId = null;
				obj.ic_manage_queryICTForOperation();
				if (obj.changePartyUserIntegralScoreForm.changeType == null || obj.changePartyUserIntegralScoreForm.changeType == "") {
					return;
				}
        		url = "/org/ict/queryICT";
				var t = {
					type: obj.changePartyUserIntegralScoreForm.changeType
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.selectBox.ict = datas.data;
					}
					
				})
			},
			ic_manage_queryICTForOperation() {
				var obj = this;
				obj.changePartyUserIntegralScoreForm.operation = null;
				if (obj.changePartyUserIntegralScoreForm.changeId == null || obj.changePartyUserIntegralScoreForm.changeId == "") {
					return;
				}
        		url = "/org/ict/queryICT";
				var t = {
					ictId: obj.changePartyUserIntegralScoreForm.changeId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.changePartyUserIntegralScoreForm.operation = datas.data[0].operation;
					}
					
				})
			},
			ic_manager_insertPartyUserIntegralRecord() {	/*添加积分变更记录*/
				var obj = this;
				this.$refs.changePartyUserIntegralScoreForm.validate( function(valid) {
					if (valid) {
						url = "/party/integral/insertPartyUserIntegralRecord";
						var t = {
							orgId: obj.queryPartyUserInfoAndIcInfoCondition.orgId,
							partyId: obj.changePartyUserIntegralScoreForm.partyUserId,
							changeDescribes: obj.changePartyUserIntegralScoreForm.changeDescribes,
							changeIntegralType: obj.changePartyUserIntegralScoreForm.changeId,
							changeScore: obj.changePartyUserIntegralScoreForm.changeScore,
							isMerge: obj.changePartyUserIntegralScoreForm.isMerge
						}
						$.post(url, t, function(datas, status){
							if (datas.code == 200) {
								obj.ic_manage_queryPartyIntegralRecords();
								obj.ic_manage_resetChangePartyUserIntegralScoreForm();
								obj.ic_manage_changePartyUserIntegralDialog = false;
								toast('添加成功',datas.msg,'success');
							}
							
						})
					}
				})	
			},
			ic_manage_resetChangePartyUserIntegralScoreForm() {
				var obj = this;
        		obj.$refs.changePartyUserIntegralScoreForm.resetFields();
			}
		}
	});
</script>
</html>