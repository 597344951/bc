<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${eventPlan.title}</title>

<%@include file="/include/head_notbootstrap.jsp"%>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	html,body{
		height: 100%;
		width: 100%;
	}
	h1{
		font-size: 22px;
		background-color: darkseagreen;;
		color:#fff;
		padding: 5px;
		padding-left: 20px;

	}
	.plan-box{
		width: 95%;
		margin:0 auto;
		box-sizing:border-box;
	}
	.content-box{
		margin: 20px 0;
		font-size: 14px;
		min-width: 720px;
		padding:5px 20px;
		border:1px solid #ddd;
		box-shadow:inset 0px 0px 20px 2px #ddd;
	}
	.content-list{
		margin: 10px 0;
	}
	.title{
		font-weight: bold;
		width: 6%;
		float: left;
		min-width: 72px;
	}
	.plan-content{
		overflow: auto;
	}
	.plan-content-box{
		float: left;
		line-height: 26px;
		width: 90%;
	}
	/*****************cost-box*****************/
	.cost-txt{
		background-color: darkkhaki;
		padding: 5px 20px;
		border-left: 10px solid olivedrab;
		font-weight: bold;
		color: #fff;

	}
	.table-box{
		margin: 20px;
	}
	.cost-box table{
		table-layout: fixed;
		border-collapse: collapse;
		border:1px solid #bbb;
		width: 100%;
	}
	.cost-box table th{
		background-color: #ececec;
	}
	.cost-box table th,.cost-box table td{
		text-align: center;
		padding: 8px;
		font-size: 14px;
	}
	/*****************vote*****************/
	.vote{
		background-color: darkorange;
		padding: 5px 20px;
		border-left: 10px solid #c32d2d;
		font-weight: bold;
		color: #fff;
	}
	.vote-btn button{
		border:none;
		padding:10px 20px;
		font-size: 16px;
		outline: none;
		border-radius: 4px;
		cursor: pointer;
		color: #fff;
	}
	.vote-btn button.pass{
		background-color: #2cc32c;
	}
	.vote-btn button.pass:hover{
		background-color: #2ed42e;
	}
	.vote-btn button.no-pass{
		background-color: #e80000;
	}
	.vote-btn button.no-pass:hover{
		background-color: #f72525;
	}
	.vote-btn button.result-before{
		background-color: #ccc;
		cursor: not-allowed;
	}
	.suggest{
		padding: 15px;
	}
	.vote-result{
		border:1px solid #ddd;
		padding: 10px;
		margin: 10px;
		box-shadow: inset 0px 0px 20px 2px #ddd;
	}
	.result-after{
		background-color: #2497e8;
		cursor: pointer;
	}
	.result-after:hover{
		background-color: #3aa3ec;
	}
	.result-txt{
		font-weight: bold;
	}
	.vote-pass,.vote-nopass{
		overflow: auto;	
		margin: 20px 0;
	}
	.vote-pass span,.vote-nopass span,.vote-rate{
		float: left;
	}
	.vote-pass span,.vote-nopass span{
		width: 80px;
		text-align: center;
	}
	.vote-rate{
		width: 400px;
		height: 28px;	
		border:1px solid #00a3f1;
		padding: 1px;

	}
	.vote-rate div{
		background-color: #00a3f1;
		height: 28px;
	}
	.your-result{
		font-weight: bold;
		font-size: 18px;
	}
	</style>
</head>

<body>

    <h1>活动计划</h1>
    <div class="plan-box">
        <div class="content-box">
            <div class="content">
                <div class="content-list plan-title">
                    <span class="title">活动名称：</span>
                    <span>${eventPlan.title}</span>
                </div>
                <div class="content-list plan-content">
                    <span class="title">活动策划:</span>
                    <div class="plan-content-box">
                        ${eventPlan.content}
                    </div>
                </div>
                <div class="content-list">
                    <span class="title">开始时间：</span>
                    <span>
                        <fmt:formatDate value="${eventPlan.stime}" pattern="yyyy-MM-dd" />
                    </span>
                </div>
                <div class="content-list">
                    <span class="title">结束时间：</span>
                    <span>
                        <fmt:formatDate value="${eventPlan.etime}" pattern="yyyy-MM-dd" /> </span>
                </div>
                <div class="content-list">
                    <span class="title">计划状态</span>
                    <span>${eventPlan.planStatus.name}</span>
                </div>
            </div>
        </div>
        <div class="cost-box">
            <p class="cost-txt">费用计划</p>
            <div class="table-box">
                <table border="1" cellspacing='0' cellpadding='0'>
                    <thead>
                        <tr>
                            <th>费用名称</th>
                            <th>费用类型</th>
                            <th>费用值</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forTokens items="" delims="," var="name">
                            <c:out value="${name}" />
                            <p>
                        </c:forTokens>
                        <c:forEach items="${costPlans}" var="plan">
                            <tr>
                                <td>
                                    <c:out value="${plan.name}" />
                                </td>
                                <td>
                                    <c:out value="${plan.costTypeInfo.name}" />
                                </td>
                                <td>
                                    <c:out value="${plan.value}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="app" v-cloak>
            <div class="vote-box">
                <c:if test="${vitingItem == null}">
                    <div>
                        <p class="vote">投票</p>
                        <p class="suggest">您对该计划的建议是：
                            <span class="your-result"></span>
                        </p>
                        <div class="vote-btn">
                            <el-button type="success" @click="pass(1,'${eventPlan.eventPlanId}')">赞成</el-button>
                            <el-button type="danger" @click="pass(-1,'${eventPlan.eventPlanId}')">反对</el-button>
                        </div>
                    </div>
                </c:if>
                <c:if test="${vitingItem != null}">
                    <p class="vote">投票</p>
                    <p class="suggest">您对该计划的建议是：
                        <span class="your-result">
                            ${vitingItem.yesOrNo=='1'?'赞成':'反对'}
                        </span>
                    </p>
                    <div class="vote-result">
                        <p class="result-txt">投票结果:</p>
                        <div class="vote-pass">
                            <c:set var="yp" value="${votingResult.yesCount*100/votingResult.total}" />
                            <span>赞成</span>
                            <div class="vote-rate">
                                <div class="vote-pass-rate" style="width:${yp}%;"></div>
                            </div>
                            <span>${votingResult.yesCount}票</span>
                            <span class="vote-pass-percent">
                                <fmt:formatNumber type="number" value="${yp}" pattern="0.00" maxFractionDigits="0" />%</span>
                        </div>
                        <div class="vote-nopass">
                            <c:set var="np" value="${votingResult.noCount*100/votingResult.total}" />
                            <span>反对</span>
                            <div class="vote-rate">
                                <div class="vote-nopass-rate" style="width:${np}%;"></div>
                            </div>
                            <span>${votingResult.noCount}票</span>
                            <span class="vote-nopass-percent">
                                <fmt:formatNumber type="number" value="${np}" pattern="0.00" maxFractionDigits="0" />%</span>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
<script>
    window.appInstince = new Vue({
        el: "#app",
        data: {},
        methods:{
            pass(type,eventPlanId){
                let url = '/event/voting';
                let obj = {eventPlanId:eventPlanId,yesOrNo:type};
                ajax_json_promise(url,'post',obj).then(result=>{
                    $message('投票成功','success',this);
                    //刷新页面
                    window.location.reload();
                })
            }
        }
    });
</script>
</html>