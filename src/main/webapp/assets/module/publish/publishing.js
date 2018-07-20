window.onFocus = function() {
    window.location.reload()
}

var app = new Vue({
    el: '#app',
    data: {
        dis_h_v:false,
        pageSizes: [10, 20, 50, 100],
        pageSize: 10,
        publishingContent:{},
        publishTerminal: {
            show: false,
            list: []
        }
    },
    methods: {
        handleSizeChange(pageSize) {
            load(1, pageSize)
        },
        handleCurrentChange(curPage) {
            load(curPage, this.pageSize)
        },
        offline(row) {
            this.$confirm('确认移除？').then(()=>{
                get('/publish/process/offline/' + row.content_id + '?byType=contentId', reps => {
                    if(reps.status) {
                        app.$message('下线成功 !');
                        load(1, app.pageSize);
                    } else {
                        app.$message('下线失败 !');
                    }
                })
            }).catch(()=>{});
        },
        view(row) {
            if(!row.snapshot) {
                this.$message("预览失败...");
                return;
            }
            window.open ('/sola/view/' + row.snapshot, 'view', 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
        },
        viewTerminal(row) {
            let url = '/publish/publishTerminal/' + row.content_id
            get(url, reps => {
                this.publishTerminal.list = reps.data
                this.publishTerminal.show = true
            })
        }
    }
});
load(1, app.pageSize);

function load(pageNum, pageSize) {
    get('/publish/publishing/content/'+ pageNum+'/' + pageSize, reps => {
        if(reps.status) {
            app.publishingContent = reps.data;
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
            app.$notify({
                title: 'ERROR',
                message: '系统错误...',
                type: 'error'
            });
        }
    });
}