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
<title>组织信息管理</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<style type="text/css">
	body {
		margin: 20px;
	}
	#orgInfo_manager_pagesdididi {
		text-align: center;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
		  <el-aside width="300px">
		  	<el-tree :expand-on-click-node="false" :default-expand-all="true" 
		  		:highlight-current="true" :data="orgInfoTree" :props="orgInfoTreeProps" @node-click="orgInfo_manager_queryOrgInfosOfTree">
		  	</el-tree>
		  </el-aside>
		  <el-main>
		  	<el-container>
			  <el-header>
			  	<el-row>
			  		<shiro:hasPermission name="org:info:insert">  
				 	    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="orgInfo_manager_openOrgInfoDialog">添加组织信息</el-button>
				  	</shiro:hasPermission>
				</el-row>
			  </el-header>
			  <el-main>
			  	<template>
			  		<el-table size="mini" :data="orgInfo_manager_orgInfo" style="width: 100%" max-height="600">
					    <el-table-column fixed prop="orgInfoId" label="组织ID" width="100">
					    </el-table-column>
					    <el-table-column prop="orgInfoName" label="组织名" width="120">
					    </el-table-column>
					    <el-table-column prop="orgInfoManagerRegion" label="组织管辖区域" width="200">
					    </el-table-column>
					    <el-table-column prop="orgInfoActiveRegion" label="组织活动区域" width="300">
					    </el-table-column>
					    <el-table-column prop="orgInfoDescribe" label="组织简介" width="300">
					    </el-table-column>
					    <el-table-column prop="orgInfoParentId" label="上级组织ID" width="300">
					    </el-table-column>
					    <el-table-column prop="orgTypeName" label="组织类型" width="300">
					    </el-table-column>
					    <el-table-column fixed="right" label="操作" width=180>
						    <template slot-scope="scope">
						      <shiro:hasPermission name="org:info:delete">  
							      <el-button @click="orgInfo_manager_deleteOrgInfo(scope.row)" type="text" size="small">删除</el-button>
						      </shiro:hasPermission>
						      <shiro:hasPermission name="org:info:update">  
							      <el-button @click="orgInfo_manager_openUpdateOrgInfoDialog(scope.row)" type="text" size="small">修改信息</el-button>
						      </shiro:hasPermission>
						    </template>
					    </el-table-column>
					 </el-table>
			  	</template>
			  </el-main>
			</el-container>
		  </el-main>
		</el-container>
		
		
		<el-dialog @close="orgInfo_manager_resetinsertOrgInfoForm('orgInfo_manager_insertOrgInfoForm')" title="添加组织信息" :visible.sync="orgInfo_manager_insertOrgInfoDialog">
			<el-form size="mini" label-position="left" :model="orgInfo_manager_insertOrgInfoForm" status-icon :rules="orgInfo_manager_insertOrgInfoRules" 
				ref="orgInfo_manager_insertOrgInfoForm" label-width="100px">
				<el-form-item label="组织名" prop="orgInfoName">
				    <el-input v-model="orgInfo_manager_insertOrgInfoForm.orgInfoName" placeholder="组织名"></el-input>
				</el-form-item>
				<el-form-item label="管辖区域" prop="orgInfoManagerRegion">
				    <el-input v-model="orgInfo_manager_insertOrgInfoForm.orgInfoManagerRegion"></el-input>
				</el-form-item>
				<el-form-item label="活动区域" prop="orgInfoActiveRegion">
				    <el-input v-model="orgInfo_manager_insertOrgInfoForm.orgInfoActiveRegion"></el-input>
				</el-form-item>
				<el-form-item label="组织简介" prop="orgInfoDescribe">
				    <el-input v-model="orgInfo_manager_insertOrgInfoForm.orgInfoDescribe"></el-input>
				</el-form-item>
				<el-form-item label="组织层级关系" prop="orgInfoParentId">
				    <el-tree :expand-on-click-node="false" :highlight-current="true" :data="orgInfoTreeOfInsert" :props="orgInfoTreeOfInsertProps" @node-click="orgInfo_manager_setOrgInfoParentId">
				  	</el-tree>
				</el-form-item>
				<el-form-item label="组织类型" prop="orgInfoTypeId">
				    <el-select v-model="orgInfo_manager_insertOrgInfoForm.orgInfoTypeId" placeholder="请选择">
					    <el-option
					      v-for="item in orgInfoTypeOfInsert"
					      :key="item.value"
					      :label="item.orgTypeName"
					      :value="item.orgTypeId">
					    </el-option>
				  </el-select>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="orgInfo_manager_insertOrgInfo('orgInfo_manager_insertOrgInfoForm')">添加组织信息</el-button>
				    <el-button @click="orgInfo_manager_resetinsertOrgInfoForm('orgInfo_manager_insertOrgInfoForm')">重置</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	    
	    
	    <el-dialog title="修改组织信息" :visible.sync="orgInfo_manager_updateOrgInfoDialog">
			<el-form size="mini" label-position="left" :model="orgInfo_manager_updateOrgInfoForm" status-icon :rules="orgInfo_manager_updateOrgInfoRules" 
				ref="orgInfo_manager_updateOrgInfoForm" label-width="100px">
				<el-form-item label="组织名" prop="orgInfoName">
				    <el-input :disabled="true" v-model="orgInfo_manager_updateOrgInfoForm.orgInfoName" placeholder="组织名"></el-input>
				</el-form-item>
				<el-form-item label="管辖区域" prop="orgInfoManagerRegion">
				    <el-input v-model="orgInfo_manager_updateOrgInfoForm.orgInfoManagerRegion"></el-input>
				</el-form-item>
				<el-form-item label="活动区域" prop="orgInfoActiveRegion">
				    <el-input v-model="orgInfo_manager_updateOrgInfoForm.orgInfoActiveRegion"></el-input>
				</el-form-item>
				<el-form-item label="组织简介" prop="orgInfoDescribe">
				    <el-input v-model="orgInfo_manager_updateOrgInfoForm.orgInfoDescribe"></el-input>
				</el-form-item>
				<!-- <el-form-item label="组织层级关系" prop="orgInfoParentId">
				    <el-tree :expand-on-click-node="false" :highlight-current="true" :data="orgInfoTreeOfUpdate" :props="orgInfoTreeOfUpdateProps">
				  	</el-tree>
				</el-form-item> -->
				<el-form-item label="组织类型" prop="orgInfoTypeId">
				    <el-select :disabled="true" v-model="orgInfo_manager_updateOrgInfoForm.orgInfoTypeId" placeholder="请选择">
					    <el-option
					      v-for="item in orgInfoTypeOfUpdate"
					      :key="item.value"
					      :label="item.orgTypeName"
					      :value="item.orgTypeId">
					    </el-option>
				  </el-select>
				</el-form-item>
				<el-form-item>
				    <el-button type="primary" @click="orgInfo_manager_updateOrgInfo">修改组织信息</el-button>
				</el-form-item>
			</el-form>
	    </el-dialog>
	</div>
</body>

<script>
	var appInstince = new Vue({
		el: '#app',
		data: {
			orgInfoTree: [],
			orgInfoTreeOfInsert: [],
			orgInfoTypeOfInsert: [],
			orgInfoTreeOfUpdate: [],
			orgInfoTypeOfUpdate: [],
			orgInfoTreeProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
			orgInfoTreeOfInsertProps: {
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
		    orgInfo_manager_orgInfo: [],
		    orgInfo_manager_insertOrgInfoDialog: false,
		    orgInfo_manager_updateOrgInfoDialog: false,
		    orgInfo_manager_insertOrgInfoForm: {
		    	orgInfoName: "",
		    	orgInfoManagerRegion: "",
		    	orgInfoActiveRegion: "",
		    	orgInfoDescribe: "",
		    	orgInfoParentId: -1,
		    	orgInfoTypeId: "",
		    	orgTypeId: "",
		    	orgTypeName: "",
		    	orgTypeDescribe: ""
		    },
		    orgInfo_manager_insertOrgInfoRules: {
		    	orgInfoName: [
		    		{ required: true, message: '请输入组织名!', trigger: 'blur' }
		    	],
		    	orgInfoTypeId: [
		    		{ required: true, message: '请选择组织类型!', trigger: 'blur' }
		    	]
		    },
		    orgInfo_manager_updateOrgInfoForm: {
		    	orgInfoName: "",
		    	orgInfoManagerRegion: "",
		    	orgInfoActiveRegion: "",
		    	orgInfoDescribe: "",
		    	orgInfoParentId: -1,
		    	orgInfoTypeId: "",
		    	orgTypeId: "",
		    	orgTypeName: "",
		    	orgTypeDescribe: ""
		    },
		    orgInfo_manager_updateOrgInfoRules: {
		    	orgInfoName: [
		    		{ required: true, message: '请输入组织名!', trigger: 'blur' }
		    	],
		    	orgInfoTypeId: [
		    		{ required: true, message: '请选择组织类型!', trigger: 'blur' }
		    	]
		    }
		},
		mounted:function () {
			this.orgInfo_manager_queryOrgInfosToTree();
			this.orgInfo_manager_queryOrgInfos(-1);
        },
        methods: {		
        	orgInfo_manager_openOrgInfoDialog() {
        		var obj = this;
	        	obj.orgInfo_manager_insertOrgInfoDialog = true
	        	var obj = this;
				var url = "/org/info/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.orgInfoTreeOfInsert = [
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
				
				url = "/org/type/queryOrgTypesNotPage";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.orgInfoTypeOfInsert = datas.data;
					}
					
				})
        	},
        	orgInfo_manager_queryOrgInfosToTree() {	/* 查询组织信息层级关系 */
				var obj = this;
				var url = "/org/info/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.orgInfoTree = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									orgInfoId: -1,
									orgInfoName: "所有数据 "
								},
								children: datas.data
							}
						];
					}
					
				})
			},
			orgInfo_manager_queryOrgInfosOfTree(data) {		/* 点击树查询组织信息 */
				var obj = this;
				obj.orgInfo_manager_queryOrgInfos(data.data.orgInfoId);
			},
			orgInfo_manager_setOrgInfoParentId(data) {
				var obj = this;
				obj.orgInfo_manager_insertOrgInfoForm.orgInfoParentId = data.data.orgInfoId;
			},
			orgInfo_manager_queryOrgInfos(orgInfoId) {	/* 查询组织信息 */
        		var obj = this;
				var url = "/org/info/queryOrgInfos";
				var t = {
					orgInfoId: orgInfoId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.orgInfo_manager_orgInfo = data.data;
					}
					
				})
        	},
        	orgInfo_manager_resetinsertOrgInfoForm(orgInfo_manager_insertOrgInfoForm){	//清空所有数据
        		var obj = this;
		    	obj.$refs[orgInfo_manager_insertOrgInfoForm].resetFields();
        	},
        	orgInfo_manager_insertOrgInfo(orgInfo_manager_insertOrgInfoForm) {
        		var obj = this;
        		this.$refs[orgInfo_manager_insertOrgInfoForm].validate( function(valid) {
        			if (valid) {
        				var url = "/org/info/insertOrgInfo";
        				var t = {
        					orgInfoName: obj.orgInfo_manager_insertOrgInfoForm.orgInfoName,
        			    	orgInfoManagerRegion: obj.orgInfo_manager_insertOrgInfoForm.orgInfoManagerRegion,
        			    	orgInfoActiveRegion: obj.orgInfo_manager_insertOrgInfoForm.orgInfoActiveRegion,
        			    	orgInfoDescribe: obj.orgInfo_manager_insertOrgInfoForm.orgInfoDescribe,
        			    	orgInfoParentId: obj.orgInfo_manager_insertOrgInfoForm.orgInfoParentId,
        			    	orgInfoTypeId: obj.orgInfo_manager_insertOrgInfoForm.orgInfoTypeId
        				}
        				$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('添加成功',data.msg,'success');
        						var nextQueryOrgInfosById = obj.orgInfo_manager_insertOrgInfoForm.orgInfoParentId;
        						obj.orgInfo_manager_resetinsertOrgInfoForm("orgInfo_manager_insertOrgInfoForm");
        						obj.orgInfo_manager_insertOrgInfoDialog = false;
        						obj.orgInfo_manager_queryOrgInfosToTree();
        						obj.orgInfo_manager_queryOrgInfos(nextQueryOrgInfosById);
        					}
        					
        				})
        			}
        		})
        	},
        	orgInfo_manager_deleteOrgInfo(row) {
        		var obj = this;
        		var url = "/org/info/deleteOrgInfo";
				var t = {
						orgInfoId: row.orgInfoId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('删除成功',data.msg,'success');
						obj.orgInfo_manager_queryOrgInfosToTree();
						obj.orgInfo_manager_queryOrgInfos(row.orgInfoParentId);
					}
					
				})
        	},
        	orgInfo_manager_openUpdateOrgInfoDialog(row) {
        		var obj = this;
        		obj.orgInfo_manager_updateOrgInfoForm.orgInfoId = row.orgInfoId;
        		obj.orgInfo_manager_updateOrgInfoForm.orgInfoName = row.orgInfoName;
        		obj.orgInfo_manager_updateOrgInfoForm.orgInfoManagerRegion = row.orgInfoManagerRegion;
        		obj.orgInfo_manager_updateOrgInfoForm.orgInfoActiveRegion = row.orgInfoActiveRegion;
        		obj.orgInfo_manager_updateOrgInfoForm.orgInfoDescribe = row.orgInfoDescribe;
		    	obj.orgInfo_manager_updateOrgInfoForm.orgInfoParentId = row.orgInfoParentId;
		    	obj.orgInfo_manager_updateOrgInfoForm.orgInfoTypeId = row.orgInfoTypeId;
		    	
		    	var url = "/org/type/queryOrgTypesNotPage";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.orgInfoTypeOfUpdate = datas.data;
					}
					
				})
        		obj.orgInfo_manager_updateOrgInfoDialog = true;
        	},
        	orgInfo_manager_updateOrgInfo() {
        		var obj = this;
        		var url = "/org/info/updateOrgInfo";
				var t = {
					orgInfoId: obj.orgInfo_manager_updateOrgInfoForm.orgInfoId,
					orgInfoManagerRegion: obj.orgInfo_manager_updateOrgInfoForm.orgInfoManagerRegion,
					orgInfoActiveRegion: obj.orgInfo_manager_updateOrgInfoForm.orgInfoActiveRegion,
					orgInfoDescribe: obj.orgInfo_manager_updateOrgInfoForm.orgInfoDescribe
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						toast('成功',datas.msg,'success')
						obj.orgInfo_manager_queryOrgInfosToTree();
						obj.orgInfo_manager_queryOrgInfos(obj.orgInfo_manager_updateOrgInfoForm.orgInfoId);
						obj.orgInfo_manager_updateOrgInfoDialog = false;
					}
					
				})
        		
        		
        	}
        }
	});
</script>

</html>