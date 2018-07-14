package com.zltel.broadcast.doc_handler.service;

import java.io.InputStream;

/**
 * 文档处理接口
 * 
 * @author wangch
 *
 */
public interface DocConvertService {
    /**
     * 处理word 成html
     * 
     * @param in 输入流
     * @param fileName word文件名,根据.doc 或 .docx 决定处理方式
     * @return html代码
     * @junit {@link com.zltel.broadcast.doc_handler.service.DocConvertServiceTest#testWordToHtml()}
     */
    String wordToHtml(InputStream in, String fileName);
    
    

}
