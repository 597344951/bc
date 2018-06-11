<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>

<head>
	<base href="/">
	<%@include file="/include/head.jsp"%>
	<%@include file="/include/vcharts.jsp"%>
  </head>

<body>
	<h1>vcharts 测试页面 </h1>
	<div id="app">
		<ve-line :data="chartData"></ve-line>
		<el-button type="primary" @click="addData">增加一个点</el-button>
		<el-button type="danger" @click="delData">删除一个点</el-button>

		<h2>饼图 </h2>
		<template>
			<ve-pie :data="pieChartData" :settings="pieChartData.chartSettings"></ve-pie>
		</template>
	</div>
</body>
<script type="text/javascript">
	var appInstince = new Vue({
		el: '#app',
		data: function () {
			return {
				idx: 5,
				pieChartData: {
					chartSettings: {
						roseType: 'radius'
					},
					columns: ['日期', '访问用户'],
					rows: [
						{ '日期': '1/1', '访问用户': 1393 },
						{ '日期': '1/2', '访问用户': 3530 },
						{ '日期': '1/3', '访问用户': 2923 },
						{ '日期': '1/4', '访问用户': 1723 },
						{ '日期': '1/5', '访问用户': 3792 },
						{ '日期': '1/6', '访问用户': 4593 }
					]
				},
				chartData: {
					columns: ['日期', '销售额'],
					rows: [
						{ '日期': '1月1日', '销售额': 123 },
						{ '日期': '1月2日', '销售额': 1223 },
						{ '日期': '1月3日', '销售额': 2123 },
						{ '日期': '1月4日', '销售额': 4123 },
						{ '日期': '1月5日', '销售额': 3123 }

					]
				}
			}
		},
		methods: {
			addData() {
				this.idx++;
				this.chartData.rows.push({ '日期': '1月' + this.idx + '日', '销售额': Math.random() * 5000 });
			},
			delData() {
				this.chartData.rows = this.chartData.rows.slice(1);
			}
		}
	})
</script>

</html>