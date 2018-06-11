package com.zltel.broadcast.publish.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.util.FileUtil;
import com.zltel.broadcast.common.util.UUID;
import com.zltel.broadcast.publish.Constant;
import com.zltel.broadcast.publish.service.MaterialService;
import com.zltel.broadcast.publish.utils.MaterialUtil;
import com.zltel.broadcast.um.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 素材相关操作
 * MaterialController class
 *
 * @author Touss
 * @date 2018/5/10
 */
@Controller
@RequestMapping("/material")
@PropertySource("classpath:upload.properties")
public class MaterialController extends BaseController {
    @Value("${upload.temp.dir}")
    private String uploadTempDir;
    @Value("${upload.file.dir}")
    private String uploadFileDir;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/uploadImage")
    @ResponseBody
    public R uploadImage (@RequestParam("file") MultipartFile file) {
        R r = new R();
        if(!file.isEmpty()) {
            try {
                saveFile(file, r, Constant.MATERIAL_TYPE_PICTURE);
            } catch (Exception e) {
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @PostMapping("/uploadVideo")
    @ResponseBody
    public R uploadVideo (@RequestParam("file") MultipartFile file) {
        R r = new R();
        if(!file.isEmpty()) {
            try {
                saveFile(file, r, Constant.MATERIAL_TYPE_VIDEO);
            } catch (Exception e) {
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
        if(!file.isEmpty()) {
            try {
                saveFile(file, r, Constant.MATERIAL_TYPE_AUDIO);
            } catch (Exception e) {
                r.setStatus(false);
                r.put("msg", "Upload failed.");
            }
        }
        return r;
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public R uploadFile (@RequestParam("file") MultipartFile file) {
        R r = new R();
        if(!file.isEmpty()) {
            try {
                saveFile(file, r, MaterialUtil.getFileType(file.getOriginalFilename()));
            } catch (Exception e) {
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
        if(material == null || !Constant.MATERIAL_TYPE_PICTURE.equals(material.get("type"))) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                String url = (String) material.get("url");
                String suffix = url.substring(url.lastIndexOf(".") + 1, url.length());
                response.setContentType("image/"+suffix);
                writeFile(uploadFileDir + url, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        }
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") int id, HttpServletResponse response) {
        Map<String, Object> material = materialService.getMaterial(id);
        if(material == null || (material.get("url") == null)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                String url = (String) material.get("url");
                String fileName = url.substring(url.lastIndexOf("/"), url.length());
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
                writeFile(uploadFileDir + url, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void writeFile(String file, HttpServletResponse response) {
        ServletOutputStream out = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            out = response.getOutputStream();
            byte[] buffer = new byte[512];
            int length;
            while((length = is.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void saveFile(MultipartFile file, R r, String type) throws IOException {
        String fileName = file.getOriginalFilename();
        String relateDir = uploadTempDir + "/" + getSysUser().getUserId() + "/" + FileUtil.getYMD() + "/";
        String saveDir = request.getSession().getServletContext().getRealPath("/") + relateDir;
        FileUtil.createDir(saveDir);
        String saveName = UUID.get() + FileUtil.getSuffix(fileName);
        String savePath = saveDir + saveName;
        String url = relateDir + saveName;
        file.transferTo(new File(savePath));
        r.put("url", url);
        r.put("name", fileName);
        r.put("type", type);
        r.put("isFile", true);
    }
}
