<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%
//设定返回头 javascript格式
response.setHeader("Content-Type","application/javascript");
%>

let serverUrl = '${sysInfo.mediaserve}'
let serverConfig = {
    //"/upload -> POST - 其他类型文件上传, 参数：file - 文件",
    //"/file/:name -> DELETE - 删除文件, URL参数：name - 文件名称",
    // "/image -> POST - 图片文件上传, 参数：file - 图片文件",
    //"/image/:name -> DELETE - 删除图片文件, URL参数：name - 图片名称",
    //"/ueditor -> GET - Ueditor全配置",
    //"/ueditor -> POST - Ueditor相关操作：参数 action - 对应操作",
    //"/video -> POST - 视频文件上传, 参数：file - 视频文件",
    //"/video/:name -> DELETE - 删除视频文件, URL参数：name - 视频名称"

    getUploadUrl(contentType){
        if(contentType.startsWith('image')){
            return serverUrl + '/image';
        }
        if(contentType.startsWith('audio') || contentType.startsWith('video')){
            return serverUrl + '/video';
        }
        
        return serverUrl + '/upload';
    },
    getUrl(url){
        return serverUrl + url;
    } 
}

export default serverConfig;
