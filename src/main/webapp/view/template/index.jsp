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

    <title>模板管理</title>
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
                            <el-button type="success" icon="el-icon-plus" @click="addTemplate" size="small">新增模板</el-button>
                        </div>
                    </div>
                    <div   style="text-align: left;">
                        <el-form :inline="true" class="demo-form-inline">
                            <el-form-item label="搜索模板">
                                <el-input placeholder="搜索模板" v-model="keyword" ></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="searchTemplate">搜索</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-tree ref="tree" :data="tpt_data" :props="props" :highlight-current="true" node-key="id" default-expand-all :expand-on-click-node="false"
                        @node-click="tptTreeClick" class="menu-tree"  @node-contextmenu="treeContextmenu"> 
                    </el-tree>
                </el-aside>
                <el-main>
                    <div role="mainDis" v-show="!tp.visible" style="overflow: auto; padding-bottom:20px;">
                        <el-row>
                            <el-col :span="10">
                                <!--查询-->
                                <el-breadcrumb separator="/" style="margin-top: 10px;">
                                    <el-breadcrumb-item>所有类别</el-breadcrumb-item>
                                    <template v-for="item in breadcrumbData">
                                        <el-breadcrumb-item ><a @click="breadPathClick(item)">{{item.name}}</a></el-breadcrumb-item>
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
                        <template v-for="tp in tps">
                            <el-card class="passage-conver" :body-style="{ padding: '0px' }" shadow="never">
                                <div class="background-img" :style="{'background-image':'url('+getResUrl(tp.previewPicture)+')'}" @mouseenter="card_hover(tp)" @mouseleave="card_leave(tp)">
                                    <!--<img src="tp.previewPicture" class="image">-->
                                    <div class="control ">
                                            <span class="title">{{tp.title}}</span>
                                            <el-collapse-transition>
                                                <div v-show="tp.showtoolbar"  class="bottom clearfix">
                                                    <el-button-group>
                                                        <el-button type="success" size="small" icon="el-icon-view" @click="viewTemplate(tp)"></el-button>
                                                        <el-button type="primary" size="small" icon="el-icon-edit" @click="updateTemplate(tp)"></el-button>
                                                        <el-popover placement="top" width="160" v-model="tp.cfv">
                                                            <p>是否删除这个模板?</p>
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
                                        <el-form-item label="模板标题" prop="title">
                                            <el-row>
                                                <el-col :span="15">
                                                    <el-input v-model="tp.data.title"></el-input>
                                                </el-col>
                                            </el-row>
                                        </el-form-item>
                                        <el-form-item label="模板正文">
                                            <div class="editerContainer" id="templateText" type="textarea" style="height: 300px;"></div>
                                        </el-form-item>
                                        <el-form-item label="所属分类" prop="tpTypeIds">
                                            <el-cascader v-model="tp.data.tpTypeIds" :props="tpt_props" :options="tpt_data" :show-all-levels="false"></el-cascader>
                                        </el-form-item>
                                        <el-form-item label="节目模版Id" prop="programTemplate">
                                            <el-row>
                                                <el-col :span="5">
                                                    <el-input v-model="tp.data.programTemplate"></el-input>
                                                </el-col>
                                                <el-col :span="5">
                                                    <el-button style="margin-left: 15px;" type="button" @click="ptw.visiable=true">选择</el-button>
                                                </el-col>
                                            </el-row>
                                        </el-form-item>
                                        <el-form-item label="封面图片" prop="tpTypeIds">
                                            <el-upload class="avatar-uploader" :action="resource_server_url" :show-file-list="false" :on-success="handleAvatarSuccess"
                                                :before-upload="beforeAvatarUpload">
                                                <img v-if="tp.data.previewPicture" :src="getResUrl(tp.data.previewPicture)" class="avatar">
                                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                            </el-upload>
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
        <!--节目模版选择-->
        <el-dialog title="节目模版" :visible.sync="ptw.visiable" :fullscreen="true">
            <program-template @chose="choseProgramTemplate"></program-template>
        </el-dialog>
        <!--节目模版选择-->
        <!--Tree 右键菜单-->
        <context-menu :visiable.sync="contextMenu.visiable" :data="contextMenuData" :mouse-event="contextMenu.event" @click="contextMenuClick"></context-menu>
        <!--Tree 右键菜单-->
    </div>
</body>
</html>
<script type="module" charset="utf-8" src="${urls.getForLookupPath('/assets/module/template/template.js')}"></script>