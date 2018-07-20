window.onFocus = function() {
    window.location.reload()
}

var app = new Vue({
    el: '#app',
    data: {
        dis_h_v:false,
        pageSizes: [10, 20, 50, 100],
        pageSize: 10,
        publishingContent: {},
        additionDialogVisible: false,
        participantDialogVisible: false,
        addition: {
            ordainParticipantNumber: 0,
            material: []
        },
        participateLength: 0,
        participant: []
    },
    methods: {
        handleSizeChange(pageSize) {
            load(1, pageSize)
        },
        handleCurrentChange(curPage) {
            load(curPage, this.pageSize)
        },
        loadAddition(row) {
            get("/publish/activity/addition/" + row.content_id, reps => {
                if (reps.status && reps.data) {
                    this.addition.ordainParticipantNumber = reps.data.ordain_participant_number;
                    this.addition.actualParticipantNumber = reps.data.actual_participant_number;
                    this.addition.summary = reps.data.summary;
                    this.addition.date = new Date(reps.data.activity_date).toLocaleString();
                    this.addition.contentId = row.content_id;
                    this.addition.id = reps.data.activity_addition_id;

                    this.additionDialogVisible = true;
                } else {
                    app.$message('没有活动附加信息...');
                }
            });
        },
        completeAddition() {
            this.$confirm('确认添加？').then(() => {
                post("/publish/activity/addition", this.addition, reps => {
                    if (reps.status) {
                        app.$message('添加成功...');
                        this.additionDialogVisible = false;
                    } else {
                        app.$message('信息补充失败...');
                    }
                });
            }).catch(() => {});
        },
        handleUploadRemove(file, fileList) {
            for (var i = 0; i < this.addition.material.length; i++) {
                if (file.name == this.addition.material[i].name) {
                    this.addition.material.splice(i, 1);
                }
            }
        },
        handleUploadSuccess(response, file, fileList) {
            if (response.status) {
                this.addition.material.push({
                    name: response.name,
                    url: response.url,
                    type: response.type,
                    isFile: response.isFile
                });
            } else {
                this.$message('上传失败,请重新选择上传.');
            }
        },
        handleUploadError(err, file, fileList) {
            this.$message('上传失败,请重新选择上传.');
        },
        beforeRemove(file, fileList) {
            return this.$confirm('确定移除 ' + file.name + '？');
        },
        changeAllParticipateLength() {
            for (var i in this.participant) {
                this.participant[i].participateLength = this.participateLength;
            }
        },
        changeAllParticipated() {
            for (var i in this.participant) {
                this.participant[i].isParticipated = '1';
            }
        },
        loadParticipant(row) {
            get("/publish/activity/participant/" + row.content_id, reps => {
                if (reps.status) {
                    this.participant = [];
                    var p;
                    for (var i in reps.data) {
                        p = reps.data[i];
                        this.participant.push({
                            name: p.username,
                            isParticipated: p.is_participated + '',
                            participateLength: p.participate_length,
                            id: p.activity_participant_id
                        });
                    }
                    this.participantDialogVisible = true;
                } else {
                    app.$message('没有参与人数据...');
                }
            })
        },
        updateParticipant() {
            this.$confirm('确认提交？').then(() => {
                post("/publish/activity/participant", this.participant, reps => {
                    if (reps.status) {
                        app.$message('提交成功...');
                        this.participantDialogVisible = false;
                    } else {
                        app.$message('系统繁忙请稍后再试...');
                    }
                });
            }).catch(() => {});

        }
    }
});
load(1, app.pageSize);

function load(pageNum, pageSize) {
    get('/publish/published/content/' + pageNum + '/' + pageSize, reps => {
        if (reps.status) {
            app.publishingContent = reps.data;
        } else {
            app.$message('没有数据...');
        }
    });

}

function get(url, callback) {
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function (reps) {
            callback(reps);
        },
        error: function (err) {
            app.$notify({
                title: 'ERROR',
                message: '系统错误...',
                type: 'error'
            });
        }
    });
}

function post(url, postData, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(postData),
        success: function (reps) {
            callback(reps);
        },
        error: function (err) {
            app.$notify({
                title: 'ERROR',
                message: '系统错误...',
                type: 'error'
            });
        }
    });
}