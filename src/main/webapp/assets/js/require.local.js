/**使用ajax 同步加载资源 **/
function html_require(url) {
    return $.get({
        type: "get",
        url: url,
        dataType: 'text',
        async: false,
        cache: true,
        complete: function (XMLHttpRequest, textStatus) {}
    }).responseText;
}

function html_require_asyn(url, callback) {
    $.get({
        type: "get",
        url: url,
        dataType: 'text',
        async: true,
        cache: true,
        success: callback,
        complete: function (XMLHttpRequest, textStatus) {}
    })
}
/**
 * html require promise 实现
 * @param {*} url html地址
 */
function html_require_promise(url) {
    let p = new Promise((resolve, reject) => {
        $.get({
            type: "get",
            url: url,
            dataType: 'text',
            async: true,
            cache: true,
            success(result) {
                resolve(result);
            },
            error(xhr, status, _error) {
                reject(_error);
            },
            complete: function (XMLHttpRequest, textStatus) {}
        })
    });

    return p;
}
/**
 * Promise 异步 加载 组件
 * @param {*} resolve 
 * @param {*} reject 
 * @param {*} comp 
 * 使用方法:
 * conponents:{
       'test':(resolve,reject)=>require_comp(resolve,reject,MessageNotice)
   }
 */
function require_comp(resolve, reject, comp) {
    if (!comp.info.template_url) {
        resolve(comp);
    } else {
        html_require_asyn(comp.info.template_url, function (html) {
            comp.template = html;
            resolve(comp);
        })
    }
}
/**
 * fake method
 */
function require(){

}