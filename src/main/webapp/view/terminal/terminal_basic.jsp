 <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="/">
	<meta charset="UTF-8">
	<title>终端机基础信息</title>
	<%@include file="/include/head.jsp"%>
	<%@include file="/include/echarts.jsp"%>
	<%@include file="/include/vcharts.jsp"%>
	<%@include file="/include/map-gaode.jsp"%>

<style>
	html,body{
		width:100% ;
		height: 100%;
	}
	#app,.el-container,.el-main{
		height:100%;
	}
	#container {
		width: 98%;
		height: 96%;
	}

	.info-tip {
		position: absolute;
		top: 10px;
		right: 10px;
		font-size: 12px;
		background-color: #fff;
		height: 35px;
		text-align: left;
	}

	.info {
		border: solid 1px silver;
	}

	div.info-top {
		position: relative;
		background: none repeat scroll 0 0 #F9F9F9;
		border-bottom: 1px solid #CCC;
		border-radius: 5px 5px 0 0;
	}

	div.info-top div {
		display: inline-block;
		color: #333333;
		font-size: 14px;
		font-weight: bold;
		line-height: 31px;
		padding: 0 10px;
	}

	div.info-top img {
		position: absolute;
		top: 10px;
		right: 10px;
		transition-duration: 0.25s;
	}

	div.info-top img:hover {
		box-shadow: 0px 0px 5px #000;
	}

	div.info-middle {
		font-size: 12px;
		padding: 6px;
		line-height: 25px;
	}

	div.info-bottom {
		height: 0px;
		width: 100%;
		clear: both;
		text-align: center;
	}

	div.info-bottom img {
		position: relative;
		z-index: 104;
	}

	span {
		margin-left: 5px;
		font-size: 11px;
	}

	.info-middle img {
		float: left;
		margin-right: 6px;
	}
	.control-button{
		margin-left: 10px;
		margin-top: 0;
	}
	.amap-logo,.amap-copyright{
		display: none!important;
		position: relative;
	}
</style>
</head>

<body>
	<div id="app" class="height_full" v-cloak>
		<el-container>
			<div role="table">
				<div class="toolbar">
					<div class="grid-content bg-purple">
						<el-button type="success" size="small" icon="el-icon-circle-check-outline" @click="online()">在线设备</el-button>
						<el-button type="info" size="small" icon="el-icon-circle-close-outline" @click="outline()">离线设备</el-button>

						<!-- <el-button type="primary" size="small" icon="el-icon-search" @click="search()">搜索</el-button> -->
						<el-popover placement="right" width="400" trigger="click">
							<el-form ref="form" label-width="80px" size="mini">
								<el-form-item label="终端名称">
									<el-input v-model="tbi.data.name" style="width: 80%;"></el-input>
								</el-form-item>
								<el-form-item label="所属地区">
									<el-input v-model="tbi.data.loc" style="width: 80%;"></el-input>
								</el-form-item>
								<el-form-item label="在线状态">
									<el-input v-model="tbi.data.online" style="width: 80%;"></el-input>
								</el-form-item>
								<el-form-item label="屏幕方向">
									<el-input v-model="tbi.data.rev" style="width: 80%;"></el-input>
								</el-form-item>
								<el-form-item>
									<el-button type="primary" @click="fastSearch()" size="mini">确定</el-button>
									<el-button @click="search()" size="mini">更多</el-button>
								</el-form-item>
							</el-form>
							<el-button type="primary" size="small" icon="el-icon-search" slot="reference" class="margin-0-10">搜索设备</el-button>
						</el-popover>
						<el-button type="primary" size="small" icon="el-icon-refresh" @click="syn()">同步数据</el-button>
						<el-button type="primary" size="small" icon="el-icon-info" @click="statistic()">统计图表</el-button>
						<el-button-group >
							<el-button size="small" :type="tbi.style?'primary':''" icon="el-icon-location" @click="mapThings()"></el-button>
                            <el-button size="small" :type="!tbi.style?'primary':''"  icon="el-icon-menu" @click="tbi.style=false"></el-button>                           
                        </el-button-group>

					</div>
				</div>
			</div>


			<!--表格主体-->
			<el-main>
				<div v-show="tbi.style" style="height:100%;border-radius: 6px;overflow: hidden;box-shadow: inset 0px 0px 12px 2px #ccc;padding: 10px;background: #fcf9f2;">
					<div id="container">
			
					</div>
					<div class="button-group">
						<input id="setFitView" class="button" type="button" value="地图自适应显示" />
					</div>
					<div class="info-tip">
						<div id="centerCoord"></div>
						<div id="tips"></div>
					</div>
				</div>
				<div v-show="!tbi.style">
					<template>
						<el-table :data="pager.list" style="width:100%">
							<el-table-column type="expand">
								<template slot-scope="props">
									<el-form abel-position="left" inline class="demo-table-expand">
										<table style="width: 100%">
											<thead>
												<tr>
													<th>硬件信息</th>
													<th> 软件信息 </th>
													<th>地理位置信息</th>
												</tr>
											</thead>
											<tbody>
												<td>
													<table class="dis-info-big">
														<tr>
															<td>屏幕尺寸</td>
															<td> </td>
															<td>{{props.row.size}}</td>
														</tr>
														<tr>
															<td>屏幕分辨率</td>
															<td> </td>
															<td>{{props.row.ratio}}</td>
														</tr>
														<tr>
															<td>屏幕方向</td>
															<td> </td>
															<td>{{props.row.rev}}</td>
														</tr>
														<tr>
															<td>终端类型</td>
															<td> </td>
															<td>{{props.row.typ}}</td>
														</tr>
														<tr>
															<td>标识ID</td>
															<td> </td>
															<td>{{props.row.id}}</td>
														</tr>
														<tr>
															<td>标识code</td>
															<td> </td>
															<td>{{props.row.code}}</td>
														</tr>
														<tr>
															<td>在线状态</td>
															<td> </td>
															<td>{{props.row.online=='1'?'在线':'离线'}}</td>
														</tr>
													</table>
												</td>
												<td>
													<table class="dis-info-big">
														<tr>
															<td>系统</td>
															<td> </td>
															<td>{{props.row.sys}}</td>
														</tr>
														<tr>
															<td>软件版本</td>
															<td> </td>
															<td>{{props.row.ver}}</td>
														</tr>
														<tr>
															<td>ip地址</td>
															<td> </td>
															<td>{{props.row.ip}}</td>
														</tr>
														<tr>
															<td>mac地址</td>
															<td> </td>
															<td>{{props.row.mac}}</td>
														</tr>
														<tr>
															<td>注册时间</td>
															<td> </td>
															<td>{{props.row.resTime}}</td>
														</tr>
														<tr>
															<td>心跳检测时间</td>
															<td> </td>
															<td>
																<el-tooltip class="item" effect="dark" :content="props.row.lastTime | datetime" placement="top-start">
																	<span>{{props.row.lastTime|time_ago}}</span>
																</el-tooltip>
															</td>
														</tr>
														<tr>
															<td>最后同步时间</td>
															<td> </td>
															<td>
																<el-tooltip class="item" effect="dark" :content="props.row.lastSynTime | datetime" placement="top-start">
																	<span>{{props.row.lastSynTime|time_ago}}</span>
																</el-tooltip>
															</td>
														</tr>
													</table>
												</td>
												<td>
													<table class="dis-info-big">
														<tr>
															<td>终端名称</td>
															<td> </td>
															<td>{{props.row.name}}</td>
														</tr>
														<tr>
															<td>所属地区</td>
															<td> </td>
															<td>{{props.row.loc}}</td>
														</tr>
														<tr>
															<td>终端地址</td>
															<td> </td>
															<td>{{props.row.addr}}</td>
														</tr>
														<tr>
															<td>联系电话</td>
															<td> </td>
															<td>{{props.row.tel}}</td>
														</tr>
														<tr>
															<td>GIS信息</td>
															<td> </td>
															<td>{{props.row.gis}}</td>
														</tr>
														<tr>
															<td>保修信息</td>
															<td> </td>
															<td>{{props.row.warranty}}</td>
														</tr>
														<tr>
															<td>其他</td>
															<td> </td>
															<td></td>
														</tr>
													</table>
												</td>
											</tbody>
										</table>
									</el-form>
								</template>
							</el-table-column>
							<el-table-column prop="name" label="终端名称"></el-table-column>
							<el-table-column label="所属组织">
								<template slot-scope="scope">
									{{scope.row.orgInfo ? scope.row.orgInfo.orgInfoName:'未配置'}}
								</template>
							</el-table-column>
							<el-table-column prop="loc" label="所属地区"></el-table-column>
							<el-table-column prop="tel" label="联系电话"></el-table-column>
							<el-table-column prop="addr" label="终端地址"></el-table-column>
							<el-table-column prop="ip" label="ip地址"></el-table-column>
			
							<el-table-column label="操作" fixed="right" width="200">
								<template slot-scope="scope">
									<el-button size="mini" icon="el-icon-edit" type="warning" @click="updateTbi(scope.row)">编辑</el-button>
								</template>
							</el-table-column>
						</el-table>
					</template>
				</div>
			</el-main>
			
			<el-footer>
				<el-pagination id="pagesdididi " layout="total, prev, pager, next, jumper " @current-change="pagerCurrentChange(pager.pageNum,
									    pager.pageSize) " :current-page.sync="pager.pageNum " :page-size.sync="pager.pageSize " :total="pager.total ">
				</el-pagination>
			</el-footer>
			
		</el-container>



		<el-dialog title="" :visible.sync="tbi.visible" width="40%">
			<el-form :model="tbi" label-width="100px" size="mini">
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="终端名称" v-if="tbi.search">
							<el-input v-model="tbi.data.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="所属地区">
							<el-input v-model="tbi.data.loc"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">
						<el-form-item label="在线状态">
							<el-input v-model="tbi.data.online"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="屏幕方向">
							<el-input v-model="tbi.data.rev"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">
						<el-form-item label="屏幕尺寸">
							<el-input v-model="tbi.data.size"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="屏幕分辨率">
							<el-input v-model="tbi.data.ratio"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="联系电话">
							<el-input v-model="tbi.data.tel"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="终端地址">
							<el-input v-model="tbi.data.addr"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">
						<el-form-item label="标识ID">
							<el-input v-model="tbi.data.id"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="标识code">
							<el-input v-model="tbi.data.code"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="类别id">
							<el-input v-model="tbi.data.typeId"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="注册时间">
							<el-input v-model="tbi.data.resTime"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="最新时间">
							<el-input v-model="tbi.data.lastTime"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="ip地址">
							<el-input v-model="tbi.data.ip"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="mac地址">
							<el-input v-model="tbi.data.mac"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="系统">
							<el-input v-model="tbi.data.sys"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="系统版本">
							<el-input v-model="tbi.data.ver"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<el-form-item label="终端类型">
							<el-input v-model="tbi.data.typ"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="GIS信息">
							<el-input v-model="tbi.data.gis"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<el-form-item label="保修信息">
							<el-input v-model="tbi.data.warranty"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="refresh()" size="mini">取 消</el-button>
				<el-button type="primary" @click="updateorinsert(tbi)" size="mini">确 定</el-button>
			</div>
		</el-dialog>
		<el-dialog title="统计" :visible.sync="statistics" width="80%">
			<el-header>
				<el-button @click="sta('onesta')">1</el-button>
				<el-button @click="sta('twosta')">2</el-button>
				<el-button @click="sta('threesta')">3</el-button>
				<el-button @click="sta('foursta')">4</el-button>
			</el-header>
			<el-main>
				<template>
					<el-row type="flex" justify="center" v-if="onesta">
						<el-col :span="8">
							<h2>屏幕分辨率统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="ratiochartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
						<el-col :span="8">
							<h2>屏幕方向统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="revchartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
					</el-row>
					<el-row type="flex" justify="center" v-if="twosta">
						<el-col :span="8">
							<h2>终端在线统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="onlinechartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
						<el-col :span="8">
							<h2>终端所属地区统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="locchartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
					</el-row>
					<el-row v-if="threesta" type="flex" justify="center">
						<el-col :span="8">
							<h2>终端类型统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="typchartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
						<el-col :span="8">
							<h2>终端保修信息统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="warrantychartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
					</el-row>
					<el-row v-if="foursta" type="flex" justify="center">
						<el-col :span="8">
							<h2>软件版本统计</h2>
							<ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="verchartData" :settings="chartSettings"
							    :legend="chartConfig.legend"></ve-pie>
						</el-col>
						<el-col :span="16">
							<h2>全局统计</h2>
							<ve-bar :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="totalData" :settings="chartSettings" :legend="chartConfig.legend"></ve-bar>
						</el-col>
					</el-row>
				</template>
			</el-main>
			<el-footer>
				<el-button @click="refresh()">返回</el-button>
			</el-footer>
		</el-dialog>
	</div>
</body>

<script type="module" src="${urls.getForLookupPath('/assets/module/terminal/terminal_basic.js')}"></script>
</html>