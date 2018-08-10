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
<%@include file="/include/head_notbootstrap.jsp"%>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	body{
		background: url(s2.jpg);
	}
	.bg{
		width: 1080px;
		height: 1900px;
	    background: rgba(255, 255, 255, 0.18);
	    margin:0 auto;
	}
	.header{
		text-align: center;
		padding-top: 20px;
	}
	img{
		display: block;
		border-radius: 50%;
		border:1px solid #999;
		margin: 100px auto;
		width: 700px;
		height: 700px;
	}
	.name{
		color: #fff;
		margin-top: 20px;
		font-size: 46px;
	}
	.main{
		color: #fff;
		margin-top: 30px;
		overflow: hidden;
	}
	.main-left,.main-right{
		width: 50%;
		float: left;
		padding: 0 40px;
		box-sizing:border-box;
	}
	.w3ls-banner-left-info{
	    padding: 1em 0;
    	border-bottom: 1px dashed #fff;
	}
	.w3ls-banner-left-info h4{
	    font-size: 1.2em;
	    margin: 0;
	    font-family: 'Roboto Slab', serif;
	    color: #FFFFFF;
	    text-transform: uppercase;
	    font-weight: 700;
	    letter-spacing: 2px;
	}
	.w3ls-banner-left-info p,.w3ls-banner-left-info p a {
	    font-size: .9em;
	    margin: 1em 0 0 0;
	    color: #cecece;
	    text-decoration: none;
	}
	.main-right h2{
	    color: #FFFFFF;
	    font-size: 1em;
	    font-weight: 600;
	    margin: 0;
	    line-height: 1.5em;
        margin-top: 20px;
    	margin-bottom: 15px;
	}
	.main-right p{
		color: #cecece;
	    font-size: .9em;
	    margin: 1em 0 0 0;
	    line-height: 1.8em;
	}
</style>
</head>
<body>
	<div class="bg" id="app">
		<div class="header">
			<img :src="getPath(userInfo.id)" />
			<p class="name">{{userInfo.name}}</p>
		</div>
		<div class="main">
			<div class="main-left">
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
			<div class="main-right">
				<h2>经历</h2>
				<p>{{userInfo.introduce}}</p>
			</div>
		</div>
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