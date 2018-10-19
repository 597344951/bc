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
				<el-radio-button label="-1">其他用途</el-radio-button> 
				<template v-for="ps in useCategory">
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
				</el-form-item>
			</el-form>
		</div>
		<div>
			<el-pagination background style="margin:auto;" class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]"
			 :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper" :total="tpager.total" @size-change="handleSizeChange"
			 @current-change="handleCurrentChange"> </el-pagination>

		</div>
		<div style="overflow: auto;">
			<waterfall :line="displayType" //宽度 :line-gap="240" :min-line-gap="150" :max-line-gap="250" //各个组件之间的间距 :gap="20"
			 :padding="10" //水平工作模式时 最大宽度 :single-max-width="300" center="center" //是否修正高度 :fixed-height="false" :watch="posterInfos">
				<!-- each component is wrapped by a waterfall slot -->
				<waterfall-slot v-for="(item, index) in posterInfos" :width="item.width" :height="item.height" :order="index" :key="item.templateId">
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
			 :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper" :total="tpager.total" @size-change="handleSizeChange"
			 @current-change="handleCurrentChange"> </el-pagination>

		</div>

		<!--dialog-->
		<el-dialog title="修改模板基础信息" :visible.sync="posterBaseInfo.visible" width="50%" >
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
					<el-radio-group v-model="posterBaseInfo.data.sizeType" >
						<template v-for="ps in posterSizes">
							<el-radio-button :label="ps.id">{{ps.tag}}</el-radio-button>
						</template>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="分类信息">
					<el-checkbox-group v-model="posterBaseInfo.data.useCategorys" >
						<template v-for="ps in useCategory">
							<el-checkbox-button :label="ps.categoryId" :key="ps.categoryId">{{ps.name}}</el-checkbox-button>
						</template>
					</el-checkbox-group>	
				</el-form-item>
				<el-form-item >
					<el-button type="primary" @click="saveBaseInfo">确 定</el-button>
					<el-button @click="posterBaseInfo.visible = false">取 消</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

		<!--dialog-->
	</div>
</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/poster/posterTemplate.js')}"></script>