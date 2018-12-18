package com.zltel.broadcast.example.exception.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;

@RestController
@RequestMapping("/exception-test")
public class ExceptionTestController {

	@GetMapping("/mkrrexception")
	public R createRRe() {
		throw new RRException("创建运行异常");
	}

	@GetMapping("/mkexception")
	public R createException() throws Exception {
		throw new Exception("创建异常");
	}

}
