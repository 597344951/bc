<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>参加活动追踪</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>

<%@include file="/include/mock.jsp"%>
</head>

<body>
    <div id="app" class="height_full">
        <div id="toolbar">
            <el-row>
                <el-col :span="24">
                    <el-menu class="el-menu-demo" mode="horizontal">
                        <el-menu-item index="-1" class="normal-menu-item" disabled>参与活动 </el-menu-item>
                        <el-menu-item index="-3" class="normal-menu-item" disabled>
                            <el-button-group class="control-button">
                                <el-button size="small" :type="dis_h_v==1?'primary':''" icon="el-icon-menu" @click="changeType(1);dis_h_v=1">可参加</el-button>
                                <el-button size="small" :type="dis_h_v==2?'primary':''" icon="el-icon-menu" @click="changeType(2);dis_h_v=2">已报名</el-button>
                                <el-button size="small" :type="dis_h_v==3?'primary':''" icon="el-icon-tickets" @click="changeType(3);dis_h_v=3">已结束</el-button>
                            </el-button-group>
                        </el-menu-item>
                        <el-menu-item index="-2" :style="{'float':'right'}" class="normal-menu-item" disabled>
                            <el-pagination class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]" :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper"
                                :total="tpager.total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
                            </el-pagination>
                        </el-menu-item>
                    </el-menu>
                </el-col>
            </el-row>
        </div>
         
        <div >

            <table class="table">
                <thead>
                    <tr>
                        <th>活动主题</th>
                        <th>发起人</th>
                        <th>开始时间</th>
                        <th>公示状态</th>
                        <th>活动详情</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <template v-for="data in activities">
                        <tr>
                            <td>{{data.title}}</td>
                            <td>{{getUserName(data)}}</td>
                            <td>{{data.stime | date}}</td>
                            <td>
                                <el-popover placement="top-start" title="公示投票结果" width="250" trigger="hover" @show="showVotingInfo(data)">
                                    <el-progress :text-inside="true" :stroke-width="18" :percentage="votingResult.yesCount*100/votingResult.total" status="success"></el-progress>
                                    <ul style="margin-top:12px;">
                                        <li>赞成: {{votingResult.yesCount}}票</li>
                                        <li>反对: {{votingResult.noCount}} 票</li>
                                        <li>总共: {{votingResult.total}} 票</li>
                                    </ul>
                                    <el-tag slot="reference" :type="getStatusType(data)">{{getStatusName(data)}}</el-tag>
                                </el-popover>
                            </td>
                            <td>
                                <a :href="'/event/plan/view/'+data.eventPlanId" target="_blank">查看</a>
                            </td>
                            <td>
                                <el-button-group>
                                    <template v-if="!data.userVoting && 4 > data.status">
                                        <el-button type="primary" icon="el-icon-edit" size="small">投票</el-button>
                                    </template>
                                    <template v-if="!data.userRegist && (data.status == 4 || data.status == 3)">
                                        <el-button type="primary" icon="el-icon-edit" size="small" @click="registPlan(data)">报名</el-button>
                                    </template>
                                    <template v-if="!data.userSign && data.status == 5">
                                        <el-button type="primary" icon="el-icon-edit" size="small" @click="signPlan(data)">签到</el-button>
                                    </template>
                                    <template v-if="data.userSign && data.status == 5">
                                        已签到: {{data.userSign.time|datetime}}
                                    </template>
                                    <template v-if="data.status == 6">
                                        <el-button type="primary" icon="el-icon-edit" size="small">评价</el-button>
                                    </template>
                                    <template v-if="data.status == 6">
                                        <el-button type="primary" icon="el-icon-edit" size="small">提交素材</el-button>
                                    </template>
                                </el-button-group>
                            </td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </div>
        <!--dialog-->
        <el-dialog :title="registWindow.planInfo.title +'活动报名'" :visible.sync="registWindow.visible">
                <el-form ref="form" :model="registWindow.data" label-width="100px" >
                    <el-form-item label="人员姓名">
                        <el-input v-model="registWindow.data.name"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话">
                            <el-input v-model="registWindow.data.tel"></el-input>
                        </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="commitRegist">立即创建</el-button>
                        <el-button @click="registWindow.visible=false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        <!--dialog-->
    </div>
</body>
<script type="module" src="${urls.getForLookupPath('/assets/module/planjoin/planjoin.js')}"></script>
</html>