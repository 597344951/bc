let ProgramTemplate = {
    info: {
        name: 'program-template', //注册组件名
        template_url: '/components/template/program-template.view.html', //模块 页面模板 url
        author: 'Wangch',
        descript: '选择节目模板'
    },
    data() {
        return {
            //加载进度条
            loading:true,
            curCate: {
                name: '所有分类',
                pkId: 0
            },
            catelogTreeData: [],
            defaultProps: {
                children: 'children',
                label(data, node) {
                    return data.data.name;
                }
            },
            programTemplates: [],
            tpager: {
                total: 0,
                current: 1,
                size: 10
            }
        };
    },
    mounted() {
        this.loadCategory();
        this.loadCategoryProgram();
    },
    methods: {
        //选择类别目录
        selectCatelog(data) {
            if(this.loading)return;
            this.curCate = data.data;
            this.loadCategoryProgram();
        },
        //加载类别信息
        loadCategory() {
            let url = '/pt/category';
            ajax_promise(url, 'get').then((result) => {
                this.catelogTreeData = result.data;
            });
        },
        //加载类别节目
        loadCategoryProgram() {
            this.loading = true;
            let url = '/pt/' + this.curCate.pkId + '/ProgramTemplate/' + this.tpager.current + '-' + this.tpager.size;
            ajax_promise(url, 'get').then(result => {
                this.loading = false;
                this.tpager.total = result.pager.total;
                this.programTemplates = result.data;
            });
        },
        //打开节目预览
        openProgramView(pt) {
            let url = '/sola/view/' + pt.programId;
            if(openwindow) {
                openwindow(url,pt.name)
            } else {
                window.open(url, "_blank", "width=800 height=600");
            }
        },
        handleSizeChange(val) {
            this.tpager.size = val;
            this.loadCategoryProgram();
        },
        handleCurrentChange(val) {
            this.tpager.current = val;
            this.loadCategoryProgram();
        },
        //选择节目模版
        choseProgramTempate(pt) {
            this.$emit('chose', pt)
        }

    },
    template: require("./program-template.view.html")
}

export default ProgramTemplate;