<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>活动剪影制作</title>
    <%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
    <style>
        .el-menu-item.is-active {
            border-bottom: 0px !important;
        }
        .pagination {
            float: right !important;
        }
        .el-main {
            margin-top: 10px;
        }
        .material-box {
			width: 100px;
			height: 100px;
			border-radius: 5px;
			margin: 3px;
			overflow: hidden;
			display: inline-block;
			position: relative;
		}
		.material-box img {
			height: 100%;
			width: 100%;
		}
		.material-add {
			border: 1px dashed #c0ccda;
			text-align: center;
			cursor: pointer;
		}
		.material-add i {
			font-size: 30px;
			line-height: 100px;
			color: #c0ccda;
		}
		.material-del {
			height: 100%;
			width: 100%;
			background-color: rgba(0, 0, 0, 0.7);
			position: absolute;
			top: 0;
			left: 0;
			z-index: 100;
			text-align: center;
			color: white;
			opacity: 0;
			transition:opacity 0.5s;
		}
		.material-del:hover{
			opacity: 1;
		}
		.material-del i {
		    margin: 40px auto;
			font-size: 20px;
			cursor: pointer;
			color:#fff;
		}
    </style>
</head>

<body>
    <div id="app">
        <el-container>
            <el-header>
                <el-menu mode="horizontal">
                    <el-menu-item index="title">
                        <h2>活动剪影</h2>
                    </el-menu-item>
                    <el-menu-item index="pagination" class="pagination">
                        <el-pagination background layout="prev, pager, next" :total="silhouettes.total" :page-size="silhouettes.pageSize"
                            :current-page="silhouettes.pageNum" @current-change="otherPage">
                        </el-pagination>
                    </el-menu-item>
                </el-menu>
            </el-header>
            <el-main>
                <el-button size="mini" type="primary" @click="silhouette.show = true">添加活动剪影
                    <i class="el-icon-plus el-icon--right"></i>
                </el-button>
                <el-table :data="silhouettes.list" stripe style="width: 100%">
                    <%-- <el-table-column type="expand">
                        <template slot-scope="props">
                        </template>
                    </el-table-column> --%>
                    <el-table-column prop="title" label="主题"></el-table-column>
                    <el-table-column prop="photoCount" label="照片数" width="100">
                        <template slot-scope="scope">
                            <el-tag>{{scope.row.material.split(',').length}}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="addDate" label="制作时间" width="200">
                        <template slot-scope="scope">
                            {{new Date(scope.row.updateDate).toLocaleString()}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="operate" label="操作" width="120">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" @click="preview(scope.row)">预览</el-button>
                            <el-button type="text" size="small" @click="share(scope.row)">投屏展示</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-main>
        </el-container>
        <%-- 添加活动剪影 --%>
        <el-dialog title="制作活动剪影" :visible.sync="silhouette.show">
            <el-form :model="silhouette.data" :rules="silhouette.rules" ref="silhouetteForm" label-width="100px">
                <el-form-item label="主题" prop="title">
                    <el-input v-model="silhouette.data.title"></el-input>
                </el-form-item>
                <el-form-item label="选着照片" prop="photos">
                    <div class="material-box" v-for="photo in silhouette.data.photos">
                        <img :src="photo.coverUrl">
                        <div class="material-del">
                            <i class="el-icon-delete" @click="materialDelete(photo)"></i>
                        </div>
                    </div>
                    <div class="material-box material-add" @click="materialExplorerShow = true">
                        <i class="el-icon-plus"></i>
                    </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="resetSilhouette">重 置</el-button>
                <el-button type="primary" @click="commitSilhouette">确 定</el-button>
            </span>
        </el-dialog>
        <%-- 照片选择框 --%>
        <resource-material-explorer :display.sync="materialExplorerShow" title="素材选择" @submit="materialAdd"></resource-material-explorer>
    </div>
</body>
<script type="module">
    import serverConfig from '/environment/resourceUploadConfig.jsp'

     window.app = new Vue({
        el: '#app',
        data() {
            let photosCheck = (rule, value, callback) => {
                if(this.silhouette.data.photos.length <= 0) {
                    callback(new Error('请添加需要展示的照片'))
                }
                callback()
            }
            return {
                materialExplorerShow: false,
                silhouettes: {
                    total: 0,
                    pageSize: 20,
                    pageNum: 1,
                    list: []
                },
                silhouette: {
                    show: false,
                    maxCount: 30,
                    data: {
                        title: '',
                        photos: []
                    },
                    rules: {
                        title: [
                            { required: true, message: '请输入主题', trigger: 'blur' }
                        ],
                        photos: [
                            { validator: photosCheck, trigger: 'blur' }
                        ]
                    }
                }
            }
        },
        mounted() {
            this.loadSilhouettes(1, this.silhouettes.pageSize)
        },
        methods: {
            otherPage(pageNum) {
                this.loadSilhouettes(pageNum, this.silhouettes.pageSize)
            },
            preview(row) {
                window.open('/html/silhouette_preview.jsp?id=' + row.silhouetteId, 'template', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
            },
            resetSilhouette() {
                this.$refs.silhouetteForm.resetFields()
            },
            commitSilhouette() {
                this.$refs.silhouetteForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交 ? ', '确认提交', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            let material = this.silhouette.data.photos.map(photo => photo.uid).join(',')
                            let postData = {
                                activityId: 0,
                                title: this.silhouette.data.title,
                                material: material
                            }
                            let url = "/publish/activity/silhouette"
                            postJson(url, postData, reps => {
                                if (reps.status) {
                                    this.$message('制作成功')
                                    this.loadSilhouettes(1, this.silhouettes.pageSize)
                                } else {
                                    this.$message.error(reps.msg)
                                }
                            })
                            this.silhouette.show = false
                        }).catch(() => { })
                    } else {
                        return false;
                    }
                })
            },
            materialAdd(materials) {
                if(this.silhouette.data.photos.length > this.silhouette.maxCount) {
                    this.$alert('最多可以添加' + this.silhouette.maxCount + '张照片, 你可以删除之后在进行添加')
                    return;
                }
                let coverUrl
                materials.forEach(item => {
                    coverUrl = item.type.startsWith('audio') ? "/assets/img/timg.png" : serverConfig.getUrl(item.coverUrl)
                    this.silhouette.data.photos.push({
                        uid: item.materialId,
                        name: item.name,
                        type: item.type,
                        coverUrl: coverUrl,
                        url: serverConfig.getUrl(item.url),
                        content: item.content,
                        isFile: item.type == 'text' ? false : true
                    })
                })
            },
            materialDelete(material) {
                this.silhouette.data.photos.splice(this.silhouette.data.photos.indexOf(material), 1)
            },
            loadSilhouettes(pageNum, pageSize) {
                let url = '/publish/activity/silhouette/' + pageNum + '/' + pageSize
                get(url, reps => {
                    if(reps.status) {
                        this.silhouettes.total = reps.data.total
                        this.silhouettes.pageNum = reps.data.pageNum,
                        this.silhouettes.list = reps.data.list
                    } else {
                        this.$message.error(reps.msg)
                    }
                })
            },
            share(row) {
                let share = [{weburl: '/html/silhouette_preview.jsp?id=' + row.silhouetteId, playtime: 20}]
                let url = '/view/publish/new.jsp?title=活动剪影&startStep=2&url=' + encodeURIComponent(JSON.stringify(share))
                if (parent.addTab) {
                    parent.addTab({ menuId: -4, name: '活动剪影投屏', url: url })
                } else {
                    window.location.href = url
                }
            }
        }
    })

    function get(url, callback) {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            success: function (reps) {
                if (callback) callback(reps)
            },
            error: function (err) {
                app.$message.error('系统错误')
            }
        });
    }
    function postJson(url, postData, callback) {
        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            data: JSON.stringify(postData),
            contentType: 'application/json',
            success: function (reps) {
                if (callback) callback(reps)
            },
            error: function (err) {
                app.$message.error('系统错误')
            }
        });
    }
</script>

</html>