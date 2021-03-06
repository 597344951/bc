<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <title>报告管理</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <%--ueditor--%>
    <%@include file="/include/ueditor.jsp"%>
    <style>
    #edui1,#edui1_iframeholder{
        width:100%!important;
    }
    .el-dialog{
        width: 80%!important;
    }
    .el-button+.el-button {
        margin-left: 0px;
    }
    .el-button--small, .el-button--small.is-round {
        padding: 9px 6px;
    }
    </style>
</head>
<body  >
<div class="height_full" id="app" v-cloak>
    <div>
        <div>
            <el-row class="toolbar" style="padding:10px;">
                <el-col :span="14" class="full-width-if-mobile">
                    <div class="grid-content bg-purple">
                        <shiro:hasPermission name="report:type:save">
                            <el-button type="success" icon="el-icon-plus" @click="addTemplateType" size="small">新增分类</el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:type:update">
                            <el-button type="primary" icon="el-icon-edit" @click="updateTemplateType" size="small">修改分类</el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:type:delete">
                            <el-button type="danger" icon="el-icon-delete" @click="deleteTemplateType" size="small">删除分类</el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:template:save">
                            <el-button type="success" icon="el-icon-plus" @click="addTemplate" size="small">新建模版</el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:template:save">
                            <el-button type="success" icon="el-icon-upload" @click="importResources" size="small">导入模版</el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:submit:save">
                            <el-button type="success" icon="el-icon-plus" @click="editReportShow()" size="small">新建空白报告</el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:submit:query">
                            <el-button type="primary" icon="el-icon-document" @click="openSubmitReport" size="small">已提交报告</el-button>
                        </shiro:hasPermission>
                    </div>
                </el-col>
                <el-col :span="8" class="hidden-if-mobile">
                    <div>
                        <el-form :inline="true" class="no-margin-form demo-form-inline">
                            <el-form-item label="搜索模版">
                                <el-input placeholder="搜索模版" v-model="keyword"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="searchTemplate">搜索</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-col>
                <el-col :span="2"></el-col>
            </el-row>
        </div>
        <el-container>
            <el-aside width="200px">
                <el-tree ref="tree" :data="tpt_data" :props="props" :highlight-current="true" node-key="id"  :expand-on-click-node="true"
                    @node-click="tptTreeClick" class="menu-tree" @node-contextmenu="treeContextmenu" draggable
                    @node-drop="treeDrapDrop" :allow-drag="allowDrag">
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
                        <el-col :span="10" class="full-width-if-mobile">
                            <!--查询-->
                            <el-breadcrumb separator="/" style="margin-top: 10px;">
                                <el-breadcrumb-item>所有类别</el-breadcrumb-item>
                                <template v-for="item in breadcrumbData">
                                    <el-breadcrumb-item>
                                        <a @click="breadPathClick(item)">{{item.name}}</a>
                                    </el-breadcrumb-item>
                                </template>
                            </el-breadcrumb>
                        </el-col>
                        <el-col :span="14" style="text-align: right;" class="full-width-if-mobile">
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
                                <!--图片-->
                                <img src="/assets/img/Docs_740px_1141790_easyicon.net.png" class="image">
                                <div class="control ">
                                    <span class="title">{{tp.title}}</span>
                                    <el-collapse-transition name="el-fade-in">
                                        <div v-show="tp.showtoolbar" class="bottom clearfix">
                                            <p class="descript">{{tp.description}}</p>
                                            <el-button-group>
                                                <el-button type="success" size="small" icon="el-icon-view" @click="viewTemplate(tp)"></el-button>
                                                <shiro:hasPermission name="report:template:update">
                                                    <el-button type="primary" size="small" icon="el-icon-edit" @click="updateTemplate(tp)"></el-button>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="report:template:delete">
                                                    <el-popover placement="top" width="160" v-model="tp.cfv">
                                                        <p>是否删除这个素材?</p>
                                                        <div style="text-align: right; margin: 0">
                                                            <el-button type="text" size="mini" @click="tp.cfv=false">取消</el-button>
                                                            <el-button type="danger" size="mini" @click="tp.cfv=false;delTemplate(tp)">确定</el-button>
                                                        </div>
                                                        <el-button type="danger" slot="reference" size="small" icon="el-icon-delete" @click="tp.cfv=true"></el-button>
                                                    </el-popover>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="report:submit:save">
                                                    <el-button type="primary" size="small" icon="el-icon-document" @click="editReportShow(tp)"></el-button>
                                                </shiro:hasPermission>
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
                        <el-col :span="2" style="width:60px;">
                            <el-button type="primary" icon="el-icon-close" circle @click="tpClose"></el-button>
                        </el-col>
                        <el-col :span="22">
                            <div class="editTp">
                                <h2 style="margin-top: 5px;">{{tp.title}}</h2>
                                <el-form ref="tpForm" :model="tp.data" :rules="rules" label-width="100px">
                                    <el-form-item label="标题" prop="title">
                                        <el-row>
                                            <el-col :span="15">
                                                <el-input v-model="tp.data.title"></el-input>
                                            </el-col>
                                        </el-row>
                                    </el-form-item>
                                    <el-form-item label="正文">
                                        <div class="editerContainer" id="templateText" type="textarea" style="height: 400px;"></div>
                                    </el-form-item>
                                    <el-form-item label="分类" prop="albumIds">
                                        <el-cascader v-model="tp.data.albumIds" :props="tpt_props" :options="tpt_data_normal" :show-all-levels="true"></el-cascader>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button @click="tp.visible = false">取 消</el-button>
                                        <el-button type="primary" @click="saveOrUpdateTemplate">{{tp.yesBtnLabel}}</el-button>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                </transition>
                <!-- 模板编辑框 -->
            </el-main>
        </el-container>
    </div>
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
                    <el-option v-for="item in tpt_parents" :key="item.parent" :label="item.parentLabel" :value="item.parentLabel">
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
    <el-dialog :title="importResource.title" :visible.sync="importResource.visiable" width="40%" @close="importResourceClose">
        <el-form ref="importResForm" :model="importResource.data" :rules="rules" label-width="120px">
            <el-form-item label="名称来源">
                <el-radio v-model="importResource.nameType" label="fileName" @change="importResource.data.name=''">文件名</el-radio>
                <el-radio v-model="importResource.nameType" label="input" @change="importResource.data.name=''">用户输入</el-radio>
            </el-form-item>
            <el-form-item label="素材标题" prop="name" v-if="importResource.nameType == 'input'">
                <el-input v-model="importResource.data.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="所属分类" prop="albumIds">
                <el-cascader v-model="importResource.data.albumIds" @change="albumIdsChange" :props="tpt_props" :options="tpt_data_normal"
                    :show-all-levels="false"></el-cascader>
            </el-form-item>
            <el-form-item label="选取文件">
                <el-upload class="upload-demo" ref="upload" action="/report/template/import" :on-preview="handlePreview" :on-success="handleSuccess"
                    :on-remove="handleRemove" :auto-upload="false" :multiple="true" :data="importResource.data" :on-error="handleOnError"
                    :before-upload="handleBeforeUpload" accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="danger" @click="clearChose">清空文件</el-button>
                    <div slot="tip" class="el-upload__tip">只能导入word文件</div>
                </el-upload>
            </el-form-item>
        </el-form>
        <div class="dialog-footer" slot="footer">
            <el-button @click="importResource.visiable = false">取 消</el-button>
            <el-button style="margin-left: 10px;" size="small" type="primary" @click="submitUpload">上传文件</el-button>
        </div>
    </el-dialog>
    <!--导入资源-->

    <!--Tree 右键菜单-->
    <context-menu ref="treeContextMenu" :visiable.sync="contextMenu.visiable" :data="contextMenuData" :mouse-event="contextMenu.event"
        @click="contextMenuClick"></context-menu>
    <!--Tree 右键菜单-->
    <el-dialog class="templateView" :title="resourceView.title" :visible.sync="resourceView.visible" width="80%">
        <div v-html="resourceView.content"></div>
    </el-dialog>

    <el-dialog title="已提交报告" :visible.sync="submitedReport.visible" class="submitReport">
        <el-pagination style="margin:auto;" class="pagebar" :current-page="crpager.current" :page-sizes="[10, 20, 30]" :page-size="crpager.size"
            layout="total, sizes, prev, pager, next, jumper" :total="crpager.total" @size-change="crSizeChange" @current-change="crCurrentChange">
        </el-pagination>
        <el-table :data="submitedReport.data">
            <el-table-column prop="title" label="报告标题">
            </el-table-column>
            <el-table-column label="分类">
                <template slot-scope="scope">
                    {{ getTypeName(scope.row) }}
                </template>
            </el-table-column>
            <el-table-column label="提交时间">
                <template slot-scope="scope">
                    {{ scope.row.createtime|datetime }}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button type="success" size="small" icon="el-icon-view" @click="viewTemplate(scope.row)"></el-button>
                        <shiro:hasPermission name="report:submit:update">
                            <el-button type="primary" size="small" icon="el-icon-edit" @click="updateReport(scope.row)"></el-button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:submit:delete">
                            <el-button type="danger" size="small" icon="el-icon-delete" @click="delReport(scope.row)"></el-button>
                        </shiro:hasPermission>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
    </el-dialog>
</div>
</body>

</html>
<script>
 let menuData = [];
<shiro:hasPermission name="report:type:save">
    menuData.push({ label: '增加分类', icon: 'el-icon-plus', type: 'success' });
</shiro:hasPermission>
<shiro:hasPermission name="report:type:update">
    menuData.push( { label: '修改分类', icon: 'el-icon-edit', type: 'primary' });
</shiro:hasPermission>
<shiro:hasPermission name="report:type:delete">
    menuData.push( { label: '删除分类', icon: 'el-icon-delete', type: 'danger' });
</shiro:hasPermission>
window.menuData = menuData;
</script>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/report/report.js')}"></script>