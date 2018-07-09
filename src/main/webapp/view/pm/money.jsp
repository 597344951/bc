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
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20">
					<!-- <el-popover class="margin-0-10"
							placement="bottom" 
						  	width="400" 
						  	trigger="click" >
					  	<el-button size="small" type="primary" slot="reference">
					  		<i class="el-icon-upload"></i>
					  		导入记录
					  	</el-button>
					  	<div>
							<el-button type="text" @click="">下载Excel模板</el-button>，按照模板填写
					  		<el-upload
					  			action="" 
					 	   		:http-request=""
					 	   		:multiple="true" 
					 	   		:before-upload="" >
						      	<el-button type="text">点击上传excel文件</el-button>
							</el-upload>
							<el-button type="text" @click="">显示导入错误信息</el-button>
					  	</div>
					</el-popover> -->
					<el-popover class="margin-0-10"
						placement="bottom" 
					  	width="200" 
					  	trigger="hover" >
					  	<el-button size="small" type="primary" slot="reference">
					  		<i class="el-icon-search"></i>
					  		搜索组织
					  	</el-button>
					  	<div>
							<el-row>
								<el-input size="small" clearable
									@change="money_manage_queryPartyMembershipDuesOfCondition"
									v-model="queryCondition.partyUserName" placeholder="请输入党员名称"></el-input>
							</el-row>
					  	</div>
					</el-popover>
			 	    <span style="float: right;">
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
					<el-table-column label="应缴金额" prop="shouldPayMoney"></el-table-column>
					<el-table-column label="实缴金额" prop="paidMoney"></el-table-column>
					<el-table-column label="应缴日期" width=200>
						<template slot-scope="scope">
							<span>{{scope.row.shouldPayDateStart}} 到 {{scope.row.shouldPayDateEnd}}</span>
						</template>
					</el-table-column>
					<el-table-column label="实缴日期" prop="paidDate"></el-table-column>
					<el-table-column label="缴费方式" prop="paidWay"></el-table-column>
					<el-table-column label="状态">
						<template slot-scope="scope">
							<span :style="scope.row.payStatus == 1 ? 'color: green; font-weight: bold;' : 'color: red; font-weight: bold;'">
								{{scope.row.payStatus == 0 ? "失败" : "成功"}}
							</span>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
		</el-container>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			money_manage_pmdmPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			queryCondition: {
				partyUserName: null
			}
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.money_manage_queryPartyMembershipDues()	/*查询组织信息*/
		},
		methods: {
			getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
				var obj = this;
				var height = window.innerHeight;
				obj.money_manage_pmdmPages.pageSize = parseInt((height - 100)/50);
			},
			money_manage_initPager() {	/* 初始化页面数据 */
				var obj = this;
				obj.money_manage_pmdmPages.pageNum = 1;
				obj.money_manage_pmdmPages.total = 0;
				obj.money_manage_pmdmPages.list = new Array();
			},
			money_manage_pagerCurrentChange() {
				var obj = this;
				obj.money_manage_queryPartyMembershipDues();
			},
			money_manage_queryPartyMembershipDuesOfCondition() {
				var obj = this;
				obj.money_manage_queryPartyMembershipDues();
			},
			money_manage_queryPartyMembershipDues() {
				var obj = this;
				var url = "/party/pmdm/queryPartyMembershipDues";
				var t = {
					pageNum: obj.money_manage_pmdmPages.pageNum,
					pageSize: obj.money_manage_pmdmPages.pageSize,
					partyUserName: obj.queryCondition.partyUserName
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
			}
		}
	});
</script>
</html>