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
<%@include file="/include/head.jsp"%>
<%--ueditor--%>
<%@include file="/include/ueditor.jsp"%>
<style>
.time {
	font-size: 13px;
	color: #999;
}

.image {
	width: 100%;
	display: block;
}

.bottom {
	margin-top: 13px;
	line-height: 12px;
}

.button {
	padding: 0;
	float: right;
}

.box-card {
	width: 200px;
	float: left;
	margin-left: 20px;
	margin-top: 20px;
}

.el-main ,.el-header{
	padding: 0;
}

.box-card-container {
	height: 200px;
}

/**页面预览**/
.page-thumb {
	transform: scale(0.36);
}

.editTp {
	width: 70%;
}
</style>
</head>
<body>
<div class="height_full" id="app">
    <el-container>
        <el-header>
            <el-row class="toolbar">
                <el-col :span="20">
                    <div class="grid-content bg-purple">
                        <el-button type="success" icon="el-icon-plus" @click="addTemplateType" size="small" >新增分类</el-button>
                        <el-button type="primary" icon="el-icon-edit" @click="updateTemplateType" size="small">修改分类</el-button>
                        <el-button type="danger" icon="el-icon-delete" @click="deleteTemplateType" size="small">删除分类</el-button>
                        <el-button type="success" icon="el-icon-plus" @click="addTemplate" size="small">新增模板</el-button>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-container>
            <el-aside width="200px">
                <el-tree ref="tree" :data="tpt_data" :props="props" :highlight-current="true" node-key="id" default-expand-all :expand-on-click-node="false" @node-click="tptTreeClick"> </el-tree>
            </el-aside>
            <el-main>
                <div role="mainDis" v-show="!tp.visible">
                    <template v-for="tp in tps">
                        <el-card class="box-card">
                            <div style="padding: 14px;">
                                <p>
                                    <b>{{tp.title}}</b>
                                </p>
                                <span></span>
                                <div class="bottom clearfix">
                                    <time class="time"></time>
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
                            </div>
                        </el-card>
                    </template>
                </div>
                <!-- 模板编辑框 -->
                 <transition name="el-zoom-in-bottom">
                <div class="editTp" v-show="tp.visible">
                    <h2>{{tp.title}}</h2>
                    <el-form ref="tpForm" :model="tp.data" :rules="rules" label-width="80px">
                        <el-form-item label="模板标题" prop="title">
                            <el-input v-model="tp.data.title"></el-input>
                        </el-form-item>
                        <el-form-item label="模板正文">
                            <div class="editerContainer" id="templateText" type="textarea" style="height: 300px;"></div>
                        </el-form-item>
                        <el-form-item label="所属分类" prop="tpTypeIds">
                            <el-cascader v-model="tp.data.tpTypeIds" :props="tpt_props" :options="tpt_data" :show-all-levels="false"></el-cascader>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="tp.visible = false">取 消</el-button>
                            <el-button type="primary" @click="saveOrUpdateTemplate">确 定</el-button>
                        </el-form-item>
                    </el-form>
                </div>
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
<script type="text/javascript" charset="utf-8" src="${urls.getForLookupPath('/assets/module/template/template.js')}"></script>