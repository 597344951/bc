<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
    <base href="/">
    <meta charset="UTF-8">

    <title>终端管理界面</title>
<%@include file="/include/head.jsp"%>

<link href="${urls.getForLookupPath('/assets/module/terminal/terminal-control.css')}" rel="stylesheet">
</head>
<body>
<div id="app">
    <div id="toolbar">
        <el-row>
            <el-col :span="24">
                <el-menu :default-active="tqueryIf.online=='1'?'1':'2'" class="nav-menu el-menu-demo" mode="horizontal">
                    <el-menu-item index="-1" class="t-title" disabled>终端情况: </el-menu-item>
                    <el-menu-item index="1" @click="changeSearch(1)">
                        <i class="el-icon-check online-icon"></i>正常 ({{onlineCount.online?onlineCount.online:0}})</el-menu-item>
                    <el-menu-item index="2" @click="changeSearch(0)">
                        <i class="el-icon-close offline-icon"></i> 终端不在线 ({{onlineCount.offline?onlineCount.offline:0}})</el-menu-item>
                    <el-menu-item index="-3" class="t-title" disabled>
                        <el-button-group class="control-button">
                            <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                            <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                        </el-button-group>
                    </el-menu-item>
                    <el-menu-item index="-2" :style="{'float':'right'}" class="t-title" disabled>
                        <el-pagination class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]" :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper"
                            :total="tpager.total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
                        </el-pagination>
                    </el-menu-item>
                </el-menu>
            </el-col>
        </el-row>
    </div>
    <div id="terminalDisplay">
        <div v-show="dis_h_v">
            <table class="table terminal-table-dis table-hover">
                <thead>
                    <th>屏幕截图</th>
                    <th>硬件信息</th>
                    <th>软件信息</th>
                    <th>地理位置信息</th>
                    <th>控制</th>
                </thead>
                <tbody>
                    <template v-for="t in terminals">
                        <tr :class="t.online=='1' ? 'online':'termialDis-offline'">
                            <td>
                                <div :class="t.rev == '横屏' ? 'screenshoot-h':'screenshoot-v'">
                                    <img :src="t.coverImage" class="image">
                                </div>
                            </td>
                            <td>
                                <table class="terminal-disInfo-big">
                                    <tr>
                                        <td>设备名 </td>
                                        <td>：</td>
                                        <td>{{t.name}}</td>
                                    </tr>
                                    <tr>
                                        <td>屏幕尺寸 </td>
                                        <td>：</td>
                                        <td>{{t.size}}</td>
                                    </tr>
                                    <tr>
                                        <td>分辨率 </td>
                                        <td>：</td>
                                        <td>{{t.ratio}}</td>
                                    </tr>
                                    <tr>
                                        <td>屏幕类型</td>
                                        <td>：</td>
                                        <td>{{t.rev}}</td>
                                    </tr>
                                    <tr>
                                        <td>触摸屏 </td>
                                        <td>：</td>
                                        <td>{{t.typ}}</td>
                                    </tr>
                                    <tr>
                                        <td>是否在线 </td>
                                        <td>：</td>
                                        <td>
                                            <el-tag size="small" :type="t.online == '1'?'success':'danger'">{{t.online == '1' ? '是':'否'}}</el-tag>
                                        </td>
                                    </tr>

                                </table>
                            </td>
                            <td>
                                <table class="terminal-disInfo-big">
                                    <tr>
                                        <td>当前节目</td>
                                        <td>：</td>
                                        <td>暂无数据</td>
                                    </tr>
                                    <tr>
                                        <td>操作系统</td>
                                        <td>：</td>
                                        <td>{{t.sys}}</td>
                                    </tr>
                                    <tr>
                                        <td>IP</td>
                                        <td>：</td>
                                        <td>{{t.ip}}</td>
                                    </tr>
                                    <tr>
                                        <td>MAC</td>
                                        <td>：</td>
                                        <td>{{t.mac}}</td>
                                    </tr>
                                    <tr>
                                        <td>版本</td>
                                        <td>：</td>
                                        <td>{{t.ver}}</td>
                                    </tr>
                                    <tr>
                                        <td>心跳检测时间</td>
                                        <td>：</td>
                                        <td>{{getDateTimeInfo(t.lastTime)}}</td>
                                    </tr>
                                    <tr>
                                        <td>最后同步时间</td>
                                        <td>：</td>
                                        <td>{{getDateTimeInfo(t.lastSynTime)}}</td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <table class="terminal-disInfo-big">
                                    <tr>
                                        <td>位置</td>
                                        <td>：</td>
                                        <td>{{t.addr}}</td>
                                    </tr>
                                    <tr>
                                        <td>Gis</td>
                                        <td>：</td>
                                        <td>{{t.gis}}</td>
                                    </tr>
                                    <tr>
                                        <td>注册时间</td>
                                        <td>：</td>
                                        <td>
                                            {{t.resTime}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>保修状态</td>
                                        <td>：</td>
                                        <td>
                                            <el-tag type="success" size="small">{{t.warranty}} 未过保</el-tag>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>区域</td>
                                        <td>：</td>
                                        <td>{{t.loc}}</td>
                                    </tr>
                                    <tr>
                                        <td>联系电话</td>
                                        <td>：</td>
                                        <td>{{t.tel}}</td>
                                    </tr>
                                    <tr>
                                        <td>所属组织</td>
                                        <td>：</td>
                                        <td>{{t.orgInfo?t.orgInfo.orgInfoName:''}}</td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <div class="bottom-h">
                                    <el-button type="button" size="mini" class="button" @click="openTerminalController(t)">控制</el-button>
                                    <br/>
                                    <el-button type="button" size="mini" class="button" @click="openTerminalProgram(t)">节目</el-button>
                                    <br/>
                                    <el-button type="button" size="mini" class="button" @click="openTerminalLog(t)">日志</el-button>
                                    <br/>
                                </div>
                            </td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </div>
        <div v-show="!dis_h_v">
            <template v-for="t in terminals">
                <el-card class="termialDis" :class="t.online=='1' ? 'online':'termialDis-offline'" :body-style="{ padding: '0px' }" shadow="never">
                    <div class="deviceInfo">
                        <span class="name"><i class="el-icon-star-on"></i> {{t.name}}</span>
                        <span class="size">{{t.size}}</span>
                    </div>
                    <div :class="t.rev == '横屏' ? 'screenshoot-h':'screenshoot-v'">
                        <img :src="t.coverImage" class="image">
                        <div class="ratio">{{t.ratio}}</div>
                    </div>
                    <div class="control" style="">
                        <table class="disInfo">
                            <tr>
                                <td><i class="el-icon-document"></i> 当前节目</td>
                                <td></td>
                                <td>暂无数据</td>
                            </tr>
                            <tr>
                                <td><i class="el-icon-mobile-phone"></i> 屏幕类型</td>
                                <td></td>
                                <td>{{t.rev}}</td>
                            </tr>
                            <tr>
                                <td><i class="el-icon-location-outline"></i> 所在位置</td>
                                <td></td>
                                <td>
                                    <el-tooltip class="item" effect="dark" :content="t.addr" placement="top-start">
                                        <p class="disInfo-overflow">{{t.addr}}</p>
                                    </el-tooltip>
                                </td>
                            </tr>
                            <tr>
                                <td><i class="el-icon-location-outline"></i> 所属组织</td>
                                <td></td>
                                <td>
                                    <p class="disInfo-overflow">{{t.orgInfo?t.orgInfo.orgInfoName:''}}</p>
                                </td>
                            </tr>
                        </table>
                        <div class="bottom clearfix">
                            <el-button type="button" size="mini" class="button" @click="openTerminalController(t)">控制</el-button>
                            <el-button type="button" size="mini" class="button" @click="openTerminalProgram(t)">节目</el-button>
                            <el-button type="button" size="mini" class="button" @click="openTerminalLog(t)">日志</el-button>
                        </div>
                    </div>
                </el-card>
            </template>
        </div>
    </div>
    <!--dialog-->
    <el-dialog :title="tcw.title" :visible.sync="tcw.visiable" :fullscreen="tcw.fullscreen" :width="tcw.fullscreen?'100%':'70%'">
        <div slot="title">
            <span class="el-dialog__title"> {{tcw.title}}</span>
            <el-button-group style="margin-left:30px;">
                <el-button size="small" icon="el-icon-rank" @click="tcw.fullscreen=!tcw.fullscreen" title="最大"></el-button>
            </el-button-group>
        </div>
        <div class="command-list">
            <template v-for="control in controlList">
                <a class="action" :class="control.checked ? 'active':''" id="start" @click="SendCommand(control)">
                    <i :class="control.icon" aria-hidden="true"></i>
                    <div class="start">{{control.label}}</div>
                </a>
            </template>
        </div>
    </el-dialog>
    <el-dialog :title="tlw.title" :visible.sync="tlw.visiable" :fullscreen="tlw.fullscreen" :width="tlw.fullscreen?'100%':'70%'">
        <div slot="title">
            <span class="el-dialog__title"> {{tlw.title}}</span>
            <el-button-group style="margin-left:30px;">
                <el-button size="small" icon="el-icon-rank" @click="tlw.fullscreen=!tlw.fullscreen" title="最大"></el-button>
            </el-button-group>
        </div>
        <el-menu :default-active="selectLogType" class="el-menu-demo" mode="horizontal">
            <el-menu-item index="1" @click="selectLogType='1'"> 终端执行日志 </el-menu-item>
            <el-menu-item index="2" @click="selectLogType='2'"> 终端操作日志 </el-menu-item>
            <el-menu-item index="-2" :style="{'float':'right'}" class="t-title" disabled>
                <el-pagination v-show="selectLogType=='1'" class="pagebar" :current-page="extPager.current" :page-sizes="[10, 20, 30]" :page-size="extPager.size"
                    layout="total, sizes, prev, pager, next, jumper" :total="extPager.total" @size-change="logHandleSizeChange"
                    @current-change="logHandleCurrentChange">
                </el-pagination>
                <el-pagination v-show="selectLogType=='2'" class="pagebar" :current-page="optPager.current" :page-sizes="[10, 20, 30]" :page-size="optPager.size"
                    layout="total, sizes, prev, pager, next, jumper" :total="optPager.total" @size-change="logHandleSizeChange"
                    @current-change="logHandleCurrentChange">
                </el-pagination>
            </el-menu-item>
        </el-menu>
        <el-container>
            <el-main>
                <div v-show="selectLogType=='1'">
                    <!--执行-->
                    <template>
                        <el-table :data="extLogs" v-loading.lock="extPager.loading">
                            <div slot="empty">
                                <i class="el-icon-warning"> {{extPager.msg}} </i>
                            </div>
                            <el-table-column label="日志ID" prop="pkId" width="100">
                            </el-table-column>
                            <el-table-column label="指令名称" prop="name" width="180">
                            </el-table-column>
                            <el-table-column label="发送时间" width="180">
                                <template slot-scope="scope">
                                    <div slot="reference" class="name-wrapper">
                                        {{ scope.row.sendtime | datetime }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="反馈时间" width="180">
                                <template slot-scope="scope">
                                    <div slot="reference" class="name-wrapper">
                                        {{ scope.row.acesstime | datetime }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="执行状态" prop="status" width="150">
                            </el-table-column>
                            <el-table-column label="接收信息" prop="remark">
                            </el-table-column>
                        </el-table>
                    </template>
                </div>
                <div v-show="selectLogType=='2'">
                    <!--操作日志-->
                    <template>
                        <el-table :data="optLogs" style="width: 100%" v-loading.lock="optPager.loading">
                            <div slot="empty">
                                <i class="el-icon-warning"> {{optPager.msg}} </i>
                            </div>
                            <el-table-column label="日志ID" prop="pkId" width="100">
                            </el-table-column>
                            <el-table-column label="操作用户" prop="userName" width="150">
                            </el-table-column>
                            <el-table-column label="操作机器IP" prop="ip" width="180">
                            </el-table-column>
                            <el-table-column label="操作时间" width="180">
                                <template slot-scope="scope">
                                    <div slot="reference" class="name-wrapper">
                                        {{ scope.row.createTime | datetime }}
                                    </div>
                                </template>
                            </el-table-column>

                            <el-table-column label="操作信息描述" prop="remark">
                            </el-table-column>
                        </el-table>
                    </template>
                </div>
            </el-main>
        </el-container>
    </el-dialog>
    <el-dialog :title="pgw.title" :visible.sync="pgw.visiable" :fullscreen="pgw.fullscreen" :width="pgw.fullscreen?'100%':'70%'" v-loading.lock="programLoading">
        <div slot="title">
            <span class="el-dialog__title"> {{pgw.title}}</span>
            <el-button-group style="margin-left:30px;">
                <el-button size="small" icon="el-icon-menu" @click="prg_dis_h_v=false" :type="!prg_dis_h_v?'primary':''"></el-button>
                <el-button size="small" icon="el-icon-tickets" @click="prg_dis_h_v=true" :type="prg_dis_h_v?'primary':''"></el-button>
                <el-button size="small" icon="el-icon-rank" @click="pgw.fullscreen=!pgw.fullscreen" title="最大"></el-button>
            </el-button-group>
            <el-button-group style="margin-left:30px;">
                <el-button size="small"  @click="filterStatus(1)" :type="disStatus=='1'?'primary':''">正在播放({{playingCount}})</el-button>
                <el-button size="small"  @click="filterStatus(2)" :type="disStatus=='2'?'primary':''" >暂未播放({{notPlayCount}})</el-button>
                <el-button size="small"  @click="filterStatus(0)" :type="disStatus=='0'?'primary':''">过期节目({{expiredCount}})</el-button>
            </el-button-group>
        </div>

        <div v-if="prg_dis_h_v">
            <table class="table table-board terminal-table-dis" >
                <thead>
                    <th>节目封面</th>
                    <th>节目信息</th>
                    <th>播放模式</th>
                    <th>控制</th>
                </thead>
                <tbody>
                    <template v-for="t in tprograms">
                        <tr class="termialDis-table">
                            <td>
                                <div class="screenshoot">
                                    <img :src="t.coverImageUrl" class="image">
                                    <div class="ratio">{{t.ratio}}</div>
                                </div>
                            </td>
                            <td>
                                <table class="terminal-disInfo-big">
                                    <tr>
                                        <td>节目名称 </td>
                                        <td>：</td>
                                        <td>{{t.name}}</td>
                                    </tr>
                                    <tr>
                                        <td>发布类型 </td>
                                        <td>：</td>
                                        <td>
                                            <span v-html="t.publishTypeStr"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>发布人 </td>
                                        <td>：</td>
                                        <td>{{t.userName}}</td>
                                    </tr>
                                    <tr>
                                        <td>发布时间</td>
                                        <td>：</td>
                                        <td>{{t.createTime}}</td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <table class="terminal-disInfo-big">
                                    <tr>
                                        <td>播放时间 </td>
                                        <td>：</td>
                                        <td>
                                            <span v-html="t.playTime"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>有效日期 </td>
                                        <td>：</td>
                                        <td>{{t.activeTime}}</td>
                                    </tr>
                                    <tr>
                                        <td>节目状态 </td>
                                        <td>：</td>
                                        <td>{{getStatusLabel(t)}}</td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <div class="bottom-h">
                                    <el-button type="button" size="mini" class="button" @click="openProgramView(t)">预览</el-button>
                                    <br/>
                                    <el-button type="danger" size="mini" class="button" @click="cancelTerminalProgram(t)">移除</el-button>
                                        <br/>
                                </div>
                            </td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </div>
        <div v-if="!prg_dis_h_v">
            <template v-for="t in tprograms">
                <el-card class="termialDis" :body-style="{ padding: '0px' }" shadow="never">
                    <div class="deviceInfo">
                        <span class="name"> <i class="el-icon-star-on"></i> {{t.name}}</span>
                    </div>
                    <div class="screenshoot">
                        <img :src="t.coverImageUrl" class="image">
                        <div class="ratio">{{t.ratio}}</div>
                    </div>
                    <div class="control">
                        <table class="disInfo">
                            <tr>
                                <td><i class="el-icon-tickets"></i> 发布类型 </td>
                                <td></td>
                                <td>
                                    <span v-html="t.publishTypeStr"></span>
                                </td>
                            </tr>
                            <tr>
                                <td><i class="el-icon-news"></i> 发布人 </td>
                                <td></td>
                                <td>{{t.userName}}</td>
                            </tr>
                            <tr>
                                <td><i class="el-icon-time"></i> 发布时间</td>
                                <td></td>
                                <td>{{t.createTime}}</td>
                            </tr>
                        </table>
                        <div class="bottom clearfix">
                            <el-button type="button" size="mini" class="button" @click="openProgramView(t)">预览</el-button>
                            <el-button type="danger" size="mini" class="button" @click="cancelTerminalProgram(t)">移除</el-button>
                        </div>
                    </div>
                </el-card>
            </template>
        </div>
    </el-dialog>
    <!--dialog-->
</div>
</body>

</html>
<script type="text/javascript" charset="utf-8" src="${urls.getForLookupPath('/assets/module/terminal/terminal_manage.js')}"></script>