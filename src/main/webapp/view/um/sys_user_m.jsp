<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<title>系统用户管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<style>
	.el-row {
		margin-bottom: 10px;
	}
	#pagesdididi {
		text-align: center;
	}
	.el-transfer-panel{
		width:40% !important;
		min-width: 250px !important;
	}
	.el-transfer-panel__header{
		padding-top:8px !important;
	}
</style>
</head>
<body>
	<div id="app" v-cloak>
		<el-container>
		  	<el-header>
		  		<el-row class="toolbar" >
		  			<shiro:hasPermission name="sys:user:insert">  
			 	    	<el-button class="margin-left-10" size="small" type="primary" icon="el-icon-circle-plus-outline" @click="insertSysUserDialog = true">添加用户</el-button>
			  		</shiro:hasPermission>
			  		<shiro:hasPermission name="sys:user:query">  
			  			<!-- 个人用户没有查询的必要 -->
				  		<el-popover 
				  			v-if="signInAccountType != 'party_role'"
				  			class="margin-left-10"
							placement="bottom" 
						  	width="380" 
						  	trigger="click" >
						  	<el-button size="small" type="primary" slot="reference">
						  		<i class="el-icon-search"></i>
						  		搜索用户
						  	</el-button>
						  	<div>
						  		<el-row>
									<el-select 
										size="small" 
										clearable 
										v-model="sysUserStatu" 
										@change="querySysUsersByStatus" 
										placeholder="请选择系统用户状态">
									   	<el-option v-for="item in usersStatus" :key="item.value" :label="item.label" :value="item.value"></el-option>
									</el-select>
								</el-row>
								<el-row>
									<el-date-picker 
										size="small" 
										v-model="sysUsercreateTime" 
										@change="querySysUsersBycreateTime" 
										type="daterange" align="right" 
										unlink-panels range-separator="至"
							      		start-placeholder="开始日期" end-placeholder="结束日期">
							    	</el-date-picker>
						    	</el-row>
						  	</div>
						</el-popover>
					</shiro:hasPermission>
				</el-row>
			</el-header>
			<el-main>
		  	<template>
		  		<el-table :data="pager.list">
				    <el-table-column fixed prop="userId" label="ID" width="50">
				    </el-table-column>
				    <el-table-column prop="username" label="用户名" width="150">
				    </el-table-column>
				    <el-table-column prop="email" label="邮箱" width="150">
				    </el-table-column>
				    <el-table-column prop="mobile" label="手机号" width="100">
				    </el-table-column>
				    <el-table-column label="账户类型" width="80">
					    <template slot-scope="scope">
					        <span>{{ scope.row.userType != 1 ? "一般账户" : "党员账户" }}</span>
					    </template>
				    </el-table-column>
				    <el-table-column label="状态" width="80">
					    <template slot-scope="scope">
					        <span>{{ scope.row.status == 0 ? "禁用" : "可用" }}</span>
					    </template>
					</el-table-column>
					<el-table-column label="角色" >
						<template slot-scope="scope">
							{{scope.row.roleNames.join(' , ')}}
						</template>	
					</el-table-column>
				    <el-table-column prop="createTime" label="创建时间" width="270">
						<template slot-scope="scope">
							{{scope.row.createTime | datetime}}
						</template>
				    </el-table-column>
				    <el-table-column label="操作" width="270">
					    <template slot-scope="scope">
					      	<shiro:hasPermission name="sys:user:delete">  
						      	<el-button @click="deleteSysUser(scope.row)" type="text" size="small">删除</el-button>
					      	</shiro:hasPermission>
					      	<shiro:hasPermission name="sys:user:update">  
						     	<el-button @click="openUpdateSysUserDialog(scope.row)" type="text" size="small">修改信息</el-button>
					      	</shiro:hasPermission>
					      	<shiro:hasPermission name="sys:userRole:update">  
							  	<!--v-if="signInAccountType != 'party_role' && scope.row.userType != 1"-->
						      	<el-button  @click="openChangeSysUserRoleDialog(scope.row)" type="text" size="small">角色变更</el-button>
							</shiro:hasPermission>
							<!-- 合并角色
					      	<shiro:hasPermission name="sys:userRole:update">  
						      	<el-button v-if="signInAccountType == 'plant_admin' && scope.row.userType != 1" type="text" size="small" slot="reference" @click="openSetInnerManageRolesDialog(scope.row)">赋予管理角色</el-button>
							</shiro:hasPermission>
							-->
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
		
		
		
		<el-dialog @close="resetInsertSysUserForm('insertSysUserForm')" title="添加系统用户" :visible.sync="insertSysUserDialog">
			<el-form size="mini" label-position="left" :model="insertSysUserForm" status-icon :rules="insertSysUserRules" ref="insertSysUserForm" label-width="100px">
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
				<el-form-item label="隶属组织" prop="orgInfoId">
				    <el-tree :expand-on-click-node="false" 
				    	:highlight-current="true" 
				    	:data="orgInfoTreeOfJoinOrg" 
				    	:props="orgInfoTreeOfJoinOrgProps" 
				    	@node-click="setSysUserOrgId">
				  	</el-tree>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="insertSysUser('insertSysUserForm')">添加用户</el-button>
				    <el-button @click="resetInsertSysUserForm('insertSysUserForm')">重置</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    <el-dialog title="修改系统用户信息" :visible.sync="updateSysUserDialog">
	    	<el-dialog @close="resetupdateSysUserPwdForm('updateSysUserPwdForm')" width="30%" title="修改用户密码" :visible.sync="updateSysUserPwdDialog" append-to-body>
	    		<el-form size="mini" label-position="left" :model="updateSysUserPwdForm" status-icon :rules="updateSysUserPwdRules" ref="updateSysUserPwdForm" label-width="100px">
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
			<el-form size="mini" label-position="left" :model="updateSysUserForm" status-icon :rules="updateSysUserRules" ref="updateSysUserForm" label-width="100px">
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
				    <el-select :disabled="signInAccountType == 'plant_admin' ? 'true' : 'false'" v-model="updateSysUserForm.status">
				      <el-option label="禁用" value="0"></el-option>
				      <el-option label="可用" value="1"></el-option>
				    </el-select>
				</el-form-item>
				<el-form-item label="创建日期" prop="createTime">
				    <el-input :disabled="true" v-model="updateSysUserForm.createTime"></el-input>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="updateSysUser('updateSysUserForm')">变更信息</el-button>
				</el-form-item>
			</el-form>	  
	    </el-dialog>
	    
	    <el-dialog @close="resetChangeSysUserRoleForm" title="用户角色变更" :visible.sync="changeSysUserRoleDialog">
			<div style="margin-bottom: 10px;">
			  <el-transfer :props="{key: 'roleId', label: 'remark'}" v-model="sysUserRoles" :data="sortedRoleList" :titles="['角色列表', '用户拥有角色']" :button-texts="['移除角色', '添加角色']">
				<span slot-scope="{ option }" :style="{color:option.builtin?'red':''}">
					<template v-if="option.builtin">
						内置 - 
					</template>
					<span >{{ option.remark }}</span>
				</span>
				
			  </el-transfer>
			</div>
			<el-row style="margin-bottom: 20px;">
			    <el-button size="small" type="primary" @click="changeSysUserRoles">变更角色</el-button>
			</el-row>
	    </el-dialog>

	     <el-dialog title="管理内置角色" :visible.sync="innserManageRolesDialog" width="200px">
	     	<el-select size="small" clearable 
					v-model="innerManageRoles.roleId"
					placeholder="所在组织">
				<el-option
					v-for="item in innerManageRoles.roles"
					:key="item.roleId"
					:label="item.remark"
					:value="item.roleId">
				</el-option>
			</el-select>
			<el-button style="margin: 10px 0" size="small" type="primary" @click="updateInnerManageRols">确认</el-button>
	     </el-dialog>
	</div>
</body>

<script type="text/javascript">
	window.onFocus = function () {
		console.log('sys_user_m activated')
		appInstince.queryUsers(appInstince.pager.pageNum, appInstince.pager.pageSize)
	}
	var appInstince = new Vue({
		el: '#app',
		data: {
			orgInfoTreeOfJoinOrg: [],	/*添加党员组织树*/
			orgInfoTreeOfJoinOrgProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
			sysUserStatu: "",	/* 用户状态下拉框选定时绑定值初始化 */
		    sysUsercreateTime: "",
	        insertSysUserDialog: false,		/* 新增用户弹窗默认不显示值初始化 */
	        updateSysUserDialog: false,		/* 修改用户信息弹窗默认不显示初始化 */
	        updateSysUserPwdDialog: false,
	        changeSysUserRoleDialog: false,	/* 变更用户角色弹窗默认不显示初始化 */
	        innserManageRolesDialog: false, 	/*内置角色管理弹窗*/
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
		    	email: "",
		    	orgId: null
		    },
		    insertSysUserRules: {	/* 校验规则 */
		    	name: [
		            { required: true, message: '请输入用户名!', trigger: 'blur' },
		            { min: 3, message: '账户长度需大于3位!'},
		            { pattern: "^[A-Za-z][A-Za-z0-9]+$", message: '字母开头，仅支持字母和数字的组合!'},
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
		        	{ pattern: "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$", message: '号码输入不正确!'}
		        ],
		        email: [
		        	{ required: true, message: '请输入邮箱!', trigger: 'blur' },
		            { type: "email", message: '请输入正确的邮箱!'},
		        ],
		        orgInfoId: [
		        	{ 
		        		validator: function(rule, value, callback){
		        			if (appInstince.insertSysUserForm.orgId != null) {
	        		            callback();
	        		        } else {
	        		            callback(new Error('请选择隶属组织!'));
	        		        }
		        		},
		        		trigger: 'blur'
		        	}
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
		        	{ pattern: "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$", message: '号码输入不正确!'}
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
		    },
		    innerManageRoles: {
		    	roles: [],
		    	userId: null,
		    	roleId: null,
		    	isShow: 0
		    },
		    signInAccountType: null
		},
		computed: {
			sortedRoleList(){
				return this.roleList.sort((a,b) => {
					return b.builtin -  a.builtin
				})
			}
		},
		created:function () {
			this.queryUsers(1, 12); //这里定义这个方法，vue实例之后运行到这里就调用这个函数
			this.getInnerManageRoles();
			this.initOrgInfoTree();
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
			updateInnerManageRols() {
				var obj = this;
				var url = "/sys/userRole/updateInnerManageRols";
				var t = {
					userId: obj.innerManageRoles.userId,
					roleId: obj.innerManageRoles.roleId,
					isShow: obj.innerManageRoles.isShow
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('修改成功成功',data.msg,'success');
						obj.innserManageRolesDialog = false;
						obj.queryUsers(obj.pager.pageNum, obj.pager.pageSize);
					}
				})
			},
			openSetInnerManageRolesDialog(row) {
				var obj = this;
				obj.innerManageRoles.userId = row.userId;

				var url = "/sys/userRole/querySysUserRolesNotPage";
				var t = {
					userId: obj.innerManageRoles.userId,
					isShow: obj.innerManageRoles.isShow
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {
							obj.innerManageRoles.roleId = data.data[0].roleId;
						} else {
							obj.innerManageRoles.roleId = null;
						}
					}
				})

				obj.innserManageRolesDialog = true;
			},
			getInnerManageRoles() {
				var obj = this;
				var url = "/sys/role/querySysRolesNotPage";
		    	var t = {
					isShow: obj.innerManageRoles.isShow
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.innerManageRoles.roles = data.data;
					}
				})
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
       						mobile: appInstince.insertSysUserForm.mobile,
       						orgId: appInstince.insertSysUserForm.orgId
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
        					username: appInstince.updateSysUserForm.username,
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
		    	var t = {
					isShow: 1
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						appInstince.changeSysUserRoleDialog = true;
						appInstince.roleList = data.data;
					}
				})
				
				var url = "/sys/userRole/querySysUserRolesNotPage";
				var t = {
					userId: row.userId,
					isShow: 1
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
						toast('角色变更信息',data.msg,'success');
						appInstince.changeSysUserRoleDialog = false;
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
				this.$confirm('是否删除该用户: '+row.username, '是否删除该用户', {
					type: 'warning'
				}).then(()=>{
					$.post(url, t, function(data, status){
						if (data.code == 200) {
							toast('删除成功',data.msg,'success')
							appInstince.queryUsers(appInstince.pager.pageNum, appInstince.pager.pageSize);
						}
					})
				})
			},
			initOrgInfoTree() {	/*打开加入组织的窗口*/
				var obj = this;

				var url = "/org/ifmt/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.orgInfoTreeOfJoinOrg =  datas.data;
						obj.orgInfoTreeOfJoinOrg.push({
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									orgInfoId: 0,
									orgInfoName: "平台组织"
								},
								children: null
						});
					}
					
				})
			},
			setSysUserOrgId(data) {
				var obj = this;
				obj.insertSysUserForm.orgId = data.data.orgInfoId;
			}
		}
	});
</script>
</html>