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
<title>用户管理图标总览</title>
<%@include file="/include/head.jsp"%>
<%@include file="/include/echarts.jsp"%>
<style type="text/css">
	#myChart{
		width: 400px;
		height: 400px
	}
</style>
</head>
<body>
	<div id="app">
		<div id="myChart"></div>
	</div>
</body>


<script type="text/javascript">
	var appInstince = new Vue({
		el: "#app",
		data: {
			userSexChart: {
				title : {
			        text: '用户男女占比',
			        subtext: '实时数据',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['男','女']
			    },
			    series : [
			        {
			            name: '性别',
			            type: 'pie',
			            radius : '70%',
			            center: ['50%', '50%'],
			            roseType: 'angle',	/* 通过半径表示数据大小 */
			            data:[
			                {value:335, name:'男'},
			                {value:310, name:'女'}
			            ],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			}
		},
		mounted () {
			this.drawLine();
		},
		methods: {
			drawLine(){
				var obj = this;
		        // 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById("myChart"));
		        // 绘制图表
		        myChart.setOption(obj.userSexChart);
		    }
		}
	});
</script>
</html>