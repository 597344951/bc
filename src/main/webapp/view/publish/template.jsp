<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	int contentId = (int) request.getAttribute("contentId");
%>
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
						<el-col :span="4"><h3>主题</h3></el-col>
						<el-col :span="20"><el-input v-model="title" placeholder="发布内容主题" :disabled="true"></el-input></el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="4"><h3>播放周期</h3></el-col>
						<el-col :span="20">
							<el-date-picker v-model="startDate" type="date" placeholder="开始日期" value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
							-
							<el-date-picker v-model="endDate" type="date" placeholder="结束日期" value-format="yyyy-MM-dd" :disabled="true"></el-date-picker>
							:
							<el-time-picker
									:disabled="true"
									is-range
									v-model="time"
									range-separator="-"
									value-format="H:mm"
									start-placeholder="开始时间"
									end-placeholder="结束时间"
									placeholder="选择时间范围">
							</el-time-picker>
						</el-col>
					</el-row>
					<el-row :gutter="20">
						<el-col :span="4">
							<h3>内容</h3>
						</el-col>
						<el-col :span="20">
							<div><script id="templateText" name="templateText" style="height:500px;" type="text/plain"></script></div>
						</el-col>
					</el-row>

					<el-row :gutter="20">
						<el-col :span="4">
							<h3>素材列表</h3>
						</el-col>
						<el-col :span="20">
							<el-row>
								<el-col :span="3" v-for="pic in pictureAnnex">
									<el-card :body-style="{ padding: '0px' }">
										<img :src="pic.url" class="image" :title="pic.title">
										<a :href="pic.downloadUrl" class="download">点击下载</a>
									</el-card>
								</el-col>
								<el-col :span="3" v-for="video in videoAnnex">
									<el-card :body-style="{ padding: '0px' }">
										<img src="/images/video.png" class="image" :title="video.title">
										<a :href="video.downloadUrl" class="download">点击下载</a>
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
				endDate:'',
				time: '',
                templateText: '',
				pictureAnnex: [],
				videoAnnex: [],
                terminals: [],
				exUsers:[]
			},
            methods: {
			}
		});
		//编辑器
        var ue = UE.getEditor('templateText',{
            serverUrl: '/ueditor/controller.jsp',
            toolbars: [['fullscreen']],
            wordCount: false,
            elementPathEnabled: false
        });
        function init() {
            $.ajax({
                type:'GET',
                url:'/publish/content/<%=contentId%>',
                dataType:'json',
                success: function(reps){
                    if(reps.status) {
						app.title = reps.data.title;
                        app.startDate = new Date(reps.data.start_date);
                        app.endDate = new Date(reps.data.end_date);
                        app.time = reps.data.period.split('-');
                        ue.setContent(reps.data.content);
                        app.pictureAnnex = [];
                        app.videoAnnex = [];
                        for(var i in reps.data.material) {
                            if('picture' == reps.data.material[i].type) {
                                app.pictureAnnex.push({
									title: reps.data.material[i].description,
									url: '/material/image/' + reps.data.material[i].material_id,
									downloadUrl: '/material/download/' + reps.data.material[i].material_id
								});
							} else {
                                app.videoAnnex.push({
                                    title: reps.data.material[i].description,
                                    url: '/material/image/' + reps.data.material[i].material_id,
                                    downloadUrl: '/material/download/' + reps.data.material[i].material_id
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