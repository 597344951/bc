package com.zltel.broadcast.doc_handler.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Generated;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.util.UUID;

@Generated(value = "org.junit-tools-1.0.6")
public class WordToHtmlUtilTest extends BaseTests {



    @Test
    public void testDocToHtml() throws Throwable {
        String bp = "/doc_handler/";
        String path1 = bp + "word.doc";
        String url = this.getClass().getResource(path1).getFile();
        logout.info(url);
        File tp = new File(url);
        if (!tp.exists()) {
            logout.error("路径不存在:{}", url);
            return;
        }
        InputStream in = new FileInputStream(tp);

        String id = UUID.get();
        File tmp = FileUtils.getTempDirectory();
        File workTmp = new File(tmp, id);
        try {
            if (!workTmp.exists()) workTmp.mkdirs();
            String html = WordToHtmlUtil.docToHtml(in, workTmp, id);
            logout.info(html);
        } finally {
            FileUtils.deleteQuietly(workTmp);
        }

    }

    @Test
    public void testDocxToHtml() throws Throwable {
        String bp = "/doc_handler/";
        String path1 = bp + "word.docx";
        String url = this.getClass().getResource(path1).getFile();
        logout.info(url);
        File tp = new File(url);
        if (!tp.exists()) {
            logout.error("路径不存在:{}", url);
            return;
        }
        InputStream in = new FileInputStream(tp);


        String id = UUID.get();
        File tmp = FileUtils.getTempDirectory();
        File workTmp = new File(tmp, id);
        try {
            if (!workTmp.exists()) workTmp.mkdirs();
            String html = WordToHtmlUtil.docxToHtml(in, workTmp, id);
            logout.info(html);
        } finally {
            FileUtils.deleteQuietly(workTmp);
        }

    }

}
