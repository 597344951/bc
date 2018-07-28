import chartConfig from "/assets/js/vcharts_config.js";
var appInstince = new Vue({
    el: '#app',
    data: {
        grid:{
            show: true,
            top: 10,
            left: 10,
            bottom:10,
            borderColor:'none'
        },
        pgrid:{
            show: true,
            bottom:0,
            borderColor:'none'
        },
        fullscreen:false,
        chartConfig: chartConfig,
        oneSettings: {
            limitShowNum: 6,
            roseType: 'radius',
            offsetY: '100',
            radius:"50",

        },
        twoSettings: {
            dataType: {
                '占比': 'percent'
            },
            seriesMap: {
                '占比': {
                    min: 0,
                    max: 1,
                    splitNumber:4,
                    radius: '90%',
                     detail:{
                         offsetCenter: [0, '100%'],
                         textStyle: {
                            fontWeight: 'bolder',
                            color: '#fff',
                            fontSize:'15px',
                          },
                        show:true
                     }
                }
            },
            useDefaultOrder: false,
        },
        fourSettings: {
            yAxisType: ['percent'],
            area: true,
            yAxisName: ['增长率'],
        },
        fiveSettings: {
            showLine: ['收缴完成率'],
            stack: {
                '用户': ['应缴金额', '实缴金额']
            },
            axisSite: {
                right: ['收缴完成率']
            },
            yAxisType: ['KMB', 'percent'],
            yAxisName: ['数值', '完成率'],

        },

        oneData: {

            columns: ['组织类型', '占比'],
            rows: [{
                    '组织类型': '非公企业',
                    '占比': 1393
                },
                {
                    '组织类型': '国有企业',
                    '占比': 3530
                },
                {
                    '组织类型': '机关(事业)单位',
                    '占比': 2923
                },
                {
                    '组织类型': '教育系统',
                    '占比': 1723
                },
                {
                    '组织类型': '农村',
                    '占比': 3792
                },
                {
                    '组织类型': '街道社区',
                    '占比': 4593
                }
            ]
        },
        twoData: {
            columns: ['type', 'value'],
            rows: [{
                type: '占比',
                value: 0.8
            }]
        },
        threeData: {
            columns: ['状态', '数值'],
            rows: [{
                    '状态': '反馈',
                    '数值': 4360
                },
                {
                    '状态': '心得体会',
                    '数值': 2530
                },
                {
                    '状态': '评论',
                    '数值': 1430
                },
            ]
        },
        fourData: {

            columns: ['日期', '全市', '街道', ],
            rows: [{
                    '日期': '1/1',
                    '全市': 0.03,
                    '街道': 0.04,
                },
                {
                    '日期': '3/1',
                    '全市': 0.01,
                    '街道': 0.02,
                },
                {
                    '日期': '5/1',
                    '全市': 0.02,
                    '街道': 0.01,
                },
                {
                    '日期': '7/1',
                    '全市': 0.02,
                    '街道': 0.04,
                },
                {
                    '日期': '9/1',
                    '全市': 0.03,
                    '街道': 0.02,
                },
                {
                    '日期': '11/1',
                    '全市': 0.04,
                    '街道': 0.02,
                }
            ],

        },
        fiveData: {
            title: {
                text: '特性示例',
            },

            columns: ['日期', '应缴金额', '实缴金额', '收缴完成率'],
            rows: [{
                    '日期': '1/1',
                    '应缴金额': 1393,
                    '实缴金额': 1093,
                    '收缴完成率': 0.7846
                },
                {
                    '日期': '3/1',
                    '应缴金额': 3530,
                    '实缴金额': 3230,
                    '收缴完成率': 0.915
                },
                {
                    '日期': '5/1',
                    '应缴金额': 2923,
                    '实缴金额': 2623,
                    '收缴完成率': 0.8973
                },
                {
                    '日期': '7/1',
                    '应缴金额': 1723,
                    '实缴金额': 1423,
                    '收缴完成率': 0.8259
                },
                {
                    '日期': '9/1',
                    '应缴金额': 3792,
                    '实缴金额': 3492,
                    '收缴完成率': 0.9209
                },
                {
                    '日期': '11/1',
                    '应缴金额': 4593,
                    '实缴金额': 4293,
                    '收缴完成率': 0.9347
                }
            ],

        },
        chartExtend: {
            series(v) {
                v.forEach(i => {
                    i.barWidth = 20
                })
                return v
            },
            // tooltip (v) {
            //   v.trigger = 'none'
            //   return v
            // }
        },
    },

    created: function () {},
    mounted() {
        
    },
    methods: {
        setFullScreen(){
            this.fullscreen = true;
            fullScreen(this);
        }
    }
});
window.onload = function(){
    fullScreen(appInstince);
    console.log('ready');
}