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