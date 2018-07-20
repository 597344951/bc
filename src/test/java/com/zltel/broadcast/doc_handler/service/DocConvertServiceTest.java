package com.zltel.broadcast.doc_handler.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.junit.Test;

import com.zltel.BroadcastApplicationTests;

public class DocConvertServiceTest extends BroadcastApplicationTests {

    @Resource
    private DocConvertService service;


    @Test
    public void testWordToHtml() throws Exception {
        String bp = "/doc_handler/";
        String path = bp + "word.doc";
        String url = this.getClass().getResource(path).getFile();
        logout.info(url);
        File tp = new File(url);
        if (!tp.exists()) {
            logout.error("路径不存在:{}", url);
            return;
        }
        InputStream in = new FileInputStream(tp);

        String html = this.service.wordToHtml(in, path);
        logout.info("读取出html: {}", html.length());
    }

    @Test
    public void testWordToHtmlForDocx() throws Exception {
        String bp = "/doc_handler/";
        String path = bp + "word.docx";
        String url = this.getClass().getResource(path).getFile();
        logout.info(url);
        File tp = new File(url);
        if (!tp.exists()) {
            logout.error("路径不存在:{}", url);
            return;
        }
        InputStream in = new FileInputStream(tp);

        String html = this.service.wordToHtml(in, path);
        logout.info("读取出html: {}", html.length());
    }
}
