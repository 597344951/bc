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
<title>角色权限管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<style type="text/css">
	body {
		
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
				<el-row class="toolbar" :gutter="20">
			  		<shiro:hasPermission name="sys:role:insert">  
				 	    <el-button class="margin-left-10" size="small" type="primary" icon="el-icon-circle-plus-outline" @click="role_manager_showInsertSysRoleDialog">新建角色</el-button>
				  	</shiro:hasPermission>
				</el-row>
			</el-header>
			<el-main>
				<template>
			  		<el-table size="mini" stripe :data="role_manager_pager.list" style="width: 100%" max-height="550">
					    <el-table-column fixed prop="roleId" label="角色ID" width="100">
					    </el-table-column>
					    <el-table-column prop="roleName" label="角色名（英）" width="200">
					    </el-table-column>
					    <el-table-column prop="remark" label="角色名（中）" width="200">
					    </el-table-column>
					    <el-table-column prop="createTime" label="创建时间" width="300">
					    </el-table-column>
					    <el-table-column label="操作" width=180>
						    <template slot-scope="scope">
						      <shiro:hasPermission name="sys:role:delete">  
							      <el-button v-if="signInAccountType != 'party_role'" @click="role_manager_deleteSysRole(scope.row)" type="text" size="small">删除</el-button>
						      </shiro:hasPermission>
						      <shiro:hasPermission name="sys:role:update">  
							      <el-button @click="role_manager_openUpdateSysRoleDialog(scope.row)" type="text" size="small">修改信息</el-button>
						      </shiro:hasPermission>
						      <!-- 不仅有权限还的是平台或组织管理员才有权限修改 -->
						      <shiro:hasPermission name="sys:role:update">  
							      <el-button v-if="signInAccountType != 'party_role'" @click="role_manager_openChangeSysRoleMenuDialog(scope.row)" type="text" size="small">权限变更</el-button>
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
		
		
		
		<el-dialog @close="role_manager_resetinsertSysRoleForm('role_manager_insertSysRoleForm')" title="新建角色" :visible.sync="role_manager_insertSysRoleDialog">
			<el-form size="mini" label-position="left" :model="role_manager_insertSysRoleForm" status-icon :rules="role_manager_insertSysRoleRules" 
				ref="role_manager_insertSysRoleForm" label-width="100px">
				<el-form-item label="角色名>英" prop="roleName">
				    <el-input v-model="role_manager_insertSysRoleForm.roleName" placeholder="仅支持字母和数字组合成的用户名填写"></el-input>
				</el-form-item>
				<el-form-item label="角色名>中" prop="remark">
				    <el-input v-model="role_manager_insertSysRoleForm.remark"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="role_manager_insertSysRole('role_manager_insertSysRoleForm')">添加角色</el-button>
				    <el-button @click="role_manager_resetinsertSysRoleForm('role_manager_insertSysRoleForm')">重置</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog title="修改角色信息" :visible.sync="role_manager_updateSysRoleDialog">
			<el-form size="mini" label-position="left" :model="role_manager_updateSysRoleForm" status-icon :rules="role_manager_updateSysRoleRules" 
				ref="role_manager_updateSysRoleForm" label-width="100px">
				<el-form-item label="角色ID" prop="roleId">
				    <el-input :disabled="true" v-model="role_manager_updateSysRoleForm.roleId"></el-input>
				</el-form-item>
				<el-form-item label="角色名>英" prop="roleName">
				    <el-input v-model="role_manager_updateSysRoleForm.roleName" placeholder="仅支持字母和数字组合成的用户名填写"></el-input>
				</el-form-item>
				<el-form-item label="角色名>中" prop="remark">
				    <el-input v-model="role_manager_updateSysRoleForm.remark"></el-input>
				</el-form-item>
				<el-form-item label="创建时间" prop="createTime">
				    <el-input :disabled="true" v-model="role_manager_updateSysRoleForm.createTime"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="role_manager_updateSysRole('role_manager_updateSysRoleForm')">修改信息</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog @close="role_manager_resetUpdateSysRoleMenuDialog" title="角色权限变更" :visible.sync="role_manager_updateSysRoleMenuDialog">
	    	<div style="margin-bottom: 10px;">
				<el-tree ref="role_manager_updateSysUserRoleTree" 
					node-key="id" 
					:default-expanded-keys=role_manager_menuTreeDefaultExpandedKeys 
					show-checkbox 
					:expand-on-click-node="false" 
					:highlight-current="true" 
					:data="role_manager_querySysMenuTreeCondition" 
			    	:props="role_manager_querySysMenuConditionTreeProps" 
			    	:check-strictly="true">
			  	</el-tree>
		  	</div>
		  	<el-row style="margin-bottom: 20px">
			    <el-button size="small" type="primary" @click="role_manager_updateSysRoleMenu">变更权限</el-button>
			</el-row>
	    </el-dialog>
	</div>
</body>

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			role_manager_querySysMenuTreeCondition: [],	/* 给角色分配权限时查询所有菜单及权限生成树 */
			role_manager_querySysMenuConditionTreeProps: {	/* 菜单权限树显示自定义 */
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.name;
	            }
			},
			role_manager_pager: {	/*初始化角色管理分页信息*/
		    	pageNum: 1,		/* 当前页 */
		    	pageSize: 12,	/* 页面大小 */
		    	total: 0,
		    	list: []
		    },
		    role_manager_menuTreeDefaultExpandedKeys:[	/* 默认展开的节点，默认展开顶级父节点 */
		    	-1
		    ],
		    //role_manager_menuTreeDefaultCheckedKeys: [],	/* 默认选中的节点 */
		    role_manager_insertSysRoleDialog: false,	/* 添加角色弹窗 */
		    role_manager_updateSysRoleDialog: false,	/* 修改角色弹窗 */
		    role_manager_updateSysRoleMenuDialog: false,/* 角色权限变更弹窗 */
		    role_manager_insertSysRoleForm: {			/* 添加角色相关信息保存 */
		    	roleName: "",
		    	remark: ""
		    },
		    role_manager_updateSysRoleForm: {	/* 跟新角色相关信息保存 */
		    	roleId: "",
		    	roleName: "",
		    	remark: "",
		    	createTime: ""
		    },
		    role_manager_updateSysRoleMenuTree: {	/* 跟新角色相关信息保存 */
		    	roleId: ""
		    },
		    role_manager_insertSysRoleRules: {	/* 新增角色验证 */
		    	roleName: [
		    		{ required: true, message: '请输入角色名!', trigger: 'blur' },
		    		{ pattern: "^[A-Za-z0-9_]+$", message: '仅支持字母、下划线和数字的组合!'}
		    	],
		    	remark: [
		    		{ required: true, message: '请输入角色名!', trigger: 'blur' }
		    	]
		    },
		    
		    role_manager_updateSysRoleRules: {	/* 修改角色验证 */
		    	roleName: [
		    		{ required: true, message: '请输入角色名!', trigger: 'blur' },
		    		{ pattern: "^[A-Za-z0-9_]+$", message: '仅支持字母、下划线和数字的组合!'}
		    	],
		    	remark: [
		    		{ required: true, message: '请输入角色名!', trigger: 'blur' }
		    	]
		    },
		    signInAccountType: null
		},
		created:function () {
			this.role_manager_querySysRoles();	//页面加载完成就开始查询
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
						}
					}

				})
			},
			role_manager_initPager(){	/* 初始化页面 */
				var obj = this;
				obj.role_manager_pager.pageNum = 1;
				obj.role_manager_pager.pageSize = 12;
				obj.role_manager_pager.total = 0;
				obj.role_manager_pager.list = new Array();
			},
			role_manager_querySysRoles() {	/* 查询所有角色 */
				var obj = this;
				var url = "/sys/role/querySysRoles";
				var t = {
					pageNum: obj.role_manager_pager.pageNum,
					pageSize: obj.role_manager_pager.pageSize,
					isShow: 1
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.role_manager_initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.role_manager_pager = data.data;
						}
					}
					
				})
			},
			role_manager_pagerCurrentChange () {	/* 分页查询：页面信息变更时 */
				var obj = this;
				obj.role_manager_querySysRoles();
			},
			role_manager_showInsertSysRoleDialog () {	/* 显示添加角色弹窗 */
				var obj = this;
				obj.role_manager_insertSysRoleDialog = true;
			},
			role_manager_resetinsertSysRoleForm(role_manager_insertSysRoleForm) {	/* 添加角色弹窗重置 */
				var obj = this;
				obj.$refs[role_manager_insertSysRoleForm].resetFields();
			},
			role_manager_insertSysRole(role_manager_insertSysRoleForm) {	/* 开始添加角色 */
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
        						obj.role_manager_resetinsertSysRoleForm("role_manager_insertSysRoleForm");	/* 重置窗口 */
        						obj.role_manager_insertSysRoleDialog = false;	/* 关闭窗口 */
        						obj.role_manager_querySysRoles();
	    					}
	    					
	    				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
			},
			role_manager_deleteSysRole(row) {	/* 删除角色 */
				var obj = this;
				var url = "/sys/role/deleteSysRole";
				var t = {
					roleId: row.roleId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('删除成功',data.msg,'success')
						obj.role_manager_querySysRoles();
					}
				})
			},
			role_manager_openUpdateSysRoleDialog(row) {	/* 打开修改角色窗口并初始化角色信息到窗口 */
				var obj = this;
				obj.role_manager_updateSysRoleDialog = true;
				obj.role_manager_updateSysRoleForm.roleId = row.roleId;
				obj.role_manager_updateSysRoleForm.roleName = row.roleName;
				obj.role_manager_updateSysRoleForm.remark = row.remark;
				obj.role_manager_updateSysRoleForm.createTime = row.createTime;
			},
			role_manager_updateSysRole(role_manager_updateSysRoleForm) {	/* 开始更新角色 */
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
	        						obj.role_manager_querySysRoles();
		    					}
		    					
		    				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
			},
			role_manager_openChangeSysRoleMenuDialog(row) {	/* 打开角色权限变更窗口时查询此角色拥有的权限 */
				var obj = this;
				
				var url = "/sys/menu/querySysMenusNotPage";	/* 查询全部权限 */
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.role_manager_querySysMenuTreeCondition = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									menuId: -1,
									name: "所有权限（选中设置全部权限）"
								},
								children: datas.data
							}
						];
						obj.forrole_manager_querySysMenuTreeConditionToAddId(obj.role_manager_querySysMenuTreeCondition);
					}
					
				})
				
				var url = "/sys/roleMenu/querySysRoleMenusNotPage";	/* 查询当前角色拥有的权限 */
				var t = {
					roleId: row.roleId
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						if (datas.data != undefined) {
							var treeCheckedKeys = new Array();
							for (var i = 0; i < datas.data.length; i++) {
								obj.role_manager_menuTreeDefaultExpandedKeys.push(datas.data[i].menuId),
								treeCheckedKeys.push(datas.data[i].menuId)
							}
							obj.$refs["role_manager_updateSysUserRoleTree"].setCheckedKeys(treeCheckedKeys,false);	/* 通过函数设置选中的节点 */
						}
					}
					
				})
				obj.role_manager_updateSysRoleMenuTree.roleId = row.roleId;	/* 保存id，用于更新角色权限信息 */
				obj.role_manager_updateSysRoleMenuDialog = true;
			},
			forrole_manager_querySysMenuTreeConditionToAddId(menuTrees){	/* 向树里添加id属性，方便设置node-key */
				var obj = this;
				if(menuTrees != null) {
					for (var i = 0; i < menuTrees.length; i++) {
						var menuTree = menuTrees[i];
						menuTree.id = menuTree.data.menuId;
						obj.forrole_manager_querySysMenuTreeConditionToAddId(menuTree.children);
					}
				}
			},
			role_manager_resetUpdateSysRoleMenuDialog() {	/* 修改角色权限窗口关闭时清空相关的信息 */
				var obj = this;
				obj.role_manager_querySysMenuTreeCondition = [];
				obj.role_manager_menuTreeDefaultExpandedKeys = [
			    	-1
			    ];
			    //obj.role_manager_menuTreeDefaultCheckedKeys = []
				obj.$refs["role_manager_updateSysUserRoleTree"].setCheckedKeys([],false);
				obj.role_manager_updateSysRoleMenuTree.roleId = "";
			},
			role_manager_updateSysRoleMenu() {	/* 开始变更角色权限 */
				var obj = this;
				var checkedKeys = [];
				checkedKeys = obj.$refs["role_manager_updateSysUserRoleTree"].getCheckedKeys(false);
				if (checkedKeys.length == 0) {
					toast('错误',"请设置角色的权限",'error');
					return;
				}
				
				for (var i = 0; i < checkedKeys.length; i++) {
					if(checkedKeys[i] == -1) {
						checkedKeys = [-1];
						break;
					}
				}
				
				var url = "/sys/roleMenu/insertSysRoleMenu";	/* 查询当前角色拥有的权限 */
				var t = {
					roleId: obj.role_manager_updateSysRoleMenuTree.roleId,
					menuIds: checkedKeys
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						toast('成功',datas.msg,'success');
						obj.role_manager_updateSysRoleMenuDialog = false;
					}
					
				})
			}
		}
	});
</script>
</html>