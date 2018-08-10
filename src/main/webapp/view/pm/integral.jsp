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
	.el-tree-node__label {
		font-size: 12px;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20" style="margin:0;">
					<shiro:hasPermission name="org:ic:query">
						<el-popover
							class="margin-left-10"
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
					</shiro:hasPermission>
					<shiro:hasPermission name="org:ic:query">
						<el-popover
							v-if="signInAccountType != 'party_role'"
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
					</shiro:hasPermission>
					<el-button-group class="margin-left-10" v-if="signInAccountType != 'party_role'">
                        <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                        <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                    </el-button-group>
                    <el-popover
                    	class="margin-left-10"
						placement="bottom" 
					  	width="300" 
					  	trigger="hover" >
					  	<el-button type="text" size="small" slot="reference">公告</el-button>
					  	<div style="font-size: 12px;">
					  		<p>如果在这里没有找到希望的组织，可能没有对组织设置积分组成结构，请前往组织管理设置该组织的积分结构</p>
					  		<p style="margin-top: 10px;">积分上限为该项设置的上限，下限为0，如果积分变为0时在扣分不会变成负分，不过之后有加分的情况
					  		则会和之前扣的分相抵消，超出上限的积分不会保留下来抵消扣除的分值</p>
					  	</div>
					</el-popover>
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
				<div v-show="!dis_h_v" v-if="signInAccountType != 'party_role'">
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
				  			<el-row>
				  				<p style="font-size: 12px; float: left;">
				  					该组织积分总分为 
				  					<span style="font-size: 18px; font-weight: bold; color: red;">
				  						<a style="color: red;" href="javascript: void(0)" @click="ic_manager_openUpdateOrgIntegralConstitute(queryPartyUserInfoAndIcInfoCondition.orgId)">
				  							{{orgIntegralInfo.integralCount}}
				  						</a>
				  					</span> 
				  					分 
				  				</p>
				  				<div style="font-size: 12px;">
					  				<el-popover
										placement="bottom" 
									  	width="300" 
									  	trigger="hover" >
									  	<i class="el-icon-question" slot="reference"></i>
									  	<div style="font-size: 12px;">
									  		<p style="margin-bottom: 8px;">总分的计算是按照子项的分值来统计的</p>
									  		<p>例：基础积分40分，子项是政治素质方面9分，那么总分不是40分而是9分，如果政治素质还有子项就计算其子项的分值，依次类推</p>
									  	</div>
									</el-popover>
								</div>
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
						<el-table-column label="积分变更项" prop="type"></el-table-column>
						<el-table-column label="积分变更说明" prop="changeDescribes"></el-table-column>
						<el-table-column label="积分变更时间" prop="changeTime" width=200></el-table-column>
						<el-table-column label="积分变更操作">
							<template slot-scope="scope">
								<span 
									:style="getChangeOperation(scope.row.changeOperation)">
									{{scope.row.changeOperation == 1 ? "加分" : "扣分"}}
								</span>
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



		<el-dialog @close="ic_manage_resetChangePartyUserIntegralScoreForm" title="党员积分变更" :visible.sync="ic_manage_changePartyUserIntegralDialog" width="400px">
			<el-form label-width="120px" size="small" :model="changePartyUserIntegralScoreForm" status-icon 
				ref="changePartyUserIntegralScoreForm" label-width="100px" :rules="changePartyUserIntegralScoreFormRules">
				<div>
					<div>
						<el-form-item label="积分项类型" prop="icId">
							<el-select size="small" clearable 
									@change="ic_manage_queryICTForOperation"
									style="width: 180px;"
									v-model="changePartyUserIntegralScoreForm.icId" filterable placeholder="请选择变更类型">
								<el-option
									v-for="item in selectBox.integralType"
								    :key="item.icId"
								    :label="item.type"
								    :value="item.icId">
								</el-option>
							</el-select>
						</el-form-item>
					</div>
					<div>
						<el-form-item label="变更操作" prop="ictId">
							<el-select size="small" clearable 
									style="width: 180px;"
									v-model="changePartyUserIntegralScoreForm.ictId" filterable placeholder="请选择变更分类">
								<el-option
									v-for="item in selectBox.ict"
								    :key="item.ictId"
								    :label="item.type"
								    :value="item.ictId">
								</el-option>
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

		<el-dialog @close="ic_manage_resetOrgIntegralConstituteInfoForm" title="查看并修改积分信息" :visible.sync="ic_manage_initOrgIntegralConstituteDialog" width="70%">
			<div style="width: 100%; margin: 0 10px;">
				<div style="font-size: 14px; color: red; font-weight: bold; ">
					<p>如果要使用积分系统，请初始化积分项中没有设置的的项，未设置的项标记在积分名称的后面</p>
					<p>1、如果出现“分值未设置”，请设置该项的分值</p>
					<p>2、如果出现“加分未设置”、“减分未设置”，请设置党员该项积分加/减分时的分值变更信息</p>
				</div>
				<div style="margin: 10px 0;">
					<el-container>
					  	<el-aside width="50%" style="border-right: 1px solid #ddd;">
					  		<el-tree :expand-on-click-node="false" 
					  			node-key="icId" 
				    			ref="orgInfoTreeOfIntegralConstituteTree"
						    	:highlight-current="true" 
						    	:default-expand-all="true" 
						    	:data="ic_manager_orgInfoTreeOfIntegralConstitute" 
						    	:props="ic_manager_orgInfoTreeOfIntegralConstituteProps" 
						    	@node-click="ic_manage_getIntegralInfo">
						  	</el-tree>
					  	</el-aside>
					  	<el-main>
					  		<div v-show="updateIntegralInfo" style="text-align: center;font-size: 12px;">
					  			请选择要修改的积分项进行修改操作
					  		</div>
					  		<div v-show="!updateIntegralInfo">
					  			<el-form label-width="120px" size="mini" :model="updateIntegralInfoForm" status-icon 
									ref="updateIntegralInfoForm" label-width="100px" :rules="updateIntegralInfoFormRules">
									<div style="border-bottom: 1px solid #ddd;">
										<div>
											<div>
												<el-form-item label="分值类型" prop="type">
													<el-input 
														clearable 
														:disabled="updateIntegralInfoForm.isInnerIntegral == 1 ? true : false"
														style="width: 230px;" 
														size="mini" 
														v-model="updateIntegralInfoForm.type" 
														placeholder="分值类型"></el-input>
												</el-form-item>
											</div>
										</div>
										<div>
											<div>
												<el-form-item label="积分值" prop="integral">
													<el-input 
														clearable 
														style="width: 230px;" 
														size="mini" 
														v-model="updateIntegralInfoForm.integral" 
														placeholder="必须大于零"></el-input>
												</el-form-item>
											</div>
										</div>
										<div>
											<div>
												<el-form-item label="积分说明" prop="describes">
													<el-input 
													  	type="textarea"
													  	size="mini"
													  	style="width: 230px;"
													 	:autosize="{ minRows: 1, maxRows: 2}"
													 	placeholder="积分说明"
														v-model="updateIntegralInfoForm.describes">
													</el-input>
												</el-form-item>
											</div>
										</div>
									</div>
									<div style="border-bottom: 1px solid #ddd; margin-top: 20px;" v-if="!updateIntegralInfoForm.isChildrens">
										<div>
											<div>
												<el-form-item label="操作类型" prop="add_operation">
													<el-input 
														clearable 
														:disabled="true"
														style="width: 230px;" 
														size="mini" 
														v-model="updateIntegralInfoForm.add_operation" 
														placeholder="操作类型"></el-input>
												</el-form-item>
											</div>
										</div>
										<div>
											<div>
												<el-form-item label="建议加分值" prop="add_integral">
													<el-input 
														clearable 
														style="width: 230px;" 
														size="mini" 
														v-model="updateIntegralInfoForm.add_integral" 
														placeholder="分数必须要大于0"></el-input>
												</el-form-item>
											</div>
										</div>
										<div>
											<div>
												<el-form-item label="加分说明" prop="add_describes">
													<el-input 
													  	type="textarea"
													  	size="mini"
													  	style="width: 230px;"
													 	:autosize="{ minRows: 1, maxRows: 2}"
													 	placeholder="加分说明"
														v-model="updateIntegralInfoForm.add_describes">
													</el-input>
												</el-form-item>
											</div>
										</div>
									</div>
									<div style="border-bottom: 1px solid #ddd; margin-top: 20px;" v-if="!updateIntegralInfoForm.isChildrens">
										<div>
											<div>
												<el-form-item label="操作类型" prop="reduce_operation">
													<el-input 
														clearable 
														:disabled="true"
														style="width: 230px;" 
														size="mini" 
														v-model="updateIntegralInfoForm.reduce_operation" 
														placeholder="操作类型"></el-input>
												</el-form-item>
											</div>
										</div>
										<div>
											<div>
												<el-form-item label="建议扣分值" prop="reduce_integral">
													<el-input 
														clearable 
														style="width: 230px;" 
														size="mini" 
														v-model="updateIntegralInfoForm.reduce_integral" 
														placeholder="分数必须要小于0"></el-input>
												</el-form-item>
											</div>
										</div>
										<div>
											<div>
												<el-form-item label="扣分说明" prop="reduce_describes">
													<el-input 
													  	type="textarea"
													  	size="mini"
													  	style="width: 230px;"
													 	:autosize="{ minRows: 1, maxRows: 2}"
													 	placeholder="扣分说明"
														v-model="updateIntegralInfoForm.reduce_describes">
													</el-input>
												</el-form-item>
											</div>
										</div>
									</div>
									<div style="margin-top: 15px; padding-left: 30px;">
										<el-button size="small" type="primary" @click="ic_manage_updateOrgIntegral">变更</el-button>
									</div>
								</el-form>
					  		</div>
					  	</el-main>
					</el-container>
				</div>
			</div>
		</el-dialog>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			updateIntegralInfoForm: {	/*修改积分信息表单*/
				type: null,
				integral: null,
				describes: null,
				add_ictId: null,
				add_operation: "加分",
				add_integral: null,
				add_describes: null,
				reduce_ictId: null,
				reduce_operation: "扣分",
				reduce_integral: null,
				reduce_describes: null,
				icId: null,
				isChildrens: null,
				isInnerIntegral: null,
				currentNode: null
			},
			updateIntegralInfoFormRules: {
				type: [
					{ required: true, message: '请输入分值类型', trigger: 'blur' }
				],
				integral: [
					{ required: true, message: '请输入分值', trigger: 'blur' },
					{ pattern: /^\d+(\.\d{1})?$/, message: '分值输入有误', trigger: 'blur'},
					{
						validator: function(rule, value, callback){
							var icId = appInstince.updateIntegralInfoForm.icId;
							var score = value;

							var url = "/org/ic/integralValidator";
	        				var t = {
        						icId: icId,
        						score: score
	        				}
	        				$.post(url, t, function(data, status){
	        					if (data.code == 200) {
	        						if (data.data == undefined) {
		        						callback();
		        					} else {
		        						callback(new Error(data.data));
		        					}
	        					} else {
	        						callback(new Error('验证出错'));
	        					}
	        					
	        				})
						},
		        		trigger: 'blur'
					}
				],
				add_integral: [
					{ required: true, message: '请输入建议加分值', trigger: 'blur' },
					{ pattern: /^\d+(\.\d{1})?$/, message: '分值输入有误', trigger: 'blur'}
				],
				reduce_integral: [
					{ required: true, message: '请输入建议扣分值', trigger: 'blur' },
					{ pattern: /^-\d+(\.\d{1})?$/, message: '分值输入有误', trigger: 'blur'}
				]
			},
			dis_h_v: false,
			updateIntegralInfo: true,
			ic_manage_changePartyUserIntegralDialog: false,	/*党员积分变更弹窗*/
			ic_manage_initOrgIntegralConstituteDialog: false,	/*初始化组织积分*/
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
				icId: null,	/*用于判断类型选择框是否有值，不提交*/
				ictId: null,
				changeScore: null,
				isMerge: null,
				changeDescribes: null
			},
			selectBox: {
				integralType: [],
				ict: [],
				orgInfo_Ic: []
			},
			changePartyUserIntegralScoreFormRules: {
				ictId: [
					{ required: true, message: '请选择分数变更类型', trigger: 'blur' }
				],
				changeScore: [
					{ required: true, message: '请输入变更分值', trigger: 'blur' },
					{ pattern: /^(-|)?\d+(\.\d{1})?$/, message: '分值输入有误', trigger: 'blur'}
				],
				isMerge: [
					{ required: true, message: '请选择是否计入总分', trigger: 'blur' }
				]
			},
			orgIntegralInfo: {
				integralCount: null
			},
			ic_manager_orgInfoTreeOfIntegralConstitute: [],
			ic_manager_orgInfoTreeOfIntegralConstituteProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.type + "==>" + (_data.data.integral == undefined ? "分值未设置" : _data.data.integral + "分") + 
	            		(_data.data.isChildrens ? "" : (_data.data.isReduceIntegral ? "" : " / 扣分未设置")) + 
	            		(_data.data.isChildrens ? "" : (_data.data.isAddIntegral ? "" : " / 加分未设置"));
	            }
			},
			signInAccountType: null
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.ic_manage_queryOrgInfoForIc();
			this.ic_manage_queryPartyIntegralRecords();	/*搜索积分变更记录*/
			this.money_manage_initSelectBox();
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
			money_manage_initSelectBox() {	/*初始化下拉框*/
				var obj = this;
				url = "/org/ic/queryOrgInfoForIcNotPage";
				t = {

				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.selectBox.orgInfo_Ic = datas.data;
					}
					
				})
			},
			getChangeOperation(changeOperation) {	/*根据条件初始化样式*/
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












			/*点击左侧组织导航栏查询组织的积分信息和组织下党员的积分信息*/
			ic_manage_setOrgIdForQueryPartyUserInfoAndIcInfo(orgId) {	/*点击组织查询组织下的成员和积分信息*/
				var obj = this;
				obj.queryPartyUserInfoAndIcInfoCondition.orgId = orgId;
				obj.ic_manage_queryOrgIntegralInfo();	/*查询积分信息*/
			},
			ic_manage_queryOrgIntegralInfo() {	/*查询组织积分信息*/
				var obj = this;
				var url = "/org/ic/queryOrgIntegralInfo";
				var t = {
					orgId: obj.queryPartyUserInfoAndIcInfoCondition.orgId,
					parentIcId: -1
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined)  {
							if (data.data.integralError) {	/*如果积分里有没有设置分值、加分或扣分时*/
								obj.orgIntegralInfo.integralCount = "NULL";	/*积分值设置未null*/
								obj.ic_manager_openUpdateOrgIntegralConstitute(obj.queryPartyUserInfoAndIcInfoCondition.orgId);
							} else {
								obj.orgIntegralInfo.integralCount = data.data.integralCount;
								obj.ic_manage_queryPartyUserInfoAndIcInfo();	/*组织下的党员信息*/
							}
						}
					}
					
				})
			},
			/*打开积分初始化窗口*/
			ic_manager_openUpdateOrgIntegralConstitute(orgId) {
				var obj = this;

				var url = "/org/ifmt/queryOrgIntegralConstituteToTree";
				var t = {
					orgId: orgId,
					parentIcId: -1
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.ic_manager_orgInfoTreeOfIntegralConstitute = datas.data;
					}
					
				})

				obj.ic_manage_initOrgIntegralConstituteDialog = true;
			},
			ic_manage_getIntegralInfo(data) {	/*点击积分项获取该积分项的信息*/
				var obj = this;
				obj.updateIntegralInfoForm.currentNode = obj.$refs.orgInfoTreeOfIntegralConstituteTree.getCurrentNode();
				obj.$refs.updateIntegralInfoForm.resetFields();
				obj.updateIntegralInfoForm.icId = data.data.icId;
				obj.updateIntegralInfoForm.isChildrens = data.data.isChildrens;

				var url = "/org/ic/queryOrgIntegralConstituteInfo";
				var t = {
					icId: obj.updateIntegralInfoForm.icId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.updateIntegralInfoForm.type = datas.data.type;
						obj.updateIntegralInfoForm.integral = datas.data.integral;
						obj.updateIntegralInfoForm.describes = datas.data.describes;
						obj.updateIntegralInfoForm.add_ictId = datas.data.add_ictId;
						obj.updateIntegralInfoForm.add_operation = datas.data.add_operation;
						obj.updateIntegralInfoForm.add_integral = datas.data.add_integral;
						obj.updateIntegralInfoForm.add_describes = datas.data.add_describes;
						obj.updateIntegralInfoForm.reduce_ictId = datas.data.reduce_ictId;
						obj.updateIntegralInfoForm.reduce_operation = datas.data.reduce_operation;
						obj.updateIntegralInfoForm.reduce_integral = datas.data.reduce_integral;
						obj.updateIntegralInfoForm.reduce_describes = datas.data.reduce_describes;
						obj.updateIntegralInfoForm.isInnerIntegral = datas.data.isInnerIntegral;
					}
					
				})

				obj.updateIntegralInfo = false;	/*显示修改控件*/
			},
			ic_manage_updateOrgIntegral() {
				var obj = this;
				this.$refs.updateIntegralInfoForm.validate( function(valid) {
					if (valid) {
						var url = "/org/ic/updateOrgIntegralConstituteInfo";
						var t = {
							icId: obj.updateIntegralInfoForm.icId,
							type: obj.updateIntegralInfoForm.type,
							integral: obj.updateIntegralInfoForm.integral,
							describes: obj.updateIntegralInfoForm.describes,
							add_ictId: obj.updateIntegralInfoForm.add_ictId,
							add_operation: obj.updateIntegralInfoForm.add_operation,
							add_integral: obj.updateIntegralInfoForm.add_integral,
							add_describes: obj.updateIntegralInfoForm.add_describes,
							reduce_ictId: obj.updateIntegralInfoForm.reduce_ictId,
							reduce_operation: obj.updateIntegralInfoForm.reduce_operation,
							reduce_integral: obj.updateIntegralInfoForm.reduce_integral,
							reduce_describes: obj.updateIntegralInfoForm.reduce_describes
						}
						$.post(url, t, function(datas, status){
							if (datas.code == 200) {
								obj.ic_manage_getIntegralInfo(obj.updateIntegralInfoForm.currentNode);	/*从新获取该积分项的信息*/
								obj.ic_manager_openUpdateOrgIntegralConstitute(obj.queryPartyUserInfoAndIcInfoCondition.orgId);	/*重新获取该积分结构信息*/
								obj.ic_manage_queryOrgIntegralInfo();
							}
							
						})
					}
				})	
			},
			ic_manage_resetOrgIntegralConstituteInfoForm() {	/*关闭积分修改弹窗时*/
				var obj = this;
				obj.updateIntegralInfo = true;
			},
			ic_manage_queryPartyUserInfoAndIcInfo() {	/*查询组织下的党员信息及其积分信息*/
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
			ic_manage_queryPartyIntegralRecords() {	/*查询党员积分记录*/
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

        		var url = "/org/ic/queryOrgIntegralInfo_IcType";
				var t = {
					orgId: obj.queryPartyUserInfoAndIcInfoCondition.orgId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.selectBox.integralType = datas.data;
					}
					
				})

				obj.changePartyUserIntegralScoreForm.partyUserId = row.orgRltUserId;

				obj.ic_manage_changePartyUserIntegralDialog = true;
			},
			ic_manage_queryICTForOperation() {
				var obj = this;
				obj.selectBox.ict = null;
				obj.changePartyUserIntegralScoreForm.ictId = null;
				if (obj.changePartyUserIntegralScoreForm.icId == null || obj.changePartyUserIntegralScoreForm.icId == "") {
					return;
				}

        		url = "/org/ict/queryICT";
				var t = {
					icId: obj.changePartyUserIntegralScoreForm.icId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.selectBox.ict = datas.data;
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
							changeIntegralType: obj.changePartyUserIntegralScoreForm.ictId,
							changeScore: obj.changePartyUserIntegralScoreForm.changeScore,
							isMerge: obj.changePartyUserIntegralScoreForm.isMerge,
							changeTypeId: obj.changePartyUserIntegralScoreForm.icId
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