<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户提交申请材料</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>

<style>
    .form-container {
        width: 80%;
        padding: 20px;
        border: solid 1px #ddd;
        margin: auto;
        text-align: left;
    }

    .title {
        font-weight: bolder;
        display: inline-block;
        width: 100px;
        text-align: right;
        margin-right: 10px;
    }

    .value {
        margin-left: 5px;
    }
</style>
</head>

<body>
<div id="app">
    <el-pagination style="margin:auto;" class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]" :page-size="tpager.size"
        layout="total, sizes, prev, pager, next, jumper" :total="tpager.total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
    </el-pagination>
    <el-table :data="formDatas">
        <el-table-column type="expand">
            <div slot-scope="props" style="width:60%;">
                <el-row v-for="group in toGroupFiels(props.row.fields)">
                    <el-col v-for="field in group" :span="field.colWidth">
                        <span class="title">{{field.label}} </span>: <span class="value">{{getFieldValue(props.row,field)}} </span>
                    </el-col>
                </el-row>
            </div>
        </el-table-column>
        <el-table-column label="申请人">
            <template slot-scope="scope">
                {{scope.row.user.username}}
            </template>
        </el-table-column>
        <el-table-column label="申请主题">
            <template slot-scope="scope">
                {{scope.row.form.title}}
            </template>
        </el-table-column>
        <el-table-column label="申请时间">
            <template slot-scope="scope">
                {{scope.row.saveTime | datetime}}
            </template>
        </el-table-column>
    </el-table>
</div>
</body>
<script src="${urls.getForLookupPath('/assets/module/applicationform/application-user-data.js')}"></script>
</html>