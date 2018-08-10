window.appInstince = new Vue({
    el: '#app',
    data: {
        tpager: {
            current: 1,
            size: 20,
            total: 0
        },
        formDatas: []

    },
    mounted() {
        this.loadUserData();
    },
    computed: {},
    methods: {
        loadUserData() {
            let pageNum = this.tpager.current;
            let pageSize = this.tpager.size;
            let url = `/application-user/form-data/query/${pageNum}-${pageSize}`;
            ajax_json_promise(url, 'post', {}).then(result => {
                this.formDatas = result.data;
                this.tpager.total = result.pager.total;
            });
        },
        handleSizeChange(val) {
            this.tpager.size = val;
            this.loadUserData()
        },
        handleCurrentChange(val) {
            this.tpager.current = val;
            this.loadUserData()
        },
        toGroupFiels(fields) {
            let ay = [];
            fields.forEach(element => {
                let ny = ay[element.rowIndex] || [];
                ny.push(element);
                ay[element.rowIndex] = ny;
            });
            return ay;
        },
        getFieldValue(data, field) {
            return data.datas.filter(item => {
                return item.fieldId == field.fieldId
            }).map(item => item.value).join('');
        }

    }
})