package com.zltel.broadcast.um.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.annotation.LogPoint;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.service.OrganizationRelationService;

import io.swagger.annotations.ApiOperation;

/**
 * 组织关系控制
 * @author 张毅
 * @since jdk1.8.0_172
 * date：2018.5.13
 */
@RequestMapping(value="/org/relation")
@RestController
public class OrganizationRelationController extends BaseController {
	@Autowired
	private OrganizationRelationService organizationRelationService;
	
	/**
	 * 查询组织关系
	 * @param organizationRelation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgRelations", method=RequestMethod.POST)
	@LogPoint("查询组织关系")
	@RequiresPermissions(value = {"org:relation:query"})
	@ApiOperation(value = "查询组织关系")
	public R queryOrgRelations(@RequestParam Map<String, Object> orgRelationConditiona, int pageNum, int pageSize) {
		try {
			return organizationRelationService.queryOrgRelations(orgRelationConditiona, pageNum, pageSize);
		} catch (Exception e) {
			logout.error("查询组织关系失败",e);
			return R.error().setMsg("查询组织关系失败");
		}
	}
	
	/**
	 * 查询组织关系
	 * @param organizationRelation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgRelationNewsNotPage", method=RequestMethod.POST)
	@LogPoint("查询组织关系")
	@RequiresPermissions(value = {"org:relation:query"})
	@ApiOperation(value = "查询组织关系")
	public R queryOrgRelationNewsNotPage(@RequestParam Map<String, Object> orgRelationConditiona) {
		try {
			return organizationRelationService.queryOrgRelationNewsNotPage(orgRelationConditiona);
		} catch (Exception e) {
		    logout.error("查询组织关系失败",e);
			return R.error().setMsg("查询组织关系失败");
		}
	}
	
	/**
	 * 查询组织关系
	 * @param organizationRelation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgRelationNews", method=RequestMethod.POST)
	@LogPoint("查询组织关系")
	@RequiresPermissions(value = {"org:relation:query"})
	@ApiOperation(value = "查询组织关系")
	public R queryOrgRelationNews(@RequestParam Map<String, Object> orgRelationConditiona, int pageNum, int pageSize) {
		try {
			return organizationRelationService.queryOrgRelationNews(orgRelationConditiona, pageNum, pageSize);
		} catch (Exception e) {
		    logout.error("查询组织关系失败",e);
			return R.error().setMsg("查询组织关系失败");
		}
	}
	
	/**
	 * 查询组织关系
	 * @param organizationRelation 条件
	 * @return
	 */
	@RequestMapping(value="/queryOrgRelationsNotPage", method=RequestMethod.POST)
	@LogPoint("查询组织关系")
	@RequiresPermissions(value = {"org:relation:query"})
	@ApiOperation(value = "查询组织关系")
	public R queryOrgRelationsNotPage(@RequestParam Map<String, Object> organizationRelation) {
		try {
			return organizationRelationService.queryOrgRelationsNotPage(organizationRelation);
		} catch (Exception e) {
			logout.error("查询组织关系失败",e);
			return R.error().setMsg("查询组织关系失败");
		}
	}
	
	
	/**
	 * 批量删除组织关系
	 * @param organizationRelation 要删除的组织关系
	 * @return
	 */
	@RequestMapping(value="/deleteOrgRelation", method=RequestMethod.POST)
	@LogPoint("批量删除组织关系")
	@RequiresPermissions(value = {"org:relation:delete"})
	@ApiOperation(value = "批量删除组织关系")
	public R deleteOrgRelation(OrganizationRelation organizationRelation) {
		try {
			return organizationRelationService.deleteOrgRelation(organizationRelation);	//开始删除组织关系
		} catch (Exception e) {
			logout.error("组织关系删除失败",e);
			return R.error().setMsg("组织关系删除失败。");
		}
	}
	
	
	/**
	 * 添加组织关系
	 * @param organizationRelation 要添加的组织关系
	 * @return
	 */
	@RequestMapping(value="/insertOrgRelation", method=RequestMethod.POST)
	@LogPoint("添加组织关系")
	@RequiresPermissions(value = {"org:relation:insert"})
	@ApiOperation(value = "添加组织关系")
	public R insertOrgRelation(OrganizationRelation organizationRelation,
			@RequestParam(required = false, name = "orgRltDutys[]") List<Integer> orgRltDutys) {
		try {
			return organizationRelationService.insertOrgRelation(organizationRelation, orgRltDutys);	//开始添加组织关系
		} catch (Exception e) {
		    logout.error("组织关系添加失败",e);
			return R.error().setMsg("组织关系添加失败");
		}
	}
	
	/**
	 * 查询存在党员的组织
	 * @param conditions 条件
	 * @return
	 */
	@RequestMapping(value="/queryHavePartyUserOrg", method=RequestMethod.POST)
	@ApiOperation(value = "查询存在党员的组织")
	public R queryHavePartyUserOrg(Map<String, Object> conditions) {
		try {
			return organizationRelationService.queryHavePartyUserOrg(conditions);	//开始添加组织关系
		} catch (Exception e) {
		    logout.error("查询存在党员的组织失败",e);
			return R.error().setMsg("查询存在党员的组织失败");
		}
	}
}
