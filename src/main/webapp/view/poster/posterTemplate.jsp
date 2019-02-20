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

	<title>海报模版</title>
	<%@include file="/include/head.jsp"%>
	<%--ueditor--%>
	<%@include file="/include/ueditor.jsp"%>
	<script src="${urls.getForLookupPath('/assets/js/vue-waterfall.js')}"></script>
	<link href="${urls.getForLookupPath('/assets/module/terminal/terminal-control.css')}" rel="stylesheet">
	<link href="${urls.getForLookupPath('/assets/module/poster/posterTemplate.css')}" rel="stylesheet">
</head>

<body>
    <div id="app" v-cloak>
        <!--
		<div class="templets-filter-list">
			<span>行业: </span>
			<el-radio-group v-model="filter.category" size="mini" @change="doSearch">
				<el-radio-button label="-1">所有</el-radio-button> 
				<template v-for="ps in category">
					<el-radio-button :label="ps.categoryId">{{ps.name}}</el-radio-button>
				</template>
			</el-radio-group>
		</div>
		-->
        <div class="templets-filter-list">
            <span>用途: </span>
            <el-radio-group v-model="filter.useCategory" size="mini" @change="doSearch">
                <el-radio-button label="0">所有</el-radio-button>
                <template v-for="ps in useCategory">
                    <el-radio-button :label="ps.categoryId">{{ps.name}}</el-radio-button>
                </template>
                <el-radio-button label="-1">其他用途</el-radio-button>
                <el-button type="primary" style="margin-left:20px;" size="mini" icon="el-icon-edit" circle @click="editPosterCategoryVisible=true"></el-button>
            </el-radio-group>
        </div>
        <div class="templets-filter-list" v-show="secondCategory.length > 0">
            <span>详细分类: </span>
            <el-radio-group v-model="filter.useCategory2" size="mini" @change="doSearch">
                <template v-for="ps in secondCategory">
                    <el-radio-button :label="ps.categoryId">{{ps.name}}</el-radio-button>
                </template>
            </el-radio-group>
        </div>
        <div class="templets-filter-list">
            <span>屏幕: </span>
            <el-radio-group v-model="filter.h_v" size="mini" @change="doSearch">
                <el-radio-button label="all">所有</el-radio-button>
                <el-radio-button label="h">横屏</el-radio-button>
                <el-radio-button label="v">竖屏</el-radio-button>
            </el-radio-group>
        </div>
        <div class="templets-filter-list">
            <span>尺寸: </span>
            <el-radio-group v-model="filter.sizeType" size="mini" @change="doSearch">
                <el-radio-button label="-1">所有</el-radio-button>
                <template v-for="ps in filterPosterSize">
                    <el-radio-button :label="ps.id">{{ps.tag}}</el-radio-button>
                </template>
            </el-radio-group>
        </div>
        <div class="templets-filter-list">
            <span>关键字: </span>
            <el-form :inline="true" :model="filter" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="filter.keyword" placeholder="关键字"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="doSearch">查询</el-button>
                    <el-button @click="replaceMetaDataDialog.visible=true">替换</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-pagination background style="margin:auto;" class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]"
                :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper" :total="tpager.total"
                @size-change="handleSizeChange" @current-change="handleCurrentChange"> </el-pagination>

        </div>
        <div style="overflow: auto;">
            <waterfall :line="displayType" //宽度 :line-gap="240" :min-line-gap="150" :max-line-gap="250" //各个组件之间的间距
                :gap="20" :padding="10" //水平工作模式时 最大宽度 :single-max-width="300" center="center" //是否修正高度 :fixed-height="false"
                :watch="posterInfos">
                <!-- each component is wrapped by a waterfall slot -->
                <waterfall-slot :width="217" :height="385">
                    <div class="img-box" style="cursor: pointer;" @click="newPoster">
                        <img src="/assets/img/newPoster.png" style="width:100%;height:100%;">
                        <el-tooltip class="item" effect="dark" content="新建海报" placement="bottom">
                            <p>新建海报</p>
                        </el-tooltip>
                    </div>
                </waterfall-slot>
                <waterfall-slot v-for="(item, index) in posterInfos" :width="item.width" :height="item.height" :order="index"
                    :key="item.templateId">
                    <!--
			      your component
			    -->
                    <div class="img-box">
                        <img :src="'/media-server/url?url='+item.preview" style="width:100%;height:100%;">
                        <div class="img-box-content">
                            <div class="ratio">{{item.width}} * {{item.height}}</div>
                            <div class="bottom clearfix">
                                <el-button type="button" size="mini" class="button" @click="viewPT(item)">预览</el-button>
                                <el-button type="button" size="mini" class="button" @click="editPT(item)">编辑</el-button>
                                <el-button type="danger" size="mini" class="button" @click="deletePT(item)">删除</el-button>
                                <el-button type="button" size="mini" class="button" @click="fromPT(item)">新建</el-button>
                                <el-button type="button" size="mini" class="button" @click="editBaseInfo(item)">编辑基础信息</el-button>
                            </div>
                        </div>
                        <el-tooltip class="item" effect="dark" :content="item.title" placement="bottom">
                            <p>{{item.title}}</p>
                        </el-tooltip>
                    </div>



                </waterfall-slot>
            </waterfall>
            <!-- 
			<template v-for="tp in posterInfos">
			<el-card
				class="termialDis" :body-style="{ padding: '0px' }" shadow="never">
			<div class="deviceInfo">
				<span class="name"><i class="el-icon-star-on"></i>
					{{tp.title}}</span>
			</div>
			<div :class="v_or_h(tp) ? 'screenshoot-h':'screenshoot-v'">
				<img :src="'/media-server/url?url='+tp.preview" class="image">
				<div class="ratio">{{tp.width}} * {{tp.height}}</div>
			</div>
			<div class="control" style="">
				<div class="bottom clearfix">
					<el-button type="button" size="mini" class="button"
						@click="viewPT(tp)">预览</el-button>
					<el-button type="button" size="mini" class="button"
						@click="editPT(tp)">编辑</el-button>
					<el-button type="danger" size="mini" class="button"
						@click="deletePT(tp)">删除</el-button>
				</div>
			</div>
			</el-card> </template>
			 -->
        </div>
        <div>
            <el-pagination background style="margin:auto;" class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]"
                :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper" :total="tpager.total"
                @size-change="handleSizeChange" @current-change="handleCurrentChange"> </el-pagination>

        </div>

        <!--dialog-->
        <el-dialog title="修改模板基础信息" :visible.sync="posterBaseInfo.visible" width="50%">
            <el-form ref="form" :model="posterBaseInfo.data" label-width="80px" size="small">
                <el-form-item label="模板名称">
                    <el-col :span="10">
                        <el-input v-model="posterBaseInfo.data.title"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="模板尺寸">
                    <el-col :span="5">
                        <el-input v-model="posterBaseInfo.data.width" placeholder=""></el-input>
                    </el-col>
                    <el-col class="line" :span="1" style="text-align: center;"> × </el-col>
                    <el-col :span="5">
                        <el-input v-model="posterBaseInfo.data.height" placeholder=""></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="尺寸信息">
                    <el-radio-group v-model="posterBaseInfo.data.sizeType">
                        <template v-for="ps in posterSizes">
                            <el-radio-button :label="ps.id">{{ps.tag}}</el-radio-button>
                        </template>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="分类信息">
                    <!--
					<el-checkbox-group v-model="posterBaseInfo.data.useCategorys" >
						<template v-for="ps in useCategory">
							<el-checkbox-button :label="ps.categoryId" :key="ps.categoryId">{{ps.name}}</el-checkbox-button>
						</template>
					</el-checkbox-group>
					-->
                    <el-tree :data="categoryTree" show-checkbox node-key="categoryId" ref="tree" highlight-current
                        :default-checked-keys="posterBaseInfo.data.useCategorys" :props="defaultProps">
                    </el-tree>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveBaseInfo">确 定</el-button>
                    <el-button @click="posterBaseInfo.visible = false">取 消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog title="修改海报分类" :visible.sync="editPosterCategoryVisible">
            <el-tree :data="categoryTree" node-key="categoryId" ref="tree" :props="defaultProps">
                <span class="custom-tree-node" slot-scope="{ node, data }">
                    <span class="node-label">{{ node.label }}</span>
                    <span style="margin-left:50px;">
                        <el-button size="mini" @click="newPosterCategory(data)">
                           	 新增
                        </el-button>
                        <el-button type="primary" size="mini" @click="editPosterCategory(data)">
							修改
                        </el-button>
                        <el-button type="danger" size="mini" @click="deletePosterCategory(data)">
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
                        <el-input v-model="posterCategoryDialog.data.sortNum"></el-input>
                    </el-form-item>
                    <el-form-item label="是否显示">
                        <el-switch v-model="posterCategoryDialog.data.display"></el-switch>
                    </el-form-item>
                    <el-form-item label="上一级">
                        <el-select v-model="posterCategoryDialog.data.parent" placeholder="请选择">
                            <el-option :key="0" label="根目录" :value="0"></el-option>
                            <el-option v-for="c in totalCategory.filter(el => el.type == 2)" :key="c.categoryId" :label="c.name"
                                :value="c.categoryId"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveOrUpdateCategory">确定</el-button>
                        <el-button @click="posterCategoryDialog.visible=false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </el-dialog>
        <el-dialog title="替换海报源数据" :visible.sync="replaceMetaDataDialog.visible">
            <el-form ref="form" size="mini" :model="replaceMetaDataDialog.data" label-width="80px">
                <el-form-item label="搜索内容">
                    <el-input v-model="replaceMetaDataDialog.data.search"></el-input>
                </el-form-item>
                <el-form-item label="替換内容">
                    <el-input v-model="replaceMetaDataDialog.data.rep"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="searchMetaData">搜索</el-button>
                    <el-button type="primary" @click="replaceMetaData">替換</el-button>
                </el-form-item>
                <el-form-item>
                    <el-table :data="searchedMetaData" style="width: 100%">
                        <el-table-column prop="title" label="海报标题">
                        </el-table-column>
                        <el-table-column prop="width" label="宽度" width="180">
                        </el-table-column>
                        <el-table-column prop="height" label="高度">
                        </el-table-column>
                        <el-table-column>
                            <template slot-scope="scope">
                                <el-button type="button" size="mini" class="button" @click="editPT(scope.row)">编辑</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--dialog-->
    </div>
</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/poster/posterTemplate.js')}"></script>