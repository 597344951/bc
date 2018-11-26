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

    <title>素材审核</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <%--ueditor--%>
    <%@include file="/include/ueditor.jsp"%>
</head>

<body >
    <div class="height_full" id="app" v-cloak>
        <el-container>
            <el-header>
                <div class="toolbar" style="display:flex;">
                    <div style="width: 550px;">
                        <div class="grid-content bg-purple">
                            <shiro:hasPermission name="resource:verify:verify">
                                <el-button type="success" size="small" icon="el-icon-check" @click="verifys(true)">通过</el-button>
                                <el-button type="danger" size="small" icon="el-icon-close" @click="verifys(false)">不通过</el-button>
                            </shiro:hasPermission>
                            <el-popover placement="right-start" width="400" trigger="click" style="margin-left:10px;">
                                <el-form ref="form" :model="queryForm" label-width="80px">
                                    <el-form-item label="资源名称">
                                        <el-input v-model="queryForm.keyword"></el-input>
                                    </el-form-item>
                                    <el-form-item label="审核状态">
                                        <el-select v-model="queryForm.verify" placeholder="请选择">
                                            <el-option v-for="item in verifyOptions" :key="item.value" :label="item.label" :value="item.value">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="searchTemplate">确定</el-button>
                                        <el-button @click="resetSearch">取消</el-button>
                                    </el-form-item>
                                </el-form>
                                <el-button slot="reference" type="primary" icon="el-icon-search" size="small">查询</el-button>
                            </el-popover>

                        </div>
                    </div>

                </div>
            </el-header>
            <el-container>
                <el-aside width="200px" class="hidden-if-mobile">
                    <el-tree ref="tree" :data="tpt_data" :props="props" :highlight-current="true" node-key="id" :expand-on-click-node="false"
                        @node-click="tptTreeClick" class="menu-tree" @node-contextmenu="treeContextmenu">
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
                                <el-breadcrumb separator="/" style="margin-top: 10px;">
                                    <el-breadcrumb-item>所有类别</el-breadcrumb-item>
                                    <template v-for="item in breadcrumbData">
                                        <el-breadcrumb-item>
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
                        <el-table ref="resourceTable" :data="tps" @selection-change="changeFun">
                            <el-table-column type="selection" width="55">
                            </el-table-column>
                            <el-table-column type="expand">
                                <template slot-scope="scope">
                                    <p>
                                        <span class="right-start-icon">
                                            <i class="el-icon-document"></i>资源名称 ： </span>{{scope.row.name}}</p>
                                    <p>
                                        <span class="right-start-icon">
                                            <i class="el-icon-document"></i> 文件类型 ： </span> {{scope.row.contentType}}</p>
                                    <p>
                                        <span class="right-start-icon">
                                            <i class="el-icon-goods"></i> 文件大小 ： </span> {{scope.row.size | byteToSize}}</p>
                                    <p>
                                        <span class="right-start-icon">
                                            <i class="el-icon-printer"></i> 文件长度 ： </span> {{scope.row.timeLength | secToMin}}</p>
                                    <p>
                                        <span class="right-start-icon">
                                            <i class="el-icon-edit-outline"></i> 资源描述 ： </span>
                                        {{scope.row.description}}
                                    </p>

                                </template>
                            </el-table-column>
                            <el-table-column label="名称" prop="name"></el-table-column>
                            <el-table-column label="所属专辑">
                                <template slot-scope="props">
                                    {{getAlbumName(props.row)}}
                                </template>
                            </el-table-column>
                            <el-table-column label="上传人员" prop="userId">
                                <template slot-scope="props">
                                    {{getUserName(props.row)}}
                                </template>
                            </el-table-column>
                            <el-table-column label="上传时间">
                                <template slot-scope="props">
                                    <p> {{props.row.addDate | date}}</p>
                                    <p> {{props.row.addDate | time}}</p>
                                </template>
                            </el-table-column>
                            <el-table-column label="审核状态" prop="verify">
                                <template slot-scope="props">
                                    <el-tag v-if="props.row.verify == null" type="info">未审核</el-tag>
                                    <el-tag v-if="props.row.verify" type="success">通过</el-tag>
                                    <el-tag v-if="props.row.verify == false" type="danger">未通过</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column label="审核时间">
                                <template slot-scope="props">
                                    <p> {{props.row.verifyDate | date}}</p>
                                    <p> {{props.row.verifyDate | time}}</p>
                                </template>
                            </el-table-column>
                            <el-table-column fixed="right" label="操作" width="100">
                                <template slot-scope="props">
                                    <el-button class="control-button" type="success" size="small" icon="el-icon-view" @click="viewTemplate(props.row)">查看</el-button><br/>
                                    <shiro:hasPermission name="resource:material:update">
                                        <el-button class="control-button" type="primary" size="small" icon="el-icon-edit" @click="updateTemplate(props.row)">编辑</el-button>
                                    </shiro:hasPermission>
                                </template>
                            </el-table-column>
                            <el-table-column fixed="right" label="审核" width="100">
                                <template slot-scope="scope">
                                    <shiro:hasPermission name="resource:verify:verify">
                                        <el-button class="control-button" type="success" size="small" icon="el-icon-check" @click="verify(scope.row,true)">通过</el-button><br/>
                                        <el-button class="control-button" type="danger" size="small" icon="el-icon-close" @click="verify(scope.row,false)">不通过</el-button>
                                    </shiro:hasPermission>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!---->
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
                    <el-cascader v-model="importResource.data.albumIds" :props="tpt_props" :options="tpt_data_normal" :show-all-levels="false"></el-cascader>
                </el-form-item>
                <el-form-item label="选取文件">
                    <el-upload class="upload-demo" ref="upload" :action="getUploadUrl('file')" :on-preview="handlePreview" :on-success="handleSuccess"
                        :on-remove="handleRemove" :auto-upload="false" :multiple="true">
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                        <el-button style="margin-left: 10px;" size="small" type="danger" @click="clearChose">清空文件</el-button>
                        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传文件</el-button>
                        <el-checkbox style="margin-left: 10px;" v-model="importResource.commitOnUpload">上传完成后自动提交</el-checkbox>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
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
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/template/material-verify.js')}"></script>