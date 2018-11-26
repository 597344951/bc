<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getServletPath();
%>
<html>

<head>
    <meta charset="utf-8">
    <title>文字快闪</title>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="https://aliyun.beecdn.cn/libs/vue/2.5.17/vue.min.js"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <script src="https://aliyun.beecdn.cn/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="../../components/xx-components.js"></script>
    <style>
        #app {
            overflow: auto;
            height: 100%;
            width: 100%;
        }
        .box {
            width: 80%;
            margin: 10px auto;
        }

        .el-tag {
            margin: 5px 10px;
        }

        .button-new-tag {
            margin-left: 10px;
            height: 32px;
            line-height: 30px;
            padding-top: 0;
            padding-bottom: 0;
        }

        .button-del-words {
            float: right;
            margin: 5px 10px;
        }

        .input-new-tag {
            width: 180px;
            margin: 5px 10px;
            vertical-align: bottom;
        }
        .operate-btn {
            margin-top: 50px;
            text-align: center;
        }
        .preview-close {
            position: fixed;
            right: 10;
            top: 10;
            z-index: 100;
        }
        .preview {
            position: fixed;
            top: 0;
            right: 0;
            left: 0;
            bottom: 0;
            z-index: 99;
        }
    </style>
</head>

<body>
    <div id="app">
        <el-tabs type="border-card" v-model="curTab">
            <el-tab-pane label="文字快闪制作" name="creator">
                <el-container>
                    <el-main>
                        <h3>文字快闪制作</h3>
                        <el-card shadow="hover" class="box">
                            <el-input v-model="title" placeholder="请在此处输入主题内容."></el-input>
                        </el-card>
                        <el-card shadow="hover" class="box" v-for="(w, index) in words" :key="index">
                            <el-tag :key="tag" v-for="tag in w.tags" closable :disable-transitions="false" @close="handleTagClose(index, tag)">
                                {{tag}}
                            </el-tag>
                            <el-input class="input-new-tag" v-if="w.inputVisible" v-model="w.inputValue" ref="saveTagInput"
                                size="small" @keyup.enter.native="handleInputConfirm(index)" @blur="handleInputConfirm(index)">
                            </el-input>
                            <el-button v-else class="button-new-tag" size="small" @click="showInput(index)">+ 新词句</el-button>
                            <el-button class="button-del-words" type="danger" size="small" icon="el-icon-delete" @click="delWords(index)"></el-button>
                        </el-card>
                        <div class="operate-btn">
                            <el-button @click="addWords">添加词句</el-button>
                            <el-button type="primary" @click="preview(null)">预 览</el-button>
                            <el-button type="success" @click="submit">提 交</el-button>
                        </div>
                    </el-main>
                </el-container>
            </el-tab-pane>
            <el-tab-pane label="文字快闪列表" name="list">
                <el-container>
                    <el-header>
                        <h3>列表</h3>
                    </el-header>
                    <el-main>
                        <el-pagination class="pagination" background layout="prev, pager, next" :total="wordsList.total"
                            :page-size="wordsList.pageSize" @current-change="onCurrentPageChange">
                        </el-pagination>
                        <el-table :data="wordsList.list" stripe>
                            <el-table-column prop="title" label="主题">
                            </el-table-column>
                            <el-table-column label="日期" width="360">
                                <template slot-scope="scope">
                                    {{new Date(scope.row.add_date).toLocaleString()}}
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="180">
                                <template slot-scope="scope">
                                    <el-button @click="preview(scope.row)" type="text" size="small">预览</el-button>
                                    <el-button @click="onDelete(scope.row)" type="text" size="small">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-main>
                </el-container>
            </el-tab-pane>
        </el-tabs>
        <div class="preview" v-if="previewVisible">
            <flashing :words="playWords" delay="800" loop="true"></flashing>
        </div>
        <el-button v-if="previewVisible" class="preview-close" icon="el-icon-close" circle @click="closePreview"></el-button>
    </div>
</body>
<script>
    const app = new Vue({
        el: '#app',
        data: {
            curTab: 'creator',
            title: '',
            words: [],
            playWords: [],
            previewVisible: false,
            wordsList: {
                pageSize: 10
            }
        },
        mounted() {
            this.loadWordsList(1, this.wordsList.pageSize)
        },
        methods: {
            handleTagClose(index, tag) {
                this.words[index].tags.splice(this.words.indexOf(tag), 1)
            },
            showInput(index) {
                this.words[index].inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput[0].$refs.input.focus();
                });
            },
            handleInputConfirm(index) {
                let inputValue = this.words[index].inputValue;
                if (inputValue) {
                    this.words[index].tags.push(inputValue);
                }
                this.words[index].inputVisible = false;
                this.words[index].inputValue = '';
            },
            addWords() {
                this.words.push({
                    tags: [],
                    inputVisible: false,
                    inputValue: ''
                })
            },
            delWords(index) {
                this.words.splice(index, 1)
            },
            preview(row) {
                if (row) {
                    /* get("/flashing/preview/" + row.silhouette_id, resp => {
                        if (resp.status) {
                            this.playWords = resp.words
                        }
                    }) */

                    window.open("/view/flashing/flashing-text-preview.jsp?id=" + row.silhouette_id, "_blank")
                } else {
                    this.playWords = []
                    this.words.forEach(word => {
                        this.playWords.push(word.tags)
                    })
                }

                this.previewVisible = true
            },
            closePreview() {
                this.previewVisible = false
            },
            submit() {
                if (!this.title) {
                    this.$message.error('请输入快闪主题...')
                    return
                }
                if (this.playWords.length <= 0) {
                    this.$message.error('请添加快闪文字...')
                    return
                }
                let postData = {
                    title: this.title,
                    words: []
                }
                this.words.forEach(word => {
                    postData.words.push(word.tags)
                })
                post('/flashing/add', postData, resp => {
                    if (resp.status) {
                        this.$message({
                            message: '提交成功.',
                            type: 'success'
                        })
                        this.words = []
                        this.title = ''
                        this.loadWordsList(1, this.wordsList.pageSize)
                    } else {
                        this.$message.error('提交失败: ' + resp.msg)
                    }
                })
            },
            onCurrentPageChange(pageNum) {
                this.loadWordsList(pageNum, this.wordsList.pageSize)
            },
            loadWordsList(pageNum, pageSize) {
                get("/flashing/list/" + pageNum + "/" + pageSize, resp => {
                    if (resp.status) {
                        this.wordsList = resp.data;
                    }
                })
            },
            onDelete(row) {
                get("/flashing/delete/" + row.silhouette_id, resp => {
                    if (resp.status) {
                        this.$message({
                            message: '删除成功.',
                            type: 'success'
                        });
                        this.loadWordsList(this.wordsList.pageNum, this.wordsList.pageSize)
                    }
                })
            }
        }
    })

    function get(url, callback, errorCallback) {
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: resp => {
                if (callback) callback(resp)
            },
            error: (jqXHR, textStatus, errorThrown) => {
                console.error(errorThrown)
                if (errorCallback) errorCallback(jqXHR, textStatus, errorThrown)
            }
        })
    }

    function post(url, postData, callback, errorCallback) {
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(postData),
            success: resp => {
                if (callback) callback(resp)
            },
            error: (jqXHR, textStatus, errorThrown) => {
                console.error(errorThrown)
                if (errorCallback) errorCallback(jqXHR, textStatus, errorThrown)
            }
        })
    }
</script>

</html>