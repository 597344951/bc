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

    <title>学习中心管理后台</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <link href="${urls.getForLookupPath('/assets/module/terminal/terminal-control.css')}" rel="stylesheet">
    <link href="${urls.getForLookupPath('/assets/module/poster/posterTemplate.css')}" rel="stylesheet">
    <link href="${urls.getForLookupPath('/assets/module/learn-center/style.css')}" rel="stylesheet">
</head>

<body>
    <div class="app-main" id="app" v-cloak>
        <el-container>
            <el-header>
                <div class="toolbar" style="display:flex;">
                    <div>
                        <div class="grid-content bg-purple">
                            <el-button type="primary" icon="el-icon-edit" @click="editCategoryVisible=true" size="small">修改分类</el-button>
                        </div>
                    </div>
                    <div style="margin-left:50px;" class="hidden-if-mobile">

                    </div>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-tree ref="tree" :data="categoryTree" node-key="categoryId" :props="defaultProps"
                        :highlight-current="true" default-expand-all :expand-on-click-node="true" @node-click="tptTreeClick"
                        class="menu-tree">
                    </el-tree>
                </el-aside>
                <el-main>
                    <div class="contain">
                        <template v-for="lesson in lessons">
                            <a class="item" @click="openLessonUnit(lesson)">
                                <div class="cover">
                                    <img src="view/learn-center/img/01.png" alt="">
                                    <p>总共 {{lesson.lessonList.length}} 小节 , <span>总时长：
                                            {{sumTotalTime(lesson)}} 分钟</span></p>
                                </div>
                                <p class="title">{{lesson.name}}</p>
                                <div class="pandect">
                                    <span class="student">学习限制:
                                        <template v-if="lesson.learnerLimit == 0">
                                            <i class="fa fa-unlock margin-left-10"></i> 无限制
                                        </template>
                                        <template v-if="lesson.learnerLimit == 1">
                                            <i class="fa fa-lock margin-left-10 "></i> 限定组织
                                        </template>
                                    </span>
                                </div>
                                <div class="pandect">
                                    <span class="student">创建时间: {{lesson.addTime | date}}</span>
                                    <span class="teacher">讲师：吴景明</span>
                                </div>
                            </a>
                        </template>
                    </div>
                    <div role="pager">
                        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :current-page="pager.pageNum" :page-sizes="[15, 30, 60, 100]" :page-size="pager.pageSize"
                            layout="total, sizes, prev, pager, next, jumper" :total="pager.total">
                        </el-pagination>
                    </div>
                </el-main>
            </el-container>
        </el-container>
        <!--dialog-->
        <el-dialog title="修改分类" :visible.sync="editCategoryVisible">
            <el-tree :data="categoryTree" node-key="categoryId" ref="tree" :props="defaultProps">
                <span class="custom-tree-node" slot-scope="{ node, data }">
                    <span class="node-label">{{ node.label }}</span>
                    <span style="margin-left:50px;">
                        <el-button size="mini" @click="newCategory(data)">
                            新增
                        </el-button>
                        <el-button type="primary" size="mini" @click="editCategory(data)">
                            修改
                        </el-button>
                        <el-button type="danger" size="mini" @click="deleteCategory(data)">
                            删除
                        </el-button>
                    </span>
                </span>
            </el-tree>
            <el-dialog :title="posterCategoryDialog.title" :visible.sync="posterCategoryDialog.visible" width="440px"
                append-to-body>
                <el-form ref="form" size="mini" :model="posterCategoryDialog.data" label-width="80px">
                    <el-form-item label="分类名称">
                        <el-input v-model="posterCategoryDialog.data.name"></el-input>
                    </el-form-item>
                    <el-form-item label="排序序号">
                        <el-input v-model="posterCategoryDialog.data.orderNum"></el-input>
                    </el-form-item>
                    <el-form-item label="上一级">
                        <el-tree ref="categoryTree" :data="allCategoryTree" :props="defaultProps"></el-tree>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveOrUpdateCategory">确定</el-button>
                        <el-button @click="posterCategoryDialog.visible=false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </el-dialog>
        <!--dialog-->
    </div>
    <!--app end-->

</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/view/lesson-center-manage/lessonCenterManage.js')}"></script>