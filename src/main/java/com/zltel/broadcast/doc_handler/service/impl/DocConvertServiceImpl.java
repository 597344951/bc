package com.zltel.broadcast.doc_handler.service.impl;

import java.io.File;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zltel.broadcast.common.configuration.SystemInfoConfig;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.util.UUID;
import com.zltel.broadcast.doc_handler.service.DocConvertService;
import com.zltel.broadcast.doc_handler.util.WordToHtmlUtil;
import com.zltel.broadcast.incision.sola.utils.HttpUtil;

@Service
public class DocConvertServiceImpl implements DocConvertService {

    private static final Logger logout = LoggerFactory.getLogger(DocConvertServiceImpl.class);


    @Resource
    SystemInfoConfig systemInfoCofig;

    @Override
    public String wordToHtml(InputStream in, String fileName) {
        String html = "";
        String uuid = UUID.get();
        File tmpBase = SystemUtils.getJavaIoTmpDir();
        logout.debug("获取系统临时目录: {}", tmpBase.getPath());
        File workDir = createWorkDir(tmpBase, uuid);
        logout.debug("生成处理工作目录: {}", workDir.getPath());
        try {
            if (fileName.toLowerCase().endsWith(".doc")) {
                html = WordToHtmlUtil.docToHtml(in, workDir, uuid);
            } else if (fileName.toLowerCase().endsWith(".docx")) {
                html = WordToHtmlUtil.docxToHtml(in, workDir, uuid);
            } else {
                RRException.makeThrow("未知的文件类型: " + fileName);
            }
            commitImages(workDir);
        } catch (Throwable e) {
            RRException.makeThrow(e.getMessage());
        } finally {
            logout.debug("删除工作目录:{}", workDir.getPath());
            FileUtils.deleteQuietly(workDir);
        }

        return html;
    }

    /**
     * 提交工作目录下的资源到资源服务器
     * 
     * @param workDir
     */
    private void commitImages(File workDir) {
        String burl = systemInfoCofig.getCustomizeUploadUrl();

        for (File fd : workDir.listFiles()) {
            if (fd.isDirectory()) {
                // 目录
                this.commitImages(fd);
            } else {
                File p = fd.getParentFile();
                File pp = p.getParentFile();

                String url = burl + "/" + pp.getName() + "/" + p.getName();
                logout.debug("提交资源服务器地址： {}", url);
                // 文件
                Object data = HttpUtil.postFile(url, "file", fd);
                logout.debug("上传结果: {}", JSON.toJSONString(data));
            }
        }
    }

    /** 创建工作目录 **/
    private File createWorkDir(File tmpBase, String uuid) {
        File w = new File(tmpBase, uuid);
        if (!w.exists()) w.mkdirs();
        return w;
    }



}
