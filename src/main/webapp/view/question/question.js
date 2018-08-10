const app = new Vue({
    el: '#app',
    data() {
        return {
            defaultTreeProps: {
                children: 'children',
                label: (data, node) => {
                    console.log(node)
                    return data.label
                }
            },
            questionFilter: [],
            questionMeta: [{
                label: '科目',
                type: 'subject',
                children: []
            }, {
                type: 'category',
                label: '类别',
                children: []
            }],
            meta: {
                show: false,
                title: '',
                type: '',
                data: {
                    name: '',
                    desc: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入名称', trigger: 'blur' }
                    ],
                    desc: [
                        { required: true, message: '请输入描述', trigger: 'blur' }
                    ]
                }
            },
            keyword: '',

            questions: {
                total: 0,
                pageSize: 20,
                pageNum: 1,
                list: []
            },
            question: {
                show: false,
                data: {
                    subject: '',
                    category: '',
                    type: '',
                    content: '',
                    answers: []
                }
            },
            questionImport: {
                show: false,
                data: {
                    subject: '',
                    category: '',
                    type: '',
                    importFile: ''
                }
            }
        }
    },
    computed: {
        questionMetaMap: function () {
            let map = {}
            this.questionMeta.forEach(item => {
                map[item.type] = item
            })
            return map
        }
    },
    mounted() {
        this.loadSubject()
        this.loadCategory()
        this.loadQuestion(1, this.questions.pageSize)
    },
    methods: {
        addKeyword() {
            if (this.keyword) {
                let filter = {
                    id: new Date().getTime(),
                    type: 'keyword',
                    label: this.keyword,
                    value: this.keyword
                }
                if (!this.isFilterContain(filter, this.questionFilter)) {
                    this.questionFilter.push(filter)
                    this.loadQuestion(1, this.questions.pageSize)
                } else {
                    this.$message({
                        message: '请忽重复添加关键字',
                        type: 'warning'
                    })
                }
                this.keyword = ''
            }
        },
        addMetaFilter(meta) {
            if (!meta.hasOwnProperty('children')) {
                let filter = {
                    id: new Date().getTime(),
                    type: meta.type,
                    label: meta.label,
                    value: meta.id
                }
                if (!this.isFilterContain(filter, this.questionFilter)) {
                    this.questionFilter.push(filter)
                    this.loadQuestion(1, this.questions.pageSize)
                } else {
                    this.$message({
                        message: '已添加本项过滤',
                        type: 'warning'
                    })
                }

            }
        },
        isFilterContain(filter, filters) {
            for (let i in filters) {
                if (filter.type == filters[i].type && (filter.value == 'keyword' || filter.value == filters[i].value)) {
                    return true
                }
            }
            return false
        },
        removeFilter(filter) {
            this.questionFilter.splice(this.questionFilter.indexOf(filter), 1)
            this.loadQuestion(1, this.questions.pageSize)
        },
        checkAnswerFormat(rule, value, callback) {
            let field = rule.field, answer
            let hasCorrect = false
            for (let i in this.question.data.answers) {
                answer = this.question.data.answers[i]
                if (answer.isCorrect) {
                    hasCorrect = true
                }
                if (answer.uid == field) {
                    if (!answer.content) {
                        callback(new Error('请输入答案内容'))
                    }
                }
            }
            if (!hasCorrect) {
                callback(new Error('请至少勾选一个正确答案'))
            } else {
                callback()
            }
        },
        addMeta(type) {
            this.meta.type = type
            this.meta.title = type === 'subject' ? '新增科目' : '新增类目'
            this.meta.show = true
        },
        loadSubject() {
            get('/question/subject', reps => {
                if (reps.status) {
                    for (let i in app.questionMeta) {
                        let m = app.questionMeta[i]
                        if (m.type == 'subject') {
                            m.children = []
                            reps.data.forEach(item => {
                                item.id = item.subjectId,
                                    item.label = item.name,
                                    item.type = 'subject'
                                m.children.push(item)
                            })
                            break
                        }
                    }
                }
            })
        },
        loadCategory() {
            get('/question/category', reps => {
                if (reps.status) {
                    for (let i in app.questionMeta) {
                        let m = app.questionMeta[i]
                        if (m.type == 'category') {
                            m.children = []
                            reps.data.forEach(item => {
                                item.id = item.categoryId,
                                    item.label = item.name,
                                    item.type = 'category'
                                m.children.push(item)
                            })
                            break
                        }
                    }
                }
            })
        },
        commitMeta() {
            this.$refs.metaForm.validate((valid) => {
                if (valid) {
                    this.$confirm('确认提交该类目?', '确认提交', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.meta.show = false
                        let url
                        if ('subject' == this.meta.type) {
                            url = '/question/subject'
                        } else if ('category' == this.meta.type) {
                            url = '/question/category'
                        } else {
                            app.$message('错误类型添加')
                            return
                        }
                        postJson(url, { name: this.meta.data.name, description: this.meta.data.desc }, reps => {
                            if (reps.status) {
                                app.$message('添加成功')
                                app.resetMeta()
                                if ('subject' == this.meta.type) {
                                    app.loadSubject()
                                } else if ('category' == this.meta.type) {
                                    app.loadCategory()
                                }
                            } else {
                                app.$message.error(reps.msg)
                            }
                        })
                    }).catch(() => { })
                } else {
                    return false;
                }
            })
        },
        resetMeta() {
            this.$refs.metaForm.resetFields()
        },
        loadQuestion(pageNum, pageSize) {
            let postData = { type: [1], keyword: '', subject: [], category: [], pageNum: pageNum, pageSize: pageSize }
            this.questionFilter.forEach(item => {
                if ('keyword' == item.type) {
                    postData.keyword = item.value
                } else if ('subject' == item.type) {
                    postData.subject.push(item.value)
                } else if ('category' == item.type) {
                    postData.category.push(item.value)
                }
            })
            let url = "/question/query"
            postJson(url, postData, reps => {
                if (reps.status) {
                    this.questions.list = reps.data
                    this.questions.total = reps.total
                }
            })

        },
        addAnswer() {
            this.question.data.answers.push({
                uid: '' + new Date().getTime(),
                content: '',
                isCorrect: false
            })
        },
        removeAnswer(answer) {
            for (let i in this.question.data.answers) {
                if (answer.uid == this.question.data.answers[i].uid) {
                    this.question.data.answers.splice(i, 1)
                    break
                }
            }
        },
        commitQuestion() {
            this.$refs.questionForm.validate((valid) => {
                if (valid) {
                    if (!this.question.data.answers || this.question.data.answers.length == 0) {
                        this.$alert('答案呢...还没有添加答案呢...', '添加答案', {
                            confirmButtonText: '现在添加'
                        })
                        return false
                    }
                    let msg, confirm, cancel
                    if (this.question.data.answers.length <= 2) {
                        msg = `只有${this.question.data.answers.length}个答案, 遗漏了把 ?`
                        confirm = '不可能'
                        cancel = '哎哟,还真是'
                    } else if (this.question.data.answers.length >= 5) {
                        msg = `${this.question.data.answers.length}个答案, 有这么多 ?`
                        confirm = '就有这么多'
                        cancel = '哈....手抖了'
                    } else {
                        msg = `确认提交 ?`
                        confirm = '确认'
                        cancel = '再看看'
                    }
                    this.$confirm(msg, '确认提交', {
                        confirmButtonText: confirm,
                        cancelButtonText: cancel,
                        type: 'warning'
                    }).then(() => {
                        let postData = {}
                        postData.content = this.question.data.content
                        postData.type = { typeId: this.question.data.type }
                        postData.category = { categoryId: this.question.data.category }
                        postData.subject = { subjectId: this.question.data.subject }
                        postData.answers = []
                        this.question.data.answers.forEach(item => {
                            postData.answers.push({
                                content: item.content,
                                type: item.isCorrect ? 1 : 0
                            })
                        })
                        let url = "/question/add"
                        postJson(url, postData, reps => {
                            if (reps.status) {
                                app.$message('录入成功')
                                app.resetQuestion()
                                //刷新数据
                                app.loadQuestion(1, app.questions.pageSize)
                            } else {
                                app.$message.error(reps.msg)
                            }
                        })
                        this.question.show = false
                    }).catch(() => { })
                } else {
                    return false;
                }
            })
        },
        resetQuestion() {
            this.$refs.questionForm.resetFields()
        },
        handleCurrentChange(cur) {
            this.loadQuestion(cur, this.questions.pageSize)
        },
        handleUploadSuccess(response, file, fileList) {
            if (response.status) {
                this.questionImport.data.importFile = response.fileName
            }
        },
        importQuestion() {
            this.$refs.questionImportForm.validate((valid) => {
                if (valid) {
                    if (!this.questionImport.data.importFile) {
                        this.$alert('题库文件...还没有添加题库文件...', '添加题库文件', {
                            confirmButtonText: '现在添加'
                        })
                        return false
                    }

                    this.$confirm('确认提交 ?', '确认提交', {
                        confirmButtonText: '确认',
                        cancelButtonText: '再看看',
                        type: 'warning'
                    }).then(() => {
                        let url = "/question/import"
                        postJson(url, this.questionImport.data, reps => {
                            if (reps.status) {
                                app.$message(`成功添加${reps.correct}条记录, 错误记录: [${reps.errorRow.join(',')}]`)
                                app.loadQuestion(1, app.questions.pageSize)
                            } else {
                                app.$message.error(reps.msg)
                            }
                        })
                        this.questionImport.show = false
                    }).catch(() => { })
                } else {
                    return false;
                }
            })
        },
        resetQuestionImport() {
            this.$refs.questionImportForm.resetFields()
        },
        removeQuestion(row) {
            this.$confirm('确认删除 ?', '确认删除', {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let url = "/question/delete"
                postJson(url, row, reps => {
                    if (reps.status) {
                        app.$message('删除成功...')
                        app.loadQuestion(1, app.questions.pageSize)
                    } else {
                        app.$message.error(reps.msg)
                    }
                })
            }).catch(() => { })
        },
        share() {
            let share = [{weburl: '/html/questionnaire_view.html', playtime: 30*60}]
            let url = '/view/publish/new.jsp?title=知识问答&startStep=2&url=' + encodeURIComponent(JSON.stringify(share))
            if (parent.addTab) {
                parent.addTab({ menuId: -4, name: '知识问道投屏', url: url })
            } else {
                window.location.href = url
            }
        }
    }

})

function get(url, callback) {
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function (reps) {
            if (callback) callback(reps)
        },
        error: function (err) {
            app.$message.error('系统错误')
        }
    });
}

function post(url, postData, callback) {
    var postStr = '';
    for (var key in postData) {
        postStr += key + '=' + postData[key] + '&';
    }
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: postStr,
        success: function (reps) {
            if (callback) callback(reps)
        },
        error: function (err) {
            app.$message.error('系统错误')
        }
    });
}

function postJson(url, postData, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: JSON.stringify(postData),
        contentType: 'application/json',
        success: function (reps) {
            if (callback) callback(reps)
        },
        error: function (err) {
            app.$message.error('系统错误')
        }
    });
}