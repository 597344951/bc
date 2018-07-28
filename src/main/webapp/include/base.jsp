<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.js"></script>
<script src="https://cdn.bootcss.com/jqueryui/1.12.1/jquery-ui.js"></script>

<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>

<script src="https://cdn.bootcss.com/vue/2.5.16/vue.js"></script>
<script src="https://cdn.bootcss.com/vue-router/3.0.1/vue-router.js"></script>
<!--font-awsome-->
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<!-- js 日期处理-->
<script src="https://cdn.bootcss.com/moment.js/2.22.1/moment.js"></script>

<!-- project --> 
<script src="${urls.getForLookupPath('/assets/js/common.js')}"></script>
<script src="${urls.getForLookupPath('/assets/js/vue-common.js')}"></script>

<script src="${urls.getForLookupPath('/assets/js/require.local.js')}"></script>
<script src="${urls.getForLookupPath('/assets/js/ajax_package.js')}"></script>
<!--模块打包后引入-->
<script src="${urls.getForLookupPath('/components/main.js')}" ></script>
<!--模块打包前源码引入
<script src="${urls.getForLookupPath('/components/index.js')}" type="module"></script>-->
<!-- 不支持模块的兼容方案
<script nomodule src="${urls.getForLookupPath('/components/main.js')}"></script>
-->

<!--弹窗-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.js"></script>

<!--系统描述信息-->
<script>
    var sysInfo = {
        sysName: '${sysInfo.appname}',
        userId: '<shiro:principal property="userId"/>',
        orgId: '<shiro:principal property="orgId"/>',
        sysUserName: '<shiro:principal property="username"/>',
        title_theme: ('#' + ('<shiro:principal property="theme"/>' || '20a0ff'))
    }
    window.sysInfo = sysInfo;
</script>
<link rel="shortcut icon" href="/assets/img/logo-icon2.png" />
<link rel="bookmark" href="/assets/img/logo-icon2.png" />