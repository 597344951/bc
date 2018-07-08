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
			<div id="toolbar">
				<el-row>
					<el-col :span="24">
						<el-menu class="el-menu-demo" mode="horizontal">
							<el-menu-item index="-1" class="normal-menu-item" disabled>正在发布中内容活动 </el-menu-item>
							<el-menu-item index="-3" class="normal-menu-item" disabled>
								<el-button-group class="control-button">
									<el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
									<el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
								</el-button-group>
							</el-menu-item>
							<el-menu-item index="-2" :style="{'float':'right'}" class="normal-menu-item" disabled>
								<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="publishingContent.pageNum"
								    :page-sizes="pageSizes" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="publishingContent.total">
								</el-pagination>
							</el-menu-item>
						</el-menu>
					</el-col>
				</el-row>
			</div>
		</el-header>
		<el-main>
			<div v-show="!dis_h_v">
				<template v-for=" it in  publishingContent.list">
					<el-card class="card-item" :body-style="{ padding: '0px' }">
						<div class="title">
							<span class="bolder"> {{it.title}} </span>
							<span class="right">
							</span>
						</div>
						<div class="content">
							<table class="dis-info-min">
								<tbody>
									<tr>
										<td>发起人</td>
										<td>：</td>
										<td>{{it.username}}</td>
									</tr>
									<tr>
										<td>发布时间</td>
										<td>：</td>
										<td>{{it.add_date}}</td>
									</tr>
									<tr>
										<td>开始时间</td>
										<td>：</td>
										<td>{{it.start_date}}</td>
									</tr>
									<tr>
										<td>结束时间</td>
										<td>：</td>
										<td>{{it.end_date}}</td>
									</tr>
									<tr>
										<td>播放时段</td>
										<td>：</td>
										<td>{{it.period}}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="bottom clearfix">
							<el-button type="text" size="small" @click="view(it)">节目预览</el-button>
							<el-button type="text" size="small" @click="viewTerminal(it)">发布终端</el-button>
							<el-button type="text" size="small" @click="offline(it)">从播放列表移除</el-button>
						</div>
					</el-card>
				</template>
			</div>
			<div v-show="dis_h_v">
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
								<el-button type="text" size="small" @click="view(scope.row)">节目预览</el-button>
								<el-button type="text" size="small" @click="viewTerminal(scope.row)">发布终端</el-button>
								<el-button type="text" size="small" @click="offline(scope.row)">从播放列表移除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
		</el-main>
		<el-footer>
		</el-footer>
	</el-container>

	<el-dialog title="终端列表" :visible.sync="publishTerminal.show" width="80%">
		<el-table :data="publishTerminal.list" border>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="rev" label="横屏/竖屏"></el-table-column>
			<el-table-column prop="typ" label="触摸类型"></el-table-column>
			<el-table-column prop="size" label="尺寸"></el-table-column>
			<el-table-column prop="ratio" label="分辨率"></el-table-column>
			<el-table-column prop="ip" label="IP"></el-table-column>
			<el-table-column prop="loc" label="位置"></el-table-column>
		</el-table>
	</el-dialog>
</div>
</body>
</html>
<script src="${urls.getForLookupPath('/assets/module/publish/publishing.js')}"></script>