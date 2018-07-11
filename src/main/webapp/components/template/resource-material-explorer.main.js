import serverConfig from '/environment/resourceUploadConfig.jsp';
let ResourceMaterialExplorer = {
    info: {
        name: 'resource-material-explorer', //注册组件名
        template_url: '/components/template/resource-material-explorer.view.html', //模块 页面模板 url
        author: 'Wangch',
        descript: '素材浏览器'
    },
    data() {
        return {
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
                keyword:this.keyword
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
            default: '素材浏览'
        },
        //关键词
        keyword: {
            type: String,
            default: ''
        },
        type:{
            type: String,
            default: ''
        }
    },
    mounted() {
        this.loadTreeData();
        this.loadTpTypeData(null, this);
        console.debug('加载数据:', this.config.visiable);
    },
    computed: {
        breadcrumbData() {
            let bp = breadPath(this.currentCategory, this.tpt_data, item => item.children, item => item.parent, item => item.albumId, item => item.data);
            return bp;
        }
    },
    watch: {
        type(val, oldVal){
            this.config.type = val;
        },
        keyword(val, oldVal){
            this.config.keyword = val;
        },
        display(val, oldVal) {
            console.debug('外部visiable change:', val, oldVal);
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
    methods: {
        search(){
            this.loadTpTypeData();
        },
        breadPathClick(item) {
            let cc = this.currentCategory;
            if (cc.albumId == item.albumId) return;
            this.currentCategory = item;
            this.loadTpTypeData(item);
        },
        getResUrl(url) {
            return serverConfig.getUrl(url);
        },
        setFullscreen() {
            this.config.fullscreen = !this.config.fullscreen;
            this.config.width = this.config.fullscreen ? '100%' : '70%'
        },
        tptTreeClick: function (_data, node) {
            var ins = this;
            var data = _data.data; // 类别节点数据
            this.loadTpTypeData(data, ins);
        },
        // 加载分类数据
        loadTpTypeData: function (data) {
            let ins = this;
            if (data) {
                this.currentCategory = data;
            } else {
                data = {};
            }
            let url = '/resource/Material/' + this.tpager.current + '-' + this.tpager.size;
            data.keyword = this.config.keyword?this.config.keyword:null;
            data.type = this.config.type?this.config.type:null;
            ajax_json(url, "post", data, function (result) {
                ins.tps = ins.initData(result.data);
                ins.tpager.total = result.pager.total;
            });
        }, // 重新加载分类数据
        reloadTpTypeData: function () {
            var node = this.$refs.tree.getCurrentNode();
            this.loadTpTypeData(node ? node.data : null, this);
        }, // 加载类别树的数据
        loadTreeData: function () {
            var ins = this;
            let data = {
                keyword: this.config.keyword?this.config.keyword:null,
                type:this.config.type?this.config.type:null
            };
            ajax("/MaterialAlbum/Album", "get", data, function (result) {
                ins.tpt_data = result.data;
                ins.tpt_data_normal = ins.toNormalData(result.data);
            });
        },
        handleSizeChange(val) {
            this.tpager.size = val;
            this.loadTpTypeData();
        },
        handleCurrentChange(val) {
            this.tpager.current = val;
            this.loadTpTypeData();
        },
        //转化为普通数据
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
        },
        initData(data) {
            let me = this;
            data.forEach(element => {
                element.showtoolbar = false;
                element.cfv = false;
                element.chose = me.choseResource.filter(e2 => e2.materialId == element.materialId).length > 0;
            });
            return data;
        },
        card_hover(it) {
            it.showtoolbar = true;
        },
        card_leave(it) {
            it.showtoolbar = false;
        },
        chose(tp) {
            tp.chose = !tp.chose;
            if (tp.chose) {
                this.choseResource.push(tp);
            } else {
                this.choseResource = this.choseResource.filter(item => item.chose);
            }
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
            if (clearOnSubmit) {
                this.choseResource = [];
            }
        }
    }
}

export default ResourceMaterialExplorer