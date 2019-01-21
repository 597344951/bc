
import chartConfig from "/assets/js/vcharts_config.js";
var appInstince = new Vue({
	el: '#app',
	data: {
		chartConfig: chartConfig,
		onesta: true,
		twosta: false,
		threesta: false,
		foursta: false,
		chartSettings: {
			roseType: 'radius'
		},

		ratiochartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		onlinechartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		locchartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		warrantychartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		revchartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		verchartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		typchartData: {
			columns: ['name', 'value'],
			rows: [{
				'日期': '1/1',
				'访问用户': 1393
			},
			{
				'日期': '1/2',
				'访问用户': 3530
			},
			]
		},
		totalData: {
			columns: ['name', 'value', 'rate'],
			rows: [
				{ '日期': '1/1', '访问用户': 1393, '下单率': 0.32 },
				{ '日期': '1/2', '访问用户': 3530, '下单率': 0.26 },
				{ '日期': '1/3', '访问用户': 2923, '下单率': 0.76 },
				{ '日期': '1/4', '访问用户': 1723, '下单率': 0.49 },
				{ '日期': '1/5', '访问用户': 3792, '下单率': 0.323 },
				{ '日期': '1/6', '访问用户': 4593, '下单率': 0.78 }
			]
		},
		statistics: false,
		dialogVisible: false,
		/* 新增用户弹窗默认不显示值初始化 */
		usersStatus: [ /* 用户状态值初始化 */ {
			value: '0',
			label: '禁用'
		}, {
			value: '1',
			label: '可用'
		}],
		pager: { /*初始化分页信息*/
			pageNum: 1,
			/* 当前页 */
			pageSize: 10,
			/* 页面大小 */
		},
		tbi: {
			title: "测试标题",
			visible: false,
			visibleMap2: false,
			update: false, // 是否是更新
			search: false,
			style: false,
			helpMap: "",
			data: {
				oid: "",
				name: "",
				id: "",
				code: "",
				typeId: "",
				resTime: "",
				online: "",
				lastTime: "",
				ip: "",
				mac: "",
				sys: "",
				size: "",
				ratio: "",
				rev: "",
				ver: "",
				typ: "",
				tel: "",
				addr: "",
				gis: "",
				warranty: "",
				loc: "",
				principal:""
			},

		}

	},
	created: function () {
		this.queryTerminals(1, 12); //这里定义这个方法，vue实例之后运行到这里就调用这个函数
		this.mapThings();

	},
	methods: {
		pagerCurrentChange(pageNum, pageSize) { /* 页码改变时 */
			var tbi = this.tbi;
			// this.queryTerminals(pageNum, pageSize);
			this.querySearch(tbi, pageNum, pageSize);
		},
		initPager() {
			this.pager.pageNum = 1;
			this.pager.pageSize = 12;
			this.pager.total = 0;
			this.pager.list = new Array();
		},

		queryTerminals(pageNum, pageSize) { /* 查询系统用户信息 */
			var obj = this;
			var pn = pageNum;
			var pz = pageSize;
			var url2 = pn + "-" + pz;

			var url = "/terminal/basic/queryInfo/";
			url += url2;
			var t = {
				pageNum: pageNum == null ? 1 : pageNum,
				pageSize: pageSize == null ? 12 : pageSize,
			}
			ajax_json(url, 'post', t, function (data) {
				// toast('成功', data.msg, 'success')
				if (data.data == undefined) { /* 没有查询到数据时，初始化页面信息，使页面正常显示 */
					obj.initPager();
				} else {
					obj.pager = data.data;
				}
			});

		},
		refresh() {
			this.visibleMap2 = false
            this.tbi.visible = false
            this.tbi.update = false
            this.tbi.search = false
            this.tbi.style = false
            this.tbi.data.oid = ""
            this.tbi.data.name = ""
            this.tbi.data.id = ""
            this.tbi.data.code = ""
            this.tbi.data.typeId = ""
            this.tbi.data.resTime = ""
            this.tbi.data.online = ""
            this.tbi.data.lastTime = ""
            this.tbi.data.ip = ""
            this.tbi.data.mac = ""
            this.tbi.data.sys = ""
            this.tbi.data.size = ""
            this.tbi.data.ratio = ""
            this.tbi.data.rev = ""
            this.tbi.data.ver = ""
            this.tbi.data.typ = ""
            this.tbi.data.tel = ""
            this.tbi.data.addr = ""
            this.tbi.data.gis = ""
            this.tbi.data.warranty = ""
            this.tbi.data.loc = ""
            this.tbi.data.principal = ""
            this.statistics = false
            this.onesta = true
            this.twosta = false
            this.threesta = false
            this.foursta = false
			this.queryTerminals(1, 12)
			this.tbi.helpMap = ""
		},

		querySearch(tbi, pageNum, pageSize) { /* 查询系统用户信息 */
			var obj = this;
			var url = "/terminal/basic/queryInfo/";
			var tbi = this.tbi;
			var pn = pageNum;
			var pz = pageSize;
			var url2 = pn + "-" + pz;
			url += url2;
			tbi.visible = false;
			ajax_json(url, 'post', tbi.data, function (data) {
				// toast('成功', data.msg, 'success')
				if (data.data == undefined) { /* 没有查询到数据时，初始化页面信息，使页面正常显示 */
					obj.initPager();
				} else {
					obj.pager = data.data;
				}
			});

		},
		mapThings() {
			var tbi = this.tbi;
			tbi.style = true;
			setTimeout(() => { this.drawmap() }, 200);


		},
		mapThings2() {
			var tbi = this.tbi;
			tbi.visibleMap2 = true;
			setTimeout(() => { this.drawmap2() }, 200);
		},
		drawmap2() {
			var tbi = this.tbi;
			var centerString="";
			//var centers=[120.024583,30.290275];
			if(tbi.data.gis){
				centerString=tbi.data.gis;
				centers=[centerString.split(",")[0],centerString.split(",")[1]];
			};
			
			var map = new AMap.Map("container1", {
				resizeEnable: true,	
				center:centers,
				zoom: 13
			});
			var placeSearch = new AMap.PlaceSearch({
				map: map
			});  //构造地点查询类
			var geocoder;
			function regeoCode() {
				if(!geocoder){
					geocoder = new AMap.Geocoder({
						
					});
				}
				var lnglat  = tbi.data.gis.split(',');
				
				geocoder.getAddress(lnglat, function(status, result) {
					if (status === 'complete'&&result.regeocode) {
						var address = result.regeocode.formattedAddress;
						tbi.data.addr = address;
					}
				});
			}
			map.on('click', function (e) {
				tbi.data.gis = e.lnglat.toString();
				regeoCode();
			});

			placeSearch.search(tbi.helpMap, function (status, result) {
				// 搜索成功时，result即是对应的匹配数据
				console.log(result)
				var pois = result.poiList.pois;
				for (var i = 0; i < pois.length; i++) {
					var poi = pois[i];
					var marker = [];
					marker[i] = new AMap.Marker({
						position: poi.location, // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
						title: poi.name
					});
					// 将创建的点标记添加到已有的地图实例：
					map.add(marker[i]);
				}
				map.setFitView();
			});




		},
		drawmap() {
			var map = new AMap.Map('container', {
				resizeEnable: true,
				zoom: 11,//级别
				center: [120.187222, 30.24298],//中心点坐标
				// viewMode: '3D',//使用3D视图
				layers: [//使用多个图层
					new AMap.TileLayer.Satellite(),
					new AMap.TileLayer.RoadNet(),
				],

			});
			map.getLayers()[0].hide();//默认隐藏卫星图层
			AMap.event.addListener(map, 'zoomend', function () {
				var zoom = map.getZoom();
				if (zoom >= 14) {
					map.getLayers()[0].show();
				} else {
					map.getLayers()[0].hide();
				}
			});
			var markers = [{
				icon: 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b1.png',
				position: [116.205467, 39.907761],

			}, {
				icon: 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b2.png',
				position: [116.368904, 39.913423],

			}, {
				icon: 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b3.png',
				position: [116.305467, 39.807761],

			}];

			ajax_json("/terminal/basic/addup", "get", 'tbi.data', function (data) {
				markers = data.data;
				console.log(data.data);
				initMarker();

			});
			function initMarker() {
				map.clearMap();
				// 添加一些分布不均的点到地图上,地图上添加三个点标记，作为参照
				markers.forEach(function (marker) {
					marker.position[0] = marker.position[0] - 0;
					marker.position[1] = marker.position[1] - 0;
					var c = marker;
					var b = [marker.position[0], marker.position[1]];
					var a = new AMap.Marker({
						map: map,
						icon: marker.icon,
						position: [marker.position[0], marker.position[1]],
						offset: new AMap.Pixel(-12, -36),

					});
					AMap.event.addListener(a, 'click', function () {
						windowMake(a, b, c);
					});
				});
				// var center = map.getCenter();
				var center = map.setFitView();
				// var centerText = '当前中心点坐标：' + center.getLng() + ',' + center.getLat();
				document.getElementById('centerCoord').innerHTML = center.getCenter;
				document.getElementById('tips').innerHTML = '';

				// 添加事件监听, 使地图自适应显示到合适的范围
				AMap.event.addDomListener(document.getElementById('setFitView'), 'click', function () {
					var newCenter = map.setFitView();
					document.getElementById('centerCoord').innerHTML = '当前中心点坐标：' + newCenter.getCenter();
					document.getElementById('tips').innerHTML = '';
				});
				function windowMake(a, b, c) {
					var title = c.title;
					var content = c.content
					var infoWindow = new AMap.InfoWindow({
						isCustom: true,  //使用自定义窗体
						content: createInfoWindow(title, content.join("<br/>")),
						offset: new AMap.Pixel(8, -26)
					});
					console.log(b);
					infoWindow.open(map, b);
					//构建自定义信息窗体
					function createInfoWindow(title, content) {
						var info = document.createElement("div");
						info.className = "info";

						//可以通过下面的方式修改自定义窗体的宽高
						info.style.width = "250px";
						info.style.borderRadius = '6px';
						info.style.padding = '5px';
						info.style.backgroundColor = "#fff";
						info.style.boxShadow = "0px 0 20px 0px #7d7d7d";
						// 定义顶部标题
						var top = document.createElement("div");
						var titleD = document.createElement("div");
						var closeX = document.createElement("img");
						top.className = "info-top";
						titleD.innerHTML = title;
						closeX.src = "http://webapi.amap.com/images/close2.gif";
						closeX.onclick = closeInfoWindow;

						top.appendChild(titleD);
						top.appendChild(closeX);
						info.appendChild(top);

						// 定义中部内容
						var middle = document.createElement("div");
						middle.className = "info-middle";
						middle.style.backgroundColor = 'white';
						middle.style.borderBottomRightRadius = '6px';
						middle.style.borderBottomLeftRadius = '6px';
						middle.style.padding = '10px';
						middle.innerHTML = content;
						info.appendChild(middle);

						// 定义底部内容
						var bottom = document.createElement("div");
						bottom.className = "info-bottom";
						bottom.style.position = 'relative';
						bottom.style.top = '5px';
						bottom.style.margin = '0 auto';
						var sharp = document.createElement("img");
						sharp.src = "http://webapi.amap.com/images/sharp.png";
						bottom.appendChild(sharp);
						info.appendChild(bottom);
						return info;
					};
				};
				//关闭信息窗体

			};
			function closeInfoWindow() {
				map.clearInfoWindow();
			};


		},

		//同步
		syn() {
			var a = this;
			ajax_json("/terminal/basic/addup", "post", "", function (result) {
				a.refresh();
				a.$message({
					message: result.msg,
					type: result.status ? 'success' : 'error'
				});
			});
		},
		updateTbi(a) {

			var tbi = this.tbi;
			tbi.search = false;
			tbi.data = a;
			tbi.visible = true;
			tbi.title = "修改终端信息";
			tbi.update = true;
			tbi.visibleMap2 = false;
			tbi.helpMap = "";
		},
		cancel(tbi) {
			tbi.visible = false;
			this.queryTerminals(1, 12);
		},
		search() {
			this.refresh();
			var tbi = this.tbi;
			tbi.visible = true;
			tbi.update = false;
			tbi.search = true;
			tbi.visibleMap2 = false;
		},
		fastSearch() {
			var tbi = this.tbi;
			tbi.update = false;
			tbi.search = true;
			tbi.visibleMap2 = false;
			this.updateorinsert(tbi);
		},
		online() {
			var tbi = this.tbi;
			tbi.search = true;
			tbi.data.online = '1';
			this.updateorinsert(tbi)
		},
		outline() {
			var tbi = this.tbi;
			tbi.search = true;
			tbi.data.online = '0';
			this.updateorinsert(tbi)
		},

		updateorinsert(tbi) {
			var a = this;
			if (tbi.search) {
				tbi.search = false;
				this.querySearch(tbi, 1, 12);
			} else {
				if (tbi.update) {
					ajax_json("/terminal/basic/addup", "put", tbi.data, function (result) {
						if (result.status) {
							tbi.visible = false;
							a.refresh();
						}
					});
				} else {
					ajax_json("/terminal/basic/addup", "post", tbi.data, function (result) {
						if (result.status) {
							tbi.visible = false;
							a.refresh();
						}
					});
				}
			}
		},

		draw(str) {

			var ins = this;
			var a = str;
			// 使用刚指定的配置项和数据显示图表。
			ajax_json("/terminal/basic/echarts/" + a, "put", null, function (data) {
				if (a == 'ratio') {
					ins.ratiochartData.rows = data.data;
				}
				if (a == 'online') {
					ins.onlinechartData.rows = data.data;
				}
				if (a == 'loc') {
					ins.locchartData.rows = data.data;
				}
				if (a == 'warranty') {
					ins.warrantychartData.rows = data.data;
				}
				if (a == 'rev') {
					ins.revchartData.rows = data.data;
				}
				if (a == 'ver') {
					ins.verchartData.rows = data.data;
				}
				if (a == 'typ') {
					ins.typchartData.rows = data.data;
				}
				if (a == 'total') {
					ins.totalData.rows = data.data;
				}


			});

		},
		sta(str) {
			var ins = this;
			var a = str;
			if (a == 'onesta') {
				ins.onesta = true;
				ins.twosta = false;
				ins.threesta = false;
				ins.foursta = false;
			}
			if (a == 'twosta') {
				ins.onesta = false;
				ins.twosta = true;
				ins.threesta = false;
				ins.foursta = false;
			}
			if (a == 'threesta') {
				ins.onesta = false;
				ins.twosta = false;
				ins.threesta = true;
				ins.foursta = false;
			}
			if (a == 'threesta') {
				ins.onesta = false;
				ins.twosta = false;
				ins.threesta = true;
				ins.foursta = false;
			}
			if (a == 'foursta') {
				ins.onesta = false;
				ins.twosta = false;
				ins.threesta = false;
				ins.foursta = true;

			}
			ins.statistic();
		},

		statistic() {
			this.statistics = true;
			this.draw("ratio");
			this.draw("online");
			this.draw("loc");
			this.draw("warranty");
			this.draw("rev");
			this.draw("ver");
			this.draw("typ");
			this.draw("total");
		},
	}

});
window.appInstince = appInstince