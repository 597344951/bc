<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>三会一课统计</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <%@include file="/include/echarts.jsp"%>
    <style>
        body {
                margin: 0px;
                padding: 0px;
                font-size: 12px;
                background-color: #f5f5f5;
            }
            #top_layer {
                padding: 10px;
            }
            #head_layer {
                padding: 0 10px;
            }
            #foot_layer {
                padding: 0 10px;
                margin-top: 10px;
            }
            #head_search {
                width: 100%;
            }
            #foot_search {
                width: 100%;
            }
            #head_body {
                background-color: #fff;
            }
            #foot_body {
                margin-top: 10px;
            }
            #chart_year {
                width: 35%;
                height: 250px;
                padding: 10px;
                float: left;
            }
            #chart_month {
                width: 60%;
                height: 250px;
                padding: 10px;
                float: right;
            }

            .font_red {
                color: red;
            }
            .font_bold {
                font-weight: bold;
            }
            .title-style {
                text-align: left;
                float: left;
                padding-top: 7px;
                padding-right: 10px;
            }
        </style>
</head>

<body>
    <div id="app">
        <div id="top_layer">

            <div id="head_layer">
                <div id="head_search">
                    <p class="font_bold title-style">统计年度</p>
                    <el-select @change="onYearChange" size="small" v-model="statistics.top.condition.search_year"
                        clearable placeholder="请选择">
                        <el-option v-for="item in statistics.top.option_year" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div id="head_body">
                    <div id="chart_year"></div>
                    <div id="chart_month"></div>
                </div>
            </div>

            <div id="foot_layer">
                <div id="foot_search">
                    <!-- <p class="font_bold title-style">统计年度</p>
                    <el-select size="small" v-model="statistics.button.condition.search_year" clearable placeholder="请选择">
                        <el-option v-for="item in statistics.button.option_year" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select> -->
                    <!-- <div style="float: right;" class="margin-left-10">
                        <el-button type="primary" size="small">
                            <i class="el-icon-download"></i>
                            导出EXCEL
                        </el-button>
                    </div> -->
                    <div style="float: right; margin-bottom: 20px;">
                        <el-popover class="margin-left-10" placement="bottom" width="400" trigger="click">
                            <el-button size="small" type="primary" slot="reference">
                                <i class="el-icon-upload2"></i>
                                导入会议记录
                            </el-button>
                            <div>
                                <el-button type="text" @click="export_meeting_example">下载三会一课会议记录导入Excel模板</el-button>
                                <el-upload action="/threeone/schedule/import" :on-success="onUploadSuccess" :on-error="onUploadError">
                                    <el-button type="text">上传三会一课会议记录Excel文件</el-button>
                                </el-upload>
                                <!-- <el-button type="text" @click="show_error_msg">显示导入错误信息</el-button> -->
                            </div>
                        </el-popover>
                    </div>
                </div>
                <div id="foot_body">
                    <el-table border size="small" :span-method="tables_merge" :data="statistics.button.metting_table">
                        <el-table-column prop="sn" label="序号"></el-table-column>
                        <el-table-column prop="org_name" label="党组织"></el-table-column>
                        <el-table-column prop="m_c" label="三会一课" width="100"></el-table-column>
                        <el-table-column label="1月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.jan}}</span>
                                <template v-if="scope.row.jan != '' && scope.row.jan != '0' && scope.row.jan != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="2月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.feb}}</span>
                                <template v-if="scope.row.feb != '' && scope.row.feb != '0' && scope.row.feb != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="3月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.mar}}</span>
                                <template v-if="scope.row.mar != '' && scope.row.mar != '0' && scope.row.mar != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="4月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.apr}}</span>
                                <template v-if="scope.row.apr != '' && scope.row.apr != '0' && scope.row.apr != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="5月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.may}}</span>
                                <template v-if="scope.row.may != '' && scope.row.may != '0' && scope.row.may != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="6月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.june}}</span>
                                <template v-if="scope.row.june != '' && scope.row.june != '0' && scope.row.june != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="7月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.july}}</span>
                                <template v-if="scope.row.july != '' && scope.row.july != '0' && scope.row.july != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="8月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.aug}}</span>
                                <template v-if="scope.row.aug != '' && scope.row.aug != '0' && scope.row.aug != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="9月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.sept}}</span>
                                <template v-if="scope.row.sept != '' && scope.row.sept != '0' && scope.row.sept != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="10月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.oct}}</span>
                                <template v-if="scope.row.oct != '' && scope.row.oct != '0' && scope.row.oct != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="11月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.nov}}</span>
                                <template v-if="scope.row.nov != '' && scope.row.nov != '0' && scope.row.nov != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="12月">
                            <template slot-scope="scope">
                                <span style="vertical-align:middle">{{scope.row.dec}}</span>
                                <template v-if="scope.row.dec != '' && scope.row.dec != '0' && scope.row.dec != 0">
                                    <img src="/view/pm/img/meeting_img.jpg" style="width: 20px; height: 20px;vertical-align:middle;" />
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column prop="total" label="合计"></el-table-column>
                    </el-table>
                </div>
            </div>
        </div>
    </div>
</body>

<script>
    var appInstince = new Vue({
        el: '#app',
        data: {
            threeone: [
                { id: 1, label: '党员小组会' },
                { id: 2, label: '支部党员大会' },
                { id: 3, label: '支部委员会' },
                { id: 4, label: '党课' }
            ],
            statistics: {
                top: {
                    condition: {
                        search_year: '',
                    },
                    option_year: [],
                    monthCount: {},
                    threeoneCount: {}
                },
                button: {
                    condition: {
                        search_year: '',
                    },
                    option_year: [],
                    metting_tables: {},
                    metting_table: []
                }
            },
            chartYear: null,
            chartMonth: null,


        },
        created: function () {

        },
        mounted: function () {
            this.initChart()
            this.loadStatistics()
        },
        methods: {
            initChart() {
                this.chartYear = echarts.init(document.getElementById('chart_year'))
                this.chartMonth = echarts.init(document.getElementById('chart_month'))
            },
            loadStatistics() {
                $.ajax({
                    type: 'GET',
                    url: '/threeone/schedule/count',
                    dataType: 'json',
                    success: resp => {
                        if (resp.status) {
                            let org = resp.org
                            this.statistics.top.option_year = []
                            this.statistics.button.option_year = []
                            this.statistics.button.metting_tables = {}
                            this.statistics.top.monthCount = {}
                            this.statistics.top.threeoneCount = {}
                            for (let year in resp.data) {
                                if (year != 0) {
                                    this.statistics.top.option_year.push({
                                        value: year,
                                        label: year + '年度'
                                    })
                                    this.statistics.button.option_year.push({
                                        value: year,
                                        label: year + '年度'
                                    })
                                    this.statistics.top.monthCount[year] = {}
                                    for (let i = 1; i <= 12; i++) {
                                        this.statistics.top.monthCount[year][i] = resp.data[year][i * -1] ? resp.data[year][i * -1] : 0
                                    }
                                    this.statistics.top.threeoneCount[year] = {}
                                    this.statistics.button.metting_tables[year] = []
                                    for (let i in this.threeone) {
                                        let m = this.threeone[i].id
                                        let label = this.threeone[i].label
                                        this.statistics.top.threeoneCount[year][m] = resp.data[year][m] ? resp.data[year][m][0] : 0
                                        if (resp.data[year][m]) {
                                            this.statistics.button.metting_tables[year].push({
                                                sn: ++i,
                                                org_name: org,
                                                m_c: label,
                                                jan: resp.data[year][m][1] ? resp.data[year][m][1] : '',
                                                feb: resp.data[year][m][2] ? resp.data[year][m][2] : '',
                                                mar: resp.data[year][m][3] ? resp.data[year][m][3] : '',
                                                apr: resp.data[year][m][4] ? resp.data[year][m][4] : '',
                                                may: resp.data[year][m][5] ? resp.data[year][m][5] : '',
                                                june: resp.data[year][m][6] ? resp.data[year][m][6] : '',
                                                july: resp.data[year][m][7] ? resp.data[year][m][7] : '',
                                                aug: resp.data[year][m][8] ? resp.data[year][m][8] : '',
                                                sept: resp.data[year][m][9] ? resp.data[year][m][9] : '',
                                                oct: resp.data[year][m][10] ? resp.data[year][m][10] : '',
                                                nov: resp.data[year][m][11] ? resp.data[year][m][11] : '',
                                                dec: resp.data[year][m][12] ? resp.data[year][m][12] : '',
                                                total: resp.data[year][m][0] ? resp.data[year][m][0] : 0
                                            })
                                        } else {
                                            this.statistics.button.metting_tables[year].push({
                                                sn: ++i,
                                                org_name: org,
                                                m_c: label,
                                                jan: '',
                                                feb: '',
                                                mar: '',
                                                apr: '',
                                                may: '',
                                                june: '',
                                                july: '',
                                                aug: '',
                                                sept: '',
                                                oct: '',
                                                nov: '',
                                                dec: '',
                                                total: 0
                                            })
                                        }
                                    }
                                }
                            }
                            this.statistics.top.condition.search_year = this.statistics.top.option_year[this.statistics.top.option_year.length - 1].value
                            this.statistics.button.condition.search_year = this.statistics.button.option_year[this.statistics.top.option_year.length - 1].value
                            this.statistics.button.metting_table = this.statistics.button.metting_tables[this.statistics.button.condition.search_year]
                        }

                        //渲染图表
                        this.set_chart_month()
                        this.set_chart_year()
                    },
                    error: err => {
                        this.$message.error("系统错误.")
                    }
                });
            },
            set_chart_year() {  //年份总统计图
                setTimeout(() => {  //防止div未渲染
                    option = {
                        title: {
                            text: '年度统计',
                            subtext: '一年总量的统计',
                            x: '35%'
                        },
                        xAxis: {
                            type: 'value',
                            name: '次'
                        },
                        yAxis: {
                            type: 'category',
                            name: '会议/课',
                            data: ['支部党员大会', '支部委员会', '党员小组会', '党课']
                        },
                        grid: {
                            left: 85
                        },
                        series: [{
                            data: [
                                this.statistics.top.threeoneCount[this.statistics.top.condition.search_year][2],
                                this.statistics.top.threeoneCount[this.statistics.top.condition.search_year][3],
                                this.statistics.top.threeoneCount[this.statistics.top.condition.search_year][1],
                                this.statistics.top.threeoneCount[this.statistics.top.condition.search_year][4]
                            ],
                            type: 'bar',
                            itemStyle: {
                                //通常情况下：
                                normal: {
                                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                                    color: function (params) {
                                        var colorList =
                                            [
                                                '#C33531',
                                                '#EFE42A',
                                                '#64BD3D',
                                                '#EE9201'
                                            ];
                                        return colorList[params.dataIndex];
                                    }
                                },
                                //鼠标悬停时：
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }]
                    };
                    this.chartYear.setOption(option);
                }, 500);
            },
            set_chart_month() { //年份月度统计图
                setTimeout(() => {  //防止div未渲染
                    option = {
                        title: {
                            text: '月度统计',
                            subtext: '一年中每个月的统计',
                            x: '40%'
                        },
                        xAxis: {
                            type: 'category',
                            name: this.statistics.top.condition.search_year + '/月',
                            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                        },
                        yAxis: {
                            type: 'value',
                            name: '次'
                        },
                        series: [{
                            data: (() => {
                                let cm = []
                                for (let i = 1; i <= 12; i++) {
                                    cm.push(Number.parseInt(this.statistics.top.monthCount[this.statistics.top.condition.search_year][i]))
                                }
                                return cm
                            })(),
                            type: 'bar',
                            itemStyle: {
                                //通常情况下：
                                normal: {
                                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                                    color: function (params) {
                                        var colorList =
                                            [
                                                '#C33531',
                                                '#EFE42A',
                                                '#64BD3D',
                                                '#EE9201',
                                                '#29AAE3',
                                                '#B74AE5',
                                                '#0AAF9F',
                                                '#E89589',
                                                '#C33531',
                                                '#EFE42A',
                                                '#64BD3D',
                                                '#EE9201',
                                                '#29AAE3',
                                            ];
                                        return colorList[params.dataIndex];
                                    }
                                },
                                //鼠标悬停时：
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }]
                    };
                    this.chartMonth.setOption(option);
                }, 500);
            },
            tables_merge({ row, column, rowIndex, columnIndex }) {  //合并单元格
                if (columnIndex === 1) {
                    if (rowIndex % 4 === 0) {
                        return [4, 1];
                    } else {
                        return [0, 0];
                    }
                }
            },
            export_meeting_example() {  //下载会议模板
                window.open('/view/threeone/schedule/三会一课导入模板.xlsx', '_blank')
            },
            onYearChange(year) {
                if (year) {
                    this.set_chart_month()
                    this.set_chart_year()
                    this.statistics.button.metting_table = this.statistics.button.metting_tables[year]
                }
            },
            onUploadSuccess(response, file, fileList) {
                if (response.status) {
                    this.loadStatistics()
                    this.$message({
                        message: '导入记录成功...',
                        type: 'success'
                    })
                } else {
                    this.$message.error(response.msg)
                }
            },
            onUploadError(err, file, fileList) {
                this.$message.error("系统错误:" + err)
            }
        }
    });

    window.onFocus = function () {
        window.location.reload();
    }
</script>

</html>