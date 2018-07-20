<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>待编辑/审核发布内容</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <style>
        .el-table thead,.el-table__row {
            font-size: 14px;
        }
        .period-input {
            width: 300px;
        }
       
    </style>
</head>
<body>
    <div id="app">
        <el-container>
            <el-header>
                <div id="toolbar">
                    <el-row>
                        <el-col :span="24">
                            <el-menu class="el-menu-demo" mode="horizontal">
                                <el-menu-item index="-1" class="normal-menu-item" disabled>正在处理中内容活动 </el-menu-item>
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
            </el-header>
            <el-main>
                <div v-show="!dis_h_v">
                    <template v-for="pc in processContent">
                        <el-card class="card-item card-item1" :body-style="{ padding: '0px'}" shadow="never">
                            <div class="title">
                                <span class="bolder"> <i class="el-icon-star-on" style="font-size: 20px;"></i> {{pc.title}} </span>
                                <span class="right">
                                    <el-popover title="发布流程" placement="right"  trigger="hover" @show="getProcessState(pc,false)">
                                        <template>
                                            <el-steps :space="200" :active="processState.active" finish-status="success">
                                                <el-step v-for="step in processState.steps" :title="step.title"></el-step>
                                            </el-steps>
                                        </template>
                                        <el-tag size="small" slot="reference">{{pc.state}}</el-tag>
                                    </el-popover>

                                </span>
                            </div>
                            <div class="content">
                                <table class="dis-info-min">
                                    <tbody>
                                        <tr>
                                            <td> <i class="el-icon-news"></i>发起人</td>
                                            <td></td>
                                            <td>{{pc.sponsor}}</td>
                                        </tr>
                                        <tr>
                                            <td> <i class="el-icon-time"></i>时间</td>
                                            <td></td>
                                            <td>{{pc.date}}</td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div>
                            <div class="bottom clearfix">
                                <table class="dis-info-min">
                                    <tbody>
                                        <tr>
                                            <td><i class="el-icon-message"></i>查看</td>
                                            <td></td>
                                            <td>
                                                <div>
                                                    <el-button class="no-margin-padding" type="text" size="small" v-if="pc.operate.process" @click="getProcessState(pc)">进度</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" v-if="pc.operate.snapshot" @click="view(pc)">预览</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" v-if="pc.operate.verify_state" @click="getVerifyState(pc)">审核状态</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="viewTerminal(pc)">发布终端</el-button>
                                                    
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><i class="el-icon-setting"></i>操作</td>
                                            <td></td>
                                            <td>
                                                <div>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="startMoreEdit(pc)" v-if="pc.operate.more_edit_start">开始编辑</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="moreEdit(pc)" v-if="pc.operate.more_edit">编辑</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="preCommitMoreEdit(pc)" v-if="pc.operate.more_edit_commit">编辑提交</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="preVerify(pc, 1)" v-if="pc.operate.verify">审核通过</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="preVerify(pc, 2)" v-if="pc.operate.verify">审核不通过</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="preVerify(pc, 3)" v-if="pc.operate.verify">审核放弃</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="openPublishPeriod(pc)" v-if="pc.operate.publish">发布</el-button>
                                                    <el-button class="no-margin-padding" type="text" size="small" @click="discard(pc)" v-if="pc.operate.delete">移除</el-button>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </el-card>
                    </template>
                </div>
                <div v-show="dis_h_v">
                <template>
                    <el-table :data="processContent" border style="width: 100%">
                        <el-table-column prop="title" label="主题"></el-table-column>
                        <el-table-column prop="sponsor" label="发起人" ></el-table-column>
                        <el-table-column prop="date" label="时间" width="180"></el-table-column>
                        <%-- <el-table-column prop="startDate" label="预定开始时间" width="120"></el-table-column>
                        <el-table-column prop="endDate" label="预定结束时间" width="120"></el-table-column>
                        <el-table-column prop="period" label="预定播放时段" width="120"></el-table-column> --%>
                            <el-table-column prop="state" label="当前状态" width="100">
                                <template slot-scope="scope">
                                    <el-tag>{{scope.row.state}}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column label="查看" width="200">
                                <template slot-scope="scope">
                                    <el-button type="text" size="small" v-if="scope.row.operate.process" @click="getProcessState(scope.row)">进度</el-button>
                                    <%-- <el-button type="text" size="small" v-if="scope.row.operate.template" @click="viewTemplate(scope.row)">模板</el-button> --%>
                                        <el-button type="text" size="small" v-if="scope.row.operate.snapshot" @click="view(scope.row)">预览</el-button>
                                        <el-button type="text" size="small" v-if="scope.row.operate.verify_state" @click="getVerifyState(scope.row)">审核状态</el-button>
                                        <el-button type="text" size="small" @click="viewTerminal(scope.row)">发布终端</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column prop="operate" label="操作" width="300">
                                <template slot-scope="scope">
                                    <el-button type="text" size="small" @click="startMoreEdit(scope.row)" v-if="scope.row.operate.more_edit_start">开始编辑</el-button>
                                    <el-button type="text" size="small" @click="moreEdit(scope.row)" v-if="scope.row.operate.more_edit">编辑</el-button>
                                    <el-button type="text" size="small" @click="preCommitMoreEdit(scope.row)" v-if="scope.row.operate.more_edit_commit">编辑提交</el-button>
                                    <el-button type="text" size="small" @click="preVerify(scope.row, 1)" v-if="scope.row.operate.verify">审核通过</el-button>
                                    <el-button type="text" size="small" @click="preVerify(scope.row, 2)" v-if="scope.row.operate.verify">审核不通过</el-button>
                                    <el-button type="text" size="small" @click="preVerify(scope.row, 3)" v-if="scope.row.operate.verify">审核放弃</el-button>
                                    <el-button type="text" size="small" @click="openPublishPeriod(scope.row)" v-if="scope.row.operate.publish">发布</el-button>
                                    <el-button type="text" size="small" @click="discard(scope.row)" v-if="scope.row.operate.delete">移除</el-button>
                                </template>
                            </el-table-column>
                    </el-table>
                </template>
            </div>
            </el-main>
        </el-container>
        <!--编辑提交确定-->
            <el-dialog title="提交确认" :visible.sync="isMoreEditCommit" width="30%">
                <%--<el-input v-model="snapshot" placeholder="预览地址"></el-input>--%>
                    <p style="color: red; padding-left: 5px;">* 请确认是否已经完成内容编辑制作, 确认无误请点击确认提交.</p>
                    <span slot="footer" class="dialog-footer">
                        <el-button size="mini" @click="isMoreEditCommit = false">取 消</el-button>
                        <el-button size="mini" type="primary" @click="commitMoreEdit">确 定</el-button>
                    </span>
            </el-dialog>

            <el-dialog title="填写理由/修改建议" :visible.sync="isVerify" width="30%">
                <el-input v-model="opinion" placeholder="不通过理由/修改意见" type="textarea" :rows="4"></el-input>
                <span slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="isVerify = false">取 消</el-button>
                    <el-button size="mini" type="primary" @click="verify">确 定</el-button>
                </span>
            </el-dialog>

            <el-dialog title="详细进度" :visible.sync="processState.visible" width="60%">
                <el-steps :space="200" :active="processState.active" finish-status="success">
                    <el-step v-for="step in processState.steps" :title="step.title"></el-step>
                </el-steps>
                <el-card shadow="never">
                    <p style="font-size: 12px;" v-for="d in processState.detail">{{d}}</p>
                </el-card>
                <span slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="processState.visible = false">取 消</el-button>
                    <el-button size="mini" type="primary" @click="processState.visible = false">确 定</el-button>
                </span>
            </el-dialog>

            <el-dialog title="审核进度" :visible.sync="verifyState.visible" width="60%">
                <el-card shadow="never">
                    <p style="font-size: 13px;" v-for="l in verifyState.list">{{l.username}} : {{l.state}}</p>
                </el-card>
                <span slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="verifyState.visible = false">取 消</el-button>
                    <el-button size="mini" type="primary" @click="verifyState.visible = false">确 定</el-button>
                </span>
            </el-dialog>

            <el-dialog title="终端列表" :visible.sync="publishTerminal.show" width="80%">
                <el-table :data="publishTerminal.list" border>
                    <el-table-column prop="name" label="名称"></el-table-column>
                    <el-table-column prop="rev" label="横屏/竖屏"></el-table-column>
                    <el-table-column prop="typ" label="触摸类型"></el-table-column>
                    <el-table-column prop="size" label="尺寸"></el-table-column>
                    <el-table-column prop="ratio" label="分辨率"></el-table-column>
                    <el-table-column prop="ip" label="IP"></el-table-column>
                    <el-table-column prop="loc" label="位置"></el-table-column>
                </el-table>
            </el-dialog>

            <el-dialog title="选择播放时间" :visible="publishPeriod.show" :show-close="false" width="500px">
                <el-form :model="publishPeriod.period" :rules="publishPeriod.rules" ref="publishPeriod" label-width="150px">
                    <el-form-item label="开始日期" prop="startDate">
                        <el-date-picker v-model="publishPeriod.period.startDate" type="date" placeholder="开始日期" value-format="yyyy-MM-dd 00:00:00"
                            style="width: 300px"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束日期" prop="endDate">
                        <el-date-picker v-model="publishPeriod.period.endDate" type="date" placeholder="开始日期" value-format="yyyy-MM-dd 00:00:00"
                            style="width: 300px"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="工作/非工作日" prop="week">
                        <el-select class="period-input" v-model="publishPeriod.period.week" multiple collapse-tags placeholder="工作日/非工作日">
                            <el-option key="0" label="星期日" value="0"></el-option>
                            <el-option key="1" label="星期一" value="1"></el-option>
                            <el-option key="2" label="星期二" value="2"></el-option>
                            <el-option key="3" label="星期三" value="3"></el-option>
                            <el-option key="4" label="星期四" value="4"></el-option>
                            <el-option key="5" label="星期五" value="5"></el-option>
                            <el-option key="6" label="星期六" value="6"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="每日时段" prop="time">
                        <el-time-picker style="width: 300px" is-range v-model="publishPeriod.period.time" range-separator="-" value-format="H:mm"
                            start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围">
                        </el-time-picker>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="publishPeriod.show = false">取 消</el-button>
                    <el-button size="mini" @click="resetForm('publishPeriod')">重 置</el-button>
                    <el-button size="mini" type="primary" @click="publish('publishPeriod')">发 布</el-button>
                </span>
            </el-dialog>


            <el-dialog title="素材验证" :visible="commitMessage.show" :show-close="false" width="300px">
                <p style="font-size: 13px; margin: 5px 20px; color: blue" v-for="m in commitMessage.message">{{m}}</p>
            </el-dialog>
    </div>
</body>
</html>
<script src="${urls.getForLookupPath('/assets/module/publish/process.js')}"></script>
