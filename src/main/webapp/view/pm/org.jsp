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
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.6&key=b8db1a2a77d2226ba663235353e3546b&plugin=AMap.Geocoder"></script>
<%@include file="/include/echarts.jsp"%>
<script type="text/javascript" src="/js/jquery.orgchart.js"></script>
<link rel="stylesheet" href="/css/jquery.orgchart.css" />
<style type="text/css">
	body {
		
	}
	.center {
		margin-bottom: 20px;
	}
	.el-select.el-select--small {
		width: 220px;
	}
	.el-input.el-input--small {
		width: 220px;
	}
	.el-textarea.el-input--small {
		width: 220px;
	}
	.el-tree.el-tree--highlight-current {
		width: 220px;
	}
	.el-date-editor {
		width: 220px;
	}
	.el-row {
		margin-bottom: 10px;
	}
	#partyUser_manager_agesdididi {
		text-align: center;
	}
	a:link,a:visited{
		text-decoration:none;  /*超链接无下划线*/
		color: red;
	}
	a:hover{
		text-decoration:underline;  /*鼠标放上去有下划线*/
		color: red;
	}
	div[id*="container"] {	/*地图*/
		width: 100%;
		height: 230px;
		margin: 10px 0;
		border-radius: 10px;
   		box-shadow:3px 3px 10px #909090;
	}
	.zzcy,.xszz{
		width: 45%;
		height: 200px;
		background-color: #f3f2eb;
		border-radius:10px;
		margin: 10px;
		float: left;
		text-align: center;
		overflow: hidden;
		position: relative;
		transition: transform 0.2s,box-shadow 0.3s;
	}
	.zzcy{
		margin-left: 0px;
	}
	.zzcy:hover,.xszz:hover{
		box-shadow: 0 8px 15px rgba(0,0,0,0.15);
		cursor: pointer;
		
	}
	.xszz {
		float: right;
		margin-right: 0px;
	}
	.zzcy .title, .xszz .title{
		margin:10px ;
		font-size: 18px;
		font-weight: bold;
		color: #999;
		text-align: center;
	}
	
	.woman,.man{
		text-align: left;
		padding-left: 20px;
		margin-bottom: 5px;
		float: left;
		text-align: center;
	}
	.woman-image{
		width: 50px;
		height: 70px;
		display: inline-block;
		background: url(/view/pm/img/woman.png) no-repeat;
		background-size: 50px 70px;
	}
	.man-image{
		width: 50px;
		height: 70px;
		display: inline-block;
		background: url(/view/pm/img/man.png) no-repeat;
		background-size: 50px 70px;
	}
	.date{
		margin:0 0 10px;
		border:1px solid #ddd;
		width: 80%;
		height: 30px;
		border-radius: 6px;
		margin-left: 10px;
	}
	.bottom-main{
		overflow: auto;
		position: absolute;
		bottom: 0;
		width: 100%;
		height: 40px;
	}
	.main1,.main2{
		float: left;
		width: 50%;
		line-height: 40px;
		color: #999;
		font-size: 14px;
		background: #de0000;
		font-size: 15px;
		color:#fff;
	}
	.main1:hover,.main2:hover{
		/*font-weight: bold;*/
	}
	.main1:active,.main2:active{
		font-size: 16px;
		font-weight: bold;
	}
	.main1{
		box-sizing: border-box;
		border-right: 1px solid #fff;
	}
	.total{
		text-align: left;
		padding-left: 10px;
	}
	.orgInfosRelationClass{
		width: 300px;
		height: 300px;
		margin: 10px;
		float: left;
	}
	.box{
		box-shadow:0px 0px 3px 0px #909090;
		border-radius: 8px;
		width: 340px;
		height: 200px;
		padding: 20px;
		margin-bottom: 10px;
		margin-left: 10px;
		float:left;
	}
	.box-up p{
		font-size: 12px;
	}
	.box:hover {
		box-shadow:3px 3px 25px 2px #909090;
		cursor: pointer;
	}
	.box-up{
		width: 30%;
		height: 100%;
		border-right: 1px solid #ddd;
		float: left;
	}
	.box-down{
		width: 69%;
		height: 100%;
		float: left;
	}
	.el-carousel__item:nth-child(n) {
	    background-color: #fff;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<el-row class="toolbar" :gutter="20">
			  		<shiro:hasPermission name="org:info:insert">  
				 	    <el-button class="margin-left-10" size="small" type="primary" @click="partyOrg_manager_openInsertOrgInfoDialog">
				 	    	<i class="el-icon-circle-plus-outline"></i>
				 	    	添加组织
				 	    </el-button>
				  	</shiro:hasPermission>
				  	<shiro:hasPermission name="org:info:query">  
						<el-popover class="margin-left-10"
							placement="bottom" 
							v-if="signInAccountType != 'party_role'"
						  	width="220" 
						  	trigger="hover" >
						  	<el-button size="small" type="primary" slot="reference">
						  		<i class="el-icon-search"></i>
						  		搜索组织
						  	</el-button>
						  	<div>
								<el-row v-show="dis_h_v">
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
								<el-row v-show="dis_h_v">
									<el-select size="small" clearable 
											@change="partyOrg_manager_queryOrgInfosForOrgType"
											v-model="queryCondition.partyOrg_manager_orgInfoNature" filterable placeholder="请选择组织性质">
										<el-option
											v-for="item in partyOrg_manager_orgInfoNatures"
										    :key="item.value"
										    :label="item.orgNatureName"
										    :value="item.orgNatureId">
										</el-option>
									</el-select>
								</el-row>
								<el-row v-show="dis_h_v">
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
								<el-row v-show="dis_h_v">
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
								<el-row v-show="dis_h_v">
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
								<el-row v-show="dis_h_v">
									<el-input size="small" clearable
										@change="partyOrg_manager_queryOrgInfosForInfoName"
										v-model="queryCondition.partyOrg_manager_orgInfoName" placeholder="请输入组织名"></el-input>
								</el-row>
								<el-row v-show="!dis_h_v">
									<el-input size="small" clearable
										@change="partyOrg_manager_getOrgInfoTreesForZMD"
										v-model="queryConditionForCharts.orgInfoName" placeholder="请输入组织名"></el-input>
								</el-row>
						  	</div>
						</el-popover>
					</shiro:hasPermission>
					<el-button v-if="signInAccountType == 'plant_admin'" class="margin-left-10" size="small" type="primary" @click="open_change_org_structure">
						<i class="el-icon-sort"></i>
						结构变更
					</el-button>
					<el-button-group class="margin-left-10" v-if="signInAccountType != 'party_role'">
                        <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                        <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                    </el-button-group>
                    <span style="float: right;" v-show="dis_h_v">
	                    <el-pagination id="partyUser_manager_agesdididi" 
						  	layout="total, prev, pager, next, jumper" 
			      		 	@current-change="partyOrg_manager_pagerCurrentChange"
						  	:current-page.sync="partyOrg_manager_orgInfoPages.pageNum"
						  	:page-size.sync="partyOrg_manager_orgInfoPages.pageSize"
						  	:total="partyOrg_manager_orgInfoPages.total">
						</el-pagination>
					</span>
				</el-row>
			</el-header>
			<el-main>
				<div v-show="!dis_h_v" v-if="signInAccountType != 'party_role'">
					<div style="text-align: center;">
						<p style="margin-bottom: 20px; font-size: 20px; font-weight: bold;">党委结构一览</p>
					</div>
				  	<div v-if="partyOrg_manager_orgInfoTreesForZMD != null">
						<div style="text-align: center;">
							<p style="margin-top: 20px; margin-bottom: 20px; font-size: 16px; font-weight: bold;">{{partyOrg_manager_orgInfoTreesForZMD.data.orgInfoName}}</p>
						</div>
						<div id="orgInfoTree" 
							style="width: 100%; height: 80%; text-align: center; overflow: auto; background-image: url(/view/pm/img/orgInfoChartBg.png); background-size: 100%">
						</div>
				    </div>
				</div>
				<span v-show="dis_h_v">
					<el-table  
							row-key="orgInfoId" 
							ref="partyOrg_manager_orgInfoDetailTables" 
							:expand-row-keys="partyOrg_manager_jumpToOrgDetailInfoArray" 
							@expand-change="setMap" 
							size="small" 
							:data="partyOrg_manager_orgInfoPages.list" 
							style="width: 100%">
						<el-table-column type="expand">
							<template slot-scope="scope">
								<div style="width: 60%; margin: 0 auto;">
									<el-row :gutter="20">
										<el-col :span="12">组织名称：{{scope.row.orgInfoName}}</el-col>
										<el-col :span="12">组织类型：{{scope.row.orgTypeName}}</el-col>
									</el-row>
									<el-row :gutter="20" v-for="item in scope.row.orgLevel1s">
										<el-col :span="5">{{item.orgDutyName}}：{{item.name}}</el-col>
										<el-col :span="3">性别：{{item.sex}}</el-col>
										<el-col :span="3">年龄：{{item.age}}</el-col>
										<el-col :span="6">联系电话：{{item.mobilePhone}}</el-col>
									</el-row>
									<el-row :gutter="20" v-if="scope.row.orgLevel1s.length != 0">
										<el-col :span="24">
											<el-collapse>
											  	<el-collapse-item>
											  		<template slot="title">
												    	<div style="text-align: center;">
												    		<a href="javascript:void(0)">
													    		<i class="el-icon-arrow-down"></i>
													    		<span style="font-weight: bold;">领导信息</span>
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
										<el-col :span="24">
											管委会地址：
											{{scope.row.orgInfoCommitteeProvince}}-
											{{scope.row.orgInfoCommitteeCity}}-
											{{scope.row.orgInfoCommitteeArea}}-
											{{scope.row.orgInfoCommitteeDetail}}
										</el-col>
									</el-row>
									<div :id="'container'+scope.row.orgInfoId"></div>	<!-- 地图信息 -->
									<div class="zzcy" v-if="signInAccountType != 'party_role'">
										<p class="title">组织成员 <span style="color: red;">{{scope.row.orgMemberNum}}</span> 人</p>
										<p style="text-align: left; padding-left: 10px; margin-bottom: 5px;">今日新增成员：</p>
										<div class="woman">
											<span class="woman-image"></span>
											<span class="woman-data">
												+ 
												<span style="color: red; font-weight: bold; font-size: 22px">{{scope.row.thisOrgNowTimeWoManCounts}}</span> 
												人
											</span>
										</div>
										<div class="man">
											<span class="man-image"></span>
											<span class="man-data">
												+ 
												<span style="color: red; font-weight: bold; font-size: 22px">{{scope.row.thisOrgNowTimeManCounts}}</span> 
												人
											</span>
										</div>
										<div class="bottom-main">
											<div class="main1" @click="partyOrg_manager_setOrgInfoIdOfShowThisOrgPeoples(scope.row)">
												所有成员
											</div>
											<div class="main2" @click="partyOrg_manager_setOrgInfoIdOfShowThisOrgPeoplesChart(scope.row)">
												报表分析
											</div>
										</div>
									</div>

									<div class="xszz" v-if="signInAccountType != 'party_role'">
										<p class="title">下属组织</p>
										<div class="total">
											共有下属组织：
											<span style="color: red; font-weight: bold; font-size: 22px">{{scope.row.orgChildrensNum}}</span>
											个
										</div>
										<div class="bottom-main">
											<div class="main1" @click="partyOrg_manager_setOrgInfoIdOfShowThisOrgChildrens(scope.row)">
												查看组织
											</div>
											<div class="main2" @click="partyOrg_manager_setOrgInfoIdOfShowOrgInfosChildrenChart(scope.row)">
												组织结构
											</div>
										</div>
									</div>
								</div>
							</template>
						</el-table-column>
						<el-table-column label="组织ID" prop="orgInfoId"></el-table-column>
						<el-table-column label="组织类型" prop="orgTypeName"></el-table-column>
						<el-table-column label="组织名" prop="orgInfoName"></el-table-column>
						<el-table-column label="组织管理所在省" prop="orgInfoCommitteeProvince"></el-table-column>
						<el-table-column label="组织管理所在市" prop="orgInfoCommitteeCity"></el-table-column>
						<el-table-column label="操作" width=420>
							<template slot-scope="scope">
								<shiro:hasPermission name="party:user:delete">  
									<el-button @click="partyOrg_manager_deleteOrgInfo(scope.row)" 
										type="text" size="small">删除</el-button>
								</shiro:hasPermission>
								<shiro:hasPermission name="party:user:update">  
									<el-button @click="partyOrg_manager_openUpdateOrgInfoDialog(scope.row)" 
										type="text" size="small">修改信息</el-button>
								</shiro:hasPermission>
								<shiro:hasPermission name="party:user:update">  
									<el-button 
										@click="partyOrg_manager_openAddOrgIntegralConstituteDialog(scope.row)" 
										type="text" size="small">添加积分结构
									</el-button>
								</shiro:hasPermission>
								<el-button 
									@click="open_into_org_user_dialog(scope.row)" 
									type="text" size="small">转入用户
								</el-button>
								<shiro:hasPermission name="party:user:update">  
									<el-button 
										@click="partyOrg_manager_openInsertOrgDutyDialog(scope.row)" 
										type="text" size="small">添加角色
									</el-button>
								</shiro:hasPermission>
								<el-button 
									v-if="scope.row.isPartyOrg && scope.row.orgSetJoin"
									@click="open_update_join_process_dialog(scope.row)" 
									type="text" size="small">调整入党流程
								</el-button>
							</template>
						</el-table-column>
					</el-table>
				</span>
			</el-main>
		</el-container>

		<el-dialog @close="" title="变更组织结构" :visible.sync="change_org_structure.dialog">
			<div style="margin: 10px;">
				<el-tree :expand-on-click-node="false" 
					:highlight-current="true" 
					draggable
					:default-expand-all="true"
					@node-drop="change_org_structure_start"
					:data="change_org_structure.org_tree" 
					:props="change_org_structure.org_tree_props" >
				</el-tree>
			</div>
		</el-dialog>

		<el-dialog @close="reset_join_party" title="变更入党流程" :visible.sync="join_process_dialog" width="70%">
			<div style="margin-bottom: 10px;">
				<p style="font-size:16px; font-weight: bold;">提示</p>
				<p style="font-size:12px; color: red;">
					步骤必须含有“确认入党积极份子”、“会议确认发展对象”、“确定预备党员”、“提交转正申请”这四步且按照此顺序排列
				</p>
			</div>
			<div style="float: left; margin-bottom: 20px; margin-right: 20px;">
				<el-transfer
					v-model="join_party_org.org_process"
					:data="join_party_org.process"
					:props="{key: 'id', label: 'name'}"
					:titles="['全部步骤', '我的步骤']"
					:button-texts="['到左边', '到右边']"
					@right-check-change="org_process_check"
					target-order="push"
					filterable>
				</el-transfer>
			</div>
			<div>
				<el-button @click="change_process_position('s')" size="small">上移</el-button>
				<el-button @click="change_process_position('x')" size="small">下移</el-button>
				<el-button type="primary" @click="insert_org_join_process" size="small">变更流程</el-button>
			</div>
		</el-dialog>

		<el-dialog @close="" title="添加组织积分结构" :visible.sync="partyOrg_manager_addOrgIntegralConstituteDialog" width="70%">
			<el-form label-width="120px" size="small" :model="partyOrg_manager_addOrgIntegralConstituteForm" status-icon 
				ref="partyOrg_manager_addOrgIntegralConstituteForm" label-width="100px" :rules="partyOrg_manager_addOrgIntegralConstituteFormRules" >
				<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 10px;">
					<div>
						<el-form-item label="积分类型" prop="type">
						    <el-input 
						    	clearable 
						    	style="width: 180px;"
						    	v-model="partyOrg_manager_addOrgIntegralConstituteForm.type" 
						    	placeholder="例：基础积分"></el-input>
						</el-form-item>
					</div>
					<!-- <div>
						<el-form-item label="积分值" prop="integral">
						    <el-input 
						    	clearable 
						    	style="width: 90px;"
						    	v-model="partyOrg_manager_addOrgIntegralConstituteForm.integral" 
						    	placeholder="填写数字"></el-input> 分
						</el-form-item>
					</div> -->
					<div>
						<el-form-item label="说明" prop="describes">
						    <el-input 
							  	type="textarea"
							 	:autosize="{ minRows: 3, maxRows: 5}"
							 	placeholder="积分类型说明"
								v-model="partyOrg_manager_addOrgIntegralConstituteForm.describes">
							</el-input>
						</el-form-item>
					</div>
					<div v-if="partyOrg_manager_orgInfoTreeOfIntegralConstitute.length != 0">
						<el-form-item label="积分类型细分" prop="parentIcId" style="margin-bottom: 0px;">
							<el-tree :expand-on-click-node="false" 
								ref="ref_integral_con"
								draggable
								node-key="icId" 
								@node-drop="change_integral_structure"
						    	:highlight-current="true" 
						    	:data="partyOrg_manager_orgInfoTreeOfIntegralConstitute" 
						    	:props="partyOrg_manager_orgInfoTreeOfIntegralConstituteProps" 
								@node-click="partyOrg_manager_setIntegralparentId">
								<span class="custom-tree-node" slot-scope="{ node, data }">
									<span>{{ node.label }}</span>
									<span v-if="data.data.isInnerIntegral != 1 && data.data.icId != -1">
										<el-button
											type="text"
											size="mini"
											@click="() => update_integral_con(node, data)">
											修改
										</el-button>
										<el-button
											type="text"
											size="mini"
											@click="() => delete_integral_con(node, data)">
											删除
										</el-button>
									</span>
								</span>
						  	</el-tree>
						</el-form-item>
					</div>
				</div>
				<div style="margin: 20px 0px;">
					<el-button size="small" type="primary" @click="partyOrg_manager_addOrgIntegralConstitute">添加积分</el-button>
			    	<el-button size="small" @click="partyOrg_manager_resetAddOrgIntegralConstituteForm">重置</el-button>
				</div>
			</el-form>
		</el-dialog>

		<el-dialog @close="partyOrg_manager_resetOrgInfosRelationDialog" title="组织成员分析" :visible.sync="partyOrg_manager_showOrgInfosRelationDialog" width="70%">
			<div class="orgInfosRelationClass" id="joinOrgProportionOfMenAndWomenChart"></div>
			<div class="orgInfosRelationClass" id="totalOrgProportionOfMenAndWomenChart"></div>
		</el-dialog>

		<el-dialog @close="partyOrg_manager_resetOrgInfosChildren" title="下属组织关系图" :visible.sync="partyOrg_manager_showOrgInfosChildrenDialog" width="70%">
			<div style="width: 90%; height: 500px; margin: 0 auto; padding: 20px; text-align: center;" id="childrensOrgChildren"></div>
		</el-dialog>

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
							size="mini"
							align="center"
							:stripe="true"
							class="common" border size="small" :data="partyOrg_manager_ThisOrgInfos.peoples_pager.list" style="width: 100%">
						<el-table-column label="姓名" prop="name"></el-table-column>
						<el-table-column label="性别" prop="sex"></el-table-column>
						<el-table-column label="民族" prop="nationName"></el-table-column>
						<el-table-column label="生日" prop="birthDate" width="150px"></el-table-column>
						<el-table-column label="年龄">
							<template slot-scope="scope">
								<span>{{scope.row.age}} 岁</span>
							</template>
						</el-table-column>
						<el-table-column label="学历" prop="educationName"></el-table-column>
						<el-table-column label="学位" prop="academicDegreeName"></el-table-column>
						<el-table-column label="党员类型" prop="partyType"></el-table-column>
						<el-table-column label="党员状态" prop="partyStatus"></el-table-column>
						<el-table-column label="职责" prop="orgDutyName"></el-table-column>
						<el-table-column label="加入时间" prop="orgRltJoinTime" width="150px"></el-table-column>
						<el-table-column fixed="right" label="操作">
							<template slot-scope="scope">
								<el-button @click="partyOrg_manager_openChangeUserDutyThisOrgDialog(scope.row)" type="text" size="mini">岗位管理</el-button>
							</template>
						</el-table-column>
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

		<el-dialog @close="" title="职责变更/离开组织" :visible.sync="partyOrg_manager_changeUserDutyThisOrgDialog">
			<el-row :gutter="20">
				<el-col :span="24">
				    <el-tree :default-expand-all="true" 
				    	node-key="id" 
				    	ref="partyOrg_manager_changeUserDutyThisOrgTree"
				    	show-checkbox 
				    	:expand-on-click-node="false" 
				    	:highlight-current="true" 
				    	:default-checked-keys="partyOrg_manager_changeUserDutyThisOrg.haveDuty"
				    	:data="partyOrg_manager_changeUserDutyThisOrg.allDutyThisOrg" 
				    	:props="partyOrg_manager_changeUserDutyThisOrg.allDutyThisOrgProps" 
				    	:check-strictly="true" >
				  	</el-tree>
				</el-col>
			</el-row>
			<el-row :gutter="20" class="toolbar">
				<el-button @click="partyOrg_manager_updateChangeUserDutyThisOrg" type="primary" size="small">确认变更</el-button>
				<el-button @click="partyOrg_manager_deleteChangeUserDutyThisOrg" type="danger" size="small">离开组织</el-button>
			</el-row>
		</el-dialog>

		<el-dialog @close="partyOrg_manager_resetinsertOrgInfoDutyForm()" class="common" @close="" title="增加角色" :visible.sync="partyOrg_manager_insertOrgDutyDialog" width="70%">
			<el-form label-width="120px" size="small" :model="partyOrg_manager_insertOrgInfoDutyForm" status-icon :rules="partyOrg_manager_insertOrgInfoDutyRules" 
				ref="partyOrg_manager_insertOrgInfoDutyForm" label-width="100px">
				<el-row :gutter="20">
					<el-col :span="12">
						<el-form-item label="角色名" prop="orgDutyName">
						    <el-input clearable v-model="partyOrg_manager_insertOrgInfoDutyForm.orgDutyName" placeholder="职责名（经理、书记、普通党员...）"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="角色描述" prop="orgDutyDescribe">
							<el-input clearable
							  	type="textarea"
							 	:autosize="{ minRows: 2}"
							 	placeholder="请输入对此角色描述的内容"
								v-model="partyOrg_manager_insertOrgInfoDutyForm.orgDutyDescribe">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="选择上级角色" prop="orgDutyParentId">
						    <el-tree :expand-on-click-node="false" 
								:highlight-current="true" 
								node-key="orgDutyId"
								ref="ref_duty_orginfo"
								draggable
								@node-drop="change_org_duty_structure"
						    	:data="partyOrg_manager_insertOrgInfoDutyForm.orgDutyTreesForOrgInfo" 
						    	:props="partyOrg_manager_orgInfoDutyTreesProps" 
								@node-click="partyOrg_manager_setOrgDutyParentId">
								<span class="custom-tree-node" slot-scope="{ node, data }">
									<span>{{ node.label }}</span>
									<span>
									  	<el-button
											type="text"
											size="mini"
											@click="() => update_org_duty(node, data)">
											修改
									  	</el-button>
									  	<el-button
											type="text"
											size="mini"
											@click="() => delete_org_duty(node, data)">
											删除
									  	</el-button>
									</span>
								</span>
						  	</el-tree>
						</el-form-item>
					</el-col>
				</el-row>

				<el-form-item>
				    <el-button type="primary" @click="partyOrg_manager_insertOrgInfoDuty">添加角色</el-button>
				    <el-button @click="partyOrg_manager_resetinsertOrgInfoDutyForm">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

		<el-dialog class="common" @close="" title="修改组织信息" :visible.sync="partyOrg_manager_updateOrgInfoDialog" width="70%">
			<el-form label-width="120px" size="small" :model="partyOrg_manager_updateOrgInfoForm" status-icon :rules="partyOrg_manager_updateOrgInfoRules" 
				ref="partyOrg_manager_updateOrgInfoForm" label-width="100px">
				<el-row :gutter="20">
					<el-col :span="8">
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
					<el-col :span="16">
						<el-form-item label="组织性质" prop="orgInfoNatureId">
						    <el-select :disabled="true" clearable v-model="partyOrg_manager_updateOrgInfoForm.orgInfoNatureId" placeholder="请选择">
							    <el-option
							      v-for="item in partyOrg_manager_orgInfoNatures"
							      :key="item.value"
							      :label="item.orgNatureName"
							      :value="item.orgNatureId">
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

		<el-dialog @close="partyOrg_manager_resetinsertOrgInfoForm" class="common" @close="" title="添加组织信息" :visible.sync="partyOrg_manager_insertOrgInfoDialog" width="400px">
			<el-form label-width="140px" size="small" :model="partyOrg_manager_insertOrgInfoForm" status-icon :rules="partyOrg_manager_insertOrgInfoRules" 
				ref="partyOrg_manager_insertOrgInfoForm" label-width="100px">
				<div>
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
				</div>
				<div>
					<el-form-item label="组织性质" prop="orgInfoNatureId">
						<el-select clearable v-model="partyOrg_manager_insertOrgInfoForm.orgInfoNatureId" placeholder="请选择">
							<el-option
							  v-for="item in partyOrg_manager_orgInfoNatures"
							  :key="item.value"
							  :label="item.orgNatureName"
							  :value="item.orgNatureId">
							</el-option>
						</el-select>
					</el-form-item>
				</div>
				<div>
					<el-form-item label="组织名" prop="orgInfoName">
						<el-input clearable v-model="partyOrg_manager_insertOrgInfoForm.orgInfoName" placeholder="组织名"></el-input>
					</el-form-item>
				</div>
				<div>
					<el-form-item label="组织地址" prop="orgInfoCommittee_pca">
						<el-cascader clearable clearable :props="partyOrg_manager_address_prop"
							v-model="partyOrg_manager_insertOrgInfoForm.orgInfoCommittee_pca"
							separator="/"
							placeholder="可搜索地点" :options="partyOrg_manager_address_pca" 
							filterable >
						</el-cascader>
					</el-form-item>
				</div>
				<div>
					<el-form-item class="detailAddressInput" label="详细地址" prop="orgInfoCommitteeDetail">
						<el-input clearable v-model="partyOrg_manager_insertOrgInfoForm.orgInfoCommitteeDetail" placeholder="详细地址"></el-input>
					</el-form-item>
				</div>
				<div>
					<el-form-item label="组织管辖描述" prop="orgInfoManageDetail">
						<el-input clearable
							  type="textarea"
							 :autosize="{ minRows: 1}"
							 placeholder="请输入内容"
							v-model="partyOrg_manager_insertOrgInfoForm.orgInfoManageDetail">
						</el-input>
					</el-form-item>
				</div>		
				<div>
					<el-form-item label="组织简介" prop="orgInfoDescribe">
						<el-input clearable
							  type="textarea"
							 :autosize="{ minRows: 1}"
							 placeholder="请输入内容"
							v-model="partyOrg_manager_insertOrgInfoForm.orgInfoDescribe">
						</el-input>
					</el-form-item>
				</div>
				<div>
					<el-form-item label="组织关系" prop="orgInfoParentId">
						<el-tree :expand-on-click-node="false" 
							:highlight-current="true" 
							:data="partyOrg_manager_orgInfoTreeOfInsert" 
							:props="partyOrg_manager_orgInfoTreeOfInsertProps" 
							@node-click="partyOrg_manager_setOrgInfoParentId">
						</el-tree>
					</el-form-item>
				</div>
				<el-form-item>
				    <el-button type="primary" @click="partyOrg_manager_insertOrgInfo">添加组织</el-button>
				    <el-button @click="partyOrg_manager_resetinsertOrgInfoForm">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

		<el-dialog @close="" title="转入组织党员列表" :visible.sync="into_org_user.dialog">
			<div>
				<el-table 
					row-key="id"
					size="small" 
					:data="into_org_user.query.page.list" 
					style="width: 100%">
					<el-table-column label="转移编号" prop="id" width="100"></el-table-column>
					<el-table-column label="姓名" prop="name" width="100"></el-table-column>
					<el-table-column label="性别" prop="sex" width="100"></el-table-column>
					<el-table-column label="身份证号码" prop="idCard" width="200"></el-table-column>
					<el-table-column label="状态">
						<template slot-scope="scope">
							<el-button 
								style="color: red; "
								v-if="scope.row.nowStep == scope.row.maxOrgProcess.processId && 
									scope.row.joinStatus == 'success'"
								@click="openTurnOutOrgDutyDialog(scope.row)" 
								type="text" size="small">指定职责
							</el-button>
							<p v-if="scope.row.nowStep != scope.row.maxOrgProcess.processId || 
								scope.row.joinStatus != 'success'">
								对方组织审核中
							</p>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<div style="margin: 10px 0px; text-align: center;">
				<el-pagination
					layout="total, prev, pager, next, jumper" 
					@current-change="open_into_org_user_dialog"
					:current-page.sync="into_org_user.query.page.pageNum"
					:page-size.sync="into_org_user.query.page.pageSize"
					:total="into_org_user.query.page.total">
			  	</el-pagination>
			</div>
		</el-dialog>

		<el-dialog @close="" title="指定角色" :visible.sync="turnOutDuty.dialog">
			<div>
				<el-tree :default-expand-all="true" 
					node-key="id" 
					ref="turnOutOrgInfoTree"
					show-checkbox 
					:expand-on-click-node="false" 
					:highlight-current="true" 
					:data="turnOutDuty.orgDutyTreesForOrgInfo" 
					:props="turnOutOrgInfoOrgDutyTreesForOrgInfoProps" 
					:check-strictly="true" >
				</el-tree>
			</div>
			<div style="margin: 10px;">
				<el-button 
					@click="turnOutUpdateOrgRelation" 
					type="primary" size="small">确认
				</el-button>
			</div>
		</el-dialog>
	</div>
</body>

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			partyOrg_manager_CarouselHeight: "500px",
			partyOrg_manager_jumpToOrgDetailInfoArray: [], 	/*点击卡片跳转到详细信息，用于保存值，赋值给rowkey以便展开信息*/
			dis_h_v: true,
			partyOrg_manager_address_pca: pca,	/* 省市区三级联动数据 */
			partyOrg_manager_address_prop: {	/* 地址prop */
				value: "name",
				label: "name",
				children: "children"
			},
			partyOrg_manager_changeUserDutyThisOrg: {
				orgInfoId: null,
				orgUserId: null,
				haveDuty: [],
				allDutyThisOrg: [],
				allDutyThisOrgProps: {
					children: 'children',
		            label: function(_data, node){
		            	return _data.data.orgDutyName;
		            }
				}
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
				partyOrg_manager_orgInfoName: null,	/*用于保存使用组织名模糊查询*/
				partyOrg_manager_orgInfoId: null,	/*用于保存使用组织id查询*/
				partyOrg_manager_orgInfoNature: null	/*用于保存使用组织性质查询*/
			},
			queryConditionForCharts: {
				orgInfoName: null
			},
			partyOrg_manager_insertOrgInfoDialog: false,	/*添加组织信息*/
			partyOrg_manager_updateOrgInfoDialog: false, 	/*修改组织信息*/
			partyOrg_manager_insertOrgDutyDialog: false,	/*添加组织职责*/
			partyOrg_manager_showThisOrgPeoplesDialog: false,	/*查看这个组织下成员信息弹窗*/
			partyOrg_manager_showThisOrgChildrensDialog: false, 	/*下属组织信息*/
			partyOrg_manager_changeUserDutyThisOrgDialog: false,	/*变更用户职责弹窗*/
			partyOrg_manager_showOrgInfosChildrenDialog: false, 	/*下属组织关系图弹窗*/
			partyOrg_manager_showOrgInfosRelationDialog: false, 	/*组织人员分析图弹窗*/
			partyOrg_manager_addOrgIntegralConstituteDialog: false, /*添加组织积分结构弹窗*/
			join_process_dialog: false,	//修改入党流程弹窗
			partyOrg_manager_orgInfoTreeOfInsert: [],	/*添加组织信息时的组织关系树*/
			partyOrg_manager_orgInfoTreeOfInsertProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
			partyOrg_manager_orgInfoTreeOfIntegralConstitute: [],	/*添加积分结构是选择章节的父章节*/
			partyOrg_manager_orgInfoTreeOfIntegralConstituteProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.type;
	            }
			},
			partyOrg_manager_orgInfoTypes:[],	/*添加组织信息时选择组织类型*/
			partyOrg_manager_orgInfoNatures: [],	/*组织性质选择下拉框*/
			partyOrg_manager_insertOrgInfoForm: {	/*添加党员的信息*/
				orgInfoTypeId: null,
				orgInfoNatureId: null,
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
		    	orgInfoNatureId: [
		    		{ required: true, message: '请选择组织性质!', trigger: 'blur' }
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
				orgInfoNatureId: null,
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
			partyOrg_manager_addOrgIntegralConstituteFormRules: {
				type: [
		    		{ required: true, message: '请输入积分类型!', trigger: 'blur' }
		    	],
		    	integral: [
		    		{ required: true, message: '请输入积分值!', trigger: 'blur' }
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
	            	return _data.data.orgDutyName + '  职责id：' + _data.data.orgDutyId;
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
				},
				partyOrg_manager_orgInfosChildrenTree: [],	/*组织关系树图*/
				orgInfoChildrenTreeOc: null
			},
			partyOrg_manager_orgInfoTreesForZMD: null,	/*组织结构信息-走马灯页面*/
			partyOrg_manager_addOrgIntegralConstituteForm: {	/*添加组织积分结构弹窗*/
				type: null,
				integral: null,
				describes: null,
				orgId: null,
				parentIcId: -1,
				isInnerIntegral: 0
			},
			count: 0,
			realCount: 0,
			signInAccountType: null,
			loadedOrgInfoId: [],
			orgInfoTreeOC: null,
			join_party_org: {
				org_id: null,
				process: [],
				org_process: [],
				org_process_checked: []
			},
			into_org_user: {
				dialog: false,
				userInfo: null,
				query: {
					page: {
						pageNum: 1,		/* 当前页 */
						pageSize: 6,	/* 页面大小 */
						total: 0,
						list: []
					},
					conditions: {	//搜索条件
						idCard: null
					}
				}
			},
			turnOutDuty: {
				dialog: false,
				orgDutyTreesForOrgInfo: [],
				userInfo: null
			},
			turnOutOrgInfoOrgDutyTreesForOrgInfoProps: {
				children: 'children',
				label: function(_data, node){
					return _data.data.orgDutyName;
				}
			},
			change_org_structure: {
				dialog: false,
				org_tree: null,
				org_tree_props: {
					children: 'children',
					label: function(_data, node){
						return _data.data.orgInfoName;
					}
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
			this.partyOrg_manager_getOrgInfoNatures();
			this.partyOrg_manager_getOrgInfoTreesForZMD();	/*走马灯*/
			this.getSignInAccountType();
			this.getItaf();
		},
		methods: {
			change_org_structure_start(draggingNode, dropNode, dropType, ev) {

			},
			open_change_org_structure() {
				let obj = this;
				var url = "/org/ifmt/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.change_org_structure.org_tree = datas.data;
						obj.change_org_structure.dialog = true;
					}
				})
			},
			turnOutUpdateOrgRelation() {
				var obj = this;
				var checkedKeys = [];
				checkedKeys = obj.$refs.turnOutOrgInfoTree.getCheckedKeys(false);
				if (checkedKeys.length == 0) {
					toast('错误',"请选择该用户在此组织担任的职责",'error');
					return;
				}
				obj.$confirm(
					'同意后将加入申请的组织，本条申请流程完毕，将会消失，确认？', 
					'提示', 
					{
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}
				).then(function(){			
					var url = "/toou/user/insertOrgRelation";
					var t = {
						turnOutId: obj.turnOutDuty.userInfo.id,
						orgRltUserId: obj.turnOutDuty.userInfo.baseUserId,
						orgRltInfoId: obj.turnOutDuty.userInfo.turnOutOrgId,
						orgRltDutys: checkedKeys
					}
					$.post(url, t, function(datas, status){
						if (datas.code == 200) {
							toast('成功',datas.msg,'success');
							obj.turnOutDuty.dialog = false;
							obj.open_into_org_user_dialog(obj.into_org_user.userInfo);
						}
						
					})
				}).catch(function(){
					obj.$message({
						type: 'info',
						message: '已取消此操作'
					});  
				});
			},
			openTurnOutOrgDutyDialog(row) {
				let obj = this;
				obj.turnOutDuty.userInfo = row;

				let url = "/org/duty/queryOrgDutyTreeForOrgInfo";
				let t = {
					orgDutyOrgInfoId: row.turnOutOrgId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						if (datas.data != undefined) {
							obj.turnOutDuty.orgDutyTreesForOrgInfo = datas.data;
							obj.forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(obj.turnOutDuty.orgDutyTreesForOrgInfo);
						} else {
							obj.turnOutDuty.orgDutyTreesForOrgInfo = [];
						}
					}
					
				})

				obj.turnOutDuty.dialog = true;
			},
			forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTrees){	/* 向树里添加id属性，方便设置node-key */
				var obj = this;
				if(menuTrees != null) {
					for (var i = 0; i < menuTrees.length; i++) {
						var menuTree = menuTrees[i];
						menuTree.id = menuTree.data.orgDutyId;                            
						obj.forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTree.children);
					}
				}
			},
			open_into_org_user_dialog(row) {
				let obj = this;
				obj.into_org_user.userInfo = row;
				
				var url = "/toou/user/queryTurnOutOrgPartyUsers";
				var t = {
					pageNum: obj.into_org_user.query.page.pageNum,
					pageSize: obj.into_org_user.query.page.pageSize,
					orgId: obj.into_org_user.userInfo.orgInfoId,
					isHistory: 0
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.into_org_user.query.page.pageNum = 1;
							obj.into_org_user.query.page.total = 0;
							obj.into_org_user.query.page.list = new Array();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.into_org_user.query.page = data.data;
						}
						obj.into_org_user.dialog = true;
					}
				})
			},
			insert_org_join_process() {
				let obj = this;
				if (obj.join_party_org.org_process == null || obj.join_party_org.org_process.length < 1) {
					toast("提示", "请选择流程", "error");
					return;
				}

				let url = "/org/process/insertOrganizationJoinProcess";
		    	var processId = "";
				for(var i of obj.join_party_org.org_process) {
					processId += i + ","
				}
		    	var t = {
		    		orgId: obj.join_party_org.org_id,
		    		processId: processId
		    	}
		    	$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('提示', "变更成功",'success');
						obj.reset_join_party();
					} else if (data.code == 500) {
						toast('提示', data.msg,'error');
					}
				})
			},
			open_update_join_process_dialog(row) {
				let obj = this;

				var url = "/join/process/queryJoinPartyOrgProcess";
				var t = {}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.join_party_org.process = data.data;
						obj.join_process_dialog = true;
						obj.join_party_org.org_id = row.orgInfoId;

						url = "/org/process/queryOjp";
						t = {
							orgId: row.orgInfoId
						}
						$.post(url, t, function(data, status){
							if (data.code == 200 && data.data != null) {
								for (let i = 0; i < data.data.length; i++) {
									obj.join_party_org.org_process.push(data.data[i].processId);
								}
							}
						})
					} else {
						toast("提示", "查询步骤失败", "error");
					}
				})
			},
			change_process_position(position) {
				let obj = this;
				if (obj.join_party_org.org_process_checked != null 
					&& obj.join_party_org.org_process_checked.length != 1) {
					toast("提示", "请在“我的步骤”栏里勾选一个步骤进行调整顺序", "error");
					return;
				}
				
				let change_process = obj.join_party_org.org_process_checked[0];	//在组织步骤里勾选的步骤
				for (let i = 0; i < obj.join_party_org.org_process.length; i++) {	//便利组织步骤
					if (change_process == obj.join_party_org.org_process[i]) {
						if (i == 0 && position == 's') {
							toast("提示", "已经是第一个了", "error");
							return;
						}
						if (i == obj.join_party_org.org_process.length - 1 && position == 'x') {
							toast("提示", "已经是最后一个了", "error");
							return;
						}

						//对应调换位置
						obj.join_party_org.org_process = obj.swapArray(obj.join_party_org.org_process, 
							i, position == 's' ? i - 1 : i + 1);
						return;
					}
				}
			},
			swapArray(arr, index1, index2) {
				arr[index1] = arr.splice(index2, 1, arr[index1])[0];
				return arr;
			},
			org_process_check(orgProcess, check_orgProcess) {
				let obj = this;
				obj.join_party_org.org_process_checked = orgProcess;
			},
			reset_join_party() {
				let obj = this;
				obj.join_party_org.process = [];
				obj.join_party_org.org_process = [];
				obj.join_party_org.org_id = null;
				obj.join_party_org.org_process_checked = [];
				obj.join_process_dialog = false;
			},
			delete_org_duty(node, data) {
				var obj = this;
				obj.$confirm(
					'确认要删除吗？', 
					'提示', 
					{
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
		        	}
		        ).then(function(){
	        		if (data.data.orgDutyId == -1) {
						toast("提示", "此节点不允许删除", "error")
						return;
					}
					var url = "/org/duty/deleteOrgDuty";
					let t = {
						orgDutyId: data.data.orgDutyId
					};
					$.post(url, t, function(datas, status){
						if (datas != null && datas.code == 200) {
							toast("提示", "删除成功", "success");
							obj.$refs.ref_duty_orginfo.remove(node);
						}
					})
		        }).catch(function(){
		        	obj.$message({
			            type: 'info',
			            message: '已取消此操作'
			        });  
				});
			},
			update_org_duty(node, data) {
				let obj = this;
				this.$prompt('请输入新的职责名字', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					inputPattern: /^[\u4e00-\u9fa5.·]+$/,
					inputErrorMessage: '请输入正确的名字'
				}).then(({ value }) => {
					if (data.data.orgDutyId == -1) {
						toast("提示", "此节点不允许修改", "error")
						return;
					}
					var url = "/org/duty/updateOrgDuty";
					let t = {
						orgDutyId: data.data.orgDutyId,
						orgDutyName: value
					}
					$.post(url, t, function(datas, status){
						if (datas != null && datas.code == 200) {
							toast("提示", "修改成功", "success");
							data.data.orgDutyName = value;
						}
					})
				}).catch(() => {
					obj.$message({
						type: 'info',
						message: '取消修改'
					});       
				});
			},
			change_org_duty_structure(draggingNode, dropNode, dropType, ev) {
				let obj = this;
				var url = "/org/duty/updateOrgDuty";
				let t = null;
				if (dropType == "inner") {
					t = {
						orgDutyId: draggingNode.data.data.orgDutyId,
						orgDutyParentId: dropNode.data.data.orgDutyId
					}
				} else if (dropType == "after" || dropType == "before") {
					if (dropNode.data.data.orgDutyId == -1) {
						toast("提示", "不能与顶级节点平行，请重新变更", "error");
						return;
					}
					t = {
						orgDutyId: draggingNode.data.data.orgDutyId,
						orgDutyParentId: dropNode.data.data.orgDutyParentId
					}
				}
				$.post(url, t, function(datas, status){
					if (datas != null && datas.code == 200) {
						toast("提示", "职责结构变更成功", "success");
					}
				})
			},
			delete_integral_con(node, data) {
				var obj = this;
				obj.$confirm(
					'确认要删除吗？', 
					'提示', 
					{
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
		        	}
		        ).then(function(){
	        		if (data.data.icId == -1) {
						toast("提示", "此节点不允许删除", "error")
						return;
					}
					var url = "/org/ic/deleteIntegralConstitute";
					let t = {
							icId: data.data.icId
					};
					$.post(url, t, function(datas, status){
						if (datas != null && datas.code == 200) {
							toast("提示", "删除成功", "success");
							obj.$refs.ref_integral_con.remove(node);
						}
					})
		        }).catch(function(){
		        	obj.$message({
			            type: 'info',
			            message: '已取消此操作'
			        });  
				});
			},
			update_integral_con(node, data) {
				let obj = this;
				this.$prompt('请输入积分类型名字', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					inputPattern: /^[\u4e00-\u9fa5.·]+$/,
					inputErrorMessage: '请输入正确的名字'
				}).then(({ value }) => {
					if (data.data.icId == -1) {
						toast("提示", "此节点不允许修改", "error")
						return;
					}
					var url = "/org/ic/updateIntegralConstitute";
					let t = {
						icId: data.data.icId,
						type: value
					}
					$.post(url, t, function(datas, status){
						if (datas != null && datas.code == 200) {
							toast("提示", "修改成功", "success");
							data.data.type = value;
						}
					})
				}).catch(() => {
					obj.$message({
						type: 'info',
						message: '取消修改'
					});       
				});
			},
			change_integral_structure(draggingNode, dropNode, dropType, ev) {	//变更积分结构
				let obj = this;
				var url = "/org/ic/updateIntegralConstitute";
				let t = null;
				if (dropType == "inner") {
					t = {
						icId: draggingNode.data.data.icId,
						parentIcId: dropNode.data.data.icId
					}
				} else if (dropType == "after" || dropType == "before") {
					if (dropNode.data.data.icId == -1) {
						toast("提示", "不能与顶级节点平行，请重新变更", "error");
						return;
					}
					t = {
						icId: draggingNode.data.data.icId,
						parentIcId: dropNode.data.data.parentIcId
					}
				}
				$.post(url, t, function(datas, status){
					if (datas != null && datas.code == 200) {
						toast("提示", "积分结构变更成功", "success");
					}
				})
			},
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
			partyOrg_manager_getOrgInfoTreesForZMD() {
				
			},
			setMap(row, expandedRows) {
				var obj = this;
				setTimeout(()=>{
					obj.initAmap("container"+row.orgInfoId, row.orgInfoCommitteeProvince+row.orgInfoCommitteeCity+row.orgInfoCommitteeArea+row.orgInfoCommitteeDetail);
				},200)
				
			},
			initAmap(idName, address) {
				var obj = this;
				var map = new AMap.Map(idName, {
				    pitch:30,
				    viewMode:'3D',//开启3D视图,默认为关闭
				    resizeEnable: true
			    })
				var geocoder = new AMap.Geocoder({
		            radius: 1000 //范围，默认：500
		        });
		        //地理编码,返回地理编码结果
		        geocoder.getLocation(address, function(status, result) {
		            if (status === 'complete' && result.info === 'OK') {
		                obj.geocoder_CallBack(result, map);
		            }
		        });
			},
			//地理编码返回结果展示
		    geocoder_CallBack(data, map) {
		    	var obj = this;
		        //地理编码结果数组
		        var geocode = data.geocodes;
		        for (var i = 0; i < geocode.length; i++) {
		            obj.addMarker(i, geocode[i], map);
		        }
		        map.setFitView();	/*缩放自适应*/
		    },
			addMarker(i, d, map) {
				var obj = this;
		        var marker = new AMap.Marker({
		            map: map,
		            position: [ d.location.getLng(),  d.location.getLat()]
		        });
		        var infoWindow = new AMap.InfoWindow({
		            content: d.formattedAddress,
		            offset: {x: 0, y: -30}
		        });
		        marker.on("mouseover", function(e) {
		            infoWindow.open(map, marker.getPosition());
		        });
		    },
		    

			getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
				var obj = this;
				var height = window.innerHeight;
				obj.partyOrg_manager_orgInfoPages.pageSize = parseInt((height - 100)/50)
				obj.partyOrg_manager_CarouselHeight = height*0.75 + "px";
			},
			partyOrg_manager_initPager() {	/* 初始化页面数据 */
				var obj = this;
				obj.partyOrg_manager_orgInfoPages.pageNum = 1;
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
					orgInfoNatureId: obj.queryCondition.partyOrg_manager_orgInfoNature,
					orgInfoName: obj.queryCondition.partyOrg_manager_orgInfoName,
					orgInfoId: obj.queryCondition.partyOrg_manager_orgInfoId,
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
				var url = "/org/ifmt/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_getOrgInfoTypes();
						obj.partyOrg_manager_orgInfoTreeOfInsert = datas.data;
						obj.partyOrg_manager_insertOrgInfoDialog = true;
					}
				})
        	},
			partyOrg_manager_getOrgInfoTypes() {	/*组织类型*/
				var obj = this;
				url = "/org/type/queryOrgTypesNotPage";
				var t = {}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_orgInfoTypes = datas.data;
					}
				})
        	},
        	partyOrg_manager_getOrgInfoNatures() {	/*组织性质*/
        		var obj = this;
        		url = "/org/nature/queryOrgNaturesNotPage";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_orgInfoNatures = datas.data;
					}
					
				})
        	},
        	partyOrg_manager_setOrgInfoParentId(data) {	/*设置组织层级关系值*/
        		var obj = this;
				obj.partyOrg_manager_insertOrgInfoForm.orgInfoParentId = data.data.orgInfoId;
        	},
        	partyOrg_manager_setIntegralparentId(data) {
        		var obj = this;
				obj.partyOrg_manager_addOrgIntegralConstituteForm.parentIcId = data.data.icId;
        	},
        	partyOrg_manager_resetinsertOrgInfoForm() {	/*重置新增组织表单*/
				var obj = this;
				obj.partyOrg_manager_orgInfoTreeOfInsert = [];
        		obj.$refs.partyOrg_manager_insertOrgInfoForm.resetFields();
        	},
        	partyOrg_manager_resetAddOrgIntegralConstituteForm() {
        		var obj = this;
        		obj.$refs.partyOrg_manager_addOrgIntegralConstituteForm.resetFields();
        	},
        	partyOrg_manager_insertOrgInfo() {	/*新增组织信息*/
        		var obj = this;
        		this.$refs.partyOrg_manager_insertOrgInfoForm.validate( function(valid) {
        			if (valid) {
						var url = "/org/ifmt/queryOrgInfosSelect";
						var t = {
							orgInfoParentId: -1
						}
						$.post(url, t, function(data, status){
							if (data.code == 200) {
								if (data.data != null && data.data.length > 0 && obj.partyOrg_manager_insertOrgInfoForm.orgInfoParentId == -1) {
									obj.$notify({
										title: '提示',
										message: '请选择所在党委或党总支',
										type: 'info'
									});
									return;
								} else {
									var url = "/org/ifmt/insertOrgInfo";
									var t = {
										orgInfoTypeId: obj.partyOrg_manager_insertOrgInfoForm.orgInfoTypeId,
										orgInfoNatureId: obj.partyOrg_manager_insertOrgInfoForm.orgInfoNatureId,
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
							}
							
						})

        				
        			}
        		})
        	},
        	partyOrg_manager_openUpdateOrgInfoDialog(row) {	/*打开组织修改弹窗*/
        		var obj = this;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoId = row.orgInfoId;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoTypeId = row.orgInfoTypeId;
        		obj.partyOrg_manager_updateOrgInfoForm.orgInfoNatureId = row.orgInfoNatureId;
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
						} else {
							toast('删除失败',data.msg,'error');
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
				return "http://192.168.1.8:3000" + row.idPhoto;
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
			},
			partyOrg_manager_openChangeUserDutyThisOrgDialog(row) {
				var obj = this;

				obj.partyOrg_manager_changeUserDutyThisOrg.orgUserId = row.baseUserId;
				obj.partyOrg_manager_changeUserDutyThisOrg.orgInfoId = obj.partyOrg_manager_ThisOrgInfos.orgInfoId;
				obj.partyOrg_manager_changeUserDutyThisOrg.haveDuty = [];

				var url = "/org/relation/queryOrgRelationNewsNotPage";
				var t = {
					orgRltInfoId: obj.partyOrg_manager_changeUserDutyThisOrg.orgInfoId,
					orgRltUserId: obj.partyOrg_manager_changeUserDutyThisOrg.orgUserId
				}
				$.post(url, t, function(datas, status){	/*查询该用户在次组织已有的职责*/
					if (datas.code == 200) {
						if (datas.data != undefined) {
							for (var i = 0; i < datas.data.length; i++) {
								obj.partyOrg_manager_changeUserDutyThisOrg.haveDuty.push(datas.data[i].orgDutyId);
							}
						} else {
							obj.partyOrg_manager_changeUserDutyThisOrg.haveDuty = [];
						}
					}
					
				})

				
				url = "/org/duty/queryOrgDutyTreeForOrgInfo";
				t = {
					orgDutyOrgInfoId: obj.partyOrg_manager_changeUserDutyThisOrg.orgInfoId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						if (datas.data != undefined) {
							obj.partyOrg_manager_changeUserDutyThisOrg.allDutyThisOrg = datas.data;
							obj.forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(obj.partyOrg_manager_changeUserDutyThisOrg.allDutyThisOrg);
						} else {
							obj.partyOrg_manager_changeUserDutyThisOrg.allDutyThisOrg = [];
						}
					}
					
				})


				obj.partyOrg_manager_changeUserDutyThisOrgDialog = true;
			},
			forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTrees){	/* 向树里添加id属性，方便设置node-key */
				var obj = this;
				if(menuTrees != null) {
					for (var i = 0; i < menuTrees.length; i++) {
						var menuTree = menuTrees[i];
						menuTree.id = menuTree.data.orgDutyId;
						obj.forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTree.children);
					}
				}
			},
			partyOrg_manager_updateChangeUserDutyThisOrg() {
				var obj = this;					
				var url = "/org/relation/insertOrgRelation";
				
				var checkedKeys = [];
				checkedKeys = obj.$refs.partyOrg_manager_changeUserDutyThisOrgTree.getCheckedKeys(false);
				var t = {
					orgRltUserId: obj.partyOrg_manager_changeUserDutyThisOrg.orgUserId,
					orgRltInfoId: obj.partyOrg_manager_changeUserDutyThisOrg.orgInfoId,
					orgRltDutys: checkedKeys
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						toast('成功',datas.msg,'success');
						obj.partyOrg_manager_changeUserDutyThisOrgDialog = false;
						obj.partyOrg_manager_showThisOrgPeoples();
					}
					
				})
			},
			partyOrg_manager_deleteChangeUserDutyThisOrg() {
				var obj = this;
				obj.$confirm(
					'离开此组织, 是否继续?', 
					'提示', 
					{
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
		        	}
		        ).then(function(){
		        	obj.$refs.partyOrg_manager_changeUserDutyThisOrgTree.setCheckedKeys([], false);
	        		obj.partyOrg_manager_updateChangeUserDutyThisOrg();
		        }).catch(function(){
		        	obj.$message({
			            type: 'info',
			            message: '已取消操作'
			        });  
		        });
			},
			partyOrg_manager_setOrgInfoIdOfShowOrgInfosChildrenChart(row) {	/*查看当前组织下属组织关系图*/
				var obj = this;
        		obj.partyOrg_manager_ThisOrgInfos.orgInfoId = row.orgInfoId;
        		obj.partyOrg_manager_showOrgInfosChildren();
			},
			partyOrg_manager_showOrgInfosChildren(){
				var obj = this;

				var url = "/org/ifmt/queryOrgInfosToTrees";
				var t = {
					orgInfoId: obj.partyOrg_manager_ThisOrgInfos.orgInfoId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {
							if (data.data.length < 1) {
								return;
							}
							obj.partyOrg_manager_ThisOrgInfos.partyOrg_manager_orgInfosChildrenTree = new Array;
							data.data[0].count = 0;
							obj.partyOrg_manager_getOrgChildrensTreeAndAddCount(data.data, data.data, obj.partyOrg_manager_ThisOrgInfos.partyOrg_manager_orgInfosChildrenTree);

							setTimeout(()=>{
								//obj.partyOrg_manager_setChartForOrgChildrens(trees, "orgInfoTree"+item.data.orgInfoId, true, items[0].count);
								if (obj.partyOrg_manager_ThisOrgInfos.orgInfoChildrenTreeOc == null) {
									obj.partyOrg_manager_ThisOrgInfos.orgInfoChildrenTreeOc = $("#childrensOrgChildren").orgchart({
								      	'data' : obj.partyOrg_manager_ThisOrgInfos.partyOrg_manager_orgInfosChildrenTree[0],
				      					'nodeContent': 'title',
				      					'pan': true,
				      					'zoom': true
								    });
								} else {
									obj.partyOrg_manager_ThisOrgInfos.orgInfoChildrenTreeOc.init({ 'data': obj.partyOrg_manager_ThisOrgInfos.partyOrg_manager_orgInfosChildrenTree[0] });
								}
								
							},200)
						} else {
							if (obj.partyOrg_manager_ThisOrgInfos.orgInfoChildrenTreeOc != null) {
								obj.partyOrg_manager_ThisOrgInfos.orgInfoChildrenTreeOc.init({ 'data': null });
							}
						}
					}

				})

				obj.partyOrg_manager_showOrgInfosChildrenDialog = true;
			},
			partyOrg_manager_getOrgChildrensTree(orgInfosRelationTree, trees) {
				var obj = this;
				if (orgInfosRelationTree != null && orgInfosRelationTree.length > 0) {
					for (var i = 0; i < orgInfosRelationTree.length; i++) {
						var tree = {name: null, children: null}
						tree.id = orgInfosRelationTree[i].data.orgInfoId;
						tree.name = orgInfosRelationTree[i].data.orgInfoName;
						tree.children = new Array;
						trees[i] = tree;
						obj.partyOrg_manager_getOrgChildrensTree(orgInfosRelationTree[i].children, tree.children);
					}
				}
			},
			partyOrg_manager_setChartForOrgChildrens(orgInfosRelationTree, elementId, clickJump, count) {
				var obj = this;
				var Orgchildrens = echarts.init(document.getElementById(elementId));
				var top = "15%";
				var bottom = "15%";
				if (count == 2) {
					top = "45%";
					bottom = "45%";
				} else if (count == 1) {
					top = "50%";
					bottom = "50%";
				} else if (count == 3) {
					top = "35%";
					bottom = "35%";
				}
				Orgchildrens.setOption(
					{
				        tooltip: {
				            trigger: 'item',
				            triggerOn: 'mousemove'
				        },
				        series: [
				            {
				                type: 'tree',

				                data: orgInfosRelationTree,

				                top: top,
				                left: '10%',
				                bottom: bottom,
				                right: '10%',

				                orient: 'vertical',
				                symbolSize: 8,

				                label: {
				                    normal: {
				                        position: 'top',
				                        rotate: -90,
				                        verticalAlign: 'middle',
				                        align: 'right',
				                        fontSize: 12
				                    }
				                },

				                lineStyle: {
				                	width: 1,
				                	color: {
									    type: 'radial',
									    x: 0.5,
									    y: 0.5,
									    r: 0.5,
									    colorStops: [{
									        offset: 0, color: 'red' // 0% 处的颜色
									    }, {
									        offset: 1, color: 'blue' // 100% 处的颜色
									    }],
									    globalCoord: false // 缺省为 false
									}
				                },

				                leaves: {
				                    label: {
				                        normal: {
				                            position: 'right',
				                            verticalAlign: 'middle',
				                            align: 'left'
				                        }
				                    }
				                },

				                expandAndCollapse: false,
				                animationDuration: 550,
				                animationDurationUpdate: 750
				            }
				        ]
				    }
				);
				if (clickJump) {
					Orgchildrens.on("click",function(params){
						obj.queryCondition.partyOrg_manager_orgInfoType = null,
						obj.queryCondition.partyOrg_manager_orgInfoNature = null,
						obj.queryCondition.partyOrg_manager_orgInfoName = null,
						obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeProvince = null,
						obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeCity = null,
						obj.partyOrg_manager_address.partyOrg_manager_orgInfoCommitteeArea = null

						obj.queryCondition.partyOrg_manager_orgInfoId = params.data.id;
						obj.partyOrg_manager_queryOrgInfosForMap();
						obj.dis_h_v = true;
						obj.queryCondition.partyOrg_manager_orgInfoId = null;
					});
				}
				
			},
			partyOrg_manager_resetOrgInfosChildren() {
				/*var obj = this;
				obj.partyOrg_manager_ThisOrgInfos.partyOrg_manager_orgInfosChildrenTree = new Array;
				var Orgchildrens = echarts.init(document.getElementById("childrensOrgChildren"));
				Orgchildrens.clear();*/
				var obj = this;
				obj.partyOrg_manager_ThisOrgInfos.partyOrg_manager_orgInfosChildrenTree = null;
			},
			partyOrg_manager_resetOrgInfosRelationDialog(){	/*组织人员分析弹窗关闭时重置图表*/
				var joinorgProportionOfMenAndWomenChart = echarts.init(document.getElementById("joinOrgProportionOfMenAndWomenChart"));
				joinorgProportionOfMenAndWomenChart.clear();
				var totalOrgProportionOfMenAndWomenChart = echarts.init(document.getElementById("totalOrgProportionOfMenAndWomenChart"));
				totalOrgProportionOfMenAndWomenChart.clear();
			},
			partyOrg_manager_setOrgInfoIdOfShowThisOrgPeoplesChart(row) {	/*组织人员报表分析*/
				var obj = this;
				setTimeout(()=>{
					obj.partyOrg_manager_setjoinOrgProportionOfMenAndWomenChart(row);
					obj.partyOrg_manager_settotalOrgProportionOfMenAndWomenChart(row);
				},200)
				obj.partyOrg_manager_showOrgInfosRelationDialog = true;
			},
			partyOrg_manager_setjoinOrgProportionOfMenAndWomenChart(row) {	/*今日新增男女比例*/
				var joinorgProportionOfMenAndWomenChart = echarts.init(document.getElementById("joinOrgProportionOfMenAndWomenChart"));
				joinorgProportionOfMenAndWomenChart.setOption(
					{
					    title : {
					        text: '今日新增人数',
					        subtext: '男女比例',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					        orient: 'vertical',
					        left: 'left',
					        data: ['男','女']
					    },
					    series : [
					        {
					            name: '比例',
					            type: 'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:[
					            	{value: row.thisOrgNowTimeWoManCounts, name:'女'},
					                {value: row.thisOrgNowTimeManCounts, name:'男'}
					                
					            ],
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
					            }
					        }
					    ]
					}
				);
			},
			partyOrg_manager_settotalOrgProportionOfMenAndWomenChart(row){	/*此组织总男女比例*/
				var obj = this;
				var manCount = 0;
				var woMenCount = 0;

				var url = "/org/ifmt/queryOrgRelationsNewForUserId";
				var t = {
					orgRltInfoId: row.orgInfoId,
					sex: '男'
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							manCount = data.data.length;
						}
					}

				})
				t.sex = '女';
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							woMenCount = data.data.length;
						}
					}

				})

				var totalOrgProportionOfMenAndWomenChart = echarts.init(document.getElementById("totalOrgProportionOfMenAndWomenChart"));
				setTimeout(()=>{
					totalOrgProportionOfMenAndWomenChart.setOption(
						{
						    title : {
						        text: '组织所有人员',
						        subtext: '男女比例',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        left: 'left',
						        data: ['男','女']
						    },
						    series : [
						        {
						            name: '比例',
						            type: 'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:[
						            	{value: woMenCount, name:'女'},
						                {value: manCount, name:'男'}
						                
						            ],
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
						            }
						        }
						    ]
						}
					);
				},200)
			},
			getOrgTypeImg(item) {
				if(item.orgTypeName == "党组织") {
					return "/view/pm/img/party.jpg";
				} else if(item.orgTypeName == "党支部") {
					return "/view/pm/img/party_zhi.jpg";
				} else if(item.orgTypeName == "国有企业") {
					return "/view/pm/img/company_public.jpg";
				} else if(item.orgTypeName == "私有企业") {
					return "/view/pm/img/company_private.jpg";
				} else if(item.orgTypeName == "公益组织") {
					return "/view/pm/img/welfare.jpg";
				} else if(item.orgTypeName == "学校") {
					return "/view/pm/img/school.jpg";
				} 
			},
			partyOrg_manager_jumpToOrgDetailInfo(item) {
				var obj = this;
				obj.dis_h_v = true;
				obj.partyOrg_manager_jumpToOrgDetailInfoArray = [item.orgInfoId];
				obj.setMap(item, null);
			},
			getItaf() {
				var obj = this;

				//为了按照层级关系延时依次展示
				for (let i = 0; i < 99; i++) {
					setTimeout(() => {

						var url = "/org/ifmt/bossSayOneByOneShowOrgInfoLevel";
						var t = {
							orgInfoParentId: -1,
							orgInfoName: obj.queryConditionForCharts.orgInfoName,
							nowLevel: i + 1
						}
						$.post(url, t, function(data, status){
							if (data.data != undefined && data.data != null && data.data.length > 0) {	
								if (data.code == 200) {
									obj.partyOrg_manager_orgInfoTreesForZMD = data.data[0];

									var trees = new Array;
									var items = new Array;
									obj.partyOrg_manager_orgInfoTreesForZMD.count = 0;
									items[0] = obj.partyOrg_manager_orgInfoTreesForZMD;
									obj.partyOrg_manager_getOrgChildrensTreeAndAddCount(items, items, trees);

									setTimeout(()=>{
										if (obj.orgInfoTreeOC == null) {
											obj.orgInfoTreeOC = $("#orgInfoTree").orgchart({
												'data' : trees[0],
												'nodeContent': 'title',
												'pan': true,
												'zoom': true,
												'toggleSiblingsResp': false,
												'parentNodeSymbol': false
											});
										} else {
											obj.orgInfoTreeOC.init({ 'data': trees[0] });
										}
									},200)
								}
							}

						})
					}, (i + 1) * 1000);
				}
				
			},
			partyOrg_manager_getOrgChildrensTreeAndAddCount(addCountOrgInfosRelationTree, orgInfosRelationTree, trees) {
				var obj = this;
				if (orgInfosRelationTree != null && orgInfosRelationTree.length > 0) {
					for (var i = 0; i < orgInfosRelationTree.length; i++) {
						var tree = {name: null, title: null, children: null}
						tree.id = orgInfosRelationTree[i].data.orgInfoId;
						tree.name = orgInfosRelationTree[i].data.orgTypeName;
						tree.title = orgInfosRelationTree[i].data.orgInfoName;
						if (orgInfosRelationTree[i].data.count > addCountOrgInfosRelationTree[0].count) {
							addCountOrgInfosRelationTree[0].count = orgInfosRelationTree[i].data.count;
						}
						tree.children = new Array;
						trees[i] = tree;
						obj.partyOrg_manager_getOrgChildrensTreeAndAddCount(addCountOrgInfosRelationTree, orgInfosRelationTree[i].children, tree.children);
					}
				}
			},
			partyOrg_manager_openAddOrgIntegralConstituteDialog(row) {
				var obj = this;
				obj.partyOrg_manager_addOrgIntegralConstituteForm.orgId = row.orgInfoId;

				var url = "/org/ifmt/queryOrgIntegralConstituteToTree";
				var t = {
					orgId: row.orgInfoId,
					parentIcId: -1
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.partyOrg_manager_orgInfoTreeOfIntegralConstitute = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									icId: -1,
									type: "新的积分类型 （不选默认）"
								},
								children: datas.data
							}
						];
					}
					
				})

				obj.partyOrg_manager_addOrgIntegralConstituteDialog = true;
			},
			partyOrg_manager_addOrgIntegralConstitute() {
				var obj = this;
        		this.$refs.partyOrg_manager_addOrgIntegralConstituteForm.validate( function(valid) {
        			if (valid) {
        				var url = "/org/ic/insertIntegralConstitute";
        				var t = {
        					type: obj.partyOrg_manager_addOrgIntegralConstituteForm.type,
							/*integral: obj.partyOrg_manager_addOrgIntegralConstituteForm.integral,*/
							describes: obj.partyOrg_manager_addOrgIntegralConstituteForm.describes,
							orgId: obj.partyOrg_manager_addOrgIntegralConstituteForm.orgId,
							parentIcId: obj.partyOrg_manager_addOrgIntegralConstituteForm.parentIcId,
							isInnerIntegral: obj.partyOrg_manager_addOrgIntegralConstituteForm.isInnerIntegral
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('添加成功',data.msg,'success');
        						obj.partyOrg_manager_resetAddOrgIntegralConstituteForm();
        						obj.partyOrg_manager_addOrgIntegralConstituteDialog = false;
        					}
        					
        				})
        			}
        		})
			}
		}
	});

	window.onFocus = function () {
		window.location.reload();
	}
</script>
</html>