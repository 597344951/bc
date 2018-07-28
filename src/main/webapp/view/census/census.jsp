<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<base href="/">
	<meta charset="UTF-8">
	<title>平台统计信息</title>
<%@include file="/include/head.jsp"%>
<%@include file="/include/echarts.jsp"%>
<%@include file="/include/vcharts.jsp"%>


<style>
	html,
	body {
		width: 100%;
		height: 100%;
		/*overflow: hidden;*/
	}

	.height_full {
		width: 100%;
		height: 100%;
		background-color: #0d2256;
	}

	.height_full * {
		color: #cccdff;
	}

	.height_full .up-box>.el-row {
		display: flex;
		height: 100%;
	}

	h1 {
		font-size: 24px;
		padding-left: 20px;
		font-weight: bold;
		margin: 0;
		text-align: center;
		height: 10%;
		line-height: 10%;
		line-height: 4;
	}

	.height_full .el-button {
		float: right;
		margin-right: 10px;
		margin-top: 20px;
	}

	.height_full .el-button i,
	.height_full .el-button span {
		color: #fff;
	}

	.height_full .el-col {
		padding: 5px;
	}

	.up-box {
		height: 60%;
	}

	.down-box {
		height: 30%;
		overflow: hidden;
	}

	/****************up-left-box ***************/

	.up-left-box .ve-pie *{
		height:100%!important;
	}

	.up-left-up {
		height: 48%;
		border: 1px solid #195bff;
		box-shadow: inset 0px 0px 30px 0px #2624ff;
		margin: 10px;
		border-image: url(../assets/img/border-icon.png) 1  stretch;
	}

	.up-left-down {
		height: 50%;
		border: 1px solid #195bff;
		box-shadow: inset 0px 0px 30px 0px #2624ff;
		margin: 10px;
		border-image: url(../assets/img/border-icon.png) 1  stretch;
	}

	.party-part {
		text-align: center;
		font-size: 18px;
		font-weight: bold;
		margin-bottom: 10px;
	}

	.part-title,
	.part-total {
		display: block;
	}

	.part-title {
		font-size: 12px;
		text-align: center;
	}

	.part-total {
		font-size: 18px;
		font-weight: bold;
		text-align: center;
	}	
	.part-icon {
		width: 36px;
		height: 36px;
		border-radius: 50%;
		background: #eee;
		display: inline-block;
		margin-top: -5px;
		margin-right: 10px;
	}

	.party-part-1 {
		font-size: 12px;
		width: 50px;
	}

	.party-part-2 {
		font-size: 20px;
		width: 50px;
		color: #cd8fda;
	}

	.party-part-3 {
		color: #f47e3d;
	}

	.party-part-4 {
		color: #735af7;
	}

	.party-part-5 {
		color: #80cddd;
	}

	.part-icon-box {
		display: inline-block;
		text-align: center;
	}

	.part-icon1 {
		background: url(../assets/img/19.png);
		background-size: cover;
	}

	.part-icon2 {
		margin-top: 3px;
		background: url(../assets/img/security_icon9.png);
		background-size: cover;
	}

	.part-icon3 {
		background: url(../assets/img/20.png);
		background-size: cover;
	}

	.part-icon4 {

		background: url(../assets/img/9.png);
		background-size: cover;
	}

	/**************** up-center-box ***************/

	.height_full .up-center-box .img1 {
		margin: 10px 5px 0;
		height: 100%;
		border: 1px solid #195bff;
		box-shadow: inset 0px 0px 30px 0px #2624ff;
		border-image: url(../assets/img/border-icon2.png) 1  stretch;
	}

	.img1 img {
		width: 100%;
		height: 100%;
	}

	/**************** up-right-box ***************/

	.height_full .up-right-box>.el-row {
		margin: 10px;
		height: 100%;
		/* padding-bottom: 10px; */
		border: 1px solid #195bff;
		box-shadow: inset 0px 0px 30px 0px #2624ff;
		border-image: url(../assets/img/border-icon2.png) 1  stretch;

	}

	.up-right-box .up-right-left .party-part,
	.up-right-box .up-right-right .party-part {
		padding-top: 10px;
		/*height: 100px;*/
	}

	.up-right-right .el-row {
		text-align: center;
	}

	/**************** down-left-box ***************/

	.down-left-box {
		float: left;
		height: 100%;
		width: 48.5%;
		margin: 15px 0 0 15px;
		border: 1px solid #195bff;
		box-shadow: inset 0px 0px 30px 0px #2624ff;
		border-image: url(../assets/img/border-icon1.png) 1  stretch;
	}

	/**************** down-right-box ***************/

	.down-right-box {
		float: right;
		width: 48.5%;
		height: 100%;
		margin: 15px 15px 0 0px;
		padding: 10px;
		border: 1px solid #195bff;
		box-shadow: inset 0px 0px 30px 0px #2624ff;
		border-image: url(../assets/img/border-icon1.png) 1  stretch;
	}
</style>
</head>

<body>
	<div id="app" class="height_full" v-cloak>
		<h1>
			<el-button v-if="!fullscreen" @click="setFullScreen" round type="success" icon="el-icon-view" name="FullScreen" >全屏显示</el-button>
			党建数据统计
		</h1>
		<div class="up-box">
			<el-row>
				<el-col :span="8" class="up-left-box">
					<el-row class="up-left-up">
						<el-col :span="12">
							<el-row>
								<el-col :span="24" class="party-part">
									<i class="part-icon part-icon1"></i>
									<div class="part-icon-box">
										<span class="party-part-1">党组织</span>
										<br>
										<span class="party-part-2">1688</span>
									</div>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="12">
									<span class="part-title">党委数</span>
									<span class="part-total">688</span>
								</el-col>
								<el-col :span="12">
									<span class="part-title">党总支数</span>
									<span class="part-total">544</span>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="12">
									<span class="part-title">党支部数</span>
									<span class="part-total">325</span>
								</el-col>
								<el-col :span="12">
									<span class="part-title">联合党支部数</span>
									<span class="part-total">128</span>
								</el-col>
							</el-row>
						</el-col>
						<el-col :span="12">
							<el-row>
								<el-col :span="24" class="party-part">
									<i class="part-icon part-icon2"></i>
									<div class="part-icon-box">
										<span class="party-part-1">党员数 </span>
										<br>
										<span class="party-part-3">1688</span>
									</div>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="12">
									<span class="part-title">正式党员数</span>
									<span class="part-total">869</span>
								</el-col>
								<el-col :span="12">
									<span class="part-title">预备党员数</span>
									<span class="part-total">6555</span>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="12">
									<span class="part-title">党支部数</span>
									<span class="part-total"> 688</span>
								</el-col>
								<el-col :span="12">
									<span class="part-title">联合党支部数</span>
									<span class="part-total">128</span>
								</el-col>
							</el-row>
						</el-col>
					</el-row>
					<el-row class="up-left-down">
						<el-col :span="24" style="height:100%">
							<template>
								<ve-pie  style="height:100%;"  :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="oneData" :settings="oneSettings"
								    :text-style="chartConfig.textStyle":grid="pgrid" :legend-visible="false"></ve-pie>
							</template>
						</el-col>
					</el-row>

				</el-col>
				<el-col :span="8" class="up-center-box">
					<div class="img1">
						<img src="/assets/icons/a.png">
					</div>
				</el-col>
				<el-col :span="8" class="up-right-box">
					<el-row>
						<el-col :span="12" class="up-right-left">
							<el-row>
								<el-col :span="24" class="party-part">
									<i class="part-icon part-icon3"></i>
									<div class="part-icon-box">
										<span class="party-part-1">党建活动 </span>
										<br>
										<span class="party-part-4">68次</span>
									</div>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<template>
										<ve-funnel height="300px" :data="threeData" :theme-name="chartConfig.themeName" :text-style="chartConfig.textStyle" :legend-visible="false"></ve-funnel>
									</template>
								</el-col>
							</el-row>

						</el-col>
						<el-col :span="12" class="up-right-right">
							<el-row>
								<el-col :span="24" class="party-part">
									<i class="part-icon part-icon4"></i>
									<div class="part-icon-box">
										<span class="party-part-1">教育学习 </span>
										<br>
										<span class="party-part-5">64次</span>
									</div>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">标准化达标率
									<template>
										<ve-gauge height="300px" :grid="grid" :theme-name="chartConfig.themeName" :settings="twoSettings" :data="twoData" :text-style="chartConfig.textStyle"
										    :legend-visible="false"></ve-gauge>
									</template>
								</el-col>
							</el-row>

						</el-col>
					</el-row>

				</el-col>
			</el-row>
		</div>
		<div class="down-box">
			<el-row>
				<el-col :span="12" class="down-left-box">
					<template>
						<ve-line :data="fourData" height="170px" :settings="fourSettings" :text-style="chartConfig.textStyle" :grid="grid" :legend-visible="false"></ve-line>
					</template>
				</el-col>
				<el-col :span="12" class="down-right-box">
					<template>
						<ve-histogram :data="fiveData" height="170px" :settings="fiveSettings" :text-style="chartConfig.textStyle" :grid="grid" :legend-visible="false"
						    :extend="chartExtend"></ve-histogram>
					</template>
				</el-col>
			</el-row>
		</div>




	</div>



	<!--		<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="ratiochartData" :settings="chartSettings" -->

</body>

<script type="module" src="${urls.getForLookupPath('/assets/module/census/census.js')}"></script>

</html>