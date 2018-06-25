<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<title>系统日志</title>
<%@include file="/include/head.jsp"%>
 
<style>
    .queryForm{
        margin-bottom:0px;
    }
    .queryForm > .el-form-item {
        margin-bottom:5px;
    }
    .queryForm > .el-form-item > .el-form-item__content>.el-select--mini{
        width:130px;
    }
    .queryForm > .el-form-item > .el-form-item__content>.el-date-editor.el-input{
        width:130px;
    }
    .log-table{
        width:80%;
    }
    .log-table>tbody>tr>:nth-child(1){
        width:100px;
        font-weight: bolder;
    }
</style>
</head>
<body>
    <el-container id="app">
        <el-header style="height: auto;padding-left: 20px;">
            <el-row style="padding-top: 5px;">
                <el-col :span="19">
                    <el-form :inline="true" class="demo-form-inline queryForm" size="mini" >
                        <el-form-item label="日志类别">
                            <el-select v-model="log.type" placeholder="请选择">
                                <el-option label="所有日志" value=""></el-option>
                                <el-option v-for="t in logTypes" :key="t.tid" :label="t.name" :value="t.tid">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="日志等级">
                            <el-select v-model="log.level" placeholder="请选择">
                                <el-option label="所有日志" value=""></el-option>
                                <el-option v-for="t in levels" :key="t.value" :label="t.value" :value="t.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="记录时间">
                            <el-date-picker type="date" placeholder="选择日期" v-model="log.startTime"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="-">
                            <el-date-picker type="date" placeholder="选择日期" v-model="log.endTime"></el-date-picker>
                        </el-form-item>
                        <el-form-item style="width:50px;">
                            <el-button type="primary" @click="queryBtnHandle">查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="5">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pager.currentPage"
                        :page-sizes="[14, 20, 40,100]" :page-size="pager.limit" :total="pager.total" layout="total, sizes, prev,  next">
                    </el-pagination>
                </el-col>
            </el-row>
        </el-header>
        <el-main>
            <template>
                <el-table :data="logs" style="width: 100%" v-loading="loading">
                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <table class="log-table">
                                <tbody>
                                    <tr>
                                        <td>
                                                发生位置 :
                                        </td>
                                        <td>{{props.row.method}}</td>
                                    </tr>
                                    <tr>
                                            <td>
                                                    详细数据 :
                                            </td>
                                            <td><pre class="brush: js">{{ props.row.params }}</pre></td>
                                        </tr>
                                </tbody>
                            </table>
                        </template>
                    </el-table-column>
                    <el-table-column label="id" prop="logId" width="100">
                    </el-table-column>
                     <el-table-column label="日志类别" width="155">
                         <template slot-scope="scope">
                              {{logType_dis(scope.row)}}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作用户" prop="username" width="155">
                    </el-table-column>
                    <el-table-column label="操作信息描述" prop="operation" width="200">
                    </el-table-column>
                    <el-table-column label="ip" prop="ip" width="155">
                    </el-table-column>
                    <el-table-column label="等级" width="100">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <el-tag :type="level_dis(scope.row)" size="medium">{{ scope.row.level }}</el-tag>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="时间" width="180">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                {{ scope.row.date | datetime }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="日志内容" prop="msg">
                    </el-table-column>
                </el-table>
            </template>

        </el-main>
    </el-container>
</body>
</html>
<script type="text/javascript" charset="utf-8" src="assets/module/log/log.js"></script>