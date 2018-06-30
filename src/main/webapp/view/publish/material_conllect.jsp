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
            <div id="toolbar">
                <el-row>
                    <el-col :span="24">
                        <el-menu class="el-menu-demo" mode="horizontal">
                            <el-menu-item index="-1" class="normal-menu-item" disabled>已结束活动 </el-menu-item>
                            <el-menu-item index="-3" class="normal-menu-item" disabled>
                                <el-button-group class="control-button">
                                    <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                                    <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                                </el-button-group>
                            </el-menu-item>
                            <el-menu-item index="-2" :style="{'float':'right'}" class="normal-menu-item" disabled>
                                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="activity.pageNum" :page-sizes="pageSizes"
                                    :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="activity.total">
                                </el-pagination>
                            </el-menu-item>
                        </el-menu>
                    </el-col>
                </el-row>
            </div>
        </el-header>
        <el-main>
            <div v-show="!dis_h_v">
                <template v-for="it in activity.list">
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
                            <el-button type="text" size="small" @click="collectMaterial">募集素材</el-button>
                        </div>
                    </el-card>
                </template>
            </div>
            <div v-show="dis_h_v">
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
            </div>
        </el-main>
        <el-footer>
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
            <el-upload action="/material/commonUpload" :show-file-list="false" list-type="picture-card" :on-success="handleUploadSuccess"
                :on-error="handleUploadError" :on-remove="handleUploadRemove">
                <i class="el-icon-plus"></i>
            </el-upload>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button size="mini" @click="material.show = false">取 消</el-button>
            <el-button size="mini" type="primary" @click="material.show = false">添 加</el-button>
        </div>
    </el-dialog>
</div>
</body>
</html>
<script src="${urls.getForLookupPath('/assets/module/publish/material_conllect.js')}"></script>