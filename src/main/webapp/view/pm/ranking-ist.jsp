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
        <title>排行榜</title>
        <%@include file="/include/head_notbootstrap.jsp"%>
        <script type="text/javascript" src="/json/address-pca.json"></script>
        <%@include file="/include/echarts.jsp"%>
        <style type="text/css">
            #integral_ranking_title {
                text-align: center; 
                font-size: 18px; 
                font-weight: bold;
                margin: 10px 0px;
            }
            #integralRankingBox{
                border-radius: 10px;
		        transition: transform 0.2s,box-shadow 0.3s;
                box-shadow: 0 8px 15px rgba(0,0,0,0.15);
                width: 350px; 
                height: 500px; 
                border: 1px solid #ebeef5;
            }
            #integralRankingBox:hover {
                box-shadow: 0 8px 15px rgba(0,0,0,0.15);
                transform:translateY(-2px);
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div id="app" style="margin: 20px;">
            <div>
                <div id="integralRankingBox">
                    <div id="integral_ranking_title">前十积分排行榜</div>
                    <div style="text-align:center;">
                        <el-select size="mini" clearable 
                                @change="queryIntegralRanking"
                                v-model="integralRanking.orgInfoId" placeholder="请选择组织">
                            <el-option
                                v-for="item in integralRanking.havePartyUserOrgs"
                                :key="item.orgInfoId"
                                :label="item.orgInfoName"
                                :value="item.orgInfoId">
                                <span style="float: left; margin-right: 15px;">{{item.orgInfoName}}</span>
                                <span style="float: right;">{{item.orgInfoId}}</span>
                            </el-option>
                        </el-select>
                    </div>
                    <div style="text-align:center;margin-top: 10px;">
                        <template>
                            <el-radio :disabled="integralRanking.changeIntegralRankingShowModeRadio" @change="changeIntegralRankingShowMode" 
                                v-model="integralRanking.integralRankingShowMode" label="1">表格</el-radio>
                            <el-radio :disabled="integralRanking.changeIntegralRankingShowModeRadio" @change="changeIntegralRankingShowMode" 
                                v-model="integralRanking.integralRankingShowMode" label="2">图示</el-radio>
                        </template>
                    </div>
                    <div v-show="integralRanking.integralRankingShowMode == 2" 
                        style="width: 330px; height: 400px;" id="integralRanking"></div>
                    <div v-show="integralRanking.integralRankingShowMode == 1" style="margin: 10px;">
                        <el-table size="mini" 
                            :data="integralRanking.tables">
                            <el-table-column align="left" label="人员">
                                <template slot-scope="scope">
                                    <span :style="getRankingStyle(scope.$index + 1)">{{scope.row.name}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column align="left" label="积分">
                                <template slot-scope="scope">
                                    <span :style="getRankingStyle(scope.$index + 1)">{{scope.row.integral}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column align="center" label="名次">
                                <template slot-scope="scope">
                                    <span v-if="scope.$index + 1 > 3">{{scope.$index + 1}}</span>
                                    <span v-if="scope.$index + 1 <= 3">
                                        <img style="width:20px; height: 27px;" :src="getRankingImg(scope.$index + 1)" />
                                    </span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <script type="text/javascript">
        var appInstince = new Vue({
            el: '#app',
            data: {
                integralRanking: {
                    orgInfoId: null,
                    changeIntegralRankingShowModeRadio: false,
                    data: null,
                    tables: null,
                    integralRankingShowMode: "1",
                    havePartyUserOrgs: null
                }
            },
            created: function () {
			
            },
            mounted:function () {
                this.initHavePartyUserOrgSelect();
            },
            methods: {
                initHavePartyUserOrgSelect() {	//搜索条件框-有党员的组织
                    var obj = this;
                    var url = "/org/relation/queryHavePartyUserOrg";
                    var t = {
                        
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != null && data.data.length > 0) {
                            obj.integralRanking.havePartyUserOrgs = data.data;
                            obj.integralRanking.orgInfoId = data.data[0].orgInfoId;
                            obj.queryIntegralRanking();
                        } 
                    })
                },
                changeIntegralRankingShowMode() {
                    let obj = this;
                    if (obj.integralRanking.integralRankingShowMode == "1") {
                        obj.switchIntegralRankingTable();
                    } else {
                        obj.switchIntegralRankingChart();
                    }
                },
                queryIntegralRanking() {
                    let obj = this;
                    obj.integralRanking.integralRankingShowMode = "1";
                    if (obj.integralRanking.orgInfoId == null || obj.integralRanking.orgInfoId == "") {
                        obj.integralRanking.changeIntegralRankingShowModeRadio = true;
                        obj.integralRanking.tables = null;
                        return;
                    } else {
                        obj.integralRanking.changeIntegralRankingShowModeRadio = false;
                    }
                    var url = "/ranking/list/queryIntegralRanking";
                    var t = {
                        orgId: obj.integralRanking.orgInfoId
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != null) {
                            obj.integralRanking.data = data.data;
                            obj.switchIntegralRankingTable();
                        } 
                    })
                },
                switchIntegralRankingTable() {
                    let obj = this;
                    obj.integralRanking.tables = obj.integralRanking.data.tables;
                    var myChart = echarts.init(document.getElementById("integralRanking"));
                    myChart.clear();
                },
                switchIntegralRankingChart() {   //图标切换
                    let obj = this;
                    var myChart = echarts.init(document.getElementById("integralRanking"));
                    myChart.clear();
                    // 基于准备好的dom，初始化echarts实例
                    var lines = obj.integralRanking.data.lines;	/*x轴信息*/
                    var datas = obj.integralRanking.data.datas;
                    // 绘制图表
                    var option = {
                        grid: {
                            right: 50,
                            left: 50,
                            borderWidth:1
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                crossStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        xAxis: [
                            {
                                type: 'value',
                                name: '分数',
                                min: 0,
                                axisLabel: {
                                    formatter: '{value}'
                                }
                            }
                        ],
                        yAxis: [
                            {
                                type: 'category',
                                data: lines,
                                axisPointer: {
                                    type: 'shadow'
                                }
                            }
                        ],
                        series: {
                            name: "当前积分",
                            type: "bar",
                            smooth: true,
                            barWidth: 30,
                            data: datas
                        }
                    };
                    myChart.setOption(option);
                },
                getRankingImg(index) {
                    let obj = this;
                    if (index == 1) {
                        return "/view/pm/img/first.png"
                    } else if (index == 2) {
                        return "/view/pm/img/second.png"
                    } else if (index == 3) {
                        return "/view/pm/img/three.png"
                    }
                },
                getRankingStyle(index) {
                    let obj = this;
                    if (index == 1) {
                        return "font-weight: bold; color: #fc2b02; font-size: 22px;"
                    } else if (index == 2) {
                        return "font-weight: bold; color: #0168f0; font-size: 18px;"
                    } else if (index == 3) {
                        return "font-weight: bold; color: #0ab419; font-size: 14px;"
                    }
                }
            }
        })
    </script>
</html>