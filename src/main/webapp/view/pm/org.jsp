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
<title>党委管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<script type="text/javascript" src="/json/address-pca.json"></script>
<style type="text/css">
	body {
		
	}
	.center {
		margin-bottom: 20px;
	}
	.common .el-input__inner {
		width: 160px;
	}
	.el-date-editor {
		width: 160px;
	}
	.el-row {
		margin-bottom: 10px;
	}
	#partyUser_manager_agesdididi {
		text-align: center;
	}
	.detailAddressInput .el-input__inner {
		width: 280px;
	}
	a:link,a:visited{
		text-decoration:none;  /*超链接无下划线*/
		color: red;
	}
	a:hover{
		text-decoration:underline;  /*鼠标放上去有下划线*/
		color: red;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header class="common">
				<el-row class="toolbar" :gutter="20">
			  		<shiro:hasPermission name="org:info:insert">  
				 	    <el-button size="small" type="primary" @click="partyOrg_manager_openInsertOrgInfoDialog">
				 	    	<i class="el-icon-circle-plus-outline"></i>
				 	    	添加组织
				 	    </el-button>
				  	</shiro:hasPermission>
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
								<el-select size="small" clearable 
										@change="partyOrg_manager_queryOrgInfosForOrgType"
										v-model="queryCondition.partyOrg_manager_orgInfoType" filterable placeholder="请选择组织类型">
									<el-option
										v-for="item in partyOrg_manager_orgInfoTypes"
									    :key="item.value"
									    :label="item.orgTypeName"
									    :value="item.orgTypeId">
									</el-option>
								</el-select>
							</el-row>
							<el-row>
								<el-select size="small" clearable 
										@change="partyOrg_manager_queryOrgInfosForProvince"
										@clear="resetCityAndArea"
										v-model="partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince" filterable placeholder="请选择组织所在省份">
									<el-option
										v-for="addressProvince in partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvinces"
										:key="addressProvince.orgInfoCommitteeProvince"
										:label="addressProvince.orgInfoCommitteeProvince"
										:value="addressProvince.orgInfoCommitteeProvince">
									</el-option>
								</el-select>
							</el-row>
							<el-row>
								<el-select size="small" clearable 
										@change="partyOrg_manager_queryOrgInfosForCity"
										@clear="resetArea"
										v-model="partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity" filterable placeholder="请选择组织所在城市">
									<el-option
										v-for="addressCity in partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCitys"
										:key="addressCity.orgInfoCommitteeCity"
										:label="addressCity.orgInfoCommitteeCity"
										:value="addressCity.orgInfoCommitteeCity">
									</el-option>
								</el-select>
							</el-row>
							<el-row>
								<el-select size="small" clearable 
										@change="partyOrg_manager_queryOrgInfosForArea"
										v-model="partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea" filterable placeholder="请选择组织所在区域">
									<el-option
										v-for="addressArea in partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas"
										:key="addressArea.orgInfoCommitteeArea"
										:label="addressArea.orgInfoCommitteeArea"
										:value="addressArea.orgInfoCommitteeArea">
									</el-option>
								</el-select>
							</el-row>
							<el-row>
								<el-input size="small" clearable
									@change="partyOrg_manager_queryOrgInfosForInfoName"
									v-model="queryCondition.partyOrg_manager_orgInfoName" placeholder="请输入组织名"></el-input>
							</el-row>
					  	</div>
					</el-popover>
				</el-row>
			</el-header>
			<el-main>
				<template>
					<el-table size="small" :data="partyOrg_manager_orgInfoPages.list" style="width: 100%">
						<el-table-column type="expand">
							<template slot-scope="scope">
								<el-row :gutter="20" v-if="scope.row.orgLevel1s.length != 0">
									<el-col :span="24">
										<el-collapse>
										  	<el-collapse-item>
										  		<template slot="title">
											    	<div>
											    		<a href="javascript:void(0)">
												    		<i class="el-icon-arrow-down"></i>
												    		<span style="font: bold;">领导风采</span>
												    		<i class="el-icon-arrow-down"></i>
											    		</a>
											    	</div>
											    </template>
											    <div style="background-image: url(/view/pm/img/orgInfoOfLeaderBG.png);padding: 10px">
												    <div style="margin-bottom: 10px" align="center">
														<el-popover 
															class="orgLeaderBG"
															v-for="item in scope.row.orgLevel1s"
															placement="top" 
														  	width="100" 
														  	trigger="hover" >
														  	<span slot="reference" style="margin: 5px"><img :src="getPath(item)" width="100" height="150" /></span>
														  	<div>
														  		<p>{{item.orgDutyName}}：{{item.name}}</p>
														  	</div>
														</el-popover>
													</div>
													<div align="center">
														<el-popover 
															class="orgLeaderBG"
															v-for="item in scope.row.orgLevel2s"
															placement="top" 
														  	width="100" 
														  	trigger="hover" >
														  	<span slot="reference" style="margin: 5px"><img :src="getPath(item)" width="100" height="150" /></span>
														  	<div>
														  		<p>{{item.orgDutyName}}：{{item.name}}</p>
														  	</div>
														</el-popover>
													</div>
												</div>
										  	</el-collapse-item>
										</el-collapse>
									</el-col>
								</el-row>
								<el-row :gutter="20">
									<el-col :span="7">组织名称：{{scope.row.orgInfoName}}</el-col>
									<el-col :span="3">组织类型：{{scope.row.orgTypeName}}</el-col>
								</el-row>
								<el-row :gutter="20">
									<el-col :span="24">
										管委会地址：
										{{scope.row.orgInfoCommitteeProvince}}-
										{{scope.row.orgInfoCommitteeCity}}-
										{{scope.row.orgInfoCommitteeArea}}-
										{{scope.row.orgInfoCommitteeDetail}}
									</el-col>
								</el-row>
								<el-row :gutter="20" v-for="item in scope.row.orgLevel1s">
									<el-col :span="3">{{item.orgDutyName}}：{{item.name}}</el-col>
									<el-col :span="3">性别：{{item.sex}}</el-col>
									<el-col :span="3">年龄：{{item.age}}</el-col>
									<el-col :span="6">联系电话：{{item.mobilePhone}}</el-col>
								</el-row>
								<el-row :gutter="20">
									<el-col :span="3">共有成员：<a @click="partyOrg_manager_setOrgInfoIdOfShowThisOrgPeoples(scope.row)" href="javascript:void(0)">{{scope.row.orgMemberNum}} 人</a></el-col>
									<el-col :span="3">共有下属组织：<a @click="partyOrg_manager_setOrgInfoIdOfShowThisOrgChildrens(scope.row)" href="javascript:void(0)">{{scope.row.orgChildrensNum}} 个</a></el-col>
								</el-row>
							</template>
						</el-table-column>
						<el-table-column label="组织ID" prop="orgInfoId"></el-table-column>
						<el-table-column label="组织类型" prop="orgTypeName"></el-table-column>
						<el-table-column label="组织名" prop="orgInfoName"></el-table-column>
						<el-table-column label="组织管理所在省" prop="orgInfoCommitteeProvince"></el-table-column>
						<el-table-column label="组织管理所在市" prop="orgInfoCommitteeCity"></el-table-column>
						<el-table-column label="操作" width=270>
							<template slot-scope="scope">
								<shiro:hasPermission name="party:user:delete">  
									<el-button @click="partyOrg_manager_deleteOrgInfo(scope.row)" type="text" size="small">删除</el-button>
								</shiro:hasPermission>
								<shiro:hasPermission name="party:user:update">  
									<el-button @click="partyOrg_manager_openUpdateOrgInfoDialog(scope.row)" type="text" size="small">修改信息</el-button>
								</shiro:hasPermission>
								<el-button @click="partyOrg_manager_openInsertOrgDutyDialog(scope.row)" type="text" size="small">添加职责</el-button>
							</template>
						</el-table-column>
					</el-table>
				</template>
			</el-main>
			<el-footer>
				<el-pagination id="partyUser_manager_agesdididi" 
				  	layout="total, prev, pager, next, jumper" 
	      		 	@current-change="partyOrg_manager_pagerCurrentChange"
				  	:current-page.sync="partyOrg_manager_orgInfoPages.pageNum"
				  	:page-size.sync="partyOrg_manager_orgInfoPages.pageSize"
				  	:total="partyOrg_manager_orgInfoPages.total">
				</el-pagination>
			</el-footer>
		</el-container>




		<el-dialog @close="" title="下属组织信息" :visible.sync="partyOrg_manager_showThisOrgChildrensDialog" width="82%">
			<el-container>
				<el-main>
					<el-table style="text-align: center;" 
							align="center"
							:stripe="true"
							class="common" border size="small" :data="partyOrg_manager_ThisOrgInfos.childrens_pager.list" style="width: 100%">
						<el-table-column label="组织ID" prop="orgInfoId"></el-table-column>
						<el-table-column label="组织类型" prop="orgTypeName"></el-table-column>
						<el-table-column label="组织名" prop="orgInfoName"></el-table-column>
						<el-table-column label="组织管理所在省" prop="orgInfoCommitteeProvince"></el-table-column>
						<el-table-column label="组织管理所在市" prop="orgInfoCommitteeCity"></el-table-column>
					</el-table>
				</el-main>
				<el-footer>
					<el-pagination id="partyUser_manager_agesdididi" 
					  	layout="total, prev, pager, next, jumper" 
		      		 	@current-change="partyOrg_manager_thisOrgChildrenCurrentChange"
					  	:current-page.sync="partyOrg_manager_ThisOrgInfos.childrens_pager.pageNum"
					  	:page-size.sync="partyOrg_manager_ThisOrgInfos.childrens_pager.pageSize"
					  	:total="partyOrg_manager_ThisOrgInfos.childrens_pager.total">
					</el-pagination>
				</el-footer>
			</el-container>
		</el-dialog>


		<el-dialog @close="" title="组织下成员信息" :visible.sync="partyOrg_manager_showThisOrgPeoplesDialog" width="82%">
			<el-container>
				<el-main>
					<el-table style="text-align: center;" 
							align="center"
							:stripe="true"
							class="common" border size="small" :data="partyOrg_manager_ThisOrgInfos.peoples_pager.list" style="width: 100%">
						<el-table-column label="姓名" prop="name"></el-table-column>
						<el-table-column label="性别" prop="sex"></el-table-column>
						<el-table-column label="民族" prop="nationName"></el-table-column>
						<el-table-column label="生日" prop="birthDate"></el-table-column>
						<el-table-column label="年龄" prop="age"></el-table-column>
						<el-table-column label="学历" prop="educationName"></el-table-column>
						<el-table-column label="学位" prop="academicDegreeName"></el-table-column>
						<el-table-column label="党员类型" prop="partyType"></el-table-column>
						<el-table-column label="党员状态" prop="partyStatus"></el-table-column>
						<el-table-column label="职责" prop="orgDutyName"></el-table-column>
					</el-table>
				</el-main>
				<el-footer>
					<el-pagination id="partyUser_manager_agesdididi" 
					  	layout="total, prev, pager, next, jumper" 
		      		 	@current-change="partyOrg_manager_thisOrgPeopleCurrentChange"
					  	:current-page.sync="partyOrg_manager_ThisOrgInfos.peoples_pager.pageNum"
					  	:page-size.sync="partyOrg_manager_ThisOrgInfos.peoples_pager.pageSize"
					  	:total="partyOrg_manager_ThisOrgInfos.peoples_pager.total">
					</el-pagination>
				</el-footer>
			</el-container>
		</el-dialog>


		<el-dialog @close="partyOrg_manager_resetinsertOrgInfoDutyForm()" class="common" @close="" title="增加职责" :visible.sync="partyOrg_manager_insertOrgDutyDialog" width="70%">
			<el-form label-width="120px" size="small" :model="partyOrg_manager_insertOrgInfoDutyForm" status-icon :rules="partyOrg_manager_insertOrgInfoDutyRules" 
				ref="partyOrg_manager_insertOrgInfoDutyForm" label-width="100px">
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="职责名" prop="orgDutyName">
						    <el-input clearable v-model="partyOrg_manager_insertOrgInfoDutyForm.orgDutyName" placeholder="职责名（经理、书记...）"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="职责描述" prop="orgDutyDescribe">
							<el-input clearable
							  	type="textarea"
							 	:autosize="{ minRows: 2}"
							 	placeholder="请输入对此职责描述的内容"
								v-model="partyOrg_manager_insertOrgInfoDutyForm.orgDutyDescribe">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="选择上级职责" prop="orgDutyParentId">
						    <el-tree :expand-on-click-node="false" 
						    	:highlight-current="true" 
						    	:data="partyOrg_manager_insertOrgInfoDutyForm.orgDutyTreesForOrgInfo" 
						    	:props="partyOrg_manager_orgInfoDutyTreesProps" 
						    	@node-click="partyOrg_manager_setOrgDutyParentId">
						  	</el-tree>
						</el-form-item>
					</el-col>
				</el-row>

				<el-form-item>
				    <el-button type="primary" @click="partyOrg_manager_insertOrgInfoDuty">添加职责</el-button>
				    <el-button @click="partyOrg_manager_resetinsertOrgInfoDutyForm">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>


		<el-dialog class="common" @close="" title="修改组织信息" :visible.sync="partyOrg_manager_updateOrgInfoDialog" width="70%">
			<el-form label-width="120px" size="small" :model="partyOrg_manager_updateOrgInfoForm" status-icon :rules="partyOrg_manager_updateOrgInfoRules" 
				ref="partyOrg_manager_updateOrgInfoForm" label-width="100px">
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="组织类型" prop="orgInfoTypeId">
						    <el-select :disabled="true" clearable v-model="partyOrg_manager_updateOrgInfoForm.orgInfoTypeId" placeholder="请选择">
							    <el-option
							      v-for="item in partyOrg_manager_orgInfoTypes"
							      :key="item.value"
							      :label="item.orgTypeName"
							      :value="item.orgTypeId">
							    </el-option>
						  	</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="组织名" prop="orgInfoName">
						    <el-input clearable v-model="partyOrg_manager_updateOrgInfoForm.orgInfoName" placeholder="组织名"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="8">
						<el-form-item label="组织办公地址" prop="orgInfoCommittee_pca">
						    <el-cascader clearable clearable :props="partyOrg_manager_address_prop"
								v-model="partyOrg_manager_updateOrgInfoForm.orgInfoCommittee_pca"
								separator="/"
								placeholder="可搜索地点" :options="partyOrg_manager_address_pca" 
								filterable >
							</el-cascader>
						</el-form-item>
					</el-col>
					<el-col :span="16">
						<el-form-item class="detailAddressInput" label="详细地址" prop="orgInfoCommitteeDetail">
						    <el-input clearable v-model="partyOrg_manager_updateOrgInfoForm.orgInfoCommitteeDetail" placeholder="详细地址"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="组织管理范围描述：" prop="orgInfoManageDetail">
							<el-input clearable
							  	type="textarea"
							 	:autosize="{ minRows: 2}"
							 	placeholder="请输入内容"
								v-model="partyOrg_manager_updateOrgInfoForm.orgInfoManageDetail">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="组织简介/介绍：" prop="orgInfoDescribe">
							<el-input clearable
							  	type="textarea"
							 	:autosize="{ minRows: 2}"
							 	placeholder="请输入内容"
								v-model="partyOrg_manager_updateOrgInfoForm.orgInfoDescribe">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-form-item>
				    <el-button type="primary" @click="partyOrg_manager_updateOrgInfo">提交跟新</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

		<el-dialog class="common" @close="" title="添加组织信息" :visible.sync="partyOrg_manager_insertOrgInfoDialog" width="70%">
			<el-form label-width="120px" size="small" :model="partyOrg_manager_insertOrgInfoForm" status-icon :rules="partyOrg_manager_insertOrgInfoRules" 
				ref="partyOrg_manager_insertOrgInfoForm" label-width="100px">
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="组织类型" prop="orgInfoTypeId">
						    <el-select clearable v-model="partyOrg_manager_insertOrgInfoForm.orgInfoTypeId" placeholder="请选择">
							    <el-option
							      v-for="item in partyOrg_manager_orgInfoTypes"
							      :key="item.value"
							      :label="item.orgTypeName"
							      :value="item.orgTypeId">
							    </el-option>
						  	</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="组织名" prop="orgInfoName">
						    <el-input clearable v-model="partyOrg_manager_insertOrgInfoForm.orgInfoName" placeholder="组织名"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="8">
						<el-form-item label="组织办公地址" prop="orgInfoCommittee_pca">
						    <el-cascader clearable clearable :props="partyOrg_manager_address_prop"
								v-model="partyOrg_manager_insertOrgInfoForm.orgInfoCommittee_pca"
								separator="/"
								placeholder="可搜索地点" :options="partyOrg_manager_address_pca" 
								filterable >
							</el-cascader>
						</el-form-item>
					</el-col>
					<el-col :span="16">
						<el-form-item class="detailAddressInput" label="详细地址" prop="orgInfoCommitteeDetail">
						    <el-input clearable v-model="partyOrg_manager_insertOrgInfoForm.orgInfoCommitteeDetail" placeholder="详细地址"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="组织管理范围描述：" prop="orgInfoManageDetail">
							<el-input clearable
							  	type="textarea"
							 	:autosize="{ minRows: 2}"
							 	placeholder="请输入内容"
								v-model="partyOrg_manager_insertOrgInfoForm.orgInfoManageDetail">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="组织简介/介绍：" prop="orgInfoDescribe">
							<el-input clearable
							  	type="textarea"
							 	:autosize="{ minRows: 2}"
							 	placeholder="请输入内容"
								v-model="partyOrg_manager_insertOrgInfoForm.orgInfoDescribe">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="组织层级关系" prop="orgInfoParentId">
						    <el-tree :expand-on-click-node="false" 
						    	:highlight-current="true" 
						    	:data="partyOrg_manager_orgInfoTreeOfInsert" 
						    	:props="partyOrg_manager_orgInfoTreeOfInsertProps" 
						    	@node-click="partyOrg_manager_setOrgInfoParentId">
						  	</el-tree>
						</el-form-item>
					</el-col>
				</el-row>
				<el-form-item>
				    <el-button type="primary" @click="partyOrg_manager_insertOrgInfo">添加组织</el-button>
				    <el-button @click="partyOrg_manager_resetinsertOrgInfoForm">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</body>

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			partyOrg_manager_address_pca: pca,	/* 省市区三级联动数据 */
			partyOrg_manager_address_prop: {	/* 地址prop */
				value: "name",
				label: "name",
				children: "children"
			},
			partyOrg_manager_orgInfoPages: {
				pageNum: 1,		/* 当前页 */
				pageSize: 10,	/* 页面大小 */
				total: 0,
				list: []
			},
			partyOrg_manager_address: {
				partyOrg_manager_orgInfoCommitteeProvinces: [],	/*组织信息所在省份*/
				partyOrg_manager_orgInfoCommitteeProvince: null,
				partyOrg_manager_orgInfoCommitteeCitys: [],	/*组织信息所在城市*/
				partyOrg_manager_orgInfoCommitteeCity: null,
				partyOrg_manager_orgInfoCommitteeAreas: [],	/*组织信息所在区域*/
				partyOrg_manager_orgInfoCommitteeArea: null
			},
			queryCondition: {
				partyOrg_manager_orgInfoType: null,	/*用于保存使用组织类型查询*/
				partyOrg_manager_orgInfoName: null	/*用于保存使用组织名模糊查询*/
			},
			partyOrg_manager_insertOrgInfoDialog: false,	/*添加组织信息*/
			partyOrg_manager_updateOrgInfoDialog: false, 	/*修改组织信息*/
			partyOrg_manager_insertOrgDutyDialog: false,	/*添加组织职责*/
			partyOrg_manager_showThisOrgPeoplesDialog: false,	/*查看这个组织下成员信息弹窗*/
			partyOrg_manager_showThisOrgChildrensDialog: false, 	/*下属组织信息*/
			partyOrg_manager_orgInfoTreeOfInsert: [],	/*添加组织信息时的组织关系树*/
			partyOrg_manager_orgInfoTreeOfInsertProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
			partyOrg_manager_orgInfoTypes:[],	/*添加组织信息时选择组织类型*/
			partyOrg_manager_insertOrgInfoForm: {	/*添加党员的信息*/
				orgInfoTypeId: null,
				orgInfoName: null,
				orgInfoCommittee_pca: null,
				orgInfoCommitteeDetail: null,
				orgInfoManageDetail: null,
				orgInfoDescribe: null,
				orgInfoParentId: -1
			},
			partyOrg_manager_insertOrgInfoRules: {	/*添加党员验证*/
				orgInfoName: [
		    		{ required: true, message: '请输入组织名!', trigger: 'blur' }
		    	],
		    	orgInfoTypeId: [
		    		{ required: true, message: '请选择组织类型!', trigger: 'blur' }
		    	],
		    	orgInfoCommittee_pca: [
		    		{ required: true, message: '请选择省市区!', trigger: 'blur' }
		    	],
		    	orgInfoCommitteeDetail: [
		    		{ required: true, message: '请输入管委会详细地址!', trigger: 'blur' }
		    	]
			},
			partyOrg_manager_updateOrgInfoForm: {	/*修改党员信息*/
				orgInfoId: null,
				orgInfoTypeId: null,
				orgInfoName: null,
				orgInfoCommittee_pca: null,
				orgInfoCommitteeProvince: null,
				orgInfoCommitteeCity: null,
				orgInfoCommitteeArea: null,
				orgInfoCommitteeDetail: null,
				orgInfoManageDetail: null,
				orgInfoDescribe: null
			},
			partyOrg_manager_updateOrgInfoRules: {	/*修改党员信息字段验证*/
				orgInfoName: [
		    		{ required: true, message: '请输入组织名!', trigger: 'blur' }
		    	],
		    	orgInfoTypeId: [
		    		{ required: true, message: '请选择组织类型!', trigger: 'blur' }
		    	],
		    	orgInfoCommittee_pca: [
		    		{ required: true, message: '请选择省市区!', trigger: 'blur' }
		    	],
		    	orgInfoCommitteeDetail: [
		    		{ required: true, message: '请输入管委会详细地址!', trigger: 'blur' }
		    	]
			},
			partyOrg_manager_insertOrgInfoDutyForm: {	/*给组织添加职责信息*/
				orgDutyName: null,
				orgDutyDescribe: null,
				orgDutyTreesForOrgInfo: [],
				orgDutyOrgInfoId: null,
				orgDutyParentId: -1
			},
			partyOrg_manager_orgInfoDutyTreesProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgDutyName;
	            }
			},
			partyOrg_manager_insertOrgInfoDutyRules: {
				orgDutyName: [
		    		{ required: true, message: '请输入职责名称!', trigger: 'blur' }
		    	]
			},
			partyOrg_manager_ThisOrgInfos: {	/*当前组织的信息*/
				orgInfoId: null,
				peoples_pager: {
					pageNum: 1,		/* 当前页 */
			    	pageSize: 10,	/* 页面大小 */
			    	total: 0,
			    	list: []
				},
				childrens_pager: {
					pageNum: 1,		/* 当前页 */
			    	pageSize: 10,	/* 页面大小 */
			    	total: 0,
			    	list: []
				}
			}
		},
		created: function () {
			this.getScreenHeightForPageSize();
		},
		mounted:function () {
			this.partyOrg_manager_queryOrgInfosForMap()	/*查询组织信息*/
			this.partyOrg_manager_queryOrgInfosCommitteeProvince();
			this.partyOrg_manager_getOrgInfoTypes();
		},
		methods: {
			getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
				var obj = this;
				var height = window.screen.height;
				if (height > 2000) {
					obj.partyOrg_manager_orgInfoPages.pageSize = 25;
				} else if (height > 1700) {
					obj.partyOrg_manager_orgInfoPages.pageSize = 22;
				} else if (height > 1400) {
					obj.partyOrg_manager_orgInfoPages.pageSize = 19;
				} else if (height > 1100) {
					obj.partyOrg_manager_orgInfoPages.pageSize = 16;
				} else if (height > 900) {
					obj.partyOrg_manager_orgInfoPages.pageSize = 13;
				} else {
					obj.partyOrg_manager_orgInfoPages.pageSize = 9;
				}
			},
			partyOrg_manager_initPager() {	/* 初始化页面数据 */
				var obj = this;
				obj.partyOrg_manager_orgInfoPages.pageNum = 1;
				obj.partyOrg_manager_orgInfoPages.pageSize = 10;
				obj.partyOrg_manager_orgInfoPages.total = 0;
				obj.partyOrg_manager_orgInfoPages.list = new Array();
			},
			partyOrg_manager_pagerCurrentChange () {
				var obj = this;
				obj.partyOrg_manager_queryOrgInfosForMap();
			},
			partyOrg_manager_queryOrgInfosForMap() {	/* 查询组织信息 */
        		var obj = this;
				var url = "/org/ifmt/queryOrgInfosForMap";
				var t = {
					pageNum: obj.partyOrg_manager_orgInfoPages.pageNum,
					pageSize: obj.partyOrg_manager_orgInfoPages.pageSize,
					orgInfoTypeId: obj.queryCondition.partyOrg_manager_orgInfoType,
					orgInfoName: obj.queryCondition.partyOrg_manager_orgInfoName,
					orgInfoCommitteeProvince: obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince,
					orgInfoCommitteeCity: obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity,
					orgInfoCommitteeArea: obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.partyOrg_manager_initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.partyOrg_manager_orgInfoPages = data.data;
						}
					}
					
				})
        	},
        	partyOrg_manager_queryOrgInfosCommitteeProvince(){	/*查询组织省份信息下拉框*/
        		var obj = this;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvinces = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince = null;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCitys = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity = null;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea = null;

				var url = "/org/ifmt/queryOrgInfosCommitteeProvince";
				var t = {
					orgInfoTypeId: obj.queryCondition.partyOrg_manager_orgInfoType
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvinces = data.data;
					}
					
				})
        	},
        	partyOrg_manager_queryOrgInfosForOrgType() {	/*组织类型下拉框选择是查询组织信息*/
        		var obj = this;
        		obj.partyOrg_manager_queryOrgInfosCommitteeProvince();
        		obj.partyOrg_manager_queryOrgInfosForMap();
        	},
        	partyOrg_manager_queryOrgInfosForInfoName() {	/*根据名字模糊查询组织信息*/
        		var obj = this;
        		obj.partyOrg_manager_queryOrgInfosForMap();
        	},
        	partyOrg_manager_queryOrgInfosForProvince() {	/*省份下拉框选择时查询组织信息和城市信息*/
        		var obj = this;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCitys = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity = null;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea = null;
        		obj.partyOrg_manager_queryOrgInfosForMap();

        		if (obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince != null &&
        			 obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince != '') {	/*表示下拉框值改变，没有清空，查询城市信息*/
        			var obj = this;
					var url = "/org/ifmt/queryOrgInfosCommitteeCity";
					var t = {
						orgInfoCommitteeProvince: obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince
					}
					$.post(url, t, function(data, status){
						if (data.code == 200) {
							obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCitys = data.data;
						}
						
					})
        		}
        		
        	},
        	partyOrg_manager_queryOrgInfosForCity() {	/*城市下拉框选择时查询组织信息和地区信息*/
        		var obj = this;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea = null;
        		obj.partyOrg_manager_queryOrgInfosForMap();

        		if(obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity != null && 
        				obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity != '') {	/*表示下拉框值改变，没有清空，查询地区信息*/
        			var obj = this;
					var url = "/org/ifmt/queryOrgInfosCommitteeArea";
					var t = {
						orgInfoCommitteeCity: obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity
					}
					$.post(url, t, function(data, status){
						if (data.code == 200) {
							obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas = data.data;
						}
						
					})
        		}
        		
        	},
        	partyOrg_manager_queryOrgInfosForArea() {	/*地区下拉框选择时查询组织信息*/
        		var obj = this;
        		obj.partyOrg_manager_queryOrgInfosForMap();
        	},
        	resetCityAndArea() {	/*表示执行省下拉框清空，清空城市地区信息*/
        		var obj = this;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCitys = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity = null;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea = null;
        	},
        	resetArea() {	/*表示执行城市下拉框清空，清空地区信息*/
        		var obj = this;
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeAreas = [];
        		obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea = null;
        	},
        	partyOrg_manager_openInsertOrgInfoDialog() {	/*打开添加组织信息弹窗*/
        		var obj = this;
        		obj.partyOrg_manager_insertOrgInfoDialog = true;

        		var obj = this;
				var url = "/org/ifmt/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_orgInfoTreeOfInsert = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									orgInfoId: -1,
									orgInfoName: "顶层组织 （不选默认顶级组织）"
								},
								children: datas.data
							}
						];
					}
					
				})
        	},
        	partyOrg_manager_getOrgInfoTypes() {	/*组织类型*/
        		var obj = this;
        		url = "/org/type/queryOrgTypesNotPage";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_orgInfoTypes = datas.data;
					}
					
				})
        	},
        	partyOrg_manager_setOrgInfoParentId(data) {	/*设置组织层级关系值*/
        		var obj = this;
				obj.partyOrg_manager_insertOrgInfoForm.orgInfoParentId = data.data.orgInfoId;
        	},
        	partyOrg_manager_resetinsertOrgInfoForm() {	/*重置新增组织表单*/
        		var obj = this;
        		obj.$refs.partyOrg_manager_insertOrgInfoForm.resetFields();
        	},
        	partyOrg_manager_insertOrgInfo() {	/*新增组织信息*/
        		var obj = this;
        		this.$refs.partyOrg_manager_insertOrgInfoForm.validate( function(valid) {
        			if (valid) {
        				var url = "/org/ifmt/insertOrgInfo";
        				var t = {
        					orgInfoTypeId: obj.partyOrg_manager_insertOrgInfoForm.orgInfoTypeId,
        					orgInfoName: obj.partyOrg_manager_insertOrgInfoForm.orgInfoName,
        					orgInfoCommitteeProvince: obj.partyOrg_manager_insertOrgInfoForm.orgInfoCommittee_pca[0],
        					orgInfoCommitteeCity: obj.partyOrg_manager_insertOrgInfoForm.orgInfoCommittee_pca[1],
        					orgInfoCommitteeArea: obj.partyOrg_manager_insertOrgInfoForm.orgInfoCommittee_pca[2],
        					orgInfoCommitteeDetail: obj.partyOrg_manager_insertOrgInfoForm.orgInfoCommitteeDetail,
        					orgInfoManageDetail: obj.partyOrg_manager_insertOrgInfoForm.orgInfoManageDetail,
        			    	orgInfoDescribe: obj.partyOrg_manager_insertOrgInfoForm.orgInfoDescribe,
        			    	orgInfoParentId: obj.partyOrg_manager_insertOrgInfoForm.orgInfoParentId
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('添加成功',data.msg,'success');
        						obj.partyOrg_manager_resetinsertOrgInfoForm();
        						obj.partyOrg_manager_insertOrgInfoDialog = false;
        						obj.partyOrg_manager_queryOrgInfosForMap();
        						obj.partyOrg_manager_queryOrgInfosCommitteeProvince();	/*重新查询地址-省*/
        					}
        					
        				})
        			}
        		})
        	},
        	partyOrg_manager_openUpdateOrgInfoDialog(row) {	/*打开组织修改弹窗*/
        		var obj = this;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoId = row.orgInfoId;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoTypeId = row.orgInfoTypeId;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoName = row.orgInfoName;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommittee_pca = [row.orgInfoCommitteeProvince, row.orgInfoCommitteeCity, row.orgInfoCommitteeArea];
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommitteeDetail = row.orgInfoCommitteeDetail;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoManageDetail = row.orgInfoManageDetail;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoDescribe = row.orgInfoDescribe;

        		obj.partyOrg_manager_updateOrgInfoDialog = true;
        	},
        	partyOrg_manager_updateOrgInfo() {	/*更新组织信息*/
        		var obj = this;
        		this.$refs.partyOrg_manager_updateOrgInfoForm.validate( function(valid) {
        			if (valid) {
        				var url = "/org/ifmt/updateOrgInfo";
        				obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommitteeProvince = obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommittee_pca[0];
						obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommitteeCity = obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommittee_pca[1];
						obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommitteeArea = obj.partyOrg_manager_updateOrgInfoForm.orgInfoCommittee_pca[2];
						var t = obj.partyOrg_manager_updateOrgInfoForm;
						$.post(url, t, function(data, status){
							if (data.code == 200) {	/*添加成功*/
								obj.partyOrg_manager_updateOrgInfoDialog = false;
								toast('更新成功',data.msg,'success');
        						obj.partyOrg_manager_queryOrgInfosForMap();
        						obj.partyOrg_manager_queryOrgInfosCommitteeProvince();	/*重新查询地址-省*/
							} else {
								toast('更新失败',data.msg,'error');
							}

						})
        			}
        		})
        	},
        	partyOrg_manager_deleteOrgInfo(row) {	/*删除组织及子组织信息*/
        		var obj = this;
				obj.$confirm(
					'会删除此组织及下属组织的信息, 是否继续?', 
					'警示', 
					{
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
		        	}
		        ).then(function(){
	        		var url = "/org/ifmt/deleteOrgInfo";
					var t = {
						orgInfoId: row.orgInfoId
					}
					$.post(url, t, function(data, status){
						if (data.code == 200) {
							toast('删除成功',data.msg,'success');
							obj.partyOrg_manager_queryOrgInfosForMap();
    						obj.partyOrg_manager_queryOrgInfosCommitteeProvince();	/*重新查询地址-省*/
						}
						
					})
		        }).catch(function(){
		        	obj.$message({
			            type: 'info',
			            message: '已取消删除'
			        });  
		        });
        	},
        	partyOrg_manager_openInsertOrgDutyDialog(row) {	/*打开添加职责窗口*/
        		var obj = this;
        		obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyOrgInfoId = row.orgInfoId;

        		var url = "/org/duty/queryOrgDutyTreeForOrgInfo";
				var t = {
					orgDutyOrgInfoId: row.orgInfoId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyTreesForOrgInfo = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									orgDutyId: -1,
									orgDutyName: "新的顶级职责（不选默认）"
								},
								children: datas.data
							}
						];
					}
					
				})

        		obj.partyOrg_manager_insertOrgDutyDialog = true;
        	},
        	partyOrg_manager_setOrgDutyParentId(data) {	/*点击设置组织关系id*/
        		var obj = this;
        		obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyParentId = data.data.orgDutyId;
        	},
        	partyOrg_manager_resetinsertOrgInfoDutyForm() {	/*重置表单*/
        		var obj = this;
        		obj.$refs.partyOrg_manager_insertOrgInfoDutyForm.resetFields();
        	},
        	partyOrg_manager_insertOrgInfoDuty() {	/*为组织添加职责*/
        		var obj = this;

        		this.$refs.partyOrg_manager_insertOrgInfoDutyForm.validate( function(valid) {
        			if (valid) {
        				var url = "/org/duty/insertOrgDuty";
						var t = {
							orgDutyName: obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyName,
							orgDutyDescribe: obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyDescribe,
							orgDutyOrgInfoId: obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyOrgInfoId,
							orgDutyParentId: obj.partyOrg_manager_insertOrgInfoDutyForm.orgDutyParentId
						}
						$.post(url, t, function(datas, status){
							if (datas.code == 200) {
								toast('添加成功',data.msg,'success');
								obj.partyOrg_manager_resetinsertOrgInfoDutyForm();
								obj.partyOrg_manager_insertOrgDutyDialog = false;
							}
							
						})
        			}
        		})
        	},
        	thisOrgInfoPeoplesPagerInit() {
        		var obj = this;
				obj.partyOrg_manager_ThisOrgInfos.peoples_pager.pageNum = 1;
				obj.partyOrg_manager_ThisOrgInfos.peoples_pager.pageSize = 10;
				obj.partyOrg_manager_ThisOrgInfos.peoples_pager.total = 0;
				obj.partyOrg_manager_ThisOrgInfos.peoples_pager.list = new Array();
        	},
        	partyOrg_manager_setOrgInfoIdOfShowThisOrgPeoples(row) {
        		var obj = this;
        		obj.partyOrg_manager_ThisOrgInfos.orgInfoId = row.orgInfoId;
        		obj.partyOrg_manager_showThisOrgPeoples();
        	},
        	partyOrg_manager_showThisOrgPeoples() {	/*查看当前组织的人员*/
        		var obj = this;

				var url = "/org/relation/queryOrgRelationNews";
				var t = {
					pageNum: obj.partyOrg_manager_ThisOrgInfos.peoples_pager.pageNum,
					pageSize: obj.partyOrg_manager_ThisOrgInfos.peoples_pager.pageSize,
					orgRltInfoId: obj.partyOrg_manager_ThisOrgInfos.orgInfoId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.thisOrgInfoPeoplesPagerInit();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.partyOrg_manager_ThisOrgInfos.peoples_pager = data.data;
						}
					}

				})

        		obj.partyOrg_manager_showThisOrgPeoplesDialog = true;
        	},
        	partyOrg_manager_thisOrgPeopleCurrentChange() {	/*分页查询*/
        		var obj = this;
        		obj.partyOrg_manager_showThisOrgPeoples();
        	},
        	getPath(row) {	/* 得到党员用户id并返回请求路径 */
				return "/party/user/getPartyUserInfoIdPhoto?partyId="+row.baseUserId;
			},
			thisOrgInfoChildrensPagerInit() {
        		var obj = this;
				obj.partyOrg_manager_ThisOrgInfos.childrens_pager.pageNum = 1;
				obj.partyOrg_manager_ThisOrgInfos.childrens_pager.pageSize = 10;
				obj.partyOrg_manager_ThisOrgInfos.childrens_pager.total = 0;
				obj.partyOrg_manager_ThisOrgInfos.childrens_pager.list = new Array();
        	},
			partyOrg_manager_setOrgInfoIdOfShowThisOrgChildrens(row) {
        		var obj = this;
        		obj.partyOrg_manager_ThisOrgInfos.orgInfoId = row.orgInfoId;
        		obj.partyOrg_manager_showThisOrgChildrens();
        	},
			partyOrg_manager_showThisOrgChildrens() {
				var obj = this;

				var url = "/org/ifmt/queryThisOrgChildren";
				var t = {
					pageNum: obj.partyOrg_manager_ThisOrgInfos.childrens_pager.pageNum,
					pageSize: obj.partyOrg_manager_ThisOrgInfos.childrens_pager.pageSize,
					orgInfoId: obj.partyOrg_manager_ThisOrgInfos.orgInfoId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.thisOrgInfoChildrensPagerInit();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.partyOrg_manager_ThisOrgInfos.childrens_pager = data.data;
						}
					}

				})

				obj.partyOrg_manager_showThisOrgChildrensDialog = true;
			},
			partyOrg_manager_thisOrgChildrenCurrentChange() {
				var obj = this;
        		obj.partyOrg_manager_showThisOrgChildrens();
			}
		}
	});
</script>
</html>