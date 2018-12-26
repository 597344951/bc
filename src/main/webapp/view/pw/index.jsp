<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <title>Wrap Creator...</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <link rel="stylesheet" href="<%=path%>/../index.css">
    <script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
</head>

<body>
    <div id="app">
        <el-tabs type="border-card" v-model="curTab" @tab-click="onTabClick">
            <el-tab-pane label="图片特效制作" name="photos">
                <el-container>
                    <el-main>
                        <el-form :model="wrap" label-width="100px">
                            <h3>图片展示+特效</h3>
                            <el-form-item label="轮播特效">
                                <el-button type="text" @click="effectSelecterShow = true">选择特效...</el-button>
                                <div class="wrap" @click="effectSelecterShow = true">
                                    <iframe v-if="wrap.url" frameborder="0" scrolling="no" :src="wrap.demoUrl"></iframe>
                                    <p v-if="!wrap.url">请先选择一个特效模板开始制作...</p>
                                </div>

                            </el-form-item>
                            <el-form-item label="主题名称">
                                <el-input v-model="wrap.title" placeholder="请输入主题名称"></el-input>

                            </el-form-item>
                            <el-form-item label="主题模板">
                                <el-button type="text" @click="titleThemeSelecterShow = true">选择主题模版...</el-button>
                                <br />
                                <div v-if="wrap.titleTheme" class="image-box">
                                    <img :src="curTitleTheme">
                                </div>
                            </el-form-item>
                            <el-form-item label="背景颜色">
                                <el-color-picker v-model="wrap.backgroundColor" show-alpha></el-color-picker>
                            </el-form-item>
                            <el-form-item label="背景图片">
                                <el-button type="text" @click="materialExplorerShow=true; curSelectedFrom='background'">选择背景图片...</el-button>
                                <div class="image-box" v-if="wrap.backgroundImageCover">
                                    <img :src="wrap.backgroundImageCover">
                                </div>
                                <!-- <el-upload action="${mediaServe}/image" :on-success="onBackgroundImageSuccess"
                                    :on-remove="onBackgroundImageRemove" :limit="1" accept="image/*">
                                    <el-button type="text">选择背景图片...</el-button>
                                </el-upload> -->
                            </el-form-item>
                            <el-form-item label="背景音乐">
                                <el-button type="text" @click="materialExplorerShow=true; curSelectedFrom='backgroundMusic'">选择背景音乐...</el-button>
                                <div class="el-upload__tip">只能上传mp3文件...</div>
                                <div class="image-box" v-if="wrap.backgroundMusicCover">
                                    <img :src="wrap.backgroundMusicCover" onerror="this.src='/assets/img/timg.png'">
                                </div>

                                <!-- <el-upload action="${mediaServe}/video" :on-success="onBackgroundMusicSuccess"
                                    :on-remove="onBackgroundMusicRemove" :limit="1" accept="audio/*">
                                    <el-button type="text">选择背景音乐...</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传mp3文件...</div>
                                </el-upload> -->
                            </el-form-item>
                            <el-form-item label="展示图片">
                                <div v-show="effects.current && effects.current.tool == 'M2'">
                                    <div class="image-box" v-for="(photo, index) in wrap.photos" :key="index">
                                        <img :src="photo.thumbnail">
                                    </div>
                                    <div style="width: 800px; height: 600px">
                                        <div id="m2"></div>
                                    </div>
                                </div>
                                <div v-show="effects.current && !effects.current.tool">
                                    <p>选择需要展示的图片(允许图片数量：{{wrap.minimum}} - {{wrap.maximum}})</p>
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
            <el-tab-pane label="视频连缀" name="videos">
                <el-container>
                    <el-main>
                        <el-form :model="wrap" label-width="100px">
                            <h3>视频连缀</h3>
                            <el-form-item label="主题名称">
                                <el-input v-model="wrap.title" placeholder="请输入主题名称"></el-input>
                            </el-form-item>
                            <el-form-item label="连播视频">
                                <p>选择需要连播的视频[.mp4](允许视频数量：{{wrap.minimum}} - {{wrap.maximum}})</p>
                                <div>
                                    <div class="image-box" v-for="(photo, index) in wrap.photos" v-dragging="{ list: wrap.photos, item: photo, group: 'photos' }"
                                        :key="index">
                                        <img :src="photo.thumbnail">
                                        <label class="upload-delete" @click="onRemove(photo)"><i class="el-icon-close"></i></label>
                                        <label class="upload-success"><i class="el-icon-check"></i></label>
                                    </div>
                                    <div class="image-box click-enable" @click="materialExplorerShow=true; curSelectedFrom='photos'">
                                        <!-- <el-upload multiple action="${mediaServe}/video" :show-file-list="false"
                                            :on-success="onSuccess" accept="video/*">
                                            <i class="el-icon-plus add"></i>
                                        </el-upload> -->
                                        <i class="el-icon-plus add"></i>
                                    </div>
                                </div>
                            </el-form-item>
                            <el-form-item>
                                <form id="wrapJsonStrForm" method="POST" action="/pw/prePreview" target="_blank">
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
                                    <el-button @click="onPreview(scope.row)" type="text" size="small">预览</el-button>
                                    <el-button @click="onDelete(scope.row)" type="text" size="small">删除</el-button>
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
        <el-dialog title="选择主题模板" :visible.sync="titleThemeSelecterShow">
            <div class="title-theme" v-for="theme in titleThemes" :key="theme.name" @click="onTitleThemeSelected(theme)">
                <img :src="theme.thumbnail">
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="titleThemeSelecterShow = false">取 消</el-button>
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