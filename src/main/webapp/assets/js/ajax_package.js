/**公共ajax封装**/
/** 为所有 AJAX 请求设置默认* */
$.ajaxSetup({
    async: true,
    dataType: 'json',
    type: 'post',
    //默认complet 回调,如果不须换被调用此函数,需自行覆盖 ajax中的complete回调
    complete: function (XMLHttpRequest) {
        var str = XMLHttpRequest.responseText
        let status = XMLHttpRequest.status
        switch (status) {
            case 200: // 正常请求
                break
            case 401: //未登录
                console.log('登陆超时')
                confirm('登陆超时,是否刷新页面?') && top.window.location.reload()
                break
            case 403: //授权失败
                toast('系统错误', '你没有权限访问此路径', 'error')
                break
            default: //其他不正常情况
                let msg = str
                try {
                    let data = JSON.parse(str);
                    if (data.msg) msg = data.msg
                } catch (e) {
                    console.error('序列化结果失败!', e)
                }
                toast('系统错误', msg, 'error')
                break
        }
    }
});

/**
 * 公共Ajax方法
 * 
 * @param url 提交url
 * @param data 提交数据， JSON 格式 或 key=value格式 或 JSON字符串
 */
function ajax(url, method, data, suc, err) {
    if (!method) method = 'get';
    let p = ajax_promise(url, method, data)
    p.then(suc).catch(err);
}
/**
 * ajax 请求promise实现
 * @param {*} url url
 * @param {*} method  http方法
 * @param {*} data 数据格式
 */
function ajax_promise(url, method, data) {
    if (!method)
        method = 'get';
    var p = new Promise(function (resolve, reject) {
        var qp = {
            type: method,
            url: url,
            data: data,
            contentType: 'application/x-www-form-urlencoded', //默认提交数据 格式类型
            success: function (result) {
                resolve(result);
            },
            error: function (xhr, status, _error) {
                reject(xhr, status, _error);
            }
        }
        $.ajax(qp);
    })
    return p;
}

/**
 * 传递ajax数据的情况
 * 
 * @param url
 *            请求地址
 * @param method
 *            请求方法
 * @param obj
 * @param suc
 * @param err
 */
function ajax_json(url, method, data, suc, err) {
    if (!method) method = 'post';
    //get 没办法 提交json数据
    if (method == 'get') return ajax(url, method, data, suc, err);
    let p = ajax_json_promise(url, method, data)
    p.then(suc).catch(err);
}

/**
 * 封装ajax_json promise实现
 */
function ajax_json_promise(url, method, data) {
    var p = new Promise(function (resolve, reject) {
        var qp = {
            type: method,
            url: url,
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            //提交格式为json
            success: function (result) {
                resolve(result);
            },
            error: function (xhr, status, _error) {
                reject(xhr, status, _error);
            }
        }
        $.ajax(qp);
    })
    return p;
}