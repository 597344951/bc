<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<title>${sysInfo.appname}</title>
<%@include file="/include/head.jsp"%>
<%@include file="/include/websocket.jsp"%>
<link href="${urls.getForLookupPath('/assets/css/index.css')}" rel="stylesheet">


<style type="text/css">
.collapseIcon,.glyphicon-send {
    line-height: 55px;
}
</style>
</head>
<body>
    <el-container class="mycontainer" id="app" v-cloak>
        <el-header class="noselect" height="60px">
            <!--head-->
            <el-col class="header" :span="24">
                <el-col :span="10" :class="app.isCollapse?' logo logo-collapse-width':'logo logo-width'">
                    {{app.isCollapse?'':app.sysName}}
                    <i v-if="app.isCollapse" :class="app.sysIcon" :title="app.sysName"></i>
                </el-col>
                <el-col :span="4">
                    <div class="tools" @click.prevent="openHideMenu">
                        <i class="glyphicon glyphicon-align-justify collapseIcon"></i>
                    </div>
                </el-col>
                <el-col class="userinfo" :span="10">
                    <ul class="top-nav">
                        <li>
                            <el-dropdown trigger="hover">
                                <span class="el-dropdown-link userinfo-inner">
                                    <img :src="this.app.sysUserAvatar"> {{app.sysUserName}}</span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>我的消息</el-dropdown-item>
                                    <el-dropdown-item>设置</el-dropdown-item>
                                    <el-dropdown-item @click.native="changeTheme.visible=true">切换主题色</el-dropdown-item>
                                    <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </li>
                        <li>
                            <message-notice :data="messages" @click="message_click" @allclick="messageAllClick"></message-notice>
                        </li>
                        <li>
                             <span class="el-icon-rank pop-message-button" @click="setFullscreen" title="最大"></span>
                        </li>
                    </ul>
                </el-col>
            </el-col>

            <!--head-->
        </el-header>
        <el-container class="mainContainer">
            <el-aside class="menu_bg" :width="app.menuWidth" v-bind:style="{'background-color': app.colors.menu_bg }">

                <!--菜单 -->
                <el-menu :collapse="app.isCollapse" @open="handleOpen" @close="handleClose" :background-color="app.colors.menu_bg" :text-color="app.colors.menu_txt"
                    :active-text-color="app.colors.menu_act_txt" :unique-opened="true">
                    <el-submenu v-for="menu in menuDatas" :index="menu.menuId+''" :key="menu.menuId">
                        <template slot="title">
                            <i v-bind:class="menu.icon"></i>
                            <span>{{menu.name}}</span>
                        </template>
                        <el-menu-item v-for="sm in menu.children" :index="sm.menuId+''" :key="sm.menuId" v-on:click="menuItemClick(sm)">
                            <i v-bind:class="sm.icon"></i>
                            <span slot="title">{{sm.name}}</span>
                        </el-menu-item>
                    </el-submenu>

                </el-menu>
                <!--菜单 -->

            </el-aside>
            <el-main>
                <!--主体-->
                <el-tabs class="mytab noselect" type="border-card" v-model="tab_index" @tab-remove="removeTab">
                    <el-tab-pane class="full" v-for="(item, index) in openTabDatas" :key="item.menuId" :name="item.menuId +''" :closable="item.closable" >
                        <span slot="label" @contextmenu.prevent="showMenu(item)" class="tab-label-padding">
                            <i :class="item.icon"></i>{{item.name}}</span>
                        <iframe :id="item.menuId +'_iframe'" v-bind:src="item.url"></iframe>
                    </el-tab-pane>
                </el-tabs>
                <!--主体-->
            </el-main>
        </el-container>

        <!--dialog-->
        <el-dialog title="切换主题色" :visible.sync="changeTheme.visible" width="400" center>
            <center>
                <el-form label-position="top" label-width="180px">
                    <el-form-item label="主题颜色 ">
                        <el-color-picker v-model="changeTheme.color"></el-color-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="changeTheme_handle">更改</el-button>
                        <el-button @click="changeTheme.visible=false">取消</el-button>
                    </el-form-item>
                </el-form>
            </center>
        </el-dialog>
        <!--contextMenu-->
        <context-menu :visiable.sync="contextMenu.visiable" :mouse-event="contextMenu.event" @click="contextMenuClick" :data="contextMenuData"></context-menu>
    </el-container>
</body>
</html>
<script>
$(function(){
	//检测当前页面是否和顶层一致
	if(!checkLocationSame()){
		top.location.href = window.location.href;
	}
});
var sysInfo = {
    sysName: '${sysInfo.appname}',
    userId:'<shiro:principal property="userId"/>',
    sysUserName: '<shiro:principal property="username"/>',
    title_theme: ('#'+('<shiro:principal property="theme"/>' || '20a0ff'))
}
window.sysInfo = sysInfo;
</script>
<script type="module" src="${urls.getForLookupPath('/assets/module/index.js')}"></script>