<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事件追踪</title>

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
                        <el-menu-item index="-1" class="normal-menu-item" disabled>已发布活动信息 </el-menu-item>
                        <el-menu-item index="-3" class="normal-menu-item" disabled>
                            <el-button-group class="control-button">
                                <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                                <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
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
        <div v-show="!dis_h_v">
            <template v-for="data in activities">
                <el-card class="card-item card-item4" :body-style="{ padding: '0px' }">
                    <div class="title">
                        <span class="bolder">
                            <i class="el-icon-star-on" style="font-size: 20px;"></i> {{data.title}} </span>
                        <span class="right">
                        </span>
                    </div>
                    <div class="content">
                        <table class="dis-info-min">
                            <tbody>
                                <tr>
                                    <td>
                                        <i class="el-icon-news"></i>发起人</td>
                                    <td></td>
                                    <td>{{getUserName(data)}}</td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="el-icon-time"></i>开始时间</td>
                                    <td></td>
                                    <td>{{data.stime | date}}</td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="el-icon-warning"></i>公示状态</td>
                                    <td></td>
                                    <td>
                                        <el-popover placement="top-start" title="公示投票结果" width="250" trigger="hover">
                                            <el-progress :text-inside="true" :stroke-width="18" :percentage="100" status="success"></el-progress>
                                            <ul style="margin-top:12px;">
                                                <li>赞成: 100票</li>
                                                <li>反对: 0 票</li>
                                            </ul>
                                            <el-tag size="mini" slot="reference" :type="getStatusType(data)">{{getStatusName(data)}}</el-tag>
                                        </el-popover>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="el-icon-edit-outline"></i>策划方案</td>
                                    <td></td>
                                    <td>
                                        <el-dropdown>
                                            <span class="el-dropdown-link">
                                                已提交
                                                <i class="el-icon-arrow-down el-icon--right"></i>
                                            </span>
                                            <el-dropdown-menu slot="dropdown">
                                                <el-dropdown-item @click.native="viewPlan(data)">查看方案</el-dropdown-item>
                                                <el-dropdown-item @click.native="editPlan(data)">修改方案</el-dropdown-item>
                                            </el-dropdown-menu>
                                        </el-dropdown>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="el-icon-document"></i>费用清单</td>
                                    <td></td>
                                    <td>
                                        <el-dropdown>
                                            <span class="el-dropdown-link">
                                                <template v-if="data.costplans && data.costplans.length > 0">
                                                    已提交
                                                </template>
                                                <template v-if="!data.costplans || data.costplans.length == 0">
                                                    未提交
                                                </template>
                                                <i class="el-icon-arrow-down el-icon--right"></i>
                                            </span>
                                            <el-dropdown-menu slot="dropdown">
                                                <template v-if="data.costplans && data.costplans.length > 0">
                                                    <el-dropdown-item @click.native="viewCostPlan(data)">查看方案</el-dropdown-item>
                                                    <el-dropdown-item @click.native="editCostPlan(data)">修改方案</el-dropdown-item>
                                                </template>
                                                <template v-if="!data.costplans || data.costplans.length == 0">
                                                    <el-dropdown-item @click.native="newCostPlan(data)">创建方案</el-dropdown-item>
                                                </template>
                                            </el-dropdown-menu>
                                        </el-dropdown>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="el-icon-tickets"></i>宣传方案</td>
                                    <td></td>
                                    <td>
                                        <el-popover placement="top-start" title="投屏推广" width="250" trigger="hover">
                                            <el-progress :text-inside="true" :stroke-width="18" :percentage="80" status="success"></el-progress>
                                            <p></p>
                                            <p>投屏任务已创建</p>
                                            <p>当前状态: 审核中</p>
                                            <el-button slot="reference" type="text" size="small">投屏推广</el-button>
                                        </el-popover>
                                        <el-popover placement="top-start" title="微信推广" width="250" trigger="hover">
                                            <p>微信推广任务没有创建!</p>
                                            <p>你可以
                                                <el-button type="text" size="small">创建</el-button>
                                            </p>
                                            <el-button slot="reference" type="text" size="small">微信推广</el-button>
                                        </el-popover>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="bottom clearfix">
                        <el-button-group>
                            <el-button type="text" icon="el-icon-edit" size="small">签到统计</el-button>
                            <el-button type="text" icon="el-icon-share" size="small">活动资源</el-button>
                            <el-button type="text" icon="el-icon-delete" size="small">活动评价</el-button>
                        </el-button-group>
                    </div>
                </el-card>
            </template>
        </div>
        <div v-show="dis_h_v">

            <table class="table">
                <thead>
                    <tr>
                        <th>活动主题</th>
                        <th>发起人</th>
                        <th>开始时间</th>
                        <th>公示状态</th>
                        <th>策划方案</th>
                        <th>费用清单</th>
                        <th>宣传方案</th>
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
                                <el-popover placement="top-start" title="公示投票结果" width="250" trigger="hover">
                                    <el-progress :text-inside="true" :stroke-width="18" :percentage="100" status="success"></el-progress>
                                    <ul style="margin-top:12px;">
                                        <li>赞成: 100票</li>
                                        <li>反对: 0 票</li>
                                    </ul>
                                    <el-tag slot="reference"  :type="getStatusType(data)">{{getStatusName(data)}}</el-tag>
                                </el-popover>
                            </td>
                            <td>
                                    <el-dropdown>
                                            <span class="el-dropdown-link">
                                                已提交
                                                <i class="el-icon-arrow-down el-icon--right"></i>
                                            </span>
                                            <el-dropdown-menu slot="dropdown">
                                                <el-dropdown-item @click.native="viewPlan(data)">查看方案</el-dropdown-item>
                                                <el-dropdown-item @click.native="editPlan(data)">修改方案</el-dropdown-item>
                                            </el-dropdown-menu>
                                        </el-dropdown>
                            </td>
                            <td>
                                <el-dropdown>
                                    <span class="el-dropdown-link">
                                        <template v-if="data.costplans && data.costplans.length > 0">
                                            已提交
                                        </template>
                                        <template v-if="!data.costplans || data.costplans.length == 0">
                                            未提交
                                        </template>
                                        <i class="el-icon-arrow-down el-icon--right"></i>
                                    </span>
                                    <el-dropdown-menu slot="dropdown">
                                        <template v-if="data.costplans && data.costplans.length > 0">
                                            <el-dropdown-item @click.native="viewCostPlan(data)">查看方案</el-dropdown-item>
                                            <el-dropdown-item @click.native="editCostPlan(data)">修改方案</el-dropdown-item>
                                        </template>
                                        <template v-if="!data.costplans || data.costplans.length == 0">
                                            <el-dropdown-item @click.native="newCostPlan(data)">创建方案</el-dropdown-item>
                                        </template>
                                    </el-dropdown-menu>
                                </el-dropdown>
                            </td>
                            <td>
                                <el-popover placement="top-start" title="投屏推广" width="250" trigger="hover">
                                    <el-progress :text-inside="true" :stroke-width="18" :percentage="80" status="success"></el-progress>
                                    <p></p>
                                    <p>投屏任务已创建</p>
                                    <p>当前状态: 审核中</p>
                                    <el-button slot="reference" type="text" size="small">投屏推广</el-button>
                                </el-popover>
                                <el-popover placement="top-start" title="微信推广" width="250" trigger="hover">
                                    <p>微信推广任务没有创建!</p>
                                    <p>你可以
                                        <el-button type="text" size="small">创建</el-button>
                                    </p>
                                    <el-button slot="reference" type="text" size="small">微信推广</el-button>
                                </el-popover>

                            </td>
                            <td>
                                <el-button-group>
                                    <el-button type="primary" icon="el-icon-edit" size="small">签到统计</el-button>
                                    <el-button type="primary" icon="el-icon-share" size="small">活动资源</el-button>
                                    <el-button type="primary" icon="el-icon-delete" size="small">活动评价</el-button>
                                </el-button-group>
                            </td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </div>
        <!--文本编辑器-->
        <text-view-edit :display.sync="planDataVE.display" :title="planDataVE.title" :mode="planDataVE.mode" :content="planDataVE.content"
            @submit="planDataSubmit"></text-view-edit>
        <!--修改费用计划-->
        <el-dialog title="费用计划" :visible.sync="costPlanDataDialog.visiable" append-to-body>
            <cost-plan :cost-plan-data="costPlanData" :mode="costPlanDataDialog.mode" @complete="costPlanDataComplete"></cost-plan>
        </el-dialog>
    </div>
</body>
<script type="module" src="${urls.getForLookupPath('/assets/module/eventplan/eventtracking.js')}"></script>
</html>