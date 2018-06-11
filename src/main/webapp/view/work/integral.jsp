<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>积分</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<h3>积分</h3>
			</el-header>
			<el-main>
				<el-row :cutter="20">
					<el-col :span="20">
						<el-form :inline="true" :model="filter">
							<el-form-item label="姓名">
								<el-input v-model="filter.name" placeholder="请输入要查询的姓名"></el-input>
							</el-form-item>
							<el-form-item>
								<el-button type="primary" @click="search" icon="el-icon-search">查询</el-button>
							</el-form-item>
						</el-form>
					</el-col>
				</el-row>


				<el-table :data="integral.list" border style="width: 100%">
					<el-table-column prop="name" label="姓名"></el-table-column>
					<el-table-column prop="branch" label="支部"></el-table-column>
					<el-table-column prop="curMonth" label="本月获得积分"></el-table-column>
					<el-table-column prop="total" label="总获得积分"></el-table-column>
					<el-table-column prop="cur" label="当前积分数"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button size="mini" type="text" @click="showIntegralMove(scope.row)">查看积分获得情况</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="integral.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="integral.total">
				</el-pagination>
			</el-footer>
		</el-container>

		<%--添加计划弹窗--%>
		<el-dialog title="积分动态" :visible.sync="integralMove.show">
			<el-card shadow="never">
				<p style="font-size: 12px;" v-for="m in integralMove.move">[{{m.date}}] {{m.desc}} 积分：{{m.move}}</p>
			</el-card>
			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="integralMove.show = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="integralMove.show = false">确 定</el-button>
			</div>
		</el-dialog>
	</div>
	<script>
        var planContent;
		var app = new Vue({
			el: '#app',
			data: {
			    keyword: '',
                pageSizes: [10, 20, 50, 100],
			    pageSize: 10,
                integral: {
			        total: 1,
                    pageNum: 1,
					list: [{
			            name: 'XXX党员',
                        branch: 'XXX党支部',
                        curMonth: 100,
						total: 1000,
                        cur: 500
					}]

				},
                integralMove: {
			        show: false,
					move:[{
			            date: '2018-05-01 10:00:00',
						desc: '参加五一节活动',
						type: '活动参加',
						move: 100
					}, {
                        date: '2018-05-01 10:00:00',
                        desc: '参加五一节活动',
                        type: '活动参加',
                        move: 100
                    }, {
                        date: '2018-05-01 10:00:00',
                        desc: '参加五一节活动',
                        type: '活动参加',
                        move: 100
                    }, {
                        date: '2018-05-01 10:00:00',
                        desc: '参加五一节活动',
                        type: '活动参加',
                        move: 100
                    }, {
                        date: '2018-05-01 10:00:00',
                        desc: '兑换活动礼品',
                        type: '活动参加',
                        move: -500
                    }]
				},
				filter: {}
			},
            methods: {
                handleSizeChange(pageSize) {
                    load(1, pageSize)
				},
                handleCurrentChange(curPage) {
					load(curPage, this.pageSize)
				},
				search() {

				},
				showIntegralMove(row) {
                    this.integralMove.show = true
				}
			}
		});

		//load(1, app.pageSize);

		function load(pageNum, pageSize) {
		    get('' + pageSize, reps => {
				if(reps.status) {
					app.lecture = reps.data;
				} else {
				    app.$message('没有数据...');
				}
			});
        }

        function get(url, callback) {
            $.ajax({
                type:'GET',
                url:url,
                dataType:'json',
                success: function(reps){
                    callback(reps);
                },
                error: function (err) {
                    app.$notify({
                        title: 'ERROR',
                        message: '系统错误...',
                        type: 'error'
                    });
                }
            });
        }

        function post(url, postData, callback) {
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(postData),
                success: function(reps){
                    callback(reps);
                },
                error: function (err) {
                    app.$notify({
                        title: 'ERROR',
                        message: '系统错误...',
                        type: 'error'
                    });
                }
            });
        }
	</script>
</body>
</html>