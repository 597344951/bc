<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>发布完成内容</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-dialog.el-table thead, .el-dialog.el-table__row {
			font-size: 12px;
		}
		.el-input-number i {
			margin-top: 6px;
		}
		.el-upload-list__item {
			height: 25px;
		}
		.card-item{
            min-height: 200px;
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
							<el-menu-item index="-1" class="normal-menu-item" disabled>过往发布内容活动 </el-menu-item>
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
							<el-button v-if="it.content_type_id == 3" type="text" size="small" @click="loadAddition(it)">完善活动信息</el-button>
							<el-button v-if="it.content_type_id == 3" type="text" size="small" @click="loadParticipant(it)">参加人员</el-button>
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
					<el-table-column label="操作" width="200">
						<template slot-scope="scope">
							<el-button v-if="scope.row.content_type_id == 3" type="text" size="small" @click="loadAddition(scope.row)">完善活动信息</el-button>
							<el-button v-if="scope.row.content_type_id == 3" type="text" size="small" @click="loadParticipant(scope.row)">参加人员</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
		</el-main>
		<el-footer>
		</el-footer>
	</el-container>

	<el-dialog title="信息完善" :visible.sync="additionDialogVisible">
		<el-form ref="form" :model="addition" label-width="100px">
			<el-form-item label="预定参加人数">
				<el-input v-model="addition.ordainParticipantNumber" disabled></el-input>
			</el-form-item>
			<el-form-item label="活动时间">
				<el-input v-model="addition.date" disabled></el-input>
			</el-form-item>
			<el-form-item label="实际参加人数">
				<el-input-number v-model="addition.actualParticipantNumber" controls-position="right" :min="0" :max="addition.ordainParticipantNumber"></el-input-number>
			</el-form-item>
			<el-form-item label="活动总结">
				<el-input type="textarea" :rows="4" placeholder="请输入内容" v-model="addition.summary">
				</el-input>
			</el-form-item>
			<el-form-item label="活动素材">
				<el-upload action="/material/uploadFile" :file-list="addition.material" :before-remove="beforeRemove" :on-remove="handleUploadRemove"
				    :on-success="handleUploadSuccess" :on-error="handleUploadError" list-type="text">
					<el-button size="small" type="primary">点击上传</el-button>
				</el-upload>
			</el-form-item>
		</el-form>

		<div slot="footer" class="dialog-footer">
			<el-button size="mini" @click="additionDialogVisible = false">取 消</el-button>
			<el-button size="mini" type="primary" @click="completeAddition">确 定</el-button>
		</div>
	</el-dialog>

	<el-dialog title="参加人员" :visible.sync="participantDialogVisible">
		<el-input size="mini" style="width: 80px;" v-model="participateLength" placeholder="活动时长"></el-input>
		<el-button type="success" size="mini" @click="changeAllParticipateLength">一键修改</el-button>
		<el-button type="success" size="mini" @click="changeAllParticipated">一键签到</el-button>
		<el-table :data="participant" size="mini" style="width: 100%">
			<el-table-column type="index" width="50"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="isParticipated" label="是否缺勤">
				<template slot-scope="scope">
					<el-select size="mini" v-model="scope.row.isParticipated">
						<el-option label="缺勤" value="0"></el-option>
						<el-option label="参加" value="1"></el-option>
					</el-select>
				</template>
			</el-table-column>
			<el-table-column prop="participateLength" label="活动时长（分钟）">
				<template slot-scope="scope">
					<el-input size="mini" v-model="scope.row.participateLength"></el-input>
				</template>
			</el-table-column>
		</el-table>

		<div slot="footer" class="dialog-footer">
			<el-button size="mini" @click="participantDialogVisible = false">取 消</el-button>
			<el-button size="mini" type="primary" @click="updateParticipant">确 定</el-button>
		</div>
	</el-dialog>
</div>
</body>
</html>
<script src="${urls.getForLookupPath('/assets/module/publish/published.js')}"></script>