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

</head>

<body>
    <div id="app" class="height_full" v-cloak>
		<el-container>
            <el-main >
                    <template>
                            
                                    <el-select v-model="userId" clearable placeholder="请选择所属广告" size="mini">
                                        <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value" >
                                        </el-option>
                                    </el-select>
                                {{userId}}
                                    <el-date-picker v-model="programDate" align="right" type="date" placeholder="选择日期" :picker-options="pickerOptions1" size="mini" value-format="yyyy-MM-dd">
                                    </el-date-picker>
                                {{programDate}}
                                    <el-button type="primary" icon="el-icon-search" size="mini">查询</el-button>
                                
                        <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card" >
                            <el-tab-pane label="播放时长" name="first">
                                    <el-row type="flex" justify="center" v-if="activeName=='first'">
                                            <el-col :span="6">
                                                <h2>播放天数统计</h2>
                                                <ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="ratiochartData"
                                                    :settings="chartSettings" ></ve-pie>
                                            </el-col>
                                            <el-col :span="10">
                                                <h2>播放时长统计</h2>
                                                <ve-bar  :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="barchartData"
                                                :colors="chartConfig.color" ></ve-bar>
                                            </el-col>
                                        </el-row>
                                        <el-row type="flex" justify="center" >
                                            <el-col :span="12">
                                                <h2>播放次数统计</h2>
                                                <ve-bar :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="barchartData2"
                                                ></ve-bar>
                                            </el-col>
                                           
                                        </el-row>
                            </el-tab-pane>
                            <el-tab-pane label="地域关联" name="second">
                                    <el-row type="flex" justify="center" v-if="activeName=='second'">
                                            <el-col :span="12">
                                                <h2>播放终端地区统计</h2>
                                                <ve-pie :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="ratiochartData2"
                                                :settings="chartSettings"></ve-pie>
                                            </el-col>
                                            <el-col :span="4">
                                                <h2>参与播放终端数</h2>
                                                <h2>1850</h2>
                                            </el-col>
                                        </el-row>
                            </el-tab-pane>
                            <el-tab-pane label="时段分布" name="third">
                                    <el-row type="flex" justify="center" v-if="activeName=='third'">
                                            <el-col :span="12">
                                                <h2>终端预设播放时段</h2>
                                                <ve-line :theme-name="chartConfig.themeName" :toolbox="chartConfig.toolbox" :data="linechartData"
                                                :settings="chartSettings2"></ve-line>
                                            </el-col>
                                            
                                        </el-row>
                            </el-tab-pane>
                        </el-tabs>
                    </template>
                
                   
                   
                   
                   
                
            </el-main>
        </el-container>
    </div>
</body>

<script type="module" src="${urls.getForLookupPath('/assets/module/terminal/terminal_playlist.js')}"></script>

</html>