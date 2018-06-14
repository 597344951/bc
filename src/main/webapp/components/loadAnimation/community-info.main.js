/**
 * 社区统计信息
 */
import chartConfig from '/assets/js/vcharts_config.js';
let CommunityInfo = {
    info: {
        name: 'community-info', //注册组件名
        template_url: '/components/loadAnimation/community-info.view.html', //模块 页面模板 url
        author: 'Wangch',
        descript: '社区统计信息'
    },
    props: {},
    data() {
        return {
            chartConfig: chartConfig,
            age_pie_chart: {
                title: {
                    text: '年龄分布'
                },
                chartSettings: {
                    roseType: 'radius'
                },
                columns: ['年龄', '人口数'],
                rows: [{
                        '年龄': '1-18',
                        '人口数': 1393
                    },
                    {
                        '年龄': '19-30',
                        '人口数': 3485
                    } ,
                    {
                        '年龄': '30-60',
                        '人口数': 3485
                    },
                    {
                        '年龄': '60-以上',
                        '人口数': 1393
                    } 
                ]
            },
            shequinfo:[{
                shequ:'衢州路社区'
            }]
        };
    },
    mounted() {
        this.map()

    },
    methods: {
        map() {
            let map = new BMap.Map(this.$refs.allmap); // 创建Map实例  
            map.centerAndZoom(new BMap.Point(116.404, 39.915), 11); // 初始化地图,设置中心点坐标和地图级别  
            map.addControl(new BMap.MapTypeControl({ //添加地图类型控件  
                mapTypes: [
                    BMAP_NORMAL_MAP,
                    BMAP_HYBRID_MAP
                ]
            }));
            map.setCurrentCity("北京"); // 设置地图显示的城市 此项是必须设置的  
            map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放  
        }

    }

}
export default CommunityInfo;