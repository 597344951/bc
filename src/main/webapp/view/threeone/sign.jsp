<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
    <title>会议签到</title>
    <style>
        .sign-box {
            text-align: center;
            zoom: 3;
            position: absolute;
            top: 50%;
            height: 400px;
            margin-top: -200px;
            width: 100%;
        }

        .button {
            display: inline-block;
            line-height: 1;
            white-space: nowrap;
            cursor: pointer;
            background: #fff;
            border: 1px solid #dcdfe6;
            border-color: #dcdfe6;
            color: #606266;
            -webkit-appearance: none;
            text-align: center;
            box-sizing: border-box;
            outline: none;
            margin: 0;
            transition: .1s;
            font-weight: 500;
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
            padding: 12px 20px;
            font-size: 14px;
            border-radius: 4px;
        }
        .button:hover {
            color: #fff;
            background-color: #b3d4f5;
            border-color: #b3d4f5;
        }
        .primary {
            color: #fff;
            background-color: #409eff;
            border-color: #409eff;
        }
        .disabled {
            background-color: #fff;
            border-color: #ebeef5;
            color: #c0c4cc;
        }
    </style>
</head>

<body>
    <div class="sign-box">
        <c:if test="${signInfo != null}">
            <div>
                <h3 style="margin-bottom: 50px;">${signInfo.name}</h3>
                <c:if test="${signInfo.isParticipate == 0}">
                    <a href="/threeone/participant/${signInfo.id}/${signInfo.participantId}" class="button primary">签到</a>
                </c:if>
                <c:if test="${signInfo.isParticipate != 0}">
                    <button disabled="disabled" class="button disabled">已签到</button>
                </c:if>
            </div>
        </c:if>
        <c:if test="${signInfo == null}">
            <div>
                <p>没有可用签到</p>
                <p>...</p>
            </div>
        </c:if>
    </div>
</body>

</html>
