<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
    <meta charset="UTF-8">
    <title>党费缴纳收据</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
</head>

<body>
    <div id="app">
        <payment-list :data="partyDue"></payment-list>
    </div>
</body>
<script>
    const app = new Vue({
        el: '#app',
        data: {
            partyDue: {
                title: "党费缴纳收据",
                seriaNum: (() => {
                    let num = ${ due.id }, size = 8
                    let length = ('' + num).length;
                    return Array(size > length ? size - length + 1 || 0 : 0).join(0) + num
                })(),
                time: new Date("${due.paymentTime}"), //时间
                payer: "${due.username}", //付款人
                payee: "${due.organization}", //收款人
                detail: "党费缴纳费用", //收款详情
                amount: ${ due.amount }, //收款金额
                sealInfo: {
                    name: "${due.organization}",
                    type: "收款专用章"
                }
            }
        }
    })
</script>

</html>