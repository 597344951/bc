<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
	
	String toId = (String)request.getParameter("toId");
	String isParent = (String)request.getParameter("isParent");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>中国共产党党员组织关系介绍信</title>
	<%@include file="/include/head_notbootstrap.jsp"%>
	<style>
	*{
		padding: 0;
		margin: 0;
		font-family: "宋体";
	}
	.box{
		width: 1000px;
		margin:0 auto;
		height: auto;
	}
	.box .contain{
		overflow: auto;
		margin-bottom: 10px;
	}
	.contain1{
		width: 820px;
		height:285px;
		margin:0 auto;
		border:2px solid #222;
	}
	.box>p.p1{
		text-align: center;
		margin: 20px 0;
	}
	.cungen{
		width:50px;
		height: 100%;
		font-size: 30px;
		border-right: 2px solid #222;
		text-align: center;
		float: left;
	}
	.section{
		float: left;
		padding-top: 10px;
		width: 760px;;
		padding-right: 20px;
		box-sizing:border-box;
		font-size: 20px;
	}
	.number{
		text-align: right;
		line-height: 40px;
		font-size: 20px;
	}
	.content p.detail{
		line-height:60px;	
		font-size: 24px;
		padding:0px 0 20px 40px;
	}
	.content p.time{
		text-align: right;
		font-size: 24px;
		padding-right: 50px;
		padding-top: 50px;
	}
	.content .kongge{
		display: inline-block;
		width: 256px;
		height:40px;
		border-bottom: 1px solid #222;
		text-align: center;
	}
	.p2{
		text-align: center;
		font-size: 30px;
	}
	.contain2{
		width: 820px;
		/*height:285px;*/
		margin:0 auto;
		border:2px solid #222;
	}
	.menu{
	    line-height: 36px;
	}
	.list{
		padding:0 20px 10px;
		font-size: 24px;
	}
	.kongge{
		display: inline-block;
		width: 256px;
		height:28px;
		border-bottom: 1px solid #222;
		text-align: center;
	}
	.name{
		width: 380px;
		border-bottom: 1px solid #222;
		overflow: hidden;
		display: inline-block;
		text-align: center;
		line-height: 36px;
	}
	.age,.nation,.card,.kongge-1,.year,.month{
		display: inline-block;
		height:28px;
		border-bottom: 1px solid #222;
		text-align: center;
	}
	.age{
		width: 60px;
		text-align: center;
	}
	.nation{
		width: 130px;
	}
	.card{
		width: 330px;
	}
	.kongge-1{
		width: 350px;
	}
	.year,.month{
		width:80px;
	}
	.validity{
		margin: 10px;
	}
	.yinzhang{
		text-align: right;
		padding-right: 2em;
		margin: 10px;
	}
	.date{
		text-align: right;
		margin: 10px;
	}
	.touch,.contact{
		margin: 15px 0px;
		overflow: hidden;
	}
	.contact span{
		display: inline-block;
		width:33%;
		float: left;
	}
	.zhang{
		float: right;
		width: 130px;
    	height: 130px;
		border-radius: 50%;
		margin-top: -45px;
    	margin-left: -180px;
	}
	.contain3{
		width: 820px;
		height:285px;
		margin:0 auto;
		border:2px solid #222;
		font-size: 24px;
		padding:10px;
		box-sizing:border-box;
	}
	.footer{
		width: 820px;
		font-size: 18px;
		text-align: center;
		margin: 5px auto;
	}
	.nbsp{
		width: 2em;
		display: inline-block;
	}
	</style>
</head>
<body>
	
	<div id="app" class="box">
		<p  class="p1">《中国共产党党员组织关系介绍信》</p>
		<div class="contain contain1">
			<p class="cungen">党员介绍信存根</p>
			<div class="section">
				<p class="number">第 <span> {{printInfo.turnOutOrgPartyUser.id}} </span> 号</p>
				<div class="content">
					<p class="detail">
						<span class="nbsp"></span><span class="kongge">{{printInfo.partyUserInfo.name}}</span>同志系中共（{{printInfo.partyUserInfo.type == 1 ? '正式' : '预备'}}）党员，  组织关系由<span class="kongge">{{printInfo.organizationRelation.orgInfoName}}</span>转到<span class="kongge">{{printInfo.turnOutOrgPartyUser.orgInfoName == null || printInfo.turnOutOrgPartyUser.orgInfoName == '' ? printInfo.turnOutOrgPartyUser.otherOrgName : printInfo.turnOutOrgPartyUser.orgInfoName}}</span>。</p>
					<p class="time">{{printInfo.turnOutOrgPartyUser.nowTime}}</p>
				</div>
			</div>
		</div>
		<p class="p2">中国共产党党员组织关系介绍信 </p>
		<div class="contain contain2">
			<div class="list">
				<p class="number">第 <span> {{printInfo.turnOutOrgPartyUser.id}} </span> 号</p>
				<div class="menu">
					<p class="name">{{printInfo.turnOutOrgPartyUser.orgInfoName == null || printInfo.turnOutOrgPartyUser.orgInfoName == '' ? printInfo.turnOutOrgPartyUser.otherOrgName : printInfo.turnOutOrgPartyUser.orgInfoName}}</p><span>:</span>
					<p class="resume"><span class="nbsp"></span><span class="kongge">{{printInfo.partyUserInfo.name}}</span>同志（{{printInfo.partyUserInfo.sex}}），<span class="age">{{printInfo.partyUserInfo.age}}</span>岁，<span class="nation">{{printInfo.partyUserInfo.nation}}</span>，系中共（{{printInfo.partyUserInfo.type == 1 ? '正式' : '预备'}}）党员，身份证号码<span class="card">{{printInfo.partyUserInfo.idCard}}</span>,由<span class="kongge-1">{{printInfo.organizationRelation.orgInfoName}}</span>去<span class="kongge-1">{{printInfo.turnOutOrgPartyUser.orgInfoName == null || printInfo.turnOutOrgPartyUser.orgInfoName == '' ? printInfo.turnOutOrgPartyUser.otherOrgName : printInfo.turnOutOrgPartyUser.orgInfoName}}</span>，请转接组织关系。该同志党费已交到<span class="year">{{printInfo.partyMembershipDue.payDateYear}}</span>年<span class="month">{{printInfo.partyMembershipDue.payDateMonth}}</span>月。</p>
				</div>
				<p class="validity">（有效期  30 天）</p>
				<p class="yinzhang">（盖章）
					<span class="zhang">
						<seal-viewer :data="yzConditionFir"></seal-viewer>
					</span>
				</p>
				<p class="date">{{printInfo.turnOutOrgPartyUser.nowTime}}</p>
				<p class="touch">党员联系电话或其他联系方式：{{printInfo.partyUserInfo.mobilePhone}}</p>
				<p class="touch">党员原所在基层党委通讯地址：{{printInfo.organizationRelation.orgInfoCommitteeProvince}}{{printInfo.organizationRelation.orgInfoCommitteeCity}}{{printInfo.organizationRelation.orgInfoCommitteeArea}}{{printInfo.organizationRelation.orgInfoCommitteeCetail}}</p>
				<div v-if="false" class="contact">
					<span>联系电话：15879413256</span>
					<span>传真：15879413256</span>
					<span>邮编：589641</span>
				</div>
			</div>
		</div>
		<p class="p2">中国共产党党员组织关系介绍信回执联</p>
		<div class="contain contain3">
			<p class="number">第 <span> {{printInfo.turnOutOrgPartyUser.id}} </span> 号</p>
			<p class="name">{{printInfo.organizationRelation.orgInfoName}}</p><span>:</span>
			<p class="resume">
				<span class="nbsp"></span><span class="kongge">{{printInfo.partyUserInfo.name}}</span>同志的党员组织关系已转达我处，特此回复。
			</p>
			<p class="yinzhang">（盖章）
				<span class="zhang">
					<seal-viewer :data="yzConditionSec"></seal-viewer>
				</span>
			</p>
			<p class="date">{{printInfo.turnOutOrgPartyUser.nowTime}}</p>
			<div v-if="false" class="contact">
					<span>经办人：吴晓伟</span>
					<span>联系电话：1354873825</span>
					
				</div>
		</div>
		<p class="footer">
			注：回执联由接收党员组织关系的基层党委在接收党员后一个月内邮寄或传真至党员原所在基层党委。
		</p>
	</div>
</body>

<script>

	var appInstince = new Vue({
		el: '#app',
		data: {
			yzConditionFir: {
				companyName: '测试组织超长名字了解一下',
				typeName: "党员转出",
				radius: 90
			},
			yzConditionSec: {
				companyName: '南无阿弥托福',
				typeName: "党员转入",
				radius: 90
			},
			toId: '<%=toId%>',
			isParent: '<%=isParent%>',
			printInfo: {
				turnOutOrgPartyUser: {
					id: null
				},
				partyUserInfo: {
					name: null
				},
				organizationRelation: {

				},
				partyMembershipDue: {

				}
			}
		},
		created: function () {
			
		},
		mounted: function () {
			this.printIntroduce();
		},
		methods: {
			printIntroduce() {
				let obj = this;
				var url = "/toou/user/printIntroduce";
				var t = {
					toId: obj.toId,
					isParent: obj.isParent
				}
				$.post(url, t, function(data, status){
					if (data.code == 200 && data.data != undefined && data.data != null) {
						obj.printInfo = data.data;
						obj.yzConditionFir.companyName = obj.printInfo.organizationRelation.orgInfoName;
						obj.yzConditionSec.companyName = obj.printInfo.turnOutOrgPartyUser.orgInfoName == null || 
							obj.printInfo.turnOutOrgPartyUser.orgInfoName == '' ? 
							obj.printInfo.turnOutOrgPartyUser.otherOrgName : 
							obj.printInfo.turnOutOrgPartyUser.orgInfoName;
					}
				})
			}
		}
	})
</script>
</html>