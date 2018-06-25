<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>活动素材征集</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
        }
        .image-box {
            width: 320px;
            height: 60px;
            border-radius: 5px;
            margin: 10px;
            float: left;
            position: relative;
        }
        .image-preview {
            width: 600px;
            height: 400px;
        }
        .image-box img {
            height: 60px;
            width: 60px;
            border-radius: 5px;
            display: inline-block;
        }
        .image-delete {
            font-size: 25px;
            position: absolute;
            top: -10px;
            right: -5px;
            color: red;
            cursor: pointer;
        }
        .image-desc {
            display: inline-block;
            width: 250px;
            height: 60px;
            font-size: 12px;
        }
        .image-desc textarea {
            height: 60px;
        }
        .image-add {
            clear: both;
        }
        .el-upload--picture-card {
            height: 50px;
            width: 50px;
            line-height: 50px;
            margin: 10px;
        }
        .el-upload--picture-card i {
            margin: 10px;
        }
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<h3>已结束活动</h3>
			</el-header>
			<el-main>
				<el-table :data="activity.list" border style="width: 100%">
					<el-table-column prop="title" label="主题"></el-table-column>
					<el-table-column prop="username" label="发起人" width="180"></el-table-column>
					<el-table-column prop="add_date" label="时间" width="180"></el-table-column>
					<el-table-column prop="start_date" label="预定开始时间" width="120"></el-table-column>
					<el-table-column prop="end_date" label="预定结束时间" width="120"></el-table-column>
					<el-table-column prop="period" label="预定播放时段" width="120"></el-table-column>
					<el-table-column label="操作" width="200">
						<template slot-scope="scope">
							<el-button type="text" size="small" @click="collectMaterial">募集素材</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="activity.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="activity.total">
				</el-pagination>
			</el-footer>
		</el-container>

        <el-dialog title="素材募集" :visible.sync="material.show">
			<div class="image-box" v-for="img in material.list">

                <el-popover placement="right" width="600" trigger="hover">
                    <img :src="img.url" class="image-preview">
                    <img :src="img.url" slot="reference">
                </el-popover>

                <%-- <img :src="img.url"> --%>
                <div class="image-desc">
                    <el-input type="textarea" resize="none" :rows="2" placeholder="请描述一下素材内容" v-model="img.desc"></el-input>
                </div>
                <span class="el-icon-circle-close-outline image-delete" @click="deleteMaterial(img.uid)"></span>
            </div>
            <div class="image-add">
                <el-upload
                    action="/material/commonUpload"
                    :show-file-list="false"
                    list-type="picture-card"
                    :on-success="handleUploadSuccess"
					:on-error="handleUploadError"
                    :on-remove="handleUploadRemove">
                    <i class="el-icon-plus"></i>
                </el-upload>
            </div>
			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="material.show = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="material.show = false">添 加</el-button>
			</div>
		</el-dialog>
	</div>

	<script>
		var app = new Vue({
			el: '#app',
			data: {
                pageSizes: [10, 20, 50, 100],
			    pageSize: 10,
				activity:{},
                material: {
                    show: false,
                    list:[]
                }
			},
            methods: {
                handleSizeChange(pageSize) {
                    load(1, pageSize)
				},
                handleCurrentChange(curPage) {
					load(curPage, this.pageSize)
				},
                handleUploadRemove(file, fileList) {
                    for(var i=0; i<this.addition.material.length; i++) {
                        if(file.name == this.addition.material[i].name) {
                            this.addition.material.splice(i, 1);
                        }
                    }
				},
				handleUploadSuccess(response, file, fileList) {
                    if(response.status) {
                        this.material.list.push({
                            uid: new Date().getTime(),
                            url:response.url,
                            desc: ''
                        });
                    } else {
                        this.$message('上传失败,请重新选择上传.');
                    }
                },
                handleUploadError(err, file, fileList) {
                    this.$message('上传失败,请重新选择上传.');
                },
                beforeRemove(file, fileList) {
                    return this.$confirm('确定移除 '+ file.name +'？');
                },
                collectMaterial() {
                    this.material.show = true
                },
                deleteMaterial(uid) {
                    for(let i in this.material.list) {
                        if(uid == this.material.list[i].uid) {
                            this.material.list.splice(i, 1)
                            break
                        }
                    }
                }
			}
		});
		load(1, app.pageSize);

		function load(pageNum, pageSize) {
		    get('/publish/activity/'+ pageNum+'/' + pageSize, reps => {
				if(reps.status) {
					app.activity = reps.data;
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
                    app.$message.error('系统错误, 请联系管理员.')
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
                    app.$message.error('系统错误, 请联系管理员.')
                }
            });
        }
	</script>
</body>
</html>