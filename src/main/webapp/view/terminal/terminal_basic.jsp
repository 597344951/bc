<%@ page contentType="text/html;charset=UTF-8" language="java"%>
	<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
		<!DOCTYPE html>
		<html>

		<head>
			<base href="<%=basePath%>">
			<meta charset="UTF-8">
			<!-- 引入样式 -->
			
<%@include file="/include/head.jsp"%>
<%@include file="/include/echarts.jsp"%>
<%@include file="/include/vcharts.jsp"%>
</head>

<body>
	<div id="app" class="height_full">
		<el-container>
			<div role="table">
				<div class="toolbar">
					<div class="grid-content bg-purple">
						<el-button type="primary" size="small" icon="el-icon-plus" title="增加" @click="addT()">增加</el-button>
						<%-- <el-button type="primary" size="small" icon="el-icon-search" @click="search()">搜索</el-button> --%>
							<el-popover placement="right" width="400" trigger="click">
								<el-form ref="form" label-width="80px" size="mini">
									<el-form-item label="终端名称">
										<el-input v-model="tbi.data.name" style="width: 80%;"></el-input>
									</el-form-item>
									<el-form-item label="所属地区">
										<el-input v-model="tbi.data.loc" style="width: 80%;"></el-input>
									</el-form-item>
									<el-form-item label="在线状态">
										<el-input v-model="tbi.data.online" style="width: 80%;"></el-input>
									</el-form-item>
									<el-form-item label="屏幕方向">
										<el-input v-model="tbi.data.rev" style="width: 80%;"></el-input>
									</el-form-item>
									<el-form-item size="large">
										<el-button type="primary">确定</el-button>
										<el-button @click="search()">更多</el-button>
									</el-form-item>
								</el-form>
								<el-button type="primary" size="small" icon="el-icon-search" slot="reference" class="margin-0-10">搜索</el-button>
							</el-popover>
							<el-button type="primary" size="small" icon="el-icon-refresh" @click="refresh()">刷新</el-button>
							<el-button type="primary" size="small" icon="el-icon-info" @click="statistic()">图表</el-button>
					</div>
				</div>
			</div>


			<!--表格主体-->
			<el-main>
				<template>
					<el-table :data="pager.list">

						<el-table-column prop="name" label="终端名称" width="200"></el-table-column>
						<el-table-column prop="loc" label="所属地区" width="200"></el-table-column>
						<el-table-column prop="online" label="在线状态" width="100"></el-table-column>
						<el-table-column prop="size" label="屏幕尺寸" width="100"></el-table-column>
						<el-table-column prop="ratio" label="屏幕分辨率" width="150"></el-table-column>
						<el-table-column prop="rev" label="屏幕方向" width="100"></el-table-column>
						<el-table-column prop="tel" label="联系电话" width="200"></el-table-column>
						<el-table-column prop="addr" label="终端地址" width="200"></el-table-column>
						<el-table-column prop="sys" label="系统" width="100"></el-table-column>
						<el-table-column prop="ver" label="软件版本" width="200"></el-table-column>
						<el-table-column prop="id" label="标识ID" width="100"></el-table-column>
						<el-table-column prop="code" label="标识code" width="200"></el-table-column>
						<el-table-column prop="typeId" label="类别id" width="100"></el-table-column>
						<el-table-column prop="resTime" label="注册时间" width="220"></el-table-column>
						<el-table-column prop="lastTime" label="最新时间" width="220"></el-table-column>
						<el-table-column prop="ip" label="ip地址" width="150"></el-table-column>
						<el-table-column prop="mac" label="mac地址" width="200"></el-table-column>
						<el-table-column prop="typ" label="终端类型" width="100"></el-table-column>
						<el-table-column prop="gis" label="GIS信息" width="200"></el-table-column>
						<el-table-column prop="warranty" label="保修信息" width="100"></el-table-column>
						<el-table-column prop="oid" label="oid" width="200"></el-table-column>
						<el-table-column label="操作" fixed="right" width="200">
							<template slot-scope="scope">
								<el-button SIZI="MINI" type="info" @click="updateTbi(scope.row)">编辑</el-button>
								<el-button SIZI="MINI" type="danger " @click="handleDelete(scope.row.oid) ">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
				</template>
			</el-main>
			<el-footer>
				<el-pagination id="pagesdididi " layout="total, prev, pager, next, jumper " @current-change="pagerCurrentChange(pager.pageNum,
									    pager.pageSize) " :current-page.sync="pager.pageNum " :page-size.sync="pager.pageSize " :total="pager.total ">
				</el-pagination>
			</el-footer>
		</el-container>
		<el-dialog title="" :visible.sync="tbi.visible" width="60%">
			<el-form :model="tbi" label-width="100px" ref>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="终端名称">
							<el-input v-model="tbi.data.name" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="所属地区">
							<el-input v-model="tbi.data.loc" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="在线状态">
							<el-input v-model="tbi.data.online" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="屏幕方向">
							<el-input v-model="tbi.data.rev" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="屏幕尺寸">
							<el-input v-model="tbi.data.size" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="屏幕分辨率">
							<el-input v-model="tbi.data.ratio" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="联系电话">
							<el-input v-model="tbi.data.tel" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="终端地址">
							<el-input v-model="tbi.data.addr" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="标识ID">
							<el-input v-model="tbi.data.id" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="标识code">
							<el-input v-model="tbi.data.code" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="类别id">
							<el-input v-model="tbi.data.typeId" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="注册时间">
							<el-input v-model="tbi.data.resTime" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="最新时间">
							<el-input v-model="tbi.data.lastTime" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="ip地址">
							<el-input v-model="tbi.data.ip" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="mac地址">
							<el-input v-model="tbi.data.mac" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="系统">
							<el-input v-model="tbi.data.sys" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="系统版本">
							<el-input v-model="tbi.data.ver" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<el-form-item label="终端类型">
							<el-input v-model="tbi.data.typ" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="GIS信息">
							<el-input v-model="tbi.data.gis" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<el-form-item label="保修信息">
							<el-input v-model="tbi.data.warranty" style="width: 80%;"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="refresh()">取 消</el-button>
				<el-button type="primary" @click="updateorinsert(tbi)">确 定</el-button>
			</div>
		</el-dialog>
		<el-dialog title="统计" :visible.sync="statistics" width="80%">
			<el-header>
				<el-button @click="sta('onesta')">1</el-button>
				<el-button @click="sta('twosta')">2</el-button>
				<el-button @click="sta('threesta')">3</el-button>
				<el-button @click="sta('foursta')">4</el-button>
			</el-header>
			<el-main>
				<template>
					<el-row type="flex" justify="center" v-if="onesta">
						<el-col :span="8">
							<h2>屏幕分辨率统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="ratiochartData" :settings="chartSettings"></ve-pie>
						</el-col>
						<el-col :span="8">
							<h2>屏幕方向统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="revchartData" :settings="chartSettings"></ve-pie>
						</el-col>
					</el-row>
					<el-row type="flex" justify="center" v-if="twosta">
						<el-col :span="8">
							<h2>终端在线统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="onlinechartData" :settings="chartSettings"></ve-pie>
						</el-col>
						<el-col :span="8">
							<h2>终端所属地区统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="locchartData" :settings="chartSettings"></ve-pie>
						</el-col>
					</el-row>
					<el-row v-if="threesta" type="flex" justify="center">
						<el-col :span="8">
							<h2>终端类型统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="typchartData" :settings="chartSettings"></ve-pie>
						</el-col>
						<el-col :span="8">
							<h2>终端保修信息统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="warrantychartData" :settings="chartSettings"></ve-pie>
						</el-col>
					</el-row>
					<el-row v-if="foursta" type="flex" justify="center">
						<el-col :span="8">
							<h2>软件版本统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="verchartData" :settings="chartSettings"></ve-pie>
						</el-col>
						<el-col :span="16">
							<h2>全局统计</h2>
							<ve-bar :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="totalData" :settings="chartSettings"></ve-bar>
						</el-col>
					</el-row>
				</template>
			</el-main>
			<el-footer>
				<el-button @click="refresh()">返回</el-button>
			</el-footer>
		</el-dialog>

	</div>
</body>
<script type="module">
	import chartConfig from "/assets/js/vcharts_config.js";
	var appInstince = new Vue({
		el: '#app',
		
		data: {
			chartConfig: chartConfig,
			onesta:true,
			twosta:false,
			threesta:false,
			foursta:false,
			chartSettings: {
				roseType: 'radius'
			},

			ratiochartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			onlinechartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			locchartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			warrantychartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			revchartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			verchartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			typchartData: {
				columns: ['name', 'value'],
				rows: [{
					'日期': '1/1',
					'访问用户': 1393
				},
				{
					'日期': '1/2',
					'访问用户': 3530
				},
				]
			},
			totalData: {
				columns: ['name', 'value', 'rate'],
				rows: [
					{ '日期': '1/1', '访问用户': 1393, '下单率': 0.32 },
					{ '日期': '1/2', '访问用户': 3530, '下单率': 0.26 },
					{ '日期': '1/3', '访问用户': 2923, '下单率': 0.76 },
					{ '日期': '1/4', '访问用户': 1723, '下单率': 0.49 },
					{ '日期': '1/5', '访问用户': 3792, '下单率': 0.323 },
					{ '日期': '1/6', '访问用户': 4593, '下单率': 0.78 }
				]
			},
			statistics: false,
			sysUserStatu: " ",
			/* 用户状态下拉框选定时绑定值初始化 */
			sysUsercreateTime: " ",
			insertSysUserDialog: false,
			dialogVisible: false,
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
			tbi: {
				title: "测试标题",
				visible: false,
				update: false, // 是否是更新
				search: false,
				data: {
					oid: "",
					name: "",
					id: "",
					code: "",
					typeId: "",
					resTime: "",
					online: "",
					lastTime: "",
					ip: "",
					mac: "",
					sys: "",
					size: "",
					ratio: "",
					rev: "",
					ver: "",
					typ: "",
					tel: "",
					addr: "",
					gis: "",
					warranty: "",
					loc: ""
				},

			}

		},
		created: function () {
			this.queryTerminals(1, 12); //这里定义这个方法，vue实例之后运行到这里就调用这个函数
		},
		methods: {
			pagerCurrentChange(pageNum, pageSize) { /* 页码改变时 */
				this.queryTerminals(pageNum, pageSize);
			},
			initPager() {
				this.pager.pageNum = 1;
				this.pager.pageSize = 12;
				this.pager.total = 0;
				this.pager.list = new Array();
			},

			queryTerminals(pageNum, pageSize) { /* 查询系统用户信息 */
				var obj = this;
				var pn = pageNum;
				var pz = pageSize;
				var url2 = pn + "-" + pz;

				var url = "/terminal/basic/queryInfo/";
				url += url2;
				var t = {
					pageNum: pageNum == null ? 1 : pageNum,
					pageSize: pageSize == null ? 12 : pageSize,
				}
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
					this.tbi.visible = false,
					this.tbi.update = false,
					this.tbi.search = false,
					this.tbi.data.oid = "",
					this.tbi.data.name = "",
					this.tbi.data.id = "",
					this.tbi.data.code = "",
					this.tbi.data.typeId = "",
					this.tbi.data.resTime = "",
					this.tbi.data.online = "",
					this.tbi.data.lastTime = "",
					this.tbi.data.ip = "",
					this.tbi.data.mac = "",
					this.tbi.data.sys = "",
					this.tbi.data.size = "",
					this.tbi.data.ratio = "",
					this.tbi.data.rev = "",
					this.tbi.data.ver = "",
					this.tbi.data.typ = "",
					this.tbi.data.tel = "",
					this.tbi.data.addr = "",
					this.tbi.data.gis = "",
					this.tbi.data.warranty = "",
					this.tbi.data.loc = "",
					this.statistics = false,
					this.onesta = true,
					this.twosta = false,
					this.threesta = false,
					this.foursta=false,
					this.queryTerminals(1, 12);
			},

			querySearch(tbi, pageNum, pageSize) { /* 查询系统用户信息 */
				var obj = this;
				var url = "/terminal/basic/queryInfo/";
				var tbi = this.tbi;
				var pn = pageNum;
				var pz = pageSize;
				var url2 = pn + "-" + pz;
				url += url2;
				tbi.visible = false;
				ajax_json(url, 'post', tbi.data, function (data) {
					// toast('成功', data.msg, 'success')
					if (data.data == undefined) { /* 没有查询到数据时，初始化页面信息，使页面正常显示 */
						obj.initPager();
					} else {
						obj.pager = data.data;
					}
				});

			},
			deleteok() {
				this.$message({
					type: 'info',
					message: '已删除该条数据'
				});
			},


			updateTbi(a) {
				var tbi = this.tbi;
				tbi.data = a;
				tbi.visible = true;
				tbi.title = "修改终端信息";
				tbi.update = true;

			},
			cancel(tbi) {
				tbi.visible = false;
				this.queryTerminals(1, 12);
			},
			addT() {
				var tbi = this.tbi;
				tbi.visible = true;
				tbi.update = false;
			},
			search() {
				var tbi = this.tbi;
				tbi.visible = true;
				tbi.update = false;
				tbi.search = true;
			},
			updateorinsert(tbi) {
				var a = this;
				if (tbi.search) {
					tbi.search = false;
					this.querySearch(tbi, 1, 12);
				} else {
					if (tbi.update) {
						ajax_json("/terminal/basic/addup", "put", tbi.data, function (result) {
							if (result.status) {
								tbi.visible = false;
								a.refresh();
							}
						});
					} else {
						ajax_json("/terminal/basic/addup", "post", tbi.data, function (result) {
							if (result.status) {
								tbi.visible = false;
								a.refresh();
							}
						});
					}
				}
			},
		
			draw(str) {

				var ins = this;
				var a = str;
				// 使用刚指定的配置项和数据显示图表。
				ajax_json("/terminal/basic/echarts/" + a, "put", null, function (data) {
					if (a == 'ratio') {
						ins.ratiochartData.rows = data.data;
					}
					if (a == 'online') {
						ins.onlinechartData.rows = data.data;
					}
					if (a == 'loc') {
						ins.locchartData.rows = data.data;
					}
					if (a == 'warranty') {
						ins.warrantychartData.rows = data.data;
					}
					if (a == 'rev') {
						ins.revchartData.rows = data.data;
					}
					if (a == 'ver') {
						ins.verchartData.rows = data.data;
					}
					if (a == 'typ') {
						ins.typchartData.rows = data.data;
					}
					if (a == 'total') {
						ins.totalData.rows = data.data;
					}


				});

			},
			sta(str){
				var ins = this;
				var a = str;
				if(a=='onesta'){
					ins.onesta=true;
					ins.twosta=false;
					ins.threesta=false;
					ins.foursta=false;
				}
				if(a=='twosta'){
					ins.onesta=false;
					ins.twosta=true;
					ins.threesta=false;
					ins.foursta=false;
				}
				if(a=='threesta'){
					ins.onesta=false;
					ins.twosta=false;
					ins.threesta=true;
					ins.foursta=false;
				}
				if(a=='threesta'){
					ins.onesta=false;
					ins.twosta=false;
					ins.threesta=true;
					ins.foursta=false;
				}
				if(a=='foursta'){
					ins.onesta=false;
					ins.twosta=false;
					ins.threesta=false;
					ins.foursta=true;
					
				}
				ins.statistic();
			},

			statistic() {
				this.statistics = true;
				this.draw("ratio");
				this.draw("online");
				this.draw("loc");
				this.draw("warranty");
				this.draw("rev");
				this.draw("ver");
				this.draw("typ");
				this.draw("total");
			},
			handleDelete(oid) {
				var a = oid;
				var obj = this;
				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					ajax_json("/terminal/basic/addup/ " + a, "delete", null, function (
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
		<script type="text/javascript">
		</script>

		</html>