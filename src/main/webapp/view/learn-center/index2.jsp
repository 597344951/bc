<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <title>学习中心</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <%--ueditor--%>
    <%@include file="/include/ueditor.jsp"%>
    <link href="${urls.getForLookupPath('/assets/module/template/template.css')}" rel="stylesheet">
    <style>
        .el-popover{
        background: white;
        padding:5px;
    }
    .el-popover__reference{
       /*  text-align: center; */
        font-size: 14px;
    }
    .el-popover__title{
       /*  text-align: center; */
        font-weight: bold;
        font-size: 15px;
    }
    .integral p{
        margin: 3px 0;
        padding: 2px 0;
        border-bottom: 1px dotted #dadada;
    }

    .bottom{
        text-align: center;
        margin-top: 10px;
    }
    .bottom  span{
        display: inline-block;
        width: 150px;
        height:30px;
        text-align: center;
        line-height: 30px;
        background-color: #16abe8;
        color: #fff;
        cursor: pointer;
        border-radius: 4px;
    }
    .bottom span:hover{
        font-weight: bold;
    }
    .can span,.need span span{
        font-size: 14px;
        color: #67c23a;
        font-weight: bold;
    }
    .finish span{
        font-size: 14px;
        color: red;
        font-weight: bold;
    }
    .can,.need,.finish{
        /*text-align: center;*/
    }
    .need-list-item{
        font-size: 15px;
        color: #303133;
        padding: 0 20px;
    }
    .need-list-item>i{
        margin:0 10px;
    }
    .progress-label-txt{
        margin-top:5px;
    }
    .ant-card-body>.bottom {
        height: 37px;
        line-height: 40px;
        text-align: center;
        color: rgba(0, 0, 0, .65);
        border-radius: 0 0 4px 4px;
        border-top: 1px solid #e8e8e8;
        transition: all .3s;
        cursor: pointer;
    }
    </style>
</head>

<body>
    <div class="height_full" id="app" v-cloak>
        <div>
            <div>
                <el-row class="toolbar">
                    <el-col :span="4" class="full-width-if-mobile">
                        <h2>学习中心</h2>
                    </el-col>
                    <el-col :span="16" style="text-align: left;">
                        <el-form :inline="true" class="no-margin-form demo-form-inline">
                            <el-form-item label="搜索素材">
                                <el-input placeholder="搜索素材" v-model="keyword"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="searchTemplate">搜索</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="4">
                        <div>
                            <el-popover placement="top-start" width="400" trigger="hover">
                                <div class="integral">
                                    <h3>今日进度</h3>
                                    <el-row style="margin:10px">
                                        <el-col :span="14">
                                            <el-progress :text-inside="true" :stroke-width="18" :percentage="dayScorePercent"
                                                status="success"></el-progress>
                                        </el-col>
                                        <el-col :span="10" style="text-align:center;">
                                            还可获得: <span>{{dayScoreLeft}}</span>积分
                                        </el-col>
                                    </el-row>
                                    <h3>还需完成</h3>
                                    <div style="margin:10px">
                                        <ul>
                                            <li class="need-list-item"><i class="el-icon-picture"></i> 观看 <span>
                                                    {{handleFloat(dayScoreLeft/learnProcess.videoScore)}}
                                                </span>个视频</li>
                                            <li class="need-list-item"><i class="el-icon-document"></i> 阅读 <span>
                                                    {{handleFloat(dayScoreLeft/learnProcess.passageScore)}}
                                                </span>篇文章</li>
                                        </ul>
                                    </div>
                                    <h3>本季度学习进度</h3>
                                    <el-row style="margin:10px;text-align: center;">
                                        <el-col :span="12">
                                            <el-progress type="circle" :percentage="quarterDayPercent"></el-progress>
                                            <div class="progress-label-txt">剩余 {{learnProcess.quarterDayLeaves}} 天</div>
                                        </el-col>
                                        <el-col :span="12">
                                            <el-progress type="circle" :percentage="quarterScorePercent"></el-progress>
                                            <div class="progress-label-txt">还需 {{learnProcess.quarterTotalScores -
                                                learnProcess.quarterScores}} 积分</div>
                                        </el-col>
                                    </el-row>
                                    <div class="ant-card-body">
                                        <div class="bottom">
                                            <div @click="history.visiable=true">查看积分获取历史</div>
                                        </div>
                                    </div>
                                </div>
                                <div slot="reference">
                                    <el-progress :text-inside="true" :stroke-width="18" :percentage="quarterScorePercent"
                                        status="success"></el-progress>
                                    <span class="label-txt">本季度学习进度</span>
                                </div>
                            </el-popover>
                        </div>

                    </el-col>
                </el-row>
            </div>
            <el-container>
                <el-aside width="200px">
                    <el-menu router class="el-menu-vertical-demo" :default-active="$route.path">
                        <el-menu-item index="/category/all" route="/category/all">
                            <i class="el-icon-setting"></i>
                            <span slot="title">所有</span>
                        </el-menu-item>
                        <template v-for="tp in tpt_data">
                            <el-menu-item :index="'/category/'+tp.albumId" :route="'/category/'+tp.albumId">
                                <i class="el-icon-setting"></i>
                                <span slot="title">{{tp.name}}</span>
                            </el-menu-item>
                        </template>
                    </el-menu>
                </el-aside>
                <el-main>
                    <div role="mainDis" v-show="!tp.visible" style="overflow: auto;">
                        <el-row>
                            <el-col :span="10" class="full-width-if-mobile">
                            </el-col>
                            <el-col :span="14" style="text-align: right;" class="full-width-if-mobile">
                                <!--分页-->
                                <el-pagination style="margin:auto;" class="pagebar" :current-page="tpager.current"
                                    :page-sizes="[10, 20, 30]" :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper"
                                    :total="tpager.total" @size-change="handleSizeChange" @current-change="handleCurrentChange">
                                </el-pagination>
                            </el-col>
                        </el-row>

                        <template v-for="tp in tps">
                            <el-card class="passage-conver image-card" :body-style="{ padding: '0px' }" @click.native="openView(tp)">
                                <span class="verify-status">
                                    <el-tag class="not-verify" v-if="tp.verify == null" type="info" size="mini"></el-tag>
                                    <el-tag class="pass" v-if="tp.verify" type="success" size="mini"></el-tag>
                                    <el-tag class="not-pass" v-if="tp.verify == false" type="danger" size="mini"></el-tag>
                                </span>
                                <div class="background-img" @mouseenter="card_hover(tp)" @mouseleave="card_leave(tp)">
                                    <template v-if="tp.type == 'image' || tp.type == 'text'">
                                        <!--图片-->
                                        <img :src="getResUrl(tp.coverUrl) " class="image">
                                    </template>
                                    <template v-if="tp.type == 'audio'">
                                        <!--音频-->
                                        <span class="audio-icon"></span>
                                    </template>
                                    <template v-if="tp.type == 'video'">
                                        <!--视频-->
                                        <img :src="getResUrl(tp.coverUrl) " class="image">
                                        <span class="video-icon"></span>
                                    </template>
                                    <div class="control ">
                                        <span class="title">{{tp.name}}</span>
                                        <el-collapse-transition name="el-fade-in">
                                            <div v-show="tp.showtoolbar" class="bottom clearfix">
                                                <p class="descript">{{tp.description}}</p>
                                            </div>
                                        </el-collapse-transition>
                                    </div>
                                </div>
                            </el-card>
                        </template>

                    </div>
                </el-main>
            </el-container>
        </div>
        <!--对话框-->

        <!--模板类别 管理对话框-->
        <!--导入资源-->
        <el-dialog title="积分历史纪录" :visible.sync="history.visiable" @open="loadScoreHistory(true)">
            <el-pagination style="margin:auto;" class="pagebar" :current-page="history.pager.current" :page-sizes="[10, 20, 30]"
                :page-size="history.pager.size" layout="total, sizes, prev, pager, next, jumper" :total="history.pager.total"
                @size-change="historySizeChange" @current-change="historyCurrentChange">
            </el-pagination>
            <el-table :data="scoreHistory">
                <el-table-column label="时间">
                    <template slot-scope="scope">
                        <p> {{ scope.row.time | datetime }}</p>
                    </template>
                </el-table-column>
                <el-table-column label="项目" prop="name"></el-table-column>
                <el-table-column label="获得积分" prop="score"></el-table-column>
            </el-table>
        </el-dialog>
        <!--导入资源-->

        <!--Tree 右键菜单-->
        <el-dialog class="resourceView" :title="resourceView.title" :visible.sync="resourceView.visible" @close="resourceViewClose">
            <template v-if="resourceView.type == 'video'">
                <video :src="getResUrl(resourceView.url)" controls="controls" class="videoView"></video>
            </template>
            <template v-if="resourceView.type == 'audio'">
                <audio :src="getResUrl(resourceView.url)" controls="controls" class="audioView">
                    Your browser does not support the audio element.
                </audio>
            </template>
            <template v-if="resourceView.type == 'image'">
                <img :src="getResUrl(resourceView.url) " class="imageView">
            </template>

        </el-dialog>
    </div>
</body>

</html>
<script charset="utf-8" type="module" src="${urls.getForLookupPath('/assets/module/learn-center/index.js')}"></script>
<!-- 美图开放API -->
<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>