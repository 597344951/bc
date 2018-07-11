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
						.height_full{
							width:100%;
							height:100%;/*
							margin-top: -20px;
							padding-top: 20px;*/
							background-color: #0d2256;
						}
						.height_full *{
							color:#cccdff;
						}
						.height_full .el-row{
							display:flex;
						}
						h1{
							font-size: 24px;
							padding-left: 20px;
							font-weight: bold;
							/*margin: 45px 0 20px;*/
							text-align: center;
							height:5%;
						}
						.height_full .el-col{
							padding: 5px;
						}
						.up-box{
							height: 65%;
						}
						.down-box{
							height: 30%;
						}
						/****************up-left-box ***************/
						.up-left-box{
							/*height:500px;*/
						}
						.up-left-up{
							/*height: 200px;*/
							border:1px solid #195bff;
							box-shadow:inset 0px 0px 30px 0px #2624ff;
							margin: 10px;
						}
						.up-left-down{
							/*height:300px;*/
							border:1px solid #195bff;
							box-shadow:inset 0px 0px 30px 0px #2624ff;
							margin: 10px;
						}
						.party-part{
							text-align: center;
							font-size: 18px;
							font-weight: bold;
							margin-bottom: 10px;
						}
						.ve-pie{
							/*height:300px!important;*/
						}
						.part-title,.part-total{
							display:block;
						}
						.part-title{
							font-size: 15px;
							text-align: center;
						}
						.part-total{
							font-size: 18px;
							font-weight: bold;
							text-align: center;
						}
						.up-left-down canvas{
							/*height:300px!important;*/
							/*width:480px!important;*/
						}
						.part-icon{
							width:50px;
							height: 50px;
							border-radius: 50%;
							background:#eee;
							display: inline-block;
							vertical-align: bottom;
    						margin-top: -5px;
							margin-right: 10px;
						} 
						.party-part-1{
							font-size: 12px;
							width: 50px;
						}
						.party-part-2{
							font-size: 20px;
							width: 50px;
							color:#cd8fda;
						}
						.party-part-3{
							color:#f47e3d;
						}
						.party-part-4{
							color:#735af7;
						}
						.party-part-5{
							color:#80cddd;
						}
						.part-icon-box{
							display: inline-block;
							text-align: center;
						}
						.part-icon1{
							background:url(../assets/img/19.png);
							background-size: cover;
						}
						.part-icon2{
							margin-top: 3px;
							background:url(../assets/img/security_icon9.png);
							background-size: cover;
						}
						.part-icon3{
							background:url(../assets/img/20.png);
							background-size: cover;
						}
						.part-icon4{
							
							background:url(../assets/img/9.png);
							background-size: cover;
						}

						/**************** up-center-box ***************/
						.height_full .up-center-box .img1{
							margin:10px 5px 0; 
							height:510px;
							border:1px solid #195bff;
							box-shadow:inset 0px 0px 30px 0px #2624ff;
						}
						.img1 img{
							width: 100%;
							height:100%;
						}
						/**************** up-right-box ***************/
						.height_full .up-right-box>.el-row{
							margin:10px; 
							height:510px;
							/* padding-bottom: 10px; */
							border:1px solid #195bff;
							box-shadow:inset 0px 0px 30px 0px #2624ff;
							
						}
						.up-right-box .up-right-left .party-part,
						.up-right-box .up-right-right .party-part{
							padding-top: 20px;
							height: 100px;
						}
						.up-right-right .el-row{
							text-align: center;
						}
						/**************** down-left-box ***************/
						.down-left-box .ve-line{
							margin:10px; 
							/* height:350px!important; */
							border:1px solid #195bff;
							box-shadow:inset 0px 0px 30px 0px #2624ff;
						}
						/**************** down-right-box ***************/
						.down-right-box .ve-histogram{
							margin:10px; 
							padding:10px;
							/* height:350px!important; */
							border:1px solid #195bff;
							box-shadow:inset 0px 0px 30px 0px #2624ff;
						}
					</style>
	</head>

	<body>
		<div id="app" class="height_full" v-cloak>

						<h1>党建数据统计</h1>
		
			
			<div class="up-box">
				<el-row>
					<el-col :span="8" class="up-left-box">
						<el-row class="up-left-up" >
						  <el-col :span="12">
								<el-row>
								  <el-col :span="24" class="party-part"> 
									<i class="part-icon part-icon1"></i>
									<div class="part-icon-box">
										<span class="party-part-1">党组织</span> <br><span class="party-part-2">1688</span>
									</div>
								</el-col>
								</el-row>
								<el-row>
								  <el-col :span="12"><span class="part-title">党委数</span> <span class="part-total">688</span></el-col>
								  <el-col :span="12"><span class="part-title">党总支数</span> <span class="part-total">544</span></el-col>
								</el-row>
								<el-row>
								  <el-col :span="12"><span class="part-title">党支部数</span>  <span class="part-total">325</span></el-col>
								  <el-col :span="12"><span class="part-title">联合党支部数</span>  <span class="part-total">128</span></el-col>
								</el-row>
						  </el-col>
						  <el-col :span="12">
								<el-row>
									<el-col :span="24" class="party-part">
										<i class="part-icon part-icon2"></i>
										<div class="part-icon-box">
											<span class="party-part-1">党员数 </span> <br><span class="party-part-3">1688</span>
										</div>
									</el-col>
									</el-row>
									<el-row>
									<el-col :span="12"><span class="part-title">正式党员数</span> <span class="part-total">869</span></el-col>
									<el-col :span="12"><span class="part-title">预备党员数</span>  <span class="part-total">6555</span></el-col>
									</el-row>
									<el-row>
									<el-col :span="12"><span class="part-title">党支部数</span> <span class="part-total"> 688</span></el-col>
									<el-col :span="12"><span class="part-title">联合党支部数</span> <span class="part-total">128</span></el-col>
									</el-row>
						  </el-col>
						</el-row>
						<el-row class="up-left-down">
						  <el-col :span="24">
									<template>
										<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="oneData" :settings="oneSettings" :text-style="chartConfig.textStyle"  :legend-visible="false"></ve-pie>
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
											<span class="party-part-1">党建活动 </span> <br><span class="party-part-4">68次</span>
										</div>
									</el-col>
								</el-row>
								<el-row>
								  <el-col :span="24">	
									<template>
										<ve-funnel :data="threeData" :theme-name="chartConfig.themeName" :text-style="chartConfig.textStyle" :legend-visible="false"></ve-funnel>
									</template></el-col>
								</el-row>
							
							</el-col>
							<el-col :span="12" class="up-right-right">
								<el-row>
								  <el-col :span="24" class="party-part">
									  <i class="part-icon part-icon4"></i>
									  <div class="part-icon-box">
											<span class="party-part-1">教育学习 </span> <br><span class="party-part-5">64次</span>
										</div>
									</el-col>
								</el-row>
								<el-row>
								  <el-col :span="24">标准化达标率
										<template>
											<ve-gauge :theme-name="chartConfig.themeName" :settings="twoSettings" :data="twoData" :text-style="chartConfig.textStyle" :legend-visible="false"></ve-gauge>
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
							<ve-line :data="fourData" height="298px" :settings="fourSettings" :text-style="chartConfig.textStyle" :legend-visible="false"></ve-line>
						</template>
					</el-col>
					<el-col :span="12" class="down-right-box">
						<template>
							<ve-histogram :data="fiveData" height="298px" :settings="fiveSettings" :text-style="chartConfig.textStyle" :legend-visible="false"  :extend="chartExtend"></ve-histogram>
						</template>
					</el-col>
				</el-row>
			</div>




		</div>



		<!--		<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="ratiochartData" :settings="chartSettings" -->

	</body>

	<script type="module" src="${urls.getForLookupPath('/assets/module/census/census.js')}"></script>

	</html>