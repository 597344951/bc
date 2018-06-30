var app = new Vue({
    el: '#app',
    data: {
        dis_h_v:false,
        pageSizes: [10, 20, 50, 100],
        pageSize: 10,
        activity:{},
        material: {
            show: false,
            list:[]
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            load(1, pageSize)
        },
        handleCurrentChange(curPage) {
            load(curPage, this.pageSize)
        },
        handleUploadRemove(file, fileList) {
            for(var i=0; i<this.addition.material.length; i++) {
                if(file.name == this.addition.material[i].name) {
                    this.addition.material.splice(i, 1);
                }
            }
        },
        handleUploadSuccess(response, file, fileList) {
            if(response.status) {
                this.material.list.push({
                    uid: new Date().getTime(),
                    url:response.url,
                    desc: ''
                });
            } else {
                this.$message('上传失败,请重新选择上传.');
            }
        },
        handleUploadError(err, file, fileList) {
            this.$message('上传失败,请重新选择上传.');
        },
        beforeRemove(file, fileList) {
            return this.$confirm('确定移除 '+ file.name +'？');
        },
        collectMaterial() {
            this.material.show = true
        },
        deleteMaterial(uid) {
            for(let i in this.material.list) {
                if(uid == this.material.list[i].uid) {
                    this.material.list.splice(i, 1)
                    break
                }
            }
        }
    }
});
load(1, app.pageSize);

function load(pageNum, pageSize) {
    get('/publish/activity/'+ pageNum+'/' + pageSize, reps => {
        if(reps.status) {
            app.activity = reps.data;
        } else {
            app.$message('没有数据...');
        }
    });
    
}

function get(url, callback) {
    $.ajax({
        type:'GET',
        url:url,
        dataType:'json',
        success: function(reps){
            callback(reps);
        },
        error: function (err) {
            app.$message.error('系统错误, 请联系管理员.')
        }
    });
}

function post(url, postData, callback) {
    $.ajax({
        type:'POST',
        url:url,
        dataType:'json',
        contentType: 'application/json',
        data: JSON.stringify(postData),
        success: function(reps){
            callback(reps);
        },
        error: function (err) {
            app.$message.error('系统错误, 请联系管理员.')
        }
    });
}