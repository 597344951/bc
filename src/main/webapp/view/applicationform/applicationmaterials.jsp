<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请材料</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>

<%@include file="/include/mock.jsp"%>
<style>
    .form-container{
        width:80%;
        padding:20px;
        border:solid 1px #ddd;
        margin: auto;
        text-align:left;
    } 
</style>
</head>

<body>
    <div id="app">
        <el-menu class="el-menu-demo" mode="horizontal">
            <template v-for="form in formList">
                <el-menu-item :index="form.formId+''" @click="changeForm(form)">{{form.title}}</el-menu-item>
            </template>
        </el-menu>
        <div style="text-align:center;" v-if="formFieldGroup.length>0">
            <div class="form-container">
                <center>
                    <h2>{{formInfo.title}}</h2>
                </center>
                <el-form ref="form" :model="form" label-width="100px" style="margin-top:40px;">
                    <el-row v-for="group in formFieldGroup">
                        <template v-for="field in group">
                            <el-col :span="field.colWidth">
                                <el-form-item :label="field.label">
                                    <template v-if="field.type == 'text'">
                                        <el-input v-model="form[field.name]"></el-input>
                                    </template>
                                    <template v-if="field.type == 'radio'">
                                        <el-radio-group v-model="form[field.name]">
                                            <template v-for="vl in field.fieldValues">
                                                <el-radio :label="vl.value">{{vl.label}}</el-radio>
                                            </template>
                                        </el-radio-group>
                                    </template>
                                    <template v-if="field.type == 'select'">
                                        <el-select v-model="form[field.name]">
                                            <template v-for="vl in field.fieldValues">
                                                <el-option :label="vl.label" :value="vl.value"></el-option>
                                            </template>
                                        </el-select>
                                    </template>
                                    <template v-if="field.type == 'textarea'">
                                        <el-input type="textarea" v-model="form[field.name]"></el-input>
                                    </template>
                                </el-form-item>
                            </el-col>
                        </template>
                    </el-row>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm">提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</body>
<script src="${urls.getForLookupPath('/assets/module/applicationform/applicationmaterials.js')}"></script>
</html>