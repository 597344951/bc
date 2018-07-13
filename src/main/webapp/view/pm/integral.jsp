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
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20" style="margin:0;">
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
							开始做跳转开始做跳转开始做跳转开始做跳转开始做跳转
							<el-table 
								row-key="orgRltUserId"
								size="small" 
								:data="ic_manage_partyUserInfoAndIcInfoPages.list" 
								style="width: 100%">
								<el-table-column label="用户ID" prop="orgRltUserId"></el-table-column>
								<el-table-column label="姓名" prop="name"></el-table-column>
								<el-table-column label="身份证号" prop="idCard"></el-table-column>
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
						<el-table-column label="积分变更要素" prop="changeName"></el-table-column>
						<el-table-column label="积分变更说明" prop="changeTypeDescribes"></el-table-column>
						<el-table-column label="积分变更时间" prop="changeTime"></el-table-column>
						<el-table-column label="积分变更操作">
							<template slot-scope="scope">
								<span :style="getChangeOperation(scope.row.changeOperation)">{{scope.row.changeOperation == 1 ? "加分" : "扣分"}}</span>
							</template>
						</el-table-column>
						<el-table-column label="是否计入总积分">
							<template slot-scope="scope">
								<span>{{scope.row.isMerge == 1 ? "是" : "否"}}</span>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</el-main>
		</el-container>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			dis_h_v: false,
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
			queryPartyUserInfoAndIcInfoCondition: {
				orgId: null
			},
			queryPartyIntegralRecordsCondition: {

			}
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.ic_manage_queryOrgInfoForIc();
			this.ic_manage_queryPartyIntegralRecords();
		},
		methods: {
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
			ic_manage_queryOrgInfoForIc() {	/*有积分结构的组织*/
				var obj = this;
				var url = "/org/ic/queryOrgInfoForIc";
				var t = {
					pageNum: obj.ic_manage_orgInfoForIcPages.pageNum,
					pageSize: obj.ic_manage_orgInfoForIcPages.pageSize
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
			ic_manage_setOrgIdForQueryPartyUserInfoAndIcInfo(orgId) {
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
			ic_manage_queryPartyIntegralRecords() {
				var obj = this;
				var url = "/party/integral/queryPartyIntegralRecords";
				var t = {
					pageNum: obj.ic_manage_partyIntegralRecordPages.pageNum,
					pageSize: obj.ic_manage_partyIntegralRecordPages.pageSize
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
			}
		}
	});
</script>
</html>