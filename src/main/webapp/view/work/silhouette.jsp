<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>活动剪影</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <%@include file="/include/bootstrap.jsp" %>
    <style>
        .image-box {
            width: 200px;
            height: 250px;
            border-radius: 5px;
            padding: 3px;
            margin: 10px;
            background-color: ghostwhite;
            float: left;
        }
        .image {
            width: 100%;
            height: 175px;
        }
        .bottom {
            padding: 5px 10px;
        }
        .bottom p {
            margin: 0px;
        }
        .show-image {
            height: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div id="app">
    <el-container>
        <el-header style="height: 10%">
            <h3>活动剪影</h3>
        </el-header>
        <el-mian>
            <div class="image-box" @click="more">
                <img src="http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w" class="image">
                <div class="bottom">
                    <p><strong>活 动：</strong>植树节植树活动</p>
                    <p><strong>作 者：</strong>宣传委员</p>
                    <p><strong>时 间：</strong>2018-03-12</p>
                </div>
            </div>
            <div class="image-box" @click="more">
                <img src="http://t-1.tuzhan.com/40c8c2c6e5ba/c-1/l/2012/12/08/04/6cf74110f373459a91c1c0cac277e1f7.jpg" class="image">
                <div class="bottom">
                    <p><strong>活 动：</strong>植树节植树活动</p>
                    <p><strong>作 者：</strong>宣传委员</p>
                    <p><strong>时 间：</strong>2018-03-12</p>
                </div>
            </div>
            <div class="image-box" @click="more">
                <img src="http://simg.dahe.cn/2016/6/1/aede574940536904.jpg" class="image">
                <div class="bottom">
                    <p><strong>活 动：</strong>植树节植树活动</p>
                    <p><strong>作 者：</strong>宣传委员</p>
                    <p><strong>时 间：</strong>2018-03-12</p>
                </div>
            </div>
        </el-mian>

    </el-container>
    <el-dialog title="活动剪影" :visible.sync="showImages.show" width="80%">
        <el-carousel :interval="4000" type="card" height="350px">
            <el-carousel-item v-for="l in showImages.list" :key="l.id">
                <img :src="l.src" class="show-image">
            </el-carousel-item>
        </el-carousel>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="showImages.show = false">确 定</el-button>
        </span>
    </el-dialog>
</div>
<script>
    const app = new Vue({
        el: '#app',
        data: {
            showImages: {
                show: false,
                list:[{
                    id: 1,
                    src: 'http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w'
                },{
                    id: 2,
                    src: 'http://t-1.tuzhan.com/40c8c2c6e5ba/c-1/l/2012/12/08/04/6cf74110f373459a91c1c0cac277e1f7.jpg'
                },{
                    id: 3,
                    src: 'http://simg.dahe.cn/2016/6/1/aede574940536904.jpg'
                },{
                    id: 4,
                    src: 'http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w'
                },{
                    id: 4,
                    src: 'http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w'
                },{
                    id: 4,
                    src: 'http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w'
                },{
                    id: 4,
                    src: 'http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w'
                },{
                    id: 4,
                    src: 'http://mpic.tiankong.com/782/4e8/7824e839adc10c0ad4f82a5f24cbf5c8/640.jpg@!670w'
                }]
            }
        },
        methods: {
            more() {
                this.showImages.show = true
            }
        }
    })
</script>
</body>
</html>
