import chartConfig from "/assets/js/vcharts_config.js";
var appInstince = new Vue({
	el: '#app',
	data: {
		activeName: 'first',
		chartConfig: chartConfig,
		options: [{
			value: '选项1',
			name: '广告1'
		}, {
			value: '选项2',
			name: '广告2'
		}, {
			value: '选项3',
			name: '广告3'
		}, {
			value: '选项4',
			name: '广告4'
		}, {
			value: '选项5',
			name: '广告5'
		}],
		userId: '',
		pickerOptions1: {
			disabledDate(time) {
				return time.getTime() > Date.now();
			},
			shortcuts: [{
				text: '今天',
				onClick(picker) {
					picker.$emit('pick', new Date());
				}
			}, {
				text: '昨天',
				onClick(picker) {
					const date = new Date();
					date.setTime(date.getTime() - 3600 * 1000 * 24);
					picker.$emit('pick', date);
				}
			}, {
				text: '一周前',
				onClick(picker) {
					const date = new Date();
					date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
					picker.$emit('pick', date);
				}
			}]
		},
		programDate: '',
		chartSettings: {
			roseType: 'radius'
		},
		chartSettings2: {
			xAxisType: 'value'
		},
		ratiochartData: {
			columns: ['name', '天数'],
			rows: [{
					'name': '预定播放天数',
					'天数': 3530
				},
				{
					'name': '当前已播放天数',
					'天数': 1620
				},
			]
		},
		ratiochartData2: {
			columns: ['name', '终端数'],
			rows: [{
					'name': '上城区',
					'终端数': 330
				},
				{
					'name': '拱墅区',
					'终端数': 420
				},
				{
					'name': '下城区',
					'终端数': 350
				},
				{
					'name': '江干区',
					'终端数': 140
				},
				{
					'name': '西湖区',
					'终端数': 610
				},
			]
		},
		barchartData: {
			columns: ['name', '时长'],
			rows: [{
					'name': '当日总播放',
					'时长': 3530
				},
				{
					'name': '当前已播放',
					'时长': 1120
				},
				{
					'name': '累计已播放',
					'时长': 7620
				},
			]
		},
		barchartData2: {
			columns: ['name', '次数'],
			rows: [{
					'name': '当日预估播放次数',
					'次数': 24
				},
				{
					'name': '当前已播放次数',
					'次数': 11
				},
				{
					'name': '累计已播放次数',
					'次数': 72
				},
			]
		},
		linechartData: {
			columns: ['name', '终端数'],
			rows: [{
					'name': '7',
					'终端数': 24
				},
				{
					'name': '9',
					'终端数': 35
				},
				{
					'name': '12',
					'终端数': 45
				},
				{
					'name': '17',
					'终端数': 35
				},
				{
					'name': '18',
					'终端数': 30
				},
				{
					'name': '20',
					'终端数': 10
				},
			]
		},




	},
	created: function () {
		this.userProgram();
	},
	methods: {
		handleClick(tab, event) {
			console.log(tab, event);

		},
		userProgram() {
			var ins = this;
			ajax_json("/terminal/playlist/userProgram", "get", '', function (data) {
				ins.options = data.data;
			});
		}
	},


});

window.appInstince = appInstince