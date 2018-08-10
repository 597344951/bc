<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人履历</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link href="css/font-awesome.css" rel="stylesheet"> 
<%@include file="/include/head_notbootstrap.jsp"%>
</head>
<body>
	<div id="app">
		<!-- banner -->
		<div class="banner" id="home">
			
			<div class="container">
				<div class="w3l-banner-grids">
					<div class="col-md-8 w3ls-banner-right">
						<div class="banner-right-img">
							<img :src="getPath(userInfo.id)" width="100" />
						</div>
						<div class="banner-right-info">
							<h2>经历</h2>
							<p>{{userInfo.introduce}}</p>
						</div>
						<div class="clearfix"> </div>
						
						
					</div>
					<div class="col-md-4 w3ls-banner-left">
						<div class="w3ls-banner-left-info">
							<h4>姓名</h4>
							<p>{{userInfo.name}}</p>
						</div>
						<div class="w3ls-banner-left-info">
							<h4>任职</h4>
							<p>
								{{
									(userInfo.orgInfoName == null || userInfo.orgInfoName == '') ? 
									'没有加入组织' : userInfo.orgInfoName + 
									((userInfo.orgDutyName == null || userInfo.orgDutyName == '') ?
									'' : ' 任职 ' + userInfo.orgDutyName)
								}}
							</p>
						</div>
						<div class="w3ls-banner-left-info">
							<h4>手机号码</h4>
							<p>{{userInfo.mobilePhone}}</p>
						</div>
						<div class="w3ls-banner-left-info">
							<h4>毕业学校</h4>
							<p>{{userInfo.graduationSchool}}</p>
						</div>
						<div class="w3ls-banner-left-info">
							<h4>工作单位</h4>
							<p>{{userInfo.workUnit}}</p>
						</div>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
		</div>
		<!-- //banner -->
	</div>

</body>	

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			id: <%=id%>,
			userInfo: null
		},
		created: function () {
			
		},
		mounted:function () {
			this.getUserInfo();
		},
		methods: {
			getUserInfo() {
				var obj = this;
				var url = "/party/user/queryPartyUserInfos";
				var t = {
					pageNum: 1,
					pageSize: 999999,
					id: obj.id
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							if (data.data.list.length == 1) {
								obj.userInfo = data.data.list[0];
							}
						} 
					} 

				})
			},
			getPath(id) {	/* 得到党员用户id并返回请求路径 */
				/*给予一个随机数，保证每次请求的参数都不一样，防止从缓存里取值，用于证件照的更新*/
				return "/party/user/getPartyUserInfoIdPhoto?partyId="+id + "&t=" + Math.random();
			},
		}
	});
</script>

</html>