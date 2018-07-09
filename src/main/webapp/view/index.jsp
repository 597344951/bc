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
                    :active-text-color="app.colors.menu_act_txt">
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
                        <span slot="label">
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
</script>
<script type="module">
    /**增加标签{menuId:'',name:'',url:'',closable:false,icon:''}**/
    window.addTab = function(target){
        appInstince.addTab(target);
    }
    var title_theme = '#'+('<shiro:principal property="theme"/>' || '20a0ff');
    var appInstince = new Vue({
        el: '#app',
        data: {
            app: {
                sysIcon: 'glyphicon glyphicon-send',
                sysName: '${sysInfo.appname}',
                userId:'<shiro:principal property="userId"/>',
                sysUserName: '<shiro:principal property="username"/>',
                sysUserAvatar: './assets/avatars/develop.gif',
                menuWidth: '230px',
                isCollapse: false, //是否收缩
                colors: {
                    menu_bg: '#eef1f6',
                    menu_txt: '#48576a',
                    menu_act_bg: '#d1dbe5',
                    menu_act_txt: title_theme
                },
                fullscreen:false
            },
            tab_index: '', //tab选中分页index
            openTabDatas: [], //tab分页中的数据
            tabIndex: 2,
            menuDatas: [],
            messages: [{ mid: 1, title: 'test title', time: new Date(), sender: "System", content: "test message", url: 'http://www.baidu.com' }]
            ,changeTheme:{
                visible:false,
                color:title_theme
            }
        },
        mounted(){
            this.loadMenu();
            this.initWS();
            this.loadSetting();
        },
        methods: {
            tabRemove(){
                console.log(arguments)
            },
            //
            initWS() {
                //获取userid
                var userId = '<shiro:principal property="userId"/>'
                var stompClient = null;
                connect();

                function connect() {
                    var socket = new SockJS('/ws');
                    stompClient = Stomp.over(socket);
                    stompClient.connect({}, onConnected, onError);
                }
                function onConnected() {
                    stompClient.subscribe('/notification/' + userId, message => {
                        handleMessage(JSON.parse(message.body));
                    });
                }
                function onError() {
                    appInstince.$message('无法连接到消息服务器.');
                }

                /**
                 * 请在该方法中处理收到的消息
                 */
                function handleMessage(message) {
                    console.log(message);
                }
            },
            loadMenu() {
                var ins = this;
                ajax_json('sys/menu/tree', 'get', null, function (r) {
                    ins.menuDatas = r.data;
                });
            },
            logout() {
                var ins = this;
                $confirm('是否退出?', '是否退出系统登陆?', 'warning', this, function () {
                    ins.saveSetting(false);
                    top.window.location.href = "/logout";
                });
            },
            //true:show,false:hide,其他为 事件
            openHideMenu() {
                this._openHideMenu(!this.app.isCollapse)
                this.saveSetting();
            },
            _openHideMenu(show){
                this.app.isCollapse = show;
                if (!this.app.isCollapse) this.app.menuWidth = '230px';
                else this.app.menuWidth = '65px'
            },
            handleOpen(key, keyPath) {
                //console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                // console.log(key, keyPath);
            },
            //增加标签{menuId:'',name:'',url:''}
            addTab(target) {
				if(target.closable !== false)target.closable = true;
                var have = false;
                this.openTabDatas.forEach((tab, index) => {
                    if (tab.menuId === target.menuId) {
                        this.tab_index = tab.menuId + '';
                        have = true;
                    }
                });
                if (!have) {
                    //判断打开标签数量
                    if(this.openTabDatas.length > 10){
                        toast('打开过多的标签','请关闭一些后继续!','warning',this);
                        return;
                    }
                    //打开新标签
                    this.openTabDatas.push(target);
                    this.tab_index = target.menuId + '';
                }
                this.saveSetting();
            },
            //移除标签
            removeTab(target_index) {
                let ifid = target_index+'_iframe';
                console.debug('close ',ifid);
                try{
                    let callback = $('#'+ifid)[0].contentWindow.onclose;//页面的关闭回调函数
                    if(callback && !callback()){
                        console.debug('不关闭标签');
                        return;
                    }
                }catch(e){}

                let tabs = this.openTabDatas;
                let action_index = this.tab_index;
                this.openTabDatas.forEach((tab, index) => {
                    if (tab.menuId == target_index) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            action_index = nextTab.menuId;
                        }
                    }
                });
                this.tab_index = action_index + '';
                this.openTabDatas = this.openTabDatas.filter(tab => tab.menuId != target_index);
                
                this.saveSetting();
            },
            menuItemClick(obj) {
                console.log('打开' + obj.menuId + ' ' + obj.name)
                this.addTab(obj);
            },
            //消息点击
            message_click(m) {
                var mt = { menuId: m.mid, name: m.title, url: m.url }
                this.addTab(mt);
            },
            //全部消息点击
            messageAllClick(){
                console.log('全部消息点击');
            },
            //加载设置
            loadSetting(){
                let ck = this.getSettingKey();
                let str = getCookie(ck);
                if(!str || JSON.parse(str).openTabDatas.length == 0) {
                    this.addTab({menuId: 0, name: '工作台', url: '/view/bench.jsp',closable:false,icon:'fa fa-briefcase'});
                    return;
                }
                let obj = JSON.parse(str);
                this._openHideMenu(obj.showMenu);
                this.openTabDatas = obj.openTabDatas || [];
                this.tab_index = obj.tab_index || 0;
            },
            /**保存设置**/
            saveSetting(save){
                let ck = this.getSettingKey();
                if(save == false){
                    //clear setting
                    setCookie(ck,'');
                    return;   
                }
                let data = {
                    showMenu: this.app.isCollapse,//
                    openTabDatas:this.openTabDatas,
                    tab_index:this.tab_index
                }
                setCookie(ck,JSON.stringify(data),1);//
            },
            getSettingKey(){
                return 'userSetting'+this.app.userId;
            },
            changeTheme_handle(){
                let color = this.changeTheme.color;
                if(!color)return;
                console.log('change Color to :',this.changeTheme.color);
                var url = 'theme/color-'+encodeURIComponent(this.changeTheme.color.replace('#',''))+'.css';
                this.app.colors.menu_act_txt = color;
                $('#theme-css')[0].href = url;
                this.changeTheme.visible = false;

                //this.app.colors.menu_bg = ColorCalc.gradient(color,36)
            },
            //设置全屏
            setFullscreen(){
                this.app.fullscreen = !this.app.fullscreen;
                if(this.app.fullscreen){
                    fullScreen(this);
                }else{
                    exitFullScreen(this);
                }
            }    
        },
        components:{
            //局部注册组件
           // 'test':(resolve,reject)=>require_comp(resolve,reject,MessageNotice)
        }
    });
 
</script>