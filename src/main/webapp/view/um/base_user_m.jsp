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
<title>党员信息管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<style type="text/css">
	body {
		margin: 20px;
	}
	#pagesdididi,#baseUser_manager_pagesdididi,#role_manager_pagesdididi {
		text-align: center;
	}
	#querySysUserStyle>span {
		margin-right: 20px;
	}
	#baseUser_manager_queryBaseUserStyle>span {
		margin-right: 20px;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
			  	<el-row :gutter="10">
			  		<el-col :span="3">
				  		<shiro:hasPermission name="base:user:insert">  
					 	    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="baseUser_manager_insertBaseUserDialog = true">添加用户</el-button>
					  	</shiro:hasPermission>
				  	</el-col>
				  	<el-col :span="3">
					  <shiro:hasPermission name="party:user:insert">  
					  	<el-popover 
							  placement="bottom" 
							  width="400" 
							  trigger="click" >
						  <el-button type="primary" slot="reference">批量导入党员</el-button>
						  <div>
							<el-button type="text" @click="partyUser_manager_exportPartyUsersExcelExample">下载Excel模板</el-button>，按照模板填写
						  	<el-upload
						  		action="" 
						 	   	:http-request="partyUser_manager_importPartyUsersExcel"
						 	   	:multiple="true" 
						 	   	:before-upload="partyUser_manager_validatePartyUsersExcel" >
							      <el-button type="text">点击上传excel文件</el-button>
							</el-upload>
						  </div>
						</el-popover>
					  </shiro:hasPermission>
				  	</el-col>
				</el-row>
			  </el-header>
			<el-header id="baseUser_manager_queryBaseUserStyle">
				<span>
			  	    性别
				  <el-select clearable v-model="baseUser_manager_baseUserSex" @change="baseUser_manager_queryBaseUsersBySex" placeholder="请选择系统用户性别">
				   	 <el-option v-for="item in baseUser_manager_initBaseUserSex" :key="item.value" :label="item.label" :value="item.value"></el-option>
				  </el-select>
				</span>
				<span>
			  	    是否党员
				  <el-select clearable v-model="baseUser_manager_baseUserIsParty" @change="baseUser_manager_queryBaseUsersByIsParty" placeholder="系统用户是否党员">
				   	 <el-option v-for="item in baseUser_manager_initBaseUserIsParty" :key="item.value" :label="item.label" :value="item.value"></el-option>
				  </el-select>
				</span>
			</el-header>
			<el-main>
				<template>
			  		<el-table size="mini" :data="baseUser_manager_pager.list" style="width: 100%" max-height="550">
						<el-table-column fixed prop="uid" label="ID" width="50">
					    </el-table-column>
					    <el-table-column prop="name" label="姓名" width="120">
					    </el-table-column>
					    <el-table-column prop="idCard" label="身份证号码" width="200">
					    </el-table-column>
					    <el-table-column label="性别" width="120">
						    <template slot-scope="scope">
						        <span>{{ scope.row.sex == 0 ? "女" : "男" }}</span>
						    </template>
					    </el-table-column>
					    <el-table-column prop="address" label="居住地址" width="300">
					    </el-table-column>
					    <el-table-column prop="eduLevel" label="教育程度" width="120">
					    </el-table-column>
					    <el-table-column prop="phone" label="手机号码" width="150">
					    </el-table-column>
					    <el-table-column prop="nation" label="名族" width="100">
					    </el-table-column>
					    <el-table-column prop="email" label="邮箱" width="200">
					    </el-table-column>
					    <el-table-column prop="qq" label="qq号码" width="150">
					    </el-table-column>
					    <el-table-column prop="wechat" label="微信号" width="150">
					    </el-table-column>
					    <el-table-column prop="politicalBg" label="政治背景" width="300">
					    </el-table-column>
					    <el-table-column fixed="right" label="操作" width=270>
						    <template slot-scope="scope">
						      <shiro:hasPermission name="base:user:delete">  
							      <el-button @click="base_manager_deleteBaseUser(scope.row)" type="text" size="small">删除</el-button>
						      </shiro:hasPermission>
						      <shiro:hasPermission name="base:user:update">  
							      <el-button @click="baseUser_manager_openUpdateBaseUserDialog(scope.row)" type="text" size="small">修改信息</el-button>
						      </shiro:hasPermission>
						      <shiro:hasPermission name="base:user:update">  
						      	   <el-button v-if="scope.row.isParty == 0" @click="baseUser_manager_baseUsertoPartyUser(scope.row)" type="text" size="small">成为预备党员</el-button>
						      	   <el-button v-if="scope.row.isParty == 1" @click="partyUser_manager_openUpdatePartyUserDialog(scope.row)" type="text" size="small"><span style="color:red">党员信息</span></el-button>
						      </shiro:hasPermission>
					  		  <shiro:hasPermission name="org:relation:insert">  
						 	      <el-button @click="baseUser_manager_openOrgRelationDialog(scope.row)" type="text" size="small">加入组织</el-button>
						  	  </shiro:hasPermission>
						    </template>
						</el-table-column>
				    </el-table>
			  	</template>
			</el-main>
			<el-footer>
				<el-pagination id="baseUser_manager_pagesdididi" 
				  layout="total, prev, pager, next, jumper" 
	      		  @current-change="baseUser_manager_pagerCurrentChange"
				  :current-page.sync="baseUser_manager_pager.pageNum"
				  :page-size.sync="baseUser_manager_pager.pageSize"
				  :total="baseUser_manager_pager.total">
				</el-pagination>
			</el-footer>
		</el-container>
		
		
		<el-dialog title="用户信息修改" :visible.sync="baseUser_manager_updateBaseUserDialog">
			<el-form size="mini" size="mini" label-position="left" label-position="left" :model="baseUser_manager_updateBaseUserForm" status-icon :rules="baseUser_manager_updateBaseUserRules" 
				ref="baseUser_manager_updateBaseUserForm" label-width="100px">
				<el-form-item label="用户ID" prop="uid">
				    <el-input :disabled="true" v-model="baseUser_manager_updateBaseUserForm.uid" placeholder="用户ID"></el-input>
				</el-form-item>
				<el-form-item label="姓名" prop="name">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.name"></el-input>
				</el-form-item>
				<el-form-item label="身份证号码" prop="idCard">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.idCard"></el-input>
				</el-form-item>
				<el-form-item label="性别" prop="sex">
				    <el-select v-model="baseUser_manager_updateBaseUserForm.sex">
				      <el-option label="女" value="0"></el-option>
				      <el-option label="男" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="居住地址" prop="address">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.address" placeholder="居住地址"></el-input>
				</el-form-item>
				<el-form-item label="受教育程度" prop="eduLevel">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.eduLevel" placeholder="受教育程度"></el-input>
				</el-form-item>
				<el-form-item label="手机号" prop="phone">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.phone" placeholder="联系手机"></el-input>
				</el-form-item>
				<el-form-item label="民族" prop="nation">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.nation" placeholder="民族"></el-input>
				</el-form-item>
				<el-form-item label="qq" prop="qq">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.qq" placeholder="qq"></el-input>
				</el-form-item>
				<el-form-item label="微信号" prop="wechat">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.wechat" placeholder="微信号"></el-input>
				</el-form-item>
				<el-form-item label="政治背景" prop="politicalBg">
				    <el-input v-model="baseUser_manager_updateBaseUserForm.politicalBg" placeholder="政治背景"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="baseUser_manager_updateBaseUser('baseUser_manager_updateBaseUserForm')">更新用户信息</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog @close="baseUser_manager_resetinsertBaseUserForm('baseUser_manager_insertBaseUserForm')" 
			title="添加用户" :visible.sync="baseUser_manager_insertBaseUserDialog">
			<el-form size="mini" size="mini" label-position="left" :model="baseUser_manager_insertBaseUserForm" status-icon :rules="baseUser_manager_insertBaseUserRules" 
				ref="baseUser_manager_insertBaseUserForm" label-width="100px">
				<el-form-item label="姓名" prop="name">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.name"></el-input>
				</el-form-item>
				<el-form-item label="身份证号码" prop="idCard">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.idCard"></el-input>
				</el-form-item>
				<el-form-item label="性别" prop="sex">
				    <el-select v-model="baseUser_manager_insertBaseUserForm.sex">
				      <el-option label="女" value="0"></el-option>
				      <el-option label="男" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="居住地址" prop="address">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.address" placeholder="居住地址"></el-input>
				</el-form-item>
				<el-form-item label="受教育程度" prop="eduLevel">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.eduLevel" placeholder="受教育程度"></el-input>
				</el-form-item>
				<el-form-item label="手机号" prop="phone">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.phone" placeholder="联系手机"></el-input>
				</el-form-item>
				<el-form-item label="民族" prop="nation">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.nation" placeholder="民族"></el-input>
				</el-form-item>
				<el-form-item label="qq" prop="qq">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.qq" placeholder="qq"></el-input>
				</el-form-item>
				<el-form-item label="微信号" prop="wechat">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.wechat" placeholder="微信号"></el-input>
				</el-form-item>
				<el-form-item label="政治背景" prop="politicalBg">
				    <el-input v-model="baseUser_manager_insertBaseUserForm.politicalBg" placeholder="政治背景"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="baseUser_manager_insertBaseUser('baseUser_manager_insertBaseUserForm')">添加用户用户</el-button>
				    <el-button @click="baseUser_manager_resetinsertBaseUserForm('baseUser_manager_insertBaseUserForm')">重置</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog @close="partyUser_manager_resetUpdatePartyUserForm" title="党员信息变更" :visible.sync="party_manager_updatePartyUserDialog">
			<el-form size="mini" size="mini" label-position="left" :model="partyUser_manager_updatePartyUserForm" status-icon :rules="partyUser_manager_updatePartyUserRules" 
				ref="partyUser_manager_updatePartyUserForm" label-width="100px">
				<el-form-item label="党员ID" prop="uid">
				    <el-input :disabled="true" v-model="partyUser_manager_updatePartyUserForm.uid"></el-input>
				</el-form-item>
				<el-form-item label="入党时间" prop="joinDate">
				    <el-input :disabled="true" v-model="partyUser_manager_updatePartyUserForm.joinDate"></el-input>
				</el-form-item>
				<el-form-item label="党务工作者" prop="partyStaff">
				    <el-select :disabled=party_manager_isPrepareParty v-model="partyUser_manager_updatePartyUserForm.partyStaff">
				      <el-option label="否" :value="0"></el-option>
				      <el-option label="是" :value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="党代表" prop="partRepresentative">
				    <el-select :disabled=party_manager_isPrepareParty v-model="partyUser_manager_updatePartyUserForm.partRepresentative">
				      <el-option label="否" :value="0"></el-option>
				      <el-option label="是" :value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="志愿者" prop="volunteer">
				    <el-select :disabled=party_manager_isPrepareParty v-model="partyUser_manager_updatePartyUserForm.volunteer">
				      <el-option label="否" :value="0"></el-option>
				      <el-option label="是" :value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="困难党员" prop="difficultMember">
				    <el-select :disabled=party_manager_isPrepareParty v-model="partyUser_manager_updatePartyUserForm.difficultMember">
				      <el-option label="否" :value="0"></el-option>
				      <el-option label="是" :value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="先进党员" prop="advancedMember">
				    <el-select :disabled=party_manager_isPrepareParty v-model="partyUser_manager_updatePartyUserForm.advancedMember">
				      <el-option label="否" :value="0"></el-option>
				      <el-option label="是" :value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="预备党员" prop="reserveMember">
				    <el-select :disabled="true" v-model="partyUser_manager_updatePartyUserForm.reserveMember">
				      <el-option label="否" :value="0"></el-option>
				      <el-option label="是" :value="1"></el-option>
				    </el-select>
				    <!-- 转正时间为空表示是预备党员 -->
				    <el-button v-if="partyUser_manager_updatePartyUserForm.joinDate == null" type="primary" @click="party_manager_changeIsYBOrZS">
				    	<span>转正</span>
				    </el-button>
				</el-form-item>
				<el-form-item label="预备党员批准时间" prop="reserveApprovalDate">
				    <el-input :disabled="true" v-model="partyUser_manager_updatePartyUserForm.reserveApprovalDate"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="partyUser_manager_updatePartyUser">更新党员信息</el-button>
				    <el-button type="danger" @click="partyUser_manager_deletePartyUser">删除党员</el-button>
				    <el-button type="primary" @click="partyUser_manager_exportPartyUsersExcel">下载党员信息</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog title="添加组织成员" :visible.sync="baseUser_manager_insertOrgRelationDialog">
		    <el-form size="mini" size="mini" label-position="left" :model="baseUser_manager_insertOrgRelationForm" status-icon 
					ref="baseUser_manager_insertOrgRelationForm" label-width="100px">
				<el-form-item label="用户ID" prop="orgRltUserId">
				    <el-input :disabled="true" type="text" v-model="baseUser_manager_insertOrgRelationForm.orgRltUserId" placeholder="用户ID"></el-input>
				</el-form-item>
				<el-form-item label="组织" prop="orgRltInfoId">
				    <el-tree :expand-on-click-node="false" :highlight-current="true" :data="baseUser_manager_queryOrgInfosForInsertRelationTreeCondition" 
				    	:props="baseUser_manager_queryOrgInfosForInsertRelationConditionTreeProps" 
				    	@node-click="baseUser_manager_queryOrgDutyForOrgInfoClickTree">
				  	</el-tree>
				</el-form-item>
				<el-form-item label="对应职责" prop="orgRltDutyIds">
				    <el-tree :default-expand-all="true" 
				    	node-key="id" 
				    	ref="baseUser_manager_queryOrgDutyForOrgInfoTree"
				    	show-checkbox 
				    	:expand-on-click-node="false" 
				    	:highlight-current="true" 
				    	:data="baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition" 
				    	:props="baseUser_manager_queryOrgDutyForOrgInfoClickConditionTreeProps" 
				    	:check-strictly="true" >
				  	</el-tree>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="baseUser_manager_insertOrgRlts('baseUser_manager_insertOrgRelationForm')">添加组织成员信息</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</body>

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			baseUser_manager_insertOrgRelationForm: {
				orgRltUserId: "",
				orgRltInfoId: "",
				orgRltDutyIds: []
			},
			baseUser_manager_queryOrgInfosForInsertRelationTreeCondition: [],
			baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition: [],
			baseUser_manager_queryOrgInfosForInsertRelationConditionTreeProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
			baseUser_manager_queryOrgDutyForOrgInfoClickConditionTreeProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgDutyName;
	            }
			},
			before_reserveMember: "",
			party_manager_isPrepareParty: false,	/* 是否党员，设置下拉框是否可用 */
			partyUser_manager_updatePartyUserForm: {	/* 党员信息弹窗属性保存、设置 */
				uid: "",
				joinDate: "",
				partyStaff: -1,
				partRepresentative: -1,
				volunteer: -1,
				difficultMember: -1,
				advancedMember: -1,
				reserveMember: -1,
				reserveApprovalDate: ""
			},
			partyUser_manager_updatePartyUserRules: {
				
			},
			baseUser_manager_pager: {	/*初始化基础用户分页信息*/
		    	pageNum: 1,		/* 当前页 */
		    	pageSize: 12,	/* 页面大小 */
		    	total: 0,
		    	list: []
		    },
		    baseUser_manager_initBaseUserSex: [	/* 用户性别值初始化 */
				{
					value: '0',
			        label: '女'
				},{
					value: '1',
			        label: '男'
				}
			],
			baseUser_manager_initBaseUserIsParty: [
				{
					value: '0',
			        label: '否'
				},{
					value: '1',
			        label: '是'
				}
			],
			baseUser_manager_updateBaseUserForm: {
				uid: "",
				name:"",
				idCard:"",
				sex:"",
				address:"",
				eduLevel:"",
				phone:"",
				nation:"",
				email:"",
				qq:"",
				wechat:"",
				politicalBg:""
			},
			baseUser_manager_insertBaseUserForm: {
				uid: "",
				name:"",
				idCard:"",
				sex:"",
				address:"",
				eduLevel:"",
				phone:"",
				nation:"",
				email:"",
				qq:"",
				wechat:"",
				politicalBg:""
			},
			baseUser_manager_baseUserSex: "",
			baseUser_manager_baseUserIsParty: "",
			baseUser_manager_updateBaseUserDialog: false,
		    baseUser_manager_insertBaseUserDialog: false,
		    party_manager_updatePartyUserDialog: false,
		    baseUser_manager_insertOrgRelationDialog: false,
		    baseUser_manager_updateBaseUserRules: {
				idCard: [
					{ required: true, message: '请输入身份证号码!', trigger: 'blur' },
					{ pattern: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/, message: '请输入正确的身份证号码!'}
				],
				phone: [
		        	{ required: true, message: '请输入联系号码!', trigger: 'blur' },
		        	{ pattern: "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message: '号码输入不正确!'}
		        ],
		        qq: [
					{ pattern: /[0-9]+/, message: '请输入正确qq号码!'}
				],
				wechat: [
					{ pattern: /^[A-Za-z0-9_]+$/, message: '请输入正确微信号码!'}
				]
			},
			baseUser_manager_insertBaseUserRules: {
				idCard: [
					{ required: true, message: '请输入身份证号码!', trigger: 'blur' },
					{ pattern: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/, message: '请输入正确的身份证号码!'}
				],
				phone: [
		        	{ required: true, message: '请输入联系号码!', trigger: 'blur' },
		        	{ pattern: "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message: '号码输入不正确!'}
		        ],
		        qq: [
					{ pattern: /[0-9]+/, message: '请输入正确qq号码!'}
				],
				wechat: [
					{ pattern: /^[A-Za-z0-9_]+$/, message: '请输入正确微信号码!'}
				]
			}
		},
		mounted:function () {
			this.baseUser_manager_queryBaseUsers();
		},
		methods: {
			partyUser_manager_deletePartyUser () {	/* 移除党员党员 */
				var obj = this;
				var url = "/party/user/deletePartyUser";
				var t = {
					uid: obj.partyUser_manager_updatePartyUserForm.uid,
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('成功',data.msg,'success');
						obj.party_manager_updatePartyUserDialog = false;
						obj.baseUser_manager_queryBaseUsers();
					}
					
				})
			},
			partyUser_manager_updatePartyUser () {	/* 变更党员信息 */
				var obj = this;
				var url = "/party/user/updatePartyUser";
				var _joinDate = null;
				/* 两个值不对应说明预备党员的信息发生了修改，说明此预备党员附带转正操作 */
				if(obj.before_reserveMember != obj.partyUser_manager_updatePartyUserForm.reserveMember) {	
					_joinDate = new Date();
				}
				var t = {
					uid: obj.partyUser_manager_updatePartyUserForm.uid,
					joinDate: _joinDate,
					partyStaff:	obj.partyUser_manager_updatePartyUserForm.partyStaff,
					partRepresentative: obj.partyUser_manager_updatePartyUserForm.partRepresentative,
					volunteer: obj.partyUser_manager_updatePartyUserForm.volunteer,
					difficultMember: obj.partyUser_manager_updatePartyUserForm.difficultMember,
					advancedMember: obj.partyUser_manager_updatePartyUserForm.advancedMember,
					reserveMember: obj.partyUser_manager_updatePartyUserForm.reserveMember
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('成功',data.msg,'success')
						if (data.data == undefined) {	
							obj.party_manager_updatePartyUserDialog = false;
    						obj.baseUser_manager_queryBaseUsers();
						} 
					}
					
				})
			},
			party_manager_changeIsYBOrZS() {
				var obj = this;
				//只有当点击转正时才变更是否为预备党员信息
				obj.partyUser_manager_updatePartyUserForm.reserveMember = 
						obj.partyUser_manager_updatePartyUserForm.reserveMember == 1 ? 0 : 1;	
				/* 不是预备党员点击设为党员 */
				obj.party_manager_isPrepareParty = obj.partyUser_manager_updatePartyUserForm.reserveMember == 0 ? false : true;	
				/* 预备党员点击转正按钮后可以设置党员信息，如果取消转正将党员信息还原默认值 */
				if (obj.partyUser_manager_updatePartyUserForm.reserveMember == 1) {
					obj.partyUser_manager_updatePartyUserForm.partyStaff = 0;
					obj.partyUser_manager_updatePartyUserForm.partRepresentative = 0;
					obj.partyUser_manager_updatePartyUserForm.volunteer = 0;
					obj.partyUser_manager_updatePartyUserForm.difficultMember = 0;
					obj.partyUser_manager_updatePartyUserForm.advancedMember = 0;
				} 
			},
			baseUser_manager_initPager(){
				var obj = this;
				obj.baseUser_manager_pager.pageNum = 1;
				obj.baseUser_manager_pager.pageSize = 12;
				obj.baseUser_manager_pager.total = 0;
				obj.baseUser_manager_pager.list = new Array();
			},
			baseUser_manager_queryBaseUsers () {
				var obj = this;
				var url = "/base/user/queryBaseUsers";
				var t = {
					isParty: obj.baseUser_manager_baseUserIsParty,
					sex: obj.baseUser_manager_baseUserSex,
					pageNum: obj.baseUser_manager_pager.pageNum,
					pageSize: obj.baseUser_manager_pager.pageSize
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.baseUser_manager_initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.baseUser_manager_pager = data.data;
						}
					}
					
				})
			},
			baseUser_manager_openUpdateBaseUserDialog(row) {
				var obj = this;
				obj.baseUser_manager_updateBaseUserDialog = true;
				obj.baseUser_manager_updateBaseUserForm.uid = row.uid;
				obj.baseUser_manager_updateBaseUserForm.name = row.name;
				obj.baseUser_manager_updateBaseUserForm.idCard = row.idCard;
				obj.baseUser_manager_updateBaseUserForm.sex = row.sex == 0 ? "0" : "1";
				obj.baseUser_manager_updateBaseUserForm.address = row.address;
				obj.baseUser_manager_updateBaseUserForm.eduLevel = row.eduLevel;
				obj.baseUser_manager_updateBaseUserForm.phone = row.phone;
				obj.baseUser_manager_updateBaseUserForm.nation = row.nation;
				obj.baseUser_manager_updateBaseUserForm.email = row.email;
				obj.baseUser_manager_updateBaseUserForm.qq = row.qq;
				obj.baseUser_manager_updateBaseUserForm.wechat = row.wechat;
				obj.baseUser_manager_updateBaseUserForm.politicalBg = row.politicalBg;
			},
			baseUser_manager_updateBaseUser(baseUser_manager_updateBaseUserForm) {
				var obj = this;
		        this.$refs[baseUser_manager_updateBaseUserForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/base/user/updateBaseUser";
		            	if (obj.baseUser_manager_updateBaseUserForm.sex == "女") {
		            		obj.baseUser_manager_updateBaseUserForm.sex = 0;
		            	} else if (obj.baseUser_manager_updateBaseUserForm.sex == "男") {
		            		obj.baseUser_manager_updateBaseUserForm.sex = 1;
		            	}
        				var t = {
        					uid: obj.baseUser_manager_updateBaseUserForm.uid,
       						name: obj.baseUser_manager_updateBaseUserForm.name,
        					idCard: obj.baseUser_manager_updateBaseUserForm.idCard,
        					sex: obj.baseUser_manager_updateBaseUserForm.sex,
        					address: obj.baseUser_manager_updateBaseUserForm.address,
        					eduLevel: obj.baseUser_manager_updateBaseUserForm.eduLevel,
        					phone: obj.baseUser_manager_updateBaseUserForm.phone,
        					nation: obj.baseUser_manager_updateBaseUserForm.nation,
        					email: obj.baseUser_manager_updateBaseUserForm.email,
        					qq: obj.baseUser_manager_updateBaseUserForm.qq,
        					wechat: obj.baseUser_manager_updateBaseUserForm.wechat,
        					politicalBg: obj.baseUser_manager_updateBaseUserForm.politicalBg
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('修改成功',data.msg,'success')
        						obj.baseUser_manager_updateBaseUserDialog = false;
        						obj.baseUser_manager_queryBaseUsers();
        					}
        				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
		    },
		    partyUser_manager_openUpdatePartyUserDialog(row) {	/* 根据用户id查询党员信息 */
		    	var obj = this;
		    	var url = "/party/user/queryPartyUsersNotPage";
				var t = {
					uid: row.uid
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.partyUser_manager_updatePartyUserForm.uid = data.data[0].uid;
						obj.partyUser_manager_updatePartyUserForm.joinDate = data.data[0].joinDate;
						obj.partyUser_manager_updatePartyUserForm.partyStaff = data.data[0].partyStaff;
						obj.partyUser_manager_updatePartyUserForm.partRepresentative = data.data[0].partRepresentative;
						obj.partyUser_manager_updatePartyUserForm.volunteer = data.data[0].volunteer;
						obj.partyUser_manager_updatePartyUserForm.difficultMember = data.data[0].difficultMember;
						obj.partyUser_manager_updatePartyUserForm.advancedMember = data.data[0].advancedMember;
						obj.partyUser_manager_updatePartyUserForm.reserveMember = data.data[0].reserveMember;
						obj.partyUser_manager_updatePartyUserForm.reserveApprovalDate = data.data[0].reserveApprovalDate;
						obj.before_reserveMember = obj.partyUser_manager_updatePartyUserForm.reserveMember;	/* 记录之前的是否预备党员信息 */
						/* 如果是0代表不是预备党员 */
						obj.party_manager_isPrepareParty = obj.partyUser_manager_updatePartyUserForm.reserveMember == 0 ? false : true;
		    			obj.party_manager_updatePartyUserDialog = true;
					}
				})
		    },
		    partyUser_manager_resetUpdatePartyUserForm(){	/* 党员信息弹窗关闭时清空相关的信息 */
		    	var obj = this;
		    	obj.partyUser_manager_updatePartyUserForm.uid = "";
				obj.partyUser_manager_updatePartyUserForm.joinDate = "";
				obj.partyUser_manager_updatePartyUserForm.partyStaff = "";
				obj.partyUser_manager_updatePartyUserForm.partRepresentative = "";
				obj.partyUser_manager_updatePartyUserForm.volunteer = "";
				obj.partyUser_manager_updatePartyUserForm.difficultMember = "";
				obj.partyUser_manager_updatePartyUserForm.advancedMember = "";
				obj.partyUser_manager_updatePartyUserForm.reserveMember = "";
				obj.partyUser_manager_updatePartyUserForm.reserveApprovalDate = "";
				obj.before_reserveMember = "";	/* 记录之前的是否预备党员信息 */
				obj.party_manager_isPrepareParty = false;
		    },
		    baseUser_manager_insertBaseUser(baseUser_manager_insertBaseUserForm) {	/* 添加基础用户 */
		    	var obj = this;
		    	this.$refs[baseUser_manager_insertBaseUserForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/base/user/insertBaseUser";
        				var t = {
       						uid: obj.baseUser_manager_insertBaseUserForm.uid,
        					name: obj.baseUser_manager_insertBaseUserForm.name,
           					idCard: obj.baseUser_manager_insertBaseUserForm.idCard,
           					sex: obj.baseUser_manager_insertBaseUserForm.sex,
           					address: obj.baseUser_manager_insertBaseUserForm.address,
           					eduLevel: obj.baseUser_manager_insertBaseUserForm.eduLevel,
           					phone: obj.baseUser_manager_insertBaseUserForm.phone,
           					nation: obj.baseUser_manager_insertBaseUserForm.nation,
           					email: obj.baseUser_manager_insertBaseUserForm.email,
           					qq: obj.baseUser_manager_insertBaseUserForm.qq,
           					wechat: obj.baseUser_manager_insertBaseUserForm.wechat,
           					politicalBg: obj.baseUser_manager_insertBaseUserForm.politicalBg
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('添加成功',data.msg,'success')
        						obj.baseUser_manager_resetinsertBaseUserForm("baseUser_manager_insertBaseUserForm");
        						obj.baseUser_manager_insertBaseUserDialog = false;
        						obj.baseUser_manager_queryBaseUsers();
        					}
        				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
		    },
		    baseUser_manager_baseUsertoPartyUser(row) {	/* 普通成员转正成为预备党员 */
		    	var obj = this;
		    	var url = "/party/user/insertPartyUser";
				var t = {
					uid: row.uid,	/* 成为预备党员的用户id */
					reserveMember: 1	/* 设置为预备党员，不是党员，党员相关信息不可设置，使用数据库默认值0 */
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
    					toast('修改成功',"已成为预备党员",'success')
    					obj.baseUser_manager_queryBaseUsers();
					}
				})
		    },
		    baseUser_manager_resetinsertBaseUserForm(baseUser_manager_insertBaseUserForm) {
		    	var obj = this;
		    	obj.$refs[baseUser_manager_insertBaseUserForm].resetFields();
		    },
			baseUser_manager_pagerCurrentChange () {
				var obj = this;
				obj.baseUser_manager_queryBaseUsers();
			},
			baseUser_manager_queryBaseUsersBySex () {
				var obj = this;
				obj.baseUser_manager_queryBaseUsers();
			},
			baseUser_manager_queryBaseUsersByIsParty () {
				var obj = this;
				obj.baseUser_manager_queryBaseUsers();
			},
			base_manager_deleteBaseUser(row) {
				var obj = this;
				var url = "/base/user/deleteBaseUser";
				var t = {
					uid: row.uid
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('删除成功',data.msg,'success')
						obj.baseUser_manager_queryBaseUsers();
					}
				})
			},
			baseUser_manager_openOrgRelationDialog(row) {	/* 加入组织弹窗打开时的操作 */
				var obj = this;
				obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds = [];
				obj.baseUser_manager_insertOrgRelationForm.orgRltUserId = "";
				obj.baseUser_manager_insertOrgRelationForm.orgRltInfoId = "";
				
				obj.baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition = [];	/* 初始化组织职责 */
				obj.baseUser_manager_queryOrgInfosForInsertRelationTreeCondition = []; /* 初始化组织信息 */
				obj.baseUser_manager_insertOrgRelationForm.orgRltUserId = row.uid;	/* 设置并保存用户id */
				
				var url = "/org/info/queryOrgInfosToTree";/* 插入组织成员时组织信息查询 */
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.baseUser_manager_queryOrgInfosForInsertRelationTreeCondition = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									orgInfoId: -1,
									orgInfoName: "所有组织（不能以此为组织）"
								},
								children: datas.data
							}
						];
					}
					
				})
				
				obj.baseUser_manager_insertOrgRelationDialog = true;
			},
			baseUser_manager_queryOrgDutyForOrgInfoClickTree(data) {	/* 选择组织时根据组织查询此组织拥有的全部职责 */
				var obj = this;
				obj.baseUser_manager_insertOrgRelationForm.orgRltInfoId = data.data.orgInfoId;	/* 保存组织id */
				obj.baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition = [];
				if (data.data.orgInfoTypeId == undefined) {
					return;
				}
				var url = "/org/duty/queryOrgDutyForOrgInfoClick";
				var t = {
					orgDutyTypeId: data.data.orgInfoTypeId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition = datas.data;
						obj.forbaseUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(obj.baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition);
					}
					
				})
			},
			forbaseUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTrees){	/* 向树里添加id属性，方便设置node-key */
				var obj = this;
				if(menuTrees != null) {
					for (var i = 0; i < menuTrees.length; i++) {
						var menuTree = menuTrees[i];
						menuTree.id = menuTree.data.orgDutyId;
						obj.forbaseUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTree.children);
					}
				}
			},
			baseUser_manager_insertOrgRlts(baseUser_manager_insertOrgRelationForm){
				var obj = this;					
				var url = "/org/relation/insertOrgRelation";
				
				var checkedKeys = [];
				checkedKeys = obj.$refs["baseUser_manager_queryOrgDutyForOrgInfoTree"].getCheckedKeys(false);
				if (checkedKeys.length == 0) {
					toast('错误',"请选择该用户在此组织担任的职责",'error');
					return;
				}
				var t = {
					orgRltUserId: obj.baseUser_manager_insertOrgRelationForm.orgRltUserId,
					orgRltInfoId: obj.baseUser_manager_insertOrgRelationForm.orgRltInfoId,
					orgRltDutys: checkedKeys
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						toast('成功',datas.msg,'success');
						obj.baseUser_manager_insertOrgRelationDialog = false;
					}
					
				})	
				
			},
			partyUser_manager_exportPartyUsersExcelExample() {	/* 下载党员导入excel格式示例 */
				window.location = "/excel/exportPartyUsersExcelExample";
			},
			partyUser_manager_validatePartyUsersExcel(thisFile) {	/* 文件上传前的类型校验 */
				var fileFormat = thisFile.name.split(".");
				var fileSuffix = fileFormat[fileFormat.length - 1];	/* 拿到文件后缀 */
				if (fileSuffix == "xlsx" || fileSuffix == "xls") {
					return true;
				} else {
					toast('格式错误',"只能上传excel文档（xlsx、xls）",'error');
					return false;
				}
			},
			partyUser_manager_importPartyUsersExcel (thisImport) {	/* 自定义文件上传 */		
				var obj = this;
				var formData = new FormData();
				formData.append("file", thisImport.file);
				$.ajax({
                   url: "/excel/importPartyUsersExcel",
                   data: formData,
                   type: "Post",
                   dataType: "json",
                   cache: false,//上传文件无需缓存
                   processData: false,//用于对data参数进行序列化处理 这里必须false
                   contentType: false, //必须
                   success: function (data) {
                	   if (data.code == 200) {
                		   toast('导入成功',data.msg,'success');
                		   obj.baseUser_manager_queryBaseUsers();
                	   } else if (data.code == 500) {
                		   toast('导入失败',data.msg,'error');
                		   window.location = "/excel/downloadValidataMsg";
                	   }
                   },
               })
			},
			partyUser_manager_exportPartyUsersExcel() {
				var obj = this;
				var uid = obj.partyUser_manager_updatePartyUserForm.uid
				window.location = "/excel/exportPartyUsersExcel?uid="+uid;
			}
		}
	});
</script>
</html>