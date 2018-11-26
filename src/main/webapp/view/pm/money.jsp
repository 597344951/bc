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
<title>党费缴纳管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<script type="text/javascript" src="/json/address-pca.json"></script>
<%@include file="/include/echarts.jsp"%>
<style type="text/css">
	.pmdmInfoTitle {
		font-weight:bold;
		font-size:16px
	}
	.el-row {
		margin-bottom: 10px;
	}
	.toolbar{
	    position: fixed;
	    top: 0;
	    background: #fff;
	    width: 100%;
	    box-shadow: 0 2px 15px 2px #ddd;
	}
	.el-aside{
		width: 15%;
	    position: fixed;
	    top: 50px;
	    left: 0;
	    height: 100%;
	    padding-top: 10px;
	    padding-bottom: 50px;
	    padding-left: 10px;
	}
	.orgInfoMain{
	    position: fixed;
	    top: 50px;
	    left: 15%;
	    height: 90%;
	    padding-top: 10px;
	    padding-bottom: 50px;
	    width: 100%;
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
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20" style="margin:0;">
					<shiro:hasPermission name="party:pmdm:query">
						<el-popover
							class="margin-left-10"
							v-if="signInAccountType != 'party_role'"
							placement="bottom" 
						  	width="200" 
						  	v-show="!dis_h_v"
						  	trigger="click" >
						  	<el-button size="small" type="primary" slot="reference">
						  		<i class="el-icon-search"></i>
						  		搜索组织
						  	</el-button>
						  	<div>
								<el-row>
									<el-input size="small" clearable
										@change="money_manage_queryPartyMembershipDuesOfConditionForOrgInfo"
										v-model="queryConditionForOrgInfo.orgInfoName" placeholder="请输入组织名"></el-input>
								</el-row>
						  	</div>
						</el-popover>
					</shiro:hasPermission>
					<shiro:hasPermission name="party:pmdm:query">
						<el-popover
							class="margin-left-10"
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
									<el-input size="small" clearable
										@change="money_manage_queryPartyMembershipDuesOfCondition"
										v-model="queryCondition.partyUserName" placeholder="请输入党员名称"></el-input>
								</el-row>
								<el-row>
									<el-input size="small" clearable
										@change="money_manage_queryPartyMembershipDuesOfCondition"
										v-model="queryCondition.idCard" placeholder="请输入党员身份证号码"></el-input>
								</el-row>
								<el-row>
									<el-select size="small" clearable 
											@change="money_manage_queryPartyMembershipDuesOfCondition()"
											v-model="queryCondition.payStatus" placeholder="缴纳状态">
										<el-option
											v-for="item in queryCondition.PMDSs"
											:key="item.id"
											:label="item.name"
											:value="item.id">
										</el-option>
									</el-select>
								</el-row>
								<el-row>
									<el-select size="small" clearable 
											@change="money_manage_queryPartyMembershipDuesOfCondition()"
											v-model="queryCondition.paidWay" placeholder="缴纳方式">
										<el-option
											v-for="item in queryCondition.PMDWs"
											:key="item.id"
											:label="item.name"
											:value="item.id">
											<img :src="getPaidImg(item.name)" style="width: 55px;height: 25px;float: left; padding-top: 4px;" />
											<span style="float: right;">{{item.name}}</span>
										</el-option>
									</el-select>
								</el-row>
								<el-row>
									<el-select size="small" clearable filterable 
											@change="money_manage_queryPartyMembershipDuesOfCondition()"
											v-model="queryCondition.orgInfoId" placeholder="组织，如果太多可搜索组织">
										<el-option
											v-for="item in queryCondition.orgInfo_PMDM"
											:key="item.orgId"
											:label="item.orgInfoName"
											:value="item.orgId">
										</el-option>
									</el-select>
								</el-row>
								<el-row>
									<el-date-picker
								      	v-model="queryCondition.paidDateStart" 
								      	@change="money_manage_queryPartyMembershipDuesOfCondition()" 
								      	type="month"
								      	size="small"
								      	style="width: 100%;"
								      	value-format="yyyy-MM-dd" 
								      	placeholder="起始时间">
								    </el-date-picker>
								</el-row>
								<el-row>
									<el-date-picker
								      	v-model="queryCondition.paidDateEnd" 
								      	@change="money_manage_queryPartyMembershipDuesOfCondition()" 
								      	type="month"
								      	size="small"
								      	style="width: 100%;"
								      	value-format="yyyy-MM-dd" 
								      	placeholder="结束时间">
								    </el-date-picker>
								</el-row>
						  	</div>
						</el-popover>
					</shiro:hasPermission>
					<shiro:hasPermission name="party:pmdm:query">
						<el-popover class="margin-left-10"
							v-if="signInAccountType != 'party_role'"
							placement="bottom" 
						  	width="400" 
						  	trigger="click" >
						  	<el-button size="small" type="primary" slot="reference">
						  		<i class="el-icon-upload2"></i>
						  		导入记录
						  	</el-button>
						  	<div>
								<el-button type="text" @click="money_manage_exportPMDMExcelExample">下载Excel模板</el-button>，按照模板填写
								<p>党费缴纳会使用组织积分系统，如果没有初始化积分，请到积分管理初始化</p>
						  		<el-upload
						  			action="" 
						 	   		:http-request="money_manage_importPMDMsExcel"
						 	   		:multiple="true" 
						 	   		:before-upload="money_manage_validatePMDMsExcel" >
							      	<el-button type="text">点击上传excel文件</el-button>
								</el-upload>
								<el-button type="text" @click="money_manage_showImportPMDMsExcelErrorMsgDialog">显示导入错误信息</el-button>
						  	</div>
						</el-popover>
					</shiro:hasPermission>
					<shiro:hasPermission name="party:pmdm:query">
						<el-button v-if="signInAccountType != 'party_role'" 
							class="margin-left-10" v-show="dis_h_v" 
							size="small" icon="el-icon-download"
							type="primary" @click="money_manage_exportPMDMExcel">下载记录
						</el-button>
					</shiro:hasPermission>
					<el-button-group v-if="signInAccountType != 'party_role'"  class="margin-left-10">
                        <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                        <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                    </el-button-group>
                    <span style="float: right;" v-show="!dis_h_v">
	                    <el-pagination
						  	layout="total, prev, pager, next, jumper" 
			      		 	@current-change="money_manage_pagerCurrentChangeForOrgInfo"
						  	:current-page.sync="money_manage_orgInfoForPmdmPages.pageNum"
						  	:page-size.sync="money_manage_orgInfoForPmdmPages.pageSize"
						  	:total="money_manage_orgInfoForPmdmPages.total">
						</el-pagination>
					</span>
			 	    <span style="float: right;" v-show="dis_h_v">
	                    <el-pagination
						  	layout="total, prev, pager, next, jumper" 
			      		 	@current-change="money_manage_pagerCurrentChange"
						  	:current-page.sync="money_manage_pmdmPages.pageNum"
						  	:page-size.sync="money_manage_pmdmPages.pageSize"
						  	:total="money_manage_pmdmPages.total">
						</el-pagination>
					</span>
				</el-row>
			</el-header>
			<el-main>
				<div v-show="!dis_h_v" v-if="signInAccountType != 'party_role'">
					<el-container>
					  	<el-aside width="15%">
					  		<div>
					  			<ul>
					  				<li class="orgInfoNameList" v-for="item in money_manage_orgInfoForPmdmPages.list"
					  						@click="money_manage_doSomething(item.orgId)">
					  					<span>{{item.orgInfoName}}</span>
					  				</li>
					  				<li class="orgInfoNameList" v-if="money_manage_orgInfoForPmdmPages.list.length == 0">
					  					没有缴费记录
					  				</li>
					  			</ul>
					  		</div>
					  	</el-aside>
					  	<el-main class="orgInfoMain">
					  		<div style="width: 100%">
					  			<el-row style="padding: 10px 20px;">
					  				<el-button @click="money_manage_queryPartyMembershipDuesOfConditionForOrgInfoId" size="small" type="primary" @click="">
										成员缴纳记录
									</el-button>
									<el-button v-if="false" @click="" size="small" type="primary" @click="">
										成员离线缴纳上报
									</el-button>
								</el-row>
					  			<el-row style="padding: 10px 20px;">
									<el-date-picker
								      	v-model="queryConditionForOrgInfoInner.paidDateStart" 
								      	@change="money_manage_queryPMDMChartForOrgInfo"
								      	type="month"
								      	size="small"
								      	value-format="yyyy-MM-dd" 
								      	placeholder="起始时间">
								    </el-date-picker>
								   	至
								    <el-date-picker
								      	v-model="queryConditionForOrgInfoInner.paidDateEnd" 
								      	@change="money_manage_queryPMDMChartForOrgInfo"
								      	type="month"
								      	size="small"
								      	value-format="yyyy-MM-dd" 
								      	placeholder="结束时间">
								    </el-date-picker>
					  			</el-row>
					  			<div id="PMDMChart" :style="PMDMChartStyle"></div>
					  			<div id="PMDMLineChart" :style="PMDMChartStyle"></div>
					  		</div>
					  	</el-main>
					</el-container>
				</div>
				<span v-show="dis_h_v">
					<el-table 
							row-key="id"
							size="small" 
							:data="money_manage_pmdmPages.list" 
							style="width: 100%">
						<el-table-column type="expand">
							<template slot-scope="scope">
								<el-row :gutter="20">
									<el-col :span="24"><span class="pmdmInfoTitle">缴纳备注：</span></el-col>
								</el-row>
								<el-row :gutter="20">
									<el-col :span="22">{{scope.row.paidDescribe}}</el-col>
								</el-row>
							</template>
						</el-table-column>
						<el-table-column label="姓名" prop="name"></el-table-column>
						<el-table-column label="组织" prop="orgInfoName"></el-table-column>
						<el-table-column label="应缴金额">
							<template slot-scope="scope">
								<span>￥{{scope.row.shouldPayMoney}}</span>
							</template>
						</el-table-column>
						<el-table-column label="实缴金额">
							<template slot-scope="scope">
								<span :style="getPaidStyle(scope.row.payStatus)">￥{{scope.row.paidMoney}}</span>
							</template>
						</el-table-column>
						<el-table-column label="应缴日期" width=200>
							<template slot-scope="scope">
								<span>{{scope.row.shouldPayDateStart}} 到 {{scope.row.shouldPayDateEnd}}</span>
							</template>
						</el-table-column>
						<el-table-column label="实缴日期" prop="paidDate"></el-table-column>
						<el-table-column label="缴费方式">
							<template slot-scope="scope">
								<img :src="getPaidImg(scope.row.paidWay)" style="width: 60px; height: 20px;" :title="scope.row.paidWay">
							</template>
							
						</el-table-column>
						<el-table-column label="缴纳状态">
							<template slot-scope="scope">
								<span :style="getPaidStyle(scope.row.payStatus)">
									{{scope.row.payStatus}}
								</span>
							</template>
						</el-table-column>
					</el-table>
				</span>
			</el-main>
		</el-container>

		<el-dialog title="导入党费缴纳记录错误信息" :visible.sync="money_manage_importPMDMsExcelErrorMsgDialog" width="50%">
			<span style="margin: 0 15px">
				可以在
				<span style="color: blue"> 导入记录->显示导入错误信息 </span>
				再次打开
			</span>
			<div style="margin: 10px 15px">
				<el-input 
				  	type="textarea"
				 	:autosize="{ minRows: 10, maxRows: 15}"
				 	placeholder="导入党费缴纳记录信息失败时，对表格校验的错误信息将显示在这里" 
				 	v-model="money_manage_importPMDMsExcelErrorMsg">
				</el-input>
			</div>
		</el-dialog>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			money_manage_importPMDMsExcelErrorMsg: null,
			dis_h_v: false,
			PMDMChartStyle: null,
			money_manage_importPMDMsExcelErrorMsgDialog: false,
			money_manage_pmdmPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			money_manage_orgInfoForPmdmPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			queryCondition: {
				PMDSs: [],	/*党费缴纳状态下拉框*/
				PMDWs: [],	/*党费缴纳方式下拉框*/
				orgInfo_PMDM: [],	/*缴费记录所包含的全部党组织*/
				partyUserName: null,
				idCard: null,
				payStatus: null,
				paidWay: null,
				paidDateStart: null,
				paidDateEnd: null,
				orgInfoId: null
			},
			queryConditionForOrgInfo: {
				orgInfoName: null
			},
			queryConditionForOrgInfoInner: {
				orgInfoId: null,
				paidDateStart: null,
				paidDateEnd: null
			},
			signInAccountType: null
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.money_manage_queryPartyMembershipDues();	/*查询组织信息*/
			this.money_manage_initSelectBox();
			this.money_manage_queryOrgInfoOfPMDM();
			this.getSignInAccountType();
		},
		methods: {
			getSignInAccountType() {	/*得到该登录用户的类型*/
				var obj = this;

				var url = "/siat/getSignInAccountType";
				var t = {
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							obj.signInAccountType = data.data;
							if (obj.signInAccountType == "party_role") {
								obj.dis_h_v = true;
							}
						}
					}

				})
			},
			money_manage_initSelectBox() {	/*记录下拉选择框*/
				var obj = this;
        		url = "/party/pmdw/queryPMDWs";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.queryCondition.PMDWs = datas.data;
					}
					
				})

				url = "/party/pmds/queryPMDSs";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.queryCondition.PMDSs = datas.data;
					}
					
				})

				url = "/party/pmdm/queryOrgInfoOfPMDMNotPage";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.queryCondition.orgInfo_PMDM = datas.data;
					}
					
				})
			},
			getPaidImg(paidWay) {	/*根据支付方式得到对应图片*/
				if (paidWay == "支付宝") {
					return "/view/pm/img/ali_pay.jpg";
				} else if (paidWay == "网上银行") {
					return "/view/pm/img/ol_pay.jpg";
				} else if (paidWay == "微信") {
					return "/view/pm/img/wechat_pay.jpg";
				} else if (paidWay == "现金") {
					return "/view/pm/img/money_pay.jpg";
				} else if (paidWay == "代扣代缴") {
					return "/view/pm/img/withhold_pay.jpg";
				} else {
					return "/view/pm/img/other_pay.jpg";
				}
			},
			getPaidStyle(payStatus) {	/*根据支付状态设置不同的样式*/
				if (payStatus == "按时缴清") {
					return "color: green; font-weight: bold;";
				} else if (payStatus == "未缴清") {
					return "color: #f4a314; font-weight: bold;";
				} else if (payStatus == "未缴") {
					return "color: red; font-weight: bold;";
				} else if (payStatus == "迟缴") {
					return "color: blue; font-weight: bold;";
				} else if (payStatus == "补缴") {
					return "color: black; font-weight: bold;";
				} 
			},
			getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
				var obj = this;
				var height = window.innerHeight;
				obj.money_manage_pmdmPages.pageSize = parseInt((height - 100)/50);
				obj.money_manage_orgInfoForPmdmPages.pageSize = parseInt((height - 100)/25);
				obj.PMDMChartStyle = "width: 75%; height: " + (height - 300) + "px;padding-left: 17px;margin-bottom: 30px;";
			},
			money_manage_initPager() {	/* 初始化页面数据 */
				var obj = this;
				obj.money_manage_pmdmPages.pageNum = 1;
				obj.money_manage_pmdmPages.total = 0;
				obj.money_manage_pmdmPages.list = new Array();
			},
			money_manage_pagerCurrentChange() {	/*分页查询动作*/
				var obj = this;
				obj.money_manage_queryPartyMembershipDues();
			},
			money_manage_queryPartyMembershipDuesOfCondition() {	/*按条件查询*/
				var obj = this;
				obj.money_manage_queryPartyMembershipDues();
			},
			money_manage_queryPartyMembershipDues() {	/*查询党费缴纳记录*/
				var obj = this;
				var url = "/party/pmdm/queryPartyMembershipDues";
				var t = {
					pageNum: obj.money_manage_pmdmPages.pageNum,
					pageSize: obj.money_manage_pmdmPages.pageSize,
					partyUserName: obj.queryCondition.partyUserName,
					idCard: obj.queryCondition.idCard,
					payStatus: obj.queryCondition.payStatus,
					paidWay: obj.queryCondition.paidWay,
					paidDateStart: obj.queryCondition.paidDateStart,
					paidDateEnd: obj.queryCondition.paidDateEnd,
					orgInfoId: obj.queryCondition.orgInfoId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.money_manage_initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.money_manage_pmdmPages = data.data;
						}
					}
					
				})
			},






			
			money_manage_initorgInfoForPmdmPager() {	/*初始化组织记录页面数据*/
				var obj = this;
				obj.money_manage_orgInfoForPmdmPages.pageNum = 1;
				obj.money_manage_orgInfoForPmdmPages.total = 0;
				obj.money_manage_orgInfoForPmdmPages.list = new Array();
			},
			money_manage_pagerCurrentChangeForOrgInfo() {	/*组织记录分页查询动作*/
				var obj = this;
				obj.money_manage_queryOrgInfoOfPMDM();
			},
			money_manage_queryPartyMembershipDuesOfConditionForOrgInfo() {	/*组织记录按条件查询*/
				var obj = this;
				obj.money_manage_queryOrgInfoOfPMDM();
			},
			money_manage_queryOrgInfoOfPMDM() {	/*查询党费缴纳记录组织信息*/
				var obj = this;
				var url = "/party/pmdm/queryOrgInfoOfPMDM";
				var t = {
					pageNum: obj.money_manage_orgInfoForPmdmPages.pageNum,
					pageSize: obj.money_manage_orgInfoForPmdmPages.pageSize,
					orgInfoName: obj.queryConditionForOrgInfo.orgInfoName
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.money_manage_initorgInfoForPmdmPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.money_manage_orgInfoForPmdmPages = data.data;
							obj.money_manage_doSomething(data.data.list[0].orgId);
						}
					}
					
				})
			},








			money_manage_doSomething(orgInfoId) {	/*按组织查询记录点击之后处理部分*/
				var obj = this;
				obj.queryConditionForOrgInfoInner.orgInfoId = orgInfoId;	/*保存id，用于之后使用*/
				obj.money_manage_queryPMDMChartForOrgInfo(orgInfoId);
			},
			money_manage_getMonth(month) {
				return month >=10 ? month : "0" + month;
			},
			money_manage_queryPartyMembershipDuesOfConditionForOrgInfoId() {	/*在组织记录界面用组织id查询该组织党员的缴费记录*/
				var obj = this;
				obj.queryCondition.partyUserName = null;
				obj.queryCondition.idCard = null;
				obj.queryCondition.payStatus = null;
				obj.queryCondition.paidWay = null;
				obj.queryCondition.paidDateStart = null;
				obj.queryCondition.paidDateEnd = null;
				obj.queryCondition.orgInfoId = obj.queryConditionForOrgInfoInner.orgInfoId;
				obj.money_manage_queryPartyMembershipDues();
				obj.dis_h_v = true;
			},
			money_manage_queryPMDMChartForOrgInfo(orgInfoId) {	/*按组织统计图*/
				var obj = this;
				var url = "/party/pmdm/queryPMDMChartForOrgInfo";
				var t = {
					orgInfoId: obj.queryConditionForOrgInfoInner.orgInfoId,
					paidDateStart: obj.queryConditionForOrgInfoInner.paidDateStart,
					paidDateEnd: obj.queryConditionForOrgInfoInner.paidDateEnd
				}

				var myChart = echarts.init(document.getElementById("PMDMChart"));
		        myChart.clear();
				myChart.showLoading({
				　　text : '正在查找当前组织党费缴纳统计... ...\n为避免加载过长在时间选择上应避免太大的跨度'
				});
				var myLineChart = echarts.init(document.getElementById("PMDMLineChart"));
		        myLineChart.clear();
				myLineChart.showLoading({
				　　text : '正在查找当前组织党费缴纳统计... ...\n为避免加载过长在时间选择上应避免太大的跨度'
				});
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	

							// 基于准备好的dom，初始化echarts实例

					        var legendDatasData = data.data.legendDatas;	/*提示文本*/
					        var dateLinesData = data.data.dateLines;	/*x轴信息*/

					        var datasData = data.data.datas;
					        var datasss = new Array;
					        for (var i = 0; i < datasData.length; i++) {
					        	var datas = datasData[i];
					        	var shuju = {name:null, type: "bar", data: []};
					        	shuju.name = legendDatasData[i];
					        	shuju.data = datasData[i];
					        	datasss[i] = shuju;
					        }
					        var lineDatasData = data.data.lineDatas;
					        var lineDatasss = new Array;
					        for (var i = 0; i < lineDatasData.length; i++) {
					        	var lineDatas = lineDatasData[i];
					        	var lineShuju = {name:null, type: "line", data: [], smooth: true};
					        	lineShuju.name = legendDatasData[i];
					        	lineShuju.data = lineDatasData[i];
					        	lineDatasss[i] = lineShuju;
					        }






					        // 绘制图表
					        myChart.hideLoading();    //隐藏加载动画
					        myLineChart.hideLoading();
					        var option = {
					        	title: {
							        text: '党费缴纳人数统计'
							    },
							    tooltip: {
							        trigger: 'axis',
							        axisPointer: {
							            type: 'cross',
							            crossStyle: {
							                color: '#999'
							            }
							        }
							    },
							    toolbox: {
							        feature: {
							            dataView: {show: true, readOnly: false},
							            magicType: {show: true, type: ['line', 'bar']},
							            restore: {show: true},
							            saveAsImage: {show: true}
							        }
							    },
							    legend: {
							        data: legendDatasData
							    },
							    xAxis: [
							        {
							            type: 'category',
							            data: dateLinesData,
							            axisPointer: {
							                type: 'shadow'
							            }
							        }
							    ],
							    yAxis: [
							        {
							            type: 'value',
							            name: '人数',
							            min: 0,
							            axisLabel: {
							                formatter: '{value} 人'
							            }
							        }
							    ],
							    dataZoom: [
							        {
							            type: 'slider',
							            show: true
							        }
							    ],
							    series: datasss
							};
							var lineOption = {
							    title: {
							        text: '党费缴纳比例统计'
							    },
							    tooltip: {
							        trigger: 'axis'
							    },
							    legend: {
							        data:legendDatasData
							    },

							    toolbox: {
							        feature: {
							            dataView: {show: true, readOnly: false},
							            magicType: {show: true, type: ['line', 'bar']},
							            restore: {show: true},
							            saveAsImage: {show: true}
							        }
							    },
							    xAxis: {
							        type: 'category',
							        boundaryGap: false,
							        data: dateLinesData
							    },
							    yAxis: [
							        {
							            type: 'value',
							            name: '比例',
							            min: 0,
							            axisLabel: {
							                formatter: '{value} %'
							            }
							        }
							    ],
							    dataZoom: [
							        {
							            type: 'slider',
							            show: true
							        }
							    ],
							    series: lineDatasss
							};
					        myChart.setOption(option);
					        myLineChart.setOption(lineOption);
						} 
					}
					
				})
			},






			money_manage_exportPMDMExcelExample() {
				window.location = "/party/pmdm/exportPMDMExcelExample";
			},
			money_manage_validatePMDMsExcel(thisFile) {
				var fileFormat = thisFile.name.split(".");
				if (fileFormat == null || fileFormat == undefined) {
					toast('格式错误',"只能上传excel文档（xlsx、xls）",'error');
					return false;
				}
				var fileSuffix = fileFormat[fileFormat.length - 1];	/* 拿到文件后缀 */
				if (fileSuffix == "xlsx" || fileSuffix == "xls") {
					return true;
				} else {
					toast('格式错误',"只能上传excel文档（xlsx、xls）",'error');
					return false;
				}
			},
			money_manage_importPMDMsExcel(thisImport) {
				var obj = this;
				var formData = new FormData();
				formData.append("file", thisImport.file);
				$.ajax({
                   url: "/party/pmdm/importPMDMsExcel",
                   data: formData,
                   type: "Post",
                   dataType: "json",
                   cache: false,//上传文件无需缓存
                   processData: false,//用于对data参数进行序列化处理 这里必须false
                   contentType: false, //必须
                   success: function (data) {
                	   if (data.code == 200) {
                		   toast('导入成功',data.msg,'success');
                		   obj.money_manage_importPMDMsExcelErrorMsg = null;
                		   obj.money_manage_queryOrgInfoOfPMDM();
                		   obj.money_manage_queryPartyMembershipDues();
                	   } else if (data.code == 500) {
                		   toast('导入失败',data.msg,'error');
                		   obj.money_manage_importPMDMsExcelErrorMsg = data.data;
                		   //window.location = "/party/user/excel/downloadValidataMsg";	/*导入失败下载错误信息*/
                		   obj.money_manage_importPMDMsExcelErrorMsgDialog = true;
                	   }
                   },
               })
			},
			money_manage_showImportPMDMsExcelErrorMsgDialog() {
				var obj = this;
				obj.money_manage_importPMDMsExcelErrorMsgDialog = true;
			},
			linkCondition(conditions) {
				if (conditions.length == 0) {
					conditions += "?";
				} else {
					conditions += "&";
				}
				return conditions;
			},
			money_manage_exportPMDMExcel() {
				var obj = this;
				var conditions = "";
				if (obj.queryCondition.partyUserName != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "partyUserName=" + obj.queryCondition.partyUserName;
				}
				if (obj.queryCondition.idCard != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "idCard=" + obj.queryCondition.idCard;
				}
				if (obj.queryCondition.payStatus != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "payStatus=" + obj.queryCondition.payStatus;
				}
				if (obj.queryCondition.paidWay != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "paidWay=" + obj.queryCondition.paidWay;
				}
				if (obj.queryCondition.paidDateStart != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "paidDateStart=" + obj.queryCondition.paidDateStart;
				}
				if (obj.queryCondition.paidDateEnd != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "paidDateEnd=" + obj.queryCondition.paidDateEnd;
				}
				if (obj.queryCondition.orgInfoId != null) {
					conditions += obj.linkCondition(conditions);
					conditions += "orgInfoId=" + obj.queryCondition.orgInfoId;
				}

				window.location = "/party/pmdm/exportPMDMExcel" + conditions;
			}
		}
	});
	
	$(document).ready(function(){
		
	});
	
</script>
</html>