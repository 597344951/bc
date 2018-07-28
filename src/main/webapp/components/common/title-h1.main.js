var TitleH1 = {
    info: {
        name: 'title-h1', //注册组件名
        author: 'Wangch',
        descript: 'h1标题组件'
    },
    props: {
        data: { //数据
            type: String,
            default: ' 默认标题 '
        } 
    },
    data: function () {
        return {}
    },
    mounted() {},
    watch: {},
    computed: {},
    methods: { },
    template: '<h1>this is h1 style : {{data}}</h1>'
};

export default TitleH1;