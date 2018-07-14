package com.zltel.broadcast.doc_handler.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.convert.out.html.SdtToListSdtTagHandler;
import org.docx4j.convert.out.html.SdtWriter;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.w3c.dom.Document;

import com.zltel.broadcast.um.util.DateUtil;

/**
 * Word转化为html
 * 
 * @author wangch
 *
 */
public class WordToHtmlUtil {
    public static final String basePath = "/doc-view/image/";

    public static final String WORD_IMG = "word_img/";
    // 基础路径/word_img/时间/序列id_文件名

    /** 生成文件名 **/
    public static String getResName(String id, String sugName) {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("_").append(sugName);
        return sb.toString();
    }

    /** 获取父路径名称 **/
    public static String getParentName() {

        return WORD_IMG + DateUtil.formatDate(DateUtil.YYYYMMDD, new Date());
    }

    /** 获取 资源地址名称 **/
    public static String getResUrl(String resName) {
        return basePath + getParentName() + "/" + resName;
    }

    /** 获取保存路径 **/
    public static String getSavePath(File base) {
        File savePath = new File(base, getParentName());

        if (!savePath.exists()) savePath.mkdirs();
        return savePath.getPath();
    }

    /**
     * doc 文档转html
     * 
     * @param docIn 文档输入流
     * @param workTmp
     * @param id
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public static String docToHtml(InputStream docIn, File workTmp, String id) throws Throwable {
        // 图片原始名 - 重新计算名 映射
        Map<String, String> imageNameMap = new HashMap<>();
        // 实例化WordToHtmlConverter，为图片等资源文件做准备
        WordToHtmlConverter wordToHtmlConverter =
                new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager((byte[] content, PictureType pictureType, String suggestedName,
                float widthInches, float heightInches) -> {
            String resName = getResName(id, suggestedName);
            imageNameMap.put(suggestedName, resName);
            return getResUrl(resName);
        });

        // docx
        HWPFDocument wordDocument = new HWPFDocument(docIn);
        wordToHtmlConverter.processDocument(wordDocument);
        // 处理图片，会在同目录下生成 image/media/ 路径并保存图片
        List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
            for (Picture pic : pics) {
                String saveName = imageNameMap.get(pic.suggestFullFileName());
                String savedPath = getSavePath(workTmp) + File.separatorChar + saveName;
                try (FileOutputStream out = new FileOutputStream(savedPath)) {
                    // 图片保存入
                    pic.writeImageContent(out);
                }
            }
        }

        // 转换
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");// 编码格式
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");// 是否用空白分割
        serializer.setOutputProperty(OutputKeys.METHOD, "html");// 输出类型
        serializer.transform(domSource, streamResult);
        return outStream.toString();
    }

    public static String docxToHtml(InputStream docIn, File workTmp, String id) throws Throwable {

        final WordprocessingMLPackage wmlPackage = Docx4J.load(docIn);// 读取word文档
        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();// 转html的设置
        String savedPath = getSavePath(workTmp) + File.separatorChar;
        htmlSettings.setImageDirPath(savedPath);// 设置保存图片路径
        String url = basePath + getParentName();
        htmlSettings.setImageTargetUri(url);
        htmlSettings.setWmlPackage(wmlPackage);
        SdtWriter.registerTagHandler("HTML_ELEMENT", new SdtToListSdtTagHandler());
        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", false);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Docx4J.toHTML(htmlSettings, outputStream, Docx4J.FLAG_EXPORT_PREFER_XSL);// word文档转html

        return outputStream.toString();
    }
}
