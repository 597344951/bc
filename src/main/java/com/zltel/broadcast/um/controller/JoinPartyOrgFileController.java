package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.um.service.JoinPartyOrgFileService;

/**
 * 入党提交的相关材料
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.8.21
 */
@RequestMapping(value="/join/file")
@RestController
public class JoinPartyOrgFileController extends BaseController {
	@Autowired
	private JoinPartyOrgFileService joinPartyOrgFileService;
}
