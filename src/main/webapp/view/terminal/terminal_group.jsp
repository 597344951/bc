<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
<!-- 引入样式 -->
<%@include file="/include/head.jsp"%>

<title>终端基础信息管理</title>
<style type="text/css">
	.collapseIcon,
	.glyphicon-send {
		line-height: 55px;
	}

	.el-transfer-panel {
		width: 40%;
	}
</style>
</head>

<body>
	<div id="app" class="height_full">
		<div class="grid-content bg-purple toolbar">
			<el-button type="primary" size="small" icon="el-icon-plus" @click="addT()">增加</el-button>
			<!--  <el-button type="primary" size="small" icon="el-icon-search" @click="search()">搜索</el-button> -->
			<el-popover placement="right" width="400" trigger="click">
				<el-form ref="form" label-width="80px" size="small">
					<el-form-item label="分组名称">
						<el-input v-model="tg.data.name" style="width: 80%;"></el-input>
					</el-form-item>
					<el-form-item label="终端地址">
						<el-input v-model="tg.data.addr" style="width: 80%;"></el-input>
					</el-form-item>
					<el-form-item label="终端数量">
						<el-input v-model="tg.data.count" style="width: 80%;"></el-input>
					</el-form-item>
					<el-form-item label="修改日期">
						<el-input v-model="tg.data.date" style="width: 80%;"></el-input>
					</el-form-item>
					<el-form-item label="备注">
						<el-input v-model="tg.data.ps" style="width: 80%;"></el-input>
					</el-form-item>
					<el-form-item >
						<el-button type="primary" @click="search()" size="small">确定</el-button>
						<el-button  @click="refresh()" size="small">重置</el-button>
					</el-form-item>
				</el-form>
				<el-button type="primary" size="small" icon="el-icon-search" slot="reference" class="margin-0-10">搜索</el-button>
			</el-popover>
			<el-button type="primary" size="small" icon="el-icon-refresh" @click="refresh()">刷新</el-button>
		</div>
		<!--表格主体-->
		<el-main>
			<template>
				<el-table :data="pager.list">
					<el-table-column prop="name" label="分组名称" ></el-table-column>
					<el-table-column prop="count" label="终端数量" ></el-table-column>
					<el-table-column prop="addr" label="终端地址" ></el-table-column>
					<el-table-column prop="date" label="修改日期" ></el-table-column>
					<el-table-column prop="ps" label="备注" ></el-table-column>
					<el-table-column prop="oid" label="oid" ></el-table-column>
					<el-table-column label="操作" fixed="right" >
						<template slot-scope="scope">
							<el-button size="mini" type="info" @click="updatetg(scope.row)">编辑</el-button>
							<el-button size="mini" type="danger " @click="handleDelete(scope.row.oid) ">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</template>
		</el-main>
		<el-footer>
			<el-pagination id="pagesdididi" layout="total, prev, pager, next, jumper " @current-change="pagerCurrentChange(pager.pageNum,pager.pageSize) "
			 :current-page.sync="pager.pageNum " :page-size.sync="pager.pageSize " :total="pager.total ">
			</el-pagination>
		</el-footer>
		<el-dialog title="" :visible.sync="tg.visible" width="80%">
			<el-form :model="tg" label-width="100px">
				<!-- <el-form-item label="oid" v-show="tg.search">
							<el-input v-model="tg.data.oid" style="width: 40%;"></el-input>
						</el-form-item> -->
				<el-row type="flex">
					<el-col :span="10">
						<el-form-item label="分组名称">
							<el-input v-model="tg.data.name" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="终端地址">
							<el-input v-model="tg.data.addr" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex">
					<el-col :span="10">
						<el-form-item label="终端数量" v-show="tg.search">
							<el-input v-model="tg.data.count" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="修改日期" v-show="tg.search">
							<el-input v-model="tg.data.date" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex">
					<el-col :span="10">
						<el-form-item label="备注">
							<el-input v-model="tg.data.ps" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template>
				<el-transfer :props="{key: 'oid', label: 'name'}" v-model="tbi.result" :data="tbi.data" v-show="tbi.visible" panelwidth="100">
				</el-transfer>
			</template>
			<div slot="footer" class="dialog-footer">
				<el-button v-show="!tg.search" type="primary" @click="tbis()">详情</el-button>
				<el-button @click="cancel(tg)">取 消</el-button>
				<el-button @click="updateorinsert(tg)">确定</el-button>
			</div>
		</el-dialog>
	</div>
</body>
<script>
	var appInstince = new Vue({
		el: '#app',
		data: {


			/* 新增用户弹窗默认不显示值初始化 */
			usersStatus: [ /* 用户状态值初始化 */ {
				value: '0',
				label: '禁用'
			}, {
				value: '1',
				label: '可用'
			}],
			pager: { /*初始化分页信息*/
				pageNum: 1,
				/* 当前页 */
				pageSize: 10,
				/* 页面大小 */
			},
			tg: {
				title: " 测试标题 ",
				visible: false,
				update: false, // 是否是更新
				search: false,
				data: {
					oid: " ",
					name: " ",
					addr: " ",
					count: " ",
					ps: " ",
					date: " ",
				},
			},
			tbi: {
				visible: false,
				result: [],
				data: [],
			},
			tgb: {
				oids: [],
				tg: '',
			},
		},
		created: function () {
			this.queryGroup(1, 12); //这里定义这个方法，vue实例之后运行到这里就调用这个函数
		},
		methods: {

			initPager() {
				this.pager.pageNum = 1;
				this.pager.pageSize = 12;
				this.pager.total = 0;
				this.pager.list = new Array();
			},

			queryGroup(pageNum, pageSize) { /* 查询系统用户信息 */
				var obj = this;
				var url = "/terminal/group/queryInfo/";

				var t = {
					pageNum: pageNum == null ? 1 : pageNum,
					pageSize: pageSize == null ? 12 : pageSize,
				}
				var url2 = t.pageNum + "-" + t.pageSize;
				url += url2;
				ajax_json(url, 'post', t, function (data) {
					// toast('成功', data.msg, 'success')
					if (data.data == undefined) { /* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						obj.initPager();
					} else {
						obj.pager = data.data;
					}
				});

			},
			refresh() {
				this.queryGroup(1, 12);
				this.tg.title = '';
				this.tg.visible = false;
				this.tg.update = false;
				this.tg.search = false;
				this.tg.data.oid = '';
				this.tg.data.name = '';
				this.tg.data.addr = '';
				this.tg.data.count = '';
				this.tg.data.ps = '';
				this.tg.data.date = '';
				this.tbi.visible = false;
				this.tbi.result = [];
				this.tbi.data = [];
				this.tbi.oids = [];
				this.tbi.tg = '';
			},
			refresh2() {
				this.tg.title = '';
				this.tg.visible = false;
				this.tg.update = false;
				this.tg.search = false;
				this.tg.data.oid = '';
				this.tg.data.name = '';
				this.tg.data.addr = '';
				this.tg.data.count = '';
				this.tg.data.ps = '';
				this.tg.data.date = '';
				this.tbi.visible = false;
				this.tbi.result = [];
				this.tbi.data = [];
				this.tbi.oids = [];
				this.tbi.tg = '';
			},

			querySearch(tg, pageNum, pageSize) { /* 查询系统用户信息 */
				var obj = this;
				var pn = pageNum;
				var ps = pageSize;
				var url2 = pn + "-" + ps;
				var url = "/terminal/group/queryInfo/";
				url += url2;
				var tg = this.tg;
				tg.visible = false;
				ajax_json(url, 'post', tg.data, function (data) {
					// toast('成功', data.msg, 'success')
					if (data.data == undefined) { /* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						obj.initPager();
					} else {
						obj.pager = data.data;
					}
				});

			},


			updatetg(a) {
				var tg = this.tg;
				tg.data = a;
				tg.visible = true;
				tg.title = "修改终端信息 ";
				tg.update = true;

			},
			cancel(tg) {
				this.refresh();
			},
			addT() {
				var tg = this.tg;
				tg.visible = true;
				tg.update = false;
				tg.search = false;
			},
			search() {
				var tg = this.tg;
				tg.update = false;
				tg.search = true;
				this.updateorinsert(tg);
			},
			updateorinsert(tg) {
				var a = this;
				tgb = this.tgb;
				tbi = this.tbi;
				tgb.tg = tg.data;
				tgb.oids = this.tbi.result;
				if (tg.search) {

					tg.search = false;
					this.querySearch(tg, 1, 12);
					a.refresh2();
				} else {
					if (tg.update) {
						ajax_json("/terminal/group/addup ", "put", tgb, function (result) {
							if (result.status) {
								tg.visible = false;
								tbi.visible = false;
								a.refresh();
							}
						});
					} else {
						ajax_json("/terminal/group/addup ", "post", tgb, function (result) {
							if (result.status) {
								tg.visible = false;
								tbi.visible = false;
								a.refresh();
							}
						});
					}
				}
			},
			deleteok() {
				this.$message({
					type: 'info',
					message: '已删除该条数据'
				});
			},
			tbis() {
				var obj = this;
				var tbi = this.tbi;
				var url = "/terminal/group/queryInfos ";
				var tg = this.tg;
				tbi.visible = true;


				ajax_json(url, 'post', tg.data, function (data) {
					if (data.data == undefined) { /* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						obj.initPager();
					} else {
						tbi.data = data.data.tbis;
						tbi.result = data.data.tbiss;
					}
				});

			},
			pagerCurrentChange(pageNum, pageSize) { /* 页码改变时 */
				var tg = this.tg;
				this.querySearch(tg, pageNum, pageSize);
				// this.queryGroup(pageNum, pageSize);
			},
			handleDelete(oid) {
				var a = oid;
				var obj = this;
				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					ajax_json("/terminal/group/addup/ " + a, "delete", null, function (
						result
					) {
						if (result.status) {
							obj.deleteok();
							obj.refresh();
						}
					});

				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});

			}


		}

	});
</script>

</html>