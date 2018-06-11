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
<title>权限管理</title>
<%@include file="/include/head.jsp"%>
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
		<template> 
			<el-tabs v-model="selectJurisdictionTabs">
				<el-tab-pane label="系统用户管理" name="sys_manager_tab_pane">
					<el-container>
					  <el-header>
					  	<el-row>
					  		<shiro:hasPermission name="sys:user:insert">  
						 	    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="insertSysUserDialog = true">添加系统用户</el-button>
						  	</shiro:hasPermission>
						</el-row>
					  </el-header>
					  <el-header>
					  	<el-row	id="querySysUserStyle">
						  	<span>
						  		用户状态
							  <el-select clearable v-model="sysUserStatu" @change="querySysUsersByStatus" placeholder="请选择系统用户状态">
							   	 <el-option v-for="item in usersStatus" :key="item.value" :label="item.label" :value="item.value"></el-option>
							  </el-select>
							</span>
							<span>
								创建日期
							    <el-date-picker v-model="sysUsercreateTime" @change="querySysUsersBycreateTime" type="daterange" align="right" unlink-panels range-separator="至"
							      start-placeholder="开始日期" end-placeholder="结束日期">
							    </el-date-picker>
							</span>
						</el-row>
					  </el-header>
					  <el-main>
					  	<template>
					  		<el-table size="mini" :data="pager.list" style="width: 100%" max-height="550">
							    <el-table-column fixed prop="userId" label="ID" width="50">
							    </el-table-column>
							    <el-table-column prop="username" label="用户名" width="120">
							    </el-table-column>
							    <el-table-column prop="password" label="密码" width="120">
							    </el-table-column>
							    <el-table-column prop="salt" label="盐" width="120">
							    </el-table-column>
							    <el-table-column prop="email" label="邮箱" width="300">
							    </el-table-column>
							    <el-table-column prop="mobile" label="手机号" width="120">
							    </el-table-column>
							    <el-table-column label="状态" width="120">
								    <template slot-scope="scope">
								        <span>{{ scope.row.status == 0 ? "禁用" : "可用" }}</span>
								    </template>
							    </el-table-column>
							    <el-table-column prop="createTime" label="创建时间" width="300">
							    </el-table-column>
							    <el-table-column fixed="right" label="操作" width=180>
								    <template slot-scope="scope">
								      <shiro:hasPermission name="sys:user:delete">  
									      <el-button @click="deleteSysUser(scope.row)" type="text" size="small">删除</el-button>
								      </shiro:hasPermission>
								      <shiro:hasPermission name="sys:user:update">  
									      <el-button @click="openUpdateSysUserDialog(scope.row)" type="text" size="small">修改信息</el-button>
								      </shiro:hasPermission>
								      <shiro:hasPermission name="sys:userRole:update">  
									      <el-button @click="openChangeSysUserRoleDialog(scope.row)" type="text" size="small">角色变更</el-button>
								      </shiro:hasPermission>
								    </template>
							    </el-table-column>
							 </el-table>
					  	</template>
					  </el-main>
					  <el-footer>
				  		<el-pagination id="pagesdididi" 
						  layout="total, prev, pager, next, jumper" 
			      		  @current-change="pagerCurrentChange(pager.pageNum, pager.pageSize)"
						  :current-page.sync="pager.pageNum"
						  :page-size.sync="pager.pageSize"
						  :total="pager.total">
						</el-pagination>
					  </el-footer>
					</el-container>
				</el-tab-pane>
				<el-tab-pane label="角色管理" name="role_manager_tab_pane">
					<el-container>
						<el-header>
							<el-row>
						  		<shiro:hasPermission name="sys:role:insert">  
							 	    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="role_manager_showInsertSysRoleDialog">新建角色</el-button>
							  	</shiro:hasPermission>
							</el-row>
						</el-header>
						<el-main>
							<template>
						  		<el-table size="mini" :data="role_manager_pager.list" style="width: 100%" max-height="550">
								    <el-table-column fixed prop="roleId" label="角色ID" width="100">
								    </el-table-column>
								    <el-table-column prop="roleName" label="角色名" width="120">
								    </el-table-column>
								    <el-table-column prop="remark" label="角色备注" width="200">
								    </el-table-column>
								    <el-table-column prop="createTime" label="创建时间" width="300">
								    </el-table-column>
								    <el-table-column label="操作" width=180>
									    <template slot-scope="scope">
									      <shiro:hasPermission name="sys:role:delete">  
										      <el-button @click="role_manager_deleteSysRole(scope.row)" type="text" size="small">删除</el-button>
									      </shiro:hasPermission>
									      <shiro:hasPermission name="sys:role:update">  
										      <el-button @click="role_manager_openUpdateSysRoleDialog(scope.row)" type="text" size="small">修改信息</el-button>
									      </shiro:hasPermission>
									      <shiro:hasPermission name="sys:roleMenu:update">  
										      <el-button @click="role_manager_openChangeSysRoleMenuDialog(scope.row)" type="text" size="small">权限变更</el-button>
									      </shiro:hasPermission>
									    </template>
								    </el-table-column>
								 </el-table>
						  	</template>
						</el-main>
						<el-footer>
							<el-pagination id="role_manager_pagesdididi" 
							  layout="total, prev, pager, next, jumper" 
				      		  @current-change="role_manager_pagerCurrentChange"
							  :current-page.sync="role_manager_pager.pageNum"
							  :page-size.sync="role_manager_pager.pageSize"
							  :total="role_manager_pager.total">
							</el-pagination>
						</el-footer>
					</el-container>
				</el-tab-pane>
				
				<el-tab-pane label="基础用户/党员管理" name="baseUser_manager_tab_pane">
					<el-container>
						<el-header>
						  	<el-row>
						  		<shiro:hasPermission name="base:user:insert">  
							 	    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="baseUser_manager_insertBaseUserDialog = true">添加用户</el-button>
							  	</shiro:hasPermission>
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
				</el-tab-pane>
			
			</el-tabs> 
		</template>
		
		
		<el-dialog title="基础用户信息修改" :visible.sync="baseUser_manager_updateBaseUserDialog">
			<el-form size="mini" :model="baseUser_manager_updateBaseUserForm" status-icon :rules="baseUser_manager_updateBaseUserRules" 
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
			<el-form size="mini" :model="baseUser_manager_insertBaseUserForm" status-icon :rules="baseUser_manager_insertBaseUserRules" 
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
		
		<el-dialog @close="role_manager_resetinsertSysRoleForm('role_manager_insertSysRoleForm')" title="新建角色" :visible.sync="role_manager_insertSysRoleDialog">
			<el-form size="mini" :model="role_manager_insertSysRoleForm" status-icon :rules="role_manager_insertSysRoleRules" 
				ref="role_manager_insertSysRoleForm" label-width="100px">
				<el-form-item label="角色名称" prop="roleName">
				    <el-input v-model="role_manager_insertSysRoleForm.roleName" placeholder="仅支持字母和数字组合成的用户名填写"></el-input>
				</el-form-item>
				<el-form-item label="角色描述" prop="remark">
				    <el-input v-model="role_manager_insertSysRoleForm.remark"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="role_manager_insertSysRole('role_manager_insertSysRoleForm')">添加系统用户</el-button>
				    <el-button @click="role_manager_resetinsertSysRoleForm('role_manager_insertSysRoleForm')">重置</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog title="修改角色信息" :visible.sync="role_manager_updateSysRoleDialog">
			<el-form size="mini" :model="role_manager_updateSysRoleForm" status-icon :rules="role_manager_updateSysRoleRules" 
				ref="role_manager_updateSysRoleForm" label-width="100px">
				<el-form-item label="角色ID" prop="roleId">
				    <el-input :disabled="true" v-model="role_manager_updateSysRoleForm.roleId"></el-input>
				</el-form-item>
				<el-form-item label="角色名称" prop="roleName">
				    <el-input v-model="role_manager_updateSysRoleForm.roleName" placeholder="仅支持字母和数字组合成的用户名填写"></el-input>
				</el-form-item>
				<el-form-item label="角色描述" prop="remark">
				    <el-input v-model="role_manager_updateSysRoleForm.remark"></el-input>
				</el-form-item>
				<el-form-item label="创建时间" prop="createTime">
				    <el-input :disabled="true" v-model="role_manager_updateSysRoleForm.createTime"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="role_manager_updateSysRole('role_manager_updateSysRoleForm')">添加系统用户</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog title="角色权限变更" :visible.sync="role_manager_updateSysRoleMenuDialog">
			<el-tree :default-expand-all="true" show-checkbox :expand-on-click-node="false" :highlight-current="true" 
				:data="role_manager_querySysMenuTreeCondition" 
		    	:props="role_manager_querySysMenuConditionTreeProps" 
		    	@check-change="">
		  	</el-tree>
	    </el-dialog>
	    
	    <el-dialog title="党员信息变更" :visible.sync="party_manager_updatePartyUserDialog">
			<el-form size="mini" :model="partyUser_manager_updatePartyUserForm" status-icon :rules="partyUser_manager_updatePartyUserRules" 
				ref="partyUser_manager_updatePartyUserForm" label-width="100px">
				<el-form-item label="党员ID" prop="uid">
				    <el-input :disabled="true" v-model="partyUser_manager_updatePartyUserForm.uid"></el-input>
				</el-form-item>
				<el-form-item label="入党时间" prop="joinDate">
				    <el-input :disabled="true" v-model="partyUser_manager_updatePartyUserForm.joinDate"></el-input>
				</el-form-item>
				<el-form-item label="党务工作者" prop="partyStaff">
				    <el-select :disabled=party_manager_isYBOrZS v-model="partyUser_manager_updatePartyUserForm.partyStaff">
				      <el-option label="否" value="0"></el-option>
				      <el-option label="是" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="党代表" prop="partRepresentative">
				    <el-select :disabled=party_manager_isYBOrZS v-model="partyUser_manager_updatePartyUserForm.partRepresentative">
				      <el-option label="否" value="0"></el-option>
				      <el-option label="是" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="志愿者" prop="volunteer">
				    <el-select :disabled=party_manager_isYBOrZS v-model="partyUser_manager_updatePartyUserForm.volunteer">
				      <el-option label="否" value="0"></el-option>
				      <el-option label="是" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="困难党员" prop="difficultMember">
				    <el-select :disabled=party_manager_isYBOrZS v-model="partyUser_manager_updatePartyUserForm.difficultMember">
				      <el-option label="否" value="0"></el-option>
				      <el-option label="是" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="先进党员" prop="advancedMember">
				    <el-select :disabled=party_manager_isYBOrZS v-model="partyUser_manager_updatePartyUserForm.advancedMember">
				      <el-option label="否" value="0"></el-option>
				      <el-option label="是" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="预备党员" prop="reserveMember">
				    <el-select :disabled="true" v-model="partyUser_manager_updatePartyUserForm.reserveMember">
				      <el-option label="否" value="0"></el-option>
				      <el-option label="是" value="1"></el-option>
				    </el-select>
				    <el-button v-if="partyUser_manager_updatePartyUserForm.joinDate == null" type="primary" @click="party_manager_changeIsYBOrZS('click')">
				    	<span>预备党员转正</span>
				    </el-button>
				</el-form-item>
				<el-form-item label="预备党员批准时间" prop="reserveApprovalDate">
				    <el-input :disabled="true" v-model="partyUser_manager_updatePartyUserForm.reserveApprovalDate"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="partyUser_manager_updatePartyUser">更新党员信息</el-button>
				    <el-button type="danger" @click="partyUser_manager_deletePartyUser">删除党员</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    
	    <el-dialog @close="resetInsertSysUserForm('insertSysUserForm')" title="添加系统用户" :visible.sync="insertSysUserDialog">
			<el-form size="mini" :model="insertSysUserForm" status-icon :rules="insertSysUserRules" ref="insertSysUserForm" label-width="100px">
				<el-form-item label="用户名称" prop="name">
				    <el-input v-model="insertSysUserForm.name" placeholder="仅支持字母和数字组合成的用户名填写"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="pass">
				    <el-input type="password" v-model="insertSysUserForm.pass" placeholder="必须是字母和数字组合"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="checkPass">
				    <el-input type="password" v-model="insertSysUserForm.checkPass"></el-input>
				</el-form-item>
				<el-form-item label="手机号" prop="mobile">
				    <el-input v-model="insertSysUserForm.mobile" placeholder="仅支持国内个人手机号码填写"></el-input>
				</el-form-item>
				<el-form-item label="联系邮箱" prop="email">
				    <el-input v-model="insertSysUserForm.email" placeholder="示例：zltel@zltel.com"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="insertSysUser('insertSysUserForm')">添加系统用户</el-button>
				    <el-button @click="resetInsertSysUserForm('insertSysUserForm')">重置</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    <el-dialog title="修改系统用户信息" :visible.sync="updateSysUserDialog">
	    	<el-dialog @close="resetupdateSysUserPwdForm('updateSysUserPwdForm')" width="30%" title="修改用户密码" :visible.sync="updateSysUserPwdDialog" append-to-body>
	    		<el-form size="mini" :model="updateSysUserPwdForm" status-icon :rules="updateSysUserPwdRules" ref="updateSysUserPwdForm" label-width="100px">
					<el-form-item label="新密码" prop="pass">
					    <el-input type="password" v-model="updateSysUserPwdForm.pass" placeholder="必须是字母和数字组合"></el-input>
					</el-form-item>
					<el-form-item label="确认密码" prop="checkPass">
					    <el-input type="password" v-model="updateSysUserPwdForm.checkPass"></el-input>
					</el-form-item>
					<el-form-item>
					    <el-button type="primary" @click="updateSysUserPwd('updateSysUserPwdForm')">修改密码</el-button>
					    <el-button @click="resetupdateSysUserPwdForm('updateSysUserPwdForm')">重置</el-button>
					</el-form-item>
				</el-form>
		    </el-dialog>
			<el-form size="mini" :model="updateSysUserForm" status-icon :rules="updateSysUserRules" ref="updateSysUserForm" label-width="100px">
				<el-form-item label="用户ID" prop="userId">
				    <el-input :disabled="true" v-model="updateSysUserForm.userId" placeholder="用户ID"></el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="username">
				    <el-input :disabled="true" v-model="updateSysUserForm.username"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-row>
					  <el-button @click="updateSysUserPwdDialog = true" type="primary" v-model="updateSysUserForm.password">修改密码</el-button>
					</el-row>
				</el-form-item>
				<el-form-item label="联系邮箱" prop="email">
				    <el-input v-model="updateSysUserForm.email" placeholder="示例：zltel@zltel.com"></el-input>
				</el-form-item>
				<el-form-item label="手机号" prop="mobile">
				    <el-input v-model="updateSysUserForm.mobile" placeholder="联系手机"></el-input>
				</el-form-item>
				<el-form-item label="用户状态" prop="status">
				    <el-select v-model="updateSysUserForm.status">
				      <el-option label="禁用" value="0"></el-option>
				      <el-option label="可用" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="创建日期" prop="createTime">
				    <el-input :disabled="true" v-model="updateSysUserForm.createTime"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="updateSysUser('updateSysUserForm')">变更系统用户信息</el-button>
				</el-form-item>
			</el-form>	  
	    </el-dialog>
	    <el-dialog @close="resetChangeSysUserRoleForm" title="用户角色变更" :visible.sync="changeSysUserRoleDialog">
			<template>
			  <el-transfer :props="{key: 'roleId', label: 'remark'}" v-model="sysUserRoles" :data="roleList" :titles="['角色列表', '用户拥有角色']" :button-texts="['移除角色', '添加角色']"></el-transfer>
			</template>
			<el-row>
			    <el-button type="primary" @click="changeSysUserRoles">变更系统用户角色</el-button>
			</el-row>
	    </el-dialog>
	    
	    <el-dialog title="添加组织成员" :visible.sync="baseUser_manager_insertOrgRelationDialog">
		    <el-form size="mini":model="baseUser_manager_insertOrgRelationForm" status-icon 
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
				    <el-tree :default-expand-all="true" show-checkbox :expand-on-click-node="false" :highlight-current="true" :data="baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition" 
				    	:props="baseUser_manager_queryOrgDutyForOrgInfoClickConditionTreeProps" 
				    	@check-change="baseUser_manager_saveRelationDutyId">
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
			role_manager_querySysMenuTreeCondition: [],
			
			role_manager_querySysMenuConditionTreeProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.name;
	            }
			},
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
			party_manager_isYBOrZS: "",
			partyUser_manager_updatePartyUserForm: {
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
			selectJurisdictionTabs: "sys_manager_tab_pane",	//默认选中角色管理
			
			role_manager_pager: {	/*初始化角色管理分页信息*/
		    	pageNum: 1,		/* 当前页 */
		    	pageSize: 12,	/* 页面大小 */
		    	total: 0,
		    	list: []
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
		    
		    role_manager_insertSysRoleDialog: false,
		    role_manager_updateSysRoleDialog: false,
		    role_manager_updateSysRoleMenuDialog: false,
		    baseUser_manager_updateBaseUserDialog: false,
		    baseUser_manager_insertBaseUserDialog: false,
		    party_manager_updatePartyUserDialog: false,
		    baseUser_manager_insertOrgRelationDialog: false,
		    
		    role_manager_insertSysRoleForm: {
		    	roleName: "",
		    	remark: ""
		    },
		    role_manager_updateSysRoleForm: {
		    	roleId: "",
		    	roleName: "",
		    	remark: "",
		    	createTime: ""
		    },
		    
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
			},
		    
		    role_manager_insertSysRoleRules: {
		    	roleName: [
		    		{ required: true, message: '请输入角色名!', trigger: 'blur' },
		    		{ pattern: "^[A-Za-z0-9_]+$", message: '仅支持字母、下划线和数字的组合!'}
		    	]
		    },
		    
		    role_manager_updateSysRoleRules: {
		    	roleName: [
		    		{ required: true, message: '请输入角色名!', trigger: 'blur' },
		    		{ pattern: "^[A-Za-z0-9_]+$", message: '仅支持字母、下划线和数字的组合!'}
		    	]
		    },
		    sysUserStatu: "",	/* 用户状态下拉框选定时绑定值初始化 */
		    sysUsercreateTime: "",
	        insertSysUserDialog: false,		/* 新增用户弹窗默认不显示值初始化 */
	        updateSysUserDialog: false,		/* 修改用户信息弹窗默认不显示初始化 */
	        updateSysUserPwdDialog: false,
	        changeSysUserRoleDialog: false,	/* 变更用户角色弹窗默认不显示初始化 */
		    usersStatus: [	/* 用户状态值初始化 */
				{
					value: '0',
			        label: '禁用'
				},{
					value: '1',
			        label: '可用'
				}
			],
		    pager: {	/*初始化分页信息*/
		    	pageNum: 1,		/* 当前页 */
		    	pageSize: 12,	/* 页面大小 */
		    },
		    insertSysUserForm: {	/* 初始化添加系统用户表单字段 */
		    	name: "",
		    	pass: "",
		    	checkPass: "",
		    	mobile: "",
		    	email: ""
		    },
		    insertSysUserRules: {	/* 校验规则 */
		    	name: [
		            { required: true, message: '请输入用户名!', trigger: 'blur' },
		            { pattern: "^[A-Za-z0-9]+$", message: '仅支持字母和数字的组合!'},
		            { 	/* 重复用户名验证 */
		        		validator: function(rule, value, callback){
		        			setTimeout(function() {
		        				var url = "/sys/user/querySysUsers";
		        				var t = {
	        						username: value,
	        						pageNum: 1,
       								pageSize: 1,
		        				}
		        				$.post(url, t, function(data, status){
		        					if (data.code == 200) {
		        						if (data.data == undefined) {
			        						callback();
			        					} else {
			        						callback(new Error('用户名已经存在!'));
			        					}
		        					} else {
		        						alert("添加系统用户功能出错，不能添加系统用户");
		        						appInstince.resetInsertSysUserForm(insertSysUserForm);
		        					}
		        					
		        				})
	        		        }, 1);
		        		}
		        	}
		        ],
		        pass: [
		        	{ required: true, message: '请输入密码!', trigger: 'blur' },
		        	{ min: 6, max: 16, message: '密码不能小于6位大于16位!'},
		        	{ pattern: "^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,16}$", message: '密码必须是数字+密码的组合!'}
		        ],
		        checkPass: [
		        	{ required: true, message: '请再次输入密码!', trigger: 'blur' },
		        	{ 
		        		validator: function(rule, value, callback){
		        			if (value !== appInstince.insertSysUserForm.pass) {
	        		            callback(new Error('两次输入密码不一致!'));
	        		        } else {
	        		            callback();
	        		        }
		        		}
		        	}
		        ],
		        mobile: [
		        	{ required: true, message: '请输入联系号码!', trigger: 'blur' },
		        	{ pattern: "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message: '号码输入不正确!'}
		        ],
		        email: [
		        	{ required: true, message: '请输入邮箱!', trigger: 'blur' },
		            { type: "email", message: '请输入正确的邮箱!'},
		        ]
		    },
		    updateSysUserForm: {	/* 初始化添加系统用户表单字段 */
		    	userId: "",
		    	username: "",
		    	password: "",
		    	email: "",
		    	mobile: "",
		    	status: "",
		    	createTime: ""
		    },
		    updateSysUserRules: {
		    	mobile: [
		        	{ required: true, message: '请输入联系号码!', trigger: 'blur' },
		        	{ pattern: "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message: '号码输入不正确!'}
		        ],
		        email: [
		        	{ required: true, message: '请输入邮箱!', trigger: 'blur' },
		            { type: "email", message: '请输入正确的邮箱!'},
		        ]
		    },
		    updateSysUserPwdForm: {
		    	pass: "",
		    	checkPass: "",
		    },
		    updateSysUserPwdRules: {
		    	pass: [
		        	{ required: true, message: '请输入密码!', trigger: 'blur' },
		        	{ min: 6, max: 16, message: '密码不能小于6位大于16位!'},
		        	{ pattern: "^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,16}$", message: '密码必须是数字+密码的组合!'}
		        ],
		        checkPass: [
		        	{ required: true, message: '请再次输入密码!', trigger: 'blur' },
		        	{ 
		        		validator: function(rule, value, callback){
		        			if (value !== appInstince.updateSysUserPwdForm.pass) {
	        		            callback(new Error('两次输入密码不一致!'));
	        		        } else {
	        		            callback();
	        		        }
		        		}
		        	}
		        ]
		    },
		    roleList: [],
		    sysUserRoles: [],
		    changeSysUserRole: {
		    	userId: ""
		    }
		},
		created:function () {
			this.role_manager_querySysRoles();	//页面加载完成就开始查询
			this.queryUsers(1, 12); //这里定义这个方法，vue实例之后运行到这里就调用这个函数
			this.baseUser_manager_queryBaseUsers();
        },
		methods: {
			partyUser_manager_deletePartyUser () {
				var obj = this;
				var url = "/party/user/deletePartyUser";
				var t = {
					uid: obj.partyUser_manager_updatePartyUserForm.uid,
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('成功',data.msg,'success')
						if (data.code == 200) {	
							url = "/base/user/updateBaseUser";
	        				t = {
	        					uid: obj.partyUser_manager_updatePartyUserForm.uid,
	        					isParty: 0
	        				}
	        				$.post(url, t, function(data2, status2){
	        					if (data2.code == 200) {
	        						obj.party_manager_updatePartyUserDialog = false;
	        						obj.baseUser_manager_queryBaseUsers();
	        					}
	        				})
						} 
					}
					
				})
			},
			partyUser_manager_updatePartyUser () {
				var obj = this;
				var url = "/party/user/updatePartyUser";
				var _joinDate = null;
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
			party_manager_changeIsYBOrZS(click) {
				var obj = this;
				obj.party_manager_isYBOrZS = obj.partyUser_manager_updatePartyUserForm.reserveMember == "0" ? true : false;	/* 不是预备党员点击设为党员 */
				if(click == "click") {
					obj.partyUser_manager_updatePartyUserForm.reserveMember = obj.party_manager_isYBOrZS ? "1" : "0";	//只有当点击转正时才变更是否为预备党员信息
				}
				if (obj.partyUser_manager_updatePartyUserForm.reserveMember == "1") {
					obj.partyUser_manager_updatePartyUserForm.partyStaff = "0";
					obj.partyUser_manager_updatePartyUserForm.partRepresentative = "0";
					obj.partyUser_manager_updatePartyUserForm.volunteer = "0";
					obj.partyUser_manager_updatePartyUserForm.difficultMember = "0";
					obj.partyUser_manager_updatePartyUserForm.advancedMember = "0";
				} 
			},
			role_manager_initPager(){
				var obj = this;
				obj.role_manager_pager.pageNum = 1;
				obj.role_manager_pager.pageSize = 12;
				obj.role_manager_pager.total = 0;
				obj.role_manager_pager.list = new Array();
			},
			baseUser_manager_initPager(){
				var obj = this;
				obj.baseUser_manager_pager.pageNum = 1;
				obj.baseUser_manager_pager.pageSize = 12;
				obj.baseUser_manager_pager.total = 0;
				obj.baseUser_manager_pager.list = new Array();
			},
			role_manager_querySysRoles() {
				var obj = this;
				var url = "/sys/role/querySysRoles";
				var t = {
					pageNum: obj.role_manager_pager.pageNum,
					pageSize: obj.role_manager_pager.pageSize
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('成功',data.msg,'success')
						if (data.data == undefined) {	
							obj.role_manager_initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.role_manager_pager = data.data;
						}
					}
					
				})
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
						toast('成功',data.msg,'success')
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
		    partyUser_manager_openUpdatePartyUserDialog(row) {
		    	var obj = this;
		    	var url = "/party/user/queryPartyUsersNotPage";
				var t = {
					uid: row.uid
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.partyUser_manager_updatePartyUserForm.uid = data.data[0].uid;
						obj.partyUser_manager_updatePartyUserForm.joinDate = data.data[0].joinDate;
						obj.partyUser_manager_updatePartyUserForm.partyStaff = data.data[0].partyStaff == 0 ? "0" : "1";
						obj.partyUser_manager_updatePartyUserForm.partRepresentative = data.data[0].partRepresentative == 0 ? "0" : "1";
						obj.partyUser_manager_updatePartyUserForm.volunteer = data.data[0].volunteer == 0 ? "0" : "1";
						obj.partyUser_manager_updatePartyUserForm.difficultMember = data.data[0].difficultMember == 0 ? "0" : "1";
						obj.partyUser_manager_updatePartyUserForm.advancedMember = data.data[0].advancedMember == 0 ? "0" : "1";
						obj.partyUser_manager_updatePartyUserForm.reserveMember = data.data[0].reserveMembe == 0 ? "0" : "1";
						obj.partyUser_manager_updatePartyUserForm.reserveApprovalDate = data.data[0].reserveApprovalDate;
						obj.before_reserveMember = obj.partyUser_manager_updatePartyUserForm.reserveMember;
						obj.party_manager_changeIsYBOrZS(null);
		    			obj.party_manager_updatePartyUserDialog = true;
					}
				})
		    },
		    baseUser_manager_insertBaseUser(baseUser_manager_insertBaseUserForm) {
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
		    baseUser_manager_baseUsertoPartyUser(row) {
		    	var obj = this;
		    	var url = "/party/user/insertPartyUser";
				var t = {
					uid: row.uid,
					reserveMember: 1,
					reserveApprovalDate: new Date()
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						url = "/base/user/updateBaseUser";
        				t = {
        					uid: row.uid,
        					isParty: 1
        				}
        				$.post(url, t, function(data2, status2){
        					if (data2.code == 200) {
        						toast('修改成功',"已成为预备党员",'success')
        						obj.baseUser_manager_queryBaseUsers();
        					}
        				})
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
			role_manager_pagerCurrentChange () {
				var obj = this;
				obj.role_manager_querySysRoles();
			},
			role_manager_showInsertSysRoleDialog () {
				var obj = this;
				obj.role_manager_insertSysRoleDialog = true;
			},
			role_manager_resetinsertSysRoleForm(role_manager_insertSysRoleForm) {
				var obj = this;
				obj.$refs[role_manager_insertSysRoleForm].resetFields();
			},
			role_manager_insertSysRole(role_manager_insertSysRoleForm) {
				var obj = this;
				this.$refs[role_manager_insertSysRoleForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/sys/role/insertSysRole";
		            	var t = {
	    					roleName: obj.role_manager_insertSysRoleForm.roleName,
	    					remark: obj.role_manager_insertSysRoleForm.remark
	    				}
	    				$.post(url, t, function(data, status){
	    					if (data.code == 200) {
	    						toast('添加成功',data.msg,'success')
        						obj.role_manager_resetinsertSysRoleForm("role_manager_insertSysRoleForm");
        						obj.role_manager_insertSysRoleDialog = false;
        						obj.role_manager_querySysRoles(obj.role_manager_pager.pageNum, obj.role_manager_pager.pageSize);
	    					}
	    					
	    				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
			},
			role_manager_deleteSysRole(row) {
				var obj = this;
				var url = "/sys/role/deleteSysRole";
				var t = {
					roleId: row.roleId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('删除成功',data.msg,'success')
						obj.role_manager_querySysRoles(obj.role_manager_pager.pageNum, obj.role_manager_pager.pageSize);
					}
				})
			},
			role_manager_openUpdateSysRoleDialog(row) {
				var obj = this;
				obj.role_manager_updateSysRoleDialog = true;
				obj.role_manager_updateSysRoleForm.roleId = row.roleId;
				obj.role_manager_updateSysRoleForm.roleName = row.roleName;
				obj.role_manager_updateSysRoleForm.remark = row.remark;
				obj.role_manager_updateSysRoleForm.createTime = row.createTime;
			},
			role_manager_updateSysRole(role_manager_updateSysRoleForm) {
				var obj = this;
				this.$refs[role_manager_updateSysRoleForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/sys/role/updateSysRole";
		            	var t = {
		            			roleId: obj.role_manager_updateSysRoleForm.roleId,
		    					roleName: obj.role_manager_updateSysRoleForm.roleName,
		    					remark: obj.role_manager_updateSysRoleForm.remark
		    				}
		    				$.post(url, t, function(data, status){
		    					if (data.code == 200) {
		    						toast('删除成功',data.msg,'success')
	        						obj.role_manager_updateSysRoleDialog = false;
	        						obj.role_manager_querySysRoles(obj.role_manager_pager.pageNum, obj.role_manager_pager.pageSize);
		    					}
		    					
		    				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
			},
			role_manager_openChangeSysRoleMenuDialog(row) {
				var obj = this;
				
				var url = "/sys/menu/querySysMenusNotPage";/* 插入组织成员时组织信息查询 */
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						toast('成功',datas.msg,'success')
						obj.role_manager_querySysMenuTreeCondition = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									menuId: -1,
									name: "所有权限（）"
								},
								children: datas.data
							}
						];
					}
					
				})
				
				obj.role_manager_updateSysRoleMenuDialog = true;
			},
			initPager(){
				this.pager.pageNum = 1;
				this.pager.pageSize = 12;
				this.pager.total = 0;
				this.pager.list = new Array();
			},
			
			queryUsers(pageNum, pageSize) {		/* 查询系统用户信息 */
				var obj = this;
				var url = "/sys/user/querySysUsers";
				var t = {
					status: this.sysUserStatu,
					pageNum: pageNum == null ? 1 : pageNum,
					pageSize: pageSize == null ? 12 : pageSize,
				}
				if (this.sysUsercreateTime != null) {
					t.afterTime = obj.sysUsercreateTime[0];
					t.beforeTime = obj.sysUsercreateTime[1];
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('成功',data.msg,'success')
						if (data.data == undefined) {	/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
							obj.initPager();
						} else {
							obj.pager = data.data;
						}
					}
					
				})
			},
			
			
			insertSysUser(insertSysUserForm) {
		        this.$refs[insertSysUserForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/sys/user/insertSysUser";
        				var t = {
       						username: appInstince.insertSysUserForm.name,
       						password: appInstince.insertSysUserForm.pass,
       						email: appInstince.insertSysUserForm.email,
       						mobile: appInstince.insertSysUserForm.mobile
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('添加成功',data.msg,'success')
        						appInstince.resetInsertSysUserForm("insertSysUserForm");
        						appInstince.insertSysUserDialog = false;
        						appInstince.queryUsers(appInstince.pager.pageNum, appInstince.pager.pageSize);
        					}
        				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
		    },
		    
		    
		    resetInsertSysUserForm(insertSysUserForm) {
		        this.$refs[insertSysUserForm].resetFields();
		    },
		    
		    updateSysUser(updateSysUserForm) {
		        this.$refs[updateSysUserForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/sys/user/updateSysUser";
		            	if (appInstince.updateSysUserForm.status == "禁用") {
		            		appInstince.updateSysUserForm.status = 0;
		            	} else if (appInstince.updateSysUserForm.status == "可用") {
		            		appInstince.updateSysUserForm.status = 1;
		            	}
        				var t = {
        					userId: appInstince.updateSysUserForm.userId,
       						email: appInstince.updateSysUserForm.email,
       						mobile: appInstince.updateSysUserForm.mobile,
       						status: appInstince.updateSysUserForm.status
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('修改成功',data.msg,'success')
        						appInstince.updateSysUserDialog = false;
        						appInstince.queryUsers(appInstince.pager.pageNum, appInstince.pager.pageSize);
        					}
        				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
		    },
		    
		    resetUpdateSysUserForm(updateSysUserForm) {
		        this.$refs[updateSysUserForm].resetFields();
		    },
		    
		    updateSysUserPwd(updateSysUserPwdForm) {
		    	this.$refs[updateSysUserPwdForm].validate( function(valid) {
		            if (valid) {
		            	var url = "/sys/user/updateSysUserPwd";
		            	var t = {
        					userId: appInstince.updateSysUserForm.userId,
        					password: appInstince.updateSysUserPwdForm.pass
        				}
		            	$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('密码修改成功',data.msg,'success')
        						appInstince.updateSysUserPwdDialog = false;
        						appInstince.queryUsers(appInstince.pager.pageNum, appInstince.pager.pageSize);
        					}
        				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
		    },
		    
		    resetupdateSysUserPwdForm(updateSysUserPwdForm) {
		    	this.$refs[updateSysUserPwdForm].resetFields();
		    },
		    
		    
		    openUpdateSysUserDialog(row) {
		    	appInstince.updateSysUserDialog = true;
		    	appInstince.updateSysUserForm.userId = row.userId;
		    	appInstince.updateSysUserForm.username = row.username;
		    	appInstince.updateSysUserForm.password = row.password;
		    	appInstince.updateSysUserForm.email = row.email;
		    	appInstince.updateSysUserForm.mobile = row.mobile;
		    	appInstince.updateSysUserForm.status = row.status == 0 ? "0" : "1";
		    	appInstince.updateSysUserForm.createTime = row.createTime;
		    },
		    
		    openChangeSysUserRoleDialog(row) {
		    	appInstince.changeSysUserRole.userId = row.userId;
		    	
		    	var url = "/sys/role/querySysRolesNotPage";
				$.post(url, function(data, status){
					if (data.code == 200) {
						appInstince.changeSysUserRoleDialog = true;
						appInstince.roleList = data.data;
					}
				})
				
				var url = "/sys/userRole/querySysUserRolesNotPage";
				var t = {
					userId: row.userId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {
							appInstince.changeSysUserRoleDialog = true;
							for(var sysUser of data.data) {
								appInstince.sysUserRoles.push(sysUser.roleId)
							}
						}
					}
				})
		    },
		    
		    
		    changeSysUserRoles() {
		    	var url = "/sys/userRole/insertSysUserRole";
		    	var roles = "";
		    	if (appInstince.sysUserRoles.length != 0) {
		    		for(var i of appInstince.sysUserRoles) {
		    			roles += i + ","
		    		}
		    	} else {
		    		roles = null; 
		    	}
		    	var t = {
		    		userId: appInstince.changeSysUserRole.userId,
		    		roles: roles
		    	}
		    	$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('角色变更信息',data.msg,'success')
					}
				})
		    },
		    
		    resetChangeSysUserRoleForm() {
		    	appInstince.roleList = [];
		    	appInstince.sysUserRoles = [];
		    },
			
			querySysUsersByStatus() {	/* 根据系统用户状态查询信息 */
				this.queryUsers(this.pager.pageNum, this.pager.pageSize);
			},
			
			querySysUsersBycreateTime() {	/* 根据创建日期查询信息 */
				this.queryUsers(this.pager.pageNum, this.pager.pageSize);
			},
			
			
		    pagerCurrentChange(pageNum, pageSize) {		/* 页码改变时 */
				this.queryUsers(pageNum, pageSize);
		    },
			
			deleteSysUser(row){	/* 删除用户 */
		    	var url = "/sys/user/deleteSysUser";
				var t = {
					userId: row.userId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('删除成功',data.msg,'success')
						appInstince.queryUsers(appInstince.pager.pageNum, appInstince.pager.pageSize);
					}
				})
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
			baseUser_manager_openOrgRelationDialog(row) {
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
						toast('成功',datas.msg,'success')
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
			baseUser_manager_queryOrgDutyForOrgInfoClickTree(data) {	/* 根据组织查询此组织拥有的全部职责 */
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
						toast('成功',datas.msg,'success')
						obj.baseUser_manager_queryOrgDutyForOrgInfoClickTreeCondition = datas.data;
					}
					
				})
			},
			baseUser_manager_saveRelationDutyId(data, checked, indeterminate) {
				var obj = this;
				if (checked == true) {
					obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds.push(data.data);
				} else {
					var ordis = obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds;
					for (var i = 0; i < ordis.length; i++) {
						var ordi = ordis[i];
						if (data.data.orgDutyId == ordi.orgDutyId) {
							ordis.splice(i,1);	/* 删除没选中的 */
						}
					}
					obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds = ordis;
					for ( var ordi in ordis) {	/* 遍历后才可以使用，没选中的会被替换成undefined,遍历后删除 */
						
					}
				}
			},
			baseUser_manager_insertOrgRlts(baseUser_manager_insertOrgRelationForm){
				var obj = this;					
				var url = "/org/relation/insertOrgRelation";
				if (obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds.length == 0) {
					toast('错误',"请选择该用户在此组织担任的职责",'error');
					return;
				}
				var orgDutyIds = [];
				for (var i = 0; i < obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds.length; i++) {
					orgDutyIds[i] = obj.baseUser_manager_insertOrgRelationForm.orgRltDutyIds[i].orgDutyId;
				}
				var t = {
					orgRltUserId: obj.baseUser_manager_insertOrgRelationForm.orgRltUserId,
					orgRltInfoId: obj.baseUser_manager_insertOrgRelationForm.orgRltInfoId,
					orgRltDutys: orgDutyIds
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						toast('成功',datas.msg,'success')
						obj.baseUser_manager_insertOrgRelationDialog = false;
					}
					
				})	
				
			}
		}
	});
</script>
</html>