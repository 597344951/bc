package com.zltel.broadcast.um.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationDuty;
import com.zltel.broadcast.um.service.OrganizationDutyService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织职责控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.13
 */
@RequestMapping(value="/org/duty")
@RestController
public class OrganizationDutyController extends BaseController {
	@Autowired
	private OrganizationDutyService organizationDutyService;
	
	/**
	 * 查询组织职责
	 * @param organizationDuty 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgDutys", method=RequestMethod.POST)
	@LogPoint("查询组织职责")
	@RequiresPermissions(value = {"org:duty:query"})
	@ApiOperation(value = "查询组织职责")
	public R queryOrgDutys(OrganizationDuty organizationDuty, int pageNum, int pageSize) {
		try {
			return organizationDutyService.queryOrgDutys(organizationDuty, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织职责失败");
		}
	}
	
	/**
	 * 查询组织职责
	 * @param organizationDuty 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgDutyForOrgInfoClick", method=RequestMethod.POST)
	@LogPoint("查询组织职责")
	@RequiresPermissions(value = {"org:duty:query"})
	@ApiOperation(value = "查询组织职责")
	public R queryOrgDutyForOrgInfoClick(OrganizationDuty organizationDuty) {
		try {
			return organizationDutyService.queryOrgDutyForOrgInfoClick(organizationDuty);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织职责失败");
		}
	}
	
	/**
	 * 查询组织职责
	 * @param organizationDuty 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgDutyTreeForOrgInfo", method=RequestMethod.POST)
	@LogPoint("查询组织职责")
	@RequiresPermissions(value = {"org:duty:query"})
	@ApiOperation(value = "查询组织职责")
	public R queryOrgDutyTreeForOrgInfo(OrganizationDuty organizationDuty) {
		try {
			return organizationDutyService.queryOrgDutyTreeForOrgInfo(organizationDuty);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织职责失败");
		}
	}
	
	/**
	 * 查询组织职责
	 * @param organizationDuty 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgDutysOfQueryRelations", method=RequestMethod.POST)
	@LogPoint("查询组织职责")
	@RequiresPermissions(value = {"org:duty:query"})
	@ApiOperation(value = "查询组织职责")
	public R queryOrgDutysOfQueryRelations() {
		try {
			return organizationDutyService.queryOrgDutysOfQueryRelations(null);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织职责失败");
		}
	}
	
	/**
	 * 查询组织职责
	 * @param organizationDuty 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgDutysNotPage", method=RequestMethod.POST)
	@LogPoint("查询组织职责")
	@RequiresPermissions(value = {"org:duty:query"})
	@ApiOperation(value = "查询组织职责")
	public R queryOrgDutysNotPage(OrganizationDuty organizationDuty) {
		try {
			return organizationDutyService.queryOrgDutysNotPage(organizationDuty);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("查询组织职责失败");
		}
	}
	
	/**
	 * 修改组织职责
	 * @param organizationDuty 要修改的组织职责
	 * @return
	 */
	@RequestMapping(value="/updateOrgDuty", method=RequestMethod.POST)
	@LogPoint("修改组织职责")
	@RequiresPermissions(value = {"org:duty:update"})
	@ApiOperation(value = "修改组织职责")
	public R updateOrgDuty(OrganizationDuty organizationDuty) {
		try {
			return organizationDutyService.updateOrgDuty(organizationDuty);	//开始修改职责
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("组织职责修改出错。");
		}
	}
	
	/**
	 * 批量删除组织职责
	 * @param organizationDuty 要删除的组织职责
	 * @return
	 */
	@RequestMapping(value="/deleteOrgDuty", method=RequestMethod.POST)
	@LogPoint("批量删除组织职责")
	@RequiresPermissions(value = {"org:duty:delete"})
	@ApiOperation(value = "批量删除组织职责")
	public R deleteOrgDuty(OrganizationDuty organizationDuty) {
		try {
			return organizationDutyService.deleteOrgDuty(organizationDuty);	//开始删除组织职责
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("组织职责删除失败。");
		}
	}
	
	
	/**
	 * 添加组织职责
	 * @param organizationDuty 要添加的组织职责
	 * @return
	 */
	@RequestMapping(value="/insertOrgDuty", method=RequestMethod.POST)
	@LogPoint("添加组织职责")
	@RequiresPermissions(value = {"org:duty:insert"})
	@ApiOperation(value = "添加组织职责")
	public R insertOrgDuty(OrganizationDuty organizationDuty) {
		try {
			return organizationDutyService.insertOrgDuty(organizationDuty);	//开始添加组织职责
		} catch (Exception e) {
			e.printStackTrace();
			return R.error().setMsg("组织职责添加失败。");
		}
	}
}
