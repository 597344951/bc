<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <link rel="stylesheet" href="<%=path%>/../index.css">
    <script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
    <style>
        .el-row--flex{
            height: 48px;
        }
    </style>
</head>

<body>
    <div id="app">
        <el-tabs type="border-card" v-model="curTab" @tab-click="onTabClick">
            <el-tab-pane label="先进典型材料制作" name="photos">
                <el-container>
                    <el-main>
                        <el-form :model="wrap" label-width="100px">
                            <h3>先进典型页面制作</h3>
                            <el-form-item label="先进模板">
                                <el-button type="text" @click="effectSelecterShow = true">选择模板...</el-button>
                                <div class="wrap" @click="effectSelecterShow = true">
                                    <iframe v-if="wrap.url" frameborder="0" scrolling="no" :src="wrap.demoUrl"></iframe>
                                    <p v-if="!wrap.url">请先选择一个模板开始制作...</p>
                                </div>
                            </el-form-item>
                            <el-row type="flex" justify="center">
                                <el-col :span="12">
                                    <el-form-item label="主题名称">
                                        <el-input v-model="wrap.title" placeholder="请输入主题名称"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="人物简介">
                                        <el-input v-model="wrap.xx.x1" placeholder="请输入人物简介 (0~50)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row type="flex" justify="center" >
                                <el-col :span="12">
                                    <el-form-item label="姓名">
                                        <el-input v-model="wrap.xx.x2" placeholder="请输入姓名 (2~4)"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="职业">
                                        <el-input v-model="wrap.xx.x3" placeholder="请输入职业 (2~6)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :span="12">
                                    <el-form-item label="主要成就">
                                        <el-input v-model="wrap.xx.x4" placeholder="请输入主要成就 (0~20)"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="籍贯">
                                        <el-input v-model="wrap.xx.x5" placeholder="请输入籍贯 (2~4)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :span="12">
                                    <el-form-item label="经历标题1">
                                        <el-input v-model="wrap.xx.x6" placeholder="请输入经历标题1 (2~10)"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="经历标题2">
                                        <el-input v-model="wrap.xx.x7" placeholder="请输入经历标题2 (2~10)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :span="12">
                                    <el-form-item label="经历标题3">
                                        <el-input v-model="wrap.xx.x8" placeholder="请输入经历标题3 (2~10)"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="经历标题4">
                                        <el-input v-model="wrap.xx.x9" placeholder="请输入经历标题4 (2~10)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :span="12">
                                    <el-form-item label="经历内容1">
                                        <el-input v-model="wrap.xx.x10" placeholder="请输入经历内容1 (50~200)"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="经历内容2">
                                        <el-input v-model="wrap.xx.x11" placeholder="请输入经历内容2 (50~200)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :span="12">
                                    <el-form-item label="经历内容3">
                                        <el-input v-model="wrap.xx.x12" placeholder="请输入经历内容3 (50~200)"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="经历内容4">
                                        <el-input v-model="wrap.xx.x13" placeholder="请输入经历内容4 (50~200)"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-form-item label="展示图片">
                                <div v-if="effects.current && effects.current.tool == 'M2'">
                                    <div class="image-box" v-for="(photo, index) in wrap.photos" :key="index">
                                        <img :src="photo.thumbnail">
                                    </div>
                                    <div id="m2"></div>
                                </div>
                                <div v-else>
                                    <p>选择需要展示的图片(允许图片数量：{{wrap.minimum}} - {{wrap.maximum}})</p>
                                    <p> {{wrap.tips}}</p>
                                    <div class="image-box" v-for="(photo, index) in wrap.photos" v-dragging="{ list: wrap.photos, item: photo, group: 'photos' }"
                                        :key="index">
                                        <img :src="photo.thumbnail">
                                        <label class="upload-delete" @click="onRemove(photo)"><i class="el-icon-close"></i></label>
                                        <label class="upload-success"><i class="el-icon-check"></i></label>
                                    </div>
                                    <div class="image-box click-enable" @click="materialExplorerShow=true; curSelectedFrom='photos'">
                                        <!-- <el-upload multiple action="${mediaServe}/image" :show-file-list="false"
                                            :on-success="onSuccess" accept="image/*">
                                            <i class="el-icon-plus add"></i>
                                        </el-upload> -->
                                        <i class="el-icon-plus add"></i>
                                    </div>
                                </div>

                            </el-form-item>
                            <el-form-item>
                                <form id="wrapJsonStrForm" method="GET" action="/pw/prePreview" target="_blank">
                                    <input type="hidden" name="wrapJsonStr" v-model="wrapJsonStr" value="">
                                </form>
                                <el-button @click="onPrePreview">预览</el-button>
                                <el-button @click="onSubmit" type="primary">立即创建</el-button>
                            </el-form-item>
                        </el-form>
                    </el-main>
                </el-container>
            </el-tab-pane>

            <el-tab-pane label="已完成" name="list">
                <el-container>
                    <el-header>
                        <h3>已完成</h3>
                    </el-header>
                    <el-main>
                        <el-pagination class="pagination" background layout="prev, pager, next" :total="wraps.total"
                            :page-size="wraps.pageSize" @current-change="onCurrentPageChange">
                        </el-pagination>
                        <el-table :data="wraps.list" stripe>
                            <el-table-column prop="title" label="主题">
                            </el-table-column>
                            <el-table-column label="日期" width="360">
                                <template slot-scope="scope">
                                    {{new Date(scope.row.add_date).toLocaleString()}}
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="180">
                                <template slot-scope="scope">
                                    <el-button @click="onPreview(scope.row)" type="text" size="mini">预览</el-button>
                                    <el-button @click="onDelete(scope.row)" type="text" size="mini">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-main>
                </el-container>
            </el-tab-pane>
        </el-tabs>

        <el-dialog title="选择特效" :visible.sync="effectSelecterShow">
            <el-collapse v-model="effects.active" accordion>
                <el-collapse-item v-for="(group, index) in effects.groups" :title="group.title" :name="group.name" :key="index">
                    <div v-bind:class="{ 'cur': effects.current && effects.current.title == effect.title, 'not-cur': !effects.current || effects.current.title != effect.title }"
                        v-for="(effect, index) in group.list" :key="index" @click="onEffectClick(effect)">{{effect.title}}</div>
                </el-collapse-item>
            </el-collapse>
            <span slot="footer" class="dialog-footer">
                <el-button @click="effectSelecterShow = false">取 消</el-button>
                <el-button type="primary" @click="onEffectSelected">确 定</el-button>
            </span>
        </el-dialog>
        <resource-material-explorer :display.sync="materialExplorerShow" title="素材选择" @submit="onMaterialSelected"></resource-material-explorer>
    </div>
</body>
<script>
    const effectsFile = '<%=path%>/../effects.json'
    const titleThemeFile = '<%=path%>/../title-theme.json'
    const imageHost = '${mediaServe}'
    const module = '${module}'
</script>
<script src="<%=path%>/../vue-dragging.es5.js"></script>
<script src="<%=path%>/../index.js"></script>

</html>