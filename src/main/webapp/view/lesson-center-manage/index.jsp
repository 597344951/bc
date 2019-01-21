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
    <%--ueditor--%>
    <%@include file="/include/ueditor.jsp"%>
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
                            <el-button type="primary" icon="el-icon-plus" @click="lessonUnitDialog.visible=true" size="small">新建课程</el-button>
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
                            <a class="item">
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
                                <div class="pandect">
                                    <el-button type="primary" size="mini" @click="editLessonUnit(lesson)">
                                        修改
                                    </el-button>
                                    <el-button type="danger" size="mini" @click="deleteLessonUnit(lesson)">
                                        删除
                                    </el-button>
                                </div>
                                <div class="pandect">
                                    <el-button v-if="lesson.learnerLimit == 1" size="mini" @click="orgLearnLimitInit(lesson)">组织配置</el-button>
                                    <el-button size="mini" @click="lessonSectionInit(lesson)">课程章节</el-button>
                                    <el-button size="mini" @click="lessonUnitTestInit(lesson)">考核方式</el-button>
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
            <el-button size="mini" v-if="!categoryTree || categoryTree.length==0" @click="newCategory({parent:0})">
                新增分类
            </el-button>
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
        <el-dialog :title="lessonUnitDialog.title" :visible.sync="lessonUnitDialog.visible" :fullscreen="true" @open="lessonUnitDialogOpened"
            append-to-body>
            <el-form ref="form" size="mini" :model="lessonUnitDialog.data" label-width="120px">
                <el-form-item label="课程名称">
                    <el-input v-model="lessonUnitDialog.data.name"></el-input>
                </el-form-item>
                <el-form-item label="考试资格">
                    <el-radio-group v-model="lessonUnitDialog.data.testThreshold">
                        <el-radio :label="0">不限制</el-radio>
                        <el-radio :label="1">限制学时</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="学时百分比" v-if="lessonUnitDialog.data.testThreshold == 1">
                    <el-input-number v-model="lessonUnitDialog.data.creditHoursPercent" :min="1" :max="100" label="描述文字"></el-input-number>
                </el-form-item>
                <el-form-item label="学习人员限定">
                    <el-radio-group v-model="lessonUnitDialog.data.learnerLimit">
                        <el-radio :label="0">不限制</el-radio>
                        <el-radio :label="1">限定指定组织</el-radio>
                    </el-radio-group>
                </el-form-item>
                <!--
                <el-form-item label="限定学习组织" v-if="lessonUnitDialog.data.learnerLimit == 1">
                    <el-tree ref="orgTree" :data="orgTreeData" :props="orgTreeProps"></el-tree>
                </el-form-item>
                -->
                <el-form-item label="所属目录">
                    <el-tree ref="lessonCategoryTree" :auto-expand-parent="true" :default-expanded-keys="lessonCategoryTreeDefaultExpanded"
                        :highlight-current="true" node-key="categoryId" :data="categoryTree" :props="defaultProps"></el-tree>
                </el-form-item>
                <el-form-item label="课程描述">
                    <div class="editerContainer" id="templateText" type="textarea" style="height: 300px;"></div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOrUpdateLessonUnit">确定</el-button>
                    <el-button @click="lessonUnitDialog.visible=false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog title="限定学习组织" :visible.sync="orgLearnLimitDialog.visible" append-to-body>
            <el-tree ref="orgTree" node-key="orgInfoId" :data="orgTreeData" :props="orgTreeProps" :default-checked-keys="orgLearnLimitDialog.defaultCheckedKeys"
                show-checkbox default-expand-all></el-tree>
            <el-button type="primary" @click="saveLearnLimit">确定</el-button>
            <el-button @click="orgLearnLimitDialog.visible=false">取消</el-button>
        </el-dialog>
        <el-dialog title="课程章节编辑" :visible.sync="lessonSectionDialog.visible" width="70%" append-to-body>
            <el-table size="mini" :data="lessonSectionDialog.lessonTree" style="font-size: 10px">
                <el-table-column type="expand">
                    <template slot-scope="scope">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>章节名</th>
                                    <th>增加时间</th>
                                    <th>学时</th>
                                    <th>排序</th>
                                    <th>素材类型</th>
                                    <th>控制</th>
                                </tr>
                            </thead>
                            <tbody>
                                <template v-for="ls in scope.row.children">
                                    <tr>
                                        <td>{{ls.name}}</td>
                                        <td>{{ls.addTime|datetime}}</td>
                                        <td>{{ls.creditHours}}分钟</td>
                                        <td>{{ls.sortNum}}</td>
                                        <td>{{ls.sourceType == 0 ? '内部素材':'外链素材'}}</td>
                                        <td>
                                            <el-button size="mini" @click="newSection(lessonSectionDialog.lessonUnit,ls)">新增</el-button>
                                            <el-button size="mini" @click="updateSection(lessonSectionDialog.lessonUnit,ls)">修改</el-button>
                                            <el-button size="mini" type="danger" @click="deleteSection(ls)">删除</el-button>
                                        </td>
                                    </tr>
                                </template>
                            </tbody>
                        </table>
                    </template>
                </el-table-column>
                <el-table-column label="章节名称" prop="name"></el-table-column>
                <el-table-column label="增加时间">
                    <template slot-scope="scope">
                        {{scope.row.addTime|datetime}}
                    </template>
                </el-table-column>
                <el-table-column label="控制">
                    <template slot="header" slot-scope="scope">
                        <el-button size="mini" @click="newSection(lessonSectionDialog.lessonUnit,scope.row)">新增</el-button>
                    </template>
                    <div slot-scope="scope">
                        <el-button size="mini" @click="newSection(lessonSectionDialog.lessonUnit,scope.row)">新增</el-button>
                        <el-button size="mini" @click="updateSection(lessonSectionDialog.lessonUnit,scope.row)">修改</el-button>
                        <el-button size="mini" type="danger" @click="deleteSection(scope.row)">删除</el-button>
                    </div>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog :title="lessonSectionEditorDialog.title" :visible.sync="lessonSectionEditorDialog.visible"
            append-to-body>
            <el-form ref="form" size="mini" :model="lessonSectionEditorDialog.data" label-width="120px">
                <el-form-item label="课程名称">
                    <el-input v-model="lessonSectionEditorDialog.data.name"></el-input>
                </el-form-item>
                <el-form-item label="章节类型">
                    <el-radio-group v-model="lessonSectionEditorDialog.data.sourceType">
                        <el-radio :label="2">包</el-radio>
                        <el-radio :label="0">内部素材</el-radio>
                        <el-radio :label="1">外链素材</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="外链地址输入" v-if="lessonSectionEditorDialog.data.sourceType == 1">
                    <el-input type="textarea" v-model="lessonSectionEditorDialog.data.sourceData"></el-input>
                </el-form-item>
                <el-form-item label="内部素材选择" v-if="lessonSectionEditorDialog.data.sourceType == 0">
                    <el-button @click="materialExplorer.visible=true">打开</el-button>
                    <el-tag v-for="s in materialExplorer.selecteds">{{s.name}}</el-tag>
                </el-form-item>
                <el-form-item label="学时" v-if="lessonSectionEditorDialog.data.sourceType != 2">
                    <el-input-number v-model="lessonSectionEditorDialog.data.creditHours" :min="1" :max="999" label="输入学时"></el-input-number>
                </el-form-item>
                <el-form-item label="课程描述">
                    <el-input type="textarea" v-model="lessonSectionEditorDialog.data.descript"></el-input>
                </el-form-item>
                <el-form-item label="排序序号">
                    <el-input-number v-model="lessonSectionEditorDialog.data.sortNum" :min="1" :max="999" label="排序序号"></el-input-number>
                </el-form-item>
                <el-form-item label="上一级" v-if="lessonSectionEditorDialog.data.sourceType != 2">
                    <el-select v-model="lessonSectionEditorDialog.data.parent" placeholder="">
                        <el-option v-for="item in parentLessonSections" :key="item.lessonId" :label="item.name" :value="item.lessonId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOrUpdateLessonSection">确定</el-button>
                    <el-button @click="lessonSectionEditorDialog.visible=false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog title="考核方式编辑" :visible.sync="lessonUnitTestDialog.visible" width="70%" append-to-body>
            <el-table size="mini" :data="lessonUnitTestDialog.testTypes" style="font-size: 10px">
                <el-table-column type="expand">
                    <template slot-scope="scope">
                        {{scope.row.descript}}
                    </template>
                </el-table-column>
                <el-table-column label="考试名称" prop="name"></el-table-column>
                <el-table-column label="控制">
                    <template slot="header" slot-scope="scope">
                        <el-button size="mini" @click="newLessonUnitTest()">新增</el-button>
                    </template>
                    <div slot-scope="scope">
                        <el-button size="mini" @click="newLessonUnitTest()">新增</el-button>
                        <el-button size="mini" @click="updateLessonUnitTest(scope.row)">修改</el-button>
                        <el-button size="mini" type="danger" @click="deleteLessonUnitTest(scope.row)">删除</el-button>
                    </div>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog :title="lessonUnitTestEditorDialog.title" :visible.sync="lessonUnitTestEditorDialog.visible"
            append-to-body>
            <el-form ref="form" size="mini" :model="lessonUnitTestEditorDialog.data" label-width="120px">
                <el-form-item label="课程名称">
                    <el-input v-model="lessonUnitTestEditorDialog.data.name"></el-input>
                </el-form-item>
                <el-form-item label="考核类型">
                    <el-radio-group v-model="lessonUnitTestEditorDialog.data.testType">
                        <el-radio :label="1">提交论文</el-radio>
                        <el-radio :label="2">参与在线考试</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="考核描述">
                    <el-input type="textarea" :rows="15" v-model="lessonUnitTestEditorDialog.data.descript"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOrUpdateLessonUnitTest">确定</el-button>
                    <el-button @click="lessonUnitTestEditorDialog.visible=false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--dialog-->

        <resource-material-explorer :display.sync="materialExplorer.visible" type="video" title="素材选择" @submit="param=>materialExplorer.selecteds=param"></resource-material-explorer>
    </div>
    <!--app end-->

</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/view/lesson-center-manage/lessonCenterManage.js')}"></script>