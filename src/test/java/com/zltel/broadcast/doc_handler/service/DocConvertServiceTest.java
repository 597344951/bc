package com.zltel.broadcast.doc_handler.service;


import java.io.InputStream;

import javax.annotation.Resource;

import org.junit.Test;

import com.zltel.BroadcastApplicationTests;

public class DocConvertServiceTest extends BroadcastApplicationTests {

    @Resource
    private DocConvertService service;


    @Test
    public void testWordToHtml() throws Exception {
        String bp = "com/zltel/broadcast/doc_handler/resource/";
        String path1 = bp + "word.doc";
        String fn1 = "word.doc";
        InputStream in1 = Thread.currentThread().getContextClassLoader().getResourceAsStream(path1);
        String html = this.service.wordToHtml(in1, fn1);
        logout.info("读取出html: {}", html.length());
    }

    @Test
    public void testWordToHtmlForDocx() throws Exception {
        String bp = "com/zltel/broadcast/doc_handler/resource/";
        String path1 = bp + "word.docx";
        String fn1 = "word.docx";
        InputStream in1 = Thread.currentThread().getContextClassLoader().getResourceAsStream(path1);
        String html = this.service.wordToHtml(in1, fn1);
        logout.info("读取出html: {}", html.length());
    }
}
