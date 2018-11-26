<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
	<meta charset="UTF-8">
	<title>内容模板</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<%@include file="/include/ueditor.jsp" %>
	<style>
		.image {
			width: 100%;
			height: 128px;
			display: block;
		}
		.download {
			float: right;
			margin: 5px 15px;
			font-size: 12px;
		}
		.el-row {
			margin-top: 20px;
		}
		h3 {
			margin: 5px;
			font-size: 12px;
		}
	</style>
</head>

<body>
	<div id="app">
		<el-container>
			<el-main>
				<div>
					<el-row :gutter="20">
						<el-col :span="4">
							<h3>主题</h3>
						</el-col>
						<el-col :span="18">
							<el-input v-model="title" placeholder="发布内容主题" :disabled="true"></el-input>
						</el-col>
					</el-row>
					<el-row :gutter="20" v-show="templateText">
						<el-col :span="4">
							<h3>内容</h3>
						</el-col>
						<el-col :span="18">
							<div>
								<script id="templateText" name="templateText" style="height:500px;" type="text/plain"></script>
							</div>
						</el-col>
					</el-row>
					<el-row :gutter="20" v-if="demand">
						<el-col :span="4">
							<h5>海报制作要求</h5>
						</el-col>
						<el-col :span="18">
							<el-input type="textarea" :rows="8" v-model="demand" disabled></el-input>
						</el-col>
					</el-row>

					<el-row :gutter="20">
						<el-col :span="4">
							<h3>素材列表</h3>
						</el-col>
						<el-col :span="18">
							<el-row>
								<el-col :span="3" v-for="pic in pictureAnnex">
									<el-card :body-style="{ padding: '0px' }">
										<img :src="pic.url" class="image" :title="pic.title" @click="open(pic.url)">
									</el-card>
								</el-col>
								<el-col :span="3" v-for="video in videoAnnex">
									<el-card :body-style="{ padding: '0px' }">
										<img src="/images/video.png" class="image" :title="video.title" @click="open(video.url)">
									</el-card>
								</el-col>
							</el-row>
						</el-col>
					</el-row>
				</div>
			</el-main>
		</el-container>
	</div>
	<script type="text/javascript">
		var app = new Vue({
			el: '#app',
			data: {
				title: '',
				startDate: '',
				endDate: '',
				time: '',
				templateText: '',
				demand: '',
				pictureAnnex: [],
				videoAnnex: [],
				terminals: [],
				exUsers: []
			},
			methods: {
				open(src) {
					window.open(src, '_blank')
				}
			}
		});
		//编辑器
		var ue = UE.getEditor('templateText', {
			serverUrl: '/ueditor/controller.jsp',
			toolbars: [['fullscreen']],
			wordCount: false,
			elementPathEnabled: false
		});
		function init() {
			$.ajax({
				type: 'GET',
				url: '/publish/content/${contentId}',
				dataType: 'json',
				success: function (reps) {
					if (reps.status) {
						app.title = reps.data.title;
						if (reps.data.content) ue.setContent(reps.data.content);
						app.demand = reps.data.demand;
						app.pictureAnnex = [];
						app.videoAnnex = [];
						for (var i in reps.data.material) {
							if (reps.data.material[i].type.startsWith('image')) {
								app.pictureAnnex.push({
									title: reps.data.material[i].description,
									url: '${mediaServe}' + reps.data.material[i].url,
								});
							} else {
								app.videoAnnex.push({
									title: reps.data.material[i].description,
									url: '${mediaServe}' + reps.data.material[i].url,
								});
							}
						}
					} else {
						app.$message('没有数据...');
					}
				},
				error: function (err) {
					app.$message('系统错误.');
				}
			});
		}

		ue.ready(() => {
			init();
		});
	</script>
</body>

</html>