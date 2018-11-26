package com.zltel.broadcast.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyOrgProcess;
import com.zltel.broadcast.um.service.JoinPartyOrgProcessService;

import io.swagger.annotations.ApiOperation;

/**
 * 入党步骤
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.8.21
 */
@RequestMapping(value="/join/process")
@RestController
public class JoinPartyOrgProcessController extends BaseController {
	@Autowired
	private JoinPartyOrgProcessService joinPartyOrgProcessService;
	
	/**
	 * 查询加入党的步骤
	 * @param jpop 条件
	 * @return
	 */
	@RequestMapping(value="/queryJoinPartyOrgProcess", method=RequestMethod.POST)
	@ApiOperation(value = "查询加入党的步骤")
	public R queryJoinPartyOrgProcess(JoinPartyOrgProcess jpop) {
		try {
			return joinPartyOrgProcessService.queryJoinPartyOrgProcess(jpop);
		} catch (Exception e) {
			return R.error().setMsg("查询加入党的步骤失败");
		}
	}
}
