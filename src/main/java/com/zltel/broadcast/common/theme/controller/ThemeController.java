package com.zltel.broadcast.common.theme.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.util.ColorUtil;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.service.SysUserService;

@RestController
@RequestMapping("/theme")
public class ThemeController extends BaseController {
    public static final String DEFAULT_COLOR = "#20a0ff";
    public static final String TEMPLATE_FILE_NAME = "templates/elementUi-theme-template.txt";
    
    @Resource
    private SysUserService sysUserService;


    @GetMapping("/color-{color}.css")
    public ResponseEntity changeTheme(@PathVariable("color") String _color) throws IOException {
        SysUser user = this.getSysUser();
        String color = "";
        if (StringUtils.isNotBlank(_color)) {
            color = _color;
        } else {
            if (null == user || StringUtils.isBlank(user.getTheme())) {
                color = DEFAULT_COLOR;
            } else {
                color = user.getTheme();
            }
        }
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(TEMPLATE_FILE_NAME);
        if (in == null) return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).body("/** not found **/");
        OutputStream out = new ByteArrayOutputStream();
        IOUtil.copyCompletely(in, out);
        if (!color.startsWith("#")) color = "#" + color;
        String content = replaceTemplate(out.toString(),color);

        String nc = color.replace("#", "");
        if (user != null) {
            if (!nc.equals(user.getTheme())) {
                user.setTheme(nc);
                //调用保存
                SysUser nu = new SysUser();
                nu.setUserId(user.getUserId());
                nu.setTheme(user.getTheme());
                sysUserService.updateByPrimaryKeySelective(nu);
            }
        }
        //设置允许浏览器缓存信息
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .body(content);
    }
    
    private String replaceTemplate(String in,String color) {
        ColorUtil util = new ColorUtil();
        String hColor = util.gradient(color, 3, -1);
        String aColor = util.gradient(color, 3, 1);
        //${honver-color}
        //${active-color}
        String ret = in.replaceAll("\\$\\{color\\}", color);
        ret = ret.replaceAll("\\$\\{honver-color\\}", color+"ba");//设置透明度引起区别
        ret = ret.replaceAll("\\$\\{active-color\\}", aColor);
        
        //ret = ret.replaceAll(" ", "");
        return ret;
    }

}
