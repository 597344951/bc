/**
 * Vue 公共代码
 * **/

/*常用过滤器设置,可在管道调用格式化数据显示
 * eg: {{ varable | datetime }}
 * */
/** 日期格式化* */
Vue.filter('date', function (value) {
	if (!value)
		return ''
	return moment(value).format('YYYY-MM-DD')
});
/** 日期时间格式化 * */
Vue.filter('datetime', function (value) {
	if (!value)
		return ''
	return moment(value).format('YYYY-MM-DD HH:mm:ss')
});

/** 覆盖Date toString **/
 

Date.prototype.toLocaleString = function () {
	return moment(this).format('YYYY-MM-DD HH:mm:ss')
}

Date.prototype.toLocaleDateString = function () {
	return moment(this).format('YYYY-MM-DD')
}