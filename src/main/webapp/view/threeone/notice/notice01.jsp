<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
    <meta charset="UTF-8">
    <title>通知 - 三会一课</title>
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        html,body {
           width:100%;
           height:100%; 
        }
        .content{
            width: 100%;
            height:100%;
            background: url(/view/threeone/notice/bg1.png) no-repeat;
            background-size: 100%;
        }
        .header{
            width: 100%;
           text-align: center; 
           font-size: 3.5em;
           color:#ef0000;
           text-shadow: 2px 3px 2px #310000;
        }
        .box{
            width: 80%;
            margin: 0 auto;
            font-size: 2em;
            background-color: rgba(255,255,255,.5);
            padding:0px 1em 1em;
            border-radius: 6px;
            color: #bb0000;
        }
        .contain{
            text-indent: 2em;
        }
        .type,.name,.place,.schedule{
            font-weight: bold;
            margin:1em;
            text-align: center;
        }
        .type{
            padding-top: 20px;
        }
        .contain{
            font-size: 0.8em;
        }
    </style>
</head>

<body>
    <div class="content">
        <h1 class="header">通知</h1>
        <div class="box">
            <p class="type">${schedule.typeName}</p>
            <p class="name">${schedule.name}</p>
            <p class="place">${schedule.place}</p>
            <p class="schedule">${schedule.startTimeString} - ${schedule.endTimeString}</p>
            <p class="contain">${schedule.description}</p>
        </div>
    </div>
</body>
</html>