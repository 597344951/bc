<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>党费缴纳情况</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-row {
			margin-bottom: 15px;
		}
		.el-upload-list__item {
			height: 25px;
		}
		.separation {
			text-align: center;
		}
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<h3>党费缴纳信息</h3>
			</el-header>
			<el-main>
				<el-row :cutter="20">
					<el-col :span="22">
						<el-form :inline="true" :model="filter">
							<el-form-item label="姓名">
								<el-input size="medium" v-model="filter.name" placeholder="姓名"></el-input>
							</el-form-item>
							<el-form-item label="支部">
								<el-input size="medium" v-model="filter.branch" placeholder="支部"></el-input>
							</el-form-item>
							<el-form-item label="缴纳时间" style="margin: 0">
								<el-col :span="10">
									<el-form-item style="margin: 0">
										<el-date-picker size="medium" type="date" placeholder="选择日期" v-model="filter.startDate" style="width: 100%;"></el-date-picker>
									</el-form-item>
								</el-col>
								<el-col :span="2" class="separation">-</el-col>
								<el-col :span="10">
									<el-form-item >
										<el-date-picker size="medium" type="date" placeholder="选择时间" v-model="filter.endDate" style="width: 100%;"></el-date-picker>
									</el-form-item>
								</el-col>
							</el-form-item>
							<el-form-item>
								<el-button size="medium" type="primary" @click="search" icon="el-icon-search">查询</el-button>
							</el-form-item>
						</el-form>
					</el-col>
					<el-col :span="2">
						<el-button size="medium" type="primary" @click="addDuesDialogVisible = true" icon="el-icon-plus">添加新纪录</el-button>
					</el-col>
				</el-row>


				<el-table :data="dues.list" border style="width: 100%">
					<el-table-column prop="name" label="姓名"></el-table-column>
					<el-table-column prop="branch" label="支部"></el-table-column>
					<el-table-column prop="period" label="党费周期"></el-table-column>
					<el-table-column prop="date" label="缴纳时间"></el-table-column>
					<el-table-column prop="amount" label="缴纳金额"></el-table-column>
					<el-table-column prop="wage" label="工资"></el-table-column>
					<el-table-column prop="remark" label="备注"></el-table-column>
					<%--<el-table-column label="操作">
						<template slot-scope="scope">
						</template>
					</el-table-column>--%>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="dues.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="dues.total">
				</el-pagination>
			</el-footer>
		</el-container>

		<%--添加计划弹窗--%>
		<el-dialog title="导入新纪录" :visible.sync="addDuesDialogVisible">
			<el-upload
					style="text-align: center"
					accept=".xls, .xlsx"
					action="/material/uploadFile"
					drag>
				<i class="el-icon-upload"></i>
				<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
				<div class="el-upload__tip" slot="tip">只能上传指定格式的excel文件</div>
			</el-upload>
			<div slot="footer" class="dialog-footer">
				<el-button @click="addDuesDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addDuesDialogVisible = false">添 加</el-button>
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
				dues: {
			        total: 1,
                    pageNum: 1,
					list: [{
			            name: 'XXX党员',
                        branch: 'XXX党支部',
                        period: '5月份',
						date: '2018-06-01 10:30:00',
                        amount: 50,
                        wage: 5000,
                        remark: ''

					}]

				},
                addDuesDialogVisible: false,
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