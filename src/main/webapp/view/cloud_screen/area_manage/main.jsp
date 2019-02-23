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
        <title>区域管理</title>
        <%@include file="/include/head_notbootstrap.jsp"%>
        <script type="text/javascript" src="/json/address-pca.json"></script>
        <style type="text/css">
            .el-select.el-select--small {
                width: 220px;
            }
            .el-input-number.el-input-number--small {
                width: 220px;
            }
            .el-input.el-input--small {
                width: 220px;
            }
            .el-textarea.el-input--small {
                width: 220px;
            }
            .el-slider {
                width: 220px;
            }
            body {
                margin: 0px;
                padding: 0px;
            }
            #main_box {
                margin: 0px;
                padding: 10px;
            }
            #left_main {
                width: 29%;
                height: 100%;
                float: left;
            }
            #area_manage {
                height: 100%;
            }
            #right_main {
                width: 70%;
                height: 100%;
                float: right;
            }
            #conditions_map{
                width: 100%;
                height: 7%;
            }
            #main_map {
                width: 100%;
                height: 92%;
                border: 1px solid #dcdfe6;
                border-radius:5px
            }
            .el-textarea.is-disabled .el-textarea__inner{
                color: #636363;
            }
            #mapAreaInfoCard {
                border: 1px solid #dcdfe6;
                transition: transform 0.2s,box-shadow 0.3s;
                border-radius:5px;
                margin: 0 auto;
                margin-bottom: 10px;
                height: 100px;
                width: 90%;
                padding: 10px;
                font-size: 14px;
            }
            #mapAreaInfoCard:hover {
                box-shadow: 0 8px 15px rgba(0,0,0,0.15);
                transform:translateY(-2px);
                cursor: pointer;
            }
            a {
                text-decoration: none;
            }
            a:hover {
                color: red;
            }
            .unrelatedTerminal-card {
                position: absolute;
                z-index: 998;
                background-color: rgba(232, 230, 226, 0.9);
                margin: 5px;
                padding: 10px;
                border-radius:5px;
                width: 270px;
                height: 300px;
                font-size: 12px;
            }
            .area_terminal_card .title {
                font-size: 12px;
                font-weight: normal;
                width: 90px; 
                float: left; 
                text-align: right;
            }
            .area_terminal_card .context {
                font-size: 12px;
                font-weight: normal;
                width: 180px; 
                float: left; 
            }
            .areaInfo-card {
                position: absolute;
                z-index: 998;
                margin: 5px;
                border-radius:5px;
                font-size: 12px;
                width:390px;
                height:20%;
                overflow: hidden;
                transition: height 0.3s;
                background: #fff;
                box-shadow:0px 0px 10px 2px #8c8c8c;
            }
            .areaInfo-card .title {
                font-size: 12px;
                font-weight: normal;
                width: 90px; 
                float: left; 
                text-align: right;
            }
            .areaInfo-card .context {
                font-size: 12px;
                font-weight: normal;
                width: 180px; 
                float: left; 
            }
            .unrelatedTerminal-card .title {
                font-size: 12px;
                font-weight: normal;
                width: 90px; 
                float: left; 
                text-align: right;
            }
            .unrelatedTerminal-card .context {
                font-size: 12px;
                font-weight: normal;
                width: 180px; 
                float: left; 
            }
            .areaInfo-card:hover{
                height: 90%;
            }
            .area_terminal_diglog .el-dialog__body {
                padding-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <div id="app">
            <div :style="'height: ' + (commons.screen.height - 40) + 'px'" id="main_box">
                <div id="left_main">
                    <div id="area_manage">
                        <el-tabs type="card" size="small" v-model="commons.tabs.switchName" @tab-click="tabSwitchInit">
                            <el-tab-pane name="first">
                                <span slot="label"><i class="el-icon-location"></i> 添加区域</span>
                                <div>
                                    <el-form 
                                        size="small" 
                                        :model="insert_area.insertAreaInfoForm" 
                                        status-icon 
                                        :rules="insert_area.insertAreaInfoFormRules" 
                                        ref="insertAreaInfoForm" 
                                        label-width="100px">
                                        <el-form-item label="区域类型" prop="area_type">
                                            <el-select 
                                                v-model="insert_area.insertAreaInfoForm.area_type" 
                                                clearable 
                                                size="small"
                                                placeholder="请选择区域类型">
                                                <el-option
                                                    v-for="item in insert_area.area_types"
                                                    :key="item.id"
                                                    :label="item.name"
                                                    :value="item.id">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item label="区域形状" prop="shape">
                                            <el-radio-group 
                                                @change="initMouseTool"
                                                v-model="insert_area.insertAreaInfoForm.shape" size="small">
                                                <el-radio label="polygon" border>多边形</el-radio>
                                                <el-radio label="circle" border>圆形</el-radio>
                                                <el-radio label="rectangle" border>矩形</el-radio>
                                            </el-radio-group>
                                        </el-form-item>
                                        <el-form-item v-if="insert_area.open_polygon_hole" label="“扣洞”" prop="">
                                            <el-radio-group 
                                                @change="initMouseTool_Hole"
                                                v-model="insert_area.polygon_hole_shape" size="small">
                                                <el-radio label="polygon" border>多边形</el-radio>
                                            </el-radio-group>
                                        </el-form-item>
                                        <el-form-item label="区域填充色" prop="fillColor">
                                            <el-color-picker :disabled="insert_area.insertAreaInfoForm.prohibit_change_color" 
                                                @change="initMouseTool" 
                                                v-model="insert_area.insertAreaInfoForm.fillColor">
                                            </el-color-picker>
                                        </el-form-item>
                                        <el-form-item label="区域边框色" prop="lineColor">
                                            <el-color-picker :disabled="insert_area.insertAreaInfoForm.prohibit_change_color" 
                                                @change="initMouseTool" 
                                                v-model="insert_area.insertAreaInfoForm.lineColor">
                                            </el-color-picker>
                                        </el-form-item>
                                        <el-form-item label="勾选区域GIS信息" prop="giss">
                                            <el-input
                                                type="textarea"
                                                :disabled="true"
                                                :autosize="{ minRows: 3, maxRows: 6}"
                                                placeholder="勾选区域的GIS信息"
                                                v-model="insert_area.insertAreaInfoForm.showGiss">
                                            </el-input>
                                        </el-form-item>
                                        <el-form-item label="区域名" prop="name">
                                            <el-input 
                                                clearable v-model="insert_area.insertAreaInfoForm.name" 
                                                placeholder="为这些区域取名">
                                            </el-input>
                                        </el-form-item>
                                        <el-form-item label="这些区域的简要说明" prop="describes">
                                            <el-input
                                                type="textarea"
                                                :autosize="{ minRows: 3, maxRows: 6}"
                                                placeholder="这些区域的简要说明"
                                                v-model="insert_area.insertAreaInfoForm.describes">
                                            </el-input>
                                        </el-form-item>
                                        <el-form-item label="区域等级" prop="areaLevel">
                                            <el-select
                                                clearable v-model="insert_area.insertAreaInfoForm.areaLevel"
                                                size="small"
                                                placeholder="区域等级">
                                                <el-option label="A类" value="1"></el-option>
                                                <el-option label="B类" value="2"></el-option>
                                                <el-option label="C类" value="3"></el-option>
                                                <el-option label="D类" value="4"></el-option>
                                                <el-option label="E类" value="5"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-form>
                                    <div style="margin-left: 18px;">
                                        <el-button 
                                            size="small" type="primary" 
                                            @click="insertMapAreaInfo">添加区域
                                        </el-button>
                                    </div>
                                </div>
                            </el-tab-pane>
                            <el-tab-pane name="second">
                                <span slot="label"><i class="el-icon-setting"></i> 区域管理</span>
                                <div>
                                    <div>
                                        <el-table 
                                            size="small" 
                                            :data="query_area.basic_pages.list">
                                            <el-table-column label="区域名" prop="name"></el-table-column>
                                            <el-table-column label="创建时间" prop="createTime" width="100"></el-table-column>
                                            <el-table-column label="操作" width="100">
                                                <template slot-scope="scope">
                                                    <el-button 
                                                        @click="openAreaBasicManageDialog(scope.row)" 
                                                        type="text" size="small">修改
                                                    </el-button>
                                                    <el-button 
                                                        @click="queryMapAreaInfosPages(scope.row)" 
                                                        type="text" size="small">查看
                                                    </el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                    </div>
                                    <div style="text-align: center;">
                                        <el-pagination
                                            layout="total, prev, pager, next, jumper" 
                                            @current-change="queryMapAreaBasicsPages"
                                            :current-page.sync="query_area.basic_pages.pageNum"
                                            :page-size.sync="query_area.basic_pages.pageSize"
                                            :total="query_area.basic_pages.total">
                                        </el-pagination>
                                    </div>
                                </div>
                            </el-tab-pane>
                            <el-tab-pane name="third">
                                <span slot="label"><i class="el-icon-document"></i>未关联终端</span>
                                <div>
                                    <el-table 
                                        size="small" 
                                        :data="unrelatedTerminal.pages.list">
                                        <el-table-column label="终端名" prop="name"></el-table-column>
                                        <el-table-column label="地址" prop="addr"></el-table-column>
                                        <el-table-column label="操作" width="50">
                                            <template slot-scope="scope">
                                                <el-button 
                                                    @click="showUnrelatedTerminalInfoAndMarker(scope.row)" 
                                                    type="text" size="small">查看
                                                </el-button>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-tab-pane>
                        </el-tabs>
                    </div>
                </div>
                <div id="right_main">
                    <div id="conditions_map">
                        <!-- 作为 value 唯一标识的键名，绑定值为对象类型时必填 -->
                        <el-select
                            size="small"
                            v-model="map.address_search"
                            filterable
                            value-key="id"
                            remote
                            clearable
                            placeholder="请输入地址以定位"
                            @change="search_main_map"
                            :remote-method="search_address_optional">
                            <el-option
                                v-for="item in map.address_search_optional"
                                :label="item.district + item.name"
                                :value="item">
                                <span>{{item.name}}</span>
                                <span style="color: #8492a6;">{{item.district}}</span>
                            </el-option>
                        </el-select>
                    </div>
                    <div id="main_map">
                        <div v-if="commons.showAreaInfoCard" class="areaInfo-card">
                            <el-table 
                                size="small" 
                                :data="query_area.info_pages.list">
                                <el-table-column label="区域形状" width="70">
                                    <template slot-scope="scope">
                                        {{scope.row.shape == 'circle' ? '圆形' : scope.row.shape == 'polygon' ? '多边形' : '矩形'}}
                                    </template>
                                </el-table-column>
                                <el-table-column label="区域着色" width="100">
                                    <template slot-scope="scope">
                                        <span :style="'color:' + scope.row.fillColor">▮</span>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <span :style="'color:' + scope.row.lineColor">▯</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="区域享受折扣" width="100">
                                    <template slot-scope="scope">
                                        {{scope.row.discount}}%
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作">
                                    <template slot-scope="scope">
                                        <el-button 
                                            @click="openAreaInfoManageDialog(scope.row)" 
                                            type="text" size="small">修改
                                        </el-button>
                                        <el-button 
                                            @click="queryMapAreaLatLngs(scope.row)" 
                                            type="text" size="small">查看区域
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <div style="text-align: center; margin-top: 10px;">
                                <el-pagination
                                    layout="total, prev, pager, next, jumper" 
                                    @current-change="queryMapAreaInfosPages(query_area.now_basic_area)"
                                    :current-page.sync="query_area.info_pages.pageNum"
                                    :page-size.sync="query_area.info_pages.pageSize"
                                    :total="query_area.info_pages.total">
                                </el-pagination>
                            </div>
                        </div>
                        <div v-if="commons.showUnrelatedTerminalCard" class="unrelatedTerminal-card">
                            <div>
                                <div class="title">终端名：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.name}}</div>
                            </div>
                            <div>
                                <div class="title">在线状态：</div>
                                <div class="context">
                                    <span :style="unrelatedTerminal.show_terminal.online == 1 ? 'color: green; font-weight: bold;' : 'color: red; font-weight: bold;'">
                                        &nbsp;{{unrelatedTerminal.show_terminal.online == 1 ? '在线' : '离线'}}
                                    </span>
                                </div>
                            </div>
                            <div>
                                <div class="title">注册时间：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.resTime}}</div>
                            </div>
                            <div>
                                <div class="title">最后在线时间：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.lastTime}}</div>
                            </div>
                            <div>
                                <div class="title">最后同步时间：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.lastSysTime}}</div>
                            </div>
                            <div>
                                <div class="title">IP地址：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.ip}}</div>
                            </div>
                            <div>
                                <div class="title">MAC地址：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.mac}}</div>
                            </div>
                            <div>
                                <div class="title">终端系统：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.sys}}</div>
                            </div>
                            <div>
                                <div class="title">屏幕尺吋：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.size}}</div>
                            </div>
                            <div>
                                <div class="title">屏幕分辨率：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.ratio}}</div>
                            </div>
                            <div>
                                <div class="title">屏幕放置：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.rev}}</div>
                            </div>
                            <div>
                                <div class="title">软件版本：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.ver}}</div>
                            </div>
                            <div>
                                <div class="title">是否触控：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.typ}}</div>
                            </div>
                            <div>
                                <div class="title">负责人：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.principal}}</div>
                            </div>
                            <div>
                                <div class="title">负责人号码：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.tel}}</div>
                            </div>
                            <div>
                                <div class="title">终端所在地点：</div>
                                <div class="context">&nbsp;{{unrelatedTerminal.show_terminal.addr}}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <el-dialog @close="reset_update_area_basic_form" title="区域基础信息" :visible.sync="area_manage.basic_dialog" width="430px">
                <div>
                    <!-- 区域价格等信息设置界面 -->
                    <div>
                        <el-form label-width="120px" size="small" :model="area_manage.update_area_basic.form" status-icon 
                            ref="update_area_basic_form" :rules="area_manage.update_area_basic.rules" >
                            <div>
                                <div>
                                    <el-form-item label="区域名" prop="name">
                                        <el-input clearable v-model="area_manage.update_area_basic.form.name" placeholder="区域名"></el-input>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-form-item label="区域类型" prop="typeId">
                                        <el-select clearable v-model="area_manage.update_area_basic.form.typeId" placeholder="区域类型">
                                            <el-option
                                                v-for="item in commons.area_types"
                                                :key="item.id"
                                                :label="item.name"
                                                :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-form-item label="创建时间" prop="createTime">
                                        <el-date-picker
                                            v-model="area_manage.update_area_basic.form.createTime"
                                            type="date" 
                                            disabled
                                            placeholder="创建时间">
                                        </el-date-picker>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-form-item label="区域说明" prop="describes">
                                        <el-input
                                            type="textarea"
                                            :autosize="{ minRows: 1}"
                                            placeholder="请输入内容"
                                            v-model="area_manage.update_area_basic.form.describes">
                                        </el-input>
                                    </el-form-item>
                                </div>
                            </div>
                        </el-form>
                    </div>
                    <div>
                        <div style="width: 30%; margin: 10px 0; margin-left: 120px;">
                            <el-button size="small" 
                                type="primary" @click="update_area_basic">更新</el-button>
                        </div>
                    </div>
                </div>
            </el-dialog>

            <el-dialog @close="reset_update_area_info_form" title="区域信息" :visible.sync="area_manage.info_dialog" width="450px">
                <div>
                    <!-- 区域价格等信息设置界面 -->
                    <div>
                        <el-form label-width="120px" size="small" :model="area_manage.update_area_info.form" status-icon 
                            ref="update_area_info_form" :rules="area_manage.update_area_info.rules" >
                            <div>
                                <div>
                                    <el-form-item label="填充色" prop="fillColor">
                                        <el-color-picker v-model="area_manage.update_area_info.form.fillColor" 
                                            size="small"></el-color-picker>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-form-item label="边框色" prop="lineColor">
                                        <el-color-picker v-model="area_manage.update_area_info.form.lineColor" 
                                            size="small"></el-color-picker>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-form-item label="折扣系数" prop="discount">
                                        <el-slider v-model="area_manage.update_area_info.form.discount" 
                                            :format-tooltip="formatDiscountSlider"></el-slider>
                                    </el-form-item>
                                </div>
                                <div>
                                    <el-form-item label="区域等级" prop="areaLevel">
                                        <el-select
                                            clearable v-model="area_manage.update_area_info.form.areaLevel"
                                            placeholder="区域等级">
                                            <el-option label="A类" :value="1"></el-option>
                                            <el-option label="B类" :value="2"></el-option>
                                            <el-option label="C类" :value="3"></el-option>
                                            <el-option label="D类" :value="4"></el-option>
                                            <el-option label="E类" :value="5"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                            </div>
                        </el-form>
                    </div>
                    <div>
                        <div style="width: 30%; margin: 10px 0; margin-left: 120px;">
                            <el-button size="small" 
                                type="primary" @click="update_area_info">更新</el-button>
                        </div>
                    </div>
                </div>
            </el-dialog>

            <el-dialog class="area_terminal_diglog" title="区域终端信息" :visible.sync="query_area.area_terminal_dialog" width="310px">
                <div class="area_terminal_card">
                    <div>
                        <div class="title">终端名：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.name}}</div>
                    </div>
                    <div>
                        <div class="title">在线状态：</div>
                        <div class="context">
                            <span :style="query_area.in_dialog_terminal.online == 1 ? 'color: green; font-weight: bold;' : 'color: red; font-weight: bold;'">
                                &nbsp;{{query_area.in_dialog_terminal.online == 1 ? '在线' : '离线'}}
                            </span>
                        </div>
                    </div>
                    <div>
                        <div class="title">注册时间：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.resTime}}</div>
                    </div>
                    <div>
                        <div class="title">最后在线时间：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.lastTime}}</div>
                    </div>
                    <div>
                        <div class="title">最后同步时间：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.lastSysTime}}</div>
                    </div>
                    <div>
                        <div class="title">IP地址：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.ip}}</div>
                    </div>
                    <div>
                        <div class="title">MAC地址：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.mac}}</div>
                    </div>
                    <div>
                        <div class="title">终端系统：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.sys}}</div>
                    </div>
                    <div>
                        <div class="title">屏幕尺吋：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.size}}</div>
                    </div>
                    <div>
                        <div class="title">屏幕分辨率：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.ratio}}</div>
                    </div>
                    <div>
                        <div class="title">屏幕放置：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.rev}}</div>
                    </div>
                    <div>
                        <div class="title">软件版本：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.ver}}</div>
                    </div>
                    <div>
                        <div class="title">是否触控：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.typ}}</div>
                    </div>
                    <div>
                        <div class="title">负责人：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.principal}}</div>
                    </div>
                    <div>
                        <div class="title">负责人号码：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.tel}}</div>
                    </div>
                    <div>
                        <div class="title">终端所在地点：</div>
                        <div class="context">&nbsp;{{query_area.in_dialog_terminal.addr}}</div>
                    </div>
                </div>
            </el-dialog>
        </div>
    </body>

    <script type="text/javascript">
        var appInstince = new Vue({
            el: '#app',
            data: {
                commons: {
                    screen: {
                        height: null
                    },
                    tabs: {
                        switchName: 'first'
                    },
                    area_types: null,
                    showUnrelatedTerminalCard: false,    //显示未关联终端信息窗
                    showAreaInfoCard: false //显示实体区域信息卡片窗
                },
                map: {
                    main_map: null, //住地图
                    address_search: null,
                    address_search_markers: null,    //搜索后的marker点
                    address_search_optional: null,  //使用address_search搜索时，将快捷搜索出来的地址保存
                },
                insert_area: {
                    area_types: null,
                    prohibit_change_color: false,   //是否允许修改填充色和边框色
                    open_polygon_hole: false,    //当选择区域为多边形时，可以在多边形中扣洞，剔除部分区域
                    polygon_hole_shape: null,   //扣洞的形状
                    mouseTool_hole: null,   //扣洞鼠标工具
                    insertAreaInfoForm: {
                        area_type: null,
                        shape: null,
                        fillColor: "#00b0ff",
                        lineColor: "#80d8ff",
                        giss: null,
                        showGiss: null,    //giss是数组信息不方便展示，格式化后用此变量展示
                        name: null,
                        describes: null,
                        areaLevel: null
                    },
                    insertAreaInfoFormRules: {	/*添加区域信息信息验证*/
                        area_type: [
                            { required: true, message: '请选择要添加的区域类型', trigger: 'blur' }
                        ],
                        shape: [
                            { required: true, message: '请选择区域形状', trigger: 'blur' }
                        ],
                        name: [
                            { required: true, message: '请为勾选的这些区域起名', trigger: 'blur' }
                        ],
                        areaLevel: [
                            { required: true, message: '请选择区域等级', trigger: 'blur' }
                        ],
                        giss: [
                            { 
                                validator: function(rule, value, callback){
                                    if (appInstince.insert_area.insertAreaInfoForm.showGiss == null || 
                                        appInstince.insert_area.insertAreaInfoForm.showGiss == "") {
                                        callback(new Error('请勾选要添加的区域'));
                                    }
                                    callback();
                                },
                                trigger: 'blur'
                            }
                        ]
                    },

                    mouseTool: null
                },
                query_area: {
                    basic_pages: {
                        pageNum: 1,		/* 当前页 */
                        pageSize: 10,	/* 页面大小 */
                        total: 0,
                        list: []
                    },
                    now_basic_area: null,   //当前正在查看的基础区域
                    area_terminal_dialog: false,    //点击区域内终端marker，弹窗
                    in_dialog_terminal: {
                        name: null
                    },   //在弹窗里显示的终端
                    info_pages: {
                        pageNum: 1,		/* 当前页 */
                        pageSize: 10,	/* 页面大小 */
                        total: 0,
                        list: []
                    },
                    show_areas: null,   //管理已经显示的区域
                    show_terminal: null //管理已经显示的终端
                },
                area_manage: {
                    basic_dialog: false,
                    areaInfo: null,
                    update_area_basic: {
                        form: {

                        },
                        rules: {
                            name: [
                                { required: true, message: '请输入该区域的名字', trigger: 'blur' }
                            ],
                            typeId: [
                                { required: true, message: '请选择该区域的类型', trigger: 'blur' }
                            ]
                        }
                    },
                    info_dialog: false,
                    update_area_info: {
                        form: {

                        },
                        rules: {
                            lineColor: [
                                { required: true, message: '请选择区域填充色', trigger: 'blur' }
                            ],
                            fillColor: [
                                { required: true, message: '请选择区域边框色', trigger: 'blur' }
                            ],
                            areaLevel: [
                            { required: true, message: '请选择区域等级', trigger: 'blur' }
                            ]
                        }
                    }
                },
                unrelatedTerminal: {    //未关联的终端
                    pages: {
                        pageNum: 1,		/* 当前页 */
                        pageSize: 6,	/* 页面大小 */
                        total: 0,
                        list: []
                    },
                    show_terminal: null, //正在使用（显示的终端）
                    show_terminal_marker: null
                }
            },
            created: function () {
                this.getScreenHeightForPageSize();
                this.initMap();
                this.queryMapAreaTypes();
            },
            mounted:function () {
                
            },
            methods: {
                showUnrelatedTerminalInfoAndMarker(item) {  //显示未关联终端的信息和地图上的marker点
                    let obj = this;
                    obj.commons.showUnrelatedTerminalCard = true;
                    obj.unrelatedTerminal.show_terminal = item;

                    if (item.gis == null || item.gis == "") {
                        this.$message.error('该终端没有地理位置信息，无法显示位置');
                        if (obj.unrelatedTerminal.show_terminal_marker != null) {
                            obj.map.main_map.remove(obj.unrelatedTerminal.show_terminal_marker);
                        }
                        return;
                    }
                    if (obj.unrelatedTerminal.show_terminal_marker != null) {
                        obj.map.main_map.remove(obj.unrelatedTerminal.show_terminal_marker);
                    }

                    let gis = item.gis.split(",");
                    let iconPath;
                    if (item.online == 1) {
                        iconPath = '/view/cloud_screen/area_icon/marker_online.png';
                    } else {
                        iconPath = '/view/cloud_screen/area_icon/marker_offline.png';
                    }
                    obj.unrelatedTerminal.show_terminal_marker = new AMap.Marker({
                        position: [gis[0], gis[1]],
                        // offset: new AMap.Pixel(-7, -15),
                        // icon: iconPath, // 添加 Icon 图标 URL
                        title: item.name
                    });
                    obj.map.main_map.add(obj.unrelatedTerminal.show_terminal_marker);   //将marker点添加到地图上
                    obj.map.main_map.setFitView();	/*根据marker缩放自适应*/
                },
                queryUnrelatedTerminal() {  //查询未关联的终端
                    let obj = this;
                    var url = "/area/info/queryAreaTerminalPages";
                    var t = {
                        pageNum: obj.unrelatedTerminal.pages.pageNum,
                        pageSize: obj.unrelatedTerminal.pages.pageSize,
                        mapAreaIdIsNull: 'mapAreaIdIsNull'
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            obj.unrelatedTerminal.pages = data.data;
                        }
                    })
                },
                reset_update_area_basic_form() {
                    let obj = this;
                    obj.$refs.update_area_basic_form.resetFields();
                },
                reset_update_area_info_form() {
                    let obj = this;
                    obj.$refs.update_area_info_form.resetFields();
                },
                update_area_info() {
                    var obj = this;
                    this.$refs.update_area_info_form.validate( function(valid) {
                        if (valid) {
                            var url = "/area/info/updateMapAreaInfo";
                            var t = obj.area_manage.update_area_info.form;
                            $.post(url, t, function(data, status){
                                if (data != null && data.code == 200) {
                                    toast('提示',data.msg,'success');
                                    obj.area_manage.info_dialog = false;
                                    obj.queryMapAreaInfosPages(obj.query_area.now_basic_area)
                                }
                            })
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                },
                update_area_basic() {
                    var obj = this;
                    this.$refs.update_area_basic_form.validate( function(valid) {
                        if (valid) {
                            var url = "/area/info/updateMapAreaBasic";
                            obj.area_manage.update_area_basic.form.createTime = new Date(obj.area_manage.update_area_basic.form.createTime);
                            var t = obj.area_manage.update_area_basic.form;
                            $.post(url, t, function(data, status){
                                if (data != null && data.code == 200) {
                                    toast('提示',data.msg,'success');
                                    obj.area_manage.basic_dialog = false;
                                    obj.queryMapAreaBasicsPages();
                                }
                            })
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                },
                openAreaBasicManageDialog(item) {
                    let obj = this;
                    var url = "/area/info/queryMapAreaBasics";
                    var t = {
                        areaBasicId: item.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != null) {
                            obj.area_manage.update_area_basic.form = data.data[0];
                            obj.area_manage.basic_dialog = true;
                        }
                    })
                },
                openAreaInfoManageDialog(item) {
                    let obj = this;
                    var url = "/area/info/queryMapAreaInfosNoPage";
                    var t = {
                        infoId: item.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != null) {
                            obj.area_manage.update_area_info.form = data.data[0];
                            obj.area_manage.info_dialog = true;
                        }
                    })
                },
                showAreaTerminalMarker(item) {  //将终端用marker点的方式显示在地图上
                    var obj = this;
                    var url = "/area/info/queryAreaTerminal";
                    var t = {
                        mapAreaId: item.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != null && data.data.length > 0) {
                            if (obj.query_area.show_terminal == null) {
                                obj.query_area.show_terminal = new Array;
                            }
                            for (let i = 0; i < obj.query_area.show_terminal.length; i++) {
                                let terminal = obj.query_area.show_terminal[i];
                                if (terminal.id == item.id) {
                                    //已经显示过
                                    obj.map.main_map.remove(terminal.terminalMarker);
                                    obj.query_area.show_terminal.splice(i, 1); //取消显示，移除此元素
                                    return;
                                }
                            }

                            var style = [
                                {
                                    url: '/view/cloud_screen/area_icon/marker_online.png',  // 图标地址-在线
                                    size: new AMap.Size(20, 48),      // 图标大小
                                    anchor: new AMap.Pixel(10, 24) // 图标显示位置偏移量，基准点为图标左上角
                                },
                                {
                                    url: '/view/cloud_screen/area_icon/marker_offline.png',  // 图标地址-离线
                                    size: new AMap.Size(20, 48),      // 图标大小
                                    anchor: new AMap.Pixel(10, 24) // 图标显示位置偏移量，基准点为图标左上角
                                }
                            ];
                            var datas = new Array;
                            for (let i = 0; i < data.data.length; i++) {
                                var d = data.data[i];
                                if (d.gis == null || d.gis == "") continue;
                                var lnglat = d.gis.split(",");
                                var t_data = {
                                    lnglat: [lnglat[0], lnglat[1]],
                                    style: d.online == 1 ? 0 : 1,
                                    terminal: d
                                }
                                datas.push(t_data);
                            }
                            var massMarks = new AMap.MassMarks(datas, {
                                zIndex: 999,  // 海量点图层叠加的顺序
                                zooms: [3, 18],  // 在指定地图缩放级别范围内展示海量点图层
                                cursor:'pointer',
                                style: style  // 设置样式对象
                            });
                            massMarks.on('click', function(object){
                                obj.query_area.area_terminal_dialog = true;
                                obj.query_area.in_dialog_terminal = object.data.terminal;
                            });
                            massMarks.setMap(obj.map.main_map);

                            let terminal = {
                                id: item.id,
                                terminalMarker: massMarks
                            }
                            //加入已显示区域
                            obj.query_area.show_terminal.push(terminal);
                        }
                    })
                },

                queryMapAreaLatLngs(item) {
                    var obj = this;
                    var url = "/area/info/queryMapAreaLatLngs";
                    var t = {
                        infoId: item.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != null && data.data.length > 0) {
                            obj.showAreaTerminalMarker(item);
                            if (obj.query_area.show_areas == null) {
                                obj.query_area.show_areas = new Array;
                            }

                            for (let i = 0; i < obj.query_area.show_areas.length; i++) {
                                let area = obj.query_area.show_areas[i];
                                if (area.id == item.id) {
                                    //已经显示过
                                    obj.map.main_map.remove(area.polygon);
                                    obj.map.main_map.setFitView();
                                    obj.query_area.show_areas.splice(i, 1); //取消显示，移除此元素
                                    console.log("已显示过");
                                    return;
                                }
                            }
                            let area = null;
                            if (item.shape == "polygon") {
                                //否则没有在地图上显示过该区域
                                let paths = new Array;
                                data.data.forEach(path => {
                                    let pa = new Array;
                                    pa.push(path.lng);  //先设经度
                                    pa.push(path.lat);  //在设纬度
                                    paths.push(pa);
                                });

                                area = new AMap.Polygon({
                                    path: paths,
                                    strokeColor: item.lineColor,
                                    strokeOpacity: 0.9,
                                    fillColor: item.fillColor,
                                    fillOpacity: 0.5
                                })
                            } else if (item.shape == "circle") {
                                let path = data.data[0];
                                let pa = new Array;
                                pa.push(path.lng);  //先设经度
                                pa.push(path.lat);  //在设纬度
                                area = new AMap.Circle({
                                    center: pa,
                                    radius: path.radius, //半径
                                    strokeColor: item.lineColor, 
                                    strokeOpacity: 0.9,
                                    fillColor: item.fillColor,
                                    fillOpacity: 0.5
                                })
                            } else if (item.shape == "rectangle") {
                                var southWest = new AMap.LngLat(data.data[0].southwestLng, data.data[0].southwestLat)
                                var northEast = new AMap.LngLat(data.data[0].northeastLng, data.data[0].northeastLat)

                                var bounds = new AMap.Bounds(southWest, northEast)

                                area = new AMap.Rectangle({
                                    bounds: bounds,
                                    strokeColor: item.lineColor,
                                    strokeOpacity: 0.9,
                                    fillColor: item.fillColor,
                                    fillOpacity: 0.5
                                })
                            }
                            obj.map.main_map.add(area);
                            // 缩放地图到合适的视野级别
                            obj.map.main_map.setFitView();
                            let areaInfo = {
                                id: item.id,
                                polygon: area
                            }
                            //加入已显示区域
                            obj.query_area.show_areas.push(areaInfo);
                        }
                    })
                },
                queryMapAreaInfosPages(item) {
                    var obj = this;
                    var url = "/area/info/queryMapAreaInfos";
                    obj.query_area.now_basic_area = item;
                    var t = {
                        pageNum: obj.query_area.info_pages.pageNum,
                        pageSize: obj.query_area.info_pages.pageSize,
                        basicId: item.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            obj.query_area.info_pages = data.data;
                            obj.commons.showAreaInfoCard = true;
                        }
                    })
                },
                queryMapAreaBasicsPages() {
                    var obj = this;
                    var url = "/area/info/queryMapAreaBasicsPages";
                    var t = {
                        pageNum: obj.query_area.basic_pages.pageNum,
                        pageSize: obj.query_area.basic_pages.pageSize
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            obj.query_area.basic_pages = data.data;
                        }
                    })
                },

                insertMapAreaInfo() {   //添加区域信息
                    var obj = this;
                    this.$refs.insertAreaInfoForm.validate( function(valid) {
                        if (valid) {
                            //取得所有的区域信息
                            let overlays = obj.map.main_map.getAllOverlays();
                            let map_areas = new Array;
                            overlays.forEach(overlay => {
                                //遍历取得每个区域的经纬度信息
                                let latlngs = new Array;    //经纬度数组
                                let radius = null;
                                let shape = overlay.extData;;
                                let fill_color = overlay.getOptions().fillColor;
                                let stroke_color = overlay.getOptions().strokeColor;
                                if (overlay.extData == "circle") {
                                    let latlng = {
                                        lat: overlay.getOptions().center.lat,
                                        lng: overlay.getOptions().center.lng
                                    }
                                    latlngs.push(latlng);
                                    radius = overlay.getOptions().radius;
                                } else if (overlay.extData == "polygon") {
                                    overlay.getOptions().path.forEach(path => {
                                        let latlng = {
                                            lat: path.lat,
                                            lng: path.lng
                                        }
                                        latlngs.push(latlng);
                                    });
                                } else if (overlay.extData == "rectangle") {
                                    //高德地图中没有斜的矩形，所以在高德中构造矩形只需要西南角和东北角的坐标信息
                                    //所以在数据库里只保存这两个点的信息
                                    let latlng = {
                                        southwestLat: overlay.getBounds().southwest.lat,
                                        southwestLng: overlay.getBounds().southwest.lng,
                                        northeastLat: overlay.getBounds().northeast.lat,
                                        northeastLng: overlay.getBounds().northeast.lng
                                    }
                                    latlngs.push(latlng);
                                }

                                let map_area = {
                                    latlngs: latlngs,
                                    radius: radius,
                                    shape: shape,
                                    fill_color: fill_color,
                                    stroke_color: stroke_color
                                }
                                map_areas.push(map_area);
                            });
                            
                            var url = "/area/info/insertMapAreaInfo";
                            let condi = {
                                type_id: obj.insert_area.insertAreaInfoForm.area_type,
                                name: obj.insert_area.insertAreaInfoForm.name,
                                describes: obj.insert_area.insertAreaInfoForm.describes,
                                areaLevel: obj.insert_area.insertAreaInfoForm.areaLevel,
                                map_areas: map_areas,
                                discount: 100   //折扣系数，默认不打折
                            }
                            var t = {
                                condition: JSON.stringify(condi)
                            }
                            $.post(url, t, function(data, status){
                                if (data.code == 200) {
                                    obj.$message({
                                        message: "添加成功，可以去“区域管理”查看刚才添加的区域",
                                        type: 'success'
                                    });
                                    obj.resetInsertMapAreaInfo();
                                }
                            })
                        }
                    })
                },
                resetInsertMapAreaInfo() {  //重置添加区域信息
                    var obj = this;
				    obj.$refs.insertAreaInfoForm.resetFields();
                    obj.insert_area.insertAreaInfoForm.showGiss = null;
                    obj.commons.showUnrelatedTerminalCard = false;
                    obj.commons.showAreaInfoCard = false;
                    obj.insert_area.open_polygon_hole = false;   //关闭扣洞
                    obj.insert_area.polygon_hole_shape = null;
                    if (obj.insert_area.mouseTool != null) {
                        //关闭鼠标工具并清除所有待添加的覆盖物
                        obj.insert_area.mouseTool.close(true);
                    }
                    if (obj.insert_area.mouseTool_hole != null) {
                        //关闭鼠标工具并清除所有待添加的覆盖物
                        obj.insert_area.mouseTool_hole.close(true);
                    }
                },
                resetManageMapAreaInfo() {  //重置区域管理区域信息
                    let obj = this;
                    obj.commons.showUnrelatedTerminalCard = false;
                    obj.commons.showAreaInfoCard = false;
                    obj.map.main_map.clearMap();    //清除所有覆盖物，避免添加覆盖物时读取到显示的覆盖物信息
                    //清除每个点的海量点标记
                    if (obj.query_area.show_terminal != null) {
                        for (let i = 0; i < obj.query_area.show_terminal.length; i++) {
                            obj.map.main_map.remove(obj.query_area.show_terminal[i].terminalMarker);
                        }
                    }
                },
                resetUnrelatedTerminal() {  //重置未关联设备区域信息
                    let obj = this;
                    obj.commons.showUnrelatedTerminalCard = false;
                    obj.commons.showAreaInfoCard = false;
                    if (obj.unrelatedTerminal.show_terminal_marker != null) {
                        obj.map.main_map.remove(obj.unrelatedTerminal.show_terminal_marker);
                    }
                },
                initMouseTool() {   //初始化鼠标工具用于绘制区域
                    let obj = this;
                    if (obj.insert_area.mouseTool == null) {
                        obj.insert_area.mouseTool = new AMap.MouseTool(obj.map.main_map); 
                    }
                    if (obj.insert_area.insertAreaInfoForm.shape == "polygon") {
                        obj.insert_area.mouseTool.polygon({
                            fillColor: obj.insert_area.insertAreaInfoForm.fillColor,
                            strokeColor: obj.insert_area.insertAreaInfoForm.lineColor,
                            strokeOpacity: 0.9,
                            fillOpacity: 0.5
                        });  
                    } else if (obj.insert_area.insertAreaInfoForm.shape == "rectangle") {
                        obj.insert_area.mouseTool.rectangle({
                            fillColor: obj.insert_area.insertAreaInfoForm.fillColor,
                            strokeColor: obj.insert_area.insertAreaInfoForm.lineColor,
                            strokeOpacity: 0.9,
                            fillOpacity: 0.5
                        });
                    } else if (obj.insert_area.insertAreaInfoForm.shape == "circle") {
                        obj.insert_area.mouseTool.circle({
                            fillColor: obj.insert_area.insertAreaInfoForm.fillColor,
                            strokeColor: obj.insert_area.insertAreaInfoForm.lineColor,
                            strokeOpacity: 0.9,
                            fillOpacity: 0.5
                        });
                    }

                    //绑定绘图时事件
                    obj.insert_area.mouseTool.on('draw',function(e){
                        let overlays = obj.map.main_map.getAllOverlays();
                        if (overlays != null && overlays.length > 1) {
                            obj.$message.error('只能添加一个区域哦');
                            obj.map.main_map.remove(e.obj);
                            return;
                        }
                        //画了一个图形之后不允许在修改颜色
                        obj.insert_area.insertAreaInfoForm.prohibit_change_color = true;
                        const overlay = overlays[0];
                        //extData为高德为用户提供的自定义属性，这里保存区域类型
                        if (overlay.extData == null) {
                            //之前有些设置过了
                            overlay.extData = obj.insert_area.insertAreaInfoForm.shape;
                        }
                        if (overlay.extData == "polygon") {
                            obj.insert_area.open_polygon_hole = true;   //开启扣洞
                        }
                        if (overlay.extData == "polygon") {
                            obj.insert_area.insertAreaInfoForm.showGiss = "标记区域，多边形，坐标\n";
                            overlay.getOptions().path.forEach(path => {
                                obj.insert_area.insertAreaInfoForm.showGiss += (path + "\n");
                            });
                            obj.insert_area.insertAreaInfoForm.showGiss += "\n";
                        } else if (overlay.extData == "circle") {
                            obj.insert_area.insertAreaInfoForm.showGiss = "标记区域，圆形，坐标\n";
                            obj.insert_area.insertAreaInfoForm.showGiss += 
                                (overlay.getOptions().center + "\n半径" + overlay.getOptions().radius + "\n\n");
                        } else if (overlay.extData == "rectangle") {
                            obj.insert_area.insertAreaInfoForm.showGiss = "标记区域，矩形，坐标\n";
                            obj.insert_area.insertAreaInfoForm.showGiss += "西南角：" + overlay.getBounds().southwest + "\n";
                            obj.insert_area.insertAreaInfoForm.showGiss += "东北角：" + overlay.getBounds().northeast + "\n";
                        }
                        if (obj.insert_area.mouseTool != null) {
                            //关闭鼠标工具不清除覆盖物
                            obj.insert_area.mouseTool.close(false);
                        }
                    });
                },
                initMouseTool_Hole() {  //扣洞，只能抠多边形
                    let obj = this;
                    if (obj.insert_area.mouseTool_hole == null) {
                        obj.insert_area.mouseTool_hole = new AMap.MouseTool(obj.map.main_map); 
                    }
                    if (obj.insert_area.polygon_hole_shape == "polygon") {
                        obj.insert_area.mouseTool_hole.polygon({
                            fillColor: obj.insert_area.insertAreaInfoForm.fillColor,
                            strokeColor: obj.insert_area.insertAreaInfoForm.lineColor
                        });  
                    } else {
                        return;
                    } 

                    //绑定绘图时事件
                    obj.insert_area.mouseTool_hole.on('draw',function(e){
                        //有且只有一个元素
                        let overlay = obj.map.main_map.getAllOverlays()[0];
                        let thisPolygonPath = overlay.getPath(); //当前多边形的path
                        //判断依据：如果已经扣过洞，那么多边形的path是一个二维数组，否则是一个一维数组
                        if (Array.isArray(thisPolygonPath[0])) {
                            //已经扣过
                            let path = new Array;
                            path = overlay.getPath();
                            path.push(e.obj.getPath());
                            overlay.setPath(path);
                        } else {
                            //未抠过
                            let path = [
                                overlay.getPath(),
                                e.obj.getPath()
                            ]
                            overlay.setPath(path);
                        }
                        obj.map.main_map.remove(e.obj); //移除用于抠洞的多边形
                    });
                },
                tabSwitchInit(tab, event) { //切换tab时初始化一些信息
                    let obj = this;
                    if (tab.name == "first") {
                        obj.resetManageMapAreaInfo();
                        obj.resetUnrelatedTerminal();
                    } else if (tab.name == "second") {
                        obj.resetInsertMapAreaInfo();   //重置添加区域信息
                        obj.resetUnrelatedTerminal();
                        //查询前个性化每页显示数据量
                        obj.query_area.basic_pages.pageSize = parseInt((obj.commons.screen.height - 120) / 50);
                        obj.query_area.info_pages.pageSize = parseInt(((obj.commons.screen.height * 0.92 * 0.9) - 100) / 50);
                        obj.queryMapAreaBasicsPages();    //查询区域信息
                    } else if (tab.name == "third") {
                        obj.resetInsertMapAreaInfo();   //重置添加区域信息
                        obj.resetManageMapAreaInfo();
                        //查询前个性化每页显示数据量
                        obj.unrelatedTerminal.pages.pageSize = parseInt((obj.commons.screen.height - 120) / 50);
                        obj.queryUnrelatedTerminal();    //查询未关联终端
                    }
                },
                


                queryMapAreaTypes() {   //查询区域类型
                    var obj = this;
                    url = "/area/type/queryMapAreaTypes";
                    var t = {
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            obj.insert_area.area_types = data.data;
                            obj.commons.area_types = data.data;
                        }
                        
                    })
                },
                search_address_optional(val) {  //搜索框值变更时及时搜索符合条件的位置
                    let obj= this;
                    obj.map.address_search_optional = new Array;

                    AMap.plugin('AMap.Autocomplete', function(){
                        // 实例化Autocomplete
                        var autoOptions = {
                            city: '全国'
                        }
                        var autoComplete = new AMap.Autocomplete(autoOptions);
                        autoComplete.search(val, function(status, result) {
                            if (status == "complete") { //搜索到结果
                                console.log(result);    //输出搜索结果
                                obj.map.address_search_optional = result.tips;
                            }
                        })
                    })
                },
                search_main_map() { //搜索框定位地点
                    let obj = this;
                    if (obj.map.address_search == null || obj.map.address_search == "") {
                        return;
                    }
                    var marker = new AMap.Marker({
                        position: [obj.map.address_search.location.getLng(), obj.map.address_search.location.getLat()]
                    });
                    obj.map.main_map.add(marker);   //将marker点添加到地图上
                    obj.map.main_map.setFitView();	/*根据marker缩放自适应*/
                    obj.map.main_map.remove(marker); //缩放完毕，移除marker
                },
                initMap() { //初始化地图
                    let obj = this;
                    window.init = function(){
                        // 绘制地图
                        obj.map.main_map = new AMap.Map('main_map', {
                            resizeEnable: true, //是否监控地图容器尺寸变化
                            zoom: 15, //初始化地图层级
                            //mapStyle: 'amap://styles/normal', //设置地图的显示样式
                            //viewMode:'3D'//使用3D视图
                        });
                        obj.map.main_map.on('complete', function(){
                            // 地图图块加载完成后触发
                            console.log("地图图块加载完成");
                        });
                    }
                },
                getScreenHeightForPageSize() {	//得到屏幕高度
                    var obj = this;
                    obj.commons.screen.height = window.innerHeight;
                },
                formatDiscountSlider(val) {
                    if (val == 100) {
                        return "不打折";
                    } else if (val == 0) {
                        return "免费";
                    }

                    return "折扣系数：" + val + " 折";
                }
            }
        });

        window.onFocus = function () {
            window.location.reload();
        }
    </script>
    <!-- callback=init异步加载，多个插件用逗号隔开 -->
    <!-- AMap.Autocomplete 输入提示插件 -->
    <!-- AMap.Geocoder 搜索 -->
    <script type="text/javascript" 
        src="https://webapi.amap.com/maps?v=1.4.6&key=b8db1a2a77d2226ba663235353e3546b&plugin=AMap.MouseTool,AMap.Geocoder,AMap.Autocomplete&callback=init">
    </script>
</html>