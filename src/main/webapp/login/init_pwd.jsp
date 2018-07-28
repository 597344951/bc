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
<title>初始化党员登录密码</title>
<%@include file="/include/head_notbootstrap.jsp"%>
<style type="text/css">
	#initPwdDiv {
		border: 1px solid #ddd;
		width: 420px;
		height: 400px;
		border-radius: 8px;
	}
</style>
</head>
<body>
	<div id="app">
		<div id="initPwdDiv" :style="marginTop">
			<div style="text-align: center; font-size: 20px; font-weight: bold; margin: 30px 0;">党员首次登录密码重置</div>
			<el-form label-width="120px" size="mini" :model="initPartyPasswordForm" status-icon 
					ref="initPartyPasswordForm" :rules="initPartyPasswordFormRules">
				<div>
					<div>
						<el-form-item label="账号" prop="idCard">
							<el-input 
								clearable 
								style="width: 230px;" 
								size="mini" 
								v-model="initPartyPasswordForm.idCard" 
								placeholder="账号为党员身份证号码"></el-input>
						</el-form-item>
					</div>
					<div>
						<el-form-item label="密码" prop="pwd">
							<el-input 
								clearable 
								style="width: 230px;" 
								size="mini" 
								type="password"
								v-model="initPartyPasswordForm.pwd" 
								placeholder="请输入密码"></el-input>
						</el-form-item>
					</div>
					<div>
						<el-form-item label="确认" prop="rPwd">
							<el-input 
								clearable 
								style="width: 230px;" 
								size="mini" 
								type="password"
								v-model="initPartyPasswordForm.rPwd" 
								placeholder="请在此确认输入的密码"></el-input>
						</el-form-item>
					</div>
				</div>
				<div style="margin-top: 15px; padding-left: 30px;">
					<el-button size="small" type="primary" @click="updatePartyUserPwd">修改</el-button>
				</div>
			</el-form>
		</div>
	</div>
</body>

<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: {
			marginTop: null,
			initPartyPasswordForm: {
				idCard: null,
				pwd: null,
				rPwd: null
			},
			initPartyPasswordFormRules: {
				idCard: [
					{ required: true, message: '请输入15或18位身份证号码!', trigger: 'blur' },
					{ pattern: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/, message: '请输入正确的身份证号码!'}
				],
				pwd: [
		        	{ required: true, message: '请输入密码!', trigger: 'blur' },
		        	{ min: 6, max: 16, message: '密码不能小于6位大于16位!'},
		        	{ pattern: "^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,16}$", message: '密码必须是数字+密码的组合!'}
		        ],
		        rPwd: [
		        	{ required: true, message: '请再次输入密码!', trigger: 'blur' },
		        	{ 
		        		validator: function(rule, value, callback){
		        			if (value !== appInstince.initPartyPasswordForm.pwd) {
	        		            callback(new Error('两次输入密码不一致!'));
	        		        } else {
	        		            callback();
	        		        }
		        		}
		        	}
		        ]
			}
		},
		created: function () {
			
		},
		mounted:function () {
			this.getScreenHeight();
		},
		methods: {
			getScreenHeight() {
				var obj = this;
				var height = window.innerHeight;
				obj.marginTop = "margin:" + parseInt(((height - 400) * 0.30)) + "px auto";
			},
			updatePartyUserPwd() {
				var obj = this;
				obj.$refs.initPartyPasswordForm.validate( function(valid) {
		            if (valid) {
		            	var url = "/sys/user/updateSysUserPwd";
		            	var t = {
        					username: obj.initPartyPasswordForm.idCard,
        					password: obj.initPartyPasswordForm.pwd
        				}
		            	$.post(url, t, function(data, status){
        					if (data.code == 200) {
        						toast('密码修改成功',data.msg,'success');
        						window.location.href = "/login/login.jsp"
        					}
        				})
		            } else {
		                console.log('error submit!!');
		                return false;
		            }
		        });
			}
		}
	});
</script>

</html>