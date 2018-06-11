<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>正在发布中内容</title>
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
				<h3>正在发布中内容活动</h3>
			</el-header>
			<el-main>
				<el-table :data="publishingContent.list" border style="width: 100%">
					<el-table-column prop="title" label="主题"></el-table-column>
					<el-table-column prop="username" label="发起人" width="180"></el-table-column>
					<el-table-column prop="add_date" label="时间" width="180"></el-table-column>
					<el-table-column prop="start_date" label="预定开始时间" width="120"></el-table-column>
					<el-table-column prop="end_date" label="预定结束时间" width="120"></el-table-column>
					<el-table-column prop="period" label="预定播放时段" width="120"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<%--<el-button type="text" size="small">查看终端</el-button>--%>
							<el-button type="text" size="small" @click="offline(scope.row)">从播放列表移除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="publishingContent.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="publishingContent.total">
				</el-pagination>
			</el-footer>
		</el-container>
	</div>
	<script>
		var app = new Vue({
			el: '#app',
			data: {
                pageSizes: [10, 20, 50, 100],
			    pageSize: 10,
				publishingContent:{}
			},
            methods: {
                handleSizeChange(pageSize) {
                    load(1, pageSize)
				},
                handleCurrentChange(curPage) {
					load(curPage, this.pageSize)
				},
				offline(row) {
                    this.$confirm('确认移除？').then(()=>{
                        get('/publish/process/offline/' + row.content_id, reps => {
                            if(reps.status) {
                                app.$message('下线成功 !');
                                load(1, app.pageSize);
                            } else {
                                app.$message('下线失败 !');
                            }
                        })
                    }).catch(()=>{});
				}
			}
		});
		load(1, app.pageSize);

		function load(pageNum, pageSize) {
		    get('/publish/publishing/content/'+ pageNum+'/' + pageSize, reps => {
				if(reps.status) {
					app.publishingContent = reps.data;
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
	</script>
</body>
</html>