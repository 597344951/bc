/**
 * 党员统计信息
 */
import chartConfig from '../../assets/js/vcharts_config.js';
let PmInfo = {
    info: {
        name: 'pm-info', //注册组件名
        template_url: '/components/loadAnimation/pm-info.view.html',
        author: 'Wangch',
        descript: '党员统计信息'
    },
    data() {
        return {
            chartConfig: chartConfig,
            joinTime_chart: {
                title: {
                    text: '党龄分布'
                },
                columns: ['党龄', '人数'],
                rows: [{
                    '党龄': '50以上',
                    '人数': 134
                }, {
                    '党龄': '40-50',
                    '人数': 341
                }, {
                    '党龄': '35-40',
                    '人数': 334
                }, {
                    '党龄': '30-35',
                    '人数': 364
                }, {
                    '党龄': '25-30',
                    '人数': 304
                }, {
                    '党龄': '18-25',
                    '人数': 234
                }]
            },
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
                    },
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
            sex_pie_chart: {
                title: {
                    text: '性别分布'
                },
                chartSettings: {
                    roseType: 'radius'
                },
                columns: ['性别', '人数'],
                rows: [{
                    '性别': '男',
                    '人数': 5015
                }, {
                    '性别': '女',
                    '人数': 5095
                }]
            },
            edu_pie_chart: {
                title: {
                    text: '学历分布'
                },
                chartSettings: {
                    roseType: 'radius'
                },
                columns: ['学历', '人数'],
                rows: [{
                    '学历': '高中以下',
                    '人数': 5015
                }, {
                    '学历': '本科',
                    '人数': 3095
                }, {
                    '学历': '博士以上',
                    '人数': 1095
                }]
            },
            job_pie_chart: {
                title: {
                    text: '工作分布'
                },
                chartSettings: {
                    roseType: 'radius'
                },
                columns: ['工作', '人数'],
                rows: [{
                    '工作': '医生',
                    '人数': 5015
                }, {
                    '工作': '教师',
                    '人数': 3095
                }, {
                    '工作': '工程师',
                    '人数': 10095
                }]
            }
        }
    },
    template: require("./pm-info.view.html")
};

export default PmInfo;