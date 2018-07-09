package com.zltel.broadcast.publish.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.FileContentTypeUtil;
import com.zltel.broadcast.common.util.FileUtil;
import com.zltel.broadcast.common.util.UUID;
import com.zltel.broadcast.publish.Constant;
import com.zltel.broadcast.publish.service.MaterialService;
import com.zltel.broadcast.publish.utils.MaterialUtil;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 素材相关操作 MaterialController class
 *
 * @author Touss
 * @date 2018/5/10
 */
@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {
    @Value("${material.temp.dir}")
    private String uploadTempDir;
    @Value("${material.file.dir}")
    private String uploadFileDir;
    @Value("${material.local.dir}")
    private String uploadLocalDir;
    @Autowired
    private MaterialService materialService;

    @PostMapping("/uploadImage")
    @ResponseBody
    public R uploadImage(@RequestParam("file") MultipartFile file) {
        R r = new R();
        if (!file.isEmpty()) {
            try {
                saveFile(file, r, Constant.MATERIAL_TYPE_IMAGE);
            } catch (Exception e) {
                logout.error(e.getMessage());
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @PostMapping("/uploadVideo")
    @ResponseBody
    public R uploadVideo(@RequestParam("file") MultipartFile file) {
        R r = new R();
        if (!file.isEmpty()) {
            try {
                saveFile(file, r, Constant.MATERIAL_TYPE_VIDEO);
            } catch (Exception e) {
                logout.error(e.getMessage());
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @PostMapping("/uploadAudio")
    @ResponseBody
    public R uploadAudio(@RequestParam("file") MultipartFile file) {
        R r = new R();
        if (!file.isEmpty()) {
            try {
                saveFile(file, r, Constant.MATERIAL_TYPE_AUDIO);
            } catch (Exception e) {
                logout.error(e.getMessage());
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        R r = new R();
        if (!file.isEmpty()) {
            try {
                saveFile(file, r, MaterialUtil.getFileType(file.getOriginalFilename()));
            } catch (Exception e) {
                logout.error(e.getMessage());
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @GetMapping("/image/{name}")
    public void image(@PathVariable("name") String name, HttpServletResponse response) {
        int id = Integer.parseInt(name.split("\\.")[0]);
        Map<String, Object> material = materialService.getMaterial(id);
        if (material == null || !Constant.MATERIAL_TYPE_IMAGE.equals(material.get("type"))) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                String url = (String) material.get("url");
                String suffix = url.substring(url.lastIndexOf(".") + 1, url.length());
                response.setContentType("image/" + suffix);
                writeFile(uploadFileDir + url, response);
            } catch (Exception e) {
                logout.error(e.getMessage());
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") int id, HttpServletResponse response) {
        Map<String, Object> material = materialService.getMaterial(id);
        if (material == null || (material.get("url") == null)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                String url = (String) material.get("url");
                String fileName = url.substring(url.lastIndexOf("/"), url.length());
                // response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
                writeFile(uploadFileDir + url, response);
            } catch (Exception e) {
                logout.error(e.getMessage());
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void writeFile(String file, HttpServletResponse response) {
        try (InputStream in = new FileInputStream(file); ServletOutputStream out = response.getOutputStream();) {
            String ct = FileContentTypeUtil.getContentType(file);
            response.setContentType(ct);
            if (ct.startsWith("application")) {
                response.setHeader("Content-Disposition", "attachment; filename=" + file);
            } else {
                response.setDateHeader("Expires", System.currentTimeMillis() + 60 * 60 * 1000);// 缓存时间一小时
            }
            IOUtils.copy(in, out);
        } catch (FileNotFoundException ffe) {
            logout.error(ffe.getMessage());
        } catch (Exception e) {
            logout.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void saveFile(MultipartFile file, R r, String type) throws IOException {
        String fileName = file.getOriginalFilename();
        String saveName = UUID.get() + FileUtil.getSuffix(fileName);
        String relateDir = "/" + getSysUser().getUserId() + "/" + FileUtil.getYMD() + "/";
        String saveDir = uploadTempDir + relateDir;
        FileUtil.createDir(saveDir);
        String savePath = saveDir + saveName;
        file.transferTo(new File(savePath));
        String url = relateDir + saveName;

        r.put("url", url);
        r.put("name", fileName);
        r.put("type", type);
        r.put("isFile", true);
    }

    @PostMapping("/commonUpload")
    @ResponseBody
    public R commonUpload(@RequestParam("file") MultipartFile file) {
        R r = new R();
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String relateDir = "/" + getSysUser().getUserId() + "/" + FileUtil.getYMD() + "/";
                String saveDir = uploadLocalDir + relateDir;
                FileUtil.createDir(saveDir);
                String saveName = UUID.get() + FileUtil.getSuffix(fileName);
                String savePath = saveDir + saveName;
                file.transferTo(new File(savePath));
                String url = relateDir + saveName;
                r.put("url", "/material/commonDownload/" + url.replaceAll("/", "_"));
                r.put("path", url);
            } catch (IOException e) {
                logout.error(e.getMessage());
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @GetMapping("/commonDownload/{path}")
    public void commonDownload(@PathVariable("path") String path, HttpServletResponse response) {
        try {
            writeFile(uploadLocalDir + path.replaceAll("_", "/"), response);
        } catch (Exception e) {
            logout.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
