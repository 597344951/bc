<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib
prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
    <meta charset="UTF-8" />
    <title>词云</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <link rel="stylesheet" href="/view/wordcloud/index.css" />
</head>

<body>
    <div id="app">
        <el-container>
            <el-header>
                <h3>词云</h3>
            </el-header>
            <el-main>
                <el-form ref="wordcloudForm" :model="wordcloud" label-width="120px">
                    <el-form-item label="标题">
                        <el-input v-model="wordcloud.title"></el-input>
                    </el-form-item>
                    <el-form-item label="宽-高">
                        <el-col :span="5">
                            <el-input v-model="wordcloud.width" :placeholder="800"></el-input>
                        </el-col>
                        <el-col class="line" :span="1">x</el-col>
                        <el-col :span="5">
                            <el-input v-model="wordcloud.height" :placeholder="600"></el-input>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="是否已格式化">
                        <el-switch v-model="wordcloud.formated" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                    </el-form-item>
                    <el-form-item label="文字段落">
                        <el-input type="textarea" v-model="wordcloud.text" rows="10"></el-input>
                    </el-form-item>
                    <el-form-item label="文本文件">
                        <el-upload action="${mediaServe}/upload" :limit="1" :on-success="onTextUploadSuccess"
                            :on-remove="onTextRemove">
                            <el-button slot="trigger" size="mini" type="primary">选取文件</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传文本文件</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="形状底图">
                        <el-button size="mini" type="primary" @click="shape.show = true">选择形状</el-button>
                        <el-button size="mini" type="text" @click="onShapeRemove">移除</el-button>
                        <br />
                        <el-card v-if="wordcloud.imageShape" class="shape">
                            <img :src="wordcloud.imageShape">
                        </el-card>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onView">查看词云</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
        <el-dialog title="词云图" :visible.sync="view.show" center width="80%">
            <div style="text-align: center;">
                <img :src="view.url">
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="onShare">投屏展示</el-button>
            </span>
        </el-dialog>
        <el-dialog title="选择形状" :visible.sync="shape.show" center width="750px">
            <el-card class="shape" v-for="(shape, index) in shape.list" :key="index">
                <img :src="shape" @click="onShapeSelected(shape)">
            </el-card>
        </el-dialog>
    </div>
</body>
<script>
    const mediaServe = '${mediaServe}'
</script>
<script src="/components/xx-components.js"></script>
<script src="/view/wordcloud/index.js"></script>

</html>