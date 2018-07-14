package com.zltel.broadcast.doc_handler.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zltel.broadcast.common.configuration.SystemInfoConfig;
import com.zltel.broadcast.common.controller.BaseController;

@Controller
@RequestMapping("/doc-view/image/")
public class DocHandlerController extends BaseController {

    @Resource
    private SystemInfoConfig sysInfoConfig;

    @RequestMapping("/{type}/{dir}/{resName}")
    public void view(@PathVariable("type") String type, @PathVariable("dir") String dir,
            @PathVariable("resName") String resName, HttpServletResponse response) throws IOException {
        String url = sysInfoConfig.getMediaServerUrl().append(type).append("/").append(dir).append("/").append(resName)
                .toString();
        logout.debug("重定资源地址:{}", url);
        response.sendRedirect(url);
    }

}
