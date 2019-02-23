<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>党费缴纳</title>
    <%@include file="/include/head_notbootstrap.jsp"%>
    <style>
        body {
            font-size: 12px;
        }
        #top_style {
            height: 100%;
            padding: 10px;
        }
        #head_style, #foot_style {
            width: 75%;
            margin: 0px auto;
        }
        #user_style {
            border: 1px solid #e0e0e0;
            background-color: #f5f5f5;
            height: 140px;
        }
        #user_img_style {
            float: left;
        }
        #user_img_img_style {
            margin: 10px;
            height: 120px;
            box-shadow: 0 8px 15px rgba(0,0,0,0.55);
        }
        #pay-top-style {
            margin-top: 10px;
            width: 250px;
        }
        #wechat_pay {
            background-color: #09bb07;
            width: 140px;
            height: 35px;
            text-align: center;
            line-height: 35px;
            color: #e0e0e0;
            font-size: 18px;
            border-radius: 5px;
        }
        #wechat_pay:hover {
            cursor:pointer;
            border:#aaa 1px dashed;
            box-shadow:0 0 8px rgba(81, 81,81,0.8);
        }
        .font_red {
            color: red;
        }
        .font_bold {
            font-weight: bold;
        }
        .padding-10px {
            padding: 10px;
        }
        .margin-buttom-10px {
            margin-bottom: 10px;
        }
        .title-style {
            width: 150px;
            text-align: left;
            float: left;
            padding-top: 6px;
            padding-right: 20px;
        }
        .pay-font-style {
            text-align: right;
        }
        div.el-input.el-input--small {
            width: 220px;
        }
        #pay_dialog_top_layer {
            padding: 0 20px;
            text-align: center;
        }
        #wechat_pay_qrcode {
            text-align: center;
        }
    </style>
</head>
<body>
    <div id="app">
        <div id="top_style">
            <el-tabs @tab-click="init_tab_info" v-model="common.tabs_num">
                <el-tab-pane label="微信缴纳党费" name="first">
                    <div id="head_style">
                        <div id="user_style" class="margin-buttom-10px">
                            <div id="user_img_style">
                                <img id="user_img_img_style" :src="common.user_info.idPhoto" />
                            </div>
                            <div class="padding-10px font_bold">
                                <p style="float: left; margin-right: 30px;" class="margin-buttom-10px">姓名：{{common.user_info.name}}</p>
                                <p class="margin-buttom-10px">
                                    缴费方向：
                                    <span class="font_red">{{ol_pay.pay_who ? '代缴' : '自缴'}}</span>
                                </p>
                                <p class="margin-buttom-10px">身份证号码：{{common.user_info.idCard}}</p>
                                <p class="margin-buttom-10px">性别：{{common.user_info.sex}}</p>
                                <p class="margin-buttom-10px">所在党委：{{common.user_info.orgInfoName}}</p>
                                <p style="float: left; margin-right: 30px;" class="margin-buttom-10px">党员性质：{{common.user_info.typeName}}</p>
                                <p class="margin-buttom-10px">
                                    党员状态：{{common.user_info.statusName}}
                                </p>
                            </div>
                        </div>
                        <div class="padding-10px font_bold">
                            <div style="padding-top: 0px" class="title-style">缴费方向</div>
                            <el-switch
                                @change="reset_user_info()"
                                v-model="ol_pay.pay_who"
                                active-color="red"
                                inactive-color="green"
                                active-text="代缴"
                                inactive-text="自缴">
                            </el-switch>
                        </div>
                        <div v-if="ol_pay.pay_who" class="padding-10px font_bold">
                            <div class="title-style">被代缴人身份证号码</div>
                            <el-input
                                @change="get_who_info()"
                                placeholder="请输入"
                                v-model="ol_pay.pay_who_idCard"
                                clearable
                                size="small">
                            </el-input>
                        </div>
                        <div v-if="common.user_info.statusName == '流动党员'" class="padding-10px font_bold">
                            <div class="title-style">流动党员税后月工资收入/元</div>
                            <el-input
                                @change="getPayTotalMoney()"
                                placeholder="请输入"
                                v-model="common.user_info.income"
                                clearable
                                size="small">
                            </el-input>
                        </div>
                        <div class="padding-10px font_bold">
                            <div class="title-style">缴纳起始月</div>
                            <el-date-picker
                                type="month"
                                size="small"
                                v-model="ol_pay.monthly"
                                placeholder="选择月">
                            </el-date-picker>
                        </div>
                        <div class="padding-10px font_bold">
                            <div class="title-style">缴纳月份</div>
                            <el-radio-group @change="getPayTotalMoney()" v-model="ol_pay.month_num" size="small">
                                <el-radio :label="1" border>一个月</el-radio>
                                <el-radio :label="3" border>三个月</el-radio>
                                <el-radio :label="6" border>六个月</el-radio>
                            </el-radio-group>
                        </div>
                        <div class="padding-10px font_bold">
                            <div style="padding-top: 0px" class="title-style">收款账户信息</div>
                            <span class="font_red font_bold">广州卓凌通讯技术有限公司杭州分公司</span>
                        </div>
                    </div>
                    <hr />
                    <div id="foot_style">
                        <div id="pay-top-style">
                            <div class="pay-font-style margin-buttom-10px">
                                税后月工资收入（元）
                                <div style="width: 50px; float: right;">
                                    <span class="font_red font_bold">￥{{common.user_info.income}}</span>
                                </div>
                            </div>
                            <div class="pay-font-style margin-buttom-10px">
                                您要缴纳的月份（个月）
                                <div style="width: 50px; float: right;">
                                    <span class="font_red font_bold">{{ol_pay.month_num}}/个月</span>
                                </div>
                            </div>
                            <div class="margin-buttom-10px">
                                <div class="font_bold" style="float: left; padding-left: 20px;">x</div>
                                <div class="pay-font-style">
                                    您的缴纳比例（百分比）
                                    <div style="width: 50px; float: right;">
                                        <span class="font_red font_bold">{{common.user_info.partyProportion}}%</span>
                                    </div>
                                </div>
                            </div>
                            <hr />
                            <div class="pay-font-style margin-buttom-10px">
                                共需缴纳（元）
                                <div style="width: 50px; float: right;">
                                    <span class="font_red font_bold">￥{{ol_pay.pay_money}}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr />
                    <div id="foot_style">
                        <div style="margin-top: 10px;">
                            <el-checkbox v-model="ol_pay.agree">
                                我同意
                            </el-checkbox>
                            <span class="font_red font_bold">
                                <a target="_blank" href="letter_of_authorization.html">
                                    《新智慧党建平台线上支付须知》
                                </a>
                            </span>
                            条例
                        </div>
                        <div style="margin-top: 10px; display: false;">
                            <div @click="open_pay_dialog()" id="wechat_pay">
                                微&nbsp;信&nbsp;支&nbsp;付
                            </div>
                        </div>
                    </div>
                </el-tab-pane>


                <el-tab-pane label="现金缴纳党费记录填写" name="second">
                    <div id="head_style">
                        <div id="user_style" class="margin-buttom-10px">
                            <div id="user_img_style">
                                <img id="user_img_img_style" :src="common.user_info.idPhoto" />
                            </div>
                            <div class="padding-10px font_bold">
                                <p class="margin-buttom-10px">姓名：{{common.user_info.name}}</p>
                                <p class="margin-buttom-10px">身份证号码：{{common.user_info.idCard}}</p>
                                <p class="margin-buttom-10px">性别：{{common.user_info.sex}}</p>
                                <p class="margin-buttom-10px">所在党委：{{common.user_info.orgInfoName}}</p>
                                <p style="float: left; margin-right: 30px;" class="margin-buttom-10px">党员性质：{{common.user_info.typeName}}</p>
                                <p class="margin-buttom-10px">
                                    党员状态：
                                    {{common.user_info.statusName}}
                                </p>
                            </div>
                        </div>
                        <div class="padding-10px font_bold">
                            <div class="title-style">缴纳时间</div>
                            <el-date-picker
                                type="date"
                                size="small"
                                v-model="offl_pay.pay_date"
                                placeholder="选择缴纳时间">
                            </el-date-picker>
                        </div>
                        <div class="padding-10px font_bold">
                            <div class="title-style">缴纳起始月</div>
                            <el-date-picker
                                type="month"
                                size="small"
                                v-model="offl_pay.monthly"
                                placeholder="选择月">
                            </el-date-picker>
                        </div>
                        <div class="padding-10px font_bold">
                            <div class="title-style">缴纳月份</div>
                            <el-radio-group v-model="offl_pay.month_num" size="small">
                                <el-radio :label="1" border>一个月</el-radio>
                                <el-radio :label="3" border>三个月</el-radio>
                                <el-radio :label="6" border>六个月</el-radio>
                            </el-radio-group>
                        </div>
                        <div class="padding-10px font_bold">
                            <div class="title-style">共缴纳（元）</div>
                            <el-input
                                placeholder="请输入"
                                v-model="offl_pay.pay_money"
                                clearable
                                size="small">
                            </el-input>
                        </div>
                        <div class="padding-10px font_bold">
                            <el-button @click="subm_record()" type="primary" size="small">提交记录</el-button>
                        </div>
                    </div>
                </el-tab-pane>

                <el-tab-pane label="特殊党费缴纳" name="third">
                        <div id="head_style">
                            <div id="user_style" class="margin-buttom-10px">
                                <div id="user_img_style">
                                    <img id="user_img_img_style" :src="common.user_info.idPhoto" />
                                </div>
                                <div class="padding-10px font_bold">
                                    <p class="margin-buttom-10px">姓名：{{common.user_info.name}}</p>
                                    <p class="margin-buttom-10px">身份证号码：{{common.user_info.idCard}}</p>
                                    <p class="margin-buttom-10px">性别：{{common.user_info.sex}}</p>
                                    <p class="margin-buttom-10px">所在党委：{{common.user_info.orgInfoName}}</p>
                                    <p style="float: left; margin-right: 30px;" class="margin-buttom-10px">党员性质：{{common.user_info.typeName}}</p>
                                    <p class="margin-buttom-10px">
                                        党员状态：
                                        {{common.user_info.statusName}}
                                    </p>
                                </div>
                            </div>
                            <div v-if="false" class="padding-10px font_bold">
                                <div style="padding-top: 0px" class="title-style">本次发起主题</div>
                                <span class="font_red font_bold">关爱山区贫困家庭</span>
                            </div>
                            <div class="padding-10px font_bold">
                                <div class="title-style">自愿缴纳的金额（元）</div>
                                <el-input
                                    placeholder="请输入"
                                    v-model="special_pay.pay_money"
                                    clearable
                                    size="small">
                                </el-input>
                            </div>
                        </div>
                        <hr />
                        <div id="foot_style">
                            <div style="margin-top: 10px;">
                                <el-checkbox v-model="special_pay.agree">
                                    我同意
                                    <span class="font_red font_bold">《微信支付须知》</span>
                                    和
                                    <span class="font_red font_bold">《新智慧党建平台线上支付须知》</span>
                                    条例
                                </el-checkbox>
                            </div>
                            <div disabled="false" style="margin-top: 10px; display: false;">
                                <div id="wechat_pay">
                                    微&nbsp;信&nbsp;支&nbsp;付
                                </div>
                            </div>
                        </div>
                </el-tab-pane>

                <el-tab-pane label="我的缴纳记录" name="four">
                    <el-table :data="record_pay.pay_record" stripe size="small" style="width: 100%">
                        <el-table-column prop="name" label="姓名"></el-table-column>
                        <el-table-column prop="date" label="缴纳日期"></el-table-column>
                        <el-table-column prop="pay_date" label="实缴日期"></el-table-column>
                        <el-table-column prop="money" label="缴纳金额"></el-table-column>
                        <el-table-column prop="pay_money" label="实缴金额"></el-table-column>
                        <el-table-column prop="pay_month" label="缴纳期数"></el-table-column>
                        <el-table-column prop="pay_monthly" label="缴纳期数"></el-table-column>
                    </el-table>
                </el-tab-pane>

                <el-tab-pane label="我的现金缴纳填报记录" name="five">
                    <el-table :data="off_line_pay.pay_record" stripe size="small" style="width: 100%">
                        <el-table-column prop="name" label="姓名"></el-table-column>
                        <el-table-column prop="orgInfoName" label="所在组织"></el-table-column>
                        <el-table-column prop="payDate" label="缴纳日期"></el-table-column>
                        <el-table-column prop="payMonthly" label="缴纳党费起始月"></el-table-column>
                        <el-table-column label="连续缴纳月">
                            <template slot-scope="scope">
                                <span>{{scope.row.monthNum}}月</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="缴纳金额">
                            <template slot-scope="scope">
                                <span>￥{{scope.row.payMoney}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="审核是否通过">
                            <template slot-scope="scope">
                                <span>{{scope.row.isPass == 1 ? '通过' : scope.row.isPass == 0 ? '审核中' : '不通过'}}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </div>


        <el-dialog @close="reset_wechat_pay_dialog" title="微信付款" :visible.sync="common.wechat_pay_dialog">
            <div id="pay_dialog_top_layer">
                <p>请打开手机微信扫一扫功能扫描下方二维码进行付款</p>
                <p>付款发起时间：<span class="font_bold font_red">{{ol_pay.dynamic_data}}</span></p>
                <p>付款截止时间：<span class="font_bold font_red">{{ol_pay.surplus_sec}}</span></p>
                <div id="wechat_pay_qrcode">
                    <img :src="'/wx/pay/encodeQrcode?content=' + ol_pay.pay_qr_code" alt="">
                </div>
            </div>
            
        </el-dialog>
    </div>
</body>

<script>
    var appInstince = new Vue({
        el: '#app',
        data: {
            common: {   //页面公共信息
                tabs_num: 'first',  //tab页面切换按钮
                wechat_pay_dialog: false,   //微信支付弹窗
                signInAccountType: '',      //当前登录用户类型
                user_info: {        //用户信息
                    idPhoto: ''
                }
            },
            ol_pay: {   //在线支付
                dynamic_data: null, //动态时间
                pay_who: false,  //为谁缴费
                pay_who_idCard: '',  //被代缴人身份证号码
                monthly: null,  //支付起始月
                month_num: 1,   //一次性缴费时长
                pay_money: 0,   //要支付的党费
                agree: false,    //同意缴费条款
                pay_qr_code: null,   //微信支付二维码链接
                surplus_sec: null  //有效支付剩余时间
            },
            offl_pay: { //离线支付
                monthly: null,  //支付起始月
                month_num: 1,   //缴费时长
                pay_money: 0,   //支付的党费
                pay_date: null  //缴纳时间
            },
            special_pay: {  // 特殊党费缴纳
                pay_money: 0,   //想要支付的特殊党费
                agree: false    //同意缴费条款
            },
            record_pay: {   //缴费记录
                pay_record: [
                    {
                        name: '王小虎',
                        date: '2016-05-01 至 2016-05-31',
                        pay_date: '2016-05-23',
                        money: '￥300',
                        pay_money: '￥300',
                        pay_month: '2015-05期',
                        pay_monthly: '3个月'
                    },{
                        name: '王小虎',
                        date: '2016-08-01 至 2016-05-31',
                        pay_date: '2016-08-23',
                        money: '￥300',
                        pay_money: '￥300',
                        pay_month: '2015-08期',
                        pay_monthly: '3个月'
                    }
                ]
            },
            off_line_pay: {
                pay_record: null
            }
        },
        created: function () {
            this.getNowDate();
        },
        mounted:function () {
            this.get_user_info();   //得到当前登录用户信息
            this.getSignInAccountType();
        },
        methods: {
            getPayTotalMoney() {
                var obj = this;
                obj.ol_pay.pay_money = 0;
                obj.ol_pay.pay_money = obj.common.user_info.income * obj.ol_pay.month_num * obj.common.user_info.partyProportion / 100;
            },
            getNowDate() {
                var obj = this;

                let nowDate = new Date();
                let year = nowDate.getFullYear();
                let month = nowDate.getMonth();
                let day = nowDate.getDay();
                let hour = nowDate.getHours();
                let min = nowDate.getMinutes();
                let sec = nowDate.getSeconds();
                min = min < 10 ? "0" + min : min;
                sec = sec < 10 ? "0" + sec : sec;

                obj.ol_pay.dynamic_data = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
                setTimeout(() => {
                    obj.getNowDate();
                }, 500);
            },
            getSignInAccountType() {	/*得到该登录用户的类型*/
				var obj = this;

				var url = "/siat/getSignInAccountType";
				var t = {
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							obj.common.signInAccountType = data.data;
						}
					}

				})
			},
            get_user_info() {   //自缴时查询自己的信息
                var obj = this;
				var url = "/siat/getSignAccount";
				var t = {
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							obj.common.user_info= data.data;

                            obj.common.user_info.idPhoto = "http://192.168.0.8:3000" + obj.common.user_info.idPhoto;

                            obj.getPayTotalMoney();
						}
					}
				})
            },
            reset_user_info() { //重置用户信息
                var obj = this;
                if (obj.ol_pay.pay_who) {   //为他人缴费
                    obj.common.user_info = {
                        idPhoto: '',
                        income: 0
                    };
                    obj.ol_pay.pay_money = 0;
                    obj.ol_pay.pay_who_idCard = '';
                } else {
                    obj.get_user_info();
                }
            },
            get_who_info() {    //得到代缴人的信息
                var obj = this;
                if (obj.ol_pay.pay_who_idCard != null) {
                    var obj = this;
                    var url = "/siat/getSignAccountIdCard";
                    var t = {
                        idCard: obj.ol_pay.pay_who_idCard
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            if (data.data != undefined) {	
                                obj.common.user_info= data.data;

                                obj.common.user_info.idPhoto = "http://192.168.0.8:3000" + obj.common.user_info.idPhoto;

                                obj.getPayTotalMoney();
                            }
                        }
                    })
                } else {
                    obj.reset_user_info();
                }
            },
            query_off_line_pay_record() {
                let obj = this;
                var url = "/offlpr/queryOffLinePayRecord";
				var t = {
					userId: obj.common.user_info.id
				}
				$.post(url, t, function(data, status){
					if (data.code == 200 && data.data != null) {
                        obj.off_line_pay.pay_record = data.data;
                    }
				})
            },
            init_tab_info(tab, event) { //切换tab页时的处理
                let obj = this;
                obj.ol_pay.pay_who = false;
                obj.get_user_info();
                if (tab.name == 'five') {
                    obj.query_off_line_pay_record();
                }
            },
            open_pay_dialog() { //开始微信支付
                var obj = this;
                
                if (obj.common.signInAccountType != 'party_role') {
                    obj.$message({
                        message: '请使用个人用户登录',
                        type: 'error'
                    });
                    return;
                }
                if (!obj.ol_pay.agree) {    //没有点击同意条款
                    obj.$message({
                        message: '请先阅读条款并同意',
                        type: 'error'
                    });
                    return;
                }
                if (obj.common.user_info.id == null || obj.common.user_info.id == '') {
                    obj.$message({
                        message: '没有查询到用户信息',
                        type: 'error'
                    });
                    return;
                }
                if (obj.common.user_info.orgInfoName == null || obj.common.user_info.orgInfoName == '') {
                    obj.$message({
                        message: '该用户未加入组织',
                        type: 'error'
                    });
                    return;
                }
                if (obj.ol_pay.monthly == null || obj.ol_pay.monthly == '') {
                    obj.$message({
                        message: '请选择党费缴纳的起始月份',
                        type: 'error'
                    });
                    return;
                }
                if (obj.common.user_info.statusName == '流动党员' && (obj.common.user_info.income == null
                    || obj.common.user_info.income == '' || obj.common.user_info.income == 0)) {
                    obj.$message({
                        message: '请输入您的月收入',
                        type: 'error'
                    });
                    return;
                }
                
                var url = "/wx/pay/getCodeUrl";
				var t = {
					total_fee: obj.ol_pay.pay_money * 100,   //后台以分为单位，所以在元的单位上乘100
                    body: "智慧党建-在线缴纳党费",
                    product_id: "00000001"
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						if (data.data != undefined) {	
							console.log(data.data);
                            if (data.data.return_code == "FAIL") {
                                obj.$message({
                                    message: data.data.return_msg,
                                    type: 'error'
                                });
                            } else if (data.data.return_code == "SUCCESS") {
                                obj.ol_pay.pay_qr_code = data.data.code_url;
                                obj.common.wechat_pay_dialog = true;
                                let old_time_expire = new Date(parseInt(data.data.time_expire));
                                let old_year = old_time_expire.getFullYear();
                                let old_month = old_time_expire.getMonth();
                                let old_day = old_time_expire.getDay();
                                let old_hour = old_time_expire.getHours();
                                let old_min = old_time_expire.getMinutes();
                                let old_sec = old_time_expire.getSeconds();
                                obj.ol_pay.surplus_sec = old_year + "-" + old_month + "-" + old_day +
                                    " " + old_hour + ":" + old_min + ":" + old_sec;
                            }
						} else {
                            obj.$message({
                                message: '出现一些意外',
                                type: 'error'
                            });
                        }
					}
					
				})
            },
            reset_wechat_pay_dialog() {
                let obj = this;
                obj.ol_pay.surplus_sec = null;
            },
            subm_record() {
                let obj = this;
                if (obj.offl_pay.pay_date == null || obj.offl_pay.pay_date == '') {
                    obj.$message({
                        message: '请输入您的缴纳时间',
                        type: 'error'
                    });
                    return;
                }
                if (obj.offl_pay.monthly == null || obj.offl_pay.monthly == '') {
                    obj.$message({
                        message: '请输入您缴纳党费的起始月',
                        type: 'error'
                    });
                    return;
                }
                if (obj.offl_pay.pay_money == null || obj.offl_pay.pay_money == '') {
                    obj.$message({
                        message: '请输入您已缴纳的党费金额',
                        type: 'error'
                    });
                    return;
                }
                if (obj.offl_pay.pay_money <= 0) {
                    obj.$message({
                        message: '请输入正确的金额',
                        type: 'error'
                    });
                    return;
                }
                var url = "/offlpr/insertOffLinePayRecord";
				var t = {
					userId: obj.common.user_info.id,
                    orgId: obj.common.user_info.orgInfoId,
                    payDate: obj.offl_pay.pay_date,
                    payMonthly: obj.offl_pay.monthly,
                    monthNum: obj.offl_pay.month_num,
                    payMoney: obj.offl_pay.pay_money,
                    isPass: 0
				}
				$.post(url, t, function(data, status){
					if (data.code == 200) {
						obj.$message({
                            message: data.msg,
                            type: 'success'
                        });

                        obj.offl_pay.pay_date = '';
                        obj.offl_pay.monthly = '';
                        obj.offl_pay.pay_money = '';
					}
				})
            }
        }
    })

    window.onFocus = function () {
		window.location.reload();
	}
</script>


</html>