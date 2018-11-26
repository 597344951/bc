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
        <title>流动党员管理</title>
        <%@include file="/include/head_notbootstrap.jsp"%>
        <style>
            body {
                margin: 0px;
                padding: 10px;
            }
        </style>
    </head>

    <body>
        <div id="app">
            <div>
                <el-popover
                    placement="bottom" 
                    width="200" 
                    trigger="click" >
                    <el-button size="small" type="primary" slot="reference">
                        <i class="el-icon-search"></i>
                        搜索用户
                    </el-button>
                    <div>
                        <el-input size="small" clearable
                            @change="query_flow_party"
                            v-model="query_condition.name" placeholder="请输入用户名">
                        </el-input>
                    </div>
                </el-popover>
                <span style="float: right;">
                    <el-pagination
                          layout="total, prev, pager, next, jumper" 
                          @current-change="query_flow_party"
                          :current-page.sync="flow_party_pager.pageNum"
                          :page-size.sync="flow_party_pager.pageSize"
                          :total="flow_party_pager.total">
                    </el-pagination>
                </span>
            </div>
            <div>
                <el-table size="small" :data="flow_party_pager.list" stripe>
                    <el-table-column prop="userId" label="ID"></el-table-column>
                    <el-table-column prop="name" label="姓名"></el-table-column>
                    <el-table-column prop="sex" label="性别"></el-table-column>
                    <el-table-column prop="nation" label="民族"></el-table-column>
                    <el-table-column prop="mobilePhone" label="联系电话"></el-table-column>
                    <el-table-column label="党员类型">
                        <template slot-scope="scope">
                            <span>
                                {{scope.row.partyType == 1 ? "正式党员" : scope.row.partyType == 0 ? 
                                "预备党员" : "非党员"}}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column label="党员状态">
                        <template slot-scope="scope">
                            <span>
                                {{scope.row.partyStatus == 1 ? "正常" : scope.row.partyType == 0 ? 
                                "停止党籍" : scope.row.partyStatus == -1 ? "流动党员" : "未知"}}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="flowOrgInfoName" label="流动组织"></el-table-column>
                    <el-table-column prop="flowTime" label="流动时间"></el-table-column>
                    <el-table-column label="更多">
                        <template slot-scope="scope">
                            <el-popover
                                placement="top-start"
                                title="流动原因"
                                trigger="focus"
                                :content="scope.row.reason">
                                <el-button size="small" type="text" slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column label="联系记录">
                        <template slot-scope="scope">
                            <el-popover
                                placement="top-start"
                                width="800"
                                trigger="click">
                                <el-table :data="flow_party_records">
                                    <el-table-column label="是否联系成功">
                                        <template slot-scope="_scope">
                                            {{_scope.row.contact == 1 ? "成功" : "失败"}}
                                        </template>
                                    </el-table-column>
                                    <el-table-column property="contactTime" label="联系时间"></el-table-column>
                                </el-table>
                                <el-button @click="query_flowParty_records(scope.row)" size="small" type="text" slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </body>

    <script>
        var appInstince = new Vue({
            el: '#app',
            data: {
                flow_party_pager: {
                    pageNum: 1,		/* 当前页 */
                    pageSize: 10,	/* 默认页面大小 */
                    total: 0,
                    list: []
                },
                flow_party_records: [],
                query_condition: {
                    name: null
                }
            },
            created: function () {
                this.set_page_size();
            },
            mounted: function () {
                this.query_flow_party();
            },
            methods: {
                set_page_size() {	/*根据屏幕分辨率个性化每页数据记录数*/
                    var obj = this;
                    var height = window.innerHeight;
                    //100是搜索栏高度，50是表格每条数据所占的高度
                    obj.flow_party_pager.pageSize = parseInt((height - 100) / 50);
                },
                initPager() {	/* 初始化页面数据 */
                    var obj = this;
                    obj.flow_party_pager.pageNum = 1;
                    obj.flow_party_pager.total = 0;
                    obj.flow_party_pager.list = new Array();
                },
                query_flow_party() {
                    var obj =  this;
                    var url = "/flow/party/queryFlowPartys";
                    var t = {
                        pageNum: obj.flow_party_pager.pageNum,
                        pageSize: obj.flow_party_pager.pageSize,
                        name: obj.query_condition.name
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            if (data.data == undefined) {	
                                obj.initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
                                toast('提示',"没有查询到信息",'error');
                            } else {
                                obj.flow_party_pager = data.data;
                            }
                        }
                        
                    })
                },
                query_flowParty_records(row) {
                    var obj =  this;
                    var url = "/flow_party/record/queryFlowPartyRecords";
                    var t = {
                        fpId: row.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != undefined) {
                            obj.flow_party_records = data.data;
                        } else {
                            obj.flow_party_records = []
                        }
                        
                    })
                }
            }
        })
    </script>
</html>