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
 * @param duration 1:一天
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
 * @param {*} dt 
 */
function timeAgo(dt) {
	var n = new Date().getTime();
	var f = n - dt.getTime();
	var bs = (f >= 0 ? '前' : '后'); //判断时间点是在当前时间的 之前 还是 之后
	f = Math.abs(f);
	// return f;
	if (f < 6e4) {
		return '刚刚'
	} //小于60秒,刚刚
	if (f < 36e5) {
		return parseInt(f / 6e4) + '分钟' + bs
	} //小于1小时,按分钟
	if (f < 864e5) {
		return parseInt(f / 36e5) + '小时' + bs
	} //小于1天按小时
	if (f < 2592e6) {
		return parseInt(f / 864e5) + '天' + bs
	} //小于1个月(30天),按天数
	if (f < 31536e6) {
		return parseInt(f / 2592e6) + '个月' + bs
	} //小于1年(365天),按月数
	return parseInt(f / 31536e6) + '年' + bs; //大于365天,按年算
}
/**
 * 检测 顶层url是否和当前页面一致
 * @returns
 */
function checkLocationSame() {
	return window.location.href == top.location.href;
}

function openwindow(url, name, iWidth, iHeight) {
	// url 转向网页的地址  
	// name 网页名称，可为空  
	// iWidth 弹出窗口的宽度  
	// iHeight 弹出窗口的高度  
	//window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽  
	var iTop = (window.screen.height - 30 - iHeight) / 2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width - 10 - iWidth) / 2; //获得窗口的水平位置;  
	window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}