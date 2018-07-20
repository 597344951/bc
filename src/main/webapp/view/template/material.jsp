<%@ page contentType="text/html;charset=UTF-8" language="java"%>
    <%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <title>素材管理</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <%--ueditor--%>
    <%@include file="/include/ueditor.jsp"%>
<link href="${urls.getForLookupPath('/assets/module/template/template.css')}" rel="stylesheet">
</head>

<body >
    <div class="height_full" id="app" v-cloak>
        <el-container>
            <el-header>
                <div class="toolbar" style="display:flex;">
                    <div style="width: 550px;">
                        <div class="grid-content bg-purple">
                            <el-button type="success" icon="el-icon-plus" @click="addTemplateType" size="small">新增分类</el-button>
                            <el-button type="primary" icon="el-icon-edit" @click="updateTemplateType" size="small">修改分类</el-button>
                            <el-button type="danger" icon="el-icon-delete" @click="deleteTemplateType" size="small">删除分类</el-button>
                            <el-button type="success" icon="el-icon-plus" @click="addTemplate" size="small">上传素材</el-button>
                            <el-button type="success" icon="el-icon-upload" @click="importResources" size="small">导入素材</el-button>
                        </div>
                    </div>
                    <div style="text-align: left;margin-left: 30px;">
                        <el-form :inline="true" class="demo-form-inline">
                            <el-form-item label="搜索素材">
                                <el-input placeholder="搜索素材" v-model="keyword"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="searchTemplate">搜索</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div>
                        <div class="store-status">
                            <el-progress :text-inside="true" :stroke-width="18" :percentage="storeUsedPercent" status="success"></el-progress>
                            <span class="label-txt">{{storeInfo.used | byteToSize}}/{{storeInfo.total | byteToSize}}</span>
                        </div>
                    </div>
                </div>
            </el-header>
            <el-container>
                <el-aside width="250px">
                    <el-tree ref="tree" :data="tpt_data" :props="props" :highlight-current="true" node-key="id" default-expand-all :expand-on-click-node="false"
                        @node-click="tptTreeClick" class="menu-tree" @node-contextmenu="treeContextmenu">
                        <span class="custom-tree-node" slot-scope="{ node, data }">
                            <span class="left-label-group">
                                <i class="icon" v-if="data.data.icon" :class="data.data.icon"></i>
                                {{ node.label }}</span>
                        </span>
                    </el-tree>
                </el-aside>
                <el-main>
                    <div role="mainDis" v-show="!tp.visible" style="overflow: auto;">
                        <el-row>
                            <el-col :span="10">
                                <!--查询-->
                                <el-breadcrumb separator="/" style="margin-top: 10px;">
                                    <el-breadcrumb-item><a @click="breadPathClick({parent:0})">所有类别</a></el-breadcrumb-item>
                                    <template v-for="item in breadcrumbData">
                                        <el-breadcrumb-item >
                                            <a @click="breadPathClick(item)">{{item.name}}</a>
                                        </el-breadcrumb-item>
                                    </template>
                                </el-breadcrumb>
                            </el-col>
                            <el-col :span="14" style="text-align: right;">
                                <!--分页-->
                                <el-pagination style="margin:auto;" class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]" :page-size="tpager.size"
                                    layout="total, sizes, prev, pager, next, jumper" :total="tpager.total" @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange">
                                </el-pagination>
                            </el-col>
                        </el-row>
                        <div role="nextDirs" class="next-dirs">
                                <el-button icon="el-icon-back" circle style="float:left;" @click="backToBefore()"></el-button>
                                <template v-for="dir in nextDirs">
                                    <div class="dirs" @click="dir_click(dir.data)">
                                        <span class="title">{{dir.data.name}}</span>
                                    </div>
                                </template>
                        </div>
                        <template v-for="tp in tps">
                            <el-card class="passage-conver image-card" :body-style="{ padding: '0px' }">
                                <span class="verify-status">
                                    <el-tag class="not-verify" v-if="tp.verify == null" type="info" size="mini"></el-tag>
                                    <el-tag class="pass" v-if="tp.verify" type="success" size="mini"></el-tag>
                                    <el-tag class="not-pass" v-if="tp.verify == false" type="danger" size="mini"></el-tag>
                                </span>
                                <div class="background-img" @mouseenter="card_hover(tp)" @mouseleave="card_leave(tp)">
                                    <template v-if="tp.type == 'image' || tp.type == 'text'">
                                        <!--图片-->
                                        <img :src="getResUrl(tp.coverUrl) " class="image">
                                    </template>
                                    <template v-if="tp.type == 'audio'">
                                        <!--音频-->
                                        <span class="audio-icon"></span>
                                    </template>
                                    <template v-if="tp.type == 'video'">
                                        <!--视频-->
                                        <img :src="getResUrl(tp.coverUrl) " class="image">
                                        <span class="video-icon"></span>
                                    </template>
                                    <div class="control ">
                                        <span class="title">{{tp.name}}</span>
                                        <el-collapse-transition name="el-fade-in">
                                            <div v-show="tp.showtoolbar" class="bottom clearfix">
                                                <p class="descript">{{tp.description}}</p>
                                                <el-button-group>
                                                    <el-button type="success" size="small" icon="el-icon-view" @click="viewTemplate(tp)"></el-button>
                                                    <el-button type="primary" size="small" icon="el-icon-edit" @click="updateTemplate(tp)"></el-button>
                                                    <el-popover placement="top" width="160" v-model="tp.cfv">
                                                        <p>是否删除这个素材?</p>
                                                        <div style="text-align: right; margin: 0">
                                                            <el-button type="text" size="mini" @click="tp.cfv=false">取消</el-button>
                                                            <el-button type="danger" size="mini" @click="tp.cfv=false;delTemplate(tp)">确定</el-button>
                                                        </div>
                                                        <el-button type="danger" slot="reference" size="small" icon="el-icon-delete" @click="tp.cfv=true"></el-button>
                                                    </el-popover>
                                                </el-button-group>
                                            </div>
                                        </el-collapse-transition>
                                    </div>
                                </div>
                            </el-card>
                            </template>

                    </div>
                    <!-- 模板编辑框 -->
                    <transition name="el-zoom-in-bottom">
                        <el-row v-show="tp.visible">
                            <el-col :span="4" style="width:60px;">
                                <el-button type="primary" icon="el-icon-close" circle @click="tp.visible = false"></el-button>
                            </el-col>
                            <el-col :span="20">
                                <div class="editTp">
                                    <h2 style="margin-top: 5px;">{{tp.title}}</h2>
                                    <el-form ref="tpForm" :model="tp.data" :rules="rules" label-width="100px">
                                        <el-form-item label="素材标题" prop="name">
                                            <el-row>
                                                <el-col :span="15">
                                                    <el-input v-model="tp.data.name"></el-input>
                                                </el-col>
                                            </el-row>
                                        </el-form-item>
                                        <el-form-item label="描述信息" prop="description">
                                            <el-input type="textarea" :rows="3" v-model="tp.data.description" auto-complete="off"></el-input>
                                        </el-form-item>
                                        <el-form-item label="素材类型">
                                            <el-radio v-model="tp.data.type" :disabled="tp.data.typeDisable" label="image">图片</el-radio>
                                            <el-radio v-model="tp.data.type" :disabled="tp.data.typeDisable" label="audio">音频</el-radio>
                                            <el-radio v-model="tp.data.type" :disabled="tp.data.typeDisable" label="video">视频</el-radio>
                                            <el-radio v-model="tp.data.type" :disabled="tp.data.typeDisable" label="text">文字</el-radio>
                                            <el-radio v-model="tp.data.type" :disabled="tp.data.typeDisable" label="images">图片万花筒</el-radio>
                                        </el-form-item>
                                        <el-form-item label="素材正文" v-show="tp.data.type == 'text'">
                                            <div class="editerContainer" id="templateText" type="textarea" style="height: 300px;"></div>
                                        </el-form-item>
                                        <el-form-item label="素材截图" v-show="tp.data.type == 'text'">
                                            <el-upload class="avatar-uploader" :action="resource_server_url" :show-file-list="false" :on-success="handleAvatarSuccess"
                                                :before-upload="beforeAvatarUpload">
                                                <div style="display:flex;">
                                                    <img v-show="tp.data.coverUrl" :src="getResUrl(tp.data.coverUrl)" class="avatar">
                                                    <i class="el-icon-plus avatar-uploader-icon"></i>
                                                </div>
                                            </el-upload>
                                        </el-form-item>
                                        <el-form-item label="上传素材" v-show="tp.data.type != 'text' && tp.data.type != 'images'">
                                            <el-upload class="avatar-uploader" :action="resource_server_url" :show-file-list="false" :on-success="handleAvatarSuccess"
                                                :before-upload="beforeResourceUpload">
                                                <div style="display:flex;">
                                                    <img v-show="tp.data.coverUrl" :src="tp.data.type == 'audio' ? tp.data.coverUrl:getResUrl(tp.data.coverUrl)" class="avatar">
                                                    <i class="el-icon-plus avatar-uploader-icon"></i>
                                                </div>
                                            </el-upload>
                                        </el-form-item>
                                        <el-form-item label="上传素材" v-show="tp.data.type == 'images'">
                                            <div class="editerContainer" v-show="tp.m3Visible" style="height: 400px;">
                                                <div id="imagesContainer"></div>
                                            </div>
                                            <img v-show="!tp.m3Visible" :src="getResUrl(tp.data.coverUrl)" class="avatar" @click="openM3">
                                        </el-form-item>

                                        <el-form-item label="所属分类" prop="albumIds">
                                            <el-cascader v-model="tp.data.albumIds" :props="tpt_props" :options="tpt_data_normal" :show-all-levels="true" filterable
                                                change-on-select></el-cascader>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button @click="tp.visible = false">取 消</el-button>
                                            <el-button type="primary" @click="saveOrUpdateTemplate">确 定</el-button>
                                        </el-form-item>
                                    </el-form>
                                </div>
                            </el-col>
                        </el-row>
                    </transition>
                    <!-- 模板编辑框 -->
                </el-main>
            </el-container>
        </el-container>
        <!--对话框-->
        <!--模板类别 管理对话框-->
        <el-dialog :title="tpt.title" :visible.sync="tpt.visible">
            <el-form ref="tptForm" :model="tpt.data" :rules="rules" label-width="120px">
                <el-form-item label="类别名称" prop="name">
                    <el-input v-model="tpt.data.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="描述信息" prop="remark">
                    <el-input type="textarea" :rows="3" v-model="tpt.data.remark" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="排序序号" prop="orderNum">
                    <el-input type="text" v-model="tpt.data.orderNum"></el-input>
                </el-form-item>
                <el-form-item label="上一级目录" v-if="tpt.update != true">
                    <el-select v-model="tpt.data.parentLabel" placeholder="请选择">
                        <el-option
                          v-for="item in tpt_parents"
                          :key="item.parent"
                          :label="item.parentLabel"
                          :value="item.parentLabel">
                        </el-option>
                </el-select>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="tpt.visible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateTemplateType">确 定</el-button>
            </div>
        </el-dialog>
        <!--模板类别 管理对话框-->
        <!--导入资源-->
        <el-dialog :title="importResource.title" :visible.sync="importResource.visiable">
            <el-form ref="importResForm" :model="importResource.data" :rules="rules" label-width="120px">
                <el-form-item label="名称来源">
                    <el-radio v-model="importResource.nameType" label="fileName" @change="importResource.data.name=''">文件名</el-radio>
                    <el-radio v-model="importResource.nameType" label="input" @change="importResource.data.name=''">用户输入</el-radio>
                </el-form-item>
                <el-form-item label="素材标题" prop="name" v-if="importResource.nameType == 'input'">
                    <el-input v-model="importResource.data.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="素材描述">
                    <el-input type="textarea" :rows="3" v-model="importResource.data.description" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="所属分类" prop="albumIds">
                    <el-cascader v-model="importResource.data.albumIds" :props="tpt_props" :options="tpt_data_normal" :show-all-levels="true"></el-cascader>
                </el-form-item>
                <el-form-item label="选取文件">
                    <el-upload class="upload-demo" ref="upload" :action="getUploadUrl('file')" :on-preview="handlePreview" :on-success="handleSuccess"
                        :on-remove="handleRemove" :auto-upload="false" :multiple="true">
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                        <el-button style="margin-left: 10px;" size="small" type="danger" @click="clearChose">清空文件</el-button>
                        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传文件</el-button>
                        <el-checkbox style="margin-left: 10px;" v-model="importResource.commitOnUpload">上传完成后自动提交</el-checkbox>
                        <div slot="tip" class="el-upload__tip">上传文件大小不能超过200M</div>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="importResource.visiable = false">取 消</el-button>
                <el-button type="primary" @click="saveResources">确 定</el-button>
            </div>
        </el-dialog>
        <!--导入资源-->

        <!--Tree 右键菜单-->
        <context-menu :visiable.sync="contextMenu.visiable" :data="contextMenuData" :mouse-event="contextMenu.event" @click="contextMenuClick"></context-menu>
        <!--Tree 右键菜单-->
        <el-dialog class="resourceView" :title="resourceView.title" :visible.sync="resourceView.visible" @close="resourceViewClose">
            <template v-if="resourceView.type == 'video'">
                <video :src="getResUrl(resourceView.url)" controls="controls" class="videoView"></video>
            </template>
            <template v-if="resourceView.type == 'audio'">
                <audio :src="getResUrl(resourceView.url)" controls="controls" class="audioView">
                    Your browser does not support the audio element.
                </audio>
            </template>
            <template v-if="resourceView.type == 'image'">
                <img :src="getResUrl(resourceView.url) " class="imageView">
            </template>

        </el-dialog>
    </div>
</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/template/material.js')}"></script>
<%-- 美图开放API --%>
<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>