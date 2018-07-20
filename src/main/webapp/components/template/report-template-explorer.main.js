let ReportTemplateExplorer = {
    info: {
        name: 'report-template-explorer',
        template_url: '/components/template/report-template-explorer.view.html',
        author: 'Wangch',
        descript: '报告模版浏览器'
    },
    data() {
        return {
            resourceView: {
                title: '',
                visible: false,
                content: ''
            },
            dialogVisible: true,
            choseResource: [],
            currentCategory: {
                name: '所有类别'
            },
            tpt_data: [],
            tps: [],
            tpt_data_normal: [],
            props: {
                children: 'children',
                label(data, node) {
                    let label = data.data.name;
                    if (data.data.count) {
                        label += '(' + data.data.count + ')';
                    }
                    return label;
                }
            },
            tpager: {
                total: 0,
                current: 1,
                size: 10
            },
            config: {
                visiable: this.display,
                fullscreen: false,
                width: '70%',
                //提交后清除数据
                clearOnSubmit: true,
                type: this.type,
                keyword: this.keyword
            }
        }
    },
    props: {
        display: {
            type: Boolean,
            default: true
        },
        title: {
            type: String,
            default: '文档模版浏览'
        },
        //关键词
        keyword: {
            type: String,
            default: ''
        } 
    },
    watch: {
        keyword(val, oldVal){
            this.config.keyword = val;
        },
        display(val, oldVal) {
            this.config.visiable = val;
        },
        "config.visiable": function (val, oldVal) {
            console.debug('内部visiable change:', val, oldVal);
            if (!val) {
                this.$emit('close');
                this.$emit('update:display', false); //触发更新
            }else{
                this.loadTreeData();
                this.loadTpTypeData(null, this);
                console.debug('加载数据:', this.config.visiable);
            }
        }
    },
    mounted() {
        this.loadTreeData();
        this.loadTpTypeData(null, this);
    },
    computed: {
        breadcrumbData() {
            let bp = breadPath(this.currentCategory, this.tpt_data, item => item.children, item => item.parent, item => item.typeId, item => item.data);
            return bp;
        }
    },
    methods: {
        //查询模板
        searchTemplate() {
            this.currentCategory.name = '所有类别';
            this.currentCategory.typeId = 0;
            this.loadTpTypeData();
            this.loadTreeData();
        },
        // 加载分类数据
        loadTpTypeData: function (data) {
            let ins = this;
            if (data) {
                this.currentCategory = data;
            } else {
                data = {};
            }
            let url = '/report/template/' + this.tpager.current + '-' + this.tpager.size;
            data.keyword = this.config.keyword ? this.config.keyword : null;
            ajax_json(url, "post", data, function (result) {
                ins.tps = ins.initData(result.data);
                ins.tpager.total = result.pager.total;
            });
        },
        // 加载类别树的数据
        loadTreeData: function () {
            var ins = this;
            let data = {
                keyword: this.config.keyword ? this.config.keyword : null
            };
            ajax("/report/type", "get", data, function (result) {
                ins.tpt_data = ins.init_tpt_data(result.data);
                ins.tpt_data_normal = ins.toNormalData(result.data);
            });
        },
        initData(data) {
            data.forEach(element => {
                element.showtoolbar = false;
                element.cfv = false;
            });
            return data;
        },
        init_tpt_data(data) {
            data.forEach(item => {
                item.data.showtoolbar = false;
            })
            return data;
        }, //转化为普通数据
        toNormalData(data) {
            function next(obj, chi) {
                if (chi) {
                    let ay = [];
                    chi.forEach(v => {
                        let o = v.data;
                        ay.push(o);
                        next(o);
                    });
                    obj.children = ay;
                } else {
                    return;
                }
            }

            let ret = [];
            data.map((v) => {
                let obj = v.data;
                ret.push(obj);
                next(obj, v.children);
            });
            return ret;
        }, // 类别点击
        tptTreeClick: function (_data, node) {
            var ins = this;
            var data = _data.data; // 类别节点数据
            this.loadTpTypeData(data, ins);
        },

        card_hover(it) {
            it.showtoolbar = true;
        },
        card_leave(it) {
            it.showtoolbar = false;
        },
        handleSizeChange(val) {
            this.tpager.size = val;
            this.loadTpTypeData();
        },
        handleCurrentChange(val) {
            this.tpager.current = val;
            this.loadTpTypeData();
        },
        //预览模板
        viewTemplate: function (tp) {

            //_editor.setContent(tp.content);
            //_editor.execCommand("preview");
            this.resourceView.visible = true;
            this.resourceView.title = tp.title
            this.resourceView.content = tp.content;
        },
        setFullscreen() {
            this.config.fullscreen = !this.config.fullscreen;
            this.config.width = this.config.fullscreen ? '100%' : '70%'
        },
        close() {
            this.$emit('close', this.choseResource)
            this.visiable = false;
            this.config.visiable = false;
        },
        submit() {
            console.debug('提交选择素材', this.choseResource);
            this.$emit('submit', this.choseResource);
            this.config.visiable = false;
            if (this.config.clearOnSubmit) {
                this.choseResource = [];
                this.tps.forEach(item => item.chose = false)
            }
        },
        chose(tp){
            this.$emit('chose', tp);
            this.close();
        }
    }
}

export default ReportTemplateExplorer;