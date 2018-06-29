package com.zltel.broadcast.um.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUserInfo;
import com.zltel.broadcast.um.service.PartyUserInfoService;

import io.swagger.annotations.ApiOperation;

/**
 * 党员信息
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.29
 */
@RequestMapping(value="/party/user")
@RestController
public class PartyUserInfoController extends BaseController  {
	@Autowired
	private PartyUserInfoService partyUserInfoService;
	
	/**
	 * 查询党员信息
	 * @param partyUserMap 条件
	 * @return
	 */
	@RequestMapping(value="/queryPartyUserInfos", method=RequestMethod.POST)
	@LogPoint("查询党员信息")
	@RequiresPermissions(value = {"party:user:query"})
	@ApiOperation(value = "查询党员信息")
	public R queryPartyUserInfos(@RequestParam Map<String, Object> partyUserMap, int pageNum, int pageSize) {
		try {
			return partyUserInfoService.queryPartyUserInfos(partyUserMap, pageNum, pageSize);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("查询党员信息失败");
		}
	}
	
	/**
	 * 得到党员证件照
	 * @param idPhotoPath 参数
	 * @return
	 */
	@RequestMapping(value="/getPartyUserInfoIdPhoto", method=RequestMethod.GET)
	@LogPoint("得到党员证件照")
	@RequiresPermissions(value = {"party:user:query"})
	@ApiOperation(value = "得到党员证件照")
	public void getPartyUserInfoIdPhoto(HttpServletResponse response, int partyId) {
		try {
			partyUserInfoService.getPartyUserInfoIdPhoto(response, partyId);
		} catch (Exception e) {
			logout.error(e.getMessage());
		}
	}
	
	/**
	 * 暂时保存照片
	 * @param file 参数
	 * @return
	 */
	@RequestMapping(value="/savePartyUserInfoIdPhoto", method=RequestMethod.POST)
	@LogPoint("暂时保存照片")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "暂时保存照片")
	public R savePartyUserInfoIdPhoto(HttpServletRequest request, @RequestParam MultipartFile file) {
		try {
			return partyUserInfoService.savePartyUserInfoIdPhoto(request, file);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("后台出错，停止党员注册");
		}
	}
	
	/**
	 * 添加党员
	 * @param partyUser 参数
	 * @return
	 */
	@RequestMapping(value="/insertPartyUserInfo", method=RequestMethod.POST)
	@LogPoint("添加党员")
	@RequiresPermissions(value = {"party:user:insert"})
	@ApiOperation(value = "添加党员")
	public R insertPartyUserInfo(HttpServletRequest request, @RequestParam Map<String, Object> partyUser) {
		try {
			return partyUserInfoService.insertPartyUserInfo(request, partyUser);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("添加党员信息出错");
		}
	}
	
	/**
	 * 修改证件照
	 * @param partyUser 参数
	 * @return
	 */
	@RequestMapping(value="/updatePartyUserIdPhoto", method=RequestMethod.POST)
	@LogPoint("修改证件照")
	@RequiresPermissions(value = {"party:user:update"})
	@ApiOperation(value = "修改证件照")
	public R updatePartyUserIdPhoto(HttpServletRequest request, MultipartFile file, @RequestParam Map<String, Object> partyUser) {
		try {
			return partyUserInfoService.updatePartyUserIdPhoto(request, file, partyUser);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("修改证件照出错");
		}
	}
	
	/**
	 * 修改党员信息
	 * @param partyUser 参数
	 * @return
	 */
	@RequestMapping(value="/updatePartyUserInfo", method=RequestMethod.POST)
	@LogPoint("修改党员信息")
	@RequiresPermissions(value = {"party:user:update"})
	@ApiOperation(value = "修改党员信息")
	public R updatePartyUserInfo(HttpServletRequest request, @RequestParam Map<String, Object> partyUser) {
		try {
			return partyUserInfoService.updatePartyUserInfo(request, partyUser);
		} catch (Exception e) {
			logout.error(e.getMessage());
			return R.error().setMsg("修改党员信息出错");
		}
	}
	
	/**
	 * 批量删除党员
	 * @param partyUser 要删除的党员, 目前只删除一个党员
	 * @return
	 */
	@RequestMapping(value="/deletePartyUserInfo", method=RequestMethod.POST)
	@LogPoint("批量删除党员用户")
	@RequiresPermissions(value = {"party:user:delete"})
	@ApiOperation(value = "批量删除党员用户")
	public R deletePartyUserInfo(PartyUserInfo partyUserInfo) {
		try {
			return partyUserInfoService.deletePartyUserInfo(partyUserInfo);
		} catch (Exception e) {
			return R.error().setMsg("删除党员失败。");
		}
	}
}
