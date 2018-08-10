<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>缓存使用信息</title>

<%@include file="/include/head.jsp"%>
 
<%@include file="/include/ueditor.jsp"%>
<%@include file="/include/fullcalendar.jsp"%>

<style>
     
</style>
</head>

<body>
    <div id="app">
        <h1>登录凭据信息缓存</h1>
        <el-table :data="UpAuthentication">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <pre>{{disJson(props.row.primaryPrincipal)}}
                              </pre>
                </template>
            </el-table-column>
            <el-table-column label="账户" prop="username"></el-table-column>
            <el-table-column label="来源" prop="realmNames"></el-table-column>

            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="danger" size="small" @click="rmUpAuthenticationCache(scope.row)">删除缓存</el-button>
                </template>
            </el-table-column>
        </el-table>
        <h1>账户权限信息缓存</h1>
        <el-table :data="authorization">
            <el-table-column type="expand">
                <template slot-scope="props">
                    {{props.row.stringPermissions}}
                </template>
            </el-table-column>
            <el-table-column label="账户" prop="username"></el-table-column>
            <el-table-column label="账户信息" prop="name"></el-table-column>
            <el-table-column label="角色">
                <template slot-scope="scope">
                    <el-tag v-for="role in scope.row.roles">{{role}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="danger" size="small" @click="rmAuthorizationCache(scope.row)">删除缓存</el-button>
                </template>
            </el-table-column>
        </el-table>


        <h1>单账户登录缓存</h1>
        <el-table :data="KickOut">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <pre>{{disJson(props.row.primaryPrincipal)}}
                    </pre>
                </template>
            </el-table-column>
            <el-table-column label="账户" prop="username"></el-table-column>
            <el-table-column label="session id" prop="0"></el-table-column>

            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="danger" size="small" @click="rmKickOutCache(scope.row)">删除缓存</el-button>
                </template>
            </el-table-column>
        </el-table>
        <h1>账户密码重试记录</h1>
        <el-table :data="PasswordRetry">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <pre>{{disJson(props.row.primaryPrincipal)}}
                          </pre>
                </template>
            </el-table-column>
            <el-table-column label="账户" prop="username"></el-table-column>
            <el-table-column label="重试次数" prop="count"></el-table-column>

            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="danger" size="small" @click="rmPasswordRetryCache(scope.row)">删除缓存</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</body>
<script>
    window.appInstince = new Vue({
    el: '#app',
    data: { 
        PasswordRetry:[],
        KickOut:[],
        authorization:[],
        UpAuthentication:[],
        caches:{}
    },
    mounted() {
        this.loadCache();
    },
    computed: {},
    methods: {
        rmAuthorizationCache(dt){
            this.removeCache('authorization',dt.username);
        }, rmUpAuthenticationCache(dt){
            this.removeCache('UpAuthentication',dt.username);
        }, rmKickOutCache(dt){
            this.removeCache('KickOut',dt['0']);
        }, rmPasswordRetryCache(dt){
            this.removeCache('PasswordRetry',dt.username);
        },
        removeCache(cache,item){
            let url = '/cache/delete/'+cache+'/'+item;
            ajax_promise(url,'delete',{}).then(result=>{
                if(result.status){
                    $message(result.msg,'success',this);
                }else{
                    $message(result.msg,'danger',this);
                }
                this.loadCache();
            });
        },
        disJson(js){
          return JSON.stringify(js, null, '  ');
        },
        split(str){
            return str.split('');
        },
        loadCache(){
            ajax_promise('/cache/list','get',{}).then(result=>{
                this.handleCache(result.data);
            });
        },
        handleCache(caches){
            let au = caches.authorization;
            let ay = [];
            for(var it in au){
                var iv = au[it];
                iv.name = it;
                iv.username = this.getUserName(it);
                ay.push(iv);
            }
            this.authorization = ay;
            
            ay = [];
            let up = caches.UpAuthentication;
            for(var it in up){
                var iv = up[it].principals;
                iv.username = it;
                ay.push(iv);
            }
            this.UpAuthentication = ay;

            ay = [];
            let ko = caches.KickOut;
            for(var it in ko){
                var iv = ko[it] ;
                iv.username = it;
                ay.push(iv);
            }
            this.KickOut = ay;

            ay=[];
            let pr = caches.PasswordRetry;
            for(var it in pr){
                var iv = pr[it] ; 
                ay.push({username:it,count:iv});
            }
            this.PasswordRetry = ay;
        },
        getUserName(it){
            var regexp = /username=(\w+)/g
            var ay = regexp.exec(it);
            return ay[1];
        }
    }
})
</script>
</html>