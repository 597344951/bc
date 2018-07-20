var TextViewEdit = {
    info: {
        name: 'text-view-edit',
        template_url: '/components/common/text-view-edit.view.html',
        author: 'Wangch',
        descript: '文本可视化/编辑 组件'
    },
    props: {
        display: {
            type: Boolean,
            default: true
        },
        title: {
            type: String,
            default: '编辑'
        },
        mode: {
            //编辑模式, view/edit
            type: String,
            default: 'edit'
        },
        content: {
            type: String,
            default: '测试正文'
        }
    },
    data() {
        return {
            config: {
                visiable: this.display,
                fullscreen: false,
                width: '70%',
                content: this.content
            }
        };
    },
    watch: {
        display(val, oldVal) {
            this.config.visiable = val;
        },
        mode(val){
            if (this.mode == 'edit' && this.config.content) {
               this.initEditor(this.config.content);
            }
        },
        content(val) {
            let me = this;
            this.config.content = val;
            if (this.mode == 'edit') {
                this.initEditor(val);
            }
        },
        "config.visiable": function (val, oldVal) {
            console.debug('内部visiable change:', val, oldVal);
            if (!val) {
                this.$emit('close');
                this.$emit('update:display', false); //触发更新
            } else {

            }
        }
    },
    mounted() {
        let me = this;
        console.log('mounted');
    },
    methods: {
        initEditor(defaultValue){
            let me = this;
            if(me.$refs['ueditor'] == null){
                setTimeout(() => {
                    UE.getEditor('editor').ready(function() {
                        this.setContent(defaultValue)
                        me.$refs['ueditor'] = this;
                    })
                }, 10);
            }else{
                me.$refs['ueditor'] .setContent(defaultValue)
            }
           
        },
        setFullscreen() {
            this.config.fullscreen = !this.config.fullscreen;
            this.config.width = this.config.fullscreen ? '100%' : '70%'
        },
        close() {
            this.$emit('close')
            this.config.visiable = false;
        },
        submit() {
            this.$emit('submit', this.$refs['ueditor'].getContent());
            this.close();
        },
        chose(tp) {
            this.$emit('chose', tp);
            this.close();
        }
    }
}
export default TextViewEdit;