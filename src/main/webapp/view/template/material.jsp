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

<body style="min-width:1100px;">
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
                        </div>
                    </div>
                    <div style="text-align: left;">
                        <el-form :inline="true" class="demo-form-inline">
                            <el-form-item label="搜索模板">
                                <el-input placeholder="搜索模板" v-model="keyword"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="searchTemplate">搜索</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </el-header>
            <el-container>
                <el-aside width="300px">
                    <el-tree ref="tree" :data="tpt_data" :props="props" :highlight-current="true" node-key="id" default-expand-all :expand-on-click-node="false"
                        @node-click="tptTreeClick" class="menu-tree" @current-change="currentChange">
                        <span class="custom-tree-node" slot-scope="{ node, data }">
                            <span class="left-label-group">
                                <i class="icon" v-if="data.data.icon" :class="data.data.icon"></i> 
                                {{ node.label }}</span>
                            <span class="right-btn-group" v-show="node.showtoolbar">
                                <!---->
                                    <el-button-group>
                                        <el-button type="primary" size="mini" icon="el-icon-edit"></el-button>
                                        <el-button type="primary" size="mini" icon="el-icon-share"></el-button>
                                        <el-button type="primary" size="mini" icon="el-icon-delete"></el-button>
                                    </el-button-group>
                            </span>
                        </span>
                    </el-tree>
                </el-aside>
                <el-main>
                    <div role="mainDis" v-show="!tp.visible" style="overflow: auto;">
                        <el-row>
                            <el-col :span="10">
                                <!--查询-->
                                <span style="font-size:18px;font-weight:bolder;">{{currentCategory.name}}</span>
                            </el-col>
                            <el-col :span="14" style="text-align: right;">
                                <!--分页-->
                                <el-pagination style="margin:auto;" class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]" :page-size="tpager.size"
                                    layout="total, sizes, prev, pager, next, jumper" :total="tpager.total" @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange">
                                </el-pagination>
                            </el-col>
                        </el-row>
                        <template v-for="tp in tps">
                            <el-card class="passage-conver image-card" :body-style="{ padding: '0px' }">
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
                                        <transition name="el-fade-in">
                                            <div v-show="tp.showtoolbar" class="bottom clearfix">
                                                <p class="descript">{{tp.description}}</p>
                                                <el-button-group>
                                                    <template v-if="tp.type == 'text'">
                                                        <el-button type="success" size="small" icon="el-icon-view" @click="viewTemplate(tp)"></el-button>
                                                    </template>
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
                                        </transition>
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
                                        <el-form-item label="上传素材" v-show="tp.data.type != 'text'">
                                            <el-upload class="avatar-uploader" :action="resource_server_url" :show-file-list="false" :on-success="handleAvatarSuccess"
                                                :before-upload="beforeResourceUpload">
                                                <div style="display:flex;">
                                                    <img v-show="tp.data.coverUrl" :src="tp.data.type == 'audio' ? tp.data.coverUrl:getResUrl(tp.data.coverUrl)" class="avatar">
                                                    <i class="el-icon-plus avatar-uploader-icon"></i>
                                                </div>
                                            </el-upload>
                                        </el-form-item>

                                        <el-form-item label="所属分类" prop="albumIds">
                                            <el-cascader v-model="tp.data.albumIds" :props="tpt_props" :options="tpt_data_normal" :show-all-levels="false"></el-cascader>
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
                    <el-input aria-readonly="true" v-model="tpt.data.parentLabel"></el-input>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="tpt.visible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateTemplateType">确 定</el-button>
            </div>
        </el-dialog>
        <!--模板类别 管理对话框-->

    </div>
</body>

</html>
<script  charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/template/material.js')}"></script>