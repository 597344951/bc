package com.zltel.broadcast.doc_handler.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.util.UUID;

@Generated(value = "org.junit-tools-1.0.6")
public class WordToHtmlUtilTest extends BaseTests {



    @Test
    public void testDocToHtml() throws Throwable {
        String name = "d:/test/convert/3.doc";
        File workTmp = new File("d:/test/convert/");
        String id = UUID.get();
        InputStream docIn = new FileInputStream(name);
        String html = WordToHtmlUtil.docToHtml(docIn, workTmp, id);
        logout.info(html);
    }

    @Test
    public void testDocxToHtml() throws Throwable {
        String name = "d:/test/convert/3.docx";
        File workTmp = new File("d:/test/convert/");
        String id = UUID.get();
        InputStream docIn = new FileInputStream(name);
        String html = WordToHtmlUtil.docxToHtml(docIn, workTmp, id);
        logout.info(html);
    }

}
