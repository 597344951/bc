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
<title>组织成员关系管理</title>
<%@include file="/include/head.jsp"%>
<style type="text/css">
	body {
		margin: 20px;
	}
	#orgRelation_manager_pagesdididi {
		text-align: center;
	}
	#queryOrgRelationStyle>span {
		margin-right: 20px;
	}
</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-aside width="300px">
				<el-tree :expand-on-click-node="false" :default-expand-all="true" 
		  		:highlight-current="true" :data="orgInfoTree" :props="orgInfoTreeProps" @node-click="orgRelation_manager_queryOrgInfosOfTree">
		  	</el-tree>
			</el-aside>
			<el-main>
				<el-container>
					<el-header>
						<el-row id="queryOrgRelationStyle">
							<span>
								用户性别：
								<el-select clearable @change="orgRelation_manager_queryOrgInfosOfBuSex" v-model="orgRelation_manager_queryRelationsCondition.buSex">
							      <el-option label="女" value="0"></el-option>
							      <el-option label="男" value="1"></el-option>
							    </el-select>
							</span>
							<span>
								用户职责：
								<el-select clearable v-model="orgRelation_manager_queryRelationsCondition.orgRltDutyId" @change="orgRelation_manager_queryOrgInfosOfDuty" placeholder="请选择系统用户状态">
							   	   <el-option v-for="item in orgRelation_manager_queryOrgDutysOfQueryRelationsSelect" :key="item.orgDutyName" :label="item.orgDutyName" :value="item.orgDutyId"></el-option>
							    </el-select>
							</span>
						</el-row>
					</el-header>
					<el-main>
						<template>
					  		<el-table size="mini" :data="orgRelation_manager_pager.list" style="width: 100%" max-height="600">
							    <el-table-column fixed prop="orgRltId" label="成员关系ID" width="100">
							    </el-table-column>
							    <el-table-column prop="buName" label="用户名" width="120">
							    </el-table-column>
							    <el-table-column prop="buIdCard" label="身份证号码" width="200">
							    </el-table-column>
							    <el-table-column label="性别" width="120">
									    <template slot-scope="scope">
									        <span>{{ scope.row.buSex == 0 ? "女" : "男" }}</span>
									    </template>
								    </el-table-column>
							    <el-table-column prop="buAddress" label="居住地址" width="300">
							    </el-table-column>
							    <el-table-column prop="buEduLevel" label="教育程度" width="300">
							    </el-table-column>
							    <el-table-column prop="buPhone" label="联系电话" width="300">
							    </el-table-column>
							    <el-table-column prop="buNation" label="籍贯" width="300">
							    </el-table-column>
							    <el-table-column prop="buEmail" label="联系邮箱" width="300">
							    </el-table-column>
							    <el-table-column prop="buQq" label="QQ" width="300">
							    </el-table-column>
							    <el-table-column prop="buWechat" label="微信" width="300">
							    </el-table-column>
							    <el-table-column prop="infoName" label="组织名" width="300">
							    </el-table-column>
							    <el-table-column prop="orgInfoManagerRegion" label="组织管理区域" width="300">
							    </el-table-column>
							    <el-table-column prop="orgInfoActionRegion" label="组织活动区域" width="300">
							    </el-table-column>
							    <el-table-column prop="orgInfoDescribe" label="组织说明" width="300">
							    </el-table-column>
							    <el-table-column prop="orgDutyName" label="组织内职责" width="300">
							    </el-table-column>
							    <el-table-column prop="orgDutyDescribe" label="职责说明" width="300">
							    </el-table-column>
							    <el-table-column prop="orgTypeName" label="职责所在组织" width="300">
							    </el-table-column>
							    <el-table-column fixed="right" label="操作" width=180>
								    <template slot-scope="scope">
								      <shiro:hasPermission name="org:relation:delete">  
									      <el-button @click="orgRelation_manager_deleteOrgRelation(scope.row)" type="text" size="small">删除</el-button>
								      </shiro:hasPermission>
								    </template>
							    </el-table-column>
							 </el-table>
					  	</template>
					</el-main>
					<el-footer>
						<el-pagination id="orgRelation_manager_pagesdididi" 
						  layout="total, prev, pager, next, jumper" 
			      		  @current-change="orgRelation_manager_pagerCurrentChange"
						  :current-page.sync="orgRelation_manager_pager.pageNum"
						  :page-size.sync="orgRelation_manager_pager.pageSize"
						  :total="orgRelation_manager_pager.total">
						</el-pagination>
					</el-footer>
				</el-container>
			</el-main>
		</el-container>
		
		<el-dialog title="添加组织成员" :visible.sync="orgRelation_manager_insertOrgRelationDialog">
		
		
		</el-dialog>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			orgInfoTree: [],	//组织树
			orgRelation_manager_insertOrgRelationDialog: false,
			orgInfoTreeProps: {	//自定义组织树的绑定值
				children: 'children',
	            label: function(_data, node){
	            	return _data.data.orgInfoName;
	            }
			},
			orgRelation_manager_pager: {	/*初始化角色管理分页信息*/
		    	pageNum: 1,		/* 当前页 */
		    	pageSize: 12,	/* 页面大小 */
		    	total: 0,
		    	list: []
		    },
		    orgRelation_manager_queryRelationsCondition: {	/* 保存查询是的条件 */
		    	orgRltInfoId: "",
		    	orgRltDutyId: "",
		    	orgRltUserId: "",
		    	buSex: "",
		    },
		    orgRelation_manager_queryOrgDutysOfQueryRelationsSelect: []
		},
		created:function () {
			this.orgRelation_manager_queryOrgInfosToTree();	//初始化查询组织树
			this.orgRelation_manager_queryOrgRelations(); //查询所有数据
			this.orgRelation_manager_queryOrgDutysOfQueryRelations();	//查询用户关系中存在的组织职责信息
		},
		methods: {
			orgRelation_manager_initPager(){	//初始化页面信息，查询不到数据时充值页面信息以便正确显示
				var obj = this;
				obj.orgRelation_manager_pager.pageNum = 1;
				obj.orgRelation_manager_pager.pageSize = 12;
				obj.orgRelation_manager_pager.total = 0;
				obj.orgRelation_manager_pager.list = new Array();
			},
			orgRelation_manager_queryOrgDutysOfQueryRelations() {
				var obj = this;
				var url = "/org/duty/queryOrgDutysOfQueryRelations";
				var t = {
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.orgRelation_manager_queryOrgDutysOfQueryRelationsSelect = data.data;
					}
					
				})
			},
			orgRelation_manager_queryOrgInfosToTree() {	/* 查询组织信息层级关系树 */
				var obj = this;
				var url = "/org/info/queryOrgInfosToTree";
				var t = {
				}
				$.post(url, t, function(datas, status){
					if (datas.code == 200) {
						obj.orgInfoTree = [
							data = {
								data: {		/* 给根目录设置一个顶级节点，用于查询全部数据 */
									orgInfoName: "所有数据 "
								},
								children: datas.data
							}
						];
					}
					
				})
			},
			orgRelation_manager_queryOrgRelations() {	/* 查询组织成员关系 */
				var obj = this;
				var url = "/org/relation/queryOrgRelations";
				var t = {
					orgRltInfoId: obj.orgRelation_manager_queryRelationsCondition.orgRltInfoId,
					buSex: obj.orgRelation_manager_queryRelationsCondition.buSex,
					orgRltDutyId: obj.orgRelation_manager_queryRelationsCondition.orgRltDutyId,
					pageNum: obj.orgRelation_manager_pager.pageNum,
					pageSize: obj.orgRelation_manager_pager.pageSize
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data == undefined) {	
							obj.orgRelation_manager_initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						} else {
							obj.orgRelation_manager_pager = data.data;
						}
					}
					
				})
			},
			orgRelation_manager_queryOrgInfosOfTree (orgInfo) {	/* 点击左侧导航树时，根据组织查询此组织的成员关系click */
				var obj = this;
				obj.orgRelation_manager_queryRelationsCondition.orgRltInfoId = orgInfo.data.orgInfoId;
				obj.orgRelation_manager_queryOrgRelations();
			},
			orgRelation_manager_pagerCurrentChange() {	/* 分页查询 */
				var obj = this;
				obj.orgRelation_manager_queryOrgRelations();
			},
			orgRelation_manager_queryOrgInfosOfBuSex() {	/* 按用户性别查询 */
				var obj = this;
				obj.orgRelation_manager_queryOrgRelations();
			},
			orgRelation_manager_queryOrgInfosOfDuty() {
				var obj = this;
				obj.orgRelation_manager_queryOrgRelations();
			},
			orgRelation_manager_deleteOrgRelation(row){
				var obj = this;
				var url = "/org/relation/deleteOrgRelation";
				var t = {
					orgRltId: row.orgRltId
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						toast('成功',data.msg,'success')
						obj.orgRelation_manager_queryOrgRelations();
					}
					
				})
			}
		}
	});
</script>
</html>