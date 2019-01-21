<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
    <base href="/">
    <meta charset="UTF-8">

    <title>终端配置</title>
    <%@include file="/include/head.jsp"%>

    <link href="${urls.getForLookupPath('/assets/module/terminal/terminal-control.css')}" rel="stylesheet">
    <%@include file="/include/map-gaode.jsp"%>
</head>

<body>
    <div id="app">
        <div id="toolbar">
            <el-row>
                <el-col :span="24">
                    <el-menu :default-active="tqueryIf.online=='1'?'1':'2'" class="nav-menu el-menu-demo" mode="horizontal">
                        <el-menu-item index="-1" class="t-title" disabled>未配置终端: </el-menu-item>
                        <el-menu-item index="1" @click="changeSearch(1)">
                            <i class="el-icon-check online-icon"></i>正常 ({{onlineCount.online?onlineCount.online:0}})</el-menu-item>
                        <el-menu-item index="2" @click="changeSearch(0)">
                            <i class="el-icon-close offline-icon"></i> 终端不在线
                            ({{onlineCount.offline?onlineCount.offline:0}})</el-menu-item>
                        <shiro:hasPermission name="terminal:orgconfig:list-configed-terminals">
                            <el-menu-item index="3" class="t-title" disabled>
                                <el-checkbox v-model="tqueryIf.orgId" label="所有终端" :true-label="-1" :false-label="0"
                                    @change="loadTeminalInfo"></el-checkbox>
                            </el-menu-item>
                        </shiro:hasPermission>
                        <el-menu-item index="-3" class="t-title" disabled>
                            <el-button-group class="control-button">
                                <el-button size="small" :type="!dis_h_v?'primary':''" icon="el-icon-menu" @click="dis_h_v=false"></el-button>
                                <el-button size="small" :type="dis_h_v?'primary':''" icon="el-icon-tickets" @click="dis_h_v=true"></el-button>
                            </el-button-group>
                        </el-menu-item>
                        <el-menu-item index="-4" class="t-title" disabled>
                            <el-popover placement="top-start" title="配置终端" width="400" trigger="hover">
                                <div>
                                    <p>终端关联所属组织流程</p>
                                    <ol>
                                        <li>1. 打开播放器安装路径目录下 <code>guid.config</code> </li>
                                        <li>2. 选择关联的组织</li>
                                        <li>3. 完成</li>
                                    </ol>

                                    <el-button type="primary" @click="pgw.visiable=true">开始</el-button>
                                </div>
                                <el-button slot="reference" type="text">配置终端</el-button>
                            </el-popover>
                        </el-menu-item>
                        <el-menu-item index="-5" class="t-title" disabled>
                            <el-button type="text" @click="syn" class="syn">同步终端</el-button>
                        </el-menu-item>
                        <el-menu-item index="-2" :style="{'float':'right'}" class="t-title" disabled>
                            <el-pagination class="pagebar" :current-page="tpager.current" :page-sizes="[10, 20, 30]"
                                :page-size="tpager.size" layout="total, sizes, prev, pager, next, jumper" :total="tpager.total"
                                @size-change="handleSizeChange" @current-change="handleCurrentChange">
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
                                                <el-tag size="small" :type="t.online == '1'?'success':'danger'">{{t.online
                                                    == '1' ? '是':'否'}}</el-tag>
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
                                            <td>所属组织 </td>
                                            <td>：</td>
                                            <td>
                                                {{getBelowOrg(t)}}
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <div class="bottom-h">
                                        <el-button type="button" size="mini" class="button" @click="openTerminalConfig(t)">配置</el-button>
                                        <br />
                                        <el-button type="danger" size="mini" class="button" @click="terminalDelete(t)">删除</el-button>
                                    </div>
                                </td>
                            </tr>
                        </template>
                    </tbody>
                </table>
            </div>
            <div v-show="!dis_h_v">
                <template v-for="t in terminals">
                    <el-card class="termialDis" :class="t.online=='1' ? 'online':'termialDis-offline'" :body-style="{ padding: '0px' }"
                        shadow="never">
                        <div class="deviceInfo">
                            <span class="name"><i class="el-icon-star-on"></i> {{t.name}}</span>
                            <span class="size">{{t.size}}</span>
                        </div>
                        <div :class="t.rev == '横屏' ? 'screenshoot-h':'screenshoot-v'">
                            <img :src="t.coverImage" class="image">
                            <div class="ratio">{{t.ratio}}</div>
                        </div>
                        <div class="control">
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
                                    <td><i class="el-icon-location-outline"></i> 所属组织</td>
                                    <td></td>
                                    <td>
                                        <p class="disInfo-overflow">{{getBelowOrg(t)}}</p>
                                    </td>
                                </tr>
                            </table>
                            <div class="bottom clearfix">
                                <el-button size="mini" icon="el-icon-edit" type="primary" @click="updateTbi(t)">编辑</el-button>
                                <el-button type="button" size="mini" class="button" @click="openTerminalConfig(t)">配置</el-button>
                                <el-button type="danger" size="mini" class="button" @click="terminalDelete(t)">删除</el-button>
                                <el-button v-if="t.orgId != 0" type="warning" size="mini" class="button" @click="terminalConfigDelete(t)">删除关联</el-button>
                            </div>
                        </div>
                    </el-card>
                </template>
            </div>
        </div>
        <!--配置终端-->
        <el-dialog :title="pgw.title" :visible.sync="pgw.visiable" :fullscreen="pgw.fullscreen" :width="pgw.fullscreen?'100%':'60%'"
            v-loading.lock="programLoading">
            <div slot="title">
                <span class="el-dialog__title"> {{pgw.title}}</span>
                <el-button-group style="margin-left:30px;">
                    <el-button size="small" icon="el-icon-rank" @click="pgw.fullscreen=!pgw.fullscreen" title="最大"></el-button>
                </el-button-group>
            </div>
            <div>
                <div>
                    <div>
                        <h2 class="choice">选择guid</h2>
                        <div class="guid-container">
                            <template v-for="tag in guidTags">
                                <el-card class="guid-box" :class="tag.status && tag.checked?'success':'fail'" :body-style="{ padding: '5px' }">
                                    <h3>{{tag.guid}}</h3>
                                    <p>{{tag.msg}}</p>
                                    <div class="bottom clearfix">
                                        <el-button type="danger" size="mini" class="button" @click="handleClose(tag)">删除</el-button>
                                    </div>
                                </el-card>
                            </template>
                        </div>
                        <el-upload class="upload-demo .margin-10" action="/" :on-change="fileSelectChange" :auto-upload="false"
                            accept=".config" :show-file-list="false" multiple>
                            <el-button size="small">选择guid.config</el-button>
                            <div slot="tip" class="el-upload__tip">点击选择<code>guid.config</code>文件</div>
                        </el-upload>
                    </div>
                    <div>
                        <h2 class="contact">选择关联组织</h2>
                        <el-tree ref="orgTree" :data="orgTreeData" :props="props"></el-tree>
                    </div>

                    <el-button type="primary" @click="submitConfig">提交</el-button>
                </div>
            </div>
        </el-dialog>

        <el-dialog title="" :visible.sync="tbi.visible" width="40%">
			<el-form :model="tbi" label-width="100px" size="mini">
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="责任人">
							<el-input v-model="tbi.data.principal"></el-input>
						</el-form-item>
					</el-col>

					<el-col :span="10">
						<el-form-item label="所属地区">
							<el-input v-model="tbi.data.loc"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">
						<el-form-item label="在线状态">
							<el-input v-model="tbi.data.online"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="屏幕方向">
							<el-input v-model="tbi.data.rev"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">
						<el-form-item label="屏幕尺寸">
							<el-input v-model="tbi.data.size"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="屏幕分辨率">
							<el-input v-model="tbi.data.ratio"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="联系电话">
							<el-input v-model="tbi.data.tel"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="终端地址">
							<el-input v-model="tbi.data.addr"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">
						<el-form-item label="标识ID">
							<el-input v-model="tbi.data.id"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="标识code">
							<el-input v-model="tbi.data.code"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="类别id">
							<el-input v-model="tbi.data.typeId"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="注册时间">
							<el-input v-model="tbi.data.resTime"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="最新时间">
							<el-input v-model="tbi.data.lastTime"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="ip地址">
							<el-input v-model="tbi.data.ip"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="mac地址">
							<el-input v-model="tbi.data.mac"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="系统">
							<el-input v-model="tbi.data.sys"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center" v-if="tbi.search">
					<el-col :span="10">

						<el-form-item label="系统版本">
							<el-input v-model="tbi.data.ver"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<el-form-item label="终端类型">
							<el-input v-model="tbi.data.typ"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">

						<el-form-item label="GIS信息">
							<el-input v-model="tbi.data.gis"></el-input>
							<el-button type="primary" size="mini" @click="mapThings2()" v-if="!tbi.search">地图支持</el-button>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<el-form-item label="保修信息">
							<el-input v-model="tbi.data.warranty"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :span="10">
						<el-form-item label="终端名称" v-if="tbi.search">
							<el-input v-model="tbi.data.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">

						<!-- <el-form-item label="责任人">
							<el-input v-model="tbi.data.principal"></el-input>
						</el-form-item> -->
					</el-col>

				</el-row>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="refresh()" size="mini">取 消</el-button>
				<el-button type="primary" @click="updateorinsert(tbi)" size="mini">确 定</el-button>

			</div>
			<div v-if="tbi.visibleMap2" style="height: 500px">
				<div id="myPageTop">
					<table>
						<tr>
							<td>
								<label>请输入关键字：</label>
							</td>
						</tr>
						<tr>
							<td>
								<el-input v-model="tbi.helpMap" size="mini"></el-input>
							</td>
							<td>
								<el-button type="primary" size="mini" @click="drawmap2()"> 搜索</el-button>
							</td>
						</tr>
					</table>
				</div>
				<div id="container1" style="height: 100% ; width: 100%"></div>
			</div>
		</el-dialog>
        <!--dialog-->
    </div>
</body>

</html>
<script type="text/javascript" charset="utf-8" src="${urls.getForLookupPath('/assets/module/terminal/terminal_config.js')}"></script>