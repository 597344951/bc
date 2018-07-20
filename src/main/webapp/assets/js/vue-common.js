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
Vue.filter('time', function (value) {
	if (!value)
		return ''
	return moment(value).format('HH:mm:ss')
});
/** 日期时间格式化 * */
Vue.filter('datetime', function (value) {
	if (!value)
		return ''
	return moment(value).format('YYYY-MM-DD HH:mm:ss')
});
/** 指定时间距离现在 过去多长时间 **/
Vue.filter('time_ago', function (value) {
	if (!value)
		return '为止'
	return timeAgo(new Date(value));
});
/** 计算机容量转化 **/
Vue.filter('byteToSize', value => {

	return bytesToSize(value);
});
/**时间转化 **/
Vue.filter('secToMin', value => {
	if(!value)return '';
	let f = Math.abs(value);
	let ret = '';
	// return f;
	if (f < 60) {
		return f+'秒'
	} //小于60秒
	if (f > 60 ) {
		ret  = parseInt(f / 60) + '分'+(f % 60)+'秒'
		return ret
	}
});

function bytesToSize(bytes) {
	if(!bytes) return '';
	if (bytes === 0) return '0 B';
	var k = 1024, // or 1024
		sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
		i = Math.floor(Math.log(bytes) / Math.log(k));
	return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

/** 覆盖Date toString **/


Date.prototype.toLocaleString = function () {
	return moment(this).format('YYYY-MM-DD HH:mm:ss')
}

Date.prototype.toLocaleDateString = function () {
	return moment(this).format('YYYY-MM-DD')
}