function isJsonString(str) {
	try {
		if (typeof JSON.parse(str) == "object") {
			return true;
		}
	} catch (e) {}
	return false;
}

/**
 * EVAL
 */
function EVAL(str) {
	var json = eval('(' + str + ')');
	return json;
}

/**
 * 设置 cookie
 * 
 * @param CKname
 *            cookie名
 * @param CKvalue
 *            值
 * @param duration
 *            1:一天
 * @param CKpath
 * @param CKdomain
 */
function setCookie(CKname, CKvalue, duration, CKpath, CKdomain) {
	var NewDate = new Date();
	NewDate.setTime(NewDate.getTime() + duration * 24 * 60 * 60 * 1000);

	document.cookie = CKname + '=' + escape(CKvalue) +
		(duration ? ";expires=" + NewDate.toGMTString() : "") +
		(CKpath ? ";path=" + CKpath : "") +
		(CKdomain ? ";domain=" + CKdomain : "")
}
/**
 * 获取 cookie
 * 
 * @param CKname
 *            cookie名
 * @returns
 */
function getCookie(CKname) {
	var arrCookie = document.cookie.match(new RegExp('(^|)' + CKname +
		'=([^;]*)(;|$)'));
	if (arrCookie) {
		return unescape(arrCookie[2]);
	} else {
		return null;
	}
}
// success/warning/info/error
function toast(title, content, type, _ins) {
	if (!type)
		type = 'info';
	var ins = _ins || top.appInstince || this.appInstince;
	if (ins) {
		ins.$notify({
			title: title,
			message: content,
			type: type,
			offset: 50
		});
	} else {
		alert(content);
	}
}
//
function $confirm(title, content, type, _ins, yes, no) {
	_ins.$confirm(content, title, {
		type: type
	}).then(yes).catch(no);
}

function $message(msg, type, _ins) {
	_ins.$message({
		message: msg,
		type: type
	});
}

/*
 * 浏览器全屏
 */
function fullScreen(_ins) {
	var el = document.documentElement;
	var rfs = el.requestFullScreen || el.webkitRequestFullScreen;
	if (typeof rfs != "undefined" && rfs) {
		rfs.call(el);
	} else if (typeof window.ActiveXObject != "undefined") {
		var wscript = new ActiveXObject("WScript.Shell");
		if (wscript != null) {
			wscript.SendKeys("{F11}");
		}
	} else if (el.msRequestFullscreen) {
		el.msRequestFullscreen();
	} else if (el.oRequestFullscreen) {
		el.oRequestFullscreen();
	} else {
		_ins.$message({
			message: "浏览器不支持全屏调用！请更换浏览器或按F11键切换全屏",
			type: 'warn'
		});
	}
}

/*
 * 浏览器退出全屏
 */
function exitFullScreen(_ins) {
	var el = document;
	var cfs = el.cancelFullScreen || el.webkitCancelFullScreen || el.exitFullScreen;
	if (typeof cfs != "undefined" && cfs) {
		cfs.call(el);
	} else if (typeof window.ActiveXObject != "undefined") {
		var wscript = new ActiveXObject("WScript.Shell");
		if (wscript != null) {
			wscript.SendKeys("{F11}");
		}
	} else if (el.msExitFullscreen) {
		el.msExitFullscreen();
	} else if (el.oRequestFullscreen) {
		el.oCancelFullScreen();
	} else {
		_ins.$message({
			message: "浏览器不支持全屏调用！请更换浏览器或按F11键切换全屏",
			type: 'warn'
		});
	}
}
/**
 * 指定时间据当前过去了多长时间
 * 
 * @param {*}
 *            dt
 */
function timeAgo(dt) {
	var n = new Date().getTime();
	var f = n - dt.getTime();
	var bs = (f >= 0 ? '前' : '后'); // 判断时间点是在当前时间的 之前 还是 之后
	f = Math.abs(f);
	// return f;
	if (f < 6e4) {
		return '刚刚'
	} // 小于60秒,刚刚
	if (f < 36e5) {
		return parseInt(f / 6e4) + '分钟' + bs
	} // 小于1小时,按分钟
	if (f < 864e5) {
		return parseInt(f / 36e5) + '小时' + bs
	} // 小于1天按小时
	if (f < 2592e6) {
		return parseInt(f / 864e5) + '天' + bs
	} // 小于1个月(30天),按天数
	if (f < 31536e6) {
		return parseInt(f / 2592e6) + '个月' + bs
	} // 小于1年(365天),按月数
	return parseInt(f / 31536e6) + '年' + bs; // 大于365天,按年算
}
/**
 * 检测 顶层url是否和当前页面一致
 * 
 * @returns
 */
function checkLocationSame() {
	return window.location.href == top.location.href;
}

function openwindow(url, name, iWidth, iHeight) {
	if(!iWidth) iWidth = window.screen.width;
	if(!iHeight) iHeight = window.screen.height;

	// url 转向网页的地址
	// name 网页名称，可为空
	// iWidth 弹出窗口的宽度
	// iHeight 弹出窗口的高度
	// window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽
	var iTop = (window.screen.height -  iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.width -  iWidth) / 2; // 获得窗口的水平位置;
	window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}
/**
 * 生成面包路径 
 * @param {*} target 目标对象 
 * @param {*} datas 所有数据
 * @param {*} getChild  获取
 * @param {*} getParentId 上一级id
 * @param {*} getSelfId   自己的id
 * @param {*} getSelf     获取自身数据
 */
function breadPath(target, datas, getChild, getParentId, getSelfId, getSelf) {
	/**
	 * 深度遍历路径
	 * @param {*} ay 路径数组
	 * @param {*} target 目标对象
	 * @param {*} datas  数组
	 */
	function pathScan(ay, target, datas) {
		//最顶级的item
		if (getParentId(target) == 0) {
			ay.push(target);
			return ay;
		}

		var pid = getSelfId(target);
		for (var i = 0; i < datas.length; i++) {
			var v = datas[i];
			let self = getSelf(v);
			//自身
			if (getSelfId(self) == pid) {
				ay.push(self);
				return;
			}
			let chi = getChild(v);
			if (chi) {
				pathScan(ay, target, chi);
				if (ay.length > 0) {
					var cn = ay[ay.length - 1];
					let self = getSelf(v);
					if (getSelfId(self) == getParentId(cn)) {
						ay.push(self);
						return;
					}
				}
			} else {
				if (ay.length == 0) {
					let self = getSelf(v);
					// 找到了位置
					if (getSelfId(self) == pid) {
						ay.push(self);
					}
				} else {
					var cn = ay[ay.length - 1];
					let self = getSelf(v);
					if (getSelfId(self) == getParentId(cn)) {
						ay.push(self);
						return;
					}
				}
			}
		}
	}
	var ay = [];
	pathScan(ay, target, datas);
	return ay.reverse();
}
/**
 * 获取指定数据对象子数据
 * @param {*} target 目标对象 
 * @param {*} datas 所有数据
 * @param {*} getChild  获取
 * @param {*} getSelfId   自己的id
 * @param {*} getSelf     获取自身数据
 */
function getDataNextItem(target, datas, getChild,getSelfId, getSelf) {
	/**
	 * 深度遍历路径
	 * @param {*} ay 路径数组
	 * @param {*} target 目标对象
	 * @param {*} datas  数组
	 */
	function pathScan( target, datas) {
		//优先 广度遍历
		var pid = getSelfId(target);
		for (var i = 0; i < datas.length; i++) {
			var v = datas[i];
			let self = getSelf(v);
			//自身
			if (getSelfId(self) == pid) {
				return v;
			}
		}
		for (var i = 0; i < datas.length; i++) {
			var v = datas[i];
			let chi = getChild(v);
			if (chi) {
				return pathScan( target, chi);
			}  
		}
	}
	return pathScan(target, datas);
}

/** 复制对象* */
function clone(obj) {
	return JSON.parse(JSON.stringify(obj));
}
 
/**
 * 获取地址栏参数Map对象
 */
function UrlSearchParams() {
	var name, value;
	var str = location.href; //取得整个地址栏
	var num = str.indexOf("?")
	str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]

	var arr = str.split("&"); //各个参数放到数组里
	let map = new Map();
	for (var i = 0; i < arr.length; i++) {
		num = arr[i].indexOf("=");
		if (num > 0) {
			name = arr[i].substring(0, num);
			value = arr[i].substr(num + 1);
			map.set(name,value);
		}
	}
	return map;
}